// port-lint: source sync/atomic.rs
package io.github.kotlinmania.rustlang.core.sync.atomic

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.hint.spinLoop
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicFence
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicSignalFence
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.atomicCxchgWithLayout
import io.github.kotlinmania.rustlang.core.mem.atomicLoadWithLayout
import io.github.kotlinmania.rustlang.core.mem.atomicRmwWithLayout
import io.github.kotlinmania.rustlang.core.mem.atomicStoreWithLayout
import io.github.kotlinmania.rustlang.core.mem.atomicXchgWithLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmutability.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.*

// Atomic types
//
// Atomic types provide primitive shared-memory communication between
// threads, and are the building blocks of other concurrent
// types.
//
// This module defines atomic versions of a select number of primitive
// types, including [AtomicBool], [AtomicIsize], [AtomicUsize],
// [AtomicI8], [AtomicU16], etc.
// Atomic types present operations that, when used correctly, synchronize
// updates between threads.
//
// Atomic variables are safe to share between threads (they implement [Sync])
// but they do not themselves provide the mechanism for sharing and follow the
// [threading model](../../../std/thread/index.html#the-threading-model) of Rust.
// The most common way to share an atomic variable is to put it into an [`Arc`][arc] (an
// atomically-reference-counted shared pointer).
//
// [arc]: ../../../std/sync/struct.Arc.html
//
// Atomic types may be stored in static variables, initialized using
// the constant initializers like [AtomicBool.new]. Atomic statics
// are often used for lazy global initialization.
//
// ## Memory model for atomic accesses
//
// Rust atomics currently follow the same rules as [C++20 atomics][cpp], specifically the rules
// from the [`intro.races`][cpp-intro.races] section, without the "consume" memory ordering. Since
// C++ uses an object-based memory model whereas Rust is access-based, a bit of translation work
// has to be done to apply the C++ rules to Rust: whenever C++ talks about "the value of an
// object", we understand that to mean the resulting bytes obtained when doing a read. When the C++
// standard talks about "the value of an atomic object", this refers to the result of doing an
// atomic load (via the operations provided in this module). A "modification of an atomic object"
// refers to an atomic store.
//
// The end result is *almost* equivalent to saying that creating a *shared reference* to one of the
// Rust atomic types corresponds to creating an `atomicRef` in C++, with the `atomicRef` being
// destroyed when the scope of the shared reference ends. The main difference is that Rust
// permits concurrent atomic and non-atomic reads to the same memory as those cause no issue in the
// C++ memory model, they are just forbidden in C++ because memory is partitioned into "atomic
// objects" and "non-atomic objects" (with `atomicRef` temporarily converting a non-atomic object
// into an atomic object).
//
// The most important aspect of this model is that *data races* are undefined behavior. A data race
// is defined as conflicting non-synchronized accesses where at least one of the accesses is
// non-atomic. Here, accesses are *conflicting* if they affect overlapping regions of memory and at
// least one of them is a write. (A `compareExchange` or `compareExchangeWeak` that does not
// succeed is not considered a write.) They are *non-synchronized* if neither of them
// *happens-before* the other, according to the happens-before order of the memory model.
//
// The other possible cause of undefined behavior in the memory model are mixed-size accesses: Rust
// inherits the C++ limitation that non-synchronized conflicting atomic accesses may not partially
// overlap. In other words, every pair of non-synchronized atomic accesses must be either disjoint,
// access the exact same memory (including using the same access size), or both be reads.
//
// Each atomic access takes an [Ordering] which defines how the operation interacts with the
// happens-before order. These orderings behave the same as the corresponding [C++20 atomic
// orderings][cppMemoryOrder]. For more information, see the [nomicon].
//
// [cpp]: https://en.cppreference.com/w/cpp/atomic
// [cpp-intro.races]: https://timsong-cpp.github.io/cppwp/n4868/intro.multithread#intro.races
// [cppMemoryOrder]: https://en.cppreference.com/w/cpp/atomic/memoryOrder
// [nomicon]: ../../../nomicon/atomics.html
//
// ```kotlin
// import std.mem.reinterpret
// import std.sync.atomic.AtomicU16
// import std.sync.atomic.AtomicU8
// import std.sync.atomic.Ordering
// import std.thread
//
// val atomic = AtomicU16.new(0)
// thread.scope({ s ->
//     // This is UB: conflicting non-synchronized accesses, at least one of which is non-atomic.
//     s.spawn({ atomic.store(1, Ordering.Relaxed) })
//     // atomic store
//     s.spawn({ atomic.asPtr().write(2) })
// })
// thread.scope({ s ->
//     // This is fine: the accesses do not conflict (as none of them performs any modification).
//     // In C++ this would be disallowed since creating an `atomicRef` precludes
//     // further non-atomic accesses, but Rust does not have that limitation.
//     s.spawn({ atomic.load(Ordering.Relaxed) })
//     // atomic load
//     s.spawn({ atomic.asPtr().read() })
// })
// thread.scope({ s ->
//     // This is fine: `join` synchronizes the code in a way such that the atomic
//     // store happens-before the non-atomic write.
//     val handle = s.spawn({ atomic.store(1, Ordering.Relaxed) })
//     // atomic store
//     handle.join().expect("thread won't panic")
//     // synchronize
//     s.spawn({ atomic.asPtr().write(2) })
// })
// thread.scope({ s ->
//     // This is UB: non-synchronized conflicting differently-sized atomic accesses.
//     s.spawn({ atomic.store(1, Ordering.Relaxed) })
//     s.spawn({ run {
//         val differentlySized = reinterpret(atomic)
//         differentlySized.store(2, Ordering.Relaxed)
//     } })
// })
// thread.scope({ s ->
//     // This is fine: `join` synchronizes the code in a way such that
//     // the 1-byte store happens-before the 2-byte store.
//     val handle = s.spawn({ atomic.store(1, Ordering.Relaxed) })
//     handle.join().expect("thread won't panic")
//     s.spawn({ run {
//         val differentlySized = reinterpret(atomic)
//         differentlySized.store(2, Ordering.Relaxed)
//     } })
// })
// ```
//
// # Portability
//
// All atomic types in this module are guaranteed to be [lock-free] if they're
// available. This means they don't internally acquire a global mutex. Atomic
// types and operations are not guaranteed to be wait-free. This means that
// operations like `fetchOr` may be implemented with a compare-and-swap loop.
//
// Atomic operations may be implemented at the instruction layer with
// larger-size atomics. For example some platforms use 4-byte atomic
// instructions to implement `AtomicI8`. Note that this emulation should not
// have an impact on correctness of code, it's just something to be aware of.
//
// The atomic types in this module might not be available on all platforms. The
// atomic types here are all widely available, however, and can generally be
// relied upon existing. Some notable exceptions are:
//
// * PowerPC and MIPS platforms with 32-bit pointers do not have `AtomicU64` or
//   `AtomicI64` types.
// * Legacy ARM platforms like ARMv4T and ARMv5TE have very limited hardware
//   support for atomics. The bare-metal targets disable this module
//   entirely, but the Linux targets [use the kernel] to assist (which comes
//   with a performance penalty). It's not until ARMv6K onwards that ARM CPUs
//   have support for load/store and Compare and Swap (CAS) atomics in hardware.
// * ARMv6-M targets (`thumbv6m - *`) only provide `load` and `store` operations, and do
//   not support Compare and Swap (CAS) operations, such as `swap`,
//   `fetchAdd`, etc. Full CAS support is available on ARMv7-M and ARMv8-M
//   (`thumbv7m - *`, `thumbv7em *`, `thumbv8m.base - *` and `thumbv8m.main - *`).
//
// [use the kernel]: https://www.kernel.org/doc/Documentation/arm/kernelUserHelpers.txt
//
// Note that future platforms may be added that also do not have support for
// some atomic operations. Maximally portable code will want to be careful
// about which atomic types are used. `AtomicUsize` and `AtomicIsize` are
// generally the most portable, but even then they're not available everywhere.
// For reference, the `std` library requires `AtomicBool`s and pointer-sized atomics, although
// `core` does not.
//
// The `@cfg(targetHasAtomic)` attribute can be used to conditionally
// compile based on the target's supported bit widths. It is a key-value
// option set for each supported size, with values "8", "16", "32", "64",
// "128", and "ptr" for pointer-sized atomics.
//
// [lock-free]: https://en.wikipedia.org/wiki/Non-blockingAlgorithm
//
// # Atomic accesses to read-only memory
//
// In general, *all* atomic accesses on read-only memory are undefined behavior. For instance, attempting
// to do a `compareExchange` that will definitely fail (making it conceptually a read-only
// operation) can still cause a segmentation fault if the underlying memory page is mapped read-only. Since
// atomic `load`s might be implemented using compare-exchange operations, even a `load` can fault
// on read-only memory.
//
// For the purpose of this section, "read-only memory" is defined as memory that is read-only in
// the underlying target, i.e., the pages are mapped with a read-only flag and any attempt to write
// will cause a page fault. In particular, an `ULong` reference that points to memory that is
// read-write mapped is *not* considered to point to "read-only memory". In Rust, almost all memory
// is read-write; the only exceptions are memory created by `const` items or `static` items without
// interior mutability, and memory that was specifically marked as read-only by the operating
// system via platform-specific APIs.
//
// As an exception from the general rule stated above, "sufficiently small" atomic loads with
// `Ordering.Relaxed` are implemented in a way that works on read-only memory, and are hence not
// undefined behavior. The exact size limit for what makes a load "sufficiently small" varies
// depending on the target:
//
// | `targetArch` | Size limit |
// |---------------|---------|
// | `x86`, `arm`, `loongarch32`, `mips`, `mips32r6`, `powerpc`, `riscv32`, `sparc`, `hexagon` | 4 bytes |
// | `x8664`, `aarch64`, `loongarch64`, `mips64`, `mips64r6`, `powerpc64`, `riscv64`, `sparc64`, `s390x` | 8 bytes |
//
// Atomics loads that are larger than this limit as well as atomic loads with ordering other
// than `Relaxed`, as well as *all* atomic loads on targets not listed in the table, might still be
// read-only under certain conditions, but that is not a stable guarantee and should not be relied
// upon.
//
// If you need to do an acquire load on read-only memory, you can do a relaxed load followed by an
// acquire fence instead.
//
// # Examples
//
// A simple spinlock:
//
// ```ignore-wasm
// use std.sync.Arc;
// use std.sync.atomic::{AtomicUsize, Ordering};
// use std::{hint, thread};
//
// fn main() {
//     let spinlock = Arc.new(AtomicUsize.new(1));
//
//     let spinlockClone = Arc.clone(&spinlock);
//
//     let thread = thread.spawn(move || {
//         spinlockClone.store(0, Ordering.Release);
//     });
//
//     // Wait for the other thread to release the lock
//     while spinlock.load(Ordering.Acquire) != 0 {
//         hint.spinLoop();
//     }
//
//     if let Err(panic) = thread.join() {
//         println("Thread had an error: {panic:?}");
//     }
// }
// ```
//
// Keep a global count of live threads:
//
// ```kotlin
// import std.sync.atomic.AtomicUsize
// import std.sync.atomic.Ordering
//
// var GLOBAL_THREAD_COUNT: AtomicUsize = AtomicUsize.new(0)
// // Note that Relaxed ordering doesn't synchronize anything
// // except the global thread counter itself.
// val oldThreadCount = GLOBAL_THREAD_COUNT.fetchAdd(1, Ordering.Relaxed)
// // Note that this number may not be true at the moment of printing
// // because some other thread may have changed static value already.
// println("live threads: ${oldThreadCount + 1}")
// ```

public open class Align1<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 1L)), AlignedTypeLayout(fieldLayout, 1L))

    public fun pointerLayout(): TypeLayout<Align1<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align1<T>(ptr, layout) }, { it.value })
}

public open class Align2<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 2L)), AlignedTypeLayout(fieldLayout, 2L))

    public fun pointerLayout(): TypeLayout<Align2<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align2<T>(ptr, layout) }, { it.value })
}

public open class Align4<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))

    public fun pointerLayout(): TypeLayout<Align4<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align4<T>(ptr, layout) }, { it.value })
}

public open class Align8<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 8L)), AlignedTypeLayout(fieldLayout, 8L))

    public fun pointerLayout(): TypeLayout<Align8<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align8<T>(ptr, layout) }, { it.value })
}

public open class Align16<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<Align16<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align16<T>(ptr, layout) }, { it.value })
}

//
// # Safety
//
// Types implementing this trait must be primitives that can be modified atomically.
//
// The associated `Self.Storage` type must have the same size, but may have fewer validity
// invariants or a higher alignment requirement than `Self`.

/**
 * A marker trait for primitive types which can be modified atomically.
 *
 * This is an implementation detail for <code>[Atomic]\<T></code> which may disappear or be replaced at any time.
 */
public interface AtomicPrimitive<out Storage>

/**
 * A memory location which can be safely modified from multiple threads.
 *
 * This has the same size and bit validity as the underlying type `T`. However,
 * the alignment of this type is always equal to its size, even on targets where
 * `T` has alignment less than its size.
 *
 * For more about the differences between atomic types and non-atomic types as
 * well as information about the portability of this type, please see the
 * [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support atomic loads
 * and stores of `T`.
 */
public open class Atomic<T, Storage> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UnsafeCell<Storage>> = UnresolvedTypeLayout(),
) {
    public var v: UnsafeCell<Storage>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(v: UnsafeCell<Storage>, fieldLayout: TypeLayout<UnsafeCell<Storage>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Atomic<T, Storage>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Atomic<T, Storage>(ptr, layout) }, { it.v })

    public companion object {
        public operator fun <T, Storage> invoke(v: UnsafeCell<Storage>, fieldLayout: TypeLayout<UnsafeCell<Storage>> = UnresolvedTypeLayout()): Atomic<T, Storage> = Atomic<T, Storage>(v, fieldLayout)

        public fun <T, Storage> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Storage>> = UnresolvedTypeLayout()): Atomic<T, Storage> = Atomic<T, Storage>(fieldStorage, fieldLayout)
    }
}

// Some architectures don't have byte-sized atomics, which results in LLVM
// emulating them using a LL/SC loop. However for AtomicBool we can take
// advantage of the fact that it only ever contains 0 or 1 and use atomic OR/AND
// instead, which LLVM can emulate using a larger atomic OR/AND operation.
//
// This list should only contain architectures which have word-sized atomic-or/
// atomic-and instructions but don't natively support byte-sized atomics.
public val EMULATE_ATOMIC_BOOL: Boolean = false

/**
 * A boolean type which can be safely shared between threads.
 *
 * This type has the same size, alignment, and bit validity as a [bool].
 *
 * **Note**: This type is only available on platforms that support atomic
 * loads and stores of `UByte`.
 */
public class AtomicBool(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align1<UByte>>> = UnresolvedTypeLayout()) : Atomic<Boolean, Align1<UByte>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align1<UByte>>, fieldLayout: TypeLayout<UnsafeCell<Align1<UByte>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

/**
 * Creates an `AtomicBool` initialized to `false`.
 */
public fun defaultAtomicBool(): AtomicBool = AtomicBool.new(false)

/**
 * A raw pointer type which can be safely shared between threads.
 *
 * This type has the same size and bit validity as a `* T`.
 *
 * **Note**: This type is only available on platforms that support atomic
 * loads and stores of pointers. Its size depends on the target pointer's size.
 */
public class AtomicPtr(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align8<MutPtr>>> = UnresolvedTypeLayout()) : Atomic<MutPtr, Align8<MutPtr>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align8<MutPtr>>, fieldLayout: TypeLayout<UnsafeCell<Align8<MutPtr>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

/**
 * Creates a null `AtomicPtr<T>`.
 */
public fun defaultAtomicPtrT(): AtomicPtr = AtomicPtr.new(MutPtr(0L))

/**
 * Atomic memory orderings
 *
 * Memory orderings specify the way atomic operations synchronize memory.
 * In its weakest [Ordering.Relaxed], only the memory directly touched by the
 * operation is synchronized. On the other hand, a store-load pair of [Ordering.SeqCst]
 * operations synchronize other memory while additionally preserving a total order of such
 * operations across all threads.
 *
 * Rust's memory orderings are [the same as those of
 * C++20](https://en.cppreference.com/w/cpp/atomic/memoryOrder).
 *
 * For more information see the [nomicon].
 *
 * [nomicon]: ../../../nomicon/atomics.html
 */
public sealed class Ordering {
    /**
     * No ordering constraints, only atomic operations.
     *
     * Corresponds to [memoryOrderRelaxed] in C++20.
     *
     * [memoryOrderRelaxed]: https://en.cppreference.com/w/cpp/atomic/memoryOrder#RelaxedOrdering
     */
    public data object Relaxed : Ordering()
    /**
     * When coupled with a store, all previous operations become ordered
     * before any load of this value with [Acquire] (or stronger) ordering.
     * In particular, all previous writes become visible to all threads
     * that perform an [Acquire] (or stronger) load of this value.
     *
     * Notice that using this ordering for an operation that combines loads
     * and stores leads to a [Relaxed] load operation!
     *
     * This ordering is only applicable for operations that can perform a store.
     *
     * Corresponds to [memoryOrderRelease] in C++20.
     *
     * [memoryOrderRelease]: https://en.cppreference.com/w/cpp/atomic/memoryOrder#Release-AcquireOrdering
     */
    public data object Release : Ordering()
    /**
     * When coupled with a load, if the loaded value was written by a store operation with
     * [Release] (or stronger) ordering, then all subsequent operations
     * become ordered after that store. In particular, all subsequent loads will see data
     * written before the store.
     *
     * Notice that using this ordering for an operation that combines loads
     * and stores leads to a [Relaxed] store operation!
     *
     * This ordering is only applicable for operations that can perform a load.
     *
     * Corresponds to [memoryOrderAcquire] in C++20.
     *
     * [memoryOrderAcquire]: https://en.cppreference.com/w/cpp/atomic/memoryOrder#Release-AcquireOrdering
     */
    public data object Acquire : Ordering()
    /**
     * Has the effects of both [Acquire] and [Release] together:
     * For loads it uses [Acquire] ordering. For stores it uses the [Release] ordering.
     *
     * Notice that in the case of `compareAndSwap`, it is possible that the operation ends up
     * not performing any store and hence it has just [Acquire] ordering. However,
     * `AcqRel` will never perform [Relaxed] accesses.
     *
     * This ordering is only applicable for operations that combine both loads and stores.
     *
     * Corresponds to [memoryOrderAcqRel] in C++20.
     *
     * [memoryOrderAcqRel]: https://en.cppreference.com/w/cpp/atomic/memoryOrder#Release-AcquireOrdering
     */
    public data object AcqRel : Ordering()
    /**
     * Like [Acquire]/[Release]/[AcqRel] (for load, store, and load-with-store
     * operations, respectively) with the additional guarantee that all threads see all
     * sequentially consistent operations in the same order.
     *
     * Corresponds to [memoryOrderSeqCst] in C++20.
     *
     * [memoryOrderSeqCst]: https://en.cppreference.com/w/cpp/atomic/memoryOrder#Sequentially-consistentOrdering
     */
    public data object SeqCst : Ordering()

    public fun rustEquals(other: Ordering): Boolean = this == other
}

/**
 * An [AtomicBool] initialized to `false`.
 */
public val ATOMIC_BOOL_INIT: AtomicBool = AtomicBool.new(false)

/**
 * Creates a new `AtomicBool`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 *
 * val atomicTrue = AtomicBool.new(true)
 * val atomicFalse = AtomicBool.new(false)
 * ```
 */
public fun AtomicBool.Companion.new(v: Boolean): AtomicBool {
    // SAFETY:
    // `Atomic<T>` is essentially a transparent wrapper around `T`.
    return transmuteWithLayouts(v, PrimitiveLayouts.BOOL, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> Align1<UByte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<UByte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicBool(ptr, layout) }, { it.v }))
}

/**
 * Creates a new `AtomicBool` from a pointer.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.atomic.AtomicBool
 *
 * // Get a pointer to an allocated value
 * val ptr: MutPtr = Holder.intoRaw(false)
 * check(MutPtr((ptr).rawAddress).isAligned())
 * run {
 *     // Create an atomic view of the allocated value
 *     val atomic = AtomicBool.fromPtr(ptr)
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(true, atomic.Ordering.Relaxed)
 * }
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * check((PrimitiveLayouts.BOOL).readNative((ptr).rawAddress) == true)
 * // Deallocate the value
 * (Holder.fromRaw(ptr)).let { }
 * ```
 *
 * # Safety
 *
 * * `ptr` must be aligned to `alignOf<AtomicBool>()` (note that this is always true, since
 *   `alignOf<AtomicBool>() == 1`).
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the [Memory model for atomic accesses]. In particular, it is not
 *   allowed to mix conflicting atomic and non-atomic accesses, or atomic accesses of different
 *   sizes, without synchronization.
 *
 * [Memory model for atomic accesses]: self#memory-model-for-atomic-accesses
 */
public fun AtomicBool.Companion.fromPtr(ptr: MutPtr): AtomicBool {
    // SAFETY: guaranteed by the caller
    return MutPtr((ptr).rawAddress)
}

/**
 * Creates a new pointer to `AtomicBool` from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicBool.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying [bool].
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * var someBool = AtomicBool.new(true)
 * check(someBool.getMut() == true)
 * someBool.getMut() = false
 * check(someBool.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.getMut(): RustMutableReference<Boolean> {
    // SAFETY: the mutable reference guarantees unique ownership.
    return RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.BOOL)
}

/**
 * Gets atomic access to a `Boolean`.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val localStorageL5C0 = AddressableLocal(true, PrimitiveLayouts.BOOL)
 * var someBool by localStorageL5C0
 * val a = AtomicBool.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
 * a.store(false, Ordering.Relaxed)
 * check(someBool == false)
 * ```
 */
public fun AtomicBool.Companion.fromMut(v: RustMutableReference<Boolean>): AtomicBool {
    // SAFETY: the mutable reference guarantees unique ownership, and
    // alignment of both `Boolean` and `Self` is 1.
    return RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> Align1<UByte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<UByte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicBool(ptr, layout) }, { it.v }))
}

/**
 * Gets non-atomic access to a `[AtomicBool]` slice.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```ignore-wasm
 * use std.sync.atomic::{AtomicBool, Ordering};
 *
 * let mut someBools = [const { AtomicBool.new(false) }; 10];
 *
 * let view: [bool] = AtomicBool.getMutSlice(someBools);
 * assertEq(view, [false; 10]);
 * view[..5].copyFromSlice(&[true; 5]);
 *
 * std.thread.scope(|s| {
 *     for t in &someBools[..5] {
 *         s.spawn(move || assertEq(t.load(Ordering.Relaxed), true));
 *     }
 *
 *     for f in &someBools[5..] {
 *         s.spawn(move || assertEq(f.load(Ordering.Relaxed), false));
 *     }
 * });
 * ```
 */
public fun AtomicBool.Companion.getMutSlice(thisRef: MutableList<AtomicBool>): MutableList<Boolean> {
    // SAFETY: the mutable reference guarantees unique ownership.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Boolean>>())
}

/**
 * Gets atomic access to a `[Boolean]` slice.
 *
 * # Examples
 *
 * ```rust,ignore-wasm
 * use std.sync.atomic::{AtomicBool, Ordering};
 *
 * let mut someBools = [false; 10];
 * let a = &*AtomicBool.fromMutSlice(someBools);
 * std.thread.scope(|s| {
 *     for i in 0..a.len() {
 *         s.spawn(move || a[i].store(true, Ordering.Relaxed));
 *     }
 * });
 * assertEq(someBools, [true; 10]);
 * ```
 */
public fun AtomicBool.Companion.fromMutSlice(v: MutableList<Boolean>): MutableList<AtomicBool> {
    // SAFETY: the mutable reference guarantees unique ownership, and
    // alignment of both `Boolean` and `Self` is 1.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicBool>>())
}

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 *
 * val someBool = AtomicBool.new(true)
 * check(someBool.intoInner() == true)
 * ```
 */
public fun AtomicBool.intoInner(): Boolean {
    // SAFETY:
    // * `Atomic<T>` is essentially a transparent wrapper around `T`.
    // * all operations on `Atomic<Boolean>` ensure that `T.Storage` remains
    // a valid `Boolean`.
    return transmute(this)
}

// even without panics, this helps for Miri backtraces

/**
 * Loads a value from the bool.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val someBool = AtomicBool.new(true)
 * check(someBool.load(Ordering.Relaxed) == true)
 * ```
 */
public fun AtomicBool.load(order: Ordering): Boolean {
    // SAFETY: any data races are prevented by atomic intrinsics and the raw
    // pointer passed in is valid because we got it from a reference.
    return atomicLoad((MutPtr((this.v.get()).rawAddress)).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.U8) != 0
}

// even without panics, this helps for Miri backtraces

/**
 * Perform a volatile atomic load from the bool.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * Reading from `self` must produce a properly initialized value of type `Boolean`.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicBool.loadVolatile(order: Ordering): Boolean {
    // SAFETY: follows from our own safety requirements.
    return atomicLoad(this.cast(), order, true) != 0
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the bool.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val someBool = AtomicBool.new(true)
 * someBool.store(false, Ordering.Relaxed)
 * check(someBool.load(Ordering.Relaxed) == false)
 * ```
 */
public fun AtomicBool.store(value: Boolean, order: Ordering) {
    // SAFETY: any data races are prevented by atomic intrinsics and the raw
    // pointer passed in is valid because we got it from a reference.
    run {
        atomicStore(MutPtr((this.v.get()).rawAddress), value.toUByte(), order, false, typeLayoutT = PrimitiveLayouts.U8)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Performs a volatile atomic store into the bool.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicBool.storeVolatile(value: Boolean, order: Ordering) {
    // SAFETY: follows from our own safety requirements.
    run {
        atomicStore(this.cast().castMut(), value.toUByte(), order, true)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the bool, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val someBool = AtomicBool.new(true)
 * check(someBool.swap(false, Ordering.Relaxed) == true)
 * check(someBool.load(Ordering.Relaxed) == false)
 * ```
 */
public fun AtomicBool.swap(value: Boolean, order: Ordering): Boolean =
    if (EMULATE_ATOMIC_BOOL) {
        if (value) {
            this.fetchOr(true, order)
        } else {
            this.fetchAnd(false, order)
        }
    } else {
        // SAFETY: data races are prevented by atomic intrinsics.
        atomicSwap(MutPtr((this.v.get()).rawAddress), value.toUByte(), order, typeLayoutT = PrimitiveLayouts.U8) != 0
    }

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the [bool] if the current value is the same as the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the value
 * was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val someBool = AtomicBool.new(true)
 * check(someBool.compareAndSwap(true, false, Ordering.Relaxed) == true)
 * check(someBool.load(Ordering.Relaxed) == false)
 * check(someBool.compareAndSwap(true, true, Ordering.Relaxed) == false)
 * check(someBool.load(Ordering.Relaxed) == false)
 * ```
 */
public fun AtomicBool.compareAndSwap(current: Boolean, new: Boolean, order: Ordering): Boolean =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the [bool] if the current value is the same as the `current` value.
 *
 * The return value is a result indicating whether the new value was written and containing
 * the previous value. On success this value is guaranteed to be equal to `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val someBool = AtomicBool.new(true)
 * check(someBool.compareExchange(true, false, Ordering.Acquire, Ordering.Relaxed) == Result.success(true))
 * check(someBool.load(Ordering.Relaxed) == false)
 * check(someBool.compareExchange(true, true, Ordering.SeqCst, Ordering.Acquire) == Result.failure(false))
 * check(someBool.load(Ordering.Relaxed) == false)
 * ```
 *
 * # Considerations
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations. In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim. This is usually important when the *equality* check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity. In this case, `compareExchange` can lead to the
 * [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 */
public fun AtomicBool.compareExchange(current: Boolean, new: Boolean, success: Ordering, failure: Ordering): Result<Boolean, Boolean> =
    if (EMULATE_ATOMIC_BOOL) {
        // Pick the strongest ordering from success and failure.
        val order =
            run {
                val matchVal = Pair(success, failure)
                when {
                    matchVal.first == SeqCst -> SeqCst
                    matchVal.second == SeqCst -> SeqCst
                    matchVal.first == AcqRel -> AcqRel
                    matchVal.second == AcqRel -> {
                        error("there is no such thing as an acquire-release failure ordering")
                    }
                    matchVal.first == Release && matchVal.second == Acquire -> AcqRel
                    matchVal.first == Acquire -> Acquire
                    matchVal.second == Acquire -> Acquire
                    matchVal.first == Release && matchVal.second == Relaxed -> Release
                    matchVal.second == Release -> error("there is no such thing as a release failure ordering")
                    else -> Relaxed
                }
            }
        val old =
            if (current == new) {
                // This is a no-op, but we still need to perform the operation
                // for memory ordering reasons.
                this.fetchOr(false, order)
            } else {
                // This sets the value to the new one and returns the old one.
                this.swap(new, order)
            }
        if (old == current) {
            Result.Ok<Boolean, Boolean>(old)
        } else {
            Result.Err<Boolean, Boolean>(old)
        }
    } else {
        // SAFETY: data races are prevented by atomic intrinsics.
        run {
            val matchVal1 = atomicCompareExchange(MutPtr((this.v.get()).rawAddress), current.toUByte(), new.toUByte(), success, failure, typeLayoutT = PrimitiveLayouts.U8)
            when (matchVal1) { is Result.Ok -> {
                val x = matchVal1.value
                Result.Ok<Boolean, Boolean>(x != 0)
            }; is Result.Err -> {
                val x1 = matchVal1.error
                Result.Err<Boolean, Boolean>(x1 != 0)
            } }
        }
    }

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the [bool] if the current value is the same as the `current` value.
 *
 * Unlike [AtomicBool.compareExchange], this function is allowed to spuriously fail even when the
 * comparison succeeds, which can result in more efficient code on some platforms. The
 * return value is a result indicating whether the new value was written and containing the
 * previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val value = AtomicBool.new(false)
 * val new = true
 * var old = value.load(Ordering.Relaxed)
 * while (true) {
 *     run {
 *         val matchVal = value.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed)
 *         if (matchVal.isSuccess) {
 *             break
 *         } else {
 *             val x = matchVal.exceptionOrNull()!!
 *             old = x
 *         }
 *     }
 * }
 * ```
 *
 * # Considerations
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations. In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim. This is usually important when the *equality* check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity. In this case, `compareExchange` can lead to the
 * [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 */
public fun AtomicBool.compareExchangeWeak(current: Boolean, new: Boolean, success: Ordering, failure: Ordering): Result<Boolean, Boolean> {
    if (EMULATE_ATOMIC_BOOL) {
        return this.compareExchange(current, new, success, failure)
    }
    // SAFETY: data races are prevented by atomic intrinsics.
    return run {
        val matchVal = atomicCompareExchangeWeak(MutPtr((this.v.get()).rawAddress), current.toUByte(), new.toUByte(), success, failure, typeLayoutT = PrimitiveLayouts.U8)
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            Result.Ok<Boolean, Boolean>(x != 0)
        }; is Result.Err -> {
            val x1 = matchVal.error
            Result.Err<Boolean, Boolean>(x1 != 0)
        } }
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Logical "and" with a boolean value.
 *
 * Performs a logical "and" operation on the current value and the argument `val`, and sets
 * the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val foo = AtomicBool.new(true)
 * check(foo.fetchAnd(false, Ordering.SeqCst) == true)
 * check(foo.load(Ordering.SeqCst) == false)
 * val foo1 = AtomicBool.new(true)
 * check(foo1.fetchAnd(true, Ordering.SeqCst) == true)
 * check(foo1.load(Ordering.SeqCst) == true)
 * val foo2 = AtomicBool.new(false)
 * check(foo2.fetchAnd(false, Ordering.SeqCst) == false)
 * check(foo2.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.fetchAnd(value: Boolean, order: Ordering): Boolean {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicAnd(MutPtr((this.v.get()).rawAddress), value.toUByte(), order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8) != 0
}

// even without panics, this helps for Miri backtraces

/**
 * Logical "nand" with a boolean value.
 *
 * Performs a logical "nand" operation on the current value and the argument `val`, and sets
 * the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val foo = AtomicBool.new(true)
 * check(foo.fetchNand(false, Ordering.SeqCst) == true)
 * check(foo.load(Ordering.SeqCst) == true)
 * val foo1 = AtomicBool.new(true)
 * check(foo1.fetchNand(true, Ordering.SeqCst) == true)
 * check(foo1.load(Ordering.SeqCst).toInt() == 0)
 * check(foo1.load(Ordering.SeqCst) == false)
 * val foo2 = AtomicBool.new(false)
 * check(foo2.fetchNand(false, Ordering.SeqCst) == false)
 * check(foo2.load(Ordering.SeqCst) == true)
 * ```
 */
public fun AtomicBool.fetchNand(value: Boolean, order: Ordering): Boolean {
    // We can't use atomicNand here because it can result in a bool with
    // an invalid value. This happens because the atomic operation is done
    // with an 8-bit integer internally, which would set the upper 7 bits.
    // So we just use fetchXor or swap instead.
    return if (value) {
        // !(x & true) == !x
        // We must invert the bool.
        this.fetchXor(true, order)
    } else {
        // !(x & false) == true
        // We must set the bool to true.
        this.swap(true, order)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Logical "or" with a boolean value.
 *
 * Performs a logical "or" operation on the current value and the argument `val`, and sets the
 * new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val foo = AtomicBool.new(true)
 * check(foo.fetchOr(false, Ordering.SeqCst) == true)
 * check(foo.load(Ordering.SeqCst) == true)
 * val foo1 = AtomicBool.new(false)
 * check(foo1.fetchOr(true, Ordering.SeqCst) == false)
 * check(foo1.load(Ordering.SeqCst) == true)
 * val foo2 = AtomicBool.new(false)
 * check(foo2.fetchOr(false, Ordering.SeqCst) == false)
 * check(foo2.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.fetchOr(value: Boolean, order: Ordering): Boolean {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicOr(MutPtr((this.v.get()).rawAddress), value.toUByte(), order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8) != 0
}

// even without panics, this helps for Miri backtraces

/**
 * Logical "xor" with a boolean value.
 *
 * Performs a logical "xor" operation on the current value and the argument `val`, and sets
 * the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val foo = AtomicBool.new(true)
 * check(foo.fetchXor(false, Ordering.SeqCst) == true)
 * check(foo.load(Ordering.SeqCst) == true)
 * val foo1 = AtomicBool.new(true)
 * check(foo1.fetchXor(true, Ordering.SeqCst) == true)
 * check(foo1.load(Ordering.SeqCst) == false)
 * val foo2 = AtomicBool.new(false)
 * check(foo2.fetchXor(false, Ordering.SeqCst) == false)
 * check(foo2.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.fetchXor(value: Boolean, order: Ordering): Boolean {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicXor(MutPtr((this.v.get()).rawAddress), value.toUByte(), order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8) != 0
}

// even without panics, this helps for Miri backtraces

/**
 * Logical "not" with a boolean value.
 *
 * Performs a logical "not" operation on the current value, and sets
 * the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNot` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val foo = AtomicBool.new(true)
 * check(foo.fetchNot(Ordering.SeqCst) == true)
 * check(foo.load(Ordering.SeqCst) == false)
 * val foo1 = AtomicBool.new(false)
 * check(foo1.fetchNot(Ordering.SeqCst) == false)
 * check(foo1.load(Ordering.SeqCst) == true)
 * ```
 */
public fun AtomicBool.fetchNot(order: Ordering): Boolean = this.fetchXor(true, order)

/**
 * Returns a mutable pointer to the underlying [bool].
 *
 * Doing non-atomic reads and writes on the resulting boolean can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 * `* Boolean` instead of `AtomicBool`.
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 * use std.sync.atomic.AtomicBool;
 *
 * extern "C" {
 *     fn myAtomicOp(arg: *mut bool);
 * }
 *
 * let mut atomic = AtomicBool.new(true);
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicBool.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

// even without panics, this helps for Miri backtraces

/**
 * An alias for [AtomicBool.tryUpdate].
 */
public fun AtomicBool.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Boolean) -> Boolean?): Result<Boolean, Boolean> = this.tryUpdate(setOrder, fetchOrder, f)

// even without panics, this helps for Miri backtraces

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function
 * returned `Some(_)`, else `Err(previousValue)`.
 *
 * See also: [`update`](`AtomicBool.update`).
 *
 * Note: This may call the function multiple times if the value has been
 * changed from other threads in the meantime, as long as the function
 * returns `Some(_)`, but the function will have been applied only once to
 * the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. The first describes the required ordering for
 * when the operation finally succeeds while the second describes the
 * required ordering for loads. These correspond to the success and failure
 * orderings of [AtomicBool.compareExchange] respectively.
 *
 * Using [Acquire] as success ordering makes the store part of this
 * operation [Relaxed], and using [Release] makes the final successful
 * load [Relaxed]. The (failed) load ordering can only be [SeqCst],
 * [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on `UByte`.
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val x = AtomicBool.new(false)
 * check(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, { null }) == Result.failure(false))
 * check(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, { x1 -> !x1 }) == Result.success(false))
 * check(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, { x1 -> !x1 }) == Result.success(true))
 * check(x.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Boolean) -> Boolean?): Result<Boolean, Boolean> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Boolean, Boolean>(prev)
}

// even without panics, this helps for Miri backtraces

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * See also: [`tryUpdate`](`AtomicBool.tryUpdate`).
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. The first describes the required ordering for
 * when the operation finally succeeds while the second describes the
 * required ordering for loads. These correspond to the success and failure
 * orderings of [AtomicBool.compareExchange] respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic operations on `UByte`.
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 *
 * val x = AtomicBool.new(false)
 * check(x.update(Ordering.SeqCst, Ordering.SeqCst, { x1 -> !x1 }) == false)
 * check(x.update(Ordering.SeqCst, Ordering.SeqCst, { x1 -> !x1 }) == true)
 * check(x.load(Ordering.SeqCst) == false)
 * ```
 */
public fun AtomicBool.update(setOrder: Ordering, fetchOrder: Ordering, f: (Boolean) -> Boolean): Boolean {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Creates a new `AtomicPtr`.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val atomicPtr = AtomicPtr.new(ptr)
 * ```
 */
public fun AtomicPtr.Companion.new(p: MutPtr): AtomicPtr {
    // SAFETY:
    // `Atomic<T>` is essentially a transparent wrapper around `T`.
    return transmuteWithLayouts(p, PrimitiveLayouts.mutPtrLayout(64L), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.mutPtrLayout(64L), { ptr, layout -> Align8<MutPtr>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<MutPtr>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicPtr(ptr, layout) }, { it.v }))
}

/**
 * Creates a new `AtomicPtr` from a pointer.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.atomic.AtomicPtr
 *
 * // Get a pointer to an allocated value
 * val ptr: MutPtr = Holder.intoRaw(MutPtr(0L))
 * check(MutPtr((ptr).rawAddress).isAligned())
 * run {
 *     // Create an atomic view of the allocated value
 *     val atomic = AtomicPtr.fromPtr(ptr)
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(NonNull.dangling().asPtr(), atomic.Ordering.Relaxed)
 * }
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * check(!(((PrimitiveLayouts.mutPtrLayout(64L)).readNative((ptr).rawAddress)).rawAddress == 0L))
 * // Deallocate the value
 * (Holder.fromRaw(ptr)).let { }
 * ```
 *
 * # Safety
 *
 * * `ptr` must be aligned to `alignOf<AtomicPtr<T>>()` (note that on some platforms this
 *   can be bigger than `alignOf< * T>()`).
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the [Memory model for atomic accesses]. In particular, it is not
 *   allowed to mix conflicting atomic and non-atomic accesses, or atomic accesses of different
 *   sizes, without synchronization.
 *
 * [Memory model for atomic accesses]: self#memory-model-for-atomic-accesses
 */
public fun AtomicPtr.Companion.fromPtr(ptr: MutPtr): AtomicPtr {
    // SAFETY: guaranteed by the caller
    return MutPtr((ptr).rawAddress)
}

/**
 * Creates a new pointer to `AtomicPtr` from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicPtr.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Creates a new `AtomicPtr` initialized with a null pointer.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * @feature(atomicPtrNull)
 * public fun doctestEntry() {
 *     val atomicPtr = ConstPtr(0L)
 *     check(atomicPtr.load(Ordering.Relaxed).isNull())
 * }
 * ```
 */
public fun AtomicPtr.Companion.nullVal(): AtomicPtr = AtomicPtr.new(MutPtr(0L))

/**
 * Returns a mutable reference to the underlying pointer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val localStorageL5C0 = AddressableLocal(10, PrimitiveLayouts.I32)
 * var atomicPtr = AtomicPtr.new(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
 * val localStorageL7C0 = AddressableLocal(5, PrimitiveLayouts.I32)
 * atomicPtr.getMut() = RustMutableReference(localStorageL7C0.asMutPtr(), localStorageL7C0.layout)
 * check(atomicPtr.load(Ordering.SeqCst) == 5)
 * ```
 */
public fun AtomicPtr.getMut(): RustMutableReference<MutPtr> {
    // SAFETY:
    // `Atomic<T>` is essentially a transparent wrapper around `T`.
    return RustMutableReference(MutPtr((this.asPtr()).rawAddress), this.v.value.fieldLayout)
}

/**
 * Gets atomic access to a pointer.
 *
 * **Note:** This function is only available on targets where `AtomicPtr<T>` has the same alignment as `* const T`
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val localStorageL5C0 = AddressableLocal(123, PrimitiveLayouts.I32)
 * val localStorageL6C0 = AddressableLocal(MutPtr(localStorageL5C0.asMutPtr().rawAddress), PrimitiveLayouts.mutPtrLayout(64L))
 * var somePtr by localStorageL6C0
 * val a = AtomicPtr.fromMut(RustMutableReference(localStorageL6C0.asMutPtr(), localStorageL6C0.layout))
 * val localStorageL8C0 = AddressableLocal(456, PrimitiveLayouts.I32)
 * a.store(RustMutableReference(localStorageL8C0.asMutPtr(), localStorageL8C0.layout), Ordering.Relaxed)
 * check((PrimitiveLayouts.I32).readNative((somePtr).rawAddress) == 456)
 * ```
 */
public fun AtomicPtr.Companion.fromMut(v: RustMutableReference<MutPtr>): AtomicPtr {
    // SAFETY:
    // - the mutable reference guarantees unique ownership.
    // - the alignment of `* T` and `Self` is the same on all platforms
    // supported by rust, as verified above.
    return RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.mutPtrLayout(64L), { ptr, layout -> Align8<MutPtr>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<MutPtr>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicPtr(ptr, layout) }, { it.v }))
}

/**
 * Gets non-atomic access to a `[AtomicPtr]` slice.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```ignore-wasm
 * use std.ptr.nullMut;
 * use std.sync.atomic::{AtomicPtr, Ordering};
 *
 * let mut somePtrs = [const { AtomicPtr.new(nullMut::<String>()) }; 10];
 *
 * let view: [*mut String] = AtomicPtr.getMutSlice(somePtrs);
 * assertEq(view, [nullMut::<String>(); 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(i, ptr)| *ptr = Box.intoRaw(Box.new(format("iteration#{i}"))));
 *
 * std.thread.scope(|s| {
 *     for ptr in &somePtrs {
 *         s.spawn(move || {
 *             let ptr = ptr.load(Ordering.Relaxed);
 *             assert(!ptr.isNull());
 *
 *             let name = unchecked { Box.fromRaw(ptr) };
 *             println("Hello, {name}!");
 *         });
 *     }
 * });
 * ```
 */
public fun <T> AtomicPtr.Companion.getMutSlice(thisRef: MutableList<AtomicPtr>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutableList<MutPtr> {
    // SAFETY: the mutable reference guarantees unique ownership.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<MutPtr>>())
}

/**
 * Gets atomic access to a slice of pointers.
 *
 * **Note:** This function is only available on targets where `AtomicPtr<T>` has the same alignment as `* const T`
 *
 * # Examples
 *
 * ```ignore-wasm
 * use std.ptr.nullMut;
 * use std.sync.atomic::{AtomicPtr, Ordering};
 *
 * let mut somePtrs = [nullMut::<String>(); 10];
 * let a = &*AtomicPtr.fromMutSlice(somePtrs);
 * std.thread.scope(|s| {
 *     for i in 0..a.len() {
 *         s.spawn(move || {
 *             let name = Box.new(format("thread{i}"));
 *             a[i].store(Box.intoRaw(name), Ordering.Relaxed);
 *         });
 *     }
 * });
 * for p in somePtrs {
 *     assert(!p.isNull());
 *     let name = unchecked { Box.fromRaw(p) };
 *     println("Hello, {name}!");
 * }
 * ```
 */
public fun AtomicPtr.Companion.fromMutSlice(v: MutableList<MutPtr>): MutableList<AtomicPtr> {
    // SAFETY:
    // - the mutable reference guarantees unique ownership.
    // - the alignment of `* T` and `Self` is the same on all platforms
    // supported by rust, as verified above.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicPtr>>())
}

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.sync.atomic.AtomicPtr
 *
 * val localStorageL5C0 = AddressableLocal(5, PrimitiveLayouts.I32)
 * val atomicPtr = AtomicPtr.new(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
 * check(atomicPtr.intoInner() == 5)
 * ```
 */
public fun AtomicPtr.intoInner(): MutPtr {
    // SAFETY:
    // `Atomic<T>` is essentially a transparent wrapper around `T`.
    return transmute(this)
}

// even without panics, this helps for Miri backtraces

/**
 * Loads a value from the pointer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val value = somePtr.load(Ordering.Relaxed)
 * ```
 */
public fun AtomicPtr.load(order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = this.v.value.fieldLayout)
}

// even without panics, this helps for Miri backtraces

/**
 * Perform a volatile atomic load from the pointer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to `alignOf<AtomicPtr<T>>()` (note that on some platforms this
 *   can be bigger than `alignOf< * T>()`).
 *
 * * Reading from `self` must produce a properly initialized value of type `* T`.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * Assuming an MMIO region at `MMIO_ADDR` that belongs to a device with direct memory
 * access, we may receive a buffer in shared memory from that device as follows:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 * import std.sync.atomic.fence
 *
 * @feature(atomicVolatile)
 * public fun doctestEntry() {
 *     val MMIO_ADDR: MutPtr = ptr.withoutProvenanceMut(0xCAF0)
 *     val atomicPtr = AtomicPtr.fromPtrRaw(MMIO_ADDR)
 *     // Spin until we see a non-zero value.
 *     val buf1 = while (true) {
 *         val buf = atomicPtr.loadVolatile(Ordering.Relaxed)
 *         if (!buf.isNull()) {
 *             break
 *         }
 *     }
 *     // Synchronize with the store whose value we just read.
 *     // Note: a standard acquire fence may not be sufficient to synchronize with DMA devices.
 *     // Depending on your target, you may have to use inline assembly to emit a special fence.
 *     fence(Ordering.Acquire)
 *     // Now process the data in `buf`.
 * }
 * ```
 */
public fun AtomicPtr.loadVolatile(order: Ordering): MutPtr {
    // SAFETY: follows from our own safety requirements.
    return atomicLoad(this.cast(), order, true)
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the pointer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val otherPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * somePtr.store(otherPtr, Ordering.Relaxed)
 * ```
 */
public fun AtomicPtr.store(ptr: MutPtr, order: Ordering) {
    // SAFETY: data races are prevented by atomic intrinsics.
    run {
        atomicStore(this.asPtr(), ptr, order, false, typeLayoutT = this.v.value.fieldLayout)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Performs a volatile atomic store into the pointer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to `alignOf<AtomicPtr<T>>()` (note that on some platforms this
 *   can be bigger than `alignOf< * T>()`).
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * Assuming an MMIO region at `MMIO_ADDR` that belongs to a device with direct memory
 * access, we may submit a buffer in shared memory to that device as follows:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 * import std.sync.atomic.fence
 *
 * @feature(atomicVolatile)
 * public fun doctestEntry() {
 *     val MMIO_ADDR: MutPtr = ptr.withoutProvenanceMut(0xCAF0)
 *     val atomicPtr = AtomicPtr.fromPtrRaw(MMIO_ADDR)
 *     // Prepare some data for the DMA device.
 *     val buf = getDmaBuffer()
 *     // Ensure the other side can synchronize with the store we do below.
 *     // Note: a standard release fence may not be sufficient to synchronize with DMA devices.
 *     // Depending on your target, you may have to use inline assembly to emit a special fence.
 *     fence(Ordering.Release)
 *     atomicPtr.storeVolatile(buf, Ordering.Relaxed)
 * }
 * ```
 */
public fun AtomicPtr.storeVolatile(ptr: MutPtr, order: Ordering) {
    // SAFETY: follows from our own safety requirements.
    run {
        atomicStore(this.cast().castMut(), ptr, order, true)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the pointer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val otherPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val value = somePtr.swap(otherPtr, Ordering.Relaxed)
 * ```
 */
public fun AtomicPtr.swap(ptr: MutPtr, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicSwap(this.asPtr(), ptr, order, typeLayoutT = this.v.value.fieldLayout)
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the pointer if the current value is the same as the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the value
 * was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val otherPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val value = somePtr.compareAndSwap(ptr, otherPtr, Ordering.Relaxed)
 * ```
 */
public fun AtomicPtr.compareAndSwap(current: MutPtr, new: MutPtr, order: Ordering): MutPtr =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the pointer if the current value is the same as the `current` value.
 *
 * The return value is a result indicating whether the new value was written and containing
 * the previous value. On success this value is guaranteed to be equal to `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val otherPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val value = somePtr.compareExchange(ptr, otherPtr, Ordering.SeqCst, Ordering.Relaxed)
 * ```
 *
 * # Considerations
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations. In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim. This is usually important when the *equality* check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity. This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 */
public fun AtomicPtr.compareExchange(current: MutPtr, new: MutPtr, success: Ordering, failure: Ordering): Result<MutPtr, MutPtr> {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = this.v.value.fieldLayout)
}

// even without panics, this helps for Miri backtraces

/**
 * Stores a value into the pointer if the current value is the same as the `current` value.
 *
 * Unlike [AtomicPtr.compareExchange], this function is allowed to spuriously fail even when the
 * comparison succeeds, which can result in more efficient code on some platforms. The
 * return value is a result indicating whether the new value was written and containing the
 * previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val somePtr = AtomicPtr.new(RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
 * val new = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * var old = somePtr.load(Ordering.Relaxed)
 * while (true) {
 *     run {
 *         val matchVal = somePtr.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed)
 *         if (matchVal.isSuccess) {
 *             break
 *         } else {
 *             val x = matchVal.exceptionOrNull()!!
 *             old = x
 *         }
 *     }
 * }
 * ```
 *
 * # Considerations
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations. In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim. This is usually important when the *equality* check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity. This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 */
public fun AtomicPtr.compareExchangeWeak(current: MutPtr, new: MutPtr, success: Ordering, failure: Ordering): Result<MutPtr, MutPtr> {
    // SAFETY: This intrinsic is unchecked because it operates on a raw pointer
    // but we know for sure that the pointer is valid (we just got it from
    // an `UnsafeCell` that we have by reference) and the atomic operation
    // itself allows us to safely mutate the `UnsafeCell` contents.
    return atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = this.v.value.fieldLayout)
}

// even without panics, this helps for Miri backtraces

/**
 * An alias for [AtomicPtr.tryUpdate].
 */
public fun AtomicPtr.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (MutPtr) -> MutPtr?): Result<MutPtr, MutPtr> = this.tryUpdate(setOrder, fetchOrder, f)

// even without panics, this helps for Miri backtraces

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function
 * returned `Some(_)`, else `Err(previousValue)`.
 *
 * See also: [`update`](`AtomicPtr.update`).
 *
 * Note: This may call the function multiple times if the value has been
 * changed from other threads in the meantime, as long as the function
 * returns `Some(_)`, but the function will have been applied only once to
 * the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. The first describes the required ordering for
 * when the operation finally succeeds while the second describes the
 * required ordering for loads. These correspond to the success and failure
 * orderings of [AtomicPtr.compareExchange] respectively.
 *
 * Using [Acquire] as success ordering makes the store part of this
 * operation [Relaxed], and using [Release] makes the final successful
 * load [Relaxed]. The (failed) load ordering can only be [SeqCst],
 * [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem],
 * which is a particularly common pitfall for pointers!
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr: MutPtr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val new: MutPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * check(somePtr.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, { null }) == Result.failure(ptr))
 * val result = somePtr.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, { x ->
 *     if (((x).rawAddress.toULong() == (ptr).rawAddress.toULong())) {
 *         new
 *     } else {
 *         null
 *     }
 * })
 * check(result == Result.success(ptr))
 * check(somePtr.load(Ordering.SeqCst) == new)
 * ```
 */
public fun AtomicPtr.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (MutPtr) -> MutPtr?): Result<MutPtr, MutPtr> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<MutPtr, MutPtr>(prev)
}

// even without panics, this helps for Miri backtraces

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * See also: [`tryUpdate`](`AtomicPtr.tryUpdate`).
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. The first describes the required ordering for
 * when the operation finally succeeds while the second describes the
 * required ordering for loads. These correspond to the success and failure
 * orderings of [AtomicPtr.compareExchange] respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note:** This method is only available on platforms that support atomic
 * operations on pointers.
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem],
 * which is a particularly common pitfall for pointers!
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.atomic.AtomicPtr
 * import std.sync.atomic.Ordering
 *
 * val ptr: MutPtr = RustMutableReference(allocateNativeCell(5, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val somePtr = AtomicPtr.new(ptr)
 * val new: MutPtr = RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)
 * val result = somePtr.update(Ordering.SeqCst, Ordering.SeqCst, { new })
 * check(result == ptr)
 * check(somePtr.load(Ordering.SeqCst) == new)
 * ```
 */
public fun AtomicPtr.update(setOrder: Ordering, fetchOrder: Ordering, f: (MutPtr) -> MutPtr): MutPtr {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Offsets the pointer's address by adding `val` (in units of `T`),
 * returning the previous pointer.
 *
 * This is equivalent to using [wrappingAdd] to atomically perform the
 * equivalent of `ptr = ptr.wrappingAdd(val);`.
 *
 * This method operates in units of `T`, which means that it cannot be used
 * to offset the pointer by an amount which is not a multiple of
 * `sizeOf<T>()`. This can sometimes be inconvenient, as you may want to
 * work with a deliberately misaligned pointer. In such cases, you may use
 * the [`fetchByteAdd`](Self.fetchByteAdd) method instead.
 *
 * `fetchPtrAdd` takes an [Ordering] argument which describes the
 * memory ordering of this operation. All ordering modes are possible. Note
 * that using [Acquire] makes the store part of this operation
 * [Relaxed], and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * [wrappingAdd]: pointer.wrappingAdd
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val atom = AtomicPtr.new(MutPtr(0L))
 * check(atom.fetchPtrAdd(1, Ordering.Relaxed).addr() == 0)
 * // Note: units of `sizeOf<Long>()`.
 * check(atom.load(Ordering.Relaxed).addr() == 8)
 * ```
 */
public fun <T> AtomicPtr.fetchPtrAdd(value: Int, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr = this.fetchByteAdd((value * sizeOf<T>(typeLayoutT = typeLayoutT).toInt()), order)

// even without panics, this helps for Miri backtraces

/**
 * Offsets the pointer's address by subtracting `val` (in units of `T`),
 * returning the previous pointer.
 *
 * This is equivalent to using [wrappingSub] to atomically perform the
 * equivalent of `ptr = ptr.wrappingSub(val);`.
 *
 * This method operates in units of `T`, which means that it cannot be used
 * to offset the pointer by an amount which is not a multiple of
 * `sizeOf<T>()`. This can sometimes be inconvenient, as you may want to
 * work with a deliberately misaligned pointer. In such cases, you may use
 * the [`fetchByteSub`](Self.fetchByteSub) method instead.
 *
 * `fetchPtrSub` takes an [Ordering] argument which describes the memory
 * ordering of this operation. All ordering modes are possible. Note that
 * using [Acquire] makes the store part of this operation [Relaxed],
 * and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * [wrappingSub]: pointer.wrappingSub
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2))
 * var array by localStorageL5C0
 * val atom = AtomicPtr.new((ConstPtr((localStorageL5C0.elementConstPtr(0)).rawAddress + ((1).toLong() * (PrimitiveLayouts.I32).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
 * check(eq(atom.fetchPtrSub(1, Ordering.Relaxed), array[1]))
 * check(eq(atom.load(Ordering.Relaxed), array[0]))
 * ```
 */
public fun <T> AtomicPtr.fetchPtrSub(value: Int, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr = this.fetchByteSub((value * sizeOf<T>(typeLayoutT = typeLayoutT).toInt()), order)

// even without panics, this helps for Miri backtraces

/**
 * Offsets the pointer's address by adding `val` *bytes*, returning the
 * previous pointer.
 *
 * This is equivalent to using [wrappingByteAdd] to atomically
 * perform `ptr = ptr.wrappingByteAdd(val)`.
 *
 * `fetchByteAdd` takes an [Ordering] argument which describes the
 * memory ordering of this operation. All ordering modes are possible. Note
 * that using [Acquire] makes the store part of this operation
 * [Relaxed], and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * [wrappingByteAdd]: pointer.wrappingByteAdd
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val atom = AtomicPtr.new(MutPtr(0L))
 * check(atom.fetchByteAdd(1, Ordering.Relaxed).addr() == 0)
 * // Note: in units of bytes, not `sizeOf<Long>()`.
 * check(atom.load(Ordering.Relaxed).addr() == 1)
 * ```
 */
public fun AtomicPtr.fetchByteAdd(value: Int, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicAdd(this.asPtr(), value, order, typeLayoutT = this.v.value.fieldLayout, typeLayoutU = PrimitiveLayouts.USIZE64).cast()
}

// even without panics, this helps for Miri backtraces

/**
 * Offsets the pointer's address by subtracting `val` *bytes*, returning the
 * previous pointer.
 *
 * This is equivalent to using [wrappingByteSub] to atomically
 * perform `ptr = ptr.wrappingByteSub(val)`.
 *
 * `fetchByteSub` takes an [Ordering] argument which describes the
 * memory ordering of this operation. All ordering modes are possible. Note
 * that using [Acquire] makes the store part of this operation
 * [Relaxed], and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * [wrappingByteSub]: pointer.wrappingByteSub
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 *
 * var arr = longArrayOf(0L, 1L)
 * val atom = AtomicPtr.new(addrOfMut(arr[1]))
 * check(atom.fetchByteSub(8, Ordering.Relaxed).addr() == ((addrOf(arr[1]))).rawAddress.toInt())
 * check(atom.load(Ordering.Relaxed).addr() == ((addrOf(arr[0]))).rawAddress.toInt())
 * ```
 */
public fun AtomicPtr.fetchByteSub(value: Int, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicSub(this.asPtr(), value, order, typeLayoutT = this.v.value.fieldLayout, typeLayoutU = PrimitiveLayouts.USIZE64).cast()
}

// even without panics, this helps for Miri backtraces

/**
 * Performs a bitwise "or" operation on the address of the current pointer,
 * and the argument `val`, and stores a pointer with provenance of the
 * current pointer and the resulting address.
 *
 * This is equivalent to using [mapAddr] to atomically perform
 * `ptr = ptr.mapAddr(| a | a | val)`. This can be used in tagged
 * pointer schemes to atomically set tag bits.
 *
 * **Caveat**: This operation returns the previous value. To compute the
 * stored value without losing provenance, you may use [mapAddr]. For
 * example: `a.fetchOr(val).mapAddr(| a | a | val)`.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory
 * ordering of this operation. All ordering modes are possible. Note that
 * using [Acquire] makes the store part of this operation [Relaxed],
 * and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * This API and its claimed semantics are part of the Strict Provenance
 * experiment, see the [module documentation for `ptr`][ptr] for
 * details.
 *
 * [mapAddr]: pointer.mapAddr
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val pointer = MutPtr((RustMutableReference(allocateNativeCell(3L, PrimitiveLayouts.I64), PrimitiveLayouts.I64)).asMutPtr().rawAddress)
 * val atom = AtomicPtr.new(pointer)
 * // Tag the bottom bit of the pointer.
 * check(atom.fetchOr(1, Ordering.Relaxed).addr() and 1 == 0)
 * // Extract and untag.
 * val tagged = atom.load(Ordering.Relaxed)
 * check(tagged.addr() and 1 == 1)
 * check(tagged.mapAddr({ p -> p and 1.inv() }) == pointer)
 * ```
 */
public fun AtomicPtr.fetchOr(value: Int, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicOr(this.asPtr(), value, order, typeLayoutT = this.v.value.fieldLayout, typeLayoutU = PrimitiveLayouts.USIZE64).cast()
}

// even without panics, this helps for Miri backtraces

/**
 * Performs a bitwise "and" operation on the address of the current
 * pointer, and the argument `val`, and stores a pointer with provenance of
 * the current pointer and the resulting address.
 *
 * This is equivalent to using [mapAddr] to atomically perform
 * `ptr = ptr.mapAddr(| a | a & val)`. This can be used in tagged
 * pointer schemes to atomically unset tag bits.
 *
 * **Caveat**: This operation returns the previous value. To compute the
 * stored value without losing provenance, you may use [mapAddr]. For
 * example: `a.fetchAnd(val).mapAddr(| a | a & val)`.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory
 * ordering of this operation. All ordering modes are possible. Note that
 * using [Acquire] makes the store part of this operation [Relaxed],
 * and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * This API and its claimed semantics are part of the Strict Provenance
 * experiment, see the [module documentation for `ptr`][ptr] for
 * details.
 *
 * [mapAddr]: pointer.mapAddr
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val pointer = MutPtr((RustMutableReference(allocateNativeCell(3L, PrimitiveLayouts.I64), PrimitiveLayouts.I64)).asMutPtr().rawAddress)
 * // A tagged pointer
 * val atom = AtomicPtr.new(pointer.mapAddr({ a -> a or 1 }))
 * check(atom.fetchOr(1, Ordering.Relaxed).addr() and 1 == 1)
 * // Untag, and extract the previously tagged pointer.
 * val untagged = atom.fetchAnd(1.inv(), Ordering.Relaxed).mapAddr({ a -> a and 1.inv() })
 * check(untagged == pointer)
 * ```
 */
public fun AtomicPtr.fetchAnd(value: Int, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicAnd(this.asPtr(), value, order, typeLayoutT = this.v.value.fieldLayout, typeLayoutU = PrimitiveLayouts.USIZE64).cast()
}

// even without panics, this helps for Miri backtraces

/**
 * Performs a bitwise "xor" operation on the address of the current
 * pointer, and the argument `val`, and stores a pointer with provenance of
 * the current pointer and the resulting address.
 *
 * This is equivalent to using [mapAddr] to atomically perform
 * `ptr = ptr.mapAddr(| a | a ^ val)`. This can be used in tagged
 * pointer schemes to atomically toggle tag bits.
 *
 * **Caveat**: This operation returns the previous value. To compute the
 * stored value without losing provenance, you may use [mapAddr]. For
 * example: `a.fetchXor(val).mapAddr(| a | a ^ val)`.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory
 * ordering of this operation. All ordering modes are possible. Note that
 * using [Acquire] makes the store part of this operation [Relaxed],
 * and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic
 * operations on [AtomicPtr].
 *
 * This API and its claimed semantics are part of the Strict Provenance
 * experiment, see the [module documentation for `ptr`][ptr] for
 * details.
 *
 * [mapAddr]: pointer.mapAddr
 *
 * # Examples
 *
 * ```kotlin
 * import core.sync.atomic.AtomicPtr
 * import core.sync.atomic.Ordering
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val pointer = MutPtr((RustMutableReference(allocateNativeCell(3L, PrimitiveLayouts.I64), PrimitiveLayouts.I64)).asMutPtr().rawAddress)
 * val atom = AtomicPtr.new(pointer)
 * // Toggle a tag bit on the pointer.
 * atom.fetchXor(1, Ordering.Relaxed)
 * check(atom.load(Ordering.Relaxed).addr() and 1 == 1)
 * ```
 */
public fun AtomicPtr.fetchXor(value: Int, order: Ordering): MutPtr {
    // SAFETY: data races are prevented by atomic intrinsics.
    return atomicXor(this.asPtr(), value, order, typeLayoutT = this.v.value.fieldLayout, typeLayoutU = PrimitiveLayouts.USIZE64).cast()
}

/**
 * Returns a mutable pointer to the underlying pointer.
 *
 * Doing non-atomic reads and writes on the resulting pointer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 * `* * T` instead of `AtomicPtr<T>`.
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * use std.sync.atomic.AtomicPtr;
 *
 * extern "C" {
 *     fn myAtomicOp(arg: *mut *mut u32);
 * }
 *
 * let mut value = 17;
 * let atomic = AtomicPtr.new(value);
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicPtr.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * Converts a `Boolean` into an `AtomicBool`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 *
 * val atomicBool = AtomicBool.from(true)
 * check("$atomicBool" == "true")
 * ```
 */
public fun fromAtomicBool(b: Boolean): AtomicBool = AtomicBool.new(b)

/**
 * Converts a `* T` into an `AtomicPtr<T>`.
 */
public fun fromAtomicPtrT(p: MutPtr): AtomicPtr = AtomicPtr.new(p)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * i8
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * i8
 * `].
 */
public class AtomicI8(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align1<Byte>>> = UnresolvedTypeLayout()) : Atomic<Byte, Align1<Byte>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align1<Byte>>, fieldLayout: TypeLayout<UnsafeCell<Align1<Byte>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicI8(): AtomicI8 = AtomicI8.new(Default.default())

public fun fromAtomicI8(v: Byte): AtomicI8 = AtomicI8.new(v)

public fun AtomicI8.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicI8.Companion.new(v: Byte): AtomicI8 = transmuteWithLayouts(v, PrimitiveLayouts.I8, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I8, { ptr, layout -> Align1<Byte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<Byte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI8(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicI8.Companion.fromPtr(ptr: MutPtr): AtomicI8 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicI8.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicI8.getMut(): RustMutableReference<Byte> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.I8)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicI8.Companion.fromMut(v: RustMutableReference<Byte>): AtomicI8 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I8, { ptr, layout -> Align1<Byte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<Byte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI8(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicI8.Companion.getMutSlice(thisRef: MutableList<AtomicI8>): MutableList<Byte> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Byte>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicI8.Companion.fromMutSlice(v: MutableList<Byte>): MutableList<AtomicI8> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicI8>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicI8.intoInner(): Byte = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI8.load(order: Ordering): Byte = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicI8.loadVolatile(order: Ordering): Byte = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI8.store(value: Byte, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.I8)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicI8.storeVolatile(value: Byte, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI8.swap(value: Byte, order: Ordering): Byte = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI8.compareAndSwap(current: Byte, new: Byte, order: Ordering): Byte =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI8.compareExchange(current: Byte, new: Byte, success: Ordering, failure: Ordering): Result<Byte, Byte> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI8.compareExchangeWeak(current: Byte, new: Byte, success: Ordering, failure: Ordering): Result<Byte, Byte> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI8.fetchAdd(value: Byte, order: Ordering): Byte = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI8.fetchSub(value: Byte, order: Ordering): Byte = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicI8.fetchAnd(value: Byte, order: Ordering): Byte = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicI8.fetchNand(value: Byte, order: Ordering): Byte = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicI8.fetchOr(value: Byte, order: Ordering): Byte = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicI8.fetchXor(value: Byte, order: Ordering): Byte = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8, typeLayoutU = PrimitiveLayouts.I8)

/**
 * An alias for
 * .
 */
public fun AtomicI8.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Byte) -> Byte?): Result<Byte, Byte> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI8.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Byte) -> Byte?): Result<Byte, Byte> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Byte, Byte>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI8.update(setOrder: Ordering, fetchOrder: Ordering, f: (Byte) -> Byte): Byte {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicI8.fetchMax(value: Byte, order: Ordering): Byte = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicI8.fetchMin(value: Byte, order: Ordering): Byte = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicI8.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * u8
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * u8
 * `].
 */
public class AtomicU8(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align1<UByte>>> = UnresolvedTypeLayout()) : Atomic<UByte, Align1<UByte>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align1<UByte>>, fieldLayout: TypeLayout<UnsafeCell<Align1<UByte>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicU8(): AtomicU8 = AtomicU8.new(Default.default())

public fun fromAtomicU8(v: UByte): AtomicU8 = AtomicU8.new(v)

public fun AtomicU8.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicU8.Companion.new(v: UByte): AtomicU8 = transmuteWithLayouts(v, PrimitiveLayouts.U8, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> Align1<UByte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<UByte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU8(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicU8.Companion.fromPtr(ptr: MutPtr): AtomicU8 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicU8.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicU8.getMut(): RustMutableReference<UByte> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.U8)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicU8.Companion.fromMut(v: RustMutableReference<UByte>): AtomicU8 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> Align1<UByte>(ptr, layout) }, { it.value }), 1L), { ptr, layout -> UnsafeCell<Align1<UByte>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU8(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicU8.Companion.getMutSlice(thisRef: MutableList<AtomicU8>): MutableList<UByte> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<UByte>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicU8.Companion.fromMutSlice(v: MutableList<UByte>): MutableList<AtomicU8> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicU8>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicU8.intoInner(): UByte = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU8.load(order: Ordering): UByte = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicU8.loadVolatile(order: Ordering): UByte = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU8.store(value: UByte, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.U8)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicU8.storeVolatile(value: UByte, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU8.swap(value: UByte, order: Ordering): UByte = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU8.compareAndSwap(current: UByte, new: UByte, order: Ordering): UByte =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU8.compareExchange(current: UByte, new: UByte, success: Ordering, failure: Ordering): Result<UByte, UByte> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU8.compareExchangeWeak(current: UByte, new: UByte, success: Ordering, failure: Ordering): Result<UByte, UByte> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU8.fetchAdd(value: UByte, order: Ordering): UByte = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU8.fetchSub(value: UByte, order: Ordering): UByte = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicU8.fetchAnd(value: UByte, order: Ordering): UByte = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicU8.fetchNand(value: UByte, order: Ordering): UByte = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicU8.fetchOr(value: UByte, order: Ordering): UByte = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicU8.fetchXor(value: UByte, order: Ordering): UByte = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8, typeLayoutU = PrimitiveLayouts.U8)

/**
 * An alias for
 * .
 */
public fun AtomicU8.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UByte) -> UByte?): Result<UByte, UByte> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU8.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UByte) -> UByte?): Result<UByte, UByte> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<UByte, UByte>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU8.update(setOrder: Ordering, fetchOrder: Ordering, f: (UByte) -> UByte): UByte {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicU8.fetchMax(value: UByte, order: Ordering): UByte = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicU8.fetchMin(value: UByte, order: Ordering): UByte = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicU8.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * i16
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * i16
 * `].
 */
public class AtomicI16(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align2<Short>>> = UnresolvedTypeLayout()) : Atomic<Short, Align2<Short>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align2<Short>>, fieldLayout: TypeLayout<UnsafeCell<Align2<Short>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicI16(): AtomicI16 = AtomicI16.new(Default.default())

public fun fromAtomicI16(v: Short): AtomicI16 = AtomicI16.new(v)

public fun AtomicI16.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicI16.Companion.new(v: Short): AtomicI16 = transmuteWithLayouts(v, PrimitiveLayouts.I16, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I16, { ptr, layout -> Align2<Short>(ptr, layout) }, { it.value }), 2L), { ptr, layout -> UnsafeCell<Align2<Short>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI16(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicI16.Companion.fromPtr(ptr: MutPtr): AtomicI16 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicI16.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicI16.getMut(): RustMutableReference<Short> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.I16)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicI16.Companion.fromMut(v: RustMutableReference<Short>): AtomicI16 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I16, { ptr, layout -> Align2<Short>(ptr, layout) }, { it.value }), 2L), { ptr, layout -> UnsafeCell<Align2<Short>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI16(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicI16.Companion.getMutSlice(thisRef: MutableList<AtomicI16>): MutableList<Short> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Short>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicI16.Companion.fromMutSlice(v: MutableList<Short>): MutableList<AtomicI16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicI16>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicI16.intoInner(): Short = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI16.load(order: Ordering): Short = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicI16.loadVolatile(order: Ordering): Short = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI16.store(value: Short, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.I16)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicI16.storeVolatile(value: Short, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI16.swap(value: Short, order: Ordering): Short = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI16.compareAndSwap(current: Short, new: Short, order: Ordering): Short =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI16.compareExchange(current: Short, new: Short, success: Ordering, failure: Ordering): Result<Short, Short> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI16.compareExchangeWeak(current: Short, new: Short, success: Ordering, failure: Ordering): Result<Short, Short> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI16.fetchAdd(value: Short, order: Ordering): Short = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI16.fetchSub(value: Short, order: Ordering): Short = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicI16.fetchAnd(value: Short, order: Ordering): Short = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicI16.fetchNand(value: Short, order: Ordering): Short = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicI16.fetchOr(value: Short, order: Ordering): Short = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicI16.fetchXor(value: Short, order: Ordering): Short = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16, typeLayoutU = PrimitiveLayouts.I16)

/**
 * An alias for
 * .
 */
public fun AtomicI16.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Short) -> Short?): Result<Short, Short> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI16.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Short) -> Short?): Result<Short, Short> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Short, Short>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI16.update(setOrder: Ordering, fetchOrder: Ordering, f: (Short) -> Short): Short {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicI16.fetchMax(value: Short, order: Ordering): Short = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicI16.fetchMin(value: Short, order: Ordering): Short = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicI16.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * u16
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * u16
 * `].
 */
public class AtomicU16(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align2<UShort>>> = UnresolvedTypeLayout()) : Atomic<UShort, Align2<UShort>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align2<UShort>>, fieldLayout: TypeLayout<UnsafeCell<Align2<UShort>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicU16(): AtomicU16 = AtomicU16.new(Default.default())

public fun fromAtomicU16(v: UShort): AtomicU16 = AtomicU16.new(v)

public fun AtomicU16.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicU16.Companion.new(v: UShort): AtomicU16 = transmuteWithLayouts(v, PrimitiveLayouts.U16, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U16, { ptr, layout -> Align2<UShort>(ptr, layout) }, { it.value }), 2L), { ptr, layout -> UnsafeCell<Align2<UShort>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU16(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicU16.Companion.fromPtr(ptr: MutPtr): AtomicU16 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicU16.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicU16.getMut(): RustMutableReference<UShort> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.U16)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicU16.Companion.fromMut(v: RustMutableReference<UShort>): AtomicU16 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U16, { ptr, layout -> Align2<UShort>(ptr, layout) }, { it.value }), 2L), { ptr, layout -> UnsafeCell<Align2<UShort>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU16(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicU16.Companion.getMutSlice(thisRef: MutableList<AtomicU16>): MutableList<UShort> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<UShort>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicU16.Companion.fromMutSlice(v: MutableList<UShort>): MutableList<AtomicU16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicU16>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicU16.intoInner(): UShort = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU16.load(order: Ordering): UShort = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicU16.loadVolatile(order: Ordering): UShort = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU16.store(value: UShort, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.U16)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicU16.storeVolatile(value: UShort, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU16.swap(value: UShort, order: Ordering): UShort = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU16.compareAndSwap(current: UShort, new: UShort, order: Ordering): UShort =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU16.compareExchange(current: UShort, new: UShort, success: Ordering, failure: Ordering): Result<UShort, UShort> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU16.compareExchangeWeak(current: UShort, new: UShort, success: Ordering, failure: Ordering): Result<UShort, UShort> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU16.fetchAdd(value: UShort, order: Ordering): UShort = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU16.fetchSub(value: UShort, order: Ordering): UShort = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicU16.fetchAnd(value: UShort, order: Ordering): UShort = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicU16.fetchNand(value: UShort, order: Ordering): UShort = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicU16.fetchOr(value: UShort, order: Ordering): UShort = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicU16.fetchXor(value: UShort, order: Ordering): UShort = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16, typeLayoutU = PrimitiveLayouts.U16)

/**
 * An alias for
 * .
 */
public fun AtomicU16.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UShort) -> UShort?): Result<UShort, UShort> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU16.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UShort) -> UShort?): Result<UShort, UShort> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<UShort, UShort>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU16.update(setOrder: Ordering, fetchOrder: Ordering, f: (UShort) -> UShort): UShort {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicU16.fetchMax(value: UShort, order: Ordering): UShort = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicU16.fetchMin(value: UShort, order: Ordering): UShort = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicU16.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * i32
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * i32
 * `].
 */
public class AtomicI32(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align4<Int>>> = UnresolvedTypeLayout()) : Atomic<Int, Align4<Int>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align4<Int>>, fieldLayout: TypeLayout<UnsafeCell<Align4<Int>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicI32(): AtomicI32 = AtomicI32.new(Default.default())

public fun fromAtomicI32(v: Int): AtomicI32 = AtomicI32.new(v)

public fun AtomicI32.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicI32.Companion.new(v: Int): AtomicI32 = transmuteWithLayouts(v, PrimitiveLayouts.I32, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> Align4<Int>(ptr, layout) }, { it.value }), 4L), { ptr, layout -> UnsafeCell<Align4<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI32(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicI32.Companion.fromPtr(ptr: MutPtr): AtomicI32 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicI32.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicI32.getMut(): RustMutableReference<Int> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.I32)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicI32.Companion.fromMut(v: RustMutableReference<Int>): AtomicI32 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> Align4<Int>(ptr, layout) }, { it.value }), 4L), { ptr, layout -> UnsafeCell<Align4<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI32(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicI32.Companion.getMutSlice(thisRef: MutableList<AtomicI32>): MutableList<Int> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Int>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicI32.Companion.fromMutSlice(v: MutableList<Int>): MutableList<AtomicI32> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicI32>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicI32.intoInner(): Int = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI32.load(order: Ordering): Int = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicI32.loadVolatile(order: Ordering): Int = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI32.store(value: Int, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.I32)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicI32.storeVolatile(value: Int, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI32.swap(value: Int, order: Ordering): Int = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI32.compareAndSwap(current: Int, new: Int, order: Ordering): Int =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI32.compareExchange(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI32.compareExchangeWeak(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI32.fetchAdd(value: Int, order: Ordering): Int = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI32.fetchSub(value: Int, order: Ordering): Int = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicI32.fetchAnd(value: Int, order: Ordering): Int = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicI32.fetchNand(value: Int, order: Ordering): Int = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicI32.fetchOr(value: Int, order: Ordering): Int = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicI32.fetchXor(value: Int, order: Ordering): Int = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32, typeLayoutU = PrimitiveLayouts.I32)

/**
 * An alias for
 * .
 */
public fun AtomicI32.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI32.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Int, Int>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI32.update(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int): Int {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicI32.fetchMax(value: Int, order: Ordering): Int = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicI32.fetchMin(value: Int, order: Ordering): Int = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicI32.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * u32
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * u32
 * `].
 */
public class AtomicU32(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align4<UInt>>> = UnresolvedTypeLayout()) : Atomic<UInt, Align4<UInt>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align4<UInt>>, fieldLayout: TypeLayout<UnsafeCell<Align4<UInt>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicU32(): AtomicU32 = AtomicU32.new(Default.default())

public fun fromAtomicU32(v: UInt): AtomicU32 = AtomicU32.new(v)

public fun AtomicU32.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicU32.Companion.new(v: UInt): AtomicU32 = transmuteWithLayouts(v, PrimitiveLayouts.U32, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> Align4<UInt>(ptr, layout) }, { it.value }), 4L), { ptr, layout -> UnsafeCell<Align4<UInt>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU32(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicU32.Companion.fromPtr(ptr: MutPtr): AtomicU32 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicU32.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicU32.getMut(): RustMutableReference<UInt> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.U32)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicU32.Companion.fromMut(v: RustMutableReference<UInt>): AtomicU32 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> Align4<UInt>(ptr, layout) }, { it.value }), 4L), { ptr, layout -> UnsafeCell<Align4<UInt>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU32(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicU32.Companion.getMutSlice(thisRef: MutableList<AtomicU32>): MutableList<UInt> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<UInt>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicU32.Companion.fromMutSlice(v: MutableList<UInt>): MutableList<AtomicU32> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicU32>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicU32.intoInner(): UInt = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU32.load(order: Ordering): UInt = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicU32.loadVolatile(order: Ordering): UInt = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU32.store(value: UInt, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.U32)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicU32.storeVolatile(value: UInt, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU32.swap(value: UInt, order: Ordering): UInt = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU32.compareAndSwap(current: UInt, new: UInt, order: Ordering): UInt =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU32.compareExchange(current: UInt, new: UInt, success: Ordering, failure: Ordering): Result<UInt, UInt> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU32.compareExchangeWeak(current: UInt, new: UInt, success: Ordering, failure: Ordering): Result<UInt, UInt> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU32.fetchAdd(value: UInt, order: Ordering): UInt = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU32.fetchSub(value: UInt, order: Ordering): UInt = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicU32.fetchAnd(value: UInt, order: Ordering): UInt = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicU32.fetchNand(value: UInt, order: Ordering): UInt = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicU32.fetchOr(value: UInt, order: Ordering): UInt = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicU32.fetchXor(value: UInt, order: Ordering): UInt = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32, typeLayoutU = PrimitiveLayouts.U32)

/**
 * An alias for
 * .
 */
public fun AtomicU32.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UInt) -> UInt?): Result<UInt, UInt> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU32.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (UInt) -> UInt?): Result<UInt, UInt> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<UInt, UInt>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU32.update(setOrder: Ordering, fetchOrder: Ordering, f: (UInt) -> UInt): UInt {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicU32.fetchMax(value: UInt, order: Ordering): UInt = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicU32.fetchMin(value: UInt, order: Ordering): UInt = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicU32.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * i64
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * i64
 * `].
 */
public class AtomicI64(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align8<Long>>> = UnresolvedTypeLayout()) : Atomic<Long, Align8<Long>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align8<Long>>, fieldLayout: TypeLayout<UnsafeCell<Align8<Long>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicI64(): AtomicI64 = AtomicI64.new(Default.default())

public fun fromAtomicI64(v: Long): AtomicI64 = AtomicI64.new(v)

public fun AtomicI64.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicI64.Companion.new(v: Long): AtomicI64 = transmuteWithLayouts(v, PrimitiveLayouts.I64, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> Align8<Long>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Long>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI64(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicI64.Companion.fromPtr(ptr: MutPtr): AtomicI64 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicI64.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicI64.getMut(): RustMutableReference<Long> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.I64)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicI64.Companion.fromMut(v: RustMutableReference<Long>): AtomicI64 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> Align8<Long>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Long>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI64(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicI64.Companion.getMutSlice(thisRef: MutableList<AtomicI64>): MutableList<Long> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Long>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicI64.Companion.fromMutSlice(v: MutableList<Long>): MutableList<AtomicI64> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicI64>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicI64.intoInner(): Long = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI64.load(order: Ordering): Long = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicI64.loadVolatile(order: Ordering): Long = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI64.store(value: Long, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.I64)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicI64.storeVolatile(value: Long, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI64.swap(value: Long, order: Ordering): Long = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI64.compareAndSwap(current: Long, new: Long, order: Ordering): Long =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI64.compareExchange(current: Long, new: Long, success: Ordering, failure: Ordering): Result<Long, Long> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI64.compareExchangeWeak(current: Long, new: Long, success: Ordering, failure: Ordering): Result<Long, Long> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI64.fetchAdd(value: Long, order: Ordering): Long = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI64.fetchSub(value: Long, order: Ordering): Long = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicI64.fetchAnd(value: Long, order: Ordering): Long = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicI64.fetchNand(value: Long, order: Ordering): Long = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicI64.fetchOr(value: Long, order: Ordering): Long = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicI64.fetchXor(value: Long, order: Ordering): Long = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64, typeLayoutU = PrimitiveLayouts.I64)

/**
 * An alias for
 * .
 */
public fun AtomicI64.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Long) -> Long?): Result<Long, Long> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI64.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Long) -> Long?): Result<Long, Long> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Long, Long>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI64.update(setOrder: Ordering, fetchOrder: Ordering, f: (Long) -> Long): Long {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicI64.fetchMax(value: Long, order: Ordering): Long = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicI64.fetchMin(value: Long, order: Ordering): Long = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicI64.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * u64
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * u64
 * `].
 */
public class AtomicU64(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align8<ULong>>> = UnresolvedTypeLayout()) : Atomic<ULong, Align8<ULong>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align8<ULong>>, fieldLayout: TypeLayout<UnsafeCell<Align8<ULong>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicU64(): AtomicU64 = AtomicU64.new(Default.default())

public fun fromAtomicU64(v: ULong): AtomicU64 = AtomicU64.new(v)

public fun AtomicU64.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicU64.Companion.new(v: ULong): AtomicU64 = transmuteWithLayouts(v, PrimitiveLayouts.U64, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> Align8<ULong>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<ULong>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU64(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicU64.Companion.fromPtr(ptr: MutPtr): AtomicU64 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicU64.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicU64.getMut(): RustMutableReference<ULong> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.U64)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicU64.Companion.fromMut(v: RustMutableReference<ULong>): AtomicU64 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> Align8<ULong>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<ULong>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU64(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicU64.Companion.getMutSlice(thisRef: MutableList<AtomicU64>): MutableList<ULong> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<ULong>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicU64.Companion.fromMutSlice(v: MutableList<ULong>): MutableList<AtomicU64> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicU64>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicU64.intoInner(): ULong = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU64.load(order: Ordering): ULong = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicU64.loadVolatile(order: Ordering): ULong = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU64.store(value: ULong, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.U64)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicU64.storeVolatile(value: ULong, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU64.swap(value: ULong, order: Ordering): ULong = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU64.compareAndSwap(current: ULong, new: ULong, order: Ordering): ULong =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU64.compareExchange(current: ULong, new: ULong, success: Ordering, failure: Ordering): Result<ULong, ULong> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU64.compareExchangeWeak(current: ULong, new: ULong, success: Ordering, failure: Ordering): Result<ULong, ULong> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU64.fetchAdd(value: ULong, order: Ordering): ULong = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU64.fetchSub(value: ULong, order: Ordering): ULong = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicU64.fetchAnd(value: ULong, order: Ordering): ULong = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicU64.fetchNand(value: ULong, order: Ordering): ULong = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicU64.fetchOr(value: ULong, order: Ordering): ULong = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicU64.fetchXor(value: ULong, order: Ordering): ULong = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64, typeLayoutU = PrimitiveLayouts.U64)

/**
 * An alias for
 * .
 */
public fun AtomicU64.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (ULong) -> ULong?): Result<ULong, ULong> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU64.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (ULong) -> ULong?): Result<ULong, ULong> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<ULong, ULong>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU64.update(setOrder: Ordering, fetchOrder: Ordering, f: (ULong) -> ULong): ULong {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicU64.fetchMax(value: ULong, order: Ordering): ULong = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicU64.fetchMin(value: ULong, order: Ordering): ULong = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicU64.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * i128
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * i128
 * `].
 */
public class AtomicI128(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align16<I128>>> = UnresolvedTypeLayout()) : Atomic<I128, Align16<I128>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align16<I128>>, fieldLayout: TypeLayout<UnsafeCell<Align16<I128>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicI128(): AtomicI128 = AtomicI128.new(Default.default())

public fun fromAtomicI128(v: I128): AtomicI128 = AtomicI128.new(v)

public fun AtomicI128.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicI128.Companion.new(v: I128): AtomicI128 = transmuteWithLayouts(v, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), { ptr, layout -> Align16<I128>(ptr, layout) }, { it.value }), 16L), { ptr, layout -> UnsafeCell<Align16<I128>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI128(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicI128.Companion.fromPtr(ptr: MutPtr): AtomicI128 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicI128.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicI128.getMut(): RustMutableReference<I128> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicI128.Companion.fromMut(v: RustMutableReference<I128>): AtomicI128 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), { ptr, layout -> Align16<I128>(ptr, layout) }, { it.value }), 16L), { ptr, layout -> UnsafeCell<Align16<I128>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicI128(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicI128.Companion.getMutSlice(thisRef: MutableList<AtomicI128>): MutableList<I128> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<I128>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicI128.Companion.fromMutSlice(v: MutableList<I128>): MutableList<AtomicI128> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicI128>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicI128.intoInner(): I128 = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI128.load(order: Ordering): I128 = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicI128.loadVolatile(order: Ordering): I128 = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI128.store(value: I128, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicI128.storeVolatile(value: I128, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicI128.swap(value: I128, order: Ordering): I128 = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicI128.compareAndSwap(current: I128, new: I128, order: Ordering): I128 =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI128.compareExchange(current: I128, new: I128, success: Ordering, failure: Ordering): Result<I128, I128> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicI128.compareExchangeWeak(current: I128, new: I128, success: Ordering, failure: Ordering): Result<I128, I128> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI128.fetchAdd(value: I128, order: Ordering): I128 = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicI128.fetchSub(value: I128, order: Ordering): I128 = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicI128.fetchAnd(value: I128, order: Ordering): I128 = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicI128.fetchNand(value: I128, order: Ordering): I128 = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicI128.fetchOr(value: I128, order: Ordering): I128 = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicI128.fetchXor(value: I128, order: Ordering): I128 = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * An alias for
 * .
 */
public fun AtomicI128.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (I128) -> I128?): Result<I128, I128> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI128.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (I128) -> I128?): Result<I128, I128> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<I128, I128>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicI128.update(setOrder: Ordering, fetchOrder: Ordering, f: (I128) -> I128): I128 {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicI128.fetchMax(value: I128, order: Ordering): I128 = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicI128.fetchMin(value: I128, order: Ordering): I128 = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicI128.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * u128
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * u128
 * `].
 */
public class AtomicU128(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align16<U128>>> = UnresolvedTypeLayout()) : Atomic<U128, Align16<U128>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align16<U128>>, fieldLayout: TypeLayout<UnsafeCell<Align16<U128>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicU128(): AtomicU128 = AtomicU128.new(Default.default())

public fun fromAtomicU128(v: U128): AtomicU128 = AtomicU128.new(v)

public fun AtomicU128.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicU128.Companion.new(v: U128): AtomicU128 = transmuteWithLayouts(v, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), { ptr, layout -> Align16<U128>(ptr, layout) }, { it.value }), 16L), { ptr, layout -> UnsafeCell<Align16<U128>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU128(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicU128.Companion.fromPtr(ptr: MutPtr): AtomicU128 = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicU128.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicU128.getMut(): RustMutableReference<U128> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicU128.Companion.fromMut(v: RustMutableReference<U128>): AtomicU128 = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), { ptr, layout -> Align16<U128>(ptr, layout) }, { it.value }), 16L), { ptr, layout -> UnsafeCell<Align16<U128>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicU128(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicU128.Companion.getMutSlice(thisRef: MutableList<AtomicU128>): MutableList<U128> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<U128>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicU128.Companion.fromMutSlice(v: MutableList<U128>): MutableList<AtomicU128> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicU128>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicU128.intoInner(): U128 = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU128.load(order: Ordering): U128 = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicU128.loadVolatile(order: Ordering): U128 = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU128.store(value: U128, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicU128.storeVolatile(value: U128, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicU128.swap(value: U128, order: Ordering): U128 = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicU128.compareAndSwap(current: U128, new: U128, order: Ordering): U128 =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU128.compareExchange(current: U128, new: U128, success: Ordering, failure: Ordering): Result<U128, U128> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicU128.compareExchangeWeak(current: U128, new: U128, success: Ordering, failure: Ordering): Result<U128, U128> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU128.fetchAdd(value: U128, order: Ordering): U128 = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicU128.fetchSub(value: U128, order: Ordering): U128 = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicU128.fetchAnd(value: U128, order: Ordering): U128 = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicU128.fetchNand(value: U128, order: Ordering): U128 = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicU128.fetchOr(value: U128, order: Ordering): U128 = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicU128.fetchXor(value: U128, order: Ordering): U128 = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), typeLayoutU = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * An alias for
 * .
 */
public fun AtomicU128.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (U128) -> U128?): Result<U128, U128> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU128.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (U128) -> U128?): Result<U128, U128> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<U128, U128>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicU128.update(setOrder: Ordering, fetchOrder: Ordering, f: (U128) -> U128): U128 {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicU128.fetchMax(value: U128, order: Ordering): U128 = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicU128.fetchMin(value: U128, order: Ordering): U128 = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicU128.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * isize
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * isize
 * `].
 */
public class AtomicIsize(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align8<Int>>> = UnresolvedTypeLayout()) : Atomic<Int, Align8<Int>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align8<Int>>, fieldLayout: TypeLayout<UnsafeCell<Align8<Int>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicIsize(): AtomicIsize = AtomicIsize.new(Default.default())

public fun fromAtomicIsize(v: Int): AtomicIsize = AtomicIsize.new(v)

public fun AtomicIsize.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicIsize.Companion.new(v: Int): AtomicIsize = transmuteWithLayouts(v, PrimitiveLayouts.ISIZE64, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.ISIZE64, { ptr, layout -> Align8<Int>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicIsize(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicIsize.Companion.fromPtr(ptr: MutPtr): AtomicIsize = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicIsize.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicIsize.getMut(): RustMutableReference<Int> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.ISIZE64)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicIsize.Companion.fromMut(v: RustMutableReference<Int>): AtomicIsize = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.ISIZE64, { ptr, layout -> Align8<Int>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicIsize(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicIsize.Companion.getMutSlice(thisRef: MutableList<AtomicIsize>): MutableList<Int> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Int>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicIsize.Companion.fromMutSlice(v: MutableList<Int>): MutableList<AtomicIsize> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicIsize>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicIsize.intoInner(): Int = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicIsize.load(order: Ordering): Int = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicIsize.loadVolatile(order: Ordering): Int = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicIsize.store(value: Int, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.ISIZE64)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicIsize.storeVolatile(value: Int, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicIsize.swap(value: Int, order: Ordering): Int = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicIsize.compareAndSwap(current: Int, new: Int, order: Ordering): Int =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicIsize.compareExchange(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicIsize.compareExchangeWeak(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicIsize.fetchAdd(value: Int, order: Ordering): Int = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicIsize.fetchSub(value: Int, order: Ordering): Int = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicIsize.fetchAnd(value: Int, order: Ordering): Int = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicIsize.fetchNand(value: Int, order: Ordering): Int = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicIsize.fetchOr(value: Int, order: Ordering): Int = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicIsize.fetchXor(value: Int, order: Ordering): Int = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64, typeLayoutU = PrimitiveLayouts.ISIZE64)

/**
 * An alias for
 * .
 */
public fun AtomicIsize.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicIsize.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Int, Int>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicIsize.update(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int): Int {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicIsize.fetchMax(value: Int, order: Ordering): Int = atomicMax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicIsize.fetchMin(value: Int, order: Ordering): Int = atomicMin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.ISIZE64)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicIsize.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An integer type which can be safely shared between threads.
 *
 * This type has the same
 * as the underlying integer type, [`
 * Int
 * `].
 *
 * For more about the differences between atomic types and
 * non-atomic types as well as information about the portability of
 * this type, please see the [module-level documentation].
 *
 * **Note:** This type is only available on platforms that support
 * atomic loads and stores of [`
 * Int
 * `].
 */
public class AtomicUsize(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<Align8<Int>>> = UnresolvedTypeLayout()) : Atomic<Int, Align8<Int>>(fieldStorage, fieldLayout) {
    public constructor(v: UnsafeCell<Align8<Int>>, fieldLayout: TypeLayout<UnsafeCell<Align8<Int>>> = UnresolvedTypeLayout()) : this(allocateNativeCell(v, fieldLayout), fieldLayout)
    public companion object
}

public fun defaultAtomicUsize(): AtomicUsize = AtomicUsize.new(Default.default())

public fun fromAtomicUsize(v: Int): AtomicUsize = AtomicUsize.new(v)

public fun AtomicUsize.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Creates a new atomic integer.
 *
 * # Examples
 *
 * ```kotlin
 */
public fun AtomicUsize.Companion.new(v: Int): AtomicUsize = transmuteWithLayouts(v, PrimitiveLayouts.USIZE64, TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> Align8<Int>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicUsize(ptr, layout) }, { it.v }))

/**
 * Creates a new reference to an atomic integer from a pointer.
 *
 * # Examples
 *
 * // Get a pointer to an allocated value
 *
 * (
 *     // Create an atomic view of the allocated value
 *
 *     // Use `atomic` for atomic operations, possibly share it with other threads
 *     atomic.store(1, atomic.Ordering.Relaxed);
 * )
 *
 * // It's ok to non-atomically access the value behind `ptr`,
 * // since the reference to the atomic ended its scope in the block above
 * assertEq(unchecked ( *ptr ), 1);
 *
 * // Deallocate the value
 * unchecked ( drop(Box.fromRaw(ptr)) )
 * ```kotlin
 *
 * * `ptr` must be aligned to
 * * `ptr` must be [valid] for both reads and writes for the whole scope `a`.
 * * You must adhere to the[Memory model for atomic accesses].In particular, it is not
 * allowed to mix conflicting atomic and non - atomic accesses, or atomic accesses of different
 * sizes, without synchronization.
 *
 * [valid]: ptr # safety
 * [Memory model for atomic accesses]: self # memory - model - for - atomic - accesses
 * ```
 */
public fun AtomicUsize.Companion.fromPtr(ptr: MutPtr): AtomicUsize = MutPtr((ptr).rawAddress)

/**
 * Creates a new pointer to an atomic integer from a pointer.
 *
 * This is useful if you want to do volatile atomic accesses, and thus avoid creating
 * a reference to the destination.
 */
public fun AtomicUsize.Companion.fromPtrRaw(ptr: MutPtr): ConstPtr = ConstPtr((ConstPtr((ptr).rawAddress)).rawAddress)

/**
 * Returns a mutable reference to the underlying integer.
 *
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(*someVar.getMut(), 10);
 * *someVar.getMut() = 5;
 * assertEq(someVar.load(Ordering.SeqCst), 5);
 * ```kotlin
 */
public fun AtomicUsize.getMut(): RustMutableReference<Int> = RustMutableReference(MutPtr((this.asPtr()).rawAddress), PrimitiveLayouts.USIZE64)

/**
 * # Examples
 *
 * let mut someInt = 123;
 * a.store(100, Ordering.Relaxed);
 * assertEq(someInt, 100);
 * ```kotlin
 * ```
 */
public fun AtomicUsize.Companion.fromMut(v: RustMutableReference<Int>): AtomicUsize = RustMutableReference(MutPtr((((MutPtr(v.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(TransparentTypeLayout(AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> Align8<Int>(ptr, layout) }, { it.value }), 8L), { ptr, layout -> UnsafeCell<Align8<Int>>(ptr, layout) }, { it.value }), { ptr, layout -> AtomicUsize(ptr, layout) }, { it.v }))

/**
 * This is safe because the mutable reference guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(view, [0; 10]);
 * view
 *     .iterMut()
 *     .enumerate()
 *     .forEach(|(idx, int)| *int = idx as _);
 *
 * std.thread.scope(|s| (
 *     someInts
 *         .iter()
 *         .enumerate()
 *         .forEach(|(idx, int)| (
 *             s.spawn(move || assertEq(int.load(Ordering.Relaxed), idx as _));
 *         ))
 * ));
 * ```kotlin
 */
public fun AtomicUsize.Companion.getMutSlice(thisRef: MutableList<AtomicUsize>): MutableList<Int> = RustMutableReference(MutPtr((((mutPtrFromRustCast(thisRef)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<Int>>())

/**
 * # Examples
 *
 * let mut someInts = [0; 10];
 * std.thread.scope(|s| (
 *     for i in 0..a.len() (
 *         s.spawn(move || a[i].store(i as _, Ordering.Relaxed));
 *     )
 * ));
 * for (i, n) in someInts.intoIter().enumerate() (
 *     assertEq(i, n as Int);
 * )
 * ```kotlin
 */
public fun AtomicUsize.Companion.fromMutSlice(v: MutableList<Int>): MutableList<AtomicUsize> = RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<AtomicUsize>>())

/**
 * Consumes the atomic and returns the contained value.
 *
 * This is safe because passing `self` by value guarantees that no other threads are
 * concurrently accessing the atomic data.
 *
 * # Examples
 *
 * assertEq(someVar.intoInner(), 5);
 * ```kotlin
 */
public fun AtomicUsize.intoInner(): Int = transmute(this)

/**
 * Loads a value from the atomic integer.
 *
 * `load` takes an [Ordering] argument which describes the memory ordering of this operation.
 * Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 *
 * # Examples
 *
 * assertEq(someVar.load(Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicUsize.load(order: Ordering): Int = atomicLoad((this.asPtr()).asConstPtr(), order, false, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Perform a volatile load from the atomic integer.
 *
 * `loadVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Acquire] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be [valid] for reads, or `self` must point to memory
 *   outside of all Rust allocations and reading from that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * * Reading from `self` must produce a properly initialized value of the underlying
 *   integer type.
 *
 * # Panics
 *
 * Panics if `order` is [Release] or [AcqRel].
 */
public fun AtomicUsize.loadVolatile(order: Ordering): Int = atomicLoad(this.cast(), order, true)

/**
 * Stores a value into the atomic integer.
 *
 * `store` takes an [Ordering] argument which describes the memory ordering of this operation.
 *  Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 *
 * # Examples
 *
 * someVar.store(10, Ordering.Relaxed);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicUsize.store(value: Int, order: Ordering) {
    run {
        atomicStore(this.asPtr(), value, order, false, typeLayoutT = PrimitiveLayouts.USIZE64)
    }
}

/**
 * Performs a volatile store into the atomic integer.
 *
 * `storeVolatile` takes an [Ordering] argument which describes the memory ordering
 * of this operation. Possible values are [SeqCst], [Release] and [Relaxed].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `self` must be either [valid] for writes, or `self` must point to memory
 *   outside of all Rust allocations and writing to that memory must:
 *   - not trap, and
 *   - not cause any memory inside a Rust allocation to be modified.
 *
 * * `self` must be aligned to
 *
 * # Panics
 *
 * Panics if `order` is [Acquire] or [AcqRel].
 */
public fun AtomicUsize.storeVolatile(value: Int, order: Ordering) {
    run {
        atomicStore(this.cast().castMut(), value, order, true)
    }
}

/**
 * Stores a value into the atomic integer, returning the previous value.
 *
 * `swap` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.swap(10, Ordering.Relaxed), 5);
 * ```kotlin
 */
public fun AtomicUsize.swap(value: Int, order: Ordering): Int = atomicSwap(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is always the previous value. If it is equal to `current`, then the
 * value was updated.
 *
 * `compareAndSwap` also takes an [Ordering] argument which describes the memory
 * ordering of this operation. Notice that even when using [AcqRel], the operation
 * might fail and hence just perform an `Acquire` load, but not have `Release` semantics.
 * Using [Acquire] makes the store part of this operation [Relaxed] if it
 * happens, and using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Migrating to `compareExchange` and `compareExchangeWeak`
 *
 * `compareAndSwap` is equivalent to `compareExchange` with the following mapping for
 * memory orderings:
 *
 * Original | Success | Failure
 * -------- | ------- | -------
 * Relaxed  | Relaxed | Relaxed
 * Acquire  | Acquire | Acquire
 * Release  | Release | Relaxed
 * AcqRel   | AcqRel  | Acquire
 * SeqCst   | SeqCst  | SeqCst
 *
 * `compareAndSwap` and `compareExchange` also differ in their return type. You can use
 * `compareExchange(...).unwrapOrElse(| x | x)` to recover the behavior of `compareAndSwap`,
 * but in most cases it is more idiomatic to check whether the return value is `Ok` or `Err`
 * rather than to infer success vs failure based on the value that was read.
 *
 * During migration, consider whether it makes sense to use `compareExchangeWeak` instead.
 * `compareExchangeWeak` is allowed to fail spuriously even when the comparison succeeds,
 * which allows the compiler to generate better assembly code when the compare and swap
 * is used in a loop.
 *
 * # Examples
 *
 * assertEq(someVar.compareAndSwap(5, 10, Ordering.Relaxed), 5);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareAndSwap(6, 12, Ordering.Relaxed), 10);
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 */
public fun AtomicUsize.compareAndSwap(current: Int, new: Int, order: Ordering): Int =
    run {
        val matchVal = this.compareExchange(current, new, order, strongestFailureOrdering(order))
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            val x1 = matchVal.error
            x1
        } }
    }

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * The return value is a result indicating whether the new value was written and
 * containing the previous value. On success this value is guaranteed to be equal to
 * `current`.
 *
 * `compareExchange` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(someVar.compareExchange(5, 10,
 *                                      Ordering.Acquire,
 *                                      Ordering.Relaxed),
 *            Ok(5));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 *
 * assertEq(someVar.compareExchange(6, 12,
 *                                      Ordering.SeqCst,
 *                                      Ordering.Acquire),
 *            Err(10));
 * assertEq(someVar.load(Ordering.Relaxed), 10);
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicUsize.compareExchange(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchange(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Stores a value into the atomic integer if the current value is the same as
 * the `current` value.
 *
 * this function is allowed to spuriously fail even
 * when the comparison succeeds, which can result in more efficient code on some
 * platforms. The return value is a result indicating whether the new value was
 * written and containing the previous value.
 *
 * `compareExchangeWeak` takes two [Ordering] arguments to describe the memory
 * ordering of this operation. `success` describes the required ordering for the
 * read-modify-write operation that takes place if the comparison with `current` succeeds.
 * `failure` describes the required ordering for the load operation that takes place when
 * the comparison fails. Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the successful load
 * [Relaxed]. The failure ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * let mut old = val.load(Ordering.Relaxed);
 * loop (
 *     let new = old * 2;
 *     match val.compareExchangeWeak(old, new, Ordering.SeqCst, Ordering.Relaxed) (
 *         Ok(_) => break,
 *         Err(x) => old = x,
 *     )
 * )
 * ```kotlin
 *
 * `compareExchange` is a [compare-and-swap operation] and thus exhibits the usual downsides
 * of CAS operations.In particular, a load of the value followed by a successful
 * `compareExchange` with the previous load *does not ensure* that other threads have not
 * changed the value in the interim.This is usually important when the * equality * check in
 * the `compareExchange` is being used to check the *identity* of a value, but equality
 * does not necessarily imply identity.This is a particularly common case for pointers, as
 * a pointer holding the same address does not imply that the same object exists at that
 * address! In this case, `compareExchange` can lead to the [ABA problem].
 *
 * [ABA Problem]: https:
 * [compare - and - swap operation]: https:
 * ```
 */
public fun AtomicUsize.compareExchangeWeak(current: Int, new: Int, success: Ordering, failure: Ordering): Result<Int, Int> = atomicCompareExchangeWeak(this.asPtr(), current, new, success, failure, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Adds to the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchAdd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAdd(10, Ordering.SeqCst), 0);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicUsize.fetchAdd(value: Int, order: Ordering): Int = atomicAdd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * Subtracts from the current value, returning the previous value.
 *
 * This operation wraps around on overflow.
 *
 * `fetchSub` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchSub(10, Ordering.SeqCst), 20);
 * assertEq(foo.load(Ordering.SeqCst), 10);
 * ```kotlin
 */
public fun AtomicUsize.fetchSub(value: Int, order: Ordering): Int = atomicSub(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * Bitwise "and" with the current value.
 *
 * Performs a bitwise "and" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchAnd` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchAnd(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b100001);
 * ```kotlin
 */
public fun AtomicUsize.fetchAnd(value: Int, order: Ordering): Int = atomicAnd(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * Bitwise "nand" with the current value.
 *
 * Performs a bitwise "nand" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchNand` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchNand(0x31, Ordering.SeqCst), 0x13);
 * assertEq(foo.load(Ordering.SeqCst), !(0x13 & 0x31));
 * ```kotlin
 */
public fun AtomicUsize.fetchNand(value: Int, order: Ordering): Int = atomicNand(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * Bitwise "or" with the current value.
 *
 * Performs a bitwise "or" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchOr` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchOr(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b111111);
 * ```kotlin
 */
public fun AtomicUsize.fetchOr(value: Int, order: Ordering): Int = atomicOr(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * Bitwise "xor" with the current value.
 *
 * Performs a bitwise "xor" operation on the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchXor` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchXor(0b110011, Ordering.SeqCst), 0b101101);
 * assertEq(foo.load(Ordering.SeqCst), 0b011110);
 * ```kotlin
 */
public fun AtomicUsize.fetchXor(value: Int, order: Ordering): Int = atomicXor(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64, typeLayoutU = PrimitiveLayouts.USIZE64)

/**
 * An alias for
 * .
 */
public fun AtomicUsize.fetchUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> = this.tryUpdate(setOrder, fetchOrder, f)

/**
 * Fetches the value, and applies a function to it that returns an optional
 * new value. Returns a `Result` of `Ok(previousValue)` if the function returned `Some(_)`, else
 * `Err(previousValue)`.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, as long as the function returns `Some(_)`, but the function will have been applied
 * only once to the stored value.
 *
 * `tryUpdate` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |_| None), Err(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(7));
 * assertEq(x.tryUpdate(Ordering.SeqCst, Ordering.SeqCst, |x| Some(x + 1)), Ok(8));
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicUsize.tryUpdate(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int?): Result<Int, Int> {
    var prev = this.load(fetchOrder)
    while (true) {
        val matchVal = f(prev)
        if (matchVal == null) break
        val next = matchVal
        when (val matchVal1 = this.compareExchangeWeak(prev, next, setOrder, fetchOrder)) {
            is Result.Ok -> {
                val x = matchVal1
                return x
            }
            is Result.Err -> {
                val nextPrev = matchVal1.error
                prev = nextPrev
            }
        }
    }
    return Result.Err<Int, Int>(prev)
}

/**
 * Fetches the value, and applies a function to it that returns a new value.
 * The new value is stored and the old value is returned.
 *
 * Note: This may call the function multiple times if the value has been changed from other threads in
 * the meantime, but the function will have been applied only once to the stored value.
 *
 * `update` takes two [Ordering] arguments to describe the memory ordering of this operation.
 * The first describes the required ordering for when the operation finally succeeds while the second
 * describes the required ordering for loads. These correspond to the success and failure orderings of
 * respectively.
 *
 * Using [Acquire] as success ordering makes the store part
 * of this operation [Relaxed], and using [Release] makes the final successful load
 * [Relaxed]. The (failed) load ordering can only be [SeqCst], [Acquire] or [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Considerations
 *
 * [CAS operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 * This method is not magic; it is not provided by the hardware, and does not act like a
 * critical section or mutex.
 *
 * It is implemented on top of an atomic [compare-and-swap operation], and thus is subject to
 * the usual drawbacks of CAS operations. In particular, be careful of the [ABA problem]
 * if this atomic integer is an index or more generally if knowledge of only the *bitwise value*
 * of the atomic is not in and of itself sufficient to ensure any required preconditions.
 *
 * [ABA Problem]: https://en.wikipedia.org/wiki/AbaProblem
 * [compare-and-swap operation]: https://en.wikipedia.org/wiki/Compare-and-swap
 *
 * # Examples
 *
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 7);
 * assertEq(x.update(Ordering.SeqCst, Ordering.SeqCst, |x| x + 1), 8);
 * assertEq(x.load(Ordering.SeqCst), 9);
 * ```kotlin
 */
public fun AtomicUsize.update(setOrder: Ordering, fetchOrder: Ordering, f: (Int) -> Int): Int {
    var prev = this.load(fetchOrder)
    while (true) {
        run {
            val matchVal = this.compareExchangeWeak(prev, f(prev), setOrder, fetchOrder)
            when (matchVal) { is Result.Ok -> {
                val x = matchVal.value
                break
            }; is Result.Err -> {
                val nextPrev = matchVal.error
                prev = nextPrev
            } }
        }
    }
}

/**
 * Maximum with the current value.
 *
 * Finds the maximum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMax` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMax(42, Ordering.SeqCst), 23);
 * assertEq(foo.load(Ordering.SeqCst), 42);
 * ```kotlin
 * If you want to obtain the maximum value in one step, you can import the following: val bar = 42; val maxFoo = foo.fetchMax(bar, Ordering.SeqCst).max(bar); assert(maxFoo == 42);
 * ```
 */
public fun AtomicUsize.fetchMax(value: Int, order: Ordering): Int = atomicUmax(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Minimum with the current value.
 *
 * Finds the minimum of the current value and the argument `val`, and
 * sets the new value to the result.
 *
 * Returns the previous value.
 *
 * `fetchMin` takes an [Ordering] argument which describes the memory ordering
 * of this operation. All ordering modes are possible. Note that using
 * [Acquire] makes the store part of this operation [Relaxed], and
 * using [Release] makes the load part [Relaxed].
 *
 * **Note**: This method is only available on platforms that support atomic operations on
 *
 * # Examples
 *
 * assertEq(foo.fetchMin(42, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 23);
 * assertEq(foo.fetchMin(22, Ordering.Relaxed), 23);
 * assertEq(foo.load(Ordering.Relaxed), 22);
 * ```kotlin
 * If you want to obtain the minimum value in one step, you can import the following: val bar = 12; val minFoo = foo.fetchMin(bar, Ordering.SeqCst).min(bar); assertEq(minFoo, 12);
 * ```
 */
public fun AtomicUsize.fetchMin(value: Int, order: Ordering): Int = atomicUmin(this.asPtr(), value, order, typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Returns a mutable pointer to the underlying integer.
 *
 * Doing non-atomic reads and writes on the resulting integer can be a data race.
 * This method is mostly useful for FFI, where the function signature may use
 *
 * Returning an `*` pointer from a shared reference to this atomic is safe because the
 * atomic types work with interior mutability. All modifications of an atomic change the value
 * through a shared reference, and can do so safely as long as they use atomic operations. Any
 * use of the returned raw pointer requires an `unchecked` block and still has to uphold the
 * requirements of the [memory model].
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 *
 * extern "C" {
 * }
 *
 * // SAFETY: Safe as long as `myAtomicOp` is atomic.
 * unchecked {
 *     myAtomicOp(atomic.asPtr());
 * }
 * # }
 * ```
 *
 * [memory model]: self#memory-model-for-atomic-accesses
 */
public fun AtomicUsize.asPtr(): MutPtr = MutPtr((this.v.get()).rawAddress)

/**
 * An [AtomicIsize] initialized to `0`.
 */
public val ATOMIC_ISIZE_INIT: AtomicIsize = AtomicIsize.new(0)

/**
 * An [AtomicUsize] initialized to `0`.
 */
public val ATOMIC_USIZE_INIT: AtomicUsize = AtomicUsize.new(0)

public fun strongestFailureOrdering(order: Ordering): Ordering =
    when (order) {
        Release -> Relaxed
        Relaxed -> Relaxed
        SeqCst -> SeqCst
        Acquire -> Acquire
        AcqRel -> Acquire
    }

// even without panics, this helps for Miri backtraces
public fun <T> atomicStore(dst: MutPtr, value: T, order: Ordering, volatile: Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `atomicStore`.
    when (order) {
        Relaxed -> atomicStoreWithLayout(dst.rawAddress, value, 0, typeLayoutT)
        Release -> atomicStoreWithLayout(dst.rawAddress, value, 1, typeLayoutT)
        SeqCst -> atomicStoreWithLayout(dst.rawAddress, value, 4, typeLayoutT)
        Acquire -> error("there is no such thing as an acquire store")
        AcqRel -> error("there is no such thing as an acquire-release store")
    }
}

// even without panics, this helps for Miri backtraces
public fun <T> atomicLoad(dst: ConstPtr, order: Ordering, volatile: Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicLoad`.
    return when (order) {
        Relaxed -> atomicLoadWithLayout(dst.rawAddress, 0, typeLayoutT)
        Acquire -> atomicLoadWithLayout(dst.rawAddress, 2, typeLayoutT)
        SeqCst -> atomicLoadWithLayout(dst.rawAddress, 4, typeLayoutT)
        Release -> error("there is no such thing as a release load")
        AcqRel -> error("there is no such thing as an acquire-release load")
    }
}

// even without panics, this helps for Miri backtraces
public fun <T> atomicSwap(dst: MutPtr, value: T, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicSwap`.
    return when (order) {
        Relaxed -> atomicXchgWithLayout(dst.rawAddress, value, 0, typeLayoutT)
        Acquire -> atomicXchgWithLayout(dst.rawAddress, value, 2, typeLayoutT)
        Release -> atomicXchgWithLayout(dst.rawAddress, value, 1, typeLayoutT)
        AcqRel -> atomicXchgWithLayout(dst.rawAddress, value, 3, typeLayoutT)
        SeqCst -> atomicXchgWithLayout(dst.rawAddress, value, 4, typeLayoutT)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Returns the previous value (like __sync_fetch_and_add).
 */
public fun <T, U> atomicAdd(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicAdd`.
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 1, 0, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 1, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 1, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 1, 3, typeLayoutT, typeLayoutU)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 1, 4, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Returns the previous value (like __sync_fetch_and_sub).
 */
public fun <T, U> atomicSub(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicSub`.
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 2, 0, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 2, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 2, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 2, 3, typeLayoutT, typeLayoutU)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 2, 4, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Publicly exposed for stdarch; nobody else should use this.
 */
public fun <T> atomicCompareExchange(dst: MutPtr, old: T, new: T, success: Ordering, failure: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<T, T> {
    // SAFETY: the caller must uphold the safety contract for `atomicCompareExchange`.
    val (value, ok) =
        run {
            val matchVal = Pair(success, failure)
            when {
                matchVal.first == Relaxed && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 0, typeLayoutT)
                }
                matchVal.first == Relaxed && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 2, typeLayoutT)
                }
                matchVal.first == Relaxed && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 4, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 0, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 2, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 4, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 0, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 2, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 4, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 0, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 2, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 4, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 0, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 2, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 4, typeLayoutT)
                }
                matchVal.second == AcqRel -> error("there is no such thing as an acquire-release failure ordering")
                else -> error("there is no such thing as a release failure ordering")
            }
        }
    return if (ok) {
        Result.Ok<T, T>(value)
    } else {
        Result.Err<T, T>(value)
    }
}

// even without panics, this helps for Miri backtraces
public fun <T> atomicCompareExchangeWeak(dst: MutPtr, old: T, new: T, success: Ordering, failure: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<T, T> {
    // SAFETY: the caller must uphold the safety contract for `atomicCompareExchangeWeak`.
    val (value, ok) =
        run {
            val matchVal = Pair(success, failure)
            when {
                matchVal.first == Relaxed && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 0, typeLayoutT)
                }
                matchVal.first == Relaxed && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 2, typeLayoutT)
                }
                matchVal.first == Relaxed && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 0, 4, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 0, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 2, typeLayoutT)
                }
                matchVal.first == Acquire && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 2, 4, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 0, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 2, typeLayoutT)
                }
                matchVal.first == Release && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 1, 4, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 0, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 2, typeLayoutT)
                }
                matchVal.first == AcqRel && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 3, 4, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == Relaxed -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 0, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == Acquire -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 2, typeLayoutT)
                }
                matchVal.first == SeqCst && matchVal.second == SeqCst -> {
                    atomicCxchgWithLayout(dst.rawAddress, old, new, 4, 4, typeLayoutT)
                }
                matchVal.second == AcqRel -> error("there is no such thing as an acquire-release failure ordering")
                else -> error("there is no such thing as a release failure ordering")
            }
        }
    return if (ok) {
        Result.Ok<T, T>(value)
    } else {
        Result.Err<T, T>(value)
    }
}

// even without panics, this helps for Miri backtraces
public fun <T, U> atomicAnd(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicAnd`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 3, 0, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 3, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 3, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 3, 3, typeLayoutT, typeLayoutU)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 3, 4, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces
public fun <T, U> atomicNand(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicNand`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 4, 0, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 4, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 4, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 4, 3, typeLayoutT, typeLayoutU)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 4, 4, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces
public fun <T, U> atomicOr(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicOr`
    return when (order) {
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 5, 4, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 5, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 5, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 5, 3, typeLayoutT, typeLayoutU)
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 5, 0, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces
public fun <T, U> atomicXor(dst: MutPtr, value: U, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicXor`
    return when (order) {
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 6, 4, typeLayoutT, typeLayoutU)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 6, 2, typeLayoutT, typeLayoutU)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 6, 1, typeLayoutT, typeLayoutU)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 6, 3, typeLayoutT, typeLayoutU)
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 6, 0, typeLayoutT, typeLayoutU)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Updates `* dst` to the max value of `val` and the old value (signed comparison)
 */
public fun <T> atomicMax(dst: MutPtr, value: T, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicMax`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 7, 0, typeLayoutT, typeLayoutT)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 7, 2, typeLayoutT, typeLayoutT)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 7, 1, typeLayoutT, typeLayoutT)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 7, 3, typeLayoutT, typeLayoutT)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 7, 4, typeLayoutT, typeLayoutT)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Updates `* dst` to the min value of `val` and the old value (signed comparison)
 */
public fun <T> atomicMin(dst: MutPtr, value: T, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicMin`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 8, 0, typeLayoutT, typeLayoutT)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 8, 2, typeLayoutT, typeLayoutT)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 8, 1, typeLayoutT, typeLayoutT)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 8, 3, typeLayoutT, typeLayoutT)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 8, 4, typeLayoutT, typeLayoutT)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Updates `* dst` to the max value of `val` and the old value (unsigned comparison)
 */
public fun <T> atomicUmax(dst: MutPtr, value: T, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicUmax`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 9, 0, typeLayoutT, typeLayoutT)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 9, 2, typeLayoutT, typeLayoutT)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 9, 1, typeLayoutT, typeLayoutT)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 9, 3, typeLayoutT, typeLayoutT)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 9, 4, typeLayoutT, typeLayoutT)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Updates `* dst` to the min value of `val` and the old value (unsigned comparison)
 */
public fun <T> atomicUmin(dst: MutPtr, value: T, order: Ordering, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `atomicUmin`
    return when (order) {
        Relaxed -> atomicRmwWithLayout(dst.rawAddress, value, 10, 0, typeLayoutT, typeLayoutT)
        Acquire -> atomicRmwWithLayout(dst.rawAddress, value, 10, 2, typeLayoutT, typeLayoutT)
        Release -> atomicRmwWithLayout(dst.rawAddress, value, 10, 1, typeLayoutT, typeLayoutT)
        AcqRel -> atomicRmwWithLayout(dst.rawAddress, value, 10, 3, typeLayoutT, typeLayoutT)
        SeqCst -> atomicRmwWithLayout(dst.rawAddress, value, 10, 4, typeLayoutT, typeLayoutT)
    }
}

// even without panics, this helps for Miri backtraces

/**
 * An atomic fence.
 *
 * Fences create synchronization between themselves and atomic operations or fences in other
 * threads. It can be helpful to think of a fence as preventing the compiler and CPU from
 * reordering certain types of memory operations around it, but that is a simplified model which
 * fails to capture some of the nuances.
 *
 * There are 3 different ways to use an atomic fence:
 *
 * - atomic - fence synchronization: an atomic operation with (at least) [Release] ordering
 *   semantics synchronizes with a fence with (at least) [Acquire] ordering semantics.
 * - fence - atomic synchronization: a fence with (at least) [Release] ordering semantics
 *   synchronizes with an atomic operation with (at least) [Acquire] ordering semantics.
 * - fence - fence synchronization: a fence with (at least) [Release] ordering semantics
 *   synchronizes with a fence with (at least) [Acquire] ordering semantics.
 *
 * These 3 ways complement the regular, fence-less, atomic - atomic synchronization.
 *
 * ## Atomic - Fence
 *
 * An atomic operation on one thread will synchronize with a fence on another thread when:
 *
 * -   on thread 1:
 *     -   an atomic operation `X` with (at least) [Release] ordering semantics on some atomic
 *         object `m`,
 *
 * -   is paired on thread 2 with:
 *     -   an atomic read `Y` with any order on `m`,
 *     -   followed by a fence `B` with (at least) [Acquire] ordering semantics.
 *
 * This provides a happens-before dependence between X and B.
 *
 * ```text
 *     Thread 1                                          Thread 2
 *
 * m.store(3, Release); X ---------
 *                                |
 *                                |
 *                                -------------> Y  if m.load(Relaxed) == 3 {
 *                                               B      fence(Acquire);
 *                                                      ...
 *                                                  }
 * ```
 *
 * ## Fence - Atomic
 *
 * A fence on one thread will synchronize with an atomic operation on another thread when:
 *
 * -   on thread:
 *     -   a fence `A` with (at least) [Release] ordering semantics,
 *     -   followed by an atomic write `X` with any ordering on some atomic object `m`,
 *
 * -   is paired on thread 2 with:
 *     -   an atomic operation `Y` with (at least) [Acquire] ordering semantics.
 *
 * This provides a happens-before dependence between A and Y.
 *
 * ```text
 *     Thread 1                                          Thread 2
 *
 * fence(Release);      A
 * m.store(3, Relaxed); X ---------
 *                                |
 *                                |
 *                                -------------> Y  if m.load(Acquire) == 3 {
 *                                                      ...
 *                                                  }
 * ```
 *
 * ## Fence - Fence
 *
 * A fence on one thread will synchronize with a fence on another thread when:
 *
 * -   on thread 1:
 *     -   a fence `A` which has (at least) [Release] ordering semantics,
 *     -   followed by an atomic write `X` with any ordering on some atomic object `m`,
 *
 * -   is paired on thread 2 with:
 *     -   an atomic read `Y` with any ordering on `m`,
 *     -   followed by a fence `B` with (at least) [Acquire] ordering semantics.
 *
 * This provides a happens-before dependence between A and B.
 *
 * ```text
 *     Thread 1                                          Thread 2
 *
 * fence(Release);      A --------------
 * m.store(3, Relaxed); X ---------    |
 *                                |    |
 *                                |    |
 *                                -------------> Y  if m.load(Relaxed) == 3 {
 *                                     |-------> B      fence(Acquire);
 *                                                      ...
 *                                                  }
 * ```
 *
 * ## Mandatory Atomic
 *
 * Note that in the examples above, it is crucial that the access to `m` are atomic. Fences cannot
 * be used to establish synchronization between non-atomic accesses in different threads. However,
 * thanks to the happens-before relationship, any non-atomic access that happen-before the atomic
 * operation or fence with (at least) [Release] ordering semantics are now also properly
 * synchronized with any non-atomic accesses that happen-after the atomic operation or fence with
 * (at least) [Acquire] ordering semantics.
 *
 * ## Memory Ordering
 *
 * A fence which has [SeqCst] ordering, in addition to having both [Acquire] and [Release]
 * semantics, participates in the global program order of the other [SeqCst] operations and/or
 * fences.
 *
 * Accepts [Acquire], [Release], [AcqRel] and [SeqCst] orderings.
 *
 * # Panics
 *
 * Panics if `order` is [Relaxed].
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 * import std.sync.atomic.fence
 *
 * // A mutual exclusion primitive based on spinlock.
 * open class Mutex(public var flag: AtomicBool) {
 *     public open fun new(): Mutex {
 *         return Mutex(flag = AtomicBool.new(false))
 *     }
 *
 *     public open fun lock() {
 *         // Wait until the old value is `false`.
 *         while (this.flag.compareExchangeWeak(false, true, Ordering.Relaxed, Ordering.Relaxed).isErr()) {}
 *         // This fence synchronizes-with store in `unlock`.
 *         fence(Ordering.Acquire)
 *     }
 *
 *     public open fun unlock() {
 *         this.flag.store(false, Ordering.Release)
 *     }
 * }
 * ```
 */
public fun fence(order: Ordering) {
    // SAFETY: using an atomic fence is safe.
    when (order) {
        Acquire -> kmAtomicFence(2)
        Release -> kmAtomicFence(1)
        AcqRel -> kmAtomicFence(3)
        SeqCst -> kmAtomicFence(4)
        Relaxed -> error("there is no such thing as a relaxed fence")
    }
}

// even without panics, this helps for Miri backtraces

/**
 * An atomic fence for synchronization within a single thread.
 *
 * Like [fence], this function establishes synchronization with other atomic operations and
 * fences. However, unlike [fence], `compilerFence` only establishes synchronization with
 * operations *in the same thread*. This may at first sound rather useless, since code within a
 * thread is typically already totally ordered and does not need any further synchronization.
 * However, there are cases where code can run on the same thread without being synchronized:
 * - The most common case is that of a *signal handler*: a signal handler runs in the same thread
 *   as the code it interrupted, but it is not synchronized with that code. `compilerFence`
 *   can be used to establish synchronization between a thread and its signal handler, the same way
 *   that `fence` can be used to establish synchronization across threads.
 * - Similar situations can arise in embedded programming with interrupt handlers, or in custom
 *   implementations of preemptive green threads. In general, `compilerFence` can establish
 *   synchronization with code that is guaranteed to run on the same hardware CPU.
 *
 * See [fence] for how a fence can be used to achieve synchronization. Note that just like
 * [fence], synchronization still requires atomic operations to be used in both threads -- it is
 * not possible to perform synchronization entirely with fences and non-atomic operations.
 *
 * `compilerFence` does not emit any machine code. However, note that `compilerFence` is also
 * *not* a "compiler barrier". It can be helpful to think of a `compilerFence` as preventing the
 * compiler from reordering certain types of memory operations around it, but that is a simplified
 * model which fails to capture some of the nuances. The only actual guarantee made by
 * `compilerFence` is establishing synchronization with signal handlers and similar kinds of code,
 * under the rules described in the [fence] documentation.
 *
 * `compilerFence` corresponds to [atomicSignalFence] in C and C++.
 *
 * [atomicSignalFence]: https://en.cppreference.com/w/cpp/atomic/atomicSignalFence
 *
 * # Panics
 *
 * Panics if `order` is [Relaxed].
 *
 * # Examples
 *
 * Without the two `compilerFence` calls, the read of `IMPORTANT_VARIABLE` in `signalHandler`
 * is *undefined behavior* due to a data race, despite everything happening in a single thread.
 * This is because the signal handler is considered to run concurrently with its associated
 * thread, and explicit synchronization is required to pass data between a thread and its
 * signal handler. The code below uses two `compilerFence` calls to establish the usual
 * release-acquire synchronization pattern (see [fence] for an image).
 *
 * ```kotlin
 * import std.sync.atomic.AtomicBool
 * import std.sync.atomic.Ordering
 * import std.sync.atomic.compilerFence
 *
 * var IMPORTANT_VARIABLE: Int = 0
 * var IS_READY: AtomicBool = AtomicBool.new(false)
 * fun main() {
 *     IMPORTANT_VARIABLE = 42
 *     // Marks earlier writes as being released with future relaxed stores.
 *     compilerFence(Ordering.Release)
 *     IS_READY.store(true, Ordering.Relaxed)
 * }
 * fun signalHandler() {
 *     if (IS_READY.load(Ordering.Relaxed)) {
 *         // Acquires writes that were released with relaxed stores that we read from.
 *         compilerFence(Ordering.Acquire)
 *         check(IMPORTANT_VARIABLE == 42)
 *     }
 * }
 * ```
 */
public fun compilerFence(order: Ordering) {
    // SAFETY: using an atomic fence is safe.
    when (order) {
        Acquire -> kmAtomicSignalFence(2)
        Release -> kmAtomicSignalFence(1)
        AcqRel -> kmAtomicSignalFence(3)
        SeqCst -> kmAtomicSignalFence(4)
        Relaxed -> error("there is no such thing as a relaxed fence")
    }
}

public fun AtomicBool.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

public fun AtomicPtr.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.load(Ordering.Relaxed)).toString()); Result.Ok(Unit) }

/**
 * Signals the processor that it is inside a busy-wait spin-loop ("spin lock").
 *
 * This function is deprecated in favor of [hint.spinLoop].
 */
public fun spinLoopHint() {
    spinLoop()
}

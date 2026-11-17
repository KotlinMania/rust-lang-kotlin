// port-lint: source boxed.rs
package io.github.kotlinmania.rustlang.alloc.boxed

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.StaticAllocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.error.Request
import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFn
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFnMut
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFnOnce
import io.github.kotlinmania.rustlang.core.ops.coroutine.Coroutine
import io.github.kotlinmania.rustlang.core.ops.coroutine.CoroutineState
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.deref.LegacyReceiver
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.pin.PinSafePointer
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dangling
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castUninit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.withMetadataOf
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

// The `Holder<T>` type for heap allocation.
//
// [Box.T], casually referred to as a `box`, provides the simplest form of
// heap allocation in Rust. Boxes provide ownership for this allocation, and
// drop their contents when they go out of scope. Boxes also ensure that they
// never allocate more than `Long.MAX` bytes.
//
// # Examples
//
// Move a value from the stack to the heap by creating a [Box]:
//
// ```kotlin
// val value: UByte = 5u
// val boxed: UByte = value
// ```
//
// Move a value from a [Box] back to the stack by [dereferencing]:
//
// ```kotlin
// val boxed: UByte = 5u
// val value: UByte = boxed
// ```
//
// Creating a recursive data structure:
//
// ```kotlin
// sealed class List<T> {
//     public data class Cons<T>(public var field0: T, public var field1: List<T>) : List<T>()
//     public class Nil<T>() : List<T>()
// }
// val list: List<Int> = List.Cons(1, List.Cons(2, List.Nil))
// println("$list")
// ```
//
// This will print `Cons(1, Cons(2, Nil))`.
//
// Recursive structures must be boxed, because if the definition of `Cons`
// looked like this:
//
// ```compile_fail,E0072
// # enum List<T> {
// Cons(T, List<T>),
// # }
// ```
//
// It wouldn't work. This is because the size of a `List` depends on how many
// elements are in the list, and so we don't know how much memory to allocate
// for a `Cons`. By introducing a [Box.T], which has a defined size, we know how
// big `Cons` needs to be.
//
// # Memory layout
//
// For non-zero-sized values, a [Box.T.A] will use its allocator of type `A` for its allocation.
// It is valid to convert both ways between a `Holder` and a raw pointer allocated with `A`, so long as:
// - the size of the pointee, as reported by [sizeOfVal], is non-zero,
// - the [Layout.forValue] of the pointee [*fits*](Allocator#memory-fitting) the memory block, and
// - the pointee is a valid instance of `T` (for the given metadata of the pointer).
//
// For zero-sized values, a [Box.T.A] may use any sufficiently aligned non-null pointer.
// While it is sound to convert a zero-sized allocation from `A` into a `Holder`, as
// long as it is backed by a valid value of `T`, doing so may lead to memory leaks, as
// the destructor of [Box] will not call [`deallocate`](Allocator.deallocate)
// on zero-sized values. It is recommended to use [ptr.dangling] instead.
//
// Likewise, manually deallocating the raw pointer acquired from a `Holder` is only sound
// if the size of the pointee is non-zero, as zero-sized values may be represented by
// a pointer that is not [*currently allocated*] by that allocator. It is recommended to
// pair [Box.intoRawWithAllocator] with [Box.fromRawIn], rather than
// doing this.
//
// Note that even the [Global] allocator might not use dangling pointers for
// zero-sized allocations, even though *most* implementations do this.
//
// So long as `T: Sized`, a `Holder<T>` is guaranteed to be represented
// as a single pointer and is also ABI-compatible with C pointers
// (i.e. the C type `T *`). This means that if you have extern "C"
// Rust functions that will be called from C, you can define those
// Rust functions using `Holder<T>` types, and use `T *` as corresponding
// type on the C side. As an example, consider this C header which
// declares functions that create and destroy some kind of `Foo`
// value:
//
// ```c
// / * C header * /
//
// / * Returns ownership to the caller * /
// struct Foo* fooNew(void);
//
// / * Takes ownership from the caller; no-op when invoked with null * /
// void fooDelete(struct Foo*);
// ```
//
// These two functions might be implemented in Rust as follows. Here, the
// `class Foo *` type from C is translated to `Holder<Foo>`, which captures
// the ownership constraints. Note also that the nullable argument to
// `fooDelete` is represented in Rust as `Option<Holder<Foo>>`, since `Holder<Foo>`
// cannot be null.
//
// ```kotlin
// @repr(C)
// open class Foo
// @unchecked(noMangle)
// fun fooNew(): Foo {
//     return Foo()
// }
// @unchecked(noMangle)
// fun fooDelete(arg0: Foo?) {}
// ```
//
// Even though `Holder<T>` has the same representation and C ABI as a C pointer,
// this does not mean that you can convert an arbitrary `T *` into a `Holder<T>`
// and expect things to work. `Holder<T>` values will always be fully aligned,
// non-null pointers. Moreover, the destructor for `Holder<T>` will attempt to
// free the value with the global allocator. In general, the best practice
// is to only use `Holder<T>` for pointers that originated from the global
// allocator.
//
// **Important.** At least at present, you should avoid using
// `Holder<T>` types for functions that are defined in C but invoked
// from Rust. In those cases, you should directly mirror the C types
// as closely as possible. Using types like `Holder<T>` where the C
// definition is just using `T *` can lead to undefined behavior, as
// described in [rust-lang/unchecked-code-guidelines#198][ucg#198].
//
// # Considerations for unchecked code
//
// **Warning: This section is not normative and is subject to change, possibly
// being relaxed in the future! It is a simplified summary of the rules
// currently implemented in the compiler.**
//
// The aliasing rules for `Holder<T>` are the same as for `T`. `Holder<T>`
// asserts uniqueness over its content. Using raw pointers derived from a box
// after that box has been mutated through, moved or borrowed as `T`
// is not allowed. For more guidance on working with box from unchecked code, see
// [rust-lang/unchecked-code-guidelines#326][ucg#326].
//
// # Editions
//
// A special case exists for the implementation of `IntoIterator` for arrays on the Rust 2021
// edition, as documented [here][array]. Unfortunately, it was later found that a similar
// workaround should be added for boxed slices, and this was applied in the 2024 edition.
//
// Specifically, `IntoIterator` is implemented for `Holder<[T]>` on all editions, but specific calls
// to `intoIter()` for boxed slices will defer to the slice implementation on editions before
// 2024:
//
// ```kotlin
// // Rust 2015, 2018, and 2021:
// val boxedSlice: List<Int> = mutableListOf(0, 3).intoBoxedSlice()
// // This creates a slice iterator, producing references to each value.
// for (item in boxedSlice.mapIndexed { index, item -> Pair(index, item) }) {
//     val (i: Int, x: Int) = item
//     println("boxedSlice[$i] = $x")
// }
// // The `boxedSliceIntoIter` lint suggests this change for future compatibility:
// for (item1 in boxedSlice.mapIndexed { index, item -> Pair(index, item) }) {
//     val (i1: Int, x1: Int) = item1
//     println("boxedSlice[$i] = $x")
// }
// // You can explicitly iterate a boxed slice by value using `IntoIterator.intoIter`
// for (item2 in IntoIterator.intoIter(boxedSlice).mapIndexed { index, item -> Pair(index, item) }) {
//     val (i2: Int, x2: Int) = item2
//     println("boxedSlice[$i] = $x")
// }
// ```
//
// Similar to the array implementation, this may be modified in the future to remove this override,
// and it's best to avoid relying on this edition-dependent behavior if you wish to preserve
// compatibility with future versions of the compiler.
//
// [*currently allocated*]: Allocator#currently-allocated-memory
// [ucg#198]: https://github.com/rust-lang/unchecked-code-guidelines/issues/198
// [ucg#326]: https://github.com/rust-lang/unchecked-code-guidelines/issues/326
// [Box.T.fromRaw]: Box.fromRaw
// [valid]: ptr#safety

public typealias CallOnceFuture<F> = F.CallOnceFuture

public typealias CallRefFuture<F> = F.CallRefFuture

/**
 * An internal wrapper for the pointer + `PhantomData` inside a `Holder`.
 * This type has no semantic meaning. It only exists because the layout of
 * `Holder` is hard-coded into the compiler, and because moving the
 * auto trait impls to `Holder` would cause regressions (#162850).
 */
public open class BoxRaw<T>(
    public var pointer: NonNull,
    public var marker: Unit,
) {
    public open fun <U> cast(): BoxRaw<U> = BoxRaw(pointer = this.pointer.cast(), marker = Unit)

    public open fun asNonNullPtr(): NonNull = this.pointer

    public open fun asPtr(): MutPtr = this.pointer.asPtr()

    public open fun clone(): BoxRaw<T> = BoxRaw(this.pointer, this.marker)

    public companion object {
        /**
         * # Safety
         * `ptr` must be non-null
         */
        public fun <T> newUnchecked(ptr: MutPtr): BoxRaw<T> {
            // SAFETY: Upheld by the caller
            return BoxRaw(pointer = NonNull.newUnchecked(ptr), marker = Unit)
        }

        public fun <T> dangling(): BoxRaw<T> = BoxRaw(pointer = NonNull.dangling(), marker = Unit)
    }
}

public fun <T> fromNonNullT(value: BoxRaw<T>): NonNull = value.asNonNullPtr()

// The declaration of the `Holder` struct must be kept in sync with the
// compiler or ICEs will happen.

/**
 * A pointer type that uniquely owns a heap allocation of type `T`.
 *
 * See the [module-level documentation](../../std/boxed/index.html) for more.
 */
public open class Box<T, A : Allocator>(
    public var field0: BoxRaw<T>,
    public var field1: A,
) : DerefPure, LegacyReceiver, PinSafePointer, Borrow<T>, BorrowMut<T>, AsRef<T>, AsMut<T> {
    public open fun drop() {
        // the T in the Holder is dropped by the compiler before the destructor is run
        val ptr = this.field0
        // SAFETY: The construction site of the unsized box had ensured for us that the
        // allocation was made with a valid layout (the size does not overflow an isize,
        // possibly because the size of the type is 0).
        val layout = Layout.forValueRaw(ptr.asPtr())
        if (layout.size() != 0) {
            // SAFETY: Any nonzero allocation would have been created with the allocator
            // of this box and `layout` would fit that allocation. We also are the only ones
            // responsible for doing this deallocation and know that the pointer must be valid.
            run {
                this.field1.deallocate(From.from(ptr.cast()), layout)
            }
        }
    }

    // NB: This is not `AllocatorClone` since we don't care about allocator
    // equivalence when cloning boxes.

    /**
     * Returns a new box with a `clone()` of this box's contents.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
     *
     * val x = 5
     * val y = x.clone()
     * // The value is the same
     * check(x == y)
     * // But they are unique objects
     * check(constPtrFromRustCast(x) != constPtrFromRustCast(y))
     * ```
     */
    public open fun clone(): Box<T, A> {
        // Pre-allocate memory to allow writing the cloned value directly.
        val boxed = Box.newUninitIn(this.field1.clone())
        // SAFETY: Destination pointer is valid and will then become initialised.
        return run {
            Box(this.field0, this.field1).cloneToUninit(boxed.asMutPtr().cast())
            boxed.assumeInit()
        }
    }

    /**
     * Copies `source`s contents into `self` without creating a new allocation.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     *
     * val x = 5
     * var y = 10
     * val yp: ConstPtr = y
     * y.cloneFrom(x)
     * // The value is the same
     * check(x == y)
     * // And no allocation occurred
     * check(yp == y)
     * ```
     */
    public open fun cloneFrom(source: Box<T, A>) {
        this.cloneFrom(source)
    }

    public open fun eq(other: Box<T, A>): Boolean = PartialEq.eq(this, other)

    public open fun ne(other: Box<T, A>): Boolean = PartialEq.ne(this, other)

    public open fun partialCmp(other: Box<T, A>): Ordering? = PartialOrd.partialCmp(this, other)

    public open fun lt(other: Box<T, A>): Boolean = PartialOrd.lt(this, other)

    public open fun le(other: Box<T, A>): Boolean = PartialOrd.le(this, other)

    public open fun ge(other: Box<T, A>): Boolean = PartialOrd.ge(this, other)

    public open fun gt(other: Box<T, A>): Boolean = PartialOrd.gt(this, other)

    public open fun cmp(other: Box<T, A>): Ordering = Ord.cmp(this, other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun finish(): ULong = this.finish()

    public open fun write(bytes: List<UByte>) {
        this.write(bytes)
    }

    public open fun writeU8(i: UByte) {
        this.writeU8(i)
    }

    public open fun writeU16(i: UShort) {
        this.writeU16(i)
    }

    public open fun writeU32(i: UInt) {
        this.writeU32(i)
    }

    public open fun writeU64(i: ULong) {
        this.writeU64(i)
    }

    public open fun writeU128(i: U128) {
        this.writeU128(i)
    }

    public open fun writeUsize(i: Int) {
        this.writeUsize(i)
    }

    public open fun writeI8(i: Byte) {
        this.writeI8(i)
    }

    public open fun writeI16(i: Short) {
        this.writeI16(i)
    }

    public open fun writeI32(i: Int) {
        this.writeI32(i)
    }

    public open fun writeI64(i: Long) {
        this.writeI64(i)
    }

    public open fun writeI128(i: I128) {
        this.writeI128(i)
    }

    public open fun writeIsize(i: Int) {
        this.writeIsize(i)
    }

    public open fun writeLengthPrefix(len: Int) {
        this.writeLengthPrefix(len)
    }

    public open fun writeStr(s: String) {
        this.append(s)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Error> {
        val ptr: ConstPtr = this
        return run { f.append((ptr).toString()); Result.Ok<Unit, Error>(Unit) }
    }

    public open fun deref(): T = this

    public open fun derefMut(): T = this

    // A pointer can only be pin safe if it does not implement certain safe traits
    // maliciously. Since `Holder` is fundamental, downstream crates may be able to
    // implement those traits for `Holder<LocalType>`, so we must carefully check that
    // this is not a problem for each trait.
    //
    // The `Holder` type always implements `Deref` and `DerefMut`, so despite being
    // fundamental, downstream crates cannot implement these traits for
    // `Holder<LocalType>`.
    //
    // Conversely, downstream crates are able to implement `Clone`, `Debug`, and
    // `Display` for `Holder<LocalType>` as long as `LocalType` does not implement
    // said trait. However, the `Holder<T>` type does not treat the existence of an
    // `Holder<T>` as evidence that the `T` is not pinned, so this is not
    // problematic.
    //
    // Finally, even if downstream crates provide their own implementation of
    // `Clone` for `Holder<LocalType>`, it is not problematic for the cloned box to be
    // wrapped in `Pin`, since the same conversion could have been carried out
    // safely as `Holder.pin((* p).clone())`.

    public open fun borrow(): T = this

    public open fun borrowMut(): T = this

    public open fun asRef(): T = this

    public open fun asMut(): T = this

    // Nota bene
    //
    // We could have chosen not to add this impl, and instead have written a
    // function of Pin<Holder<T>> to Pin<T>. Such a function would not be sound,
    // because Holder<T> implements Unpin even when T does not, as a result of
    // this impl.
    //
    // We chose this API instead of the alternative for a few reasons:
    // - Logically, it is helpful to understand pinning in regard to the
    // memory region being pointed to. For this reason none of the
    // standard library pointer types support projecting through a pin
    // (Holder<T> is the only pointer type in std for which this would be
    // safe.)
    // - It is in practice very useful to have Holder<T> be unconditionally
    // Unpin because of trait objects, for which the structural auto
    // trait functionality does not apply (e.g., Holder<dynamic Foo> would
    // otherwise not be Unpin).
    //
    // Another type with the same semantics as Holder but only a conditional
    // implementation of `Unpin` (where `T: Unpin`) would be valid/safe, and
    // could have a method to project a Pin<T> from it.
    //

    public companion object {
        /**
         * Allocates memory in the given allocator then places `x` into it.
         *
         * This doesn't actually allocate if `T` is zero-sized.
         * See the [memory layout] section for more details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * val five = Holder.newIn(5, System)
         * ```
         *
         * [memory layout]: self#memory-layout
         */
        public fun <T, A : Allocator> newIn(x: T, alloc: A): Box<T, A> {
            val boxed = Box.newUninitIn(alloc)
            boxed.write(x)
            // SAFETY: Initialised by the above.
            return boxed.assumeInit()
        }

        /**
         * Allocates memory in the given allocator then places `x` into it,
         * returning an error if the allocation fails
         *
         * This doesn't actually allocate if `T` is zero-sized.
         * See the [memory layout] section for more details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val five = Holder.tryNewIn(5, System).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         */
        public fun <T, A : Allocator> tryNewIn(x: T, alloc: A): Result<Box<T, A>, AllocError> {
            val boxed = when (val tryResult = Box.tryNewUninitIn(alloc)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<T, A>, AllocError>(tryResult.errValue()) }
            boxed.write(x)
            // SAFETY: Initialised by the above.
            return Result.Ok<Box<T, A>, AllocError>(boxed.assumeInit())
        }

        /**
         * Constructs a new box with uninitialized contents in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     var five = Holder.newUninitIn(System)
         *     // Deferred initialization:
         *     five.write(5)
         *     val five1 = five.assumeInit()
         *     check(five1 == 5)
         * }
         * ```
         */
        public fun <T, A : Allocator> newUninitIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<MaybeUninit<T>, A> {
            val layout = Layout.new<MaybeUninit<T>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
            // NOTE: Prefer match over unwrapOrElse since closure sometimes not inlineable.
            // That would make code size bigger.
            return run {
                val matchVal = Box.tryNewUninitIn(alloc)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val m = matchVal.okValue()
                    m
                }; is Result.Err<*, *> -> {
                    handleAllocError(layout)
                } }
            }
        }

        /**
         * Constructs a new box with uninitialized contents in the provided allocator,
         * returning an error if the allocation fails
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     var five = Holder.tryNewUninitIn(System).getOrElse { return Result.failure(it) }
         *     // Deferred initialization:
         *     five.write(5)
         *     val five1 = five.assumeInit()
         *     check(five1 == 5)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryNewUninitIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<MaybeUninit<T>, A>, AllocError> {
            val ptr =
                if (((typeLayoutT).sizeBytes == 0L)) {
                    NonNull.dangling()
                } else {
                    val layout = Layout.new<MaybeUninit<T>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (val tryResult = alloc.allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<MaybeUninit<T>, A>, AllocError>(tryResult.errValue()) }.cast()
                }
            // SAFETY: Pointer is nonnull and matches the allocator.
            return Result.Ok<Box<MaybeUninit<T>, A>, AllocError>(Box.fromRawIn(ptr.asPtr(), alloc))
        }

        /**
         * Constructs a new `Holder` with uninitialized contents, with the memory
         * being filled with `0` bytes in the provided allocator.
         *
         * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
         * of this method.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val zero = Holder.newZeroedIn(System)
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> newZeroedIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<MaybeUninit<T>, A> {
            val layout = Layout.new<MaybeUninit<T>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
            // NOTE: Prefer match over unwrapOrElse since closure sometimes not inlineable.
            // That would make code size bigger.
            return run {
                val matchVal = Box.tryNewZeroedIn(alloc)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val m = matchVal.okValue()
                    m
                }; is Result.Err<*, *> -> {
                    handleAllocError(layout)
                } }
            }
        }

        /**
         * Constructs a new `Holder` with uninitialized contents, with the memory
         * being filled with `0` bytes in the provided allocator,
         * returning an error if the allocation fails,
         *
         * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
         * of this method.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val zero = Holder.tryNewZeroedIn(System).getOrElse { return Result.failure(it) }
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> tryNewZeroedIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<MaybeUninit<T>, A>, AllocError> {
            val ptr =
                if (((typeLayoutT).sizeBytes == 0L)) {
                    NonNull.dangling()
                } else {
                    val layout = Layout.new<MaybeUninit<T>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (val tryResult = alloc.allocateZeroed(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<MaybeUninit<T>, A>, AllocError>(tryResult.errValue()) }.cast()
                }
            // SAFETY: Pointer is nonnull and matches the allocator.
            return Result.Ok<Box<MaybeUninit<T>, A>, AllocError>(Box.fromRawIn(ptr.asPtr(), alloc))
        }

        /**
         * Constructs a new `Pin<Holder<T, A>>`. If `T` does not implement [Unpin], then
         * `x` will be pinned in memory and unable to be moved.
         *
         * Constructing and pinning of the `Holder` can also be done in two steps: `Holder.pinIn(x, alloc)`
         * does the same as <code>[Box.intoPin]\([Box.newIn]\(x, alloc))</code>. Consider using
         * [`intoPin`](Box.intoPin) if you already have a `Holder<T, A>`, or if you want to
         * construct a (pinned) `Holder` in a different way than with [Box.newIn].
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x = Holder.pinIn(1, System)
         * }
         * ```
         */
        public fun <T, A : Allocator> pinIn(x: T, alloc: A): Pin<Box<T, A>> where A : StaticAllocator = Box.intoPin(Box.newIn(x, alloc))

        /**
         * Converts a `Holder<T>` into a `Holder<[T]>`
         *
         * This conversion does not allocate on the heap and happens in place.
         */
        public fun <T, A : Allocator> intoBoxedSlice(boxed: Box<T, A>): Box<List<T>, A> {
            val (raw, alloc) = Box.intoRawWithAllocator(boxed)
            // SAFETY: A pointer to T is also a valid pointer to [T; 1].
            return Box.fromRawIn(mutPtrFromRustCast(raw), alloc)
        }

        /**
         * Consumes the `Holder`, returning the wrapped value.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(boxIntoInner)
         * public fun doctestEntry() {
         *     val c = 5
         *     check(Holder.intoInner(c) == 5)
         * }
         * ```
         */
        public fun <T, A : Allocator> intoInner(boxed: Box<T, A>): T = boxed

        /**
         * Consumes the `Holder` without consuming its allocation, returning the wrapped value and a `Holder`
         * to the uninitialized memory where the wrapped value used to live.
         *
         * This can be used together with [`write`](Box.write) to reuse the allocation for multiple
         * boxed values.
         *
         * # Examples
         *
         * ```kotlin
         * val c = 5
         * // take the value out of the box
         * val (value, uninit) = Holder.take(c)
         * check(value == 5)
         * // reuse the box for a second value
         * val c1 = Holder.write(uninit, 6)
         * check(c1 == 6)
         * ```
         */
        public fun <T, A : Allocator> take(boxed: Box<T, A>): Pair<T, Box<MaybeUninit<T>, A>> {
            // SAFETY: Reading out an initialised value & leaving behind a
            // box with uninit contents.
            return run {
                val (raw, alloc) = Box.intoNonNullWithAllocator(boxed)
                val value = raw.read()
                val uninit = Box.fromNonNullIn(raw.castUninit(), alloc)
                Pair(value, uninit)
            }
        }

        /**
         * Maps the value in a box, reusing the allocation if possible.
         *
         * `f` is called on the value in the box, and the result is returned, also boxed.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.map(b, f)` instead of `b.map(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * val b = 7
         * val new = Holder.map(b, { i -> i + 7 })
         * check(new == 14)
         * ```
         */
        public fun <T, A : Allocator, U> map(thisRef: Box<T, A>, f: (T) -> U, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): Box<U, A> {
            val (value, allocation) = Box.take(thisRef)
            val (raw, alloc) = Box.intoNonNullWithAllocator(allocation)
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU)) {
                // SAFETY: We checked that the memory requirements are the same for both types
                // and `raw` is already a valid pointer for the requisite memory.
                val allocation1 = Box.fromNonNullIn(raw.cast(), alloc)
                Box.write(allocation1, f(value))
            } else {
                if (sizeOf<T>(typeLayoutT = typeLayoutT) != 0) {
                    // SAFETY: `raw` isn't dangling since it points to a non-zero-sized
                    // allocation and is never used again after this point.
                    alloc.deallocate(raw.cast(), Layout.forValue(value))
                }
                Box.newIn(f(value), alloc)
            }
        }

        /**
         * Attempts to map the value in a box, reusing the allocation if possible.
         *
         * `f` is called on the value in the box, and if the operation succeeds, the result is
         * returned, also boxed.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.tryMap(b, f)` instead of `b.tryMap(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(smartPointerTryMap)
         * public fun doctestEntry() {
         *     val b = 7
         *     val new = Holder.tryMap(b, UInt.tryFrom).unwrap()
         *     check(new == 7)
         * }
         * ```
         */
        public fun <Output, Residual, T, A : Allocator, R> tryMap(thisRef: Box<T, A>, f: (T) -> R, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): TryType where R : Try<Output, Residual> {
            val (value, allocation) = Box.take(thisRef)
            val (raw, alloc) = Box.intoNonNullWithAllocator(allocation)
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>()) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>())) {
                val allocation1 = Box.fromNonNullIn(raw.cast(), alloc)
                Box.write(allocation1, when (val tryResult = f(value)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
            } else {
                if (sizeOf<T>(typeLayoutT = typeLayoutT) != 0) {
                    // SAFETY: `raw` isn't dangling since it points to a non-zero-sized
                    // allocation and is never used again after this point.
                    alloc.deallocate(raw.cast(), Layout.forValue(value))
                }
                Box.newIn(when (val tryResult = f(value)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }, alloc)
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Allocates memory in the given allocator then clones `src` into it.
         *
         * This doesn't actually allocate if `src` is zero-sized.
         * See the [memory layout] section for more details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * public fun doctestEntry() {
         *     val hello: String = Holder.cloneFromRefIn("hello", System)
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         */
        public fun <T, A : Allocator> cloneFromRefIn(src: T, alloc: A): Box<T, A> {
            val layout = Layout.forValue<T>(src)
            return run {
                val matchVal = Box.tryCloneFromRefIn(src, alloc)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val bx = matchVal.okValue()
                    bx
                }; is Result.Err<*, *> -> {
                    handleAllocError(layout)
                } }
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Allocates memory in the given allocator then clones `src` into it, returning an error if allocation fails.
         *
         * This doesn't actually allocate if `src` is zero-sized.
         * See the [memory layout] section for more details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * public fun doctestEntry(): Result<Unit> {
         *     val hello: String = Holder.tryCloneFromRefIn("hello", System).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         */
        public fun <T, A : Allocator> tryCloneFromRefIn(src: T, alloc: A, cloneToUninitTFn: (T, Any?) -> Unit): Result<Box<T, A>, AllocError> {
            open class DeallocDropGuard<A : Allocator>(
                public var field0: Layout,
                public var field1: A,
                public var field2: NonNull,
            ) {
                public open fun drop() {
                    var layout by run {
                        fun <V> place(read: () -> V, write: (V) -> Unit): kotlin.properties.ReadWriteProperty<Any?, V> =
                            object : kotlin.properties.ReadWriteProperty<Any?, V> {
                                override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): V = read()
                                override fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: V) = write(value)
                            }
                        place({ this.field0 }, { this.field0 = it })
                    }
                    var alloc1 by run {
                        fun <V> place(read: () -> V, write: (V) -> Unit): kotlin.properties.ReadWriteProperty<Any?, V> =
                            object : kotlin.properties.ReadWriteProperty<Any?, V> {
                                override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): V = read()
                                override fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: V) = write(value)
                            }
                        place({ this.field1 }, { this.field1 = it })
                    }
                    var ptr by run {
                        fun <V> place(read: () -> V, write: (V) -> Unit): kotlin.properties.ReadWriteProperty<Any?, V> =
                            object : kotlin.properties.ReadWriteProperty<Any?, V> {
                                override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): V = read()
                                override fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: V) = write(value)
                            }
                        place({ this.field2 }, { this.field2 = it })
                    }
                    // SAFETY: `ptr` was allocated by `* alloc` with layout `layout`
                    run {
                        alloc1.deallocate(ptr, layout)
                    }
                }
            }
            val layout = Layout.forValue<T>(src)
            val (ptr1, guard) =
                if (layout.size() == 0) {
                    Pair(layout.danglingPtr(), null)
                } else {
                    // Safety: layout is non-zero-sized
                    val ptr = when (val tryResult = alloc.allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<T, A>, AllocError>(tryResult.errValue()) }.cast()
                    Pair(ptr, DeallocDropGuard(layout, alloc, ptr))
                }
            val ptr2 = ptr1.asPtr()
            // SAFETY: `* ptr` is newly allocated (or a ZST), correctly aligned to
            // `alignOfVal(src)`, and is valid for writes for `sizeOfVal(src)`.
            // If this panics, then `guard` will deallocate for us (if allocation occuured)
            run {
                cloneToUninitTFn(src, ptr2)
            }
            // Defuse the deallocate guard
            forget(guard)
            // SAFETY: We just initialized `* ptr` as a clone of `src`
            return Result.Ok<Box<T, A>, AllocError>(Box.fromRawIn(ptr2.withMetadataOf(src), alloc))
        }

        /**
         * Constructs a box from a raw pointer in the given allocator.
         *
         * After calling this function, the raw pointer is owned by the
         * resulting `Holder`. Specifically, the `Holder` destructor will call
         * the destructor of `T` and free the allocated memory, except
         * in the cases described in the [memory layout] section.
         *
         * # Safety
         *
         * This function is unchecked because improper use may lead to
         * memory problems. For example, a double-free may occur if the
         * function is called twice on the same raw pointer.
         *
         * The exact safety requirements are described in the [memory layout] section, together
         * with recommendations about how to handle the special case of zero-sized allocations
         * correctly.
         *
         * In particular, if the size of the pointee, as reported by [sizeOfVal], is
         * nonzero, then the raw pointer must point to a block of memory allocated by
         * `alloc`.
         * If the size of the pointee is zero, then the raw pointer can be any
         * sufficiently aligned non-null pointer, e.g. [ptr.dangling].
         *
         * Note that the [considerations for unchecked code] apply to all `Holder<T, A>` values.
         *
         * # Examples
         *
         * Recreate a `Holder` which was previously converted to a raw pointer
         * using [Box.intoRawWithAllocator]:
         * ```kotlin
         * import std.alloc.System
         *
         * val x = Holder.newIn(5, System)
         * val (ptr, alloc) = Holder.intoRawWithAllocator(x)
         * val x1 = Holder.fromRawIn(ptr, alloc)
         * ```
         * Manually create a `Holder` from scratch by using the system allocator:
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
         * import std.alloc.Allocator
         * import std.alloc.Layout
         * import std.alloc.System
         *
         * @feature(slicePtrGet)
         * public fun doctestEntry(): Result<Unit> {
         *     run {
         *         val ptr = mutPtrFromRustCast(System.allocate(Layout.new<Int>(typeLayoutT = PrimitiveLayouts.I32)).getOrElse { return Result.failure(it) }.asMutPtr())
         *         // In general .write is required to avoid attempting to destruct
         *         // the (uninitialized) previous contents of `ptr`, though for this
         *         // simple example `* ptr = 5` would have worked as well.
         *         (PrimitiveLayouts.I32).writeNative((ptr).rawAddress, 5)
         *         val x = Holder.fromRawIn(ptr, System)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         * [considerations for unchecked code]: self#considerations-for-unchecked-code
         */
        public fun <T, A : Allocator> fromRawIn(raw: MutPtr, alloc: A): Box<T, A> {
            // SAFETY: Upheld by caller.
            return Box(BoxRaw.newUnchecked(raw), alloc)
        }

        /**
         * Constructs a box from a `NonNull` pointer in the given allocator.
         *
         * After calling this function, the `NonNull` pointer is owned by
         * the resulting `Holder`. Specifically, the `Holder` destructor will call
         * the destructor of `T` and free the allocated memory, except
         * in the cases described in the [memory layout] section.
         *
         * # Safety
         *
         * This function is unchecked because improper use may lead to
         * memory problems. For example, a double-free may occur if the
         * function is called twice on the same raw pointer.
         *
         * The exact safety requirements are described in the [memory layout] section, together
         * with recommendations about how to handle the special case of zero-sized allocations
         * correctly.
         *
         * In particular, if the size of the pointee, as reported by [sizeOfVal], is
         * nonzero, then the raw pointer must point to a block of memory allocated by
         * `alloc`.
         * If the size of the pointee is zero, then the raw pointer can be any
         * sufficiently aligned pointer, e.g. [NonNull.dangling].
         *
         * Note that the [considerations for unchecked code] apply to all `Holder<T, A>` values.
         *
         * # Examples
         *
         * Recreate a `Holder` which was previously converted to a `NonNull` pointer
         * using [Box.intoNonNullWithAllocator]:
         * ```kotlin
         * import std.alloc.System
         *
         * val x = Holder.newIn(5, System)
         * val (nonNull, alloc) = Holder.intoNonNullWithAllocator(x)
         * val x1 = Holder.fromNonNullIn(nonNull, alloc)
         * ```
         * Manually create a `Holder` from scratch by using the system allocator:
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import std.alloc.Allocator
         * import std.alloc.Layout
         * import std.alloc.System
         *
         * run {
         *     val nonNull = System.allocate(Layout.new<Int>(typeLayoutT = PrimitiveLayouts.I32)).getOrElse { return Result.failure(it) }.cast()
         *     // In general .write is required to avoid attempting to destruct
         *     // the (uninitialized) previous contents of `nonNull`.
         *     nonNull.write(5)
         *     val x = Holder.fromNonNullIn(nonNull, System)
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         * [considerations for unchecked code]: self#considerations-for-unchecked-code
         */
        public fun <T, A : Allocator> fromNonNullIn(raw: NonNull, alloc: A): Box<T, A> {
            // SAFETY: guaranteed by the caller.
            return Box.fromRawIn(raw.asPtr(), alloc)
        }

        /**
         * Consumes the `Holder`, returning a wrapped raw pointer and the allocator.
         *
         * The pointer will be properly aligned and non-null.
         *
         * After calling this function, the caller is responsible for the
         * memory previously managed by the `Holder`. In particular, the
         * caller should properly destroy `T` and release the memory, taking
         * into account the [memory layout] used by `Holder`. The easiest way to
         * do this is to convert the raw pointer back into a `Holder` with the
         * [Box.fromRawIn] function, allowing the `Holder` destructor to perform
         * the cleanup.
         *
         * While manually deallocating the pointer through [deallocate]
         * is possible, you must only do this if the pointee's size, as reported by
         * [sizeOfVal], is non-zero (the `Holder` may be a dangling pointer
         * otherwise). See the [memory layout] section for more details.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.intoRawWithAllocator(b)` instead of `b.intoRawWithAllocator()`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         * Converting the raw pointer back into a `Holder` with [Box.fromRawIn]
         * for automatic cleanup:
         * ```kotlin
         * import std.alloc.System
         *
         * val x = Holder.newIn("Hello", System)
         * val (ptr, alloc) = Holder.intoRawWithAllocator(x)
         * val x1 = Holder.fromRawIn(ptr, alloc)
         * ```
         * Manual cleanup by explicitly running the destructor and deallocating
         * the memory:
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.alloc.Allocator
         * import std.alloc.Layout
         * import std.alloc.System
         * import std.ptr.NonNull
         *
         * val x = Holder.newIn("Hello", System)
         * val (ptr, alloc) = Holder.intoRawWithAllocator(x)
         * run {
         *     ptr.dropInPlace(ptr)
         *     val nonNull = NonNull.newUnchecked(ptr)
         *     alloc.deallocate(nonNull.cast(), Layout.new<String>(typeLayoutT = UnresolvedTypeLayout<String>()))
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         * [deallocate]: Allocator.deallocate
         */
        public fun <T, A : Allocator> intoRawWithAllocator(b: Box<T, A>): Pair<MutPtr, A> {
            val b1 = b
            // We carefully get the raw pointer out in a way that Miri's aliasing model understands what
            // is happening: using the primitive "deref" of `Holder`. In case `A` is *not* `Global`, we
            // want *no* aliasing requirements here!
            // In case `A` *is* `Global`, this does not quite have the right behavior; `intoRaw`
            // works around that.
            val ptr = b1
            // SAFETY: See above.
            val alloc = read(b1.second)
            return Pair(ptr, alloc)
        }

        /**
         * Consumes the `Holder`, returning a wrapped `NonNull` pointer and the allocator.
         *
         * The pointer will be properly aligned.
         *
         * After calling this function, the caller is responsible for the
         * memory previously managed by the `Holder`. In particular, the
         * caller should properly destroy `T` and release the memory, taking
         * into account the [memory layout] used by `Holder`. The easiest way to
         * do this is to convert the `NonNull` pointer back into a `Holder` with the
         * [Box.fromNonNullIn] function, allowing the `Holder` destructor to
         * perform the cleanup.
         *
         * While manually deallocating the pointer through [deallocate]
         * is possible, you must only do this if the pointee's size, as reported by
         * [sizeOfVal], is non-zero (the `Holder` may be a dangling pointer
         * otherwise). See the [memory layout] section for more details.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.intoNonNullWithAllocator(b)` instead of
         * `b.intoNonNullWithAllocator()`. This is so that there is no
         * conflict with a method on the inner type.
         *
         * # Examples
         * Converting the `NonNull` pointer back into a `Holder` with
         * [Box.fromNonNullIn] for automatic cleanup:
         * ```kotlin
         * import std.alloc.System
         *
         * val x = Holder.newIn("Hello", System)
         * val (nonNull, alloc) = Holder.intoNonNullWithAllocator(x)
         * val x1 = Holder.fromNonNullIn(nonNull, alloc)
         * ```
         * Manual cleanup by explicitly running the destructor and deallocating
         * the memory:
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.alloc.Allocator
         * import std.alloc.Layout
         * import std.alloc.System
         *
         * val x = Holder.newIn("Hello", System)
         * val (nonNull, alloc) = Holder.intoNonNullWithAllocator(x)
         * run {
         *     nonNull.dropInPlace()
         *     alloc.deallocate(nonNull.cast(), Layout.new<String>(typeLayoutT = UnresolvedTypeLayout<String>()))
         * }
         * ```
         *
         * [memory layout]: self#memory-layout
         * [deallocate]: Allocator.deallocate
         */
        public fun <T, A : Allocator> intoNonNullWithAllocator(b: Box<T, A>): Pair<NonNull, A> {
            val (ptr, alloc) = Box.intoRawWithAllocator(b)
            // SAFETY: `Holder` is guaranteed to be non-null.
            return Pair(NonNull.newUnchecked(ptr), alloc)
        }

        /**
         * Returns a raw mutable pointer to the `Holder`s contents.
         *
         * The caller must ensure that the `Holder` outlives the pointer this
         * function returns, or else it will end up dangling.
         *
         * This method guarantees that for the purpose of the aliasing model, this method
         * does not materialize a reference to the underlying memory, and thus the returned pointer
         * will remain valid when mixed with other calls to [asPtr], [asMutPtr], and [asNonNull].
         * Note that calling other methods that materialize references to the memory
         * may still invalidate this pointer.
         * See the example below for how this guarantee can be used.
         *
         * # Examples
         *
         * Due to the aliasing guarantee, the following code is legal:
         *
         * ```kotlin
         * run {
         *     var b = 0
         *     val ptr1 = Holder.asMutPtr(b)
         *     ptr1.write(1)
         *     val ptr2 = Holder.asMutPtr(b)
         *     ptr2.write(2)
         *     // Notably, the write to `ptr2` did *not* invalidate `ptr1`:
         *     ptr1.write(3)
         * }
         * ```
         *
         * [asMutPtr]: Self.asMutPtr
         * [asPtr]: Self.asPtr
         * [asNonNull]: Self.asNonNull
         */
        public fun <T, A : Allocator> asMutPtr(b: Box<T, A>): MutPtr {
            // This is a primitive deref, not going through `DerefMut`, and therefore not materializing
            // any references.
            return b
        }

        /**
         * Returns a raw pointer to the `Holder`s contents.
         *
         * The caller must ensure that the `Holder` outlives the pointer this
         * function returns, or else it will end up dangling.
         *
         * The caller must also ensure that the memory the pointer (non-transitively) points to
         * is never written to (except inside an `UnsafeCell`) using this pointer or any pointer
         * derived from it. If you need to mutate the contents of the `Holder`, use [asMutPtr].
         *
         * This method guarantees that for the purpose of the aliasing model, this method
         * does not materialize a reference to the underlying memory, and thus the returned pointer
         * will remain valid when mixed with other calls to [asPtr], [asMutPtr], and [asNonNull].
         * Note that calling other methods that materialize mutable references to the memory,
         * as well as writing to this memory, may still invalidate this pointer.
         * See the example below for how this guarantee can be used.
         *
         * # Examples
         *
         * Due to the aliasing guarantee, the following code is legal:
         *
         * ```kotlin
         * run {
         *     var v = 0
         *     val ptr1 = Holder.asPtr(v)
         *     val ptr2 = Holder.asMutPtr(v)
         *     val value = ptr2.read()
         *     // No write to this memory has happened yet, so `ptr1` is still valid.
         *     val value1 = ptr1.read()
         *     // However, once we do a write...
         *     ptr2.write(1)
         *     // ... `ptr1` is no longer valid.
         *     // This would be UB: let _val = ptr1.read();
         * }
         * ```
         *
         * [asMutPtr]: Self.asMutPtr
         * [asPtr]: Self.asPtr
         * [asNonNull]: Self.asNonNull
         */
        public fun <T, A : Allocator> asPtr(b: Box<T, A>): ConstPtr {
            // This is a primitive deref, not going through `DerefMut`, and therefore not materializing
            // any references.
            return b
        }

        /**
         * Returns a `NonNull` pointer to the `Holder`s contents.
         *
         * The caller must ensure that the `Holder` outlives the pointer this
         * function returns, or else it will end up dangling.
         *
         * This method guarantees that for the purpose of the aliasing model, this method
         * does not materialize a reference to the underlying memory, and thus the returned pointer
         * will remain valid when mixed with other calls to [asPtr], [asMutPtr], and [asNonNull].
         * Note that calling other methods that materialize references to the memory
         * may still invalidate this pointer.
         * See the example below for how this guarantee can be used.
         *
         * # Examples
         *
         * Due to the aliasing guarantee, the following code is legal:
         *
         * ```kotlin
         * @feature(boxAsNonNull)
         * public fun doctestEntry() {
         *     run {
         *         var b = 0
         *         val ptr1 = Holder.asNonNull(b)
         *         ptr1.write(1)
         *         val ptr2 = Holder.asNonNull(b)
         *         ptr2.write(2)
         *         // Notably, the write to `ptr2` did *not* invalidate `ptr1`:
         *         ptr1.write(3)
         *     }
         * }
         * ```
         *
         * [asMutPtr]: Self.asMutPtr
         * [asPtr]: Self.asPtr
         * [asNonNull]: Self.asNonNull
         */
        public fun <T, A : Allocator> asNonNull(b: Box<T, A>): NonNull {
            // SAFETY: `Holder` is guaranteed to be non-null.
            return NonNull.newUnchecked(Box.asMutPtr(b))
        }

        /**
         * Returns a reference to the underlying allocator.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.allocator(b)` instead of `b.allocator()`. This
         * is so that there is no conflict with a method on the inner type.
         */
        public fun <T, A : Allocator> allocator(b: Box<T, A>): A = b.field1

        /**
         * Consumes and leaks the `Holder`, returning a mutable reference,
         * `T`.
         *
         * Note that the type `T` must outlive the chosen scope `a`. If the type
         * has only static references, or none at all, then this may be chosen to be
         * `static`.
         *
         * This function is mainly useful for data that lives for the remainder of the program's life,
         * i.e., memory that is meant to leak. If the memory should eventually be freed, prefer to use
         * [Box.intoRaw] or [Box.intoNonNull] instead. Reconstructing ("unleaking") a `Holder` from
         * the mutable reference returned here (e.g. via [Box.fromRaw]) is only possible if the
         * allocator is `Global`, and even then it is a grey area (meaning it is possible under specific
         * circumstances but many seemingly harmless ways of doing it are undefined behavior) and should
         * be avoided.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Holder.leak(b)` instead of `b.leak()`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * Simple usage:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
         *
         * val x = 41
         * val staticRef: RustMutableReference<Int> = Holder.leak(x)
         * (staticRef).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
         * check((staticRef).read() == 42)
         * ```
         *
         * Unsized data:
         *
         * ```kotlin
         * val x = mutableListOf(1, 2, 3).intoBoxedSlice()
         * val staticRef = Holder.leak(x)
         * staticRef[0] = 4
         * check(staticRef == listOf(4, 2, 3))
         * ```
         */
        public fun <T, A : Allocator> leak(b: Box<T, A>): T {
            val (ptr, alloc) = Box.intoRawWithAllocator(b)
            forget(alloc)
            // SAFETY: Pointer is valid and unique.
            return ptr
        }

        /**
         * Converts a `Holder<T>` into a `Pin<Holder<T>>`. If `T` does not implement [Unpin], then
         * `* boxed` will be pinned in memory and unable to be moved.
         *
         * This conversion does not allocate on the heap and happens in place.
         *
         * This is also available via [From].
         *
         * Constructing and pinning a `Holder` with <code>Box.intoPin([Box.new]\(x))</code>
         * can also be written more concisely using <code>[Box.pin]\(x)</code>.
         * This `intoPin` method is useful if you already have a `Holder<T>`, or you are
         * constructing a (pinned) `Holder` in a different way than with [Box.new].
         *
         * # Notes
         *
         * It's not recommended that crates add an impl like `From<Holder<T>> for Pin<T>`,
         * as it'll introduce an ambiguity when calling `Pin.from`.
         * A demonstration of such a poor impl is shown below.
         *
         * ```compile_fail
         * # use std.pin.Pin;
         * struct Foo; // A type defined in this crate.
         * impl From<Holder<()>> for Pin<Foo> {
         *     fn from(_: Holder<()>) -> Pin<Foo> {
         *         Pin.new(Foo)
         *     }
         * }
         *
         * let foo = Box.new(());
         * let bar = Pin.from(foo);
         * ```
         */
        public fun <T, A : Allocator> intoPin(boxed: Box<T, A>): Pin<Box<T, A>> where A : StaticAllocator {
            // SAFETY: It's not possible to move or replace the insides of a
            // `Pin<Holder<T>>` when `T: !Unpin`, so it's safe to pin it directly
            // so long as the allocator promises to not break the pinning invariants.
            return Pin.newUnchecked(boxed)
        }
    }
}

// The is a separate function to avoid doing it in every generic version, but it
// looks small to the mir inliner (particularly in panic=abort) so leave it to
// the backend to decide whether pulling it in everywhere is worth doing.
// even without panics, this helps for Miri backtraces

/**
 * Monomorphic function for allocating an uninit `Holder`.
 */
public fun boxNewUninit(layout: Layout): MutPtr {
    if (layout.size() == 0) {
        return layout.danglingPtr().asPtr()
    }
    return run {
        val matchVal = Global().allocate(layout)
        when (matchVal) { is Result.Ok<*, *> -> {
            val ptr = matchVal.okValue()
            ptr.asMutPtr()
        }; is Result.Err<*, *> -> {
            handleAllocError(layout)
        } }
    }
}

/**
 * Helper for `vec`.
 *
 * This is unchecked, but has to be marked as safe or else we couldn't use it in `vec`.
 */
public fun <T> boxAssumeInitIntoVecUnsafe(b: Box<MaybeUninit<List<T>>, Global>, n: Int): Vec<T, Global> {
    // SAFETY: Technically not, but this can't be
    // called stably except in ways we control.
    return (((b.assumeInit() as Any?) as Box<List<T>, Global>)).intoVec()
}

// even without panics, this helps for Miri backtraces

/**
 * Allocates memory on the heap and then places `x` into it.
 *
 * This doesn't actually allocate if `T` is zero-sized.
 * See the [memory layout] section for more details.
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * ```
 *
 * [memory layout]: self#memory-layout
 */
public fun <T> Box.Companion.new(x: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<T, Global> {
    // This is `Holder.newUninit` but inlined to avoid build time regressions.
    val ptr = (boxNewUninit(Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    // Nothing below can panic so we do not have to worry about deallocating `ptr`.
    // SAFETY: we just allocated the box to store `x`.
    (typeLayoutT).writeNative(ptr.rawAddress, x)
    // SAFETY: we just initialized the memory `ptr` points to.
    return transmuteWithLayouts<MutPtr, Box<T, Global>>(ptr, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<Box<T, Global>>())
}

// even without panics, this helps for Miri backtraces

/**
 * Constructs a new box with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var five = Holder.newUninit()
 * // Deferred initialization:
 * five.write(5)
 * val five1 = five.assumeInit()
 * check(five1 == 5)
 * ```
 */
public fun <T> Box.Companion.newUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<MaybeUninit<T>, Global> {
    // This is the same as `Self.newUninitIn(Global)`, but manually inlined (just like
    // `Holder.new`).
    // SAFETY:
    // - If `allocate` succeeds, the returned pointer exactly matches what `Holder` needs.
    return transmuteWithLayouts<MutPtr, Box<MaybeUninit<T>, Global>>(boxNewUninit(Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())), UnresolvedTypeLayout<MutPtr>(), UnresolvedTypeLayout<Box<MaybeUninit<T>, Global>>())
}

/**
 * Constructs a new `Holder` with uninitialized contents, with the memory
 * being filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val zero = Holder.newZeroed()
 * val zero1 = zero.assumeInit()
 * check(zero1 == 0)
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Box.Companion.newZeroed(): Box<MaybeUninit<T>, Global> = Box.newZeroedIn(Global())

/**
 * Constructs a new `Pin<Holder<T>>`. If `T` does not implement [Unpin], then
 * `x` will be pinned in memory and unable to be moved.
 *
 * Constructing and pinning of the `Holder` can also be done in two steps: `Holder.pin(x)`
 * does the same as <code>[Box.intoPin]\([Box.new]\(x))</code>. Consider using
 * [`intoPin`](Box.intoPin) if you already have a `Holder<T>`, or if you want to
 * construct a (pinned) `Holder` in a different way than with [Box.new].
 */
public fun <T> Box.Companion.pin(x: T): Pin<Box<T, Global>> = Box.new(x).into()

/**
 * Allocates memory on the heap then places `x` into it,
 * returning an error if the allocation fails
 *
 * This doesn't actually allocate if `T` is zero-sized.
 * See the [memory layout] section for more details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val five = Holder.tryNew(5).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 */
public fun <T> Box.Companion.tryNew(x: T): Result<Box<T, Global>, AllocError> = Box.tryNewIn(x, Global())

/**
 * Constructs a new box with uninitialized contents on the heap,
 * returning an error if the allocation fails
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     var five = Holder.tryNewUninit().getOrElse { return Result.failure(it) }
 *     // Deferred initialization:
 *     five.write(5)
 *     val five1 = five.assumeInit()
 *     check(five1 == 5)
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> Box.Companion.tryNewUninit(): Result<Box<MaybeUninit<T>, Global>, AllocError> = Box.tryNewUninitIn(Global())

/**
 * Constructs a new `Holder` with uninitialized contents, with the memory
 * being filled with `0` bytes on the heap
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val zero = Holder.tryNewZeroed().getOrElse { return Result.failure(it) }
 *     val zero1 = zero.assumeInit()
 *     check(zero1 == 0)
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Box.Companion.tryNewZeroed(): Result<Box<MaybeUninit<T>, Global>, AllocError> = Box.tryNewZeroedIn(Global())

/**
 * Allocates memory on the heap then clones `src` into it.
 *
 * This doesn't actually allocate if `src` is zero-sized.
 * See the [memory layout] section for more details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry() {
 *     val hello: String = Holder.cloneFromRef("hello")
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 */
public fun <T : CloneToUninit> Box.Companion.cloneFromRef(src: T): Box<T, Global> = Box.cloneFromRefIn(src, Global())

// @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

/**
 * Allocates memory on the heap then clones `src` into it, returning an error if allocation fails.
 *
 * This doesn't actually allocate if `src` is zero-sized.
 * See the [memory layout] section for more details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry(): Result<Unit> {
 *     val hello: String = Holder.tryCloneFromRef("hello").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 */
public fun <T : CloneToUninit> Box.Companion.tryCloneFromRef(src: T): Result<Box<T, Global>, AllocError> = Box.tryCloneFromRefIn(src, Global())

/**
 * Constructs a new boxed slice with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var values = Holder.newUninitSlice(3)
 * // Deferred initialization:
 * values[0].write(1)
 * values[1].write(2)
 * values[2].write(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(1, 2, 3))
 * ```
 */
public fun <T> Box.Companion.newUninitSlice(len: Int): Box<List<MaybeUninit<T>>, Global> {
    // SAFETY: `len` is exactly the capacity of this `RawVec`.
    return RawVec.withCapacity(len).intoBox(len)
}

/**
 * Constructs a new boxed slice with uninitialized contents, with the memory
 * being filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val values = Holder.newZeroedSlice(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(0, 0, 0))
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Box.Companion.newZeroedSlice(len: Int): Box<List<MaybeUninit<T>>, Global> {
    // SAFETY: `len` is exactly the capacity of this `RawVec`.
    return RawVec.withCapacityZeroed(len).intoBox(len)
}

/**
 * Constructs a new boxed slice with uninitialized contents. Returns an error if
 * the allocation fails.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     var values = Holder.tryNewUninitSlice(3).getOrElse { return Result.failure(it) }
 *     // Deferred initialization:
 *     values[0].write(1)
 *     values[1].write(2)
 *     values[2].write(3)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(1, 2, 3))
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> Box.Companion.tryNewUninitSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, Global>, AllocError> {
    val ptr =
        if (((typeLayoutT).sizeBytes == 0L) || len == 0) {
            NonNull.dangling()
        } else {
            val layout =
                run {
                    val matchVal = Layout.array<MaybeUninit<T>>(len, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val l = matchVal.okValue()
                        l
                    }; is Result.Err<*, *> -> {
                        return Result.Err<Box<List<MaybeUninit<T>>, Global>, AllocError>(AllocError())
                    } }
                }
            when (val tryResult = Global().allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<List<MaybeUninit<T>>, Global>, AllocError>(tryResult.errValue()) }.cast()
        }
    // SAFETY: `ptr` was just allocated with `Global` with the layout for an array of length
    // `len`, and the layout creation would have failed if `len` overflowed an isize.
    // `intoBox` is sound to call since `len` corresponds to the length of the just-created
    // `RawVec`.
    return Result.Ok<Box<List<MaybeUninit<T>>, Global>, AllocError>(RawVec.fromRawPartsIn(ptr.asPtr(), len, Global()).intoBox(len))
}

/**
 * Constructs a new boxed slice with uninitialized contents, with the memory
 * being filled with `0` bytes. Returns an error if the allocation fails.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val values = Holder.tryNewZeroedSlice(3).getOrElse { return Result.failure(it) }
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(0, 0, 0))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Box.Companion.tryNewZeroedSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, Global>, AllocError> {
    val ptr =
        if (((typeLayoutT).sizeBytes == 0L) || len == 0) {
            NonNull.dangling()
        } else {
            val layout =
                run {
                    val matchVal = Layout.array<MaybeUninit<T>>(len, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val l = matchVal.okValue()
                        l
                    }; is Result.Err<*, *> -> {
                        return Result.Err<Box<List<MaybeUninit<T>>, Global>, AllocError>(AllocError())
                    } }
                }
            when (val tryResult = Global().allocateZeroed(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<List<MaybeUninit<T>>, Global>, AllocError>(tryResult.errValue()) }.cast()
        }
    // SAFETY: `ptr` was just allocated with `Global` with the layout for an array of length
    // `len`, and the layout creation would have failed if `len` overflowed an isize.
    // `intoBox` is sound to call since `len` corresponds to the length of the just-created
    // `RawVec`.
    return Result.Ok<Box<List<MaybeUninit<T>>, Global>, AllocError>(RawVec.fromRawPartsIn(ptr.asPtr(), len, Global()).intoBox(len))
}

/**
 * Constructs a new boxed slice with uninitialized contents in the provided allocator.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     var values = Holder.newUninitSliceIn(3, System)
 *     // Deferred initialization:
 *     values[0].write(1)
 *     values[1].write(2)
 *     values[2].write(3)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(1, 2, 3))
 * }
 * ```
 */
public fun <T, A : Allocator> Box.Companion.newUninitSliceIn(len: Int, alloc: A): Box<List<MaybeUninit<T>>, A> {
    // SAFETY: `len` is exactly the capacity of this `RawVec`.
    return RawVec.withCapacityIn(len, alloc).intoBox(len)
}

/**
 * Constructs a new boxed slice with uninitialized contents in the provided allocator,
 * with the memory being filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     val values = Holder.newZeroedSliceIn(3, System)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(0, 0, 0))
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T, A : Allocator> Box.Companion.newZeroedSliceIn(len: Int, alloc: A): Box<List<MaybeUninit<T>>, A> {
    // SAFETY: `len` is exactly the capacity of this `RawVec`.
    return RawVec.withCapacityZeroedIn(len, alloc).intoBox(len)
}

/**
 * Constructs a new boxed slice with uninitialized contents in the provided allocator. Returns an error if
 * the allocation fails.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     var values = Holder.tryNewUninitSliceIn(3, System).getOrElse { return Result.failure(it) }
 *     // Deferred initialization:
 *     values[0].write(1)
 *     values[1].write(2)
 *     values[2].write(3)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(1, 2, 3))
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T, A : Allocator> Box.Companion.tryNewUninitSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, A>, AllocError> {
    val ptr =
        if (((typeLayoutT).sizeBytes == 0L) || len == 0) {
            NonNull.dangling()
        } else {
            val layout =
                run {
                    val matchVal = Layout.array<MaybeUninit<T>>(len, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val l = matchVal.okValue()
                        l
                    }; is Result.Err<*, *> -> {
                        return Result.Err<Box<List<MaybeUninit<T>>, A>, AllocError>(AllocError())
                    } }
                }
            when (val tryResult = alloc.allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<List<MaybeUninit<T>>, A>, AllocError>(tryResult.errValue()) }.cast()
        }
    // SAFETY: `ptr` was just allocated with `alloc` with the layout for an array of length
    // `len`, and the layout creation would have failed if `len` overflowed an isize.
    // `intoBox` is sound to call since `len` corresponds to the length of the just-created
    // `RawVec`.
    return Result.Ok<Box<List<MaybeUninit<T>>, A>, AllocError>(RawVec.fromRawPartsIn(ptr.asPtr(), len, alloc).intoBox(len))
}

/**
 * Constructs a new boxed slice with uninitialized contents in the provided allocator, with the memory
 * being filled with `0` bytes. Returns an error if the allocation fails.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val values = Holder.tryNewZeroedSliceIn(3, System).getOrElse { return Result.failure(it) }
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(0, 0, 0))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T, A : Allocator> Box.Companion.tryNewZeroedSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, A>, AllocError> {
    val ptr =
        if (((typeLayoutT).sizeBytes == 0L) || len == 0) {
            NonNull.dangling()
        } else {
            val layout =
                run {
                    val matchVal = Layout.array<MaybeUninit<T>>(len, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val l = matchVal.okValue()
                        l
                    }; is Result.Err<*, *> -> {
                        return Result.Err<Box<List<MaybeUninit<T>>, A>, AllocError>(AllocError())
                    } }
                }
            when (val tryResult = alloc.allocateZeroed(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Box<List<MaybeUninit<T>>, A>, AllocError>(tryResult.errValue()) }.cast()
        }
    // SAFETY: `ptr` was just allocated with `alloc` with the layout for an array of length
    // `len`, and the layout creation would have failed if `len` overflowed an isize.
    // `intoBox` is sound to call since `len` corresponds to the length of the just-created
    // `RawVec`.
    return Result.Ok<Box<List<MaybeUninit<T>>, A>, AllocError>(RawVec.fromRawPartsIn(ptr.asPtr(), len, alloc).intoBox(len))
}

/**
 * Converts the boxed slice into a boxed array.
 *
 * This operation does not reallocate; the underlying array of the slice is simply reinterpreted as an array type.
 *
 * # Errors
 *
 * Returns the original `Holder<[T]>` in the `Err` variant if `self.len()` does not equal `N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocSliceIntoArray)
 * public fun doctestEntry() {
 *     val boxSlice: List<Int> = listOf(1, 2, 3)
 *     val boxArray: IntArray = boxSlice.intoArray().unwrap()
 * }
 * ```
 */
public fun <T, A : Allocator> Box<List<T>, A>.intoArray(n: Int): Result<Box<List<T>, A>, Box<List<T>, A>> =
    if (this.len() == n) {
        val (ptr, alloc) = Box.intoRawWithAllocator(this)
        val ptr1 = mutPtrFromRustCast(ptr)
        // SAFETY: The underlying array of a slice has the exact same layout as an actual array `[T; N]` if `N` is equal to the slice's length.
        val me = Box.fromRawIn(ptr1, alloc)
        Result.Ok<Box<List<T>, A>, Box<List<T>, A>>(me)
    } else {
        Result.Err<Box<List<T>, A>, Box<List<T>, A>>(this)
    }

/**
 * Converts to `Holder<T, A>`.
 *
 * # Safety
 *
 * As with [MaybeUninit.assumeInit],
 * it is up to the caller to guarantee that the value
 * really is in an initialized state.
 * Calling this when the content is not yet fully initialized
 * causes immediate undefined behavior.
 *
 * [MaybeUninit.assumeInit]: mem.MaybeUninit.assumeInit
 *
 * # Examples
 *
 * ```kotlin
 * var five = Holder.newUninit()
 * // Deferred initialization:
 * five.write(5)
 * val five1: UInt = five.assumeInit()
 * check(five1 == 5u)
 * ```
 */
public fun <T, A : Allocator> Box<MaybeUninit<T>, A>.assumeInit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutA: TypeLayout<A> = UnresolvedTypeLayout()): Box<T, A> {
    // This is used in the `vec` macro, so we optimize for minimal IR generation
    // even in debug builds.
    // SAFETY: `Holder<T>` and `Holder<MaybeUninit<T>>` have the same layout.
    return transmuteWithLayouts<Box<MaybeUninit<T>, A>, Box<T, A>>(this, UnresolvedTypeLayout<Box<MaybeUninit<T>, A>>(), UnresolvedTypeLayout<Box<T, A>>())
}

/**
 * Writes the value and converts to `Holder<T, A>`.
 *
 * This method converts the box similarly to [Box.assumeInit] but
 * writes `value` into it before conversion thus guaranteeing safety.
 * In some scenarios use of this method may improve performance because
 * the compiler may be able to optimize copying from stack.
 *
 * # Examples
 *
 * ```kotlin
 * val bigBox = Holder.newUninit()
 * var array = MutableList(1024) { 0 }
 * for ((i, place) in ArrayDeque(array).mapIndexed { index, item -> Pair(index, item) }) {
 *     place = i
 * }
 * // The optimizer may be able to elide this copy, so previous code writes
 * // to heap directly.
 * val bigBox1 = Holder.write(bigBox, array)
 * for ((i1, x) in bigBox1.iter().mapIndexed { index, item -> Pair(index, item) }) {
 *     check(x == i1)
 * }
 * ```
 */
public fun <T, A : Allocator> Box.Companion.write(boxed: Box<MaybeUninit<T>, A>, value: T): Box<T, A> {
    // SAFETY: Writing initialises the boxed value.
    return run {
        boxed.write(value)
        boxed.assumeInit()
    }
}

/**
 * Converts to `Holder<[T], A>`.
 *
 * # Safety
 *
 * As with [MaybeUninit.assumeInit],
 * it is up to the caller to guarantee that the values
 * really are in an initialized state.
 * Calling this when the content is not yet fully initialized
 * causes immediate undefined behavior.
 *
 * [MaybeUninit.assumeInit]: mem.MaybeUninit.assumeInit
 *
 * # Examples
 *
 * ```kotlin
 * var values = Holder.newUninitSlice(3)
 * // Deferred initialization:
 * values[0].write(1)
 * values[1].write(2)
 * values[2].write(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(1, 2, 3))
 * ```
 */
public fun <T, A : Allocator> Box<List<MaybeUninit<T>>, A>.assumeInit(): Box<List<T>, A> {
    val (raw, alloc) = Box.intoRawWithAllocator(this)
    // SAFETY: Upheld by caller.
    return Box.fromRawIn(mutPtrFromRustCast(raw), alloc)
}

/**
 * Constructs a box from a raw pointer.
 *
 * After calling this function, the raw pointer is owned by the
 * resulting `Holder`. Specifically, the `Holder` destructor will call
 * the destructor of `T` and free the allocated memory. For this
 * to be safe, the memory must have been allocated in accordance
 * with the [memory layout] used by `Holder` .
 *
 * # Safety
 *
 * This function is unchecked because improper use may lead to
 * memory problems. For example, a double-free may occur if the
 * function is called twice on the same raw pointer.
 *
 * The exact safety requirements are described in the [memory layout] section, together
 * with recommendations about how to handle the special case of zero-sized allocations
 * correctly.
 *
 * In particular, if the size of the pointee, as reported by [sizeOfVal], is
 * nonzero, then the raw pointer must point to a block of memory allocated by
 * [Global].
 * If the size of the pointee is zero, then the raw pointer can be any
 * sufficiently aligned non-null pointer, e.g. [ptr.dangling].
 *
 * Note that the [considerations for unchecked code] apply to all `Holder<T>` values.
 *
 * # Examples
 *
 * Recreate a `Holder` which was previously converted to a raw pointer
 * using [Box.intoRaw]:
 * ```kotlin
 * val x = 5
 * val ptr = Holder.intoRaw(x)
 * val x1 = Holder.fromRaw(ptr)
 * ```
 * Manually create a `Holder` from scratch by using the global allocator:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import std.alloc.Layout
 * import std.alloc.alloc
 *
 * run {
 *     val ptr = mutPtrFromRustCast(alloc(Layout.new<Int>(typeLayoutT = PrimitiveLayouts.I32)))
 *     // In general .write is required to avoid attempting to destruct
 *     // the (uninitialized) previous contents of `ptr`, though for this
 *     // simple example `* ptr = 5` would have worked as well.
 *     (PrimitiveLayouts.I32).writeNative((ptr).rawAddress, 5)
 *     val x = Holder.fromRaw(ptr)
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 * [considerations for unchecked code]: self#considerations-for-unchecked-code
 */
public fun <T> Box.Companion.fromRaw(raw: MutPtr): Box<T, Global> {
    // SAFETY: Upheld by caller.
    return Box.fromRawIn(raw, Global())
}

/**
 * Constructs a box from a `NonNull` pointer.
 *
 * After calling this function, the `NonNull` pointer is owned by
 * the resulting `Holder`. Specifically, the `Holder` destructor will call
 * the destructor of `T` and free the allocated memory. For this
 * to be safe, the memory must have been allocated in accordance
 * with the [memory layout] used by `Holder` .
 *
 * # Safety
 *
 * This function is unchecked because improper use may lead to
 * memory problems. For example, a double-free may occur if the
 * function is called twice on the same `NonNull` pointer.
 *
 * The exact safety requirements are described in the [memory layout] section, together
 * with recommendations about how to handle the special case of zero-sized allocations
 * correctly.
 *
 * In particular, if the size of the pointee, as reported by [sizeOfVal], is
 * nonzero, then the raw pointer must point to a block of memory allocated by
 * [Global].
 * If the size of the pointee is zero, then the raw pointer can be any
 * sufficiently aligned pointer, e.g. [NonNull.dangling].
 *
 * Note that the [considerations for unchecked code] apply to all `Holder<T>` values.
 *
 * # Examples
 *
 * Recreate a `Holder` which was previously converted to a `NonNull`
 * pointer using [Box.intoNonNull]:
 * ```kotlin
 * val x = 5
 * val nonNull = Holder.intoNonNull(x)
 * val x1 = Holder.fromNonNull(nonNull)
 * ```
 * Manually create a `Holder` from scratch by using the global allocator:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.alloc.Layout
 * import std.alloc.alloc
 * import std.ptr.NonNull
 *
 * run {
 *     val nonNull = NonNull.new(alloc(Layout.new<Int>(typeLayoutT = PrimitiveLayouts.I32)).cast()).expect("alloc should have successfully allocated memory")
 *     // In general .write is required to avoid attempting to destruct
 *     // the (uninitialized) previous contents of `nonNull`.
 *     nonNull.write(5)
 *     val x = Holder.fromNonNull(nonNull)
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 * [considerations for unchecked code]: self#considerations-for-unchecked-code
 */
public fun <T> Box.Companion.fromNonNull(ptr: NonNull): Box<T, Global> {
    // SAFETY: Upheld by caller.
    return Box.fromRaw(ptr.asPtr())
}

/**
 * Consumes the `Holder`, returning a wrapped raw pointer.
 *
 * The pointer will be properly aligned and non-null.
 *
 * After calling this function, the caller is responsible for the
 * memory previously managed by the `Holder`. In particular, the
 * caller should properly destroy `T` and release the memory, taking
 * into account the [memory layout] used by `Holder`. The easiest way to
 * do this is to convert the raw pointer back into a `Holder` with the
 * [Box.fromRaw] function, allowing the `Holder` destructor to perform
 * the cleanup.
 *
 * While manually deallocating the pointer through [deallocate]
 * is possible, you must only do this if the pointee's size, as reported by
 * [sizeOfVal], is non-zero (the `Holder` may be a dangling pointer
 * otherwise). See the [memory layout] section for more details.
 *
 * Note: this is an associated function, which means that you have
 * to call it as `Holder.intoRaw(b)` instead of `b.intoRaw()`. This
 * is so that there is no conflict with a method on the inner type.
 *
 * # Examples
 * Converting the raw pointer back into a `Holder` with [Box.fromRaw]
 * for automatic cleanup:
 * ```kotlin
 * val x = "Hello"
 * val ptr = Holder.intoRaw(x)
 * val x1 = Holder.fromRaw(ptr)
 * ```
 * Manual cleanup by explicitly running the destructor and deallocating
 * the memory:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import std.alloc.Layout
 * import std.alloc.dealloc
 * import std.ptr
 *
 * val x = "Hello"
 * val ptr = Holder.intoRaw(x)
 * run {
 *     ptr.dropInPlace(ptr)
 *     dealloc(mutPtrFromRustCast(ptr), Layout.new<String>(typeLayoutT = UnresolvedTypeLayout<String>()))
 * }
 * ```
 * Note: This is equivalent to the following:
 * ```kotlin
 * val x = "Hello"
 * val ptr = Holder.intoRaw(x)
 * run {
 *     (Holder.fromRaw(ptr)).let { }
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 * [deallocate]: Allocator.deallocate
 */
public fun <T> Box.Companion.intoRaw(b: Box<T, Global>): MutPtr {
    // Avoid `intoRawWithAllocator` as that interacts poorly with Miri's Stacked Borrows.
    val b1 = b
    // We need to give Miri (specifically, Stacked Borrows) a chance to recognize this as a
    // safe-to-raw-pointer cast. To achieve this, we first create a mutable reference, and then
    // cast that to a raw pointer -- this cast is recognized by the aliasing model and leads to
    // a suitable retag.
    // It would be wrong for `intoRawWithAllocator` to do the same as that would induce
    // uniqueness assumptions (from the ) that we only want with the default allocator.
    return mutPtrFromRustCast((b1))
}

/**
 * Consumes the `Holder`, returning a wrapped `NonNull` pointer.
 *
 * The pointer will be properly aligned.
 *
 * After calling this function, the caller is responsible for the
 * memory previously managed by the `Holder`. In particular, the
 * caller should properly destroy `T` and release the memory, taking
 * into account the [memory layout] used by `Holder`. The easiest way to
 * do this is to convert the `NonNull` pointer back into a `Holder` with the
 * [Box.fromNonNull] function, allowing the `Holder` destructor to
 * perform the cleanup.
 *
 * While manually deallocating the pointer through [deallocate]
 * is possible, you must only do this if the pointee's size, as reported by
 * [sizeOfVal], is non-zero (the `Holder` may be a dangling pointer
 * otherwise). See the [memory layout] section for more details.
 *
 * Note: this is an associated function, which means that you have
 * to call it as `Holder.intoNonNull(b)` instead of `b.intoNonNull()`.
 * This is so that there is no conflict with a method on the inner type.
 *
 * # Examples
 * Converting the `NonNull` pointer back into a `Holder` with [Box.fromNonNull]
 * for automatic cleanup:
 * ```kotlin
 * val x = "Hello"
 * val nonNull = Holder.intoNonNull(x)
 * val x1 = Holder.fromNonNull(nonNull)
 * ```
 * Manual cleanup by explicitly running the destructor and deallocating
 * the memory:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.alloc.Layout
 * import std.alloc.dealloc
 *
 * val x = "Hello"
 * val nonNull = Holder.intoNonNull(x)
 * run {
 *     nonNull.dropInPlace()
 *     dealloc(nonNull.asPtr().cast(), Layout.new<String>(typeLayoutT = UnresolvedTypeLayout<String>()))
 * }
 * ```
 * Note: This is equivalent to the following:
 * ```kotlin
 * val x = "Hello"
 * val nonNull = Holder.intoNonNull(x)
 * run {
 *     (Holder.fromNonNull(nonNull)).let { }
 * }
 * ```
 *
 * [memory layout]: self#memory-layout
 * [deallocate]: Allocator.deallocate
 */
public fun <T> Box.Companion.intoNonNull(b: Box<T, Global>): NonNull {
    // As of August 2026, we cannot utilize `Holder.leak`
    // because whether or not you can reconstruct the `Holder`
    // later using `Holder.fromRaw` or `Holder.fromNonNull` is
    // an open question.
    // SAFETY: `Holder` is guaranteed to be non-null.
    return NonNull.newUnchecked(Box.intoRaw(b))
}

/**
 * Creates a `Holder<T>`, with the `Default` value for `T`.
 */
public fun <T> defaultBoxT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): Box<T, Global> {
    val x: Box<MaybeUninit<T>, Global>  = Box.newUninit()
    // SAFETY: `x` is valid for writing and has the same layout as `T`.
    // If `T.default()` panics, dropping `x` will just deallocate the Holder as `MaybeUninit<T>`
    // does not have a destructor.
    //
    // We use `ptr.write` as `MaybeUninit.write` creates
    // extra stack copies of `T` in debug mode.
    //
    // See https://github.com/rust-lang/rust/issues/136043 for more context.
    write((x).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, defaultTFn(), typeLayoutT = typeLayoutT)
    // SAFETY: `x` was just initialized above.
    return x.assumeInit()
}

/**
 * Creates an empty `[T]` inside a `Holder`.
 */
public fun <T> defaultBoxTSlice(): Box<List<T>, Global> {
    val ptr: BoxRaw<List<T>> = BoxRaw.dangling()
    // SAFETY: `[T; 0]` is a ZST, for which `dangling` is valid to turn into a `Holder`,
    // even after unsize coercion.
    return Box(ptr, Global())
}

public fun defaultBoxString(): Box<String, Global> {
    // SAFETY:
    // - `[UByte; 0]` is a ZST, for which `dangling` is valid to turn into a `Holder`
    // - Casting `[UByte]` to `String` is correct
    // - The empty byte slice is valid UTF-8
    val ptr: BoxRaw<String> =
        run {
            val bytes: BoxRaw<List<UByte>> = BoxRaw.dangling()
            BoxRaw.newUnchecked(mutPtrFromRustCast(bytes.asPtr()))
        }
    return Box(ptr, Global())
}

public fun <T> defaultPinBoxT(): Pin<Box<T, Global>> = Box.intoPin(Box.default())

public fun <T, A : Allocator> Box<List<T>, A>.clone(): Box<List<T>, A> {
    val alloc = Box.allocator(this).clone()
    return this.toVecIn(alloc).intoBoxedSlice()
}

/**
 * Copies `source`s contents into `self` without creating a new allocation,
 * so long as the two are of the same length.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val x = listOf(5, 6, 7)
 * var y = listOf(8, 9, 10)
 * val yp: ConstPtr = y
 * y.cloneFrom(x)
 * // The value is the same
 * check(x == y)
 * // And no allocation occurred
 * check(yp == y)
 * ```
 */
public fun <T, A : Allocator> Box<List<T>, A>.cloneFrom(source: Box<List<T>, A>) {
    var selfMut = this
    if (selfMut.len() == source.len()) {
        selfMut.cloneFromSlice(source)
    } else {
        selfMut = source.clone()
    }
}

public fun <Output, Args, F : FnOnce<Args, Output>, A : Allocator> Box<F, A>.callOnce(args: Args, callOnceFFn: (Box<F, A>, Args) -> Output): Output = callOnceFFn(this, args)

public fun <Output, Args, F : FnMut<Args, Output>, A : Allocator> Box<F, A>.callMut(args: Args, callMutFFn: (Box<F, A>, Args) -> Output): Output = callMutFFn(this, args)

public fun <Output, Args, F : Fn<Args, Output>, A : Allocator> Box<F, A>.call(args: Args, callFFn: (Box<F, A>, Args) -> Output): Output = callFFn(this, args)

public fun <Args, F : AsyncFnOnce<Args>, A : Allocator> Box<F, A>.asyncCallOnce(args: Args, asyncCallOnceFFn: (Box<F, A>, Args) -> CallOnceFuture): CallOnceFuture = asyncCallOnceFFn(this, args)

public fun <Args, F : AsyncFnMut<Args>, A : Allocator> Box<F, A>.asyncCallMut(args: Args, asyncCallMutFFn: (Box<F, A>, Args) -> CallRefFuture): CallRefFuture = asyncCallMutFFn(this, args)

public fun <Args, F : AsyncFn<Args>, A : Allocator> Box<F, A>.asyncCall(args: Args, asyncCallFFn: (Box<F, A>, Args) -> CallRefFuture): CallRefFuture = asyncCallFFn(this, args)

// It is quite crucial that we only allow the `Global` allocator here.
// Handling arbitrary custom allocators (which can affect the `Holder` layout heavily!)
// would need a lot of codegen and interpreter adjustments.

public fun <G : Coroutine<R>, R, A : Allocator> Box<G, A>.resume(arg: R, resumeGFn: (Any?, R) -> CoroutineState<Yield, Return>): CoroutineState<Yield, Return> = resumeGFn(Pin.new(this), arg)

public fun <G : Coroutine<R>, R, A : Allocator> Pin<Box<G, A>>.resume(arg: R, resumeGFn: (Any?, R) -> CoroutineState<Yield, Return>): CoroutineState<Yield, Return> where A : StaticAllocator = resumeGFn(this.asMut(), arg)

public fun <Output, F : Future<Output>, A : Allocator> Box<F, A>.poll(cx: Context, pollFFn: (Any?, Context) -> Poll<Output>): Poll<Output> = pollFFn(Pin.new(this), cx)

public fun <E, A : Allocator> Box<E, A>.cause(): Any? = Error.cause(this)

public fun <E, A : Allocator> Box<E, A>.source(): Any? = Error.source(this)

public fun <E, A : Allocator> Box<E, A>.provide(request: Request) {
    Error.provide(this, request)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.allocate(layout: Layout): Result<NonNull, AllocError> = this.allocate(layout)

public fun <T : Allocator, A : Allocator> Box<T, A>.allocateZeroed(layout: Layout): Result<NonNull, AllocError> = this.allocateZeroed(layout)

public fun <T : Allocator, A : Allocator> Box<T, A>.deallocate(ptr: NonNull, layout: Layout) {
    // SAFETY: the safety contract must be upheld by the caller
    this.deallocate(ptr, layout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.grow(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.growZeroed(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.shrink(ptr, oldLayout, newLayout)
}

/**
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 */

public fun <R : Rng, A : Allocator> Box<R, A>.fillBytes(bytes: MutableList<UByte>) {
    this.fillBytes(bytes)
}

// rust-kotlin: manual
// port-lint: source boxed.rs
package io.github.kotlinmania.rustlang.alloc.boxed

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.StaticAllocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.convert.AsMut
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.intrinsics.KmForgettableResource
import io.github.kotlinmania.rustlang.core.intrinsics.KmResourceCleaner
import io.github.kotlinmania.rustlang.core.intrinsics.kmRegisterResourceCleaner
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.deref.LegacyReceiver
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.pin.PinSafePointer
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

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
// val value: UByte = 5u.toUByte()
// val boxed: UByte = value
// ```
//
// Move a value from a [Box] back to the stack by [dereferencing]:
//
// ```kotlin
// val boxed: UByte = 5u.toUByte()
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
// val list: List<Int> = List.Cons(1, List.Cons(2, List.Nil()))
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

/**
 * An internal wrapper for the pointer + `PhantomData` inside a `Holder`.
 * This type has no semantic meaning. It only exists because the layout of
 * `Holder` is hard-coded into the compiler, and because moving the
 * auto trait impls to `Holder` would cause regressions (#162850).
 */
public open class BoxRaw<T>(
    public var pointer: NonNull,
) {
    public open fun <U> cast(): BoxRaw<U> = BoxRaw(pointer = this.pointer.cast())

    public open fun asNonNullPtr(): NonNull = this.pointer

    public open fun asPtr(): MutPtr = this.pointer.asPtr()

    public open fun clone(): BoxRaw<T> = BoxRaw(this.pointer)

    public companion object {
        /**
         * # Safety
         * `ptr` must be non-null
         */
        public fun <T> newUnchecked(ptr: MutPtr): BoxRaw<T> {
            // SAFETY: Upheld by the caller
            return BoxRaw(pointer = NonNull.newUnchecked(ptr))
        }

        public fun <T> dangling(): BoxRaw<T> = BoxRaw(pointer = NonNull.dangling())
    }
}

public fun <T> fromNonNullT(value: BoxRaw<T>): NonNull = value.asNonNullPtr()

// The declaration of the `Holder` struct must be kept in sync with the
// compiler or ICEs will happen.

internal class BoxAllocState<A : Allocator>(
    var addr: Long,
    var layout: Layout,
    var alloc: A,
    var owned: Boolean,
) {
    fun deallocIfOwned() {
        val currentAddr = addr
        val currentLayout = layout
        if (owned && currentAddr != 0L && currentLayout.size() != 0) {
            owned = false
            addr = 0L
            alloc.deallocate(NonNull.newUnchecked(MutPtr(currentAddr)), currentLayout)
        } else {
            owned = false
            addr = 0L
        }
    }
}

internal class BoxHeapSlot<T>(
    var value: T,
)

private fun <T, U, A : Allocator> reuseAllocationAs(
    allocation: Box<MaybeUninit<T>, A>,
    value: U,
    typeLayoutU: TypeLayout<U>,
): Box<U, A> {
    val raw = allocation.field0.cast<U>()
    val alloc = allocation.field1
    val layout = allocation.allocState.layout
    val owned = allocation.allocState.owned
    if (typeLayoutU !is UnresolvedTypeLayout && raw.asPtr().rawAddress != 0L && layout.size() > 0) {
        typeLayoutU.writeNative(raw.asPtr().rawAddress, value)
    }
    allocation.disarmCleaner()
    allocation.clearHeapSlot()
    return Box(
        field0 = raw,
        field1 = alloc,
        valueLayout = layout,
        typeLayoutArg = typeLayoutU,
        ownedNative = owned,
        heapSlotArg = BoxHeapSlot(value),
    )
}

private fun orderingFromCmpInt(cmp: Int): Ordering =
    when {
        cmp < 0 -> Ordering.Less
        cmp > 0 -> Ordering.Greater
        else -> Ordering.Equal
    }

/**
 * A pointer type that uniquely owns a heap allocation of type `T`.
 *
 * See the [module-level documentation](../../std/boxed/index.html) for more.
 */
public open class Box<T, A : Allocator> internal constructor(
    public var field0: BoxRaw<T>,
    public var field1: A,
    valueLayout: Layout,
    typeLayoutArg: TypeLayout<T>,
    ownedNative: Boolean,
    heapSlotArg: BoxHeapSlot<T>?,
) : DerefPure,
    LegacyReceiver,
    PinSafePointer,
    Borrow<T>,
    BorrowMut<T>,
    AsRef<T>,
    AsMut<T>,
    Drop,
    KmForgettableResource {
    internal val typeLayout: TypeLayout<T> = typeLayoutArg
    internal val allocState: BoxAllocState<A> =
        BoxAllocState(
            addr = field0.asPtr().rawAddress,
            layout = valueLayout,
            alloc = field1,
            owned = ownedNative,
        )
    private var heapSlot: BoxHeapSlot<T>? = heapSlotArg
    private val cleaner: KmResourceCleaner =
        allocState.let { state ->
            kmRegisterResourceCleaner(this) { state.deallocIfOwned() }
        }

    public constructor(field0: BoxRaw<T>, field1: A) : this(
        field0 = field0,
        field1 = field1,
        valueLayout = Layout.fromSizeAlignUnchecked(0, 1),
        typeLayoutArg = UnresolvedTypeLayout(),
        ownedNative = false,
        heapSlotArg = null,
    )

    internal fun clearHeapSlot() {
        this.heapSlot = null
    }

    internal fun readValue(): T {
        val addr = this.field0.asPtr().rawAddress
        if (this.typeLayout !is UnresolvedTypeLayout && addr != 0L && this.allocState.layout.size() > 0) {
            return this.typeLayout.readNative(addr)
        }
        val slot = this.heapSlot ?: error("Attempted to dereference uninitialized Box without heap backing or resolved TypeLayout")
        return slot.value
    }

    internal fun writeValue(v: T) {
        val addr = this.allocState.addr
        if (this.typeLayout !is UnresolvedTypeLayout && addr != 0L && this.allocState.layout.size() > 0) {
            this.typeLayout.writeNative(addr, v)
        }
        val slot = this.heapSlot
        if (slot != null) {
            slot.value = v
        } else {
            this.heapSlot = BoxHeapSlot(v)
        }
    }

    public override fun close() {
        this.drop()
    }

    public override fun disarmCleaner() {
        this.allocState.owned = false
        this.cleaner.disarm()
    }

    public override fun drop() {
        val v = this.heapSlot?.value
        this.heapSlot = null
        if (v is Drop) {
            v.drop()
        }
        this.allocState.deallocIfOwned()
        this.cleaner.disarm()
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
    public open fun clone(): Box<T, A> = Box.newIn(this.readValue(), this.field1, this.typeLayout)

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
        this.writeValue(source.readValue())
    }

    public open fun eq(other: Box<T, A>): Boolean = this.readValue() == other.readValue()

    public open fun ne(other: Box<T, A>): Boolean = !this.eq(other)

    public open fun partialCmp(other: Box<T, A>): Ordering? =
        kmPartialCompareValues(this.readValue(), other.readValue())?.let(::orderingFromCmpInt)

    public open fun lt(other: Box<T, A>): Boolean = this.partialCmp(other) == Ordering.Less

    public open fun le(other: Box<T, A>): Boolean =
        when (this.partialCmp(other)) {
            Ordering.Less, Ordering.Equal -> true
            else -> false
        }

    public open fun ge(other: Box<T, A>): Boolean =
        when (this.partialCmp(other)) {
            Ordering.Greater, Ordering.Equal -> true
            else -> false
        }

    public open fun gt(other: Box<T, A>): Boolean = this.partialCmp(other) == Ordering.Greater

    public open fun cmp(other: Box<T, A>): Ordering =
        orderingFromCmpInt(kmCompareValues(this.readValue(), other.readValue()))

    public override fun equals(other: Any?): Boolean =
        other is Box<*, *> && this.readValue() == other.readValue()

    public override fun hashCode(): Int = this.readValue()?.hashCode() ?: 0

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.readValue().toString())
            Result.Ok(Unit)
        }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.readValue().toString())
            Result.Ok(Unit)
        }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Unit> {
        val ptr: ConstPtr = Box.asPtr(this)
        return run {
            f.append(ptr.toString())
            Result.Ok(Unit)
        }
    }

    public open fun deref(): T = this.readValue()

    public open fun derefMut(): T = this.readValue()

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

    public override fun borrow(): T = this.readValue()

    public override fun borrowMut(): T = this.readValue()

    public override fun asRef(): T = this.readValue()

    public override fun asMut(): T = this.readValue()

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
        public fun <T, A : Allocator> newIn(x: T, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<T, A> {
            val boxed = Box.newUninitIn<T, A>(alloc, typeLayoutT)
            return Box.write(boxed, x, typeLayoutT)
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
        public fun <T, A : Allocator> tryNewIn(x: T, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<T, A>, AllocError> {
            val boxed =
                when (val tryResult = Box.tryNewUninitIn<T, A>(alloc, typeLayoutT)) {
                    is Result.Ok -> tryResult.value
                    is Result.Err -> return Result.Err(tryResult.value)
                }
            return Result.Ok(Box.write(boxed, x, typeLayoutT))
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
            val layout = Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
            return when (val matchVal = Box.tryNewUninitIn<T, A>(alloc, typeLayoutT)) {
                is Result.Ok -> matchVal.value
                is Result.Err -> handleAllocError(layout)
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
            if (typeLayoutT is UnresolvedTypeLayout || typeLayoutT.sizeBytes == 0L) {
                val ptr = NonNull.dangling()
                return Result.Ok(
                    Box(
                        field0 = BoxRaw.newUnchecked(ptr.asPtr()),
                        field1 = alloc,
                        valueLayout = Layout.fromSizeAlignUnchecked(0, 1),
                        typeLayoutArg = UnresolvedTypeLayout(),
                        ownedNative = false,
                        heapSlotArg = BoxHeapSlot(MaybeUninit.uninit()),
                    ),
                )
            }
            val layout = Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
            val ptr =
                when (val tryResult = alloc.allocate(layout)) {
                    is Result.Ok -> tryResult.value
                    is Result.Err -> return Result.Err(tryResult.value)
                }
            return Result.Ok(
                Box(
                    field0 = BoxRaw.newUnchecked(ptr.asPtr()),
                    field1 = alloc,
                    valueLayout = layout,
                    typeLayoutArg = UnresolvedTypeLayout(),
                    ownedNative = true,
                    heapSlotArg = BoxHeapSlot(MaybeUninit.uninit()),
                ),
            )
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
            val layout = Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
            return when (val matchVal = Box.tryNewZeroedIn<T, A>(alloc, typeLayoutT)) {
                is Result.Ok -> matchVal.value
                is Result.Err -> handleAllocError(layout)
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
            if (typeLayoutT is UnresolvedTypeLayout || typeLayoutT.sizeBytes == 0L) {
                val ptr = NonNull.dangling()
                val initial =
                    if (typeLayoutT !is UnresolvedTypeLayout) {
                        MaybeUninit.new(typeLayoutT.readNative(ptr.asPtr().rawAddress))
                    } else {
                        MaybeUninit.uninit()
                    }
                return Result.Ok(
                    Box(
                        field0 = BoxRaw.newUnchecked(ptr.asPtr()),
                        field1 = alloc,
                        valueLayout = Layout.fromSizeAlignUnchecked(0, 1),
                        typeLayoutArg = UnresolvedTypeLayout(),
                        ownedNative = false,
                        heapSlotArg = BoxHeapSlot(initial),
                    ),
                )
            }
            val layout = Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
            val ptr =
                when (val tryResult = alloc.allocateZeroed(layout)) {
                    is Result.Ok -> tryResult.value
                    is Result.Err -> return Result.Err(tryResult.value)
                }
            val zeroedVal = typeLayoutT.readNative(ptr.asPtr().rawAddress)
            return Result.Ok(
                Box(
                    field0 = BoxRaw.newUnchecked(ptr.asPtr()),
                    field1 = alloc,
                    valueLayout = layout,
                    typeLayoutArg = UnresolvedTypeLayout(),
                    ownedNative = true,
                    heapSlotArg = BoxHeapSlot(MaybeUninit.new(zeroedVal)),
                ),
            )
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
        public fun <T, A> pinIn(x: T, alloc: A): Pin<Box<T, A>> where A : Allocator, A : StaticAllocator = Box.intoPin(Box.newIn(x, alloc))

        /**
         * Converts a `Holder<T>` into a `Holder<[T]>`
         *
         * This conversion does not allocate on the heap and happens in place.
         */
        public fun <T, A : Allocator> intoBoxedSlice(boxed: Box<T, A>): Box<List<T>, A> {
            val value = boxed.readValue()
            val raw =
                boxed.field0
                    .asNonNullPtr()
                    .castSlice(1)
                    .asPtr()
            val alloc = boxed.field1
            val layout = boxed.allocState.layout
            val owned = boxed.allocState.owned
            boxed.disarmCleaner()
            boxed.clearHeapSlot()
            return Box(
                field0 = BoxRaw.newUnchecked(raw),
                field1 = alloc,
                valueLayout = layout,
                typeLayoutArg = UnresolvedTypeLayout(),
                ownedNative = owned,
                heapSlotArg = BoxHeapSlot(listOf(value)),
            )
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
        public fun <T, A : Allocator> intoInner(boxed: Box<T, A>): T {
            val value = boxed.readValue()
            boxed.clearHeapSlot()
            boxed.allocState.deallocIfOwned()
            boxed.cleaner.disarm()
            return value
        }

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
            val value = boxed.readValue()
            val raw = boxed.field0.cast<MaybeUninit<T>>()
            val alloc = boxed.field1
            val layout = boxed.allocState.layout
            val owned = boxed.allocState.owned
            boxed.disarmCleaner()
            boxed.clearHeapSlot()
            val uninit =
                Box(
                    field0 = raw,
                    field1 = alloc,
                    valueLayout = layout,
                    typeLayoutArg = UnresolvedTypeLayout<MaybeUninit<T>>(),
                    ownedNative = owned,
                    heapSlotArg = BoxHeapSlot(MaybeUninit.uninit()),
                )
            return Pair(value, uninit)
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
        public fun <T, A : Allocator, U> map(
            thisRef: Box<T, A>,
            f: (T) -> U,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
            typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout(),
        ): Box<U, A> {
            val (value, allocation) = Box.take(thisRef)
            val canReuse =
                typeLayoutT !is UnresolvedTypeLayout &&
                    typeLayoutU !is UnresolvedTypeLayout &&
                    sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) &&
                    alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU)
            if (canReuse) {
                return reuseAllocationAs(allocation, f(value), typeLayoutU)
            }
            val alloc = allocation.field1
            allocation.drop()
            return Box.newIn(f(value), alloc, typeLayoutU)
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
        public fun <U, E, T, A : Allocator> tryMap(
            thisRef: Box<T, A>,
            f: (T) -> Result<U, E>,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
            typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout(),
        ): Result<Box<U, A>, E> {
            val (value, allocation) = Box.take(thisRef)
            val mapped =
                when (val tryResult = f(value)) {
                    is Result.Ok -> tryResult.value
                    is Result.Err -> {
                        allocation.drop()
                        return Result.Err(tryResult.value)
                    }
                }
            val canReuse =
                typeLayoutT !is UnresolvedTypeLayout &&
                    typeLayoutU !is UnresolvedTypeLayout &&
                    sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) &&
                    alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU)
            if (canReuse) {
                return Result.Ok(reuseAllocationAs(allocation, mapped, typeLayoutU))
            }
            val alloc = allocation.field1
            allocation.drop()
            return Result.Ok(Box.newIn(mapped, alloc, typeLayoutU))
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
        public fun <T, A : Allocator> cloneFromRefIn(
            src: T,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
            cloneToUninitTFn: (T, MutPtr) -> Unit = { s, dst ->
                if (typeLayoutT !is UnresolvedTypeLayout && dst.rawAddress != 0L) {
                    typeLayoutT.writeNative(dst.rawAddress, s)
                }
            },
        ): Box<T, A> {
            val layout =
                if (typeLayoutT !is UnresolvedTypeLayout) {
                    Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
                } else {
                    Layout.fromSizeAlignUnchecked(0, 1)
                }
            return when (val matchVal = Box.tryCloneFromRefIn(src, alloc, typeLayoutT, cloneToUninitTFn)) {
                is Result.Ok -> matchVal.value
                is Result.Err -> handleAllocError(layout)
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
        public fun <T, A : Allocator> tryCloneFromRefIn(
            src: T,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
            cloneToUninitTFn: (T, MutPtr) -> Unit = { s, dst ->
                if (typeLayoutT !is UnresolvedTypeLayout && dst.rawAddress != 0L) {
                    typeLayoutT.writeNative(dst.rawAddress, s)
                }
            },
        ): Result<Box<T, A>, AllocError> {
            val layout =
                if (typeLayoutT !is UnresolvedTypeLayout) {
                    Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
                } else {
                    Layout.fromSizeAlignUnchecked(0, 1)
                }
            if (layout.size() == 0) {
                val ptr = NonNull.dangling().asPtr()
                return Result.Ok(
                    Box(
                        field0 = BoxRaw.newUnchecked(ptr),
                        field1 = alloc,
                        valueLayout = layout,
                        typeLayoutArg = typeLayoutT,
                        ownedNative = false,
                        heapSlotArg = BoxHeapSlot(src),
                    ),
                )
            }
            val ptr =
                when (val tryResult = alloc.allocate(layout)) {
                    is Result.Ok -> tryResult.value
                    is Result.Err -> return Result.Err(tryResult.value)
                }
            val rawPtr = ptr.asPtr()
            cloneToUninitTFn(src, rawPtr)
            return Result.Ok(
                Box(
                    field0 = BoxRaw.newUnchecked(rawPtr),
                    field1 = alloc,
                    valueLayout = layout,
                    typeLayoutArg = typeLayoutT,
                    ownedNative = true,
                    heapSlotArg = BoxHeapSlot(src),
                ),
            )
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
        public fun <T, A : Allocator> fromRawIn(
            raw: MutPtr,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Box<T, A> {
            val layout =
                if (typeLayoutT !is UnresolvedTypeLayout) {
                    Layout.fromSizeAlignUnchecked(typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt())
                } else {
                    Layout.fromSizeAlignUnchecked(0, 1)
                }
            val owned = layout.size() != 0 && raw.rawAddress != 0L
            // SAFETY: Upheld by caller.
            return Box(
                field0 = BoxRaw.newUnchecked(raw),
                field1 = alloc,
                valueLayout = layout,
                typeLayoutArg = typeLayoutT,
                ownedNative = owned,
                heapSlotArg = null,
            )
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
        public fun <T, A : Allocator> fromNonNullIn(
            raw: NonNull,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Box<T, A> {
            // SAFETY: guaranteed by the caller.
            return Box.fromRawIn(raw.asPtr(), alloc, typeLayoutT)
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
            val ptr = b.field0.asPtr()
            val alloc = b.field1
            b.disarmCleaner()
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
            return b.field0.asPtr()
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
            return b.field0.asPtr().asConstPtr()
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
            val value = b.readValue()
            b.disarmCleaner()
            forget(b.field1)
            return value
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
        public fun <T, A> intoPin(boxed: Box<T, A>): Pin<Box<T, A>> where A : Allocator, A : StaticAllocator {
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
    return when (val matchVal = Global().allocate(layout)) {
        is Result.Ok -> matchVal.value.asPtr()
        is Result.Err -> handleAllocError(layout)
    }
}

/**
 * Helper for `vec`.
 *
 * This is unchecked, but has to be marked as safe or else we couldn't use it in `vec`.
 */
public fun <T> boxAssumeInitIntoVecUnsafe(b: Box<MaybeUninit<List<T>>, Global>, n: Int = 0): Vec<T, Global> {
    // SAFETY: Technically not, but this can't be
    // called stably except in ways we control.
    val initBox = b.assumeInit()
    val len = if (n > 0) n else initBox.readValue().size
    val (ptr, alloc) = Box.intoRawWithAllocator(initBox)
    return Vec.fromRawPartsIn(ptr, len, len, alloc)
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
public fun <T> Box.Companion.new(x: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<T, Global> =
    Box.newIn(x, Global(), typeLayoutT)

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
public fun <T> Box.Companion.newUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<MaybeUninit<T>, Global> =
    Box.newUninitIn(Global(), typeLayoutT)

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
public fun <T> Box.Companion.newZeroed(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<MaybeUninit<T>, Global> =
    Box.newZeroedIn(Global(), typeLayoutT)

/**
 * Constructs a new `Pin<Holder<T>>`. If `T` does not implement [Unpin], then
 * `x` will be pinned in memory and unable to be moved.
 *
 * Constructing and pinning of the `Holder` can also be done in two steps: `Holder.pin(x)`
 * does the same as <code>[Box.intoPin]\([Box.new]\(x))</code>. Consider using
 * [`intoPin`](Box.intoPin) if you already have a `Holder<T>`, or if you want to
 * construct a (pinned) `Holder` in a different way than with [Box.new].
 */
public fun <T> Box.Companion.pin(x: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Pin<Box<T, Global>> =
    Box.intoPin(Box.new(x, typeLayoutT))

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
public fun <T> Box.Companion.tryNew(x: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<T, Global>, AllocError> =
    Box.tryNewIn(x, Global(), typeLayoutT)

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
public fun <T> Box.Companion.tryNewUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<MaybeUninit<T>, Global>, AllocError> =
    Box.tryNewUninitIn(Global(), typeLayoutT)

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
public fun <T> Box.Companion.tryNewZeroed(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<MaybeUninit<T>, Global>, AllocError> =
    Box.tryNewZeroedIn(Global(), typeLayoutT)

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
public fun <T : CloneToUninit> Box.Companion.cloneFromRef(src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<T, Global> =
    Box.cloneFromRefIn(src, Global(), typeLayoutT) { s, dst -> s.cloneToUninit(dst) }

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
public fun <T : CloneToUninit> Box.Companion.tryCloneFromRef(src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<T, Global>, AllocError> =
    Box.tryCloneFromRefIn(src, Global(), typeLayoutT) { s, dst -> s.cloneToUninit(dst) }

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
public fun <T> Box.Companion.newUninitSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<List<MaybeUninit<T>>, Global> =
    Box.newUninitSliceIn(len, Global(), typeLayoutT)

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
public fun <T> Box.Companion.newZeroedSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<List<MaybeUninit<T>>, Global> =
    Box.newZeroedSliceIn(len, Global(), typeLayoutT)

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
public fun <T> Box.Companion.tryNewUninitSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, Global>, AllocError> =
    Box.tryNewUninitSliceIn(len, Global(), typeLayoutT)

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
public fun <T> Box.Companion.tryNewZeroedSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Box<List<MaybeUninit<T>>, Global>, AllocError> =
    Box.tryNewZeroedSliceIn(len, Global(), typeLayoutT)

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
public fun <T, A : Allocator> Box.Companion.newUninitSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<List<MaybeUninit<T>>, A> {
    val layout =
        if (typeLayoutT !is UnresolvedTypeLayout && len > 0) {
            when (val lr = Layout.array<T>(len, typeLayoutT)) {
                is Result.Ok -> lr.value
                is Result.Err -> handleAllocError(Layout.fromSizeAlignUnchecked(0, 1))
            }
        } else {
            Layout.fromSizeAlignUnchecked(0, 1)
        }
    return when (val matchVal = Box.tryNewUninitSliceIn(len, alloc, typeLayoutT)) {
        is Result.Ok -> matchVal.value
        is Result.Err -> handleAllocError(layout)
    }
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
public fun <T, A : Allocator> Box.Companion.newZeroedSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<List<MaybeUninit<T>>, A> {
    val layout =
        if (typeLayoutT !is UnresolvedTypeLayout && len > 0) {
            when (val lr = Layout.array<T>(len, typeLayoutT)) {
                is Result.Ok -> lr.value
                is Result.Err -> handleAllocError(Layout.fromSizeAlignUnchecked(0, 1))
            }
        } else {
            Layout.fromSizeAlignUnchecked(0, 1)
        }
    return when (val matchVal = Box.tryNewZeroedSliceIn(len, alloc, typeLayoutT)) {
        is Result.Ok -> matchVal.value
        is Result.Err -> handleAllocError(layout)
    }
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
    if (typeLayoutT is UnresolvedTypeLayout || typeLayoutT.sizeBytes == 0L || len == 0) {
        val ptr = NonNull.dangling().castSlice(len).asPtr()
        return Result.Ok(
            Box(
                field0 = BoxRaw.newUnchecked(ptr),
                field1 = alloc,
                valueLayout = Layout.fromSizeAlignUnchecked(0, 1),
                typeLayoutArg = UnresolvedTypeLayout(),
                ownedNative = false,
                heapSlotArg = BoxHeapSlot(List(len) { MaybeUninit.uninit() }),
            ),
        )
    }
    val layout =
        when (val matchVal = Layout.array<T>(len, typeLayoutT)) {
            is Result.Ok -> matchVal.value
            is Result.Err -> return Result.Err(AllocError())
        }
    val ptr =
        when (val tryResult = alloc.allocate(layout)) {
            is Result.Ok -> tryResult.value
            is Result.Err -> return Result.Err(tryResult.value)
        }
    return Result.Ok(
        Box(
            field0 = BoxRaw.newUnchecked(ptr.castSlice(len).asPtr()),
            field1 = alloc,
            valueLayout = layout,
            typeLayoutArg = UnresolvedTypeLayout(),
            ownedNative = true,
            heapSlotArg = BoxHeapSlot(List(len) { MaybeUninit.uninit() }),
        ),
    )
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
    if (typeLayoutT is UnresolvedTypeLayout || typeLayoutT.sizeBytes == 0L || len == 0) {
        val ptr = NonNull.dangling().castSlice(len).asPtr()
        val items =
            if (typeLayoutT !is UnresolvedTypeLayout) {
                List(len) { MaybeUninit.new(typeLayoutT.readNative(ptr.rawAddress)) }
            } else {
                List(len) { MaybeUninit.uninit() }
            }
        return Result.Ok(
            Box(
                field0 = BoxRaw.newUnchecked(ptr),
                field1 = alloc,
                valueLayout = Layout.fromSizeAlignUnchecked(0, 1),
                typeLayoutArg = UnresolvedTypeLayout(),
                ownedNative = false,
                heapSlotArg = BoxHeapSlot(items),
            ),
        )
    }
    val layout =
        when (val matchVal = Layout.array<T>(len, typeLayoutT)) {
            is Result.Ok -> matchVal.value
            is Result.Err -> return Result.Err(AllocError())
        }
    val ptr =
        when (val tryResult = alloc.allocateZeroed(layout)) {
            is Result.Ok -> tryResult.value
            is Result.Err -> return Result.Err(tryResult.value)
        }
    val baseAddr = ptr.asPtr().rawAddress
    val items = List(len) { idx -> MaybeUninit.new(typeLayoutT.readNative(baseAddr + idx.toLong() * typeLayoutT.sizeBytes)) }
    return Result.Ok(
        Box(
            field0 = BoxRaw.newUnchecked(ptr.castSlice(len).asPtr()),
            field1 = alloc,
            valueLayout = layout,
            typeLayoutArg = UnresolvedTypeLayout(),
            ownedNative = true,
            heapSlotArg = BoxHeapSlot(items),
        ),
    )
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
public fun <T, A : Allocator> Box<List<T>, A>.intoArray(n: Int = 0): Result<Box<List<T>, A>, Box<List<T>, A>> {
    val items = this.readValue()
    return if (items.size == n) {
        val raw = this.field0.asPtr()
        val alloc = this.field1
        val layout = this.allocState.layout
        val owned = this.allocState.owned
        this.disarmCleaner()
        this.clearHeapSlot()
        Result.Ok(
            Box(
                field0 = BoxRaw.newUnchecked(raw),
                field1 = alloc,
                valueLayout = layout,
                typeLayoutArg = UnresolvedTypeLayout(),
                ownedNative = owned,
                heapSlotArg = BoxHeapSlot(items),
            ),
        )
    } else {
        Result.Err(this)
    }
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
public fun <T, A : Allocator> Box<MaybeUninit<T>, A>.assumeInit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<T, A> {
    val mu = this.readValue()
    val addr = this.field0.asPtr().rawAddress
    val value: T =
        when {
            mu.value != null -> mu.assumeInit()
            typeLayoutT !is UnresolvedTypeLayout && addr != 0L && this.allocState.layout.size() > 0 ->
                typeLayoutT.readNative(addr)
            else -> mu.assumeInit()
        }
    if (typeLayoutT !is UnresolvedTypeLayout && addr != 0L && this.allocState.layout.size() > 0) {
        typeLayoutT.writeNative(addr, value)
    }
    val raw = this.field0.cast<T>()
    val alloc = this.field1
    val layout = this.allocState.layout
    val owned = this.allocState.owned
    this.disarmCleaner()
    this.clearHeapSlot()
    return Box(
        field0 = raw,
        field1 = alloc,
        valueLayout = layout,
        typeLayoutArg = typeLayoutT,
        ownedNative = owned,
        heapSlotArg = BoxHeapSlot(value),
    )
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
public fun <T, A : Allocator> Box.Companion.write(
    boxed: Box<MaybeUninit<T>, A>,
    value: T,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Box<T, A> {
    boxed.readValue().write(value)
    val addr = boxed.allocState.addr
    if (typeLayoutT !is UnresolvedTypeLayout && addr != 0L && boxed.allocState.layout.size() > 0) {
        typeLayoutT.writeNative(addr, value)
    }
    val raw = boxed.field0.cast<T>()
    val alloc = boxed.field1
    val layout = boxed.allocState.layout
    val owned = boxed.allocState.owned
    boxed.disarmCleaner()
    boxed.clearHeapSlot()
    return Box(
        field0 = raw,
        field1 = alloc,
        valueLayout = layout,
        typeLayoutArg = typeLayoutT,
        ownedNative = owned,
        heapSlotArg = BoxHeapSlot(value),
    )
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
    val items = this.readValue().map { it.assumeInit() }
    val raw = this.field0.cast<List<T>>()
    val alloc = this.field1
    val layout = this.allocState.layout
    val owned = this.allocState.owned
    this.disarmCleaner()
    this.clearHeapSlot()
    return Box(
        field0 = raw,
        field1 = alloc,
        valueLayout = layout,
        typeLayoutArg = UnresolvedTypeLayout(),
        ownedNative = owned,
        heapSlotArg = BoxHeapSlot(items),
    )
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
public fun <T> Box.Companion.fromRaw(
    raw: MutPtr,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Box<T, Global> {
    // SAFETY: Upheld by caller.
    return Box.fromRawIn(raw, Global(), typeLayoutT)
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
public fun <T> Box.Companion.fromNonNull(
    ptr: NonNull,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Box<T, Global> {
    // SAFETY: Upheld by caller.
    return Box.fromRaw(ptr.asPtr(), typeLayoutT)
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
    val (ptr, _) = Box.intoRawWithAllocator(b)
    return ptr
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
public fun <T> defaultBoxT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): Box<T, Global> =
    Box.new(defaultTFn(), typeLayoutT = typeLayoutT)

/**
 * Creates an empty `[T]` inside a `Holder`.
 */
public fun <T> defaultBoxTSlice(): Box<List<T>, Global> = Box.newIn(emptyList(), Global())

public fun defaultBoxString(): Box<String, Global> = Box.new("")

public fun <T> defaultPinBoxT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): Pin<Box<T, Global>> =
    Box.intoPin(defaultBoxT(typeLayoutT = typeLayoutT, defaultTFn = defaultTFn))

public fun <T : Allocator, A : Allocator> Box<T, A>.allocate(layout: Layout): Result<NonNull, AllocError> =
    this.deref().allocate(layout)

public fun <T : Allocator, A : Allocator> Box<T, A>.allocateZeroed(layout: Layout): Result<NonNull, AllocError> =
    this.deref().allocateZeroed(layout)

public fun <T : Allocator, A : Allocator> Box<T, A>.deallocate(ptr: NonNull, layout: Layout) {
    // SAFETY: the safety contract must be upheld by the caller
    this.deref().deallocate(ptr, layout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.deref().grow(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.deref().growZeroed(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Box<T, A>.shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.deref().shrink(ptr, oldLayout, newLayout)
}

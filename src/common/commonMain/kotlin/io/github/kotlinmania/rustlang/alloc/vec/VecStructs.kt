package io.github.kotlinmania.rustlang.alloc.vec

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.alloc.vec.drain.Drain
import io.github.kotlinmania.rustlang.alloc.vec.extractif.ExtractIf
import io.github.kotlinmania.rustlang.alloc.vec.intoiter.IntoIter
import io.github.kotlinmania.rustlang.alloc.vec.peekmut.PeekMut
import io.github.kotlinmania.rustlang.alloc.vec.setlenondrop.SetLenOnDrop
import io.github.kotlinmania.rustlang.alloc.vec.splice.Splice
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.MIN_SVE_SIZE_1
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.MIN_SVE_SIZE_2
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.MIN_SVE_SIZE_4
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.MIN_SVE_SIZE_8
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.PanicGuard
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.chunkedRetain
import io.github.kotlinmania.rustlang.alloc.wtf8.cloneInto
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.hint.unlikely
import io.github.kotlinmania.rustlang.core.marker.Freeze
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter.Iter
import io.github.kotlinmania.rustlang.core.slice.raw.fromRawParts
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition

/**
 * A contiguous growable array type, written as `List<T>`, short for 'vector'.
 *
 * # Examples
 *
 * ```kotlin
 * var vec = Vec.new()
 * vec.push(1)
 * vec.push(2)
 * check(vec.len() == 2)
 * check(vec[0] == 1)
 * check(vec.pop() == 2)
 * check(vec.len() == 1)
 * vec[0] = 7
 * check(vec[0] == 7)
 * vec.extend(listOf(1, 2, 3))
 * for (x in vec) {
 *     println("$x")
 * }
 * check(vec == listOf(7, 1, 2, 3))
 * ```
 *
 * The [vec] macro is provided for convenient initialization:
 *
 * ```kotlin
 * var vec1 = mutableListOf(1, 2, 3)
 * vec1.add(4)
 * val vec2 = Vec.from(listOf(1, 2, 3, 4))
 * check(vec1 == vec2)
 * ```
 *
 * It can also initialize each element of a `List<T>` with a given value.
 * This may be more efficient than performing allocation and initialization
 * in separate steps, especially when initializing a vector of zeros:
 *
 * ```kotlin
 * val vec = mutableListOf(0, 5)
 * check(vec == listOf(0, 0, 0, 0, 0))
 * // The following is equivalent, but potentially slower:
 * var vec1 = Vec.withCapacity(5)
 * vec1.resize(5, 0)
 * check(vec1 == listOf(0, 0, 0, 0, 0))
 * ```
 *
 * For more information, see
 * [Capacity and Reallocation](#capacity-and-reallocation).
 *
 * Use a `List<T>` as an efficient stack:
 *
 * ```kotlin
 * var stack = Vec.new()
 * stack.push(1)
 * stack.push(2)
 * stack.push(3)
 * while (true) {
 *     val matchVal = stack.pop()
 *     if (matchVal == null) break
 *     val top = matchVal
 *     // Prints 3, 2, 1
 *     println("$top")
 * }
 * ```
 *
 * # Indexing
 *
 * The `List` type allows access to values by index, because it implements the
 * [Index] trait. An example will be more explicit:
 *
 * ```kotlin
 * val v = mutableListOf(0, 2, 4, 6)
 * println("${v[1]}")
 * // it will display '2'
 * ```
 *
 * However be careful: if you try to access an index which isn't in the `List`,
 * your software will panic You cannot do this:
 *
 * ```kotlin
 * val v = mutableListOf(0, 2, 4, 6)
 * println("${v[6]}")
 * // it will panic
 * ```
 *
 * Use [get] and [getMut] if you want to check whether the index is in
 * the `List`.
 *
 * # Slicing
 *
 * A `List` can be mutable. On the other hand, slices are read-only objects.
 * To get a [slice][prim@slice], use [&]. Example:
 *
 * ```kotlin
 * fun readSlice(slice: List<Int>) {
 *     // ...
 * }
 * val v = mutableListOf(0, 1)
 * readSlice(v)
 * // ... and that's all!
 * // you can also do it like this:
 * val u: List<Int> = v
 * // or like this:
 * val u1: List<Any?> = v
 * ```
 *
 * In Rust, it's more common to pass slices as arguments rather than vectors
 * when you just want to provide read access. The same goes for [String] and
 * [str].
 *
 * # Capacity and reallocation
 *
 * The capacity of a vector is the amount of space allocated for any future
 * elements that will be added onto the vector. This is not to be confused with
 * the *length* of a vector, which specifies the number of actual elements
 * within the vector. If a vector's length exceeds its capacity, its capacity
 * will automatically be increased, but its elements will have to be
 * reallocated.
 *
 * For example, a vector with capacity 10 and length 0 would be an empty vector
 * with space for 10 more elements. Pushing 10 or fewer elements onto the
 * vector will not change its capacity or cause reallocation to occur. However,
 * if the vector's length is increased to 11, it will have to reallocate, which
 * can be slow. For this reason, it is recommended to use [Vec.withCapacity]
 * whenever possible to specify how big the vector is expected to get.
 *
 * # Guarantees
 *
 * The following section is to be read as an addendum of the
 * [memory layout](self#memory-layout) section.
 *
 * Due to its incredibly fundamental nature, `List` makes a lot of guarantees
 * about its design. This ensures that it's as low-overhead as possible in
 * the general case, and can be correctly manipulated in primitive ways
 * by unchecked code.
 *
 * A vector containing the elements `'a'` and `'b'` with capacity 4 can be
 * visualized as below. The top part is the `List` struct, it contains a
 * pointer to the head of the allocation in the heap, length and capacity.
 * The bottom part is the allocation on the heap, a contiguous memory block.
 *
 * ```text
 *             ptr      len  capacity
 *        +--------+--------+--------+
 *        | 0x0123 |      2 |      4 |
 *        +--------+--------+--------+
 *             |
 *             v
 * Heap   +--------+--------+--------+--------+
 *        |    'a' |    'b' | uninit | uninit |
 *        +--------+--------+--------+--------+
 * ```
 *
 * - **uninit** represents memory that is not initialized, see [MaybeUninit].
 * - Note: the ABI is not stable and `List` makes no guarantees about its memory
 *   layout (including the order of fields).
 *
 * `List` will never perform a "small optimization" where elements are actually
 * stored on the stack for two reasons:
 *
 * * It would make it more difficult for unchecked code to correctly manipulate
 *   a `List`. The contents of a `List` wouldn't have a stable address if it were
 *   only moved, and it would be more difficult to determine if a `List` had
 *   actually allocated memory.
 *
 * * It would penalize the general case, incurring an additional branch
 *   on every access.
 *
 * `List` will never automatically shrink itself, even if completely empty. This
 * ensures no unnecessary allocations or deallocations occur. Emptying a `List`
 * and then filling it back up to the same [len] should incur no calls to
 * the allocator. If you wish to free up unused memory, use
 * [shrinkToFit] or [shrinkTo].
 *
 * [push] and [insert] will never (re)allocate if the reported capacity is
 * sufficient. [push] and [insert] *will* (re)allocate if
 * <code>[len] == [capacity]</code>. That is, the reported capacity is completely
 * accurate, and can be relied on. It can even be used to manually free the memory
 * allocated by a `List` if desired. Bulk insertion methods *may* reallocate, even
 * when not necessary.
 *
 * `List` does not guarantee any particular growth strategy when reallocating
 * when full, nor when [reserve] is called. The current strategy is basic
 * and it may prove desirable to use a non-constant growth factor. Whatever
 * strategy is used will of course guarantee *O*(1) amortized [push].
 *
 * It is guaranteed, in order to respect the intentions of the programmer, that
 * all of `listOf(e1, e2, ..., eN)`, `listOf(x; n)`, and [Vec.withCapacity] produce a `List`
 * that requests an allocation of the exact size needed for precisely `n` elements from the allocator,
 * and no other size (such as, for example: a size rounded up to the nearest power of 2).
 * The allocator will return an allocation that is at least as large as requested, but it may be larger.
 *
 * It is guaranteed that the [Vec.capacity] method returns a value that is at least the requested capacity
 * and not more than the allocated capacity.
 *
 * The method [Vec.shrinkToFit] will attempt to discard excess capacity an allocator has given to a `List`.
 * If <code>[len] == [capacity]</code>, then a `List<T>` can be converted
 * to and from a [`Box`][owned slice] without reallocating or moving the elements.
 * `List` exploits this fact as much as reasonable when implementing common conversions
 * such as [intoBoxedSlice].
 *
 * `List` will not specifically overwrite any data that is removed from it,
 * but also won't specifically preserve it. Its uninitialized memory is
 * scratch space that it may use however it wants. It will generally just do
 * whatever is most efficient or otherwise easy to implement. Do not rely on
 * removed data to be erased for security purposes. Even if you drop a `List`, its
 * buffer may simply be reused by another allocation. Even if you zero a `List`s memory
 * first, that might not actually happen because the optimizer does not consider
 * this a side-effect that must be preserved. There is one case which we will
 * not break, however: using `unchecked` code to write to the excess capacity,
 * and then increasing the length to match, is always valid.
 *
 * Currently, `List` does not guarantee the order in which elements are dropped.
 * The order has changed in the past and may change again.
 *
 * [get]: slice.get
 * [getMut]: slice.getMut
 * [str]: type@str
 * [shrinkToFit]: Vec.shrinkToFit
 * [shrinkTo]: Vec.shrinkTo
 * [capacity]: Vec.capacity
 * [capacity]: Vec.capacity
 * [Vec.capacity]: Vec.capacity
 * [sizeOf::\<T>]: sizeOf
 * [len]: Vec.len
 * [len]: Vec.len
 * [push]: Vec.push
 * [insert]: Vec.insert
 * [reserve]: Vec.reserve
 * [Vec.withCapacity]: Vec.withCapacity
 * [owned slice]: Holder
 * [intoBoxedSlice]: Vec.intoBoxedSlice
 */
public open class Vec<T, A : Allocator> internal constructor(
    bufCtorArg: RawVec<T, A>,
    public var len: Int,
) : ExtendFromWithinSpec, DerefPure, AsRef<Vec<T, A>>, AsMut<Vec<T, A>>, Iterable<T> {
    public var buf: RawVec<T, A> = bufCtorArg
        internal set

    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    /**
     * Appends an element to the back of a collection.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2)
     * vec.add(3)
     * check(vec == listOf(1, 2, 3))
     * ```
     *
     * # Time complexity
     *
     * Takes amortized *O*(1) time. If the vector's length would exceed its
     * capacity after the push, *O*(*capacity*) time is taken to copy the
     * vector's elements to a larger allocation. This expensive operation is
     * offset by the *capacity* *O*(1) insertions it allows.
     */
    public open fun push(value: T) {
        this.pushMut(value)
    }

    /**
     * Appends an element to the back of a collection, returning a reference to it.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2)
     * val last = vec.pushMut(3)
     * check(last == 3)
     * check(vec == listOf(1, 2, 3))
     * val last1 = vec.pushMut(3)
     * last1 += 1
     * check(vec == listOf(1, 2, 3, 4))
     * ```
     *
     * # Time complexity
     *
     * Takes amortized *O*(1) time. If the vector's length would exceed its
     * capacity after the push, *O*(*capacity*) time is taken to copy the
     * vector's elements to a larger allocation. This expensive operation is
     * offset by the *capacity* *O*(1) insertions it allows.
     */
    public open fun pushMut(value: T): T {
        // Inform codegen that the length does not change across growOne().
        val len = this.len
        // This will panic or abort if we would allocate > isize.Max bytes
        // or if the length increment would overflow for zero-sized types.
        if (len == this.buf.capacity()) {
            this.buf.growOne()
        }
        // ignore-tidy-undocumented-unchecked
        return run {
            val end = this.asMutPtr().add(len)
            write(end, value)
            this.len = len + 1
            // SAFETY: We just wrote a value to the pointer that will live the scope of the reference.
            end
        }
    }

    /**
     * Decomposes a `List<T>` into its raw components: `(pointer, length, capacity, allocator)`.
     *
     * Returns the raw pointer to the underlying data, the length of the vector (in elements),
     * the allocated capacity of the data (in elements), and the allocator. These are the same
     * arguments in the same order as the arguments to [fromRawPartsIn].
     *
     * After calling this function, the caller is responsible for the
     * memory previously managed by the `List`. The only way to do
     * this is to convert the raw pointer, length, and capacity back
     * into a `List` with the [fromRawPartsIn] function, allowing
     * the destructor to perform the cleanup.
     *
     * [fromRawPartsIn]: Vec.fromRawPartsIn
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
     * import std.alloc.System
     *
     * var v: Vec<Int, System> = Vec.newIn(System)
     * v.push(-1)
     * v.push(0)
     * v.push(1)
     * val (ptr, len, cap, alloc) = v.intoRawPartsWithAllocator()
     * val rebuilt = run {
     *     // We can now make changes to the components, such as
     *     // transmuting the raw pointer to a compatible type.
     *     val ptr1 = mutPtrFromRustCast(ptr)
     *     Vec.fromRawPartsIn(ptr1, len, cap, alloc)
     * }
     * check(rebuilt == listOf(4294967295, 0, 1))
     * ```
     */
    public open fun intoRawPartsWithAllocator(): List<Any?> {
        val me = this
        val len = me.len()
        val capacity = me.capacity()
        val ptr = me.asMutPtr()
        // ignore-tidy-undocumented-unchecked
        val alloc = read(me.allocator())
        return listOf(ptr, len, capacity, alloc)
    }

    /**
     * Decomposes a `List<T>` into its raw components: `(NonNull pointer, length, capacity, allocator)`.
     *
     * Returns the `NonNull` pointer to the underlying data, the length of the vector (in elements),
     * the allocated capacity of the data (in elements), and the allocator. These are the same
     * arguments in the same order as the arguments to [fromPartsIn].
     *
     * After calling this function, the caller is responsible for the
     * memory previously managed by the `List`. The only way to do
     * this is to convert the `NonNull` pointer, length, and capacity back
     * into a `List` with the [fromPartsIn] function, allowing
     * the destructor to perform the cleanup.
     *
     * [fromPartsIn]: Vec.fromPartsIn
     *
     * # Examples
     *
     * ```kotlin
     * import std.alloc.System
     *
     * var v: Vec<Int, System> = Vec.newIn(System)
     * v.push(-1)
     * v.push(0)
     * v.push(1)
     * val (ptr, len, cap, alloc) = v.intoPartsWithAllocator()
     * val rebuilt = run {
     *     // We can now make changes to the components, such as
     *     // transmuting the raw pointer to a compatible type.
     *     val ptr1 = ptr.cast()
     *     Vec.fromPartsIn(ptr1, len, cap, alloc)
     * }
     * check(rebuilt == listOf(4294967295, 0, 1))
     * ```
     */
    public open fun intoPartsWithAllocator(): List<Any?> {
        val (ptr, len, capacity, alloc) = this.intoRawPartsWithAllocator()
        // SAFETY: A `List` always has a non-null pointer.
        return listOf(NonNull.newUnchecked(ptr), len, capacity, alloc)
    }

    /**
     * Returns the total number of elements the vector can hold without
     * reallocating.
     *
     * # Examples
     *
     * ```kotlin
     * var vec: Vec<Int> = Vec.withCapacity(10)
     * vec.push(42)
     * check(vec.capacity() >= 10)
     * ```
     *
     * A vector with zero-sized elements will always have a capacity of usize.Max:
     *
     * ```kotlin
     * open class ZeroSized {
     *     public open fun clone(): ZeroSized = ZeroSized()
     * }
     * fun main() {
     *     check(mem.sizeOf<ZeroSized>(typeLayoutT = ZeroSized.typeLayout()) == 0)
     *     val v = mutableListOf(ZeroSized(), 0)
     *     check(v.capacity() == Int.MAX_VALUE)
     * }
     * ```
     */
    public open fun capacity(): Int = this.buf.capacity()

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the given `List<T>`. The collection may reserve more space to
     * speculatively avoid frequent reallocations. After calling `reserve`,
     * capacity will be greater than or equal to `self.len() + additional`.
     * Does nothing if capacity is already sufficient.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1)
     * vec.reserve(10)
     * check(vec.capacity() >= 11)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.buf.reserve(this.len, additional)
    }

    /**
     * Reserves the minimum capacity for at least `additional` more elements to
     * be inserted in the given `List<T>`. Unlike [reserve], this will not
     * deliberately over-allocate to speculatively avoid frequent allocations.
     * After calling `reserveExact`, capacity will be greater than or equal to
     * `self.len() + additional`. Does nothing if the capacity is already
     * sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [reserve] if future insertions are expected.
     *
     * [reserve]: Vec.reserve
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1)
     * vec.reserveExact(10)
     * check(vec.capacity() >= 11)
     * ```
     */
    public open fun reserveExact(additional: Int) {
        this.buf.reserveExact(this.len, additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the given `List<T>`. The collection may reserve more space to speculatively avoid
     * frequent reallocations. After calling `tryReserve`, capacity will be
     * greater than or equal to `self.len() + additional` if it returns
     * `Ok(())`. Does nothing if capacity is already sufficient. This method
     * preserves the contents even if an error occurs.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * fun processData(data: List<UInt>): Result<Vec<UInt>> {
     *     var output: Vec<UInt> = Vec.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserve(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     output.extend(data.map({ value ->
     *         value * 2 + 5
     *     }))
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.buf.tryReserve(this.len, additional)

    /**
     * Tries to reserve the minimum capacity for at least `additional`
     * elements to be inserted in the given `List<T>`. Unlike [tryReserve],
     * this will not deliberately over-allocate to speculatively avoid frequent
     * allocations. After calling `tryReserveExact`, capacity will be greater
     * than or equal to `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: Vec.tryReserve
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * fun processData(data: List<UInt>): Result<Vec<UInt>> {
     *     var output: Vec<UInt> = Vec.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserveExact(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     output.extend(data.map({ value ->
     *         value * 2 + 5
     *     }))
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.buf.tryReserveExact(this.len, additional)

    /**
     * Shrinks the capacity of the vector as much as possible.
     *
     * When `vec.len() == vec.capacity()` the allocation is unchanged. Otherwise, the behavior of
     * this method depends on the allocator, which may either shrink the vector in-place or
     * reallocate. The resulting vector might still have some excess capacity, just as
     * is the case for [withCapacity]. See [Allocator.shrink] for more details.
     *
     * [withCapacity]: Vec.withCapacity
     *
     * # Examples
     *
     * ```kotlin
     * var vec = Vec.withCapacity(10)
     * vec.extend(listOf(1, 2, 3))
     * check(vec.capacity() >= 10)
     * vec.shrinkToFit()
     * check(vec.capacity() >= 3)
     * ```
     */
    public open fun shrinkToFit() {
        // The capacity is never less than the length, and there's nothing to do when
        // they are equal, so we can avoid the panic case in `RawVec.shrinkToFit`
        // by only calling it with a greater capacity.
        if (this.capacity() > this.len) {
            this.buf.shrinkToFit(this.len)
        }
    }

    /**
     * Shrinks the capacity of the vector with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = Vec.withCapacity(10)
     * vec.extend(listOf(1, 2, 3))
     * check(vec.capacity() >= 10)
     * vec.shrinkTo(4)
     * check(vec.capacity() >= 4)
     * vec.shrinkTo(0)
     * check(vec.capacity() >= 3)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        if (this.capacity() > minCapacity) {
            this.buf.shrinkToFit(max(this.len, minCapacity))
        }
    }

    /**
     * Tries to shrink the capacity of the vector as much as possible
     *
     * The behavior of this method depends on the allocator, which may either shrink the vector
     * in-place or reallocate. The resulting vector might still have some excess capacity, just as
     * is the case for [withCapacity]. See [Allocator.shrink] for more details.
     *
     * [withCapacity]: Vec.withCapacity
     *
     * # Errors
     *
     * This function returns an error if the allocator fails to shrink the allocation,
     * the vector thereafter is still safe to use, the capacity remains unchanged
     * however. See [Allocator.shrink].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(vecFallibleShrink)
     * public fun doctestEntry() {
     *     var vec = Vec.withCapacity(10)
     *     vec.extend(listOf(1, 2, 3))
     *     check(vec.capacity() >= 10)
     *     vec.tryShrinkToFit().expect("for this test, shrink shouldn't fail")
     *     check(vec.capacity() >= 3)
     * }
     * ```
     */
    public open fun tryShrinkToFit(): Result<Unit, TryReserveError> =
        if (this.capacity() > this.len) {
            this.buf.tryShrinkToFit(this.len)
        } else {
            Result.Ok(Unit)
        }

    /**
     * Shrinks the capacity of the vector with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Errors
     *
     * This function returns an error if the allocator fails to shrink the allocation,
     * the vector thereafter is still safe to use, the capacity remains unchanged
     * however. See [Allocator.shrink].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(vecFallibleShrink)
     * public fun doctestEntry() {
     *     var vec = Vec.withCapacity(10)
     *     vec.extend(listOf(1, 2, 3))
     *     check(vec.capacity() >= 10)
     *     vec.tryShrinkTo(4).expect("for this test, shrink shouldn't fail")
     *     check(vec.capacity() >= 4)
     *     vec.tryShrinkTo(0).expect("this is a no-op and thus the allocator isn't involved.")
     *     check(vec.capacity() >= 3)
     * }
     * ```
     */
    public open fun tryShrinkTo(minCapacity: Int): Result<Unit, TryReserveError> =
        if (this.capacity() > minCapacity) {
            this.buf.tryShrinkToFit(max(this.len, minCapacity))
        } else {
            Result.Ok(Unit)
        }

    /**
     * Converts the vector into a boxed slice.
     *
     * This conversion is in-place if `vec.len() == vec.capacity()`. Otherwise excess capacity
     * is discarded by reallocating before doing the conversion.
     *
     * # Examples
     *
     * ```kotlin
     * val v = mutableListOf(1, 2, 3)
     * val slice = v.intoBoxedSlice()
     * ```
     *
     * Any excess capacity is removed:
     *
     * ```kotlin
     * var vec = Vec.withCapacity(10)
     * vec.extend(listOf(1, 2, 3))
     * check(vec.capacity() >= 10)
     * val slice = vec.intoBoxedSlice()
     * check(slice.intoVec().capacity() == 3)
     * ```
     */
    public open fun intoBoxedSlice(): List<T> {
        this.shrinkToFit()
        val me = this
        // ignore-tidy-undocumented-unchecked
        return run {
            val buf = read(me.buf)
            val len = me.len()
            buf.intoBox(len).assumeInit()
        }
    }

    /**
     * Converts the Vec into a boxed array. This conversion will discard any spare capacity,
     * if there is any, see [Vec.shrinkToFit].
     * If you merely wish for a reference to an array, use [`asArray`](https://doc.rust-lang.org/stable/std/primitive.slice.html#method.asArray).
     *
     * # Errors
     *
     * Returns the original `List<T>` in the `Err` variant if [Vec.len] does not equal `N`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(allocSliceIntoArray)
     * public fun doctestEntry() {
     *     val vec: Vec<Int> = mutableListOf(1, 2, 3)
     *     val boxArray: IntArray = vec.toMutableList().intoArray().unwrap()
     *     val notEnoughElements: Result<IntArray> = vec.intoArray()
     *     check(notEnoughElements == Result.failure(mutableListOf(1, 2, 3)))
     * }
     * ```
     */
    public open fun intoArray(n: Int): Result<List<T>, Vec<T, A>> {
        return if (this.len() == n) {
            // SAFETY: `Holder.intoArray` is guaranteed to return `Ok` if the
            // length of the slice is equal to `N`.
            // `self.intoBoxedSlice().len()` is equal to `self.len()`,
            // which we just checked.
            Result.Ok(this.intoBoxedSlice().intoArray().unwrapUnchecked())
        } else {
            Result.Err(this)
        }
    }

    /**
     * Shortens the vector, keeping the first `len` elements and dropping
     * the rest.
     *
     * If `len` is greater or equal to the vector's current length, this has
     * no effect.
     *
     * The [drain] method can emulate `truncate`, but causes the excess
     * elements to be returned instead of dropped.
     *
     * Note that this method has no effect on the allocated capacity
     * of the vector.
     *
     * # Examples
     *
     * Truncating a five element vector to two elements:
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3, 4, 5)
     * vec.truncate(2)
     * check(vec == listOf(1, 2))
     * ```
     *
     * No truncation occurs when `len` is greater than or equal to the
     * vector's current length:
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3)
     * vec.truncate(8)
     * check(vec == listOf(1, 2, 3))
     * ```
     *
     * Truncating when `len == 0` is equivalent to calling the [clear]
     * method.
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3)
     * vec.truncate(0)
     * check(vec == listOf())
     * ```
     *
     * [clear]: Vec.clear
     * [drain]: Vec.drain
     */
    public open fun truncate(len: Int) {
        // SAFETY: `BufWriter.flushBuf` assumes that this will not
        // de-initialize any elements of the spare capacity.
        // This is safe because:
        //
        // * the slice passed to `dropInPlace` is valid; the `len >= self.len`
        // case avoids creating an invalid slice, and
        // * the `len` of the vector is shrunk before calling `dropInPlace`,
        // such that no value will be dropped twice in case `dropInPlace`
        // were to panic once (if it panics twice, the program aborts).
        run {
            if (len >= this.len) {
                return
            }
            val remainingLen = this.len - len
            val s = this.asMutPtr().add(len).castSlice(remainingLen)
            this.len = len
            dropInPlace(s)
        }
    }

    /**
     * Extracts a slice containing the entire vector.
     *
     * Equivalent to `s[..]`.
     *
     * # Examples
     *
     * ```kotlin
     * val buffer = mutableListOf(1, 2, 3, 5, 8)
     * io.sink().write(buffer.asSlice()).unwrap()
     * ```
     */
    public open fun asSlice(): List<T> {
        // SAFETY: `slice.fromRawParts` requires pointee is a contiguous, aligned buffer of size
        // `len` containing properly-initialized `T`s. Data must not be mutated for the returned
        // scope. Further, `len * sizeOf<T>` <= `Long.MAX`, and allocation does not
        // "wrap" through overflowing memory addresses.
        //
        // * Vec API guarantees that self.buf:
        // * contains only properly-initialized items within 0..len
        // * is aligned, contiguous, and valid for `len` reads
        // * obeys size and address-wrapping constraints
        //
        // * We only construct  references to `self.buf` through `self` methods; borrow-
        // check ensures that it is not possible to mutably alias `self.buf` within the
        // returned scope.
        return ConstPtr(this.asPtr().rawAddress, (this.len).toLong())
    }

    /**
     * Extracts a mutable slice of the entire vector.
     *
     * Equivalent to `s[..]`.
     *
     * # Examples
     *
     * ```kotlin
     * var buffer = mutableListOf(0, 3)
     * io.repeat(0x5).readExact(buffer.asMutSlice()).unwrap()
     * ```
     */
    public open fun asMutSlice(): MutableList<T> {
        // SAFETY: `BufWriter.flushBuf` assumes that this will not
        // de-initialize any elements of the spare capacity.
        // SAFETY: `slice.fromRawPartsMut` requires pointee is a contiguous, aligned buffer of
        // size `len` containing properly-initialized `T`s. Data must not be accessed through any
        // other pointer for the returned scope. Further, `len * sizeOf<T>` <=
        // `Long.MAX` and allocation does not "wrap" through overflowing memory addresses.
        //
        // * Vec API guarantees that self.buf:
        // * contains only properly-initialized items within 0..len
        // * is aligned, contiguous, and valid for `len` reads
        // * obeys size and address-wrapping constraints
        //
        // * We only construct references to `self.buf` through `self` and `self` methods;
        // borrow-check ensures that it is not possible to construct a reference to `self.buf`
        // within the returned scope.
        return ConstPtr(this.asMutPtr().rawAddress, (this.len).toLong())
    }

    /**
     * Returns a raw pointer to the vector's buffer, or a dangling raw pointer
     * valid for zero sized reads if the vector [didn't allocate].
     *
     * The caller must ensure that the vector outlives the pointer this
     * function returns, or else it will end up dangling.
     * Modifying the vector may cause its buffer to be reallocated,
     * which would also make any pointers to it invalid.
     *
     * The caller must also ensure that the memory the pointer (non-transitively) points to
     * is never written to (except inside an `UnsafeCell`) using this pointer or any pointer
     * derived from it. If you need to mutate the contents of the slice, use [asMutPtr].
     *
     * This method guarantees that for the purpose of the aliasing model, this method
     * does not materialize a reference to the underlying slice, and thus the returned pointer
     * will remain valid when mixed with other calls to [asPtr], [asMutPtr],
     * and [asNonNull].
     * Note that calling other methods that materialize mutable references to the slice,
     * or mutable references to specific elements you are planning on accessing through this pointer,
     * as well as writing to those elements, may still invalidate this pointer.
     * See the second example below for how this guarantee can be used.
     *
     * # Examples
     *
     * ```kotlin
     * val x = mutableListOf(1, 2, 4)
     * val xPtr = x.asPtr()
     * for (i in 0 until x.size) {
     *     check(xPtr.add(i) == 1 shl i.toInt())
     * }
     * ```
     *
     * Due to the aliasing guarantee, the following code is legal:
     *
     * ```kotlin
     * run {
     *     var v = mutableListOf(0, 1, 2)
     *     val ptr1 = v.asPtr()
     *     ptr1.read()
     *     val ptr2 = v.asMutPtr().offset(2)
     *     ptr2.write(2)
     *     // Notably, the write to `ptr2` did *not* invalidate `ptr1`
     *     // because it mutated a different element:
     *     ptr1.read()
     * }
     * ```
     *
     * [asMutPtr]: Vec.asMutPtr
     * [asPtr]: Vec.asPtr
     * [asNonNull]: Vec.asNonNull
     * [didn't allocate]: self#memory-layout
     */
    public open fun asPtr(): ConstPtr {
        // We shadow the slice method of the same name to avoid going through
        // `deref`, which creates an intermediate reference.
        return this.buf.ptr()
    }

    /**
     * Returns a raw mutable pointer to the vector's buffer, or a dangling
     * raw pointer valid for zero sized reads if the vector [didn't allocate].
     *
     * The caller must ensure that the vector outlives the pointer this
     * function returns, or else it will end up dangling.
     * Modifying the vector may cause its buffer to be reallocated,
     * which would also make any pointers to it invalid.
     *
     * This method guarantees that for the purpose of the aliasing model, this method
     * does not materialize a reference to the underlying slice, and thus the returned pointer
     * will remain valid when mixed with other calls to [asPtr], [asMutPtr],
     * and [asNonNull].
     * Note that calling other methods that materialize references to the slice,
     * or references to specific elements you are planning on accessing through this pointer,
     * may still invalidate this pointer.
     * See the second example below for how this guarantee can be used.
     *
     * The method also guarantees that, as long as `T` is not zero-sized and the capacity is
     * nonzero, the pointer may be passed into [dealloc] with a layout of
     * `Layout.array<T>(capacity)` in order to deallocate the backing memory. If this is done,
     * be careful not to run the destructor of the `List`, as dropping it will result in
     * double-frees. Wrapping the `List` in a [ManuallyDrop] is the typical way to achieve this.
     *
     * # Examples
     *
     * ```kotlin
     * // Allocate vector big enough for 4 elements.
     * val size = 4
     * var x: Vec<Int> = Vec.withCapacity(size)
     * val xPtr = x.asMutPtr()
     * // Initialize elements via raw pointer writes, then set length.
     * run {
     *     for (i in 0 until size) {
     *         xPtr.add(i) = i.toInt()
     *     }
     *     x.setLen(size)
     * }
     * check(x == listOf(0, 1, 2, 3))
     * ```
     *
     * Due to the aliasing guarantee, the following code is legal:
     *
     * ```kotlin
     * run {
     *     var v = mutableListOf(0)
     *     val ptr1 = v.asMutPtr()
     *     ptr1.write(1)
     *     val ptr2 = v.asMutPtr()
     *     ptr2.write(2)
     *     // Notably, the write to `ptr2` did *not* invalidate `ptr1`:
     *     ptr1.write(3)
     * }
     * ```
     *
     * Deallocating a vector using [Box] (which uses [dealloc] internally):
     *
     * ```kotlin
     * import boxed.Holder
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     *
     * var v = mutableListOf(0, 1, 2)
     * val ptr = v.asMutPtr()
     * val capacity = v.capacity()
     * val slicePtr: MutPtr = ptr.sliceFromRawPartsMut(ptr.cast(), capacity)
     * (Holder.fromRaw(slicePtr)).let { }
     * ```
     *
     * [asMutPtr]: Vec.asMutPtr
     * [asPtr]: Vec.asPtr
     * [asNonNull]: Vec.asNonNull
     * [didn't allocate]: self#memory-layout
     */
    public open fun asMutPtr(): MutPtr {
        // We shadow the slice method of the same name to avoid going through
        // `derefMut`, which creates an intermediate reference.
        return this.buf.ptr()
    }

    /**
     * Returns a `NonNull` pointer to the vector's buffer, or a dangling
     * `NonNull` pointer valid for zero sized reads if the vector [didn't allocate].
     *
     * The caller must ensure that the vector outlives the pointer this
     * function returns, or else it will end up dangling.
     * Modifying the vector may cause its buffer to be reallocated,
     * which would also make any pointers to it invalid.
     *
     * This method guarantees that for the purpose of the aliasing model, this method
     * does not materialize a reference to the underlying slice, and thus the returned pointer
     * will remain valid when mixed with other calls to [asPtr], [asMutPtr],
     * and [asNonNull].
     * Note that calling other methods that materialize references to the slice,
     * or references to specific elements you are planning on accessing through this pointer,
     * may still invalidate this pointer.
     * See the second example below for how this guarantee can be used.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(vecAsNonNull)
     * public fun doctestEntry() {
     *     // Allocate vector big enough for 4 elements.
     *     val size = 4
     *     var x: Vec<Int> = Vec.withCapacity(size)
     *     val xPtr = x.asNonNull()
     *     // Initialize elements via raw pointer writes, then set length.
     *     run {
     *         for (i in 0 until size) {
     *             xPtr.add(i).write(i.toInt())
     *         }
     *         x.setLen(size)
     *     }
     *     check(x == listOf(0, 1, 2, 3))
     * }
     * ```
     *
     * Due to the aliasing guarantee, the following code is legal:
     *
     * ```kotlin
     * @feature(vecAsNonNull)
     * public fun doctestEntry() {
     *     run {
     *         var v = mutableListOf(0)
     *         val ptr1 = v.asNonNull()
     *         ptr1.write(1)
     *         val ptr2 = v.asNonNull()
     *         ptr2.write(2)
     *         // Notably, the write to `ptr2` did *not* invalidate `ptr1`:
     *         ptr1.write(3)
     *     }
     * }
     * ```
     *
     * [asMutPtr]: Vec.asMutPtr
     * [asPtr]: Vec.asPtr
     * [asNonNull]: Vec.asNonNull
     * [didn't allocate]: self#memory-layout
     */
    public open fun asNonNull(): NonNull = this.buf.nonNull()

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.buf.allocator()

    /**
     * Forces the length of the vector to `newLen`.
     *
     * This is a low-level operation that maintains none of the normal
     * invariants of the type. Normally changing the length of a vector
     * is done using one of the safe operations instead, such as
     * [truncate], [resize], [extend], or [clear].
     *
     * [truncate]: Vec.truncate
     * [resize]: Vec.resize
     * [extend]: Extend.extend
     * [clear]: Vec.clear
     *
     * # Safety
     *
     * - `newLen` must be less than or equal to [capacity].
     * - The elements at `oldLen .. newLen` must be initialized.
     *
     * [capacity]: Vec.capacity
     *
     * # Examples
     *
     * See [spareCapacityMut] for an example with safe
     * initialization of capacity elements and use of this method.
     *
     * `setLen()` can be useful for situations in which the vector
     * is serving as a buffer for other code, particularly over FFI:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * fun getDictionary(): Vec<UByte>? {
     *     // Per the FFI method's docs, "32768 bytes is always enough".
     *     var dict = Vec.withCapacity(32768)
     *     val localStorageL6C4 = AddressableLocal(0, PrimitiveLayouts.I32)
     *     val dictLength by localStorageL6C4
     *     // SAFETY: When `deflateGetDictionary` returns `Z_OK`, it holds that:
     *     // 1. `dictLength` elements were initialized.
     *     // 2. `dictLength` <= the capacity (32_768)
     *     // which makes `setLen` safe to call.
     *     return run {
     *         // Make the FFI call...
     *         val r = deflateGetDictionary(this.strm, dict.asMutPtr(), RustMutableReference(localStorageL6C4.asMutPtr(), localStorageL6C4.layout))
     *         if (r == Z_OK) {
     *             // ...and update the length to what was initialized.
     *             dict.setLen(dictLength)
     *             dict
     *         } else {
     *             null
     *         }
     *     }
     * }
     * ```
     *
     * While the following example is sound, there is a memory leak since
     * the inner vectors were not freed prior to the `setLen` call:
     *
     * ```kotlin
     * var vec = mutableListOf(mutableListOf(1, 0, 0), mutableListOf(0, 1, 0), mutableListOf(0, 0, 1))
     * // SAFETY:
     * // 1. `oldLen .. 0` is empty so no elements need to be initialized.
     * // 2. `0 <= capacity` always holds whatever `capacity` is.
     * run {
     *     vec.setLen(0)
     * }
     * ```
     *
     * Normally, here, one would use [clear] instead to correctly drop
     * the contents and thus not leak memory.
     *
     * [spareCapacityMut]: Vec.spareCapacityMut
     */
    public open fun setLen(newLen: Int) {
        assertUnsafePrecondition(checkLibraryUb, "Vec::set_len requires that new_len <= capacity()", newLen, newLen, capacity, this, capacity, newLen, capacity)
        this.len = newLen
    }

    /**
     * Removes an element from the vector and returns it.
     *
     * The removed element is replaced by the last element of the vector.
     *
     * This does not preserve ordering of the remaining elements, but is *O*(1).
     * If you need to preserve the element order, use [remove] instead.
     *
     * [remove]: Vec.remove
     *
     * # Panics
     *
     * Panics if `index` is out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf("foo", "bar", "baz", "qux")
     * check(v.swapRemove(1) == "bar")
     * check(v == listOf("foo", "qux", "baz"))
     * check(v.swapRemove(0) == "foo")
     * check(v == listOf("baz", "qux"))
     * ```
     */
    public open fun swapRemove(index: Int): T {
        fun assertFailed(index1: Int, len: Int): Nothing {
            error("swap_remove index (is $index) should be < len (is $len)")
        }
        val len = this.len()
        if (index >= len) {
            assertFailed(index, len)
        }
        // ignore-tidy-undocumented-unchecked
        return run {
            // We replace self[index] with the last element. Note that if the
            // bounds check above succeeds there must be a last element (which
            // can be self[index] itself).
            val value = read(this.asPtr().add(index))
            val basePtr = this.asMutPtr()
            copy(basePtr.add(len - 1), basePtr.add(index), 1)
            this.setLen(len - 1)
            value
        }
    }

    /**
     * Inserts an element at position `index` within the vector, shifting all
     * elements after it to the right.
     *
     * # Panics
     *
     * Panics if `index> len`.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf('a', 'b', 'c')
     * vec.add(1, 'd')
     * check(vec == listOf('a', 'd', 'b', 'c'))
     * vec.add(4, 'e')
     * check(vec == listOf('a', 'd', 'b', 'c', 'e'))
     * ```
     *
     * # Time complexity
     *
     * Takes *O*([Vec.len]) time. All items after the insertion index must be
     * shifted to the right. In the worst case, all elements are shifted when
     * the insertion index is 0.
     */
    public open fun insert(index: Int, element: T) {
        this.insertMut(index, element)
    }

    /**
     * Inserts an element at position `index` within the vector, shifting all
     * elements after it to the right, and returning a reference to the new
     * element.
     *
     * # Panics
     *
     * Panics if `index> len`.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 3, 5, 9)
     * val x = vec.insertMut(3, 6)
     * x += 1
     * check(vec == listOf(1, 3, 5, 7, 9))
     * ```
     *
     * # Time complexity
     *
     * Takes *O*([Vec.len]) time. All items after the insertion index must be
     * shifted to the right. In the worst case, all elements are shifted when
     * the insertion index is 0.
     */
    public open fun insertMut(index: Int, element: T): T {
        fun assertFailed(index1: Int, len: Int): Nothing {
            error("insertion index (is $index) should be <= len (is $len)")
        }
        val len = this.len()
        if (index > len) {
            assertFailed(index, len)
        }
        // space for the new element
        if (len == this.buf.capacity()) {
            this.buf.growOne()
        }
        // ignore-tidy-undocumented-unchecked
        return run {
            // infallible
            // The spot to put the new value
            val p = this.asMutPtr().add(index)
            run {
                if (index < len) {
                    // Shift everything over to make space. (Duplicating the
                    // `index`th element into two consecutive places.)
                    copy(p, p.add(1), len - index)
                }
                // Write it in, overwriting the first copy of the `index`th
                // element.
                write(p, element)
            }
            this.setLen(len + 1)
            p
        }
    }

    /**
     * Removes and returns the element at position `index` within the vector,
     * shifting all elements after it to the left.
     *
     * Note: Because this shifts over the remaining elements, it has a
     * worst-case performance of *O*(*n*). If you don't need the order of elements
     * to be preserved, use [swapRemove] instead. If you'd like to remove
     * elements from the beginning of the `List`, consider using
     * [VecDeque.popFront] instead.
     *
     * [swapRemove]: Vec.swapRemove
     *
     * # Panics
     *
     * Panics if `index` is out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf('a', 'b', 'c')
     * check(v.remove(1) == 'b')
     * check(v == listOf('a', 'c'))
     * ```
     */
    public open fun remove(index: Int): T {
        fun assertFailed(index1: Int, len: Int): Nothing {
            error("removal index (is $index) should be < len (is $len)")
        }
        return when (val matchVal = this.tryRemove(index)) {
            null -> assertFailed(index, this.len())
            else -> {
                val elem = matchVal
                elem
            }
        }
    }

    /**
     * Remove and return the element at position `index` within the vector,
     * shifting all elements after it to the left, or [None] if it does not
     * exist.
     *
     * Note: Because this shifts over the remaining elements, it has a
     * worst-case performance of *O*(*n*). If you'd like to remove
     * elements from the beginning of the `List`, consider using
     * [VecDeque.popFront] instead.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf(1, 2, 3)
     * check(v.tryRemove(0) == 1)
     * check(v.tryRemove(2) == null)
     * ```
     */
    public open fun tryRemove(index: Int): T? {
        val len = this.len()
        if (index >= len) {
            return null
        }
        // infallible
        val ret: Any?
        // ignore-tidy-undocumented-unchecked
        run {
            run {
                // the place we are taking from.
                val ptr = this.asMutPtr().add(index)
                // copy it out, unsafely having a copy of the value on
                // the stack and in the vector at the same time.
                ret = read(ptr)
                // Shift everything down to fill in that spot.
                copy(ptr.add(1), ptr, len - index - 1)
            }
            this.setLen(len - 1)
        }
        return ret
    }

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns `false`.
     * This method operates in place, visiting each element exactly once in the
     * original order, and preserves the order of the retained elements.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3, 4)
     * vec.retain({ x -> x % 2 == 0 })
     * check(vec == listOf(2, 4))
     * ```
     *
     * Because the elements are visited exactly once in the original order,
     * external state may be used to decide which elements to keep.
     *
     * ```kotlin
     * import core.iter
     *
     * var vec = mutableListOf(1, 2, 3, 4, 5)
     * val keep = booleanArrayOf(false, true, true, false, true)
     * var iter = keep.iter()
     * vec.retain({ iter.next()!! })
     * check(vec == listOf(2, 3, 5))
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        this.retainMut({ elem -> f(elem) })
    }

    /**
     * Retains only the elements specified by the predicate, passing a mutable reference to it.
     *
     * In other words, remove all elements `e` such that `f(e)` returns `false`.
     * This method operates in place, visiting each element exactly once in the
     * original order, and preserves the order of the retained elements.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3, 4)
     * vec.retainMut({ x -> if (x <= 3) {
     *     x += 1
     *     true
     * } else {
     *     false
     * } })
     * check(vec == listOf(2, 3, 4))
     * ```
     */
    public open fun retainMut(f: (T) -> Boolean, typeLayoutT: TypeLayout<T>) {
        // Vec: [Kept, Kept, Hole, Hole, Hole, Hole, Unchecked, Unchecked]
        // |            ^- write                ^- read             |
        // |<-              originalLen                          ->|
        // Kept: Elements which predicate returns true on.
        // Hole: Moved or dropped element slot.
        // Unchecked: Unchecked valid elements.
        //
        // This drop guard will be invoked when predicate or `drop` of element panicked.
        // It shifts unchecked elements to cover holes and `setLen` to the correct length.
        // In cases when predicate and `drop` never panick, it will be optimized out.
        open class PanicGuard<T, A : Allocator>(
            public var v: Vec<T, A>,
            public var read: Int,
            public var write: Int,
            public var originalLen: Int,
        ) {
            public open fun drop() {
                val remaining = this.originalLen - this.read
                // SAFETY: Trailing unchecked items must be valid since we never touch them.
                run {
                    copy(this.v.asPtr().add(this.read), this.v.asMutPtr().add(this.write), remaining)
                }
                // SAFETY: After filling holes, all items are in contiguous memory.
                run {
                    this.v.setLen(this.write + remaining)
                }
            }
        }
        val originalLen = this.len()
        if (originalLen == 0) {
            // Empty case: explicit return allows better optimization, vs letting compiler infer it
            return
        }
        run {
            val longEnough =
                when (sizeOf<T>(typeLayoutT = typeLayoutT)) {
                    1 -> originalLen >= MIN_SVE_SIZE_1
                    2 -> originalLen >= MIN_SVE_SIZE_2
                    4 -> originalLen >= MIN_SVE_SIZE_4
                    8 -> originalLen >= MIN_SVE_SIZE_8
                    else -> false
                }
            if (longEnough) {
                // SAFETY: sizeOf::<T>() is 1, 2, 4 or 8, matching
                // the kernel lane widths.
                return chunkedRetain(this, f)
            }
        }
        var read = 0
        while (true) {
            // SAFETY: read < originalLen
            val cur = this.getUncheckedMut(read)
            if (unlikely(!f(cur))) {
                break
            }
            read += 1
            if (read == originalLen) {
                // All elements are kept, return early.
                return
            }
        }
        // Critical section starts here and at least one element is going to be removed.
        // Advance `g.read` early to avoid double drop if `dropInPlace` panicked.
        val g = PanicGuard(v = this, read = read + 1, write = read, originalLen = originalLen)
        // SAFETY: previous `read` is always less than originalLen.
        dropInPlace(g.v.asMutPtr().add(read))
        while (g.read < g.originalLen) {
            // SAFETY: `read` is always less than originalLen.
            val cur1 = g.v.asMutPtr().add(g.read)
            if (!f(cur1)) {
                // Advance `read` early to avoid double drop if `dropInPlace` panicked.
                g.read += 1
                // SAFETY: We never touch this element again after dropped.
                dropInPlace(cur1)
            } else {
                // SAFETY: `read` > `write`, so the slots don't overlap.
                // We use copy for move, and never touch the source element again.
                run {
                    val hole = g.v.asMutPtr().add(g.write)
                    copyNonoverlapping(cur1, hole, 1)
                }
                g.write += 1
                g.read += 1
            }
        }
        // We are leaving the critical section and no panic happened,
        // Commit the length change and forget the guard.
        // SAFETY: `write` is always less than or equal to originalLen.
        g.v.setLen(g.write)
        forget(g)
    }

    /**
     * Retains only the elements specified by the predicate, passing a mutable reference to it.
     *
     * In other words, remove all elements `e` such that `f(e)` returns `false`.
     * This method does not maintain the relative order of the retained elements. If order matters,
     * consider using [Vec.retainMut] instead. However, if no items are removed, then all items
     * are left in their original positions.
     *
     * Generally, this method is faster than [Vec.retainMut] due to its weak ordering guarantees.
     *
     * Time complexity: *O*(*n*)
     *
     * # Examples
     *
     * ```kotlin
     * @feature(retainUnstable)
     * public fun doctestEntry() {
     *     var vec = mutableListOf(4, 3, 2, 1)
     *     vec.retainUnstable({ x -> if (x <= 3) {
     *         x += 1
     *         true
     *     } else {
     *         false
     *     } })
     *     vec.sort()
     *     // it might be in any order, so normalize before asserting
     *     check(vec == listOf(2, 3, 4))
     * }
     * ```
     */
    public open fun retainUnstable(f: (T) -> Boolean) {
        // Currently this does the following:
        // 1) Search backward until you find an element you want to remove
        // 2) Swap this element with an element that was kept in the back
        // (or if there are no remaining elements, simply truncate the vec)
        // 3) Repeat from 1) until there's no element to iterate through
        val originalLen = this.len()
        if (originalLen == 0) {
            // Empty case: explicit return allows better optimization, vs letting compiler infer it
            return
        }
        var readBack = originalLen
        var hole =
            while (true) {
                if (readBack == 0) {
                    // All elements are kept
                    return
                }
                // SAFETY: readBack - 1 < originalLen
                val cur = this.asMutPtr().add(readBack - 1)
                if (unlikely(!f(cur))) {
                    break
                }
                readBack -= 1
            }
        if (readBack < this.len) {
            val swap = this.pop()!!
            hole = swap
        } else {
            this.truncate(this.len - 1)
        }
        readBack -= 1
        while (readBack > 0) {
            // SAFETY: readBack - 1 is always less than current length of
            // the vector and the loop condition, readBack > 0, prevents
            // readBack from underflowing
            hole = this.asMutPtr().add(readBack - 1)
            if (!f(hole)) {
                if (readBack < this.len) {
                    val swap1 = this.pop()!!
                    hole = swap1
                } else {
                    this.truncate(this.len - 1)
                }
            }
            readBack -= 1
        }
    }

    /**
     * Removes all but the first of consecutive elements in the vector that resolve to the same
     * key.
     *
     * If the vector is sorted, this removes all duplicates.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(10, 20, 21, 30, 20)
     * vec.dedupByKey({ i -> i / 10 })
     * check(vec == listOf(10, 20, 30, 20))
     * ```
     */
    public open fun <K> dedupByKey(key: (T) -> K) {
        this.dedupBy({ a, b -> key(a) == key(b) })
    }

    /**
     * Removes all but the first of consecutive elements in the vector that are
     * "equal" according to the given predicate function.
     *
     * The predicate `sameBucket(x, p)` is passed references to two elements.
     * If it returns `true`, the element `x` is removed from the vector.
     *
     * The element `p` occurs *before* `x` in the vector (`[.., p, .., x, ..]`),
     * so `sameBucket(x, p)` is receiving them in reversed order (unlike [windows]).
     *
     * If the vector is sorted, this removes all duplicates. For more complicated predicates
     * however, the order (ascending vs. descending) can matter.
     *
     * [windows]: slice.windows
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf("foo", "bar", "Bar", "baz", "bar")
     * vec.dedupBy({ x, p -> x.eqIgnoreAsciiCase(p) })
     * check(vec == listOf("foo", "bar", "baz", "bar"))
     * ```
     *
     * Both references passed to `sameBucket` are mutable.
     * This allows merging elements by mutating `p` and returning `true`:
     *
     * ```kotlin
     * import core.cmp
     *
     * var ranges = mutableListOf(1 until 2, 2 until 4, 2 until 5, 8 until 9)
     * // Sort ranges by start, and if equal, by end (lexicographically)
     * // Sorting in reverse instead (`x.start.cmp(p.start) ...`) would later fail
     * ranges.sortUnstableBy({ p, x -> p.start.cmp(x.start).then(p.end.cmp(x.end)) })
     * // Merge touching (`1 .. 2` and `2 .. 4`) and then overlapping (`1 .. 4` and `2 .. 5`) ranges
     * ranges.dedupBy({ x, p ->
     *     if (p.end >= x.start) {
     *         p.end = p.end.max(x.end)
     *         true
     *     } else {
     *         false
     *     }
     * })
     * check(ranges == listOf(1 until 5, 8 until 9))
     * ```
     */
    public open fun dedupBy(sameBucket: (T, T) -> Boolean) {
        val len = this.len()
        if (len <= 1) {
            return
        }
        // Check if we ever want to remove anything.
        // This allows to use copyNonOverlapping in next cycle.
        // And avoids any memory writes if we don't need to remove anything.
        var firstDuplicateIdx: Int = 1
        val start = this.asMutPtr()
        while (firstDuplicateIdx != len) {
            val foundDuplicate = run {
                // SAFETY: firstDuplicate always in range [1..len).
                // Note that we start iteration from 1 so we never overflow.
                val prev = start.add((firstDuplicateIdx - 1))
                // ignore-tidy-undocumented-unchecked
                val current = start.add(firstDuplicateIdx)
                // We explicitly say in docs that references are reversed.
                // ignore-tidy-undocumented-unchecked
                sameBucket(current, prev)
            }
            if (foundDuplicate) {
                break
            }
            firstDuplicateIdx += 1
        }
        // Don't need to remove anything.
        // We cannot get bigger than len.
        if (firstDuplicateIdx == len) {
            return
        }
        // SAFETY: firstDuplicateIdx always in range [1..len)
        run {
            this.dedupTail(sameBucket, firstDuplicateIdx)
        }
    }

    /**
     * Continues deduplication after the first duplicate is found.
     *
     * The function treats `self[firstDuplicate]` as the first duplicated
     * element and drops it.
     *
     * # Safety
     *
     * `1 <= firstDuplicate<self.len()`
     */
    public open fun dedupTail(sameBucket: (T, T) -> Boolean, firstDuplicate: Int) {
        // INVARIANT: vec.len() > read > write > write-1 >= 0
        open class FillGapOnDrop<T, A : Allocator>(
            // Offset of the element we want to check if it is duplicate
            public var read: Int,
            // Offset of the place where we want to place the non-duplicate
            // when we find it.
            public var write: Int,
            // The Vec that would need correction if `sameBucket` panicked
            public var vec: Vec<T, A>,
        ) {
            public open fun drop() {
                // This code gets executed when `sameBucket` panics
                // SAFETY: invariant guarantees that `read - write`
                // and `len - read` never overflow and that the copy is always
                // in-bounds.
                run {
                    val ptr = this.vec.asMutPtr()
                    val len = this.vec.len()
                    // How many items were left when `sameBucket` panicked.
                    // Basically vec[read..].len()
                    val itemsLeft = (len - (this.read.toInt()))
                    // Pointer to first item in vec[write..write+itemsLeft] slice
                    val droppedPtr = ptr.add(this.write)
                    // Pointer to first item in vec[read..] slice
                    val validPtr = ptr.add(this.read)
                    // Copy `vec[read ..]` to `vec[write .. write + itemsLeft]`.
                    // The slices can overlap, so `copyNonoverlapping` cannot be used
                    copy(validPtr, droppedPtr, itemsLeft)
                    // How many items have been already dropped
                    // Basically vec[read..write].len()
                    val dropped = (this.read - (this.write.toInt()))
                    this.vec.setLen(len - dropped)
                }
            }
        }
        val start = this.asMutPtr()
        val len = this.len()
        // Drop items while going through Vec, it should be more efficient than
        // doing slice partitionDedup + truncate
        // Construct gap first and then drop item to avoid memory corruption if `T.drop` panics.
        val gap = FillGapOnDrop(read = firstDuplicate + 1, write = firstDuplicate, vec = this)
        // SAFETY: By this function's safety contract, `firstDuplicate<self.len()`.
        // If drop panics, `gap` would remove this item without drop.
        run {
            dropInPlace(start.add(firstDuplicate))
        }
        // SAFETY: Because of the invariant, readPtr, prevPtr and writePtr
        // are always in-bounds and readPtr never aliases prevPtr
        run {
            while (gap.read < len) {
                val readPtr = start.add(gap.read)
                val prevPtr = start.add((gap.write - 1))
                // We explicitly say in docs that references are reversed.
                val foundDuplicate = sameBucket(readPtr, prevPtr)
                if (foundDuplicate) {
                    // Increase `gap.read` now since the drop may panic.
                    gap.read += 1
                    // We have found duplicate, drop it in-place
                    dropInPlace(readPtr)
                } else {
                    val writePtr = start.add(gap.write)
                    // readPtr cannot be equal to writePtr because at this point
                    // we guaranteed to skip at least one element (before loop starts).
                    //
                    copyNonoverlapping(readPtr, writePtr, 1)
                    // We have filled that place, so go further
                    gap.write += 1
                    gap.read += 1
                }
            }
            // Technically we could let `gap` clean up with its Drop, but
            // when `sameBucket` is guaranteed to not panic, this bloats a little
            // the codegen, so we just do it manually
            gap.vec.setLen(gap.write)
            forget(gap)
        }
    }

    /**
     * Appends an element and returns a reference to it if there is sufficient spare capacity,
     * otherwise an error is returned with the element.
     *
     * Unlike [push] this method will not reallocate when there's insufficient capacity.
     * The caller should use [reserve] or [tryReserve] to ensure that there is enough capacity.
     *
     * [push]: Vec.push
     * [reserve]: Vec.reserve
     * [tryReserve]: Vec.tryReserve
     *
     * # Examples
     *
     * A manual, panic-free alternative to [FromIterator]:
     *
     * ```kotlin
     * import core.iter
     *
     * @feature(vecPushWithinCapacity)
     * public fun doctestEntry(): Result<Unit> {
     *     fun <T> fromIterFallible(iter: Any): Result<Vec<T>> {
     *         var vec: Vec<T> = Vec.new()
     *         for (value in iter) {
     *             when (val matchVal = vec.pushWithinCapacity(value)) {
     *                 is Err -> {
     *                     val (value1) = matchVal
     *                     vec.tryReserve(1).getOrElse { return Result.failure(it) }
     *                     // this cannot fail, the previous line either returned or added at least 1 free slot
     *                     vec.pushWithinCapacity(value1)
     *                 }
     *                 else -> {}
     *             }
     *         }
     *         return Result.success(vec)
     *     }
     *     check(fromIterFallible(0 until 100) == Result.success(Vec.fromIter(0 until 100)))
     *     return Result.success(Unit)
     * }
     * ```
     *
     * # Time complexity
     *
     * Takes *O*(1) time.
     */
    public open fun pushWithinCapacity(value: T): Result<T, T> {
        if (this.len == this.buf.capacity()) {
            return Result.Err(value)
        }
        // ignore-tidy-undocumented-unchecked
        val end = this.asMutPtr().add(this.len)
        // ignore-tidy-undocumented-unchecked
        write(end, value)
        this.len += 1
        // SAFETY: We just wrote a value to the pointer that will live the scope of the reference.
        return Result.Ok(end)
    }

    /**
     * Removes the last element from a vector and returns it, or [None] if it
     * is empty.
     *
     * If you'd like to pop the first element, consider using
     * [VecDeque.popFront] instead.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3)
     * check(vec.removeLastOrNull() == 3)
     * check(vec == listOf(1, 2))
     * ```
     *
     * # Time complexity
     *
     * Takes *O*(1) time.
     */
    public open fun pop(): T? =
        if (this.len == 0) {
            null
        } else {
            this.len -= 1
            // ignore-tidy-undocumented-unchecked
            run {
                assertUnchecked(this.len < this.capacity())
                read(this.asPtr().add(this.len()))
            }
        }

    /**
     * Removes and returns the last element from a vector if the predicate
     * returns `true`, or [None] if the predicate returns false or the vector
     * is empty (the predicate will not be called in that case).
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var vec = mutableListOf(1, 2, 3, 4)
     * val pred = { x: RustMutableReference<Int> -> x % 2 == 0 }
     * check(vec.popIf(pred) == 4)
     * check(vec == listOf(1, 2, 3))
     * check(vec.popIf(pred) == null)
     * ```
     */
    public open fun popIf(predicate: (T) -> Boolean): T? {
        val last = (this.lastMut() ?: return null)
        return if (predicate(last)) {
            this.pop()
        } else {
            null
        }
    }

    /**
     * Returns a mutable reference to the last item in the vector, or
     * `None` if it is empty.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(vecPeekMut)
     * public fun doctestEntry() {
     *     var vec = Vec.new()
     *     check(vec.peekMut() == null)
     *     vec.push(1)
     *     vec.push(5)
     *     vec.push(2)
     *     check(vec.last() == 2)
     *     when (val matchVal = vec.peekMut()) {
     *         null -> {}
     *         else -> {
     *             val value = matchVal
     *             value = 0
     *         }
     *     }
     *     check(vec.last() == 0)
     * }
     * ```
     */
    public open fun peekMut(): PeekMut<T, A>? = PeekMut.new(this)

    /**
     * Moves all the elements of `other` into `self`, leaving `other` empty.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3)
     * var vec2 = mutableListOf(4, 5, 6)
     * vec.append(vec2)
     * check(vec == listOf(1, 2, 3, 4, 5, 6))
     * check(vec2 == listOf())
     * ```
     */
    public open fun append(other: Vec<T, A>) {
        // ignore-tidy-undocumented-unchecked
        run {
            this.appendElements(((other.asSlice() as Any?) as Unit))
            other.setLen(0)
        }
    }

    /**
     * Appends elements to `self` from other buffer.
     */
    public open fun appendElements(other: ConstPtr) {
        this.reserve(other.len())
        // ignore-tidy-undocumented-unchecked
        run {
            this.appendElementsUnreserved(other)
        }
    }

    /**
     * Appends elements to `self` from other buffer, returning [TryReserveError] on OOM.
     */
    public open fun tryAppendElements(other: ConstPtr): Result<Unit, TryReserveError> {
        when (val tryResult = this.tryReserve(other.len())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // ignore-tidy-undocumented-unchecked
        run {
            this.appendElementsUnreserved(other)
        }
        return Result.Ok(Unit)
    }

    /**
     * Appends elements to `self` from other buffer without reserving additional capacity.
     */
    public open fun appendElementsUnreserved(other: ConstPtr, typeLayoutT: TypeLayout<T>) {
        val count = other.len()
        val len = this.len()
        if (count > 0) {
            // ignore-tidy-undocumented-unchecked
            copyNonoverlapping(ConstPtr(other.rawAddress, other.rawMeta), this.asMutPtr().add(len), count, typeLayoutT = typeLayoutT)
        }
        this.len += count
    }

    /**
     * Removes the subslice indicated by the given range from the vector,
     * returning a double-ended iterator over the removed subslice.
     *
     * If the iterator is dropped before being fully consumed,
     * it drops the remaining removed elements.
     *
     * The returned iterator keeps a mutable borrow on the vector to optimize
     * its implementation.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the vector.
     *
     * # Leaking
     *
     * If the returned iterator goes out of scope without being dropped (due to
     * [mem.forget], for example), the vector may have lost and leaked
     * elements arbitrarily, including elements outside the range.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf(1, 2, 3)
     * val u: Vec = v.drain(1 until Int.MAX_VALUE).collect()
     * check(v == listOf(1))
     * check(u == listOf(2, 3))
     * // A full range clears the vector, like `clear()` does
     * v.drain(0 until 0)
     * check(v == listOf())
     * ```
     */
    public open fun <R> drain(range: R): Drain<T, A> where R : RangeBounds<Int> {
        // Memory safety
        //
        // When the Drain is first created, it shortens the length of
        // the source vector to make sure no uninitialized or moved-from elements
        // are accessible at all if the Drain's destructor never gets to run.
        //
        // Drain will ptr.read out the values to remove.
        // When finished, remaining tail of the vec is copied back to cover
        // the hole, and the vector length is restored to the new length.
        //
        val len = this.len()
        val structVal = Range(range, 0 until len)
        val start = structVal.start
        val end = structVal.end
        // ignore-tidy-undocumented-unchecked
        return run {
            // set self.vec length's to start, to be safe in case Drain is leaked
            this.setLen(start)
            val rangeSlice = rawSliceList(this.asPtr().add(start), (end - start).toInt(), PrimitiveLayouts.U8)
            Drain(tailStart = end, tailLen = len - end, iter = rangeSlice.iter(), vec = NonNull.from(this))
        }
    }

    /**
     * Clears the vector, removing all values.
     *
     * Note that this method has no effect on the allocated capacity
     * of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf(1, 2, 3)
     * v.clear()
     * check(v.isEmpty())
     * ```
     */
    public open fun clear() {
        // Though this is equivalent to `truncate(0)`, the manual version
        // optimizes better, justifying the additional complexity
        // (see #96002 and #154095 for context).
        val elems: MutPtr = this.asMutSlice()
        // SAFETY:
        // - `elems` comes directly from `asMutSlice` and is therefore valid.
        // - Setting `self.len` before calling `dropInPlace` means that,
        // if an element's `Drop` impl panics, the vector's `Drop` impl will
        // do nothing (leaking the rest of the elements) instead of dropping
        // some twice.
        run {
            this.len = 0
            dropInPlace(elems)
        }
    }

    /**
     * Returns the number of elements in the vector, also referred to
     * as its 'length'.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableListOf(1, 2, 3)
     * check(a.size == 3)
     * ```
     */
    public open fun len(): Int {
        val len = this.len
        // SAFETY: The maximum capacity of `List<T>` is `Long.MAX` bytes, so the maximum value can
        // be returned is `Int.checkedDiv(sizeOf<T>()).unwrapOr(Int.MAX)`, which
        // matches the definition of `T.MAX_SLICE_LEN`.
        return len
    }

    /**
     * Returns `true` if the vector contains no elements.
     *
     * # Examples
     *
     * ```kotlin
     * var v = Vec.new()
     * check(v.isEmpty())
     * v.push(1)
     * check(!v.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Splits the collection into two at the given index.
     *
     * Returns a newly allocated vector containing the elements in the range
     * `[at, len)`. After the call, the original vector will be left containing
     * the elements `[0, at)` with its previous capacity unchanged.
     *
     * - If you want to take ownership of the entire contents and capacity of
     *   the vector, see [mem.take] or [mem.replace].
     * - If you don't need the returned vector at all, see [Vec.truncate].
     * - If you want to take ownership of an arbitrary subslice, or you don't
     *   necessarily want to store the removed items in a vector, see [Vec.drain].
     *
     * # Panics
     *
     * Panics if `at> len`.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf('a', 'b', 'c')
     * val vec2 = vec.splitOff(1)
     * check(vec == listOf('a'))
     * check(vec2 == listOf('b', 'c'))
     * ```
     */
    public open fun splitOff(at: Int): Vec<T, A> {
        fun assertFailed(at1: Int, len: Int): Nothing {
            error("`at` split index (is $at) should be <= len (is $len)")
        }
        if (at > this.len()) {
            assertFailed(at, this.len())
        }
        val otherLen = this.len - at
        val other = Vec.withCapacityIn(otherLen, this.allocator().clone())
        // Unsafely `setLen` and copy items to `other`.
        // ignore-tidy-undocumented-unchecked
        run {
            this.setLen(at)
            other.setLen(otherLen)
            copyNonoverlapping(this.asPtr().add(at), other.asMutPtr(), other.len())
        }
        return other
    }

    /**
     * Resizes the `List` in-place so that `len` is equal to `newLen`.
     *
     * If `newLen` is greater than `len`, the `List` is extended by the
     * difference, with each additional slot filled with the result of
     * calling the closure `f`. The return values from `f` will end up
     * in the `List` in the order they have been generated.
     *
     * If `newLen` is less than or equal to `len`, the `List` is simply
     * truncated.
     *
     * This method uses a closure to create new values on every push. If
     * you'd rather [Clone] a given value, use [Vec.resize]. If you
     * want to use the [Default] trait to generate values, you can
     * pass [Default.default] as the second argument.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 3)
     * vec.resizeWith(5, Default.default)
     * check(vec == listOf(1, 2, 3, 0, 0))
     * var vec1 = mutableListOf()
     * var p = 1
     * vec1.resizeWith(4, {
     *     p *= 2
     *     p
     * })
     * check(vec1 == listOf(2, 4, 8, 16))
     * ```
     */
    public open fun resizeWith(newLen: Int, f: () -> T) {
        val len = this.len()
        if (newLen > len) {
            this.extendTrusted(Iter.repeatWith(f).take(newLen - len))
        } else {
            this.truncate(newLen)
        }
    }

    /**
     * Consumes and leaks the `List`, returning a mutable reference to the contents,
     * `[T]`.
     *
     * Note that the type `T` must outlive the chosen scope `a`. If the type
     * has only static references, or none at all, then this may be chosen to be
     * `static`.
     *
     * As of Rust 1.57, this method does not reallocate or shrink the `List`,
     * so the leaked allocation may include unused capacity that is not part
     * of the returned slice.
     *
     * This function is mainly useful for data that lives for the remainder of
     * the program's life. Dropping the returned reference will cause a memory
     * leak.
     *
     * # Examples
     *
     * Simple usage:
     *
     * ```kotlin
     * val x = mutableListOf(1, 2, 3)
     * val staticRef: MutableList<Int> = x.leak()
     * staticRef[0] += 1
     * check(staticRef == listOf(2, 2, 3))
     * ```
     */
    public open fun leak(): MutableList<T> {
        val me = this
        // ignore-tidy-undocumented-unchecked
        return rawMutSliceList(me.asMutPtr(), me.len.toInt(), PrimitiveLayouts.U8)
    }

    /**
     * Returns the remaining spare capacity of the vector as a slice of
     * `MaybeUninit<T>`.
     *
     * The returned slice can be used to fill the vector with data (e.g. by
     * reading from a file) before marking the data as initialized using the
     * [setLen] method.
     *
     * [setLen]: Vec.setLen
     *
     * # Examples
     *
     * ```kotlin
     * // Allocate vector big enough for 10 elements.
     * var v = Vec.withCapacity(10)
     * // Fill in the first 3 elements.
     * val uninit = v.spareCapacityMut()
     * uninit[0].write(0)
     * uninit[1].write(1)
     * uninit[2].write(2)
     * // Mark the first 3 elements of the vector as being initialized.
     * run {
     *     v.setLen(3)
     * }
     * check(v == listOf(0, 1, 2))
     * ```
     */
    public open fun spareCapacityMut(): MutableList<MaybeUninit<T>> {
        // Note:
        // This method is not implemented in terms of `splitAtSpareMut`,
        // to prevent invalidation of pointers to the buffer.
        // ignore-tidy-undocumented-unchecked
        return rawMutSliceList(mutPtrFromRustCast(this.asMutPtr().add(this.len)), (this.buf.capacity() - this.len).toInt(), ObjectSlotLayout<MaybeUninit<T>>())
    }

    /**
     * Returns vector content as a slice of `T`, along with the remaining spare
     * capacity of the vector as a slice of `MaybeUninit<T>`.
     *
     * The returned spare capacity slice can be used to fill the vector with data
     * (e.g. by reading from a file) before marking the data as initialized using
     * the [setLen] method.
     *
     * [setLen]: Vec.setLen
     *
     * Note that this is a low-level API, which should be used with care for
     * optimization purposes. If you need to append data to a `List`
     * you can use [push], [extend], [extendFromSlice],
     * [extendFromWithin], [insert], [append], [resize] or
     * [resizeWith], depending on your exact needs.
     *
     * [push]: Vec.push
     * [extend]: Vec.extend
     * [extendFromSlice]: Vec.extendFromSlice
     * [extendFromWithin]: Vec.extendFromWithin
     * [insert]: Vec.insert
     * [append]: Vec.append
     * [resize]: Vec.resize
     * [resizeWith]: Vec.resizeWith
     *
     * # Examples
     *
     * ```kotlin
     * import core.iter
     *
     * @feature(vecSplitAtSpare)
     * public fun doctestEntry() {
     *     var v = mutableListOf(1, 1, 2)
     *     // Reserve additional space big enough for 10 elements.
     *     v.reserve(10)
     *     val (init, uninit) = v.splitAtSpareMut()
     *     val sum = init.iter().copied().sum<UInt>()
     *     // Fill in the next 4 elements.
     *     uninit[0].write(sum)
     *     uninit[1].write(sum * 2)
     *     uninit[2].write(sum * 3)
     *     uninit[3].write(sum * 4)
     *     // Mark the 4 elements of the vector as being initialized.
     *     run {
     *         val len = v.size
     *         v.setLen(len + 4)
     *     }
     *     check(v == listOf(1, 1, 2, 4, 8, 12, 16))
     * }
     * ```
     */
    public open fun splitAtSpareMut(): Pair<MutableList<T>, MutableList<MaybeUninit<T>>> {
        // SAFETY:
        // - len is ignored and so never changed
        val (init, spare, _) = this.splitAtSpareMutWithLen()
        return Pair(init, spare)
    }

    /**
     * Safety: changing returned .2 (Int) is considered the same as calling `.setLen(_)`.
     *
     * This method provides unique access to all vec parts at once in `extendFromWithin`.
     */
    public open fun splitAtSpareMutWithLen(): Triple<MutableList<T>, MutableList<MaybeUninit<T>>, RustMutableReference<Int>> {
        val ptr = this.asMutPtr()
        // SAFETY:
        // - `ptr` is guaranteed to be valid for `self.len` elements
        // - but the allocation extends out to `self.buf.capacity()` elements, possibly
        // uninitialized
        val sparePtr = ptr.add(this.len)
        val sparePtr1 = sparePtr.castUninit()
        val spareLen = this.buf.capacity() - this.len
        // SAFETY:
        // - `ptr` is guaranteed to be valid for `self.len` elements
        // - `sparePtr` is pointing one element past the buffer, so it doesn't overlap with `initialized`
        return run {
            val initialized = rawMutSliceList(ptr, this.len.toInt(), PrimitiveLayouts.U8)
            val spare = rawMutSliceList(sparePtr1, spareLen.toInt(), PrimitiveLayouts.U8)
            Triple(initialized, spare, this.len)
        }
    }

    /**
     * Groups every `N` elements in the `List<T>` into chunks to produce a `List<[T; N]>`, dropping
     * elements in the remainder. `N` must be greater than zero.
     *
     * If the capacity is not a multiple of the chunk size, the buffer will shrink down to the
     * nearest multiple with a reallocation or deallocation.
     *
     * This function can be used to reverse [Vec.intoFlattened].
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * @feature(vecIntoChunks)
     * public fun doctestEntry() {
     *     val vec = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
     *     check(vec.intoChunks() == listOf(listOf(0, 1, 2), listOf(3, 4, 5)))
     *     val vec1 = mutableListOf(0, 1, 2, 3)
     *     val chunks: Vec<UByteArray> = vec1.intoChunks()
     *     check(chunks.isEmpty())
     *     val flat = mutableListOf(0, 8 * 8 * 8)
     *     val reshaped: Vec<List<List<UByteArray>>> = flat.intoChunks().intoChunks().intoChunks()
     *     check(reshaped.len() == 1)
     * }
     * ```
     */
    public open fun intoChunks(n: Int): Vec<List<T>, A> {
        run {
            check(n != 0) { "chunk size must be greater than zero" }
        }
        val (len, cap) = Pair(this.len(), this.capacity())
        val lenRemainder = len % n
        if (lenRemainder != 0) {
            this.truncate(len - lenRemainder)
        }
        val capRemainder = cap % n
        if (T.IS_ZST.inv() && capRemainder != 0) {
            this.buf.shrinkToFit(cap - capRemainder)
        }
        val (ptr, _, _, alloc) = this.intoRawPartsWithAllocator()
        // SAFETY:
        // - `ptr` and `alloc` were just returned from `self.intoRawPartsWithAllocator()`
        // - `[T; N]` has the same alignment as `T`
        // - `sizeOf<[T; N]>() * cap / N == sizeOf<T>() * cap`
        // - `len / N <= cap / N` because `len <= cap`
        // - the allocated memory consists of `len / N` valid values of type `[T; N]`
        // - `cap / N` fits the size of the allocated memory after shrinking
        return Vec.fromRawPartsIn(ptr.cast(), len / n, cap / n, alloc)
    }

    /**
     * This clears out this `List` and recycles the allocation into a new `List`.
     * The item type of the resulting `List` needs to have the same size and
     * alignment as the item type of the original `List`.
     *
     * # Examples
     *
     *  ```kotlin
     *  @feature(vecRecycle, transmutability)
     *  public fun doctestEntry() {
     *      val a: Vec<UByte> = mutableListOf(0, 100)
     *      val capacity = a.capacity()
     *      val addr = a.asPtr().addr()
     *      val b: Vec<Byte> = a.recycle()
     *      check(b.len() == 0)
     *      check(b.capacity() == capacity)
     *      check(b.asPtr().addr() == addr)
     *  }
     * ```
     *
     * The `Recyclable` bound prevents this method from being called when `T` and `U` have different sizes; e.g.:
     *
     *  ```compile_fail,E0277
     * @file:feature(vecRecycle, transmutability)
     * let vec: Vec<[u8; 2]> = Vec.new();
     * let _: Vec<[u8; 1]> = vec.recycle();
     * ```
     * ...or different alignments:
     *
     *  ```compile_fail,E0277
     * @file:feature(vecRecycle, transmutability)
     * let vec: Vec<[u16; 0]> = Vec.new();
     * let _: Vec<[u8; 0]> = vec.recycle();
     * ```
     *
     * However, due to temporary implementation limitations of `Recyclable`,
     * this method is not yet callable when `T` or `U` are slices, trait objects,
     * or other exotic types; e.g.:
     *
     * ```compile_fail,E0277
     * @file:feature(vecRecycle, transmutability)
     * # let inputs = ["a b c", "d e f"];
     * # fn process(_: &[&str]) {}
     * let mut storage: Vec<&[&str]> = Vec.new();
     *
     * for input in inputs {
     *     let mut buffer: Vec<&str> = storage.recycle();
     *     buffer.extend(input.split(" "));
     *     process(&buffer);
     *     storage = buffer.recycle();
     * }
     * ```
     */
    public open fun <U> recycle(): Vec<U, A> where U : Recyclable<T> {
        this.clear()
        run {
            // Note(const-Note, 146097): compare `Layout`s
            check(sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == sizeOf<U>(typeLayoutT = ObjectSlotLayout<U>()))
            check(alignOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == alignOf<U>(typeLayoutT = ObjectSlotLayout<U>()))
        }
        val (ptr, length, capacity, alloc) = this.intoPartsWithAllocator()
        check(length == 0)
        // SAFETY:
        // - `ptr` and `alloc` were just returned from `self.intoRawPartsWithAllocator()`
        // - `T` & `U` have the same layout, so `capacity` does not need to be changed and we can safely use `alloc.dealloc` later
        // - the original vector was cleared, so there is no problem with "transmuting" the stored values
        return Vec.fromPartsIn(ptr.cast(), length, capacity, alloc)
    }

    /**
     * Resizes the `List` in-place so that `len` is equal to `newLen`.
     *
     * If `newLen` is greater than `len`, the `List` is extended by the
     * difference, with each additional slot filled with `value`.
     * If `newLen` is less than or equal to `len`, the `List` is simply
     * truncated.
     *
     * This method requires `T` to implement [Clone],
     * in order to be able to clone the passed value.
     * If you need more flexibility (or want to rely on [Default] instead of
     * [Clone]), use [Vec.resizeWith].
     * If you only need to resize to a smaller size, use [Vec.truncate].
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf("hello")
     * vec.resize(3, "world")
     * check(vec == listOf("hello", "world", "world"))
     * var vec1 = mutableListOf('a', 'b', 'c', 'd')
     * vec1.resize(2, '_')
     * check(vec1 == listOf('a', 'b'))
     * ```
     */
    public open fun resize(newLen: Int, value: T) {
        val len = this.len()
        if (newLen > len) {
            this.extendWith(newLen - len, value)
        } else {
            this.truncate(newLen)
        }
    }

    /**
     * Clones and appends all elements in a slice to the `List`.
     *
     * Iterates over the slice `other`, clones each element, and then appends
     * it to this `List`. The `other` slice is traversed in-order.
     *
     * Note that this function is the same as [extend],
     * except that it also works with slice elements that are Clone but not Copy.
     * If Rust gets specialization this function may be deprecated.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1)
     * vec.addAll(listOf(2, 3, 4))
     * check(vec == listOf(1, 2, 3, 4))
     * ```
     *
     * [extend]: Vec.extend
     */
    public open fun extendFromSlice(other: List<T>) {
        this.specExtend(other.iter())
    }

    /**
     * Given a range `src`, clones a slice of elements in that range and appends it to the end.
     *
     * `src` must be a range that can form a valid subslice of the `List`.
     *
     * # Panics
     *
     * Panics if starting index is greater than the end index, if the index is
     * greater than the length of the vector, or if the new capacity exceeds
     * `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var characters = mutableListOf('a', 'b', 'c', 'd', 'e')
     * characters.extendFromWithin(2 until Int.MAX_VALUE)
     * check(characters == listOf('a', 'b', 'c', 'd', 'e', 'c', 'd', 'e'))
     * var numbers = mutableListOf(0, 1, 2, 3, 4)
     * numbers.extendFromWithin(0 until 2)
     * check(numbers == listOf(0, 1, 2, 3, 4, 0, 1))
     * var strings = mutableListOf("hello", "world", "!")
     * strings.extendFromWithin(1..2)
     * check(strings == listOf("hello", "world", "!", "world", "!"))
     * ```
     */
    public open fun <R> extendFromWithin(src: R) where R : RangeBounds<Int> {
        val range = Range(src, 0 until this.len())
        this.reserve(range.len())
        // SAFETY:
        // - `slice.range` guarantees that the given range is valid for indexing self
        run {
            this.specExtendFromWithin(range)
        }
    }

    /**
     * Extend the vector by `n` clones of value.
     */
    public open fun extendWith(n: Int, value: T) {
        this.specExtendWith(n, value)
    }

    /**
     * Removes consecutive repeated elements in the vector according to the
     * [PartialEq] trait implementation.
     *
     * If the vector is sorted, this removes all duplicates.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf(1, 2, 2, 3, 2)
     * vec.dedup()
     * check(vec == listOf(1, 2, 3, 2))
     * ```
     */
    public open fun dedup(typeLayoutT: TypeLayout<T>) {
        // Vectorize the process of finding first duplicate if possible
        when (val matchVal = dedupSimdChunkSize<T>(typeLayoutT = typeLayoutT)) {
            null -> {
                this.dedupBy({ a, b -> a == b })
            }
            else -> {
                val chunkSize = matchVal
                // Unlike dedupBy, we don't guarantee the order
                // and time in which we call `PartialEq.eq`.
                val firstDuplicate = findFirstDuplicate(this.asSlice(), chunkSize) ?: return
                // SAFETY: `1 <= firstDuplicate<self.len()`
                run {
                    this.dedupTail({ a, b -> a == b }, firstDuplicate)
                }
            }
        }
    }

    public override fun specExtendFromWithin(src: IntRange) {
        // SAFETY:
        // - len is increased only after initializing elements
        val (thisRef, spare, len) = this.splitAtSpareMutWithLen()
        // SAFETY:
        // - caller guarantees that src is a valid index
        val toClone = thisRef.getUnchecked(src)
        Iter.zip(toClone, spare).map({ (src1, dst) -> dst.write(src1.clone()) }).forEach({ len += 1 })
    }

    public open fun specExtendFromWithinExtendFromWithinSpec(src: IntRange) {
        val count = src.len()
        run {
            val (init, spare) = this.splitAtSpareMut()
            // SAFETY:
            // - caller guarantees that `src` is a valid index
            val source = init.getUnchecked(src)
            // SAFETY:
            // - Both pointers are created from unique slice references (`[_]`)
            // so they are valid and do not overlap.
            // - Elements implement `TrivialClone` so this is equivalent to calling
            // `clone` on every one of them.
            // - `count` is equal to the len of `source`, so source is valid for
            // `count` reads
            // - `.reserve(count)` guarantees that `spare.len() >= count` so spare
            // is valid for `count` writes
            copyNonoverlapping(source.asPtr(), ((spare.asMutPtr() as Any?) as Unit), count)
        }
        // SAFETY:
        // - The elements were just initialized by `copyNonoverlapping`
        this.len += count
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Common trait implementations for Vec
    // ///////////////////////////////////////////////////////////////////////////
    public open fun deref(): List<T> = this.asSlice()

    public open fun derefMut(): MutableList<T> = this.asMutSlice()

    /**
     * Creates a new `List` by deep-copying the contents of an existing `List`.
     *
     * This method will allocate a new `List` and `clone` all of `self`s contents
     * into it. The capacity of the duplicate `List` is not forced to match the
     * capacity of the original.
     */
    public open fun clone(): Vec<T, A> {
        val alloc = this.allocator().clone()
        return List.toVecIn(this, alloc)
    }

    /**
     * Overwrites the contents of `self` with a clone of the contents of `source`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible. Additionally, if the element type
     * `T` overrides `cloneFrom()`, this will reuse the resources of `self`s
     * elements as well.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     *
     * val x = mutableListOf(5, 6, 7)
     * var y = mutableListOf(8, 9, 10)
     * val yp: ConstPtr = y.asPtr()
     * run { y.clear(); y.addAll(x) }
     * // The value is the same
     * check(x == y)
     * // And no reallocation occurred
     * check(yp == y.asPtr())
     * ```
     */
    public open fun cloneFrom(source: Vec<T, A>) {
        cloneInto(source.asSlice(), this)
    }

    /**
     * The hash of a vector is the same as that of the corresponding slice,
     * as required by the `core.borrow.Borrow` implementation.
     *
     * ```kotlin
     * val b = RandomState.new()
     * val v: Vec<UByte> = mutableListOf(0xa8, 0x3c, 0x09)
     * val s: List<UByte> = listOf(0xa8u.toUByte(), 0x3cu.toUByte(), 0x09u.toUByte())
     * check(b.hashOne(v) == b.hashOne(s))
     * ```
     */
    public override fun hashCode(): Int {
        var state = 0
        Hash.hash(this, state)
        return state
    }

    public open fun index(index: I): Output = Index.index(this, index)

    public open fun indexMut(index: I): Output = IndexMut.indexMut(this, index)

    /**
     * Creates a consuming iterator, that is, one that moves each value out of
     * the vector (from start to end). The vector cannot be used after calling
     * this.
     *
     * # Examples
     *
     * ```kotlin
     * val v = mutableListOf("a".toString(), "b".toString())
     * var vIter = ArrayDeque(v)
     * val firstElement: String? = vIter.removeFirstOrNull()
     * check(firstElement == "a".toString())
     * check(vIter.removeFirstOrNull() == "b".toString())
     * check(vIter.removeFirstOrNull() == null)
     * ```
     */
    public open fun intoIter(): IntoIter<T, A> {
        val me = this
        // ignore-tidy-undocumented-unchecked
        return run {
            val alloc = read(me.allocator())
            val buf = me.buf.nonNull()
            val begin = buf.asPtr()
            val end =
                if (T.IS_ZST) {
                    begin.wrappingByteAdd(me.len())
                } else {
                    constPtrFromRustCast(begin.add(me.len()))
                }
            val cap = me.buf.capacity()
            IntoIter(buf = buf, phantom = Unit, cap = cap, alloc = alloc, ptr = buf, end = end)
        }
    }

    public open fun <I : Iterable<T>> extend(iter: I) {
        Vec.specExtend(this, iter.iterator())
    }

    public open fun extendOne(item: T) {
        this.push(item)
    }

    public open fun extendReserve(additional: Int) {
        this.reserve(additional)
    }

    public open fun extendOneUnchecked(item: T) {
        // SAFETY: Our preconditions ensure the space has been reserved, and `extendReserve` is implemented correctly.
        run {
            val len = this.len()
            write(this.asMutPtr().add(len), item)
            this.setLen(len + 1)
        }
    }

    // leaf method to which various SpecFrom/SpecExtend implementations delegate when
    // they have no further optimizations to apply
    public open fun <I : Iterator<T>> extendDesugared(iterator: I) {
        // This is the case for a general iterator.
        //
        // This function should be the moral equivalent of:
        //
        // for item in iterator {
        // self.push(item);
        // }
        while (true) {
            val matchVal = iterator.next()
            if (matchVal == null) break
            val element = matchVal
            val len = this.len()
            if (len == this.capacity()) {
                val (lower, _) = iterator.sizeHint()
                this.reserve(lower.saturatingAdd(1))
            }
            // ignore-tidy-undocumented-unchecked
            run {
                write(this.asMutPtr().add(len), element)
                // Since next() executes user code which can panic we have to bump the length
                // after each step.
                // NB can't overflow since we would have had to alloc the address space
                this.setLen(len + 1)
            }
        }
    }

    // specific extend for `TrustedLen` iterators, called both by the specializations
    // and internal places where resolving specialization makes compilation slower
    public open fun extendTrusted(iterator: TrustedLen<T>) {
        val (low, high) = iterator.sizeHint()
        if (high != null) {
            val additional = high
            check(low == additional)
            this.reserve(additional)
            val ptr = this.asMutPtr()
            val localLen = SetLenOnDrop.new(this.len)
            // ignore-tidy-undocumented-unchecked
            run {
                iterator.forEach({ element ->
                    write(ptr.add(localLen.currentLen()), element)
                    // Since the loop executes user code which can panic we have to update
                    // the length every step to correctly drop what we've written.
                    // NB can't overflow since we would have had to alloc the address space
                    localLen.incrementLen(1)
                })
            }
        } else {
            // Per TrustedLen contract a `None` upper bound means that the iterator length
            // truly exceeds usize.Max, which would eventually lead to a capacity overflow anyway.
            // Since the other branch already panics eagerly (via `reserve()`) we do the same here.
            // This avoids additional codegen for a fallback code path which would eventually
            // panic anyway.
            error("capacity overflow")
        }
    }

    /**
     * Creates a splicing iterator that replaces the specified range in the vector
     * with the given `replaceWith` iterator and yields the removed items.
     * `replaceWith` does not need to be the same length as `range`.
     *
     * `range` is removed even if the `Splice` iterator is not consumed before it is dropped.
     *
     * It is unspecified how many elements are removed from the vector
     * if the `Splice` value is leaked.
     *
     * The input iterator `replaceWith` is only consumed when the `Splice` value is dropped.
     *
     * This is optimal if:
     *
     * * The tail (elements in the vector after `range`) is empty,
     * * or `replaceWith` yields fewer or equal elements than `range`s length
     * * or the lower bound of its `sizeHint()` is exact.
     *
     * Otherwise, a temporary vector is allocated and the tail is moved twice.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableListOf(1, 2, 3, 4)
     * val new = listOf(7, 8, 9)
     * val u: Vec = v.splice(1 until 3, new).collect()
     * check(v == listOf(1, 7, 8, 9, 4))
     * check(u == listOf(2, 3))
     * ```
     *
     * Using `splice` to insert new items into a vector efficiently at a specific position
     * indicated by an empty range:
     *
     * ```kotlin
     * var v = mutableListOf(1, 5)
     * val new = listOf(2, 3, 4)
     * v.splice(1 until 1, new)
     * check(v == listOf(1, 2, 3, 4, 5))
     * ```
     */
    public open fun <R, I> splice(range: R, replaceWith: I): Splice<IntoIter, A> where R : RangeBounds<Int>, I : Iterable<T> = Splice(drain = this.drain(range), replaceWith = replaceWith.iterator())

    /**
     * Creates an iterator which uses a closure to determine if an element in the range should be removed.
     *
     * If the closure returns `true`, the element is removed from the vector
     * and yielded. If the closure returns `false`, or panics, the element
     * remains in the vector and will not be yielded.
     *
     * Only elements that fall in the provided range are considered for extraction, but any elements
     * after the range will still have to be moved if any element has been extracted.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use `extractIf().forEach(drop)` if you do not need the returned iterator,
     * or [retainMut] with a negated predicate if you also do not need to restrict the range.
     *
     * [retainMut]: Vec.retainMut
     *
     * Using this method is equivalent to the following code:
     *
     * ```kotlin
     * var i = range.start
     * val endItems = vec.len() - range.end
     * while (i < vec.len() - endItems) {
     *     if (somePredicate(vec[i])) {
     *         val value = vec.remove(i)
     *         // your code here
     *     } else {
     *         i += 1
     *     }
     * }
     * ```
     *
     * But `extractIf` is easier to use. `extractIf` is also more efficient,
     * because it can backshift the elements of the array in bulk.
     *
     * The iterator also lets you mutate the value of each element in the
     * closure, regardless of whether you choose to keep or remove it.
     *
     * # Panics
     *
     * If `range` is out of bounds.
     *
     * # Examples
     *
     * Splitting a vector into even and odd values, reusing the original vector:
     *
     * ```kotlin
     * var numbers = mutableListOf(1, 2, 3, 4, 5, 6, 8, 9, 11, 13, 14, 15)
     * val evens = numbers.extractIf(0 until 0, { x -> x % 2 == 0 }).collect<Vec>()
     * val odds = numbers
     * check(evens == mutableListOf(2, 4, 6, 8, 14))
     * check(odds == mutableListOf(1, 3, 5, 9, 11, 13, 15))
     * ```
     *
     * Using the range argument to only process a part of the vector:
     *
     * ```kotlin
     * var items = mutableListOf(0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 2)
     * val ones = items.extractIf(7 until Int.MAX_VALUE, { x -> x == 1 }).collect<Vec>()
     * check(items == mutableListOf(0, 0, 0, 0, 0, 0, 0, 2, 2, 2))
     * check(ones.len() == 3)
     * ```
     */
    public open fun <R> extractIf(range: R, filter: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> where R : RangeBounds<Int> = ExtractIf.new(this, filter, range)

    /**
     * Extend implementation that copies elements out of references before pushing them onto the Vec.
     *
     * This implementation is specialized for slice iterators, where it uses [copyFromSlice] to
     * append the entire slice at once.
     *
     * [copyFromSlice]: slice.copyFromSlice
     */
    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.specExtend(iter.iterator())
    }

    public open fun extendOneT(item: T) {
        this.push(item)
    }

    public open fun extendReserveT(additional: Int) {
        this.reserve(additional)
    }

    public open fun extendOneUncheckedT(item: T) {
        // SAFETY: Our preconditions ensure the space has been reserved, and `extendReserve` is implemented correctly.
        run {
            val len = this.len()
            write(this.asMutPtr().add(len), item)
            this.setLen(len + 1)
        }
    }

    /**
     * Implements ordering of vectors, [lexicographically](Ord#lexicographical-comparison).
     */
    public open fun cmp(other: Vec<T, A>): Ordering = Ord.cmp(this, other)

    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        this.asMutPtr().castSlice(this.len).dropInPlace()
        // RawVec handles deallocation
    }

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun asRef(): Vec<T, A> = this

    public open fun asMut(): Vec<T, A> = this

    public open fun asRefTSlice(): List<T> = this

    public open fun asMutTSlice(): MutableList<T> = this

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            buf: RawVec<T, A>,
            len: Int,
        ): Vec<T, A> = Vec<T, A>(
            bufCtorArg = buf,
            len = len,
        )

        /**
         * Constructs a new, empty `List<T, A>` with at least the specified capacity
         * with the provided allocator.
         *
         * The vector will be able to hold at least `capacity` elements without
         * reallocating. This method is allowed to allocate for more elements than
         * `capacity`. If `capacity` is zero, the vector [will not allocate].
         *
         * It is important to note that although the returned vector has the
         * minimum *capacity* specified, the vector will have a zero *length*. For
         * an explanation of the difference between length and capacity, see
         * *[Capacity and reallocation]*.
         *
         * If it is important to know the exact allocated capacity of a `List`,
         * always use the [capacity] method after construction.
         *
         * For `List<T, A>` where `T` is a zero-sized type, the vector
         * [will not allocate], and the capacity will always be `Int.MAX`.
         *
         * [Capacity and reallocation]: #capacity-and-reallocation
         * [capacity]: Vec.capacity
         * [will not allocate]: self#memory-layout
         *
         * # Panics
         *
         * Panics if the new capacity exceeds `Long.MAX` _bytes_.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * var vec = Vec.withCapacityIn(10, System)
         * // The vector contains no items, even though it has capacity for more
         * check(vec.len() == 0)
         * check(vec.capacity() >= 10)
         * // These are all done without reallocating...
         * for (i in 0 until 10) {
         *     vec.push(i)
         * }
         * check(vec.len() == 10)
         * check(vec.capacity() >= 10)
         * // ...but this may make the vector reallocate
         * vec.push(11)
         * check(vec.len() == 11)
         * check(vec.capacity() >= 11)
         * // A vector of a zero-sized type will always over-allocate, since no
         * // allocation is necessary
         * val vecUnits = Vec.withCapacityIn(10, System)
         * check(vecUnits.capacity() == Int.MAX_VALUE)
         * ```
         */
        public fun <T, A : Allocator> withCapacityIn(capacity: Int, alloc: A): Vec<T, A> = Vec(buf = RawVec.withCapacityIn(capacity, alloc), len = 0)

        /**
         * Constructs a new, empty `List<T, A>`.
         *
         * The vector will not allocate until elements are pushed onto it.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * val vec: Vec<Int, System> = Vec.newIn(System)
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): Vec<T, A> = Vec(buf = RawVec.newIn(alloc), len = 0)

        // @unstable(feature = "tryWithCapacity", issue = "91913")

        /**
         * Constructs a new, empty `List<T, A>` with at least the specified capacity
         * with the provided allocator.
         *
         * The vector will be able to hold at least `capacity` elements without
         * reallocating. This method is allowed to allocate for more elements than
         * `capacity`. If `capacity` is zero, the vector [will not allocate].
         *
         * # Errors
         *
         * Returns an error if the capacity exceeds `Long.MAX` _bytes_,
         * or if the allocator reports allocation failure.
         *
         * [will not allocate]: self#memory-layout
         */
        public fun <T, A : Allocator> tryWithCapacityIn(capacity: Int, alloc: A): Result<Vec<T, A>, TryReserveError> {
            return Result.Ok(Vec(buf = when (val tryResult = RawVec.tryWithCapacityIn(capacity, alloc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, len = 0))
        }

        /**
         * Creates a `List<T, A>` directly from a pointer, a length, a capacity,
         * and an allocator.
         *
         * # Safety
         *
         * The parts must satisfy the invariants described in the [memory layout] section.
         *
         * Violating them may cause problems like corrupting the allocator's
         * internal data structures. For example it is **not** safe
         * to build a `List<UByte>` from a pointer to a C `char` array with length `sizeT`.
         * It's also not safe to build one from a `List<UShort>` and its length, because
         * the allocator cares about the alignment, and these two types have different
         * alignments. The buffer was allocated with alignment 2 (for `UShort`), but after
         * turning it into a `List<UByte>` it'll be deallocated with alignment 1.
         *
         * The ownership of `ptr` is effectively transferred to the
         * `List<T>` which may then deallocate, reallocate or change the
         * contents of memory pointed to by the pointer at will. Ensure
         * that nothing else uses the pointer after calling this
         * function.
         *
         * [memory layout]: self#memory-layout
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         * import std.ptr
         *
         * var v = Vec.withCapacityIn(3, System)
         * v.push(1)
         * v.push(2)
         * v.push(3)
         * // Deconstruct the vector into parts.
         * val (p, len, cap, alloc) = v.intoRawPartsWithAllocator()
         * run {
         *     // Overwrite memory with 4, 5, 6
         *     for (i in 0 until len) {
         *         ptr.write(p.add(i), 4 + i)
         *     }
         *     // Put everything back together into a Vec
         *     val rebuilt = Vec.fromRawPartsIn(p, len, cap, alloc.clone())
         *     check(rebuilt == listOf(4, 5, 6))
         * }
         * ```
         *
         * Using memory that was allocated elsewhere:
         *
         * ```kotlin
         * import alloc.Global
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import std.alloc.AllocError
         * import std.alloc.Layout
         *
         * fun main() {
         *     val layout = Layout.array<UInt>(16, typeLayoutT = PrimitiveLayouts.U32).expect("16 u32s take 64 bytes, so it shouldn't overflow")
         *     val vec = run {
         *         val mem1 = run {
         *             val matchVal = Global.allocate(layout)
         *             when {
         *                 matchVal is Ok -> {
         *                     val (mem) = matchVal
         *                     mem.cast().asPtr()
         *                 }
         *                 else -> {
         *                     val (AllocError) = matchVal
         *                     return
         *                 }
         *             }
         *         }
         *         mem1.write(1000000)
         *         Vec.fromRawPartsIn(mem1, 1, 16, Global)
         *     }
         *     check(vec == listOf(1000000))
         *     check(vec.capacity() == 16)
         * }
         * ```
         */
        public fun <T, A : Allocator> fromRawPartsIn(ptr: MutPtr, length: Int, capacity: Int, alloc: A): Vec<T, A> {
            assertUnsafePrecondition(checkLibraryUb, "Vec::from_raw_parts_in requires that length <= capacity", length, length, capacity, capacity, length, capacity)
            // SAFETY: Upheld by caller.
            return Vec(buf = RawVec.fromRawPartsIn(ptr, capacity, alloc), len = length)
        }

        /**
         * Creates a `List<T, A>` directly from a `NonNull` pointer, a length, a capacity,
         * and an allocator.
         *
         * # Safety
         *
         * The parts must satisfy the invariants described in the [memory layout] section.
         *
         * Violating them may cause problems like corrupting the allocator's
         * internal data structures. For example it is **not** safe
         * to build a `List<UByte>` from a pointer to a C `char` array with length `sizeT`.
         * It's also not safe to build one from a `List<UShort>` and its length, because
         * the allocator cares about the alignment, and these two types have different
         * alignments. The buffer was allocated with alignment 2 (for `UShort`), but after
         * turning it into a `List<UByte>` it'll be deallocated with alignment 1.
         *
         * The ownership of `ptr` is effectively transferred to the
         * `List<T>` which may then deallocate, reallocate or change the
         * contents of memory pointed to by the pointer at will. Ensure
         * that nothing else uses the pointer after calling this
         * function.
         *
         * [memory layout]: self#memory-layout
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * var v = Vec.withCapacityIn(3, System)
         * v.push(1)
         * v.push(2)
         * v.push(3)
         * // Deconstruct the vector into parts.
         * val (p, len, cap, alloc) = v.intoPartsWithAllocator()
         * run {
         *     // Overwrite memory with 4, 5, 6
         *     for (i in 0 until len) {
         *         p.add(i).write(4 + i)
         *     }
         *     // Put everything back together into a Vec
         *     val rebuilt = Vec.fromPartsIn(p, len, cap, alloc.clone())
         *     check(rebuilt == listOf(4, 5, 6))
         * }
         * ```
         *
         * Using memory that was allocated elsewhere:
         *
         * ```kotlin
         * import alloc.Global
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import std.alloc.AllocError
         * import std.alloc.Layout
         *
         * fun main() {
         *     val layout = Layout.array<UInt>(16, typeLayoutT = PrimitiveLayouts.U32).expect("16 u32s take 64 bytes, so it shouldn't overflow")
         *     val vec = run {
         *         val mem1 = run {
         *             val matchVal = Global.allocate(layout)
         *             when {
         *                 matchVal is Ok -> {
         *                     val (mem) = matchVal
         *                     mem.cast()
         *                 }
         *                 else -> {
         *                     val (AllocError) = matchVal
         *                     return
         *                 }
         *             }
         *         }
         *         mem1.write(1000000)
         *         Vec.fromPartsIn(mem1, 1, 16, Global)
         *     }
         *     check(vec == listOf(1000000))
         *     check(vec.capacity() == 16)
         * }
         * ```
         */
        public fun <T, A : Allocator> fromPartsIn(ptr: NonNull, length: Int, capacity: Int, alloc: A): Vec<T, A> {
            assertUnsafePrecondition(checkLibraryUb, "Vec::from_parts_in requires that length <= capacity", length, length, capacity, capacity, length, capacity)
            // SAFETY: Upheld by caller.
            return Vec(buf = RawVec.fromNonnullIn(ptr, capacity, alloc), len = length)
        }

        // note: test pulls in std, which causes errors here

        /**
         * Converts a boxed slice into a vector by transferring ownership of
         * the existing heap allocation.
         *
         * # Examples
         *
         * ```kotlin
         * val b: List<Int> = mutableListOf(1, 2, 3).intoBoxedSlice()
         * check(Vec.from(b) == mutableListOf(1, 2, 3))
         * ```
         */
        public fun <T, A : Allocator> from(s: List<T>): Vec<T, A> = s.intoVec()
    }
}

// ///////////////////////////////////////////////////////////////////////////
// Inherent methods
// ///////////////////////////////////////////////////////////////////////////

/**
 * Constructs a new, empty `List<T>`.
 *
 * The vector will not allocate until elements are pushed onto it.
 *
 * # Examples
 *
 * ```kotlin
 * var vec: Vec<Int> = Vec.new()
 * ```
 */
public fun <T> Vec.Companion.new(): Vec<T> = Vec(buf = RawVec.new(), len = 0)

/**
 * Constructs a new, empty `List<T>` with at least the specified capacity.
 *
 * The vector will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the vector [will not allocate].
 *
 * It is important to note that although the returned vector has the
 * minimum *capacity* specified, the vector will have a zero *length*. For
 * an explanation of the difference between length and capacity, see
 * *[Capacity and reallocation]*.
 *
 * If it is important to know the exact allocated capacity of a `List`,
 * always use the [capacity] method after construction.
 *
 * For `List<T>` where `T` is a zero-sized type, the vector
 * [will not allocate], and the capacity will always be `Int.MAX`.
 *
 * [Capacity and reallocation]: #capacity-and-reallocation
 * [capacity]: Vec.capacity
 * [will not allocate]: self#memory-layout
 *
 * # Panics
 *
 * Panics if the new capacity exceeds `Long.MAX` _bytes_.
 *
 * # Examples
 *
 * ```kotlin
 * var vec = Vec.withCapacity(10)
 * // The vector contains no items, even though it has capacity for more
 * check(vec.len() == 0)
 * check(vec.capacity() >= 10)
 * // These are all done without reallocating...
 * for (i in 0 until 10) {
 *     vec.push(i)
 * }
 * check(vec.len() == 10)
 * check(vec.capacity() >= 10)
 * // ...but this may make the vector reallocate
 * vec.push(11)
 * check(vec.len() == 11)
 * check(vec.capacity() >= 11)
 * // A vector of a zero-sized type will always over-allocate, since no
 * // allocation is necessary
 * val vecUnits = Vec.withCapacity(10)
 * check(vecUnits.capacity() == Int.MAX_VALUE)
 * ```
 */
public fun <T> Vec.Companion.withCapacity(capacity: Int): Vec<T> = Vec.withCapacityIn(capacity, Global)

/**
 * Constructs a new, empty `List<T>` with at least the specified capacity.
 *
 * The vector will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the vector [will not allocate].
 *
 * # Errors
 *
 * Returns an error if the capacity exceeds `Long.MAX` _bytes_,
 * or if the allocator reports allocation failure.
 *
 * [will not allocate]: self#memory-layout
 */
public fun <T> Vec.Companion.tryWithCapacity(capacity: Int): Result<Vec<T>, TryReserveError> = Vec.tryWithCapacityIn(capacity, Global)

/**
 * Creates a `List<T>` directly from a pointer, a length, and a capacity.
 *
 * # Safety
 *
 * The parts must satisfy the invariants described in the [memory layout] section,
 * with allocator [Global].
 *
 * Violating them may cause problems like corrupting the allocator's
 * internal data structures. For example it is normally **not** safe
 * to build a `List<UByte>` from a pointer to a C `char` array with length
 * `sizeT`, doing so is only safe if the array was initially allocated by
 * a `List` or `String`.
 * It's also not safe to build one from a `List<UShort>` and its length, because
 * the allocator cares about the alignment, and these two types have different
 * alignments. The buffer was allocated with alignment 2 (for `UShort`), but after
 * turning it into a `List<UByte>` it'll be deallocated with alignment 1. To avoid
 * these issues, it is often preferable to do casting/transmuting using
 * [slice.fromRawParts] instead.
 *
 * The ownership of `ptr` is effectively transferred to the
 * `List<T>` which may then deallocate, reallocate or change the
 * contents of memory pointed to by the pointer at will. Ensure
 * that nothing else uses the pointer after calling this
 * function.
 *
 * [memory layout]: self#memory-layout
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr
 *
 * val v = mutableListOf(1, 2, 3)
 * // Deconstruct the vector into parts.
 * val (p, len, cap) = v.intoRawParts()
 * run {
 *     // Overwrite memory with 4, 5, 6
 *     for (i in 0 until len) {
 *         ptr.write(p.add(i), 4 + i)
 *     }
 *     // Put everything back together into a Vec
 *     val rebuilt = Vec.fromRawParts(p, len, cap)
 *     check(rebuilt == listOf(4, 5, 6))
 * }
 * ```
 *
 * Using memory that was allocated elsewhere:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.alloc.Layout
 * import std.alloc.alloc
 *
 * fun main() {
 *     val layout = Layout.array<UInt>(16, typeLayoutT = PrimitiveLayouts.U32).expect("16 u32s take 64 bytes, so it shouldn't overflow")
 *     val vec = run {
 *         val mem = alloc(layout).cast()
 *         if (mem.isNull()) {
 *             return
 *         }
 *         mem.write(1000000)
 *         Vec.fromRawParts(mem, 1, 16)
 *     }
 *     check(vec == listOf(1000000))
 *     check(vec.capacity() == 16)
 * }
 * ```
 */
public fun <T> Vec.Companion.fromRawParts(ptr: MutPtr, length: Int, capacity: Int): Vec<T> {
    // SAFETY: Upheld by caller.
    return Vec.fromRawPartsIn(ptr, length, capacity, Global)
}

/**
 * Creates a `List<T>` directly from a `NonNull` pointer, a length, and a capacity.
 *
 * # Safety
 *
 * The parts must satisfy the invariants described in the [memory layout] section,
 * with allocator [Global].
 *
 * Violating them may cause problems like corrupting the allocator's
 * internal data structures. For example it is normally **not** safe
 * to build a `List<UByte>` from a pointer to a C `char` array with length
 * `sizeT`, doing so is only safe if the array was initially allocated by
 * a `List` or `String`.
 * It's also not safe to build one from a `List<UShort>` and its length, because
 * the allocator cares about the alignment, and these two types have different
 * alignments. The buffer was allocated with alignment 2 (for `UShort`), but after
 * turning it into a `List<UByte>` it'll be deallocated with alignment 1. To avoid
 * these issues, it is often preferable to do casting/transmuting using
 * [NonNull.sliceFromRawParts] instead.
 *
 * The ownership of `ptr` is effectively transferred to the
 * `List<T>` which may then deallocate, reallocate or change the
 * contents of memory pointed to by the pointer at will. Ensure
 * that nothing else uses the pointer after calling this
 * function.
 *
 * [memory layout]: self#memory-layout
 *
 * # Examples
 *
 * ```kotlin
 * val v = mutableListOf(1, 2, 3)
 * // Deconstruct the vector into parts.
 * val (p, len, cap) = v.intoParts()
 * run {
 *     // Overwrite memory with 4, 5, 6
 *     for (i in 0 until len) {
 *         p.add(i).write(4 + i)
 *     }
 *     // Put everything back together into a Vec
 *     val rebuilt = Vec.fromParts(p, len, cap)
 *     check(rebuilt == listOf(4, 5, 6))
 * }
 * ```
 *
 * Using memory that was allocated elsewhere:
 *
 * ```kotlin
 * import core.ptr.NonNull
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.alloc.Layout
 * import std.alloc.alloc
 *
 * fun main() {
 *     val layout = Layout.array<UInt>(16, typeLayoutT = PrimitiveLayouts.U32).expect("16 u32s take 64 bytes, so it shouldn't overflow")
 *     val vec = run {
 *         val mem = NonNull.new(alloc(layout).cast()) ?: return
 *         mem.write(1000000)
 *         Vec.fromParts(mem, 1, 16)
 *     }
 *     check(vec == listOf(1000000))
 *     check(vec.capacity() == 16)
 * }
 * ```
 */
public fun <T> Vec.Companion.fromParts(ptr: NonNull, length: Int, capacity: Int): Vec<T> {
    // SAFETY: Upheld by caller.
    return Vec.fromPartsIn(ptr, length, capacity, Global)
}

/**
 * Creates a `List<T>` where each element is produced by calling `f` with
 * that element's index while walking forward through the `List<T>`.
 *
 * This is essentially the same as writing
 *
 * ```text
 * vec[f(0), f(1), f(2), …, f(length - 2), f(length - 1)]
 * ```
 * and is similar to `(0 .. i).map(f)`, just for `List<T>`s not iterators.
 *
 * If `length == 0`, this produces an empty `List<T>` without ever calling `f`.
 *
 * # Example
 *
 * ```kotlin
 * val vec = Vec.fromFn(5, { i -> i })
 * // indexes are:  0  1  2  3  4
 * check(vec == listOf(0, 1, 2, 3, 4))
 * val vec2 = Vec.fromFn(8, { i -> i * 2 })
 * // indexes are:   0  1  2  3  4  5   6   7
 * check(vec2 == listOf(0, 2, 4, 6, 8, 10, 12, 14))
 * val boolVec = Vec.fromFn(5, { i -> i % 2 == 0 })
 * // indexes are:       0     1      2     3      4
 * check(boolVec == booleanArrayOf(true, false, true, false, true))
 * ```
 *
 * The `List<T>` is generated in ascending index order, starting from the front
 * and going towards the back, so you can use closures with mutable state:
 * ```kotlin
 * var state = 1
 * val a = Vec.fromFn(6, {
 *     val x = state
 *     state *= 2
 *     x
 * })
 * check(a == listOf(1, 2, 4, 8, 16, 32))
 * ```
 */
public fun <T> Vec.Companion.fromFn(length: Int, f: (Int) -> T): Vec<T> = (0 until length).map(f).collect()

/**
 * Decomposes a `List<T>` into its raw components: `(pointer, length, capacity)`.
 *
 * Returns the raw pointer to the underlying data, the length of
 * the vector (in elements), and the allocated capacity of the
 * data (in elements). These are the same arguments in the same
 * order as the arguments to [fromRawParts].
 *
 * After calling this function, the caller is responsible for the
 * memory previously managed by the `List`. Most often, one does
 * this by converting the raw pointer, length, and capacity back
 * into a `List` with the [fromRawParts] function; more generally,
 * if `T` is non-zero-sized and the capacity is nonzero, one may use
 * any method that calls [dealloc] with a layout of
 * `Layout.array<T>(capacity)`; if `T` is zero-sized or the
 * capacity is zero, nothing needs to be done.
 *
 * [fromRawParts]: Vec.fromRawParts
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 *
 * val v: Vec<Int> = mutableListOf(-1, 0, 1)
 * val (ptr, len, cap) = v.intoRawParts()
 * val rebuilt = run {
 *     // We can now make changes to the components, such as
 *     // transmuting the raw pointer to a compatible type.
 *     val ptr1 = mutPtrFromRustCast(ptr)
 *     Vec.fromRawParts(ptr1, len, cap)
 * }
 * check(rebuilt == listOf(4294967295, 0, 1))
 * ```
 */
public fun <T> Vec<T>.intoRawParts(): Triple<MutPtr, Int, Int> {
    val me = this
    return Triple(me.asMutPtr(), me.len(), me.capacity())
}

/**
 * Decomposes a `List<T>` into its raw components: `(NonNull pointer, length, capacity)`.
 *
 * Returns the `NonNull` pointer to the underlying data, the length of
 * the vector (in elements), and the allocated capacity of the
 * data (in elements). These are the same arguments in the same
 * order as the arguments to [fromParts].
 *
 * After calling this function, the caller is responsible for the
 * memory previously managed by the `List`. The only way to do
 * this is to convert the `NonNull` pointer, length, and capacity back
 * into a `List` with the [fromParts] function, allowing
 * the destructor to perform the cleanup.
 *
 * [fromParts]: Vec.fromParts
 *
 * # Examples
 *
 * ```kotlin
 * val v: Vec<Int> = mutableListOf(-1, 0, 1)
 * val (ptr, len, cap) = v.intoParts()
 * val rebuilt = run {
 *     // We can now make changes to the components, such as
 *     // transmuting the raw pointer to a compatible type.
 *     val ptr1 = ptr.cast()
 *     Vec.fromParts(ptr1, len, cap)
 * }
 * check(rebuilt == listOf(4294967295, 0, 1))
 * ```
 */
public fun <T> Vec<T>.intoParts(): Triple<NonNull, Int, Int> {
    val (ptr, len, capacity) = this.intoRawParts()
    // SAFETY: A `List` always has a non-null pointer.
    return Triple(NonNull.newUnchecked(ptr), len, capacity)
}

/**
 * Interns the `List<T>`, making the underlying memory read-only. This method should be
 * called during compile time. (This is a no-op if called during runtime)
 *
 * This method must be called if the memory used by `List` needs to appear in the final
 * values of constants.
 */
public fun <T> Vec<T>.constMakeGlobal(): List<T> where T : Freeze {
    // `constMakeGlobal` requires the pointer to point to the beginning of a heap allocation,
    // which is not the case when `self.capacity()` is 0, or if `T.IS_ZST`,
    // which is why we instead return a new slice in this case.
    return if (this.capacity() == 0 || T.IS_ZST) {
        val me = this
        // ignore-tidy-undocumented-unchecked
        rawSliceList(NonNull.dangling().asPtr(), me.len.toInt(), PrimitiveLayouts.U8)
    } else {
        // ignore-tidy-undocumented-unchecked
        constMakeGlobal(this.asMutPtr())
        val me1 = this
        // ignore-tidy-undocumented-unchecked
        rawSliceList(me1.asPtr(), me1.len.toInt(), PrimitiveLayouts.U8)
    }
}

public fun <A : Allocator> Vec<UByte, A>.tryExtendFromSliceOfBytes(other: List<UByte>): Result<Unit, TryReserveError> {
    // ignore-tidy-undocumented-unchecked
    return this.tryAppendElements(other)
}

/**
 * Takes a `List<[T; N]>` and flattens it into a `List<T>`.
 *
 * # Panics
 *
 * Panics if the length of the resulting vector would overflow a `Int`.
 *
 * This is only possible when flattening a vector of arrays of zero-sized
 * types, and thus tends to be irrelevant in practice. If
 * `sizeOf<T>()> 0`, this will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * var vec = mutableListOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9))
 * check(vec.removeLastOrNull() == listOf(7, 8, 9))
 * var flattened = vec.intoFlattened()
 * check(flattened.pop() == 6)
 * ```
 */
public fun <T, A : Allocator> Vec<List<T>, A>.intoFlattened(n: Int): Vec<T, A> {
    val (ptr, len, cap, alloc) = this.intoRawPartsWithAllocator()
    val (newLen, newCap) =
        if (T.IS_ZST) {
            Pair((len.checkedMul(n) ?: error("the product of vec len and N shouldn't overflow")), Int.MAX_VALUE)
        } else {
            // SAFETY:
            // - `cap * N` cannot overflow because the allocation is already in
            // the address space.
            // - Each `[T; N]` has `N` valid elements, so there are `len * N`
            // valid elements in the allocation.
            Pair(len.uncheckedMul(n), cap.uncheckedMul(n))
        }
    // SAFETY:
    // - `ptr` was allocated by `self`
    // - `ptr` is well-aligned because `[T; N]` has the same alignment as `T`.
    // - `newCap` refers to the same sized allocation as `cap` because
    // `newCap * sizeOf<T>()` == `cap * sizeOf<[T; N]>()`
    // - `len` <= `cap`, so `len * N` <= `cap * N`.
    return Vec.fromRawPartsIn(ptr.cast(), newLen, newCap, alloc)
}

/**
 * Collects an iterator into a Vec, commonly called via [Iterator.collect]
 *
 * # Allocation behavior
 *
 * In general `List` does not guarantee any particular growth or allocation strategy.
 * That also applies to this trait impl.
 *
 * **Note:** This section covers implementation details and is therefore exempt from
 * stability guarantees.
 *
 * Vec may use any or none of the following strategies,
 * depending on the supplied iterator:
 *
 * * preallocate based on [Iterator.sizeHint]
 *   * and panic if the number of items is outside the provided lower/upper bounds
 * * use an amortized growth strategy similar to `pushing` one item at a time
 * * perform the iteration in-place on the original allocation backing the iterator
 *
 * The last case warrants some attention. It is an optimization that in many cases reduces peak memory
 * consumption and improves cache locality. But when big, short-lived allocations are created,
 * only a small fraction of their items get collected, no further use is made of the spare capacity
 * and the resulting `List` is moved into a longer-lived structure, then this can lead to the large
 * allocations having their scopes unnecessarily extended which can result in increased memory
 * footprint.
 *
 * In cases where this is an issue, the excess capacity can be discarded with [Vec.shrinkTo],
 * [Vec.shrinkToFit] or by collecting into [`Box`][owned slice] instead, which additionally reduces
 * the size of the long-lived struct.
 *
 * [owned slice]: Holder
 *
 * ```kotlin
 * var LONG_LIVED: Mutex<Vec<Vec<UShort>>> = Mutex.new(Vec.new())
 * for (i in 0 until 10) {
 *     val bigTemporary: Vec<UShort> = (0 until 1024).collect()
 *     // discard most items
 *     var result: Vec = bigTemporary.intoIter().filter({ i1 -> i1 % 100 == 0 }).collect()
 *     // without this a lot of unused capacity might be moved into the global
 *     result.shrinkToFit()
 *     LONG_LIVED.lock().unwrap().push(result)
 * }
 * ```
 */
public fun <T, I : Iterable<T>> fromIteratorFromIterVecT(iter: I): Vec<T> = Vec.fromIter(iter.iterator())

/**
 * Implements comparison of vectors, [lexicographically](Ord#lexicographical-comparison).
 */
public fun <T, A1, A2> Vec<T, A1>.partialCmp(other: Vec<T, A2>): Ordering? where A1 : Allocator, A2 : Allocator = partialOrdPartialCmpT(this, other)

/**
 * Creates an empty `List<T>`.
 *
 * The vector will not allocate until elements are pushed onto it.
 */
public fun <T> defaultVecT(): Vec<T> = Vec.new()

/**
 * Allocates a `List<T>` and fills it by cloning `s`s items.
 *
 * # Examples
 *
 * ```kotlin
 * check(Vec.from(listOf(1, 2, 3)) == mutableListOf(1, 2, 3))
 * ```
 */
public fun <T> fromVecT(s: List<T>): Vec<T> = s.toMutableList()

/**
 * Allocates a `List<T>` and fills it by cloning `s`s items.
 *
 * # Examples
 *
 * ```kotlin
 * check(Vec.from(listOf(1, 2, 3)) == mutableListOf(1, 2, 3))
 * ```
 */
public fun <T> fromVecT(s: MutableList<T>): Vec<T> = s.toMutableList()

/**
 * Allocates a `List<T>` and fills it by cloning `s`s items.
 *
 * # Examples
 *
 * ```kotlin
 * check(Vec.from(listOf(1, 2, 3)) == mutableListOf(1, 2, 3))
 * ```
 */
public fun <T> fromVecT(s: List<T>, n: Int): Vec<T> = Vec.from(s.asSlice())

/**
 * Allocates a `List<UByte>` and fills it with a UTF-8 string.
 *
 * # Examples
 *
 * ```kotlin
 * check(Vec.from("123") == mutableListOf(49u, 50u, 51u))
 * ```
 */
public fun fromVecUByte(s: String): Vec<UByte> = fromUByte(s.encodeToByteArray().map { it.toUByte() })

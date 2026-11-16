// port-lint: source slice.rs
package io.github.kotlinmania.rustlang.alloc.slice

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.sort.unstable.sort

// Utilities for the slice primitive type.
//
// *[See also the slice primitive type](slice).*
//
// Most of the structs in this module are iterator types which can only be created
// using a certain function. For example, `slice.iter()` yields an [Iter].
//
// A few functions are provided to create a slice from a value reference
// or from a raw pointer.

public typealias Owned<T> = List<T>

/**
 * Sorts the slice in ascending order, preserving initial order of equal elements.
 *
 * This sort is stable (i.e., does not reorder equal elements) and *O*(*n* \* log(*n*))
 * worst-case.
 *
 * If the implementation of [Ord] for `T` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * When applicable, unstable sorting is preferred because it is generally faster than stable
 * sorting and it doesn't allocate auxiliary memory. See
 * [`sortUnstable`](slice.sortUnstable). The exception are partially sorted slices, which
 * may be better served with `slice.sort`.
 *
 * Sorting types that only implement [PartialOrd] such as [f32] and [f64] require
 * additional precautions. For example, `Float.NAN != Float.NAN`, which doesn't fulfill the
 * reflexivity requirement of [Ord]. By using an alternative comparison function with
 * `slice.sortBy` such as [f32.totalCmp] or [f64.totalCmp] that defines a [total
 * order] users can sort slices containing floating-point values. Alternatively, if all values
 * in the slice are guaranteed to be in a subset for which [PartialOrd.partialCmp] forms a
 * [total order], it's possible to sort the slice with `sortBy(|a, b|
 * a.partialCmp(b).unwrap())`.
 *
 * # Current implementation
 *
 * The current implementation is based on [driftsort] by Orson Peters and Lukas Bergdoll, which
 * combines the fast average case of quicksort with the fast worst case and partial run
 * detection of mergesort, achieving linear time on fully sorted and reversed inputs. On inputs
 * with k distinct elements, the expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * The auxiliary memory allocation behavior depends on the input length. Short slices are
 * handled without allocation, medium sized slices allocate `self.len()` and beyond that it
 * clamps at `self.len() / 2`.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `T` does not implement a [total order], or if
 * the [Ord] implementation itself panics.
 *
 * All safe functions on slices preserve the invariant that even if the function panics, all
 * original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. This ensures that recovery code (for instance inside
 * of a `Drop` or following a `catchUnwind`) will still have access to all the original
 * elements. For instance, if the slice belongs to a `List`, the `List.drop` method will be able
 * to dispose of all contained elements.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(4, -5, 1, -3, 2)
 * v.sort()
 * check(v == listOf(-5, -3, 1, 2, 4))
 * ```
 *
 * [driftsort]: https://github.com/Voultapher/driftsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.sort(ltTFn: (Any?) -> Any?) {
    stableSort(this, ltTFn)
}

/**
 * Sorts the slice in ascending order with a comparison function, preserving initial order of
 * equal elements.
 *
 * This sort is stable (i.e., does not reorder equal elements) and *O*(*n* \* log(*n*))
 * worst-case.
 *
 * If the comparison function `compare` does not implement a [total order], the function may
 * panic; even if the function exits normally, the resulting order of elements in the slice is
 * unspecified. See also the note on panicking below.
 *
 * For example `| a, b |(a - b).cmp(a)` is a comparison function that is neither transitive nor
 * reflexive nor total, `a<b<c<a` with `a = 1, b = 2, c = 3`. For more information and
 * examples see the [Ord] documentation.
 *
 * # Current implementation
 *
 * The current implementation is based on [driftsort] by Orson Peters and Lukas Bergdoll, which
 * combines the fast average case of quicksort with the fast worst case and partial run
 * detection of mergesort, achieving linear time on fully sorted and reversed inputs. On inputs
 * with k distinct elements, the expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * The auxiliary memory allocation behavior depends on the input length. Short slices are
 * handled without allocation, medium sized slices allocate `self.len()` and beyond that it
 * clamps at `self.len() / 2`.
 *
 * # Panics
 *
 * May panic if `compare` does not implement a [total order], or if `compare` itself panics.
 *
 * All safe functions on slices preserve the invariant that even if the function panics, all
 * original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. This ensures that recovery code (for instance inside
 * of a `Drop` or following a `catchUnwind`) will still have access to all the original
 * elements. For instance, if the slice belongs to a `List`, the `List.drop` method will be able
 * to dispose of all contained elements.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(4, -5, 1, -3, 2)
 * v.sortBy({ a, b -> a.cmp(b) })
 * check(v == listOf(-5, -3, 1, 2, 4))
 * // reverse sorting
 * v.sortBy({ a, b -> b.cmp(a) })
 * check(v == listOf(4, 2, 1, -3, -5))
 * ```
 *
 * [driftsort]: https://github.com/Voultapher/driftsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.sortBy(compare: (T, T) -> Ordering) {
    stableSort(this, { a, b -> compare(a, b) == Less })
}

/**
 * Sorts the slice in ascending order with a key extraction function, preserving initial order
 * of equal elements.
 *
 * This sort is stable (i.e., does not reorder equal elements) and *O*(*m* \* *n* \* log(*n*))
 * worst-case, where the key function is *O*(*m*).
 *
 * If the implementation of [Ord] for `K` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * # Current implementation
 *
 * The current implementation is based on [driftsort] by Orson Peters and Lukas Bergdoll, which
 * combines the fast average case of quicksort with the fast worst case and partial run
 * detection of mergesort, achieving linear time on fully sorted and reversed inputs. On inputs
 * with k distinct elements, the expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * The auxiliary memory allocation behavior depends on the input length. Short slices are
 * handled without allocation, medium sized slices allocate `self.len()` and beyond that it
 * clamps at `self.len() / 2`.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `K` does not implement a [total order], or if
 * the [Ord] implementation or the key-function `f` panics.
 *
 * All safe functions on slices preserve the invariant that even if the function panics, all
 * original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. This ensures that recovery code (for instance inside
 * of a `Drop` or following a `catchUnwind`) will still have access to all the original
 * elements. For instance, if the slice belongs to a `List`, the `List.drop` method will be able
 * to dispose of all contained elements.
 *
 * # Examples
 *
 * ```kotlin
 * var v = intArrayOf(4, -5, 1, -3, 2)
 * v.sortByKey({ k -> k.abs() })
 * check(v == intArrayOf(1, 2, -3, 4, -5))
 * ```
 *
 * [driftsort]: https://github.com/Voultapher/driftsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T, K> List<T>.sortByKey(f: (T) -> K) {
    stableSort(this, { a, b -> f(a).lt(f(b)) })
}

/**
 * Sorts the slice in ascending order with a key extraction function, preserving initial order
 * of equal elements.
 *
 * This sort is stable (i.e., does not reorder equal elements) and *O*(*m* \* *n* + *n* \*
 * log(*n*)) worst-case, where the key function is *O*(*m*).
 *
 * During sorting, the key function is called at most once per element, by using temporary
 * storage to remember the results of key evaluation. The order of calls to the key function is
 * unspecified and may change in future versions of the standard library.
 *
 * If the implementation of [Ord] for `K` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * For simple key functions (e.g., functions that are property accesses or basic operations),
 * [`sortByKey`](slice.sortByKey) is likely to be faster.
 *
 * # Current implementation
 *
 * The current implementation is based on [instruction-parallel-network sort][ipnsort] by Lukas
 * Bergdoll, which combines the fast average case of randomized quicksort with the fast worst
 * case of heapsort, while achieving linear time on fully sorted and reversed inputs. And
 * *O*(*k* \* log(*n*)) where *k* is the number of distinct elements in the input. It leverages
 * superscalar out-of-order execution capabilities commonly found in CPUs, to efficiently
 * perform the operation.
 *
 * In the worst case, the algorithm allocates temporary storage in a `List<(K, Int)>` the
 * length of the slice.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `K` does not implement a [total order], or if
 * the [Ord] implementation panics.
 *
 * All safe functions on slices preserve the invariant that even if the function panics, all
 * original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. This ensures that recovery code (for instance inside
 * of a `Drop` or following a `catchUnwind`) will still have access to all the original
 * elements. For instance, if the slice belongs to a `List`, the `List.drop` method will be able
 * to dispose of all contained elements.
 *
 * # Examples
 *
 * ```kotlin
 * var v = intArrayOf(4, -5, 1, -3, 2, 10)
 * // Strings are sorted by lexicographical order.
 * v.sortByCachedKey({ k -> k.toString() })
 * check(v == intArrayOf(-3, -5, 1, 10, 2, 4))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T, K> List<T>.sortByCachedKey(f: (T) -> K, typeLayoutK: TypeLayout<K> = UnresolvedTypeLayout()) {
    // Helper macro for indexing our vector by the smallest possible type, to reduce allocation.
    val len = this.size
    if (len < 2) {
        return
    }
    // Avoids binary-size usage in cases where the alignment doesn't work out to make this
    // beneficial or on 32-bit platforms.
    val isUsingU32AsIdxTypeHelpful = sizeOf<Pair<K, UInt>>(typeLayoutT = UnresolvedTypeLayout<Pair<K, UInt>>()) < sizeOf<Pair<K, Int>>(typeLayoutT = UnresolvedTypeLayout<Pair<K, Int>>())
    // It's possible to instantiate this for u8 and u16 but, doing so is very wasteful in terms
    // of compile-times and binary-size, the peak saved heap memory for u16 is (u8 + u16) -> 4
    // bytes * u16.Max vs (u8 + u32) -> 8 bytes * u16.Max, the saved heap memory is at peak
    // ~262KB.
    if (isUsingU32AsIdxTypeHelpful && len <= (UInt.MAX_VALUE.toInt())) {
        return run {
            val indices: Vec  = this.map(f).mapIndexed { index, item -> Pair(index, item) }.map({ (i, k) -> Pair(k, i.toUInt()) }).collect()
            indices.sortUnstable()
            for (i in 0 until this.size) {
                var index = indices[i].second
                while ((index.toInt()) < i) {
                    index = indices[index.toInt()].second
                }
                indices[i].second = index
                this.swap(i, index.toInt())
            }
        }
    }
    run {
        val indices1: Vec  = this.map(f).mapIndexed { index, item -> Pair(index, item) }.map({ (i1, k) -> Pair(k, i1.toInt()) }).collect()
        indices1.sortUnstable()
        for (i1 in 0 until this.size) {
            var index1 = indices1[i1].second
            while ((index1.toInt()) < i1) {
                index1 = indices1[index1.toInt()].second
            }
            indices1[i1].second = index1
            this.swap(i1, index1.toInt())
        }
    }
}

/**
 * Copies `self` into a new `List`.
 *
 * # Examples
 *
 * ```kotlin
 * val s = listOf(10, 40, 30)
 * val x = s.toVec()
 * // Here, `s` and `x` can be modified independently.
 * ```
 */
public fun <T> List<T>.toVec(): List<T> = this.toVecIn(Global())

/**
 * Copies `self` into a new `List` with an allocator.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     val s = listOf(10, 40, 30)
 *     val x = s.toVecIn(System)
 *     // Here, `s` and `x` can be modified independently.
 * }
 * ```
 */
public fun <T, A : Allocator> List<T>.toVecIn(alloc: A, toVecTFn: (List<T>, A) -> List<T>): List<T> {
    interface ConvertVec {
        public fun <A : Allocator> toVec(s: List<ConvertVec>, alloc1: A): List<ConvertVec>
    }
    fun <T, A : Allocator> convertVecToVecT(s: List<T>, alloc1: A): List<T> {
        open class DropGuard<T, A : Allocator>(
            public var vec: List<T>,
            public var numInit: Int,
        ) {
            public open fun drop() {
                // SAFETY:
                // items were marked initialized in the loop below
                run {
                    this.vec.setLen(this.numInit)
                }
            }
        }
        var vec = Vec.withCapacityIn(s.size, alloc1)
        val guard = DropGuard(vec = vec, numInit = 0)
        val slots = guard.vec.spareCapacityMut()
        // .take(slots.len()) is necessary for LLVM to remove bounds checks
        // and has better codegen than zip.
        for ((i, b) in s.mapIndexed { index, item -> Pair(index, item) }.take(slots.len())) {
            guard.numInit = i
            slots[i].write(b.clone())
        }
        forget(guard)
        // SAFETY:
        // the vec was allocated and initialized above to at least this length.
        run {
            vec.setLen(s.size)
        }
        return vec
    }
    return toVecTFn(this, alloc)
}

/**
 * Converts `self` into a vector without clones or allocation.
 *
 * The resulting vector can be converted back into a box via
 * `List<T>`s `intoBoxedSlice` method.
 *
 * # Examples
 *
 * ```kotlin
 * val s: List<Int> = listOf(10, 40, 30)
 * val x = s.intoVec()
 * // `s` cannot be used anymore because it has been converted into `x`.
 * check(x == mutableListOf(10, 40, 30))
 * ```
 */
public fun <T, A : Allocator> List<T>.intoVec(): List<T> {
    val len = this.size
    val (b, alloc) = Box.intoRawWithAllocator(this)
    // SAFETY: `b` is currently allocated with `alloc` and was allocated with the
    // matching layout for an array of `T * len`, the length is equal to the capacity,
    // and the existence of a `Holder<[T]>` is proof that the first `len` elements are
    // valid `T`s.
    return Vec.fromRawPartsIn(MutPtr(b.toLong()), len, len, alloc)
}

/**
 * Creates a vector by copying a slice `n` times.
 *
 * # Panics
 *
 * This function will panic if the capacity would overflow.
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf(1, 2).repeat(3) == mutableListOf(1, 2, 1, 2, 1, 2))
 * ```
 *
 * A panic upon overflow:
 *
 * ```kotlin
 * // this will panic at runtime
 * "0123456789abcdef".encodeToByteArray().repeat(Int.MAX_VALUE)
 * ```
 */
public fun <T> List<T>.repeat(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> {
    if (n == 0) {
        return mutableListOf()
    }
    // If `n` is larger than zero, it can be split as
    // `n = 2 ^ expn + rem(2 ^ expn> rem, expn >= 0, rem >= 0)`.
    // `2 ^ expn` is the number represented by the leftmost '1' bit of `n`,
    // and `rem` is the remaining part of `n`.
    // Using `List` to access `setLen()`.
    val capacity = (this.size.checkedMul(n) ?: error("capacity overflow"))
    val buf = ArrayList<T>(capacity)
    // `2 ^ expn` repetition is done by doubling `buf` `expn`-times.
    buf.extend(this)
    run {
        var m = n shr 1
        // If `m> 0`, there are remaining bits up to the leftmost '1'.
        while (m > 0) {
            // `buf.extend(buf)`:
            // SAFETY: We're copying `len` elements after offsetting by `len`,
            // with the previous call to `extend` ensuring that the first `len`
            // elements are valid `T`s and the call to `withCapacity` ensuring
            // we have `len * n` space to write the new elements.
            // Each iteration of this loop doubles the number of initialised elements,
            // which is tracked via `m` - when `m == 0`, we've written `mostSignificantBit(n)`
            // elements to the buffer.
            run {
                copyNonoverlapping<T>(buf.asPtr(), MutPtr(((buf.asMutPtr())).rawAddress + ((buf.size).toLong() * (UnresolvedTypeLayout<*>()).strideBytes)), buf.size, typeLayoutT = typeLayoutT)
            }
            // `buf` has capacity of `self.len() * n`.
            val bufLen = buf.size
            // SAFETY: We initialised another `bufLen` elements above.
            buf.setLen(bufLen * 2)
            m = m shr 1
        }
    }
    // `rem` (`= n - 2 ^ expn`) repetition is done by copying
    // first `rem` repetitions from `buf` itself.
    val remLen = capacity - buf.size
    // `self.len() * rem`
    if (remLen > 0) {
        // `buf.extend(buf[0 .. remLen])`:
        // SAFETY: We're copying `remLen` elements after offsetting by `len`. The previous
        // looping `copyNonoverlapping` always doubled the number of instantiated elements,
        // and so if `remLen` was greater than `len` it would have allowed for another such
        // doubling, until such time that `remLen<len`. Thus, the space for these remaining
        // `remLen` elements must be preceded by more than `remLen` previously-copied
        // elements.
        // Setting the length is correct since we've initialised the whole `capacity`-length
        // space with copies of the previous `len` elements.
        run {
            // This is non-overlapping since `2 ^ expn> rem`.
            copyNonoverlapping<T>(buf.asPtr(), MutPtr(((buf.asMutPtr())).rawAddress + ((buf.size).toLong() * (UnresolvedTypeLayout<*>()).strideBytes)), remLen, typeLayoutT = typeLayoutT)
            // `buf.len() + remLen` equals to `buf.capacity()` (`= self.len() * n`).
            buf.setLen(capacity)
        }
    }
    return buf
}

/**
 * Flattens a slice of `T` into a single value `Self.Output`.
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf("hello", "world").concat() == "helloworld")
 * check(listOf(listOf(1, 2), listOf(3, 4)).concat() == listOf(1, 2, 3, 4))
 * ```
 */
public fun <T, Item> List<T>.concat(): Output = Concat.Concat(this)

/**
 * Flattens a slice of `T` into a single value `Self.Output`, placing a
 * given separator between each.
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf("hello", "world").join(" ") == "hello world")
 * check(listOf(listOf(1, 2), listOf(3, 4)).join(0) == listOf(1, 2, 0, 3, 4))
 * check(listOf(listOf(1, 2), listOf(3, 4)).join(listOf(0, 0)) == listOf(1, 2, 0, 0, 3, 4))
 * ```
 */
public fun <T, Separator> List<T>.join(sep: Separator): Output = Join.Join(this, sep)

/**
 * Flattens a slice of `T` into a single value `Self.Output`, placing a
 * given separator between each.
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf("hello", "world").connect(" ") == "hello world")
 * check(listOf(listOf(1, 2), listOf(3, 4)).connect(0) == listOf(1, 2, 0, 3, 4))
 * ```
 */
public fun <T, Separator> List<T>.connect(sep: Separator): Output = Join.Join(this, sep)

/**
 * Returns a vector containing a copy of this slice where each byte
 * is mapped to its ASCII upper case equivalent.
 *
 * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
 * but non-ASCII letters are unchanged.
 *
 * To uppercase the value in-place, use [makeAsciiUppercase].
 *
 * [makeAsciiUppercase]: slice.makeAsciiUppercase
 */
public fun List<UByte>.toAsciiUppercase(): List<UByte> = this.map({ b -> b.toAsciiUppercase() }).toMutableList()

/**
 * Returns a vector containing a copy of this slice where each byte
 * is mapped to its ASCII lower case equivalent.
 *
 * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
 * but non-ASCII letters are unchanged.
 *
 * To lowercase the value in-place, use [makeAsciiLowercase].
 *
 * [makeAsciiLowercase]: slice.makeAsciiLowercase
 */
public fun List<UByte>.toAsciiLowercase(): List<UByte> = this.map({ b -> b.toAsciiLowercase() }).toMutableList()

// ///////////////////////////////////////////////////////////////////////////
// Extension traits for slices over specific kinds of data
// ///////////////////////////////////////////////////////////////////////////

/**
 * Helper trait for [`concat`](slice.concat).
 *
 * Note: the `Item` type parameter is not used in this trait,
 * but it allows impls to be more generic.
 * Without it, we get this error:
 *
 * ```kotlin
 * error[E0207]: the type parameter `T` is not constrained by the impl trait, self type, or predica
 * - -> library / alloc / src / slice.rs: 608: 6
 * |
 * 608 | impl<T: Clone, V: Borrow<[T]>> Concat for [V] {
 * | ^ unconstrained type parameter
 * ```
 *
 * This is because there could exist `V` types with multiple `Borrow<[_]>` impls,
 * such that multiple `T` types would apply:
 *
 * ```kotlin
 * open class Foo(public var field0: List<UInt>, public var field1: List<String>) {
 *     public open fun borrow(): List<UInt> {
 *         return this.field0
 *     }
 *
 *     public open fun borrowStringSlice(): List<String> {
 *         return this.field1
 *     }
 * }
 * ```
 */
public interface Concat<Item, T, out Output> {
    /**
     * Implementation of [`concat`](slice.concat)
     */
    public fun concat(slice: T): Output
}

/**
 * Helper trait for [`join`](slice.join)
 */
public interface Join<Separator, T, out Output> {
    /**
     * Implementation of [`join`](slice.join)
     */
    public fun join(slice: T, sep: Separator): Output
}

public fun <T, V : Borrow<List<T>>> concatVSlice(slice: List<V>): List<T> {
    val size = slice.map({ slice1 -> slice1.borrow().len() }).sum()
    val result = ArrayList<T>(size)
    for (v in slice) {
        result.addAll(v.borrow())
    }
    return result
}

public fun <T, V : Borrow<List<T>>> joinVSlice(slice: List<V>, sep: T, cloneTFn: (T) -> T = { it }): List<T> {
    val iter = slice.iter()
    val first1 =
        when (val matchVal = iter.next()) {
            null -> { return mutableListOf() }
            else -> {
                val first = matchVal
                first
            }
        }
    val size = slice.map({ v -> v.borrow().len() }).sum<Int>() + slice.size - 1
    val result = ArrayList<T>(size)
    result.addAll(first1.borrow())
    for (v in iter) {
        result.add(cloneTFn(sep))
        result.addAll(v.borrow())
    }
    return result
}

public fun <T, V : Borrow<List<T>>> joinVSlice(slice: List<V>, sep: List<T>): List<T> {
    val iter = slice.iter()
    val first1 =
        when (val matchVal = iter.next()) {
            null -> { return mutableListOf() }
            else -> {
                val first = matchVal
                first
            }
        }
    val size = slice.map({ v -> v.borrow().len() }).sum<Int>() + sep.size * (slice.size - 1)
    val result = ArrayList<T>(size)
    result.addAll(first1.borrow())
    for (v in iter) {
        result.addAll(sep)
        result.addAll(v.borrow())
    }
    return result
}

// ///////////////////////////////////////////////////////////////////////////
// Standard trait implementations for slices
// ///////////////////////////////////////////////////////////////////////////
public fun <T, A : Allocator> List<T>.borrow(): List<T> = this

public fun <T, A : Allocator> List<T>.borrowMut(): MutableList<T> = this

// Specializable trait for implementing ToOwned.cloneInto. This is
// public in the crate and has the Allocator parameter so that
// vec.cloneFrom use it too.
public interface SpecCloneIntoVec<T, A : Allocator> {
    public fun cloneInto(target: List<T>)
}

public fun <T, A : Allocator> List<T>.cloneInto(target: List<T>) {
    // drop anything in target that will not be overwritten
    target.truncate(this.size)
    // target.len <= self.len due to the truncate above, so the
    // slices here are always in-bounds.
    val (init, tail) = this.splitAt(target.size)
    // reuse the contained values' allocations/resources.
    target.cloneFromSlice(init)
    target.extendFromSlice(tail)
}

public fun <T : TrivialClone, A : Allocator> List<T>.cloneIntoTA(target: List<T>) {
    target.clear()
    target.extendFromSlice(this)
}

public fun <T> List<T>.toOwned(): List<T> = this.toMutableList()

public fun <T> List<T>.cloneIntoFromTSlice(target: List<T>) {
    this.cloneInto(target)
}

// ///////////////////////////////////////////////////////////////////////////
// Sorting
// ///////////////////////////////////////////////////////////////////////////
public fun <T> stableSort(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    sort<T, (T, T) -> Boolean, List<T>>(v, isLess)
}

public fun <T> bufGuardWithCapacityVecT(capacity: Int): List<T> = ArrayList(capacity)

public fun <T> List<T>.asUninitSliceMut(): MutableList<MaybeUninit<T>> = this.spareCapacityMut()

// port-lint: source slice/iter.rs
package io.github.kotlinmania.rustlang.core.slice.iter

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.iter.adapters.fuse.nth
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.fromMut
import io.github.kotlinmania.rustlang.core.ptr.fromRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenance
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter.macros.len
import io.github.kotlinmania.rustlang.core.slice.splitAtMut
import io.github.kotlinmania.rustlang.core.slice.splitFirst

// Definitions of a bunch of iterators for `[T]`.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

public fun <T> List<T>.intoIter(): Iter<T> = this.iter()

public fun <T> MutableList<T>.intoIter(): IterMut<T> = this.iterMut()

/**
 * Immutable slice iterator
 *
 * This struct is created by the [iter] method on [slices].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // First, we need a slice to call the `iter` method on:
 * val slice = listOf(1, 2, 3)
 * // Then we call `iter` on the slice to get the `Iter` iterator,
 * // and iterate over it:
 * for (element in slice) {
 *     println("$element")
 * }
 * // This for loop actually already works without calling `iter`:
 * for (element1 in slice) {
 *     println("$element")
 * }
 * ```
 *
 * [iter]: slice.iter
 * [slices]: slice
 */
public open class Iter<T>(
    /**
     * The pointer to the next element to return, or the past-the-end location
     * if the iterator is empty.
     *
     * This address will be used for all ZST elements, never changed.
     */
    public var ptr: NonNull,
    /**
     * For non-ZSTs, the non-null pointer to the past-the-end element.
     *
     * For ZSTs, this is `ptr.withoutProvenanceMut(len)`.
     */
    public var endOrLen: ConstPtr,
    public var marker: Unit,
) : AsRef<List<T>>, TrustedRandomAccess, TrustedRandomAccessNoCoerce {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugTuple(f, "Iter").field(this.asSlice()).finish(); Result.Ok<Unit, Unit>(Unit) }

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * // First, we need a slice to call the `iter` method on:
     * val slice = listOf(1, 2, 3)
     * // Then we call `iter` on the slice to get the `Iter` iterator:
     * var iter = slice.iter()
     * // Here `asSlice` still returns the whole slice, so this prints "[1, 2, 3]":
     * println("${iter.asSlice()}")
     * // Now, we call the `next` method to remove the first element from the iterator:
     * iter.next()
     * // Here the iterator does not contain the first element of the slice any more,
     * // so `asSlice` only returns the last two elements of the slice,
     * // and so this prints "[2, 3]":
     * println("${iter.asSlice()}")
     * // The underlying slice has not been modified and still contains three elements,
     * // so this prints "[1, 2, 3]":
     * println("$slice")
     * ```
     */
    public open fun asSlice(): List<T> = this.makeSlice()

    public open fun clone(): Iter<T> = Iter(ptr = this.ptr, endOrLen = this.endOrLen, marker = this.marker)

    public override fun asRef(): List<T> = this.asSlice()

    public companion object {
        public fun <T> new(slice: List<T>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Iter<T> {
            val len = slice.size
            val ptr: NonNull = NonNull.fromRef(slice).cast()
            // SAFETY: Similar to `IterMut.new`.
            return run {
                val endOrLen =
                    if (((typeLayoutT).sizeBytes == 0L)) {
                        withoutProvenance(len)
                    } else {
                        ptr.asPtr().add(len)
                    }
                Iter(ptr = ptr, endOrLen = endOrLen, marker = Unit)
            }
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * Mutable slice iterator.
 *
 * This struct is created by the [iterMut] method on [slices].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // First, we need a slice to call the `iterMut` method on:
 * val slice = listOf(1, 2, 3)
 * // Then we call `iterMut` on the slice to get the `IterMut` iterator,
 * // iterate over it and increment each element value:
 * for (idx in slice.indices) {
 *     slice[idx] += 1
 * }
 * // We now have "[2, 3, 4]":
 * println("$slice")
 * ```
 *
 * [iterMut]: slice.iterMut
 * [slices]: slice
 */
public open class IterMut<T>(
    /**
     * The pointer to the next element to return, or the past-the-end location
     * if the iterator is empty.
     *
     * This address will be used for all ZST elements, never changed.
     */
    public var ptr: NonNull,
    /**
     * For non-ZSTs, the non-null pointer to the past-the-end element.
     *
     * For ZSTs, this is `ptr.withoutProvenanceMut(len)`.
     */
    public var endOrLen: MutPtr,
    public var marker: Unit,
) : AsRef<List<T>>, TrustedRandomAccess, TrustedRandomAccessNoCoerce {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugTuple(f, "IterMut").field(this.makeSlice()).finish(); Result.Ok<Unit, Unit>(Unit) }

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * To avoid creating  references that alias, this is forced
     * to consume the iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * // First, we need a slice to call the `iterMut` method on:
     * var slice = listOf(1, 2, 3)
     * // Then we call `iterMut` on the slice to get the `IterMut` struct:
     * var iter = slice.iterMut()
     * // Now, we call the `next` method to remove the first element of the iterator,
     * // unwrap and dereference what we get from `next` and increase its value by 1:
     * iter.next()!! += 1
     * // Here the iterator does not contain the first element of the slice any more,
     * // so `intoSlice` only returns the last two elements of the slice,
     * // and so this prints "[2, 3]":
     * println("${iter.intoSlice()}")
     * // The underlying slice still contains three elements, but its first element
     * // was increased by 1, so this prints "[2, 2, 3]":
     * println("$slice")
     * ```
     */
    public open fun intoSlice(): MutableList<T> {
        // SAFETY: the iterator was created from a mutable slice with pointer
        // `self.ptr` and length `len(self)`. This guarantees that all the prerequisites
        // for `fromRawPartsMut` are fulfilled.
        return linearMutSliceView(this.ptr.asPtr(), (len(this)).toInt(), PrimitiveLayouts.U8)
    }

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * // First, we need a slice to call the `iterMut` method on:
     * val slice = listOf(1, 2, 3)
     * // Then we call `iterMut` on the slice to get the `IterMut` iterator:
     * var iter = slice.iterMut()
     * // Here `asSlice` still returns the whole slice, so this prints "[1, 2, 3]":
     * println("${iter.asSlice()}")
     * // Now, we call the `next` method to remove the first element from the iterator
     * // and increment its value:
     * iter.next()!! += 1
     * // Here the iterator does not contain the first element of the slice any more,
     * // so `asSlice` only returns the last two elements of the slice,
     * // and so this prints "[2, 3]":
     * println("${iter.asSlice()}")
     * // The underlying slice still contains three elements, but its first element
     * // was increased by 1, so this prints "[2, 2, 3]":
     * println("$slice")
     * ```
     */
    public open fun asSlice(): List<T> = this.makeSlice()

    // Note: Uncomment the `AsMut<[T]>` impl when this gets stabilized.

    /**
     * Views the underlying data as a mutable subslice of the original data.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * @feature(sliceIterMutAsMutSlice)
     * public fun doctestEntry() {
     *     var slice: MutableList<Int> = mutableListOf(1, 2, 3)
     *     // First, we get the iterator:
     *     var iter = ArrayDeque(slice)
     *     // Then, we get a mutable slice from it:
     *     val mutSlice = iter.asMutSlice()
     *     // So if we check what the `asMutSlice` method returned, we have "[1, 2, 3]":
     *     check(mutSlice == listOf(1, 2, 3))
     *     // We can use it to mutate the slice:
     *     mutSlice[0] = 4
     *     mutSlice[2] = 5
     *     // Next, we can move to the second element of the slice, checking that
     *     // it yields the value we just wrote:
     *     check(iter.removeFirstOrNull() == RustMutableReference(allocateNativeCell(4, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
     *     // Now `asMutSlice` returns "[2, 5]":
     *     check(iter.asMutSlice() == listOf(2, 5))
     * }
     * ```
     */
    public open fun asMutSlice(): MutableList<T> {
        // SAFETY: the iterator was created from a mutable slice with pointer
        // `self.ptr` and length `len(self)`. This guarantees that all the prerequisites
        // for `fromRawPartsMut` are fulfilled.
        return linearMutSliceView(this.ptr.asPtr(), (len(this)).toInt(), PrimitiveLayouts.U8)
    }

    public override fun asRef(): List<T> = this.asSlice()

    public companion object {
        public fun <T> new(slice: MutableList<T>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): IterMut<T> {
            val len = slice.size
            val ptr: NonNull = NonNull.fromMut(slice).cast()
            // SAFETY: There are several things here:
            //
            // `ptr` has been obtained by `slice.asPtr()` where `slice` is a valid
            // reference thus it is non-NUL and safe to use and pass to
            // `NonNull.newUnchecked` .
            //
            // Adding `slice.len()` to the starting pointer gives a pointer
            // at the end of `slice`. `end` will never be dereferenced, only checked
            // for direct pointer equality with `ptr` to check if the iterator is
            // done.
            //
            // In the case of a ZST, the end pointer is just the length.  It's never
            // used as a pointer at all, and thus it's fine to have no provenance.
            //
            // See the `nextUnchecked` and `isEmpty` macros as well as the
            // `postIncStart` method for more information.
            return run {
                val endOrLen =
                    if (((typeLayoutT).sizeBytes == 0L)) {
                        withoutProvenanceMut(len)
                    } else {
                        ptr.asPtr().add(len)
                    }
                IterMut(ptr = ptr, endOrLen = endOrLen, marker = Unit)
            }
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An internal abstraction over the splitting iterators, so that
 * splitn, splitnMut etc can be implemented once.
 */
public interface SplitIter {
    /**
     * Marks the underlying iterator as complete, extracting the remaining
     * portion of the slice.
     */
    public fun finish(): Item?
}

/**
 * An iterator over subslices separated by elements that match a predicate
 * function.
 *
 * This struct is created by the [split] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(10, 40, 33, 20)
 * var iter = slice.split({ num -> num % 3 == 0 })
 * check(iter.next() == listOf(10, 40))
 * check(iter.next() == listOf(20))
 * check(iter.next() == null)
 * ```
 *
 * [split]: slice.split
 * [slices]: slice
 */
public open class Split<T, P, Output>(
    // Used for `SplitWhitespace` and `SplitAsciiWhitespace` `asStr` methods
    public var v: List<T>,
    public var pred: P,
    // Used for `SplitAsciiWhitespace` `asStr` method
    public var finished: Boolean,
) : SplitIter, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@Split.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a slice which contains items not yet handled by split.
     * # Example
     *
     * ```kotlin
     * @feature(splitAsSlice)
     * public fun doctestEntry() {
     *     val slice = listOf(1, 2, 3, 4, 5)
     *     var split = slice.split({ v -> v % 2 == 0 })
     *     check(split.next() != null)
     *     check(split.asSlice() == listOf(3, 4, 5))
     * }
     * ```
     */
    public open fun asSlice(): List<T> =
        if (this.finished) {
            listOf()
        } else {
            this.v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Split").field("v", this.v).field("finished", this.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Split<T, P, Output> = Split(v = this.v, pred = this.pred.clone(), finished = this.finished)

    public open fun next(): List<T>? {
        if (this.finished) {
            return null
        }
        return when (val matchVal = this.v.indexOfFirst({ x -> (this.pred)(x) }).takeIf { it >= 0 }) {
            null -> this.finish()
            else -> {
                val idx = matchVal
                val (left, right) = Pair(this.v.getUnchecked(0 until idx), this.v.getUnchecked(idx + 1 until Int.MAX_VALUE))
                val ret = left
                this.v = right
                ret
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.finished) {
            Pair(0, 0)
        } else {
            // If the predicate doesn't match anything, we yield one slice.
            // If it matches every element, we yield `len() + 1` empty slices.
            Pair(1, this.v.size + 1)
        }

    public open fun nextBack(): List<T>? {
        if (this.finished) {
            return null
        }
        return when (val matchVal = this.v.iter().indexOfLast({ x -> (this.pred)(x) }).takeIf { it >= 0 }) {
            null -> this.finish()
            else -> {
                val idx = matchVal
                val (left, right) = Pair(this.v.getUnchecked(0 until idx), this.v.getUnchecked(idx + 1 until Int.MAX_VALUE))
                val ret = right
                this.v = left
                ret
            }
        }
    }

    public override fun finish(): List<T>? =
        if (this.finished) {
            null
        } else {
            this.finished = true
            this.v
        }

    public companion object {
        public fun <T> new(slice: List<T>, pred: (T) -> Boolean): Split<T, P, Output> = Split(v = slice, pred = pred, finished = false)
    }
}

/**
 * An iterator over subslices separated by elements that match a predicate
 * function. Unlike `Split`, it contains the matched part as a terminator
 * of the subslice.
 *
 * This struct is created by the [splitInclusive] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(10, 40, 33, 20)
 * var iter = slice.splitInclusive({ num -> num % 3 == 0 })
 * check(iter.next() == listOf(10, 40, 33))
 * check(iter.next() == listOf(20))
 * check(iter.next() == null)
 * ```
 *
 * [splitInclusive]: slice.splitInclusive
 * [slices]: slice
 */
public open class SplitInclusive<T, P, Output>(
    public var v: List<T>,
    public var pred: P,
    public var finished: Boolean,
) : Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@SplitInclusive.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SplitInclusive").field("v", this.v).field("finished", this.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): SplitInclusive<T, P, Output> = SplitInclusive(v = this.v, pred = this.pred.clone(), finished = this.finished)

    public open fun next(): List<T>? {
        if (this.finished) {
            return null
        }
        val idx = ((this.v.indexOfFirst({ x -> (this.pred)(x) }).takeIf { it >= 0 })?.let({ idx -> idx + 1 }) ?: this.v.size)
        if (idx == this.v.size) {
            this.finished = true
        }
        val ret = ((this.v).subList(0, idx))
        this.v = ((this.v).subList(idx, (this.v).size))
        return ret
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.finished) {
            Pair(0, 0)
        } else {
            // If the predicate doesn't match anything, we yield one slice.
            // If it matches every element, we yield `len()` one-element slices,
            // or a single empty slice.
            Pair(1, max(1, this.v.size))
        }

    public open fun nextBack(): List<T>? {
        if (this.finished) {
            return null
        }
        // The last index of self.v is already checked and found to match
        // by the last iteration, so we start searching a new match
        // one index to the left.
        val remainder =
            if (this.v.isEmpty()) {
                listOf()
            } else {
                ((this.v).subList(0, (this.v.size - 1)))
            }
        val idx = ((remainder.iter().indexOfLast({ x -> (this.pred)(x) }).takeIf { it >= 0 })?.let({ idx -> idx + 1 }) ?: 0)
        if (idx == 0) {
            this.finished = true
        }
        val ret = ((this.v).subList(idx, (this.v).size))
        this.v = ((this.v).subList(0, idx))
        return ret
    }

    public companion object {
        public fun <T> new(slice: List<T>, pred: (T) -> Boolean): SplitInclusive<T, P, Output> {
            val finished = slice.isEmpty()
            return SplitInclusive(v = slice, pred = pred, finished = finished)
        }
    }
}

/**
 * An iterator over the mutable subslices of the vector which are separated
 * by elements that match `pred`.
 *
 * This struct is created by the [splitMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var v = mutableListOf(10, 40, 30, 20, 60, 50)
 * val iter = v.splitMut({ num -> num % 3 == 0 })
 * ```
 *
 * [splitMut]: slice.splitMut
 * [slices]: slice
 */
public open class SplitMut<T, P, Output>(
    public var v: MutableList<T>,
    public var pred: P,
    public var finished: Boolean,
) : SplitIter, Iterable<MutableList<T>> {
    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@SplitMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SplitMut").field("v", this.v).field("finished", this.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    public override fun finish(): MutableList<T>? =
        if (this.finished) {
            null
        } else {
            this.finished = true
            take(this.v)
        }

    public open fun next(): MutableList<T>? {
        if (this.finished) {
            return null
        }
        return when (val matchVal = this.v.indexOfFirst({ x -> (this.pred)(x) }).takeIf { it >= 0 }) {
            null -> this.finish()
            else -> {
                val idx = matchVal
                val tmp = take(this.v)
                // idx is the index of the element we are splitting on. We want to set self to the
                // region after idx, and return the subslice before and not including idx.
                // So first we split after idx
                val (head, tail) = tmp.splitAtMut(idx + 1)
                this.v = tail
                // Then return the subslice up to but not including the found element
                head[0 until idx]
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.finished) {
            Pair(0, 0)
        } else {
            // If the predicate doesn't match anything, we yield one slice.
            // If it matches every element, we yield `len() + 1` empty slices.
            Pair(1, this.v.size + 1)
        }

    public open fun nextBack(): MutableList<T>? {
        if (this.finished) {
            return null
        }
        val idxOpt = run {
            // work around borrowck limitations
            val pred = this.pred
            this.v.iter().indexOfLast({ x -> pred(x) }).takeIf { it >= 0 }
        }
        return when (idxOpt) {
            null -> this.finish()
            else -> {
                val idx = idxOpt
                val tmp = take(this.v)
                val (head, tail) = tmp.splitAtMut(idx)
                this.v = head
                tail[1 until tail.size]
            }
        }
    }

    public companion object {
        public fun <T> new(slice: MutableList<T>, pred: (T) -> Boolean): SplitMut<T, P, Output> = SplitMut(v = slice, pred = pred, finished = false)
    }
}

/**
 * An iterator over the mutable subslices of the vector which are separated
 * by elements that match `pred`. Unlike `SplitMut`, it contains the matched
 * parts in the ends of the subslices.
 *
 * This struct is created by the [splitInclusiveMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var v = mutableListOf(10, 40, 30, 20, 60, 50)
 * val iter = v.splitInclusiveMut({ num -> num % 3 == 0 })
 * ```
 *
 * [splitInclusiveMut]: slice.splitInclusiveMut
 * [slices]: slice
 */
public open class SplitInclusiveMut<T, P, Output>(
    public var v: MutableList<T>,
    public var pred: P,
    public var finished: Boolean,
) : Iterable<MutableList<T>> {
    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@SplitInclusiveMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SplitInclusiveMut").field("v", this.v).field("finished", this.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun next(): MutableList<T>? {
        if (this.finished) {
            return null
        }
        val idxOpt = run {
            // work around borrowck limitations
            val pred = this.pred
            this.v.indexOfFirst({ x -> pred(x) }).takeIf { it >= 0 }
        }
        val idx = idxOpt.map({ idx -> idx + 1 }).unwrapOr(this.v.size)
        if (idx == this.v.size) {
            this.finished = true
        }
        val tmp = take(this.v)
        val (head, tail) = tmp.splitAtMut(idx)
        this.v = tail
        return head
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.finished) {
            Pair(0, 0)
        } else {
            // If the predicate doesn't match anything, we yield one slice.
            // If it matches every element, we yield `len()` one-element slices,
            // or a single empty slice.
            Pair(1, max(1, this.v.size))
        }

    public open fun nextBack(): MutableList<T>? {
        if (this.finished) {
            return null
        }
        val idxOpt =
            if (this.v.isEmpty()) {
                null
            } else {
                // work around borrowck limitations
                val pred = this.pred
                // The last index of self.v is already checked and found to match
                // by the last iteration, so we start searching a new match
                // one index to the left.
                val remainder = ((this.v).subList(0, (this.v.size - 1)))
                remainder.iter().indexOfLast({ x -> pred(x) }).takeIf { it >= 0 }
            }
        val idx = idxOpt.map({ idx -> idx + 1 }).unwrapOr(0)
        if (idx == 0) {
            this.finished = true
        }
        val tmp = take(this.v)
        val (head, tail) = tmp.splitAtMut(idx)
        this.v = head
        return tail
    }

    public companion object {
        public fun <T> new(slice: MutableList<T>, pred: (T) -> Boolean): SplitInclusiveMut<T, P, Output> {
            val finished = slice.isEmpty()
            return SplitInclusiveMut(v = slice, pred = pred, finished = finished)
        }
    }
}

/**
 * An iterator over subslices separated by elements that match a predicate
 * function, starting from the end of the slice.
 *
 * This struct is created by the [rsplit] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(11, 22, 33, 0, 44, 55)
 * var iter = slice.rsplit({ num -> num == 0 })
 * check(iter.next() == listOf(44, 55))
 * check(iter.next() == listOf(11, 22, 33))
 * check(iter.next() == null)
 * ```
 *
 * [rsplit]: slice.rsplit
 * [slices]: slice
 */
public open class RSplit<T, P, Output> internal constructor(
    innerCtorArg: Split<T, P, Output>,
) : SplitIter, Iterable<List<T>> {
    public var inner: Split<T, P, Output> = innerCtorArg
        internal set

    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@RSplit.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "RSplit").field("v", this.inner.v).field("finished", this.inner.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): RSplit<T, P, Output> = RSplit(inner = this.inner.clone())

    public open fun next(): List<T>? = this.inner.nextBack()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun nextBack(): List<T>? = this.inner.next()

    public override fun finish(): List<T>? = this.inner.finish()

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: Split<T, P, Output>,
        ): RSplit<T, P, Output> = RSplit<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(slice: List<T>, pred: (T) -> Boolean): RSplit<T, P, Output> = RSplit(inner = Split.new(slice, pred))
    }
}

/**
 * An iterator over the subslices of the vector which are separated
 * by elements that match `pred`, starting from the end of the slice.
 *
 * This struct is created by the [rsplitMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf(11, 22, 33, 0, 44, 55)
 * val iter = slice.rsplitMut({ num -> num == 0 })
 * ```
 *
 * [rsplitMut]: slice.rsplitMut
 * [slices]: slice
 */
public open class RSplitMut<T, P, Output> internal constructor(
    innerCtorArg: SplitMut<T, P, Output>,
) : SplitIter, Iterable<MutableList<T>> {
    public var inner: SplitMut<T, P, Output> = innerCtorArg
        internal set

    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@RSplitMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "RSplitMut").field("v", this.inner.v).field("finished", this.inner.finished).finish(); Result.Ok<Unit, Unit>(Unit) }

    public override fun finish(): MutableList<T>? = this.inner.finish()

    public open fun next(): MutableList<T>? = this.inner.nextBack()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun nextBack(): MutableList<T>? = this.inner.next()

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: SplitMut<T, P, Output>,
        ): RSplitMut<T, P, Output> = RSplitMut<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(slice: MutableList<T>, pred: (T) -> Boolean): RSplitMut<T, P, Output> = RSplitMut(inner = SplitMut.new(slice, pred))
    }
}

/**
 * An private iterator over subslices separated by elements that
 * match a predicate function, splitting at most a fixed number of
 * times.
 */
public open class GenericSplitN<I>(
    public var iter: I,
    public var count: Int,
) {
    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<GenericSplitN<I>> =
            object : TypeLayout<GenericSplitN<I>> {
                override val alignBytes: Long = listOf(8L, typeLayoutI.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 8L)
                override val sizeBytes: Long = alignUpBytes((off1 + 8L), alignBytes)
                override fun readNative(addr: Long): GenericSplitN<I> =
                    GenericSplitN(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: GenericSplitN<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off1, value.count)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GenericSplitN<I> =
                    GenericSplitN(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GenericSplitN<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off1.toInt(), value.count)
                }
            }
    }
}

public fun <T, I : SplitIter> GenericSplitN<I>.next(): T? =
    when (this.count) {
        0 -> null
        1 -> {
            this.count -= 1
            this.iter.finish()
        }
        else -> {
            this.count -= 1
            this.iter.next()
        }
    }

public fun <T, I : SplitIter> GenericSplitN<I>.sizeHint(): Pair<Int, Int?> {
    val (lower, upperOpt) = this.iter.sizeHint()
    return Pair(minOf(this.count, lower), upperOpt.mapOr(this.count, { upper -> minOf(this.count, upper) }))
}

/**
 * An iterator over subslices separated by elements that match a predicate
 * function, limited to a given number of splits.
 *
 * This struct is created by the [splitn] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(10, 40, 30, 20, 60, 50)
 * var iter = slice.splitn(2, { num -> num % 3 == 0 })
 * check(iter.next() == listOf(10, 40))
 * check(iter.next() == listOf(20, 60, 50))
 * check(iter.next() == null)
 * ```
 *
 * [splitn]: slice.splitn
 * [slices]: slice
 */
public open class SplitN<T, P, Output> internal constructor(
    innerCtorArg: GenericSplitN<Split<T, P, Output>>,
) {
    public var inner: GenericSplitN<Split<T, P, Output>> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SplitN").field("inner", this.inner).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: GenericSplitN<Split<T, P, Output>>,
        ): SplitN<T, P, Output> = SplitN<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(s: Split<T, (T) -> Boolean, Output>, n: Int): SplitN<T, P, Output> = SplitN(inner = GenericSplitN(iter = s, count = n))
    }
}

/**
 * An iterator over subslices separated by elements that match a
 * predicate function, limited to a given number of splits, starting
 * from the end of the slice.
 *
 * This struct is created by the [rsplitn] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(10, 40, 30, 20, 60, 50)
 * var iter = slice.rsplitn(2, { num -> num % 3 == 0 })
 * check(iter.next() == listOf(50))
 * check(iter.next() == listOf(10, 40, 30, 20))
 * check(iter.next() == null)
 * ```
 *
 * [rsplitn]: slice.rsplitn
 * [slices]: slice
 */
public open class RSplitN<T, P, Output> internal constructor(
    innerCtorArg: GenericSplitN<RSplit<T, P, Output>>,
) {
    public var inner: GenericSplitN<RSplit<T, P, Output>> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "RSplitN").field("inner", this.inner).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: GenericSplitN<RSplit<T, P, Output>>,
        ): RSplitN<T, P, Output> = RSplitN<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(s: RSplit<T, (T) -> Boolean, Output>, n: Int): RSplitN<T, P, Output> = RSplitN(inner = GenericSplitN(iter = s, count = n))
    }
}

/**
 * An iterator over subslices separated by elements that match a predicate
 * function, limited to a given number of splits.
 *
 * This struct is created by the [splitnMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf(10, 40, 30, 20, 60, 50)
 * val iter = slice.splitnMut(2, { num -> num % 3 == 0 })
 * ```
 *
 * [splitnMut]: slice.splitnMut
 * [slices]: slice
 */
public open class SplitNMut<T, P, Output> internal constructor(
    innerCtorArg: GenericSplitN<SplitMut<T, P, Output>>,
) {
    public var inner: GenericSplitN<SplitMut<T, P, Output>> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SplitNMut").field("inner", this.inner).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: GenericSplitN<SplitMut<T, P, Output>>,
        ): SplitNMut<T, P, Output> = SplitNMut<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(s: SplitMut<T, (T) -> Boolean, Output>, n: Int): SplitNMut<T, P, Output> = SplitNMut(inner = GenericSplitN(iter = s, count = n))
    }
}

/**
 * An iterator over subslices separated by elements that match a
 * predicate function, limited to a given number of splits, starting
 * from the end of the slice.
 *
 * This struct is created by the [rsplitnMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf(10, 40, 30, 20, 60, 50)
 * val iter = slice.rsplitnMut(2, { num -> num % 3 == 0 })
 * ```
 *
 * [rsplitnMut]: slice.rsplitnMut
 * [slices]: slice
 */
public open class RSplitNMut<T, P, Output> internal constructor(
    innerCtorArg: GenericSplitN<RSplitMut<T, P, Output>>,
) {
    public var inner: GenericSplitN<RSplitMut<T, P, Output>> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "RSplitNMut").field("inner", this.inner).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun <T, P, Output> invoke(
            inner: GenericSplitN<RSplitMut<T, P, Output>>,
        ): RSplitNMut<T, P, Output> = RSplitNMut<T, P, Output>(
            innerCtorArg = inner,
        )

        public fun <T> new(s: RSplitMut<T, (T) -> Boolean, Output>, n: Int): RSplitNMut<T, P, Output> = RSplitNMut(inner = GenericSplitN(iter = s, count = n))
    }
}

/**
 * An iterator over overlapping subslices of length `size`.
 *
 * This struct is created by the [windows] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf('r', 'u', 's', 't')
 * var iter = slice.windows(2)
 * check(iter.next() == listOf('r', 'u'))
 * check(iter.next() == listOf('u', 's'))
 * check(iter.next() == listOf('s', 't'))
 * check(iter.next() == null)
 * ```
 *
 * [windows]: slice.windows
 * [slices]: slice
 */
public open class Windows<T> internal constructor(
    public var v: List<T>,
    sizeCtorArg: NonZero<Int, NonZeroUsizeInner>,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    public var size: NonZero<Int, NonZeroUsizeInner> = sizeCtorArg
        internal set

    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@Windows.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Windows<T> = Windows(v = this.v, size = this.size)

    public open fun next(): List<T>? =
        if (this.size.get() > this.v.size) {
            null
        } else {
            val ret = ((this.v).subList(0, this.size.get()))
            this.v = ((this.v).subList(1, (this.v).size))
            ret
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.size.get() > this.v.size) {
            Pair(0, 0)
        } else {
            val size = this.v.size - this.size.get() + 1
            Pair(size, size)
        }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? {
        val size = this.size.get()
        return if (this.v.get(n until Int.MAX_VALUE) != null && rest.get(0 until size) != null) {
            this.v = rest[1 until rest.size]
            nth
        } else {
            // setting length to 0 is cheaper than overwriting the pointer when assigning &[]
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }
    }

    public open fun last(): List<T>? =
        if (this.size.get() > this.v.size) {
            null
        } else {
            val start = this.v.size - this.size.get()
            ((this.v).subList(start, (this.v).size))
        }

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        // SAFETY: since the caller guarantees that `i` is in bounds,
        // which means that `i` cannot overflow an `Long`, and the
        // slice created by `fromRawParts` is a subslice of `self.v`
        // thus is guaranteed to be valid for the scope `a` of `self.v`.
        return linearSliceView(ConstPtr((this.v.asPtr()).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), this.size.get().toInt(), UnresolvedTypeLayout<T>())
    }

    public open fun nextBack(): List<T>? = this.nthBack(0)

    public open fun nthBack(n: Int): List<T>? =
        if (this.v.size.checkedSub(n) != null && end.checkedSub(this.size.get()) != null) {
            val res = ((this.v).subList(start, end))
            this.v = ((this.v).subList(0, end - 1))
            res
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }

    public companion object {
        public operator fun <T> invoke(
            v: List<T>,
            size: NonZero<Int, NonZeroUsizeInner>,
        ): Windows<T> = Windows<T>(
            v = v,
            sizeCtorArg = size,
        )

        public fun <T> new(slice: List<T>, size: NonZero<Int, NonZeroUsizeInner>): Windows<T> = Windows(v = slice, size = size)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) chunks (`chunkSize` elements at a
 * time), starting at the beginning of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last slice
 * of the iteration will be the remainder.
 *
 * This struct is created by the [chunks] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.chunks(2)
 * check(iter.next() == listOf('l', 'o'))
 * check(iter.next() == listOf('r', 'e'))
 * check(iter.next() == listOf('m'))
 * check(iter.next() == null)
 * ```
 *
 * [chunks]: slice.chunks
 * [slices]: slice
 */
public open class Chunks<T>(
    public var v: List<T>,
    public var chunkSize: Int,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@Chunks.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Chunks<T> = Chunks(v = this.v, chunkSize = this.chunkSize)

    public open fun next(): List<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val chunksz = minOf(this.v.size, this.chunkSize)
            val (fst, snd) = this.v.splitAt(chunksz)
            this.v = snd
            fst
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.v.isEmpty()) {
            Pair(0, 0)
        } else {
            val n = this.v.size.divCeil(this.chunkSize)
            Pair(n, n)
        }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? =
        if (n.checkedMul(this.chunkSize) != null && start < this.v.size) {
            val rest = ((this.v).subList(start, (this.v).size))
            val (chunk, rest1) = rest.splitAt(this.chunkSize.min(rest.size))
            this.v = rest1
            chunk
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }

    public open fun last(): List<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val start = (this.v.size - 1) / this.chunkSize * this.chunkSize
            ((this.v).subList(start, (this.v).size))
        }

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        val start = idx * this.chunkSize
        // SAFETY: the caller guarantees that `i` is in bounds,
        // which means that `start` must be in bounds of the
        // underlying `self.v` slice, and we made sure that `len`
        // is also in bounds of `self.v`. Thus, `start` cannot overflow
        // an `Long`, and the slice constructed by `fromRawParts`
        // is a subslice of `self.v` which is guaranteed to be valid
        // for the scope `a` of `self.v`.
        return run {
            val len = minOf(this.v.size.uncheckedSub(start), this.chunkSize)
            linearSliceView(ConstPtr((this.v.asPtr()).rawAddress + ((start).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), len.toInt(), UnresolvedTypeLayout<T>())
        }
    }

    public open fun nextBack(): List<T>? {
        return if (this.v.isEmpty()) {
            null
        } else {
            val remainder = this.v.size % this.chunkSize
            val chunksz =
                if (remainder != 0) {
                    remainder
                } else {
                    this.chunkSize
                }
            // SAFETY: splitAtUnchecked requires the argument be less than or
            // equal to the length. This is guaranteed, but subtle: `chunksz`
            // will always either be `self.v.len() % self.chunkSize`, which
            // will always evaluate to strictly less than `self.v.len()` (or
            // panic, in the case that `self.chunkSize` is zero), or it can be
            // `self.chunkSize`, in the case that the length is exactly
            // divisible by the chunk size.
            //
            // While it seems like using `self.chunkSize` in this case could
            // lead to a value greater than `self.v.len()`, it cannot: if
            // `self.chunkSize` were greater than `self.v.len()`, then
            // `self.v.len() % self.chunkSize` would return nonzero (note that
            // in this branch of the `if`, we already know that `self.v` is
            // non-empty).
            val (fst, snd) = this.v.splitAtUnchecked(this.v.size - chunksz)
            this.v = fst
            snd
        }
    }

    public open fun nthBack(n: Int): List<T>? {
        val len = this.len()
        return if (n < len) {
            val start = (len - 1 - n) * this.chunkSize
            val end = start + (this.v.size - start).min(this.chunkSize)
            val nthBack = ((this.v).subList(start, end))
            this.v = ((this.v).subList(0, start))
            nthBack
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }
    }

    public companion object {
        public fun <T> new(slice: List<T>, size: Int): Chunks<T> = Chunks(v = slice, chunkSize = size)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) mutable chunks (`chunkSize`
 * elements at a time), starting at the beginning of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last slice
 * of the iteration will be the remainder.
 *
 * This struct is created by the [chunksMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf('l', 'o', 'r', 'e', 'm')
 * val iter = slice.chunksMut(2)
 * ```
 *
 * [chunksMut]: slice.chunksMut
 * [slices]: slice
 */
public open class ChunksMut<T> internal constructor(
    vCtorArg: MutPtr,
    public var chunkSize: Int,
    public var marker: Unit,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<MutableList<T>> {
    /**
     * # Safety
     * This slice pointer must point at a valid region of `T` with at least length `v.len()`. Normally,
     * those requirements would mean that we could instead use a `[T]` here, but we cannot
     * because `__iterator_get_unchecked` needs to return `[T]`, which guarantees certain aliasing
     * properties that we cannot uphold if we hold on to the full original `[T]`. Wrapping a raw
     * slice instead lets us hand out non-overlapping `[T]` subslices of the slice we wrap.
     */
    public var v: MutPtr = vCtorArg
        internal set

    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@ChunksMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val sz = minOf(this.v.len(), this.chunkSize)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (head, tail) = this.v.splitAtMut(sz)
            this.v = tail
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            head
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.v.isEmpty()) {
            Pair(0, 0)
        } else {
            val n = this.v.len().divCeil(this.chunkSize)
            Pair(n, n)
        }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): MutableList<T>? =
        if (n.checkedMul(this.chunkSize) != null && start < this.v.len()) {
            // SAFETY: `start<self.v.len()` ensures this is in bounds
            val (_, rest) = this.v.splitAtMut(start)
            // SAFETY: `.min(rest.len()` ensures this is in bounds
            val (chunk, rest1) = rest.splitAtMut(this.chunkSize.min(rest.len()))
            this.v = rest1
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            chunk
        } else {
            this.v = listOf()
            null
        }

    public open fun last(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val start = (this.v.len() - 1) / this.chunkSize * this.chunkSize
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            this.v.getUncheckedMut(start until Int.MAX_VALUE)
        }

    public open fun iteratorGetUnchecked(idx: Int): MutableList<T> {
        val start = idx * this.chunkSize
        // SAFETY: see comments for `Chunks.__iterator_get_unchecked` and `self.v`.
        //
        // Also note that the caller also guarantees that we're never called
        // with the same index again, and that no other methods that will
        // access this subslice are called, so it is valid for the returned
        // slice to be mutable.
        return run {
            val len = minOf(this.v.len().uncheckedSub(start), this.chunkSize)
            linearMutSliceView(this.v.asMutPtr().add(start), len.toInt(), PrimitiveLayouts.U8)
        }
    }

    public open fun nextBack(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val remainder = this.v.len() % this.chunkSize
            val sz =
                if (remainder != 0) {
                    remainder
                } else {
                    this.chunkSize
                }
            val len = this.v.len()
            // SAFETY: Similar to `Chunks.nextBack`
            val (head, tail) = this.v.splitAtMutUnchecked(len - sz)
            this.v = head
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            tail
        }

    public open fun nthBack(n: Int): MutableList<T>? {
        val len = this.len()
        return if (n < len) {
            val start = (len - 1 - n) * this.chunkSize
            val end =
                when (val matchVal = start.checkedAdd(this.chunkSize)) {
                    null -> this.v.len()
                    else -> {
                        val res = matchVal
                        minOf(this.v.len(), res)
                    }
                }
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (temp, tail) = this.v.splitAtMut(end)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (head, nthBack) = temp.splitAtMut(start)
            this.v = head
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            nthBack
        } else {
            this.v = listOf()
            null
        }
    }

    public companion object {
        public operator fun <T> invoke(
            v: MutPtr,
            chunkSize: Int,
            marker: Unit,
        ): ChunksMut<T> = ChunksMut<T>(
            vCtorArg = v,
            chunkSize = chunkSize,
            marker = marker,
        )

        public fun <T> new(slice: MutableList<T>, size: Int): ChunksMut<T> = ChunksMut(v = slice, chunkSize = size, marker = Unit)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) chunks (`chunkSize` elements at a
 * time), starting at the beginning of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last
 * up to `chunkSize - 1` elements will be omitted but can be retrieved from
 * the [remainder] function from the iterator.
 *
 * This struct is created by the [chunksExact] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.chunksExact(2)
 * check(iter.next() == listOf('l', 'o'))
 * check(iter.next() == listOf('r', 'e'))
 * check(iter.next() == null)
 * ```
 *
 * [chunksExact]: slice.chunksExact
 * [remainder]: ChunksExact.remainder
 * [slices]: slice
 */
public open class ChunksExact<T>(
    public var v: List<T>,
    public var rem: List<T>,
    public var chunkSize: Int,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@ChunksExact.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns the remainder of the original slice that is not going to be
     * returned by the iterator. The returned slice has at most `chunkSize - 1`
     * elements.
     *
     * # Example
     *
     * ```kotlin
     * val slice = listOf('l', 'o', 'r', 'e', 'm')
     * var iter = slice.chunksExact(2)
     * check(iter.remainder() == listOf('m'))
     * check(iter.next() == listOf('l', 'o'))
     * check(iter.remainder() == listOf('m'))
     * check(iter.next() == listOf('r', 'e'))
     * check(iter.remainder() == listOf('m'))
     * check(iter.next() == null)
     * check(iter.remainder() == listOf('m'))
     * ```
     */
    public open fun remainder(): List<T> = this.rem

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): ChunksExact<T> = ChunksExact(v = this.v, rem = this.rem, chunkSize = this.chunkSize)

    public open fun next(): List<T>? =
        this.v.splitAtChecked(this.chunkSize).map({ (chunk, rest) ->
            this.v = rest
            chunk
        })

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.v.size / this.chunkSize
        return Pair(n, n)
    }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? =
        if (n.checkedMul(this.chunkSize) != null && start < this.v.size) {
            this.v = ((this.v).subList(start, (this.v).size))
            this.next()
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }

    public open fun last(): List<T>? = this.nextBack()

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        val start = idx * this.chunkSize
        // SAFETY: mostly identical to `Chunks.__iterator_get_unchecked`.
        return linearSliceView(ConstPtr((this.v.asPtr()).rawAddress + ((start).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), this.chunkSize.toInt(), UnresolvedTypeLayout<T>())
    }

    public open fun nextBack(): List<T>? =
        if (this.v.size < this.chunkSize) {
            null
        } else {
            val (fst, snd) = this.v.splitAt(this.v.size - this.chunkSize)
            this.v = fst
            snd
        }

    public open fun nthBack(n: Int): List<T>? {
        val len = this.len()
        return if (n < len) {
            val start = (len - 1 - n) * this.chunkSize
            val end = start + this.chunkSize
            val nthBack = ((this.v).subList(start, end))
            this.v = ((this.v).subList(0, start))
            nthBack
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }
    }

    public open fun isEmpty(): Boolean = this.v.isEmpty()

    public companion object {
        public fun <T> new(slice: List<T>, chunkSize: Int): ChunksExact<T> {
            val rem = slice.size % chunkSize
            val fstLen = slice.size - rem
            // SAFETY: 0 <= fstLen <= slice.len() by construction above
            val (fst, snd) = slice.splitAtUnchecked(fstLen)
            return ChunksExact(v = fst, rem = snd, chunkSize = chunkSize)
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) mutable chunks (`chunkSize`
 * elements at a time), starting at the beginning of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last up to
 * `chunkSize - 1` elements will be omitted but can be retrieved from the
 * [intoRemainder] function from the iterator.
 *
 * This struct is created by the [chunksExactMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf('l', 'o', 'r', 'e', 'm')
 * val iter = slice.chunksExactMut(2)
 * ```
 *
 * [chunksExactMut]: slice.chunksExactMut
 * [intoRemainder]: ChunksExactMut.intoRemainder
 * [slices]: slice
 */
public open class ChunksExactMut<T> internal constructor(
    vCtorArg: MutPtr,
    public var rem: MutableList<T>,
    // The iterator never yields from here, so this can be unique
    public var chunkSize: Int,
    public var marker: Unit,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<MutableList<T>> {
    /**
     * # Safety
     * This slice pointer must point at a valid region of `T` with at least length `v.len()`. Normally,
     * those requirements would mean that we could instead use a `[T]` here, but we cannot
     * because `__iterator_get_unchecked` needs to return `[T]`, which guarantees certain aliasing
     * properties that we cannot uphold if we hold on to the full original `[T]`. Wrapping a raw
     * slice instead lets us hand out non-overlapping `[T]` subslices of the slice we wrap.
     */
    public var v: MutPtr = vCtorArg
        internal set

    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@ChunksExactMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns the remainder of the original slice that is not going to be
     * returned by the iterator. The returned slice has at most `chunkSize - 1`
     * elements.
     */
    public open fun intoRemainder(): MutableList<T> = this.rem

    public open fun next(): MutableList<T>? {
        // SAFETY: we have `self`, so are allowed to temporarily materialize a mut slice
        return RustMutableReference(MutPtr((this.v).rawAddress), UnresolvedTypeLayout<List<T>>()).splitAtMutChecked(this.chunkSize).map({ (chunk, rest) ->
            this.v = rest
            chunk
        })
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.v.len() / this.chunkSize
        return Pair(n, n)
    }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): MutableList<T>? =
        if (n.checkedMul(this.chunkSize) != null && start < this.v.len()) {
            // SAFETY: `start<self.v.len()`
            this.v = this.v.splitAtMut(start).second
            this.next()
        } else {
            this.v = listOf()
            null
        }

    public open fun last(): MutableList<T>? = this.nextBack()

    public open fun iteratorGetUnchecked(idx: Int): MutableList<T> {
        val start = idx * this.chunkSize
        // SAFETY: see comments for `Chunks.__iterator_get_unchecked` and `self.v`.
        return linearMutSliceView(this.v.asMutPtr().add(start), this.chunkSize.toInt(), PrimitiveLayouts.U8)
    }

    public open fun nextBack(): MutableList<T>? =
        if (this.v.len() < this.chunkSize) {
            null
        } else {
            // SAFETY: This subtraction is inbounds because of the check above
            val (head, tail) = this.v.splitAtMut(this.v.len() - this.chunkSize)
            this.v = head
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            tail
        }

    public open fun nthBack(n: Int): MutableList<T>? {
        val len = this.len()
        return if (n < len) {
            val start = (len - 1 - n) * this.chunkSize
            val end = start + this.chunkSize
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (temp, tail) = replace(this.v, listOf()).splitAtMut(end)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (head, nthBack) = temp.splitAtMut(start)
            this.v = head
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            nthBack
        } else {
            this.v = listOf()
            null
        }
    }

    public open fun isEmpty(): Boolean = this.v.isEmpty()

    public companion object {
        public operator fun <T> invoke(
            v: MutPtr,
            rem: MutableList<T>,
            chunkSize: Int,
            marker: Unit,
        ): ChunksExactMut<T> = ChunksExactMut<T>(
            vCtorArg = v,
            rem = rem,
            chunkSize = chunkSize,
            marker = marker,
        )

        public fun <T> new(slice: MutableList<T>, chunkSize: Int): ChunksExactMut<T> {
            val rem = slice.size % chunkSize
            val fstLen = slice.size - rem
            // SAFETY: 0 <= fstLen <= slice.len() by construction above
            val (fst, snd) = slice.splitAtMutUnchecked(fstLen)
            return ChunksExactMut(v = fst, rem = snd, chunkSize = chunkSize, marker = Unit)
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * A windowed iterator over a slice in overlapping chunks (`N` elements at a
 * time), starting at the beginning of the slice
 *
 * This struct is created by the [arrayWindows] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf(0, 1, 2, 3)
 * var iter = slice.arrayWindows()
 * check(iter.next() == listOf(0, 1))
 * check(iter.next() == listOf(1, 2))
 * check(iter.next() == listOf(2, 3))
 * check(iter.next() == null)
 * ```
 *
 * [arrayWindows]: slice.arrayWindows
 * [slices]: slice
 */
public open class ArrayWindows<T>(
    public var v: List<T>,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@ArrayWindows.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): ArrayWindows<T> = ArrayWindows(v = this.v)

    public open fun next(): List<T>? {
        val ret = this.v.firstChunk()
        if (ret != null) {
            this.v = ((this.v).subList(1, (this.v).size))
        }
        return ret
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val size = this.v.size.saturatingSub((N - 1).toInt())
        return Pair(size, size)
    }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? {
        val idx = n.min(this.v.size)
        this.v = ((this.v).subList(idx, (this.v).size))
        return this.next()
    }

    public open fun last(): List<T>? = this.v.lastChunk()

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        // SAFETY: since the caller guarantees that `idx` is in bounds,
        // which means that `idx` cannot overflow an `Long`, and the
        // "slice" created by `castArray` is a subslice of `self.v`
        // thus is guaranteed to be valid for the scope `a` of `self.v`.
        return ConstPtr((this.v.asPtr()).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)).castArray()
    }

    public open fun nextBack(): List<T>? {
        val ret = this.v.lastChunk()
        if (ret != null) {
            this.v = ((this.v).subList(0, this.v.size - 1))
        }
        return ret
    }

    public open fun nthBack(n: Int): List<T>? {
        val idx = this.v.size.saturatingSub(n)
        this.v = ((this.v).subList(0, idx))
        return this.nextBack()
    }

    public open fun isEmpty(): Boolean = this.v.size < N

    public companion object {
        public fun <T> new(slice: List<T>, n: Int): ArrayWindows<T> = ArrayWindows(v = slice)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) chunks (`chunkSize` elements at a
 * time), starting at the end of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last slice
 * of the iteration will be the remainder.
 *
 * This struct is created by the [rchunks] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.rchunks(2)
 * check(iter.next() == listOf('e', 'm'))
 * check(iter.next() == listOf('o', 'r'))
 * check(iter.next() == listOf('l'))
 * check(iter.next() == null)
 * ```
 *
 * [rchunks]: slice.rchunks
 * [slices]: slice
 */
public open class RChunks<T>(
    public var v: List<T>,
    public var chunkSize: Int,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@RChunks.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): RChunks<T> = RChunks(v = this.v, chunkSize = this.chunkSize)

    public open fun next(): List<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val idx = this.v.size.saturatingSub(this.chunkSize)
            // SAFETY: self.chunkSize() > 0, so 0 <= idx < self.v.len().
            // Thus `idx` is in-bounds for `self.v` and can be used as a valid argument for `splitAtMutUnchecked`.
            val (rest, chunk) = this.v.splitAtUnchecked(idx)
            this.v = rest
            chunk
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.v.isEmpty()) {
            Pair(0, 0)
        } else {
            val n = this.v.size.divCeil(this.chunkSize)
            Pair(n, n)
        }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? =
        if (n.checkedMul(this.chunkSize) != null && end < this.v.size) {
            val end = this.v.size - end
            val rest = ((this.v).subList(0, end))
            val (rest1, chunk) = rest.splitAt(end.saturatingSub(this.chunkSize))
            this.v = rest1
            chunk
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }

    public open fun last(): List<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val rem = this.v.size % this.chunkSize
            val end =
                if (rem == 0) {
                    this.chunkSize
                } else {
                    rem
                }
            ((this.v).subList(0, end))
        }

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        val end = this.v.size - idx * this.chunkSize
        val start = end.saturatingSub(this.chunkSize)
        // SAFETY: mostly identical to `Chunks.__iterator_get_unchecked`.
        return linearSliceView(ConstPtr((this.v.asPtr()).rawAddress + ((start).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), (end - start).toInt(), UnresolvedTypeLayout<T>())
    }

    public open fun nextBack(): List<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val remainder = this.v.size % this.chunkSize
            val chunksz =
                if (remainder != 0) {
                    remainder
                } else {
                    this.chunkSize
                }
            // SAFETY: similar to Chunks.nextBack
            val (fst, snd) = this.v.splitAtUnchecked(chunksz)
            this.v = snd
            fst
        }

    public open fun nthBack(n: Int): List<T>? {
        val len = this.len()
        return if (n < len) {
            val offsetFromEnd = (len - 1 - n) * this.chunkSize
            val end = this.v.size - offsetFromEnd
            val start = end.saturatingSub(this.chunkSize)
            val nthBack = ((this.v).subList(start, end))
            this.v = ((this.v).subList(end, (this.v).size))
            nthBack
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }
    }

    public companion object {
        public fun <T> new(slice: List<T>, size: Int): RChunks<T> = RChunks(v = slice, chunkSize = size)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) mutable chunks (`chunkSize`
 * elements at a time), starting at the end of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last slice
 * of the iteration will be the remainder.
 *
 * This struct is created by the [rchunksMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf('l', 'o', 'r', 'e', 'm')
 * val iter = slice.rchunksMut(2)
 * ```
 *
 * [rchunksMut]: slice.rchunksMut
 * [slices]: slice
 */
public open class RChunksMut<T> internal constructor(
    vCtorArg: MutPtr,
    public var chunkSize: Int,
    public var marker: Unit,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<MutableList<T>> {
    /**
     * # Safety
     * This slice pointer must point at a valid region of `T` with at least length `v.len()`. Normally,
     * those requirements would mean that we could instead use a `[T]` here, but we cannot
     * because `__iterator_get_unchecked` needs to return `[T]`, which guarantees certain aliasing
     * properties that we cannot uphold if we hold on to the full original `[T]`. Wrapping a raw
     * slice instead lets us hand out non-overlapping `[T]` subslices of the slice we wrap.
     */
    public var v: MutPtr = vCtorArg
        internal set

    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@RChunksMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val idx = this.v.len().saturatingSub(this.chunkSize)
            // SAFETY: self.chunkSize() > 0, so 0 <= idx < self.v.len().
            // Thus `idx` is in-bounds for `self.v` and can be used as a valid argument for `splitAtMutUnchecked`.
            val (rest, chunk) = this.v.splitAtMutUnchecked(idx)
            this.v = rest
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            chunk
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.v.isEmpty()) {
            Pair(0, 0)
        } else {
            val n = this.v.len().divCeil(this.chunkSize)
            Pair(n, n)
        }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): MutableList<T>? =
        if (n.checkedMul(this.chunkSize) != null && end < this.v.len()) {
            val end = this.v.len() - end
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (rest, _) = this.v.splitAtMut(end)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (rest1, chunk) = rest.splitAtMut(end.saturatingSub(this.chunkSize))
            this.v = rest1
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            chunk
        } else {
            this.v = listOf()
            null
        }

    public open fun last(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val rem = this.v.len() % this.chunkSize
            val end =
                if (rem == 0) {
                    this.chunkSize
                } else {
                    rem
                }
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            this.v.getUncheckedMut(0 until end)
        }

    public open fun iteratorGetUnchecked(idx: Int): MutableList<T> {
        val end = this.v.len() - idx * this.chunkSize
        val start = end.saturatingSub(this.chunkSize)
        // SAFETY: see comments for `RChunks.__iterator_get_unchecked` and
        // `ChunksMut.__iterator_get_unchecked`, `self.v`.
        return linearMutSliceView(this.v.asMutPtr().add(start), (end - start).toInt(), PrimitiveLayouts.U8)
    }

    public open fun nextBack(): MutableList<T>? =
        if (this.v.isEmpty()) {
            null
        } else {
            val remainder = this.v.len() % this.chunkSize
            val sz =
                if (remainder != 0) {
                    remainder
                } else {
                    this.chunkSize
                }
            // SAFETY: Similar to `Chunks.nextBack`
            val (head, tail) = this.v.splitAtMutUnchecked(sz)
            this.v = tail
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            head
        }

    public open fun nthBack(n: Int): MutableList<T>? {
        val len = this.len()
        return if (n < len) {
            // can't underflow because `n<len`
            val offsetFromEnd = (len - 1 - n) * this.chunkSize
            val end = this.v.len() - offsetFromEnd
            val start = end.saturatingSub(this.chunkSize)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (tmp, tail) = this.v.splitAtMut(end)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (_, nthBack) = tmp.splitAtMut(start)
            this.v = tail
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            nthBack
        } else {
            this.v = listOf()
            null
        }
    }

    public companion object {
        public operator fun <T> invoke(
            v: MutPtr,
            chunkSize: Int,
            marker: Unit,
        ): RChunksMut<T> = RChunksMut<T>(
            vCtorArg = v,
            chunkSize = chunkSize,
            marker = marker,
        )

        public fun <T> new(slice: MutableList<T>, size: Int): RChunksMut<T> = RChunksMut(v = slice, chunkSize = size, marker = Unit)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) chunks (`chunkSize` elements at a
 * time), starting at the end of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last
 * up to `chunkSize - 1` elements will be omitted but can be retrieved from
 * the [remainder] function from the iterator.
 *
 * This struct is created by the [rchunksExact] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.rchunksExact(2)
 * check(iter.next() == listOf('e', 'm'))
 * check(iter.next() == listOf('o', 'r'))
 * check(iter.next() == null)
 * ```
 *
 * [rchunksExact]: slice.rchunksExact
 * [remainder]: RChunksExact.remainder
 * [slices]: slice
 */
public open class RChunksExact<T>(
    public var v: List<T>,
    public var rem: List<T>,
    public var chunkSize: Int,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        override fun computeNext() {
            when (val v = this@RChunksExact.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns the remainder of the original slice that is not going to be
     * returned by the iterator. The returned slice has at most `chunkSize - 1`
     * elements.
     *
     * # Example
     *
     * ```kotlin
     * val slice = listOf('l', 'o', 'r', 'e', 'm')
     * var iter = slice.rchunksExact(2)
     * check(iter.remainder() == listOf('l'))
     * check(iter.next() == listOf('e', 'm'))
     * check(iter.remainder() == listOf('l'))
     * check(iter.next() == listOf('o', 'r'))
     * check(iter.remainder() == listOf('l'))
     * check(iter.next() == null)
     * check(iter.remainder() == listOf('l'))
     * ```
     */
    public open fun remainder(): List<T> = this.rem

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): RChunksExact<T> = RChunksExact(v = this.v, rem = this.rem, chunkSize = this.chunkSize)

    public open fun next(): List<T>? =
        if (this.v.size < this.chunkSize) {
            null
        } else {
            val (fst, snd) = this.v.splitAt(this.v.size - this.chunkSize)
            this.v = fst
            snd
        }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.v.size / this.chunkSize
        return Pair(n, n)
    }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): List<T>? =
        if (n.checkedMul(this.chunkSize) != null && end < this.v.size) {
            this.v = ((this.v).subList(0, this.v.size - end))
            this.next()
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }

    public open fun last(): List<T>? = this.nextBack()

    public open fun iteratorGetUnchecked(idx: Int): List<T> {
        val end = this.v.size - idx * this.chunkSize
        val start = end - this.chunkSize
        // SAFETY: mostly identical to `Chunks.__iterator_get_unchecked`.
        return linearSliceView(ConstPtr((this.v.asPtr()).rawAddress + ((start).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), this.chunkSize.toInt(), UnresolvedTypeLayout<T>())
    }

    public open fun nextBack(): List<T>? =
        if (this.v.size < this.chunkSize) {
            null
        } else {
            val (fst, snd) = this.v.splitAt(this.chunkSize)
            this.v = snd
            fst
        }

    public open fun nthBack(n: Int): List<T>? {
        val len = this.len()
        return if (n < len) {
            // now that we know that `n` corresponds to a chunk,
            // none of these operations can underflow/overflow
            val offset = (len - n) * this.chunkSize
            val start = this.v.size - offset
            val end = start + this.chunkSize
            val nthBack = ((this.v).subList(start, end))
            this.v = ((this.v).subList(end, (this.v).size))
            nthBack
        } else {
            this.v = ((this.v).subList(0, 0))
            // cheaper than &[]
            null
        }
    }

    public open fun isEmpty(): Boolean = this.v.isEmpty()

    public companion object {
        public fun <T> new(slice: List<T>, chunkSize: Int): RChunksExact<T> {
            val rem = slice.size % chunkSize
            // SAFETY: 0 <= rem <= slice.len() by construction above
            val (fst, snd) = slice.splitAtUnchecked(rem)
            return RChunksExact(v = snd, rem = fst, chunkSize = chunkSize)
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over a slice in (non-overlapping) mutable chunks (`chunkSize`
 * elements at a time), starting at the end of the slice.
 *
 * When the slice len is not evenly divided by the chunk size, the last up to
 * `chunkSize - 1` elements will be omitted but can be retrieved from the
 * [intoRemainder] function from the iterator.
 *
 * This struct is created by the [rchunksExactMut] method on [slices].
 *
 * # Example
 *
 * ```kotlin
 * var slice = mutableListOf('l', 'o', 'r', 'e', 'm')
 * val iter = slice.rchunksExactMut(2)
 * ```
 *
 * [rchunksExactMut]: slice.rchunksExactMut
 * [intoRemainder]: RChunksExactMut.intoRemainder
 * [slices]: slice
 */
public open class RChunksExactMut<T> internal constructor(
    vCtorArg: MutPtr,
    public var rem: MutableList<T>,
    public var chunkSize: Int,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<MutableList<T>> {
    /**
     * # Safety
     * This slice pointer must point at a valid region of `T` with at least length `v.len()`. Normally,
     * those requirements would mean that we could instead use a `[T]` here, but we cannot
     * because `__iterator_get_unchecked` needs to return `[T]`, which guarantees certain aliasing
     * properties that we cannot uphold if we hold on to the full original `[T]`. Wrapping a raw
     * slice instead lets us hand out non-overlapping `[T]` subslices of the slice we wrap.
     */
    public var v: MutPtr = vCtorArg
        internal set

    override fun iterator(): Iterator<MutableList<T>> = object : AbstractIterator<MutableList<T>>() {
        override fun computeNext() {
            when (val v = this@RChunksExactMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns the remainder of the original slice that is not going to be
     * returned by the iterator. The returned slice has at most `chunkSize - 1`
     * elements.
     */
    public open fun intoRemainder(): MutableList<T> = this.rem

    public open fun next(): MutableList<T>? =
        if (this.v.len() < this.chunkSize) {
            null
        } else {
            val len = this.v.len()
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (head, tail) = this.v.splitAtMut(len - this.chunkSize)
            this.v = head
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            tail
        }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.v.len() / this.chunkSize
        return Pair(n, n)
    }

    public open fun count(): Int = this.len()

    public open fun nth(n: Int): MutableList<T>? =
        if (n.checkedMul(this.chunkSize) != null && end < this.v.len()) {
            val idx = this.v.len() - end
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (fst, _) = this.v.splitAtMut(idx)
            this.v = fst
            this.next()
        } else {
            this.v = listOf()
            null
        }

    public open fun last(): MutableList<T>? = this.nextBack()

    public open fun iteratorGetUnchecked(idx: Int): MutableList<T> {
        val end = this.v.len() - idx * this.chunkSize
        val start = end - this.chunkSize
        // SAFETY: see comments for `RChunksMut.__iterator_get_unchecked` and `self.v`.
        return linearMutSliceView(this.v.asMutPtr().add(start), this.chunkSize.toInt(), PrimitiveLayouts.U8)
    }

    public open fun nextBack(): MutableList<T>? =
        if (this.v.len() < this.chunkSize) {
            null
        } else {
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (head, tail) = this.v.splitAtMut(this.chunkSize)
            this.v = tail
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            head
        }

    public open fun nthBack(n: Int): MutableList<T>? {
        val len = this.len()
        return if (n < len) {
            // now that we know that `n` corresponds to a chunk,
            // none of these operations can underflow/overflow
            val offset = (len - n) * this.chunkSize
            val start = this.v.len() - offset
            val end = start + this.chunkSize
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (tmp, tail) = this.v.splitAtMut(end)
            // SAFETY: The self.v contract ensures that any splitAtMut is valid.
            val (_, nthBack) = tmp.splitAtMut(start)
            this.v = tail
            // SAFETY: Nothing else points to or will point to the contents of this slice.
            nthBack
        } else {
            this.v = listOf()
            null
        }
    }

    public open fun isEmpty(): Boolean = this.v.isEmpty()

    public companion object {
        public operator fun <T> invoke(
            v: MutPtr,
            rem: MutableList<T>,
            chunkSize: Int,
        ): RChunksExactMut<T> = RChunksExactMut<T>(
            vCtorArg = v,
            rem = rem,
            chunkSize = chunkSize,
        )

        public fun <T> new(slice: MutableList<T>, chunkSize: Int): RChunksExactMut<T> {
            val rem = slice.size % chunkSize
            // SAFETY: 0 <= rem <= slice.len() by construction above
            val (fst, snd) = slice.splitAtMutUnchecked(rem)
            return RChunksExactMut(v = snd, rem = fst, chunkSize = chunkSize)
        }

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * An iterator over slice in (non-overlapping) chunks separated by a predicate.
 *
 * This struct is created by the [chunkBy] method on [slices].
 *
 * [chunkBy]: slice.chunkBy
 * [slices]: slice
 */
public open class ChunkBy<T, P>(
    public var slice: List<T>,
    public var predicate: P,
) {
    public open fun clone(): ChunkBy<T, P> = ChunkBy(slice = this.slice, predicate = this.predicate.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "ChunkBy").field("slice", this.slice).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <T, P> new(slice: List<T>, predicate: P): ChunkBy<T, P> = ChunkBy(slice = slice, predicate = predicate)
    }
}

public fun <T> ChunkBy<T, P>.next(): List<T>? =
    if (this.slice.isEmpty()) {
        null
    } else {
        var len = 1
        val iter = this.slice.windows(2)
        while (true) {
            val matchVal = iter.next()
            if (matchVal == null) break
            val l = matchVal[0]
            val r = matchVal[1]
            if ((this.predicate)(l, r)) {
                len += 1
            } else {
                break
            }
        }
        val (head, tail) = this.slice.splitAt(len)
        this.slice = tail
        head
    }

public fun <T> ChunkBy<T, P>.sizeHint(): Pair<Int, Int?> =
    if (this.slice.isEmpty()) {
        Pair(0, 0)
    } else {
        Pair(1, this.slice.size)
    }

public fun <T> ChunkBy<T, P>.count(): Int {
    var (previous, rest) = this.slice.splitFirst() ?: return 0
    var count = 1
    for (current in rest) {
        count += Int.from(!(this.predicate)(previous, current))
        previous = current
    }
    return count
}

public fun <T> ChunkBy<T, P>.last(): List<T>? = this.nextBack()

public fun <T> ChunkBy<T, P>.nextBack(): List<T>? =
    if (this.slice.isEmpty()) {
        null
    } else {
        var len = 1
        val iter = this.slice.windows(2)
        while (true) {
            val matchVal = iter.nextBack()
            if (matchVal == null) break
            val l = matchVal[0]
            val r = matchVal[1]
            if ((this.predicate)(l, r)) {
                len += 1
            } else {
                break
            }
        }
        val (head, tail) = this.slice.splitAt(this.slice.size - len)
        this.slice = head
        tail
    }

/**
 * An iterator over slice in (non-overlapping) mutable chunks separated
 * by a predicate.
 *
 * This struct is created by the [chunkByMut] method on [slices].
 *
 * [chunkByMut]: slice.chunkByMut
 * [slices]: slice
 */
public open class ChunkByMut<T, P>(
    public var slice: MutableList<T>,
    public var predicate: P,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "ChunkByMut").field("slice", this.slice).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <T, P> new(slice: MutableList<T>, predicate: P): ChunkByMut<T, P> = ChunkByMut(slice = slice, predicate = predicate)
    }
}

public fun <T> ChunkByMut<T, P>.next(): MutableList<T>? =
    if (this.slice.isEmpty()) {
        null
    } else {
        var len = 1
        val iter = this.slice.windows(2)
        while (true) {
            val matchVal = iter.next()
            if (matchVal == null) break
            val l = matchVal[0]
            val r = matchVal[1]
            if ((this.predicate)(l, r)) {
                len += 1
            } else {
                break
            }
        }
        val slice = take(this.slice)
        val (head, tail) = slice.splitAtMut(len)
        this.slice = tail
        head
    }

public fun <T> ChunkByMut<T, P>.sizeHint(): Pair<Int, Int?> =
    if (this.slice.isEmpty()) {
        Pair(0, 0)
    } else {
        Pair(1, this.slice.size)
    }

public fun <T> ChunkByMut<T, P>.last(): MutableList<T>? = this.nextBack()

public fun <T> ChunkByMut<T, P>.nextBack(): MutableList<T>? =
    if (this.slice.isEmpty()) {
        null
    } else {
        var len = 1
        val iter = this.slice.windows(2)
        while (true) {
            val matchVal = iter.nextBack()
            if (matchVal == null) break
            val l = matchVal[0]
            val r = matchVal[1]
            if ((this.predicate)(l, r)) {
                len += 1
            } else {
                break
            }
        }
        val slice = take(this.slice)
        val (head, tail) = slice.splitAtMut(slice.len() - len)
        this.slice = head
        tail
    }

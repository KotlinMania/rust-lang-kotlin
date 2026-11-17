// port-lint: source array/iter.rs
package io.github.kotlinmania.rustlang.core.array.iter

import io.github.kotlinmania.rustlang.core.array.iter.iterinner.PolymorphicIter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.indexrange.IndexRange
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result

// Defines the `IntoIter` owned iterator for arrays.

public typealias InnerSized<T> = PolymorphicIter<List<MaybeUninit<T>>>

public typealias InnerUnsized<T> = PolymorphicIter<List<MaybeUninit<T>>>

/**
 * A by-value [array] iterator.
 */
public open class IntoIter<T>(
    public var inner: InnerSized<T>,
) : TrustedLen<Any>, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<T> = IntoIter(inner)

    public open fun unsize(): InnerUnsized<T> = this.inner.deref()

    public open fun unsizeMut(): InnerUnsized<T> = this.inner.derefMut()

    /**
     * Returns an immutable slice of all elements that have not been yielded
     * yet.
     */
    public open fun asSlice(): List<T> = this.unsize().asSlice()

    /**
     * Returns a mutable slice of all elements that have not been yielded yet.
     */
    public open fun asMutSlice(): MutableList<T> = this.unsizeMut().asMutSlice()

    public open fun next(): T? = this.unsizeMut().next()

    public open fun sizeHint(): Pair<Int, Int?> = this.unsize().sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, T) -> Acc): Acc = this.unsizeMut().fold(init, fold)

    public open fun <Residual, B, R> tryFold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> = this.unsizeMut().tryFold(init, f)

    public open fun count(): Int = this.len()

    public open fun last(): T? = this.nextBack()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.unsizeMut().advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): T {
        // SAFETY: The caller must provide an idx that is in bound of the remainder.
        val elemRef = this.asMutSlice().getUncheckedMut(idx)
        // SAFETY: We only implement `TrustedRandomAccessNoCoerce` for types
        // which are actually `Copy`, so cannot have multiple-drop issues.
        return read(elemRef)
    }

    public open fun nextBack(): T? = this.unsizeMut().nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, T) -> Acc): Acc = this.unsizeMut().rfold(init, rfold)

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> = this.unsizeMut().tryRfold(init, f)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.unsizeMut().advanceBackBy(n)

    // Even though all the Drop logic could be completely handled by
    // PolymorphicIter, this impl still serves two purposes:
    // - Drop has been part of the public API, so we can't remove it
    // - the partialDrop function doesn't always get fully optimized away
    // for !Drop types and ends up as dead code in the final binary.
    // Branching on needsDrop higher in the call-tree allows it to be
    // removed by earlier optimization passes.
    public open fun drop() {
        if (needsDrop<T>()) {
            // SAFETY: This is the only place where we drop this field.
            ManuallyDrop.drop(this.inner)
        }
    }

    public open fun len(): Int = this.inner.len()

    public open fun isEmpty(): Boolean = this.inner.len() == 0

    // The iterator indeed reports the correct length. The number of "alive"
    // elements (that will still be yielded) is the length of the range `alive`.
    // This range is decremented in length in either `next` or `nextBack`. It is
    // always decremented by 1 in those methods, but only if `Some(_)` is returned.

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.unsize().fmt(f)

    public companion object {
        /**
         * Creates a new iterator over the given `array`.
         */
        public fun <T> new(array: List<T>, n: Int): IntoIter<T> = IntoIterator.IntoIter(array)

        /**
         * Creates an iterator over the elements in a partially-initialized buffer.
         *
         * If you have a fully-initialized array, then use [IntoIterator].
         * But this is useful for returning partial results from unchecked code.
         *
         * # Safety
         *
         * - The `buffer[initialized]` elements must all be initialized.
         * - The range must be canonical, with `initialized.start <= initialized.end`.
         * - The range must be in-bounds for the buffer, with `initialized.end <= N`.
         *   (Like how indexing `[0][100 .. 100]` fails despite the range being empty.)
         *
         * It's sound to have more elements initialized than mentioned, though that
         * will most likely result in them being leaked.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.array.IntoIter
         * import std.mem.MaybeUninit
         *
         * @feature(arrayIntoIterConstructors)
         * @feature(maybeUninitUninitArrayTranspose)
         * public fun doctestEntry() {
         *     fun <T> nextChunk(it: Any, n: Int): Result<List<T>> {
         *         var buffer = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<Result<List<T>>>()) }
         *         var i = 0
         *         while (i < n) {
         *             when (val matchVal = it.next()) {
         *                 null -> {
         *                     // SAFETY: We've initialized the first `i` items
         *                     run {
         *                         return Result.failure(IntoIter.newUnchecked(buffer, 0 until i))
         *                     }
         *                 }
         *                 else -> {
         *                     val x = matchVal
         *                     buffer[i].write(x)
         *                     i += 1
         *                 }
         *             }
         *         }
         *         // SAFETY: We've initialized all N items
         *         return Result.success(buffer.transpose().assumeInit())
         *     }
         *     val r: List<Any> = nextChunk((10 until 16)).unwrap()
         *     check(r == listOf(10, 11, 12, 13))
         *     val r1: IntoIter = nextChunk((10 until 16)).unwrapErr()
         *     check(r1.collect<Vec>() == mutableListOf(10, 11, 12, 13, 14, 15))
         * }
         * ```
         */
        public fun <T> newUnchecked(buffer: List<MaybeUninit<T>>, initialized: IntRange, n: Int): IntoIter<T> {
            // SAFETY: one of our safety conditions is that the range is canonical.
            val alive = IndexRange.newUnchecked(initialized.start, initialized.end)
            // SAFETY: one of our safety condition is that these items are initialized.
            val inner = PolymorphicIter.newUnchecked(alive, buffer, n)
            return IntoIter(inner = inner)
        }

        /**
         * Creates an iterator over `T` which returns no elements.
         *
         * If you just need an empty iterator, then use
         * [`iter.empty`](iter.empty) instead.
         * And if you need an empty array, use `[]`.
         *
         * But this is useful when you need an `array.IntoIter<T, N>` *specifically*.
         *
         * # Examples
         *
         * ```kotlin
         * import std.array.IntoIter
         *
         * @feature(arrayIntoIterConstructors)
         * public fun doctestEntry() {
         *     val empty = IntoIter.empty()
         *     check(empty.len() == 0)
         *     check(empty.asSlice() == listOf())
         *     val empty1 = IntoIter.empty()
         *     check(empty1.len() == 0)
         * }
         * ```
         *
         * `[1, 2].intoIter()` and `[].intoIter()` have different types
         * ```kotlin
         * import std.array.IntoIter
         *
         * @feature(arrayIntoIterConstructors)
         * public fun doctestEntry() {
         *     fun getBytes(b: Boolean): IntoIter<Byte> {
         *         return if (b) {
         *             listOf(1, 2, 3, 4).intoIter()
         *         } else {
         *             listOf().intoIter()
         *             // error[E0308]: mismatched types
         *         }
         *     }
         * }
         * ```
         *
         * But using this method you can get an empty iterator of appropriate size:
         * ```kotlin
         * import std.array.IntoIter
         *
         * @feature(arrayIntoIterConstructors)
         * public fun doctestEntry() {
         *     fun getBytes(b: Boolean): IntoIter<Byte> {
         *         return if (b) {
         *             listOf(1, 2, 3, 4).intoIter()
         *         } else {
         *             IntoIter.empty()
         *         }
         *     }
         *     check(getBytes(true).collect<Vec>() == mutableListOf(1, 2, 3, 4))
         *     check(getBytes(false).collect<Vec>() == mutableListOf())
         * }
         * ```
         */
        public fun <T> empty(n: Int): IntoIter<T> {
            val inner = PolymorphicIter.empty(n)
            return IntoIter(inner = inner)
        }

        public fun <T> default(n: Int): IntoIter<T> = IntoIter.empty(n)
    }
}

// Note: the `@rustcSkipDuringMethodDispatch(array)` on `interface IntoIterator`
// hides this implementation from explicit `.intoIter()` calls on editions < 2021,
// so those calls will still resolve to the slice implementation, by reference.

/**
 * Creates a consuming iterator, that is, one that moves each value out of
 * the array (from start to end).
 *
 * The array cannot be used after calling this unless `T` implements
 * `Copy`, so the whole array is copied.
 *
 * Arrays have special behavior when calling `.intoIter()` prior to the
 * 2021 edition -- see the [array] Editions section for more information.
 *
 * [array]: prim@array
 */
public fun <T> List<T>.intoIterFromTArrayn(n: Int): IntoIter<T> {
    // SAFETY: The reinterpret here is actually safe. The docs of `MaybeUninit`
    // promise:
    //
    // > `MaybeUninit<T>` is guaranteed to have the same size and alignment
    // > as `T`.
    //
    // The docs even show a reinterpret from an array of `MaybeUninit<T>` to
    // an array of `T`.
    //
    // With that, this initialization satisfies the invariants.
    //
    // Note: If normal `reinterpret` ever gets smart enough to allow this
    // directly, use it instead of `transmuteUnchecked`.
    val data: List<MaybeUninit<T>> = transmuteWithLayouts<List<T>, List<MaybeUninit<T>>>(this, UnresolvedTypeLayout<List<T>>(), UnresolvedTypeLayout<List<MaybeUninit<T>>>())
    // SAFETY: The original array was entirely initialized and the alive
    // range we're passing here represents that fact.
    val inner = PolymorphicIter.newUnchecked(IndexRange.zeroTo(n), data, n)
    return IntoIter(inner = inner)
}

public interface NonDrop

// T: Copy as approximation for !Drop since getUnchecked does not advance self.alive
// and thus we can't implement drop-handling

public val MAY_HAVE_SIDE_EFFECT: Boolean = false

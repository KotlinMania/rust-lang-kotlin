// port-lint: source array/iter.rs
package io.github.kotlinmania.rustlang.core.array.iter

import io.github.kotlinmania.rustlang.core.array.iter.iterinner.PolymorphicIter
import io.github.kotlinmania.rustlang.core.intrinsics.transmuteUnchecked
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.indexrange.IndexRange
import io.github.kotlinmania.rustlang.core.result.Result

// Defines the `IntoIter` owned iterator for arrays.

public typealias InnerSized<T> = PolymorphicIter<List<MaybeUninit<T>>>

public typealias InnerUnsized<T> = PolymorphicIter<List<MaybeUninit<T>>>

/**
 * A by-value [array] iterator.
 */
public open class IntoIter<T> internal constructor(
    innerCtorArg: InnerSized<T, N>,
) {
    public var inner: InnerSized<T, N> = innerCtorArg
        internal set

    public open fun clone(): IntoIter<T> = IntoIter(inner)

    public companion object {
        public operator fun <T> invoke(
            inner: InnerSized<T, N>,
        ): IntoIter<T> = IntoIter<T>(
            innerCtorArg = inner,
        )
    }
}

public fun <T> IntoIter<T, N>.unsize(n: Int): InnerUnsized<T> = this.inner.deref()

public fun <T> IntoIter<T, N>.unsizeMut(n: Int): InnerUnsized<T> = this.inner.derefMut()

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
    val data: List<MaybeUninit<T>> = transmuteUnchecked(this)
    // SAFETY: The original array was entirely initialized and the alive
    // range we're passing here represents that fact.
    val inner = InnerSized.newUnchecked(IndexRange.zeroTo(n), data)
    return IntoIter(inner = inner)
}

/**
 * Creates a new iterator over the given `array`.
 */
public fun <T> IntoIter.Companion.new(array: List<T>, n: Int): IntoIter<T, N> = IntoIterator.IntoIter(array)

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
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import std.array.IntoIter
 * import std.mem.MaybeUninit
 *
 * @feature(arrayIntoIterConstructors)
 * @feature(maybeUninitUninitArrayTranspose)
 * public fun doctestEntry() {
 *     fun <T> nextChunk(it: Any, n: Int): Result<List<T>> {
 *         var buffer = MutableList(n) { MaybeUninit.uninit(ObjectSlotLayout<Result<List<T>>>()) }
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
public fun <T> IntoIter.Companion.newUnchecked(buffer: List<MaybeUninit<T>>, initialized: IntRange, n: Int): IntoIter<T, N> {
    // SAFETY: one of our safety conditions is that the range is canonical.
    val alive = IndexRange.newUnchecked(initialized.start, initialized.end)
    // SAFETY: one of our safety condition is that these items are initialized.
    val inner = InnerSized.newUnchecked(alive, buffer)
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
public fun <T> IntoIter.Companion.empty(n: Int): IntoIter<T, N> {
    val inner = InnerSized.empty()
    return IntoIter(inner = inner)
}

/**
 * Returns an immutable slice of all elements that have not been yielded
 * yet.
 */
public fun <T> IntoIter<T, N>.asSlice(n: Int): List<T> = this.unsize().asSlice()

/**
 * Returns a mutable slice of all elements that have not been yielded yet.
 */
public fun <T> IntoIter<T, N>.asMutSlice(n: Int): MutableList<T> = this.unsizeMut().asMutSlice()

public fun <T> defaultIntoIterTN(n: Int): IntoIter<T, N> = IntoIter.empty()

public fun <T> IntoIter<T, N>.next(n: Int): T? = this.unsizeMut().next()

public fun <T> IntoIter<T, N>.sizeHint(n: Int): Pair<Int, Int?> = this.unsize().sizeHint()

public fun <T, Acc> IntoIter<T, N>.fold(init: Acc, fold: (Acc, T) -> Acc, n: Int): Acc = this.unsizeMut().fold(init, fold)

public fun <T, B, R> IntoIter<T, N>.tryFold(init: B, f: (B, T) -> R, n: Int): R where R : Try<R, B, Any?> = this.unsizeMut().tryFold(init, f)

public fun <T> IntoIter<T, N>.count(n: Int): Int = this.len()

public fun <T> IntoIter<T, N>.last(n: Int): T? = this.nextBack()

public fun <T> IntoIter<T, N>.advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.unsizeMut().advanceBy(n)

public fun <T> IntoIter<T, N>.iteratorGetUnchecked(idx: Int, n: Int): T {
    // SAFETY: The caller must provide an idx that is in bound of the remainder.
    val elemRef = this.asMutSlice().getUncheckedMut(idx)
    // SAFETY: We only implement `TrustedRandomAccessNoCoerce` for types
    // which are actually `Copy`, so cannot have multiple-drop issues.
    return read(elemRef)
}

public fun <T> IntoIter<T, N>.nextBack(n: Int): Item? = this.unsizeMut().nextBack()

public fun <T, Acc> IntoIter<T, N>.rfold(init: Acc, rfold: (Acc, Item) -> Acc, n: Int): Acc = this.unsizeMut().rfold(init, rfold)

public fun <T, B, R> IntoIter<T, N>.tryRfold(init: B, f: (B, Item) -> R, n: Int): R where R : Try<R, B, Any?> = this.unsizeMut().tryRfold(init, f)

public fun <T> IntoIter<T, N>.advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.unsizeMut().advanceBackBy(n)

// Even though all the Drop logic could be completely handled by
// PolymorphicIter, this impl still serves two purposes:
// - Drop has been part of the public API, so we can't remove it
// - the partialDrop function doesn't always get fully optimized away
// for !Drop types and ends up as dead code in the final binary.
// Branching on needsDrop higher in the call-tree allows it to be
// removed by earlier optimization passes.
public fun <T> IntoIter<T, N>.drop(n: Int) {
    if (needsDrop<T>()) {
        // SAFETY: This is the only place where we drop this field.
        ManuallyDrop.drop(this.inner)
    }
}

public fun <T> IntoIter<T, N>.len(n: Int): Int = this.inner.len()

public fun <T> IntoIter<T, N>.isEmpty(n: Int): Boolean = this.inner.len() == 0

// The iterator indeed reports the correct length. The number of "alive"
// elements (that will still be yielded) is the length of the range `alive`.
// This range is decremented in length in either `next` or `nextBack`. It is
// always decremented by 1 in those methods, but only if `Some(_)` is returned.

public interface NonDrop

// T: Copy as approximation for !Drop since getUnchecked does not advance self.alive
// and thus we can't implement drop-handling

public val MAY_HAVE_SIDE_EFFECT: Boolean = false

public fun <T> IntoIter<T, N>.fmt(f: StringBuilder, n: Int): Result<Unit, Unit> = this.unsize().fmt(f)

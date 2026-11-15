package io.github.kotlinmania.rustlang.core.slice

import io.github.kotlinmania.rustlang.core.result.Result

public sealed class Direction {
    public data object Front : Direction()
    public data object Back : Direction()
}

public interface CloneFromSpec<T> {
    public fun specCloneFrom(src: List<T>)
}

/**
 * Patterns in slices - currently, only used by `stripPrefix` and `stripSuffix`.  At a future
 * point, we hope to generalise `core.String.Pattern` (which at the time of writing is limited to
 * `String`) to slices, and then this trait will be replaced or abolished.
 */
public interface SlicePattern<Item> {
    /**
     * Currently, the consumers of `SlicePattern` need a slice.
     */
    public fun asSlice(): List<Item>
}

/**
 * The error type returned by [`getDisjointMut`][slice.getDisjointMut].
 *
 * It indicates one of two possible errors:
 * - An index is out-of-bounds.
 * - The same index appeared multiple times in the array
 *   (or different but overlapping indices when ranges are provided).
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(1, 2, 3)
 * check(v.getDisjointMut(listOf(0, 999)) == Result.failure(GetDisjointMutError.IndexOutOfBounds))
 * check(v.getDisjointMut(listOf(1, 1)) == Result.failure(GetDisjointMutError.OverlappingIndices))
 * ```
 */
public sealed class GetDisjointMutError {
    /**
     * An index provided was out-of-bounds for the slice.
     */
    public data object IndexOutOfBounds : GetDisjointMutError()

    /**
     * Two indices provided were overlapping.
     */
    public data object OverlappingIndices : GetDisjointMutError()

    public fun rustEquals(other: GetDisjointMutError): Boolean = this == other

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val msg =
            when (this) {
                GetDisjointMutError.IndexOutOfBounds -> "an index is out of bounds"
                GetDisjointMutError.OverlappingIndices -> "there were overlapping indices"
            }
        return run {
            f.append((msg).toString())
            Result.Ok(Unit)
        }
    }
}

/**
 * A helper trait for `<[T]>.getDisjointMut()`.
 *
 * # Safety
 *
 * If `isInBounds()` returns `true` and `isOverlapping()` returns `false`,
 * it must be safe to index the slice with the indices.
 */
public interface GetDisjointMutIndex<T> {
    /**
     * Returns `true` if `self` is in bounds for `len` slice elements.
     */
    public fun isInBounds(len: Int): Boolean

    /**
     * Returns `true` if `self` overlaps with `other`.
     *
     * Note that we don't consider zero-length ranges to overlap at the beginning or the end,
     * but do consider them to overlap in the middle.
     */
    public fun isOverlapping(other: T): Boolean
}

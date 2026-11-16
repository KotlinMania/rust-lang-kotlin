package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result

// This is only used as a fallback when building as part of `std`.

/**
 * Key equivalence trait.
 *
 * This trait defines the function used to compare the input value with the
 * map keys (or set values) during a lookup operation such as [HashMap.get]
 * or [HashSet.contains].
 * It is provided with a blanket implementation based on the
 * [`Borrow`](core.borrow.Borrow) trait.
 *
 * # Correctness
 *
 * Equivalent values must hash to the same value.
 */
public interface Equivalent<K> {
    /**
     * Checks if this value is equivalent to the given key.
     *
     * Returns `true` if both values are equivalent, and `false` otherwise.
     *
     * # Correctness
     *
     * When this function returns `true`, both `self` and `key` must hash to
     * the same value.
     */
    public fun equivalent(key: K): Boolean
}

/**
 * The error type for `tryReserve` methods.
 */
public sealed class TryReserveError {
    /**
     * Error due to the computed capacity exceeding the collection's maximum
     * (usually `Long.MAX` bytes).
     */
    public data object CapacityOverflow : TryReserveError()
    /**
     * The memory allocator returned an error
     */
    public data class AllocError(public var layout: Layout) : TryReserveError()

    public fun rustEquals(other: TryReserveError): Boolean = this == other

    // matches stdalloc.collections.TryReserveError
    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        f.append("memory allocation failed")
        val reason =
            when (this) {
                TryReserveError.CapacityOverflow -> {
                    " because the computed capacity exceeded the collection's maximum"
                }
                is TryReserveError.AllocError -> " because the memory allocator returned an error"
            }
        return run { f.append(reason); Result.Ok(Unit) }
    }
}

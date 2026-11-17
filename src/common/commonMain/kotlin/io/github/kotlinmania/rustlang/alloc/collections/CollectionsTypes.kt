package io.github.kotlinmania.rustlang.alloc.collections

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.alloc.layout.LayoutError

/**
 * Details of the allocation that caused a `TryReserveError`
 */
public sealed class TryReserveErrorKind {
    /**
     * Error due to the computed capacity exceeding the collection's maximum
     * (usually `Long.MAX` bytes).
     */
    public data object CapacityOverflow : TryReserveErrorKind()
    /**
     * The memory allocator returned an error
     */
    public data class AllocError(public var layout: Layout, public var nonExhaustive: Unit) : TryReserveErrorKind()

    public fun rustEquals(other: TryReserveErrorKind): Boolean = this == other

    public open fun clone(): TryReserveErrorKind =
        when (this) {
            TryReserveErrorKind.CapacityOverflow -> TryReserveErrorKind.CapacityOverflow
            is TryReserveErrorKind.AllocError -> {
                val layout = this.layout
                TryReserveErrorKind.AllocError(layout = layout, nonExhaustive = Unit)
            }
        }

    public companion object {
        /**
         * Always evaluates to [TryReserveErrorKind.CapacityOverflow].
         */
        public fun from(arg0: LayoutError): TryReserveErrorKind = TryReserveErrorKind.CapacityOverflow
    }
}

/**
 * An intermediate trait for specialization of `Extend`.
 */
public interface SpecExtend<I, Item, IntoIter> {
    /**
     * Extends `self` with the contents of the given iterator.
     */
    public fun specExtend(iter: I)
}

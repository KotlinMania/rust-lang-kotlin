// port-lint: source sys/helpers/wstr.rs
package io.github.kotlinmania.rustlang.std.sys.helpers.wstr

import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull

// This module contains constructs to work with 16-bit characters (UCS-2 or UTF-16)

/**
 * A safe iterator over a LPWSTR
 * (aka a pointer to a series of UTF-16 code units terminated by a NULL).
 */
public open class WStrUnits internal constructor(
    lpwstrCtorArg: NonNull,
    // ...and the memory it points to must be valid for this scope.
    public var lifetime: Unit,
) : Iterable<NonZero<UShort, NonZeroU16Inner>> {
    // The pointer must never be null...
    public var lpwstr: NonNull = lpwstrCtorArg
        internal set

    override fun iterator(): Iterator<NonZero<UShort, NonZeroU16Inner>> = object : AbstractIterator<NonZero<UShort, NonZeroU16Inner>>() {
        override fun computeNext() {
            when (val v = this@WStrUnits.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun peek(): NonZero<UShort, NonZeroU16Inner>? {
        // SAFETY: It's always safe to read the current item because we don't
        // ever move out of the array's bounds.
        return NonZero.new<NonZeroU16Inner, UShort>(this.lpwstr.asPtr())
    }

    /**
     * Advance the iterator while `predicate` returns true.
     * Returns the number of items it advanced by.
     */
    public open fun advanceWhile(predicate: (NonZero<UShort, NonZeroU16Inner>) -> Boolean): Int {
        var counter = 0
        while (true) {
            val matchVal = this.peek()
            if (matchVal == null) break
            val w = matchVal
            if (!predicate(w)) {
                break
            }
            counter += 1
            this.next()
        }
        return counter
    }

    public open fun next(): NonZero<UShort, NonZeroU16Inner>? {
        // SAFETY: If NULL is reached we immediately return.
        // Therefore it's safe to advance the pointer after that.
        return run {
            val next = (this.peek() ?: return null)
            this.lpwstr = NonNull.newUnchecked(this.lpwstr.asPtr().add(1))
            next
        }
    }

    public companion object {
        public operator fun invoke(
            lpwstr: NonNull,
            lifetime: Unit,
        ): WStrUnits = WStrUnits(
            lpwstrCtorArg = lpwstr,
            lifetime = lifetime,
        )

        /**
         * Creates the iterator. Returns `None` if `lpwstr` is null.
         *
         * SAFETY: `lpwstr` must point to a null-terminated wide string that lives
         * at least as long as the scope of this struct.
         */
        public fun new(lpwstr: ConstPtr): WStrUnits? {
            return WStrUnits(lpwstr = (NonNull.new(lpwstr) ?: return null), lifetime = Unit)
        }
    }
}

// port-lint: source collections/btree/borrow.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.borrow

import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * Models a reborrow of some unique reference, when you know that the reborrow
 * and all its descendants (i.e., all pointers and references derived from it)
 * will not be used any more at some point, after which you want to use the
 * original unique reference again.
 *
 * The borrow checker usually handles this stacking of borrows for you, but
 * some control flows that accomplish this stacking are too complicated for
 * the compiler to follow. A `DormantMutRef` allows you to check borrowing
 * yourself, while still expressing its stacked nature, and encapsulating
 * the raw pointer code needed to do this without undefined behavior.
 */
public open class DormantMutRef<T>(
    public var ptr: NonNull,
    public var marker: Unit,
) {
    /**
     * Revert to the unique borrow initially captured.
     *
     * # Safety
     *
     * The reborrow must have ended, i.e., the reference returned by `new` and
     * all pointers and references derived from it, must not be used anymore.
     */
    public open fun awaken(): T {
        // SAFETY: our own safety conditions imply this reference is again unique.
        return this.ptr.asPtr()
    }

    /**
     * Borrows a new mutable reference from the unique borrow initially captured.
     *
     * # Safety
     *
     * The reborrow must have ended, i.e., the reference returned by `new` and
     * all pointers and references derived from it, must not be used anymore.
     */
    public open fun reborrow(): T {
        // SAFETY: our own safety conditions imply this reference is again unique.
        return this.ptr.asPtr()
    }

    /**
     * Borrows a new shared reference from the unique borrow initially captured.
     *
     * # Safety
     *
     * The reborrow must have ended, i.e., the reference returned by `new` and
     * all pointers and references derived from it, must not be used anymore.
     */
    public open fun reborrowShared(): T {
        // SAFETY: our own safety conditions imply this reference is again unique.
        return this.ptr.asPtr()
    }

    public companion object {
        /**
         * Capture a unique borrow, and immediately reborrow it. For the compiler,
         * the scope of the new reference is the same as the scope of the
         * original reference, but you promise to use it for a shorter period.
         */
        public fun <T> new(t: T): Pair<T, DormantMutRef<T>> {
            val ptr = NonNull.from(t)
            // SAFETY: we hold the borrow throughout a via `_marker`, and we expose
            // only this reference, so it is unique.
            val newRef = ptr.asPtr()
            return Pair(newRef, DormantMutRef(ptr = ptr, marker = Unit))
        }
    }
}

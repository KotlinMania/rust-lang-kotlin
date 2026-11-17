// port-lint: source array/drain.rs
package io.github.kotlinmania.rustlang.core.array.drain

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.conjureZst
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ops.function.FnMut
import io.github.kotlinmania.rustlang.core.ops.function.FnOnce
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut

/**
 * See [Drain.new]; this is our fake iterator.
 */
public open class Drain<T, F>(
    // Note(const-Note): This is a slice.IterMut<l>, replace when possible.
    /**
     * The pointer to the next element to return, or the past-the-end location
     * if the drainer is empty.
     *
     * This address will be used for all ZST elements, never changed.
     * As we "own" this array, we dont need to store any scope.
     */
    public var ptr: NonNull,
    /**
     * For non-ZSTs, the non-null pointer to the past-the-end element.
     * For ZSTs, this is the number of unprocessed items.
     */
    public var endOrLen: MutPtr,
    public var f: F,
    public var l: Unit,
) : FnOnce<Int<Drain<T, F>>, U>, FnMut<Int<Drain<T, F>>, Any> {
    /**
     * This implementation is useless.
     */
    public override fun callOnce(args: Int): U = this.callMut(args)

    // Note(const-Note): ideally this would be an unchecked fn `next()`, and to use it you would instead `| _ | unchecked ( drain.next() )`.
    public override fun callMut(tupleArg0: Int, typeLayoutT: TypeLayout<T>): U =
        if (((typeLayoutT).sizeBytes == 0L)) {
            val len = transmuteWithLayouts(this.endOrLen, PrimitiveLayouts.mutPtrLayout(64L), PrimitiveLayouts.USIZE64)
            // SAFETY:
            // The caller guarantees that this is never called more than N times
            // (see `Drain.new`), hence this cannot underflow.
            this.endOrLen = withoutProvenanceMut(len.uncheckedSub(1))
            // its UB to call this more than N times, so returning more ZSTs is valid.
            // SAFETY: its a ZST? we conjur.
            (this.f)(conjureZst<T>())
        } else {
            // increment before moving; if `f` panics, we drop the rest.
            val p = this.ptr
            // SAFETY: caller guarantees never called more than N times (see `Drain.new`)
            this.ptr = this.ptr.add(1)
            // SAFETY: we are allowed to move this.
            (this.f)(p.read())
        }

    public open fun drop(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        val slice =
            if (((typeLayoutT).sizeBytes == 0L)) {
                linearMutSliceView(this.ptr.asPtr(), (transmuteWithLayouts(this.endOrLen, PrimitiveLayouts.mutPtrLayout(64L), PrimitiveLayouts.USIZE64)).toInt(), PrimitiveLayouts.U8)
            } else {
                // SAFETY: we cant read more than N elements
                linearMutSliceView(this.ptr.asPtr(), this.endOrLen.offsetFromUnsigned((this.ptr.asPtr()).asConstPtr()).toInt(), PrimitiveLayouts.U8)
            }
        // SAFETY: By the type invariant, we're allowed to drop all these. (we own it, after all)
        dropInPlace(slice)
    }

    public companion object {
        /**
         * This function returns a function that lets you index the given array in const.
         * As implemented it can optimize better than iterators, and can be constified.
         * It acts like a sort of guard (owns the array) and iterator combined, which can be implemented
         * as it is a struct that implements const fn.
         * The only method you're really allowed to call is `next()`,
         * anything else is more or less UB, hence this function being unchecked.
         * Moved elements will not be dropped.
         * This will also not actually store the array.
         *
         * SAFETY: must only be called `N` times. Thou shalt not drop the array either.
         */
        public fun <T, U> new(array: List<T>, f: (T) -> U, n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Drain<T, F> {
            // dont drop the array, transfers "ownership" to Self
            val ptr: NonNull = NonNull.fromMut(array).cast()
            // SAFETY:
            // Adding `slice.len()` to the starting pointer gives a pointer
            // at the end of `slice`. `end` will never be dereferenced, only checked
            // for direct pointer equality with `ptr` to check if the drainer is done.
            return run {
                val endOrLen =
                    if (((typeLayoutT).sizeBytes == 0L)) {
                        withoutProvenanceMut(n)
                    } else {
                        ptr.asPtr().add(n)
                    }
                Drain(ptr = ptr, endOrLen = endOrLen, f = f, l = Unit)
            }
        }
    }
}

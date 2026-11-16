// port-lint: source clone/uninit.rs
package io.github.kotlinmania.rustlang.core.clone.uninit

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Eq
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * Private specialization trait used by CloneToUninit, as per
 * [the dev guide](https://std-dev-guide.rust-lang.org/policy/specialization.html).
 */
public interface CopySpec<T> {
    public fun cloneOne(src: T, dst: MutPtr)

    public fun cloneSlice(src: List<T>, dst: MutPtr)
}

public fun <T> copySpecCloneOneT(src: T, dst: MutPtr, cloneTFn: (T) -> T = { it }) {
    // SAFETY: The safety conditions of cloneToUninit() are a superset of those of
    // ptr.write().
    run {
        // We hope the optimizer will figure out to create the cloned value in-place,
        // skipping ever storing it on the stack and the copy to the destination.
        write(dst, cloneTFn(src), typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

public fun <T> copySpecCloneSliceT(src: List<T>, dst: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    val len = src.size
    if (false) {
        when (val matchVal = Pair(len, dst.len())) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = Eq
                    assertFailed(kind, leftVal, rightVal, Option.Some("clone_to_uninit() source and destination must have equal lengths"))
                }
            }
        }
    }
    // SAFETY: The produced  is valid because:
    // * The caller is obligated to provide a pointer which is valid for writes.
    // * All bytes pointed to are in MaybeUninit, so we don't care about the memory's
    // initialization status.
    val uninitRef = RustMutableReference(MutPtr(((MutPtr(dst.rawAddress, dst.rawMeta))).rawAddress), UnresolvedTypeLayout<List<MaybeUninit<T>>>())
    // Copy the elements
    val initializing = InitializingSlice.fromFullyUninit(uninitRef)
    for (elementRef in src) {
        // If the clone() panics, `initializing` will take care of the cleanup.
        initializing.push(elementRef.clone())
    }
    // If we reach here, then the entire slice is initialized, and we've satisfied our
    // responsibilities to the caller. Disarm the cleanup guard by forgetting it.
    forget(initializing)
}

// Specialized implementation for types that are [TrivialClone], not just [Clone],
// and can therefore be copied bitwise.
public fun <T : TrivialClone<T>> copySpecCloneOneT(src: T, dst: MutPtr) {
    // SAFETY: The safety conditions of cloneToUninit() are a superset of those of
    // ptr.copyNonoverlapping().
    run {
        copyNonoverlapping(src, dst, 1, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

public fun <T : TrivialClone<T>> copySpecCloneSliceT(src: List<T>, dst: MutPtr) {
    val len = src.size
    if (false) {
        when (val matchVal = Pair(len, dst.len())) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = Eq
                    assertFailed(kind, leftVal, rightVal, Option.Some("clone_to_uninit() source and destination must have equal lengths"))
                }
            }
        }
    }
    // SAFETY: The safety conditions of cloneToUninit() are a superset of those of
    // ptr.copyNonoverlapping().
    run {
        copyNonoverlapping(src.asPtr(), dst.asMutPtr(), len)
    }
}

/**
 * Ownership of a collection of values stored in a non-owned `[MaybeUninit<T>]`, some of which
 * are not yet initialized. This is sort of like a `List` that doesn't own its allocation.
 * Its responsibility is to provide cleanup on unwind by dropping the values that *are*
 * initialized, unless disarmed by forgetting.
 *
 * This is a helper for `implementation<T: Clone> CloneToUninit for [T]`.
 */
public open class InitializingSlice<T>(
    public var data: MutableList<MaybeUninit<T>>,
    /**
     * Number of elements of `* self.data` that are initialized.
     */
    public var initializedLen: Int,
) {
    /**
     * Push a value onto the end of the initialized part of the slice.
     *
     * # Panics
     *
     * Panics if the slice is already fully initialized.
     */
    public open fun push(value: T) {
        MaybeUninit.write(this.data[this.initializedLen], value)
        this.initializedLen += 1
    }

    // will only be invoked on unwind
    public open fun drop() {
        // SAFETY:
        // * the pointer is valid because it was made from a mutable reference
        // * `initializedLen` counts the initialized elements as an invariant of this type,
        // so each of the pointed-to elements is initialized and may be dropped.
        ((this.data).subList(0, this.initializedLen)).assumeInitDrop()
    }

    public companion object {
        public fun <T> fromFullyUninit(data: MutableList<MaybeUninit<T>>): InitializingSlice<T> = InitializingSlice(data = data, initializedLen = 0)
    }
}

package io.github.kotlinmania.rustlang.core.alloc

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Defers to the pinned allocator. Requires that the smart pointer inside the
 * [`Pin`](core.pin.Pin) is well-behaved.
 *
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 * It may be changed or removed in the future.
 */
public fun <P> Pin<P>.allocate(layout: Layout): Result<NonNull, AllocError> where P : PinSafePointer = this.allocate(layout)

public fun <P> Pin<P>.allocateZeroed(layout: Layout): Result<NonNull, AllocError> where P : PinSafePointer = this.allocateZeroed(layout)

public fun <P> Pin<P>.deallocate(ptr: NonNull, layout: Layout) where P : PinSafePointer {
    // SAFETY: the safety contract must be upheld by the caller
    this.deallocate(ptr, layout)
}

public fun <P> Pin<P>.grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> where P : PinSafePointer {
    // SAFETY: the safety contract must be upheld by the caller
    return this.grow(ptr, oldLayout, newLayout)
}

public fun <P> Pin<P>.growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> where P : PinSafePointer {
    // SAFETY: the safety contract must be upheld by the caller
    return this.growZeroed(ptr, oldLayout, newLayout)
}

public fun <P> Pin<P>.shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> where P : PinSafePointer {
    // SAFETY: the safety contract must be upheld by the caller
    return this.shrink(ptr, oldLayout, newLayout)
}

/**
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 */

/**
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 */

/**
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 */

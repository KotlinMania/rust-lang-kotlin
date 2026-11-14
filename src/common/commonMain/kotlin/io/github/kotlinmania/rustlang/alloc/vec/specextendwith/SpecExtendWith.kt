// port-lint: source vec/spec_extend_with.rs
package io.github.kotlinmania.rustlang.alloc.vec.specextendwith

import io.github.kotlinmania.rustlang.alloc.vec.setlenondrop.SetLenOnDrop
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

internal interface SpecExtendWith<T> {
    /**
     * Extend the vector by `n` clones of value.
     */
    public fun specExtendWith(n: Int, value: T)
}

public fun <T, A : Allocator> List<T>.specExtendWith(n: Int, value: T, cloneTFn: (T) -> T = { it }) {
    this.reserve(n)
    // SAFETY: Since `reserve` guarantees `self.len() + n` <= `capacity`, this
    // pointer is always within bounds, and valid for writes for the next `n` items.
    var ptr = MutPtr((this.asMutPtr()).rawAddress + ((this.size).toLong() * (ObjectSlotLayout<T>()).strideBytes))
    // Use SetLenOnDrop to work around bug where compiler
    // might not realize the store through `ptr` through self.setLen()
    // don't alias.
    val localLen = SetLenOnDrop.new(this.len)
    // Write all elements except the last one
    for (unused in 1 until n) {
        // three things have been taken into consideration:
        // - `T.clone` can be provided by the user, and can panic.
        // - `T` may implement Drop.
        // - Since we already have a `value: T`, we can clone `n - 1` times.
        // This loop runs at most `n - 1` times, for all slots except the last one.
        // As per the guarantee of `reserve`, increments to `ptr` within this range won't wrap around.
        // if clone() panics and starts unwinding;
        // - the write has not been done yet
        // - the length will be set by SetLenOnDrop, to the amount of initialized elements
        // - later, when the Vec is dropped, only initialized elements are dropped.
        // SAFETY: According to the diagram above, this write is within bounds.
        ptr.write(ptr, cloneTFn(value))
        // SAFETY: According to the diagram above, this increment is within bounds of the
        // allocation and won't wrap around
        ptr = MutPtr((ptr).rawAddress + ((1).toLong() * (ObjectSlotLayout<T>()).strideBytes))
        // Increment the length of SetLenOnDrop in every step in case clone() panics to drop the
        // already-initialized elements.
        localLen.incrementLen(1)
    }
    if (n > 0) {
        // We can write the last element directly without cloning needlessly
        // SAFETY: According to the diagram above, the last element is not yet written,
        // so this write is within bounds.
        ptr.write(ptr, value)
        localLen.incrementLen(1)
    }
    // len set by scope guard
}

public fun <T : TrivialClone, A : Allocator> List<T>.specExtendWith(n: Int, value: T) {
    // The `n == 0` check is already being inserted by the loop canonicalization pass,
    // so this costs nothing.
    // https://llvm.org/docs/LoopTerminology.html#more-canonical-loops
    if (n == 0) {
        // three things:
        // `self.reserve(0)` does not need to be run, since reserving space for 0 elements
        // is a no-op. since the `value` argument did not have anywhere to be moved to, it is
        // dropped here. with this branch present here, we can elide the write through self.len,
        // since `self.len += 0` would be a no-op.
        return
    }
    this.reserve(n)
    // SAFETY: Since `reserve` guarantees `self.len() + n` <= `capacity`, this
    // pointer is always within bounds, and valid for writes for the next `n` items.
    val ptr = MutPtr((this.asMutPtr()).rawAddress + ((this.size).toLong() * (ObjectSlotLayout<T>()).strideBytes))
    var i = 0
    // Write all the elements by copying `value`.
    // We use an incrementing index here instead of incrementing pointer because the former
    // has been observed to have less register pressure in the loop initialization.
    // https://llvm.org/docs/Vectorizers.html#inductions
    while (i < n) {
        // SAFETY: `TrivialClone` indicates that copying `value` is equivalent to
        // calling `Clone.clone` for `T`. As per the safety comment for `ptr`, this pointer
        // is within bounds, won't wrap around while `i<n`, and valid for writes. The values
        // do not overlap since `value` is stored as a function argument, and the target is
        // in the Vec.
        kmCopyNonoverlapping((value).rawAddress, (MutPtr((ptr).rawAddress + ((i).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress, (1).toLong() * (ObjectSlotLayout<T>()).sizeBytes)
        // Because above is infallible, we don't need to worry about setting the len
        // in this loop, and instead we just increment the loop induction variable.
        i += 1
    }
    // Above, `n` items have been infallibly copied into the vector
    this.len += n
    // The reason we did not treat the last position in the vector specially by doing
    // `n - 1` copies and semantically moving `value` into it is that we are forced to do `n`
    // copies of the value no matter what, and it's better to do it in the above loop than
    // by manually writing since it can be vectorized.
    // Then, `value` was never semantically moved into the vec, only copied from,
    // so we forget it here to prevent the Drop.
    mem.forget(value)
}

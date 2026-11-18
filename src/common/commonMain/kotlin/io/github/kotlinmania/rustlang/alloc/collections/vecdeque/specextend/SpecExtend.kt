// port-lint: source collections/vec_deque/spec_extend.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.specextend

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.drain.Drain
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.iter.adapters.copied.Copied
import io.github.kotlinmania.rustlang.core.iter.adapters.rev.Rev
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

// Specialization trait used for VecDeque.extend
public interface SpecExtend<T, I> {
    public fun specExtend(iter: I)
}

public fun <T, I, A : Allocator> VecDeque<T, A>.specExtend(iter: I) where I : Iterator<T> {
    // This function should be the moral equivalent of:
    //
    // for item in iter (
    // self.pushBack(item);
    // )
    while (true) {
        val matchVal = iter.next()
        if (matchVal == null) break
        val element = matchVal
        val (lower, _) = iter.sizeHint()
        this.reserve(lower.saturatingAdd(1))
        // SAFETY: We just reserved space for at least one element.
        this.pushUnchecked(element)
        // Inner loop to avoid repeatedly calling `reserve`.
        while (this.len < this.capacity()) {
            val element1 = iter.next() ?: return
            // SAFETY: The loop condition guarantees that `self.len()<self.capacity()`.
            this.pushUnchecked(element1)
        }
    }
}

public fun <T, I, A : Allocator> VecDeque<T, A>.specExtendTI(iter: I) where I : TrustedLen<T> {
    // This is the case for a TrustedLen iterator.
    val (low, high) = iter.sizeHint()
    if (high != null) {
        val additional = high
        check(low == additional)
        this.reserve(additional)
        // ignore-tidy-undocumented-unchecked
        val written = this.writeIterWrapping(this.toWrappedIndex(this.len), iter, additional)
        check(additional == written)
    } else {
        // Per TrustedLen contract a `None` upper bound means that the iterator length
        // truly exceeds usize.Max, which would eventually lead to a capacity overflow anyway.
        // Since the other branch already panics eagerly (via `reserve()`) we do the same here.
        // This avoids additional codegen for a fallback code path which would eventually
        // panic anyway.
        error("capacity overflow")
    }
}

public fun <T, A1 : Allocator, A2 : Allocator> VecDeque<T, A1>.specExtend(iterator: ArrayDeque<T>) {
    val slice = iterator.toList()
    this.reserve(slice.len())
    // ignore-tidy-undocumented-unchecked
    run {
        this.copySlice(this.toWrappedIndex(this.len), slice)
    }
    this.len += slice.len()
    iterator.forgetRemainingElementsAndDealloc()
}

public fun <T, A : Allocator> VecDeque<T, A>.specExtendTIterT(iterator: ArrayDeque<T>) {
    val slice = iterator.toList()
    this.reserve(slice.len())
    // ignore-tidy-undocumented-unchecked
    run {
        this.copySlice(this.toWrappedIndex(this.len), slice)
    }
    this.len += slice.len()
}

// Specialization trait used for VecDeque.extendFront
public interface SpecExtendFront<T, I> {
    public fun specExtendFront(iter: I)
}

public fun <T, I, A : Allocator> VecDeque<T, A>.specExtendFront(iter: I) where I : Iterator<T> {
    // This function should be the moral equivalent of:
    //
    // for item in iter (
    // self.pushFront(item);
    // )
    while (true) {
        val matchVal = iter.next()
        if (matchVal == null) break
        val element = matchVal
        val (lower, _) = iter.sizeHint()
        this.reserve(lower.saturatingAdd(1))
        // SAFETY: We just reserved space for at least one element.
        this.pushFrontUnchecked(element)
        // Inner loop to avoid repeatedly calling `reserve`.
        while (this.len < this.capacity()) {
            val element1 = iter.next() ?: return
            // SAFETY: The loop condition guarantees that `self.len()<self.capacity()`.
            this.pushFrontUnchecked(element1)
        }
    }
}

public fun <T, A1 : Allocator, A2 : Allocator> VecDeque<T, A1>.specExtendFront(iterator: ArrayDeque<T>) {
    val slice = iterator.toList()
    this.reserve(slice.len())
    // SAFETY: `slice.len()` space was just reserved and elements in the slice are forgotten after this call
    prependReversed(this, slice)
    iterator.forgetRemainingElementsAndDealloc()
}

public fun <T, A1 : Allocator, A2 : Allocator> VecDeque<T, A1>.specExtendFront(iterator: Rev<ArrayDeque<T>>) {
    val iterator1 = iterator.intoInner()
    val slice = iterator1.asSlice()
    this.reserve(slice.len())
    // SAFETY: `slice.len()` space was just reserved and elements in the slice are forgotten after this call
    prepend(this, slice)
    iterator1.forgetRemainingElementsAndDealloc()
}

public fun <T, A : Allocator> VecDeque<T, A>.specExtendFront(iter: Copied<ArrayDeque<T>>) {
    val slice = iter.intoInner().asSlice()
    this.reserve(slice.len())
    // SAFETY: `slice.len()` space was just reserved and T is Copy because Copied<slice.Iter<a, T>> is Iterator
    prependReversed(this, slice)
}

public fun <T, A : Allocator> VecDeque<T, A>.specExtendFrontTRevCopiedIterT(iter: Rev<Copied<ArrayDeque<T>>>) {
    val slice = iter.intoInner().intoInner().asSlice()
    this.reserve(slice.len())
    // SAFETY: `slice.len()` space was just reserved and T is Copy because Rev<Copied<slice.Iter<a, T>>> is Iterator
    prepend(this, slice)
}

public fun <T, A1 : Allocator, A2 : Allocator> VecDeque<T, A1>.specExtendFront(iter: Drain<T, A2>) {
    if (iter.remaining == 0) {
        return
    }
    this.reserve(iter.remaining)
    // SAFETY: iter.remaining != 0.
    val (left, right) = iter.asSlices()
    // SAFETY:
    // - `iter.remaining` space was reserved, `iter.remaining == left.len() + right.len()`.
    // - The elements in `left` and `right` are forgotten after these calls.
    run {
        prependReversed(this, left)
        prependReversed(this, right)
    }
    iter.idx += iter.remaining
    iter.remaining = 0
}

public fun <T, A1 : Allocator, A2 : Allocator> VecDeque<T, A1>.specExtendFrontTRevDrainTA2(iter: Rev<Drain<T, A2>>) {
    val iter1 = iter.intoInner()
    if (iter1.remaining == 0) {
        return
    }
    this.reserve(iter1.remaining)
    // SAFETY: iter.remaining != 0.
    val (left, right) = iter1.asSlices()
    // SAFETY:
    // - `iter.remaining` space was reserved, `iter.remaining == left.len() + right.len()`.
    // - The elements in `left` and `right` are forgotten after these calls.
    run {
        prepend(this, right)
        prepend(this, left)
    }
    iter1.idx += iter1.remaining
    iter1.remaining = 0
}

/**
 * Prepends elements of `slice` to `deque` using a copy.
 *
 * # Safety
 *
 * - `deque` must have space for `slice.len()` new elements.
 * - Elements of `slice` will be copied into the deque, make sure to forget the elements if `T` is not `Copy`.
 */
public fun <T, A : Allocator> prepend(deque: VecDeque<T, A>, slice: List<T>) {
    // SAFETY: Upheld by caller.
    run {
        deque.head = deque.wrapSub(deque.head, slice.size)
        deque.copySlice(deque.head, slice)
        deque.len += slice.size
    }
}

/**
 * Prepends elements of `slice` to `deque` in reverse order using a copy.
 *
 * # Safety
 *
 * - `deque` must have space for `slice.len()` new elements.
 * - Elements of `slice` will be copied into the deque, make sure to forget the elements if `T` is not `Copy`.
 */
public fun <T, A : Allocator> prependReversed(deque: VecDeque<T, A>, slice: List<T>) {
    // SAFETY: Upheld by caller.
    run {
        deque.head = deque.wrapSub(deque.head, slice.size)
        deque.copySliceReversed(deque.head, slice)
        deque.len += slice.size
    }
}

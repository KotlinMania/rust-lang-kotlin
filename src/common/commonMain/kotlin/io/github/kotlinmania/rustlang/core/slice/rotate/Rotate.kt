// port-lint: source slice/rotate.rs
package io.github.kotlinmania.rustlang.core.slice.rotate

import io.github.kotlinmania.rustlang.core.intrinsics.alignOf
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.swapNonoverlapping
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public typealias BufType = IntArray

/**
 * Rotates the range `[mid-left, mid+right)` such that the element at `mid` becomes the first
 * element. Equivalently, rotates the range `left` elements to the left or `right` elements to the
 * right.
 *
 * # Safety
 *
 * The specified range must be valid for reading and writing.
 */
public fun <T> ptrRotate(left: Int, mid: MutPtr, right: Int, typeLayoutT: TypeLayout<T>) {
    if (T.IS_ZST) {
        return
    }
    // abort early if the rotate is a no-op
    if ((left == 0) || (right == 0)) {
        return
    }
    // For huge slices, GCD has poor locality because it jumps by large element-sized
    // strides and carries a large temporary. Since rotation only permutes raw storage,
    // a large element whose size/alignment permits it can instead be viewed as multiple
    // `MaybeUninit<Int>` words. This makes dispatch choose the contiguous swap algorithm,
    // which has much better cache/TLB locality.
    if (!false && sizeOf<T>(typeLayoutT = typeLayoutT) > sizeOf<IntArray>(typeLayoutT = PrimitiveArrayLayouts.usize64Array((4).toInt())) && sizeOf<T>(typeLayoutT = typeLayoutT) % sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) == 0 && alignOf<T>(typeLayoutT = typeLayoutT) >= alignOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)) {
        val ratio = sizeOf<T>(typeLayoutT = typeLayoutT) / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
        // SAFETY: this covers exactly the same bytes as `[mid-left, mid+right)` because
        // `ratio * sizeOf<Int>() == sizeOf<T>()`. The check above guarantees
        // `mid` is aligned for `Int`, and preserving the total byte length introduces
        // no new overflow.
        ptrRotateDispatch(left * ratio, MutPtr(mid.rawAddress, mid.rawMeta), right * ratio, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<Int>>())
    } else {
        // SAFETY: guaranteed by the caller
        ptrRotateDispatch(left, mid, right, typeLayoutT = typeLayoutT)
    }
}

/**
 * Chooses between the three rotation algorithms. See the algorithm docs below.
 *
 * # Safety
 *
 * The specified range must be valid for reading and writing.
 */
public fun <T> ptrRotateDispatch(left: Int, mid: MutPtr, right: Int, typeLayoutT: TypeLayout<T>) {
    // `T` is not a zero-sized type, so it's okay to divide by its size.
    if (!false && constMin(left, right) <= sizeOf<BufType>(typeLayoutT = PrimitiveArrayLayouts.usize64Array((32).toInt())) / sizeOf<T>(typeLayoutT = typeLayoutT)) {
        // SAFETY: guaranteed by the caller
        ptrRotateMemmove(left, mid, right, typeLayoutT = typeLayoutT)
    } else if (!false && ((left + right < 24) || (sizeOf<T>(typeLayoutT = typeLayoutT) > sizeOf<IntArray>(typeLayoutT = PrimitiveArrayLayouts.usize64Array((4).toInt()))))) {
        // SAFETY: guaranteed by the caller
        ptrRotateGcd(left, mid, right, typeLayoutT = typeLayoutT)
    } else {
        // SAFETY: guaranteed by the caller
        ptrRotateSwap(left, mid, right, typeLayoutT = typeLayoutT)
    }
}

/**
 * Algorithm 1 is used if `min(left, right)` is small enough to fit onto a stack buffer. The
 * `min(left, right)` elements are copied onto the buffer, `memmove` is applied to the others, and
 * the ones on the buffer are moved back into the hole on the opposite side of where they
 * originated.
 *
 * # Safety
 *
 * The specified range must be valid for reading and writing.
 */
public fun <T> ptrRotateMemmove(left: Int, mid: MutPtr, right: Int, typeLayoutT: TypeLayout<T>) {
    // The `[T; 0]` here is to ensure this is appropriately aligned for T
    val rawarray = MaybeUninit.uninit(UnresolvedTypeLayout<Pair<BufType, List<T>>>())
    val buf = mutPtrFromRustCast(rawarray.asMutPtr())
    // SAFETY: `mid - left <= mid - left + right<mid + right`
    val dim = MutPtr((MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes))).rawAddress + ((right).toLong() * (typeLayoutT).strideBytes))
    if (left <= right) {
        // SAFETY:
        //
        // 1) The `if` condition about the sizes ensures `[mid - left; left]` will fit in
        // `buf` without overflow and `buf` was created just above and so cannot be
        // overlapped with any value of `[mid - left; left]`
        // 2) [mid-left, mid+right) are all valid for reading and writing and we don't care
        // about overlaps here.
        // 3) The `if` condition about `left <= right` ensures writing `left` elements to
        // `dim = mid - left + right` is valid because:
        // - `buf` is valid and `left` elements were written in it in 1)
        // - `dim + left = mid - left + right + left = mid + right` and we write `[dim, dim+left)`
        run {
            // 1)
            copyNonoverlapping(MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes)), buf, left, typeLayoutT = typeLayoutT)
            // 2)
            copy(mid, MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes)), right, typeLayoutT = typeLayoutT)
            // 3)
            copyNonoverlapping(buf, dim, left, typeLayoutT = typeLayoutT)
        }
    } else {
        // SAFETY: same reasoning as above but with `left` and `right` reversed
        run {
            copyNonoverlapping(mid, buf, right, typeLayoutT = typeLayoutT)
            copy(MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes)), dim, left, typeLayoutT = typeLayoutT)
            copyNonoverlapping(buf, MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes)), right, typeLayoutT = typeLayoutT)
        }
    }
}

/**
 * Algorithm 2 is used for small values of `left + right` or for large `T`. The elements
 * are moved into their final positions one at a time starting at `mid - left` and advancing by
 * `right` steps modulo `left + right`, such that only one temporary is needed. Eventually, we
 * arrive back at `mid - left`. However, if `gcd(left + right, right)` is not 1, the above steps
 * skipped over elements. For example:
 * ```text
 * left = 10, right = 6
 * the `^` indicates an element in its final place
 * 6 7 8 9 10 11 12 13 14 15 . 0 1 2 3 4 5
 * after using one step of the above algorithm (The X will be overwritten at the end of the round,
 * and 12 is stored in a temporary):
 * X 7 8 9 10 11 6 13 14 15 . 0 1 2 3 4 5
 *               ^
 * after using another step (now 2 is in the temporary):
 * X 7 8 9 10 11 6 13 14 15 . 0 1 12 3 4 5
 *               ^                 ^
 * after the third step (the steps wrap around, and 8 is in the temporary):
 * X 7 2 9 10 11 6 13 14 15 . 0 1 12 3 4 5
 *     ^         ^                 ^
 * after 7 more steps, the round ends with the temporary 0 getting put in the X:
 * 0 7 2 9 4 11 6 13 8 15 . 10 1 12 3 14 5
 * ^   ^   ^    ^    ^       ^    ^    ^
 * ```
 * Fortunately, the number of skipped over elements between finalized elements is always equal, so
 * we can just offset our starting position and do more rounds (the total number of rounds is the
 * `gcd(left + right, right)` value). The end result is that all elements are finalized once and
 * only once.
 *
 * Algorithm 2 can be vectorized by chunking and performing many rounds at once, but there are too
 * few rounds on average until `left + right` is enormous, and the worst case of a single
 * round is always there.
 *
 * # Safety
 *
 * The specified range must be valid for reading and writing.
 */
public fun <T> ptrRotateGcd(left: Int, mid: MutPtr, right: Int, typeLayoutT: TypeLayout<T>) {
    // Algorithm 2
    // Microbenchmarks indicate that the average performance for random shifts is better all
    // the way until about `left + right == 32`, but the worst case performance breaks even
    // around 16. 24 was chosen as middle ground. If the size of `T` is larger than 4
    // `Int`s, this algorithm also outperforms other algorithms.
    // SAFETY: callers must ensure `mid - left` is valid for reading and writing.
    val x = MutPtr((mid).rawAddress - ((left).toLong() * (typeLayoutT).strideBytes))
    // beginning of first round
    // SAFETY: see previous comment.
    var tmp: T = (typeLayoutT).readNative((x).rawAddress)
    var i = right
    // `gcd` can be found before hand by calculating `gcd(left + right, right)`,
    // but it is faster to do one loop which calculates the gcd as a side effect, then
    // doing the rest of the chunk
    var gcd = right
    // benchmarks reveal that it is faster to swap temporaries all the way through instead
    // of reading one temporary once, copying backwards, and then writing that temporary at
    // the very end. This is possibly due to the fact that swapping or replacing temporaries
    // uses only one memory address in the loop instead of needing to manage two.
    while (true) {
        // [long-safety-expl]
        // SAFETY: callers must ensure `[left, left+mid+right)` are all valid for reading and
        // writing.
        //
        // - `i` start with `right` so `mid - left <= x + i = x + right = mid - left + right<mid + right`
        // - `i <= left + right - 1` is always true
        // - if `i<left`, `right` is added so `i<left + right` and on the next
        // iteration `left` is removed from `i` so it doesn't go further
        // - if `i >= left`, `left` is removed immediately and so it doesn't go further.
        // - overflows cannot happen for `i` since the function's safety contract ask for
        // `mid + right - 1 = x + left + right` to be valid for writing
        // - underflows cannot happen because `i` must be bigger or equal to `left` for
        // a subtraction of `left` to happen.
        //
        // So `x + i` is valid for reading and writing if the caller respected the contract
        tmp = MutPtr((x).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes)).replace(tmp)
        // instead of incrementing `i` and then checking if it is outside the bounds, we
        // check if `i` will go outside the bounds on the next increment. This prevents
        // any wrapping of pointers or `Int`.
        if (i >= left) {
            i -= left
            if (i == 0) {
                // end of first round
                // SAFETY: tmp has been read from a valid source and x is valid for writing
                // according to the caller.
                (typeLayoutT).writeNative((x).rawAddress, tmp)
                break
            }
            // this conditional must be here if `left + right >= 15`
            if (i < gcd) {
                gcd = i
            }
        } else {
            i += right
        }
    }
    // finish the chunk with more rounds
    // Note(const-Note): Use `for start in 1 .. gcd` when available in const
    var start = 1
    while (start < gcd) {
        // SAFETY: `gcd` is at most equal to `right` so all values in `1 .. gcd` are valid for
        // reading and writing as per the function's safety contract, see [long-safety-expl]
        // above
        tmp = (typeLayoutT).readNative((MutPtr((x).rawAddress + ((start).toLong() * (typeLayoutT).strideBytes))).rawAddress)
        // [safety-expl-addition]
        //
        // Here `start<gcd` so `start<right` so `i<right + right`: `right` being the
        // greatest common divisor of `(left + right, right)` means that `left = right` so
        // `i<left + right` so `x + i = mid - left + i` is always valid for reading and writing
        // according to the function's safety contract.
        i = start + right
        while (true) {
            // SAFETY: see [long-safety-expl] and [safety-expl-addition]
            tmp = MutPtr((x).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes)).replace(tmp)
            if (i >= left) {
                i -= left
                if (i == start) {
                    // SAFETY: see [long-safety-expl] and [safety-expl-addition]
                    (typeLayoutT).writeNative((MutPtr((x).rawAddress + ((start).toLong() * (typeLayoutT).strideBytes))).rawAddress, tmp)
                    break
                }
            } else {
                i += right
            }
        }
        start += 1
    }
}

/**
 * Algorithm 3 utilizes repeated swapping of `min(left, right)` elements.
 *
 * ///
 * ```text
 * left = 11, right = 4
 * [4 5 6 7 8 9 10 11 12 13 14 . 0 1 2 3]
 *                  ^  ^  ^  ^   ^ ^ ^ ^ swapping the right most elements with elements to the left
 * [4 5 6 7 8 9 10 . 0 1 2 3] 11 12 13 14
 *        ^ ^ ^  ^   ^ ^ ^ ^ swapping these
 * [4 5 6 . 0 1 2 3] 7 8 9 10 11 12 13 14
 * we cannot swap any more, but a smaller rotation problem is left to solve
 * ```
 * when `left<right` the swapping happens from the left instead.
 *
 * # Safety
 *
 * The specified range must be valid for reading and writing.
 */
public fun <T> ptrRotateSwap(left: Int, mid: MutPtr, right: Int, typeLayoutT: TypeLayout<T>) {
    var left1 = left
    var mid1 = mid
    var right1 = right
    while (true) {
        if (left1 >= right1) {
            // Algorithm 3
            // There is an alternate way of swapping that involves finding where the last swap
            // of this algorithm would be, and swapping using that last chunk instead of swapping
            // adjacent chunks like this algorithm is doing, but this way is still faster.
            while (true) {
                // SAFETY:
                // `left >= right` so `[mid-right, mid+right)` is valid for reading and writing
                // Subtracting `right` from `mid` each turn is counterbalanced by the addition and
                // check after it.
                run {
                    swapNonoverlapping(MutPtr((mid1).rawAddress - ((right1).toLong() * (typeLayoutT).strideBytes)), mid1, right1)
                    mid1 = MutPtr((mid1).rawAddress - ((right1).toLong() * (typeLayoutT).strideBytes))
                }
                left1 -= right1
                if (left1 < right1) {
                    break
                }
            }
        } else {
            // Algorithm 3, `left<right`
            while (true) {
                // SAFETY: `[mid-left, mid+left)` is valid for reading and writing because
                // `left<right` so `mid + left<mid + right`.
                // Adding `left` to `mid` each turn is counterbalanced by the subtraction and check
                // after it.
                run {
                    swapNonoverlapping(MutPtr((mid1).rawAddress - ((left1).toLong() * (typeLayoutT).strideBytes)), mid1, left1)
                    mid1 = MutPtr((mid1).rawAddress + ((left1).toLong() * (typeLayoutT).strideBytes))
                }
                right1 -= left1
                if (right1 < left1) {
                    break
                }
            }
        }
        if ((right1 == 0) || (left1 == 0)) {
            return
        }
        // Switch to the buffered algorithm once the smaller side fits in the stack
        // buffer. The swap algorithm is efficient for reducing large blocks, but its
        // tail can degenerate into many small swaps when one side becomes tiny.
        if (!false && constMin(left1, right1) <= sizeOf<BufType>(typeLayoutT = PrimitiveArrayLayouts.usize64Array((32).toInt())) / sizeOf<T>(typeLayoutT = typeLayoutT)) {
            // SAFETY: the remaining range `[mid-left, mid+right)` is a subrange of the
            // original input, so it is valid for reading and writing.
            ptrRotateMemmove(left1, mid1, right1)
            return
        }
    }
}

// Note(const-Note): Use cmp.min when available in const
public fun constMin(left: Int, right: Int): Int =
    if (right < left) {
        right
    } else {
        left
    }

// port-lint: source slice/sort/stable/merge.rs
package io.github.kotlinmania.rustlang.core.slice.sort.stable.merge

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castInit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// This module contains logic for performing a merge of two sorted sub-slices.

/**
 * Merges non-decreasing runs `v[.. mid]` and `v[mid ..]` using `scratch` as
 * temporary storage, and stores the result into `v[..]`.
 */
public fun <T> merge(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, mid: Int, isLess: (T, T) -> Boolean) {
    val len = v.size
    if (mid == 0 || mid >= len || scratch.size < minOf(mid, len - mid)) {
        return
    }
    // SAFETY: We checked that the two slices are non-empty and `mid` is in-bounds.
    // We checked that the buffer `scratch` has enough capacity to hold a copy of
    // the shorter slice. `mergeUp` and `mergeDown` are written in such a way that
    // they uphold the contract described in `MergeState.drop`.
    run {
        // The merge process first copies the shorter run into `buf`. Then it traces
        // the newly copied run and the longer run forwards (or backwards), comparing
        // their next unconsumed elements and copying the lesser (or greater) one into `v`.
        //
        // As soon as the shorter run is fully consumed, the process is done. If the
        // longer run gets consumed first, then we must copy whatever is left of the
        // shorter run into the remaining gap in `v`.
        //
        // Intermediate state of the process is always tracked by `gap`, which serves
        // two purposes:
        // 1. Protects integrity of `v` from panics in `isLess`.
        // 2. Fills the remaining gap in `v` if the longer run gets consumed first.
        val buf = scratch.asMutPtr().castInit()
        val vBase = v.asMutPtr()
        val vMid = MutPtr((vBase).rawAddress + ((mid).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        val vEnd = MutPtr((vBase).rawAddress + ((len).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        val leftLen = mid
        val rightLen = len - mid
        val leftIsShorter = leftLen <= rightLen
        val saveBase =
            if (leftIsShorter) {
                vBase
            } else {
                vMid
            }
        val saveLen =
            if (leftIsShorter) {
                leftLen
            } else {
                rightLen
            }
        copyNonoverlapping((saveBase).asConstPtr(), buf, saveLen)
        val mergeState = MergeState(start = buf, end = buf.add(saveLen), dst = saveBase)
        if (leftIsShorter) {
            mergeState.mergeUp(vMid, vEnd, isLess)
        } else {
            mergeState.mergeDown(vBase, buf, vEnd, isLess)
        }
        // Finally, `mergeState` gets dropped. If the shorter run was not fully
        // consumed, whatever remains of it will now be copied into the hole in `v`.
    }
}

// When dropped, copies the range `start .. end` into `dst ..`.
public open class MergeState<T>(
    public var start: MutPtr,
    public var end: MutPtr,
    public var dst: MutPtr,
) {
    /**
     * # Safety
     * The caller MUST guarantee that `self` is initialized in a way where `start -> end` is
     * the longer sub-slice and so that `dst` can be written to at least the shorter sub-slice
     * length times. In addition `start -> end` and `right -> rightEnd` MUST be valid to be
     * read. This function MUST only be called once.
     */
    public open fun mergeUp(right: ConstPtr, rightEnd: ConstPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        var right1 = right
        // SAFETY: See function safety comment.
        run {
            val left = this.start
            val out = this.dst
            while ((((typeLayoutT).readNative((left).rawAddress)).rawAddress.toULong() != (this.end).rawAddress.toULong()) && ((right1).rawAddress.toULong() != (rightEnd).rawAddress.toULong())) {
                val consumeLeft = !isLess((typeLayoutT).readNative((right1).rawAddress), (typeLayoutT).readNative((left).rawAddress))
                val src =
                    if (consumeLeft) {
                        (typeLayoutT).readNative((left).rawAddress)
                    } else {
                        right1
                    }
                copyNonoverlapping(src, (typeLayoutT).readNative((out).rawAddress), 1)
                (typeLayoutT).writeNative((left).rawAddress, MutPtr((left).rawAddress + ((consumeLeft.toInt()).toLong() * (typeLayoutT).strideBytes)))
                right1 = ConstPtr((right1).rawAddress + (((if (!consumeLeft) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
                (typeLayoutT).writeNative((out).rawAddress, MutPtr((out).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes)))
            }
        }
    }

    /**
     * # Safety
     * The caller MUST guarantee that `self` is initialized in a way where `leftEnd< - dst` is
     * the shorter sub-slice and so that `out` can be written to at least the shorter sub-slice
     * length times. In addition `leftEnd< - dst` and `rightEnd< - end` MUST be valid to be
     * read. This function MUST only be called once.
     */
    public open fun mergeDown(leftEnd: ConstPtr, rightEnd: ConstPtr, out: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        var out1 = out
        // SAFETY: See function safety comment.
        while (true) {
            val left = MutPtr((this.dst).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
            val right = MutPtr((this.end).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
            out1 = MutPtr((out1).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
            val consumeLeft = isLess((typeLayoutT).readNative((right).rawAddress), (typeLayoutT).readNative((left).rawAddress))
            val src =
                if (consumeLeft) {
                    left
                } else {
                    right
                }
            copyNonoverlapping((src).asConstPtr(), out1, 1)
            this.dst = MutPtr((left).rawAddress + (((if (!consumeLeft) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
            this.end = MutPtr((right).rawAddress + ((consumeLeft.toInt()).toLong() * (typeLayoutT).strideBytes))
            if ((((this.dst).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong() == (leftEnd).rawAddress.toULong()) || (((this.end).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong() == (rightEnd).rawAddress.toULong())) {
                break
            }
        }
    }

    public open fun drop() {
        // SAFETY: The user of MergeState MUST ensure, that at any point this drop
        // impl MAY run, for example when the user provided `isLess` panics, that
        // copying the contiguous region between `start` and `end` to `dst` will
        // leave the input slice `v` with each original element and all possible
        // modifications observed.
        run {
            val len = this.end.offsetFromUnsigned((this.start).asConstPtr())
            copyNonoverlapping((this.start).asConstPtr(), this.dst, len)
        }
    }

    public companion object {
        public fun <T> typeLayout(): TypeLayout<MergeState<T>> =
            object : TypeLayout<MergeState<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): MergeState<T> =
                    MergeState(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: MergeState<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.start)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.end)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.dst)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MergeState<T> =
                    MergeState(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MergeState<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.start)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.end)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.dst)
                }
            }
    }
}

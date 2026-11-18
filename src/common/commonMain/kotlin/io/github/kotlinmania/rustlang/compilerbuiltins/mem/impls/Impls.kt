// port-lint: source mem/impls.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.mem.impls

import io.github.kotlinmania.rustlang.compilerbuiltins.support.coldPath
import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub

public val WORD_SIZE: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

public val WORD_MASK: Int = WORD_SIZE - 1

// If the number of bytes involved exceed this threshold we will opt in word-wise copy.
// The value here selected is max(2 * WORD_SIZE, 16):
// * We need at least 2 * WORD_SIZE bytes to guarantee that at least 1 word will be copied through
// word-wise copy.
// * The word-wise copy logic needs to perform some checks so it has some small overhead.
// ensures that even on 32-bit platforms we have copied at least 8 bytes through
// word-wise copy so the saving of word-wise copy outweighs the fixed overhead.
public val WORD_COPY_THRESHOLD: Int =
    if (2 * WORD_SIZE > 16) {
        2 * WORD_SIZE
    } else {
        16
    }

public fun readUsizeUnaligned(x: ConstPtr): Int {
    // Do not use `core.ptr.readUnaligned` here, since it calls `copyNonoverlapping` which
    // is translated to memcpy in LLVM.
    val xRead = (PrimitiveArrayLayouts.uByteArray((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)).toInt())).readNative(((ConstPtr(x.rawAddress, x.rawMeta))).rawAddress)
    return run { val bytesTmp0 = xRead; ((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()) }
}

/**
 * Loads a `T`-sized chunk from `src` into `dst` at offset `offset`, if that does not exceed
 * `loadSz`. The offset pointers must both be `T`-aligned. Returns the new offset, advanced by the
 * chunk size if a load happened.
 */
public fun <T> loadChunkAligned(src: ConstPtr, dst: MutPtr, loadSz: Int, offset: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    val chunkSz = sizeOf<T>(typeLayoutT = typeLayoutT)
    return if ((loadSz and chunkSz) != 0) {
        (typeLayoutT).writeNative((MutPtr((MutPtr((dst).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress, (typeLayoutT).readNative((ConstPtr((ConstPtr((src).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress))
        offset or chunkSz
    } else {
        offset
    }
}

/**
 * Load `loadSz` many bytes from `src`, which must be Int-aligned. Acts as if we did a `Int`
 * read with the out-of-bounds part filled with 0s.
 * `loadSz` be strictly less than `WORD_SIZE`.
 */
public fun loadAlignedPartial(src: ConstPtr, loadSz: Int): Int {
    check(loadSz < WORD_SIZE)
    // We can read up to 7 bytes here, which is enough for WORD_SIZE of 8
    // (since `loadSz<WORD_SIZE`).
    check(WORD_SIZE <= 8)
    var i = 0
    val localStorageL78C4 = AddressableLocal(0, PrimitiveLayouts.USIZE64)
    val out by localStorageL78C4
    // We load in decreasing order, so the pointers remain sufficiently aligned for the next step.
    i = loadChunkAligned<UInt>(src, localStorageL78C4.asMutPtr(), loadSz, i, typeLayoutT = PrimitiveLayouts.U32)
    i = loadChunkAligned<UShort>(src, localStorageL78C4.asMutPtr(), loadSz, i, typeLayoutT = PrimitiveLayouts.U16)
    i = loadChunkAligned<UByte>(src, localStorageL78C4.asMutPtr(), loadSz, i, typeLayoutT = PrimitiveLayouts.U8)
    check(i == loadSz)
    return out
}

/**
 * Load `loadSz` many bytes from `src.wrappingByteAdd(WORD_SIZE - loadSz)`. `src` must be
 * `Int`-aligned. The bytes are returned as the *last* bytes of the return value, i.e., this acts
 * as if we had done a `Int` read from `src`, with the out-of-bounds part filled with 0s.
 * `loadSz` be strictly less than `WORD_SIZE`.
 */
public fun loadAlignedEndPartial(src: ConstPtr, loadSz: Int): Int {
    check(loadSz < WORD_SIZE)
    // We can read up to 7 bytes here, which is enough for WORD_SIZE of 8
    // (since `loadSz<WORD_SIZE`).
    check(WORD_SIZE <= 8)
    var i = 0
    val localStorageL100C4 = AddressableLocal(0, PrimitiveLayouts.USIZE64)
    val out by localStorageL100C4
    // Obtain pointers pointing to the beginning of the range we want to load.
    val srcShifted = ConstPtr((src).rawAddress + ((WORD_SIZE - loadSz).toLong()))
    val outShifted = MutPtr(((localStorageL100C4.asMutPtr())).rawAddress + ((WORD_SIZE - loadSz).toLong()))
    // We load in increasing order, so by the time we reach `UShort` things are 2-aligned etc.
    i = loadChunkAligned<UByte>(srcShifted, outShifted, loadSz, i, typeLayoutT = PrimitiveLayouts.U8)
    i = loadChunkAligned<UShort>(srcShifted, outShifted, loadSz, i, typeLayoutT = PrimitiveLayouts.U16)
    i = loadChunkAligned<UInt>(srcShifted, outShifted, loadSz, i, typeLayoutT = PrimitiveLayouts.U32)
    check(i == loadSz)
    return out
}

public fun copyForward(dest: MutPtr, src: ConstPtr, n: Int) {
    var dest1 = dest
    var src1 = src
    var n1 = n
    fun copyForwardBytes(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var dest23 = dest2
        var src23 = src2
        val destEnd = MutPtr((dest23).rawAddress + ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))
        while (((dest23).rawAddress.toULong() < (destEnd).rawAddress.toULong())) {
            (PrimitiveLayouts.U8).writeNative((dest23).rawAddress, (PrimitiveLayouts.U8).readNative((src23).rawAddress))
            dest23 = MutPtr((dest23).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
            src23 = ConstPtr((src23).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }
    fun copyForwardAlignedWords(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var destUsize = MutPtr(dest2.rawAddress, dest2.rawMeta)
        var srcUsize = MutPtr(src2.rawAddress, src2.rawMeta)
        val destEnd = (MutPtr((dest2).rawAddress + ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (((destUsize).rawAddress.toULong() < (destEnd).rawAddress.toULong())) {
            (PrimitiveLayouts.USIZE64).writeNative((destUsize).rawAddress, (PrimitiveLayouts.USIZE64).readNative((srcUsize).rawAddress))
            destUsize = MutPtr((destUsize).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            srcUsize = MutPtr((srcUsize).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
        }
    }
    /**
     * `n` is in units of bytes, but must be a multiple of the word size and must not be 0.
     * `src` *must not* be `Int`-aligned.
     */
    fun copyForwardMisalignedWords(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var destUsize = MutPtr(dest2.rawAddress, dest2.rawMeta)
        var srcUsize = MutPtr(src2.rawAddress, src2.rawMeta)
        val destEnd = (MutPtr((dest2).rawAddress + ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (((destUsize).rawAddress.toULong() < (destEnd).rawAddress.toULong())) {
            (PrimitiveLayouts.USIZE64).writeNative((destUsize).rawAddress, readUsizeUnaligned((srcUsize).asConstPtr()))
            destUsize = MutPtr((destUsize).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            srcUsize = MutPtr((srcUsize).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
        }
    }
    if (n1 >= WORD_COPY_THRESHOLD) {
        // Align dest
        // Because of n >= 2 * WORD_SIZE, dstMisalignment < n
        val destMisalignment = (dest1.rawAddress.toInt()).wrappingNeg() and WORD_MASK
        copyForwardBytes(dest1, src1, destMisalignment)
        dest1 = MutPtr((dest1).rawAddress + ((destMisalignment).toLong() * (PrimitiveLayouts.U8).strideBytes))
        src1 = ConstPtr((src1).rawAddress + ((destMisalignment).toLong() * (PrimitiveLayouts.U8).strideBytes))
        n1 -= destMisalignment
        val nWords = n1 and WORD_MASK.inv()
        val srcMisalignment = src1.rawAddress.toInt() and WORD_MASK
        if (srcMisalignment == 0) {
            copyForwardAlignedWords(dest1, src1, nWords)
        } else {
            coldPath()
            copyForwardMisalignedWords(dest1, src1, nWords)
        }
        dest1 = MutPtr((dest1).rawAddress + ((nWords).toLong() * (PrimitiveLayouts.U8).strideBytes))
        src1 = ConstPtr((src1).rawAddress + ((nWords).toLong() * (PrimitiveLayouts.U8).strideBytes))
        n1 -= nWords
    }
    copyForwardBytes(dest1, src1, n1)
}

public fun copyBackward(dest: MutPtr, src: ConstPtr, n: Int) {
    val dest1 = dest
    val src1 = src
    var n1 = n
    // The following backward copy helper functions uses the pointers past the end
    // as their inputs instead of pointers to the start!
    fun copyBackwardBytes(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var dest23 = dest2
        var src23 = src2
        val destStart = MutPtr((dest23).rawAddress - ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))
        while (((destStart).rawAddress.toULong() < (dest23).rawAddress.toULong())) {
            dest23 = MutPtr((dest23).rawAddress - ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
            src23 = ConstPtr((src23).rawAddress - ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
            (PrimitiveLayouts.U8).writeNative((dest23).rawAddress, (PrimitiveLayouts.U8).readNative((src23).rawAddress))
        }
    }
    fun copyBackwardAlignedWords(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var destUsize = MutPtr(dest2.rawAddress, dest2.rawMeta)
        var srcUsize = MutPtr(src2.rawAddress, src2.rawMeta)
        val destStart = (MutPtr((dest2).rawAddress - ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (((destStart).rawAddress.toULong() < (destUsize).rawAddress.toULong())) {
            destUsize = MutPtr((destUsize).rawAddress - ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            srcUsize = MutPtr((srcUsize).rawAddress - ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            (PrimitiveLayouts.USIZE64).writeNative((destUsize).rawAddress, (PrimitiveLayouts.USIZE64).readNative((srcUsize).rawAddress))
        }
    }
    /**
     * `n` is in units of bytes, but must be a multiple of the word size and must not be 0.
     * `src` *must not* be `Int`-aligned.
     */
    fun copyBackwardMisalignedWords(dest2: MutPtr, src2: ConstPtr, n2: Int) {
        var destUsize = MutPtr(dest2.rawAddress, dest2.rawMeta)
        var srcUsize = MutPtr(src2.rawAddress, src2.rawMeta)
        val destStart = (MutPtr((dest2).rawAddress - ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (((destStart).rawAddress.toULong() < (destUsize).rawAddress.toULong())) {
            destUsize = MutPtr((destUsize).rawAddress - ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            srcUsize = MutPtr((srcUsize).rawAddress - ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
            (PrimitiveLayouts.USIZE64).writeNative((destUsize).rawAddress, readUsizeUnaligned((srcUsize).asConstPtr()))
        }
    }
    var dest2 = MutPtr((dest1).rawAddress + ((n1).toLong() * (PrimitiveLayouts.U8).strideBytes))
    var src2 = ConstPtr((src1).rawAddress + ((n1).toLong() * (PrimitiveLayouts.U8).strideBytes))
    if (n1 >= WORD_COPY_THRESHOLD) {
        // Align dest
        // Because of n >= 2 * WORD_SIZE, dstMisalignment < n
        val destMisalignment = dest2.toInt() and WORD_MASK
        copyBackwardBytes(dest2, src2, destMisalignment)
        dest2 = dest2.wrappingSub(destMisalignment)
        src2 = src2.wrappingSub(destMisalignment)
        n1 -= destMisalignment
        val nWords = n1 and WORD_MASK.inv()
        val srcMisalignment = src2.toInt() and WORD_MASK
        if (srcMisalignment == 0) {
            copyBackwardAlignedWords(dest2, src2, nWords)
        } else {
            coldPath()
            copyBackwardMisalignedWords(dest2, src2, nWords)
        }
        dest2 = dest2.wrappingSub(nWords)
        src2 = src2.wrappingSub(nWords)
        n1 -= nWords
    }
    copyBackwardBytes(dest2, src2, n1)
}

public fun setBytes(s: MutPtr, c: UByte, n: Int) {
    var s1 = s
    var n1 = n
    fun setBytesBytes(s2: MutPtr, c1: UByte, n2: Int) {
        var s23 = s2
        val end = MutPtr((s23).rawAddress + ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))
        while (((s23).rawAddress.toULong() < (end).rawAddress.toULong())) {
            (PrimitiveLayouts.U8).writeNative((s23).rawAddress, c1)
            s23 = MutPtr((s23).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }
    fun setBytesWords(s2: MutPtr, c1: UByte, n2: Int) {
        var broadcast = c1.toInt()
        var bits = 8
        while (bits < WORD_SIZE * 8) {
            broadcast = broadcast or (broadcast shl bits.toInt())
            bits *= 2
        }
        var sUsize = MutPtr(s2.rawAddress, s2.rawMeta)
        val end = (MutPtr((s2).rawAddress + ((n2).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (((sUsize).rawAddress.toULong() < (end).rawAddress.toULong())) {
            (PrimitiveLayouts.USIZE64).writeNative((sUsize).rawAddress, broadcast)
            sUsize = MutPtr((sUsize).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
        }
    }
    if (n1 >= WORD_COPY_THRESHOLD) {
        // Align s
        // Because of n >= 2 * WORD_SIZE, dstMisalignment < n
        val misalignment = (s1.rawAddress.toInt()).wrappingNeg() and WORD_MASK
        setBytesBytes(s1, c, misalignment)
        s1 = MutPtr((s1).rawAddress + ((misalignment).toLong() * (PrimitiveLayouts.U8).strideBytes))
        n1 -= misalignment
        val nWords = n1 and WORD_MASK.inv()
        setBytesWords(s1, c, nWords)
        s1 = MutPtr((s1).rawAddress + ((nWords).toLong() * (PrimitiveLayouts.U8).strideBytes))
        n1 -= nWords
    } else {
        coldPath()
    }
    setBytesBytes(s1, c, n1)
}

public fun compareBytes(s1: ConstPtr, s2: ConstPtr, n: Int): CInt {
    var i = 0
    while (i < n) {
        val a = (PrimitiveLayouts.U8).readNative((ConstPtr((s1).rawAddress + ((i).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
        val b = (PrimitiveLayouts.U8).readNative((ConstPtr((s2).rawAddress + ((i).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
        if (a != b) {
            return CInt.from(a) - CInt.from(b)
        }
        i += 1
    }
    return 0
}

public fun cStringLength(s: ConstPtr): Int {
    var s1 = s
    var n = 0
    while ((UnresolvedTypeLayout<CChar>()).readNative((s1).rawAddress) != 0u.toUByte()) {
        n += 1
        s1 = ConstPtr((s1).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<CChar>()).strideBytes))
    }
    return n
}

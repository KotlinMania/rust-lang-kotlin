// port-lint: source vec/sve_retain.rs
package io.github.kotlinmania.rustlang.alloc.vec.sveretain

import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// SVE-accelerated `List.retainMut` implementation.
//
// Two-phase algorithm per 64-element chunk:
// - Phase A (scalar): evaluates predicate exactly once, in order, into a bool mask.
// - Phase B (SVE): uses `COMPACT` instruction to compress retained elements.

public val CHUNK_SIZE: Int = 64

internal val MIN_SVE_SIZE_1: Int = 32

internal val MIN_SVE_SIZE_2: Int = 32

internal val MIN_SVE_SIZE_4: Int = 64

internal val MIN_SVE_SIZE_8: Int = 64

/**
 * Guard for the SVE retain path. On panic, this guard:
 * 1. Scalar-compresses the already-decided prefix of the current chunk.
 * 2. Copies the untouched tail forward.
 * 3. Sets the Vec length.
 */
public open class PanicGuard<T, A : Allocator>(
    public var v: List<T>,
    /**
     * Start index of the current chunk within the Vec.
     */
    public var read: Int,
    /**
     * Write cursor (accumulated from previous chunks).
     */
    public var write: Int,
    /**
     * How many elements in the current chunk have been decided by the predicate.
     */
    public var decided: Int,
    public var mask: BooleanArray,
    /**
     * Original length of the Vec.
     */
    public var originalLen: Int,
) {
    public open fun drop() {
        // Scalar-compress the decided prefix: move kept elements to write position.
        var dst = this.write
        for (i in 0 until this.decided) {
            if (this.mask[i]) {
                // SAFETY: read + i < originalLen (in-bounds).
                val src = ConstPtr((this.v.asPtr()).rawAddress + ((this.read + i).toLong() * (ObjectSlotLayout<T>()).strideBytes))
                // SAFETY: Continued from above.
                val dstPtr = MutPtr((this.v.asMutPtr()).rawAddress + ((dst).toLong() * (ObjectSlotLayout<T>()).strideBytes))
                // SAFETY: src and dstPtr < originalLen
                ptr.copy(src, dstPtr, 1)
                dst += 1
            }
        }
        // Copy the untouched tail.
        val untouchedStart = this.read + this.decided
        val untouchedLen = this.originalLen - untouchedStart
        if (untouchedLen > 0) {
            // SAFETY: untouchedStart..originalLen are valid; dst + untouchedLen <= originalLen.
            run {
                ptr.copy(ConstPtr((this.v.asPtr()).rawAddress + ((untouchedStart).toLong() * (ObjectSlotLayout<T>()).strideBytes)), MutPtr((this.v.asMutPtr()).rawAddress + ((dst).toLong() * (ObjectSlotLayout<T>()).strideBytes)), untouchedLen)
            }
            dst += untouchedLen
        }
        // SAFETY: After filling holes, all items are in contiguous memory.
        this.v.setLen(dst)
    }
}

/**
 * # Safety
 *
 * - `sizeOf<T>() == 1 / 2 / 4 / 8`
 * - Called on aarch64 + SVE target.
 */
internal fun <T, A : Allocator> chunkedRetain(v: List<T>, f: (T) -> Boolean, typeLayoutT: TypeLayout<T>) {
    val originalLen = v.size
    val guard = PanicGuard(v = v, read = 0, write = 0, decided = 0, mask = BooleanArray(CHUNK_SIZE) { false }, originalLen = originalLen)
    while (guard.read < guard.originalLen) {
        val chunkLen = minOf(CHUNK_SIZE, guard.originalLen - guard.read)
        guard.decided = 0
        // Phase A: scalar predicate evaluation (exactly once, in order).
        for (i in 0 until chunkLen) {
            // SAFETY: read + i < originalLen
            val cur = RustMutableReference(MutPtr((MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.read + i).toLong() * (typeLayoutT).strideBytes))).rawAddress), typeLayoutT)
            guard.mask[i] = f(cur)
            guard.decided = i + 1
            if (!guard.mask[i]) {
                // SAFETY: read + i < originalLen, and after marking `mask` and `decided`,
                // the guard can properly handles the case where dropInPlace panics.
                ptr.dropInPlace(cur)
            }
        }
        // Phase B: SVE compress.
        val kept =
            when (mem.sizeOf<T>(typeLayoutT = typeLayoutT)) {
                // SAFETY: write <= read and the dispatch guarantees sizeOf::<T>() matches
                // the kernel lane width.
                1 -> compact8Kernel(MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.read).toLong() * (typeLayoutT).strideBytes)), MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.write).toLong() * (typeLayoutT).strideBytes)), guard.mask.asPtr(), chunkLen)
                // SAFETY: Same as above.
                2 -> compact16Kernel(MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.read).toLong() * (typeLayoutT).strideBytes)), MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.write).toLong() * (typeLayoutT).strideBytes)), guard.mask.asPtr(), chunkLen)
                // SAFETY: Same as above.
                4 -> compact32Kernel(MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.read).toLong() * (typeLayoutT).strideBytes)), MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.write).toLong() * (typeLayoutT).strideBytes)), guard.mask.asPtr(), chunkLen)
                // SAFETY: Same as above.
                8 -> compact64Kernel(MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.read).toLong() * (typeLayoutT).strideBytes)), MutPtr((guard.v.asMutPtr()).rawAddress + ((guard.write).toLong() * (typeLayoutT).strideBytes)), guard.mask.asPtr(), chunkLen)
                else -> error("unreachable")
            }
        guard.write += kept
        guard.read += chunkLen
    }
    // SAFETY: write <= originalLen, all retained elements are packed at the front.
    guard.v.setLen(guard.write)
    mem.forget(guard)
}

/**
 * SVE compress kernel: pack retained elements from `src` to `dst`
 * according to `mask`. Returns the number of retained elements.
 *
 * # Safety
 *
 * - `src` is valid for `chunkLen` reads of `T`, `dst` for `chunkLen`
 *   writes, `mask` for `chunkLen` bool reads.
 * - `sizeOf<T>()` equals this kernel's lane width in bytes.
 */
public fun <T> compact8Kernel(src: ConstPtr, dst: MutPtr, mask: ConstPtr, chunkLen: Int): Int {
    check(mem.sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == 1)
    val idxIn = 0
    val idxOut = 0
    run {
        asm("whilelo p0.s, xzr, {len}", "2:", "ld1b {{ z0.s }}, p0/z, [{mask}, {idx_in}]", "cmpne p1.s, p0/z, z0.s, #0", "ld1b {{ z1.s }}, p1/z, [{src}, {idx_in}]", "compact z1.s, p1, z1.s", "cntp {kept}, p0, p1.s", "whilelo p2.s, xzr, {kept}", "st1b {{ z1.s }}, p2, [{dst}, {idx_out}]", "add {idx_out}, {idx_out}, {kept}", "incw {idx_in}", "whilelo p0.s, {idx_in}, {len}", "b.first 2b", src, reg, src, dst, reg, dst, mask, reg, mask, len, reg, chunkLen, idxIn, inout, reg, idxIn, idxOut, inout, reg, idxOut, kept, out, reg, out, "p0", out, "p1", out, "p2", out, "z0", out, "z1", options(nostack))
    }
    return idxOut
}

/**
 * SVE compress kernel: pack retained elements from `src` to `dst`
 * according to `mask`. Returns the number of retained elements.
 *
 * # Safety
 *
 * - `src` is valid for `chunkLen` reads of `T`, `dst` for `chunkLen`
 *   writes, `mask` for `chunkLen` bool reads.
 * - `sizeOf<T>()` equals this kernel's lane width in bytes.
 */
public fun <T> compact16Kernel(src: ConstPtr, dst: MutPtr, mask: ConstPtr, chunkLen: Int): Int {
    check(mem.sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == 2)
    val idxIn = 0
    val idxOut = 0
    run {
        asm("whilelo p0.s, xzr, {len}", "2:", "ld1b {{ z0.s }}, p0/z, [{mask}, {idx_in}]", "cmpne p1.s, p0/z, z0.s, #0", "ld1h {{ z1.s }}, p1/z, [{src}, {idx_in}, lsl #1]", "compact z1.s, p1, z1.s", "cntp {kept}, p0, p1.s", "whilelo p2.s, xzr, {kept}", "st1h {{ z1.s }}, p2, [{dst}, {idx_out}, lsl #1]", "add {idx_out}, {idx_out}, {kept}", "incw {idx_in}", "whilelo p0.s, {idx_in}, {len}", "b.first 2b", src, reg, src, dst, reg, dst, mask, reg, mask, len, reg, chunkLen, idxIn, inout, reg, idxIn, idxOut, inout, reg, idxOut, kept, out, reg, out, "p0", out, "p1", out, "p2", out, "z0", out, "z1", options(nostack))
    }
    return idxOut
}

/**
 * SVE compress kernel: pack retained elements from `src` to `dst`
 * according to `mask`. Returns the number of retained elements.
 *
 * # Safety
 *
 * - `src` is valid for `chunkLen` reads of `T`, `dst` for `chunkLen`
 *   writes, `mask` for `chunkLen` bool reads.
 * - `sizeOf<T>()` equals this kernel's lane width in bytes.
 */
public fun <T> compact32Kernel(src: ConstPtr, dst: MutPtr, mask: ConstPtr, chunkLen: Int): Int {
    check(mem.sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == 4)
    val idxIn = 0
    val idxOut = 0
    run {
        asm("whilelo p0.s, xzr, {len}", "2:", "ld1b {{ z0.s }}, p0/z, [{mask}, {idx_in}]", "cmpne p1.s, p0/z, z0.s, #0", "ld1w {{ z1.s }}, p1/z, [{src}, {idx_in}, lsl #2]", "compact z1.s, p1, z1.s", "cntp {kept}, p0, p1.s", "whilelo p2.s, xzr, {kept}", "st1w {{ z1.s }}, p2, [{dst}, {idx_out}, lsl #2]", "add {idx_out}, {idx_out}, {kept}", "incw {idx_in}", "whilelo p0.s, {idx_in}, {len}", "b.first 2b", src, reg, src, dst, reg, dst, mask, reg, mask, len, reg, chunkLen, idxIn, inout, reg, idxIn, idxOut, inout, reg, idxOut, kept, out, reg, out, "p0", out, "p1", out, "p2", out, "z0", out, "z1", options(nostack))
    }
    return idxOut
}

/**
 * SVE compress kernel: pack retained elements from `src` to `dst`
 * according to `mask`. Returns the number of retained elements.
 *
 * # Safety
 *
 * - `src` is valid for `chunkLen` reads of `T`, `dst` for `chunkLen`
 *   writes, `mask` for `chunkLen` bool reads.
 * - `sizeOf<T>()` equals this kernel's lane width in bytes.
 */
public fun <T> compact64Kernel(src: ConstPtr, dst: MutPtr, mask: ConstPtr, chunkLen: Int): Int {
    check(mem.sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == 8)
    val idxIn = 0
    val idxOut = 0
    run {
        asm("whilelo p0.d, xzr, {len}", "2:", "ld1b {{ z0.d }}, p0/z, [{mask}, {idx_in}]", "cmpne p1.d, p0/z, z0.d, #0", "ld1d {{ z1.d }}, p1/z, [{src}, {idx_in}, lsl #3]", "compact z1.d, p1, z1.d", "cntp {kept}, p0, p1.d", "whilelo p2.d, xzr, {kept}", "st1d {{ z1.d }}, p2, [{dst}, {idx_out}, lsl #3]", "add {idx_out}, {idx_out}, {kept}", "incd {idx_in}", "whilelo p0.d, {idx_in}, {len}", "b.first 2b", src, reg, src, dst, reg, dst, mask, reg, mask, len, reg, chunkLen, idxIn, inout, reg, idxIn, idxOut, inout, reg, idxOut, kept, out, reg, out, "p0", out, "p1", out, "p2", out, "z0", out, "z1", options(nostack))
    }
    return idxOut
}

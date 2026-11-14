// port-lint: source x86/sse4a.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

// `i686`s Streaming SIMD Extensions 4a (`SSE4a`)

public expect fun extrq(x: I64x2, y: I8x16): I64x2

public expect fun extrqi(x: I64x2, len: UByte, idx: UByte): I64x2

public expect fun insertq(x: I64x2, y: I64x2): I64x2

public expect fun insertqi(x: I64x2, y: I64x2, len: UByte, idx: UByte): I64x2

/**
 * Extracts the bit range specified by `y` from the lower 64 bits of `x`.
 *
 * The `[13: 8]` bits of `y` specify the index of the bit-range to extract. The
 * `[5: 0]` bits of `y` specify the length of the bit-range to extract. All
 * other bits are ignored.
 *
 * If the length is zero, it is interpreted as `64`. If the length and index
 * are zero, the lower 64 bits of `x` are extracted.
 *
 * If `length == 0 && index> 0` or `length + index> 64` the result is
 * undefined.
 *
 * The extracted bits are saved in the least-significant bit positions of the lower
 * quadword of the destination; the remaining bits in the lower quadword of the
 * destination register are cleared to 0. The upper quadword of the destination
 * register is undefined.
 */
public fun mmExtractSi64(x: M128i, y: M128i): M128i = transmute(extrq(x.asI64x2(), y.asI8x16()))

/**
 * Extracts the specified bits from the lower 64 bits of the 128-bit integer vector operand at the
 * index `idx` and of the length `len`.
 *
 * `idx` specifies the index of the LSB. `len` specifies the number of bits to extract. If length
 * and index are both zero, bits `[63: 0]` of parameter `x` are extracted. It is a compile-time error
 * for `len + idx` to be greater than 64 or for `len` to be zero and `idx` to be non-zero.
 *
 * The extracted bits are saved in the least-significant bit positions of the lower
 * quadword of the destination; the remaining bits in the lower quadword of the
 * destination register are cleared to 0. The upper quadword of the destination
 * register is undefined.
 */
public fun mmExtractiSi64(x: M128i, len: Int, idx: Int): M128i {
    run {
        check(0 <= len && len < (1 shl 6)) { "LEN doesn't fit in 6 bits (unsigned)" }
    }
    run {
        check(0 <= idx && idx < (1 shl 6)) { "IDX doesn't fit in 6 bits (unsigned)" }
    }
    run {
        check((len == 0 && idx == 0) || (len != 0 && len + idx <= 64))
    }
    return transmute(extrqi(x.asI64x2(), len.toUByte(), idx.toUByte()))
}

/**
 * Inserts the `[length: 0]` bits of `y` into `x` at `index`.
 *
 * The bits of `y`:
 *
 * - `[69: 64]` specify the `length`,
 * - `[77: 72]` specify the index.
 *
 * If the `length` is zero it is interpreted as `64`. If `index + length> 64`
 * or `index> 0 && length == 0` the result is undefined.
 *
 * The upper 64 bits of the destination are undefined.
 */
public fun mmInsertSi64(x: M128i, y: M128i): M128i = transmute(insertq(x.asI64x2(), y.asI64x2()))

/**
 * Inserts the `len` least-significant bits from the lower 64 bits of the 128-bit integer vector operand `y` into
 * the lower 64 bits of the 128-bit integer vector operand `x` at the index `idx` and of the length `len`.
 *
 * `idx` specifies the index of the LSB. `len` specifies the number of bits to insert. If length and index
 * are both zero, bits `[63: 0]` of parameter `x` are replaced with bits `[63: 0]` of parameter `y`. It is a
 * compile-time error for `len + idx` to be greater than 64 or for `len` to be zero and `idx` to be non-zero.
 *
 * The upper 64 bits of the destination are undefined.
 */
public fun mmInsertiSi64(x: M128i, y: M128i, len: Int, idx: Int): M128i {
    run {
        check(0 <= len && len < (1 shl 6)) { "LEN doesn't fit in 6 bits (unsigned)" }
    }
    run {
        check(0 <= idx && idx < (1 shl 6)) { "IDX doesn't fit in 6 bits (unsigned)" }
    }
    run {
        check((len == 0 && idx == 0) || (len != 0 && len + idx <= 64))
    }
    return transmute(insertqi(x.asI64x2(), y.asI64x2(), len.toUByte(), idx.toUByte()))
}

/**
 * Non-temporal store of `a.0` into `p`.
 *
 * Writes 64-bit data to a memory location without polluting the caches.
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamSd(p: MutPtr, a: M128d) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

/**
 * Non-temporal store of `a.0` into `p`.
 *
 * Writes 32-bit data to a memory location without polluting the caches.
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamSs(p: MutPtr, a: M128) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

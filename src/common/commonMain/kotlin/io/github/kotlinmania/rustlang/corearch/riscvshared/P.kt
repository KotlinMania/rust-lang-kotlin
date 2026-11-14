// port-lint: source riscv_shared/p.rs
package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.arch.asm

// RISC-V Packed SIMD intrinsics; shared part.
//
// RV64 only part is placed in riscv64 folder.

// Note: Currently the P extension is still unratified, so there is no support
// for it in the upstream LLVM for now, and thus no LLVM built-in functions or
// serialization of instructions are provided.
//
// We add `assertInstr(unknown)` to each function so that we can at least make
// sure they compile. Since there is no serialization yet, we can only write
// "unknown" here, so that if LLVM upstream provides support for the P extension
// at some point in the future, we can know in time and then update our
// implementation.

/**
 * Adds packed 16-bit signed numbers, discarding overflow bits
 */
public fun add16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x20, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the sum of packed 16-bit signed numbers, dropping least bits
 */
public fun radd16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x00, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the sum of packed 16-bit unsigned numbers, dropping least bits
 */
public fun uradd16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x10, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun kadd16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x08, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukadd16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x18, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 16-bit signed numbers, discarding overflow bits
 */
public fun sub16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x21, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the subtraction result of packed 16-bit signed numbers, dropping least bits
 */
public fun rsub16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x01, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the subtraction result of packed 16-bit unsigned numbers, dropping least bits
 */
public fun ursub16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x11, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun ksub16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x09, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun uksub16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x19, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross adds and subtracts packed 16-bit signed numbers, discarding overflow bits
 */
public fun cras16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x22, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross halves of adds and subtracts packed 16-bit signed numbers, dropping least bits
 */
public fun rcras16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x02, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross halves of adds and subtracts packed 16-bit unsigned numbers, dropping least bits
 */
public fun urcras16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x12, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross adds and subtracts packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun kcras16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross adds and subtracts packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukcras16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross subtracts and adds packed 16-bit signed numbers, discarding overflow bits
 */
public fun crsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x23, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross halves of subtracts and adds packed 16-bit signed numbers, dropping least bits
 */
public fun rcrsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x03, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross halves of subtracts and adds packed 16-bit unsigned numbers, dropping least bits
 */
public fun urcrsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x13, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross subtracts and adds packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun kcrsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Cross subtracts and adds packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukcrsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight adds and subtracts packed 16-bit signed numbers, discarding overflow bits
 */
public fun stas16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x7A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight halves of adds and subtracts packed 16-bit signed numbers, dropping least bits
 */
public fun rstas16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x5A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight halves of adds and subtracts packed 16-bit unsigned numbers, dropping least bits
 */
public fun urstas16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x6A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight adds and subtracts packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun kstas16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x62, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight adds and subtracts packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukstas16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x72, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight subtracts and adds packed 16-bit signed numbers, discarding overflow bits
 */
public fun stsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x7B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight halves of subtracts and adds packed 16-bit signed numbers, dropping least bits
 */
public fun rstsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x5B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight halves of subtracts and adds packed 16-bit unsigned numbers, dropping least bits
 */
public fun urstsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x6B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight subtracts and adds packed 16-bit signed numbers, saturating at the numeric bounds
 */
public fun kstsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x63, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Straight subtracts and adds packed 16-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukstsa16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x73, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds packed 8-bit signed numbers, discarding overflow bits
 */
public fun add8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x24, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the sum of packed 8-bit signed numbers, dropping least bits
 */
public fun radd8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x04, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the sum of packed 8-bit unsigned numbers, dropping least bits
 */
public fun uradd8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x14, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds packed 8-bit signed numbers, saturating at the numeric bounds
 */
public fun kadd8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0C, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds packed 8-bit unsigned numbers, saturating at the numeric bounds
 */
public fun ukadd8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1C, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 8-bit signed numbers, discarding overflow bits
 */
public fun sub8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x25, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the subtraction result of packed 8-bit signed numbers, dropping least bits
 */
public fun rsub8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x05, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Halves the subtraction result of packed 8-bit unsigned numbers, dropping least bits
 */
public fun ursub8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x15, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 8-bit signed numbers, saturating at the numeric bounds
 */
public fun ksub8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0D, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts packed 8-bit unsigned numbers, saturating at the numeric bounds
 */
public fun uksub8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1D, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Arithmetic right shift packed 16-bit elements without rounding up
 */
public fun sra16(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x28, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Arithmetic right shift packed 16-bit elements with rounding up
 */
public fun sra16u(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x30, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical right shift packed 16-bit elements without rounding up
 */
public fun srl16(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x29, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical right shift packed 16-bit elements with rounding up
 */
public fun srl16u(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x31, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical left shift packed 16-bit elements, discarding overflow bits
 */
public fun sll16(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical left shift packed 16-bit elements, saturating at the numeric bounds
 */
public fun ksll16(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x32, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical saturating left then arithmetic right shift packed 16-bit elements
 */
public fun kslra16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical saturating left then arithmetic right shift packed 16-bit elements
 */
public fun kslra16u(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x33, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Arithmetic right shift packed 8-bit elements without rounding up
 */
public fun sra8(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2C, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Arithmetic right shift packed 8-bit elements with rounding up
 */
public fun sra8u(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x34, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical right shift packed 8-bit elements without rounding up
 */
public fun srl8(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2D, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical right shift packed 8-bit elements with rounding up
 */
public fun srl8u(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x35, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical left shift packed 8-bit elements, discarding overflow bits
 */
public fun sll8(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2E, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical left shift packed 8-bit elements, saturating at the numeric bounds
 */
public fun ksll8(a: Int, b: UInt): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x36, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical saturating left then arithmetic right shift packed 8-bit elements
 */
public fun kslra8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x2F, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Logical saturating left then arithmetic right shift packed 8-bit elements
 */
public fun kslra8u(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x37, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare equality for packed 16-bit elements
 */
public fun cmpeq16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x26, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 16-bit packed signed integers are less than the others
 */
public fun scmplt16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x06, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 16-bit packed signed integers are less than or equal to the others
 */
public fun scmple16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0E, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 16-bit packed unsigned integers are less than the others
 */
public fun ucmplt16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x16, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 16-bit packed unsigned integers are less than or equal to the others
 */
public fun ucmple16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1E, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare equality for packed 8-bit elements
 */
public fun cmpeq8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x27, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 8-bit packed signed integers are less than the others
 */
public fun scmplt8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x07, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 8-bit packed signed integers are less than or equal to the others
 */
public fun scmple8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x0F, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 8-bit packed unsigned integers are less than the others
 */
public fun ucmplt8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x17, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Compare whether 8-bit packed unsigned integers are less than or equal to the others
 */
public fun ucmple8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x1F, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get minimum values from 16-bit packed signed integers
 */
public fun smin16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x40, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get minimum values from 16-bit packed unsigned integers
 */
public fun umin16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x48, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get maximum values from 16-bit packed signed integers
 */
public fun smax16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x41, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get maximum values from 16-bit packed unsigned integers
 */
public fun umax16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x49, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

// Note: sclip16, uclip16

/**
 * Compute the absolute value of packed 16-bit signed integers
 */
public fun kabs16(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAD1)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of redundant sign bits of the packed 16-bit elements
 */
public fun clrs16(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAE8)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of leading zero bits of the packed 16-bit elements
 */
public fun clz16(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAE9)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Swap the 16-bit halfwords within each 32-bit word of a register
 */
public fun swap16(a: Int): Int {
    val value: Int
    // this instruction is an alias for `pkbt rd, rs1, rs1`.
    asm(".insn r 0x77, 0x0, 0x0F, {}, {}, {}", lateout, reg, value, reg, a, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Get minimum values from 8-bit packed signed integers
 */
public fun smin8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x44, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get minimum values from 8-bit packed unsigned integers
 */
public fun umin8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x4C, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get maximum values from 8-bit packed signed integers
 */
public fun smax8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x45, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Get maximum values from 8-bit packed unsigned integers
 */
public fun umax8(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x4D, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

// Note: sclip8, uclip8

/**
 * Compute the absolute value of packed 8-bit signed integers
 */
public fun kabs8(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAD0)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of redundant sign bits of the packed 8-bit elements
 */
public fun clrs8(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAE0)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of leading zero bits of the packed 8-bit elements
 */
public fun clz8(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAE1)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Swap the 8-bit bytes within each 16-bit halfword of a register.
 */
public fun swap8(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAD8)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack first and zeroth into two 16-bit signed halfwords in each 32-bit chunk
 */
public fun sunpkd810(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAC8)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack second and zeroth into two 16-bit signed halfwords in each 32-bit chunk
 */
public fun sunpkd820(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAC9)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and zeroth into two 16-bit signed halfwords in each 32-bit chunk
 */
public fun sunpkd830(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACA)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and first into two 16-bit signed halfwords in each 32-bit chunk
 */
public fun sunpkd831(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACB)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and second into two 16-bit signed halfwords in each 32-bit chunk
 */
public fun sunpkd832(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAD3)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack first and zeroth into two 16-bit unsigned halfwords in each 32-bit chunk
 */
public fun zunpkd810(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACC)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack second and zeroth into two 16-bit unsigned halfwords in each 32-bit chunk
 */
public fun zunpkd820(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACD)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and zeroth into two 16-bit unsigned halfwords in each 32-bit chunk
 */
public fun zunpkd830(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACE)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and first into two 16-bit unsigned halfwords in each 32-bit chunk
 */
public fun zunpkd831(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xACF)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Unpack third and second into two 16-bit unsigned halfwords in each 32-bit chunk
 */
public fun zunpkd832(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAD7)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

// Note: pkbb16, pktt16

/**
 * Pack two 16-bit data from bottom and top half from 32-bit chunks
 */
public fun pkbt16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x0F, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Pack two 16-bit data from top and bottom half from 32-bit chunks
 */
public fun pktb16(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x1F, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of redundant sign bits of the packed 32-bit elements
 */
public fun clrs32(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAF8)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Count the number of leading zero bits of the packed 32-bit elements
 */
public fun clz32(a: Int): Int {
    val value: Int
    asm(".insn i 0x77, 0x0, {}, {}, %lo(0xAF9)", lateout, reg, value, reg, a, options(pure, nomem, nostack))
    return value
}

/**
 * Calculate the sum of absolute difference of unsigned 8-bit data elements
 */
public fun pbsad(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x0, 0x7E, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Calculate and accumulate the sum of absolute difference of unsigned 8-bit data elements
 */
public fun pbsada(t: Int, a: Int, b: Int): Int {
    var value: Int
    asm(".insn r 0x77, 0x0, 0x7F, {}, {}, {}", inlateout, reg, t, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Multiply signed 8-bit elements and add 16-bit elements on results for packed 32-bit chunks
 */
public fun smaqa(t: Int, a: Int, b: Int): Int {
    var value: Int
    asm(".insn r 0x77, 0x0, 0x64, {}, {}, {}", inlateout, reg, t, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Multiply unsigned 8-bit elements and add 16-bit elements on results for packed 32-bit chunks
 */
public fun umaqa(t: Int, a: Int, b: Int): Int {
    var value: Int
    asm(".insn r 0x77, 0x0, 0x66, {}, {}, {}", inlateout, reg, t, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Multiply signed to unsigned 8-bit and add 16-bit elements on results for packed 32-bit chunks
 */
public fun smaqasu(t: Int, a: Int, b: Int): Int {
    var value: Int
    asm(".insn r 0x77, 0x0, 0x65, {}, {}, {}", inlateout, reg, t, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds signed lower 16-bit content of two registers with Q15 saturation
 */
public fun kaddh(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x02, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts signed lower 16-bit content of two registers with Q15 saturation
 */
public fun ksubh(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x03, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Adds signed lower 16-bit content of two registers with U16 saturation
 */
public fun ukaddh(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x0A, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

/**
 * Subtracts signed lower 16-bit content of two registers with U16 saturation
 */
public fun uksubh(a: Int, b: Int): Int {
    val value: Int
    asm(".insn r 0x77, 0x1, 0x0B, {}, {}, {}", lateout, reg, value, reg, a, reg, b, options(pure, nomem, nostack))
    return value
}

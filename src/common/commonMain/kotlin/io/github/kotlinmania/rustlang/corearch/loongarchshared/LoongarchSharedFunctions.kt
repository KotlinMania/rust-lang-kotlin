package io.github.kotlinmania.rustlang.corearch.loongarchshared

/**
 * Reads the lower 32-bit stable counter value and the counter ID
 */
public fun rdtimelW(): Pair<Int, Int> {
    val (value: Int, tid: Int)
    return Pair(value, tid)
}

/**
 * Reads the upper 32-bit stable counter value and the counter ID
 */
public fun rdtimehW(): Pair<Int, Int> {
    val (value: Int, tid: Int)
    return Pair(value, tid)
}

public expect fun dbar(a: Int)

public expect fun ibar(a: Int)

public expect fun movgr2fcsr(a: Int, b: Int)

public expect fun movfcsr2gr(a: Int): Int

public expect fun iocsrrdB(a: Int): Int

public expect fun iocsrrdH(a: Int): Int

public expect fun iocsrrdW(a: Int): Int

public expect fun iocsrwrB(a: Int, b: Int)

public expect fun iocsrwrH(a: Int, b: Int)

public expect fun iocsrwrW(a: Int, b: Int)

public expect fun breakTarget(a: Int)

public expect fun cpucfg(a: Int): Int

public expect fun syscall(a: Int)

public expect fun frecipeS(a: Float): Float

public expect fun frecipeD(a: Double): Double

public expect fun frsqrteS(a: Float): Float

public expect fun frsqrteD(a: Double): Double

/**
 * Generates the memory barrier instruction
 */
public fun dbar(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    dbar(imm15)
}

/**
 * Generates the instruction-fetch barrier instruction
 */
public fun ibar(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    ibar(imm15)
}

/**
 * Moves data from a GPR to the FCSR
 */
public fun movgr2fcsr(a: Int, imm2: Int) {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    movgr2fcsr(imm2, a)
}

/**
 * Moves data from a FCSR to the GPR
 */
public fun movfcsr2gr(imm2: Int): Int {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return movfcsr2gr(imm2)
}

/**
 * Reads the 8-bit IO-CSR
 */
public fun iocsrrdB(a: Int): Int = iocsrrdB(a)

/**
 * Reads the 16-bit IO-CSR
 */
public fun iocsrrdH(a: Int): Int = iocsrrdH(a)

/**
 * Reads the 32-bit IO-CSR
 */
public fun iocsrrdW(a: Int): Int = iocsrrdW(a)

/**
 * Writes the 8-bit IO-CSR
 */
public fun iocsrwrB(a: Int, b: Int) {
    iocsrwrB(a, b)
}

/**
 * Writes the 16-bit IO-CSR
 */
public fun iocsrwrH(a: Int, b: Int) {
    iocsrwrH(a, b)
}

/**
 * Writes the 32-bit IO-CSR
 */
public fun iocsrwrW(a: Int, b: Int) {
    iocsrwrW(a, b)
}

/**
 * Generates the breakpoint instruction
 */
public fun brk(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    breakTarget(imm15)
}

/**
 * Reads the CPU configuration register
 */
public fun cpucfg(a: Int): Int = cpucfg(a)

/**
 * Generates the syscall instruction
 */
public fun syscall(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    syscall(imm15.toLong())
}

/**
 * Calculate the approximate single-precision result of 1.0 divided
 */
public fun frecipeS(a: Float): Float = frecipeS(a)

/**
 * Calculate the approximate double-precision result of 1.0 divided
 */
public fun frecipeD(a: Double): Double = frecipeD(a)

/**
 * Calculate the approximate single-precision result of dividing 1.0 by the square root
 */
public fun frsqrteS(a: Float): Float = frsqrteS(a)

/**
 * Calculate the approximate double-precision result of dividing 1.0 by the square root
 */
public fun frsqrteD(a: Double): Double = frsqrteD(a)

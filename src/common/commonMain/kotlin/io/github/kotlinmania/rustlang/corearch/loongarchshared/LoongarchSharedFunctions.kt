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

public expect fun externDbar(a: Int)

public expect fun externIbar(a: Int)

public expect fun externMovgr2fcsr(a: Int, b: Int)

public expect fun externMovfcsr2gr(a: Int): Int

public expect fun externIocsrrdB(a: Int): Int

public expect fun externIocsrrdH(a: Int): Int

public expect fun externIocsrrdW(a: Int): Int

public expect fun externIocsrwrB(a: Int, b: Int)

public expect fun externIocsrwrH(a: Int, b: Int)

public expect fun externIocsrwrW(a: Int, b: Int)

public expect fun breakTarget(a: Int)

public expect fun externCpucfg(a: Int): Int

public expect fun externSyscall(a: Int)

public expect fun externFrecipeS(a: Float): Float

public expect fun externFrecipeD(a: Double): Double

public expect fun externFrsqrteS(a: Float): Float

public expect fun externFrsqrteD(a: Double): Double

/**
 * Generates the memory barrier instruction
 */
public fun dbar(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    externDbar(imm15)
}

/**
 * Generates the instruction-fetch barrier instruction
 */
public fun ibar(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    externIbar(imm15)
}

/**
 * Moves data from a GPR to the FCSR
 */
public fun movgr2fcsr(a: Int, imm2: Int) {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    externMovgr2fcsr(imm2, a)
}

/**
 * Moves data from a FCSR to the GPR
 */
public fun movfcsr2gr(imm2: Int): Int {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMovfcsr2gr(imm2)
}

/**
 * Reads the 8-bit IO-CSR
 */
public fun iocsrrdB(a: Int): Int = externIocsrrdB(a)

/**
 * Reads the 16-bit IO-CSR
 */
public fun iocsrrdH(a: Int): Int = externIocsrrdH(a)

/**
 * Reads the 32-bit IO-CSR
 */
public fun iocsrrdW(a: Int): Int = externIocsrrdW(a)

/**
 * Writes the 8-bit IO-CSR
 */
public fun iocsrwrB(a: Int, b: Int) {
    externIocsrwrB(a, b)
}

/**
 * Writes the 16-bit IO-CSR
 */
public fun iocsrwrH(a: Int, b: Int) {
    externIocsrwrH(a, b)
}

/**
 * Writes the 32-bit IO-CSR
 */
public fun iocsrwrW(a: Int, b: Int) {
    externIocsrwrW(a, b)
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
public fun cpucfg(a: Int): Int = externCpucfg(a)

/**
 * Generates the syscall instruction
 */
public fun syscall(imm15: Int) {
    run {
        check(0 <= imm15 && imm15 < (1 shl 15)) { "IMM15 doesn't fit in 15 bits (unsigned)" }
    }
    externSyscall(imm15)
}

/**
 * Calculate the approximate single-precision result of 1.0 divided
 */
public fun frecipeS(a: Float): Float = externFrecipeS(a)

/**
 * Calculate the approximate double-precision result of 1.0 divided
 */
public fun frecipeD(a: Double): Double = externFrecipeD(a)

/**
 * Calculate the approximate single-precision result of dividing 1.0 by the square root
 */
public fun frsqrteS(a: Float): Float = externFrsqrteS(a)

/**
 * Calculate the approximate double-precision result of dividing 1.0 by the square root
 */
public fun frsqrteD(a: Double): Double = externFrsqrteD(a)

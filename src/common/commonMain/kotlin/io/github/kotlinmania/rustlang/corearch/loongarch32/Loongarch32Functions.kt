package io.github.kotlinmania.rustlang.corearch.loongarch32

public expect fun cacop(a: Int, b: Int, c: Int)

public expect fun csrrd(a: Int): Int

public expect fun csrwr(a: Int, b: Int): Int

public expect fun csrxchg(a: Int, b: Int, c: Int): Int

/**
 * Generates the cache operation instruction
 */
public fun cacop(b: Int, imm5: Int, immS12: Int) {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    cacop(imm5, b, immS12)
}

/**
 * Reads the CSR
 */
public fun csrrd(imm14: Int): Int {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrrd(imm14)
}

/**
 * Writes the CSR
 */
public fun csrwr(a: Int, imm14: Int): Int {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrwr(a, imm14)
}

/**
 * Exchanges the CSR
 */
public fun csrxchg(a: Int, b: Int, imm14: Int): Int {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrxchg(a, b, imm14)
}

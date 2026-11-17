package io.github.kotlinmania.rustlang.corearch.loongarch64

/**
 * Reads the 64-bit stable counter value and the counter ID
 */
public fun rdtimeD(): Pair<Long, Int> {
    val (value: Long, tid: Int)
    return Pair(value, tid)
}

public expect fun externCrcWBW(a: Int, b: Int): Int

public expect fun externCrcWHW(a: Int, b: Int): Int

public expect fun externCrcWWW(a: Int, b: Int): Int

public expect fun externCrcWDW(a: Long, b: Int): Int

public expect fun externCrccWBW(a: Int, b: Int): Int

public expect fun externCrccWHW(a: Int, b: Int): Int

public expect fun externCrccWWW(a: Int, b: Int): Int

public expect fun externCrccWDW(a: Long, b: Int): Int

public expect fun externCacop(a: Long, b: Long, c: Long)

public expect fun externCsrrd(a: Int): Long

public expect fun externCsrwr(a: Long, b: Int): Long

public expect fun externCsrxchg(a: Long, b: Long, c: Int): Long

public expect fun externIocsrrdD(a: Int): Long

public expect fun externIocsrwrD(a: Long, b: Int)

public expect fun externAsrtle(a: Long, b: Long)

public expect fun externAsrtgt(a: Long, b: Long)

public expect fun externLddir(a: Long, b: Long): Long

public expect fun externLdpte(a: Long, b: Long)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWBW(a: Byte, b: Int): Int = externCrcWBW(a.toInt(), b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWHW(a: Short, b: Int): Int = externCrcWHW(a.toInt(), b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWWW(a: Int, b: Int): Int = externCrcWWW(a, b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWDW(a: Long, b: Int): Int = externCrcWDW(a, b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWBW(a: Byte, b: Int): Int = externCrccWBW(a.toInt(), b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWHW(a: Short, b: Int): Int = externCrccWHW(a.toInt(), b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWWW(a: Int, b: Int): Int = externCrccWWW(a, b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWDW(a: Long, b: Int): Int = externCrccWDW(a, b)

/**
 * Generates the cache operation instruction
 */
public fun cacop(b: Long, imm5: Long, immS12: Long) {
    run {
        check(0L <= imm5 && imm5 < (1L shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    run {
        check((-1L shl (12 - 1).toInt()) - 1L <= immS12 && immS12 < (1L shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    externCacop(imm5, b, immS12)
}

/**
 * Reads the CSR
 */
public fun csrrd(imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return externCsrrd(imm14)
}

/**
 * Writes the CSR
 */
public fun csrwr(a: Long, imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return externCsrwr(a, imm14)
}

/**
 * Exchanges the CSR
 */
public fun csrxchg(a: Long, b: Long, imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return externCsrxchg(a, b, imm14)
}

/**
 * Reads the 64-bit IO-CSR
 */
public fun iocsrrdD(a: Int): Long = externIocsrrdD(a)

/**
 * Writes the 64-bit IO-CSR
 */
public fun iocsrwrD(a: Long, b: Int) {
    externIocsrwrD(a, b)
}

/**
 * Generates the less-than-or-equal assertion instruction
 */
public fun asrtle(a: Long, b: Long) {
    externAsrtle(a, b)
}

/**
 * Generates the greater-than assertion instruction
 */
public fun asrtgt(a: Long, b: Long) {
    externAsrtgt(a, b)
}

/**
 * Loads the page table directory entry
 */
public fun lddir(a: Long, imm8: Long): Long {
    run {
        check(0L <= imm8 && imm8 < (1L shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externLddir(a, imm8)
}

/**
 * Loads the page table entry
 */
public fun ldpte(a: Long, imm8: Long) {
    run {
        check(0L <= imm8 && imm8 < (1L shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    externLdpte(a, imm8)
}

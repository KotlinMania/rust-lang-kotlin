package io.github.kotlinmania.rustlang.corearch.loongarch64

/**
 * Reads the 64-bit stable counter value and the counter ID
 */
public fun rdtimeD(): Pair<Long, Int> {
    val (value: Long, tid: Int)
    return Pair(value, tid)
}

public expect fun crcWBW(a: Int, b: Int): Int

public expect fun crcWHW(a: Int, b: Int): Int

public expect fun crcWWW(a: Int, b: Int): Int

public expect fun crcWDW(a: Long, b: Int): Int

public expect fun crccWBW(a: Int, b: Int): Int

public expect fun crccWHW(a: Int, b: Int): Int

public expect fun crccWWW(a: Int, b: Int): Int

public expect fun crccWDW(a: Long, b: Int): Int

public expect fun cacop(a: Long, b: Long, c: Long)

public expect fun csrrd(a: Int): Long

public expect fun csrwr(a: Long, b: Int): Long

public expect fun csrxchg(a: Long, b: Long, c: Int): Long

public expect fun iocsrrdD(a: Int): Long

public expect fun iocsrwrD(a: Long, b: Int)

public expect fun asrtle(a: Long, b: Long)

public expect fun asrtgt(a: Long, b: Long)

public expect fun lddir(a: Long, b: Long): Long

public expect fun ldpte(a: Long, b: Long)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWBW(a: Byte, b: Int): Int = crcWBW(a.toInt(), b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWHW(a: Short, b: Int): Int = crcWHW(a.toInt(), b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWWW(a: Int, b: Int): Int = crcWWW(a, b)

/**
 * Calculate the CRC value using the IEEE 802.3 polynomial (0xEDB88320)
 */
public fun crcWDW(a: Long, b: Int): Int = crcWDW(a, b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWBW(a: Byte, b: Int): Int = crccWBW(a.toInt(), b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWHW(a: Short, b: Int): Int = crccWHW(a.toInt(), b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWWW(a: Int, b: Int): Int = crccWWW(a, b)

/**
 * Calculate the CRC value using the Castagnoli polynomial (0x82F63B78)
 */
public fun crccWDW(a: Long, b: Int): Int = crccWDW(a, b)

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
    cacop(imm5, b, immS12)
}

/**
 * Reads the CSR
 */
public fun csrrd(imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrrd(imm14)
}

/**
 * Writes the CSR
 */
public fun csrwr(a: Long, imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrwr(a, imm14)
}

/**
 * Exchanges the CSR
 */
public fun csrxchg(a: Long, b: Long, imm14: Int): Long {
    run {
        check(0 <= imm14 && imm14 < (1 shl 14)) { "IMM14 doesn't fit in 14 bits (unsigned)" }
    }
    return csrxchg(a, b, imm14)
}

/**
 * Reads the 64-bit IO-CSR
 */
public fun iocsrrdD(a: Int): Long = iocsrrdD(a)

/**
 * Writes the 64-bit IO-CSR
 */
public fun iocsrwrD(a: Long, b: Int) {
    iocsrwrD(a, b)
}

/**
 * Generates the less-than-or-equal assertion instruction
 */
public fun asrtle(a: Long, b: Long) {
    asrtle(a, b)
}

/**
 * Generates the greater-than assertion instruction
 */
public fun asrtgt(a: Long, b: Long) {
    asrtgt(a, b)
}

/**
 * Loads the page table directory entry
 */
public fun lddir(a: Long, imm8: Long): Long {
    run {
        check(0L <= imm8 && imm8 < (1L shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return lddir(a, imm8)
}

/**
 * Loads the page table entry
 */
public fun ldpte(a: Long, imm8: Long) {
    run {
        check(0L <= imm8 && imm8 < (1L shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    ldpte(a, imm8)
}

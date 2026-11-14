// port-lint: source mem/x86_64.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.mem.x8664

import io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.cmp
import io.github.kotlinmania.rustlang.core.ffi.CChar
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustTrailingZeros
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun copyForward(dest: MutPtr, src: ConstPtr, count: Int) {
    asm("rep movsb [rdi], [rsi]", inout, "rcx", count, inout, "rdi", dest, inout, "rsi", src, options(nostack, preservesFlags))
}

public fun copyBackward(dest: MutPtr, src: ConstPtr, count: Int) {
    val (preByteCount, qwordCount, byteCount) = repParam(dest, count)
    // We can't separate this block due to std/cld
    asm("std", "rep movsb", "sub rsi, 7", "sub rdi, 7", "mov rcx, {qword_count:r}", "rep movsq", "add rsi, 7", "add rdi, 7", "mov ecx, {pre_byte_count:e}", "rep movsb", "cld", preByteCount, reg, preByteCount, qwordCount, reg, qwordCount, inout, "ecx", byteCount, inout, "rdi", dest, add, count, 1, inout, "rsi", src, add, count, 1, options(nostack))
}

public fun setBytes(dest: MutPtr, c: UByte, count: Int) {
    asm("rep stosb [rdi], al", inout, "rcx", count, inout, "rdi", dest, inout, "al", c, options(nostack, preservesFlags))
}

public fun compareBytes(a: ConstPtr, b: ConstPtr, n: Int): Int {
    val a1 = a
    val b1 = b
    fun <T> cmp(a2: ConstPtr, b2: ConstPtr, n1: Int, f: (ConstPtr, ConstPtr, Int) -> Int, typeLayoutT: TypeLayout<T>): Int {
        var a23 = a2
        var b23 = b2
        // Ensure T is not a ZST.
        check(mem.sizeOf<T>(typeLayoutT = typeLayoutT) != 0)
        val end = ConstPtr((a23).rawAddress + ((intrinsics.uncheckedDiv(n1, mem.sizeOf<T>(typeLayoutT = typeLayoutT))).toLong() * (typeLayoutT).strideBytes))
        while (((a23).rawAddress.toULong() != (end).rawAddress.toULong())) {
            if ((typeLayoutT).readNative((a23).rawAddress) != (typeLayoutT).readNative((b23).rawAddress)) {
                return f(a23, b23, mem.sizeOf<T>(typeLayoutT = typeLayoutT))
            }
            a23 = ConstPtr((a23).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
            b23 = ConstPtr((b23).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
        }
        return f(a23, b23, intrinsics.uncheckedRem(n1, mem.sizeOf<T>(typeLayoutT = typeLayoutT)))
    }
    val c1 =
        closure@{ a2: ConstPtr, b2: ConstPtr, n1 ->
            for (unused in 0 until n1) {
                if (a2.read() != b2.read()) {
                    return@closure Int.from(a2.read()) - Int.from(b2.read())
                }
                a2 = a2.add(1)
                b2 = b2.add(1)
            }
            0
        }
    val c2 = { a2: ConstPtr, b2, n1 -> cmp(a2, b2, n1, c1) }
    val c4 = { a2: ConstPtr, b2, n1 -> cmp(a2, b2, n1, c2) }
    val c8 = { a2: ConstPtr, b2, n1 -> cmp(a2, b2, n1, c4) }
    val c16 = { a2: ConstPtr, b2, n1 -> cmp(a2, b2, n1, c8) }
    return c16(a1, b1, n)
}

// In order to process more than one byte simultaneously when executing strlen,
// two things must be considered:
// * An n byte read with an n-byte aligned address will never cross
// a page boundary and will always succeed. Any smaller alignment
// may result in a read that will cross a page boundary, which may
// trigger an access violation.
// * Surface Rust considers any kind of out-of-bounds read as undefined
// behaviour. To dodge this, memory access operations are written
// using inline assembly.
public fun cStringLength(s: ConstPtr): Int {
    var s1 = s
    var n = 0
    // The use of _mm_movemask_epi8 and company allow for speedups,
    // but they aren't cheap by themselves. Thus, possibly small strings
    // are handled in simple loops.
    for (unused in 0 until 4) {
        if ((ObjectSlotLayout<CChar>()).readNative((s1).rawAddress) == 0u.toUByte()) {
            return n
        }
        n += 1
        s1 = ConstPtr((s1).rawAddress + ((1).toLong() * (ObjectSlotLayout<CChar>()).strideBytes))
    }
    // Shave off the least significant bits to align the address to a 16
    // byte boundary. The shaved off bits are used to correct the first iteration.
    val align = s1.rawAddress.toInt() and 15
    var s2 = ConstPtr(((s1.rawAddress.toInt()) - align).toLong())
    val zero = mmSet1Epi8(0)
    val x = run {
        val r: Any?
        asm("movdqa {dest}, [{addr:r}]", addr, reg, s2, dest, out, xmmReg, r, options(nostack, preservesFlags))
        r
    }
    val cmp = mmMovemaskEpi8(mmCmpeqEpi8(x, zero)) shr align
    if (cmp != 0) {
        return n + cmp.trailingZeros().toInt()
    }
    n += 16 - align
    s2 = ConstPtr((s2).rawAddress + ((1).toLong() * (ObjectSlotLayout<M128i>()).strideBytes))
    while (true) {
        val x1 = run {
            val r1: Any?
            asm("movdqa {dest}, [{addr:r}]", addr, reg, s2, dest, out, xmmReg, r1, options(nostack, preservesFlags))
            r1
        }
        val cmp1 = mmMovemaskEpi8(mmCmpeqEpi8(x1, zero)).toUInt()
        if (cmp1 == 0u) {
            n += 16
            s2 = ConstPtr((s2).rawAddress + ((1).toLong() * (ObjectSlotLayout<M128i>()).strideBytes))
        } else {
            return n + rustTrailingZeros(cmp1).toInt()
        }
    }
}

/**
 * Determine optimal parameters for a `rep` instruction.
 */
public fun repParam(dest: MutPtr, count: Int): Triple<Int, Int, Int> {
    var count1 = count
    // Unaligned writes are still slow on modern processors, so align the destination address.
    val preByteCount = ((8 - (dest.rawAddress.toInt() and 0x7)) and 0x7).min(count1)
    count1 -= preByteCount
    val qwordCount = count1 shr 3
    val byteCount = count1 and 0x7
    return Triple(preByteCount, qwordCount, byteCount)
}

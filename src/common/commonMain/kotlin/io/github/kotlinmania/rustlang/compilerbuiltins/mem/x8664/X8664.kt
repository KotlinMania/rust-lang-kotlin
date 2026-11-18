// port-lint: source mem/x86_64.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.mem.x8664

import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.trailingZeros
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read

public fun copyForward(dest: MutPtr, src: ConstPtr, count: Int) {
    Unit
}

public fun copyBackward(dest: MutPtr, src: ConstPtr, count: Int) {
    val (preByteCount, qwordCount, byteCount) = repParam(dest, count)
    // We can't separate this block due to std/cld
    Unit
}

public fun setBytes(dest: MutPtr, c: UByte, count: Int) {}

public fun compareBytes(a: ConstPtr, b: ConstPtr, n: Int): Int {
    val a1 = a
    val b1 = b
    fun <T> cmp(a2: ConstPtr, b2: ConstPtr, n1: Int, f: (ConstPtr, ConstPtr, Int) -> Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
        var a23 = a2
        var b23 = b2
        // Ensure T is not a ZST.
        check(sizeOf<T>(typeLayoutT = typeLayoutT) != 0)
        val end = ConstPtr((a23).rawAddress + ((((n1) / (sizeOf<T>(typeLayoutT = typeLayoutT)))).toLong() * (typeLayoutT).strideBytes))
        while (((a23).rawAddress.toULong() != (end).rawAddress.toULong())) {
            if ((typeLayoutT).readNative((a23).rawAddress) != (typeLayoutT).readNative((b23).rawAddress)) {
                return f(a23, b23, sizeOf<T>(typeLayoutT = typeLayoutT))
            }
            a23 = ConstPtr((a23).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
            b23 = ConstPtr((b23).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
        }
        return f(a23, b23, ((n1) % (sizeOf<T>(typeLayoutT = typeLayoutT))))
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
        if ((UnresolvedTypeLayout<CChar>()).readNative((s1).rawAddress) == 0u.toUByte()) {
            return n
        }
        n += 1
        s1 = ConstPtr((s1).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<CChar>()).strideBytes))
    }
    // Shave off the least significant bits to align the address to a 16
    // byte boundary. The shaved off bits are used to correct the first iteration.
    val align = s1.rawAddress.toInt() and 15
    var s2 = ConstPtr(((s1.rawAddress.toInt()) - align).toLong())
    val zero = mmSet1Epi8(0)
    val x = run {
        val r: Any?
        Unit
        r
    }
    val cmp = mmMovemaskEpi8(mmCmpeqEpi8(x, zero)) shr align
    if (cmp != 0) {
        return n + cmp.trailingZeros().toInt()
    }
    n += 16 - align
    s2 = ConstPtr((s2).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<M128i>()).strideBytes))
    while (true) {
        val x1 = run {
            val r1: Any?
            Unit
            r1
        }
        val cmp1 = mmMovemaskEpi8(mmCmpeqEpi8(x1, zero)).toUInt()
        if (cmp1 == 0u) {
            n += 16
            s2 = ConstPtr((s2).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<M128i>()).strideBytes))
        } else {
            return n + cmp1.trailingZeros().toInt()
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

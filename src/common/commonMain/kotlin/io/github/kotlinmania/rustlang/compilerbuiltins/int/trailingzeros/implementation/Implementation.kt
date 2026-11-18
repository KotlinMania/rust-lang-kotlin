// port-lint: source int/trailing_zeros.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.trailingzeros.implementation

import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

/**
 * Returns number of trailing binary zeros in `x`.
 */
public fun <I : Int> trailingZeros(x: I): Int {
    val x1 = x
    var x2 = x1
    var r: UInt = 0u
    var t: UInt
    check(x.BITS <= 64)
    if (x.BITS >= 64) {
        r += ((if (UInt.castFromLossy(x2) == 0) 1u else 0u)) shl 5
        // if (x has no 32 small bits) t = 32 else 0
        x2 = x2 shr r.toInt()
        // remove 32 zero bits
    }
    if (x.BITS >= 32) {
        t = ((if (UShort.castFromLossy(x2) == 0) 1u else 0u)) shl 4
        // if (x has no 16 small bits) t = 16 else 0
        r += t
        x2 = x2 shr t.toInt()
        // x = [0 - 0xFFFF] + higher garbage bits
    }
    check(x.BITS >= 16)
    t = ((if (UByte.castFromLossy(x2) == 0) 1u else 0u)) shl 3
    x2 = x2 shr t.toInt()
    // x = [0 - 0xFF] + higher garbage bits
    r += t
    var x3: UByte = x2.castLossy()
    t = ((if ((x3 and 0x0Fu.toUByte()) == 0u.toUByte()) 1u else 0u)) shl 2
    x3 = ((x3).toUInt() shr t.toInt()).toUByte()
    // x = [0 - 0xF] + higher garbage bits
    r += t
    t = ((if ((x3 and 0x3u.toUByte()) == 0u.toUByte()) 1u else 0u)) shl 1
    x3 = ((x3).toUInt() shr t.toInt()).toUByte()
    // x = [0 - 0x3] + higher garbage bits
    r += t
    x3 = x3 and 3u.toUByte()
    return r.toInt() + ((2 - ((x3.toUInt() shr 1).toUByte()).toInt()) and ((if ((x3 and 1u.toUByte()) == 0u.toUByte()) 1 else 0)).wrappingNeg())
}

// port-lint: source int/leading_zeros.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.leadingzeros.implementation

import io.github.kotlinmania.rustlang.support.Int

/**
 * Returns the number of leading binary zeros in `x`.
 */
public fun <I : Int> leadingZerosDefault(x: I): Int {
    val x1 = x
    // The basic idea is to test if the higher bits of `x` are zero and bisect the number
    // of leading zeros. It is possible for all branches of the bisection to use the same
    // code path by conditionally shifting the higher parts down to let the next bisection
    // step work on the higher or lower parts of `x`. Instead of starting with `z == 0`
    // and adding to the number of zeros, it is slightly faster to start with
    // `z == Int.MAX.countOnes()` and subtract from the potential number of zeros,
    // because it simplifies the final bisection step.
    var x2 = x1
    // the number of potential leading zeros
    var z = I.BITS.toInt()
    // a temporary
    var t: I
    check(I.BITS <= 64)
    if (I.BITS >= 64) {
        t = x2 shr 32
        if (t != I.ZERO) {
            z -= 32
            x2 = t
        }
    }
    if (I.BITS >= 32) {
        t = x2 shr 16
        if (t != I.ZERO) {
            z -= 16
            x2 = t
        }
    }
    check(I.BITS >= 16)
    t = x2 shr 8
    if (t != I.ZERO) {
        z -= 8
        x2 = t
    }
    t = x2 shr 4
    if (t != I.ZERO) {
        z -= 4
        x2 = t
    }
    t = x2 shr 2
    if (t != I.ZERO) {
        z -= 2
        x2 = t
    }
    // the last two bisections are combined into one conditional
    t = x2 shr 1
    return if (t != I.ZERO) {
        z - 2
    } else {
        z - Int.castFrom(x2)
    }
    // We could potentially save a few cycles by using the LUT trick from
    // "https://embeddedgurus.com/state-space/2014/09/
    // fast-deterministic-and-portable-counting-leading-zeros/".
    // However, 256 bytes for a LUT is too large for embedded use cases. We could remove
    // the last 3 bisections  and use this 16 byte LUT for the rest of the work:
    // const LUT: [u8; 16] = [0, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4];
    // z -= LUT[x] as Int;
    // z
    // However, it ends up generating about the same number of instructions. When benchmarked
    // on x8664, it is slightly faster to use the LUT, but this is probably because of OOO
    // execution effects. Changing to using a LUT and branching is risky for smaller cores.
}

// The above method does not compile well on RISC-V and LoongArch (because of the lack
// of predicated instructions), producing code with many branches or using an
// excessively long branchless solution. This method takes advantage of the
// set-if-less-than instruction on RISC-V and LoongArch that allows
// `(x >= power - of - two) as Int` to be branchless.

/**
 * Returns the number of leading binary zeros in `x`.
 */
public fun <I : Int> leadingZerosCondset(x: I): Int {
    val x1 = x
    var x2 = x1
    // the number of potential leading zeros
    var z = I.BITS
    // a temporary
    var t: UInt
    // RISC-V and LoongArch do not have a set-if-greater-than-or-equal instruction and
    // `(x >= power - of - two) as Int` will get compiled into two instructions, but this is
    // still the most optimal method. A conditional set can only be turned into a single
    // immediate instruction if `x` is compared with an immediate `imm` (that can fit into
    // 12 bits) like `x<imm` but not `imm<x` (because the immediate is always on the
    // right). If we try to save an instruction by using `x<imm` for each bisection, we
    // have to shift `x` left and compare with powers of two approaching `Int.MAX + 1`,
    // but the immediate will never fit into 12 bits and never save an instruction.
    check(I.BITS <= 64)
    if (I.BITS >= 64) {
        // If the upper 32 bits of `x` are not all 0, `t` is set to `1<<5`, otherwise
        // `t` is set to 0.
        t = ((if (x2 >= (I.ONE shl 32)) 1u else 0u)) shl 5
        // If `t` was set to `1<<5`, then the upper 32 bits are shifted down for the
        // next step to process.
        x2 = x2 shr t.toInt()
        // If `t` was set to `1<<5`, then we subtract 32 from the number of potential
        // leading zeros
        z -= t
    }
    if (I.BITS >= 32) {
        t = ((if (x2 >= (I.ONE shl 16)) 1u else 0u)) shl 4
        x2 = x2 shr t.toInt()
        z -= t
    }
    check(I.BITS >= 16)
    t = ((if (x2 >= (I.ONE shl 8)) 1u else 0u)) shl 3
    x2 = x2 shr t.toInt()
    z -= t
    t = ((if (x2 >= (I.ONE shl 4)) 1u else 0u)) shl 2
    x2 = x2 shr t.toInt()
    z -= t
    t = ((if (x2 >= (I.ONE shl 2)) 1u else 0u)) shl 1
    x2 = x2 shr t.toInt()
    z -= t
    t = (if (x2 >= (I.ONE shl 1)) 1u else 0u)
    x2 = x2 shr t.toInt()
    z -= t
    // All bits except the LSB are guaranteed to be zero for this final bisection step.
    // If `x != 0` then `x == 1` and subtracts one potential zero from `z`.
    return z.toInt() - Int.castFrom(x2)
}

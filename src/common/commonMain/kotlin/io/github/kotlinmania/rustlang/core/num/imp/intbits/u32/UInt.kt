// port-lint: source num/imp/int_bits.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.intbits.u32

import io.github.kotlinmania.rustlang.core.num.ilog2

public val STAGES: Int = 32u.ilog2().toInt()

public fun prepare(sparse: UInt): UIntArray {
    var zeros = sparse.inv()
    val masks = MutableList(STAGES) { 0 }
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        var parity = zeros
        var len = n
        while (len < 32u) {
            parity = parity xor (parity shl len.toInt())
            len = len shl 1
        }
        masks[stage] = parity
        zeros = zeros and parity.inv()
        zeros = zeros xor (zeros shr n.toInt())
        stage += 1
    }
    return masks
}

public fun extractImpl(x: UInt, sparse: UInt): UInt {
    var x1 = x
    val masks = prepare(sparse)
    x1 = x1 and sparse
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        val q = x1 and masks[stage]
        if (false && 32u <= 64u) {
            x1 = ((x1 and masks[stage].inv()) + ((q shr n.toInt()).toUInt()))
        } else {
            x1 = x1 xor q
            x1 = x1 xor (q shr n.toInt())
        }
        stage += 1
    }
    return x1
}

public fun depositImpl(x: UInt, sparse: UInt): UInt {
    var x1 = x
    val masks = prepare(sparse)
    var stage = STAGES
    while (stage > 0) {
        stage -= 1
        val n = 1 shl stage
        val q = x1 and masks[stage]
        x1 = x1 xor q
        x1 = x1 xor (q shl n.toInt())
    }
    return x1 and sparse
}

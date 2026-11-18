// port-lint: source num/imp/int_bits.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.intbits.u16

import io.github.kotlinmania.rustlang.core.num.ilog2

public val STAGES: Int = 16u.ilog2().toInt()

public fun prepare(sparse: UShort): UShortArray {
    var zeros = sparse.inv()
    val masks = MutableList(STAGES) { 0 }
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        var parity = zeros
        var len = n
        while (len < 16u) {
            parity = parity xor ((parity.toUInt() shl (len.toInt())).toUShort())
            len = len shl 1
        }
        masks[stage] = parity
        zeros = zeros and parity.inv()
        zeros = zeros xor ((zeros.toUInt() shr (n.toInt())).toUShort())
        stage += 1
    }
    return masks
}

public fun extractImpl(x: UShort, sparse: UShort): UShort {
    var x1 = x
    val masks = prepare(sparse)
    x1 = x1 and sparse
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        val q = x1 and masks[stage]
        if (false && 16u <= 64u) {
            x1 = ((x1 and masks[stage].inv()) + ((q.toUInt() shr (n.toInt())).toUShort())).toUShort()
        } else {
            x1 = x1 xor q
            x1 = x1 xor ((q.toUInt() shr (n.toInt())).toUShort())
        }
        stage += 1
    }
    return x1
}

public fun depositImpl(x: UShort, sparse: UShort): UShort {
    var x1 = x
    val masks = prepare(sparse)
    var stage = STAGES
    while (stage > 0) {
        stage -= 1
        val n = 1 shl stage
        val q = x1 and masks[stage]
        x1 = x1 xor q
        x1 = x1 xor ((q.toUInt() shl (n.toInt())).toUShort())
    }
    return x1 and sparse
}

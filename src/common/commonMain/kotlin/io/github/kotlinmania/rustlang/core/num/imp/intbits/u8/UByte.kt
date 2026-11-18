// port-lint: source num/imp/int_bits.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.intbits.u8

import io.github.kotlinmania.rustlang.core.num.ilog2

public val STAGES: Int = 8u.ilog2().toInt()

public fun prepare(sparse: UByte): UByteArray {
    var zeros = sparse.inv()
    val masks = MutableList(STAGES) { 0 }
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        var parity = zeros
        var len = n
        while (len < 8u) {
            parity = parity xor ((parity.toUInt() shl (len.toInt())).toUByte())
            len = len shl 1
        }
        masks[stage] = parity
        zeros = zeros and parity.inv()
        zeros = zeros xor ((zeros.toUInt() shr (n.toInt())).toUByte())
        stage += 1
    }
    return masks
}

public fun extractImpl(x: UByte, sparse: UByte): UByte {
    var x1 = x
    val masks = prepare(sparse)
    x1 = x1 and sparse
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        val q = x1 and masks[stage]
        if (false && 8u <= 64u) {
            x1 = ((x1 and masks[stage].inv()) + ((q.toUInt() shr (n.toInt())).toUByte())).toUByte()
        } else {
            x1 = x1 xor q
            x1 = x1 xor ((q.toUInt() shr (n.toInt())).toUByte())
        }
        stage += 1
    }
    return x1
}

public fun depositImpl(x: UByte, sparse: UByte): UByte {
    var x1 = x
    val masks = prepare(sparse)
    var stage = STAGES
    while (stage > 0) {
        stage -= 1
        val n = 1 shl stage
        val q = x1 and masks[stage]
        x1 = x1 xor q
        x1 = x1 xor ((q.toUInt() shl (n.toInt())).toUByte())
    }
    return x1 and sparse
}

// port-lint: source num/imp/int_bits.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.intbits.u64

public val STAGES: Int = 64u.ilog2().toInt()

public fun prepare(sparse: ULong): ULongArray {
    var zeros = !sparse
    val masks = MutableList(STAGES) { 0 }
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        var parity = zeros
        var len = n
        while (len < 64u) {
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

public fun extractImpl(x: ULong, sparse: ULong): ULong {
    var x1 = x
    val masks = prepare(sparse)
    x1 = x1 and sparse
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        val q = x1 and masks[stage]
        if (false && 64u <= 64u) {
            x1 = ((x1 and masks[stage].inv()) + ((q shr n.toInt()).toULong()))
        } else {
            x1 = x1 xor q
            x1 = x1 xor (q shr n.toInt())
        }
        stage += 1
    }
    return x1
}

public fun depositImpl(x: ULong, sparse: ULong): ULong {
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

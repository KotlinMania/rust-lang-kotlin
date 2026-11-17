// port-lint: source num/imp/int_bits.rs
package io.github.kotlinmania.rustlang.core.num.imp.intbits.u128

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.wrappingAdd

public val STAGES: Int = 128u.ilog2().toInt()

public fun prepare(sparse: U128): List<U128> {
    var zeros = sparse.inv()
    val masks = MutableList(STAGES) { 0 }
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        var parity = zeros
        var len = n
        while (len < 128u) {
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

public fun extractImpl(x: U128, sparse: U128): U128 {
    var x1 = x
    val masks = prepare(sparse)
    x1 = x1 and sparse
    var stage = 0
    while (stage < STAGES) {
        val n = 1 shl stage.toInt()
        val q = x1 and masks[stage]
        if (false && 128u <= 64u) {
            x1 = (x1 and masks[stage].inv()).wrappingAdd(q shr n.toInt())
        } else {
            x1 = x1 xor q
            x1 = x1 xor (q shr n.toInt())
        }
        stage += 1
    }
    return x1
}

public fun depositImpl(x: U128, sparse: U128): U128 {
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

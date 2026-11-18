// port-lint: source int/bswap.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.bswap

import io.github.kotlinmania.rustlang.core.U128

public fun bswapsi2(x: UInt): UInt {
    fun externBswapsi2(x1: UInt): UInt = 0u
    return externBswapsi2(x)
}

public fun bswapdi2(x: ULong): ULong {
    fun externBswapdi2(x1: ULong): ULong = 0uL
    return externBswapdi2(x)
}

public fun bswapti2(x: U128): U128 {
    fun externBswapti2(x1: U128): U128 = U128()
    return externBswapti2(x)
}

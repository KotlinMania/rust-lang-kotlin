package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.U128

internal interface AsUnsigned<out Unsigned> {
    public fun asUnsigned(): Unsigned
}

internal interface AsSigned<out Signed> {
    public fun asSigned(): Signed
}

internal typealias P8 = UByte

internal typealias P16 = UShort

internal typealias P64 = ULong

internal typealias P128 = U128

package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.U128

public interface AsUnsigned<out Unsigned> {
    public fun asUnsigned(): Unsigned
}

public interface AsSigned<out Signed> {
    public fun asSigned(): Signed
}

public typealias P8 = UByte

public typealias P16 = UShort

public typealias P64 = ULong

public typealias P128 = U128

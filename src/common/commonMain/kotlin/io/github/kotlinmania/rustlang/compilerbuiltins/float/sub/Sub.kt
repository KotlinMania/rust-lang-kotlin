// port-lint: source float/sub.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.sub

import io.github.kotlinmania.f16.SIGN_MASK
import io.github.kotlinmania.f16.fromBits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.add.adddf3
import io.github.kotlinmania.rustlang.compilerbuiltins.float.add.addhf3
import io.github.kotlinmania.rustlang.compilerbuiltins.float.add.addsf3
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.compilerbuiltins.float.add.addkf3 as addtf3

public fun subhf3(a: F16, b: F16): F16 = addhf3(a, fromBits(b.toBits() xor SIGN_MASK))

public fun subsf3(a: Float, b: Float): Float = addsf3(a, Float.fromBits((b.toRawBits().toUInt() xor Float.SIGN_MASK.toUInt()).toInt()))

public fun subdf3(a: Double, b: Double): Double = adddf3(a, Double.fromBits((b.toRawBits().toULong() xor Double.SIGN_MASK.toULong()).toLong()))

public fun subtf3(a: F128, b: F128): F128 = addtf3(a, io.github.kotlinmania.f128.fromBits(b.toBits() xor io.github.kotlinmania.f128.SIGN_MASK))

public fun subkf3(a: F128, b: F128): F128 = addtf3(a, io.github.kotlinmania.f128.fromBits(b.toBits() xor io.github.kotlinmania.f128.SIGN_MASK))

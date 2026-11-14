package io.github.kotlinmania.rustlang.corearch.aarch64.sve

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.mem.transmute

public fun <T> T.sveInto(): T = this

public fun Byte.asUnsigned(): UByte = transmute(this)

public fun UByte.asSigned(): Byte = transmute(this)

public fun Short.asUnsigned(): UShort = transmute(this)

public fun UShort.asSigned(): Short = transmute(this)

public fun Int.asUnsigned(): UInt = transmute(this)

public fun UInt.asSigned(): Int = transmute(this)

public fun Long.asUnsigned(): ULong = transmute(this)

public fun ULong.asSigned(): Long = transmute(this)

public fun ConstPtr.asUnsigned(): ConstPtr = transmute(this)

public fun ConstPtr.asSigned(): ConstPtr = transmute(this)

public fun MutPtr.asUnsigned(): MutPtr = transmute(this)

public fun MutPtr.asSigned(): MutPtr = transmute(this)

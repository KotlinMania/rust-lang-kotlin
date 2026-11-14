package io.github.kotlinmania.rustlang.corearch.aarch64.sve

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

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

public fun ConstPtr.asUnsignedFromConstPtrShort(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrUShort(): ConstPtr = transmute(this)

public fun ConstPtr.asUnsignedFromConstPtrInt(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrUInt(): ConstPtr = transmute(this)

public fun ConstPtr.asUnsignedFromConstPtrLong(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrULong(): ConstPtr = transmute(this)

public fun MutPtr.asUnsigned(): MutPtr = transmute(this)

public fun MutPtr.asSigned(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrShort(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrUShort(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrInt(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrUInt(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrLong(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrULong(): MutPtr = transmute(this)

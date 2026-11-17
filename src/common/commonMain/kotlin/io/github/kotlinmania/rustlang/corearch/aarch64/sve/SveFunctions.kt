package io.github.kotlinmania.rustlang.corearch.aarch64.sve

import io.github.kotlinmania.rustlang.core.intrinsics.*
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun <T> T.sveInto(): T = this

public fun Byte.asUnsigned(): UByte = transmuteWithLayouts<Byte, UByte>(this, UnresolvedTypeLayout<Byte>(), PrimitiveLayouts.U8)

public fun UByte.asSigned(): Byte = transmuteWithLayouts<UByte, Byte>(this, UnresolvedTypeLayout<UByte>(), PrimitiveLayouts.I8)

public fun Short.asUnsigned(): UShort = transmuteWithLayouts<Short, UShort>(this, UnresolvedTypeLayout<Short>(), PrimitiveLayouts.U16)

public fun UShort.asSigned(): Short = transmuteWithLayouts<UShort, Short>(this, UnresolvedTypeLayout<UShort>(), PrimitiveLayouts.I16)

public fun Int.asUnsigned(): UInt = transmuteWithLayouts<Int, UInt>(this, UnresolvedTypeLayout<Int>(), PrimitiveLayouts.U32)

public fun UInt.asSigned(): Int = transmuteWithLayouts<UInt, Int>(this, UnresolvedTypeLayout<UInt>(), PrimitiveLayouts.I32)

public fun Long.asUnsigned(): ULong = transmuteWithLayouts<Long, ULong>(this, UnresolvedTypeLayout<Long>(), PrimitiveLayouts.U64)

public fun ULong.asSigned(): Long = transmuteWithLayouts<ULong, Long>(this, UnresolvedTypeLayout<ULong>(), PrimitiveLayouts.I64)

public fun ConstPtr.asUnsigned(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSigned(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrShort(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrUShort(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrInt(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrUInt(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrLong(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrULong(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun MutPtr.asUnsigned(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSigned(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrShort(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrUShort(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrInt(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrUInt(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrLong(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrULong(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

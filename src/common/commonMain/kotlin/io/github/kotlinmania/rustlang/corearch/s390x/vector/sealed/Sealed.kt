// port-lint: source s390x/vector.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.PackedTuple
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorBoolChar
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorBoolInt
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorBoolLongLong
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorBoolShort
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedChar
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedInt
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedLongLong
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedShort
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorUnsignedChar
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorUnsignedInt
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorUnsignedLongLong
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorUnsignedShort
import io.github.kotlinmania.rustlang.corearch.s390x.vector.validateBlockBoundary
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecMax
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmab
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmaf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmah
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVgfmh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVistrbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVistrfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVistrhs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaeb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaeh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmahb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmahf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmahh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalIb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalIf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalIh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalUb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalUf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalUh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaleb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaleh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalob
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmalof
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaloh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaob
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaof
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmaoh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmlhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmlhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVmlhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVsumb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVsumgf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVsumgh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vecVsumh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.verimb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.verimf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.verimg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.verimh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeeb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeebs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeefs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeeh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeehs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeezhs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeneb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenebs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenefs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfeneh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenehs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfenezhs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfmadb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfmasb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfmsdb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vfmssb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vftcidb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vftcisb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vlbb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vll
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vperm
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrcb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrcbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrcf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrcfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrch
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrchs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrczhs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrsb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrsf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrsh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrszb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrszf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vstrszh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumqf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumqg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vtm
import io.github.kotlinmania.rustlang.corearch.simd.I16x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x8

public interface VectorNeg<T> {
    public fun vecNeg(): T
}

// NOTE: `vuplh` is used for "unpack logical high", hence `vuplhw`.
public fun VectorSignedChar.vecNeg(): VectorSignedChar = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedChar.vecNeg(): VectorUnsignedChar = run { val _simdArg0 = this; _simdArg0 }

public fun VectorSignedShort.vecNeg(): VectorSignedShort = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedShort.vecNeg(): VectorUnsignedShort = run { val _simdArg0 = this; _simdArg0 }

public fun VectorSignedInt.vecNeg(): VectorSignedInt = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedInt.vecNeg(): VectorUnsignedInt = run { val _simdArg0 = this; _simdArg0 }

public fun VectorSignedLongLong.vecNeg(): VectorSignedLongLong = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedLongLong.vecNeg(): VectorUnsignedLongLong = run { val _simdArg0 = this; _simdArg0 }

public fun VectorFloat.vecNeg(): VectorFloat = run { val _simdArg0 = this; _simdArg0 }

public fun VectorDouble.vecNeg(): VectorDouble = run { val _simdArg0 = this; _simdArg0 }

public interface VectorAdd<Other, out Result> {
    public fun vecAdd(other: Other): Result
}

public fun vaSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaSc(this, other)

public fun vaUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaUc(this, other)

public fun vaSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaSh(this, other)

public fun vaUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaUh(this, other)

public fun vaSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaSf(this, other)

public fun vaUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaUf(this, other)

public fun vaSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaSg(this, other)

public fun vaUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaUg(this, other)

public fun vaScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedChar.vecAdd(other: VectorBoolChar): VectorSignedChar = vaScBc(this, other)

public fun vaUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedChar.vecAdd(other: VectorBoolChar): VectorUnsignedChar = vaUcBc(this, other)

public fun vaShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedShort.vecAdd(other: VectorBoolShort): VectorSignedShort = vaShBh(this, other)

public fun vaUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedShort.vecAdd(other: VectorBoolShort): VectorUnsignedShort = vaUhBh(this, other)

public fun vaSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedInt.vecAdd(other: VectorBoolInt): VectorSignedInt = vaSfBf(this, other)

public fun vaUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedInt.vecAdd(other: VectorBoolInt): VectorUnsignedInt = vaUfBf(this, other)

public fun vaSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedLongLong.vecAdd(other: VectorBoolLongLong): VectorSignedLongLong = vaSgBg(this, other)

public fun vaUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedLongLong.vecAdd(other: VectorBoolLongLong): VectorUnsignedLongLong = vaUgBg(this, other)

public fun vaBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaBcSc(this, other)

public fun vaBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaBcUc(this, other)

public fun vaBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaBhSh(this, other)

public fun vaBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaBhUh(this, other)

public fun vaBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaBfSf(this, other)

public fun vaBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaBfUf(this, other)

public fun vaBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaBgSg(this, other)

public fun vaBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaBgUg(this, other)

public fun vaDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorDouble.vecAdd(other: VectorDouble): VectorDouble = vaDouble(this, other)

public fun vaFloat(a: VectorFloat, b: VectorFloat): VectorFloat = transmute(run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 })

public fun VectorFloat.vecAdd(other: VectorFloat): VectorFloat = vaFloat(this, other)

public interface VectorSub<Other, out Result> {
    public fun vecSub(other: Other): Result
}

public fun vsSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsSc(this, other)

public fun vsUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsUc(this, other)

public fun vsSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsSh(this, other)

public fun vsUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsUh(this, other)

public fun vsSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsSf(this, other)

public fun vsUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsUf(this, other)

public fun vsSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsSg(this, other)

public fun vsUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsUg(this, other)

public fun vsScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedChar.vecSub(other: VectorBoolChar): VectorSignedChar = vsScBc(this, other)

public fun vsUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedChar.vecSub(other: VectorBoolChar): VectorUnsignedChar = vsUcBc(this, other)

public fun vsShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedShort.vecSub(other: VectorBoolShort): VectorSignedShort = vsShBh(this, other)

public fun vsUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedShort.vecSub(other: VectorBoolShort): VectorUnsignedShort = vsUhBh(this, other)

public fun vsSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedInt.vecSub(other: VectorBoolInt): VectorSignedInt = vsSfBf(this, other)

public fun vsUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedInt.vecSub(other: VectorBoolInt): VectorUnsignedInt = vsUfBf(this, other)

public fun vsSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedLongLong.vecSub(other: VectorBoolLongLong): VectorSignedLongLong = vsSgBg(this, other)

public fun vsUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedLongLong.vecSub(other: VectorBoolLongLong): VectorUnsignedLongLong = vsUgBg(this, other)

public fun vsBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsBcSc(this, other)

public fun vsBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsBcUc(this, other)

public fun vsBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsBhSh(this, other)

public fun vsBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsBhUh(this, other)

public fun vsBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsBfSf(this, other)

public fun vsBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsBfUf(this, other)

public fun vsBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsBgSg(this, other)

public fun vsBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorBoolLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsBgUg(this, other)

public fun vsDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorDouble.vecSub(other: VectorDouble): VectorDouble = vsDouble(this, other)

public fun vsFloat(a: VectorFloat, b: VectorFloat): VectorFloat = transmute(run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 })

public fun VectorFloat.vecSub(other: VectorFloat): VectorFloat = vsFloat(this, other)

public interface VectorMul<T> {
    public fun vecMul(b: T): T
}

public fun vmlSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedChar.vecMul(other: VectorSignedChar): VectorSignedChar = vmlSc(this, other)

public fun vmlUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedChar.vecMul(other: VectorUnsignedChar): VectorUnsignedChar = vmlUc(this, other)

public fun vmlSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedShort.vecMul(other: VectorSignedShort): VectorSignedShort = vmlSh(this, other)

public fun vmlUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedShort.vecMul(other: VectorUnsignedShort): VectorUnsignedShort = vmlUh(this, other)

public fun vmlSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorSignedInt.vecMul(other: VectorSignedInt): VectorSignedInt = vmlSf(this, other)

public fun vmlUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorUnsignedInt.vecMul(other: VectorUnsignedInt): VectorUnsignedInt = vmlUf(this, other)

public fun VectorSignedLongLong.vecMul(other: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val _simdArg0 = transmute(this); val _simdArg1 = other; _simdArg0 })

public fun VectorUnsignedLongLong.vecMul(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val _simdArg0 = transmute(this); val _simdArg1 = other; _simdArg0 })

public fun VectorFloat.vecMul(other: VectorFloat): VectorFloat = transmute(run { val _simdArg0 = transmute(this); val _simdArg1 = other; _simdArg0 })

public fun vmlDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = b; _simdArg0 })

public fun VectorDouble.vecMul(other: VectorDouble): VectorDouble = vmlDouble(this, other)

public interface VectorMax<Other, out Result> {
    public fun vecMax(b: Other): Result
}

public fun vecVmxsc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedChar() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedChar.vecMax(other: VectorSignedChar): VectorSignedChar = vecVmxsc(this, other)

public fun vecVmxslc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedChar() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedChar.vecMax(other: VectorUnsignedChar): VectorUnsignedChar = vecVmxslc(this, other)

public fun vecVmxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedShort() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedShort.vecMax(other: VectorSignedShort): VectorSignedShort = vecVmxsh(this, other)

public fun vecVmxslh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedShort() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedShort.vecMax(other: VectorUnsignedShort): VectorUnsignedShort = vecVmxslh(this, other)

public fun vecVmxsf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedInt() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedInt.vecMax(other: VectorSignedInt): VectorSignedInt = vecVmxsf(this, other)

public fun vecVmxslf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedInt() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedInt.vecMax(other: VectorUnsignedInt): VectorUnsignedInt = vecVmxslf(this, other)

public fun vecVmxsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedLongLong() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedLongLong.vecMax(other: VectorSignedLongLong): VectorSignedLongLong = vecVmxsg(this, other)

public fun vecVmxslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedLongLong() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedLongLong.vecMax(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmxslg(this, other)

public interface VectorMin<Other, out Result> {
    public fun vecMin(b: Other): Result
}

public fun vecVmnsc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedChar() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedChar.vecMin(other: VectorSignedChar): VectorSignedChar = vecVmnsc(this, other)

public fun vecVmnslc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedChar() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedChar.vecMin(other: VectorUnsignedChar): VectorUnsignedChar = vecVmnslc(this, other)

public fun vecVmnsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedShort() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedShort.vecMin(other: VectorSignedShort): VectorSignedShort = vecVmnsh(this, other)

public fun vecVmnslh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedShort() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedShort.vecMin(other: VectorUnsignedShort): VectorUnsignedShort = vecVmnslh(this, other)

public fun vecVmnsf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedInt() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedInt.vecMin(other: VectorSignedInt): VectorSignedInt = vecVmnsf(this, other)

public fun vecVmnslf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedInt() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedInt.vecMin(other: VectorUnsignedInt): VectorUnsignedInt = vecVmnslf(this, other)

public fun vecVmnsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorSignedLongLong() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorSignedLongLong.vecMin(other: VectorSignedLongLong): VectorSignedLongLong = vecVmnsg(this, other)

public fun vecVmnslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; VectorUnsignedLongLong() }; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }

public fun VectorUnsignedLongLong.vecMin(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmnslg(this, other)

public interface VectorAbs<T> {
    public fun vecAbs(): T
}

public fun vecAbsI8(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsI16(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsI32(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsI64(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public interface VectorNabs<T> {
    public fun vecNabs(): T
}

public fun vecNabsF32(a: VectorFloat): VectorFloat = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; _simdArg0 }

public fun vecNabsF64(a: VectorDouble): VectorDouble = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; _simdArg0 }

public interface VectorNmsub<T> {
    public fun vecNmsub(b: T, c: T): T
}

public fun vecNmsubF32(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }; _simdArg0 }

public fun VectorFloat.vecNmsub(b: VectorFloat, c: VectorFloat): VectorFloat = vecNmsubF32(this, b, c)

public fun vecNmsubF64(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }; _simdArg0 }

public fun VectorDouble.vecNmsub(b: VectorDouble, c: VectorDouble): VectorDouble = vecNmsubF64(this, b, c)

public interface VectorNmadd<T> {
    public fun vecNmadd(b: T, c: T): T
}

public fun vecNmaddF32(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }; _simdArg0 }

public fun VectorFloat.vecNmadd(b: VectorFloat, c: VectorFloat): VectorFloat = vecNmaddF32(this, b, c)

public fun vecNmaddF64(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }; _simdArg0 }

public fun VectorDouble.vecNmadd(b: VectorDouble, c: VectorDouble): VectorDouble = vecNmaddF64(this, b, c)

public interface VectorSplat<T> {
    public fun vecSplat(imm: UInt): T
}

public fun vrepb(a: VectorSignedChar, imm2: UInt): VectorSignedChar {
    run {
        check(0u <= imm2 && imm2 < (1u shl 4)) { "IMM2 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { imm2 }); _simdArg0 }
}

public fun vreph(a: VectorSignedShort, imm2: UInt): VectorSignedShort {
    run {
        check(0u <= imm2 && imm2 < (1u shl 3)) { "IMM2 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { imm2 }); _simdArg0 }
}

public fun vrepf(a: VectorSignedInt, imm2: UInt): VectorSignedInt {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { imm2 }); _simdArg0 }
}

public fun vrepg(a: VectorSignedLongLong, imm2: UInt): VectorSignedLongLong {
    run {
        check(0u <= imm2 && imm2 < (1u shl 1)) { "IMM2 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(2) { imm2 }); _simdArg0 }
}

public fun VectorSignedChar.vecSplat(imm: UInt): VectorSignedChar = transmute(vrepb(transmute(this), imm))

public fun VectorUnsignedChar.vecSplat(imm: UInt): VectorUnsignedChar = transmute(vrepb(transmute(this), imm))

public fun VectorBoolChar.vecSplat(imm: UInt): VectorBoolChar = transmute(vrepb(transmute(this), imm))

public fun VectorSignedShort.vecSplat(imm: UInt): VectorSignedShort = transmute(vreph(transmute(this), imm))

public fun VectorUnsignedShort.vecSplat(imm: UInt): VectorUnsignedShort = transmute(vreph(transmute(this), imm))

public fun VectorBoolShort.vecSplat(imm: UInt): VectorBoolShort = transmute(vreph(transmute(this), imm))

public fun VectorSignedInt.vecSplat(imm: UInt): VectorSignedInt = transmute(vrepf(transmute(this), imm))

public fun VectorUnsignedInt.vecSplat(imm: UInt): VectorUnsignedInt = transmute(vrepf(transmute(this), imm))

public fun VectorBoolInt.vecSplat(imm: UInt): VectorBoolInt = transmute(vrepf(transmute(this), imm))

public fun VectorSignedLongLong.vecSplat(imm: UInt): VectorSignedLongLong = transmute(vrepg(transmute(this), imm))

public fun VectorUnsignedLongLong.vecSplat(imm: UInt): VectorUnsignedLongLong = transmute(vrepg(transmute(this), imm))

public fun VectorBoolLongLong.vecSplat(imm: UInt): VectorBoolLongLong = transmute(vrepg(transmute(this), imm))

public fun VectorFloat.vecSplat(imm: UInt): VectorFloat = transmute(vrepf(transmute(this), imm))

public fun VectorDouble.vecSplat(imm: UInt): VectorDouble = transmute(vrepg(transmute(this), imm))

public interface VectorSplats<Output, Result> {
    public fun vecSplats(): Output
}

public fun vecSplatsU8(v: UByte): STL = transmute(u8x16.splat(v))

public fun UByte.vecSplats(): STL = vecSplatsU8(this)

public fun vecSplatsI8(v: Byte): STL = transmute(i8x16.splat(v))

public fun Byte.vecSplats(): STL = vecSplatsI8(this)

public fun vecSplatsU16(v: UShort): STL = transmute(u16x8.splat(v))

public fun UShort.vecSplats(): STL = vecSplatsU16(this)

public fun vecSplatsI16(v: Short): STL = transmute(i16x8.splat(v))

public fun Short.vecSplats(): STL = vecSplatsI16(this)

public fun vecSplatsU32(v: UInt): STL = transmute(u32x4.splat(v))

public fun UInt.vecSplats(): STL = vecSplatsU32(this)

public fun vecSplatsI32(v: Int): STL = transmute(i32x4.splat(v))

public fun Int.vecSplats(): STL = vecSplatsI32(this)

public fun vecSplatsU64(v: ULong): STL = transmute(u64x2.splat(v))

public fun ULong.vecSplats(): STL = vecSplatsU64(this)

public fun vecSplatsI64(v: Long): STL = transmute(i64x2.splat(v))

public fun Long.vecSplats(): STL = vecSplatsI64(this)

public fun vecSplatsF32(v: Float): STL = transmute(f32x4.splat(v))

public fun Float.vecSplats(): STL = vecSplatsF32(this)

public fun vecSplatsF64(v: Double): STL = transmute(f64x2.splat(v))

public fun Double.vecSplats(): STL = vecSplatsF64(this)

public fun UByte.vecSplatsVectorBoolChar(): VectorBoolChar = transmute(u8x16.splat(this))

public fun Byte.vecSplatsVectorBoolChar(): VectorBoolChar = transmute(i8x16.splat(this))

public fun UShort.vecSplatsVectorBoolShort(): VectorBoolShort = transmute(u16x8.splat(this))

public fun Short.vecSplatsVectorBoolShort(): VectorBoolShort = transmute(i16x8.splat(this))

public fun UInt.vecSplatsVectorBoolInt(): VectorBoolInt = transmute(u32x4.splat(this))

public fun Int.vecSplatsVectorBoolInt(): VectorBoolInt = transmute(i32x4.splat(this))

public fun ULong.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmute(u64x2.splat(this))

public fun Long.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmute(i64x2.splat(this))

public interface CountBits<out Result> {
    public fun vecCntlz(): Result

    public fun vecCnttz(): Result

    public fun vecPopcnt(): Result
}

public fun VectorSignedChar.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedChar.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedChar.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedChar.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedChar.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedChar.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedShort.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedShort.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedShort.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedShort.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedShort.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedShort.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedInt.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedInt.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedInt.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedInt.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedInt.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedInt.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedLongLong.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedLongLong.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorSignedLongLong.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedLongLong.vecCntlz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedLongLong.vecCnttz(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public fun VectorUnsignedLongLong.vecPopcnt(): TU = transmute(run { val _simdArg0 = this; _simdArg0 })

public interface VectorAnd<Other, out Result> {
    public fun vecAnd(b: Other): Result
}

public interface VectorOr<Other, out Result> {
    public fun vecOr(b: Other): Result
}

public interface VectorXor<Other, out Result> {
    public fun vecXor(b: Other): Result
}

public fun nor(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmute(a)
    val b1: U8x16 = transmute(b)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = u8x16.splat(0xff); _simdArg0 })
}

public interface VectorNor<Other, out Result> {
    public fun vecNor(b: Other): Result
}

public fun nand(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmute(a)
    val b1: U8x16 = transmute(b)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = u8x16.splat(0xff); _simdArg0 })
}

public interface VectorNand<Other, out Result> {
    public fun vecNand(b: Other): Result
}

public fun eqv(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmute(a)
    val b1: U8x16 = transmute(b)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = u8x16.splat(0xff); _simdArg0 })
}

public interface VectorEqv<Other, out Result> {
    public fun vecEqv(b: Other): Result
}

public fun andc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = transmute(a)
    val b1 = transmute(b)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = u8x16.splat(0xff); val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = a1; _simdArg0 })
}

public interface VectorAndc<Other, out Result> {
    public fun vecAndc(b: Other): Result
}

public fun orc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = transmute(a)
    val b1 = transmute(b)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = u8x16.splat(0xff); val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = a1; _simdArg0 })
}

public interface VectorOrc<Other, out Result> {
    public fun vecOrc(b: Other): Result
}

public interface VectorRoundc<T> {
    public fun vecRoundc(): T
}

public interface VectorRound<T> {
    public fun vecRound(): T
}

public interface VectorRint<T> {
    public fun vecRint(): T
}

public interface VectorTrunc<T> {
    public fun vecTrunc(): T
}

public interface VectorCeil<T> {
    public fun vecCeil(): T
}

public interface VectorFloor<T> {
    public fun vecFloor(): T
}

public interface VectorSl<Other, out Result> {
    public fun vecSl(b: Other): Result
}

public fun veslvb(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(8u.toUByte()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun veslvh(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(16u.toUShort()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun veslvf(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(32u); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun veslvg(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(64u.toULong()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public interface VectorSr<Other, out Result> {
    public fun vecSr(b: Other): Result
}

public fun vesrlvb(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(8u.toUByte()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesrlvh(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(16u.toUShort()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesrlvf(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(32u); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesrlvg(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(64u.toULong()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public interface VectorSra<Other, out Result> {
    public fun vecSra(b: Other): Result
}

public fun vesravb(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(8u.toByte()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesravh(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(16u.toShort()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesravf(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(32u.toInt()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vesravg(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(64u.toLong()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public interface VectorSlb<Other, out Result> {
    public fun vecSlb(b: Other): Result
}

public interface VectorSrab<Other, out Result> {
    public fun vecSrab(b: Other): Result
}

public interface VectorSrb<Other, out Result> {
    public fun vecSrb(b: Other): Result
}

public interface VectorSrl<Other, out Result> {
    public fun vecSrl(b: Other): Result
}

public interface VectorSral<Other, out Result> {
    public fun vecSral(b: Other): Result
}

public interface VectorSll<Other, out Result> {
    public fun vecSll(b: Other): Result
}

public interface VectorRl<Other, T, out Result> {
    public fun vecRl(b: Other): Result
}

public fun verllvb(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun verllvh(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun verllvf(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun verllvg(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun verllvbImm(a: TTL, bits: CUlong): TTL {
    val bits1 = (bits % 8u.toULong()).toUByte()
    val b = TTS.splat(bits1)
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = transmute(b); _simdArg0 }
}

public fun verllvhImm(a: TTL, bits: CUlong): TTL {
    val bits1 = (bits % 16u.toULong()).toUShort()
    val b = TTS.splat(bits1)
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = transmute(b); _simdArg0 }
}

public fun verllvfImm(a: TTL, bits: CUlong): TTL {
    val bits1 = (bits % 32u.toULong()).toUInt()
    val b = TTS.splat(bits1)
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = transmute(b); _simdArg0 }
}

public fun verllvgImm(a: TTL, bits: CUlong): TTL {
    val bits1 = (bits % 64u.toULong())
    val b = TTS.splat(bits1)
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = transmute(b); _simdArg0 }
}

public interface VectorRli<T> {
    public fun vecRli(bits: CUlong): T
}

public fun VectorSignedChar.vecRli(bits: CUlong): VectorSignedChar = transmute(verllvbImm(transmute(this), bits))

public fun TU.vecRli(bits: CUlong): TU = verllvbImm(this, bits)

public fun VectorSignedShort.vecRli(bits: CUlong): VectorSignedShort = transmute(verllvhImm(transmute(this), bits))

public fun VectorSignedInt.vecRli(bits: CUlong): VectorSignedInt = transmute(verllvfImm(transmute(this), bits))

public fun VectorSignedLongLong.vecRli(bits: CUlong): VectorSignedLongLong = transmute(verllvgImm(transmute(this), bits))

public interface VectorRlMask<Other, T> {
    public fun vecRlMask(other: Other, imm8: UByte): T
}

public fun testVerimb(a: VectorSignedChar, b: TU, imm8: UByte): VectorSignedChar = verimb(a, a, transmute(b), (imm8 % LTT.BITS.toUByte()).toInt())

public fun VectorSignedChar.vecRlMask(other: TU, imm8: UByte): VectorSignedChar = testVerimb(this, other, imm8)

public fun TU.vecRlMask(other: TU, imm8: UByte): TU = transmute(testVerimb(transmute(this), transmute(other), imm8))

public fun testVerimh(a: VectorSignedShort, b: TU, imm8: UByte): VectorSignedShort = verimh(a, a, transmute(b), (imm8 % LTT.BITS.toUByte()).toInt())

public fun VectorSignedShort.vecRlMask(other: TU, imm8: UByte): VectorSignedShort = testVerimh(this, other, imm8)

public fun TU.vecRlMaskTU(other: TU, imm8: UByte): TU = transmute(testVerimh(transmute(this), transmute(other), imm8))

public fun testVerimf(a: VectorSignedInt, b: TU, imm8: UByte): VectorSignedInt = verimf(a, a, transmute(b), (imm8 % LTT.BITS.toUByte()).toInt())

public fun VectorSignedInt.vecRlMask(other: TU, imm8: UByte): VectorSignedInt = testVerimf(this, other, imm8)

public fun testVerimg(a: VectorSignedLongLong, b: TU, imm8: UByte): VectorSignedLongLong = verimg(a, a, transmute(b), (imm8 % LTT.BITS.toUByte()).toInt())

public fun VectorSignedLongLong.vecRlMask(other: TU, imm8: UByte): VectorSignedLongLong = testVerimg(this, other, imm8)

public interface VectorReve<T> {
    public fun vecReve(): T
}

public open class ReverseMask(
    public var value: UIntArray,
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(n: Int = 0): TypeLayout<ReverseMask> =
            object : TypeLayout<ReverseMask> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((n).toLong() * (PrimitiveLayouts.U32).strideBytes), 4L)
                override fun readNative(addr: Long): ReverseMask =
                    ReverseMask(
                        (PrimitiveArrayLayouts.uIntArray((n).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ReverseMask) {
                    (PrimitiveArrayLayouts.uIntArray((n).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ReverseMask =
                    ReverseMask(
                        (PrimitiveArrayLayouts.uIntArray((n).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ReverseMask) {
                    (PrimitiveArrayLayouts.uIntArray((n).toInt())).writeBytes(buf, offset, value.value)
                }
            }

        public fun new(n: Int): ReverseMask {
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = (n - i - 1).toUInt()
                i += 1
            }
            return ReverseMask(index)
        }
    }
}

public fun reveb(a: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.reverse(); _simdArg0 }

public fun VectorSignedChar.vecReve(): VectorSignedChar = reveb(this)

public fun TU.vecReve(): TU = transmute(reveb(transmute(this)))

public fun TB.vecReve(): TB = transmute(reveb(transmute(this)))

public fun reveh(a: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.reverse(); _simdArg0 }

public fun VectorSignedShort.vecReve(): VectorSignedShort = reveh(this)

public fun revef(a: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.reverse(); _simdArg0 }

public fun VectorSignedInt.vecReve(): VectorSignedInt = revef(this)

public fun reveg(a: VectorSignedLongLong): VectorSignedLongLong = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.reverse(); _simdArg0 }

public fun VectorSignedLongLong.vecReve(): VectorSignedLongLong = reveg(this)

public fun VectorFloat.vecReve(): VectorFloat = transmute(transmute(this).vecReve())

public fun VectorDouble.vecReve(): VectorDouble = transmute(transmute(this).vecReve())

public interface VectorRevb<T> {
    public fun vecRevb(): T
}

public fun VectorFloat.vecRevb(): VectorFloat = transmute(transmute(this).vecRevb())

public fun VectorDouble.vecRevb(): VectorDouble = transmute(transmute(this).vecRevb())

public interface VectorMergel<T, Result> {
    public fun vecMergel(other: T): T
}

public interface VectorMergeh<T, Result> {
    public fun vecMergeh(other: T): T
}

public fun vmrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeLow(); _simdArg0 }

public fun VectorSignedChar.vecMergel(other: VectorSignedChar): VectorSignedChar = vmrlb(this, other)

public fun TU.vecMergel(other: TU): TU = transmute(vmrlb(transmute(this), transmute(other)))

public fun vmrhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeHigh(); _simdArg0 }

public fun VectorSignedChar.vecMergeh(other: VectorSignedChar): VectorSignedChar = vmrhb(this, other)

public fun TU.vecMergeh(other: TU): TU = transmute(vmrhb(transmute(this), transmute(other)))

public fun vmrlh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeLow(); _simdArg0 }

public fun VectorSignedShort.vecMergel(other: VectorSignedShort): VectorSignedShort = vmrlh(this, other)

public fun vmrhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeHigh(); _simdArg0 }

public fun VectorSignedShort.vecMergeh(other: VectorSignedShort): VectorSignedShort = vmrhh(this, other)

public fun vmrlf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeLow(); _simdArg0 }

public fun VectorSignedInt.vecMergel(other: VectorSignedInt): VectorSignedInt = vmrlf(this, other)

public fun vmrhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeHigh(); _simdArg0 }

public fun VectorSignedInt.vecMergeh(other: VectorSignedInt): VectorSignedInt = vmrhf(this, other)

public fun vmrlg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeLow(); _simdArg0 }

public fun VectorSignedLongLong.vecMergel(other: VectorSignedLongLong): VectorSignedLongLong = vmrlg(this, other)

public fun vmrhg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.mergeHigh(); _simdArg0 }

public fun VectorSignedLongLong.vecMergeh(other: VectorSignedLongLong): VectorSignedLongLong = vmrhg(this, other)

public interface VectorPerm<T> {
    public fun vecPerm(other: T, c: VectorUnsignedChar): T
}

public fun VectorSignedChar.vecPerm(other: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorSignedShort.vecPerm(other: VectorSignedShort, c: VectorUnsignedChar): VectorSignedShort = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorSignedInt.vecPerm(other: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorSignedLongLong.vecPerm(other: VectorSignedLongLong, c: VectorUnsignedChar): VectorSignedLongLong = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorUnsignedChar.vecPerm(other: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorUnsignedShort.vecPerm(other: VectorUnsignedShort, c: VectorUnsignedChar): VectorUnsignedShort = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorUnsignedInt.vecPerm(other: VectorUnsignedInt, c: VectorUnsignedChar): VectorUnsignedInt = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorUnsignedLongLong.vecPerm(other: VectorUnsignedLongLong, c: VectorUnsignedChar): VectorUnsignedLongLong = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorBoolChar.vecPerm(other: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorBoolShort.vecPerm(other: VectorBoolShort, c: VectorUnsignedChar): VectorBoolShort = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorBoolInt.vecPerm(other: VectorBoolInt, c: VectorUnsignedChar): VectorBoolInt = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorBoolLongLong.vecPerm(other: VectorBoolLongLong, c: VectorUnsignedChar): VectorBoolLongLong = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorFloat.vecPerm(other: VectorFloat, c: VectorUnsignedChar): VectorFloat = transmute(vperm(transmute(this), transmute(other), c))

public fun VectorDouble.vecPerm(other: VectorDouble, c: VectorUnsignedChar): VectorDouble = transmute(vperm(transmute(this), transmute(other), c))

public interface VectorSumU128<T> {
    public fun vecSumU128(other: T): VectorUnsignedChar
}

public fun vecVsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128 = transmute(vsumqf(a, b))

public fun vecVsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 = transmute(vsumqg(a, b))

public fun VectorUnsignedInt.vecSumU128(other: VectorUnsignedInt): VectorUnsignedChar = transmute(vecVsumqf(this, other))

public fun VectorUnsignedLongLong.vecSumU128(other: VectorUnsignedLongLong): VectorUnsignedChar = transmute(vecVsumqg(this, other))

public interface VectorSum2<T> {
    public fun vecSum2(other: T): VectorUnsignedLongLong
}

public fun VectorUnsignedShort.vecSum2(other: VectorUnsignedShort): VectorUnsignedLongLong = vecVsumgh(this, other)

public fun VectorUnsignedInt.vecSum2(other: VectorUnsignedInt): VectorUnsignedLongLong = vecVsumgf(this, other)

public interface VectorSum4<T> {
    public fun vecSum4(other: T): VectorUnsignedInt
}

public fun VectorUnsignedChar.vecSum4(other: VectorUnsignedChar): VectorUnsignedInt = vecVsumb(this, other)

public fun VectorUnsignedShort.vecSum4(other: VectorUnsignedShort): VectorUnsignedInt = vecVsumh(this, other)

public interface VectorSubc<Other, out Result> {
    public fun vecSubc(b: Other): Result
}

public interface VectorSqrt<T> {
    public fun vecSqrt(): T
}

public fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, imm: Int): VectorSignedChar = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaeb(a, b, imm)

public fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, imm: Int): VectorSignedShort = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaeh(a, b, imm)

public fun vfaef(a: VectorSignedInt, b: VectorSignedInt, imm: Int): VectorSignedInt = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaef(a, b, imm)

public fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, imm: Int): VectorSignedChar = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezb(a, b, imm)

public fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, imm: Int): VectorSignedShort = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezh(a, b, imm)

public fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, imm: Int): VectorSignedInt = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezf(a, b, imm)

public interface VectorFindAnyEq<Other, out Result> {
    public fun vecFindAnyEq(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEq(b: VectorSignedChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyEq(b: VectorUnsignedChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorBoolChar.vecFindAnyEq(b: VectorBoolChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorSignedShort.vecFindAnyEq(b: VectorSignedShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyEq(b: VectorUnsignedShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorBoolShort.vecFindAnyEq(b: VectorBoolShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorSignedInt.vecFindAnyEq(b: VectorSignedInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyEq(b: VectorUnsignedInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public fun VectorBoolInt.vecFindAnyEq(b: VectorBoolInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Eq.value.toInt()))

public interface VectorFindAnyNe<Other, out Result> {
    public fun vecFindAnyNe(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNe(b: VectorSignedChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyNe(b: VectorUnsignedChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorBoolChar.vecFindAnyNe(b: VectorBoolChar): TB = transmute(vfaeb(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorSignedShort.vecFindAnyNe(b: VectorSignedShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyNe(b: VectorUnsignedShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorBoolShort.vecFindAnyNe(b: VectorBoolShort): TB = transmute(vfaeh(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorSignedInt.vecFindAnyNe(b: VectorSignedInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyNe(b: VectorUnsignedInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public fun VectorBoolInt.vecFindAnyNe(b: VectorBoolInt): TB = transmute(vfaef(transmute(this), transmute(b), FindImm.Ne.value.toInt()))

public interface VectorFindAnyEqOrZeroIdx<Other, out Result> {
    public fun vecFindAnyEqOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqOr0Idx(b: VectorSignedChar): VectorSignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyEqOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolChar.vecFindAnyEqOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorSignedShort.vecFindAnyEqOr0Idx(b: VectorSignedShort): VectorSignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyEqOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolShort.vecFindAnyEqOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorSignedInt.vecFindAnyEqOr0Idx(b: VectorSignedInt): VectorSignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyEqOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolInt.vecFindAnyEqOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public interface VectorFindAnyNeOrZeroIdx<Other, out Result> {
    public fun vecFindAnyNeOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeOr0Idx(b: VectorSignedChar): VectorSignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyNeOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolChar.vecFindAnyNeOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmute(vfaezb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorSignedShort.vecFindAnyNeOr0Idx(b: VectorSignedShort): VectorSignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyNeOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolShort.vecFindAnyNeOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmute(vfaezh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorSignedInt.vecFindAnyNeOr0Idx(b: VectorSignedInt): VectorSignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyNeOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolInt.vecFindAnyNeOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmute(vfaezf(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public interface VectorFindAnyEqIdx<Other, out Result> {
    public fun vecFindAnyEqIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqIdx(b: VectorSignedChar): VectorSignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyEqIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolChar.vecFindAnyEqIdx(b: VectorBoolChar): VectorUnsignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorSignedShort.vecFindAnyEqIdx(b: VectorSignedShort): VectorSignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyEqIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolShort.vecFindAnyEqIdx(b: VectorBoolShort): VectorUnsignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorSignedInt.vecFindAnyEqIdx(b: VectorSignedInt): VectorSignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyEqIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public fun VectorBoolInt.vecFindAnyEqIdx(b: VectorBoolInt): VectorUnsignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.EqIdx.value.toInt()))

public interface VectorFindAnyNeIdx<Other, out Result> {
    public fun vecFindAnyNeIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeIdx(b: VectorSignedChar): VectorSignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedChar.vecFindAnyNeIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolChar.vecFindAnyNeIdx(b: VectorBoolChar): VectorUnsignedChar = transmute(vfaeb(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorSignedShort.vecFindAnyNeIdx(b: VectorSignedShort): VectorSignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedShort.vecFindAnyNeIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolShort.vecFindAnyNeIdx(b: VectorBoolShort): VectorUnsignedShort = transmute(vfaeh(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorSignedInt.vecFindAnyNeIdx(b: VectorSignedInt): VectorSignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorUnsignedInt.vecFindAnyNeIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun VectorBoolInt.vecFindAnyNeIdx(b: VectorBoolInt): VectorUnsignedInt = transmute(vfaef(transmute(this), transmute(b), FindImm.NeIdx.value.toInt()))

public fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, imm: Int): PackedTuple<VectorSignedChar, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaebs(a, b, imm)

public fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, imm: Int): PackedTuple<VectorSignedShort, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaehs(a, b, imm)

public fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, imm: Int): PackedTuple<VectorSignedInt, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaefs(a, b, imm)

public fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, imm: Int): PackedTuple<VectorSignedChar, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezbs(a, b, imm)

public fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, imm: Int): PackedTuple<VectorSignedShort, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezhs(a, b, imm)

public fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, imm: Int): PackedTuple<VectorSignedInt, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezfs(a, b, imm)

public interface VectorFindAnyEqCC<Other, out Result> {
    public fun vecFindAnyEqCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqCc(b: VectorSignedChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqCc(b: VectorUnsignedChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqCc(b: VectorBoolChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqCc(b: VectorSignedShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqCc(b: VectorUnsignedShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqCc(b: VectorBoolShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqCc(b: VectorSignedInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqCc(b: VectorUnsignedInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqCc(b: VectorBoolInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeCC<Other, out Result> {
    public fun vecFindAnyNeCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeCc(b: VectorSignedChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeCc(b: VectorUnsignedChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeCc(b: VectorBoolChar): Pair<TB, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeCc(b: VectorSignedShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeCc(b: VectorUnsignedShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeCc(b: VectorBoolShort): Pair<TB, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeCc(b: VectorSignedInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeCc(b: VectorUnsignedInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeCc(b: VectorBoolInt): Pair<TB, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyEqIdxCC<Other, out Result> {
    public fun vecFindAnyEqIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeIdxCC<Other, out Result> {
    public fun vecFindAnyNeIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyEqOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyEqOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyNeOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmute(this), transmute(b), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun testVectorLoad(offset: Int, ptr: ConstPtr): VectorSignedInt = (UnresolvedTypeLayout<VectorSignedInt>()).readNative((ConstPtr((ConstPtr((ptr).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress)

public fun testVectorStore(vector: VectorSignedInt, offset: Int, ptr: MutPtr) {
    (UnresolvedTypeLayout<VectorSignedInt>()).writeNative((MutPtr((MutPtr((ptr).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress, vector)
}

public interface VectorLoad<T, ElementType> {
    public fun vecXl(offset: Int, ptr: ConstPtr): T = (UnresolvedTypeLayout<T>()).readNative((ConstPtr((ConstPtr((ptr).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress)

    public fun vecLoadLen(ptr: ConstPtr, byteCount: UInt): T

    public fun vecLoadBndry(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<T>
}

public interface VectorStore<ElementType> {
    public fun vecXst(offset: Int, ptr: MutPtr) {
        (UnresolvedTypeLayout<VectorStore>()).writeNative((MutPtr((MutPtr((ptr).rawAddress + ((offset).toLong()))).rawAddress)).rawAddress, this)
    }

    public fun vecStoreLen(ptr: MutPtr, byteCount: UInt)
}

public fun vectorLoadVecLoadLenTTL(ptr: ConstPtr, byteCount: UInt): TTL = transmute(vll(byteCount, ptr))

public fun vectorLoadVecLoadBndryTTL(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<TTL> = transmute(vlbb(ptr, validateBlockBoundary(blockBoundary)))

public fun TTL.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun testVecLoadLen(ptr: ConstPtr, byteCount: UInt): VectorSignedInt = vectorSignedInt.vecLoadLen(ptr, byteCount)

public fun testVecLoadBndry(ptr: ConstPtr): MaybeUninit<VectorSignedInt> = vectorSignedInt.vecLoadBndry(ptr, 512)

public fun testVecStoreLen(vector: VectorSignedInt, ptr: MutPtr, byteCount: UInt) {
    vector.vecStoreLen(ptr, byteCount)
}

public interface VectorLoadPair<T, ElementType> {
    public fun vecLoadPair(a: ElementType, b: ElementType): T
}

public fun vectorLoadPairVecLoadPairVectorSignedLongLong(a: Long, b: Long): VectorSignedLongLong = vectorSignedLongLong(longArrayOf(a, b))

public fun vectorLoadPairVecLoadPairVectorUnsignedLongLong(a: ULong, b: ULong): VectorUnsignedLongLong = vectorUnsignedLongLong(ulongArrayOf(a, b))

public fun <T> pack(a: T, b: T, n: Int): T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = ShuffleMask.pack(); _simdArg0 }

public fun vpkh(a: I16x8, b: I16x8): I8x16 {
    val a1: I8x16 = transmute(a)
    val b1: I8x16 = transmute(b)
    return run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = ShuffleMask.pack(); _simdArg0 }
}

public fun vpkf(a: I32x4, b: I32x4): I16x8 {
    val a1: I16x8 = transmute(a)
    val b1: I16x8 = transmute(b)
    return run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = ShuffleMask.pack(); _simdArg0 }
}

public fun vpkg(a: I64x2, b: I64x2): I32x4 {
    val a1: I32x4 = transmute(a)
    val b1: I32x4 = transmute(b)
    return run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = ShuffleMask.pack(); _simdArg0 }
}

public interface VectorPack<Other, out Result> {
    public fun vecPack(b: Other): Result
}

public interface VectorPacks<Other, out Result> {
    public fun vecPacks(b: Other): Result
}

public interface VectorPacksu<Other, out Result> {
    public fun vecPacksu(b: Other): Result
}

public fun vpacksuh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar = vpklsh(vecMax(a, vectorSignedShort(List(8) { 0 })), vecMax(b, vectorSignedShort(List(8) { 0 })))

public fun vpacksuf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort = vpklsf(vecMax(a, vectorSignedInt(List(4) { 0 })), vecMax(b, vectorSignedInt(List(4) { 0 })))

public fun vpacksug(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt = vpklsg(vecMax(a, vectorSignedLongLong(List(2) { 0 })), vecMax(b, vectorSignedLongLong(List(2) { 0 })))

public interface VectorPacksCC<T, out Result> {
    public fun vecPacksCc(b: T): Pair<Result, Int>
}

public fun vpkshs(a: VectorSignedShort, b: VectorSignedShort): Pair<VectorSignedChar, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpkshs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedShort.vecPacksCc(b: VectorSignedShort): Pair<VectorSignedChar, Int> = vpkshs(this, b)

public fun vpklshs(a: VectorUnsignedShort, b: VectorUnsignedShort): Pair<VectorUnsignedChar, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklshs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vecPacksCc(b: VectorUnsignedShort): Pair<VectorUnsignedChar, Int> = vpklshs(this, b)

public fun vpksfs(a: VectorSignedInt, b: VectorSignedInt): Pair<VectorSignedShort, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpksfs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedInt.vecPacksCc(b: VectorSignedInt): Pair<VectorSignedShort, Int> = vpksfs(this, b)

public fun vpklsfs(a: VectorUnsignedInt, b: VectorUnsignedInt): Pair<VectorUnsignedShort, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsfs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vecPacksCc(b: VectorUnsignedInt): Pair<VectorUnsignedShort, Int> = vpklsfs(this, b)

public fun vpksgs(a: VectorSignedLongLong, b: VectorSignedLongLong): Pair<VectorSignedInt, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpksgs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedLongLong.vecPacksCc(b: VectorSignedLongLong): Pair<VectorSignedInt, Int> = vpksgs(this, b)

public fun vpklsgs(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): Pair<VectorUnsignedInt, Int> {
    val structVal = io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsgs(a, b)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedLongLong.vecPacksCc(b: VectorUnsignedLongLong): Pair<VectorUnsignedInt, Int> = vpklsgs(this, b)

public interface VectorPacksuCC<T, out Result> {
    public fun vecPacksuCc(b: T): Pair<Result, Int>
}

public fun VectorUnsignedShort.vecPacksuCc(b: VectorUnsignedShort): Pair<VectorUnsignedChar, Int> = vpklshs(this, b)

public fun VectorUnsignedInt.vecPacksuCc(b: VectorUnsignedInt): Pair<VectorUnsignedShort, Int> = vpklsfs(this, b)

public fun VectorUnsignedLongLong.vecPacksuCc(b: VectorUnsignedLongLong): Pair<VectorUnsignedInt, Int> = vpklsgs(this, b)

public interface VectorMadd<T> {
    public fun vecMadd(b: T, c: T): T

    public fun vecMsub(b: T, c: T): T
}

public fun <T> simdFms(a: T, b: T, c: T): T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

public fun VectorFloat.vecMadd(b: VectorFloat, c: VectorFloat): VectorFloat = vfmasb(this, b, c)

public fun VectorFloat.vecMsub(b: VectorFloat, c: VectorFloat): VectorFloat = vfmssb(this, b, c)

public fun VectorDouble.vecMadd(b: VectorDouble, c: VectorDouble): VectorDouble = vfmadb(this, b, c)

public fun VectorDouble.vecMsub(b: VectorDouble, c: VectorDouble): VectorDouble = vfmsdb(this, b, c)

public interface VectorUnpackh<out Result> {
    public fun vecUnpackh(): Result
}

public fun vuphb(a: VectorSignedChar): VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; _simdArg0 }

public fun vuphh(a: VectorSignedShort): VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; _simdArg0 }

public fun vuphf(a: VectorSignedInt): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; _simdArg0 }

public fun vuplhb(a: VectorUnsignedChar): VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; _simdArg0 }

public fun vuplhh(a: VectorUnsignedShort): VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; _simdArg0 }

public fun vuplhf(a: VectorUnsignedInt): VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; _simdArg0 }

public interface VectorUnpackl<out Result> {
    public fun vecUnpackl(): Result
}

public fun vuplb(a: VectorSignedChar): VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; _simdArg0 }

public fun vuplhw(a: VectorSignedShort): VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; _simdArg0 }

public fun vuplf(a: VectorSignedInt): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; _simdArg0 }

public fun vupllb(a: VectorUnsignedChar): VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; _simdArg0 }

public fun vupllh(a: VectorUnsignedShort): VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; _simdArg0 }

public fun vupllf(a: VectorUnsignedInt): VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; _simdArg0 }

public interface VectorAvg<Other, out Result> {
    public fun vecAvg(b: Other): Result
}

public interface VectorMule<Result, T> {
    public fun vecMule(b: T): Result
}

public fun vmeb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort {
    val elemsA: VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    val elemsB: VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmeh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt {
    val elemsA: VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    val elemsB: VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmef(a: VectorSignedInt, b: VectorSignedInt): VectorSignedLongLong {
    val elemsA: VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    val elemsB: VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmleb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort {
    val elemsA: VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    val elemsB: VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmleh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt {
    val elemsA: VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    val elemsB: VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmlef(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong {
    val elemsA: VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    val elemsB: VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun VectorSignedChar.vecMule(b: VectorSignedChar): VectorSignedShort = vmeb(transmute(this), transmute(b))

public fun VectorSignedShort.vecMule(b: VectorSignedShort): VectorSignedInt = vmeh(transmute(this), transmute(b))

public fun VectorSignedInt.vecMule(b: VectorSignedInt): VectorSignedLongLong = vmef(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecMule(b: VectorUnsignedChar): VectorUnsignedShort = vmleb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMule(b: VectorUnsignedShort): VectorUnsignedInt = vmleh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMule(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlef(transmute(this), transmute(b))

public interface VectorMulo<Result, T> {
    public fun vecMulo(b: T): Result
}

public fun vmob(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort {
    val elemsA: VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    val elemsB: VectorSignedShort = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmoh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt {
    val elemsA: VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    val elemsB: VectorSignedInt = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmof(a: VectorSignedInt, b: VectorSignedInt): VectorSignedLongLong {
    val elemsA: VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    val elemsB: VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmlob(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort {
    val elemsA: VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    val elemsB: VectorUnsignedShort = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmloh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt {
    val elemsA: VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    val elemsB: VectorUnsignedInt = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun vmlof(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong {
    val elemsA: VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    val elemsB: VectorUnsignedLongLong = run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = b; val _simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    return run { val _simdArg0 = elemsA; val _simdArg1 = elemsB; _simdArg0 }
}

public fun VectorSignedChar.vecMulo(b: VectorSignedChar): VectorSignedShort = vmob(transmute(this), transmute(b))

public fun VectorSignedShort.vecMulo(b: VectorSignedShort): VectorSignedInt = vmoh(transmute(this), transmute(b))

public fun VectorSignedInt.vecMulo(b: VectorSignedInt): VectorSignedLongLong = vmof(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecMulo(b: VectorUnsignedChar): VectorUnsignedShort = vmlob(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMulo(b: VectorUnsignedShort): VectorUnsignedInt = vmloh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMulo(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlof(transmute(this), transmute(b))

public interface VectorMulh<Result, T> {
    public fun vecMulh(b: T): Result
}

public fun VectorSignedChar.vecMulh(b: VectorSignedChar): VectorSignedChar = vecVmhb(transmute(this), transmute(b))

public fun VectorSignedShort.vecMulh(b: VectorSignedShort): VectorSignedShort = vecVmhh(transmute(this), transmute(b))

public fun VectorSignedInt.vecMulh(b: VectorSignedInt): VectorSignedInt = vecVmhf(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecMulh(b: VectorUnsignedChar): VectorUnsignedChar = vecVmlhb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMulh(b: VectorUnsignedShort): VectorUnsignedShort = vecVmlhh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMulh(b: VectorUnsignedInt): VectorUnsignedInt = vecVmlhf(transmute(this), transmute(b))

public interface VectorMeadd<T, Result> {
    public fun vecMeadd(b: T, c: Result): Result
}

public fun VectorSignedChar.vecMeadd(b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vecVmaeb(this, b, c)

public fun VectorSignedShort.vecMeadd(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vecVmaeh(this, b, c)

public fun VectorSignedInt.vecMeadd(b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vecVmaef(this, b, c)

public fun VectorUnsignedChar.vecMeadd(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVmaleb(this, b, c)

public fun VectorUnsignedShort.vecMeadd(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVmaleh(this, b, c)

public fun VectorUnsignedInt.vecMeadd(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmalef(this, b, c)

public interface VectorMoadd<T, Result> {
    public fun vecMoadd(b: T, c: Result): Result
}

public fun VectorSignedChar.vecMoadd(b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vecVmaob(this, b, c)

public fun VectorSignedShort.vecMoadd(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vecVmaoh(this, b, c)

public fun VectorSignedInt.vecMoadd(b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vecVmaof(this, b, c)

public fun VectorUnsignedChar.vecMoadd(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalob(this, b, c)

public fun VectorUnsignedShort.vecMoadd(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVmaloh(this, b, c)

public fun VectorUnsignedInt.vecMoadd(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmalof(this, b, c)

public interface VectorMhadd<T, Result> {
    public fun vecMhadd(b: T, c: Result): Result
}

public fun VectorSignedChar.vecMhadd(b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = vecVmahb(this, b, c)

public fun VectorSignedShort.vecMhadd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vecVmahh(this, b, c)

public fun VectorSignedInt.vecMhadd(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = vecVmahf(this, b, c)

public fun VectorUnsignedChar.vecMhadd(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = vecVmalhb(this, b, c)

public fun VectorUnsignedShort.vecMhadd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalhh(this, b, c)

public fun VectorUnsignedInt.vecMhadd(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = vecVmalhf(this, b, c)

public interface VectorMladd<T, Result> {
    public fun vecMladd(b: T, c: Result): Result
}

public fun <T> simdMladd(a: T, b: T, c: T): T = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = c; _simdArg0 }

public fun VectorSignedChar.vecMladd(b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = vecVmalIb(this, b, c)

public fun VectorSignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vecVmalIh(this, b, c)

public fun VectorSignedInt.vecMladd(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = vecVmalIf(this, b, c)

public fun VectorUnsignedChar.vecMladd(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = vecVmalUb(this, b, c)

public fun VectorUnsignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalUh(this, b, c)

public fun VectorUnsignedInt.vecMladd(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = vecVmalUf(this, b, c)

public interface VectorGfmsum<Result, T> {
    public fun vecGfmsum(b: T): Result
}

public fun VectorUnsignedChar.vecGfmsum(b: VectorUnsignedChar): VectorUnsignedShort = vecVgfmb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecGfmsum(b: VectorUnsignedShort): VectorUnsignedInt = vecVgfmh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecGfmsum(b: VectorUnsignedInt): VectorUnsignedLongLong = vecVgfmf(transmute(this), transmute(b))

public interface VectorGfmsumAccum<T, Result> {
    public fun vecGfmsumAccum(b: T, c: Result): Result
}

public fun VectorUnsignedChar.vecGfmsumAccum(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVgfmab(this, b, c)

public fun VectorUnsignedShort.vecGfmsumAccum(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVgfmah(this, b, c)

public fun VectorUnsignedInt.vecGfmsumAccum(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVgfmaf(this, b, c)

public fun vgef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorUnsignedInt {
    run {
        check(0u <= d && d < (1u shl 2)) { "D doesn't fit in 2 bits (unsigned)" }
    }
    val offset: UInt = run { val _simdArg0 = b; val _simdArg1 = d; 0u }
    val ptr = ConstPtr((c).rawAddress + ((offset.toInt()).toLong()))
    val value = (PrimitiveLayouts.U32).readNative((ptr).rawAddress)
    return run { val _simdArg0 = a; val _simdArg1 = d; val _simdArg2 = value; _simdArg0 }
}

public fun vgeg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= d && d < (1u shl 1)) { "D doesn't fit in 1 bits (unsigned)" }
    }
    val offset: ULong = run { val _simdArg0 = b; val _simdArg1 = d; 0uL }
    val ptr = ConstPtr((c).rawAddress + ((offset.toInt()).toLong()))
    val value = (PrimitiveLayouts.U64).readNative((ptr).rawAddress)
    return run { val _simdArg0 = a; val _simdArg1 = d; val _simdArg2 = value; _simdArg0 }
}

public interface VectorGatherElement<T, Element, Offset> {
    public fun vecGatherElement(b: Offset, c: ConstPtr, d: UInt): T
}

public fun VectorSignedInt.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorSignedInt = transmute(vgef(transmute(this), b, c, d))

public fun VectorBoolInt.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorBoolInt = transmute(vgef(transmute(this), b, c, d))

public fun VectorUnsignedInt.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorUnsignedInt = transmute(vgef(transmute(this), b, c, d))

public fun VectorSignedLongLong.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorSignedLongLong = transmute(vgeg(transmute(this), b, c, d))

public fun VectorBoolLongLong.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorBoolLongLong = transmute(vgeg(transmute(this), b, c, d))

public fun VectorUnsignedLongLong.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorUnsignedLongLong = transmute(vgeg(transmute(this), b, c, d))

public fun VectorFloat.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorFloat = transmute(vgef(transmute(this), b, c, d))

public fun VectorDouble.vecGatherElement(b: TU, c: ConstPtr, d: UInt): VectorDouble = transmute(vgeg(transmute(this), b, c, d))

public fun vscef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    run {
        check(0u <= d && d < (1u shl 2)) { "D doesn't fit in 2 bits (unsigned)" }
    }
    val value = run { val _simdArg0 = a; val _simdArg1 = d; _simdArg0 }
    val offset: UInt = run { val _simdArg0 = b; val _simdArg1 = d; 0u }
    val ptr = MutPtr((c).rawAddress + ((offset.toInt()).toLong()))
    (PrimitiveLayouts.U32).writeNative((ptr).rawAddress, value)
}

public fun vsceg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    run {
        check(0u <= d && d < (1u shl 1)) { "D doesn't fit in 1 bits (unsigned)" }
    }
    val value = run { val _simdArg0 = a; val _simdArg1 = d; _simdArg0 }
    val offset: ULong = run { val _simdArg0 = b; val _simdArg1 = d; 0uL }
    val ptr = MutPtr((c).rawAddress + ((offset.toInt()).toLong()))
    (PrimitiveLayouts.U64).writeNative((ptr).rawAddress, value)
}

public interface VectorScatterElement<Element, Offset> {
    public fun vecScatterElement(b: Offset, c: MutPtr, d: UInt)
}

public fun VectorSignedInt.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vscef(transmute(this), b, c, d)
}

public fun VectorBoolInt.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vscef(transmute(this), b, c, d)
}

public fun VectorUnsignedInt.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vscef(transmute(this), b, c, d)
}

public fun VectorSignedLongLong.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vsceg(transmute(this), b, c, d)
}

public fun VectorBoolLongLong.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vsceg(transmute(this), b, c, d)
}

public fun VectorUnsignedLongLong.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vsceg(transmute(this), b, c, d)
}

public fun VectorFloat.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vscef(transmute(this), b, c, d)
}

public fun VectorDouble.vecScatterElement(b: TU, c: MutPtr, d: UInt) {
    vsceg(transmute(this), b, c, d)
}

public interface VectorSel<Mask, T> {
    public fun vecSel(b: T, c: Mask): T
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: TU): VectorSignedChar {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorSignedChar.vecSelVectorSel(b: VectorSignedChar, c: TB): VectorSignedChar = this.vecSel(b, transmute(c))

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: TU): VectorSignedShort {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorSignedShort.vecSelVectorSel(b: VectorSignedShort, c: TB): VectorSignedShort = this.vecSel(b, transmute(c))

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: TU): VectorSignedInt {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorSignedInt.vecSelVectorSel(b: VectorSignedInt, c: TB): VectorSignedInt = this.vecSel(b, transmute(c))

public fun VectorSignedLongLong.vecSel(b: VectorSignedLongLong, c: TU): VectorSignedLongLong {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorSignedLongLong.vecSelVectorSel(b: VectorSignedLongLong, c: TB): VectorSignedLongLong = this.vecSel(b, transmute(c))

public fun VectorUnsignedChar.vecSel(b: VectorUnsignedChar, c: TU): VectorUnsignedChar {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorUnsignedChar.vecSelVectorSel(b: VectorUnsignedChar, c: TB): VectorUnsignedChar = this.vecSel(b, transmute(c))

public fun VectorUnsignedShort.vecSel(b: VectorUnsignedShort, c: TU): VectorUnsignedShort {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorUnsignedShort.vecSelVectorSel(b: VectorUnsignedShort, c: TB): VectorUnsignedShort = this.vecSel(b, transmute(c))

public fun VectorUnsignedInt.vecSel(b: VectorUnsignedInt, c: TU): VectorUnsignedInt {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorUnsignedInt.vecSelVectorSel(b: VectorUnsignedInt, c: TB): VectorUnsignedInt = this.vecSel(b, transmute(c))

public fun VectorUnsignedLongLong.vecSel(b: VectorUnsignedLongLong, c: TU): VectorUnsignedLongLong {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorUnsignedLongLong.vecSelVectorSel(b: VectorUnsignedLongLong, c: TB): VectorUnsignedLongLong = this.vecSel(b, transmute(c))

public fun VectorBoolChar.vecSel(b: VectorBoolChar, c: TU): VectorBoolChar {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorBoolChar.vecSelVectorSel(b: VectorBoolChar, c: TB): VectorBoolChar = this.vecSel(b, transmute(c))

public fun VectorBoolShort.vecSel(b: VectorBoolShort, c: TU): VectorBoolShort {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorBoolShort.vecSelVectorSel(b: VectorBoolShort, c: TB): VectorBoolShort = this.vecSel(b, transmute(c))

public fun VectorBoolInt.vecSel(b: VectorBoolInt, c: TU): VectorBoolInt {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorBoolInt.vecSelVectorSel(b: VectorBoolInt, c: TB): VectorBoolInt = this.vecSel(b, transmute(c))

public fun VectorBoolLongLong.vecSel(b: VectorBoolLongLong, c: TU): VectorBoolLongLong {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorBoolLongLong.vecSelVectorSel(b: VectorBoolLongLong, c: TB): VectorBoolLongLong = this.vecSel(b, transmute(c))

public fun VectorFloat.vecSel(b: VectorFloat, c: TU): VectorFloat {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorFloat.vecSelVectorSel(b: VectorFloat, c: TB): VectorFloat = this.vecSel(b, transmute(c))

public fun VectorDouble.vecSel(b: VectorDouble, c: TU): VectorDouble {
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = c; _simdArg0 }
    val a = run { val _simdArg0 = transmute(this); val _simdArg1 = run { val _simdArg0 = c; val _simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); _simdArg0 }; _simdArg0 }
    return transmute(run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 })
}

public fun VectorDouble.vecSelVectorSel(b: VectorDouble, c: TB): VectorDouble = this.vecSel(b, transmute(c))

public interface VectorFpTestDataClass<out Result> {
    public fun vecFpTestDataClass(cls: UInt): Pair<Result, Int>
}

public fun VectorFloat.vecFpTestDataClass(cls: UInt): Pair<VectorBoolInt, Int> {
    val structVal = vftcisb(this, cls)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorDouble.vecFpTestDataClass(cls: UInt): Pair<VectorBoolLongLong, Int> {
    val structVal = vftcidb(this, cls)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public interface VectorCompare<T> {
    public fun vecAllLt(other: T): Int

    public fun vecAllLe(other: T): Int

    public fun vecAllGt(other: T): Int

    public fun vecAllGe(other: T): Int
}

public fun VectorSignedChar.vecAllLt(other: VectorSignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedChar.vecAllLe(other: VectorSignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedChar.vecAllGt(other: VectorSignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedChar.vecAllGe(other: VectorSignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedChar.vecAllLt(other: VectorUnsignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedChar.vecAllLe(other: VectorUnsignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedChar.vecAllGt(other: VectorUnsignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedChar.vecAllGe(other: VectorUnsignedChar): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedShort.vecAllLt(other: VectorSignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedShort.vecAllLe(other: VectorSignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedShort.vecAllGt(other: VectorSignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedShort.vecAllGe(other: VectorSignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedShort.vecAllLt(other: VectorUnsignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedShort.vecAllLe(other: VectorUnsignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedShort.vecAllGt(other: VectorUnsignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedShort.vecAllGe(other: VectorUnsignedShort): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedInt.vecAllLt(other: VectorSignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedInt.vecAllLe(other: VectorSignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedInt.vecAllGt(other: VectorSignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedInt.vecAllGe(other: VectorSignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedInt.vecAllLt(other: VectorUnsignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedInt.vecAllLe(other: VectorUnsignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedInt.vecAllGt(other: VectorUnsignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedInt.vecAllGe(other: VectorUnsignedInt): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorFloat.vecAllLt(other: VectorFloat): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorFloat.vecAllLe(other: VectorFloat): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorFloat.vecAllGt(other: VectorFloat): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorFloat.vecAllGe(other: VectorFloat): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedLongLong.vecAllLt(other: VectorSignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedLongLong.vecAllLe(other: VectorSignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedLongLong.vecAllGt(other: VectorSignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorSignedLongLong.vecAllGe(other: VectorSignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllLt(other: VectorUnsignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllLe(other: VectorUnsignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllGt(other: VectorUnsignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllGe(other: VectorUnsignedLongLong): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorDouble.vecAllLt(other: VectorDouble): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorDouble.vecAllLe(other: VectorDouble): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorDouble.vecAllGt(other: VectorDouble): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public fun VectorDouble.vecAllGe(other: VectorDouble): Int = (run { val _simdArg0 = run { val _simdArg0 = this; val _simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); val res = (UnresolvedTypeLayout<TB>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<TB>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<TB>()).alignBytes)); res } }; false }).toInt()

public interface VectorTestMask<Mask> {
    public fun vecTestMask(other: Mask): Int
}

public fun VectorSignedShort.vecTestMask(other: TU): Int = vtm(transmute(this), transmute(other))

public fun VectorSignedLongLong.vecTestMask(other: TU): Int = vtm(transmute(this), transmute(other))

public fun VectorUnsignedShort.vecTestMask(other: TU): Int = vtm(transmute(this), transmute(other))

public fun VectorUnsignedLongLong.vecTestMask(other: TU): Int = vtm(transmute(this), transmute(other))

public fun VectorDouble.vecTestMask(other: TU): Int = vtm(transmute(this), transmute(other))

public interface VectorSearchString<T> {
    public fun vecSearchStringCc(b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int>

    public fun vecSearchStringUntilZeroCc(b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int>
}

public fun VectorSignedChar.vecSearchStringCc(b: VectorSignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedChar.vecSearchStringUntilZeroCc(b: VectorSignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolChar.vecSearchStringCc(b: VectorBoolChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolChar.vecSearchStringUntilZeroCc(b: VectorBoolChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vecSearchStringCc(b: VectorUnsignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vecSearchStringUntilZeroCc(b: VectorUnsignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedShort.vecSearchStringCc(b: VectorSignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedShort.vecSearchStringUntilZeroCc(b: VectorSignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolShort.vecSearchStringCc(b: VectorBoolShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolShort.vecSearchStringUntilZeroCc(b: VectorBoolShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vecSearchStringCc(b: VectorUnsignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vecSearchStringUntilZeroCc(b: VectorUnsignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedInt.vecSearchStringCc(b: VectorSignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedInt.vecSearchStringUntilZeroCc(b: VectorSignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolInt.vecSearchStringCc(b: VectorBoolInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolInt.vecSearchStringUntilZeroCc(b: VectorBoolInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vecSearchStringCc(b: VectorUnsignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vecSearchStringUntilZeroCc(b: VectorUnsignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmute(this), transmute(b), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun vcdgb(a: VectorSignedLongLong): VectorDouble = run { val _simdArg0 = a; _simdArg0 }

public fun vcdlgb(a: VectorUnsignedLongLong): VectorDouble = run { val _simdArg0 = a; _simdArg0 }

public interface VectorDouble {
    public fun vecDouble(): VectorDouble
}

public fun VectorSignedLongLong.vecDouble(): VectorDouble = vcdgb(this)

public fun VectorUnsignedLongLong.vecDouble(): VectorDouble = vcdlgb(this)

public fun vcefb(a: VectorSignedInt): VectorFloat = run { val _simdArg0 = a; _simdArg0 }

public fun vcelfb(a: VectorUnsignedInt): VectorFloat = run { val _simdArg0 = a; _simdArg0 }

public interface VectorFloat {
    public fun vecFloat(): VectorFloat
}

public fun VectorSignedInt.vecFloat(): VectorFloat = vcefb(this)

public fun VectorUnsignedInt.vecFloat(): VectorFloat = vcelfb(this)

public interface VectorExtendSigned64 {
    public fun vecExtendS64(): VectorSignedLongLong
}

public fun vsegb(a: VectorSignedChar): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x2.fromArray(listOf(7, 15)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x2>()).alignBytes)); res } }; _simdArg0 }

public fun vsegh(a: VectorSignedShort): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x2.fromArray(listOf(3, 7)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x2>()).alignBytes)); res } }; _simdArg0 }

public fun vsegf(a: VectorSignedInt): VectorSignedLongLong = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x2.fromArray(listOf(1, 3)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; _simdArg0 }

public fun VectorSignedChar.vecExtendS64(): VectorSignedLongLong = vsegb(this)

public fun VectorSignedShort.vecExtendS64(): VectorSignedLongLong = vsegh(this)

public fun VectorSignedInt.vecExtendS64(): VectorSignedLongLong = vsegf(this)

// NOTE: VectorSigned and VectorUnsigned make strong safety assumptions around floats.
// This is what C provides, but even IBM does not clearly document these constraints.
//
// https://doc.rust-lang.org/std/intrinsics/simd/fn.simdCast.html
public interface VectorSigned<out Result> {
    public fun vecSigned(): Result
}

public interface VectorUnsigned<out Result> {
    public fun vecUnsigned(): Result
}

public interface VectorCopyUntilZero<T> {
    public fun vecCpUntilZero(): T
}

public interface VectorCopyUntilZeroCC<T> {
    public fun vecCpUntilZeroCc(): Pair<T, Int>
}

public fun VectorSignedChar.vecCpUntilZeroCc(): Pair<VectorSignedChar, Int> {
    val structVal = vecVistrbs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCpUntilZeroCc(): Pair<VectorBoolChar, Int> {
    val structVal = vecVistrbs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCpUntilZeroCc(): Pair<VectorUnsignedChar, Int> {
    val structVal = vecVistrbs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCpUntilZeroCc(): Pair<VectorSignedShort, Int> {
    val structVal = vecVistrhs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCpUntilZeroCc(): Pair<VectorBoolShort, Int> {
    val structVal = vecVistrhs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCpUntilZeroCc(): Pair<VectorUnsignedShort, Int> {
    val structVal = vecVistrhs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCpUntilZeroCc(): Pair<VectorSignedInt, Int> {
    val structVal = vecVistrfs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCpUntilZeroCc(): Pair<VectorBoolInt, Int> {
    val structVal = vecVistrfs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCpUntilZeroCc(): Pair<VectorUnsignedInt, Int> {
    val structVal = vecVistrfs(transmute(this))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorSrdb<T> {
    public fun vecSrdb(b: T, c: UInt): T
}

public interface VectorSld<T> {
    public fun vecSld(b: T, c: UInt): T

    public fun vecSldw(b: T, c: UInt): T

    public fun vecSldb(b: T, c: UInt): T
}

public fun testVecSld(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSld(b)

public fun testVecSldw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSldw(b)

public fun testVecSldb(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSldb(b)

public fun testVecSrdb(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSrdb(b)

public fun VectorSignedChar.vecSld(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorSignedChar.vecSldw(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorSignedChar.vecSldb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorSignedChar.vecSrdb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorBoolChar.vecSld(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorBoolChar.vecSldw(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorBoolChar.vecSldb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorBoolChar.vecSrdb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorUnsignedChar.vecSld(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorUnsignedChar.vecSldw(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorUnsignedChar.vecSldb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorUnsignedChar.vecSrdb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorSignedShort.vecSld(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorSignedShort.vecSldw(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorSignedShort.vecSldb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorSignedShort.vecSrdb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorBoolShort.vecSld(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorBoolShort.vecSldw(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorBoolShort.vecSldb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorBoolShort.vecSrdb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorUnsignedShort.vecSld(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorUnsignedShort.vecSldw(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorUnsignedShort.vecSldb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorUnsignedShort.vecSrdb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorSignedInt.vecSld(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorSignedInt.vecSldw(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorSignedInt.vecSldb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorSignedInt.vecSrdb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorBoolInt.vecSld(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorBoolInt.vecSldw(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorBoolInt.vecSldb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorBoolInt.vecSrdb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorUnsignedInt.vecSld(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorUnsignedInt.vecSldw(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorUnsignedInt.vecSldb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorUnsignedInt.vecSrdb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorSignedLongLong.vecSld(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorSignedLongLong.vecSldw(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorSignedLongLong.vecSldb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorSignedLongLong.vecSrdb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorBoolLongLong.vecSld(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorBoolLongLong.vecSldw(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorBoolLongLong.vecSldb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorBoolLongLong.vecSrdb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorUnsignedLongLong.vecSld(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorUnsignedLongLong.vecSldw(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorUnsignedLongLong.vecSldb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorUnsignedLongLong.vecSrdb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorFloat.vecSld(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorFloat.vecSldw(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorFloat.vecSldb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorFloat.vecSrdb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public fun VectorDouble.vecSld(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 8u))
}

public fun VectorDouble.vecSldw(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u))
}

public fun VectorDouble.vecSldb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(transmute(this), transmute(b), c))
}

public fun VectorDouble.vecSrdb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(transmute(this), transmute(b), c))
}

public interface VectorCompareRange<T, out Result> {
    public fun vstrc(b: T, c: T, imm: UInt): Result

    public fun vstrcz(b: T, c: T, imm: UInt): Result

    public fun vstrcs(b: T, c: T, imm: UInt): Pair<Result, Int>

    public fun vstrczs(b: T, c: T, imm: UInt): Pair<Result, Int>
}

public fun validateCompareRangeImm(imm: UInt) {
    if (!when (imm) {
        0, 4, 8, 12 -> true
        else -> false
    }) {
        error("IMM needs to be one of 0, 4, 8, 12")
    }
}

public fun VectorUnsignedChar.vstrc(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrcb(this, b, c, imm)
}

public fun VectorUnsignedChar.vstrcz(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrczb(this, b, c, imm)
}

public fun VectorUnsignedChar.vstrcs(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrcbs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vstrczs(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczbs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vstrc(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrch(this, b, c, imm)
}

public fun VectorUnsignedShort.vstrcz(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrczh(this, b, c, imm)
}

public fun VectorUnsignedShort.vstrcs(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrchs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vstrczs(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczhs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vstrc(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrcf(this, b, c, imm)
}

public fun VectorUnsignedInt.vstrcz(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): TB {
    ::validateCompareRangeImm
    return vstrczf(this, b, c, imm)
}

public fun VectorUnsignedInt.vstrcs(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrcfs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vstrczs(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): Pair<TB, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczfs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public interface VectorComparePredicate<T, out Result> {
    public fun vecCmpgt(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

    public fun vecCmpge(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

    public fun vecCmplt(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

    public fun vecCmple(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }
}

public interface VectorEquality<T, out Result> {
    public fun vecCmpeq(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

    public fun vecCmpne(other: T): Result = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }
}

public interface VectorEqualityIdx<T, out Result> {
    public fun vecCmpeqIdx(other: T): Result

    public fun vecCmpneIdx(other: T): Result

    public fun vecCmpeqIdxCc(other: T): Pair<Result, Int>

    public fun vecCmpneIdxCc(other: T): Pair<Result, Int>

    public fun vecCmpeqOr0Idx(other: T): Result

    public fun vecCmpneOr0Idx(other: T): Result

    public fun vecCmpeqOr0IdxCc(other: T): Pair<Result, Int>

    public fun vecCmpneOr0IdxCc(other: T): Pair<Result, Int>
}

public fun VectorSignedChar.vecCmpeqIdx(other: VectorSignedChar): VectorSignedChar = transmute(vfeeb(transmute(this), transmute(other)))

public fun VectorSignedChar.vecCmpneIdx(other: VectorSignedChar): VectorSignedChar = transmute(vfeneb(transmute(this), transmute(other)))

public fun VectorSignedChar.vecCmpeqOr0Idx(other: VectorSignedChar): VectorSignedChar = transmute(vfeezb(transmute(this), transmute(other)))

public fun VectorSignedChar.vecCmpneOr0Idx(other: VectorSignedChar): VectorSignedChar = transmute(vfenezb(transmute(this), transmute(other)))

public fun VectorSignedChar.vecCmpeqIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpneIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpeqOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpneOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpeqIdx(other: VectorBoolChar): VectorUnsignedChar = transmute(vfeeb(transmute(this), transmute(other)))

public fun VectorBoolChar.vecCmpneIdx(other: VectorBoolChar): VectorUnsignedChar = transmute(vfeneb(transmute(this), transmute(other)))

public fun VectorBoolChar.vecCmpeqOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmute(vfeezb(transmute(this), transmute(other)))

public fun VectorBoolChar.vecCmpneOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmute(vfenezb(transmute(this), transmute(other)))

public fun VectorBoolChar.vecCmpeqIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpneIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpeqOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpneOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpeqIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmute(vfeeb(transmute(this), transmute(other)))

public fun VectorUnsignedChar.vecCmpneIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmute(vfeneb(transmute(this), transmute(other)))

public fun VectorUnsignedChar.vecCmpeqOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmute(vfeezb(transmute(this), transmute(other)))

public fun VectorUnsignedChar.vecCmpneOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmute(vfenezb(transmute(this), transmute(other)))

public fun VectorUnsignedChar.vecCmpeqIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpneIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpeqOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpneOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpeqIdx(other: VectorSignedShort): VectorSignedShort = transmute(vfeeh(transmute(this), transmute(other)))

public fun VectorSignedShort.vecCmpneIdx(other: VectorSignedShort): VectorSignedShort = transmute(vfeneh(transmute(this), transmute(other)))

public fun VectorSignedShort.vecCmpeqOr0Idx(other: VectorSignedShort): VectorSignedShort = transmute(vfeezh(transmute(this), transmute(other)))

public fun VectorSignedShort.vecCmpneOr0Idx(other: VectorSignedShort): VectorSignedShort = transmute(vfenezh(transmute(this), transmute(other)))

public fun VectorSignedShort.vecCmpeqIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpneIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpeqOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpneOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpeqIdx(other: VectorBoolShort): VectorUnsignedShort = transmute(vfeeh(transmute(this), transmute(other)))

public fun VectorBoolShort.vecCmpneIdx(other: VectorBoolShort): VectorUnsignedShort = transmute(vfeneh(transmute(this), transmute(other)))

public fun VectorBoolShort.vecCmpeqOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmute(vfeezh(transmute(this), transmute(other)))

public fun VectorBoolShort.vecCmpneOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmute(vfenezh(transmute(this), transmute(other)))

public fun VectorBoolShort.vecCmpeqIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpneIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpeqOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpneOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpeqIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmute(vfeeh(transmute(this), transmute(other)))

public fun VectorUnsignedShort.vecCmpneIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmute(vfeneh(transmute(this), transmute(other)))

public fun VectorUnsignedShort.vecCmpeqOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmute(vfeezh(transmute(this), transmute(other)))

public fun VectorUnsignedShort.vecCmpneOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmute(vfenezh(transmute(this), transmute(other)))

public fun VectorUnsignedShort.vecCmpeqIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpneIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpeqOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpneOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpeqIdx(other: VectorSignedInt): VectorSignedInt = transmute(vfeef(transmute(this), transmute(other)))

public fun VectorSignedInt.vecCmpneIdx(other: VectorSignedInt): VectorSignedInt = transmute(vfenef(transmute(this), transmute(other)))

public fun VectorSignedInt.vecCmpeqOr0Idx(other: VectorSignedInt): VectorSignedInt = transmute(vfeezf(transmute(this), transmute(other)))

public fun VectorSignedInt.vecCmpneOr0Idx(other: VectorSignedInt): VectorSignedInt = transmute(vfenezf(transmute(this), transmute(other)))

public fun VectorSignedInt.vecCmpeqIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpneIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpeqOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpneOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpeqIdx(other: VectorBoolInt): VectorUnsignedInt = transmute(vfeef(transmute(this), transmute(other)))

public fun VectorBoolInt.vecCmpneIdx(other: VectorBoolInt): VectorUnsignedInt = transmute(vfenef(transmute(this), transmute(other)))

public fun VectorBoolInt.vecCmpeqOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmute(vfeezf(transmute(this), transmute(other)))

public fun VectorBoolInt.vecCmpneOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmute(vfenezf(transmute(this), transmute(other)))

public fun VectorBoolInt.vecCmpeqIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpneIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpeqOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpneOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpeqIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmute(vfeef(transmute(this), transmute(other)))

public fun VectorUnsignedInt.vecCmpneIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmute(vfenef(transmute(this), transmute(other)))

public fun VectorUnsignedInt.vecCmpeqOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmute(vfeezf(transmute(this), transmute(other)))

public fun VectorUnsignedInt.vecCmpneOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmute(vfenezf(transmute(this), transmute(other)))

public fun VectorUnsignedInt.vecCmpeqIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpneIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpeqOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpneOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorExtract<T, out ElementType> {
    public fun vecExtract(a: T, b: Int): ElementType
}

public fun vlgvb(a: VectorUnsignedChar, b: Int): UByte = run { val _simdArg0 = a; val _simdArg1 = b.toUInt() % 16u; _simdArg0 }

public fun vlgvh(a: VectorUnsignedShort, b: Int): UShort = run { val _simdArg0 = a; val _simdArg1 = b.toUInt() % 8u; _simdArg0 }

public fun vlgvf(a: VectorUnsignedInt, b: Int): UInt = run { val _simdArg0 = a; val _simdArg1 = b.toUInt() % 4u; _simdArg0 }

public fun vlgvg(a: VectorUnsignedLongLong, b: Int): ULong = run { val _simdArg0 = a; val _simdArg1 = b.toUInt() % 2u; _simdArg0 }

public interface VectorInsert<T, ElementType> {
    public fun vecInsert(a: ElementType, b: T, c: Int): T
}

public interface VectorPromote<T, ElementType> {
    public fun vecPromote(a: ElementType, b: Int): MaybeUninit<T>
}

public fun vlvgb(a: UByte, b: VectorUnsignedChar, c: Int): VectorUnsignedChar = run { val _simdArg0 = b; val _simdArg1 = c.toUInt() % 16u; val _simdArg2 = a; _simdArg0 }

public fun vlvgh(a: UShort, b: VectorUnsignedShort, c: Int): VectorUnsignedShort = run { val _simdArg0 = b; val _simdArg1 = c.toUInt() % 8u; val _simdArg2 = a; _simdArg0 }

public fun vlvgf(a: UInt, b: VectorUnsignedInt, c: Int): VectorUnsignedInt = run { val _simdArg0 = b; val _simdArg1 = c.toUInt() % 4u; val _simdArg2 = a; _simdArg0 }

public fun vlvgg(a: ULong, b: VectorUnsignedLongLong, c: Int): VectorUnsignedLongLong = run { val _simdArg0 = b; val _simdArg1 = c.toUInt() % 2u; val _simdArg2 = a; _simdArg0 }

public interface VectorInsertAndZero<T, ElementType> {
    public fun vecInsertAndZero(a: ConstPtr): T
}

public fun vllezb(x: ConstPtr): VectorUnsignedChar = vectorUnsignedChar(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, (PrimitiveLayouts.U8).readNative((x).rawAddress), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u))

public fun vllezh(x: ConstPtr): VectorUnsignedShort = vectorUnsignedShort(ushortArrayOf(0u, 0u, 0u, (PrimitiveLayouts.U16).readNative((x).rawAddress), 0u, 0u, 0u, 0u))

public fun vllezf(x: ConstPtr): VectorUnsignedInt = vectorUnsignedInt(uintArrayOf(0u, (PrimitiveLayouts.U32).readNative((x).rawAddress), 0u, 0u))

public fun vllezg(x: ConstPtr): VectorUnsignedLongLong = vectorUnsignedLongLong(ulongArrayOf((PrimitiveLayouts.U64).readNative((x).rawAddress), 0uL))

public fun vectorExtractVecExtractVectorSignedChar(a: VectorSignedChar, b: Int): LTT = transmute(vlgvb(transmute(a), b))

public fun vectorInsertVecInsertVectorSignedChar(a: LTT, b: VectorSignedChar, c: Int): VectorSignedChar = transmute(vlvgb(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedChar(a: ConstPtr): VectorSignedChar = transmute(vllezb(a))

public fun vectorPromoteVecPromoteVectorSignedChar(a: LTT, c: Int): MaybeUninit<VectorSignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedChar>())
    return MaybeUninit.new(transmute(vlvgb(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vectorExtractVecExtractVectorUnsignedChar(a: VectorUnsignedChar, b: Int): LTT = transmute(vlgvb(transmute(a), b))

public fun vectorInsertVecInsertVectorUnsignedChar(a: LTT, b: VectorUnsignedChar, c: Int): VectorUnsignedChar = transmute(vlvgb(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedChar(a: ConstPtr): VectorUnsignedChar = transmute(vllezb(a))

public fun vectorPromoteVecPromoteVectorUnsignedChar(a: LTT, c: Int): MaybeUninit<VectorUnsignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedChar>())
    return MaybeUninit.new(transmute(vlvgb(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vectorExtractVecExtractVectorSignedShort(a: VectorSignedShort, b: Int): LTT = transmute(vlgvh(transmute(a), b))

public fun vectorInsertVecInsertVectorSignedShort(a: LTT, b: VectorSignedShort, c: Int): VectorSignedShort = transmute(vlvgh(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedShort(a: ConstPtr): VectorSignedShort = transmute(vllezh(a))

public fun vectorPromoteVecPromoteVectorSignedShort(a: LTT, c: Int): MaybeUninit<VectorSignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedShort>())
    return MaybeUninit.new(transmute(vlvgh(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun vectorExtractVecExtractVectorUnsignedShort(a: VectorUnsignedShort, b: Int): LTT = transmute(vlgvh(transmute(a), b))

public fun vectorInsertVecInsertVectorUnsignedShort(a: LTT, b: VectorUnsignedShort, c: Int): VectorUnsignedShort = transmute(vlvgh(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedShort(a: ConstPtr): VectorUnsignedShort = transmute(vllezh(a))

public fun vectorPromoteVecPromoteVectorUnsignedShort(a: LTT, c: Int): MaybeUninit<VectorUnsignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedShort>())
    return MaybeUninit.new(transmute(vlvgh(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vectorExtractVecExtractVectorSignedInt(a: VectorSignedInt, b: Int): LTT = transmute(vlgvf(transmute(a), b))

public fun vectorInsertVecInsertVectorSignedInt(a: LTT, b: VectorSignedInt, c: Int): VectorSignedInt = transmute(vlvgf(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedInt(a: ConstPtr): VectorSignedInt = transmute(vllezf(a))

public fun vectorPromoteVecPromoteVectorSignedInt(a: LTT, c: Int): MaybeUninit<VectorSignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedInt>())
    return MaybeUninit.new(transmute(vlvgf(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun vectorExtractVecExtractVectorUnsignedInt(a: VectorUnsignedInt, b: Int): LTT = transmute(vlgvf(transmute(a), b))

public fun vectorInsertVecInsertVectorUnsignedInt(a: LTT, b: VectorUnsignedInt, c: Int): VectorUnsignedInt = transmute(vlvgf(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedInt(a: ConstPtr): VectorUnsignedInt = transmute(vllezf(a))

public fun vectorPromoteVecPromoteVectorUnsignedInt(a: LTT, c: Int): MaybeUninit<VectorUnsignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedInt>())
    return MaybeUninit.new(transmute(vlvgf(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun vectorExtractVecExtractVectorSignedLongLong(a: VectorSignedLongLong, b: Int): LTT = transmute(vlgvg(transmute(a), b))

public fun vectorInsertVecInsertVectorSignedLongLong(a: LTT, b: VectorSignedLongLong, c: Int): VectorSignedLongLong = transmute(vlvgg(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedLongLong(a: ConstPtr): VectorSignedLongLong = transmute(vllezg(a))

public fun vectorPromoteVecPromoteVectorSignedLongLong(a: LTT, c: Int): MaybeUninit<VectorSignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedLongLong>())
    return MaybeUninit.new(transmute(vlvgg(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun vectorExtractVecExtractVectorUnsignedLongLong(a: VectorUnsignedLongLong, b: Int): LTT = transmute(vlgvg(transmute(a), b))

public fun vectorInsertVecInsertVectorUnsignedLongLong(a: LTT, b: VectorUnsignedLongLong, c: Int): VectorUnsignedLongLong = transmute(vlvgg(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedLongLong(a: ConstPtr): VectorUnsignedLongLong = transmute(vllezg(a))

public fun vectorPromoteVecPromoteVectorUnsignedLongLong(a: LTT, c: Int): MaybeUninit<VectorUnsignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedLongLong>())
    return MaybeUninit.new(transmute(vlvgg(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun vectorExtractVecExtractVectorFloat(a: VectorFloat, b: Int): LTT = transmute(vlgvf(transmute(a), b))

public fun vectorInsertVecInsertVectorFloat(a: LTT, b: VectorFloat, c: Int): VectorFloat = transmute(vlvgf(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorFloat(a: ConstPtr): VectorFloat = transmute(vllezf(a))

public fun vectorPromoteVecPromoteVectorFloat(a: LTT, c: Int): MaybeUninit<VectorFloat> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorFloat>())
    return MaybeUninit.new(transmute(vlvgf(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorFloat>())
}

public fun vectorExtractVecExtractVectorDouble(a: VectorDouble, b: Int): LTT = transmute(vlgvg(transmute(a), b))

public fun vectorInsertVecInsertVectorDouble(a: LTT, b: VectorDouble, c: Int): VectorDouble = transmute(vlvgg(transmute(a), transmute(b), c))

public fun vectorInsertAndZeroVecInsertAndZeroVectorDouble(a: ConstPtr): VectorDouble = transmute(vllezg(a))

public fun vectorPromoteVecPromoteVectorDouble(a: LTT, c: Int): MaybeUninit<VectorDouble> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorDouble>())
    return MaybeUninit.new(transmute(vlvgg(transmute(a), transmute(b), c)), UnresolvedTypeLayout<VectorDouble>())
}

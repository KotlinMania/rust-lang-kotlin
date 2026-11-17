// port-lint: source s390x/vector.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
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
import io.github.kotlinmania.rustlang.corearch.s390x.vector.maxnumV2f64
import io.github.kotlinmania.rustlang.corearch.s390x.vector.maxnumV4f32
import io.github.kotlinmania.rustlang.corearch.s390x.vector.minnumV2f64
import io.github.kotlinmania.rustlang.corearch.s390x.vector.minnumV4f32
import io.github.kotlinmania.rustlang.corearch.s390x.vector.nearbyintV2f64
import io.github.kotlinmania.rustlang.corearch.s390x.vector.nearbyintV4f32
import io.github.kotlinmania.rustlang.corearch.s390x.vector.roundevenV2f64
import io.github.kotlinmania.rustlang.corearch.s390x.vector.roundevenV4f32
import io.github.kotlinmania.rustlang.corearch.s390x.vector.validateBlockBoundary
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavgb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavgf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavgg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavgh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavglb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavglf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavglg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vavglh
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
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vftcidb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vftcisb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmab
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmaf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmah
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vgfmh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrfs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vistrhs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vlbb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vll
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaeb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaeh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmahb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmahf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmahh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaleb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalef
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaleh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalob
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmalof
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaloh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaob
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaof
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmaoh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmlhb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmlhf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vmlhh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vperm
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpklsh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpksf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpksg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vpksh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vscbib
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vscbif
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vscbig
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vscbih
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vslb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsra
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsrab
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsrl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsrlb
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
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumgf
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumgh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.vsumh
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
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x8

public interface VectorNeg<T> {
    public fun vecNeg(): T
}

// Z vector intrinsic      C23 math.h  LLVM IR         ISO/IEC 60559 operation        inexact  vfidb parameters
//
// vecRint                rint        llvm.rint       roundToIntegralExact           yes      0, 0
// vecRoundc              nearbyint   llvm.nearbyint  n/a                            no       4, 0
// vecFloor / vecRoundm  floor       llvm.floor      roundToIntegralTowardNegative  no       4, 7
// vecCeil / vecRoundp   ceil        llvm.ceil       roundToIntegralTowardPositive  no       4, 6
// vecTrunc / vecRoundz  trunc       llvm.trunc      roundToIntegralTowardZero      no       4, 5
// vecRound               roundeven   llvm.roundeven  roundToIntegralTiesToEven      no       4, 4
// n/a                     round       llvm.round      roundToIntegralTiesAway        no       4, 1
// `simdRoundTiesEven` is implemented as `llvm.rint`.
// Note(llvm): https://github.com/llvm/llvm-project/issues/153655
// Other targets can use a min/max for the saturation + a truncation.
// NOTE: `vuplh` is used for "unpack logical high", hence `vuplhw`.
public fun VectorSignedChar.vecNeg(): VectorSignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedChar.vecNeg(): VectorUnsignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedShort.vecNeg(): VectorSignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedShort.vecNeg(): VectorUnsignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedInt.vecNeg(): VectorSignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedInt.vecNeg(): VectorUnsignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedLongLong.vecNeg(): VectorSignedLongLong = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedLongLong.vecNeg(): VectorUnsignedLongLong = run { val simdArg0 = this; simdArg0 }

public fun VectorFloat.vecNeg(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public fun VectorDouble.vecNeg(): VectorDouble = run { val simdArg0 = this; simdArg0 }

public interface VectorAdd<Other, out Result> {
    public fun vecAdd(other: Other): Result
}

public fun vaSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaSc(this, other)

public fun vaUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaUc(this, other)

public fun vaSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaSh(this, other)

public fun vaUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaUh(this, other)

public fun vaSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaSf(this, other)

public fun vaUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaUf(this, other)

public fun vaSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaSg(this, other)

public fun vaUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaUg(this, other)

public fun vaScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedChar.vecAdd(other: VectorBoolChar): VectorSignedChar = vaScBc(this, other)

public fun vaUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedChar.vecAdd(other: VectorBoolChar): VectorUnsignedChar = vaUcBc(this, other)

public fun vaShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedShort.vecAdd(other: VectorBoolShort): VectorSignedShort = vaShBh(this, other)

public fun vaUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedShort.vecAdd(other: VectorBoolShort): VectorUnsignedShort = vaUhBh(this, other)

public fun vaSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedInt.vecAdd(other: VectorBoolInt): VectorSignedInt = vaSfBf(this, other)

public fun vaUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedInt.vecAdd(other: VectorBoolInt): VectorUnsignedInt = vaUfBf(this, other)

public fun vaSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedLongLong.vecAdd(other: VectorBoolLongLong): VectorSignedLongLong = vaSgBg(this, other)

public fun vaUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedLongLong.vecAdd(other: VectorBoolLongLong): VectorUnsignedLongLong = vaUgBg(this, other)

public fun vaBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaBcSc(this, other)

public fun vaBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaBcUc(this, other)

public fun vaBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaBhSh(this, other)

public fun vaBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaBhUh(this, other)

public fun vaBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaBfSf(this, other)

public fun vaBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaBfUf(this, other)

public fun vaBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaBgSg(this, other)

public fun vaBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaBgUg(this, other)

public fun vaDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorDouble.vecAdd(other: VectorDouble): VectorDouble = vaDouble(this, other)

public fun vaFloat(a: VectorFloat, b: VectorFloat): VectorFloat = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorFloat.vecAdd(other: VectorFloat): VectorFloat = vaFloat(this, other)

public interface VectorSub<Other, out Result> {
    public fun vecSub(other: Other): Result
}

public fun vsSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsSc(this, other)

public fun vsUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsUc(this, other)

public fun vsSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsSh(this, other)

public fun vsUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsUh(this, other)

public fun vsSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsSf(this, other)

public fun vsUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsUf(this, other)

public fun vsSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsSg(this, other)

public fun vsUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsUg(this, other)

public fun vsScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedChar.vecSub(other: VectorBoolChar): VectorSignedChar = vsScBc(this, other)

public fun vsUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedChar.vecSub(other: VectorBoolChar): VectorUnsignedChar = vsUcBc(this, other)

public fun vsShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedShort.vecSub(other: VectorBoolShort): VectorSignedShort = vsShBh(this, other)

public fun vsUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedShort.vecSub(other: VectorBoolShort): VectorUnsignedShort = vsUhBh(this, other)

public fun vsSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedInt.vecSub(other: VectorBoolInt): VectorSignedInt = vsSfBf(this, other)

public fun vsUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedInt.vecSub(other: VectorBoolInt): VectorUnsignedInt = vsUfBf(this, other)

public fun vsSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorSignedLongLong.vecSub(other: VectorBoolLongLong): VectorSignedLongLong = vsSgBg(this, other)

public fun vsUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorUnsignedLongLong.vecSub(other: VectorBoolLongLong): VectorUnsignedLongLong = vsUgBg(this, other)

public fun vsBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsBcSc(this, other)

public fun vsBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsBcUc(this, other)

public fun vsBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsBhSh(this, other)

public fun vsBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsBhUh(this, other)

public fun vsBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsBfSf(this, other)

public fun vsBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsBfUf(this, other)

public fun vsBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsBgSg(this, other)

public fun vsBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 })

public fun VectorBoolLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsBgUg(this, other)

public fun vsDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorDouble.vecSub(other: VectorDouble): VectorDouble = vsDouble(this, other)

public fun vsFloat(a: VectorFloat, b: VectorFloat): VectorFloat = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorFloat.vecSub(other: VectorFloat): VectorFloat = vsFloat(this, other)

public interface VectorMul<T> {
    public fun vecMul(b: T): T
}

public fun vmlSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedChar.vecMul(other: VectorSignedChar): VectorSignedChar = vmlSc(this, other)

public fun vmlUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedChar.vecMul(other: VectorUnsignedChar): VectorUnsignedChar = vmlUc(this, other)

public fun vmlSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedShort.vecMul(other: VectorSignedShort): VectorSignedShort = vmlSh(this, other)

public fun vmlUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedShort.vecMul(other: VectorUnsignedShort): VectorUnsignedShort = vmlUh(this, other)

public fun vmlSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorSignedInt.vecMul(other: VectorSignedInt): VectorSignedInt = vmlSf(this, other)

public fun vmlUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorUnsignedInt.vecMul(other: VectorUnsignedInt): VectorUnsignedInt = vmlUf(this, other)

public fun VectorSignedLongLong.vecMul(other: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = this; val simdArg1 = other; simdArg0 })

public fun VectorUnsignedLongLong.vecMul(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; val simdArg1 = other; simdArg0 })

public fun VectorFloat.vecMul(other: VectorFloat): VectorFloat = transmute(run { val simdArg0 = this; val simdArg1 = other; simdArg0 })

public fun vmlDouble(a: VectorDouble, b: VectorDouble): VectorDouble = transmute(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun VectorDouble.vecMul(other: VectorDouble): VectorDouble = vmlDouble(this, other)

public interface VectorMax<Other, out Result> {
    public fun vecMax(b: Other): Result
}

public fun vecVmxsc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedChar() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedChar.vecMax(other: VectorSignedChar): VectorSignedChar = vecVmxsc(this, other)

public fun vecVmxslc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedChar() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedChar.vecMax(other: VectorUnsignedChar): VectorUnsignedChar = vecVmxslc(this, other)

public fun vecVmxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedShort() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedShort.vecMax(other: VectorSignedShort): VectorSignedShort = vecVmxsh(this, other)

public fun vecVmxslh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedShort() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedShort.vecMax(other: VectorUnsignedShort): VectorUnsignedShort = vecVmxslh(this, other)

public fun vecVmxsf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedInt() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedInt.vecMax(other: VectorSignedInt): VectorSignedInt = vecVmxsf(this, other)

public fun vecVmxslf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedInt() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedInt.vecMax(other: VectorUnsignedInt): VectorUnsignedInt = vecVmxslf(this, other)

public fun vecVmxsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedLongLong() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedLongLong.vecMax(other: VectorSignedLongLong): VectorSignedLongLong = vecVmxsg(this, other)

public fun vecVmxslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedLongLong() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedLongLong.vecMax(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmxslg(this, other)

public fun vecVfmaxsb(a: VectorFloat, b: VectorFloat): VectorFloat = maxnumV4f32(a, b)

public fun vecVfmaxdb(a: VectorDouble, b: VectorDouble): VectorDouble = maxnumV2f64(a, b)

public fun VectorFloat.vecMax(b: VectorFloat): VectorFloat = vecVfmaxsb(this, b)

public fun VectorDouble.vecMax(b: VectorDouble): VectorDouble = vecVfmaxdb(this, b)

public interface VectorMin<Other, out Result> {
    public fun vecMin(b: Other): Result
}

public fun vecVmnsc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedChar() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedChar.vecMin(other: VectorSignedChar): VectorSignedChar = vecVmnsc(this, other)

public fun vecVmnslc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedChar() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedChar.vecMin(other: VectorUnsignedChar): VectorUnsignedChar = vecVmnslc(this, other)

public fun vecVmnsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedShort() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedShort.vecMin(other: VectorSignedShort): VectorSignedShort = vecVmnsh(this, other)

public fun vecVmnslh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedShort() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedShort.vecMin(other: VectorUnsignedShort): VectorUnsignedShort = vecVmnslh(this, other)

public fun vecVmnsf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedInt() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedInt.vecMin(other: VectorSignedInt): VectorSignedInt = vecVmnsf(this, other)

public fun vecVmnslf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedInt() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedInt.vecMin(other: VectorUnsignedInt): VectorUnsignedInt = vecVmnslf(this, other)

public fun vecVmnsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorSignedLongLong() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorSignedLongLong.vecMin(other: VectorSignedLongLong): VectorSignedLongLong = vecVmnsg(this, other)

public fun vecVmnslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; VectorUnsignedLongLong() }; val simdArg1 = a; val simdArg2 = b; simdArg1 }

public fun VectorUnsignedLongLong.vecMin(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmnslg(this, other)

public fun vecVfminsb(a: VectorFloat, b: VectorFloat): VectorFloat = minnumV4f32(a, b)

public fun vecVfmindb(a: VectorDouble, b: VectorDouble): VectorDouble = minnumV2f64(a, b)

public fun VectorFloat.vecMin(b: VectorFloat): VectorFloat = vecVfminsb(this, b)

public fun VectorDouble.vecMin(b: VectorDouble): VectorDouble = vecVfmindb(this, b)

public interface VectorAbs<T> {
    public fun vecAbs(): T
}

public fun vecAbsI8(v: VectorSignedChar): VectorSignedChar = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedChar.vecAbs(): VectorSignedChar = vecAbsI8(this)

public fun vecAbsI16(v: VectorSignedShort): VectorSignedShort = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedShort.vecAbs(): VectorSignedShort = vecAbsI16(this)

public fun vecAbsI32(v: VectorSignedInt): VectorSignedInt = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedInt.vecAbs(): VectorSignedInt = vecAbsI32(this)

public fun vecAbsI64(v: VectorSignedLongLong): VectorSignedLongLong = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedLongLong.vecAbs(): VectorSignedLongLong = vecAbsI64(this)

public fun vecAbsF32(v: VectorFloat): VectorFloat = run { val simdArg0 = v; simdArg0 }

public fun vecAbsF64(v: VectorDouble): VectorDouble = run { val simdArg0 = v; simdArg0 }

public fun VectorFloat.vecAbs(): VectorFloat = vecAbsF32(this)

public fun VectorDouble.vecAbs(): VectorDouble = vecAbsF64(this)

public interface VectorNabs<T> {
    public fun vecNabs(): T
}

public fun vecNabsF32(a: VectorFloat): VectorFloat = run { val simdArg0 = run { val simdArg0 = a; simdArg0 }; simdArg0 }

public fun vecNabsF64(a: VectorDouble): VectorDouble = run { val simdArg0 = run { val simdArg0 = a; simdArg0 }; simdArg0 }

public fun VectorFloat.vecNabs(): VectorFloat = vecNabsF32(this)

public fun VectorDouble.vecNabs(): VectorDouble = vecNabsF64(this)

public interface VectorNmsub<T> {
    public fun vecNmsub(b: T, c: T): T
}

public fun vecNmsubF32(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = run { val simdArg0 = c; simdArg0 }; simdArg0 }; simdArg0 }

public fun VectorFloat.vecNmsub(b: VectorFloat, c: VectorFloat): VectorFloat = vecNmsubF32(this, b, c)

public fun vecNmsubF64(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = run { val simdArg0 = c; simdArg0 }; simdArg0 }; simdArg0 }

public fun VectorDouble.vecNmsub(b: VectorDouble, c: VectorDouble): VectorDouble = vecNmsubF64(this, b, c)

public interface VectorNmadd<T> {
    public fun vecNmadd(b: T, c: T): T
}

public fun vecNmaddF32(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }; simdArg0 }

public fun VectorFloat.vecNmadd(b: VectorFloat, c: VectorFloat): VectorFloat = vecNmaddF32(this, b, c)

public fun vecNmaddF64(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }; simdArg0 }

public fun VectorDouble.vecNmadd(b: VectorDouble, c: VectorDouble): VectorDouble = vecNmaddF64(this, b, c)

public interface VectorSplat<T> {
    public fun vecSplat(imm: UInt): T
}

public fun vrepb(a: VectorSignedChar, imm2: UInt): VectorSignedChar {
    run {
        check(0u <= imm2 && imm2 < (1u shl 4)) { "IMM2 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(UIntArray(16) { imm2 }); VectorSignedChar() }
}

public fun vreph(a: VectorSignedShort, imm2: UInt): VectorSignedShort {
    run {
        check(0u <= imm2 && imm2 < (1u shl 3)) { "IMM2 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(UIntArray(8) { imm2 }); VectorSignedShort() }
}

public fun vrepf(a: VectorSignedInt, imm2: UInt): VectorSignedInt {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(UIntArray(4) { imm2 }); VectorSignedInt() }
}

public fun vrepg(a: VectorSignedLongLong, imm2: UInt): VectorSignedLongLong {
    run {
        check(0u <= imm2 && imm2 < (1u shl 1)) { "IMM2 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(UIntArray(2) { imm2 }); VectorSignedLongLong() }
}

public fun VectorSignedChar.vecSplat(imm: UInt): VectorSignedChar = transmute(vrepb(this, imm))

public fun VectorUnsignedChar.vecSplat(imm: UInt): VectorUnsignedChar = transmute(vrepb(this, imm))

public fun VectorBoolChar.vecSplat(imm: UInt): VectorBoolChar = transmute(vrepb(this, imm))

public fun VectorSignedShort.vecSplat(imm: UInt): VectorSignedShort = transmute(vreph(this, imm))

public fun VectorUnsignedShort.vecSplat(imm: UInt): VectorUnsignedShort = transmute(vreph(this, imm))

public fun VectorBoolShort.vecSplat(imm: UInt): VectorBoolShort = transmute(vreph(this, imm))

public fun VectorSignedInt.vecSplat(imm: UInt): VectorSignedInt = transmute(vrepf(this, imm))

public fun VectorUnsignedInt.vecSplat(imm: UInt): VectorUnsignedInt = transmute(vrepf(this, imm))

public fun VectorBoolInt.vecSplat(imm: UInt): VectorBoolInt = transmute(vrepf(this, imm))

public fun VectorSignedLongLong.vecSplat(imm: UInt): VectorSignedLongLong = transmute(vrepg(this, imm))

public fun VectorUnsignedLongLong.vecSplat(imm: UInt): VectorUnsignedLongLong = transmute(vrepg(this, imm))

public fun VectorBoolLongLong.vecSplat(imm: UInt): VectorBoolLongLong = transmute(vrepg(this, imm))

public fun VectorFloat.vecSplat(imm: UInt): VectorFloat = transmute(vrepf(this, imm))

public fun VectorDouble.vecSplat(imm: UInt): VectorDouble = transmute(vrepg(this, imm))

public interface VectorSplats<Output, Result> {
    public fun vecSplats(): Output
}

public fun vecSplatsU8(v: UByte): VectorUnsignedChar = transmute(Simd<UByte>(List(16) { v }))

public fun UByte.vecSplats(): VectorUnsignedChar = vecSplatsU8(this)

public fun vecSplatsI8(v: Byte): VectorSignedChar = transmute(Simd<Byte>(List(16) { v }))

public fun Byte.vecSplats(): VectorSignedChar = vecSplatsI8(this)

public fun vecSplatsU16(v: UShort): VectorUnsignedShort = transmute(Simd<UShort>(List(8) { v }))

public fun UShort.vecSplats(): VectorUnsignedShort = vecSplatsU16(this)

public fun vecSplatsI16(v: Short): VectorSignedShort = transmute(Simd<Short>(List(8) { v }))

public fun Short.vecSplats(): VectorSignedShort = vecSplatsI16(this)

public fun vecSplatsU32(v: UInt): VectorUnsignedInt = transmute(Simd<UInt>(List(4) { v }))

public fun UInt.vecSplats(): VectorUnsignedInt = vecSplatsU32(this)

public fun vecSplatsI32(v: Int): VectorSignedInt = transmute(Simd<Int>(List(4) { v }))

public fun Int.vecSplats(): VectorSignedInt = vecSplatsI32(this)

public fun vecSplatsU64(v: ULong): VectorUnsignedLongLong = transmute(Simd<ULong>(List(2) { v }))

public fun ULong.vecSplats(): VectorUnsignedLongLong = vecSplatsU64(this)

public fun vecSplatsI64(v: Long): VectorSignedLongLong = transmute(Simd<Long>(List(2) { v }))

public fun Long.vecSplats(): VectorSignedLongLong = vecSplatsI64(this)

public fun vecSplatsF32(v: Float): VectorFloat = transmute(Simd<Float>(List(4) { v }))

public fun Float.vecSplats(): VectorFloat = vecSplatsF32(this)

public fun vecSplatsF64(v: Double): VectorDouble = transmute(Simd<Double>(List(2) { v }))

public fun Double.vecSplats(): VectorDouble = vecSplatsF64(this)

public fun UByte.vecSplatsVectorBoolChar(): VectorBoolChar = transmute(Simd<UByte>(List(16) { this }))

public fun Byte.vecSplatsVectorBoolChar(): VectorBoolChar = transmute(Simd<Byte>(List(16) { this }))

public fun UShort.vecSplatsVectorBoolShort(): VectorBoolShort = transmute(Simd<UShort>(List(8) { this }))

public fun Short.vecSplatsVectorBoolShort(): VectorBoolShort = transmute(Simd<Short>(List(8) { this }))

public fun UInt.vecSplatsVectorBoolInt(): VectorBoolInt = transmute(Simd<UInt>(List(4) { this }))

public fun Int.vecSplatsVectorBoolInt(): VectorBoolInt = transmute(Simd<Int>(List(4) { this }))

public fun ULong.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmute(Simd<ULong>(List(2) { this }))

public fun Long.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmute(Simd<Long>(List(2) { this }))

public interface CountBits<out Result> {
    public fun vecCntlz(): Result

    public fun vecCnttz(): Result

    public fun vecPopcnt(): Result
}

public fun VectorSignedChar.vecCntlz(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedChar.vecCnttz(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedChar.vecPopcnt(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedChar.vecCntlz(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedChar.vecCnttz(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedChar.vecPopcnt(): VectorUnsignedChar = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedShort.vecCntlz(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedShort.vecCnttz(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedShort.vecPopcnt(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedShort.vecCntlz(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedShort.vecCnttz(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedShort.vecPopcnt(): VectorUnsignedShort = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedInt.vecCntlz(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedInt.vecCnttz(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedInt.vecPopcnt(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedInt.vecCntlz(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedInt.vecCnttz(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedInt.vecPopcnt(): VectorUnsignedInt = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedLongLong.vecCntlz(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedLongLong.vecCnttz(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorSignedLongLong.vecPopcnt(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedLongLong.vecCntlz(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedLongLong.vecCnttz(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun VectorUnsignedLongLong.vecPopcnt(): VectorUnsignedLongLong = transmute(run { val simdArg0 = this; simdArg0 })

public fun vecClzbSigned(a: VectorSignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzhSigned(a: VectorSignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzfSigned(a: VectorSignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzgSigned(a: VectorSignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzhUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecClzgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzbSigned(a: VectorSignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzhSigned(a: VectorSignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzfSigned(a: VectorSignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzgSigned(a: VectorSignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzhUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecCtzgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctbSigned(a: VectorSignedChar): VectorSignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopcthSigned(a: VectorSignedShort): VectorSignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctfSigned(a: VectorSignedInt): VectorSignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctgSigned(a: VectorSignedLongLong): VectorSignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopcthUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val simdArg0 = a; simdArg0 })

public fun vecVpopctgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = transmute(run { val simdArg0 = a; simdArg0 })

public interface VectorAnd<Other, out Result> {
    public fun vecAnd(b: Other): Result
}

public fun VectorUnsignedChar.vecAnd(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecAnd(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecAnd(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAnd(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAnd(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecAnd(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAnd(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAnd(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecAnd(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAnd(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAnd(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecAnd(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAnd(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAnd(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecAnd(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAnd(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAnd(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecAnd(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecAnd(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecAnd(b: VectorBoolLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecAnd(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecAnd(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecAnd(b: VectorBoolLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecAnd(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 }

public interface VectorOr<Other, out Result> {
    public fun vecOr(b: Other): Result
}

public fun VectorUnsignedChar.vecOr(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecOr(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecOr(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecOr(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecOr(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecOr(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecOr(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecOr(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecOr(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecOr(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecOr(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecOr(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecOr(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecOr(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecOr(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecOr(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecOr(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecOr(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecOr(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecOr(b: VectorBoolLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecOr(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecOr(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecOr(b: VectorBoolLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecOr(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 }

public interface VectorXor<Other, out Result> {
    public fun vecXor(b: Other): Result
}

public fun VectorUnsignedChar.vecXor(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecXor(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecXor(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecXor(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecXor(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecXor(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecXor(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecXor(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecXor(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecXor(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecXor(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecXor(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecXor(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecXor(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecXor(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecXor(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecXor(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecXor(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecXor(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecXor(b: VectorBoolLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecXor(b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecXor(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecXor(b: VectorBoolLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); simdArg0 }

public fun VectorBoolLongLong.vecXor(b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun nor(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    val b1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    return transmute(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 })
}

public interface VectorNor<Other, out Result> {
    public fun vecNor(b: Other): Result
}

public fun VectorBoolChar.vecNor(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNor(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNor(b: VectorSignedChar): VectorSignedChar = nor(this, b)

public fun VectorBoolShort.vecNor(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNor(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNor(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecNor(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNor(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNor(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecNor(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecNor(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecNor(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecNor(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(nor(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun nand(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    val b1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    return transmute(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 })
}

public interface VectorNand<Other, out Result> {
    public fun vecNand(b: Other): Result
}

public fun VectorBoolChar.vecNand(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNand(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNand(b: VectorSignedChar): VectorSignedChar = nand(this, b)

public fun VectorBoolShort.vecNand(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNand(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNand(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecNand(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNand(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNand(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecNand(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecNand(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecNand(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecNand(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(nand(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun eqv(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    val b1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    return transmute(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 })
}

public interface VectorEqv<Other, out Result> {
    public fun vecEqv(b: Other): Result
}

public fun VectorBoolChar.vecEqv(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecEqv(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecEqv(b: VectorSignedChar): VectorSignedChar = eqv(this, b)

public fun VectorBoolShort.vecEqv(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecEqv(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecEqv(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecEqv(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecEqv(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecEqv(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecEqv(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecEqv(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecEqv(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecEqv(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(eqv(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun andc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmute(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 })
}

public interface VectorAndc<Other, out Result> {
    public fun vecAndc(b: Other): Result
}

public fun VectorBoolChar.vecAndc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecAndc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecAndc(b: VectorSignedChar): VectorSignedChar = andc(this, b)

public fun VectorBoolShort.vecAndc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecAndc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecAndc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecAndc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecAndc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecAndc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecAndc(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecAndc(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecAndc(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecAndc(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(andc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun orc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmute(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 })
}

public interface VectorOrc<Other, out Result> {
    public fun vecOrc(b: Other): Result
}

public fun VectorBoolChar.vecOrc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecOrc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecOrc(b: VectorSignedChar): VectorSignedChar = orc(this, b)

public fun VectorBoolShort.vecOrc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecOrc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecOrc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecOrc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecOrc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecOrc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecOrc(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecOrc(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecOrc(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecOrc(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(orc(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun vecRintF32(a: VectorFloat): VectorFloat = run { val simdArg0 = a; simdArg0 }

public fun vecRintF64(a: VectorDouble): VectorDouble = run { val simdArg0 = a; simdArg0 }

public fun vecRoundcF32(a: VectorFloat): VectorFloat = nearbyintV4f32(a)

public fun vecRoundcF64(a: VectorDouble): VectorDouble = nearbyintV2f64(a)

public fun vecRoundF32(a: VectorFloat): VectorFloat = roundevenV4f32(a)

public fun vecRoundF64(a: VectorDouble): VectorDouble = roundevenV2f64(a)

public interface VectorRoundc<T> {
    public fun vecRoundc(): T
}

public interface VectorRound<T> {
    public fun vecRound(): T
}

public interface VectorRint<T> {
    public fun vecRint(): T
}

public fun VectorFloat.vecRoundc(): VectorFloat = vecRoundcF32(this)

public fun VectorDouble.vecRoundc(): VectorDouble = vecRoundcF64(this)

public fun VectorFloat.vecRound(): VectorFloat = vecRoundF32(this)

public fun VectorDouble.vecRound(): VectorDouble = vecRoundF64(this)

public fun VectorFloat.vecRint(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public fun VectorDouble.vecRint(): VectorDouble = run { val simdArg0 = this; simdArg0 }

public interface VectorTrunc<T> {
    public fun vecTrunc(): T
}

public interface VectorCeil<T> {
    public fun vecCeil(): T
}

public interface VectorFloor<T> {
    public fun vecFloor(): T
}

public fun VectorFloat.vecTrunc(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public fun VectorDouble.vecTrunc(): VectorDouble = run { val simdArg0 = this; simdArg0 }

public fun VectorFloat.vecCeil(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public fun VectorDouble.vecCeil(): VectorDouble = run { val simdArg0 = this; simdArg0 }

public fun VectorFloat.vecFloor(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public fun VectorDouble.vecFloor(): VectorDouble = run { val simdArg0 = this; simdArg0 }

public interface VectorSl<Other, out Result> {
    public fun vecSl(b: Other): Result
}

public fun veslvb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U8x16.splat(8u.toUByte()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun veslvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(16u.toUShort()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun veslvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(32u); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun veslvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U64x2.splat(64u.toULong()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedChar.vecSl(b: VectorUnsignedChar): VectorUnsignedChar = veslvb(this, b)

public fun VectorSignedChar.vecSl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(veslvb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSl(b: VectorUnsignedShort): VectorUnsignedShort = veslvh(this, b)

public fun VectorSignedShort.vecSl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(veslvh(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSl(b: VectorUnsignedInt): VectorUnsignedInt = veslvf(this, b)

public fun VectorSignedInt.vecSl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(veslvf(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSl(b: VectorUnsignedLongLong): VectorUnsignedLongLong = veslvg(this, b)

public fun VectorSignedLongLong.vecSl(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(veslvg(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSr<Other, out Result> {
    public fun vecSr(b: Other): Result
}

public fun vesrlvb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U8x16.splat(8u.toUByte()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesrlvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(16u.toUShort()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesrlvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(32u); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesrlvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U64x2.splat(64u.toULong()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedChar.vecSr(b: VectorUnsignedChar): VectorUnsignedChar = vesrlvb(this, b)

public fun VectorSignedChar.vecSr(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vesrlvb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSr(b: VectorUnsignedShort): VectorUnsignedShort = vesrlvh(this, b)

public fun VectorSignedShort.vecSr(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vesrlvh(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSr(b: VectorUnsignedInt): VectorUnsignedInt = vesrlvf(this, b)

public fun VectorSignedInt.vecSr(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vesrlvf(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSr(b: VectorUnsignedLongLong): VectorUnsignedLongLong = vesrlvg(this, b)

public fun VectorSignedLongLong.vecSr(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vesrlvg(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSra<Other, out Result> {
    public fun vecSra(b: Other): Result
}

public fun vesravb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I8x16.splat(8u.toByte()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesravh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I16x8.splat(16u.toShort()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesravf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I32x4.splat(32u.toInt()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun vesravg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I64x2.splat(64u.toLong()); simdArg0 }
    return transmute(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedChar.vecSra(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vesravb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSra(b: VectorUnsignedChar): VectorSignedChar = vesravb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSra(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vesravh(this, b), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSra(b: VectorUnsignedShort): VectorSignedShort = vesravh(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorUnsignedInt.vecSra(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vesravf(this, b), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSra(b: VectorUnsignedInt): VectorSignedInt = vesravf(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorUnsignedLongLong.vecSra(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vesravg(this, b), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSra(b: VectorUnsignedLongLong): VectorSignedLongLong = vesravg(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public interface VectorSlb<Other, out Result> {
    public fun vecSlb(b: Other): Result
}

public fun VectorUnsignedChar.vecSlb(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vslb(this, transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSlb(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSlb(b: VectorSignedChar): VectorSignedChar = vslb(this, b)

public fun VectorSignedChar.vecSlb(b: VectorUnsignedChar): VectorSignedChar = vslb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSlb(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vslb(this, transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSlb(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSlb(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSlb(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vslb(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSlb(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vslb(this, transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSlb(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSlb(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSlb(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vslb(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSlb(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vslb(this, transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSlb(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSlb(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vslb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSlb(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vslb(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSlb(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vslb(this, transmuteWithLayouts<VectorSignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSlb(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vslb(this, transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSlb(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vslb(this, transmuteWithLayouts<VectorSignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSlb(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vslb(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrab<Other, out Result> {
    public fun vecSrab(b: Other): Result
}

public fun VectorUnsignedChar.vecSrab(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrab(this, transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSrab(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrab(b: VectorSignedChar): VectorSignedChar = vsrab(this, b)

public fun VectorSignedChar.vecSrab(b: VectorUnsignedChar): VectorSignedChar = vsrab(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSrab(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrab(this, transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSrab(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrab(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSrab(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrab(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrab(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrab(this, transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSrab(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrab(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSrab(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrab(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrab(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrab(this, transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSrab(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrab(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrab(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSrab(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrab(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSrab(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrab(this, transmuteWithLayouts<VectorSignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSrab(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrab(this, transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSrab(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrab(this, transmuteWithLayouts<VectorSignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSrab(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrab(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrb<Other, out Result> {
    public fun vecSrb(b: Other): Result
}

public fun VectorUnsignedChar.vecSrb(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrlb(this, transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSrb(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrb(b: VectorSignedChar): VectorSignedChar = vsrlb(this, b)

public fun VectorSignedChar.vecSrb(b: VectorUnsignedChar): VectorSignedChar = vsrlb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSrb(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrlb(this, transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSrb(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrb(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSrb(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrlb(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrb(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrlb(this, transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSrb(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrb(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSrb(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrlb(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrb(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrlb(this, transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSrb(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrb(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrlb(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSrb(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrlb(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSrb(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrlb(this, transmuteWithLayouts<VectorSignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSrb(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrlb(this, transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSrb(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrlb(this, transmuteWithLayouts<VectorSignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSrb(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrlb(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrl<Other, out Result> {
    public fun vecSrl(b: Other): Result
}

public fun VectorUnsignedChar.vecSrl(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrl(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrl(b: VectorUnsignedChar): VectorSignedChar = vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSrl(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrl(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrl(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrl(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrl(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrl(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSral<Other, out Result> {
    public fun vecSral(b: Other): Result
}

public fun VectorUnsignedChar.vecSral(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsra(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSral(b: VectorUnsignedChar): VectorSignedChar = vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSral(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSral(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSral(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSral(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSral(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSral(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsra(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSll<Other, out Result> {
    public fun vecSll(b: Other): Result
}

public fun VectorUnsignedChar.vecSll(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsl(this, b), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSll(b: VectorUnsignedChar): VectorSignedChar = vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSll(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSll(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSll(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSll(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSll(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSll(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsl(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorRl<Other, T, out Result> {
    public fun vecRl(b: Other): Result
}

public fun verllvb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun VectorUnsignedChar.vecRl(b: VectorUnsignedChar): VectorUnsignedChar = verllvb(this, b)

public fun VectorSignedChar.vecRl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(verllvb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecRl(b: VectorUnsignedShort): VectorUnsignedShort = verllvh(this, b)

public fun VectorSignedShort.vecRl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(verllvh(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecRl(b: VectorUnsignedInt): VectorUnsignedInt = verllvf(this, b)

public fun VectorSignedInt.vecRl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(verllvf(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecRl(b: VectorUnsignedLongLong): VectorUnsignedLongLong = verllvg(this, b)

public fun VectorSignedLongLong.vecRl(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(verllvg(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun verllvbImm(a: VectorUnsignedChar, bits: CUlong): VectorUnsignedChar {
    val bits1 = (bits % 8u.toULong()).toUByte()
    val b = U8x16.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmute(b); simdArg0 }
}

public fun verllvhImm(a: VectorUnsignedShort, bits: CUlong): VectorUnsignedShort {
    val bits1 = (bits % 16u.toULong()).toUShort()
    val b = U16x8.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmute(b); simdArg0 }
}

public fun verllvfImm(a: VectorUnsignedInt, bits: CUlong): VectorUnsignedInt {
    val bits1 = (bits % 32u.toULong()).toUInt()
    val b = U32x4.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmute(b); simdArg0 }
}

public fun verllvgImm(a: VectorUnsignedLongLong, bits: CUlong): VectorUnsignedLongLong {
    val bits1 = (bits % 64u.toULong())
    val b = U64x2.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmute(b); simdArg0 }
}

public interface VectorRli<T> {
    public fun vecRli(bits: CUlong): T
}

public fun VectorSignedChar.vecRli(bits: CUlong): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(verllvbImm(this, bits), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecRli(bits: CUlong): VectorUnsignedChar = verllvbImm(this, bits)

public fun VectorSignedShort.vecRli(bits: CUlong): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(verllvhImm(this, bits), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecRli(bits: CUlong): VectorUnsignedShort = verllvhImm(this, bits)

public fun VectorSignedInt.vecRli(bits: CUlong): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(verllvfImm(this, bits), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedInt.vecRli(bits: CUlong): VectorUnsignedInt = verllvfImm(this, bits)

public fun VectorSignedLongLong.vecRli(bits: CUlong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(verllvgImm(this, bits), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorUnsignedLongLong.vecRli(bits: CUlong): VectorUnsignedLongLong = verllvgImm(this, bits)

public interface VectorRlMask<Other, T> {
    public fun vecRlMask(other: Other, imm8: UByte): T
}

public fun testVerimb(a: VectorSignedChar, b: VectorUnsignedChar, imm8: UByte): VectorSignedChar = verimb(a, a, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), (imm8 % 8u.toUByte()).toInt())

public fun VectorSignedChar.vecRlMask(other: VectorUnsignedChar, imm8: UByte): VectorSignedChar = testVerimb(this, other, imm8)

public fun VectorUnsignedChar.vecRlMask(other: VectorUnsignedChar, imm8: UByte): VectorUnsignedChar = transmute(testVerimb(this, other, imm8))

public fun testVerimh(a: VectorSignedShort, b: VectorUnsignedShort, imm8: UByte): VectorSignedShort = verimh(a, a, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), (imm8 % 16u.toUByte()).toInt())

public fun VectorSignedShort.vecRlMask(other: VectorUnsignedShort, imm8: UByte): VectorSignedShort = testVerimh(this, other, imm8)

public fun VectorUnsignedShort.vecRlMask(other: VectorUnsignedShort, imm8: UByte): VectorUnsignedShort = transmute(testVerimh(this, other, imm8))

public fun testVerimf(a: VectorSignedInt, b: VectorUnsignedInt, imm8: UByte): VectorSignedInt = verimf(a, a, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), (imm8 % 32u.toUByte()).toInt())

public fun VectorSignedInt.vecRlMask(other: VectorUnsignedInt, imm8: UByte): VectorSignedInt = testVerimf(this, other, imm8)

public fun VectorUnsignedInt.vecRlMask(other: VectorUnsignedInt, imm8: UByte): VectorUnsignedInt = transmute(testVerimf(this, other, imm8))

public fun testVerimg(a: VectorSignedLongLong, b: VectorUnsignedLongLong, imm8: UByte): VectorSignedLongLong = verimg(a, a, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), (imm8 % 64u.toUByte()).toInt())

public fun VectorSignedLongLong.vecRlMask(other: VectorUnsignedLongLong, imm8: UByte): VectorSignedLongLong = testVerimg(this, other, imm8)

public fun VectorUnsignedLongLong.vecRlMask(other: VectorUnsignedLongLong, imm8: UByte): VectorUnsignedLongLong = transmute(testVerimg(this, other, imm8))

public interface VectorReve<T> {
    public fun vecReve(): T
}

public open class ReverseMask(
    public var value: UIntArray = UIntArray(0),
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
            val index = UIntArray(n) { 0u }
            var i = 0
            while (i < n) {
                index[i] = (n - i - 1).toUInt()
                i += 1
            }
            return ReverseMask(index)
        }
    }
}

public fun reveb(a: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedChar() }

public fun VectorSignedChar.vecReve(): VectorSignedChar = reveb(this)

public fun VectorUnsignedChar.vecReve(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(reveb(this), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecReve(): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(reveb(this), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun reveh(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedShort() }

public fun VectorSignedShort.vecReve(): VectorSignedShort = reveh(this)

public fun VectorUnsignedShort.vecReve(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(reveh(this), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecReve(): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(reveh(this), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun revef(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedInt() }

public fun VectorSignedInt.vecReve(): VectorSignedInt = revef(this)

public fun VectorUnsignedInt.vecReve(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(revef(this), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecReve(): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(revef(this), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun reveg(a: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecReve(): VectorSignedLongLong = reveg(this)

public fun VectorUnsignedLongLong.vecReve(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(reveg(this), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorBoolLongLong.vecReve(): VectorBoolLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(reveg(this), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorFloat.vecReve(): VectorFloat = transmute(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()).vecReve())

public fun VectorDouble.vecReve(): VectorDouble = transmute(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()).vecReve())

public interface VectorRevb<T> {
    public fun vecRevb(): T
}

public fun bswapb(a: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; simdArg0 }

public fun bswaph(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; simdArg0 }

public fun bswapf(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; simdArg0 }

public fun bswapg(a: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; simdArg0 }

public fun VectorUnsignedChar.vecRevb(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(bswapb(this), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecRevb(): VectorSignedChar = bswapb(this)

public fun VectorUnsignedShort.vecRevb(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(bswaph(this), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecRevb(): VectorSignedShort = bswaph(this)

public fun VectorUnsignedInt.vecRevb(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(bswapf(this), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecRevb(): VectorSignedInt = bswapf(this)

public fun VectorUnsignedLongLong.vecRevb(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(bswapg(this), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecRevb(): VectorSignedLongLong = bswapg(this)

public fun VectorFloat.vecRevb(): VectorFloat = transmute(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()).vecRevb())

public fun VectorDouble.vecRevb(): VectorDouble = transmute(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()).vecRevb())

public interface VectorMergel<T, Result> {
    public fun vecMergel(other: T): T
}

public interface VectorMergeh<T, Result> {
    public fun vecMergeh(other: T): T
}

public fun vmrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedChar() }

public fun VectorSignedChar.vecMergel(other: VectorSignedChar): VectorSignedChar = vmrlb(this, other)

public fun VectorUnsignedChar.vecMergel(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vmrlb(this, other), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vmrhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedChar() }

public fun VectorSignedChar.vecMergeh(other: VectorSignedChar): VectorSignedChar = vmrhb(this, other)

public fun VectorUnsignedChar.vecMergeh(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vmrhb(this, other), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vmrlh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedShort() }

public fun VectorSignedShort.vecMergel(other: VectorSignedShort): VectorSignedShort = vmrlh(this, other)

public fun VectorUnsignedShort.vecMergel(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vmrlh(this, other), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vmrhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedShort() }

public fun VectorSignedShort.vecMergeh(other: VectorSignedShort): VectorSignedShort = vmrhh(this, other)

public fun VectorUnsignedShort.vecMergeh(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vmrhh(this, other), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vmrlf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedInt() }

public fun VectorSignedInt.vecMergel(other: VectorSignedInt): VectorSignedInt = vmrlf(this, other)

public fun VectorUnsignedInt.vecMergel(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vmrlf(this, other), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vmrhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedInt() }

public fun VectorSignedInt.vecMergeh(other: VectorSignedInt): VectorSignedInt = vmrhf(this, other)

public fun VectorUnsignedInt.vecMergeh(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vmrhf(this, other), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vmrlg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecMergel(other: VectorSignedLongLong): VectorSignedLongLong = vmrlg(this, other)

public fun VectorUnsignedLongLong.vecMergel(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vmrlg(this, other), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vmrhg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecMergeh(other: VectorSignedLongLong): VectorSignedLongLong = vmrhg(this, other)

public fun VectorUnsignedLongLong.vecMergeh(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vmrhg(this, other), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public interface VectorPerm<T> {
    public fun vecPerm(other: T, c: VectorUnsignedChar): T
}

public fun VectorSignedChar.vecPerm(other: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar = vperm(this, other, c)

public fun VectorSignedShort.vecPerm(other: VectorSignedShort, c: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedInt.vecPerm(other: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedLongLong.vecPerm(other: VectorSignedLongLong, c: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorUnsignedChar.vecPerm(other: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedShort.vecPerm(other: VectorUnsignedShort, c: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedInt.vecPerm(other: VectorUnsignedInt, c: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedLongLong.vecPerm(other: VectorUnsignedLongLong, c: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorBoolChar.vecPerm(other: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolShort.vecPerm(other: VectorBoolShort, c: VectorUnsignedChar): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolInt.vecPerm(other: VectorBoolInt, c: VectorUnsignedChar): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolLongLong.vecPerm(other: VectorBoolLongLong, c: VectorUnsignedChar): VectorBoolLongLong = transmuteWithLayouts<VectorSignedChar, VectorBoolLongLong>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorFloat.vecPerm(other: VectorFloat, c: VectorUnsignedChar): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecPerm(other: VectorDouble, c: VectorUnsignedChar): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vperm(this, other, c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSumU128<T> {
    public fun vecSumU128(other: T): VectorUnsignedChar
}

public fun vecVsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128 = vsumqf(a, b)

public fun vecVsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 = vsumqg(a, b)

public fun VectorUnsignedInt.vecSumU128(other: VectorUnsignedInt): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vecVsumqf(this, other), UnresolvedTypeLayout<U128>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedLongLong.vecSumU128(other: VectorUnsignedLongLong): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vecVsumqg(this, other), UnresolvedTypeLayout<U128>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public interface VectorSum2<T> {
    public fun vecSum2(other: T): VectorUnsignedLongLong
}

public fun vecVsumgh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedLongLong = vsumgh(a, b)

public fun vecVsumgf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong = vsumgf(a, b)

public fun VectorUnsignedShort.vecSum2(other: VectorUnsignedShort): VectorUnsignedLongLong = vecVsumgh(this, other)

public fun VectorUnsignedInt.vecSum2(other: VectorUnsignedInt): VectorUnsignedLongLong = vecVsumgf(this, other)

public interface VectorSum4<T> {
    public fun vecSum4(other: T): VectorUnsignedInt
}

public fun vecVsumb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedInt = vsumb(a, b)

public fun vecVsumh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vsumh(a, b)

public fun VectorUnsignedChar.vecSum4(other: VectorUnsignedChar): VectorUnsignedInt = vecVsumb(this, other)

public fun VectorUnsignedShort.vecSum4(other: VectorUnsignedShort): VectorUnsignedInt = vecVsumh(this, other)

public interface VectorSubc<Other, out Result> {
    public fun vecSubc(b: Other): Result
}

public fun vecVscbib(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vscbib(a, b)

public fun vecVscbih(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vscbih(a, b)

public fun vecVscbif(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vscbif(a, b)

public fun vecVscbig(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = vscbig(a, b)

public fun VectorUnsignedChar.vecSubc(b: VectorUnsignedChar): VectorUnsignedChar = vecVscbib(this, b)

public fun VectorUnsignedShort.vecSubc(b: VectorUnsignedShort): VectorUnsignedShort = vecVscbih(this, b)

public fun VectorUnsignedInt.vecSubc(b: VectorUnsignedInt): VectorUnsignedInt = vecVscbif(this, b)

public fun VectorUnsignedLongLong.vecSubc(b: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVscbig(this, b)

public interface VectorSqrt<T> {
    public fun vecSqrt(): T
}

public fun vecSqrtF32(v: VectorFloat): VectorFloat = run { val simdArg0 = v; simdArg0 }

public fun vecSqrtF64(v: VectorDouble): VectorDouble = run { val simdArg0 = v; simdArg0 }

public fun VectorFloat.vecSqrt(): VectorFloat = vecSqrtF32(this)

public fun VectorDouble.vecSqrt(): VectorDouble = vecSqrtF64(this)

public fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, imm: Int): VectorSignedChar = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaeb(a, b, imm)

public fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, imm: Int): VectorSignedShort = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaeh(a, b, imm)

public fun vfaef(a: VectorSignedInt, b: VectorSignedInt, imm: Int): VectorSignedInt = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaef(a, b, imm)

public fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, imm: Int): VectorSignedChar = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezb(a, b, imm)

public fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, imm: Int): VectorSignedShort = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezh(a, b, imm)

public fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, imm: Int): VectorSignedInt = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezf(a, b, imm)

public interface VectorFindAnyEq<Other, out Result> {
    public fun vecFindAnyEq(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEq(b: VectorSignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecFindAnyEq(b: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolChar.vecFindAnyEq(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecFindAnyEq(b: VectorSignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecFindAnyEq(b: VectorUnsignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecFindAnyEq(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecFindAnyEq(b: VectorSignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecFindAnyEq(b: VectorUnsignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecFindAnyEq(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorFindAnyNe<Other, out Result> {
    public fun vecFindAnyNe(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNe(b: VectorSignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecFindAnyNe(b: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolChar.vecFindAnyNe(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecFindAnyNe(b: VectorSignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecFindAnyNe(b: VectorUnsignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecFindAnyNe(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecFindAnyNe(b: VectorSignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecFindAnyNe(b: VectorUnsignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecFindAnyNe(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorFindAnyEqOrZeroIdx<Other, out Result> {
    public fun vecFindAnyEqOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqOr0Idx(b: VectorSignedChar): VectorSignedChar = vfaezb(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyEqOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyEqOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyEqOr0Idx(b: VectorSignedShort): VectorSignedShort = vfaezh(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyEqOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyEqOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyEqOr0Idx(b: VectorSignedInt): VectorSignedInt = vfaezf(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyEqOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyEqOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyNeOrZeroIdx<Other, out Result> {
    public fun vecFindAnyNeOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeOr0Idx(b: VectorSignedChar): VectorSignedChar = vfaezb(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyNeOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyNeOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyNeOr0Idx(b: VectorSignedShort): VectorSignedShort = vfaezh(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyNeOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyNeOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyNeOr0Idx(b: VectorSignedInt): VectorSignedInt = vfaezf(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyNeOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyNeOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyEqIdx<Other, out Result> {
    public fun vecFindAnyEqIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqIdx(b: VectorSignedChar): VectorSignedChar = vfaeb(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyEqIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyEqIdx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyEqIdx(b: VectorSignedShort): VectorSignedShort = vfaeh(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyEqIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyEqIdx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyEqIdx(b: VectorSignedInt): VectorSignedInt = vfaef(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyEqIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(this, b, FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyEqIdx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyNeIdx<Other, out Result> {
    public fun vecFindAnyNeIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeIdx(b: VectorSignedChar): VectorSignedChar = vfaeb(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyNeIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyNeIdx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyNeIdx(b: VectorSignedShort): VectorSignedShort = vfaeh(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyNeIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyNeIdx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyNeIdx(b: VectorSignedInt): VectorSignedInt = vfaef(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyNeIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(this, b, FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyNeIdx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, imm: Int): PackedTuple<VectorSignedChar, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaebs(a, b, imm)

public fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, imm: Int): PackedTuple<VectorSignedShort, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaehs(a, b, imm)

public fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, imm: Int): PackedTuple<VectorSignedInt, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaefs(a, b, imm)

public fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, imm: Int): PackedTuple<VectorSignedChar, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezbs(a, b, imm)

public fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, imm: Int): PackedTuple<VectorSignedShort, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezhs(a, b, imm)

public fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, imm: Int): PackedTuple<VectorSignedInt, Int> = io.github.kotlinmania.rustlang.corearch.s390x.vector.vfaezfs(a, b, imm)

public interface VectorFindAnyEqCC<Other, out Result> {
    public fun vecFindAnyEqCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqCc(b: VectorSignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqCc(b: VectorUnsignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqCc(b: VectorBoolChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqCc(b: VectorSignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqCc(b: VectorUnsignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqCc(b: VectorBoolShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqCc(b: VectorSignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqCc(b: VectorUnsignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqCc(b: VectorBoolInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeCC<Other, out Result> {
    public fun vecFindAnyNeCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeCc(b: VectorSignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeCc(b: VectorUnsignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeCc(b: VectorBoolChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorBoolChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeCc(b: VectorSignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeCc(b: VectorUnsignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeCc(b: VectorBoolShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorBoolShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeCc(b: VectorSignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeCc(b: VectorUnsignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeCc(b: VectorBoolInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorBoolInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyEqIdxCC<Other, out Result> {
    public fun vecFindAnyEqIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeIdxCC<Other, out Result> {
    public fun vecFindAnyNeIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyEqOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyEqOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyEqOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyEqOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyEqOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyEqOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyEqOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyEqOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyEqOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyEqOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorFindAnyNeOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyNeOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecFindAnyNeOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecFindAnyNeOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecFindAnyNeOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecFindAnyNeOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecFindAnyNeOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecFindAnyNeOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecFindAnyNeOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecFindAnyNeOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), FindImm.NeIdx.value.toInt())
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

public fun vectorLoadVecLoadLenVectorSignedChar(ptr: ConstPtr, byteCount: UInt): VectorSignedChar = vll(byteCount, ptr)

public fun vectorLoadVecLoadBndryVectorSignedChar(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedChar> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedChar>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedChar>>())

public fun VectorSignedChar.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorSignedChar, Unit>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedChar(ptr: ConstPtr, byteCount: UInt): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vectorLoadVecLoadBndryVectorUnsignedChar(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedChar> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedChar>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedChar>>())

public fun VectorUnsignedChar.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorUnsignedChar, Unit>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedShort(ptr: ConstPtr, byteCount: UInt): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorLoadVecLoadBndryVectorSignedShort(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedShort> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedShort>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedShort>>())

public fun VectorSignedShort.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorSignedShort, Unit>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedShort(ptr: ConstPtr, byteCount: UInt): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vectorLoadVecLoadBndryVectorUnsignedShort(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedShort> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedShort>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedShort>>())

public fun VectorUnsignedShort.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorUnsignedShort, Unit>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedInt(ptr: ConstPtr, byteCount: UInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorLoadVecLoadBndryVectorSignedInt(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedInt> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedInt>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedInt>>())

public fun VectorSignedInt.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorSignedInt, Unit>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedInt(ptr: ConstPtr, byteCount: UInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vectorLoadVecLoadBndryVectorUnsignedInt(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedInt> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedInt>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedInt>>())

public fun VectorUnsignedInt.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorUnsignedInt, Unit>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedLongLong(ptr: ConstPtr, byteCount: UInt): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorLoadVecLoadBndryVectorSignedLongLong(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedLongLong> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedLongLong>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedLongLong>>())

public fun VectorSignedLongLong.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorSignedLongLong, Unit>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedLongLong(ptr: ConstPtr, byteCount: UInt): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vectorLoadVecLoadBndryVectorUnsignedLongLong(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedLongLong> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedLongLong>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedLongLong>>())

public fun VectorUnsignedLongLong.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorUnsignedLongLong, Unit>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorFloat(ptr: ConstPtr, byteCount: UInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorLoadVecLoadBndryVectorFloat(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorFloat> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorFloat>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorFloat>>())

public fun VectorFloat.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorFloat, Unit>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorDouble(ptr: ConstPtr, byteCount: UInt): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorLoadVecLoadBndryVectorDouble(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorDouble> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorDouble>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorDouble>>())

public fun VectorDouble.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmuteWithLayouts<VectorDouble, Unit>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<Unit>()), byteCount, ptr)
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

public fun <T> pack(a: T, b: T, n: Int): T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.pack(n); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun vpkh(a: I16x8, b: I16x8): I8x16 {
    val a1: I8x16 = transmuteWithLayouts<I16x8, I8x16>(a, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<I8x16>())
    val b1: I8x16 = transmuteWithLayouts<I16x8, I8x16>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<I8x16>())
    return run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = ShuffleMask.pack(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }
}

public fun vpkf(a: I32x4, b: I32x4): I16x8 {
    val a1: I16x8 = transmuteWithLayouts<I32x4, I16x8>(a, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<I16x8>())
    val b1: I16x8 = transmuteWithLayouts<I32x4, I16x8>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<I16x8>())
    return run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = ShuffleMask.pack(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
}

public fun vpkg(a: I64x2, b: I64x2): I32x4 {
    val a1: I32x4 = transmuteWithLayouts<I64x2, I32x4>(a, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<I32x4>())
    val b1: I32x4 = transmuteWithLayouts<I64x2, I32x4>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<I32x4>())
    return run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = ShuffleMask.pack(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
}

public interface VectorPack<Other, out Result> {
    public fun vecPack(b: Other): Result
}

public fun VectorSignedShort.vecPack(b: VectorSignedShort): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vpkh(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecPack(b: VectorUnsignedShort): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vpkh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolShort.vecPack(b: VectorBoolShort): VectorBoolChar = transmuteWithLayouts<I8x16, VectorBoolChar>(vpkh(transmuteWithLayouts<VectorBoolShort, VectorBoolChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorBoolChar>()), transmuteWithLayouts<VectorBoolShort, VectorBoolChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorBoolChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedInt.vecPack(b: VectorSignedInt): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vpkf(transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecPack(b: VectorUnsignedInt): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vpkf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolInt.vecPack(b: VectorBoolInt): VectorBoolShort = transmuteWithLayouts<I16x8, VectorBoolShort>(vpkf(transmuteWithLayouts<VectorBoolInt, VectorBoolShort>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorBoolShort>()), transmuteWithLayouts<VectorBoolInt, VectorBoolShort>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorBoolShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedLongLong.vecPack(b: VectorSignedLongLong): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vpkg(transmuteWithLayouts<VectorSignedLongLong, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecPack(b: VectorUnsignedLongLong): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vpkg(transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolLongLong.vecPack(b: VectorBoolLongLong): VectorBoolInt = transmuteWithLayouts<I32x4, VectorBoolInt>(vpkg(transmuteWithLayouts<VectorBoolLongLong, VectorBoolInt>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorBoolInt>()), transmuteWithLayouts<VectorBoolLongLong, VectorBoolInt>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorBoolInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorPacks<Other, out Result> {
    public fun vecPacks(b: Other): Result
}

public fun VectorSignedShort.vecPacks(b: VectorSignedShort): VectorSignedChar = vpksh(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecPacks(b: VectorUnsignedShort): VectorUnsignedChar = vpklsh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorSignedInt.vecPacks(b: VectorSignedInt): VectorSignedShort = vpksf(transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorUnsignedInt.vecPacks(b: VectorUnsignedInt): VectorUnsignedShort = vpklsf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorSignedLongLong.vecPacks(b: VectorSignedLongLong): VectorSignedInt = vpksg(transmuteWithLayouts<VectorSignedLongLong, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorUnsignedLongLong.vecPacks(b: VectorUnsignedLongLong): VectorUnsignedInt = vpklsg(transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public interface VectorPacksu<Other, out Result> {
    public fun vecPacksu(b: Other): Result
}

public fun vpacksuh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar = vpklsh(vecMax(a, vectorSignedShort(List(8) { 0 })), vecMax(b, vectorSignedShort(List(8) { 0 })))

public fun vpacksuf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort = vpklsf(vecMax(a, vectorSignedInt(List(4) { 0 })), vecMax(b, vectorSignedInt(List(4) { 0 })))

public fun vpacksug(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt = vpklsg(vecMax(a, vectorSignedLongLong(List(2) { 0 })), vecMax(b, vectorSignedLongLong(List(2) { 0 })))

public fun VectorSignedShort.vecPacksu(b: VectorSignedShort): VectorUnsignedChar = vpacksuh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecPacksu(b: VectorUnsignedShort): VectorUnsignedChar = vpklsh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorSignedInt.vecPacksu(b: VectorSignedInt): VectorUnsignedShort = vpacksuf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecPacksu(b: VectorUnsignedInt): VectorUnsignedShort = vpklsf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorSignedLongLong.vecPacksu(b: VectorSignedLongLong): VectorUnsignedInt = vpacksug(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedLongLong.vecPacksu(b: VectorUnsignedLongLong): VectorUnsignedInt = vpklsg(transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

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

public fun vfmasb(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }

public fun vfmadb(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }

public fun <T> simdFms(a: T, b: T, c: T): T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = run { val simdArg0 = c; simdArg0 }; simdArg0 }

public fun vfmssb(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = simdFms(a, b, c)

public fun vfmsdb(a: VectorDouble, b: VectorDouble, c: VectorDouble): VectorDouble = simdFms(a, b, c)

public fun VectorFloat.vecMadd(b: VectorFloat, c: VectorFloat): VectorFloat = vfmasb(this, b, c)

public fun VectorFloat.vecMsub(b: VectorFloat, c: VectorFloat): VectorFloat = vfmssb(this, b, c)

public fun VectorDouble.vecMadd(b: VectorDouble, c: VectorDouble): VectorDouble = vfmadb(this, b, c)

public fun VectorDouble.vecMsub(b: VectorDouble, c: VectorDouble): VectorDouble = vfmsdb(this, b, c)

public interface VectorUnpackh<out Result> {
    public fun vecUnpackh(): Result
}

public fun vuphb(a: VectorSignedChar): VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }

public fun vuphh(a: VectorSignedShort): VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }

public fun vuphf(a: VectorSignedInt): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }

public fun vuplhb(a: VectorUnsignedChar): VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }

public fun vuplhh(a: VectorUnsignedShort): VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }

public fun vuplhf(a: VectorUnsignedInt): VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackHigh(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }

public fun VectorSignedChar.vecUnpackh(): VectorSignedShort = vuphb(transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorSignedShort.vecUnpackh(): VectorSignedInt = vuphh(transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorSignedInt.vecUnpackh(): VectorSignedLongLong = vuphf(transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public fun VectorUnsignedChar.vecUnpackh(): VectorUnsignedShort = vuplhb(transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedShort.vecUnpackh(): VectorUnsignedInt = vuplhh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedInt.vecUnpackh(): VectorUnsignedLongLong = vuplhf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorBoolChar.vecUnpackh(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vuplhb(transmuteWithLayouts<VectorBoolChar, VectorBoolShort>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorBoolShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecUnpackh(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vuplhh(transmuteWithLayouts<VectorBoolShort, VectorBoolInt>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorBoolInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecUnpackh(): VectorBoolLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(vuplhf(transmuteWithLayouts<VectorBoolInt, VectorBoolLongLong>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorBoolLongLong>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public interface VectorUnpackl<out Result> {
    public fun vecUnpackl(): Result
}

public fun vuplb(a: VectorSignedChar): VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }

public fun vuplhw(a: VectorSignedShort): VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }

public fun vuplf(a: VectorSignedInt): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }

public fun vupllb(a: VectorUnsignedChar): VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }

public fun vupllh(a: VectorUnsignedShort): VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }

public fun vupllf(a: VectorUnsignedInt): VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }

public fun VectorSignedChar.vecUnpackl(): VectorSignedShort = vuplb(transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorSignedShort.vecUnpackl(): VectorSignedInt = vuplhw(transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorSignedInt.vecUnpackl(): VectorSignedLongLong = vuplf(transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public fun VectorUnsignedChar.vecUnpackl(): VectorUnsignedShort = vupllb(transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedShort.vecUnpackl(): VectorUnsignedInt = vupllh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedInt.vecUnpackl(): VectorUnsignedLongLong = vupllf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorBoolChar.vecUnpackl(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vupllb(transmuteWithLayouts<VectorBoolChar, VectorBoolShort>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorBoolShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecUnpackl(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vupllh(transmuteWithLayouts<VectorBoolShort, VectorBoolInt>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorBoolInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecUnpackl(): VectorBoolLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(vupllf(transmuteWithLayouts<VectorBoolInt, VectorBoolLongLong>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorBoolLongLong>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun vecVavgb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vavgb(a, b)

public fun vecVavgh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vavgh(a, b)

public fun vecVavgf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vavgf(a, b)

public fun vecVavgg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = vavgg(a, b)

public fun vecVavglb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vavglb(a, b)

public fun vecVavglh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vavglh(a, b)

public fun vecVavglf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vavglf(a, b)

public fun vecVavglg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = vavglg(a, b)

public interface VectorAvg<Other, out Result> {
    public fun vecAvg(b: Other): Result
}

public fun VectorUnsignedChar.vecAvg(b: VectorUnsignedChar): VectorUnsignedChar = vecVavglb(this, b)

public fun VectorSignedChar.vecAvg(b: VectorSignedChar): VectorSignedChar = vecVavgb(this, b)

public fun VectorUnsignedShort.vecAvg(b: VectorUnsignedShort): VectorUnsignedShort = vecVavglh(this, b)

public fun VectorSignedShort.vecAvg(b: VectorSignedShort): VectorSignedShort = vecVavgh(this, b)

public fun VectorUnsignedInt.vecAvg(b: VectorUnsignedInt): VectorUnsignedInt = vecVavglf(this, b)

public fun VectorSignedInt.vecAvg(b: VectorSignedInt): VectorSignedInt = vecVavgf(this, b)

public fun VectorUnsignedLongLong.vecAvg(b: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVavglg(this, b)

public fun VectorSignedLongLong.vecAvg(b: VectorSignedLongLong): VectorSignedLongLong = vecVavgg(this, b)

public interface VectorMule<Result, T> {
    public fun vecMule(b: T): Result
}

public fun vmeb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort {
    val elemsA: VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    val elemsB: VectorSignedShort = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmeh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt {
    val elemsA: VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    val elemsB: VectorSignedInt = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmef(a: VectorSignedInt, b: VectorSignedInt): VectorSignedLongLong {
    val elemsA: VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    val elemsB: VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmleb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort {
    val elemsA: VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    val elemsB: VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmleh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt {
    val elemsA: VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    val elemsB: VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmlef(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong {
    val elemsA: VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    val elemsB: VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.even(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun VectorSignedChar.vecMule(b: VectorSignedChar): VectorSignedShort = vmeb(transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorSignedShort.vecMule(b: VectorSignedShort): VectorSignedInt = vmeh(transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorSignedInt.vecMule(b: VectorSignedInt): VectorSignedLongLong = vmef(transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()), transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public fun VectorUnsignedChar.vecMule(b: VectorUnsignedChar): VectorUnsignedShort = vmleb(transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedShort.vecMule(b: VectorUnsignedShort): VectorUnsignedInt = vmleh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedInt.vecMule(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlef(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public interface VectorMulo<Result, T> {
    public fun vecMulo(b: T): Result
}

public fun vmob(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort {
    val elemsA: VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    val elemsB: VectorSignedShort = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmoh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt {
    val elemsA: VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    val elemsB: VectorSignedInt = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmof(a: VectorSignedInt, b: VectorSignedInt): VectorSignedLongLong {
    val elemsA: VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    val elemsB: VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmlob(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort {
    val elemsA: VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    val elemsB: VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmloh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt {
    val elemsA: VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    val elemsB: VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun vmlof(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong {
    val elemsA: VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    val elemsB: VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = b; val simdArg1 = b; val simdArg2 = ShuffleMask.odd(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }
    return run { val simdArg0 = elemsA; val simdArg1 = elemsB; simdArg0 }
}

public fun VectorSignedChar.vecMulo(b: VectorSignedChar): VectorSignedShort = vmob(transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorSignedShort.vecMulo(b: VectorSignedShort): VectorSignedInt = vmoh(transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorSignedInt.vecMulo(b: VectorSignedInt): VectorSignedLongLong = vmof(transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()), transmuteWithLayouts<VectorSignedInt, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public fun VectorUnsignedChar.vecMulo(b: VectorUnsignedChar): VectorUnsignedShort = vmlob(transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedShort.vecMulo(b: VectorUnsignedShort): VectorUnsignedInt = vmloh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedInt.vecMulo(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlof(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public interface VectorMulh<Result, T> {
    public fun vecMulh(b: T): Result
}

public fun vecVmhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vmhb(a, b)

public fun vecVmhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vmhh(a, b)

public fun vecVmhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vmhf(a, b)

public fun vecVmlhb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vmlhb(a, b)

public fun vecVmlhh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vmlhh(a, b)

public fun vecVmlhf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vmlhf(a, b)

public fun VectorSignedChar.vecMulh(b: VectorSignedChar): VectorSignedChar = vecVmhb(this, b)

public fun VectorSignedShort.vecMulh(b: VectorSignedShort): VectorSignedShort = vecVmhh(this, b)

public fun VectorSignedInt.vecMulh(b: VectorSignedInt): VectorSignedInt = vecVmhf(this, b)

public fun VectorUnsignedChar.vecMulh(b: VectorUnsignedChar): VectorUnsignedChar = vecVmlhb(this, b)

public fun VectorUnsignedShort.vecMulh(b: VectorUnsignedShort): VectorUnsignedShort = vecVmlhh(this, b)

public fun VectorUnsignedInt.vecMulh(b: VectorUnsignedInt): VectorUnsignedInt = vecVmlhf(this, b)

public interface VectorMeadd<T, Result> {
    public fun vecMeadd(b: T, c: Result): Result
}

public fun vecVmaeb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vmaeb(a, b, c)

public fun vecVmaeh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmaeh(a, b, c)

public fun vecVmaef(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vmaef(a, b, c)

public fun vecVmaleb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vmaleb(a, b, c)

public fun vecVmaleh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmaleh(a, b, c)

public fun vecVmalef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vmalef(a, b, c)

public fun VectorSignedChar.vecMeadd(b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vecVmaeb(this, b, c)

public fun VectorSignedShort.vecMeadd(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vecVmaeh(this, b, c)

public fun VectorSignedInt.vecMeadd(b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vecVmaef(this, b, c)

public fun VectorUnsignedChar.vecMeadd(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVmaleb(this, b, c)

public fun VectorUnsignedShort.vecMeadd(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVmaleh(this, b, c)

public fun VectorUnsignedInt.vecMeadd(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmalef(this, b, c)

public interface VectorMoadd<T, Result> {
    public fun vecMoadd(b: T, c: Result): Result
}

public fun vecVmaob(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vmaob(a, b, c)

public fun vecVmaoh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmaoh(a, b, c)

public fun vecVmaof(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vmaof(a, b, c)

public fun vecVmalob(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vmalob(a, b, c)

public fun vecVmaloh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmaloh(a, b, c)

public fun vecVmalof(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vmalof(a, b, c)

public fun VectorSignedChar.vecMoadd(b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort = vecVmaob(this, b, c)

public fun VectorSignedShort.vecMoadd(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vecVmaoh(this, b, c)

public fun VectorSignedInt.vecMoadd(b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong = vecVmaof(this, b, c)

public fun VectorUnsignedChar.vecMoadd(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalob(this, b, c)

public fun VectorUnsignedShort.vecMoadd(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVmaloh(this, b, c)

public fun VectorUnsignedInt.vecMoadd(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVmalof(this, b, c)

public interface VectorMhadd<T, Result> {
    public fun vecMhadd(b: T, c: Result): Result
}

public fun vecVmahb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = vmahb(a, b, c)

public fun vecVmahh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vmahh(a, b, c)

public fun vecVmahf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = vmahf(a, b, c)

public fun vecVmalhb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = vmalhb(a, b, c)

public fun vecVmalhh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = vmalhh(a, b, c)

public fun vecVmalhf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = vmalhf(a, b, c)

public fun VectorSignedChar.vecMhadd(b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = vecVmahb(this, b, c)

public fun VectorSignedShort.vecMhadd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vecVmahh(this, b, c)

public fun VectorSignedInt.vecMhadd(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = vecVmahf(this, b, c)

public fun VectorUnsignedChar.vecMhadd(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = vecVmalhb(this, b, c)

public fun VectorUnsignedShort.vecMhadd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalhh(this, b, c)

public fun VectorUnsignedInt.vecMhadd(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = vecVmalhf(this, b, c)

public interface VectorMladd<T, Result> {
    public fun vecMladd(b: T, c: Result): Result
}

public fun <T> simdMladd(a: T, b: T, c: T): T = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }; val simdArg1 = c; simdArg0 }

public fun vecVmalIb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = simdMladd(a, b, c)

public fun vecVmalIh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = simdMladd(a, b, c)

public fun vecVmalIf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = simdMladd(a, b, c)

public fun vecVmalUb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = simdMladd(a, b, c)

public fun vecVmalUh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = simdMladd(a, b, c)

public fun vecVmalUf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = simdMladd(a, b, c)

public fun VectorSignedChar.vecMladd(b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar = vecVmalIb(this, b, c)

public fun VectorSignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vecVmalIh(this, b, c)

public fun VectorSignedInt.vecMladd(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt = vecVmalIf(this, b, c)

public fun VectorUnsignedChar.vecMladd(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = vecVmalUb(this, b, c)

public fun VectorUnsignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort = vecVmalUh(this, b, c)

public fun VectorUnsignedInt.vecMladd(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt = vecVmalUf(this, b, c)

public interface VectorGfmsum<Result, T> {
    public fun vecGfmsum(b: T): Result
}

public fun vecVgfmb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort = vgfmb(a, b)

public fun vecVgfmh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vgfmh(a, b)

public fun vecVgfmf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong = vgfmf(a, b)

public fun VectorUnsignedChar.vecGfmsum(b: VectorUnsignedChar): VectorUnsignedShort = vecVgfmb(transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorUnsignedChar, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedShort.vecGfmsum(b: VectorUnsignedShort): VectorUnsignedInt = vecVgfmh(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedInt.vecGfmsum(b: VectorUnsignedInt): VectorUnsignedLongLong = vecVgfmf(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public interface VectorGfmsumAccum<T, Result> {
    public fun vecGfmsumAccum(b: T, c: Result): Result
}

public fun vecVgfmab(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vgfmab(a, b, c)

public fun vecVgfmah(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vgfmah(a, b, c)

public fun vecVgfmaf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vgfmaf(a, b, c)

public fun VectorUnsignedChar.vecGfmsumAccum(b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort = vecVgfmab(this, b, c)

public fun VectorUnsignedShort.vecGfmsumAccum(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vecVgfmah(this, b, c)

public fun VectorUnsignedInt.vecGfmsumAccum(b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong = vecVgfmaf(this, b, c)

public fun vgef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorUnsignedInt {
    run {
        check(0u <= d && d < (1u shl 2)) { "D doesn't fit in 2 bits (unsigned)" }
    }
    val offset: UInt = run { val simdArg0 = b; val simdArg1 = d; 0u }
    val ptr = ConstPtr((c).rawAddress + ((offset.toInt()).toLong()))
    val value = (PrimitiveLayouts.U32).readNative((ptr).rawAddress)
    return run { val simdArg0 = a; val simdArg1 = d; val simdArg2 = value; simdArg0 }
}

public fun vgeg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= d && d < (1u shl 1)) { "D doesn't fit in 1 bits (unsigned)" }
    }
    val offset: ULong = run { val simdArg0 = b; val simdArg1 = d; 0uL }
    val ptr = ConstPtr((c).rawAddress + ((offset.toInt()).toLong()))
    val value = (PrimitiveLayouts.U64).readNative((ptr).rawAddress)
    return run { val simdArg0 = a; val simdArg1 = d; val simdArg2 = value; simdArg0 }
}

public interface VectorGatherElement<T, Element, Offset> {
    public fun vecGatherElement(b: Offset, c: ConstPtr, d: UInt): T
}

public fun VectorSignedInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorSignedInt = transmute(vgef(this, b, c, d))

public fun VectorBoolInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorBoolInt = transmute(vgef(this, b, c, d))

public fun VectorUnsignedInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorUnsignedInt = transmute(vgef(this, b, c, d))

public fun VectorSignedLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorSignedLongLong = transmute(vgeg(this, b, c, d))

public fun VectorBoolLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorBoolLongLong = transmute(vgeg(this, b, c, d))

public fun VectorUnsignedLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorUnsignedLongLong = transmute(vgeg(this, b, c, d))

public fun VectorFloat.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorFloat = transmute(vgef(this, b, c, d))

public fun VectorDouble.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorDouble = transmute(vgeg(this, b, c, d))

public fun vscef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    run {
        check(0u <= d && d < (1u shl 2)) { "D doesn't fit in 2 bits (unsigned)" }
    }
    val value = run { val simdArg0 = a; val simdArg1 = d; simdArg0 }
    val offset: UInt = run { val simdArg0 = b; val simdArg1 = d; 0u }
    val ptr = MutPtr((c).rawAddress + ((offset.toInt()).toLong()))
    (PrimitiveLayouts.U32).writeNative((ptr).rawAddress, value)
}

public fun vsceg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    run {
        check(0u <= d && d < (1u shl 1)) { "D doesn't fit in 1 bits (unsigned)" }
    }
    val value = run { val simdArg0 = a; val simdArg1 = d; simdArg0 }
    val offset: ULong = run { val simdArg0 = b; val simdArg1 = d; 0uL }
    val ptr = MutPtr((c).rawAddress + ((offset.toInt()).toLong()))
    (PrimitiveLayouts.U64).writeNative((ptr).rawAddress, value)
}

public interface VectorScatterElement<Element, Offset> {
    public fun vecScatterElement(b: Offset, c: MutPtr, d: UInt)
}

public fun VectorSignedInt.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorSignedInt, Unit>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorBoolInt.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorBoolInt, Unit>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorUnsignedInt.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorUnsignedInt, Unit>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorSignedLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorSignedLongLong, Unit>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorBoolLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorBoolLongLong, Unit>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorUnsignedLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorUnsignedLongLong, Unit>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorFloat.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorFloat, Unit>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public fun VectorDouble.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorDouble, Unit>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<Unit>()), b, c, d)
}

public interface VectorSel<Mask, T> {
    public fun vecSel(b: T, c: Mask): T
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorSignedChar.vecSelVectorSel(b: VectorSignedChar, c: VectorBoolChar): VectorSignedChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: VectorUnsignedShort): VectorSignedShort {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorSignedShort.vecSelVectorSel(b: VectorSignedShort, c: VectorBoolShort): VectorSignedShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: VectorUnsignedInt): VectorSignedInt {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorSignedInt.vecSelVectorSel(b: VectorSignedInt, c: VectorBoolInt): VectorSignedInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorSignedLongLong.vecSel(b: VectorSignedLongLong, c: VectorUnsignedLongLong): VectorSignedLongLong {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorSignedLongLong.vecSelVectorSel(b: VectorSignedLongLong, c: VectorBoolLongLong): VectorSignedLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorUnsignedChar.vecSel(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedChar.vecSelVectorSel(b: VectorUnsignedChar, c: VectorBoolChar): VectorUnsignedChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecSel(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedShort.vecSelVectorSel(b: VectorUnsignedShort, c: VectorBoolShort): VectorUnsignedShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecSel(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedInt.vecSelVectorSel(b: VectorUnsignedInt, c: VectorBoolInt): VectorUnsignedInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedLongLong.vecSel(b: VectorUnsignedLongLong, c: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorUnsignedLongLong.vecSelVectorSel(b: VectorUnsignedLongLong, c: VectorBoolLongLong): VectorUnsignedLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorBoolChar.vecSel(b: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorBoolChar.vecSelVectorSel(b: VectorBoolChar, c: VectorBoolChar): VectorBoolChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorBoolShort.vecSel(b: VectorBoolShort, c: VectorUnsignedShort): VectorBoolShort {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorBoolShort.vecSelVectorSel(b: VectorBoolShort, c: VectorBoolShort): VectorBoolShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorBoolInt.vecSel(b: VectorBoolInt, c: VectorUnsignedInt): VectorBoolInt {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorBoolInt.vecSelVectorSel(b: VectorBoolInt, c: VectorBoolInt): VectorBoolInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorBoolLongLong.vecSel(b: VectorBoolLongLong, c: VectorUnsignedLongLong): VectorBoolLongLong {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorBoolLongLong.vecSelVectorSel(b: VectorBoolLongLong, c: VectorBoolLongLong): VectorBoolLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorFloat.vecSel(b: VectorFloat, c: VectorUnsignedInt): VectorFloat {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorFloat.vecSelVectorSel(b: VectorFloat, c: VectorBoolInt): VectorFloat = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorDouble.vecSel(b: VectorDouble, c: VectorUnsignedLongLong): VectorDouble {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmute(vectorSignedChar(List(16) { 0.inv() })); simdArg0 }; simdArg0 }
    return transmute(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 })
}

public fun VectorDouble.vecSelVectorSel(b: VectorDouble, c: VectorBoolLongLong): VectorDouble = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

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

public fun VectorSignedChar.vecAllLt(other: VectorSignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorSignedChar.vecAllLe(other: VectorSignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorSignedChar.vecAllGt(other: VectorSignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorSignedChar.vecAllGe(other: VectorSignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorUnsignedChar.vecAllLt(other: VectorUnsignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorUnsignedChar.vecAllLe(other: VectorUnsignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorUnsignedChar.vecAllGt(other: VectorUnsignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorUnsignedChar.vecAllGe(other: VectorUnsignedChar): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolChar() }; false }).toInt()

public fun VectorSignedShort.vecAllLt(other: VectorSignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorSignedShort.vecAllLe(other: VectorSignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorSignedShort.vecAllGt(other: VectorSignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorSignedShort.vecAllGe(other: VectorSignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorUnsignedShort.vecAllLt(other: VectorUnsignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorUnsignedShort.vecAllLe(other: VectorUnsignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorUnsignedShort.vecAllGt(other: VectorUnsignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorUnsignedShort.vecAllGe(other: VectorUnsignedShort): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolShort() }; false }).toInt()

public fun VectorSignedInt.vecAllLt(other: VectorSignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorSignedInt.vecAllLe(other: VectorSignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorSignedInt.vecAllGt(other: VectorSignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorSignedInt.vecAllGe(other: VectorSignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorUnsignedInt.vecAllLt(other: VectorUnsignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorUnsignedInt.vecAllLe(other: VectorUnsignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorUnsignedInt.vecAllGt(other: VectorUnsignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorUnsignedInt.vecAllGe(other: VectorUnsignedInt): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorFloat.vecAllLt(other: VectorFloat): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorFloat.vecAllLe(other: VectorFloat): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorFloat.vecAllGt(other: VectorFloat): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorFloat.vecAllGe(other: VectorFloat): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolInt() }; false }).toInt()

public fun VectorSignedLongLong.vecAllLt(other: VectorSignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorSignedLongLong.vecAllLe(other: VectorSignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorSignedLongLong.vecAllGt(other: VectorSignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorSignedLongLong.vecAllGe(other: VectorSignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllLt(other: VectorUnsignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllLe(other: VectorUnsignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllGt(other: VectorUnsignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorUnsignedLongLong.vecAllGe(other: VectorUnsignedLongLong): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorDouble.vecAllLt(other: VectorDouble): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorDouble.vecAllLe(other: VectorDouble): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorDouble.vecAllGt(other: VectorDouble): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public fun VectorDouble.vecAllGe(other: VectorDouble): Int = (run { val simdArg0 = run { val simdArg0 = this; val simdArg1 = other; VectorBoolLongLong() }; false }).toInt()

public interface VectorTestMask<Mask> {
    public fun vecTestMask(other: Mask): Int
}

public fun VectorSignedShort.vecTestMask(other: VectorUnsignedShort): Int = vtm(transmuteWithLayouts<VectorSignedShort, Int>(this, UnresolvedTypeLayout<VectorSignedShort>(), PrimitiveLayouts.I32), transmuteWithLayouts<VectorUnsignedShort, Int>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), PrimitiveLayouts.I32))

public fun VectorSignedLongLong.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmuteWithLayouts<VectorSignedLongLong, Int>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), PrimitiveLayouts.I32), transmuteWithLayouts<VectorUnsignedLongLong, Int>(other, UnresolvedTypeLayout<VectorUnsignedLongLong>(), PrimitiveLayouts.I32))

public fun VectorUnsignedShort.vecTestMask(other: VectorUnsignedShort): Int = vtm(transmuteWithLayouts<VectorUnsignedShort, Int>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), PrimitiveLayouts.I32), transmuteWithLayouts<VectorUnsignedShort, Int>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), PrimitiveLayouts.I32))

public fun VectorUnsignedLongLong.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmuteWithLayouts<VectorUnsignedLongLong, Int>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), PrimitiveLayouts.I32), transmuteWithLayouts<VectorUnsignedLongLong, Int>(other, UnresolvedTypeLayout<VectorUnsignedLongLong>(), PrimitiveLayouts.I32))

public fun VectorDouble.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmuteWithLayouts<VectorDouble, Int>(this, UnresolvedTypeLayout<VectorDouble>(), PrimitiveLayouts.I32), transmuteWithLayouts<VectorUnsignedLongLong, Int>(other, UnresolvedTypeLayout<VectorUnsignedLongLong>(), PrimitiveLayouts.I32))

public interface VectorSearchString<T> {
    public fun vecSearchStringCc(b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int>

    public fun vecSearchStringUntilZeroCc(b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int>
}

public fun VectorSignedChar.vecSearchStringCc(b: VectorSignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmuteWithLayouts<VectorSignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedChar.vecSearchStringUntilZeroCc(b: VectorSignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmuteWithLayouts<VectorSignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolChar.vecSearchStringCc(b: VectorBoolChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolChar.vecSearchStringUntilZeroCc(b: VectorBoolChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vecSearchStringCc(b: VectorUnsignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsb(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vecSearchStringUntilZeroCc(b: VectorUnsignedChar, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszb(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedShort.vecSearchStringCc(b: VectorSignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmuteWithLayouts<VectorSignedShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedShort.vecSearchStringUntilZeroCc(b: VectorSignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmuteWithLayouts<VectorSignedShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolShort.vecSearchStringCc(b: VectorBoolShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolShort.vecSearchStringUntilZeroCc(b: VectorBoolShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vecSearchStringCc(b: VectorUnsignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsh(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vecSearchStringUntilZeroCc(b: VectorUnsignedShort, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszh(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedInt.vecSearchStringCc(b: VectorSignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmuteWithLayouts<VectorSignedInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorSignedInt.vecSearchStringUntilZeroCc(b: VectorSignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmuteWithLayouts<VectorSignedInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolInt.vecSearchStringCc(b: VectorBoolInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorBoolInt.vecSearchStringUntilZeroCc(b: VectorBoolInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vecSearchStringCc(b: VectorUnsignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrsf(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vecSearchStringUntilZeroCc(b: VectorUnsignedInt, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vstrszf(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedChar, Int>>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), c)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun vcdgb(a: VectorSignedLongLong): VectorDouble = run { val simdArg0 = a; VectorDouble() }

public fun vcdlgb(a: VectorUnsignedLongLong): VectorDouble = run { val simdArg0 = a; VectorDouble() }

public interface VectorDouble {
    public fun vecDouble(): VectorDouble
}

public fun VectorSignedLongLong.vecDouble(): VectorDouble = vcdgb(this)

public fun VectorUnsignedLongLong.vecDouble(): VectorDouble = vcdlgb(this)

public fun vcefb(a: VectorSignedInt): VectorFloat = run { val simdArg0 = a; VectorFloat() }

public fun vcelfb(a: VectorUnsignedInt): VectorFloat = run { val simdArg0 = a; VectorFloat() }

public interface VectorFloat {
    public fun vecFloat(): VectorFloat
}

public fun VectorSignedInt.vecFloat(): VectorFloat = vcefb(this)

public fun VectorUnsignedInt.vecFloat(): VectorFloat = vcelfb(this)

public interface VectorExtendSigned64 {
    public fun vecExtendS64(): VectorSignedLongLong
}

public fun vsegb(a: VectorSignedChar): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>((listOf(7, 15)).toList()); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x2>()).alignBytes)); res } }; VectorSignedLongLong() }

public fun vsegh(a: VectorSignedShort): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>((listOf(3, 7)).toList()); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x2>()).alignBytes)); res } }; VectorSignedLongLong() }

public fun vsegf(a: VectorSignedInt): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>((listOf(1, 3)).toList()); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }

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

public fun vcgsb(a: VectorFloat): VectorSignedInt = run { val simdArg0 = a; VectorSignedInt() }

public fun vcgdb(a: VectorDouble): VectorSignedLongLong = run { val simdArg0 = a; VectorSignedLongLong() }

public fun VectorFloat.vecSigned(): VectorSignedInt = vcgsb(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorDouble.vecSigned(): VectorSignedLongLong = vcgdb(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public interface VectorUnsigned<out Result> {
    public fun vecUnsigned(): Result
}

public fun vclgsb(a: VectorFloat): VectorUnsignedInt = run { val simdArg0 = a; VectorUnsignedInt() }

public fun vclgdb(a: VectorDouble): VectorUnsignedLongLong = run { val simdArg0 = a; VectorUnsignedLongLong() }

public fun VectorFloat.vecUnsigned(): VectorUnsignedInt = vclgsb(transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorDouble.vecUnsigned(): VectorUnsignedLongLong = vclgdb(transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public interface VectorCopyUntilZero<T> {
    public fun vecCpUntilZero(): T
}

public fun vecVistrb(a: VectorUnsignedChar): VectorUnsignedChar = vistrb(a)

public fun vecVistrh(a: VectorUnsignedShort): VectorUnsignedShort = vistrh(a)

public fun vecVistrf(a: VectorUnsignedInt): VectorUnsignedInt = vistrf(a)

public fun VectorSignedChar.vecCpUntilZero(): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vecVistrb(this), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorBoolChar.vecCpUntilZero(): VectorBoolChar = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(vecVistrb(this), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecCpUntilZero(): VectorUnsignedChar = vecVistrb(this)

public fun VectorSignedShort.vecCpUntilZero(): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vecVistrh(this), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolShort.vecCpUntilZero(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vecVistrh(this), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecCpUntilZero(): VectorUnsignedShort = vecVistrh(this)

public fun VectorSignedInt.vecCpUntilZero(): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vecVistrf(this), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorBoolInt.vecCpUntilZero(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vecVistrf(this), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecCpUntilZero(): VectorUnsignedInt = vecVistrf(this)

public interface VectorCopyUntilZeroCC<T> {
    public fun vecCpUntilZeroCc(): Pair<T, Int>
}

public fun vecVistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> = vistrbs(a)

public fun vecVistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int> = vistrhs(a)

public fun vecVistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int> = vistrfs(a)

public fun VectorSignedChar.vecCpUntilZeroCc(): Pair<VectorSignedChar, Int> {
    val structVal = vecVistrbs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCpUntilZeroCc(): Pair<VectorBoolChar, Int> {
    val structVal = vecVistrbs(transmuteWithLayouts<VectorBoolChar, Pair<VectorBoolChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorBoolChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCpUntilZeroCc(): Pair<VectorUnsignedChar, Int> {
    val structVal = vecVistrbs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCpUntilZeroCc(): Pair<VectorSignedShort, Int> {
    val structVal = vecVistrhs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCpUntilZeroCc(): Pair<VectorBoolShort, Int> {
    val structVal = vecVistrhs(transmuteWithLayouts<VectorBoolShort, Pair<VectorBoolShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorBoolShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCpUntilZeroCc(): Pair<VectorUnsignedShort, Int> {
    val structVal = vecVistrhs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCpUntilZeroCc(): Pair<VectorSignedInt, Int> {
    val structVal = vecVistrfs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCpUntilZeroCc(): Pair<VectorBoolInt, Int> {
    val structVal = vecVistrfs(transmuteWithLayouts<VectorBoolInt, Pair<VectorBoolInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorBoolInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCpUntilZeroCc(): Pair<VectorUnsignedInt, Int> {
    val structVal = vecVistrfs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
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
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorSignedChar.vecSldw(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorSignedChar.vecSldb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorSignedChar.vecSrdb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorBoolChar.vecSld(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorBoolChar.vecSldw(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorBoolChar.vecSldb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorBoolChar.vecSrdb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorUnsignedChar.vecSld(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorUnsignedChar.vecSldw(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorUnsignedChar.vecSldb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorUnsignedChar.vecSrdb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorSignedShort.vecSld(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorSignedShort.vecSldw(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorSignedShort.vecSldb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorSignedShort.vecSrdb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorBoolShort.vecSld(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorBoolShort.vecSldw(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorBoolShort.vecSldb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorBoolShort.vecSrdb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorUnsignedShort.vecSld(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorUnsignedShort.vecSldw(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorUnsignedShort.vecSldb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorUnsignedShort.vecSrdb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorSignedInt.vecSld(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorSignedInt.vecSldw(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorSignedInt.vecSldb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorSignedInt.vecSrdb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorBoolInt.vecSld(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorBoolInt.vecSldw(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorBoolInt.vecSldb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorBoolInt.vecSrdb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorUnsignedInt.vecSld(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorUnsignedInt.vecSldw(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorUnsignedInt.vecSldb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorUnsignedInt.vecSrdb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorSignedLongLong.vecSld(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorSignedLongLong.vecSldw(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorSignedLongLong.vecSldb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorSignedLongLong.vecSrdb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorBoolLongLong.vecSld(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorBoolLongLong.vecSldw(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorBoolLongLong.vecSldb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorBoolLongLong.vecSrdb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorUnsignedLongLong.vecSld(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorUnsignedLongLong.vecSldw(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorUnsignedLongLong.vecSldb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorUnsignedLongLong.vecSrdb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorFloat.vecSld(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorFloat.vecSldw(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorFloat.vecSldb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorFloat.vecSrdb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public fun VectorDouble.vecSld(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 8u))
}

public fun VectorDouble.vecSldw(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c * 4u * 8u))
}

public fun VectorDouble.vecSldb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShl(this, b, c))
}

public fun VectorDouble.vecSrdb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(U128.funnelShr(this, b, c))
}

public interface VectorCompareRange<T, out Result> {
    public fun vstrc(b: T, c: T, imm: UInt): Result

    public fun vstrcz(b: T, c: T, imm: UInt): Result

    public fun vstrcs(b: T, c: T, imm: UInt): Pair<Result, Int>

    public fun vstrczs(b: T, c: T, imm: UInt): Pair<Result, Int>
}

public fun validateCompareRangeImm(imm: UInt) {
    if (!when (imm) {
        0u, 4u, 8u, 12u -> true
        else -> false
    }) {
        error("IMM needs to be one of 0, 4, 8, 12")
    }
}

public fun VectorUnsignedChar.vstrc(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): VectorBoolChar {
    ::validateCompareRangeImm
    return vstrcb(this, b, c, imm)
}

public fun VectorUnsignedChar.vstrcz(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): VectorBoolChar {
    ::validateCompareRangeImm
    return vstrczb(this, b, c, imm)
}

public fun VectorUnsignedChar.vstrcs(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): Pair<VectorBoolChar, Int> {
    ::validateCompareRangeImm
    val structVal = vstrcbs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedChar.vstrczs(b: VectorUnsignedChar, c: VectorUnsignedChar, imm: UInt): Pair<VectorBoolChar, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczbs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vstrc(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): VectorBoolShort {
    ::validateCompareRangeImm
    return vstrch(this, b, c, imm)
}

public fun VectorUnsignedShort.vstrcz(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): VectorBoolShort {
    ::validateCompareRangeImm
    return vstrczh(this, b, c, imm)
}

public fun VectorUnsignedShort.vstrcs(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): Pair<VectorBoolShort, Int> {
    ::validateCompareRangeImm
    val structVal = vstrchs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedShort.vstrczs(b: VectorUnsignedShort, c: VectorUnsignedShort, imm: UInt): Pair<VectorBoolShort, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczhs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vstrc(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): VectorBoolInt {
    ::validateCompareRangeImm
    return vstrcf(this, b, c, imm)
}

public fun VectorUnsignedInt.vstrcz(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): VectorBoolInt {
    ::validateCompareRangeImm
    return vstrczf(this, b, c, imm)
}

public fun VectorUnsignedInt.vstrcs(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): Pair<VectorBoolInt, Int> {
    ::validateCompareRangeImm
    val structVal = vstrcfs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public fun VectorUnsignedInt.vstrczs(b: VectorUnsignedInt, c: VectorUnsignedInt, imm: UInt): Pair<VectorBoolInt, Int> {
    ::validateCompareRangeImm
    val structVal = vstrczfs(this, b, c, imm)
    val x = structVal.x
    val y = structVal.y
    return Pair(x, y)
}

public interface VectorComparePredicate<T, out Result> {
    public fun vecCmpgt(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }

    public fun vecCmpge(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }

    public fun vecCmplt(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }

    public fun vecCmple(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }
}

public interface VectorEquality<T, out Result> {
    public fun vecCmpeq(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }

    public fun vecCmpne(other: T): Result = run { val simdArg0 = this; val simdArg1 = other; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); val res = (UnresolvedTypeLayout<Result>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Result>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Result>()).alignBytes)); res } }
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

public fun VectorSignedChar.vecCmpeqIdx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeeb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpneIdx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeneb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpeqOr0Idx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeezb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpneOr0Idx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfenezb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpeqIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(other, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpneIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenebs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(other, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpeqOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeezbs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(other, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedChar.vecCmpneOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenezbs(transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()), transmuteWithLayouts<VectorSignedChar, Pair<VectorSignedChar, Int>>(other, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Pair<VectorSignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpeqIdx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeeb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpneIdx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeneb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpeqOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeezb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpneOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfenezb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpeqIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpneIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpeqOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolChar.vecCmpneOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorBoolChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpeqIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeeb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpneIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeneb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpeqOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeezb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpneOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfenezb(this, other), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpeqIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpneIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpeqOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedChar.vecCmpneOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()), transmuteWithLayouts<VectorUnsignedChar, Pair<VectorUnsignedChar, Int>>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<Pair<VectorUnsignedChar, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpeqIdx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeeh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpneIdx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeneh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpeqOr0Idx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeezh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpneOr0Idx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfenezh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpeqIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(other, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpneIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenehs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(other, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpeqOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeezhs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(other, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedShort.vecCmpneOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenezhs(transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()), transmuteWithLayouts<VectorSignedShort, Pair<VectorSignedShort, Int>>(other, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Pair<VectorSignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpeqIdx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeeh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpneIdx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeneh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpeqOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeezh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpneOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfenezh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpeqIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpneIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpeqOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolShort.vecCmpneOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorBoolShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpeqIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeeh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpneIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeneh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpeqOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeezh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpneOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfenezh(this, other), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpeqIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpneIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpeqOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedShort.vecCmpneOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()), transmuteWithLayouts<VectorUnsignedShort, Pair<VectorUnsignedShort, Int>>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<Pair<VectorUnsignedShort, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpeqIdx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfeef(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpneIdx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfenef(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpeqOr0Idx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfeezf(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpneOr0Idx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfenezf(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpeqIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(other, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpneIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenefs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(other, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpeqOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeezfs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(other, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorSignedInt.vecCmpneOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenezfs(transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()), transmuteWithLayouts<VectorSignedInt, Pair<VectorSignedInt, Int>>(other, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Pair<VectorSignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpeqIdx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpneIdx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpeqOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeezf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpneOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenezf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpeqIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpneIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpeqOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorBoolInt.vecCmpneOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorBoolInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpeqIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeef(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpneIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenef(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpeqOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeezf(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpneOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenezf(this, other), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpeqIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpneIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpeqOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public fun VectorUnsignedInt.vecCmpneOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()), transmuteWithLayouts<VectorUnsignedInt, Pair<VectorUnsignedInt, Int>>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<Pair<VectorUnsignedInt, Int>>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmute(x), y)
}

public interface VectorExtract<T, out ElementType> {
    public fun vecExtract(a: T, b: Int): ElementType
}

public fun vlgvb(a: VectorUnsignedChar, b: Int): UByte = run { val simdArg0 = a; val simdArg1 = b.toUInt() % 16u; 0u }

public fun vlgvh(a: VectorUnsignedShort, b: Int): UShort = run { val simdArg0 = a; val simdArg1 = b.toUInt() % 8u; 0u }

public fun vlgvf(a: VectorUnsignedInt, b: Int): UInt = run { val simdArg0 = a; val simdArg1 = b.toUInt() % 4u; 0u }

public fun vlgvg(a: VectorUnsignedLongLong, b: Int): ULong = run { val simdArg0 = a; val simdArg1 = b.toUInt() % 2u; 0uL }

public interface VectorInsert<T, ElementType> {
    public fun vecInsert(a: ElementType, b: T, c: Int): T
}

public interface VectorPromote<T, ElementType> {
    public fun vecPromote(a: ElementType, b: Int): MaybeUninit<T>
}

public fun vlvgb(a: UByte, b: VectorUnsignedChar, c: Int): VectorUnsignedChar = run { val simdArg0 = b; val simdArg1 = c.toUInt() % 16u; val simdArg2 = a; simdArg0 }

public fun vlvgh(a: UShort, b: VectorUnsignedShort, c: Int): VectorUnsignedShort = run { val simdArg0 = b; val simdArg1 = c.toUInt() % 8u; val simdArg2 = a; simdArg0 }

public fun vlvgf(a: UInt, b: VectorUnsignedInt, c: Int): VectorUnsignedInt = run { val simdArg0 = b; val simdArg1 = c.toUInt() % 4u; val simdArg2 = a; simdArg0 }

public fun vlvgg(a: ULong, b: VectorUnsignedLongLong, c: Int): VectorUnsignedLongLong = run { val simdArg0 = b; val simdArg1 = c.toUInt() % 2u; val simdArg2 = a; simdArg0 }

public interface VectorInsertAndZero<T, ElementType> {
    public fun vecInsertAndZero(a: ConstPtr): T
}

public fun vllezb(x: ConstPtr): VectorUnsignedChar = vectorUnsignedChar(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, (PrimitiveLayouts.U8).readNative((x).rawAddress), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u))

public fun vllezh(x: ConstPtr): VectorUnsignedShort = vectorUnsignedShort(ushortArrayOf(0u, 0u, 0u, (PrimitiveLayouts.U16).readNative((x).rawAddress), 0u, 0u, 0u, 0u))

public fun vllezf(x: ConstPtr): VectorUnsignedInt = vectorUnsignedInt(uintArrayOf(0u, (PrimitiveLayouts.U32).readNative((x).rawAddress), 0u, 0u))

public fun vllezg(x: ConstPtr): VectorUnsignedLongLong = vectorUnsignedLongLong(ulongArrayOf((PrimitiveLayouts.U64).readNative((x).rawAddress), 0uL))

public fun vectorExtractVecExtractVectorSignedChar(a: VectorSignedChar, b: Int): Byte = transmuteWithLayouts<UByte, Byte>(vlgvb(transmuteWithLayouts<VectorSignedChar, Byte>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<Byte>()), b), UnresolvedTypeLayout<UByte>(), UnresolvedTypeLayout<Byte>())

public fun vectorInsertVecInsertVectorSignedChar(a: Byte, b: VectorSignedChar, c: Int): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vlvgb(transmuteWithLayouts<Byte, VectorSignedChar>(a, UnresolvedTypeLayout<Byte>(), UnresolvedTypeLayout<VectorSignedChar>()), b, c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedChar(a: ConstPtr): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vllezb(a), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun vectorPromoteVecPromoteVectorSignedChar(a: Byte, c: Int): MaybeUninit<VectorSignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedChar>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedChar, MaybeUninit<VectorSignedChar>>(vlvgb(transmuteWithLayouts<Byte, MaybeUninit<VectorSignedChar>>(a, UnresolvedTypeLayout<Byte>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedChar>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedChar>>()), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vectorExtractVecExtractVectorUnsignedChar(a: VectorUnsignedChar, b: Int): UByte = vlgvb(transmuteWithLayouts<VectorUnsignedChar, UByte>(a, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<UByte>()), b)

public fun vectorInsertVecInsertVectorUnsignedChar(a: UByte, b: VectorUnsignedChar, c: Int): VectorUnsignedChar = vlvgb(transmuteWithLayouts<UByte, VectorUnsignedChar>(a, UnresolvedTypeLayout<UByte>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedChar(a: ConstPtr): VectorUnsignedChar = vllezb(a)

public fun vectorPromoteVecPromoteVectorUnsignedChar(a: UByte, c: Int): MaybeUninit<VectorUnsignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedChar>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedChar, MaybeUninit<VectorUnsignedChar>>(vlvgb(transmuteWithLayouts<UByte, MaybeUninit<VectorUnsignedChar>>(a, UnresolvedTypeLayout<UByte>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedChar>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedChar>>()), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vectorExtractVecExtractVectorSignedShort(a: VectorSignedShort, b: Int): Short = transmuteWithLayouts<UShort, Short>(vlgvh(transmuteWithLayouts<VectorSignedShort, Short>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<Short>()), b), UnresolvedTypeLayout<UShort>(), UnresolvedTypeLayout<Short>())

public fun vectorInsertVecInsertVectorSignedShort(a: Short, b: VectorSignedShort, c: Int): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vlvgh(transmuteWithLayouts<Short, VectorSignedShort>(a, UnresolvedTypeLayout<Short>(), UnresolvedTypeLayout<VectorSignedShort>()), b, c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedShort(a: ConstPtr): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vllezh(a), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorPromoteVecPromoteVectorSignedShort(a: Short, c: Int): MaybeUninit<VectorSignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedShort>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedShort, MaybeUninit<VectorSignedShort>>(vlvgh(transmuteWithLayouts<Short, MaybeUninit<VectorSignedShort>>(a, UnresolvedTypeLayout<Short>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedShort>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedShort>>()), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun vectorExtractVecExtractVectorUnsignedShort(a: VectorUnsignedShort, b: Int): UShort = vlgvh(transmuteWithLayouts<VectorUnsignedShort, UShort>(a, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<UShort>()), b)

public fun vectorInsertVecInsertVectorUnsignedShort(a: UShort, b: VectorUnsignedShort, c: Int): VectorUnsignedShort = vlvgh(transmuteWithLayouts<UShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<UShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedShort(a: ConstPtr): VectorUnsignedShort = vllezh(a)

public fun vectorPromoteVecPromoteVectorUnsignedShort(a: UShort, c: Int): MaybeUninit<VectorUnsignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedShort>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedShort, MaybeUninit<VectorUnsignedShort>>(vlvgh(transmuteWithLayouts<UShort, MaybeUninit<VectorUnsignedShort>>(a, UnresolvedTypeLayout<UShort>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedShort>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedShort>>()), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vectorExtractVecExtractVectorSignedInt(a: VectorSignedInt, b: Int): Int = transmuteWithLayouts<UInt, Int>(vlgvf(transmuteWithLayouts<VectorSignedInt, Int>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<Int>()), b), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Int>())

public fun vectorInsertVecInsertVectorSignedInt(a: Int, b: VectorSignedInt, c: Int): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vlvgf(transmuteWithLayouts<Int, VectorSignedInt>(a, UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<VectorSignedInt>()), b, c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedInt(a: ConstPtr): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vllezf(a), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorPromoteVecPromoteVectorSignedInt(a: Int, c: Int): MaybeUninit<VectorSignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedInt>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorSignedInt>>(vlvgf(transmuteWithLayouts<Int, MaybeUninit<VectorSignedInt>>(a, UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedInt>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedInt>>()), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun vectorExtractVecExtractVectorUnsignedInt(a: VectorUnsignedInt, b: Int): UInt = vlgvf(transmuteWithLayouts<VectorUnsignedInt, UInt>(a, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<UInt>()), b)

public fun vectorInsertVecInsertVectorUnsignedInt(a: UInt, b: VectorUnsignedInt, c: Int): VectorUnsignedInt = vlvgf(transmuteWithLayouts<UInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedInt(a: ConstPtr): VectorUnsignedInt = vllezf(a)

public fun vectorPromoteVecPromoteVectorUnsignedInt(a: UInt, c: Int): MaybeUninit<VectorUnsignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedInt>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorUnsignedInt>>(vlvgf(transmuteWithLayouts<UInt, MaybeUninit<VectorUnsignedInt>>(a, UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedInt>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedInt>>()), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun vectorExtractVecExtractVectorSignedLongLong(a: VectorSignedLongLong, b: Int): Long = transmuteWithLayouts<ULong, Long>(vlgvg(transmuteWithLayouts<VectorSignedLongLong, Long>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<Long>()), b), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Long>())

public fun vectorInsertVecInsertVectorSignedLongLong(a: Long, b: VectorSignedLongLong, c: Int): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vlvgg(transmuteWithLayouts<Long, VectorSignedLongLong>(a, UnresolvedTypeLayout<Long>(), UnresolvedTypeLayout<VectorSignedLongLong>()), b, c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedLongLong(a: ConstPtr): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vllezg(a), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorPromoteVecPromoteVectorSignedLongLong(a: Long, c: Int): MaybeUninit<VectorSignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedLongLong>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorSignedLongLong>>(vlvgg(transmuteWithLayouts<Long, MaybeUninit<VectorSignedLongLong>>(a, UnresolvedTypeLayout<Long>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedLongLong>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedLongLong>>()), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun vectorExtractVecExtractVectorUnsignedLongLong(a: VectorUnsignedLongLong, b: Int): ULong = vlgvg(transmuteWithLayouts<VectorUnsignedLongLong, ULong>(a, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<ULong>()), b)

public fun vectorInsertVecInsertVectorUnsignedLongLong(a: ULong, b: VectorUnsignedLongLong, c: Int): VectorUnsignedLongLong = vlvgg(transmuteWithLayouts<ULong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedLongLong(a: ConstPtr): VectorUnsignedLongLong = vllezg(a)

public fun vectorPromoteVecPromoteVectorUnsignedLongLong(a: ULong, c: Int): MaybeUninit<VectorUnsignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedLongLong>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorUnsignedLongLong>>(vlvgg(transmuteWithLayouts<ULong, MaybeUninit<VectorUnsignedLongLong>>(a, UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedLongLong>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedLongLong>>()), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun vectorExtractVecExtractVectorFloat(a: VectorFloat, b: Int): Float = transmuteWithLayouts<UInt, Float>(vlgvf(transmuteWithLayouts<VectorFloat, Float>(a, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<Float>()), b), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Float>())

public fun vectorInsertVecInsertVectorFloat(a: Float, b: VectorFloat, c: Int): VectorFloat = transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(vlvgf(transmuteWithLayouts<Float, VectorFloat>(a, UnresolvedTypeLayout<Float>(), UnresolvedTypeLayout<VectorFloat>()), b, c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorFloat(a: ConstPtr): VectorFloat = transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(vllezf(a), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorPromoteVecPromoteVectorFloat(a: Float, c: Int): MaybeUninit<VectorFloat> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorFloat>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorFloat>>(vlvgf(transmuteWithLayouts<Float, MaybeUninit<VectorFloat>>(a, UnresolvedTypeLayout<Float>(), UnresolvedTypeLayout<MaybeUninit<VectorFloat>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorFloat>>()), UnresolvedTypeLayout<VectorFloat>())
}

public fun vectorExtractVecExtractVectorDouble(a: VectorDouble, b: Int): Double = transmuteWithLayouts<ULong, Double>(vlgvg(transmuteWithLayouts<VectorDouble, Double>(a, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<Double>()), b), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Double>())

public fun vectorInsertVecInsertVectorDouble(a: Double, b: VectorDouble, c: Int): VectorDouble = transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(vlvgg(transmuteWithLayouts<Double, VectorDouble>(a, UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<VectorDouble>()), b, c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorDouble(a: ConstPtr): VectorDouble = transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(vllezg(a), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorPromoteVecPromoteVectorDouble(a: Double, c: Int): MaybeUninit<VectorDouble> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorDouble>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorDouble>>(vlvgg(transmuteWithLayouts<Double, MaybeUninit<VectorDouble>>(a, UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<MaybeUninit<VectorDouble>>()), transmute(b), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorDouble>>()), UnresolvedTypeLayout<VectorDouble>())
}

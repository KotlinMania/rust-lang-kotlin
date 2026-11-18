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
import io.github.kotlinmania.rustlang.corearch.s390x.vector.FindImm
import io.github.kotlinmania.rustlang.corearch.s390x.vector.PackedTuple
import io.github.kotlinmania.rustlang.corearch.s390x.vector.ShuffleMask
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
import kotlin.experimental.inv

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

public fun vaSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaSc(this, other)

public fun vaUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaUc(this, other)

public fun vaSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaSh(this, other)

public fun vaUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaUh(this, other)

public fun vaSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaSf(this, other)

public fun vaUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaUf(this, other)

public fun vaSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaSg(this, other)

public fun vaUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaUg(this, other)

public fun vaScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecAdd(other: VectorBoolChar): VectorSignedChar = vaScBc(this, other)

public fun vaUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecAdd(other: VectorBoolChar): VectorUnsignedChar = vaUcBc(this, other)

public fun vaShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecAdd(other: VectorBoolShort): VectorSignedShort = vaShBh(this, other)

public fun vaUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecAdd(other: VectorBoolShort): VectorUnsignedShort = vaUhBh(this, other)

public fun vaSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecAdd(other: VectorBoolInt): VectorSignedInt = vaSfBf(this, other)

public fun vaUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecAdd(other: VectorBoolInt): VectorUnsignedInt = vaUfBf(this, other)

public fun vaSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecAdd(other: VectorBoolLongLong): VectorSignedLongLong = vaSgBg(this, other)

public fun vaUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecAdd(other: VectorBoolLongLong): VectorUnsignedLongLong = vaUgBg(this, other)

public fun vaBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vaBcSc(this, other)

public fun vaBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vaBcUc(this, other)

public fun vaBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vaBhSh(this, other)

public fun vaBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vaBhUh(this, other)

public fun vaBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vaBfSf(this, other)

public fun vaBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vaBfUf(this, other)

public fun vaBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolLongLong.vecAdd(other: VectorSignedLongLong): VectorSignedLongLong = vaBgSg(this, other)

public fun vaBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolLongLong.vecAdd(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vaBgUg(this, other)

public fun vaDouble(a: VectorDouble, b: VectorDouble): VectorDouble = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorDouble.vecAdd(other: VectorDouble): VectorDouble = vaDouble(this, other)

public fun vaFloat(a: VectorFloat, b: VectorFloat): VectorFloat = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorFloat.vecAdd(other: VectorFloat): VectorFloat = vaFloat(this, other)

public interface VectorSub<Other, out Result> {
    public fun vecSub(other: Other): Result
}

public fun vsSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsSc(this, other)

public fun vsUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsUc(this, other)

public fun vsSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsSh(this, other)

public fun vsUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsUh(this, other)

public fun vsSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsSf(this, other)

public fun vsUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsUf(this, other)

public fun vsSg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsSg(this, other)

public fun vsUg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsUg(this, other)

public fun vsScBc(a: VectorSignedChar, b: VectorBoolChar): VectorSignedChar = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecSub(other: VectorBoolChar): VectorSignedChar = vsScBc(this, other)

public fun vsUcBc(a: VectorUnsignedChar, b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(a, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSub(other: VectorBoolChar): VectorUnsignedChar = vsUcBc(this, other)

public fun vsShBh(a: VectorSignedShort, b: VectorBoolShort): VectorSignedShort = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSub(other: VectorBoolShort): VectorSignedShort = vsShBh(this, other)

public fun vsUhBh(a: VectorUnsignedShort, b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(a, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSub(other: VectorBoolShort): VectorUnsignedShort = vsUhBh(this, other)

public fun vsSfBf(a: VectorSignedInt, b: VectorBoolInt): VectorSignedInt = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSub(other: VectorBoolInt): VectorSignedInt = vsSfBf(this, other)

public fun vsUfBf(a: VectorUnsignedInt, b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(a, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSub(other: VectorBoolInt): VectorUnsignedInt = vsUfBf(this, other)

public fun vsSgBg(a: VectorSignedLongLong, b: VectorBoolLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSub(other: VectorBoolLongLong): VectorSignedLongLong = vsSgBg(this, other)

public fun vsUgBg(a: VectorUnsignedLongLong, b: VectorBoolLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(a, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>()); val simdArg1 = b; simdArg0 }, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSub(other: VectorBoolLongLong): VectorUnsignedLongLong = vsUgBg(this, other)

public fun vsBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecSub(other: VectorSignedChar): VectorSignedChar = vsBcSc(this, other)

public fun vsBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecSub(other: VectorUnsignedChar): VectorUnsignedChar = vsBcUc(this, other)

public fun vsBhSh(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolShort.vecSub(other: VectorSignedShort): VectorSignedShort = vsBhSh(this, other)

public fun vsBhUh(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolShort.vecSub(other: VectorUnsignedShort): VectorUnsignedShort = vsBhUh(this, other)

public fun vsBfSf(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolInt.vecSub(other: VectorSignedInt): VectorSignedInt = vsBfSf(this, other)

public fun vsBfUf(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolInt.vecSub(other: VectorUnsignedInt): VectorUnsignedInt = vsBfUf(this, other)

public fun vsBgSg(a: VectorBoolLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolLongLong.vecSub(other: VectorSignedLongLong): VectorSignedLongLong = vsBgSg(this, other)

public fun vsBgUg(a: VectorBoolLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolLongLong.vecSub(other: VectorUnsignedLongLong): VectorUnsignedLongLong = vsBgUg(this, other)

public fun vsDouble(a: VectorDouble, b: VectorDouble): VectorDouble = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorDouble.vecSub(other: VectorDouble): VectorDouble = vsDouble(this, other)

public fun vsFloat(a: VectorFloat, b: VectorFloat): VectorFloat = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorFloat.vecSub(other: VectorFloat): VectorFloat = vsFloat(this, other)

public interface VectorMul<T> {
    public fun vecMul(b: T): T
}

public fun vmlSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecMul(other: VectorSignedChar): VectorSignedChar = vmlSc(this, other)

public fun vmlUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecMul(other: VectorUnsignedChar): VectorUnsignedChar = vmlUc(this, other)

public fun vmlSh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecMul(other: VectorSignedShort): VectorSignedShort = vmlSh(this, other)

public fun vmlUh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecMul(other: VectorUnsignedShort): VectorUnsignedShort = vmlUh(this, other)

public fun vmlSf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecMul(other: VectorSignedInt): VectorSignedInt = vmlSf(this, other)

public fun vmlUf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecMul(other: VectorUnsignedInt): VectorUnsignedInt = vmlUf(this, other)

public fun VectorSignedLongLong.vecMul(other: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = this; val simdArg1 = other; simdArg0 }

public fun VectorUnsignedLongLong.vecMul(other: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = this; val simdArg1 = other; simdArg0 }

public fun VectorFloat.vecMul(other: VectorFloat): VectorFloat = run { val simdArg0 = this; val simdArg1 = other; simdArg0 }

public fun vmlDouble(a: VectorDouble, b: VectorDouble): VectorDouble = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

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

public fun VectorSignedChar.vecSplat(imm: UInt): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(vrepb(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecSplat(imm: UInt): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(vrepb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecSplat(imm: UInt): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(vrepb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecSplat(imm: UInt): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(vreph(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecSplat(imm: UInt): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(vreph(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecSplat(imm: UInt): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(vreph(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecSplat(imm: UInt): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(vrepf(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedInt.vecSplat(imm: UInt): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(vrepf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecSplat(imm: UInt): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(vrepf(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorSignedLongLong.vecSplat(imm: UInt): VectorSignedLongLong = transmuteWithLayouts<Any?, VectorSignedLongLong>(vrepg(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorUnsignedLongLong.vecSplat(imm: UInt): VectorUnsignedLongLong = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(vrepg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorBoolLongLong.vecSplat(imm: UInt): VectorBoolLongLong = transmuteWithLayouts<Any?, VectorBoolLongLong>(vrepg(transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorFloat.vecSplat(imm: UInt): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(vrepf(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSplat(imm: UInt): VectorDouble = transmuteWithLayouts<Any?, VectorDouble>(vrepg(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSplats<Output, Result> {
    public fun vecSplats(): Output
}

public fun vecSplatsU8(v: UByte): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(Simd<UByte>(List(16) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun UByte.vecSplats(): VectorUnsignedChar = vecSplatsU8(this)

public fun vecSplatsI8(v: Byte): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(Simd<Byte>(List(16) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun Byte.vecSplats(): VectorSignedChar = vecSplatsI8(this)

public fun vecSplatsU16(v: UShort): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(Simd<UShort>(List(8) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun UShort.vecSplats(): VectorUnsignedShort = vecSplatsU16(this)

public fun vecSplatsI16(v: Short): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(Simd<Short>(List(8) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun Short.vecSplats(): VectorSignedShort = vecSplatsI16(this)

public fun vecSplatsU32(v: UInt): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(Simd<UInt>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun UInt.vecSplats(): VectorUnsignedInt = vecSplatsU32(this)

public fun vecSplatsI32(v: Int): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<Int>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun Int.vecSplats(): VectorSignedInt = vecSplatsI32(this)

public fun vecSplatsU64(v: ULong): VectorUnsignedLongLong = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(Simd<ULong>(List(2) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun ULong.vecSplats(): VectorUnsignedLongLong = vecSplatsU64(this)

public fun vecSplatsI64(v: Long): VectorSignedLongLong = transmuteWithLayouts<Any?, VectorSignedLongLong>(Simd<Long>(List(2) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun Long.vecSplats(): VectorSignedLongLong = vecSplatsI64(this)

public fun vecSplatsF32(v: Float): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(Simd<Float>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun Float.vecSplats(): VectorFloat = vecSplatsF32(this)

public fun vecSplatsF64(v: Double): VectorDouble = transmuteWithLayouts<Any?, VectorDouble>(Simd<Double>(List(2) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())

public fun Double.vecSplats(): VectorDouble = vecSplatsF64(this)

public fun UByte.vecSplatsVectorBoolChar(): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(Simd<UByte>(List(16) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun Byte.vecSplatsVectorBoolChar(): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(Simd<Byte>(List(16) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun UShort.vecSplatsVectorBoolShort(): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(Simd<UShort>(List(8) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun Short.vecSplatsVectorBoolShort(): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(Simd<Short>(List(8) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun UInt.vecSplatsVectorBoolInt(): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(Simd<UInt>(List(4) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun Int.vecSplatsVectorBoolInt(): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(Simd<Int>(List(4) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun ULong.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmuteWithLayouts<Any?, VectorBoolLongLong>(Simd<ULong>(List(2) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun Long.vecSplatsVectorBoolLongLong(): VectorBoolLongLong = transmuteWithLayouts<Any?, VectorBoolLongLong>(Simd<Long>(List(2) { this }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public interface CountBits<out Result> {
    public fun vecCntlz(): Result

    public fun vecCnttz(): Result

    public fun vecPopcnt(): Result
}

public fun VectorSignedChar.vecCntlz(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecCnttz(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecPopcnt(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCntlz(): VectorUnsignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedChar.vecCnttz(): VectorUnsignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedChar.vecPopcnt(): VectorUnsignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedShort.vecCntlz(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecCnttz(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecPopcnt(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCntlz(): VectorUnsignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedShort.vecCnttz(): VectorUnsignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedShort.vecPopcnt(): VectorUnsignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedInt.vecCntlz(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecCnttz(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecPopcnt(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCntlz(): VectorUnsignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedInt.vecCnttz(): VectorUnsignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedInt.vecPopcnt(): VectorUnsignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedLongLong.vecCntlz(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecCnttz(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecPopcnt(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(run { val simdArg0 = this; simdArg0 }, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecCntlz(): VectorUnsignedLongLong = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedLongLong.vecCnttz(): VectorUnsignedLongLong = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedLongLong.vecPopcnt(): VectorUnsignedLongLong = run { val simdArg0 = this; simdArg0 }

public fun vecClzbSigned(a: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vecClzhSigned(a: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vecClzfSigned(a: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vecClzgSigned(a: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vecClzbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; simdArg0 }

public fun vecClzhUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; simdArg0 }

public fun vecClzfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; simdArg0 }

public fun vecClzgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; simdArg0 }

public fun vecCtzbSigned(a: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vecCtzhSigned(a: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vecCtzfSigned(a: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vecCtzgSigned(a: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(run { val simdArg0 = a; simdArg0 }, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vecCtzbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; simdArg0 }

public fun vecCtzhUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; simdArg0 }

public fun vecCtzfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; simdArg0 }

public fun vecCtzgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctbSigned(a: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; simdArg0 }

public fun vecVpopcthSigned(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctfSigned(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctgSigned(a: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctbUnsigned(a: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; simdArg0 }

public fun vecVpopcthUnsigned(a: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctfUnsigned(a: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; simdArg0 }

public fun vecVpopctgUnsigned(a: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; simdArg0 }

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
    return transmuteWithLayouts<U8x16, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 }, UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorNor<Other, out Result> {
    public fun vecNor(b: Other): Result
}

public fun VectorBoolChar.vecNor(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(nor(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNor(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(nor(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNor(b: VectorSignedChar): VectorSignedChar = nor(this, b)

public fun VectorBoolShort.vecNor(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(nor(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNor(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(nor(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNor(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(nor(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecNor(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(nor(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNor(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(nor(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNor(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(nor(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecNor(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(nor(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecNor(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(nor(transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecNor(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(nor(transmuteWithLayouts<VectorFloat, VectorSignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorFloat, VectorSignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecNor(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(nor(transmuteWithLayouts<VectorDouble, VectorSignedChar>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorDouble, VectorSignedChar>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun nand(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    val b1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    return transmuteWithLayouts<U8x16, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 }, UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorNand<Other, out Result> {
    public fun vecNand(b: Other): Result
}

public fun VectorBoolChar.vecNand(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(nand(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNand(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(nand(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNand(b: VectorSignedChar): VectorSignedChar = nand(this, b)

public fun VectorBoolShort.vecNand(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(nand(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNand(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(nand(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNand(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(nand(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecNand(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(nand(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNand(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(nand(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNand(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(nand(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecNand(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(nand(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecNand(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(nand(transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecNand(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(nand(transmuteWithLayouts<VectorFloat, VectorSignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorFloat, VectorSignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecNand(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(nand(transmuteWithLayouts<VectorDouble, VectorSignedChar>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorDouble, VectorSignedChar>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun eqv(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    val b1: U8x16 = transmuteWithLayouts<VectorSignedChar, U8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<U8x16>())
    return transmuteWithLayouts<U8x16, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg0 }, UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorEqv<Other, out Result> {
    public fun vecEqv(b: Other): Result
}

public fun VectorBoolChar.vecEqv(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(eqv(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecEqv(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(eqv(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecEqv(b: VectorSignedChar): VectorSignedChar = eqv(this, b)

public fun VectorBoolShort.vecEqv(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(eqv(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecEqv(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(eqv(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecEqv(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(eqv(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecEqv(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(eqv(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecEqv(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(eqv(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecEqv(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(eqv(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecEqv(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(eqv(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecEqv(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(eqv(transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecEqv(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(eqv(transmuteWithLayouts<VectorFloat, VectorSignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorFloat, VectorSignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecEqv(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(eqv(transmuteWithLayouts<VectorDouble, VectorSignedChar>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorDouble, VectorSignedChar>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun andc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmuteWithLayouts<Any?, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorAndc<Other, out Result> {
    public fun vecAndc(b: Other): Result
}

public fun VectorBoolChar.vecAndc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(andc(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecAndc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(andc(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecAndc(b: VectorSignedChar): VectorSignedChar = andc(this, b)

public fun VectorBoolShort.vecAndc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(andc(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecAndc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(andc(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecAndc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(andc(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecAndc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(andc(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecAndc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(andc(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecAndc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(andc(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecAndc(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(andc(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecAndc(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(andc(transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecAndc(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(andc(transmuteWithLayouts<VectorFloat, VectorSignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorFloat, VectorSignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecAndc(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(andc(transmuteWithLayouts<VectorDouble, VectorSignedChar>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorDouble, VectorSignedChar>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun orc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmuteWithLayouts<Any?, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorOrc<Other, out Result> {
    public fun vecOrc(b: Other): Result
}

public fun VectorBoolChar.vecOrc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(orc(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecOrc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(orc(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecOrc(b: VectorSignedChar): VectorSignedChar = orc(this, b)

public fun VectorBoolShort.vecOrc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(orc(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecOrc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(orc(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecOrc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(orc(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecOrc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(orc(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecOrc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(orc(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecOrc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(orc(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecOrc(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(orc(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecOrc(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(orc(transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedLongLong, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecOrc(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(orc(transmuteWithLayouts<VectorFloat, VectorSignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorFloat, VectorSignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecOrc(b: VectorDouble): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(orc(transmuteWithLayouts<VectorDouble, VectorSignedChar>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorDouble, VectorSignedChar>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

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
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun veslvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(16u.toUShort()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun veslvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(32u); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun veslvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U64x2.splat(64u.toULong()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorUnsignedChar.vecSl(b: VectorUnsignedChar): VectorUnsignedChar = veslvb(this, b)

public fun VectorSignedChar.vecSl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(veslvb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSl(b: VectorUnsignedShort): VectorUnsignedShort = veslvh(this, b)

public fun VectorSignedShort.vecSl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(veslvh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSl(b: VectorUnsignedInt): VectorUnsignedInt = veslvf(this, b)

public fun VectorSignedInt.vecSl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(veslvf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSl(b: VectorUnsignedLongLong): VectorUnsignedLongLong = veslvg(this, b)

public fun VectorSignedLongLong.vecSl(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(veslvg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSr<Other, out Result> {
    public fun vecSr(b: Other): Result
}

public fun vesrlvb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U8x16.splat(8u.toUByte()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vesrlvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(16u.toUShort()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vesrlvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(32u); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun vesrlvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U64x2.splat(64u.toULong()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorUnsignedChar.vecSr(b: VectorUnsignedChar): VectorUnsignedChar = vesrlvb(this, b)

public fun VectorSignedChar.vecSr(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vesrlvb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSr(b: VectorUnsignedShort): VectorUnsignedShort = vesrlvh(this, b)

public fun VectorSignedShort.vecSr(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vesrlvh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSr(b: VectorUnsignedInt): VectorUnsignedInt = vesrlvf(this, b)

public fun VectorSignedInt.vecSr(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vesrlvf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSr(b: VectorUnsignedLongLong): VectorUnsignedLongLong = vesrlvg(this, b)

public fun VectorSignedLongLong.vecSr(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vesrlvg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSra<Other, out Result> {
    public fun vecSra(b: Other): Result
}

public fun vesravb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I8x16.splat(8u.toByte()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorSignedChar>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vesravh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I16x8.splat(16u.toShort()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorSignedShort>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun vesravf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I32x4.splat(32u.toInt()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorSignedInt>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun vesravg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = I64x2.splat(64u.toLong()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorSignedLongLong>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorUnsignedChar.vecSra(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vesravb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSra(b: VectorUnsignedChar): VectorSignedChar = vesravb(this, transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorUnsignedShort.vecSra(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vesravh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSra(b: VectorUnsignedShort): VectorSignedShort = vesravh(this, transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorUnsignedInt.vecSra(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vesravf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSra(b: VectorUnsignedInt): VectorSignedInt = vesravf(this, transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorUnsignedLongLong.vecSra(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vesravg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSra(b: VectorUnsignedLongLong): VectorSignedLongLong = vesravg(this, transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()))

public interface VectorSlb<Other, out Result> {
    public fun vecSlb(b: Other): Result
}

public fun VectorUnsignedChar.vecSlb(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSlb(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSlb(b: VectorSignedChar): VectorSignedChar = vslb(transmute(this), transmute(b))

public fun VectorSignedChar.vecSlb(b: VectorUnsignedChar): VectorSignedChar = vslb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSlb(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSlb(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSlb(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSlb(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSlb(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSlb(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSlb(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSlb(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSlb(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSlb(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSlb(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSlb(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSlb(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSlb(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSlb(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSlb(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vslb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrab<Other, out Result> {
    public fun vecSrab(b: Other): Result
}

public fun VectorUnsignedChar.vecSrab(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSrab(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrab(b: VectorSignedChar): VectorSignedChar = vsrab(transmute(this), transmute(b))

public fun VectorSignedChar.vecSrab(b: VectorUnsignedChar): VectorSignedChar = vsrab(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSrab(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSrab(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrab(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSrab(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrab(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSrab(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrab(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSrab(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrab(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSrab(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrab(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSrab(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSrab(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSrab(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSrab(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSrab(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrb<Other, out Result> {
    public fun vecSrb(b: Other): Result
}

public fun VectorUnsignedChar.vecSrb(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSrb(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrb(b: VectorSignedChar): VectorSignedChar = vsrlb(transmute(this), transmute(b))

public fun VectorSignedChar.vecSrb(b: VectorUnsignedChar): VectorSignedChar = vsrlb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSrb(b: VectorSignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSrb(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrb(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSrb(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrb(b: VectorSignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSrb(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrb(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSrb(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrb(b: VectorSignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorUnsignedLongLong.vecSrb(b: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrb(b: VectorSignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorSignedLongLong.vecSrb(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorFloat.vecSrb(b: VectorSignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSrb(b: VectorUnsignedInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecSrb(b: VectorSignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun VectorDouble.vecSrb(b: VectorUnsignedLongLong): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vsrlb(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorSrl<Other, out Result> {
    public fun vecSrl(b: Other): Result
}

public fun VectorUnsignedChar.vecSrl(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrl(b: VectorUnsignedChar): VectorSignedChar = vsrl(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSrl(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrl(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrl(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrl(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSrl(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSrl(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsrl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSral<Other, out Result> {
    public fun vecSral(b: Other): Result
}

public fun VectorUnsignedChar.vecSral(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSral(b: VectorUnsignedChar): VectorSignedChar = vsra(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSral(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSral(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSral(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSral(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSral(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSral(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsra(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorSll<Other, out Result> {
    public fun vecSll(b: Other): Result
}

public fun VectorUnsignedChar.vecSll(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSll(b: VectorUnsignedChar): VectorSignedChar = vsl(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSll(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSll(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSll(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSll(b: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecSll(b: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecSll(b: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public interface VectorRl<Other, T, out Result> {
    public fun vecRl(b: Other): Result
}

public fun verllvb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun verllvg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun VectorUnsignedChar.vecRl(b: VectorUnsignedChar): VectorUnsignedChar = verllvb(this, b)

public fun VectorSignedChar.vecRl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(verllvb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecRl(b: VectorUnsignedShort): VectorUnsignedShort = verllvh(this, b)

public fun VectorSignedShort.vecRl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(verllvh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecRl(b: VectorUnsignedInt): VectorUnsignedInt = verllvf(this, b)

public fun VectorSignedInt.vecRl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(verllvf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecRl(b: VectorUnsignedLongLong): VectorUnsignedLongLong = verllvg(this, b)

public fun VectorSignedLongLong.vecRl(b: VectorUnsignedLongLong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(verllvg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun verllvbImm(a: VectorUnsignedChar, bits: CUlong): VectorUnsignedChar {
    val bits1 = (bits % 8u.toULong()).toUByte()
    val b = U8x16.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmuteWithLayouts<Any?, VectorUnsignedChar>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }
}

public fun verllvhImm(a: VectorUnsignedShort, bits: CUlong): VectorUnsignedShort {
    val bits1 = (bits % 16u.toULong()).toUShort()
    val b = U16x8.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmuteWithLayouts<Any?, VectorUnsignedShort>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }
}

public fun verllvfImm(a: VectorUnsignedInt, bits: CUlong): VectorUnsignedInt {
    val bits1 = (bits % 32u.toULong()).toUInt()
    val b = U32x4.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmuteWithLayouts<Any?, VectorUnsignedInt>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }
}

public fun verllvgImm(a: VectorUnsignedLongLong, bits: CUlong): VectorUnsignedLongLong {
    val bits1 = (bits % 64u.toULong())
    val b = U64x2.splat(bits1)
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }
}

public interface VectorRli<T> {
    public fun vecRli(bits: CUlong): T
}

public fun VectorSignedChar.vecRli(bits: CUlong): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(verllvbImm(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), bits), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecRli(bits: CUlong): VectorUnsignedChar = verllvbImm(this, bits)

public fun VectorSignedShort.vecRli(bits: CUlong): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(verllvhImm(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), bits), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecRli(bits: CUlong): VectorUnsignedShort = verllvhImm(this, bits)

public fun VectorSignedInt.vecRli(bits: CUlong): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(verllvfImm(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), bits), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedInt.vecRli(bits: CUlong): VectorUnsignedInt = verllvfImm(this, bits)

public fun VectorSignedLongLong.vecRli(bits: CUlong): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(verllvgImm(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), bits), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorUnsignedLongLong.vecRli(bits: CUlong): VectorUnsignedLongLong = verllvgImm(this, bits)

public interface VectorRlMask<Other, T> {
    public fun vecRlMask(other: Other, imm8: UByte): T
}

public fun testVerimb(a: VectorSignedChar, b: VectorUnsignedChar, imm8: UByte): VectorSignedChar = verimb(a, a, transmute(b), (imm8 % 8u.toUByte()).toInt())

public fun VectorSignedChar.vecRlMask(other: VectorUnsignedChar, imm8: UByte): VectorSignedChar = testVerimb(this, other, imm8)

public fun VectorUnsignedChar.vecRlMask(other: VectorUnsignedChar, imm8: UByte): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(testVerimb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), other, imm8), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun testVerimh(a: VectorSignedShort, b: VectorUnsignedShort, imm8: UByte): VectorSignedShort = verimh(a, a, transmute(b), (imm8 % 16u.toUByte()).toInt())

public fun VectorSignedShort.vecRlMask(other: VectorUnsignedShort, imm8: UByte): VectorSignedShort = testVerimh(this, other, imm8)

public fun VectorUnsignedShort.vecRlMask(other: VectorUnsignedShort, imm8: UByte): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(testVerimh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), other, imm8), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun testVerimf(a: VectorSignedInt, b: VectorUnsignedInt, imm8: UByte): VectorSignedInt = verimf(a, a, transmute(b), (imm8 % 32u.toUByte()).toInt())

public fun VectorSignedInt.vecRlMask(other: VectorUnsignedInt, imm8: UByte): VectorSignedInt = testVerimf(this, other, imm8)

public fun VectorUnsignedInt.vecRlMask(other: VectorUnsignedInt, imm8: UByte): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(testVerimf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), other, imm8), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun testVerimg(a: VectorSignedLongLong, b: VectorUnsignedLongLong, imm8: UByte): VectorSignedLongLong = verimg(a, a, transmute(b), (imm8 % 64u.toUByte()).toInt())

public fun VectorSignedLongLong.vecRlMask(other: VectorUnsignedLongLong, imm8: UByte): VectorSignedLongLong = testVerimg(this, other, imm8)

public fun VectorUnsignedLongLong.vecRlMask(other: VectorUnsignedLongLong, imm8: UByte): VectorUnsignedLongLong = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(testVerimg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), other, imm8), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

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

    public val n: Int = 0

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

public fun VectorUnsignedChar.vecReve(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(reveb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecReve(): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(reveb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun reveh(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedShort() }

public fun VectorSignedShort.vecReve(): VectorSignedShort = reveh(this)

public fun VectorUnsignedShort.vecReve(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(reveh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecReve(): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(reveh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun revef(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedInt() }

public fun VectorSignedInt.vecReve(): VectorSignedInt = revef(this)

public fun VectorUnsignedInt.vecReve(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(revef(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecReve(): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(revef(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun reveg(a: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.reverse(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecReve(): VectorSignedLongLong = reveg(this)

public fun VectorUnsignedLongLong.vecReve(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(reveg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorBoolLongLong.vecReve(): VectorBoolLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorBoolLongLong>(reveg(transmuteWithLayouts<VectorBoolLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorFloat.vecReve(): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()).vecReve(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecReve(): VectorDouble = transmuteWithLayouts<Any?, VectorDouble>(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()).vecReve(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorRevb<T> {
    public fun vecRevb(): T
}

public fun bswapb(a: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; simdArg0 }

public fun bswaph(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; simdArg0 }

public fun bswapf(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; simdArg0 }

public fun bswapg(a: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; simdArg0 }

public fun VectorUnsignedChar.vecRevb(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(bswapb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecRevb(): VectorSignedChar = bswapb(this)

public fun VectorUnsignedShort.vecRevb(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(bswaph(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecRevb(): VectorSignedShort = bswaph(this)

public fun VectorUnsignedInt.vecRevb(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(bswapf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecRevb(): VectorSignedInt = bswapf(this)

public fun VectorUnsignedLongLong.vecRevb(): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(bswapg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorSignedLongLong.vecRevb(): VectorSignedLongLong = bswapg(this)

public fun VectorFloat.vecRevb(): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()).vecRevb(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecRevb(): VectorDouble = transmuteWithLayouts<Any?, VectorDouble>(transmuteWithLayouts<VectorDouble, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorSignedLongLong>()).vecRevb(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())

public interface VectorMergel<T, Result> {
    public fun vecMergel(other: T): T
}

public interface VectorMergeh<T, Result> {
    public fun vecMergeh(other: T): T
}

public fun vmrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedChar() }

public fun VectorSignedChar.vecMergel(other: VectorSignedChar): VectorSignedChar = vmrlb(this, other)

public fun VectorUnsignedChar.vecMergel(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vmrlb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vmrhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedChar() }

public fun VectorSignedChar.vecMergeh(other: VectorSignedChar): VectorSignedChar = vmrhb(this, other)

public fun VectorUnsignedChar.vecMergeh(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vmrhb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(other, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vmrlh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedShort() }

public fun VectorSignedShort.vecMergel(other: VectorSignedShort): VectorSignedShort = vmrlh(this, other)

public fun VectorUnsignedShort.vecMergel(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vmrlh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vmrhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedShort() }

public fun VectorSignedShort.vecMergeh(other: VectorSignedShort): VectorSignedShort = vmrhh(this, other)

public fun VectorUnsignedShort.vecMergeh(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vmrhh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(other, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vmrlf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedInt() }

public fun VectorSignedInt.vecMergel(other: VectorSignedInt): VectorSignedInt = vmrlf(this, other)

public fun VectorUnsignedInt.vecMergel(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vmrlf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vmrhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedInt() }

public fun VectorSignedInt.vecMergeh(other: VectorSignedInt): VectorSignedInt = vmrhf(this, other)

public fun VectorUnsignedInt.vecMergeh(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vmrhf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(other, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vmrlg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeLow(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecMergel(other: VectorSignedLongLong): VectorSignedLongLong = vmrlg(this, other)

public fun VectorUnsignedLongLong.vecMergel(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vmrlg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(other, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vmrhg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = ShuffleMask.mergeHigh(); VectorSignedLongLong() }

public fun VectorSignedLongLong.vecMergeh(other: VectorSignedLongLong): VectorSignedLongLong = vmrhg(this, other)

public fun VectorUnsignedLongLong.vecMergeh(other: VectorUnsignedLongLong): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(vmrhg(transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>()), transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(other, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())), UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public interface VectorPerm<T> {
    public fun vecPerm(other: T, c: VectorUnsignedChar): T
}

public fun VectorSignedChar.vecPerm(other: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar = vperm(transmute(this), transmute(other), c)

public fun VectorSignedShort.vecPerm(other: VectorSignedShort, c: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedInt.vecPerm(other: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedLongLong.vecPerm(other: VectorSignedLongLong, c: VectorUnsignedChar): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorUnsignedChar.vecPerm(other: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedShort.vecPerm(other: VectorUnsignedShort, c: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedInt.vecPerm(other: VectorUnsignedInt, c: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedLongLong.vecPerm(other: VectorUnsignedLongLong, c: VectorUnsignedChar): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorBoolChar.vecPerm(other: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolShort.vecPerm(other: VectorBoolShort, c: VectorUnsignedChar): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolInt.vecPerm(other: VectorBoolInt, c: VectorUnsignedChar): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolLongLong.vecPerm(other: VectorBoolLongLong, c: VectorUnsignedChar): VectorBoolLongLong = transmuteWithLayouts<VectorSignedChar, VectorBoolLongLong>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorFloat.vecPerm(other: VectorFloat, c: VectorUnsignedChar): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecPerm(other: VectorDouble, c: VectorUnsignedChar): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vperm(transmute(this), transmute(other), c), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

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

public fun VectorSignedChar.vecFindAnyEq(b: VectorSignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecFindAnyEq(b: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolChar.vecFindAnyEq(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecFindAnyEq(b: VectorSignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecFindAnyEq(b: VectorUnsignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecFindAnyEq(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecFindAnyEq(b: VectorSignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(this, b, FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecFindAnyEq(b: VectorUnsignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecFindAnyEq(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Eq.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorFindAnyNe<Other, out Result> {
    public fun vecFindAnyNe(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNe(b: VectorSignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecFindAnyNe(b: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolChar.vecFindAnyNe(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecFindAnyNe(b: VectorSignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecFindAnyNe(b: VectorUnsignedShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecFindAnyNe(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecFindAnyNe(b: VectorSignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(this, b, FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecFindAnyNe(b: VectorUnsignedInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecFindAnyNe(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Ne.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorFindAnyEqOrZeroIdx<Other, out Result> {
    public fun vecFindAnyEqOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqOr0Idx(b: VectorSignedChar): VectorSignedChar = vfaezb(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyEqOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyEqOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyEqOr0Idx(b: VectorSignedShort): VectorSignedShort = vfaezh(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyEqOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyEqOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyEqOr0Idx(b: VectorSignedInt): VectorSignedInt = vfaezf(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyEqOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyEqOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyNeOrZeroIdx<Other, out Result> {
    public fun vecFindAnyNeOr0Idx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeOr0Idx(b: VectorSignedChar): VectorSignedChar = vfaezb(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyNeOr0Idx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyNeOr0Idx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaezb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyNeOr0Idx(b: VectorSignedShort): VectorSignedShort = vfaezh(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyNeOr0Idx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyNeOr0Idx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaezh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyNeOr0Idx(b: VectorSignedInt): VectorSignedInt = vfaezf(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyNeOr0Idx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyNeOr0Idx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaezf(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyEqIdx<Other, out Result> {
    public fun vecFindAnyEqIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyEqIdx(b: VectorSignedChar): VectorSignedChar = vfaeb(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyEqIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyEqIdx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyEqIdx(b: VectorSignedShort): VectorSignedShort = vfaeh(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyEqIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyEqIdx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyEqIdx(b: VectorSignedInt): VectorSignedInt = vfaef(this, b, FindImm.EqIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyEqIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyEqIdx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public interface VectorFindAnyNeIdx<Other, out Result> {
    public fun vecFindAnyNeIdx(other: Other): Result
}

public fun VectorSignedChar.vecFindAnyNeIdx(b: VectorSignedChar): VectorSignedChar = vfaeb(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedChar.vecFindAnyNeIdx(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecFindAnyNeIdx(b: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vfaeb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecFindAnyNeIdx(b: VectorSignedShort): VectorSignedShort = vfaeh(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedShort.vecFindAnyNeIdx(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecFindAnyNeIdx(b: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vfaeh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecFindAnyNeIdx(b: VectorSignedInt): VectorSignedInt = vfaef(this, b, FindImm.NeIdx.value.toInt())

public fun VectorUnsignedInt.vecFindAnyNeIdx(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecFindAnyNeIdx(b: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vfaef(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt()), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

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
    val structVal = vfaebs(this, b, FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyEqCc(b: VectorUnsignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorBoolChar.vecFindAnyEqCc(b: VectorBoolChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorSignedShort.vecFindAnyEqCc(b: VectorSignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(this, b, FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyEqCc(b: VectorUnsignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorBoolShort.vecFindAnyEqCc(b: VectorBoolShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorSignedInt.vecFindAnyEqCc(b: VectorSignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(this, b, FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyEqCc(b: VectorUnsignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public fun VectorBoolInt.vecFindAnyEqCc(b: VectorBoolInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Eq.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public interface VectorFindAnyNeCC<Other, out Result> {
    public fun vecFindAnyNeCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeCc(b: VectorSignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(this, b, FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyNeCc(b: VectorUnsignedChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorBoolChar.vecFindAnyNeCc(b: VectorBoolChar): Pair<VectorBoolChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorSignedShort.vecFindAnyNeCc(b: VectorSignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(this, b, FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyNeCc(b: VectorUnsignedShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorBoolShort.vecFindAnyNeCc(b: VectorBoolShort): Pair<VectorBoolShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorSignedInt.vecFindAnyNeCc(b: VectorSignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(this, b, FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyNeCc(b: VectorUnsignedInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public fun VectorBoolInt.vecFindAnyNeCc(b: VectorBoolInt): Pair<VectorBoolInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.Ne.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public interface VectorFindAnyEqIdxCC<Other, out Result> {
    public fun vecFindAnyEqIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyEqIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecFindAnyEqIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecFindAnyEqIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyEqIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecFindAnyEqIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecFindAnyEqIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyEqIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecFindAnyEqIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public interface VectorFindAnyNeIdxCC<Other, out Result> {
    public fun vecFindAnyNeIdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeIdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaebs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyNeIdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecFindAnyNeIdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaebs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecFindAnyNeIdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaehs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyNeIdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecFindAnyNeIdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaehs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecFindAnyNeIdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaefs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyNeIdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecFindAnyNeIdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaefs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public interface VectorFindAnyEqOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyEqOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyEqOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyEqOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecFindAnyEqOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecFindAnyEqOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyEqOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecFindAnyEqOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecFindAnyEqOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(this, b, FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyEqOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecFindAnyEqOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.EqIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public interface VectorFindAnyNeOrZeroIdxCC<Other, out Result> {
    public fun vecFindAnyNeOr0IdxCc(other: Other): Pair<Result, Int>
}

public fun VectorSignedChar.vecFindAnyNeOr0IdxCc(b: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfaezbs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorUnsignedChar.vecFindAnyNeOr0IdxCc(b: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecFindAnyNeOr0IdxCc(b: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfaezbs(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecFindAnyNeOr0IdxCc(b: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfaezhs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorUnsignedShort.vecFindAnyNeOr0IdxCc(b: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecFindAnyNeOr0IdxCc(b: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfaezhs(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecFindAnyNeOr0IdxCc(b: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfaezfs(this, b, FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorUnsignedInt.vecFindAnyNeOr0IdxCc(b: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecFindAnyNeOr0IdxCc(b: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfaezfs(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), FindImm.NeIdx.value.toInt())
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
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
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedChar(ptr: ConstPtr, byteCount: UInt): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun vectorLoadVecLoadBndryVectorUnsignedChar(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedChar> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedChar>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedChar>>())

public fun VectorUnsignedChar.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedShort(ptr: ConstPtr, byteCount: UInt): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorLoadVecLoadBndryVectorSignedShort(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedShort> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedShort>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedShort>>())

public fun VectorSignedShort.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedShort(ptr: ConstPtr, byteCount: UInt): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun vectorLoadVecLoadBndryVectorUnsignedShort(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedShort> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedShort>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedShort>>())

public fun VectorUnsignedShort.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedInt(ptr: ConstPtr, byteCount: UInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorLoadVecLoadBndryVectorSignedInt(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedInt> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedInt>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedInt>>())

public fun VectorSignedInt.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedInt(ptr: ConstPtr, byteCount: UInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vectorLoadVecLoadBndryVectorUnsignedInt(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedInt> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedInt>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedInt>>())

public fun VectorUnsignedInt.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorSignedLongLong(ptr: ConstPtr, byteCount: UInt): VectorSignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorSignedLongLong>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorLoadVecLoadBndryVectorSignedLongLong(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorSignedLongLong> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorSignedLongLong>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedLongLong>>())

public fun VectorSignedLongLong.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorUnsignedLongLong(ptr: ConstPtr, byteCount: UInt): VectorUnsignedLongLong = transmuteWithLayouts<VectorSignedChar, VectorUnsignedLongLong>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun vectorLoadVecLoadBndryVectorUnsignedLongLong(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorUnsignedLongLong> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorUnsignedLongLong>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedLongLong>>())

public fun VectorUnsignedLongLong.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorFloat(ptr: ConstPtr, byteCount: UInt): VectorFloat = transmuteWithLayouts<VectorSignedChar, VectorFloat>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorLoadVecLoadBndryVectorFloat(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorFloat> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorFloat>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorFloat>>())

public fun VectorFloat.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun vectorLoadVecLoadLenVectorDouble(ptr: ConstPtr, byteCount: UInt): VectorDouble = transmuteWithLayouts<VectorSignedChar, VectorDouble>(vll(byteCount, ptr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorLoadVecLoadBndryVectorDouble(ptr: ConstPtr, blockBoundary: UShort): MaybeUninit<VectorDouble> = transmuteWithLayouts<VectorSignedChar, MaybeUninit<VectorDouble>>(vlbb(ptr, validateBlockBoundary(blockBoundary)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorDouble>>())

public fun VectorDouble.vecStoreLen(ptr: MutPtr, byteCount: UInt) {
    vstl(transmute(this), byteCount, ptr)
}

public fun testVecLoadLen(ptr: ConstPtr, byteCount: UInt): VectorSignedInt = VectorSignedInt.vecLoadLen(ptr, byteCount)

public fun testVecLoadBndry(ptr: ConstPtr): MaybeUninit<VectorSignedInt> = VectorSignedInt.vecLoadBndry(ptr, 512)

public fun testVecStoreLen(vector: VectorSignedInt, ptr: MutPtr, byteCount: UInt) {
    vector.vecStoreLen(ptr, byteCount)
}

public interface VectorLoadPair<T, ElementType> {
    public fun vecLoadPair(a: ElementType, b: ElementType): T
}

public fun vectorLoadPairVecLoadPairVectorSignedLongLong(a: Long, b: Long): VectorSignedLongLong = VectorSignedLongLong(longArrayOf(a, b))

public fun vectorLoadPairVecLoadPairVectorUnsignedLongLong(a: ULong, b: ULong): VectorUnsignedLongLong = VectorUnsignedLongLong(ulongArrayOf(a, b))

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

public fun VectorSignedShort.vecPack(b: VectorSignedShort): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vpkh(transmuteWithLayouts<VectorSignedShort, I16x8>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<I16x8>()), transmuteWithLayouts<VectorSignedShort, I16x8>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<I16x8>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecPack(b: VectorUnsignedShort): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vpkh(transmuteWithLayouts<VectorUnsignedShort, I16x8>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<I16x8>()), transmuteWithLayouts<VectorUnsignedShort, I16x8>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<I16x8>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolShort.vecPack(b: VectorBoolShort): VectorBoolChar = transmuteWithLayouts<I8x16, VectorBoolChar>(vpkh(transmuteWithLayouts<VectorBoolShort, I16x8>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<I16x8>()), transmuteWithLayouts<VectorBoolShort, I16x8>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<I16x8>())), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedInt.vecPack(b: VectorSignedInt): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vpkf(transmuteWithLayouts<VectorSignedInt, I32x4>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<I32x4>()), transmuteWithLayouts<VectorSignedInt, I32x4>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<I32x4>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecPack(b: VectorUnsignedInt): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vpkf(transmuteWithLayouts<VectorUnsignedInt, I32x4>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<I32x4>()), transmuteWithLayouts<VectorUnsignedInt, I32x4>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<I32x4>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolInt.vecPack(b: VectorBoolInt): VectorBoolShort = transmuteWithLayouts<I16x8, VectorBoolShort>(vpkf(transmuteWithLayouts<VectorBoolInt, I32x4>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<I32x4>()), transmuteWithLayouts<VectorBoolInt, I32x4>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<I32x4>())), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedLongLong.vecPack(b: VectorSignedLongLong): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vpkg(transmuteWithLayouts<VectorSignedLongLong, I64x2>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<I64x2>()), transmuteWithLayouts<VectorSignedLongLong, I64x2>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<I64x2>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedLongLong.vecPack(b: VectorUnsignedLongLong): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vpkg(transmuteWithLayouts<VectorUnsignedLongLong, I64x2>(this, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<I64x2>()), transmuteWithLayouts<VectorUnsignedLongLong, I64x2>(b, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<I64x2>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolLongLong.vecPack(b: VectorBoolLongLong): VectorBoolInt = transmuteWithLayouts<I32x4, VectorBoolInt>(vpkg(transmuteWithLayouts<VectorBoolLongLong, I64x2>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<I64x2>()), transmuteWithLayouts<VectorBoolLongLong, I64x2>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<I64x2>())), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorPacks<Other, out Result> {
    public fun vecPacks(b: Other): Result
}

public fun VectorSignedShort.vecPacks(b: VectorSignedShort): VectorSignedChar = vpksh(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecPacks(b: VectorUnsignedShort): VectorUnsignedChar = vpklsh(transmute(this), transmute(b))

public fun VectorSignedInt.vecPacks(b: VectorSignedInt): VectorSignedShort = vpksf(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecPacks(b: VectorUnsignedInt): VectorUnsignedShort = vpklsf(transmute(this), transmute(b))

public fun VectorSignedLongLong.vecPacks(b: VectorSignedLongLong): VectorSignedInt = vpksg(transmute(this), transmute(b))

public fun VectorUnsignedLongLong.vecPacks(b: VectorUnsignedLongLong): VectorUnsignedInt = vpklsg(transmute(this), transmute(b))

public interface VectorPacksu<Other, out Result> {
    public fun vecPacksu(b: Other): Result
}

public fun vpacksuh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar = vpklsh(vecMax(a, VectorSignedShort(List(8) { 0 })), vecMax(b, VectorSignedShort(List(8) { 0 })))

public fun vpacksuf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort = vpklsf(vecMax(a, VectorSignedInt(List(4) { 0 })), vecMax(b, VectorSignedInt(List(4) { 0 })))

public fun vpacksug(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt = vpklsg(vecMax(a, VectorSignedLongLong(List(2) { 0 })), vecMax(b, VectorSignedLongLong(List(2) { 0 })))

public fun VectorSignedShort.vecPacksu(b: VectorSignedShort): VectorUnsignedChar = vpacksuh(this, b)

public fun VectorUnsignedShort.vecPacksu(b: VectorUnsignedShort): VectorUnsignedChar = vpklsh(transmute(this), transmute(b))

public fun VectorSignedInt.vecPacksu(b: VectorSignedInt): VectorUnsignedShort = vpacksuf(this, b)

public fun VectorUnsignedInt.vecPacksu(b: VectorUnsignedInt): VectorUnsignedShort = vpklsf(transmute(this), transmute(b))

public fun VectorSignedLongLong.vecPacksu(b: VectorSignedLongLong): VectorUnsignedInt = vpacksug(this, b)

public fun VectorUnsignedLongLong.vecPacksu(b: VectorUnsignedLongLong): VectorUnsignedInt = vpklsg(transmute(this), transmute(b))

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

public fun VectorSignedChar.vecUnpackh(): VectorSignedShort = vuphb(this)

public fun VectorSignedShort.vecUnpackh(): VectorSignedInt = vuphh(this)

public fun VectorSignedInt.vecUnpackh(): VectorSignedLongLong = vuphf(this)

public fun VectorUnsignedChar.vecUnpackh(): VectorUnsignedShort = vuplhb(this)

public fun VectorUnsignedShort.vecUnpackh(): VectorUnsignedInt = vuplhh(this)

public fun VectorUnsignedInt.vecUnpackh(): VectorUnsignedLongLong = vuplhf(this)

public fun VectorBoolChar.vecUnpackh(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vuplhb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecUnpackh(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vuplhh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecUnpackh(): VectorBoolLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(vuplhf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public interface VectorUnpackl<out Result> {
    public fun vecUnpackl(): Result
}

public fun vuplb(a: VectorSignedChar): VectorSignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; VectorSignedShort() }

public fun vuplhw(a: VectorSignedShort): VectorSignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; VectorSignedInt() }

public fun vuplf(a: VectorSignedInt): VectorSignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; VectorSignedLongLong() }

public fun vupllb(a: VectorUnsignedChar): VectorUnsignedShort = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; VectorUnsignedShort() }

public fun vupllh(a: VectorUnsignedShort): VectorUnsignedInt = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; VectorUnsignedInt() }

public fun vupllf(a: VectorUnsignedInt): VectorUnsignedLongLong = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = ShuffleMask.unpackLow(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; VectorUnsignedLongLong() }

public fun VectorSignedChar.vecUnpackl(): VectorSignedShort = vuplb(this)

public fun VectorSignedShort.vecUnpackl(): VectorSignedInt = vuplhw(this)

public fun VectorSignedInt.vecUnpackl(): VectorSignedLongLong = vuplf(this)

public fun VectorUnsignedChar.vecUnpackl(): VectorUnsignedShort = vupllb(this)

public fun VectorUnsignedShort.vecUnpackl(): VectorUnsignedInt = vupllh(this)

public fun VectorUnsignedInt.vecUnpackl(): VectorUnsignedLongLong = vupllf(this)

public fun VectorBoolChar.vecUnpackl(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vupllb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecUnpackl(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vupllh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecUnpackl(): VectorBoolLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(vupllf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())

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

public fun VectorSignedChar.vecMule(b: VectorSignedChar): VectorSignedShort = vmeb(this, b)

public fun VectorSignedShort.vecMule(b: VectorSignedShort): VectorSignedInt = vmeh(this, b)

public fun VectorSignedInt.vecMule(b: VectorSignedInt): VectorSignedLongLong = vmef(this, b)

public fun VectorUnsignedChar.vecMule(b: VectorUnsignedChar): VectorUnsignedShort = vmleb(this, b)

public fun VectorUnsignedShort.vecMule(b: VectorUnsignedShort): VectorUnsignedInt = vmleh(this, b)

public fun VectorUnsignedInt.vecMule(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlef(this, b)

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

public fun VectorSignedChar.vecMulo(b: VectorSignedChar): VectorSignedShort = vmob(this, b)

public fun VectorSignedShort.vecMulo(b: VectorSignedShort): VectorSignedInt = vmoh(this, b)

public fun VectorSignedInt.vecMulo(b: VectorSignedInt): VectorSignedLongLong = vmof(this, b)

public fun VectorUnsignedChar.vecMulo(b: VectorUnsignedChar): VectorUnsignedShort = vmlob(this, b)

public fun VectorUnsignedShort.vecMulo(b: VectorUnsignedShort): VectorUnsignedInt = vmloh(this, b)

public fun VectorUnsignedInt.vecMulo(b: VectorUnsignedInt): VectorUnsignedLongLong = vmlof(this, b)

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

public fun VectorUnsignedChar.vecGfmsum(b: VectorUnsignedChar): VectorUnsignedShort = vecVgfmb(this, b)

public fun VectorUnsignedShort.vecGfmsum(b: VectorUnsignedShort): VectorUnsignedInt = vecVgfmh(this, b)

public fun VectorUnsignedInt.vecGfmsum(b: VectorUnsignedInt): VectorUnsignedLongLong = vecVgfmf(this, b)

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

public fun VectorSignedInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(vgef(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorBoolInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(vgef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(vgef(this, b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorSignedLongLong = transmuteWithLayouts<Any?, VectorSignedLongLong>(vgeg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun VectorBoolLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorBoolLongLong = transmuteWithLayouts<Any?, VectorBoolLongLong>(vgeg(transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())

public fun VectorUnsignedLongLong.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorUnsignedLongLong = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(vgeg(this, b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())

public fun VectorFloat.vecGatherElement(b: VectorUnsignedInt, c: ConstPtr, d: UInt): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(vgef(transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorDouble.vecGatherElement(b: VectorUnsignedLongLong, c: ConstPtr, d: UInt): VectorDouble = transmuteWithLayouts<Any?, VectorDouble>(vgeg(transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())

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
    vscef(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d)
}

public fun VectorBoolInt.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d)
}

public fun VectorUnsignedInt.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(this, b, c, d)
}

public fun VectorSignedLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d)
}

public fun VectorBoolLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d)
}

public fun VectorUnsignedLongLong.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(this, b, c, d)
}

public fun VectorFloat.vecScatterElement(b: VectorUnsignedInt, c: MutPtr, d: UInt) {
    vscef(transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b, c, d)
}

public fun VectorDouble.vecScatterElement(b: VectorUnsignedLongLong, c: MutPtr, d: UInt) {
    vsceg(transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b, c, d)
}

public interface VectorSel<Mask, T> {
    public fun vecSel(b: T, c: Mask): T
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedChar>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun VectorSignedChar.vecSelVectorSel(b: VectorSignedChar, c: VectorBoolChar): VectorSignedChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: VectorUnsignedShort): VectorSignedShort {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedShort>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorSignedShort.vecSelVectorSel(b: VectorSignedShort, c: VectorBoolShort): VectorSignedShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: VectorUnsignedInt): VectorSignedInt {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedInt>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorSignedInt.vecSelVectorSel(b: VectorSignedInt, c: VectorBoolInt): VectorSignedInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorSignedLongLong.vecSel(b: VectorSignedLongLong, c: VectorUnsignedLongLong): VectorSignedLongLong {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorSignedLongLong.vecSelVectorSel(b: VectorSignedLongLong, c: VectorBoolLongLong): VectorSignedLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorUnsignedChar.vecSel(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedChar>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }; simdArg0 }
    return run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }
}

public fun VectorUnsignedChar.vecSelVectorSel(b: VectorUnsignedChar, c: VectorBoolChar): VectorUnsignedChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecSel(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedShort>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }; simdArg0 }
    return run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }
}

public fun VectorUnsignedShort.vecSelVectorSel(b: VectorUnsignedShort, c: VectorBoolShort): VectorUnsignedShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecSel(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedInt>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }; simdArg0 }
    return run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }
}

public fun VectorUnsignedInt.vecSelVectorSel(b: VectorUnsignedInt, c: VectorBoolInt): VectorUnsignedInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorUnsignedLongLong.vecSel(b: VectorUnsignedLongLong, c: VectorUnsignedLongLong): VectorUnsignedLongLong {
    val b1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = this; val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }; simdArg0 }
    return run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }
}

public fun VectorUnsignedLongLong.vecSelVectorSel(b: VectorUnsignedLongLong, c: VectorBoolLongLong): VectorUnsignedLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorBoolChar.vecSel(b: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedChar>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorBoolChar.vecSelVectorSel(b: VectorBoolChar, c: VectorBoolChar): VectorBoolChar = this.vecSel(b, transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorBoolShort.vecSel(b: VectorBoolShort, c: VectorUnsignedShort): VectorBoolShort {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedShort>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorBoolShort.vecSelVectorSel(b: VectorBoolShort, c: VectorBoolShort): VectorBoolShort = this.vecSel(b, transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorBoolInt.vecSel(b: VectorBoolInt, c: VectorUnsignedInt): VectorBoolInt {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedInt>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorBoolInt.vecSelVectorSel(b: VectorBoolInt, c: VectorBoolInt): VectorBoolInt = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorBoolLongLong.vecSel(b: VectorBoolLongLong, c: VectorUnsignedLongLong): VectorBoolLongLong {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedLongLong, VectorBoolLongLong>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorBoolLongLong>())
}

public fun VectorBoolLongLong.vecSelVectorSel(b: VectorBoolLongLong, c: VectorBoolLongLong): VectorBoolLongLong = this.vecSel(b, transmuteWithLayouts<VectorBoolLongLong, VectorUnsignedLongLong>(c, UnresolvedTypeLayout<VectorBoolLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()))

public fun VectorFloat.vecSel(b: VectorFloat, c: VectorUnsignedInt): VectorFloat {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedInt>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecSelVectorSel(b: VectorFloat, c: VectorBoolInt): VectorFloat = this.vecSel(b, transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun VectorDouble.vecSel(b: VectorDouble, c: VectorUnsignedLongLong): VectorDouble {
    val b1 = run { val simdArg0 = transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = c; simdArg0 }
    val a = run { val simdArg0 = transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(this, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); val simdArg1 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, VectorUnsignedLongLong>(VectorSignedChar(List(16) { 0.inv() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()); simdArg0 }; simdArg0 }
    return transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(run { val simdArg0 = a; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())
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

public fun VectorSignedShort.vecTestMask(other: VectorUnsignedShort): Int = vtm(transmute(this), transmute(other))

public fun VectorSignedLongLong.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmute(this), transmute(other))

public fun VectorUnsignedShort.vecTestMask(other: VectorUnsignedShort): Int = vtm(transmute(this), transmute(other))

public fun VectorUnsignedLongLong.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmute(this), transmute(other))

public fun VectorDouble.vecTestMask(other: VectorUnsignedLongLong): Int = vtm(transmute(this), transmute(other))

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

public fun VectorFloat.vecSigned(): VectorSignedInt = vcgsb(this)

public fun VectorDouble.vecSigned(): VectorSignedLongLong = vcgdb(this)

public interface VectorUnsigned<out Result> {
    public fun vecUnsigned(): Result
}

public fun vclgsb(a: VectorFloat): VectorUnsignedInt = run { val simdArg0 = a; VectorUnsignedInt() }

public fun vclgdb(a: VectorDouble): VectorUnsignedLongLong = run { val simdArg0 = a; VectorUnsignedLongLong() }

public fun VectorFloat.vecUnsigned(): VectorUnsignedInt = vclgsb(this)

public fun VectorDouble.vecUnsigned(): VectorUnsignedLongLong = vclgdb(this)

public interface VectorCopyUntilZero<T> {
    public fun vecCpUntilZero(): T
}

public fun vecVistrb(a: VectorUnsignedChar): VectorUnsignedChar = vistrb(a)

public fun vecVistrh(a: VectorUnsignedShort): VectorUnsignedShort = vistrh(a)

public fun vecVistrf(a: VectorUnsignedInt): VectorUnsignedInt = vistrf(a)

public fun VectorSignedChar.vecCpUntilZero(): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vecVistrb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorBoolChar.vecCpUntilZero(): VectorBoolChar = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(vecVistrb(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecCpUntilZero(): VectorUnsignedChar = vecVistrb(this)

public fun VectorSignedShort.vecCpUntilZero(): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vecVistrh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolShort.vecCpUntilZero(): VectorBoolShort = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(vecVistrh(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecCpUntilZero(): VectorUnsignedShort = vecVistrh(this)

public fun VectorSignedInt.vecCpUntilZero(): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vecVistrf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorBoolInt.vecCpUntilZero(): VectorBoolInt = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(vecVistrf(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecCpUntilZero(): VectorUnsignedInt = vecVistrf(this)

public interface VectorCopyUntilZeroCC<T> {
    public fun vecCpUntilZeroCc(): Pair<T, Int>
}

public fun vecVistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> = vistrbs(a)

public fun vecVistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int> = vistrhs(a)

public fun vecVistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int> = vistrfs(a)

public fun VectorSignedChar.vecCpUntilZeroCc(): Pair<VectorSignedChar, Int> {
    val structVal = vecVistrbs(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorBoolChar.vecCpUntilZeroCc(): Pair<VectorBoolChar, Int> {
    val structVal = vecVistrbs(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>()), y)
}

public fun VectorUnsignedChar.vecCpUntilZeroCc(): Pair<VectorUnsignedChar, Int> {
    val structVal = vecVistrbs(this)
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecCpUntilZeroCc(): Pair<VectorSignedShort, Int> {
    val structVal = vecVistrhs(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorBoolShort.vecCpUntilZeroCc(): Pair<VectorBoolShort, Int> {
    val structVal = vecVistrhs(transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>()), y)
}

public fun VectorUnsignedShort.vecCpUntilZeroCc(): Pair<VectorUnsignedShort, Int> {
    val structVal = vecVistrhs(this)
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecCpUntilZeroCc(): Pair<VectorSignedInt, Int> {
    val structVal = vecVistrfs(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorBoolInt.vecCpUntilZeroCc(): Pair<VectorBoolInt, Int> {
    val structVal = vecVistrfs(transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorBoolInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>()), y)
}

public fun VectorUnsignedInt.vecCpUntilZeroCc(): Pair<VectorUnsignedInt, Int> {
    val structVal = vecVistrfs(this)
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public interface VectorSrdb<T> {
    public fun vecSrdb(b: T, c: UInt): T
}

public interface VectorSld<T> {
    public fun vecSld(b: T, c: UInt): T

    public fun vecSldw(b: T, c: UInt): T

    public fun vecSldb(b: T, c: UInt): T
}

public fun testVecSld(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSld(b, 13)

public fun testVecSldw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSldw(b, 3)

public fun testVecSldb(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSldb(b, 7)

public fun testVecSrdb(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = a.vecSrdb(b, 7)

public fun VectorSignedChar.vecSld(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedChar>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun VectorSignedChar.vecSldw(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedChar>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun VectorSignedChar.vecSldb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedChar>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun VectorSignedChar.vecSrdb(b: VectorSignedChar, c: UInt): VectorSignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedChar>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun VectorBoolChar.vecSld(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolChar>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorBoolChar.vecSldw(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolChar>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorBoolChar.vecSldb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolChar>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorBoolChar.vecSrdb(b: VectorBoolChar, c: UInt): VectorBoolChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolChar>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorUnsignedChar.vecSld(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorUnsignedChar.vecSldw(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorUnsignedChar.vecSldb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorUnsignedChar.vecSrdb(b: VectorUnsignedChar, c: UInt): VectorUnsignedChar {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorSignedShort.vecSld(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedShort>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorSignedShort.vecSldw(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedShort>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorSignedShort.vecSldb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedShort>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorSignedShort.vecSrdb(b: VectorSignedShort, c: UInt): VectorSignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedShort>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorBoolShort.vecSld(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolShort>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorBoolShort.vecSldw(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolShort>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorBoolShort.vecSldb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolShort>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorBoolShort.vecSrdb(b: VectorBoolShort, c: UInt): VectorBoolShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolShort>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorUnsignedShort.vecSld(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorUnsignedShort.vecSldw(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorUnsignedShort.vecSldb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorUnsignedShort.vecSrdb(b: VectorUnsignedShort, c: UInt): VectorUnsignedShort {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorSignedInt.vecSld(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedInt>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorSignedInt.vecSldw(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedInt>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorSignedInt.vecSldb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedInt>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorSignedInt.vecSrdb(b: VectorSignedInt, c: UInt): VectorSignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedInt>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorBoolInt.vecSld(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolInt>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorBoolInt.vecSldw(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolInt>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorBoolInt.vecSldb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolInt>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorBoolInt.vecSrdb(b: VectorBoolInt, c: UInt): VectorBoolInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolInt>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorUnsignedInt.vecSld(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedInt.vecSldw(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedInt.vecSldb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedInt.vecSrdb(b: VectorUnsignedInt, c: UInt): VectorUnsignedInt {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorSignedLongLong.vecSld(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorSignedLongLong.vecSldw(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorSignedLongLong.vecSldb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorSignedLongLong.vecSrdb(b: VectorSignedLongLong, c: UInt): VectorSignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedLongLong>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun VectorBoolLongLong.vecSld(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())
}

public fun VectorBoolLongLong.vecSldw(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())
}

public fun VectorBoolLongLong.vecSldb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolLongLong>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())
}

public fun VectorBoolLongLong.vecSrdb(b: VectorBoolLongLong, c: UInt): VectorBoolLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorBoolLongLong>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolLongLong>())
}

public fun VectorUnsignedLongLong.vecSld(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorUnsignedLongLong.vecSldw(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorUnsignedLongLong.vecSldb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorUnsignedLongLong.vecSrdb(b: VectorUnsignedLongLong, c: UInt): VectorUnsignedLongLong {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedLongLong>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun VectorFloat.vecSld(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorFloat>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecSldw(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorFloat>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecSldb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorFloat>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecSrdb(b: VectorFloat, c: UInt): VectorFloat {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorFloat>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorDouble.vecSld(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorDouble>(U128.funnelShl(transmute(this), transmute(b), c * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())
}

public fun VectorDouble.vecSldw(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorDouble>(U128.funnelShl(transmute(this), transmute(b), c * 4u * 8u), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())
}

public fun VectorDouble.vecSldb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorDouble>(U128.funnelShl(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())
}

public fun VectorDouble.vecSrdb(b: VectorDouble, c: UInt): VectorDouble {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Any?, VectorDouble>(U128.funnelShr(transmute(this), transmute(b), c), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorDouble>())
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

public fun VectorSignedChar.vecCmpeqIdx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeeb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpneIdx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeneb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpeqOr0Idx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfeezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpneOr0Idx(other: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<I8x16, VectorSignedChar>(vfenezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecCmpeqIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorSignedChar.vecCmpneIdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorSignedChar.vecCmpeqOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorSignedChar.vecCmpneOr0IdxCc(other: VectorSignedChar): Pair<VectorSignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>()), y)
}

public fun VectorBoolChar.vecCmpeqIdx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeeb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpneIdx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeneb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpeqOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpneOr0Idx(other: VectorBoolChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfenezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecCmpeqIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecCmpneIdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecCmpeqOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorBoolChar.vecCmpneOr0IdxCc(other: VectorBoolChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorUnsignedChar.vecCmpeqIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeeb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpneIdx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeneb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpeqOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfeezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpneOr0Idx(other: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<I8x16, VectorUnsignedChar>(vfenezb(transmute(this), transmute(other)), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecCmpeqIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorUnsignedChar.vecCmpneIdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenebs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorUnsignedChar.vecCmpeqOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfeezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorUnsignedChar.vecCmpneOr0IdxCc(other: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> {
    val structVal = vfenezbs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedChar>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), y)
}

public fun VectorSignedShort.vecCmpeqIdx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeeh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpneIdx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeneh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpeqOr0Idx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfeezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpneOr0Idx(other: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<I16x8, VectorSignedShort>(vfenezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecCmpeqIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorSignedShort.vecCmpneIdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorSignedShort.vecCmpeqOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorSignedShort.vecCmpneOr0IdxCc(other: VectorSignedShort): Pair<VectorSignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>()), y)
}

public fun VectorBoolShort.vecCmpeqIdx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeeh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpneIdx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeneh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpeqOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpneOr0Idx(other: VectorBoolShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfenezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecCmpeqIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecCmpneIdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecCmpeqOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorBoolShort.vecCmpneOr0IdxCc(other: VectorBoolShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorUnsignedShort.vecCmpeqIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeeh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpneIdx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeneh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpeqOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfeezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpneOr0Idx(other: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<I16x8, VectorUnsignedShort>(vfenezh(transmute(this), transmute(other)), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecCmpeqIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorUnsignedShort.vecCmpneIdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenehs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorUnsignedShort.vecCmpeqOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfeezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorUnsignedShort.vecCmpneOr0IdxCc(other: VectorUnsignedShort): Pair<VectorUnsignedShort, Int> {
    val structVal = vfenezhs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedShort>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), y)
}

public fun VectorSignedInt.vecCmpeqIdx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfeef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpneIdx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfenef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpeqOr0Idx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfeezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpneOr0Idx(other: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(vfenezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecCmpeqIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorSignedInt.vecCmpneIdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorSignedInt.vecCmpeqOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorSignedInt.vecCmpneOr0IdxCc(other: VectorSignedInt): Pair<VectorSignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorSignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>()), y)
}

public fun VectorBoolInt.vecCmpeqIdx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpneIdx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpeqOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpneOr0Idx(other: VectorBoolInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecCmpeqIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecCmpneIdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecCmpeqOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorBoolInt.vecCmpneOr0IdxCc(other: VectorBoolInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorUnsignedInt.vecCmpeqIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpneIdx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenef(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpeqOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfeezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpneOr0Idx(other: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<I32x4, VectorUnsignedInt>(vfenezf(transmute(this), transmute(other)), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecCmpeqIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorUnsignedInt.vecCmpneIdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenefs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorUnsignedInt.vecCmpeqOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfeezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
}

public fun VectorUnsignedInt.vecCmpneOr0IdxCc(other: VectorUnsignedInt): Pair<VectorUnsignedInt, Int> {
    val structVal = vfenezfs(transmute(this), transmute(other))
    val x = structVal.x
    val y = structVal.y
    return Pair(transmuteWithLayouts<Any?, VectorUnsignedInt>(x, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), y)
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

public fun vllezb(x: ConstPtr): VectorUnsignedChar = VectorUnsignedChar(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, (PrimitiveLayouts.U8).readNative((x).rawAddress), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u))

public fun vllezh(x: ConstPtr): VectorUnsignedShort = VectorUnsignedShort(ushortArrayOf(0u, 0u, 0u, (PrimitiveLayouts.U16).readNative((x).rawAddress), 0u, 0u, 0u, 0u))

public fun vllezf(x: ConstPtr): VectorUnsignedInt = VectorUnsignedInt(uintArrayOf(0u, (PrimitiveLayouts.U32).readNative((x).rawAddress), 0u, 0u))

public fun vllezg(x: ConstPtr): VectorUnsignedLongLong = VectorUnsignedLongLong(ulongArrayOf((PrimitiveLayouts.U64).readNative((x).rawAddress), 0uL))

public fun vectorExtractVecExtractVectorSignedChar(a: VectorSignedChar, b: Int): Byte = transmuteWithLayouts<UByte, Byte>(vlgvb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<UByte>(), UnresolvedTypeLayout<Byte>())

public fun vectorInsertVecInsertVectorSignedChar(a: Byte, b: VectorSignedChar, c: Int): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vlvgb(transmute(a), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedChar(a: ConstPtr): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vllezb(a), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun vectorPromoteVecPromoteVectorSignedChar(a: Byte, c: Int): MaybeUninit<VectorSignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedChar>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedChar, MaybeUninit<VectorSignedChar>>(vlvgb(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedChar>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedChar>>()), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vectorExtractVecExtractVectorUnsignedChar(a: VectorUnsignedChar, b: Int): UByte = vlgvb(a, b)

public fun vectorInsertVecInsertVectorUnsignedChar(a: UByte, b: VectorUnsignedChar, c: Int): VectorUnsignedChar = vlvgb(transmute(a), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedChar(a: ConstPtr): VectorUnsignedChar = vllezb(a)

public fun vectorPromoteVecPromoteVectorUnsignedChar(a: UByte, c: Int): MaybeUninit<VectorUnsignedChar> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedChar>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedChar, MaybeUninit<VectorUnsignedChar>>(vlvgb(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedChar>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>()), c), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedChar>>()), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vectorExtractVecExtractVectorSignedShort(a: VectorSignedShort, b: Int): Short = transmuteWithLayouts<UShort, Short>(vlgvh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<UShort>(), UnresolvedTypeLayout<Short>())

public fun vectorInsertVecInsertVectorSignedShort(a: Short, b: VectorSignedShort, c: Int): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vlvgh(transmute(a), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedShort(a: ConstPtr): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vllezh(a), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun vectorPromoteVecPromoteVectorSignedShort(a: Short, c: Int): MaybeUninit<VectorSignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedShort>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedShort, MaybeUninit<VectorSignedShort>>(vlvgh(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedShort>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedShort>>()), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun vectorExtractVecExtractVectorUnsignedShort(a: VectorUnsignedShort, b: Int): UShort = vlgvh(a, b)

public fun vectorInsertVecInsertVectorUnsignedShort(a: UShort, b: VectorUnsignedShort, c: Int): VectorUnsignedShort = vlvgh(transmute(a), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedShort(a: ConstPtr): VectorUnsignedShort = vllezh(a)

public fun vectorPromoteVecPromoteVectorUnsignedShort(a: UShort, c: Int): MaybeUninit<VectorUnsignedShort> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedShort>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedShort, MaybeUninit<VectorUnsignedShort>>(vlvgh(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedShort>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>()), c), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedShort>>()), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vectorExtractVecExtractVectorSignedInt(a: VectorSignedInt, b: Int): Int = transmuteWithLayouts<UInt, Int>(vlgvf(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Int>())

public fun vectorInsertVecInsertVectorSignedInt(a: Int, b: VectorSignedInt, c: Int): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vlvgf(transmute(a), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedInt(a: ConstPtr): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vllezf(a), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vectorPromoteVecPromoteVectorSignedInt(a: Int, c: Int): MaybeUninit<VectorSignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedInt>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorSignedInt>>(vlvgf(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedInt>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedInt>>()), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun vectorExtractVecExtractVectorUnsignedInt(a: VectorUnsignedInt, b: Int): UInt = vlgvf(a, b)

public fun vectorInsertVecInsertVectorUnsignedInt(a: UInt, b: VectorUnsignedInt, c: Int): VectorUnsignedInt = vlvgf(transmute(a), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedInt(a: ConstPtr): VectorUnsignedInt = vllezf(a)

public fun vectorPromoteVecPromoteVectorUnsignedInt(a: UInt, c: Int): MaybeUninit<VectorUnsignedInt> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedInt>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorUnsignedInt>>(vlvgf(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedInt>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedInt>>()), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun vectorExtractVecExtractVectorSignedLongLong(a: VectorSignedLongLong, b: Int): Long = transmuteWithLayouts<ULong, Long>(vlgvg(transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Long>())

public fun vectorInsertVecInsertVectorSignedLongLong(a: Long, b: VectorSignedLongLong, c: Int): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vlvgg(transmute(a), transmuteWithLayouts<VectorSignedLongLong, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorSignedLongLong>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorSignedLongLong(a: ConstPtr): VectorSignedLongLong = transmuteWithLayouts<VectorUnsignedLongLong, VectorSignedLongLong>(vllezg(a), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorSignedLongLong>())

public fun vectorPromoteVecPromoteVectorSignedLongLong(a: Long, c: Int): MaybeUninit<VectorSignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorSignedLongLong>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorSignedLongLong>>(vlvgg(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorSignedLongLong>>()), UnresolvedTypeLayout<VectorSignedLongLong>())
}

public fun vectorExtractVecExtractVectorUnsignedLongLong(a: VectorUnsignedLongLong, b: Int): ULong = vlgvg(a, b)

public fun vectorInsertVecInsertVectorUnsignedLongLong(a: ULong, b: VectorUnsignedLongLong, c: Int): VectorUnsignedLongLong = vlvgg(transmute(a), b, c)

public fun vectorInsertAndZeroVecInsertAndZeroVectorUnsignedLongLong(a: ConstPtr): VectorUnsignedLongLong = vllezg(a)

public fun vectorPromoteVecPromoteVectorUnsignedLongLong(a: ULong, c: Int): MaybeUninit<VectorUnsignedLongLong> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorUnsignedLongLong>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorUnsignedLongLong>>(vlvgg(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorUnsignedLongLong>>()), UnresolvedTypeLayout<VectorUnsignedLongLong>())
}

public fun vectorExtractVecExtractVectorFloat(a: VectorFloat, b: Int): Float = transmuteWithLayouts<UInt, Float>(vlgvf(transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(a, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Float>())

public fun vectorInsertVecInsertVectorFloat(a: Float, b: VectorFloat, c: Int): VectorFloat = transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(vlvgf(transmute(a), transmuteWithLayouts<VectorFloat, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedInt>()), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorFloat(a: ConstPtr): VectorFloat = transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(vllezf(a), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vectorPromoteVecPromoteVectorFloat(a: Float, c: Int): MaybeUninit<VectorFloat> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorFloat>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedInt, MaybeUninit<VectorFloat>>(vlvgf(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedInt>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>()), c), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<MaybeUninit<VectorFloat>>()), UnresolvedTypeLayout<VectorFloat>())
}

public fun vectorExtractVecExtractVectorDouble(a: VectorDouble, b: Int): Double = transmuteWithLayouts<ULong, Double>(vlgvg(transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(a, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), b), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Double>())

public fun vectorInsertVecInsertVectorDouble(a: Double, b: VectorDouble, c: Int): VectorDouble = transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(vlvgg(transmute(a), transmuteWithLayouts<VectorDouble, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<VectorDouble>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorInsertAndZeroVecInsertAndZeroVectorDouble(a: ConstPtr): VectorDouble = transmuteWithLayouts<VectorUnsignedLongLong, VectorDouble>(vllezg(a), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<VectorDouble>())

public fun vectorPromoteVecPromoteVectorDouble(a: Double, c: Int): MaybeUninit<VectorDouble> {
    val b = MaybeUninit.zeroed(UnresolvedTypeLayout<VectorDouble>())
    return MaybeUninit.new(transmuteWithLayouts<VectorUnsignedLongLong, MaybeUninit<VectorDouble>>(vlvgg(transmute(a), transmuteWithLayouts<Any?, VectorUnsignedLongLong>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedLongLong>()), c), UnresolvedTypeLayout<VectorUnsignedLongLong>(), UnresolvedTypeLayout<MaybeUninit<VectorDouble>>()), UnresolvedTypeLayout<VectorDouble>())
}

// port-lint: source s390x/vector.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.overflowingAdd
import io.github.kotlinmania.rustlang.core.num.overflowingSub
import io.github.kotlinmania.rustlang.core.num.wrappingAdd
import io.github.kotlinmania.rustlang.core.num.wrappingShl
import io.github.kotlinmania.rustlang.core.num.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.CountBits
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorAbs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorAdd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorAnd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorAndc
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorAvg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorCeil
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorCompare
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorComparePredicate
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorCompareRange
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorCopyUntilZero
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorCopyUntilZeroCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorEquality
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorEqualityIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorEqv
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorExtendSigned64
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorExtract
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEq
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEqCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEqIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEqIdxCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEqOrZeroIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyEqOrZeroIdxCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNe
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNeCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNeIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNeIdxCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNeOrZeroIdx
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFindAnyNeOrZeroIdxCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFloor
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFpTestDataClass
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorGatherElement
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorGfmsum
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorGfmsumAccum
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorInsert
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorInsertAndZero
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorLoad
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorLoadPair
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMadd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMax
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMeadd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMergeh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMergel
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMhadd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMin
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMladd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMoadd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMul
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMule
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMulh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorMulo
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNabs
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNand
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNeg
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNmadd
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNmsub
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorNor
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorOr
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorOrc
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPack
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPacks
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPacksCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPacksu
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPacksuCC
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPerm
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorPromote
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRevb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorReve
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRint
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRli
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRound
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorRoundc
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSearchString
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSel
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSigned
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSlb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSld
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSll
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSplat
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSplats
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSqrt
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSr
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSra
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSrab
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSral
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSrb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSrdb
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSrl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorStore
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSub
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSubc
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSum2
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSum4
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorSumU128
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorTestMask
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorTrunc
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorUnpackh
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorUnpackl
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorUnsigned
import io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorXor
import io.github.kotlinmania.rustlang.corearch.simd.F32x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// s390x vector intrinsics.
//
// For more info see the [Reference Summary] or the online [IBM docs].
//
// [Reference Summary]: https://www.ibm.com/support/pages/sites/default/files/2021-05/SA22-7871-10.pdf
// [IBM docs]: https://www.ibm.com/docs/en/zos/2.4.0?topic=support-vector-built-in-functions

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

/**
 * s390x-specific 128-bit wide vector of sixteen packed `Byte`
 */
public open class VectorSignedChar(
    public var value: ByteArray = ByteArray(16),
) {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorSignedChar = VectorSignedChar(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_signed_char"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorSignedChar> =
            object : TypeLayout<VectorSignedChar> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorSignedChar =
                    VectorSignedChar(
                        (PrimitiveArrayLayouts.byteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorSignedChar) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorSignedChar =
                    VectorSignedChar(
                        (PrimitiveArrayLayouts.byteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorSignedChar) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Byte): VectorSignedChar = run { val simdArg0 = value; VectorSignedChar() }

        public fun from(simd: Simd<Byte>): VectorSignedChar = transmuteWithLayouts<Simd<Byte>, VectorSignedChar>(simd, UnresolvedTypeLayout<Simd<Byte>>(), VectorSignedChar.typeLayout())
    }
}

public fun fromSimdByte(simd: VectorSignedChar): Simd<Byte> = transmuteWithLayouts<VectorSignedChar, Simd<Byte>>(simd, VectorSignedChar.typeLayout(), UnresolvedTypeLayout<Simd<Byte>>())

/**
 * s390x-specific 128-bit wide vector of sixteen packed `UByte`
 */
public open class VectorUnsignedChar(
    public var value: UByteArray = UByteArray(16),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorUnsignedChar = VectorUnsignedChar(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_unsigned_char"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorUnsignedChar> =
            object : TypeLayout<VectorUnsignedChar> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorUnsignedChar =
                    VectorUnsignedChar(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorUnsignedChar) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorUnsignedChar =
                    VectorUnsignedChar(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorUnsignedChar) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UByte): VectorUnsignedChar = run { val simdArg0 = value; VectorUnsignedChar() }

        public fun from(simd: Simd<UByte>): VectorUnsignedChar = transmuteWithLayouts<Simd<UByte>, VectorUnsignedChar>(simd, UnresolvedTypeLayout<Simd<UByte>>(), VectorUnsignedChar.typeLayout())
    }
}

public fun fromSimdUByte(simd: VectorUnsignedChar): Simd<UByte> = transmuteWithLayouts<VectorUnsignedChar, Simd<UByte>>(simd, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<Simd<UByte>>())

/**
 * s390x-specific 128-bit wide vector mask of sixteen packed elements
 */
public open class VectorBoolChar(
    public var value: ByteArray = ByteArray(16),
) {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorBoolChar = VectorBoolChar(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_bool_char"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorBoolChar> =
            object : TypeLayout<VectorBoolChar> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorBoolChar =
                    VectorBoolChar(
                        (PrimitiveArrayLayouts.byteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorBoolChar) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorBoolChar =
                    VectorBoolChar(
                        (PrimitiveArrayLayouts.byteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorBoolChar) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Byte): VectorBoolChar = run { val simdArg0 = value; VectorBoolChar() }

        public fun from(simd: Simd<Byte>): VectorBoolChar = transmuteWithLayouts<Simd<Byte>, VectorBoolChar>(simd, UnresolvedTypeLayout<Simd<Byte>>(), VectorBoolChar.typeLayout())

        public fun from(value: M8x16): VectorBoolChar = transmuteWithLayouts<M8x16, VectorBoolChar>(value, UnresolvedTypeLayout<M8x16>(), VectorBoolChar.typeLayout())
    }
}

public fun fromSimdByte(simd: VectorBoolChar): Simd<Byte> = transmuteWithLayouts<VectorBoolChar, Simd<Byte>>(simd, VectorBoolChar.typeLayout(), UnresolvedTypeLayout<Simd<Byte>>())

/**
 * s390x-specific 128-bit wide vector of eight packed `Short`
 */
public open class VectorSignedShort(
    public var value: ShortArray = ShortArray(8),
) {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorSignedShort = VectorSignedShort(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_signed_short"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorSignedShort> =
            object : TypeLayout<VectorSignedShort> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorSignedShort =
                    VectorSignedShort(
                        (PrimitiveArrayLayouts.shortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorSignedShort) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorSignedShort =
                    VectorSignedShort(
                        (PrimitiveArrayLayouts.shortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorSignedShort) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Short): VectorSignedShort = run { val simdArg0 = value; VectorSignedShort() }

        public fun from(simd: Simd<Short>): VectorSignedShort = transmuteWithLayouts<Simd<Short>, VectorSignedShort>(simd, UnresolvedTypeLayout<Simd<Short>>(), VectorSignedShort.typeLayout())
    }
}

public fun fromSimdShort(simd: VectorSignedShort): Simd<Short> = transmuteWithLayouts<VectorSignedShort, Simd<Short>>(simd, VectorSignedShort.typeLayout(), UnresolvedTypeLayout<Simd<Short>>())

/**
 * s390x-specific 128-bit wide vector of eight packed `UShort`
 */
public open class VectorUnsignedShort(
    public var value: UShortArray = UShortArray(8),
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorUnsignedShort = VectorUnsignedShort(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_unsigned_short"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorUnsignedShort> =
            object : TypeLayout<VectorUnsignedShort> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorUnsignedShort =
                    VectorUnsignedShort(
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorUnsignedShort) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorUnsignedShort =
                    VectorUnsignedShort(
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorUnsignedShort) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): VectorUnsignedShort = run { val simdArg0 = value; VectorUnsignedShort() }

        public fun from(simd: Simd<UShort>): VectorUnsignedShort = transmuteWithLayouts<Simd<UShort>, VectorUnsignedShort>(simd, UnresolvedTypeLayout<Simd<UShort>>(), VectorUnsignedShort.typeLayout())
    }
}

public fun fromSimdUShort(simd: VectorUnsignedShort): Simd<UShort> = transmuteWithLayouts<VectorUnsignedShort, Simd<UShort>>(simd, VectorUnsignedShort.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

/**
 * s390x-specific 128-bit wide vector mask of eight packed elements
 */
public open class VectorBoolShort(
    public var value: ShortArray = ShortArray(8),
) {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorBoolShort = VectorBoolShort(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_bool_short"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorBoolShort> =
            object : TypeLayout<VectorBoolShort> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorBoolShort =
                    VectorBoolShort(
                        (PrimitiveArrayLayouts.shortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorBoolShort) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorBoolShort =
                    VectorBoolShort(
                        (PrimitiveArrayLayouts.shortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorBoolShort) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Short): VectorBoolShort = run { val simdArg0 = value; VectorBoolShort() }

        public fun from(simd: Simd<Short>): VectorBoolShort = transmuteWithLayouts<Simd<Short>, VectorBoolShort>(simd, UnresolvedTypeLayout<Simd<Short>>(), VectorBoolShort.typeLayout())

        public fun from(value: M16x8): VectorBoolShort = transmuteWithLayouts<M16x8, VectorBoolShort>(value, UnresolvedTypeLayout<M16x8>(), VectorBoolShort.typeLayout())
    }
}

public fun fromSimdShort(simd: VectorBoolShort): Simd<Short> = transmuteWithLayouts<VectorBoolShort, Simd<Short>>(simd, VectorBoolShort.typeLayout(), UnresolvedTypeLayout<Simd<Short>>())

/**
 * s390x-specific 128-bit wide vector of four packed `Int`
 */
public open class VectorSignedInt(
    public var value: IntArray = IntArray(4),
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorSignedInt = VectorSignedInt(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_signed_int"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorSignedInt> =
            object : TypeLayout<VectorSignedInt> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorSignedInt =
                    VectorSignedInt(
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorSignedInt) {
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorSignedInt =
                    VectorSignedInt(
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorSignedInt) {
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): VectorSignedInt = run { val simdArg0 = value; VectorSignedInt() }

        public fun from(simd: Simd<Int>): VectorSignedInt = transmuteWithLayouts<Simd<Int>, VectorSignedInt>(simd, UnresolvedTypeLayout<Simd<Int>>(), VectorSignedInt.typeLayout())
    }
}

public fun fromSimdInt(simd: VectorSignedInt): Simd<Int> = transmuteWithLayouts<VectorSignedInt, Simd<Int>>(simd, VectorSignedInt.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

/**
 * s390x-specific 128-bit wide vector of four packed `UInt`
 */
public open class VectorUnsignedInt(
    public var value: UIntArray = UIntArray(4),
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorUnsignedInt = VectorUnsignedInt(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_unsigned_int"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorUnsignedInt> =
            object : TypeLayout<VectorUnsignedInt> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorUnsignedInt =
                    VectorUnsignedInt(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorUnsignedInt) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorUnsignedInt =
                    VectorUnsignedInt(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorUnsignedInt) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UInt): VectorUnsignedInt = run { val simdArg0 = value; VectorUnsignedInt() }

        public fun from(simd: Simd<UInt>): VectorUnsignedInt = transmuteWithLayouts<Simd<UInt>, VectorUnsignedInt>(simd, UnresolvedTypeLayout<Simd<UInt>>(), VectorUnsignedInt.typeLayout())
    }
}

public fun fromSimdUInt(simd: VectorUnsignedInt): Simd<UInt> = transmuteWithLayouts<VectorUnsignedInt, Simd<UInt>>(simd, VectorUnsignedInt.typeLayout(), UnresolvedTypeLayout<Simd<UInt>>())

/**
 * s390x-specific 128-bit wide vector mask of four packed elements
 */
public open class VectorBoolInt(
    public var value: IntArray = IntArray(4),
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorBoolInt = VectorBoolInt(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_bool_int"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorBoolInt> =
            object : TypeLayout<VectorBoolInt> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorBoolInt =
                    VectorBoolInt(
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorBoolInt) {
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorBoolInt =
                    VectorBoolInt(
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorBoolInt) {
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): VectorBoolInt = run { val simdArg0 = value; VectorBoolInt() }

        public fun from(simd: Simd<Int>): VectorBoolInt = transmuteWithLayouts<Simd<Int>, VectorBoolInt>(simd, UnresolvedTypeLayout<Simd<Int>>(), VectorBoolInt.typeLayout())

        public fun from(value: M32x4): VectorBoolInt = transmuteWithLayouts<M32x4, VectorBoolInt>(value, UnresolvedTypeLayout<M32x4>(), VectorBoolInt.typeLayout())
    }
}

public fun fromSimdInt(simd: VectorBoolInt): Simd<Int> = transmuteWithLayouts<VectorBoolInt, Simd<Int>>(simd, VectorBoolInt.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

/**
 * s390x-specific 128-bit wide vector of two packed `Long`
 */
public open class VectorSignedLongLong(
    public var value: LongArray = LongArray(2),
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorSignedLongLong = VectorSignedLongLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_signed_long_long"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorSignedLongLong> =
            object : TypeLayout<VectorSignedLongLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorSignedLongLong =
                    VectorSignedLongLong(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorSignedLongLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorSignedLongLong =
                    VectorSignedLongLong(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorSignedLongLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): VectorSignedLongLong = run { val simdArg0 = value; VectorSignedLongLong() }

        public fun from(simd: Simd<Long>): VectorSignedLongLong = transmuteWithLayouts<Simd<Long>, VectorSignedLongLong>(simd, UnresolvedTypeLayout<Simd<Long>>(), VectorSignedLongLong.typeLayout())
    }
}

public fun fromSimdLong(simd: VectorSignedLongLong): Simd<Long> = transmuteWithLayouts<VectorSignedLongLong, Simd<Long>>(simd, VectorSignedLongLong.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * s390x-specific 128-bit wide vector of two packed `ULong`
 */
public open class VectorUnsignedLongLong(
    public var value: ULongArray = ULongArray(2),
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorUnsignedLongLong = VectorUnsignedLongLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_unsigned_long_long"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorUnsignedLongLong> =
            object : TypeLayout<VectorUnsignedLongLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorUnsignedLongLong =
                    VectorUnsignedLongLong(
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorUnsignedLongLong) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorUnsignedLongLong =
                    VectorUnsignedLongLong(
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorUnsignedLongLong) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: ULong): VectorUnsignedLongLong = run { val simdArg0 = value; VectorUnsignedLongLong() }

        public fun from(simd: Simd<ULong>): VectorUnsignedLongLong = transmuteWithLayouts<Simd<ULong>, VectorUnsignedLongLong>(simd, UnresolvedTypeLayout<Simd<ULong>>(), VectorUnsignedLongLong.typeLayout())
    }
}

public fun fromSimdULong(simd: VectorUnsignedLongLong): Simd<ULong> = transmuteWithLayouts<VectorUnsignedLongLong, Simd<ULong>>(simd, VectorUnsignedLongLong.typeLayout(), UnresolvedTypeLayout<Simd<ULong>>())

/**
 * s390x-specific 128-bit wide vector mask of two packed elements
 */
public open class VectorBoolLongLong(
    public var value: LongArray = LongArray(2),
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorBoolLongLong = VectorBoolLongLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_bool_long_long"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorBoolLongLong> =
            object : TypeLayout<VectorBoolLongLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorBoolLongLong =
                    VectorBoolLongLong(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorBoolLongLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorBoolLongLong =
                    VectorBoolLongLong(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorBoolLongLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): VectorBoolLongLong = run { val simdArg0 = value; VectorBoolLongLong() }

        public fun from(simd: Simd<Long>): VectorBoolLongLong = transmuteWithLayouts<Simd<Long>, VectorBoolLongLong>(simd, UnresolvedTypeLayout<Simd<Long>>(), VectorBoolLongLong.typeLayout())
    }
}

public fun fromSimdLong(simd: VectorBoolLongLong): Simd<Long> = transmuteWithLayouts<VectorBoolLongLong, Simd<Long>>(simd, VectorBoolLongLong.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * s390x-specific 128-bit wide vector of four packed `Float`
 */
public open class VectorFloat(
    public var value: FloatArray = FloatArray(4),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorFloat = VectorFloat(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_float"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorFloat> =
            object : TypeLayout<VectorFloat> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorFloat =
                    VectorFloat(
                        (PrimitiveArrayLayouts.floatArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorFloat) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorFloat =
                    VectorFloat(
                        (PrimitiveArrayLayouts.floatArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorFloat) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): VectorFloat = run { val simdArg0 = value; VectorFloat() }

        public fun from(simd: Simd<Float>): VectorFloat = transmuteWithLayouts<Simd<Float>, VectorFloat>(simd, UnresolvedTypeLayout<Simd<Float>>(), VectorFloat.typeLayout())
    }
}

public fun fromSimdFloat(simd: VectorFloat): Simd<Float> = transmuteWithLayouts<VectorFloat, Simd<Float>>(simd, VectorFloat.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

/**
 * s390x-specific 128-bit wide vector of two packed `Double`
 */
public open class VectorDouble(
    public var value: DoubleArray = DoubleArray(2),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorDouble = VectorDouble(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_double"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<VectorDouble> =
            object : TypeLayout<VectorDouble> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorDouble =
                    VectorDouble(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorDouble) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorDouble =
                    VectorDouble(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorDouble) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): VectorDouble = run { val simdArg0 = value; VectorDouble() }

        public fun from(simd: Simd<Double>): VectorDouble = transmuteWithLayouts<Simd<Double>, VectorDouble>(simd, UnresolvedTypeLayout<Simd<Double>>(), VectorDouble.typeLayout())
    }
}

public fun fromSimdDouble(simd: VectorDouble): Simd<Double> = transmuteWithLayouts<VectorDouble, Simd<Double>>(simd, VectorDouble.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromM8x16(value: VectorBoolChar): M8x16 = transmuteWithLayouts<VectorBoolChar, M8x16>(value, VectorBoolChar.typeLayout(), UnresolvedTypeLayout<M8x16>())

public fun fromM16x8(value: VectorBoolShort): M16x8 = transmuteWithLayouts<VectorBoolShort, M16x8>(value, VectorBoolShort.typeLayout(), UnresolvedTypeLayout<M16x8>())

public fun fromM32x4(value: VectorBoolInt): M32x4 = transmuteWithLayouts<VectorBoolInt, M32x4>(value, VectorBoolInt.typeLayout(), UnresolvedTypeLayout<M32x4>())

public open class PackedTuple<T, U>(
    public var x: T,
    public var y: U,
) {
    public companion object {
        public fun <T, U> typeLayout(typeLayoutT: TypeLayout<T>, typeLayoutU: TypeLayout<U>): TypeLayout<PackedTuple<T, U>> =
            object : TypeLayout<PackedTuple<T, U>> {
                override val alignBytes: Long = listOf(1L, minOf(typeLayoutT.alignBytes, 1L), minOf(typeLayoutU.alignBytes, 1L)).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, minOf(typeLayoutU.alignBytes, 1L))
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutU.sizeBytes), alignBytes)
                override fun readNative(addr: Long): PackedTuple<T, U> =
                    PackedTuple(
                        (typeLayoutT).readNative(addr),
                        (typeLayoutU).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: PackedTuple<T, U>) {
                    (typeLayoutT).writeNative(addr, value.x)
                    (typeLayoutU).writeNative(addr + off1, value.y)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PackedTuple<T, U> =
                    PackedTuple(
                        (typeLayoutT).readBytes(buf, offset),
                        (typeLayoutU).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PackedTuple<T, U>) {
                    (typeLayoutT).writeBytes(buf, offset, value.x)
                    (typeLayoutU).writeBytes(buf, offset + off1.toInt(), value.y)
                }
            }
    }
}

public expect fun nearbyintV4f32(a: VectorFloat): VectorFloat

public expect fun nearbyintV2f64(a: VectorDouble): VectorDouble

public expect fun roundevenV4f32(a: VectorFloat): VectorFloat

public expect fun roundevenV2f64(a: VectorDouble): VectorDouble

public expect fun vsra(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vsrl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vsl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vsrab(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vsrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vslb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun verimb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar, d: Int): VectorSignedChar

public expect fun verimh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort, d: Int): VectorSignedShort

public expect fun verimf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt, d: Int): VectorSignedInt

public expect fun verimg(a: VectorSignedLongLong, b: VectorSignedLongLong, c: VectorSignedLongLong, d: Int): VectorSignedLongLong

public expect fun vperm(a: VectorSignedChar, b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar

public expect fun vsumb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedInt

public expect fun vsumh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt

public expect fun vsumgh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedLongLong

public expect fun vsumgf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong

public expect fun vsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128

public expect fun vsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128

public expect fun vscbiq(a: U128, b: U128): U128

public expect fun vsbiq(a: U128, b: U128, c: U128): U128

public expect fun vsbcbiq(a: U128, b: U128, c: U128): U128

public expect fun vacq(a: U128, b: U128, c: U128): U128

public expect fun vscbib(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vscbih(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vscbif(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vscbig(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong

public expect fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar

public expect fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort

public expect fun vfaef(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt

public expect fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar

public expect fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort

public expect fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt

public expect fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int>

public expect fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int>

public expect fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int>

public expect fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int>

public expect fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int>

public expect fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int>

public expect fun vll(a: UInt, b: ConstPtr): VectorSignedChar

public expect fun vstl(a: VectorSignedChar, b: UInt, c: MutPtr)

public expect fun vlrl(a: UInt, b: ConstPtr): VectorUnsignedChar

public expect fun vstrl(a: VectorUnsignedChar, b: UInt, c: MutPtr)

public expect fun lcbb(a: ConstPtr, b: UInt): UInt

public expect fun vlbb(a: ConstPtr, b: UInt): MaybeUninit<VectorSignedChar>

public expect fun vpksh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar

public expect fun vpksf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort

public expect fun vpksg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedInt

public expect fun vpklsh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar

public expect fun vpklsf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort

public expect fun vpklsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt

public expect fun vpkshs(a: VectorSignedShort, b: VectorSignedShort): PackedTuple<VectorSignedChar, Int>

public expect fun vpksfs(a: VectorSignedInt, b: VectorSignedInt): PackedTuple<VectorSignedShort, Int>

public expect fun vpksgs(a: VectorSignedLongLong, b: VectorSignedLongLong): PackedTuple<VectorSignedInt, Int>

public expect fun vpklshs(a: VectorUnsignedShort, b: VectorUnsignedShort): PackedTuple<VectorUnsignedChar, Int>

public expect fun vpklsfs(a: VectorUnsignedInt, b: VectorUnsignedInt): PackedTuple<VectorUnsignedShort, Int>

public expect fun vpklsgs(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): PackedTuple<VectorUnsignedInt, Int>

public expect fun vavgb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vavgh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vavgf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vavgg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong

public expect fun vavglb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vavglh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vavglf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vavglg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong

public expect fun vcksm(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vmhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vmhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vmlhb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vmlhh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmlhf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmaeb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort

public expect fun vmaeh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt

public expect fun vmaef(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong

public expect fun vmaleb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmaleh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmalef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong

public expect fun vmaob(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort

public expect fun vmaoh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt

public expect fun vmaof(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong

public expect fun vmalob(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmaloh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmalof(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong

public expect fun vmahb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar

public expect fun vmahh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort

public expect fun vmahf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt

public expect fun vmalhb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar

public expect fun vmalhh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmalhf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmalb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar

public expect fun vmalh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort

public expect fun vmalf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt

public expect fun vmallb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar

public expect fun vmallh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmallf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vgfmb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort

public expect fun vgfmh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt

public expect fun vgfmf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong

public expect fun vgfmg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128

public expect fun vgfmab(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort

public expect fun vgfmah(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vgfmaf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong

public expect fun vgfmag(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128): U128

public expect fun vbperm(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedLongLong

public expect fun vftcisb(a: VectorFloat, b: UInt): PackedTuple<VectorBoolInt, Int>

public expect fun vftcidb(a: VectorDouble, b: UInt): PackedTuple<VectorBoolLongLong, Int>

public expect fun vtm(a: I8x16, b: I8x16): Int

public expect fun vstrsb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vstrsh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vstrsf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vstrszb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vstrszh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vstrszf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vistrb(a: VectorUnsignedChar): VectorUnsignedChar

public expect fun vistrh(a: VectorUnsignedShort): VectorUnsignedShort

public expect fun vistrf(a: VectorUnsignedInt): VectorUnsignedInt

public expect fun vistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int>

public expect fun vistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int>

public expect fun vistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int>

public expect fun vmslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128, d: UInt): U128

public expect fun vstrcb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar

public expect fun vstrch(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort

public expect fun vstrcf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt

public expect fun vstrcbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int>

public expect fun vstrchs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int>

public expect fun vstrcfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int>

public expect fun vstrczb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar

public expect fun vstrczh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort

public expect fun vstrczf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt

public expect fun vstrczbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int>

public expect fun vstrczhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int>

public expect fun vstrczfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int>

public expect fun vfeeb(a: I8x16, b: I8x16): I8x16

public expect fun vfeeh(a: I16x8, b: I16x8): I16x8

public expect fun vfeef(a: I32x4, b: I32x4): I32x4

public expect fun vfeezb(a: I8x16, b: I8x16): I8x16

public expect fun vfeezh(a: I16x8, b: I16x8): I16x8

public expect fun vfeezf(a: I32x4, b: I32x4): I32x4

public expect fun vfeebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int>

public expect fun vfeehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int>

public expect fun vfeefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int>

public expect fun vfeezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int>

public expect fun vfeezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int>

public expect fun vfeezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int>

public expect fun vfeneb(a: I8x16, b: I8x16): I8x16

public expect fun vfeneh(a: I16x8, b: I16x8): I16x8

public expect fun vfenef(a: I32x4, b: I32x4): I32x4

public expect fun vfenezb(a: I8x16, b: I8x16): I8x16

public expect fun vfenezh(a: I16x8, b: I16x8): I16x8

public expect fun vfenezf(a: I32x4, b: I32x4): I32x4

public expect fun vfenebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int>

public expect fun vfenehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int>

public expect fun vfenefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int>

public expect fun vfenezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int>

public expect fun vfenezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int>

public expect fun vfenezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int>

public expect fun vclfnhs(a: VectorSignedShort, immarg: Int): VectorFloat

public expect fun vclfnls(a: VectorSignedShort, immarg: Int): VectorFloat

public expect fun vcfn(a: VectorSignedShort, immarg: Int): VectorSignedShort

public expect fun vcnf(a: VectorSignedShort, immarg: Int): VectorSignedShort

public expect fun vcrnfs(a: VectorFloat, b: VectorFloat, immarg: Int): VectorSignedShort

// These are the intrinsics we'd like to use (with mode 0). However, they require
// "vector-enhancements-1" and don't have a fallback, whereas `vecMin`/`vecMax` should be
// available with just "vector". Therefore, we cannot use them.
// @linkName= "llvm.s390.vfmaxsb" fn vfmaxsb(a: vectorFloat, b: vectorFloat, mode: i32) -> vectorFloat;
// @linkName= "llvm.s390.vfmaxdb" fn vfmaxdb(a: vectorDouble, b: vectorDouble, mode: i32) -> vectorDouble;
// @linkName= "llvm.s390.vfminsb" fn vfminsb(a: vectorFloat, b: vectorFloat, mode: i32) -> vectorFloat;
// @linkName= "llvm.s390.vfmindb" fn vfmindb(a: vectorDouble, b: vectorDouble, mode: i32) -> vectorDouble;
// Instead, we use "portable" LLVM intrinsics -- even though those have the wrong semantics
// (https://github.com/rust-lang/stdarch/issues/2060), they usually do the right thing.
public expect fun minnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat

public expect fun minnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble

public expect fun maxnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat

public expect fun maxnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble

public open class ShuffleMask(
    public var value: UIntArray = UIntArray(0),
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(n: Int = 0): TypeLayout<ShuffleMask> =
            object : TypeLayout<ShuffleMask> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((n).toLong() * (PrimitiveLayouts.U32).strideBytes), 4L)
                override fun readNative(addr: Long): ShuffleMask =
                    ShuffleMask(
                        (PrimitiveArrayLayouts.uIntArray((n).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ShuffleMask) {
                    (PrimitiveArrayLayouts.uIntArray((n).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ShuffleMask =
                    ShuffleMask(
                        (PrimitiveArrayLayouts.uIntArray((n).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ShuffleMask) {
                    (PrimitiveArrayLayouts.uIntArray((n).toInt())).writeBytes(buf, offset, value.value)
                }
            }

        public fun reverse(n: Int): ShuffleMask {
            val index = UIntArray(n) { 0u }
            var i = 0
            while (i < n) {
                index[i] = (n - i - 1).toUInt()
                i += 1
            }
            return ShuffleMask(index)
        }

        public fun mergeLow(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = n / 2
            var index = 0
            while (index < n) {
                mask[index] = i.toUInt()
                mask[index + 1] = (i + n).toUInt()
                i += 1
                index += 2
            }
            return ShuffleMask(mask)
        }

        public fun mergeHigh(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = 0
            var index = 0
            while (index < n) {
                mask[index] = i.toUInt()
                mask[index + 1] = (i + n).toUInt()
                i += 1
                index += 2
            }
            return ShuffleMask(mask)
        }

        public fun even(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = 0
            var index = 0
            while (index < n) {
                mask[index] = i.toUInt()
                i += 2
                index += 1
            }
            return ShuffleMask(mask)
        }

        public fun odd(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = 1
            var index = 0
            while (index < n) {
                mask[index] = i.toUInt()
                i += 2
                index += 1
            }
            return ShuffleMask(mask)
        }

        public fun pack(n: Int): ShuffleMask = ShuffleMask.odd(n)

        public fun unpackLow(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = 0
            while (i < n) {
                mask[i] = (n + i).toUInt()
                i += 1
            }
            return ShuffleMask(mask)
        }

        public fun unpackHigh(n: Int): ShuffleMask {
            val mask = UIntArray(n) { 0u }
            var i = 0
            while (i < n) {
                mask[i] = i.toUInt()
                i += 1
            }
            return ShuffleMask(mask)
        }
    }
}

public fun genmask(mask: UShort): UByteArray {
    var bits = mask
    val elements = UByteArray(16) { 0u.toUByte() }
    var i = 0
    while (i < 16) {
        elements[i] = when (bits and (((1u.toUShort()).toUInt() shl 15).toUShort())) {
            0u.toUShort() -> 0u
            else -> 0xFFu
        }
        bits = ((bits).toUInt() shl 1).toUShort()
        i += 1
    }
    return elements
}

public fun genmasks(bitWidth: UInt, a: UByte, b: UByte): ULong {
    val b1 = b
    val bitWidth1 = bitWidth.toUByte()
    val a1 = a % bitWidth1
    var b2 = b1 % bitWidth1
    if (a1 > b2) {
        b2 = (bitWidth1 - 1u).toUByte()
    }
    // of course these indices start from the left
    val a2 = (bitWidth1 - 1u) - a1
    val b3 = (bitWidth1 - 1u) - b2
    return ((1uL.wrappingShl(a2.toUInt() + 1u)) - 1uL) and ((1uL.wrappingShl(b3.toUInt())) - 1uL).inv()
}

public fun validateBlockBoundary(blockBoundary: UShort): UInt {
    check(blockBoundary.isPowerOfTwo() && blockBoundary >= 64u.toUShort() && blockBoundary <= 4096u.toUShort()) { "block boundary must be a constant power of 2 from 64 to 4096" }
    // so that 64 is encoded as 0, 128 as 1, ect.
    return blockBoundary.toUInt() shr 7
}

public sealed class FindImm(
    public val value: Int,
) {
    public data object Eq : FindImm(4)
    public data object Ne : FindImm(12)
    public data object EqIdx : FindImm(0)
    public data object NeIdx : FindImm(8)
}

/**
 * Load Count to Block Boundary
 */
public fun lcbb(ptr: ConstPtr, blockBoundary: UShort): UInt = lcbb(ptr, validateBlockBoundary(blockBoundary))

/**
 * Vector Negate
 */
public fun <T : VectorNeg<T>> vecNeg(a: T): T = a.vecNeg()

/**
 * Vector Add
 */
public fun <Result, T : VectorAdd<U, Result>, U> vecAdd(a: T, b: U): Result = a.vecAdd(b)

/**
 * Vector Subtract
 */
public fun <Result, T : VectorSub<U, Result>, U> vecSub(a: T, b: U): Result = a.vecSub(b)

/**
 * Vector Multiply
 *
 * ## Purpose
 * Compute the products of corresponding elements of two vectors.
 *
 * ## Result value
 * Each element of r receives the product of the corresponding elements of a and b.
 */
public fun <T : VectorMul<T>> vecMul(a: T, b: T): T = a.vecMul(b)

/**
 * Vector Count Leading Zeros
 */
public fun <Result, T : CountBits<Result>> vecCntlz(a: T): Result = a.vecCntlz()

/**
 * Vector Count Trailing Zeros
 */
public fun <Result, T : CountBits<Result>> vecCnttz(a: T): Result = a.vecCnttz()

/**
 * Vector Population Count
 *
 * Computes the population count (number of set bits) in each element of the input.
 */
public fun <Result, T : CountBits<Result>> vecPopcnt(a: T): Result = a.vecPopcnt()

/**
 * Vector Maximum
 */
public fun <Result, T : VectorMax<U, Result>, U> vecMax(a: T, b: U): Result = a.vecMax(b)

/**
 * Vector  Minimum
 */
public fun <Result, T : VectorMin<U, Result>, U> vecMin(a: T, b: U): Result = a.vecMin(b)

/**
 * Vector Absolute
 */
public fun <T : VectorAbs<T>> vecAbs(a: T): T = a.vecAbs()

/**
 * Vector Negative Absolute
 */
public fun <T : VectorNabs<T>> vecNabs(a: T): T = a.vecNabs()

/**
 * Vector Negative Multiply Add
 */
public fun <T : VectorNmadd<T>> vecNmadd(a: T, b: T, c: T): T = a.vecNmadd(b, c)

/**
 * Vector Negative Multiply Subtract
 */
public fun <T : VectorNmsub<T>> vecNmsub(a: T, b: T, c: T): T = a.vecNmsub(b, c)

/**
 * Vector Square Root
 */
public fun <T : VectorSqrt<T>> vecSqrt(a: T): T = a.vecSqrt()

/**
 * Vector Splat
 */
public fun <T : VectorSplat<T>> vecSplat(a: T, imm: UInt): T = a.vecSplat()

/**
 * Vector Splats
 */
public fun <Result, T : VectorSplats<U, Result>, U> vecSplats(a: T): U = a.vecSplats()

/**
 * Vector AND
 */
public fun <Result, T : VectorAnd<U, Result>, U> vecAnd(a: T, b: U): Result = a.vecAnd(b)

/**
 * Vector OR
 */
public fun <Result, T : VectorOr<U, Result>, U> vecOr(a: T, b: U): Result = a.vecOr(b)

/**
 * Vector XOR
 */
public fun <Result, T : VectorXor<U, Result>, U> vecXor(a: T, b: U): Result = a.vecXor(b)

/**
 * Vector NOR
 */
public fun <Result, T : VectorNor<U, Result>, U> vecNor(a: T, b: U): Result = a.vecNor(b)

/**
 * Vector NAND
 */
public fun <Result, T : VectorNand<U, Result>, U> vecNand(a: T, b: U): Result = a.vecNand(b)

/**
 * Vector XNOR
 */
public fun <Result, T : VectorEqv<U, Result>, U> vecEqv(a: T, b: U): Result = a.vecEqv(b)

/**
 * Vector ANDC
 */
public fun <Result, T : VectorAndc<U, Result>, U> vecAndc(a: T, b: U): Result = a.vecAndc(b)

/**
 * Vector OR with Complement
 *
 * ## Purpose
 * Performs a bitwise OR of the first vector with the bitwise-complemented second vector.
 *
 * ## Result value
 * r is the bitwise OR of a and the bitwise complement of b.
 */
public fun <Result, T : VectorOrc<U, Result>, U> vecOrc(a: T, b: U): Result = a.vecOrc(b)

/**
 * Vector Floor
 */
public fun <T : VectorFloor<T>> vecFloor(a: T): T = a.vecFloor()

/**
 * Vector Ceil
 */
public fun <T : VectorCeil<T>> vecCeil(a: T): T = a.vecCeil()

/**
 * Vector Truncate
 *
 * Returns a vector containing the truncated values of the corresponding elements of the given vector.
 * Each element of the result contains the value of the corresponding element of a, truncated to an integral value.
 */
public fun <T : VectorTrunc<T>> vecTrunc(a: T): T = a.vecTrunc()

/**
 * Vector Round
 *
 * Returns a vector containing the rounded values to the nearest representable floating-point integer,
 * using IEEE round-to-nearest rounding, of the corresponding elements of the given vector
 */
public fun <T : VectorRound<T>> vecRound(a: T): T = a.vecRound()

/**
 * Vector Round to Current
 *
 * Returns a vector by using the current rounding mode to round every
 * floating-point element in the given vector to integer.
 */
public fun <T : VectorRoundc<T>> vecRoundc(a: T): T = a.vecRoundc()

/**
 * Vector Round toward Negative Infinity
 *
 * Returns a vector containing the largest representable floating-point integral values less
 * than or equal to the values of the corresponding elements of the given vector.
 */
public fun <T : VectorFloor<T>> vecRoundm(a: T): T {
    // the IBM docs note
    //
    // > vecRoundm provides the same functionality as vecFloor, except that vecRoundz would not trigger the IEEE-inexact exception.
    //
    // but in practice `vecFloor` also does not trigger that exception, so both are equivalent
    return a.vecFloor()
}

/**
 * Vector Round toward Positive Infinity
 *
 * Returns a vector containing the smallest representable floating-point integral values greater
 * than or equal to the values of the corresponding elements of the given vector.
 */
public fun <T : VectorCeil<T>> vecRoundp(a: T): T {
    // the IBM docs note
    //
    // > vecRoundp provides the same functionality as vecCeil, except that vecRoundz would not trigger the IEEE-inexact exception.
    //
    // but in practice `vecCeil` also does not trigger that exception, so both are equivalent
    return a.vecCeil()
}

/**
 * Vector Round toward Zero
 *
 * Returns a vector containing the truncated values of the corresponding elements of the given vector.
 * Each element of the result contains the value of the corresponding element of a, truncated to an integral value.
 */
public fun <T : VectorTrunc<T>> vecRoundz(a: T): T {
    // the IBM docs note
    //
    // > vecRoundz provides the same functionality as vecTrunc, except that vecRoundz would not trigger the IEEE-inexact exception.
    //
    // but in practice `vecTrunc` also does not trigger that exception, so both are equivalent
    return a.vecTrunc()
}

/**
 * Vector Round to Integer
 *
 * Returns a vector by using the current rounding mode to round every floating-point element in the given vector to integer.
 */
public fun <T : VectorRint<T>> vecRint(a: T): T = a.vecRint()

/**
 * Vector Average
 */
public fun <Result, T : VectorAvg<U, Result>, U> vecAvg(a: T, b: U): Result = a.vecAvg(b)

/**
 * Vector Shift Left
 */
public fun <Result, T : VectorSl<U, Result>, U> vecSl(a: T, b: U): Result = a.vecSl(b)

/**
 * Vector Shift Right
 */
public fun <Result, T : VectorSr<U, Result>, U> vecSr(a: T, b: U): Result = a.vecSr(b)

/**
 * Vector Shift Right Algebraic
 */
public fun <Result, T : VectorSra<U, Result>, U> vecSra(a: T, b: U): Result = a.vecSra(b)

/**
 * Vector Shift Left by Byte
 */
public fun <Result, T : VectorSlb<U, Result>, U> vecSlb(a: T, b: U): Result = a.vecSlb(b)

/**
 * Vector Shift Right by Byte
 */
public fun <Result, T : VectorSrb<U, Result>, U> vecSrb(a: T, b: U): Result = a.vecSrb(b)

/**
 * Vector Shift Right Algebraic by Byte
 */
public fun <Result, T : VectorSrab<U, Result>, U> vecSrab(a: T, b: U): Result = a.vecSrab(b)

/**
 * Vector Element Rotate Left
 */
public fun <Result, T : VectorRl<U, T, Result>, U> vecRl(a: T, b: U): Result = a.vecRl(b)

/**
 * Vector Shift Left
 *
 * Performs a left shift for a vector by a given number of bits. Each element of the result is obtained by shifting the corresponding
 * element of a left by the number of bits specified by the last 3 bits of every byte of b. The bits that are shifted out are replaced by zeros.
 */
public fun <T> vecSll(a: T, b: VectorUnsignedChar): T where T : VectorSll<VectorUnsignedChar, T> = a.vecSll(b)

/**
 * Vector Shift Right
 *
 * Performs a right shift for a vector by a given number of bits. Each element of the result is obtained by shifting the corresponding
 * element of a right by the number of bits specified by the last 3 bits of every byte of b. The bits that are shifted out are replaced by zeros.
 */
public fun <T> vecSrl(a: T, b: VectorUnsignedChar): T where T : VectorSrl<VectorUnsignedChar, T> = a.vecSrl(b)

/**
 * Vector Shift Right Arithmetic
 *
 * Performs an algebraic right shift for a vector by a given number of bits. Each element of the result is obtained by shifting the corresponding
 * element of a right by the number of bits specified by the last 3 bits of every byte of b. The bits that are shifted out are replaced by copies of
 * the most significant bit of the element of a.
 */
public fun <T> vecSral(a: T, b: VectorUnsignedChar): T where T : VectorSral<VectorUnsignedChar, T> = a.vecSral(b)

/**
 * Vector Element Rotate Left Immediate
 *
 * Rotates each element of a vector left by a given number of bits. Each element of the result is obtained by rotating the corresponding element
 * of a left by the number of bits specified by b, modulo the number of bits in the element.
 */
public fun <T : VectorRli<T>> vecRli(a: T, bits: CUlong): T = a.vecRli(bits)

/**
 * Vector Reverse Elements
 *
 * Returns a vector with the elements of the input vector in reversed order.
 */
public fun <T : VectorReve<T>> vecReve(a: T): T = a.vecReve()

/**
 * Vector Byte Reverse
 *
 * Returns a vector where each vector element contains the corresponding byte-reversed vector element of the input vector.
 */
public fun <T : VectorRevb<T>> vecRevb(a: T): T = a.vecRevb()

/**
 * Vector Merge High
 *
 * Merges the most significant ("high") halves of two vectors.
 */
public fun <Result, T : VectorMergeh<T, Result>> vecMergeh(a: T, b: T): T = a.vecMergeh(b)

/**
 * Vector Merge Low
 *
 * Merges the least significant ("low") halves of two vectors.
 */
public fun <Result, T : VectorMergel<T, Result>> vecMergel(a: T, b: T): T = a.vecMergel(b)

/**
 * Vector Pack
 */
public fun <Result, T : VectorPack<U, Result>, U> vecPack(a: T, b: U): Result = a.vecPack(b)

/**
 * Vector Pack Saturated
 */
public fun <Result, T : VectorPacks<U, Result>, U> vecPacks(a: T, b: U): Result = a.vecPacks(b)

/**
 * Vector Pack Saturated Condition Code
 */
public fun <Result, T : VectorPacksCC<T, Result>> vecPacksCc(a: T, b: T): Pair<Result, Int> = a.vecPacksCc(b)

/**
 * Vector Pack Saturated Unsigned
 */
public fun <Result, T : VectorPacksu<U, Result>, U> vecPacksu(a: T, b: U): Result = a.vecPacksu(b)

/**
 * Vector Pack Saturated Unsigned Condition Code
 */
public fun <Result, T : VectorPacksuCC<T, Result>> vecPacksuCc(a: T, b: T): Pair<Result, Int> = a.vecPacksuCc(b)

/**
 * Vector Unpack High
 */
public fun <Result, T : VectorUnpackh<Result>> vecUnpackh(a: T): Result = a.vecUnpackh()

/**
 * Vector Unpack Low
 */
public fun <Result, T : VectorUnpackl<Result>> vecUnpackl(a: T): Result = a.vecUnpackl()

/**
 * Vector Generate Byte Mask
 *
 * Generates byte masks for elements in the return vector. For each bit in a, if the bit is one, all bit positions
 * in the corresponding byte element of d are set to ones. Otherwise, if the bit is zero, the corresponding byte element is set to zero.
 */
public fun vecGenmask(mask: UShort): VectorUnsignedChar = VectorUnsignedChar(genmask(mask))

/**
 * Vector Generate Mask (Byte)
 */
public fun vecGenmasks8(l: UByte, h: UByte): VectorUnsignedChar = VectorUnsignedChar(UByteArray(16) { genmasks(8u, l, h).toUByte() })

/**
 * Vector Generate Mask (Halfword)
 */
public fun vecGenmasks16(l: UByte, h: UByte): VectorUnsignedShort = VectorUnsignedShort(UShortArray(8) { genmasks(16u, l, h).toUShort() })

/**
 * Vector Generate Mask (Word)
 */
public fun vecGenmasks32(l: UByte, h: UByte): VectorUnsignedInt = VectorUnsignedInt(UIntArray(4) { genmasks(32u, l, h).toUInt() })

/**
 * Vector Generate Mask (Doubleword)
 */
public fun vecGenmasks64(l: UByte, h: UByte): VectorUnsignedLongLong = VectorUnsignedLongLong(ULongArray(2) { genmasks(64u, l, h) })

/**
 * Vector Permute
 *
 * Returns a vector that contains some elements of two vectors, in the order specified by a third vector.
 * Each byte of the result is selected by using the least significant 5 bits of the corresponding byte of c as an index into the concatenated bytes of a and b.
 * Note: The vector generate mask built-in function [vecGenmask] could help generate the mask c.
 */
public fun <T : VectorPerm<T>> vecPerm(a: T, b: T, c: VectorUnsignedChar): T = a.vecPerm(b, c)

/**
 * Vector Sum Across Quadword
 *
 * Returns a vector containing the results of performing a sum across all the elements in each of the quadword of vector a,
 * and the rightmost word or doubleword element of the b. The result is an unsigned 128-bit integer.
 */
public fun <T : VectorSumU128<T>> vecSumU128(a: T, b: T): VectorUnsignedChar = a.vecSumU128(b)

/**
 * Vector Sum Across Doubleword
 *
 * Returns a vector containing the results of performing a sum across all the elements in each of the doubleword of vector a,
 * and the rightmost sub-element of the corresponding doubleword of b.
 */
public fun <T : VectorSum2<T>> vecSum2(a: T, b: T): VectorUnsignedLongLong = a.vecSum2(b)

/**
 * Vector Sum Across Word
 *
 * Returns a vector containing the results of performing a sum across all the elements in each of the word of vector a,
 * and the rightmost sub-element of the corresponding word of b.
 */
public fun <T : VectorSum4<T>> vecSum4(a: T, b: T): VectorUnsignedInt = a.vecSum4(b)

/**
 * Vector Addition unsigned 128-bits
 *
 * Adds unsigned quadword values.
 *
 * This function operates on the vectors as 128-bit unsigned integers. It returns low 128 bits of a + b.
 */
public fun vecAddU128(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    return transmuteWithLayouts<U128, VectorUnsignedChar>(a1.wrappingAdd(b1), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Subtract unsigned 128-bits
 *
 * Subtracts unsigned quadword values.
 *
 * This function operates on the vectors as 128-bit unsigned integers. It returns low 128 bits of a - b.
 */
public fun vecSubU128(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    return transmuteWithLayouts<U128, VectorUnsignedChar>(a1.wrappingSub(b1), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Subtract Carryout
 *
 * Returns a vector containing the borrow produced by subtracting each of corresponding elements of b from a.
 *
 * On each resulting element, the value is 0 if a borrow occurred, or 1 if no borrow occurred.
 */
public fun <Result, T : VectorSubc<U, Result>, U> vecSubc(a: T, b: U): Result = a.vecSubc(b)

/**
 * Vector Subtract Carryout unsigned 128-bits
 *
 * Gets the carry bit of the 128-bit subtraction of two quadword values.
 * This function operates on the vectors as 128-bit unsigned integers. It returns a vector containing the borrow produced by subtracting b from a, as unsigned 128-bits integers.
 * If no borrow occurred, the bit 127 of d is 1; otherwise it is set to 0. All other bits of d are 0.
 */
public fun vecSubcU128(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    return transmuteWithLayouts<U128, VectorUnsignedChar>((if (!a1.overflowingSub(b1).second) U128.ONE else U128.ZERO), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Add Compute Carryout unsigned 128-bits
 */
public fun vecAddcU128(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    return transmuteWithLayouts<U128, VectorUnsignedChar>(U128.fromLong((a1.overflowingAdd(b1).second).toLong()), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Add With Carry unsigned 128-bits
 */
public fun vecAddeU128(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val c1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(c, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    // Note(llvm) https://github.com/llvm/llvm-project/pull/153557
    // let (d, _carry) = a.carryingAdd(b, c & 1 != 0);
    // reinterpret(d)
    return transmuteWithLayouts<U128, VectorUnsignedChar>(vacq(a1, b1, c1), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Add With Carry Compute Carry unsigned 128-bits
 */
public fun vecAddecU128(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar {
    val a1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(a, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val b1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(b, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val c1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(c, VectorUnsignedChar.typeLayout(), UnresolvedTypeLayout<U128>())
    val (d, carry) = a1.carryingAdd(b1, c1 and U128(0x0uL, 0x1uL) != U128(0x0uL, 0x0uL))
    return transmuteWithLayouts<U128, VectorUnsignedChar>(U128.fromLong(carry.toLong()), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Subtract with Carryout
 *
 * Subtracts unsigned quadword values with carry bit from a previous operation.
 *
 * This function operates on the vectors as 128-bit unsigned integers. It returns a vector containing the result of subtracting of b from a,
 * and the carryout bit from a previous operation.
 *
 * Note: Only the borrow indication bit (127-bit) of c is used, and the other bits are ignored.
 */
public fun vecSubeU128(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vsbiq(a, b, c), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())

/**
 * Vector Subtract with Carryout, Carryout
 *
 * Gets the carry bit of the 128-bit subtraction of two quadword values with carry bit from the previous operation.
 *
 * It returns a vector containing the carryout produced from the result of subtracting of b from a,
 * and the carryout bit from a previous operation. If no borrow occurred, the 127-bit of d is 1, otherwise 0.
 * All other bits of d are 0.
 *
 * Note: Only the borrow indication bit (127-bit) of c is used, and the other bits are ignored.
 */
public fun vecSubecU128(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vsbcbiq(a, b, c), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())

/**
 * Vector Splat Signed Byte
 */
public fun vecSplatS8(imm: Byte): VectorSignedChar = VectorSignedChar(ByteArray(16) { imm })

/**
 * Vector Splat Signed Halfword
 */
public fun vecSplatS16(imm: Short): VectorSignedShort = VectorSignedShort(ShortArray(8) { imm })

/**
 * Vector Splat Signed Word
 */
public fun vecSplatS32(imm: Short): VectorSignedInt = VectorSignedInt(IntArray(4) { imm.toInt() })

/**
 * Vector Splat Signed Doubleword
 */
public fun vecSplatS64(imm: Short): VectorSignedLongLong = VectorSignedLongLong(LongArray(2) { imm.toLong() })

/**
 * Vector Splat Unsigned Byte
 */
public fun vecSplatU8(imm: UByte): VectorUnsignedChar = VectorUnsignedChar(UByteArray(16) { imm })

/**
 * Vector Splat Unsigned Halfword
 */
public fun vecSplatU16(imm: Short): VectorUnsignedShort = VectorUnsignedShort(UShortArray(8) { imm.toUShort() })

/**
 * Vector Splat Unsigned Word
 */
public fun vecSplatU32(imm: Short): VectorUnsignedInt = VectorUnsignedInt(UIntArray(4) { imm.toUInt() })

/**
 * Vector Splat Unsigned Doubleword
 */
public fun vecSplatU64(imm: Short): VectorUnsignedLongLong = VectorUnsignedLongLong(ULongArray(2) { imm.toULong() })

/**
 * Vector Find Any Element Equal with Condition Code
 */
public fun <Result, T : VectorFindAnyEq<U, Result>, U> vecFindAnyEq(a: T, b: U): Result = a.vecFindAnyEq(b)

/**
 * Vector Find Any Element Not Equal with Condition Code
 */
public fun <Result, T : VectorFindAnyNe<U, Result>, U> vecFindAnyNe(a: T, b: U): Result = a.vecFindAnyNe(b)

/**
 * Vector Find Any Element Equal Index with Condition Code
 */
public fun <Result, T : VectorFindAnyEqIdx<U, Result>, U> vecFindAnyEqIdx(a: T, b: U): Result = a.vecFindAnyEqIdx(b)

/**
 * Vector Find Any Element Not Equal Index with Condition Code
 */
public fun <Result, T : VectorFindAnyNeIdx<U, Result>, U> vecFindAnyNeIdx(a: T, b: U): Result = a.vecFindAnyNeIdx(b)

/**
 * Vector Find Any Element Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorFindAnyEqOrZeroIdx<U, Result>, U> vecFindAnyEqOr0Idx(a: T, b: U): Result = a.vecFindAnyEqOr0Idx(b)

/**
 * Vector Find Any Element Not Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorFindAnyNeOrZeroIdx<U, Result>, U> vecFindAnyNeOr0Idx(a: T, b: U): Result = a.vecFindAnyNeOr0Idx(b)

/**
 * Vector Find Any Element Equal with Condition Code
 */
public fun <Result, T : VectorFindAnyEqCC<U, Result>, U> vecFindAnyEqCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyEqCc(b)

/**
 * Vector Find Any Element Not Equal with Condition Code
 */
public fun <Result, T : VectorFindAnyNeCC<U, Result>, U> vecFindAnyNeCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyNeCc(b)

/**
 * Vector Find Any Element Equal Index with Condition Code
 */
public fun <Result, T : VectorFindAnyEqIdxCC<U, Result>, U> vecFindAnyEqIdxCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyEqIdxCc(b)

/**
 * Vector Find Any Element Not Equal Index with Condition Code
 */
public fun <Result, T : VectorFindAnyNeIdxCC<U, Result>, U> vecFindAnyNeIdxCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyNeIdxCc(b)

/**
 * Vector Find Any Element Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorFindAnyEqOrZeroIdxCC<U, Result>, U> vecFindAnyEqOr0IdxCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyEqOr0IdxCc(b)

/**
 * Vector Find Any Element Not Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorFindAnyNeOrZeroIdxCC<U, Result>, U> vecFindAnyNeOr0IdxCc(a: T, b: U): Pair<Result, Int> = a.vecFindAnyNeOr0IdxCc(b)

/**
 * Vector Load
 */
public fun <T : VectorLoad<T, ElementType>> vecXl(offset: Int, ptr: ConstPtr, vecXlTFn: (Int, ConstPtr) -> T): T = vecXlTFn(offset, ptr)

/**
 * Vector Load Pair
 */
public fun <ElementType, T : VectorLoadPair<T, ElementType>> vecLoadPair(a: ElementType, b: ElementType, vecLoadPairTFn: (ElementType, ElementType) -> T): T = vecLoadPairTFn(a, b)

/**
 * Vector Load to Block Boundary
 */
public fun <T : VectorLoad<T, ElementType>> vecLoadBndry(ptr: ConstPtr, blockBoundary: UShort, vecLoadBndryTFn: (ConstPtr) -> MaybeUninit<T>): MaybeUninit<T> = vecLoadBndryTFn(ptr, blockBoundary)

/**
 * Vector Store
 */
public fun <T : VectorStore<ElementType>> vecXst(vector: T, offset: Int, ptr: MutPtr) {
    vector.vecXst(offset, ptr)
}

/**
 * Vector Load with Length
 */
public fun <T : VectorLoad<T, ElementType>> vecLoadLen(ptr: ConstPtr, byteCount: UInt, vecLoadLenTFn: (ConstPtr, UInt) -> T): T = vecLoadLenTFn(ptr, byteCount)

/**
 * Vector Store with Length
 */
public fun <T : VectorStore<ElementType>> vecStoreLen(vector: T, ptr: MutPtr, byteCount: UInt) {
    vector.vecStoreLen(ptr, byteCount)
}

/**
 * Vector Load Rightmost with Length
 */
public fun vecLoadLenR(ptr: ConstPtr, byteCount: UInt): VectorUnsignedChar = vlrl(byteCount, ptr)

/**
 * Vector Store Rightmost with Length
 */
public fun vecStoreLenR(vector: VectorUnsignedChar, ptr: MutPtr, byteCount: UInt) {
    vstrl(vector, byteCount, ptr)
}

/**
 * Vector Multiply Add
 */
public fun <T : VectorMadd<T>> vecMadd(a: T, b: T, c: T): T = a.vecMadd(b, c)

/**
 * Vector Multiply Add
 */
public fun <T : VectorMadd<T>> vecMsub(a: T, b: T, c: T): T = a.vecMsub(b, c)

/**
 * Vector Multiply and Add Even
 */
public fun <Result, T : VectorMeadd<T, Result>> vecMeadd(a: T, b: T, c: Result): Result = a.vecMeadd(b, c)

/**
 * Vector Multiply and Add Odd
 */
public fun <Result, T : VectorMoadd<T, Result>> vecMoadd(a: T, b: T, c: Result): Result = a.vecMoadd(b, c)

/**
 * Vector Multiply and Add High
 */
public fun <Result, T : VectorMhadd<T, Result>> vecMhadd(a: T, b: T, c: Result): Result = a.vecMhadd(b, c)

/**
 * Vector Multiply and Add Low
 */
public fun <Result, T : VectorMladd<T, Result>> vecMladd(a: T, b: T, c: Result): Result = a.vecMladd(b, c)

/**
 * Vector Checksum
 */
public fun vecChecksum(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vcksm(a, b)

/**
 * Vector Multiply Even
 */
public fun <T : VectorMule<U, T>, U> vecMule(a: T, b: T): U = a.vecMule(b)

/**
 * Vector Multiply Odd
 */
public fun <T : VectorMulo<U, T>, U> vecMulo(a: T, b: T): U = a.vecMulo(b)

/**
 * Vector Multiply High
 */
public fun <T : VectorMulh<U, T>, U> vecMulh(a: T, b: T): U = a.vecMulh(b)

/**
 * Vector Galois Field Multiply Sum
 */
public fun <T : VectorGfmsum<U, T>, U> vecGfmsum(a: T, b: T): U = a.vecGfmsum(b)

/**
 * Vector Galois Field Multiply Sum
 */
public fun <Result, T : VectorGfmsumAccum<T, Result>> vecGfmsumAccum(a: T, b: T, c: Result): Result = a.vecGfmsumAccum(b, c)

/**
 * Vector Galois Field Multiply Sum 128-bits
 */
public fun vecGfmsum128(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vgfmg(a, b), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())

/**
 * Vector Galois Field Multiply Sum and Accumulate 128-bits
 */
public fun vecGfmsumAccum128(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(vgfmag(a, b, c), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())

/**
 * Vector Bit Permute
 */
public fun vecBpermU128(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedLongLong = vbperm(a, b)

/**
 * Vector Gather Element
 */
public fun <Offset, T : VectorGatherElement<T, Element, Offset>> vecGatherElement(a: T, b: Offset, c: ConstPtr, d: UInt): T = a.vecGatherElement(b, c)

/**
 * Vector Select
 */
public fun <T : VectorSel<U, T>, U> vecSel(a: T, b: T, c: U): T = a.vecSel(b, c)

public val VEC_CLASS_FP_ZERO_P: UInt = 1u shl 11

public val VEC_CLASS_FP_ZERO_N: UInt = 1u shl 10

public val VEC_CLASS_FP_ZERO: UInt = VEC_CLASS_FP_ZERO_P or VEC_CLASS_FP_ZERO_N

public val VEC_CLASS_FP_NORMAL_P: UInt = 1u shl 9

public val VEC_CLASS_FP_NORMAL_N: UInt = 1u shl 8

public val VEC_CLASS_FP_NORMAL: UInt = VEC_CLASS_FP_NORMAL_P or VEC_CLASS_FP_NORMAL_N

public val VEC_CLASS_FP_SUBNORMAL_P: UInt = 1u shl 7

public val VEC_CLASS_FP_SUBNORMAL_N: UInt = 1u shl 6

public val VEC_CLASS_FP_SUBNORMAL: UInt = VEC_CLASS_FP_SUBNORMAL_P or VEC_CLASS_FP_SUBNORMAL_N

public val VEC_CLASS_FP_INFINITY_P: UInt = 1u shl 5

public val VEC_CLASS_FP_INFINITY_N: UInt = 1u shl 4

public val VEC_CLASS_FP_INFINITY: UInt = VEC_CLASS_FP_INFINITY_P or VEC_CLASS_FP_INFINITY_N

public val VEC_CLASS_FP_QNAN_P: UInt = 1u shl 3

public val VEC_CLASS_FP_QNAN_N: UInt = 1u shl 2

public val VEC_CLASS_FP_QNAN: UInt = VEC_CLASS_FP_QNAN_P or VEC_CLASS_FP_QNAN_N

public val VEC_CLASS_FP_SNAN_P: UInt = 1u shl 1

public val VEC_CLASS_FP_SNAN_N: UInt = 1u shl 0

public val VEC_CLASS_FP_SNAN: UInt = VEC_CLASS_FP_SNAN_P or VEC_CLASS_FP_SNAN_N

public val VEC_CLASS_FP_NAN: UInt = VEC_CLASS_FP_QNAN or VEC_CLASS_FP_SNAN

public val VEC_CLASS_FP_NOT_NORMAL: UInt = VEC_CLASS_FP_NAN or VEC_CLASS_FP_SUBNORMAL or VEC_CLASS_FP_ZERO or VEC_CLASS_FP_INFINITY

/**
 * Vector Floating-Point Test Data Class
 *
 * You can use the `__VEC_CLASS_FP_ *` constants as the argument for this operand
 */
public fun <Result, T : VectorFpTestDataClass<Result>> vecFpTestDataClass(a: T, c: MutPtr, cls: UInt): Result {
    val (x, y) = a.vecFpTestDataClass()
    (PrimitiveLayouts.I32).writeNative((c).rawAddress, y)
    return x
}

/**
 * All Elements Not a Number
 */
public fun <Result, T : VectorFpTestDataClass<Result>> vecAllNan(a: T): Int = Int.from(a.vecFpTestDataClass().second == 0)

/**
 * All Elements Numeric
 */
public fun <Result, T : VectorFpTestDataClass<Result>> vecAllNumeric(a: T): Int = Int.from(a.vecFpTestDataClass().second == 3)

/**
 * Any Elements Not a Number
 */
public fun <Result, T : VectorFpTestDataClass<Result>> vecAnyNan(a: T): Int = Int.from(a.vecFpTestDataClass().second != 3)

/**
 * Any Elements Numeric
 */
public fun <Result, T : VectorFpTestDataClass<Result>> vecAnyNumeric(a: T): Int = Int.from(a.vecFpTestDataClass().second != 0)

/**
 * Vector Test under Mask
 */
public fun <Mask, T : VectorTestMask<Mask>> vecTestMask(a: T, b: Mask): Int {
    // I can't find much information about this, but this might just be a check for whether the
    // bitwise and of a and b is non-zero?
    return a.vecTestMask(b)
}

/**
 * Vector Search String
 */
public fun <T : VectorSearchString<T>> vecSearchStringCc(a: T, b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> = a.vecSearchStringCc(b, c)

/**
 * Vector Search String Until Zero
 */
public fun <T : VectorSearchString<T>> vecSearchStringUntilZeroCc(a: T, b: T, c: VectorUnsignedChar): Pair<VectorUnsignedChar, Int> = a.vecSearchStringUntilZeroCc(b, c)

// NOTE: `vflls` and `vldeb` are equivalent; our disassmbler prefers vflls.

/**
 * Vector Convert from float (even elements) to double
 */
public fun vecDoublee(a: VectorFloat): VectorDouble = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); res } }; VectorDouble() }

// Note: the C version uses a shuffle mask with poison; we can't do that
// @cfgAttr(all(test, targetFeature = "vector-enhancements-1"), assertInstr(vledb))

/**
 * Vector Convert from double to float (even elements)
 */
public fun vecFloate(a: VectorDouble): VectorFloat {
    val truncated: F32x2 = run { val simdArg0 = a; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); res } }
    return run { val simdArg0 = truncated; val simdArg1 = truncated; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 1u, 1u)); VectorFloat() }
}

/**
 * Vector Convert from int to float
 */
public fun vecFloat(a: io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorFloat): VectorFloat = a.vecFloat()

/**
 * Vector Convert from long long to double
 */
public fun vecDouble(a: io.github.kotlinmania.rustlang.corearch.s390x.vector.sealed.VectorDouble): VectorDouble = a.vecDouble()

/**
 * Vector Sign Extend to Doubleword
 */
public fun vecExtendS64(a: VectorExtendSigned64): VectorSignedLongLong = a.vecExtendS64()

/**
 * Vector Convert floating point to signed
 */
public fun <Result, T : VectorSigned<Result>> vecSigned(a: T): Result = a.vecSigned()

/**
 * Vector Convert floating point to unsigned
 */
public fun <Result, T : VectorUnsigned<Result>> vecUnsigned(a: T): Result = a.vecUnsigned()

/**
 * Vector Copy Until Zero
 */
public fun <T : VectorCopyUntilZero<T>> vecCpUntilZero(a: T): T = a.vecCpUntilZero()

/**
 * Vector Copy Until Zero
 */
public fun <T : VectorCopyUntilZeroCC<T>> vecCpUntilZeroCc(a: T): Pair<T, Int> = a.vecCpUntilZeroCc()

/**
 * Vector Multiply Sum Logical
 */
public fun vecMsumU128(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: VectorUnsignedChar, d: UInt): VectorUnsignedChar {
    if (!when (d) {
        0u, 4u, 8u, 12u -> true
        else -> false
    }) {
        error("D needs to be one of 0, 4, 8, 12")
    }
    return transmuteWithLayouts<U128, VectorUnsignedChar>(vmslg(a, b, c, d), UnresolvedTypeLayout<U128>(), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Shift Left Double by Byte
 */
public fun <T : VectorSld<T>> vecSld(a: T, b: T, c: UInt): T {
    run {
        check(0u <= c && c < (1u shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return a.vecSld(b)
}

/**
 * Vector Shift Left Double by Word
 */
public fun <T : VectorSld<T>> vecSldw(a: T, b: T, c: UInt): T {
    run {
        check(0u <= c && c < (1u shl 2)) { "C doesn't fit in 2 bits (unsigned)" }
    }
    return a.vecSldw(b)
}

/**
 * Vector Shift Left Double by Bit
 */
public fun <T : VectorSld<T>> vecSldb(a: T, b: T, c: UInt): T {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return a.vecSldb(b)
}

/**
 * Vector Shift Right Double by Bit
 */
public fun <T : VectorSrdb<T>> vecSrdb(a: T, b: T, c: UInt): T {
    run {
        check(0u <= c && c < (1u shl 3)) { "C doesn't fit in 3 bits (unsigned)" }
    }
    return a.vecSrdb(b)
}

/**
 * Vector Compare Ranges
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprg(a: T, b: T, c: T): Result = a.vstrc(b, c)

/**
 * Vector Compare Not in Ranges
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrg(a: T, b: T, c: T): Result = a.vstrc(b, c)

/**
 * Vector Compare Ranges Index
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprgIdx(a: T, b: T, c: T): Result = a.vstrc(b, c)

/**
 * Vector Compare Not in Ranges Index
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrgIdx(a: T, b: T, c: T): Result = a.vstrc(b, c)

/**
 * Vector Compare Ranges with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprgCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrcs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Not in Ranges with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrgCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrcs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Ranges Index with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprgIdxCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrcs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Not in Ranges Index with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrgIdxCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrcs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Ranges or Zero Index
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprgOr0Idx(a: T, b: T, c: T): Result = a.vstrcz(b, c)

/**
 * Vector Compare Not in Ranges or Zero Index
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrgOr0Idx(a: T, b: T, c: T): Result = a.vstrcz(b, c)

/**
 * Vector Compare Ranges or Zero Index with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmprgOr0IdxCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrczs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Not in Ranges or Zero Index with Condition Code
 */
public fun <Result, T : VectorCompareRange<T, Result>> vecCmpnrgOr0IdxCc(a: T, b: T, c: T, d: MutPtr): Result {
    val (x, y) = a.vstrczs(b, c)
    (PrimitiveLayouts.I32).writeNative((d).rawAddress, y)
    return x
}

/**
 * Vector Compare Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecCmpeq(a: T, b: T): Result = a.vecCmpeq(b)

/**
 * Vector Compare Not Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecCmpne(a: T, b: T): Result = a.vecCmpne(b)

/**
 * Vector Compare Greater Than
 */
public fun <Result, T : VectorComparePredicate<T, Result>> vecCmpgt(a: T, b: T): Result = a.vecCmpgt(b)

/**
 * Vector Compare Greater Than or Equal
 */
public fun <Result, T : VectorComparePredicate<T, Result>> vecCmpge(a: T, b: T): Result = a.vecCmpge(b)

/**
 * Vector Compare Less
 */
public fun <Result, T : VectorComparePredicate<T, Result>> vecCmplt(a: T, b: T): Result = a.vecCmplt(b)

/**
 * Vector Compare Less Than or Equal
 */
public fun <Result, T : VectorComparePredicate<T, Result>> vecCmple(a: T, b: T): Result = a.vecCmple(b)

/**
 * Vector Compare Equal Index
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpeqIdx(a: T, b: T): Result = a.vecCmpeqIdx(b)

/**
 * Vector Compare Not Equal Index
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpneIdx(a: T, b: T): Result = a.vecCmpneIdx(b)

/**
 * Vector Compare Equal Index with Condition Code
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpeqIdxCc(a: T, b: T): Pair<Result, Int> = a.vecCmpeqIdxCc(b)

/**
 * Vector Compare Not Equal Index with Condition Code
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpneIdxCc(a: T, b: T): Pair<Result, Int> = a.vecCmpneIdxCc(b)

/**
 * Vector Compare Equal or Zero Index
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpeqOr0Idx(a: T, b: T): Result = a.vecCmpeqOr0Idx(b)

/**
 * Vector Compare Not Equal or Zero Index
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpneOr0Idx(a: T, b: T): Result = a.vecCmpneOr0Idx(b)

/**
 * Vector Compare Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpeqOr0IdxCc(a: T, b: T): Pair<Result, Int> = a.vecCmpeqOr0IdxCc(b)

/**
 * Vector Compare Not Equal or Zero Index with Condition Code
 */
public fun <Result, T : VectorEqualityIdx<T, Result>> vecCmpneOr0IdxCc(a: T, b: T): Pair<Result, Int> = a.vecCmpneOr0IdxCc(b)

/**
 * All Elements Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecAllEq(a: T, b: T): Int = (run { val simdArg0 = vecCmpeq(a, b); false }).toInt()

/**
 * All Elements Not Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecAllNe(a: T, b: T): Int = (run { val simdArg0 = vecCmpne(a, b); false }).toInt()

/**
 * Any Element Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecAnyEq(a: T, b: T): Int = (run { val simdArg0 = vecCmpeq(a, b); false }).toInt()

/**
 * Any Element Not Equal
 */
public fun <Result, T : VectorEquality<T, Result>> vecAnyNe(a: T, b: T): Int = (run { val simdArg0 = vecCmpne(a, b); false }).toInt()

/**
 * All Elements Less Than
 */
public fun <T : VectorCompare<T>> vecAllLt(a: T, b: T): Int = a.vecAllLt(b)

/**
 * All Elements Less Than or Equal
 */
public fun <T : VectorCompare<T>> vecAllLe(a: T, b: T): Int = a.vecAllLe(b)

/**
 * All Elements Greater Than
 */
public fun <T : VectorCompare<T>> vecAllGt(a: T, b: T): Int = a.vecAllGt(b)

/**
 * All Elements Greater Than or Equal
 */
public fun <T : VectorCompare<T>> vecAllGe(a: T, b: T): Int = a.vecAllGe(b)

/**
 * All Elements Not Less Than
 */
public fun <T : VectorCompare<T>> vecAllNlt(a: T, b: T): Int = vecAllGe(a, b)

/**
 * All Elements Not Less Than or Equal
 */
public fun <T : VectorCompare<T>> vecAllNle(a: T, b: T): Int = vecAllGt(a, b)

/**
 * All Elements Not Greater Than
 */
public fun <T : VectorCompare<T>> vecAllNgt(a: T, b: T): Int = vecAllLe(a, b)

/**
 * All Elements Not Greater Than or Equal
 */
public fun <T : VectorCompare<T>> vecAllNge(a: T, b: T): Int = vecAllLt(a, b)

/**
 * Any Elements Less Than
 */
public fun <T : VectorCompare<T>> vecAnyLt(a: T, b: T): Int = vecAllGe(a, b).inv()

/**
 * Any Elements Less Than or Equal
 */
public fun <T : VectorCompare<T>> vecAnyLe(a: T, b: T): Int = vecAllGt(a, b).inv()

/**
 * Any Elements Greater Than
 */
public fun <T : VectorCompare<T>> vecAnyGt(a: T, b: T): Int = vecAllLe(a, b).inv()

/**
 * Any Elements Greater Than or Equal
 */
public fun <T : VectorCompare<T>> vecAnyGe(a: T, b: T): Int = vecAllLt(a, b).inv()

/**
 * Any Elements Not Less Than
 */
public fun <T : VectorCompare<T>> vecAnyNlt(a: T, b: T): Int = vecAnyGe(a, b)

/**
 * Any Elements Not Less Than or Equal
 */
public fun <T : VectorCompare<T>> vecAnyNle(a: T, b: T): Int = vecAnyGt(a, b)

/**
 * Any Elements Not Greater Than
 */
public fun <T : VectorCompare<T>> vecAnyNgt(a: T, b: T): Int = vecAnyLe(a, b)

/**
 * Any Elements Not Greater Than or Equal
 */
public fun <T : VectorCompare<T>> vecAnyNge(a: T, b: T): Int = vecAnyLt(a, b)

/**
 * Vector Extract
 */
public fun <ElementType, T : VectorExtract<T, ElementType>> vecExtract(a: T, b: Int, vecExtractTFn: (T, Int) -> ElementType): ElementType = vecExtractTFn(a, b)

/**
 * Vector Insert
 */
public fun <ElementType, T : VectorInsert<T, ElementType>> vecInsert(a: ElementType, b: T, c: Int, vecInsertTFn: (ElementType, T, Int) -> T): T = vecInsertTFn(a, b, c)

/**
 * Vector Insert and Zero
 */
public fun <T : VectorInsertAndZero<T, ElementType>> vecInsertAndZero(a: ConstPtr, vecInsertAndZeroTFn: (ConstPtr) -> T): T = vecInsertAndZeroTFn(a)

/**
 * Vector Promote
 */
public fun <ElementType, T : VectorPromote<T, ElementType>> vecPromote(a: ElementType, b: Int, vecPromoteTFn: (ElementType, Int) -> MaybeUninit<T>): MaybeUninit<T> = vecPromoteTFn(a, b)

/**
 * Converts the left-most half of `a` to a vector of single-precision numbers.
 * The format of the source vector elements is specified by `B`.
 */
public fun vecExtendToFp32Hi(a: VectorSignedShort, b: Int): VectorFloat {
    run {
        check(0 <= b && b < (1 shl 4)) { "B doesn't fit in 4 bits (unsigned)" }
    }
    return vclfnhs(a, b)
}

/**
 * Converts the right-most half of `a` to a vector of single-precision numbers.
 * The format of the source vector elements is specified by `B`.
 */
public fun vecExtendToFp32Lo(a: VectorSignedShort, b: Int): VectorFloat {
    run {
        check(0 <= b && b < (1 shl 4)) { "B doesn't fit in 4 bits (unsigned)" }
    }
    return vclfnls(a, b)
}

/**
 * Converts the elements of vector `a` to the 16-bit IEEE floating point format.
 * The format of the source vector elements is specified by `B`.
 */
public fun vecConvertToFp16(a: VectorSignedShort, b: Int): VectorSignedShort {
    run {
        check(0 <= b && b < (1 shl 4)) { "B doesn't fit in 4 bits (unsigned)" }
    }
    return vcfn(a, b)
}

/**
 * Converts the elements of vector `a` to an internal floating point format.
 * The format of the target vector elements is specified by `B`.
 */
public fun vecConvertFromFp16(a: VectorSignedShort, b: Int): VectorSignedShort {
    run {
        check(0 <= b && b < (1 shl 4)) { "B doesn't fit in 4 bits (unsigned)" }
    }
    return vcnf(a, b)
}

/**
 * Converts the elements of single-precision vectors `a` and `b` to an internal floating point
 * format with 16-bit sized elements. The format of the target vector elements is specified by `C`.
 */
public fun vecRoundFromFp32(a: VectorFloat, b: VectorFloat, c: Int): VectorSignedShort {
    run {
        check(0 <= c && c < (1 shl 4)) { "C doesn't fit in 4 bits (unsigned)" }
    }
    return vcrnfs(a, b, c)
}

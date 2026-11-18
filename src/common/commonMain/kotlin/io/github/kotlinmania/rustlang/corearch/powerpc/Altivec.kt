// port-lint: source powerpc/altivec.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// PowerPC AltiVec intrinsics.
//
// AltiVec is a brandname trademarked by Freescale (previously Motorola) for
// the standard `Category: Vector` part of the Power ISA v.2.03 specification.
// This Category is also known as VMX (used by IBM), and "Velocity Engine" (a
// brand name previously used by Apple).
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

/**
 * PowerPC-specific 128-bit wide vector of sixteen packed `Byte`
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
 * PowerPC-specific 128-bit wide vector of sixteen packed `UByte`
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
 * PowerPC-specific 128-bit wide vector mask of sixteen packed elements
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
 * PowerPC-specific 128-bit wide vector of eight packed `Short`
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
 * PowerPC-specific 128-bit wide vector of eight packed `UShort`
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
 * PowerPC-specific 128-bit wide vector mask of eight packed elements
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
 * PowerPC-specific 128-bit wide vector of four packed `Int`
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
 * PowerPC-specific 128-bit wide vector of four packed `UInt`
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
 * PowerPC-specific 128-bit wide vector mask of four packed elements
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
 * PowerPC-specific 128-bit wide vector of four packed `Float`
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

public fun fromM8x16(value: VectorBoolChar): M8x16 = transmuteWithLayouts<VectorBoolChar, M8x16>(value, VectorBoolChar.typeLayout(), UnresolvedTypeLayout<M8x16>())

public fun fromM16x8(value: VectorBoolShort): M16x8 = transmuteWithLayouts<VectorBoolShort, M16x8>(value, VectorBoolShort.typeLayout(), UnresolvedTypeLayout<M16x8>())

public fun fromM32x4(value: VectorBoolInt): M32x4 = transmuteWithLayouts<VectorBoolInt, M32x4>(value, VectorBoolInt.typeLayout(), UnresolvedTypeLayout<M32x4>())

public expect fun lvx(p: ConstPtr): VectorUnsignedInt

public expect fun lvebx(p: ConstPtr): VectorSignedChar

public expect fun lvehx(p: ConstPtr): VectorSignedShort

public expect fun lvewx(p: ConstPtr): VectorSignedInt

public expect fun lvxl(p: ConstPtr): VectorUnsignedInt

public expect fun stvx(a: VectorSignedInt, p: ConstPtr)

public expect fun stvebx(a: VectorSignedChar, p: ConstPtr)

public expect fun stvehx(a: VectorSignedShort, p: ConstPtr)

public expect fun stvewx(a: VectorSignedInt, p: ConstPtr)

public expect fun stvxl(a: VectorSignedInt, p: ConstPtr)

public expect fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt

public expect fun vmhaddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort

public expect fun vmhraddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort

public expect fun vmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt

public expect fun vmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt

public expect fun vmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt

public expect fun vmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt

public expect fun vsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt

public expect fun vsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt

public expect fun vmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort

public expect fun vmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort

public expect fun vmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt

public expect fun vmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt

public expect fun vmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort

public expect fun vmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort

public expect fun vmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt

public expect fun vmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt

public expect fun vmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar

public expect fun vavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort

public expect fun vavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort

public expect fun vavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt

public expect fun vcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt

public expect fun vcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar

public expect fun vcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort

public expect fun vcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt

public expect fun vcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar

public expect fun vcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort

public expect fun vcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt

public expect fun vcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt

public expect fun vcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar

public expect fun vcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort

public expect fun vcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt

public expect fun vcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar

public expect fun vcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort

public expect fun vcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt

public expect fun vexptefp(a: VectorFloat): VectorFloat

public expect fun vcmpequbP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int

public expect fun vcmpequhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int

public expect fun vcmpequwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int

public expect fun vcmpeqfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int

public expect fun vcmpgtubP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int

public expect fun vcmpgtuhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int

public expect fun vcmpgtuwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int

public expect fun vcmpgtsbP(cr: Int, a: VectorSignedChar, b: VectorSignedChar): Int

public expect fun vcmpgtshP(cr: Int, a: VectorSignedShort, b: VectorSignedShort): Int

public expect fun vcmpgtswP(cr: Int, a: VectorSignedInt, b: VectorSignedInt): Int

public expect fun vcmpgefpP(cr: Int, a: VectorFloat, b: VectorFloat): Int

public expect fun vcmpgtfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int

public expect fun vcmpbfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int

public expect fun vcfsx(a: VectorSignedInt, b: Int): VectorFloat

public expect fun vcfux(a: VectorUnsignedInt, b: Int): VectorFloat

public expect fun vctsxs(a: VectorFloat, b: Int): VectorSignedInt

public expect fun vctuxs(a: VectorFloat, b: Int): VectorUnsignedInt

public expect fun vpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar

public expect fun vpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar

public expect fun vpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar

public expect fun vpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort

public expect fun vpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort

public expect fun vpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort

public expect fun vupkhsb(a: VectorSignedChar): VectorSignedShort

public expect fun vupklsb(a: VectorSignedChar): VectorSignedShort

public expect fun vupkhsh(a: VectorSignedShort): VectorSignedInt

public expect fun vupklsh(a: VectorSignedShort): VectorSignedInt

public expect fun mfvscr(): VectorUnsignedShort

public expect fun vlogefp(a: VectorFloat): VectorFloat

public expect fun vsl(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vslo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vsrab(a: VectorSignedChar, b: VectorUnsignedChar): VectorSignedChar

public expect fun vsrah(a: VectorSignedShort, b: VectorUnsignedShort): VectorSignedShort

public expect fun vsraw(a: VectorSignedInt, b: VectorUnsignedInt): VectorSignedInt

public expect fun vsr(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vsro(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt

public expect fun vslv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vsrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar

public expect fun vrfin(a: VectorFloat): VectorFloat

/**
 * Vector Insert
 *
 * ## Purpose
 * Returns a copy of vector b with element c replaced by the value of a.
 *
 * ## Result value
 * r contains a copy of vector b with element c replaced by the value of a.
 * This function uses modular arithmetic on c to determine the element number.
 * For example, if c is out of range, the compiler uses c modulo the number of
 * elements in the vector to determine the element position.
 */
public fun <Scalar, T> vecInsert(a: T, b: Scalar, idx: UInt): T where T : VectorInsert<T, Scalar> = a.vecInsert(b, idx)

/**
 * Vector Extract
 *
 * ## Purpose
 * Returns the value of the bth element of vector a.
 *
 * ## Result value
 * The value of each element of r is the element of a at position b modulo the number of
 * elements of a.
 */
public fun <Scalar, T> vecExtract(a: T, idx: UInt): Scalar where T : VectorExtract<T, Scalar> = a.vecExtract(idx)

/**
 * Vector Merge Low
 */
public fun <Result, T, U> vecMergel(a: T, b: U): Result where T : VectorMergel<U, T, Result> = a.vecMergel(b)

/**
 * Vector Merge High
 */
public fun <Result, T, U> vecMergeh(a: T, b: U): Result where T : VectorMergeh<U, T, Result> = a.vecMergeh(b)

/**
 * Vector Pack
 */
public fun <Result, T, U> vecPack(a: T, b: U): Result where T : VectorPack<U, Result> = a.vecPack(b)

/**
 * Vector Pack Saturated
 */
public fun <Result, T, U> vecPacks(a: T, b: U): Result where T : VectorPacks<U, Result> = a.vecPacks(b)

/**
 * Vector Pack Saturated Unsigned
 */
public fun <Result, T, U> vecPacksu(a: T, b: U): Result where T : VectorPacksu<U, Result> = a.vecPacksu(b)

/**
 * Vector Unpack High
 */
public fun <Result, T> vecUnpackh(a: T): Result where T : VectorUnpackh<Result> = a.vecUnpackh()

/**
 * Vector Unpack Low
 */
public fun <Result, T> vecUnpackl(a: T): Result where T : VectorUnpackl<Result> = a.vecUnpackl()

/**
 * Vector Shift Left
 */
public fun <Result, T, U> vecSl(a: T, b: U): Result where T : VectorSl<U, Result> = a.vecSl(b)

/**
 * Vector Shift Right
 */
public fun <Result, T, U> vecSr(a: T, b: U): Result where T : VectorSr<U, Result> = a.vecSr(b)

/**
 * Vector Shift Right Algebraic
 */
public fun <Result, T, U> vecSra(a: T, b: U): Result where T : VectorSra<U, Result> = a.vecSra(b)

/**
 * Vector Shift Left Double
 *
 * ## Endian considerations
 *
 * This intrinsic is not endian-neutral, so uses of vecSld in
 * big-endian code must be rewritten for little-endian targets.
 *
 * Historically, vecSld could be used to shift by amounts not a multiple of the element size
 * for most types, in which case the purpose of the shift is difficult to determine and difficult
 * to automatically rewrite efficiently for little endian.
 *
 * So the concatenation of a and b is done in big-endian fashion (left to right), and the shift is
 * always to the left. This will generally produce surprising results for little-endian targets.
 */
public fun <T> vecSld(a: T, b: T, uimm4: Int): T where T : VectorSld<T> = a.vecSld(b, uimm4)

/**
 * Vector Shift Left Double by Words
 *
 * ## Endian considerations
 *
 * This intrinsic is not endian-neutral, so uses of vecSldw in
 * big-endian code must be rewritten for little-endian targets.
 *
 * The concatenation of a and b is done in big-endian fashion (left to right), and the shift is
 * always to the left. This will generally produce surprising results for little- endian targets.
 */
public fun <T> vecSldw(a: T, b: T, uimm2: Int): T where T : VectorSld<T> = a.vecSldw(b, uimm2)

/**
 * Vector Shift Left Long
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSll in big-endian
 * code must be rewritten for little-endian targets.
 */
public fun <Result, T, U> vecSll(a: T, b: U): Result where T : VectorSll<U, Result> = a.vecSll(b)

/**
 * Vector Shift Right Long
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSrl in big-endian
 * code must be rewritten for little-endian targets.
 */
public fun <Result, T, U> vecSrl(a: T, b: U): Result where T : VectorSrl<U, Result> = a.vecSrl(b)

/**
 * Vector Shift Left by Octets
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSlo in big-endian code must be rewritten
 * for little-endian targets. The shift count is in element 15 of b for big-endian, but in element
 * 0 of b for little-endian.
 */
public fun <Result, T, U> vecSlo(a: T, b: U): Result where T : VectorSlo<U, Result> = a.vecSlo(b)

/**
 * Vector Shift Right by Octets
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSro in big-endian code must be rewritten
 * for little-endian targets. The shift count is in element 15 of b for big-endian, but in element
 * 0 of b for little-endian.
 */
public fun <Result, T, U> vecSro(a: T, b: U): Result where T : VectorSro<U, Result> = a.vecSro(b)

/**
 * Vector Shift Left Variable
 *
 * ## Result value
 * Let v be a 17-byte vector formed from a in bytes `[0: 15]` and a zero byte in element 16.
 * Then each byte element i of r is determined as follows. The start bit sb is
 * obtained from bits 5:7 of byte element i of b. Then the contents of bits sb:sb+7 of the
 * halfword in byte elements i:i+1 of v are placed into byte element i of r.
 *
 * ## Endian considerations
 * All bit and byte element numbers are specified in big-endian order. This intrinsic is not
 * endian-neutral.
 */
public fun vecSlv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vslv(a, b)

/**
 * Vector Shift Right Variable
 *
 * ## Result value
 * Let v be a 17-byte vector formed from a zero byte in element 0 and the elements of
 * a in bytes `[1: 16]`. Then each byte element i of r is determined as follows. The start bit sb is
 * obtained from bits 5:7 of byte element i of b. Then the contents of bits (8 – sb):(15 – sb) of
 * the halfword in byte elements i:i+1 of v are placed into byte element i of r.
 *
 * ## Endian considerations
 * All bit and byte element numbers are specified in big-endian order. This intrinsic is not
 * endian-neutral.
 */
public fun vecSrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vsrv(a, b)

/**
 * Vector Load Indexed.
 */
public fun <Result, T> vecLd(off: Int, p: T): Result where T : VectorLd<Result> = p.vecLd(off)

/**
 * Vector Load Indexed Least Recently Used.
 */
public fun <Result, T> vecLdl(off: Int, p: T): Result where T : VectorLd<Result> = p.vecLdl(off)

/**
 * Vector Load Element Indexed.
 */
public fun <Result, T> vecLde(off: Int, p: T): Result where T : VectorLde<Result> = p.vecLde(off)

/**
 * Vector Store Indexed
 *
 * ## Purpose
 * Stores a 16-byte vector into memory at the address specified by a displacement and a
 * pointer, ignoring the four low-order bits of the calculated address.
 *
 * ## Operation
 * A memory address is obtained by adding b and c, and masking off the four low-order
 * bits of the result. The 16-byte vector in a is stored to the resultant memory address.
 */
public fun <Target, T> vecSt(a: T, off: Int, c: Target) where T : VectorSt<Target> {
    a.vecSt(off, c)
}

/**
 * Vector Store Indexed Least Recently Used
 *
 * ## Purpose
 * Stores a 16-byte vector into memory at the address specified by a displacement and
 * a pointer, ignoring the four low-order bits of the calculated address, and marking the cache
 * line containing the address as least frequently used.
 *
 * ## Operation
 * A memory address is obtained by adding b and c, and masking off the four
 * low-order bits of the result. The 16-byte vector in a is stored to the resultant memory
 * address, and the containing cache line is marked as least frequently used.
 *
 * ## Notes
 * This intrinsic can be used to indicate the last access to a portion of memory, as a hint to the
 * data cache controller that the associated cache line can be replaced without performance loss.
 */
public fun <Target, T> vecStl(a: T, off: Int, c: Target) where T : VectorSt<Target> {
    a.vecStl(off, c)
}

/**
 * Vector Store Element Indexed
 *
 * ## Purpose
 * Stores a single element from a 16-byte vector into memory at the address specified by
 * a displacement and a pointer, aligned to the element size.
 *
 * ## Operation
 * The integer value b is added to the pointer value c. The resulting address is
 * rounded down to the nearest address that is a multiple of es, where es is 1 for char pointers,
 * 2 for short pointers, and 4 for float or int pointers. An element offset eo is calculated by
 * taking the resultant address modulo 16. The vector element of a at offset eo is stored to the
 * resultant address.
 *
 * ## Notes
 * Be careful to note that the address (b+c) is aligned to an element boundary. Do not attempt
 * to store unaligned data with this intrinsic.
 */
public fun <Target, T> vecSte(a: T, off: Int, c: Target) where T : VectorSte<Target> {
    a.vecSte(off, c)
}

/**
 * VSX Unaligned Load
 */
public fun <Result, T> vecXl(off: Int, p: T): Result where T : VectorXl<Result> = p.vecXl(off)

/**
 * VSX Unaligned Store
 */
public fun <Out, T> vecXst(v: T, off: Int, p: Out) where T : VectorXst<Out> {
    v.vecXst(off, p)
}

/**
 * Vector Base-2 Logarithm Estimate
 */
public fun vecLoge(a: VectorFloat): VectorFloat = vlogefp(a)

/**
 * Vector floor.
 */
public fun vecFloor(a: VectorFloat): VectorFloat = vecFloor(a)

/**
 * Vector expte.
 */
public fun vecExpte(a: VectorFloat): VectorFloat = vecVexptefp(a)

/**
 * Vector cmplt.
 */
public fun <Result, T, U> vecCmplt(a: U, b: T): Result where T : VectorCmpGt<U, Result> = vecCmpgt(b, a)

/**
 * Vector cmple.
 */
public fun vecCmple(a: VectorFloat, b: VectorFloat): VectorBoolInt = vecCmpge(b, a)

/**
 * Vector cmpgt.
 */
public fun <Result, T, U> vecCmpgt(a: T, b: U): Result where T : VectorCmpGt<U, Result> = a.vecCmpgt(b)

/**
 * Vector cmpge.
 */
public fun vecCmpge(a: VectorFloat, b: VectorFloat): VectorBoolInt = vecVcmpgefp(a, b)

/**
 * Vector cmpeq.
 */
public fun <Result, T, U> vecCmpeq(a: T, b: U): Result where T : VectorCmpEq<U, Result> = a.vecCmpeq(b)

/**
 * Vector Compare Not Equal
 *
 * ## Result value
 * For each element of r, the value of each bit is 1 if the corresponding elements
 * of a and b are not equal. Otherwise, the value of each bit is 0.
 */
public fun <Result, T, U> vecCmpne(a: T, b: U): Result where T : VectorCmpNe<U, Result> = a.vecCmpne(b)

/**
 * Vector cmpb.
 */
public fun vecCmpb(a: VectorFloat, b: VectorFloat): VectorSignedInt = vecVcmpbfp(a, b)

/**
 * Vector ceil.
 */
public fun vecCeil(a: VectorFloat): VectorFloat = Sealed.vecVceil(a)

/**
 * Vector avg.
 */
public fun <Result, T, U> vecAvg(a: T, b: U): Result where T : VectorAvg<U, Result> = a.vecAvg(b)

/**
 * Vector andc.
 */
public fun <Result, T, U> vecAndc(a: T, b: U): Result where T : VectorAndc<U, Result> = a.vecAndc(b)

/**
 * Vector OR with Complement
 *
 * ## Purpose
 * Performs a bitwise OR of the first vector with the bitwise-complemented second vector.
 *
 * ## Result value
 * r is the bitwise OR of a and the bitwise complement of b.
 */
public fun <Result, T, U> vecOrc(a: T, b: U): Result where T : VectorOrc<U, Result> = a.vecOrc(b)

/**
 * Vector and.
 */
public fun <Result, T, U> vecAnd(a: T, b: U): Result where T : VectorAnd<U, Result> = a.vecAnd(b)

/**
 * Vector or.
 */
public fun <Result, T, U> vecOr(a: T, b: U): Result where T : VectorOr<U, Result> = a.vecOr(b)

/**
 * Vector NAND
 *
 * ## Purpose
 * Performs a bitwise NAND of two vectors.
 *
 * ## Result value
 * r is the bitwise NAND of a and b.
 */
public fun <Result, T, U> vecNand(a: T, b: U): Result where T : VectorNand<U, Result> = a.vecNand(b)

/**
 * Vector nor.
 */
public fun <Result, T, U> vecNor(a: T, b: U): Result where T : VectorNor<U, Result> = a.vecNor(b)

/**
 * Vector xor.
 */
public fun <Result, T, U> vecXor(a: T, b: U): Result where T : VectorXor<U, Result> = a.vecXor(b)

/**
 * Vector adds.
 */
public fun <Result, T, U> vecAdds(a: T, b: U): Result where T : VectorAdds<U, Result> = a.vecAdds(b)

/**
 * Vector addc.
 */
public fun vecAddc(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vecVaddcuw(a, b)

/**
 * Vector abs.
 */
public fun <T> vecAbs(a: T): T where T : VectorAbs<T> = a.vecAbs()

/**
 * Vector abss.
 */
public fun <T> vecAbss(a: T): T where T : VectorAbss<T> = a.vecAbss()

/**
 * Vector Rotate Left
 *
 * ## Purpose
 * Rotates each element of a vector left by a given number of bits.
 *
 * ## Result value
 * Each element of r is obtained by rotating the corresponding element of a left by
 * the number of bits specified by the corresponding element of b.
 */
public fun <Shift, T> vecRl(a: T, b: Shift): T where T : VectorRl<T, Shift> = a.vecRl(b)

/**
 * Vector Round
 *
 * ## Purpose
 * Returns a vector containing the rounded values of the corresponding elements of the
 * source vector.
 *
 * ## Result value
 * Each element of r contains the value of the corresponding element of a, rounded
 * to the nearest representable floating-point integer, using IEEE round-to-nearest
 * rounding.
 * The current floating-point rounding mode is ignored.
 */
public fun <T> vecRound(a: T): T where T : VectorRound<T> = a.vecRound()

/**
 * Vector Splat
 */
public fun <T> vecSplat(a: T, imm: UInt): T where T : VectorSplat<T> = a.vecSplat(imm)

/**
 * Vector Splat to Unsigned Byte
 */
public fun vecSplatU8(imm5: Byte): VectorUnsignedChar {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(Simd<UByte>(List(16) { imm5.toUByte() }), UnresolvedTypeLayout<Any?>(), VectorUnsignedChar.typeLayout())
}

/**
 * Vector Splat to Signed Byte
 */
public fun vecSplatS8(imm5: Byte): VectorSignedChar {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedChar>(Simd<Byte>(List(16) { imm5 }), UnresolvedTypeLayout<Any?>(), VectorSignedChar.typeLayout())
}

/**
 * Vector Splat to Unsigned Halfword
 */
public fun vecSplatU16(imm5: Byte): VectorUnsignedShort {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(Simd<UShort>(List(8) { imm5.toUShort() }), UnresolvedTypeLayout<Any?>(), VectorUnsignedShort.typeLayout())
}

/**
 * Vector Splat to Signed Halfword
 */
public fun vecSplatS16(imm5: Byte): VectorSignedShort {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedShort>(Simd<Short>(List(8) { imm5.toShort() }), UnresolvedTypeLayout<Any?>(), VectorSignedShort.typeLayout())
}

/**
 * Vector Splat to Unsigned Word
 */
public fun vecSplatU32(imm5: Byte): VectorUnsignedInt {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(Simd<UInt>(List(4) { imm5.toUInt() }), UnresolvedTypeLayout<Any?>(), VectorUnsignedInt.typeLayout())
}

/**
 * Vector Splat to Signed Word
 */
public fun vecSplatS32(imm5: Byte): VectorSignedInt {
    run {
        check((((-1).toInt() shl ((5 - 1).toInt())).toByte()) - 1 <= imm5 && imm5 < ((1.toInt() shl ((5 - 1).toInt())).toByte())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmuteWithLayouts<Any?, VectorSignedInt>(Simd<Int>(List(4) { imm5.toInt() }), UnresolvedTypeLayout<Any?>(), VectorSignedInt.typeLayout())
}

/**
 * Vector splats.
 */
public fun <Result, T> vecSplats(a: T): Result where T : VectorSplats<Result> = a.vecSplats()

/**
 * Vector sub.
 */
public fun <Result, T, U> vecSub(a: T, b: U): Result where T : VectorSub<U, Result> = a.vecSub(b)

/**
 * Vector Subtract Carryout
 *
 * ## Purpose
 * Returns a vector wherein each element contains the carry produced by subtracting the
 * corresponding elements of the two source vectors.
 *
 * ## Result value
 * The value of each element of r is the complement of the carry produced by subtract- ing the
 * value of the corresponding element of b from the value of the corresponding element of a. The
 * value is 0 if a borrow occurred, or 1 if no borrow occurred.
 */
public fun <Result, T, U> vecSubc(a: T, b: U): Result where T : VectorSubc<U, Result> = a.vecSubc(b)

/**
 * Vector subs.
 */
public fun <Result, T, U> vecSubs(a: T, b: U): Result where T : VectorSubs<U, Result> = a.vecSubs(b)

/**
 * Vector min.
 */
public fun <Result, T, U> vecMin(a: T, b: U): Result where T : VectorMin<U, Result> = a.vecMin(b)

/**
 * Vector max.
 */
public fun <Result, T, U> vecMax(a: T, b: U): Result where T : VectorMax<U, Result> = a.vecMax(b)

/**
 * Move From Vector Status and Control Register.
 */
public fun vecMfvscr(): VectorUnsignedShort = mfvscr()

/**
 * Vector Negate
 */
public fun <T : VectorNeg<T>> vecNeg(a: T): T = a.vecNeg()

/**
 * Vector add.
 */
public fun <Result, T, U> vecAdd(a: T, b: U): Result where T : VectorAdd<U, Result> = a.vecAdd(b)

/**
 * Vector Add Extended
 *
 * ## Result value
 * The value of each element of r is produced by adding the corresponding elements of
 * a and b with a carry specified in the corresponding element of c (1 if there is a carry, 0
 * otherwise).
 */
public fun <T> vecAdde(a: T, b: T, c: T): T where T : VectorAdde<T> = a.vecAdde(b, c)

/**
 * Vector Convert to Floating-Point
 */
public fun <T> vecCtf(a: T, imm5: Int): VectorFloat where T : VectorCtf = a.vecCtf(imm5)

/**
 * Vector Convert to Signed Integer
 */
public fun vecCts(a: VectorFloat, imm5: Int): VectorSignedInt {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vctsxs(a, imm5)
}

/**
 * Vector Convert to Unsigned Integer
 */
public fun vecCtu(a: VectorFloat, imm5: Int): VectorUnsignedInt {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vctuxs(a, imm5)
}

/**
 * Vector Multiply
 *
 * ## Purpose
 * Compute the products of corresponding elements of two vectors.
 *
 * ## Result value
 * Each element of r receives the product of the corresponding elements of a and b.
 */
public fun <T> vecMul(a: T, b: T): T where T : VectorMul<T> = a.vecMul(b)

/**
 * Vector Multiply Add Saturated
 */
public fun vecMadds(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vmhaddshs(a, b, c)

/**
 * Vector Multiply Low and Add Unsigned Half Word
 */
public fun <Result, T, U> vecMladd(a: T, b: U, c: U): Result where T : VectorMladd<U, T, Result> = a.vecMladd(b, c)

/**
 * Vector Multiply Round and Add Saturated
 */
public fun vecMradds(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort = vmhraddshs(a, b, c)

/**
 * Vector Multiply Sum
 */
public fun <T, B, U> vecMsum(a: T, b: B, c: U): U where T : VectorMsum<B, U> = a.vecMsum(b, c)

/**
 * Vector Multiply Sum Saturated
 */
public fun <T, U> vecMsums(a: T, b: T, c: U): U where T : VectorMsums<U, T> = a.vecMsums(b, c)

/**
 * Vector Multiply Add
 */
public fun vecMadd(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = vecVmaddfp(a, b, c)

/**
 * Vector Negative Multiply Subtract
 */
public fun vecNmsub(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = vecVnmsubfp(a, b, c)

/**
 * Vector Select
 *
 * ## Purpose
 * Returns a vector selecting bits from two source vectors depending on the corresponding
 * bit values of a third source vector.
 *
 * ## Result value
 * Each bit of r has the value of the corresponding bit of a if the corresponding
 * bit of c is 0. Otherwise, the bit of r has the value of the corresponding bit of b.
 */
public fun <T, U> vecSel(a: T, b: T, c: U): T where T : VectorSel<U, T> = a.vecSel(b, c)

/**
 * Vector Sum Across Partial (1/4) Saturated
 */
public fun <T, U> vecSum4s(a: T, b: U): U where T : VectorSum4s<U> = a.vecSum4s(b)

/**
 * Vector All Elements Equal
 */
public fun <Result, T, U> vecAllEq(a: T, b: U): Result where T : VectorAllEq<U, Result> = a.vecAllEq(b)

/**
 * Vector All Elements Equal
 */
public fun <Result, T, U> vecAnyEq(a: T, b: U): Result where T : VectorAnyEq<U, Result> = a.vecAnyEq(b)

/**
 * Vector All Elements Greater or Equal
 */
public fun <Result, T, U> vecAllGe(a: T, b: U): Result where T : VectorAllGe<U, Result> = a.vecAllGe(b)

/**
 * Vector Any Element Greater or Equal
 */
public fun <Result, T, U> vecAnyGe(a: T, b: U): Result where T : VectorAnyGe<U, Result> = a.vecAnyGe(b)

/**
 * Vector All Elements Greater Than
 */
public fun <Result, T, U> vecAllGt(a: T, b: U): Result where T : VectorAllGt<U, Result> = a.vecAllGt(b)

/**
 * Vector Any Element Greater Than
 */
public fun <Result, T, U> vecAnyGt(a: T, b: U): Result where T : VectorAnyGt<U, Result> = a.vecAnyGt(b)

/**
 * Vector All In
 */
public fun vecAllIn(a: VectorFloat, b: VectorFloat): Boolean = vcmpbfpP(0, a, b) != 0

/**
 * Vector All Elements Less Than or Equal
 */
public fun <Result, T, U> vecAllLe(a: U, b: T): Result where T : VectorAllGe<U, Result> = b.vecAllGe(a)

/**
 * Vector Any Element Less Than or Equal
 */
public fun <Result, T, U> vecAnyLe(a: U, b: T): Result where T : VectorAnyGe<U, Result> = b.vecAnyGe(a)

/**
 * Vector All Elements Less Than
 */
public fun <Result, T, U> vecAllLt(a: U, b: T): Result where T : VectorAllGt<U, Result> = b.vecAllGt(a)

/**
 * Vector Any Element Less Than
 */
public fun <Result, T, U> vecAnyLt(a: U, b: T): Result where T : VectorAnyGt<U, Result> = b.vecAnyGt(a)

/**
 * All Elements Not a Number
 */
public fun vecAllNan(a: VectorFloat): Boolean = vcmpeqfpP(0, a, a) != 0

/**
 * Any Elements Not a Number
 */
public fun vecAnyNan(a: VectorFloat): Boolean = vcmpeqfpP(3, a, a) != 0

/**
 * Vector All Elements Not Equal
 */
public fun <Result, T, U> vecAllNe(a: T, b: U): Result where T : VectorAllNe<U, Result> = a.vecAllNe(b)

/**
 * Vector Any Elements Not Equal
 */
public fun <Result, T, U> vecAnyNe(a: T, b: U): Result where T : VectorAnyNe<U, Result> = a.vecAnyNe(b)

/**
 * All Elements Not Greater Than or Equal
 */
public fun vecAllNge(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(0, a, b) != 0

/**
 * All Elements Not Greater Than
 */
public fun vecAllNgt(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(0, a, b) != 0

/**
 * All Elements Not Less Than or Equal
 */
public fun vecAllNle(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(0, b, a) != 0

/**
 * All Elements Not Less Than
 */
public fun vecAllNlt(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(0, b, a) != 0

/**
 * All Elements Numeric
 */
public fun vecAllNumeric(a: VectorFloat): Boolean = vcmpgefpP(2, a, a) != 0

/**
 * Any Elements Not Greater Than or Equal
 */
public fun vecAnyNge(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(3, a, b) != 0

/**
 * Any Elements Not Greater Than
 */
public fun vecAnyNgt(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(3, a, b) != 0

/**
 * Any Elements Not Less Than or Equal
 */
public fun vecAnyNle(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(3, b, a) != 0

/**
 * Any Elements Not Less Than
 */
public fun vecAnyNlt(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(3, b, a) != 0

/**
 * Any Elements Numeric
 */
public fun vecAnyNumeric(a: VectorFloat): Boolean = vcmpgefpP(1, a, a) != 0

/**
 * Vector Count Leading Zeros
 *
 * ## Purpose
 * Returns a vector containing the number of most-significant bits equal to zero of each
 * corresponding element of the source vector.
 *
 * ## Result value
 * The value of each element of r is set to the number of leading zeros of the
 * corresponding element of a.
 */
public fun <T> vecCntlz(a: T): T where T : VectorCntlz<T> = a.vecCntlz()

/**
 * Any Element Out of Bounds
 */
public fun vecAnyOut(a: VectorFloat): Boolean = vcmpeqfpP(1, a, a) != 0

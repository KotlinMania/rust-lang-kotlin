// port-lint: source mips/msa.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// MIPS SIMD Architecture intrinsics
//
// The reference is [MIPS Architecture for Programmers Volume IV-j: The
// MIPS32 SIMD Architecture Module Revision 1.12][msaRef].
//
// [msaRef]: http://cdn2.imgtec.com/documentation/MD00866-2B-MSA32-AFP-01.12.pdf

/**
 * MIPS-specific 128-bit wide vector of 16 packed `Byte`.
 */
public open class V16i8(
    public var value: ByteArray = ByteArray(16),
) {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V16i8 = V16i8(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v16i8"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V16i8> =
            object : TypeLayout<V16i8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V16i8 =
                    V16i8(
                        (PrimitiveArrayLayouts.byteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V16i8) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V16i8 =
                    V16i8(
                        (PrimitiveArrayLayouts.byteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V16i8) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Byte): V16i8 = run { val simdArg0 = value; V16i8() }

        public fun from(simd: Simd<Byte>): V16i8 = transmuteWithLayouts<Simd<Byte>, V16i8>(simd, UnresolvedTypeLayout<Simd<Byte>>(), V16i8.typeLayout())
    }
}

public fun fromSimdByte(simd: V16i8): Simd<Byte> = transmuteWithLayouts<V16i8, Simd<Byte>>(simd, V16i8.typeLayout(), UnresolvedTypeLayout<Simd<Byte>>())

/**
 * MIPS-specific 128-bit wide vector of 8 packed `Short`.
 */
public open class V8i16(
    public var value: ShortArray = ShortArray(8),
) {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V8i16 = V8i16(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v8i16"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V8i16> =
            object : TypeLayout<V8i16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V8i16 =
                    V8i16(
                        (PrimitiveArrayLayouts.shortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V8i16) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V8i16 =
                    V8i16(
                        (PrimitiveArrayLayouts.shortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V8i16) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Short): V8i16 = run { val simdArg0 = value; V8i16() }

        public fun from(simd: Simd<Short>): V8i16 = transmuteWithLayouts<Simd<Short>, V8i16>(simd, UnresolvedTypeLayout<Simd<Short>>(), V8i16.typeLayout())
    }
}

public fun fromSimdShort(simd: V8i16): Simd<Short> = transmuteWithLayouts<V8i16, Simd<Short>>(simd, V8i16.typeLayout(), UnresolvedTypeLayout<Simd<Short>>())

/**
 * MIPS-specific 128-bit wide vector of 4 packed `Int`.
 */
public open class V4i32(
    public var value: IntArray = IntArray(4),
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V4i32 = V4i32(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v4i32"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V4i32> =
            object : TypeLayout<V4i32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V4i32 =
                    V4i32(
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4i32) {
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4i32 =
                    V4i32(
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4i32) {
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): V4i32 = run { val simdArg0 = value; V4i32() }

        public fun from(simd: Simd<Int>): V4i32 = transmuteWithLayouts<Simd<Int>, V4i32>(simd, UnresolvedTypeLayout<Simd<Int>>(), V4i32.typeLayout())
    }
}

public fun fromSimdInt(simd: V4i32): Simd<Int> = transmuteWithLayouts<V4i32, Simd<Int>>(simd, V4i32.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

/**
 * MIPS-specific 128-bit wide vector of 2 packed `Long`.
 */
public open class V2i64(
    public var value: LongArray = LongArray(2),
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V2i64 = V2i64(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v2i64"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V2i64> =
            object : TypeLayout<V2i64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V2i64 =
                    V2i64(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V2i64) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V2i64 =
                    V2i64(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V2i64) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): V2i64 = run { val simdArg0 = value; V2i64() }

        public fun from(simd: Simd<Long>): V2i64 = transmuteWithLayouts<Simd<Long>, V2i64>(simd, UnresolvedTypeLayout<Simd<Long>>(), V2i64.typeLayout())
    }
}

public fun fromSimdLong(simd: V2i64): Simd<Long> = transmuteWithLayouts<V2i64, Simd<Long>>(simd, V2i64.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * MIPS-specific 128-bit wide vector of 16 packed `UByte`.
 */
public open class V16u8(
    public var value: UByteArray = UByteArray(16),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V16u8 = V16u8(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v16u8"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V16u8> =
            object : TypeLayout<V16u8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V16u8 =
                    V16u8(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V16u8) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V16u8 =
                    V16u8(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V16u8) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UByte): V16u8 = run { val simdArg0 = value; V16u8() }

        public fun from(simd: Simd<UByte>): V16u8 = transmuteWithLayouts<Simd<UByte>, V16u8>(simd, UnresolvedTypeLayout<Simd<UByte>>(), V16u8.typeLayout())
    }
}

public fun fromSimdUByte(simd: V16u8): Simd<UByte> = transmuteWithLayouts<V16u8, Simd<UByte>>(simd, V16u8.typeLayout(), UnresolvedTypeLayout<Simd<UByte>>())

/**
 * MIPS-specific 128-bit wide vector of 8 packed `UShort`.
 */
public open class V8u16(
    public var value: UShortArray = UShortArray(8),
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V8u16 = V8u16(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v8u16"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V8u16> =
            object : TypeLayout<V8u16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V8u16 =
                    V8u16(
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V8u16) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V8u16 =
                    V8u16(
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V8u16) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): V8u16 = run { val simdArg0 = value; V8u16() }

        public fun from(simd: Simd<UShort>): V8u16 = transmuteWithLayouts<Simd<UShort>, V8u16>(simd, UnresolvedTypeLayout<Simd<UShort>>(), V8u16.typeLayout())
    }
}

public fun fromSimdUShort(simd: V8u16): Simd<UShort> = transmuteWithLayouts<V8u16, Simd<UShort>>(simd, V8u16.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

/**
 * MIPS-specific 128-bit wide vector of 4 packed `UInt`.
 */
public open class V4u32(
    public var value: UIntArray = UIntArray(4),
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V4u32 = V4u32(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v4u32"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V4u32> =
            object : TypeLayout<V4u32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V4u32 =
                    V4u32(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4u32) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4u32 =
                    V4u32(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4u32) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UInt): V4u32 = run { val simdArg0 = value; V4u32() }

        public fun from(simd: Simd<UInt>): V4u32 = transmuteWithLayouts<Simd<UInt>, V4u32>(simd, UnresolvedTypeLayout<Simd<UInt>>(), V4u32.typeLayout())
    }
}

public fun fromSimdUInt(simd: V4u32): Simd<UInt> = transmuteWithLayouts<V4u32, Simd<UInt>>(simd, V4u32.typeLayout(), UnresolvedTypeLayout<Simd<UInt>>())

/**
 * MIPS-specific 128-bit wide vector of 2 packed `ULong`.
 */
public open class V2u64(
    public var value: ULongArray = ULongArray(2),
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V2u64 = V2u64(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v2u64"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V2u64> =
            object : TypeLayout<V2u64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V2u64 =
                    V2u64(
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V2u64) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V2u64 =
                    V2u64(
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V2u64) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: ULong): V2u64 = run { val simdArg0 = value; V2u64() }

        public fun from(simd: Simd<ULong>): V2u64 = transmuteWithLayouts<Simd<ULong>, V2u64>(simd, UnresolvedTypeLayout<Simd<ULong>>(), V2u64.typeLayout())
    }
}

public fun fromSimdULong(simd: V2u64): Simd<ULong> = transmuteWithLayouts<V2u64, Simd<ULong>>(simd, V2u64.typeLayout(), UnresolvedTypeLayout<Simd<ULong>>())

public open class V4f32(
    public var value: FloatArray = FloatArray(4),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V4f32 = V4f32(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v4f32"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V4f32> =
            object : TypeLayout<V4f32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V4f32 =
                    V4f32(
                        (PrimitiveArrayLayouts.floatArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4f32) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4f32 =
                    V4f32(
                        (PrimitiveArrayLayouts.floatArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4f32) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): V4f32 = run { val simdArg0 = value; V4f32() }

        public fun from(simd: Simd<Float>): V4f32 = transmuteWithLayouts<Simd<Float>, V4f32>(simd, UnresolvedTypeLayout<Simd<Float>>(), V4f32.typeLayout())
    }
}

public fun fromSimdFloat(simd: V4f32): Simd<Float> = transmuteWithLayouts<V4f32, Simd<Float>>(simd, V4f32.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

/**
 * MIPS-specific 128-bit wide vector of 2 packed `Double`.
 */
public open class V2f64(
    public var value: DoubleArray = DoubleArray(2),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V2f64 = V2f64(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v2f64"); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<V2f64> =
            object : TypeLayout<V2f64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V2f64 =
                    V2f64(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V2f64) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V2f64 =
                    V2f64(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V2f64) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): V2f64 = run { val simdArg0 = value; V2f64() }

        public fun from(simd: Simd<Double>): V2f64 = transmuteWithLayouts<Simd<Double>, V2f64>(simd, UnresolvedTypeLayout<Simd<Double>>(), V2f64.typeLayout())
    }
}

public fun fromSimdDouble(simd: V2f64): Simd<Double> = transmuteWithLayouts<V2f64, Simd<Double>>(simd, V2f64.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public expect fun externMsaAddAB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAddAH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAddAW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAddAD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAddsAB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAddsAH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAddsAW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAddsAD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAddsSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAddsSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAddsSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAddsSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAddsUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaAddsUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaAddsUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaAddsUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaAddvB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAddvH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAddvW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAddvD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAddviB(a: V16i8, b: Int): V16i8

public expect fun externMsaAddviH(a: V8i16, b: Int): V8i16

public expect fun externMsaAddviW(a: V4i32, b: Int): V4i32

public expect fun externMsaAddviD(a: V2i64, b: Int): V2i64

public expect fun externMsaAndV(a: V16u8, b: V16u8): V16u8

public expect fun externMsaAndiB(a: V16u8, b: Int): V16u8

public expect fun externMsaAsubSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAsubSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAsubSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAsubSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAsubUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaAsubUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaAsubUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaAsubUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaAveSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAveSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAveSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAveSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAveUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaAveUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaAveUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaAveUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaAverSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaAverSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaAverSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaAverSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaAverUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaAverUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaAverUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaAverUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaBclrB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaBclrH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaBclrW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaBclrD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaBclriB(a: V16u8, b: Int): V16u8

public expect fun externMsaBclriH(a: V8u16, b: Int): V8u16

public expect fun externMsaBclriW(a: V4u32, b: Int): V4u32

public expect fun externMsaBclriD(a: V2u64, b: Int): V2u64

public expect fun externMsaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externMsaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16

public expect fun externMsaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32

public expect fun externMsaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun externMsaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun externMsaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16

public expect fun externMsaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32

public expect fun externMsaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64

public expect fun externMsaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externMsaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16

public expect fun externMsaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32

public expect fun externMsaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun externMsaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun externMsaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16

public expect fun externMsaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32

public expect fun externMsaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64

public expect fun externMsaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externMsaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun externMsaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externMsaBmziB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun externMsaBnegB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaBnegH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaBnegW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaBnegD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaBnegiB(a: V16u8, b: Int): V16u8

public expect fun externMsaBnegiH(a: V8u16, b: Int): V8u16

public expect fun externMsaBnegiW(a: V4u32, b: Int): V4u32

public expect fun externMsaBnegiD(a: V2u64, b: Int): V2u64

public expect fun externMsaBnzB(a: V16u8): Int

public expect fun externMsaBnzH(a: V8u16): Int

public expect fun externMsaBnzW(a: V4u32): Int

public expect fun externMsaBnzD(a: V2u64): Int

public expect fun externMsaBnzV(a: V16u8): Int

public expect fun externMsaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externMsaBseliB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun externMsaBsetB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaBsetH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaBsetW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaBsetD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaBsetiB(a: V16u8, b: Int): V16u8

public expect fun externMsaBsetiH(a: V8u16, b: Int): V8u16

public expect fun externMsaBsetiW(a: V4u32, b: Int): V4u32

public expect fun externMsaBsetiD(a: V2u64, b: Int): V2u64

public expect fun externMsaBzB(a: V16u8): Int

public expect fun externMsaBzH(a: V8u16): Int

public expect fun externMsaBzW(a: V4u32): Int

public expect fun externMsaBzD(a: V2u64): Int

public expect fun externMsaBzV(a: V16u8): Int

public expect fun externMsaCeqB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaCeqH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaCeqW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaCeqD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaCeqiB(a: V16i8, b: Int): V16i8

public expect fun externMsaCeqiH(a: V8i16, b: Int): V8i16

public expect fun externMsaCeqiW(a: V4i32, b: Int): V4i32

public expect fun externMsaCeqiD(a: V2i64, b: Int): V2i64

public expect fun externMsaCfcmsa(a: Int): Int

public expect fun externMsaCleSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaCleSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaCleSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaCleSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaCleUB(a: V16u8, b: V16u8): V16i8

public expect fun externMsaCleUH(a: V8u16, b: V8u16): V8i16

public expect fun externMsaCleUW(a: V4u32, b: V4u32): V4i32

public expect fun externMsaCleUD(a: V2u64, b: V2u64): V2i64

public expect fun externMsaCleiSB(a: V16i8, b: Int): V16i8

public expect fun externMsaCleiSH(a: V8i16, b: Int): V8i16

public expect fun externMsaCleiSW(a: V4i32, b: Int): V4i32

public expect fun externMsaCleiSD(a: V2i64, b: Int): V2i64

public expect fun externMsaCleiUB(a: V16u8, b: Int): V16i8

public expect fun externMsaCleiUH(a: V8u16, b: Int): V8i16

public expect fun externMsaCleiUW(a: V4u32, b: Int): V4i32

public expect fun externMsaCleiUD(a: V2u64, b: Int): V2i64

public expect fun externMsaCltSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaCltSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaCltSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaCltSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaCltUB(a: V16u8, b: V16u8): V16i8

public expect fun externMsaCltUH(a: V8u16, b: V8u16): V8i16

public expect fun externMsaCltUW(a: V4u32, b: V4u32): V4i32

public expect fun externMsaCltUD(a: V2u64, b: V2u64): V2i64

public expect fun externMsaCltiSB(a: V16i8, b: Int): V16i8

public expect fun externMsaCltiSH(a: V8i16, b: Int): V8i16

public expect fun externMsaCltiSW(a: V4i32, b: Int): V4i32

public expect fun externMsaCltiSD(a: V2i64, b: Int): V2i64

public expect fun externMsaCltiUB(a: V16u8, b: Int): V16i8

public expect fun externMsaCltiUH(a: V8u16, b: Int): V8i16

public expect fun externMsaCltiUW(a: V4u32, b: Int): V4i32

public expect fun externMsaCltiUD(a: V2u64, b: Int): V2i64

public expect fun externMsaCopySB(a: V16i8, b: Int): Int

public expect fun externMsaCopySH(a: V8i16, b: Int): Int

public expect fun externMsaCopySW(a: V4i32, b: Int): Int

public expect fun externMsaCopySD(a: V2i64, b: Int): Long

public expect fun externMsaCopyUB(a: V16i8, b: Int): UInt

public expect fun externMsaCopyUH(a: V8i16, b: Int): UInt

public expect fun externMsaCopyUW(a: V4i32, b: Int): UInt

public expect fun externMsaCopyUD(a: V2i64, b: Int): ULong

public expect fun externMsaCtcmsa(imm5: Int, a: Int)

public expect fun externMsaDivSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaDivSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaDivSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaDivSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaDivUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaDivUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaDivUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaDivUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaDotpSH(a: V16i8, b: V16i8): V8i16

public expect fun externMsaDotpSW(a: V8i16, b: V8i16): V4i32

public expect fun externMsaDotpSD(a: V4i32, b: V4i32): V2i64

public expect fun externMsaDotpUH(a: V16u8, b: V16u8): V8u16

public expect fun externMsaDotpUW(a: V8u16, b: V8u16): V4u32

public expect fun externMsaDotpUD(a: V4u32, b: V4u32): V2u64

public expect fun externMsaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun externMsaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun externMsaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun externMsaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun externMsaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun externMsaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun externMsaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun externMsaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun externMsaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun externMsaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16

public expect fun externMsaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32

public expect fun externMsaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64

public expect fun externMsaFaddW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFaddD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFcafW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcafD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFceqW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFceqD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFclassW(a: V4f32): V4i32

public expect fun externMsaFclassD(a: V2f64): V2i64

public expect fun externMsaFcleW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcleD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcltW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcltD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcneW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcneD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcorW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcorD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcueqW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcueqD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFculeW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFculeD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcultW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcultD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcunW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcunD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFcuneW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFcuneD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFdivW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFdivD(a: V2f64, b: V2f64): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexdo.h"
// fn msaFexdoH(a: v4f32, b: v4f32) -> f16x8;
public expect fun externMsaFexdoW(a: V2f64, b: V2f64): V4f32

public expect fun externMsaFexp2W(a: V4f32, b: V4i32): V4f32

public expect fun externMsaFexp2D(a: V2f64, b: V2i64): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexupl.w"
// fn msaFexuplW(a: f16x8) -> v4f32;
public expect fun externMsaFexuplD(a: V4f32): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexupr.w"
// fn msaFexuprW(a: f16x8) -> v4f32;
public expect fun externMsaFexuprD(a: V4f32): V2f64

public expect fun externMsaFfintSW(a: V4i32): V4f32

public expect fun externMsaFfintSD(a: V2i64): V2f64

public expect fun externMsaFfintUW(a: V4u32): V4f32

public expect fun externMsaFfintUD(a: V2u64): V2f64

public expect fun externMsaFfqlW(a: V8i16): V4f32

public expect fun externMsaFfqlD(a: V4i32): V2f64

public expect fun externMsaFfqrW(a: V8i16): V4f32

public expect fun externMsaFfqrD(a: V4i32): V2f64

public expect fun externMsaFillB(a: Int): V16i8

public expect fun externMsaFillH(a: Int): V8i16

public expect fun externMsaFillW(a: Int): V4i32

public expect fun externMsaFillD(a: Long): V2i64

public expect fun externMsaFlog2W(a: V4f32): V4f32

public expect fun externMsaFlog2D(a: V2f64): V2f64

public expect fun externMsaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32

public expect fun externMsaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64

public expect fun externMsaFmaxW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFmaxD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFmaxAW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFmaxAD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFminW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFminD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFminAW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFminAD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32

public expect fun externMsaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64

public expect fun externMsaFmulW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFmulD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFrintW(a: V4f32): V4f32

public expect fun externMsaFrintD(a: V2f64): V2f64

public expect fun externMsaFrcpW(a: V4f32): V4f32

public expect fun externMsaFrcpD(a: V2f64): V2f64

public expect fun externMsaFrsqrtW(a: V4f32): V4f32

public expect fun externMsaFrsqrtD(a: V2f64): V2f64

public expect fun externMsaFsafW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsafD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFseqW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFseqD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsleW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsleD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsltW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsltD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsneW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsneD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsorW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsorD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsqrtW(a: V4f32): V4f32

public expect fun externMsaFsqrtD(a: V2f64): V2f64

public expect fun externMsaFsubW(a: V4f32, b: V4f32): V4f32

public expect fun externMsaFsubD(a: V2f64, b: V2f64): V2f64

public expect fun externMsaFsueqW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsueqD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsuleW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsuleD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsultW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsultD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsunW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsunD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFsuneW(a: V4f32, b: V4f32): V4i32

public expect fun externMsaFsuneD(a: V2f64, b: V2f64): V2i64

public expect fun externMsaFtintSW(a: V4f32): V4i32

public expect fun externMsaFtintSD(a: V2f64): V2i64

public expect fun externMsaFtintUW(a: V4f32): V4u32

public expect fun externMsaFtintUD(a: V2f64): V2u64

public expect fun externMsaFtqH(a: V4f32, b: V4f32): V8i16

public expect fun externMsaFtqW(a: V2f64, b: V2f64): V4i32

public expect fun externMsaFtruncSW(a: V4f32): V4i32

public expect fun externMsaFtruncSD(a: V2f64): V2i64

public expect fun externMsaFtruncUW(a: V4f32): V4u32

public expect fun externMsaFtruncUD(a: V2f64): V2u64

public expect fun externMsaHaddSH(a: V16i8, b: V16i8): V8i16

public expect fun externMsaHaddSW(a: V8i16, b: V8i16): V4i32

public expect fun externMsaHaddSD(a: V4i32, b: V4i32): V2i64

public expect fun externMsaHaddUH(a: V16u8, b: V16u8): V8u16

public expect fun externMsaHaddUW(a: V8u16, b: V8u16): V4u32

public expect fun externMsaHaddUD(a: V4u32, b: V4u32): V2u64

public expect fun externMsaHsubSH(a: V16i8, b: V16i8): V8i16

public expect fun externMsaHsubSW(a: V8i16, b: V8i16): V4i32

public expect fun externMsaHsubSD(a: V4i32, b: V4i32): V2i64

public expect fun externMsaHsubUH(a: V16u8, b: V16u8): V8i16

public expect fun externMsaHsubUW(a: V8u16, b: V8u16): V4i32

public expect fun externMsaHsubUD(a: V4u32, b: V4u32): V2i64

public expect fun externMsaIlvevB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaIlvevH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaIlvevW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaIlvevD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaIlvlB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaIlvlH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaIlvlW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaIlvlD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaIlvodB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaIlvodH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaIlvodW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaIlvodD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaIlvrB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaIlvrH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaIlvrW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaIlvrD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaInsertB(a: V16i8, b: Int, c: Int): V16i8

public expect fun externMsaInsertH(a: V8i16, b: Int, c: Int): V8i16

public expect fun externMsaInsertW(a: V4i32, b: Int, c: Int): V4i32

public expect fun externMsaInsertD(a: V2i64, b: Int, c: Long): V2i64

public expect fun externMsaInsveB(a: V16i8, b: Int, c: V16i8): V16i8

public expect fun externMsaInsveH(a: V8i16, b: Int, c: V8i16): V8i16

public expect fun externMsaInsveW(a: V4i32, b: Int, c: V4i32): V4i32

public expect fun externMsaInsveD(a: V2i64, b: Int, c: V2i64): V2i64

public expect fun externMsaLdB(memAddr: MutPtr, b: Int): V16i8

public expect fun externMsaLdH(memAddr: MutPtr, b: Int): V8i16

public expect fun externMsaLdW(memAddr: MutPtr, b: Int): V4i32

public expect fun externMsaLdD(memAddr: MutPtr, b: Int): V2i64

public expect fun externMsaLdiB(a: Int): V16i8

public expect fun externMsaLdiH(a: Int): V8i16

public expect fun externMsaLdiW(a: Int): V4i32

public expect fun externMsaLdiD(a: Int): V2i64

public expect fun externMsaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externMsaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externMsaMaxAB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaMaxAH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMaxAW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMaxAD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaMaxSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaMaxSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMaxSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMaxSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaMaxUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaMaxUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaMaxUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaMaxUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaMaxiSB(a: V16i8, b: Int): V16i8

public expect fun externMsaMaxiSH(a: V8i16, b: Int): V8i16

public expect fun externMsaMaxiSW(a: V4i32, b: Int): V4i32

public expect fun externMsaMaxiSD(a: V2i64, b: Int): V2i64

public expect fun externMsaMaxiUB(a: V16u8, b: Int): V16u8

public expect fun externMsaMaxiUH(a: V8u16, b: Int): V8u16

public expect fun externMsaMaxiUW(a: V4u32, b: Int): V4u32

public expect fun externMsaMaxiUD(a: V2u64, b: Int): V2u64

public expect fun externMsaMinAB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaMinAH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMinAW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMinAD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaMinSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaMinSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMinSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMinSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaMinUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaMinUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaMinUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaMinUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaMiniSB(a: V16i8, b: Int): V16i8

public expect fun externMsaMiniSH(a: V8i16, b: Int): V8i16

public expect fun externMsaMiniSW(a: V4i32, b: Int): V4i32

public expect fun externMsaMiniSD(a: V2i64, b: Int): V2i64

public expect fun externMsaMiniUB(a: V16u8, b: Int): V16u8

public expect fun externMsaMiniUH(a: V8u16, b: Int): V8u16

public expect fun externMsaMiniUW(a: V4u32, b: Int): V4u32

public expect fun externMsaMiniUD(a: V2u64, b: Int): V2u64

public expect fun externMsaModSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaModSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaModSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaModSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaModUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaModUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaModUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaModUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaMoveV(a: V16i8): V16i8

public expect fun externMsaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externMsaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externMsaMulQH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMulQW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMulrQH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMulrQW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMulvB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaMulvH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaMulvW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaMulvD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaNlocB(a: V16i8): V16i8

public expect fun externMsaNlocH(a: V8i16): V8i16

public expect fun externMsaNlocW(a: V4i32): V4i32

public expect fun externMsaNlocD(a: V2i64): V2i64

public expect fun externMsaNlzcB(a: V16i8): V16i8

public expect fun externMsaNlzcH(a: V8i16): V8i16

public expect fun externMsaNlzcW(a: V4i32): V4i32

public expect fun externMsaNlzcD(a: V2i64): V2i64

public expect fun externMsaNorV(a: V16u8, b: V16u8): V16u8

public expect fun externMsaNoriB(a: V16u8, b: Int): V16u8

public expect fun externMsaOrV(a: V16u8, b: V16u8): V16u8

public expect fun externMsaOriB(a: V16u8, b: Int): V16u8

public expect fun externMsaPckevB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaPckevH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaPckevW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaPckevD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaPckodB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaPckodH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaPckodW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaPckodD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaPcntB(a: V16i8): V16i8

public expect fun externMsaPcntH(a: V8i16): V8i16

public expect fun externMsaPcntW(a: V4i32): V4i32

public expect fun externMsaPcntD(a: V2i64): V2i64

public expect fun externMsaSatSB(a: V16i8, b: Int): V16i8

public expect fun externMsaSatSH(a: V8i16, b: Int): V8i16

public expect fun externMsaSatSW(a: V4i32, b: Int): V4i32

public expect fun externMsaSatSD(a: V2i64, b: Int): V2i64

public expect fun externMsaSatUB(a: V16u8, b: Int): V16u8

public expect fun externMsaSatUH(a: V8u16, b: Int): V8u16

public expect fun externMsaSatUW(a: V4u32, b: Int): V4u32

public expect fun externMsaSatUD(a: V2u64, b: Int): V2u64

public expect fun externMsaShfB(a: V16i8, b: Int): V16i8

public expect fun externMsaShfH(a: V8i16, b: Int): V8i16

public expect fun externMsaShfW(a: V4i32, b: Int): V4i32

public expect fun externMsaSldB(a: V16i8, b: V16i8, c: Int): V16i8

public expect fun externMsaSldH(a: V8i16, b: V8i16, c: Int): V8i16

public expect fun externMsaSldW(a: V4i32, b: V4i32, c: Int): V4i32

public expect fun externMsaSldD(a: V2i64, b: V2i64, c: Int): V2i64

public expect fun externMsaSldiB(a: V16i8, b: V16i8, c: Int): V16i8

public expect fun externMsaSldiH(a: V8i16, b: V8i16, c: Int): V8i16

public expect fun externMsaSldiW(a: V4i32, b: V4i32, c: Int): V4i32

public expect fun externMsaSldiD(a: V2i64, b: V2i64, c: Int): V2i64

public expect fun externMsaSllB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSllH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSllW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSllD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSlliB(a: V16i8, b: Int): V16i8

public expect fun externMsaSlliH(a: V8i16, b: Int): V8i16

public expect fun externMsaSlliW(a: V4i32, b: Int): V4i32

public expect fun externMsaSlliD(a: V2i64, b: Int): V2i64

public expect fun externMsaSplatB(a: V16i8, c: Int): V16i8

public expect fun externMsaSplatH(a: V8i16, c: Int): V8i16

public expect fun externMsaSplatW(a: V4i32, w: Int): V4i32

public expect fun externMsaSplatD(a: V2i64, c: Int): V2i64

public expect fun externMsaSplatiB(a: V16i8, b: Int): V16i8

public expect fun externMsaSplatiH(a: V8i16, b: Int): V8i16

public expect fun externMsaSplatiW(a: V4i32, b: Int): V4i32

public expect fun externMsaSplatiD(a: V2i64, b: Int): V2i64

public expect fun externMsaSraB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSraH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSraW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSraD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSraiB(a: V16i8, b: Int): V16i8

public expect fun externMsaSraiH(a: V8i16, b: Int): V8i16

public expect fun externMsaSraiW(a: V4i32, b: Int): V4i32

public expect fun externMsaSraiD(a: V2i64, b: Int): V2i64

public expect fun externMsaSrarB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSrarH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSrarW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSrarD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSrariB(a: V16i8, b: Int): V16i8

public expect fun externMsaSrariH(a: V8i16, b: Int): V8i16

public expect fun externMsaSrariW(a: V4i32, b: Int): V4i32

public expect fun externMsaSrariD(a: V2i64, b: Int): V2i64

public expect fun externMsaSrlB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSrlH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSrlW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSrlD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSrliB(a: V16i8, b: Int): V16i8

public expect fun externMsaSrliH(a: V8i16, b: Int): V8i16

public expect fun externMsaSrliW(a: V4i32, b: Int): V4i32

public expect fun externMsaSrliD(a: V2i64, b: Int): V2i64

public expect fun externMsaSrlrB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSrlrH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSrlrW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSrlrD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSrlriB(a: V16i8, b: Int): V16i8

public expect fun externMsaSrlriH(a: V8i16, b: Int): V8i16

public expect fun externMsaSrlriW(a: V4i32, b: Int): V4i32

public expect fun externMsaSrlriD(a: V2i64, b: Int): V2i64

public expect fun externMsaStB(a: V16i8, memAddr: MutPtr, immS10: Int)

public expect fun externMsaStH(a: V8i16, memAddr: MutPtr, immS11: Int)

public expect fun externMsaStW(a: V4i32, memAddr: MutPtr, immS12: Int)

public expect fun externMsaStD(a: V2i64, memAddr: MutPtr, immS13: Int)

public expect fun externMsaSubsSB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSubsSH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSubsSW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSubsSD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSubsUB(a: V16u8, b: V16u8): V16u8

public expect fun externMsaSubsUH(a: V8u16, b: V8u16): V8u16

public expect fun externMsaSubsUW(a: V4u32, b: V4u32): V4u32

public expect fun externMsaSubsUD(a: V2u64, b: V2u64): V2u64

public expect fun externMsaSubsusUB(a: V16u8, b: V16i8): V16u8

public expect fun externMsaSubsusUH(a: V8u16, b: V8i16): V8u16

public expect fun externMsaSubsusUW(a: V4u32, b: V4i32): V4u32

public expect fun externMsaSubsusUD(a: V2u64, b: V2i64): V2u64

public expect fun externMsaSubsuuSB(a: V16u8, b: V16u8): V16i8

public expect fun externMsaSubsuuSH(a: V8u16, b: V8u16): V8i16

public expect fun externMsaSubsuuSW(a: V4u32, b: V4u32): V4i32

public expect fun externMsaSubsuuSD(a: V2u64, b: V2u64): V2i64

public expect fun externMsaSubvB(a: V16i8, b: V16i8): V16i8

public expect fun externMsaSubvH(a: V8i16, b: V8i16): V8i16

public expect fun externMsaSubvW(a: V4i32, b: V4i32): V4i32

public expect fun externMsaSubvD(a: V2i64, b: V2i64): V2i64

public expect fun externMsaSubviB(a: V16i8, b: Int): V16i8

public expect fun externMsaSubviH(a: V8i16, b: Int): V8i16

public expect fun externMsaSubviW(a: V4i32, b: Int): V4i32

public expect fun externMsaSubviD(a: V2i64, b: Int): V2i64

public expect fun externMsaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externMsaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externMsaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externMsaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externMsaXorV(a: V16u8, b: V16u8): V16u8

public expect fun externMsaXoriB(a: V16u8, b: Int): V16u8

/**
 * Vector Add Absolute Values.
 *
 * The absolute values of the elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddAB(a: V16i8, b: V16i8): V16i8 = externMsaAddAB(a, b)

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddAH(a: V8i16, b: V8i16): V8i16 = externMsaAddAH(a, b)

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddAW(a: V4i32, b: V4i32): V4i32 = externMsaAddAW(a, b)

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddAD(a: V2i64, b: V2i64): V2i64 = externMsaAddAD(a, b)

/**
 * Signed Saturated Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The saturated signed result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddsAB(a: V16i8, b: V16i8): V16i8 = externMsaAddsAB(a, b)

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (eight signed 16-bit integer numbers).
 * The saturated signed result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddsAH(a: V8i16, b: V8i16): V8i16 = externMsaAddsAH(a, b)

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (four signed 32-bit integer numbers).
 * The saturated signed result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddsAW(a: V4i32, b: V4i32): V4i32 = externMsaAddsAW(a, b)

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (two signed 64-bit integer numbers).
 * The saturated signed result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddsAD(a: V2i64, b: V2i64): V2i64 = externMsaAddsAD(a, b)

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddsSB(a: V16i8, b: V16i8): V16i8 = externMsaAddsSB(a, b)

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddsSH(a: V8i16, b: V8i16): V8i16 = externMsaAddsSH(a, b)

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaAddsSW(a: V4i32, b: V4i32): V4i32 = externMsaAddsSW(a, b)

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaAddsSD(a: V2i64, b: V2i64): V2i64 = externMsaAddsSD(a, b)

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAddsUB(a: V16u8, b: V16u8): V16u8 = externMsaAddsUB(a, b)

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (eight unsigned 16-bit integer numbers)
 * are added to the elements in vector `b` (eight unsigned 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAddsUH(a: V8u16, b: V8u16): V8u16 = externMsaAddsUH(a, b)

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (four unsigned 32-bit integer numbers)
 * are added to the elements in vector `b` (four unsigned 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAddsUW(a: V4u32, b: V4u32): V4u32 = externMsaAddsUW(a, b)

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (two unsigned 64-bit integer numbers)
 * are added to the elements in vector `b` (two unsigned 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAddsUD(a: V2u64, b: V2u64): V2u64 = externMsaAddsUD(a, b)

/**
 * Vector Add
 *
 * The elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddvB(a: V16i8, b: V16i8): V16i8 = externMsaAddvB(a, b)

/**
 * Vector Add
 *
 * The elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddvH(a: V8i16, b: V8i16): V8i16 = externMsaAddvH(a, b)

/**
 * Vector Add
 *
 * The elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddvW(a: V4i32, b: V4i32): V4i32 = externMsaAddvW(a, b)

/**
 * Vector Add
 *
 * The elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddvD(a: V2i64, b: V2i64): V2i64 = externMsaAddvD(a, b)

/**
 * Immediate Add
 *
 * The 5-bit immediate unsigned value `imm5` is added to the elements
 * vector in `a` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddviB(a: V16i8, imm5: Int): V16i8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaAddviB(a, imm5)
}

/**
 * Immediate Add
 *
 * The 5-bit immediate unsigned value `imm5` is added to the elements
 * vector in `a` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddviH(a: V8i16, imm5: Int): V8i16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaAddviH(a, imm5)
}

/**
 * Immediate Add
 *
 * The 5-bit immediate unsigned value `imm5` is added to the elements
 * vector in `a` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddviW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaAddviW(a, imm5)
}

/**
 * Immediate Add
 *
 * The 5-bit immediate unsigned value `imm5` is added to the elements
 * vector in `a` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddviD(a: V2i64, imm5: Int): V2i64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaAddviD(a, imm5)
}

/**
 * Vector Logical And
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical AND operation.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAndV(a: V16u8, b: V16u8): V16u8 = externMsaAndV(a, b)

/**
 * Immediate Logical And
 *
 * Each byte element of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the 8-bit immediate i8 (signed 8-bit integer number) in a bitwise logical AND operation.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAndiB(a: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaAndiB(a, imm8)
}

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The absolute value of the signed result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAsubSB(a: V16i8, b: V16i8): V16i8 = externMsaAsubSB(a, b)

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (eight signed 16-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (eight signed 16-bit integer numbers).
 * The absolute value of the signed result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAsubSH(a: V8i16, b: V8i16): V8i16 = externMsaAsubSH(a, b)

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (four signed 32-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (four signed 32-bit integer numbers).
 * The absolute value of the signed result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAsubSW(a: V4i32, b: V4i32): V4i32 = externMsaAsubSW(a, b)

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (two signed 64-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (two signed 64-bit integer numbers).
 * The absolute value of the signed result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAsubSD(a: V2i64, b: V2i64): V2i64 = externMsaAsubSD(a, b)

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAsubUB(a: V16u8, b: V16u8): V16u8 = externMsaAsubUB(a, b)

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAsubUH(a: V8u16, b: V8u16): V8u16 = externMsaAsubUH(a, b)

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (four unsigned 32-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAsubUW(a: V4u32, b: V4u32): V4u32 = externMsaAsubUW(a, b)

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (two unsigned 64-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAsubUD(a: V2u64, b: V2u64): V2u64 = externMsaAsubUD(a, b)

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAveSB(a: V16i8, b: V16i8): V16i8 = externMsaAveSB(a, b)

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaAveSH(a: V8i16, b: V8i16): V8i16 = externMsaAveSH(a, b)

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaAveSW(a: V4i32, b: V4i32): V4i32 = externMsaAveSW(a, b)

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaAveSD(a: V2i64, b: V2i64): V2i64 = externMsaAveSD(a, b)

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAveUB(a: V16u8, b: V16u8): V16u8 = externMsaAveUB(a, b)

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are added to the elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAveUH(a: V8u16, b: V8u16): V8u16 = externMsaAveUH(a, b)

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (four unsigned 32-bit integer numbers)
 * are added to the elements in vector `b` (four unsigned 32-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAveUW(a: V4u32, b: V4u32): V4u32 = externMsaAveUW(a, b)

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (two unsigned 64-bit integer numbers)
 * are added to the elements in vector `b` (two unsigned 64-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAveUD(a: V2u64, b: V2u64): V2u64 = externMsaAveUD(a, b)

/**
 * Vector Signed Average Rounded
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done signed with full precision,
 * i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAverSB(a: V16i8, b: V16i8): V16i8 = externMsaAverSB(a, b)

/**
 * Vector Signed Average Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done signed with full precision,
 * i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaAverSH(a: V8i16, b: V8i16): V8i16 = externMsaAverSH(a, b)

/**
 * Vector Signed Average Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done signed with full precision,
 * i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaAverSW(a: V4i32, b: V4i32): V4i32 = externMsaAverSW(a, b)

/**
 * Vector Signed Average Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done signed with full precision,
 * i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaAverSD(a: V2i64, b: V2i64): V2i64 = externMsaAverSD(a, b)

/**
 * Vector Unsigned Average Rounded
 *
 * The elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done unsigned with full precision,
 * i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAverUB(a: V16u8, b: V16u8): V16u8 = externMsaAverUB(a, b)

/**
 * Vector Unsigned Average Rounded
 *
 * The elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are added to the elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done unsigned with full precision,
 * i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAverUH(a: V8u16, b: V8u16): V8u16 = externMsaAverUH(a, b)

/**
 * Vector Unsigned Average Rounded
 *
 * The elements in vector `a` (four unsigned 32-bit integer numbers)
 * are added to the elements in vector `b` (four unsigned 32-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done unsigned with full precision,
 * i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAverUW(a: V4u32, b: V4u32): V4u32 = externMsaAverUW(a, b)

/**
 * Vector Unsigned Average Rounded
 *
 * The elements in vector `a` (two unsigned 64-bit integer numbers)
 * are added to the elements in vector `b` (two unsigned 64-bit integer numbers).
 * The addition of the elements plus 1 (for rounding) is done unsigned with full precision,
 * i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAverUD(a: V2u64, b: V2u64): V2u64 = externMsaAverUD(a, b)

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBclrB(a: V16u8, b: V16u8): V16u8 = externMsaBclrB(a, b)

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBclrH(a: V8u16, b: V8u16): V8u16 = externMsaBclrH(a, b)

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBclrW(a: V4u32, b: V4u32): V4u32 = externMsaBclrW(a, b)

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBclrD(a: V2u64, b: V2u64): V2u64 = externMsaBclrD(a, b)

/**
 * Immediate Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the immediate `m` modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBclriB(a: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaBclriB(a, imm3)
}

/**
 * Immediate Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the immediate `m` modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBclriH(a: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaBclriH(a, imm4)
}

/**
 * Immediate Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the immediate `m` modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBclriW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaBclriW(a, imm5)
}

/**
 * Immediate Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the immediate `m` modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBclriD(a: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaBclriD(a, imm6)
}

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 = externMsaBinslB(a, b, c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 = externMsaBinslH(a, b, c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 = externMsaBinslW(a, b, c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 = externMsaBinslD(a, b, c)

/**
 * Immediate Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the immediate `imm3` modulo the size of the element in bits plus 1.
 */
public fun msaBinsliB(a: V16u8, b: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaBinsliB(a, b, imm3)
}

/**
 * Immediate Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the immediate `imm4` modulo the size of the element in bits plus 1.
 */
public fun msaBinsliH(a: V8u16, b: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaBinsliH(a, b, imm4)
}

/**
 * Immediate Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the immediate `imm5` modulo the size of the element in bits plus 1.
 */
public fun msaBinsliW(a: V4u32, b: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaBinsliW(a, b, imm5)
}

/**
 * Immediate Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the immediate `imm6` modulo the size of the element in bits plus 1.
 */
public fun msaBinsliD(a: V2u64, b: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaBinsliD(a, b, imm6)
}

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 = externMsaBinsrB(a, b, c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 = externMsaBinsrH(a, b, c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 = externMsaBinsrW(a, b, c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 = externMsaBinsrD(a, b, c)

/**
 * Immediate Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the immediate `imm3` modulo the size of the element in bits plus 1.
 */
public fun msaBinsriB(a: V16u8, b: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaBinsriB(a, b, imm3)
}

/**
 * Immediate Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the immediate `imm4` modulo the size of the element in bits plus 1.
 */
public fun msaBinsriH(a: V8u16, b: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaBinsriH(a, b, imm4)
}

/**
 * Immediate Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the immediate `imm5` modulo the size of the element in bits plus 1.
 */
public fun msaBinsriW(a: V4u32, b: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaBinsriW(a, b, imm5)
}

/**
 * Immediate Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the immediate `imm6` modulo the size of the element in bits plus 1.
 */
public fun msaBinsriD(a: V2u64, b: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaBinsriD(a, b, imm6)
}

/**
 * Vector Bit Move If Not Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from target vector `c`
 * (sixteen unsigned 8-bit integer numbers) are 1 and leaves unchanged all destination bits
 * for which the corresponding target bits are 0.
 */
public fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 = externMsaBmnzV(a, b, c)

/**
 * Immediate Bit Move If Not Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from immediate `imm8`
 * are 1 and leaves unchanged all destination bits for which the corresponding target bits are 0.
 */
public fun msaBmnziB(a: V16u8, b: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaBmnziB(a, b, imm8)
}

/**
 * Vector Bit Move If Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from target vector `c`
 * (sixteen unsigned 8-bit integer numbers) are 0 and leaves unchanged all destination bits
 * for which the corresponding target bits are 1.
 */
public fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 = externMsaBmzV(a, b, c)

/**
 * Immediate Bit Move If Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from immediate `imm8`
 * are 0 and leaves unchanged all destination bits for which the corresponding immediate bits are 1.
 */
public fun msaBmziB(a: V16u8, b: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaBmziB(a, b, imm8)
}

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBnegB(a: V16u8, b: V16u8): V16u8 = externMsaBnegB(a, b)

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in vector `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBnegH(a: V8u16, b: V8u16): V8u16 = externMsaBnegH(a, b)

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in vector `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBnegW(a: V4u32, b: V4u32): V4u32 = externMsaBnegW(a, b)

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in vector `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBnegD(a: V2u64, b: V2u64): V2u64 = externMsaBnegD(a, b)

/**
 * Immediate Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by immediate `imm3` modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBnegiB(a: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaBnegiB(a, imm3)
}

/**
 * Immediate Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by immediate `imm4` modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBnegiH(a: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaBnegiH(a, imm4)
}

/**
 * Immediate Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by immediate `imm5` modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBnegiW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaBnegiW(a, imm5)
}

/**
 * Immediate Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by immediate `imm6` modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBnegiD(a: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaBnegiD(a, imm6)
}

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (sixteen unsigned 8-bit integer numbers) are not zero.
 */
public fun msaBnzB(a: V16u8): Int = externMsaBnzB(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (eight unsigned 16-bit integer numbers) are not zero.
 */
public fun msaBnzH(a: V8u16): Int = externMsaBnzH(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (four unsigned 32-bit integer numbers) are not zero.
 */
public fun msaBnzW(a: V4u32): Int = externMsaBnzW(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (two unsigned 64-bit integer numbers) are not zero.
 */
public fun msaBnzD(a: V2u64): Int = externMsaBnzD(a)

/**
 * Immediate Branch If Not Zero (At Least One Element of Any Format Is Not Zero)
 *
 * PC-relative branch if at least one bit in `a` (four unsigned 32-bit integer numbers) are not zero.
 * i.e at least one element is not zero regardless of the data format.
 */
public fun msaBnzV(a: V16u8): Int = externMsaBnzV(a)

/**
 * Vector Bit Select
 *
 * Selectively copy bits from the source vectors `b` (eight unsigned 16-bit integer numbers)
 * and `c` (eight unsigned 16-bit integer numbers)
 * into destination vector `a` (eight unsigned 16-bit integer numbers) based on the corresponding bit in `a`:
 * if 0 copies the bit from `b`, if 1 copies the bit from `c`.
 */
public fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 = externMsaBselV(a, b, c)

/**
 * Immediate Bit Select
 *
 * Selectively copy bits from the 8-bit immediate `imm8` and `c` (eight unsigned 16-bit integer numbers)
 * into destination vector `a` (eight unsigned 16-bit integer numbers) based on the corresponding bit in `a`:
 * if 0 copies the bit from `b`, if 1 copies the bit from `c`.
 */
public fun msaBseliB(a: V16u8, b: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaBseliB(a, b, imm8)
}

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBsetB(a: V16u8, b: V16u8): V16u8 = externMsaBsetB(a, b)

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in vector `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBsetH(a: V8u16, b: V8u16): V8u16 = externMsaBsetH(a, b)

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in vector `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBsetW(a: V4u32, b: V4u32): V4u32 = externMsaBsetW(a, b)

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in vector `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBsetD(a: V2u64, b: V2u64): V2u64 = externMsaBsetD(a, b)

/**
 * Immediate Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by immediate `imm3`.
 * The result is written to vector `a` (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBsetiB(a: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaBsetiB(a, imm3)
}

/**
 * Immediate Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by immediate `imm4`.
 * The result is written to vector `a` (eight unsigned 16-bit integer numbers).
 */
public fun msaBsetiH(a: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaBsetiH(a, imm4)
}

/**
 * Immediate Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by immediate `imm5`.
 * The result is written to vector `a` (four unsigned 32-bit integer numbers).
 */
public fun msaBsetiW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaBsetiW(a, imm5)
}

/**
 * Immediate Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by immediate `imm6`.
 * The result is written to vector `a` (two unsigned 64-bit integer numbers).
 */
public fun msaBsetiD(a: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaBsetiD(a, imm6)
}

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (sixteen unsigned 8-bit integer numbers) is zero.
 */
public fun msaBzB(a: V16u8): Int = externMsaBzB(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (eight unsigned 16-bit integer numbers) is zero.
 */
public fun msaBzH(a: V8u16): Int = externMsaBzH(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (four unsigned 32-bit integer numbers) is zero.
 */
public fun msaBzW(a: V4u32): Int = externMsaBzW(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (two unsigned 64-bit integer numbers) is zero.
 */
public fun msaBzD(a: V2u64): Int = externMsaBzD(a)

/**
 * Immediate Branch If Zero (All Elements of Any Format Are Zero)
 *
 * PC-relative branch if all elements in `a` (sixteen unsigned 8-bit integer numbers) bits are zero,
 * i.e. all elements are zero regardless of the data format.
 */
public fun msaBzV(a: V16u8): Int = externMsaBzV(a)

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) and `b` (sixteen signed 8-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqB(a: V16i8, b: V16i8): V16i8 = externMsaCeqB(a, b)

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) and `b` (eight signed 16-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqH(a: V8i16, b: V8i16): V8i16 = externMsaCeqH(a, b)

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) and `b` (four signed 32-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqW(a: V4i32, b: V4i32): V4i32 = externMsaCeqW(a, b)

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) and `b` (two signed 64-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqD(a: V2i64, b: V2i64): V2i64 = externMsaCeqD(a, b)

/**
 * Immediate Compare Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) the 5-bit signed immediate immS5
 * are equal, otherwise set all bits to 0.
 */
public fun msaCeqiB(a: V16i8, immS5: Int): V16i8 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCeqiB(a, immS5)
}

/**
 * Immediate Compare Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) the 5-bit signed immediate immS5
 * are equal, otherwise set all bits to 0.
 */
public fun msaCeqiH(a: V8i16, immS5: Int): V8i16 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCeqiH(a, immS5)
}

/**
 * Immediate Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) the 5-bit signed immediate immS5
 * are equal, otherwise set all bits to 0.
 */
public fun msaCeqiW(a: V4i32, immS5: Int): V4i32 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCeqiW(a, immS5)
}

/**
 * Immediate Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) the 5-bit signed immediate immS5
 * are equal, otherwise set all bits to 0.
 */
public fun msaCeqiD(a: V2i64, immS5: Int): V2i64 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCeqiD(a, immS5)
}

/**
 * GPR Copy from MSA Control Register
 *
 * The sign extended content of MSA control register cs is copied to GPR rd.
 *
 * Can not be tested in user mode
 */
public fun msaCfcmsa(imm5: Int): Int {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCfcmsa(imm5)
}

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * are signed less than or equal to `b` (sixteen signed 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSB(a: V16i8, b: V16i8): V16i8 = externMsaCleSB(a, b)

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * are signed less than or equal to `b` (eight signed 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSH(a: V8i16, b: V8i16): V8i16 = externMsaCleSH(a, b)

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * are signed less than or equal to `b` (four signed 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSW(a: V4i32, b: V4i32): V4i32 = externMsaCleSW(a, b)

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * are signed less than or equal to `b` (two signed 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSD(a: V2i64, b: V2i64): V2i64 = externMsaCleSD(a, b)

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * are unsigned less than or equal to `b` (sixteen unsigned 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUB(a: V16u8, b: V16u8): V16i8 = externMsaCleUB(a, b)

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * are unsigned less than or equal to `b` (eight unsigned 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUH(a: V8u16, b: V8u16): V8i16 = externMsaCleUH(a, b)

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * are unsigned less than or equal to `b` (four unsigned 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUW(a: V4u32, b: V4u32): V4i32 = externMsaCleUW(a, b)

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * are unsigned less than or equal to `b` (two unsigned 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUD(a: V2u64, b: V2u64): V2i64 = externMsaCleUD(a, b)

/**
 * Immediate Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * is less than or equal to the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCleiSB(a: V16i8, immS5: Int): V16i8 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCleiSB(a, immS5)
}

/**
 * Immediate Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * is less than or equal to the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCleiSH(a: V8i16, immS5: Int): V8i16 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCleiSH(a, immS5)
}

/**
 * Immediate Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * is less than or equal to the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCleiSW(a: V4i32, immS5: Int): V4i32 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCleiSW(a, immS5)
}

/**
 * Immediate Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * is less than or equal to the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCleiSD(a: V2i64, immS5: Int): V2i64 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCleiSD(a, immS5)
}

/**
 * Immediate Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * is unsigned less than or equal to the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCleiUB(a: V16u8, imm5: Int): V16i8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCleiUB(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * is unsigned less than or equal to the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCleiUH(a: V8u16, imm5: Int): V8i16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCleiUH(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * is unsigned less than or equal to the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCleiUW(a: V4u32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCleiUW(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * is unsigned less than or equal to the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCleiUD(a: V2u64, imm5: Int): V2i64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCleiUD(a, imm5)
}

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * are signed less than `b` (sixteen signed 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSB(a: V16i8, b: V16i8): V16i8 = externMsaCltSB(a, b)

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * are signed less than `b` (eight signed 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSH(a: V8i16, b: V8i16): V8i16 = externMsaCltSH(a, b)

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * are signed less than `b` (four signed 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSW(a: V4i32, b: V4i32): V4i32 = externMsaCltSW(a, b)

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * are signed less than `b` (two signed 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSD(a: V2i64, b: V2i64): V2i64 = externMsaCltSD(a, b)

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * are unsigned less than `b` (sixteen unsigned 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUB(a: V16u8, b: V16u8): V16i8 = externMsaCltUB(a, b)

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * are unsigned less than `b` (eight unsigned 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUH(a: V8u16, b: V8u16): V8i16 = externMsaCltUH(a, b)

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * are unsigned less than `b` (four unsigned 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUW(a: V4u32, b: V4u32): V4i32 = externMsaCltUW(a, b)

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * are unsigned less than `b` (two unsigned 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUD(a: V2u64, b: V2u64): V2i64 = externMsaCltUD(a, b)

/**
 * Immediate Compare Signed Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * is less than the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCltiSB(a: V16i8, immS5: Int): V16i8 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCltiSB(a, immS5)
}

/**
 * Immediate Compare Signed Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * is less than the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCltiSH(a: V8i16, immS5: Int): V8i16 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCltiSH(a, immS5)
}

/**
 * Immediate Compare Signed Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * is less than the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCltiSW(a: V4i32, immS5: Int): V4i32 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCltiSW(a, immS5)
}

/**
 * Immediate Compare Signed Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * is less than the 5-bit signed immediate immS5,
 * otherwise set all bits to 0.
 */
public fun msaCltiSD(a: V2i64, immS5: Int): V2i64 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaCltiSD(a, immS5)
}

/**
 * Immediate Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * is unsigned less than the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCltiUB(a: V16u8, imm5: Int): V16i8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCltiUB(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * is unsigned less than the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCltiUH(a: V8u16, imm5: Int): V8i16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCltiUH(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * is unsigned less than the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCltiUW(a: V4u32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCltiUW(a, imm5)
}

/**
 * Immediate Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * is unsigned less than the 5-bit unsigned immediate `imm5`,
 * otherwise set all bits to 0.
 */
public fun msaCltiUD(a: V2u64, imm5: Int): V2i64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaCltiUD(a, imm5)
}

/**
 * Element Copy to GPR Signed
 *
 * Sign-extend element `imm4` of vector `a` (sixteen signed 8-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopySB(a: V16i8, imm4: Int): Int {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaCopySB(a, imm4)
}

/**
 * Element Copy to GPR Signed
 *
 * Sign-extend element `imm3` of vector `a` (eight signed 16-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopySH(a: V8i16, imm3: Int): Int {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaCopySH(a, imm3)
}

/**
 * Element Copy to GPR Signed
 *
 * Sign-extend element `imm2` of vector `a` (four signed 32-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopySW(a: V4i32, imm2: Int): Int {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaCopySW(a, imm2)
}

/**
 * Element Copy to GPR Signed
 *
 * Sign-extend element `imm1` of vector `a` (two signed 64-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopySD(a: V2i64, imm1: Int): Long {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaCopySD(a, imm1)
}

/**
 * Element Copy to GPR Unsigned
 *
 * Zero-extend element `imm4` of vector `a` (sixteen signed 8-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopyUB(a: V16i8, imm4: Int): UInt {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaCopyUB(a, imm4)
}

/**
 * Element Copy to GPR Unsigned
 *
 * Zero-extend element `imm3` of vector `a` (eight signed 16-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopyUH(a: V8i16, imm3: Int): UInt {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaCopyUH(a, imm3)
}

/**
 * Element Copy to GPR Unsigned
 *
 * Zero-extend element `imm2` of vector `a` (four signed 32-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopyUW(a: V4i32, imm2: Int): UInt {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaCopyUW(a, imm2)
}

/**
 * Element Copy to GPR Unsigned
 *
 * Zero-extend element `imm1` of vector `a` (two signed 64-bit integer numbers)
 * and copy the result to GPR rd.
 */
public fun msaCopyUD(a: V2i64, imm1: Int): ULong {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaCopyUD(a, imm1)
}

/**
 * GPR Copy to MSA Control Register
 *
 * The content of the least significant 31 bits of GPR `imm1` is copied to
 * MSA control register cd.
 *
 * Can not be tested in user mode
 */
public fun msaCtcmsa(a: Int, imm5: Int) {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    externMsaCtcmsa(imm5, a)
}

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are divided by signed integer elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaDivSB(a: V16i8, b: V16i8): V16i8 = externMsaDivSB(a, b)

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are divided by signed integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaDivSH(a: V8i16, b: V8i16): V8i16 = externMsaDivSH(a, b)

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are divided by signed integer elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaDivSW(a: V4i32, b: V4i32): V4i32 = externMsaDivSW(a, b)

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are divided by signed integer elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaDivSD(a: V2i64, b: V2i64): V2i64 = externMsaDivSD(a, b)

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaDivUB(a: V16u8, b: V16u8): V16u8 = externMsaDivUB(a, b)

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaDivUH(a: V8u16, b: V8u16): V8u16 = externMsaDivUH(a, b)

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers).
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaDivUW(a: V4u32, b: V4u32): V4u32 = externMsaDivUW(a, b)

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (two unsigned 64-bit integer numbers).
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaDivUD(a: V2u64, b: V2u64): V2u64 = externMsaDivUD(a, b)

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (eight signed 16-bit integer numbers).
 */
public fun msaDotpSH(a: V16i8, b: V16i8): V8i16 = externMsaDotpSH(a, b)

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (four signed 32-bit integer numbers).
 */
public fun msaDotpSW(a: V8i16, b: V8i16): V4i32 = externMsaDotpSW(a, b)

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (two signed 64-bit integer numbers).
 */
public fun msaDotpSD(a: V4i32, b: V4i32): V2i64 = externMsaDotpSD(a, b)

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (eight unsigned 16-bit integer numbers).
 */
public fun msaDotpUH(a: V16u8, b: V16u8): V8u16 = externMsaDotpUH(a, b)

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (four unsigned 32-bit integer numbers).
 */
public fun msaDotpUW(a: V8u16, b: V8u16): V4u32 = externMsaDotpUW(a, b)

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (two unsigned 64-bit integer numbers).
 */
public fun msaDotpUD(a: V4u32, b: V4u32): V2u64 = externMsaDotpUD(a, b)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (eight signed 16-bit integer numbers).
 */
public fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 = externMsaDpaddSH(a, b, c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (four signed 32-bit integer numbers).
 */
public fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 = externMsaDpaddSW(a, b, c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (two signed 64-bit integer numbers).
 */
public fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 = externMsaDpaddSD(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (eight unsigned 16-bit integer numbers).
 */
public fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 = externMsaDpaddUH(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (four unsigned 32-bit integer numbers).
 */
public fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 = externMsaDpaddUW(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (two unsigned 64-bit integer numbers).
 */
public fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 = externMsaDpaddUD(a, b, c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (eight signed 16-bit integer numbers).
 */
public fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 = externMsaDpsubSH(a, b, c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (four signed 32-bit integer numbers).
 */
public fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 = externMsaDpsubSW(a, b, c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (two signed 64-bit integer numbers).
 */
public fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 = externMsaDpsubSD(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (eight signed 16-bit integer numbers).
 */
public fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 = externMsaDpsubUH(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (four signed 32-bit integer numbers).
 */
public fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 = externMsaDpsubUW(a, b, c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (two signed 64-bit integer numbers).
 */
public fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 = externMsaDpsubUD(a, b, c)

/**
 * Vector Floating-Point Addition
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are added to the floating-point elements in `bc` (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFaddW(a: V4f32, b: V4f32): V4f32 = externMsaFaddW(a, b)

/**
 * Vector Floating-Point Addition
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are added to the floating-point elements in `bc` (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFaddD(a: V2f64, b: V2f64): V2f64 = externMsaFaddD(a, b)

/**
 * Vector Floating-Point Quiet Compare Always False
 *
 * Set all bits to 0 in vector (four signed 32-bit integer numbers).
 * Signaling NaN elements in `a` (four 32-bit floating point numbers)
 * or `b` (four 32-bit floating point numbers) signal Invalid Operation exception.
 */
public fun msaFcafW(a: V4f32, b: V4f32): V4i32 = externMsaFcafW(a, b)

/**
 * Vector Floating-Point Quiet Compare Always False
 *
 * Set all bits to 0 in vector (two signed 64-bit integer numbers).
 * Signaling NaN elements in `a` (two 64-bit floating point numbers)
 * or `b` (two 64-bit floating point numbers) signal Invalid Operation exception.
 */
public fun msaFcafD(a: V2f64, b: V2f64): V2i64 = externMsaFcafD(a, b)

/**
 * Vector Floating-Point Quiet Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding in `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are ordered and equal,
 * otherwise set all bits to 0.
 */
public fun msaFceqW(a: V4f32, b: V4f32): V4i32 = externMsaFceqW(a, b)

/**
 * Vector Floating-Point Quiet Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding in `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are ordered and equal,
 * otherwise set all bits to 0.
 */
public fun msaFceqD(a: V2f64, b: V2f64): V2i64 = externMsaFceqD(a, b)

/**
 * Vector Floating-Point Class Mask
 *
 * Store in each element of vector (four signed 32-bit integer numbers)
 * a bit mask reflecting the floating-point class of the corresponding element of vector
 * `a` (four 32-bit floating point numbers).
 * The mask has 10 bits as follows. Bits 0 and 1 indicate NaN values: signaling NaN (bit 0) and quiet NaN (bit 1).
 * Bits 2, 3, 4, 5 classify negative values: infinity (bit 2), normal (bit 3), subnormal (bit 4), and zero (bit 5).
 * Bits 6, 7, 8, 9 classify positive values: infinity (bit 6), normal (bit 7), subnormal (bit 8), and zero (bit 9).
 */
public fun msaFclassW(a: V4f32): V4i32 = externMsaFclassW(a)

/**
 * Vector Floating-Point Class Mask
 *
 * Store in each element of vector (two signed 64-bit integer numbers)
 * a bit mask reflecting the floating-point class of the corresponding element of vector
 * `a` (two 64-bit floating point numbers).
 * The mask has 10 bits as follows. Bits 0 and 1 indicate NaN values: signaling NaN (bit 0) and quiet NaN (bit 1).
 * Bits 2, 3, 4, 5 classify negative values: infinity (bit 2), normal (bit 3), subnormal (bit 4), and zero (bit 5).
 * Bits 6, 7, 8, 9 classify positive values: infinity (bit 6), normal (bit 7), subnormal (bit 8), and zero (bit 9).
 */
public fun msaFclassD(a: V2f64): V2i64 = externMsaFclassD(a)

/**
 * Vector Floating-Point Quiet Compare Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) elements are ordered
 * and either less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcleW(a: V4f32, b: V4f32): V4i32 = externMsaFcleW(a, b)

/**
 * Vector Floating-Point Quiet Compare Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) elements are ordered
 * and either less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcleD(a: V2f64, b: V2f64): V2i64 = externMsaFcleD(a, b)

/**
 * Vector Floating-Point Quiet Compare Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) elements are ordered
 * and less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcltW(a: V4f32, b: V4f32): V4i32 = externMsaFcltW(a, b)

/**
 * Vector Floating-Point Quiet Compare Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) elements are ordered
 * and less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcltD(a: V2f64, b: V2f64): V2i64 = externMsaFcltD(a, b)

/**
 * Vector Floating-Point Quiet Compare Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered and not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcneW(a: V4f32, b: V4f32): V4i32 = externMsaFcneW(a, b)

/**
 * Vector Floating-Point Quiet Compare Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered and not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcneD(a: V2f64, b: V2f64): V2i64 = externMsaFcneD(a, b)

/**
 * Vector Floating-Point Quiet Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered, i.e. both elements are not NaN values,
 * otherwise set all bits to 0.
 */
public fun msaFcorW(a: V4f32, b: V4f32): V4i32 = externMsaFcorW(a, b)

/**
 * Vector Floating-Point Quiet Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered, i.e. both elements are not NaN values,
 * otherwise set all bits to 0.
 */
public fun msaFcorD(a: V2f64, b: V2f64): V2i64 = externMsaFcorD(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFcueqW(a: V4f32, b: V4f32): V4i32 = externMsaFcueqW(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFcueqD(a: V2f64, b: V2f64): V2i64 = externMsaFcueqD(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding elements in `a` (four 32-bit floating point numbers)
 * are unordered or less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFculeW(a: V4f32, b: V4f32): V4i32 = externMsaFculeW(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding elements in `a` (two 64-bit floating point numbers)
 * are unordered or less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFculeD(a: V2f64, b: V2f64): V2i64 = externMsaFculeD(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding elements in `a` (four 32-bit floating point numbers)
 * are unordered or less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcultW(a: V4f32, b: V4f32): V4i32 = externMsaFcultW(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding elements in `a` (two 64-bit floating point numbers)
 * are unordered or less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcultD(a: V2f64, b: V2f64): V2i64 = externMsaFcultD(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFcunW(a: V4f32, b: V4f32): V4i32 = externMsaFcunW(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFcunD(a: V2f64, b: V2f64): V2i64 = externMsaFcunD(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcuneW(a: V4f32, b: V4f32): V4i32 = externMsaFcuneW(a, b)

/**
 * Vector Floating-Point Quiet Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcuneD(a: V2f64, b: V2f64): V2i64 = externMsaFcuneD(a, b)

/**
 * Vector Floating-Point Division
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are divided by the floating-point elements in vector `b` (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFdivW(a: V4f32, b: V4f32): V4f32 = externMsaFdivW(a, b)

/**
 * Vector Floating-Point Division
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are divided by the floating-point elements in vector `b` (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFdivD(a: V2f64, b: V2f64): V2f64 = externMsaFdivD(a, b)

// Note: 16-bit float
// /// Vector Floating-Point Down-Convert Interchange Format
// ///
// /// The floating-point elements in vector `a` (four 64-bit floating point numbers)
// /// and vector `b` (four 64-bit floating point numbers) are down-converted
// /// to a smaller interchange format, i.e. from 64-bit to 32-bit, or from 32-bit to 16-bit.
// /// The result is written to vector (8 16-bit floating point numbers).
// ///
// @inline
// @targetFeature(enable = "msa")
// @cfgAttr(test, assertInstr(fexdo.h))
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexdo_h(a: v4f32, b: v4f32) -> f16x8 (
// msaFexdoH(a, mem.transmute(b))
// )

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers) are down-converted
 * to a smaller interchange format, i.e. from 64-bit to 32-bit, or from 32-bit to 16-bit.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFexdoW(a: V2f64, b: V2f64): V4f32 = externMsaFexdoW(a, b)

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are scaled, i.e. multiplied, by 2 to the power of integer elements in vector `b`
 * (four signed 32-bit integer numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFexp2W(a: V4f32, b: V4i32): V4f32 = externMsaFexp2W(a, b)

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are scaled, i.e. multiplied, by 2 to the power of integer elements in vector `b`
 * (two signed 64-bit integer numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexp2D(a: V2f64, b: V2i64): V2f64 = externMsaFexp2D(a, b)

// Note: 16-bit float
// /// Vector Floating-Point Up-Convert Interchange Format Left
// ///
// /// The left half floating-point elements in vector `a` (two 16-bit floating point numbers)
// /// are up-converted to a larger interchange format,
// /// i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
// /// The result is written to vector (four 32-bit floating point numbers).
// ///
// @inline
// @targetFeature(enable = "msa")
// @cfgAttr(test, assertInstr(fexupl.w))
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexupl_w(a: f16x8) -> v4f32 (
// msaFexuplW(a)
// )

/**
 * Vector Floating-Point Up-Convert Interchange Format Left
 *
 * The left half floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are up-converted to a larger interchange format,
 * i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexuplD(a: V4f32): V2f64 = externMsaFexuplD(a)

// Note: 16-bit float
// /// Vector Floating-Point Up-Convert Interchange Format Left
// ///
// /// The right half floating-point elements in vector `a` (two 16-bit floating point numbers)
// /// are up-converted to a larger interchange format,
// /// i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
// /// The result is written to vector (four 32-bit floating point numbers).
// ///
// @inline
// @targetFeature(enable = "msa")
// @cfgAttr(test, assertInstr(fexupr.w))
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexupr_w(a: f16x8) -> v4f32 (
// msaFexuprW(a)
// )

/**
 * Vector Floating-Point Up-Convert Interchange Format Left
 *
 * The right half floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are up-converted to a larger interchange format,
 * i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexuprD(a: V4f32): V2f64 = externMsaFexuprD(a)

/**
 * Vector Floating-Point Round and Convert from Signed Integer
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfintSW(a: V4i32): V4f32 = externMsaFfintSW(a)

/**
 * Vector Floating-Point Round and Convert from Signed Integer
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfintSD(a: V2i64): V2f64 = externMsaFfintSD(a)

/**
 * Vector Floating-Point Round and Convert from Unsigned Integer
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfintUW(a: V4u32): V4f32 = externMsaFfintUW(a)

/**
 * Vector Floating-Point Round and Convert from Unsigned Integer
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfintUD(a: V2u64): V2f64 = externMsaFfintUD(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The left half fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfqlW(a: V8i16): V4f32 = externMsaFfqlW(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The left half fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfqlD(a: V4i32): V2f64 = externMsaFfqlD(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The right half fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfqrW(a: V8i16): V4f32 = externMsaFfqrW(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The right half fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfqrD(a: V4i32): V2f64 = externMsaFfqrD(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (sixteen signed 8-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillB(a: Int): V16i8 = externMsaFillB(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (eight signed 16-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillH(a: Int): V8i16 = externMsaFillH(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (four signed 32-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillW(a: Int): V4i32 = externMsaFillW(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (two signed 64-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillD(a: Long): V2i64 = externMsaFillD(a)

/**
 * Vector Floating-Point Base 2 Logarithm
 *
 * The signed integral base 2 exponents of floating-point elements in vector `a`
 * (four 32-bit floating point numbers) are written as floating-point values to vector elements
 * (four 32-bit floating point numbers).
 */
public fun msaFlog2W(a: V4f32): V4f32 = externMsaFlog2W(a)

/**
 * Vector Floating-Point Base 2 Logarithm
 *
 * The signed integral base 2 exponents of floating-point elements in vector `a`
 * (two 64-bit floating point numbers) are written as floating-point values to vector elements
 * (two 64-bit floating point numbers).
 */
public fun msaFlog2D(a: V2f64): V2f64 = externMsaFlog2D(a)

/**
 * Vector Floating-Point Multiply-Add
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (four 32-bit floating point numbers)
 * are added to the floating-point elements in vector `a` (four 32-bit floating point numbers).
 */
public fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 = externMsaFmaddW(a, b, c)

/**
 * Vector Floating-Point Multiply-Add
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (two 64-bit floating point numbers)
 * are added to the floating-point elements in vector `a` (two 64-bit floating point numbers).
 */
public fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 = externMsaFmaddD(a, b, c)

/**
 * Vector Floating-Point Maximum
 *
 * The largest values between corresponding floating-point elements in vector `a`
 * (four 32-bit floating point numbers) and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFmaxW(a: V4f32, b: V4f32): V4f32 = externMsaFmaxW(a, b)

/**
 * Vector Floating-Point Maximum
 *
 * The largest values between corresponding floating-point elements in vector `a`
 * (two 64-bit floating point numbers) and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFmaxD(a: V2f64, b: V2f64): V2f64 = externMsaFmaxD(a, b)

/**
 * Vector Floating-Point Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (four 32-bit floating point numbers)
 * and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFmaxAW(a: V4f32, b: V4f32): V4f32 = externMsaFmaxAW(a, b)

/**
 * Vector Floating-Point Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFmaxAD(a: V2f64, b: V2f64): V2f64 = externMsaFmaxAD(a, b)

/**
 * Vector Floating-Point Minimum
 *
 * The smallest values between corresponding floating-point elements in vector `a`
 * (four 32-bit floating point numbers) and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFminW(a: V4f32, b: V4f32): V4f32 = externMsaFminW(a, b)

/**
 * Vector Floating-Point Minimum
 *
 * The smallest values between corresponding floating-point elements in vector `a`
 * (two 64-bit floating point numbers) and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFminD(a: V2f64, b: V2f64): V2f64 = externMsaFminD(a, b)

/**
 * Vector Floating-Point Minimum Based on Absolute Values
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (four 32-bit floating point numbers)
 * and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFminAW(a: V4f32, b: V4f32): V4f32 = externMsaFminAW(a, b)

/**
 * Vector Floating-Point Minimum Based on Absolute Values
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFminAD(a: V2f64, b: V2f64): V2f64 = externMsaFminAD(a, b)

/**
 * Vector Floating-Point Multiply-Sub
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (four 32-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a` (four 32-bit floating point numbers).
 */
public fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 = externMsaFmsubW(a, b, c)

/**
 * Vector Floating-Point Multiply-Sub
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (two 64-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a` (two 64-bit floating point numbers).
 */
public fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 = externMsaFmsubD(a, b, c)

/**
 * Vector Floating-Point Multiplication
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers) are
 * multiplied by floating-point elements in vector `b` (four 32-bit floating point numbers).
 */
public fun msaFmulW(a: V4f32, b: V4f32): V4f32 = externMsaFmulW(a, b)

/**
 * Vector Floating-Point Multiplication
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers) are
 * multiplied by floating-point elements in vector `b` (two 64-bit floating point numbers).
 */
public fun msaFmulD(a: V2f64, b: V2f64): V2f64 = externMsaFmulD(a, b)

/**
 * Vector Floating-Point Round to Integer
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are rounded to an integral valued floating-point number in the same format based
 * on the rounding mode bits RM in MSA Control and Status Register MSACSR.
 */
public fun msaFrintW(a: V4f32): V4f32 = externMsaFrintW(a)

/**
 * Vector Floating-Point Round to Integer
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are rounded to an integral valued floating-point number in the same format based
 * on the rounding mode bits RM in MSA Control and Status Register MSACSR.
 */
public fun msaFrintD(a: V2f64): V2f64 = externMsaFrintD(a)

/**
 * Vector Approximate Floating-Point Reciprocal
 *
 * The reciprocals of floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are calculated and the result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFrcpW(a: V4f32): V4f32 = externMsaFrcpW(a)

/**
 * Vector Approximate Floating-Point Reciprocal
 *
 * The reciprocals of floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are calculated and the result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFrcpD(a: V2f64): V2f64 = externMsaFrcpD(a)

/**
 * Vector Approximate Floating-Point Reciprocal of Square Root
 *
 * The reciprocals of the square roots of floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are calculated and the result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFrsqrtW(a: V4f32): V4f32 = externMsaFrsqrtW(a)

/**
 * Vector Approximate Floating-Point Reciprocal of Square Root
 *
 * The reciprocals of the square roots of floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are calculated and the result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFrsqrtD(a: V2f64): V2f64 = externMsaFrsqrtD(a)

/**
 * Vector Floating-Point Signaling Compare Always False
 *
 * Set all bits to 0 in vector (four signed 32-bit integer numbers) elements.
 * Signaling and quiet NaN elements in vector `a` (four 32-bit floating point numbers)
 * or `b` (four 32-bit floating point numbers) signal Invalid Operation exception.
 * In case of a floating-point exception, the default result has all bits set to 0.
 */
public fun msaFsafW(a: V4f32, b: V4f32): V4i32 = externMsaFsafW(a, b)

/**
 * Vector Floating-Point Signaling Compare Always False
 *
 * Set all bits to 0 in vector (two signed 64-bit integer numbers) elements.
 * Signaling and quiet NaN elements in vector `a` (two 64-bit floating point numbers)
 * or `b` (two 64-bit floating point numbers) signal Invalid Operation exception.
 * In case of a floating-point exception, the default result has all bits set to 0.
 */
public fun msaFsafD(a: V2f64, b: V2f64): V2i64 = externMsaFsafD(a, b)

/**
 * Vector Floating-Point Signaling Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are equal, otherwise set all bits to 0.
 */
public fun msaFseqW(a: V4f32, b: V4f32): V4i32 = externMsaFseqW(a, b)

/**
 * Vector Floating-Point Signaling Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are equal, otherwise set all bits to 0.
 */
public fun msaFseqD(a: V2f64, b: V2f64): V2i64 = externMsaFseqD(a, b)

/**
 * Vector Floating-Point Signaling Compare Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are less than or equal to `b` (four 32-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsleW(a: V4f32, b: V4f32): V4i32 = externMsaFsleW(a, b)

/**
 * Vector Floating-Point Signaling Compare Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are less than or equal to `b` (two 64-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsleD(a: V2f64, b: V2f64): V2i64 = externMsaFsleD(a, b)

/**
 * Vector Floating-Point Signaling Compare Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are less than `b` (four 32-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsltW(a: V4f32, b: V4f32): V4i32 = externMsaFsltW(a, b)

/**
 * Vector Floating-Point Signaling Compare Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are less than `b` (two 64-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsltD(a: V2f64, b: V2f64): V2i64 = externMsaFsltD(a, b)

/**
 * Vector Floating-Point Signaling Compare Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are not equal, otherwise set all bits to 0.
 */
public fun msaFsneW(a: V4f32, b: V4f32): V4i32 = externMsaFsneW(a, b)

/**
 * Vector Floating-Point Signaling Compare Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are not equal, otherwise set all bits to 0.
 */
public fun msaFsneD(a: V2f64, b: V2f64): V2i64 = externMsaFsneD(a, b)

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered,
 * i.e. both elements are not NaN values, otherwise set all bits to 0.
 */
public fun msaFsorW(a: V4f32, b: V4f32): V4i32 = externMsaFsorW(a, b)

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered,
 * i.e. both elements are not NaN values, otherwise set all bits to 0.
 */
public fun msaFsorD(a: V2f64, b: V2f64): V2i64 = externMsaFsorD(a, b)

/**
 * Vector Floating-Point Square Root
 *
 * The square roots of floating-point elements in vector `a`
 * (four 32-bit floating point numbers) are written to vector
 * (four 32-bit floating point numbers) elements are ordered,.
 */
public fun msaFsqrtW(a: V4f32): V4f32 = externMsaFsqrtW(a)

/**
 * Vector Floating-Point Square Root
 *
 * The square roots of floating-point elements in vector `a`
 * (two 64-bit floating point numbers) are written to vector
 * (two 64-bit floating point numbers) elements are ordered,.
 */
public fun msaFsqrtD(a: V2f64): V2f64 = externMsaFsqrtD(a)

/**
 * Vector Floating-Point Subtraction
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a`
 * (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFsubW(a: V4f32, b: V4f32): V4f32 = externMsaFsubW(a, b)

/**
 * Vector Floating-Point Subtraction
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a`
 * (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFsubD(a: V2f64, b: V2f64): V2f64 = externMsaFsubD(a, b)

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFsueqW(a: V4f32, b: V4f32): V4i32 = externMsaFsueqW(a, b)

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFsueqD(a: V2f64, b: V2f64): V2i64 = externMsaFsueqD(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements are
 * unordered or less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsuleW(a: V4f32, b: V4f32): V4i32 = externMsaFsuleW(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements are
 * unordered or less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsuleD(a: V2f64, b: V2f64): V2i64 = externMsaFsuleD(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are unordered or less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsultW(a: V4f32, b: V4f32): V4i32 = externMsaFsultW(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are unordered or less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsultD(a: V2f64, b: V2f64): V2i64 = externMsaFsultD(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFsunW(a: V4f32, b: V4f32): V4i32 = externMsaFsunW(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFsunD(a: V2f64, b: V2f64): V2i64 = externMsaFsunD(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFsuneW(a: V4f32, b: V4f32): V4i32 = externMsaFsuneW(a, b)

/**
 * Vector Floating-Point Signaling Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFsuneD(a: V2f64, b: V2f64): V2i64 = externMsaFsuneD(a, b)

/**
 * Vector Floating-Point Convert to Signed Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtintSW(a: V4f32): V4i32 = externMsaFtintSW(a)

/**
 * Vector Floating-Point Convert to Signed Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaFtintSD(a: V2f64): V2i64 = externMsaFtintSD(a)

/**
 * Vector Floating-Point Convert to Unsigned Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaFtintUW(a: V4f32): V4u32 = externMsaFtintUW(a)

/**
 * Vector Floating-Point Convert to Unsigned Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaFtintUD(a: V2f64): V2u64 = externMsaFtintUD(a)

/**
 * Vector Floating-Point Convert to Fixed-Point
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) are down-converted to a fixed-point
 * representation, i.e. from 64-bit floating-point to 32-bit Q31 fixed-point
 * representation, or from 32-bit floating-point to 16-bit Q15 fixed-point representation.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaFtqH(a: V4f32, b: V4f32): V8i16 = externMsaFtqH(a, b)

/**
 * Vector Floating-Point Convert to Fixed-Point
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) are down-converted to a fixed-point
 * representation, i.e. from 64-bit floating-point to 32-bit Q31 fixed-point
 * representation, or from 32-bit floating-point to 16-bit Q15 fixed-point representation.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtqW(a: V2f64, b: V2f64): V4i32 = externMsaFtqW(a, b)

/**
 * Vector Floating-Point Truncate and Convert to Signed Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to signed integer values.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtruncSW(a: V4f32): V4i32 = externMsaFtruncSW(a)

/**
 * Vector Floating-Point Truncate and Convert to Signed Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to signed integer values.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaFtruncSD(a: V2f64): V2i64 = externMsaFtruncSD(a)

/**
 * Vector Floating-Point Truncate and Convert to Unsigned Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to unsigned integer values.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaFtruncUW(a: V4f32): V4u32 = externMsaFtruncUW(a)

/**
 * Vector Floating-Point Truncate and Convert to Unsigned Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to unsigned integer values.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaFtruncUD(a: V2f64): V2u64 = externMsaFtruncUD(a)

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHaddSH(a: V16i8, b: V16i8): V8i16 = externMsaHaddSH(a, b)

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (eight signed 16-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHaddSW(a: V8i16, b: V8i16): V4i32 = externMsaHaddSW(a, b)

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (four signed 32-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHaddSD(a: V4i32, b: V4i32): V2i64 = externMsaHaddSD(a, b)

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaHaddUH(a: V16u8, b: V16u8): V8u16 = externMsaHaddUH(a, b)

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaHaddUW(a: V8u16, b: V8u16): V4u32 = externMsaHaddUW(a, b)

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (four unsigned 32-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaHaddUD(a: V4u32, b: V4u32): V2u64 = externMsaHaddUD(a, b)

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHsubSH(a: V16i8, b: V16i8): V8i16 = externMsaHsubSH(a, b)

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHsubSW(a: V8i16, b: V8i16): V4i32 = externMsaHsubSW(a, b)

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHsubSD(a: V4i32, b: V4i32): V2i64 = externMsaHsubSD(a, b)

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHsubUH(a: V16u8, b: V16u8): V8i16 = externMsaHsubUH(a, b)

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHsubUW(a: V8u16, b: V8u16): V4i32 = externMsaHsubUW(a, b)

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHsubUD(a: V4u32, b: V4u32): V2i64 = externMsaHsubUD(a, b)

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevB(a: V16i8, b: V16i8): V16i8 = externMsaIlvevB(a, b)

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevH(a: V8i16, b: V8i16): V8i16 = externMsaIlvevH(a, b)

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevW(a: V4i32, b: V4i32): V4i32 = externMsaIlvevW(a, b)

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevD(a: V2i64, b: V2i64): V2i64 = externMsaIlvevD(a, b)

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlB(a: V16i8, b: V16i8): V16i8 = externMsaIlvlB(a, b)

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlH(a: V8i16, b: V8i16): V8i16 = externMsaIlvlH(a, b)

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlW(a: V4i32, b: V4i32): V4i32 = externMsaIlvlW(a, b)

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlD(a: V2i64, b: V2i64): V2i64 = externMsaIlvlD(a, b)

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodB(a: V16i8, b: V16i8): V16i8 = externMsaIlvodB(a, b)

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodH(a: V8i16, b: V8i16): V8i16 = externMsaIlvodH(a, b)

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodW(a: V4i32, b: V4i32): V4i32 = externMsaIlvodW(a, b)

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodD(a: V2i64, b: V2i64): V2i64 = externMsaIlvodD(a, b)

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrB(a: V16i8, b: V16i8): V16i8 = externMsaIlvrB(a, b)

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrH(a: V8i16, b: V8i16): V8i16 = externMsaIlvrH(a, b)

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrW(a: V4i32, b: V4i32): V4i32 = externMsaIlvrW(a, b)

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrD(a: V2i64, b: V2i64): V2i64 = externMsaIlvrD(a, b)

/**
 * GPR Insert Element
 *
 * Set element `imm4` in vector `a` (sixteen signed 8-bit integer numbers) to GPR `c` value.
 * All other elements in vector `a` are unchanged. If the source GPR is wider than the
 * destination data format, the destination's elements will be set to the least significant bits of the GPR.
 */
public fun msaInsertB(a: V16i8, c: Int, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaInsertB(a, imm4, c)
}

/**
 * GPR Insert Element
 *
 * Set element `imm3` in vector `a` (eight signed 16-bit integer numbers) to GPR `c` value.
 * All other elements in vector `a` are unchanged. If the source GPR is wider than the
 * destination data format, the destination's elements will be set to the least significant bits of the GPR.
 */
public fun msaInsertH(a: V8i16, c: Int, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaInsertH(a, imm3, c)
}

/**
 * GPR Insert Element
 *
 * Set element `imm2` in vector `a` (four signed 32-bit integer numbers) to GPR `c` value.
 * All other elements in vector `a` are unchanged. If the source GPR is wider than the
 * destination data format, the destination's elements will be set to the least significant bits of the GPR.
 */
public fun msaInsertW(a: V4i32, c: Int, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaInsertW(a, imm2, c)
}

/**
 * GPR Insert Element
 *
 * Set element `imm1` in vector `a` (two signed 64-bit integer numbers) to GPR `c` value.
 * All other elements in vector `a` are unchanged. If the source GPR is wider than the
 * destination data format, the destination's elements will be set to the least significant bits of the GPR.
 */
public fun msaInsertD(a: V2i64, c: Long, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaInsertD(a, imm1, c)
}

/**
 * Element Insert Element
 *
 * Set element `imm1` in the result vector `a` (sixteen signed 8-bit integer numbers) to element 0
 * in vector `c` (sixteen signed 8-bit integer numbers) value.
 * All other elements in vector `a` are unchanged.
 */
public fun msaInsveB(a: V16i8, c: V16i8, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaInsveB(a, imm4, c)
}

/**
 * Element Insert Element
 *
 * Set element `imm1` in the result vector `a` (eight signed 16-bit integer numbers) to element 0
 * in vector `c` (eight signed 16-bit integer numbers) value.
 * All other elements in vector `a` are unchanged.
 */
public fun msaInsveH(a: V8i16, c: V8i16, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaInsveH(a, imm3, c)
}

/**
 * Element Insert Element
 *
 * Set element `imm1` in the result vector `a` (four signed 32-bit integer numbers) to element 0
 * in vector `c` (four signed 32-bit integer numbers) value.
 * All other elements in vector `a` are unchanged.
 */
public fun msaInsveW(a: V4i32, c: V4i32, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaInsveW(a, imm2, c)
}

/**
 * Element Insert Element
 *
 * Set element `imm1` in the result vector `a` (two signed 64-bit integer numbers) to element 0
 * in vector `c` (two signed 64-bit integer numbers) value.
 * All other elements in vector `a` are unchanged.
 */
public fun msaInsveD(a: V2i64, c: V2i64, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaInsveD(a, imm1, c)
}

/**
 * Vector Load
 *
 * The WRLEN / 8 bytes at the effective memory location addressed by the base
 * `memAddr` and the 10-bit signed immediate offset `immS10` are fetched and placed in
 * the vector (sixteen signed 8-bit integer numbers) value.
 */
public fun msaLdB(memAddr: MutPtr, immS10: Int): V16i8 {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return externMsaLdB(memAddr, immS10)
}

/**
 * Vector Load
 *
 * The WRLEN / 8 bytes at the effective memory location addressed by the base
 * `memAddr` and the 10-bit signed immediate offset `immS11` are fetched and placed in
 * the vector (eight signed 16-bit integer numbers) value.
 */
public fun msaLdH(memAddr: MutPtr, immS11: Int): V8i16 {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    run {
        check(immS11 % 2 == 0)
    }
    return externMsaLdH(memAddr, immS11)
}

/**
 * Vector Load
 *
 * The WRLEN / 8 bytes at the effective memory location addressed by the base
 * `memAddr` and the 10-bit signed immediate offset `immS12` are fetched and placed in
 * the vector (four signed 32-bit integer numbers) value.
 */
public fun msaLdW(memAddr: MutPtr, immS12: Int): V4i32 {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    run {
        check(immS12 % 4 == 0)
    }
    return externMsaLdW(memAddr, immS12)
}

/**
 * Vector Load
 *
 * The WRLEN / 8 bytes at the effective memory location addressed by the base
 * `memAddr` and the 10-bit signed immediate offset `immS13` are fetched and placed in
 * the vector (two signed 64-bit integer numbers) value.
 */
public fun msaLdD(memAddr: MutPtr, immS13: Int): V2i64 {
    run {
        check((-1 shl (13 - 1).toInt()) - 1 <= immS13 && immS13 < (1 shl (13 - 1).toInt())) { "IMM_S13 doesn't fit in 13 bits (signed)" }
    }
    run {
        check(immS13 % 8 == 0)
    }
    return externMsaLdD(memAddr, immS13)
}

/**
 * Immediate Load
 *
 * The signed immediate immS10 is replicated in all vector
 * (sixteen signed 8-bit integer numbers) elements. For byte elements,
 * only the least significant 8 bits of immS10 will be used.
 */
public fun msaLdiB(immS10: Int): V16i8 {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return externMsaLdiB(immS10)
}

/**
 * Immediate Load
 *
 * The signed immediate immS10 is replicated in all vector
 * (eight signed 16-bit integer numbers) elements. For byte elements,
 * only the least significant 8 bits of immS10 will be used.
 */
public fun msaLdiH(immS10: Int): V8i16 {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return externMsaLdiH(immS10)
}

/**
 * Immediate Load
 *
 * The signed immediate immS10 is replicated in all vector
 * (four signed 32-bit integer numbers) elements. For byte elements,
 * only the least significant 8 bits of immS10 will be used.
 */
public fun msaLdiW(immS10: Int): V4i32 {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return externMsaLdiW(immS10)
}

/**
 * Immediate Load
 *
 * The signed immediate immS10 is replicated in all vector
 * (two signed 64-bit integer numbers) elements. For byte elements,
 * only the least significant 8 bits of immS10 will be used.
 */
public fun msaLdiD(immS10: Int): V2i64 {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return externMsaLdiD(immS10)
}

/**
 * Vector Fixed-Point Multiply and Add
 *
 * The products of fixed-point elements in `b` (eight signed 16-bit integer numbers)
 * by fixed-point elements in vector `c` (eight signed 16-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (eight signed 16-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMaddQH(a, b, c)

/**
 * Vector Fixed-Point Multiply and Add
 *
 * The products of fixed-point elements in `b` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (four signed 32-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMaddQW(a, b, c)

/**
 * Vector Fixed-Point Multiply and Add Rounded
 *
 * The products of fixed-point elements in `b` (eight signed 16-bit integer numbers)
 * by fixed-point elements in vector `c` (eight signed 16-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (eight signed 16-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The rounded and saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMaddrQH(a, b, c)

/**
 * Vector Fixed-Point Multiply and Add Rounded
 *
 * The products of fixed-point elements in `b` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (four signed 32-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The rounded and saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMaddrQW(a, b, c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * and added to the integer elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 = externMsaMaddvB(a, b, c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `c` (eight signed 16-bit integer numbers)
 * and added to the integer elements in vector `a` (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMaddvH(a, b, c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `c` (four signed 32-bit integer numbers)
 * and added to the integer elements in vector `a` (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMaddvW(a, b, c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `c` (two signed 64-bit integer numbers)
 * and added to the integer elements in vector `a` (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 = externMsaMaddvD(a, b, c)

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (sixteen signed 8-bit integer numbers) and
 * `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMaxAB(a: V16i8, b: V16i8): V16i8 = externMsaMaxAB(a, b)

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (eight signed 16-bit integer numbers) and
 * `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMaxAH(a: V8i16, b: V8i16): V8i16 = externMsaMaxAH(a, b)

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (four signed 32-bit integer numbers) and
 * `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMaxAW(a: V4i32, b: V4i32): V4i32 = externMsaMaxAW(a, b)

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (two signed 64-bit integer numbers) and
 * `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMaxAD(a: V2i64, b: V2i64): V2i64 = externMsaMaxAD(a, b)

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and signed elements in vector `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMaxSB(a: V16i8, b: V16i8): V16i8 = externMsaMaxSB(a, b)

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and signed elements in vector `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMaxSH(a: V8i16, b: V8i16): V8i16 = externMsaMaxSH(a, b)

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and signed elements in vector `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMaxSW(a: V4i32, b: V4i32): V4i32 = externMsaMaxSW(a, b)

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and signed elements in vector `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMaxSD(a: V2i64, b: V2i64): V2i64 = externMsaMaxSD(a, b)

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers) are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMaxUB(a: V16u8, b: V16u8): V16u8 = externMsaMaxUB(a, b)

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and unsigned elements in vector `b` (eight unsigned 16-bit integer numbers) are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMaxUH(a: V8u16, b: V8u16): V8u16 = externMsaMaxUH(a, b)

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and unsigned elements in vector `b` (four unsigned 32-bit integer numbers) are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMaxUW(a: V4u32, b: V4u32): V4u32 = externMsaMaxUW(a, b)

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and unsigned elements in vector `b` (two unsigned 64-bit integer numbers) are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMaxUD(a: V2u64, b: V2u64): V2u64 = externMsaMaxUD(a, b)

/**
 * Immediate Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMaxiSB(a: V16i8, immS5: Int): V16i8 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMaxiSB(a, immS5)
}

/**
 * Immediate Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMaxiSH(a: V8i16, immS5: Int): V8i16 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMaxiSH(a, immS5)
}

/**
 * Immediate Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMaxiSW(a: V4i32, immS5: Int): V4i32 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMaxiSW(a, immS5)
}

/**
 * Immediate Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMaxiSD(a: V2i64, immS5: Int): V2i64 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMaxiSD(a, immS5)
}

/**
 * Immediate Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMaxiUB(a: V16u8, imm5: Int): V16u8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMaxiUB(a, imm5)
}

/**
 * Immediate Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMaxiUH(a: V8u16, imm5: Int): V8u16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMaxiUH(a, imm5)
}

/**
 * Immediate Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMaxiUW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMaxiUW(a, imm5)
}

/**
 * Immediate Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMaxiUD(a: V2u64, imm5: Int): V2u64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMaxiUD(a, imm5)
}

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (sixteen signed 8-bit integer numbers) and
 * `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMinAB(a: V16i8, b: V16i8): V16i8 = externMsaMinAB(a, b)

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (eight signed 16-bit integer numbers) and
 * `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMinAH(a: V8i16, b: V8i16): V8i16 = externMsaMinAH(a, b)

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (four signed 32-bit integer numbers) and
 * `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMinAW(a: V4i32, b: V4i32): V4i32 = externMsaMinAW(a, b)

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (two signed 64-bit integer numbers) and
 * `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMinAD(a: V2i64, b: V2i64): V2i64 = externMsaMinAD(a, b)

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and signed elements in vector `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMinSB(a: V16i8, b: V16i8): V16i8 = externMsaMinSB(a, b)

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and signed elements in vector `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMinSH(a: V8i16, b: V8i16): V8i16 = externMsaMinSH(a, b)

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and signed elements in vector `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMinSW(a: V4i32, b: V4i32): V4i32 = externMsaMinSW(a, b)

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and signed elements in vector `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMinSD(a: V2i64, b: V2i64): V2i64 = externMsaMinSD(a, b)

/**
 * Immediate Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMiniSB(a: V16i8, immS5: Int): V16i8 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMiniSB(a, immS5)
}

/**
 * Immediate Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMiniSH(a: V8i16, immS5: Int): V8i16 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMiniSH(a, immS5)
}

/**
 * Immediate Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMiniSW(a: V4i32, immS5: Int): V4i32 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMiniSW(a, immS5)
}

/**
 * Immediate Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and the 5-bit signed immediate immS5 are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMiniSD(a: V2i64, immS5: Int): V2i64 {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= immS5 && immS5 < (1 shl (5 - 1).toInt())) { "IMM_S5 doesn't fit in 5 bits (signed)" }
    }
    return externMsaMiniSD(a, immS5)
}

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers) are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMinUB(a: V16u8, b: V16u8): V16u8 = externMsaMinUB(a, b)

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and unsigned elements in vector `b` (eight unsigned 16-bit integer numbers) are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMinUH(a: V8u16, b: V8u16): V8u16 = externMsaMinUH(a, b)

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and unsigned elements in vector `b` (four unsigned 32-bit integer numbers) are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMinUW(a: V4u32, b: V4u32): V4u32 = externMsaMinUW(a, b)

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and unsigned elements in vector `b` (two unsigned 64-bit integer numbers) are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMinUD(a: V2u64, b: V2u64): V2u64 = externMsaMinUD(a, b)

/**
 * Immediate Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMiniUB(a: V16u8, imm5: Int): V16u8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMiniUB(a, imm5)
}

/**
 * Immediate Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMiniUH(a: V8u16, imm5: Int): V8u16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMiniUH(a, imm5)
}

/**
 * Immediate Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMiniUW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMiniUW(a, imm5)
}

/**
 * Immediate Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and the 5-bit unsigned immediate `imm5` are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMiniUD(a: V2u64, imm5: Int): V2u64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaMiniUD(a, imm5)
}

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are divided by signed integer elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (sixteen signed 8-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSB(a: V16i8, b: V16i8): V16i8 = externMsaModSB(a, b)

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are divided by signed integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (eight signed 16-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSH(a: V8i16, b: V8i16): V8i16 = externMsaModSH(a, b)

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are divided by signed integer elements in vector `b` (four signed 32-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (four signed 32-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSW(a: V4i32, b: V4i32): V4i32 = externMsaModSW(a, b)

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are divided by signed integer elements in vector `b` (two signed 64-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (two signed 64-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSD(a: V2i64, b: V2i64): V2i64 = externMsaModSD(a, b)

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (sixteen unsigned 8-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUB(a: V16u8, b: V16u8): V16u8 = externMsaModUB(a, b)

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (eight unsigned 16-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUH(a: V8u16, b: V8u16): V8u16 = externMsaModUH(a, b)

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (four unsigned 32-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUW(a: V4u32, b: V4u32): V4u32 = externMsaModUW(a, b)

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (two unsigned 64-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (two unsigned 64-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUD(a: V2u64, b: V2u64): V2u64 = externMsaModUD(a, b)

/**
 * Vector Move
 *
 * Copy all WRLEN bits in vector `a` (eight signed 16-bit integer numbers)
 * to vector (eight signed 16-bit integer numbers).
 */
public fun msaMoveV(a: V16i8): V16i8 = externMsaMoveV(a)

/**
 * Vector Fixed-Point Multiply and Subtract
 *
 * The product of fixed-point elements in vector `c` (eight signed 16-bit integer numbers)
 * by fixed-point elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the fixed-point elements in vector `a`
 * (eight signed 16-bit integer numbers). The multiplication result is not saturated,
 * i.e. exact (-1) * (-1) = 1 is subtracted from the destination.
 * The saturated fixed-point results are stored back to vector `a`.
 */
public fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMsubQH(a, b, c)

/**
 * Vector Fixed-Point Multiply and Subtract
 *
 * The product of fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the fixed-point elements in vector `a`
 * (four signed 32-bit integer numbers). The multiplication result is not saturated,
 * i.e. exact (-1) * (-1) = 1 is subtracted from the destination.
 * The saturated fixed-point results are stored back to vector `a`.
 */
public fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMsubQW(a, b, c)

/**
 * Vector Fixed-Point Multiply and Subtract Rounded
 *
 * The product of fixed-point elements in vector `c` (eight signed 16-bit integer numbers)
 * by fixed-point elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the fixed-point elements in vector `a`
 * (eight signed 16-bit integer numbers). The multiplication result is not saturated,
 * i.e. exact (-1) * (-1) = 1 is subtracted from the destination.
 * The rounded and saturated fixed-point results are stored back to vector `a`.
 */
public fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMsubrQH(a, b, c)

/**
 * Vector Fixed-Point Multiply and Subtract Rounded
 *
 * The product of fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the fixed-point elements in vector `a`
 * (four signed 32-bit integer numbers). The multiplication result is not saturated,
 * i.e. exact (-1) * (-1) = 1 is subtracted from the destination.
 * The rounded and saturated fixed-point results are stored back to vector `a`.
 */
public fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMsubrQW(a, b, c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 = externMsaMsubvB(a, b, c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `b` (eight signed 16-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaMsubvH(a, b, c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `b` (four signed 32-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaMsubvW(a, b, c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `b` (two signed 64-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 = externMsaMsubvD(a, b, c)

/**
 * Vector Fixed-Point Multiply
 *
 * The fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaMulQH(a: V8i16, b: V8i16): V8i16 = externMsaMulQH(a, b)

/**
 * Vector Fixed-Point Multiply
 *
 * The fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaMulQW(a: V4i32, b: V4i32): V4i32 = externMsaMulQW(a, b)

/**
 * Vector Fixed-Point Multiply Rounded
 *
 * The fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (eight signed 16-bit integer numbers).
 * The rounded result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaMulrQH(a: V8i16, b: V8i16): V8i16 = externMsaMulrQH(a, b)

/**
 * Vector Fixed-Point Multiply Rounded
 *
 * The fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (four signed 32-bit integer numbers).
 * The rounded result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaMulrQW(a: V4i32, b: V4i32): V4i32 = externMsaMulrQW(a, b)

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvB(a: V16i8, b: V16i8): V16i8 = externMsaMulvB(a, b)

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvH(a: V8i16, b: V8i16): V8i16 = externMsaMulvH(a, b)

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvW(a: V4i32, b: V4i32): V4i32 = externMsaMulvW(a, b)

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvD(a: V2i64, b: V2i64): V2i64 = externMsaMulvD(a, b)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in vector (sixteen signed 8-bit integer numbers).
 */
public fun msaNlocB(a: V16i8): V16i8 = externMsaNlocB(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in vector (eight signed 16-bit integer numbers).
 */
public fun msaNlocH(a: V8i16): V8i16 = externMsaNlocH(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in vector (four signed 32-bit integer numbers).
 */
public fun msaNlocW(a: V4i32): V4i32 = externMsaNlocW(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in vector (two signed 64-bit integer numbers).
 */
public fun msaNlocD(a: V2i64): V2i64 = externMsaNlocD(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in vector (sixteen signed 8-bit integer numbers).
 */
public fun msaNlzcB(a: V16i8): V16i8 = externMsaNlzcB(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in vector (eight signed 16-bit integer numbers).
 */
public fun msaNlzcH(a: V8i16): V8i16 = externMsaNlzcH(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in vector (four signed 32-bit integer numbers).
 */
public fun msaNlzcW(a: V4i32): V4i32 = externMsaNlzcW(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in vector (two signed 64-bit integer numbers).
 */
public fun msaNlzcD(a: V2i64): V2i64 = externMsaNlzcD(a)

/**
 * Vector Logical Negated Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical NOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaNorV(a: V16u8, b: V16u8): V16u8 = externMsaNorV(a, b)

/**
 * Immediate Logical Negated Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the 8-bit immediate `imm8`
 * in a bitwise logical NOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaNoriB(a: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaNoriB(a, imm8)
}

/**
 * Vector Logical Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical OR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaOrV(a: V16u8, b: V16u8): V16u8 = externMsaOrV(a, b)

/**
 * Immediate Logical Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the 8-bit immediate `imm8`
 * in a bitwise logical OR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaOriB(a: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaOriB(a, imm8)
}

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (sixteen signed 8-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevB(a: V16i8, b: V16i8): V16i8 = externMsaPckevB(a, b)

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (eight signed 16-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (eight signed 16-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevH(a: V8i16, b: V8i16): V8i16 = externMsaPckevH(a, b)

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (four signed 32-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (four signed 32-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevW(a: V4i32, b: V4i32): V4i32 = externMsaPckevW(a, b)

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (two signed 64-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (two signed 64-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevD(a: V2i64, b: V2i64): V2i64 = externMsaPckevD(a, b)

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (sixteen signed 8-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodB(a: V16i8, b: V16i8): V16i8 = externMsaPckodB(a, b)

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (eight signed 16-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (eight signed 16-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodH(a: V8i16, b: V8i16): V8i16 = externMsaPckodH(a, b)

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (four signed 32-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (four signed 32-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodW(a: V4i32, b: V4i32): V4i32 = externMsaPckodW(a, b)

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (two signed 64-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (two signed 64-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodD(a: V2i64, b: V2i64): V2i64 = externMsaPckodD(a, b)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in the result vector (sixteen signed 8-bit integer numbers).
 */
public fun msaPcntB(a: V16i8): V16i8 = externMsaPcntB(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in the result vector (eight signed 16-bit integer numbers).
 */
public fun msaPcntH(a: V8i16): V8i16 = externMsaPcntH(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in the result vector (four signed 32-bit integer numbers).
 */
public fun msaPcntW(a: V4i32): V4i32 = externMsaPcntW(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in the result vector (two signed 64-bit integer numbers).
 */
public fun msaPcntD(a: V2i64): V2i64 = externMsaPcntD(a)

/**
 * Immediate Signed Saturate
 *
 * Signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are saturated to signed values of `imm3 + 1` bits without changing the data width.
 * The result is stored in the vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSatSB(a: V16i8, imm3: Int): V16i8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSatSB(a, imm3)
}

/**
 * Immediate Signed Saturate
 *
 * Signed elements in vector `a` (eight signed 16-bit integer numbers)
 * are saturated to signed values of `imm4 + 1` bits without changing the data width.
 * The result is stored in the vector (eight signed 16-bit integer numbers).
 */
public fun msaSatSH(a: V8i16, imm4: Int): V8i16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSatSH(a, imm4)
}

/**
 * Immediate Signed Saturate
 *
 * Signed elements in vector `a` (four signed 32-bit integer numbers)
 * are saturated to signed values of `imm5 + 1` bits without changing the data width.
 * The result is stored in the vector (four signed 32-bit integer numbers).
 */
public fun msaSatSW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSatSW(a, imm5)
}

/**
 * Immediate Signed Saturate
 *
 * Signed elements in vector `a` (two signed 64-bit integer numbers)
 * are saturated to signed values of `imm6 + 1` bits without changing the data width.
 * The result is stored in the vector (two signed 64-bit integer numbers).
 */
public fun msaSatSD(a: V2i64, imm6: Int): V2i64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaSatSD(a, imm6)
}

/**
 * Immediate Unsigned Saturate
 *
 * Unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are saturated to unsigned values of `imm3 + 1` bits without changing the data width.
 * The result is stored in the vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSatUB(a: V16u8, imm3: Int): V16u8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSatUB(a, imm3)
}

/**
 * Immediate Unsigned Saturate
 *
 * Unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are saturated to unsigned values of `imm4 + 1` bits without changing the data width.
 * The result is stored in the vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSatUH(a: V8u16, imm4: Int): V8u16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSatUH(a, imm4)
}

/**
 * Immediate Unsigned Saturate
 *
 * Unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * are saturated to unsigned values of `imm5 + 1` bits without changing the data width.
 * The result is stored in the vector (four unsigned 32-bit integer numbers).
 */
public fun msaSatUW(a: V4u32, imm5: Int): V4u32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSatUW(a, imm5)
}

/**
 * Immediate Unsigned Saturate
 *
 * Unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * are saturated to unsigned values of `imm6 + 1` bits without changing the data width.
 * The result is stored in the vector (two unsigned 64-bit integer numbers).
 */
public fun msaSatUD(a: V2u64, imm6: Int): V2u64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaSatUD(a, imm6)
}

/**
 * Immediate Set Shuffle Elements
 *
 * The set shuffle instruction works on 4-element sets.
 * All sets are shuffled in the same way: the element i82i+1..2i in `a`
 * (sixteen signed 8-bit integer numbers) is copied over the element i in result vector
 * (sixteen signed 8-bit integer numbers), where i is 0, 1, 2, 3.
 */
public fun msaShfB(a: V16i8, imm8: Int): V16i8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaShfB(a, imm8)
}

/**
 * Immediate Set Shuffle Elements
 *
 * The set shuffle instruction works on 4-element sets.
 * All sets are shuffled in the same way: the element i82i+1..2i in `a`
 * (eight signed 16-bit integer numbers) is copied over the element i in result vector
 * (eight signed 16-bit integer numbers), where i is 0, 1, 2, 3.
 */
public fun msaShfH(a: V8i16, imm8: Int): V8i16 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaShfH(a, imm8)
}

/**
 * Immediate Set Shuffle Elements
 *
 * The set shuffle instruction works on 4-element sets.
 * All sets are shuffled in the same way: the element i82i+1..2i in `a`
 * (four signed 32-bit integer numbers) is copied over the element i in result vector
 * (four signed 32-bit integer numbers), where i is 0, 1, 2, 3.
 */
public fun msaShfW(a: V4i32, imm8: Int): V4i32 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaShfW(a, imm8)
}

/**
 * GPR Columns Slide
 *
 * Vector registers `a` (sixteen signed 8-bit integer numbers) and `b`
 * (sixteen signed 8-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by the number of columns given in GPR `c`.
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 * GPR `c` value is interpreted modulo the number of columns in destination rectangle,
 * or equivalently, the number of data format df elements in the destination vector.
 */
public fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8 = externMsaSldB(a, b, c)

/**
 * GPR Columns Slide
 *
 * Vector registers `a` (eight signed 16-bit integer numbers) and `b`
 * (eight signed 16-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by the number of columns given in GPR `c`.
 * The result is written to vector (eight signed 16-bit integer numbers).
 * GPR `c` value is interpreted modulo the number of columns in destination rectangle,
 * or equivalently, the number of data format df elements in the destination vector.
 */
public fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16 = externMsaSldH(a, b, c)

/**
 * GPR Columns Slide
 *
 * Vector registers `a` (four signed 32-bit integer numbers) and `b`
 * (four signed 32-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by the number of columns given in GPR `c`.
 * The result is written to vector (four signed 32-bit integer numbers).
 * GPR `c` value is interpreted modulo the number of columns in destination rectangle,
 * or equivalently, the number of data format df elements in the destination vector.
 */
public fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32 = externMsaSldW(a, b, c)

/**
 * GPR Columns Slide
 *
 * Vector registers `a` (two signed 64-bit integer numbers) and `b`
 * (two signed 64-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by the number of columns given in GPR `c`.
 * The result is written to vector (two signed 64-bit integer numbers).
 * GPR `c` value is interpreted modulo the number of columns in destination rectangle,
 * or equivalently, the number of data format df elements in the destination vector.
 */
public fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64 = externMsaSldD(a, b, c)

/**
 * Immediate Columns Slide
 *
 * Vector registers `a` (sixteen signed 8-bit integer numbers) and `b`
 * (sixteen signed 8-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by `imm1` columns.
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSldiB(a: V16i8, b: V16i8, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSldiB(a, b, imm4)
}

/**
 * Immediate Columns Slide
 *
 * Vector registers `a` (eight signed 16-bit integer numbers) and `b`
 * (eight signed 16-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by `imm1` columns.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSldiH(a: V8i16, b: V8i16, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSldiH(a, b, imm3)
}

/**
 * Immediate Columns Slide
 *
 * Vector registers `a` (four signed 32-bit integer numbers) and `b`
 * (four signed 32-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by `imm1` columns.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSldiW(a: V4i32, b: V4i32, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaSldiW(a, b, imm2)
}

/**
 * Immediate Columns Slide
 *
 * Vector registers `a` (two signed 64-bit integer numbers) and `b`
 * (two signed 64-bit integer numbers) contain 2-dimensional byte arrays (rectangles)
 * stored row-wise with as many rows as bytes in integer data format df.
 * The two source rectangles `b` and `a` are concatenated horizontally in the order
 * they appear in the syntax, i.e. first `a` and then `b`. Place a new destination
 * rectangle over `b` and then slide it to the left over the concatenation of `a` and `b`
 * by `imm1` columns.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSldiD(a: V2i64, b: V2i64, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaSldiD(a, b, imm1)
}

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSllB(a: V16i8, b: V16i8): V16i8 = externMsaSllB(a, b)

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSllH(a: V8i16, b: V8i16): V8i16 = externMsaSllH(a, b)

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSllW(a: V4i32, b: V4i32): V4i32 = externMsaSllW(a, b)

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSllD(a: V2i64, b: V2i64): V2i64 = externMsaSllD(a, b)

/**
 * Immediate Shift Left
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted left by `imm4` bits.
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSlliB(a: V16i8, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSlliB(a, imm4)
}

/**
 * Immediate Shift Left
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted left by `imm3` bits.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSlliH(a: V8i16, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSlliH(a, imm3)
}

/**
 * Immediate Shift Left
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted left by `imm2` bits.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSlliW(a: V4i32, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaSlliW(a, imm2)
}

/**
 * Immediate Shift Left
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted left by `imm1` bits.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSlliD(a: V2i64, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaSlliD(a, imm1)
}

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (sixteen signed 8-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (sixteen signed 8-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatB(a: V16i8, b: Int): V16i8 = externMsaSplatB(a, transmute(b))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (eight signed 16-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (eight signed 16-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatH(a: V8i16, b: Int): V8i16 = externMsaSplatH(a, transmute(b))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (four signed 32-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (four signed 32-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatW(a: V4i32, b: Int): V4i32 = externMsaSplatW(a, transmute(b))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (two signed 64-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (two signed 64-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatD(a: V2i64, b: Int): V2i64 = externMsaSplatD(a, transmute(b))

/**
 * Immediate Element Splat
 *
 * Replicate element `imm4` in vector `a` (sixteen signed 8-bit integer numbers)
 * to all elements in vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSplatiB(a: V16i8, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSplatiB(a, imm4)
}

/**
 * Immediate Element Splat
 *
 * Replicate element `imm3` in vector `a` (eight signed 16-bit integer numbers)
 * to all elements in vector (eight signed 16-bit integer numbers).
 */
public fun msaSplatiH(a: V8i16, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSplatiH(a, imm3)
}

/**
 * Immediate Element Splat
 *
 * Replicate element `imm2` in vector `a` (four signed 32-bit integer numbers)
 * to all elements in vector (four signed 32-bit integer numbers).
 */
public fun msaSplatiW(a: V4i32, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaSplatiW(a, imm2)
}

/**
 * Immediate Element Splat
 *
 * Replicate element `imm1` in vector `a` (two signed 64-bit integer numbers)
 * to all elements in vector (two signed 64-bit integer numbers).
 */
public fun msaSplatiD(a: V2i64, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaSplatiD(a, imm1)
}

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSraB(a: V16i8, b: V16i8): V16i8 = externMsaSraB(a, b)

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSraH(a: V8i16, b: V8i16): V8i16 = externMsaSraH(a, b)

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSraW(a: V4i32, b: V4i32): V4i32 = externMsaSraW(a, b)

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSraD(a: V2i64, b: V2i64): V2i64 = externMsaSraD(a, b)

/**
 * Immediate Shift Right Arithmetic
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right arithmetic by `imm3` bits.
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSraiB(a: V16i8, imm3: Int): V16i8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSraiB(a, imm3)
}

/**
 * Immediate Shift Right Arithmetic
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by `imm4` bits.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSraiH(a: V8i16, imm4: Int): V8i16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSraiH(a, imm4)
}

/**
 * Immediate Shift Right Arithmetic
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by `imm5` bits.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSraiW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSraiW(a, imm5)
}

/**
 * Immediate Shift Right Arithmetic
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by `imm6` bits.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSraiD(a: V2i64, imm6: Int): V2i64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaSraiD(a, imm6)
}

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrarB(a: V16i8, b: V16i8): V16i8 = externMsaSrarB(a, b)

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrarH(a: V8i16, b: V8i16): V8i16 = externMsaSrarH(a, b)

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrarW(a: V4i32, b: V4i32): V4i32 = externMsaSrarW(a, b)

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrarD(a: V2i64, b: V2i64): V2i64 = externMsaSrarD(a, b)

/**
 * Immediate Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right arithmetic by `imm3` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrariB(a: V16i8, imm3: Int): V16i8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSrariB(a, imm3)
}

/**
 * Immediate Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by `imm4` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrariH(a: V8i16, imm4: Int): V8i16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSrariH(a, imm4)
}

/**
 * Immediate Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by `imm5` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrariW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSrariW(a, imm5)
}

/**
 * Immediate Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by `imm6` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrariD(a: V2i64, imm6: Int): V2i64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaSrariD(a, imm6)
}

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrlB(a: V16i8, b: V16i8): V16i8 = externMsaSrlB(a, b)

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrlH(a: V8i16, b: V8i16): V8i16 = externMsaSrlH(a, b)

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrlW(a: V4i32, b: V4i32): V4i32 = externMsaSrlW(a, b)

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrlD(a: V2i64, b: V2i64): V2i64 = externMsaSrlD(a, b)

/**
 * Immediate Shift Right Logical
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right logical by `imm4` bits.
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrliB(a: V16i8, imm4: Int): V16i8 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSrliB(a, imm4)
}

/**
 * Immediate Shift Right Logical
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by `imm3` bits.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrliH(a: V8i16, imm3: Int): V8i16 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSrliH(a, imm3)
}

/**
 * Immediate Shift Right Logical
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by `imm2` bits.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrliW(a: V4i32, imm2: Int): V4i32 {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externMsaSrliW(a, imm2)
}

/**
 * Immediate Shift Right Logical
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by `imm1` bits.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrliD(a: V2i64, imm1: Int): V2i64 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return externMsaSrliD(a, imm1)
}

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrlrB(a: V16i8, b: V16i8): V16i8 = externMsaSrlrB(a, b)

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrlrH(a: V8i16, b: V8i16): V8i16 = externMsaSrlrH(a, b)

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrlrW(a: V4i32, b: V4i32): V4i32 = externMsaSrlrW(a, b)

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrlrD(a: V2i64, b: V2i64): V2i64 = externMsaSrlrD(a, b)

/**
 * Immediate Shift Right Logical Rounded
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right logical by `imm6` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrlriB(a: V16i8, imm3: Int): V16i8 {
    run {
        check(0 <= imm3 && imm3 < (1 shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return externMsaSrlriB(a, imm3)
}

/**
 * Immediate Shift Right Logical Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by `imm6` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrlriH(a: V8i16, imm4: Int): V8i16 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return externMsaSrlriH(a, imm4)
}

/**
 * Immediate Shift Right Logical Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by `imm6` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrlriW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSrlriW(a, imm5)
}

/**
 * Immediate Shift Right Logical Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by `imm6` bits.The most significant
 * discarded bit is added to the shifted value (for rounding) and
 * the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrlriD(a: V2i64, imm6: Int): V2i64 {
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externMsaSrlriD(a, imm6)
}

/**
 * Vector Store
 *
 * The WRLEN / 8 bytes in vector `a` (sixteen signed 8-bit integer numbers)
 * are stored as elements of data format df at the effective memory location
 * addressed by the base `memAddr` and the 10-bit signed immediate offset `immS10`.
 */
public fun msaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    externMsaStB(a, memAddr, immS10)
}

/**
 * Vector Store
 *
 * The WRLEN / 8 bytes in vector `a` (eight signed 16-bit integer numbers)
 * are stored as elements of data format df at the effective memory location
 * addressed by the base `memAddr` and the 11-bit signed immediate offset `immS11`.
 */
public fun msaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    run {
        check(immS11 % 2 == 0)
    }
    externMsaStH(a, memAddr, immS11)
}

/**
 * Vector Store
 *
 * The WRLEN / 8 bytes in vector `a` (four signed 32-bit integer numbers)
 * are stored as elements of data format df at the effective memory location
 * addressed by the base `memAddr` and the 12-bit signed immediate offset `immS12`.
 */
public fun msaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    run {
        check(immS12 % 4 == 0)
    }
    externMsaStW(a, memAddr, immS12)
}

/**
 * Vector Store
 *
 * The WRLEN / 8 bytes in vector `a` (two signed 64-bit integer numbers)
 * are stored as elements of data format df at the effective memory location
 * addressed by the base `memAddr` and the 13-bit signed immediate offset `immS13`.
 */
public fun msaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
    run {
        check((-1 shl (13 - 1).toInt()) - 1 <= immS13 && immS13 < (1 shl (13 - 1).toInt())) { "IMM_S13 doesn't fit in 13 bits (signed)" }
    }
    run {
        check(immS13 % 8 == 0)
    }
    externMsaStD(a, memAddr, immS13)
}

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen signed 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSubsSB(a: V16i8, b: V16i8): V16i8 = externMsaSubsSB(a, b)

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight signed 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaSubsSH(a: V8i16, b: V8i16): V8i16 = externMsaSubsSH(a, b)

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four signed 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaSubsSW(a: V4i32, b: V4i32): V4i32 = externMsaSubsSW(a, b)

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two signed 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaSubsSD(a: V2i64, b: V2i64): V2i64 = externMsaSubsSD(a, b)

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsUB(a: V16u8, b: V16u8): V16u8 = externMsaSubsUB(a, b)

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight unsigned 16-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsUH(a: V8u16, b: V8u16): V8u16 = externMsaSubsUH(a, b)

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four unsigned 32-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsUW(a: V4u32, b: V4u32): V4u32 = externMsaSubsUW(a, b)

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (two unsigned 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two unsigned 64-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsUD(a: V2u64, b: V2u64): V2u64 = externMsaSubsUD(a, b)

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsusUB(a: V16u8, b: V16i8): V16u8 = externMsaSubsusUB(a, b)

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (eight unsigned 16-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsusUH(a: V8u16, b: V8i16): V8u16 = externMsaSubsusUH(a, b)

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (four signed 6432it integer numbers)
 * are subtracted from the unsigned elements in vector `a` (four unsigned 32-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsusUW(a: V4u32, b: V4i32): V4u32 = externMsaSubsusUW(a, b)

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (two unsigned 64-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsusUD(a: V2u64, b: V2i64): V2u64 = externMsaSubsusUD(a, b)

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8 = externMsaSubsuuSB(a, b)

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (eight unsigned 16-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16 = externMsaSubsuuSH(a, b)

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (four unsigned 32-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32 = externMsaSubsuuSW(a, b)

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (two unsigned 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (two unsigned 64-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64 = externMsaSubsuuSD(a, b)

/**
 * Vector Subtract
 *
 * The elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSubvB(a: V16i8, b: V16i8): V16i8 = externMsaSubvB(a, b)

/**
 * Vector Subtract
 *
 * The elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSubvH(a: V8i16, b: V8i16): V8i16 = externMsaSubvH(a, b)

/**
 * Vector Subtract
 *
 * The elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSubvW(a: V4i32, b: V4i32): V4i32 = externMsaSubvW(a, b)

/**
 * Vector Subtract
 *
 * The elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSubvD(a: V2i64, b: V2i64): V2i64 = externMsaSubvD(a, b)

/**
 * Immediate Subtract
 *
 * The 5-bit immediate unsigned value `imm5`
 * are subtracted from the elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSubviB(a: V16i8, imm5: Int): V16i8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSubviB(a, imm5)
}

/**
 * Immediate Subtract
 *
 * The 5-bit immediate unsigned value `imm5`
 * are subtracted from the elements in vector `a` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSubviH(a: V8i16, imm5: Int): V8i16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSubviH(a, imm5)
}

/**
 * Immediate Subtract
 *
 * The 5-bit immediate unsigned value `imm5`
 * are subtracted from the elements in vector `a` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSubviW(a: V4i32, imm5: Int): V4i32 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSubviW(a, imm5)
}

/**
 * Immediate Subtract
 *
 * The 5-bit immediate unsigned value `imm5`
 * are subtracted from the elements in vector `a` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSubviD(a: V2i64, imm5: Int): V2i64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return externMsaSubviD(a, imm5)
}

/**
 * Vector Data Preserving Shuffle
 *
 * The vector shuffle instructions selectively copy data elements from the
 * concatenation of vectors `b` (sixteen signed 8-bit integer numbers)
 * and `c` (sixteen signed 8-bit integer numbers) in to vector `a`
 * (sixteen signed 8-bit integer numbers) based on the corresponding control element in `a`.
 * The least significant 6 bits in `a` control elements modulo the number of elements in
 * the concatenated vectors `b`, `a` specify the index of the source element.
 * If bit 6 or bit 7 is 1, there will be no copy, but rather the destination element is set to 0.
 */
public fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 = externMsaVshfB(a, b, c)

/**
 * Vector Data Preserving Shuffle
 *
 * The vector shuffle instructions selectively copy data elements from the
 * concatenation of vectors `b` (eight signed 16-bit integer numbers)
 * and `c` (eight signed 16-bit integer numbers) in to vector `a`
 * (eight signed 16-bit integer numbers) based on the corresponding control element in `a`.
 * The least significant 6 bits in `a` control elements modulo the number of elements in
 * the concatenated vectors `b`, `a` specify the index of the source element.
 * If bit 6 or bit 7 is 1, there will be no copy, but rather the destination element is set to 0.
 */
public fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 = externMsaVshfH(a, b, c)

/**
 * Vector Data Preserving Shuffle
 *
 * The vector shuffle instructions selectively copy data elements from the
 * concatenation of vectors `b` (four signed 32-bit integer numbers)
 * and `c` (four signed 32-bit integer numbers) in to vector `a`
 * (four signed 32-bit integer numbers) based on the corresponding control element in `a`.
 * The least significant 6 bits in `a` control elements modulo the number of elements in
 * the concatenated vectors `b`, `a` specify the index of the source element.
 * If bit 6 or bit 7 is 1, there will be no copy, but rather the destination element is set to 0.
 */
public fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 = externMsaVshfW(a, b, c)

/**
 * Vector Data Preserving Shuffle
 *
 * The vector shuffle instructions selectively copy data elements from the
 * concatenation of vectors `b` (two signed 64-bit integer numbers)
 * and `c` (two signed 64-bit integer numbers) in to vector `a`
 * (two signed 64-bit integer numbers) based on the corresponding control element in `a`.
 * The least significant 6 bits in `a` control elements modulo the number of elements in
 * the concatenated vectors `b`, `a` specify the index of the source element.
 * If bit 6 or bit 7 is 1, there will be no copy, but rather the destination element is set to 0.
 */
public fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 = externMsaVshfD(a, b, c)

/**
 * Vector Logical Exclusive Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical XOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaXorV(a: V16u8, b: V16u8): V16u8 = externMsaXorV(a, b)

/**
 * Immediate Logical Exclusive Or
 *
 * Each byte of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the 8-bit immediate `imm8`
 * in a bitwise logical XOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaXoriB(a: V16u8, imm8: Int): V16u8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return externMsaXoriB(a, imm8)
}

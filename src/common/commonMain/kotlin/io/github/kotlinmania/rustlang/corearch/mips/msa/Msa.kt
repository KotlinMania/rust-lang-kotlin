// port-lint: source mips/msa.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.Simd
import io.github.kotlinmania.rustlang.simd.debugSimdFinish

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
    public var value: ByteArray,
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
    public open fun asMutArray(): ByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.byteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v16i8", this.asArray())

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
        public fun splat(value: Byte): V16i8 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Byte>): V16i8 = transmute(simd)
    }
}

public fun fromSimdByte(simd: V16i8): Simd<Byte> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 8 packed `Short`.
 */
public open class V8i16(
    public var value: ShortArray,
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
    public open fun asMutArray(): ShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.shortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v8i16", this.asArray())

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
        public fun splat(value: Short): V8i16 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Short>): V8i16 = transmute(simd)
    }
}

public fun fromSimdShort(simd: V8i16): Simd<Short> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 4 packed `Int`.
 */
public open class V4i32(
    public var value: IntArray,
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
    public open fun asMutArray(): IntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.intArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v4i32", this.asArray())

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
        public fun splat(value: Int): V4i32 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Int>): V4i32 = transmute(simd)
    }
}

public fun fromSimdInt(simd: V4i32): Simd<Int> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 2 packed `Long`.
 */
public open class V2i64(
    public var value: LongArray,
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
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v2i64", this.asArray())

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
        public fun splat(value: Long): V2i64 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): V2i64 = transmute(simd)
    }
}

public fun fromSimdLong(simd: V2i64): Simd<Long> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 16 packed `UByte`.
 */
public open class V16u8(
    public var value: UByteArray,
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
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v16u8", this.asArray())

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
        public fun splat(value: UByte): V16u8 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UByte>): V16u8 = transmute(simd)
    }
}

public fun fromSimdUByte(simd: V16u8): Simd<UByte> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 8 packed `UShort`.
 */
public open class V8u16(
    public var value: UShortArray,
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
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v8u16", this.asArray())

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
        public fun splat(value: UShort): V8u16 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): V8u16 = transmute(simd)
    }
}

public fun fromSimdUShort(simd: V8u16): Simd<UShort> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 4 packed `UInt`.
 */
public open class V4u32(
    public var value: UIntArray,
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
    public open fun asMutArray(): UIntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uIntArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v4u32", this.asArray())

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
        public fun splat(value: UInt): V4u32 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UInt>): V4u32 = transmute(simd)
    }
}

public fun fromSimdUInt(simd: V4u32): Simd<UInt> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 2 packed `ULong`.
 */
public open class V2u64(
    public var value: ULongArray,
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
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v2u64", this.asArray())

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
        public fun splat(value: ULong): V2u64 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<ULong>): V2u64 = transmute(simd)
    }
}

public fun fromSimdULong(simd: V2u64): Simd<ULong> = transmute(simd)

public open class V4f32(
    public var value: FloatArray,
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
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v4f32", this.asArray())

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
        public fun splat(value: Float): V4f32 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): V4f32 = transmute(simd)
    }
}

public fun fromSimdFloat(simd: V4f32): Simd<Float> = transmute(simd)

/**
 * MIPS-specific 128-bit wide vector of 2 packed `Double`.
 */
public open class V2f64(
    public var value: DoubleArray,
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
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "v2f64", this.asArray())

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
        public fun splat(value: Double): V2f64 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): V2f64 = transmute(simd)
    }
}

public fun fromSimdDouble(simd: V2f64): Simd<Double> = transmute(simd)

public expect fun msaAddAB(a: V16i8, b: V16i8): V16i8

public expect fun msaAddAH(a: V8i16, b: V8i16): V8i16

public expect fun msaAddAW(a: V4i32, b: V4i32): V4i32

public expect fun msaAddAD(a: V2i64, b: V2i64): V2i64

public expect fun msaAddsAB(a: V16i8, b: V16i8): V16i8

public expect fun msaAddsAH(a: V8i16, b: V8i16): V8i16

public expect fun msaAddsAW(a: V4i32, b: V4i32): V4i32

public expect fun msaAddsAD(a: V2i64, b: V2i64): V2i64

public expect fun msaAddsSB(a: V16i8, b: V16i8): V16i8

public expect fun msaAddsSH(a: V8i16, b: V8i16): V8i16

public expect fun msaAddsSW(a: V4i32, b: V4i32): V4i32

public expect fun msaAddsSD(a: V2i64, b: V2i64): V2i64

public expect fun msaAddsUB(a: V16u8, b: V16u8): V16u8

public expect fun msaAddsUH(a: V8u16, b: V8u16): V8u16

public expect fun msaAddsUW(a: V4u32, b: V4u32): V4u32

public expect fun msaAddsUD(a: V2u64, b: V2u64): V2u64

public expect fun msaAddvB(a: V16i8, b: V16i8): V16i8

public expect fun msaAddvH(a: V8i16, b: V8i16): V8i16

public expect fun msaAddvW(a: V4i32, b: V4i32): V4i32

public expect fun msaAddvD(a: V2i64, b: V2i64): V2i64

public expect fun msaAddviB(a: V16i8, b: Int): V16i8

public expect fun msaAddviH(a: V8i16, b: Int): V8i16

public expect fun msaAddviW(a: V4i32, b: Int): V4i32

public expect fun msaAddviD(a: V2i64, b: Int): V2i64

public expect fun msaAndV(a: V16u8, b: V16u8): V16u8

public expect fun msaAndiB(a: V16u8, b: Int): V16u8

public expect fun msaAsubSB(a: V16i8, b: V16i8): V16i8

public expect fun msaAsubSH(a: V8i16, b: V8i16): V8i16

public expect fun msaAsubSW(a: V4i32, b: V4i32): V4i32

public expect fun msaAsubSD(a: V2i64, b: V2i64): V2i64

public expect fun msaAsubUB(a: V16u8, b: V16u8): V16u8

public expect fun msaAsubUH(a: V8u16, b: V8u16): V8u16

public expect fun msaAsubUW(a: V4u32, b: V4u32): V4u32

public expect fun msaAsubUD(a: V2u64, b: V2u64): V2u64

public expect fun msaAveSB(a: V16i8, b: V16i8): V16i8

public expect fun msaAveSH(a: V8i16, b: V8i16): V8i16

public expect fun msaAveSW(a: V4i32, b: V4i32): V4i32

public expect fun msaAveSD(a: V2i64, b: V2i64): V2i64

public expect fun msaAveUB(a: V16u8, b: V16u8): V16u8

public expect fun msaAveUH(a: V8u16, b: V8u16): V8u16

public expect fun msaAveUW(a: V4u32, b: V4u32): V4u32

public expect fun msaAveUD(a: V2u64, b: V2u64): V2u64

public expect fun msaAverSB(a: V16i8, b: V16i8): V16i8

public expect fun msaAverSH(a: V8i16, b: V8i16): V8i16

public expect fun msaAverSW(a: V4i32, b: V4i32): V4i32

public expect fun msaAverSD(a: V2i64, b: V2i64): V2i64

public expect fun msaAverUB(a: V16u8, b: V16u8): V16u8

public expect fun msaAverUH(a: V8u16, b: V8u16): V8u16

public expect fun msaAverUW(a: V4u32, b: V4u32): V4u32

public expect fun msaAverUD(a: V2u64, b: V2u64): V2u64

public expect fun msaBclrB(a: V16u8, b: V16u8): V16u8

public expect fun msaBclrH(a: V8u16, b: V8u16): V8u16

public expect fun msaBclrW(a: V4u32, b: V4u32): V4u32

public expect fun msaBclrD(a: V2u64, b: V2u64): V2u64

public expect fun msaBclriB(a: V16u8, b: Int): V16u8

public expect fun msaBclriH(a: V8u16, b: Int): V8u16

public expect fun msaBclriW(a: V4u32, b: Int): V4u32

public expect fun msaBclriD(a: V2u64, b: Int): V2u64

public expect fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16

public expect fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32

public expect fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun msaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun msaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16

public expect fun msaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32

public expect fun msaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64

public expect fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16

public expect fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32

public expect fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun msaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun msaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16

public expect fun msaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32

public expect fun msaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64

public expect fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun msaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun msaBmziB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun msaBnegB(a: V16u8, b: V16u8): V16u8

public expect fun msaBnegH(a: V8u16, b: V8u16): V8u16

public expect fun msaBnegW(a: V4u32, b: V4u32): V4u32

public expect fun msaBnegD(a: V2u64, b: V2u64): V2u64

public expect fun msaBnegiB(a: V16u8, b: Int): V16u8

public expect fun msaBnegiH(a: V8u16, b: Int): V8u16

public expect fun msaBnegiW(a: V4u32, b: Int): V4u32

public expect fun msaBnegiD(a: V2u64, b: Int): V2u64

public expect fun msaBnzB(a: V16u8): Int

public expect fun msaBnzH(a: V8u16): Int

public expect fun msaBnzW(a: V4u32): Int

public expect fun msaBnzD(a: V2u64): Int

public expect fun msaBnzV(a: V16u8): Int

public expect fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun msaBseliB(a: V16u8, b: V16u8, c: Int): V16u8

public expect fun msaBsetB(a: V16u8, b: V16u8): V16u8

public expect fun msaBsetH(a: V8u16, b: V8u16): V8u16

public expect fun msaBsetW(a: V4u32, b: V4u32): V4u32

public expect fun msaBsetD(a: V2u64, b: V2u64): V2u64

public expect fun msaBsetiB(a: V16u8, b: Int): V16u8

public expect fun msaBsetiH(a: V8u16, b: Int): V8u16

public expect fun msaBsetiW(a: V4u32, b: Int): V4u32

public expect fun msaBsetiD(a: V2u64, b: Int): V2u64

public expect fun msaBzB(a: V16u8): Int

public expect fun msaBzH(a: V8u16): Int

public expect fun msaBzW(a: V4u32): Int

public expect fun msaBzD(a: V2u64): Int

public expect fun msaBzV(a: V16u8): Int

public expect fun msaCeqB(a: V16i8, b: V16i8): V16i8

public expect fun msaCeqH(a: V8i16, b: V8i16): V8i16

public expect fun msaCeqW(a: V4i32, b: V4i32): V4i32

public expect fun msaCeqD(a: V2i64, b: V2i64): V2i64

public expect fun msaCeqiB(a: V16i8, b: Int): V16i8

public expect fun msaCeqiH(a: V8i16, b: Int): V8i16

public expect fun msaCeqiW(a: V4i32, b: Int): V4i32

public expect fun msaCeqiD(a: V2i64, b: Int): V2i64

public expect fun msaCfcmsa(a: Int): Int

public expect fun msaCleSB(a: V16i8, b: V16i8): V16i8

public expect fun msaCleSH(a: V8i16, b: V8i16): V8i16

public expect fun msaCleSW(a: V4i32, b: V4i32): V4i32

public expect fun msaCleSD(a: V2i64, b: V2i64): V2i64

public expect fun msaCleUB(a: V16u8, b: V16u8): V16i8

public expect fun msaCleUH(a: V8u16, b: V8u16): V8i16

public expect fun msaCleUW(a: V4u32, b: V4u32): V4i32

public expect fun msaCleUD(a: V2u64, b: V2u64): V2i64

public expect fun msaCleiSB(a: V16i8, b: Int): V16i8

public expect fun msaCleiSH(a: V8i16, b: Int): V8i16

public expect fun msaCleiSW(a: V4i32, b: Int): V4i32

public expect fun msaCleiSD(a: V2i64, b: Int): V2i64

public expect fun msaCleiUB(a: V16u8, b: Int): V16i8

public expect fun msaCleiUH(a: V8u16, b: Int): V8i16

public expect fun msaCleiUW(a: V4u32, b: Int): V4i32

public expect fun msaCleiUD(a: V2u64, b: Int): V2i64

public expect fun msaCltSB(a: V16i8, b: V16i8): V16i8

public expect fun msaCltSH(a: V8i16, b: V8i16): V8i16

public expect fun msaCltSW(a: V4i32, b: V4i32): V4i32

public expect fun msaCltSD(a: V2i64, b: V2i64): V2i64

public expect fun msaCltUB(a: V16u8, b: V16u8): V16i8

public expect fun msaCltUH(a: V8u16, b: V8u16): V8i16

public expect fun msaCltUW(a: V4u32, b: V4u32): V4i32

public expect fun msaCltUD(a: V2u64, b: V2u64): V2i64

public expect fun msaCltiSB(a: V16i8, b: Int): V16i8

public expect fun msaCltiSH(a: V8i16, b: Int): V8i16

public expect fun msaCltiSW(a: V4i32, b: Int): V4i32

public expect fun msaCltiSD(a: V2i64, b: Int): V2i64

public expect fun msaCltiUB(a: V16u8, b: Int): V16i8

public expect fun msaCltiUH(a: V8u16, b: Int): V8i16

public expect fun msaCltiUW(a: V4u32, b: Int): V4i32

public expect fun msaCltiUD(a: V2u64, b: Int): V2i64

public expect fun msaCopySB(a: V16i8, b: Int): Int

public expect fun msaCopySH(a: V8i16, b: Int): Int

public expect fun msaCopySW(a: V4i32, b: Int): Int

public expect fun msaCopySD(a: V2i64, b: Int): Long

public expect fun msaCopyUB(a: V16i8, b: Int): UInt

public expect fun msaCopyUH(a: V8i16, b: Int): UInt

public expect fun msaCopyUW(a: V4i32, b: Int): UInt

public expect fun msaCopyUD(a: V2i64, b: Int): ULong

public expect fun msaCtcmsa(imm5: Int, a: Int)

public expect fun msaDivSB(a: V16i8, b: V16i8): V16i8

public expect fun msaDivSH(a: V8i16, b: V8i16): V8i16

public expect fun msaDivSW(a: V4i32, b: V4i32): V4i32

public expect fun msaDivSD(a: V2i64, b: V2i64): V2i64

public expect fun msaDivUB(a: V16u8, b: V16u8): V16u8

public expect fun msaDivUH(a: V8u16, b: V8u16): V8u16

public expect fun msaDivUW(a: V4u32, b: V4u32): V4u32

public expect fun msaDivUD(a: V2u64, b: V2u64): V2u64

public expect fun msaDotpSH(a: V16i8, b: V16i8): V8i16

public expect fun msaDotpSW(a: V8i16, b: V8i16): V4i32

public expect fun msaDotpSD(a: V4i32, b: V4i32): V2i64

public expect fun msaDotpUH(a: V16u8, b: V16u8): V8u16

public expect fun msaDotpUW(a: V8u16, b: V8u16): V4u32

public expect fun msaDotpUD(a: V4u32, b: V4u32): V2u64

public expect fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16

public expect fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32

public expect fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64

public expect fun msaFaddW(a: V4f32, b: V4f32): V4f32

public expect fun msaFaddD(a: V2f64, b: V2f64): V2f64

public expect fun msaFcafW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcafD(a: V2f64, b: V2f64): V2i64

public expect fun msaFceqW(a: V4f32, b: V4f32): V4i32

public expect fun msaFceqD(a: V2f64, b: V2f64): V2i64

public expect fun msaFclassW(a: V4f32): V4i32

public expect fun msaFclassD(a: V2f64): V2i64

public expect fun msaFcleW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcleD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcltW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcltD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcneW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcneD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcorW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcorD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcueqW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcueqD(a: V2f64, b: V2f64): V2i64

public expect fun msaFculeW(a: V4f32, b: V4f32): V4i32

public expect fun msaFculeD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcultW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcultD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcunW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcunD(a: V2f64, b: V2f64): V2i64

public expect fun msaFcuneW(a: V4f32, b: V4f32): V4i32

public expect fun msaFcuneD(a: V2f64, b: V2f64): V2i64

public expect fun msaFdivW(a: V4f32, b: V4f32): V4f32

public expect fun msaFdivD(a: V2f64, b: V2f64): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexdo.h"
// fn msaFexdoH(a: v4f32, b: v4f32) -> f16x8;
public expect fun msaFexdoW(a: V2f64, b: V2f64): V4f32

public expect fun msaFexp2W(a: V4f32, b: V4i32): V4f32

public expect fun msaFexp2D(a: V2f64, b: V2i64): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexupl.w"
// fn msaFexuplW(a: f16x8) -> v4f32;
public expect fun msaFexuplD(a: V4f32): V2f64

// Note: 16-bit floats
// @linkName= "llvm.mips.fexupr.w"
// fn msaFexuprW(a: f16x8) -> v4f32;
public expect fun msaFexuprD(a: V4f32): V2f64

public expect fun msaFfintSW(a: V4i32): V4f32

public expect fun msaFfintSD(a: V2i64): V2f64

public expect fun msaFfintUW(a: V4u32): V4f32

public expect fun msaFfintUD(a: V2u64): V2f64

public expect fun msaFfqlW(a: V8i16): V4f32

public expect fun msaFfqlD(a: V4i32): V2f64

public expect fun msaFfqrW(a: V8i16): V4f32

public expect fun msaFfqrD(a: V4i32): V2f64

public expect fun msaFillB(a: Int): V16i8

public expect fun msaFillH(a: Int): V8i16

public expect fun msaFillW(a: Int): V4i32

public expect fun msaFillD(a: Long): V2i64

public expect fun msaFlog2W(a: V4f32): V4f32

public expect fun msaFlog2D(a: V2f64): V2f64

public expect fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32

public expect fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64

public expect fun msaFmaxW(a: V4f32, b: V4f32): V4f32

public expect fun msaFmaxD(a: V2f64, b: V2f64): V2f64

public expect fun msaFmaxAW(a: V4f32, b: V4f32): V4f32

public expect fun msaFmaxAD(a: V2f64, b: V2f64): V2f64

public expect fun msaFminW(a: V4f32, b: V4f32): V4f32

public expect fun msaFminD(a: V2f64, b: V2f64): V2f64

public expect fun msaFminAW(a: V4f32, b: V4f32): V4f32

public expect fun msaFminAD(a: V2f64, b: V2f64): V2f64

public expect fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32

public expect fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64

public expect fun msaFmulW(a: V4f32, b: V4f32): V4f32

public expect fun msaFmulD(a: V2f64, b: V2f64): V2f64

public expect fun msaFrintW(a: V4f32): V4f32

public expect fun msaFrintD(a: V2f64): V2f64

public expect fun msaFrcpW(a: V4f32): V4f32

public expect fun msaFrcpD(a: V2f64): V2f64

public expect fun msaFrsqrtW(a: V4f32): V4f32

public expect fun msaFrsqrtD(a: V2f64): V2f64

public expect fun msaFsafW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsafD(a: V2f64, b: V2f64): V2i64

public expect fun msaFseqW(a: V4f32, b: V4f32): V4i32

public expect fun msaFseqD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsleW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsleD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsltW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsltD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsneW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsneD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsorW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsorD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsqrtW(a: V4f32): V4f32

public expect fun msaFsqrtD(a: V2f64): V2f64

public expect fun msaFsubW(a: V4f32, b: V4f32): V4f32

public expect fun msaFsubD(a: V2f64, b: V2f64): V2f64

public expect fun msaFsueqW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsueqD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsuleW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsuleD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsultW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsultD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsunW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsunD(a: V2f64, b: V2f64): V2i64

public expect fun msaFsuneW(a: V4f32, b: V4f32): V4i32

public expect fun msaFsuneD(a: V2f64, b: V2f64): V2i64

public expect fun msaFtintSW(a: V4f32): V4i32

public expect fun msaFtintSD(a: V2f64): V2i64

public expect fun msaFtintUW(a: V4f32): V4u32

public expect fun msaFtintUD(a: V2f64): V2u64

public expect fun msaFtqH(a: V4f32, b: V4f32): V8i16

public expect fun msaFtqW(a: V2f64, b: V2f64): V4i32

public expect fun msaFtruncSW(a: V4f32): V4i32

public expect fun msaFtruncSD(a: V2f64): V2i64

public expect fun msaFtruncUW(a: V4f32): V4u32

public expect fun msaFtruncUD(a: V2f64): V2u64

public expect fun msaHaddSH(a: V16i8, b: V16i8): V8i16

public expect fun msaHaddSW(a: V8i16, b: V8i16): V4i32

public expect fun msaHaddSD(a: V4i32, b: V4i32): V2i64

public expect fun msaHaddUH(a: V16u8, b: V16u8): V8u16

public expect fun msaHaddUW(a: V8u16, b: V8u16): V4u32

public expect fun msaHaddUD(a: V4u32, b: V4u32): V2u64

public expect fun msaHsubSH(a: V16i8, b: V16i8): V8i16

public expect fun msaHsubSW(a: V8i16, b: V8i16): V4i32

public expect fun msaHsubSD(a: V4i32, b: V4i32): V2i64

public expect fun msaHsubUH(a: V16u8, b: V16u8): V8i16

public expect fun msaHsubUW(a: V8u16, b: V8u16): V4i32

public expect fun msaHsubUD(a: V4u32, b: V4u32): V2i64

public expect fun msaIlvevB(a: V16i8, b: V16i8): V16i8

public expect fun msaIlvevH(a: V8i16, b: V8i16): V8i16

public expect fun msaIlvevW(a: V4i32, b: V4i32): V4i32

public expect fun msaIlvevD(a: V2i64, b: V2i64): V2i64

public expect fun msaIlvlB(a: V16i8, b: V16i8): V16i8

public expect fun msaIlvlH(a: V8i16, b: V8i16): V8i16

public expect fun msaIlvlW(a: V4i32, b: V4i32): V4i32

public expect fun msaIlvlD(a: V2i64, b: V2i64): V2i64

public expect fun msaIlvodB(a: V16i8, b: V16i8): V16i8

public expect fun msaIlvodH(a: V8i16, b: V8i16): V8i16

public expect fun msaIlvodW(a: V4i32, b: V4i32): V4i32

public expect fun msaIlvodD(a: V2i64, b: V2i64): V2i64

public expect fun msaIlvrB(a: V16i8, b: V16i8): V16i8

public expect fun msaIlvrH(a: V8i16, b: V8i16): V8i16

public expect fun msaIlvrW(a: V4i32, b: V4i32): V4i32

public expect fun msaIlvrD(a: V2i64, b: V2i64): V2i64

public expect fun msaInsertB(a: V16i8, b: Int, c: Int): V16i8

public expect fun msaInsertH(a: V8i16, b: Int, c: Int): V8i16

public expect fun msaInsertW(a: V4i32, b: Int, c: Int): V4i32

public expect fun msaInsertD(a: V2i64, b: Int, c: Long): V2i64

public expect fun msaInsveB(a: V16i8, b: Int, c: V16i8): V16i8

public expect fun msaInsveH(a: V8i16, b: Int, c: V8i16): V8i16

public expect fun msaInsveW(a: V4i32, b: Int, c: V4i32): V4i32

public expect fun msaInsveD(a: V2i64, b: Int, c: V2i64): V2i64

public expect fun msaLdB(memAddr: MutPtr, b: Int): V16i8

public expect fun msaLdH(memAddr: MutPtr, b: Int): V8i16

public expect fun msaLdW(memAddr: MutPtr, b: Int): V4i32

public expect fun msaLdD(memAddr: MutPtr, b: Int): V2i64

public expect fun msaLdiB(a: Int): V16i8

public expect fun msaLdiH(a: Int): V8i16

public expect fun msaLdiW(a: Int): V4i32

public expect fun msaLdiD(a: Int): V2i64

public expect fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun msaMaxAB(a: V16i8, b: V16i8): V16i8

public expect fun msaMaxAH(a: V8i16, b: V8i16): V8i16

public expect fun msaMaxAW(a: V4i32, b: V4i32): V4i32

public expect fun msaMaxAD(a: V2i64, b: V2i64): V2i64

public expect fun msaMaxSB(a: V16i8, b: V16i8): V16i8

public expect fun msaMaxSH(a: V8i16, b: V8i16): V8i16

public expect fun msaMaxSW(a: V4i32, b: V4i32): V4i32

public expect fun msaMaxSD(a: V2i64, b: V2i64): V2i64

public expect fun msaMaxUB(a: V16u8, b: V16u8): V16u8

public expect fun msaMaxUH(a: V8u16, b: V8u16): V8u16

public expect fun msaMaxUW(a: V4u32, b: V4u32): V4u32

public expect fun msaMaxUD(a: V2u64, b: V2u64): V2u64

public expect fun msaMaxiSB(a: V16i8, b: Int): V16i8

public expect fun msaMaxiSH(a: V8i16, b: Int): V8i16

public expect fun msaMaxiSW(a: V4i32, b: Int): V4i32

public expect fun msaMaxiSD(a: V2i64, b: Int): V2i64

public expect fun msaMaxiUB(a: V16u8, b: Int): V16u8

public expect fun msaMaxiUH(a: V8u16, b: Int): V8u16

public expect fun msaMaxiUW(a: V4u32, b: Int): V4u32

public expect fun msaMaxiUD(a: V2u64, b: Int): V2u64

public expect fun msaMinAB(a: V16i8, b: V16i8): V16i8

public expect fun msaMinAH(a: V8i16, b: V8i16): V8i16

public expect fun msaMinAW(a: V4i32, b: V4i32): V4i32

public expect fun msaMinAD(a: V2i64, b: V2i64): V2i64

public expect fun msaMinSB(a: V16i8, b: V16i8): V16i8

public expect fun msaMinSH(a: V8i16, b: V8i16): V8i16

public expect fun msaMinSW(a: V4i32, b: V4i32): V4i32

public expect fun msaMinSD(a: V2i64, b: V2i64): V2i64

public expect fun msaMinUB(a: V16u8, b: V16u8): V16u8

public expect fun msaMinUH(a: V8u16, b: V8u16): V8u16

public expect fun msaMinUW(a: V4u32, b: V4u32): V4u32

public expect fun msaMinUD(a: V2u64, b: V2u64): V2u64

public expect fun msaMiniSB(a: V16i8, b: Int): V16i8

public expect fun msaMiniSH(a: V8i16, b: Int): V8i16

public expect fun msaMiniSW(a: V4i32, b: Int): V4i32

public expect fun msaMiniSD(a: V2i64, b: Int): V2i64

public expect fun msaMiniUB(a: V16u8, b: Int): V16u8

public expect fun msaMiniUH(a: V8u16, b: Int): V8u16

public expect fun msaMiniUW(a: V4u32, b: Int): V4u32

public expect fun msaMiniUD(a: V2u64, b: Int): V2u64

public expect fun msaModSB(a: V16i8, b: V16i8): V16i8

public expect fun msaModSH(a: V8i16, b: V8i16): V8i16

public expect fun msaModSW(a: V4i32, b: V4i32): V4i32

public expect fun msaModSD(a: V2i64, b: V2i64): V2i64

public expect fun msaModUB(a: V16u8, b: V16u8): V16u8

public expect fun msaModUH(a: V8u16, b: V8u16): V8u16

public expect fun msaModUW(a: V4u32, b: V4u32): V4u32

public expect fun msaModUD(a: V2u64, b: V2u64): V2u64

public expect fun msaMoveV(a: V16i8): V16i8

public expect fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun msaMulQH(a: V8i16, b: V8i16): V8i16

public expect fun msaMulQW(a: V4i32, b: V4i32): V4i32

public expect fun msaMulrQH(a: V8i16, b: V8i16): V8i16

public expect fun msaMulrQW(a: V4i32, b: V4i32): V4i32

public expect fun msaMulvB(a: V16i8, b: V16i8): V16i8

public expect fun msaMulvH(a: V8i16, b: V8i16): V8i16

public expect fun msaMulvW(a: V4i32, b: V4i32): V4i32

public expect fun msaMulvD(a: V2i64, b: V2i64): V2i64

public expect fun msaNlocB(a: V16i8): V16i8

public expect fun msaNlocH(a: V8i16): V8i16

public expect fun msaNlocW(a: V4i32): V4i32

public expect fun msaNlocD(a: V2i64): V2i64

public expect fun msaNlzcB(a: V16i8): V16i8

public expect fun msaNlzcH(a: V8i16): V8i16

public expect fun msaNlzcW(a: V4i32): V4i32

public expect fun msaNlzcD(a: V2i64): V2i64

public expect fun msaNorV(a: V16u8, b: V16u8): V16u8

public expect fun msaNoriB(a: V16u8, b: Int): V16u8

public expect fun msaOrV(a: V16u8, b: V16u8): V16u8

public expect fun msaOriB(a: V16u8, b: Int): V16u8

public expect fun msaPckevB(a: V16i8, b: V16i8): V16i8

public expect fun msaPckevH(a: V8i16, b: V8i16): V8i16

public expect fun msaPckevW(a: V4i32, b: V4i32): V4i32

public expect fun msaPckevD(a: V2i64, b: V2i64): V2i64

public expect fun msaPckodB(a: V16i8, b: V16i8): V16i8

public expect fun msaPckodH(a: V8i16, b: V8i16): V8i16

public expect fun msaPckodW(a: V4i32, b: V4i32): V4i32

public expect fun msaPckodD(a: V2i64, b: V2i64): V2i64

public expect fun msaPcntB(a: V16i8): V16i8

public expect fun msaPcntH(a: V8i16): V8i16

public expect fun msaPcntW(a: V4i32): V4i32

public expect fun msaPcntD(a: V2i64): V2i64

public expect fun msaSatSB(a: V16i8, b: Int): V16i8

public expect fun msaSatSH(a: V8i16, b: Int): V8i16

public expect fun msaSatSW(a: V4i32, b: Int): V4i32

public expect fun msaSatSD(a: V2i64, b: Int): V2i64

public expect fun msaSatUB(a: V16u8, b: Int): V16u8

public expect fun msaSatUH(a: V8u16, b: Int): V8u16

public expect fun msaSatUW(a: V4u32, b: Int): V4u32

public expect fun msaSatUD(a: V2u64, b: Int): V2u64

public expect fun msaShfB(a: V16i8, b: Int): V16i8

public expect fun msaShfH(a: V8i16, b: Int): V8i16

public expect fun msaShfW(a: V4i32, b: Int): V4i32

public expect fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8

public expect fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16

public expect fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32

public expect fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64

public expect fun msaSldiB(a: V16i8, b: V16i8, c: Int): V16i8

public expect fun msaSldiH(a: V8i16, b: V8i16, c: Int): V8i16

public expect fun msaSldiW(a: V4i32, b: V4i32, c: Int): V4i32

public expect fun msaSldiD(a: V2i64, b: V2i64, c: Int): V2i64

public expect fun msaSllB(a: V16i8, b: V16i8): V16i8

public expect fun msaSllH(a: V8i16, b: V8i16): V8i16

public expect fun msaSllW(a: V4i32, b: V4i32): V4i32

public expect fun msaSllD(a: V2i64, b: V2i64): V2i64

public expect fun msaSlliB(a: V16i8, b: Int): V16i8

public expect fun msaSlliH(a: V8i16, b: Int): V8i16

public expect fun msaSlliW(a: V4i32, b: Int): V4i32

public expect fun msaSlliD(a: V2i64, b: Int): V2i64

public expect fun msaSplatB(a: V16i8, c: Int): V16i8

public expect fun msaSplatH(a: V8i16, c: Int): V8i16

public expect fun msaSplatW(a: V4i32, w: Int): V4i32

public expect fun msaSplatD(a: V2i64, c: Int): V2i64

public expect fun msaSplatiB(a: V16i8, b: Int): V16i8

public expect fun msaSplatiH(a: V8i16, b: Int): V8i16

public expect fun msaSplatiW(a: V4i32, b: Int): V4i32

public expect fun msaSplatiD(a: V2i64, b: Int): V2i64

public expect fun msaSraB(a: V16i8, b: V16i8): V16i8

public expect fun msaSraH(a: V8i16, b: V8i16): V8i16

public expect fun msaSraW(a: V4i32, b: V4i32): V4i32

public expect fun msaSraD(a: V2i64, b: V2i64): V2i64

public expect fun msaSraiB(a: V16i8, b: Int): V16i8

public expect fun msaSraiH(a: V8i16, b: Int): V8i16

public expect fun msaSraiW(a: V4i32, b: Int): V4i32

public expect fun msaSraiD(a: V2i64, b: Int): V2i64

public expect fun msaSrarB(a: V16i8, b: V16i8): V16i8

public expect fun msaSrarH(a: V8i16, b: V8i16): V8i16

public expect fun msaSrarW(a: V4i32, b: V4i32): V4i32

public expect fun msaSrarD(a: V2i64, b: V2i64): V2i64

public expect fun msaSrariB(a: V16i8, b: Int): V16i8

public expect fun msaSrariH(a: V8i16, b: Int): V8i16

public expect fun msaSrariW(a: V4i32, b: Int): V4i32

public expect fun msaSrariD(a: V2i64, b: Int): V2i64

public expect fun msaSrlB(a: V16i8, b: V16i8): V16i8

public expect fun msaSrlH(a: V8i16, b: V8i16): V8i16

public expect fun msaSrlW(a: V4i32, b: V4i32): V4i32

public expect fun msaSrlD(a: V2i64, b: V2i64): V2i64

public expect fun msaSrliB(a: V16i8, b: Int): V16i8

public expect fun msaSrliH(a: V8i16, b: Int): V8i16

public expect fun msaSrliW(a: V4i32, b: Int): V4i32

public expect fun msaSrliD(a: V2i64, b: Int): V2i64

public expect fun msaSrlrB(a: V16i8, b: V16i8): V16i8

public expect fun msaSrlrH(a: V8i16, b: V8i16): V8i16

public expect fun msaSrlrW(a: V4i32, b: V4i32): V4i32

public expect fun msaSrlrD(a: V2i64, b: V2i64): V2i64

public expect fun msaSrlriB(a: V16i8, b: Int): V16i8

public expect fun msaSrlriH(a: V8i16, b: Int): V8i16

public expect fun msaSrlriW(a: V4i32, b: Int): V4i32

public expect fun msaSrlriD(a: V2i64, b: Int): V2i64

public expect fun msaStB(a: V16i8, memAddr: MutPtr, immS10: Int)

public expect fun msaStH(a: V8i16, memAddr: MutPtr, immS11: Int)

public expect fun msaStW(a: V4i32, memAddr: MutPtr, immS12: Int)

public expect fun msaStD(a: V2i64, memAddr: MutPtr, immS13: Int)

public expect fun msaSubsSB(a: V16i8, b: V16i8): V16i8

public expect fun msaSubsSH(a: V8i16, b: V8i16): V8i16

public expect fun msaSubsSW(a: V4i32, b: V4i32): V4i32

public expect fun msaSubsSD(a: V2i64, b: V2i64): V2i64

public expect fun msaSubsUB(a: V16u8, b: V16u8): V16u8

public expect fun msaSubsUH(a: V8u16, b: V8u16): V8u16

public expect fun msaSubsUW(a: V4u32, b: V4u32): V4u32

public expect fun msaSubsUD(a: V2u64, b: V2u64): V2u64

public expect fun msaSubsusUB(a: V16u8, b: V16i8): V16u8

public expect fun msaSubsusUH(a: V8u16, b: V8i16): V8u16

public expect fun msaSubsusUW(a: V4u32, b: V4i32): V4u32

public expect fun msaSubsusUD(a: V2u64, b: V2i64): V2u64

public expect fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8

public expect fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16

public expect fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32

public expect fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64

public expect fun msaSubvB(a: V16i8, b: V16i8): V16i8

public expect fun msaSubvH(a: V8i16, b: V8i16): V8i16

public expect fun msaSubvW(a: V4i32, b: V4i32): V4i32

public expect fun msaSubvD(a: V2i64, b: V2i64): V2i64

public expect fun msaSubviB(a: V16i8, b: Int): V16i8

public expect fun msaSubviH(a: V8i16, b: Int): V8i16

public expect fun msaSubviW(a: V4i32, b: Int): V4i32

public expect fun msaSubviD(a: V2i64, b: Int): V2i64

public expect fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun msaXorV(a: V16u8, b: V16u8): V16u8

public expect fun msaXoriB(a: V16u8, b: Int): V16u8

/**
 * Vector Add Absolute Values.
 *
 * The absolute values of the elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddAB(a: V16i8, b: V16i8): V16i8 = msaAddAB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddAH(a: V8i16, b: V8i16): V8i16 = msaAddAH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddAW(a: V4i32, b: V4i32): V4i32 = msaAddAW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Add Absolute Values
 *
 * The absolute values of the elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddAD(a: V2i64, b: V2i64): V2i64 = msaAddAD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Signed Saturated Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The saturated signed result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddsAB(a: V16i8, b: V16i8): V16i8 = msaAddsAB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (eight signed 16-bit integer numbers).
 * The saturated signed result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddsAH(a: V8i16, b: V8i16): V8i16 = msaAddsAH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (four signed 32-bit integer numbers).
 * The saturated signed result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddsAW(a: V4i32, b: V4i32): V4i32 = msaAddsAW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Saturated Add of Absolute Values
 *
 * The absolute values of the elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the absolute values of the elements in vector `b` (two signed 64-bit integer numbers).
 * The saturated signed result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddsAD(a: V2i64, b: V2i64): V2i64 = msaAddsAD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddsSB(a: V16i8, b: V16i8): V16i8 = msaAddsSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddsSH(a: V8i16, b: V8i16): V8i16 = msaAddsSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaAddsSW(a: V4i32, b: V4i32): V4i32 = msaAddsSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Saturated Add of Signed Values
 *
 * The elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaAddsSD(a: V2i64, b: V2i64): V2i64 = msaAddsSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAddsUB(a: V16u8, b: V16u8): V16u8 = msaAddsUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (eight unsigned 16-bit integer numbers)
 * are added to the elements in vector `b` (eight unsigned 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAddsUH(a: V8u16, b: V8u16): V8u16 = msaAddsUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (four unsigned 32-bit integer numbers)
 * are added to the elements in vector `b` (four unsigned 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAddsUW(a: V4u32, b: V4u32): V4u32 = msaAddsUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Saturated Add of Unsigned Values
 *
 * The elements in vector in `a` (two unsigned 64-bit integer numbers)
 * are added to the elements in vector `b` (two unsigned 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAddsUD(a: V2u64, b: V2u64): V2u64 = msaAddsUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Add
 *
 * The elements in vector in `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAddvB(a: V16i8, b: V16i8): V16i8 = msaAddvB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Add
 *
 * The elements in vector in `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAddvH(a: V8i16, b: V8i16): V8i16 = msaAddvH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Add
 *
 * The elements in vector in `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAddvW(a: V4i32, b: V4i32): V4i32 = msaAddvW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Add
 *
 * The elements in vector in `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAddvD(a: V2i64, b: V2i64): V2i64 = msaAddvD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaAddviB(a, imm5)
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
    return msaAddviH(a, imm5)
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
    return msaAddviW(a, imm5)
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
    return msaAddviD(a, imm5)
}

/**
 * Vector Logical And
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical AND operation.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAndV(a: V16u8, b: V16u8): V16u8 = msaAndV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

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
    return msaAndiB(a, imm8)
}

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The absolute value of the signed result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAsubSB(a: V16i8, b: V16i8): V16i8 = msaAsubSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (eight signed 16-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (eight signed 16-bit integer numbers).
 * The absolute value of the signed result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaAsubSH(a: V8i16, b: V8i16): V8i16 = msaAsubSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (four signed 32-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (four signed 32-bit integer numbers).
 * The absolute value of the signed result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaAsubSW(a: V4i32, b: V4i32): V4i32 = msaAsubSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Absolute Values of Signed Subtract
 *
 * The signed elements in vector `a` (two signed 64-bit integer numbers)
 * are subtracted from the signed elements in vector `b` (two signed 64-bit integer numbers).
 * The absolute value of the signed result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaAsubSD(a: V2i64, b: V2i64): V2i64 = msaAsubSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAsubUB(a: V16u8, b: V16u8): V16u8 = msaAsubUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAsubUH(a: V8u16, b: V8u16): V8u16 = msaAsubUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (four unsigned 32-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAsubUW(a: V4u32, b: V4u32): V4u32 = msaAsubUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Absolute Values of Unsigned Subtract
 *
 * The unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `b` (two unsigned 64-bit integer numbers).
 * The absolute value of the unsigned result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAsubUD(a: V2u64, b: V2u64): V2u64 = msaAsubUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaAveSB(a: V16i8, b: V16i8): V16i8 = msaAveSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are added to the elements in vector `b` (eight signed 16-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaAveSH(a: V8i16, b: V8i16): V8i16 = msaAveSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are added to the elements in vector `b` (four signed 32-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaAveSW(a: V4i32, b: V4i32): V4i32 = msaAveSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Average
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are added to the elements in vector `b` (two signed 64-bit integer numbers).
 * The addition is done signed with full precision, i.e. the result has one extra bit.
 * Signed division by 2 (or arithmetic shift right by one bit) is performed before
 * writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaAveSD(a: V2i64, b: V2i64): V2i64 = msaAveSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaAveUB(a: V16u8, b: V16u8): V16u8 = msaAveUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are added to the elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaAveUH(a: V8u16, b: V8u16): V8u16 = msaAveUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (four unsigned 32-bit integer numbers)
 * are added to the elements in vector `b` (four unsigned 32-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaAveUW(a: V4u32, b: V4u32): V4u32 = msaAveUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Average
 *
 * The elements in vector `a` (two unsigned 64-bit integer numbers)
 * are added to the elements in vector `b` (two unsigned 64-bit integer numbers).
 * The addition is done unsigned with full precision, i.e. the result has one extra bit.
 * Unsigned division by 2 (or logical shift right by one bit) is performed before
 * writing the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaAveUD(a: V2u64, b: V2u64): V2u64 = msaAveUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
public fun msaAverSB(a: V16i8, b: V16i8): V16i8 = msaAverSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

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
public fun msaAverSH(a: V8i16, b: V8i16): V8i16 = msaAverSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

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
public fun msaAverSW(a: V4i32, b: V4i32): V4i32 = msaAverSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

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
public fun msaAverSD(a: V2i64, b: V2i64): V2i64 = msaAverSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
public fun msaAverUB(a: V16u8, b: V16u8): V16u8 = msaAverUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

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
public fun msaAverUH(a: V8u16, b: V8u16): V8u16 = msaAverUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

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
public fun msaAverUW(a: V4u32, b: V4u32): V4u32 = msaAverUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

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
public fun msaAverUD(a: V2u64, b: V2u64): V2u64 = msaAverUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBclrB(a: V16u8, b: V16u8): V16u8 = msaBclrB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBclrH(a: V8u16, b: V8u16): V8u16 = msaBclrH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBclrW(a: V4u32, b: V4u32): V4u32 = msaBclrW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Bit Clear
 *
 * Clear (set to 0) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBclrD(a: V2u64, b: V2u64): V2u64 = msaBclrD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
    return msaBclriB(a, imm3)
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
    return msaBclriH(a, imm4)
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
    return msaBclriW(a, imm5)
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
    return msaBclriD(a, imm6)
}

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 = msaBinslB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 = msaBinslH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()), c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 = msaBinslW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()), c)

/**
 * Vector Bit Insert Left
 *
 * Copy most significant (left) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (right) bits.
 * The number of bits to copy is given by the elements in vector `c` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 = msaBinslD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()), c)

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
    return msaBinsliB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), imm3)
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
    return msaBinsliH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()), imm4)
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
    return msaBinsliW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()), imm5)
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
    return msaBinsliD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()), imm6)
}

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (sixteen unsigned 8-bit integer numbers)
 * to elements in vector `a` (sixteen unsigned 8-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 = msaBinsrB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (eight unsigned 16-bit integer numbers)
 * to elements in vector `a` (eight unsigned 16-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 = msaBinsrH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()), c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (four unsigned 32-bit integer numbers)
 * to elements in vector `a` (four unsigned 32-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 = msaBinsrW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()), c)

/**
 * Vector Bit Insert Right
 *
 * Copy most significant (right) bits in each element of vector `b` (two unsigned 64-bit integer numbers)
 * to elements in vector `a` (two unsigned 64-bit integer numbers) while preserving the least significant (left) bits.
 * The number of bits to copy is given by the elements in vector `c` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits plus 1.
 */
public fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 = msaBinsrD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()), c)

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
    return msaBinsriB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), imm3)
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
    return msaBinsriH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()), imm4)
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
    return msaBinsriW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()), imm5)
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
    return msaBinsriD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()), imm6)
}

/**
 * Vector Bit Move If Not Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from target vector `c`
 * (sixteen unsigned 8-bit integer numbers) are 1 and leaves unchanged all destination bits
 * for which the corresponding target bits are 0.
 */
public fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 = msaBmnzV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), c)

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
    return msaBmnziB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), imm8)
}

/**
 * Vector Bit Move If Zero
 *
 * Copy to destination vector `a` (sixteen unsigned 8-bit integer numbers) all bits from source vector
 * `b` (sixteen unsigned 8-bit integer numbers) for which the corresponding bits from target vector `c`
 * (sixteen unsigned 8-bit integer numbers) are 0 and leaves unchanged all destination bits
 * for which the corresponding target bits are 1.
 */
public fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 = msaBmzV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), c)

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
    return msaBmziB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), imm8)
}

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBnegB(a: V16u8, b: V16u8): V16u8 = msaBnegB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in vector `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBnegH(a: V8u16, b: V8u16): V8u16 = msaBnegH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in vector `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBnegW(a: V4u32, b: V4u32): V4u32 = msaBnegW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Bit Negate
 *
 * Negate (complement) one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in vector `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBnegD(a: V2u64, b: V2u64): V2u64 = msaBnegD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
    return msaBnegiB(a, imm3)
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
    return msaBnegiH(a, imm4)
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
    return msaBnegiW(a, imm5)
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
    return msaBnegiD(a, imm6)
}

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (sixteen unsigned 8-bit integer numbers) are not zero.
 */
public fun msaBnzB(a: V16u8): Int = msaBnzB(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (eight unsigned 16-bit integer numbers) are not zero.
 */
public fun msaBnzH(a: V8u16): Int = msaBnzH(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (four unsigned 32-bit integer numbers) are not zero.
 */
public fun msaBnzW(a: V4u32): Int = msaBnzW(a)

/**
 * Immediate Branch If All Elements Are Not Zero
 *
 * PC-relative branch if all elements in `a` (two unsigned 64-bit integer numbers) are not zero.
 */
public fun msaBnzD(a: V2u64): Int = msaBnzD(a)

/**
 * Immediate Branch If Not Zero (At Least One Element of Any Format Is Not Zero)
 *
 * PC-relative branch if at least one bit in `a` (four unsigned 32-bit integer numbers) are not zero.
 * i.e at least one element is not zero regardless of the data format.
 */
public fun msaBnzV(a: V16u8): Int = msaBnzV(a)

/**
 * Vector Bit Select
 *
 * Selectively copy bits from the source vectors `b` (eight unsigned 16-bit integer numbers)
 * and `c` (eight unsigned 16-bit integer numbers)
 * into destination vector `a` (eight unsigned 16-bit integer numbers) based on the corresponding bit in `a`:
 * if 0 copies the bit from `b`, if 1 copies the bit from `c`.
 */
public fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 = msaBselV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), c)

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
    return msaBseliB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()), imm8)
}

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (sixteen unsigned 8-bit integer numbers).
 * The bit position is given by the elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaBsetB(a: V16u8, b: V16u8): V16u8 = msaBsetB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (eight unsigned 16-bit integer numbers).
 * The bit position is given by the elements in vector `b` (eight unsigned 16-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaBsetH(a: V8u16, b: V8u16): V8u16 = msaBsetH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (four unsigned 32-bit integer numbers).
 * The bit position is given by the elements in vector `b` (four unsigned 32-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaBsetW(a: V4u32, b: V4u32): V4u32 = msaBsetW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Bit Set
 *
 * Set to 1 one bit in each element of vector `a` (two unsigned 64-bit integer numbers).
 * The bit position is given by the elements in vector `b` (two unsigned 64-bit integer numbers)
 * modulo the size of the element in bits.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaBsetD(a: V2u64, b: V2u64): V2u64 = msaBsetD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
    return msaBsetiB(a, imm3)
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
    return msaBsetiH(a, imm4)
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
    return msaBsetiW(a, imm5)
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
    return msaBsetiD(a, imm6)
}

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (sixteen unsigned 8-bit integer numbers) is zero.
 */
public fun msaBzB(a: V16u8): Int = msaBzB(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (eight unsigned 16-bit integer numbers) is zero.
 */
public fun msaBzH(a: V8u16): Int = msaBzH(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (four unsigned 32-bit integer numbers) is zero.
 */
public fun msaBzW(a: V4u32): Int = msaBzW(a)

/**
 * Immediate Branch If At Least One Element Is Zero
 *
 * PC-relative branch if at least one element in `a` (two unsigned 64-bit integer numbers) is zero.
 */
public fun msaBzD(a: V2u64): Int = msaBzD(a)

/**
 * Immediate Branch If Zero (All Elements of Any Format Are Zero)
 *
 * PC-relative branch if all elements in `a` (sixteen unsigned 8-bit integer numbers) bits are zero,
 * i.e. all elements are zero regardless of the data format.
 */
public fun msaBzV(a: V16u8): Int = msaBzV(a)

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) and `b` (sixteen signed 8-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqB(a: V16i8, b: V16i8): V16i8 = msaCeqB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) and `b` (eight signed 16-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqH(a: V8i16, b: V8i16): V8i16 = msaCeqH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) and `b` (four signed 32-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqW(a: V4i32, b: V4i32): V4i32 = msaCeqW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) and `b` (two signed 64-bit integer numbers)
 * elements are equal, otherwise set all bits to 0.
 */
public fun msaCeqD(a: V2i64, b: V2i64): V2i64 = msaCeqD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaCeqiB(a, immS5)
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
    return msaCeqiH(a, immS5)
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
    return msaCeqiW(a, immS5)
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
    return msaCeqiD(a, immS5)
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
    return msaCfcmsa(imm5)
}

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * are signed less than or equal to `b` (sixteen signed 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSB(a: V16i8, b: V16i8): V16i8 = msaCleSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * are signed less than or equal to `b` (eight signed 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSH(a: V8i16, b: V8i16): V8i16 = msaCleSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * are signed less than or equal to `b` (four signed 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSW(a: V4i32, b: V4i32): V4i32 = msaCleSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Compare Signed Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * are signed less than or equal to `b` (two signed 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleSD(a: V2i64, b: V2i64): V2i64 = msaCleSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * are unsigned less than or equal to `b` (sixteen unsigned 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUB(a: V16u8, b: V16u8): V16i8 = msaCleUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * are unsigned less than or equal to `b` (eight unsigned 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUH(a: V8u16, b: V8u16): V8i16 = msaCleUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * are unsigned less than or equal to `b` (four unsigned 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUW(a: V4u32, b: V4u32): V4i32 = msaCleUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Compare Unsigned Less Than or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * are unsigned less than or equal to `b` (two unsigned 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCleUD(a: V2u64, b: V2u64): V2i64 = msaCleUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2i64.typeLayout()))

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
    return msaCleiSB(a, immS5)
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
    return msaCleiSH(a, immS5)
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
    return msaCleiSW(a, immS5)
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
    return msaCleiSD(a, immS5)
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
    return msaCleiUB(a, imm5)
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
    return msaCleiUH(a, imm5)
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
    return msaCleiUW(a, imm5)
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
    return msaCleiUD(a, imm5)
}

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen signed 8-bit integer numbers) element
 * are signed less than `b` (sixteen signed 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSB(a: V16i8, b: V16i8): V16i8 = msaCltSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight signed 16-bit integer numbers) element
 * are signed less than `b` (eight signed 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSH(a: V8i16, b: V8i16): V8i16 = msaCltSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four signed 32-bit integer numbers) element
 * are signed less than `b` (four signed 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSW(a: V4i32, b: V4i32): V4i32 = msaCltSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Compare Signed Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two signed 64-bit integer numbers) element
 * are signed less than `b` (two signed 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltSD(a: V2i64, b: V2i64): V2i64 = msaCltSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (sixteen signed 8-bit integer numbers) elements
 * if the corresponding `a` (sixteen unsigned 8-bit integer numbers) element
 * are unsigned less than `b` (sixteen unsigned 8-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUB(a: V16u8, b: V16u8): V16i8 = msaCltUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (eight signed 16-bit integer numbers) elements
 * if the corresponding `a` (eight unsigned 16-bit integer numbers) element
 * are unsigned less than `b` (eight unsigned 16-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUH(a: V8u16, b: V8u16): V8i16 = msaCltUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four unsigned 32-bit integer numbers) element
 * are unsigned less than `b` (four unsigned 32-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUW(a: V4u32, b: V4u32): V4i32 = msaCltUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Compare Unsigned Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two unsigned 64-bit integer numbers) element
 * are unsigned less than `b` (two unsigned 64-bit integer numbers) element.
 * Otherwise set all bits to 0.
 */
public fun msaCltUD(a: V2u64, b: V2u64): V2i64 = msaCltUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2i64.typeLayout()))

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
    return msaCltiSB(a, immS5)
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
    return msaCltiSH(a, immS5)
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
    return msaCltiSW(a, immS5)
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
    return msaCltiSD(a, immS5)
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
    return msaCltiUB(a, imm5)
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
    return msaCltiUH(a, imm5)
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
    return msaCltiUW(a, imm5)
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
    return msaCltiUD(a, imm5)
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
    return msaCopySB(a, imm4)
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
    return msaCopySH(a, imm3)
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
    return msaCopySW(a, imm2)
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
    return msaCopySD(a, imm1)
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
    return msaCopyUB(a, imm4)
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
    return msaCopyUH(a, imm3)
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
    return msaCopyUW(a, imm2)
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
    return msaCopyUD(a, imm1)
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
    msaCtcmsa(imm5, a)
}

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are divided by signed integer elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaDivSB(a: V16i8, b: V16i8): V16i8 = msaDivSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are divided by signed integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaDivSH(a: V8i16, b: V8i16): V8i16 = msaDivSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are divided by signed integer elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaDivSW(a: V4i32, b: V4i32): V4i32 = msaDivSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Divide
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are divided by signed integer elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaDivSD(a: V2i64, b: V2i64): V2i64 = msaDivSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The result is written to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaDivUB(a: V16u8, b: V16u8): V16u8 = msaDivUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaDivUH(a: V8u16, b: V8u16): V8u16 = msaDivUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers).
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaDivUW(a: V4u32, b: V4u32): V4u32 = msaDivUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Divide
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (two unsigned 64-bit integer numbers).
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaDivUD(a: V2u64, b: V2u64): V2u64 = msaDivUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (eight signed 16-bit integer numbers).
 */
public fun msaDotpSH(a: V16i8, b: V16i8): V8i16 = msaDotpSH(a, transmuteWithLayouts(b, V16i8.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (four signed 32-bit integer numbers).
 */
public fun msaDotpSW(a: V8i16, b: V8i16): V4i32 = msaDotpSW(a, transmuteWithLayouts(b, V8i16.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Dot Product
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (two signed 64-bit integer numbers).
 */
public fun msaDotpSD(a: V4i32, b: V4i32): V2i64 = msaDotpSD(a, transmuteWithLayouts(b, V4i32.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (eight unsigned 16-bit integer numbers).
 */
public fun msaDotpUH(a: V16u8, b: V16u8): V8u16 = msaDotpUH(a, transmuteWithLayouts(b, V16u8.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (four unsigned 32-bit integer numbers).
 */
public fun msaDotpUW(a: V8u16, b: V8u16): V4u32 = msaDotpUW(a, transmuteWithLayouts(b, V8u16.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Dot Product
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * producing a result the size of the input operands. The multiplication results of
 * adjacent odd/even elements are added and stored to the destination
 * vector (two unsigned 64-bit integer numbers).
 */
public fun msaDotpUD(a: V4u32, b: V4u32): V2u64 = msaDotpUD(a, transmuteWithLayouts(b, V4u32.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (eight signed 16-bit integer numbers).
 */
public fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 = msaDpaddSH(a, transmuteWithLayouts(b, V16i8.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (four signed 32-bit integer numbers).
 */
public fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 = msaDpaddSW(a, transmuteWithLayouts(b, V8i16.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (two signed 64-bit integer numbers).
 */
public fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 = msaDpaddSD(a, transmuteWithLayouts(b, V4i32.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (eight unsigned 16-bit integer numbers).
 */
public fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 = msaDpaddUH(a, transmuteWithLayouts(b, V16u8.typeLayout(), V8u16.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (four unsigned 32-bit integer numbers).
 */
public fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 = msaDpaddUW(a, transmuteWithLayouts(b, V8u16.typeLayout(), V4u32.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are added to the vector `a` (two unsigned 64-bit integer numbers).
 */
public fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 = msaDpaddUD(a, transmuteWithLayouts(b, V4u32.typeLayout(), V2u64.typeLayout()), c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (eight signed 16-bit integer numbers).
 */
public fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 = msaDpsubSH(a, transmuteWithLayouts(b, V16i8.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (four signed 32-bit integer numbers).
 */
public fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 = msaDpsubSW(a, transmuteWithLayouts(b, V8i16.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Signed Dot Product and Add
 *
 * The signed integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by signed integer elements in vector `c` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (two signed 64-bit integer numbers).
 */
public fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 = msaDpsubSD(a, transmuteWithLayouts(b, V4i32.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (eight signed 16-bit integer numbers).
 */
public fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 = msaDpsubUH(a, transmuteWithLayouts(b, V16u8.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (four signed 32-bit integer numbers).
 */
public fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 = msaDpsubUW(a, transmuteWithLayouts(b, V8u16.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Unsigned Dot Product and Add
 *
 * The unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers)
 * are multiplied by unsigned integer elements in vector `c` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands. The multiplication results
 * of adjacent odd/even elements are subtracted from the integer elements in vector `a`
 * (two signed 64-bit integer numbers).
 */
public fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 = msaDpsubUD(a, transmuteWithLayouts(b, V4u32.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Floating-Point Addition
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are added to the floating-point elements in `bc` (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFaddW(a: V4f32, b: V4f32): V4f32 = msaFaddW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Addition
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are added to the floating-point elements in `bc` (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFaddD(a: V2f64, b: V2f64): V2f64 = msaFaddD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Always False
 *
 * Set all bits to 0 in vector (four signed 32-bit integer numbers).
 * Signaling NaN elements in `a` (four 32-bit floating point numbers)
 * or `b` (four 32-bit floating point numbers) signal Invalid Operation exception.
 */
public fun msaFcafW(a: V4f32, b: V4f32): V4i32 = msaFcafW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Always False
 *
 * Set all bits to 0 in vector (two signed 64-bit integer numbers).
 * Signaling NaN elements in `a` (two 64-bit floating point numbers)
 * or `b` (two 64-bit floating point numbers) signal Invalid Operation exception.
 */
public fun msaFcafD(a: V2f64, b: V2f64): V2i64 = msaFcafD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding in `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are ordered and equal,
 * otherwise set all bits to 0.
 */
public fun msaFceqW(a: V4f32, b: V4f32): V4i32 = msaFceqW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding in `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are ordered and equal,
 * otherwise set all bits to 0.
 */
public fun msaFceqD(a: V2f64, b: V2f64): V2i64 = msaFceqD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

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
public fun msaFclassW(a: V4f32): V4i32 = msaFclassW(a)

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
public fun msaFclassD(a: V2f64): V2i64 = msaFclassD(a)

/**
 * Vector Floating-Point Quiet Compare Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) elements are ordered
 * and either less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcleW(a: V4f32, b: V4f32): V4i32 = msaFcleW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) elements are ordered
 * and either less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcleD(a: V2f64, b: V2f64): V2i64 = msaFcleD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) elements are ordered
 * and less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcltW(a: V4f32, b: V4f32): V4i32 = msaFcltW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) elements are ordered
 * and less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcltD(a: V2f64, b: V2f64): V2i64 = msaFcltD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered and not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcneW(a: V4f32, b: V4f32): V4i32 = msaFcneW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered and not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcneD(a: V2f64, b: V2f64): V2i64 = msaFcneD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered, i.e. both elements are not NaN values,
 * otherwise set all bits to 0.
 */
public fun msaFcorW(a: V4f32, b: V4f32): V4i32 = msaFcorW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered, i.e. both elements are not NaN values,
 * otherwise set all bits to 0.
 */
public fun msaFcorD(a: V2f64, b: V2f64): V2i64 = msaFcorD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFcueqW(a: V4f32, b: V4f32): V4i32 = msaFcueqW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFcueqD(a: V2f64, b: V2f64): V2i64 = msaFcueqD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding elements in `a` (four 32-bit floating point numbers)
 * are unordered or less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFculeW(a: V4f32, b: V4f32): V4i32 = msaFculeW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding elements in `a` (two 64-bit floating point numbers)
 * are unordered or less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFculeD(a: V2f64, b: V2f64): V2i64 = msaFculeD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding elements in `a` (four 32-bit floating point numbers)
 * are unordered or less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcultW(a: V4f32, b: V4f32): V4i32 = msaFcultW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding elements in `a` (two 64-bit floating point numbers)
 * are unordered or less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFcultD(a: V2f64, b: V2f64): V2i64 = msaFcultD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFcunW(a: V4f32, b: V4f32): V4i32 = msaFcunW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFcunD(a: V2f64, b: V2f64): V2i64 = msaFcunD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers)
 * elements if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcuneW(a: V4f32, b: V4f32): V4i32 = msaFcuneW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Quiet Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers)
 * elements if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFcuneD(a: V2f64, b: V2f64): V2i64 = msaFcuneD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Division
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are divided by the floating-point elements in vector `b` (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFdivW(a: V4f32, b: V4f32): V4f32 = msaFdivW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Division
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are divided by the floating-point elements in vector `b` (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFdivD(a: V2f64, b: V2f64): V2f64 = msaFdivD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

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
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexdo_h(a: v4f32, b: v4f32) -> f16x8 {
// msaFexdoH(a, mem.transmute(b))
// }

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers) are down-converted
 * to a smaller interchange format, i.e. from 64-bit to 32-bit, or from 32-bit to 16-bit.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFexdoW(a: V2f64, b: V2f64): V4f32 = msaFexdoW(a, transmuteWithLayouts(b, V2f64.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are scaled, i.e. multiplied, by 2 to the power of integer elements in vector `b`
 * (four signed 32-bit integer numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFexp2W(a: V4f32, b: V4i32): V4f32 = msaFexp2W(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Down-Convert Interchange Format
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are scaled, i.e. multiplied, by 2 to the power of integer elements in vector `b`
 * (two signed 64-bit integer numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexp2D(a: V2f64, b: V2i64): V2f64 = msaFexp2D(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2f64.typeLayout()))

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
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexupl_w(a: f16x8) -> v4f32 {
// msaFexuplW(a)
// }

/**
 * Vector Floating-Point Up-Convert Interchange Format Left
 *
 * The left half floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are up-converted to a larger interchange format,
 * i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexuplD(a: V4f32): V2f64 = msaFexuplD(a)

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
// @unstable(feature = "stdarchMips", issue = "111198") pub unchecked fn __msa_fexupr_w(a: f16x8) -> v4f32 {
// msaFexuprW(a)
// }

/**
 * Vector Floating-Point Up-Convert Interchange Format Left
 *
 * The right half floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are up-converted to a larger interchange format,
 * i.e. from 16-bit to 32-bit, or from 32-bit to 64-bit.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFexuprD(a: V4f32): V2f64 = msaFexuprD(a)

/**
 * Vector Floating-Point Round and Convert from Signed Integer
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfintSW(a: V4i32): V4f32 = msaFfintSW(a)

/**
 * Vector Floating-Point Round and Convert from Signed Integer
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfintSD(a: V2i64): V2f64 = msaFfintSD(a)

/**
 * Vector Floating-Point Round and Convert from Unsigned Integer
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfintUW(a: V4u32): V4f32 = msaFfintUW(a)

/**
 * Vector Floating-Point Round and Convert from Unsigned Integer
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are converted to floating-point values.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfintUD(a: V2u64): V2f64 = msaFfintUD(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The left half fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfqlW(a: V8i16): V4f32 = msaFfqlW(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The left half fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfqlD(a: V4i32): V2f64 = msaFfqlD(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The right half fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFfqrW(a: V8i16): V4f32 = msaFfqrW(a)

/**
 * Vector Floating-Point Convert from Fixed-Point Left
 *
 * The right half fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * are up-converted to floating-point data format.
 * i.e. from 16-bit Q15 to 32-bit floating-point, or from 32-bit Q31 to 64-bit floating-point.
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFfqrD(a: V4i32): V2f64 = msaFfqrD(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (sixteen signed 8-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillB(a: Int): V16i8 = msaFillB(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (eight signed 16-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillH(a: Int): V8i16 = msaFillH(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (four signed 32-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillW(a: Int): V4i32 = msaFillW(a)

/**
 * Vector Fill from GPR
 *
 * Replicate GPR rs value to all elements in vector (two signed 64-bit integer numbers).
 * If the source GPR is wider than the destination data format, the destination's elements
 * will be set to the least significant bits of the GPR.
 */
public fun msaFillD(a: Long): V2i64 = msaFillD(a)

/**
 * Vector Floating-Point Base 2 Logarithm
 *
 * The signed integral base 2 exponents of floating-point elements in vector `a`
 * (four 32-bit floating point numbers) are written as floating-point values to vector elements
 * (four 32-bit floating point numbers).
 */
public fun msaFlog2W(a: V4f32): V4f32 = msaFlog2W(a)

/**
 * Vector Floating-Point Base 2 Logarithm
 *
 * The signed integral base 2 exponents of floating-point elements in vector `a`
 * (two 64-bit floating point numbers) are written as floating-point values to vector elements
 * (two 64-bit floating point numbers).
 */
public fun msaFlog2D(a: V2f64): V2f64 = msaFlog2D(a)

/**
 * Vector Floating-Point Multiply-Add
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (four 32-bit floating point numbers)
 * are added to the floating-point elements in vector `a` (four 32-bit floating point numbers).
 */
public fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 = msaFmaddW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()), c)

/**
 * Vector Floating-Point Multiply-Add
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (two 64-bit floating point numbers)
 * are added to the floating-point elements in vector `a` (two 64-bit floating point numbers).
 */
public fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 = msaFmaddD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()), c)

/**
 * Vector Floating-Point Maximum
 *
 * The largest values between corresponding floating-point elements in vector `a`
 * (four 32-bit floating point numbers) and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFmaxW(a: V4f32, b: V4f32): V4f32 = msaFmaxW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Maximum
 *
 * The largest values between corresponding floating-point elements in vector `a`
 * (two 64-bit floating point numbers) and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFmaxD(a: V2f64, b: V2f64): V2f64 = msaFmaxD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (four 32-bit floating point numbers)
 * and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFmaxAW(a: V4f32, b: V4f32): V4f32 = msaFmaxAW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFmaxAD(a: V2f64, b: V2f64): V2f64 = msaFmaxAD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Minimum
 *
 * The smallest values between corresponding floating-point elements in vector `a`
 * (four 32-bit floating point numbers) and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFminW(a: V4f32, b: V4f32): V4f32 = msaFminW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Minimum
 *
 * The smallest values between corresponding floating-point elements in vector `a`
 * (two 64-bit floating point numbers) and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFminD(a: V2f64, b: V2f64): V2f64 = msaFminD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Minimum Based on Absolute Values
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (four 32-bit floating point numbers)
 * and vector `b` (four 32-bit floating point numbers)
 * are written to vector (four 32-bit floating point numbers).
 */
public fun msaFminAW(a: V4f32, b: V4f32): V4f32 = msaFminAW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Minimum Based on Absolute Values
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * floating-point elements in vector `a` (two 64-bit floating point numbers)
 * and vector `b` (two 64-bit floating point numbers)
 * are written to vector (two 64-bit floating point numbers).
 */
public fun msaFminAD(a: V2f64, b: V2f64): V2f64 = msaFminAD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Multiply-Sub
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (four 32-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a` (four 32-bit floating point numbers).
 */
public fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 = msaFmsubW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()), c)

/**
 * Vector Floating-Point Multiply-Sub
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * multiplied by floating-point elements in vector `c` (two 64-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a` (two 64-bit floating point numbers).
 */
public fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 = msaFmsubD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()), c)

/**
 * Vector Floating-Point Multiplication
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers) are
 * multiplied by floating-point elements in vector `b` (four 32-bit floating point numbers).
 */
public fun msaFmulW(a: V4f32, b: V4f32): V4f32 = msaFmulW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Multiplication
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers) are
 * multiplied by floating-point elements in vector `b` (two 64-bit floating point numbers).
 */
public fun msaFmulD(a: V2f64, b: V2f64): V2f64 = msaFmulD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Round to Integer
 *
 * The floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are rounded to an integral valued floating-point number in the same format based
 * on the rounding mode bits RM in MSA Control and Status Register MSACSR.
 */
public fun msaFrintW(a: V4f32): V4f32 = msaFrintW(a)

/**
 * Vector Floating-Point Round to Integer
 *
 * The floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are rounded to an integral valued floating-point number in the same format based
 * on the rounding mode bits RM in MSA Control and Status Register MSACSR.
 */
public fun msaFrintD(a: V2f64): V2f64 = msaFrintD(a)

/**
 * Vector Approximate Floating-Point Reciprocal
 *
 * The reciprocals of floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are calculated and the result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFrcpW(a: V4f32): V4f32 = msaFrcpW(a)

/**
 * Vector Approximate Floating-Point Reciprocal
 *
 * The reciprocals of floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are calculated and the result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFrcpD(a: V2f64): V2f64 = msaFrcpD(a)

/**
 * Vector Approximate Floating-Point Reciprocal of Square Root
 *
 * The reciprocals of the square roots of floating-point elements in vector `a` (four 32-bit floating point numbers)
 * are calculated and the result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFrsqrtW(a: V4f32): V4f32 = msaFrsqrtW(a)

/**
 * Vector Approximate Floating-Point Reciprocal of Square Root
 *
 * The reciprocals of the square roots of floating-point elements in vector `a` (two 64-bit floating point numbers)
 * are calculated and the result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFrsqrtD(a: V2f64): V2f64 = msaFrsqrtD(a)

/**
 * Vector Floating-Point Signaling Compare Always False
 *
 * Set all bits to 0 in vector (four signed 32-bit integer numbers) elements.
 * Signaling and quiet NaN elements in vector `a` (four 32-bit floating point numbers)
 * or `b` (four 32-bit floating point numbers) signal Invalid Operation exception.
 * In case of a floating-point exception, the default result has all bits set to 0.
 */
public fun msaFsafW(a: V4f32, b: V4f32): V4i32 = msaFsafW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Always False
 *
 * Set all bits to 0 in vector (two signed 64-bit integer numbers) elements.
 * Signaling and quiet NaN elements in vector `a` (two 64-bit floating point numbers)
 * or `b` (two 64-bit floating point numbers) signal Invalid Operation exception.
 * In case of a floating-point exception, the default result has all bits set to 0.
 */
public fun msaFsafD(a: V2f64, b: V2f64): V2i64 = msaFsafD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) elements are equal, otherwise set all bits to 0.
 */
public fun msaFseqW(a: V4f32, b: V4f32): V4i32 = msaFseqW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) elements are equal, otherwise set all bits to 0.
 */
public fun msaFseqD(a: V2f64, b: V2f64): V2i64 = msaFseqD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are less than or equal to `b` (four 32-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsleW(a: V4f32, b: V4f32): V4i32 = msaFsleW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are less than or equal to `b` (two 64-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsleD(a: V2f64, b: V2f64): V2i64 = msaFsleD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are less than `b` (four 32-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsltW(a: V4f32, b: V4f32): V4i32 = msaFsltW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are less than `b` (two 64-bit floating point numbers) elements, otherwise set all bits to 0.
 */
public fun msaFsltD(a: V2f64, b: V2f64): V2i64 = msaFsltD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are not equal, otherwise set all bits to 0.
 */
public fun msaFsneW(a: V4f32, b: V4f32): V4i32 = msaFsneW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are not equal, otherwise set all bits to 0.
 */
public fun msaFsneD(a: V2f64, b: V2f64): V2i64 = msaFsneD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are ordered,
 * i.e. both elements are not NaN values, otherwise set all bits to 0.
 */
public fun msaFsorW(a: V4f32, b: V4f32): V4i32 = msaFsorW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are ordered,
 * i.e. both elements are not NaN values, otherwise set all bits to 0.
 */
public fun msaFsorD(a: V2f64, b: V2f64): V2i64 = msaFsorD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Square Root
 *
 * The square roots of floating-point elements in vector `a`
 * (four 32-bit floating point numbers) are written to vector
 * (four 32-bit floating point numbers) elements are ordered,.
 */
public fun msaFsqrtW(a: V4f32): V4f32 = msaFsqrtW(a)

/**
 * Vector Floating-Point Square Root
 *
 * The square roots of floating-point elements in vector `a`
 * (two 64-bit floating point numbers) are written to vector
 * (two 64-bit floating point numbers) elements are ordered,.
 */
public fun msaFsqrtD(a: V2f64): V2f64 = msaFsqrtD(a)

/**
 * Vector Floating-Point Subtraction
 *
 * The floating-point elements in vector `b` (four 32-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a`
 * (four 32-bit floating point numbers).
 * The result is written to vector (four 32-bit floating point numbers).
 */
public fun msaFsubW(a: V4f32, b: V4f32): V4f32 = msaFsubW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4f32.typeLayout()))

/**
 * Vector Floating-Point Subtraction
 *
 * The floating-point elements in vector `b` (two 64-bit floating point numbers)
 * are subtracted from the floating-point elements in vector `a`
 * (two 64-bit floating point numbers).
 * The result is written to vector (two 64-bit floating point numbers).
 */
public fun msaFsubD(a: V2f64, b: V2f64): V2f64 = msaFsubD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2f64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFsueqW(a: V4f32, b: V4f32): V4i32 = msaFsueqW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Ordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or equal,
 * otherwise set all bits to 0.
 */
public fun msaFsueqD(a: V2f64, b: V2f64): V2i64 = msaFsueqD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements are
 * unordered or less than or equal to `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsuleW(a: V4f32, b: V4f32): V4i32 = msaFsuleW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Less or Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements are
 * unordered or less than or equal to `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsuleD(a: V2f64, b: V2f64): V2i64 = msaFsuleD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) elements
 * are unordered or less than `b` (four 32-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsultW(a: V4f32, b: V4f32): V4i32 = msaFsultW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Less Than
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) elements
 * are unordered or less than `b` (two 64-bit floating point numbers) elements,
 * otherwise set all bits to 0.
 */
public fun msaFsultD(a: V2f64, b: V2f64): V2i64 = msaFsultD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFsunW(a: V4f32, b: V4f32): V4i32 = msaFsunW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered,
 * i.e. at least one element is a NaN value, otherwise set all bits to 0.
 */
public fun msaFsunD(a: V2f64, b: V2f64): V2i64 = msaFsunD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (four signed 32-bit integer numbers) elements
 * if the corresponding `a` (four 32-bit floating point numbers) and
 * `b` (four 32-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFsuneW(a: V4f32, b: V4f32): V4i32 = msaFsuneW(a, transmuteWithLayouts(b, V4f32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Signaling Compare Unordered or Not Equal
 *
 * Set all bits to 1 in vector (two signed 64-bit integer numbers) elements
 * if the corresponding `a` (two 64-bit floating point numbers) and
 * `b` (two 64-bit floating point numbers) elements are unordered or not equal,
 * otherwise set all bits to 0.
 */
public fun msaFsuneD(a: V2f64, b: V2f64): V2i64 = msaFsuneD(a, transmuteWithLayouts(b, V2f64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Floating-Point Convert to Signed Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtintSW(a: V4f32): V4i32 = msaFtintSW(a)

/**
 * Vector Floating-Point Convert to Signed Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaFtintSD(a: V2f64): V2i64 = msaFtintSD(a)

/**
 * Vector Floating-Point Convert to Unsigned Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaFtintUW(a: V4f32): V4u32 = msaFtintUW(a)

/**
 * Vector Floating-Point Convert to Unsigned Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are rounded and converted to signed integer values based on the
 * rounding mode bits RM in MSA Control and Status Register MSACSR.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaFtintUD(a: V2f64): V2u64 = msaFtintUD(a)

/**
 * Vector Floating-Point Convert to Fixed-Point
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * and `b` (four 32-bit floating point numbers) are down-converted to a fixed-point
 * representation, i.e. from 64-bit floating-point to 32-bit Q31 fixed-point
 * representation, or from 32-bit floating-point to 16-bit Q15 fixed-point representation.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaFtqH(a: V4f32, b: V4f32): V8i16 = msaFtqH(a, transmuteWithLayouts(b, V4f32.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Floating-Point Convert to Fixed-Point
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * and `b` (two 64-bit floating point numbers) are down-converted to a fixed-point
 * representation, i.e. from 64-bit floating-point to 32-bit Q31 fixed-point
 * representation, or from 32-bit floating-point to 16-bit Q15 fixed-point representation.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtqW(a: V2f64, b: V2f64): V4i32 = msaFtqW(a, transmuteWithLayouts(b, V2f64.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Floating-Point Truncate and Convert to Signed Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to signed integer values.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaFtruncSW(a: V4f32): V4i32 = msaFtruncSW(a)

/**
 * Vector Floating-Point Truncate and Convert to Signed Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to signed integer values.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaFtruncSD(a: V2f64): V2i64 = msaFtruncSD(a)

/**
 * Vector Floating-Point Truncate and Convert to Unsigned Integer
 *
 * The elements in vector `a` (four 32-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to unsigned integer values.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaFtruncUW(a: V4f32): V4u32 = msaFtruncUW(a)

/**
 * Vector Floating-Point Truncate and Convert to Unsigned Integer
 *
 * The elements in vector `a` (two 64-bit floating point numbers)
 * are truncated, i.e. rounded toward zero, to unsigned integer values.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaFtruncUD(a: V2f64): V2u64 = msaFtruncUD(a)

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHaddSH(a: V16i8, b: V16i8): V8i16 = msaHaddSH(a, transmuteWithLayouts(b, V16i8.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (eight signed 16-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHaddSW(a: V8i16, b: V8i16): V4i32 = msaHaddSW(a, transmuteWithLayouts(b, V8i16.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Horizontal Add
 *
 * The sign-extended odd elements in vector `a` (four signed 32-bit integer numbers)
 * are added to the sign-extended even elements in vector `b` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHaddSD(a: V4i32, b: V4i32): V2i64 = msaHaddSD(a, transmuteWithLayouts(b, V4i32.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaHaddUH(a: V16u8, b: V16u8): V8u16 = msaHaddUH(a, transmuteWithLayouts(b, V16u8.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four unsigned 32-bit integer numbers).
 */
public fun msaHaddUW(a: V8u16, b: V8u16): V4u32 = msaHaddUW(a, transmuteWithLayouts(b, V8u16.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Horizontal Add
 *
 * The zero-extended odd elements in vector `a` (four unsigned 32-bit integer numbers)
 * are added to the zero-extended even elements in vector `b` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two unsigned 64-bit integer numbers).
 */
public fun msaHaddUD(a: V4u32, b: V4u32): V2u64 = msaHaddUD(a, transmuteWithLayouts(b, V4u32.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (sixteen signed 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHsubSH(a: V16i8, b: V16i8): V8i16 = msaHsubSH(a, transmuteWithLayouts(b, V16i8.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (eight signed 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHsubSW(a: V8i16, b: V8i16): V4i32 = msaHsubSW(a, transmuteWithLayouts(b, V8i16.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Horizontal Subtract
 *
 * The sign-extended odd elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the sign-extended elements in vector `a` (four signed 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHsubSD(a: V4i32, b: V4i32): V2i64 = msaHsubSD(a, transmuteWithLayouts(b, V4i32.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaHsubUH(a: V16u8, b: V16u8): V8i16 = msaHsubUH(a, transmuteWithLayouts(b, V16u8.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (eight unsigned 16-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaHsubUW(a: V8u16, b: V8u16): V4i32 = msaHsubUW(a, transmuteWithLayouts(b, V8u16.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Unsigned Horizontal Subtract
 *
 * The zero-extended odd elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the zero-extended elements in vector `a` (four unsigned 32-bit integer numbers)
 * producing a result twice the size of the input operands.
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaHsubUD(a: V4u32, b: V4u32): V2i64 = msaHsubUD(a, transmuteWithLayouts(b, V4u32.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevB(a: V16i8, b: V16i8): V16i8 = msaIlvevB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevH(a: V8i16, b: V8i16): V8i16 = msaIlvevH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevW(a: V4i32, b: V4i32): V4i32 = msaIlvevW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Interleave Even
 *
 * Even elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvevD(a: V2i64, b: V2i64): V2i64 = msaIlvevD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlB(a: V16i8, b: V16i8): V16i8 = msaIlvlB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlH(a: V8i16, b: V8i16): V8i16 = msaIlvlH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlW(a: V4i32, b: V4i32): V4i32 = msaIlvlW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Interleave Left
 *
 * The left half elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvlD(a: V2i64, b: V2i64): V2i64 = msaIlvlD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodB(a: V16i8, b: V16i8): V16i8 = msaIlvodB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodH(a: V8i16, b: V8i16): V8i16 = msaIlvodH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodW(a: V4i32, b: V4i32): V4i32 = msaIlvodW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Interleave Odd
 *
 * Odd elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvodD(a: V2i64, b: V2i64): V2i64 = msaIlvodD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * and vector `b` (sixteen signed 8-bit integer numbers) are copied to the result
 * (sixteen signed 8-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrB(a: V16i8, b: V16i8): V16i8 = msaIlvrB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (eight signed 16-bit integer numbers)
 * and vector `b` (eight signed 16-bit integer numbers) are copied to the result
 * (eight signed 16-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrH(a: V8i16, b: V8i16): V8i16 = msaIlvrH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (four signed 32-bit integer numbers)
 * and vector `b` (four signed 32-bit integer numbers) are copied to the result
 * (four signed 32-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrW(a: V4i32, b: V4i32): V4i32 = msaIlvrW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Interleave Right
 *
 * The right half elements in vectors `a` (two signed 64-bit integer numbers)
 * and vector `b` (two signed 64-bit integer numbers) are copied to the result
 * (two signed 64-bit integer numbers)
 * alternating one element from `a` with one element from `b`.
 */
public fun msaIlvrD(a: V2i64, b: V2i64): V2i64 = msaIlvrD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaInsertB(a, imm4, c)
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
    return msaInsertH(a, imm3, c)
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
    return msaInsertW(a, imm2, c)
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
    return msaInsertD(a, imm1, c)
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
    return msaInsveB(a, imm4, c)
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
    return msaInsveH(a, imm3, c)
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
    return msaInsveW(a, imm2, c)
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
    return msaInsveD(a, imm1, c)
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
    return msaLdB(memAddr, immS10)
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
    return msaLdH(memAddr, immS11)
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
    return msaLdW(memAddr, immS12)
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
    return msaLdD(memAddr, immS13)
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
    return msaLdiB(immS10)
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
    return msaLdiH(immS10)
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
    return msaLdiW(immS10)
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
    return msaLdiD(immS10)
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
public fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMaddQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Fixed-Point Multiply and Add
 *
 * The products of fixed-point elements in `b` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (four signed 32-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMaddQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Fixed-Point Multiply and Add Rounded
 *
 * The products of fixed-point elements in `b` (eight signed 16-bit integer numbers)
 * by fixed-point elements in vector `c` (eight signed 16-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (eight signed 16-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The rounded and saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMaddrQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Fixed-Point Multiply and Add Rounded
 *
 * The products of fixed-point elements in `b` (four signed 32-bit integer numbers)
 * by fixed-point elements in vector `c` (four signed 32-bit integer numbers)
 * are added to the fixed-point elements in vector `a` (four signed 32-bit integer numbers).
 * The multiplication result is not saturated, i.e. exact (-1) * (-1) = 1 is added to the destination.
 * The rounded and saturated fixed-point results are stored to vector `a`.
 */
public fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMaddrQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * and added to the integer elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 = msaMaddvB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()), c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `c` (eight signed 16-bit integer numbers)
 * and added to the integer elements in vector `a` (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMaddvH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `c` (four signed 32-bit integer numbers)
 * and added to the integer elements in vector `a` (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMaddvW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Multiply and Add
 *
 * The integer elements in vector `b` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `c` (two signed 64-bit integer numbers)
 * and added to the integer elements in vector `a` (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 = msaMaddvD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (sixteen signed 8-bit integer numbers) and
 * `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMaxAB(a: V16i8, b: V16i8): V16i8 = msaMaxAB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (eight signed 16-bit integer numbers) and
 * `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMaxAH(a: V8i16, b: V8i16): V8i16 = msaMaxAH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (four signed 32-bit integer numbers) and
 * `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMaxAW(a: V4i32, b: V4i32): V4i32 = msaMaxAW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Maximum Based on Absolute Values
 *
 * The value with the largest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (two signed 64-bit integer numbers) and
 * `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMaxAD(a: V2i64, b: V2i64): V2i64 = msaMaxAD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and signed elements in vector `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMaxSB(a: V16i8, b: V16i8): V16i8 = msaMaxSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and signed elements in vector `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMaxSH(a: V8i16, b: V8i16): V8i16 = msaMaxSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and signed elements in vector `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMaxSW(a: V4i32, b: V4i32): V4i32 = msaMaxSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Maximum
 *
 * Maximum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and signed elements in vector `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMaxSD(a: V2i64, b: V2i64): V2i64 = msaMaxSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers) are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMaxUB(a: V16u8, b: V16u8): V16u8 = msaMaxUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and unsigned elements in vector `b` (eight unsigned 16-bit integer numbers) are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMaxUH(a: V8u16, b: V8u16): V8u16 = msaMaxUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and unsigned elements in vector `b` (four unsigned 32-bit integer numbers) are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMaxUW(a: V4u32, b: V4u32): V4u32 = msaMaxUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Maximum
 *
 * Maximum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and unsigned elements in vector `b` (two unsigned 64-bit integer numbers) are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMaxUD(a: V2u64, b: V2u64): V2u64 = msaMaxUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
    return msaMaxiSB(a, immS5)
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
    return msaMaxiSH(a, immS5)
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
    return msaMaxiSW(a, immS5)
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
    return msaMaxiSD(a, immS5)
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
    return msaMaxiUB(a, imm5)
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
    return msaMaxiUH(a, imm5)
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
    return msaMaxiUW(a, imm5)
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
    return msaMaxiUD(a, imm5)
}

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (sixteen signed 8-bit integer numbers) and
 * `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMinAB(a: V16i8, b: V16i8): V16i8 = msaMinAB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (eight signed 16-bit integer numbers) and
 * `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMinAH(a: V8i16, b: V8i16): V8i16 = msaMinAH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (four signed 32-bit integer numbers) and
 * `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMinAW(a: V4i32, b: V4i32): V4i32 = msaMinAW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Minimum Based on Absolute Value
 *
 * The value with the smallest magnitude, i.e. absolute value, between corresponding
 * signed elements in vector `a` (two signed 64-bit integer numbers) and
 * `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMinAD(a: V2i64, b: V2i64): V2i64 = msaMinAD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (sixteen signed 8-bit integer numbers)
 * and signed elements in vector `b` (sixteen signed 8-bit integer numbers) are written to vector
 * (sixteen signed 8-bit integer numbers).
 */
public fun msaMinSB(a: V16i8, b: V16i8): V16i8 = msaMinSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (eight signed 16-bit integer numbers)
 * and signed elements in vector `b` (eight signed 16-bit integer numbers) are written to vector
 * (eight signed 16-bit integer numbers).
 */
public fun msaMinSH(a: V8i16, b: V8i16): V8i16 = msaMinSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (four signed 32-bit integer numbers)
 * and signed elements in vector `b` (four signed 32-bit integer numbers) are written to vector
 * (four signed 32-bit integer numbers).
 */
public fun msaMinSW(a: V4i32, b: V4i32): V4i32 = msaMinSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Minimum
 *
 * Minimum values between signed elements in vector `a` (two signed 64-bit integer numbers)
 * and signed elements in vector `b` (two signed 64-bit integer numbers) are written to vector
 * (two signed 64-bit integer numbers).
 */
public fun msaMinSD(a: V2i64, b: V2i64): V2i64 = msaMinSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaMiniSB(a, immS5)
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
    return msaMiniSH(a, immS5)
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
    return msaMiniSW(a, immS5)
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
    return msaMiniSD(a, immS5)
}

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * and unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers) are written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaMinUB(a: V16u8, b: V16u8): V16u8 = msaMinUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (eight unsigned 16-bit integer numbers)
 * and unsigned elements in vector `b` (eight unsigned 16-bit integer numbers) are written to vector
 * (eight unsigned 16-bit integer numbers).
 */
public fun msaMinUH(a: V8u16, b: V8u16): V8u16 = msaMinUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (four unsigned 32-bit integer numbers)
 * and unsigned elements in vector `b` (four unsigned 32-bit integer numbers) are written to vector
 * (four unsigned 32-bit integer numbers).
 */
public fun msaMinUW(a: V4u32, b: V4u32): V4u32 = msaMinUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Minimum
 *
 * Minimum values between unsigned elements in vector `a` (two unsigned 64-bit integer numbers)
 * and unsigned elements in vector `b` (two unsigned 64-bit integer numbers) are written to vector
 * (two unsigned 64-bit integer numbers).
 */
public fun msaMinUD(a: V2u64, b: V2u64): V2u64 = msaMinUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

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
    return msaMiniUB(a, imm5)
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
    return msaMiniUH(a, imm5)
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
    return msaMiniUW(a, imm5)
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
    return msaMiniUD(a, imm5)
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
public fun msaModSB(a: V16i8, b: V16i8): V16i8 = msaModSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are divided by signed integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (eight signed 16-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSH(a: V8i16, b: V8i16): V8i16 = msaModSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (four signed 32-bit integer numbers)
 * are divided by signed integer elements in vector `b` (four signed 32-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (four signed 32-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSW(a: V4i32, b: V4i32): V4i32 = msaModSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Modulo
 *
 * The signed integer elements in vector `a` (two signed 64-bit integer numbers)
 * are divided by signed integer elements in vector `b` (two signed 64-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (two signed 64-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModSD(a: V2i64, b: V2i64): V2i64 = msaModSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (sixteen unsigned 8-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (sixteen unsigned 8-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (sixteen unsigned 8-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUB(a: V16u8, b: V16u8): V16u8 = msaModUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (eight unsigned 16-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (eight unsigned 16-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (eight unsigned 16-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUH(a: V8u16, b: V8u16): V8u16 = msaModUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (four unsigned 32-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (four unsigned 32-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (four unsigned 32-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUW(a: V4u32, b: V4u32): V4u32 = msaModUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Modulo
 *
 * The unsigned integer elements in vector `a` (two unsigned 64-bit integer numbers)
 * are divided by unsigned integer elements in vector `b` (two unsigned 64-bit integer numbers).
 * The remainder of the same sign as the dividend is written to vector
 * (two unsigned 64-bit integer numbers). If a divisor element vector `b` is zero,
 * the result value is UNPREDICTABLE.
 */
public fun msaModUD(a: V2u64, b: V2u64): V2u64 = msaModUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Move
 *
 * Copy all WRLEN bits in vector `a` (eight signed 16-bit integer numbers)
 * to vector (eight signed 16-bit integer numbers).
 */
public fun msaMoveV(a: V16i8): V16i8 = msaMoveV(a)

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
public fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMsubQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

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
public fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMsubQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

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
public fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMsubrQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

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
public fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMsubrQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `b` (sixteen signed 8-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 = msaMsubvB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()), c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `b` (eight signed 16-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaMsubvH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `b` (four signed 32-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaMsubvW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

/**
 * Vector Multiply and Subtract
 *
 * The integer elements in vector `c` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `b` (two signed 64-bit integer numbers)
 * and subtracted from the integer elements in vector `a` (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 = msaMsubvD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Fixed-Point Multiply
 *
 * The fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaMulQH(a: V8i16, b: V8i16): V8i16 = msaMulQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Fixed-Point Multiply
 *
 * The fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaMulQW(a: V4i32, b: V4i32): V4i32 = msaMulQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Fixed-Point Multiply Rounded
 *
 * The fixed-point elements in vector `a` (eight signed 16-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (eight signed 16-bit integer numbers).
 * The rounded result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaMulrQH(a: V8i16, b: V8i16): V8i16 = msaMulrQH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Fixed-Point Multiply Rounded
 *
 * The fixed-point elements in vector `a` (four signed 32-bit integer numbers)
 * multiplied by fixed-point elements in vector `b` (four signed 32-bit integer numbers).
 * The rounded result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaMulrQW(a: V4i32, b: V4i32): V4i32 = msaMulrQW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are multiplied by integer elements in vector `b` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvB(a: V16i8, b: V16i8): V16i8 = msaMulvB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (eight signed 16-bit integer numbers)
 * are multiplied by integer elements in vector `b` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvH(a: V8i16, b: V8i16): V8i16 = msaMulvH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (four signed 32-bit integer numbers)
 * are multiplied by integer elements in vector `b` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvW(a: V4i32, b: V4i32): V4i32 = msaMulvW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Multiply
 *
 * The integer elements in vector `a` (two signed 64-bit integer numbers)
 * are multiplied by integer elements in vector `b` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 * The most significant half of the multiplication result is discarded.
 */
public fun msaMulvD(a: V2i64, b: V2i64): V2i64 = msaMulvD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in vector (sixteen signed 8-bit integer numbers).
 */
public fun msaNlocB(a: V16i8): V16i8 = msaNlocB(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in vector (eight signed 16-bit integer numbers).
 */
public fun msaNlocH(a: V8i16): V8i16 = msaNlocH(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in vector (four signed 32-bit integer numbers).
 */
public fun msaNlocW(a: V4i32): V4i32 = msaNlocW(a)

/**
 * Vector Leading Ones Count
 *
 * The number of leading ones for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in vector (two signed 64-bit integer numbers).
 */
public fun msaNlocD(a: V2i64): V2i64 = msaNlocD(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in vector (sixteen signed 8-bit integer numbers).
 */
public fun msaNlzcB(a: V16i8): V16i8 = msaNlzcB(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in vector (eight signed 16-bit integer numbers).
 */
public fun msaNlzcH(a: V8i16): V8i16 = msaNlzcH(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in vector (four signed 32-bit integer numbers).
 */
public fun msaNlzcW(a: V4i32): V4i32 = msaNlzcW(a)

/**
 * Vector Leading Zeros Count
 *
 * The number of leading zeros for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in vector (two signed 64-bit integer numbers).
 */
public fun msaNlzcD(a: V2i64): V2i64 = msaNlzcD(a)

/**
 * Vector Logical Negated Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical NOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaNorV(a: V16u8, b: V16u8): V16u8 = msaNorV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

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
    return msaNoriB(a, imm8)
}

/**
 * Vector Logical Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical OR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaOrV(a: V16u8, b: V16u8): V16u8 = msaOrV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

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
    return msaOriB(a, imm8)
}

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (sixteen signed 8-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevB(a: V16i8, b: V16i8): V16i8 = msaPckevB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (eight signed 16-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (eight signed 16-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevH(a: V8i16, b: V8i16): V8i16 = msaPckevH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (four signed 32-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (four signed 32-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevW(a: V4i32, b: V4i32): V4i32 = msaPckevW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Pack Even
 *
 * Even elements in vectors `a` (two signed 64-bit integer numbers)
 * are copied to the left half of the result vector and even elements in vector `b`
 * (two signed 64-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckevD(a: V2i64, b: V2i64): V2i64 = msaPckevD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (sixteen signed 8-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (sixteen signed 8-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodB(a: V16i8, b: V16i8): V16i8 = msaPckodB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (eight signed 16-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (eight signed 16-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodH(a: V8i16, b: V8i16): V8i16 = msaPckodH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (four signed 32-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (four signed 32-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodW(a: V4i32, b: V4i32): V4i32 = msaPckodW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Pack Odd
 *
 * Odd elements in vectors `a` (two signed 64-bit integer numbers)
 * are copied to the left half of the result vector and odd elements in vector `b`
 * (two signed 64-bit integer numbers) are copied to the right half of the result vector.
 */
public fun msaPckodD(a: V2i64, b: V2i64): V2i64 = msaPckodD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (sixteen signed 8-bit integer numbers)
 * is stored to the elements in the result vector (sixteen signed 8-bit integer numbers).
 */
public fun msaPcntB(a: V16i8): V16i8 = msaPcntB(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (eight signed 16-bit integer numbers)
 * is stored to the elements in the result vector (eight signed 16-bit integer numbers).
 */
public fun msaPcntH(a: V8i16): V8i16 = msaPcntH(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (four signed 32-bit integer numbers)
 * is stored to the elements in the result vector (four signed 32-bit integer numbers).
 */
public fun msaPcntW(a: V4i32): V4i32 = msaPcntW(a)

/**
 * Vector Population Count
 *
 * The number of bits set to 1 for elements in vector `a` (two signed 64-bit integer numbers)
 * is stored to the elements in the result vector (two signed 64-bit integer numbers).
 */
public fun msaPcntD(a: V2i64): V2i64 = msaPcntD(a)

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
    return msaSatSB(a, imm3)
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
    return msaSatSH(a, imm4)
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
    return msaSatSW(a, imm5)
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
    return msaSatSD(a, imm6)
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
    return msaSatUB(a, imm3)
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
    return msaSatUH(a, imm4)
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
    return msaSatUW(a, imm5)
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
    return msaSatUD(a, imm6)
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
    return msaShfB(a, imm8)
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
    return msaShfH(a, imm8)
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
    return msaShfW(a, imm8)
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
public fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8 = msaSldB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()), c)

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
public fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16 = msaSldH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

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
public fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32 = msaSldW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

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
public fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64 = msaSldD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()), c)

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
    return msaSldiB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()), imm4)
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
    return msaSldiH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), imm3)
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
    return msaSldiW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), imm2)
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
    return msaSldiD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()), imm1)
}

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSllB(a: V16i8, b: V16i8): V16i8 = msaSllB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSllH(a: V8i16, b: V8i16): V8i16 = msaSllH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSllW(a: V4i32, b: V4i32): V4i32 = msaSllW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Shift Left
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted left by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits. The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSllD(a: V2i64, b: V2i64): V2i64 = msaSllD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSlliB(a, imm4)
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
    return msaSlliH(a, imm3)
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
    return msaSlliW(a, imm2)
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
    return msaSlliD(a, imm1)
}

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (sixteen signed 8-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (sixteen signed 8-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatB(a: V16i8, b: Int): V16i8 = msaSplatB(a, transmuteWithLayouts(b, PrimitiveLayouts.I32, V16i8.typeLayout()))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (eight signed 16-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (eight signed 16-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatH(a: V8i16, b: Int): V8i16 = msaSplatH(a, transmuteWithLayouts(b, PrimitiveLayouts.I32, V8i16.typeLayout()))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (four signed 32-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (four signed 32-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatW(a: V4i32, b: Int): V4i32 = msaSplatW(a, transmuteWithLayouts(b, PrimitiveLayouts.I32, V4i32.typeLayout()))

/**
 * GPR Element Splat
 *
 * Replicate vector `a` (two signed 64-bit integer numbers)
 * element with index given by GPR `b` to all elements in vector
 * (two signed 64-bit integer numbers) GPR `b` value is interpreted
 * modulo the number of data format df elements in the destination vector.
 */
public fun msaSplatD(a: V2i64, b: Int): V2i64 = msaSplatD(a, transmuteWithLayouts(b, PrimitiveLayouts.I32, V2i64.typeLayout()))

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
    return msaSplatiB(a, imm4)
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
    return msaSplatiH(a, imm3)
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
    return msaSplatiW(a, imm2)
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
    return msaSplatiD(a, imm1)
}

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSraB(a: V16i8, b: V16i8): V16i8 = msaSraB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSraH(a: V8i16, b: V8i16): V8i16 = msaSraH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSraW(a: V4i32, b: V4i32): V4i32 = msaSraW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Shift Right Arithmetic
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSraD(a: V2i64, b: V2i64): V2i64 = msaSraD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSraiB(a, imm3)
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
    return msaSraiH(a, imm4)
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
    return msaSraiW(a, imm5)
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
    return msaSraiD(a, imm6)
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
public fun msaSrarB(a: V16i8, b: V16i8): V16i8 = msaSrarB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrarH(a: V8i16, b: V8i16): V8i16 = msaSrarH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrarW(a: V4i32, b: V4i32): V4i32 = msaSrarW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Shift Right Arithmetic Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right arithmetic by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrarD(a: V2i64, b: V2i64): V2i64 = msaSrarD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSrariB(a, imm3)
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
    return msaSrariH(a, imm4)
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
    return msaSrariW(a, imm5)
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
    return msaSrariD(a, imm6)
}

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (sixteen signed 8-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (sixteen signed 8-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSrlB(a: V16i8, b: V16i8): V16i8 = msaSrlB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrlH(a: V8i16, b: V8i16): V8i16 = msaSrlH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrlW(a: V4i32, b: V4i32): V4i32 = msaSrlW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Shift Right Logical
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrlD(a: V2i64, b: V2i64): V2i64 = msaSrlD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSrliB(a, imm4)
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
    return msaSrliH(a, imm3)
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
    return msaSrliW(a, imm2)
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
    return msaSrliD(a, imm1)
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
public fun msaSrlrB(a: V16i8, b: V16i8): V16i8 = msaSrlrB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (eight signed 16-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (eight signed 16-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSrlrH(a: V8i16, b: V8i16): V8i16 = msaSrlrH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (four signed 32-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (four signed 32-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSrlrW(a: V4i32, b: V4i32): V4i32 = msaSrlrW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Shift Right Logical Rounded
 *
 * The elements in vector `a` (two signed 64-bit integer numbers)
 * are shifted right logical by the number of bits the elements in vector `b`
 * (two signed 64-bit integer numbers) specify modulo the size of the
 * element in bits.The most significant discarded bit is added to the shifted
 * value (for rounding) and the result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSrlrD(a: V2i64, b: V2i64): V2i64 = msaSrlrD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSrlriB(a, imm3)
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
    return msaSrlriH(a, imm4)
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
    return msaSrlriW(a, imm5)
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
    return msaSrlriD(a, imm6)
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
    msaStB(a, memAddr, immS10)
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
    msaStH(a, memAddr, immS11)
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
    msaStW(a, memAddr, immS12)
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
    msaStD(a, memAddr, immS13)
}

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen signed 8-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSubsSB(a: V16i8, b: V16i8): V16i8 = msaSubsSB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight signed 16-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (eight signed 16-bit integer numbers).
 */
public fun msaSubsSH(a: V8i16, b: V8i16): V8i16 = msaSubsSH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four signed 32-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (four signed 32-bit integer numbers).
 */
public fun msaSubsSW(a: V4i32, b: V4i32): V4i32 = msaSubsSW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Signed Values
 *
 * The elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two signed 64-bit integer numbers).
 * Signed arithmetic is performed and overflows clamp to the largest and/or smallest
 * representable signed values before writing the result to vector (two signed 64-bit integer numbers).
 */
public fun msaSubsSD(a: V2i64, b: V2i64): V2i64 = msaSubsSD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsUB(a: V16u8, b: V16u8): V16u8 = msaSubsUB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight unsigned 16-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsUH(a: V8u16, b: V8u16): V8u16 = msaSubsUH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four unsigned 32-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsUW(a: V4u32, b: V4u32): V4u32 = msaSubsUW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Unsigned Values
 *
 * The elements in vector `b` (two unsigned 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two unsigned 64-bit integer numbers).
 * Unsigned arithmetic is performed and under-flows clamp to 0 before writing
 * the result to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsUD(a: V2u64, b: V2u64): V2u64 = msaSubsUD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsusUB(a: V16u8, b: V16i8): V16u8 = msaSubsusUB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16u8.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (eight unsigned 16-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsusUH(a: V8u16, b: V8i16): V8u16 = msaSubsusUH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8u16.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (four signed 6432it integer numbers)
 * are subtracted from the unsigned elements in vector `a` (four unsigned 32-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsusUW(a: V4u32, b: V4i32): V4u32 = msaSubsusUW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4u32.typeLayout()))

/**
 * Vector Unsigned Saturated Subtract of Signed from Unsigned
 *
 * The signed elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (two unsigned 64-bit integer numbers).
 * The signed result is unsigned saturated and written to
 * to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsusUD(a: V2u64, b: V2i64): V2u64 = msaSubsusUD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2u64.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (sixteen unsigned 8-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (sixteen unsigned 8-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (sixteen unsigned 8-bit integer numbers).
 */
public fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8 = msaSubsuuSB(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (eight unsigned 16-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (eight unsigned 16-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (eight unsigned 16-bit integer numbers).
 */
public fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16 = msaSubsuuSH(a, transmuteWithLayouts(b, V8u16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (four unsigned 32-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (four unsigned 32-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (four unsigned 32-bit integer numbers).
 */
public fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32 = msaSubsuuSW(a, transmuteWithLayouts(b, V4u32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Signed Saturated Subtract of Unsigned Values
 *
 * The unsigned elements in vector `b` (two unsigned 64-bit integer numbers)
 * are subtracted from the unsigned elements in vector `a` (two unsigned 64-bit integer numbers).
 * The signed result is signed saturated and written to
 * to vector (two unsigned 64-bit integer numbers).
 */
public fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64 = msaSubsuuSD(a, transmuteWithLayouts(b, V2u64.typeLayout(), V2i64.typeLayout()))

/**
 * Vector Subtract
 *
 * The elements in vector `b` (sixteen signed 8-bit integer numbers)
 * are subtracted from the elements in vector `a` (sixteen signed 8-bit integer numbers).
 * The result is written to vector (sixteen signed 8-bit integer numbers).
 */
public fun msaSubvB(a: V16i8, b: V16i8): V16i8 = msaSubvB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()))

/**
 * Vector Subtract
 *
 * The elements in vector `b` (eight signed 16-bit integer numbers)
 * are subtracted from the elements in vector `a` (eight signed 16-bit integer numbers).
 * The result is written to vector (eight signed 16-bit integer numbers).
 */
public fun msaSubvH(a: V8i16, b: V8i16): V8i16 = msaSubvH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()))

/**
 * Vector Subtract
 *
 * The elements in vector `b` (four signed 32-bit integer numbers)
 * are subtracted from the elements in vector `a` (four signed 32-bit integer numbers).
 * The result is written to vector (four signed 32-bit integer numbers).
 */
public fun msaSubvW(a: V4i32, b: V4i32): V4i32 = msaSubvW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()))

/**
 * Vector Subtract
 *
 * The elements in vector `b` (two signed 64-bit integer numbers)
 * are subtracted from the elements in vector `a` (two signed 64-bit integer numbers).
 * The result is written to vector (two signed 64-bit integer numbers).
 */
public fun msaSubvD(a: V2i64, b: V2i64): V2i64 = msaSubvD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()))

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
    return msaSubviB(a, imm5)
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
    return msaSubviH(a, imm5)
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
    return msaSubviW(a, imm5)
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
    return msaSubviD(a, imm5)
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
public fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 = msaVshfB(a, transmuteWithLayouts(b, V16i8.typeLayout(), V16i8.typeLayout()), c)

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
public fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 = msaVshfH(a, transmuteWithLayouts(b, V8i16.typeLayout(), V8i16.typeLayout()), c)

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
public fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 = msaVshfW(a, transmuteWithLayouts(b, V4i32.typeLayout(), V4i32.typeLayout()), c)

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
public fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 = msaVshfD(a, transmuteWithLayouts(b, V2i64.typeLayout(), V2i64.typeLayout()), c)

/**
 * Vector Logical Exclusive Or
 *
 * Each bit of vector `a` (sixteen unsigned 8-bit integer numbers)
 * is combined with the corresponding bit of vector `b` (sixteen unsigned 8-bit integer numbers)
 * in a bitwise logical XOR operation. The result is written to vector
 * (sixteen unsigned 8-bit integer numbers).
 */
public fun msaXorV(a: V16u8, b: V16u8): V16u8 = msaXorV(a, transmuteWithLayouts(b, V16u8.typeLayout(), V16u8.typeLayout()))

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
    return msaXoriB(a, imm8)
}

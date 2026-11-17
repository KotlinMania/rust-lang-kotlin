@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.debugSimdFinish

/**
 * Arm-specific 64-bit wide vector of eight packed `Byte`.
 */
public open class Int8x8T(
    public var value: ByteArray = ByteArray(8),
) : AsUnsigned<Uint8x8T>, AsSigned<Uint8x8T> {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int8x8T = Int8x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.byteArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int8x8_t", this.asArray())

    public override fun asUnsigned(): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(this, Int8x8T.typeLayout(), Uint8x8T.typeLayout())

    public override fun asSigned(): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(this, Int8x8T.typeLayout(), Uint8x8T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x8T> =
            object : TypeLayout<Int8x8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Int8x8T =
                    Int8x8T(
                        (PrimitiveArrayLayouts.byteArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int8x8T) {
                    (PrimitiveArrayLayouts.byteArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x8T =
                    Int8x8T(
                        (PrimitiveArrayLayouts.byteArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x8T) {
                    (PrimitiveArrayLayouts.byteArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Byte): Int8x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Byte>): Int8x8T = transmuteWithLayouts<Simd<Byte>, Int8x8T>(simd, UnresolvedTypeLayout<Simd<Byte>>(), Int8x8T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of eight packed `UByte`.
 */
public open class Uint8x8T(
    public var value: UByteArray = UByteArray(8),
) : AsSigned<Int8x8T>, AsUnsigned<Int8x8T> {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint8x8T = Uint8x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint8x8_t", this.asArray())

    public override fun asSigned(): Int8x8T = transmuteWithLayouts<Uint8x8T, Int8x8T>(this, Uint8x8T.typeLayout(), Int8x8T.typeLayout())

    public override fun asUnsigned(): Int8x8T = transmuteWithLayouts<Uint8x8T, Int8x8T>(this, Uint8x8T.typeLayout(), Int8x8T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x8T> =
            object : TypeLayout<Uint8x8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Uint8x8T =
                    Uint8x8T(
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint8x8T) {
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x8T =
                    Uint8x8T(
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x8T) {
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UByte): Uint8x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UByte>): Uint8x8T = transmuteWithLayouts<Simd<UByte>, Uint8x8T>(simd, UnresolvedTypeLayout<Simd<UByte>>(), Uint8x8T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide polynomial vector of eight packed `p8`.
 */
public open class Poly8x8T(
    public var value: UByteArray = UByteArray(8),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly8x8T = Poly8x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly8x8_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x8T> =
            object : TypeLayout<Poly8x8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Poly8x8T =
                    Poly8x8T(
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly8x8T) {
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x8T =
                    Poly8x8T(
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x8T) {
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P8): Poly8x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P8>): Poly8x8T = transmuteWithLayouts<Simd<P8>, Poly8x8T>(simd, UnresolvedTypeLayout<Simd<P8>>(), Poly8x8T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of four packed `Short`.
 */
public open class Int16x4T(
    public var value: ShortArray = ShortArray(4),
) : AsUnsigned<Uint16x4T>, AsSigned<Uint16x4T> {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int16x4T = Int16x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.shortArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int16x4_t", this.asArray())

    public override fun asUnsigned(): Uint16x4T = transmuteWithLayouts<Int16x4T, Uint16x4T>(this, Int16x4T.typeLayout(), Uint16x4T.typeLayout())

    public override fun asSigned(): Uint16x4T = transmuteWithLayouts<Int16x4T, Uint16x4T>(this, Int16x4T.typeLayout(), Uint16x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x4T> =
            object : TypeLayout<Int16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Int16x4T =
                    Int16x4T(
                        (PrimitiveArrayLayouts.shortArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int16x4T) {
                    (PrimitiveArrayLayouts.shortArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x4T =
                    Int16x4T(
                        (PrimitiveArrayLayouts.shortArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x4T) {
                    (PrimitiveArrayLayouts.shortArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Short): Int16x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Short>): Int16x4T = transmuteWithLayouts<Simd<Short>, Int16x4T>(simd, UnresolvedTypeLayout<Simd<Short>>(), Int16x4T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of four packed `UShort`.
 */
public open class Uint16x4T(
    public var value: UShortArray = UShortArray(4),
) : AsSigned<Int16x4T>, AsUnsigned<Int16x4T> {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint16x4T = Uint16x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint16x4_t", this.asArray())

    public override fun asSigned(): Int16x4T = transmuteWithLayouts<Uint16x4T, Int16x4T>(this, Uint16x4T.typeLayout(), Int16x4T.typeLayout())

    public override fun asUnsigned(): Int16x4T = transmuteWithLayouts<Uint16x4T, Int16x4T>(this, Uint16x4T.typeLayout(), Int16x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x4T> =
            object : TypeLayout<Uint16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Uint16x4T =
                    Uint16x4T(
                        (PrimitiveArrayLayouts.uShortArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint16x4T) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x4T =
                    Uint16x4T(
                        (PrimitiveArrayLayouts.uShortArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x4T) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): Uint16x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): Uint16x4T = transmuteWithLayouts<Simd<UShort>, Uint16x4T>(simd, UnresolvedTypeLayout<Simd<UShort>>(), Uint16x4T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of four packed `p16`.
 */
public open class Poly16x4T(
    public var value: UShortArray = UShortArray(4),
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly16x4T = Poly16x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly16x4_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x4T> =
            object : TypeLayout<Poly16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Poly16x4T =
                    Poly16x4T(
                        (PrimitiveArrayLayouts.uShortArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly16x4T) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x4T =
                    Poly16x4T(
                        (PrimitiveArrayLayouts.uShortArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x4T) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P16): Poly16x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P16>): Poly16x4T = transmuteWithLayouts<Simd<P16>, Poly16x4T>(simd, UnresolvedTypeLayout<Simd<P16>>(), Poly16x4T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of two packed `Int`.
 */
public open class Int32x2T(
    public var value: IntArray = IntArray(2),
) : AsUnsigned<Uint32x2T>, AsSigned<Uint32x2T> {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int32x2T = Int32x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.intArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int32x2_t", this.asArray())

    public override fun asUnsigned(): Uint32x2T = transmuteWithLayouts<Int32x2T, Uint32x2T>(this, Int32x2T.typeLayout(), Uint32x2T.typeLayout())

    public override fun asSigned(): Uint32x2T = transmuteWithLayouts<Int32x2T, Uint32x2T>(this, Int32x2T.typeLayout(), Uint32x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x2T> =
            object : TypeLayout<Int32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Int32x2T =
                    Int32x2T(
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int32x2T) {
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x2T =
                    Int32x2T(
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x2T) {
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): Int32x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Int>): Int32x2T = transmuteWithLayouts<Simd<Int>, Int32x2T>(simd, UnresolvedTypeLayout<Simd<Int>>(), Int32x2T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of two packed `UInt`.
 */
public open class Uint32x2T(
    public var value: UIntArray = UIntArray(2),
) : AsSigned<Int32x2T>, AsUnsigned<Int32x2T> {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint32x2T = Uint32x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UIntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uIntArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint32x2_t", this.asArray())

    public override fun asSigned(): Int32x2T = transmuteWithLayouts<Uint32x2T, Int32x2T>(this, Uint32x2T.typeLayout(), Int32x2T.typeLayout())

    public override fun asUnsigned(): Int32x2T = transmuteWithLayouts<Uint32x2T, Int32x2T>(this, Uint32x2T.typeLayout(), Int32x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x2T> =
            object : TypeLayout<Uint32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Uint32x2T =
                    Uint32x2T(
                        (PrimitiveArrayLayouts.uIntArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint32x2T) {
                    (PrimitiveArrayLayouts.uIntArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x2T =
                    Uint32x2T(
                        (PrimitiveArrayLayouts.uIntArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x2T) {
                    (PrimitiveArrayLayouts.uIntArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UInt): Uint32x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UInt>): Uint32x2T = transmuteWithLayouts<Simd<UInt>, Uint32x2T>(simd, UnresolvedTypeLayout<Simd<UInt>>(), Uint32x2T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of two packed `Float`.
 */
public open class Float32x2T(
    public var value: FloatArray = FloatArray(2),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float32x2T = Float32x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float32x2_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x2T> =
            object : TypeLayout<Float32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Float32x2T =
                    Float32x2T(
                        (PrimitiveArrayLayouts.floatArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float32x2T) {
                    (PrimitiveArrayLayouts.floatArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x2T =
                    Float32x2T(
                        (PrimitiveArrayLayouts.floatArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x2T) {
                    (PrimitiveArrayLayouts.floatArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): Float32x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): Float32x2T = transmuteWithLayouts<Simd<Float>, Float32x2T>(simd, UnresolvedTypeLayout<Simd<Float>>(), Float32x2T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of one packed `Long`.
 */
public open class Int64x1T(
    public var value: LongArray = LongArray(1),
) : AsUnsigned<Uint64x1T>, AsSigned<Uint64x1T> {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int64x1T = Int64x1T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((1).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((1).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int64x1_t", this.asArray())

    public override fun asUnsigned(): Uint64x1T = transmuteWithLayouts<Int64x1T, Uint64x1T>(this, Int64x1T.typeLayout(), Uint64x1T.typeLayout())

    public override fun asSigned(): Uint64x1T = transmuteWithLayouts<Int64x1T, Uint64x1T>(this, Int64x1T.typeLayout(), Uint64x1T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x1T> =
            object : TypeLayout<Int64x1T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Int64x1T =
                    Int64x1T(
                        (PrimitiveArrayLayouts.longArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int64x1T) {
                    (PrimitiveArrayLayouts.longArray(1)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x1T =
                    Int64x1T(
                        (PrimitiveArrayLayouts.longArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x1T) {
                    (PrimitiveArrayLayouts.longArray(1)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): Int64x1T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): Int64x1T = transmuteWithLayouts<Simd<Long>, Int64x1T>(simd, UnresolvedTypeLayout<Simd<Long>>(), Int64x1T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of one packed `ULong`.
 */
public open class Uint64x1T(
    public var value: ULongArray = ULongArray(1),
) : AsSigned<Int64x1T>, AsUnsigned<Int64x1T> {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint64x1T = Uint64x1T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((1).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((1).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint64x1_t", this.asArray())

    public override fun asSigned(): Int64x1T = transmuteWithLayouts<Uint64x1T, Int64x1T>(this, Uint64x1T.typeLayout(), Int64x1T.typeLayout())

    public override fun asUnsigned(): Int64x1T = transmuteWithLayouts<Uint64x1T, Int64x1T>(this, Uint64x1T.typeLayout(), Int64x1T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x1T> =
            object : TypeLayout<Uint64x1T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Uint64x1T =
                    Uint64x1T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint64x1T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x1T =
                    Uint64x1T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x1T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: ULong): Uint64x1T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<ULong>): Uint64x1T = transmuteWithLayouts<Simd<ULong>, Uint64x1T>(simd, UnresolvedTypeLayout<Simd<ULong>>(), Uint64x1T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of one packed `p64`.
 */
public open class Poly64x1T(
    public var value: ULongArray = ULongArray(1),
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly64x1T = Poly64x1T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((1).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((1).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly64x1_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x1T> =
            object : TypeLayout<Poly64x1T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Poly64x1T =
                    Poly64x1T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly64x1T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x1T =
                    Poly64x1T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x1T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P64): Poly64x1T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P64>): Poly64x1T = transmuteWithLayouts<Simd<P64>, Poly64x1T>(simd, UnresolvedTypeLayout<Simd<P64>>(), Poly64x1T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of sixteen packed `Byte`.
 */
public open class Int8x16T(
    public var value: ByteArray = ByteArray(16),
) : AsUnsigned<Uint8x16T>, AsSigned<Uint8x16T> {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int8x16T = Int8x16T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ByteArray = (PrimitiveArrayLayouts.byteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.byteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int8x16_t", this.asArray())

    public override fun asUnsigned(): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(this, Int8x16T.typeLayout(), Uint8x16T.typeLayout())

    public override fun asSigned(): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(this, Int8x16T.typeLayout(), Uint8x16T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x16T> =
            object : TypeLayout<Int8x16T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int8x16T =
                    Int8x16T(
                        (PrimitiveArrayLayouts.byteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int8x16T) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x16T =
                    Int8x16T(
                        (PrimitiveArrayLayouts.byteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x16T) {
                    (PrimitiveArrayLayouts.byteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Byte): Int8x16T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Byte>): Int8x16T = transmuteWithLayouts<Simd<Byte>, Int8x16T>(simd, UnresolvedTypeLayout<Simd<Byte>>(), Int8x16T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of sixteen packed `UByte`.
 */
public open class Uint8x16T(
    public var value: UByteArray = UByteArray(16),
) : AsSigned<Int8x16T>, AsUnsigned<Int8x16T> {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint8x16T = Uint8x16T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint8x16_t", this.asArray())

    public override fun asSigned(): Int8x16T = transmuteWithLayouts<Uint8x16T, Int8x16T>(this, Uint8x16T.typeLayout(), Int8x16T.typeLayout())

    public override fun asUnsigned(): Int8x16T = transmuteWithLayouts<Uint8x16T, Int8x16T>(this, Uint8x16T.typeLayout(), Int8x16T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x16T> =
            object : TypeLayout<Uint8x16T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint8x16T =
                    Uint8x16T(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint8x16T) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x16T =
                    Uint8x16T(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x16T) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UByte): Uint8x16T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UByte>): Uint8x16T = transmuteWithLayouts<Simd<UByte>, Uint8x16T>(simd, UnresolvedTypeLayout<Simd<UByte>>(), Uint8x16T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of sixteen packed `p8`.
 */
public open class Poly8x16T(
    public var value: UByteArray = UByteArray(16),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly8x16T = Poly8x16T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UByteArray = (PrimitiveArrayLayouts.uByteArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly8x16_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x16T> =
            object : TypeLayout<Poly8x16T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly8x16T =
                    Poly8x16T(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly8x16T) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x16T =
                    Poly8x16T(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x16T) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P8): Poly8x16T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P8>): Poly8x16T = transmuteWithLayouts<Simd<P8>, Poly8x16T>(simd, UnresolvedTypeLayout<Simd<P8>>(), Poly8x16T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of eight packed `Short`.
 */
public open class Int16x8T(
    public var value: ShortArray = ShortArray(8),
) : AsUnsigned<Uint16x8T>, AsSigned<Uint16x8T> {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int16x8T = Int16x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ShortArray = (PrimitiveArrayLayouts.shortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.shortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int16x8_t", this.asArray())

    public override fun asUnsigned(): Uint16x8T = transmuteWithLayouts<Int16x8T, Uint16x8T>(this, Int16x8T.typeLayout(), Uint16x8T.typeLayout())

    public override fun asSigned(): Uint16x8T = transmuteWithLayouts<Int16x8T, Uint16x8T>(this, Int16x8T.typeLayout(), Uint16x8T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x8T> =
            object : TypeLayout<Int16x8T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int16x8T =
                    Int16x8T(
                        (PrimitiveArrayLayouts.shortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int16x8T) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x8T =
                    Int16x8T(
                        (PrimitiveArrayLayouts.shortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x8T) {
                    (PrimitiveArrayLayouts.shortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Short): Int16x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Short>): Int16x8T = transmuteWithLayouts<Simd<Short>, Int16x8T>(simd, UnresolvedTypeLayout<Simd<Short>>(), Int16x8T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of eight packed `UShort`.
 */
public open class Uint16x8T(
    public var value: UShortArray = UShortArray(8),
) : AsSigned<Int16x8T>, AsUnsigned<Int16x8T> {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint16x8T = Uint16x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint16x8_t", this.asArray())

    public override fun asSigned(): Int16x8T = transmuteWithLayouts<Uint16x8T, Int16x8T>(this, Uint16x8T.typeLayout(), Int16x8T.typeLayout())

    public override fun asUnsigned(): Int16x8T = transmuteWithLayouts<Uint16x8T, Int16x8T>(this, Uint16x8T.typeLayout(), Int16x8T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x8T> =
            object : TypeLayout<Uint16x8T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint16x8T =
                    Uint16x8T(
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint16x8T) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x8T =
                    Uint16x8T(
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x8T) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): Uint16x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): Uint16x8T = transmuteWithLayouts<Simd<UShort>, Uint16x8T>(simd, UnresolvedTypeLayout<Simd<UShort>>(), Uint16x8T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of eight packed `p16`.
 */
public open class Poly16x8T(
    public var value: UShortArray = UShortArray(8),
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly16x8T = Poly16x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly16x8_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x8T> =
            object : TypeLayout<Poly16x8T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly16x8T =
                    Poly16x8T(
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly16x8T) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x8T =
                    Poly16x8T(
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x8T) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P16): Poly16x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P16>): Poly16x8T = transmuteWithLayouts<Simd<P16>, Poly16x8T>(simd, UnresolvedTypeLayout<Simd<P16>>(), Poly16x8T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of four packed `Int`.
 */
public open class Int32x4T(
    public var value: IntArray = IntArray(4),
) : AsUnsigned<Uint32x4T>, AsSigned<Uint32x4T> {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int32x4T = Int32x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.intArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int32x4_t", this.asArray())

    public override fun asUnsigned(): Uint32x4T = transmuteWithLayouts<Int32x4T, Uint32x4T>(this, Int32x4T.typeLayout(), Uint32x4T.typeLayout())

    public override fun asSigned(): Uint32x4T = transmuteWithLayouts<Int32x4T, Uint32x4T>(this, Int32x4T.typeLayout(), Uint32x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x4T> =
            object : TypeLayout<Int32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int32x4T =
                    Int32x4T(
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int32x4T) {
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x4T =
                    Int32x4T(
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x4T) {
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): Int32x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Int>): Int32x4T = transmuteWithLayouts<Simd<Int>, Int32x4T>(simd, UnresolvedTypeLayout<Simd<Int>>(), Int32x4T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of four packed `UInt`.
 */
public open class Uint32x4T(
    public var value: UIntArray = UIntArray(4),
) : AsSigned<Int32x4T>, AsUnsigned<Int32x4T> {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint32x4T = Uint32x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UIntArray = (PrimitiveArrayLayouts.uIntArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UIntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uIntArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint32x4_t", this.asArray())

    public override fun asSigned(): Int32x4T = transmuteWithLayouts<Uint32x4T, Int32x4T>(this, Uint32x4T.typeLayout(), Int32x4T.typeLayout())

    public override fun asUnsigned(): Int32x4T = transmuteWithLayouts<Uint32x4T, Int32x4T>(this, Uint32x4T.typeLayout(), Int32x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x4T> =
            object : TypeLayout<Uint32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint32x4T =
                    Uint32x4T(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint32x4T) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x4T =
                    Uint32x4T(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x4T) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UInt): Uint32x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UInt>): Uint32x4T = transmuteWithLayouts<Simd<UInt>, Uint32x4T>(simd, UnresolvedTypeLayout<Simd<UInt>>(), Uint32x4T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of four packed `Float`.
 */
public open class Float32x4T(
    public var value: FloatArray = FloatArray(4),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float32x4T = Float32x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float32x4_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x4T> =
            object : TypeLayout<Float32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float32x4T =
                    Float32x4T(
                        (PrimitiveArrayLayouts.floatArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float32x4T) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x4T =
                    Float32x4T(
                        (PrimitiveArrayLayouts.floatArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x4T) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): Float32x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): Float32x4T = transmuteWithLayouts<Simd<Float>, Float32x4T>(simd, UnresolvedTypeLayout<Simd<Float>>(), Float32x4T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of two packed `Long`.
 */
public open class Int64x2T(
    public var value: LongArray = LongArray(2),
) : AsUnsigned<Uint64x2T>, AsSigned<Uint64x2T> {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Int64x2T = Int64x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "int64x2_t", this.asArray())

    public override fun asUnsigned(): Uint64x2T = transmuteWithLayouts<Int64x2T, Uint64x2T>(this, Int64x2T.typeLayout(), Uint64x2T.typeLayout())

    public override fun asSigned(): Uint64x2T = transmuteWithLayouts<Int64x2T, Uint64x2T>(this, Int64x2T.typeLayout(), Uint64x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x2T> =
            object : TypeLayout<Int64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int64x2T =
                    Int64x2T(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Int64x2T) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x2T =
                    Int64x2T(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x2T) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): Int64x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): Int64x2T = transmuteWithLayouts<Simd<Long>, Int64x2T>(simd, UnresolvedTypeLayout<Simd<Long>>(), Int64x2T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of two packed `ULong`.
 */
public open class Uint64x2T(
    public var value: ULongArray = ULongArray(2),
) : AsSigned<Int64x2T>, AsUnsigned<Int64x2T> {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Uint64x2T = Uint64x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "uint64x2_t", this.asArray())

    public override fun asSigned(): Int64x2T = transmuteWithLayouts<Uint64x2T, Int64x2T>(this, Uint64x2T.typeLayout(), Int64x2T.typeLayout())

    public override fun asUnsigned(): Int64x2T = transmuteWithLayouts<Uint64x2T, Int64x2T>(this, Uint64x2T.typeLayout(), Int64x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x2T> =
            object : TypeLayout<Uint64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint64x2T =
                    Uint64x2T(
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Uint64x2T) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x2T =
                    Uint64x2T(
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x2T) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: ULong): Uint64x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<ULong>): Uint64x2T = transmuteWithLayouts<Simd<ULong>, Uint64x2T>(simd, UnresolvedTypeLayout<Simd<ULong>>(), Uint64x2T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of two packed `p64`.
 */
public open class Poly64x2T(
    public var value: ULongArray = ULongArray(2),
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Poly64x2T = Poly64x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "poly64x2_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x2T> =
            object : TypeLayout<Poly64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly64x2T =
                    Poly64x2T(
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Poly64x2T) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x2T =
                    Poly64x2T(
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x2T) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: P64): Poly64x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<P64>): Poly64x2T = transmuteWithLayouts<Simd<P64>, Poly64x2T>(simd, UnresolvedTypeLayout<Simd<P64>>(), Poly64x2T.typeLayout())
    }
}

/**
 * Arm-specific 64-bit wide vector of four packed `f16`.
 */
public open class Float16x4T(
    public var value: List<F16> = List(4) { F16() },
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float16x4T = Float16x4T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float16x4_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x4T> =
            object : TypeLayout<Float16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Float16x4T =
                    Float16x4T(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float16x4T) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x4T =
                    Float16x4T(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x4T) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): Float16x4T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<F16>): Float16x4T = transmuteWithLayouts<Simd<F16>, Float16x4T>(simd, UnresolvedTypeLayout<Simd<F16>>(), Float16x4T.typeLayout())
    }
}

/**
 * Arm-specific 128-bit wide vector of eight packed `f16`.
 */
public open class Float16x8T(
    public var value: List<F16> = List(8) { F16() },
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float16x8T = Float16x8T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float16x8_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x8T> =
            object : TypeLayout<Float16x8T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float16x8T =
                    Float16x8T(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float16x8T) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x8T =
                    Float16x8T(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x8T) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): Float16x8T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<F16>): Float16x8T = transmuteWithLayouts<Simd<F16>, Float16x8T>(simd, UnresolvedTypeLayout<Simd<F16>>(), Float16x8T.typeLayout())
    }
}

/**
 * Arm-specific type containing two `int8x8T` vectors.
 */
public open class Int8x8x2T(
    public var field0: Int8x8T,
    public var field1: Int8x8T,
) : AsUnsigned<Uint8x8x2T>, AsSigned<Uint8x8x2T> {
    public open fun clone(): Int8x8x2T = Int8x8x2T(field0, field1)

    public override fun asUnsigned(): Uint8x8x2T = transmuteWithLayouts<Int8x8x2T, Uint8x8x2T>(this, Int8x8x2T.typeLayout(), Uint8x8x2T.typeLayout())

    public override fun asSigned(): Uint8x8x2T = transmuteWithLayouts<Int8x8x2T, Uint8x8x2T>(this, Int8x8x2T.typeLayout(), Uint8x8x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x8x2T> =
            object : TypeLayout<Int8x8x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int8x8x2T =
                    Int8x8x2T(
                        (Int8x8T.typeLayout()).readNative(addr),
                        (Int8x8T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Int8x8x2T) {
                    (Int8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x8x2T =
                    Int8x8x2T(
                        (Int8x8T.typeLayout()).readBytes(buf, offset),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x8x2T) {
                    (Int8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int8x8T` vectors.
 */
public open class Int8x8x3T(
    public var field0: Int8x8T,
    public var field1: Int8x8T,
    public var field2: Int8x8T,
) : AsUnsigned<Uint8x8x3T>, AsSigned<Uint8x8x3T> {
    public open fun clone(): Int8x8x3T = Int8x8x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint8x8x3T = transmuteWithLayouts<Int8x8x3T, Uint8x8x3T>(this, Int8x8x3T.typeLayout(), Uint8x8x3T.typeLayout())

    public override fun asSigned(): Uint8x8x3T = transmuteWithLayouts<Int8x8x3T, Uint8x8x3T>(this, Int8x8x3T.typeLayout(), Uint8x8x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x8x3T> =
            object : TypeLayout<Int8x8x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Int8x8x3T =
                    Int8x8x3T(
                        (Int8x8T.typeLayout()).readNative(addr),
                        (Int8x8T.typeLayout()).readNative(addr + 8L),
                        (Int8x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int8x8x3T) {
                    (Int8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x8x3T =
                    Int8x8x3T(
                        (Int8x8T.typeLayout()).readBytes(buf, offset),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x8x3T) {
                    (Int8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int8x8T` vectors.
 */
public open class Int8x8x4T(
    public var field0: Int8x8T,
    public var field1: Int8x8T,
    public var field2: Int8x8T,
    public var field3: Int8x8T,
) : AsUnsigned<Uint8x8x4T>, AsSigned<Uint8x8x4T> {
    public open fun clone(): Int8x8x4T = Int8x8x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint8x8x4T = transmuteWithLayouts<Int8x8x4T, Uint8x8x4T>(this, Int8x8x4T.typeLayout(), Uint8x8x4T.typeLayout())

    public override fun asSigned(): Uint8x8x4T = transmuteWithLayouts<Int8x8x4T, Uint8x8x4T>(this, Int8x8x4T.typeLayout(), Uint8x8x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x8x4T> =
            object : TypeLayout<Int8x8x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int8x8x4T =
                    Int8x8x4T(
                        (Int8x8T.typeLayout()).readNative(addr),
                        (Int8x8T.typeLayout()).readNative(addr + 8L),
                        (Int8x8T.typeLayout()).readNative(addr + 16L),
                        (Int8x8T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Int8x8x4T) {
                    (Int8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Int8x8T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x8x4T =
                    Int8x8x4T(
                        (Int8x8T.typeLayout()).readBytes(buf, offset),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Int8x8T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x8x4T) {
                    (Int8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Int8x8T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int8x16T` vectors.
 */
public open class Int8x16x2T(
    public var field0: Int8x16T,
    public var field1: Int8x16T,
) : AsUnsigned<Uint8x16x2T>, AsSigned<Uint8x16x2T> {
    public open fun clone(): Int8x16x2T = Int8x16x2T(field0, field1)

    public override fun asUnsigned(): Uint8x16x2T = transmuteWithLayouts<Int8x16x2T, Uint8x16x2T>(this, Int8x16x2T.typeLayout(), Uint8x16x2T.typeLayout())

    public override fun asSigned(): Uint8x16x2T = transmuteWithLayouts<Int8x16x2T, Uint8x16x2T>(this, Int8x16x2T.typeLayout(), Uint8x16x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x16x2T> =
            object : TypeLayout<Int8x16x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int8x16x2T =
                    Int8x16x2T(
                        (Int8x16T.typeLayout()).readNative(addr),
                        (Int8x16T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int8x16x2T) {
                    (Int8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x16x2T =
                    Int8x16x2T(
                        (Int8x16T.typeLayout()).readBytes(buf, offset),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x16x2T) {
                    (Int8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int8x16T` vectors.
 */
public open class Int8x16x3T(
    public var field0: Int8x16T,
    public var field1: Int8x16T,
    public var field2: Int8x16T,
) : AsUnsigned<Uint8x16x3T>, AsSigned<Uint8x16x3T> {
    public open fun clone(): Int8x16x3T = Int8x16x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint8x16x3T = transmuteWithLayouts<Int8x16x3T, Uint8x16x3T>(this, Int8x16x3T.typeLayout(), Uint8x16x3T.typeLayout())

    public override fun asSigned(): Uint8x16x3T = transmuteWithLayouts<Int8x16x3T, Uint8x16x3T>(this, Int8x16x3T.typeLayout(), Uint8x16x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x16x3T> =
            object : TypeLayout<Int8x16x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Int8x16x3T =
                    Int8x16x3T(
                        (Int8x16T.typeLayout()).readNative(addr),
                        (Int8x16T.typeLayout()).readNative(addr + 16L),
                        (Int8x16T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Int8x16x3T) {
                    (Int8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x16x3T =
                    Int8x16x3T(
                        (Int8x16T.typeLayout()).readBytes(buf, offset),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x16x3T) {
                    (Int8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int8x16T` vectors.
 */
public open class Int8x16x4T(
    public var field0: Int8x16T,
    public var field1: Int8x16T,
    public var field2: Int8x16T,
    public var field3: Int8x16T,
) : AsUnsigned<Uint8x16x4T>, AsSigned<Uint8x16x4T> {
    public open fun clone(): Int8x16x4T = Int8x16x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint8x16x4T = transmuteWithLayouts<Int8x16x4T, Uint8x16x4T>(this, Int8x16x4T.typeLayout(), Uint8x16x4T.typeLayout())

    public override fun asSigned(): Uint8x16x4T = transmuteWithLayouts<Int8x16x4T, Uint8x16x4T>(this, Int8x16x4T.typeLayout(), Uint8x16x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int8x16x4T> =
            object : TypeLayout<Int8x16x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Int8x16x4T =
                    Int8x16x4T(
                        (Int8x16T.typeLayout()).readNative(addr),
                        (Int8x16T.typeLayout()).readNative(addr + 16L),
                        (Int8x16T.typeLayout()).readNative(addr + 32L),
                        (Int8x16T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Int8x16x4T) {
                    (Int8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Int8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Int8x16T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int8x16x4T =
                    Int8x16x4T(
                        (Int8x16T.typeLayout()).readBytes(buf, offset),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 32),
                        (Int8x16T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int8x16x4T) {
                    (Int8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Int8x16T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint8x8T` vectors.
 */
public open class Uint8x8x2T(
    public var field0: Uint8x8T,
    public var field1: Uint8x8T,
) : AsSigned<Int8x8x2T>, AsUnsigned<Int8x8x2T> {
    public open fun clone(): Uint8x8x2T = Uint8x8x2T(field0, field1)

    public override fun asSigned(): Int8x8x2T = transmuteWithLayouts<Uint8x8x2T, Int8x8x2T>(this, Uint8x8x2T.typeLayout(), Int8x8x2T.typeLayout())

    public override fun asUnsigned(): Int8x8x2T = transmuteWithLayouts<Uint8x8x2T, Int8x8x2T>(this, Uint8x8x2T.typeLayout(), Int8x8x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x8x2T> =
            object : TypeLayout<Uint8x8x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint8x8x2T =
                    Uint8x8x2T(
                        (Uint8x8T.typeLayout()).readNative(addr),
                        (Uint8x8T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Uint8x8x2T) {
                    (Uint8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x8x2T =
                    Uint8x8x2T(
                        (Uint8x8T.typeLayout()).readBytes(buf, offset),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x8x2T) {
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint8x8T` vectors.
 */
public open class Uint8x8x3T(
    public var field0: Uint8x8T,
    public var field1: Uint8x8T,
    public var field2: Uint8x8T,
) : AsSigned<Int8x8x3T>, AsUnsigned<Int8x8x3T> {
    public open fun clone(): Uint8x8x3T = Uint8x8x3T(field0, field1, field2)

    public override fun asSigned(): Int8x8x3T = transmuteWithLayouts<Uint8x8x3T, Int8x8x3T>(this, Uint8x8x3T.typeLayout(), Int8x8x3T.typeLayout())

    public override fun asUnsigned(): Int8x8x3T = transmuteWithLayouts<Uint8x8x3T, Int8x8x3T>(this, Uint8x8x3T.typeLayout(), Int8x8x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x8x3T> =
            object : TypeLayout<Uint8x8x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Uint8x8x3T =
                    Uint8x8x3T(
                        (Uint8x8T.typeLayout()).readNative(addr),
                        (Uint8x8T.typeLayout()).readNative(addr + 8L),
                        (Uint8x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint8x8x3T) {
                    (Uint8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x8x3T =
                    Uint8x8x3T(
                        (Uint8x8T.typeLayout()).readBytes(buf, offset),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x8x3T) {
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint8x8T` vectors.
 */
public open class Uint8x8x4T(
    public var field0: Uint8x8T,
    public var field1: Uint8x8T,
    public var field2: Uint8x8T,
    public var field3: Uint8x8T,
) : AsSigned<Int8x8x4T>, AsUnsigned<Int8x8x4T> {
    public open fun clone(): Uint8x8x4T = Uint8x8x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int8x8x4T = transmuteWithLayouts<Uint8x8x4T, Int8x8x4T>(this, Uint8x8x4T.typeLayout(), Int8x8x4T.typeLayout())

    public override fun asUnsigned(): Int8x8x4T = transmuteWithLayouts<Uint8x8x4T, Int8x8x4T>(this, Uint8x8x4T.typeLayout(), Int8x8x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x8x4T> =
            object : TypeLayout<Uint8x8x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint8x8x4T =
                    Uint8x8x4T(
                        (Uint8x8T.typeLayout()).readNative(addr),
                        (Uint8x8T.typeLayout()).readNative(addr + 8L),
                        (Uint8x8T.typeLayout()).readNative(addr + 16L),
                        (Uint8x8T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Uint8x8x4T) {
                    (Uint8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Uint8x8T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x8x4T =
                    Uint8x8x4T(
                        (Uint8x8T.typeLayout()).readBytes(buf, offset),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint8x8T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x8x4T) {
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Uint8x8T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint8x16T` vectors.
 */
public open class Uint8x16x2T(
    public var field0: Uint8x16T,
    public var field1: Uint8x16T,
) : AsSigned<Int8x16x2T>, AsUnsigned<Int8x16x2T> {
    public open fun clone(): Uint8x16x2T = Uint8x16x2T(field0, field1)

    public override fun asSigned(): Int8x16x2T = transmuteWithLayouts<Uint8x16x2T, Int8x16x2T>(this, Uint8x16x2T.typeLayout(), Int8x16x2T.typeLayout())

    public override fun asUnsigned(): Int8x16x2T = transmuteWithLayouts<Uint8x16x2T, Int8x16x2T>(this, Uint8x16x2T.typeLayout(), Int8x16x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x16x2T> =
            object : TypeLayout<Uint8x16x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint8x16x2T =
                    Uint8x16x2T(
                        (Uint8x16T.typeLayout()).readNative(addr),
                        (Uint8x16T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint8x16x2T) {
                    (Uint8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x16x2T =
                    Uint8x16x2T(
                        (Uint8x16T.typeLayout()).readBytes(buf, offset),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x16x2T) {
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint8x16T` vectors.
 */
public open class Uint8x16x3T(
    public var field0: Uint8x16T,
    public var field1: Uint8x16T,
    public var field2: Uint8x16T,
) : AsSigned<Int8x16x3T>, AsUnsigned<Int8x16x3T> {
    public open fun clone(): Uint8x16x3T = Uint8x16x3T(field0, field1, field2)

    public override fun asSigned(): Int8x16x3T = transmuteWithLayouts<Uint8x16x3T, Int8x16x3T>(this, Uint8x16x3T.typeLayout(), Int8x16x3T.typeLayout())

    public override fun asUnsigned(): Int8x16x3T = transmuteWithLayouts<Uint8x16x3T, Int8x16x3T>(this, Uint8x16x3T.typeLayout(), Int8x16x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x16x3T> =
            object : TypeLayout<Uint8x16x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Uint8x16x3T =
                    Uint8x16x3T(
                        (Uint8x16T.typeLayout()).readNative(addr),
                        (Uint8x16T.typeLayout()).readNative(addr + 16L),
                        (Uint8x16T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Uint8x16x3T) {
                    (Uint8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x16x3T =
                    Uint8x16x3T(
                        (Uint8x16T.typeLayout()).readBytes(buf, offset),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x16x3T) {
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint8x16T` vectors.
 */
public open class Uint8x16x4T(
    public var field0: Uint8x16T,
    public var field1: Uint8x16T,
    public var field2: Uint8x16T,
    public var field3: Uint8x16T,
) : AsSigned<Int8x16x4T>, AsUnsigned<Int8x16x4T> {
    public open fun clone(): Uint8x16x4T = Uint8x16x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int8x16x4T = transmuteWithLayouts<Uint8x16x4T, Int8x16x4T>(this, Uint8x16x4T.typeLayout(), Int8x16x4T.typeLayout())

    public override fun asUnsigned(): Int8x16x4T = transmuteWithLayouts<Uint8x16x4T, Int8x16x4T>(this, Uint8x16x4T.typeLayout(), Int8x16x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint8x16x4T> =
            object : TypeLayout<Uint8x16x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Uint8x16x4T =
                    Uint8x16x4T(
                        (Uint8x16T.typeLayout()).readNative(addr),
                        (Uint8x16T.typeLayout()).readNative(addr + 16L),
                        (Uint8x16T.typeLayout()).readNative(addr + 32L),
                        (Uint8x16T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Uint8x16x4T) {
                    (Uint8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Uint8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Uint8x16T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint8x16x4T =
                    Uint8x16x4T(
                        (Uint8x16T.typeLayout()).readBytes(buf, offset),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 32),
                        (Uint8x16T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint8x16x4T) {
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Uint8x16T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly8x8T` vectors.
 */
public open class Poly8x8x2T(
    public var field0: Poly8x8T,
    public var field1: Poly8x8T,
) {
    public open fun clone(): Poly8x8x2T = Poly8x8x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x8x2T> =
            object : TypeLayout<Poly8x8x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly8x8x2T =
                    Poly8x8x2T(
                        (Poly8x8T.typeLayout()).readNative(addr),
                        (Poly8x8T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Poly8x8x2T) {
                    (Poly8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x8x2T =
                    Poly8x8x2T(
                        (Poly8x8T.typeLayout()).readBytes(buf, offset),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x8x2T) {
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly8x8T` vectors.
 */
public open class Poly8x8x3T(
    public var field0: Poly8x8T,
    public var field1: Poly8x8T,
    public var field2: Poly8x8T,
) {
    public open fun clone(): Poly8x8x3T = Poly8x8x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x8x3T> =
            object : TypeLayout<Poly8x8x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Poly8x8x3T =
                    Poly8x8x3T(
                        (Poly8x8T.typeLayout()).readNative(addr),
                        (Poly8x8T.typeLayout()).readNative(addr + 8L),
                        (Poly8x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly8x8x3T) {
                    (Poly8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x8x3T =
                    Poly8x8x3T(
                        (Poly8x8T.typeLayout()).readBytes(buf, offset),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x8x3T) {
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly8x8T` vectors.
 */
public open class Poly8x8x4T(
    public var field0: Poly8x8T,
    public var field1: Poly8x8T,
    public var field2: Poly8x8T,
    public var field3: Poly8x8T,
) {
    public open fun clone(): Poly8x8x4T = Poly8x8x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x8x4T> =
            object : TypeLayout<Poly8x8x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly8x8x4T =
                    Poly8x8x4T(
                        (Poly8x8T.typeLayout()).readNative(addr),
                        (Poly8x8T.typeLayout()).readNative(addr + 8L),
                        (Poly8x8T.typeLayout()).readNative(addr + 16L),
                        (Poly8x8T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Poly8x8x4T) {
                    (Poly8x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x8T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly8x8T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Poly8x8T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x8x4T =
                    Poly8x8x4T(
                        (Poly8x8T.typeLayout()).readBytes(buf, offset),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly8x8T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x8x4T) {
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Poly8x8T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly8x16T` vectors.
 */
public open class Poly8x16x2T(
    public var field0: Poly8x16T,
    public var field1: Poly8x16T,
) {
    public open fun clone(): Poly8x16x2T = Poly8x16x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x16x2T> =
            object : TypeLayout<Poly8x16x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly8x16x2T =
                    Poly8x16x2T(
                        (Poly8x16T.typeLayout()).readNative(addr),
                        (Poly8x16T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly8x16x2T) {
                    (Poly8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x16x2T =
                    Poly8x16x2T(
                        (Poly8x16T.typeLayout()).readBytes(buf, offset),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x16x2T) {
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly8x16T` vectors.
 */
public open class Poly8x16x3T(
    public var field0: Poly8x16T,
    public var field1: Poly8x16T,
    public var field2: Poly8x16T,
) {
    public open fun clone(): Poly8x16x3T = Poly8x16x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x16x3T> =
            object : TypeLayout<Poly8x16x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Poly8x16x3T =
                    Poly8x16x3T(
                        (Poly8x16T.typeLayout()).readNative(addr),
                        (Poly8x16T.typeLayout()).readNative(addr + 16L),
                        (Poly8x16T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Poly8x16x3T) {
                    (Poly8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x16x3T =
                    Poly8x16x3T(
                        (Poly8x16T.typeLayout()).readBytes(buf, offset),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x16x3T) {
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly8x16T` vectors.
 */
public open class Poly8x16x4T(
    public var field0: Poly8x16T,
    public var field1: Poly8x16T,
    public var field2: Poly8x16T,
    public var field3: Poly8x16T,
) {
    public open fun clone(): Poly8x16x4T = Poly8x16x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly8x16x4T> =
            object : TypeLayout<Poly8x16x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Poly8x16x4T =
                    Poly8x16x4T(
                        (Poly8x16T.typeLayout()).readNative(addr),
                        (Poly8x16T.typeLayout()).readNative(addr + 16L),
                        (Poly8x16T.typeLayout()).readNative(addr + 32L),
                        (Poly8x16T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Poly8x16x4T) {
                    (Poly8x16T.typeLayout()).writeNative(addr, value.field0)
                    (Poly8x16T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly8x16T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Poly8x16T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly8x16x4T =
                    Poly8x16x4T(
                        (Poly8x16T.typeLayout()).readBytes(buf, offset),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 32),
                        (Poly8x16T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly8x16x4T) {
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Poly8x16T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int16x4T` vectors.
 */
public open class Int16x4x2T(
    public var field0: Int16x4T,
    public var field1: Int16x4T,
) : AsUnsigned<Uint16x4x2T>, AsSigned<Uint16x4x2T> {
    public open fun clone(): Int16x4x2T = Int16x4x2T(field0, field1)

    public override fun asUnsigned(): Uint16x4x2T = transmuteWithLayouts<Int16x4x2T, Uint16x4x2T>(this, Int16x4x2T.typeLayout(), Uint16x4x2T.typeLayout())

    public override fun asSigned(): Uint16x4x2T = transmuteWithLayouts<Int16x4x2T, Uint16x4x2T>(this, Int16x4x2T.typeLayout(), Uint16x4x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x4x2T> =
            object : TypeLayout<Int16x4x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int16x4x2T =
                    Int16x4x2T(
                        (Int16x4T.typeLayout()).readNative(addr),
                        (Int16x4T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Int16x4x2T) {
                    (Int16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x4x2T =
                    Int16x4x2T(
                        (Int16x4T.typeLayout()).readBytes(buf, offset),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x4x2T) {
                    (Int16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int16x4T` vectors.
 */
public open class Int16x4x3T(
    public var field0: Int16x4T,
    public var field1: Int16x4T,
    public var field2: Int16x4T,
) : AsUnsigned<Uint16x4x3T>, AsSigned<Uint16x4x3T> {
    public open fun clone(): Int16x4x3T = Int16x4x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint16x4x3T = transmuteWithLayouts<Int16x4x3T, Uint16x4x3T>(this, Int16x4x3T.typeLayout(), Uint16x4x3T.typeLayout())

    public override fun asSigned(): Uint16x4x3T = transmuteWithLayouts<Int16x4x3T, Uint16x4x3T>(this, Int16x4x3T.typeLayout(), Uint16x4x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x4x3T> =
            object : TypeLayout<Int16x4x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Int16x4x3T =
                    Int16x4x3T(
                        (Int16x4T.typeLayout()).readNative(addr),
                        (Int16x4T.typeLayout()).readNative(addr + 8L),
                        (Int16x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int16x4x3T) {
                    (Int16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x4x3T =
                    Int16x4x3T(
                        (Int16x4T.typeLayout()).readBytes(buf, offset),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x4x3T) {
                    (Int16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int16x4T` vectors.
 */
public open class Int16x4x4T(
    public var field0: Int16x4T,
    public var field1: Int16x4T,
    public var field2: Int16x4T,
    public var field3: Int16x4T,
) : AsUnsigned<Uint16x4x4T>, AsSigned<Uint16x4x4T> {
    public open fun clone(): Int16x4x4T = Int16x4x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint16x4x4T = transmuteWithLayouts<Int16x4x4T, Uint16x4x4T>(this, Int16x4x4T.typeLayout(), Uint16x4x4T.typeLayout())

    public override fun asSigned(): Uint16x4x4T = transmuteWithLayouts<Int16x4x4T, Uint16x4x4T>(this, Int16x4x4T.typeLayout(), Uint16x4x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x4x4T> =
            object : TypeLayout<Int16x4x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int16x4x4T =
                    Int16x4x4T(
                        (Int16x4T.typeLayout()).readNative(addr),
                        (Int16x4T.typeLayout()).readNative(addr + 8L),
                        (Int16x4T.typeLayout()).readNative(addr + 16L),
                        (Int16x4T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Int16x4x4T) {
                    (Int16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Int16x4T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x4x4T =
                    Int16x4x4T(
                        (Int16x4T.typeLayout()).readBytes(buf, offset),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Int16x4T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x4x4T) {
                    (Int16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Int16x4T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int16x8T` vectors.
 */
public open class Int16x8x2T(
    public var field0: Int16x8T,
    public var field1: Int16x8T,
) : AsUnsigned<Uint16x8x2T>, AsSigned<Uint16x8x2T> {
    public open fun clone(): Int16x8x2T = Int16x8x2T(field0, field1)

    public override fun asUnsigned(): Uint16x8x2T = transmuteWithLayouts<Int16x8x2T, Uint16x8x2T>(this, Int16x8x2T.typeLayout(), Uint16x8x2T.typeLayout())

    public override fun asSigned(): Uint16x8x2T = transmuteWithLayouts<Int16x8x2T, Uint16x8x2T>(this, Int16x8x2T.typeLayout(), Uint16x8x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x8x2T> =
            object : TypeLayout<Int16x8x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int16x8x2T =
                    Int16x8x2T(
                        (Int16x8T.typeLayout()).readNative(addr),
                        (Int16x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int16x8x2T) {
                    (Int16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x8x2T =
                    Int16x8x2T(
                        (Int16x8T.typeLayout()).readBytes(buf, offset),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x8x2T) {
                    (Int16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int16x8T` vectors.
 */
public open class Int16x8x3T(
    public var field0: Int16x8T,
    public var field1: Int16x8T,
    public var field2: Int16x8T,
) : AsUnsigned<Uint16x8x3T>, AsSigned<Uint16x8x3T> {
    public open fun clone(): Int16x8x3T = Int16x8x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint16x8x3T = transmuteWithLayouts<Int16x8x3T, Uint16x8x3T>(this, Int16x8x3T.typeLayout(), Uint16x8x3T.typeLayout())

    public override fun asSigned(): Uint16x8x3T = transmuteWithLayouts<Int16x8x3T, Uint16x8x3T>(this, Int16x8x3T.typeLayout(), Uint16x8x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x8x3T> =
            object : TypeLayout<Int16x8x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Int16x8x3T =
                    Int16x8x3T(
                        (Int16x8T.typeLayout()).readNative(addr),
                        (Int16x8T.typeLayout()).readNative(addr + 16L),
                        (Int16x8T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Int16x8x3T) {
                    (Int16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x8x3T =
                    Int16x8x3T(
                        (Int16x8T.typeLayout()).readBytes(buf, offset),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x8x3T) {
                    (Int16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int16x8T` vectors.
 */
public open class Int16x8x4T(
    public var field0: Int16x8T,
    public var field1: Int16x8T,
    public var field2: Int16x8T,
    public var field3: Int16x8T,
) : AsUnsigned<Uint16x8x4T>, AsSigned<Uint16x8x4T> {
    public open fun clone(): Int16x8x4T = Int16x8x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint16x8x4T = transmuteWithLayouts<Int16x8x4T, Uint16x8x4T>(this, Int16x8x4T.typeLayout(), Uint16x8x4T.typeLayout())

    public override fun asSigned(): Uint16x8x4T = transmuteWithLayouts<Int16x8x4T, Uint16x8x4T>(this, Int16x8x4T.typeLayout(), Uint16x8x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int16x8x4T> =
            object : TypeLayout<Int16x8x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Int16x8x4T =
                    Int16x8x4T(
                        (Int16x8T.typeLayout()).readNative(addr),
                        (Int16x8T.typeLayout()).readNative(addr + 16L),
                        (Int16x8T.typeLayout()).readNative(addr + 32L),
                        (Int16x8T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Int16x8x4T) {
                    (Int16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Int16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Int16x8T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int16x8x4T =
                    Int16x8x4T(
                        (Int16x8T.typeLayout()).readBytes(buf, offset),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 32),
                        (Int16x8T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int16x8x4T) {
                    (Int16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Int16x8T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint16x4T` vectors.
 */
public open class Uint16x4x2T(
    public var field0: Uint16x4T,
    public var field1: Uint16x4T,
) : AsSigned<Int16x4x2T>, AsUnsigned<Int16x4x2T> {
    public open fun clone(): Uint16x4x2T = Uint16x4x2T(field0, field1)

    public override fun asSigned(): Int16x4x2T = transmuteWithLayouts<Uint16x4x2T, Int16x4x2T>(this, Uint16x4x2T.typeLayout(), Int16x4x2T.typeLayout())

    public override fun asUnsigned(): Int16x4x2T = transmuteWithLayouts<Uint16x4x2T, Int16x4x2T>(this, Uint16x4x2T.typeLayout(), Int16x4x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x4x2T> =
            object : TypeLayout<Uint16x4x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint16x4x2T =
                    Uint16x4x2T(
                        (Uint16x4T.typeLayout()).readNative(addr),
                        (Uint16x4T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Uint16x4x2T) {
                    (Uint16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x4x2T =
                    Uint16x4x2T(
                        (Uint16x4T.typeLayout()).readBytes(buf, offset),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x4x2T) {
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint16x4T` vectors.
 */
public open class Uint16x4x3T(
    public var field0: Uint16x4T,
    public var field1: Uint16x4T,
    public var field2: Uint16x4T,
) : AsSigned<Int16x4x3T>, AsUnsigned<Int16x4x3T> {
    public open fun clone(): Uint16x4x3T = Uint16x4x3T(field0, field1, field2)

    public override fun asSigned(): Int16x4x3T = transmuteWithLayouts<Uint16x4x3T, Int16x4x3T>(this, Uint16x4x3T.typeLayout(), Int16x4x3T.typeLayout())

    public override fun asUnsigned(): Int16x4x3T = transmuteWithLayouts<Uint16x4x3T, Int16x4x3T>(this, Uint16x4x3T.typeLayout(), Int16x4x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x4x3T> =
            object : TypeLayout<Uint16x4x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Uint16x4x3T =
                    Uint16x4x3T(
                        (Uint16x4T.typeLayout()).readNative(addr),
                        (Uint16x4T.typeLayout()).readNative(addr + 8L),
                        (Uint16x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint16x4x3T) {
                    (Uint16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x4x3T =
                    Uint16x4x3T(
                        (Uint16x4T.typeLayout()).readBytes(buf, offset),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x4x3T) {
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint16x4T` vectors.
 */
public open class Uint16x4x4T(
    public var field0: Uint16x4T,
    public var field1: Uint16x4T,
    public var field2: Uint16x4T,
    public var field3: Uint16x4T,
) : AsSigned<Int16x4x4T>, AsUnsigned<Int16x4x4T> {
    public open fun clone(): Uint16x4x4T = Uint16x4x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int16x4x4T = transmuteWithLayouts<Uint16x4x4T, Int16x4x4T>(this, Uint16x4x4T.typeLayout(), Int16x4x4T.typeLayout())

    public override fun asUnsigned(): Int16x4x4T = transmuteWithLayouts<Uint16x4x4T, Int16x4x4T>(this, Uint16x4x4T.typeLayout(), Int16x4x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x4x4T> =
            object : TypeLayout<Uint16x4x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint16x4x4T =
                    Uint16x4x4T(
                        (Uint16x4T.typeLayout()).readNative(addr),
                        (Uint16x4T.typeLayout()).readNative(addr + 8L),
                        (Uint16x4T.typeLayout()).readNative(addr + 16L),
                        (Uint16x4T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Uint16x4x4T) {
                    (Uint16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Uint16x4T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x4x4T =
                    Uint16x4x4T(
                        (Uint16x4T.typeLayout()).readBytes(buf, offset),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint16x4T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x4x4T) {
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Uint16x4T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint16x8T` vectors.
 */
public open class Uint16x8x2T(
    public var field0: Uint16x8T,
    public var field1: Uint16x8T,
) : AsSigned<Int16x8x2T>, AsUnsigned<Int16x8x2T> {
    public open fun clone(): Uint16x8x2T = Uint16x8x2T(field0, field1)

    public override fun asSigned(): Int16x8x2T = transmuteWithLayouts<Uint16x8x2T, Int16x8x2T>(this, Uint16x8x2T.typeLayout(), Int16x8x2T.typeLayout())

    public override fun asUnsigned(): Int16x8x2T = transmuteWithLayouts<Uint16x8x2T, Int16x8x2T>(this, Uint16x8x2T.typeLayout(), Int16x8x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x8x2T> =
            object : TypeLayout<Uint16x8x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint16x8x2T =
                    Uint16x8x2T(
                        (Uint16x8T.typeLayout()).readNative(addr),
                        (Uint16x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint16x8x2T) {
                    (Uint16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x8x2T =
                    Uint16x8x2T(
                        (Uint16x8T.typeLayout()).readBytes(buf, offset),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x8x2T) {
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint16x8T` vectors.
 */
public open class Uint16x8x3T(
    public var field0: Uint16x8T,
    public var field1: Uint16x8T,
    public var field2: Uint16x8T,
) : AsSigned<Int16x8x3T>, AsUnsigned<Int16x8x3T> {
    public open fun clone(): Uint16x8x3T = Uint16x8x3T(field0, field1, field2)

    public override fun asSigned(): Int16x8x3T = transmuteWithLayouts<Uint16x8x3T, Int16x8x3T>(this, Uint16x8x3T.typeLayout(), Int16x8x3T.typeLayout())

    public override fun asUnsigned(): Int16x8x3T = transmuteWithLayouts<Uint16x8x3T, Int16x8x3T>(this, Uint16x8x3T.typeLayout(), Int16x8x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x8x3T> =
            object : TypeLayout<Uint16x8x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Uint16x8x3T =
                    Uint16x8x3T(
                        (Uint16x8T.typeLayout()).readNative(addr),
                        (Uint16x8T.typeLayout()).readNative(addr + 16L),
                        (Uint16x8T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Uint16x8x3T) {
                    (Uint16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x8x3T =
                    Uint16x8x3T(
                        (Uint16x8T.typeLayout()).readBytes(buf, offset),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x8x3T) {
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint16x8T` vectors.
 */
public open class Uint16x8x4T(
    public var field0: Uint16x8T,
    public var field1: Uint16x8T,
    public var field2: Uint16x8T,
    public var field3: Uint16x8T,
) : AsSigned<Int16x8x4T>, AsUnsigned<Int16x8x4T> {
    public open fun clone(): Uint16x8x4T = Uint16x8x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int16x8x4T = transmuteWithLayouts<Uint16x8x4T, Int16x8x4T>(this, Uint16x8x4T.typeLayout(), Int16x8x4T.typeLayout())

    public override fun asUnsigned(): Int16x8x4T = transmuteWithLayouts<Uint16x8x4T, Int16x8x4T>(this, Uint16x8x4T.typeLayout(), Int16x8x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint16x8x4T> =
            object : TypeLayout<Uint16x8x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Uint16x8x4T =
                    Uint16x8x4T(
                        (Uint16x8T.typeLayout()).readNative(addr),
                        (Uint16x8T.typeLayout()).readNative(addr + 16L),
                        (Uint16x8T.typeLayout()).readNative(addr + 32L),
                        (Uint16x8T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Uint16x8x4T) {
                    (Uint16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Uint16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Uint16x8T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint16x8x4T =
                    Uint16x8x4T(
                        (Uint16x8T.typeLayout()).readBytes(buf, offset),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 32),
                        (Uint16x8T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint16x8x4T) {
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Uint16x8T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly16x4T` vectors.
 */
public open class Poly16x4x2T(
    public var field0: Poly16x4T,
    public var field1: Poly16x4T,
) {
    public open fun clone(): Poly16x4x2T = Poly16x4x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x4x2T> =
            object : TypeLayout<Poly16x4x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly16x4x2T =
                    Poly16x4x2T(
                        (Poly16x4T.typeLayout()).readNative(addr),
                        (Poly16x4T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Poly16x4x2T) {
                    (Poly16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x4x2T =
                    Poly16x4x2T(
                        (Poly16x4T.typeLayout()).readBytes(buf, offset),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x4x2T) {
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly16x4T` vectors.
 */
public open class Poly16x4x3T(
    public var field0: Poly16x4T,
    public var field1: Poly16x4T,
    public var field2: Poly16x4T,
) {
    public open fun clone(): Poly16x4x3T = Poly16x4x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x4x3T> =
            object : TypeLayout<Poly16x4x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Poly16x4x3T =
                    Poly16x4x3T(
                        (Poly16x4T.typeLayout()).readNative(addr),
                        (Poly16x4T.typeLayout()).readNative(addr + 8L),
                        (Poly16x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly16x4x3T) {
                    (Poly16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x4x3T =
                    Poly16x4x3T(
                        (Poly16x4T.typeLayout()).readBytes(buf, offset),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x4x3T) {
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly16x4T` vectors.
 */
public open class Poly16x4x4T(
    public var field0: Poly16x4T,
    public var field1: Poly16x4T,
    public var field2: Poly16x4T,
    public var field3: Poly16x4T,
) {
    public open fun clone(): Poly16x4x4T = Poly16x4x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x4x4T> =
            object : TypeLayout<Poly16x4x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly16x4x4T =
                    Poly16x4x4T(
                        (Poly16x4T.typeLayout()).readNative(addr),
                        (Poly16x4T.typeLayout()).readNative(addr + 8L),
                        (Poly16x4T.typeLayout()).readNative(addr + 16L),
                        (Poly16x4T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Poly16x4x4T) {
                    (Poly16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Poly16x4T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x4x4T =
                    Poly16x4x4T(
                        (Poly16x4T.typeLayout()).readBytes(buf, offset),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly16x4T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x4x4T) {
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Poly16x4T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly16x8T` vectors.
 */
public open class Poly16x8x2T(
    public var field0: Poly16x8T,
    public var field1: Poly16x8T,
) {
    public open fun clone(): Poly16x8x2T = Poly16x8x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x8x2T> =
            object : TypeLayout<Poly16x8x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly16x8x2T =
                    Poly16x8x2T(
                        (Poly16x8T.typeLayout()).readNative(addr),
                        (Poly16x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly16x8x2T) {
                    (Poly16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x8x2T =
                    Poly16x8x2T(
                        (Poly16x8T.typeLayout()).readBytes(buf, offset),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x8x2T) {
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly16x8T` vectors.
 */
public open class Poly16x8x3T(
    public var field0: Poly16x8T,
    public var field1: Poly16x8T,
    public var field2: Poly16x8T,
) {
    public open fun clone(): Poly16x8x3T = Poly16x8x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x8x3T> =
            object : TypeLayout<Poly16x8x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Poly16x8x3T =
                    Poly16x8x3T(
                        (Poly16x8T.typeLayout()).readNative(addr),
                        (Poly16x8T.typeLayout()).readNative(addr + 16L),
                        (Poly16x8T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Poly16x8x3T) {
                    (Poly16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x8x3T =
                    Poly16x8x3T(
                        (Poly16x8T.typeLayout()).readBytes(buf, offset),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x8x3T) {
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly16x8T` vectors.
 */
public open class Poly16x8x4T(
    public var field0: Poly16x8T,
    public var field1: Poly16x8T,
    public var field2: Poly16x8T,
    public var field3: Poly16x8T,
) {
    public open fun clone(): Poly16x8x4T = Poly16x8x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly16x8x4T> =
            object : TypeLayout<Poly16x8x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Poly16x8x4T =
                    Poly16x8x4T(
                        (Poly16x8T.typeLayout()).readNative(addr),
                        (Poly16x8T.typeLayout()).readNative(addr + 16L),
                        (Poly16x8T.typeLayout()).readNative(addr + 32L),
                        (Poly16x8T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Poly16x8x4T) {
                    (Poly16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Poly16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Poly16x8T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly16x8x4T =
                    Poly16x8x4T(
                        (Poly16x8T.typeLayout()).readBytes(buf, offset),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 32),
                        (Poly16x8T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly16x8x4T) {
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Poly16x8T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int32x2T` vectors.
 */
public open class Int32x2x2T(
    public var field0: Int32x2T,
    public var field1: Int32x2T,
) : AsUnsigned<Uint32x2x2T>, AsSigned<Uint32x2x2T> {
    public open fun clone(): Int32x2x2T = Int32x2x2T(field0, field1)

    public override fun asUnsigned(): Uint32x2x2T = transmuteWithLayouts<Int32x2x2T, Uint32x2x2T>(this, Int32x2x2T.typeLayout(), Uint32x2x2T.typeLayout())

    public override fun asSigned(): Uint32x2x2T = transmuteWithLayouts<Int32x2x2T, Uint32x2x2T>(this, Int32x2x2T.typeLayout(), Uint32x2x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x2x2T> =
            object : TypeLayout<Int32x2x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int32x2x2T =
                    Int32x2x2T(
                        (Int32x2T.typeLayout()).readNative(addr),
                        (Int32x2T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Int32x2x2T) {
                    (Int32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x2x2T =
                    Int32x2x2T(
                        (Int32x2T.typeLayout()).readBytes(buf, offset),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x2x2T) {
                    (Int32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int32x2T` vectors.
 */
public open class Int32x2x3T(
    public var field0: Int32x2T,
    public var field1: Int32x2T,
    public var field2: Int32x2T,
) : AsUnsigned<Uint32x2x3T>, AsSigned<Uint32x2x3T> {
    public open fun clone(): Int32x2x3T = Int32x2x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint32x2x3T = transmuteWithLayouts<Int32x2x3T, Uint32x2x3T>(this, Int32x2x3T.typeLayout(), Uint32x2x3T.typeLayout())

    public override fun asSigned(): Uint32x2x3T = transmuteWithLayouts<Int32x2x3T, Uint32x2x3T>(this, Int32x2x3T.typeLayout(), Uint32x2x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x2x3T> =
            object : TypeLayout<Int32x2x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Int32x2x3T =
                    Int32x2x3T(
                        (Int32x2T.typeLayout()).readNative(addr),
                        (Int32x2T.typeLayout()).readNative(addr + 8L),
                        (Int32x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int32x2x3T) {
                    (Int32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x2x3T =
                    Int32x2x3T(
                        (Int32x2T.typeLayout()).readBytes(buf, offset),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x2x3T) {
                    (Int32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int32x2T` vectors.
 */
public open class Int32x2x4T(
    public var field0: Int32x2T,
    public var field1: Int32x2T,
    public var field2: Int32x2T,
    public var field3: Int32x2T,
) : AsUnsigned<Uint32x2x4T>, AsSigned<Uint32x2x4T> {
    public open fun clone(): Int32x2x4T = Int32x2x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint32x2x4T = transmuteWithLayouts<Int32x2x4T, Uint32x2x4T>(this, Int32x2x4T.typeLayout(), Uint32x2x4T.typeLayout())

    public override fun asSigned(): Uint32x2x4T = transmuteWithLayouts<Int32x2x4T, Uint32x2x4T>(this, Int32x2x4T.typeLayout(), Uint32x2x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x2x4T> =
            object : TypeLayout<Int32x2x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int32x2x4T =
                    Int32x2x4T(
                        (Int32x2T.typeLayout()).readNative(addr),
                        (Int32x2T.typeLayout()).readNative(addr + 8L),
                        (Int32x2T.typeLayout()).readNative(addr + 16L),
                        (Int32x2T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Int32x2x4T) {
                    (Int32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Int32x2T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x2x4T =
                    Int32x2x4T(
                        (Int32x2T.typeLayout()).readBytes(buf, offset),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Int32x2T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x2x4T) {
                    (Int32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Int32x2T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int32x4T` vectors.
 */
public open class Int32x4x2T(
    public var field0: Int32x4T,
    public var field1: Int32x4T,
) : AsUnsigned<Uint32x4x2T>, AsSigned<Uint32x4x2T> {
    public open fun clone(): Int32x4x2T = Int32x4x2T(field0, field1)

    public override fun asUnsigned(): Uint32x4x2T = transmuteWithLayouts<Int32x4x2T, Uint32x4x2T>(this, Int32x4x2T.typeLayout(), Uint32x4x2T.typeLayout())

    public override fun asSigned(): Uint32x4x2T = transmuteWithLayouts<Int32x4x2T, Uint32x4x2T>(this, Int32x4x2T.typeLayout(), Uint32x4x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x4x2T> =
            object : TypeLayout<Int32x4x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int32x4x2T =
                    Int32x4x2T(
                        (Int32x4T.typeLayout()).readNative(addr),
                        (Int32x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int32x4x2T) {
                    (Int32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x4x2T =
                    Int32x4x2T(
                        (Int32x4T.typeLayout()).readBytes(buf, offset),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x4x2T) {
                    (Int32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int32x4T` vectors.
 */
public open class Int32x4x3T(
    public var field0: Int32x4T,
    public var field1: Int32x4T,
    public var field2: Int32x4T,
) : AsUnsigned<Uint32x4x3T>, AsSigned<Uint32x4x3T> {
    public open fun clone(): Int32x4x3T = Int32x4x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint32x4x3T = transmuteWithLayouts<Int32x4x3T, Uint32x4x3T>(this, Int32x4x3T.typeLayout(), Uint32x4x3T.typeLayout())

    public override fun asSigned(): Uint32x4x3T = transmuteWithLayouts<Int32x4x3T, Uint32x4x3T>(this, Int32x4x3T.typeLayout(), Uint32x4x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x4x3T> =
            object : TypeLayout<Int32x4x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Int32x4x3T =
                    Int32x4x3T(
                        (Int32x4T.typeLayout()).readNative(addr),
                        (Int32x4T.typeLayout()).readNative(addr + 16L),
                        (Int32x4T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Int32x4x3T) {
                    (Int32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x4x3T =
                    Int32x4x3T(
                        (Int32x4T.typeLayout()).readBytes(buf, offset),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x4x3T) {
                    (Int32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int32x4T` vectors.
 */
public open class Int32x4x4T(
    public var field0: Int32x4T,
    public var field1: Int32x4T,
    public var field2: Int32x4T,
    public var field3: Int32x4T,
) : AsUnsigned<Uint32x4x4T>, AsSigned<Uint32x4x4T> {
    public open fun clone(): Int32x4x4T = Int32x4x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint32x4x4T = transmuteWithLayouts<Int32x4x4T, Uint32x4x4T>(this, Int32x4x4T.typeLayout(), Uint32x4x4T.typeLayout())

    public override fun asSigned(): Uint32x4x4T = transmuteWithLayouts<Int32x4x4T, Uint32x4x4T>(this, Int32x4x4T.typeLayout(), Uint32x4x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int32x4x4T> =
            object : TypeLayout<Int32x4x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Int32x4x4T =
                    Int32x4x4T(
                        (Int32x4T.typeLayout()).readNative(addr),
                        (Int32x4T.typeLayout()).readNative(addr + 16L),
                        (Int32x4T.typeLayout()).readNative(addr + 32L),
                        (Int32x4T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Int32x4x4T) {
                    (Int32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Int32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Int32x4T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int32x4x4T =
                    Int32x4x4T(
                        (Int32x4T.typeLayout()).readBytes(buf, offset),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 32),
                        (Int32x4T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int32x4x4T) {
                    (Int32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Int32x4T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint32x2T` vectors.
 */
public open class Uint32x2x2T(
    public var field0: Uint32x2T,
    public var field1: Uint32x2T,
) : AsSigned<Int32x2x2T>, AsUnsigned<Int32x2x2T> {
    public open fun clone(): Uint32x2x2T = Uint32x2x2T(field0, field1)

    public override fun asSigned(): Int32x2x2T = transmuteWithLayouts<Uint32x2x2T, Int32x2x2T>(this, Uint32x2x2T.typeLayout(), Int32x2x2T.typeLayout())

    public override fun asUnsigned(): Int32x2x2T = transmuteWithLayouts<Uint32x2x2T, Int32x2x2T>(this, Uint32x2x2T.typeLayout(), Int32x2x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x2x2T> =
            object : TypeLayout<Uint32x2x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint32x2x2T =
                    Uint32x2x2T(
                        (Uint32x2T.typeLayout()).readNative(addr),
                        (Uint32x2T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Uint32x2x2T) {
                    (Uint32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x2x2T =
                    Uint32x2x2T(
                        (Uint32x2T.typeLayout()).readBytes(buf, offset),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x2x2T) {
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint32x2T` vectors.
 */
public open class Uint32x2x3T(
    public var field0: Uint32x2T,
    public var field1: Uint32x2T,
    public var field2: Uint32x2T,
) : AsSigned<Int32x2x3T>, AsUnsigned<Int32x2x3T> {
    public open fun clone(): Uint32x2x3T = Uint32x2x3T(field0, field1, field2)

    public override fun asSigned(): Int32x2x3T = transmuteWithLayouts<Uint32x2x3T, Int32x2x3T>(this, Uint32x2x3T.typeLayout(), Int32x2x3T.typeLayout())

    public override fun asUnsigned(): Int32x2x3T = transmuteWithLayouts<Uint32x2x3T, Int32x2x3T>(this, Uint32x2x3T.typeLayout(), Int32x2x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x2x3T> =
            object : TypeLayout<Uint32x2x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Uint32x2x3T =
                    Uint32x2x3T(
                        (Uint32x2T.typeLayout()).readNative(addr),
                        (Uint32x2T.typeLayout()).readNative(addr + 8L),
                        (Uint32x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint32x2x3T) {
                    (Uint32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x2x3T =
                    Uint32x2x3T(
                        (Uint32x2T.typeLayout()).readBytes(buf, offset),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x2x3T) {
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint32x2T` vectors.
 */
public open class Uint32x2x4T(
    public var field0: Uint32x2T,
    public var field1: Uint32x2T,
    public var field2: Uint32x2T,
    public var field3: Uint32x2T,
) : AsSigned<Int32x2x4T>, AsUnsigned<Int32x2x4T> {
    public open fun clone(): Uint32x2x4T = Uint32x2x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int32x2x4T = transmuteWithLayouts<Uint32x2x4T, Int32x2x4T>(this, Uint32x2x4T.typeLayout(), Int32x2x4T.typeLayout())

    public override fun asUnsigned(): Int32x2x4T = transmuteWithLayouts<Uint32x2x4T, Int32x2x4T>(this, Uint32x2x4T.typeLayout(), Int32x2x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x2x4T> =
            object : TypeLayout<Uint32x2x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint32x2x4T =
                    Uint32x2x4T(
                        (Uint32x2T.typeLayout()).readNative(addr),
                        (Uint32x2T.typeLayout()).readNative(addr + 8L),
                        (Uint32x2T.typeLayout()).readNative(addr + 16L),
                        (Uint32x2T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Uint32x2x4T) {
                    (Uint32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Uint32x2T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x2x4T =
                    Uint32x2x4T(
                        (Uint32x2T.typeLayout()).readBytes(buf, offset),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint32x2T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x2x4T) {
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Uint32x2T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint32x4T` vectors.
 */
public open class Uint32x4x2T(
    public var field0: Uint32x4T,
    public var field1: Uint32x4T,
) : AsSigned<Int32x4x2T>, AsUnsigned<Int32x4x2T> {
    public open fun clone(): Uint32x4x2T = Uint32x4x2T(field0, field1)

    public override fun asSigned(): Int32x4x2T = transmuteWithLayouts<Uint32x4x2T, Int32x4x2T>(this, Uint32x4x2T.typeLayout(), Int32x4x2T.typeLayout())

    public override fun asUnsigned(): Int32x4x2T = transmuteWithLayouts<Uint32x4x2T, Int32x4x2T>(this, Uint32x4x2T.typeLayout(), Int32x4x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x4x2T> =
            object : TypeLayout<Uint32x4x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint32x4x2T =
                    Uint32x4x2T(
                        (Uint32x4T.typeLayout()).readNative(addr),
                        (Uint32x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint32x4x2T) {
                    (Uint32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x4x2T =
                    Uint32x4x2T(
                        (Uint32x4T.typeLayout()).readBytes(buf, offset),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x4x2T) {
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint32x4T` vectors.
 */
public open class Uint32x4x3T(
    public var field0: Uint32x4T,
    public var field1: Uint32x4T,
    public var field2: Uint32x4T,
) : AsSigned<Int32x4x3T>, AsUnsigned<Int32x4x3T> {
    public open fun clone(): Uint32x4x3T = Uint32x4x3T(field0, field1, field2)

    public override fun asSigned(): Int32x4x3T = transmuteWithLayouts<Uint32x4x3T, Int32x4x3T>(this, Uint32x4x3T.typeLayout(), Int32x4x3T.typeLayout())

    public override fun asUnsigned(): Int32x4x3T = transmuteWithLayouts<Uint32x4x3T, Int32x4x3T>(this, Uint32x4x3T.typeLayout(), Int32x4x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x4x3T> =
            object : TypeLayout<Uint32x4x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Uint32x4x3T =
                    Uint32x4x3T(
                        (Uint32x4T.typeLayout()).readNative(addr),
                        (Uint32x4T.typeLayout()).readNative(addr + 16L),
                        (Uint32x4T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Uint32x4x3T) {
                    (Uint32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x4x3T =
                    Uint32x4x3T(
                        (Uint32x4T.typeLayout()).readBytes(buf, offset),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x4x3T) {
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint32x4T` vectors.
 */
public open class Uint32x4x4T(
    public var field0: Uint32x4T,
    public var field1: Uint32x4T,
    public var field2: Uint32x4T,
    public var field3: Uint32x4T,
) : AsSigned<Int32x4x4T>, AsUnsigned<Int32x4x4T> {
    public open fun clone(): Uint32x4x4T = Uint32x4x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int32x4x4T = transmuteWithLayouts<Uint32x4x4T, Int32x4x4T>(this, Uint32x4x4T.typeLayout(), Int32x4x4T.typeLayout())

    public override fun asUnsigned(): Int32x4x4T = transmuteWithLayouts<Uint32x4x4T, Int32x4x4T>(this, Uint32x4x4T.typeLayout(), Int32x4x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint32x4x4T> =
            object : TypeLayout<Uint32x4x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Uint32x4x4T =
                    Uint32x4x4T(
                        (Uint32x4T.typeLayout()).readNative(addr),
                        (Uint32x4T.typeLayout()).readNative(addr + 16L),
                        (Uint32x4T.typeLayout()).readNative(addr + 32L),
                        (Uint32x4T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Uint32x4x4T) {
                    (Uint32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Uint32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Uint32x4T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint32x4x4T =
                    Uint32x4x4T(
                        (Uint32x4T.typeLayout()).readBytes(buf, offset),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 32),
                        (Uint32x4T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint32x4x4T) {
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Uint32x4T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `float16x4T` vectors.
 */
public open class Float16x4x2T(
    public var field0: Float16x4T,
    public var field1: Float16x4T,
) {
    public open fun clone(): Float16x4x2T = Float16x4x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x4x2T> =
            object : TypeLayout<Float16x4x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float16x4x2T =
                    Float16x4x2T(
                        (Float16x4T.typeLayout()).readNative(addr),
                        (Float16x4T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Float16x4x2T) {
                    (Float16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x4x2T =
                    Float16x4x2T(
                        (Float16x4T.typeLayout()).readBytes(buf, offset),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x4x2T) {
                    (Float16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `float16x4T` vectors.
 */
public open class Float16x4x3T(
    public var field0: Float16x4T,
    public var field1: Float16x4T,
    public var field2: Float16x4T,
) {
    public open fun clone(): Float16x4x3T = Float16x4x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x4x3T> =
            object : TypeLayout<Float16x4x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Float16x4x3T =
                    Float16x4x3T(
                        (Float16x4T.typeLayout()).readNative(addr),
                        (Float16x4T.typeLayout()).readNative(addr + 8L),
                        (Float16x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float16x4x3T) {
                    (Float16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x4x3T =
                    Float16x4x3T(
                        (Float16x4T.typeLayout()).readBytes(buf, offset),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x4x3T) {
                    (Float16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `float16x4T` vectors.
 */
public open class Float16x4x4T(
    public var field0: Float16x4T,
    public var field1: Float16x4T,
    public var field2: Float16x4T,
    public var field3: Float16x4T,
) {
    public open fun clone(): Float16x4x4T = Float16x4x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x4x4T> =
            object : TypeLayout<Float16x4x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float16x4x4T =
                    Float16x4x4T(
                        (Float16x4T.typeLayout()).readNative(addr),
                        (Float16x4T.typeLayout()).readNative(addr + 8L),
                        (Float16x4T.typeLayout()).readNative(addr + 16L),
                        (Float16x4T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Float16x4x4T) {
                    (Float16x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x4T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float16x4T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Float16x4T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x4x4T =
                    Float16x4x4T(
                        (Float16x4T.typeLayout()).readBytes(buf, offset),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 8),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Float16x4T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x4x4T) {
                    (Float16x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Float16x4T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `float16x8T` vectors.
 */
public open class Float16x8x2T(
    public var field0: Float16x8T,
    public var field1: Float16x8T,
) {
    public open fun clone(): Float16x8x2T = Float16x8x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x8x2T> =
            object : TypeLayout<Float16x8x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float16x8x2T =
                    Float16x8x2T(
                        (Float16x8T.typeLayout()).readNative(addr),
                        (Float16x8T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float16x8x2T) {
                    (Float16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x8x2T =
                    Float16x8x2T(
                        (Float16x8T.typeLayout()).readBytes(buf, offset),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x8x2T) {
                    (Float16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `float16x8T` vectors.
 */
public open class Float16x8x3T(
    public var field0: Float16x8T,
    public var field1: Float16x8T,
    public var field2: Float16x8T,
) {
    public open fun clone(): Float16x8x3T = Float16x8x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x8x3T> =
            object : TypeLayout<Float16x8x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Float16x8x3T =
                    Float16x8x3T(
                        (Float16x8T.typeLayout()).readNative(addr),
                        (Float16x8T.typeLayout()).readNative(addr + 16L),
                        (Float16x8T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Float16x8x3T) {
                    (Float16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x8x3T =
                    Float16x8x3T(
                        (Float16x8T.typeLayout()).readBytes(buf, offset),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x8x3T) {
                    (Float16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `float16x8T` vectors.
 */
public open class Float16x8x4T(
    public var field0: Float16x8T,
    public var field1: Float16x8T,
    public var field2: Float16x8T,
    public var field3: Float16x8T,
) {
    public open fun clone(): Float16x8x4T = Float16x8x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float16x8x4T> =
            object : TypeLayout<Float16x8x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Float16x8x4T =
                    Float16x8x4T(
                        (Float16x8T.typeLayout()).readNative(addr),
                        (Float16x8T.typeLayout()).readNative(addr + 16L),
                        (Float16x8T.typeLayout()).readNative(addr + 32L),
                        (Float16x8T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Float16x8x4T) {
                    (Float16x8T.typeLayout()).writeNative(addr, value.field0)
                    (Float16x8T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float16x8T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Float16x8T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float16x8x4T =
                    Float16x8x4T(
                        (Float16x8T.typeLayout()).readBytes(buf, offset),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 16),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 32),
                        (Float16x8T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float16x8x4T) {
                    (Float16x8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Float16x8T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `float32x2T` vectors.
 */
public open class Float32x2x2T(
    public var field0: Float32x2T,
    public var field1: Float32x2T,
) {
    public open fun clone(): Float32x2x2T = Float32x2x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x2x2T> =
            object : TypeLayout<Float32x2x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float32x2x2T =
                    Float32x2x2T(
                        (Float32x2T.typeLayout()).readNative(addr),
                        (Float32x2T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Float32x2x2T) {
                    (Float32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x2x2T =
                    Float32x2x2T(
                        (Float32x2T.typeLayout()).readBytes(buf, offset),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x2x2T) {
                    (Float32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `float32x2T` vectors.
 */
public open class Float32x2x3T(
    public var field0: Float32x2T,
    public var field1: Float32x2T,
    public var field2: Float32x2T,
) {
    public open fun clone(): Float32x2x3T = Float32x2x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x2x3T> =
            object : TypeLayout<Float32x2x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Float32x2x3T =
                    Float32x2x3T(
                        (Float32x2T.typeLayout()).readNative(addr),
                        (Float32x2T.typeLayout()).readNative(addr + 8L),
                        (Float32x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float32x2x3T) {
                    (Float32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x2x3T =
                    Float32x2x3T(
                        (Float32x2T.typeLayout()).readBytes(buf, offset),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x2x3T) {
                    (Float32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `float32x2T` vectors.
 */
public open class Float32x2x4T(
    public var field0: Float32x2T,
    public var field1: Float32x2T,
    public var field2: Float32x2T,
    public var field3: Float32x2T,
) {
    public open fun clone(): Float32x2x4T = Float32x2x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x2x4T> =
            object : TypeLayout<Float32x2x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float32x2x4T =
                    Float32x2x4T(
                        (Float32x2T.typeLayout()).readNative(addr),
                        (Float32x2T.typeLayout()).readNative(addr + 8L),
                        (Float32x2T.typeLayout()).readNative(addr + 16L),
                        (Float32x2T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Float32x2x4T) {
                    (Float32x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x2T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float32x2T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Float32x2T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x2x4T =
                    Float32x2x4T(
                        (Float32x2T.typeLayout()).readBytes(buf, offset),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 8),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Float32x2T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x2x4T) {
                    (Float32x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Float32x2T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `float32x4T` vectors.
 */
public open class Float32x4x2T(
    public var field0: Float32x4T,
    public var field1: Float32x4T,
) {
    public open fun clone(): Float32x4x2T = Float32x4x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x4x2T> =
            object : TypeLayout<Float32x4x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float32x4x2T =
                    Float32x4x2T(
                        (Float32x4T.typeLayout()).readNative(addr),
                        (Float32x4T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float32x4x2T) {
                    (Float32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x4x2T =
                    Float32x4x2T(
                        (Float32x4T.typeLayout()).readBytes(buf, offset),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x4x2T) {
                    (Float32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `float32x4T` vectors.
 */
public open class Float32x4x3T(
    public var field0: Float32x4T,
    public var field1: Float32x4T,
    public var field2: Float32x4T,
) {
    public open fun clone(): Float32x4x3T = Float32x4x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x4x3T> =
            object : TypeLayout<Float32x4x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Float32x4x3T =
                    Float32x4x3T(
                        (Float32x4T.typeLayout()).readNative(addr),
                        (Float32x4T.typeLayout()).readNative(addr + 16L),
                        (Float32x4T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Float32x4x3T) {
                    (Float32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x4x3T =
                    Float32x4x3T(
                        (Float32x4T.typeLayout()).readBytes(buf, offset),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x4x3T) {
                    (Float32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `float32x4T` vectors.
 */
public open class Float32x4x4T(
    public var field0: Float32x4T,
    public var field1: Float32x4T,
    public var field2: Float32x4T,
    public var field3: Float32x4T,
) {
    public open fun clone(): Float32x4x4T = Float32x4x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float32x4x4T> =
            object : TypeLayout<Float32x4x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Float32x4x4T =
                    Float32x4x4T(
                        (Float32x4T.typeLayout()).readNative(addr),
                        (Float32x4T.typeLayout()).readNative(addr + 16L),
                        (Float32x4T.typeLayout()).readNative(addr + 32L),
                        (Float32x4T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Float32x4x4T) {
                    (Float32x4T.typeLayout()).writeNative(addr, value.field0)
                    (Float32x4T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float32x4T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Float32x4T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float32x4x4T =
                    Float32x4x4T(
                        (Float32x4T.typeLayout()).readBytes(buf, offset),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 16),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 32),
                        (Float32x4T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float32x4x4T) {
                    (Float32x4T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Float32x4T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int64x1T` vectors.
 */
public open class Int64x1x2T(
    public var field0: Int64x1T,
    public var field1: Int64x1T,
) : AsUnsigned<Uint64x1x2T>, AsSigned<Uint64x1x2T> {
    public open fun clone(): Int64x1x2T = Int64x1x2T(field0, field1)

    public override fun asUnsigned(): Uint64x1x2T = transmuteWithLayouts<Int64x1x2T, Uint64x1x2T>(this, Int64x1x2T.typeLayout(), Uint64x1x2T.typeLayout())

    public override fun asSigned(): Uint64x1x2T = transmuteWithLayouts<Int64x1x2T, Uint64x1x2T>(this, Int64x1x2T.typeLayout(), Uint64x1x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x1x2T> =
            object : TypeLayout<Int64x1x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Int64x1x2T =
                    Int64x1x2T(
                        (Int64x1T.typeLayout()).readNative(addr),
                        (Int64x1T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Int64x1x2T) {
                    (Int64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x1x2T =
                    Int64x1x2T(
                        (Int64x1T.typeLayout()).readBytes(buf, offset),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x1x2T) {
                    (Int64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int64x1T` vectors.
 */
public open class Int64x1x3T(
    public var field0: Int64x1T,
    public var field1: Int64x1T,
    public var field2: Int64x1T,
) : AsUnsigned<Uint64x1x3T>, AsSigned<Uint64x1x3T> {
    public open fun clone(): Int64x1x3T = Int64x1x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint64x1x3T = transmuteWithLayouts<Int64x1x3T, Uint64x1x3T>(this, Int64x1x3T.typeLayout(), Uint64x1x3T.typeLayout())

    public override fun asSigned(): Uint64x1x3T = transmuteWithLayouts<Int64x1x3T, Uint64x1x3T>(this, Int64x1x3T.typeLayout(), Uint64x1x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x1x3T> =
            object : TypeLayout<Int64x1x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Int64x1x3T =
                    Int64x1x3T(
                        (Int64x1T.typeLayout()).readNative(addr),
                        (Int64x1T.typeLayout()).readNative(addr + 8L),
                        (Int64x1T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int64x1x3T) {
                    (Int64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x1x3T =
                    Int64x1x3T(
                        (Int64x1T.typeLayout()).readBytes(buf, offset),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x1x3T) {
                    (Int64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int64x1T` vectors.
 */
public open class Int64x1x4T(
    public var field0: Int64x1T,
    public var field1: Int64x1T,
    public var field2: Int64x1T,
    public var field3: Int64x1T,
) : AsUnsigned<Uint64x1x4T>, AsSigned<Uint64x1x4T> {
    public open fun clone(): Int64x1x4T = Int64x1x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint64x1x4T = transmuteWithLayouts<Int64x1x4T, Uint64x1x4T>(this, Int64x1x4T.typeLayout(), Uint64x1x4T.typeLayout())

    public override fun asSigned(): Uint64x1x4T = transmuteWithLayouts<Int64x1x4T, Uint64x1x4T>(this, Int64x1x4T.typeLayout(), Uint64x1x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x1x4T> =
            object : TypeLayout<Int64x1x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int64x1x4T =
                    Int64x1x4T(
                        (Int64x1T.typeLayout()).readNative(addr),
                        (Int64x1T.typeLayout()).readNative(addr + 8L),
                        (Int64x1T.typeLayout()).readNative(addr + 16L),
                        (Int64x1T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Int64x1x4T) {
                    (Int64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Int64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Int64x1T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x1x4T =
                    Int64x1x4T(
                        (Int64x1T.typeLayout()).readBytes(buf, offset),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 16),
                        (Int64x1T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x1x4T) {
                    (Int64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Int64x1T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `int64x2T` vectors.
 */
public open class Int64x2x2T(
    public var field0: Int64x2T,
    public var field1: Int64x2T,
) : AsUnsigned<Uint64x2x2T>, AsSigned<Uint64x2x2T> {
    public open fun clone(): Int64x2x2T = Int64x2x2T(field0, field1)

    public override fun asUnsigned(): Uint64x2x2T = transmuteWithLayouts<Int64x2x2T, Uint64x2x2T>(this, Int64x2x2T.typeLayout(), Uint64x2x2T.typeLayout())

    public override fun asSigned(): Uint64x2x2T = transmuteWithLayouts<Int64x2x2T, Uint64x2x2T>(this, Int64x2x2T.typeLayout(), Uint64x2x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x2x2T> =
            object : TypeLayout<Int64x2x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Int64x2x2T =
                    Int64x2x2T(
                        (Int64x2T.typeLayout()).readNative(addr),
                        (Int64x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Int64x2x2T) {
                    (Int64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x2x2T =
                    Int64x2x2T(
                        (Int64x2T.typeLayout()).readBytes(buf, offset),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x2x2T) {
                    (Int64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `int64x2T` vectors.
 */
public open class Int64x2x3T(
    public var field0: Int64x2T,
    public var field1: Int64x2T,
    public var field2: Int64x2T,
) : AsUnsigned<Uint64x2x3T>, AsSigned<Uint64x2x3T> {
    public open fun clone(): Int64x2x3T = Int64x2x3T(field0, field1, field2)

    public override fun asUnsigned(): Uint64x2x3T = transmuteWithLayouts<Int64x2x3T, Uint64x2x3T>(this, Int64x2x3T.typeLayout(), Uint64x2x3T.typeLayout())

    public override fun asSigned(): Uint64x2x3T = transmuteWithLayouts<Int64x2x3T, Uint64x2x3T>(this, Int64x2x3T.typeLayout(), Uint64x2x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x2x3T> =
            object : TypeLayout<Int64x2x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Int64x2x3T =
                    Int64x2x3T(
                        (Int64x2T.typeLayout()).readNative(addr),
                        (Int64x2T.typeLayout()).readNative(addr + 16L),
                        (Int64x2T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Int64x2x3T) {
                    (Int64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x2x3T =
                    Int64x2x3T(
                        (Int64x2T.typeLayout()).readBytes(buf, offset),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x2x3T) {
                    (Int64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `int64x2T` vectors.
 */
public open class Int64x2x4T(
    public var field0: Int64x2T,
    public var field1: Int64x2T,
    public var field2: Int64x2T,
    public var field3: Int64x2T,
) : AsUnsigned<Uint64x2x4T>, AsSigned<Uint64x2x4T> {
    public open fun clone(): Int64x2x4T = Int64x2x4T(field0, field1, field2, field3)

    public override fun asUnsigned(): Uint64x2x4T = transmuteWithLayouts<Int64x2x4T, Uint64x2x4T>(this, Int64x2x4T.typeLayout(), Uint64x2x4T.typeLayout())

    public override fun asSigned(): Uint64x2x4T = transmuteWithLayouts<Int64x2x4T, Uint64x2x4T>(this, Int64x2x4T.typeLayout(), Uint64x2x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Int64x2x4T> =
            object : TypeLayout<Int64x2x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Int64x2x4T =
                    Int64x2x4T(
                        (Int64x2T.typeLayout()).readNative(addr),
                        (Int64x2T.typeLayout()).readNative(addr + 16L),
                        (Int64x2T.typeLayout()).readNative(addr + 32L),
                        (Int64x2T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Int64x2x4T) {
                    (Int64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Int64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Int64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Int64x2T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Int64x2x4T =
                    Int64x2x4T(
                        (Int64x2T.typeLayout()).readBytes(buf, offset),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 32),
                        (Int64x2T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Int64x2x4T) {
                    (Int64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Int64x2T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint64x1T` vectors.
 */
public open class Uint64x1x2T(
    public var field0: Uint64x1T,
    public var field1: Uint64x1T,
) : AsSigned<Int64x1x2T>, AsUnsigned<Int64x1x2T> {
    public open fun clone(): Uint64x1x2T = Uint64x1x2T(field0, field1)

    public override fun asSigned(): Int64x1x2T = transmuteWithLayouts<Uint64x1x2T, Int64x1x2T>(this, Uint64x1x2T.typeLayout(), Int64x1x2T.typeLayout())

    public override fun asUnsigned(): Int64x1x2T = transmuteWithLayouts<Uint64x1x2T, Int64x1x2T>(this, Uint64x1x2T.typeLayout(), Int64x1x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x1x2T> =
            object : TypeLayout<Uint64x1x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Uint64x1x2T =
                    Uint64x1x2T(
                        (Uint64x1T.typeLayout()).readNative(addr),
                        (Uint64x1T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Uint64x1x2T) {
                    (Uint64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x1x2T =
                    Uint64x1x2T(
                        (Uint64x1T.typeLayout()).readBytes(buf, offset),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x1x2T) {
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint64x1T` vectors.
 */
public open class Uint64x1x3T(
    public var field0: Uint64x1T,
    public var field1: Uint64x1T,
    public var field2: Uint64x1T,
) : AsSigned<Int64x1x3T>, AsUnsigned<Int64x1x3T> {
    public open fun clone(): Uint64x1x3T = Uint64x1x3T(field0, field1, field2)

    public override fun asSigned(): Int64x1x3T = transmuteWithLayouts<Uint64x1x3T, Int64x1x3T>(this, Uint64x1x3T.typeLayout(), Int64x1x3T.typeLayout())

    public override fun asUnsigned(): Int64x1x3T = transmuteWithLayouts<Uint64x1x3T, Int64x1x3T>(this, Uint64x1x3T.typeLayout(), Int64x1x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x1x3T> =
            object : TypeLayout<Uint64x1x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Uint64x1x3T =
                    Uint64x1x3T(
                        (Uint64x1T.typeLayout()).readNative(addr),
                        (Uint64x1T.typeLayout()).readNative(addr + 8L),
                        (Uint64x1T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint64x1x3T) {
                    (Uint64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x1x3T =
                    Uint64x1x3T(
                        (Uint64x1T.typeLayout()).readBytes(buf, offset),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x1x3T) {
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint64x1T` vectors.
 */
public open class Uint64x1x4T(
    public var field0: Uint64x1T,
    public var field1: Uint64x1T,
    public var field2: Uint64x1T,
    public var field3: Uint64x1T,
) : AsSigned<Int64x1x4T>, AsUnsigned<Int64x1x4T> {
    public open fun clone(): Uint64x1x4T = Uint64x1x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int64x1x4T = transmuteWithLayouts<Uint64x1x4T, Int64x1x4T>(this, Uint64x1x4T.typeLayout(), Int64x1x4T.typeLayout())

    public override fun asUnsigned(): Int64x1x4T = transmuteWithLayouts<Uint64x1x4T, Int64x1x4T>(this, Uint64x1x4T.typeLayout(), Int64x1x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x1x4T> =
            object : TypeLayout<Uint64x1x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint64x1x4T =
                    Uint64x1x4T(
                        (Uint64x1T.typeLayout()).readNative(addr),
                        (Uint64x1T.typeLayout()).readNative(addr + 8L),
                        (Uint64x1T.typeLayout()).readNative(addr + 16L),
                        (Uint64x1T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Uint64x1x4T) {
                    (Uint64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Uint64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Uint64x1T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x1x4T =
                    Uint64x1x4T(
                        (Uint64x1T.typeLayout()).readBytes(buf, offset),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint64x1T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x1x4T) {
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Uint64x1T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `uint64x2T` vectors.
 */
public open class Uint64x2x2T(
    public var field0: Uint64x2T,
    public var field1: Uint64x2T,
) : AsSigned<Int64x2x2T>, AsUnsigned<Int64x2x2T> {
    public open fun clone(): Uint64x2x2T = Uint64x2x2T(field0, field1)

    public override fun asSigned(): Int64x2x2T = transmuteWithLayouts<Uint64x2x2T, Int64x2x2T>(this, Uint64x2x2T.typeLayout(), Int64x2x2T.typeLayout())

    public override fun asUnsigned(): Int64x2x2T = transmuteWithLayouts<Uint64x2x2T, Int64x2x2T>(this, Uint64x2x2T.typeLayout(), Int64x2x2T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x2x2T> =
            object : TypeLayout<Uint64x2x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Uint64x2x2T =
                    Uint64x2x2T(
                        (Uint64x2T.typeLayout()).readNative(addr),
                        (Uint64x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Uint64x2x2T) {
                    (Uint64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x2x2T =
                    Uint64x2x2T(
                        (Uint64x2T.typeLayout()).readBytes(buf, offset),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x2x2T) {
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `uint64x2T` vectors.
 */
public open class Uint64x2x3T(
    public var field0: Uint64x2T,
    public var field1: Uint64x2T,
    public var field2: Uint64x2T,
) : AsSigned<Int64x2x3T>, AsUnsigned<Int64x2x3T> {
    public open fun clone(): Uint64x2x3T = Uint64x2x3T(field0, field1, field2)

    public override fun asSigned(): Int64x2x3T = transmuteWithLayouts<Uint64x2x3T, Int64x2x3T>(this, Uint64x2x3T.typeLayout(), Int64x2x3T.typeLayout())

    public override fun asUnsigned(): Int64x2x3T = transmuteWithLayouts<Uint64x2x3T, Int64x2x3T>(this, Uint64x2x3T.typeLayout(), Int64x2x3T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x2x3T> =
            object : TypeLayout<Uint64x2x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Uint64x2x3T =
                    Uint64x2x3T(
                        (Uint64x2T.typeLayout()).readNative(addr),
                        (Uint64x2T.typeLayout()).readNative(addr + 16L),
                        (Uint64x2T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Uint64x2x3T) {
                    (Uint64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x2x3T =
                    Uint64x2x3T(
                        (Uint64x2T.typeLayout()).readBytes(buf, offset),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x2x3T) {
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `uint64x2T` vectors.
 */
public open class Uint64x2x4T(
    public var field0: Uint64x2T,
    public var field1: Uint64x2T,
    public var field2: Uint64x2T,
    public var field3: Uint64x2T,
) : AsSigned<Int64x2x4T>, AsUnsigned<Int64x2x4T> {
    public open fun clone(): Uint64x2x4T = Uint64x2x4T(field0, field1, field2, field3)

    public override fun asSigned(): Int64x2x4T = transmuteWithLayouts<Uint64x2x4T, Int64x2x4T>(this, Uint64x2x4T.typeLayout(), Int64x2x4T.typeLayout())

    public override fun asUnsigned(): Int64x2x4T = transmuteWithLayouts<Uint64x2x4T, Int64x2x4T>(this, Uint64x2x4T.typeLayout(), Int64x2x4T.typeLayout())

    public companion object {
        public fun typeLayout(): TypeLayout<Uint64x2x4T> =
            object : TypeLayout<Uint64x2x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Uint64x2x4T =
                    Uint64x2x4T(
                        (Uint64x2T.typeLayout()).readNative(addr),
                        (Uint64x2T.typeLayout()).readNative(addr + 16L),
                        (Uint64x2T.typeLayout()).readNative(addr + 32L),
                        (Uint64x2T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Uint64x2x4T) {
                    (Uint64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Uint64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Uint64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Uint64x2T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Uint64x2x4T =
                    Uint64x2x4T(
                        (Uint64x2T.typeLayout()).readBytes(buf, offset),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 32),
                        (Uint64x2T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Uint64x2x4T) {
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Uint64x2T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly64x1T` vectors.
 */
public open class Poly64x1x2T(
    public var field0: Poly64x1T,
    public var field1: Poly64x1T,
) {
    public open fun clone(): Poly64x1x2T = Poly64x1x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x1x2T> =
            object : TypeLayout<Poly64x1x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Poly64x1x2T =
                    Poly64x1x2T(
                        (Poly64x1T.typeLayout()).readNative(addr),
                        (Poly64x1T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Poly64x1x2T) {
                    (Poly64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x1x2T =
                    Poly64x1x2T(
                        (Poly64x1T.typeLayout()).readBytes(buf, offset),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x1x2T) {
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly64x1T` vectors.
 */
public open class Poly64x1x3T(
    public var field0: Poly64x1T,
    public var field1: Poly64x1T,
    public var field2: Poly64x1T,
) {
    public open fun clone(): Poly64x1x3T = Poly64x1x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x1x3T> =
            object : TypeLayout<Poly64x1x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Poly64x1x3T =
                    Poly64x1x3T(
                        (Poly64x1T.typeLayout()).readNative(addr),
                        (Poly64x1T.typeLayout()).readNative(addr + 8L),
                        (Poly64x1T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly64x1x3T) {
                    (Poly64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x1x3T =
                    Poly64x1x3T(
                        (Poly64x1T.typeLayout()).readBytes(buf, offset),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x1x3T) {
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly64x1T` vectors.
 */
public open class Poly64x1x4T(
    public var field0: Poly64x1T,
    public var field1: Poly64x1T,
    public var field2: Poly64x1T,
    public var field3: Poly64x1T,
) {
    public open fun clone(): Poly64x1x4T = Poly64x1x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x1x4T> =
            object : TypeLayout<Poly64x1x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly64x1x4T =
                    Poly64x1x4T(
                        (Poly64x1T.typeLayout()).readNative(addr),
                        (Poly64x1T.typeLayout()).readNative(addr + 8L),
                        (Poly64x1T.typeLayout()).readNative(addr + 16L),
                        (Poly64x1T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Poly64x1x4T) {
                    (Poly64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Poly64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Poly64x1T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x1x4T =
                    Poly64x1x4T(
                        (Poly64x1T.typeLayout()).readBytes(buf, offset),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly64x1T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x1x4T) {
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Poly64x1T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * Arm-specific type containing two `poly64x2T` vectors.
 */
public open class Poly64x2x2T(
    public var field0: Poly64x2T,
    public var field1: Poly64x2T,
) {
    public open fun clone(): Poly64x2x2T = Poly64x2x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x2x2T> =
            object : TypeLayout<Poly64x2x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Poly64x2x2T =
                    Poly64x2x2T(
                        (Poly64x2T.typeLayout()).readNative(addr),
                        (Poly64x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Poly64x2x2T) {
                    (Poly64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x2x2T =
                    Poly64x2x2T(
                        (Poly64x2T.typeLayout()).readBytes(buf, offset),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x2x2T) {
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * Arm-specific type containing three `poly64x2T` vectors.
 */
public open class Poly64x2x3T(
    public var field0: Poly64x2T,
    public var field1: Poly64x2T,
    public var field2: Poly64x2T,
) {
    public open fun clone(): Poly64x2x3T = Poly64x2x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x2x3T> =
            object : TypeLayout<Poly64x2x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Poly64x2x3T =
                    Poly64x2x3T(
                        (Poly64x2T.typeLayout()).readNative(addr),
                        (Poly64x2T.typeLayout()).readNative(addr + 16L),
                        (Poly64x2T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Poly64x2x3T) {
                    (Poly64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x2x3T =
                    Poly64x2x3T(
                        (Poly64x2T.typeLayout()).readBytes(buf, offset),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x2x3T) {
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * Arm-specific type containing four `poly64x2T` vectors.
 */
public open class Poly64x2x4T(
    public var field0: Poly64x2T,
    public var field1: Poly64x2T,
    public var field2: Poly64x2T,
    public var field3: Poly64x2T,
) {
    public open fun clone(): Poly64x2x4T = Poly64x2x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Poly64x2x4T> =
            object : TypeLayout<Poly64x2x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Poly64x2x4T =
                    Poly64x2x4T(
                        (Poly64x2T.typeLayout()).readNative(addr),
                        (Poly64x2T.typeLayout()).readNative(addr + 16L),
                        (Poly64x2T.typeLayout()).readNative(addr + 32L),
                        (Poly64x2T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Poly64x2x4T) {
                    (Poly64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Poly64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Poly64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Poly64x2T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Poly64x2x4T =
                    Poly64x2x4T(
                        (Poly64x2T.typeLayout()).readBytes(buf, offset),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 32),
                        (Poly64x2T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Poly64x2x4T) {
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Poly64x2T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

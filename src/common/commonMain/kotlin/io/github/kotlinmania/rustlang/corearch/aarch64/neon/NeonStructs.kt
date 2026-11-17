package io.github.kotlinmania.rustlang.corearch.aarch64.neon

import io.github.kotlinmania.rustlang.core.intrinsics.*
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.debugSimdFinish

/**
 * ARM-specific 64-bit wide vector of one packed `Double`.
 */
public open class Float64x1T(
    public var value: DoubleArray = DoubleArray(1),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float64x1T = Float64x1T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((1).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((1).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float64x1_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x1T> =
            object : TypeLayout<Float64x1T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Float64x1T =
                    Float64x1T(
                        (PrimitiveArrayLayouts.doubleArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float64x1T) {
                    (PrimitiveArrayLayouts.doubleArray(1)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x1T =
                    Float64x1T(
                        (PrimitiveArrayLayouts.doubleArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x1T) {
                    (PrimitiveArrayLayouts.doubleArray(1)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): Float64x1T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): Float64x1T = transmuteWithLayouts<Simd<Double>, Float64x1T>(simd, UnresolvedTypeLayout<Simd<Double>>(), Float64x1T.typeLayout())
    }
}

/**
 * ARM-specific 128-bit wide vector of two packed `Double`.
 */
public open class Float64x2T(
    public var value: DoubleArray = DoubleArray(2),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Float64x2T = Float64x2T(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "float64x2_t", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x2T> =
            object : TypeLayout<Float64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float64x2T =
                    Float64x2T(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Float64x2T) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x2T =
                    Float64x2T(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x2T) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): Float64x2T = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): Float64x2T = transmuteWithLayouts<Simd<Double>, Float64x2T>(simd, UnresolvedTypeLayout<Simd<Double>>(), Float64x2T.typeLayout())
    }
}

/**
 * ARM-specific type containing two `float64x1T` vectors.
 */
public open class Float64x1x2T(
    public var field0: Float64x1T,
    public var field1: Float64x1T,
) {
    public open fun clone(): Float64x1x2T = Float64x1x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x1x2T> =
            object : TypeLayout<Float64x1x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Float64x1x2T =
                    Float64x1x2T(
                        (Float64x1T.typeLayout()).readNative(addr),
                        (Float64x1T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Float64x1x2T) {
                    (Float64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x1x2T =
                    Float64x1x2T(
                        (Float64x1T.typeLayout()).readBytes(buf, offset),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x1x2T) {
                    (Float64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

/**
 * ARM-specific type containing three `float64x1T` vectors.
 */
public open class Float64x1x3T(
    public var field0: Float64x1T,
    public var field1: Float64x1T,
    public var field2: Float64x1T,
) {
    public open fun clone(): Float64x1x3T = Float64x1x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x1x3T> =
            object : TypeLayout<Float64x1x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Float64x1x3T =
                    Float64x1x3T(
                        (Float64x1T.typeLayout()).readNative(addr),
                        (Float64x1T.typeLayout()).readNative(addr + 8L),
                        (Float64x1T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float64x1x3T) {
                    (Float64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x1x3T =
                    Float64x1x3T(
                        (Float64x1T.typeLayout()).readBytes(buf, offset),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x1x3T) {
                    (Float64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

/**
 * ARM-specific type containing four `float64x1T` vectors.
 */
public open class Float64x1x4T(
    public var field0: Float64x1T,
    public var field1: Float64x1T,
    public var field2: Float64x1T,
    public var field3: Float64x1T,
) {
    public open fun clone(): Float64x1x4T = Float64x1x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x1x4T> =
            object : TypeLayout<Float64x1x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float64x1x4T =
                    Float64x1x4T(
                        (Float64x1T.typeLayout()).readNative(addr),
                        (Float64x1T.typeLayout()).readNative(addr + 8L),
                        (Float64x1T.typeLayout()).readNative(addr + 16L),
                        (Float64x1T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Float64x1x4T) {
                    (Float64x1T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x1T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Float64x1T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Float64x1T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x1x4T =
                    Float64x1x4T(
                        (Float64x1T.typeLayout()).readBytes(buf, offset),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 8),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 16),
                        (Float64x1T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x1x4T) {
                    (Float64x1T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Float64x1T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

/**
 * ARM-specific type containing two `float64x2T` vectors.
 */
public open class Float64x2x2T(
    public var field0: Float64x2T,
    public var field1: Float64x2T,
) {
    public open fun clone(): Float64x2x2T = Float64x2x2T(field0, field1)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x2x2T> =
            object : TypeLayout<Float64x2x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Float64x2x2T =
                    Float64x2x2T(
                        (Float64x2T.typeLayout()).readNative(addr),
                        (Float64x2T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Float64x2x2T) {
                    (Float64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x2x2T =
                    Float64x2x2T(
                        (Float64x2T.typeLayout()).readBytes(buf, offset),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x2x2T) {
                    (Float64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                }
            }
    }
}

/**
 * ARM-specific type containing three `float64x2T` vectors.
 */
public open class Float64x2x3T(
    public var field0: Float64x2T,
    public var field1: Float64x2T,
    public var field2: Float64x2T,
) {
    public open fun clone(): Float64x2x3T = Float64x2x3T(field0, field1, field2)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x2x3T> =
            object : TypeLayout<Float64x2x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Float64x2x3T =
                    Float64x2x3T(
                        (Float64x2T.typeLayout()).readNative(addr),
                        (Float64x2T.typeLayout()).readNative(addr + 16L),
                        (Float64x2T.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Float64x2x3T) {
                    (Float64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x2x3T =
                    Float64x2x3T(
                        (Float64x2T.typeLayout()).readBytes(buf, offset),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x2x3T) {
                    (Float64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                }
            }
    }
}

/**
 * ARM-specific type containing four `float64x2T` vectors.
 */
public open class Float64x2x4T(
    public var field0: Float64x2T,
    public var field1: Float64x2T,
    public var field2: Float64x2T,
    public var field3: Float64x2T,
) {
    public open fun clone(): Float64x2x4T = Float64x2x4T(field0, field1, field2, field3)

    public companion object {
        public fun typeLayout(): TypeLayout<Float64x2x4T> =
            object : TypeLayout<Float64x2x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Float64x2x4T =
                    Float64x2x4T(
                        (Float64x2T.typeLayout()).readNative(addr),
                        (Float64x2T.typeLayout()).readNative(addr + 16L),
                        (Float64x2T.typeLayout()).readNative(addr + 32L),
                        (Float64x2T.typeLayout()).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Float64x2x4T) {
                    (Float64x2T.typeLayout()).writeNative(addr, value.field0)
                    (Float64x2T.typeLayout()).writeNative(addr + 16L, value.field1)
                    (Float64x2T.typeLayout()).writeNative(addr + 32L, value.field2)
                    (Float64x2T.typeLayout()).writeNative(addr + 48L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Float64x2x4T =
                    Float64x2x4T(
                        (Float64x2T.typeLayout()).readBytes(buf, offset),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 16),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 32),
                        (Float64x2T.typeLayout()).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Float64x2x4T) {
                    (Float64x2T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 16, value.field1)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 32, value.field2)
                    (Float64x2T.typeLayout()).writeBytes(buf, offset + 48, value.field3)
                }
            }
    }
}

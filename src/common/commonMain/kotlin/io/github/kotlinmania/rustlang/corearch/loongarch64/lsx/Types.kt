// port-lint: source loongarch64/lsx/types.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

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
 * 128-bit wide integer vector type, LoongArch-specific
 *
 * This type is the same as the `__m128i` type defined in `lsxintrin.h`,
 * representing a 128-bit SIMD register. Usage of this type typically
 * occurs in conjunction with the `lsx` and higher target features for
 * LoongArch.
 *
 * Internally this type may be viewed as:
 *
 * * `i8x16` - sixteen `Byte` values packed together
 * * `i16x8` - eight `Short` values packed together
 * * `i32x4` - four `Int` values packed together
 * * `i64x2` - two `Long` values packed together
 *
 * (as well as unsigned versions). Each intrinsic may interpret the
 * internal bits differently, check the documentation of the intrinsic
 * to see how it's being used.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Note that this means that an instance of `m128i` typically just means
 * a "bag of bits" which is left up to interpretation at the point of use.
 *
 * Most intrinsics using `m128i` are prefixed with `lsx_` and the integer
 * types tend to correspond to suffixes like "b", "h", "w" or "d".
 */
public open class M128i(
    public var value: LongArray = LongArray(2),
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M128i = M128i(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m128i", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M128i> =
            object : TypeLayout<M128i> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128i =
                    M128i(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M128i) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128i =
                    M128i(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128i) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): M128i = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): M128i = transmuteWithLayouts<Simd<Long>, M128i>(simd, UnresolvedTypeLayout<Simd<Long>>(), M128i.typeLayout())
    }
}

public fun fromSimdLong(simd: M128i): Simd<Long> = transmuteWithLayouts<M128i, Simd<Long>>(simd, M128i.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * 128-bit wide set of four `Float` values, LoongArch-specific
 *
 * This type is the same as the `__m128` type defined in `lsxintrin.h`,
 * representing a 128-bit SIMD register which internally consists of
 * four packed `Float` instances. Usage of this type typically occurs in
 * conjunction with the `lsx` and higher target features for LoongArch.
 *
 * Note that unlike `m128i`, the integer version of the 128-bit registers,
 * this `m128` type has *one* interpretation. Each instance of `m128`
 * corresponds to `f32x4`, or four `Float` values packed together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `m128` are prefixed with `lsx_` and are suffixed
 * with "s".
 */
public open class M128(
    public var value: FloatArray = FloatArray(4),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M128 = M128(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m128", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M128> =
            object : TypeLayout<M128> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128 =
                    M128(
                        (PrimitiveArrayLayouts.floatArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M128) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128 =
                    M128(
                        (PrimitiveArrayLayouts.floatArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128) {
                    (PrimitiveArrayLayouts.floatArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): M128 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): M128 = transmuteWithLayouts<Simd<Float>, M128>(simd, UnresolvedTypeLayout<Simd<Float>>(), M128.typeLayout())
    }
}

public fun fromSimdFloat(simd: M128): Simd<Float> = transmuteWithLayouts<M128, Simd<Float>>(simd, M128.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

/**
 * 128-bit wide set of two `Double` values, LoongArch-specific
 *
 * This type is the same as the `__m128d` type defined in `lsxintrin.h`,
 * representing a 128-bit SIMD register which internally consists of
 * two packed `Double` instances. Usage of this type typically occurs in
 * conjunction with the `lsx` and higher target features for LoongArch.
 *
 * Note that unlike `m128i`, the integer version of the 128-bit registers,
 * this `m128d` type has *one* interpretation. Each instance of `m128d`
 * always corresponds to `f64x2`, or two `Double` values packed together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `m128d` are prefixed with `lsx_` and are suffixed
 * with "d". Not to be confused with "d" which is used for `m128i`.
 */
public open class M128d(
    public var value: DoubleArray = DoubleArray(2),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M128d = M128d(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m128d", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M128d> =
            object : TypeLayout<M128d> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128d =
                    M128d(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M128d) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128d =
                    M128d(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128d) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): M128d = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): M128d = transmuteWithLayouts<Simd<Double>, M128d>(simd, UnresolvedTypeLayout<Simd<Double>>(), M128d.typeLayout())
    }
}

public fun fromSimdDouble(simd: M128d): Simd<Double> = transmuteWithLayouts<M128d, Simd<Double>>(simd, M128d.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public open class V16i8(
    public var value: ByteArray = ByteArray(16),
) {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V8i16(
    public var value: ShortArray = ShortArray(8),
) {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V4i32(
    public var value: IntArray = IntArray(4),
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V2i64(
    public var value: LongArray = LongArray(2),
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V16u8(
    public var value: UByteArray = UByteArray(16),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V8u16(
    public var value: UShortArray = UShortArray(8),
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V4u32(
    public var value: UIntArray = UIntArray(4),
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V2u64(
    public var value: ULongArray = ULongArray(2),
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V4f32(
    public var value: FloatArray = FloatArray(4),
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

public open class V2f64(
    public var value: DoubleArray = DoubleArray(2),
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

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
    }
}

// These type aliases are provided solely for transitional compatibility.
// They are temporary and will be removed when appropriate.
public typealias V16i8 = M128i

public typealias V8i16 = M128i

public typealias V4i32 = M128i

public typealias V2i64 = M128i

public typealias V16u8 = M128i

public typealias V8u16 = M128i

public typealias V4u32 = M128i

public typealias V2u64 = M128i

public typealias V4f32 = M128

public typealias V2f64 = M128d

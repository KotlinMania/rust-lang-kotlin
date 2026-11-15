// port-lint: source loongarch64/lasx/types.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.Simd
import io.github.kotlinmania.rustlang.simd.debugSimdFinish

/**
 * 256-bit wide integer vector type, LoongArch-specific
 *
 * This type is the same as the `__m256i` type defined in `lasxintrin.h`,
 * representing a 256-bit SIMD register. Usage of this type typically
 * occurs in conjunction with the `lasx` target features for LoongArch.
 *
 * Internally this type may be viewed as:
 *
 * * `i8x32` - thirty two `Byte` values packed together
 * * `i16x16` - sixteen `Short` values packed together
 * * `i32x8` - eight `Int` values packed together
 * * `i64x4` - four `Long` values packed together
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
 * Note that this means that an instance of `m256i` typically just means
 * a "bag of bits" which is left up to interpretation at the point of use.
 *
 * Most intrinsics using `m256i` are prefixed with `lasx_` and the integer
 * types tend to correspond to suffixes like "b", "h", "w" or "d".
 */
public open class M256i(
    public var value: LongArray,
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M256i = M256i(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m256i", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M256i> =
            object : TypeLayout<M256i> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): M256i =
                    M256i(
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M256i) {
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M256i =
                    M256i(
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M256i) {
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): M256i = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): M256i = transmute(simd)
    }
}

public fun fromSimdLong(simd: M256i): Simd<Long> = transmute(simd)

/**
 * 256-bit wide set of eight `Float` values, LoongArch-specific
 *
 * This type is the same as the `__m256` type defined in `lasxintrin.h`,
 * representing a 256-bit SIMD register which internally consists of
 * eight packed `Float` instances. Usage of this type typically occurs in
 * conjunction with the `lasx` target features for LoongArch.
 *
 * Note that unlike `m256i`, the integer version of the 256-bit registers,
 * this `m256` type has *one* interpretation. Each instance of `m256`
 * always corresponds to `f32x8`, or eight `Float` values packed together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding  between two consecutive elements); however, the
 * alignment is different and equal to the size of the type. Note that the
 * ABI for function calls may *not* be the same.
 *
 * Most intrinsics using `m256` are prefixed with `lasx_` and are
 * suffixed with "s".
 */
public open class M256(
    public var value: FloatArray,
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M256 = M256(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m256", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M256> =
            object : TypeLayout<M256> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): M256 =
                    M256(
                        (PrimitiveArrayLayouts.floatArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M256) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M256 =
                    M256(
                        (PrimitiveArrayLayouts.floatArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M256) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): M256 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): M256 = transmute(simd)
    }
}

public fun fromSimdFloat(simd: M256): Simd<Float> = transmute(simd)

/**
 * 256-bit wide set of four `Double` values, LoongArch-specific
 *
 * This type is the same as the `__m256d` type defined in `lasxintrin.h`,
 * representing a 256-bit SIMD register which internally consists of
 * four packed `Double` instances. Usage of this type typically occurs in
 * conjunction with the `lasx` target features for LoongArch.
 *
 * Note that unlike `m256i`, the integer version of the 256-bit registers,
 * this `m256d` type has *one* interpretation. Each instance of `m256d`
 * always corresponds to `f64x4`, or four `Double` values packed together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `m256d` are prefixed with `lasx_` and are suffixed
 * with "d". Not to be confused with "d" which is used for `m256i`.
 */
public open class M256d(
    public var value: DoubleArray,
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M256d = M256d(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "m256d", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<M256d> =
            object : TypeLayout<M256d> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): M256d =
                    M256d(
                        (PrimitiveArrayLayouts.doubleArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M256d) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M256d =
                    M256d(
                        (PrimitiveArrayLayouts.doubleArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M256d) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): M256d = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): M256d = transmute(simd)
    }
}

public fun fromSimdDouble(simd: M256d): Simd<Double> = transmute(simd)

public open class V32i8(
    public var value: ByteArray,
) {
    public var field0: ByteArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V32i8> =
            object : TypeLayout<V32i8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V32i8 =
                    V32i8(
                        (PrimitiveArrayLayouts.byteArray(32)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V32i8) {
                    (PrimitiveArrayLayouts.byteArray(32)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V32i8 =
                    V32i8(
                        (PrimitiveArrayLayouts.byteArray(32)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V32i8) {
                    (PrimitiveArrayLayouts.byteArray(32)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V16i16(
    public var value: ShortArray,
) {
    public var field0: ShortArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V16i16> =
            object : TypeLayout<V16i16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V16i16 =
                    V16i16(
                        (PrimitiveArrayLayouts.shortArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V16i16) {
                    (PrimitiveArrayLayouts.shortArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V16i16 =
                    V16i16(
                        (PrimitiveArrayLayouts.shortArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V16i16) {
                    (PrimitiveArrayLayouts.shortArray(16)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V8i32(
    public var value: IntArray,
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V8i32> =
            object : TypeLayout<V8i32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V8i32 =
                    V8i32(
                        (PrimitiveArrayLayouts.intArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V8i32) {
                    (PrimitiveArrayLayouts.intArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V8i32 =
                    V8i32(
                        (PrimitiveArrayLayouts.intArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V8i32) {
                    (PrimitiveArrayLayouts.intArray(8)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V4i64(
    public var value: LongArray,
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V4i64> =
            object : TypeLayout<V4i64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V4i64 =
                    V4i64(
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4i64) {
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4i64 =
                    V4i64(
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4i64) {
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V32u8(
    public var value: UByteArray,
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V32u8> =
            object : TypeLayout<V32u8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V32u8 =
                    V32u8(
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V32u8) {
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V32u8 =
                    V32u8(
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V32u8) {
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V16u16(
    public var value: UShortArray,
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V16u16> =
            object : TypeLayout<V16u16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V16u16 =
                    V16u16(
                        (PrimitiveArrayLayouts.uShortArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V16u16) {
                    (PrimitiveArrayLayouts.uShortArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V16u16 =
                    V16u16(
                        (PrimitiveArrayLayouts.uShortArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V16u16) {
                    (PrimitiveArrayLayouts.uShortArray(16)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V8u32(
    public var value: UIntArray,
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V8u32> =
            object : TypeLayout<V8u32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V8u32 =
                    V8u32(
                        (PrimitiveArrayLayouts.uIntArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V8u32) {
                    (PrimitiveArrayLayouts.uIntArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V8u32 =
                    V8u32(
                        (PrimitiveArrayLayouts.uIntArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V8u32) {
                    (PrimitiveArrayLayouts.uIntArray(8)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V4u64(
    public var value: ULongArray,
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V4u64> =
            object : TypeLayout<V4u64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V4u64 =
                    V4u64(
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4u64) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4u64 =
                    V4u64(
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4u64) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V8f32(
    public var value: FloatArray,
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V8f32> =
            object : TypeLayout<V8f32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V8f32 =
                    V8f32(
                        (PrimitiveArrayLayouts.floatArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V8f32) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V8f32 =
                    V8f32(
                        (PrimitiveArrayLayouts.floatArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V8f32) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class V4f64(
    public var value: DoubleArray,
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<V4f64> =
            object : TypeLayout<V4f64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): V4f64 =
                    V4f64(
                        (PrimitiveArrayLayouts.doubleArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V4f64) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V4f64 =
                    V4f64(
                        (PrimitiveArrayLayouts.doubleArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V4f64) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

// These type aliases are provided solely for transitional compatibility.
// They are temporary and will be removed when appropriate.
public typealias V32i8 = M256i

public typealias V16i16 = M256i

public typealias V8i32 = M256i

public typealias V4i64 = M256i

public typealias V32u8 = M256i

public typealias V16u16 = M256i

public typealias V8u32 = M256i

public typealias V4u64 = M256i

public typealias V8f32 = M256

public typealias V4f64 = M256d

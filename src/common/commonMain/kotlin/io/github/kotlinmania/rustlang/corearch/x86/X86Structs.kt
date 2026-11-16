@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.corearch.simd.F16x16
import io.github.kotlinmania.rustlang.corearch.simd.F16x32
import io.github.kotlinmania.rustlang.corearch.simd.F16x8
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x64
import io.github.kotlinmania.rustlang.corearch.simd.debugSimdFinish

/**
 * The x87 80-bit extended-precision floating-point type.
 *
 * Note: extend the documentation.
 */
public open class X87F80 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<X87F80> = UnresolvedTypeLayout(),
) {
    public var field0: X87F80
        get() = value
        set(v) {
            value = v
        }

    public var value: X87F80
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: X87F80, fieldLayout: TypeLayout<X87F80> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<X87F80> = TransparentTypeLayout(fieldLayout, { ptr, layout -> X87F80(ptr, layout) }, { it.value })

    public open fun clone(): X87F80 = X87F80(value, fieldLayout)

    public open fun fmt(f: Formatter): Result {
        val bytes = UByteArray(16) { 0u.toUByte() }
        this.toNeBytes().copyInto(bytes, destinationOffset = 0)
        return f.append("x87_f80(${U128.fromLeBytes(bytes)})")
    }

    /**
     * Returns the memory representation of this floating point number as a byte array in
     * native byte order.
     */
    public open fun toNeBytes(): UByteArray {
        // SAFETY: the first 10 bytes are initialized.
        return (UnresolvedTypeLayout<UByteArray>()).readNative((ConstPtr(((addrOf(this))).rawAddress)).rawAddress)
    }

    public companion object {
        public operator fun invoke(value: X87F80, fieldLayout: TypeLayout<X87F80> = UnresolvedTypeLayout()): X87F80 = X87F80(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<X87F80> = UnresolvedTypeLayout()): X87F80 = X87F80(fieldStorage, fieldLayout)

        /**
         * Creates a floating point value from its representation as a byte array in native endian.
         */
        public fun fromNeBytes(bytes: UByteArray): X87F80 {
            // SAFETY: we initialize the first 10 bytes, the remaining bytes remain uninitialized.
            // That is exactly the layout of x87F80.
            return run {
                val thisRef = MaybeUninit.uninit(UnresolvedTypeLayout<X87F80>())
                thisRef.asMutPtr().cast().write(bytes)
                thisRef.assumeInit()
            }
        }
    }
}

/**
 * 128-bit wide integer vector type, x86-specific
 *
 * This type is the same as the `__m128i` type defined by Intel,
 * representing a 128-bit SIMD register. Usage of this type typically
 * corresponds to the `sse` and up target features for x86/x8664.
 *
 * Internally this type may be viewed as:
 *
 * * `i8x16` - sixteen `Byte` variables packed together
 * * `i16x8` - eight `Short` variables packed together
 * * `i32x4` - four `Int` variables packed together
 * * `i64x2` - two `Long` variables packed together
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
 * Note that this means that an instance of `__m128i` typically just means
 * a "bag of bits" which is left up to interpretation at the point of use.
 *
 * Most intrinsics using `__m128i` are prefixed with `_mm_` and the
 * integer types tend to correspond to suffixes like "epi8" or "epi32".
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val allBytesZero = mmSetzeroSi128()
 * val allBytesOne = mmSet1Epi8(1)
 * val fourI32 = mmSetEpi32(1, 2, 3, 4)
 * ```
 */
public open class M128i(
    public var value: LongArray,
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m128i", this.asArray())

    public open fun asU8x16(): U8x16 = transmute(this)

    public open fun asU16x8(): U16x8 = transmute(this)

    public open fun asU32x4(): U32x4 = transmute(this)

    public open fun asU64x2(): U64x2 = transmute(this)

    public open fun asI8x16(): I8x16 = transmute(this)

    public open fun asI16x8(): I16x8 = transmute(this)

    public open fun asI32x4(): I32x4 = transmute(this)

    public open fun asI64x2(): I64x2 = transmute(this)

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

        public fun from(simd: Simd<Long>): M128i = transmute(simd)
    }
}

/**
 * 128-bit wide set of four `Float` types, x86-specific
 *
 * This type is the same as the `__m128` type defined by Intel,
 * representing a 128-bit SIMD register which internally is consisted of
 * four packed `Float` instances. Usage of this type typically corresponds
 * to the `sse` and up target features for x86/x8664.
 *
 * Note that unlike `__m128i`, the integer version of the 128-bit
 * registers, this `__m128` type has *one* interpretation. Each instance
 * of `__m128` always corresponds to `f32x4`, or four `Float` types packed
 * together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `__m128` are prefixed with `_mm_` and are
 * suffixed with "ps" (or otherwise contain "ps"). Not to be confused with
 * "pd" which is used for `__m128d`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val fourZeros = mmSetzeroPs()
 * val fourOnes = mmSet1Ps(1.0)
 * val fourFloats = mmSetPs(1.0, 2.0, 3.0, 4.0)
 * ```
 */
public open class M128(
    public var value: FloatArray,
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m128", this.asArray())

    public open fun asF32x4(): F32x4 = transmute(this)

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

        public fun from(simd: Simd<Float>): M128 = transmute(simd)
    }
}

/**
 * 128-bit wide set of two `Double` types, x86-specific
 *
 * This type is the same as the `__m128d` type defined by Intel,
 * representing a 128-bit SIMD register which internally is consisted of
 * two packed `Double` instances. Usage of this type typically corresponds
 * to the `sse` and up target features for x86/x8664.
 *
 * Note that unlike `__m128i`, the integer version of the 128-bit
 * registers, this `__m128d` type has *one* interpretation. Each instance
 * of `__m128d` always corresponds to `f64x2`, or two `Double` types packed
 * together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `__m128d` are prefixed with `_mm_` and are
 * suffixed with "pd" (or otherwise contain "pd"). Not to be confused with
 * "ps" which is used for `__m128`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val twoZeros = mmSetzeroPd()
 * val twoOnes = mmSet1Pd(1.0)
 * val twoFloats = mmSetPd(1.0, 2.0)
 * ```
 */
public open class M128d(
    public var value: DoubleArray,
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m128d", this.asArray())

    public open fun asF64x2(): F64x2 = transmute(this)

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

        public fun from(simd: Simd<Double>): M128d = transmute(simd)
    }
}

/**
 * 256-bit wide integer vector type, x86-specific
 *
 * This type is the same as the `__m256i` type defined by Intel,
 * representing a 256-bit SIMD register. Usage of this type typically
 * corresponds to the `avx` and up target features for x86/x8664.
 *
 * Internally this type may be viewed as:
 *
 * * `i8x32` - thirty two `Byte` variables packed together
 * * `i16x16` - sixteen `Short` variables packed together
 * * `i32x8` - eight `Int` variables packed together
 * * `i64x4` - four `Long` variables packed together
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
 * Note that this means that an instance of `__m256i` typically just means
 * a "bag of bits" which is left up to interpretation at the point of use.
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val allBytesZero = mm256SetzeroSi256()
 * val allBytesOne = mm256Set1Epi8(1)
 * val eightI32 = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
 * ```
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m256i", this.asArray())

    public open fun asU8x32(): U8x32 = transmute(this)

    public open fun asU16x16(): U16x16 = transmute(this)

    public open fun asU32x8(): U32x8 = transmute(this)

    public open fun asU64x4(): U64x4 = transmute(this)

    public open fun asI8x32(): I8x32 = transmute(this)

    public open fun asI16x16(): I16x16 = transmute(this)

    public open fun asI32x8(): I32x8 = transmute(this)

    public open fun asI64x4(): I64x4 = transmute(this)

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

/**
 * 256-bit wide set of eight `Float` types, x86-specific
 *
 * This type is the same as the `__m256` type defined by Intel,
 * representing a 256-bit SIMD register which internally is consisted of
 * eight packed `Float` instances. Usage of this type typically corresponds
 * to the `avx` and up target features for x86/x8664.
 *
 * Note that unlike `__m256i`, the integer version of the 256-bit
 * registers, this `__m256` type has *one* interpretation. Each instance
 * of `__m256` always corresponds to `f32x8`, or eight `Float` types packed
 * together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding  between two consecutive elements); however, the
 * alignment is different and equal to the size of the type. Note that the
 * ABI for function calls may *not* be the same.
 *
 * Most intrinsics using `__m256` are prefixed with `_mm256_` and are
 * suffixed with "ps" (or otherwise contain "ps"). Not to be confused with
 * "pd" which is used for `__m256d`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val eightZeros = mm256SetzeroPs()
 * val eightOnes = mm256Set1Ps(1.0)
 * val eightFloats = mm256SetPs(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
 * ```
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m256", this.asArray())

    public open fun asF32x8(): F32x8 = transmute(this)

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

/**
 * 256-bit wide set of four `Double` types, x86-specific
 *
 * This type is the same as the `__m256d` type defined by Intel,
 * representing a 256-bit SIMD register which internally is consisted of
 * four packed `Double` instances. Usage of this type typically corresponds
 * to the `avx` and up target features for x86/x8664.
 *
 * Note that unlike `__m256i`, the integer version of the 256-bit
 * registers, this `__m256d` type has *one* interpretation. Each instance
 * of `__m256d` always corresponds to `f64x4`, or four `Double` types packed
 * together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 *
 * Most intrinsics using `__m256d` are prefixed with `_mm256_` and are
 * suffixed with "pd" (or otherwise contain "pd"). Not to be confused with
 * "ps" which is used for `__m256`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val fourZeros = mm256SetzeroPd()
 * val fourOnes = mm256Set1Pd(1.0)
 * val fourFloats = mm256SetPd(1.0, 2.0, 3.0, 4.0)
 * ```
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

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m256d", this.asArray())

    public open fun asF64x4(): F64x4 = transmute(this)

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

/**
 * 512-bit wide integer vector type, x86-specific
 *
 * This type is the same as the `__m512i` type defined by Intel,
 * representing a 512-bit SIMD register. Usage of this type typically
 * corresponds to the `avx512 *` and up target features for x86/x8664.
 *
 * Internally this type may be viewed as:
 *
 * * `i8x64` - sixty-four `Byte` variables packed together
 * * `i16x32` - thirty-two `Short` variables packed together
 * * `i32x16` - sixteen `Int` variables packed together
 * * `i64x8` - eight `Long` variables packed together
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
 * Note that this means that an instance of `__m512i` typically just means
 * a "bag of bits" which is left up to interpretation at the point of use.
 */
public open class M512i(
    public var value: LongArray,
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M512i = M512i(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m512i", this.asArray())

    public open fun asU8x64(): U8x64 = transmute(this)

    public open fun asU16x32(): U16x32 = transmute(this)

    public open fun asU32x16(): U32x16 = transmute(this)

    public open fun asU64x8(): U64x8 = transmute(this)

    public open fun asI8x64(): I8x64 = transmute(this)

    public open fun asI16x32(): I16x32 = transmute(this)

    public open fun asI32x16(): I32x16 = transmute(this)

    public open fun asI64x8(): I64x8 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M512i> =
            object : TypeLayout<M512i> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): M512i =
                    M512i(
                        (PrimitiveArrayLayouts.longArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M512i) {
                    (PrimitiveArrayLayouts.longArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M512i =
                    M512i(
                        (PrimitiveArrayLayouts.longArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M512i) {
                    (PrimitiveArrayLayouts.longArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): M512i = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): M512i = transmute(simd)
    }
}

/**
 * 512-bit wide set of sixteen `Float` types, x86-specific
 *
 * This type is the same as the `__m512` type defined by Intel,
 * representing a 512-bit SIMD register which internally is consisted of
 * eight packed `Float` instances. Usage of this type typically corresponds
 * to the `avx512 *` and up target features for x86/x8664.
 *
 * Note that unlike `__m512i`, the integer version of the 512-bit
 * registers, this `__m512` type has *one* interpretation. Each instance
 * of `__m512` always corresponds to `f32x16`, or sixteen `Float` types
 * packed together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding  between two consecutive elements); however, the
 * alignment is different and equal to the size of the type. Note that the
 * ABI for function calls may *not* be the same.
 *
 * Most intrinsics using `__m512` are prefixed with `_mm512_` and are
 * suffixed with "ps" (or otherwise contain "ps"). Not to be confused with
 * "pd" which is used for `__m512d`.
 */
public open class M512(
    public var value: FloatArray,
) {
    public var field0: FloatArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M512 = M512(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): FloatArray = (PrimitiveArrayLayouts.floatArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m512", this.asArray())

    public open fun asF32x16(): F32x16 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M512> =
            object : TypeLayout<M512> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): M512 =
                    M512(
                        (PrimitiveArrayLayouts.floatArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M512) {
                    (PrimitiveArrayLayouts.floatArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M512 =
                    M512(
                        (PrimitiveArrayLayouts.floatArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M512) {
                    (PrimitiveArrayLayouts.floatArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Float): M512 = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): M512 = transmute(simd)
    }
}

/**
 * 512-bit wide set of eight `Double` types, x86-specific
 *
 * This type is the same as the `__m512d` type defined by Intel,
 * representing a 512-bit SIMD register which internally is consisted of
 * eight packed `Double` instances. Usage of this type typically corresponds
 * to the `avx` and up target features for x86/x8664.
 *
 * Note that unlike `__m512i`, the integer version of the 512-bit
 * registers, this `__m512d` type has *one* interpretation. Each instance
 * of `__m512d` always corresponds to `f64x8`, or eight `Double` types packed
 * together.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding  between two consecutive elements); however, the
 * alignment is different and equal to the size of the type. Note that the
 * ABI for function calls may *not* be the same.
 *
 * Most intrinsics using `__m512d` are prefixed with `_mm512_` and are
 * suffixed with "pd" (or otherwise contain "pd"). Not to be confused with
 * "ps" which is used for `__m512`.
 */
public open class M512d(
    public var value: DoubleArray,
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M512d = M512d(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m512d", this.asArray())

    public open fun asF64x8(): F64x8 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M512d> =
            object : TypeLayout<M512d> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): M512d =
                    M512d(
                        (PrimitiveArrayLayouts.doubleArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M512d) {
                    (PrimitiveArrayLayouts.doubleArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M512d =
                    M512d(
                        (PrimitiveArrayLayouts.doubleArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M512d) {
                    (PrimitiveArrayLayouts.doubleArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): M512d = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): M512d = transmute(simd)
    }
}

/**
 * 128-bit wide set of eight `UShort` types, x86-specific
 *
 * This type is representing a 128-bit SIMD register which internally is consisted of
 * eight packed `UShort` instances. Its purpose is for bf16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M128bh(
    public var value: UShortArray,
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M128bh = M128bh(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m128bh", this.asArray())

    public open fun asU16x8(): U16x8 = transmute(this)

    public open fun asU32x4(): U32x4 = transmute(this)

    public open fun asI16x8(): I16x8 = transmute(this)

    public open fun asI32x4(): I32x4 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M128bh> =
            object : TypeLayout<M128bh> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128bh =
                    M128bh(
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M128bh) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128bh =
                    M128bh(
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128bh) {
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): M128bh = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): M128bh = transmute(simd)
    }
}

/**
 * 256-bit wide set of 16 `UShort` types, x86-specific
 *
 * This type is the same as the `__m256bh` type defined by Intel,
 * representing a 256-bit SIMD register which internally is consisted of
 * 16 packed `UShort` instances. Its purpose is for bf16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M256bh(
    public var value: UShortArray,
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M256bh = M256bh(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m256bh", this.asArray())

    public open fun asU16x16(): U16x16 = transmute(this)

    public open fun asU32x8(): U32x8 = transmute(this)

    public open fun asI16x16(): I16x16 = transmute(this)

    public open fun asI32x8(): I32x8 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M256bh> =
            object : TypeLayout<M256bh> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): M256bh =
                    M256bh(
                        (PrimitiveArrayLayouts.uShortArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M256bh) {
                    (PrimitiveArrayLayouts.uShortArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M256bh =
                    M256bh(
                        (PrimitiveArrayLayouts.uShortArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M256bh) {
                    (PrimitiveArrayLayouts.uShortArray(16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): M256bh = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): M256bh = transmute(simd)
    }
}

/**
 * 512-bit wide set of 32 `UShort` types, x86-specific
 *
 * This type is the same as the `__m512bh` type defined by Intel,
 * representing a 512-bit SIMD register which internally is consisted of
 * 32 packed `UShort` instances. Its purpose is for bf16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M512bh(
    public var value: UShortArray,
) {
    public var field0: UShortArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M512bh = M512bh(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): UShortArray = (PrimitiveArrayLayouts.uShortArray((32).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((32).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m512bh", this.asArray())

    public open fun asU16x32(): U16x32 = transmute(this)

    public open fun asU32x16(): U32x16 = transmute(this)

    public open fun asI16x32(): I16x32 = transmute(this)

    public open fun asI32x16(): I32x16 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M512bh> =
            object : TypeLayout<M512bh> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): M512bh =
                    M512bh(
                        (PrimitiveArrayLayouts.uShortArray(32)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M512bh) {
                    (PrimitiveArrayLayouts.uShortArray(32)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M512bh =
                    M512bh(
                        (PrimitiveArrayLayouts.uShortArray(32)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M512bh) {
                    (PrimitiveArrayLayouts.uShortArray(32)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: UShort): M512bh = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): M512bh = transmute(simd)
    }
}

/**
 * 128-bit wide set of 8 `f16` types, x86-specific
 *
 * This type is the same as the `__m128h` type defined by Intel,
 * representing a 128-bit SIMD register which internally is consisted of
 * 8 packed `f16` instances. its purpose is for f16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M128h(
    public var value: List<F16>,
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M128h = M128h(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m128h", this.asArray())

    public open fun asF16x8(): F16x8 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M128h> =
            object : TypeLayout<M128h> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128h =
                    M128h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M128h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128h =
                    M128h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 8)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): M128h = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<F16>): M128h = transmute(simd)
    }
}

/**
 * 256-bit wide set of 16 `f16` types, x86-specific
 *
 * This type is the same as the `__m256h` type defined by Intel,
 * representing a 256-bit SIMD register which internally is consisted of
 * 16 packed `f16` instances. its purpose is for f16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M256h(
    public var value: List<F16>,
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M256h = M256h(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m256h", this.asArray())

    public open fun asF16x16(): F16x16 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M256h> =
            object : TypeLayout<M256h> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): M256h =
                    M256h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M256h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M256h =
                    M256h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M256h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 16)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): M256h = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<F16>): M256h = transmute(simd)
    }
}

/**
 * 512-bit wide set of 32 `f16` types, x86-specific
 *
 * This type is the same as the `__m512h` type defined by Intel,
 * representing a 512-bit SIMD register which internally is consisted of
 * 32 packed `f16` instances. its purpose is for f16 related intrinsic
 * implementations.
 *
 * The in-memory representation of this type is the same as the one of an
 * equivalent array (i.e. the in-memory order of elements is the same, and
 * there is no padding); however, the alignment is different and equal to
 * the size of the type. Note that the ABI for function calls may *not* be
 * the same.
 */
public open class M512h(
    public var value: List<F16>,
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): M512h = M512h(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (32).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (32).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "__m512h", this.asArray())

    public open fun asF16x32(): F16x32 = transmute(this)

    public companion object {
        public fun typeLayout(): TypeLayout<M512h> =
            object : TypeLayout<M512h> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): M512h =
                    M512h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 32)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: M512h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 32)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M512h =
                    M512h(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 32)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M512h) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 32)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): M512h = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<F16>): M512h = transmute(simd)
    }
}

/**
 * The BFloat16 type used in AVX-512 intrinsics.
 */
public open class Bf16 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout(),
) {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public var value: UShort
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Bf16> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Bf16(ptr, layout) }, { it.value })

    public open fun clone(): Bf16 = Bf16(value, fieldLayout)

    /**
     * Raw transmutation to `UShort`
     */
    public open fun toBits(): UShort = this.value

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): Bf16 = Bf16(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): Bf16 = Bf16(fieldStorage, fieldLayout)

        /**
         * Raw transmutation from `UShort`
         */
        public fun fromBits(bits: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): Bf16 = Bf16(bits, fieldLayout = fieldLayout)
    }
}

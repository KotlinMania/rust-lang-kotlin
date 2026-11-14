// port-lint: source powerpc/altivec.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.debugSimdFinish

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
    public var value: ByteArray,
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
    public open fun asMutArray(): ByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.byteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_signed_char", this.asArray())

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
        public fun splat(value: Byte): VectorSignedChar = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Byte>): VectorSignedChar = transmute(simd)
    }
}

public fun fromSimdByte(simd: VectorSignedChar): Simd<Byte> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of sixteen packed `UByte`
 */
public open class VectorUnsignedChar(
    public var value: UByteArray,
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
    public open fun asMutArray(): UByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uByteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_unsigned_char", this.asArray())

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
        public fun splat(value: UByte): VectorUnsignedChar = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UByte>): VectorUnsignedChar = transmute(simd)
    }
}

public fun fromSimdUByte(simd: VectorUnsignedChar): Simd<UByte> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector mask of sixteen packed elements
 */
public open class VectorBoolChar(
    public var value: ByteArray,
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
    public open fun asMutArray(): ByteArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.byteArray((16).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_bool_char", this.asArray())

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
        public fun splat(value: Byte): VectorBoolChar = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Byte>): VectorBoolChar = transmute(simd)

        public fun from(value: M8x16): VectorBoolChar = transmute(value)
    }
}

public fun fromSimdByte(simd: VectorBoolChar): Simd<Byte> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of eight packed `Short`
 */
public open class VectorSignedShort(
    public var value: ShortArray,
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
    public open fun asMutArray(): ShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.shortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_signed_short", this.asArray())

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
        public fun splat(value: Short): VectorSignedShort = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Short>): VectorSignedShort = transmute(simd)
    }
}

public fun fromSimdShort(simd: VectorSignedShort): Simd<Short> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of eight packed `UShort`
 */
public open class VectorUnsignedShort(
    public var value: UShortArray,
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
    public open fun asMutArray(): UShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uShortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_unsigned_short", this.asArray())

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
        public fun splat(value: UShort): VectorUnsignedShort = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UShort>): VectorUnsignedShort = transmute(simd)
    }
}

public fun fromSimdUShort(simd: VectorUnsignedShort): Simd<UShort> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector mask of eight packed elements
 */
public open class VectorBoolShort(
    public var value: ShortArray,
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
    public open fun asMutArray(): ShortArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.shortArray((8).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_bool_short", this.asArray())

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
        public fun splat(value: Short): VectorBoolShort = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Short>): VectorBoolShort = transmute(simd)

        public fun from(value: M16x8): VectorBoolShort = transmute(value)
    }
}

public fun fromSimdShort(simd: VectorBoolShort): Simd<Short> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of four packed `Int`
 */
public open class VectorSignedInt(
    public var value: IntArray,
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
    public open fun asMutArray(): IntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.intArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_signed_int", this.asArray())

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
        public fun splat(value: Int): VectorSignedInt = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Int>): VectorSignedInt = transmute(simd)
    }
}

public fun fromSimdInt(simd: VectorSignedInt): Simd<Int> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of four packed `UInt`
 */
public open class VectorUnsignedInt(
    public var value: UIntArray,
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
    public open fun asMutArray(): UIntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uIntArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_unsigned_int", this.asArray())

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
        public fun splat(value: UInt): VectorUnsignedInt = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<UInt>): VectorUnsignedInt = transmute(simd)
    }
}

public fun fromSimdUInt(simd: VectorUnsignedInt): Simd<UInt> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector mask of four packed elements
 */
public open class VectorBoolInt(
    public var value: IntArray,
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
    public open fun asMutArray(): IntArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.intArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_bool_int", this.asArray())

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
        public fun splat(value: Int): VectorBoolInt = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Int>): VectorBoolInt = transmute(simd)

        public fun from(value: M32x4): VectorBoolInt = transmute(value)
    }
}

public fun fromSimdInt(simd: VectorBoolInt): Simd<Int> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of four packed `Float`
 */
public open class VectorFloat(
    public var value: FloatArray,
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
    public open fun asMutArray(): FloatArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.floatArray((4).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_float", this.asArray())

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
        public fun splat(value: Float): VectorFloat = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Float>): VectorFloat = transmute(simd)
    }
}

public fun fromSimdFloat(simd: VectorFloat): Simd<Float> = transmute(simd)

public fun fromM8x16(value: VectorBoolChar): M8x16 = transmute(value)

public fun fromM16x8(value: VectorBoolShort): M16x8 = transmute(value)

public fun fromM32x4(value: VectorBoolInt): M32x4 = transmute(value)

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

public interface VectorNeg<T> {
    public fun vecNeg(): T
}

public fun VectorSignedChar.vecNeg(): VectorSignedChar = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedChar.vecNeg(): VectorUnsignedChar = run { val _simdArg0 = this; _simdArg0 }

public fun VectorBoolChar.vecNeg(): VectorBoolChar = run { val _simdArg0 = this; _simdArg0 }

public fun VectorSignedShort.vecNeg(): VectorSignedShort = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedShort.vecNeg(): VectorUnsignedShort = run { val _simdArg0 = this; _simdArg0 }

public fun VectorBoolShort.vecNeg(): VectorBoolShort = run { val _simdArg0 = this; _simdArg0 }

public fun VectorSignedInt.vecNeg(): VectorSignedInt = run { val _simdArg0 = this; _simdArg0 }

public fun VectorUnsignedInt.vecNeg(): VectorUnsignedInt = run { val _simdArg0 = this; _simdArg0 }

public fun VectorBoolInt.vecNeg(): VectorBoolInt = run { val _simdArg0 = this; _simdArg0 }

public fun VectorFloat.vecNeg(): VectorFloat = run { val _simdArg0 = this; _simdArg0 }

public interface VectorInsert<T, Scalar> {
    public fun vecInsert(s: Scalar, idx: UInt): T
}

public fun <T> idxInVec(idx: UInt, typeLayoutT: TypeLayout<T>): UInt = idx and (16u / sizeOf<T>(typeLayoutT = typeLayoutT).toUInt())

public fun TTL.vecInsert(s: Byte, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<Byte>(idx, typeLayoutT = ObjectSlotLayout<Byte>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: UByte, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<UByte>(idx, typeLayoutT = ObjectSlotLayout<UByte>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: Short, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<Short>(idx, typeLayoutT = ObjectSlotLayout<Short>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: UShort, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<UShort>(idx, typeLayoutT = ObjectSlotLayout<UShort>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: Int, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<Int>(idx, typeLayoutT = ObjectSlotLayout<Int>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: UInt, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<UInt>(idx, typeLayoutT = ObjectSlotLayout<UInt>()); val _simdArg2 = s; _simdArg0 }

public fun TTL.vecInsert(s: Float, idx: UInt): TTL = run { val _simdArg0 = this; val _simdArg1 = idxInVec<Float>(idx, typeLayoutT = ObjectSlotLayout<Float>()); val _simdArg2 = s; _simdArg0 }

public interface VectorExtract<T, out Scalar> {
    public fun vecExtract(idx: UInt): Scalar
}

public fun TTL.vecExtract(idx: UInt): Byte = run { val _simdArg0 = this; val _simdArg1 = idxInVec<Byte>(idx, typeLayoutT = ObjectSlotLayout<Byte>()); _simdArg0 }

public interface VectorLd<out Result> {
    public fun vecLd(off: Int): Result

    public fun vecLdl(off: Int): Result
}

public fun vecLdU8(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlU8(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLd(off: Int): TTL = vecLdU8(off, this)

public fun ConstPtr.vecLdl(off: Int): TTL = vecLdlU8(off, this)

public fun vecLdI8(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlI8(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrByte(off: Int): TTL = vecLdI8(off, this)

public fun ConstPtr.vecLdlFromConstPtrByte(off: Int): TTL = vecLdlI8(off, this)

public fun vecLdU16(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlU16(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrUShort(off: Int): TTL = vecLdU16(off, this)

public fun ConstPtr.vecLdlFromConstPtrUShort(off: Int): TTL = vecLdlU16(off, this)

public fun vecLdI16(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlI16(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrShort(off: Int): TTL = vecLdI16(off, this)

public fun ConstPtr.vecLdlFromConstPtrShort(off: Int): TTL = vecLdlI16(off, this)

public fun vecLdU32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlU32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrUInt(off: Int): TTL = vecLdU32(off, this)

public fun ConstPtr.vecLdlFromConstPtrUInt(off: Int): TTL = vecLdlU32(off, this)

public fun vecLdI32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlI32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrInt(off: Int): TTL = vecLdI32(off, this)

public fun ConstPtr.vecLdlFromConstPtrInt(off: Int): TTL = vecLdlI32(off, this)

public fun vecLdF32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvx(addr))
}

public fun vecLdlF32(off: Int, p: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmute(lvxl(addr))
}

public fun ConstPtr.vecLdFromConstPtrFloat(off: Int): TTL = vecLdF32(off, this)

public fun ConstPtr.vecLdlFromConstPtrFloat(off: Int): TTL = vecLdlF32(off, this)

public interface VectorLde<out Result> {
    public fun vecLde(a: Int): Result
}

public fun vecLdeU8(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvebx(addr))
}

public fun ConstPtr.vecLde(a: Int): TTL = vecLdeU8(a, this)

public fun vecLdeI8(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((b).rawAddress + ((a).toLong()))
    return transmute(lvebx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrByte(a: Int): TTL = vecLdeI8(a, this)

public fun vecLdeU16(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvehx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrUShort(a: Int): TTL = vecLdeU16(a, this)

public fun vecLdeI16(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvehx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrShort(a: Int): TTL = vecLdeI16(a, this)

public fun vecLdeU32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvewx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrUInt(a: Int): TTL = vecLdeU32(a, this)

public fun vecLdeI32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvewx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrInt(a: Int): TTL = vecLdeI32(a, this)

public fun vecLdeF32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmute(lvewx(addr))
}

public fun ConstPtr.vecLdeFromConstPtrFloat(a: Int): TTL = vecLdeF32(a, this)

public interface VectorSt<Target> {
    public fun vecSt(off: Int, p: Target)

    public fun vecStl(off: Int, p: Target)
}

public fun vecStU8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun TTL.vecSt(off: Int, p: ConstPtr) {
    vecStU8(this, off, p)
}

public fun TTL.vecStl(off: Int, p: ConstPtr) {
    vecStU8(this, off, p)
}

public fun vecStI8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun vecStU16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun vecStI16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun vecStU32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun vecStI32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun vecStF32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlF32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public interface VectorSte<Target> {
    public fun vecSte(off: Int, p: Target)
}

public fun vecSteU8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvebx(transmute(a), addr)
}

public fun TTL.vecSte(off: Int, p: ConstPtr) {
    vecSteU8(this, off, p)
}

public fun vecSteI8(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvebx(transmute(a), addr)
}

public fun vecSteU16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvehx(transmute(a), addr)
}

public fun vecSteI16(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvehx(transmute(a), addr)
}

public fun vecSteU32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public fun vecSteI32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public fun vecSteF32(a: TTL, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public interface VectorXl<out Result> {
    public fun vecXl(a: Int): Result
}

public fun vecXlI8(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXl(a: Int): TTL = vecXlI8(a, this)

public fun vecXlU8(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((b)).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUByte(a: Int): TTL = vecXlU8(a, this)

public fun vecXlI16(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrShort(a: Int): TTL = vecXlI16(a, this)

public fun vecXlU16(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUShort(a: Int): TTL = vecXlU16(a, this)

public fun vecXlI32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrInt(a: Int): TTL = vecXlI32(a, this)

public fun vecXlU32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUInt(a: Int): TTL = vecXlU32(a, this)

public fun vecXlF32(a: Int, b: ConstPtr): TTL {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(ObjectSlotLayout<TTL>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrFloat(a: Int): TTL = vecXlF32(a, this)

public interface VectorXst<Out> {
    public fun vecXst(a: Int, p: Out)
}

public fun vecXstI8(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun TTL.vecXst(a: Int, b: MutPtr) {
    vecXstI8(this, a, b)
}

public fun vecXstU8(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((b)).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun vecXstI16(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun vecXstU16(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun vecXstI32(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun vecXstU32(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun vecXstF32(s: TTL, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<TTL>(typeLayoutT = ObjectSlotLayout<TTL>()), typeLayoutT = PrimitiveLayouts.U8)
}

public interface VectorCmpGt<Other, out Result> {
    public fun vecCmpgt(b: Other): Result
}

public interface VectorCmpEq<Other, out Result> {
    public fun vecCmpeq(b: Other): Result
}

public fun vecVcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    if (false) {
        transmute(vcmpneb(transmuteWithLayouts(a, VectorSignedChar.typeLayout(), VectorBoolChar.typeLayout()), transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorBoolChar.typeLayout())))
    } else {
        val zero = transmute(i32x4.new(0, 0, 0, 0))
        vecNor(vecCmpeq(a, b), zero)
    }

public fun vecVcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    if (false) {
        transmute(vcmpneh(transmuteWithLayouts(a, VectorSignedShort.typeLayout(), VectorBoolShort.typeLayout()), transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorBoolShort.typeLayout())))
    } else {
        val zero = transmute(i32x4.new(0, 0, 0, 0))
        vecNor(vecCmpeq(a, b), zero)
    }

public fun vecVcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    if (false) {
        transmute(vcmpnew(transmuteWithLayouts(a, VectorSignedInt.typeLayout(), VectorBoolInt.typeLayout()), transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorBoolInt.typeLayout())))
    } else {
        val zero = transmute(i32x4.new(0, 0, 0, 0))
        vecNor(vecCmpeq(a, b), zero)
    }

public interface VectorCmpNe<Other, out Result> {
    public fun vecCmpne(b: Other): Result
}

public fun vcmpequbAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(2, a, b) != 0

public fun vcmpequbAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(1, a, b) != 0

public fun vcmpequhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(2, a, b) != 0

public fun vcmpequhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(1, a, b) != 0

public fun vcmpequwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(2, a, b) != 0

public fun vcmpequwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(1, a, b) != 0

public interface VectorAllEq<Other, out Result> {
    public fun vecAllEq(b: Other): Result
}

// Note: vsx encoding
public fun vcmpeqfpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(2, a, b) != 0

public fun VectorFloat.vecAllEq(b: VectorFloat): Boolean = vcmpeqfpAll(this, b)

public interface VectorAnyEq<Other, out Result> {
    public fun vecAnyEq(b: Other): Result
}

public fun vcmpeqfpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(1, a, b) != 0

public fun VectorFloat.vecAnyEq(b: VectorFloat): Boolean = vcmpeqfpAny(this, b)

// All/Any GreaterEqual
public fun vcmpgesbAll(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(0, b, a) != 0

public fun vcmpgesbAny(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(3, b, a) != 0

public fun vcmpgeshAll(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(0, b, a) != 0

public fun vcmpgeshAny(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(3, b, a) != 0

public fun vcmpgeswAll(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(0, b, a) != 0

public fun vcmpgeswAny(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(3, b, a) != 0

public fun vcmpgeubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(0, b, a) != 0

public fun vcmpgeubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(3, b, a) != 0

public fun vcmpgeuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(0, b, a) != 0

public fun vcmpgeuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(3, b, a) != 0

public fun vcmpgeuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(0, b, a) != 0

public fun vcmpgeuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(3, b, a) != 0

public interface VectorAllGe<Other, out Result> {
    public fun vecAllGe(b: Other): Result
}

// Note: vsx encoding
public fun vcmpgefpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(2, a, b) != 0

public fun VectorFloat.vecAllGe(b: VectorFloat): Boolean = vcmpgefpAll(this, b)

public interface VectorAnyGe<Other, out Result> {
    public fun vecAnyGe(b: Other): Result
}

public fun vcmpgefpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(1, a, b) != 0

public fun VectorFloat.vecAnyGe(b: VectorFloat): Boolean = vcmpgefpAny(this, b)

// All/Any Greater Than
public fun vcmpgtsbAll(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(2, a, b) != 0

public fun vcmpgtsbAny(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(1, a, b) != 0

public fun vcmpgtshAll(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(2, a, b) != 0

public fun vcmpgtshAny(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(1, a, b) != 0

public fun vcmpgtswAll(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(2, a, b) != 0

public fun vcmpgtswAny(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(1, a, b) != 0

public fun vcmpgtubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(2, a, b) != 0

public fun vcmpgtubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(1, a, b) != 0

public fun vcmpgtuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(2, a, b) != 0

public fun vcmpgtuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(1, a, b) != 0

public fun vcmpgtuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(2, a, b) != 0

public fun vcmpgtuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(1, a, b) != 0

public interface VectorAllGt<Other, out Result> {
    public fun vecAllGt(b: Other): Result
}

// Note: vsx encoding
public fun vcmpgtfpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(2, a, b) != 0

public fun VectorFloat.vecAllGt(b: VectorFloat): Boolean = vcmpgtfpAll(this, b)

public interface VectorAnyGt<Other, out Result> {
    public fun vecAnyGt(b: Other): Result
}

public fun vcmpgtfpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(1, a, b) != 0

public fun VectorFloat.vecAnyGt(b: VectorFloat): Boolean = vcmpgtfpAny(this, b)

// All/Any Elements Not Equal
public fun vcmpneubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(0, a, b) != 0

public fun vcmpneubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(3, a, b) != 0

public fun vcmpneuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(0, a, b) != 0

public fun vcmpneuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(3, a, b) != 0

public fun vcmpneuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(0, a, b) != 0

public fun vcmpneuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(3, a, b) != 0

public interface VectorAllNe<Other, out Result> {
    public fun vecAllNe(b: Other): Result
}

// Note: vsx encoding
public fun vcmpnefpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(0, a, b) != 0

public fun VectorFloat.vecAllNe(b: VectorFloat): Boolean = vcmpnefpAll(this, b)

public interface VectorAnyNe<Other, out Result> {
    public fun vecAnyNe(b: Other): Result
}

public fun vcmpnefpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(3, a, b) != 0

public fun VectorFloat.vecAnyNe(b: VectorFloat): Boolean = vcmpnefpAny(this, b)

public interface VectorAvg<Other, out Result> {
    public fun vecAvg(b: Other): Result
}

public fun andc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = transmuteWithLayouts(a, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val b1 = transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    return transmute(run { val _simdArg0 = run { val _simdArg0 = u8x16.splat(0xff); val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = a1; _simdArg0 })
}

public interface VectorAndc<Other, out Result> {
    public fun vecAndc(b: Other): Result
}

public fun orc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = transmuteWithLayouts(a, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val b1 = transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    return transmute(run { val _simdArg0 = run { val _simdArg0 = u8x16.splat(0xff); val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = a1; _simdArg0 })
}

public interface VectorOrc<Other, out Result> {
    public fun vecOrc(b: Other): Result
}

public interface VectorAnd<Other, out Result> {
    public fun vecAnd(b: Other): Result
}

public interface VectorAdds<Other, out Result> {
    public fun vecAdds(b: Other): Result
}

public interface VectorSubs<Other, out Result> {
    public fun vecSubs(b: Other): Result
}

public interface VectorAbs<T> {
    public fun vecAbs(): T
}

public fun vecAbsI8(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsI16(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsI32(v: STL): STL = v.vecMax(run { val _simdArg0 = v; _simdArg0 })

public fun vecAbsF32(v: VectorFloat): VectorFloat {
    val v1: U32x4 = transmuteWithLayouts(v, VectorFloat.typeLayout(), VectorFloat.typeLayout())
    return transmute(run { val _simdArg0 = v1; val _simdArg1 = u32x4.splat(0x7FFFFFFF); _simdArg0 })
}

public interface VectorAbss<T> {
    public fun vecAbss(): T
}

public fun vecAbssI8(v: STL): STL {
    val zero: STL = transmute(0u.toUByte().vecSplats())
    return v.vecMax(zero.vecSubs(v))
}

public fun vecAbssI16(v: STL): STL {
    val zero: STL = transmute(0u.toUByte().vecSplats())
    return v.vecMax(zero.vecSubs(v))
}

public fun vecAbssI32(v: STL): STL {
    val zero: STL = transmute(0u.toUByte().vecSplats())
    return v.vecMax(zero.vecSubs(v))
}

public fun vspltb(a: VectorSignedChar, imm4: UInt): VectorSignedChar {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x16.splat(imm4); _simdArg0 }
}

public fun vsplth(a: VectorSignedShort, imm3: UInt): VectorSignedShort {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x8.splat(imm3); _simdArg0 }
}

public fun vspltw(a: VectorSignedInt, imm2: UInt): VectorSignedInt {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = u32x4.splat(imm2); _simdArg0 }
}

public interface VectorSplat<T> {
    public fun vecSplat(imm: UInt): T
}

public fun VectorSignedChar.vecSplat(imm: UInt): VectorSignedChar = transmute(vspltb(transmute(this), imm))

public fun VectorUnsignedChar.vecSplat(imm: UInt): VectorUnsignedChar = transmute(vspltb(transmute(this), imm))

public fun VectorBoolChar.vecSplat(imm: UInt): VectorBoolChar = transmute(vspltb(transmute(this), imm))

public fun VectorSignedShort.vecSplat(imm: UInt): VectorSignedShort = transmute(vsplth(transmute(this), imm))

public fun VectorUnsignedShort.vecSplat(imm: UInt): VectorUnsignedShort = transmute(vsplth(transmute(this), imm))

public fun VectorBoolShort.vecSplat(imm: UInt): VectorBoolShort = transmute(vsplth(transmute(this), imm))

public fun VectorSignedInt.vecSplat(imm: UInt): VectorSignedInt = transmute(vspltw(transmute(this), imm))

public fun VectorUnsignedInt.vecSplat(imm: UInt): VectorUnsignedInt = transmute(vspltw(transmute(this), imm))

public fun VectorBoolInt.vecSplat(imm: UInt): VectorBoolInt = transmute(vspltw(transmute(this), imm))

public fun splatsU8(v: UByte): STL = transmute(u8x16.splat(v))

public fun splatsU16(v: UShort): STL = transmute(u16x8.splat(v))

public fun splatsU32(v: UInt): STL = transmute(u32x4.splat(v))

public fun splatsI8(v: Byte): STL = transmute(i8x16.splat(v))

public fun splatsI16(v: Short): STL = transmute(i16x8.splat(v))

public fun splatsI32(v: Int): STL = transmute(i32x4.splat(v))

public fun splatsF32(v: Float): STL = transmute(f32x4.splat(v))

public interface VectorSplats<out Result> {
    public fun vecSplats(): Result
}

public interface VectorSub<Other, out Result> {
    public fun vecSub(b: Other): Result
}

public interface VectorSubc<Other, out Result> {
    public fun vecSubc(b: Other): Result
}

public interface VectorMin<Other, out Result> {
    public fun vecMin(b: Other): Result
}

public interface VectorMax<Other, out Result> {
    public fun vecMax(b: Other): Result
}

public fun vecVmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort = vmuleub(a, b)

public fun vecVmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort = vmulesb(a, b)

public fun vecVmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vmuleuh(a, b)

public fun vecVmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt = vmulesh(a, b)

public interface VectorMul<T> {
    public fun vecMul(b: T): T
}

public fun vecVmuluwm(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmute(run { val _simdArg0 = transmuteWithLayouts(a, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout()); val _simdArg1 = transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout()); _simdArg0 })

public fun vecXvmulsp(a: VectorFloat, b: VectorFloat): VectorFloat = transmute(run { val _simdArg0 = transmuteWithLayouts(a, VectorFloat.typeLayout(), VectorFloat.typeLayout()); val _simdArg1 = transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout()); _simdArg0 })

public fun VectorSignedInt.vecMul(b: VectorSignedInt): VectorSignedInt = vecVmuluwm(this, b)

public fun VectorUnsignedInt.vecMul(b: VectorUnsignedInt): VectorUnsignedInt = transmute(run { val _simdArg0 = transmute(this); val _simdArg1 = transmuteWithLayouts(b, VectorUnsignedInt.typeLayout(), VectorUnsignedInt.typeLayout()); _simdArg0 })

public fun VectorFloat.vecMul(b: VectorFloat): VectorFloat = vecXvmulsp(this, b)

public interface VectorMule<Result, T> {
    public fun vecMule(b: T): Result
}

public fun VectorUnsignedChar.vecMule(b: VectorUnsignedChar): VectorUnsignedShort = vmuleub(this, b)

public fun VectorSignedChar.vecMule(b: VectorSignedChar): VectorSignedShort = vmulesb(this, b)

public fun VectorUnsignedShort.vecMule(b: VectorUnsignedShort): VectorUnsignedInt = vmuleuh(this, b)

public fun VectorSignedShort.vecMule(b: VectorSignedShort): VectorSignedInt = vmulesh(this, b)

public fun vecVmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort = vmuloub(a, b)

public fun vecVmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort = vmulosb(a, b)

public fun vecVmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vmulouh(a, b)

public fun vecVmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt = vmulosh(a, b)

public interface VectorMulo<Result, T> {
    public fun vecMulo(b: T): Result
}

public fun VectorUnsignedChar.vecMulo(b: VectorUnsignedChar): VectorUnsignedShort = vmuloub(this, b)

public fun VectorSignedChar.vecMulo(b: VectorSignedChar): VectorSignedShort = vmulosb(this, b)

public fun VectorUnsignedShort.vecMulo(b: VectorUnsignedShort): VectorUnsignedInt = vmulouh(this, b)

public fun VectorSignedShort.vecMulo(b: VectorSignedShort): VectorSignedInt = vmulosh(this, b)

public fun vecVsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt = vsum4ubs(a, b)

public fun vecVsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt = vsum4sbs(a, b)

public fun vecVsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt = vsum4shs(a, b)

public interface VectorSum4s<Other> {
    public fun vecSum4s(b: Other): Other
}

public fun VectorUnsignedChar.vecSum4s(b: VectorUnsignedInt): VectorUnsignedInt = vsum4ubs(this, b)

public fun VectorSignedChar.vecSum4s(b: VectorSignedInt): VectorSignedInt = vsum4sbs(this, b)

public fun VectorSignedShort.vecSum4s(b: VectorSignedInt): VectorSignedInt = vsum4shs(this, b)

public fun vecVsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vsum2sws(a, b)

public fun vecVnmsubfp(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }; _simdArg0 }

public fun vecVmaddfp(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

public fun vecVmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt = vmsumubm(a, b, c)

public fun vecVmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt = vmsummbm(a, b, c)

public fun vecVmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhm(a, b, c)

public fun vecVmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshm(a, b, c)

public interface VectorMsum<B, Other> {
    public fun vecMsum(b: B, c: Other): Other
}

public fun VectorUnsignedChar.vecMsum(b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt = vmsumubm(this, b, c)

public fun VectorSignedChar.vecMsum(b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt = vmsummbm(this, b, c)

public fun VectorUnsignedShort.vecMsum(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhm(this, b, c)

public fun VectorSignedShort.vecMsum(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshm(this, b, c)

public fun vecVmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhs(a, b, c)

public fun vecVmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshs(a, b, c)

public interface VectorMsums<Other, T> {
    public fun vecMsums(b: T, c: Other): Other
}

public fun VectorUnsignedShort.vecMsums(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhs(this, b, c)

public fun VectorSignedShort.vecMsums(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshs(this, b, c)

public fun vecVperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = vperm(a, b, c)

public interface VectorPerm<T> {
    public fun vecVperm(b: T, c: VectorUnsignedChar): T
}

public fun VectorSignedChar.vecVperm(b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout()), c))

public fun VectorUnsignedChar.vecVperm(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorUnsignedChar.typeLayout(), VectorUnsignedChar.typeLayout()), c))

public fun VectorBoolChar.vecVperm(b: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorBoolChar.typeLayout(), VectorBoolChar.typeLayout()), c))

public fun VectorSignedShort.vecVperm(b: VectorSignedShort, c: VectorUnsignedChar): VectorSignedShort = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout()), c))

public fun VectorUnsignedShort.vecVperm(b: VectorUnsignedShort, c: VectorUnsignedChar): VectorUnsignedShort = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorUnsignedShort.typeLayout(), VectorUnsignedShort.typeLayout()), c))

public fun VectorBoolShort.vecVperm(b: VectorBoolShort, c: VectorUnsignedChar): VectorBoolShort = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorBoolShort.typeLayout(), VectorBoolShort.typeLayout()), c))

public fun VectorSignedInt.vecVperm(b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout()), c))

public fun VectorUnsignedInt.vecVperm(b: VectorUnsignedInt, c: VectorUnsignedChar): VectorUnsignedInt = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorUnsignedInt.typeLayout(), VectorUnsignedInt.typeLayout()), c))

public fun VectorBoolInt.vecVperm(b: VectorBoolInt, c: VectorUnsignedChar): VectorBoolInt = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorBoolInt.typeLayout(), VectorBoolInt.typeLayout()), c))

public fun VectorFloat.vecVperm(b: VectorFloat, c: VectorUnsignedChar): VectorFloat = transmute(vecVperm(transmute(this), transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout()), c))

public interface VectorAdd<Other, out Result> {
    public fun vecAdd(other: Other): Result
}

public fun vecAddBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = transmuteWithLayouts(a, VectorBoolChar.typeLayout(), VectorSignedChar.typeLayout()); val _simdArg1 = b; _simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vecAddBcSc(this, other)

public fun VectorSignedChar.vecAdd(other: VectorBoolChar): VectorSignedChar = other.vecAdd(this)

public fun vecAddScSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorSignedChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vecAddScSc(this, other)

public fun vecAddBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val _simdArg0 = transmuteWithLayouts(a, VectorBoolChar.typeLayout(), VectorUnsignedChar.typeLayout()); val _simdArg1 = b; _simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vecAddBcUc(this, other)

public fun VectorUnsignedChar.vecAdd(other: VectorBoolChar): VectorUnsignedChar = other.vecAdd(this)

public fun vecAddUcUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorUnsignedChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vecAddUcUc(this, other)

public fun vecAddBsSs(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort {
    val a1: I16x8 = transmuteWithLayouts(a, VectorBoolShort.typeLayout(), VectorSignedShort.typeLayout())
    val a2: VectorSignedShort = run { val _simdArg0 = a1; VectorSignedShort() }
    return run { val _simdArg0 = a2; val _simdArg1 = b; _simdArg0 }
}

public fun VectorBoolShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vecAddBsSs(this, other)

public fun VectorSignedShort.vecAdd(other: VectorBoolShort): VectorSignedShort = other.vecAdd(this)

public fun vecAddSsSs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorSignedShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vecAddSsSs(this, other)

public fun vecAddBsUs(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1: I16x8 = transmuteWithLayouts(a, VectorBoolShort.typeLayout(), VectorUnsignedShort.typeLayout())
    val a2: VectorUnsignedShort = run { val _simdArg0 = a1; VectorUnsignedShort() }
    return run { val _simdArg0 = a2; val _simdArg1 = b; _simdArg0 }
}

public fun VectorBoolShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vecAddBsUs(this, other)

public fun VectorUnsignedShort.vecAdd(other: VectorBoolShort): VectorUnsignedShort = other.vecAdd(this)

public fun vecAddUsUs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorUnsignedShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vecAddUsUs(this, other)

public fun vecAddBiSi(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt {
    val a1: I32x4 = transmuteWithLayouts(a, VectorBoolInt.typeLayout(), VectorSignedInt.typeLayout())
    val a2: VectorSignedInt = run { val _simdArg0 = a1; VectorSignedInt() }
    return run { val _simdArg0 = a2; val _simdArg1 = b; _simdArg0 }
}

public fun VectorBoolInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vecAddBiSi(this, other)

public fun VectorSignedInt.vecAdd(other: VectorBoolInt): VectorSignedInt = other.vecAdd(this)

public fun vecAddSiSi(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorSignedInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vecAddSiSi(this, other)

public fun vecAddBiUi(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1: I32x4 = transmuteWithLayouts(a, VectorBoolInt.typeLayout(), VectorUnsignedInt.typeLayout())
    val a2: VectorUnsignedInt = run { val _simdArg0 = a1; VectorUnsignedInt() }
    return run { val _simdArg0 = a2; val _simdArg1 = b; _simdArg0 }
}

public fun VectorBoolInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vecAddBiUi(this, other)

public fun VectorUnsignedInt.vecAdd(other: VectorBoolInt): VectorUnsignedInt = other.vecAdd(this)

public fun vecAddUiUi(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorUnsignedInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vecAddUiUi(this, other)

public fun vecAddFloatFloat(a: VectorFloat, b: VectorFloat): VectorFloat = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

public fun VectorFloat.vecAdd(other: VectorFloat): VectorFloat = vecAddFloatFloat(this, other)

public interface VectorAdde<T> {
    public fun vecAdde(b: T, c: T): T
}

public fun VectorUnsignedInt.vecAdde(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt {
    val mask: VectorUnsignedInt = transmute(u32x4.new(1, 1, 1, 1))
    val carry = vecAnd(c, mask)
    return vecAdd(vecAdd(this, b), carry)
}

public fun VectorSignedInt.vecAdde(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt {
    val mask: VectorSignedInt = transmute(i32x4.new(1, 1, 1, 1))
    val carry = vecAnd(c, mask)
    return vecAdd(vecAdd(this, b), carry)
}

public interface VectorMladd<Other, T, out Result> {
    public fun vecMladd(b: Other, c: Other): Result
}

public fun mladd(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a1: I16x8 = transmuteWithLayouts(a, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val b1: I16x8 = transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1: I16x8 = transmuteWithLayouts(c, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }; val _simdArg1 = c1; _simdArg0 })
}

public fun VectorUnsignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorUnsignedShort.typeLayout(), VectorUnsignedShort.typeLayout())
    val c1 = transmuteWithLayouts(c, VectorUnsignedShort.typeLayout(), VectorUnsignedShort.typeLayout())
    return transmute(mladd(a, b1, c1))
}

public fun VectorUnsignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1 = transmuteWithLayouts(c, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    return transmute(mladd(a, b1, c1))
}

public fun VectorSignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorSignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorUnsignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1 = transmuteWithLayouts(c, VectorUnsignedShort.typeLayout(), VectorSignedShort.typeLayout())
    return transmute(mladd(a, b1, c1))
}

public fun VectorSignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1 = transmuteWithLayouts(c, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    return transmute(mladd(a, b1, c1))
}

public interface VectorOr<Other, out Result> {
    public fun vecOr(b: Other): Result
}

public interface VectorXor<Other, out Result> {
    public fun vecXor(b: Other): Result
}

public fun vecVnorsb(a: TTL, b: TTL): TTL {
    val o = vecSplats((0.inv()).toByte())
    return vecXor(vecOr(a, b), o)
}

public fun vecVnorsh(a: TTL, b: TTL): TTL {
    val o = vecSplats((0.inv()).toShort())
    return vecXor(vecOr(a, b), o)
}

public fun vecVnorsw(a: TTL, b: TTL): TTL {
    val o = vecSplats(0.inv())
    return vecXor(vecOr(a, b), o)
}

public interface VectorNor<Other, out Result> {
    public fun vecNor(b: Other): Result
}

public fun vecVnandsb(a: TTL, b: TTL): TTL {
    val o = vecSplats((0.inv()).toByte())
    return vecXor(vecAnd(a, b), o)
}

public fun vecVnandsh(a: TTL, b: TTL): TTL {
    val o = vecSplats((0.inv()).toShort())
    return vecXor(vecAnd(a, b), o)
}

public fun vecVnandsw(a: TTL, b: TTL): TTL {
    val o = vecSplats(0.inv())
    return vecXor(vecAnd(a, b), o)
}

public interface VectorNand<Other, out Result> {
    public fun vecNand(b: Other): Result
}

public fun vecVsel(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar {
    val a1: I8x16 = transmuteWithLayouts(a, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val b1: I8x16 = transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val c1: I8x16 = transmuteWithLayouts(c, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val notC = run { val _simdArg0 = c1; val _simdArg1 = i8x16.splat(0.inv()); _simdArg0 }
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a1; val _simdArg1 = notC; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b1; val _simdArg1 = c1; _simdArg0 }; _simdArg0 })
}

public interface VectorSel<Mask, T> {
    public fun vecSel(b: T, c: Mask): T
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: TB): VectorSignedChar {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: TU): VectorSignedChar {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TU.vecSel(b: TU, c: TB): TU {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TU.vecSel(b: TU, c: TU): TU {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TB.vecSel(b: TB, c: TB): TB {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TB.vecSel(b: TB, c: TU): TB {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: TB): VectorSignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: TU): VectorSignedShort {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TU.vecSelTB(b: TU, c: TB): TU {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TU.vecSelTU(b: TU, c: TU): TU {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TB.vecSelTB(b: TB, c: TB): TB {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun TB.vecSelTU(b: TB, c: TU): TB {
    val a = transmute(this)
    val b1 = transmute(b)
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: TB): VectorSignedInt {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: TU): VectorSignedInt {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorFloat.vecSel(b: VectorFloat, c: TB): VectorFloat {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun VectorFloat.vecSel(b: VectorFloat, c: TU): VectorFloat {
    val a = transmute(this)
    val b1 = transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout())
    val c1 = transmute(c)
    return transmute(vecVsel(a, b1, c1))
}

public fun vecCtfI32(a: VectorSignedInt, imm5: Int): VectorFloat {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcfsx(a, imm5)
}

public fun vecCtfU32(a: VectorUnsignedInt, imm5: Int): VectorFloat {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcfux(a, imm5)
}

public interface VectorCtf {
    public fun vecCtf(imm5: Int): VectorFloat
}

public fun VectorSignedInt.vecCtf(imm5: Int): VectorFloat = vecCtfI32(this, imm5)

public fun VectorUnsignedInt.vecCtf(imm5: Int): VectorFloat = vecCtfU32(this, imm5)

public fun vecVmrglb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val mergelPerm = transmute(u8x16.new(0x08, 0x18, 0x09, 0x19, 0x0A, 0x1A, 0x0B, 0x1B, 0x0C, 0x1C, 0x0D, 0x1D, 0x0E, 0x1E, 0x0F, 0x1F))
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrglh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val mergelPerm = transmute(u8x16.new(0x08, 0x09, 0x18, 0x19, 0x0A, 0x0B, 0x1A, 0x1B, 0x0C, 0x0D, 0x1C, 0x1D, 0x0E, 0x0F, 0x1E, 0x1F))
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrglw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val mergelPerm = transmute(u8x16.new(0x08, 0x09, 0x0A, 0x0B, 0x18, 0x19, 0x1A, 0x1B, 0x0C, 0x0D, 0x0E, 0x0F, 0x1C, 0x1D, 0x1E, 0x1F))
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val mergelPerm = transmute(u8x16.new(0x00, 0x10, 0x01, 0x11, 0x02, 0x12, 0x03, 0x13, 0x04, 0x14, 0x05, 0x15, 0x06, 0x16, 0x07, 0x17))
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val mergelPerm = transmute(u8x16.new(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17))
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val mergelPerm = transmute(u8x16.new(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17))
    return vecPerm(a, b, mergelPerm)
}

public interface VectorMergeh<Other, T, out Result> {
    public fun vecMergeh(b: Other): Result
}

public interface VectorMergel<Other, T, out Result> {
    public fun vecMergel(b: Other): Result
}

public fun vecVpkuhum(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar {
    val packPerm =
        if (true) {
            transmute(u8x16.new(0x00, 0x02, 0x04, 0x06, 0x08, 0x0A, 0x0C, 0x0E, 0x10, 0x12, 0x14, 0x16, 0x18, 0x1A, 0x1C, 0x1E))
        } else {
            transmute(u8x16.new(0x01, 0x03, 0x05, 0x07, 0x09, 0x0B, 0x0D, 0x0F, 0x11, 0x13, 0x15, 0x17, 0x19, 0x1B, 0x1D, 0x1F))
        }
    return transmute(vecPerm(a, b, packPerm))
}

public fun vecVpkuwum(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort {
    val packPerm =
        if (true) {
            transmute(u8x16.new(0x00, 0x01, 0x04, 0x05, 0x08, 0x09, 0x0C, 0x0D, 0x10, 0x11, 0x14, 0x15, 0x18, 0x19, 0x1C, 0x1D))
        } else {
            transmute(u8x16.new(0x02, 0x03, 0x06, 0x07, 0x0A, 0x0B, 0x0E, 0x0F, 0x12, 0x13, 0x16, 0x17, 0x1A, 0x1B, 0x1E, 0x1F))
        }
    return transmute(vecPerm(a, b, packPerm))
}

public interface VectorPack<Other, out Result> {
    public fun vecPack(b: Other): Result
}

public fun vecVpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    if (true) {
        vpkshss(b, a)
    } else {
        vpkshss(a, b)
    }

public fun vecVpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    if (true) {
        vpkshus(b, a)
    } else {
        vpkshus(a, b)
    }

public fun vecVpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    if (true) {
        vpkuhus(b, a)
    } else {
        vpkuhus(a, b)
    }

public fun vecVpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    if (true) {
        vpkswss(b, a)
    } else {
        vpkswss(a, b)
    }

public fun vecVpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    if (true) {
        vpkswus(b, a)
    } else {
        vpkswus(a, b)
    }

public fun vecVpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    if (true) {
        vpkuwus(b, a)
    } else {
        vpkuwus(a, b)
    }

public interface VectorPacks<Other, out Result> {
    public fun vecPacks(b: Other): Result
}

public interface VectorPacksu<Other, out Result> {
    public fun vecPacksu(b: Other): Result
}

public fun vecVupkhsb(a: VectorSignedChar): VectorSignedShort =
    if (true) {
        vupklsb(a)
    } else {
        vupkhsb(a)
    }

public fun vecVupklsb(a: VectorSignedChar): VectorSignedShort =
    if (true) {
        vupkhsb(a)
    } else {
        vupklsb(a)
    }

public fun vecVupkhsh(a: VectorSignedShort): VectorSignedInt =
    if (true) {
        vupklsh(a)
    } else {
        vupkhsh(a)
    }

public fun vecVupklsh(a: VectorSignedShort): VectorSignedInt =
    if (true) {
        vupkhsh(a)
    } else {
        vupklsh(a)
    }

public interface VectorUnpackh<out Result> {
    public fun vecUnpackh(): Result
}

public interface VectorUnpackl<out Result> {
    public fun vecUnpackl(): Result
}

public interface VectorSl<Other, out Result> {
    public fun vecSl(b: Other): Result
}

public fun vslb(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8).toUByte() * 8u.toUByte()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vslh(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16).toUShort() * 16u.toUShort()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vslw(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32).toUInt() * 32u); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public interface VectorSr<Other, out Result> {
    public fun vecSr(b: Other): Result
}

public fun vsrb(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8).toUByte() * 8u.toUByte()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vsrh(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16).toUShort() * 16u.toUShort()); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public fun vsrw(a: TTL, b: TTL): TTL {
    val a1 = transmute(a)
    val b1 = run { val _simdArg0 = transmute(b); val _simdArg1 = TTS.splat(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32).toUInt() * 32u); _simdArg0 }
    return transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
}

public interface VectorSra<Other, out Result> {
    public fun vecSra(b: Other): Result
}

public interface VectorSld<T> {
    public fun vecSld(b: T, uimm4: Int): T

    public fun vecSldw(b: T, uimm2: Int): T
}

public fun vsldoi(a: VectorUnsignedChar, b: VectorUnsignedChar, uimm4: Int): VectorUnsignedChar {
    run {
        check(0 <= uimm4 && uimm4 < (1 shl 4)) { "UIMM4 doesn't fit in 4 bits (unsigned)" }
    }
    val d = uimm4.toUByte()
    return if (true) {
        val perm = u8x16.new(16u - d, 17u - d, 18u - d, 19u - d, 20u - d, 21u - d, 22u - d, 23u - d, 24u - d, 25u - d, 26u - d, 27u - d, 28u - d, 29u - d, 30u - d, 31u - d)
        vecPerm(b, a, transmute(perm))
    } else {
        val perm1 = u8x16.new(d, d + 1u, d + 2u, d + 3u, d + 4u, d + 5u, d + 6u, d + 7u, d + 8u, d + 9u, d + 10u, d + 11u, d + 12u, d + 13u, d + 14u, d + 15u)
        vecPerm(a, b, transmute(perm1))
    }
}

// Note: collapse the two once genericConstExprs are usable.
public fun xxsldwi(a: VectorUnsignedChar, b: VectorUnsignedChar, uimm2: Int): VectorUnsignedChar {
    run {
        check(0 <= uimm2 && uimm2 < (1 shl 2)) { "UIMM2 doesn't fit in 2 bits (unsigned)" }
    }
    val d = (uimm2 shl 2).toUByte()
    return if (true) {
        val perm = u8x16.new(16u - d, 17u - d, 18u - d, 19u - d, 20u - d, 21u - d, 22u - d, 23u - d, 24u - d, 25u - d, 26u - d, 27u - d, 28u - d, 29u - d, 30u - d, 31u - d)
        vecPerm(b, a, transmute(perm))
    } else {
        val perm1 = u8x16.new(d, d + 1u, d + 2u, d + 3u, d + 4u, d + 5u, d + 6u, d + 7u, d + 8u, d + 9u, d + 10u, d + 11u, d + 12u, d + 13u, d + 14u, d + 15u)
        vecPerm(a, b, transmute(perm1))
    }
}

public fun VectorBoolChar.vecSld(b: VectorBoolChar, uimm4: Int): VectorBoolChar = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorBoolChar.typeLayout(), VectorBoolChar.typeLayout()), uimm4))

public fun VectorBoolChar.vecSldw(b: VectorBoolChar, uimm2: Int): VectorBoolChar = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorBoolChar.typeLayout(), VectorBoolChar.typeLayout()), uimm2))

public fun VectorSignedChar.vecSld(b: VectorSignedChar, uimm4: Int): VectorSignedChar = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout()), uimm4))

public fun VectorSignedChar.vecSldw(b: VectorSignedChar, uimm2: Int): VectorSignedChar = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorSignedChar.typeLayout(), VectorSignedChar.typeLayout()), uimm2))

public fun VectorUnsignedChar.vecSld(b: VectorUnsignedChar, uimm4: Int): VectorUnsignedChar = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorUnsignedChar.typeLayout(), VectorUnsignedChar.typeLayout()), uimm4))

public fun VectorUnsignedChar.vecSldw(b: VectorUnsignedChar, uimm2: Int): VectorUnsignedChar = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorUnsignedChar.typeLayout(), VectorUnsignedChar.typeLayout()), uimm2))

public fun VectorBoolShort.vecSld(b: VectorBoolShort, uimm4: Int): VectorBoolShort = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorBoolShort.typeLayout(), VectorBoolShort.typeLayout()), uimm4))

public fun VectorBoolShort.vecSldw(b: VectorBoolShort, uimm2: Int): VectorBoolShort = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorBoolShort.typeLayout(), VectorBoolShort.typeLayout()), uimm2))

public fun VectorSignedShort.vecSld(b: VectorSignedShort, uimm4: Int): VectorSignedShort = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout()), uimm4))

public fun VectorSignedShort.vecSldw(b: VectorSignedShort, uimm2: Int): VectorSignedShort = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorSignedShort.typeLayout(), VectorSignedShort.typeLayout()), uimm2))

public fun VectorUnsignedShort.vecSld(b: VectorUnsignedShort, uimm4: Int): VectorUnsignedShort = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorUnsignedShort.typeLayout(), VectorUnsignedShort.typeLayout()), uimm4))

public fun VectorUnsignedShort.vecSldw(b: VectorUnsignedShort, uimm2: Int): VectorUnsignedShort = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorUnsignedShort.typeLayout(), VectorUnsignedShort.typeLayout()), uimm2))

public fun VectorBoolInt.vecSld(b: VectorBoolInt, uimm4: Int): VectorBoolInt = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorBoolInt.typeLayout(), VectorBoolInt.typeLayout()), uimm4))

public fun VectorBoolInt.vecSldw(b: VectorBoolInt, uimm2: Int): VectorBoolInt = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorBoolInt.typeLayout(), VectorBoolInt.typeLayout()), uimm2))

public fun VectorSignedInt.vecSld(b: VectorSignedInt, uimm4: Int): VectorSignedInt = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout()), uimm4))

public fun VectorSignedInt.vecSldw(b: VectorSignedInt, uimm2: Int): VectorSignedInt = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorSignedInt.typeLayout(), VectorSignedInt.typeLayout()), uimm2))

public fun VectorUnsignedInt.vecSld(b: VectorUnsignedInt, uimm4: Int): VectorUnsignedInt = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorUnsignedInt.typeLayout(), VectorUnsignedInt.typeLayout()), uimm4))

public fun VectorUnsignedInt.vecSldw(b: VectorUnsignedInt, uimm2: Int): VectorUnsignedInt = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorUnsignedInt.typeLayout(), VectorUnsignedInt.typeLayout()), uimm2))

public fun VectorFloat.vecSld(b: VectorFloat, uimm4: Int): VectorFloat = transmute(vsldoi(transmute(this), transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout()), uimm4))

public fun VectorFloat.vecSldw(b: VectorFloat, uimm2: Int): VectorFloat = transmute(xxsldwi(transmute(this), transmuteWithLayouts(b, VectorFloat.typeLayout(), VectorFloat.typeLayout()), uimm2))

public interface VectorSll<Other, out Result> {
    public fun vecSll(b: Other): Result
}

public interface VectorSrl<Other, out Result> {
    public fun vecSrl(b: Other): Result
}

public interface VectorSlo<Other, out Result> {
    public fun vecSlo(b: Other): Result
}

public interface VectorSro<Other, out Result> {
    public fun vecSro(b: Other): Result
}

public interface VectorCntlz<T> {
    public fun vecCntlz(): T
}

public fun VectorSignedChar.vecCntlz(): VectorSignedChar = transmute(vecVcntlzb(transmute(this)))

public fun VectorUnsignedChar.vecCntlz(): VectorUnsignedChar = transmute(vecVcntlzb(transmute(this)))

public fun VectorSignedShort.vecCntlz(): VectorSignedShort = transmute(vecVcntlzh(transmute(this)))

public fun VectorUnsignedShort.vecCntlz(): VectorUnsignedShort = transmute(vecVcntlzh(transmute(this)))

public fun VectorSignedInt.vecCntlz(): VectorSignedInt = transmute(vecVcntlzw(transmute(this)))

public fun VectorUnsignedInt.vecCntlz(): VectorUnsignedInt = transmute(vecVcntlzw(transmute(this)))

public fun vrlb(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun vrlh(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public fun vrlw(a: TTL, b: TTL): TTL = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = b; _simdArg0 }

public interface VectorRl<T, Shift> {
    public fun vecRl(b: Shift): T
}

public fun VectorSignedChar.vecRl(b: TU): VectorSignedChar = transmute(vrlb(transmute(this), b))

public fun VectorSignedShort.vecRl(b: TU): VectorSignedShort = transmute(vrlh(transmute(this), b))

public fun VectorSignedInt.vecRl(b: TU): VectorSignedInt = transmute(vrlw(transmute(this), b))

public fun VectorUnsignedChar.vecRl(b: TU): VectorUnsignedChar = transmute(vrlb(transmute(this), b))

public fun VectorUnsignedShort.vecRl(b: TU): VectorUnsignedShort = transmute(vrlh(transmute(this), b))

public fun VectorUnsignedInt.vecRl(b: TU): VectorUnsignedInt = transmute(vrlw(transmute(this), b))

public interface VectorRound<T> {
    public fun vecRound(): T
}

public fun VectorFloat.vecRound(): VectorFloat = vecVrfin(this)

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
public fun <Scalar, T> vecInsert(a: T, b: Scalar, idx: UInt): T where T : VectorInsert<T, Any?> = a.vecInsert(b)

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
public fun <Scalar, T> vecExtract(a: T, idx: UInt): Scalar where T : VectorExtract<T, Any?> = a.vecExtract()

/**
 * Vector Merge Low
 */
public fun <T, U> vecMergel(a: T, b: U): Result where T : VectorMergel<U, T, Any?> = a.vecMergel(b)

/**
 * Vector Merge High
 */
public fun <T, U> vecMergeh(a: T, b: U): Result where T : VectorMergeh<U, T, Any?> = a.vecMergeh(b)

/**
 * Vector Pack
 */
public fun <T, U> vecPack(a: T, b: U): Result where T : VectorPack<U, Any?> = a.vecPack(b)

/**
 * Vector Pack Saturated
 */
public fun <T, U> vecPacks(a: T, b: U): Result where T : VectorPacks<U, Any?> = a.vecPacks(b)

/**
 * Vector Pack Saturated Unsigned
 */
public fun <T, U> vecPacksu(a: T, b: U): Result where T : VectorPacksu<U, Any?> = a.vecPacksu(b)

/**
 * Vector Unpack High
 */
public fun <Result, T> vecUnpackh(a: T): Result where T : VectorUnpackh<Any?> = a.vecUnpackh()

/**
 * Vector Unpack Low
 */
public fun <Result, T> vecUnpackl(a: T): Result where T : VectorUnpackl<Any?> = a.vecUnpackl()

/**
 * Vector Shift Left
 */
public fun <T, U> vecSl(a: T, b: U): Result where T : VectorSl<U, Any?> = a.vecSl(b)

/**
 * Vector Shift Right
 */
public fun <T, U> vecSr(a: T, b: U): Result where T : VectorSr<U, Any?> = a.vecSr(b)

/**
 * Vector Shift Right Algebraic
 */
public fun <T, U> vecSra(a: T, b: U): Result where T : VectorSra<U, Any?> = a.vecSra(b)

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
public fun <T> vecSld(a: T, b: T, uimm4: Int): T where T : VectorSld<T> = a.vecSld(b)

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
public fun <T> vecSldw(a: T, b: T, uimm2: Int): T where T : VectorSld<T> = a.vecSldw(b)

/**
 * Vector Shift Left Long
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSll in big-endian
 * code must be rewritten for little-endian targets.
 */
public fun <T, U> vecSll(a: T, b: U): Result where T : VectorSll<U, Any?> = a.vecSll(b)

/**
 * Vector Shift Right Long
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSrl in big-endian
 * code must be rewritten for little-endian targets.
 */
public fun <T, U> vecSrl(a: T, b: U): Result where T : VectorSrl<U, Any?> = a.vecSrl(b)

/**
 * Vector Shift Left by Octets
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSlo in big-endian code must be rewritten
 * for little-endian targets. The shift count is in element 15 of b for big-endian, but in element
 * 0 of b for little-endian.
 */
public fun <T, U> vecSlo(a: T, b: U): Result where T : VectorSlo<U, Any?> = a.vecSlo(b)

/**
 * Vector Shift Right by Octets
 *
 * ## Endian considerations
 * This intrinsic is not endian-neutral, so uses of vecSro in big-endian code must be rewritten
 * for little-endian targets. The shift count is in element 15 of b for big-endian, but in element
 * 0 of b for little-endian.
 */
public fun <T, U> vecSro(a: T, b: U): Result where T : VectorSro<U, Any?> = a.vecSro(b)

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
public fun <Result, T> vecLd(off: Int, p: T): Result where T : VectorLd<Any?> = p.vecLd(off)

/**
 * Vector Load Indexed Least Recently Used.
 */
public fun <Result, T> vecLdl(off: Int, p: T): Result where T : VectorLd<Any?> = p.vecLdl(off)

/**
 * Vector Load Element Indexed.
 */
public fun <Result, T> vecLde(off: Int, p: T): Result where T : VectorLde<Any?> = p.vecLde(off)

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
public fun <Target, T> vecSt(a: T, off: Int, c: Target) where T : VectorSt<Any?> {
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
public fun <Target, T> vecStl(a: T, off: Int, c: Target) where T : VectorSt<Any?> {
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
public fun <Target, T> vecSte(a: T, off: Int, c: Target) where T : VectorSte<Any?> {
    a.vecSte(off, c)
}

/**
 * VSX Unaligned Load
 */
public fun <Result, T> vecXl(off: Int, p: T): Result where T : VectorXl<Any?> = p.vecXl(off)

/**
 * VSX Unaligned Store
 */
public fun <Out, T> vecXst(v: T, off: Int, p: Out) where T : VectorXst<Any?> {
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
public fun <T, U> vecCmplt(a: U, b: T): Result where T : VectorCmpGt<U, Any?> = vecCmpgt(b, a)

/**
 * Vector cmple.
 */
public fun vecCmple(a: VectorFloat, b: VectorFloat): VectorBoolInt = vecCmpge(b, a)

/**
 * Vector cmpgt.
 */
public fun <T, U> vecCmpgt(a: T, b: U): Result where T : VectorCmpGt<U, Any?> = a.vecCmpgt(b)

/**
 * Vector cmpge.
 */
public fun vecCmpge(a: VectorFloat, b: VectorFloat): VectorBoolInt = vecVcmpgefp(a, b)

/**
 * Vector cmpeq.
 */
public fun <T, U> vecCmpeq(a: T, b: U): Result where T : VectorCmpEq<U, Any?> = a.vecCmpeq(b)

/**
 * Vector Compare Not Equal
 *
 * ## Result value
 * For each element of r, the value of each bit is 1 if the corresponding elements
 * of a and b are not equal. Otherwise, the value of each bit is 0.
 */
public fun <T, U> vecCmpne(a: T, b: U): Result where T : VectorCmpNe<U, Any?> = a.vecCmpne(b)

/**
 * Vector cmpb.
 */
public fun vecCmpb(a: VectorFloat, b: VectorFloat): VectorSignedInt = vecVcmpbfp(a, b)

/**
 * Vector ceil.
 */
public fun vecCeil(a: VectorFloat): VectorFloat = vecVceil(a)

/**
 * Vector avg.
 */
public fun <T, U> vecAvg(a: T, b: U): Result where T : VectorAvg<U, Any?> = a.vecAvg(b)

/**
 * Vector andc.
 */
public fun <T, U> vecAndc(a: T, b: U): Result where T : VectorAndc<U, Any?> = a.vecAndc(b)

/**
 * Vector OR with Complement
 *
 * ## Purpose
 * Performs a bitwise OR of the first vector with the bitwise-complemented second vector.
 *
 * ## Result value
 * r is the bitwise OR of a and the bitwise complement of b.
 */
public fun <T, U> vecOrc(a: T, b: U): Result where T : VectorOrc<U, Any?> = a.vecOrc(b)

/**
 * Vector and.
 */
public fun <T, U> vecAnd(a: T, b: U): Result where T : VectorAnd<U, Any?> = a.vecAnd(b)

/**
 * Vector or.
 */
public fun <T, U> vecOr(a: T, b: U): Result where T : VectorOr<U, Any?> = a.vecOr(b)

/**
 * Vector NAND
 *
 * ## Purpose
 * Performs a bitwise NAND of two vectors.
 *
 * ## Result value
 * r is the bitwise NAND of a and b.
 */
public fun <T, U> vecNand(a: T, b: U): Result where T : VectorNand<U, Any?> = a.vecNand(b)

/**
 * Vector nor.
 */
public fun <T, U> vecNor(a: T, b: U): Result where T : VectorNor<U, Any?> = a.vecNor(b)

/**
 * Vector xor.
 */
public fun <T, U> vecXor(a: T, b: U): Result where T : VectorXor<U, Any?> = a.vecXor(b)

/**
 * Vector adds.
 */
public fun <T, U> vecAdds(a: T, b: U): Result where T : VectorAdds<U, Any?> = a.vecAdds(b)

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
public fun <Shift, T> vecRl(a: T, b: Shift): T where T : VectorRl<T, Any?> = a.vecRl(b)

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
public fun <T> vecSplat(a: T, imm: UInt): T where T : VectorSplat<T> = a.vecSplat()

/**
 * Vector Splat to Unsigned Byte
 */
public fun vecSplatU8(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(u8x16.splat(imm5.toUByte()))
}

/**
 * Vector Splat to Signed Byte
 */
public fun vecSplatS8(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(i8x16.splat(imm5))
}

/**
 * Vector Splat to Unsigned Halfword
 */
public fun vecSplatU16(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(u16x8.splat(imm5.toUShort()))
}

/**
 * Vector Splat to Signed Halfword
 */
public fun vecSplatS16(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(i16x8.splat(imm5.toShort()))
}

/**
 * Vector Splat to Unsigned Word
 */
public fun vecSplatU32(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(u32x4.splat(imm5.toUInt()))
}

/**
 * Vector Splat to Signed Word
 */
public fun vecSplatS32(imm5: Byte): STL {
    run {
        check((-1 shl (5 - 1).toInt()) - 1 <= imm5 && imm5 < (1 shl (5 - 1).toInt())) { "IMM5 doesn't fit in 5 bits (signed)" }
    }
    return transmute(i32x4.splat(imm5.toInt()))
}

/**
 * Vector splats.
 */
public fun <Result, T> vecSplats(a: T): Result where T : VectorSplats<Any?> = a.vecSplats()

/**
 * Vector sub.
 */
public fun <T, U> vecSub(a: T, b: U): Result where T : VectorSub<U, Any?> = a.vecSub(b)

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
public fun <T, U> vecSubc(a: T, b: U): Result where T : VectorSubc<U, Any?> = a.vecSubc(b)

/**
 * Vector subs.
 */
public fun <T, U> vecSubs(a: T, b: U): Result where T : VectorSubs<U, Any?> = a.vecSubs(b)

/**
 * Vector min.
 */
public fun <T, U> vecMin(a: T, b: U): Result where T : VectorMin<U, Any?> = a.vecMin(b)

/**
 * Vector max.
 */
public fun <T, U> vecMax(a: T, b: U): Result where T : VectorMax<U, Any?> = a.vecMax(b)

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
public fun <T, U> vecAdd(a: T, b: U): Result where T : VectorAdd<U, Any?> = a.vecAdd(b)

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
public fun <T> vecCtf(a: T, imm5: Int): VectorFloat where T : VectorCtf = a.vecCtf()

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
 * Vector permute.
 */
public fun <T> vecPerm(a: T, b: T, c: VectorUnsignedChar): T where T : VectorPerm<T> {
    // vperm has big-endian bias
    //
    // Xor the mask and flip the arguments
    val d = transmute(u8x16.new(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255))
    val c1 = run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    return b.vecVperm(a, c1)
}

/**
 * Vector Sum Across Partial (1/2) Saturated
 */
public fun vecSum2s(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    // vsum2sws has big-endian bias
    //
    // swap the even b elements with the odd ones
    val flip = transmute(u8x16.new(4, 5, 6, 7, 0, 1, 2, 3, 12, 13, 14, 15, 8, 9, 10, 11))
    val b1 = vecPerm(b, b, flip)
    val c = vsum2sws(a, b1)
    return vecPerm(c, c, flip)
}

// Even and Odd are swapped in little-endian

/**
 * Vector Multiply Even
 */
public fun <T, U> vecMule(a: T, b: T): U where T : VectorMulo<U, T> = a.vecMulo(b)

/**
 * Vector Multiply Odd
 */
public fun <T, U> vecMulo(a: T, b: T): U where T : VectorMule<U, T> = a.vecMule(b)

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
public fun <T, U> vecMladd(a: T, b: U, c: U): Result where T : VectorMladd<U, T, Any?> = a.vecMladd(b, c)

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
public fun <T, U> vecAllEq(a: T, b: U): Result where T : VectorAllEq<U, Any?> = a.vecAllEq(b)

/**
 * Vector All Elements Equal
 */
public fun <T, U> vecAnyEq(a: T, b: U): Result where T : VectorAnyEq<U, Any?> = a.vecAnyEq(b)

/**
 * Vector All Elements Greater or Equal
 */
public fun <T, U> vecAllGe(a: T, b: U): Result where T : VectorAllGe<U, Any?> = a.vecAllGe(b)

/**
 * Vector Any Element Greater or Equal
 */
public fun <T, U> vecAnyGe(a: T, b: U): Result where T : VectorAnyGe<U, Any?> = a.vecAnyGe(b)

/**
 * Vector All Elements Greater Than
 */
public fun <T, U> vecAllGt(a: T, b: U): Result where T : VectorAllGt<U, Any?> = a.vecAllGt(b)

/**
 * Vector Any Element Greater Than
 */
public fun <T, U> vecAnyGt(a: T, b: U): Result where T : VectorAnyGt<U, Any?> = a.vecAnyGt(b)

/**
 * Vector All In
 */
public fun vecAllIn(a: VectorFloat, b: VectorFloat): Boolean = vcmpbfpP(0, a, b) != 0

/**
 * Vector All Elements Less Than or Equal
 */
public fun <T, U> vecAllLe(a: U, b: T): Result where T : VectorAllGe<U, Any?> = b.vecAllGe(a)

/**
 * Vector Any Element Less Than or Equal
 */
public fun <T, U> vecAnyLe(a: U, b: T): Result where T : VectorAnyGe<U, Any?> = b.vecAnyGe(a)

/**
 * Vector All Elements Less Than
 */
public fun <T, U> vecAllLt(a: U, b: T): Result where T : VectorAllGt<U, Any?> = b.vecAllGt(a)

/**
 * Vector Any Element Less Than
 */
public fun <T, U> vecAnyLt(a: U, b: T): Result where T : VectorAnyGt<U, Any?> = b.vecAnyGt(a)

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
public fun <T, U> vecAllNe(a: T, b: U): Result where T : VectorAllNe<U, Any?> = a.vecAllNe(b)

/**
 * Vector Any Elements Not Equal
 */
public fun <T, U> vecAnyNe(a: T, b: U): Result where T : VectorAnyNe<U, Any?> = a.vecAnyNe(b)

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

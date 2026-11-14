package io.github.kotlinmania.rustlang.core.mem

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicCxchgU128Pair
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicCxchgU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicCxchgU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicCxchgU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicCxchgU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicLoadU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicLoadU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicLoadU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicRmwU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicRmwU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicRmwU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicRmwU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicStoreU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicStoreU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicStoreU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicXchgU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicXchgU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicXchgU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAtomicXchgU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadF32
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadF64
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreF32
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreF64
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileLoadU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileLoadU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileLoadU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileStoreU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileStoreU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmVolatileStoreU8
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun alignUpBytes(offset: Long, align: Long): Long {
    if (align <= 1L) return offset
    val rem = offset % align
    return if (rem == 0L) offset else offset + (align - rem)
}

public fun kmBytesLoadU8(buf: ByteArray, offset: Int): UByte = buf[offset].toUByte()

public fun kmBytesStoreU8(buf: ByteArray, offset: Int, value: UByte) {
    buf[offset] = value.toByte()
}

public fun kmBytesLoadU16Le(buf: ByteArray, offset: Int): UShort =
    ((buf[offset].toInt() and 0xFF) or ((buf[offset + 1].toInt() and 0xFF) shl 8)).toUShort()

public fun kmBytesStoreU16Le(buf: ByteArray, offset: Int, value: UShort) {
    val v = value.toInt()
    buf[offset] = v.toByte()
    buf[offset + 1] = (v ushr 8).toByte()
}

public fun kmBytesLoadU32Le(buf: ByteArray, offset: Int): UInt =
    (
        (buf[offset].toInt() and 0xFF) or
            ((buf[offset + 1].toInt() and 0xFF) shl 8) or
            ((buf[offset + 2].toInt() and 0xFF) shl 16) or
            ((buf[offset + 3].toInt() and 0xFF) shl 24)
    ).toUInt()

public fun kmBytesStoreU32Le(buf: ByteArray, offset: Int, value: UInt) {
    val v = value.toInt()
    buf[offset] = v.toByte()
    buf[offset + 1] = (v ushr 8).toByte()
    buf[offset + 2] = (v ushr 16).toByte()
    buf[offset + 3] = (v ushr 24).toByte()
}

public fun kmBytesLoadU64Le(buf: ByteArray, offset: Int): ULong =
    (
        (buf[offset].toLong() and 0xFFL) or
            ((buf[offset + 1].toLong() and 0xFFL) shl 8) or
            ((buf[offset + 2].toLong() and 0xFFL) shl 16) or
            ((buf[offset + 3].toLong() and 0xFFL) shl 24) or
            ((buf[offset + 4].toLong() and 0xFFL) shl 32) or
            ((buf[offset + 5].toLong() and 0xFFL) shl 40) or
            ((buf[offset + 6].toLong() and 0xFFL) shl 48) or
            ((buf[offset + 7].toLong() and 0xFFL) shl 56)
    ).toULong()

public fun kmBytesStoreU64Le(buf: ByteArray, offset: Int, value: ULong) {
    val v = value.toLong()
    buf[offset] = v.toByte()
    buf[offset + 1] = (v ushr 8).toByte()
    buf[offset + 2] = (v ushr 16).toByte()
    buf[offset + 3] = (v ushr 24).toByte()
    buf[offset + 4] = (v ushr 32).toByte()
    buf[offset + 5] = (v ushr 40).toByte()
    buf[offset + 6] = (v ushr 48).toByte()
    buf[offset + 7] = (v ushr 56).toByte()
}

public fun kmBytesLoadF32Le(buf: ByteArray, offset: Int): Float =
    Float.fromBits(kmBytesLoadU32Le(buf, offset).toInt())

public fun kmBytesStoreF32Le(buf: ByteArray, offset: Int, value: Float) {
    kmBytesStoreU32Le(buf, offset, value.toRawBits().toUInt())
}

public fun kmBytesLoadF64Le(buf: ByteArray, offset: Int): Double =
    Double.fromBits(kmBytesLoadU64Le(buf, offset).toLong())

public fun kmBytesStoreF64Le(buf: ByteArray, offset: Int, value: Double) {
    kmBytesStoreU64Le(buf, offset, value.toRawBits().toULong())
}

public interface TypeLayout<T> {
    public val sizeBytes: Long
    public val alignBytes: Long
    public val strideBytes: Long
        get() = alignUpBytes(sizeBytes, alignBytes)
    public val isGcObjectSlot: Boolean
        get() = false

    public fun readNative(addr: Long): T

    public fun writeNative(addr: Long, value: T)

    public fun readBytes(buf: ByteArray, offset: Int): T {
        val bytes = maxOf(1L, sizeBytes)
        val align = maxOf(1L, alignBytes)
        val scratch = kmAlloc(bytes, align)
        val n = minOf(sizeBytes.toInt(), buf.size - offset)
        for (i in 0 until n) {
            kmStoreU8(scratch + i.toLong(), buf[offset + i].toUByte())
        }
        val out = readNative(scratch)
        kmDealloc(scratch, bytes, align)
        return out
    }

    public fun writeBytes(buf: ByteArray, offset: Int, value: T) {
        val bytes = maxOf(1L, sizeBytes)
        val align = maxOf(1L, alignBytes)
        val scratch = kmAlloc(bytes, align)
        writeNative(scratch, value)
        val n = minOf(sizeBytes.toInt(), buf.size - offset)
        for (i in 0 until n) {
            buf[offset + i] = kmLoadU8(scratch + i.toLong()).toByte()
        }
        kmDealloc(scratch, bytes, align)
    }

    public fun readVolatileNative(addr: Long): T = readNative(addr)

    public fun writeVolatileNative(addr: Long, value: T): Unit = writeNative(addr, value)

    public fun toBitsU64(value: T): ULong {
        val scratch = kmAlloc(8L, 8L)
        kmStoreU64(scratch, 0uL)
        writeNative(scratch, value)
        val bits = kmLoadU64(scratch)
        kmDealloc(scratch, 8L, 8L)
        return bits
    }

    public fun fromBitsU64(bits: ULong): T {
        val scratch = kmAlloc(8L, 8L)
        kmStoreU64(scratch, bits)
        val out = readNative(scratch)
        kmDealloc(scratch, 8L, 8L)
        return out
    }
}

public object PrimitiveLayouts {
    public val UNIT: TypeLayout<Unit> =
        object : TypeLayout<Unit> {
            override val sizeBytes: Long = 0L
            override val alignBytes: Long = 1L
            override fun readNative(addr: Long) {}
            override fun writeNative(addr: Long, value: Unit) {}
            override fun readBytes(buf: ByteArray, offset: Int) {}
            override fun writeBytes(buf: ByteArray, offset: Int, value: Unit) {}
            override fun toBitsU64(value: Unit): ULong = 0uL
            override fun fromBitsU64(bits: ULong) {}
        }

    public val BOOL: TypeLayout<Boolean> =
        object : TypeLayout<Boolean> {
            override val sizeBytes: Long = 1L
            override val alignBytes: Long = 1L
            override fun readNative(addr: Long): Boolean = kmLoadU8(addr) != 0u.toUByte()
            override fun writeNative(addr: Long, value: Boolean): Unit = kmStoreU8(addr, if (value) 1u else 0u)
            override fun readBytes(buf: ByteArray, offset: Int): Boolean = buf[offset] != 0.toByte()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Boolean) {
                buf[offset] = if (value) 1 else 0
            }
            override fun readVolatileNative(addr: Long): Boolean = kmVolatileLoadU8(addr) != 0u.toUByte()
            override fun writeVolatileNative(addr: Long, value: Boolean): Unit = kmVolatileStoreU8(addr, if (value) 1u else 0u)
            override fun toBitsU64(value: Boolean): ULong = if (value) 1uL else 0uL
            override fun fromBitsU64(bits: ULong): Boolean = (bits and 0xFFuL) != 0uL
        }

    public val I8: TypeLayout<Byte> =
        object : TypeLayout<Byte> {
            override val sizeBytes: Long = 1L
            override val alignBytes: Long = 1L
            override fun readNative(addr: Long): Byte = kmLoadU8(addr).toByte()
            override fun writeNative(addr: Long, value: Byte): Unit = kmStoreU8(addr, value.toUByte())
            override fun readBytes(buf: ByteArray, offset: Int): Byte = buf[offset]
            override fun writeBytes(buf: ByteArray, offset: Int, value: Byte) {
                buf[offset] = value
            }
            override fun readVolatileNative(addr: Long): Byte = kmVolatileLoadU8(addr).toByte()
            override fun writeVolatileNative(addr: Long, value: Byte): Unit = kmVolatileStoreU8(addr, value.toUByte())
            override fun toBitsU64(value: Byte): ULong = value.toUByte().toULong()
            override fun fromBitsU64(bits: ULong): Byte = bits.toUByte().toByte()
        }

    public val U8: TypeLayout<UByte> =
        object : TypeLayout<UByte> {
            override val sizeBytes: Long = 1L
            override val alignBytes: Long = 1L
            override fun readNative(addr: Long): UByte = kmLoadU8(addr)
            override fun writeNative(addr: Long, value: UByte): Unit = kmStoreU8(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): UByte = buf[offset].toUByte()
            override fun writeBytes(buf: ByteArray, offset: Int, value: UByte) {
                buf[offset] = value.toByte()
            }
            override fun readVolatileNative(addr: Long): UByte = kmVolatileLoadU8(addr)
            override fun writeVolatileNative(addr: Long, value: UByte): Unit = kmVolatileStoreU8(addr, value)
            override fun toBitsU64(value: UByte): ULong = value.toULong()
            override fun fromBitsU64(bits: ULong): UByte = bits.toUByte()
        }

    public val I16: TypeLayout<Short> =
        object : TypeLayout<Short> {
            override val sizeBytes: Long = 2L
            override val alignBytes: Long = 2L
            override fun readNative(addr: Long): Short = kmLoadU16(addr).toShort()
            override fun writeNative(addr: Long, value: Short): Unit = kmStoreU16(addr, value.toUShort())
            override fun readBytes(buf: ByteArray, offset: Int): Short = kmBytesLoadU16Le(buf, offset).toShort()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Short): Unit =
                kmBytesStoreU16Le(buf, offset, value.toUShort())
            override fun readVolatileNative(addr: Long): Short = kmVolatileLoadU16(addr).toShort()
            override fun writeVolatileNative(addr: Long, value: Short): Unit = kmVolatileStoreU16(addr, value.toUShort())
            override fun toBitsU64(value: Short): ULong = value.toUShort().toULong()
            override fun fromBitsU64(bits: ULong): Short = bits.toUShort().toShort()
        }

    public val U16: TypeLayout<UShort> =
        object : TypeLayout<UShort> {
            override val sizeBytes: Long = 2L
            override val alignBytes: Long = 2L
            override fun readNative(addr: Long): UShort = kmLoadU16(addr)
            override fun writeNative(addr: Long, value: UShort): Unit = kmStoreU16(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): UShort = kmBytesLoadU16Le(buf, offset)
            override fun writeBytes(buf: ByteArray, offset: Int, value: UShort): Unit =
                kmBytesStoreU16Le(buf, offset, value)
            override fun readVolatileNative(addr: Long): UShort = kmVolatileLoadU16(addr)
            override fun writeVolatileNative(addr: Long, value: UShort): Unit = kmVolatileStoreU16(addr, value)
            override fun toBitsU64(value: UShort): ULong = value.toULong()
            override fun fromBitsU64(bits: ULong): UShort = bits.toUShort()
        }

    public val I32: TypeLayout<Int> =
        object : TypeLayout<Int> {
            override val sizeBytes: Long = 4L
            override val alignBytes: Long = 4L
            override fun readNative(addr: Long): Int = kmLoadU32(addr).toInt()
            override fun writeNative(addr: Long, value: Int): Unit = kmStoreU32(addr, value.toUInt())
            override fun readBytes(buf: ByteArray, offset: Int): Int = kmBytesLoadU32Le(buf, offset).toInt()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Int): Unit =
                kmBytesStoreU32Le(buf, offset, value.toUInt())
            override fun readVolatileNative(addr: Long): Int = kmVolatileLoadU32(addr).toInt()
            override fun writeVolatileNative(addr: Long, value: Int): Unit = kmVolatileStoreU32(addr, value.toUInt())
            override fun toBitsU64(value: Int): ULong = value.toUInt().toULong()
            override fun fromBitsU64(bits: ULong): Int = bits.toUInt().toInt()
        }

    public val U32: TypeLayout<UInt> =
        object : TypeLayout<UInt> {
            override val sizeBytes: Long = 4L
            override val alignBytes: Long = 4L
            override fun readNative(addr: Long): UInt = kmLoadU32(addr)
            override fun writeNative(addr: Long, value: UInt): Unit = kmStoreU32(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): UInt = kmBytesLoadU32Le(buf, offset)
            override fun writeBytes(buf: ByteArray, offset: Int, value: UInt): Unit =
                kmBytesStoreU32Le(buf, offset, value)
            override fun readVolatileNative(addr: Long): UInt = kmVolatileLoadU32(addr)
            override fun writeVolatileNative(addr: Long, value: UInt): Unit = kmVolatileStoreU32(addr, value)
            override fun toBitsU64(value: UInt): ULong = value.toULong()
            override fun fromBitsU64(bits: ULong): UInt = bits.toUInt()
        }

    public val CHAR: TypeLayout<Char> =
        object : TypeLayout<Char> {
            override val sizeBytes: Long = 4L
            override val alignBytes: Long = 4L
            override fun readNative(addr: Long): Char = kmLoadU32(addr).toInt().toChar()
            override fun writeNative(addr: Long, value: Char): Unit = kmStoreU32(addr, value.code.toUInt())
            override fun readBytes(buf: ByteArray, offset: Int): Char = kmBytesLoadU32Le(buf, offset).toInt().toChar()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Char): Unit =
                kmBytesStoreU32Le(buf, offset, value.code.toUInt())
            override fun readVolatileNative(addr: Long): Char = kmVolatileLoadU32(addr).toInt().toChar()
            override fun writeVolatileNative(addr: Long, value: Char): Unit = kmVolatileStoreU32(addr, value.code.toUInt())
            override fun toBitsU64(value: Char): ULong = value.code.toUInt().toULong()
            override fun fromBitsU64(bits: ULong): Char = bits.toUInt().toInt().toChar()
        }

    public val F32: TypeLayout<Float> =
        object : TypeLayout<Float> {
            override val sizeBytes: Long = 4L
            override val alignBytes: Long = 4L
            override fun readNative(addr: Long): Float = kmLoadF32(addr)
            override fun writeNative(addr: Long, value: Float): Unit = kmStoreF32(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): Float = Float.fromBits(kmBytesLoadU32Le(buf, offset).toInt())
            override fun writeBytes(buf: ByteArray, offset: Int, value: Float): Unit =
                kmBytesStoreU32Le(buf, offset, value.toRawBits().toUInt())
            override fun readVolatileNative(addr: Long): Float = Float.fromBits(kmVolatileLoadU32(addr).toInt())
            override fun writeVolatileNative(addr: Long, value: Float): Unit = kmVolatileStoreU32(addr, value.toRawBits().toUInt())
            override fun toBitsU64(value: Float): ULong = value.toRawBits().toUInt().toULong()
            override fun fromBitsU64(bits: ULong): Float = Float.fromBits(bits.toUInt().toInt())
        }

    public val I64: TypeLayout<Long> =
        object : TypeLayout<Long> {
            override val sizeBytes: Long = 8L
            override val alignBytes: Long = 8L
            override fun readNative(addr: Long): Long = kmLoadU64(addr).toLong()
            override fun writeNative(addr: Long, value: Long): Unit = kmStoreU64(addr, value.toULong())
            override fun readBytes(buf: ByteArray, offset: Int): Long = kmBytesLoadU64Le(buf, offset).toLong()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Long): Unit =
                kmBytesStoreU64Le(buf, offset, value.toULong())
            override fun readVolatileNative(addr: Long): Long = kmVolatileLoadU64(addr).toLong()
            override fun writeVolatileNative(addr: Long, value: Long): Unit = kmVolatileStoreU64(addr, value.toULong())
            override fun toBitsU64(value: Long): ULong = value.toULong()
            override fun fromBitsU64(bits: ULong): Long = bits.toLong()
        }

    public val U64: TypeLayout<ULong> =
        object : TypeLayout<ULong> {
            override val sizeBytes: Long = 8L
            override val alignBytes: Long = 8L
            override fun readNative(addr: Long): ULong = kmLoadU64(addr)
            override fun writeNative(addr: Long, value: ULong): Unit = kmStoreU64(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): ULong = kmBytesLoadU64Le(buf, offset)
            override fun writeBytes(buf: ByteArray, offset: Int, value: ULong): Unit =
                kmBytesStoreU64Le(buf, offset, value)
            override fun readVolatileNative(addr: Long): ULong = kmVolatileLoadU64(addr)
            override fun writeVolatileNative(addr: Long, value: ULong): Unit = kmVolatileStoreU64(addr, value)
            override fun toBitsU64(value: ULong): ULong = value
            override fun fromBitsU64(bits: ULong): ULong = bits
        }

    public val F64: TypeLayout<Double> =
        object : TypeLayout<Double> {
            override val sizeBytes: Long = 8L
            override val alignBytes: Long = 8L
            override fun readNative(addr: Long): Double = kmLoadF64(addr)
            override fun writeNative(addr: Long, value: Double): Unit = kmStoreF64(addr, value)
            override fun readBytes(buf: ByteArray, offset: Int): Double = Double.fromBits(kmBytesLoadU64Le(buf, offset).toLong())
            override fun writeBytes(buf: ByteArray, offset: Int, value: Double): Unit =
                kmBytesStoreU64Le(buf, offset, value.toRawBits().toULong())
            override fun readVolatileNative(addr: Long): Double = Double.fromBits(kmVolatileLoadU64(addr).toLong())
            override fun writeVolatileNative(addr: Long, value: Double): Unit = kmVolatileStoreU64(addr, value.toRawBits().toULong())
            override fun toBitsU64(value: Double): ULong = value.toRawBits().toULong()
            override fun fromBitsU64(bits: ULong): Double = Double.fromBits(bits.toLong())
        }

    public val F16_LAYOUT: TypeLayout<F16> =
        object : TypeLayout<F16> {
            override val sizeBytes: Long = 2L
            override val alignBytes: Long = 2L
            override fun readNative(addr: Long): F16 = F16.fromBits(kmLoadU16(addr))
            override fun writeNative(addr: Long, value: F16): Unit = kmStoreU16(addr, value.toBits())
            override fun readBytes(buf: ByteArray, offset: Int): F16 = F16.fromBits(kmBytesLoadU16Le(buf, offset))
            override fun writeBytes(buf: ByteArray, offset: Int, value: F16): Unit =
                kmBytesStoreU16Le(buf, offset, value.toBits())
            override fun readVolatileNative(addr: Long): F16 = F16.fromBits(kmVolatileLoadU16(addr))
            override fun writeVolatileNative(addr: Long, value: F16): Unit = kmVolatileStoreU16(addr, value.toBits())
            override fun toBitsU64(value: F16): ULong = value.toBits().toULong()
            override fun fromBitsU64(bits: ULong): F16 = F16.fromBits(bits.toUShort())
        }

    public val F128_LAYOUT: TypeLayout<F128> = u128Layout(::F128, { it.high }, { it.low })

    public val ISIZE64: TypeLayout<Int> =
        object : TypeLayout<Int> {
            override val sizeBytes: Long = 8L
            override val alignBytes: Long = 8L
            override fun readNative(addr: Long): Int = kmLoadU64(addr).toLong().toInt()
            override fun writeNative(addr: Long, value: Int): Unit = kmStoreU64(addr, value.toLong().toULong())
            override fun readBytes(buf: ByteArray, offset: Int): Int = kmBytesLoadU64Le(buf, offset).toLong().toInt()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Int): Unit =
                kmBytesStoreU64Le(buf, offset, value.toLong().toULong())
            override fun readVolatileNative(addr: Long): Int = kmVolatileLoadU64(addr).toLong().toInt()
            override fun writeVolatileNative(addr: Long, value: Int): Unit = kmVolatileStoreU64(addr, value.toLong().toULong())
            override fun toBitsU64(value: Int): ULong = value.toLong().toULong()
            override fun fromBitsU64(bits: ULong): Int = bits.toLong().toInt()
        }

    public val USIZE64: TypeLayout<Int> =
        object : TypeLayout<Int> {
            override val sizeBytes: Long = 8L
            override val alignBytes: Long = 8L
            override fun readNative(addr: Long): Int = kmLoadU64(addr).toInt()
            override fun writeNative(addr: Long, value: Int): Unit = kmStoreU64(addr, value.toUInt().toULong())
            override fun readBytes(buf: ByteArray, offset: Int): Int = kmBytesLoadU64Le(buf, offset).toInt()
            override fun writeBytes(buf: ByteArray, offset: Int, value: Int): Unit =
                kmBytesStoreU64Le(buf, offset, value.toUInt().toULong())
            override fun readVolatileNative(addr: Long): Int = kmVolatileLoadU64(addr).toInt()
            override fun writeVolatileNative(addr: Long, value: Int): Unit = kmVolatileStoreU64(addr, value.toUInt().toULong())
            override fun toBitsU64(value: Int): ULong = value.toUInt().toULong()
            override fun fromBitsU64(bits: ULong): Int = bits.toInt()
        }

    public val ISIZE32: TypeLayout<Int> = I32
    public val USIZE32: TypeLayout<Int> = I32

    public fun constPtrLayout(pointerWidthBits: Long = 64L): TypeLayout<ConstPtr> {
        val bytes = pointerWidthBits / 8L
        return object : TypeLayout<ConstPtr> {
            override val sizeBytes: Long = bytes
            override val alignBytes: Long = bytes
            override fun readNative(addr: Long): ConstPtr {
                val raw = if (bytes == 8L) kmLoadU64(addr).toLong() else kmLoadU32(addr).toLong()
                return ConstPtr(raw)
            }
            override fun writeNative(addr: Long, value: ConstPtr) {
                if (bytes == 8L) {
                    kmStoreU64(addr, value.rawAddress.toULong())
                } else {
                    kmStoreU32(addr, value.rawAddress.toUInt())
                }
            }
            override fun readBytes(buf: ByteArray, offset: Int): ConstPtr {
                val raw = if (bytes == 8L) kmBytesLoadU64Le(buf, offset).toLong() else kmBytesLoadU32Le(buf, offset).toLong()
                return ConstPtr(raw)
            }
            override fun writeBytes(buf: ByteArray, offset: Int, value: ConstPtr) {
                if (bytes == 8L) {
                    kmBytesStoreU64Le(buf, offset, value.rawAddress.toULong())
                } else {
                    kmBytesStoreU32Le(buf, offset, value.rawAddress.toUInt())
                }
            }
            override fun toBitsU64(value: ConstPtr): ULong = value.rawAddress.toULong()
            override fun fromBitsU64(bits: ULong): ConstPtr = ConstPtr(bits.toLong())
        }
    }

    public fun mutPtrLayout(pointerWidthBits: Long = 64L): TypeLayout<MutPtr> {
        val bytes = pointerWidthBits / 8L
        return object : TypeLayout<MutPtr> {
            override val sizeBytes: Long = bytes
            override val alignBytes: Long = bytes
            override fun readNative(addr: Long): MutPtr {
                val raw = if (bytes == 8L) kmLoadU64(addr).toLong() else kmLoadU32(addr).toLong()
                return MutPtr(raw)
            }
            override fun writeNative(addr: Long, value: MutPtr) {
                if (bytes == 8L) {
                    kmStoreU64(addr, value.rawAddress.toULong())
                } else {
                    kmStoreU32(addr, value.rawAddress.toUInt())
                }
            }
            override fun readBytes(buf: ByteArray, offset: Int): MutPtr {
                val raw = if (bytes == 8L) kmBytesLoadU64Le(buf, offset).toLong() else kmBytesLoadU32Le(buf, offset).toLong()
                return MutPtr(raw)
            }
            override fun writeBytes(buf: ByteArray, offset: Int, value: MutPtr) {
                if (bytes == 8L) {
                    kmBytesStoreU64Le(buf, offset, value.rawAddress.toULong())
                } else {
                    kmBytesStoreU32Le(buf, offset, value.rawAddress.toUInt())
                }
            }
            override fun toBitsU64(value: MutPtr): ULong = value.rawAddress.toULong()
            override fun fromBitsU64(bits: ULong): MutPtr = MutPtr(bits.toLong())
        }
    }

    public fun <T> mutableReferenceLayout(
        innerLayout: TypeLayout<T>,
        pointerWidthBits: Long = 64L,
    ): TypeLayout<RustMutableReference<T>> {
        val bytes = pointerWidthBits / 8L
        return object : TypeLayout<RustMutableReference<T>> {
            override val sizeBytes: Long = bytes
            override val alignBytes: Long = bytes
            override fun readNative(addr: Long): RustMutableReference<T> {
                val raw = if (bytes == 8L) kmLoadU64(addr).toLong() else kmLoadU32(addr).toLong()
                return RustMutableReference(MutPtr(raw), innerLayout)
            }
            override fun writeNative(addr: Long, value: RustMutableReference<T>) {
                if (bytes == 8L) {
                    kmStoreU64(addr, value.pointer.rawAddress.toULong())
                } else {
                    kmStoreU32(addr, value.pointer.rawAddress.toUInt())
                }
            }
            override fun readBytes(buf: ByteArray, offset: Int): RustMutableReference<T> {
                val raw = if (bytes == 8L) kmBytesLoadU64Le(buf, offset).toLong() else kmBytesLoadU32Le(buf, offset).toLong()
                return RustMutableReference(MutPtr(raw), innerLayout)
            }
            override fun writeBytes(buf: ByteArray, offset: Int, value: RustMutableReference<T>) {
                if (bytes == 8L) {
                    kmBytesStoreU64Le(buf, offset, value.pointer.rawAddress.toULong())
                } else {
                    kmBytesStoreU32Le(buf, offset, value.pointer.rawAddress.toUInt())
                }
            }
            override fun toBitsU64(value: RustMutableReference<T>): ULong = value.pointer.rawAddress.toULong()
            override fun fromBitsU64(bits: ULong): RustMutableReference<T> =
                RustMutableReference(MutPtr(bits.toLong()), innerLayout)
        }
    }

    public fun <T> u128Layout(
        construct: (ULong, ULong) -> T,
        highOf: (T) -> ULong,
        lowOf: (T) -> ULong,
    ): TypeLayout<T> =
        object : TypeLayout<T> {
            override val sizeBytes: Long = 16L
            override val alignBytes: Long = 16L
            override fun readNative(addr: Long): T {
                val lo = kmLoadU64(addr)
                val hi = kmLoadU64(addr + 8L)
                return construct(hi, lo)
            }
            override fun writeNative(addr: Long, value: T) {
                kmStoreU64(addr, lowOf(value))
                kmStoreU64(addr + 8L, highOf(value))
            }
            override fun readBytes(buf: ByteArray, offset: Int): T {
                val lo = kmBytesLoadU64Le(buf, offset)
                val hi = kmBytesLoadU64Le(buf, offset + 8)
                return construct(hi, lo)
            }
            override fun writeBytes(buf: ByteArray, offset: Int, value: T) {
                kmBytesStoreU64Le(buf, offset, lowOf(value))
                kmBytesStoreU64Le(buf, offset + 8, highOf(value))
            }
        }

    public fun <T> i128Layout(
        construct: (Long, ULong) -> T,
        highOf: (T) -> Long,
        lowOf: (T) -> ULong,
    ): TypeLayout<T> =
        object : TypeLayout<T> {
            override val sizeBytes: Long = 16L
            override val alignBytes: Long = 16L
            override fun readNative(addr: Long): T {
                val lo = kmLoadU64(addr)
                val hi = kmLoadU64(addr + 8L).toLong()
                return construct(hi, lo)
            }
            override fun writeNative(addr: Long, value: T) {
                kmStoreU64(addr, lowOf(value))
                kmStoreU64(addr + 8L, highOf(value).toULong())
            }
            override fun readBytes(buf: ByteArray, offset: Int): T {
                val lo = kmBytesLoadU64Le(buf, offset)
                val hi = kmBytesLoadU64Le(buf, offset + 8).toLong()
                return construct(hi, lo)
            }
            override fun writeBytes(buf: ByteArray, offset: Int, value: T) {
                kmBytesStoreU64Le(buf, offset, lowOf(value))
                kmBytesStoreU64Le(buf, offset + 8, highOf(value).toULong())
            }
        }
}

public fun <T> allocateNativeCell(
    value: T,
    layout: TypeLayout<T>,
): MutPtr {
    val bytes = maxOf(1L, layout.sizeBytes)
    val align = maxOf(1L, layout.alignBytes)
    val addr = kmAlloc(bytes, align)
    layout.writeNative(addr, value)
    return MutPtr(addr)
}

public class TransparentTypeLayout<T, F>
    private constructor(
        public val inner: TypeLayout<F>,
        public val project: (MutPtr, TypeLayout<F>) -> T,
        public val unwrap: (T) -> F,
        private val gcWrap: ((F) -> T)?,
    ) : TypeLayout<T> {
        public constructor(
            inner: TypeLayout<F>,
            project: (MutPtr, TypeLayout<F>) -> T,
            unwrap: (T) -> F,
        ) : this(
            inner = inner,
            project = project,
            unwrap = unwrap,
            gcWrap = null,
        )

        public constructor(
            inner: TypeLayout<F>,
            wrap: (F) -> T,
            unwrap: (T) -> F,
        ) : this(
            inner = inner,
            project = { ptr, layout -> wrap(layout.readNative(ptr.rawAddress)) },
            unwrap = unwrap,
            gcWrap = wrap,
        )

        override val sizeBytes: Long get() = inner.sizeBytes
        override val alignBytes: Long get() = inner.alignBytes
        override fun readNative(addr: Long): T = project(MutPtr(addr), inner)
        override fun writeNative(addr: Long, value: T): Unit = inner.writeNative(addr, unwrap(value))
        override fun readVolatileNative(addr: Long): T = project(MutPtr(addr), inner)
        override fun writeVolatileNative(addr: Long, value: T): Unit = inner.writeVolatileNative(addr, unwrap(value))
        override fun readBytes(buf: ByteArray, offset: Int): T =
            if (gcWrap != null) gcWrap.invoke(inner.readBytes(buf, offset)) else super.readBytes(buf, offset)
        override fun writeBytes(buf: ByteArray, offset: Int, value: T): Unit =
            inner.writeBytes(buf, offset, unwrap(value))
        override fun toBitsU64(value: T): ULong = inner.toBitsU64(unwrap(value))
        override fun fromBitsU64(bits: ULong): T {
            val cell = allocateNativeCell(inner.fromBitsU64(bits), inner)
            return project(cell, inner)
        }
    }

public class AlignedTypeLayout<T>(
    public val inner: TypeLayout<T>,
    override val alignBytes: Long,
) : TypeLayout<T> {
    override val sizeBytes: Long get() = alignUpBytes(inner.sizeBytes, alignBytes)
    override val isGcObjectSlot: Boolean get() = inner.isGcObjectSlot
    override fun readNative(addr: Long): T = inner.readNative(addr)
    override fun writeNative(addr: Long, value: T): Unit = inner.writeNative(addr, value)
    override fun readVolatileNative(addr: Long): T = inner.readVolatileNative(addr)
    override fun writeVolatileNative(addr: Long, value: T): Unit = inner.writeVolatileNative(addr, value)
    override fun readBytes(buf: ByteArray, offset: Int): T = inner.readBytes(buf, offset)
    override fun writeBytes(buf: ByteArray, offset: Int, value: T): Unit = inner.writeBytes(buf, offset, value)
    override fun toBitsU64(value: T): ULong = inner.toBitsU64(value)
    override fun fromBitsU64(bits: ULong): T = inner.fromBitsU64(bits)
}

public class FixedArrayTypeLayout<T>(
    public val elementLayout: TypeLayout<T>,
    public val length: Int,
) : TypeLayout<List<T>> {
    override val sizeBytes: Long = length.toLong() * elementLayout.strideBytes
    override val alignBytes: Long = maxOf(1L, elementLayout.alignBytes)
    override val isGcObjectSlot: Boolean get() = elementLayout.isGcObjectSlot

    override fun readNative(addr: Long): List<T> {
        val stride = elementLayout.strideBytes
        return List(length) { index ->
            elementLayout.readNative(addr + index.toLong() * stride)
        }
    }

    override fun writeNative(addr: Long, value: List<T>) {
        val stride = elementLayout.strideBytes
        val limit = minOf(length, value.size)
        for (index in 0 until limit) {
            elementLayout.writeNative(addr + index.toLong() * stride, value[index])
        }
    }

    override fun readBytes(buf: ByteArray, offset: Int): List<T> {
        val stride = elementLayout.strideBytes.toInt()
        return List(length) { index ->
            elementLayout.readBytes(buf, offset + index * stride)
        }
    }

    override fun writeBytes(buf: ByteArray, offset: Int, value: List<T>) {
        val stride = elementLayout.strideBytes.toInt()
        val limit = minOf(length, value.size)
        for (index in 0 until limit) {
            elementLayout.writeBytes(buf, offset + index * stride, value[index])
        }
    }
}

public fun <T> fixedListTypeLayout(
    elementLayout: TypeLayout<T>,
    length: Int,
): TypeLayout<List<T>> = FixedArrayTypeLayout(elementLayout, length)

private object ManagedSlotRegistry {
    private var nextHandleId: Long = 1L
    private val slotTable: MutableMap<Long, Any?> = linkedMapOf()
    private val freeIds: MutableList<Long> = mutableListOf()

    fun store(value: Any?): Long {
        val id = if (freeIds.isNotEmpty()) freeIds.removeAt(freeIds.size - 1) else nextHandleId++
        slotTable[id] = value
        return id
    }

    fun load(id: Long): Any? = slotTable[id]

    fun release(id: Long) {
        if (slotTable.containsKey(id)) {
            slotTable.remove(id)
            freeIds.add(id)
        }
    }
}

public class ObjectSlotLayout<T> : TypeLayout<T> {
    override val sizeBytes: Long = 8L
    override val alignBytes: Long = 8L
    override val isGcObjectSlot: Boolean = true

    @Suppress("UNCHECKED_CAST")
    override fun readNative(addr: Long): T {
        val handleId = kmLoadU64(addr).toLong()
        return ManagedSlotRegistry.load(handleId) as T
    }

    override fun writeNative(addr: Long, value: T) {
        val handleId = ManagedSlotRegistry.store(value)
        kmStoreU64(addr, handleId.toULong())
    }

    @Suppress("UNCHECKED_CAST")
    override fun readBytes(buf: ByteArray, offset: Int): T {
        val handleId = kmBytesLoadU64Le(buf, offset).toLong()
        return ManagedSlotRegistry.load(handleId) as T
    }

    override fun writeBytes(buf: ByteArray, offset: Int, value: T) {
        val handleId = ManagedSlotRegistry.store(value)
        kmBytesStoreU64Le(buf, offset, handleId.toULong())
    }
}

public object PrimitiveArrayLayouts {
    private fun <A> arrayToBitsU64(
        layout: TypeLayout<A>,
        value: A,
    ): ULong {
        if (layout.sizeBytes in 0L..8L) {
            val buf = ByteArray(8)
            layout.writeBytes(buf, 0, value)
            return kmBytesLoadU64Le(buf, 0)
        }
        val scratch = kmAlloc(8L, 8L)
        kmStoreU64(scratch, 0uL)
        layout.writeNative(scratch, value)
        val bits = kmLoadU64(scratch)
        kmDealloc(scratch, 8L, 8L)
        return bits
    }

    private fun <A> arrayFromBitsU64(
        layout: TypeLayout<A>,
        bits: ULong,
    ): A {
        if (layout.sizeBytes in 0L..8L) {
            val buf = ByteArray(8)
            kmBytesStoreU64Le(buf, 0, bits)
            return layout.readBytes(buf, 0)
        }
        val scratch = kmAlloc(8L, 8L)
        kmStoreU64(scratch, bits)
        val out = layout.readNative(scratch)
        kmDealloc(scratch, 8L, 8L)
        return out
    }

    public fun booleanArray(length: Int): TypeLayout<BooleanArray> =
        object : TypeLayout<BooleanArray> {
            override val sizeBytes: Long = length.toLong()
            override val alignBytes: Long = 1L

            override fun readNative(addr: Long): BooleanArray =
                BooleanArray(length) { i -> kmLoadU8(addr + i.toLong()) != 0u.toUByte() }

            override fun writeNative(addr: Long, value: BooleanArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU8(addr + i.toLong(), if (value[i]) 1u else 0u)
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): BooleanArray =
                BooleanArray(length) { i -> buf[offset + i] != 0.toByte() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: BooleanArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    buf[offset + i] = if (value[i]) 1 else 0
                }
            }

            override fun toBitsU64(value: BooleanArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): BooleanArray = arrayFromBitsU64(this, bits)
        }

    public fun byteArray(length: Int): TypeLayout<ByteArray> =
        object : TypeLayout<ByteArray> {
            override val sizeBytes: Long = length.toLong()
            override val alignBytes: Long = 1L

            override fun readNative(addr: Long): ByteArray =
                ByteArray(length) { i -> kmLoadU8(addr + i.toLong()).toByte() }

            override fun writeNative(addr: Long, value: ByteArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU8(addr + i.toLong(), value[i].toUByte())
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): ByteArray {
                val out = ByteArray(length)
                val n = minOf(length, maxOf(0, buf.size - offset))
                if (n > 0) {
                    buf.copyInto(out, destinationOffset = 0, startIndex = offset, endIndex = offset + n)
                }
                return out
            }

            override fun writeBytes(buf: ByteArray, offset: Int, value: ByteArray) {
                val n = minOf(length, value.size)
                if (n > 0) {
                    value.copyInto(buf, destinationOffset = offset, startIndex = 0, endIndex = n)
                }
            }

            override fun toBitsU64(value: ByteArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): ByteArray = arrayFromBitsU64(this, bits)
        }

    @OptIn(ExperimentalUnsignedTypes::class)
    public fun uByteArray(length: Int): TypeLayout<UByteArray> =
        object : TypeLayout<UByteArray> {
            override val sizeBytes: Long = length.toLong()
            override val alignBytes: Long = 1L

            override fun readNative(addr: Long): UByteArray =
                UByteArray(length) { i -> kmLoadU8(addr + i.toLong()) }

            override fun writeNative(addr: Long, value: UByteArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU8(addr + i.toLong(), value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): UByteArray {
                val out = ByteArray(length)
                val n = minOf(length, maxOf(0, buf.size - offset))
                if (n > 0) {
                    buf.copyInto(out, destinationOffset = 0, startIndex = offset, endIndex = offset + n)
                }
                return out.asUByteArray()
            }

            override fun writeBytes(buf: ByteArray, offset: Int, value: UByteArray) {
                val n = minOf(length, value.size)
                if (n > 0) {
                    value.asByteArray().copyInto(buf, destinationOffset = offset, startIndex = 0, endIndex = n)
                }
            }

            override fun toBitsU64(value: UByteArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): UByteArray = arrayFromBitsU64(this, bits)
        }

    public fun shortArray(length: Int): TypeLayout<ShortArray> =
        object : TypeLayout<ShortArray> {
            override val sizeBytes: Long = length.toLong() * 2L
            override val alignBytes: Long = 2L

            override fun readNative(addr: Long): ShortArray =
                ShortArray(length) { i -> kmLoadU16(addr + i.toLong() * 2L).toShort() }

            override fun writeNative(addr: Long, value: ShortArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU16(addr + i.toLong() * 2L, value[i].toUShort())
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): ShortArray =
                ShortArray(length) { i -> kmBytesLoadU16Le(buf, offset + i * 2).toShort() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: ShortArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU16Le(buf, offset + i * 2, value[i].toUShort())
                }
            }

            override fun toBitsU64(value: ShortArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): ShortArray = arrayFromBitsU64(this, bits)
        }

    @OptIn(ExperimentalUnsignedTypes::class)
    public fun uShortArray(length: Int): TypeLayout<UShortArray> =
        object : TypeLayout<UShortArray> {
            override val sizeBytes: Long = length.toLong() * 2L
            override val alignBytes: Long = 2L

            override fun readNative(addr: Long): UShortArray =
                UShortArray(length) { i -> kmLoadU16(addr + i.toLong() * 2L) }

            override fun writeNative(addr: Long, value: UShortArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU16(addr + i.toLong() * 2L, value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): UShortArray =
                UShortArray(length) { i -> kmBytesLoadU16Le(buf, offset + i * 2) }

            override fun writeBytes(buf: ByteArray, offset: Int, value: UShortArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU16Le(buf, offset + i * 2, value[i])
                }
            }

            override fun toBitsU64(value: UShortArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): UShortArray = arrayFromBitsU64(this, bits)
        }

    public fun intArray(length: Int): TypeLayout<IntArray> =
        object : TypeLayout<IntArray> {
            override val sizeBytes: Long = length.toLong() * 4L
            override val alignBytes: Long = 4L

            override fun readNative(addr: Long): IntArray =
                IntArray(length) { i -> kmLoadU32(addr + i.toLong() * 4L).toInt() }

            override fun writeNative(addr: Long, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU32(addr + i.toLong() * 4L, value[i].toUInt())
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): IntArray =
                IntArray(length) { i -> kmBytesLoadU32Le(buf, offset + i * 4).toInt() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU32Le(buf, offset + i * 4, value[i].toUInt())
                }
            }

            override fun toBitsU64(value: IntArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): IntArray = arrayFromBitsU64(this, bits)
        }

    @OptIn(ExperimentalUnsignedTypes::class)
    public fun uIntArray(length: Int): TypeLayout<UIntArray> =
        object : TypeLayout<UIntArray> {
            override val sizeBytes: Long = length.toLong() * 4L
            override val alignBytes: Long = 4L

            override fun readNative(addr: Long): UIntArray =
                UIntArray(length) { i -> kmLoadU32(addr + i.toLong() * 4L) }

            override fun writeNative(addr: Long, value: UIntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU32(addr + i.toLong() * 4L, value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): UIntArray =
                UIntArray(length) { i -> kmBytesLoadU32Le(buf, offset + i * 4) }

            override fun writeBytes(buf: ByteArray, offset: Int, value: UIntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU32Le(buf, offset + i * 4, value[i])
                }
            }

            override fun toBitsU64(value: UIntArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): UIntArray = arrayFromBitsU64(this, bits)
        }

    public fun longArray(length: Int): TypeLayout<LongArray> =
        object : TypeLayout<LongArray> {
            override val sizeBytes: Long = length.toLong() * 8L
            override val alignBytes: Long = 8L

            override fun readNative(addr: Long): LongArray =
                LongArray(length) { i -> kmLoadU64(addr + i.toLong() * 8L).toLong() }

            override fun writeNative(addr: Long, value: LongArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU64(addr + i.toLong() * 8L, value[i].toULong())
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): LongArray =
                LongArray(length) { i -> kmBytesLoadU64Le(buf, offset + i * 8).toLong() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: LongArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU64Le(buf, offset + i * 8, value[i].toULong())
                }
            }

            override fun toBitsU64(value: LongArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): LongArray = arrayFromBitsU64(this, bits)
        }

    @OptIn(ExperimentalUnsignedTypes::class)
    public fun uLongArray(length: Int): TypeLayout<ULongArray> =
        object : TypeLayout<ULongArray> {
            override val sizeBytes: Long = length.toLong() * 8L
            override val alignBytes: Long = 8L

            override fun readNative(addr: Long): ULongArray =
                ULongArray(length) { i -> kmLoadU64(addr + i.toLong() * 8L) }

            override fun writeNative(addr: Long, value: ULongArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU64(addr + i.toLong() * 8L, value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): ULongArray =
                ULongArray(length) { i -> kmBytesLoadU64Le(buf, offset + i * 8) }

            override fun writeBytes(buf: ByteArray, offset: Int, value: ULongArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU64Le(buf, offset + i * 8, value[i])
                }
            }

            override fun toBitsU64(value: ULongArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): ULongArray = arrayFromBitsU64(this, bits)
        }

    public fun floatArray(length: Int): TypeLayout<FloatArray> =
        object : TypeLayout<FloatArray> {
            override val sizeBytes: Long = length.toLong() * 4L
            override val alignBytes: Long = 4L

            override fun readNative(addr: Long): FloatArray =
                FloatArray(length) { i -> kmLoadF32(addr + i.toLong() * 4L) }

            override fun writeNative(addr: Long, value: FloatArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreF32(addr + i.toLong() * 4L, value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): FloatArray =
                FloatArray(length) { i -> kmBytesLoadF32Le(buf, offset + i * 4) }

            override fun writeBytes(buf: ByteArray, offset: Int, value: FloatArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreF32Le(buf, offset + i * 4, value[i])
                }
            }

            override fun toBitsU64(value: FloatArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): FloatArray = arrayFromBitsU64(this, bits)
        }

    public fun doubleArray(length: Int): TypeLayout<DoubleArray> =
        object : TypeLayout<DoubleArray> {
            override val sizeBytes: Long = length.toLong() * 8L
            override val alignBytes: Long = 8L

            override fun readNative(addr: Long): DoubleArray =
                DoubleArray(length) { i -> kmLoadF64(addr + i.toLong() * 8L) }

            override fun writeNative(addr: Long, value: DoubleArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreF64(addr + i.toLong() * 8L, value[i])
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): DoubleArray =
                DoubleArray(length) { i -> kmBytesLoadF64Le(buf, offset + i * 8) }

            override fun writeBytes(buf: ByteArray, offset: Int, value: DoubleArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreF64Le(buf, offset + i * 8, value[i])
                }
            }

            override fun toBitsU64(value: DoubleArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): DoubleArray = arrayFromBitsU64(this, bits)
        }

    public fun usize64Array(length: Int): TypeLayout<IntArray> =
        object : TypeLayout<IntArray> {
            override val sizeBytes: Long = length.toLong() * 8L
            override val alignBytes: Long = 8L

            override fun readNative(addr: Long): IntArray =
                IntArray(length) { i -> kmLoadU64(addr + i.toLong() * 8L).toInt() }

            override fun writeNative(addr: Long, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU64(addr + i.toLong() * 8L, value[i].toLong().toULong() and 0xFFFF_FFFFuL)
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): IntArray =
                IntArray(length) { i -> kmBytesLoadU64Le(buf, offset + i * 8).toInt() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU64Le(buf, offset + i * 8, value[i].toLong().toULong() and 0xFFFF_FFFFuL)
                }
            }

            override fun toBitsU64(value: IntArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): IntArray = arrayFromBitsU64(this, bits)
        }

    public fun isize64Array(length: Int): TypeLayout<IntArray> =
        object : TypeLayout<IntArray> {
            override val sizeBytes: Long = length.toLong() * 8L
            override val alignBytes: Long = 8L

            override fun readNative(addr: Long): IntArray =
                IntArray(length) { i -> kmLoadU64(addr + i.toLong() * 8L).toInt() }

            override fun writeNative(addr: Long, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmStoreU64(addr + i.toLong() * 8L, value[i].toLong().toULong())
                }
            }

            override fun readBytes(buf: ByteArray, offset: Int): IntArray =
                IntArray(length) { i -> kmBytesLoadU64Le(buf, offset + i * 8).toInt() }

            override fun writeBytes(buf: ByteArray, offset: Int, value: IntArray) {
                val n = minOf(length, value.size)
                for (i in 0 until n) {
                    kmBytesStoreU64Le(buf, offset + i * 8, value[i].toLong().toULong())
                }
            }

            override fun toBitsU64(value: IntArray): ULong = arrayToBitsU64(this, value)

            override fun fromBitsU64(bits: ULong): IntArray = arrayFromBitsU64(this, bits)
        }
}

public fun mutPtrFromRustCast(value: Any?): MutPtr =
    when (value) {
        is ConstPtr -> MutPtr(value.rawAddress, value.rawMeta)
        is MutPtr -> MutPtr(value.rawAddress, value.rawMeta)
        is RustMutableReference<*> -> MutPtr(value.pointer.rawAddress, value.pointer.rawMeta)
        is AddressableLocal<*> -> MutPtr(value.rawAddress)
        is AddressableArrayLocal<*> -> MutPtr(value.rawAddress, value.length.toLong())
        else -> {
            val addr = kmAlloc(8L, 8L)
            val handleId = ManagedSlotRegistry.store(value)
            kmStoreU64(addr, handleId.toULong())
            val meta = if (value is Collection<*>) value.size.toLong() else 0L
            MutPtr(addr, meta)
        }
    }

public fun constPtrFromRustCast(value: Any?): ConstPtr {
    val mutPtr = mutPtrFromRustCast(value)
    return ConstPtr(mutPtr.rawAddress, mutPtr.rawMeta)
}

public class AddressableLocal<T>(
    initialValue: T,
    public val layout: TypeLayout<T>,
) {
    public val rawAddress: Long =
        kmAlloc(maxOf(1L, layout.sizeBytes), maxOf(1L, layout.alignBytes)).also { addr ->
            layout.writeNative(addr, initialValue)
        }

    public operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): T =
        layout.readNative(rawAddress)

    public operator fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: T) {
        layout.writeNative(rawAddress, value)
    }

    public fun asConstPtr(): ConstPtr = ConstPtr(rawAddress)

    public fun asMutPtr(): MutPtr = MutPtr(rawAddress)
}

public class AddressableArrayLocal<A>(
    initialValue: A,
    public val layout: TypeLayout<A>,
    public val length: Int,
    public val elementStrideBytes: Long,
) {
    public val rawAddress: Long =
        kmAlloc(maxOf(1L, layout.sizeBytes), maxOf(1L, layout.alignBytes)).also { addr ->
            layout.writeNative(addr, initialValue)
        }

    public operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): A =
        layout.readNative(rawAddress)

    public operator fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: A) {
        layout.writeNative(rawAddress, value)
    }

    public fun asConstPtr(): ConstPtr = ConstPtr(rawAddress)

    public fun asMutPtr(): MutPtr = MutPtr(rawAddress)

    public fun elementConstPtr(index: Int = 0): ConstPtr =
        ConstPtr(rawAddress + index.toLong() * elementStrideBytes)

    public fun elementMutPtr(index: Int = 0): MutPtr =
        MutPtr(rawAddress + index.toLong() * elementStrideBytes)

    public companion object {
        public fun fromBooleanArray(values: BooleanArray): AddressableArrayLocal<BooleanArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.booleanArray(values.size), values.size, 1L)

        public fun fromByteArray(values: ByteArray): AddressableArrayLocal<ByteArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.byteArray(values.size), values.size, 1L)

        @OptIn(ExperimentalUnsignedTypes::class)
        public fun fromUByteArray(values: UByteArray): AddressableArrayLocal<UByteArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.uByteArray(values.size), values.size, 1L)

        public fun fromShortArray(values: ShortArray): AddressableArrayLocal<ShortArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.shortArray(values.size), values.size, 2L)

        @OptIn(ExperimentalUnsignedTypes::class)
        public fun fromUShortArray(values: UShortArray): AddressableArrayLocal<UShortArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.uShortArray(values.size), values.size, 2L)

        public fun fromIntArray(values: IntArray): AddressableArrayLocal<IntArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.intArray(values.size), values.size, 4L)

        public fun fromUsize64Array(values: IntArray): AddressableArrayLocal<IntArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.usize64Array(values.size), values.size, 8L)

        public fun fromIsize64Array(values: IntArray): AddressableArrayLocal<IntArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.isize64Array(values.size), values.size, 8L)

        @OptIn(ExperimentalUnsignedTypes::class)
        public fun fromUIntArray(values: UIntArray): AddressableArrayLocal<UIntArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.uIntArray(values.size), values.size, 4L)

        public fun fromLongArray(values: LongArray): AddressableArrayLocal<LongArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.longArray(values.size), values.size, 8L)

        @OptIn(ExperimentalUnsignedTypes::class)
        public fun fromULongArray(values: ULongArray): AddressableArrayLocal<ULongArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.uLongArray(values.size), values.size, 8L)

        public fun fromFloatArray(values: FloatArray): AddressableArrayLocal<FloatArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.floatArray(values.size), values.size, 4L)

        public fun fromDoubleArray(values: DoubleArray): AddressableArrayLocal<DoubleArray> =
            AddressableArrayLocal(values, PrimitiveArrayLayouts.doubleArray(values.size), values.size, 8L)

        public fun <E> fromList(
            values: List<E>,
            elementLayout: TypeLayout<E>,
        ): AddressableArrayLocal<List<E>> =
            AddressableArrayLocal(
                values,
                fixedListTypeLayout(elementLayout, values.size),
                values.size,
                elementLayout.strideBytes,
            )
    }
}

public class RustMutableReference<T>(
    public val pointer: MutPtr,
    public val layout: TypeLayout<T>,
) {
    public fun read(): T = layout.readNative(pointer.rawAddress)

    public fun write(value: T) {
        layout.writeNative(pointer.rawAddress, value)
    }

    public fun reborrow(): RustMutableReference<T> = RustMutableReference(pointer, layout)

    public fun asMutPtr(): MutPtr = pointer

    public fun asConstPtr(): ConstPtr = ConstPtr(pointer.rawAddress)
}

public fun <Src, Dst> transmuteWithLayouts(
    value: Src,
    srcLayout: TypeLayout<Src>,
    dstLayout: TypeLayout<Dst>,
): Dst {
    if (srcLayout.sizeBytes == dstLayout.sizeBytes && srcLayout.sizeBytes in 1L..8L) {
        return dstLayout.fromBitsU64(srcLayout.toBitsU64(value))
    }
    val bytes = maxOf(1L, maxOf(srcLayout.sizeBytes, dstLayout.sizeBytes))
    val align = maxOf(1L, maxOf(srcLayout.alignBytes, dstLayout.alignBytes))
    val scratch = kmAlloc(bytes, align)
    srcLayout.writeNative(scratch, value)
    val out = dstLayout.readNative(scratch)
    kmDealloc(scratch, bytes, align)
    return out
}

public fun <T> copyMemoryValues(
    src: ConstPtr,
    dst: MutPtr,
    count: Long,
    layout: TypeLayout<T>,
) {
    kmCopyNonoverlapping(dst.rawAddress, src.rawAddress, count * layout.sizeBytes)
}

public fun <T> copyOverlappingMemoryValues(
    src: ConstPtr,
    dst: MutPtr,
    count: Long,
    layout: TypeLayout<T>,
) {
    kmCopy(dst.rawAddress, src.rawAddress, count * layout.sizeBytes)
}

public fun <T> atomicLoadWithLayout(
    addr: Long,
    ord: Int,
    layout: TypeLayout<T>,
): T =
    when (layout.sizeBytes) {
        1L -> layout.fromBitsU64(kmAtomicLoadU8(addr, ord).toULong())
        2L -> layout.fromBitsU64(kmAtomicLoadU16(addr, ord).toULong())
        4L -> layout.fromBitsU64(kmAtomicLoadU32(addr, ord).toULong())
        8L -> layout.fromBitsU64(kmAtomicLoadU64(addr, ord))
        16L -> {
            val res = kmAtomicCxchgU128Pair(addr, 0uL, 0uL, 0uL, 0uL)
            val scratch = kmAlloc(16L, 16L)
            kmStoreU64(scratch, res.prevLo)
            kmStoreU64(scratch + 8L, res.prevHi)
            val out = layout.readNative(scratch)
            kmDealloc(scratch, 16L, 16L)
            out
        }
        else -> error("Unsupported atomic load width: " + layout.sizeBytes)
    }

public fun <T> atomicStoreWithLayout(
    addr: Long,
    value: T,
    ord: Int,
    layout: TypeLayout<T>,
) {
    when (layout.sizeBytes) {
        1L -> kmAtomicStoreU8(addr, layout.toBitsU64(value).toUByte(), ord)
        2L -> kmAtomicStoreU16(addr, layout.toBitsU64(value).toUShort(), ord)
        4L -> kmAtomicStoreU32(addr, layout.toBitsU64(value).toUInt(), ord)
        8L -> kmAtomicStoreU64(addr, layout.toBitsU64(value), ord)
        16L -> {
            val scratch = kmAlloc(16L, 16L)
            layout.writeNative(scratch, value)
            val desLo = kmLoadU64(scratch)
            val desHi = kmLoadU64(scratch + 8L)
            kmDealloc(scratch, 16L, 16L)
            var cur = kmAtomicCxchgU128Pair(addr, 0uL, 0uL, desLo, desHi)
            while (!cur.succeeded) {
                cur = kmAtomicCxchgU128Pair(addr, cur.prevLo, cur.prevHi, desLo, desHi)
            }
        }
        else -> error("Unsupported atomic store width: " + layout.sizeBytes)
    }
}

public fun <T> atomicXchgWithLayout(
    addr: Long,
    value: T,
    ord: Int,
    layout: TypeLayout<T>,
): T =
    when (layout.sizeBytes) {
        1L -> layout.fromBitsU64(kmAtomicXchgU8(addr, layout.toBitsU64(value).toUByte(), ord).toULong())
        2L -> layout.fromBitsU64(kmAtomicXchgU16(addr, layout.toBitsU64(value).toUShort(), ord).toULong())
        4L -> layout.fromBitsU64(kmAtomicXchgU32(addr, layout.toBitsU64(value).toUInt(), ord).toULong())
        8L -> layout.fromBitsU64(kmAtomicXchgU64(addr, layout.toBitsU64(value), ord))
        16L -> {
            val scratch = kmAlloc(16L, 16L)
            layout.writeNative(scratch, value)
            val desLo = kmLoadU64(scratch)
            val desHi = kmLoadU64(scratch + 8L)
            var cur = kmAtomicCxchgU128Pair(addr, 0uL, 0uL, desLo, desHi)
            while (!cur.succeeded) {
                cur = kmAtomicCxchgU128Pair(addr, cur.prevLo, cur.prevHi, desLo, desHi)
            }
            kmStoreU64(scratch, cur.prevLo)
            kmStoreU64(scratch + 8L, cur.prevHi)
            val out = layout.readNative(scratch)
            kmDealloc(scratch, 16L, 16L)
            out
        }
        else -> error("Unsupported atomic xchg width: " + layout.sizeBytes)
    }

public data class AtomicCxchgLayoutOutcome<T>(
    public val first: T,
    public val second: Boolean,
)

public fun <T> atomicCxchgWithLayout(
    addr: Long,
    expected: T,
    desired: T,
    succ: Int,
    fail: Int,
    layout: TypeLayout<T>,
): AtomicCxchgLayoutOutcome<T> =
    when (layout.sizeBytes) {
        1L -> {
            val res = kmAtomicCxchgU8(addr, layout.toBitsU64(expected).toUByte(), layout.toBitsU64(desired).toUByte(), succ, fail)
            AtomicCxchgLayoutOutcome(layout.fromBitsU64(res.previous.toULong()), res.succeeded)
        }
        2L -> {
            val res = kmAtomicCxchgU16(addr, layout.toBitsU64(expected).toUShort(), layout.toBitsU64(desired).toUShort(), succ, fail)
            AtomicCxchgLayoutOutcome(layout.fromBitsU64(res.previous.toULong()), res.succeeded)
        }
        4L -> {
            val res = kmAtomicCxchgU32(addr, layout.toBitsU64(expected).toUInt(), layout.toBitsU64(desired).toUInt(), succ, fail)
            AtomicCxchgLayoutOutcome(layout.fromBitsU64(res.previous.toULong()), res.succeeded)
        }
        8L -> {
            val res = kmAtomicCxchgU64(addr, layout.toBitsU64(expected), layout.toBitsU64(desired), succ, fail)
            AtomicCxchgLayoutOutcome(layout.fromBitsU64(res.previous), res.succeeded)
        }
        16L -> {
            val scratch = kmAlloc(32L, 16L)
            layout.writeNative(scratch, expected)
            layout.writeNative(scratch + 16L, desired)
            val expLo = kmLoadU64(scratch)
            val expHi = kmLoadU64(scratch + 8L)
            val desLo = kmLoadU64(scratch + 16L)
            val desHi = kmLoadU64(scratch + 24L)
            val res = kmAtomicCxchgU128Pair(addr, expLo, expHi, desLo, desHi)
            kmStoreU64(scratch, res.prevLo)
            kmStoreU64(scratch + 8L, res.prevHi)
            val out = layout.readNative(scratch)
            kmDealloc(scratch, 32L, 16L)
            AtomicCxchgLayoutOutcome(out, res.succeeded)
        }
        else -> error("Unsupported atomic cxchg width: " + layout.sizeBytes)
    }

public fun <T, U> atomicRmwWithLayout(
    addr: Long,
    operand: U,
    op: Int,
    ord: Int,
    dstLayout: TypeLayout<T>,
    operandLayout: TypeLayout<U>,
): T {
    val opBits = operandLayout.toBitsU64(operand)
    return when (dstLayout.sizeBytes) {
        1L -> dstLayout.fromBitsU64(kmAtomicRmwU8(addr, opBits.toUByte(), op, ord).toULong())
        2L -> dstLayout.fromBitsU64(kmAtomicRmwU16(addr, opBits.toUShort(), op, ord).toULong())
        4L -> dstLayout.fromBitsU64(kmAtomicRmwU32(addr, opBits.toUInt(), op, ord).toULong())
        8L -> dstLayout.fromBitsU64(kmAtomicRmwU64(addr, opBits, op, ord))
        else -> error("Unsupported atomic rmw width: " + dstLayout.sizeBytes)
    }
}

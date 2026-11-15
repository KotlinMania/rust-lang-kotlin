@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.array

import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU8
import io.github.kotlinmania.rustlang.core.mem.FixedArrayTypeLayout
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmBytesLoadU64Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreF32Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreF64Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreU16Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreU32Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreU64Le
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * Index-based element generator SAM for [FixedArray.fromFn] (`core::array::from_fn`).
 */
public fun interface FixedArrayElementInitializer<out T> {
    public fun initAt(index: Int): T
}

/**
 * Fixed-size contiguous array `[T; N]` stored in packed contiguous memory (`core::array`).
 *
 * Uses a GC-managed [ByteArray] by default so fixed arrays garbage-collect cleanly on all targets
 * without leaking native memory, and lazily promotes to native linear memory on demand if [asPtr],
 * [asMutPtr], or [dataPtr] is requested.
 */
public class FixedArray<T>
    private constructor(
        private var managedBuf: ByteArray?,
        private var managedByteOffsetValue: Int,
        private var nativeAddr: Long,
        private val length: Int,
        public val elementLayout: TypeLayout<T>,
    ) {
        public constructor(
            dataPtr: MutPtr,
            length: Int,
            elementLayout: TypeLayout<T>,
        ) : this(
            managedBuf = null,
            managedByteOffsetValue = 0,
            nativeAddr = dataPtr.rawAddress,
            length = length,
            elementLayout = elementLayout,
        )

        init {
            require(length >= 0) { "FixedArray length must be non-negative: $length" }
        }

        public val dataPtr: MutPtr
            get() = asMutPtr()

        public val size: Int
            get() = length

        public fun len(): Int = length

        public fun isEmpty(): Boolean = length == 0

        private fun ensureNativeAddr(): Long {
            val bytes = managedBuf ?: return nativeAddr
            val totalBytes = length.toLong() * elementLayout.strideBytes
            if (totalBytes <= 0L || elementLayout.sizeBytes == 0L) {
                val alignAddr = maxOf(1L, elementLayout.alignBytes)
                managedBuf = null
                managedByteOffsetValue = 0
                nativeAddr = alignAddr
                return alignAddr
            }
            val align = maxOf(1L, elementLayout.alignBytes)
            val addr = kmAlloc(totalBytes, align)
            val byteLen = totalBytes.toInt()
            val off = managedByteOffsetValue
            var i = 0
            val fastEnd = byteLen and 7.inv()
            while (i < fastEnd) {
                kmStoreU64(addr + i.toLong(), kmBytesLoadU64Le(bytes, off + i))
                i += 8
            }
            while (i < byteLen) {
                kmStoreU8(addr + i.toLong(), bytes[off + i].toUByte())
                i += 1
            }
            managedBuf = null
            managedByteOffsetValue = 0
            nativeAddr = addr
            return addr
        }

        public fun asPtr(): ConstPtr = ConstPtr(ensureNativeAddr())

        public fun asMutPtr(): MutPtr = MutPtr(ensureNativeAddr())

        private fun readAtIndex(index: Int): T {
            val bytes = managedBuf
            return if (bytes != null) {
                elementLayout.readBytes(bytes, managedByteOffsetValue + index * elementLayout.strideBytes.toInt())
            } else {
                elementLayout.readNative(nativeAddr + index.toLong() * elementLayout.strideBytes)
            }
        }

        private fun writeAtIndex(index: Int, value: T) {
            val bytes = managedBuf
            if (bytes != null) {
                elementLayout.writeBytes(bytes, managedByteOffsetValue + index * elementLayout.strideBytes.toInt(), value)
            } else {
                elementLayout.writeNative(nativeAddr + index.toLong() * elementLayout.strideBytes, value)
            }
        }

        private fun <P> readPrimitiveAt(
            index: Int,
            layout: TypeLayout<P>,
        ): P {
            val bytes = managedBuf
            return if (bytes != null) {
                layout.readBytes(bytes, managedByteOffsetValue + index * layout.strideBytes.toInt())
            } else {
                layout.readNative(nativeAddr + index.toLong() * layout.strideBytes)
            }
        }

        public operator fun get(index: Int): T {
            if (index < 0 || index >= length) {
                throw IndexOutOfBoundsException("array index out of bounds: the len is $length but the index is $index")
            }
            return readAtIndex(index)
        }

        public operator fun get(range: IntRange): List<T> {
            if (range.isEmpty()) return emptyList()
            if (range.first < 0 || range.last >= length) {
                throw IndexOutOfBoundsException("range $range out of bounds for length $length")
            }
            return List(range.last - range.first + 1) { offset -> readAtIndex(range.first + offset) }
        }

        public operator fun set(index: Int, value: T) {
            if (index < 0 || index >= length) {
                throw IndexOutOfBoundsException("array index out of bounds: the len is $length but the index is $index")
            }
            writeAtIndex(index, value)
        }

        public fun fill(value: T) {
            for (i in 0 until length) {
                writeAtIndex(i, value)
            }
        }

        public fun swap(a: Int, b: Int) {
            if (a < 0 || a >= length) {
                throw IndexOutOfBoundsException("index $a out of bounds for length $length")
            }
            if (b < 0 || b >= length) {
                throw IndexOutOfBoundsException("index $b out of bounds for length $length")
            }
            if (a == b) return
            val tmp = readAtIndex(a)
            writeAtIndex(a, readAtIndex(b))
            writeAtIndex(b, tmp)
        }

        public fun reverse() {
            var lo = 0
            var hi = length - 1
            while (lo < hi) {
                val tmp = readAtIndex(lo)
                writeAtIndex(lo, readAtIndex(hi))
                writeAtIndex(hi, tmp)
                lo += 1
                hi -= 1
            }
        }

        public fun cloneArray(): FixedArray<T> {
            val out = uninit(length, elementLayout)
            if (length == 0) return out
            val totalBytes = length.toLong() * elementLayout.strideBytes
            if (totalBytes <= 0L) return out
            val srcBytes = managedBuf
            val dstBytes = out.managedBuf
            if (srcBytes != null && dstBytes != null && !elementLayout.isGcObjectSlot) {
                srcBytes.copyInto(
                    dstBytes,
                    destinationOffset = out.managedByteOffsetValue,
                    startIndex = managedByteOffsetValue,
                    endIndex = managedByteOffsetValue + totalBytes.toInt(),
                )
            } else if (dstBytes != null) {
                for (i in 0 until length) {
                    out[i] = readAtIndex(i)
                }
            } else {
                kmCopyNonoverlapping(out.asMutPtr().rawAddress, asPtr().rawAddress, totalBytes)
            }
            return out
        }

        public fun deallocate() {
            val buf = managedBuf
            if (buf != null) {
                managedBuf = null
                managedByteOffsetValue = 0
                return
            }
            val totalBytes = maxOf(1L, length.toLong() * elementLayout.strideBytes)
            val align = maxOf(1L, elementLayout.alignBytes)
            if (nativeAddr != 0L) {
                kmDealloc(nativeAddr, totalBytes, align)
                nativeAddr = 0L
            }
        }

        public fun iter(): Iterator<T> = iterator()

        public operator fun iterator(): Iterator<T> =
            object : Iterator<T> {
                private var index: Int = 0

                override fun hasNext(): Boolean = index < length

                override fun next(): T {
                    if (index >= length) {
                        throw NoSuchElementException("FixedArray iterator exhausted")
                    }
                    val v = readAtIndex(index)
                    index += 1
                    return v
                }
            }

        public fun toByteArray(): ByteArray = ByteArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.I8) }

        public fun toUByteArray(): UByteArray = UByteArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.U8) }

        public fun toBooleanArray(): BooleanArray = BooleanArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.BOOL) }

        public fun toShortArray(): ShortArray = ShortArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.I16) }

        public fun toUShortArray(): UShortArray = UShortArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.U16) }

        public fun toIntArray(): IntArray = IntArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.I32) }

        public fun toUIntArray(): UIntArray = UIntArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.U32) }

        public fun toLongArray(): LongArray = LongArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.I64) }

        public fun toULongArray(): ULongArray = ULongArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.U64) }

        public fun toFloatArray(): FloatArray = FloatArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.F32) }

        public fun toDoubleArray(): DoubleArray = DoubleArray(length) { i -> readPrimitiveAt(i, PrimitiveLayouts.F64) }

        public fun toList(): List<T> = List(length) { i -> readAtIndex(i) }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is FixedArray<*>) return false
            if (length != other.length) return false
            for (i in 0 until length) {
                if (readAtIndex(i) != other.readAtIndex(i)) return false
            }
            return true
        }

        override fun hashCode(): Int {
            var acc = 1
            for (i in 0 until length) {
                acc = 31 * acc + (readAtIndex(i)?.hashCode() ?: 0)
            }
            return acc
        }

        override fun toString(): String = toList().toString()

        public companion object {
            public fun <T> fromRawParts(
                ptr: MutPtr,
                length: Int,
                elementLayout: TypeLayout<T>,
            ): FixedArray<T> = FixedArray(ptr, length, elementLayout)

            public fun <T> uninit(
                length: Int,
                elementLayout: TypeLayout<T> = ObjectSlotLayout(),
            ): FixedArray<T> {
                require(length >= 0) { "FixedArray length must be non-negative: $length" }
                val totalBytes = length.toLong() * elementLayout.strideBytes
                if (totalBytes <= 0L || elementLayout.sizeBytes == 0L) {
                    return FixedArray(ByteArray(0), 0, 0L, length, elementLayout)
                }
                if (totalBytes <= Int.MAX_VALUE.toLong()) {
                    return FixedArray(ByteArray(totalBytes.toInt()), 0, 0L, length, elementLayout)
                }
                val align = maxOf(1L, elementLayout.alignBytes)
                val addr = kmAlloc(totalBytes, align)
                return FixedArray(MutPtr(addr), length, elementLayout)
            }

            public fun <T> zeroed(
                length: Int,
                elementLayout: TypeLayout<T> = ObjectSlotLayout(),
            ): FixedArray<T> = uninit(length, elementLayout)

            public fun <T> repeat(
                elem: T,
                length: Int,
                elementLayout: TypeLayout<T> = ObjectSlotLayout(),
            ): FixedArray<T> {
                val out = uninit(length, elementLayout)
                for (i in 0 until length) {
                    out.writeAtIndex(i, elem)
                }
                return out
            }

            public fun <T> fromFn(
                length: Int,
                elementLayout: TypeLayout<T> = ObjectSlotLayout(),
                cb: FixedArrayElementInitializer<T>,
            ): FixedArray<T> {
                val out = uninit(length, elementLayout)
                for (i in 0 until length) {
                    out.writeAtIndex(i, cb.initAt(i))
                }
                return out
            }

            public fun fromBooleanArray(values: BooleanArray): FixedArray<Boolean> {
                val buf = ByteArray(values.size) { i -> if (values[i]) 1 else 0 }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.BOOL)
            }

            public fun fromUByteArray(bytes: UByteArray): FixedArray<UByte> =
                FixedArray(bytes.asByteArray().copyOf(), 0, 0L, bytes.size, PrimitiveLayouts.U8)

            public fun fromByteArray(bytes: ByteArray): FixedArray<UByte> =
                FixedArray(bytes.copyOf(), 0, 0L, bytes.size, PrimitiveLayouts.U8)

            public fun fromByteArraySigned(bytes: ByteArray): FixedArray<Byte> =
                FixedArray(bytes.copyOf(), 0, 0L, bytes.size, PrimitiveLayouts.I8)

            public fun fromShortArray(values: ShortArray): FixedArray<Short> {
                val buf = ByteArray(values.size * 2)
                for (i in values.indices) {
                    kmBytesStoreU16Le(buf, i * 2, values[i].toUShort())
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.I16)
            }

            public fun fromUShortArray(values: UShortArray): FixedArray<UShort> {
                val buf = ByteArray(values.size * 2)
                for (i in values.indices) {
                    kmBytesStoreU16Le(buf, i * 2, values[i])
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.U16)
            }

            public fun fromIntArray(values: IntArray): FixedArray<Int> {
                val buf = ByteArray(values.size * 4)
                for (i in values.indices) {
                    kmBytesStoreU32Le(buf, i * 4, values[i].toUInt())
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.I32)
            }

            public fun fromIntList(values: List<Int>): FixedArray<Int> =
                fromList(values, PrimitiveLayouts.I32)

            public fun fromUIntArray(values: UIntArray): FixedArray<UInt> {
                val buf = ByteArray(values.size * 4)
                for (i in values.indices) {
                    kmBytesStoreU32Le(buf, i * 4, values[i])
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.U32)
            }

            public fun fromLongArray(values: LongArray): FixedArray<Long> {
                val buf = ByteArray(values.size * 8)
                for (i in values.indices) {
                    kmBytesStoreU64Le(buf, i * 8, values[i].toULong())
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.I64)
            }

            public fun fromULongArray(values: ULongArray): FixedArray<ULong> {
                val buf = ByteArray(values.size * 8)
                for (i in values.indices) {
                    kmBytesStoreU64Le(buf, i * 8, values[i])
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.U64)
            }

            public fun fromFloatArray(values: FloatArray): FixedArray<Float> {
                val buf = ByteArray(values.size * 4)
                for (i in values.indices) {
                    kmBytesStoreF32Le(buf, i * 4, values[i])
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.F32)
            }

            public fun fromDoubleArray(values: DoubleArray): FixedArray<Double> {
                val buf = ByteArray(values.size * 8)
                for (i in values.indices) {
                    kmBytesStoreF64Le(buf, i * 8, values[i])
                }
                return FixedArray(buf, 0, 0L, values.size, PrimitiveLayouts.F64)
            }

            public fun <T> fromList(
                values: List<T>,
                elementLayout: TypeLayout<T> = ObjectSlotLayout(),
            ): FixedArray<T> {
                val out = uninit(values.size, elementLayout)
                for (i in values.indices) {
                    out.writeAtIndex(i, values[i])
                }
                return out
            }

            public fun <T> typeLayout(
                elementLayout: TypeLayout<T>,
                length: Int,
            ): TypeLayout<FixedArray<T>> =
                object : TypeLayout<FixedArray<T>> {
                    private val listLayout = FixedArrayTypeLayout(elementLayout, length)

                    override val sizeBytes: Long
                        get() = listLayout.sizeBytes

                    override val alignBytes: Long
                        get() = listLayout.alignBytes

                    override val isGcObjectSlot: Boolean
                        get() = listLayout.isGcObjectSlot

                    override fun readNative(addr: Long): FixedArray<T> =
                        fromList(listLayout.readNative(addr), elementLayout)

                    override fun writeNative(addr: Long, value: FixedArray<T>) {
                        listLayout.writeNative(addr, value.toList())
                    }

                    override fun readBytes(buf: ByteArray, offset: Int): FixedArray<T> =
                        fromList(listLayout.readBytes(buf, offset), elementLayout)

                    override fun writeBytes(buf: ByteArray, offset: Int, value: FixedArray<T>) {
                        listLayout.writeBytes(buf, offset, value.toList())
                    }
                }
        }
    }

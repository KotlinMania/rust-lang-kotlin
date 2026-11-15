@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.slice

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU8
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlin.jvm.JvmName

private const val SLICE_RING_CAPACITY: Int = 64

private class SliceBridgeSlot(
    val addr: Long,
    val byteSize: Long,
    val align: Long,
    var active: Boolean,
    val writeBack: ((Long) -> Unit)?,
)

private object NativeSliceBridgeRegistry {
    private val slots: Array<SliceBridgeSlot?> = arrayOfNulls(SLICE_RING_CAPACITY)
    private var cursor: Int = 0

    fun allocate(
        byteSize: Long,
        align: Long,
        writeBack: ((Long) -> Unit)?,
    ): Long {
        if (byteSize <= 0L) {
            return maxOf(1L, align)
        }
        val safeAlign = maxOf(1L, align)
        val old = slots[cursor]
        if (old != null && old.active) {
            old.active = false
            kmDealloc(old.addr, old.byteSize, old.align)
        }
        val addr = kmAlloc(byteSize, safeAlign)
        slots[cursor] = SliceBridgeSlot(addr, byteSize, safeAlign, true, writeBack)
        cursor = (cursor + 1) % SLICE_RING_CAPACITY
        return addr
    }

    fun flushWriteBacks(
        dstAddr: Long,
        byteCount: Long,
    ) {
        if (byteCount <= 0L) return
        val dstEnd = dstAddr + byteCount
        for (slot in slots) {
            val writeBack = slot?.writeBack
            if (slot != null && slot.active && writeBack != null) {
                val slotEnd = slot.addr + slot.byteSize
                if (dstAddr < slotEnd && dstEnd > slot.addr) {
                    writeBack(slot.addr)
                }
            }
        }
    }
}

internal fun flushMutableSliceWriteBacks(
    dstAddr: Long,
    byteCount: Long,
) {
    NativeSliceBridgeRegistry.flushWriteBacks(dstAddr, byteCount)
}

private inline fun allocRoPtr(
    count: Int,
    elemBytes: Long,
    align: Long,
    crossinline storeAt: (baseAddr: Long, index: Int) -> Unit,
): ConstPtr {
    val totalBytes = count.toLong() * elemBytes
    val addr = NativeSliceBridgeRegistry.allocate(totalBytes, align, null)
    for (i in 0 until count) {
        storeAt(addr, i)
    }
    return ConstPtr(addr)
}

private inline fun allocRwMutPtr(
    count: Int,
    elemBytes: Long,
    align: Long,
    crossinline storeAt: (baseAddr: Long, index: Int) -> Unit,
    crossinline loadBackAt: (baseAddr: Long, index: Int) -> Unit,
): MutPtr {
    val totalBytes = count.toLong() * elemBytes
    val addr =
        NativeSliceBridgeRegistry.allocate(
            totalBytes,
            align,
            if (count == 0) {
                null
            } else {
                { baseAddr ->
                    for (i in 0 until count) {
                        loadBackAt(baseAddr, i)
                    }
                }
            },
        )
    for (i in 0 until count) {
        storeAt(addr, i)
    }
    return MutPtr(addr)
}

// --- Raw pointer <-> List/MutableList slice views ---

private class RawConstSliceList<T>(
    val dataPtr: ConstPtr,
    private val len: Int,
    private val typeLayoutT: TypeLayout<T>,
) : AbstractList<T>() {
    init {
        require(len >= 0) { "slice length must be non-negative, got $len" }
    }

    private val stride: Long = maxOf(0L, typeLayoutT.strideBytes)

    override val size: Int
        get() = len

    override fun get(index: Int): T {
        if (index < 0 || index >= len) {
            throw IndexOutOfBoundsException("index $index out of bounds for length $len")
        }
        return typeLayoutT.readNative(dataPtr.rawAddress + index.toLong() * stride)
    }

    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<T> {
        if (fromIndex < 0 || toIndex > len || fromIndex > toIndex) {
            throw IndexOutOfBoundsException("fromIndex=$fromIndex, toIndex=$toIndex, size=$len")
        }
        return RawConstSliceList(
            ConstPtr(dataPtr.rawAddress + fromIndex.toLong() * stride, dataPtr.rawMeta),
            toIndex - fromIndex,
            typeLayoutT,
        )
    }
}

private class RawMutSliceList<T>(
    val dataPtr: MutPtr,
    private val len: Int,
    private val typeLayoutT: TypeLayout<T>,
) : AbstractMutableList<T>() {
    init {
        require(len >= 0) { "slice length must be non-negative, got $len" }
    }

    private val stride: Long = maxOf(0L, typeLayoutT.strideBytes)

    override val size: Int
        get() = len

    override fun get(index: Int): T {
        if (index < 0 || index >= len) {
            throw IndexOutOfBoundsException("index $index out of bounds for length $len")
        }
        return typeLayoutT.readNative(dataPtr.rawAddress + index.toLong() * stride)
    }

    override fun set(
        index: Int,
        element: T,
    ): T {
        if (index < 0 || index >= len) {
            throw IndexOutOfBoundsException("index $index out of bounds for length $len")
        }
        val addr = dataPtr.rawAddress + index.toLong() * stride
        val prev = typeLayoutT.readNative(addr)
        typeLayoutT.writeNative(addr, element)
        NativeSliceBridgeRegistry.flushWriteBacks(addr, stride)
        return prev
    }

    override fun add(
        index: Int,
        element: T,
    ): Unit = throw UnsupportedOperationException("Fixed-length raw slice cannot resize")

    override fun removeAt(index: Int): T = throw UnsupportedOperationException("Fixed-length raw slice cannot resize")

    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): MutableList<T> {
        if (fromIndex < 0 || toIndex > len || fromIndex > toIndex) {
            throw IndexOutOfBoundsException("fromIndex=$fromIndex, toIndex=$toIndex, size=$len")
        }
        return RawMutSliceList(
            MutPtr(dataPtr.rawAddress + fromIndex.toLong() * stride, dataPtr.rawMeta),
            toIndex - fromIndex,
            typeLayoutT,
        )
    }
}

private fun List<*>.existingConstPtrOrNull(): ConstPtr? =
    when (this) {
        is RawConstSliceList<*> -> dataPtr
        is RawMutSliceList<*> -> ConstPtr(dataPtr.rawAddress)
        else -> null
    }

private fun MutableList<*>.existingMutPtrOrNull(): MutPtr? =
    when (this) {
        is RawMutSliceList<*> -> dataPtr
        else -> null
    }

public fun <T> rawSliceList(
    data: ConstPtr,
    len: Int,
    typeLayoutT: TypeLayout<T>,
): List<T> = RawConstSliceList(data, len, typeLayoutT)

public fun <T> rawSliceList(
    data: MutPtr,
    len: Int,
    typeLayoutT: TypeLayout<T>,
): List<T> = RawConstSliceList(ConstPtr(data.rawAddress), len, typeLayoutT)

public fun <T> rawMutSliceList(
    data: MutPtr,
    len: Int,
    typeLayoutT: TypeLayout<T>,
): MutableList<T> = RawMutSliceList(data, len, typeLayoutT)

// --- List<T>.asPtr() ---

@JvmName("listUByteAsPtr")
public fun List<UByte>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 1L, 1L) { addr, i -> kmStoreU8(addr + i, this[i]) }

@JvmName("listByteAsPtr")
public fun List<Byte>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 1L, 1L) { addr, i -> kmStoreU8(addr + i, this[i].toUByte()) }

@JvmName("listUShortAsPtr")
public fun List<UShort>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 2L, 2L) { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i]) }

@JvmName("listShortAsPtr")
public fun List<Short>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 2L, 2L) { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i].toUShort()) }

@JvmName("listUIntAsPtr")
public fun List<UInt>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i]) }

@JvmName("listIntAsPtr")
public fun List<Int>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toUInt()) }

@JvmName("listULongAsPtr")
public fun List<ULong>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i]) }

@JvmName("listLongAsPtr")
public fun List<Long>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toULong()) }

@JvmName("listFloatAsPtr")
public fun List<Float>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toRawBits().toUInt()) }

@JvmName("listDoubleAsPtr")
public fun List<Double>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toRawBits().toULong()) }

@JvmName("listU128AsPtr")
public fun List<U128>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 16L, 16L) { addr, i ->
            val v = this[i]
            val off = addr + i.toLong() * 16L
            kmStoreU64(off, v.low)
            kmStoreU64(off + 8L, v.high)
        }

@JvmName("listI128AsPtr")
public fun List<I128>.asPtr(): ConstPtr =
    existingConstPtrOrNull()
        ?: allocRoPtr(size, 16L, 16L) { addr, i ->
            val v = this[i]
            val off = addr + i.toLong() * 16L
            kmStoreU64(off, v.low)
            kmStoreU64(off + 8L, v.high.toULong())
        }

// --- MutableList<T>.asMutPtr() with write-back ---

@JvmName("mutableListUByteAsMutPtr")
public fun MutableList<UByte>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            1L,
            1L,
            { addr, i -> kmStoreU8(addr + i, this[i]) },
            { addr, i -> this[i] = kmLoadU8(addr + i) },
        )

@JvmName("mutableListByteAsMutPtr")
public fun MutableList<Byte>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            1L,
            1L,
            { addr, i -> kmStoreU8(addr + i, this[i].toUByte()) },
            { addr, i -> this[i] = kmLoadU8(addr + i).toByte() },
        )

@JvmName("mutableListUShortAsMutPtr")
public fun MutableList<UShort>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            2L,
            2L,
            { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i]) },
            { addr, i -> this[i] = kmLoadU16(addr + i.toLong() * 2L) },
        )

@JvmName("mutableListShortAsMutPtr")
public fun MutableList<Short>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            2L,
            2L,
            { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i].toUShort()) },
            { addr, i -> this[i] = kmLoadU16(addr + i.toLong() * 2L).toShort() },
        )

@JvmName("mutableListUIntAsMutPtr")
public fun MutableList<UInt>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            4L,
            4L,
            { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i]) },
            { addr, i -> this[i] = kmLoadU32(addr + i.toLong() * 4L) },
        )

@JvmName("mutableListIntAsMutPtr")
public fun MutableList<Int>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            4L,
            4L,
            { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toUInt()) },
            { addr, i -> this[i] = kmLoadU32(addr + i.toLong() * 4L).toInt() },
        )

@JvmName("mutableListULongAsMutPtr")
public fun MutableList<ULong>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            8L,
            8L,
            { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i]) },
            { addr, i -> this[i] = kmLoadU64(addr + i.toLong() * 8L) },
        )

@JvmName("mutableListLongAsMutPtr")
public fun MutableList<Long>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            8L,
            8L,
            { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toULong()) },
            { addr, i -> this[i] = kmLoadU64(addr + i.toLong() * 8L).toLong() },
        )

@JvmName("mutableListFloatAsMutPtr")
public fun MutableList<Float>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            4L,
            4L,
            { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toRawBits().toUInt()) },
            { addr, i -> this[i] = Float.fromBits(kmLoadU32(addr + i.toLong() * 4L).toInt()) },
        )

@JvmName("mutableListDoubleAsMutPtr")
public fun MutableList<Double>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            8L,
            8L,
            { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toRawBits().toULong()) },
            { addr, i -> this[i] = Double.fromBits(kmLoadU64(addr + i.toLong() * 8L).toLong()) },
        )

@JvmName("mutableListU128AsMutPtr")
public fun MutableList<U128>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            16L,
            16L,
            { addr, i ->
                val v = this[i]
                val off = addr + i.toLong() * 16L
                kmStoreU64(off, v.low)
                kmStoreU64(off + 8L, v.high)
            },
            { addr, i ->
                val off = addr + i.toLong() * 16L
                this[i] = U128(kmLoadU64(off + 8L), kmLoadU64(off))
            },
        )

@JvmName("mutableListI128AsMutPtr")
public fun MutableList<I128>.asMutPtr(): MutPtr =
    existingMutPtrOrNull()
        ?: allocRwMutPtr(
            size,
            16L,
            16L,
            { addr, i ->
                val v = this[i]
                val off = addr + i.toLong() * 16L
                kmStoreU64(off, v.low)
                kmStoreU64(off + 8L, v.high.toULong())
            },
            { addr, i ->
                val off = addr + i.toLong() * 16L
                this[i] = I128(kmLoadU64(off + 8L).toLong(), kmLoadU64(off))
            },
        )

// --- Primitive *Array.asPtr() and *Array.asMutPtr() ---

@JvmName("uByteArrayAsPtr")
public fun UByteArray.asPtr(): ConstPtr =
    allocRoPtr(size, 1L, 1L) { addr, i -> kmStoreU8(addr + i, this[i]) }

@JvmName("uByteArrayAsMutPtr")
public fun UByteArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        1L,
        1L,
        { addr, i -> kmStoreU8(addr + i, this[i]) },
        { addr, i -> this[i] = kmLoadU8(addr + i) },
    )

public fun ByteArray.asPtr(): ConstPtr =
    allocRoPtr(size, 1L, 1L) { addr, i -> kmStoreU8(addr + i, this[i].toUByte()) }

public fun ByteArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        1L,
        1L,
        { addr, i -> kmStoreU8(addr + i, this[i].toUByte()) },
        { addr, i -> this[i] = kmLoadU8(addr + i).toByte() },
    )

@JvmName("uShortArrayAsPtr")
public fun UShortArray.asPtr(): ConstPtr =
    allocRoPtr(size, 2L, 2L) { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i]) }

@JvmName("uShortArrayAsMutPtr")
public fun UShortArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        2L,
        2L,
        { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i]) },
        { addr, i -> this[i] = kmLoadU16(addr + i.toLong() * 2L) },
    )

public fun ShortArray.asPtr(): ConstPtr =
    allocRoPtr(size, 2L, 2L) { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i].toUShort()) }

public fun ShortArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        2L,
        2L,
        { addr, i -> kmStoreU16(addr + i.toLong() * 2L, this[i].toUShort()) },
        { addr, i -> this[i] = kmLoadU16(addr + i.toLong() * 2L).toShort() },
    )

@JvmName("uIntArrayAsPtr")
public fun UIntArray.asPtr(): ConstPtr =
    allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i]) }

@JvmName("uIntArrayAsMutPtr")
public fun UIntArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        4L,
        4L,
        { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i]) },
        { addr, i -> this[i] = kmLoadU32(addr + i.toLong() * 4L) },
    )

public fun IntArray.asPtr(): ConstPtr =
    allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toUInt()) }

public fun IntArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        4L,
        4L,
        { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toUInt()) },
        { addr, i -> this[i] = kmLoadU32(addr + i.toLong() * 4L).toInt() },
    )

@JvmName("uLongArrayAsPtr")
public fun ULongArray.asPtr(): ConstPtr =
    allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i]) }

@JvmName("uLongArrayAsMutPtr")
public fun ULongArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        8L,
        8L,
        { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i]) },
        { addr, i -> this[i] = kmLoadU64(addr + i.toLong() * 8L) },
    )

public fun LongArray.asPtr(): ConstPtr =
    allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toULong()) }

public fun LongArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        8L,
        8L,
        { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toULong()) },
        { addr, i -> this[i] = kmLoadU64(addr + i.toLong() * 8L).toLong() },
    )

public fun FloatArray.asPtr(): ConstPtr =
    allocRoPtr(size, 4L, 4L) { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toRawBits().toUInt()) }

public fun FloatArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        4L,
        4L,
        { addr, i -> kmStoreU32(addr + i.toLong() * 4L, this[i].toRawBits().toUInt()) },
        { addr, i -> this[i] = Float.fromBits(kmLoadU32(addr + i.toLong() * 4L).toInt()) },
    )

public fun DoubleArray.asPtr(): ConstPtr =
    allocRoPtr(size, 8L, 8L) { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toRawBits().toULong()) }

public fun DoubleArray.asMutPtr(): MutPtr =
    allocRwMutPtr(
        size,
        8L,
        8L,
        { addr, i -> kmStoreU64(addr + i.toLong() * 8L, this[i].toRawBits().toULong()) },
        { addr, i -> this[i] = Double.fromBits(kmLoadU64(addr + i.toLong() * 8L).toLong()) },
    )

// --- Range indexing helpers ---

@JvmName("mutableListGetRange")
public operator fun <T> MutableList<T>.get(range: IntRange): MutableList<T> =
    if (range.isEmpty()) subList(0, 0) else subList(range.first, range.last + 1)

@JvmName("listGetRange")
public operator fun <T> List<T>.get(range: IntRange): List<T> =
    if (range.isEmpty()) emptyList() else subList(range.first, range.last + 1)

package io.github.kotlinmania.rustlang.core.intrinsics

import kotlin.math.sqrt

internal object KmLinearMemoryArena {
    private const val ARENA_BASE_ADDR: Long = 65536L
    private const val INITIAL_CAPACITY_BYTES: Int = 1048576

    private var memory: ByteArray = ByteArray(INITIAL_CAPACITY_BYTES)
    private var bumpOffset: Int = ARENA_BASE_ADDR.toInt()
    private val allocBucketSizes: MutableMap<Long, Int> = hashMapOf()
    private val freeLists: MutableMap<Int, ArrayDeque<Long>> = hashMapOf()

    private fun ensureCapacity(requiredEnd: Int) {
        if (requiredEnd <= memory.size) return
        var newCap = memory.size
        while (newCap < requiredEnd) {
            newCap = newCap shl 1
        }
        memory = memory.copyOf(newCap)
    }

    private fun roundUpBucket(size: Long, align: Long): Int {
        val minReq = maxOf(1L, maxOf(size, align)).coerceAtMost(Int.MAX_VALUE.toLong() / 2L).toInt()
        var bucket = 8
        while (bucket < minReq) {
            bucket = bucket shl 1
        }
        return bucket
    }

    fun alloc(size: Long, align: Long): Long {
        val bucket = roundUpBucket(size, align)
        val freeQueue = freeLists[bucket]
        if (freeQueue != null && freeQueue.isNotEmpty()) {
            val reused = freeQueue.removeLast()
            allocBucketSizes[reused] = bucket
            return reused
        }
        val alignInt = maxOf(1L, align).toInt()
        val mask = alignInt - 1
        val alignedStart =
            if ((alignInt and mask) == 0) {
                (bumpOffset + mask) and mask.inv()
            } else {
                ((bumpOffset + alignInt - 1) / alignInt) * alignInt
            }
        val end = alignedStart + bucket
        ensureCapacity(end)
        bumpOffset = end
        val addr = alignedStart.toLong()
        allocBucketSizes[addr] = bucket
        return addr
    }

    fun allocZeroed(size: Long, align: Long): Long {
        val addr = alloc(size, align)
        val len = maxOf(0L, size).toInt()
        if (len > 0) {
            val start = addr.toInt()
            memory.fill(0, start, start + len)
        }
        return addr
    }

    fun realloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long {
        if (ptr == 0L) return alloc(newSize, align)
        val existingBucket = allocBucketSizes[ptr]
        val reqNew = maxOf(1L, newSize).toInt()
        val alignInt = maxOf(1L, align).toInt()
        if (existingBucket != null && reqNew <= existingBucket && (ptr % alignInt.toLong()) == 0L) {
            return ptr
        }
        val fresh = alloc(newSize, align)
        val copyLen = minOf(maxOf(0L, oldSize), maxOf(0L, newSize)).toInt()
        if (copyLen > 0) {
            memory.copyInto(memory, fresh.toInt(), ptr.toInt(), ptr.toInt() + copyLen)
        }
        dealloc(ptr, oldSize, align)
        return fresh
    }

    fun dealloc(ptr: Long, size: Long, align: Long) {
        if (ptr < ARENA_BASE_ADDR) return
        val bucket = allocBucketSizes.remove(ptr) ?: roundUpBucket(size, align)
        freeLists.getOrPut(bucket) { ArrayDeque() }.addLast(ptr)
    }

    fun loadU8(addr: Long): UByte = memory[addr.toInt()].toUByte()

    fun loadU16(addr: Long): UShort {
        val idx = addr.toInt()
        val b0 = memory[idx].toInt() and 0xFF
        val b1 = memory[idx + 1].toInt() and 0xFF
        return (b0 or (b1 shl 8)).toUShort()
    }

    fun loadU32(addr: Long): UInt {
        val idx = addr.toInt()
        val b0 = memory[idx].toUInt() and 0xFFu
        val b1 = memory[idx + 1].toUInt() and 0xFFu
        val b2 = memory[idx + 2].toUInt() and 0xFFu
        val b3 = memory[idx + 3].toUInt() and 0xFFu
        return b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
    }

    fun loadU64(addr: Long): ULong {
        val lo = loadU32(addr).toULong()
        val hi = loadU32(addr + 4L).toULong()
        return lo or (hi shl 32)
    }

    fun loadF32(addr: Long): Float = Float.fromBits(loadU32(addr).toInt())

    fun loadF64(addr: Long): Double = Double.fromBits(loadU64(addr).toLong())

    fun storeU8(addr: Long, value: UByte) {
        memory[addr.toInt()] = value.toByte()
    }

    fun storeU16(addr: Long, value: UShort) {
        val idx = addr.toInt()
        val v = value.toInt()
        memory[idx] = (v and 0xFF).toByte()
        memory[idx + 1] = ((v ushr 8) and 0xFF).toByte()
    }

    fun storeU32(addr: Long, value: UInt) {
        val idx = addr.toInt()
        memory[idx] = (value and 0xFFu).toByte()
        memory[idx + 1] = ((value shr 8) and 0xFFu).toByte()
        memory[idx + 2] = ((value shr 16) and 0xFFu).toByte()
        memory[idx + 3] = ((value shr 24) and 0xFFu).toByte()
    }

    fun storeU64(addr: Long, value: ULong) {
        storeU32(addr, (value and 0xFFFFFFFFuL).toUInt())
        storeU32(addr + 4L, (value shr 32).toUInt())
    }

    fun storeF32(addr: Long, value: Float) {
        storeU32(addr, value.toRawBits().toUInt())
    }

    fun storeF64(addr: Long, value: Double) {
        storeU64(addr, value.toRawBits().toULong())
    }

    fun copyNonoverlapping(dst: Long, src: Long, bytes: Long) {
        val len = bytes.toInt()
        if (len <= 0) return
        memory.copyInto(memory, dst.toInt(), src.toInt(), src.toInt() + len)
    }

    fun copy(dst: Long, src: Long, bytes: Long) {
        val len = bytes.toInt()
        if (len <= 0 || dst == src) return
        memory.copyInto(memory, dst.toInt(), src.toInt(), src.toInt() + len)
    }

    fun writeBytes(dst: Long, value: UByte, bytes: Long) {
        val len = bytes.toInt()
        if (len <= 0) return
        val start = dst.toInt()
        memory.fill(value.toByte(), start, start + len)
    }

    fun compareBytes(left: Long, right: Long, bytes: Long): Int {
        val len = bytes.toInt()
        if (len <= 0 || left == right) return 0
        val lBase = left.toInt()
        val rBase = right.toInt()
        for (i in 0 until len) {
            val lb = memory[lBase + i].toInt() and 0xFF
            val rb = memory[rBase + i].toInt() and 0xFF
            if (lb != rb) return lb - rb
        }
        return 0
    }

    fun atomicXchgU8(addr: Long, value: UByte): UByte {
        val prev = loadU8(addr)
        storeU8(addr, value)
        return prev
    }

    fun atomicXchgU16(addr: Long, value: UShort): UShort {
        val prev = loadU16(addr)
        storeU16(addr, value)
        return prev
    }

    fun atomicXchgU32(addr: Long, value: UInt): UInt {
        val prev = loadU32(addr)
        storeU32(addr, value)
        return prev
    }

    fun atomicXchgU64(addr: Long, value: ULong): ULong {
        val prev = loadU64(addr)
        storeU64(addr, value)
        return prev
    }

    fun atomicCxchgU8(addr: Long, expected: UByte, desired: UByte): AtomicCxchgU8Outcome {
        val cur = loadU8(addr)
        return if (cur == expected) {
            storeU8(addr, desired)
            AtomicCxchgU8Outcome(cur, true)
        } else {
            AtomicCxchgU8Outcome(cur, false)
        }
    }

    fun atomicCxchgU16(addr: Long, expected: UShort, desired: UShort): AtomicCxchgU16Outcome {
        val cur = loadU16(addr)
        return if (cur == expected) {
            storeU16(addr, desired)
            AtomicCxchgU16Outcome(cur, true)
        } else {
            AtomicCxchgU16Outcome(cur, false)
        }
    }

    fun atomicCxchgU32(addr: Long, expected: UInt, desired: UInt): AtomicCxchgU32Outcome {
        val cur = loadU32(addr)
        return if (cur == expected) {
            storeU32(addr, desired)
            AtomicCxchgU32Outcome(cur, true)
        } else {
            AtomicCxchgU32Outcome(cur, false)
        }
    }

    fun atomicCxchgU64(addr: Long, expected: ULong, desired: ULong): AtomicCxchgU64Outcome {
        val cur = loadU64(addr)
        return if (cur == expected) {
            storeU64(addr, desired)
            AtomicCxchgU64Outcome(cur, true)
        } else {
            AtomicCxchgU64Outcome(cur, false)
        }
    }

    fun atomicCxchgU128Pair(addr: Long, expLo: ULong, expHi: ULong, desLo: ULong, desHi: ULong): AtomicCxchgU128Outcome {
        val curLo = loadU64(addr)
        val curHi = loadU64(addr + 8L)
        return if (curLo == expLo && curHi == expHi) {
            storeU64(addr, desLo)
            storeU64(addr + 8L, desHi)
            AtomicCxchgU128Outcome(curLo, curHi, true)
        } else {
            AtomicCxchgU128Outcome(curLo, curHi, false)
        }
    }

    fun atomicRmwU8(addr: Long, value: UByte, op: Int): UByte {
        val cur = loadU8(addr)
        val next =
            when (op) {
                1 -> (cur + value).toUByte()
                2 -> (cur - value).toUByte()
                3 -> cur and value
                4 -> (cur and value).inv()
                5 -> cur or value
                6 -> cur xor value
                7 -> if (cur.toByte() > value.toByte()) cur else value
                8 -> if (cur.toByte() < value.toByte()) cur else value
                9 -> if (cur > value) cur else value
                else -> if (cur < value) cur else value
            }
        storeU8(addr, next)
        return cur
    }

    fun atomicRmwU16(addr: Long, value: UShort, op: Int): UShort {
        val cur = loadU16(addr)
        val next =
            when (op) {
                1 -> (cur + value).toUShort()
                2 -> (cur - value).toUShort()
                3 -> cur and value
                4 -> (cur and value).inv()
                5 -> cur or value
                6 -> cur xor value
                7 -> if (cur.toShort() > value.toShort()) cur else value
                8 -> if (cur.toShort() < value.toShort()) cur else value
                9 -> if (cur > value) cur else value
                else -> if (cur < value) cur else value
            }
        storeU16(addr, next)
        return cur
    }

    fun atomicRmwU32(addr: Long, value: UInt, op: Int): UInt {
        val cur = loadU32(addr)
        val next =
            when (op) {
                1 -> cur + value
                2 -> cur - value
                3 -> cur and value
                4 -> (cur and value).inv()
                5 -> cur or value
                6 -> cur xor value
                7 -> if (cur.toInt() > value.toInt()) cur else value
                8 -> if (cur.toInt() < value.toInt()) cur else value
                9 -> if (cur > value) cur else value
                else -> if (cur < value) cur else value
            }
        storeU32(addr, next)
        return cur
    }

    fun atomicRmwU64(addr: Long, value: ULong, op: Int): ULong {
        val cur = loadU64(addr)
        val next =
            when (op) {
                1 -> cur + value
                2 -> cur - value
                3 -> cur and value
                4 -> (cur and value).inv()
                5 -> cur or value
                6 -> cur xor value
                7 -> if (cur.toLong() > value.toLong()) cur else value
                8 -> if (cur.toLong() < value.toLong()) cur else value
                9 -> if (cur > value) cur else value
                else -> if (cur < value) cur else value
            }
        storeU64(addr, next)
        return cur
    }

    fun ctpopU64(value: ULong): UInt = value.countOneBits().toUInt()
    fun ctlzU64(value: ULong): UInt = value.countLeadingZeroBits().toUInt()
    fun cttzU64(value: ULong): UInt = value.countTrailingZeroBits().toUInt()
    fun bswapU16(value: UShort): UShort = (((value.toInt() and 0xFF) shl 8) or ((value.toInt() ushr 8) and 0xFF)).toUShort()
    fun bswapU32(value: UInt): UInt =
        ((value and 0x000000FFu) shl 24) or
            ((value and 0x0000FF00u) shl 8) or
            ((value and 0x00FF0000u) shr 8) or
            ((value and 0xFF000000u) shr 24)
    fun bswapU64(value: ULong): ULong =
        (bswapU32((value and 0xFFFFFFFFuL).toUInt()).toULong() shl 32) or
            bswapU32((value shr 32).toUInt()).toULong()
    fun bitreverseU64(value: ULong): ULong {
        var v = value
        v = ((v shr 1) and 0x5555555555555555uL) or ((v and 0x5555555555555555uL) shl 1)
        v = ((v shr 2) and 0x3333333333333333uL) or ((v and 0x3333333333333333uL) shl 2)
        v = ((v shr 4) and 0x0F0F0F0F0F0F0F0FuL) or ((v and 0x0F0F0F0F0F0F0F0FuL) shl 4)
        return bswapU64(v)
    }
    fun fmaf32(a: Float, b: Float, c: Float): Float = (a.toDouble() * b.toDouble() + c.toDouble()).toFloat()
    fun fmaf64(a: Double, b: Double, c: Double): Double = a * b + c
    fun sqrtf32(x: Float): Float = sqrt(x)
    fun sqrtf64(x: Double): Double = sqrt(x)

    fun umulhU64(a: ULong, b: ULong): ULong {
        val aLo = a and 0xFFFFFFFFuL
        val aHi = a shr 32
        val bLo = b and 0xFFFFFFFFuL
        val bHi = b shr 32
        val p0 = aLo * bLo
        val p1 = aLo * bHi
        val p2 = aHi * bLo
        val p3 = aHi * bHi
        val mid = (p0 shr 32) + (p1 and 0xFFFFFFFFuL) + (p2 and 0xFFFFFFFFuL)
        return p3 + (p1 shr 32) + (p2 shr 32) + (mid shr 32)
    }

    fun smulhI64(a: Long, b: Long): Long {
        var hi = umulhU64(a.toULong(), b.toULong())
        if (a < 0L) hi -= b.toULong()
        if (b < 0L) hi -= a.toULong()
        return hi.toLong()
    }

    fun simdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long) {
        for (lane in 0 until 4) {
            val off = lane.toLong() * 4L
            storeF32(dstAddr + off, fmaf32(loadF32(aAddr + off), loadF32(bAddr + off), loadF32(cAddr + off)))
        }
    }

    fun simdDotF32(aAddr: Long, bAddr: Long, len: Long): Float {
        var sum = 0.0f
        var i = 0L
        while (i < len) {
            val off = i * 4L
            sum = fmaf32(loadF32(aAddr + off), loadF32(bAddr + off), sum)
            i += 1L
        }
        return sum
    }
}

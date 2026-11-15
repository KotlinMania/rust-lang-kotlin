package io.github.kotlinmania.rustlang.core.intrinsics

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.truncate
import kotlin.math.withSign

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

    private fun roundTiesAwayF32(x: Float): Float {
        if (x.isNaN() || x.isInfinite()) return x
        val t = truncate(x)
        return if (abs(x - t) >= 0.5f) t + 1.0f.withSign(x) else t
    }

    private fun roundTiesAwayF64(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return x
        val t = truncate(x)
        return if (abs(x - t) >= 0.5) t + 1.0.withSign(x) else t
    }

    private fun minNumF32(a: Float, b: Float): Float =
        when {
            a.isNaN() -> b
            b.isNaN() -> a
            a == 0.0f && b == 0.0f -> Float.fromBits(a.toRawBits() or b.toRawBits())
            else -> min(a, b)
        }

    private fun minNumF64(a: Double, b: Double): Double =
        when {
            a.isNaN() -> b
            b.isNaN() -> a
            a == 0.0 && b == 0.0 -> Double.fromBits(a.toRawBits() or b.toRawBits())
            else -> min(a, b)
        }

    private fun maxNumF32(a: Float, b: Float): Float =
        when {
            a.isNaN() -> b
            b.isNaN() -> a
            a == 0.0f && b == 0.0f -> Float.fromBits(a.toRawBits() and b.toRawBits())
            else -> max(a, b)
        }

    private fun maxNumF64(a: Double, b: Double): Double =
        when {
            a.isNaN() -> b
            b.isNaN() -> a
            a == 0.0 && b == 0.0 -> Double.fromBits(a.toRawBits() and b.toRawBits())
            else -> max(a, b)
        }

    private fun mulU64WidePair(a: ULong, b: ULong): Pair<ULong, ULong> {
        val aLo = a and 0xFFFF_FFFFuL
        val aHi = a shr 32
        val bLo = b and 0xFFFF_FFFFuL
        val bHi = b shr 32
        val p00 = aLo * bLo
        val p01 = aLo * bHi
        val p10 = aHi * bLo
        val p11 = aHi * bHi
        val mid = (p00 shr 32) + (p01 and 0xFFFF_FFFFuL) + (p10 and 0xFFFF_FFFFuL)
        val lo = (p00 and 0xFFFF_FFFFuL) or ((mid and 0xFFFF_FFFFuL) shl 32)
        val hi = p11 + (p01 shr 32) + (p10 shr 32) + (mid shr 32)
        return lo to hi
    }

    private fun u128NegRaw(lo: ULong, hi: ULong): Pair<ULong, ULong> {
        val invLo = lo.inv()
        val invHi = hi.inv()
        val resLo = invLo + 1uL
        val carry = if (resLo == 0uL) 1uL else 0uL
        return resLo to (invHi + carry)
    }

    fun ctpopU8(value: UByte): UInt = value.toInt().countOneBits().toUInt()
    fun ctpopU16(value: UShort): UInt = value.toInt().countOneBits().toUInt()
    fun ctpopU32(value: UInt): UInt = value.countOneBits().toUInt()
    fun ctpopU128(lo: ULong, hi: ULong): UInt = (lo.countOneBits() + hi.countOneBits()).toUInt()

    fun ctlzU8(value: UByte): UInt = if (value == 0u.toUByte()) 8u else (value.toInt().countLeadingZeroBits() - 24).toUInt()
    fun ctlzU16(value: UShort): UInt = if (value == 0u.toUShort()) 16u else (value.toInt().countLeadingZeroBits() - 16).toUInt()
    fun ctlzU32(value: UInt): UInt = value.countLeadingZeroBits().toUInt()
    fun ctlzU128(lo: ULong, hi: ULong): UInt = if (hi != 0uL) hi.countLeadingZeroBits().toUInt() else (64 + lo.countLeadingZeroBits()).toUInt()

    fun cttzU8(value: UByte): UInt = if (value == 0u.toUByte()) 8u else value.toInt().countTrailingZeroBits().toUInt()
    fun cttzU16(value: UShort): UInt = if (value == 0u.toUShort()) 16u else value.toInt().countTrailingZeroBits().toUInt()
    fun cttzU32(value: UInt): UInt = value.countTrailingZeroBits().toUInt()
    fun cttzU128(lo: ULong, hi: ULong): UInt = if (lo != 0uL) lo.countTrailingZeroBits().toUInt() else (64 + hi.countTrailingZeroBits()).toUInt()

    fun bswapU128(lo: ULong, hi: ULong): U128ValueOutcome = U128ValueOutcome(bswapU64(hi), bswapU64(lo))

    fun bitreverseU8(value: UByte): UByte {
        var r = 0
        var v = value.toInt() and 0xFF
        repeat(8) {
            r = (r shl 1) or (v and 1)
            v = v ushr 1
        }
        return r.toUByte()
    }

    fun bitreverseU16(value: UShort): UShort {
        var r = 0
        var v = value.toInt() and 0xFFFF
        repeat(16) {
            r = (r shl 1) or (v and 1)
            v = v ushr 1
        }
        return r.toUShort()
    }

    fun bitreverseU32(value: UInt): UInt {
        var r = 0u
        var v = value
        repeat(32) {
            r = (r shl 1) or (v and 1u)
            v = v shr 1
        }
        return r
    }

    fun bitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome = U128ValueOutcome(bitreverseU64(hi), bitreverseU64(lo))

    fun rotateLeftU8(value: UByte, n: UInt): UByte {
        val s = (n and 7u).toInt()
        if (s == 0) return value
        val x = value.toInt() and 0xFF
        return ((x shl s) or (x ushr (8 - s))).toUByte()
    }

    fun rotateLeftU16(value: UShort, n: UInt): UShort {
        val s = (n and 15u).toInt()
        if (s == 0) return value
        val x = value.toInt() and 0xFFFF
        return ((x shl s) or (x ushr (16 - s))).toUShort()
    }

    fun rotateLeftU32(value: UInt, n: UInt): UInt = value.rotateLeft((n and 31u).toInt())
    fun rotateLeftU64(value: ULong, n: UInt): ULong = value.rotateLeft((n and 63u).toInt())

    fun rotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome {
        val s = (n and 127u).toInt()
        if (s == 0) return U128ValueOutcome(lo, hi)
        if (s >= 64) {
            val k = s - 64
            if (k == 0) return U128ValueOutcome(hi, lo)
            return U128ValueOutcome((hi shl k) or (lo shr (64 - k)), (lo shl k) or (hi shr (64 - k)))
        }
        return U128ValueOutcome((lo shl s) or (hi shr (64 - s)), (hi shl s) or (lo shr (64 - s)))
    }

    fun rotateRightU8(value: UByte, n: UInt): UByte = rotateLeftU8(value, (8u - (n and 7u)) and 7u)
    fun rotateRightU16(value: UShort, n: UInt): UShort = rotateLeftU16(value, (16u - (n and 15u)) and 15u)
    fun rotateRightU32(value: UInt, n: UInt): UInt = value.rotateRight((n and 31u).toInt())
    fun rotateRightU64(value: ULong, n: UInt): ULong = value.rotateRight((n and 63u).toInt())
    fun rotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome = rotateLeftU128(lo, hi, (128u - (n and 127u)) and 127u)

    fun addWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
        val sum = a.toInt() + b.toInt()
        return OverflowU8Outcome(sum.toUByte(), sum > 0xFF)
    }

    fun addWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
        val sum = a.toInt() + b.toInt()
        return OverflowU16Outcome(sum.toUShort(), sum > 0xFFFF)
    }

    fun addWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
        val res = a + b
        return OverflowU32Outcome(res, res < a)
    }

    fun addWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome {
        val res = a + b
        return OverflowU64Outcome(res, res < a)
    }

    fun addWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
        val sum = a.toInt() + b.toInt()
        return OverflowI8Outcome(sum.toByte(), sum < Byte.MIN_VALUE || sum > Byte.MAX_VALUE)
    }

    fun addWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
        val sum = a.toInt() + b.toInt()
        return OverflowI16Outcome(sum.toShort(), sum < Short.MIN_VALUE || sum > Short.MAX_VALUE)
    }

    fun addWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
        val sum = a.toLong() + b.toLong()
        return OverflowI32Outcome(sum.toInt(), sum < Int.MIN_VALUE || sum > Int.MAX_VALUE)
    }

    fun addWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
        val res = a + b
        val ovf = ((a xor res) and (b xor res)) < 0L
        return OverflowI64Outcome(res, ovf)
    }

    fun addWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
        val sLo = aLo + bLo
        val cLo = if (sLo < aLo) 1uL else 0uL
        val sHi1 = aHi + bHi
        val cHi1 = if (sHi1 < aHi) 1uL else 0uL
        val sHi = sHi1 + cLo
        val cHi2 = if (sHi < sHi1) 1uL else 0uL
        return OverflowU128Outcome(sLo, sHi, (cHi1 or cHi2) != 0uL)
    }

    fun addWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
        val u = addWithOverflowU128(aLo, aHi, bLo, bHi)
        val ovf = (((aHi xor bHi).inv() and (aHi xor u.hi)) shr 63) != 0uL
        return OverflowI128Outcome(u.lo, u.hi, ovf)
    }

    fun subWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome = OverflowU8Outcome((a.toInt() - b.toInt()).toUByte(), a < b)
    fun subWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome = OverflowU16Outcome((a.toInt() - b.toInt()).toUShort(), a < b)
    fun subWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome = OverflowU32Outcome(a - b, a < b)
    fun subWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome = OverflowU64Outcome(a - b, a < b)

    fun subWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
        val d = a.toInt() - b.toInt()
        return OverflowI8Outcome(d.toByte(), d < Byte.MIN_VALUE || d > Byte.MAX_VALUE)
    }

    fun subWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
        val d = a.toInt() - b.toInt()
        return OverflowI16Outcome(d.toShort(), d < Short.MIN_VALUE || d > Short.MAX_VALUE)
    }

    fun subWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
        val d = a.toLong() - b.toLong()
        return OverflowI32Outcome(d.toInt(), d < Int.MIN_VALUE || d > Int.MAX_VALUE)
    }

    fun subWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
        val res = a - b
        val ovf = ((a xor b) and (a xor res)) < 0L
        return OverflowI64Outcome(res, ovf)
    }

    fun subWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
        val borrow = if (aLo < bLo) 1uL else 0uL
        val rLo = aLo - bLo
        val rHi = aHi - bHi - borrow
        val ovf = aHi < bHi || (aHi == bHi && aLo < bLo)
        return OverflowU128Outcome(rLo, rHi, ovf)
    }

    fun subWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
        val u = subWithOverflowU128(aLo, aHi, bLo, bHi)
        val ovf = (((aHi xor bHi) and (aHi xor u.hi)) shr 63) != 0uL
        return OverflowI128Outcome(u.lo, u.hi, ovf)
    }

    fun mulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
        val p = a.toInt() * b.toInt()
        return OverflowU8Outcome(p.toUByte(), p > 0xFF)
    }

    fun mulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
        val p = a.toInt() * b.toInt()
        return OverflowU16Outcome(p.toUShort(), p > 0xFFFF)
    }

    fun mulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
        val p = a.toULong() * b.toULong()
        return OverflowU32Outcome(p.toUInt(), p > UInt.MAX_VALUE.toULong())
    }

    fun mulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome {
        val (lo, hi) = mulU64WidePair(a, b)
        return OverflowU64Outcome(lo, hi != 0uL)
    }

    fun mulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
        val p = a.toInt() * b.toInt()
        return OverflowI8Outcome(p.toByte(), p < Byte.MIN_VALUE || p > Byte.MAX_VALUE)
    }

    fun mulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
        val p = a.toInt() * b.toInt()
        return OverflowI16Outcome(p.toShort(), p < Short.MIN_VALUE || p > Short.MAX_VALUE)
    }

    fun mulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
        val p = a.toLong() * b.toLong()
        return OverflowI32Outcome(p.toInt(), p < Int.MIN_VALUE || p > Int.MAX_VALUE)
    }

    fun mulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
        val sA = a < 0L
        val sB = b < 0L
        val magA = if (sA) (0uL - a.toULong()) else a.toULong()
        val magB = if (sB) (0uL - b.toULong()) else b.toULong()
        val (lo, hi) = mulU64WidePair(magA, magB)
        val neg = sA != sB
        val res = if (neg) (0uL - lo).toLong() else lo.toLong()
        val ovf = hi != 0uL || if (neg) lo > 0x8000_0000_0000_0000uL else (lo shr 63) != 0uL
        return OverflowI64Outcome(res, ovf)
    }

    fun mulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
        val (p00Lo, p00Hi) = mulU64WidePair(aLo, bLo)
        val (p01Lo, p01Hi) = mulU64WidePair(aLo, bHi)
        val (p10Lo, p10Hi) = mulU64WidePair(aHi, bLo)
        val mid1 = p00Hi + p01Lo
        val c1 = if (mid1 < p00Hi) 1uL else 0uL
        val mid2 = mid1 + p10Lo
        val c2 = if (mid2 < mid1) 1uL else 0uL
        val ovf = p01Hi != 0uL || p10Hi != 0uL || c1 != 0uL || c2 != 0uL || (aHi != 0uL && bHi != 0uL)
        return OverflowU128Outcome(p00Lo, mid2, ovf)
    }

    fun mulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
        val sA = (aHi shr 63) != 0uL
        val sB = (bHi shr 63) != 0uL
        val (mALo, mAHi) = if (sA) u128NegRaw(aLo, aHi) else (aLo to aHi)
        val (mBLo, mBHi) = if (sB) u128NegRaw(bLo, bHi) else (bLo to bHi)
        val u = mulWithOverflowU128(mALo, mAHi, mBLo, mBHi)
        val neg = sA != sB
        val (resLo, resHi) = if (neg) u128NegRaw(u.lo, u.hi) else (u.lo to u.hi)
        val ovf =
            if (u.overflowed) {
                true
            } else if (neg) {
                u.hi > 0x8000_0000_0000_0000uL || (u.hi == 0x8000_0000_0000_0000uL && u.lo != 0uL)
            } else {
                (u.hi shr 63) != 0uL
            }
        return OverflowI128Outcome(resLo, resHi, ovf)
    }

    fun saturatingAddU8(a: UByte, b: UByte): UByte = addWithOverflowU8(a, b).let { if (it.overflowed) UByte.MAX_VALUE else it.value }
    fun saturatingAddU16(a: UShort, b: UShort): UShort = addWithOverflowU16(a, b).let { if (it.overflowed) UShort.MAX_VALUE else it.value }
    fun saturatingAddU32(a: UInt, b: UInt): UInt = addWithOverflowU32(a, b).let { if (it.overflowed) UInt.MAX_VALUE else it.value }
    fun saturatingAddU64(a: ULong, b: ULong): ULong = addWithOverflowU64(a, b).let { if (it.overflowed) ULong.MAX_VALUE else it.value }
    fun saturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
        addWithOverflowU128(aLo, aHi, bLo, bHi).let { if (it.overflowed) U128ValueOutcome(ULong.MAX_VALUE, ULong.MAX_VALUE) else U128ValueOutcome(it.lo, it.hi) }

    fun saturatingAddI8(a: Byte, b: Byte): Byte = addWithOverflowI8(a, b).let { if (it.overflowed) (if (it.value < 0) Byte.MAX_VALUE else Byte.MIN_VALUE) else it.value }
    fun saturatingAddI16(a: Short, b: Short): Short = addWithOverflowI16(a, b).let { if (it.overflowed) (if (it.value < 0) Short.MAX_VALUE else Short.MIN_VALUE) else it.value }
    fun saturatingAddI32(a: Int, b: Int): Int = addWithOverflowI32(a, b).let { if (it.overflowed) (if (it.value < 0) Int.MAX_VALUE else Int.MIN_VALUE) else it.value }
    fun saturatingAddI64(a: Long, b: Long): Long = addWithOverflowI64(a, b).let { if (it.overflowed) (if (it.value < 0) Long.MAX_VALUE else Long.MIN_VALUE) else it.value }
    fun saturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
        addWithOverflowI128(aLo, aHi, bLo, bHi).let {
            if (it.overflowed) {
                if ((it.hi shr 63) != 0uL) I128ValueOutcome(ULong.MAX_VALUE, 0x7FFF_FFFF_FFFF_FFFFuL) else I128ValueOutcome(0uL, 0x8000_0000_0000_0000uL)
            } else {
                I128ValueOutcome(it.lo, it.hi)
            }
        }

    fun saturatingSubU8(a: UByte, b: UByte): UByte = subWithOverflowU8(a, b).let { if (it.overflowed) 0u.toUByte() else it.value }
    fun saturatingSubU16(a: UShort, b: UShort): UShort = subWithOverflowU16(a, b).let { if (it.overflowed) 0u.toUShort() else it.value }
    fun saturatingSubU32(a: UInt, b: UInt): UInt = subWithOverflowU32(a, b).let { if (it.overflowed) 0u else it.value }
    fun saturatingSubU64(a: ULong, b: ULong): ULong = subWithOverflowU64(a, b).let { if (it.overflowed) 0uL else it.value }
    fun saturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
        subWithOverflowU128(aLo, aHi, bLo, bHi).let { if (it.overflowed) U128ValueOutcome(0uL, 0uL) else U128ValueOutcome(it.lo, it.hi) }

    fun saturatingSubI8(a: Byte, b: Byte): Byte = subWithOverflowI8(a, b).let { if (it.overflowed) (if (it.value < 0) Byte.MAX_VALUE else Byte.MIN_VALUE) else it.value }
    fun saturatingSubI16(a: Short, b: Short): Short = subWithOverflowI16(a, b).let { if (it.overflowed) (if (it.value < 0) Short.MAX_VALUE else Short.MIN_VALUE) else it.value }
    fun saturatingSubI32(a: Int, b: Int): Int = subWithOverflowI32(a, b).let { if (it.overflowed) (if (it.value < 0) Int.MAX_VALUE else Int.MIN_VALUE) else it.value }
    fun saturatingSubI64(a: Long, b: Long): Long = subWithOverflowI64(a, b).let { if (it.overflowed) (if (it.value < 0) Long.MAX_VALUE else Long.MIN_VALUE) else it.value }
    fun saturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
        subWithOverflowI128(aLo, aHi, bLo, bHi).let {
            if (it.overflowed) {
                if ((it.hi shr 63) != 0uL) I128ValueOutcome(ULong.MAX_VALUE, 0x7FFF_FFFF_FFFF_FFFFuL) else I128ValueOutcome(0uL, 0x8000_0000_0000_0000uL)
            } else {
                I128ValueOutcome(it.lo, it.hi)
            }
        }

    fun u128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
        addWithOverflowU128(aLo, aHi, bLo, bHi).let { U128ValueOutcome(it.lo, it.hi) }

    fun u128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
        subWithOverflowU128(aLo, aHi, bLo, bHi).let { U128ValueOutcome(it.lo, it.hi) }

    fun u128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
        mulWithOverflowU128(aLo, aHi, bLo, bHi).let { U128ValueOutcome(it.lo, it.hi) }

    fun u128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome {
        if (bLo == 0uL && bHi == 0uL) return U128DivRemOutcome(0uL, 0uL, 0uL, 0uL)
        var qLo = 0uL
        var qHi = 0uL
        var rLo = 0uL
        var rHi = 0uL
        for (bit in 127 downTo 0) {
            rHi = (rHi shl 1) or (rLo shr 63)
            val nBit = if (bit >= 64) ((aHi shr (bit - 64)) and 1uL) else ((aLo shr bit) and 1uL)
            rLo = (rLo shl 1) or nBit
            if (rHi > bHi || (rHi == bHi && rLo >= bLo)) {
                val borrow = if (rLo < bLo) 1uL else 0uL
                rLo -= bLo
                rHi -= (bHi + borrow)
                if (bit >= 64) {
                    qHi = qHi or (1uL shl (bit - 64))
                } else {
                    qLo = qLo or (1uL shl bit)
                }
            }
        }
        return U128DivRemOutcome(qLo, qHi, rLo, rHi)
    }

    fun i128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome {
        if (bLo == 0uL && bHi == 0uL) return I128DivRemOutcome(0uL, 0uL, 0uL, 0uL)
        if (aHi == 0x8000_0000_0000_0000uL && aLo == 0uL && bHi == ULong.MAX_VALUE && bLo == ULong.MAX_VALUE) {
            return I128DivRemOutcome(0uL, 0x8000_0000_0000_0000uL, 0uL, 0uL)
        }
        val nNeg = (aHi shr 63) != 0uL
        val dNeg = (bHi shr 63) != 0uL
        val (mALo, mAHi) = if (nNeg) u128NegRaw(aLo, aHi) else (aLo to aHi)
        val (mBLo, mBHi) = if (dNeg) u128NegRaw(bLo, bHi) else (bLo to bHi)
        val u = u128DivRem(mALo, mAHi, mBLo, mBHi)
        val (qLo, qHi) = if (nNeg != dNeg) u128NegRaw(u.qLo, u.qHi) else (u.qLo to u.qHi)
        val (rLo, rHi) = if (nNeg) u128NegRaw(u.rLo, u.rHi) else (u.rLo to u.rHi)
        return I128DivRemOutcome(qLo, qHi, rLo, rHi)
    }

    fun u128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome {
        val s = (shift and 127u).toInt()
        if (s == 0) return U128ValueOutcome(aLo, aHi)
        if (s == 64) return U128ValueOutcome(0uL, aLo)
        if (s > 64) return U128ValueOutcome(0uL, aLo shl (s - 64))
        return U128ValueOutcome(aLo shl s, (aHi shl s) or (aLo shr (64 - s)))
    }

    fun u128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome {
        val s = (shift and 127u).toInt()
        if (s == 0) return U128ValueOutcome(aLo, aHi)
        if (s == 64) return U128ValueOutcome(aHi, 0uL)
        if (s > 64) return U128ValueOutcome(aHi shr (s - 64), 0uL)
        return U128ValueOutcome((aLo shr s) or (aHi shl (64 - s)), aHi shr s)
    }

    fun i128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome {
        val s = (shift and 127u).toInt()
        val signFill = if ((aHi shr 63) != 0uL) ULong.MAX_VALUE else 0uL
        if (s == 0) return I128ValueOutcome(aLo, aHi)
        if (s == 64) return I128ValueOutcome(aHi, signFill)
        if (s > 64) {
            val k = s - 64
            return I128ValueOutcome((aHi shr k) or (signFill shl (64 - k)), signFill)
        }
        return I128ValueOutcome((aLo shr s) or (aHi shl (64 - s)), (aHi shr s) or (signFill shl (64 - s)))
    }

    fun sinf32(x: Float): Float = sin(x)
    fun sinf64(x: Double): Double = sin(x)
    fun cosf32(x: Float): Float = cos(x)
    fun cosf64(x: Double): Double = cos(x)
    fun expf32(x: Float): Float = exp(x)
    fun expf64(x: Double): Double = exp(x)
    fun exp2f32(x: Float): Float = (2.0f).pow(x)
    fun exp2f64(x: Double): Double = (2.0).pow(x)
    fun logf32(x: Float): Float = ln(x)
    fun logf64(x: Double): Double = ln(x)
    fun log2f32(x: Float): Float = log2(x)
    fun log2f64(x: Double): Double = log2(x)
    fun log10f32(x: Float): Float = log10(x)
    fun log10f64(x: Double): Double = log10(x)
    fun truncf32(x: Float): Float = truncate(x)
    fun truncf64(x: Double): Double = truncate(x)
    fun roundf32(x: Float): Float = roundTiesAwayF32(x)
    fun roundf64(x: Double): Double = roundTiesAwayF64(x)
    fun roundTiesEvenf32(x: Float): Float = round(x)
    fun roundTiesEvenf64(x: Double): Double = round(x)
    fun ceilf32(x: Float): Float = ceil(x)
    fun ceilf64(x: Double): Double = ceil(x)
    fun floorf32(x: Float): Float = floor(x)
    fun floorf64(x: Double): Double = floor(x)
    fun fabsf32(x: Float): Float = abs(x)
    fun fabsf64(x: Double): Double = abs(x)
    fun powf32(a: Float, b: Float): Float = a.pow(b)
    fun powf64(a: Double, b: Double): Double = a.pow(b)

    fun powif32(x: Float, exp: Int): Float {
        if (exp == 0) return 1.0f
        var e = if (exp < 0) (0u - exp.toUInt()) else exp.toUInt()
        var base = if (exp < 0) (1.0f / x) else x
        var acc = 1.0f
        while (e != 0u) {
            if ((e and 1u) != 0u) acc *= base
            e = e shr 1
            if (e != 0u) base *= base
        }
        return acc
    }

    fun powif64(x: Double, exp: Int): Double {
        if (exp == 0) return 1.0
        var e = if (exp < 0) (0u - exp.toUInt()) else exp.toUInt()
        var base = if (exp < 0) (1.0 / x) else x
        var acc = 1.0
        while (e != 0u) {
            if ((e and 1u) != 0u) acc *= base
            e = e shr 1
            if (e != 0u) base *= base
        }
        return acc
    }

    fun copysignf32(a: Float, b: Float): Float = a.withSign(b)
    fun copysignf64(a: Double, b: Double): Double = a.withSign(b)
    fun minnumf32(a: Float, b: Float): Float = minNumF32(a, b)
    fun minnumf64(a: Double, b: Double): Double = minNumF64(a, b)
    fun maxnumf32(a: Float, b: Float): Float = maxNumF32(a, b)
    fun maxnumf64(a: Double, b: Double): Double = maxNumF64(a, b)

    fun floatToU64Unchecked(v: Float): ULong = doubleToU64Unchecked(v.toDouble())

    fun doubleToU64Unchecked(v: Double): ULong {
        if (!(v >= 0.0)) return 0uL
        val two63 = 9223372036854775808.0
        return if (v >= two63) {
            ((v - two63).toLong().toULong()) or 0x8000_0000_0000_0000uL
        } else {
            v.toLong().toULong()
        }
    }

    fun floatToU128Unchecked(v: Float): U128ValueOutcome = doubleToU128Unchecked(v.toDouble())

    fun doubleToU128Unchecked(v: Double): U128ValueOutcome {
        if (!(v >= 0.0)) return U128ValueOutcome(0uL, 0uL)
        val two64 = 18446744073709551616.0
        val hi = if (v >= two64) doubleToU64Unchecked(v / two64) else 0uL
        val rem = v - hi.toDouble() * two64
        val lo = doubleToU64Unchecked(rem)
        return U128ValueOutcome(lo, hi)
    }
}

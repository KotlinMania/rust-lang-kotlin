@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.intrinsics

internal object KmCoreJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/native/$hostPlatform-$hostArch/$libFileName"
        val stream = KmCoreJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
    }


    @JvmStatic external fun kmLoadU8(addr: Long): Byte
    @JvmStatic external fun kmLoadU16(addr: Long): Short
    @JvmStatic external fun kmLoadU32(addr: Long): Int
    @JvmStatic external fun kmLoadU64(addr: Long): Long
    @JvmStatic external fun kmLoadF32(addr: Long): Float
    @JvmStatic external fun kmLoadF64(addr: Long): Double

    @JvmStatic external fun kmStoreU8(addr: Long, value: Byte)
    @JvmStatic external fun kmStoreU16(addr: Long, value: Short)
    @JvmStatic external fun kmStoreU32(addr: Long, value: Int)
    @JvmStatic external fun kmStoreU64(addr: Long, value: Long)
    @JvmStatic external fun kmStoreF32(addr: Long, value: Float)
    @JvmStatic external fun kmStoreF64(addr: Long, value: Double)

    @JvmStatic external fun kmVolatileLoadU8(addr: Long): Byte
    @JvmStatic external fun kmVolatileLoadU16(addr: Long): Short
    @JvmStatic external fun kmVolatileLoadU32(addr: Long): Int
    @JvmStatic external fun kmVolatileLoadU64(addr: Long): Long

    @JvmStatic external fun kmVolatileStoreU8(addr: Long, value: Byte)
    @JvmStatic external fun kmVolatileStoreU16(addr: Long, value: Short)
    @JvmStatic external fun kmVolatileStoreU32(addr: Long, value: Int)
    @JvmStatic external fun kmVolatileStoreU64(addr: Long, value: Long)

    @JvmStatic external fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long)
    @JvmStatic external fun kmCopy(dst: Long, src: Long, bytes: Long)
    @JvmStatic external fun kmWriteBytes(dst: Long, value: Byte, bytes: Long)
    @JvmStatic external fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int

    @JvmStatic external fun kmAlloc(size: Long, align: Long): Long
    @JvmStatic external fun kmAllocZeroed(size: Long, align: Long): Long
    @JvmStatic external fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long
    @JvmStatic external fun kmDealloc(ptr: Long, size: Long, align: Long)

    @JvmStatic external fun kmAtomicFence(ord: Int)
    @JvmStatic external fun kmAtomicSignalFence(ord: Int)
    @JvmStatic external fun kmAtomicLoadU8(addr: Long, ord: Int): Byte
    @JvmStatic external fun kmAtomicLoadU16(addr: Long, ord: Int): Short
    @JvmStatic external fun kmAtomicLoadU32(addr: Long, ord: Int): Int
    @JvmStatic external fun kmAtomicLoadU64(addr: Long, ord: Int): Long
    @JvmStatic external fun kmAtomicStoreU8(addr: Long, value: Byte, ord: Int)
    @JvmStatic external fun kmAtomicStoreU16(addr: Long, value: Short, ord: Int)
    @JvmStatic external fun kmAtomicStoreU32(addr: Long, value: Int, ord: Int)
    @JvmStatic external fun kmAtomicStoreU64(addr: Long, value: Long, ord: Int)
    @JvmStatic external fun kmAtomicXchgU8(addr: Long, value: Byte, ord: Int): Byte
    @JvmStatic external fun kmAtomicXchgU16(addr: Long, value: Short, ord: Int): Short
    @JvmStatic external fun kmAtomicXchgU32(addr: Long, value: Int, ord: Int): Int
    @JvmStatic external fun kmAtomicXchgU64(addr: Long, value: Long, ord: Int): Long
    @JvmStatic external fun kmAtomicCxchgU8Packed(addr: Long, expected: Byte, desired: Byte, succ: Int, fail: Int): Long
    @JvmStatic external fun kmAtomicCxchgU16Packed(addr: Long, expected: Short, desired: Short, succ: Int, fail: Int): Long
    @JvmStatic external fun kmAtomicCxchgU32Packed(addr: Long, expected: Int, desired: Int, succ: Int, fail: Int): Long
    @JvmStatic external fun kmAtomicCxchgU64Out(addr: Long, expected: Long, desired: Long, succ: Int, fail: Int, outBuf: LongArray)
    @JvmStatic external fun kmAtomicRmwU8(addr: Long, value: Byte, op: Int, ord: Int): Byte
    @JvmStatic external fun kmAtomicRmwU16(addr: Long, value: Short, op: Int, ord: Int): Short
    @JvmStatic external fun kmAtomicRmwU32(addr: Long, value: Int, op: Int, ord: Int): Int
    @JvmStatic external fun kmAtomicRmwU64(addr: Long, value: Long, op: Int, ord: Int): Long

    @JvmStatic external fun kmCtpopU64(value: Long): Int
    @JvmStatic external fun kmCtlzU64(value: Long): Int
    @JvmStatic external fun kmCttzU64(value: Long): Int
    @JvmStatic external fun kmBswapU16(value: Short): Short
    @JvmStatic external fun kmBswapU32(value: Int): Int
    @JvmStatic external fun kmBswapU64(value: Long): Long
    @JvmStatic external fun kmBitreverseU64(value: Long): Long
    @JvmStatic external fun kmFmaf32(a: Float, b: Float, c: Float): Float
    @JvmStatic external fun kmFmaf64(a: Double, b: Double, c: Double): Double
    @JvmStatic external fun kmSqrtf32(x: Float): Float
    @JvmStatic external fun kmSqrtf64(x: Double): Double
    @JvmStatic external fun kmAbort()

    @JvmStatic external fun kmSpinLoopHint()
    @JvmStatic external fun kmBlackBoxU64(value: Long): Long
    @JvmStatic external fun kmUmulhU64(a: Long, b: Long): Long
    @JvmStatic external fun kmSmulhI64(a: Long, b: Long): Long
    @JvmStatic external fun kmAtomicCxchgU128Out(addr: Long, expLo: Long, expHi: Long, desLo: Long, desHi: Long, outBuf: LongArray)
    @JvmStatic external fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long)
    @JvmStatic external fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float
}

public actual fun kmLoadU8(addr: Long): UByte = KmCoreJniBridge.kmLoadU8(addr).toUByte()
public actual fun kmLoadU16(addr: Long): UShort = KmCoreJniBridge.kmLoadU16(addr).toUShort()
public actual fun kmLoadU32(addr: Long): UInt = KmCoreJniBridge.kmLoadU32(addr).toUInt()
public actual fun kmLoadU64(addr: Long): ULong = KmCoreJniBridge.kmLoadU64(addr).toULong()
public actual fun kmLoadF32(addr: Long): Float = KmCoreJniBridge.kmLoadF32(addr)
public actual fun kmLoadF64(addr: Long): Double = KmCoreJniBridge.kmLoadF64(addr)

public actual fun kmStoreU8(addr: Long, value: UByte): Unit = KmCoreJniBridge.kmStoreU8(addr, value.toByte())
public actual fun kmStoreU16(addr: Long, value: UShort): Unit = KmCoreJniBridge.kmStoreU16(addr, value.toShort())
public actual fun kmStoreU32(addr: Long, value: UInt): Unit = KmCoreJniBridge.kmStoreU32(addr, value.toInt())
public actual fun kmStoreU64(addr: Long, value: ULong): Unit = KmCoreJniBridge.kmStoreU64(addr, value.toLong())
public actual fun kmStoreF32(addr: Long, value: Float): Unit = KmCoreJniBridge.kmStoreF32(addr, value)
public actual fun kmStoreF64(addr: Long, value: Double): Unit = KmCoreJniBridge.kmStoreF64(addr, value)

public actual fun kmVolatileLoadU8(addr: Long): UByte = KmCoreJniBridge.kmVolatileLoadU8(addr).toUByte()
public actual fun kmVolatileLoadU16(addr: Long): UShort = KmCoreJniBridge.kmVolatileLoadU16(addr).toUShort()
public actual fun kmVolatileLoadU32(addr: Long): UInt = KmCoreJniBridge.kmVolatileLoadU32(addr).toUInt()
public actual fun kmVolatileLoadU64(addr: Long): ULong = KmCoreJniBridge.kmVolatileLoadU64(addr).toULong()

public actual fun kmVolatileStoreU8(addr: Long, value: UByte): Unit = KmCoreJniBridge.kmVolatileStoreU8(addr, value.toByte())
public actual fun kmVolatileStoreU16(addr: Long, value: UShort): Unit = KmCoreJniBridge.kmVolatileStoreU16(addr, value.toShort())
public actual fun kmVolatileStoreU32(addr: Long, value: UInt): Unit = KmCoreJniBridge.kmVolatileStoreU32(addr, value.toInt())
public actual fun kmVolatileStoreU64(addr: Long, value: ULong): Unit = KmCoreJniBridge.kmVolatileStoreU64(addr, value.toLong())

public actual fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long): Unit =
    KmCoreJniBridge.kmCopyNonoverlapping(dst, src, bytes)
public actual fun kmCopy(dst: Long, src: Long, bytes: Long): Unit =
    KmCoreJniBridge.kmCopy(dst, src, bytes)
public actual fun kmWriteBytes(dst: Long, value: UByte, bytes: Long): Unit =
    KmCoreJniBridge.kmWriteBytes(dst, value.toByte(), bytes)
public actual fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    KmCoreJniBridge.kmCompareBytes(left, right, bytes)

public actual fun kmAlloc(size: Long, align: Long): Long = KmCoreJniBridge.kmAlloc(size, align)
public actual fun kmAllocZeroed(size: Long, align: Long): Long = KmCoreJniBridge.kmAllocZeroed(size, align)
public actual fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    KmCoreJniBridge.kmRealloc(ptr, oldSize, align, newSize)
public actual fun kmDealloc(ptr: Long, size: Long, align: Long): Unit =
    KmCoreJniBridge.kmDealloc(ptr, size, align)

public actual fun kmAtomicFence(ord: Int): Unit = KmCoreJniBridge.kmAtomicFence(ord)
public actual fun kmAtomicSignalFence(ord: Int): Unit = KmCoreJniBridge.kmAtomicSignalFence(ord)
public actual fun kmAtomicLoadU8(addr: Long, ord: Int): UByte = KmCoreJniBridge.kmAtomicLoadU8(addr, ord).toUByte()
public actual fun kmAtomicLoadU16(addr: Long, ord: Int): UShort = KmCoreJniBridge.kmAtomicLoadU16(addr, ord).toUShort()
public actual fun kmAtomicLoadU32(addr: Long, ord: Int): UInt = KmCoreJniBridge.kmAtomicLoadU32(addr, ord).toUInt()
public actual fun kmAtomicLoadU64(addr: Long, ord: Int): ULong = KmCoreJniBridge.kmAtomicLoadU64(addr, ord).toULong()
public actual fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU8(addr, value.toByte(), ord)
public actual fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU16(addr, value.toShort(), ord)
public actual fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU32(addr, value.toInt(), ord)
public actual fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU64(addr, value.toLong(), ord)
public actual fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte = KmCoreJniBridge.kmAtomicXchgU8(addr, value.toByte(), ord).toUByte()
public actual fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort = KmCoreJniBridge.kmAtomicXchgU16(addr, value.toShort(), ord).toUShort()
public actual fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt = KmCoreJniBridge.kmAtomicXchgU32(addr, value.toInt(), ord).toUInt()
public actual fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong = KmCoreJniBridge.kmAtomicXchgU64(addr, value.toLong(), ord).toULong()

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU8Packed(addr, expected.toByte(), desired.toByte(), succ, fail)
    return AtomicCxchgU8Outcome((packed and 0xFFL).toUByte(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU16Packed(addr, expected.toShort(), desired.toShort(), succ, fail)
    return AtomicCxchgU16Outcome((packed and 0xFFFFL).toUShort(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU32Packed(addr, expected.toInt(), desired.toInt(), succ, fail)
    return AtomicCxchgU32Outcome((packed and 0xFFFFFFFFL).toUInt(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmAtomicCxchgU64Out(addr, expected.toLong(), desired.toLong(), succ, fail, outBuf)
    return AtomicCxchgU64Outcome(outBuf[0].toULong(), outBuf[1] != 0L)
}

public actual fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    KmCoreJniBridge.kmAtomicRmwU8(addr, value.toByte(), op, ord).toUByte()
public actual fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    KmCoreJniBridge.kmAtomicRmwU16(addr, value.toShort(), op, ord).toUShort()
public actual fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    KmCoreJniBridge.kmAtomicRmwU32(addr, value.toInt(), op, ord).toUInt()
public actual fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    KmCoreJniBridge.kmAtomicRmwU64(addr, value.toLong(), op, ord).toULong()

public actual fun kmCtpopU64(value: ULong): UInt = KmCoreJniBridge.kmCtpopU64(value.toLong()).toUInt()
public actual fun kmCtlzU64(value: ULong): UInt = KmCoreJniBridge.kmCtlzU64(value.toLong()).toUInt()
public actual fun kmCttzU64(value: ULong): UInt = KmCoreJniBridge.kmCttzU64(value.toLong()).toUInt()
public actual fun kmBswapU16(value: UShort): UShort = KmCoreJniBridge.kmBswapU16(value.toShort()).toUShort()
public actual fun kmBswapU32(value: UInt): UInt = KmCoreJniBridge.kmBswapU32(value.toInt()).toUInt()
public actual fun kmBswapU64(value: ULong): ULong = KmCoreJniBridge.kmBswapU64(value.toLong()).toULong()
public actual fun kmBitreverseU64(value: ULong): ULong = KmCoreJniBridge.kmBitreverseU64(value.toLong()).toULong()
public actual fun kmFmaf32(a: Float, b: Float, c: Float): Float = KmCoreJniBridge.kmFmaf32(a, b, c)
public actual fun kmFmaf64(a: Double, b: Double, c: Double): Double = KmCoreJniBridge.kmFmaf64(a, b, c)
public actual fun kmSqrtf32(x: Float): Float = KmCoreJniBridge.kmSqrtf32(x)
public actual fun kmSqrtf64(x: Double): Double = KmCoreJniBridge.kmSqrtf64(x)
public actual fun kmAbort(): Nothing {
    KmCoreJniBridge.kmAbort()
    error("Unreachable after kmAbort")
}

public actual fun kmSpinLoopHint(): Unit = KmCoreJniBridge.kmSpinLoopHint()
public actual fun kmBlackBoxU64(value: ULong): ULong = KmCoreJniBridge.kmBlackBoxU64(value.toLong()).toULong()
public actual fun kmUmulhU64(a: ULong, b: ULong): ULong = KmCoreJniBridge.kmUmulhU64(a.toLong(), b.toLong()).toULong()
public actual fun kmSmulhI64(a: Long, b: Long): Long = KmCoreJniBridge.kmSmulhI64(a, b)
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmAtomicCxchgU128Out(addr, expLo.toLong(), expHi.toLong(), desLo.toLong(), desHi.toLong(), outBuf)
    return AtomicCxchgU128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long): Unit =
    KmCoreJniBridge.kmSimdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
public actual fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    KmCoreJniBridge.kmSimdDotF32(aAddr, bAddr, len)


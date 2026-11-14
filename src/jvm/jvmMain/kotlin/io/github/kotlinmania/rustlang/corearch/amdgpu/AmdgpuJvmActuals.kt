@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.amdgpu

private object AmdgpuJniBridge {
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
                "windows" -> "corearch_jni.dll"
                "macos" -> "libcorearch_jni.dylib"
                else -> "libcorearch_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = AmdgpuJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("corearch_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkitemIdX(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkitemIdY(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkitemIdZ(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkgroupIdX(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkgroupIdY(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWorkgroupIdZ(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmGroupstaticsize(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmDispatchId(): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmWavefrontsize(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmSBarrier()

    @JvmStatic
    external fun corearchAmdgpuLlvmSBarrierSignal(barrierType: Int)

    @JvmStatic
    external fun corearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType: Int): Boolean

    @JvmStatic
    external fun corearchAmdgpuLlvmSBarrierWait(barrierType: Short)

    @JvmStatic
    external fun corearchAmdgpuLlvmSGetBarrierState(barrierType: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveBarrier()

    @JvmStatic
    external fun corearchAmdgpuLlvmSchedBarrier(mask: Int)

    @JvmStatic
    external fun corearchAmdgpuLlvmSchedGroupBarrier(mask: Int, size: Int, syncId: Int)

    @JvmStatic
    external fun corearchAmdgpuLlvmSSleep(count: Int)

    @JvmStatic
    external fun corearchAmdgpuLlvmSSethalt(value: Int)

    @JvmStatic
    external fun corearchAmdgpuLlvmSGetpc(): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmMbcntLo(value: Int, init: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmMbcntHi(value: Int, init: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmBallot(b: Boolean): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmInverseBallot(value: Long): Boolean

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceUmin(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceMin(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceUmax(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceMax(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceAdd(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceAnd(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceOr(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveReduceXor(value: Int, strategy: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmReadfirstlaneU32(value: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmReadfirstlaneU64(value: Long): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmReadlaneU32(value: Int, lane: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmReadlaneU64(value: Long, lane: Int): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmWritelaneU32(value: Int, lane: Int, default: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWritelaneU64(value: Long, lane: Int, default: Long): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmEndpgm()

    @JvmStatic
    external fun corearchAmdgpuLlvmUpdateDpp(old: Int, src: Int, dppCtrl: Int, rowMask: Int, bankMask: Int, boundControl: Boolean): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmSMemrealtime(): Long

    @JvmStatic
    external fun corearchAmdgpuLlvmDsPermute(lane: Int, value: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmDsBpermute(lane: Int, value: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPerm(src0: Int, src1: Int, selector: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPermlane16U32(old: Int, src0: Int, src1: Int, src2: Int, fi: Boolean, boundControl: Boolean): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPermlanex16U32(old: Int, src0: Int, src1: Int, src2: Int, fi: Boolean, boundControl: Boolean): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmSGetWaveidInWorkgroup(): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPermlane64U32(value: Int): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPermlane16Var(old: Int, src0: Int, src1: Int, fi: Boolean, boundControl: Boolean): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmPermlanex16Var(old: Int, src0: Int, src1: Int, fi: Boolean, boundControl: Boolean): Int

    @JvmStatic
    external fun corearchAmdgpuLlvmWaveId(): Int
}

public actual fun llvmWorkitemIdX(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkitemIdX().toUInt()

public actual fun llvmWorkitemIdY(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkitemIdY().toUInt()

public actual fun llvmWorkitemIdZ(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkitemIdZ().toUInt()

public actual fun llvmWorkgroupIdX(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkgroupIdX().toUInt()

public actual fun llvmWorkgroupIdY(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkgroupIdY().toUInt()

public actual fun llvmWorkgroupIdZ(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWorkgroupIdZ().toUInt()

public actual fun llvmGroupstaticsize(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmGroupstaticsize().toUInt()

public actual fun llvmDispatchId(): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmDispatchId().toULong()

public actual fun llvmWavefrontsize(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWavefrontsize().toUInt()

public actual fun llvmSBarrier() {
    AmdgpuJniBridge.corearchAmdgpuLlvmSBarrier()
}

public actual fun llvmSBarrierSignal(barrierType: Int) {
    AmdgpuJniBridge.corearchAmdgpuLlvmSBarrierSignal(barrierType)
}

public actual fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean =
    AmdgpuJniBridge.corearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType)

public actual fun llvmSBarrierWait(barrierType: Short) {
    AmdgpuJniBridge.corearchAmdgpuLlvmSBarrierWait(barrierType)
}

public actual fun llvmSGetBarrierState(barrierType: Int): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmSGetBarrierState(barrierType).toUInt()

public actual fun llvmWaveBarrier() {
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveBarrier()
}

public actual fun llvmSchedBarrier(mask: UInt) {
    AmdgpuJniBridge.corearchAmdgpuLlvmSchedBarrier(mask.toInt())
}

public actual fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt) {
    AmdgpuJniBridge.corearchAmdgpuLlvmSchedGroupBarrier(mask.toInt(), size.toInt(), syncId.toInt())
}

public actual fun llvmSSleep(count: UInt) {
    AmdgpuJniBridge.corearchAmdgpuLlvmSSleep(count.toInt())
}

public actual fun llvmSSethalt(value: UInt): Nothing {
    AmdgpuJniBridge.corearchAmdgpuLlvmSSethalt(value.toInt())
    error("Unreachable noreturn C function llvmSSethalt")
}

public actual fun llvmSGetpc(): Long =
    AmdgpuJniBridge.corearchAmdgpuLlvmSGetpc()

public actual fun llvmMbcntLo(value: UInt, init: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmMbcntLo(value.toInt(), init.toInt()).toUInt()

public actual fun llvmMbcntHi(value: UInt, init: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmMbcntHi(value.toInt(), init.toInt()).toUInt()

public actual fun llvmBallot(b: Boolean): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmBallot(b).toULong()

public actual fun llvmInverseBallot(value: ULong): Boolean =
    AmdgpuJniBridge.corearchAmdgpuLlvmInverseBallot(value.toLong())

public actual fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceUmin(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmWaveReduceMin(value: Int, strategy: UInt): Int =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceMin(value, strategy.toInt())

public actual fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceUmax(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmWaveReduceMax(value: Int, strategy: UInt): Int =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceMax(value, strategy.toInt())

public actual fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceAdd(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceAnd(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceOr(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveReduceXor(value.toInt(), strategy.toInt()).toUInt()

public actual fun llvmReadfirstlaneU32(value: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmReadfirstlaneU32(value.toInt()).toUInt()

public actual fun llvmReadfirstlaneU64(value: ULong): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmReadfirstlaneU64(value.toLong()).toULong()

public actual fun llvmReadlaneU32(value: UInt, lane: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmReadlaneU32(value.toInt(), lane.toInt()).toUInt()

public actual fun llvmReadlaneU64(value: ULong, lane: UInt): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmReadlaneU64(value.toLong(), lane.toInt()).toULong()

public actual fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWritelaneU32(value.toInt(), lane.toInt(), default.toInt()).toUInt()

public actual fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmWritelaneU64(value.toLong(), lane.toInt(), default.toLong()).toULong()

public actual fun llvmEndpgm(): Nothing {
    AmdgpuJniBridge.corearchAmdgpuLlvmEndpgm()
    error("Unreachable noreturn C function llvmEndpgm")
}

public actual fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmUpdateDpp(old.toInt(), src.toInt(), dppCtrl.toInt(), rowMask.toInt(), bankMask.toInt(), boundControl).toUInt()

public actual fun llvmSMemrealtime(): ULong =
    AmdgpuJniBridge.corearchAmdgpuLlvmSMemrealtime().toULong()

public actual fun llvmDsPermute(lane: UInt, value: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmDsPermute(lane.toInt(), value.toInt()).toUInt()

public actual fun llvmDsBpermute(lane: UInt, value: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmDsBpermute(lane.toInt(), value.toInt()).toUInt()

public actual fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPerm(src0.toInt(), src1.toInt(), selector.toInt()).toUInt()

public actual fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPermlane16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl).toUInt()

public actual fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPermlanex16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl).toUInt()

public actual fun llvmSGetWaveidInWorkgroup(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmSGetWaveidInWorkgroup().toUInt()

public actual fun llvmPermlane64U32(value: UInt): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPermlane64U32(value.toInt()).toUInt()

public actual fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPermlane16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl).toUInt()

public actual fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmPermlanex16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl).toUInt()

public actual fun llvmWaveId(): UInt =
    AmdgpuJniBridge.corearchAmdgpuLlvmWaveId().toUInt()

public actual fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    Pair<UInt, UInt>(0u, 0u)

public actual fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    Pair<UInt, UInt>(0u, 0u)

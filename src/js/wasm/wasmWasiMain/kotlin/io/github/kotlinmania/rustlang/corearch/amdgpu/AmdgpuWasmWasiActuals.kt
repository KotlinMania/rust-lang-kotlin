@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.amdgpu

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkitemIdX")
private external fun wasiCorearchAmdgpuLlvmWorkitemIdX(): Int

public actual fun llvmWorkitemIdX(): UInt =
    wasiCorearchAmdgpuLlvmWorkitemIdX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkitemIdY")
private external fun wasiCorearchAmdgpuLlvmWorkitemIdY(): Int

public actual fun llvmWorkitemIdY(): UInt =
    wasiCorearchAmdgpuLlvmWorkitemIdY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkitemIdZ")
private external fun wasiCorearchAmdgpuLlvmWorkitemIdZ(): Int

public actual fun llvmWorkitemIdZ(): UInt =
    wasiCorearchAmdgpuLlvmWorkitemIdZ().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkgroupIdX")
private external fun wasiCorearchAmdgpuLlvmWorkgroupIdX(): Int

public actual fun llvmWorkgroupIdX(): UInt =
    wasiCorearchAmdgpuLlvmWorkgroupIdX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkgroupIdY")
private external fun wasiCorearchAmdgpuLlvmWorkgroupIdY(): Int

public actual fun llvmWorkgroupIdY(): UInt =
    wasiCorearchAmdgpuLlvmWorkgroupIdY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWorkgroupIdZ")
private external fun wasiCorearchAmdgpuLlvmWorkgroupIdZ(): Int

public actual fun llvmWorkgroupIdZ(): UInt =
    wasiCorearchAmdgpuLlvmWorkgroupIdZ().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmGroupstaticsize")
private external fun wasiCorearchAmdgpuLlvmGroupstaticsize(): Int

public actual fun llvmGroupstaticsize(): UInt =
    wasiCorearchAmdgpuLlvmGroupstaticsize().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmDispatchId")
private external fun wasiCorearchAmdgpuLlvmDispatchId(): Long

public actual fun llvmDispatchId(): ULong =
    wasiCorearchAmdgpuLlvmDispatchId().toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWavefrontsize")
private external fun wasiCorearchAmdgpuLlvmWavefrontsize(): Int

public actual fun llvmWavefrontsize(): UInt =
    wasiCorearchAmdgpuLlvmWavefrontsize().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSBarrier")
private external fun wasiCorearchAmdgpuLlvmSBarrier(): Unit

public actual fun llvmSBarrier(): Unit =
    wasiCorearchAmdgpuLlvmSBarrier()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSBarrierSignal")
private external fun wasiCorearchAmdgpuLlvmSBarrierSignal(barrierType: Int): Unit

public actual fun llvmSBarrierSignal(barrierType: Int): Unit =
    wasiCorearchAmdgpuLlvmSBarrierSignal(barrierType)

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSBarrierSignalIsfirst")
private external fun wasiCorearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType: Int): Int

public actual fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean =
    (wasiCorearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType) != 0)

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSBarrierWait")
private external fun wasiCorearchAmdgpuLlvmSBarrierWait(barrierType: Int): Unit

public actual fun llvmSBarrierWait(barrierType: Short): Unit =
    wasiCorearchAmdgpuLlvmSBarrierWait(barrierType.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSGetBarrierState")
private external fun wasiCorearchAmdgpuLlvmSGetBarrierState(barrierType: Int): Int

public actual fun llvmSGetBarrierState(barrierType: Int): UInt =
    wasiCorearchAmdgpuLlvmSGetBarrierState(barrierType).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveBarrier")
private external fun wasiCorearchAmdgpuLlvmWaveBarrier(): Unit

public actual fun llvmWaveBarrier(): Unit =
    wasiCorearchAmdgpuLlvmWaveBarrier()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSchedBarrier")
private external fun wasiCorearchAmdgpuLlvmSchedBarrier(mask: Int): Unit

public actual fun llvmSchedBarrier(mask: UInt): Unit =
    wasiCorearchAmdgpuLlvmSchedBarrier(mask.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSchedGroupBarrier")
private external fun wasiCorearchAmdgpuLlvmSchedGroupBarrier(mask: Int, size: Int, syncId: Int): Unit

public actual fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt): Unit =
    wasiCorearchAmdgpuLlvmSchedGroupBarrier(mask.toInt(), size.toInt(), syncId.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSSleep")
private external fun wasiCorearchAmdgpuLlvmSSleep(count: Int): Unit

public actual fun llvmSSleep(count: UInt): Unit =
    wasiCorearchAmdgpuLlvmSSleep(count.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSSethalt")
private external fun wasiCorearchAmdgpuLlvmSSethalt(value: Int): Unit

public actual fun llvmSSethalt(value: UInt): Nothing {
    wasiCorearchAmdgpuLlvmSSethalt(value.toInt())
    error("C noreturn function corearchAmdgpuLlvmSSethalt returned")
}

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSGetpc")
private external fun wasiCorearchAmdgpuLlvmSGetpc(): Long

public actual fun llvmSGetpc(): Long =
    wasiCorearchAmdgpuLlvmSGetpc().toLong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmMbcntLo")
private external fun wasiCorearchAmdgpuLlvmMbcntLo(value: Int, init: Int): Int

public actual fun llvmMbcntLo(value: UInt, init: UInt): UInt =
    wasiCorearchAmdgpuLlvmMbcntLo(value.toInt(), init.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmMbcntHi")
private external fun wasiCorearchAmdgpuLlvmMbcntHi(value: Int, init: Int): Int

public actual fun llvmMbcntHi(value: UInt, init: UInt): UInt =
    wasiCorearchAmdgpuLlvmMbcntHi(value.toInt(), init.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmBallot")
private external fun wasiCorearchAmdgpuLlvmBallot(b: Int): Long

public actual fun llvmBallot(b: Boolean): ULong =
    wasiCorearchAmdgpuLlvmBallot(if (b) 1 else 0).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmInverseBallot")
private external fun wasiCorearchAmdgpuLlvmInverseBallot(value: Long): Int

public actual fun llvmInverseBallot(value: ULong): Boolean =
    (wasiCorearchAmdgpuLlvmInverseBallot(value.toLong()) != 0)

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceUmin")
private external fun wasiCorearchAmdgpuLlvmWaveReduceUmin(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceUmin(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceMin")
private external fun wasiCorearchAmdgpuLlvmWaveReduceMin(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceMin(value: Int, strategy: UInt): Int =
    wasiCorearchAmdgpuLlvmWaveReduceMin(value, strategy.toInt()).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceUmax")
private external fun wasiCorearchAmdgpuLlvmWaveReduceUmax(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceUmax(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceMax")
private external fun wasiCorearchAmdgpuLlvmWaveReduceMax(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceMax(value: Int, strategy: UInt): Int =
    wasiCorearchAmdgpuLlvmWaveReduceMax(value, strategy.toInt()).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceAdd")
private external fun wasiCorearchAmdgpuLlvmWaveReduceAdd(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceAdd(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceAnd")
private external fun wasiCorearchAmdgpuLlvmWaveReduceAnd(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceAnd(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceOr")
private external fun wasiCorearchAmdgpuLlvmWaveReduceOr(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceOr(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveReduceXor")
private external fun wasiCorearchAmdgpuLlvmWaveReduceXor(value: Int, strategy: Int): Int

public actual fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt =
    wasiCorearchAmdgpuLlvmWaveReduceXor(value.toInt(), strategy.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmReadfirstlaneU32")
private external fun wasiCorearchAmdgpuLlvmReadfirstlaneU32(value: Int): Int

public actual fun llvmReadfirstlaneU32(value: UInt): UInt =
    wasiCorearchAmdgpuLlvmReadfirstlaneU32(value.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmReadfirstlaneU64")
private external fun wasiCorearchAmdgpuLlvmReadfirstlaneU64(value: Long): Long

public actual fun llvmReadfirstlaneU64(value: ULong): ULong =
    wasiCorearchAmdgpuLlvmReadfirstlaneU64(value.toLong()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmReadlaneU32")
private external fun wasiCorearchAmdgpuLlvmReadlaneU32(value: Int, lane: Int): Int

public actual fun llvmReadlaneU32(value: UInt, lane: UInt): UInt =
    wasiCorearchAmdgpuLlvmReadlaneU32(value.toInt(), lane.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmReadlaneU64")
private external fun wasiCorearchAmdgpuLlvmReadlaneU64(value: Long, lane: Int): Long

public actual fun llvmReadlaneU64(value: ULong, lane: UInt): ULong =
    wasiCorearchAmdgpuLlvmReadlaneU64(value.toLong(), lane.toInt()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWritelaneU32")
private external fun wasiCorearchAmdgpuLlvmWritelaneU32(value: Int, lane: Int, default: Int): Int

public actual fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt =
    wasiCorearchAmdgpuLlvmWritelaneU32(value.toInt(), lane.toInt(), default.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWritelaneU64")
private external fun wasiCorearchAmdgpuLlvmWritelaneU64(value: Long, lane: Int, default: Long): Long

public actual fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong =
    wasiCorearchAmdgpuLlvmWritelaneU64(value.toLong(), lane.toInt(), default.toLong()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmEndpgm")
private external fun wasiCorearchAmdgpuLlvmEndpgm(): Unit

public actual fun llvmEndpgm(): Nothing {
    wasiCorearchAmdgpuLlvmEndpgm()
    error("C noreturn function corearchAmdgpuLlvmEndpgm returned")
}

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmUpdateDpp")
private external fun wasiCorearchAmdgpuLlvmUpdateDpp(old: Int, src: Int, dppCtrl: Int, rowMask: Int, bankMask: Int, boundControl: Int): Int

public actual fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt =
    wasiCorearchAmdgpuLlvmUpdateDpp(old.toInt(), src.toInt(), dppCtrl.toInt(), rowMask.toInt(), bankMask.toInt(), if (boundControl) 1 else 0).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSMemrealtime")
private external fun wasiCorearchAmdgpuLlvmSMemrealtime(): Long

public actual fun llvmSMemrealtime(): ULong =
    wasiCorearchAmdgpuLlvmSMemrealtime().toULong()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmDsPermute")
private external fun wasiCorearchAmdgpuLlvmDsPermute(lane: Int, value: Int): Int

public actual fun llvmDsPermute(lane: UInt, value: UInt): UInt =
    wasiCorearchAmdgpuLlvmDsPermute(lane.toInt(), value.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmDsBpermute")
private external fun wasiCorearchAmdgpuLlvmDsBpermute(lane: Int, value: Int): Int

public actual fun llvmDsBpermute(lane: UInt, value: UInt): UInt =
    wasiCorearchAmdgpuLlvmDsBpermute(lane.toInt(), value.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPerm")
private external fun wasiCorearchAmdgpuLlvmPerm(src0: Int, src1: Int, selector: Int): Int

public actual fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt =
    wasiCorearchAmdgpuLlvmPerm(src0.toInt(), src1.toInt(), selector.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPermlane16U32")
private external fun wasiCorearchAmdgpuLlvmPermlane16U32(old: Int, src0: Int, src1: Int, src2: Int, fi: Int, boundControl: Int): Int

public actual fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    wasiCorearchAmdgpuLlvmPermlane16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), if (fi) 1 else 0, if (boundControl) 1 else 0).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPermlanex16U32")
private external fun wasiCorearchAmdgpuLlvmPermlanex16U32(old: Int, src0: Int, src1: Int, src2: Int, fi: Int, boundControl: Int): Int

public actual fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    wasiCorearchAmdgpuLlvmPermlanex16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), if (fi) 1 else 0, if (boundControl) 1 else 0).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmSGetWaveidInWorkgroup")
private external fun wasiCorearchAmdgpuLlvmSGetWaveidInWorkgroup(): Int

public actual fun llvmSGetWaveidInWorkgroup(): UInt =
    wasiCorearchAmdgpuLlvmSGetWaveidInWorkgroup().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPermlane64U32")
private external fun wasiCorearchAmdgpuLlvmPermlane64U32(value: Int): Int

public actual fun llvmPermlane64U32(value: UInt): UInt =
    wasiCorearchAmdgpuLlvmPermlane64U32(value.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPermlane16Var")
private external fun wasiCorearchAmdgpuLlvmPermlane16Var(old: Int, src0: Int, src1: Int, fi: Int, boundControl: Int): Int

public actual fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    wasiCorearchAmdgpuLlvmPermlane16Var(old.toInt(), src0.toInt(), src1.toInt(), if (fi) 1 else 0, if (boundControl) 1 else 0).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmPermlanex16Var")
private external fun wasiCorearchAmdgpuLlvmPermlanex16Var(old: Int, src0: Int, src1: Int, fi: Int, boundControl: Int): Int

public actual fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    wasiCorearchAmdgpuLlvmPermlanex16Var(old.toInt(), src0.toInt(), src1.toInt(), if (fi) 1 else 0, if (boundControl) 1 else 0).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchAmdgpuLlvmWaveId")
private external fun wasiCorearchAmdgpuLlvmWaveId(): Int

public actual fun llvmWaveId(): UInt =
    wasiCorearchAmdgpuLlvmWaveId().toUInt()

public actual fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAmdgpuLlvmPermlane16Swap")

public actual fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAmdgpuLlvmPermlane32Swap")

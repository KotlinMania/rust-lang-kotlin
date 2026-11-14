@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.amdgpu

import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmBallot
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmDispatchId
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmDsBpermute
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmDsPermute
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmEndpgm
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmGroupstaticsize
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmInverseBallot
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmMbcntHi
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmMbcntLo
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPerm
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPermlane16U32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPermlane16Var
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPermlane64U32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPermlanex16U32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmPermlanex16Var
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmReadfirstlaneU32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmReadfirstlaneU64
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmReadlaneU32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmReadlaneU64
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSBarrier
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSBarrierSignal
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSBarrierSignalIsfirst
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSBarrierWait
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSGetBarrierState
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSGetWaveidInWorkgroup
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSGetpc
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSMemrealtime
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSSethalt
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSSleep
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSchedBarrier
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmSchedGroupBarrier
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmUpdateDpp
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveBarrier
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveId
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceAdd
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceAnd
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceMax
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceMin
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceOr
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceUmax
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceUmin
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWaveReduceXor
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWavefrontsize
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkgroupIdX
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkgroupIdY
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkgroupIdZ
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkitemIdX
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkitemIdY
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWorkitemIdZ
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWritelaneU32
import io.github.kotlinmania.rustlang.corearch.amdgpu.cinterop.corearchAmdgpuLlvmWritelaneU64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun llvmWorkitemIdX(): UInt =
    corearchAmdgpuLlvmWorkitemIdX()

public actual fun llvmWorkitemIdY(): UInt =
    corearchAmdgpuLlvmWorkitemIdY()

public actual fun llvmWorkitemIdZ(): UInt =
    corearchAmdgpuLlvmWorkitemIdZ()

public actual fun llvmWorkgroupIdX(): UInt =
    corearchAmdgpuLlvmWorkgroupIdX()

public actual fun llvmWorkgroupIdY(): UInt =
    corearchAmdgpuLlvmWorkgroupIdY()

public actual fun llvmWorkgroupIdZ(): UInt =
    corearchAmdgpuLlvmWorkgroupIdZ()

public actual fun llvmGroupstaticsize(): UInt =
    corearchAmdgpuLlvmGroupstaticsize()

public actual fun llvmDispatchId(): ULong =
    corearchAmdgpuLlvmDispatchId()

public actual fun llvmWavefrontsize(): UInt =
    corearchAmdgpuLlvmWavefrontsize()

public actual fun llvmSBarrier() {
    corearchAmdgpuLlvmSBarrier()
}

public actual fun llvmSBarrierSignal(barrierType: Int) {
    corearchAmdgpuLlvmSBarrierSignal(barrierType)
}

public actual fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean =
    corearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType)

public actual fun llvmSBarrierWait(barrierType: Short) {
    corearchAmdgpuLlvmSBarrierWait(barrierType)
}

public actual fun llvmSGetBarrierState(barrierType: Int): UInt =
    corearchAmdgpuLlvmSGetBarrierState(barrierType)

public actual fun llvmWaveBarrier() {
    corearchAmdgpuLlvmWaveBarrier()
}

public actual fun llvmSchedBarrier(mask: UInt) {
    corearchAmdgpuLlvmSchedBarrier(mask)
}

public actual fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt) {
    corearchAmdgpuLlvmSchedGroupBarrier(mask, size, syncId)
}

public actual fun llvmSSleep(count: UInt) {
    corearchAmdgpuLlvmSSleep(count)
}

public actual fun llvmSSethalt(value: UInt): Nothing {
    corearchAmdgpuLlvmSSethalt(value)
    error("Unreachable noreturn C function llvmSSethalt")
}

public actual fun llvmSGetpc(): Long =
    corearchAmdgpuLlvmSGetpc()

public actual fun llvmMbcntLo(value: UInt, init: UInt): UInt =
    corearchAmdgpuLlvmMbcntLo(value, init)

public actual fun llvmMbcntHi(value: UInt, init: UInt): UInt =
    corearchAmdgpuLlvmMbcntHi(value, init)

public actual fun llvmBallot(b: Boolean): ULong =
    corearchAmdgpuLlvmBallot(b)

public actual fun llvmInverseBallot(value: ULong): Boolean =
    corearchAmdgpuLlvmInverseBallot(value)

public actual fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceUmin(value, strategy)

public actual fun llvmWaveReduceMin(value: Int, strategy: UInt): Int =
    corearchAmdgpuLlvmWaveReduceMin(value, strategy)

public actual fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceUmax(value, strategy)

public actual fun llvmWaveReduceMax(value: Int, strategy: UInt): Int =
    corearchAmdgpuLlvmWaveReduceMax(value, strategy)

public actual fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceAdd(value, strategy)

public actual fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceAnd(value, strategy)

public actual fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceOr(value, strategy)

public actual fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt =
    corearchAmdgpuLlvmWaveReduceXor(value, strategy)

public actual fun llvmReadfirstlaneU32(value: UInt): UInt =
    corearchAmdgpuLlvmReadfirstlaneU32(value)

public actual fun llvmReadfirstlaneU64(value: ULong): ULong =
    corearchAmdgpuLlvmReadfirstlaneU64(value)

public actual fun llvmReadlaneU32(value: UInt, lane: UInt): UInt =
    corearchAmdgpuLlvmReadlaneU32(value, lane)

public actual fun llvmReadlaneU64(value: ULong, lane: UInt): ULong =
    corearchAmdgpuLlvmReadlaneU64(value, lane)

public actual fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt =
    corearchAmdgpuLlvmWritelaneU32(value, lane, default)

public actual fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong =
    corearchAmdgpuLlvmWritelaneU64(value, lane, default)

public actual fun llvmEndpgm(): Nothing {
    corearchAmdgpuLlvmEndpgm()
    error("Unreachable noreturn C function llvmEndpgm")
}

public actual fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt =
    corearchAmdgpuLlvmUpdateDpp(old, src, dppCtrl, rowMask, bankMask, boundControl)

public actual fun llvmSMemrealtime(): ULong =
    corearchAmdgpuLlvmSMemrealtime()

public actual fun llvmDsPermute(lane: UInt, value: UInt): UInt =
    corearchAmdgpuLlvmDsPermute(lane, value)

public actual fun llvmDsBpermute(lane: UInt, value: UInt): UInt =
    corearchAmdgpuLlvmDsBpermute(lane, value)

public actual fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt =
    corearchAmdgpuLlvmPerm(src0, src1, selector)

public actual fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    corearchAmdgpuLlvmPermlane16U32(old, src0, src1, src2, fi, boundControl)

public actual fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    corearchAmdgpuLlvmPermlanex16U32(old, src0, src1, src2, fi, boundControl)

public actual fun llvmSGetWaveidInWorkgroup(): UInt =
    corearchAmdgpuLlvmSGetWaveidInWorkgroup()

public actual fun llvmPermlane64U32(value: UInt): UInt =
    corearchAmdgpuLlvmPermlane64U32(value)

public actual fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    corearchAmdgpuLlvmPermlane16Var(old, src0, src1, fi, boundControl)

public actual fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    corearchAmdgpuLlvmPermlanex16Var(old, src0, src1, fi, boundControl)

public actual fun llvmWaveId(): UInt =
    corearchAmdgpuLlvmWaveId()

public actual fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    Pair<UInt, UInt>(0u, 0u)

public actual fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    Pair<UInt, UInt>(0u, 0u)

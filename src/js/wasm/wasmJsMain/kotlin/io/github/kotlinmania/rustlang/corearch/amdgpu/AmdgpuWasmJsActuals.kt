@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.amdgpu

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initAmdgpuNapiAddon(): Boolean

private val amdgpuNapiAvailable: Boolean = initAmdgpuNapiAddon()

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkitemIdX()")
private external fun wasmCorearchAmdgpuLlvmWorkitemIdX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkitemIdY()")
private external fun wasmCorearchAmdgpuLlvmWorkitemIdY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkitemIdZ()")
private external fun wasmCorearchAmdgpuLlvmWorkitemIdZ(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkgroupIdX()")
private external fun wasmCorearchAmdgpuLlvmWorkgroupIdX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkgroupIdY()")
private external fun wasmCorearchAmdgpuLlvmWorkgroupIdY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWorkgroupIdZ()")
private external fun wasmCorearchAmdgpuLlvmWorkgroupIdZ(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmGroupstaticsize()")
private external fun wasmCorearchAmdgpuLlvmGroupstaticsize(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmDispatchId()")
private external fun wasmCorearchAmdgpuLlvmDispatchId(): String

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWavefrontsize()")
private external fun wasmCorearchAmdgpuLlvmWavefrontsize(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSBarrier()")
private external fun wasmCorearchAmdgpuLlvmSBarrier()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSBarrierSignal(p0)")
private external fun wasmCorearchAmdgpuLlvmSBarrierSignal(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSBarrierSignalIsfirst(p0)")
private external fun wasmCorearchAmdgpuLlvmSBarrierSignalIsfirst(p0: Int): Boolean

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSBarrierWait(p0)")
private external fun wasmCorearchAmdgpuLlvmSBarrierWait(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSGetBarrierState(p0)")
private external fun wasmCorearchAmdgpuLlvmSGetBarrierState(p0: Int): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveBarrier()")
private external fun wasmCorearchAmdgpuLlvmWaveBarrier()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSchedBarrier(p0)")
private external fun wasmCorearchAmdgpuLlvmSchedBarrier(p0: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSchedGroupBarrier(p0, p1, p2)")
private external fun wasmCorearchAmdgpuLlvmSchedGroupBarrier(p0: Int, p1: Int, p2: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSSleep(p0)")
private external fun wasmCorearchAmdgpuLlvmSSleep(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSSethalt(p0)")
private external fun wasmCorearchAmdgpuLlvmSSethalt(p0: Int)

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSGetpc()")
private external fun wasmCorearchAmdgpuLlvmSGetpc(): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmMbcntLo(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmMbcntLo(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmMbcntHi(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmMbcntHi(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmBallot(p0)")
private external fun wasmCorearchAmdgpuLlvmBallot(p0: Boolean): String

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmInverseBallot(p0)")
private external fun wasmCorearchAmdgpuLlvmInverseBallot(p0: String): Boolean

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceUmin(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceUmin(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceMin(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceMin(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceUmax(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceUmax(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceMax(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceMax(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceAdd(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceAdd(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceAnd(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceAnd(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceOr(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceOr(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveReduceXor(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmWaveReduceXor(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmReadfirstlaneU32(p0)")
private external fun wasmCorearchAmdgpuLlvmReadfirstlaneU32(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmReadfirstlaneU64(p0)")
private external fun wasmCorearchAmdgpuLlvmReadfirstlaneU64(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmReadlaneU32(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmReadlaneU32(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmReadlaneU64(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmReadlaneU64(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWritelaneU32(p0, p1, p2)")
private external fun wasmCorearchAmdgpuLlvmWritelaneU32(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWritelaneU64(p0, p1, p2)")
private external fun wasmCorearchAmdgpuLlvmWritelaneU64(p0: String, p1: Int, p2: String): String

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmEndpgm()")
private external fun wasmCorearchAmdgpuLlvmEndpgm()

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmUpdateDpp(p0, p1, p2, p3, p4, p5)")
private external fun wasmCorearchAmdgpuLlvmUpdateDpp(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Boolean): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSMemrealtime()")
private external fun wasmCorearchAmdgpuLlvmSMemrealtime(): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmDsPermute(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmDsPermute(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmDsBpermute(p0, p1)")
private external fun wasmCorearchAmdgpuLlvmDsBpermute(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPerm(p0, p1, p2)")
private external fun wasmCorearchAmdgpuLlvmPerm(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPermlane16U32(p0, p1, p2, p3, p4, p5)")
private external fun wasmCorearchAmdgpuLlvmPermlane16U32(p0: Int, p1: Int, p2: Int, p3: Int, p4: Boolean, p5: Boolean): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPermlanex16U32(p0, p1, p2, p3, p4, p5)")
private external fun wasmCorearchAmdgpuLlvmPermlanex16U32(p0: Int, p1: Int, p2: Int, p3: Int, p4: Boolean, p5: Boolean): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmSGetWaveidInWorkgroup()")
private external fun wasmCorearchAmdgpuLlvmSGetWaveidInWorkgroup(): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPermlane64U32(p0)")
private external fun wasmCorearchAmdgpuLlvmPermlane64U32(p0: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPermlane16Var(p0, p1, p2, p3, p4)")
private external fun wasmCorearchAmdgpuLlvmPermlane16Var(p0: Int, p1: Int, p2: Int, p3: Boolean, p4: Boolean): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_corearch.corearchAmdgpuLlvmPermlanex16Var(p0, p1, p2, p3, p4)")
private external fun wasmCorearchAmdgpuLlvmPermlanex16Var(p0: Int, p1: Int, p2: Int, p3: Boolean, p4: Boolean): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchAmdgpuLlvmWaveId()")
private external fun wasmCorearchAmdgpuLlvmWaveId(): Int

public actual fun llvmWorkitemIdX(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkitemIdX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkitemIdY(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkitemIdY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkitemIdZ(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkitemIdZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdX(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkgroupIdX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdY(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkgroupIdY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdZ(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWorkgroupIdZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmGroupstaticsize(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmGroupstaticsize().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDispatchId(): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmDispatchId().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWavefrontsize(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWavefrontsize().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSBarrier() {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSBarrier()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSBarrierSignal(barrierType: Int) {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSBarrierSignal(barrierType)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSBarrierWait(barrierType: Short) {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSBarrierWait(barrierType.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSGetBarrierState(barrierType: Int): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmSGetBarrierState(barrierType).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveBarrier() {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmWaveBarrier()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSchedBarrier(mask: UInt) {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSchedBarrier(mask.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt) {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSchedGroupBarrier(mask.toInt(), size.toInt(), syncId.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSSleep(count: UInt) {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSSleep(count.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSSethalt(value: UInt): Nothing {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmSSethalt(value.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function llvmSSethalt")
}

public actual fun llvmSGetpc(): Long =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmSGetpc().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmMbcntLo(value: UInt, init: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmMbcntLo(value.toInt(), init.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmMbcntHi(value: UInt, init: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmMbcntHi(value.toInt(), init.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBallot(b: Boolean): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmBallot(b).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmInverseBallot(value: ULong): Boolean =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmInverseBallot(value.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceUmin(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceMin(value: Int, strategy: UInt): Int =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceMin(value, strategy.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceUmax(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceMax(value: Int, strategy: UInt): Int =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceMax(value, strategy.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceAdd(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceAnd(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceOr(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveReduceXor(value.toInt(), strategy.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadfirstlaneU32(value: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmReadfirstlaneU32(value.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadfirstlaneU64(value: ULong): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmReadfirstlaneU64(value.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadlaneU32(value: UInt, lane: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmReadlaneU32(value.toInt(), lane.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadlaneU64(value: ULong, lane: UInt): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmReadlaneU64(value.toString(), lane.toInt()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWritelaneU32(value.toInt(), lane.toInt(), default.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWritelaneU64(value.toString(), lane.toInt(), default.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmEndpgm(): Nothing {
    if (amdgpuNapiAvailable) {
        wasmCorearchAmdgpuLlvmEndpgm()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function llvmEndpgm")
}

public actual fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmUpdateDpp(old.toInt(), src.toInt(), dppCtrl.toInt(), rowMask.toInt(), bankMask.toInt(), boundControl).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSMemrealtime(): ULong =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmSMemrealtime().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDsPermute(lane: UInt, value: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmDsPermute(lane.toInt(), value.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDsBpermute(lane: UInt, value: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmDsBpermute(lane.toInt(), value.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPerm(src0.toInt(), src1.toInt(), selector.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPermlane16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPermlanex16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSGetWaveidInWorkgroup(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmSGetWaveidInWorkgroup().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane64U32(value: UInt): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPermlane64U32(value.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPermlane16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmPermlanex16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveId(): UInt =
    if (amdgpuNapiAvailable) wasmCorearchAmdgpuLlvmWaveId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")

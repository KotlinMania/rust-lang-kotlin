@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.amdgpu

private val amdgpuNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun llvmWorkitemIdX(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkitemIdX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkitemIdY(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkitemIdY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkitemIdZ(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkitemIdZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdX(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkgroupIdX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdY(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkgroupIdY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWorkgroupIdZ(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWorkgroupIdZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmGroupstaticsize(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmGroupstaticsize() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDispatchId(): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmDispatchId() as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWavefrontsize(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWavefrontsize() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSBarrier() {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSBarrier()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSBarrierSignal(barrierType: Int) {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSBarrierSignal(barrierType)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmSBarrierSignalIsfirst(barrierType) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSBarrierWait(barrierType: Short) {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSBarrierWait(barrierType.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSGetBarrierState(barrierType: Int): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmSGetBarrierState(barrierType) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveBarrier() {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmWaveBarrier()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSchedBarrier(mask: UInt) {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSchedBarrier(mask.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt) {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSchedGroupBarrier(mask.toInt(), size.toInt(), syncId.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSSleep(count: UInt) {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSSleep(count.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun llvmSSethalt(value: UInt): Nothing {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmSSethalt(value.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function llvmSSethalt")
}

public actual fun llvmSGetpc(): Long =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmSGetpc() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmMbcntLo(value: UInt, init: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmMbcntLo(value.toInt(), init.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmMbcntHi(value: UInt, init: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmMbcntHi(value.toInt(), init.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBallot(b: Boolean): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmBallot(b) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmInverseBallot(value: ULong): Boolean =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmInverseBallot(value.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceUmin(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceMin(value: Int, strategy: UInt): Int =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceMin(value, strategy.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceUmax(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceMax(value: Int, strategy: UInt): Int =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceMax(value, strategy.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceAdd(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceAnd(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceOr(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveReduceXor(value.toInt(), strategy.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadfirstlaneU32(value: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmReadfirstlaneU32(value.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadfirstlaneU64(value: ULong): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmReadfirstlaneU64(value.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadlaneU32(value: UInt, lane: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmReadlaneU32(value.toInt(), lane.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmReadlaneU64(value: ULong, lane: UInt): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmReadlaneU64(value.toString(), lane.toInt()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWritelaneU32(value.toInt(), lane.toInt(), default.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWritelaneU64(value.toString(), lane.toInt(), default.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmEndpgm(): Nothing {
    if (amdgpuNapiAddon != null) {
        amdgpuNapiAddon.corearchAmdgpuLlvmEndpgm()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function llvmEndpgm")
}

public actual fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmUpdateDpp(old.toInt(), src.toInt(), dppCtrl.toInt(), rowMask.toInt(), bankMask.toInt(), boundControl) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSMemrealtime(): ULong =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmSMemrealtime() as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDsPermute(lane: UInt, value: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmDsPermute(lane.toInt(), value.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmDsBpermute(lane: UInt, value: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmDsBpermute(lane.toInt(), value.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPerm(src0.toInt(), src1.toInt(), selector.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPermlane16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPermlanex16U32(old.toInt(), src0.toInt(), src1.toInt(), src2.toInt(), fi, boundControl) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSGetWaveidInWorkgroup(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmSGetWaveidInWorkgroup() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane64U32(value: UInt): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPermlane64U32(value.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPermlane16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmPermlanex16Var(old.toInt(), src0.toInt(), src1.toInt(), fi, boundControl) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmWaveId(): UInt =
    if (amdgpuNapiAddon != null) (amdgpuNapiAddon.corearchAmdgpuLlvmWaveId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")

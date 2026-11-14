@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.core.intrinsics

@JsFun("() => { var valObj = (function() { if (typeof globalThis !== 'undefined' && typeof globalThis.kmCoreNapiV2 !== 'undefined') return globalThis.kmCoreNapiV2; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; } var fs = req('fs'); var path = req('path'); var cwd = process.cwd ? process.cwd() : '.'; var dir = cwd; for (var d = 0; d < 8; d++) { var directCands = [ path.resolve(dir, 'node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'build/js/node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'build/wasm/node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'src/js/node-kmcore/dist/build/Release/kmcore_native.node'), path.resolve(dir, 'src/js/node-kmcore/build/Release/kmcore_native.node') ]; for (var i = 0; i < directCands.length; i++) { if (fs.existsSync(directCands[i])) { req(directCands[i]); if (globalThis.kmCoreNapiV2) return globalThis.kmCoreNapiV2; } } var scopeDirs = [ path.resolve(dir, 'node_modules/@kotlinmania'), path.resolve(dir, 'build/js/node_modules/@kotlinmania'), path.resolve(dir, 'build/wasm/node_modules/@kotlinmania') ]; for (var s = 0; s < scopeDirs.length; s++) { if (fs.existsSync(scopeDirs[s])) { var entries = fs.readdirSync(scopeDirs[s]); for (var e = 0; e < entries.length; e++) { if (entries[e].endsWith('-native-bindings')) { var pkgEntry = path.resolve(scopeDirs[s], entries[e], 'index.js'); if (fs.existsSync(pkgEntry)) { req(pkgEntry); if (globalThis.kmCoreNapiV2) return globalThis.kmCoreNapiV2; } } } } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; })(); return valObj !== null && typeof valObj !== 'undefined'; }")
private external fun initKmCoreNapiAddon(): Boolean

private val kmCoreNapiAvailable: Boolean = initKmCoreNapiAddon()

private fun lo32(v: Long): Int = v.toInt()
private fun hi32(v: Long): Int = (v ushr 32).toInt()
private fun uLo32(v: ULong): Int = v.toInt()
private fun uHi32(v: ULong): Int = (v shr 32).toInt()
private fun packI64(lo: Int, hi: Int): Long = (hi.toLong() shl 32) or (lo.toLong() and 0xFFFF_FFFFL)
private fun packU64(lo: Int, hi: Int): ULong =
    ((hi.toLong().toULong() and 0xFFFF_FFFFuL) shl 32) or (lo.toLong().toULong() and 0xFFFF_FFFFuL)

@JsFun("() => globalThis.kmCoreNapiV2.kmOut0()")
private external fun wasmKmOut0(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut1()")
private external fun wasmKmOut1(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut2()")
private external fun wasmKmOut2(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut3()")
private external fun wasmKmOut3(): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadU8(lo, hi)")
private external fun wasmKmLoadU8(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadU16(lo, hi)")
private external fun wasmKmLoadU16(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadU32(lo, hi)")
private external fun wasmKmLoadU32(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadU64(lo, hi)")
private external fun wasmKmLoadU64(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadF32(lo, hi)")
private external fun wasmKmLoadF32(lo: Int, hi: Int): Double

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmLoadF64(lo, hi)")
private external fun wasmKmLoadF64(lo: Int, hi: Int): Double

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmStoreU8(lo, hi, v)")
private external fun wasmKmStoreU8(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmStoreU16(lo, hi, v)")
private external fun wasmKmStoreU16(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmStoreU32(lo, hi, v)")
private external fun wasmKmStoreU32(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, vLo, vHi) => globalThis.kmCoreNapiV2.kmStoreU64(lo, hi, vLo, vHi)")
private external fun wasmKmStoreU64(lo: Int, hi: Int, vLo: Int, vHi: Int)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmStoreF32(lo, hi, v)")
private external fun wasmKmStoreF32(lo: Int, hi: Int, v: Double)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmStoreF64(lo, hi, v)")
private external fun wasmKmStoreF64(lo: Int, hi: Int, v: Double)

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmVolatileLoadU8(lo, hi)")
private external fun wasmKmVolatileLoadU8(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmVolatileLoadU16(lo, hi)")
private external fun wasmKmVolatileLoadU16(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmVolatileLoadU32(lo, hi)")
private external fun wasmKmVolatileLoadU32(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmVolatileLoadU64(lo, hi)")
private external fun wasmKmVolatileLoadU64(lo: Int, hi: Int): Int

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmVolatileStoreU8(lo, hi, v)")
private external fun wasmKmVolatileStoreU8(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmVolatileStoreU16(lo, hi, v)")
private external fun wasmKmVolatileStoreU16(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, v) => globalThis.kmCoreNapiV2.kmVolatileStoreU32(lo, hi, v)")
private external fun wasmKmVolatileStoreU32(lo: Int, hi: Int, v: Int)

@JsFun("(lo, hi, vLo, vHi) => globalThis.kmCoreNapiV2.kmVolatileStoreU64(lo, hi, vLo, vHi)")
private external fun wasmKmVolatileStoreU64(lo: Int, hi: Int, vLo: Int, vHi: Int)

@JsFun("(dLo, dHi, sLo, sHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmCopyNonoverlapping(dLo, dHi, sLo, sHi, bLo, bHi)")
private external fun wasmKmCopyNonoverlapping(dLo: Int, dHi: Int, sLo: Int, sHi: Int, bLo: Int, bHi: Int)

@JsFun("(dLo, dHi, sLo, sHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmCopy(dLo, dHi, sLo, sHi, bLo, bHi)")
private external fun wasmKmCopy(dLo: Int, dHi: Int, sLo: Int, sHi: Int, bLo: Int, bHi: Int)

@JsFun("(dLo, dHi, v, bLo, bHi) => globalThis.kmCoreNapiV2.kmWriteBytes(dLo, dHi, v, bLo, bHi)")
private external fun wasmKmWriteBytes(dLo: Int, dHi: Int, v: Int, bLo: Int, bHi: Int)

@JsFun("(lLo, lHi, rLo, rHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmCompareBytes(lLo, lHi, rLo, rHi, bLo, bHi)")
private external fun wasmKmCompareBytes(lLo: Int, lHi: Int, rLo: Int, rHi: Int, bLo: Int, bHi: Int): Int

@JsFun("(sLo, sHi, aLo, aHi) => globalThis.kmCoreNapiV2.kmAlloc(sLo, sHi, aLo, aHi)")
private external fun wasmKmAlloc(sLo: Int, sHi: Int, aLo: Int, aHi: Int): Int

@JsFun("(sLo, sHi, aLo, aHi) => globalThis.kmCoreNapiV2.kmAllocZeroed(sLo, sHi, aLo, aHi)")
private external fun wasmKmAllocZeroed(sLo: Int, sHi: Int, aLo: Int, aHi: Int): Int

@JsFun("(pLo, pHi, oLo, oHi, aLo, aHi, nLo, nHi) => globalThis.kmCoreNapiV2.kmRealloc(pLo, pHi, oLo, oHi, aLo, aHi, nLo, nHi)")
private external fun wasmKmRealloc(pLo: Int, pHi: Int, oLo: Int, oHi: Int, aLo: Int, aHi: Int, nLo: Int, nHi: Int): Int

@JsFun("(pLo, pHi, sLo, sHi, aLo, aHi) => globalThis.kmCoreNapiV2.kmDealloc(pLo, pHi, sLo, sHi, aLo, aHi)")
private external fun wasmKmDealloc(pLo: Int, pHi: Int, sLo: Int, sHi: Int, aLo: Int, aHi: Int)

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmAtomicFence(p0)")
private external fun wasmKmAtomicFence(p0: Int)

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmAtomicSignalFence(p0)")
private external fun wasmKmAtomicSignalFence(p0: Int)

@JsFun("(lo, hi, ord) => globalThis.kmCoreNapiV2.kmAtomicLoadU8(lo, hi, ord)")
private external fun wasmKmAtomicLoadU8(lo: Int, hi: Int, ord: Int): Int

@JsFun("(lo, hi, ord) => globalThis.kmCoreNapiV2.kmAtomicLoadU16(lo, hi, ord)")
private external fun wasmKmAtomicLoadU16(lo: Int, hi: Int, ord: Int): Int

@JsFun("(lo, hi, ord) => globalThis.kmCoreNapiV2.kmAtomicLoadU32(lo, hi, ord)")
private external fun wasmKmAtomicLoadU32(lo: Int, hi: Int, ord: Int): Int

@JsFun("(lo, hi, ord) => globalThis.kmCoreNapiV2.kmAtomicLoadU64(lo, hi, ord)")
private external fun wasmKmAtomicLoadU64(lo: Int, hi: Int, ord: Int): Int

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicStoreU8(lo, hi, v, ord)")
private external fun wasmKmAtomicStoreU8(lo: Int, hi: Int, v: Int, ord: Int)

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicStoreU16(lo, hi, v, ord)")
private external fun wasmKmAtomicStoreU16(lo: Int, hi: Int, v: Int, ord: Int)

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicStoreU32(lo, hi, v, ord)")
private external fun wasmKmAtomicStoreU32(lo: Int, hi: Int, v: Int, ord: Int)

@JsFun("(lo, hi, vLo, vHi, ord) => globalThis.kmCoreNapiV2.kmAtomicStoreU64(lo, hi, vLo, vHi, ord)")
private external fun wasmKmAtomicStoreU64(lo: Int, hi: Int, vLo: Int, vHi: Int, ord: Int)

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicXchgU8(lo, hi, v, ord)")
private external fun wasmKmAtomicXchgU8(lo: Int, hi: Int, v: Int, ord: Int): Int

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicXchgU16(lo, hi, v, ord)")
private external fun wasmKmAtomicXchgU16(lo: Int, hi: Int, v: Int, ord: Int): Int

@JsFun("(lo, hi, v, ord) => globalThis.kmCoreNapiV2.kmAtomicXchgU32(lo, hi, v, ord)")
private external fun wasmKmAtomicXchgU32(lo: Int, hi: Int, v: Int, ord: Int): Int

@JsFun("(lo, hi, vLo, vHi, ord) => globalThis.kmCoreNapiV2.kmAtomicXchgU64(lo, hi, vLo, vHi, ord)")
private external fun wasmKmAtomicXchgU64(lo: Int, hi: Int, vLo: Int, vHi: Int, ord: Int): Int

@JsFun("(lo, hi, exp, des, succ, fail) => globalThis.kmCoreNapiV2.kmAtomicCxchgU8(lo, hi, exp, des, succ, fail)")
private external fun wasmKmAtomicCxchgU8(lo: Int, hi: Int, exp: Int, des: Int, succ: Int, fail: Int): Int

@JsFun("(lo, hi, exp, des, succ, fail) => globalThis.kmCoreNapiV2.kmAtomicCxchgU16(lo, hi, exp, des, succ, fail)")
private external fun wasmKmAtomicCxchgU16(lo: Int, hi: Int, exp: Int, des: Int, succ: Int, fail: Int): Int

@JsFun("(lo, hi, exp, des, succ, fail) => globalThis.kmCoreNapiV2.kmAtomicCxchgU32(lo, hi, exp, des, succ, fail)")
private external fun wasmKmAtomicCxchgU32(lo: Int, hi: Int, exp: Int, des: Int, succ: Int, fail: Int): Int

@JsFun("(lo, hi, expLo, expHi, desLo, desHi, succ, fail) => globalThis.kmCoreNapiV2.kmAtomicCxchgU64(lo, hi, expLo, expHi, desLo, desHi, succ, fail)")
private external fun wasmKmAtomicCxchgU64(
    lo: Int,
    hi: Int,
    expLo: Int,
    expHi: Int,
    desLo: Int,
    desHi: Int,
    succ: Int,
    fail: Int,
): Int

@JsFun("(lo, hi, v, op, ord) => globalThis.kmCoreNapiV2.kmAtomicRmwU8(lo, hi, v, op, ord)")
private external fun wasmKmAtomicRmwU8(lo: Int, hi: Int, v: Int, op: Int, ord: Int): Int

@JsFun("(lo, hi, v, op, ord) => globalThis.kmCoreNapiV2.kmAtomicRmwU16(lo, hi, v, op, ord)")
private external fun wasmKmAtomicRmwU16(lo: Int, hi: Int, v: Int, op: Int, ord: Int): Int

@JsFun("(lo, hi, v, op, ord) => globalThis.kmCoreNapiV2.kmAtomicRmwU32(lo, hi, v, op, ord)")
private external fun wasmKmAtomicRmwU32(lo: Int, hi: Int, v: Int, op: Int, ord: Int): Int

@JsFun("(lo, hi, vLo, vHi, op, ord) => globalThis.kmCoreNapiV2.kmAtomicRmwU64(lo, hi, vLo, vHi, op, ord)")
private external fun wasmKmAtomicRmwU64(lo: Int, hi: Int, vLo: Int, vHi: Int, op: Int, ord: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmCtpopU64(lo, hi)")
private external fun wasmKmCtpopU64(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmCtlzU64(lo, hi)")
private external fun wasmKmCtlzU64(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmCttzU64(lo, hi)")
private external fun wasmKmCttzU64(lo: Int, hi: Int): Int

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmBswapU16(p0)")
private external fun wasmKmBswapU16(p0: Int): Int

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmBswapU32(p0)")
private external fun wasmKmBswapU32(p0: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmBswapU64(lo, hi)")
private external fun wasmKmBswapU64(lo: Int, hi: Int): Int

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmBitreverseU64(lo, hi)")
private external fun wasmKmBitreverseU64(lo: Int, hi: Int): Int

@JsFun("(p0, p1, p2) => globalThis.kmCoreNapiV2.kmFmaf32(p0, p1, p2)")
private external fun wasmKmFmaf32(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0, p1, p2) => globalThis.kmCoreNapiV2.kmFmaf64(p0, p1, p2)")
private external fun wasmKmFmaf64(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmSqrtf32(p0)")
private external fun wasmKmSqrtf32(p0: Double): Double

@JsFun("(p0) => globalThis.kmCoreNapiV2.kmSqrtf64(p0)")
private external fun wasmKmSqrtf64(p0: Double): Double

@JsFun("() => globalThis.kmCoreNapiV2.kmAbort()")
private external fun wasmKmAbort()

@JsFun("() => globalThis.kmCoreNapiV2.kmSpinLoopHint()")
private external fun wasmKmSpinLoopHint()

@JsFun("(lo, hi) => globalThis.kmCoreNapiV2.kmBlackBoxU64(lo, hi)")
private external fun wasmKmBlackBoxU64(lo: Int, hi: Int): Int

@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmUmulhU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmUmulhU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int

@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSmulhI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSmulhI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int

@JsFun("(addrLo, addrHi, expLoLo, expLoHi, expHiLo, expHiHi, desLoLo, desLoHi, desHiLo, desHiHi) => globalThis.kmCoreNapiV2.kmAtomicCxchgU128Pair(addrLo, addrHi, expLoLo, expLoHi, expHiLo, expHiHi, desLoLo, desLoHi, desHiLo, desHiHi)")
private external fun wasmKmAtomicCxchgU128Pair(
    addrLo: Int,
    addrHi: Int,
    expLoLo: Int,
    expLoHi: Int,
    expHiLo: Int,
    expHiHi: Int,
    desLoLo: Int,
    desLoHi: Int,
    desHiLo: Int,
    desHiHi: Int,
): Int

@JsFun("(dLo, dHi, aLo, aHi, bLo, bHi, cLo, cHi) => globalThis.kmCoreNapiV2.kmSimdFmaF32x4(dLo, dHi, aLo, aHi, bLo, bHi, cLo, cHi)")
private external fun wasmKmSimdFmaF32x4(dLo: Int, dHi: Int, aLo: Int, aHi: Int, bLo: Int, bHi: Int, cLo: Int, cHi: Int)

@JsFun("(aLo, aHi, bLo, bHi, lLo, lHi) => globalThis.kmCoreNapiV2.kmSimdDotF32(aLo, aHi, bLo, bHi, lLo, lHi)")
private external fun wasmKmSimdDotF32(aLo: Int, aHi: Int, bLo: Int, bHi: Int, lLo: Int, lHi: Int): Double

public actual fun kmLoadU8(addr: Long): UByte =
    if (kmCoreNapiAvailable) wasmKmLoadU8(lo32(addr), hi32(addr)).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmLoadU16(addr: Long): UShort =
    if (kmCoreNapiAvailable) wasmKmLoadU16(lo32(addr), hi32(addr)).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmLoadU32(addr: Long): UInt =
    if (kmCoreNapiAvailable) wasmKmLoadU32(lo32(addr), hi32(addr)).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmLoadU64(addr: Long): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmLoadU64(lo32(addr), hi32(addr))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }
public actual fun kmLoadF32(addr: Long): Float =
    if (kmCoreNapiAvailable) wasmKmLoadF32(lo32(addr), hi32(addr)).toFloat() else KmLinearMemoryArena.loadF32(addr)
public actual fun kmLoadF64(addr: Long): Double =
    if (kmCoreNapiAvailable) wasmKmLoadF64(lo32(addr), hi32(addr)) else KmLinearMemoryArena.loadF64(addr)

public actual fun kmStoreU8(addr: Long, value: UByte) {
    if (kmCoreNapiAvailable) wasmKmStoreU8(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmStoreU16(addr: Long, value: UShort) {
    if (kmCoreNapiAvailable) wasmKmStoreU16(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmStoreU32(addr: Long, value: UInt) {
    if (kmCoreNapiAvailable) wasmKmStoreU32(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmStoreU64(addr: Long, value: ULong) {
    if (kmCoreNapiAvailable) wasmKmStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value)) else KmLinearMemoryArena.storeU64(addr, value)
}
public actual fun kmStoreF32(addr: Long, value: Float) {
    if (kmCoreNapiAvailable) wasmKmStoreF32(lo32(addr), hi32(addr), value.toDouble()) else KmLinearMemoryArena.storeF32(addr, value)
}
public actual fun kmStoreF64(addr: Long, value: Double) {
    if (kmCoreNapiAvailable) wasmKmStoreF64(lo32(addr), hi32(addr), value) else KmLinearMemoryArena.storeF64(addr, value)
}

public actual fun kmVolatileLoadU8(addr: Long): UByte =
    if (kmCoreNapiAvailable) wasmKmVolatileLoadU8(lo32(addr), hi32(addr)).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmVolatileLoadU16(addr: Long): UShort =
    if (kmCoreNapiAvailable) wasmKmVolatileLoadU16(lo32(addr), hi32(addr)).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmVolatileLoadU32(addr: Long): UInt =
    if (kmCoreNapiAvailable) wasmKmVolatileLoadU32(lo32(addr), hi32(addr)).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmVolatileLoadU64(addr: Long): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmVolatileLoadU64(lo32(addr), hi32(addr))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }

public actual fun kmVolatileStoreU8(addr: Long, value: UByte) {
    if (kmCoreNapiAvailable) wasmKmVolatileStoreU8(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmVolatileStoreU16(addr: Long, value: UShort) {
    if (kmCoreNapiAvailable) wasmKmVolatileStoreU16(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmVolatileStoreU32(addr: Long, value: UInt) {
    if (kmCoreNapiAvailable) wasmKmVolatileStoreU32(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmVolatileStoreU64(addr: Long, value: ULong) {
    if (kmCoreNapiAvailable) wasmKmVolatileStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value)) else KmLinearMemoryArena.storeU64(addr, value)
}

public actual fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long) {
    if (kmCoreNapiAvailable) wasmKmCopyNonoverlapping(lo32(dst), hi32(dst), lo32(src), hi32(src), lo32(bytes), hi32(bytes)) else KmLinearMemoryArena.copyNonoverlapping(dst, src, bytes)
}
public actual fun kmCopy(dst: Long, src: Long, bytes: Long) {
    if (kmCoreNapiAvailable) wasmKmCopy(lo32(dst), hi32(dst), lo32(src), hi32(src), lo32(bytes), hi32(bytes)) else KmLinearMemoryArena.copy(dst, src, bytes)
}
public actual fun kmWriteBytes(dst: Long, value: UByte, bytes: Long) {
    if (kmCoreNapiAvailable) wasmKmWriteBytes(lo32(dst), hi32(dst), value.toInt(), lo32(bytes), hi32(bytes)) else KmLinearMemoryArena.writeBytes(dst, value, bytes)
}
public actual fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    if (kmCoreNapiAvailable) wasmKmCompareBytes(lo32(left), hi32(left), lo32(right), hi32(right), lo32(bytes), hi32(bytes)) else KmLinearMemoryArena.compareBytes(left, right, bytes)

public actual fun kmAlloc(size: Long, align: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmAlloc(lo32(size), hi32(size), lo32(align), hi32(align))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.alloc(size, align)
    }
public actual fun kmAllocZeroed(size: Long, align: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmAllocZeroed(lo32(size), hi32(size), lo32(align), hi32(align))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.allocZeroed(size, align)
    }
public actual fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmRealloc(lo32(ptr), hi32(ptr), lo32(oldSize), hi32(oldSize), lo32(align), hi32(align), lo32(newSize), hi32(newSize))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.realloc(ptr, oldSize, align, newSize)
    }
public actual fun kmDealloc(ptr: Long, size: Long, align: Long) {
    if (kmCoreNapiAvailable) wasmKmDealloc(lo32(ptr), hi32(ptr), lo32(size), hi32(size), lo32(align), hi32(align)) else KmLinearMemoryArena.dealloc(ptr, size, align)
}

public actual fun kmAtomicFence(ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicFence(ord)
}
public actual fun kmAtomicSignalFence(ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicSignalFence(ord)
}
public actual fun kmAtomicLoadU8(addr: Long, ord: Int): UByte =
    if (kmCoreNapiAvailable) wasmKmAtomicLoadU8(lo32(addr), hi32(addr), ord).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmAtomicLoadU16(addr: Long, ord: Int): UShort =
    if (kmCoreNapiAvailable) wasmKmAtomicLoadU16(lo32(addr), hi32(addr), ord).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmAtomicLoadU32(addr: Long, ord: Int): UInt =
    if (kmCoreNapiAvailable) wasmKmAtomicLoadU32(lo32(addr), hi32(addr), ord).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmAtomicLoadU64(addr: Long, ord: Int): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmAtomicLoadU64(lo32(addr), hi32(addr), ord)
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }

public actual fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicStoreU8(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicStoreU16(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicStoreU32(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int) {
    if (kmCoreNapiAvailable) wasmKmAtomicStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), ord) else KmLinearMemoryArena.storeU64(addr, value)
}

public actual fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte =
    if (kmCoreNapiAvailable) wasmKmAtomicXchgU8(lo32(addr), hi32(addr), value.toInt(), ord).toUByte() else KmLinearMemoryArena.atomicXchgU8(addr, value)
public actual fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort =
    if (kmCoreNapiAvailable) wasmKmAtomicXchgU16(lo32(addr), hi32(addr), value.toInt(), ord).toUShort() else KmLinearMemoryArena.atomicXchgU16(addr, value)
public actual fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt =
    if (kmCoreNapiAvailable) wasmKmAtomicXchgU32(lo32(addr), hi32(addr), value.toInt(), ord).toUInt() else KmLinearMemoryArena.atomicXchgU32(addr, value)
public actual fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmAtomicXchgU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), ord)
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.atomicXchgU64(addr, value)
    }

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome =
    if (kmCoreNapiAvailable) {
        val packed = wasmKmAtomicCxchgU8(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail)
        AtomicCxchgU8Outcome((packed and 0xFF).toUByte(), (packed ushr 8) != 0)
    } else {
        KmLinearMemoryArena.atomicCxchgU8(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome =
    if (kmCoreNapiAvailable) {
        val packed = wasmKmAtomicCxchgU16(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail)
        AtomicCxchgU16Outcome((packed and 0xFFFF).toUShort(), (packed ushr 16) != 0)
    } else {
        KmLinearMemoryArena.atomicCxchgU16(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome =
    if (kmCoreNapiAvailable) {
        val ok = wasmKmAtomicCxchgU32(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail) != 0
        AtomicCxchgU32Outcome(wasmKmOut0().toUInt(), ok)
    } else {
        KmLinearMemoryArena.atomicCxchgU32(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome =
    if (kmCoreNapiAvailable) {
        val ok = wasmKmAtomicCxchgU64(lo32(addr), hi32(addr), uLo32(expected), uHi32(expected), uLo32(desired), uHi32(desired), succ, fail) != 0
        AtomicCxchgU64Outcome(packU64(wasmKmOut0(), wasmKmOut1()), ok)
    } else {
        KmLinearMemoryArena.atomicCxchgU64(addr, expected, desired)
    }

public actual fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    if (kmCoreNapiAvailable) wasmKmAtomicRmwU8(lo32(addr), hi32(addr), value.toInt(), op, ord).toUByte() else KmLinearMemoryArena.atomicRmwU8(addr, value, op)
public actual fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    if (kmCoreNapiAvailable) wasmKmAtomicRmwU16(lo32(addr), hi32(addr), value.toInt(), op, ord).toUShort() else KmLinearMemoryArena.atomicRmwU16(addr, value, op)
public actual fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    if (kmCoreNapiAvailable) wasmKmAtomicRmwU32(lo32(addr), hi32(addr), value.toInt(), op, ord).toUInt() else KmLinearMemoryArena.atomicRmwU32(addr, value, op)
public actual fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmAtomicRmwU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), op, ord)
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.atomicRmwU64(addr, value, op)
    }

public actual fun kmCtpopU64(value: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCtpopU64(uLo32(value), uHi32(value)).toUInt() else KmLinearMemoryArena.ctpopU64(value)
public actual fun kmCtlzU64(value: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCtlzU64(uLo32(value), uHi32(value)).toUInt() else KmLinearMemoryArena.ctlzU64(value)
public actual fun kmCttzU64(value: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCttzU64(uLo32(value), uHi32(value)).toUInt() else KmLinearMemoryArena.cttzU64(value)
public actual fun kmBswapU16(value: UShort): UShort =
    if (kmCoreNapiAvailable) wasmKmBswapU16(value.toInt()).toUShort() else KmLinearMemoryArena.bswapU16(value)
public actual fun kmBswapU32(value: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmBswapU32(value.toInt()).toUInt() else KmLinearMemoryArena.bswapU32(value)
public actual fun kmBswapU64(value: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmBswapU64(uLo32(value), uHi32(value))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.bswapU64(value)
    }
public actual fun kmBitreverseU64(value: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmBitreverseU64(uLo32(value), uHi32(value))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.bitreverseU64(value)
    }
public actual fun kmFmaf32(a: Float, b: Float, c: Float): Float =
    if (kmCoreNapiAvailable) wasmKmFmaf32(a.toDouble(), b.toDouble(), c.toDouble()).toFloat() else KmLinearMemoryArena.fmaf32(a, b, c)
public actual fun kmFmaf64(a: Double, b: Double, c: Double): Double =
    if (kmCoreNapiAvailable) wasmKmFmaf64(a, b, c) else KmLinearMemoryArena.fmaf64(a, b, c)
public actual fun kmSqrtf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmSqrtf32(x.toDouble()).toFloat() else KmLinearMemoryArena.sqrtf32(x)
public actual fun kmSqrtf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmSqrtf64(x) else KmLinearMemoryArena.sqrtf64(x)
public actual fun kmAbort(): Nothing {
    if (kmCoreNapiAvailable) wasmKmAbort()
    error("Rust intrinsic abort triggered")
}

public actual fun kmSpinLoopHint() {
    if (kmCoreNapiAvailable) wasmKmSpinLoopHint()
}
public actual fun kmBlackBoxU64(value: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmBlackBoxU64(uLo32(value), uHi32(value))
        packU64(lo, wasmKmOut1())
    } else {
        value
    }
public actual fun kmUmulhU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmUmulhU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.umulhU64(a, b)
    }
public actual fun kmSmulhI64(a: Long, b: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmSmulhI64(lo32(a), hi32(a), lo32(b), hi32(b))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.smulhI64(a, b)
    }
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome =
    if (kmCoreNapiAvailable) {
        val ok =
            wasmKmAtomicCxchgU128Pair(
                lo32(addr),
                hi32(addr),
                uLo32(expLo),
                uHi32(expLo),
                uLo32(expHi),
                uHi32(expHi),
                uLo32(desLo),
                uHi32(desLo),
                uLo32(desHi),
                uHi32(desHi),
            ) != 0
        AtomicCxchgU128Outcome(
            packU64(wasmKmOut0(), wasmKmOut1()),
            packU64(wasmKmOut2(), wasmKmOut3()),
            ok,
        )
    } else {
        KmLinearMemoryArena.atomicCxchgU128Pair(addr, expLo, expHi, desLo, desHi)
    }

public actual fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long) {
    if (kmCoreNapiAvailable) wasmKmSimdFmaF32x4(lo32(dstAddr), hi32(dstAddr), lo32(aAddr), hi32(aAddr), lo32(bAddr), hi32(bAddr), lo32(cAddr), hi32(cAddr)) else KmLinearMemoryArena.simdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
}
public actual fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    if (kmCoreNapiAvailable) wasmKmSimdDotF32(lo32(aAddr), hi32(aAddr), lo32(bAddr), hi32(bAddr), lo32(len), hi32(len)).toFloat() else KmLinearMemoryArena.simdDotF32(aAddr, bAddr, len)

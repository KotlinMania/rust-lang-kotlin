@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

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

@JsFun("() => globalThis.kmCoreNapiV2.kmOut4()")
private external fun wasmKmOut4(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut5()")
private external fun wasmKmOut5(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut6()")
private external fun wasmKmOut6(): Int

@JsFun("() => globalThis.kmCoreNapiV2.kmOut7()")
private external fun wasmKmOut7(): Int

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

@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtpopU8(v)")
private external fun wasmKmCtpopU8(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtpopU16(v)")
private external fun wasmKmCtpopU16(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtpopU32(v)")
private external fun wasmKmCtpopU32(v: Int): Int
@JsFun("(lo0, hi0, lo1, hi1) => globalThis.kmCoreNapiV2.kmCtpopU128(lo0, hi0, lo1, hi1)")
private external fun wasmKmCtpopU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtlzU8(v)")
private external fun wasmKmCtlzU8(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtlzU16(v)")
private external fun wasmKmCtlzU16(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCtlzU32(v)")
private external fun wasmKmCtlzU32(v: Int): Int
@JsFun("(lo0, hi0, lo1, hi1) => globalThis.kmCoreNapiV2.kmCtlzU128(lo0, hi0, lo1, hi1)")
private external fun wasmKmCtlzU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCttzU8(v)")
private external fun wasmKmCttzU8(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCttzU16(v)")
private external fun wasmKmCttzU16(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmCttzU32(v)")
private external fun wasmKmCttzU32(v: Int): Int
@JsFun("(lo0, hi0, lo1, hi1) => globalThis.kmCoreNapiV2.kmCttzU128(lo0, hi0, lo1, hi1)")
private external fun wasmKmCttzU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int): Int
@JsFun("(lo0, hi0, lo1, hi1) => globalThis.kmCoreNapiV2.kmBswapU128(lo0, hi0, lo1, hi1)")
private external fun wasmKmBswapU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmBitreverseU8(v)")
private external fun wasmKmBitreverseU8(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmBitreverseU16(v)")
private external fun wasmKmBitreverseU16(v: Int): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmBitreverseU32(v)")
private external fun wasmKmBitreverseU32(v: Int): Int
@JsFun("(lo0, hi0, lo1, hi1) => globalThis.kmCoreNapiV2.kmBitreverseU128(lo0, hi0, lo1, hi1)")
private external fun wasmKmBitreverseU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateLeftU8(v, n)")
private external fun wasmKmRotateLeftU8(v: Int, n: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateLeftU16(v, n)")
private external fun wasmKmRotateLeftU16(v: Int, n: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateLeftU32(v, n)")
private external fun wasmKmRotateLeftU32(v: Int, n: Int): Int
@JsFun("(lo, hi, n) => globalThis.kmCoreNapiV2.kmRotateLeftU64(lo, hi, n)")
private external fun wasmKmRotateLeftU64(lo: Int, hi: Int, n: Int): Int
@JsFun("(lo0, hi0, lo1, hi1, n) => globalThis.kmCoreNapiV2.kmRotateLeftU128(lo0, hi0, lo1, hi1, n)")
private external fun wasmKmRotateLeftU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int, n: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateRightU8(v, n)")
private external fun wasmKmRotateRightU8(v: Int, n: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateRightU16(v, n)")
private external fun wasmKmRotateRightU16(v: Int, n: Int): Int
@JsFun("(v, n) => globalThis.kmCoreNapiV2.kmRotateRightU32(v, n)")
private external fun wasmKmRotateRightU32(v: Int, n: Int): Int
@JsFun("(lo, hi, n) => globalThis.kmCoreNapiV2.kmRotateRightU64(lo, hi, n)")
private external fun wasmKmRotateRightU64(lo: Int, hi: Int, n: Int): Int
@JsFun("(lo0, hi0, lo1, hi1, n) => globalThis.kmCoreNapiV2.kmRotateRightU128(lo0, hi0, lo1, hi1, n)")
private external fun wasmKmRotateRightU128(lo0: Int, hi0: Int, lo1: Int, hi1: Int, n: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowU8(a, b)")
private external fun wasmKmAddWithOverflowU8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowU16(a, b)")
private external fun wasmKmAddWithOverflowU16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowU32(a, b)")
private external fun wasmKmAddWithOverflowU32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmAddWithOverflowU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmAddWithOverflowU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmAddWithOverflowU128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmAddWithOverflowU128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowI8(a, b)")
private external fun wasmKmAddWithOverflowI8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowI16(a, b)")
private external fun wasmKmAddWithOverflowI16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmAddWithOverflowI32(a, b)")
private external fun wasmKmAddWithOverflowI32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmAddWithOverflowI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmAddWithOverflowI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmAddWithOverflowI128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmAddWithOverflowI128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowU8(a, b)")
private external fun wasmKmSubWithOverflowU8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowU16(a, b)")
private external fun wasmKmSubWithOverflowU16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowU32(a, b)")
private external fun wasmKmSubWithOverflowU32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSubWithOverflowU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSubWithOverflowU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSubWithOverflowU128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSubWithOverflowU128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowI8(a, b)")
private external fun wasmKmSubWithOverflowI8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowI16(a, b)")
private external fun wasmKmSubWithOverflowI16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSubWithOverflowI32(a, b)")
private external fun wasmKmSubWithOverflowI32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSubWithOverflowI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSubWithOverflowI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSubWithOverflowI128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSubWithOverflowI128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowU8(a, b)")
private external fun wasmKmMulWithOverflowU8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowU16(a, b)")
private external fun wasmKmMulWithOverflowU16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowU32(a, b)")
private external fun wasmKmMulWithOverflowU32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmMulWithOverflowU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmMulWithOverflowU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmMulWithOverflowU128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmMulWithOverflowU128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowI8(a, b)")
private external fun wasmKmMulWithOverflowI8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowI16(a, b)")
private external fun wasmKmMulWithOverflowI16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMulWithOverflowI32(a, b)")
private external fun wasmKmMulWithOverflowI32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmMulWithOverflowI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmMulWithOverflowI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmMulWithOverflowI128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmMulWithOverflowI128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddU8(a, b)")
private external fun wasmKmSaturatingAddU8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddU16(a, b)")
private external fun wasmKmSaturatingAddU16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddU32(a, b)")
private external fun wasmKmSaturatingAddU32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSaturatingAddU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSaturatingAddU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSaturatingAddU128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSaturatingAddU128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddI8(a, b)")
private external fun wasmKmSaturatingAddI8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddI16(a, b)")
private external fun wasmKmSaturatingAddI16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingAddI32(a, b)")
private external fun wasmKmSaturatingAddI32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSaturatingAddI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSaturatingAddI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSaturatingAddI128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSaturatingAddI128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubU8(a, b)")
private external fun wasmKmSaturatingSubU8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubU16(a, b)")
private external fun wasmKmSaturatingSubU16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubU32(a, b)")
private external fun wasmKmSaturatingSubU32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSaturatingSubU64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSaturatingSubU64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSaturatingSubU128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSaturatingSubU128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubI8(a, b)")
private external fun wasmKmSaturatingSubI8(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubI16(a, b)")
private external fun wasmKmSaturatingSubI16(a: Int, b: Int): Int
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmSaturatingSubI32(a, b)")
private external fun wasmKmSaturatingSubI32(a: Int, b: Int): Int
@JsFun("(aLo, aHi, bLo, bHi) => globalThis.kmCoreNapiV2.kmSaturatingSubI64(aLo, aHi, bLo, bHi)")
private external fun wasmKmSaturatingSubI64(aLo: Int, aHi: Int, bLo: Int, bHi: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmSaturatingSubI128(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmSaturatingSubI128(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmU128Add(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmU128Add(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmU128Sub(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmU128Sub(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmU128Mul(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmU128Mul(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmU128DivRem(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmU128DivRem(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(a0, a1, a2, a3, b0, b1, b2, b3) => globalThis.kmCoreNapiV2.kmI128DivRem(a0, a1, a2, a3, b0, b1, b2, b3)")
private external fun wasmKmI128DivRem(a0: Int, a1: Int, a2: Int, a3: Int, b0: Int, b1: Int, b2: Int, b3: Int): Int
@JsFun("(lo0, hi0, lo1, hi1, s) => globalThis.kmCoreNapiV2.kmU128Shl(lo0, hi0, lo1, hi1, s)")
private external fun wasmKmU128Shl(lo0: Int, hi0: Int, lo1: Int, hi1: Int, s: Int): Int
@JsFun("(lo0, hi0, lo1, hi1, s) => globalThis.kmCoreNapiV2.kmU128Shr(lo0, hi0, lo1, hi1, s)")
private external fun wasmKmU128Shr(lo0: Int, hi0: Int, lo1: Int, hi1: Int, s: Int): Int
@JsFun("(lo0, hi0, lo1, hi1, s) => globalThis.kmCoreNapiV2.kmI128Shr(lo0, hi0, lo1, hi1, s)")
private external fun wasmKmI128Shr(lo0: Int, hi0: Int, lo1: Int, hi1: Int, s: Int): Int
@JsFun("(x) => globalThis.kmCoreNapiV2.kmSinf32(x)")
private external fun wasmKmSinf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmSinf64(x)")
private external fun wasmKmSinf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmCosf32(x)")
private external fun wasmKmCosf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmCosf64(x)")
private external fun wasmKmCosf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmExpf32(x)")
private external fun wasmKmExpf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmExpf64(x)")
private external fun wasmKmExpf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmExp2f32(x)")
private external fun wasmKmExp2f32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmExp2f64(x)")
private external fun wasmKmExp2f64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLogf32(x)")
private external fun wasmKmLogf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLogf64(x)")
private external fun wasmKmLogf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLog2f32(x)")
private external fun wasmKmLog2f32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLog2f64(x)")
private external fun wasmKmLog2f64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLog10f32(x)")
private external fun wasmKmLog10f32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmLog10f64(x)")
private external fun wasmKmLog10f64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmTruncf32(x)")
private external fun wasmKmTruncf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmTruncf64(x)")
private external fun wasmKmTruncf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmRoundf32(x)")
private external fun wasmKmRoundf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmRoundf64(x)")
private external fun wasmKmRoundf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmRoundTiesEvenf32(x)")
private external fun wasmKmRoundTiesEvenf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmRoundTiesEvenf64(x)")
private external fun wasmKmRoundTiesEvenf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmCeilf32(x)")
private external fun wasmKmCeilf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmCeilf64(x)")
private external fun wasmKmCeilf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmFloorf32(x)")
private external fun wasmKmFloorf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmFloorf64(x)")
private external fun wasmKmFloorf64(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmFabsf32(x)")
private external fun wasmKmFabsf32(x: Double): Double
@JsFun("(x) => globalThis.kmCoreNapiV2.kmFabsf64(x)")
private external fun wasmKmFabsf64(x: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmPowf32(a, b)")
private external fun wasmKmPowf32(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmPowf64(a, b)")
private external fun wasmKmPowf64(a: Double, b: Double): Double
@JsFun("(x, e) => globalThis.kmCoreNapiV2.kmPowif32(x, e)")
private external fun wasmKmPowif32(x: Double, e: Int): Double
@JsFun("(x, e) => globalThis.kmCoreNapiV2.kmPowif64(x, e)")
private external fun wasmKmPowif64(x: Double, e: Int): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmCopysignf32(a, b)")
private external fun wasmKmCopysignf32(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmCopysignf64(a, b)")
private external fun wasmKmCopysignf64(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMinnumf32(a, b)")
private external fun wasmKmMinnumf32(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMinnumf64(a, b)")
private external fun wasmKmMinnumf64(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMaxnumf32(a, b)")
private external fun wasmKmMaxnumf32(a: Double, b: Double): Double
@JsFun("(a, b) => globalThis.kmCoreNapiV2.kmMaxnumf64(a, b)")
private external fun wasmKmMaxnumf64(a: Double, b: Double): Double
@JsFun("(v) => globalThis.kmCoreNapiV2.kmFloatToU64Unchecked(v)")
private external fun wasmKmFloatToU64Unchecked(v: Double): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmDoubleToU64Unchecked(v)")
private external fun wasmKmDoubleToU64Unchecked(v: Double): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmFloatToU128Unchecked(v)")
private external fun wasmKmFloatToU128Unchecked(v: Double): Int
@JsFun("(v) => globalThis.kmCoreNapiV2.kmDoubleToU128Unchecked(v)")
private external fun wasmKmDoubleToU128Unchecked(v: Double): Int

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

public actual fun kmCtpopU8(value: UByte): UInt =
    if (kmCoreNapiAvailable) wasmKmCtpopU8(value.toInt()).toUInt() else KmLinearMemoryArena.ctpopU8(value)
public actual fun kmCtpopU16(value: UShort): UInt =
    if (kmCoreNapiAvailable) wasmKmCtpopU16(value.toInt()).toUInt() else KmLinearMemoryArena.ctpopU16(value)
public actual fun kmCtpopU32(value: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmCtpopU32(value.toInt()).toUInt() else KmLinearMemoryArena.ctpopU32(value)
public actual fun kmCtpopU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCtpopU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)).toUInt() else KmLinearMemoryArena.ctpopU128(lo, hi)
public actual fun kmCtlzU8(value: UByte): UInt =
    if (kmCoreNapiAvailable) wasmKmCtlzU8(value.toInt()).toUInt() else KmLinearMemoryArena.ctlzU8(value)
public actual fun kmCtlzU16(value: UShort): UInt =
    if (kmCoreNapiAvailable) wasmKmCtlzU16(value.toInt()).toUInt() else KmLinearMemoryArena.ctlzU16(value)
public actual fun kmCtlzU32(value: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmCtlzU32(value.toInt()).toUInt() else KmLinearMemoryArena.ctlzU32(value)
public actual fun kmCtlzU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCtlzU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)).toUInt() else KmLinearMemoryArena.ctlzU128(lo, hi)
public actual fun kmCttzU8(value: UByte): UInt =
    if (kmCoreNapiAvailable) wasmKmCttzU8(value.toInt()).toUInt() else KmLinearMemoryArena.cttzU8(value)
public actual fun kmCttzU16(value: UShort): UInt =
    if (kmCoreNapiAvailable) wasmKmCttzU16(value.toInt()).toUInt() else KmLinearMemoryArena.cttzU16(value)
public actual fun kmCttzU32(value: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmCttzU32(value.toInt()).toUInt() else KmLinearMemoryArena.cttzU32(value)
public actual fun kmCttzU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAvailable) wasmKmCttzU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)).toUInt() else KmLinearMemoryArena.cttzU128(lo, hi)
public actual fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmBswapU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.bswapU128(lo, hi)
    }
public actual fun kmBitreverseU8(value: UByte): UByte =
    if (kmCoreNapiAvailable) wasmKmBitreverseU8(value.toInt()).toUByte() else KmLinearMemoryArena.bitreverseU8(value)
public actual fun kmBitreverseU16(value: UShort): UShort =
    if (kmCoreNapiAvailable) wasmKmBitreverseU16(value.toInt()).toUShort() else KmLinearMemoryArena.bitreverseU16(value)
public actual fun kmBitreverseU32(value: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmBitreverseU32(value.toInt()).toUInt() else KmLinearMemoryArena.bitreverseU32(value)
public actual fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmBitreverseU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.bitreverseU128(lo, hi)
    }
public actual fun kmRotateLeftU8(value: UByte, n: UInt): UByte =
    if (kmCoreNapiAvailable) wasmKmRotateLeftU8(value.toInt(), n.toInt()).toUByte() else KmLinearMemoryArena.rotateLeftU8(value, n)
public actual fun kmRotateLeftU16(value: UShort, n: UInt): UShort =
    if (kmCoreNapiAvailable) wasmKmRotateLeftU16(value.toInt(), n.toInt()).toUShort() else KmLinearMemoryArena.rotateLeftU16(value, n)
public actual fun kmRotateLeftU32(value: UInt, n: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmRotateLeftU32(value.toInt(), n.toInt()).toUInt() else KmLinearMemoryArena.rotateLeftU32(value, n)
public actual fun kmRotateLeftU64(value: ULong, n: UInt): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmRotateLeftU64(uLo32(value), uHi32(value), n.toInt())
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.rotateLeftU64(value, n)
    }
public actual fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmRotateLeftU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi), n.toInt())
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.rotateLeftU128(lo, hi, n)
    }
public actual fun kmRotateRightU8(value: UByte, n: UInt): UByte =
    if (kmCoreNapiAvailable) wasmKmRotateRightU8(value.toInt(), n.toInt()).toUByte() else KmLinearMemoryArena.rotateRightU8(value, n)
public actual fun kmRotateRightU16(value: UShort, n: UInt): UShort =
    if (kmCoreNapiAvailable) wasmKmRotateRightU16(value.toInt(), n.toInt()).toUShort() else KmLinearMemoryArena.rotateRightU16(value, n)
public actual fun kmRotateRightU32(value: UInt, n: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmRotateRightU32(value.toInt(), n.toInt()).toUInt() else KmLinearMemoryArena.rotateRightU32(value, n)
public actual fun kmRotateRightU64(value: ULong, n: UInt): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmRotateRightU64(uLo32(value), uHi32(value), n.toInt())
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.rotateRightU64(value, n)
    }
public actual fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmRotateRightU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi), n.toInt())
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.rotateRightU128(lo, hi, n)
    }
public actual fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowU8(a.toInt(), b.toInt())
        OverflowU8Outcome(v.toUByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU8(a, b)
    }
public actual fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowU16(a.toInt(), b.toInt())
        OverflowU16Outcome(v.toUShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU16(a, b)
    }
public actual fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowU32(a.toInt(), b.toInt())
        OverflowU32Outcome(v.toUInt(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU32(a, b)
    }
public actual fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmAddWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) != 0
        OverflowU64Outcome(packU64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowU64(a, b)
    }
public actual fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmAddWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowU128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowI8(a.toInt(), b.toInt())
        OverflowI8Outcome(v.toByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI8(a, b)
    }
public actual fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowI16(a.toInt(), b.toInt())
        OverflowI16Outcome(v.toShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI16(a, b)
    }
public actual fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmAddWithOverflowI32(a, b)
        OverflowI32Outcome(v, wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI32(a, b)
    }
public actual fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmAddWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) != 0
        OverflowI64Outcome(packI64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowI64(a, b)
    }
public actual fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmAddWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowI128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowU8(a.toInt(), b.toInt())
        OverflowU8Outcome(v.toUByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU8(a, b)
    }
public actual fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowU16(a.toInt(), b.toInt())
        OverflowU16Outcome(v.toUShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU16(a, b)
    }
public actual fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowU32(a.toInt(), b.toInt())
        OverflowU32Outcome(v.toUInt(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU32(a, b)
    }
public actual fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmSubWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) != 0
        OverflowU64Outcome(packU64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowU64(a, b)
    }
public actual fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmSubWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowU128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowI8(a.toInt(), b.toInt())
        OverflowI8Outcome(v.toByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI8(a, b)
    }
public actual fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowI16(a.toInt(), b.toInt())
        OverflowI16Outcome(v.toShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI16(a, b)
    }
public actual fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmSubWithOverflowI32(a, b)
        OverflowI32Outcome(v, wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI32(a, b)
    }
public actual fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmSubWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) != 0
        OverflowI64Outcome(packI64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowI64(a, b)
    }
public actual fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmSubWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowI128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowU8(a.toInt(), b.toInt())
        OverflowU8Outcome(v.toUByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU8(a, b)
    }
public actual fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowU16(a.toInt(), b.toInt())
        OverflowU16Outcome(v.toUShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU16(a, b)
    }
public actual fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowU32(a.toInt(), b.toInt())
        OverflowU32Outcome(v.toUInt(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU32(a, b)
    }
public actual fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmMulWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) != 0
        OverflowU64Outcome(packU64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowU64(a, b)
    }
public actual fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmMulWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowU128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowI8(a.toInt(), b.toInt())
        OverflowI8Outcome(v.toByte(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI8(a, b)
    }
public actual fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowI16(a.toInt(), b.toInt())
        OverflowI16Outcome(v.toShort(), wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI16(a, b)
    }
public actual fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAvailable) {
        val v = wasmKmMulWithOverflowI32(a, b)
        OverflowI32Outcome(v, wasmKmOut1() != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI32(a, b)
    }
public actual fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmMulWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) != 0
        OverflowI64Outcome(packI64(wasmKmOut0(), wasmKmOut1()), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowI64(a, b)
    }
public actual fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAvailable) {
        val ovf = wasmKmMulWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) != 0
        OverflowI128Outcome(packU64(wasmKmOut0(), wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingAddU8(a: UByte, b: UByte): UByte =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddU8(a.toInt(), b.toInt()).toUByte() else KmLinearMemoryArena.saturatingAddU8(a, b)
public actual fun kmSaturatingAddU16(a: UShort, b: UShort): UShort =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddU16(a.toInt(), b.toInt()).toUShort() else KmLinearMemoryArena.saturatingAddU16(a, b)
public actual fun kmSaturatingAddU32(a: UInt, b: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddU32(a.toInt(), b.toInt()).toUInt() else KmLinearMemoryArena.saturatingAddU32(a, b)
public actual fun kmSaturatingAddU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmSaturatingAddU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.saturatingAddU64(a, b)
    }
public actual fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmSaturatingAddU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.saturatingAddU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingAddI8(a: Byte, b: Byte): Byte =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddI8(a.toInt(), b.toInt()).toByte() else KmLinearMemoryArena.saturatingAddI8(a, b)
public actual fun kmSaturatingAddI16(a: Short, b: Short): Short =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddI16(a.toInt(), b.toInt()).toShort() else KmLinearMemoryArena.saturatingAddI16(a, b)
public actual fun kmSaturatingAddI32(a: Int, b: Int): Int =
    if (kmCoreNapiAvailable) wasmKmSaturatingAddI32(a, b) else KmLinearMemoryArena.saturatingAddI32(a, b)
public actual fun kmSaturatingAddI64(a: Long, b: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmSaturatingAddI64(lo32(a), hi32(a), lo32(b), hi32(b))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.saturatingAddI64(a, b)
    }
public actual fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmSaturatingAddI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        I128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.saturatingAddI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingSubU8(a: UByte, b: UByte): UByte =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubU8(a.toInt(), b.toInt()).toUByte() else KmLinearMemoryArena.saturatingSubU8(a, b)
public actual fun kmSaturatingSubU16(a: UShort, b: UShort): UShort =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubU16(a.toInt(), b.toInt()).toUShort() else KmLinearMemoryArena.saturatingSubU16(a, b)
public actual fun kmSaturatingSubU32(a: UInt, b: UInt): UInt =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubU32(a.toInt(), b.toInt()).toUInt() else KmLinearMemoryArena.saturatingSubU32(a, b)
public actual fun kmSaturatingSubU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmSaturatingSubU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b))
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.saturatingSubU64(a, b)
    }
public actual fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmSaturatingSubU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.saturatingSubU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingSubI8(a: Byte, b: Byte): Byte =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubI8(a.toInt(), b.toInt()).toByte() else KmLinearMemoryArena.saturatingSubI8(a, b)
public actual fun kmSaturatingSubI16(a: Short, b: Short): Short =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubI16(a.toInt(), b.toInt()).toShort() else KmLinearMemoryArena.saturatingSubI16(a, b)
public actual fun kmSaturatingSubI32(a: Int, b: Int): Int =
    if (kmCoreNapiAvailable) wasmKmSaturatingSubI32(a, b) else KmLinearMemoryArena.saturatingSubI32(a, b)
public actual fun kmSaturatingSubI64(a: Long, b: Long): Long =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmSaturatingSubI64(lo32(a), hi32(a), lo32(b), hi32(b))
        packI64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.saturatingSubI64(a, b)
    }
public actual fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmSaturatingSubI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        I128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.saturatingSubI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmU128Add(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.u128Add(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmU128Sub(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.u128Sub(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmU128Mul(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.u128Mul(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome =
    if (kmCoreNapiAvailable) {
        val qLo0 = wasmKmU128DivRem(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        U128DivRemOutcome(packU64(qLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), packU64(wasmKmOut4(), wasmKmOut5()), packU64(wasmKmOut6(), wasmKmOut7()))
    } else {
        KmLinearMemoryArena.u128DivRem(aLo, aHi, bLo, bHi)
    }
public actual fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome =
    if (kmCoreNapiAvailable) {
        val qLo0 = wasmKmI128DivRem(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi))
        I128DivRemOutcome(packU64(qLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()), packU64(wasmKmOut4(), wasmKmOut5()), packU64(wasmKmOut6(), wasmKmOut7()))
    } else {
        KmLinearMemoryArena.i128DivRem(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmU128Shl(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt())
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.u128Shl(aLo, aHi, shift)
    }
public actual fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmU128Shr(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt())
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.u128Shr(aLo, aHi, shift)
    }
public actual fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmI128Shr(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt())
        I128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.i128Shr(aLo, aHi, shift)
    }
public actual fun kmSinf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmSinf32(x.toDouble()).toFloat() else KmLinearMemoryArena.sinf32(x)
public actual fun kmSinf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmSinf64(x) else KmLinearMemoryArena.sinf64(x)
public actual fun kmCosf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmCosf32(x.toDouble()).toFloat() else KmLinearMemoryArena.cosf32(x)
public actual fun kmCosf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmCosf64(x) else KmLinearMemoryArena.cosf64(x)
public actual fun kmExpf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmExpf32(x.toDouble()).toFloat() else KmLinearMemoryArena.expf32(x)
public actual fun kmExpf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmExpf64(x) else KmLinearMemoryArena.expf64(x)
public actual fun kmExp2f32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmExp2f32(x.toDouble()).toFloat() else KmLinearMemoryArena.exp2f32(x)
public actual fun kmExp2f64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmExp2f64(x) else KmLinearMemoryArena.exp2f64(x)
public actual fun kmLogf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmLogf32(x.toDouble()).toFloat() else KmLinearMemoryArena.logf32(x)
public actual fun kmLogf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmLogf64(x) else KmLinearMemoryArena.logf64(x)
public actual fun kmLog2f32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmLog2f32(x.toDouble()).toFloat() else KmLinearMemoryArena.log2f32(x)
public actual fun kmLog2f64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmLog2f64(x) else KmLinearMemoryArena.log2f64(x)
public actual fun kmLog10f32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmLog10f32(x.toDouble()).toFloat() else KmLinearMemoryArena.log10f32(x)
public actual fun kmLog10f64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmLog10f64(x) else KmLinearMemoryArena.log10f64(x)
public actual fun kmTruncf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmTruncf32(x.toDouble()).toFloat() else KmLinearMemoryArena.truncf32(x)
public actual fun kmTruncf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmTruncf64(x) else KmLinearMemoryArena.truncf64(x)
public actual fun kmRoundf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmRoundf32(x.toDouble()).toFloat() else KmLinearMemoryArena.roundf32(x)
public actual fun kmRoundf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmRoundf64(x) else KmLinearMemoryArena.roundf64(x)
public actual fun kmRoundTiesEvenf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmRoundTiesEvenf32(x.toDouble()).toFloat() else KmLinearMemoryArena.roundTiesEvenf32(x)
public actual fun kmRoundTiesEvenf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmRoundTiesEvenf64(x) else KmLinearMemoryArena.roundTiesEvenf64(x)
public actual fun kmCeilf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmCeilf32(x.toDouble()).toFloat() else KmLinearMemoryArena.ceilf32(x)
public actual fun kmCeilf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmCeilf64(x) else KmLinearMemoryArena.ceilf64(x)
public actual fun kmFloorf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmFloorf32(x.toDouble()).toFloat() else KmLinearMemoryArena.floorf32(x)
public actual fun kmFloorf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmFloorf64(x) else KmLinearMemoryArena.floorf64(x)
public actual fun kmFabsf32(x: Float): Float =
    if (kmCoreNapiAvailable) wasmKmFabsf32(x.toDouble()).toFloat() else KmLinearMemoryArena.fabsf32(x)
public actual fun kmFabsf64(x: Double): Double =
    if (kmCoreNapiAvailable) wasmKmFabsf64(x) else KmLinearMemoryArena.fabsf64(x)
public actual fun kmPowf32(a: Float, b: Float): Float =
    if (kmCoreNapiAvailable) wasmKmPowf32(a.toDouble(), b.toDouble()).toFloat() else KmLinearMemoryArena.powf32(a, b)
public actual fun kmPowf64(a: Double, b: Double): Double =
    if (kmCoreNapiAvailable) wasmKmPowf64(a, b) else KmLinearMemoryArena.powf64(a, b)
public actual fun kmPowif32(x: Float, exp: Int): Float =
    if (kmCoreNapiAvailable) wasmKmPowif32(x.toDouble(), exp).toFloat() else KmLinearMemoryArena.powif32(x, exp)
public actual fun kmPowif64(x: Double, exp: Int): Double =
    if (kmCoreNapiAvailable) wasmKmPowif64(x, exp) else KmLinearMemoryArena.powif64(x, exp)
public actual fun kmCopysignf32(a: Float, b: Float): Float =
    if (kmCoreNapiAvailable) wasmKmCopysignf32(a.toDouble(), b.toDouble()).toFloat() else KmLinearMemoryArena.copysignf32(a, b)
public actual fun kmCopysignf64(a: Double, b: Double): Double =
    if (kmCoreNapiAvailable) wasmKmCopysignf64(a, b) else KmLinearMemoryArena.copysignf64(a, b)
public actual fun kmMinnumf32(a: Float, b: Float): Float =
    if (kmCoreNapiAvailable) wasmKmMinnumf32(a.toDouble(), b.toDouble()).toFloat() else KmLinearMemoryArena.minnumf32(a, b)
public actual fun kmMinnumf64(a: Double, b: Double): Double =
    if (kmCoreNapiAvailable) wasmKmMinnumf64(a, b) else KmLinearMemoryArena.minnumf64(a, b)
public actual fun kmMaxnumf32(a: Float, b: Float): Float =
    if (kmCoreNapiAvailable) wasmKmMaxnumf32(a.toDouble(), b.toDouble()).toFloat() else KmLinearMemoryArena.maxnumf32(a, b)
public actual fun kmMaxnumf64(a: Double, b: Double): Double =
    if (kmCoreNapiAvailable) wasmKmMaxnumf64(a, b) else KmLinearMemoryArena.maxnumf64(a, b)
public actual fun kmFloatToU64Unchecked(v: Float): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmFloatToU64Unchecked(v.toDouble())
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.floatToU64Unchecked(v)
    }
public actual fun kmDoubleToU64Unchecked(v: Double): ULong =
    if (kmCoreNapiAvailable) {
        val lo = wasmKmDoubleToU64Unchecked(v)
        packU64(lo, wasmKmOut1())
    } else {
        KmLinearMemoryArena.doubleToU64Unchecked(v)
    }
public actual fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmFloatToU128Unchecked(v.toDouble())
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.floatToU128Unchecked(v)
    }
public actual fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome =
    if (kmCoreNapiAvailable) {
        val rLo0 = wasmKmDoubleToU128Unchecked(v)
        U128ValueOutcome(packU64(rLo0, wasmKmOut1()), packU64(wasmKmOut2(), wasmKmOut3()))
    } else {
        KmLinearMemoryArena.doubleToU128Unchecked(v)
    }

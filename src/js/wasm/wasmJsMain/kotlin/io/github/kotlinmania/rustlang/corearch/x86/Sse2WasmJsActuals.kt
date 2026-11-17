@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initX86Sse2NapiAddon(): Boolean

private val x86Sse2NapiAvailable: Boolean = initX86Sse2NapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.corearchX86Sse2Pause()")
private external fun wasmCorearchX86Sse2Pause()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX86Sse2Clflush(p0)")
private external fun wasmCorearchX86Sse2Clflush(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.corearchX86Sse2Lfence()")
private external fun wasmCorearchX86Sse2Lfence()

@JsFun("() => globalThis.__km_napi_kmcore.corearchX86Sse2Mfence()")
private external fun wasmCorearchX86Sse2Mfence()

public actual fun pause() {
    if (x86Sse2NapiAvailable) {
        wasmCorearchX86Sse2Pause()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun clflush(p: ConstPtr) {
    if (x86Sse2NapiAvailable) {
        wasmCorearchX86Sse2Clflush(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lfence() {
    if (x86Sse2NapiAvailable) {
        wasmCorearchX86Sse2Lfence()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mfence() {
    if (x86Sse2NapiAvailable) {
        wasmCorearchX86Sse2Mfence()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pavgb(a: U8x16, b: U8x16): U8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pavgw(a: U16x8, b: U16x8): U16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmaddwd(a: I16x8, b: I16x8): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhw(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhuw(a: U16x8, b: U16x8): U16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psadbw(a: U8x16, b: U8x16): U64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pslld(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psraw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrad(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrld(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtps2dq(a: M128): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maskmovdqu(a: I8x16, mask: I8x16, memAddr: MutPtr) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun packsswb(a: I16x8, b: I16x8): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packssdw(a: I32x4, b: I32x4): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packuswb(a: I16x8, b: I16x8): U8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxsd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxpd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minsd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minpd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comieqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comiltsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comilesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigtsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comineqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomieqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomiltsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomilesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigtsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomineqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtpd2dq(a: M128d): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtsd2si(a: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtsd2ss(a: M128, b: M128d): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttpd2dq(a: M128d): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttsd2si(a: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttps2dq(a: M128): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

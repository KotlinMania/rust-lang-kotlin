@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initHelenosNapiAddon(): Boolean

private val helenosNapiAvailable: Boolean = initHelenosNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.libcHelenosGetENVIRON()")
private external fun wasmLibcHelenosGetENVIRON(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosStrError(p0)")
private external fun wasmLibcHelenosStrError(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosStrErrorName(p0)")
private external fun wasmLibcHelenosStrErrorName(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosPthreadKeyCreate(p0, p1)")
private external fun wasmLibcHelenosPthreadKeyCreate(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosPthreadGetspecific(p0)")
private external fun wasmLibcHelenosPthreadGetspecific(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosPthreadSetspecific(p0, p1)")
private external fun wasmLibcHelenosPthreadSetspecific(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosPthreadKeyDelete(p0)")
private external fun wasmLibcHelenosPthreadKeyDelete(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosClockGetres(p0, p1)")
private external fun wasmLibcHelenosClockGetres(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosClockGettime(p0, p1)")
private external fun wasmLibcHelenosClockGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosClockSettime(p0, p1)")
private external fun wasmLibcHelenosClockSettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcHelenosClockNanosleep(p0, p1, p2, p3)")
private external fun wasmLibcHelenosClockNanosleep(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcHelenosGetpid()")
private external fun wasmLibcHelenosGetpid(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcHelenosGetcwd(p0, p1)")
private external fun wasmLibcHelenosGetcwd(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosChdir(p0)")
private external fun wasmLibcHelenosChdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcHelenosIsatty(p0)")
private external fun wasmLibcHelenosIsatty(p0: Int): Int

public actual val ENVIRON: ConstPtr
    get() = if (helenosNapiAvailable) ConstPtr(wasmLibcHelenosGetENVIRON().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strError(err: ErrnoT): ConstPtr =
    if (helenosNapiAvailable) ConstPtr(wasmLibcHelenosStrError(err).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strErrorName(err: ErrnoT): ConstPtr =
    if (helenosNapiAvailable) ConstPtr(wasmLibcHelenosStrErrorName(err).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (helenosNapiAvailable) MutPtr(wasmLibcHelenosPthreadGetspecific(key).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosPthreadSetspecific(key, value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosPthreadKeyDelete(key) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clockId: ClockidT, res: MutPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosClockGetres(clockId, res.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosClockGettime(clockId, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosClockSettime(clockId, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosClockNanosleep(clockId, flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): PidT =
    if (helenosNapiAvailable) wasmLibcHelenosGetpid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (helenosNapiAvailable) MutPtr(wasmLibcHelenosGetcwd(buf.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(buf: ConstPtr): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosChdir(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (helenosNapiAvailable) wasmLibcHelenosIsatty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

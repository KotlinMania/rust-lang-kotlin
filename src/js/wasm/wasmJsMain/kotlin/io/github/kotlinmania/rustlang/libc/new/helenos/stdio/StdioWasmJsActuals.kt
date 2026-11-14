@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdio

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosStdioNapiAddon(): Boolean

private val newHelenosStdioNapiAvailable: Boolean = initNewHelenosStdioNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosStdioGetSTDIN()")
private external fun wasmLibcNewHelenosStdioGetSTDIN(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosStdioGetSTDOUT()")
private external fun wasmLibcNewHelenosStdioGetSTDOUT(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosStdioGetSTDERR()")
private external fun wasmLibcNewHelenosStdioGetSTDERR(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFputc(p0, p1)")
private external fun wasmLibcNewHelenosStdioFputc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFputs(p0, p1)")
private external fun wasmLibcNewHelenosStdioFputs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFopen(p0, p1)")
private external fun wasmLibcNewHelenosStdioFopen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFclose(p0)")
private external fun wasmLibcNewHelenosStdioFclose(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFflush(p0)")
private external fun wasmLibcNewHelenosStdioFflush(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFread(p0, p1, p2, p3)")
private external fun wasmLibcNewHelenosStdioFread(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFwrite(p0, p1, p2, p3)")
private external fun wasmLibcNewHelenosStdioFwrite(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFseek(p0, p1, p2)")
private external fun wasmLibcNewHelenosStdioFseek(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFtell(p0)")
private external fun wasmLibcNewHelenosStdioFtell(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdioFileno(p0)")
private external fun wasmLibcNewHelenosStdioFileno(p0: String): Int

public actual val STDIN: MutPtr
    get() = if (newHelenosStdioNapiAvailable) MutPtr(wasmLibcNewHelenosStdioGetSTDIN().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val STDOUT: MutPtr
    get() = if (newHelenosStdioNapiAvailable) MutPtr(wasmLibcNewHelenosStdioGetSTDOUT().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val STDERR: MutPtr
    get() = if (newHelenosStdioNapiAvailable) MutPtr(wasmLibcNewHelenosStdioGetSTDERR().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFputc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFputs(s.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (newHelenosStdioNapiAvailable) MutPtr(wasmLibcNewHelenosStdioFopen(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFclose(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFflush(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(buf: MutPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFread(buf.rawAddress.toString(), size.toString(), nmemb.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(buf: ConstPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFwrite(buf.rawAddress.toString(), size.toString(), nmemb.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFseek(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFtell(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAvailable) wasmLibcNewHelenosStdioFileno(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

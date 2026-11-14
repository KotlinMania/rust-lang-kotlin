@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdio

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT

private val newHelenosStdioNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual val STDIN: MutPtr
    get() = if (newHelenosStdioNapiAddon != null) MutPtr((newHelenosStdioNapiAddon.libcNewHelenosStdioGetSTDIN() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val STDOUT: MutPtr
    get() = if (newHelenosStdioNapiAddon != null) MutPtr((newHelenosStdioNapiAddon.libcNewHelenosStdioGetSTDOUT() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val STDERR: MutPtr
    get() = if (newHelenosStdioNapiAddon != null) MutPtr((newHelenosStdioNapiAddon.libcNewHelenosStdioGetSTDERR() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFputc(c, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFputs(s.rawAddress.toString(), stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (newHelenosStdioNapiAddon != null) MutPtr((newHelenosStdioNapiAddon.libcNewHelenosStdioFopen(filename.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFclose(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFflush(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(buf: MutPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFread(buf.rawAddress.toString(), size.toString(), nmemb.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(buf: ConstPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFwrite(buf.rawAddress.toString(), size.toString(), nmemb.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFseek(stream.rawAddress.toString(), offset.toString(), whence) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFtell(stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (newHelenosStdioNapiAddon != null) (newHelenosStdioNapiAddon.libcNewHelenosStdioFileno(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

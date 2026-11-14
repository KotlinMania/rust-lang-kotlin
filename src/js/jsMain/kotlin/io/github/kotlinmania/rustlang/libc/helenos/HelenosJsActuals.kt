@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private val helenosNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual val ENVIRON: ConstPtr
    get() = if (helenosNapiAddon != null) ConstPtr((helenosNapiAddon.libcHelenosGetENVIRON() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strError(err: ErrnoT): ConstPtr =
    if (helenosNapiAddon != null) ConstPtr((helenosNapiAddon.libcHelenosStrError(err) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strErrorName(err: ErrnoT): ConstPtr =
    if (helenosNapiAddon != null) ConstPtr((helenosNapiAddon.libcHelenosStrErrorName(err) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (helenosNapiAddon != null) MutPtr((helenosNapiAddon.libcHelenosPthreadGetspecific(key) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosPthreadSetspecific(key, value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosPthreadKeyDelete(key) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clockId: ClockidT, res: MutPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosClockGetres(clockId, res.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosClockGettime(clockId, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosClockSettime(clockId, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosClockNanosleep(clockId, flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): PidT =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosGetpid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (helenosNapiAddon != null) MutPtr((helenosNapiAddon.libcHelenosGetcwd(buf.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(buf: ConstPtr): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosChdir(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (helenosNapiAddon != null) (helenosNapiAddon.libcHelenosIsatty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

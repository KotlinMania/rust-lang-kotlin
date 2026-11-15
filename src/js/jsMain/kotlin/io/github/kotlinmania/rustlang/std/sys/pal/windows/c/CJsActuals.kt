@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.os.raw.CInt
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Ntstatus

private val stdSysPalWindowsCNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    if (stdSysPalWindowsCNapiAddon != null) (stdSysPalWindowsCNapiAddon.rustlangStdSysPalWindowsCProcessPrng(pbdata.rawAddress.toString(), cbdata.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    if (stdSysPalWindowsCNapiAddon != null) (stdSysPalWindowsCNapiAddon.rustlangStdSysPalWindowsCNtCreateNamedPipeFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), namedpipetype.toInt(), readmode.toInt(), completionmode.toInt(), maximuminstances.toInt(), inboundquota.toInt(), outboundquota.toInt(), defaulttimeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (stdSysPalWindowsCNapiAddon != null) (stdSysPalWindowsCNapiAddon.rustlangStdSysPalWindowsCAtexit(0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

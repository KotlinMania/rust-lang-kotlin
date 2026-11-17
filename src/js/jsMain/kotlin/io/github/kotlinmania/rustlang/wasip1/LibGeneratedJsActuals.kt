@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.wasip1

private val libGeneratedNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedArgsGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedArgsSizesGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedEnvironGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedEnvironSizesGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedClockResGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedClockTimeGet(arg0, arg1.toString(), arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdAdvise(arg0, arg1.toString(), arg2.toString(), arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdAllocate(arg0, arg1.toString(), arg2.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdClose(arg0: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdClose(arg0) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdDatasync(arg0: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdDatasync(arg0) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFdstatGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFdstatSetFlags(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFdstatSetRights(arg0, arg1.toString(), arg2.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFilestatGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFilestatSetSize(arg0, arg1.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdFilestatSetTimes(arg0, arg1.toString(), arg2.toString(), arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdPread(arg0, arg1, arg2, arg3.toString(), arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdPrestatGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdPrestatDirName(arg0, arg1, arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdPwrite(arg0, arg1, arg2, arg3.toString(), arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdRead(arg0, arg1, arg2, arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdReaddir(arg0, arg1, arg2, arg3.toString(), arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdRenumber(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdSeek(arg0, arg1.toString(), arg2, arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSync(arg0: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdSync(arg0) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdTell(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedFdWrite(arg0, arg1, arg2, arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathCreateDirectory(arg0, arg1, arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4.toString(), arg5.toString(), arg6) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5.toString(), arg6.toString(), arg7, arg8) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathRemoveDirectory(arg0, arg1, arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPathUnlinkFile(arg0, arg1, arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedPollOneoff(arg0, arg1, arg2, arg3) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procExit(arg0: Int): Nothing {
    if (libGeneratedNapiAddon != null) {
        libGeneratedNapiAddon.wasip1LibGeneratedProcExit(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedProcRaise(arg0) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasiSnapshotPreview1SchedYield(): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedWasiSnapshotPreview1SchedYield() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedRandomGet(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedSockAccept(arg0, arg1, arg2) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAddon != null) (libGeneratedNapiAddon.wasip1LibGeneratedSockShutdown(arg0, arg1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

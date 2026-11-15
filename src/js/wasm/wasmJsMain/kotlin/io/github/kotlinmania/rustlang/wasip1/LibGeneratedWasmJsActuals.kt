@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.wasip1

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibGeneratedNapiAddon(): Boolean

private val libGeneratedNapiAvailable: Boolean = initLibGeneratedNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedArgsGet(p0, p1)")
private external fun wasmRustlangLibGeneratedArgsGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedArgsSizesGet(p0, p1)")
private external fun wasmRustlangLibGeneratedArgsSizesGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedEnvironGet(p0, p1)")
private external fun wasmRustlangLibGeneratedEnvironGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedEnvironSizesGet(p0, p1)")
private external fun wasmRustlangLibGeneratedEnvironSizesGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedClockResGet(p0, p1)")
private external fun wasmRustlangLibGeneratedClockResGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedClockTimeGet(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedClockTimeGet(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdAdvise(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedFdAdvise(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdAllocate(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedFdAllocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdClose(p0)")
private external fun wasmRustlangLibGeneratedFdClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdDatasync(p0)")
private external fun wasmRustlangLibGeneratedFdDatasync(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFdstatGet(p0, p1)")
private external fun wasmRustlangLibGeneratedFdFdstatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFdstatSetFlags(p0, p1)")
private external fun wasmRustlangLibGeneratedFdFdstatSetFlags(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFdstatSetRights(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedFdFdstatSetRights(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFilestatGet(p0, p1)")
private external fun wasmRustlangLibGeneratedFdFilestatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFilestatSetSize(p0, p1)")
private external fun wasmRustlangLibGeneratedFdFilestatSetSize(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdFilestatSetTimes(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedFdFilestatSetTimes(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdPread(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedFdPread(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdPrestatGet(p0, p1)")
private external fun wasmRustlangLibGeneratedFdPrestatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdPrestatDirName(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedFdPrestatDirName(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdPwrite(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedFdPwrite(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdRead(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedFdRead(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdReaddir(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedFdReaddir(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdRenumber(p0, p1)")
private external fun wasmRustlangLibGeneratedFdRenumber(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdSeek(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedFdSeek(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdSync(p0)")
private external fun wasmRustlangLibGeneratedFdSync(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdTell(p0, p1)")
private external fun wasmRustlangLibGeneratedFdTell(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedFdWrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedFdWrite(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathCreateDirectory(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedPathCreateDirectory(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathFilestatGet(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedPathFilestatGet(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathFilestatSetTimes(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibGeneratedPathFilestatSetTimes(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathLink(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibGeneratedPathLink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathOpen(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangLibGeneratedPathOpen(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: String, p6: String, p7: Int, p8: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathReadlink(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibGeneratedPathReadlink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathRemoveDirectory(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedPathRemoveDirectory(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathRename(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibGeneratedPathRename(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathSymlink(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedPathSymlink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPathUnlinkFile(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedPathUnlinkFile(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibGeneratedPollOneoff(p0, p1, p2, p3)")
private external fun wasmRustlangLibGeneratedPollOneoff(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibGeneratedProcExit(p0)")
private external fun wasmRustlangLibGeneratedProcExit(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibGeneratedProcRaise(p0)")
private external fun wasmRustlangLibGeneratedProcRaise(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibGeneratedSchedYield()")
private external fun wasmRustlangLibGeneratedSchedYield(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedRandomGet(p0, p1)")
private external fun wasmRustlangLibGeneratedRandomGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibGeneratedSockAccept(p0, p1, p2)")
private external fun wasmRustlangLibGeneratedSockAccept(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibGeneratedSockRecv(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibGeneratedSockRecv(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibGeneratedSockSend(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibGeneratedSockSend(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibGeneratedSockShutdown(p0, p1)")
private external fun wasmRustlangLibGeneratedSockShutdown(p0: Int, p1: Int): Int

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedArgsGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedArgsSizesGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedEnvironGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedEnvironSizesGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedClockResGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedClockTimeGet(arg0, arg1.toString(), arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdAdvise(arg0, arg1.toString(), arg2.toString(), arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdAllocate(arg0, arg1.toString(), arg2.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdClose(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdClose(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdDatasync(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdDatasync(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFdstatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFdstatSetFlags(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFdstatSetRights(arg0, arg1.toString(), arg2.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFilestatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFilestatSetSize(arg0, arg1.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdFilestatSetTimes(arg0, arg1.toString(), arg2.toString(), arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdPread(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdPrestatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdPrestatDirName(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdPwrite(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdRead(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdReaddir(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdRenumber(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdSeek(arg0, arg1.toString(), arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSync(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdSync(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdTell(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedFdWrite(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathCreateDirectory(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4.toString(), arg5.toString(), arg6) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5.toString(), arg6.toString(), arg7, arg8) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathRemoveDirectory(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPathUnlinkFile(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedPollOneoff(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procExit(arg0: Int): Nothing {
    if (libGeneratedNapiAvailable) {
        wasmRustlangLibGeneratedProcExit(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedProcRaise(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedYield(): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedSchedYield() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedRandomGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedSockAccept(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmRustlangLibGeneratedSockShutdown(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

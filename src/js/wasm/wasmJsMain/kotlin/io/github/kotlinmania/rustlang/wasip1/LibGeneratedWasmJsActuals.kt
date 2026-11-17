@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.wasip1

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibGeneratedNapiAddon(): Boolean

private val libGeneratedNapiAvailable: Boolean = initLibGeneratedNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedArgsGet(p0, p1)")
private external fun wasmWasip1LibGeneratedArgsGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedArgsSizesGet(p0, p1)")
private external fun wasmWasip1LibGeneratedArgsSizesGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedEnvironGet(p0, p1)")
private external fun wasmWasip1LibGeneratedEnvironGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedEnvironSizesGet(p0, p1)")
private external fun wasmWasip1LibGeneratedEnvironSizesGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedClockResGet(p0, p1)")
private external fun wasmWasip1LibGeneratedClockResGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedClockTimeGet(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedClockTimeGet(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdAdvise(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedFdAdvise(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdAllocate(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedFdAllocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdClose(p0)")
private external fun wasmWasip1LibGeneratedFdClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdDatasync(p0)")
private external fun wasmWasip1LibGeneratedFdDatasync(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFdstatGet(p0, p1)")
private external fun wasmWasip1LibGeneratedFdFdstatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFdstatSetFlags(p0, p1)")
private external fun wasmWasip1LibGeneratedFdFdstatSetFlags(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFdstatSetRights(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedFdFdstatSetRights(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFilestatGet(p0, p1)")
private external fun wasmWasip1LibGeneratedFdFilestatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFilestatSetSize(p0, p1)")
private external fun wasmWasip1LibGeneratedFdFilestatSetSize(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdFilestatSetTimes(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedFdFilestatSetTimes(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdPread(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedFdPread(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdPrestatGet(p0, p1)")
private external fun wasmWasip1LibGeneratedFdPrestatGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdPrestatDirName(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedFdPrestatDirName(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdPwrite(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedFdPwrite(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdRead(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedFdRead(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdReaddir(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedFdReaddir(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdRenumber(p0, p1)")
private external fun wasmWasip1LibGeneratedFdRenumber(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdSeek(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedFdSeek(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdSync(p0)")
private external fun wasmWasip1LibGeneratedFdSync(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdTell(p0, p1)")
private external fun wasmWasip1LibGeneratedFdTell(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedFdWrite(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedFdWrite(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathCreateDirectory(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedPathCreateDirectory(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathFilestatGet(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedPathFilestatGet(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathFilestatSetTimes(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmWasip1LibGeneratedPathFilestatSetTimes(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathLink(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmWasip1LibGeneratedPathLink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathOpen(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmWasip1LibGeneratedPathOpen(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: String, p6: String, p7: Int, p8: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathReadlink(p0, p1, p2, p3, p4, p5)")
private external fun wasmWasip1LibGeneratedPathReadlink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathRemoveDirectory(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedPathRemoveDirectory(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathRename(p0, p1, p2, p3, p4, p5)")
private external fun wasmWasip1LibGeneratedPathRename(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathSymlink(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedPathSymlink(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPathUnlinkFile(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedPathUnlinkFile(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.wasip1LibGeneratedPollOneoff(p0, p1, p2, p3)")
private external fun wasmWasip1LibGeneratedPollOneoff(p0: Int, p1: Int, p2: Int, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.wasip1LibGeneratedProcExit(p0)")
private external fun wasmWasip1LibGeneratedProcExit(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.wasip1LibGeneratedProcRaise(p0)")
private external fun wasmWasip1LibGeneratedProcRaise(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.wasip1LibGeneratedWasiSnapshotPreview1SchedYield()")
private external fun wasmWasip1LibGeneratedWasiSnapshotPreview1SchedYield(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedRandomGet(p0, p1)")
private external fun wasmWasip1LibGeneratedRandomGet(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.wasip1LibGeneratedSockAccept(p0, p1, p2)")
private external fun wasmWasip1LibGeneratedSockAccept(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.wasip1LibGeneratedSockRecv(p0, p1, p2, p3, p4, p5)")
private external fun wasmWasip1LibGeneratedSockRecv(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.wasip1LibGeneratedSockSend(p0, p1, p2, p3, p4)")
private external fun wasmWasip1LibGeneratedSockSend(p0: Int, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.wasip1LibGeneratedSockShutdown(p0, p1)")
private external fun wasmWasip1LibGeneratedSockShutdown(p0: Int, p1: Int): Int

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedArgsGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedArgsSizesGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedEnvironGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedEnvironSizesGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedClockResGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedClockTimeGet(arg0, arg1.toString(), arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdAdvise(arg0, arg1.toString(), arg2.toString(), arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdAllocate(arg0, arg1.toString(), arg2.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdClose(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdClose(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdDatasync(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdDatasync(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFdstatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFdstatSetFlags(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFdstatSetRights(arg0, arg1.toString(), arg2.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFilestatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFilestatSetSize(arg0, arg1.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdFilestatSetTimes(arg0, arg1.toString(), arg2.toString(), arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdPread(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdPrestatGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdPrestatDirName(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdPwrite(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdRead(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdReaddir(arg0, arg1, arg2, arg3.toString(), arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdRenumber(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdSeek(arg0, arg1.toString(), arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdSync(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdSync(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdTell(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedFdWrite(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathCreateDirectory(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4.toString(), arg5.toString(), arg6) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5.toString(), arg6.toString(), arg7, arg8) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathRemoveDirectory(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPathUnlinkFile(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedPollOneoff(arg0, arg1, arg2, arg3) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procExit(arg0: Int): Nothing {
    if (libGeneratedNapiAvailable) {
        wasmWasip1LibGeneratedProcExit(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedProcRaise(arg0) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasiSnapshotPreview1SchedYield(): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedWasiSnapshotPreview1SchedYield() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedRandomGet(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedSockAccept(arg0, arg1, arg2) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    if (libGeneratedNapiAvailable) wasmWasip1LibGeneratedSockShutdown(arg0, arg1) else throw UnsupportedOperationException("Native FFI addon is not available")

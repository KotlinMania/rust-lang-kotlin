@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeLinuxNapiAddon(): Boolean

private val libcUnixLinuxlikeLinuxNapiAvailable: Boolean = initLibcUnixLinuxlikeLinuxNapiAddon()

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetspnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetspnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqClose(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqUnlink(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqUnlink(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqReceive(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqReceive(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqTimedreceive(p0: Int, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqSend(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqSend(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqTimedsend(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqTimedsend(p0: Int, p1: String, p2: String, p3: Int, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqGetattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqGetattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMqSetattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMqSetattr(p0: Int, p1: String, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMrand48()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMrand48(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSeed48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSeed48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLcong48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLcong48(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLutimes(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLutimes(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxShmOpen(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxShmOpen(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxShmUnlink(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxShmUnlink(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFtok(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFtok(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSemget(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSemget(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSemop(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSemop(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSemctl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSemctl(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMsgctl(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMsgctl(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMsgget(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMsgget(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMsgrcv(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMsgrcv(p0: Int, p1: String, p2: String, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMsgsnd(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMsgsnd(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFallocate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFallocate(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixFallocate(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixFallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxReadahead(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxReadahead(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLgetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLgetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFgetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFgetxattr(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLsetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFsetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxListxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxListxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLlistxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLlistxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFlistxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFlistxattr(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxRemovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxRemovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxLremovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxLremovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFremovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFremovexattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSignalfd(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSignalfd(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxTimerfdCreate(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxTimerfdGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxTimerfdSettime(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxQuotactl(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxQuotactl(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEpollPwait(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEpollPwait(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxDup3(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxDup3(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSigtimedwait(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSigtimedwait(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSigwaitinfo(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxAccept4(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxAccept4(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxReboot(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxReboot(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSetfsgid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSetfsgid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSetfsuid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSetfsuid(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMkfifoat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMkfifoat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSyncFileRange(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSyncFileRange(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixMadvise(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMremap(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMremap(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String, p6: String, p7: String, p8: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxRemapFilePages(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxRemapFilePages(p0: String, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMkstemps(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMkstemps(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxVhangup()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxVhangup(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSync()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSync()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSyncfs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSyncfs(p0: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSyscall(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSyscall(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEpollCreate(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEpollCreate(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEpollCreate1(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEpollCreate1(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEpollWait(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEpollWait(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEpollCtl(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEpollCtl(p0: Int, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxUnshare(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxUnshare(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxUmount(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxUmount(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxTee(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxTee(p0: Int, p1: Int, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSplice(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSplice(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEventfd(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEventfd(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEventfdRead(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEventfdRead(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEventfdWrite(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEventfdWrite(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedSetparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedSetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSetns(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSetns(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSwapoff(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSwapoff(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxVmsplice(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxVmsplice(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPersonality(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPersonality(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedGetparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedGetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxClone(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxClone(p0: String, p1: String, p2: Int, p3: String, p4: Int, p5: String, p6: String, p7: String, p8: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxClockNanosleep(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxClockNanosleep(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxUmount2(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxUmount2(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSwapon(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSwapon(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSendfile(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSendfile(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSigaltstack(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSigaltstack(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetdtablesize()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetdtablesize(): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetgrouplist(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetgrouplist(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawn(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawn(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnp(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFreadUnlocked(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxInotifyInit()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxInotifyInit(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxInotifyInit1(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxInotifyInit1(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFanotifyInit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFanotifyInit(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGethostid()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGethostid(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxKlogctl(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxKlogctl(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxNameToHandleAt(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetnetbyname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetnetbyname(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(p0: Int, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGetnetent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGetnetent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSetnetent(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSetnetent(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxEndnetent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxEndnetent()

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFallocate64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFallocate64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFgetpos64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFgetpos64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxFopen64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxFopen64(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxPosixFallocate64(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxSendfile64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxSendfile64(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxTmpfile64()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxTmpfile64(): String

public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGetspnamR(name.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqClose(mqd: MqdT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqClose(mqd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqUnlink(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqReceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqTimedreceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString(), absTimeout.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqSend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqTimedsend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt(), absTimeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqGetattr(mqd, attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMqSetattr(mqd, newattr.rawAddress.toString(), oldattr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mrand48(): CLong =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMrand48().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seed48(xseed: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxSeed48(xseed.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lcong48(p: MutPtr) {
    if (libcUnixLinuxlikeLinuxNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxLcong48(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxLutimes(file.rawAddress.toString(), times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxShmOpen(name.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmUnlink(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxShmUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFtok(pathname.rawAddress.toString(), projId) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSemget(key, nsems, semflag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSemop(semid, sops.rawAddress.toString(), nsops.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMsgctl(msqid, cmd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMsgget(key, msgflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMsgrcv(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgtyp.toString(), msgflg).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMsgsnd(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFallocate(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxReadahead(fd, offset.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxLgetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxLsetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxLlistxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFlistxattr(filedes, list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxRemovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxLremovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFremovexattr(filedes, name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSignalfd(fd, mask.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxTimerfdGettime(fd, currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxTimerfdSettime(fd, flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxQuotactl(cmd, special.rawAddress.toString(), id, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEpollPwait(epfd, events.rawAddress.toString(), maxevents, timeout, sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSigtimedwait(set.rawAddress.toString(), info.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSigwaitinfo(set.rawAddress.toString(), info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxAccept4(fd, addr.rawAddress.toString(), len.rawAddress.toString(), flg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun reboot(howTo: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxReboot(howTo) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsgid(gid: GidT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSetfsgid(gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsuid(uid: UidT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSetfsuid(uid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd, offset.toString(), nbytes.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMremap(addr.rawAddress.toString(), len.toString(), newLen.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxRemapFilePages(addr.rawAddress.toString(), size.toString(), prot, pgoff.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMkstemps(template.rawAddress.toString(), suffixlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vhangup(): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxVhangup() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (libcUnixLinuxlikeLinuxNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun syncfs(fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSyncfs(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSyscall(num.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate(size: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEpollCreate(size) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate1(flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEpollWait(epfd, events.rawAddress.toString(), maxevents, timeout) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEpollCtl(epfd, op, fd, event.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unshare(flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxUnshare(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount(target: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxUmount(target.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxTee(fdIn, fdOut, len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSplice(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEventfd(initval.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEventfdRead(fd, value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd, value.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(pid, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedSetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSetns(fd, nstype) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapoff(path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSwapoff(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxVmsplice(fd, iov.rawAddress.toString(), nrSegs.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun personality(persona: CUlong): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPersonality(persona.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedGetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxClone(0L.toString(), childStack.rawAddress.toString(), flags, arg.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetscheduler(pid: PidT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxClockNanosleep(clkId.toInt(), flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxUmount2(target.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSwapon(path.rawAddress.toString(), swapflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(pid, policy, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSendfile(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdtablesize(): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGetdtablesize() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGetgrouplist(user.rawAddress.toString(), group, groups.rawAddress.toString(), ngroups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFreadUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit(): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxInotifyInit() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit1(flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(fd, path.rawAddress.toString(), mask.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags.toInt(), eventFFlags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGethostid().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxKlogctl(syslogType, bufp.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxNameToHandleAt(dirfd, path.rawAddress.toString(), handle.rawAddress.toString(), mountId.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(mountFd, handle.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGetnetbyname(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(net.toInt(), type).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetent(): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGetnetent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setnetent(stayopen: CInt) {
    if (libcUnixLinuxlikeLinuxNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endnetent() {
    if (libcUnixLinuxlikeLinuxNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxEndnetent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFallocate64(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxFgetpos64(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxFopen64(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeLinuxNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxSendfile64(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile64(): MutPtr =
    if (libcUnixLinuxlikeLinuxNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxTmpfile64().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

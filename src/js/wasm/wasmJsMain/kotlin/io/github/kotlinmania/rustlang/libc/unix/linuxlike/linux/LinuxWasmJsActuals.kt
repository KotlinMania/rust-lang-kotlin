@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.ClockidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.KeyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initUnixLinuxlikeLinuxNapiAddon(): Boolean

private val unixLinuxlikeLinuxNapiAvailable: Boolean = initUnixLinuxlikeLinuxNapiAddon()

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetspnamR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGetspnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcUnixLinuxlikeLinuxMqOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqClose(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxMqClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqUnlink(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxMqUnlink(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqReceive(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxMqReceive(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqTimedreceive(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxMqTimedreceive(p0: Int, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqSend(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxMqSend(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqTimedsend(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxMqTimedsend(p0: Int, p1: String, p2: String, p3: Int, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqGetattr(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxMqGetattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMqSetattr(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxMqSetattr(p0: Int, p1: String, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMrand48()")
private external fun wasmLibcUnixLinuxlikeLinuxMrand48(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSeed48(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSeed48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLcong48(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxLcong48(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLutimes(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxLutimes(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxShmOpen(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxShmOpen(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxShmUnlink(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxShmUnlink(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFtok(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxFtok(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSemget(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSemget(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSemop(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSemop(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSemctl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmLibcUnixLinuxlikeLinuxSemctl(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMsgctl(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxMsgctl(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMsgget(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxMsgget(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMsgrcv(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxMsgrcv(p0: Int, p1: String, p2: String, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMsgsnd(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxMsgsnd(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFallocate(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxFallocate(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixFallocate(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixFallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxReadahead(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxReadahead(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetxattr(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLgetxattr(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxLgetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFgetxattr(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxFgetxattr(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSetxattr(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxSetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxLsetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxFsetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxListxattr(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxListxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLlistxattr(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxLlistxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFlistxattr(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxFlistxattr(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxRemovexattr(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxRemovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxLremovexattr(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxLremovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFremovexattr(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxFremovexattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSignalfd(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSignalfd(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxTimerfdCreate(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxTimerfdCreate(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxTimerfdGettime(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxTimerfdGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxTimerfdSettime(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxTimerfdSettime(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxQuotactl(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxQuotactl(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEpollPwait(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxEpollPwait(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxDup3(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxDup3(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSigtimedwait(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSigtimedwait(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSigwaitinfo(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSigwaitinfo(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxAccept4(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxAccept4(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxReboot(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxReboot(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSetfsgid(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSetfsgid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSetfsuid(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSetfsuid(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMkfifoat(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxMkfifoat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSyncFileRange(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxSyncFileRange(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixMadvise(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMremap(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmLibcUnixLinuxlikeLinuxMremap(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String, p6: String, p7: String, p8: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxRemapFilePages(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxRemapFilePages(p0: String, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxMkstemps(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxMkstemps(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxVhangup()")
private external fun wasmLibcUnixLinuxlikeLinuxVhangup(): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSync()")
private external fun wasmLibcUnixLinuxlikeLinuxSync()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSyncfs(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSyncfs(p0: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSyscall(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxSyscall(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedSetaffinity(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedSetaffinity(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEpollCreate(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxEpollCreate(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEpollCreate1(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxEpollCreate1(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEpollWait(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxEpollWait(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEpollCtl(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxEpollCtl(p0: Int, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxUnshare(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxUnshare(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxUmount(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxUmount(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxTee(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxTee(p0: Int, p1: Int, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSplice(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxSplice(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEventfd(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxEventfd(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEventfdRead(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxEventfdRead(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEventfdWrite(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxEventfdWrite(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedRrGetInterval(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedRrGetInterval(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedSetparam(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedSetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSetns(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSetns(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSwapoff(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSwapoff(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxVmsplice(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxVmsplice(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPersonality(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxPersonality(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedGetparam(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedGetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxClone(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmLibcUnixLinuxlikeLinuxClone(p0: String, p1: String, p2: Int, p3: String, p4: Int, p5: String, p6: String, p7: String, p8: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedGetscheduler(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedGetscheduler(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxClockNanosleep(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxClockNanosleep(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxUmount2(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxUmount2(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSwapon(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSwapon(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSchedSetscheduler(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxSchedSetscheduler(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSendfile(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxSendfile(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSigaltstack(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxSigaltstack(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetdtablesize()")
private external fun wasmLibcUnixLinuxlikeLinuxGetdtablesize(): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetgrouplist(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGetgrouplist(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawn(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawn(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnp(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnp(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrInit(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrDestroy(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetflags(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetflags(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFreadUnlocked(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxFreadUnlocked(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxInotifyRmWatch(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxInotifyRmWatch(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxInotifyInit()")
private external fun wasmLibcUnixLinuxlikeLinuxInotifyInit(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxInotifyInit1(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxInotifyInit1(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxInotifyAddWatch(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxInotifyAddWatch(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFanotifyInit(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxFanotifyInit(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGethostid()")
private external fun wasmLibcUnixLinuxlikeLinuxGethostid(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxKlogctl(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxKlogctl(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxNameToHandleAt(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxNameToHandleAt(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxOpenByHandleAt(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxOpenByHandleAt(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetnetbyname(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGetnetbyname(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetnetbyaddr(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGetnetbyaddr(p0: Int, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGetnetent()")
private external fun wasmLibcUnixLinuxlikeLinuxGetnetent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSetnetent(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxSetnetent(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxEndnetent()")
private external fun wasmLibcUnixLinuxlikeLinuxEndnetent()

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFallocate64(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxFallocate64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFgetpos64(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxFgetpos64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxFopen64(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxFopen64(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxPosixFallocate64(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxPosixFallocate64(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxSendfile64(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxSendfile64(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxTmpfile64()")
private external fun wasmLibcUnixLinuxlikeLinuxTmpfile64(): String

public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxGetspnamR(name.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqClose(mqd: MqdT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqClose(mqd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqUnlink(name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqReceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqTimedreceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString(), absTimeout.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqSend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqTimedsend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt(), absTimeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqGetattr(mqd, attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMqSetattr(mqd, newattr.rawAddress.toString(), oldattr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mrand48(): CLong =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMrand48().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seed48(xseed: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxSeed48(xseed.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lcong48(p: MutPtr) {
    if (unixLinuxlikeLinuxNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxLcong48(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxLutimes(file.rawAddress.toString(), times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxShmOpen(name.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmUnlink(name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxShmUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFtok(pathname.rawAddress.toString(), projId) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSemget(key, nsems, semflag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSemop(semid, sops.rawAddress.toString(), nsops.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMsgctl(msqid, cmd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMsgget(key, msgflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMsgrcv(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgtyp.toString(), msgflg).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMsgsnd(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFallocate(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixFallocate(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxReadahead(fd, offset.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxLgetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxLsetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxLlistxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFlistxattr(filedes, list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxRemovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxLremovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFremovexattr(filedes, name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSignalfd(fd, mask.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxTimerfdCreate(clockid, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxTimerfdGettime(fd, currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxTimerfdSettime(fd, flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxQuotactl(cmd, special.rawAddress.toString(), id, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEpollPwait(epfd, events.rawAddress.toString(), maxevents, timeout, sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSigtimedwait(set.rawAddress.toString(), info.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSigwaitinfo(set.rawAddress.toString(), info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxAccept4(fd, addr.rawAddress.toString(), len.rawAddress.toString(), flg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun reboot(howTo: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxReboot(howTo) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsgid(gid: GidT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSetfsgid(gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsuid(uid: UidT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSetfsuid(uid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSyncFileRange(fd, offset.toString(), nbytes.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxMremap(addr.rawAddress.toString(), len.toString(), newLen.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxRemapFilePages(addr.rawAddress.toString(), size.toString(), prot, pgoff.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxMkstemps(template.rawAddress.toString(), suffixlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vhangup(): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxVhangup() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (unixLinuxlikeLinuxNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun syncfs(fd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSyncfs(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSyscall(num.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedSetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate(size: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEpollCreate(size) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate1(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEpollCreate1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEpollWait(epfd, events.rawAddress.toString(), maxevents, timeout) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEpollCtl(epfd, op, fd, event.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unshare(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxUnshare(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount(target: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxUmount(target.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxTee(fdIn, fdOut, len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSplice(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEventfd(initval.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEventfdRead(fd, value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxEventfdWrite(fd, value.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedRrGetInterval(pid, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedSetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSetns(fd, nstype) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapoff(path: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSwapoff(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxVmsplice(fd, iov.rawAddress.toString(), nrSegs.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun personality(persona: CUlong): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPersonality(persona.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedGetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxClone(0L.toString(), childStack.rawAddress.toString(), flags, arg.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetscheduler(pid: PidT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedGetscheduler(pid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxClockNanosleep(clkId, flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxUmount2(target.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSwapon(path.rawAddress.toString(), swapflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSchedSetscheduler(pid, policy, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSendfile(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdtablesize(): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxGetdtablesize() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxGetgrouplist(user.rawAddress.toString(), group, groups.rawAddress.toString(), ngroups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFreadUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit(): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxInotifyInit() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit1(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxInotifyInit1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxInotifyAddWatch(fd, path.rawAddress.toString(), mask.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFanotifyInit(flags.toInt(), eventFFlags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxGethostid().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxKlogctl(syslogType, bufp.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxNameToHandleAt(dirfd, path.rawAddress.toString(), handle.rawAddress.toString(), mountId.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxOpenByHandleAt(mountFd, handle.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGetnetbyname(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGetnetbyaddr(net.toInt(), type).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetent(): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGetnetent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setnetent(stayopen: CInt) {
    if (unixLinuxlikeLinuxNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxSetnetent(stayopen)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endnetent() {
    if (unixLinuxlikeLinuxNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxEndnetent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFallocate64(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxFgetpos64(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxFopen64(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxPosixFallocate64(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAvailable) wasmLibcUnixLinuxlikeLinuxSendfile64(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile64(): MutPtr =
    if (unixLinuxlikeLinuxNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxTmpfile64().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

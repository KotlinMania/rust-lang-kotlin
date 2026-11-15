@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
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
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeAndroidNapiAddon(): Boolean

private val libcUnixLinuxlikeAndroidNapiAvailable: Boolean = initLibcUnixLinuxlikeAndroidNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEndpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEndpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetpwent(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetgrent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEndgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEndgrent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrent(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetrlimit64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetrlimit64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPrlimit(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPrlimit(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPrlimit64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPrlimit64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidStrerrorR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidStrerrorR(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMlock2(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMlock2(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMadvise(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMsync(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMsync(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMprotect(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMprotect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetnameinfo(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetnameinfo(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String, p6: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPreadv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPreadv(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPwritev(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPwritev(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidProcessVmReadv(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidProcessVmWritev(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPtrace(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPtrace(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetpriority(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetpriority(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedCpualloc(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedCpufree(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedCpufree(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedCpucount(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedCpucount(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetcpu()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetcpu(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMallocInfo(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMallocInfo(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMallocUsableSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidUtmpname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidUtmpname(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetutent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetutent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetutent()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetutent(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSeekdir(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSeekdir(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTelldir(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTelldir(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFallocate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFallocate(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFallocate64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFallocate64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPosixFallocate(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPosixFallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPosixFallocate64(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidLgetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidLgetxattr(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFgetxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFgetxattr(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidLsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidLsetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFsetxattr(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFsetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidListxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidListxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidLlistxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidLlistxattr(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFlistxattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFlistxattr(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRemovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRemovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidLremovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidLremovexattr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFremovexattr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFremovexattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSignalfd(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSignalfd(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerfdCreate(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerfdGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerfdSettime(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerCreate(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerCreate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerDelete(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerDelete(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerGettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerGettime(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTimerSettime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTimerSettime(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSyscall(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSyscall(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEpollCreate(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEpollCreate(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEpollCreate1(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEpollCreate1(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEpollWait(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEpollWait(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEpollCtl(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEpollCtl(p0: Int, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidUnshare(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidUnshare(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidUmount(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidUmount(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTee(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTee(p0: Int, p1: Int, p2: String, p3: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSplice(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSplice(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEventfd(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEventfd(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEventfdRead(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEventfdRead(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidEventfdWrite(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidEventfdWrite(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSemTimedwait(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSemTimedwait(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSemGetvalue(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSemGetvalue(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedSetparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedSetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetns(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetns(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSwapoff(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSwapoff(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidVmsplice(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidVmsplice(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMount(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMount(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPersonality(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPersonality(p0: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPrctl(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPrctl(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetparam(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetparam(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPpoll(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPpoll(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidClone(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidClone(p0: String, p1: String, p2: Int, p3: String, p4: Int, p5: String, p6: String, p7: String, p8: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidClockNanosleep(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidClockNanosleep(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSethostname(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSethostname(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSysinfo(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSysinfo(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidUmount2(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidUmount2(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSwapon(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSwapon(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSendfile(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSendfile(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSendfile64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSendfile64(p0: Int, p1: Int, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetfsgid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetfsgid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSetfsuid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSetfsuid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSigsuspend(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSigsuspend(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrgidR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrgidR(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSigaltstack(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSigaltstack(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSemClose(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSemClose(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSemOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSemOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrnam(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrnam(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSemUnlink(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSemUnlink(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidDaemon(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidDaemon(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSigtimedwait(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSigtimedwait(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSigwait(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSigwait(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrgid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrgid(p0: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetgrouplist(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetgrouplist(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidInitgroups(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidInitgroups(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPopen(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFaccessat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFaccessat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidErrno()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidErrno(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidInotifyInit()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidInotifyInit(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidInotifyInit1(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidInotifyInit1(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRegcomp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRegcomp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRegexec(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRegexec(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRegerror(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRegerror(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRegfree(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRegfree(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGettid()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGettid(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidTgkill(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidTgkill(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetauxval(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetauxval(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetrandom(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetrandom(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetentropy(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertySet(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidArc4random()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidArc4random(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidArc4randomUniform(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidArc4randomBuf(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidReallocarray(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidReallocarray(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidDirname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidBasename(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidBasename(p0: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidGetoptLong(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidGetoptLong(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSync()")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSync()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidSyncfs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidSyncfs(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMemmem(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMemmem(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFreadUnlocked(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFflushUnlocked(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidKlogctl(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidKlogctl(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidMemfdCreate(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidMemfdCreate(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidRenameat2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidRenameat2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPreadv2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPreadv2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAndroidPwritev2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeAndroidPwritev2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

public actual fun setpwent() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSetpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endpwent() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidEndpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpwent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidGetpwent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endgrent() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidEndgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidGetgrent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit64(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetrlimit64(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetrlimit64(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPrlimit(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPrlimit64(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMlock2(addr.rawAddress.toString(), len.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMsync(addr.rawAddress.toString(), len.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMprotect(addr.rawAddress.toString(), len.toString(), prot) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SizeT, serv: MutPtr, servlen: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toString(), serv.rawAddress.toString(), servlen.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPreadv(fd, iov.rawAddress.toString(), count, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPwritev(fd, iov.rawAddress.toString(), count, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidProcessVmReadv(pid, localIov.rawAddress.toString(), localIovCount.toString(), remoteIov.rawAddress.toString(), remoteIovCount.toString(), flags.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidProcessVmWritev(pid, localIov.rawAddress.toString(), localIovCount.toString(), remoteIov.rawAddress.toString(), remoteIovCount.toString(), flags.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPtrace(request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: IdT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetpriority(which, who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetpriority(which, who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedCpualloc(count: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidSchedCpualloc(count.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedCpufree(set: MutPtr) {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSchedCpufree(set.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun schedCpucount(setsize: SizeT, set: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedCpucount(setsize.toString(), set.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetcpu(): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetcpu() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMallocInfo(options, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: ConstPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMallocUsableSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpname(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidUtmpname(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutent() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSetutent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getutent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidGetutent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTelldir(dirp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFallocate(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFallocate64(fd, mode, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidLgetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidLsetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidLlistxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFlistxattr(filedes, list.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidRemovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidLremovexattr(path.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFremovexattr(filedes, name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSignalfd(fd, mask.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdCreate(clock: ClockidT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdGettime(fd: CInt, currentValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerfdGettime(fd, currentValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerfdSettime(fd, flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerCreate(clockid.toInt(), sevp.rawAddress.toString(), timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerDelete(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerDelete(timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerGettime(timerid.rawAddress.toString(), currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTimerSettime(timerid.rawAddress.toString(), flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSyscall(num.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate(size: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEpollCreate(size) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate1(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEpollWait(epfd, events.rawAddress.toString(), maxevents, timeout) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEpollCtl(epfd, op, fd, event.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unshare(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidUnshare(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount(target: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidUmount(target.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTee(fdIn, fdOut, len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSplice(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEventfd(initval.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEventfdRead(fd, value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd, value.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(pid, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSemTimedwait(sem.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSemGetvalue(sem.rawAddress.toString(), sval.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedSetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetns(fd, nstype) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapoff(puath: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSwapoff(puath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidVmsplice(fd, iov.rawAddress.toString(), nrSegs.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMount(src.rawAddress.toString(), target.rawAddress.toString(), fstype.rawAddress.toString(), flags.toString(), data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun personality(persona: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPersonality(persona.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPrctl(option, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetparam(pid, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPpoll(fds.rawAddress.toString(), nfds.toInt(), timeout.rawAddress.toString(), sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidClone(0L.toString(), childStack.rawAddress.toString(), flags, arg.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetscheduler(pid: PidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidClockNanosleep(clkId.toInt(), flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSethostname(name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysinfo(info: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSysinfo(info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidUmount2(target.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSwapon(path.rawAddress.toString(), swapflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(pid, policy, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSendfile(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSendfile64(outFd, inFd, offset.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsgid(gid: GidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetfsgid(gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsuid(uid: UidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSetfsuid(uid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSigsuspend(mask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetgrgidR(gid, grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semClose(sem: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSemClose(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetgrnamR(name.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidSemOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidGetgrnam(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semUnlink(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSemUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidDaemon(nochdir, noclose) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSigtimedwait(set.rawAddress.toString(), info.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSigwait(set.rawAddress.toString(), sig.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgid(gid: GidT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidGetgrgid(gid).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetgrouplist(user.rawAddress.toString(), group, groups.rawAddress.toString(), ngroups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidInitgroups(user.rawAddress.toString(), group) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidPopen(command.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun errno(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidErrno().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyRmWatch(fd: CInt, wd: UInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd, wd.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit(): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidInotifyInit() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit1(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(fd, path.rawAddress.toString(), mask.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidRegcomp(preg.rawAddress.toString(), pattern.rawAddress.toString(), cflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidRegexec(preg.rawAddress.toString(), input.rawAddress.toString(), nmatch.toString(), pmatch.rawAddress.toString(), eflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidRegerror(errcode, preg.rawAddress.toString(), errbuf.rawAddress.toString(), errbufSize.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regfree(preg: MutPtr) {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidRegfree(preg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun androidSetAbortMessage(msg: ConstPtr) {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(msg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun gettid(): PidT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGettid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidTgkill(tgid, tid, sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetauxval(type.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertySet(name: ConstPtr, value: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertySet(name.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyGet(name: ConstPtr, value: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(name.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyFind(name: ConstPtr): ConstPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) ConstPtr(wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyFindNth(n: CUint): ConstPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) ConstPtr(wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(n.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyForeach(callback: SystemPropertyForeachCallbackCallback, cookie: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(0L.toString(), cookie.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(0L.toString(), data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4random(): UInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidArc4random().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomUniform(upperBound: UInt): UInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(buf: MutPtr, n: SizeT) {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidArc4randomBuf(buf.rawAddress.toString(), n.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidGetoptLong(argc, argv.rawAddress.toString(), optstring.rawAddress.toString(), longopts.rawAddress.toString(), longindex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (libcUnixLinuxlikeAndroidNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeAndroidSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun syncfs(fd: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidSyncfs(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFreadUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwriteUnlocked(buf: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflushUnlocked(stream: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidFflushUnlocked(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetsUnlocked(buf: MutPtr, size: CInt, stream: MutPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(buf.rawAddress.toString(), size, stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidKlogctl(syslogType, bufp.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidMemfdCreate(name.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAvailable) wasmRustlangLibcUnixLinuxlikeAndroidPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

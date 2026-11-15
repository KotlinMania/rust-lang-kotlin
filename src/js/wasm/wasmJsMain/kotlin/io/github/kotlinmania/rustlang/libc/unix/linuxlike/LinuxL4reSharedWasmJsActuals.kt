@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeLinuxL4reSharedNapiAddon(): Boolean

private val libcUnixLinuxlikeLinuxL4reSharedNapiAvailable: Boolean = initLibcUnixLinuxlikeLinuxL4reSharedNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRand()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRand(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48(): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(p0: String): Double

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(p0: Int, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(p0: String, p1: String, p2: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation(): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(p0: Int, p1: String, p2: String, p3: Int, p4: String, p5: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(p0: Int, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedMount(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMount(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGettid(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(p0: String, p1: String, p2: String, p3: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu(): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(p0: String): String

public actual fun iopl(level: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioperm(from: CUlong, num: CUlong, turnOn: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from.toString(), num.toString(), turnOn) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioRead(aiocbp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(op, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(aiocbp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(aiocbList.rawAddress.toString(), nitems, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(fd, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(mode, aiocbList.rawAddress.toString(), nitems, sevp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toInt(), serv.rawAddress.toString(), servlen.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(loadavg.rawAddress.toString(), nelem) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(pid, localIov.rawAddress.toString(), liovcnt.toString(), remoteIov.rawAddress.toString(), riovcnt.toString(), flags.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(pid, localIov.rawAddress.toString(), liovcnt.toString(), remoteIov.rawAddress.toString(), riovcnt.toString(), flags.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun drand48(): CDouble =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erand48(xseed: MutPtr): CDouble =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(xseed.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lrand48(): CLong =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nrand48(xseed: MutPtr): CLong =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(xseed.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun jrand48(xseed: MutPtr): CLong =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(xseed.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand48(seed: CLong) {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setpwent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endpwent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpwent(): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endgrent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrent(): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setspent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endspent() {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getspent(): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getspnam(name: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key, size.toString(), shmflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(shmid, shmaddr.rawAddress.toString(), shmflg).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(shmaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(shmid, cmd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(addr.rawAddress.toString(), len.toString(), prot) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun errnoLocation(): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree(pglob: MutPtr) {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(dirp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(addr.rawAddress.toString(), len.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(socket, buf.rawAddress.toString(), len.toString(), flags, addr.rawAddress.toString(), addrlen.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfo(item: NlItem): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(item).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfoL(item: NlItem, locale: LocaleT): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(item, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(sem.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(sem.rawAddress.toString(), sval.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMount(src.rawAddress.toString(), target.rawAddress.toString(), fstype.rawAddress.toString(), flags.toString(), data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(option, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(fds.rawAddress.toString(), nfds.toString(), timeout.rawAddress.toString(), sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysinfo(info: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(mask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(gid, grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semClose(sem: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(name.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(user.rawAddress.toString(), group) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semUnlink(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir, noclose) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(set.rawAddress.toString(), sig.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgid(gid: GidT): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(gid).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(command.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(0L.toString(), data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setmntent(filename: ConstPtr, ty: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(filename.rawAddress.toString(), ty.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntent(stream: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun addmntent(stream: MutPtr, mnt: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(stream.rawAddress.toString(), mnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun endmntent(streamp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(streamp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hasmntopt(mnt: ConstPtr, opt: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(mnt.rawAddress.toString(), opt.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(preg.rawAddress.toString(), pattern.rawAddress.toString(), cflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(preg.rawAddress.toString(), input.rawAddress.toString(), nmatch.toString(), pmatch.rawAddress.toString(), eflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(errcode, preg.rawAddress.toString(), errbuf.rawAddress.toString(), errbufSize.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regfree(preg: MutPtr) {
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(preg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(tocode.rawAddress.toString(), fromcode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(cd.rawAddress.toString(), inbuf.rawAddress.toString(), inbytesleft.rawAddress.toString(), outbuf.rawAddress.toString(), outbytesleft.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvClose(cd: IconvT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(cd.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettid(): PidT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGettid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(clockid.toInt(), sevp.rawAddress.toString(), timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerDelete(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(timerid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(timerid.rawAddress.toString(), currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(timerid.rawAddress.toString(), flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetcpu(): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(argc, argv.rawAddress.toString(), optstring.rawAddress.toString(), longopts.rawAddress.toString(), longindex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyFileRange(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen64(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos64(stream: MutPtr, ptr: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftello64(stream: MutPtr): Off64T =
    if (libcUnixLinuxlikeLinuxL4reSharedNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

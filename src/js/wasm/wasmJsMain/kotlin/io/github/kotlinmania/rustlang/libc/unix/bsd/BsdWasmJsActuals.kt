@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd

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
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixBsdNapiAddon(): Boolean

private val libcUnixBsdNapiAvailable: Boolean = initLibcUnixBsdNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixBsdGetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixBsdSetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdStrerrorR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdStrerrorR(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAbs(p0)")
private external fun wasmRustlangLibcUnixBsdAbs(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdLabs(p0)")
private external fun wasmRustlangLibcUnixBsdLabs(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRand()")
private external fun wasmRustlangLibcUnixBsdRand(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSrand(p0)")
private external fun wasmRustlangLibcUnixBsdSrand(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetifaddrs(p0)")
private external fun wasmRustlangLibcUnixBsdGetifaddrs(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdFreeifaddrs(p0)")
private external fun wasmRustlangLibcUnixBsdFreeifaddrs(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetgroups(p0, p1)")
private external fun wasmRustlangLibcUnixBsdSetgroups(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetlogin(p0)")
private external fun wasmRustlangLibcUnixBsdSetlogin(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdIoctl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixBsdIoctl(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdKqueue()")
private external fun wasmRustlangLibcUnixBsdKqueue(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdUnmount(p0, p1)")
private external fun wasmRustlangLibcUnixBsdUnmount(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetpwent()")
private external fun wasmRustlangLibcUnixBsdGetpwent(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetpwent()")
private external fun wasmRustlangLibcUnixBsdSetpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdEndpwent()")
private external fun wasmRustlangLibcUnixBsdEndpwent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdEndgrent()")
private external fun wasmRustlangLibcUnixBsdEndgrent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetgrent()")
private external fun wasmRustlangLibcUnixBsdGetgrent(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetprogname()")
private external fun wasmRustlangLibcUnixBsdGetprogname(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetprogname(p0)")
private external fun wasmRustlangLibcUnixBsdSetprogname(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetloadavg(p0, p1)")
private external fun wasmRustlangLibcUnixBsdGetloadavg(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdIfNameindex()")
private external fun wasmRustlangLibcUnixBsdIfNameindex(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdIfFreenameindex(p0)")
private external fun wasmRustlangLibcUnixBsdIfFreenameindex(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetpeereid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdGetpeereid(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGlob(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdGlob(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGlobfree(p0)")
private external fun wasmRustlangLibcUnixBsdGlobfree(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPosixMadvise(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdPosixMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdShmUnlink(p0)")
private external fun wasmRustlangLibcUnixBsdShmUnlink(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSeekdir(p0, p1)")
private external fun wasmRustlangLibcUnixBsdSeekdir(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdTelldir(p0)")
private external fun wasmRustlangLibcUnixBsdTelldir(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdMadvise(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdMadvise(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdMinherit(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdMinherit(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdMsync(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdMsync(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdRecvfrom(p0: Int, p1: String, p2: String, p3: Int, p4: String, p5: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdMkstemps(p0, p1)")
private external fun wasmRustlangLibcUnixBsdMkstemps(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdFutimes(p0, p1)")
private external fun wasmRustlangLibcUnixBsdFutimes(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdNlLanginfo(p0)")
private external fun wasmRustlangLibcUnixBsdNlLanginfo(p0: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdBind(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdBind(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdWritev(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdWritev(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdReadv(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdReadv(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSendmsg(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdSendmsg(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRecvmsg(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdRecvmsg(p0: Int, p1: String, p2: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSync()")
private external fun wasmRustlangLibcUnixBsdSync()

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetgrgidR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdGetgrgidR(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSigaltstack(p0, p1)")
private external fun wasmRustlangLibcUnixBsdSigaltstack(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSigsuspend(p0)")
private external fun wasmRustlangLibcUnixBsdSigsuspend(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSemClose(p0)")
private external fun wasmRustlangLibcUnixBsdSemClose(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetdtablesize()")
private external fun wasmRustlangLibcUnixBsdGetdtablesize(): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetgrnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdGetgrnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPthreadSigmask(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdPthreadSigmask(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSemOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixBsdSemOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetgrnam(p0)")
private external fun wasmRustlangLibcUnixBsdGetgrnam(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPthreadCancel(p0)")
private external fun wasmRustlangLibcUnixBsdPthreadCancel(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPthreadKill(p0, p1)")
private external fun wasmRustlangLibcUnixBsdPthreadKill(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSchedGetPriorityMin(p0)")
private external fun wasmRustlangLibcUnixBsdSchedGetPriorityMin(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSchedGetPriorityMax(p0)")
private external fun wasmRustlangLibcUnixBsdSchedGetPriorityMax(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSemUnlink(p0)")
private external fun wasmRustlangLibcUnixBsdSemUnlink(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetpwnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdGetpwnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetpwuidR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdGetpwuidR(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSigwait(p0, p1)")
private external fun wasmRustlangLibcUnixBsdSigwait(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPthreadAtfork(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdPthreadAtfork(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetgrgid(p0)")
private external fun wasmRustlangLibcUnixBsdGetgrgid(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPopen(p0, p1)")
private external fun wasmRustlangLibcUnixBsdPopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdFaccessat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdFaccessat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdPthreadCreate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdPthreadCreate(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAcct(p0)")
private external fun wasmRustlangLibcUnixBsdAcct(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdWait4(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdWait4(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetitimer(p0, p1)")
private external fun wasmRustlangLibcUnixBsdGetitimer(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSetitimer(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdSetitimer(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRegcomp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdRegcomp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRegexec(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdRegexec(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRegerror(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdRegerror(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdRegfree(p0)")
private external fun wasmRustlangLibcUnixBsdRegfree(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdArc4random()")
private external fun wasmRustlangLibcUnixBsdArc4random(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdArc4randomBuf(p0, p1)")
private external fun wasmRustlangLibcUnixBsdArc4randomBuf(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdArc4randomUniform(p0)")
private external fun wasmRustlangLibcUnixBsdArc4randomUniform(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdDrand48()")
private external fun wasmRustlangLibcUnixBsdDrand48(): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdErand48(p0)")
private external fun wasmRustlangLibcUnixBsdErand48(p0: String): Double

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdLrand48()")
private external fun wasmRustlangLibcUnixBsdLrand48(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdNrand48(p0)")
private external fun wasmRustlangLibcUnixBsdNrand48(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdMrand48()")
private external fun wasmRustlangLibcUnixBsdMrand48(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdJrand48(p0)")
private external fun wasmRustlangLibcUnixBsdJrand48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSrand48(p0)")
private external fun wasmRustlangLibcUnixBsdSrand48(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSeed48(p0)")
private external fun wasmRustlangLibcUnixBsdSeed48(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdLcong48(p0)")
private external fun wasmRustlangLibcUnixBsdLcong48(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdGetoptLong(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdGetoptLong(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdStrftime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdStrftime(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdStrftimeL(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdStrftimeL(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdDevname(p0, p1)")
private external fun wasmRustlangLibcUnixBsdDevname(p0: Int, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdIssetugid()")
private external fun wasmRustlangLibcUnixBsdIssetugid(): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdSyscall(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdSyscall(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdAbs(i) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdLabs(i.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getifaddrs(ifap: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetifaddrs(ifap.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeifaddrs(ifa: MutPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdFreeifaddrs(ifa.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setgroups(ngroups: CInt, ptr: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSetgroups(ngroups, ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlogin(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSetlogin(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctl(fd: CInt, request: CUlong, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdIoctl(fd, request.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kqueue(): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdKqueue() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unmount(target: ConstPtr, arg: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdUnmount(target.rawAddress.toString(), arg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwent(): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdGetpwent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpwent() {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSetpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endpwent() {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdEndpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endgrent() {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdEndgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrent(): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdGetgrent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getprogname(): ConstPtr =
    if (libcUnixBsdNapiAvailable) ConstPtr(wasmRustlangLibcUnixBsdGetprogname().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setprogname(name: ConstPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSetprogname(name.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetloadavg(loadavg.rawAddress.toString(), nelem) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNameindex(): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdIfNameindex().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifFreenameindex(ptr: MutPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdIfFreenameindex(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpeereid(socket: CInt, euid: MutPtr, egid: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetpeereid(socket, euid.rawAddress.toString(), egid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGlob(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree(pglob: MutPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdGlobfree(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPosixMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmUnlink(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdShmUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdTelldir(dirp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdMadvise(addr.rawAddress.toString(), len.toString(), advice) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minherit(addr: MutPtr, len: SizeT, inherit: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdMinherit(addr.rawAddress.toString(), len.toString(), inherit) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdMsync(addr.rawAddress.toString(), len.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRecvfrom(socket, buf.rawAddress.toString(), len.toString(), flags, addr.rawAddress.toString(), addrlen.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdMkstemps(template.rawAddress.toString(), suffixlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdFutimes(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfo(item: NlItem): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdNlLanginfo(item).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdBind(socket, address.rawAddress.toString(), addressLen.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdWritev(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdReadv(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSendmsg(fd, msg.rawAddress.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRecvmsg(fd, msg.rawAddress.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetgrgidR(gid, grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSigsuspend(mask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semClose(sem: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSemClose(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdtablesize(): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetdtablesize() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetgrnamR(name.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPthreadSigmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdSemOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdGetgrnam(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCancel(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPthreadCancel(thread.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKill(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPthreadKill(thread.toString(), sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSchedGetPriorityMin(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSchedGetPriorityMax(policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semUnlink(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSemUnlink(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetpwnamR(name.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetpwuidR(uid, pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSigwait(set.rawAddress.toString(), sig.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPthreadAtfork(0L.toString(), 0L.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgid(gid: GidT): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdGetgrgid(gid).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdPopen(command.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acct(filename: ConstPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdAcct(filename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdWait4(pid, status.rawAddress.toString(), options, rusage.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetitimer(which, currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSetitimer(which, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRegcomp(preg.rawAddress.toString(), pattern.rawAddress.toString(), cflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRegexec(preg.rawAddress.toString(), input.rawAddress.toString(), nmatch.toString(), pmatch.rawAddress.toString(), eflags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdRegerror(errcode, preg.rawAddress.toString(), errbuf.rawAddress.toString(), errbufSize.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regfree(preg: MutPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdRegfree(preg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4random(): UInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdArc4random().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(buf: MutPtr, size: SizeT) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdArc4randomBuf(buf.rawAddress.toString(), size.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4randomUniform(l: UInt): UInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdArc4randomUniform(l.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun drand48(): CDouble =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdDrand48() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erand48(xseed: MutPtr): CDouble =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdErand48(xseed.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lrand48(): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdLrand48().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nrand48(xseed: MutPtr): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdNrand48(xseed.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mrand48(): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdMrand48().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun jrand48(xseed: MutPtr): CLong =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdJrand48(xseed.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand48(seed: CLong) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdSrand48(seed.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun seed48(xseed: MutPtr): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdSeed48(xseed.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lcong48(p: MutPtr) {
    if (libcUnixBsdNapiAvailable) {
        wasmRustlangLibcUnixBsdLcong48(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdGetoptLong(argc, argv.rawAddress.toString(), optstring.rawAddress.toString(), longopts.rawAddress.toString(), longindex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr): SizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdStrftime(buf.rawAddress.toString(), maxsize.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftimeL(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr, locale: LocaleT): SizeT =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdStrftimeL(buf.rawAddress.toString(), maxsize.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString(), locale.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun devname(dev: DevT, modeT: ModeT): MutPtr =
    if (libcUnixBsdNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdDevname(dev, modeT.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun issetugid(): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdIssetugid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdNapiAvailable) wasmRustlangLibcUnixBsdSyscall(num, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

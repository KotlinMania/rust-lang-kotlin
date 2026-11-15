@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.Sigval
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.MqdT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeLinuxGnuNapiAddon(): Boolean

private val libcUnixLinuxlikeLinuxGnuNapiAvailable: Boolean = initLibcUnixLinuxlikeLinuxGnuNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetspentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuQsortR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuQsortR(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPrlimit(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuUtmpname(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxid(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxline(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPututxline(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPututxline(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSetutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSetutxent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuEndutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuEndutxent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetpt()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpt(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMallopt(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMallopt(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrandom(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetauxval(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(p0: Int, p1: Int, p2: String, p3: Int, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPreadv2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPwritev2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuRenameat2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuReallocarray(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuCtermid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuCtermid(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktrace(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGlob64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGlob64(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPtrace(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPtrace(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(p0: String, p1: String, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMallocStats()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocStats()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPutgrent(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuSethostid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuSethostid(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMlock2(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMlock2(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuEaccess(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuEaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuCtimeR(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuDirname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuDlmopen(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuDlinfo(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuDladdr1(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuDlvsym(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(p0: String, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuExecveat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuExecveat(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuCloseRange(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMqNotify(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(p0: Int, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuMempcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxGnuTgkill(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxGnuTgkill(p0: Int, p1: Int, p2: Int): Int

public actual var ENVIRON: MutPtr
    get() = if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
            wasmRustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(value.rawAddress.toString())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual fun fgetspentR(fp: MutPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(fp.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sgetspentR(s: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(s.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getspentR(spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, compar: QsortRComparCallback?, arg: MutPtr) {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuQsortR(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPrlimit(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit64(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuUtmpname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxid(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGetutxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuPututxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpt(): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallopt(param: CInt, value: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMallopt(param, value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetauxval(type.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtimex(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId.toInt(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: ULong, dirfd: CInt, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd, flags.toInt(), mask.toString(), dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(s.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctermid(s: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuCtermid(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktrace(buf.rawAddress.toString(), sz) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(buffer: ConstPtr, len: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer.rawAddress.toString(), len).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(buffer: ConstPtr, len: CInt, fd: CInt) {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer.rawAddress.toString(), len, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun glob64(pattern: ConstPtr, flags: CInt, errfunc: Glob64ErrfuncCallback?, pglob: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGlob64(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree64(pglob: MutPtr) {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ptrace(request: CUint, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPtrace(request.toInt(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetaffinityNp(attr: ConstPtr, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetaffinityNp(attr: MutPtr, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpriority(which.toInt(), who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSetpriority(which.toInt(), who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetkindNp(attr: ConstPtr, value: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetkindNp(attr: MutPtr, value: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr.rawAddress.toString(), value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigqueue(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt, value: Sigval): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread.toString(), retval.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread.toString(), retval.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo2(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocStats() {
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocStats()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(options, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwentR(pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrentR(grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpwentR(stream: MutPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetgrentR(stream: MutPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetentR(resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbynameR(name: ConstPtr, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name.rawAddress.toString(), resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddrR(net: UInt, type: CInt, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net.toInt(), type, resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putpwent(p: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putgrent(grp: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPutgrent(grp.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuSethostid(hostid.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(name.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMlock2(addr.rawAddress.toString(), len.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuEaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(tm.rawAddress.toString(), buf.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(timep: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuCtimeR(timep.rawAddress.toString(), buf.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixBasename(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuBasename(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlmopen(lmid: LmidT, filename: ConstPtr, flag: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuDlmopen(lmid.toString(), filename.rawAddress.toString(), flag).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlinfo(handle: MutPtr, request: CInt, info: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuDlinfo(handle.rawAddress.toString(), request, info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dladdr1(addr: ConstPtr, info: MutPtr, extraInfo: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuDladdr1(addr.rawAddress.toString(), info.rawAddress.toString(), extraInfo.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlvsym(handle: MutPtr, symbol: ConstPtr, version: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuDlvsym(handle.rawAddress.toString(), symbol.rawAddress.toString(), version.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocTrim(pad: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(pad.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcRelease(): ConstPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) ConstPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcVersion(): ConstPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) ConstPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclosefromNp(actions: MutPtr, from: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions.rawAddress.toString(), from) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddtcsetpgrpNp(actions: MutPtr, tcfd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions.rawAddress.toString(), tcfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntentR(stream: MutPtr, mntbuf: MutPtr, buf: MutPtr, buflen: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(stream.rawAddress.toString(), mntbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execveat(dirfd: CInt, pathname: ConstPtr, argv: ConstPtr, envp: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuExecveat(dirfd, pathname.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeRange(first: CUint, last: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuCloseRange(first.toInt(), last.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqNotify(mqdes: MqdT, sevp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes, sevp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait2(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd, events.rawAddress.toString(), maxevents, timeout.rawAddress.toString(), sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mempcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxGnuMempcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxGnuTgkill(tgid, tid, sig) else throw UnsupportedOperationException("Native FFI addon is not available")

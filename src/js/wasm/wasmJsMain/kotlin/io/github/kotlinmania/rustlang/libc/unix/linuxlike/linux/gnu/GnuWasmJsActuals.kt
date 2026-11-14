@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.ClockidT
import io.github.kotlinmania.rustlang.libc.IdT
import io.github.kotlinmania.rustlang.libc.MqdT
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.Sigval
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initUnixLinuxlikeLinuxGnuNapiAddon(): Boolean

private val unixLinuxlikeLinuxGnuNapiAvailable: Boolean = initUnixLinuxlikeLinuxGnuNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetENVIRON()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetENVIRON(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSetENVIRON(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSetENVIRON(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuFgetspentR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuFgetspentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSgetspentR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSgetspentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetspentR(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetspentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuQsortR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuQsortR(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSendmmsg(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSendmmsg(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuRecvmmsg(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuRecvmmsg(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetrlimit64(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSetrlimit64(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSetrlimit64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetrlimit(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSetrlimit(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPrlimit(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPrlimit(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPrlimit64(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPrlimit64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuUtmpname(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuUtmpname(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuUtmpxname(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuUtmpxname(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetutxent()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetutxent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetutxid(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetutxid(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetutxline(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetutxline(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPututxline(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPututxline(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSetutxent()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSetutxent()

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuEndutxent()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuEndutxent()

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetpt()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetpt(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMallopt(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMallopt(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGettimeofday(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetentropy(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetrandom(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetrandom(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetauxval(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetauxval(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuAdjtimex(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuAdjtimex(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuNtpAdjtime(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuNtpAdjtime(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuNtpGettime(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuNtpGettime(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuClockAdjtime(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuClockAdjtime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuFanotifyMark(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuFanotifyMark(p0: Int, p1: Int, p2: String, p3: Int, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPreadv2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPreadv2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPwritev2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPwritev2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPreadv64v2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPreadv64v2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPwritev64v2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPwritev64v2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuRenameat2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuRenameat2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuExplicitBzero(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuExplicitBzero(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuReallocarray(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuReallocarray(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuCtermid(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuCtermid(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuBacktrace(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuBacktrace(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuBacktraceSymbols(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGlob64(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGlob64(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGlobfree64(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGlobfree64(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPtrace(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPtrace(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetpriority(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSetpriority(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(p0: String, p1: String, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMallocStats()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMallocStats()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMallocInfo(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMallocInfo(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMallocUsableSize(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMallocUsableSize(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetpwentR(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetpwentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetgrentR(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetgrentR(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuFgetpwentR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuFgetpwentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuFgetgrentR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuFgetgrentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetnetentR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetnetentR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetnetbynameR(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetnetbynameR(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetnetbyaddrR(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPutpwent(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPutpwent(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPutgrent(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPutgrent(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuSethostid(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuSethostid(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMemfdCreate(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMemfdCreate(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMlock2(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMlock2(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuEuidaccess(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuEuidaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuEaccess(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuEaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuAsctimeR(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuAsctimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuCtimeR(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuCtimeR(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuDirname(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPosixBasename(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPosixBasename(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGnuBasename(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGnuBasename(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuDlmopen(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuDlmopen(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuDlinfo(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuDlinfo(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuDladdr1(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuDladdr1(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuDlvsym(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuDlvsym(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMallocTrim(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMallocTrim(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGnuGetLibcRelease()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGnuGetLibcVersion()")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuGetmntentR(p0, p1, p2, p3)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuGetmntentR(p0: String, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuExecveat(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuExecveat(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuCloseRange(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuCloseRange(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMqNotify(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMqNotify(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuEpollPwait2(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuEpollPwait2(p0: Int, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuMempcpy(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuMempcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuTgkill(p0, p1, p2)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuTgkill(p0: Int, p1: Int, p2: Int): Int

public actual var ENVIRON: MutPtr
    get() = if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGetENVIRON().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (unixLinuxlikeLinuxGnuNapiAvailable) {
            wasmLibcUnixLinuxlikeLinuxGnuSetENVIRON(value.rawAddress.toString())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual fun fgetspentR(fp: MutPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuFgetspentR(fp.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sgetspentR(s: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSgetspentR(s.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getspentR(spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, compar: QsortRComparCallback?, arg: MutPtr) {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuQsortR(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit64(resource: RlimitResourceT, rlim: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit64(resource: RlimitResourceT, rlim: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit(resource: RlimitResourceT, rlim: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetrlimit(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: RlimitResourceT, rlim: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSetrlimit(resource.toInt(), rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPrlimit(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit64(pid: PidT, resource: RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPrlimit64(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpname(file: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuUtmpname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpxname(file: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuUtmpxname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGetutxent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGetutxid(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGetutxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuPututxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpt(): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetpt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallopt(param: CInt, value: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMallopt(param, value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetauxval(type.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtimex(buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuAdjtimex(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuNtpGettime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId.toInt(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: ULong, dirfd: CInt, path: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd, flags.toInt(), mask.toString(), dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuExplicitBzero(s.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctermid(s: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuCtermid(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuBacktrace(buf.rawAddress.toString(), sz) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(buffer: ConstPtr, len: CInt): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer.rawAddress.toString(), len).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(buffer: ConstPtr, len: CInt, fd: CInt) {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer.rawAddress.toString(), len, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun glob64(pattern: ConstPtr, flags: CInt, errfunc: Glob64ErrfuncCallback?, pglob: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGlob64(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree64(pglob: MutPtr) {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ptrace(request: CUint, vararg variadicArgs: Long): CLong =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPtrace(request.toInt(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetaffinityNp(attr: ConstPtr, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetaffinityNp(attr: MutPtr, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: PriorityWhichT, who: IdT): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetpriority(which.toInt(), who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: PriorityWhichT, who: IdT, prio: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSetpriority(which.toInt(), who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetkindNp(attr: ConstPtr, value: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetkindNp(attr: MutPtr, value: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr.rawAddress.toString(), value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigqueue(thread: io.github.kotlinmania.rustlang.libc.PthreadT, sig: CInt, value: Sigval): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.libc.PthreadT, retval: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread.toString(), retval.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.libc.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread.toString(), retval.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo(): Mallinfo =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo2(): Mallinfo2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocStats() {
    if (unixLinuxlikeLinuxGnuNapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuMallocStats()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMallocInfo(options, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwentR(pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrentR(grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetgrentR(grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpwentR(stream: MutPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetgrentR(stream: MutPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetentR(resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbynameR(name: ConstPtr, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name.rawAddress.toString(), resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddrR(net: UInt, type: CInt, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net.toInt(), type, resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putpwent(p: ConstPtr, stream: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPutpwent(p.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putgrent(grp: ConstPtr, stream: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPutgrent(grp.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuSethostid(hostid.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMemfdCreate(name.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMlock2(addr.rawAddress.toString(), len.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuEaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuAsctimeR(tm.rawAddress.toString(), buf.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(timep: ConstPtr, buf: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuCtimeR(timep.rawAddress.toString(), buf.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixBasename(path: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuPosixBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuBasename(path: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGnuBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlmopen(lmid: LmidT, filename: ConstPtr, flag: CInt): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuDlmopen(lmid.toString(), filename.rawAddress.toString(), flag).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlinfo(handle: MutPtr, request: CInt, info: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuDlinfo(handle.rawAddress.toString(), request, info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dladdr1(addr: ConstPtr, info: MutPtr, extraInfo: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuDladdr1(addr.rawAddress.toString(), info.rawAddress.toString(), extraInfo.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlvsym(handle: MutPtr, symbol: ConstPtr, version: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuDlvsym(handle.rawAddress.toString(), symbol.rawAddress.toString(), version.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocTrim(pad: SizeT): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMallocTrim(pad.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcRelease(): ConstPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) ConstPtr(wasmLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcVersion(): ConstPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) ConstPtr(wasmLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclosefromNp(actions: MutPtr, from: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions.rawAddress.toString(), from) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddtcsetpgrpNp(actions: MutPtr, tcfd: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions.rawAddress.toString(), tcfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntentR(stream: MutPtr, mntbuf: MutPtr, buf: MutPtr, buflen: CInt): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuGetmntentR(stream.rawAddress.toString(), mntbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execveat(dirfd: CInt, pathname: ConstPtr, argv: ConstPtr, envp: ConstPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuExecveat(dirfd, pathname.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeRange(first: CUint, last: CUint, flags: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuCloseRange(first.toInt(), last.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqNotify(mqdes: MqdT, sevp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes, sevp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait2(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd, events.rawAddress.toString(), maxevents, timeout.rawAddress.toString(), sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mempcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (unixLinuxlikeLinuxGnuNapiAvailable) MutPtr(wasmLibcUnixLinuxlikeLinuxGnuMempcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    if (unixLinuxlikeLinuxGnuNapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuTgkill(tgid, tid, sig) else throw UnsupportedOperationException("Native FFI addon is not available")

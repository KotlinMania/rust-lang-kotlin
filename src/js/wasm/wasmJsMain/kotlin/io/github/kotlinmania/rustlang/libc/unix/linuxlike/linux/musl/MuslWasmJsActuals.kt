@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeLinuxMuslNapiAddon(): Boolean

private val libcUnixLinuxlikeLinuxMuslNapiAvailable: Boolean = initLibcUnixLinuxlikeLinuxMuslNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPrlimit(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPtrace(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPtrace(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(p0: Int, p1: Int, p2: String, p3: Int, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPreadv2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPwritev2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetauxval(p0: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslRenameat2(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslReallocarray(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslCtermid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslCtermid(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslMlock2(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslMlock2(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslEaccess(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslEaccess(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslDirname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslBasename(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslBasename(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetrandom(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxid(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxline(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPututxline(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPututxline(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslSetutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslSetutxent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslEndutxent()")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslEndutxent()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(p0: String, p1: String, p2: String): Int

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(resource, rlim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPrlimit(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPtrace(request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: IdT): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which, who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which, who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: CUlonglong, dirfd: CInt, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(fd, flags.toInt(), mask.toString(), dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(s.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtimex(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(clkId.toInt(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctermid(s: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslCtermid(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(name.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslMlock2(addr.rawAddress.toString(), len.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslEaccess(pathname.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(tm.rawAddress.toString(), buf.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxid(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslGetutxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeLinuxMuslPututxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxMuslSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeLinuxMuslEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(thread.rawAddress.toString(), retval.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAvailable) wasmRustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(thread.rawAddress.toString(), retval.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcUnixLinuxlikeLinuxMuslNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPtrace(request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: IdT): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which, who.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which, who.toInt(), prio) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: CUlonglong, dirfd: CInt, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(fd, flags.toInt(), mask.toString(), dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) {
        libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(s.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtimex(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(clkId.toInt(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctermid(s: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslCtermid(s.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(name.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslMlock2(addr.rawAddress.toString(), len.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(pathname.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslEaccess(pathname.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(tm.rawAddress.toString(), buf.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslDirname(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslBasename(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetutxent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPututxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) {
        libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) {
        libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(thread.rawAddress.toString(), retval.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxMuslNapiAddon != null) (libcUnixLinuxlikeLinuxMuslNapiAddon.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(thread.rawAddress.toString(), retval.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

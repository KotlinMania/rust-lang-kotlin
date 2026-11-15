@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcUnixLinuxlikeLinuxGnuNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual var ENVIRON: MutPtr
    get() = if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
            libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(value.rawAddress.toString())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual fun fgetspentR(fp: MutPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(fp.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sgetspentR(s: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(s.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getspentR(spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, compar: QsortRComparCallback?, arg: MutPtr) {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuQsortR(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags, timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource.toInt(), rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource.toInt(), rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(resource.toInt(), rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(resource.toInt(), rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit64(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(pid, resource.toInt(), newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetutxent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPututxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpt(): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetpt() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallopt(param: CInt, value: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMallopt(param, value) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(type.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtimex(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId.toInt(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: ULong, dirfd: CInt, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd, flags.toInt(), mask.toString(), dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(s.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctermid(s: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuCtermid(s.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(buf.rawAddress.toString(), sz) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(buffer: ConstPtr, len: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer.rawAddress.toString(), len) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(buffer: ConstPtr, len: CInt, fd: CInt) {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer.rawAddress.toString(), len, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun glob64(pattern: ConstPtr, flags: CInt, errfunc: Glob64ErrfuncCallback?, pglob: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGlob64(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree64(pglob: MutPtr) {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ptrace(request: CUint, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPtrace(request.toInt(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetaffinityNp(attr: ConstPtr, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetaffinityNp(attr: MutPtr, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(which.toInt(), who.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(which.toInt(), who.toInt(), prio) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetkindNp(attr: ConstPtr, value: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr.rawAddress.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetkindNp(attr: MutPtr, value: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr.rawAddress.toString(), value) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigqueue(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt, value: Sigval): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread.toString(), retval.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread.toString(), retval.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo2(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocStats() {
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMallocStats()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(options, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwentR(pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrentR(grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpwentR(stream: MutPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetgrentR(stream: MutPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetentR(resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbynameR(name: ConstPtr, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name.rawAddress.toString(), resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddrR(net: UInt, type: CInt, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net.toInt(), type, resultBuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString(), hErrnop.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putpwent(p: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p.rawAddress.toString(), stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putgrent(grp: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(grp.rawAddress.toString(), stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuSethostid(hostid.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(name.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMlock2(addr.rawAddress.toString(), len.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuEaccess(pathname.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(tm.rawAddress.toString(), buf.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(timep: ConstPtr, buf: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(timep.rawAddress.toString(), buf.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuDirname(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixBasename(path: MutPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuBasename(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlmopen(lmid: LmidT, filename: ConstPtr, flag: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(lmid.toString(), filename.rawAddress.toString(), flag) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlinfo(handle: MutPtr, request: CInt, info: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(handle.rawAddress.toString(), request, info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dladdr1(addr: ConstPtr, info: MutPtr, extraInfo: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(addr.rawAddress.toString(), info.rawAddress.toString(), extraInfo.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlvsym(handle: MutPtr, symbol: ConstPtr, version: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(handle.rawAddress.toString(), symbol.rawAddress.toString(), version.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocTrim(pad: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(pad.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcRelease(): ConstPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) ConstPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gnuGetLibcVersion(): ConstPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) ConstPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclosefromNp(actions: MutPtr, from: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions.rawAddress.toString(), from) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddtcsetpgrpNp(actions: MutPtr, tcfd: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions.rawAddress.toString(), tcfd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntentR(stream: MutPtr, mntbuf: MutPtr, buf: MutPtr, buflen: CInt): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(stream.rawAddress.toString(), mntbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execveat(dirfd: CInt, pathname: ConstPtr, argv: ConstPtr, envp: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuExecveat(dirfd, pathname.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeRange(first: CUint, last: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(first.toInt(), last.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqNotify(mqdes: MqdT, sevp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes, sevp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait2(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd, events.rawAddress.toString(), maxevents, timeout.rawAddress.toString(), sigmask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mempcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) MutPtr((libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    if (libcUnixLinuxlikeLinuxGnuNapiAddon != null) (libcUnixLinuxlikeLinuxGnuNapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuTgkill(tgid, tid, sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

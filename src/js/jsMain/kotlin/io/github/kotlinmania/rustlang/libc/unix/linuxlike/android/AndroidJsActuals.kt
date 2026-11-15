@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcUnixLinuxlikeAndroidNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun setpwent() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endpwent() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEndpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpwent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetpwent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endgrent() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEndgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit64(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetrlimit64(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetrlimit64(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPrlimit(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPrlimit64(pid, resource, newLimit.rawAddress.toString(), oldLimit.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMlock2(addr.rawAddress.toString(), len.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMadvise(addr.rawAddress.toString(), len.toString(), advice) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMsync(addr.rawAddress.toString(), len.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMprotect(addr.rawAddress.toString(), len.toString(), prot) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SizeT, serv: MutPtr, servlen: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toString(), serv.rawAddress.toString(), servlen.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPreadv(fd, iov.rawAddress.toString(), count, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPwritev(fd, iov.rawAddress.toString(), count, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(pid, localIov.rawAddress.toString(), localIovCount.toString(), remoteIov.rawAddress.toString(), remoteIovCount.toString(), flags.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(pid, localIov.rawAddress.toString(), localIovCount.toString(), remoteIov.rawAddress.toString(), remoteIovCount.toString(), flags.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPtrace(request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: IdT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetpriority(which, who.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetpriority(which, who.toInt(), prio) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedCpualloc(count: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(count.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedCpufree(set: MutPtr) {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedCpufree(set.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun schedCpucount(setsize: SizeT, set: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedCpucount(setsize.toString(), set.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetcpu(): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetcpu() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMallocInfo(options, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: ConstPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utmpname(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidUtmpname(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutent() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetutent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getutent(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetutent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTelldir(dirp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFallocate(fd, mode, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFallocate64(fd, mode, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidLgetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidLsetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidLlistxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFlistxattr(filedes, list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRemovexattr(path.rawAddress.toString(), name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidLremovexattr(path.rawAddress.toString(), name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFremovexattr(filedes, name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSignalfd(fd, mask.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdCreate(clock: ClockidT, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdGettime(fd: CInt, currentValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(fd, currentValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(fd, flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerCreate(clockid.toInt(), sevp.rawAddress.toString(), timerid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerDelete(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerDelete(timerid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(timerid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerGettime(timerid.rawAddress.toString(), currValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTimerSettime(timerid.rawAddress.toString(), flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSyscall(num.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate(size: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEpollCreate(size) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate1(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEpollWait(epfd, events.rawAddress.toString(), maxevents, timeout) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEpollCtl(epfd, op, fd, event.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unshare(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidUnshare(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount(target: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidUmount(target.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTee(fdIn, fdOut, len.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSplice(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEventfd(initval.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEventfdRead(fd, value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd, value.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(pid, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSemTimedwait(sem.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSemGetvalue(sem.rawAddress.toString(), sval.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedSetparam(pid, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetns(fd, nstype) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapoff(puath: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSwapoff(puath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidVmsplice(fd, iov.rawAddress.toString(), nrSegs.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMount(src.rawAddress.toString(), target.rawAddress.toString(), fstype.rawAddress.toString(), flags.toString(), data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun personality(persona: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPersonality(persona.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPrctl(option, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetparam(pid, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPpoll(fds.rawAddress.toString(), nfds.toInt(), timeout.rawAddress.toString(), sigmask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidClone(0L.toString(), childStack.rawAddress.toString(), flags, arg.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetscheduler(pid: PidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidClockNanosleep(clkId.toInt(), flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSethostname(name.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysinfo(info: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSysinfo(info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidUmount2(target.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSwapon(path.rawAddress.toString(), swapflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(pid, policy, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSendfile(outFd, inFd, offset.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSendfile64(outFd, inFd, offset.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsgid(gid: GidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetfsgid(gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsuid(uid: UidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSetfsuid(uid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSigsuspend(mask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrgidR(gid, grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semClose(sem: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSemClose(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrnamR(name.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSemOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrnam(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semUnlink(name: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSemUnlink(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidDaemon(nochdir, noclose) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSigtimedwait(set.rawAddress.toString(), info.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSigwait(set.rawAddress.toString(), sig.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgid(gid: GidT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrgid(gid) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetgrouplist(user.rawAddress.toString(), group, groups.rawAddress.toString(), ngroups.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidInitgroups(user.rawAddress.toString(), group) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPopen(command.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun errno(): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidErrno() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyRmWatch(fd: CInt, wd: UInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd, wd.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit(): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidInotifyInit() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit1(flags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(fd, path.rawAddress.toString(), mask.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRegcomp(preg.rawAddress.toString(), pattern.rawAddress.toString(), cflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRegexec(preg.rawAddress.toString(), input.rawAddress.toString(), nmatch.toString(), pmatch.rawAddress.toString(), eflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRegerror(errcode, preg.rawAddress.toString(), errbuf.rawAddress.toString(), errbufSize.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regfree(preg: MutPtr) {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRegfree(preg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun androidSetAbortMessage(msg: ConstPtr) {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(msg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun gettid(): PidT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGettid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidTgkill(tgid, tid, sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getauxval(type: CUlong): CUlong =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetauxval(type.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetrandom(buf.rawAddress.toString(), buflen.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetentropy(buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertySet(name: ConstPtr, value: ConstPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(name.rawAddress.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyGet(name: ConstPtr, value: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(name.rawAddress.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyFind(name: ConstPtr): ConstPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) ConstPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyFindNth(n: CUint): ConstPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) ConstPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(n.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun systemPropertyForeach(callback: SystemPropertyForeachCallbackCallback, cookie: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(0L.toString(), cookie.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(0L.toString(), data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4random(): UInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidArc4random() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomUniform(upperBound: UInt): UInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(buf: MutPtr, n: SizeT) {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(buf.rawAddress.toString(), n.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidReallocarray(ptr.rawAddress.toString(), nmemb.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidDirname(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: ConstPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidBasename(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidGetoptLong(argc, argv.rawAddress.toString(), optstring.rawAddress.toString(), longopts.rawAddress.toString(), longindex.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (libcUnixLinuxlikeAndroidNapiAddon != null) {
        libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun syncfs(fd: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidSyncfs(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwriteUnlocked(buf: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflushUnlocked(stream: MutPtr): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetsUnlocked(buf: MutPtr, size: CInt, stream: MutPtr): MutPtr =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) MutPtr((libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(buf.rawAddress.toString(), size, stream.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidKlogctl(syslogType, bufp.rawAddress.toString(), len) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidMemfdCreate(name.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidRenameat2(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPreadv2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeAndroidNapiAddon != null) (libcUnixLinuxlikeAndroidNapiAddon.rustlangLibcUnixLinuxlikeAndroidPwritev2(fd, iov.rawAddress.toString(), iovcnt, offset.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

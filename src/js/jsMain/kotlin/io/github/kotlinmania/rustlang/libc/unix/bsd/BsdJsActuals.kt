@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcUnixBsdNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetrlimit(resource, rlim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdAbs(i) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdLabs(i.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRand() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getifaddrs(ifap: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetifaddrs(ifap.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeifaddrs(ifa: MutPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdFreeifaddrs(ifa.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setgroups(ngroups: CInt, ptr: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetgroups(ngroups, ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlogin(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetlogin(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctl(fd: CInt, request: CUlong, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdIoctl(fd, request.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kqueue(): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdKqueue() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unmount(target: ConstPtr, arg: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdUnmount(target.rawAddress.toString(), arg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwent(): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetpwent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpwent() {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endpwent() {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdEndpwent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endgrent() {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdEndgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrent(): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetgrent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getprogname(): ConstPtr =
    if (libcUnixBsdNapiAddon != null) ConstPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetprogname() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setprogname(name: ConstPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetprogname(name.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetloadavg(loadavg.rawAddress.toString(), nelem) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNameindex(): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdIfNameindex() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifFreenameindex(ptr: MutPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdIfFreenameindex(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpeereid(socket: CInt, euid: MutPtr, egid: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetpeereid(socket, euid.rawAddress.toString(), egid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGlob(pattern.rawAddress.toString(), flags, 0L.toString(), pglob.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun globfree(pglob: MutPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdGlobfree(pglob.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPosixMadvise(addr.rawAddress.toString(), len.toString(), advice) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmUnlink(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdShmUnlink(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdTelldir(dirp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdMadvise(addr.rawAddress.toString(), len.toString(), advice) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minherit(addr: MutPtr, len: SizeT, inherit: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdMinherit(addr.rawAddress.toString(), len.toString(), inherit) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdMsync(addr.rawAddress.toString(), len.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRecvfrom(socket, buf.rawAddress.toString(), len.toString(), flags, addr.rawAddress.toString(), addrlen.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdMkstemps(template.rawAddress.toString(), suffixlen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdFutimes(fd, times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfo(item: NlItem): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdNlLanginfo(item) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdBind(socket, address.rawAddress.toString(), addressLen.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdWritev(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdReadv(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSendmsg(fd, msg.rawAddress.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRecvmsg(fd, msg.rawAddress.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetgrgidR(gid, grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSigsuspend(mask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semClose(sem: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSemClose(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdtablesize(): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetdtablesize() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetgrnamR(name.rawAddress.toString(), grp.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPthreadSigmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdSemOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetgrnam(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCancel(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPthreadCancel(thread.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKill(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPthreadKill(thread.toString(), sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSchedGetPriorityMin(policy) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSchedGetPriorityMax(policy) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semUnlink(name: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSemUnlink(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetpwnamR(name.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetpwuidR(uid, pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSigwait(set.rawAddress.toString(), sig.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPthreadAtfork(0L.toString(), 0L.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrgid(gid: GidT): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetgrgid(gid) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdPopen(command.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acct(filename: ConstPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdAcct(filename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdWait4(pid, status.rawAddress.toString(), options, rusage.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetitimer(which, currValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSetitimer(which, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRegcomp(preg.rawAddress.toString(), pattern.rawAddress.toString(), cflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRegexec(preg.rawAddress.toString(), input.rawAddress.toString(), nmatch.toString(), pmatch.rawAddress.toString(), eflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdRegerror(errcode, preg.rawAddress.toString(), errbuf.rawAddress.toString(), errbufSize.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun regfree(preg: MutPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdRegfree(preg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4random(): UInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdArc4random() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(buf: MutPtr, size: SizeT) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdArc4randomBuf(buf.rawAddress.toString(), size.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4randomUniform(l: UInt): UInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdArc4randomUniform(l.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun drand48(): CDouble =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdDrand48() as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erand48(xseed: MutPtr): CDouble =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdErand48(xseed.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lrand48(): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdLrand48() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nrand48(xseed: MutPtr): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdNrand48(xseed.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mrand48(): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdMrand48() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun jrand48(xseed: MutPtr): CLong =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdJrand48(xseed.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand48(seed: CLong) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdSrand48(seed.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun seed48(xseed: MutPtr): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdSeed48(xseed.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lcong48(p: MutPtr) {
    if (libcUnixBsdNapiAddon != null) {
        libcUnixBsdNapiAddon.rustlangLibcUnixBsdLcong48(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdGetoptLong(argc, argv.rawAddress.toString(), optstring.rawAddress.toString(), longopts.rawAddress.toString(), longindex.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr): SizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdStrftime(buf.rawAddress.toString(), maxsize.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftimeL(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr, locale: LocaleT): SizeT =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdStrftimeL(buf.rawAddress.toString(), maxsize.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString(), locale.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun devname(dev: DevT, modeT: ModeT): MutPtr =
    if (libcUnixBsdNapiAddon != null) MutPtr((libcUnixBsdNapiAddon.rustlangLibcUnixBsdDevname(dev, modeT.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun issetugid(): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdIssetugid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdNapiAddon != null) (libcUnixBsdNapiAddon.rustlangLibcUnixBsdSyscall(num, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdtypeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

private val libcUnixLinuxlikeNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun ioctl(fd: CInt, request: Ioctl, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeIoctl(fd, request.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun libcCurrentSigrtmax(): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun libcCurrentSigrtmin(): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semDestroy(sem: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSemDestroy(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semInit(sem: MutPtr, pshared: CInt, value: CUint): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSemInit(sem.rawAddress.toString(), pshared, value.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdatasync(fd: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFdatasync(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mincore(addr: MutPtr, len: SizeT, vec: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMincore(addr.rawAddress.toString(), len.toString(), vec.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeClockGetres(clkId, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeClockGettime(clkId, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockSettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeClockSettime(clkId, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetcpuclockid(pid: PidT, clkId: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeClockGetcpuclockid(pid, clkId.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetitimer(which, currValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSetitimer(which, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeDirfd(dirp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memalign(align: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMemalign(align.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgroups(ngroups: SizeT, ptr: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSetgroups(ngroups.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe2(fds: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePipe2(fds.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStatfs(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFstatfs(fd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMemrchr(cx.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePosixFadvise(fd, offset.toString(), len.toString(), advise) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFutimens(fd, times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeDuplocale(base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: LocaleT) {
    if (libcUnixLinuxlikeNapiAddon != null) {
        libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFreelocale(loc.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeUselocale(loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMknodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), dev) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptsnameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePtsnameR(fd, buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearenv(): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeClearenv() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeWaitid(idtype.toInt(), id.toInt(), infop.rawAddress.toString(), options) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getresuid(ruid: MutPtr, euid: MutPtr, suid: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetresuid(ruid.rawAddress.toString(), euid.rawAddress.toString(), suid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getresgid(rgid: MutPtr, egid: MutPtr, sgid: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetresgid(rgid.rawAddress.toString(), egid.rawAddress.toString(), sgid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acct(filename: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeAcct(filename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun brk(addr: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeBrk(addr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: IntptrT): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSbrk(increment.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfork(): PidT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeVfork() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setresgid(rgid: GidT, egid: GidT, sgid: GidT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSetresgid(rgid, egid, sgid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setresuid(ruid: UidT, euid: UidT, suid: UidT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSetresuid(ruid, euid, suid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeWait4(pid, status.rawAddress.toString(), options, rusage.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loginTty(fd: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeLoginTty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvpe(file: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeExecvpe(file.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fexecve(fd: CInt, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFexecve(fd, argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getifaddrs(ifap: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetifaddrs(ifap.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeifaddrs(ifa: MutPtr) {
    if (libcUnixLinuxlikeNapiAddon != null) {
        libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFreeifaddrs(ifa.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: SocklenT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeBind(socket, address.rawAddress.toString(), addressLen.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeWritev(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeReadv(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSendmsg(fd, msg.rawAddress.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeRecvmsg(fd, msg.rawAddress.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uname(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeUname(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchrnul(s: ConstPtr, c: CInt): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStrchrnul(s.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr): SizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStrftime(s.rawAddress.toString(), max.toString(), format.rawAddress.toString(), tm.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftimeL(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr, locale: LocaleT): SizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStrftimeL(s.rawAddress.toString(), max.toString(), format.rawAddress.toString(), tm.rawAddress.toString(), locale.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strptime(s: ConstPtr, format: ConstPtr, tm: MutPtr): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStrptime(s.rawAddress.toString(), format.rawAddress.toString(), tm.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkostemp(template: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMkostemp(template.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkostemps(template: MutPtr, suffixlen: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMkostemps(template.rawAddress.toString(), suffixlen, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdomainname(name: MutPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetdomainname(name.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setdomainname(name: ConstPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeSetdomainname(name.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNameindex(): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeIfNameindex() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifFreenameindex(ptr: MutPtr) {
    if (libcUnixLinuxlikeNapiAddon != null) {
        libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeIfFreenameindex(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetpwnamR(name.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeGetpwuidR(uid, pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs64(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFstatfs64(fd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStatvfs64(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFstatvfs64(fd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStatfs64(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat64(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeCreat64(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat64(fildes: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFstat64(fildes, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat64(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFstatat64(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate64(fd: CInt, length: Off64T): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeFtruncate64(fd, length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek64(fd: CInt, offset: Off64T, whence: CInt): Off64T =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeLseek64(fd, offset.toString(), whence) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeLstat64(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mmap64(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: Off64T): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeMmap64(addr.rawAddress.toString(), len.toString(), prot, flags, fd, offset.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open64(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeOpen64(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat64(fd: CInt, path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeOpenat64(fd, path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise64(fd: CInt, offset: Off64T, len: Off64T, advise: CInt): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePosixFadvise64(fd, offset.toString(), len.toString(), advise) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread64(fd: CInt, buf: MutPtr, count: SizeT, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePread64(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite64(fd: CInt, buf: ConstPtr, count: SizeT, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePwrite64(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir64(dirp: MutPtr): MutPtr =
    if (libcUnixLinuxlikeNapiAddon != null) MutPtr((libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeReaddir64(dirp.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir64R(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeReaddir64R(dirp.rawAddress.toString(), entry.rawAddress.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStat64(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate64(path: ConstPtr, length: Off64T): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeTruncate64(path.rawAddress.toString(), length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePreadv64(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikePwritev64(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): PidT =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeForkpty(amaster.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeOpenpty(amaster.rawAddress.toString(), aslave.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statx(dirfd: CInt, pathname: ConstPtr, flags: CInt, mask: CUint, statxbuf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAddon != null) (libcUnixLinuxlikeNapiAddon.rustlangLibcUnixLinuxlikeStatx(dirfd, pathname.rawAddress.toString(), flags, mask.toInt(), statxbuf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

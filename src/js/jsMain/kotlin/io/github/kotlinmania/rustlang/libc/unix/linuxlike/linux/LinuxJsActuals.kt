@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.ClockidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.KeyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT

private val unixLinuxlikeLinuxNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetspnamR(name.rawAddress.toString(), spbuf.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), spbufp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqClose(mqd: MqdT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqClose(mqd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqUnlink(name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqUnlink(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqReceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqTimedreceive(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.rawAddress.toString(), absTimeout.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqSend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqTimedsend(mqd, msgPtr.rawAddress.toString(), msgLen.toString(), msgPrio.toInt(), absTimeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqGetattr(mqd, attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMqSetattr(mqd, newattr.rawAddress.toString(), oldattr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mrand48(): CLong =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMrand48() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seed48(xseed: MutPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSeed48(xseed.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lcong48(p: MutPtr) {
    if (unixLinuxlikeLinuxNapiAddon != null) {
        unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLcong48(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLutimes(file.rawAddress.toString(), times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxShmOpen(name.rawAddress.toString(), oflag, mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmUnlink(name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxShmUnlink(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFtok(pathname.rawAddress.toString(), projId) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSemget(key, nsems, semflag) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSemop(semid, sops.rawAddress.toString(), nsops.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMsgctl(msqid, cmd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMsgget(key, msgflg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMsgrcv(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgtyp.toString(), msgflg) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMsgsnd(msqid, msgp.rawAddress.toString(), msgsz.toString(), msgflg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFallocate(fd, mode, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixFallocate(fd, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxReadahead(fd, offset.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLgetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLsetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLlistxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFlistxattr(filedes, list.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxRemovexattr(path.rawAddress.toString(), name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxLremovexattr(path.rawAddress.toString(), name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFremovexattr(filedes, name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSignalfd(fd, mask.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxTimerfdCreate(clockid, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxTimerfdGettime(fd, currValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxTimerfdSettime(fd, flags, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxQuotactl(cmd, special.rawAddress.toString(), id, data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEpollPwait(epfd, events.rawAddress.toString(), maxevents, timeout, sigmask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSigtimedwait(set.rawAddress.toString(), info.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSigwaitinfo(set.rawAddress.toString(), info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxAccept4(fd, addr.rawAddress.toString(), len.rawAddress.toString(), flg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun reboot(howTo: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxReboot(howTo) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsgid(gid: GidT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSetfsgid(gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setfsuid(uid: UidT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSetfsuid(uid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSyncFileRange(fd, offset.toString(), nbytes.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixMadvise(addr.rawAddress.toString(), len.toString(), advice) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMremap(addr.rawAddress.toString(), len.toString(), newLen.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxRemapFilePages(addr.rawAddress.toString(), size.toString(), prot, pgoff.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxMkstemps(template.rawAddress.toString(), suffixlen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vhangup(): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxVhangup() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sync() {
    if (unixLinuxlikeLinuxNapiAddon != null) {
        unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSync()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun syncfs(fd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSyncfs(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSyscall(num.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedSetaffinity(pid, cpusetsize.toString(), cpuset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate(size: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEpollCreate(size) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCreate1(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEpollCreate1(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEpollWait(epfd, events.rawAddress.toString(), maxevents, timeout) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEpollCtl(epfd, op, fd, event.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unshare(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxUnshare(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount(target: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxUmount(target.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxTee(fdIn, fdOut, len.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSplice(fdIn, offIn.rawAddress.toString(), fdOut, offOut.rawAddress.toString(), len.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEventfd(initval.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEventfdRead(fd, value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEventfdWrite(fd, value.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedRrGetInterval(pid, tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedSetparam(pid, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSetns(fd, nstype) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapoff(path: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSwapoff(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxVmsplice(fd, iov.rawAddress.toString(), nrSegs.toString(), flags.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun personality(persona: CUlong): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPersonality(persona.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedGetparam(pid, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxClone(0L.toString(), childStack.rawAddress.toString(), flags, arg.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedGetscheduler(pid: PidT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedGetscheduler(pid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxClockNanosleep(clkId, flags, rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxUmount2(target.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSwapon(path.rawAddress.toString(), swapflags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSchedSetscheduler(pid, policy, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSendfile(outFd, inFd, offset.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSigaltstack(ss.rawAddress.toString(), oss.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdtablesize(): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetdtablesize() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetgrouplist(user.rawAddress.toString(), group, groups.rawAddress.toString(), ngroups.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr.rawAddress.toString(), flags.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFreadUnlocked(buf.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit(): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxInotifyInit() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyInit1(flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxInotifyInit1(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxInotifyAddWatch(fd, path.rawAddress.toString(), mask.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFanotifyInit(flags.toInt(), eventFFlags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGethostid() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxKlogctl(syslogType, bufp.rawAddress.toString(), len) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxNameToHandleAt(dirfd, path.rawAddress.toString(), handle.rawAddress.toString(), mountId.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxOpenByHandleAt(mountFd, handle.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetnetbyname(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetnetbyaddr(net.toInt(), type) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnetent(): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxGetnetent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setnetent(stayopen: CInt) {
    if (unixLinuxlikeLinuxNapiAddon != null) {
        unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSetnetent(stayopen)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endnetent() {
    if (unixLinuxlikeLinuxNapiAddon != null) {
        unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxEndnetent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFallocate64(fd, mode, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFgetpos64(stream.rawAddress.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxFopen64(filename.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxPosixFallocate64(fd, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    if (unixLinuxlikeLinuxNapiAddon != null) (unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxSendfile64(outFd, inFd, offset.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile64(): MutPtr =
    if (unixLinuxlikeLinuxNapiAddon != null) MutPtr((unixLinuxlikeLinuxNapiAddon.libcUnixLinuxlikeLinuxTmpfile64() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

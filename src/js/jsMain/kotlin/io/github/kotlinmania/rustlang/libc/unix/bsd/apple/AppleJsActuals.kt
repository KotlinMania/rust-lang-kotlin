@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.b64.aarch64.BooleanT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

private val libcUnixBsdAppleNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT
    get() = if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetMACHTASKSELF() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcUnixBsdAppleNapiAddon != null) {
            libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetMACHTASKSELF(value.toInt())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual val VM_PAGE_SIZE: VmSizeT
    get() = if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetVMPAGESIZE() as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun aioRead(aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioRead(aiocbp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioWrite(aiocbp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioFsync(op, aiocbp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioError(aiocbp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioReturn(aiocbp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioSuspend(aiocbList.rawAddress.toString(), nitems, timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAioCancel(fd, aiocbp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleChflags(path.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFchflags(fd, flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLchflags(path.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLchmod(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleClockGetres(clkId.toInt(), tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleClockGettime(clkId.toInt(), tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLioListio(mode, aiocbList.rawAddress.toString(), nitems, sevp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDirfd(dirp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLutimes(file.rawAddress.toString(), times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetutxent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetutxid(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetutxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePututxline(ut.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleUtmpxname(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctime(tm: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAsctime(tm.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctime(clock: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCtime(clock.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdate(datestr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetdate(datestr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleStrptime(buf.rawAddress.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleAsctimeR(tm.rawAddress.toString(), result.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCtimeR(clock.rawAddress.toString(), result.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toInt(), serv.rawAddress.toString(), servlen.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMincore(addr.rawAddress.toString(), len.toString(), vec.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSysctlnametomib(name.rawAddress.toString(), mibp.rawAddress.toString(), sizep.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMprotect(addr.rawAddress.toString(), len.toString(), prot) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSemget(key, nsems, semflg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSemop(semid, sops.rawAddress.toString(), nsops.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleShmOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFtok(pathname.rawAddress.toString(), projId) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleShmat(shmid, shmaddr.rawAddress.toString(), shmflg) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleShmdt(shmaddr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleShmctl(shmid, cmd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleShmget(key, size.toString(), shmflg) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSysctl(name.rawAddress.toString(), namelen.toInt(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSysctlbyname(name.rawAddress.toString(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machAbsoluteTime(): ULong =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachAbsoluteTime() as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachTimebaseInfo(info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machHostSelf(): MachPortT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachHostSelf() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machThreadSelf(): MachPortT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachThreadSelf() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond.rawAddress.toString(), lock.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadAttrGetscope(attr.rawAddress.toString(), contentionscope.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadAttrGetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadAttrGetdetachstate(attr.rawAddress.toString(), detachstate.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadAttrSetscope(attr.rawAddress.toString(), contentionscope) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadAttrSetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadSetnameNp(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadGetnameNp(thread.toLong().toString(), name.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadMachThreadNp(thread.toLong().toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT): PthreadT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadFromMachThreadNp(port.toInt()) as String).toLong().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadGetStackaddrNp(thread.toLong().toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadGetStacksizeNp(thread.toLong().toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMainNp(): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadMainNp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadThreadidNp(thread.toLong().toString(), threadId.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteProtectNp(enabled: CInt) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.unix.bsd.apple.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(0L.toString(), ctx.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteFreezeCallbacksNp() {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSyncWaitOnAddress(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), deadline.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), timeoutNs.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(addr.rawAddress.toString(), size.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(addr.rawAddress.toString(), size.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsUnfairLockLock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsUnfairLockTrylock(lock.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsUnfairLockUnlock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsLogCreate(subsystem.rawAddress.toString(), category.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, tpe: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogTypeT): Boolean =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsLogTypeEnabled(oslog.rawAddress.toString(), tpe.toInt()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log.rawAddress.toString(), ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSignpostIdGenerate(log.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): Boolean =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOsSignpostEnabled(log.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleThreadPolicySet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleThreadPolicyGet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.rawAddress.toString(), getDefault.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleThreadInfo(targetAct.toInt(), flavor.toInt(), threadInfoOut.rawAddress.toString(), threadInfoOutCnt.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun error(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleError() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktrace(buf.rawAddress.toString(), sz) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktraceSymbols(addrs.rawAddress.toString(), sz) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktraceSymbolsFd(addrs.rawAddress.toString(), sz, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktraceFromFp(startfp.rawAddress.toString(), array.rawAddress.toString(), size) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktraceImageOffsets(array.rawAddress.toString(), imageOffsets.rawAddress.toString(), size)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBacktraceAsync(array.rawAddress.toString(), length.toString(), taskId.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleStatfs(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFstatfs(fd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleKevent(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleKevent64(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, flags.toInt(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMount(src.rawAddress.toString(), target.rawAddress.toString(), flags, data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFmount(src.rawAddress.toString(), fd, flags, data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePtrace(request, pid, addr.rawAddress.toString(), data) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleQuotactl(special.rawAddress.toString(), cmd, id, data.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSethostname(name.rawAddress.toString(), len) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSendfile(fd, s, offset.toString(), len.rawAddress.toString(), hdtr.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFutimens(fd, times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleOpenpty(amaster.rawAddress.toString(), aslave.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleForkpty(amaster.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loginTty(fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLoginTty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDuplocale(base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: LocaleT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFreelocale(loc.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconvL(loc: LocaleT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleLocaleconvL(loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleUselocale(loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    if (libcUnixBsdAppleNapiAddon != null) ConstPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleQuerylocale(mask, loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetpriority(which, who.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT, prio: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetpriority(which, who.toInt(), prio) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetdomainname(name.rawAddress.toString(), len) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetdomainname(name.rawAddress.toString(), len) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFlistxattr(filedes, list.rawAddress.toString(), size.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleRemovexattr(path.rawAddress.toString(), name.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleRenamexNp(from.rawAddress.toString(), to.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleRenameatxNp(fromfd, from.rawAddress.toString(), tofd, to.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFremovexattr(filedes, name.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetgrouplist(name.rawAddress.toString(), basegid, groups.rawAddress.toString(), ngroups.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleInitgroups(user.rawAddress.toString(), basegroup) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleWaitid(idtype.toInt(), id.toInt(), infop.rawAddress.toString(), options) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun brk(addr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBrk(addr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSbrk(increment) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldImageCount(): UInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDyldImageCount() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    if (libcUnixBsdAppleNapiAddon != null) ConstPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDyldGetImageHeader(imageIndex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    if (libcUnixBsdAppleNapiAddon != null) ConstPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDyldGetImageName(imageIndex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsInit(actions.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions.rawAddress.toString(), path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions.rawAddress.toString(), fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uname(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleUname(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleConnectx(socket, endpoints.rawAddress.toString(), associd.toInt(), flags.toInt(), iov.rawAddress.toString(), iovcnt.toInt(), len.rawAddress.toString(), connid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDisconnectx(socket, associd.toInt(), connid.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNtpAdjtime(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNtpGettime(buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetmntinfo(mntbufp.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetfsstat(mntbufp.rawAddress.toString(), bufsize, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleClonefile(src.rawAddress.toString(), dst.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleClonefileat(srcDirfd, src.rawAddress.toString(), dstDirfd, dst.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFclonefileat(srcfd, dstDirfd, dst.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCopyfile(from.rawAddress.toString(), to.rawAddress.toString(), state.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFcopyfile(from, to, state.rawAddress.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCopyfileStateFree(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateAlloc(): CopyfileStateT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCopyfileStateAlloc() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCopyfileStateGet(s.rawAddress.toString(), flags.toInt(), dst.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCopyfileStateSet(s.rawAddress.toString(), flags.toInt(), src.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.unix.bsd.apple.MachErrorT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachErrorString(errorValue) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMemsetS(s.rawAddress.toString(), smax.toString(), c, n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMemsetPattern4(b.rawAddress.toString(), pattern4.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMemsetPattern8(b.rawAddress.toString(), pattern8.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMemsetPattern16(b.rawAddress.toString(), pattern16.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleStrtonum(numstr.rawAddress.toString(), minval.toString(), maxval.toString(), errstrp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mstats(): Mstats =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneCheck(zone.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZonePrint(zone.rawAddress.toString(), verbose)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneStatistics(zone.rawAddress.toString(), stats.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneLog(zone.rawAddress.toString(), address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocDefaultZone(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocDefaultZone() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneFromPtr(ptr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneMalloc(zone.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneValloc(zone.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneCalloc(zone.rawAddress.toString(), numItems.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneRealloc(zone.rawAddress.toString(), ptr.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocZoneFree(zone.rawAddress.toString(), ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGethostuuid(id.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGethostid() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSethostid(hostid.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.CCRNGStatus =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleCCRandomGenerateBytes(bytes.rawAddress.toString(), size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetentropy(buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNsgetExecutablePath(buf.rawAddress.toString(), bufsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgv(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNsgetArgv() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgc(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNsgetArgc() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetEnviron(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNsgetEnviron() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetProgname(): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleNsgetProgname() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmSizeT, mask: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.unix.bsd.apple.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.unix.bsd.apple.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, maxProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, inheritance: io.github.kotlinmania.rustlang.unix.bsd.apple.VmInheritT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMachVmMap(targetTask.toInt(), address.rawAddress.toString(), size.toString(), mask.toString(), flags, obj.toInt(), offset.toString(), copy, curProtection, maxProtection, inheritance.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleVmAllocate(targetTask.toInt(), address.rawAddress.toString(), size.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleVmDeallocate(targetTask.toInt(), address.toString(), size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleHostStatistics64(hostPriv.toInt(), flavor, hostInfo64Out.rawAddress.toString(), hostInfo64OutCnt.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleHostProcessorInfo(host.toInt(), flavor, outProcessorCount.rawAddress.toString(), outProcessorInfo.rawAddress.toString(), outProcessorInfoCnt.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskForPid(host.toInt(), pid, task.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskInfo(host.toInt(), flavor.toInt(), taskInfoOut.rawAddress.toString(), taskInfoCount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, ledgers: io.github.kotlinmania.rustlang.unix.bsd.apple.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskCreate(targetTask.toInt(), ledgers.rawAddress.toString(), ledgersCnt.toInt(), inheritMemory, childTask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskTerminate(targetTask.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskThreads(targetTask.toInt(), actList.rawAddress.toString(), actListCnt.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleHostStatistics(hostPriv.toInt(), flavor, hostInfoOut.rawAddress.toString(), hostInfoOutCnt.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state.toInt(), path.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFgetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetattrlistat(fd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFsetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleSetattrlistat(dirFd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleGetattrlistbulk(dirfd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocSize(ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocGoodSize(size: SizeT): SizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMallocGoodSize(size.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleDirname(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleBasename(path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMknodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), dev) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleFreadlink(fd, buf.rawAddress.toString(), size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleExecvP(file.rawAddress.toString(), searchPath.rawAddress.toString(), argv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    if (libcUnixBsdAppleNapiAddon != null) {
        libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleQsortR(base.rawAddress.toString(), num.toString(), size.toString(), arg.rawAddress.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleClockSettime(clockId.toInt(), tp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcListpids(t.toInt(), typeinfo.toInt(), buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcListallpids(buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcListpgrppids(pgrpid, buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcListchildpids(ppid, buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcPidinfo(pid, flavor, arg.toString(), buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcPidfdinfo(pid, fd, flavor, buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcPidfileportinfo(pid, fileport.toInt(), flavor, buffer.rawAddress.toString(), buffersize) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcPidpath(pid, buffer.rawAddress.toString(), buffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcName(pid, buffer.rawAddress.toString(), buffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcRegionfilename(pid, address.toString(), buffer.rawAddress.toString(), buffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcKmsgbuf(buffer.rawAddress.toString(), buffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcLibversion(major.rawAddress.toString(), minor.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcPidRusage(pid, flavor, buffer.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetNoSmt(): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcSetNoSmt() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadNoSmt(): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcSetthreadNoSmt() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetCsm(flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcSetCsm(flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadCsm(flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleProcSetthreadCsm(flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, flavor: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleTaskSetInfo(targetTask.toInt(), flavor.toInt(), taskInfoIn.rawAddress.toString(), taskInfoInCnt.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    if (libcUnixBsdAppleNapiAddon != null) MutPtr((libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleIconvOpen(tocode.rawAddress.toString(), fromcode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleIconv(cd.rawAddress.toString(), inbuf.rawAddress.toString(), inbytesleft.rawAddress.toString(), outbuf.rawAddress.toString(), outbytesleft.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvClose(cd: IconvT): CInt =
    if (libcUnixBsdAppleNapiAddon != null) (libcUnixBsdAppleNapiAddon.rustlangLibcUnixBsdAppleIconvClose(cd.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

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

private object LibcUnixBsdAppleJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetMACHTASKSELF(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetMACHTASKSELF(value: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetVMPAGESIZE(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetgrent()

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioRead(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioWrite(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioFsync(op: Int, aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioError(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioReturn(aiocbp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioSuspend(aiocbList: Long, nitems: Int, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAioCancel(fd: Int, aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleChflags(path: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFchflags(fd: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLchflags(path: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLchmod(path: Long, mode: Short): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleClockGetres(clkId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleClockGettime(clkId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLioListio(mode: Int, aiocbList: Long, nitems: Int, sevp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDirfd(dirp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLutimes(file: Long, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGettimeofday(tp: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetutxent(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetutxid(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetutxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePututxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetutxent()

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleEndutxent()

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleUtmpxname(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAsctime(tm: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCtime(clock: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetdate(datestr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleStrptime(buf: Long, format: Long, timeptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleAsctimeR(tm: Long, result: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCtimeR(clock: Long, result: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetnameinfo(sa: Long, salen: Int, host: Long, hostlen: Int, serv: Long, servlen: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMincore(addr: Long, len: Long, vec: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSysctlnametomib(name: Long, mibp: Long, sizep: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMprotect(addr: Long, len: Long, prot: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSemget(key: Int, nsems: Int, semflg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSemctl(semid: Int, semnum: Int, cmd: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSemop(semid: Int, sops: Long, nsops: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleShmOpen(name: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFtok(pathname: Long, projId: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleShmat(shmid: Int, shmaddr: Long, shmflg: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleShmdt(shmaddr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleShmctl(shmid: Int, cmd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleShmget(key: Int, size: Long, shmflg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSysctl(name: Long, namelen: Int, oldp: Long, oldlenp: Long, newp: Long, newlen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSysctlbyname(name: Long, oldp: Long, oldlenp: Long, newp: Long, newlen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachAbsoluteTime(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachTimebaseInfo(info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachHostSelf(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachThreadSelf(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond: Long, lock: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadAttrGetscope(attr: Long, contentionscope: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadAttrGetstackaddr(attr: Long, stackaddr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadAttrGetdetachstate(attr: Long, detachstate: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadAttrSetscope(attr: Long, contentionscope: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadAttrSetstackaddr(attr: Long, stackaddr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadSetnameNp(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadGetnameNp(thread: Long, name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadMachThreadNp(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadFromMachThreadNp(port: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadGetStackaddrNp(thread: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadGetStacksizeNp(thread: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadMainNp(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadThreadidNp(thread: Long, threadId: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(callback: Long, ctx: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSyncWaitOnAddress(addr: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr: Long, value: Long, size: Long, flags: Int, clockid: Int, deadline: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr: Long, value: Long, size: Long, flags: Int, clockid: Int, timeoutNs: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(addr: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(addr: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsUnfairLockLock(lock: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsUnfairLockTrylock(lock: Long): Boolean

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsUnfairLockUnlock(lock: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(lock: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsLogCreate(subsystem: Long, category: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsLogTypeEnabled(oslog: Long, tpe: Byte): Boolean

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log: Long, ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSignpostIdGenerate(log: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOsSignpostEnabled(log: Long): Boolean

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleThreadPolicySet(thread: Int, flavor: Int, policyInfo: Long, count: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleThreadPolicyGet(thread: Int, flavor: Int, policyInfo: Long, count: Long, getDefault: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleThreadInfo(targetAct: Int, flavor: Int, threadInfoOut: Long, threadInfoOutCnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleError(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktrace(buf: Long, sz: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktraceSymbols(addrs: Long, sz: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktraceSymbolsFd(addrs: Long, sz: Int, fd: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktraceFromFp(startfp: Long, array: Long, size: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktraceImageOffsets(array: Long, imageOffsets: Long, size: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBacktraceAsync(array: Long, length: Long, taskId: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleStatfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFstatfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleKevent(kq: Int, changelist: Long, nchanges: Int, eventlist: Long, nevents: Int, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleKevent64(kq: Int, changelist: Long, nchanges: Int, eventlist: Long, nevents: Int, flags: Int, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMount(src: Long, target: Long, flags: Int, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFmount(src: Long, fd: Int, flags: Int, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePtrace(request: Int, pid: Int, addr: Long, data: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleQuotactl(special: Long, cmd: Int, id: Int, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSethostname(name: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSendfile(fd: Int, s: Int, offset: Long, len: Long, hdtr: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFutimens(fd: Int, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleUtimensat(dirfd: Int, path: Long, times: Long, flag: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleOpenpty(amaster: Long, aslave: Long, name: Long, termp: Long, winp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleForkpty(amaster: Long, name: Long, termp: Long, winp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLoginTty(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDuplocale(base: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFreelocale(loc: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleLocaleconvL(loc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNewlocale(mask: Int, locale: Long, base: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleUselocale(loc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleQuerylocale(mask: Int, loc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetpriority(which: Int, who: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetpriority(which: Int, who: Int, prio: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetdomainname(name: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetdomainname(name: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePreadv(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePwritev(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetxattr(path: Long, name: Long, value: Long, size: Long, position: Int, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFgetxattr(filedes: Int, name: Long, value: Long, size: Long, position: Int, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetxattr(path: Long, name: Long, value: Long, size: Long, position: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFsetxattr(filedes: Int, name: Long, value: Long, size: Long, position: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleListxattr(path: Long, list: Long, size: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFlistxattr(filedes: Int, list: Long, size: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleRemovexattr(path: Long, name: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleRenamexNp(from: Long, to: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleRenameatxNp(fromfd: Int, from: Long, tofd: Int, to: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFremovexattr(filedes: Int, name: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetgrouplist(name: Long, basegid: Int, groups: Long, ngroups: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleInitgroups(user: Long, basegroup: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleWaitid(idtype: Int, id: Int, infop: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBrk(addr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSbrk(increment: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSettimeofday(tv: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDyldImageCount(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDyldGetImageHeader(imageIndex: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDyldGetImageName(imageIndex: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawn(pid: Long, path: Long, fileActions: Long, attrp: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnp(pid: Long, file: Long, fileActions: Long, attrp: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetflags(attr: Long, flags: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetflags(attr: Long, flags: Short): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(attr: Long, flags: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(attr: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr: Long, count: Long, pref: Long, subpref: Long, ocount: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr: Long, count: Long, pref: Long, subpref: Long, ocount: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr: Long, count: Long, pref: Long, ocount: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr: Long, count: Long, pref: Long, ocount: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsInit(actions: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions: Long, fd: Int, path: Long, oflag: Int, mode: Short): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions: Long, fd: Int, newfd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions: Long, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions: Long, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleUname(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleConnectx(socket: Int, endpoints: Long, associd: Int, flags: Int, iov: Long, iovcnt: Int, len: Long, connid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDisconnectx(socket: Int, associd: Int, connid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNtpAdjtime(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNtpGettime(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetmntinfo(mntbufp: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetfsstat(mntbufp: Long, bufsize: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleClonefile(src: Long, dst: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleClonefileat(srcDirfd: Int, src: Long, dstDirfd: Int, dst: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFclonefileat(srcfd: Int, dstDirfd: Int, dst: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCopyfile(from: Long, to: Long, state: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFcopyfile(from: Int, to: Int, state: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCopyfileStateFree(s: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCopyfileStateAlloc(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCopyfileStateGet(s: Long, flags: Int, dst: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCopyfileStateSet(s: Long, flags: Int, src: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachErrorString(errorValue: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMemsetS(s: Long, smax: Long, c: Int, n: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMemsetPattern4(b: Long, pattern4: Long, len: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMemsetPattern8(b: Long, pattern8: Long, len: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMemsetPattern16(b: Long, pattern16: Long, len: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleStrtonum(numstr: Long, minval: Long, maxval: Long, errstrp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocPrintf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneCheck(zone: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZonePrint(zone: Long, verbose: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneStatistics(zone: Long, stats: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneLog(zone: Long, address: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocDefaultZone(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneFromPtr(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneMalloc(zone: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneValloc(zone: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneCalloc(zone: Long, numItems: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneRealloc(zone: Long, ptr: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocZoneFree(zone: Long, ptr: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGethostuuid(id: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGethostid(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSethostid(hostid: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleCCRandomGenerateBytes(bytes: Long, size: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetentropy(buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNsgetExecutablePath(buf: Long, bufsize: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNsgetArgv(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNsgetArgc(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNsgetEnviron(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleNsgetProgname(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMachVmMap(targetTask: Int, address: Long, size: Long, mask: Long, flags: Int, obj: Int, offset: Long, copy: Int, curProtection: Int, maxProtection: Int, inheritance: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleVmAllocate(targetTask: Int, address: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleVmDeallocate(targetTask: Int, address: Long, size: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleHostStatistics64(hostPriv: Int, flavor: Int, hostInfo64Out: Long, hostInfo64OutCnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleHostProcessorInfo(host: Int, flavor: Int, outProcessorCount: Long, outProcessorInfo: Long, outProcessorInfoCnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskForPid(host: Int, pid: Int, task: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskInfo(host: Int, flavor: Int, taskInfoOut: Long, taskInfoCount: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskCreate(targetTask: Int, ledgers: Long, ledgersCnt: Int, inheritMemory: Int, childTask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskTerminate(targetTask: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskThreads(targetTask: Int, actList: Long, actListCnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleHostStatistics(hostPriv: Int, flavor: Int, hostInfoOut: Long, hostInfoOutCnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetattrlist(path: Long, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFgetattrlist(fd: Int, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetattrlistat(fd: Int, path: Long, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetattrlist(path: Long, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFsetattrlist(fd: Int, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleSetattrlistat(dirFd: Int, path: Long, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleGetattrlistbulk(dirfd: Int, attrList: Long, attrBuf: Long, attrBufSize: Long, options: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocSize(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMallocGoodSize(size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleDirname(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleBasename(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMkfifoat(dirfd: Int, pathname: Long, mode: Short): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMknodat(dirfd: Int, pathname: Long, mode: Short, dev: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleFreadlink(fd: Int, buf: Long, size: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleExecvP(file: Long, searchPath: Long, argv: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleQsortR(base: Long, num: Long, size: Long, arg: Long, compar: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleClockSettime(clockId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcListpids(t: Int, typeinfo: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcListallpids(buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcListpgrppids(pgrpid: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcListchildpids(ppid: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcPidinfo(pid: Int, flavor: Int, arg: Long, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcPidfdinfo(pid: Int, fd: Int, flavor: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcPidfileportinfo(pid: Int, fileport: Int, flavor: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcPidpath(pid: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcName(pid: Int, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcRegionfilename(pid: Int, address: Long, buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcKmsgbuf(buffer: Long, buffersize: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcLibversion(major: Long, minor: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcPidRusage(pid: Int, flavor: Int, buffer: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcSetNoSmt(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcSetthreadNoSmt(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcSetCsm(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleProcSetthreadCsm(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleMemmem(haystack: Long, haystacklen: Long, needle: Long, needlelen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleTaskSetInfo(targetTask: Int, flavor: Int, taskInfoIn: Long, taskInfoInCnt: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleIconvOpen(tocode: Long, fromcode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleIconv(cd: Long, inbuf: Long, inbytesleft: Long, outbuf: Long, outbytesleft: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleIconvClose(cd: Long): Int
}

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT
    get() = LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetMACHTASKSELF().toUInt()
    set(value) {
        LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetMACHTASKSELF(value.toInt())
    }

public actual val VM_PAGE_SIZE: VmSizeT
    get() = LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetVMPAGESIZE().toULong()

public actual fun setgrent() {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetgrent()
}

public actual fun aioRead(aiocbp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioRead(aiocbp.rawAddress)

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioWrite(aiocbp.rawAddress)

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioFsync(op, aiocbp.rawAddress)

public actual fun aioError(aiocbp: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioError(aiocbp.rawAddress)

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioReturn(aiocbp.rawAddress)

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioSuspend(aiocbList.rawAddress, nitems, timeout.rawAddress)

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAioCancel(fd, aiocbp.rawAddress)

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleChflags(path.rawAddress, flags.toInt())

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFchflags(fd, flags.toInt())

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLchflags(path.rawAddress, flags.toInt())

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLchmod(path.rawAddress, mode.toShort())

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleClockGetres(clkId.toInt(), tp.rawAddress)

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleClockGettime(clkId.toInt(), tp.rawAddress)

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLioListio(mode, aiocbList.rawAddress, nitems, sevp.rawAddress)

public actual fun dirfd(dirp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDirfd(dirp.rawAddress)

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLutimes(file.rawAddress, times.rawAddress)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGettimeofday(tp.rawAddress, tz.rawAddress)

public actual fun getutxent(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetutxent())

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetutxid(ut.rawAddress))

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetutxline(ut.rawAddress))

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePututxline(ut.rawAddress))

public actual fun setutxent() {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetutxent()
}

public actual fun endutxent() {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleEndutxent()
}

public actual fun utmpxname(file: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleUtmpxname(file.rawAddress)

public actual fun asctime(tm: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAsctime(tm.rawAddress))

public actual fun ctime(clock: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCtime(clock.rawAddress))

public actual fun getdate(datestr: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetdate(datestr.rawAddress))

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleStrptime(buf.rawAddress, format.rawAddress, timeptr.rawAddress))

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleAsctimeR(tm.rawAddress, result.rawAddress))

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCtimeR(clock.rawAddress, result.rawAddress))

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetnameinfo(sa.rawAddress, salen.toInt(), host.rawAddress, hostlen.toInt(), serv.rawAddress, servlen.toInt(), flags)

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMincore(addr.rawAddress, len.toLong(), vec.rawAddress)

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSysctlnametomib(name.rawAddress, mibp.rawAddress, sizep.rawAddress)

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMprotect(addr.rawAddress, len.toLong(), prot)

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSemget(key, nsems, semflg)

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSemop(semid, sops.rawAddress, nsops.toLong())

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleShmOpen(name.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFtok(pathname.rawAddress, projId)

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleShmat(shmid, shmaddr.rawAddress, shmflg))

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleShmdt(shmaddr.rawAddress)

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleShmctl(shmid, cmd, buf.rawAddress)

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleShmget(key, size.toLong(), shmflg)

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSysctl(name.rawAddress, namelen.toInt(), oldp.rawAddress, oldlenp.rawAddress, newp.rawAddress, newlen.toLong())

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSysctlbyname(name.rawAddress, oldp.rawAddress, oldlenp.rawAddress, newp.rawAddress, newlen.toLong())

public actual fun machAbsoluteTime(): ULong =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachAbsoluteTime().toULong()

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachTimebaseInfo(info.rawAddress)

public actual fun machHostSelf(): MachPortT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachHostSelf().toUInt()

public actual fun machThreadSelf(): MachPortT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachThreadSelf().toUInt()

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond.rawAddress, lock.rawAddress, timeout.rawAddress)

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadAttrGetscope(attr.rawAddress, contentionscope.rawAddress)

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadAttrGetstackaddr(attr.rawAddress, stackaddr.rawAddress)

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadAttrGetdetachstate(attr.rawAddress, detachstate.rawAddress)

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadAttrSetscope(attr.rawAddress, contentionscope)

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadAttrSetstackaddr(attr.rawAddress, stackaddr.rawAddress)

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadSetnameNp(name.rawAddress)

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadGetnameNp(thread.toLong(), name.rawAddress, len.toLong())

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadMachThreadNp(thread.toLong()).toUInt()

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT): PthreadT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadFromMachThreadNp(port.toInt()).toULong()

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadGetStackaddrNp(thread.toLong()))

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadGetStacksizeNp(thread.toLong()).toULong()

public actual fun pthreadMainNp(): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadMainNp()

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadThreadidNp(thread.toLong(), threadId.rawAddress)

public actual fun pthreadJitWriteProtectNp(enabled: CInt) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)
}

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp()

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.unix.bsd.apple.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(0L, ctx.rawAddress)

public actual fun pthreadJitWriteFreezeCallbacksNp() {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()
}

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut.rawAddress)

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSyncWaitOnAddress(addr.rawAddress, value.toLong(), size.toLong(), flags.toInt())

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr.rawAddress, value.toLong(), size.toLong(), flags.toInt(), clockid.toInt(), deadline.toLong())

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr.rawAddress, value.toLong(), size.toLong(), flags.toInt(), clockid.toInt(), timeoutNs.toLong())

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(addr.rawAddress, size.toLong(), flags.toInt())

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(addr.rawAddress, size.toLong(), flags.toInt())

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsUnfairLockLock(lock.rawAddress)
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsUnfairLockTrylock(lock.rawAddress)

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsUnfairLockUnlock(lock.rawAddress)
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(lock.rawAddress)
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock.rawAddress)
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsLogCreate(subsystem.rawAddress, category.rawAddress))

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, tpe: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogTypeT): Boolean =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsLogTypeEnabled(oslog.rawAddress, tpe.toByte())

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log.rawAddress, ptr.rawAddress).toULong()

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSignpostIdGenerate(log.rawAddress).toULong()

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): Boolean =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOsSignpostEnabled(log.rawAddress)

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleThreadPolicySet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress, count.toInt())

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleThreadPolicyGet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress, count.rawAddress, getDefault.rawAddress)

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleThreadInfo(targetAct.toInt(), flavor.toInt(), threadInfoOut.rawAddress, threadInfoOutCnt.rawAddress)

public actual fun error(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleError())

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktrace(buf.rawAddress, sz)

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktraceSymbols(addrs.rawAddress, sz))

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktraceSymbolsFd(addrs.rawAddress, sz, fd)
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktraceFromFp(startfp.rawAddress, array.rawAddress, size)

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktraceImageOffsets(array.rawAddress, imageOffsets.rawAddress, size)
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBacktraceAsync(array.rawAddress, length.toLong(), taskId.rawAddress).toULong()

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleStatfs(path.rawAddress, buf.rawAddress)

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFstatfs(fd, buf.rawAddress)

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleKevent(kq, changelist.rawAddress, nchanges, eventlist.rawAddress, nevents, timeout.rawAddress)

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleKevent64(kq, changelist.rawAddress, nchanges, eventlist.rawAddress, nevents, flags.toInt(), timeout.rawAddress)

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMount(src.rawAddress, target.rawAddress, flags, data.rawAddress)

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFmount(src.rawAddress, fd, flags, data.rawAddress)

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePtrace(request, pid, addr.rawAddress, data)

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleQuotactl(special.rawAddress, cmd, id, data.rawAddress)

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSethostname(name.rawAddress, len)

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSendfile(fd, s, offset, len.rawAddress, hdtr.rawAddress, flags)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFutimens(fd, times.rawAddress)

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleUtimensat(dirfd, path.rawAddress, times.rawAddress, flag)

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleOpenpty(amaster.rawAddress, aslave.rawAddress, name.rawAddress, termp.rawAddress, winp.rawAddress)

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleForkpty(amaster.rawAddress, name.rawAddress, termp.rawAddress, winp.rawAddress)

public actual fun loginTty(fd: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLoginTty(fd)

public actual fun duplocale(base: LocaleT): LocaleT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDuplocale(base.rawAddress))

public actual fun freelocale(loc: LocaleT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFreelocale(loc.rawAddress)

public actual fun localeconvL(loc: LocaleT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleLocaleconvL(loc.rawAddress))

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNewlocale(mask, locale.rawAddress, base.rawAddress))

public actual fun uselocale(loc: LocaleT): LocaleT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleUselocale(loc.rawAddress))

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    ConstPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleQuerylocale(mask, loc.rawAddress))

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetpriority(which, who.toInt())

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT, prio: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetpriority(which, who.toInt(), prio)

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetdomainname(name.rawAddress, len)

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetdomainname(name.rawAddress, len)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePreadv(fd, iov.rawAddress, iovcnt, offset)

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePwritev(fd, iov.rawAddress, iovcnt, offset)

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), position.toInt(), flags)

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFgetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong(), position.toInt(), flags)

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), position.toInt(), flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFsetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong(), position.toInt(), flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleListxattr(path.rawAddress, list.rawAddress, size.toLong(), flags)

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFlistxattr(filedes, list.rawAddress, size.toLong(), flags)

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleRemovexattr(path.rawAddress, name.rawAddress, flags)

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleRenamexNp(from.rawAddress, to.rawAddress, flags.toInt())

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleRenameatxNp(fromfd, from.rawAddress, tofd, to.rawAddress, flags.toInt())

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFremovexattr(filedes, name.rawAddress, flags)

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetgrouplist(name.rawAddress, basegid, groups.rawAddress, ngroups.rawAddress)

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleInitgroups(user.rawAddress, basegroup)

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleWaitid(idtype.toInt(), id.toInt(), infop.rawAddress, options)

public actual fun brk(addr: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBrk(addr.rawAddress))

public actual fun sbrk(increment: CInt): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSbrk(increment))

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSettimeofday(tv.rawAddress, tz.rawAddress)

public actual fun dyldImageCount(): UInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDyldImageCount().toUInt()

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    ConstPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDyldGetImageHeader(imageIndex.toInt()))

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex.toInt())

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    ConstPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDyldGetImageName(imageIndex.toInt()))

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawn(pid.rawAddress, path.rawAddress, fileActions.rawAddress, attrp.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnp(pid.rawAddress, file.rawAddress, fileActions.rawAddress, attrp.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrInit(attr.rawAddress)

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrDestroy(attr.rawAddress)

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetflags(attr.rawAddress, flags.rawAddress)

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetflags(attr.rawAddress, flags)

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(attr.rawAddress, flags.rawAddress)

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(attr.rawAddress, flags)

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr.rawAddress, count.toLong(), pref.rawAddress, subpref.rawAddress, ocount.rawAddress)

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr.rawAddress, count.toLong(), pref.rawAddress, subpref.rawAddress, ocount.rawAddress)

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr.rawAddress, count.toLong(), pref.rawAddress, ocount.rawAddress)

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr.rawAddress, count.toLong(), pref.rawAddress, ocount.rawAddress)

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsInit(actions.rawAddress)

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions.rawAddress)

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions.rawAddress, fd, path.rawAddress, oflag, mode.toShort())

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions.rawAddress, fd)

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions.rawAddress, fd, newfd)

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions.rawAddress, path.rawAddress)

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions.rawAddress, fd)

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions.rawAddress, path.rawAddress)

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions.rawAddress, fd)

public actual fun uname(buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleUname(buf.rawAddress)

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleConnectx(socket, endpoints.rawAddress, associd.toInt(), flags.toInt(), iov.rawAddress, iovcnt.toInt(), len.rawAddress, connid.rawAddress)

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDisconnectx(socket, associd.toInt(), connid.toInt())

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNtpAdjtime(buf.rawAddress)

public actual fun ntpGettime(buf: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNtpGettime(buf.rawAddress)

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetmntinfo(mntbufp.rawAddress, flags)

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetfsstat(mntbufp.rawAddress, bufsize, flags)

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleClonefile(src.rawAddress, dst.rawAddress, flags.toInt())

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleClonefileat(srcDirfd, src.rawAddress, dstDirfd, dst.rawAddress, flags.toInt())

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFclonefileat(srcfd, dstDirfd, dst.rawAddress, flags.toInt())

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCopyfile(from.rawAddress, to.rawAddress, state.rawAddress, flags.toInt())

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFcopyfile(from, to, state.rawAddress, flags.toInt())

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCopyfileStateFree(s.rawAddress)

public actual fun copyfileStateAlloc(): CopyfileStateT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCopyfileStateAlloc())

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCopyfileStateGet(s.rawAddress, flags.toInt(), dst.rawAddress)

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCopyfileStateSet(s.rawAddress, flags.toInt(), src.rawAddress)

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.unix.bsd.apple.MachErrorT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachErrorString(errorValue))

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMemsetS(s.rawAddress, smax.toLong(), c, n.toLong())

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMemsetPattern4(b.rawAddress, pattern4.rawAddress, len.toLong())
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMemsetPattern8(b.rawAddress, pattern8.rawAddress, len.toLong())
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMemsetPattern16(b.rawAddress, pattern16.rawAddress, len.toLong())
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleStrtonum(numstr.rawAddress, minval, maxval, errstrp.rawAddress)

public actual fun mstats(): Mstats =
    Mstats()

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocPrintf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneCheck(zone.rawAddress)

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZonePrint(zone.rawAddress, verbose)
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneStatistics(zone.rawAddress, stats.rawAddress)
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneLog(zone.rawAddress, address.rawAddress)
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr.rawAddress)
}

public actual fun mallocDefaultZone(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocDefaultZone())

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneFromPtr(ptr.rawAddress))

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneMalloc(zone.rawAddress, size.toLong()))

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneValloc(zone.rawAddress, size.toLong()))

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneCalloc(zone.rawAddress, numItems.toLong(), size.toLong()))

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneRealloc(zone.rawAddress, ptr.rawAddress, size.toLong()))

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocZoneFree(zone.rawAddress, ptr.rawAddress)
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGethostuuid(id.rawAddress, timeout.rawAddress)

public actual fun gethostid(): CLong =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGethostid()

public actual fun sethostid(hostid: CLong) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSethostid(hostid)
}

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.CCRNGStatus =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleCCRandomGenerateBytes(bytes.rawAddress, size.toLong())

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetentropy(buf.rawAddress, buflen.toLong())

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNsgetExecutablePath(buf.rawAddress, bufsize.rawAddress)

public actual fun nsgetArgv(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNsgetArgv())

public actual fun nsgetArgc(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNsgetArgc())

public actual fun nsgetEnviron(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNsgetEnviron())

public actual fun nsgetProgname(): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleNsgetProgname())

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmSizeT, mask: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.unix.bsd.apple.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.unix.bsd.apple.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, maxProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, inheritance: io.github.kotlinmania.rustlang.unix.bsd.apple.VmInheritT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMachVmMap(targetTask.toInt(), address.rawAddress, size.toLong(), mask.toLong(), flags, obj.toInt(), offset.toLong(), copy, curProtection, maxProtection, inheritance.toInt())

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleVmAllocate(targetTask.toInt(), address.rawAddress, size.toLong(), flags)

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleVmDeallocate(targetTask.toInt(), address.toLong(), size.toLong())

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleHostStatistics64(hostPriv.toInt(), flavor, hostInfo64Out.rawAddress, hostInfo64OutCnt.rawAddress)

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleHostProcessorInfo(host.toInt(), flavor, outProcessorCount.rawAddress, outProcessorInfo.rawAddress, outProcessorInfoCnt.rawAddress)

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskForPid(host.toInt(), pid, task.rawAddress)

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskInfo(host.toInt(), flavor.toInt(), taskInfoOut.rawAddress, taskInfoCount.rawAddress)

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, ledgers: io.github.kotlinmania.rustlang.unix.bsd.apple.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskCreate(targetTask.toInt(), ledgers.rawAddress, ledgersCnt.toInt(), inheritMemory, childTask.rawAddress)

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskTerminate(targetTask.toInt())

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskThreads(targetTask.toInt(), actList.rawAddress, actListCnt.rawAddress)

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleHostStatistics(hostPriv.toInt(), flavor, hostInfoOut.rawAddress, hostInfoOutCnt.rawAddress)

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    0u

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state.toInt(), path.rawAddress).toUInt()

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetattrlist(path.rawAddress, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toInt())

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFgetattrlist(fd, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toInt())

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetattrlistat(fd, path.rawAddress, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toLong())

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetattrlist(path.rawAddress, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toInt())

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFsetattrlist(fd, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toInt())

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleSetattrlistat(dirFd, path.rawAddress, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toInt())

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleGetattrlistbulk(dirfd, attrList.rawAddress, attrBuf.rawAddress, attrBufSize.toLong(), options.toLong())

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocSize(ptr.rawAddress).toULong()

public actual fun mallocGoodSize(size: SizeT): SizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMallocGoodSize(size.toLong()).toULong()

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleDirname(path.rawAddress))

public actual fun basename(path: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleBasename(path.rawAddress))

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMkfifoat(dirfd, pathname.rawAddress, mode.toShort())

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMknodat(dirfd, pathname.rawAddress, mode.toShort(), dev)

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleFreadlink(fd, buf.rawAddress, size.toLong())

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleExecvP(file.rawAddress, searchPath.rawAddress, argv.rawAddress)

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleQsortR(base.rawAddress, num.toLong(), size.toLong(), arg.rawAddress, 0L)
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleClockSettime(clockId.toInt(), tp.rawAddress)

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcListpids(t.toInt(), typeinfo.toInt(), buffer.rawAddress, buffersize)

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcListallpids(buffer.rawAddress, buffersize)

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcListpgrppids(pgrpid, buffer.rawAddress, buffersize)

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcListchildpids(ppid, buffer.rawAddress, buffersize)

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcPidinfo(pid, flavor, arg.toLong(), buffer.rawAddress, buffersize)

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcPidfdinfo(pid, fd, flavor, buffer.rawAddress, buffersize)

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcPidfileportinfo(pid, fileport.toInt(), flavor, buffer.rawAddress, buffersize)

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcPidpath(pid, buffer.rawAddress, buffersize.toInt())

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcName(pid, buffer.rawAddress, buffersize.toInt())

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcRegionfilename(pid, address.toLong(), buffer.rawAddress, buffersize.toInt())

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcKmsgbuf(buffer.rawAddress, buffersize.toInt())

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcLibversion(major.rawAddress, minor.rawAddress)

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcPidRusage(pid, flavor, buffer.rawAddress)

public actual fun procSetNoSmt(): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcSetNoSmt()

public actual fun procSetthreadNoSmt(): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcSetthreadNoSmt()

public actual fun procSetCsm(flags: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcSetCsm(flags.toInt())

public actual fun procSetthreadCsm(flags: UInt): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleProcSetthreadCsm(flags.toInt())

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleMemmem(haystack.rawAddress, haystacklen.toLong(), needle.rawAddress, needlelen.toLong()))

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, flavor: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleTaskSetInfo(targetTask.toInt(), flavor.toInt(), taskInfoIn.rawAddress, taskInfoInCnt.toInt())

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    MutPtr(LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleIconvOpen(tocode.rawAddress, fromcode.rawAddress))

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleIconv(cd.rawAddress, inbuf.rawAddress, inbytesleft.rawAddress, outbuf.rawAddress, outbytesleft.rawAddress).toULong()

public actual fun iconvClose(cd: IconvT): CInt =
    LibcUnixBsdAppleJniBridge.rustlangLibcUnixBsdAppleIconvClose(cd.rawAddress)

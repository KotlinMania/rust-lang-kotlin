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

private object LibcUnixLinuxlikeAndroidJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetpwent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEndpwent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetpwent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetgrent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEndgrent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetrlimit64(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetrlimit64(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPrlimit(pid: Int, resource: Int, newLimit: Long, oldLimit: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPrlimit64(pid: Int, resource: Int, newLimit: Long, oldLimit: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidStrerrorR(errnum: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGettimeofday(tp: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMlock2(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMadvise(addr: Long, len: Long, advice: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMsync(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMprotect(addr: Long, len: Long, prot: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetnameinfo(sa: Long, salen: Int, host: Long, hostlen: Long, serv: Long, servlen: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPreadv(fd: Int, iov: Long, count: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPwritev(fd: Int, iov: Long, count: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(pid: Int, localIov: Long, localIovCount: Long, remoteIov: Long, remoteIovCount: Long, flags: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(pid: Int, localIov: Long, localIovCount: Long, remoteIov: Long, remoteIovCount: Long, flags: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPtrace(request: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetpriority(which: Int, who: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetpriority(which: Int, who: Int, prio: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedCpufree(set: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedCpucount(setsize: Long, set: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetcpu(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMallocInfo(options: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidUtmpname(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetutent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetutent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSeekdir(dirp: Long, loc: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTelldir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFallocate(fd: Int, mode: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFallocate64(fd: Int, mode: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetxattr(path: Long, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidLgetxattr(path: Long, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFgetxattr(filedes: Int, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetxattr(path: Long, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidLsetxattr(path: Long, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFsetxattr(filedes: Int, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidListxattr(path: Long, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidLlistxattr(path: Long, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFlistxattr(filedes: Int, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRemovexattr(path: Long, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidLremovexattr(path: Long, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFremovexattr(filedes: Int, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSignalfd(fd: Int, mask: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(fd: Int, currentValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(fd: Int, flags: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerCreate(clockid: Int, sevp: Long, timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerDelete(timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerGettime(timerid: Long, currValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTimerSettime(timerid: Long, flags: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSyscall(num: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(pid: Int, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(pid: Int, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEpollCreate(size: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEpollWait(epfd: Int, events: Long, maxevents: Int, timeout: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEpollCtl(epfd: Int, op: Int, fd: Int, event: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidUnshare(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidUmount(target: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTee(fdIn: Int, fdOut: Int, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSettimeofday(tv: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSplice(fdIn: Int, offIn: Long, fdOut: Int, offOut: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEventfd(initval: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEventfdRead(fd: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(pid: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSemTimedwait(sem: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSemGetvalue(sem: Long, sval: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedSetparam(pid: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetns(fd: Int, nstype: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSwapoff(puath: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidVmsplice(fd: Int, iov: Long, nrSegs: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMount(src: Long, target: Long, fstype: Long, flags: Long, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPersonality(persona: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPrctl(option: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetparam(pid: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPpoll(fds: Long, nfds: Int, timeout: Long, sigmask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidClone(cb: Long, childStack: Long, flags: Int, arg: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidClockNanosleep(clkId: Int, flags: Int, rqtp: Long, rmtp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSethostname(name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSysinfo(info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidUmount2(target: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSwapon(path: Long, swapflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(pid: Int, policy: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSendfile(outFd: Int, inFd: Int, offset: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSendfile64(outFd: Int, inFd: Int, offset: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetfsgid(gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSetfsuid(uid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSigsuspend(mask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrgidR(gid: Int, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSigaltstack(ss: Long, oss: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSemClose(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrnamR(name: Long, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSemOpen(name: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrnam(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSemUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidDaemon(nochdir: Int, noclose: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSigtimedwait(set: Long, info: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSigwait(set: Long, sig: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrgid(gid: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetgrouplist(user: Long, group: Int, groups: Long, ngroups: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidInitgroups(user: Long, group: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPopen(command: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFaccessat(dirfd: Int, pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidErrno(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd: Int, wd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidInotifyInit(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(fd: Int, path: Long, mask: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRegcomp(preg: Long, pattern: Long, cflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRegexec(preg: Long, input: Long, nmatch: Long, pmatch: Long, eflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRegerror(errcode: Int, preg: Long, errbuf: Long, errbufSize: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRegfree(preg: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(msg: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGettid(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidTgkill(tgid: Int, tid: Int, sig: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetauxval(type: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetrandom(buf: Long, buflen: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetentropy(buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(name: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(name: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(n: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(callback: Long, cookie: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(callback: Long, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidArc4random(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(buf: Long, n: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidReallocarray(ptr: Long, nmemb: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidDirname(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidBasename(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidGetoptLong(argc: Int, argv: Long, optstring: Long, longopts: Long, longindex: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSync()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidSyncfs(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMemmem(haystack: Long, haystacklen: Long, needle: Long, needlelen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(buf: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(buf: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(buf: Long, size: Int, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidKlogctl(syslogType: Int, bufp: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidMemfdCreate(name: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidRenameat2(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPreadv2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidPwritev2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long
}

public actual fun setpwent() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetpwent()
}

public actual fun endpwent() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEndpwent()
}

public actual fun getpwent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetpwent())

public actual fun setgrent() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetgrent()
}

public actual fun endgrent() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrent())

public actual fun getrlimit64(resource: CInt, rlim: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetrlimit64(resource, rlim.rawAddress)

public actual fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetrlimit64(resource, rlim.rawAddress)

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetrlimit(resource, rlim.rawAddress)

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetrlimit(resource, rlim.rawAddress)

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPrlimit(pid, resource, newLimit.rawAddress, oldLimit.rawAddress)

public actual fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPrlimit64(pid, resource, newLimit.rawAddress, oldLimit.rawAddress)

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidStrerrorR(errnum, buf.rawAddress, buflen.toLong())

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGettimeofday(tp.rawAddress, tz.rawAddress)

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMlock2(addr.rawAddress, len.toLong(), flags)

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMadvise(addr.rawAddress, len.toLong(), advice)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMsync(addr.rawAddress, len.toLong(), flags)

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMprotect(addr.rawAddress, len.toLong(), prot)

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SizeT, serv: MutPtr, servlen: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetnameinfo(sa.rawAddress, salen.toInt(), host.rawAddress, hostlen.toLong(), serv.rawAddress, servlen.toLong(), flags)

public actual fun preadv(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPreadv(fd, iov.rawAddress, count, offset)

public actual fun pwritev(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPwritev(fd, iov.rawAddress, count, offset)

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(pid, localIov.rawAddress, localIovCount.toLong(), remoteIov.rawAddress, remoteIovCount.toLong(), flags.toLong())

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(pid, localIov.rawAddress, localIovCount.toLong(), remoteIov.rawAddress, remoteIovCount.toLong(), flags.toLong())

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPtrace(request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getpriority(which: CInt, who: IdT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetpriority(which, who.toInt())

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetpriority(which, who.toInt(), prio)

public actual fun schedCpualloc(count: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(count.toLong()))

public actual fun schedCpufree(set: MutPtr) {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedCpufree(set.rawAddress)
}

public actual fun schedCpucount(setsize: SizeT, set: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedCpucount(setsize.toLong(), set.rawAddress)

public actual fun schedGetcpu(): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetcpu()

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo =
    io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo()

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMallocInfo(options, stream.rawAddress)

public actual fun mallocUsableSize(ptr: ConstPtr): SizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(ptr.rawAddress).toULong()

public actual fun utmpname(name: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidUtmpname(name.rawAddress)

public actual fun setutent() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetutent()
}

public actual fun getutent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetutent())

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSeekdir(dirp.rawAddress, loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTelldir(dirp.rawAddress)

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFallocate(fd, mode, offset, len)

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFallocate64(fd, mode, offset, len)

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd, offset, len)

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd, offset, len)

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong())

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidLgetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong())

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFgetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong())

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidLsetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFsetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidListxattr(path.rawAddress, list.rawAddress, size.toLong())

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidLlistxattr(path.rawAddress, list.rawAddress, size.toLong())

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFlistxattr(filedes, list.rawAddress, size.toLong())

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRemovexattr(path.rawAddress, name.rawAddress)

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidLremovexattr(path.rawAddress, name.rawAddress)

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFremovexattr(filedes, name.rawAddress)

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSignalfd(fd, mask.rawAddress, flags)

public actual fun timerfdCreate(clock: ClockidT, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock.toInt(), flags)

public actual fun timerfdGettime(fd: CInt, currentValue: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(fd, currentValue.rawAddress)

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(fd, flags, newValue.rawAddress, oldValue.rawAddress)

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerCreate(clockid.toInt(), sevp.rawAddress, timerid.rawAddress)

public actual fun timerDelete(timerid: TimerT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerDelete(timerid.rawAddress)

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(timerid.rawAddress)

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerGettime(timerid.rawAddress, currValue.rawAddress)

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTimerSettime(timerid.rawAddress, flags, newValue.rawAddress, oldValue.rawAddress)

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(pid, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(pid, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun epollCreate(size: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEpollCreate(size)

public actual fun epollCreate1(flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags)

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEpollWait(epfd, events.rawAddress, maxevents, timeout)

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEpollCtl(epfd, op, fd, event.rawAddress)

public actual fun unshare(flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidUnshare(flags)

public actual fun umount(target: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidUmount(target.rawAddress)

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy)

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTee(fdIn, fdOut, len.toLong(), flags.toInt())

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSettimeofday(tv.rawAddress, tz.rawAddress)

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSplice(fdIn, offIn.rawAddress, fdOut, offOut.rawAddress, len.toLong(), flags.toInt())

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEventfd(initval.toInt(), flags)

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEventfdRead(fd, value.rawAddress)

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd, value.toLong())

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(pid, tp.rawAddress)

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSemTimedwait(sem.rawAddress, abstime.rawAddress)

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSemGetvalue(sem.rawAddress, sval.rawAddress)

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedSetparam(pid, param.rawAddress)

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetns(fd, nstype)

public actual fun swapoff(puath: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSwapoff(puath.rawAddress)

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidVmsplice(fd, iov.rawAddress, nrSegs.toLong(), flags.toInt())

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMount(src.rawAddress, target.rawAddress, fstype.rawAddress, flags.toLong(), data.rawAddress)

public actual fun personality(persona: CUint): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPersonality(persona.toInt())

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPrctl(option, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetparam(pid, param.rawAddress)

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPpoll(fds.rawAddress, nfds.toInt(), timeout.rawAddress, sigmask.rawAddress)

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidClone(0L, childStack.rawAddress, flags, arg.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetscheduler(pid: PidT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid)

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidClockNanosleep(clkId.toInt(), flags, rqtp.rawAddress, rmtp.rawAddress)

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSethostname(name.rawAddress, len.toLong())

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy)

public actual fun sysinfo(info: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSysinfo(info.rawAddress)

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidUmount2(target.rawAddress, flags)

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSwapon(path.rawAddress, swapflags)

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(pid, policy, param.rawAddress)

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSendfile(outFd, inFd, offset.rawAddress, count.toLong())

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSendfile64(outFd, inFd, offset.rawAddress, count.toLong())

public actual fun setfsgid(gid: GidT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetfsgid(gid)

public actual fun setfsuid(uid: UidT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSetfsuid(uid)

public actual fun sigsuspend(mask: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSigsuspend(mask.rawAddress)

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrgidR(gid, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSigaltstack(ss.rawAddress, oss.rawAddress)

public actual fun semClose(sem: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSemClose(sem.rawAddress)

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrnamR(name.rawAddress, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSemOpen(name.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L))

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrnam(name.rawAddress))

public actual fun semUnlink(name: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSemUnlink(name.rawAddress)

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidDaemon(nochdir, noclose)

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSigtimedwait(set.rawAddress, info.rawAddress, timeout.rawAddress)

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSigwait(set.rawAddress, sig.rawAddress)

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrgid(gid))

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetgrouplist(user.rawAddress, group, groups.rawAddress, ngroups.rawAddress)

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidInitgroups(user.rawAddress, group)

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPopen(command.rawAddress, mode.rawAddress))

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFaccessat(dirfd, pathname.rawAddress, mode, flags)

public actual fun errno(): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidErrno())

public actual fun inotifyRmWatch(fd: CInt, wd: UInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd, wd.toInt())

public actual fun inotifyInit(): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidInotifyInit()

public actual fun inotifyInit1(flags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags)

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(fd, path.rawAddress, mask.toInt())

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRegcomp(preg.rawAddress, pattern.rawAddress, cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRegexec(preg.rawAddress, input.rawAddress, nmatch.toLong(), pmatch.rawAddress, eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRegerror(errcode, preg.rawAddress, errbuf.rawAddress, errbufSize.toLong()).toULong()

public actual fun regfree(preg: MutPtr) {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRegfree(preg.rawAddress)
}

public actual fun androidSetAbortMessage(msg: ConstPtr) {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(msg.rawAddress)
}

public actual fun gettid(): PidT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGettid()

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidTgkill(tgid, tid, sig)

public actual fun getauxval(type: CUlong): CUlong =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetauxval(type.toLong()).toULong()

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetrandom(buf.rawAddress, buflen.toLong(), flags.toInt())

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetentropy(buf.rawAddress, buflen.toLong())

public actual fun systemPropertySet(name: ConstPtr, value: ConstPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(name.rawAddress, value.rawAddress)

public actual fun systemPropertyGet(name: ConstPtr, value: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(name.rawAddress, value.rawAddress)

public actual fun systemPropertyFind(name: ConstPtr): ConstPtr =
    ConstPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(name.rawAddress))

public actual fun systemPropertyFindNth(n: CUint): ConstPtr =
    ConstPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(n.toInt()))

public actual fun systemPropertyForeach(callback: SystemPropertyForeachCallbackCallback, cookie: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(0L, cookie.rawAddress)

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(0L, data.rawAddress)

public actual fun arc4random(): UInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidArc4random().toUInt()

public actual fun arc4randomUniform(upperBound: UInt): UInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound.toInt()).toUInt()

public actual fun arc4randomBuf(buf: MutPtr, n: SizeT) {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(buf.rawAddress, n.toLong())
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidReallocarray(ptr.rawAddress, nmemb.toLong(), size.toLong()))

public actual fun dirname(path: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidDirname(path.rawAddress))

public actual fun basename(path: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidBasename(path.rawAddress))

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidGetoptLong(argc, argv.rawAddress, optstring.rawAddress, longopts.rawAddress, longindex.rawAddress)

public actual fun sync() {
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSync()
}

public actual fun syncfs(fd: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidSyncfs(fd)

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMemmem(haystack.rawAddress, haystacklen.toLong(), needle.rawAddress, needlelen.toLong()))

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(buf.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fwriteUnlocked(buf: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(buf.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fflushUnlocked(stream: MutPtr): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(stream.rawAddress)

public actual fun fgetsUnlocked(buf: MutPtr, size: CInt, stream: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(buf.rawAddress, size, stream.rawAddress))

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidKlogctl(syslogType, bufp.rawAddress, len)

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidMemfdCreate(name.rawAddress, flags.toInt())

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidRenameat2(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags.toInt())

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPreadv2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeAndroidJniBridge.rustlangLibcUnixLinuxlikeAndroidPwritev2(fd, iov.rawAddress, iovcnt, offset, flags)

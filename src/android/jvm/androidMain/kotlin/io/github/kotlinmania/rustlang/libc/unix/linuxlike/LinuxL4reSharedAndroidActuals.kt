@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

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
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

private object LibcUnixLinuxlikeLinuxL4reSharedJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from: Long, num: Long, turnOn: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(op: Int, aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(aiocbp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(aiocbList: Long, nitems: Int, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(fd: Int, aiocbp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(mode: Int, aiocbList: Long, nitems: Int, sevp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(sa: Long, salen: Int, host: Long, hostlen: Int, serv: Long, servlen: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(loadavg: Long, nelem: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(pid: Int, localIov: Long, liovcnt: Long, remoteIov: Long, riovcnt: Long, flags: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(pid: Int, localIov: Long, liovcnt: Long, remoteIov: Long, riovcnt: Long, flags: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(fd: Int, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(errnum: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRand(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed: Int)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48(): Double

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(xseed: Long): Double

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key: Int, size: Long, shmflg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(shmid: Int, shmaddr: Long, shmflg: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(shmaddr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(shmid: Int, cmd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(addr: Long, len: Long, prot: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(pattern: Long, flags: Int, errfunc: Long, pglob: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(pglob: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(dirp: Long, loc: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(addr: Long, len: Long, advice: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(socket: Int, buf: Long, len: Long, flags: Int, addr: Long, addrlen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(item: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(item: Int, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(pid: Int, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(tv: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(sem: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(sem: Long, sval: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedMount(src: Long, target: Long, fstype: Long, flags: Long, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(option: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(fds: Long, nfds: Long, timeout: Long, sigmask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(mask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(gid: Int, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(name: Long, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(user: Long, group: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(name: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir: Int, noclose: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(set: Long, sig: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(gid: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(command: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(dirfd: Int, pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(callback: Long, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(filename: Long, ty: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(stream: Long, mnt: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(streamp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(mnt: Long, opt: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(preg: Long, pattern: Long, cflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(preg: Long, input: Long, nmatch: Long, pmatch: Long, eflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(errcode: Int, preg: Long, errbuf: Long, errbufSize: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(preg: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(tocode: Long, fromcode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(cd: Long, inbuf: Long, inbytesleft: Long, outbuf: Long, outbytesleft: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(cd: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(clockid: Int, sevp: Long, timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(timerid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(timerid: Long, currValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(timerid: Long, flags: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(haystack: Long, haystacklen: Long, needle: Long, needlelen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(argc: Int, argv: Long, optstring: Long, longopts: Long, longindex: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(fdIn: Int, offIn: Long, fdOut: Int, offOut: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(filename: Long, mode: Long, file: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(stream: Long): Long
}

public actual fun iopl(level: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level)

public actual fun ioperm(from: CUlong, num: CUlong, turnOn: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from.toLong(), num.toLong(), turnOn)

public actual fun aioRead(aiocbp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(aiocbp.rawAddress)

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(aiocbp.rawAddress)

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(op, aiocbp.rawAddress)

public actual fun aioError(aiocbp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(aiocbp.rawAddress)

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(aiocbp.rawAddress)

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(aiocbList.rawAddress, nitems, timeout.rawAddress)

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(fd, aiocbp.rawAddress)

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(mode, aiocbList.rawAddress, nitems, sevp.rawAddress)

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(fd, iov.rawAddress, iovcnt, offset)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(fd, iov.rawAddress, iovcnt, offset)

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(sa.rawAddress, salen.toInt(), host.rawAddress, hostlen.toInt(), serv.rawAddress, servlen.toInt(), flags)

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(loadavg.rawAddress, nelem)

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(pid, localIov.rawAddress, liovcnt.toLong(), remoteIov.rawAddress, riovcnt.toLong(), flags.toLong())

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(pid, localIov.rawAddress, liovcnt.toLong(), remoteIov.rawAddress, riovcnt.toLong(), flags.toLong())

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(fd, times.rawAddress)

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(errnum, buf.rawAddress, buflen.toLong())

public actual fun abs(i: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i)

public actual fun labs(i: CLong): CLong =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i)

public actual fun rand(): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRand()

public actual fun srand(seed: CUint) {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed.toInt())
}

public actual fun drand48(): CDouble =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48()

public actual fun erand48(xseed: MutPtr): CDouble =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(xseed.rawAddress)

public actual fun lrand48(): CLong =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48()

public actual fun nrand48(xseed: MutPtr): CLong =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(xseed.rawAddress)

public actual fun jrand48(xseed: MutPtr): CLong =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(xseed.rawAddress)

public actual fun srand48(seed: CLong) {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed)
}

public actual fun setpwent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()
}

public actual fun endpwent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()
}

public actual fun getpwent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent())

public actual fun setgrent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()
}

public actual fun endgrent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent())

public actual fun setspent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()
}

public actual fun endspent() {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()
}

public actual fun getspent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent())

public actual fun getspnam(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(name.rawAddress))

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key, size.toLong(), shmflg)

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(shmid, shmaddr.rawAddress, shmflg))

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(shmaddr.rawAddress)

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(shmid, cmd, buf.rawAddress)

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(addr.rawAddress, len.toLong(), prot)

public actual fun errnoLocation(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation())

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(pattern.rawAddress, flags, 0L, pglob.rawAddress)

public actual fun globfree(pglob: MutPtr) {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(pglob.rawAddress)
}

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(dirp.rawAddress, loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(dirp.rawAddress)

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(addr.rawAddress, len.toLong(), advice)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(addr.rawAddress, len.toLong(), flags)

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(socket, buf.rawAddress, len.toLong(), flags, addr.rawAddress, addrlen.rawAddress)

public actual fun nlLanginfo(item: NlItem): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(item))

public actual fun nlLanginfoL(item: NlItem, locale: LocaleT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(item, locale.rawAddress))

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(pid, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy)

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(tv.rawAddress, tz.rawAddress)

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(sem.rawAddress, abstime.rawAddress)

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(sem.rawAddress, sval.rawAddress)

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedMount(src.rawAddress, target.rawAddress, fstype.rawAddress, flags.toLong(), data.rawAddress)

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(option, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(fds.rawAddress, nfds.toLong(), timeout.rawAddress, sigmask.rawAddress)

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(name.rawAddress, len.toLong())

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy)

public actual fun sysinfo(info: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(info.rawAddress)

public actual fun sigsuspend(mask: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(mask.rawAddress)

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(gid, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun semClose(sem: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(sem.rawAddress)

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(name.rawAddress, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(user.rawAddress, group)

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(name.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L))

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(name.rawAddress))

public actual fun semUnlink(name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(name.rawAddress)

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir, noclose)

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(set.rawAddress, sig.rawAddress)

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(gid))

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(command.rawAddress, mode.rawAddress))

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(dirfd, pathname.rawAddress, mode, flags)

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(0L, data.rawAddress)

public actual fun setmntent(filename: ConstPtr, ty: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(filename.rawAddress, ty.rawAddress))

public actual fun getmntent(stream: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(stream.rawAddress))

public actual fun addmntent(stream: MutPtr, mnt: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(stream.rawAddress, mnt.rawAddress)

public actual fun endmntent(streamp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(streamp.rawAddress)

public actual fun hasmntopt(mnt: ConstPtr, opt: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(mnt.rawAddress, opt.rawAddress))

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(preg.rawAddress, pattern.rawAddress, cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(preg.rawAddress, input.rawAddress, nmatch.toLong(), pmatch.rawAddress, eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(errcode, preg.rawAddress, errbuf.rawAddress, errbufSize.toLong()).toULong()

public actual fun regfree(preg: MutPtr) {
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(preg.rawAddress)
}

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(tocode.rawAddress, fromcode.rawAddress))

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(cd.rawAddress, inbuf.rawAddress, inbytesleft.rawAddress, outbuf.rawAddress, outbytesleft.rawAddress).toULong()

public actual fun iconvClose(cd: IconvT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(cd.rawAddress)

public actual fun gettid(): PidT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid()

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(clockid.toInt(), sevp.rawAddress, timerid.rawAddress)

public actual fun timerDelete(timerid: TimerT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(timerid.rawAddress)

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(timerid.rawAddress)

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(timerid.rawAddress, currValue.rawAddress)

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(timerid.rawAddress, flags, newValue.rawAddress, oldValue.rawAddress)

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(haystack.rawAddress, haystacklen.toLong(), needle.rawAddress, needlelen.toLong()))

public actual fun schedGetcpu(): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu()

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(argc, argv.rawAddress, optstring.rawAddress, longopts.rawAddress, longindex.rawAddress)

public actual fun copyFileRange(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(fdIn, offIn.rawAddress, fdOut, offOut.rawAddress, len.toLong(), flags.toInt())

public actual fun freopen64(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(filename.rawAddress, mode.rawAddress, file.rawAddress))

public actual fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(stream.rawAddress, offset, whence)

public actual fun fsetpos64(stream: MutPtr, ptr: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(stream.rawAddress, ptr.rawAddress)

public actual fun ftello64(stream: MutPtr): Off64T =
    LibcUnixLinuxlikeLinuxL4reSharedJniBridge.rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(stream.rawAddress)

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

private object LibcUnixLinuxlikeLinuxGnuJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(value: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(fp: Long, spbuf: Long, buf: Long, buflen: Long, spbufp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(s: Long, spbuf: Long, buf: Long, buflen: Long, spbufp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf: Long, buf: Long, buflen: Long, spbufp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuQsortR(base: Long, num: Long, size: Long, compar: Long, arg: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd: Int, msgvec: Long, vlen: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd: Int, msgvec: Long, vlen: Int, flags: Int, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(pid: Int, resource: Int, newLimit: Long, oldLimit: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(pid: Int, resource: Int, newLimit: Long, oldLimit: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetutxent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPututxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSetutxent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuEndutxent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetpt(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMallopt(param: Int, value: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(tp: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(buf: Long, buflen: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(type: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd: Int, flags: Int, mask: Long, dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(s: Long, len: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(ptr: Long, nmemb: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuCtermid(s: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(buf: Long, sz: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer: Long, len: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer: Long, len: Int, fd: Int)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGlob64(pattern: Long, flags: Int, errfunc: Long, pglob: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPtrace(request: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(which: Int, who: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(which: Int, who: Int, prio: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr: Long, value: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread: Long, retval: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread: Long, retval: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMallocStats()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(options: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream: Long, pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream: Long, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf: Long, buf: Long, buflen: Long, result: Long, hErrnop: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name: Long, resultBuf: Long, buf: Long, buflen: Long, result: Long, hErrnop: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net: Int, type: Int, resultBuf: Long, buf: Long, buflen: Long, result: Long, hErrnop: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p: Long, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(grp: Long, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuSethostid(hostid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(name: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMlock2(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuEaccess(pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(tm: Long, buf: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(timep: Long, buf: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuDirname(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(lmid: Long, filename: Long, flag: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(handle: Long, request: Int, info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(addr: Long, info: Long, extraInfo: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(handle: Long, symbol: Long, version: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(pad: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions: Long, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions: Long, from: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions: Long, tcfd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(stream: Long, mntbuf: Long, buf: Long, buflen: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuExecveat(dirfd: Int, pathname: Long, argv: Long, envp: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(first: Int, last: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes: Int, sevp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd: Int, events: Long, maxevents: Int, timeout: Long, sigmask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuTgkill(tgid: Int, tid: Int, sig: Int): Int
}

public actual var ENVIRON: MutPtr
    get() = MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON())
    set(value) {
        LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(value.rawAddress)
    }

public actual fun fgetspentR(fp: MutPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(fp.rawAddress, spbuf.rawAddress, buf.rawAddress, buflen.toLong(), spbufp.rawAddress)

public actual fun sgetspentR(s: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(s.rawAddress, spbuf.rawAddress, buf.rawAddress, buflen.toLong(), spbufp.rawAddress)

public actual fun getspentR(spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf.rawAddress, buf.rawAddress, buflen.toLong(), spbufp.rawAddress)

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, compar: QsortRComparCallback?, arg: MutPtr) {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuQsortR(base.rawAddress, num.toLong(), size.toLong(), 0L, arg.rawAddress)
}

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd, msgvec.rawAddress, vlen.toInt(), flags)

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd, msgvec.rawAddress, vlen.toInt(), flags, timeout.rawAddress)

public actual fun getrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource.toInt(), rlim.rawAddress)

public actual fun setrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource.toInt(), rlim.rawAddress)

public actual fun getrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(resource.toInt(), rlim.rawAddress)

public actual fun setrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(resource.toInt(), rlim.rawAddress)

public actual fun prlimit(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(pid, resource.toInt(), newLimit.rawAddress, oldLimit.rawAddress)

public actual fun prlimit64(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(pid, resource.toInt(), newLimit.rawAddress, oldLimit.rawAddress)

public actual fun utmpname(file: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(file.rawAddress)

public actual fun utmpxname(file: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(file.rawAddress)

public actual fun getutxent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetutxent())

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(ut.rawAddress))

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(ut.rawAddress))

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPututxline(ut.rawAddress))

public actual fun setutxent() {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSetutxent()
}

public actual fun endutxent() {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuEndutxent()
}

public actual fun getpt(): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetpt()

public actual fun mallopt(param: CInt, value: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMallopt(param, value)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(tp.rawAddress, tz.rawAddress)

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(buf.rawAddress, buflen.toLong())

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(buf.rawAddress, buflen.toLong(), flags.toInt())

public actual fun getauxval(type: CUlong): CUlong =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(type.toLong()).toULong()

public actual fun adjtimex(buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(buf.rawAddress)

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf.rawAddress)

public actual fun ntpGettime(buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(buf.rawAddress)

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId.toInt(), buf.rawAddress)

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: ULong, dirfd: CInt, path: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd, flags.toInt(), mask.toLong(), dirfd, path.rawAddress)

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun preadv64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun pwritev64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags.toInt())

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(s.rawAddress, len.toLong())
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(ptr.rawAddress, nmemb.toLong(), size.toLong()))

public actual fun ctermid(s: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuCtermid(s.rawAddress))

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(buf.rawAddress, sz)

public actual fun backtraceSymbols(buffer: ConstPtr, len: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer.rawAddress, len))

public actual fun backtraceSymbolsFd(buffer: ConstPtr, len: CInt, fd: CInt) {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer.rawAddress, len, fd)
}

public actual fun glob64(pattern: ConstPtr, flags: CInt, errfunc: Glob64ErrfuncCallback?, pglob: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGlob64(pattern.rawAddress, flags, 0L, pglob.rawAddress)

public actual fun globfree64(pglob: MutPtr) {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob.rawAddress)
}

public actual fun ptrace(request: CUint, vararg variadicArgs: Long): CLong =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPtrace(request.toInt(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun pthreadAttrGetaffinityNp(attr: ConstPtr, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr.rawAddress, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadAttrSetaffinityNp(attr: MutPtr, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr.rawAddress, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun getpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(which.toInt(), who.toInt())

public actual fun setpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT, prio: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(which.toInt(), who.toInt(), prio)

public actual fun pthreadRwlockattrGetkindNp(attr: ConstPtr, value: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr.rawAddress, value.rawAddress)

public actual fun pthreadRwlockattrSetkindNp(attr: MutPtr, value: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr.rawAddress, value)

public actual fun pthreadSigqueue(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt, value: Sigval): CInt =
    0

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread.toLong(), retval.rawAddress)

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread.toLong(), retval.rawAddress, abstime.rawAddress)

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo =
    io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo()

public actual fun mallinfo2(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2 =
    io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2()

public actual fun mallocStats() {
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMallocStats()
}

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(options, stream.rawAddress)

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr.rawAddress).toULong()

public actual fun getpwentR(pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun getgrentR(grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun fgetpwentR(stream: MutPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream.rawAddress, pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun fgetgrentR(stream: MutPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream.rawAddress, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun getnetentR(resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress, hErrnop.rawAddress)

public actual fun getnetbynameR(name: ConstPtr, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name.rawAddress, resultBuf.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress, hErrnop.rawAddress)

public actual fun getnetbyaddrR(net: UInt, type: CInt, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net.toInt(), type, resultBuf.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress, hErrnop.rawAddress)

public actual fun putpwent(p: ConstPtr, stream: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p.rawAddress, stream.rawAddress)

public actual fun putgrent(grp: ConstPtr, stream: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(grp.rawAddress, stream.rawAddress)

public actual fun sethostid(hostid: CLong): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuSethostid(hostid)

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(name.rawAddress, flags.toInt())

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMlock2(addr.rawAddress, len.toLong(), flags.toInt())

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname.rawAddress, mode)

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuEaccess(pathname.rawAddress, mode)

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(tm.rawAddress, buf.rawAddress))

public actual fun ctimeR(timep: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(timep.rawAddress, buf.rawAddress))

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuDirname(path.rawAddress))

public actual fun posixBasename(path: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(path.rawAddress))

public actual fun gnuBasename(path: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(path.rawAddress))

public actual fun dlmopen(lmid: LmidT, filename: ConstPtr, flag: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(lmid, filename.rawAddress, flag))

public actual fun dlinfo(handle: MutPtr, request: CInt, info: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(handle.rawAddress, request, info.rawAddress)

public actual fun dladdr1(addr: ConstPtr, info: MutPtr, extraInfo: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(addr.rawAddress, info.rawAddress, extraInfo.rawAddress, flags)

public actual fun dlvsym(handle: MutPtr, symbol: ConstPtr, version: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(handle.rawAddress, symbol.rawAddress, version.rawAddress))

public actual fun mallocTrim(pad: SizeT): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(pad.toLong())

public actual fun gnuGetLibcRelease(): ConstPtr =
    ConstPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease())

public actual fun gnuGetLibcVersion(): ConstPtr =
    ConstPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion())

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions.rawAddress, path.rawAddress)

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions.rawAddress, fd)

public actual fun posixSpawnFileActionsAddclosefromNp(actions: MutPtr, from: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions.rawAddress, from)

public actual fun posixSpawnFileActionsAddtcsetpgrpNp(actions: MutPtr, tcfd: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions.rawAddress, tcfd)

public actual fun getmntentR(stream: MutPtr, mntbuf: MutPtr, buf: MutPtr, buflen: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(stream.rawAddress, mntbuf.rawAddress, buf.rawAddress, buflen))

public actual fun execveat(dirfd: CInt, pathname: ConstPtr, argv: ConstPtr, envp: ConstPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuExecveat(dirfd, pathname.rawAddress, argv.rawAddress, envp.rawAddress, flags)

public actual fun closeRange(first: CUint, last: CUint, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(first.toInt(), last.toInt(), flags)

public actual fun mqNotify(mqdes: MqdT, sevp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes, sevp.rawAddress)

public actual fun epollPwait2(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd, events.rawAddress, maxevents, timeout.rawAddress, sigmask.rawAddress)

public actual fun mempcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    LibcUnixLinuxlikeLinuxGnuJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuTgkill(tgid, tid, sig)

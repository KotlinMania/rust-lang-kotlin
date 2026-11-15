@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuBacktrace
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuCloseRange
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuCtermid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuCtimeR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuDirname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuDladdr1
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuDlinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuDlmopen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuDlvsym
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuEaccess
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuEndutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuExecveat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetauxval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetentropy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetpt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetrandom
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetspentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetutxid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGetutxline
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGlob64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMallocStats
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMallopt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMempcpy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMlock2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuMqNotify
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPreadv2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPtrace
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPutgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPutpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPututxline
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPwritev2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuQsortR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuReallocarray
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuRenameat2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSethostid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSetutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuTgkill
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuUtmpname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.cinterop.rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.Sigval
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.MqdT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual var ENVIRON: MutPtr
    get() = MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON()?.toLong() ?: 0L)
    set(value) {
        rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(value.rawAddress.toCPointer<ByteVar>())
    }

public actual fun fgetspentR(fp: MutPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(fp.rawAddress.toCPointer<ByteVar>(), spbuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, spbufp.rawAddress.toCPointer<ByteVar>())

public actual fun sgetspentR(s: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(s.rawAddress.toCPointer<ByteVar>(), spbuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, spbufp.rawAddress.toCPointer<ByteVar>())

public actual fun getspentR(spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(spbuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, spbufp.rawAddress.toCPointer<ByteVar>())

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, compar: QsortRComparCallback?, arg: MutPtr) {
    rustlangLibcUnixLinuxlikeLinuxGnuQsortR(base.rawAddress.toCPointer<ByteVar>(), num, size, 0L.toCPointer<ByteVar>(), arg.rawAddress.toCPointer<ByteVar>())
}

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(sockfd, msgvec.rawAddress.toCPointer<ByteVar>(), vlen, flags)

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(sockfd, msgvec.rawAddress.toCPointer<ByteVar>(), vlen, flags, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun getrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit64(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun getrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit(resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, rlim: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun prlimit(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(pid, resource, newLimit.rawAddress.toCPointer<ByteVar>(), oldLimit.rawAddress.toCPointer<ByteVar>())

public actual fun prlimit64(pid: PidT, resource: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(pid, resource, newLimit.rawAddress.toCPointer<ByteVar>(), oldLimit.rawAddress.toCPointer<ByteVar>())

public actual fun utmpname(file: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(file.rawAddress.toCPointer<ByteVar>())

public actual fun utmpxname(file: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(file.rawAddress.toCPointer<ByteVar>())

public actual fun getutxent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGetutxent()?.toLong() ?: 0L)

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuPututxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun setutxent() {
    rustlangLibcUnixLinuxlikeLinuxGnuSetutxent()
}

public actual fun endutxent() {
    rustlangLibcUnixLinuxlikeLinuxGnuEndutxent()
}

public actual fun getpt(): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetpt()

public actual fun mallopt(param: CInt, value: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMallopt(param, value)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(tp.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(buf.rawAddress.toCPointer<ByteVar>(), buflen, flags)

public actual fun getauxval(type: CUlong): CUlong =
    rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(type)

public actual fun adjtimex(buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(buf.rawAddress.toCPointer<ByteVar>())

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(buf.rawAddress.toCPointer<ByteVar>())

public actual fun ntpGettime(buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(buf.rawAddress.toCPointer<ByteVar>())

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(clkId, buf.rawAddress.toCPointer<ByteVar>())

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: ULong, dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(fd, flags, mask, dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun preadv64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun pwritev64v2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(s.rawAddress.toCPointer<ByteVar>(), len)
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(ptr.rawAddress.toCPointer<ByteVar>(), nmemb, size)?.toLong() ?: 0L)

public actual fun ctermid(s: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuCtermid(s.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(buf.rawAddress.toCPointer<ByteVar>(), sz)

public actual fun backtraceSymbols(buffer: ConstPtr, len: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(buffer.rawAddress.toCPointer<ByteVar>(), len)?.toLong() ?: 0L)

public actual fun backtraceSymbolsFd(buffer: ConstPtr, len: CInt, fd: CInt) {
    rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(buffer.rawAddress.toCPointer<ByteVar>(), len, fd)
}

public actual fun glob64(pattern: ConstPtr, flags: CInt, errfunc: Glob64ErrfuncCallback?, pglob: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGlob64(pattern.rawAddress.toCPointer<ByteVar>(), flags, 0L.toCPointer<ByteVar>(), pglob.rawAddress.toCPointer<ByteVar>())

public actual fun globfree64(pglob: MutPtr) {
    rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(pglob.rawAddress.toCPointer<ByteVar>())
}

public actual fun ptrace(request: CUint, vararg variadicArgs: Long): CLong =
    rustlangLibcUnixLinuxlikeLinuxGnuPtrace(request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun pthreadAttrGetaffinityNp(attr: ConstPtr, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(attr.rawAddress.toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetaffinityNp(attr: MutPtr, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(attr.rawAddress.toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun getpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(which, who)

public actual fun setpriority(which: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PriorityWhichT, who: IdT, prio: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(which, who, prio)

public actual fun pthreadRwlockattrGetkindNp(attr: ConstPtr, value: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(attr.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrSetkindNp(attr: MutPtr, value: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(attr.rawAddress.toCPointer<ByteVar>(), value)

public actual fun pthreadSigqueue(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt, value: Sigval): CInt =
    0

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(thread, retval.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(thread, retval.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo =
    io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo()

public actual fun mallinfo2(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2 =
    io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Mallinfo2()

public actual fun mallocStats() {
    rustlangLibcUnixLinuxlikeLinuxGnuMallocStats()
}

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(options, stream.rawAddress.toCPointer<ByteVar>())

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(ptr.rawAddress.toCPointer<ByteVar>())

public actual fun getpwentR(pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun getgrentR(grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun fgetpwentR(stream: MutPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(stream.rawAddress.toCPointer<ByteVar>(), pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun fgetgrentR(stream: MutPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(stream.rawAddress.toCPointer<ByteVar>(), grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun getnetentR(resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(resultBuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>(), hErrnop.rawAddress.toCPointer<ByteVar>())

public actual fun getnetbynameR(name: ConstPtr, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(name.rawAddress.toCPointer<ByteVar>(), resultBuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>(), hErrnop.rawAddress.toCPointer<ByteVar>())

public actual fun getnetbyaddrR(net: UInt, type: CInt, resultBuf: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr, hErrnop: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(net, type, resultBuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>(), hErrnop.rawAddress.toCPointer<ByteVar>())

public actual fun putpwent(p: ConstPtr, stream: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(p.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun putgrent(grp: ConstPtr, stream: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(grp.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun sethostid(hostid: CLong): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuSethostid(hostid)

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(name.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMlock2(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuEaccess(pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(tm.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun ctimeR(timep: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(timep.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuDirname(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun posixBasename(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun gnuBasename(path: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun dlmopen(lmid: LmidT, filename: ConstPtr, flag: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(lmid, filename.rawAddress.toCPointer<ByteVar>(), flag)?.toLong() ?: 0L)

public actual fun dlinfo(handle: MutPtr, request: CInt, info: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(handle.rawAddress.toCPointer<ByteVar>(), request, info.rawAddress.toCPointer<ByteVar>())

public actual fun dladdr1(addr: ConstPtr, info: MutPtr, extraInfo: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(addr.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>(), extraInfo.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun dlvsym(handle: MutPtr, symbol: ConstPtr, version: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(handle.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>(), version.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mallocTrim(pad: SizeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(pad)

public actual fun gnuGetLibcRelease(): ConstPtr =
    ConstPtr(rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease()?.toLong() ?: 0L)

public actual fun gnuGetLibcVersion(): ConstPtr =
    ConstPtr(rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion()?.toLong() ?: 0L)

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun posixSpawnFileActionsAddclosefromNp(actions: MutPtr, from: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(actions.rawAddress.toCPointer<ByteVar>(), from)

public actual fun posixSpawnFileActionsAddtcsetpgrpNp(actions: MutPtr, tcfd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(actions.rawAddress.toCPointer<ByteVar>(), tcfd)

public actual fun getmntentR(stream: MutPtr, mntbuf: MutPtr, buf: MutPtr, buflen: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(stream.rawAddress.toCPointer<ByteVar>(), mntbuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen)?.toLong() ?: 0L)

public actual fun execveat(dirfd: CInt, pathname: ConstPtr, argv: ConstPtr, envp: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuExecveat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun closeRange(first: CUint, last: CUint, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(first, last, flags)

public actual fun mqNotify(mqdes: MqdT, sevp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(mqdes, sevp.rawAddress.toCPointer<ByteVar>())

public actual fun epollPwait2(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(epfd, events.rawAddress.toCPointer<ByteVar>(), maxevents, timeout.rawAddress.toCPointer<ByteVar>(), sigmask.rawAddress.toCPointer<ByteVar>())

public actual fun mempcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxGnuTgkill(tgid, tid, sig)

@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedMount
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRand
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime
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
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun iopl(level: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level)

public actual fun ioperm(from: CUlong, num: CUlong, turnOn: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from, num, turnOn)

public actual fun aioRead(aiocbp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(op, aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioError(aiocbp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(aiocbList.rawAddress.toCPointer<ByteVar>(), nitems, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(fd, aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(mode, aiocbList.rawAddress.toCPointer<ByteVar>(), nitems, sevp.rawAddress.toCPointer<ByteVar>())

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(sa.rawAddress.toCPointer<ByteVar>(), salen, host.rawAddress.toCPointer<ByteVar>(), hostlen, serv.rawAddress.toCPointer<ByteVar>(), servlen, flags)

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(loadavg.rawAddress.toCPointer<ByteVar>(), nelem)

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(pid, localIov.rawAddress.toCPointer<ByteVar>(), liovcnt, remoteIov.rawAddress.toCPointer<ByteVar>(), riovcnt, flags)

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(pid, localIov.rawAddress.toCPointer<ByteVar>(), liovcnt, remoteIov.rawAddress.toCPointer<ByteVar>(), riovcnt, flags)

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(fd, times.rawAddress.toCPointer<ByteVar>())

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(errnum, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun abs(i: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i)

public actual fun labs(i: CLong): CLong =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i)

public actual fun rand(): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRand()

public actual fun srand(seed: CUint) {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed)
}

public actual fun drand48(): CDouble =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48()

public actual fun erand48(xseed: MutPtr): CDouble =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun lrand48(): CLong =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48()

public actual fun nrand48(xseed: MutPtr): CLong =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun jrand48(xseed: MutPtr): CLong =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun srand48(seed: CLong) {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed)
}

public actual fun setpwent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()
}

public actual fun endpwent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()
}

public actual fun getpwent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent()?.toLong() ?: 0L)

public actual fun setgrent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()
}

public actual fun endgrent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent()?.toLong() ?: 0L)

public actual fun setspent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()
}

public actual fun endspent() {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()
}

public actual fun getspent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent()?.toLong() ?: 0L)

public actual fun getspnam(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key, size, shmflg)

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(shmid, shmaddr.rawAddress.toCPointer<ByteVar>(), shmflg)?.toLong() ?: 0L)

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(shmaddr.rawAddress.toCPointer<ByteVar>())

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(shmid, cmd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(addr.rawAddress.toCPointer<ByteVar>(), len, prot)

public actual fun errnoLocation(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation()?.toLong() ?: 0L)

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(pattern.rawAddress.toCPointer<ByteVar>(), flags, 0L.toCPointer<ByteVar>(), pglob.rawAddress.toCPointer<ByteVar>())

public actual fun globfree(pglob: MutPtr) {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(pglob.rawAddress.toCPointer<ByteVar>())
}

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(dirp.rawAddress.toCPointer<ByteVar>(), loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(addr.rawAddress.toCPointer<ByteVar>(), len, advice)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())

public actual fun nlLanginfo(item: NlItem): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(item)?.toLong() ?: 0L)

public actual fun nlLanginfoL(item: NlItem, locale: LocaleT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(item, locale.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(pid, cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy)

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(tv.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(sem.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(sem.rawAddress.toCPointer<ByteVar>(), sval.rawAddress.toCPointer<ByteVar>())

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedMount(src.rawAddress.toCPointer<ByteVar>(), target.rawAddress.toCPointer<ByteVar>(), fstype.rawAddress.toCPointer<ByteVar>(), flags, data.rawAddress.toCPointer<ByteVar>())

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(option, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(fds.rawAddress.toCPointer<ByteVar>(), nfds, timeout.rawAddress.toCPointer<ByteVar>(), sigmask.rawAddress.toCPointer<ByteVar>())

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy)

public actual fun sysinfo(info: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(info.rawAddress.toCPointer<ByteVar>())

public actual fun sigsuspend(mask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(mask.rawAddress.toCPointer<ByteVar>())

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(gid, grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun semClose(sem: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(sem.rawAddress.toCPointer<ByteVar>())

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(name.rawAddress.toCPointer<ByteVar>(), grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(user.rawAddress.toCPointer<ByteVar>(), group)

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)?.toLong() ?: 0L)

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun semUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir, noclose)

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(set.rawAddress.toCPointer<ByteVar>(), sig.rawAddress.toCPointer<ByteVar>())

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(gid)?.toLong() ?: 0L)

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(command.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(0L.toCPointer<ByteVar>(), data.rawAddress.toCPointer<ByteVar>())

public actual fun setmntent(filename: ConstPtr, ty: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(filename.rawAddress.toCPointer<ByteVar>(), ty.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getmntent(stream: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(stream.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun addmntent(stream: MutPtr, mnt: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(stream.rawAddress.toCPointer<ByteVar>(), mnt.rawAddress.toCPointer<ByteVar>())

public actual fun endmntent(streamp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(streamp.rawAddress.toCPointer<ByteVar>())

public actual fun hasmntopt(mnt: ConstPtr, opt: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(mnt.rawAddress.toCPointer<ByteVar>(), opt.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(preg.rawAddress.toCPointer<ByteVar>(), pattern.rawAddress.toCPointer<ByteVar>(), cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(preg.rawAddress.toCPointer<ByteVar>(), input.rawAddress.toCPointer<ByteVar>(), nmatch, pmatch.rawAddress.toCPointer<ByteVar>(), eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(errcode, preg.rawAddress.toCPointer<ByteVar>(), errbuf.rawAddress.toCPointer<ByteVar>(), errbufSize)

public actual fun regfree(preg: MutPtr) {
    rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(preg.rawAddress.toCPointer<ByteVar>())
}

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(tocode.rawAddress.toCPointer<ByteVar>(), fromcode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(cd.rawAddress.toCPointer<ByteVar>(), inbuf.rawAddress.toCPointer<ByteVar>(), inbytesleft.rawAddress.toCPointer<ByteVar>(), outbuf.rawAddress.toCPointer<ByteVar>(), outbytesleft.rawAddress.toCPointer<ByteVar>())

public actual fun iconvClose(cd: IconvT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(cd.rawAddress.toCPointer<ByteVar>())

public actual fun gettid(): PidT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid()

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(clockid, sevp.rawAddress.toCPointer<ByteVar>(), timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerDelete(timerid: TimerT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(timerid.rawAddress.toCPointer<ByteVar>(), currValue.rawAddress.toCPointer<ByteVar>())

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(timerid.rawAddress.toCPointer<ByteVar>(), flags, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(haystack.rawAddress.toCPointer<ByteVar>(), haystacklen, needle.rawAddress.toCPointer<ByteVar>(), needlelen)?.toLong() ?: 0L)

public actual fun schedGetcpu(): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu()

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(argc, argv.rawAddress.toCPointer<ByteVar>(), optstring.rawAddress.toCPointer<ByteVar>(), longopts.rawAddress.toCPointer<ByteVar>(), longindex.rawAddress.toCPointer<ByteVar>())

public actual fun copyFileRange(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(fdIn, offIn.rawAddress.toCPointer<ByteVar>(), fdOut, offOut.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun freopen64(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun fsetpos64(stream: MutPtr, ptr: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun ftello64(stream: MutPtr): Off64T =
    rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(stream.rawAddress.toCPointer<ByteVar>())

@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslBasename
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslCtermid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslDirname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslEaccess
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslEndutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetauxval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetrandom
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetutxid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslGetutxline
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslMlock2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPreadv2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPtrace
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPututxline
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslPwritev2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslReallocarray
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslRenameat2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslSetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslSetutxent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.cinterop.rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(pid, resource, newLimit.rawAddress.toCPointer<ByteVar>(), oldLimit.rawAddress.toCPointer<ByteVar>())

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(tp.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    rustlangLibcUnixLinuxlikeLinuxMuslPtrace(request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getpriority(which: CInt, who: IdT): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which, who)

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which, who, prio)

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: CUlonglong, dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(fd, flags, mask, dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun getauxval(type: CUlong): CUlong =
    rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type)

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(s.rawAddress.toCPointer<ByteVar>(), len)
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(ptr.rawAddress.toCPointer<ByteVar>(), nmemb, size)?.toLong() ?: 0L)

public actual fun adjtimex(buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(buf.rawAddress.toCPointer<ByteVar>())

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(clkId, buf.rawAddress.toCPointer<ByteVar>())

public actual fun ctermid(s: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslCtermid(s.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(name.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslMlock2(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(ptr.rawAddress.toCPointer<ByteVar>())

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslEaccess(pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(tm.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslDirname(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun basename(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslBasename(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(buf.rawAddress.toCPointer<ByteVar>(), buflen, flags)

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(actions.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun getutxent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslGetutxent()?.toLong() ?: 0L)

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMuslPututxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun setutxent() {
    rustlangLibcUnixLinuxlikeLinuxMuslSetutxent()
}

public actual fun endutxent() {
    rustlangLibcUnixLinuxlikeLinuxMuslEndutxent()
}

public actual fun utmpxname(file: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(file.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(thread.rawAddress.toCPointer<ByteVar>(), retval.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(thread.rawAddress.toCPointer<ByteVar>(), retval.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

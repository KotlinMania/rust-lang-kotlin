@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPrlimit")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPtrace")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMuslGetpriority")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which: Int, who: Int): Int

public actual fun getpriority(which: CInt, who: IdT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which, who.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMuslSetpriority")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which: Int, who: Int, prio: Int): Int

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which, who.toInt(), prio).toInt()

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: CUlonglong, dirfd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPreadv2")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPwritev2")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMuslGetauxval")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type: Long): Long

public actual fun getauxval(type: CUlong): CUlong =
    wasiRustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type.toLong()).toULong()

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslRenameat2")

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero")
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslReallocarray")

public actual fun adjtimex(buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex")

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime")

public actual fun ctermid(s: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslCtermid")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslMlock2")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize")

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess")

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslEaccess")

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR")

public actual fun dirname(path: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslDirname")

public actual fun basename(path: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslBasename")

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGetrandom")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp")

public actual fun getutxent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGetutxent")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGetutxid")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslGetutxline")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPututxline")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMuslSetutxent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMuslSetutxent(): Unit

public actual fun setutxent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxMuslSetutxent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMuslEndutxent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMuslEndutxent(): Unit

public actual fun endutxent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxMuslEndutxent()

public actual fun utmpxname(file: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname")

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp")

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp")

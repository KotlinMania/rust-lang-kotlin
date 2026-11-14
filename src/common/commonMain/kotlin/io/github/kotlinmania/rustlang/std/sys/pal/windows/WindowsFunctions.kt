package io.github.kotlinmania.rustlang.std.sys.pal.windows

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.rustSaturatingMul
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.wideStr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INSUFFICIENT_BUFFER
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.INFINITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SetLastError
import io.github.kotlinmania.rustlang.std.sys.thread.windows.setNameWide

// SAFETY: must be called only once during runtime initialization.
// NOTE: this is not guaranteed to run, for example when Rust code is called externally.
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {
    run {
        io.github.kotlinmania.rustlang.std.sys.pal.windows.stackoverflowuwp.init()
        // Normally, `thread.spawn` will call `setName` but since this thread already
        // exists, we have to call it ourselves.
        setNameWide(wideStr("main"))
    }
}

// SAFETY: must be called only once during runtime cleanup.
// NOTE: this is not guaranteed to run, for example when the program aborts, and
// is not guaranteed to run on the main thread (#161018 was caused by that
// mistaken assumption).
public fun cleanup() {
    io.github.kotlinmania.rustlang.std.sys.pal.windows.winsock.cleanup()
}

public fun unrolledFindU16s(needle: UShort, haystack: List<UShort>): Int? {
    val ptr = haystack.asPtr()
    var start = haystack
    // For performance reasons unfold the loop eight times.
    while (start.size >= 8) {
        if (start[0] == needle) {
            return (((constPtrFromRustCast(start[0]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[1] == needle) {
            return (((constPtrFromRustCast(start[1]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[2] == needle) {
            return (((constPtrFromRustCast(start[2]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[3] == needle) {
            return (((constPtrFromRustCast(start[3]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[4] == needle) {
            return (((constPtrFromRustCast(start[4]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[5] == needle) {
            return (((constPtrFromRustCast(start[5]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[6] == needle) {
            return (((constPtrFromRustCast(start[6]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        if (start[7] == needle) {
            return (((constPtrFromRustCast(start[7]))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
        start = ((start).slice(8 until start.size))
    }
    for (c in start) {
        if (c == needle) {
            return (((constPtrFromRustCast(c))).rawAddress.toInt() - (ptr).rawAddress.toInt()) / 2
        }
    }
    return null
}

public fun toU16s(s: OsStr): Result<List<UShort>, Error> {
    fun inner(s1: OsStr): Result<List<UShort>, Error> {
        if (s1.asEncodedBytes().contains(0)) {
            return Result.Err(constError(ErrorKind.InvalidInput, "strings passed to WinAPI cannot contain NULs"))
        }
        // Most paths are ASCII, so reserve capacity for as much as there are bytes
        // in the OsStr plus one for the null-terminating character. We are not
        // wasting bytes here as paths created by this function are primarily used
        // in an ephemeral fashion.
        val maybeResult = ArrayList<UShort>(s1.len() + 1)
        maybeResult.extend(s1.encodeWide())
        maybeResult.add(0)
        return Result.Ok(maybeResult)
    }
    return inner(s)
}

// Many Windows APIs follow a pattern of where we hand a buffer and then they
// will report back to us how large the buffer should be or how many bytes
// currently reside in the buffer. This function is an abstraction over these
// functions by making them easier to call.
//
// The first callback, `f1`, is passed a (pointer, len) pair which can be
// passed to a syscall. The `ptr` is valid for `len` items (u16 in this case).
// The closure is expected to:
// - On success, return the actual length of the written data *without* the null terminator.
// This can be 0. In this case the lastError must be left unchanged.
// - On insufficient buffer space,
// - either return the required length *with* the null terminator,
// - or set the last-error to ERROR_INSUFFICIENT_BUFFER and return `len`.
// - On other failure, return 0 and set lastError.
//
// This is how most but not all syscalls indicate the required buffer space.
// Other syscalls may need translation to match this protocol.
//
// Once the syscall has completed (errors bail out early) the second closure is
// passed the data which has been read from the syscall. The return value
// from this closure is then the return value of the function.
public fun <T> fillUtf16Buf(f1: (MutPtr, UInt) -> UInt, f2: (List<UShort>) -> T): Result<T, Error> {
    // Start off with a stack buf but then spill over to the heap if we end up
    // needing more space.
    //
    // This initial size also works around `GetFullPathNameW` returning
    // incorrect size hints for some short paths:
    // https://github.com/dylni/normpath/issues/5
    val stackBuf: List<MaybeUninit<UShort>>  = List(512) { MaybeUninit.uninit(PrimitiveLayouts.U16) }
    val heapBuf: List<MaybeUninit<UShort>>  = mutableListOf()
    run {
        var n = stackBuf.size
        while (true) {
            val buf =
                if (n <= stackBuf.size) {
                    stackBuf
                } else {
                    val extra = n - heapBuf.size
                    heapBuf.reserve(extra)
                    // We used `reserve` and not `reserveExact`, so in theory we
                    // may have gotten more than requested. If so, we'd like to use
                    // it... so long as we won't cause overflow.
                    n = heapBuf.capacity().min(UInt.MAX_VALUE.toInt())
                    // Safety: MaybeUninit<u16> does not need initialization
                    heapBuf.setLen(n)
                    heapBuf
                }
            // This function is typically called on windows API functions which
            // will return the correct length of the string, but these functions
            // also return the `0` on error. In some cases, however, the
            // returned "correct length" may actually be 0!
            //
            // To handle this case we call `SetLastError` to reset it to 0 and
            // then check it again if we get the "0 error value". If the "last
            // error" is still 0 then we interpret it as a 0 length buffer and
            // not an actual error.
            SetLastError(0)
            val k =
                (run {
                    val matchVal = f1(MutPtr((buf.asMutPtr()).rawAddress), n.toUInt())
                    when {
                        matchVal == 0 && getLastError().code == 0 -> 0
                        matchVal == 0 -> { return Result.Err(io.github.kotlinmania.rustlang.std.io.Error.lastOsError()) }
                        else -> {
                            val n1 = matchVal
                            n1
                        }
                    }
                }).toInt()
            if (k == n && getLastError().code == ERROR_INSUFFICIENT_BUFFER) {
                n = rustSaturatingMul(n, 2).min(UInt.MAX_VALUE.toInt())
            } else if (k > n) {
                n = k
            } else if (k == n) {
                // It is impossible to reach this point.
                // On success, k is the returned string length excluding the null.
                // On failure, k is the required buffer length including the null.
                // Therefore k never equals n.
                error("unreachable")
            } else {
                // Safety: First `k` values are initialized.
                val slice: List<UShort> = ((buf).slice(0 until k)).assumeInitRef()
                return Result.Ok(f2(slice))
            }
        }
    }
}

public fun os2path(s: List<UShort>): PathBuf = PathBuf.from(OsString.fromWide(s))

public fun truncateUtf16AtNul(v: List<UShort>): List<UShort> =
    when (val matchVal = unrolledFindU16s(0u, v)) {
        null -> v
        // don't include the 0
        else -> {
            val i = matchVal
            ((v).slice(0 until i))
        }
    }

public fun ensureNoNuls(s: OsStr): Result<OsStr, Error> =
    if (s.asEncodedBytes().contains(0)) {
        Result.Err(constError(ErrorKind.InvalidInput, "nul byte found in provided data"))
    } else {
        Result.Ok(s)
    }

public fun <I : IsZero> cvt(i: I): Result<I, Error> =
    if (i.isZero()) {
        Result.Err(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
    } else {
        Result.Ok(i)
    }

public fun <I : IsZero> cvtNz(i: I): Result<Unit, Error> =
    if (i.isZero()) {
        Result.Ok(Unit)
    } else {
        Result.Err(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
    }

public fun dur2timeout(dur: Duration): UInt {
    // Note that a duration is a (u64, u32) (seconds, nanoseconds) pair, and the
    // timeouts in windows APIs are typically u32 milliseconds. To translate, we
    // have two pieces to take care of:
    //
    // * Nanosecond precision is rounded up
    // * Greater than u32.Max milliseconds (50 days) is rounded up to INFINITE
    // (never time out).
    return (dur.asSecs().checkedMul(1000)?.let({ ms -> ms.checkedAdd((dur.subsecNanos().toULong()) / 1000000uL) })?.let({ ms -> ms.checkedAdd(if (dur.subsecNanos() % 1000000 > 0) {
        1
    } else {
        0
    }) })?.let({ ms -> if (ms > UInt.MAX_VALUE.toULong()) {
        INFINITE
    } else {
        ms.toUInt()
    } }) ?: INFINITE)
}

// inline assembly does not work in Miri

/**
 * Use `__fastfail` to abort the process
 *
 * In Windows 8 and later, this will terminate the process immediately without
 * running any in-process exception handlers. In earlier versions of Windows,
 * this sequence of instructions will be treated as an access violation, which
 * will still terminate the process but might run some exception handlers.
 *
 * <https://docs.microsoft.com/en-us/cpp/intrinsics/fastfail>
 */
public fun abortInternal(): Nothing =
    run {
        Unit
    }

public fun <T> Result<T, WinError>.ioResult(): Result<T, Error> = this.mapErr({ e -> io.github.kotlinmania.rustlang.std.io.Error.fromRawOsError(e.code) })

public fun Byte.isZero(): Boolean = (this == 0.toByte()).toUByte()

public fun Short.isZero(): Boolean = (this == 0.toShort()).toUByte()

public fun Int.isZero(): Boolean = (this == 0).toUByte()

public fun Long.isZero(): Boolean = (this == 0L).toUByte()

public fun UByte.isZero(): Boolean = this == 0u.toUByte()

public fun UShort.isZero(): Boolean = (this == 0u.toUShort()).toUByte()

public fun UInt.isZero(): Boolean = (this == 0u).toUByte()

public fun ULong.isZero(): Boolean = (this == 0uL).toUByte()

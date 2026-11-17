package io.github.kotlinmania.rustlang.std.sys.pal.unix

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.libc.CInt
import io.github.kotlinmania.rustlang.libc.Poll
import io.github.kotlinmania.rustlang.libc.Signal
import io.github.kotlinmania.rustlang.libc.abort
import io.github.kotlinmania.rustlang.libc.dup
import io.github.kotlinmania.rustlang.libc.fcntl
import io.github.kotlinmania.rustlang.libc.open
import io.github.kotlinmania.rustlang.libc.unix.Pollfd
import io.github.kotlinmania.rustlang.libc.unix.SIG_DFL
import io.github.kotlinmania.rustlang.libc.unix.SIG_ERR
import io.github.kotlinmania.rustlang.libc.unix.SIG_IGN
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGPIPE
import io.github.kotlinmania.rustlang.libc.wasi.EAGAIN
import io.github.kotlinmania.rustlang.libc.wasi.EBADF
import io.github.kotlinmania.rustlang.libc.wasi.EINTR
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ENOMEM
import io.github.kotlinmania.rustlang.libc.wasi.ENOSYS
import io.github.kotlinmania.rustlang.libc.wasi.F_GETFD
import io.github.kotlinmania.rustlang.libc.wasi.NfdsT
import io.github.kotlinmania.rustlang.libc.wasi.O_RDWR
import io.github.kotlinmania.rustlang.libc.wasi.POLLNVAL
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.io.Errno
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.thread.unix.setName

// SAFETY: must be called only once during runtime initialization.
// NOTE: this is not guaranteed to run, for example when Rust code is called externally.
// See `fun init()` in `library / std / src / rt.rs` for docs on `sigpipe`.
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {
    fun sanitizeStandardFds() {
        var openedDevnull = -1
        var openDevnull =
            closure@{
    
    
                if (openedDevnull != -1) {
                    if (dup(openedDevnull) != -1) {
                        return@closure
                    }
                }
                openedDevnull = open("/dev/null".asPtr(), O_RDWR, 0)
                if (openedDevnull == -1) {
                    // If the stream is closed but we failed to reopen it, abort the
                    // process. Otherwise we wouldn't preserve the safety of
                    // operations on the corresponding Rust object Stdin, Stdout, or
                    // Stderr.
                    abort()
                }
            }
        // fast path with a single syscall for systems with poll()
        run {
            val localStorageL92C12 = AddressableArrayLocal.fromList(emptyList(), UnresolvedTypeLayout<Pollfd>())
            val pfds by localStorageL92C12
            pfds[0].fd = 0
            pfds[0].events = 0
            pfds[1].fd = 1
            pfds[1].events = 0
            pfds[2].fd = 2
            pfds[2].events = 0
            while (Poll(localStorageL92C12.elementMutPtr(0), ((pfds.size as Any?) as NfdsT), 0) == -1) {
                when (Errno()) {
                    EINTR -> { continue }
                    ENOSYS -> {
                        // Not all configurations of Unikraft enable `LIBPOSIX_EVENT`.
                        break
                    }
                    EINVAL, EAGAIN, ENOMEM -> {
                        // RLIMIT_NOFILE or temporary allocation failures
                        // may be preventing use of poll(), fall back to fcntl
                        break
                    }
                    else -> abort()
                }
            }
            for (pfd in pfds) {
                if (pfd.revents and POLLNVAL == 0) {
                    continue
                }
                openDevnull()
            }
            return
        }
        // fallback in case poll isn't available or limited by RLIMIT_NOFILE
        run {
            for (fd in 0 until 3) {
                if (fcntl(fd, F_GETFD) == -1 && Errno() == EBADF) {
                    openDevnull()
                }
            }
        }
    }
    fun resetSigpipe(sigpipe1: UByte) {
        run {
            // We don't want to add this as a public type to std, nor do we
            // want to `include` a file from the compiler (which would break
            // Miri and xargo for example), so we choose to duplicate these
            // constants from `compiler / rustcSession / src / config / sigpipe.rs`.
            // See the other file for docs. NOTE: Make sure to keep them in
            // sync!
            val DEFAULT: UByte = 0u

            val INHERIT: UByte = 1u

            val SIG_IGN: UByte = 2u

            val SIG_DFL: UByte = 3u
            val (onBrokenPipeUsed, handler) =
                when (sigpipe1) {
                    default -> Pair(false, SIG_IGN)
                    inherit -> Pair(true, null)
                    sigIgn -> Pair(true, SIG_IGN)
                    sigDfl -> Pair(true, SIG_DFL)
                    else -> error("unreachable")
                }
            if (onBrokenPipeUsed) {
                ON_BROKEN_PIPE_USED.store(true, Ordering.Relaxed)
            }
            if (handler != null) {
                val handler1 = handler
                if (!Signal(SIGPIPE, handler1) != SIG_ERR) {
                    run {
                        when (val matchVal = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal
                                Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: signal (libc :: SIGPIPE , handler) != libc :: SIG_ERR").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("assertion failed: signal (libc :: SIGPIPE , handler) != libc :: SIG_ERR").toString() + ", aborting\n"
                        }
                        io.github.kotlinmania.rustlang.std.process.abort()
                    }
                }
            }
        }
    }
    // The standard streams might be closed on application startup. To prevent
    // std.io::(stdin, stdout,stderr) objects from using other unrelated file
    // resources opened later, we reopen standards streams when they are closed.
    sanitizeStandardFds()
    // By default, some platforms will send a *signal* when an EPIPE error
    // would otherwise be delivered. This runtime doesn't install a SIGPIPE
    // handler, causing it to kill the program, which isn't exactly what we
    // want!
    //
    // Hence, we set SIGPIPE to ignore when the program starts up in order
    // to prevent this problem. Use `- Zon - broken - pipe = ...` to alter this
    // behavior.
    resetSigpipe(sigpipe)
    io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.init()
    io.github.kotlinmania.rustlang.std.sys.args.unix.init(argc, argv)
    // Normally, `thread.spawn` will call `Thread.setName` but since this thread
    // already exists, we have to call it ourselves. We only do this on Apple targets
    // because some unix-like operating systems such as Linux share process-id and
    // thread-id for the main thread and so renaming the main thread will rename the
    // process and we only want to enable this on platforms we've tested.
    if (false) {
        setName("main")
    }
}

public fun onBrokenPipeUsed(): Boolean = ON_BROKEN_PIPE_USED.load(Ordering.Relaxed)

// SAFETY: must be called only once during runtime cleanup.
// NOTE: this is not guaranteed to run, for example when the program aborts, and
// is not guaranteed to run on the main thread (#161018 was caused by that
// mistaken assumption).
public fun cleanup() {}

/**
 * Converts native return values to Result using the *-1 means error is in `errno`*  convention.
 * Non-error values are `Ok`-wrapped.
 */
public fun <T : IsMinusOne> cvt(t: T): Result<T, Error> =
    if (t.isMinusOne()) {
        Result.Err<T, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
    } else {
        Result.Ok<T, Error>(t)
    }

/**
 * `- 1` → look at `errno` → retry on `EINTR`. Otherwise `Ok()`-wrap the closure return value.
 */
public fun <T> cvtR(f: () -> T): Result<T, Error> where T : IsMinusOne {
    while (true) {
        run {
            val matchVal = cvt(f())
            when {
                matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                    val e = matchVal.error
                }
                else -> {
                    val other = matchVal
                    return other
                }
            }
        }
    }
}

// Not used on all platforms.

/**
 * Zero means `Ok()`, all other values are treated as raw OS errors. Does not look at `errno`.
 */
public fun cvtNz(error: CInt): Result<Unit, Error> =
    if (error == 0) {
        Result.Ok<Unit, Error>(Unit)
    } else {
        Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.fromRawOsError(error))
    }

// libc.abort() will run the SIGABRT handler.  That's fine because anyone who
// installs a SIGABRT handler already has to expect it to run in Very Bad
// situations (eg, malloc crashing).
//
// Current glibc's abort() function unblocks SIGABRT, raises SIGABRT, clears the
// SIGABRT handler and raises it again, and then starts to get creative.
//
// See the public documentation for `intrinsics.abortImmediate()` and
// `process.abort()` for further discussion.
//
// There is confusion about whether libc.abort() flushes stdio streams.
// libc.abort() is required by ISO C 99 (7.14.1.1p5) to be async-signal-safe,
// so flushing streams is at least extremely hard, if not entirely impossible.
//
// However, some versions of POSIX (eg IEEE Std 1003.1-2001) required abort to
// do so.  In 1003.1-2004 this was fixed.
//
// glibc's implementation did the flush, unsafely, before glibc commit
// 91e7cf982d01 `abort: Do not flush stdio streams[BZ # 15436]` by Florian
// Weimer.  According to glibc's NEWS:
//
// The abort function terminates the process immediately, without flushing
// stdio streams.  Previous glibc versions used to flush streams, resulting
// in deadlocks and further data corruption.  This change also affects
// process aborts as the result of assertion failures.
//
// This is an accurate description of the problem.  The only solution for
// program with nontrivial use of C stdio is a fixed libc - one which does not
// try to flush in abort - since even libc-internal errors, and assertion
// failures generated from C, will go via abort().
//
// On systems with old, buggy, libcs, the impact can be severe for a
// multithreaded C program.  It is much less severe for Rust, because Rust
// stdlib doesn't use libc stdio buffering.  In a typical Rust program, which
// does not use C stdio, even a buggy libc.abort() is, in fact, safe.
// even without panics, this helps for Miri backtraces
public fun abortInternal(): Nothing = abort()

public fun Byte.isMinusOne(): Boolean = (this == (-1).toByte()).toUByte()

public fun Short.isMinusOne(): Boolean = (this == (-1).toShort()).toUByte()

public fun Int.isMinusOne(): Boolean = (this == -1).toUByte()

public fun Long.isMinusOne(): Boolean = (this == -1L).toUByte()

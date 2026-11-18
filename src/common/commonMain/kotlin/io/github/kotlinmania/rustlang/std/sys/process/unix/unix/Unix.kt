// port-lint: source sys/process/unix/unix.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.process.unix.unix

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.mem.zeroed
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.constptr.read
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iterMut
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.slice.splitAt
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.str.splitAt
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.SysPidfdOpen
import io.github.kotlinmania.rustlang.libc.WCOREDUMP
import io.github.kotlinmania.rustlang.libc.WEXITSTATUS
import io.github.kotlinmania.rustlang.libc.WIFCONTINUED
import io.github.kotlinmania.rustlang.libc.WIFEXITED
import io.github.kotlinmania.rustlang.libc.WIFSIGNALED
import io.github.kotlinmania.rustlang.libc.WIFSTOPPED
import io.github.kotlinmania.rustlang.libc.WSTOPSIG
import io.github.kotlinmania.rustlang.libc.WTERMSIG
import io.github.kotlinmania.rustlang.libc.chdir
import io.github.kotlinmania.rustlang.libc.chroot
import io.github.kotlinmania.rustlang.libc.clockGetres
import io.github.kotlinmania.rustlang.libc.dup2
import io.github.kotlinmania.rustlang.libc.execvp
import io.github.kotlinmania.rustlang.libc.exit
import io.github.kotlinmania.rustlang.libc.getpid
import io.github.kotlinmania.rustlang.libc.kill
import io.github.kotlinmania.rustlang.libc.killpg
import io.github.kotlinmania.rustlang.libc.new.musl.signal.SiginfoT
import io.github.kotlinmania.rustlang.libc.recvmsg
import io.github.kotlinmania.rustlang.libc.sendmsg
import io.github.kotlinmania.rustlang.libc.setgid
import io.github.kotlinmania.rustlang.libc.setgroups
import io.github.kotlinmania.rustlang.libc.setpgid
import io.github.kotlinmania.rustlang.libc.setsid
import io.github.kotlinmania.rustlang.libc.setuid
import io.github.kotlinmania.rustlang.libc.syscall
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SIG_DFL
import io.github.kotlinmania.rustlang.libc.unix.Timespec
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_CONTINUED
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_DUMPED
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_EXITED
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_KILLED
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_STOPPED
import io.github.kotlinmania.rustlang.libc.unix.bsd.CLD_TRAPPED
import io.github.kotlinmania.rustlang.libc.unix.bsd.Cmsghdr
import io.github.kotlinmania.rustlang.libc.unix.bsd.Msghdr
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGBUS
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGCHLD
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGCONT
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGINFO
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGIO
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGPROF
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGSTOP
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGSYS
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGTSTP
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGTTIN
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGTTOU
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGURG
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGUSR1
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGUSR2
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGVTALRM
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGWINCH
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGXCPU
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGXFSZ
import io.github.kotlinmania.rustlang.libc.unix.bsd.WNOHANG
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SIGEMT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SOCK_SEQPACKET
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.Sigaction
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AF_UNIX
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_CMSG_CLOEXEC
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_EOR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGALRM
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGHUP
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGKILL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGPIPE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGQUIT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGTRAP
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.SIGPWR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.SIGSTKFLT
import io.github.kotlinmania.rustlang.libc.waitpid
import io.github.kotlinmania.rustlang.libc.wasi.CLOCK_MONOTONIC
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.EPERM
import io.github.kotlinmania.rustlang.libc.wasi.SOL_SOCKET
import io.github.kotlinmania.rustlang.libc.wasi.STDERR_FILENO
import io.github.kotlinmania.rustlang.libc.wasi.STDIN_FILENO
import io.github.kotlinmania.rustlang.libc.wasi.STDOUT_FILENO
import io.github.kotlinmania.rustlang.libc.windows.SIGABRT
import io.github.kotlinmania.rustlang.libc.windows.SIGFPE
import io.github.kotlinmania.rustlang.libc.windows.SIGILL
import io.github.kotlinmania.rustlang.libc.windows.SIGINT
import io.github.kotlinmania.rustlang.libc.windows.SIGSEGV
import io.github.kotlinmania.rustlang.libc.windows.SIGTERM
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.panic.alwaysAbort
import io.github.kotlinmania.rustlang.std.process.Child
import io.github.kotlinmania.rustlang.std.process.StdioPipes
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.lazylock.LazyLock
import io.github.kotlinmania.rustlang.std.sys.env.unix.envReadLock
import io.github.kotlinmania.rustlang.std.sys.env.unix.environ
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.std.sys.pal.unix.onBrokenPipeUsed
import io.github.kotlinmania.rustlang.std.sys.pipe.unix.pipe
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.ChildPipes
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.cstringarray.CStringArray
import io.github.kotlinmania.rustlang.std.sys.process.unix.pidfd.PidFd
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr, arg1: ConstPtr): CInt
}

public fun interface FnCallback1 {
    public operator fun invoke(): Duration
}

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

public typealias PosixSpawnAddChdirFn = FnCallback

// Get smallest amount of time we can sleep.
// Return a common value if it cannot be determined.
public fun getClockResolution(): Duration {
    var MIN_DELAY: LazyLock<Duration, FnCallback1> =
        LazyLock.new({
            var mindelay = Timespec(tvSec = 0L, tvNsec = 0)
            if (clockGetres(CLOCK_MONOTONIC, mindelay) == 0) {
                Duration.fromNanos(mindelay.tvNsec.toULong())
            } else {
                Duration.fromMillis(1)
            }
        })
    return MIN_DELAY
}

// Arbitrary minimum sleep duration for retrying fork/spawn
public val MIN_FORKSPAWN_SLEEP: Duration = Duration.fromNanos(1)

// Maximum duration of sleeping before giving up and returning an error
public val MAX_FORKSPAWN_SLEEP: Duration = Duration.fromMillis(1000)

// ///////////////////////////////////////////////////////////////////////////
// Command
// ///////////////////////////////////////////////////////////////////////////
public fun Command.spawn(default: Stdio, needsStdin: Boolean): Result<Pair<Process, StdioPipes>, Error> {
    val CLOEXEC_MSG_FOOTER: UByteArray = "NOEX".encodeToByteArray().toUByteArray()
    val envp = this.captureEnv()
    if (this.sawNul()) {
        return Result.Err<Pair<Process, StdioPipes>, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "nul byte found in provided data")))
    }
    val (ours, theirs) = when (val tryResult = this.setupIo(default, needsStdin)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
    when (val matchVal = when (val tryResult = this.posixSpawn(theirs, envp.asRef())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }) {
        null -> {}
        else -> {
            val ret = matchVal
            return Result.Ok<Pair<Process, StdioPipes>, Error>(Pair(ret, ours))
        }
    }
    val (input, output) = when (val tryResult = Socket.newPair(AF_UNIX, SOCK_SEQPACKET)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
    val (input1, output1) = when (val tryResult = pipe()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
    // Whatever happens after the fork is almost for sure going to touch or
    // look at the environment in one way or another (PATH in `execvp` or
    // accessing the `environ` pointer ourselves). Make sure no other thread
    // is accessing the environment when we do the fork itself.
    //
    // Note that as soon as we're done with the fork there's no need to hold
    // a lock any more because the parent won't do anything and the child is
    // in its own process. Thus the parent drops the lock guard immediately.
    // The child calls `mem.forget` to leak the lock, which is crucial because
    // releasing a lock is not async-signal-safe.
    val envLock = envReadLock()
    val pid = when (val tryResult = this.doFork()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
    if (pid == 0) {
        alwaysAbort()
        forget(envLock)
        // avoid non-async-signal-safe unlocking
        (input1).let { }
        if (this.getCreatePidfd()) {
            this.sendPidfd(output1)
        }
        val err = this.doExec(theirs, envp.asRef())
        val errno = err.rawOsError().unwrapOr(EINVAL).toUInt()
        val errno1 = run { val rawBits = errno; ubyteArrayOf((rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) }
        val bytes = ubyteArrayOf(errno1[0], errno1[1], errno1[2], errno1[3], CLOEXEC_MSG_FOOTER[0], CLOEXEC_MSG_FOOTER[1], CLOEXEC_MSG_FOOTER[2], CLOEXEC_MSG_FOOTER[3])
        if (!output1.write(bytes).isOk()) {
            run {
                when (val matchVal1 = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal1
                        out.writeFmt("fatal runtime error: " + ("assertion failed: output . write (& bytes) . is_ok ()").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: output . write (& bytes) . is_ok ()").toString() + ", aborting\n"
                }
                abort()
            }
        }
        exit(1)
    }
    (envLock).let { }
    (output1).let { }
    val pidfd =
        if (this.getCreatePidfd()) {
            this.recvPidfd(input1)
        } else {
            -1
        }
    // Safety: We obtained the pidfd (on Linux) using SOCK_SEQPACKET, so it's valid.
    val p = Process.new(pid, pidfd)
    val bytes1 = MutableList(8) { 0 }
    // loop to handle EINTR
    while (true) {
        run {
            val matchVal2 = input1.read(bytes1)
            when {
                matchVal2 is Result.Ok<*, *> && matchVal2.okValue() == 0 -> {
                    val okValueVal = matchVal2.okValue()
                    return Result.Ok<Pair<Process, StdioPipes>, Error>(Pair(p, ours))
                }
                matchVal2 is Result.Ok<*, *> && matchVal2.okValue() == 8 -> {
                    val okValueVal1 = matchVal2.okValue()
                    val (errno2, footer) = bytes1.splitAt(4)
                    check(CLOEXEC_MSG_FOOTER == footer)
                    val errno3 = run { val bytesTmp0 = errno2; ((bytesTmp0[0].toInt() shl 24) or (bytesTmp0[1].toInt() shl 16) or (bytesTmp0[2].toInt() shl 8) or bytesTmp0[3].toInt()) }
                    check(p.wait().isOk()) { "wait() should either return Ok or panic" }
                    return Result.Err<Pair<Process, StdioPipes>, Error>(Error.fromRawOsError(errno3))
                }
                matchVal2 is Result.Err<*, *> && matchVal2.errValue().isInterrupted() -> {
                    val e = matchVal2.errValue()
                }
                matchVal2 is Result.Err<*, *> -> {
                    val e1 = matchVal2.errValue()
                    check(p.wait().isOk()) { "wait() should either return Ok or panic" }
                    error("the CLOEXEC pipe failed: $e")
                }
                else -> {
                    // pipe I/O up to PIPE_BUF bytes should be atomic
                    // similarly SOCK_SEQPACKET messages should arrive whole
                    check(p.wait().isOk()) { "wait() should either return Ok or panic" }
                    error("short read on the CLOEXEC pipe")
                }
            }
        }
    }
}

// WatchOS and TVOS headers mark the `fork`/`exec *` functions with
// `__WATCHOS_PROHIBITED __TVOS_PROHIBITED`, and indicate that the
// `posixSpawn *` functions should be used instead. It isn't entirely clear
// what `PROHIBITED` means here (e.g. if calls to these functions are
// allowed to exist in dead code), but it sounds bad, so we go out of our
// way to avoid that all-together.
public val ERR_APPLE_TV_WATCH_NO_FORK_EXEC: Error = Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.Unsupported, message = "`fork`+`exec`-based process spawning is not supported on this target"))

public fun Command.doFork(): Result<PidT, Error> = Result.Err<PidT, Error>(Command.ERR_APPLE_TV_WATCH_NO_FORK_EXEC)

public fun Command.exec(default: Stdio): Error {
    val envp = this.captureEnv()
    if (this.sawNul()) {
        return Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "nul byte found in provided data"))
    }
    return run {
        val matchVal = this.setupIo(default, true)
        when (matchVal) { is Result.Ok<*, *> -> {
            val okVal = matchVal.okValue()
            val theirs = okVal.second
            run {
                // Similar to when forking, we want to ensure that access to
                // the environment is synchronized, so make sure to grab the
                // environment lock before we try to exec.
                val e = this.doExec(theirs, envp.asRef())
                e
            }
        }; is Result.Err<*, *> -> {
            val e1 = matchVal.errValue()
            e1
        } }
    }
}

// And at this point we've reached a special time in the life of the
// child. The child must now be considered hamstrung and unable to
// do anything other than syscalls really. Consider the following
// scenario:
//
// 1. Thread A of process 1 grabs the malloc() mutex
// 2. Thread B of process 1 forks(), creating thread C
// 3. Thread C of process 2 then attempts to malloc()
// 4. The memory of process 2 is the same as the memory of
// process 1, so the mutex is locked.
//
// This situation looks a lot like deadlock, right? It turns out
// that this is what pthreadAtfork() takes care of, which is
// presumably implemented across platforms. The first thing that
// threads to *before* forking is to do things like grab the malloc
// mutex, and then after the fork they unlock it.
//
// Despite this information, libnative's spawn has been witnessed to
// deadlock on both macOS and FreeBSD. I'm not entirely sure why, but
// all collected backtraces point at malloc/free traffic in the
// child spawned process.
//
// For this reason, the block of code below should contain 0
// invocations of either malloc of free (or their related friends).
//
// As an example of not having malloc/free traffic, we don't close
// this file descriptor by dropping the FileDesc (which contains an
// allocation). Instead we just close it manually. This will never
// have the drop glue anyway because this code never returns (the
// child will either exec() or invoke libc.exit)
public fun Command.doExec(stdio: ChildPipes, maybeEnvp: CStringArray?): Result<Nothing, Error> {
    when (val matchVal = stdio.stdin.fd()) {
        null -> {}
        else -> {
            val fd = matchVal
            when (val tryResult = cvtR({ dup2(fd, STDIN_FILENO) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    when (val matchVal1 = stdio.stdout.fd()) {
        null -> {}
        else -> {
            val fd1 = matchVal1
            when (val tryResult = cvtR({ dup2(fd1, STDOUT_FILENO) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    when (val matchVal2 = stdio.stderr.fd()) {
        null -> {}
        else -> {
            val fd2 = matchVal2
            when (val tryResult = cvtR({ dup2(fd2, STDERR_FILENO) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    run {
        when (val matchVal3 = this.getGroups()) {
            null -> {}
            else -> {
                val g = matchVal3
                // Note: Redox kernel does not support setgroups yet
                when (val tryResult = cvt(setgroups(g.len().tryInto().unwrap(), g.asPtr()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
            }
        }
        when (val matchVal4 = this.getGid()) {
            null -> {}
            else -> {
                val u = matchVal4
                when (val tryResult = cvt(setgid(u.toUInt()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
            }
        }
        when (val matchVal5 = this.getUid()) {
            null -> {}
            else -> {
                val u1 = matchVal5
                // When dropping privileges from root, the `setgroups` call
                // will remove any extraneous groups. We only drop groups
                // if we have CAP_SETGID and we weren't given an explicit
                // set of groups. If we don't call this, then even though our
                // uid has dropped, we may still have groups that enable us to
                // do super-user things.
                // Note: Redox kernel does not support setgroups yet
                if (this.getGroups() == null) {
                    val res = cvt(setgroups(0, ConstPtr(0L)))
                    if (res is Result.Err<*, *>) {
                        val e = res.errValue()
                        // Here we ignore the case of not having CAP_SETGID.
                        // An alternative would be to require CAP_SETGID (in
                        // addition to CAP_SETUID) for setting the UID.
                        if (e.rawOsError() != EPERM) {
                            return Result.Err<Nothing, Error>(e)
                        }
                    }
                }
                when (val tryResult = cvt(setuid(u1.toUInt()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
            }
        }
    }
    when (val matchVal6 = this.getChroot()) {
        null -> {}
        else -> {
            val chroot = matchVal6
            when (val tryResult = cvt(chroot(chroot.asPtr()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    when (val matchVal7 = this.getCwd()) {
        null -> {}
        else -> {
            val cwd = matchVal7
            when (val tryResult = cvt(chdir(cwd.asPtr()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    when (val matchVal8 = this.getPgroup()) {
        null -> {}
        else -> {
            val pgroup = matchVal8
            when (val tryResult = cvt(setpgid(0, pgroup))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    if (this.getSetsid()) {
        when (val tryResult = cvt(setsid())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
    }
    // emscripten has no signal support.
    if (!onBrokenPipeUsed()) {
        run {
            val action: Sigaction  = Sigaction()
            action.saSigaction = SIG_DFL
            when (val tryResult = cvt(Sigaction(SIGPIPE, action, MutPtr(0L)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
        }
    }
    for (callback in this.getClosures().iterMut()) {
        when (val tryResult = callback()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Nothing, Error>(tryResult.errValue()) }
    }
    // Although we're performing an exec here we may also return with an
    // error from this function (without actually exec'ing) in which case we
    // want to be sure to restore the global environment back to what it
    // once was, ensuring that our temporary override, when free'd, doesn't
    // corrupt our process's environment.
    var reset = null
    if (maybeEnvp != null) {
        val envp = maybeEnvp
        open class Reset(
            public var value: ConstPtr,
        ) {
            public var field0: ConstPtr
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                run {
                    (PrimitiveLayouts.constPtrLayout(64L)).writeNative((environ()).rawAddress, this.value)
                }
            }
        }
        reset = Reset((PrimitiveLayouts.constPtrLayout(64L)).readNative((environ()).rawAddress))
        (PrimitiveLayouts.constPtrLayout(64L)).writeNative((environ()).rawAddress, envp.asPtr())
    }
    execvp(this.getProgramCstr().asPtr(), this.getArgv().asPtr())
    return Result.Err<Nothing, Error>(Error.lastOsError())
}

public fun Command.posixSpawn(arg0: ChildPipes, arg1: CStringArray?): Result<Process?, Error> = Result.Ok<Process?, Error>(null)

public fun Command.sendPidfd(sock: Socket) {
    run {
        val SCM_MSG_LEN: Int = sizeOf<IntArray>(typeLayoutT = PrimitiveArrayLayouts.intArray((1).toInt()))
        open class Cmsg(
            public var buf: UByteArray? = null,
            public var align: Cmsghdr? = null,
        )
        val childPid = getpid()
        // pidfdOpen sets CLOEXEC by default
        val pidfd = syscall(SysPidfdOpen, childPid.toLong(), 0L)
        val localStorageL862C12 = AddressableArrayLocal.fromIntArray(intArrayOf(pidfd.toInt()))
        val cmsg: Cmsg  = MaybeUninit.zeroed(UnresolvedTypeLayout<Cmsg>()).assumeInit()
        // 0-length message to send through the socket so we can pass along the fd
        val localStorageL875C12 = AddressableLocal(listOf(IoSlice.new("".encodeToByteArray())), UnresolvedTypeLayout<Any?>())
        val localStorageL876C12 = AddressableLocal(Msghdr(), UnresolvedTypeLayout<Msghdr>())
        val msg by localStorageL876C12
        msg.msgIov = (localStorageL875C12.asMutPtr())
        msg.msgIovlen = 1
        // only attach cmsg if we successfully acquired the pidfd
        if (pidfd >= 0) {
            msg.msgControllen = sizeOfVal(cmsg.buf, typeLayoutT = PrimitiveArrayLayouts.uByteArray((cmsgSpace(SCM_MSG_LEN.toUInt()).toInt()).toInt()))
            msg.msgControl = (addrOfMut(cmsg.buf))
            val hdr = cmsgFirsthdr((localStorageL876C12.asMutPtr()))
            hdr.cmsgLevel = SOL_SOCKET
            hdr.cmsgType = SCM_RIGHTS
            hdr.cmsgLen = cmsgLen(SCM_MSG_LEN)
            val data = cmsgData(hdr)
            copyNonoverlapping(ConstPtr((localStorageL862C12.elementConstPtr(0)).rawAddress), mutPtrFromRustCast(data), SCM_MSG_LEN, typeLayoutT = PrimitiveLayouts.U8)
        }
        // we send the 0-length message even if we failed to acquire the pidfd
        // so we get a consistent SEQPACKET order
        run {
            val matchVal = cvtR({ sendmsg(sock.asRaw(), msg, MSG_EOR) })
            when {
                matchVal is Result.Ok<*, *> && matchVal.okValue() == 0 -> {
                    val okValueVal = matchVal.okValue()
                }
                else -> {
                    val other = matchVal
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal1
                            out.writeFmt("fatal runtime error: " + ("failed to communicate with parent process. $other").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("failed to communicate with parent process. $other").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        }
    }
}

public fun Command.recvPidfd(sock: Socket): PidT {
    return run {
        val SCM_MSG_LEN: Int = sizeOf<IntArray>(typeLayoutT = PrimitiveArrayLayouts.intArray((1).toInt()))
        open class Cmsg(
            public var buf: UByteArray? = null,
            public var align: Cmsghdr? = null,
        )
        val localStorageL922C12 = AddressableLocal(MaybeUninit.zeroed(UnresolvedTypeLayout<Cmsg>()).assumeInit(), UnresolvedTypeLayout<Cmsg>())
        // 0-length read to get the fd
        val localStorageL924C12 = AddressableLocal(listOf(IoSliceMut.new(listOf())), UnresolvedTypeLayout<Any?>())
        val localStorageL926C12 = AddressableLocal(Msghdr(), UnresolvedTypeLayout<Msghdr>())
        val msg by localStorageL926C12
        msg.msgIov = (localStorageL924C12.asMutPtr())
        msg.msgIovlen = 1
        msg.msgControllen = sizeOf<Cmsg>(typeLayoutT = UnresolvedTypeLayout<Cmsg>())
        msg.msgControl = (localStorageL922C12.asMutPtr())
        if (cvtR({ recvmsg(sock.asRaw(), RustMutableReference(localStorageL926C12.asMutPtr(), localStorageL926C12.layout), MSG_CMSG_CLOEXEC) }).isErr()) {
            return -1
        }
        val hdr = cmsgFirsthdr((localStorageL926C12.asMutPtr()))
        if (hdr.isNull() || hdr.cmsgLevel != SOL_SOCKET || hdr.cmsgType != SCM_RIGHTS || hdr.cmsgLen != cmsgLen(SCM_MSG_LEN)) {
            return -1
        }
        val data = cmsgData(hdr)
        val localStorageL947C12 = AddressableArrayLocal.fromIntArray(intArrayOf(-1))
        val fds by localStorageL947C12
        copyNonoverlapping(constPtrFromRustCast(data), MutPtr((localStorageL947C12.elementMutPtr(0)).rawAddress), SCM_MSG_LEN, typeLayoutT = UnresolvedTypeLayout<Any?>())
        fds[0]
    }
}

// ///////////////////////////////////////////////////////////////////////////
// Processes
// ///////////////////////////////////////////////////////////////////////////

/**
 * The unique ID of the process (this should never be negative).
 */
public open class Process(
    public var pid: PidT,
    public var status: ExitStatus?,
    // On Linux, stores the pidfd created for this child.
    // This is None if the user did not request pidfd creation,
    // or if the pidfd could not be created for some reason
    // (e.g. the `pidfdOpen` syscall was not available).
    public var pidfd: PidFd?,
) {
    public open fun id(): UInt = this.pid.toUInt()

    public open fun kill(): Result<Unit, Error> = this.sendSignal(SIGKILL)

    public open fun sendSignal(signal: Int): Result<Unit, Error> {
        // If we've already waited on this process then the pid can be recycled and
        // used for another process, and we probably shouldn't be sending signals to
        // random processes, so return Ok because the process has exited already.
        if (this.status != null) {
            return Result.Ok<Unit, Error>(Unit)
        }
        when (val matchVal = this.pidfd) {
            null -> {}
            else -> {
                val pidFd = matchVal
                // pidfdSendSignal predates pidfdOpen. so if we were able to get an fd then sending signals will work too
                return pidFd.sendSignal(signal)
            }
        }
        return cvt(kill(this.pid, signal)).map({ drop(it) })
    }

    public open fun sendProcessGroupSignal(signal: Int): Result<Unit, Error> {
        // See note in `sendSignal` regarding recycled PIDs.
        if (this.status != null) {
            return Result.Ok<Unit, Error>(Unit)
        }
        when (val matchVal = this.pidfd) {
            null -> {}
            else -> {
                val pidFd = matchVal
                // The `PIDFD_SIGNAL_PROCESS_GROUP` flag requires kernel >= 6.9
                return pidFd.sendProcessGroupSignal(signal)
            }
        }
        return cvt(killpg(this.pid, signal)).map({ drop(it) })
    }

    public open fun wait(): Result<ExitStatus, Error> {
        when (val matchVal = this.status) {
            null -> {}
            else -> {
                val status = matchVal
                return Result.Ok<ExitStatus, Error>(status)
            }
        }
        when (val matchVal1 = this.pidfd) {
            null -> {}
            else -> {
                val pidFd = matchVal1
                val status1 = when (val tryResult = pidFd.wait()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus, Error>(tryResult.errValue()) }
                this.status = status1
                return Result.Ok<ExitStatus, Error>(status1)
            }
        }
        val status2 = 0
        when (val tryResult = cvtR({ waitpid(this.pid, status2, 0) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus, Error>(tryResult.errValue()) }
        this.status = ExitStatus.new(status2)
        return Result.Ok<ExitStatus, Error>(ExitStatus.new(status2))
    }

    public open fun tryWait(): Result<ExitStatus?, Error> {
        when (val matchVal = this.status) {
            null -> {}
            else -> {
                val status = matchVal
                return Result.Ok<ExitStatus?, Error>(status)
            }
        }
        when (val matchVal1 = this.pidfd) {
            null -> {}
            else -> {
                val pidFd = matchVal1
                val status1 = when (val tryResult = pidFd.tryWait()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus?, Error>(tryResult.errValue()) }
                if (status1 != null) {
                    val status2 = status1
                    this.status = status2
                }
                return Result.Ok<ExitStatus?, Error>(status1)
            }
        }
        val status3 = 0
        val pid = when (val tryResult = cvt(waitpid(this.pid, status3, WNOHANG))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus?, Error>(tryResult.errValue()) }
        return if (pid == 0) {
            Result.Ok<ExitStatus?, Error>(null)
        } else {
            this.status = ExitStatus.new(status3)
            Result.Ok<ExitStatus?, Error>(ExitStatus.new(status3))
        }
    }

    public companion object {
        /**
         * # Safety
         *
         * `pidfd` must either be -1 (representing no file descriptor) or a valid, exclusively owned file
         * descriptor (See [I/O Safety]).
         */
        public fun new(pid: PidT, pidfd: PidT): Process {
            // Safety: If `pidfd` is nonnegative, we assume it's valid and otherwise unowned.
            val pidfd1 = (pidfd >= 0).then({ PidFd.fromInner(FileDesc.fromRawFd(pidfd)) })
            return Process(pid = pid, status = null, pidfd = pidfd1)
        }
    }
}

//
// This is not actually an "exit status" in Unix terminology.  Rather, it is a "wait status".
// See the discussion in comments and doc comments for `std.process.ExitStatus`.

/**
 * Unix exit statuses
 */
public open class ExitStatus(
    public var value: CInt,
) {
    public var field0: CInt
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatus = ExitStatus(value)

    public open fun exited(): Boolean = WIFEXITED(this.value)

    public open fun exitOk(): Result<Unit, ExitStatusError> {
        // This assumes that WIFEXITED(status) && WEXITSTATUS==0 corresponds to status==0. This is
        // true on all actual versions of Unix, is widely assumed, and is specified in SuS
        // https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html. If it is not
        // true for a platform pretending to be Unix, the tests (our doctests, and also
        // unix/tests.rs) will spot it. `ExitStatusError.code` assumes this too.
        return run {
            val matchVal = NonZero.tryFrom(this.value)
            when (matchVal) { is Result.Ok<*, *> -> {
                val failure = matchVal.okValue()
                Result.Err<Unit, ExitStatusError>(ExitStatusError(failure))
            }; is Result.Err<*, *> -> {
                Result.Ok<Unit, ExitStatusError>(Unit)
            } }
        }
    }

    public open fun code(): Int? = this.exited().then({ WEXITSTATUS(this.value) })

    public open fun signal(): Int? = WIFSIGNALED(this.value).then({ WTERMSIG(this.value) })

    public open fun coreDumped(): Boolean = (WIFSIGNALED(this.value) && WCOREDUMP(this.value)).toUByte()

    public open fun stoppedSignal(): Int? = WIFSTOPPED(this.value).then({ WSTOPSIG(this.value) })

    public open fun continued(): Boolean = WIFCONTINUED(this.value)

    public open fun intoRaw(): CInt = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("unix_wait_status(${this.value}, \"${this}\")"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        when (val matchVal4 = this.code()) {
            null -> {
                when (val matchVal2 = this.signal()) {
                    null -> {
                        when (val matchVal = this.stoppedSignal()) {
                            null -> {
                                if (this.continued()) {
                                    f.append("continued (WIFCONTINUED)")
                                } else {
                                    f.append("unrecognised wait status: ${this.value} ${this.value}")
                                }
                            }
                            else -> {
                                val signal = matchVal
                                f.append("stopped (not terminated) by signal: $signal")
                                when (val matchVal1 = signalString(signal)) {
                                    null -> {}
                                    else -> {
                                        val name = matchVal1
                                        f.append(" ($name)")
                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        val signal1 = matchVal2
                        f.append("signal: $signal")
                        when (val matchVal3 = signalString(signal1)) {
                            null -> {}
                            else -> {
                                f.append(" ($name)")
                            }
                        }
                        if (this.coreDumped()) {
                            f.append(" (core dumped)")
                        }
                    }
                }
            }
            else -> {
                val code = matchVal4
                f.append("exit status: $code")
            }
        }
        return Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ExitStatus> =
            object : TypeLayout<ExitStatus> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ExitStatus) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitStatus) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                }
            }

        public fun new(status: CInt): ExitStatus = ExitStatus(status)

        public fun fromWaitidSiginfo(siginfo: SiginfoT): ExitStatus {
            val status = siginfo.siStatus()
            return when (siginfo.siCode) {
                CLD_EXITED -> ExitStatus((status and 0xff) shl 8)
                CLD_KILLED -> ExitStatus(status)
                CLD_DUMPED -> ExitStatus(status or 0x80)
                CLD_CONTINUED -> ExitStatus(0xffff)
                CLD_STOPPED, CLD_TRAPPED -> ExitStatus(((status and 0xff) shl 8) or 0x7f)
                else -> error("waitid() should only return the above codes")
            }
        }

        /**
         * Converts a raw `cInt` to a type-safe `ExitStatus` by wrapping it without copying.
         */
        public fun from(a: CInt): ExitStatus = ExitStatus(a)
    }
}

/**
 * Converts a signal number to a readable, searchable name.
 *
 * This string should be displayed right after the signal number.
 * If a signal is unrecognized, it returns the empty string, so that
 * you just get the number like "0". If it is recognized, you'll get
 * something like "9 (SIGKILL)".
 */
public fun signalString(signal: Int): String? {
    val name =
        when (signal) {
            SIGHUP -> "SIGHUP"
            SIGINT -> "SIGINT"
            SIGQUIT -> "SIGQUIT"
            SIGILL -> "SIGILL"
            SIGTRAP -> "SIGTRAP"
            SIGABRT -> "SIGABRT"
            SIGBUS -> "SIGBUS"
            SIGFPE -> "SIGFPE"
            SIGKILL -> "SIGKILL"
            SIGUSR1 -> "SIGUSR1"
            SIGSEGV -> "SIGSEGV"
            SIGUSR2 -> "SIGUSR2"
            SIGPIPE -> "SIGPIPE"
            SIGALRM -> "SIGALRM"
            SIGTERM -> "SIGTERM"
            SIGCHLD -> "SIGCHLD"
            SIGCONT -> "SIGCONT"
            SIGSTOP -> "SIGSTOP"
            SIGTSTP -> "SIGTSTP"
            SIGTTIN -> "SIGTTIN"
            SIGTTOU -> "SIGTTOU"
            SIGURG -> "SIGURG"
            SIGXCPU -> "SIGXCPU"
            SIGXFSZ -> "SIGXFSZ"
            SIGVTALRM -> "SIGVTALRM"
            SIGPROF -> "SIGPROF"
            SIGWINCH -> "SIGWINCH"
            SIGIO -> "SIGIO"
            SIGSYS -> "SIGSYS"
            // For information on Linux signals, run `man 7 signal`
            SIGSTKFLT -> "SIGSTKFLT"
            SIGPWR -> "SIGPWR"
            SIGEMT -> "SIGEMT"
            SIGINFO -> "SIGINFO"
            else -> { return null }
        }
    return name
}

public open class ExitStatusError internal constructor(
    valueCtorArg: NonZero<CInt>,
) {
    public var value: NonZero<CInt> = valueCtorArg
        internal set

    public var field0: NonZero<CInt>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatusError = ExitStatusError(value)

    public open fun into(): ExitStatus = ExitStatus(this.value.into())

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugTuple(f, "unix_wait_status").field(this.value).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun code(): NonZero<Int, NonZeroI32Inner>? = ExitStatus(this.value.into()).code()?.let({ st -> st.tryInto().unwrap() })

    public companion object {
        public operator fun invoke(
            value: NonZero<CInt>,
        ): ExitStatusError = ExitStatusError(
            valueCtorArg = value,
        )
    }
}

public fun Child.pidfd(): Result<io.github.kotlinmania.rustlang.std.os.linux.process.PidFd, Error> = (this.handle.pidfd?.let({ fd -> transmuteWithLayouts<PidFd, io.github.kotlinmania.rustlang.std.os.linux.process.PidFd>(fd, UnresolvedTypeLayout<PidFd>(), UnresolvedTypeLayout<io.github.kotlinmania.rustlang.std.os.linux.process.PidFd>()) })?.let { Result.Ok<Any, Error>(it) } ?: Result.Err<Any, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.Uncategorized, message = "no pidfd was created."))))

public fun Child.intoPidfd(): Result<io.github.kotlinmania.rustlang.std.os.linux.process.PidFd, Child> = ((this.handle.pidfd.also { this.handle.pidfd = null })?.let(io.github.kotlinmania.rustlang.std.os.linux.process.PidFd.fromInner)?.let { Result.Ok<Any, Child>(it) } ?: Result.Err<Any, Child>(this))

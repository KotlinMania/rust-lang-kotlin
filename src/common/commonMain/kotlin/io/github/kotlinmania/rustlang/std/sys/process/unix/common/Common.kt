// port-lint: source sys/process/unix/common.rs
package io.github.kotlinmania.rustlang.std.sys.process.unix.common

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.io.stdio.Stderr
import io.github.kotlinmania.rustlang.io.stdio.Stdout
import io.github.kotlinmania.rustlang.libc.CInt
import io.github.kotlinmania.rustlang.libc.CUlong
import io.github.kotlinmania.rustlang.libc.Poll
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.Pollfd
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.wasi.EAGAIN
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.EWOULDBLOCK
import io.github.kotlinmania.rustlang.libc.wasi.POLLIN
import io.github.kotlinmania.rustlang.libc.wasi.STDERR_FILENO
import io.github.kotlinmania.rustlang.libc.wasi.STDOUT_FILENO
import io.github.kotlinmania.rustlang.libc.wasi.SigsetT
import io.github.kotlinmania.rustlang.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.asBytes
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.fromBytes
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.intoVec
import io.github.kotlinmania.rustlang.path.Path
import io.github.kotlinmania.rustlang.process.StdioPipes
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.cstringarray.CStringArray
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.cstringarray.CStringIter
import io.github.kotlinmania.rustlang.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.sys.fs.unix.File
import io.github.kotlinmania.rustlang.sys.fs.unix.OpenOptions
import io.github.kotlinmania.rustlang.sys.io.error.unix.setErrno
import io.github.kotlinmania.rustlang.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.sys.pipe.unix.Pipe
import io.github.kotlinmania.rustlang.sys.pipe.unix.pipe
import io.github.kotlinmania.rustlang.sys.process.env.CommandEnv
import io.github.kotlinmania.rustlang.sys.process.env.CommandEnvs
import io.github.kotlinmania.rustlang.sys.process.env.CommandResolvedEnvs

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
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

public val DEV_NULL: CStr = "/dev/null"

// Android with api less than 21 define sig* functions inline, so it is not
// available for dynamic link. Implementing sigemptyset and sigaddset allow us
// to support older Android version (independent of libc version).
// The following implementations are based on
// https://github.com/aosp-mirror/platformBionic/blob/ad8dcd6023294b646e5a8288c0ed431b0845da49/libc/include/android/legacySignalInlines.h
public fun sigemptyset(set: MutPtr): CInt {
    kmWriteBytes((set).rawAddress, (0u.toUByte()).toUByte(), (1).toLong() * (ObjectSlotLayout<SigsetT>()).sizeBytes)
    return 0
}

public fun sigaddset(set: MutPtr, signum: CInt): CInt {
    // The implementations from bionic (android libc) type pun `sigsetT` as an
    // array of `cUlong`. This works, but lets add a smoke check to make sure
    // that doesn't change.
    val LONG_BIT: Int = sizeOf<CUlong>(typeLayoutT = ObjectSlotLayout<CUlong>()) * 8
    val bit = (signum - 1)
    if (((set).rawAddress == 0L) || bit >= (8 * sizeOf<SigsetT>(typeLayoutT = ObjectSlotLayout<SigsetT>()))) {
        setErrno(EINVAL)
        return -1
    }
    val raw = Slice.fromRawPartsMut(MutPtr(set.rawAddress, set.rawMeta), sizeOf<SigsetT>(typeLayoutT = ObjectSlotLayout<SigsetT>()) / sizeOf<CUlong>(typeLayoutT = ObjectSlotLayout<CUlong>()))
    raw[bit / LONG_BIT] = raw[bit / LONG_BIT] or (1uL shl (bit % LONG_BIT))
    return 0
}

// ///////////////////////////////////////////////////////////////////////////
// Command
// ///////////////////////////////////////////////////////////////////////////
public open class Command internal constructor(
    public var program: CString,
    public var args: CStringArray,
    public var env: CommandEnv,
    public var programKind: ProgramKind,
    public var cwd: CString?,
    public var chroot: CString?,
    public var uid: UidT?,
    public var gid: GidT?,
    public var sawNul: Boolean,
    closuresCtorArg: MutableList<() -> Result<Unit, Error>>,
    public var groups: List<GidT>?,
    public var stdin: Stdio?,
    public var stdout: Stdio?,
    public var stderr: Stdio?,
    public var createPidfd: Boolean,
    public var pgroup: PidT?,
    public var setsid: Boolean,
) {
    public var closures: MutableList<() -> Result<Unit, Error>> = closuresCtorArg
        internal set

    public open fun setArg0(arg: OsStr) {
        // Set a new arg0
        val arg1 = os2c(arg, this.sawNul)
        this.args.write(0, arg1)
    }

    public open fun arg(arg: OsStr) {
        val arg1 = os2c(arg, this.sawNul)
        this.args.push(arg1)
    }

    public open fun cwd(dir: OsStr) {
        this.cwd = os2c(dir, this.sawNul)
    }

    public open fun uid(id: UidT) {
        this.uid = id
    }

    public open fun gid(id: GidT) {
        this.gid = id
    }

    public open fun groups(groups: List<GidT>) {
        this.groups = Box.from(groups)
    }

    public open fun pgroup(pgroup: PidT) {
        this.pgroup = pgroup
    }

    public open fun chroot(dir: Path) {
        this.chroot = os2c(dir.asOsStr(), this.sawNul)
        if (this.cwd == null) {
            this.cwd(OsStr.new("/"))
        }
    }

    public open fun setsid(setsid: Boolean) {
        this.setsid = setsid
    }

    public open fun createPidfd(value: Boolean) {
        this.createPidfd = value
    }

    public open fun getCreatePidfd(): Boolean = false

    public open fun sawNul(): Boolean = this.sawNul

    public open fun getProgram(): OsStr = OsStr.fromBytes(this.program.asBytes())

    public open fun getProgramKind(): ProgramKind = this.programKind

    public open fun getArgs(): CommandArgs {
        var iter = this.args.iter()
        // argv[0] contains the program name, but we are only interested in the
        // arguments so skip it.
        iter.next()
        return CommandArgs(iter = iter)
    }

    public open fun getEnvs(): CommandEnvs = this.env.iter()

    public open fun getEnvClear(): Boolean = this.env.doesClear()

    public open fun getResolvedEnvs(): CommandResolvedEnvs = CommandResolvedEnvs.new(this.env.capture())

    public open fun getCurrentDir(): Path? = this.cwd?.let({ cs -> Path.new(OsStr.fromBytes(cs.encodeToByteArray().map { it.toUByte() })) })

    public open fun getArgv(): CStringArray = this.args

    public open fun getProgramCstr(): CStr = this.program

    public open fun getCwd(): CStr? = this.cwd

    public open fun getUid(): UidT? = this.uid

    public open fun getGid(): GidT? = this.gid

    public open fun getGroups(): List<GidT>? = this.groups

    public open fun getPgroup(): PidT? = this.pgroup

    public open fun getChroot(): CStr? = this.chroot

    public open fun getSetsid(): Boolean = this.setsid

    public open fun getClosures(): kotlin.reflect.KMutableProperty0<List<() -> Result<Unit, Error>>> {
        return this::closures
    }

    public open fun preExec(f: () -> Result<Unit, Error>) {
        this.closures.add(f)
    }

    public open fun stdin(stdin: Stdio) {
        this.stdin = stdin
    }

    public open fun stdout(stdout: Stdio) {
        this.stdout = stdout
    }

    public open fun stderr(stderr: Stdio) {
        this.stderr = stderr
    }

    public open fun envMut(): kotlin.reflect.KMutableProperty0<CommandEnv> {
        return this::env
    }

    public open fun captureEnv(): CStringArray? {
        val maybeEnv = this.env.captureIfChanged()
        return maybeEnv.map({ env -> constructEnvp(env, this.sawNul) })
    }

    public open fun envSawPath(): Boolean = this.env.haveChangedPath()

    public open fun programIsPath(): Boolean = this.program.toBytes().contains(47u)

    public open fun setupIo(default: Stdio, needsStdin: Boolean): Result<Pair<StdioPipes, ChildPipes>, Error> {
        val nullVal = Stdio.Null
        val defaultStdin =
            if (needsStdin) {
                default
            } else {
                nullVal
            }
        val stdin = (this.stdin ?: defaultStdin)
        val stdout = (this.stdout ?: default)
        val stderr = (this.stderr ?: default)
        val (theirStdin, ourStdin) = when (val tryResult = stdin.toChildStdio(true)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val (theirStdout, ourStdout) = when (val tryResult = stdout.toChildStdio(false)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val (theirStderr, ourStderr) = when (val tryResult = stderr.toChildStdio(false)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val ours = StdioPipes(stdin = ourStdin, stdout = ourStdout, stderr = ourStderr)
        val theirs = ChildPipes(stdin = theirStdin, stdout = theirStdout, stderr = theirStderr)
        return Result.Ok(Pair(ours, theirs))
    }

    // show all attributes but `self.closures` which does not implement `Debug`
    // and `self.argv` which is not useful for debugging
    public open fun fmt(f: StringBuilder) {
        if (f.alternate()) {
            val debugCommand = DebugStruct(f, "Command")
            debugCommand.field("program", this.program).field("args", this.args)
            if (!this.env.isUnchanged()) {
                debugCommand.field("env", this.env)
            }
            if (this.cwd != null) {
                debugCommand.field("cwd", this.cwd)
            }
            if (this.uid != null) {
                debugCommand.field("uid", this.uid)
            }
            if (this.gid != null) {
                debugCommand.field("gid", this.gid)
            }
            if (this.groups != null) {
                debugCommand.field("groups", this.groups)
            }
            if (this.stdin != null) {
                debugCommand.field("stdin", this.stdin)
            }
            if (this.stdout != null) {
                debugCommand.field("stdout", this.stdout)
            }
            if (this.stderr != null) {
                debugCommand.field("stderr", this.stderr)
            }
            if (this.pgroup != null) {
                debugCommand.field("pgroup", this.pgroup)
            }
            run {
                debugCommand.field("create_pidfd", this.createPidfd)
            }
            debugCommand.finish()
        } else {
            when (val matchVal = this.cwd) {
                null -> {}
                else -> {
                    val cwd = matchVal
                    f.append("cd $cwd && ")
                }
            }
            if (this.env.doesClear()) {
                f.append("env -i ")
                // Altered env vars will be printed next, that should exactly work as expected.
            } else {
                // Removed env vars need the command to be wrapped in `env`.
                var anyRemoved = false
                for ((key, valueOpt) in this.getEnvs()) {
                    if (valueOpt == null) {
                        if (!anyRemoved) {
                            f.append("env ")
                            anyRemoved = true
                        }
                        f.append("-u ${key.toStringLossy()} ")
                    }
                }
            }
            // Altered env vars can just be added in front of the program.
            for ((key1, valueOpt1) in this.getEnvs()) {
                if (valueOpt1 != null) {
                    val value = valueOpt1
                    f.append("${key1.toStringLossy()}=$value ")
                }
            }
            if (this.program != this.args[0]) {
                f.append("[${this.program}] ")
            }
            f.append("${this.args[0]}")
            for (arg in this.getArgs()) {
                f.append(" $arg")
            }
        }
    }

    public companion object {
        public operator fun invoke(
            program: CString,
            args: CStringArray,
            env: CommandEnv,
            programKind: ProgramKind,
            cwd: CString?,
            chroot: CString?,
            uid: UidT?,
            gid: GidT?,
            sawNul: Boolean,
            closures: MutableList<() -> Result<Unit, Error>>,
            groups: List<GidT>?,
            stdin: Stdio?,
            stdout: Stdio?,
            stderr: Stdio?,
            createPidfd: Boolean,
            pgroup: PidT?,
            setsid: Boolean,
        ): Command = Command(
            program = program,
            args = args,
            env = env,
            programKind = programKind,
            cwd = cwd,
            chroot = chroot,
            uid = uid,
            gid = gid,
            sawNul = sawNul,
            closuresCtorArg = closures,
            groups = groups,
            stdin = stdin,
            stdout = stdout,
            stderr = stderr,
            createPidfd = createPidfd,
            pgroup = pgroup,
            setsid = setsid,
        )

        public fun new(program: OsStr): Command {
            val localStorageL166C8 = AddressableLocal(false, PrimitiveLayouts.BOOL)
            val sawNul by localStorageL166C8
            val programKind = ProgramKind.new(program.asRef())
            val program1 = os2c(program, RustMutableReference(localStorageL166C8.asMutPtr(), localStorageL166C8.layout))
            var args = CStringArray.withCapacity(1)
            args.push(program1.clone())
            return Command(program = program1, args = args, env = Default.default(), programKind = programKind, cwd = null, chroot = null, uid = null, gid = null, sawNul = sawNul, closures = mutableListOf(), groups = null, stdin = null, stdout = null, stderr = null, createPidfd = false, pgroup = null, setsid = false)
        }
    }
}

// passed to doExec() with configuration of what the child stdio should look
// like
public open class ChildPipes(
    public var stdin: ChildStdio,
    public var stdout: ChildStdio,
    public var stderr: ChildStdio,
)

public sealed class ChildStdio {
    public data object Inherit : ChildStdio()
    public data class Explicit(public var value: io.github.kotlinmania.rustlang.os.raw.CInt) : ChildStdio()
    public data class Owned(public var value: FileDesc) : ChildStdio()

    public open fun fd(): io.github.kotlinmania.rustlang.os.raw.CInt? =
        when (this) {
            ChildStdio.Inherit -> null
            is ChildStdio.Explicit -> {
                val (fd) = this
                fd
            }
            is ChildStdio.Owned -> {
                val (fd1) = this
                fd1.asRawFd()
            }
        }
}

public sealed class Stdio {
    public data object Inherit : Stdio()
    public data object Null : Stdio()
    public data object MakePipe : Stdio()
    public data class Fd(public var value: FileDesc) : Stdio()
    public data class StaticFd(public var value: BorrowedFd) : Stdio()

    public open fun toChildStdio(readable: Boolean): Result<Pair<ChildStdio, ChildPipe?>, Error> {
        return when (this) {
            Stdio.Inherit -> Result.Ok(Pair(ChildStdio.Inherit, null))
            // Make sure that the source descriptors are not an stdio
            // descriptor, otherwise the order which we set the child's
            // descriptors may blow away a descriptor which we are hoping to
            // save. For example, suppose we want the child's stderr to be the
            // parent's stdout, and the child's stdout to be the parent's
            // stderr. No matter which we dup first, the second will get
            // overwritten prematurely.
            is Stdio.Fd -> {
                val (fd) = this
                if (fd.asRawFd() >= 0 && fd.asRawFd() <= STDERR_FILENO) {
                    Result.Ok(Pair(ChildStdio.Owned(when (val tryResult = fd.duplicate()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }), null))
                } else {
                    Result.Ok(Pair(ChildStdio.Explicit(fd.asRawFd()), null))
                }
            }
            is Stdio.StaticFd -> {
                val (fd1) = this
                val fd2 = FileDesc.fromInner(when (val tryResult = fd1.tryCloneToOwned()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) })
                Result.Ok(Pair(ChildStdio.Owned(fd2), null))
            }
            Stdio.MakePipe -> {
                val (reader, writer) = when (val tryResult = pipe()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                val (ours, theirs) =
                    if (readable) {
                        Pair(writer, reader)
                    } else {
                        Pair(reader, writer)
                    }
                Result.Ok(Pair(ChildStdio.Owned(theirs), ours))
            }
            Stdio.Null -> {
                val opts = OpenOptions.new()
                opts.read(readable)
                opts.write(!readable)
                val fd3 = when (val tryResult = File.openC(DEV_NULL, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                Result.Ok(Pair(ChildStdio.Owned(fd3.intoInner()), null))
            }
        }
    }

    public companion object {
        public fun from(fd: FileDesc): Stdio = Stdio.Fd(fd)

        public fun from(file: File): Stdio = Stdio.Fd(file.intoInner())

        public fun from(arg0: Stdout): Stdio {
            // This ought really to be is Stdio.StaticFd(inputArgument.asFd()).
            // But AsFd.asFd takes its argument by reference, and yields
            // a bounded scope, so it's no use here. There is no AsStaticFd.
            //
            // Additionally AsFd is only implemented for the *locked* versions.
            // We don't want to lock them here.  (The implications of not locking
            // are the same as those for process.Stdio.inherit().)
            //
            // Arguably the hypothetical AsStaticFd and AsFd<static>
            // should be implemented for io.Stdout, not just for StdoutLocked.
            return Stdio.StaticFd(BorrowedFd.borrowRaw(STDOUT_FILENO))
        }

        public fun from(arg0: Stderr): Stdio = Stdio.StaticFd(BorrowedFd.borrowRaw(STDERR_FILENO))
    }
}

public sealed class ProgramKind {
    /**
     * A program that would be looked up on the PATH (e.g. `ls`)
     */
    public data object PathLookup : ProgramKind()
    /**
     * A relative path (e.g. `my - dir / foo`, `.. / foo`, `./ foo`)
     */
    public data object Relative : ProgramKind()
    /**
     * An absolute path.
     */
    public data object Absolute : ProgramKind()

    public fun rustEquals(other: ProgramKind): Boolean = this == other

    public companion object {
        public fun new(program: OsStr): ProgramKind =
            if (program.asEncodedBytes().startsWith("/".encodeToByteArray())) {
                ProgramKind.Absolute
            } else if (program.asEncodedBytes().contains(47u)) {
                // If the program has more than one component in it, it is a relative path.
                ProgramKind.Relative
            } else {
                ProgramKind.PathLookup
            }
    }
}

public fun os2c(s: OsStr, sawNul: RustMutableReference<Boolean>): CString {
    val sawNul1 = sawNul
    return CString.new(s.asBytes()).unwrapOrElse({ e ->
        (sawNul1).write(true)
        "<string-with-nul>".toOwned()
    })
}

public fun constructEnvp(env: MutableMap<OsString, OsString>, sawNul: RustMutableReference<Boolean>): CStringArray {
    val sawNul1 = sawNul
    val result = CStringArray.withCapacity(env.size)
    for ((k, v) in env) {
        // Reserve additional space for '=' and null terminator
        k.reserveExact(v.len() + 2)
        k.push("=")
        k.push(v)
        // Add the new entry into the array
        when (val matchVal = CString.new(k.intoVec())) {
            is Ok -> {
                val item = matchVal.value
                result.push(item)
            }
            else -> {
                (sawNul1).write(true)
            }
        }
    }
    return result
}

public open class ExitCode(
    public var value: UByte,
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitCode = ExitCode(value)

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "unix_exit_status").field(this.value).finish()
    }

    public open fun asI32(): Int = this.value.toInt()

    public companion object {
        public fun typeLayout(): TypeLayout<ExitCode> =
            object : TypeLayout<ExitCode> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): ExitCode =
                    ExitCode(
                        (PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ExitCode) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitCode =
                    ExitCode(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitCode) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }

        public val SUCCESS: ExitCode = ExitCode(null)

        public val FAILURE: ExitCode = ExitCode(null)

        public fun from(code: UByte): ExitCode = ExitCode(code)
    }
}

public open class CommandArgs(
    public var iter: CStringIter,
) : Iterable<OsStr> {
    override fun iterator(): Iterator<OsStr> = object : AbstractIterator<OsStr>() {
        override fun computeNext() {
            when (val v = this@CommandArgs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): OsStr? = this.iter.next()?.let({ cs -> OsStr.fromBytes(cs.toBytes()) })

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter.clone()).finish()
    }
}

public typealias ChildPipe = Pipe

public fun readOutput(out: ChildPipe, stdout: List<UByte>, err: ChildPipe, stderr: List<UByte>): Result<Unit, Error> {
    // Read as much as we can from each pipe, ignoring EWOULDBLOCK or
    // EAGAIN. If we hit EOF, then this will happen because the underlying
    // reader will return Ok(0), in which case we'll see `Ok` ourselves. In
    // this case we flip the other fd back into blocking mode and read
    // whatever's leftover on that file descriptor.
    fun read(fd: FileDesc, dst: List<UByte>): Result<Boolean, io.github.kotlinmania.rustlang.io.Error> =
        run {
            val matchVal = fd.readToEnd(dst)
            when (matchVal) { is Result.Ok<*, *> -> {
                Result.Ok(true)
            }; is Result.Err<*, *> -> {
                val e = matchVal.error
                if (e.rawOsError() == EWOULDBLOCK || e.rawOsError() == EAGAIN) {
                    Result.Ok(false)
                } else {
                    Result.Err(e)
                }
            } }
        }
    // Set both pipes into nonblocking mode as we're gonna be reading from both
    // in the `select` loop below, and we wouldn't want one to block the other!
    when (val tryResult = out.setNonblocking(true)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    when (val tryResult = err.setNonblocking(true)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val localStorageL651C4 = AddressableArrayLocal.fromList(emptyList(), ObjectSlotLayout<Pollfd>())
    val fds by localStorageL651C4
    fds[0].fd = out.asRawFd()
    fds[0].events = POLLIN
    fds[1].fd = err.asRawFd()
    fds[1].events = POLLIN
    while (true) {
        // wait for either pipe to become readable using `poll`
        when (val tryResult = cvtR({ Poll(localStorageL651C4.elementMutPtr(0), 2, -1) })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        if (fds[0].revents != 0 && when (val tryResult = read(out, stdout)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
            when (val tryResult = err.setNonblocking(false)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            return err.readToEnd(stderr).map({ Unit })
        }
        if (fds[1].revents != 0 && when (val tryResult = read(err, stderr)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
            when (val tryResult = out.setNonblocking(false)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            return out.readToEnd(stdout).map({ Unit })
        }
    }
}

public fun getpid(): UInt = io.github.kotlinmania.rustlang.libc.getpid()

public fun getppid(): UInt = io.github.kotlinmania.rustlang.libc.getppid()

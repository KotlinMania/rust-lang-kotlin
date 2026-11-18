// port-lint: source sys/process/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.process.unsupported

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.iter.Iter
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.process.StdioPipes
import io.github.kotlinmania.rustlang.std.sys.fs.unsupported.File
import io.github.kotlinmania.rustlang.std.sys.pal.unsupported.unsupported
import io.github.kotlinmania.rustlang.std.sys.pipe.unsupported.Pipe
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandEnv
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandEnvs
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandResolvedEnvs

private class DebugStruct(
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

// ///////////////////////////////////////////////////////////////////////////
// Command
// ///////////////////////////////////////////////////////////////////////////
public open class Command(
    public var program: OsString,
    public var args: MutableList<OsString>,
    public var env: CommandEnv,
    public var cwd: OsString?,
    public var stdin: Stdio?,
    public var stdout: Stdio?,
    public var stderr: Stdio?,
) {
    public open fun arg(arg: OsStr) {
        this.args.add(arg.toOwned())
    }

    public open fun envMut(): kotlin.reflect.KMutableProperty0<CommandEnv> {
        return this::env
    }

    public open fun cwd(dir: OsStr) {
        this.cwd = dir.toOwned()
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

    public open fun getProgram(): OsStr = this.program

    public open fun getArgs(): CommandArgs {
        var iter = ArrayDeque(this.args)
        iter.removeFirstOrNull()
        return CommandArgs(iter = iter)
    }

    public open fun getEnvs(): CommandEnvs = this.env.iter()

    public open fun getEnvClear(): Boolean = this.env.doesClear()

    public open fun getResolvedEnvs(): CommandResolvedEnvs = CommandResolvedEnvs.new(this.env.capture())

    public open fun getCurrentDir(): Path? = this.cwd?.let({ cs -> Path.new(cs) })

    public open fun spawn(default: Stdio, needsStdin: Boolean): Result<Pair<Process, StdioPipes>, Error> = unsupported()

    // show all attributes
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        if (f.alternate()) {
            val debugCommand = DebugStruct(f, "Command")
            debugCommand.field("program", this.program).field("args", this.args)
            if (!this.env.isUnchanged()) {
                debugCommand.field("env", this.env)
            }
            if (this.cwd != null) {
                debugCommand.field("cwd", this.cwd)
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
            run { debugCommand.finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
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
            for (arg in ((this.args).subList(1, (this.args).size))) {
                f.append(" $arg")
            }
            Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
        }

    public companion object {
        public fun new(program: OsStr): Command = Command(program = program.toOwned(), args = mutableListOf(program.toOwned()), env = CommandEnv.default(), cwd = null, stdin = null, stdout = null, stderr = null)
    }
}

public sealed class Stdio {
    public data object Inherit : Stdio()
    public data object Null : Stdio()
    public data object MakePipe : Stdio()
    public data object ParentStdout : Stdio()
    public data object ParentStderr : Stdio()
    // This variant exists only for the Debug impl
    public data class InheritFile(public var value: File) : Stdio()

    public companion object {
        public fun from(pipe: ChildPipe): Stdio = pipe.diverge()

        public fun from(arg0: Stdout): Stdio = Stdio.ParentStdout

        public fun from(arg0: Stderr): Stdio = Stdio.ParentStderr

        public fun from(file: File): Stdio = Stdio.InheritFile(file)
    }
}

public fun output(cmd: Command): Result<Triple<ExitStatus, List<UByte>, List<UByte>>, Error> = unsupported()

public open class ExitStatus {
    public open fun clone(): ExitStatus = ExitStatus()

    public open fun exitOk(): Result<Unit, ExitStatusError> = Result.Ok<Unit, ExitStatusError>(Unit)

    public open fun code(): Int? = 0

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("<dummy exit status>"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<ExitStatus> =
            object : TypeLayout<ExitStatus> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ExitStatus =
                    ExitStatus()
                override fun writeNative(addr: Long, value: ExitStatus) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitStatus =
                    ExitStatus()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitStatus) {
                }
            }
    }
}

public open class ExitStatusError(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatusError = this.value

    public open fun eq(other: ExitStatusError): Boolean = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public open fun into(): ExitStatus = this.value

    public open fun code(): NonZero<Int, NonZeroI32Inner>? = this.value
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

        public val SUCCESS: ExitCode = ExitCode(0)

        public val FAILURE: ExitCode = ExitCode(1)

        public fun from(code: UByte): ExitCode = ExitCode(code)
    }
}

public open class Process(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun id(): UInt = this.value

    public open fun kill(): Result<Unit, Error> = this.value

    public open fun wait(): Result<ExitStatus, Error> = this.value

    public open fun tryWait(): Result<ExitStatus?, Error> = this.value
}

public open class CommandArgs internal constructor(
    iterCtorArg: Iter<OsString>,
) : Iterable<OsStr> {
    public var iter: Iter<OsString> = iterCtorArg
        internal set

    override fun iterator(): Iterator<OsStr> = object : AbstractIterator<OsStr>() {
        override fun computeNext() {
            when (val v = this@CommandArgs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): OsStr? = this.iter.removeFirstOrNull()?.let({ os -> os })

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(ArrayDeque(this.iter)).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public operator fun invoke(
            iter: Iter<OsString>,
        ): CommandArgs = CommandArgs(
            iterCtorArg = iter,
        )
    }
}

public typealias ChildPipe = Pipe

public fun readOutput(out: ChildPipe, stdout: MutableList<UByte>, err: ChildPipe, stderr: MutableList<UByte>): Result<Unit, Error> = when (out.diverge()) {}

public fun getpid(): UInt {
    error("no pids on this platform")
}

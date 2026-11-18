// port-lint: source sys/process/windows.rs
package io.github.kotlinmania.rustlang.std.sys.process.windows

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter.Iter
import io.github.kotlinmania.rustlang.std.env.EXE_EXTENSION
import io.github.kotlinmania.rustlang.std.env.EXE_SUFFIX
import io.github.kotlinmania.rustlang.std.env.currentExe
import io.github.kotlinmania.rustlang.std.env.splitPaths
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.process.ProcThreadAttributeList
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.process.StdioPipes
import io.github.kotlinmania.rustlang.std.sys.args.windows.Arg
import io.github.kotlinmania.rustlang.std.sys.args.windows.appendArg
import io.github.kotlinmania.rustlang.std.sys.args.windows.makeBatCommandLine
import io.github.kotlinmania.rustlang.std.sys.args.windows.toUserPath
import io.github.kotlinmania.rustlang.std.sys.env.windows.env
import io.github.kotlinmania.rustlang.std.sys.fs.windows.File
import io.github.kotlinmania.rustlang.std.sys.fs.windows.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.pal.api.utf16
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.CREATE_NEW_PROCESS_GROUP
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.CREATE_UNICODE_ENVIRONMENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DETACHED_PROCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DUPLICATE_SAME_ACCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.EXIT_FAILURE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.EXIT_SUCCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.EXTENDED_STARTUPINFO_PRESENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_FORCEOFFFEEDBACK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_FORCEONFEEDBACK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_RUNFULLSCREEN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_UNTRUSTEDSOURCE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_USESHOWWINDOW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STARTF_USESTDHANDLES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.WAIT_OBJECT_0
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.WAIT_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setthreaddescription.nullVal
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CSTR_EQUAL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CSTR_GREATER_THAN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CSTR_LESS_THAN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FALSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.INFINITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.INVALID_FILE_ATTRIBUTES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ProcessInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_ERROR_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_INPUT_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_OUTPUT_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Startupinfoexw
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Startupinfow
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TRUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.compareStringOrdinal
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createProcessW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCurrentProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getExitCodeProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFullPathNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getSystemDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getWindowsDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.terminateProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.waitForSingleObject
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.ensureNoNuls
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle
import io.github.kotlinmania.rustlang.std.sys.path.windows.appendSuffix
import io.github.kotlinmania.rustlang.std.sys.path.windows.hasTrailingSlash
import io.github.kotlinmania.rustlang.std.sys.path.windows.isAbsoluteExact
import io.github.kotlinmania.rustlang.std.sys.path.windows.isFileName
import io.github.kotlinmania.rustlang.std.sys.path.windows.isVerbatim
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandEnv
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandEnvs
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandResolvedEnvs
import io.github.kotlinmania.rustlang.std.sys.process.windows.childpipe.ChildPipe
import io.github.kotlinmania.rustlang.std.sys.process.windows.childpipe.childPipe
import io.github.kotlinmania.rustlang.std.sys.process.windows.childpipe.spawnPipeRelay
import io.github.kotlinmania.rustlang.std.sys.stdio.windows.getHandle

// ///////////////////////////////////////////////////////////////////////////
// Command
// ///////////////////////////////////////////////////////////////////////////
public open class EnvKey(
    public var osString: OsString,
    // This stores a UTF-16 encoded string to workaround the mismatch between
    // Rust's OsString (WTF-8) and the Windows API string type (UTF-16).
    // Normally converting on every API call is acceptable but here
    // `c.CompareStringOrdinal` will be called for every use of `==`.
    public var utf16: List<UShort>,
) : AsRef<OsStr> {
    public open fun clone(): EnvKey = EnvKey(osString, utf16)

    // Comparing Windows environment variable keys[1] are behaviorally the
    // composition of two operations[2]:
    //
    // 1. Case-fold both strings. This is done using a language-independent
    // uppercase mapping that's unique to Windows (albeit based on data from an
    // older Unicode spec). It only operates on individual UTF-16 code units so
    // surrogates are left unchanged. This uppercase mapping can potentially change
    // between Windows versions.
    //
    // 2. Perform an ordinal comparison of the strings. A comparison using ordinal
    // is just a comparison based on the numerical value of each UTF-16 code unit[3].
    //
    // Because the case-folding mapping is unique to Windows and not guaranteed to
    // be stable, we ask the OS to compare the strings for us. This is done by
    // calling `CompareStringOrdinal`[4] with `bIgnoreCase` set to `TRUE`.
    //
    // [1] https://docs.microsoft.com/en-us/dotnet/standard/base-types/best-practices-strings#choosing-a-stringcomparison-member-for-your-method-call
    // [2] https://docs.microsoft.com/en-us/dotnet/standard/base-types/best-practices-strings#stringtoupper-and-stringtolower
    // [3] https://docs.microsoft.com/en-us/dotnet/api/system.stringcomparison?view=net-5.0#SystemStringComparisonOrdinal
    // [4] https://docs.microsoft.com/en-us/windows/win32/api/stringapiset/nf-stringapiset-comparestringordinal
    public open fun cmp(other: EnvKey): Ordering =
        run {
            val result = compareStringOrdinal(this.utf16.asPtr(), this.utf16.size, other.utf16.asPtr(), other.utf16.size, TRUE)
            when (result) {
                CSTR_LESS_THAN -> Ordering.Less
                CSTR_EQUAL -> Ordering.Equal
                CSTR_GREATER_THAN -> Ordering.Greater
                // `CompareStringOrdinal` should never fail so long as the parameters are correct.
                else -> error("comparing environment keys failed: ${Error.lastOsError()}")
            }
        }

    public open fun partialCmp(other: EnvKey): Ordering? = this.cmp(other)

    public open fun eq(other: EnvKey): Boolean =
        if (this.utf16.size != other.utf16.size) {
            false
        } else {
            this.cmp(other) == Ordering.Equal
        }

    public open fun partialCmp(other: String): Ordering? = this.cmp(EnvKey.new(other))

    public open fun eq(other: String): Boolean =
        if (this.osString.len() != other.length) {
            false
        } else {
            this.cmp(EnvKey.new(other)) == Ordering.Equal
        }

    public open fun asRef(): OsStr = this.osString

    public companion object {
        public fun new(key: OsString): EnvKey = EnvKey.from(key)

        // Environment variable keys should preserve their original case even though
        // they are compared using a caseless string mapping.
        public fun from(k: OsString): EnvKey = EnvKey(utf16 = k.encodeWide().toMutableList(), osString = k)

        public fun from(k: OsStr): EnvKey = EnvKey.from(k.toOsString())
    }
}

public fun fromOsString(k: EnvKey): OsString = k.osString

public open class Command(
    public var program: OsString,
    public var args: MutableList<Arg>,
    public var env: CommandEnv,
    public var cwd: OsString?,
    public var flags: UInt,
    public var showWindow: UShort?,
    public var detach: Boolean,
    // not currently exposed in std.process
    public var stdin: Stdio?,
    public var stdout: Stdio?,
    public var stderr: Stdio?,
    public var forceQuotesEnabled: Boolean,
    public var startupinfoFullscreen: Boolean,
    public var startupinfoUntrustedSource: Boolean,
    public var startupinfoForceFeedback: Boolean?,
    public var inheritHandles: Boolean,
    public var desktop: List<UShort>?,
) {
    public open fun arg(arg: OsStr) {
        this.args.add(Arg.Regular(arg.toOsString()))
    }

    public open fun envMut(): kotlin.reflect.KMutableProperty0<CommandEnv> {
        return this::env
    }

    public open fun cwd(dir: OsStr) {
        this.cwd = dir.toOsString()
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

    public open fun creationFlags(flags: UInt) {
        this.flags = flags
    }

    public open fun showWindow(cmdShow: UShort?) {
        this.showWindow = cmdShow
    }

    public open fun forceQuotes(enabled: Boolean) {
        this.forceQuotesEnabled = enabled
    }

    public open fun rawArg(commandStrToAppend: OsStr) {
        this.args.add(Arg.Raw(commandStrToAppend.toOsString()))
    }

    public open fun startupinfoFullscreen(enabled: Boolean) {
        this.startupinfoFullscreen = enabled
    }

    public open fun startupinfoUntrustedSource(enabled: Boolean) {
        this.startupinfoUntrustedSource = enabled
    }

    public open fun startupinfoForceFeedback(enabled: Boolean?) {
        this.startupinfoForceFeedback = enabled
    }

    public open fun desktop(desktop: OsStr) {
        this.desktop = desktop.encodeWide().chain(listOf(0)).toMutableList()
    }

    public open fun getProgram(): OsStr = this.program

    public open fun getArgs(): CommandArgs {
        val iter = ArrayDeque(this.args)
        return CommandArgs(iter = iter)
    }

    public open fun getEnvs(): CommandEnvs = this.env.iter()

    public open fun getEnvClear(): Boolean = this.env.doesClear()

    public open fun getResolvedEnvs(): CommandResolvedEnvs = CommandResolvedEnvs.new(this.env.capture())

    public open fun getCurrentDir(): Path? = this.cwd?.let(Path::new)

    public open fun inheritHandles(inheritHandles: Boolean) {
        this.inheritHandles = inheritHandles
    }

    public open fun spawn(default: Stdio, needsStdin: Boolean): Result<Pair<Process, StdioPipes>, Error> = this.spawnWithAttributes(default, needsStdin, null)

    public open fun spawnWithAttributes(default: Stdio, needsStdin: Boolean, procThreadAttributeList: ProcThreadAttributeList?): Result<Pair<Process, StdioPipes>, Error> {
        // Prepare all stdio handles to be inherited by the child. This
        // currently involves duplicating any existing ones with the ability to
        // be inherited by child processes. Note, however, that once an
        // inheritable handle is created, *any* spawned child will inherit that
        // handle. We only want our own child to inherit this handle, so we wrap
        // the remaining portion of this spawn in a mutex.
        //
        // For more information, msdn also has an article about this race:
        // https://support.microsoft.com/kb/315939
        val envSawPath = this.env.haveChangedPath()
        val maybeEnv = this.env.captureIfChanged()
        val childPaths =
            if (envSawPath && maybeEnv.asRef() != null) {
                env.get(EnvKey.new("PATH"))?.let({ s -> s.asOsStr() })
            } else {
                null
            }
        val program = when (val tryResult = resolveExe(this.program, { varOs("PATH") }, childPaths)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val hasBatExtension =
            { program1: List<UShort> ->
                run {
                    val matchVal = program1.len().checkedSub(4)?.let({ i -> program1.get(i until Int.MAX_VALUE) })
                    when {
                        matchVal != null && ((matchVal.size == 4 && matchVal[0] == 46 && (matchVal[1] == 98 || matchVal[1] == 66) && (matchVal[2] == 97 || matchVal[2] == 65) && (matchVal[3] == 116 || matchVal[3] == 84)) || (matchVal.size == 4 && matchVal[0] == 46 && (matchVal[1] == 99 || matchVal[1] == 67) && (matchVal[2] == 109 || matchVal[2] == 77) && (matchVal[3] == 100 || matchVal[3] == 68))) -> true
                        else -> false
                    }
                }
            }
        val isBatchFile =
            if (isVerbatim(program)) {
                hasBatExtension(program[0 until program.len() - 1])
            } else {
                when (val tryResult = fillUtf16Buf({ buffer, size -> getFullPathNameW(program.asPtr(), size, buffer, MutPtr(0L)) }, { program1 -> hasBatExtension(program1) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
            }
        var (program1, cmdStr1) =
            if (isBatchFile) {
                Pair(when (val tryResult = commandPrompt()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }, when (val tryResult = makeBatCommandLine(program, this.args, this.forceQuotesEnabled)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) })
            } else {
                val cmdStr = when (val tryResult = makeCommandLine(this.program, this.args, this.forceQuotesEnabled)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
                Pair(program, cmdStr)
            }
        cmdStr1.push(0)
        // add null terminator
        // stolen from the libuv code.
        var flags = this.flags or CREATE_UNICODE_ENVIRONMENT
        if (this.detach) {
            flags = flags or (DETACHED_PROCESS or CREATE_NEW_PROCESS_GROUP)
        }
        val inheritHandles = this.inheritHandles.toInt()
        val (envp, data) = when (val tryResult = makeEnvp(maybeEnv)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val (dirp, data1) = when (val tryResult = makeDirp(this.cwd)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val pi = zeroedProcessInformation()
        val pipes = StdioPipes(stdin = null, stdout = null, stderr = null)
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
        val stdin1 = when (val tryResult = stdin.toHandle(STD_INPUT_HANDLE, pipes.stdin)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val stdout1 = when (val tryResult = stdout.toHandle(STD_OUTPUT_HANDLE, pipes.stdout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val stderr1 = when (val tryResult = stderr.toHandle(STD_ERROR_HANDLE, pipes.stderr)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        val localStorageL364C8 = AddressableLocal(zeroedStartupinfo(), UnresolvedTypeLayout<Startupinfow>())
        val si by localStorageL364C8
        // If at least one of stdin, stdout or stderr are set (i.e. are non null)
        // then set the `hStd` fields in `STARTUPINFO`.
        // Otherwise skip this and allow the OS to apply its default behavior.
        // This provides more consistent behavior between Win7 and Win8+.
        val isSet = { stdio: Handle -> !stdio.asRawHandle().isNull() }
        if (isSet(stderr1) || isSet(stdout1) || isSet(stdin1)) {
            si.dwFlags = si.dwFlags or STARTF_USESTDHANDLES
            si.hStdInput = stdin1.asRawHandle()
            si.hStdOutput = stdout1.asRawHandle()
            si.hStdError = stderr1.asRawHandle()
        }
        when (val matchVal = this.showWindow) {
            null -> {}
            else -> {
                val cmdShow = matchVal
                si.dwFlags = si.dwFlags or STARTF_USESHOWWINDOW
                si.wShowWindow = cmdShow
            }
        }
        if (this.startupinfoFullscreen) {
            si.dwFlags = si.dwFlags or STARTF_RUNFULLSCREEN
        }
        if (this.startupinfoUntrustedSource) {
            si.dwFlags = si.dwFlags or STARTF_UNTRUSTEDSOURCE
        }
        when (val matchVal1 = this.startupinfoForceFeedback) {
            true -> {
                si.dwFlags = si.dwFlags or STARTF_FORCEONFEEDBACK
            }
            false -> {
                si.dwFlags = si.dwFlags or STARTF_FORCEOFFFEEDBACK
            }
            null -> {}
        }
        when (val matchVal2 = this.desktop) {
            null -> {}
            else -> {
                val desktop = matchVal2
                si.lpDesktop = desktop.asMutPtr()
            }
        }
        val siPtr: MutPtr
        var siEx: Any?
        if (procThreadAttributeList != null) {
            val procThreadAttributeList1 = procThreadAttributeList
            si.cb = sizeOf<Startupinfoexw>(typeLayoutT = UnresolvedTypeLayout<Startupinfoexw>()).toUInt()
            flags = flags or EXTENDED_STARTUPINFO_PRESENT
            siEx = Startupinfoexw(startupInfo = si, lpAttributeList = procThreadAttributeList1.asPtr().cast().castMut())
            siPtr = mutPtrFromRustCast((addrOfMut(siEx)))
        } else {
            si.cb = sizeOf<Startupinfow>(typeLayoutT = UnresolvedTypeLayout<Startupinfow>()).toUInt()
            siPtr = (localStorageL364C8.asMutPtr())
        }
        when (val tryResult = cvt(createProcessW(program1.asPtr(), cmdStr1.asMutPtr(), MutPtr(0L), MutPtr(0L), inheritHandles, flags, envp, dirp, siPtr, pi))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Process, StdioPipes>, Error>(tryResult.errValue()) }
        return Result.Ok<Pair<Process, StdioPipes>, Error>(Pair(Process(handle = Handle.fromRawHandle(pi.hProcess), mainThreadHandle = Handle.fromRawHandle(pi.hThread)), pipes))
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        when (val tryResult = this.program.fmt(f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(tryResult.errValue()) }
        for (arg in this.args) {
            f.append(" ")
            when (val tryResult = when (arg) {
                is Arg.Regular -> {
                    val (s) = arg
                    s.fmt(f)
                }
                is Arg.Raw -> {
                    val (s1) = arg
                    f.append(s1.toStringLossy())
                }
            }) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
    }

    public companion object {
        public fun new(program: OsStr): Command = Command(program = program.toOsString(), args = mutableListOf(), env = CommandEnv.default(), cwd = null, flags = 0u, showWindow = null, detach = false, stdin = null, stdout = null, stderr = null, forceQuotesEnabled = false, startupinfoFullscreen = false, startupinfoUntrustedSource = false, startupinfoForceFeedback = null, inheritHandles = true, desktop = null)
    }
}

public sealed class Stdio {
    public data object Inherit : Stdio()
    public data class InheritSpecific(public var fromStdioId: UInt) : Stdio()
    public data object Null : Stdio()
    public data object MakePipe : Stdio()
    public class Pipe internal constructor(valueCtorArg: ChildPipe) : Stdio() {
        public var value: ChildPipe = valueCtorArg
            internal set

        public operator fun component1(): ChildPipe = this.value

        public fun copy(value: ChildPipe = this.value): Pipe = Pipe(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Pipe) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: ChildPipe,
            ): Pipe = Pipe(
                valueCtorArg = value,
            )
        }
    }
    public class Handle internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle) : Stdio() {
        public var value: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = this.value): Handle = Handle(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Handle) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle,
            ): Handle = Handle(
                valueCtorArg = value,
            )
        }
    }

    public open fun toHandle(stdioId: UInt, pipe: ChildPipe?): Result<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle, Error> {
        var pipe1 = pipe
        val useStdioId =
            { stdioId1 -> run {
                val matchVal = getHandle(stdioId1)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val io = matchVal.okValue()
                    run {
                        val io1 = Handle.fromRawHandle(io)
                        val ret = io1.duplicate(0, true, DUPLICATE_SAME_ACCESS)
                        io1.intoRawHandle()
                        // Don't close the handle
                        ret
                    }
                }; is Result.Err<*, *> -> {
                    Result.Ok(Handle.fromRawHandle(MutPtr(0L)))
                } }
            } }
        return when (this) {
            Stdio.Inherit -> useStdioId(stdioId)
            is Stdio.InheritSpecific -> {
                val fromStdioId = (this as Stdio.InheritSpecific).fromStdioId
                useStdioId(fromStdioId)
            }
            Stdio.MakePipe -> {
                val oursReadable = stdioId != STD_INPUT_HANDLE
                val pipes = when (val tryResult = childPipe(oursReadable, true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle, Error>(tryResult.errValue()) }
                pipe1 = pipes.ours
                Result.Ok<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle, Error>(pipes.theirs.intoHandle())
            }
            is Stdio.Pipe -> {
                val (source) = this as Stdio.Pipe
                val oursReadable1 = stdioId != STD_INPUT_HANDLE
                spawnPipeRelay(source, oursReadable1, true).map(ChildPipe::intoHandle)
            }
            is Stdio.Handle -> {
                val (handle) = this as Stdio.Handle
                handle.duplicate(0, true, DUPLICATE_SAME_ACCESS)
            }
            // Open up a reference to NUL with appropriate read/write
            // permissions as well as the ability to be inherited to child
            // processes (as this is about to be inherited).
            Stdio.Null -> {
                val opts = OpenOptions.new()
                opts.read(stdioId == STD_INPUT_HANDLE)
                opts.write(stdioId != STD_INPUT_HANDLE)
                opts.inheritHandle(true)
                File.open(Path.new("\\\\.\\NUL"), opts).map({ file -> file.intoInner() }).mapErr({ e ->
                    // A raw `NotFound` here is easily mistaken for the program
                    // being missing, so say what actually failed to open.
                    // `spawn` only passes the three standard ids, but print
                    // anything else as a number rather than mislabeling it.
                    val stream =
                        when (stdioId) {
                            STD_INPUT_HANDLE -> "stdin".toString()
                            STD_OUTPUT_HANDLE -> "stdout".toString()
                            STD_ERROR_HANDLE -> "stderr".toString()
                            else -> {
                                val id = stdioId
                                "stdio handle $id"
                            }
                        }
                    Error.new(e.kind(), "failed to open NUL device for child $stream: $e")
                })
            }
        }
    }

    public companion object {
        public fun from(pipe: ChildPipe): Stdio = Stdio.Pipe(pipe)

        public fun from(pipe: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle): Stdio = Stdio.Handle(pipe)

        public fun from(file: File): Stdio = Stdio.Handle(file.intoInner())

        public fun from(arg0: Stdout): Stdio = Stdio.InheritSpecific(fromStdioId = STD_OUTPUT_HANDLE)

        public fun from(arg0: Stderr): Stdio = Stdio.InheritSpecific(fromStdioId = STD_ERROR_HANDLE)
    }
}

// Resolve `exePath` to the executable name.
//
// * If the path is simply a file name then use the paths given by `searchPaths` to find the executable.
// * Otherwise use the `exePath` as given.
//
// This function may also append `.exe` to the name. The rationale for doing so is as follows:
//
// It is a very strong convention that Windows executables have the `exe` extension.
// In Rust, it is common to omit this extension.
// Therefore this functions first assumes `.exe` was intended.
// It falls back to the plain file name if a full path is given and the extension is omitted
// or if only a file name is given and it already contains an extension.
public fun resolveExe(exePath: OsStr, parentPaths: () -> OsString?, childPaths: OsStr?): Result<List<UShort>, Error> {
    // Early return if there is no filename.
    if (exePath.isEmpty() || hasTrailingSlash(exePath)) {
        return Result.Err<List<UShort>, Error>(constError(ErrorKind.InvalidInput, "program path has no file name"))
    }
    // Test if the file name has the `exe` extension.
    // This does a case-insensitive `endsWith`.
    val hasExeSuffix =
        if (exePath.len() >= EXE_SUFFIX.len()) {
            exePath.asEncodedBytes()[exePath.len() - EXE_SUFFIX.len() until exePath.asEncodedBytes().size].eqIgnoreAsciiCase(EXE_SUFFIX.encodeToByteArray().map { it.toUByte() })
        } else {
            false
        }
    // If `exePath` is an absolute path or a sub-path then don't search `PATH` for it.
    if (!isFileName(exePath)) {
        if (hasExeSuffix) {
            // The application name is a path to a `.exe` file.
            // Let `CreateProcessW` figure out if it exists or not.
            return toUserPath(Path.new(exePath))
        }
        var path = PathBuf.from(exePath)
        // Append `.exe` if not already there.
        path = appendSuffix(path, EXE_SUFFIX.asRef())
        when (val matchVal = programExists(path)) {
            null -> {
                // It's ok to use `setExtension` here because the intent is to
                // remove the extension that was just added.
                path.setExtension("")
                return toUserPath(path)
            }
            else -> {
                val path1 = matchVal
                return Result.Ok<List<UShort>, Error>(path1)
            }
        }
    } else {
        when (val tryResult = ensureNoNuls(exePath)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
        // From the `CreateProcessW` docs:
        // > If the file name does not contain an extension, .exe is appended.
        // Note that this rule only applies when searching paths.
        val hasExtension = exePath.asEncodedBytes().contains(46u)
        // Search the directories given by `searchPaths`.
        val result =
            searchPaths(parentPaths, childPaths, { path2 ->
                path2.push(exePath)
                if (!hasExtension) {
                    path2.setExtension(EXE_EXTENSION)
                }
                programExists(path2)
            })
        if (result != null) {
            val path2 = result
            return Result.Ok<List<UShort>, Error>(path2)
        }
    }
    // If we get here then the executable cannot be found.
    return Result.Err<List<UShort>, Error>(constError(ErrorKind.NotFound, "program not found"))
}

// Calls `f` for every path that should be used to find an executable.
// Returns once `f` returns the path to an executable or all paths have been searched.
public fun searchPaths(parentPaths: () -> OsString?, childPaths: OsStr?, exists: (PathBuf) -> List<UShort>?): List<UShort>? {
    // 1. Child paths
    // This is for consistency with Rust's historic behavior.
    if (childPaths != null) {
        val paths = childPaths
        for (path in splitPaths(paths).filter({ p -> !p.asOsStr().isEmpty() })) {
            when (val matchVal = exists(path)) {
                null -> {}
                else -> {
                    val path1 = matchVal
                    return path1
                }
            }
        }
    }
    // 2. Application path
    when (val matchVal1 = currentExe()) {
        is Result.Ok<*, *> -> {
            val appPath = matchVal1.okValue()
            appPath.pop()
            when (val matchVal2 = exists(appPath)) {
                null -> {}
                else -> {
                    val path2 = matchVal2
                    return path2
                }
            }
        }
        else -> {}
    }
    // 3 & 4. System paths
    // SAFETY: This uses `fillUtf16Buf` to safely call the OS functions.
    run {
        run {
            val matchVal3 = fillUtf16Buf({ buf, size -> getSystemDirectoryW(buf, size) }, { buf -> exists(PathBuf.from(OsString.fromWide(buf))) })
            if (matchVal3 is Result.Ok<*, *> && matchVal3.okValue() != null) {
                val okValueVal = matchVal3.okValue()
                val path3 = okValueVal
                return path3
            }
        }
        run {
            val matchVal4 = fillUtf16Buf({ buf, size -> getWindowsDirectoryW(buf, size) }, { buf -> exists(PathBuf.from(OsString.fromWide(buf))) })
            if (matchVal4 is Result.Ok<*, *> && matchVal4.okValue() != null) {
                val okValueVal1 = matchVal4.okValue()
                val path4 = okValueVal1
                return path4
            }
        }
    }
    // 5. Parent paths
    when (val matchVal5 = parentPaths()) {
        null -> {}
        else -> {
            val parentPaths1 = matchVal5
            for (path5 in splitPaths(parentPaths1).filter({ p -> !p.asOsStr().isEmpty() })) {
                when (val matchVal6 = exists(path5)) {
                    null -> {}
                    else -> {
                        val path6 = matchVal6
                        return path6
                    }
                }
            }
        }
    }
    return null
}

/**
 * Checks if a file exists without following symlinks.
 */
public fun programExists(path: Path): List<UShort>? {
    return run {
        val path1 = (toUserPath(path).ok() ?: return null)
        // Getting attributes using `GetFileAttributesW` does not follow symlinks
        // and it will almost always be successful if the link exists.
        // There are some exceptions for special system files (e.g. the pagefile)
        // but these are not executable.
        if (getFileAttributesW(path1.asPtr()) == INVALID_FILE_ATTRIBUTES) {
            null
        } else {
            path1
        }
    }
}

// ///////////////////////////////////////////////////////////////////////////
// Processes
// ///////////////////////////////////////////////////////////////////////////

/**
 * A value representing a child process.
 *
 * The scope of this value is linked to the scope of the actual
 * process - the Process destructor calls self.finish() which waits
 * for the process to terminate.
 */
public open class Process internal constructor(
    handleCtorArg: Handle,
    mainThreadHandleCtorArg: Handle,
) {
    public var handle: Handle = handleCtorArg
        internal set

    public var mainThreadHandle: Handle = mainThreadHandleCtorArg
        internal set

    public open fun kill(): Result<Unit, Error> {
        val result = terminateProcess(this.handle.asRawHandle(), 1u)
        if (result == FALSE) {
            val error = getLastError()
            // TerminateProcess returns ERROR_ACCESS_DENIED if the process has already been
            // terminated (by us, or for any other reason). So check if the process was actually
            // terminated, and if so, do not return an error.
            if (error != WinError.ACCESS_DENIED || this.tryWait().isErr()) {
                return Result.Err<Unit, Error>(Error.fromRawOsError(error.code))
            }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun id(): UInt = getProcessId(this.handle.asRawHandle())

    public open fun mainThreadHandle(): BorrowedHandle = this.mainThreadHandle.asHandle()

    public open fun wait(): Result<ExitStatus, Error> {
        return run {
            val res = waitForSingleObject(this.handle.asRawHandle(), INFINITE)
            if (res != WAIT_OBJECT_0) {
                return Result.Err<ExitStatus, Error>(Error.lastOsError())
            }
            val localStorageL753C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            val status by localStorageL753C12
            when (val tryResult = cvt(getExitCodeProcess(this.handle.asRawHandle(), RustMutableReference(localStorageL753C12.asMutPtr(), localStorageL753C12.layout)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus, Error>(tryResult.errValue()) }
            Result.Ok<ExitStatus, Error>(ExitStatus(status))
        }
    }

    public open fun tryWait(): Result<ExitStatus?, Error> {
        return run {
            when (waitForSingleObject(this.handle.asRawHandle(), 0u)) {
                WAIT_OBJECT_0 -> {}
                WAIT_TIMEOUT -> {
                    return Result.Ok<ExitStatus?, Error>(null)
                }
                else -> { return Result.Err<ExitStatus?, Error>(Error.lastOsError()) }
            }
            val localStorageL768C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            val status by localStorageL768C12
            when (val tryResult = cvt(getExitCodeProcess(this.handle.asRawHandle(), RustMutableReference(localStorageL768C12.asMutPtr(), localStorageL768C12.layout)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus?, Error>(tryResult.errValue()) }
            Result.Ok<ExitStatus?, Error>(ExitStatus(status))
        }
    }

    public open fun handle(): Handle = this.handle

    public open fun intoHandle(): Handle = this.handle

    public companion object {
        public operator fun invoke(
            handle: Handle,
            mainThreadHandle: Handle,
        ): Process = Process(
            handleCtorArg = handle,
            mainThreadHandleCtorArg = mainThreadHandle,
        )
    }
}

public open class ExitStatus(
    public var value: UInt,
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatus = ExitStatus(value)

    public open fun exitOk(): Result<Unit, ExitStatusError> =
        run {
            val matchVal = NonZero.tryFrom(this.value)
            when (matchVal) { is Result.Ok<*, *> -> {
                val failure = matchVal.okValue()
                Result.Err<Unit, ExitStatusError>(ExitStatusError(failure))
            }; is Result.Err<*, *> -> {
                Result.Ok<Unit, ExitStatusError>(Unit)
            } }
        }

    public open fun code(): Int? = this.value.toInt()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        // Windows exit codes with the high bit set typically mean some form of
        // unhandled exception or warning. In this scenario printing the exit
        // code in decimal doesn't always make sense because it's a very large
        // and somewhat gibberish number. The hex code is a bit more
        // recognizable and easier to search for, so print that.
        return if (this.value and 0x80000000u != 0u) {
            run { f.append("exit code: ${this.value}"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
        } else {
            run { f.append("exit code: ${this.value}"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ExitStatus> =
            object : TypeLayout<ExitStatus> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ExitStatus) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitStatus) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Converts a raw `UInt` to a type-safe `ExitStatus` by wrapping it without copying.
         */
        public fun from(u: UInt): ExitStatus = ExitStatus(u)
    }
}

public open class ExitStatusError internal constructor(
    valueCtorArg: NonZero<UInt, NonZeroU32Inner>,
) {
    public var value: NonZero<UInt, NonZeroU32Inner> = valueCtorArg
        internal set

    public var field0: NonZero<UInt, NonZeroU32Inner>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatusError = ExitStatusError(value)

    public open fun into(): ExitStatus = ExitStatus(this.value.into())

    public open fun code(): NonZero<Int, NonZeroI32Inner>? = (UInt.from(this.value).toInt()).tryInto().unwrap()

    public companion object {
        public operator fun invoke(
            value: NonZero<UInt, NonZeroU32Inner>,
        ): ExitStatusError = ExitStatusError(
            valueCtorArg = value,
        )
    }
}

public open class ExitCode(
    public var value: UInt,
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitCode = ExitCode(value)

    public open fun asI32(): Int = this.value.toInt()

    public companion object {
        public fun typeLayout(): TypeLayout<ExitCode> =
            object : TypeLayout<ExitCode> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ExitCode =
                    ExitCode(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ExitCode) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitCode =
                    ExitCode(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitCode) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }

        public val SUCCESS: ExitCode = ExitCode(EXIT_SUCCESS)

        public val FAILURE: ExitCode = ExitCode(EXIT_FAILURE)

        public fun from(code: UByte): ExitCode = ExitCode(UInt.from(code))

        public fun from(code: UInt): ExitCode = ExitCode(code)
    }
}

public fun zeroedStartupinfo(): Startupinfow = Startupinfow(cb = 0u, lpReserved = MutPtr(0L), lpDesktop = MutPtr(0L), lpTitle = MutPtr(0L), dwX = 0u, dwY = 0u, dwXSize = 0u, dwYSize = 0u, dwXCountChars = 0u, dwYCountChars = 0u, dwFillAttribute = 0u, dwFlags = 0u, wShowWindow = 0u, cbReserved2 = 0u, lpReserved2 = MutPtr(0L), hStdInput = MutPtr(0L), hStdOutput = MutPtr(0L), hStdError = MutPtr(0L))

public fun zeroedProcessInformation(): ProcessInformation = ProcessInformation(hProcess = MutPtr(0L), hThread = MutPtr(0L), dwProcessId = 0u, dwThreadId = 0u)

// Produces a wide string *without terminating null*; returns an error if
// `prog` or any of the `args` contain a nul.
public fun makeCommandLine(argv0: OsStr, args: List<Arg>, forceQuotes: Boolean): Result<List<UShort>, Error> {
    // Encode the command and arguments in a command line string such
    // that the spawned process may recover them using CommandLineToArgvW.
    val cmd: List<UShort>  = mutableListOf()
    // Always quote the program name so CreateProcess to avoid ambiguity when
    // the child process parses its arguments.
    // Note that quotes aren't escaped here because they can't be used in arg0.
    // But that's ok because file paths can't contain quotes.
    cmd.add(34u.toUShort())
    cmd.extend(argv0.encodeWide())
    cmd.add(34u.toUShort())
    for (arg in args) {
        cmd.add((' ').code.toUShort())
        when (val tryResult = appendArg(cmd, arg, forceQuotes)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
    }
    return Result.Ok<List<UShort>, Error>(cmd)
}

// Get `cmd.exe` for use with bat scripts, encoded as a UTF-16 string.
public fun commandPrompt(): Result<List<UShort>, Error> {
    val system: List<UShort>  = when (val tryResult = fillUtf16Buf({ buf, size -> getSystemDirectoryW(buf, size) }, { buf -> buf.into() })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
    system.extend("\\cmd.exe".encodeUtf16().chain(listOf(0)))
    return Result.Ok<List<UShort>, Error>(system)
}

public fun makeEnvp(maybeEnv: MutableMap<EnvKey, OsString>?): Result<Pair<MutPtr, List<UShort>>, Error> {
    // On Windows we pass an "environment block" which is not a char**, but
    // rather a concatenation of null-terminated k=v\0 sequences, with a final
    // \0 to terminate.
    return if (maybeEnv != null) {
        val env = maybeEnv
        val blk = mutableListOf<UShort>()
        // If there are no environment variables to set then signal this by
        // pushing a null.
        if (env.isEmpty()) {
            blk.add(0)
        }
        for ((k, v) in env) {
            when (val tryResult = ensureNoNuls(k.osString)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<MutPtr, List<UShort>>, Error>(tryResult.errValue()) }
            blk.extend(k.utf16)
            blk.add(('=').code.toUShort())
            blk.extend(when (val tryResult = ensureNoNuls(v)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<MutPtr, List<UShort>>, Error>(tryResult.errValue()) }.encodeWide())
            blk.add(0)
        }
        blk.add(0)
        Result.Ok<Pair<MutPtr, List<UShort>>, Error>(Pair((blk.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, blk))
    } else {
        Result.Ok<Pair<MutPtr, List<UShort>>, Error>(Pair(MutPtr(0L), mutableListOf()))
    }
}

public fun makeDirp(d: OsString?): Result<Pair<ConstPtr, List<UShort>>, Error> {
    return when (d) {
        null -> Result.Ok<Pair<ConstPtr, List<UShort>>, Error>(Pair(ConstPtr(0L), mutableListOf()))
        else -> {
            val dir = d
            var dirStr: List<UShort> = (when (val tryResult = ensureNoNuls(dir)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<ConstPtr, List<UShort>>, Error>(tryResult.errValue()) }.encodeWide().chain(listOf(0))).toMutableList()
            // Try to remove the `\\?\` prefix, if any.
            // This is necessary because the current directory does not support verbatim paths.
            // However. this can only be done if it doesn't change how the path will be resolved.
            val ptr =
                if (dirStr.startsWith(utf16("\\\\?\\UNC"))) {
                    // Turn the `C` in `UNC` into a `\` so we can then use `\\rest\of\path`.
                    val start = "\\\\?\\UN".len()
                    dirStr[start] = 92u.toUShort()
                    if (isAbsoluteExact(((dirStr).subList(start, (dirStr).size)))) {
                        ((dirStr).subList(start, (dirStr).size)).asPtr()
                    } else {
                        // Revert the above change.
                        dirStr[start] = 67u.toUShort()
                        dirStr.asPtr()
                    }
                } else if (dirStr.startsWith(utf16("\\\\?\\"))) {
                    // Strip the leading `\\?\`
                    val start1 = "\\\\?\\".len()
                    if (isAbsoluteExact(((dirStr).subList(start1, (dirStr).size)))) {
                        ((dirStr).subList(start1, (dirStr).size)).asPtr()
                    } else {
                        dirStr.asPtr()
                    }
                } else {
                    dirStr.asPtr()
                }
            Result.Ok<Pair<ConstPtr, List<UShort>>, Error>(Pair(ptr, dirStr))
        }
    }
}

public open class CommandArgs internal constructor(
    iterCtorArg: Iter<Arg>,
) : Iterable<OsStr> {
    public var iter: Iter<Arg> = iterCtorArg
        internal set

    override fun iterator(): Iterator<OsStr> = object : AbstractIterator<OsStr>() {
        override fun computeNext() {
            when (val v = this@CommandArgs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): OsStr? =
        this.iter.removeFirstOrNull()?.let({ arg -> when (arg) {
            is Arg.Regular -> {
                val (s) = arg
                s.asRef()
            }
            is Arg.Raw -> {
                val (s1) = arg
                s1.asRef()
            }
        } })

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(ArrayDeque(this.iter)).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public operator fun invoke(
            iter: Iter<Arg>,
        ): CommandArgs = CommandArgs(
            iterCtorArg = iter,
        )
    }
}

public fun getpid(): UInt = getCurrentProcessId()

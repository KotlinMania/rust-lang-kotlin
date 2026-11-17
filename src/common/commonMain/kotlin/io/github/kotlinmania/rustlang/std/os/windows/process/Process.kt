// port-lint: source os/windows/process.rs
package io.github.kotlinmania.rustlang.std.os.windows.process

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.OwnedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle
import io.github.kotlinmania.rustlang.std.sys.process.windows.Stdio
import io.github.kotlinmania.rustlang.std.sys.process.windows.childpipe.ChildPipe

// Windows-specific extensions to primitives in the [std.process] module.

public fun fromRawHandleStdio(handle: RawHandle): process.Stdio {
    val handle1 = Handle.Handle.fromRawHandle(MutPtr(handle.rawAddress, handle.rawMeta))
    val io = Stdio.Handle(handle1)
    return process.Stdio.fromInner(io)
}

/**
 * Takes ownership of a handle and returns a [`Stdio`](process.Stdio)
 * that can attach a stream to it.
 */
public fun fromStdio(handle: OwnedHandle): process.Stdio {
    val handle1 = Handle.Handle.fromInner(handle)
    val io = Stdio.Handle(handle1)
    return process.Stdio.fromInner(io)
}

public fun process.Child.asRawHandle(): RawHandle = mutPtrFromRustCast(this.asInner().handle().asRawHandle())

public fun process.Child.asHandle(): BorrowedHandle = this.asInner().handle().asHandle()

public fun process.Child.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoHandle().intoRawHandle())

/**
 * Takes ownership of a [`Child`](process.Child)'s process handle.
 */
public fun fromOwnedHandle(child: process.Child): OwnedHandle = child.intoInner().intoHandle().intoInner()

public fun process.ChildStdin.asRawHandle(): RawHandle = mutPtrFromRustCast(this.asInner().handle().asRawHandle())

public fun process.ChildStdout.asRawHandle(): RawHandle = mutPtrFromRustCast(this.asInner().handle().asRawHandle())

public fun process.ChildStderr.asRawHandle(): RawHandle = mutPtrFromRustCast(this.asInner().handle().asRawHandle())

public fun process.ChildStdin.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoHandle().intoRawHandle())

public fun process.ChildStdout.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoHandle().intoRawHandle())

public fun process.ChildStderr.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoHandle().intoRawHandle())

/**
 * Creates a `ChildStdin` from the provided `OwnedHandle`.
 *
 * The provided handle must be asynchronous, as reading and
 * writing from and to it is implemented using asynchronous APIs.
 */
public fun fromChildStdin(handle: OwnedHandle): process.ChildStdin {
    val handle1 = Handle.Handle.fromInner(handle)
    val pipe = ChildPipe.fromInner(handle1)
    return process.ChildStdin.fromInner(pipe)
}

/**
 * Creates a `ChildStdout` from the provided `OwnedHandle`.
 *
 * The provided handle must be asynchronous, as reading and
 * writing from and to it is implemented using asynchronous APIs.
 */
public fun fromChildStdout(handle: OwnedHandle): process.ChildStdout {
    val handle1 = Handle.Handle.fromInner(handle)
    val pipe = ChildPipe.fromInner(handle1)
    return process.ChildStdout.fromInner(pipe)
}

/**
 * Creates a `ChildStderr` from the provided `OwnedHandle`.
 *
 * The provided handle must be asynchronous, as reading and
 * writing from and to it is implemented using asynchronous APIs.
 */
public fun fromChildStderr(handle: OwnedHandle): process.ChildStderr {
    val handle1 = Handle.Handle.fromInner(handle)
    val pipe = ChildPipe.fromInner(handle1)
    return process.ChildStderr.fromInner(pipe)
}

/**
 * Windows-specific extensions to [process.ExitStatus].
 */
public interface ExitStatusExt<T> {
    /**
     * Creates a new `ExitStatus` from the raw underlying `UInt` return value of
     * a process.
     */
    public fun fromRaw(raw: UInt): T

    public companion object {
        public fun fromRaw(raw: UInt): process.ExitStatus = exitStatusExtFromRawExitStatus(raw)
    }
}

public fun exitStatusExtFromRawExitStatus(raw: UInt): process.ExitStatus = process.ExitStatus.fromInner(From.from(raw))

/**
 * Windows-specific extensions to the [process.Command] builder.
 */
public interface CommandExt {
    /**
     * Sets the [process creation flags][1] to be passed to `CreateProcess`.
     *
     * These will always be ORed with `CREATE_UNICODE_ENVIRONMENT`.
     *
     * [1]: https://docs.microsoft.com/en-us/windows/win32/procthread/process-creation-flags
     */
    public fun creationFlags(flags: UInt): process.Command

    /**
     * Places the child process on the desktop named `desktop` by setting the
     * `lpDesktop` field of the [STARTUPINFO][1] passed to `CreateProcess`.
     *
     * The name may be a desktop or a `window-station\desktop` path.
     *
     * [1]: <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfow>
     */
    public fun desktop(desktop: OsStr): process.Command

    /**
     * Sets the field `wShowWindow` of [STARTUPINFO][1] that is passed to `CreateProcess`.
     * Allowed values are the ones listed in
     * <https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-showwindow>
     *
     * [1]: <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfow>
     */
    public fun showWindow(cmdShow: UShort): process.Command

    /**
     * Forces all arguments to be wrapped in quote (`"`) characters.
     *
     * This is useful for passing arguments to [MSYS2/Cygwin][1] based
     * executables: these programs will expand unquoted arguments containing
     * wildcard characters (`?` and `*`) by searching for any file paths
     * matching the wildcard pattern.
     *
     * Adding quotes has no effect when passing arguments to programs
     * that use [msvcrt][2]. This includes programs built with both
     * MinGW and MSVC.
     *
     * [1]: <https://github.com/msys2/MSYS2-packages/issues/2176>
     * [2]: <https://msdn.microsoft.com/en-us/library/17w5ykft.aspx>
     */
    public fun forceQuotes(enabled: Boolean): process.Command

    /**
     * Append literal text to the command line without any quoting or escaping.
     *
     * This is useful for passing arguments to applications that don't follow
     * the standard C run-time escaping rules, such as `cmd.exe / c`.
     *
     * # Batch files
     *
     * Note the `cmd / c` command line has slightly different escaping rules than batch files
     * themselves. If possible, it may be better to write complex arguments to a temporary
     * `.bat` file, with appropriate escaping, and simply run that using:
     *
     * ```kotlin
     * val output = Command.new("cmd").args(listOf("/c", "\"$tempBatFile\"")).output()
     * ```
     *
     * # Example
     *
     * Run a batch script using both trusted and untrusted arguments.
     *
     * ```kotlin
     * import std.io.Error
     * import std.io.ErrorKind
     * import std.os.windows.process.CommandExt
     * import std.process.Command
     *
     * // `myScriptPath` is a path to known bat file.
     * // `userName` is an untrusted name given by the user.
     * fun runScript(myScriptPath: String, userName: String): Result<Output> {
     *     // Create the command line, making sure to quote the script path.
     *     // This assumes the fixed arguments have been tested to work with the script we're using.
     *     var cmdArgs = "\"$myScriptPath\" \"--features=[a,b,c]\""
     *     // Make sure the user name is safe. In particular we need to be
     *     // cautious of ascii symbols that cmd may interpret specially.
     *     // Here we only allow alphanumeric characters.
     *     if (!userName.chars().all({ c -> c.isAlphanumeric() })) {
     *         return Result.failure(Error.new(ErrorKind.InvalidInput, "invalid user name"))
     *     }
     *     // now we have validated the user name, let's add that too.
     *     cmdArgs.pushStr(" --user ")
     *     cmdArgs.pushStr(userName)
     *     // call cmd.exe and return the output
     *     return Command.new("cmd.exe").arg("/c").rawArg("\"$cmdArgs\"").output()
     * }
     * ````
     */
    public fun rawArg(textToAppendAsIs: OsStr): process.Command

    /**
     * When [process.Command] creates pipes, request that our side is always async.
     *
     * By default [process.Command] may choose to use pipes where both ends
     * are opened for synchronous read or write operations. By using
     * `asyncPipes(true)`, this behavior is overridden so that our side is
     * always async.
     *
     * This is important because if doing async I/O a pipe or a file has to be
     * opened for async access.
     *
     * The end of the pipe sent to the child process will always be synchronous
     * regardless of this option.
     *
     * # Example
     *
     * @file:feature(windowsProcessExtensionsAsyncPipes)
     * use std.os.windows.process.CommandExt;
     * use std.process::(Command, Stdio);
     *
     * # let program = "";
     *
     * Command.new(program)
     *     .asyncPipes(true)
     *     .stdin(Stdio.piped())
     *     .stdout(Stdio.piped())
     *     .stderr(Stdio.piped());
     * ```kotlin
     */
    public fun asyncPipes(alwaysAsync: Boolean): process.Command

    /**
     * Executes the command as a child process with the given
     * [ProcThreadAttributeList], returning a handle to it.
     *
     * This method enables the customization of attributes for the spawned
     * child process on Windows systems.
     * Attributes offer extended configurability for process creation,
     * but their usage can be intricate and potentially unchecked.
     *
     * # Note
     *
     * By default, stdin, stdout, and stderr are inherited from the parent
     * process.
     *
     * # Example
     *
     * @file:feature(windowsProcessExtensionsRawAttribute)
     * use std.os.windows.io.AsRawHandle;
     * use std.os.windows.process::(CommandExt, ProcThreadAttributeList);
     * use std.process.Command;
     *
     * # struct ProcessDropGuard(std.process.Child);
     * # impl Drop for ProcessDropGuard (
     * #     fn drop(self) (
     * #         let _ = self.0.kill();
     * #     )
     * # )
     * #
     * let parent = Command.new("cmd").spawn()?;
     * let parentProcessHandle = parent.asRawHandle();
     * # let parent = ProcessDropGuard(parent);
     *
     * const PROC_THREAD_ATTRIBUTE_PARENT_PROCESS: Int = 0x00020000;
     * let mut attributeList = ProcThreadAttributeList.build()
     *     .attribute(PROC_THREAD_ATTRIBUTE_PARENT_PROCESS, &parentProcessHandle)
     *     .finish()
     *     .unwrap();
     *
     * let mut child = Command.new("cmd").spawnWithAttributes(&attributeList)?;
     * #
     * # child.kill()?;
     * # Ok::<(), std.io.Error>(())
     * ```kotlin
     */
    public fun spawnWithAttributes(attributeList: ProcThreadAttributeList): Result<process.Child, Error>

    /**
     * When true, sets the `STARTF_RUNFULLSCREEN` flag on the [STARTUPINFO][1] struct before passing it to `CreateProcess`.
     *
     * [1]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfoa
     */
    public fun startupinfoFullscreen(enabled: Boolean): process.Command

    /**
     * When true, sets the `STARTF_UNTRUSTEDSOURCE` flag on the [STARTUPINFO][1] struct before passing it to `CreateProcess`.
     *
     * [1]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfoa
     */
    public fun startupinfoUntrustedSource(enabled: Boolean): process.Command

    /**
     * When specified, sets the following flags on the [STARTUPINFO][1] struct before passing it to `CreateProcess`:
     * - If `Some(true)`, sets `STARTF_FORCEONFEEDBACK`
     * - If `Some(false)`, sets `STARTF_FORCEOFFFEEDBACK`
     * - If `None`, does not set any flags
     *
     * [1]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfoa
     */
    public fun startupinfoForceFeedback(enabled: Boolean?): process.Command

    /**
     * If this flag is set to `true`, each inheritable handle in the calling
     * process is inherited by the new process. If the flag is `false`, the
     * handles are not inherited.
     *
     * The default value for this flag is `true`.
     *
     * **Note** that inherited handles have the same value and access rights
     * as the original handles. For additional discussion of inheritable handles,
     * see the [Remarks][1] section of the `CreateProcessW` documentation.
     *
     * [1]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createprocessw#remarks
     */
    public fun inheritHandles(inheritHandles: Boolean): process.Command
}

public fun process.Command.creationFlags(flags: UInt): process.Command {
    this.asInnerMut().creationFlags(flags)
    return this
}

public fun process.Command.desktop(desktop: OsStr): process.Command {
    this.asInnerMut().desktop(desktop)
    return this
}

public fun process.Command.showWindow(cmdShow: UShort): process.Command {
    this.asInnerMut().showWindow(cmdShow)
    return this
}

public fun process.Command.forceQuotes(enabled: Boolean): process.Command {
    this.asInnerMut().forceQuotes(enabled)
    return this
}

public fun process.Command.rawArg(rawText: OsStr): process.Command {
    this.asInnerMut().rawArg(rawText)
    return this
}

public fun process.Command.asyncPipes(alwaysAsync: Boolean): process.Command {
    // Note: This currently has an intentional no-op implementation.
    // For the time being our side of the pipes will always be async.
    // Once the ecosystem has adjusted, we may then be able to start making
    // use of synchronous pipes within the standard library.
    return this
}

public fun process.Command.spawnWithAttributes(attributeList: ProcThreadAttributeList): Result<process.Child, Error> = this.asInnerMut().spawnWithAttributes(Stdio.Inherit, true, attributeList).map(process.Child::fromInner)

public fun process.Command.startupinfoFullscreen(enabled: Boolean): process.Command {
    this.asInnerMut().startupinfoFullscreen(enabled)
    return this
}

public fun process.Command.startupinfoUntrustedSource(enabled: Boolean): process.Command {
    this.asInnerMut().startupinfoUntrustedSource(enabled)
    return this
}

public fun process.Command.startupinfoForceFeedback(enabled: Boolean?): process.Command {
    this.asInnerMut().startupinfoForceFeedback(enabled)
    return this
}

public fun process.Command.inheritHandles(inheritHandles: Boolean): process.Command {
    this.asInnerMut().inheritHandles(inheritHandles)
    return this
}

public interface ChildExt<T> {
    /**
     * Extracts the main thread raw handle, without taking ownership
     */
    public fun mainThreadHandle(): BorrowedHandle
}

public fun process.Child.mainThreadHandle(): BorrowedHandle = this.handle.mainThreadHandle()

/**
 * Windows-specific extensions to [process.ExitCode].
 */
public interface ExitCodeExt<T> {
    /**
     * Creates a new `ExitCode` from the raw underlying `UInt` return value of
     * a process.
     *
     * The exit code should not be 259, as this conflicts with the `STILL_ACTIVE`
     * macro returned from the `GetExitCodeProcess` function to signal that the
     * process has yet to run to completion.
     */
    public fun fromRaw(raw: UInt): T

    public companion object {
        public fun fromRaw(raw: UInt): process.ExitCode = exitCodeExtFromRawExitCode(raw)
    }
}

public fun exitCodeExtFromRawExitCode(raw: UInt): process.ExitCode = process.ExitCode.fromInner(From.from(raw))

/**
 * A wrapper around windows [`ProcThreadAttributeList`][1].
 *
 * [1]: <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-initializeprocthreadattributelist>
 */
public open class ProcThreadAttributeList internal constructor(
    attributeListCtorArg: List<MaybeUninit<UByte>>,
    public var lifetimeMarker: Unit,
) {
    public var attributeList: List<MaybeUninit<UByte>> = attributeListCtorArg
        internal set

    /**
     * Returns a pointer to the underling attribute list.
     */
    public open fun asPtr(): ConstPtr = this.attributeList.asPtr()

    /**
     * Deletes the attribute list.
     *
     * This method calls [`DeleteProcThreadAttributeList`][1] to delete the
     * underlying attribute list.
     *
     * [1]: <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-deleteprocthreadattributelist>
     */
    public open fun drop() {
        val lpAttributeList = MutPtr((this.attributeList.asMutPtr()).rawAddress)
        C.DeleteProcThreadAttributeList(lpAttributeList)
    }

    public companion object {
        public operator fun invoke(
            attributeList: List<MaybeUninit<UByte>>,
            lifetimeMarker: Unit,
        ): ProcThreadAttributeList = ProcThreadAttributeList(
            attributeListCtorArg = attributeList,
            lifetimeMarker = lifetimeMarker,
        )

        /**
         * Creates a new builder for constructing a [ProcThreadAttributeList].
         */
        public fun build(): ProcThreadAttributeListBuilder = ProcThreadAttributeListBuilder.new()
    }
}

/**
 * Builder for constructing a [ProcThreadAttributeList].
 */
public open class ProcThreadAttributeListBuilder(
    public var attributes: MutableMap<Int, ProcThreadAttributeValue>,
    public var lifetimeMarker: Unit,
) {
    public open fun clone(): ProcThreadAttributeListBuilder = ProcThreadAttributeListBuilder(attributes, lifetimeMarker)

    /**
     * Sets an attribute on the attribute list.
     *
     * The `attribute` parameter specifies the raw attribute to be set, while
     * the `value` parameter holds the value associated with that attribute.
     * Please refer to the [Windows documentation][1] for a list of valid attributes.
     *
     * # Note
     *
     * The maximum number of attributes is the value of [u32.Max]. If this
     * limit is exceeded, the call to [Self.finish] will return an `Error`
     * indicating that the maximum number of attributes has been exceeded.
     *
     * # Safety Note
     *
     * Remember that improper use of attributes can lead to undefined behavior
     * or security vulnerabilities. Always consult the documentation and ensure
     * proper attribute values are used.
     *
     * [1]: <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-updateprocthreadattribute#parameters>
     */
    public open fun <T> attribute(attribute: Int, value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ProcThreadAttributeListBuilder = this.rawAttribute(attribute, addrOf(value).cast(), sizeOf<T>(typeLayoutT = typeLayoutT))

    /**
     * Sets a raw attribute on the attribute list.
     *
     * This function is useful for setting attributes with pointers or sizes
     * that cannot be derived directly from their values.
     *
     * # Safety
     *
     * This function is marked as `unchecked` because it deals with raw pointers
     * and sizes. It is the responsibility of the caller to ensure the value
     * lives longer than the resulting [ProcThreadAttributeList] as well as
     * the validity of the size parameter.
     *
     * # Example
     *
     * @file:feature(windowsProcessExtensionsRawAttribute)
     * use std.ffi.cVoid;
     * use std.os.windows.process::(CommandExt, ProcThreadAttributeList);
     * use std.os.windows.raw.Handle;
     * use std.process.Command;
     *
     * @repr(C)
     * pub struct COORD (
     *     pub X: i16,
     *     pub Y: i16,
     * )
     *
     * unchecked extern "system" (
     *     fn CreatePipe(
     *         hreadpipe: *mut HANDLE,
     *         hwritepipe: *mut HANDLE,
     *         lppipeattributes: *const cVoid,
     *         nsize: u32,
     *     ) -> i32;
     *     fn CreatePseudoConsole(
     *         size: COORD,
     *         hinput: HANDLE,
     *         houtput: HANDLE,
     *         dwflags: u32,
     *         phpc: *mut isize,
     *     ) -> i32;
     *     fn CloseHandle(hobject: HANDLE) -> i32;
     * )
     *
     * let [mut inputReadSide, mut outputWriteSide, mut outputReadSide, mut inputWriteSide] =
     *     [unchecked ( std.mem.zeroed::<HANDLE>() ); 4];
     *
     * unchecked (
     *     CreatePipe(inputReadSide, inputWriteSide, std.ptr.nullVal(), 0);
     *     CreatePipe(outputReadSide, outputWriteSide, std.ptr.nullVal(), 0);
     * )
     *
     * let size = COORD ( X: 60, Y: 40 );
     * let mut hPc = unchecked ( std.mem.zeroed() );
     * unchecked ( CreatePseudoConsole(size, inputReadSide, outputWriteSide, 0, hPc) );
     *
     * unchecked ( CloseHandle(inputReadSide) );
     * unchecked ( CloseHandle(outputWriteSide) );
     *
     * const PROC_THREAD_ATTRIBUTE_PSEUDOCONSOLE: Int = 131094;
     *
     * let attributeList = unchecked (
     *     ProcThreadAttributeList.build()
     *         .rawAttribute(
     *             PROC_THREAD_ATTRIBUTE_PSEUDOCONSOLE,
     *             hPc as *const cVoid,
     *             sizeOf::<isize>(),
     *         )
     *         .finish()?
     * );
     *
     * let mut child = Command.new("cmd").spawnWithAttributes(&attributeList)?;
     * #
     * # child.kill()?;
     * # Ok::<(), std.io.Error>(())
     * ```kotlin
     */
    public open fun rawAttribute(attribute: Int, valuePtr: ConstPtr, valueSize: Int): ProcThreadAttributeListBuilder {
        this.attributes.put(attribute, ProcThreadAttributeValue(ptr = ConstPtr((valuePtr).rawAddress), size = valueSize))
        return this
    }

    /**
     * Finalizes the construction of the `ProcThreadAttributeList`.
     *
     * # Errors
     *
     * Returns an error if the maximum number of attributes is exceeded
     * or if there is an I/O error during initialization.
     */
    public open fun finish(): Result<ProcThreadAttributeList, Error> {
        // To initialize our ProcThreadAttributeList, we need to determine
        // how many bytes to allocate for it. The Windows API simplifies this
        // process by allowing us to call `InitializeProcThreadAttributeList`
        // with a null pointer to retrieve the required size.
        val localStorageL702C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var requiredSize by localStorageL702C8
        val attributeCount = when (val tryResult = this.attributes.size.tryInto()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ProcThreadAttributeList, Error>(constError(ErrorKind.InvalidInput, "maximum number of ProcThreadAttributes exceeded")) }
        C.InitializeProcThreadAttributeList(MutPtr(0L), attributeCount, 0, RustMutableReference(localStorageL702C8.asMutPtr(), localStorageL702C8.layout))
        var attributeList = mutableListOf(MaybeUninit.uninit(UnresolvedTypeLayout<Result<ProcThreadAttributeList, Error>>()), requiredSize).intoBoxedSlice()
        // Once we've allocated the necessary memory, it's safe to invoke
        // `InitializeProcThreadAttributeList` to properly initialize the list.
        when (val tryResult = cvt(C.InitializeProcThreadAttributeList(attributeList.asMutPtr().cast(), attributeCount, 0, RustMutableReference(localStorageL702C8.asMutPtr(), localStorageL702C8.layout)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ProcThreadAttributeList, Error>(tryResult.error) }
        // # Add our attributes to the buffer.
        // It's theoretically possible for the attribute count to exceed a u32
        // value. Therefore, we ensure that we don't add more attributes than
        // the buffer was initialized for.
        for ((attribute, value) in this.attributes.iter().take(attributeCount.toInt())) {
            when (val tryResult = cvt(C.UpdateProcThreadAttribute(attributeList.asMutPtr().cast(), 0, attribute, value.ptr, value.size, MutPtr(0L), MutPtr(0L)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ProcThreadAttributeList, Error>(tryResult.error) }
        }
        return Result.Ok<ProcThreadAttributeList, Error>(ProcThreadAttributeList(attributeList = attributeList, lifetimeMarker = Unit))
    }

    public companion object {
        public fun new(): ProcThreadAttributeListBuilder = ProcThreadAttributeListBuilder(attributes = mutableMapOf(), lifetimeMarker = Unit)
    }
}

/**
 * Wrapper around the value data to be used as a Process Thread Attribute.
 */
public open class ProcThreadAttributeValue internal constructor(
    ptrCtorArg: ConstPtr,
    public var size: Int,
) {
    public var ptr: ConstPtr = ptrCtorArg
        internal set

    public open fun clone(): ProcThreadAttributeValue = ProcThreadAttributeValue(ptr, size)

    public companion object {
        public fun typeLayout(): TypeLayout<ProcThreadAttributeValue> =
            object : TypeLayout<ProcThreadAttributeValue> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ProcThreadAttributeValue =
                    ProcThreadAttributeValue(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: ProcThreadAttributeValue) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.ptr)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcThreadAttributeValue =
                    ProcThreadAttributeValue(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcThreadAttributeValue) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.ptr)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.size)
                }
            }

        public operator fun invoke(
            ptr: ConstPtr,
            size: Int,
        ): ProcThreadAttributeValue = ProcThreadAttributeValue(
            ptrCtorArg = ptr,
            size = size,
        )
    }
}

// port-lint: source os/windows/io/handle.rs
package io.github.kotlinmania.rustlang.std.os.windows.io

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.Rc
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.std.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.std.io.stdio.IsTerminal
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.StderrLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdin
import io.github.kotlinmania.rustlang.std.io.stdio.StdinLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.io.stdio.StdoutLock
import io.github.kotlinmania.rustlang.std.io.stdio.isTerminal
import io.github.kotlinmania.rustlang.std.os.windows.raw.AsRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.raw.FromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.raw.IntoRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.raw.RawHandle
import io.github.kotlinmania.rustlang.std.process.ChildStderr
import io.github.kotlinmania.rustlang.std.process.ChildStdin
import io.github.kotlinmania.rustlang.std.process.ChildStdout
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.thread.joinhandle.JoinHandle

// Owned and borrowed OS handles.

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

/**
 * A borrowed handle.
 *
 * This has a scope parameter to tie it to the scope of something that
 * owns the handle.
 *
 * This uses `repr(transparent)` and has the representation of a host handle,
 * so it can be used in FFI in places where a handle is passed as an argument,
 * it is not captured or consumed.
 *
 * Note that it *may* have the value `- 1`, which in `BorrowedHandle` always
 * represents a valid handle value, such as [the current process handle], and
 * not `INVALID_HANDLE_VALUE`, despite the two having the same value. See
 * [here] for the full story.
 *
 * And, it *may* have the value `NULL` (0), which can occur when consoles are
 * detached from processes, or when `windowsSubsystem` is used.
 *
 * This type's `.toOwned()` implementation returns another `BorrowedHandle`
 * rather than an `OwnedHandle`. It just makes a trivial copy of the raw
 * handle, which is then borrowed under the same scope.
 *
 * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
 * [the current process handle]: https://docs.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-getcurrentprocess#remarks
 */
public open class BorrowedHandle internal constructor(
    handleCtorArg: RawHandle,
    public var phantom: Unit,
) : AsRawHandle, IsTerminal, AsHandle, AsRef<File> {
    public var handle: RawHandle = handleCtorArg
        internal set

    public open fun clone(): BorrowedHandle = BorrowedHandle(handle, phantom)

    /**
     * Converts a `BorrowedHandle` into a reference to a file-like type.
     *
     * # @file:feature(fdView)
     * # use std.fs.File;
     * # use std.io;
     * # use std.os.windows.io.AsHandle;
     * let meta = io.stdout().asHandle().toView::<File>().metadata().unwrap();
     * if meta.isFile() (
     *     println("stdout is a regular file");
     * )
     * ```kotlin
     */
    public open fun <T> toView(): T = this.asRef()

    /**
     * Creates a new `OwnedHandle` instance that shares the same underlying
     * object as the existing `BorrowedHandle` instance.
     */
    public open fun tryCloneToOwned(): Result<OwnedHandle, Error> = this.duplicate(0, false, C.DUPLICATE_SAME_ACCESS)

    public open fun duplicate(access: UInt, inherit: Boolean, options: UInt): Result<OwnedHandle, Error> {
        val handle = this.asRawHandle()
        // `Stdin`, `Stdout`, and `Stderr` can all hold null handles, such as
        // in a process with a detached console. `DuplicateHandle` would fail
        // if we passed it a null handle, but we can treat null as a valid
        // handle which doesn't do any I/O, and allow it to be duplicated.
        if (handle.isNull()) {
            return Result.Ok<OwnedHandle, Error>(OwnedHandle.FromRawHandle(handle))
        }
        val ret = MutPtr(0L)
        when (val tryResult = cvt(run {
            val curProc = C.GetCurrentProcess()
            C.DuplicateHandle(curProc, handle, curProc, ret, access, inherit.toInt(), options)
        })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OwnedHandle, Error>(tryResult.errValue()) }
        return Result.Ok<OwnedHandle, Error>(OwnedHandle.FromRawHandle(ret))
    }

    public override fun asRawHandle(): RawHandle = this.handle

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "BorrowedHandle").field("handle", this.handle).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.windows.isTerminal(this)

    public override fun asHandle(): BorrowedHandle = this

    public open fun asRef(): File {
        fun <T : AsHandle> typeCheck() {}
        run {
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<File>(typeLayoutT = UnresolvedTypeLayout<File>()))
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<BorrowedHandle>(typeLayoutT = UnresolvedTypeLayout<BorrowedHandle>()))
        }
        typeCheck<File>()
        return transmuteWithLayouts<BorrowedHandle, File>(this, UnresolvedTypeLayout<BorrowedHandle>(), UnresolvedTypeLayout<File>())
    }

    public open fun asRefPipeWriter(): PipeWriter {
        fun <T : AsHandle> typeCheck() {}
        run {
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<PipeWriter>(typeLayoutT = UnresolvedTypeLayout<PipeWriter>()))
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<BorrowedHandle>(typeLayoutT = UnresolvedTypeLayout<BorrowedHandle>()))
        }
        typeCheck<PipeWriter>()
        return transmuteWithLayouts<BorrowedHandle, PipeWriter>(this, UnresolvedTypeLayout<BorrowedHandle>(), UnresolvedTypeLayout<PipeWriter>())
    }

    public open fun asRefPipeReader(): PipeReader {
        fun <T : AsHandle> typeCheck() {}
        run {
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<PipeReader>(typeLayoutT = UnresolvedTypeLayout<PipeReader>()))
            check(sizeOf<RawHandle>(typeLayoutT = UnresolvedTypeLayout<RawHandle>()) == sizeOf<BorrowedHandle>(typeLayoutT = UnresolvedTypeLayout<BorrowedHandle>()))
        }
        typeCheck<PipeReader>()
        return transmuteWithLayouts<BorrowedHandle, PipeReader>(this, UnresolvedTypeLayout<BorrowedHandle>(), UnresolvedTypeLayout<PipeReader>())
    }

    public companion object {
        public operator fun invoke(
            handle: RawHandle,
            phantom: Unit,
        ): BorrowedHandle = BorrowedHandle(
            handleCtorArg = handle,
            phantom = phantom,
        )

        /**
         * Returns a `BorrowedHandle` holding the given raw handle.
         *
         * # Safety
         *
         * The resource pointed to by `handle` must be a valid open handle, it
         * must remain open for the duration of the returned `BorrowedHandle`.
         *
         * Note that it *may* have the value `INVALID_HANDLE_VALUE` (-1), which is
         * sometimes a valid handle value. See [here] for the full story.
         *
         * And, it *may* have the value `NULL` (0), which can occur when consoles are
         * detached from processes, or when `windowsSubsystem` is used.
         *
         * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
         */
        public fun borrowRaw(handle: RawHandle): BorrowedHandle = BorrowedHandle(handle = handle, phantom = Unit)
    }
}

/**
 * An owned handle.
 *
 * This closes the handle on drop.
 *
 * Note that it *may* have the value `- 1`, which in `OwnedHandle` always
 * represents a valid handle value, such as [the current process handle], and
 * not `INVALID_HANDLE_VALUE`, despite the two having the same value. See
 * [here] for the full story.
 *
 * And, it *may* have the value `NULL` (0), which can occur when consoles are
 * detached from processes, or when `windowsSubsystem` is used.
 *
 * `OwnedHandle` uses [CloseHandle] to close its handle on drop. As such,
 * it must not be used with handles to open registry keys which need to be
 * closed with [RegCloseKey] instead.
 *
 * [CloseHandle]: https://docs.microsoft.com/en-us/windows/win32/api/handleapi/nf-handleapi-closehandle
 * [RegCloseKey]: https://docs.microsoft.com/en-us/windows/win32/api/winreg/nf-winreg-regclosekey
 *
 * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
 * [the current process handle]: https://docs.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-getcurrentprocess#remarks
 */
public open class OwnedHandle internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout(),
) : AsRawHandle, IntoRawHandle, FromRawHandle<OwnedHandle>, IsTerminal, AsHandle {
    public var handle: RawHandle
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(handle: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()) : this(allocateNativeCell(handle, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OwnedHandle> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OwnedHandle(ptr, layout) }, { it.handle })

    // The Windows [Handle] type may be transferred across and shared between
    // thread boundaries (despite containing a `* void`, which in general isn't
    // `Send` or `Sync`).
    //
    // [Handle]: std.os.windows.raw.Handle

    /**
     * Creates a new `OwnedHandle` instance that shares the same underlying
     * object as the existing `OwnedHandle` instance.
     */
    public open fun tryClone(): Result<OwnedHandle, Error> = this.asHandle().tryCloneToOwned()

    public override fun asRawHandle(): RawHandle = this.handle

    public override fun intoRawHandle(): RawHandle = this.handle

    public open fun drop() {
        run {
            C.CloseHandle(this.handle)
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "OwnedHandle").field("handle", this.handle).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.windows.isTerminal(this)

    public override fun asHandle(): BorrowedHandle {
        // Safety: `OwnedHandle` and `BorrowedHandle` have the same validity
        // invariants, and the `BorrowedHandle` is bounded by the scope
        // of `self`.
        return BorrowedHandle.borrowRaw(this.asRawHandle())
    }

    public companion object {
        public operator fun invoke(handle: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle(handle, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle(fieldStorage, fieldLayout)

        public fun tryFrom(handleOrNull: HandleOrNull, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): Result<OwnedHandle, NullHandleError> {
            val handleOrNull1 = handleOrNull
            return if (handleOrNull1.isValid()) {
                // SAFETY: The handle is not null.
                Result.Ok<OwnedHandle, NullHandleError>(OwnedHandle.FromRawHandle(handleOrNull1.first))
            } else {
                Result.Err<OwnedHandle, NullHandleError>(NullHandleError(Unit))
            }
        }

        public fun tryFrom(handleOrInvalid: HandleOrInvalid, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): Result<OwnedHandle, InvalidHandleError> {
            val handleOrInvalid1 = handleOrInvalid
            return if (handleOrInvalid1.isValid()) {
                // SAFETY: The handle is not invalid.
                Result.Ok<OwnedHandle, InvalidHandleError>(OwnedHandle.FromRawHandle(handleOrInvalid1.first))
            } else {
                Result.Err<OwnedHandle, InvalidHandleError>(InvalidHandleError(Unit))
            }
        }

        public fun fromRawHandle(handle: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle(handle = handle, fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`File`](fs.File)'s underlying file handle.
         */
        public fun from(file: File): OwnedHandle = file.intoInner().intoInner().intoInner()

        /**
         * Takes ownership of a [`ChildStdin`](process.ChildStdin)'s file handle.
         */
        public fun from(childStdin: ChildStdin, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle.FromRawHandle(childStdin.intoRawHandle())

        /**
         * Takes ownership of a [`ChildStdout`](process.ChildStdout)'s file handle.
         */
        public fun from(childStdout: ChildStdout, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle.FromRawHandle(childStdout.intoRawHandle())

        /**
         * Takes ownership of a [`ChildStderr`](process.ChildStderr)'s file handle.
         */
        public fun from(childStderr: ChildStderr, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): OwnedHandle = OwnedHandle.FromRawHandle(childStderr.intoRawHandle())

        public fun from(pipe: PipeReader): OwnedHandle = pipe.intoInner().intoInner()

        public fun from(pipe: PipeWriter): OwnedHandle = pipe.intoInner().intoInner()
    }
}

/**
 * FFI type for handles in return values or out parameters, where `NULL` is used
 * as a sentry value to indicate errors, such as in the return value of `CreateThread`. This uses
 * `repr(transparent)` and has the representation of a host handle, so that it can be used in such
 * FFI declarations.
 *
 * The only thing you can usefully do with a `HandleOrNull` is to convert it into an
 * `OwnedHandle` using its [TryFrom] implementation; this conversion takes care of the check for
 * `NULL`. This ensures that such FFI calls cannot start using the handle without
 * checking for `NULL` first.
 *
 * This type may hold any handle value that [OwnedHandle] may hold. As with `OwnedHandle`, when
 * it holds `- 1`, that value is interpreted as a valid handle value, such as
 * [the current process handle], and not `INVALID_HANDLE_VALUE`.
 *
 * If this holds a non-null handle, it will close the handle on drop.
 *
 * [the current process handle]: https://docs.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-getcurrentprocess#remarks
 */
public open class HandleOrNull internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout(),
) {
    public var field0: RawHandle
        get() = value
        set(v) {
            value = v
        }

    public var value: RawHandle
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<HandleOrNull> = TransparentTypeLayout(fieldLayout, { ptr, layout -> HandleOrNull(ptr, layout) }, { it.value })

    public open fun drop() {
        if (this.isValid()) {
            run {
                C.CloseHandle(this.value)
            }
        }
    }

    public open fun isValid(): Boolean = (!this.value.isNull()).toUByte()

    public companion object {
        public operator fun invoke(value: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrNull = HandleOrNull(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrNull = HandleOrNull(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `Self` from the given `RawHandle` returned
         * from a Windows API that uses null to indicate failure, such as
         * `CreateThread`.
         *
         * Use `HandleOrInvalid` instead of `HandleOrNull` for APIs that
         * use `INVALID_HANDLE_VALUE` to indicate failure.
         *
         * # Safety
         *
         * The passed `handle` value must either satisfy the safety requirements
         * of [FromRawHandle.fromRawHandle], or be null. Note that not all
         * Windows APIs use null for errors; see [here] for the full story.
         *
         * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
         */
        public fun fromRawHandle(handle: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrNull = HandleOrNull(handle, fieldLayout = fieldLayout)
    }
}

/**
 * FFI type for handles in return values or out parameters, where `INVALID_HANDLE_VALUE` is used
 * as a sentry value to indicate errors, such as in the return value of `CreateFileW`. This uses
 * `repr(transparent)` and has the representation of a host handle, so that it can be used in such
 * FFI declarations.
 *
 * The only thing you can usefully do with a `HandleOrInvalid` is to convert it into an
 * `OwnedHandle` using its [TryFrom] implementation; this conversion takes care of the check for
 * `INVALID_HANDLE_VALUE`. This ensures that such FFI calls cannot start using the handle without
 * checking for `INVALID_HANDLE_VALUE` first.
 *
 * This type may hold any handle value that [OwnedHandle] may hold, except that when it holds
 * `- 1`, that value is interpreted to mean `INVALID_HANDLE_VALUE`.
 *
 * If holds a handle other than `INVALID_HANDLE_VALUE`, it will close the handle on drop.
 */
public open class HandleOrInvalid internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout(),
) {
    public var field0: RawHandle
        get() = value
        set(v) {
            value = v
        }

    public var value: RawHandle
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<HandleOrInvalid> = TransparentTypeLayout(fieldLayout, { ptr, layout -> HandleOrInvalid(ptr, layout) }, { it.value })

    public open fun drop() {
        if (this.isValid()) {
            run {
                C.CloseHandle(this.value)
            }
        }
    }

    public open fun isValid(): Boolean = (this.value != C.INVALID_HANDLE_VALUE).toUByte()

    public companion object {
        public operator fun invoke(value: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrInvalid = HandleOrInvalid(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrInvalid = HandleOrInvalid(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `Self` from the given `RawHandle` returned
         * from a Windows API that uses `INVALID_HANDLE_VALUE` to indicate
         * failure, such as `CreateFileW`.
         *
         * Use `HandleOrNull` instead of `HandleOrInvalid` for APIs that
         * use null to indicate failure.
         *
         * # Safety
         *
         * The passed `handle` value must either satisfy the safety requirements
         * of [FromRawHandle.fromRawHandle], or be
         * `INVALID_HANDLE_VALUE` (-1). Note that not all Windows APIs use
         * `INVALID_HANDLE_VALUE` for errors; see [here] for the full story.
         *
         * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
         */
        public fun fromRawHandle(handle: RawHandle, fieldLayout: TypeLayout<RawHandle> = UnresolvedTypeLayout()): HandleOrInvalid = HandleOrInvalid(handle, fieldLayout = fieldLayout)
    }
}

// The empty field prevents constructing this, and allows extending it in the future.

/**
 * This is the error type used by [HandleOrNull] when attempting to convert
 * into a handle, to indicate that the value is null.
 */
public open class NullHandleError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): NullHandleError = NullHandleError(value)

    public open fun fmt(fmt: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = "A HandleOrNull could not be converted to a handle because it was null".fmt(fmt)

    public companion object {
        public fun typeLayout(): TypeLayout<NullHandleError> =
            object : TypeLayout<NullHandleError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): NullHandleError =
                    NullHandleError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: NullHandleError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NullHandleError =
                    NullHandleError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NullHandleError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

// The empty field prevents constructing this, and allows extending it in the future.

/**
 * This is the error type used by [HandleOrInvalid] when attempting to
 * convert into a handle, to indicate that the value is
 * `INVALID_HANDLE_VALUE`.
 */
public open class InvalidHandleError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): InvalidHandleError = InvalidHandleError(value)

    public open fun fmt(fmt: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = "A HandleOrInvalid could not be converted to a handle because it was INVALID_HANDLE_VALUE".fmt(fmt)

    public companion object {
        public fun typeLayout(): TypeLayout<InvalidHandleError> =
            object : TypeLayout<InvalidHandleError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): InvalidHandleError =
                    InvalidHandleError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: InvalidHandleError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InvalidHandleError =
                    InvalidHandleError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InvalidHandleError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * A trait to borrow the handle from an underlying object.
 */
public interface AsHandle {
    /**
     * Borrows the handle.
     *
     * # Example
     *
     * use std.fs.File;
     * # use std.io;
     * use std.os.windows.io::(AsHandle, BorrowedHandle);
     *
     * let mut f = File.open("foo.txt")?;
     * let borrowedHandle: BorrowedHandle<_> = f.asHandle();
     * # Ok::<(), io.Error>(())
     * ```kotlin
     */
    public fun asHandle(): BorrowedHandle
}

/**
 * This impl allows implementing traits that require `AsHandle` on Arc.
 * ```kotlin
 * import std.fs.File
 *
 * interface MyTrait : AsHandle
 * ```
 */
public fun <T : AsHandle> Arc<T, Global>.asHandle(): BorrowedHandle = this.asHandle()

public fun <T : AsHandle> Rc.Rc<T, Global>.asHandle(): BorrowedHandle = this.asHandle()

public fun <T : AsHandle> Rc.UniqueRc<T>.asHandle(): BorrowedHandle = this.asHandle()

public fun File.asHandle(): BorrowedHandle = this.asInner().asHandle()

/**
 * Returns a [`File`](fs.File) that takes ownership of the given handle.
 */
public fun fromFile(owned: OwnedHandle): File = File.FromInner(FromInner.FromInner(FromInner.FromInner(owned)))

public fun Stdin.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun StdinLock.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun Stdout.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun StdoutLock.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun Stderr.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun StderrLock.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun ChildStdin.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun ChildStdout.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun ChildStderr.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun <T> JoinHandle<T>.asHandle(): BorrowedHandle = BorrowedHandle.borrowRaw(this.asRawHandle())

public fun <T> fromOwnedHandle(joinHandle: JoinHandle<T>): OwnedHandle = joinHandle.intoInner().intoHandle().intoInner()

public fun PipeReader.asHandle(): BorrowedHandle = this.value.asHandle()

public fun PipeWriter.asHandle(): BorrowedHandle = this.value.asHandle()

public fun fromPipeReader(ownedHandle: OwnedHandle): PipeReader = PipeReader.FromInner(FromInner.FromInner(ownedHandle))

public fun fromPipeWriter(ownedHandle: OwnedHandle): PipeWriter = PipeWriter.FromInner(FromInner.FromInner(ownedHandle))

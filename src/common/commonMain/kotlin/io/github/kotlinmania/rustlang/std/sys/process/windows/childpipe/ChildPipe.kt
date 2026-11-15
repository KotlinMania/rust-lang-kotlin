// port-lint: source sys/process/windows/child_pipe.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.process.windows.childpipe

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.io.ErrorKind
import io.github.kotlinmania.rustlang.os.windows.raw.Handle
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.sys.FromInner
import io.github.kotlinmania.rustlang.sys.IntoInner
import io.github.kotlinmania.rustlang.sys.api
import io.github.kotlinmania.rustlang.thread.functions.spawn

public open class ChildPipe internal constructor(
    innerCtorArg: Handle,
) : IntoInner<Handle>, FromInner<Handle, ChildPipe> {
    public var inner: Handle = innerCtorArg
        internal set

    public override fun intoInner(): Handle = this.inner

    public open fun handle(): Handle = this.inner

    public open fun intoHandle(): Handle = this.inner

    public open fun tryClone(): Result<ChildPipe, Error> = this.inner.duplicate(0, false, DUPLICATE_SAME_ACCESS).map({ inner -> ChildPipe(inner = inner) })

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val result =
            run {
                val len = minOf(buf.size, UInt.MAX_VALUE.toInt()).toUInt()
                val ptr = MutPtr((buf.asMutPtr()).rawAddress)
                this.alertableIoInternal({ overlapped, callback ->
                    ReadFileEx(this.inner.asRawHandle(), ptr, len, overlapped, callback)
                })
            }
        return when {
            // The special treatment of BrokenPipe is to deal with Windows
            // pipe semantics, which yields this error when *reading* from
            // a pipe after the other end has closed; we interpret that as
            // EOF on the pipe.
            result is Result.Err<*, *> && (result as Result.Err<*, *>).error.kind() == ErrorKind.BrokenPipe -> {
                val e = (result as Result.Err<*, *>).error
                Result.Ok(0)
            }
            else -> result
        }
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> {
        val result =
            run {
                val len = minOf(buf.capacity(), UInt.MAX_VALUE.toInt()).toUInt()
                val ptr = buf.asMut().asMutPtr().cast()
                this.alertableIoInternal({ overlapped, callback ->
                    ReadFileEx(this.inner.asRawHandle(), ptr, len, overlapped, callback)
                })
            }
        return when {
            // The special treatment of BrokenPipe is to deal with Windows
            // pipe semantics, which yields this error when *reading* from
            // a pipe after the other end has closed; we interpret that as
            // EOF on the pipe.
            result is Result.Err<*, *> && (result as Result.Err<*, *>).error.kind() == ErrorKind.BrokenPipe -> {
                val e = (result as Result.Err<*, *>).error
                Result.Ok(Unit)
            }
            result is Result.Err<*, *> -> {
                val e1 = (result as Result.Err<*, *>).error
                Result.Err(e1)
            }
            else -> {
                val n = (result as Result.Ok<*, *>).value
                run {
                    buf.advance(n)
                }
                Result.Ok(Unit)
            }
        }
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> = this.handle().readToEnd(buf)

    public open fun write(buf: List<UByte>): Result<Int, Error> =
        run {
            val len = minOf(buf.size, UInt.MAX_VALUE.toInt()).toUInt()
            this.alertableIoInternal({ overlapped, callback ->
                WriteFileEx(this.inner.asRawHandle(), ConstPtr((buf.asPtr()).rawAddress), len, overlapped, callback)
            })
        }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.inner.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.isWriteVectored()

    /**
     * Synchronizes asynchronous reads or writes using our anonymous pipe.
     *
     * This is a wrapper around [ReadFileEx] or [WriteFileEx] that uses
     * [Asynchronous Procedure Call] (APC) to synchronize reads or writes.
     *
     * Note: This should not be used for handles we don't create.
     *
     * # Safety
     *
     * `buf` must be a pointer to a buffer that's valid for reads or writes
     * up to `len` bytes. The `AlertableIoFn` must be either `ReadFileEx` or `WriteFileEx`
     *
     * [ReadFileEx]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-readfileex
     * [WriteFileEx]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-writefileex
     * [Asynchronous Procedure Call]: https://docs.microsoft.com/en-us/windows/win32/sync/asynchronous-procedure-calls
     */
    public open fun alertableIoInternal(io: (Overlapped, LpoverlappedCompletionRoutine) -> Bool): Result<Int, Error> {
        open class AsyncResult(
            public var error: UInt,
            public var transferred: UInt,
        )
        // STEP 3: The callback.
        fun callback(error: UInt, transferred: UInt, overlapped: MutPtr) {
            // Set `asyncResult` using a pointer smuggled through `hEvent`.
            // SAFETY:
            // At this point, the OVERLAPPED struct will have been written to by the OS,
            // except for our `hEvent` field which we set to a valid AsyncResult pointer (see below)
            run {
                val result = AsyncResult(error = error, transferred = transferred)
                (ObjectSlotLayout<Overlapped>()).readNative((overlapped).rawAddress).hEvent.cast() = result
            }
        }
        // Use "alertable I/O" to synchronize the pipe I/O.
        // This has four steps.
        //
        // STEP 1: Start the asynchronous I/O operation.
        // This simply calls either `ReadFileEx` or `WriteFileEx`,
        // giving it a pointer to the buffer and callback function.
        //
        // STEP 2: Enter an alertable state.
        // The callback set in step 1 will not be called until the thread
        // enters an "alertable" state. This can be done using `SleepEx`.
        //
        // STEP 3: The callback
        // Once the I/O is complete and the thread is in an alertable state,
        // the callback will be run on the same thread as the call to
        // `ReadFileEx` or `WriteFileEx` done in step 1.
        // In the callback we simply set the result of the async operation.
        //
        // STEP 4: Return the result.
        // At this point we'll have a result from the callback function
        // and can simply return it. Note that we must not return earlier,
        // while the I/O is still in progress.
        // The result that will be set from the asynchronous callback.
        val localStorageL367C8 = AddressableLocal(null, ObjectSlotLayout<AsyncResult?>())
        val asyncResult by localStorageL367C8
        // STEP 1: Start the I/O operation.
        val overlapped: Overlapped  = Overlapped()
        // `hEvent` is unused by `ReadFileEx` and `WriteFileEx`.
        // Therefore the documentation suggests using it to smuggle a pointer to the callback.
        overlapped.hEvent = (localStorageL367C8.asMutPtr())
        // Asynchronous read of the pipe.
        // If successful, `callback` will be called once it completes.
        val result = io(overlapped, ::callback)
        if (result == FALSE) {
            // We can return here because the call failed.
            // After this we must not return until the I/O completes.
            return Result.Err(io.github.kotlinmania.rustlang.io.Error.lastOsError())
        }
        // Wait indefinitely for the result.
        val result2 =
            while (true) {
                // STEP 2: Enter an alertable state.
                // The second parameter of `SleepEx` is used to make this sleep alertable.
                SleepEx(INFINITE, TRUE)
                if (asyncResult != null) {
                    break
                }
            }
        // STEP 4: Return the result.
        // `asyncResult` is always `Some` at this point
        return when (val matchVal = result2.error) {
            errorSuccess -> Result.Ok(result2.transferred.toInt())
            else -> {
                val error = matchVal
                Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(((error as Any?) as Result<Int, Error>)))
            }
        }
    }

    public companion object {
        public operator fun invoke(
            inner: Handle,
        ): ChildPipe = ChildPipe(
            innerCtorArg = inner,
        )

        public fun fromInner(inner: Handle): ChildPipe = ChildPipe(inner = inner)
    }
}

public open class Pipes internal constructor(
    oursCtorArg: ChildPipe,
    theirsCtorArg: ChildPipe,
) {
    public var ours: ChildPipe = oursCtorArg
        internal set

    public var theirs: ChildPipe = theirsCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            ours: ChildPipe,
            theirs: ChildPipe,
        ): Pipes = Pipes(
            oursCtorArg = ours,
            theirsCtorArg = theirs,
        )
    }
}

// Note(joboet): No, we don't guarantee that? E.g. `Stdout` is both `Read`
// and `Sync`, so there could be multiple operations at the same
// time. All the functions below that forward to the inner handle
// methods could abort if used concurrently.

/**
 * Creates an anonymous pipe suitable for communication with a child process.
 *
 * Windows unfortunately does not have a way of performing asynchronous operations
 * on a handle originally created for synchronous operation. As `readOutput` can
 * only be correctly implemented with asynchronous reads but the pipe created by
 * `CreatePipe` is synchronous, we cannot use it (and thus [io.pipe]) to create
 * a pipe for communicating with a child. Instead, this function uses the NT API
 * to create a pipe where one pipe handle (`ours`) is asynchronous and one is
 * synchronous and can be inherited by a child for use as a console handle
 * (`theirs`).
 *
 * The ours/theirs pipes are *not* specifically readable or writable. Each
 * one only supports a read or a write, but which is which depends on the
 * boolean flag given. If `oursReadable` is `true`, then `ours` is readable and
 * `theirs` is writable. Conversely, if `oursReadable` is `false`, then `ours`
 * is writable and `theirs` is readable.
 *
 * Also note that the `ours` pipe is always a handle opened up in overlapped
 * mode. This means that technically speaking it should only ever be used
 * with `OVERLAPPED` instances, but also works out ok if it's only ever used
 * once at a time (which we do indeed guarantee).
 */
public fun childPipe(oursReadable: Boolean, theirHandleInheritable: Boolean): Result<Pipes, Error> {
    // A 64kb pipe capacity is the same as a typical Linux default.
    val PIPE_BUFFER_CAPACITY: UInt = 64u * 1024u
    // Note that we specifically do *not* use `CreatePipe` here because
    // unfortunately the anonymous pipes returned do not support overlapped
    // operations. Instead, we use `NtCreateNamedPipeFile` to create the
    // anonymous pipe with overlapped support.
    //
    // Once we do this, we connect to it via `NtOpenFile`, and then
    // we return those reader/writer halves. Note that the `ours` pipe return
    // value is always the named pipe, whereas `theirs` is just the normal file.
    // This should hopefully shield us from child processes which assume their
    // stdout is a named pipe, which would indeed be odd!
    return run {
        // Open a handle to the pipe filesystem (`\Device\NamedPipe\`).
        // This will be used when creating a new anonymous pipe.
        //
        // We cache the handle once so we can reuse it without needing to reopen it each time.
        // NOTE: this means the handle may appear to be leaked but that's fine because
        // it's only one handle and the OS will clean it up when the process exits.
        val PIPE_FS: Atomic<Handle>  = Atomic.new(MutPtr(0L))
        val ioStatus = IoStatusBlock.default()
        val objectAttributes = ObjectAttributes.default()
        objectAttributes.length = sizeOf<ObjectAttributes>(typeLayoutT = ObjectSlotLayout<ObjectAttributes>()).toUInt()
        val pipeFs1 =
            if (PIPE_FS.load(Relaxed) != null && !handle.isNull()) {
                handle
            } else {
                val path = api.unicodeStr("\\Device\\NamedPipe\\")
                objectAttributes.objectName = path.asPtr().castMut()
                val pipeFs = MutPtr(0L)
                val status = NtOpenFile(pipeFs, SYNCHRONIZE or GENERIC_READ, objectAttributes, ioStatus, FILE_SHARE_READ or FILE_SHARE_WRITE, FILE_SYNCHRONOUS_IO_NONALERT)
                if (ntSuccess(status)) {
                    run {
                        val matchVal = PIPE_FS.compareExchange(MutPtr(0L), pipeFs, Relaxed, Relaxed)
                        when (matchVal) { is Result.Ok<*, *> -> {
                            pipeFs
                        }; is Result.Err<*, *> -> {
                            val existing = matchVal.error
                            CloseHandle(pipeFs)
                            existing
                        } }
                    }
                } else {
                    return Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(RtlNtStatusToDosError(status).toInt()))
                }
            }
        // From now on we're using handles instead of paths to create and open pipes.
        // So set the `ObjectName` to a zero length string.
        // As a (perhaps overzealous) mitigation for #143078, we use the null pointer
        // for empty.Buffer instead of unicodeStr("").
        // There's no difference to the OS itself but it's possible that third party
        // DLLs which hook in to processes could be relying on the exact form of this string.
        val localStorageL118C8 = AddressableLocal(UnicodeString.default(), ObjectSlotLayout<Any?>())
        objectAttributes.objectName = MutPtr(((localStorageL118C8.asConstPtr())).rawAddress)
        // Create our side of the pipe for async access.
        val ours1 = run {
            // Use the pipe filesystem as the root directory.
            // With no name provided, an anonymous pipe will be created.
            objectAttributes.rootDirectory = pipeFs1
            // A negative timeout value is a relative time (rather than an absolute time).
            // The time is given in 100's of nanoseconds so this is 50 milliseconds.
            // This value was chosen to be consistent with the default timeout set by `CreateNamedPipeW`
            // See: https://learn.microsoft.com/en-us/windows/win32/api/namedpipeapi/nf-namedpipeapi-createnamedpipew
            val timeout = (50L * 10000L).neg().toULong()
            val ours = MutPtr(0L)
            val status1 =
                NtCreateNamedPipeFile(ours, SYNCHRONIZE or
                    if (oursReadable) {
                        GENERIC_READ
                    } else {
                        GENERIC_WRITE
                    }, objectAttributes, ioStatus, if (oursReadable) {
                    FILE_SHARE_WRITE
                } else {
                    FILE_SHARE_READ
                }, FILE_CREATE, 0, FILE_PIPE_BYTE_STREAM_TYPE, FILE_PIPE_BYTE_STREAM_MODE, FILE_PIPE_QUEUE_OPERATION, 1, PIPE_BUFFER_CAPACITY, PIPE_BUFFER_CAPACITY, timeout)
            if (ntSuccess(status1)) {
                Handle.fromRawHandle(ours)
            } else {
                return Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(RtlNtStatusToDosError(status1).toInt()))
            }
        }
        // Open their side of the pipe for synchronous access.
        val theirs1 = run {
            // We can reopen the anonymous pipe without a name by setting
            // RootDirectory to the pipe handle and not setting a path name,
            objectAttributes.rootDirectory = ours1.asRawHandle()
            if (theirHandleInheritable) {
                objectAttributes.attributes = objectAttributes.attributes or OBJ_INHERIT
            }
            val theirs = MutPtr(0L)
            val status2 =
                NtOpenFile(theirs, SYNCHRONIZE or
                    if (oursReadable) {
                        GENERIC_WRITE or FILE_READ_ATTRIBUTES
                    } else {
                        GENERIC_READ
                    }, objectAttributes, ioStatus, 0, FILE_NON_DIRECTORY_FILE or FILE_SYNCHRONOUS_IO_NONALERT)
            if (ntSuccess(status2)) {
                Handle.fromRawHandle(theirs)
            } else {
                return Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(RtlNtStatusToDosError(status2).toInt()))
            }
        }
        Result.Ok(Pipes(ours = ChildPipe(inner = ours1), theirs = ChildPipe(inner = theirs1)))
    }
}

/**
 * Takes an asynchronous source pipe and returns a synchronous pipe suitable
 * for sending to a child process.
 *
 * This is achieved by creating a new set of pipes and spawning a thread that
 * relays messages between the source and the synchronous pipe.
 */
public fun spawnPipeRelay(source: ChildPipe, oursReadable: Boolean, theirHandleInheritable: Boolean): Result<ChildPipe, Error> {
    // We need this handle to live for the scope of the thread spawned below.
    val source1 = when (val tryResult = source.tryClone()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    // create a new pair of anon pipes.
    val structVal = when (val tryResult = childPipe(oursReadable, theirHandleInheritable)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val theirs = structVal.theirs
    val ours = structVal.ours
    // Spawn a thread that passes messages from one pipe to the other.
    // Any errors will simply cause the thread to exit.
    val (reader, writer) =
        if (oursReadable) {
            Pair(ours, source1)
        } else {
            Pair(source1, ours)
        }
    spawn({
        var buf = UByteArray(4096) { 0u.toUByte() }
        while (true) {
            val matchVal = reader.read(buf)
            if (matchVal !is Result.Ok<*, *>) break
            val len = (matchVal as Result.Ok<*, *>).value
            if (len == 0) {
                break
            }
            var start = 0
            while (true) {
                val matchVal1 = writer.write(((buf).sliceArray(start until len)))
                if (matchVal1 !is Result.Ok<*, *>) break
                val written = (matchVal1 as Result.Ok<*, *>).value
                start += written
                if (start == len) {
                    continue
                }
            }
            break
        }
    })
    // Return the pipe that should be sent to the child process.
    return Result.Ok(theirs)
}

public fun readOutput(p1: ChildPipe, v1: List<UByte>, p2: ChildPipe, v2: List<UByte>): Result<Unit, Error> {
    val p11 = p1.intoHandle()
    val p21 = p2.intoHandle()
    val p12 = when (val tryResult = AsyncPipe.new(p11, v1)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val p22 = when (val tryResult = AsyncPipe.new(p21, v2)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val objs = listOf(p12.event.asRawHandle(), p22.event.asRawHandle())
    // In a loop we wait for either pipe's scheduled read operation to complete.
    // If the operation completes with 0 bytes, that means EOF was reached, in
    // which case we just finish out the other pipe entirely.
    //
    // Note that overlapped I/O is in general super unchecked because we have to
    // be careful to ensure that all pointers in play are valid for the entire
    // duration of the I/O operation (where tons of operations can also fail).
    // The destructor for `AsyncPipe` ends up taking care of most of this.
    while (true) {
        val res = WaitForMultipleObjects(2, objs.asPtr(), FALSE, INFINITE)
        if (res == WAIT_OBJECT_0) {
            if (!when (val tryResult = p12.result()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) } || !when (val tryResult = p12.scheduleRead()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
                return p22.finish()
            }
        } else if (res == WAIT_OBJECT_0 + 1u) {
            if (!when (val tryResult = p22.result()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) } || !when (val tryResult = p22.scheduleRead()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
                return p12.finish()
            }
        } else {
            return Result.Err(io.github.kotlinmania.rustlang.io.Error.lastOsError())
        }
    }
}

public open class AsyncPipe internal constructor(
    pipeCtorArg: Handle,
    eventCtorArg: Handle,
    public var overlapped: Overlapped,
    // needs a stable address
    public var dst: List<UByte>,
    public var state: State,
) {
    public var pipe: Handle = pipeCtorArg
        internal set

    public var event: Handle = eventCtorArg
        internal set

    /**
     * Executes an overlapped read operation.
     *
     * Must not currently be reading, and returns whether the pipe is currently
     * at EOF or not. If the pipe is not at EOF then `result()` must be called
     * to complete the read later on (may block), but if the pipe is at EOF
     * then `result()` should not be called as it will just block forever.
     */
    public open fun scheduleRead(): Result<Boolean, Error> {
        check(this.state == State.NotReading)
        val amt =
            run {
                if (this.dst.capacity() == this.dst.size) {
                    val additional =
                        if (this.dst.capacity() == 0) {
                            16
                        } else {
                            1
                        }
                    this.dst.reserve(additional)
                }
                when (val tryResult = this.pipe.readOverlapped(this.dst.spareCapacityMut(), this.overlapped)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            }
        // If this read finished immediately then our overlapped event will
        // remain signaled (it was signaled coming in here) and we'll progress
        // down to the method below.
        //
        // Otherwise the I/O operation is scheduled and the system set our event
        // to not signaled, so we flag ourselves into the reading state and move
        // on.
        this.state = when {
            amt != null && amt == 0 -> { return Result.Ok(false) }
            amt != null -> {
                val amt1 = amt
                State.Read(amt1)
            }
            else -> State.Reading
        }
        return Result.Ok(true)
    }

    /**
     * Wait for the result of the overlapped operation previously executed.
     *
     * Takes a parameter `wait` which indicates if this pipe is currently being
     * read whether the function should block waiting for the read to complete.
     *
     * Returns values:
     *
     * * `true` - finished any pending read and the pipe is not at EOF (keep
     *            going)
     * * `false` - finished any pending read and pipe is at EOF (stop issuing
     *             reads)
     */
    public open fun result(): Result<Boolean, Error> {
        val amt1 =
            when (val matchVal = this.state) {
                State.NotReading -> { return Result.Ok(true) }
                State.Reading -> when (val tryResult = this.pipe.overlappedResult(this.overlapped, true)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                is State.Read -> {
                    val (amt) = matchVal
                    amt
                }
            }
        this.state = State.NotReading
        run {
            val len = this.dst.size
            this.dst.setLen(len + amt1)
        }
        return Result.Ok((amt1 != 0).toUByte())
    }

    /**
     * Finishes out reading this pipe entirely.
     *
     * Waits for any pending and schedule read, and then calls `readToEnd`
     * if necessary to read all the remaining information.
     */
    public open fun finish(): Result<Unit, Error> {
        while (when (val tryResult = this.result()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) } && when (val tryResult = this.scheduleRead()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
            // ...
        }
        return Result.Ok(Unit)
    }

    public open fun drop() {
        // If we have a pending read operation, then we have to make sure that
        // it's *done* before we actually drop this type. The kernel requires
        // that the `OVERLAPPED` and buffer pointers are valid for the entire
        // I/O operation.
        //
        // To do that, we call `CancelIo` to cancel any pending operation, and
        // if that succeeds we wait for the overlapped result.
        //
        // If anything here fails, there's not really much we can do, so we leak
        // the buffer/OVERLAPPED pointers to ensure we're at least memory safe.
        if (this.pipe.cancelIo().isErr() || this.result().isErr()) {
            val buf = mem.take(this.dst)
            val overlapped = null
            val overlapped1 = mem.replace(this.overlapped, overlapped)
            mem.forget(Pair(buf, overlapped1))
        }
    }

    public companion object {
        public operator fun invoke(
            pipe: Handle,
            event: Handle,
            overlapped: Overlapped,
            dst: List<UByte>,
            state: State,
        ): AsyncPipe = AsyncPipe(
            pipeCtorArg = pipe,
            eventCtorArg = event,
            overlapped = overlapped,
            dst = dst,
            state = state,
        )

        public fun new(pipe: Handle, dst: List<UByte>): Result<AsyncPipe, Error> {
            // Create an event which we'll use to coordinate our overlapped
            // operations, this event will be used in WaitForMultipleObjects
            // and passed as part of the OVERLAPPED handle.
            //
            // Note that we do a somewhat clever thing here by flagging the
            // event as being manually reset and setting it initially to the
            // signaled state. This means that we'll naturally fall through the
            // WaitForMultipleObjects call above for pipes created initially,
            // and the only time an even will go back to "unset" will be once an
            // I/O operation is successfully scheduled (what we want).
            val event = when (val tryResult = Handle.newEvent(true, true)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            var overlapped: Overlapped = Overlapped()
            overlapped.hEvent = event.asRawHandle()
            return Result.Ok(AsyncPipe(pipe = pipe, overlapped = overlapped, event = event, dst = dst, state = State.NotReading))
        }
    }
}

public sealed class State {
    public data object NotReading : State()
    public data object Reading : State()
    public data class Read(public var value: Int) : State()

    public fun rustEquals(other: State): Boolean = this == other
}

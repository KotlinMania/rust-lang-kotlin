// port-lint: source sys/io/kernel_copy/linux.rs
package io.github.kotlinmania.rustlang.std.sys.io.kernelcopy.linux

import io.github.kotlinmania.libc.fuchsia.EBADF
import io.github.kotlinmania.libc.fuchsia.EINVAL
import io.github.kotlinmania.libc.fuchsia.ENOSYS
import io.github.kotlinmania.libc.fuchsia.EOPNOTSUPP
import io.github.kotlinmania.libc.fuchsia.EOVERFLOW
import io.github.kotlinmania.libc.fuchsia.EPERM
import io.github.kotlinmania.libc.fuchsia.EXDEV
import io.github.kotlinmania.libc.unix.bsd.freebsdlike.freebsd.Splice
import io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader.BufReader
import io.github.kotlinmania.rustlang.alloc.io.buffered.bufwriter.BufWriter
import io.github.kotlinmania.rustlang.alloc.io.copy.CopyState
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.util.Take
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.Cmp
import io.github.kotlinmania.rustlang.std.cmp.min
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.std.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.std.io.stdio.StderrLock
import io.github.kotlinmania.rustlang.std.io.stdio.StdinLock
import io.github.kotlinmania.rustlang.std.io.stdio.StdoutLock
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.process.ChildStderr
import io.github.kotlinmania.rustlang.std.process.ChildStdin
import io.github.kotlinmania.rustlang.std.process.ChildStdout
import io.github.kotlinmania.rustlang.std.sys.fs.unix.cfm.CachedFileMetadata
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.read
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.write
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.weak.syscall
import io.github.kotlinmania.libc.sendfile as sendfile64

// This module contains specializations that can offload `io.copy()` operations on file descriptor
// containing types (`File`, `TcpStream`, etc.) to more efficient syscalls than `read(2)` and `write(2)`.
//
// Specialization is only applied to wholly std-owned types so that user code can't observe
// that the `Read` and `Write` traits are not used.
//
// Since a copy operation involves a reader and writer side where each can consist of different types
// and also involve generic wrappers (e.g. `Take`, `BufReader`) it is not practical to specialize
// a single method on all possible combinations.
//
// Instead readers and writers are handled separately by the `CopyRead` and `CopyWrite` specialization
// traits and then specialized on by the `Copier.copy` method.
//
// `Copier` uses the specialization traits to unpack the underlying file descriptors and
// additional prerequisites and constraints imposed by the wrapper types.
//
// Once it has obtained all necessary pieces and brought any wrapper types into a state where they
// can be safely bypassed it will attempt to use the `copyFileRange(2)`,
// `sendfile(2)` or `splice(2)` syscalls to move data directly between file descriptors.
// Since those syscalls have requirements that cannot be fully checked in advance it attempts
// to use them one after another (guided by hints) to figure out which one works and
// falls back to the generic read-write copy loop if none of them does.
// Once a working syscall is found for a pair of file descriptors it will be called in a loop
// until the copy operation is completed.
//
// Advantages of using these syscalls:
//
// * fewer context switches since reads and writes are coalesced into a single syscall
//   and more bytes are transferred per syscall. This translates to higher throughput
//   and fewer CPU cycles, at least for sufficiently large transfers to amortize the initial probing.
// * `copyFileRange` creates reflink copies on CoW filesystems, thus moving less data and
//   consuming less disk space
// * `sendfile` and `splice` can perform zero-copy IO under some circumstances while
//   a naive copy loop would move every byte through the CPU.
//
// Drawbacks:
//
// * copy operations smaller than the default buffer size can under some circumstances, especially
//   on older kernels, incur more syscalls than the naive approach would. As mentioned above
//   the syscall selection is guided by hints to minimize this possibility but they are not perfect.
// * optimizations only apply to std types. If a user adds a custom wrapper type, e.g. to report
//   progress, they can hit a performance cliff.
// * complexity

public fun <R : Read, W : Write> specCopyCopyFile(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyTcpStream(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyUnixStream(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyPipeReader(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyChildStdout(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyChildStderr(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyStdinLock(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

public fun <R : Read, W : Write> specCopyCopyCachedFileMetadata(read: R, write: W): Result<CopyState> = (Copier(read = read, write = write)).copy()

/**
 * This type represents either the inferred `FileType` of a `RawFd` based on the source
 * type from which it was extracted or the actual metadata
 *
 * The methods on this type only provide hints, due to `AsRawFd` and `FromRawFd` the inferred
 * type may be wrong.
 */
public sealed class FdMeta {
    public data class Metadata(public var value: Metadata) : FdMeta()
    public data object Socket : FdMeta()
    public data object Pipe : FdMeta()
    /**
     * We don't have any metadata because the stat syscall failed
     */
    public data object NoneObtained : FdMeta()

    public open fun maybeFifo(): Boolean =
        when (this) {
            is FdMeta.Metadata -> {
                val (meta) = this
                meta.fileType().isFifo()
            }
            FdMeta.Socket -> false
            FdMeta.Pipe -> true
            FdMeta.NoneObtained -> true
        }

    public open fun potentialSendfileSource(): Boolean =
        when {
            // procfs erroneously shows 0 length on non-empty readable files.
            // and if a file is truly empty then a `read` syscall will determine that and skip the write syscall
            // thus there would be benefit from attempting sendfile
            this is FdMeta.Metadata && ((this as FdMeta.Metadata).value.fileType().isFile() && (this as FdMeta.Metadata).value.len() > 0 || (this as FdMeta.Metadata).value.fileType().isBlockDevice()) -> {
                val (meta) = this as FdMeta.Metadata
                true
            }
            else -> false
        }

    public open fun copyFileRangeCandidate(f: FdHandle): Boolean =
        when {
            // copyFileRange will fail on empty procfs files. `read` can determine whether EOF has been reached
            // without extra cost and skip the write, thus there is no benefit in attempting copyFileRange
            this is FdMeta.Metadata && f == FdHandle.Input && (this as FdMeta.Metadata).value.isFile() && (this as FdMeta.Metadata).value.len() > 0 -> {
                val (meta) = this as FdMeta.Metadata
                true
            }
            this is FdMeta.Metadata && f == FdHandle.Output && (this as FdMeta.Metadata).value.isFile() -> {
                val (meta1) = this as FdMeta.Metadata
                true
            }
            else -> false
        }
}

public sealed class FdHandle {
    public data object Input : FdHandle()
    public data object Output : FdHandle()

    public fun rustEquals(other: FdHandle): Boolean = this == other
}

/**
 * Returns true either if changes made to the source after a sendfile/splice call won't become
 * visible in the sink or the source has explicitly opted into such behavior (e.g. by splicing
 * a file into a pipe, the pipe being the source in this case).
 *
 * This will prevent File -> Pipe and File -> Socket splicing/sendfile optimizations to uphold
 * the Read/Write API semantics of io.copy.
 *
 * Note: This is not 100% airtight, the caller can use the RawFd conversion methods to turn a
 * regular file into a TcpSocket which will be treated as a socket here without checking.
 */
public fun safeKernelCopy(source: FdMeta, sink: FdMeta): Boolean =
    run {
        val matchVal = Pair(source, sink)
        when {
            // Data arriving from a socket is safe because the sender can't modify the socket buffer.
            // Data arriving from a pipe is safe(-ish) because either the sender *copied*
            // the bytes into the pipe OR explicitly performed an operation that enables zero-copy,
            // thus promising not to modify the data later.
            matchVal.first == FdMeta.Socket -> true
            matchVal.first == FdMeta.Pipe -> true
            matchVal.first is FdMeta.Metadata && ((matchVal.first as FdMeta.Metadata).value.fileType().isFifo() || (matchVal.first as FdMeta.Metadata).value.fileType().isSocket()) -> {
                val (meta) = matchVal.first as FdMeta.Metadata
                true
            }
            // Data going into non-pipes/non-sockets is safe because the "later changes may become visible" issue
            // only happens for pages sitting in send buffers or pipes.
            matchVal.second is FdMeta.Metadata && !(matchVal.second as FdMeta.Metadata).value.fileType().isFifo() && !(matchVal.second as FdMeta.Metadata).value.fileType().isSocket() -> {
                val (meta1) = matchVal.second as FdMeta.Metadata
                true
            }
            else -> false
        }
    }

public open class CopyParams(
    public var field0: FdMeta,
    public var field1: RawFd?,
)

public open class Copier<R : Read, W : Write>(
    public var read: R,
    public var write: W,
) : SpecCopy {
    public override fun copy(): Result<CopyState> = Result.Ok(CopyState.Fallback(0))

    public open fun copySpecCopy(): Result<CopyState> {
        val (reader, writer) = Pair(this.read, this.write)
        val rCfg = reader.properties()
        val wCfg = writer.properties()
        // before direct operations on file descriptors ensure that all source and sink buffers are empty
        var flush =
            closure@{
                val bytes = when (val tryResult = reader.drainTo(writer, ULong.MAX_VALUE)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
                // BufWriter buffered bytes have already been accounted for in earlier write() calls
                when (val tryResult = writer.flush()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
                Result.Ok(bytes)
            }
        var written = 0uL
        if (Pair(rCfg, wCfg) != null) {
            val (inputMeta, _) = Pair(rCfg, wCfg).first as CopyParams
            val readfd = (Pair(rCfg, wCfg).first as CopyParams).field1
            val (outputMeta, _) = Pair(rCfg, wCfg).second as CopyParams
            val writefd = (Pair(rCfg, wCfg).second as CopyParams).field1
            written += when (val tryResult = flush()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val maxWrite = reader.minLimit()
            if (inputMeta.copyFileRangeCandidate(FdHandle.Input) && outputMeta.copyFileRangeCandidate(FdHandle.Output)) {
                val result = copyRegularFiles(readfd, writefd, maxWrite)
                result.updateTake(reader)
                when (result) {
                    is CopyResult.Ended -> {
                        val (bytesCopied) = result
                        return Result.Ok(CopyState.Ended(bytesCopied + written))
                    }
                    is CopyResult.Error -> {
                        val (e, _) = result
                        return Result.Err(e)
                    }
                    is CopyResult.Fallback -> {
                        val (bytes) = result
                        written += bytes
                    }
                }
            }
            // on modern kernels sendfile can copy from any mmapable type (some but not all regular files and block devices)
            // to any writable file descriptor. On older kernels the writer side can only be a socket.
            // So we just try and fallback if needed.
            // If current file offsets + write sizes overflow it may also fail, we do not try to fix that and instead
            // fall back to the generic copy loop.
            if (inputMeta.potentialSendfileSource() && safeKernelCopy(inputMeta, outputMeta)) {
                val result1 = sendfileSplice(SpliceMode.Sendfile, readfd, writefd, maxWrite)
                result1.updateTake(reader)
                when (result1) {
                    is CopyResult.Ended -> {
                        val (bytesCopied1) = result1
                        return Result.Ok(CopyState.Ended(bytesCopied1 + written))
                    }
                    is CopyResult.Error -> {
                        val (e1, _) = result1
                        return Result.Err(e1)
                    }
                    is CopyResult.Fallback -> {
                        val (bytes1) = result1
                        written += bytes1
                    }
                }
            }
            if ((inputMeta.maybeFifo() || outputMeta.maybeFifo()) && safeKernelCopy(inputMeta, outputMeta)) {
                val result2 = sendfileSplice(SpliceMode.Splice, readfd, writefd, maxWrite)
                result2.updateTake(reader)
                when {
                    result2 is CopyResult.Ended -> {
                        val (bytesCopied2) = result2
                        return Result.Ok(CopyState.Ended(bytesCopied2 + written))
                    }
                    result2 is CopyResult.Error -> {
                        val (e2, _) = result2
                        return Result.Err(e2)
                    }
                    result2 is CopyResult.Fallback && result2.value == 0 -> {
                        // use the fallback below
                    }
                    else -> {
                        error("splice should not return > 0 bytes on the fallback path")
                    }
                }
            }
        }
        // fallback if none of the more specialized syscalls wants to work with these file descriptors
        return Result.Ok(CopyState.Fallback(written))
    }
}

public interface SpecCopy {
    public fun copy(): Result<CopyState>
}

public interface CopyRead : Read {
    /**
     * Implementations that contain buffers (i.e. `BufReader`) must transfer data from their internal
     * buffers into `writer` until either the buffers are emptied or `limit` bytes have been
     * transferred, whichever occurs sooner.
     * If nested buffers are present the outer buffers must be drained first.
     *
     * This is necessary to directly bypass the wrapper types while preserving the data order
     * when operating directly on the underlying file descriptors.
     */
    public fun <W : Write> drainTo(writer: W, limit: ULong): Result<ULong> = Result.Ok(0)

    /**
     * Updates `Take` wrappers to remove the number of bytes copied.
     */
    public fun taken(bytes: ULong) {}

    /**
     * The minimum of the limit of all `Take<_>` wrappers, `ULong.MAX` otherwise.
     * This method does not account for data `BufReader` buffers and would underreport
     * the limit of a `Take<BufReader<Take<_>>>` type. Thus its result is only valid
     * after draining the buffers via `drainTo`.
     */
    public fun minLimit(): ULong = ULong.MAX_VALUE

    /**
     * Extracts the file descriptor and hints/metadata, delegating through wrappers if necessary.
     */
    public fun properties(): CopyParams
}

public interface CopyWrite : Write {
    /**
     * Extracts the file descriptor and hints/metadata, delegating through wrappers if necessary.
     */
    public fun properties(): CopyParams
}

public fun File.properties(): CopyParams = CopyParams(fdToMeta(this), this.asRawFd())

public fun TcpStream.properties(): CopyParams {
    // avoid the stat syscall since we can be fairly sure it's a socket
    return CopyParams(FdMeta.Socket, this.asRawFd())
}

public fun UnixStream.properties(): CopyParams {
    // avoid the stat syscall since we can be fairly sure it's a socket
    return CopyParams(FdMeta.Socket, this.asRawFd())
}

public fun PipeReader.properties(): CopyParams = CopyParams(FdMeta.Pipe, this.asRawFd())

public fun PipeWriter.properties(): CopyParams = CopyParams(FdMeta.Pipe, this.asRawFd())

public fun ChildStdin.properties(): CopyParams = CopyParams(FdMeta.Pipe, this.asRawFd())

public fun ChildStdout.properties(): CopyParams = CopyParams(FdMeta.Pipe, this.asRawFd())

public fun ChildStderr.properties(): CopyParams = CopyParams(FdMeta.Pipe, this.asRawFd())

public fun <W : Write> StdinLock.drainTo(writer: W, outerLimit: ULong): Result<ULong> {
    val bufReader = this.asMutBuf()
    val buf = bufReader.buffer()
    val buf1 = buf[0 until minOf(buf.len(), outerLimit.tryInto().unwrapOr(Int.MAX_VALUE))]
    val bytesDrained = buf1.len()
    when (val tryResult = writer.writeAll(buf1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    bufReader.consume(bytesDrained)
    return Result.Ok(bytesDrained.toULong())
}

public fun StdinLock.properties(): CopyParams = CopyParams(fdToMeta(this), this.asRawFd())

public fun StdoutLock.properties(): CopyParams = CopyParams(fdToMeta(this), this.asRawFd())

public fun StderrLock.properties(): CopyParams = CopyParams(fdToMeta(this), this.asRawFd())

public fun <T : CopyRead, W : Write> Take<T>.drainTo(writer: W, outerLimit: ULong): Result<ULong> {
    val localLimit = this.limit()
    val combinedLimit = minOf(outerLimit, localLimit)
    val bytesDrained = when (val tryResult = this.getMut().drainTo(writer, combinedLimit)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // update limit since read() was bypassed
    this.setLimit(localLimit - bytesDrained)
    return Result.Ok(bytesDrained)
}

public fun <T : CopyRead> Take<T>.taken(bytes: ULong) {
    this.setLimit(this.limit() - bytes)
    this.getMut().taken(bytes)
}

public fun <T : CopyRead> Take<T>.minLimit(): ULong = minOf(Take.limit(this), this.getRef().minLimit())

public fun <T : CopyRead> Take<T>.properties(): CopyParams = this.getRef().properties()

public fun <T : CopyRead, W : Write> BufReader<T>.drainTo(writer: W, outerLimit: ULong): Result<ULong> {
    val buf = this.buffer()
    val buf1 = buf[0 until minOf(buf.len(), outerLimit.tryInto().unwrapOr(Int.MAX_VALUE))]
    val bytes = buf1.len()
    when (val tryResult = writer.writeAll(buf1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    this.consume(bytes)
    val remaining = outerLimit - bytes.toULong()
    // in case of nested bufreaders we also need to drain the ones closer to the source
    val innerBytes = when (val tryResult = this.getMut().drainTo(writer, remaining)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(bytes.toULong() + innerBytes)
}

public fun <T : CopyRead> BufReader<T>.taken(bytes: ULong) {
    this.getMut().taken(bytes)
}

public fun <T : CopyRead> BufReader<T>.minLimit(): ULong = this.getRef().minLimit()

public fun <T : CopyRead> BufReader<T>.properties(): CopyParams = this.getRef().properties()

public fun <T : CopyWrite> BufWriter<T>.properties(): CopyParams = this.getRef().properties()

public fun CachedFileMetadata.properties(): CopyParams = CopyParams(FdMeta.Metadata(this.field1.clone()), this.value.asRawFd())

public fun <T : AsRawFd> fdToMeta(fd: T): FdMeta {
    val fd1 = fd.asRawFd()
    val file: File = File.fromRawFd(fd1)
    return run {
        val matchVal = file.metadata()
        when (matchVal) { is Result.Ok<*> -> {
            val meta = matchVal.value
            FdMeta.Metadata(meta)
        }; is Result.Err<*> -> {
            FdMeta.NoneObtained
        } }
    }
}

public sealed class CopyResult {
    public data class Ended(public var value: ULong) : CopyResult()
    public data class Error(public var field0: Error, public var field1: ULong) : CopyResult()
    public data class Fallback(public var value: ULong) : CopyResult()

    public open fun updateTake(reader: CopyRead) {
        when (this) {
            is CopyResult.Fallback -> {
                val (bytes) = this
                reader.taken(bytes)
            }
            is CopyResult.Ended -> {
                val (bytes1) = this
                reader.taken(bytes1)
            }
            is CopyResult.Error -> {
                val (_, bytes2) = this
                reader.taken(bytes2)
            }
        }
    }
}

/**
 * Invalid file descriptor.
 *
 * Valid file descriptors are guaranteed to be positive numbers (see `open()` manpage)
 * while negative values are used to indicate errors.
 * Thus -1 will never be overlap with a valid open file.
 */
public val INVALID_FD: RawFd = -1

/**
 * Linux-specific implementation that will attempt to use copyFileRange for copy offloading.
 * As the name says, it only works on regular files.
 *
 * Callers must handle fallback to a generic copy loop.
 * `Fallback` may indicate non-zero number of bytes already written
 * if one of the files' cursor +`maxLen` would exceed u64.Max (`EOVERFLOW`).
 */
public fun copyRegularFiles(reader: RawFd, writer: RawFd, maxLen: ULong): CopyResult {
    val NOT_PROBED: UByte = 0u
    val UNAVAILABLE: UByte = 1u
    val AVAILABLE: UByte = 2u
    // Kernel prior to 4.5 don't have copyFileRange
    // We store the availability in a global to avoid unnecessary syscalls
    val HAS_COPY_FILE_RANGE: Atomic<UByte>  = AtomicU8.new(NOT_PROBED)
    fun probeCopyFileRangeSupport(): UByte {
        // In some cases, we cannot determine availability from the first
        // `copyFileRange` call. In this case, we probe with an invalid file
        // descriptor so that the results are easily interpretable.
        return run {
            val matchVal = cvt(copyFileRange(INVALID_FD, MutPtr(0L), INVALID_FD, MutPtr(0L), 1, 0)).mapErr({ e -> e.rawOsError() })
            when {
                matchVal is Result.Err<*> && (matchVal.error != null && (matchVal.error == EPERM || matchVal.error == ENOSYS)) -> UNAVAILABLE
                matchVal is Result.Err<*> && matchVal.error != null && matchVal.error == EBADF -> AVAILABLE
                matchVal is Result.Ok<*> -> error("unexpected copy_file_range probe success")
                // Treat other errors as the syscall
                // being unavailable.
                else -> UNAVAILABLE
            }
        }
    }
    var haveProbed =
        when (HAS_COPY_FILE_RANGE.load(Ordering.Relaxed)) {
            NOT_PROBED -> false
            UNAVAILABLE -> { return CopyResult.Fallback(0) }
            else -> true
        }
    syscall(copyFileRange, fdIn, libc, cInt, offIn, libc, loffT, fdOut, libc, cInt, offOut, libc, loffT, len, libc, sizeT, flags, libc, cUint, libc, ssizeT)
    var written = 0uL
    while (written < maxLen) {
        val bytesToCopy = minOf(maxLen - written, Int.MAX_VALUE.toULong())
        // cap to 1GB chunks in case u64.Max is passed as maxLen and the file has a non-zero seek position
        // this allows us to copy large chunks without hitting EOVERFLOW,
        // unless someone sets a file offset close to u64.Max - 1GB, in which case a fallback would be required
        val bytesToCopy1 = minOf(bytesToCopy.toInt(), 0x40000000)
        val copyResult = cvt(copyFileRange(reader, MutPtr(0L), writer, MutPtr(0L), bytesToCopy1, 0))
        if (!haveProbed && copyResult.isOk()) {
            haveProbed = true
            HAS_COPY_FILE_RANGE.store(AVAILABLE, Ordering.Relaxed)
        }
        when {
            copyResult is Result.Ok<*> && copyResult.value == 0 && written == 0uL -> {
                // fallback to work around several kernel bugs where copyFileRange will fail to
                // copy any bytes and return 0 instead of an error if
                // - reading virtual files from the proc filesystem which appear to have 0 size
                // but are not empty. noted in coreutils to affect kernels at least up to 5.6.19.
                // - copying from an overlay filesystem in docker. reported to occur on fedora 32.
                return CopyResult.Fallback(0)
            }
            copyResult is Result.Ok<*> && copyResult.value == 0 -> { return CopyResult.Ended(written) }
            // reached EOF
            copyResult is Result.Ok<*> -> {
                val ret = (copyResult as Result.Ok<*>).value
                written += ret.toULong()
            }
            else -> {
                val err = (copyResult as Result.Err<*>).error
                return run {
                    val matchVal = err.rawOsError()
                    when {
                        // when file offset + maxLength > u64.Max
                        matchVal != null && matchVal == EOVERFLOW -> CopyResult.Fallback(written)
                        (matchVal != null && (matchVal == ENOSYS || matchVal == EXDEV || matchVal == EINVAL || matchVal == EPERM || matchVal == EOPNOTSUPP || matchVal == EBADF)) && written == 0uL -> {
                            val rawOsError = matchVal
                            if (!haveProbed) {
                                val available =
                                    if (when (rawOsError) {
                                        ENOSYS, EOPNOTSUPP, EPERM -> true
                                        else -> false
                                    }) {
                                        // EPERM can indicate seccomp filters or an
                                        // immutable file. To distinguish these
                                        // cases we probe with invalid file
                                        // descriptors which should result in EBADF
                                        // if the syscall is supported and EPERM or
                                        // ENOSYS if it's not available.
                                        //
                                        // For EOPNOTSUPP, see below. In the case of
                                        // ENOSYS, we try to cover for faulty FUSE
                                        // drivers.
                                        probeCopyFileRangeSupport()
                                    } else {
                                        AVAILABLE
                                    }
                                HAS_COPY_FILE_RANGE.store(available, Ordering.Relaxed)
                            }
                            // Try fallback io.copy if either:
                            // - Kernel version is < 4.5 (ENOSYS¹)
                            // - Files are mounted on different fs (EXDEV)
                            // - copyFileRange is broken in various ways on RHEL/CentOS 7 (EOPNOTSUPP)
                            // - copyFileRange file is immutable or syscall is blocked by seccomp¹ (EPERM)
                            // - copyFileRange cannot be used with pipes or device nodes (EINVAL)
                            // - the writer fd was opened with O_APPEND (EBADF²)
                            // and no bytes were written successfully yet. (All these errnos should
                            // not be returned if something was already written, but they happen in
                            // the wild, see #91152.)
                            //
                            // ¹ these cases should be detected by the initial probe but we handle them here
                            // anyway in case syscall interception changes during runtime
                            // ² actually invalid file descriptors would cause this too, but in that case
                            // the fallback code path is expected to encounter the same error again
                            CopyResult.Fallback(0)
                        }
                        else -> CopyResult.Error(err, written)
                    }
                }
            }
        }
    }
    return CopyResult.Ended(written)
}

public sealed class SpliceMode {
    public data object Sendfile : SpliceMode()
    public data object Splice : SpliceMode()

    public fun rustEquals(other: SpliceMode): Boolean = this == other
}

/**
 * performs splice or sendfile between file descriptors
 * Does _not_ fall back to a generic copy loop.
 */
public fun sendfileSplice(mode: SpliceMode, reader: RawFd, writer: RawFd, len: ULong): CopyResult {
    val HAS_SENDFILE: Atomic<Boolean>  = AtomicBool.new(true)
    val HAS_SPLICE: Atomic<Boolean>  = AtomicBool.new(true)
    // Android builds use feature level 14, but the libc wrapper for splice is
    // gated on feature level 21+, so we have to invoke the syscall directly.
    syscall(splice, srcfd, libc, cInt, srcOffset, dstfd, libc, cInt, dstOffset, len, libc, sizeT, flags, libc, cInt, libc, ssizeT)
    when {
        mode == SpliceMode.Sendfile && !HAS_SENDFILE.load(Ordering.Relaxed) -> {
            return CopyResult.Fallback(0)
        }
        mode == SpliceMode.Splice && !HAS_SPLICE.load(Ordering.Relaxed) -> {
            return CopyResult.Fallback(0)
        }
        else -> Unit
    }
    var written = 0uL
    while (written < len) {
        // according to its manpage that's the maximum size sendfile() will copy per invocation
        val chunkSize = Cmp.min(len - written, 0x7ffff000uL).toInt()
        val result =
            when (mode) {
                SpliceMode.Sendfile -> {
                    cvt(sendfile64(writer, reader, MutPtr(0L), chunkSize))
                }
                SpliceMode.Splice -> cvt(Splice(reader, MutPtr(0L), writer, MutPtr(0L), chunkSize, 0))
            }
        when {
            result is Result.Ok<*> && result.value == 0 -> { break }
            // EOF
            result is Result.Ok<*> -> {
                val ret = (result as Result.Ok<*>).value
                written += ret.toULong()
            }
            else -> {
                val err = (result as Result.Err<*>).error
                return run {
                    val matchVal = err.rawOsError()
                    when {
                        matchVal != null && (matchVal == ENOSYS || matchVal == EPERM) -> {
                            // syscall not supported (ENOSYS)
                            // syscall is disallowed, e.g. by seccomp (EPERM)
                            when (mode) {
                                SpliceMode.Sendfile -> HAS_SENDFILE.store(false, Ordering.Relaxed)
                                SpliceMode.Splice -> HAS_SPLICE.store(false, Ordering.Relaxed)
                            }
                            check(written == 0uL)
                            CopyResult.Fallback(0)
                        }
                        matchVal != null && matchVal == EINVAL -> {
                            // splice/sendfile do not support this particular file descriptor (EINVAL)
                            check(written == 0uL)
                            CopyResult.Fallback(0)
                        }
                        matchVal != null && mode == SpliceMode.Sendfile && matchVal == EOVERFLOW -> {
                            val osErr = matchVal
                            CopyResult.Fallback(written)
                        }
                        else -> CopyResult.Error(err, written)
                    }
                }
            }
        }
    }
    return CopyResult.Ended(written)
}

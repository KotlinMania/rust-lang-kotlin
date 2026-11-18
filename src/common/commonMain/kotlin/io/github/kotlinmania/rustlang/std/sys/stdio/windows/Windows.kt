// port-lint: source sys/stdio/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.stdio.windows

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.splitFirst
import io.github.kotlinmania.rustlang.core.str.validations.utf8CharWidth
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.CP_UTF8
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_DEVICE_CONSOLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.INVALID_HANDLE_VALUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ConsoleReadconsoleControl
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileFsDeviceInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IoStatusBlock
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_ERROR_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_INPUT_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STD_OUTPUT_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getStdHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.multiByteToWideChar
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ntQueryVolumeInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.readConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.wideCharToMultiByte
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.writeConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

// Don't cache handles but get them fresh for every read/write. This allows us to track changes to
// the value over time (such as if a process calls `SetStdHandle` while it's running). See #40490.
public open class Stdin(
    public var surrogate: UShort,
    public var incompleteUtf8: IncompleteUtf8,
) : Read {
    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val handle = when (val tryResult = getHandle(STD_INPUT_HANDLE)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        if (!isConsole(handle)) {
            run {
                val handle1 = Handle.fromRawHandle(handle)
                val ret = handle1.read(buf)
                handle1.intoRawHandle()
                // Don't close the handle
                return ret
            }
        }
        // If there are bytes in the incomplete utf-8, start with those.
        // (No-op if there is nothing in the buffer.)
        var bytesCopied = this.incompleteUtf8.read(buf)
        return if (bytesCopied == buf.size) {
            Result.Ok<Int, Error>(bytesCopied)
        } else if (buf.size - bytesCopied < 4) {
            // Not enough space to get a UTF-8 byte. We will use the incomplete UTF8.
            val utf16Buf = MutableList(2) { MaybeUninit.new(0, PrimitiveLayouts.U16) }
            // Read one u16 character.
            val read = when (val tryResult = readU16sFixupSurrogates(handle, utf16Buf, 1, this.surrogate)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
            // Read bytes, using the (now-empty) self.incompleteUtf8 as extra space.
            val readBytes = when (val tryResult = utf16ToUtf8(utf16Buf[0 until read].assumeInitRef(), this.incompleteUtf8.bytes)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
            // Read in the bytes from incompleteUtf8 until the buffer is full.
            this.incompleteUtf8.len = readBytes.toUByte()
            // No-op if no bytes.
            bytesCopied += this.incompleteUtf8.read(buf.subList(bytesCopied, buf.size))
            Result.Ok<Int, Error>(bytesCopied)
        } else {
            val utf16Buf1 = MutableList(MAX_BUFFER_SIZE / 2) { MaybeUninit.uninit(PrimitiveLayouts.U16) }
            // In the worst case, a UTF-8 string can take 3 bytes for every `UShort` of a UTF-16. So
            // we can read at most a third of `buf.len()` chars and uphold the guarantee no data gets
            // lost.
            val amount = minOf(buf.size / 3, utf16Buf1.size)
            val read1 = when (val tryResult = readU16sFixupSurrogates(handle, utf16Buf1, amount, this.surrogate)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
            // Safety `readU16sFixupSurrogates` returns the number of items
            // initialized.
            val utf16s = utf16Buf1[0 until read1].assumeInitRef()
            run {
                val matchVal = utf16ToUtf8(utf16s, buf)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val value = matchVal.okValue()
                    Result.Ok<Int, Error>(bytesCopied + value)
                }; is Result.Err<*, *> -> {
                    val e = matchVal.errValue()
                    Result.Err<Int, Error>(e)
                } }
            }
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Stdin> =
            object : TypeLayout<Stdin> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Stdin =
                    Stdin(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (IncompleteUtf8.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Stdin) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.surrogate)
                    (IncompleteUtf8.typeLayout()).writeNative(addr + 2L, value.incompleteUtf8)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stdin =
                    Stdin(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (IncompleteUtf8.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stdin) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.surrogate)
                    (IncompleteUtf8.typeLayout()).writeBytes(buf, offset + 2, value.incompleteUtf8)
                }
            }

        public fun new(): Stdin = Stdin(surrogate = 0u, incompleteUtf8 = IncompleteUtf8.new())
    }
}

public open class Stdout(
    public var incompleteUtf8: IncompleteUtf8,
) : Write {
    public open fun write(buf: List<UByte>): Result<Int, Error> = write(STD_OUTPUT_HANDLE, buf, this.incompleteUtf8)

    public open fun flush(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public companion object {
        public fun typeLayout(): TypeLayout<Stdout> =
            object : TypeLayout<Stdout> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 5L
                override fun readNative(addr: Long): Stdout =
                    Stdout(
                        (IncompleteUtf8.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Stdout) {
                    (IncompleteUtf8.typeLayout()).writeNative(addr, value.incompleteUtf8)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stdout =
                    Stdout(
                        (IncompleteUtf8.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stdout) {
                    (IncompleteUtf8.typeLayout()).writeBytes(buf, offset, value.incompleteUtf8)
                }
            }

        public fun new(): Stdout = Stdout(incompleteUtf8 = IncompleteUtf8.new())
    }
}

public open class Stderr(
    public var incompleteUtf8: IncompleteUtf8,
) : Write {
    public open fun write(buf: List<UByte>): Result<Int, Error> = write(STD_ERROR_HANDLE, buf, this.incompleteUtf8)

    public open fun flush(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public companion object {
        public fun typeLayout(): TypeLayout<Stderr> =
            object : TypeLayout<Stderr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 5L
                override fun readNative(addr: Long): Stderr =
                    Stderr(
                        (IncompleteUtf8.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Stderr) {
                    (IncompleteUtf8.typeLayout()).writeNative(addr, value.incompleteUtf8)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stderr =
                    Stderr(
                        (IncompleteUtf8.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stderr) {
                    (IncompleteUtf8.typeLayout()).writeBytes(buf, offset, value.incompleteUtf8)
                }
            }

        public fun new(): Stderr = Stderr(incompleteUtf8 = IncompleteUtf8.new())
    }
}

public open class IncompleteUtf8(
    public var bytes: UByteArray,
    public var len: UByte,
) {
    // Implemented for use in Stdin.read.
    public open fun read(buf: MutableList<UByte>): Int {
        // Write to buffer until the buffer is full or we run out of bytes.
        val toWrite = minOf(buf.size, this.len.toInt())
        run { val dstTmp0 = buf; val srcTmp0 = ((this.bytes).sliceArray(0 until toWrite)); for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
        // Rotate the remaining bytes if not enough remaining space in buffer.
        if (Int.from(this.len) > buf.size) {
            this.bytes.copyWithin(toWrite until Int.MAX_VALUE, 0)
            this.len -= toWrite.toUByte()
        } else {
            this.len = 0u
        }
        return toWrite
    }

    public companion object {
        public fun typeLayout(): TypeLayout<IncompleteUtf8> =
            object : TypeLayout<IncompleteUtf8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 5L
                override fun readNative(addr: Long): IncompleteUtf8 =
                    IncompleteUtf8(
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: IncompleteUtf8) {
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr, value.bytes)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IncompleteUtf8 =
                    IncompleteUtf8(
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IncompleteUtf8) {
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset, value.bytes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.len)
                }
            }

        public fun new(): IncompleteUtf8 = IncompleteUtf8(bytes = UByteArray((Char.MAX_LEN_UTF8).toInt()) { 0u.toUByte() }, len = 0u)
    }
}

// The way the console used to work up to Windows 8 is that the "LPC"s carried
// a small scratch space for reads/writes. If the client exceeded it, the server
// allocated a larger buffer from a fixed size section object. Its size was
// `CONSOLE_PORT_MEMORY_SIZE`, aka 64KiB. This meant any IO larger than that failed.
// If the Windows 7 source code ever leaks: The relevant function is `ConsoleAllocateCaptureBuffer`.
//
// It remains a good size for a stack allocated buffer, however.
public val MAX_BUFFER_SIZE: Int = 8192

// The standard buffer size of BufReader for Stdin should be able to hold 3x more bytes than there
// are `UShort`s in MAX_BUFFER_SIZE. This ensures the read data can always be completely decoded from
// UTF-16 to UTF-8.
public val STDIN_BUF_SIZE: Int = MAX_BUFFER_SIZE / 2 * 3

public val MAX_WRITE_HEAP_LEN: Int = 64 * 1024

// In u16 -> 128KiB
public fun getHandle(handleId: UInt): Result<Handle, Error> {
    val handle = getStdHandle(handleId)
    return if (handle == INVALID_HANDLE_VALUE) {
        Result.Err<Handle, Error>(Error.lastOsError())
    } else if (handle.isNull()) {
        Result.Err<Handle, Error>(Error.fromRawOsError(ERROR_INVALID_HANDLE.toInt()))
    } else {
        Result.Ok<Handle, Error>(handle)
    }
}

/**
 * Returns true if `handle` refers to a proper console (aka conhost).
 */
public fun isConsole(handle: Handle): Boolean =
    run {
        // `GetConsoleMode` incurs a remote RPC round-trip (= bad), so it's best avoided.
        // `NtQueryVolumeInformationFile` lets us test the handle directly.
        // The required `FILE_DEVICE_CONSOLE` type was introduced in Windows 8.
        // Fun fact: `GetFileType` uses `NtQueryVolumeInformationFile` internally.
        val ioStatus = MaybeUninit.uninit(UnresolvedTypeLayout<IoStatusBlock>())
        val deviceInfo = MaybeUninit.uninit(UnresolvedTypeLayout<FileFsDeviceInformation>())
        val status = ntQueryVolumeInformationFile(handle, ioStatus.asMutPtr(), deviceInfo.asMutPtr().cast(), sizeOfVal(deviceInfo).toUInt(), FileFsDeviceInformation)
        status >= 0 && deviceInfo.assumeInitRef().deviceType == FILE_DEVICE_CONSOLE
    }

public fun write(handleId: UInt, data: List<UByte>, incompleteUtf8: IncompleteUtf8): Result<Int, Error> {
    if (data.isEmpty()) {
        return Result.Ok<Int, Error>(0)
    }
    val handle = when (val tryResult = getHandle(handleId)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
    // At the time of writing, console IO on Windows is high latency:
    // Windows has no TTY buffers, making every call a fully synchronous RPC.
    // So, while always deferring to `writeConsoleUtf16` wastes valuable heap for
    // our UTF16 conversion, it handily beats doing 2 (or more) costly RPC round-trips.
    return if (!isConsole(handle)) {
        run {
            val handle1 = Handle.fromRawHandle(handle)
            val ret = handle1.write(data)
            handle1.intoRawHandle()
            // Don't close the handle
            ret
        }
    } else {
        writeConsoleUtf16(data, incompleteUtf8, handle)
    }
}

public fun writeConsoleUtf16(data: List<UByte>, incompleteUtf8: IncompleteUtf8, handle: Handle): Result<Int, Error> {
    // As stated for `MAX_BUFFER_SIZE`, Windows 7 does not support large writes at all.
    // --> Fall back to the small-writes stack-alloc path.
    if (data.size > MAX_BUFFER_SIZE && data.size.min(MAX_WRITE_HEAP_LEN) != null && Box.tryNewUninitSliceIn(capacity, System()) is Result.Ok<*, *>) {
        return writeConsoleWithBuffer(data, incompleteUtf8, utf16, handle)
    }
    return writeConsoleStack(data, incompleteUtf8, handle)
}

// Avoid stack allocations unless called
public fun writeConsoleStack(data: List<UByte>, incompleteUtf8: IncompleteUtf8, handle: Handle): Result<Int, Error> {
    val utf16 = MutableList(MAX_BUFFER_SIZE) { MaybeUninit.uninit(PrimitiveLayouts.U16) }
    return writeConsoleWithBuffer(data, incompleteUtf8, utf16, handle)
}

public fun writeConsoleWithBuffer(data: List<UByte>, incompleteUtf8: IncompleteUtf8, utf16: MutableList<MaybeUninit<UShort>>, handle: Handle): Result<Int, Error> {
    var written = 0
    while (written < data.size) {
        run {
            val matchVal = writeConsoleChunk(((data).subList(written, (data).size)), incompleteUtf8, utf16, handle)
            when {
                matchVal is Result.Ok<*, *> && matchVal.okValue() == 0 -> {
                    val okValueVal = matchVal.okValue()
                    break
                }
                matchVal is Result.Ok<*, *> -> {
                    val consumed = matchVal.okValue()
                    written += consumed
                }
                matchVal is Result.Err<*, *> && written == 0 -> {
                    val error = matchVal.errValue()
                    return Result.Err<Int, Error>(error)
                }
                else -> { break }
            }
        }
    }
    return Result.Ok<Int, Error>(written)
}

public fun writeConsoleChunk(data: List<UByte>, incompleteUtf8: IncompleteUtf8, utf16: MutableList<MaybeUninit<UShort>>, handle: Handle): Result<Int, Error> {
    check(utf16.size >= 2) { "not enough space for a full codepoint" }
    check(utf16.size >= 1024) { "unreasonably small buffer" }
    var chunkLen = data.size.min(utf16.size - incompleteUtf8.len.toInt())
    if (chunkLen < data.size) {
        chunkLen = incompleteUtf8SuffixStart(((data).subList(0, chunkLen)))
    }
    val data1 = ((data).subList(0, chunkLen))
    var utf16Len = 0
    var remaining = data1
    // Join the incomplete sequence with the new data.
    if (incompleteUtf8.len > 0u.toUByte()) {
        var len = incompleteUtf8.len.toInt()
        val width = utf8CharWidth(incompleteUtf8.bytes[0])
        // Accumulate until we have a complete sequence,
        // or something recognizable as a invalid sequence.
        while (len < width && remaining.splitFirst() != null && b and 0xc0 == 0x80) {
            incompleteUtf8.bytes[len] = b
            len += 1
            remaining = rest
        }
        // If we consumed all of `data` without completing the sequence we're done.
        if (len < width && remaining.isEmpty()) {
            incompleteUtf8.len = len.toUByte()
            return Result.Ok<Int, Error>(data1.len())
        }
        utf16Len = when (val tryResult = utf8ToUtf16(((incompleteUtf8.bytes).sliceArray(0 until len)), utf16)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
    }
    // Strip off any incomplete, trailing UTF8 sequence.
    val (remaining1, trailing) = remaining.splitAt(incompleteUtf8SuffixStart(remaining))
    // Convert the bulk of the data to UTF16.
    if (!remaining1.isEmpty()) {
        utf16Len += when (val tryResult = utf8ToUtf16(remaining1, utf16.subList(utf16Len, utf16.size))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
    }
    if (utf16Len != 0) {
        // NOTE: WriteConsoleW does not result in short writes.
        val localStorageL234C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        when (val tryResult = cvt(writeConsoleW(handle, utf16.asPtr(), utf16Len.toUInt(), RustMutableReference(localStorageL234C8.asMutPtr(), localStorageL234C8.layout), MutPtr(0L)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
    }
    run { val dstTmp1 = incompleteUtf8.bytes; val srcTmp1 = trailing; for (idxTmp1 in srcTmp1.indices) dstTmp1[idxTmp1] = srcTmp1[idxTmp1] }
    incompleteUtf8.len = trailing.len().toUByte()
    return Result.Ok<Int, Error>(data1.len())
}

public fun incompleteUtf8SuffixStart(bytes: List<UByte>): Int {
    for (length in 1..3.min(bytes.size)) {
        val start = bytes.size - length
        val byte = bytes[start]
        if (byte and 0xc0u.toUByte() != 0x80u.toUByte()) {
            return if (utf8CharWidth(byte) > length) {
                start
            } else {
                bytes.size
            }
        }
    }
    return bytes.size
}

// We assume that if the last `UShort` is an unpaired surrogate they got sliced apart by our
// buffer size, and keep it around for the next read hoping to put them together.
// This is a best effort, and might not work if we are not the only reader on Stdin.
public fun readU16sFixupSurrogates(handle: Handle, buf: MutableList<MaybeUninit<UShort>>, amount: Int, surrogate: RustMutableReference<UShort>): Result<Int, Error> {
    var amount1 = amount
    val surrogate1 = surrogate
    // Insert possibly remaining unpaired surrogate from last read.
    var start = 0
    if ((surrogate1).read() != 0u.toUShort()) {
        buf[0] = MaybeUninit.new((surrogate1).read(), PrimitiveLayouts.U16)
        (surrogate1).write(0)
        start = 1
        if (amount1 == 1) {
            // Special case: `Stdin.read` guarantees we can always read at least one new `UShort`
            // and combine it with an unpaired surrogate, because the UTF-8 buffer is at least
            // 4 bytes.
            amount1 = 2
        }
    }
    var amount2 = when (val tryResult = readU16s(handle, buf.subList(start, amount1))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) } + start
    if (amount2 > 0) {
        // Safety: The returned `amount` is the number of values initialized,
        // and it is not 0, so we know that `buf[amount - 1]` have been
        // initialized.
        val lastChar = buf[amount2 - 1].assumeInit()
        if (when (lastChar) {
            in 0xD800..0xDBFF -> true
            else -> false
        }) {
            // high surrogate
            (surrogate1).write(lastChar)
            amount2 -= 1
        }
    }
    return Result.Ok<Int, Error>(amount2)
}

// Returns `Ok(n)` if it initialized `n` values in `buf`.
public fun readU16s(handle: Handle, buf: MutableList<MaybeUninit<UShort>>): Result<Int, Error> {
    // Configure the `pInputControl` parameter to not only return on `\r\n` but also Ctrl-Z, the
    // traditional DOS method to indicate end of character stream / user input (SUB).
    // See #38274 and https://stackoverflow.com/questions/43836040/win-api-readconsole.
    val CTRL_Z: UShort = 0x1Au
    val CTRL_Z_MASK: UInt = 1u shl CTRL_Z.toInt()
    val inputControl = ConsoleReadconsoleControl(nLength = sizeOf<ConsoleReadconsoleControl>(typeLayoutT = UnresolvedTypeLayout<ConsoleReadconsoleControl>()).toUInt(), nInitialChars = 0u, dwCtrlWakeupMask = CTRL_Z_MASK, dwControlKeyState = 0u)
    val localStorageL374C4 = AddressableLocal(0, PrimitiveLayouts.I32)
    var amount by localStorageL374C4
    while (true) {
        when (val tryResult = cvt(run {
            setLastError(0u)
            readConsoleW(handle, (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size.toUInt(), RustMutableReference(localStorageL374C4.asMutPtr(), localStorageL374C4.layout), inputControl)
        })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        // ReadConsoleW returns success with ERROR_OPERATION_ABORTED for Ctrl-C or Ctrl-Break.
        // Explicitly check for that case here and try again.
        if (amount == 0 && getLastError() == WinError.OPERATION_ABORTED) {
            continue
        }
        break
    }
    // Safety: if `amount> 0`, then that many bytes were written, so
    // `buf[amount as Int - 1]` has been initialized.
    if (amount > 0 && buf[amount - 1].assumeInit() == CTRL_Z) {
        amount -= 1
    }
    return Result.Ok<Int, Error>(amount)
}

public fun utf16ToUtf8(utf16: List<UShort>, utf8: MutableList<UByte>): Result<Int, Error> {
    check(utf16.size <= Int.MAX_VALUE)
    check(utf8.size <= Int.MAX_VALUE)
    if (utf16.isEmpty()) {
        return Result.Ok<Int, Error>(0)
    }
    val result = wideCharToMultiByte(CP_UTF8, 0u, utf16.asPtr(), utf16.size, utf8.asMutPtr(), utf8.size, ConstPtr(0L), MutPtr(0L))
    return if (result == 0) {
        return Result.Err<Int, Error>(Error.lastOsError())
    } else {
        Result.Ok<Int, Error>(result)
    }
}

public fun utf8ToUtf16(data: List<UByte>, utf16: MutableList<MaybeUninit<UShort>>): Result<Int, Error> {
    check(!data.isEmpty())
    if (utf16.isEmpty()) {
        return Result.Ok<Int, Error>(0)
    }
    // Fun fact: As is often the case, Windows' algorithm predates Unicode's recommendation.
    // The current behavior was implemented for Windows Vista (2007) and Unicode's recommendation
    // for "Best Practices for Using U+FFFD" came with Unicode 5.2 (2009) [^1]. So, now if a
    // continuation byte violates a boundary limit, it should restore it [^2], but this consumes it.
    // For instance, `ED A0 80` produces two U+FFFD with this API, while it should be three.
    // In practice this difference bears no meaning for console output.
    //
    // NOTE: However, it means we have to be careful about the treatment of `incompleteUtf8`,
    // because the behavior of `MultiByteToWideChar` could theoretically change in the future.
    //
    // `MultiByteToWideChar` is technically a fancy wrapper around `RtlUTF8ToUnicodeN`,
    // whose MSDN is a bit more enlightening about the behavior of the conversion.
    // Despite its name, it's not a syscall, but a simple function inside ntdll.dll.
    //
    // [^1]: https://www.unicode.org/review/pr-121.html
    // [^2]: https://encoding.spec.whatwg.org/#utf-8-decoder
    val len = multiByteToWideChar(CP_UTF8, 0u, data.asPtr(), data.size, utf16.asMutPtr(), utf16.size)
    if (len <= 0) {
        return Result.Err<Int, Error>(Error.lastOsError())
    }
    return Result.Ok<Int, Error>(len)
}

public fun isEbadf(err: Error): Boolean = (err.rawOsError() == ERROR_INVALID_HANDLE.toInt()).toUByte()

public fun panicOutput(): Write? = Stderr.new()

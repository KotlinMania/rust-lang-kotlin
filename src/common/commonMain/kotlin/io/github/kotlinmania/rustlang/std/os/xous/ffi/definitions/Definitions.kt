// port-lint: source os/xous/ffi/definitions.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.TryFromIntError
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Indicates a particular syscall number as used by the Xous kernel.
 */
public sealed class Syscall(
    public val value: Int,
) {
    public data object MapMemory : Syscall(2)
    public data object Yield : Syscall(3)
    public data object UpdateMemoryFlags : Syscall(12)
    public data object ReceiveMessage : Syscall(15)
    public data object SendMessage : Syscall(16)
    public data object Connect : Syscall(17)
    public data object CreateThread : Syscall(18)
    public data object UnmapMemory : Syscall(19)
    public data object ReturnMemory : Syscall(20)
    public data object TerminateProcess : Syscall(22)
    public data object TrySendMessage : Syscall(24)
    public data object TryConnect : Syscall(25)
    public data object GetThreadId : Syscall(32)
    public data object JoinThread : Syscall(36)
    public data object AdjustProcessLimit : Syscall(38)
    public data object ReturnScalar : Syscall(40)
}

/**
 * Copies of these invocation types here for when we're running
 * in environments without libxous.
 */
public sealed class SyscallResult(
    public val value: Int,
) {
    public data object Ok : SyscallResult(0)
    public data object Error : SyscallResult(1)
    public data object MemoryRange : SyscallResult(3)
    public data object ConnectionId : SyscallResult(7)
    public data object Message : SyscallResult(9)
    public data object ThreadId : SyscallResult(10)
    public data object Scalar1 : SyscallResult(14)
    public data object Scalar2 : SyscallResult(15)
    public data object MemoryReturned : SyscallResult(18)
    public data object Scalar5 : SyscallResult(20)
}

/**
 * A list of all known errors that may be returned by the Xous kernel.
 */
public sealed class Error(
    public val value: Int,
) {
    public data object NoError : Error(0)
    public data object BadAlignment : Error(1)
    public data object BadAddress : Error(2)
    public data object OutOfMemory : Error(3)
    public data object MemoryInUse : Error(4)
    public data object InterruptNotFound : Error(5)
    public data object InterruptInUse : Error(6)
    public data object InvalidString : Error(7)
    public data object ServerExists : Error(8)
    public data object ServerNotFound : Error(9)
    public data object ProcessNotFound : Error(10)
    public data object ProcessNotChild : Error(11)
    public data object ProcessTerminated : Error(12)
    public data object Timeout : Error(13)
    public data object InternalError : Error(14)
    public data object ServerQueueFull : Error(15)
    public data object ThreadNotAvailable : Error(16)
    public data object UnhandledSyscall : Error(17)
    public data object InvalidSyscall : Error(18)
    public data object ShareViolation : Error(19)
    public data object InvalidThread : Error(20)
    public data object InvalidPid : Error(21)
    public data object UnknownError : Error(22)
    public data object AccessDenied : Error(23)
    public data object UseBeforeInit : Error(24)
    public data object DoubleFree : Error(25)
    public data object DebugInProgress : Error(26)
    public data object InvalidLimit : Error(27)

    public open fun fmt(f: StringBuilder) {
        f.append((when (this) {
            Error.NoError -> "no error occurred"
            Error.BadAlignment -> "memory was not properly aligned"
            Error.BadAddress -> "an invalid address was supplied"
            Error.OutOfMemory -> "the process or service has run out of memory"
            Error.MemoryInUse -> "the requested address is in use"
            Error.InterruptNotFound -> "the requested interrupt does not exist on this platform"
            Error.InterruptInUse -> "the requested interrupt is currently in use"
            Error.InvalidString -> "the specified string was not formatted correctly"
            Error.ServerExists -> "a server with that address already exists"
            Error.ServerNotFound -> "the requetsed server could not be found"
            Error.ProcessNotFound -> "the target process does not exist"
            Error.ProcessNotChild -> "the requested operation can only be done on child processes"
            Error.ProcessTerminated -> "the target process has crashed"
            Error.Timeout -> "the requested operation timed out"
            Error.InternalError -> "an internal error occurred"
            Error.ServerQueueFull -> "the server has too many pending messages"
            Error.ThreadNotAvailable -> "the specified thread does not exist"
            Error.UnhandledSyscall -> "the kernel did not recognize that syscall"
            Error.InvalidSyscall -> "the syscall had incorrect parameters"
            Error.ShareViolation -> "an attempt was made to share memory twice"
            Error.InvalidThread -> "tried to resume a thread that was not ready"
            Error.InvalidPid -> "kernel attempted to use a pid that was not valid"
            Error.AccessDenied -> "no permission to perform the requested operation"
            Error.UseBeforeInit -> "attempt to use a service before initialization finished"
            Error.DoubleFree -> "the requested resource was freed twice"
            Error.DebugInProgress -> "kernel attempted to activate a thread being debugged"
            Error.InvalidLimit -> "process attempted to adjust an invalid limit"
            Error.UnknownError -> "an unknown error occurred"
        }).toString())
    }

    public open fun fmtDebug(f: StringBuilder) {
        f.append("$this")
    }

    public companion object {
        public fun from(src: Int): Error =
            when (src) {
                0 -> Error.NoError
                1 -> Error.BadAlignment
                2 -> Error.BadAddress
                3 -> Error.OutOfMemory
                4 -> Error.MemoryInUse
                5 -> Error.InterruptNotFound
                6 -> Error.InterruptInUse
                7 -> Error.InvalidString
                8 -> Error.ServerExists
                9 -> Error.ServerNotFound
                10 -> Error.ProcessNotFound
                11 -> Error.ProcessNotChild
                12 -> Error.ProcessTerminated
                13 -> Error.Timeout
                14 -> Error.InternalError
                15 -> Error.ServerQueueFull
                16 -> Error.ThreadNotAvailable
                17 -> Error.UnhandledSyscall
                18 -> Error.InvalidSyscall
                19 -> Error.ShareViolation
                20 -> Error.InvalidThread
                21 -> Error.InvalidPid
                23 -> Error.AccessDenied
                24 -> Error.UseBeforeInit
                25 -> Error.DoubleFree
                26 -> Error.DebugInProgress
                27 -> Error.InvalidLimit
                22 -> Error.UnknownError
            }

        public fun fromInt(src: Int): Error {
            val src1 = TryInto.tryInto(src) ?: return Error.UnknownError
            return src1.into()
        }
    }
}

/**
 * Indicates the type of Message that is sent when making a `SendMessage` syscall.
 */
internal sealed class InvokeType(
    public val value: Int,
) {
    public data object LendMut : InvokeType(1)
    public data object Lend : InvokeType(2)
    public data object Move : InvokeType(3)
    public data object Scalar : InvokeType(4)
    public data object BlockingScalar : InvokeType(5)
}

/**
 * A representation of a connection to a Xous service.
 */
public open class Connection(
    public var value: UInt,
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public open fun into(): UInt = this.value

    public open fun tryInto(): Result<Int, TryFromIntError> = this.value.tryInto()

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Connection> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Connection> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Connection =
                    Connection(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Connection) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Connection =
                    Connection(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Connection) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }

        public fun from(src: UInt): Connection = Connection(src)

        public fun tryFrom(src: Int): Result<Connection, TryFromIntError> {
            return Result.Ok(Connection(when (val tryResult = src.tryInto()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
        }
    }
}

public sealed class ServerAddressError {
    public data object InvalidLength : ServerAddressError()
}

public open class ServerAddress(
    public var value: UIntArray,
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun into(): UIntArray = this.value

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ServerAddress> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ServerAddress> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ServerAddress =
                    ServerAddress(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ServerAddress) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ServerAddress =
                    ServerAddress(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ServerAddress) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        public fun tryFrom(value: String): Result<ServerAddress, ServerAddressError> {
            val b = value.encodeToByteArray().map { it.toUByte() }
            if (b.size == 0 || b.size > 16) {
                return Result.Err(ServerAddress.Error.InvalidLength)
            }
            val thisTemp = UByteArray(16) { 0u.toUByte() }
            for ((dest, src) in (thisTemp.zip(b))) {
                dest = src
            }
            val thisRef = UIntArray(4) { 0u }
            for ((dest1, src1) in (thisRef.zip(thisTemp.chunksExact(4)))) {
                dest1 = run { val bytesTmp0 = src1; ((bytesTmp0[3].toUInt() shl 24) or (bytesTmp0[2].toUInt() shl 16) or (bytesTmp0[1].toUInt() shl 8) or bytesTmp0[0].toUInt()) }
            }
            return Result.Ok(ServerAddress(thisRef))
        }
    }
}

internal open class ThreadId(
    public var value: Int,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public open fun into(): Int = this.value

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ThreadId> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ThreadId> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ThreadId =
                    ThreadId(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadId) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadId =
                    ThreadId(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadId) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }

        public fun from(src: Int): ThreadId = ThreadId(src)
    }
}

/**
 * Limits that can be passed to `AdjustLimit`
 */
internal sealed class Limits(
    public val value: Int,
) {
    public data object HeapMaximum : Limits(1)
    public data object HeapSize : Limits(2)
}

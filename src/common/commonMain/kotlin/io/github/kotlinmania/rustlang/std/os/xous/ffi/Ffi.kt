// port-lint: source os/xous/ffi.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.xous.ffi

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Limits
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.ServerAddress
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.memoryflags.MemoryFlags

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr, arg1: ULong, arg2: ULong): Nothing
}

public fun lendMutImpl(connection: Connection, opcode: Int, data: MutableList<UByte>, arg1: Int, arg2: Int, blocking: Boolean): Result<Pair<Int, Int>, Error> {
    var a0 =
        (if (blocking) {
            Syscall.SendMessage
        } else {
            Syscall.TrySendMessage
        }).toInt()
    val a1: Int  = connection.tryInto().unwrap()
    val a2 = InvokeType.LendMut.value.toInt()
    val a3 = opcode
    val a4 = data.asMutPtr()
    val a5 = data.size
    val a6 = arg1
    val a7 = arg2
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.MemoryReturned.value.toInt()) {
        Result.Ok(Pair(a1, a2))
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

internal fun lendMut(connection: Connection, opcode: Int, data: MutableList<UByte>, arg1: Int, arg2: Int): Result<Pair<Int, Int>, Error> = lendMutImpl(connection, opcode, data, arg1, arg2, true)

internal fun tryLendMut(connection: Connection, opcode: Int, data: MutableList<UByte>, arg1: Int, arg2: Int): Result<Pair<Int, Int>, Error> = lendMutImpl(connection, opcode, data, arg1, arg2, false)

public fun lendImpl(connection: Connection, opcode: Int, data: List<UByte>, arg1: Int, arg2: Int, blocking: Boolean): Result<Pair<Int, Int>, Error> {
    var a0 =
        (if (blocking) {
            Syscall.SendMessage
        } else {
            Syscall.TrySendMessage
        }).toInt()
    val a1: Int = connection.tryInto().unwrap()
    val a2 = InvokeType.Lend.value.toInt()
    val a3 = opcode
    val a4 = data.asPtr()
    val a5 = data.size
    val a6 = arg1
    val a7 = arg2
    var ret1: Any?
    var ret2: Any?
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, ret1, inlateout, "a2", a2, ret2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.MemoryReturned.value.toInt()) {
        Result.Ok(Pair(ret1, ret2))
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(ret1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

internal fun lend(connection: Connection, opcode: Int, data: List<UByte>, arg1: Int, arg2: Int): Result<Pair<Int, Int>, Error> = lendImpl(connection, opcode, data, arg1, arg2, true)

internal fun tryLend(connection: Connection, opcode: Int, data: List<UByte>, arg1: Int, arg2: Int): Result<Pair<Int, Int>, Error> = lendImpl(connection, opcode, data, arg1, arg2, false)

public fun scalarImpl(connection: Connection, args: IntArray, blocking: Boolean): Result<Unit, Error> {
    var a0 =
        (if (blocking) {
            Syscall.SendMessage
        } else {
            Syscall.TrySendMessage
        }).toInt()
    val a1: Int  = connection.tryInto().unwrap()
    val a2 = InvokeType.Scalar.value.toInt()
    val a3 = args[0]
    val a4 = args[1]
    val a5 = args[2]
    val a6 = args[3]
    val a7 = args[4]
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Ok.value.toInt()) {
        Result.Ok(Unit)
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

internal fun scalar(connection: Connection, args: IntArray): Result<Unit, Error> = scalarImpl(connection, args, true)

internal fun tryScalar(connection: Connection, args: IntArray): Result<Unit, Error> = scalarImpl(connection, args, false)

public fun blockingScalarImpl(connection: Connection, args: IntArray, blocking: Boolean): Result<IntArray, Error> {
    var a0 =
        (if (blocking) {
            Syscall.SendMessage
        } else {
            Syscall.TrySendMessage
        }).toInt()
    val a1: Int  = connection.tryInto().unwrap()
    val a2 = InvokeType.BlockingScalar.value.toInt()
    val a3 = args[0]
    val a4 = args[1]
    val a5 = args[2]
    val a6 = args[3]
    val a7 = args[4]
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Scalar1.value.toInt()) {
        Result.Ok(intArrayOf(a1, 0, 0, 0, 0))
    } else if (result == SyscallResult.Scalar2.value.toInt()) {
        Result.Ok(intArrayOf(a1, a2, 0, 0, 0))
    } else if (result == SyscallResult.Scalar5.value.toInt()) {
        Result.Ok(intArrayOf(a1, a2, a3, a4, a5))
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

internal fun blockingScalar(connection: Connection, args: IntArray): Result<IntArray, Error> = blockingScalarImpl(connection, args, true)

internal fun tryBlockingScalar(connection: Connection, args: IntArray): Result<IntArray, Error> = blockingScalarImpl(connection, args, false)

public fun connectImpl(address: ServerAddress, blocking: Boolean): Result<Connection, Error> {
    val a0 =
        (if (blocking) {
            Syscall.Connect
        } else {
            Syscall.TryConnect
        }).toInt()
    val address1: UIntArray = address.into()
    val a1: Int = address1[0].tryInto().unwrap()
    val a2: Int = address1[1].tryInto().unwrap()
    val a3: Int = address1[2].tryInto().unwrap()
    val a4: Int = address1[3].tryInto().unwrap()
    val a5 = 0
    val a6 = 0
    val a7 = 0
    var result: Int
    var value: Int
    asm("ecall", inlateout, "a0", a0, result, inlateout, "a1", a1, value, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    return if (result == SyscallResult.ConnectionId.value.toInt()) {
        Result.Ok(value.tryInto().unwrap())
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(value.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Connects to a Xous server represented by the specified `address`.
 *
 * The current thread will block until the server is available. Returns
 * an error if the server cannot accept any more connections.
 */
internal fun connect(address: ServerAddress): Result<Connection, Error> = connectImpl(address, true)

/**
 * Attempts to connect to a Xous server represented by the specified `address`.
 *
 * If the server does not exist then None is returned.
 */
internal fun tryConnect(address: ServerAddress): Result<Connection?, Error> =
    run {
        val matchVal = connectImpl(address, false)
        when {
            matchVal is Result.Ok<*> -> {
                val conn = (matchVal as Result.Ok<*>).value
                Result.Ok(conn)
            }
            matchVal is Result.Err<*> && matchVal.error == Error.ServerNotFound -> Result.Ok(null)
            else -> {
                val e = (matchVal as Result.Err<*>).error
                Result.Err(e)
            }
        }
    }

/**
 * Terminates the current process and returns the specified code to the parent process.
 */
internal fun exit(returnCode: UInt): Nothing {
    val a0 = Syscall.TerminateProcess.value.toInt()
    val a1 = returnCode.toInt()
    val a2 = 0
    val a3 = 0
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", "a0", a0, "a1", a1, "a2", a2, "a3", a3, "a4", a4, "a5", a5, "a6", a6, "a7", a7)
    error("unreachable")
}

/**
 * Suspends the current thread and allow another thread to run. This thread may
 * continue executing again immediately if there are no other threads available
 * to run on the system.
 */
internal fun doYield() {
    val a0 = Syscall.Yield.value.toInt()
    val a1 = 0
    val a2 = 0
    val a3 = 0
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
}

// The phys argument uses an integer rather than pointer type as pointer
// provenance only covers virtual memory, not physical memory.

/**
 * Allocates memory from the system.
 *
 * An optional physical and/or virtual address may be specified in order to
 * ensure memory is allocated at specific offsets, otherwise the kernel will
 * select an address.
 *
 * # Safety
 *
 * This function is safe unless a virtual address is specified. In that case,
 * the kernel will return an alias to the existing range. This violates Rust's
 * pointer uniqueness guarantee.
 */
internal fun <T> mapMemory(phys: NonZeroUsize?, virt: NonNull?, count: Int, flags: MemoryFlags, typeLayoutT: TypeLayout<T>): Result<MutableList<T>, Error> {
    val a0 = Syscall.MapMemory.value.toInt()
    val a1 = (phys?.let({ p -> p.get() }) ?: 0)
    val a1Out: MutPtr
    val a2 = (virt?.let({ p -> p.asPtr() }) ?: ConstPtr(0L))
    val a2Out: Int
    val a3 = count * sizeOf<T>(typeLayoutT = typeLayoutT)
    val a4 = flags.bits()
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, a1Out, inlateout, "a2", a2, a2Out, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.MemoryRange.value.toInt()) {
        val start = a1Out
        val len = a2Out / sizeOf<T>(typeLayoutT = typeLayoutT)
        Result.Ok(rawMutSliceList(start, len.toInt(), typeLayoutT))
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err((a1Out).rawAddress.toInt().into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Destroys the given memory, returning it to the compiler.
 *
 * Safety: The memory pointed to by `range` should not be used after this
 * function returns, even if this function returns Err().
 */
internal fun <T> unmapMemory(range: MutPtr, typeLayoutT: TypeLayout<T>): Result<Unit, Error> {
    val a0 = Syscall.UnmapMemory.value.toInt()
    val a1 = range.asMutPtr()
    val a2 = range.len() * sizeOf<T>(typeLayoutT = typeLayoutT)
    val a3 = 0
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Ok.value.toInt()) {
        Result.Ok(Unit)
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.addr().into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Adjusts the memory flags for the given range.
 *
 * This can be used to remove flags from a given region in order to harden
 * memory access. Note that flags may only be removed and may never be added.
 *
 * Safety: The memory pointed to by `range` may become inaccessible or have its
 * mutability removed. It is up to the caller to ensure that the flags specified
 * by `newFlags` are upheld, otherwise the program will crash.
 */
internal fun <T> updateMemoryFlags(range: MutPtr, newFlags: MemoryFlags, typeLayoutT: TypeLayout<T>): Result<Unit, Error> {
    val a0 = Syscall.UpdateMemoryFlags.value.toInt()
    val a1 = range.asMutPtr()
    val a1Out: Int
    val a2 = range.len() * sizeOf<T>(typeLayoutT = typeLayoutT)
    val a3 = newFlags.bits()
    val a4 = 0
    // Process ID is currently None
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, a1Out, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Ok.value.toInt()) {
        Result.Ok(Unit)
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1Out.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Creates a thread with a given stack and up to four arguments.
 */
internal fun createThread(start: FnCallback, stack: MutPtr, arg0: MutPtr, arg1: ConstPtr, arg2: Int, arg3: Int): Result<Any, Error> {
    val a0 = Syscall.CreateThread.value.toInt()
    val a1 = start
    val a1Out: Int
    val a2 = stack.asMutPtr()
    val a3 = stack.len()
    val a4 = arg0
    val a5 = arg1
    val a6 = arg2
    val a7 = arg3
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, a1Out, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.ThreadId.value.toInt()) {
        Result.Ok(a1Out.into())
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1Out.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Waits for the given thread to terminate and returns the exit code from that thread.
 */
internal fun joinThread(threadId: Any): Result<Int, Error> {
    val a0 = Syscall.JoinThread.value.toInt()
    val a1 = threadId.into()
    val a2 = 0
    val a3 = 0
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Scalar1.value.toInt()) {
        Result.Ok(a1)
    } else if (result == SyscallResult.Scalar2.value.toInt()) {
        Result.Ok(a1)
    } else if (result == SyscallResult.Scalar5.value.toInt()) {
        Result.Ok(a1)
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Gets the current thread's ID.
 */
internal fun threadId(): Result<Any, Error> {
    val a0 = Syscall.GetThreadId.value.toInt()
    val a1 = 0
    val a2 = 0
    val a3 = 0
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.ThreadId.value.toInt()) {
        Result.Ok(a1.into())
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

/**
 * Adjusts the given `knob` limit to match the new value `new`. The current value must
 * match the `current` in order for this to take effect.
 *
 * The new value is returned as a result of this call. If the call fails, then the old
 * value is returned. In either case, this function returns successfully.
 *
 * An error is generated if the `knob` is not a valid limit, or if the call
 * would not succeed.
 */
internal fun adjustLimit(knob: Limits, current: Int, new: Int): Result<Int, Error> {
    val a0 = Syscall.AdjustProcessLimit.value.toInt()
    val a1 = knob.toInt()
    val a2 = current
    val a3 = new
    val a4 = 0
    val a5 = 0
    val a6 = 0
    val a7 = 0
    asm("ecall", inlateout, "a0", a0, inlateout, "a1", a1, inlateout, "a2", a2, inlateout, "a3", a3, inlateout, "a4", a4, inlateout, "a5", a5, inlateout, "a6", a6, inlateout, "a7", a7)
    val result = a0
    return if (result == SyscallResult.Scalar2.value.toInt() && a1 == knob.toInt()) {
        Result.Ok(a2)
    } else if (result == SyscallResult.Scalar5.value.toInt() && a1 == knob.toInt()) {
        Result.Ok(a1)
    } else if (result == SyscallResult.Error.value.toInt()) {
        Result.Err(a1.into())
    } else {
        Result.Err(Error.InternalError)
    }
}

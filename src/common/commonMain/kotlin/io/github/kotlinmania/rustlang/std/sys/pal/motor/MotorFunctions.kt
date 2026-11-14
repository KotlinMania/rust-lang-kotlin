package io.github.kotlinmania.rustlang.std.sys.pal.motor

import io.github.kotlinmania.motort.Error
import io.github.kotlinmania.motort.ErrorCode
import io.github.kotlinmania.motort.process.exit
import io.github.kotlinmania.motort.start
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.pal.xous.ccompat.main

internal fun mapMotorError(err: Error): io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error {
    val errorCode: ErrorCode = err.into()
    return Error.fromRawOsError(errorCode.into())
}

/**
 * The buffer list `motoRt.fs.writeVectored` takes.
 *
 * Unix hands `writev` an `[IoSlice]` directly, but only because its `IoSlice`
 * is documented to be ABI-compatible with `iovec`. Motor uses the generic
 * representation, whose layout is deliberately unspecified, so the list is
 * rebuilt through the public `Deref` rather than reinterpreted. That costs one
 * small allocation against a filesystem or IPC round trip, and needs no
 * `unchecked` and no assumption about a type shared with every other target.
 */
internal fun ioSlices(bufs: List<IoSlice>): List<List<UByte>> = bufs.map({ buf -> buf }).toMutableList()

/**
 * The buffer list `motoRt.fs.readVectored` takes. See [ioSlices].
 */
internal fun ioSlicesMut(bufs: MutableList<IoSliceMut>): List<MutableList<UByte>> = bufs.map({ buf -> buf }).toMutableList()

// Weak: when a program is linked with mlibc (e.g. via the Motor clang
// driver, which always links mlibc's crt1.o), crt1.o's strong motorStart
// must win. mlibc's entry initializes the VDSO vtable and the C runtime
// (TCB, stdio, .initArray constructors) and then calls the C `main`
// that rustc generates, so Rust std works identically in both flows.
public fun motorStart(): Nothing {
    // Call main.
    fun main(arg0: Long, arg1: ConstPtr, arg2: UByte): Int = 0
    // Initialize the runtime.
    start()
    val result = main(0, ConstPtr(0L), 0)
    // Terminate the process.
    return exit(result)
}

// SAFETY: must be called only once during runtime initialization.
// NOTE: Motor OS uses motoRt.start() to initialize runtime (see above).
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {}

// SAFETY: must be called only once during runtime cleanup.
// NOTE: this is not guaranteed to run, for example when the program aborts.
public fun cleanup() {}

public fun <T> unsupported(): Result<T> = Result.Err(unsupportedErr())

public fun unsupportedErr(): io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error = Error.UNSUPPORTED_PLATFORM

public fun abortInternal(): Nothing = exit(-1)

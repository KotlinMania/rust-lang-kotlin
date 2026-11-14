package io.github.kotlinmania.rustlang.std.sys.pal.hermit

import io.github.kotlinmania.hermitabi.abort
import io.github.kotlinmania.hermitabi.exit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.env.hermit.env
import io.github.kotlinmania.rustlang.std.sys.pal.xous.ccompat.main
import io.github.kotlinmania.rustlang.std.sys.threadlocal.destructors.list.run

public fun <T> unsupported(): Result<T> = Result.Err(unsupportedErr())

public fun unsupportedErr(): Error = constError(ErrorKind.Unsupported, "operation not supported on Hermit yet")

public fun abortInternal(): Nothing = abort()

// SAFETY: must be called only once during runtime initialization.
// NOTE: this is not guaranteed to run, for example when Rust code is called externally.
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {
    run {
        io.github.kotlinmania.rustlang.std.sys.args.sgx.init(argc, argv)
    }
}

// SAFETY: must be called only once during runtime cleanup.
// NOTE: this is not guaranteed to run, for example when the program aborts.
public fun cleanup() {}

public fun runtimeEntry(argc: Int, argv: ConstPtr, env: ConstPtr): Nothing {
    fun main(argc1: Long, argv1: ConstPtr): Int = 0
    // initialize environment
    env.init(env)
    val result = main(argc, argv)
    run {
        run()
    }
    threadCleanup()
    return run {
        exit(result)
    }
}

public fun <T : IsNegative> cvt(t: T): Result<T> =
    if (t.isNegative()) {
        Result.Err(Error.fromRawOsError(t.negate()))
    } else {
        Result.Ok(t)
    }

public fun <T> cvtR(f: () -> T): Result<T> where T : IsNegative {
    while (true) {
        run {
            val matchVal = cvt(f())
            when {
                matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.isInterrupted() -> {
                    val e = (matchVal as Result.Err<*>).error
                }
                else -> {
                    val other = matchVal
                    return other
                }
            }
        }
    }
}

public fun Int.isNegative(): Boolean = (this < 0).toUByte()

public fun Int.negate(): Int = -this

public fun Byte.isNegative(): Boolean = (this < 0.toByte()).toUByte()

public fun Byte.negate(): Int = Int.tryFrom(-this).unwrap()

public fun Short.isNegative(): Boolean = (this < 0.toShort()).toUByte()

public fun Short.negate(): Int = Int.tryFrom(-this).unwrap()

public fun Long.isNegative(): Boolean = (this < 0L).toUByte()

public fun Long.negate(): Int = Int.tryFrom(-this).unwrap()

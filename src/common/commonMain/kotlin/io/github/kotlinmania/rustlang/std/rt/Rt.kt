// port-lint: source rt.rs
package io.github.kotlinmania.rustlang.std.rt

import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.process.Termination
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.once.Once
import io.github.kotlinmania.rustlang.std.sys.backtrace.rustBeginShortBacktrace
import io.github.kotlinmania.rustlang.std.sys.exit.uniqueThreadExit
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.key.keybased.CLEANUP
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.set
import io.github.kotlinmania.rustlang.std.thread.current.dropCurrent

// Runtime services
//
// The `rt` module provides a narrow set of runtime services,
// including the global heap (exported in `heap`) and unwinding and
// backtrace support. The APIs in this module are highly unstable,
// and should be considered as private implementation details for the
// time being.

// This function is needed by the panic runtime.
public fun rustAbort() {
    abort()
}

public inline fun <reified T> handleRtPanic(e: RustAny): T {
    forget(e)
    return run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("initialization or cleanup bug").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("initialization or cleanup bug").toString() + ", aborting\n"
        }
        abort()
    }
}

// One-time runtime initialization.
// Runs before `main`.
// SAFETY: must be called only once during runtime initialization.
// NOTE: this is not guaranteed to run, for example when Rust code is called externally.
//
// # The `sigpipe` parameter
//
// Since 2014, the Rust runtime on Unix has set the `SIGPIPE` handler to
// `SIG_IGN`. Applications have good reasons to want a different behavior
// though, so there is a `- Zon - broken - pipe` compiler flag that
// can be used to select how `SIGPIPE` shall be setup (if changed at all) before
// `fun main()` is called. See <https://github.com/rust-lang/rust/issues/97889>
// for more info.
//
// The `sigpipe` parameter to this function gets its value via the code that
// rustc generates to invoke `fun langStart()`. The reason we have `sigpipe` for
// all platforms and not only Unix, is because std is not allowed to have `cfg`
// directives as this high level. See the module docs in
// `src / tools / tidy / src / pal.rs` for more info. On all other platforms, `sigpipe`
// has a value, but its value is ignored.
//
// Even though it is an `UByte`, it only ever has 4 values. These are documented in
// `compiler / rustcSession / src / config / sigpipe.rs`.
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {
    // Remember the main thread ID to give it the correct name.
    // SAFETY: this is the only time and place where we call this function.
    set(thread.currentId())
    io.github.kotlinmania.rustlang.std.sys.args.unix.init(argc, argv, sigpipe)
}

/**
 * Clean up the thread-local runtime state. This *should* be run after all other
 * code managed by the Rust runtime, but will not cause UB if that condition is
 * not fulfilled. Also note that this function is not guaranteed to be run, but
 * skipping it will cause leaks and therefore is to be avoided.
 */
internal fun threadCleanup() {
    // This function is run in situations where unwinding leads to an abort
    // (think `extern "C"` functions). Abort here instead so that we can
    // print a nice message.
    catchUnwind({
        dropCurrent()
    }).unwrapOrElse(::handleRtPanic)
}

// One-time runtime cleanup.
// Runs after `main` or at program exit.
// NOTE: this is not guaranteed to run, for example when the program aborts.
internal fun cleanup() {
    val CLEANUP: Once  = Once.new()
    CLEANUP.callOnce({ run {
        // Flush stdout and disable buffering.
        io.github.kotlinmania.rustlang.std.io.stdio.cleanup()
        // SAFETY: Only called once during runtime cleanup.
        io.github.kotlinmania.rustlang.std.sys.pal.unix.cleanup()
    } })
}

// To reduce the generated code of the new `langStart`, this function is doing
// the real work.
public fun langStartInternal(main: () -> Int, argc: Int, argv: ConstPtr, sigpipe: UByte): Int {
    // Guard against the code called by this function from unwinding outside of the Rust-controlled
    // code, which is UB. This is a requirement imposed by a combination of how the
    // `@lang= "start"` attribute is implemented as well as by the implementation of the panicking
    // mechanism itself.
    //
    // There are a couple of instances where unwinding can begin. First is inside of the
    // `rt.init`, `rt.cleanup` and similar functions controlled by std. In those instances a
    // panic is a std implementation bug. A quite likely one too, as there isn't any way to
    // prevent std from accidentally introducing a panic to these functions. Another is from
    // user code from `main` or, more nefariously, as described in e.g. issue #86030.
    //
    // We use `catchUnwind` with `handleRtPanic` instead of `abortOnUnwind` to make the error
    // in case of a panic a bit nicer.
    return catchUnwind({
        // SAFETY: Only called once during runtime initialization.
        init(argc, argv, sigpipe)
        val retCode =
            catchUnwind(main).unwrapOrElse({ payload ->
                // Carefully dispose of the panic payload.
                val payload1 = AssertUnwindSafe(payload)
                catchUnwind({ drop(payload1.first) }).unwrapOrElse({ e ->
                    forget(e)
                    run {
                        when (val matchVal = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal
                                Write.writeFmt(out, "fatal runtime error: " + ("drop of the panic payload panicked").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("drop of the panic payload panicked").toString() + ", aborting\n"
                        }
                        abort()
                    }
                })
                // Return error code for panicking programs.
                101
            })
        val retCode1 = retCode.toInt()
        cleanup()
        // Guard against multiple threads calling `libc.exit` concurrently.
        // See the documentation for `uniqueThreadExit` for more information.
        uniqueThreadExit()
        retCode1
    }).unwrapOrElse(::handleRtPanic)
}

public fun <T : Termination> langStart(main: () -> T, argc: Int, argv: ConstPtr, sigpipe: UByte): Int = langStartInternal({ rustBeginShortBacktrace(main).report().toI32() }, argc, argv, sigpipe)

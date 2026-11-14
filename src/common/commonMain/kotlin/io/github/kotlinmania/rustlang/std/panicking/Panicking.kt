// port-lint: source panicking.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.panicking

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.any.Any
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.panic.location.Location
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.io.stdio.trySetOutputCapture
import io.github.kotlinmania.rustlang.std.panic.BacktraceStyle
import io.github.kotlinmania.rustlang.std.panic.PanicHookInfo
import io.github.kotlinmania.rustlang.std.panic.getBacktraceStyle
import io.github.kotlinmania.rustlang.std.panicking.paniccount.MustAbort
import io.github.kotlinmania.rustlang.std.panicking.paniccount.countIsZero
import io.github.kotlinmania.rustlang.std.panicking.paniccount.finishedPanicHook
import io.github.kotlinmania.rustlang.std.panicking.paniccount.getCount
import io.github.kotlinmania.rustlang.std.panicking.paniccount.increase
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.mpmc.counter.new
import io.github.kotlinmania.rustlang.std.sync.nonpoison.rwlock.RwLock
import io.github.kotlinmania.rustlang.std.sys.backtrace.PrintFmt
import io.github.kotlinmania.rustlang.std.sys.backtrace.lock
import io.github.kotlinmania.rustlang.std.sys.backtrace.rustEndShortBacktrace
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.current.currentOsId
import io.github.kotlinmania.rustlang.std.thread.current.withCurrentName

// Implementation of various bits and pieces of the `panic` macro and
// associated runtime pieces.
//
// Specifically, this module contains the implementation of:
//
// * Panic hooks
// * Executing a panic up to doing the actual implementation
// * Shims around "try"

public fun interface FnCallback {
    public operator fun invoke(arg0: String): Nothing
}

// This forces codegen of the function called by panic() inside the std crate, rather than in
// downstream crates. Primarily this is useful for rustc's codegen tests, which rely on noticing
// complete removal of panic from generated IR. Since beginPanic is inline(never), it's only
// codegen'd once per crate-graph so this pushes that to std rather than our codegen test crates.
//
// (See https://github.com/rust-lang/rust/pull/123244 for more info on why).
//
// If this is causing problems we can also modify those codegen tests to use a crate type like
// cdylib which doesn't export "Rust" symbols to downstream linkage units.
public var EMPTY_PANIC: FnCallback = ((beginPanic<String> as Any?) as FnCallback)

// Binary interface to the panic runtime that the standard library depends on.
//
// The standard library is tagged with `@file:needsPanicRuntime` (introduced in
// RFC 1513) to indicate that it requires some other crate tagged with
// `@file:panicRuntime` to exist somewhere. Each panic runtime is intended to
// implement these symbols (with the same signatures) so we can get matched up
// to them.
//
// One day this may look a little less ad-hoc with the compiler helping out to
// hook up these functions, but it is not this day!
public expect fun rustPanicCleanup(payload: MutPtr): RustAny

/**
 * `PanicPayload` lazily performs allocation only when needed (this avoids
 * allocations when using the "abort" panic runtime).
 */
public expect fun rustStartPanic(payload: PanicPayload): UInt

/**
 * This function is called by the panic runtime if FFI code catches a Rust
 * panic but doesn't rethrow it. We don't support this case since it messes
 * with our panic count.
 */
public fun rustDropPanic(): Nothing =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("Rust panics must be rethrown").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("Rust panics must be rethrown").toString() + ", aborting\n"
        }
        abort()
    }

/**
 * This function is called by the panic runtime if it catches an exception
 * object which does not correspond to a Rust panic.
 */
public fun rustForeignException(): Nothing =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("Rust cannot catch foreign exceptions").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("Rust cannot catch foreign exceptions").toString() + ", aborting\n"
        }
        abort()
    }

public sealed class Hook {
    public data object Default : Hook()
    public data class Custom(public var value: (PanicHookInfo) -> Unit) : Hook()

    public open fun intoBox(): (PanicHookInfo) -> Unit =
        when (this) {
            Hook.Default -> ::defaultHook
            is Hook.Custom -> {
                val (hook) = this
                hook
            }
        }
}

public var HOOK: RwLock<Hook> = RwLock.new(Hook.Default)

/**
 * Registers a custom panic hook, replacing the previously registered hook.
 *
 * The panic hook is invoked when a thread panics, but before the panic runtime
 * is invoked. As such, the hook will run with both the aborting and unwinding
 * runtimes.
 *
 * The default hook, which is registered at startup, prints a message to standard error and
 * generates a backtrace if requested. This behavior can be customized using the `setHook` function.
 * The current hook can be retrieved while reinstating the default hook with the [takeHook]
 * function.
 *
 * [takeHook]: ./fn.takeHook.html
 *
 * The hook is provided with a `PanicHookInfo` struct which contains information
 * about the origin of the panic, including the payload passed to `panic` and
 * the source code location from which the panic originated.
 *
 * The panic hook is a global resource.
 *
 * # Panics
 *
 * Panics if called from a panicking thread.
 *
 * # Examples
 *
 * The following will print "Custom panic hook":
 *
 * ```kotlin
 * import std.panic
 *
 * panic.setHook({
 *     println("Custom panic hook")
 * })
 * error("Normal panic")
 * ```
 */
public fun setHook(hook: (PanicHookInfo) -> Unit) {
    if (io.github.kotlinmania.rustlang.std.thread.functions.panicking()) {
        error("cannot modify the panic hook from a panicking thread")
    }
    // Drop the old hook after changing the hook to avoid deadlocking if its
    // destructor panics.
    (HOOK.replace(Hook.Custom(hook))).let { }
}

/**
 * Unregisters the current panic hook and returns it, registering the default hook
 * in its place.
 *
 * *See also the function [setHook].*
 *
 * [setHook]: ./fn.setHook.html
 *
 * If the default hook is registered it will be returned, but remain registered.
 *
 * # Panics
 *
 * Panics if called from a panicking thread.
 *
 * # Examples
 *
 * The following will print "Normal panic":
 *
 * ```kotlin
 * import std.panic
 *
 * panic.setHook({
 *     println("Custom panic hook")
 * })
 * panic.takeHook()
 * error("Normal panic")
 * ```
 */
public fun takeHook(): (PanicHookInfo) -> Unit {
    if (io.github.kotlinmania.rustlang.std.thread.functions.panicking()) {
        error("cannot modify the panic hook from a panicking thread")
    }
    return HOOK.replace(Hook.Default).intoBox()
}

/**
 * Atomic combination of [takeHook] and [setHook]. Use this to replace the panic handler with
 * a new panic handler that does something and then executes the old handler.
 *
 * [takeHook]: ./fn.takeHook.html
 * [setHook]: ./fn.setHook.html
 *
 * # Panics
 *
 * Panics if called from a panicking thread.
 *
 * # Examples
 *
 * The following will print the custom message, and then the normal output of panic.
 *
 * ```kotlin
 * import std.panic
 *
 * @feature(panicUpdateHook)
 * public fun doctestEntry() {
 *     // Equivalent to
 *     // let prev = panic.takeHook();
 *     // panic.setHook(Holder.new(move |info| {
 *     // println("...");
 *     // prev(info);
 *     // }));
 *     panic.updateHook({ prev, info ->
 *         println("Print custom message and execute panic handler as usual")
 *         prev(info)
 *     })
 *     error("Custom and then normal")
 * }
 * ```
 */
public fun updateHook(hookFn: ((PanicHookInfo) -> Unit, PanicHookInfo) -> Unit) {
    if (io.github.kotlinmania.rustlang.std.thread.functions.panicking()) {
        error("cannot modify the panic hook from a panicking thread")
    }
    var hook = HOOK.write()
    val prev = take(hook).intoBox()
    hook = Hook.Custom({ info -> hookFn(prev, info) })
}

/**
 * The default panic handler.
 */
public fun defaultHook(info: PanicHookInfo) {
    // If this is a double panic, make sure that we print a backtrace
    // for this panic. Otherwise only print it if logging is enabled.
    val backtrace =
        if (info.forceNoBacktrace()) {
            null
        } else if (getCount() >= 2) {
            BacktraceStyle.full()
        } else {
            getBacktraceStyle()
        }
    // The current implementation always returns `Some`.
    val location = info.location().unwrap()
    val msg = payloadAsStr(info.payload())
    val write =
        { err: Write ->
            // Use a lock to prevent mixed output in multithreading context.
            // Some platforms also require it when printing a backtrace, like `SymFromAddr` on Windows.
            var lock = lock()
            withCurrentName({ name ->
                val name1 = name.unwrapOr("<unnamed>")
                val tid = currentOsId()
                // Try to write the panic message to a buffer first to prevent other concurrent outputs
                // interleaving with it.
                var buffer = UByteArray(512) { 0u.toUByte() }
                var cursor = new(buffer)
                val writeMsg =
                    { dst: Write ->
                        // We add a newline to ensure the panic message appears at the start of a line.
                        dst.appendLine("\nthread '$name' ($tid) panicked at $location:\n$msg")
                    }
                if (writeMsg(cursor).isOk()) {
                    val pos = cursor.position().toInt()
                    err.writeAll(((buffer).sliceArray(0 until pos)))
                } else {
                    // The message did not fit into the buffer, write it directly instead.
                    writeMsg(err)
                }
            })
            var FIRST_PANIC: Atomic<Boolean> = AtomicBool.new(true)
            when (backtrace) {
                BacktraceStyle.Short -> {
                    (lock.print(err, PrintFmt.Short)).let { }
                }
                BacktraceStyle.Full -> {
                    (lock.print(err, PrintFmt.Full)).let { }
                }
                BacktraceStyle.Off -> {
                    if (FIRST_PANIC.swap(false, Ordering.Relaxed)) {
                        err.appendLine("note: run with `RUST_BACKTRACE=1` environment variable to display a backtrace")
                        if (false) {
                            err.appendLine("note: in Miri, you may have to set `MIRIFLAGS=-Zmiri-env-forward=RUST_BACKTRACE` for the environment variable to have an effect")
                        }
                    }
                }
                // If backtraces aren't supported or are forced-off, do nothing.
                null -> {}
            }
        }
    run {
        val matchVal1 = trySetOutputCapture(null)
        if (matchVal1 is Result.Ok<*> && matchVal1.value != null) {
            write(local.lock().unwrapOrElse({ e -> e.intoInner() }))
            trySetOutputCapture(local).ok()
        } else {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    write(out)
                }
            }
        }
    }
}

/**
 * Invoke a closure, capturing the cause of an unwinding panic if one occurs.
 */
public fun <R> catchUnwind(f: () -> R): Result<R, RustAny> = Result.Ok(f())

/**
 * Determines whether the current thread is unwinding because of panic.
 */
public fun panicking(): Boolean = (!countIsZero()).toUByte()

/**
 * Entry point of panics from the core crate (`panicImpl` lang item).
 */
public fun panicHandler(info: PanicInfo): Nothing {
    open class FormatStringPayload(
        public var inner: PanicMessage,
        public var string: String?,
    ) : PanicPayload {
        public open fun fill(): String {
            val inner = this.inner
            // Lazily, the first time this gets called, run the actual string formatting.
            return this.string.getOrInsertWith({
                var s = ""
                var fmt = Formatter.new(s, FormattingOptions.new())
                val err = fmt.append((inner).toString())
                s
            })
        }

        public open fun takeBox(): RustAny {
            // We do two allocations here, unfortunately. But (a) they're required with the current
            // scheme, and (b) we don't handle panic + OOM properly anyway (see comment in
            // beginPanic below).
            val contents = take(this.fill())
            return contents
        }

        public open fun get(): RustAny = this.fill()

        public open fun fmt(f: StringBuilder) {
            when (val matchVal = this.string) {
                null -> {
                    f.append((this.inner).toString())
                }
                else -> {
                    val s = matchVal
                    f.append(s)
                }
            }
        }
    }
    open class StaticStrPayload(
        public var value: String,
    ) : PanicPayload {
        public var field0: String
            get() = value
            set(v) {
                value = v
            }

        public open fun takeBox(): RustAny = this.value

        public open fun get(): RustAny = this.value

        public open fun asStr(): String? = this.value

        public open fun fmt(f: StringBuilder) {
            f.append(this.value)
        }
    }
    val loc = info.location().unwrap()
    // The current implementation always returns Some
    val msg = info.message()
    return rustEndShortBacktrace({
        when (val matchVal = msg.asStr()) {
            null -> {
                panicWithHook(FormatStringPayload(inner = msg, string = null), loc, info.canUnwind(), info.forceNoBacktrace())
            }
            else -> {
                val s = matchVal
                panicWithHook(StaticStrPayload(s), loc, info.canUnwind(), info.forceNoBacktrace())
            }
        }
    })
}

// lang item for CTFE panic support
// never inline unless panic=immediate-abort to avoid code
// bloat at the call sites as much as possible
// hooked by const-eval

/**
 * This is the entry point of panicking for the non-format-string variants of
 * panic() and assert(). In particular, this is the only entry point that supports
 * arbitrary payloads, not just format strings.
 */
public fun <M> beginPanic(msg: M): Nothing {
    open class Payload<A>(
        public var inner: A?,
    ) : PanicPayload {
        public open fun takeBox(): RustAny {
            // Note that this should be the only allocation performed in this code path. Currently
            // this means that panic() on OOM will invoke this code path, but then again we're not
            // really ready for panic on OOM anyway. If we do start doing this, then we should
            // propagate this allocation to be performed in the parent of this thread instead of the
            // thread that's panicking.
            return when (val matchVal = this.inner.also { this.inner = null }) {
                null -> abort()
                else -> {
                    val a = matchVal
                    RustAny.from(a)
                }
            }
        }

        public open fun get(): RustAny =
            when (val matchVal = this.inner) {
                null -> abort()
                else -> {
                    val a = matchVal
                    a
                }
            }

        public open fun fmt(f: StringBuilder) {
            when (val matchVal = this.inner) {
                null -> abort()
                else -> {
                    val a = matchVal
                    f.append(payloadAsStr(a))
                }
            }
        }
    }
    if (false) {
        abortImmediate()
    }
    val loc = Location.caller()
    return rustEndShortBacktrace({
        panicWithHook(Payload(inner = msg), loc, true, false)
    })
}

public fun payloadAsStr(payload: RustAny): String =
    when (val matchVal1 = payload.downcastRef<String>()) {
        null -> {
            when (val matchVal = payload.downcastRef<String>()) {
                null -> {
                    "Box<dyn Any>"
                }
                else -> {
                    val s = matchVal
                    s.asStr()
                }
            }
        }
        else -> {
            val s1 = matchVal1
            s1
        }
    }

/**
 * Central point for dispatching panics.
 *
 * Executes the primary logic for a panic, including checking for recursive
 * panics, panic hooks, and finally dispatching to the panic runtime to either
 * abort or unwind.
 */
public fun panicWithHook(payload: PanicPayload, location: Location, canUnwind: Boolean, forceNoBacktrace: Boolean): Nothing {
    val mustAbort = increase(true)
    // Check if we need to abort immediately.
    if (mustAbort != null) {
        val mustAbort1 = mustAbort
        when (mustAbort1) {
            MustAbort.PanicInHook -> {
                // Don't try to format the message in this case, perhaps that is causing the
                // recursive panics. However if the message is just a string, no user-defined
                // code is involved in printing it, so that is risk-free.
                val message: String = payload.asStr().unwrapOrDefault()
                run {
                    "panicked at $location:\n$message\nthread panicked while processing panic. aborting.\n"
                }
            }
            MustAbort.AlwaysAbort -> {
                run {
                    "aborting due to panic at $location:\n$payload\n"
                }
            }
        }
        abort()
    }
    run {
        val matchVal = HOOK.read()
        when {
            // Some platforms (like wasm) know that printing to stderr won't ever actually
            // print anything, and if that's the case we can skip the default
            // hook. Since string formatting happens lazily when calling `payload`
            // methods, this means we avoid formatting the string at all!
            // (The panic runtime might still call `payload.takeBox()` though and trigger
            // formatting.)
            matchVal == Hook.Default && panicOutput() == null -> {}
            matchVal == Hook.Default -> {
                defaultHook(PanicHookInfo.new(location, payload.get(), canUnwind, forceNoBacktrace))
            }
            else -> {
                val (hook) = matchVal
                hook(PanicHookInfo.new(location, payload.get(), canUnwind, forceNoBacktrace))
            }
        }
    }
    // Indicate that we have finished executing the panic hook. After this point
    // it is fine if there is a panic while executing destructors, as long as it
    // it contained within a `catchUnwind`.
    finishedPanicHook()
    if (!canUnwind) {
        when (val matchVal1 = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal1
                Write.writeFmt(out, "thread caused non-unwinding panic. aborting.\n")
            }
        }
        run {
            "thread caused non-unwinding panic. aborting.\n"
        }
        abort()
    }
    return rustPanic(payload)
}

/**
 * This is the entry point for `resumeUnwind`.
 * It just forwards the payload to the panic runtime.
 */
public fun resumeUnwind(payload: RustAny): Nothing {
    open class RewrapBox(
        public var value: RustAny,
    ) : PanicPayload {
        public var field0: RustAny
            get() = value
            set(v) {
                value = v
            }

        public open fun takeBox(): RustAny = replace(this.value, Box.new())

        public open fun get(): RustAny = this.value

        public open fun fmt(f: StringBuilder) {
            f.append(payloadAsStr(this.value))
        }
    }
    when (val matchVal = increase(false)) {
        null -> {}
        else -> {
            val mustAbort = matchVal
            when (mustAbort) {
                MustAbort.PanicInHook -> {
                    run {
                        "thread panicked while processing panic. aborting.\n"
                    }
                }
                MustAbort.AlwaysAbort -> {
                    run {
                        "aborting due to panic\n"
                    }
                }
            }
            abort()
        }
    }
    return rustPanic(RewrapBox(payload))
}

/**
 * A function with a fixed suffix (through `rustcStdInternalSymbol`)
 * on which to slap yer breakpoints.
 */
public fun rustPanic(msg: PanicPayload): Nothing {
    val code = rustStartPanic(msg)
    return run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("failed to initiate panic, error $code").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("failed to initiate panic, error $code").toString() + ", aborting\n"
        }
        abort()
    }
}

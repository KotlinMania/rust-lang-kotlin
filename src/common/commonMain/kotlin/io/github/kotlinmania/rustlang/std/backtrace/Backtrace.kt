// port-lint: source backtrace.rs
package io.github.kotlinmania.rustlang.std.backtrace

import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.std.env.currentDir
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.lazylock.LazyLock
import io.github.kotlinmania.rustlang.std.sys.backtrace.lock
import io.github.kotlinmania.rustlang.std.sys.backtrace.outputFilename
import io.github.kotlinmania.rustlang.std.sys.backtrace.setImageBase
import io.github.kotlinmania.rustlang.std.sys.env.common.Env

// Support for capturing a stack backtrace of an OS thread
//
// This module contains the support necessary to capture a stack backtrace of a
// running OS thread from the OS thread itself. The `Backtrace` type supports
// capturing a stack trace via the `Backtrace.capture` and
// `Backtrace.forceCapture` functions.
//
// A backtrace is typically quite handy to attach to errors (e.g. types
// implementing `std.error.Error`) to get a causal chain of where an error
// was generated.
//
// ## Accuracy
//
// Backtraces are attempted to be as accurate as possible, but no guarantees
// are provided about the exact accuracy of a backtrace. Instruction pointers,
// symbol names, filenames, line numbers, etc, may all be incorrect when
// reported. Accuracy is attempted on a best-effort basis, however, any bug
// reports are always welcome to indicate areas of improvement!
//
// For most platforms a backtrace with a filename/line number requires that
// programs be compiled with debug information. Without debug information
// filenames/line numbers will not be reported.
//
// ## Platform support
//
// Not all platforms that std compiles for support capturing backtraces. Some
// platforms simply do nothing when capturing a backtrace. To check whether the
// platform supports capturing backtraces you can consult the `BacktraceStatus`
// enum as a result of `Backtrace.status`.
//
// Like above with accuracy platform support is done on a best effort basis.
// Sometimes libraries might not be available at runtime or something may go
// wrong which would cause a backtrace to not be captured. Please feel free to
// report issues with platforms where a backtrace cannot be captured though!
//
// ## Environment Variables
//
// The `Backtrace.capture` function might not actually capture a backtrace by
// default. Its behavior is governed by two environment variables:
//
// * `RUST_LIB_BACKTRACE` - if this is set to `0` then `Backtrace.capture`
//   will never capture a backtrace. Any other value set will enable
//   `Backtrace.capture`.
//
// * `RUST_BACKTRACE` - if `RUST_LIB_BACKTRACE` is not set, then this variable
//   is consulted with the same rules of `RUST_LIB_BACKTRACE`.
//
// * If neither of the above env vars are set, then `Backtrace.capture` will
//   be disabled.
//
// Capturing a backtrace can be a quite expensive runtime operation, so the
// environment variables allow either forcibly disabling this runtime
// performance hit or allow selectively enabling it in some programs.
//
// Note that the `Backtrace.forceCapture` function can be used to ignore
// these environment variables. Also note that the state of environment
// variables is cached once the first backtrace is created, so altering
// `RUST_LIB_BACKTRACE` or `RUST_BACKTRACE` at runtime might not actually change
// how backtraces are captured.

public fun interface FnCallback {
    public operator fun invoke(): Backtrace
}

/**
 * A captured OS thread stack backtrace.
 *
 * This type represents a stack backtrace for an OS thread captured at a
 * previous point in time. In some instances the `Backtrace` type may
 * internally be empty due to configuration. For more information see
 * `Backtrace.capture`.
 */
public open class Backtrace(
    public var inner: Inner,
) {
    public open fun fmt(fmt: StringBuilder) {
        val capture =
            when (val matchVal = this.inner) {
                Inner.Unsupported -> { return fmt.append("<unsupported>") }
                Inner.Disabled -> { return fmt.append("<disabled>") }
                is Inner.Captured -> {
                    val (c) = matchVal
                    c
                }
            }
        val frames = capture.frames[capture.actualStart until capture.frames.size]
        fmt.append("Backtrace ")
        val dbg = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }
        for (frame in frames) {
            if (frame.frame.ip().isNull()) {
                continue
            }
            dbg.entries(frame.symbols)
        }
        dbg.finish()
    }

    /**
     * Returns the status of this backtrace, indicating whether this backtrace
     * request was unsupported, disabled, or a stack trace was actually
     * captured.
     */
    public open fun status(): BacktraceStatus =
        when (val matchVal = this.inner) {
            Inner.Unsupported -> BacktraceStatus.Unsupported
            Inner.Disabled -> BacktraceStatus.Disabled
            is Inner.Captured -> BacktraceStatus.Captured
        }

    /**
     * Returns an iterator over the backtrace frames.
     */
    public open fun frames(): List<BacktraceFrame> =
        when (val matchVal = this.inner) {
            is Inner.Captured -> {
                val (c) = matchVal
                c.frames
            }
            else -> {
                listOf()
            }
        }

    public open fun fmtDisplay(fmt: StringBuilder) {
        val capture =
            when (val matchVal = this.inner) {
                Inner.Unsupported -> { return fmt.append("unsupported backtrace") }
                Inner.Disabled -> { return fmt.append("disabled backtrace") }
                is Inner.Captured -> {
                    val (c) = matchVal
                    c
                }
            }
        val full = fmt.alternate()
        val (frames, style) =
            if (full) {
                Pair(capture.frames, PrintFmt.Full)
            } else {
                Pair(capture.frames[capture.actualStart until capture.frames.size], PrintFmt.Short)
            }
        // When printing paths we try to strip the cwd if it exists, otherwise
        // we just print the path as-is. Note that we also only do this for the
        // short format, because if it's full we presumably want to print
        // everything.
        val cwd = currentDir()
        var printPath =
            { fmt1: StringBuilder, path: BytesOrWideString ->
                outputFilename(fmt1, path, style, cwd.asRef().ok())
            }
        val f = BacktraceFmt.new(fmt, style, printPath)
        f.addContext()
        for (frame in frames) {
            if (frame.symbols.isEmpty()) {
                f.frame().printRaw(frame.frame.ip(), null, null, null)
            } else {
                for (symbol in frame.symbols.iter()) {
                    f.frame().printRawWithColumn(frame.frame.ip(), symbol.name.asRef().map({ b -> SymbolName.new(b) }), symbol.filename.asRef().map({ b -> when (b) {
                        is BytesOrWide.Bytes -> {
                            val (w) = b
                            BytesOrWideString.Bytes(w)
                        }
                        is BytesOrWide.Wide -> {
                            val (w1) = b
                            BytesOrWideString.Wide(w1)
                        }
                    } }), symbol.lineno, symbol.colno)
                }
            }
        }
        f.finish()
    }

    public companion object {
        /**
         * Returns whether backtrace captures are enabled through environment
         * variables.
         */
        public fun enabled(): Boolean {
            // Cache the result of reading the environment variables to make
            // backtrace captures speedy, because otherwise reading environment
            // variables every time can be somewhat slow.
            val ENABLED: Atomic<UByte>  = AtomicU8.new(0)
            when (ENABLED.load(Relaxed)) {
                0 -> {}
                1 -> { return false }
                else -> { return true }
            }
            val enabled =
                run {
                    val matchVal = Env.variable("RUST_LIB_BACKTRACE")
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val s = matchVal.value
                        s != "0"
                    }; is Result.Err<*, *> -> {
                        run {
                            val matchVal1 = Env.variable("RUST_BACKTRACE")
                            when (matchVal1) { is Result.Ok<*, *> -> {
                                val s1 = matchVal1.value
                                s1 != "0"
                            }; is Result.Err<*, *> -> {
                                false
                            } }
                        }
                    } }
                }
            ENABLED.store(enabled.toUByte() + 1u, Relaxed)
            return enabled
        }

        // want to make sure there's a frame here to remove

        /**
         * Captures a stack backtrace of the current thread.
         *
         * This function will capture a stack backtrace of the current OS thread of
         * execution, returning a `Backtrace` type which can be later used to print
         * the entire stack trace or render it to a string.
         *
         * This function will be a noop if the `RUST_BACKTRACE` or
         * `RUST_LIB_BACKTRACE` backtrace variables are both not set. If either
         * environment variable is set and enabled then this function will actually
         * capture a backtrace. Capturing a backtrace can be both memory intensive
         * and slow, so these environment variables allow liberally using
         * `Backtrace.capture` and only incurring a slowdown when the environment
         * variables are set.
         *
         * To forcibly capture a backtrace regardless of environment variables, use
         * the `Backtrace.forceCapture` function.
         */
        public fun capture(): Backtrace {
            if (!Backtrace.enabled()) {
                return Backtrace(inner = Inner.Disabled)
            }
            return Backtrace.create((((Backtrace::capture as Any?) as FnCallback)).toInt())
        }

        // want to make sure there's a frame here to remove

        /**
         * Forcibly captures a full backtrace, regardless of environment variable
         * configuration.
         *
         * This function behaves the same as `capture` except that it ignores the
         * values of the `RUST_BACKTRACE` and `RUST_LIB_BACKTRACE` environment
         * variables, always capturing a backtrace.
         *
         * Note that capturing a backtrace can be an expensive operation on some
         * platforms, so this should be used with caution in performance-sensitive
         * parts of code.
         */
        public fun forceCapture(): Backtrace = Backtrace.create((((Backtrace::forceCapture as Any?) as FnCallback)).toInt())

        /**
         * Forcibly captures a disabled backtrace, regardless of environment
         * variable configuration.
         */
        public fun disabled(): Backtrace = Backtrace(inner = Inner.Disabled)

        // Capture a backtrace which start just before the function addressed by
        // `ip`
        public fun create(ip: Int): Backtrace {
            var frames = mutableListOf<BacktraceFrame>()
            var actualStart = null
            setImageBase()
            run {
                traceUnsynchronized({ frame ->
                    frames.add(BacktraceFrame(frame = RawFrame.Actual(frame.clone()), symbols = mutableListOf()))
                    if (frame.symbolAddress().addr() == ip && actualStart == null) {
                        actualStart = frames.size
                    }
                    true
                })
            }
            // If no frames came out assume that this is an unsupported platform
            // since `backtrace` doesn't provide a way of learning this right now,
            // and this should be a good enough approximation.
            val inner =
                if (frames.isEmpty()) {
                    Inner.Unsupported
                } else {
                    Inner.Captured(LazyLock.new(lazyResolve(Capture(actualStart = (actualStart ?: 0), frames = frames))))
                }
            return Backtrace(inner = inner)
        }
    }
}

/**
 * The current status of a backtrace, indicating whether it was captured or
 * whether it is empty for some other reason.
 */
public sealed class BacktraceStatus {
    /**
     * Capturing a backtrace is not supported, likely because it's not
     * implemented for the current platform.
     */
    public data object Unsupported : BacktraceStatus()
    /**
     * Capturing a backtrace has been disabled through either the
     * `RUST_LIB_BACKTRACE` or `RUST_BACKTRACE` environment variables.
     */
    public data object Disabled : BacktraceStatus()
    /**
     * A backtrace has been captured and the `Backtrace` should print
     * reasonable information when rendered.
     */
    public data object Captured : BacktraceStatus()

    public fun rustEquals(other: BacktraceStatus): Boolean = this == other
}

public sealed class Inner {
    public data object Unsupported : Inner()
    public data object Disabled : Inner()
    public class Captured internal constructor(valueCtorArg: LazyLock<Capture, LazyResolve>) : Inner() {
        public var value: LazyLock<Capture, LazyResolve> = valueCtorArg
            internal set

        public operator fun component1(): LazyLock<Capture, LazyResolve> = this.value

        public fun copy(value: LazyLock<Capture, LazyResolve> = this.value): Captured = Captured(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Captured) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: LazyLock<Capture, LazyResolve>,
            ): Captured = Captured(
                valueCtorArg = value,
            )
        }
    }
}

public open class Capture(
    public var actualStart: Int,
    public var frames: List<BacktraceFrame>,
)

public fun assertSendSync() {
    fun <T> assert() {}
    assert<Backtrace>()
}

/**
 * A single frame of a backtrace.
 */
public open class BacktraceFrame(
    public var frame: RawFrame,
    public var symbols: List<BacktraceSymbol>,
) {
    public open fun fmt(fmt: StringBuilder) {
        val dbg = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }
        dbg.entries(this.symbols)
        dbg.finish()
    }
}

public sealed class RawFrame {
    public data class Actual(public var value: Frame) : RawFrame()

    public open fun ip(): MutPtr =
        when (this) {
            is RawFrame.Actual -> {
                val (frame) = this
                frame.ip()
            }
        }
}

public open class BacktraceSymbol(
    public var name: List<UByte>?,
    public var filename: BytesOrWide?,
    public var lineno: UInt?,
    public var colno: UInt?,
) {
    public open fun fmt(fmt: StringBuilder) {
        // Note: improve formatting: https://github.com/rust-lang/rust/issues/65280
        // Note: Also, include column numbers into the debug format as Display already has them.
        // Until there are stable per-frame accessors, the format shouldn't be changed:
        // https://github.com/rust-lang/rust/issues/65280#issuecomment-638966585
        fmt.append("{ ")
        when (val matchVal = this.name?.let({ b -> SymbolName.new(b) })) {
            null -> {
                fmt.append("fn: <unknown>")
            }
            else -> {
                val fnName = matchVal
                fmt.append("fn: \"$fnName\"")
            }
        }
        when (val matchVal1 = this.filename) {
            null -> {}
            else -> {
                val fname = matchVal1
                fmt.append(", file: \"$fname\"")
            }
        }
        when (val matchVal2 = this.lineno) {
            null -> {}
            else -> {
                val line = matchVal2
                fmt.append(", line: $line")
            }
        }
        fmt.append(" }")
    }
}

public sealed class BytesOrWide {
    public data class Bytes(public var value: List<UByte>) : BytesOrWide()
    public data class Wide(public var value: List<UShort>) : BytesOrWide()

    public open fun fmt(fmt: StringBuilder) {
        outputFilename(fmt, when (this) {
            is BytesOrWide.Bytes -> {
                val (w) = this
                BytesOrWideString.Bytes(w)
            }
            is BytesOrWide.Wide -> {
                val (w1) = this
                BytesOrWideString.Wide(w1)
            }
        }, PrintFmt.Short, currentDir().asRef().ok())
    }
}

public typealias LazyResolve = () -> Capture

public fun lazyResolve(capture: Capture): LazyResolve =
    {
        // Use the global backtrace lock to synchronize this as it's a
        // requirement of the `backtrace` crate, and then actually resolve
        // everything.
        val lock = lock()
        for (frame in ArrayDeque(capture.frames)) {
            val symbols = frame.symbols
            val frame2 =
                when (val matchVal = frame.frame) {
                    is RawFrame.Actual -> {
                        val (frame1) = matchVal
                        frame1
                    }
                }
            run {
                resolveFrameUnsynchronized(frame2, { symbol ->
                    symbols.push(BacktraceSymbol(name = symbol.name().map({ m -> (m.encodeToByteArray().map { it.toUByte() }).toMutableList() }), filename = symbol.filenameRaw().map({ b -> when (b) {
                        is BytesOrWideString.Bytes -> {
                            val (b1) = b
                            BytesOrWide.Bytes(b1.toOwned())
                        }
                        is BytesOrWideString.Wide -> {
                            val (b2) = b
                            BytesOrWide.Wide(b2.toOwned())
                        }
                    } }), lineno = symbol.lineno(), colno = symbol.colno()))
                })
            }
        }
        capture
    }

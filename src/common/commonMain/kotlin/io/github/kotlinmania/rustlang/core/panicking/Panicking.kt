// rust-kotlin: manual
// port-lint: source panicking.rs
package io.github.kotlinmania.rustlang.core.panicking

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.result.Result

// Panic support for core
//
// In core, panicking is always done with a message, resulting in a `core.panic.PanicInfo`
// containing a `fmt.Arguments`. In std, however, panicking can be done with panicAny, which
// throws a `Holder<dynamic Any>` containing any type of value. Because of this,
// `std.panic.PanicHookInfo` is a different type, which contains a `dynamic Any` instead of a
// `fmt.Arguments`. std's panic handler will convert the `fmt.Arguments` to a `dynamic Any`
// containing either a `String` or `String` containing the formatted message.
//
// The core library cannot define any panic handler, but it can invoke it.
// This means that the functions inside of core are allowed to panic, but to be
// useful an upstream crate must define panicking for core to use. The current
// interface for panicking is:
//
// ```kotlin
// fun panicImpl(pi: PanicInfo): Nothing {
//     while (true) {}
// }
// ```
//
// This module contains a few other panicking functions, but these are just the
// necessary lang items for the compiler. All panics are funneled through this
// one function. The actual symbol is declared through the `@panicHandler` attribute.

// First we define the two main entry points that all panics go through.
// In the end both are just convenience wrappers around `panicImpl`.
// If panic=immediate-abort, inline the abort call,
// otherwise avoid inlining because of it is cold path.
// needed for const-evaluated panics
// hooked by const-eval
// must follow stable const rules since it is exposed to stable

/**
 * The entry point for panicking with a formatted message.
 *
 * This is designed to reduce the amount of code required at the call
 * site as much as possible (so that `panic()` has as low an impact
 * on (e.g.) the inlining of other functions as possible), by moving
 * the actual formatting into this shared place.
 */
public fun panicFmt(msg: String): Nothing = error(msg)

// This attribute has the key side-effect that if the panic handler ignores `canUnwind`
// and unwinds anyway, we will hit the "unwinding out of nounwind function" guard,
// which causes a "panic in a function that cannot unwind".
// must follow stable const rules since it is exposed to stable

/**
 * Like `panicFmt`, but for non-unwinding panics.
 *
 * Has to be a separate function so that it can carry the `rustcNounwind` attribute.
 */
public fun panicNounwindFmt(msg: String, forceNoBacktrace: Boolean = false): Nothing = error(msg)

// Next we define a bunch of higher-level wrappers that all bottom out in the two core functions
// above.
// Never inline unless panic=immediate-abort to avoid code
// bloat at the call sites as much as possible.
// must follow stable const rules since it is exposed to stable
// used by lints and miri for panics

/**
 * The underlying implementation of core's `panic` macro when no formatting is used.
 */
public fun panic(expr: String): Nothing {
    // Use Arguments.fromStr instead of formatArgs("(expr)") to potentially
    // reduce size overhead. The formatArgs macro uses str's Display trait to
    // write expr, which calls Formatter.pad, which must accommodate string
    // truncation and padding (even though none is used here). Using
    // Arguments.fromStr may allow the compiler to omit Formatter.pad from the
    // output binary, saving up to a few kilobytes.
    // However, this optimization only works for `static` strings: `fromStr` also makes this
    // message return `Some` from `Arguments.asStr`, which means it can become part of the panic
    // payload without any allocation or copying. Shorter-lived strings would become invalid as
    // stack frames get popped during unwinding, and couldn't be directly referenced from the
    // payload.
    panicFmt(expr)
}

// Separated panic constants list for async drop feature
// (May be joined when the corresponding lang items will be in the bootstrap)

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAddOverflow(): Nothing {
    panicFmt("attempt to add with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstSubOverflow(): Nothing {
    panicFmt("attempt to subtract with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstMulOverflow(): Nothing {
    panicFmt("attempt to multiply with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstDivOverflow(): Nothing {
    panicFmt("attempt to divide with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstRemOverflow(): Nothing {
    panicFmt("attempt to calculate the remainder with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstNegOverflow(): Nothing {
    panicFmt("attempt to negate with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstShrOverflow(): Nothing {
    panicFmt("attempt to shift right with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstShlOverflow(): Nothing {
    panicFmt("attempt to shift left with overflow")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstDivByZero(): Nothing {
    panicFmt("attempt to divide by zero")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstRemByZero(): Nothing {
    panicFmt("attempt to calculate the remainder with a divisor of zero")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstCoroutineResumed(): Nothing {
    panicFmt("coroutine resumed after completion")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncFnResumed(): Nothing {
    panicFmt("`async fn` resumed after completion")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncGenFnResumed(): Nothing {
    panicFmt("`async gen fn` resumed after completion")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstGenFnNone(): Nothing {
    panicFmt("`gen fn` should just keep returning `None` after completion")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstCoroutineResumedPanic(): Nothing {
    panicFmt("coroutine resumed after panicking")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncFnResumedPanic(): Nothing {
    panicFmt("`async fn` resumed after panicking")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncGenFnResumedPanic(): Nothing {
    panicFmt("`async gen fn` resumed after panicking")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstGenFnNonePanic(): Nothing {
    panicFmt("`gen fn` should just keep returning `None` after panicking")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstCoroutineResumedDrop(): Nothing {
    panicFmt("coroutine resumed after async drop")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncFnResumedDrop(): Nothing {
    panicFmt("`async fn` resumed after async drop")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstAsyncGenFnResumedDrop(): Nothing {
    panicFmt("`async gen fn` resumed after async drop")
}

/**
 * This is a panic called with a message that's a result of a MIR-produced Assert.
 */
public fun panicConstGenFnNoneDrop(): Nothing {
    panicFmt("`gen fn` resumed after async drop")
}

// needed by codegen for non-unwinding panics
// must follow stable const rules since it is exposed to stable

/**
 * Like `panic`, but without unwinding and trackCaller to reduce the impact on codesize on the caller.
 * If you want `@trackCaller` for nicer errors, call `panicNounwindFmt` directly.
 */
public fun panicNounwind(expr: String): Nothing {
    panicNounwindFmt(expr, false)
}

/**
 * Like `panicNounwind`, but also inhibits showing a backtrace.
 */
public fun panicNounwindNobacktrace(expr: String): Nothing {
    panicNounwindFmt(expr, true)
}

// needed for `non - fmt - panics` lint
public fun <T> unreachableDisplay(x: T): Nothing {
    panicFmt("internal error: entered unreachable code: $x")
}

// must follow stable const rules since it is exposed to stable

/**
 * This exists solely for the 2015 edition `panic` macro to trigger
 * a lint on `panic(myStrVariable);`.
 */
public fun panicStr2015(expr: String): Nothing {
    panicDisplay(expr)
}

// needed for const-evaluated panics
// hooked by const-eval
// must follow stable const rules since it is exposed to stable
public fun <T> panicDisplay(x: T): Nothing {
    panicFmt("$x")
}

// needed by codegen for panic on OOB array/slice access
public fun panicBoundsCheck(index: Int, len: Int): Nothing {
    if (false) {
        kmAbort()
    }
    error("index out of bounds: the len is $len but the index is $index")
}

// needed by codegen for panic on misaligned pointer deref
// `CheckAlignment` MIR pass requires this function to never unwind
public fun panicMisalignedPointerDereference(required: Int, found: Int): Nothing {
    if (false) {
        kmAbort()
    }
    panicNounwindFmt("misaligned pointer dereference: address must be a multiple of $required but is $found", false)
}

// needed by codegen for panic on null pointer deref
// `CheckNull` MIR pass requires this function to never unwind
public fun panicNullPointerDereference(): Nothing {
    if (false) {
        kmAbort()
    }
    panicNounwindFmt("null pointer dereference occurred", false)
}

// needed by codegen for panic on null reference formation
// `CheckNull` MIR pass requires this function to never unwind
public fun panicNullReferenceConstructed(): Nothing {
    if (false) {
        kmAbort()
    }
    panicNounwindFmt("null reference produced", false)
}

// needed by codegen for panic on invalid enum construction.
// `CheckEnums` MIR pass requires this function to never unwind
public fun panicInvalidEnumConstruction(source: U128): Nothing {
    if (false) {
        kmAbort()
    }
    panicNounwindFmt("trying to construct an enum from an invalid value $source", false)
}

// needed by codegen for panic in nounwind function

/**
 * Panics because we cannot unwind out of a function.
 *
 * This is a separate function to avoid the codesize impact of each crate containing the string to
 * pass to `panicNounwind`.
 *
 * This function is called directly by the codegen backend, and must not have
 * any extra arguments (including those synthesized by trackCaller).
 */
public fun panicCannotUnwind(): Nothing {
    // Keep the text in sync with `UnwindTerminateReason.asStr` in `rustcMiddle`.
    panicNounwind("panic in a function that cannot unwind")
}

// needed by codegen for panic in nounwind function

/**
 * Panics because we are unwinding out of a destructor during cleanup.
 *
 * This is a separate function to avoid the codesize impact of each crate containing the string to
 * pass to `panicNounwind`.
 *
 * This function is called directly by the codegen backend, and must not have
 * any extra arguments (including those synthesized by trackCaller).
 */
public fun panicInCleanup(): Nothing {
    // Keep the text in sync with `UnwindTerminateReason.asStr` in `rustcMiddle`.
    panicNounwindNobacktrace("panic in a destructor during cleanup")
}

// needed by const-eval machine to replace calls to `panicFmt` lang item
// must follow stable const rules since it is exposed to stable

/**
 * This function is used instead of panicFmt in const eval.
 */
public fun constPanicFmt(msg: String): Nothing = panicDisplay(msg)

public sealed class AssertKind {
    public data object Eq : AssertKind()
    public data object Ne : AssertKind()
    public data object Match : AssertKind()

    private fun kmDiscriminant(): Int =
        when (this) {
            Eq -> 0
            Ne -> 1
            Match -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

/**
 * Internal function for `assertEq` and `assertNe` macros
 */
public fun <T, U> assertFailed(kind: AssertKind, left: T, right: U, args: Any? = null): Nothing = assertFailedInner(kind, left, right, args)

/**
 * Internal function for `assertMatch`
 */
public fun <T> assertMatchesFailed(left: T, right: String, args: Any? = null): Nothing {
    // The pattern is a string so it can be displayed directly.
    open class Pattern(
        public var value: String,
    ) {
        public var field0: String
            get() = value
            set(v) {
                value = v
            }

        public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
            run {
                f.append(this.value)
                Result.Ok<Unit, Unit>(Unit)
            }
    }
    assertFailedInner(AssertKind.Match, left, Pattern(right), args)
}

/**
 * Non-generic version of the above functions, to avoid code bloat.
 */
public fun assertFailedInner(kind: AssertKind, left: Any?, right: Any?, args: Any? = null): Nothing {
    val op =
        when (kind) {
            AssertKind.Eq -> "=="
            AssertKind.Ne -> "!="
            AssertKind.Match -> "matches"
        }
    val detail =
        when (args) {
            null, is Option.None<*> -> null
            is Option.Some<*> -> args.value
            else -> args
        }
    when (detail) {
        null -> error("assertion `left $op right` failed\n  left: $left\n right: $right")
        else -> error("assertion `left $op right` failed: $detail\n  left: $left\n right: $right")
    }
}

// panic() without args expands to this, to generate slightly less code
public fun panicDefaultMsg(): Nothing = panic("explicit panic")

// unreachable() without args expands to this, to generate slightly less code
public fun unreachableDefaultMsg(): Nothing = panic("internal error: entered unreachable code")

// unsupportedBranch() without args expands to this, to generate slightly less code
public fun unimplementedDefaultMsg(): Nothing = panic("not implemented")

// pendingTask() without args expands to this, to generate slightly less code
public fun todoDefaultMsg(): Nothing = panic("not yet implemented")

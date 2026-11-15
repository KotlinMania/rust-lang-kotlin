// port-lint: source process.rs
package io.github.kotlinmania.rustlang.core.process

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort

// A module for working with processes.
//
// Most process-related functionality requires std, but [crash] is available
// on all targets.

// even without panics, this helps for Miri backtraces

/**
 * Terminates the process in a violent fashion.
 *
 * The function will never return and will immediately terminate the current
 * process in a platform specific "abnormal" manner. As a consequence,
 * no destructors on the current stack or any other thread's stack
 * will be run, Rust IO buffers (eg, from `BufWriter`) will not be flushed,
 * and C stdio buffers will not be flushed.
 *
 * Unlike [`abort`](../../std/process/fn.abort.html), `crash` does not attempt
 * to match C `abort()` or otherwise perform a "clean" abort. Instead, it emits
 * code that will crash the process with as little overhead as possible, such
 * as a "halt and catch fire" style instruction. You should generally prefer
 * using `abort` instead except where the absolute minimum overhead is
 * required.
 *
 * # Platform-specific behavior
 *
 * `crash` lowers to a trap instruction on *most* architectures; on some
 * architectures it simply lowers to call the unmangled `abort` function. The
 * exact behavior is architecture- and system-dependent.
 *
 * On bare-metal (no OS) systems the trap instruction usually causes a
 * *hardware* exception to be raised in a *synchronous* fashion; hardware
 * exceptions have nothing to do with C++ exceptions and are closer in
 * semantics to POSIX signals.
 *
 * On hosted applications (applications running under an OS), the trap
 * instruction *usually* terminates the whole process with an exit code that
 * corresponds to `SIGILL` or equivalent, *unless* this signal is handled.
 * Other signals such as `SIGABRT`, `SIGTRAP`, `SIGSEGV`, and `SIGBUS` may be
 * produced instead, depending on specifics. This is not an exhaustive list.
 *
 * # Examples
 *
 * ```kotlin
 * import core.process
 *
 * @feature(abortImmediate)
 * public fun doctestEntry() {
 *     fun main() {
 *         println("aborting")
 *         crash()
 *         // execution never gets here
 *     }
 * }
 * ```
 *
 * The `crash` function terminates the process, so the destructor will not get
 * run on the example below:
 *
 * ```kotlin
 * import core.process
 *
 * @feature(abortImmediate)
 * public fun doctestEntry() {
 *     open class HasDrop {
 *         public open fun drop() {
 *             println("This will never be printed!")
 *         }
 *     }
 *     fun main() {
 *         val x = HasDrop()
 *         crash()
 *         // the destructor implemented for HasDrop will never get run
 *     }
 * }
 * ```
 */
public fun crash(): Nothing = kmAbort()

// NOTE: this is meant to discuss alternatives to `arch.breakpoint`. We may or may not
// actually want to stabilize both of them.

/**
 * Triggers a debugger breakpoint, pausing execution if possible.
 *
 * This signals the environment to pause execution if a debugger is attached, and pass control to
 * the debugger. Execution may then be aborted or continued by the user controlling the debugger.
 *
 * If possible, this will produce an instruction sequence that allows a debugger to resume *after*
 * the breakpoint, rather than resuming *at* the breakpoint; however, the exact behavior is
 * target-specific and debugger-specific, and not guaranteed.
 *
 * If no debugger is attached, this will typically abort the process but may also continue
 * execution.
 */
public fun resumableBreakpoint() {
    io.github.kotlinmania.rustlang.core.intrinsics
        .resumableBreakpoint()
}

// port-lint: source panic.rs
package io.github.kotlinmania.rustlang.core.panic

// Panic support in core.

// this attribute does all the actual work here

/**
 * Invokes a closure, aborting if the closure unwinds.
 *
 * When compiled with aborting panics, this function is effectively a no-op.
 * With unwinding panics, an unwind that would leave the closure results in another call into the
 * panic handler / panic hook followed by a process abort. (Locals in the closure get dropped as
 * usual.) Unwinds that get caught inside the closure still behave as normal.
 *
 * # Notes
 *
 * Instead of using this function, code should attempt to support unwinding.
 * Implementing [Drop] allows you to restore invariants uniformly in both
 * return and unwind paths.
 *
 * If an unwind can lead to logical issues but not soundness issues, you
 * should allow the unwind. Opting out of [UnwindSafe] indicates to your
 * consumers that they need to consider correctness in the face of unwinds.
 *
 * If an unwind would be unsound, then this function should be used in order
 * to prevent unwinds. However, note that `extern "C" fun` will automatically
 * convert unwinds to aborts, so using this function isn't necessary for FFI.
 */
public fun <R> abortOnUnwind(f: () -> R): R = f()

// port-lint: source arch.rs
package io.github.kotlinmania.rustlang.core.arch

// When adding more items above, also add cases to the test in `tests / assembly - llvm / intrinsics.rs`.
// When stabilizing this, update the comment on `core.intrinsics.breakpoint`.
// NOTE: there is an alternative in `process.resumableBreakpoint`. We may or may not
// actually want to stabilize both of them.

/**
 * Compiles to a target-specific software breakpoint instruction if possible,
 * falling back to an alternative trap or an abort.
 *
 * This will typically abort the program. It may result in a core dump, and/or the system logging
 * debug information. Additional target-specific capabilities may be possible depending on
 * debuggers or other tooling; in particular, a debugger may be able to resume execution.
 *
 * If possible, this will produce an instruction sequence that allows a debugger to resume *after*
 * the breakpoint, rather than resuming *at* the breakpoint; however, the exact behavior is
 * target-specific and debugger-specific, and not guaranteed.
 *
 * If the target platform does not have any kind of debug breakpoint instruction, this may compile
 * to a trapping instruction (e.g. an undefined instruction) instead, or to some other form of
 * target-specific abort that may or may not support convenient resumption.
 *
 * The precise behavior is not guaranteed, it depends on the architecture and operating system.
 * Not all architectures guarantee that a breakpoint instruction interrupts execution in the absence
 * of a debugger, and not all operating systems and execution environments guarantee that such an
 * interrupt aborts the current process. On operating systems where the behavior of the interrupt
 * can be overwritten by the application (e.g. via a SIGTRAP signal handler), the application
 * *should* make sure to still abort the process. However, code not fully in control of that
 * application state (e.g. signal handlers) cannot always rely on that for soundness.
 * If you do rely on this, such that your code's soundness requires applications to not continue
 * from your breakpoints, please document that for your users.
 *
 * The precise instruction is guaranteed only on the following targets:
 * - On x86 targets, this produces an `int3` instruction.
 * - On aarch64 targets, this produces a `brk # 0xf000` instruction.
 */
public fun breakpoint() {}

// port-lint: source os/fd/stdio.rs
package io.github.kotlinmania.rustlang.std.os.fd

import io.github.kotlinmania.rustlang.os.fd.BorrowedFd

/**
 * The file descriptor for the standard input stream of the current process.
 *
 * See [`io.stdin`][io.stdin] for the higher level handle, which should be preferred
 * whenever possible. See [Stderr] for why the file descriptor might be required and caveats.
 */
public val STDIN: BorrowedFd = BorrowedFd.borrowRaw(0)

/**
 * The file descriptor for the standard output stream of the current process.
 *
 * See [`io.stdout`][io.stdout] for the higher level handle, which should be preferred
 * whenever possible. See [Stderr] for why the file descriptor might be required and caveats. In
 * addition to the issues discussed there, note that [`Stdout`][io.Stdout] is buffered by
 * default, and writing to the file descriptor will bypass this buffer.
 */
public val STDOUT: BorrowedFd = BorrowedFd.borrowRaw(1)

/**
 * The file descriptor for the standard error stream of the current process.
 *
 * See [`io.stderr`][io.stderr] for the higher level handle, which should be preferred
 * whenever possible. However, there are situations where touching the `std.io` handles (or most
 * other parts of the standard library) risks deadlocks or other subtle bugs. For example:
 *
 * - Global allocators must be careful to [avoid reentrancy][global-alloc-reentrancy], and the
 *   `std.io` handles may allocate memory on (some) accesses.
 * - Signal handlers must be *async-signal-safe*, which rules out panicking, taking locks (may
 *   deadlock if the signal handler interrupted a thread holding that lock), allocating memory, or
 *   anything else that is not explicitly declared async-signal-safe.
 * - `CommandExt.preExec` callbacks can safely panic (with some limitations), but otherwise must
 *   abide by similar limitations as signal handlers. In particular, at the time these callbacks
 *   run, the stdio file descriptors have already been replaced, but the locks protecting the
 *   `std.io` handles may be permanently locked if another thread held the lock at `fork()` time.
 *
 * In these and similar cases, direct access to the file descriptor may be required. However, in
 * most cases, using the `std.io` handles and accessing the file descriptor via the `AsFd`
 * implementations is preferable, as it enables cooperation with the standard library's locking and
 * buffering.
 *
 * # I/O safety
 *
 * This is a `BorrowedFd<>` because the standard input/output/error streams are shared
 * resources that must remain available for the scope of the process. This is only true when
 * linking `std`, and may not always hold for [code running before `main()`][before-after-main] or
 * in `noStd` environments. It is [unsound][io-safety] to close these file descriptors. Safe
 * patterns for changing these file descriptors are available on Unix via the `StdioExt` extension
 * trait.
 *
 * [before-after-main]: ../../../std/index.html#use-before-and-after-main
 * [io-safety]: ../../../std/io/index.html#io-safety
 * [global-alloc-reentrancy]: ../../../std/alloc/trait.GlobalAlloc.html#re-entrance
 */
public val STDERR: BorrowedFd = BorrowedFd.borrowRaw(2)

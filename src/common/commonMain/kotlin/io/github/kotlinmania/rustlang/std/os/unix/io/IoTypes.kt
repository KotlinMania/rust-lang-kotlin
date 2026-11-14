package io.github.kotlinmania.rustlang.std.os.unix.io

import io.github.kotlinmania.rustlang.std.os.solid.io.OwnedFd
import io.github.kotlinmania.rustlang.std.thread.Result

public interface StdioExt {
    /**
     * Redirects the stdio file descriptor to point to the file description underpinning `fd`.
     *
     * Rust std.io write buffers (if any) are flushed, but other runtimes
     * (e.g. C stdio) or libraries that acquire a clone of the file descriptor
     * will not be aware of this change.
     *
     * # Platform-specific behavior
     *
     * This is [currently] implemented using
     *
     * - `fdRenumber` on wasip1
     * - `dup2` on most unixes
     *
     * @file:feature(stdioSwap)
     * use std.io::{self, Read, Write};
     * use std.os.unix.io.StdioExt;
     *
     * fn main() -> io.Result<()> {
     *    let (reader, mut writer) = io.pipe()?;
     *    let mut stdin = io.stdin();
     *    stdin.setFd(reader)?;
     *    writer.writeAll(b"Hello, world!")?;
     *    let mut buffer = vec[0; 13];
     *    assertEq(stdin.read(buffer)?, 13);
     *    assertEq(&buffer, b"Hello, world!");
     *    Ok(())
     * }
     * ```kotlin
     */
    public fun setFd(fd: OwnedFd): Result<Unit>

    /**
     * Redirects the stdio file descriptor and returns a new `OwnedFd`
     * backed by the previous file description.
     *
     * See [setFd] for details.
     *
     * [setFd]: StdioExt.setFd
     */
    public fun replaceFd(replaceWith: OwnedFd): Result<OwnedFd>

    /**
     * Redirects the stdio file descriptor to the null device (`/ dev / null`)
     * and returns a new `OwnedFd` backed by the previous file description.
     *
     * Programs that communicate structured data via stdio can use this early in `main()` to
     * extract the fds, treat them as other IO types (`File`, `UnixStream`, etc),
     * apply custom buffering or avoid interference from stdio use later in the program.
     *
     * See [setFd] for additional details.
     *
     * [setFd]: StdioExt.setFd
     */
    public fun takeFd(): Result<OwnedFd>
}

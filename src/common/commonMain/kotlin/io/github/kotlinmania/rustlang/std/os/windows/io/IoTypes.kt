package io.github.kotlinmania.rustlang.std.os.windows.io

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result

public interface StdioExt {
    /**
     * Sets the stdio console handle to `handle`, or `NULL` if it is `None`.
     * The old handle, if any, will not be closed, i.e. it is leaked because
     * console handles are shared global resources.
     *
     * Rust std.io write buffers (if any) are flushed, but other runtimes
     * (e.g. C stdio) or libraries that acquire a clone of the file handle
     * will not be aware of this change.
     *
     * @file:feature(stdioSwap)
     * use std.io::(self, Read, Write);
     * use std.os.windows.io.StdioExt;
     *
     * fn main() -> io.Result<()> (
     *    let (reader, mut writer) = io.pipe()?;
     *    let mut stdin = io.stdin();
     *    stdin.setHandle(Some(reader))?;
     *    writer.writeAll(b"Hello, world!")?;
     *    let mut buffer = vec[0; 13];
     *    assertEq(stdin.read(buffer)?, 13);
     *    assertEq(&buffer, b"Hello, world!");
     *    Ok(())
     * )
     * ```kotlin
     */
    public fun setHandle(handle: OwnedHandle?): Result<Unit, Error>

    /**
     * Sets the stdio console handle to `replaceWith`. The previous handle is returned, or
     * `None` if it was `NULL`.
     *
     * The returned handle is a `BorrowedHandle<>` because console handles are shared global resources
     * and may have been obtained by other functions or threads.
     * Only if you have ensured that no other part of the program has borrowed this handle you can convert it into
     * an `OwnedHandle` and drop that to close it.
     *
     * Like `setHandle()`, Rust std.io write buffers (if any) are flushed.
     */
    public fun replaceHandle(replaceWith: OwnedHandle): Result<BorrowedHandle?, Error>

    /**
     * Sets the stdio console handle to `NULL` and returns the old one
     *
     * See [setHandle] for additional details.
     *
     * [setHandle]: StdioExt.setHandle
     */
    public fun takeHandle(): Result<BorrowedHandle?, Error>
}

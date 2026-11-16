// port-lint: source io/copy.rs
package io.github.kotlinmania.rustlang.alloc.io.copy

import io.github.kotlinmania.rustlang.alloc.io.copy.generic.genericCopy
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.write.Write

/**
 * Used as a part of [copy specialization](SpecCopy) to communicate how many bytes
 * were copied, and whether copying is done.
 *
 * * [`Ended`](CopyState.Ended) indicates copying completed, moving a total
 *   of `n` bytes.
 * * [`Fallback`](CopyState.Fallback) indicates copying is _might_ not be
 *   complete, and so far `n` bytes have been copied using specialization.
 *   The remaining must be copied using a fallback implementation.
 *
 * If a particular `Read` and `Write` combination do not implement a specialized
 * copy routine, the specialized function will return `Fallback(0)`.
 */
public sealed class CopyState {
    public data class Ended(public var value: ULong) : CopyState()
    public data class Fallback(public var value: ULong) : CopyState()
}

// Note(#74481): Hard-links required to link from `alloc` to `std`

/**
 * Copies the entire contents of a reader into a writer.
 *
 * This function will continuously read data from `reader` and then
 * write it into `writer` in a streaming fashion until `reader`
 * returns EOF.
 *
 * On success, the total number of bytes that were copied from
 * `reader` to `writer` is returned.
 *
 * If you want to copy the contents of one file to another and you’re
 * working with filesystem paths, see the [fs.copy] function.
 *
 * [fs.copy]: ../../std/fs/fn.copy.html
 *
 * # Errors
 *
 * This function will return an error immediately if any call to [read] or
 * [write] returns an error. All instances of [ErrorKind.Interrupted] are
 * handled by this function and the underlying operation is retried.
 *
 * [read]: Read.read
 * [write]: Write.write
 *
 * # Examples
 *
 * ```kotlin
 * import std.io
 *
 * fun main(): io.Result<Unit> {
 *     var reader: List<UByte> = "hello".encodeToByteArray().map { it.toUByte() }
 *     var writer: List<UByte> = mutableListOf()
 *     io.copy(reader, writer).getOrElse { return Result.failure(it) }
 *     check("hello".encodeToByteArray() == writer)
 *     return Result.success(Unit)
 * }
 * ```
 *
 * # Platform-specific behavior
 *
 * On Linux (including Android), this function uses `copyFileRange(2)`,
 * `sendfile(2)` or `splice(2)` syscalls to move data directly between file
 * descriptors if possible.
 *
 * Note that platform-specific behavior may change in the future.
 */
public fun <R, W> copy(reader: R, writer: W): Result<ULong> where R : Read<R>, W : Write {
    return when (val matchVal = when (val tryResult = reader.copy(writer)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }) {
        is CopyState.Ended -> {
            val (copied) = matchVal
            Result.Ok(copied)
        }
        is CopyState.Fallback -> {
            val (copied1) = matchVal
            genericCopy(reader, writer).map({ additional -> copied1 + additional })
        }
    }
}

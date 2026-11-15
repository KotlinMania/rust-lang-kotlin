// port-lint: source io/seek.rs
package io.github.kotlinmania.rustlang.core.io.seek

import io.github.kotlinmania.rustlang.core.io.error.Result

/**
 * The `Seek` trait provides a cursor which can be moved within a stream of
 * bytes.
 *
 * The stream typically has a fixed size, allowing seeking relative to either
 * end or the current offset.
 *
 * # Examples
 *
 * `File`s implement `Seek`:
 *
 * ```kotlin
 * import std.fs.File
 * import std.io
 * import std.io.SeekFrom
 * import std.io.prelude.*
 *
 * fun main(): io.Result<Unit> {
 *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
 *     // move the cursor 42 bytes from the start of the file
 *     f.seek(SeekFrom.Start(42)).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public interface Seek {
    /**
     * Seek to an offset, in bytes, in a stream.
     *
     * A seek beyond the end of a stream is allowed, but behavior is defined
     * by the implementation.
     *
     * If the seek operation completed successfully,
     * this method returns the new position from the start of the stream.
     * That position can be used later with [SeekFrom.Start].
     *
     * # Errors
     *
     * Seeking can fail, for example because it might involve flushing a buffer.
     *
     * Seeking to a negative offset is considered an error.
     */
    public fun seek(pos: SeekFrom): Result<ULong>

    /**
     * Rewind to the beginning of a stream.
     *
     * This is a convenience method, equivalent to `seek(SeekFrom.Start(0))`.
     *
     * # Errors
     *
     * Rewinding can fail, for example because it might involve flushing a buffer.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.OpenOptions
     * import std.io.Read
     * import std.io.Seek
     * import std.io.Write
     *
     * var f = OpenOptions.new().write(true).read(true).create(true).open("foo.txt").getOrElse { return Result.failure(it) }
     * val hello = "Hello!\n"
     * f.append("$hello")
     * f.rewind().getOrElse { return Result.failure(it) }
     * var buf = ""
     * f.readToString(buf).getOrElse { return Result.failure(it) }
     * check(buf == hello)
     * ```
     */
    public fun rewind(): Result<Unit> {
        when (val tryResult = this.seek(SeekFrom.Start(0))) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
        return Result.Ok(Unit)
    }

    /**
     * Returns the length of this stream (in bytes).
     *
     * The default implementation uses up to three seek operations. If this
     * method returns successfully, the seek position is unchanged (i.e. the
     * position before calling this method is the same as afterwards).
     * However, if this method returns an error, the seek position is
     * unspecified.
     *
     * If you need to obtain the length of *many* streams and you don't care
     * about the seek position afterwards, you can reduce the number of seek
     * operations by simply calling `seek(SeekFrom.End(0))` and using its
     * return value (it is also the stream length).
     *
     * Note that length of a stream can change over time (for example, when
     * data is appended to a file). So calling this method multiple times does
     * not necessarily return the same length each time.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.Seek
     *
     * @feature(seekStreamLen)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): io.Result<Unit> {
     *         var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *         val len = f.streamLen().getOrElse { return Result.failure(it) }
     *         println("The file is currently $len bytes long")
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun streamLen(): Result<ULong> = streamLenDefault(this)

    /**
     * Returns the current seek position from the start of the stream.
     *
     * This is equivalent to `self.seek(SeekFrom.Current(0))`.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufRead
     * import std.io.BufReader
     * import std.io.Seek
     *
     * fun main(): io.Result<Unit> {
     *     var f = BufReader.new(File.open("foo.txt").getOrElse { return Result.failure(it) })
     *     val before = f.streamPosition().getOrElse { return Result.failure(it) }
     *     f.readLine("").getOrElse { return Result.failure(it) }
     *     val after = f.streamPosition().getOrElse { return Result.failure(it) }
     *     println("The first line was ${after - before} bytes long")
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun streamPosition(): Result<ULong> = this.seek(SeekFrom.Current(0))

    /**
     * Seeks relative to the current position.
     *
     * This is equivalent to `self.seek(SeekFrom.Current(offset))` but
     * doesn't return the new position which can allow some implementations
     * such as `BufReader` to perform more efficient seeks.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.Seek
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     f.seekRelative(10).getOrElse { return Result.failure(it) }
     *     check(f.streamPosition().getOrElse { return Result.failure(it) } == 10)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun seekRelative(offset: Long): Result<Unit> {
        when (val tryResult = this.seek(SeekFrom.Current(offset))) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
        return Result.Ok(Unit)
    }
}

/**
 * The default implementation of [Seek.streamLen].
 * This may be desirable in `libstd` where the default implementation is desirable,
 * but additional work needs to be done before or after.
 */
public fun <T : Seek> streamLenDefault(selfRef: T): Result<ULong> {
    val oldPos =
        when (val tryResult = selfRef.streamPosition()) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    val len =
        when (val tryResult = selfRef.seek(SeekFrom.End(0))) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    // Avoid seeking a third time when we were already at the end of the
    // stream. The branch is usually way cheaper than a seek operation.
    if (oldPos != len) {
        when (val tryResult = selfRef.seek(SeekFrom.Start(oldPos))) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    }
    return Result.Ok(len)
}

/**
 * Enumeration of possible methods to seek within an I/O object.
 *
 * It is used by the [Seek] trait.
 */
public sealed class SeekFrom {
    /**
     * Sets the offset to the provided number of bytes.
     */
    public data class Start(
        public var value: ULong,
    ) : SeekFrom()

    /**
     * Sets the offset to the size of this object plus the specified number of
     * bytes.
     *
     * It is possible to seek beyond the end of an object, but it's an error to
     * seek before byte 0.
     */
    public data class End(
        public var value: Long,
    ) : SeekFrom()

    /**
     * Sets the offset to the current position plus the specified number of
     * bytes.
     *
     * It is possible to seek beyond the end of an object, but it's an error to
     * seek before byte 0.
     */
    public data class Current(
        public var value: Long,
    ) : SeekFrom()

    public fun rustEquals(other: SeekFrom): Boolean = this == other
}

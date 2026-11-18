// port-lint: source io/buf_read.rs
package io.github.kotlinmania.rustlang.alloc.io.bufread

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.alloc.io.read.appendToString
import io.github.kotlinmania.rustlang.alloc.io.util.Lines
import io.github.kotlinmania.rustlang.alloc.io.util.Split
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * A `BufRead` is a type of [Read]er which has an internal buffer, allowing it
 * to perform extra ways of reading.
 *
 * For example, reading line-by-line is inefficient without using a buffer, so
 * if you want to read by line, you'll need `BufRead`, which includes a
 * [readLine] method as well as a [lines] iterator.
 *
 * # Examples
 *
 * A locked standard input implements `BufRead`:
 *
 * ```kotlin
 * import std.io
 * import std.io.prelude.*
 *
 * val stdin = io.stdin()
 * for (line in stdin.lock().lines()) {
 *     println("${line.getOrElse { return Result.failure(it) }}")
 * }
 * ```
 *
 * If you have something that implements [Read], you can use the `BufReader`
 * type to turn it into a `BufRead`.
 *
 * For example, `File` implements [Read], but not `BufRead`.
 * `BufReader` to the rescue!
 *
 * [readLine]: BufRead.readLine
 * [lines]: BufRead.lines
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.BufReader
 * import std.io.prelude.*
 *
 * fun main(): io.Result<Unit> {
 *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
 *     val f1 = BufReader.new(f)
 *     for (line in f1.lines()) {
 *         val line1 = line.getOrElse { return Result.failure(it) }
 *         println("$line")
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 */
public interface BufRead<T> : Read<T> {
    /**
     * Returns the contents of the internal buffer, filling it with more data, via [Read] methods, if empty.
     *
     * This is a lower-level method and is meant to be used together with [consume],
     * which can be used to mark bytes that should not be returned by subsequent calls to `read`.
     *
     * [consume]: BufRead.consume
     *
     * Returns an empty buffer when the stream has reached EOF.
     *
     * # Errors
     *
     * This function will return an I/O error if a [Read] method was called, but returned an error.
     *
     * # Examples
     *
     * A locked standard input implements `BufRead`:
     *
     * ```kotlin
     * import std.io
     * import std.io.prelude.*
     *
     * val stdin = io.stdin()
     * var stdin1 = stdin.lock()
     * val buffer = stdin1.fillBuf().getOrElse { return Result.failure(it) }
     * // work with buffer
     * println("$buffer")
     * // mark the bytes we worked with as read
     * val length = buffer.len()
     * stdin1.consume(length)
     * ```
     */
    public fun fillBuf(): Result<List<UByte>>

    /**
     * Marks the given `amount` of additional bytes from the internal buffer as having been read.
     * Subsequent calls to `read` only return bytes that have not been marked as read.
     *
     * This is a lower-level method and is meant to be used together with [fillBuf],
     * which can be used to fill the internal buffer via [Read] methods.
     *
     * It is a logic error if `amount` exceeds the number of unread bytes in the internal buffer, which is returned by [fillBuf].
     *
     * # Examples
     *
     * Since `consume()` is meant to be used with [fillBuf],
     * that method's example includes an example of `consume()`.
     *
     * [fillBuf]: BufRead.fillBuf
     */
    public fun consume(amount: Int)

    /**
     * Checks if there is any data left to be `read`.
     *
     * This function may fill the buffer to check for data,
     * so this function returns `Result<Boolean>`, not `Boolean`.
     *
     * The default implementation calls `fillBuf` and checks that the
     * returned slice is empty (which means that there is no data left,
     * since EOF is reached).
     *
     * # Errors
     *
     * This function will return an I/O error if a [Read] method was called, but returned an error.
     *
     * Examples
     *
     * ```kotlin
     * import std.io
     * import std.io.prelude.*
     *
     * @feature(bufReadHasDataLeft)
     * public fun doctestEntry(): Result<Unit> {
     *     val stdin = io.stdin()
     *     var stdin1 = stdin.lock()
     *     while (stdin1.hasDataLeft().getOrElse { return Result.failure(it) }) {
     *         var line = ""
     *         stdin1.readLine(line).getOrElse { return Result.failure(it) }
     *         // work with line
     *         println("$line")
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun hasDataLeft(): Result<Boolean> = this.fillBuf().map({ b -> !b.isEmpty() })

    /**
     * Reads all bytes into `buf` until the delimiter `byte` or EOF is reached.
     *
     * This function will read bytes from the underlying stream until the
     * delimiter or EOF is found. Once found, all bytes up to, and including,
     * the delimiter (if found) will be appended to `buf`.
     *
     * If successful, this function will return the total number of bytes read.
     *
     * This function is blocking and should be used carefully: it is possible for
     * an attacker to continuously send bytes without ever sending the delimiter
     * or EOF.
     *
     * # Errors
     *
     * This function will ignore all instances of [ErrorKind.Interrupted] and
     * will otherwise return any errors returned by [fillBuf].
     *
     * If an I/O error is encountered then all bytes read so far will be
     * present in `buf` and its length will have been adjusted appropriately.
     *
     * [fillBuf]: BufRead.fillBuf
     *
     * # Examples
     *
     * [`std.io.Cursor`][Cursor] is a type that implements `BufRead`. In
     * this example, we use [Cursor] to read all the bytes in a byte slice
     * in hyphen delimited segments:
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * var cursor = io.Cursor.new("lorem-ipsum".encodeToByteArray())
     * var buf = mutableListOf()
     * // cursor is at `l`
     * val numBytes = cursor.readUntil(45u.toUByte(), buf).expect("reading from cursor won't fail")
     * check(numBytes == 6)
     * check(buf == "lorem-".encodeToByteArray())
     * buf.clear()
     * // cursor is at `i`
     * val numBytes1 = cursor.readUntil(45u.toUByte(), buf).expect("reading from cursor won't fail")
     * check(numBytes1 == 5)
     * check(buf == "ipsum".encodeToByteArray())
     * buf.clear()
     * // cursor is at EOF
     * val numBytes2 = cursor.readUntil(45u.toUByte(), buf).expect("reading from cursor won't fail")
     * check(numBytes2 == 0)
     * check(buf == "".encodeToByteArray())
     * ```
     */
    public fun readUntil(byte: UByte, buf: MutableList<UByte>): Result<Int> = defaultReadUntil(this, byte, buf)

    /**
     * Skips all bytes until the delimiter `byte` or EOF is reached.
     *
     * This function will read (and discard) bytes from the underlying stream until the
     * delimiter or EOF is found.
     *
     * If successful, this function will return the total number of bytes read,
     * including the delimiter byte if found.
     *
     * This is useful for efficiently skipping data such as NUL-terminated strings
     * in binary file formats without buffering.
     *
     * This function is blocking and should be used carefully: it is possible for
     * an attacker to continuously send bytes without ever sending the delimiter
     * or EOF.
     *
     * # Errors
     *
     * This function will ignore all instances of [ErrorKind.Interrupted] and
     * will otherwise return any errors returned by [fillBuf].
     *
     * If an I/O error is encountered then all bytes read so far will be
     * present in `buf` and its length will have been adjusted appropriately.
     *
     * [fillBuf]: BufRead.fillBuf
     *
     * # Examples
     *
     * [`std.io.Cursor`][Cursor] is a type that implements `BufRead`. In
     * this example, we use [Cursor] to read some NUL-terminated information
     * about Ferris from a binary string, skipping the fun fact:
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * var cursor = io.Cursor.new("Ferris\u0000Likes long walks on the beach\u0000Crustacean\u0000!".encodeToByteArray())
     * // read name
     * var name = mutableListOf()
     * val numBytes = cursor.readUntil(0u.toUByte(), name).expect("reading from cursor won't fail")
     * check(numBytes == 7)
     * check(name == "Ferris\u0000".encodeToByteArray())
     * // skip fun fact
     * val numBytes1 = cursor.skipUntil(0u.toUByte()).expect("reading from cursor won't fail")
     * check(numBytes1 == 30)
     * // read animal type
     * var animal = mutableListOf()
     * val numBytes2 = cursor.readUntil(0u.toUByte(), animal).expect("reading from cursor won't fail")
     * check(numBytes2 == 11)
     * check(animal == "Crustacean\u0000".encodeToByteArray())
     * // reach EOF
     * val numBytes3 = cursor.skipUntil(0u.toUByte()).expect("reading from cursor won't fail")
     * check(numBytes3 == 1)
     * ```
     */
    public fun skipUntil(byte: UByte): Result<Int> = defaultSkipUntil(this, byte)

    /**
     * Reads all bytes until a newline (the `0xA` byte) is reached, and append
     * them to the provided [String] buffer.
     *
     * Previous content of the buffer will be preserved. To avoid appending to
     * the buffer, you need to [clear] it first.
     *
     * This function will read bytes from the underlying stream until the
     * newline delimiter (the `0xA` byte) or EOF is found. Once found, all bytes
     * up to, and including, the delimiter (if found) will be appended to
     * `buf`.
     *
     * If successful, this function will return the total number of bytes read.
     *
     * If this function returns [Ok], the stream has reached EOF.
     *
     * This function is blocking and should be used carefully: it is possible for
     * an attacker to continuously send bytes without ever sending a newline
     * or EOF. You can use [take] to limit the maximum number of bytes read.
     *
     * [Ok]: Ok
     * [clear]: String.clear
     *
     * # Errors
     *
     * This function has the same error semantics as [readUntil] and will
     * also return an error if the read bytes are not valid UTF-8. If an I/O
     * error is encountered then `buf` may contain some bytes already read in
     * the event that all data read so far was valid UTF-8.
     *
     * [readUntil]: BufRead.readUntil
     *
     * # Examples
     *
     * [`std.io.Cursor`][Cursor] is a type that implements `BufRead`. In
     * this example, we use [Cursor] to read all the lines in a byte slice:
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * var cursor = io.Cursor.new("foo\nbar".encodeToByteArray())
     * var buf = ""
     * // cursor is at `f`
     * val numBytes = cursor.readLine(buf).expect("reading from cursor won't fail")
     * check(numBytes == 4)
     * check(buf == "foo\n")
     * buf = ""
     * // cursor is at `b`
     * val numBytes1 = cursor.readLine(buf).expect("reading from cursor won't fail")
     * check(numBytes1 == 3)
     * check(buf == "bar")
     * buf = ""
     * // cursor is at EOF
     * val numBytes2 = cursor.readLine(buf).expect("reading from cursor won't fail")
     * check(numBytes2 == 0)
     * check(buf == "")
     * ```
     */
    public fun readLine(buf: String): Result<Int> {
        // Note that we are not calling the `.readUntil` method here, but
        // rather our hardcoded implementation. For more details as to why, see
        // the comments in `defaultReadToString`.
        // ignore-tidy-undocumented-unchecked
        return appendToString(buf, { b -> defaultReadUntil(this, 10u.toUByte(), b) })
    }

    /**
     * Returns an iterator over the contents of this reader split on the byte
     * `byte`.
     *
     * The iterator returned from this function will return instances of
     * <code>[io.Result]<[Vec]\<u8>></code>. Each vector returned will *not* have
     * the delimiter byte at the end.
     *
     * This function will yield errors whenever [readUntil] would have
     * also yielded an error.
     *
     * [readUntil]: BufRead.readUntil
     *
     * # Examples
     *
     * [`std.io.Cursor`][Cursor] is a type that implements `BufRead`. In
     * this example, we use [Cursor] to iterate over all hyphen delimited
     * segments in a byte slice
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * val cursor = io.Cursor.new("lorem-ipsum-dolor".encodeToByteArray())
     * var splitIter = cursor.split(45u.toUByte()).map({ l -> l.unwrap() })
     * check(splitIter.next() == "lorem".encodeToByteArray().toVec())
     * check(splitIter.next() == "ipsum".encodeToByteArray().toVec())
     * check(splitIter.next() == "dolor".encodeToByteArray().toVec())
     * check(splitIter.next() == null)
     * ```
     */
    public fun split(byte: UByte): Split<T> = split(this, byte)

    /**
     * Returns an iterator over the lines of this reader.
     *
     * The iterator returned from this function will yield instances of
     * <code>[io.Result]<[String]></code>. Each string returned will *not* have a newline
     * byte (the `0xA` byte) or `CRLF` (`0xD`, `0xA` bytes) at the end.
     *
     * # Examples
     *
     * [`std.io.Cursor`][Cursor] is a type that implements `BufRead`. In
     * this example, we use [Cursor] to iterate over all the lines in a byte
     * slice.
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * val cursor = io.Cursor.new("lorem\nipsum\r\ndolor".encodeToByteArray())
     * var linesIter = cursor.lines().map({ l -> l.unwrap() })
     * check(linesIter.next() == "lorem")
     * check(linesIter.next() == "ipsum")
     * check(linesIter.next() == "dolor")
     * check(linesIter.next() == null)
     * ```
     *
     * # Errors
     *
     * Each line of the iterator has the same error semantics as [BufRead.readLine].
     */
    public fun lines(): Lines<T> = Lines(this)
}

public fun <R : BufRead<R>> defaultReadUntil(r: R, delim: UByte, buf: MutableList<UByte>): Result<Int> {
    var read = 0
    while (true) {
        val (done1, used) = run {
            val available =
                run {
                    val matchVal = r.fillBuf()
                    when {
                        matchVal is RustResult.Ok<*, *> -> {
                            val n = matchVal.okValue()
                            n
                        }
                        matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                            val e = matchVal.errValue()
                            continue
                        }
                        else -> {
                            val e1 = matchVal.errValue()
                            return RustResult.Err(e1)
                        }
                    }
                }
            val (done, available1) =
                when (val matchVal1 = memchr(delim, available)) {
                    null -> Pair(false, available)
                    else -> {
                        val i = matchVal1
                        Pair(true, available[0..i])
                    }
                }
            run {
                val count = available1.len()
                when (val tryResult = buf.tryReserve(count)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                // SAFETY:
                // * self and buf are non-overlapping
                // * buf[..len] is already initialized
                // * buf[len..len + count] is initialized by copyNonoverlapping
                // * len + count is within the capacity of buf based on the reservation completed above
                run {
                    val len = buf.size
                    val src = available1.asPtr()
                    val dst = MutPtr((buf.asMutPtr()).rawAddress + ((len).toLong() * (PrimitiveLayouts.U8).strideBytes))
                    copyNonoverlapping(src, dst, count)
                    buf.setLen(len + count)
                }
            }(done, available1.len())
        }
        r.consume(used)
        read += used
        if (done1 || used == 0) {
            return RustResult.Ok(read)
        }
    }
}

public fun <R : BufRead<R>> defaultSkipUntil(r: R, delim: UByte): Result<Int> {
    var read = 0
    while (true) {
        val (done, used) = run {
            val available =
                run {
                    val matchVal = r.fillBuf()
                    when {
                        matchVal is RustResult.Ok<*, *> -> {
                            val n = matchVal.okValue()
                            n
                        }
                        matchVal is RustResult.Err<*, *> && matchVal.errValue().kind() == ErrorKind.Interrupted -> {
                            val e = matchVal.errValue()
                            continue
                        }
                        else -> {
                            val e1 = matchVal.errValue()
                            return RustResult.Err(e1)
                        }
                    }
                }
            when (val matchVal1 = memchr(delim, available)) {
                null -> Pair(false, available.len())
                else -> {
                    val i = matchVal1
                    Pair(true, i + 1)
                }
            }
        }
        r.consume(used)
        read += used
        if (done || used == 0) {
            return RustResult.Ok(read)
        }
    }
}

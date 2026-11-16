// port-lint: source io/read.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.io.read

import io.github.kotlinmania.rustlang.alloc.io.util.Bytes
import io.github.kotlinmania.rustlang.alloc.io.util.bytes
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.util.Chain
import io.github.kotlinmania.rustlang.core.io.util.Take
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.dropguard.DropGuard
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.andThen

// Keep this order, it's important for rust-analyzer (the preferred-to-implement method should come first).

/**
 * The `Read` trait allows for reading bytes from a source.
 *
 * Implementors of the `Read` trait are called 'readers'.
 *
 * Readers are defined by one required method, [read]. Each call to [read]
 * will attempt to pull bytes from this source into a provided buffer. A
 * number of other methods are implemented in terms of [read], giving
 * implementors a number of ways to read bytes while only needing to implement
 * a single method.
 *
 * Readers are intended to be composable with one another. Many implementors
 * throughout [std.io] take and provide types which implement the `Read`
 * trait.
 *
 * Please note that each call to [read] may involve a system call, and
 * therefore, using something that implements [BufRead], such as
 * `BufReader`, will be more efficient.
 *
 * Repeated calls to the reader use the same cursor, so for example
 * calling `readToEnd` twice on a `File` will only return the file's
 * contents once. It's recommended to first call `rewind()` in that case.
 *
 * # Examples
 *
 * `File`s implement `Read`:
 *
 * ```kotlin
 * import std.fs.File
 * import std.io
 * import std.io.prelude.*
 *
 * fun main(): io.Result<Unit> {
 *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
 *     var buffer = MutableList(10) { 0 }
 *     // read up to 10 bytes
 *     f.read(buffer).getOrElse { return Result.failure(it) }
 *     var buffer1 = mutableListOf()
 *     // read the whole file
 *     f.readToEnd(buffer1).getOrElse { return Result.failure(it) }
 *     // read into a String, so that you don't need to do the conversion.
 *     var buffer2 = ""
 *     f.readToString(buffer2).getOrElse { return Result.failure(it) }
 *     // and more! See the other methods for more details.
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Read from [str] because [`&[u8]`][prim@slice] implements `Read`:
 *
 * ```kotlin
 * import io.Result
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     var b = ("This string will be read").encodeToByteArray().map { it.toUByte() }
 *     var buffer = MutableList(10) { 0 }
 *     // read up to 10 bytes
 *     b.read(buffer).getOrElse { return Result.failure(it) }
 *     // etc... it works exactly as a File does!
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [read]: Read.read
 * [str]: prim@str
 */
public interface Read<SelfType> {
    /**
     * Pull some bytes from this source into the specified buffer, returning
     * how many bytes were read.
     *
     * This function does not provide any guarantees about whether it blocks
     * waiting for data, but if an object needs to block for a read and cannot,
     * it will typically signal this via an [Err] return value.
     *
     * If the return value of this method is [Ok], then implementations must
     * guarantee that `0 <= n <= buf.len()`. A nonzero `n` value indicates
     * that the buffer `buf` has been filled in with `n` bytes of data from this
     * source. If `n` is `0`, then it can indicate one of two scenarios:
     *
     * 1. This reader has reached its "end of file" and will likely no longer
     *    be able to produce bytes. Note that this does not mean that the
     *    reader will *always* no longer be able to produce bytes. As an example,
     *    on Linux, this method will call the `recv` syscall for a `TcpStream`,
     *    where returning zero indicates the connection was shut down correctly. While
     *    for `File`, it is possible to reach the end of file and get zero as result,
     *    but if more data is appended to the file, future calls to `read` will return
     *    more data.
     * 2. The buffer specified was 0 bytes in length.
     *
     * It is not an error if the returned value `n` is smaller than the buffer size,
     * even when the reader is not at the end of the stream yet.
     * This may happen for example because fewer bytes are actually available right now
     * (e. g. being close to end-of-file) or because read() was interrupted by a signal.
     *
     * As this trait is safe to implement, callers in unchecked code cannot rely on
     * `n <= buf.len()` for safety.
     * Extra care needs to be taken when `unchecked` functions are used to access the read bytes.
     * Callers have to ensure that no unchecked out-of-bounds accesses are possible even if
     * `n> buf.len()`.
     *
     * *Implementations* of this method can make no assumptions about the contents of `buf` when
     * this function is called. It is recommended that implementations only write data to `buf`
     * instead of reading its contents.
     *
     * Correspondingly, however, *callers* of this method in unchecked code must not assume
     * any guarantees about how the implementation uses `buf`. The trait is safe to implement,
     * so it is possible that the code that's supposed to write to the buffer might also read
     * from it. It is your responsibility to make sure that `buf` is initialized
     * before calling `read`. Calling `read` with an uninitialized `buf` (of the kind one
     * obtains via [MaybeUninit.T]) is not safe, and can lead to undefined behavior.
     *
     * # Errors
     *
     * If this function encounters any form of I/O or other error, an error
     * variant will be returned. If an error is returned then it must be
     * guaranteed that no bytes were read.
     *
     * An error of the [ErrorKind.Interrupted] kind is non-fatal and the read
     * operation should be retried if there is nothing else to do.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * [Ok]: Ok
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(10) { 0 }
     *     // read up to 10 bytes
     *     val n = f.read(buffer).getOrElse { return Result.failure(it) }
     *     println("The bytes: ${buffer[0 until n]}")
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun read(buf: MutableList<UByte>): Result<Int> {
        val buf1 = BorrowedBuf.from(buf)
        return this.readBuf(buf1.unfilled()).map({ buf1.len() })
    }

    /**
     * Like `read`, except that it reads into a slice of buffers.
     *
     * Data is copied to fill each buffer in order, with the final buffer
     * written to possibly being only partially filled. This method must
     * behave equivalently to a single call to `read` with concatenated
     * buffers.
     *
     * The default implementation calls `read` with either the first nonempty
     * buffer provided, or an empty one if none exists.
     */
    public fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = defaultReadVectored({ b -> this.read(b) }, bufs)

    /**
     * Determines if this `Read`er has an efficient `readVectored`
     * implementation.
     *
     * If a `Read`er does not override the default `readVectored`
     * implementation, code using it may want to avoid the method all together
     * and coalesce writes into a single buffer for higher performance.
     *
     * The default implementation returns `false`.
     */
    public fun isReadVectored(): Boolean = false

    /**
     * Reads all bytes until EOF in this source, placing them into `buf`.
     *
     * All bytes read from this source will be appended to the specified buffer
     * `buf`. This function will continuously call [read] to append more data to
     * `buf` until [read] returns either [Ok] or an error of
     * non-[ErrorKind.Interrupted] kind.
     *
     * If successful, this function will return the total number of bytes read.
     *
     * # Errors
     *
     * If this function encounters an error of the kind
     * [ErrorKind.Interrupted] then the error is ignored and the operation
     * will continue.
     *
     * If any other read error is encountered then this function immediately
     * returns. Any bytes which have already been read will be appended to
     * `buf`.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * [Ok]: Ok
     * [read]: Read.read
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = mutableListOf()
     *     // read the whole file
     *     f.readToEnd(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * (See also the `std.fs.read` convenience function for reading from a
     * file.)
     *
     * ## Implementing `readToEnd`
     *
     * When implementing the `io.Read` trait, it is recommended to allocate
     * memory using [Vec.tryReserve]. However, this behavior is not guaranteed
     * by all implementations, and `readToEnd` may not handle out-of-memory
     * situations gracefully.
     *
     * ```kotlin
     * import io.Result
     *
     * fun readToEnd(destVec: List<UByte>): Result<Int> {
     *     val initialVecLen = destVec.size
     *     while (true) {
     *         val srcBuf = this.exampleDatasource.fillBuf().getOrElse { return Result.failure(it) }
     *         if (srcBuf.isEmpty()) {
     *             break
     *         }
     *         destVec.tryReserve(srcBuf.len()).getOrElse { return Result.failure(it) }
     *         destVec.extendFromSlice(srcBuf)
     *         // Any irreversible side effects should happen after `tryReserve` succeeds,
     *         // to avoid losing data on allocation error.
     *         val read = srcBuf.len()
     *         this.exampleDatasource.consume(read)
     *     }
     *     return Result.success(destVec.size - initialVecLen)
     * }
     * ```
     *
     * # Usage Notes
     *
     * `readToEnd` attempts to read a source until EOF, but many sources are continuous streams
     * that do not send EOF. In these cases, `readToEnd` will block indefinitely. Standard input
     * is one such stream which may be finite if piped, but is typically continuous. For example,
     * `cat file | my - rust - program` will correctly terminate with an `EOF` upon closure of cat.
     * Reading user input or running programs that remain open indefinitely will never terminate
     * the stream with `EOF` (e.g. `yes | my - rust - program`).
     *
     * Using `.lines()` with a `BufReader` or using [read] can provide a better solution
     *
     * [read]: Read.read
     */
    public fun readToEnd(buf: List<UByte>): Result<Int> = defaultReadToEnd(this, buf, null)

    /**
     * Reads all bytes until EOF in this source, appending them to `buf`.
     *
     * If successful, this function returns the number of bytes which were read
     * and appended to `buf`.
     *
     * # Errors
     *
     * If the data in this stream is *not* valid UTF-8 then an error is
     * returned and `buf` is unchanged.
     *
     * See [readToEnd] for other error semantics.
     *
     * [readToEnd]: Read.readToEnd
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = ""
     *     f.readToString(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * (See also the `std.fs.readToString` convenience function for
     * reading from a file.)
     *
     * # Usage Notes
     *
     * `readToString` attempts to read a source until EOF, but many sources are continuous streams
     * that do not send EOF. In these cases, `readToString` will block indefinitely. Standard input
     * is one such stream which may be finite if piped, but is typically continuous. For example,
     * `cat file | my - rust - program` will correctly terminate with an `EOF` upon closure of cat.
     * Reading user input or running programs that remain open indefinitely will never terminate
     * the stream with `EOF` (e.g. `yes | my - rust - program`).
     *
     * Using `.lines()` with a `BufReader` or using [read] can provide a better solution
     *
     * [read]: Read.read
     */
    public fun readToString(buf: String): Result<Int> = defaultReadToString(this, buf, null)

    /**
     * Reads the exact number of bytes required to fill `buf`.
     *
     * This function reads as many bytes as necessary to completely fill the
     * specified buffer `buf`.
     *
     * *Implementations* of this method can make no assumptions about the contents of `buf` when
     * this function is called. It is recommended that implementations only write data to `buf`
     * instead of reading its contents. The documentation on [read] has a more detailed
     * explanation of this subject.
     *
     * # Errors
     *
     * If this function encounters an error of the kind
     * [ErrorKind.Interrupted] then the error is ignored and the operation
     * will continue.
     *
     * If this function encounters an "end of file" before completely filling
     * the buffer, it returns an error of the kind [ErrorKind.UnexpectedEof].
     * The contents of `buf` are unspecified in this case.
     *
     * If any other read error is encountered then this function immediately
     * returns. The contents of `buf` are unspecified in this case.
     *
     * If this function returns an error, it is unspecified how many bytes it
     * has read, but it will never read more than would be necessary to
     * completely fill the buffer.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * [read]: Read.read
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(10) { 0 }
     *     // read exactly 10 bytes
     *     f.readExact(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun readExact(buf: MutableList<UByte>): Result<Unit> = defaultReadExact(this, buf)

    /**
     * Pull some bytes from this source into the specified buffer.
     *
     * This is equivalent to the [`read`](Read.read) method, except that it is passed a [BorrowedCursor] rather than `[UByte]` to allow use
     * with uninitialized buffers. The new data will be appended to any existing contents of `buf`.
     *
     * The default implementation delegates to `read`.
     *
     * This method makes it possible to return both data and an error but it is advised against.
     */
    public fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ b -> this.read(b) }, buf)

    /**
     * Reads the exact number of bytes required to fill `cursor`.
     *
     * This is similar to the [`readExact`](Read.readExact) method, except
     * that it is passed a [BorrowedCursor] rather than `[UByte]` to allow use
     * with uninitialized buffers.
     *
     * # Errors
     *
     * If this function encounters an error of the kind [ErrorKind.Interrupted]
     * then the error is ignored and the operation will continue.
     *
     * If this function encounters an "end of file" before completely filling
     * the buffer, it returns an error of the kind [ErrorKind.UnexpectedEof].
     *
     * If any other read error is encountered then this function immediately
     * returns.
     *
     * If this function returns an error, all bytes read will be appended to `cursor`.
     */
    public fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBufExact(this, cursor)

    /**
     * Creates a "by reference" adapter for this instance of `Read`.
     *
     * The returned adapter also implements `Read` and will simply borrow this
     * current reader.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.Read
     *
     * fun main(): io.Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = mutableListOf()
     *     var otherBuffer = mutableListOf()
     *     run {
     *         val reference = f.byRef()
     *         // read at most 5 bytes
     *         reference.take(5).readToEnd(buffer).getOrElse { return Result.failure(it) }
     *     }
     *     // drop our reference so we can use f again
     *     // original file still usable, read the rest
     *     f.readToEnd(otherBuffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun byRef(): SelfType = this

    /**
     * Transforms this `Read` instance to an [Iterator] over its bytes.
     *
     * The returned type implements [Iterator] where the [Item] is
     * <code>[Result]<[u8], [io.Error]></code>.
     * The yielded item is [Ok] if a byte was successfully read and [Err]
     * otherwise. EOF is mapped to returning [None] from this iterator.
     *
     * The default implementation calls `read` for each byte,
     * which can be very inefficient for data that's not in memory,
     * such as `File`. Consider using a `BufReader` in such cases.
     *
     * # Errors
     *
     * When the returned iterator calls [Iterator.next],
     * if it encounters an error of the kind [ErrorKind.Interrupted]
     * then the error is ignored and it will try to read the byte again.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * [Item]: Iterator.Item
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.BufReader
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     val f = BufReader.new(File.open("foo.txt").getOrElse { return Result.failure(it) })
     *     for (byte in f.bytes()) {
     *         println("${byte.getOrElse { return Result.failure(it) }}")
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun bytes(): Bytes<SelfType> = bytes(this)

    /**
     * Creates an adapter which will chain this stream with another.
     *
     * The returned `Read` instance will first read all bytes from this object
     * until EOF is encountered. Afterwards the output is equivalent to the
     * output of `next`.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     val f1 = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     val f2 = File.open("bar.txt").getOrElse { return Result.failure(it) }
     *     var handle = f1.chain(f2)
     *     var buffer = ""
     *     // read the value into a String. We could use any Read method here,
     *     // this is just one example.
     *     handle.readToString(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun <R : Read<R>> chain(next: R): Chain<SelfType, R> = chain(this, next)

    /**
     * Creates an adapter which will read at most `limit` bytes from it.
     *
     * This function returns a new instance of `Read` which will read at most
     * `limit` bytes, after which it will always return EOF ([Ok]). Any
     * read errors will not count towards the number of bytes read and future
     * calls to [read] may succeed.
     *
     * # Examples
     *
     * `File`s implement `Read`:
     *
     * [Ok]: Ok
     * [read]: Read.read
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(5) { 0 }
     *     // read at most five bytes
     *     var handle = f.take(5)
     *     handle.read(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun take(limit: ULong): Take<SelfType> = take(this, limit)

    /**
     * Read and return a fixed array of bytes from this source.
     *
     * This function uses an array sized based on a const generic size known at compile time. You
     * can specify the size with turbofish (`reader.readArray<8>()`), or let type inference
     * determine the number of bytes needed based on how the return value gets used. For instance,
     * this function works well with functions like [u64.fromLeBytes] to turn an array of
     * bytes into an integer of the same size.
     *
     * Like `readExact`, if this function encounters an "end of file" before reading the desired
     * number of bytes, it returns an error of the kind [ErrorKind.UnexpectedEof].
     *
     * ```kotlin
     * import std.io.Cursor
     * import std.io.prelude.*
     *
     * @feature(readArray)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         var buf = Cursor.new(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2))
     *         val x = run { val bytesTmp0 = buf.readArray().getOrElse { return Result.failure(it) }; ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }
     *         val y = run { val bytesTmp1 = buf.readArray().getOrElse { return Result.failure(it) }; ((bytesTmp1[0].toUInt() shl 24) or (bytesTmp1[1].toUInt() shl 16) or (bytesTmp1[2].toUInt() shl 8) or bytesTmp1[3].toUInt()) }
     *         val z = run { val bytesTmp2 = buf.readArray().getOrElse { return Result.failure(it) }; ((bytesTmp2[0].toInt() shl 8) or bytesTmp2[1].toInt()).toUShort() }
     *         check(x == 0x807060504030201uL)
     *         check(y == 0x9080706u)
     *         check(z == 0x504u.toUShort())
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun readArray(n: Int): Result<UByteArray> {
        val buf = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<Result<UByteArray>>()) }
        val borrowedBuf = BorrowedBuf.from(buf.asMutSlice())
        when (val tryResult = this.readBufExact(borrowedBuf.unfilled())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        // Guard against incorrect `readBufExact` implementations.
        check(borrowedBuf.len() == n)
        // SAFETY: Buffer was initialised above.
        return Result.Ok(MaybeUninit.arrayAssumeInit(buf))
    }

    /**
     * Read and return a type (e.g. an integer) in little-endian order.
     *
     * You can specify the type with turbofish (`reader.readLe<ULong>()`), or let type inference
     * determine the type based on how the return value gets used.
     *
     * Like `readExact`, if this function encounters an "end of file" before reading the desired
     * number of bytes, it returns an error of the kind [ErrorKind.UnexpectedEof].
     *
     * ```kotlin
     * import std.io.Cursor
     * import std.io.prelude.*
     *
     * @feature(readLe)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         var buf = Cursor.new(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2))
     *         val x: ULong = buf.readLe().getOrElse { return Result.failure(it) }
     *         val y: UInt = buf.readLe().getOrElse { return Result.failure(it) }
     *         val z = buf.readLe<UShort>().getOrElse { return Result.failure(it) }
     *         check(x == 0x807060504030201uL)
     *         check(y == 0x6070809u)
     *         check(z == 0x405)
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun <T : FromEndianBytes<T>> readLe(readLeFromTFn: (Read) -> Result<T>): Result<T> = readLeFromTFn(this)

    /**
     * Read and return a type (e.g. an integer) in big-endian order.
     *
     * You can specify the type with turbofish (`reader.readBe<ULong>()`), or let type inference
     * determine the type based on how the return value gets used.
     *
     * Like `readExact`, if this function encounters an "end of file" before reading the desired
     * number of bytes, it returns an error of the kind [ErrorKind.UnexpectedEof].
     *
     * ```kotlin
     * import std.io.Cursor
     * import std.io.prelude.*
     *
     * @feature(readLe)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         var buf = Cursor.new(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2))
     *         val x: ULong = buf.readBe().getOrElse { return Result.failure(it) }
     *         val y: UInt = buf.readBe().getOrElse { return Result.failure(it) }
     *         val z = buf.readBe<UShort>().getOrElse { return Result.failure(it) }
     *         check(x == 0x102030405060708uL)
     *         check(y == 0x9080706u)
     *         check(z == 0x504)
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun <T : FromEndianBytes<T>> readBe(readBeFromTFn: (Read) -> Result<T>): Result<T> = readBeFromTFn(this)
}

/**
 * Reads all bytes from a [reader][Read] into a new [String].
 *
 * This is a convenience function for [Read.readToString]. Using this
 * function avoids having to create a variable first and provides more type
 * safety since you can only get the buffer out if there were no errors. (If you
 * use [Read.readToString] you have to remember to check whether the read
 * succeeded because otherwise your buffer will be empty or only partially full.)
 *
 * # Performance
 *
 * The downside of this function's increased ease of use and type safety is
 * that it gives you less control over performance. For example, you can't
 * pre-allocate memory like you can using [String.withCapacity] and
 * [Read.readToString]. Also, you can't re-use the buffer if an error
 * occurs while reading.
 *
 * In many cases, this function's performance will be adequate and the ease of use
 * and type safety tradeoffs will be worth it. However, there are cases where you
 * need more control over performance, and in those cases you should definitely use
 * [Read.readToString] directly.
 *
 * Note that in some special cases, such as when reading files, this function will
 * pre-allocate memory based on the size of the input it is reading. In those
 * cases, the performance should be as good as if you had used
 * [Read.readToString] with a manually pre-allocated buffer.
 *
 * # Errors
 *
 * This function forces you to handle errors because the output (the `String`)
 * is wrapped in a [Result]. See [Read.readToString] for the errors
 * that can occur. If any error occurs, you will get an [Err], so you
 * don't have to worry about your buffer being empty or partially full.
 *
 * # Examples
 *
 * ```kotlin
 * import io.Result
 *
 * fun main(): Result<Unit> {
 *     val stdin = readToString(stdin()).getOrElse { return Result.failure(it) }
 *     println("Stdin was:")
 *     println("$stdin")
 *     return Result.success(Unit)
 * }
 * ```
 *
 * # Usage Notes
 *
 * `readToString` attempts to read a source until EOF, but many sources are continuous streams
 * that do not send EOF. In these cases, `readToString` will block indefinitely. Standard input
 * is one such stream which may be finite if piped, but is typically continuous. For example,
 * `cat file | my - rust - program` will correctly terminate with an `EOF` upon closure of cat.
 * Reading user input or running programs that remain open indefinitely will never terminate
 * the stream with `EOF` (e.g. `yes | my - rust - program`).
 *
 * Using `.lines()` with a `BufReader` or using [read] can provide a better solution
 *
 * [read]: Read.read
 */
public fun <R : Read<R>> readToString(reader: R): Result<String> {
    val buf = ""
    when (val tryResult = reader.readToString(buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
    return Result.Ok(buf)
}

/**
 * Bare metal platforms usually have very small amounts of RAM
 * (in the order of hundreds of KB)
 */
public val DEFAULT_BUF_SIZE: Int = 8 * 1024

/**
 * Several `readToString` and `readLine` methods in the standard library will
 * append data into a `String` buffer, but we need to be pretty careful when
 * doing this. The implementation will just call `.asMutVec()` and then
 * delegate to a byte-oriented reading method, but we must ensure that when
 * returning we never leave `buf` in a state such that it contains invalid UTF-8
 * in its bounds.
 *
 * To this end, we use an RAII guard (to protect against panics) which updates
 * the length of the string when it is dropped. This guard initially truncates
 * the string to the prior length and only after we've validated that the
 * new contents are valid UTF-8 do we allow it to set a longer length.
 *
 * The unsafety in this function is twofold:
 *
 * 1. We're looking at the raw bytes of `buf`, so we take on the burden of UTF-8
 *    checks.
 * 2. We're passing a raw buffer to the function `f`, and it is expected that
 *    the function only *appends* bytes to the buffer. We'll get undefined
 *    behavior if existing bytes are overwritten to have non-UTF-8 data.
 */
public fun appendToString(buf: String, f: (List<UByte>) -> Result<Int>): Result<Int> {
    val lenOriginal = buf.length
    // SAFETY: invalid UTF-8 discarded before return or unwind
    val bufVec = buf.asMutVec()
    // ignore-tidy-undocumented-unchecked
    var g =
        DropGuard.new(Pair(lenOriginal, bufVec), { (len, buf1) -> run {
            buf1.setLen(len)
        } })
    val ret = f(g.second)
    // SAFETY: the caller promises to only append data to `buf`
    val appended = g.second.getUnchecked(g.first until Int.MAX_VALUE)
    return if (String.fromUtf8(appended).isErr()) {
        ret.andThen({ Result.Err(Error.INVALID_UTF8) })
    } else {
        g.first = g.second.len()
        ret
    }
}

/**
 * Here we must serve many masters with conflicting goals:
 *
 * - avoid allocating unless necessary
 * - avoid overallocating if we know the exact size (#89165)
 * - avoid passing large buffers to readers that always initialize the free capacity if they perform short reads (#23815, #23820)
 * - avoid re-initializing unfilled bytes into the spare buffer if we initialized >PROBE_SIZE unfilled bytes in a previous loop (#158008)
 * - pass large buffers to readers that do not initialize the spare capacity. this can amortize per-call overheads
 * - pass not-too-small and not-too-large buffers to Windows read APIs because they manage to suffer from both problems
 *   at the same time, i.e. small reads suffer from syscall overhead, all reads incur costs proportional to buffer size (#110650)
 * - also avoid <4 byte reads as this may split UTF-8 code points, which can be a problem for Windows console reads (#142847)
 */
public fun <R : Read<R>> defaultReadToEnd(r: R, buf: List<UByte>, sizeHint: Int?): Result<Int> {
    val PROBE_SIZE: Int = 32
    fun <R : Read<R>> smallProbeRead(r1: R, buf1: List<UByte>): Result<Int> {
        val probe = UByteArray((PROBE_SIZE).toInt()) { 0u.toUByte() }
        while (true) {
            // Without global OOM handling we must proactively allocate the buffer
            // to avoid failing after already reading data.
            when (val tryResult = buf1.tryReserve(PROBE_SIZE)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            run {
                val matchVal = r1.read(probe)
                when {
                    matchVal is Result.Ok -> {
                        val n = matchVal.value
                        // there is no way to recover from allocation failure here
                        // because the data has already been read.
                        when (val tryResult = buf1.tryExtendFromSliceOfBytes(((probe).sliceArray(0 until n)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
                        return Result.Ok(n)
                    }
                    matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                        val e = matchVal.error
                        continue
                    }
                    else -> {
                        val e1 = matchVal.error
                        return Result.Err(e1)
                    }
                }
            }
        }
    }
    val startLen = buf.size
    val startCap = buf.capacity()
    // Optionally limit the maximum bytes read on each iteration.
    // This adds an arbitrary fiddle factor to allow for more data than we expect.
    var maxReadSize = (sizeHint?.let(closure@{ s -> when (val tryResult = s.checkedAdd(1024)) { is Result.Ok -> tryResult.value; is Result.Err -> return@closure Result.Err(tryResult.error) }.checkedNextMultipleOf(DEFAULT_BUF_SIZE) }) ?: DEFAULT_BUF_SIZE)
    // Tracks how many bytes are initialized in the buffer
    var initUntil = buf.size
    // avoid inflating empty/small vecs before we have determined that there's anything to read
    if ((sizeHint == null || sizeHint == 0) && buf.capacity() - buf.size < PROBE_SIZE) {
        val read = when (val tryResult = smallProbeRead(r, buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        if (read == 0) {
            return Result.Ok(0)
        }
    }
    while (true) {
        if (buf.spareCapacityMut().len() < PROBE_SIZE && buf.capacity() == startCap) {
            // The buffer might be an exact fit. Let's read into a probe buffer
            // and see if it returns `Ok(0)`. If so, we've avoided an
            // unnecessary doubling of the capacity. But if not, append the
            // probe buffer to the primary buffer and let its capacity grow.
            val read1 = when (val tryResult = smallProbeRead(r, buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            if (read1 == 0) {
                return Result.Ok(buf.size - startLen)
            }
            initUntil = buf.size
            // In the case of very short reads, continue to use the stack buffer
            // until either we reach the end or we need to reallocate.
            continue
        }
        // Avoid unnecessarily short reads by ensuring there's at least PROBE_SIZE space available.
        // And assert that PROBE_SIZE is always at least large enough to fit any UTF-8 encoded code point.
        check(PROBE_SIZE >= Char.MAX_LEN_UTF8)
        if (buf.spareCapacityMut().len() < PROBE_SIZE) {
            when (val tryResult = buf.tryReserve(PROBE_SIZE)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            // When reallocation occurs, we have to update initUntil accordingly
            // to re-calibrate how many bytes are actually initialized in the buffer
            initUntil = buf.size
        }
        // We set a threshold of >PROBE_SIZE initialized yet unfilled bytes left in the
        // spare buffer before determining that we need to initialize more bytes into
        // the spare buffer
        val bufLen =
            if (initUntil > buf.size + PROBE_SIZE) {
                initUntil - buf.size
            } else {
                minOf(maxReadSize, buf.capacity() - buf.size)
            }
        val wasInit = initUntil >= buf.size + bufLen
        var spare = buf.spareCapacityMut()
        spare = spare[0 until bufLen]
        val readBuf: BorrowedBuf<UByte>  = spare.into()
        if (wasInit) {
            // SAFETY: These bytes were initialized but not filled in the previous loop
            readBuf.setInit()
        }
        val cursor = readBuf.unfilled()
        val result =
            while (true) {
                run {
                    val matchVal = r.readBuf(cursor.reborrow())
                    when {
                        matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                            val e = matchVal.error
                            continue
                        }
                        // Do not stop now in case of error: we might have received both data
                        // and an error
                        else -> {
                            val res = matchVal
                            break
                        }
                    }
                }
            }
        val bytesRead = cursor.written()
        val isInit = readBuf.isInit()
        if (isInit) {
            initUntil = buf.size + bufLen
        }
        // SAFETY: BorrowedBuf's invariants mean this much memory is initialized.
        run {
            val newLen = bytesRead + buf.size
            buf.setLen(newLen)
        }
        // Now that all data is pushed to the vector, we can fail without data loss
        when (val tryResult = result) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        if (bytesRead == 0) {
            return Result.Ok(buf.size - startLen)
        }
        // Use heuristics to determine the max read size if no initial size hint was provided
        if (sizeHint == null) {
            // The reader is returning short reads but it doesn't call ensureInit().
            // In that case we no longer need to restrict read sizes to avoid
            // initialization costs.
            // When reading from disk we usually don't get any short reads except at EOF.
            // So we wait for at least 2 short reads before uncapping the read buffer;
            // this helps with the Windows issue.
            if (!isInit) {
                maxReadSize = Int.MAX_VALUE
            } else if (bytesRead == maxReadSize) {
                maxReadSize = maxReadSize.saturatingMul(2)
            }
        }
    }
}

public fun <R : Read<R>> defaultReadToString(r: R, buf: String, sizeHint: Int?): Result<Int> {
    // Note that we do *not* call `r.readToEnd()` here. We are passing
    // `List<UByte>` (the raw contents of `buf`) into the `readToEnd`
    // method to fill it up. An arbitrary implementation could overwrite the
    // entire contents of the vector, not just append to it (which is what
    // we are expecting).
    //
    // To prevent extraneously checking the UTF-8-ness of the entire buffer
    // we pass it to our hardcoded `defaultReadToEnd` implementation which
    // we know is guaranteed to only read data into the end of the buffer.
    // ignore-tidy-undocumented-unchecked
    return appendToString(buf, { b -> defaultReadToEnd(r, b, sizeHint) })
}

public fun defaultReadVectored(read: (MutableList<UByte>) -> Result<Int>, bufs: MutableList<IoSliceMut>): Result<Int> {
    val buf = ((bufs.indexOf({ b -> !b.isEmpty() }).takeIf { it >= 0 })?.let({ b -> b }) ?: listOf())
    return read(buf)
}

public fun <R : Read<R>> defaultReadExact(thisRef: R, buf: MutableList<UByte>): Result<Unit> {
    var buf1 = buf
    while (!buf1.isEmpty()) {
        run {
            val matchVal = thisRef.read(buf1)
            when {
                matchVal is Result.Ok && matchVal.value == 0 -> { break }
                matchVal is Result.Ok -> {
                    val n = matchVal.value
                    buf1 = buf1.subList(n, buf1.size)
                }
                matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                    val e = matchVal.error
                }
                else -> {
                    val e1 = matchVal.error
                    return Result.Err(e1)
                }
            }
        }
    }
    return if (!buf1.isEmpty()) {
        Result.Err(Error.READ_EXACT_EOF)
    } else {
        Result.Ok(Unit)
    }
}

public fun defaultReadBuf(read: (MutableList<UByte>) -> Result<Int>, cursor: BorrowedCursor<UByte>): Result<Unit> {
    val n = when (val tryResult = read(cursor.ensureInit())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
    cursor.advanceChecked(n)
    return Result.Ok(Unit)
}

public fun <R : Read<R>> defaultReadBufExact(thisRef: R, cursor: BorrowedCursor<UByte>): Result<Unit> {
    while (cursor.capacity() > 0) {
        val prevWritten = cursor.written()
        run {
            val matchVal = thisRef.readBuf(cursor.reborrow())
            when {
                matchVal is Result.Ok -> {}
                matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                    val e = matchVal.error
                    continue
                }
                else -> {
                    val e1 = matchVal.error
                    return Result.Err(e1)
                }
            }
        }
        if (cursor.written() == prevWritten) {
            return Result.Err(Error.READ_EXACT_EOF)
        }
    }
    return Result.Ok(Unit)
}

// Once we can use associated consts in the types of method parameters, rewrite this to have
// `fromLeBytes` and `fromBeBytes` methods, move it to `core`, and make it public.

/**
 * Trait for types that can be converted from a fixed-size byte array with a specified endianness
 */
public interface FromEndianBytes<T> {
    public fun readLeFrom(r: Read): Result<T>

    public fun readBeFrom(r: Read): Result<T>
}

public fun fromEndianBytesReadLeFromUByte(r: Read): Result<UByte> {
    return Result.Ok((when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })[0])
}

public fun fromEndianBytesReadBeFromUByte(r: Read): Result<UByte> {
    return Result.Ok((when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })[0])
}

public fun fromEndianBytesReadLeFromUShort(r: Read): Result<UShort> {
    return Result.Ok(run { val bytesTmp1 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
}

public fun fromEndianBytesReadBeFromUShort(r: Read): Result<UShort> {
    return Result.Ok(run { val bytesTmp2 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp2[0].toInt() shl 8) or bytesTmp2[1].toInt()).toUShort() })
}

public fun fromEndianBytesReadLeFromUInt(r: Read): Result<UInt> {
    return Result.Ok(run { val bytesTmp3 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp3[3].toUInt() shl 24) or (bytesTmp3[2].toUInt() shl 16) or (bytesTmp3[1].toUInt() shl 8) or bytesTmp3[0].toUInt()) })
}

public fun fromEndianBytesReadBeFromUInt(r: Read): Result<UInt> {
    return Result.Ok(run { val bytesTmp4 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp4[0].toUInt() shl 24) or (bytesTmp4[1].toUInt() shl 16) or (bytesTmp4[2].toUInt() shl 8) or bytesTmp4[3].toUInt()) })
}

public fun fromEndianBytesReadLeFromULong(r: Read): Result<ULong> {
    return Result.Ok(run { val bytesTmp5 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp5[7].toULong() shl 56) or (bytesTmp5[6].toULong() shl 48) or (bytesTmp5[5].toULong() shl 40) or (bytesTmp5[4].toULong() shl 32) or (bytesTmp5[3].toULong() shl 24) or (bytesTmp5[2].toULong() shl 16) or (bytesTmp5[1].toULong() shl 8) or bytesTmp5[0].toULong()) })
}

public fun fromEndianBytesReadBeFromULong(r: Read): Result<ULong> {
    return Result.Ok(run { val bytesTmp6 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp6[0].toULong() shl 56) or (bytesTmp6[1].toULong() shl 48) or (bytesTmp6[2].toULong() shl 40) or (bytesTmp6[3].toULong() shl 32) or (bytesTmp6[4].toULong() shl 24) or (bytesTmp6[5].toULong() shl 16) or (bytesTmp6[6].toULong() shl 8) or bytesTmp6[7].toULong()) })
}

public fun fromEndianBytesReadLeFromU128(r: Read): Result<U128> {
    return Result.Ok(U128.fromLeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadBeFromU128(r: Read): Result<U128> {
    return Result.Ok(U128.fromBeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadLeFromInt(r: Read): Result<Int> {
    return Result.Ok(run { val bytesTmp7 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp7[3].toInt() shl 24) or (bytesTmp7[2].toInt() shl 16) or (bytesTmp7[1].toInt() shl 8) or bytesTmp7[0].toInt()) })
}

public fun fromEndianBytesReadBeFromInt(r: Read): Result<Int> {
    return Result.Ok(run { val bytesTmp8 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; val offTmp8 = bytesTmp8.size - 4; ((bytesTmp8[offTmp8].toInt() shl 24) or (bytesTmp8[offTmp8 + 1].toInt() shl 16) or (bytesTmp8[offTmp8 + 2].toInt() shl 8) or bytesTmp8[offTmp8 + 3].toInt()) })
}

public fun fromEndianBytesReadLeFromByte(r: Read): Result<Byte> {
    return Result.Ok((when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })[0].toByte())
}

public fun fromEndianBytesReadBeFromByte(r: Read): Result<Byte> {
    return Result.Ok((when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })[0].toByte())
}

public fun fromEndianBytesReadLeFromShort(r: Read): Result<Short> {
    return Result.Ok(run { val bytesTmp9 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp9[1].toInt() shl 8) or bytesTmp9[0].toInt()).toShort() })
}

public fun fromEndianBytesReadBeFromShort(r: Read): Result<Short> {
    return Result.Ok(run { val bytesTmp10 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp10[0].toInt() shl 8) or bytesTmp10[1].toInt()).toShort() })
}

public fun fromEndianBytesReadLeFromLong(r: Read): Result<Long> {
    return Result.Ok(run { val bytesTmp11 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp11[7].toLong() shl 56) or (bytesTmp11[6].toLong() shl 48) or (bytesTmp11[5].toLong() shl 40) or (bytesTmp11[4].toLong() shl 32) or (bytesTmp11[3].toLong() shl 24) or (bytesTmp11[2].toLong() shl 16) or (bytesTmp11[1].toLong() shl 8) or bytesTmp11[0].toLong()) })
}

public fun fromEndianBytesReadBeFromLong(r: Read): Result<Long> {
    return Result.Ok(run { val bytesTmp12 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; ((bytesTmp12[0].toLong() shl 56) or (bytesTmp12[1].toLong() shl 48) or (bytesTmp12[2].toLong() shl 40) or (bytesTmp12[3].toLong() shl 32) or (bytesTmp12[4].toLong() shl 24) or (bytesTmp12[5].toLong() shl 16) or (bytesTmp12[6].toLong() shl 8) or bytesTmp12[7].toLong()) })
}

public fun fromEndianBytesReadLeFromI128(r: Read): Result<I128> {
    return Result.Ok(I128.fromLeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadBeFromI128(r: Read): Result<I128> {
    return Result.Ok(I128.fromBeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadLeFromF16(r: Read): Result<F16> {
    return Result.Ok(F16.fromLeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadBeFromF16(r: Read): Result<F16> {
    return Result.Ok(F16.fromBeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadLeFromFloat(r: Read): Result<Float> {
    return Result.Ok(run { val bytesTmp13 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; Float.fromBits(((bytesTmp13[3].toInt() shl 24) or (bytesTmp13[2].toInt() shl 16) or (bytesTmp13[1].toInt() shl 8) or bytesTmp13[0].toInt())) })
}

public fun fromEndianBytesReadBeFromFloat(r: Read): Result<Float> {
    return Result.Ok(run { val bytesTmp14 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; Float.fromBits(((bytesTmp14[0].toInt() shl 24) or (bytesTmp14[1].toInt() shl 16) or (bytesTmp14[2].toInt() shl 8) or bytesTmp14[3].toInt())) })
}

public fun fromEndianBytesReadLeFromDouble(r: Read): Result<Double> {
    return Result.Ok(run { val bytesTmp15 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; Double.fromBits((bytesTmp15[7].toLong() shl 56) or (bytesTmp15[6].toLong() shl 48) or (bytesTmp15[5].toLong() shl 40) or (bytesTmp15[4].toLong() shl 32) or (bytesTmp15[3].toLong() shl 24) or (bytesTmp15[2].toLong() shl 16) or (bytesTmp15[1].toLong() shl 8) or bytesTmp15[0].toLong()) })
}

public fun fromEndianBytesReadBeFromDouble(r: Read): Result<Double> {
    return Result.Ok(run { val bytesTmp16 = when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }; Double.fromBits((bytesTmp16[0].toLong() shl 56) or (bytesTmp16[1].toLong() shl 48) or (bytesTmp16[2].toLong() shl 40) or (bytesTmp16[3].toLong() shl 32) or (bytesTmp16[4].toLong() shl 24) or (bytesTmp16[5].toLong() shl 16) or (bytesTmp16[6].toLong() shl 8) or bytesTmp16[7].toLong()) })
}

public fun fromEndianBytesReadLeFromF128(r: Read): Result<F128> {
    return Result.Ok(F128.fromLeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

public fun fromEndianBytesReadBeFromF128(r: Read): Result<F128> {
    return Result.Ok(F128.fromBeBytes(when (val tryResult = r.readArray()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }))
}

package io.github.kotlinmania.rustlang.alloc.io.buffered

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An error returned by [BufWriter.intoInner] which combines an error that
 * happened while writing out the buffer, and the buffered writer object
 * which may be used to recover from the condition.
 *
 * # Examples
 *
 * ```kotlin
 * import core.error
 * import std.io.BufWriter
 * import std.net.TcpStream
 *
 * var stream = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
 * // do stuff with the stream
 * // we want to get our `TcpStream` back, so let's try:
 * val stream1 = run {
 *     val matchVal = stream.intoInner()
 *     if (matchVal.isSuccess) {
 *         val s = matchVal.getOrThrow()
 *         s
 *     } else {
 *         val e = matchVal.exceptionOrNull()!!
 *         // Here, e is an IntoInnerError
 *         error("An error occurred")
 *     }
 * }
 * ```
 */
public open class IntoInnerError<W>(
    public var field0: W,
    public var field1: Error,
) {
    /**
     * Helper to construct a new IntoInnerError; intended to help with
     * adapters that wrap other adapters
     */
    public open fun <W2> newWrapped(f: (W) -> W2): IntoInnerError<W2> {
        val writer = this.field0
        val error = this.field1
        return IntoInnerError.new(f(writer), error)
    }

    /**
     * Returns the error which caused the call to [BufWriter.intoInner]
     * to fail.
     *
     * This error was returned when attempting to write the internal buffer.
     *
     * # Examples
     *
     * ```kotlin
     * import core.error
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * var stream = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // do stuff with the stream
     * // we want to get our `TcpStream` back, so let's try:
     * val stream1 = run {
     *     val matchVal = stream.intoInner()
     *     if (matchVal.isSuccess) {
     *         val s = matchVal.getOrThrow()
     *         s
     *     } else {
     *         val e = matchVal.exceptionOrNull()!!
     *         // Here, e is an IntoInnerError, let's log the inner error.
     *         //
     *         // We'll just `log` to stdout for this example.
     *         println("${e.error()}")
     *         error("An unexpected error occurred.")
     *     }
     * }
     * ```
     */
    public open fun error(): Error = this.field1

    /**
     * Returns the buffered writer instance which generated the error.
     *
     * The returned object can be used for error recovery, such as
     * re-inspecting the buffer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * var stream = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // do stuff with the stream
     * // we want to get our `TcpStream` back, so let's try:
     * val stream1 = run {
     *     val matchVal = stream.intoInner()
     *     if (matchVal.isSuccess) {
     *         val s = matchVal.getOrThrow()
     *         s
     *     } else {
     *         val e = matchVal.exceptionOrNull()!!
     *         // Here, e is an IntoInnerError, let's re-examine the buffer:
     *         val buffer = e.intoInner()
     *         // do stuff to try to recover
     *         // afterwards, let's just return the stream
     *         buffer.intoInner().unwrap()
     *     }
     * }
     * ```
     */
    public open fun intoInner(): W = this.field0

    /**
     * Consumes the [IntoInnerError] and returns the error which caused the call to
     * [BufWriter.intoInner] to fail.  Unlike `error`, this can be used to
     * obtain ownership of the underlying error.
     *
     * # Example
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import std.io.BufWriter
     * import std.io.ErrorKind
     *
     * public fun doctestEntry() {
     *     var notEnoughSpace = UByteArray(10) { 0u.toUByte() }
     *     var stream = BufWriter.new(notEnoughSpace.asMut())
     *     stream.append("this cannot be actually written").unwrap()
     *     val intoInnerErr = stream.intoInner().expectErr("now we discover it's too small")
     *     val err = intoInnerErr.intoError()
     *     check(err.kind() == ErrorKind.WriteZero)
     * }
     * ```
     */
    public open fun intoError(): Error = this.field1

    /**
     * Consumes the [IntoInnerError] and returns the error which caused the call to
     * [BufWriter.intoInner] to fail, and the underlying writer.
     *
     * This can be used to simply obtain ownership of the underlying error; it can also be used for
     * advanced error recovery.
     *
     * # Example
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import std.io.BufWriter
     * import std.io.ErrorKind
     *
     * public fun doctestEntry() {
     *     var notEnoughSpace = UByteArray(10) { 0u.toUByte() }
     *     var stream = BufWriter.new(notEnoughSpace.asMut())
     *     stream.append("this cannot be actually written").unwrap()
     *     val intoInnerErr = stream.intoInner().expectErr("now we discover it's too small")
     *     val (err, recoveredWriter) = intoInnerErr.intoParts()
     *     check(err.kind() == ErrorKind.WriteZero)
     *     check(recoveredWriter.buffer() == "t be actually written".encodeToByteArray())
     * }
     * ```
     */
    public open fun intoParts(): Pair<Error, W> = Pair(this.field1, this.field0)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.error().fmt(f)

    public companion object {
        /**
         * Constructs a new IntoInnerError
         */
        public fun <W> new(writer: W, error: Error): IntoInnerError<W> = IntoInnerError(writer, error)
    }
}

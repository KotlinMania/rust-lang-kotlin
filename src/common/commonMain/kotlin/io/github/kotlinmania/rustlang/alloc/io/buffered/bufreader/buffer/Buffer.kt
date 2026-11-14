// port-lint: source io/buffered/bufreader/buffer.rs
package io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader.buffer

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.io.constError
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.result.Result

// An encapsulation of `BufReader`s buffer management logic.
//
// This module factors out the basic functionality of `BufReader` in order to protect two core
// invariants:
// * `filled` bytes of `buf` are always initialized
// * `pos` is always <= `filled`
// Since this module encapsulates the buffer management logic, we can ensure that the range
// `pos .. filled` is always a valid index into the initialized region of the buffer. This means
// that user code which wants to do reads from a `BufReader` via `buffer` + `consume` can do so
// without encountering any runtime bounds checks.

public open class Buffer internal constructor(
    bufCtorArg: List<MaybeUninit<UByte>>,
    // The current seek offset into `buf`, must always be <= `filled`.
    public var pos: Int,
    // Each call to `fillBuf` sets `filled` to indicate how many bytes at the start of `buf` are
    // initialized with bytes from a read.
    public var filled: Int,
    // Whether `buf` has been fully initialized. We track this so that we can accurately tell
    // `readBuf` how many bytes of buf are initialized, to bypass as much of its defensive
    // initialization as possible. Calls to `fillBuf` are not required to actually fill the buffer,
    // and omitting this is a huge perf regression for `Read` impls that do not.
    public var initialized: Boolean,
) {
    // The buffer.
    public var buf: List<MaybeUninit<UByte>> = bufCtorArg
        internal set

    public open fun buffer(): List<UByte> {
        // SAFETY: self.pos and self.filled are valid, and self.filled >= self.pos, and
        // that region is initialized because those are all invariants of this type.
        return this.buf.getUnchecked(this.pos until this.filled).assumeInitRef()
    }

    public open fun capacity(): Int = this.buf.size

    public open fun filled(): Int = this.filled

    public open fun pos(): Int = this.pos

    // This is only used by a test which asserts that the initialization-tracking is correct.
    public open fun initialized(): Boolean = this.initialized

    public open fun discardBuffer() {
        this.pos = 0
        this.filled = 0
    }

    public open fun consume(amt: Int) {
        this.pos = minOf(this.pos + amt, this.filled)
    }

    /**
     * If there are `amt` bytes available in the buffer, pass a slice containing those bytes to
     * `visitor` and return true. If there are not enough bytes available, return false.
     */
    public open fun consumeWith(amt: Int, visitor: (List<UByte>) -> Unit): Boolean =
        when (val matchVal = this.buffer().get(0 until amt)) {
            null -> {
                false
            }
            else -> {
                val claimed = matchVal
                visitor(claimed)
                // If the indexing into self.buffer() succeeds, amt must be a valid increment.
                this.pos += amt
                true
            }
        }

    public open fun unconsume(amt: Int) {
        this.pos = rustSaturatingSub(this.pos, amt)
    }

    /**
     * Read more bytes into the buffer without discarding any of its contents
     */
    public open fun readMore(reader: Read): Result<Int> {
        val buf = BorrowedBuf.from(((this.buf).slice(this.filled until this.buf.size)))
        if (this.initialized) {
            // SAFETY: `self.initialized` is only set after `self.buf` was
            // fully initialized, and once `self.buf` is fully initialized
            // no part will become uninitialized.
            buf.setInit()
        }
        when (val tryResult = reader.readBuf(buf.unfilled())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        this.filled += buf.len()
        this.initialized = buf.isInit()
        return Result.Ok(buf.len())
    }

    /**
     * Remove bytes that have already been read from the buffer.
     */
    public open fun backshift() {
        this.buf.copyWithin(this.pos until this.filled, 0)
        this.filled -= this.pos
        this.pos = 0
    }

    public open fun fillBuf(reader: Read): Result<List<UByte>> {
        // If we've reached the end of our internal buffer then we need to fetch
        // some more data from the reader.
        // Branch using `>=` instead of the more correct `==`
        // to tell the compiler that the pos..cap slice is always valid.
        if (this.pos >= this.filled) {
            check(this.pos == this.filled)
            val buf = BorrowedBuf.from(this.buf)
            if (this.initialized) {
                // SAFETY: `self.initialized` is only set after `self.buf` was
                // fully initialized, and once `self.buf` is fully initialized
                // no part will become uninitialized.
                buf.setInit()
            }
            val result = reader.readBuf(buf.unfilled())
            this.pos = 0
            this.filled = buf.len()
            this.initialized = buf.isInit()
            when (val tryResult = result) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        return Result.Ok(this.buffer())
    }

    public companion object {
        public operator fun invoke(
            buf: List<MaybeUninit<UByte>>,
            pos: Int,
            filled: Int,
            initialized: Boolean,
        ): Buffer = Buffer(
            bufCtorArg = buf,
            pos = pos,
            filled = filled,
            initialized = initialized,
        )

        public fun withCapacity(capacity: Int): Buffer {
            val buf = Box.newUninitSlice(capacity)
            return Buffer(buf = buf, pos = 0, filled = 0, initialized = false)
        }

        public fun tryWithCapacity(capacity: Int): Result<Buffer> =
            run {
                val matchVal = Box.tryNewUninitSlice(capacity)
                when (matchVal) { is Result.Ok<*> -> {
                    val buf = matchVal.value
                    Result.Ok(Buffer(buf = buf, pos = 0, filled = 0, initialized = false))
                }; is Result.Err<*> -> {
                    Result.Err(constError(ErrorKind.OutOfMemory, "failed to allocate read buffer"))
                } }
            }
    }
}

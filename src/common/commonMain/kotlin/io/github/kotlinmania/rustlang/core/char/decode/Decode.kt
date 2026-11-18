// port-lint: source char/decode.rs
package io.github.kotlinmania.rustlang.core.char.decode

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.divCeil
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen

// UTF-8 and UTF-16 decoding iterators

/**
 * An iterator that decodes UTF-16 encoded code points from an iterator of `UShort`s.
 *
 * This `class` is created by the [decodeUtf16] method on [char]. See its
 * documentation for more.
 *
 * [decodeUtf16]: char.decodeUtf16
 */
public open class DecodeUtf16<I, Item>(
    public var iter: I,
    public var buf: UShort?,
) : Iterable<Result<Char, DecodeUtf16Error>> where I : Iterator<UShort> {
    override fun iterator(): Iterator<Result<Char, DecodeUtf16Error>> = object : AbstractIterator<Result<Char, DecodeUtf16Error>>() {
        override fun computeNext() {
            when (val v = this@DecodeUtf16.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): DecodeUtf16<I, Item> = DecodeUtf16(iter, buf)

    public open fun next(): Result<Char, DecodeUtf16Error>? {
        val u =
            when (val matchVal = this.buf.also { this.buf = null }) {
                null -> when (val tryResult = this.iter.next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                else -> {
                    val buf = matchVal
                    buf
                }
            }
        return if (!u.isUtf16Surrogate()) {
            // SAFETY: not a surrogate
            Result.Ok(Char.fromU32Unchecked(u.toUInt()))
        } else if (u >= 0xDC00) {
            // a trailing surrogate
            Result.Err(DecodeUtf16Error(code = u))
        } else {
            val u21 =
                when (val matchVal1 = this.iter.next()) {
                    // eof
                    null -> { return Result.Err(DecodeUtf16Error(code = u)) }
                    else -> {
                        val u2 = matchVal1
                        u2
                    }
                }
            if (u21 < 0xDC00 || u21 > 0xDFFF) {
                // not a trailing surrogate so we're not a valid
                // surrogate pair, so rewind to redecode u2 next time.
                this.buf = u21
                return Result.Err(DecodeUtf16Error(code = u))
            }
            // all ok, so lets decode it.
            val c = (((u and 0x3ff).toUInt()) shl 10 or (u21 and 0x3ff).toUInt()) + 0x10000u
            // SAFETY: we checked that it's a legal unicode value
            Result.Ok(Char.fromU32Unchecked(c))
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (low, high) = this.iter.sizeHint()
        val (lowBuf, highBuf) =
            run {
                val matchVal = this.buf
                when {
                    // buf is empty, no additional elements from it.
                    matchVal == null -> Pair(0, 0)
                    // `u` is a non surrogate, so it's always an additional character.
                    matchVal != null && !matchVal.isUtf16Surrogate() -> {
                        val u = matchVal
                        Pair(1, 1)
                    }
                    // `u` is a leading surrogate (it can never be a trailing surrogate and
                    // it's a surrogate due to the previous branch) and `self.iter` is empty.
                    //
                    // `u` can't be paired, since the `self.iter` is empty,
                    // so it will always become an additional element (error).
                    matchVal != null && high == 0 -> Pair(1, 1)
                    // `u` is a leading surrogate and `iter` may be non-empty.
                    //
                    // `u` can either pair with a trailing surrogate, in which case no additional elements
                    // are produced, or it can become an error, in which case it's an additional character (error).
                    else -> Pair(0, 1)
                }
            }
        // `self.iter` could contain entirely valid surrogates (2 elements per
        // char), or entirely non-surrogates (1 element per char).
        //
        // On odd lower bound, at least one element must stay unpaired
        // (with other elements from `self.iter`), so we round up.
        val low1 = low.divCeil(2) + lowBuf
        val high1 = high.andThen({ h -> h.checkedAdd(highBuf) })
        return Pair(low1, high1)
    }
}

/**
 * An error that can be returned when decoding UTF-16 code points.
 *
 * This `class` is created when using the [DecodeUtf16] type.
 */
public open class DecodeUtf16Error(
    public var code: UShort,
) {
    public open fun clone(): DecodeUtf16Error = DecodeUtf16Error(code)

    /**
     * Returns the unpaired surrogate which caused this error.
     */
    public open fun unpairedSurrogate(): UShort = this.code

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append("unpaired surrogate found: ${this.code}"); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<DecodeUtf16Error> =
            object : TypeLayout<DecodeUtf16Error> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): DecodeUtf16Error =
                    DecodeUtf16Error(
                        (PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DecodeUtf16Error) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.code)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DecodeUtf16Error =
                    DecodeUtf16Error(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DecodeUtf16Error) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.code)
                }
            }
    }
}

/**
 * Creates an iterator over the UTF-16 encoded code points in `iter`,
 * returning unpaired surrogates as `Err`s. See [char.decodeUtf16].
 */
public fun <I : Iterable<UShort>> decodeUtf16(iter: I): DecodeUtf16<IntoIter, Item> = DecodeUtf16(iter = iter.iterator(), buf = null)

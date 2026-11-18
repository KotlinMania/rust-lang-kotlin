// port-lint: source num/imp/fmt.rs
package io.github.kotlinmania.rustlang.core.num.imp.fmt

import io.github.kotlinmania.rustlang.core.num.checkedIlog10
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.slice.iterMut
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

// Shared utilities used by both float and integer formatting.

/**
 * Formatted parts.
 */
public sealed class Part {
    /**
     * Given number of zero digits.
     */
    public data class Zero(public var value: Int) : Part()
    /**
     * A literal number up to 5 digits.
     */
    public data class Num(public var value: UShort) : Part()
    /**
     * A verbatim copy of given bytes.
     */
    public data class Copy(public var value: List<UByte>) : Part()

    public fun rustEquals(other: Part): Boolean = this == other

    /**
     * Returns the exact byte length of given part.
     */
    public open fun len(): Int =
        when (this) {
            is Part.Zero -> {
                val (nzeroes) = this as Part.Zero
                nzeroes
            }
            is Part.Num -> {
                val (v) = this as Part.Num
                v.checkedIlog10().unwrapOrDefault().toInt() + 1
            }
            is Part.Copy -> {
                val (buf) = this as Part.Copy
                buf.size
            }
        }

    /**
     * Writes a part into the supplied buffer.
     * Returns the number of written bytes, or `None` if the buffer is not enough.
     * (It may still leave partially written bytes in the buffer; do not rely on that.)
     */
    public open fun write(out: MutableList<UByte>): Int? {
        val len = this.len()
        return if (out.size >= len) {
            when (this) {
                is Part.Zero -> {
                    val (nzeroes) = this as Part.Zero
                    for (idx in ((out).subList(0, nzeroes)).indices) {
                        ((out).subList(0, nzeroes))[idx] = 48u.toUByte()
                    }
                }
                is Part.Num -> {
                    val (v) = this as Part.Num
                    for (c in ((out).subList(0, len)).iterMut().rev()) {
                        c = 48u.toUByte() + (v % 10u.toUShort()).toUByte()
                        v /= 10u.toUShort()
                    }
                }
                is Part.Copy -> {
                    val (buf) = this as Part.Copy
                    run { val dstTmp0 = out; val srcTmp0 = buf; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
                }
            }
            len
        } else {
            null
        }
    }
}

/**
 * Formatted result containing one or more parts.
 * This can be written to the byte buffer or converted to the allocated string.
 */
public open class Formatted(
    /**
     * A byte slice representing a sign, either `""`, `"-"` or `"+"`.
     */
    public var sign: String,
    /**
     * Formatted parts to be rendered after a sign and optional zero padding.
     */
    public var parts: List<Part>,
) {
    public open fun clone(): Formatted = Formatted(sign, parts)

    /**
     * Returns the byte length of combined formatted result.
     *
     * Saturates at `Int.MAX` if the actual length is larger.
     *
     * This matters on 16-bit targets, where exponential formatting can exceed
     * `Int.MAX` by emitting `UShort.MAX` trailing zeroes plus `"1."` / `"e0"`.
     */
    public open fun len(): Int = this.parts.fold(this.sign.length, { len, part -> len.saturatingAdd(part.len()) })

    /**
     * Writes all formatted parts into the supplied buffer.
     * Returns the number of written bytes, or `None` if the buffer is not enough.
     * (It may still leave partially written bytes in the buffer; do not rely on that.)
     */
    public open fun write(out: MutableList<UByte>): Int? {
        (out.getOrNull(0 until this.sign.length) ?: return null).copyFromSlice(this.sign.encodeToByteArray().map { it.toUByte() })
        var written = this.sign.length
        for (part in this.parts) {
            val len = (part.write(out.subList(written, out.size)) ?: return null)
            written += len
        }
        return written
    }

    public companion object
}

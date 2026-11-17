package io.github.kotlinmania.rustlang.alloc.wtf8

import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.wtf8.Wtf8

/**
 * Creates an owned `Wtf8Buf` from a borrowed `Wtf8`.
 */
public fun toOwned(slice: Wtf8): Wtf8Buf = Wtf8Buf(bytes = slice.asBytes().toMutableList(), isKnownUtf8 = false)

/**
 * Lossily converts the string to UTF-8.
 * Returns a UTF-8 `String` slice if the contents are well-formed in UTF-8.
 *
 * Surrogates are replaced with `"\u(FFFD)"` (the replacement character “�”).
 *
 * This only copies the data if necessary (if it contains any surrogate).
 */
public fun toStringLossy(slice: Wtf8): String {
    val (surrogatePos, _) = slice.nextSurrogate(0) ?: // SAFETY: `nextSurrogate` found no surrogate, so the well-formed WTF-8
// bytes are valid UTF-8.
return String.fromUtf8Unchecked(slice.asBytes())
    val wtf8Bytes = slice.asBytes()
    val utf8Bytes = ArrayList(slice.len())
    utf8Bytes.addAll(((wtf8Bytes).subList(0, surrogatePos)))
    utf8Bytes.addAll(("�").encodeToByteArray().map { it.toUByte() })
    var pos = surrogatePos + 3
    while (true) {
        when (val matchVal = slice.nextSurrogate(pos)) {
            null -> {
                utf8Bytes.addAll(((wtf8Bytes).subList(pos, (wtf8Bytes).size)))
                // SAFETY: Every surrogate was replaced with `"\u(FFFD)"`,
                // and the remaining bytes are valid UTF-8, so `utf8Bytes` is valid UTF-8.
                return String.fromUtf8Unchecked(utf8Bytes)
            }
            else -> {
                val surrogatePos1 = matchVal.first
                utf8Bytes.addAll(((wtf8Bytes).subList(pos, surrogatePos1)))
                utf8Bytes.addAll(("�").encodeToByteArray().map { it.toUByte() })
                pos = surrogatePos1 + 3
            }
        }
    }
}

public fun cloneInto(slice: Wtf8, buf: Wtf8Buf) {
    buf.isKnownUtf8 = false
    slice.asBytes().cloneInto(buf.bytes)
}

public fun decodeSurrogatePair(lead: UShort, trail: UShort): Char {
    val codePoint = 0x10000u + ((((lead - 0xD800u)) shl 10) or (trail - 0xDC00u))
    // SAFETY: The computed `codePoint` is in 0x10000..=0x10FFFF and is not a surrogate.
    return Char.fromU32Unchecked(codePoint)
}

public fun Wtf8.toOwned(): Wtf8Buf = toOwned(this)

public fun Wtf8.cloneInto(buf: Wtf8Buf) {
    cloneInto(this, buf)
}

public fun Wtf8.toStringLossy(): String = toStringLossy(this)

public fun Wtf8.intoBox(): Wtf8 {
    val boxed: List<UByte> = this.asBytes().into()
    // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`, and `boxed` is
    // a `Holder<[UByte]>`. Therefore, transmuting `Holder<[UByte]>` to `Holder<Wtf8>` is safe.
    return transmuteWithLayouts<List<UByte>, Wtf8>(boxed, UnresolvedTypeLayout<List<UByte>>(), UnresolvedTypeLayout<Wtf8>())
}

public fun Wtf8.Companion.emptyBox(): Wtf8 {
    val boxed: List<UByte> = emptyList()
    // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`, and `boxed` is
    // an empty `Holder<[UByte]>`. Therefore, transmuting it to `Holder<Wtf8>` is safe.
    return transmuteWithLayouts<List<UByte>, Wtf8>(boxed, UnresolvedTypeLayout<List<UByte>>(), UnresolvedTypeLayout<Wtf8>())
}

public fun Wtf8.intoArc(): Wtf8 {
    val arc: List<UByte> = Arc.from(this.asBytes())
    // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`.
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

public fun Wtf8.intoRc(): Wtf8 {
    val rc: List<UByte> = Rc.from(this.asBytes())
    // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`.
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

public fun Wtf8.toAsciiLowercase(): Wtf8Buf = Wtf8Buf(bytes = this.asBytes().toAsciiLowercase(), isKnownUtf8 = false)

public fun Wtf8.toAsciiUppercase(): Wtf8Buf = Wtf8Buf(bytes = this.asBytes().toAsciiUppercase(), isKnownUtf8 = false)

// rust-kotlin: manual
// port-lint: source string.rs
package io.github.kotlinmania.rustlang.alloc.string

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.fromRawParts
import io.github.kotlinmania.rustlang.alloc.vec.intoRawParts
import io.github.kotlinmania.rustlang.alloc.vec.new
import io.github.kotlinmania.rustlang.alloc.vec.tryWithCapacity
import io.github.kotlinmania.rustlang.alloc.vec.withCapacity
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.char.RustChar
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.convert.AsMut
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.intrinsics.KmForgettableResource
import io.github.kotlinmania.rustlang.core.intrinsics.threeWayCompare
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.kmNormalizeUByteList
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.fromUtf8 as coreFromUtf8
import io.github.kotlinmania.rustlang.core.str.fromUtf8Lossy as coreFromUtf8Lossy
import io.github.kotlinmania.rustlang.core.str.fromUtf8Unchecked as coreFromUtf8Unchecked

/**
 * A UTF-8 encoded, growable string.
 */
public open class RustAllocString(
    vec: List<UByte> = emptyList(),
) : DerefPure,
    Borrow<String>,
    BorrowMut<String>,
    AsRef<String>,
    AsMut<RustAllocString>,
    Drop,
    KmForgettableResource {
    private var bytesBuf: Vec<UByte, Global> = Vec.fromList(kmNormalizeUByteList(vec), PrimitiveLayouts.U8)

    internal constructor(backing: Vec<UByte, Global>) : this(emptyList()) {
        this.bytesBuf.drop()
        this.bytesBuf = backing
    }

    public var vec: List<UByte>
        get() = this.bytesBuf.asSlice()
        set(value) {
            this.bytesBuf.clear()
            this.bytesBuf.extendFromSlice(kmNormalizeUByteList(value))
        }

    public override fun close() {
        this.drop()
    }

    public override fun disarmCleaner() {
        this.bytesBuf.disarmCleaner()
    }

    public override fun drop() {
        this.bytesBuf.drop()
    }

    public open fun asStr(): String = coreFromUtf8Unchecked(this.bytesBuf.asSlice())

    public open fun asBytes(): List<UByte> = this.bytesBuf.asSlice()

    public open fun asPtr(): ConstPtr = this.bytesBuf.asPtr()

    public open fun asMutPtr(): MutPtr = this.bytesBuf.asMutPtr()

    public open fun asMutVec(): Vec<UByte, Global> = this.bytesBuf

    public open fun intoBytes(): Vec<UByte, Global> {
        val out = this.bytesBuf
        this.bytesBuf = Vec.new(PrimitiveLayouts.U8)
        return out
    }

    public open fun intoRawParts(): Triple<MutPtr, Int, Int> = this.intoBytes().intoRawParts()

    public open fun len(): Int = this.bytesBuf.len()

    public open fun isEmpty(): Boolean = this.bytesBuf.isEmpty()

    public open fun capacity(): Int = this.bytesBuf.capacity()

    public open fun reserve(additional: Int) {
        this.bytesBuf.reserve(additional)
    }

    public open fun reserveExact(additional: Int) {
        this.bytesBuf.reserveExact(additional)
    }

    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> =
        this.bytesBuf.tryReserve(additional)

    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> =
        this.bytesBuf.tryReserveExact(additional)

    public open fun shrinkToFit() {
        this.bytesBuf.shrinkToFit()
    }

    public open fun shrinkTo(minCapacity: Int) {
        this.bytesBuf.shrinkTo(minCapacity)
    }

    public open fun isCharBoundary(index: Int): Boolean {
        val total = this.len()
        if (index == 0 || index == total) return true
        if (index !in 0..total) return false
        return (this.bytesBuf.get(index).toInt() and 0xC0) != 0x80
    }

    public open fun pushStr(string: String) {
        val utf8 = string.encodeToByteArray()
        if (utf8.isEmpty()) return
        this.bytesBuf.reserve(utf8.size)
        for (b in utf8) {
            this.bytesBuf.push(b.toUByte())
        }
    }

    public open fun push(ch: Char) {
        this.pushStr(ch.toString())
    }

    public open fun extendFromWithin(src: IntRange) {
        val start = src.first
        val endExclusive = if (src.isEmpty()) src.first else src.last + 1
        check(this.isCharBoundary(start) && this.isCharBoundary(endExclusive)) {
            "range $start..<$endExclusive is not on UTF-8 char boundaries"
        }
        this.bytesBuf.extendFromWithin(src)
    }

    public open fun truncate(newLen: Int) {
        if (newLen >= this.len()) return
        check(this.isCharBoundary(newLen)) { "byte index $newLen is not a char boundary" }
        this.bytesBuf.truncate(newLen)
    }

    private fun decodeUtf8Scalar(bytes: List<UByte>): UInt =
        when (bytes.size) {
            1 -> bytes[0].toUInt()
            2 -> ((bytes[0].toUInt() and 0x1Fu) shl 6) or (bytes[1].toUInt() and 0x3Fu)
            3 ->
                ((bytes[0].toUInt() and 0x0Fu) shl 12) or
                    ((bytes[1].toUInt() and 0x3Fu) shl 6) or
                    (bytes[2].toUInt() and 0x3Fu)
            4 ->
                ((bytes[0].toUInt() and 0x07u) shl 18) or
                    ((bytes[1].toUInt() and 0x3Fu) shl 12) or
                    ((bytes[2].toUInt() and 0x3Fu) shl 6) or
                    (bytes[3].toUInt() and 0x3Fu)
            else -> error("Invalid UTF-8 character byte sequence of length ${bytes.size}")
        }

    public open fun pop(): RustChar? {
        val curLen = this.len()
        if (curLen == 0) return null
        var idx = curLen - 1
        while (idx > 0 && !this.isCharBoundary(idx)) {
            idx -= 1
        }
        val slice = this.bytesBuf.asSlice().subList(idx, curLen)
        val cp = decodeUtf8Scalar(slice)
        this.bytesBuf.truncate(idx)
        return RustChar(cp)
    }

    public open fun remove(idx: Int): Char {
        val curLen = this.len()
        check(idx in 0 until curLen && this.isCharBoundary(idx)) {
            "cannot remove a char from byte index $idx"
        }
        var end = idx + 1
        while (end < curLen && !this.isCharBoundary(end)) {
            end += 1
        }
        val removed = coreFromUtf8Unchecked(this.bytesBuf.asSlice().subList(idx, end))
        val tail = this.bytesBuf.splitOff(end)
        this.bytesBuf.truncate(idx)
        this.bytesBuf.extendFromSlice(tail.asSlice())
        tail.drop()
        return removed[0]
    }

    public open fun insert(
        idx: Int,
        ch: Char,
    ) {
        this.insertStr(idx, ch.toString())
    }

    public open fun insertStr(
        idx: Int,
        string: String,
    ) {
        check(this.isCharBoundary(idx)) { "byte index $idx is not a char boundary" }
        val tail = this.bytesBuf.splitOff(idx)
        this.pushStr(string)
        this.bytesBuf.extendFromSlice(tail.asSlice())
        tail.drop()
    }

    public open fun retain(f: (RustChar) -> Boolean) {
        val raw = this.asBytes()
        val kept = mutableListOf<UByte>()
        var idx = 0
        while (idx < raw.size) {
            val lead = raw[idx].toInt()
            val width =
                when {
                    (lead and 0x80) == 0 -> 1
                    (lead and 0xE0) == 0xC0 -> 2
                    (lead and 0xF0) == 0xE0 -> 3
                    else -> 4
                }
            val end = (idx + width).coerceAtMost(raw.size)
            val chBytes = raw.subList(idx, end)
            val ch = RustChar(decodeUtf8Scalar(chBytes))
            if (f(ch)) {
                kept.addAll(chBytes)
            }
            idx = end
        }
        this.clear()
        this.bytesBuf.extendFromSlice(kept)
    }

    public open fun splitOff(at: Int): RustAllocString {
        check(this.isCharBoundary(at)) { "byte index $at is not a char boundary" }
        return RustAllocString(this.bytesBuf.splitOff(at))
    }

    public open fun clear() {
        this.bytesBuf.clear()
    }

    public open fun replaceRange(
        range: IntRange,
        replaceWith: String,
    ) {
        val start = range.first
        val endExclusive = if (range.isEmpty()) range.first else range.last + 1
        check(this.isCharBoundary(start) && this.isCharBoundary(endExclusive)) {
            "range $start..<$endExclusive is not on UTF-8 char boundaries"
        }
        val after = this.bytesBuf.splitOff(endExclusive)
        this.bytesBuf.truncate(start)
        this.pushStr(replaceWith)
        this.bytesBuf.extendFromSlice(after.asSlice())
        after.drop()
    }

    public open fun intoBoxedStr(): String {
        val result = this.asStr()
        this.drop()
        return result
    }

    public open fun leak(): String {
        val result = this.asStr()
        this.disarmCleaner()
        return result
    }

    public open fun clone(): RustAllocString = RustAllocString(this.asBytes())

    public open fun cloneFrom(source: RustAllocString) {
        this.bytesBuf.cloneFrom(source.bytesBuf)
    }

    public open fun eq(other: RustAllocString): Boolean = this.bytesBuf.eq(other.bytesBuf)

    public open fun ne(other: RustAllocString): Boolean = !this.eq(other)

    public open fun partialCmp(other: RustAllocString): Ordering? = this.cmp(other)

    public open fun cmp(other: RustAllocString): Ordering = threeWayCompare(this.asStr(), other.asStr())

    public override fun equals(other: Any?): Boolean =
        other is RustAllocString && this.asBytes() == other.asBytes()

    public override fun hashCode(): Int = this.asBytes().hashCode()

    public override fun toString(): String = this.asStr()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        f.append(this.asStr())
        return Result.Ok(Unit)
    }

    public open fun writeStr(s: String): Result<Unit, Unit> {
        this.pushStr(s)
        return Result.Ok(Unit)
    }

    public open fun writeChar(c: Char): Result<Unit, Unit> {
        this.push(c)
        return Result.Ok(Unit)
    }

    public open fun deref(): String = this.asStr()

    public override fun borrow(): String = this.asStr()

    public override fun borrowMut(): String = this.asStr()

    public override fun asRef(): String = this.asStr()

    public override fun asMut(): RustAllocString = this

    public companion object {
        public fun new(): RustAllocString = RustAllocString()

        public fun withCapacity(capacity: Int): RustAllocString =
            RustAllocString(Vec.withCapacity(capacity, PrimitiveLayouts.U8))

        public fun tryWithCapacity(capacity: Int): Result<RustAllocString, TryReserveError> =
            when (val res = Vec.tryWithCapacity(capacity, PrimitiveLayouts.U8)) {
                is Result.Ok -> Result.Ok(RustAllocString(res.value))
                is Result.Err -> Result.Err(res.value)
            }

        public fun from(s: String): RustAllocString {
            val out = withCapacity(s.encodeToByteArray().size)
            out.pushStr(s)
            return out
        }

        public fun fromUtf8(vec: List<UByte>): Result<RustAllocString, FromUtf8Error> {
            val normalized = kmNormalizeUByteList(vec)
            return when (val res = coreFromUtf8(normalized)) {
                is Result.Ok -> Result.Ok(RustAllocString(normalized))
                is Result.Err -> Result.Err(FromUtf8Error(bytes = normalized, error = res.value))
            }
        }

        public fun fromUtf8Lossy(v: List<UByte>): Cow<String, RustAllocString> {
            val normalized = kmNormalizeUByteList(v)
            return when (val res = coreFromUtf8(normalized)) {
                is Result.Ok -> Cow.Borrowed(res.value)
                is Result.Err -> Cow.Owned(RustAllocString.from(coreFromUtf8Lossy(normalized)))
            }
        }

        public fun fromUtf8LossyOwned(v: List<UByte>): RustAllocString =
            RustAllocString.from(coreFromUtf8Lossy(kmNormalizeUByteList(v)))

        public fun fromUtf8Unchecked(bytes: List<UByte>): RustAllocString =
            RustAllocString(kmNormalizeUByteList(bytes))

        public fun fromUtf16(v: List<UShort>): Result<RustAllocString, FromUtf16Error> {
            val sb = StringBuilder(v.size)
            var idx = 0
            while (idx < v.size) {
                val unit = v[idx].toInt()
                when {
                    unit in 0xD800..0xDBFF -> {
                        if (idx + 1 >= v.size) {
                            return Result.Err(FromUtf16Error())
                        }
                        val low = v[idx + 1].toInt()
                        if (low !in 0xDC00..0xDFFF) {
                            return Result.Err(FromUtf16Error())
                        }
                        sb.append(unit.toChar())
                        sb.append(low.toChar())
                        idx += 2
                    }
                    unit in 0xDC00..0xDFFF -> return Result.Err(FromUtf16Error())
                    else -> {
                        sb.append(unit.toChar())
                        idx += 1
                    }
                }
            }
            return Result.Ok(RustAllocString.from(sb.toString()))
        }

        public fun fromUtf16Lossy(v: List<UShort>): RustAllocString {
            val sb = StringBuilder(v.size)
            var idx = 0
            while (idx < v.size) {
                val unit = v[idx].toInt()
                when {
                    unit in 0xD800..0xDBFF -> {
                        if (idx + 1 < v.size) {
                            val low = v[idx + 1].toInt()
                            if (low in 0xDC00..0xDFFF) {
                                sb.append(unit.toChar())
                                sb.append(low.toChar())
                                idx += 2
                                continue
                            }
                        }
                        sb.append('\uFFFD')
                        idx += 1
                    }
                    unit in 0xDC00..0xDFFF -> {
                        sb.append('\uFFFD')
                        idx += 1
                    }
                    else -> {
                        sb.append(unit.toChar())
                        idx += 1
                    }
                }
            }
            return RustAllocString.from(sb.toString())
        }

        public fun fromRawParts(
            buf: MutPtr,
            length: Int,
            capacity: Int,
        ): RustAllocString =
            RustAllocString(Vec.fromRawParts(buf, length, capacity, PrimitiveLayouts.U8))

        public fun default(): RustAllocString = RustAllocString()
    }
}

/**
 * A possible error value when converting a `RustAllocString` from a UTF-8 byte vector.
 */
public open class FromUtf8Error(
    public var bytes: List<UByte>,
    public var error: Utf8Error,
) {
    public open fun asBytes(): List<UByte> = this.bytes

    public open fun intoBytes(): List<UByte> = this.bytes

    public open fun utf8Error(): Utf8Error = this.error

    public open fun clone(): FromUtf8Error = FromUtf8Error(this.bytes.toList(), this.error.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        f.append("invalid utf-8 sequence of bytes from index ${this.error.validUpTo}")
        return Result.Ok(Unit)
    }

    public override fun equals(other: Any?): Boolean =
        other is FromUtf8Error && this.bytes == other.bytes && this.error == other.error

    public override fun hashCode(): Int = 31 * this.bytes.hashCode() + this.error.hashCode()

    public companion object
}

/**
 * A possible error value when converting a `RustAllocString` from a UTF-16 byte slice.
 */
public open class FromUtf16Error {
    public open fun clone(): FromUtf16Error = FromUtf16Error()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        f.append("invalid utf-16: lone surrogate found")
        return Result.Ok(Unit)
    }

    public override fun equals(other: Any?): Boolean = other is FromUtf16Error

    public override fun hashCode(): Int = 0

    public companion object
}

/**
 * A trait for converting a value to a `RustAllocString`.
 */
public interface ToString {
    public fun toRustString(): RustAllocString
}

public fun fromUtf8String(v: Vec<UByte, Global>): Result<RustAllocString, FromUtf8Error> =
    RustAllocString.fromUtf8(v.asSlice())

public fun defaultRustAllocString(): RustAllocString = RustAllocString.default()

public fun String.toOwned(): RustAllocString = RustAllocString.from(this)

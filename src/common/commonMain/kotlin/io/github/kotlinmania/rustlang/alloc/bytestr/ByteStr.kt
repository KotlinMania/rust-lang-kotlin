// port-lint: source byte_str.rs
package io.github.kotlinmania.rustlang.alloc.bytestr

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.alloc.string.FromUtf8Error
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeTo
import io.github.kotlinmania.rustlang.core.ops.range.RangeToInclusive
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.traits.FromStr

// The `ByteStr` and `ByteString` types and trait implementations.

public typealias Owned = ByteString

/**
 * A wrapper for `List<UByte>` representing a human-readable string that's conventionally, but not
 * always, UTF-8.
 *
 * Unlike `String`, this type permits non-UTF-8 contents, making it suitable for user input,
 * non-native filenames (as `Path` only supports native filenames), and other applications that
 * need to round-trip whatever data the user provides.
 *
 * A `ByteString` owns its contents and can grow and shrink, like a `List` or `String`. For a
 * borrowed byte string, see [`ByteStr`](../../std/byteStr/struct.ByteStr.html).
 *
 * `ByteString` implements `Deref` to `List<UByte>`, so all methods available on `List<UByte>` are
 * available on `ByteString`. Similarly, `ByteString` implements `DerefMut` to `List<UByte>`,
 * so you can modify a `ByteString` using any method available on `List<UByte>`.
 *
 * The `Debug` and `Display` implementations for `ByteString` are the same as those for `ByteStr`,
 * showing invalid UTF-8 as hex escapes or the Unicode replacement character, respectively.
 */
public open class ByteString internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout(),
) : DerefPure, AsRef<List<UByte>>, AsMut<List<UByte>>, Borrow<List<UByte>>, BorrowMut<List<UByte>>, FromStr<Nothing> {
    public var field0: List<UByte>
        get() = value
        set(v) {
            value = v
        }

    public var value: List<UByte>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<ByteString> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ByteString(ptr, layout) }, { it.value })

    public open fun clone(): ByteString = ByteString(value, fieldLayout)

    public open fun asBytes(): List<UByte> = this.value

    public open fun asBytestr(): ByteStr = ByteStr.new(this.value)

    public open fun asMutBytestr(): ByteStr = ByteStr.fromBytesMut(this.value)

    /**
     * Try to get a `String` representation of the `ByteString`, if it is
     * valid UTF-8.
     *
     * This method is named `toString()` because we want `ByteString` to
     * implement `Display`, but the `ToString` trait has a blanket
     * implementation for types that implement `Display`, and the trait version
     * will use the Unicode replacement character rather than returning a
     * `Result` and allowing for the possibility of the content not being UTF-8.
     */
    public override fun toString(): Result<String, Utf8Error> {
        // Avoid allocating a copy of the contents for invalid UTF-8
        when (val matchVal = String.fromUtf8(this.value)) {
            is Err -> {
                val e = matchVal.error
                return Result.Err<String, Utf8Error>(e)
            }
            else -> {}
        }
        // SAFETY: we just checked that the contents are valid UTF-8
        return Result.Ok<String, Utf8Error>(String.fromUtf8Unchecked(this.value.clone()))
    }

    public open fun deref(): List<UByte> = this.value

    public open fun derefMut(): List<UByte> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.asBytestr()).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append((this.asBytestr()).toString()); Result.Ok(Unit) }

    public open fun asRef(): List<UByte> = this.value

    public open fun asRefByteStr(): ByteStr = this.asBytestr()

    public open fun asMut(): MutableList<UByte> = this.value

    public open fun asMutByteStr(): ByteStr = this.asMutBytestr()

    public open fun borrow(): List<UByte> = this.value

    public open fun borrowByteStr(): ByteStr = this.asBytestr()

    // `implementation Borrow<ByteStr> for List<UByte>` omitted to avoid inference failures
    // `implementation Borrow<ByteStr> for String` omitted to avoid inference failures
    public open fun borrowMut(): MutableList<UByte> = this.value

    public open fun borrowMutByteStr(): ByteStr = this.asMutBytestr()

    public open fun index(idx: Int): UByte = this.value[idx]

    public open fun index(arg0: RangeFull): ByteStr = this.asBytestr()

    public open fun index(r: IntRange): ByteStr = ByteStr.fromBytes(this.value[r])

    public open fun indexRangeInclusiveInt(r: IntRange): ByteStr = ByteStr.fromBytes(this.value[r])

    public open fun index(r: RangeFrom<Int>): ByteStr = ByteStr.fromBytes(this.value[r])

    public open fun index(r: RangeTo<Int>): ByteStr = ByteStr.fromBytes(this.value[r])

    public open fun index(r: RangeToInclusive<Int>): ByteStr = ByteStr.fromBytes(this.value[r])

    public open fun indexMut(idx: Int): RustMutableReference<UByte> = this.value[idx]

    public open fun indexMut(arg0: RangeFull): ByteStr = this.asMutBytestr()

    public open fun indexMut(r: IntRange): ByteStr = ByteStr.fromBytesMut(this.value[r])

    public open fun indexMutRangeInclusiveInt(r: IntRange): ByteStr = ByteStr.fromBytesMut(this.value[r])

    public open fun indexMut(r: RangeFrom<Int>): ByteStr = ByteStr.fromBytesMut(this.value[r])

    public open fun indexMut(r: RangeTo<Int>): ByteStr = ByteStr.fromBytesMut(this.value[r])

    public open fun indexMut(r: RangeToInclusive<Int>): ByteStr = ByteStr.fromBytesMut(this.value[r])

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.value).hashCode()
        return state
    }

    public open fun eq(other: ByteString): Boolean = this.value == other.value

    public open fun eq(other: ByteStr): Boolean {
        val other1: List<UByte> = (other).asRef()
        return PartialEq.eq(this.asBytes(), other1)
    }

    public open fun partialCmp(other: ByteStr): Ordering? {
        val other1: List<UByte> = (other).asRef()
        return PartialOrd.partialCmp(this.asBytes(), other1)
    }

    public open fun eq(other: String): Boolean {
        val other1: List<UByte> = (other)
        return PartialEq.eq(this.asBytes(), other1)
    }

    public open fun partialCmp(other: String): Ordering? {
        val other1: List<UByte> = (other)
        return PartialOrd.partialCmp(this.asBytes(), other1)
    }

    public open fun eq(other: List<UByte>): Boolean {
        val other1: List<UByte> = (other)
        return PartialEq.eq(this.asBytes(), other1)
    }

    public open fun partialCmp(other: List<UByte>): Ordering? {
        val other1: List<UByte> = (other)
        return PartialOrd.partialCmp(this.asBytes(), other1)
    }

    public open fun cmp(other: ByteString): Ordering = Ord.cmp(this.value, other.value)

    public open fun partialCmp(other: ByteString): Ordering? = PartialOrd.partialCmp(this.value, other.value)

    public companion object {
        public operator fun invoke(value: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(fieldStorage, fieldLayout)

        // Omitted due to inference failures
        //
        // @unstable(feature = "byteStr", issue = "134915")
        // impl<a> From<&a str> for ByteString (
        // @inline
        // fn from(s: &a str) -> Self (
        // ByteString(s.asBytes().toVec())
        // )
        // )
        //
        // @unstable(feature = "byteStr", issue = "134915")
        // impl From<String> for ByteString (
        // @inline
        // fn from(s: String) -> Self (
        // ByteString(s.intoBytes())
        // )
        // )
        public fun from(s: ByteStr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(s.value.toVec(), fieldLayout = fieldLayout)

        public fun <I : Iterable<Char>> fromIter(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(iter.iterator().joinToString("").intoBytes(), fieldLayout = fieldLayout)

        public fun <I : Iterable<UByte>> fromIterUByte(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(iter.iterator().toMutableList(), fieldLayout = fieldLayout)

        public fun <I : Iterable<String>> fromIterString(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString = ByteString(iter.iterator().joinToString("").intoBytes(), fieldLayout = fieldLayout)

        public fun <I : Iterable<List<UByte>>> fromIterUByteSlice(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString {
            val buf = mutableListOf<UByte>()
            for (b in iter) {
                buf.addAll(b)
            }
            return ByteString(buf, fieldLayout = fieldLayout)
        }

        public fun <I : Iterable<ByteStr>> fromIterByteStr(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString {
            val buf = mutableListOf<UByte>()
            for (b in iter) {
                buf.addAll(b.value)
            }
            return ByteString(buf, fieldLayout = fieldLayout)
        }

        public fun <I : Iterable<ByteString>> fromIterByteString(iter: I, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteString {
            val buf = mutableListOf<UByte>()
            for (b in iter) {
                buf.append(b.value)
            }
            return ByteString(buf, fieldLayout = fieldLayout)
        }

        public fun fromStr(s: String, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Result<ByteString, Nothing> = Result.Ok<ByteString, Nothing>(ByteString((s.encodeToByteArray().map { it.toUByte() }).toMutableList(), fieldLayout = fieldLayout))
    }
}

// `implementation BorrowMut<ByteStr> for List<UByte>` omitted to avoid inference failures
// Omitted due to inference failures
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a, const N: Int> From<&a [u8; N]> for ByteString (
// @inline
// fn from(s: &a [u8; N]) -> Self (
// ByteString(s.asSlice().toVec())
// )
// )
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<const N: Int> From<[u8; N]> for ByteString (
// @inline
// fn from(s: [u8; N]) -> Self (
// ByteString(s.asSlice().toVec())
// )
// )
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a> From<&a [u8]> for ByteString (
// @inline
// fn from(s: &a [u8]) -> Self (
// ByteString(s.toVec())
// )
// )
//
// @unstable(feature = "byteStr", issue = "134915")
// impl From<Vec<u8>> for ByteString (
// @inline
// fn from(s: Vec<u8>) -> Self (
// ByteString(s)
// )
// )
public fun fromVecUByte(s: ByteString): List<UByte> = s.value

public fun fromCowByteStr(s: ByteString): ByteStr = s

public fun ByteStr.eq(other: ByteString): Boolean {
    val thisRef: List<UByte> = (this).asRef()
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun ByteStr.partialCmp(other: ByteString): Ordering? {
    val thisRef: List<UByte> = (this).asRef()
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun String.eq(other: ByteString): Boolean {
    val thisRef: List<UByte> = (this)
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun String.partialCmp(other: ByteString): Ordering? {
    val thisRef: List<UByte> = (this)
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun List<UByte>.eq(other: ByteString): Boolean {
    val thisRef: List<UByte> = (this)
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun List<UByte>.partialCmp(other: ByteString): Ordering? {
    val thisRef: List<UByte> = (this)
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun ByteStr.toOwned(): ByteString = ByteString(this.value.toVec(), fieldLayout = UnresolvedTypeLayout<List<UByte>>())

public fun tryFromString(s: ByteString): Result<String, FromUtf8Error> = String.fromUtf8(s.value)

// Additional impls for `ByteStr` that require types from `alloc`:
public fun <A : Allocator> ByteStr.clone(): ByteStr = Box.cloneFromRefIn(this, ByteStr.allocator(this).clone())

public fun fromCowByteStr(s: ByteStr): ByteStr = s

public fun fromBoxByteStr(s: List<UByte>): ByteStr {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Box.fromRaw(((Box.intoRaw(s) as Any?) as ByteStr))
}

public fun fromBoxUByteSlice(s: ByteStr): List<UByte> {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Box.fromRaw(((Box.intoRaw(s) as Any?) as List<UByte>))
}

public fun fromRcByteStr(s: List<UByte>): ByteStr {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Rc.fromRaw(((Rc.intoRaw(s) as Any?) as ByteStr))
}

public fun fromRcUByteSlice(s: ByteStr): List<UByte> {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Rc.fromRaw(((Rc.intoRaw(s) as Any?) as List<UByte>))
}

public fun fromArcByteStr(s: List<UByte>): ByteStr {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Arc.fromRaw(((Arc.intoRaw(s) as Any?) as ByteStr))
}

public fun fromArcUByteSlice(s: ByteStr): List<UByte> {
    // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`.
    return Arc.fromRaw(((Arc.intoRaw(s) as Any?) as List<UByte>))
}

public fun ByteStr.eq(other: ByteStr): Boolean {
    val other1: List<UByte> = (other).asRef()
    return PartialEq.eq(this.asBytes(), other1)
}

public fun ByteStr.eqByteStr(other: ByteStr): Boolean {
    val thisRef: List<UByte> = (this).asRef()
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun ByteStr.partialCmp(other: ByteStr): Ordering? {
    val other1: List<UByte> = (other).asRef()
    return PartialOrd.partialCmp(this.asBytes(), other1)
}

public fun ByteStr.partialCmpByteStr(other: ByteStr): Ordering? {
    val thisRef: List<UByte> = (this).asRef()
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun ByteStr.eq(other: String): Boolean {
    val other1: List<UByte> = (other)
    return PartialEq.eq(this.asBytes(), other1)
}

public fun String.eq(other: ByteStr): Boolean {
    val thisRef: List<UByte> = (this)
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun ByteStr.partialCmp(other: String): Ordering? {
    val other1: List<UByte> = (other)
    return PartialOrd.partialCmp(this.asBytes(), other1)
}

public fun String.partialCmp(other: ByteStr): Ordering? {
    val thisRef: List<UByte> = (this)
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun ByteStr.eq(other: List<UByte>): Boolean {
    val other1: List<UByte> = (other)
    return PartialEq.eq(this.asBytes(), other1)
}

public fun List<UByte>.eq(other: ByteStr): Boolean {
    val thisRef: List<UByte> = (this)
    return PartialEq.eq(thisRef, other.asBytes())
}

public fun ByteStr.partialCmp(other: List<UByte>): Ordering? {
    val other1: List<UByte> = (other)
    return PartialOrd.partialCmp(this.asBytes(), other1)
}

public fun List<UByte>.partialCmp(other: ByteStr): Ordering? {
    val thisRef: List<UByte> = (this)
    return PartialOrd.partialCmp(thisRef, other.asBytes())
}

public fun tryFromString(s: ByteStr): Result<String, Utf8Error> {
    return Result.Ok<String, Utf8Error>(when (val tryResult = String.fromUtf8(s.first)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<String, Utf8Error>(tryResult.error) }.into())
}

/**
 * Try to get a `String` representation of the `ByteStr`, if it is valid
 * UTF-8.
 *
 * This method is named `toString()` because we want `ByteStr` to
 * implement `Display`, but the `ToString` trait has a blanket
 * implementation for types that implement `Display`, and the trait version
 * will use the Unicode replacement character rather than returning a
 * `Result` and allowing for the possibility of the content not being UTF-8.
 */
public fun ByteStr.toString(): Result<String, Utf8Error> {
    // Avoid allocating a copy of the contents for invalid UTF-8
    when (val matchVal = String.fromUtf8(this.value)) {
        is Err -> {
            val e = matchVal.error
            return Result.Err<String, Utf8Error>(e)
        }
        else -> {}
    }
    // SAFETY: we just checked that the contents are valid UTF-8
    return Result.Ok<String, Utf8Error>(String.fromUtf8Unchecked(this.value.toVec()))
}

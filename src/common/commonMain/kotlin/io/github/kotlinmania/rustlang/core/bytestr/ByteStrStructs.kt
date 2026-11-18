package io.github.kotlinmania.rustlang.core.bytestr

import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.convert.AsMut
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.fmt.Alignment
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr

/**
 * A wrapper for `[UByte]` representing a human-readable string that's conventionally, but not
 * always, UTF-8.
 *
 * Unlike `String`, this type permits non-UTF-8 contents, making it suitable for user input,
 * non-native filenames (as `Path` only supports native filenames), and other applications that
 * need to round-trip whatever data the user provides.
 *
 * For an owned, growable byte string buffer, use
 * [`ByteString`](../../std/byteStr/struct.ByteString.html).
 *
 * `ByteStr` implements `Deref` to `[UByte]`, so all methods available on `[UByte]` are available on
 * `ByteStr`.
 *
 * # Representation
 *
 * A `ByteStr` has the same representation as a `String`. That is, a `ByteStr` is a wide pointer
 * which includes a pointer to some bytes and a length.
 *
 * # Trait implementations
 *
 * The `ByteStr` type has a number of trait implementations, and in particular, defines equality
 * and comparisons between `ByteStr`, `String`, and `[UByte]`, for convenience.
 *
 * The `Debug` implementation for `ByteStr` shows its bytes as a normal string, with invalid UTF-8
 * presented as hex escape sequences.
 *
 * The `Display` implementation behaves as if the `ByteStr` were first lossily converted to a
 * `String`, with invalid UTF-8 presented as the Unicode replacement character (�).
 */
public open class ByteStr internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout(),
) : DerefPure, AsRef<List<UByte>>, AsMut<List<UByte>>, Borrow<List<UByte>>, BorrowMut<List<UByte>> {
    public var field0: List<UByte>
        get() = value
        set(v) {
            value = v
        }

    public var value: List<UByte>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<ByteStr> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ByteStr(ptr, layout) }, { it.value })

    // @unstable(feature = "strAsStr", issue = "130366")

    /**
     * Returns the same string as `ByteStr`.
     *
     * This method is redundant when used directly on `ByteStr`, but
     * it helps dereferencing other "container" types,
     * for example `Holder<ByteStr>` or `Arc<ByteStr>`.
     */
    public open fun asByteStr(): ByteStr = this

    // @unstable(feature = "strAsStr", issue = "130366")

    /**
     * Returns the same string as `ByteStr`.
     *
     * This method is redundant when used directly on `ByteStr`, but
     * it helps dereferencing other "container" types,
     * for example `Holder<ByteStr>` or `MutexGuard<ByteStr>`.
     */
    public open fun asMutByteStr(): RustMutableReference<ByteStr> = this

    public open fun asBytes(): List<UByte> = this.value

    public open fun asBytesMut(): MutableList<UByte> = this.value

    public open fun deref(): List<UByte> = this.value

    public open fun derefMut(): MutableList<UByte> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = f.writeFmt("\"")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        for (chunk in this.utf8Chunks()) {
            for (c in chunk.valid().chars()) {
                when (c) {
                    '\u0000' -> { when (val tryResult = f.writeFmt("\\0")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) } }
                    in '\u0001'..'\u007f' -> when (val tryResult = f.writeFmt("${(c.toUByte()).escapeAscii()}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
                    else -> when (val tryResult = f.writeFmt("${c.escapeDebug()}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
                }
            }
            when (val tryResult = f.writeFmt("${chunk.invalid().escapeAscii()}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        }
        when (val tryResult = f.writeFmt("\"")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
    }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> {
        fun emit(byteStr: ByteStr, f1: StringBuilder): Result<Unit, Error> {
            for (chunk in byteStr.utf8Chunks()) {
                f1.append(chunk.valid())
                if (!chunk.invalid().isEmpty()) {
                    f1.append("�")
                }
            }
            return Result.Ok<Unit, Error>(Unit)
        }
        val requestedWidth = f.width().unwrapOr(0)
        if (requestedWidth == 0 && f.precision() == null) {
            // Avoid counting the characters if no truncation or padding was
            // requested.
            return emit(this, f)
        }
        val (truncated, actualWidth1) =
            run {
                val matchVal = f.precision()
                when {
                    // The entire string is truncated away. Weird, but ok.
                    matchVal != null && matchVal == 0 -> Pair(ByteStr.new(listOf()), 0)
                    // Advance through string until we run out of space.
                    matchVal != null -> {
                        val precision = matchVal
                        var remainingWidth = precision
                        var chunks = this.utf8Chunks()
                        var currentWidth = 0
                        var offset = 0
                        while (true) {
                            val chunk = chunks.next() ?: // We reached the end of the string without running out
    // of space, so print the entire string.
    break
                            val chars = chunk.valid().charIndices()
                            val remaining = when (val tryResult = chars.advanceBy(remainingWidth)) { is Result.Err<*, *> -> tryResult.errValue(); is Result.Ok<*, *> -> // We've counted off `precision` characters, so truncate
    // the string at the current offset.
    break }
                            offset += chunk.valid().len()
                            currentWidth += remainingWidth - remaining.get()
                            remainingWidth = remaining.get()
                            // `remainingWidth` cannot be zero, there is still space
                            // remaining. So next, count the � character emitted for
                            // the invalid chunk (if it exists).
                            if (!chunk.invalid().isEmpty()) {
                                offset += chunk.invalid().len()
                                currentWidth += 1
                                remainingWidth -= 1
                                if (remainingWidth == 0) {
                                    break
                                }
                            }
                        }
                    }
                    // The string shouldn't be truncated at all, so just count the number
                    // of characters to calculate the padding.
                    else -> {
                        val actualWidth =
                            this.utf8Chunks().map({ chunk1 ->
                                chunk1.valid().chars().count() +
                                    if (chunk1.invalid().isEmpty()) {
                                        0
                                    } else {
                                        1
                                    }
                            }).sum()
                        Pair(this, actualWidth)
                    }
                }
            }
        // The width is originally stored as a 16-bit number, so this cannot fail.
        val padding = UShort.tryFrom(requestedWidth.saturatingSub(actualWidth1)).unwrap()
        val postPadding = when (val tryResult = f.padding(padding, Alignment.Left)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        when (val tryResult = emit(truncated, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return postPadding.write(f)
    }

    public override fun asRef(): List<UByte> = this.value

    public open fun asRefByteStr(): ByteStr = this

    public override fun asMut(): MutableList<UByte> = this.value

    // `implementation AsMut<ByteStr> for [UByte]` omitted to avoid widespread inference failures
    // `implementation Borrow<ByteStr> for [UByte]` omitted to avoid widespread inference failures
    // `implementation Borrow<ByteStr> for String` omitted to avoid widespread inference failures
    public override fun borrow(): List<UByte> = this.value

    // `implementation BorrowMut<ByteStr> for [UByte]` omitted to avoid widespread inference failures
    public override fun borrowMut(): MutableList<UByte> = this.value

    public companion object {
        public operator fun invoke(value: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteStr = ByteStr(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): ByteStr = ByteStr(fieldStorage, fieldLayout)

        /**
         * Creates a `ByteStr` slice from anything that can be converted to a byte slice.
         *
         * This is a zero-cost conversion.
         *
         * # Example
         *
         * You can create a `ByteStr` from a byte array, a byte slice or a string slice:
         *
         * ```kotlin
         * val a = ByteStr.new("abc".encodeToByteArray())
         * val b = ByteStr.new("abc".encodeToByteArray())
         * val c = ByteStr.new("abc")
         * check(a == b)
         * check(a == c)
         * ```
         */
        public fun new(bytes: List<UByte>): ByteStr = ByteStr.fromBytes(bytes)

        public fun fromBytes(slice: List<UByte>): ByteStr {
            // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`, so we can turn a reference to
            // the wrapped type into a reference to the wrapper type.
            return (UnresolvedTypeLayout<ByteStr>()).readNative((((constPtrFromRustCast(slice)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }

        public fun fromBytesMut(slice: MutableList<UByte>): RustMutableReference<ByteStr> {
            // SAFETY: `ByteStr` is a transparent wrapper around `[UByte]`, so we can turn a reference to
            // the wrapped type into a reference to the wrapper type.
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(slice)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<ByteStr>())
        }

        public fun default(): ByteStr = ByteStr.fromBytes("".encodeToByteArray().map { it.toUByte() })
    }
}

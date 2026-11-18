@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.fmt

import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugList
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugMap
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugSet
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugStruct
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugTuple
import io.github.kotlinmania.rustlang.core.fmt.builders.debugListNew
import io.github.kotlinmania.rustlang.core.fmt.builders.debugMapNew
import io.github.kotlinmania.rustlang.core.fmt.builders.debugSetNew
import io.github.kotlinmania.rustlang.core.fmt.builders.debugStructNew
import io.github.kotlinmania.rustlang.core.fmt.builders.debugTupleNew
import io.github.kotlinmania.rustlang.core.fmt.rt.Argument
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Formatted
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Part
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.offset
import io.github.kotlinmania.rustlang.core.ptr.constptr.read
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.str.asPtr
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * The error type which is returned from formatting a message into a stream.
 *
 * This type does not support transmission of an error other than that an error
 * occurred. This is because, despite the existence of this error,
 * string formatting is considered an infallible operation.
 * `fmt()` implementors should not return this `Error` unless they received it from their
 * [Formatter]. The only time your code should create a new instance of this
 * error is when implementing `fmt.Write`, in order to cancel the formatting operation when
 * writing to the underlying stream fails.
 *
 * Any extra information must be arranged to be transmitted through some other means,
 * such as storing it in a field to be consulted after the formatting operation has been
 * cancelled. (For example, this is how [std.io.Write.writeFmt] propagates IO errors
 * during writing.)
 *
 * This type, `fmt.Error`, should not be
 * confused with [std.io.Error] or [std.error.Error], which you may also
 * have in scope.
 *
 * [std.io.Error]: ../../std/io/struct.Error.html
 * [std.io.Write.writeFmt]: ../../std/io/trait.Write.html#method.writeFmt
 * [std.error.Error]: ../../std/error/trait.Error.html
 *
 * # Examples
 *
 * ```kotlin
 * var output = ""
 * run {
 *     val matchVal = write(output, "Hello " + ("world").toString() + "!")
 *     if (matchVal is Err && matchVal.value == fmt.Error) {
 *         error("An error occurred")
 *     }
 * }
 * ```
 */
public open class Error : Display {
    public open fun clone(): Error = Error()

    public override fun fmt(f: Formatter): Result = f.append(("an error occurred when formatting an argument").toString())

    public companion object {
        public fun typeLayout(): TypeLayout<Error> =
            object : TypeLayout<Error> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Error =
                    Error()
                override fun writeNative(addr: Long, value: Error) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Error =
                    Error()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Error) {
                }
            }
    }
}

/**
 * Options for formatting.
 *
 * `FormattingOptions` is a [Formatter] without an attached [Write] trait.
 * It is mainly used to construct `Formatter` instances.
 */
public open class FormattingOptions(
    // Note: This could use a pattern type with range 0x00000000..=0x7dd0ffff.
    // It's unclear if that's useful, though.
    /**
     * Flags, with the following bit fields:
     *
     * ```text
     *   31  30  29  28  27  26  25  24  23  22  21  20                              0
     * ┌───┬───────┬───┬───┬───┬───┬───┬───┬───┬───┬──────────────────────────────────┐
     * │ 0 │ align │ p │ w │ X?│ x?│'0'│ # │ - │ + │               fill               │
     * └───┴───────┴───┴───┴───┴───┴───┴───┴───┴───┴──────────────────────────────────┘
     *   │     │     │   │  └─┬───────────────────┘ └─┬──────────────────────────────┘
     *   │     │     │   │    │                       └─ The fill character (21 bits char).
     *   │     │     │   │    └─ The debug upper/lower hex, zero pad, alternate, and plus/minus flags.
     *   │     │     │   └─ Whether a width is set. (The value is stored separately.)
     *   │     │     └─ Whether a precision is set. (The value is stored separately.)
     *   │     ├─ 0: Align left. (<)
     *   │     ├─ 1: Align right. (>)
     *   │     ├─ 2: Align center. (^)
     *   │     └─ 3: Alignment not set. (default)
     *   └─ Always zero.
     * ```
     */
    public var flags: UInt,
    /**
     * Width if width flag (bit 27) above is set. Otherwise, always 0.
     */
    public var width: UShort,
    /**
     * Precision if precision flag (bit 28) above is set. Otherwise, always 0.
     */
    public var precision: UShort,
) {
    public open fun clone(): FormattingOptions = FormattingOptions(flags, width, precision)

    /**
     * Sets or removes the sign (the `+` or the `-` flag).
     *
     * - `+`: This is intended for numeric types and indicates that the sign
     *   should always be printed. By default only the negative sign of signed
     *   values is printed, and the sign of positive or unsigned values is
     *   omitted. This flag indicates that the correct sign (+ or -) should
     *   always be printed.
     * - `-`: Currently not used
     */
    public open fun sign(sign: Sign?): FormattingOptions {
        val sign1 =
            when (sign) {
                null -> 0
                Sign.Plus -> SIGN_PLUS_FLAG
                Sign.Minus -> SIGN_MINUS_FLAG
            }
        this.flags = this.flags and (SIGN_PLUS_FLAG or SIGN_MINUS_FLAG).inv() or sign1
        return this
    }

    /**
     * Sets or unsets the `0` flag.
     *
     * This is used to indicate for integer formats that the padding to width should both be done with a 0 character as well as be sign-aware
     */
    public open fun signAwareZeroPad(signAwareZeroPad: Boolean): FormattingOptions {
        if (signAwareZeroPad) {
            this.flags = this.flags or SIGN_AWARE_ZERO_PAD_FLAG
        } else {
            this.flags = this.flags and SIGN_AWARE_ZERO_PAD_FLAG.inv()
        }
        return this
    }

    /**
     * Sets or unsets the `#` flag.
     *
     * This flag indicates that the "alternate" form of printing should be
     * used. The alternate forms are:
     * - [Debug] : pretty-print the [Debug] formatting (adds linebreaks and indentation)
     * - [LowerHex] as well as [UpperHex] - precedes the argument with a `0x`
     * - [Octal] - precedes the argument with a `0o`
     * - [Binary] - precedes the argument with a `0b`
     */
    public open fun alternate(alternate: Boolean): FormattingOptions {
        if (alternate) {
            this.flags = this.flags or ALTERNATE_FLAG
        } else {
            this.flags = this.flags and ALTERNATE_FLAG.inv()
        }
        return this
    }

    /**
     * Sets the fill character.
     *
     * The optional fill character and alignment is provided normally in
     * conjunction with the width parameter. This indicates that if the value
     * being formatted is smaller than width some extra characters will be
     * printed around it.
     */
    public open fun fill(fill: Char): FormattingOptions {
        this.flags = this.flags and (UInt.MAX_VALUE shl 21) or fill.toUInt()
        return this
    }

    /**
     * Sets or removes the alignment.
     *
     * The alignment specifies how the value being formatted should be
     * positioned if it is smaller than the width of the formatter.
     */
    public open fun align(align: Alignment?): FormattingOptions {
        val align1: UInt =
            when (align) {
                Alignment.Left -> ALIGN_LEFT
                Alignment.Right -> ALIGN_RIGHT
                Alignment.Center -> ALIGN_CENTER
                null -> ALIGN_UNKNOWN
            }
        this.flags = this.flags and ALIGN_BITS.inv() or align1
        return this
    }

    /**
     * Sets or removes the width.
     *
     * This is a parameter for the “minimum width” that the format should take
     * up. If the value’s string does not fill up this many characters, then
     * the padding specified by [FormattingOptions.fill]/[FormattingOptions.align]
     * will be used to take up the required space.
     */
    public open fun width(width: UShort?): FormattingOptions {
        if (width != null) {
            val width1 = width
            this.flags = this.flags or WIDTH_FLAG
            this.width = width1
        } else {
            this.flags = this.flags and WIDTH_FLAG.inv()
            this.width = 0u
        }
        return this
    }

    /**
     * Sets or removes the precision.
     *
     * - For non-numeric types, this can be considered a “maximum width”. If
     *   the resulting string is longer than this width, then it is truncated
     *   down to this many characters and that truncated value is emitted with
     *   proper fill, alignment and width if those parameters are set.
     * - For integral types, this is ignored.
     * - For floating-point types, this indicates how many digits after the
     * decimal point should be printed.
     */
    public open fun precision(precision: UShort?): FormattingOptions {
        if (precision != null) {
            val precision1 = precision
            this.flags = this.flags or PRECISION_FLAG
            this.precision = precision1
        } else {
            this.flags = this.flags and PRECISION_FLAG.inv()
            this.precision = 0u
        }
        return this
    }

    /**
     * Specifies whether the [Debug] trait should use lower-/upper-case
     * hexadecimal or normal integers
     */
    public open fun debugAsHex(debugAsHex: DebugAsHex?): FormattingOptions {
        val debugAsHex1 =
            when (debugAsHex) {
                null -> 0
                DebugAsHex.Lower -> DEBUG_LOWER_HEX_FLAG
                DebugAsHex.Upper -> DEBUG_UPPER_HEX_FLAG
            }
        this.flags = this.flags and (DEBUG_LOWER_HEX_FLAG or DEBUG_UPPER_HEX_FLAG).inv() or debugAsHex1
        return this
    }

    /**
     * Returns the current sign (the `+` or the `-` flag).
     */
    public open fun getSign(): Sign? =
        if (this.flags and SIGN_PLUS_FLAG != 0u) {
            Sign.Plus
        } else if (this.flags and SIGN_MINUS_FLAG != 0u) {
            Sign.Minus
        } else {
            null
        }

    /**
     * Returns the current `0` flag.
     */
    public open fun getSignAwareZeroPad(): Boolean = this.flags and SIGN_AWARE_ZERO_PAD_FLAG != 0u

    /**
     * Returns the current `#` flag.
     */
    public open fun getAlternate(): Boolean = this.flags and ALTERNATE_FLAG != 0u

    /**
     * Returns the current fill character.
     */
    public open fun getFill(): Char {
        // SAFETY: We only ever put a valid `char` in the lower 21 bits of the flags field.
        return Char.fromU32Unchecked(this.flags and 0x1FFFFFu)
    }

    /**
     * Returns the current alignment.
     */
    public open fun getAlign(): Alignment? =
        when (this.flags and ALIGN_BITS) {
            ALIGN_LEFT -> Alignment.Left
            ALIGN_RIGHT -> Alignment.Right
            ALIGN_CENTER -> Alignment.Center
            else -> null
        }

    /**
     * Returns the current width.
     */
    public open fun getWidth(): UShort? =
        if (this.flags and WIDTH_FLAG != 0u) {
            this.width
        } else {
            null
        }

    /**
     * Returns the current precision.
     */
    public open fun getPrecision(): UShort? =
        if (this.flags and PRECISION_FLAG != 0u) {
            this.precision
        } else {
            null
        }

    /**
     * Returns the current `x` or `X` flag.
     */
    public open fun getDebugAsHex(): DebugAsHex? =
        if (this.flags and DEBUG_LOWER_HEX_FLAG != 0u) {
            DebugAsHex.Lower
        } else if (this.flags and DEBUG_UPPER_HEX_FLAG != 0u) {
            DebugAsHex.Upper
        } else {
            null
        }

    /**
     * Creates a [Formatter] that writes its output to the given [Write] trait.
     *
     * You may alternatively use [Formatter.new].
     */
    public open fun createFormatter(write: Write): Formatter = Formatter(options = this, buf = write)

    public companion object {
        public fun typeLayout(): TypeLayout<FormattingOptions> =
            object : TypeLayout<FormattingOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FormattingOptions =
                    FormattingOptions(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: FormattingOptions) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.width)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.precision)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FormattingOptions =
                    FormattingOptions(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FormattingOptions) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.width)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.precision)
                }
            }

        /**
         * Construct a new `FormattingOptions` representing the plain `()` formatting specifier:
         *
         * - no flags,
         * - filled with spaces,
         * - no alignment,
         * - no width,
         * - no precision, and
         * - no [DebugAsHex] output mode.
         */
        public fun new(): FormattingOptions = FormattingOptions(flags = (' ').code.toUInt() or ALIGN_UNKNOWN, width = 0u, precision = 0u)

        /**
         * Same as [FormattingOptions.new].
         */
        public fun default(): FormattingOptions {
            // The `@derive(Default)` implementation would set `fill` to `\0` instead of space.
            return FormattingOptions.new()
        }
    }
}

/**
 * Configuration for formatting.
 *
 * A `Formatter` represents various options related to formatting. Users do not
 * construct `Formatter`s directly; a mutable reference to one is passed to
 * the `fmt` method of all formatting traits, like [Debug] and [Display].
 *
 * To interact with a `Formatter`, you'll call various methods to change the
 * various options related to formatting. For examples, please see the
 * documentation of the methods defined on `Formatter` below.
 */
public open class Formatter(
    public var options: FormattingOptions,
    public var buf: Write,
) : Write<Formatter> {
    /**
     * Creates a new formatter based on this one with given [FormattingOptions].
     */
    public open fun withOptions(options: FormattingOptions): Formatter = Formatter(options = options, buf = this.buf)

    public open fun wrapBuf(wrap: (Write) -> Write): Formatter = Formatter(buf = wrap(this.buf), options = this.options)

    // Helper methods used for padding and processing formatting arguments that
    // all formatting traits can use.

    /**
     * Performs the correct padding for an integer which has already been
     * emitted into a str. The str should *not* contain the sign for the
     * integer, that will be added by this method.
     *
     * # Arguments
     *
     * * isNonnegative - whether the original integer was either positive or zero.
     * * prefix - if the '#' character (Alternate) is provided, this
     *   is the prefix to put in front of the number.
     * * buf - the byte array that the number has been formatted into
     *
     * This function will correctly account for the flags provided as well as
     * the minimum width. It will not take precision into account.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var nb: Int) {
     *     public open fun new(nb: Int): Foo {
     *         return Foo(nb = nb)
     *     }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         // We need to remove "-" from the number output.
     *         val tmp = this.nb.abs().toString()
     *         return formatter.padIntegral(this.nb >= 0, "Foo ", tmp)
     *     }
     * }
     * check("${Foo.new(2)}" == "2")
     * check("${Foo.new(-1)}" == "-1")
     * check("${Foo.new(0)}" == "0")
     * check("${Foo.new(-1)}" == "-Foo 1")
     * check("${Foo.new(-1)}" == "00-Foo 1")
     * ```
     */
    public open fun padIntegral(isNonnegative: Boolean, prefix: String, buf: String): Result {
        // Writes the sign if it exists, and then the prefix if it was requested
        fun writePrefix(f: Formatter, sign: Char?, prefix1: String?): Result {
            if (sign != null) {
                val c = sign
                f.buf.append(c)
            }
            return if (prefix1 != null) {
                val prefix2 = prefix1
                f.buf.append(prefix2)
            } else {
                RustResult.Ok(Unit)
            }
        }
        var width = buf.length
        var sign = null
        if (!isNonnegative) {
            sign = '-'
            width += 1
        } else if (this.signPlus()) {
            sign = '+'
            width += 1
        }
        val prefix1 =
            if (this.alternate()) {
                width += prefix.chars().count()
                prefix
            } else {
                null
            }
        // The `width` field is more of a `min - width` parameter at this point.
        val min = this.options.width
        return if (width >= Int.from(min)) {
            // We're over the minimum width, so then we can just write the bytes.
            when (val tryResult = writePrefix(this, sign, prefix1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            this.buf.append(buf)
        } else if (this.signAwareZeroPad()) {
            // The sign and prefix goes before the padding if the fill character
            // is zero
            val oldOptions = this.options
            this.options.fill('0').align(Alignment.Right)
            when (val tryResult = writePrefix(this, sign, prefix1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            val postPadding = when (val tryResult = this.padding(min - width.toUShort(), Alignment.Right)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            this.buf.append(buf)
            when (val tryResult = postPadding.write(this)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            this.options = oldOptions
            RustResult.Ok(Unit)
        } else {
            // Otherwise, the sign and prefix goes after the padding
            val postPadding1 = when (val tryResult = this.padding(min - width.toUShort(), Alignment.Right)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            when (val tryResult = writePrefix(this, sign, prefix1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            this.buf.append(buf)
            postPadding1.write(this)
        }
    }

    /**
     * Takes a string slice and emits it to the internal buffer after applying
     * the relevant formatting flags specified.
     *
     * The flags recognized for generic strings are:
     *
     * * width - the minimum width of what to emit
     * * fill/align - what to emit and where to emit it if the string
     *                provided needs to be padded
     * * precision - the maximum length to emit, the string is truncated if it
     *               is longer than this length
     *
     * Notably this function ignores the `flag` parameters.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo {
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return run { formatter.append("Foo"); Result.success(Unit) }
     *     }
     * }
     * check("$foo" == "Foo ")
     * check("$foo" == "0Foo")
     * ```
     */
    public open fun pad(s: String): Result {
        // Make sure there's a fast path up front.
        if (this.options.flags and (WIDTH_FLAG or PRECISION_FLAG) == 0u) {
            return run { this.buf.append(s); RustResult.Ok<Unit, Error>(Unit) }
        }
        // The `precision` field can be interpreted as a maximum width for the
        // string being formatted.
        val (s1, charCount) =
            when (val matchVal = this.options.getPrecision()) {
                null -> {
                    // Use the optimized char counting algorithm for the full string.
                    Pair(s, s.chars().count())
                }
                else -> {
                    val maxCharCount = matchVal
                    val iter = s.charIndices()
                    val remaining1 =
                        run {
                            val matchVal1 = iter.advanceBy(Int.from(maxCharCount))
                            when (matchVal1) { is RustResult.Ok<*, *> -> {
                                0
                            }; is RustResult.Err<*, *> -> {
                                val remaining = matchVal1.errValue()
                                remaining.get()
                            } }
                        }
                    // SAFETY: The offset of `.charIndices()` is guaranteed to be
                    // in-bounds and between character boundaries.
                    val truncated = s.getUnchecked(0 until iter.offset())
                    Pair(truncated, Int.from(maxCharCount) - remaining1)
                }
            }
        // The `width` field is more of a minimum width parameter at this point.
        return if (charCount < Int.from(this.options.width)) {
            // If we're under the minimum width, then fill up the minimum width
            // with the specified string + some alignment.
            val postPadding = when (val tryResult = this.padding(this.options.width - charCount.toUShort(), Alignment.Left)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
            this.buf.append(s1)
            postPadding.write(this)
        } else {
            // If we're over the minimum width or there is no minimum width, we
            // can just emit the string.
            this.buf.append(s1)
        }
    }

    /**
     * Writes the pre-padding and returns the unwritten post-padding.
     *
     * Callers are responsible for ensuring post-padding is written after the
     * thing that is being padded.
     */
    public open fun padding(padding: UShort, default: Alignment): io.github.kotlinmania.rustlang.core.result.Result<PostPadding, Error> {
        val align = (this.options.getAlign() ?: default)
        val fill = this.options.getFill()
        val paddingLeft =
            when (align) {
                Alignment.Left -> 0
                Alignment.Right -> padding
                Alignment.Center -> padding / 2u
            }
        for (unused in 0 until paddingLeft) {
            this.buf.append(fill)
        }
        return RustResult.Ok<PostPadding, Error>(PostPadding.new(fill, (padding - paddingLeft).toUShort()))
    }

    /**
     * Takes the formatted parts and applies the padding.
     *
     * Assumes that the caller already has rendered the parts with required precision,
     * so that `self.precision` can be ignored.
     *
     * # Safety
     *
     * Any `numfmt.Part.Copy` parts in `formatted` must contain valid UTF-8.
     */
    public open fun padFormattedParts(formatted: Formatted): Result {
        return if (this.options.width == 0u.toUShort()) {
            // this is the common case and we take a shortcut
            // SAFETY: Per the precondition.
            this.writeFormattedParts(formatted)
        } else {
            // for the sign-aware zero padding, we render the sign first and
            // behave as if we had no sign from the beginning.
            val formatted1 = formatted.clone()
            var width = this.options.width
            val oldOptions = this.options
            if (this.signAwareZeroPad()) {
                // a sign always goes first
                val sign = formatted1.sign
                this.buf.append(sign)
                // remove the sign from the formatted parts
                formatted1.sign = ""
                width = width.saturatingSub(sign.len().toUShort())
                this.options.fill('0').align(Alignment.Right)
            }
            // remaining parts go through the ordinary padding process.
            val len = formatted1.len()
            val ret =
                if (Int.from(width) <= len) {
                    // no padding
                    // SAFETY: Per the precondition.
                    this.writeFormattedParts(formatted1)
                } else {
                    // Padding widths are capped at `UShort`, so reaching this branch means
                    // the formatted output is also shorter than `UShort.MAX`.
                    val len2 =
                        run {
                            val matchVal = UShort.tryFrom(len)
                            when (matchVal) { is RustResult.Ok<*, *> -> {
                                val len1 = matchVal.okValue()
                                len1
                            }; is RustResult.Err<*, *> -> {
                                error("unreachable")
                            } }
                        }
                    val postPadding = when (val tryResult = this.padding(width - len2, Alignment.Right)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                    // SAFETY: Per the precondition.
                    run {
                        when (val tryResult = this.writeFormattedParts(formatted1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                    }
                    postPadding.write(this)
                }
            this.options = oldOptions
            ret
        }
    }

    /**
     * # Safety
     *
     * Any `numfmt.Part.Copy` parts in `formatted` must contain valid UTF-8.
     */
    public open fun writeFormattedParts(formatted: Formatted): Result {
        fun writeBytes(buf: Write, s: List<UByte>): Result {
            // SAFETY: This is used for `numfmt.Part.Num` and `numfmt.Part.Copy`.
            // It's safe to use for `numfmt.Part.Num` since every char `c` is between
            // `b'0'` and `b'9'`, which means `s` is valid UTF-8. It's safe to use for
            // `numfmt.Part.Copy` due to this function's precondition.
            return buf.append(String.fromUtf8Unchecked(s))
        }
        if (!formatted.sign.isEmpty()) {
            this.buf.append(formatted.sign)
        }
        for (part in formatted.parts) {
            when (part) {
                is Part.Zero -> {
                    val (nzeroes) = part
                    val ZEROES: String = "0000000000000000000000000000000000000000000000000000000000000000"
                    while (nzeroes > ZEROES.len()) {
                        this.buf.append(ZEROES)
                        nzeroes -= ZEROES.len()
                    }
                    if (nzeroes > 0) {
                        this.buf.append(ZEROES[0 until nzeroes])
                    }
                }
                is Part.Num -> {
                    val (v) = part
                    var s = MutableList(5) { 0 }
                    val len = part.len()
                    for (c in s[0 until len].iterMut().rev()) {
                        c = 48u + (v % 10).toUByte()
                        v /= 10
                    }
                    // SAFETY: Per the precondition.
                    run {
                        when (val tryResult = writeBytes(this.buf, s[0 until len])) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                    }
                }
                // SAFETY: Per the precondition.
                is Part.Copy -> {
                    val (buf) = part
                    run {
                        when (val tryResult = writeBytes(this.buf, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                    }
                }
            }
        }
        return RustResult.Ok(Unit)
    }

    /**
     * Writes some data to the underlying buffer contained within this
     * formatter.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo {
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return run { formatter.append("Foo"); Result.success(Unit) }
     *         // This is equivalent to:
     *         // write(formatter, "Foo")
     *     }
     * }
     * check("$foo" == "Foo")
     * check("$foo" == "Foo")
     * ```
     */
    public override fun writeStr(data: String): Result = this.buf.append(data)

    /**
     * Glue for usage of the [write] macro with implementors of this trait.
     *
     * This method should generally not be invoked manually, but rather through
     * the [write] macro itself.
     *
     * Writes some formatted information into this instance.
     *
     * This method delegates to the underlying [Write] implementation, so
     * whether the formatted output is appended to, or replaces, previously
     * written data is up to that writer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Int) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return formatter.writeFmt("Foo ${this.value}")
     *     }
     * }
     * check("${Foo(-1)}" == "Foo -1")
     * check("${Foo(2)}" == "Foo 2")
     * ```
     *
     * The same formatting code can produce different output depending on the
     * underlying writer:
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Hello {
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         formatter.writeFmt("Hello").getOrElse { return Result.failure(it) }
     *         return formatter.writeFmt(" world")
     *     }
     * }
     * // A writer that replaces its contents on every write.
     * open class Replace(public var value: String) : Appendable {
     *     public var field0: String
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun writeStr(s: String): Result<Unit> {
     *         this.value = ""
     *         this.value += s
     *         return Result.success(Unit)
     *     }
     * }
     * // With `String`, which appends, both writes are kept.
     * var s = ""
     * fmt.write(s, "$hello").getOrElse { return Result.failure(it) }
     * check(s == "Hello world")
     * // With a writer that replaces, the second write replaces the first.
     * var w = Replace("")
     * fmt.write(w, "$hello").getOrElse { return Result.failure(it) }
     * check(w.first == " world")
     * ```
     */
    public override fun writeFmt(fmt: Arguments): Result =
        when (val matchVal = fmt.asStaticallyKnownStr()) {
            null -> {
                write(this.buf, fmt)
            }
            else -> {
                val s = matchVal
                this.buf.append(s)
            }
        }

    /**
     * Returns flags for formatting.
     */
    public open fun flags(): UInt {
        // Extract the debug upper/lower hex, zero pad, alternate, and plus/minus flags
        // to stay compatible with older versions of Rust.
        return this.options.flags shr 21 and 0x3Fu
    }

    /**
     * Returns the character used as `fill` whenever there is alignment.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo {
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         val c = formatter.fill()
     *         return when (val matchVal = formatter.width()) {
     *             null -> {
     *                 run { formatter.append("$c"); Result.success(Unit) }
     *             }
     *             else -> {
     *                 val width = matchVal
     *                 for (unused in 0 until width) {
     *                     formatter.append("$c")
     *                 }
     *                 Result.success(Unit)
     *             }
     *         }
     *     }
     * }
     * // We set alignment to the right with ">".
     * check("$foo" == "GGG")
     * check("$foo" == "tttttt")
     * ```
     */
    public open fun fill(): Char = this.options.getFill()

    /**
     * Returns a flag indicating what form of alignment was requested.
     *
     * # Examples
     *
     * ```kotlin
     * open class Foo {
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         val s1 = when (val matchVal = formatter.align()) {
     *             null -> {
     *                 "into the void"
     *             }
     *             else -> {
     *                 val s = matchVal
     *                 when (s) {
     *                     Alignment.Left -> "left"
     *                     Alignment.Right -> "right"
     *                     Alignment.Center -> "center"
     *                 }
     *             }
     *         }
     *         return run { formatter.append("$s"); Result.success(Unit) }
     *     }
     * }
     * check("$foo" == "left")
     * check("$foo" == "right")
     * check("$foo" == "center")
     * check("$foo" == "into the void")
     * ```
     */
    public open fun align(): Alignment? = this.options.getAlign()

    /**
     * Returns the optionally specified integer width that the output should be.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Int) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return when (val matchVal = formatter.width()) {
     *             null -> {
     *                 // Otherwise we do nothing special
     *                 run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *             }
     *             else -> {
     *                 val width = matchVal
     *                 // If we received a width, we use it
     *                 run { formatter.append(("Foo(${this.value})").toString()); Result.success(Unit) }
     *             }
     *         }
     *     }
     * }
     * check("${Foo(23)}" == "Foo(23)   ")
     * check("${Foo(23)}" == "Foo(23)")
     * ```
     */
    public open fun width(): Int? =
        if (this.options.flags and WIDTH_FLAG == 0u) {
            null
        } else {
            this.options.width.toInt()
        }

    /**
     * Returns the optionally specified precision for numeric types.
     * Alternatively, the maximum width for string types.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Float) {
     *     public var field0: Float
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return when (val matchVal = formatter.precision()) {
     *             null -> {
     *                 // Otherwise we default to 2.
     *                 run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *             }
     *             else -> {
     *                 val precision = matchVal
     *                 // If we received a precision, we use it.
     *                 run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *             }
     *         }
     *     }
     * }
     * check("${Foo(23.2)}" == "Foo(23.2000)")
     * check("${Foo(23.2)}" == "Foo(23.20)")
     * ```
     */
    public open fun precision(): Int? =
        if (this.options.flags and PRECISION_FLAG == 0u) {
            null
        } else {
            this.options.precision.toInt()
        }

    /**
     * Determines if the `+` flag was specified.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public fun doctestEntry() {
     *     open class Foo(public var value: Int) {
     *         public var field0: Int
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *             return if (formatter.signPlus()) {
     *                 run {
     * formatter.append("Foo(${if (this.value < 0) {
     *                     '-'
     *                 } else {
     *                     '+'
     *                 }}${this.value.abs()})")
     * Result.success(Unit)
     * }
     *             } else {
     *                 run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *             }
     *         }
     *     }
     *     check("${Foo(23)}" == "Foo(+23)")
     *     check("${Foo(-23)}" == "Foo(-23)")
     *     check("${Foo(23)}" == "Foo(23)")
     * }
     * ```
     */
    public open fun signPlus(): Boolean = this.options.flags and SIGN_PLUS_FLAG != 0u

    /**
     * Determines if the `-` flag was specified.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Int) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return if (formatter.signMinus()) {
     *             // You want a minus sign? Have one!
     *             run { formatter.append("-Foo(${this.value})"); Result.success(Unit) }
     *         } else {
     *             run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *         }
     *     }
     * }
     * check("${Foo(23)}" == "-Foo(23)")
     * check("${Foo(23)}" == "Foo(23)")
     * ```
     */
    public open fun signMinus(): Boolean = this.options.flags and SIGN_MINUS_FLAG != 0u

    /**
     * Determines if the `#` flag was specified.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Int) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         return if (formatter.alternate()) {
     *             run { formatter.append("Foo(${this.value})"); Result.success(Unit) }
     *         } else {
     *             run { formatter.append("${this.value}"); Result.success(Unit) }
     *         }
     *     }
     * }
     * check("${Foo(23)}" == "Foo(23)")
     * check("${Foo(23)}" == "23")
     * ```
     */
    public open fun alternate(): Boolean = this.options.flags and ALTERNATE_FLAG != 0u

    /**
     * Determines if the `0` flag was specified.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: Int) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(formatter: StringBuilder): Result<Unit> {
     *         check(formatter.signAwareZeroPad())
     *         check(formatter.width() == 4)
     *         // We ignore the formatter's options.
     *         return run { formatter.append("${this.value}"); Result.success(Unit) }
     *     }
     * }
     * check("${Foo(23)}" == "23")
     * ```
     */
    public open fun signAwareZeroPad(): Boolean = this.options.flags and SIGN_AWARE_ZERO_PAD_FLAG != 0u

    // Note: Decide what public API we want for these two flags.
    // https://github.com/rust-lang/rust/issues/48584
    public open fun debugLowerHex(): Boolean = this.options.flags and DEBUG_LOWER_HEX_FLAG != 0u

    public open fun debugUpperHex(): Boolean = this.options.flags and DEBUG_UPPER_HEX_FLAG != 0u

    /**
     * Creates a [DebugStruct] builder designed to assist with creation of
     * [fmt.Debug] implementations for structs.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     * import std.net.Ipv4Addr
     *
     * open class Foo(
     *     public var bar: Int,
     *     public var baz: String,
     *     public var addr: Ipv4Addr,
     * ) {
     *     public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *         return run { DebugStruct(fmt, "Foo").field("bar", this.bar).field("baz", this.baz).field("addr", "${this.addr}").finish(); Result.success(Unit) }
     *     }
     * }
     * check("Foo { bar: 10, baz: \"Hello World\", addr: 127.0.0.1 }" == (Foo(bar = 10, baz = "Hello World".toString(), addr = Ipv4Addr.new(127, 0, 0, 1))).toString())
     * ```
     */
    public open fun debugStruct(name: String): DebugStruct = debugStructNew(this, name)

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller
     * binaries. `debugStructFieldsFinish` is more general, but this is
     * faster for 1 field.
     */
    public open fun debugStructField1Finish(names: String, value1: Debug): Result = this.debugStructFieldsFinish(names, listOf(value1))

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller
     * binaries. `debugStructFieldsFinish` is more general, but this is
     * faster for 2 fields.
     */
    public open fun debugStructField2Finish(names: String, value1: Debug, value2: Debug): Result = this.debugStructFieldsFinish(names, listOf(value1, value2))

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller
     * binaries. `debugStructFieldsFinish` is more general, but this is
     * faster for 3 fields.
     */
    public open fun debugStructField3Finish(names: String, value1: Debug, value2: Debug, value3: Debug): Result = this.debugStructFieldsFinish(names, listOf(value1, value2, value3))

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller
     * binaries. `debugStructFieldsFinish` is more general, but this is
     * faster for 4 fields.
     */
    public open fun debugStructField4Finish(names: String, value1: Debug, value2: Debug, value3: Debug, value4: Debug): Result = this.debugStructFieldsFinish(names, listOf(value1, value2, value3, value4))

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller
     * binaries. `debugStructFieldsFinish` is more general, but this is
     * faster for 5 fields.
     */
    public open fun debugStructField5Finish(names: String, value1: Debug, value2: Debug, value3: Debug, value4: Debug, value5: Debug): Result = this.debugStructFieldsFinish(names, listOf(value1, value2, value3, value4, value5))

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller binaries.
     * For the cases not covered by `debugStructField[12345] _finish`.
     * Field names should be separated by space.
     */
    public open fun debugStructFieldsFinish(names: String, values: List<Debug>): Result {
        // the macro generates either "Point x y" for a struct like `Point ( x: UInt, y: UInt, )`
        // or just "Point" for a tuple struct like `Point(UInt, UInt)`. If there are no field names
        // (and therefore no space separator), we assume we format a tuple struct.
        // `.asBytes()` is to avoid bringing in UTF-8 code bloat
        val isTuple = !names.encodeToByteArray().map { it.toUByte() }.contains(32u)
        return if (isTuple) {
            val builder = debugTupleNew(this, names)
            for (value in values) {
                builder.field(value)
            }
            builder.finish()
        } else {
            val names1 = splitStrBySpace(names)
            val builder1 = debugStructNew(this, names1.next()!!)
            for (value1 in ArrayDeque((values).toList())) {
                val name = names1.next()!!
                builder1.field(name, value1)
            }
            builder1.finish()
        }
    }

    /**
     * Shrinks `derive(Debug)` code, for faster compilation and smaller binaries.
     * For C-like enums with concatenated variant name strings.
     * Names should be separated by space.
     */
    public open fun debugCLikeEnumWriteStr(names: String, startIndex: Int): Result {
        // we use `get().unwrap()` instead of indexing to avoid binary size
        // bloat from the error path (`sliceErrorFailRt`)
        return this.append(splitStrBySpace(names.get(startIndex until Int.MAX_VALUE)!!).next()!!)
    }

    /**
     * Creates a `DebugTuple` builder designed to assist with creation of
     * `fmt.Debug` implementations for tuple structs.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Foo<T>(
     *         public var field0: Int,
     *         public var field1: String,
     *         public var field2: Unit,
     *     ) {
     *         public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *             return run { DebugTuple(fmt, "Foo").field(this.field0).field(this.field1).field("_").finish(); Result.success(Unit) }
     *         }
     *     }
     *     check("Foo(10, \"Hello\", _)" == (Foo(10, "Hello".toString(), Unit)).toString())
     * }
     * ```
     */
    public open fun debugTuple(name: String): DebugTuple = debugTupleNew(this, name)

    /**
     * Creates a `DebugList` builder designed to assist with creation of
     * `fmt.Debug` implementations for list-like structures.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *         return run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entries(this.value.iter()).finish(); Result.success(Unit) }
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11))}" == "[10, 11]")
     * ```
     */
    public open fun debugList(): DebugList = debugListNew(this)

    /**
     * Creates a `DebugSet` builder designed to assist with creation of
     * `fmt.Debug` implementations for set-like structures.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *         return run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.iter()).finish(); Result.success(Unit) }
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11))}" == "{10, 11}")
     * ```
     *
     * In this more complex example, we use [formatArgs] and `.debugSet()`
     * to build a list of match arms:
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Arm<L, R>(public var value: Pair<L, R>) {
     *     public var field0: Pair<L, R>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder, fmtLFn: (L, Formatter) -> Result, fmtRFn: (R, Formatter) -> Result): Result<Unit> {
     *         fmtLFn((this.value).first, fmt).getOrElse { return Result.failure(it) }
     *         fmt.append(" => ")
     *         return fmtRFn((this.value).second, fmt)
     *     }
     * }
     * open class Table<K, V>(public var field0: List<Pair<K, V>>, public var field1: V) {
     *     public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *         return run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.field0.map({ Arm(it) })).entry(Arm(Pair("_", this.field1))).finish(); Result.success(Unit) }
     *     }
     * }
     * ```
     */
    public open fun debugSet(): DebugSet = debugSetNew(this)

    /**
     * Creates a `DebugMap` builder designed to assist with creation of
     * `fmt.Debug` implementations for map-like structures.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder): Result<Unit> {
     *         return run { object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.map({ (k, v) -> Pair(k, v) })).finish(); Result.success(Unit) }
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"A\": 10, \"B\": 11}")
     * ```
     */
    public open fun debugMap(): DebugMap = debugMapNew(this)

    /**
     * Returns the sign of this formatter (`+` or `-`).
     */
    public open fun sign(): Sign? = this.options.getSign()

    /**
     * Returns the formatting options this formatter corresponds to.
     */
    public open fun options(): FormattingOptions = this.options

    public open fun writeStrWrite(s: String): Result = this.buf.append(s)

    public override fun writeChar(c: Char): Result = this.buf.append(c)

    public open fun writeFmtWrite(args: Arguments): Result =
        when (val matchVal = args.asStaticallyKnownStr()) {
            null -> {
                write(this.buf, args)
            }
            else -> {
                val s = matchVal
                this.buf.append(s)
            }
        }

    public companion object {
        /**
         * Creates a new formatter with given [FormattingOptions].
         *
         * If `write` is a reference to a formatter, it is recommended to use
         * [Formatter.withOptions] instead as this can borrow the underlying
         * `write`, thereby bypassing one layer of indirection.
         *
         * You may alternatively use [FormattingOptions.createFormatter].
         */
        public fun new(write: Write, options: FormattingOptions): Formatter = Formatter(options = options, buf = write)
    }
}

//
// Internal representation:
//
// fmt.Arguments is represented in one of two ways:
//
// 1) String literal representation (e.g. formatArgs("hello"))
// ┌────────────────────────────────┐
// template: │           *const u8            │ ─▷ "hello"
// ├──────────────────────────────┬─┤
// args:     │             len              │1│ (lowest bit is 1; field contains `len<<1 | 1`)
// └──────────────────────────────┴─┘
// In this representation, there are no placeholders and `fmt.Arguments.asStr()` returns Some.
// The pointer points to the start of a static `String`. The length is given by `args as Int>> 1`.
// (The length of a `String` is isize.Max at most, so it always fits in a Int minus one bit.)
//
// `fmt.Arguments.fromStr()` constructs this representation from a `String`.
//
// 2) Placeholders representation (e.g. formatArgs("hello (name)\n"))
// ┌────────────────────────────────┐
// template: │           *const u8            │ ─▷ b"\x06hello \xC0\x01\n\x00"
// ├────────────────────────────────┤
// args:     │     &a [Argument<a>; _]     0│ (lower bit is 0 due to alignment of Argument type)
// └────────────────────────────────┘
// In this representation, the template is a byte sequence encoding both the literal string pieces
// and the placeholders (including their options/flags).
//
// The `args` pointer points to an array of `fmt.Argument<>` values, of sufficient length to
// match the placeholders in the template.
//
// `fmt.Arguments.new()` constructs this representation from a template byte slice and a slice
// of arguments. This function is unchecked, as the template is assumed to be valid and the args
// slice is assumed to have elements matching the template.
//
// The template byte sequence is the concatenation of parts of the following types:
//
// - Literal string piece:
// Pieces that must be formatted verbatim (e.g. "hello " and "\n" in "hello (name)\n")
// appear literally in the template byte sequence, prefixed by their length.
//
// For pieces of up to 127 bytes, these are  represented as a single byte containing the
// length followed directly by the bytes of the string:
// ┌───┬────────────────────────────┐
// │len│    `len` bytes (utf-8)     │ (e.g. b"\x06hello ")
// └───┴────────────────────────────┘
//
// For larger pieces up to u16.Max bytes, these are  represented as a 0x80 followed by
// their length in 16-bit little endian, followed by the bytes of the string:
// ┌────┬─────────┬───────────────────────────┐
// │0x80│   len   │   `len` bytes (utf-8)     │ (e.g. b"\x80\x00\x01hello … ")
// └────┴─────────┴───────────────────────────┘
//
// Longer pieces are split into multiple pieces of max u16.Max bytes (at utf-8 boundaries).
//
// - Note:
// Placeholders (e.g. `( name )` in "hello (name)") are represented as a byte with the highest
// two bits set, followed by zero or more fields depending on the flags in the first byte:
// ┌──────────┬┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┬┄┄┄┄┄┄┄┄┄┄┄┬┄┄┄┄┄┄┄┄┄┄┄┬┄┄┄┄┄┄┄┄┄┄┄┐
// │0b11______│       flags       ┊   width   ┊ precision ┊ argIndex ┊ (e.g. b"\xC2\x05\0")
// └────││││││┴┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┴┄┄┄┄┄┄┄┄┄┄┄┴┄┄┄┄┄┄┄┄┄┄┄┴┄┄┄┄┄┄┄┄┄┄┄┘
// ││││││        32 bit          16 bit      16 bit      16 bit
// │││││└─ flags present
// ││││└─ width present
// │││└─ precision present
// ││└─ argIndex present
// │└─ width indirect
// └─ precision indirect
//
// All fields other than the first byte are optional and only present when their
// corresponding flag is set in the first byte.
//
// So, a fully default Note without any options is just a single byte:
// ┌──────────┐
// │0b11000000│ (b"\xC0")
// └──────────┘
//
// The fields are stored as little endian.
//
// The `flags` fields corresponds to the `flags` field of `FormattingOptions`.
// See doc comment of `FormattingOptions.flags` for details.
//
// The `width` and `precision` fields correspond to their respective fields in
// `FormattingOptions`. However, if their "indirect" flag is set, the field contains the
// index in the `args` array where the dynamic width or precision is stored, rather than the
// value directly.
//
// The `argIndex` field is the index into the `args` array for the argument to be
// formatted.
//
// If omitted, the flags, width and precision of the default FormattingOptions.new() are
// used.
//
// If the `argIndex` is omitted, the next argument in the `args` array is used (starting
// at 0).
//
// - End:
// A single zero byte marks the end of the template:
// ┌───┐
// │ 0 │ ("\0")
// └───┘
//
// (Note that a zero byte may also occur naturally as part of the string pieces or flags,
// width, precision and argIndex fields above. That is, the template byte sequence ends
// with a 0 byte, but isn't terminated by the first 0 byte.)
//

/**
 * This structure represents a safely precompiled version of a format string
 * and its arguments. This cannot be generated at runtime because it cannot
 * safely be done, so no constructors are given and the fields are private
 * to prevent modification.
 *
 * The [formatArgs] macro will safely create an instance of this structure.
 * The macro validates the format string at compile-time so usage of the
 * [write] and [format] functions can be safely performed.
 *
 * You can use the `Arguments<>` that [formatArgs] returns in `Debug`
 * and `Display` contexts as seen below. The example also shows that `Debug`
 * and `Display` format to the same thing: the interpolated format string
 * in `formatArgs`.
 *
 * ```kotlin
 * val debug = ("${1} foo ${2}").toString()
 * val display = ("${1} foo ${2}").toString()
 * check("1 foo 2" == display)
 * check(display == debug)
 * ```
 *
 * [format]: ../../std/fmt/fn.format.html
 */
public open class Arguments internal constructor(
    templateCtorArg: NonNull,
    argsCtorArg: NonNull,
) : Debug, Display {
    public var template: NonNull = templateCtorArg
        internal set

    public var args: NonNull = argsCtorArg
        internal set

    public open fun clone(): Arguments = Arguments(template, args)

    /**
     * Estimates the length of the formatted text.
     *
     * This is intended to be used for setting initial `String` capacity
     * when using `format`. Note: this is neither the lower nor upper bound.
     */
    public open fun estimatedCapacity(): Int {
        when (val matchVal = this.asStr()) {
            null -> {}
            else -> {
                val s = matchVal
                return s.length
            }
        }
        // Iterate over the template, counting the length of literal pieces.
        var length = 0
        var startsWithPlaceholder = false
        var template = this.template
        while (true) {
            // SAFETY: We can assume the template is valid.
            run {
                val n = template.read()
                template = template.add(1)
                if (n == 0) {
                    // End of template.
                    break
                } else if (n < 128) {
                    // Short literal string piece.
                    length += n.toInt()
                    template = template.add(n.toInt())
                } else if (n == 128) {
                    // Long literal string piece.
                    val len = Int.from(run { val bytesTmp1 = template.castArray().read(); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
                    length += len
                    template = template.add(2 + len)
                } else {
                    assertUnchecked(n >= 0xC0)
                    // Note piece.
                    if (length == 0) {
                        startsWithPlaceholder = true
                    }
                    // Skip remainder of Note:
                    val skip = (if (n and 1 != 0) 1 else 0) * 4 + (if (n and 2 != 0) 1 else 0) * 2 + (if (n and 4 != 0) 1 else 0) * 2 + (if (n and 8 != 0) 1 else 0) * 2
                    // argIndex (16 bit)
                    template = template.add(skip)
                }
            }
        }
        return if (startsWithPlaceholder && length < 16) {
            // If the format string starts with a Note,
            // don't preallocate anything, unless length
            // of literal pieces is significant.
            0
        } else {
            // There are some placeholders, so any additional push
            // will reallocate the string. To avoid that,
            // we're "pre-doubling" the capacity here.
            (length * 2)
        }
    }

    /**
     * Gets the formatted string, if it has no arguments to be formatted at runtime.
     *
     * This can be used to avoid allocations in some cases.
     *
     * # Guarantees
     *
     * For `formatArgs("just a literal")`, this function is guaranteed to
     * return `Some("just a literal")`.
     *
     * For most cases with placeholders, this function will return `None`.
     *
     * However, the compiler may perform optimizations that can cause this
     * function to return `Some(_)` even if the format string contains
     * placeholders. For example, `formatArgs("Hello, ()!", "world")` may be
     * optimized to `formatArgs("Hello, world!")`, such that `asStr()`
     * returns `Some("Hello, world!")`.
     *
     * The behavior for anything but the trivial case (without placeholders)
     * is not guaranteed, and should not be relied upon for anything other
     * than optimization.
     *
     * # Examples
     *
     * ```kotlin
     * fun writeStr(arg0: String) {
     *     // ...
     * }
     * fun writeFmt(args: Arguments) {
     *     when (val matchVal = args.asStr()) {
     *         null -> {
     *             writeStr(args.toString())
     *         }
     *         else -> {
     *             val s = matchVal
     *             writeStr(s)
     *         }
     *     }
     * }
     * ```
     *
     * ```kotlin
     * check("hello".asStr() == "hello")
     * check("".asStr() == "")
     * check("${currentDir()}".asStr() == null)
     * ```
     */
    public open fun asStr(): String? {
        // SAFETY: During const eval, `self.args` must have come from a Int,
        // not a pointer, because that's the only way to create a fmt.Arguments in const.
        // (I.e. only fmt.Arguments.fromStr is const, fmt.Arguments.new is not.)
        //
        // Outside const eval, transmuting a pointer to a Int is fine.
        val bits: Int = transmuteWithLayouts<NonNull, Int>(this.args, UnresolvedTypeLayout<NonNull>(), UnresolvedTypeLayout<Int>())
        return if (bits and 1 == 1) {
            // SAFETY: This fmt.Arguments stores a &static str. See encoding documentation above.
            String.fromUtf8Unchecked(linearSliceView(this.template.asPtr(), (bits shr 1).toInt(), PrimitiveLayouts.U8))
        } else {
            null
        }
    }

    /**
     * Same as [Arguments.asStr], but will only return `Some(s)` if it can be determined at compile time.
     */
    public open fun asStaticallyKnownStr(): String? {
        val s = this.asStr()
        return if (false) {
            s
        } else {
            null
        }
    }

    // Manually implementing these results in better error messages.

    public override fun fmt(fmt: Formatter): Result = fmt.append((this).toString())

    public open fun fmtDisplay(fmt: Formatter): Result = write(fmt.buf, this)

    public companion object {
        public operator fun invoke(
            template: NonNull,
            args: NonNull,
        ): Arguments = Arguments(
            templateCtorArg = template,
            argsCtorArg = args,
        )

        /**
         * Used by the formatArgs() macro to create a fmt.Arguments object.
         */
        // SAFETY: The caller must ensure that the provided template and args encode a valid
        // fmt.Arguments, as documented above.
        public fun new(template: UByteArray, args: List<Argument>, n: Int = 0, m: Int = 0): Arguments {
            // SAFETY: Responsibility of the caller.
            return Arguments(template = transmuteWithLayouts<UByteArray, NonNull>(template, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<NonNull>()), args = transmuteWithLayouts<List<Argument>, NonNull>(args, UnresolvedTypeLayout<List<Argument>>(), UnresolvedTypeLayout<NonNull>()))
        }

        // Same as `fromStr`, but not const.
        // Used by formatArgs() expansion when arguments are inlined,
        // e.g. formatArgs("()", 123), which is not allowed in const.
        public fun fromStrNonconst(s: String): Arguments = Arguments.fromStr(s)

        /**
         * Create a `fmt.Arguments` object for a single static string.
         *
         * Formatting this `fmt.Arguments` will just produce the string as-is.
         */
        public fun fromStr(s: String): Arguments {
            // SAFETY: This is the "static str" representation of fmt.Arguments; see above.
            return Arguments(template = transmuteWithLayouts<ConstPtr, NonNull>(s.asPtr(), PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<NonNull>()), args = transmuteWithLayouts<Any?, NonNull>(s.length shl 1 or 1, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<NonNull>()))
        }
    }
}

/**
 * Padding after the end of something. Returned by `Formatter.padding`.
 */
public open class PostPadding(
    public var fill: Char,
    public var padding: UShort,
) {
    /**
     * Writes this post padding.
     */
    public open fun write(f: Formatter): Result {
        for (unused in 0 until this.padding) {
            f.buf.append(this.fill)
        }
        return RustResult.Ok(Unit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PostPadding> =
            object : TypeLayout<PostPadding> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): PostPadding =
                    PostPadding(
                        (PrimitiveLayouts.CHAR).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: PostPadding) {
                    (PrimitiveLayouts.CHAR).writeNative(addr, value.fill)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.padding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PostPadding =
                    PostPadding(
                        (PrimitiveLayouts.CHAR).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PostPadding) {
                    (PrimitiveLayouts.CHAR).writeBytes(buf, offset, value.fill)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.padding)
                }
            }

        public fun new(fill: Char, padding: UShort): PostPadding = PostPadding(fill = fill, padding = padding)
    }
}

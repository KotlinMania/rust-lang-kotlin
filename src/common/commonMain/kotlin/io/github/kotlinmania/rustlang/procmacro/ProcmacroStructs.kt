@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.procmacro

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.num.fromStrRadix
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.traits.FromStr
import io.github.kotlinmania.rustlang.procmacro.bridge.DelimSpan
import io.github.kotlinmania.rustlang.procmacro.bridge.IdentKind
import io.github.kotlinmania.rustlang.procmacro.bridge.LitKind
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Symbol
import io.github.kotlinmania.rustlang.procmacro.diagnostic.Diagnostic
import io.github.kotlinmania.rustlang.procmacro.diagnostic.Level
import io.github.kotlinmania.rustlang.procmacro.escape.EscapeOptions
import io.github.kotlinmania.rustlang.procmacro.escape.escapeBytes
import io.github.kotlinmania.rustlang.rustcliteralescaper.MixedUnit
import io.github.kotlinmania.rustlang.rustcliteralescaper.unescapeByte
import io.github.kotlinmania.rustlang.rustcliteralescaper.unescapeByteStr
import io.github.kotlinmania.rustlang.rustcliteralescaper.unescapeCStr
import io.github.kotlinmania.rustlang.rustcliteralescaper.unescapeChar
import io.github.kotlinmania.rustlang.rustcliteralescaper.unescapeStr
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.procmacro.bridge.Methods as BridgeMethods
import io.github.kotlinmania.rustlang.rustcliteralescaper.EscapeError as EE

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Mostly relating to malformed escape sequences, but also a few other problems.
 */
public open class EscapeError(
    public var kind: EscapeErrorKind,
) {
    public open fun fmt(f: StringBuilder) {
        f.append((this.kind).toString())
    }

    public companion object {
        public fun from(value: io.github.kotlinmania.rustlang.rustcliteralescaper.EscapeError): EscapeError {
            val kind =
                when (value) {
                    EE.ZeroChars -> EscapeErrorKind.ZeroChars
                    EE.MoreThanOneChar -> EscapeErrorKind.MoreThanOneChar
                    EE.LoneSlash -> EscapeErrorKind.LoneSlash
                    EE.InvalidEscape -> EscapeErrorKind.InvalidEscape
                    EE.BareCarriageReturn -> EscapeErrorKind.BareCarriageReturn
                    EE.BareCarriageReturnInRawString -> EscapeErrorKind.BareCarriageReturnInRawString
                    EE.EscapeOnlyChar -> EscapeErrorKind.EscapeOnlyChar
                    EE.TooShortHexEscape -> EscapeErrorKind.TooShortHexEscape
                    EE.InvalidCharInHexEscape -> EscapeErrorKind.InvalidCharInHexEscape
                    EE.OutOfRangeHexEscape -> EscapeErrorKind.OutOfRangeHexEscape
                    EE.NoBraceInUnicodeEscape -> EscapeErrorKind.NoBraceInUnicodeEscape
                    EE.InvalidCharInUnicodeEscape -> EscapeErrorKind.InvalidCharInUnicodeEscape
                    EE.EmptyUnicodeEscape -> EscapeErrorKind.EmptyUnicodeEscape
                    EE.UnclosedUnicodeEscape -> EscapeErrorKind.UnclosedUnicodeEscape
                    EE.LeadingUnderscoreUnicodeEscape -> EscapeErrorKind.LeadingUnderscoreUnicodeEscape
                    EE.OverlongUnicodeEscape -> EscapeErrorKind.OverlongUnicodeEscape
                    EE.LoneSurrogateUnicodeEscape -> EscapeErrorKind.LoneSurrogateUnicodeEscape
                    EE.OutOfRangeUnicodeEscape -> EscapeErrorKind.OutOfRangeUnicodeEscape
                    EE.UnicodeEscapeInByte -> EscapeErrorKind.UnicodeEscapeInByte
                    EE.NonAsciiCharInByte -> EscapeErrorKind.NonAsciiCharInByte
                    EE.NulInCStr -> EscapeErrorKind.NulInCStr
                    EE.UnskippedWhitespaceWarning -> EscapeErrorKind.UnskippedWhitespaceWarning
                    EE.MultipleSkippedLinesWarning -> EscapeErrorKind.MultipleSkippedLinesWarning
                }
            return EscapeError(kind = kind)
        }
    }
}

/**
 * The main type provided by this crate, representing an abstract stream of
 * tokens, or, more specifically, a sequence of token trees.
 * The type provides interfaces for iterating over those token trees and, conversely,
 * collecting a number of token trees into one stream.
 *
 * This is both the input and output of `@ProcMacro`, `@ProcMacroAttribute`
 * and `@ProcMacroDerive` definitions.
 */
public open class TokenStream(
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream?,
) : FromStr<LexError> {
    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream?
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TokenStream = TokenStream(value)

    /**
     * Checks if this `TokenStream` is empty.
     */
    public open fun isEmpty(): Boolean = (this.value?.let(BridgeMethods::tsIsEmpty) ?: true)

    /**
     * Parses this `TokenStream` as an expression and attempts to expand any
     * macros within it. Returns the expanded `TokenStream`.
     *
     * Currently only expressions expanding to literals will succeed, although
     * this may be relaxed in the future.
     *
     * NOTE: In error conditions, `expandExpr` may leave macros unexpanded,
     * report an error, failing compilation, and/or return an `Err(..)`. The
     * specific behavior for any error condition, and what conditions are
     * considered errors, is unspecified and may change in the future.
     */
    public open fun expandExpr(): Result<TokenStream, ExpandError> {
        val stream = when (val tryResult = (this.value?.let { Result.Ok(it) } ?: Result.Err(ExpandError()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return run {
            val matchVal = BridgeMethods.tsExpandExpr(stream)
            when (matchVal) { is Result.Ok<*, *> -> {
                val stream1 = matchVal.value
                Result.Ok(TokenStream(stream1))
            }; is Result.Err<*, *> -> {
                Result.Err(ExpandError())
            } }
        }
    }

    /**
     * Prints the token stream as a string that is supposed to be losslessly convertible back
     * into the same token stream (modulo spans), except for possibly `TokenTree.Group`s
     * with `Delimiter.None` delimiters and negative numeric literals.
     *
     * Note: the exact form of the output is subject to change, e.g. there might
     * be changes in the whitespace used between tokens. Therefore, you should
     * *not* do any kind of simple substring matching on the output string (as
     * produced by `toString`) to implement a proc macro, because that matching
     * might stop working if such changes happen. Instead, you should work at the
     * `TokenTree` level, e.g. matching against `TokenTree.Ident`,
     * `TokenTree.Punct`, or `TokenTree.Literal`.
     */
    public open fun fmt(f: StringBuilder) {
        when (val matchVal = this.value) {
            null -> Unit
            else -> {
                val ts = matchVal
                f.append("${BridgeMethods.tsToString(ts)}")
            }
        }
    }

    /**
     * Prints tokens in a form convenient for debugging.
     */
    public open fun fmtDebug(f: StringBuilder) {
        f.append("TokenStream ")
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public open fun <I : Iterable<TokenTree>> extend(trees: I) {
        val iter = trees.iterator()
        val builder = ConcatTreesHelper.new(iter.sizeHint().first)
        iter.forEach({ tree -> builder.push(tree) })
        builder.appendTo(this)
    }

    public open fun <I : Iterable<TokenStream>> extendTokenStream(streams: I) {
        val iter = streams.iterator()
        val builder = ConcatStreamsHelper.new(iter.sizeHint().first)
        iter.forEach({ stream -> builder.push(stream) })
        builder.appendTo(this)
    }

    public open fun <I : Iterable<Group>> extendGroup(iter: I) {
        this.extend(iter.iterator().map({ TokenTree.Group(it) }))
    }

    public open fun <I : Iterable<Literal>> extendLiteral(iter: I) {
        this.extend(iter.iterator().map({ TokenTree.Literal(it) }))
    }

    public open fun <I : Iterable<Punct>> extendPunct(iter: I) {
        this.extend(iter.iterator().map({ TokenTree.Punct(it) }))
    }

    public open fun <I : Iterable<Ident>> extendIdent(iter: I) {
        this.extend(iter.iterator().map({ TokenTree.Ident(it) }))
    }

    public companion object {
        /**
         * Returns an empty `TokenStream` containing no token trees.
         */
        public fun new(): TokenStream = TokenStream(null)

        /**
         * Attempts to break the string into tokens and parse those tokens into a token stream.
         * May fail for a number of reasons, for example, if the string contains unbalanced delimiters
         * or characters not existing in the language.
         * All tokens in the parsed stream get `Span.callSite()` spans.
         *
         * NOTE: some errors may cause panics instead of returning `LexError`. We reserve the right to
         * change these errors into `LexError`s later.
         */
        public fun fromStr(src: String): Result<TokenStream, LexError> {
            return Result.Ok(TokenStream(when (val tryResult = BridgeMethods.tsFromStr(src).mapErr({ LexError(it) })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }))
        }

        public fun default(): TokenStream = TokenStream.new()

        /**
         * Creates a token stream containing a single token tree.
         */
        public fun from(tree: TokenTree): TokenStream = TokenStream(BridgeMethods.tsFromTokenTree(treeToBridgeTree(tree)))

        /**
         * Collects a number of token trees into a single stream.
         */
        public fun <I : Iterable<TokenTree>> fromIter(trees: I): TokenStream {
            val iter = trees.iterator()
            val builder = ConcatTreesHelper.new(iter.sizeHint().first)
            iter.forEach({ tree -> builder.push(tree) })
            return builder.build()
        }

        /**
         * A "flattening" operation on token streams, collects token trees
         * from multiple token streams into a single stream.
         */
        public fun <I : Iterable<TokenStream>> fromIterTokenStream(streams: I): TokenStream {
            val iter = streams.iterator()
            val builder = ConcatStreamsHelper.new(iter.sizeHint().first)
            iter.forEach({ stream -> builder.push(stream) })
            return builder.build()
        }
    }
}

/**
 * Error returned from `TokenStream.fromStr`.
 *
 * The contained error message is explicitly not guaranteed to be stable in any way,
 * and may change between Rust versions or across compilations.
 */
public open class LexError(
    public var value: String,
) {
    public var field0: String
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder) {
        f.append(this.value)
    }
}

/**
 * Error returned from `TokenStream.expandExpr`.
 */
public open class ExpandError {
    public open fun fmt(f: StringBuilder) {
        f.append("macro expansion failed")
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ExpandError> =
            object : TypeLayout<ExpandError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ExpandError =
                    ExpandError()
                override fun writeNative(addr: Long, value: ExpandError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExpandError =
                    ExpandError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExpandError) {
                }
            }
    }
}

/**
 * Non-generic helper for implementing `FromIterator<TokenTree>` and
 * `Extend<TokenTree>` with less monomorphization in calling crates.
 */
public open class ConcatTreesHelper internal constructor(
    treesCtorArg: MutableList<io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>>,
) {
    public var trees: MutableList<io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>> = treesCtorArg
        internal set

    public open fun push(tree: TokenTree) {
        this.trees.add(treeToBridgeTree(tree))
    }

    public open fun build(): TokenStream =
        if (this.trees.isEmpty()) {
            TokenStream(null)
        } else {
            TokenStream(BridgeMethods.tsConcatTrees(null, this.trees))
        }

    public open fun appendTo(stream: TokenStream) {
        if (this.trees.isEmpty()) {
            return
        }
        stream.value = BridgeMethods.tsConcatTrees(stream.value.also { stream.value = null }, this.trees)
    }

    public companion object {
        public operator fun invoke(
            trees: MutableList<io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>>,
        ): ConcatTreesHelper = ConcatTreesHelper(
            treesCtorArg = trees,
        )

        public fun new(capacity: Int): ConcatTreesHelper = ConcatTreesHelper(trees = ArrayList(capacity))
    }
}

/**
 * Non-generic helper for implementing `FromIterator<TokenStream>` and
 * `Extend<TokenStream>` with less monomorphization in calling crates.
 */
public open class ConcatStreamsHelper(
    public var streams: MutableList<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream>,
) {
    public open fun push(stream: TokenStream) {
        when (val matchVal = stream.value) {
            null -> {}
            else -> {
                val stream1 = matchVal
                this.streams.add(stream1)
            }
        }
    }

    public open fun build(): TokenStream =
        if (this.streams.size <= 1) {
            TokenStream(this.streams.removeLastOrNull())
        } else {
            TokenStream(BridgeMethods.tsConcatStreams(null, this.streams))
        }

    public open fun appendTo(stream: TokenStream) {
        if (this.streams.isEmpty()) {
            return
        }
        val base = stream.value.also { stream.value = null }
        if (base == null && this.streams.size == 1) {
            stream.value = this.streams.removeLastOrNull()
        } else {
            stream.value = BridgeMethods.tsConcatStreams(base, this.streams)
        }
    }

    public companion object {
        public fun new(capacity: Int): ConcatStreamsHelper = ConcatStreamsHelper(streams = ArrayList(capacity))
    }
}

/**
 * A region of source code, along with macro expansion information.
 */
public open class Span(
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.client.Span,
) {
    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.client.Span
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Span = Span(value)

    /**
     * The `Span` for the tokens in the previous macro expansion from which
     * `self` was generated from, if any.
     */
    public open fun parent(): Span? = BridgeMethods.spanParent(this.value).map({ Span(it) })

    /**
     * The span for the origin source code that `self` was generated from. If
     * this `Span` wasn't generated from other macro expansions then the return
     * value is the same as `* self`.
     */
    public open fun source(): Span = Span(BridgeMethods.spanSource(this.value))

    /**
     * Returns the span's byte position range in the source file.
     */
    public open fun byteRange(): IntRange = BridgeMethods.spanByteRange(this.value)

    /**
     * Creates an empty span pointing to directly before this span.
     */
    public open fun start(): Span = Span(BridgeMethods.spanStart(this.value))

    /**
     * Creates an empty span pointing to directly after this span.
     */
    public open fun end(): Span = Span(BridgeMethods.spanEnd(this.value))

    /**
     * The one-indexed line of the source file where the span starts.
     *
     * To obtain the line of the span's end, use `span.end().line()`.
     */
    public open fun line(): Int = BridgeMethods.spanLine(this.value)

    /**
     * The one-indexed column of the source file where the span starts.
     *
     * To obtain the column of the span's end, use `span.end().column()`.
     */
    public open fun column(): Int = BridgeMethods.spanColumn(this.value)

    /**
     * The path to the source file in which this span occurs, for display purposes.
     *
     * This might not correspond to a valid file system path.
     * It might be remapped (e.g. `"/src/lib.rs"`) or an artificial path (e.g. `"<command line>"`).
     */
    public open fun file(): String = BridgeMethods.spanFile(this.value)

    /**
     * The path to the source file in which this span occurs on the local file system.
     *
     * This is the actual path on disk. It is unaffected by path remapping.
     *
     * This path should not be embedded in the output of the macro; prefer `file()` instead.
     */
    public open fun localFile(): PathBuf? = BridgeMethods.spanLocalFile(this.value).map(PathBuf::from)

    /**
     * Creates a new span encompassing `self` and `other`.
     *
     * Returns `None` if `self` and `other` are from different files.
     */
    public open fun join(other: Span): Span? = BridgeMethods.spanJoin(this.value, other.value).map({ Span(it) })

    /**
     * Creates a new span with the same line/column information as `self` but
     * that resolves symbols as though it were at `other`.
     */
    public open fun resolvedAt(other: Span): Span = Span(BridgeMethods.spanResolvedAt(this.value, other.value))

    /**
     * Creates a new span with the same name resolution behavior as `self` but
     * with the line/column information of `other`.
     */
    public open fun locatedAt(other: Span): Span = other.resolvedAt(this.clone())

    /**
     * Compares two spans to see if they're equal.
     */
    public open fun eq(other: Span): Boolean = this.value == other.value

    /**
     * Returns the source text behind a span. This preserves the original source
     * code, including spaces and comments. It only returns a result if the span
     * corresponds to real source code.
     *
     * Note: The observable result of a macro should only rely on the tokens and
     * not on this source text. The result of this function is a best effort to
     * be used for diagnostics only.
     */
    public open fun sourceText(): String? = BridgeMethods.spanSourceText(this.value)

    // Used by the implementation of `Span.quote`
    public open fun saveSpan(): Int = BridgeMethods.spanSaveSpan(this.value)

    /**
     * Creates a new `Diagnostic` with the given `message` at the span
     * `self`.
     */
    public open fun error(message: String): Diagnostic = Diagnostic.spanned(this, Level.Error, message)

    /**
     * Creates a new `Diagnostic` with the given `message` at the span
     * `self`.
     */
    public open fun warning(message: String): Diagnostic = Diagnostic.spanned(this, Level.Warning, message)

    /**
     * Creates a new `Diagnostic` with the given `message` at the span
     * `self`.
     */
    public open fun note(message: String): Diagnostic = Diagnostic.spanned(this, Level.Note, message)

    /**
     * Creates a new `Diagnostic` with the given `message` at the span
     * `self`.
     */
    public open fun help(message: String): Diagnostic = Diagnostic.spanned(this, Level.Help, message)

    /**
     * Prints a span in a form convenient for debugging.
     */
    public open fun fmt(f: StringBuilder) {
        this.value.fmt(f)
    }

    public companion object {
        /**
         * A span that resolves at the macro definition site.
         */
        public fun defSite(): Span = Span(io.github.kotlinmania.rustlang.procmacro.bridge.client.Span.defSite())

        /**
         * The span of the invocation of the current procedural macro.
         * Identifiers created with this span will be resolved as if they were written
         * directly at the macro call location (call-site hygiene) and other code
         * at the macro call site will be able to refer to them as well.
         */
        public fun callSite(): Span = Span(io.github.kotlinmania.rustlang.procmacro.bridge.client.Span.callSite())

        /**
         * A span that represents `macroRules` hygiene, and sometimes resolves at the macro
         * definition site (local variables, labels, `$ crate`) and sometimes at the macro
         * call site (everything else).
         * The span location is taken from the call-site.
         */
        public fun mixedSite(): Span = Span(io.github.kotlinmania.rustlang.procmacro.bridge.client.Span.mixedSite())

        // Used by the implementation of `Span.quote`
        public fun recoverProcMacroSpan(id: Int): Span = Span(BridgeMethods.spanRecoverProcMacroSpan(id))
    }
}

/**
 * A delimited token stream.
 *
 * A `Group` internally contains a `TokenStream` which is surrounded by `Delimiter`s.
 */
public open class Group internal constructor(
    valueCtorArg: io.github.kotlinmania.rustlang.procmacro.bridge.Group<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>,
) {
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Group<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span> = valueCtorArg
        internal set

    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.Group<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Group = Group(value)

    /**
     * Returns the delimiter of this `Group`
     */
    public open fun delimiter(): Delimiter = this.value.delimiter

    /**
     * Returns the `TokenStream` of tokens that are delimited in this `Group`.
     *
     * Note that the returned token stream does not include the delimiter
     * returned above.
     */
    public open fun stream(): TokenStream = TokenStream(this.value.stream.clone())

    /**
     * Returns the span for the delimiters of this token stream, spanning the
     * entire `Group`.
     *
     * ```text
     * pub fn span(&self) -> Span {
     *            ^^^^^^^
     * ```
     */
    public open fun span(): Span = Span(this.value.span.entire)

    /**
     * Returns the span pointing to the opening delimiter of this group.
     *
     * ```text
     * pub fn spanOpen(&self) -> Span {
     *                 ^
     * ```
     */
    public open fun spanOpen(): Span = Span(this.value.span.open)

    /**
     * Returns the span pointing to the closing delimiter of this group.
     *
     * ```text
     * pub fn spanClose(&self) -> Span {
     *                        ^
     * ```
     */
    public open fun spanClose(): Span = Span(this.value.span.close)

    /**
     * Configures the span for this `Group`s delimiters, but not its internal
     * tokens.
     *
     * This method will **not** set the span of all the internal tokens spanned
     * by this group, but rather it will only set the span of the delimiter
     * tokens at the level of the `Group`.
     */
    public open fun setSpan(span: Span) {
        this.value.span = DelimSpan.fromSingle(span.value)
    }

    /**
     * Prints the group as a string that should be losslessly convertible back
     * into the same group (modulo spans), except for possibly `TokenTree.Group`s
     * with `Delimiter.None` delimiters.
     */
    public open fun fmt(f: StringBuilder) {
        f.append("${TokenStream.from(TokenTree.from(this.clone()))}")
    }

    public open fun fmtDebug(f: StringBuilder) {
        DebugStruct(f, "Group").field("delimiter", this.delimiter()).field("stream", this.stream()).field("span", this.span()).finish()
    }

    public companion object {
        public operator fun invoke(
            value: io.github.kotlinmania.rustlang.procmacro.bridge.Group<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>,
        ): Group = Group(
            valueCtorArg = value,
        )

        /**
         * Creates a new `Group` with the given delimiter and token stream.
         *
         * This constructor will set the span for this group to
         * `Span.callSite()`. To change the span you can use the `setSpan`
         * method below.
         */
        public fun new(delimiter: Delimiter, stream: TokenStream): Group = Group(io.github.kotlinmania.rustlang.procmacro.bridge.Group(delimiter = delimiter, stream = stream.value, span = DelimSpan.fromSingle(Span.callSite().value)))
    }
}

/**
 * A `Punct` is a single punctuation character such as `+`, `-` or `#`.
 *
 * Multi-character operators like `+=` are represented as two instances of `Punct` with different
 * forms of `Spacing` returned.
 */
public open class Punct internal constructor(
    valueCtorArg: io.github.kotlinmania.rustlang.procmacro.bridge.Punct<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>,
) {
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Punct<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span> = valueCtorArg
        internal set

    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.Punct<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Punct = Punct(value)

    /**
     * Returns the value of this punctuation character as `char`.
     */
    public open fun asChar(): Char = this.value.ch.toInt().toChar()

    /**
     * Returns the spacing of this punctuation character, indicating whether it can be potentially
     * combined into a multi-character operator with the following token (`Joint`), or whether the
     * operator has definitely ended (`Alone`).
     */
    public open fun spacing(): Spacing =
        if (this.value.joint) {
            Spacing.Joint
        } else {
            Spacing.Alone
        }

    /**
     * Returns the span for this punctuation character.
     */
    public open fun span(): Span = Span(this.value.span)

    /**
     * Configure the span for this punctuation character.
     */
    public open fun setSpan(span: Span) {
        this.value.span = span.value
    }

    /**
     * Prints the punctuation character as a string that should be losslessly convertible
     * back into the same character.
     */
    public open fun fmt(f: StringBuilder) {
        f.append("${this.asChar()}")
    }

    public open fun fmtDebug(f: StringBuilder) {
        DebugStruct(f, "Punct").field("ch", this.asChar()).field("spacing", this.spacing()).field("span", this.span()).finish()
    }

    public open fun eq(rhs: Char): Boolean = this.asChar() == rhs

    public companion object {
        public operator fun invoke(
            value: io.github.kotlinmania.rustlang.procmacro.bridge.Punct<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span>,
        ): Punct = Punct(
            valueCtorArg = value,
        )

        /**
         * Creates a new `Punct` from the given character and spacing.
         * The `ch` argument must be a valid punctuation character permitted by the language,
         * otherwise the function will panic.
         *
         * The returned `Punct` will have the default span of `Span.callSite()`
         * which can be further configured with the `setSpan` method below.
         */
        public fun new(ch: Char, spacing: Spacing): Punct {
            val LEGAL_CHARS: List<Char> = listOf('=', '<', '>', '!', '~', '+', '-', '*', '/', '%', '^', '&', '|', '@', '.', ',', ';', ':', '#', '$', '?', '\'')
            if (!LEGAL_CHARS.contains(ch)) {
                error("unsupported character `$ch`")
            }
            return Punct(io.github.kotlinmania.rustlang.procmacro.bridge.Punct(ch = ch.toUByte(), joint = spacing == Spacing.Joint, span = Span.callSite().value))
        }
    }
}

/**
 * An identifier (`ident`).
 */
public open class Ident internal constructor(
    valueCtorArg: io.github.kotlinmania.rustlang.procmacro.bridge.Ident<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>,
) {
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Ident<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol> = valueCtorArg
        internal set

    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.Ident<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Ident = Ident(value)

    /**
     * Returns the span of this `Ident`, encompassing the entire string returned
     * by [`toString`](ToString.toString).
     */
    public open fun span(): Span = Span(this.value.span)

    /**
     * Configures the span of this `Ident`, possibly changing its hygiene context.
     */
    public open fun setSpan(span: Span) {
        this.value.span = span.value
    }

    /**
     * Prints the identifier as a string that should be losslessly convertible back
     * into the same identifier.
     */
    public open fun fmt(f: StringBuilder) {
        when (val matchVal = this.value.kind.prefix()) {
            null -> {}
            else -> {
                val prefix = matchVal
                f.append(prefix)
            }
        }
        f.append((this.value.sym).toString())
    }

    public open fun fmtDebug(f: StringBuilder) {
        DebugStruct(f, "Ident").field("ident", this.toString()).field("span", this.span()).finish()
    }

    public companion object {
        public operator fun invoke(
            value: io.github.kotlinmania.rustlang.procmacro.bridge.Ident<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>,
        ): Ident = Ident(
            valueCtorArg = value,
        )

        /**
         * Creates a new `Ident` with the given `string` as well as the specified
         * `span`.
         * The `string` argument must be a valid identifier permitted by the
         * language (including keywords, e.g. `self` or `fun`). Otherwise, the function will panic.
         *
         * The constructed identifier will be NFC-normalized. See the [Reference] for more info.
         *
         * Note that `span`, currently in rustc, configures the hygiene information
         * for this identifier.
         *
         * As of this time `Span.callSite()` explicitly opts-in to "call-site" hygiene
         * meaning that identifiers created with this span will be resolved as if they were written
         * directly at the location of the macro call, and other code at the macro call site will be
         * able to refer to them as well.
         *
         * Later spans like `Span.defSite()` will allow to opt-in to "definition-site" hygiene
         * meaning that identifiers created with this span will be resolved at the location of the
         * macro definition and other code at the macro call site will not be able to refer to them.
         *
         * Due to the current importance of hygiene this constructor, unlike other
         * tokens, requires a `Span` to be specified at construction.
         *
         * [Reference]: https://doc.rust-lang.org/nightly/reference/identifiers.html#r-ident.normalization
         */
        public fun new(string: String, span: Span): Ident = Ident(io.github.kotlinmania.rustlang.procmacro.bridge.Ident(sym = Symbol.newIdent(string, false), kind = IdentKind.Normal, span = span.value))

        /**
         * Same as `Ident.new`, but creates a raw identifier (`ident`).
         * The `string` argument be a valid identifier permitted by the language
         * (including keywords, e.g. `fun`). Keywords which are usable in path segments
         * (e.g. `self`, `super`) are not supported, and will cause a panic.
         */
        public fun newRaw(string: String, span: Span): Ident = Ident(io.github.kotlinmania.rustlang.procmacro.bridge.Ident(sym = Symbol.newIdent(string, true), kind = IdentKind.Raw, span = span.value))
    }
}

/**
 * A literal string (`"hello"`), byte string (`b"hello"`), C string (`c"hello"`),
 * character (`'a'`), byte character (`b'a'`), an integer or floating point number
 * with or without a suffix (`1`, `1u8`, `2.3`, `2.3f32`).
 * Boolean literals like `true` and `false` do not belong here, they are `Ident`s.
 */
public open class Literal internal constructor(
    valueCtorArg: io.github.kotlinmania.rustlang.procmacro.bridge.Literal<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>,
) : FromStr<LexError> {
    public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Literal<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol> = valueCtorArg
        internal set

    public var field0: io.github.kotlinmania.rustlang.procmacro.bridge.Literal<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Literal = Literal(value)

    /**
     * Returns the span encompassing this literal.
     */
    public open fun span(): Span = Span(this.value.span)

    /**
     * Configures the span associated for this literal.
     */
    public open fun setSpan(span: Span) {
        this.value.span = span.value
    }

    // Note(SergioBenitez): check that the byte range starts and ends at a
    // UTF-8 boundary of the source. otherwise, it's likely that a panic will
    // occur elsewhere when the source text is printed.
    // Note(SergioBenitez): there is no way for the user to know what
    // `self.span()` actually maps to, so this method can currently only be
    // called blindly. For example, `toString()` for the character 'c' returns
    // "'\u{63}'"; there is no way for the user to know whether the source text
    // was 'c' or whether it was '\u{63}'.

    /**
     * Returns a `Span` that is a subset of `self.span()` containing only the
     * source bytes in range `range`. Returns `None` if the would-be trimmed
     * span is outside the bounds of `self`.
     */
    public open fun <R : RangeBounds<Int>> subspan(range: R): Span? = BridgeMethods.spanSubspan(this.value.span, range.startBound().cloned(), range.endBound().cloned()).map({ Span(it) })

    public open fun <R> withSymbolAndSuffix(f: (String, String) -> R): R =
        this.value.symbol.with({ symbol -> when (val matchVal = this.value.suffix) {
            null -> f(symbol, "")
            else -> {
                val suffix = matchVal
                suffix.with({ suffix1 -> f(symbol, suffix1) })
            }
        } })

    /**
     * Invokes the callback with a `[String]` consisting of each part of the
     * literal's representation. This is done to allow the `ToString` and
     * `Display` implementations to borrow references to symbol values, and
     * both be optimized to reduce overhead.
     */
    public open fun <R> withStringifyParts(f: (List<String>) -> R): R {
        /**
         * Returns a string containing exactly `num` '#' characters.
         * Uses a 256-character source string literal which is always safe to
         * index with a `UByte` index.
         */
        fun getHashesStr(num: UByte): String {
            val HASHES: String = "################################################################################################################################################################################################################################################################"
            return HASHES[0 until num.toInt()]
        }
        return this.withSymbolAndSuffix({ symbol, suffix -> when (val matchVal = this.value.kind) {
            LitKind.Byte -> f(listOf("b'", symbol, "'", suffix))
            LitKind.Char -> f(listOf("'", symbol, "'", suffix))
            LitKind.Str -> f(listOf("\"", symbol, "\"", suffix))
            is LitKind.StrRaw -> {
                val (n) = matchVal
                val hashes = getHashesStr(n)
                f(listOf("r", hashes, "\"", symbol, "\"", hashes, suffix))
            }
            LitKind.ByteStr -> f(listOf("b\"", symbol, "\"", suffix))
            is LitKind.ByteStrRaw -> {
                val (n1) = matchVal
                val hashes1 = getHashesStr(n1)
                f(listOf("br", hashes1, "\"", symbol, "\"", hashes1, suffix))
            }
            LitKind.CStr -> f(listOf("c\"", symbol, "\"", suffix))
            is LitKind.CStrRaw -> {
                val (n2) = matchVal
                val hashes2 = getHashesStr(n2)
                f(listOf("cr", hashes2, "\"", symbol, "\"", hashes2, suffix))
            }
            LitKind.Integer, LitKind.Float, LitKind.ErrWithGuar -> {
                f(listOf(symbol, suffix))
            }
        } })
    }

    /**
     * Returns the unescaped character value if the current literal is a byte character literal.
     */
    public open fun byteCharacterValue(): Result<UByte, ConversionErrorKind> =
        this.value.symbol.with({ symbol -> when (this.value.kind) {
            LitKind.Byte -> unescapeByte(symbol).mapErr({ err -> ConversionErrorKind.FailedToUnescape(err.into()) })
            else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
        } })

    /**
     * Returns the unescaped character value if the current literal is a character literal.
     */
    public open fun characterValue(): Result<Char, ConversionErrorKind> =
        this.value.symbol.with({ symbol -> when (this.value.kind) {
            LitKind.Char -> unescapeChar(symbol).mapErr({ err -> ConversionErrorKind.FailedToUnescape(err.into()) })
            else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
        } })

    /**
     * Returns the unescaped string value if the current literal is a string or a string literal.
     */
    public open fun strValue(): Result<String, ConversionErrorKind> =
        this.value.symbol.with({ symbol -> when (val matchVal = this.value.kind) {
            LitKind.Str -> {
                if (symbol.contains('\\')) {
                    var buf = ""
                    var error = null
                    // Force-inlining here is aggressive but the closure is
                    // called on every char in the string, so it can be hot in
                    // programs with many long strings containing escapes.
                    unescapeStr(symbol, { c -> when (c) { is Result.Ok<*, *> -> {
                        val c1 = c.value
                        buf += c1
                    }; is Result.Err<*, *> -> {
                        val err = c.error
                        if (err.isFatal()) {
                            error = ConversionErrorKind.FailedToUnescape(err.into())
                        }
                    } } })
                    if (error != null) {
                        val error1 = error
                        Result.Err(error1)
                    } else {
                        Result.Ok(buf)
                    }
                } else {
                    Result.Ok(symbol.toString())
                }
            }
            is LitKind.StrRaw -> Result.Ok(symbol.toString())
            else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
        } })

    /**
     * Returns the unescaped string value if the current literal is a c-string or a c-string
     * literal.
     */
    public open fun cstrValue(): Result<CString, ConversionErrorKind> =
        this.value.symbol.with({ symbol -> when (val matchVal = this.value.kind) {
            LitKind.CStr -> {
                var error = null
                val buf = ArrayList(symbol.len())
                unescapeCStr(symbol, { span, res -> when {
                    res is Result.Ok<*, *> && res.value is MixedUnit.Char -> {
                        buf.addAll(c.get().encodeUtf8(List(4) { 0 }).encodeToByteArray().map { it.toUByte() })
                    }
                    res is Result.Ok<*, *> && res.value is MixedUnit.HighByte -> buf.add(b.get())
                    else -> {
                        val err = (res as Result.Err<*, *>).error
                        if (err.isFatal()) {
                            error = ConversionErrorKind.FailedToUnescape(err.into())
                        }
                    }
                } })
                if (error != null) {
                    val error1 = error
                    Result.Err(error1)
                } else {
                    buf.add(0)
                    // Safety: The `buf` content was already checked by the compiler when generating
                    // the literal.
                    Result.Ok(CString.fromVecWithNulUnchecked(buf))
                }
            }
            is LitKind.CStrRaw -> {
                // Raw strings have no escapes so we can convert the symbol
                // directly to a `Lrc<UByte>` after appending the terminating NUL
                // char.
                Result.Ok(CString.new(symbol.toOwned()).expect("invalid cstr literal"))
            }
            else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
        } })

    /**
     * Returns the unescaped string value if the current literal is a byte string or a byte string
     * literal.
     */
    public open fun byteStrValue(): Result<List<UByte>, ConversionErrorKind> =
        this.value.symbol.with({ symbol -> when (val matchVal = this.value.kind) {
            LitKind.ByteStr -> {
                val buf = ArrayList(symbol.len())
                var error = null
                unescapeByteStr(symbol, { res -> when (res) { is Result.Ok<*, *> -> {
                    val b = res.value
                    buf.add(b)
                }; is Result.Err<*, *> -> {
                    val err = res.error
                    if (err.isFatal()) {
                        error = ConversionErrorKind.FailedToUnescape(err.into())
                    }
                } } })
                if (error != null) {
                    val error1 = error
                    Result.Err(error1)
                } else {
                    Result.Ok(buf)
                }
            }
            is LitKind.ByteStrRaw -> {
                // Raw strings have no escapes so we can convert the symbol
                // directly to a `Lrc<UByte>`.
                Result.Ok(symbol.toOwned().intoBytes())
            }
            else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
        } })

    public open fun u8Value(): Result<UByte, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    UByte.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun u16Value(): Result<UShort, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    UShort.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun u32Value(): Result<UInt, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    UInt.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun u64Value(): Result<ULong, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    ULong.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun u128Value(): Result<U128, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    U128.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun i8Value(): Result<Byte, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    Byte.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun i16Value(): Result<Short, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    Short.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun i32Value(): Result<Int, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    Int.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun i64Value(): Result<Long, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    Long.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun i128Value(): Result<I128, ConversionErrorKind> {
        if (this.value.kind != LitKind.Integer) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val symbol1 = stripUnderscores(symbol)
                    val (number, base) = parseNumber(symbol1)
                    I128.fromStrRadix(number, base.toUInt()).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun f16Value(): Result<F16, ConversionErrorKind> {
        if (this.value.kind != LitKind.Float) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val number = stripUnderscores(symbol)
                    F16.fromStr(number).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun f32Value(): Result<Float, ConversionErrorKind> {
        if (this.value.kind != LitKind.Float) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val number = stripUnderscores(symbol)
                    Float.fromStr(number).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    public open fun f64Value(): Result<Double, ConversionErrorKind> {
        if (this.value.kind != LitKind.Float) {
            return Result.Err(ConversionErrorKind.InvalidLiteralKind)
        }
        return this.withSymbolAndSuffix({ symbol, suffix ->
            when (suffix) {
                "" -> {
                    val number = stripUnderscores(symbol)
                    Double.fromStr(number).mapErr({ ConversionErrorKind.InvalidLiteralKind })
                }
                else -> Result.Err(ConversionErrorKind.InvalidLiteralKind)
            }
        })
    }

    /**
     * Prints the literal as a string that should be losslessly convertible
     * back into the same literal (except for possible rounding for floating point literals).
     */
    public open fun fmt(f: StringBuilder) {
        this.withStringifyParts(closure@{ parts ->
            for (part in parts) {
                f.append((part).toString())
            }
            Result.Ok(Unit)
        })
    }

    public open fun fmtDebug(f: StringBuilder) {
        DebugStruct(f, "Literal").field("kind", "${this.value.kind}").field("symbol", this.value.symbol).field("suffix", "${this.value.suffix}").field("span", this.value.span).finish()
    }

    public companion object {
        public operator fun invoke(
            value: io.github.kotlinmania.rustlang.procmacro.bridge.Literal<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol>,
        ): Literal = Literal(
            valueCtorArg = value,
        )

        public fun new(kind: LitKind, value: String, suffix: String?): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = kind, symbol = Symbol.new(value), suffix = suffix?.let(Symbol::new), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u8Suffixed(n: UByte): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("u8"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u16Suffixed(n: UShort): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("u16"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u32Suffixed(n: UInt): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("u32"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u64Suffixed(n: ULong): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("u64"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u128Suffixed(n: U128): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("u128"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun usizeSuffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("usize"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i8Suffixed(n: Byte): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("i8"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i16Suffixed(n: Short): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("i16"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i32Suffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("i32"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i64Suffixed(n: Long): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("i64"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i128Suffixed(n: I128): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("i128"), span = Span.callSite().value))

        /**
         * Creates a new suffixed integer literal with the specified value.
         *
         * This function will create an integer like `1u32` where the integer
         * value specified is the first part of the token and the integral is
         * also suffixed at the end.
         * Literals created from negative numbers might not survive round-trips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun isizeSuffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = Symbol.new("isize"), span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u8Unsuffixed(n: UByte): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u16Unsuffixed(n: UShort): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u32Unsuffixed(n: UInt): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u64Unsuffixed(n: ULong): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun u128Unsuffixed(n: U128): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun usizeUnsuffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i8Unsuffixed(n: Byte): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i16Unsuffixed(n: Short): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i32Unsuffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i64Unsuffixed(n: Long): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun i128Unsuffixed(n: I128): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed integer literal with the specified value.
         *
         * This function will create an integer like `1` where the integer
         * value specified is the first part of the token. No suffix is
         * specified on this token, meaning that invocations like
         * `Literal.i8Unsuffixed(1)` are equivalent to
         * `Literal.u32Unsuffixed(1)`.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * Literals created through this method have the `Span.callSite()`
         * span by default, which can be configured with the `setSpan` method
         * below.
         */
        public fun isizeUnsuffixed(n: Int): Literal = Literal(io.github.kotlinmania.rustlang.procmacro.bridge.Literal(kind = LitKind.Integer, symbol = Symbol.new(n.toString()), suffix = null, span = Span.callSite().value))

        /**
         * Creates a new unsuffixed floating-point literal.
         *
         * This constructor is similar to those like `Literal.i8Unsuffixed` where
         * the float's value is emitted directly into the token but no suffix is
         * used, so it may be inferred to be a `Double` later in the compiler.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * # Panics
         *
         * This function requires that the specified float is finite, for
         * example if it is infinity or NaN this function will panic.
         */
        public fun f32Unsuffixed(n: Float): Literal {
            if (!n.isFinite()) {
                error("Invalid float literal $n")
            }
            val repr = n.toString()
            if (!repr.contains('.')) {
                repr.pushStr(".0")
            }
            return Literal.new(LitKind.Float, repr, null)
        }

        /**
         * Creates a new suffixed floating-point literal.
         *
         * This constructor will create a literal like `1.0f32` where the value
         * specified is the preceding part of the token and `Float` is the suffix of
         * the token. This token will always be inferred to be an `Float` in the
         * compiler.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * # Panics
         *
         * This function requires that the specified float is finite, for
         * example if it is infinity or NaN this function will panic.
         */
        public fun f32Suffixed(n: Float): Literal {
            if (!n.isFinite()) {
                error("Invalid float literal $n")
            }
            return Literal.new(LitKind.Float, n.toString(), "f32")
        }

        /**
         * Creates a new unsuffixed floating-point literal.
         *
         * This constructor is similar to those like `Literal.i8Unsuffixed` where
         * the float's value is emitted directly into the token but no suffix is
         * used, so it may be inferred to be a `Double` later in the compiler.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * # Panics
         *
         * This function requires that the specified float is finite, for
         * example if it is infinity or NaN this function will panic.
         */
        public fun f64Unsuffixed(n: Double): Literal {
            if (!n.isFinite()) {
                error("Invalid float literal $n")
            }
            val repr = n.toString()
            if (!repr.contains('.')) {
                repr.pushStr(".0")
            }
            return Literal.new(LitKind.Float, repr, null)
        }

        /**
         * Creates a new suffixed floating-point literal.
         *
         * This constructor will create a literal like `1.0f64` where the value
         * specified is the preceding part of the token and `Double` is the suffix of
         * the token. This token will always be inferred to be an `Double` in the
         * compiler.
         * Literals created from negative numbers might not survive roundtrips through
         * `TokenStream` or strings and may be broken into two tokens (`-` and positive literal).
         *
         * # Panics
         *
         * This function requires that the specified float is finite, for
         * example if it is infinity or NaN this function will panic.
         */
        public fun f64Suffixed(n: Double): Literal {
            if (!n.isFinite()) {
                error("Invalid float literal $n")
            }
            return Literal.new(LitKind.Float, n.toString(), "f64")
        }

        /**
         * String literal.
         */
        public fun string(string: String): Literal {
            val escape = EscapeOptions(escapeSingleQuote = false, escapeDoubleQuote = true, escapeNonascii = false)
            val repr = escapeBytes(string.encodeToByteArray().map { it.toUByte() }, escape)
            return Literal.new(LitKind.Str, repr, null)
        }

        /**
         * Character literal.
         */
        public fun character(ch: Char): Literal {
            val escape = EscapeOptions(escapeSingleQuote = true, escapeDoubleQuote = false, escapeNonascii = false)
            val repr = escapeBytes(ch.encodeUtf8(UByteArray(4) { 0u.toUByte() }).encodeToByteArray().map { it.toUByte() }, escape)
            return Literal.new(LitKind.Char, repr, null)
        }

        /**
         * Byte character literal.
         */
        public fun byteCharacter(byte: UByte): Literal {
            val escape = EscapeOptions(escapeSingleQuote = true, escapeDoubleQuote = false, escapeNonascii = true)
            val repr = escapeBytes(ubyteArrayOf(byte), escape)
            return Literal.new(LitKind.Byte, repr, null)
        }

        /**
         * Byte string literal.
         */
        public fun byteString(bytes: List<UByte>): Literal {
            val escape = EscapeOptions(escapeSingleQuote = false, escapeDoubleQuote = true, escapeNonascii = true)
            val repr = escapeBytes(bytes, escape)
            return Literal.new(LitKind.ByteStr, repr, null)
        }

        /**
         * C string literal.
         */
        public fun cString(string: CStr): Literal {
            val escape = EscapeOptions(escapeSingleQuote = false, escapeDoubleQuote = true, escapeNonascii = false)
            val repr = escapeBytes(string.toBytes(), escape)
            return Literal.new(LitKind.CStr, repr, null)
        }

        /**
         * Parse a single literal from its stringified representation.
         *
         * In order to parse successfully, the input string must not contain anything
         * but the literal token. Specifically, it must not contain whitespace or
         * comments in addition to the literal.
         *
         * The resulting literal token will have a `Span.callSite()` span.
         *
         * NOTE: some errors may cause panics instead of returning `LexError`. We
         * reserve the right to change these errors into `LexError`s later.
         */
        public fun fromStr(src: String): Result<Literal, LexError> =
            run {
                val matchVal = BridgeMethods.literalFromStr(src)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val literal = matchVal.value
                    Result.Ok(Literal(literal))
                }; is Result.Err<*, *> -> {
                    val msg = matchVal.error
                    Result.Err(LexError(msg))
                } }
            }
    }
}

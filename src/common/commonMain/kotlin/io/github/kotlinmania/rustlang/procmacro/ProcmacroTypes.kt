package io.github.kotlinmania.rustlang.procmacro

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Symbol
import io.github.kotlinmania.rustlang.std.env.VarError
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.procmacro.bridge.Methods as BridgeMethods

/**
 * Mostly relating to malformed escape sequences, but also a few other problems.
 */
public sealed class EscapeErrorKind {
    /**
     * Expected 1 char, but 0 were found.
     */
    public data object ZeroChars : EscapeErrorKind()
    /**
     * Expected 1 char, but more than 1 were found.
     */
    public data object MoreThanOneChar : EscapeErrorKind()
    /**
     * Escaped '\' character without continuation.
     */
    public data object LoneSlash : EscapeErrorKind()
    /**
     * Invalid escape character (e.g. '\z').
     */
    public data object InvalidEscape : EscapeErrorKind()
    /**
     * Raw '\r' encountered.
     */
    public data object BareCarriageReturn : EscapeErrorKind()
    /**
     * Raw '\r' encountered in raw string.
     */
    public data object BareCarriageReturnInRawString : EscapeErrorKind()
    /**
     * Unescaped character that was expected to be escaped (e.g. raw '\t').
     */
    public data object EscapeOnlyChar : EscapeErrorKind()
    /**
     * Numeric character escape is too short (e.g. '\x1').
     */
    public data object TooShortHexEscape : EscapeErrorKind()
    /**
     * Invalid character in numeric escape (e.g. '\xz')
     */
    public data object InvalidCharInHexEscape : EscapeErrorKind()
    /**
     * Character code in numeric escape is non-ascii (e.g. '\xFF').
     */
    public data object OutOfRangeHexEscape : EscapeErrorKind()
    /**
     * '\u' not followed by '('.
     */
    public data object NoBraceInUnicodeEscape : EscapeErrorKind()
    /**
     * Non-hexadecimal value in '\u(..)'.
     */
    public data object InvalidCharInUnicodeEscape : EscapeErrorKind()
    /**
     * '\u()'
     */
    public data object EmptyUnicodeEscape : EscapeErrorKind()
    /**
     * No closing brace in '\u(..)', e.g. '\u(12'.
     */
    public data object UnclosedUnicodeEscape : EscapeErrorKind()
    /**
     * '\u(_12)'
     */
    public data object LeadingUnderscoreUnicodeEscape : EscapeErrorKind()
    /**
     * More than 6 characters in '\u(..)', e.g. '\u(10FFFF_FF)'
     */
    public data object OverlongUnicodeEscape : EscapeErrorKind()
    /**
     * Invalid in-bound unicode character code, e.g. '\u(DFFF)'.
     */
    public data object LoneSurrogateUnicodeEscape : EscapeErrorKind()
    /**
     * Out of bounds unicode character code, e.g. '\u(FFFFFF)'.
     */
    public data object OutOfRangeUnicodeEscape : EscapeErrorKind()
    /**
     * Unicode escape code in byte literal.
     */
    public data object UnicodeEscapeInByte : EscapeErrorKind()
    /**
     * Non-ascii character in byte literal, byte string literal, or raw byte string literal.
     */
    public data object NonAsciiCharInByte : EscapeErrorKind()
    /**
     * `\0` in a C string literal.
     */
    public data object NulInCStr : EscapeErrorKind()
    /**
     * After a line ending with '\', the next line contains whitespace
     * characters that are not skipped.
     */
    public data object UnskippedWhitespaceWarning : EscapeErrorKind()
    /**
     * After a line ending with '\', multiple lines are skipped.
     */
    public data object MultipleSkippedLinesWarning : EscapeErrorKind()

    public fun rustEquals(other: EscapeErrorKind): Boolean = this == other

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        return run {
f.append(when (this) {
            EscapeErrorKind.ZeroChars -> "zero chars"
            EscapeErrorKind.MoreThanOneChar -> "more than one char"
            EscapeErrorKind.LoneSlash -> "lone slash"
            EscapeErrorKind.InvalidEscape -> "invalid escape"
            EscapeErrorKind.BareCarriageReturn -> "bare carriage return"
            EscapeErrorKind.BareCarriageReturnInRawString -> "bare carriage return in raw string"
            EscapeErrorKind.EscapeOnlyChar -> "escape only char"
            EscapeErrorKind.TooShortHexEscape -> "too short hex escape"
            EscapeErrorKind.InvalidCharInHexEscape -> "invalid char in hex escape"
            EscapeErrorKind.OutOfRangeHexEscape -> "out of range hex escape"
            EscapeErrorKind.NoBraceInUnicodeEscape -> "no brace in unicode escape"
            EscapeErrorKind.InvalidCharInUnicodeEscape -> "invalid char in unicode escape"
            EscapeErrorKind.EmptyUnicodeEscape -> "empty unicode escape"
            EscapeErrorKind.UnclosedUnicodeEscape -> "unclosed unicode escape"
            EscapeErrorKind.LeadingUnderscoreUnicodeEscape -> "leading underscore unicode escape"
            EscapeErrorKind.OverlongUnicodeEscape -> "overlong unicode escape"
            EscapeErrorKind.LoneSurrogateUnicodeEscape -> "lone surrogate unicode escape"
            EscapeErrorKind.OutOfRangeUnicodeEscape -> "out of range unicode escape"
            EscapeErrorKind.UnicodeEscapeInByte -> "unicode escape in byte"
            EscapeErrorKind.NonAsciiCharInByte -> "non ascii char in byte"
            EscapeErrorKind.NulInCStr -> "nul in CStr"
            EscapeErrorKind.UnskippedWhitespaceWarning -> "unskipped whitespace warning"
            EscapeErrorKind.MultipleSkippedLinesWarning -> "multiple skipped lines warning"
        })
Result.Ok<Unit, Error>(Unit)
}
    }
}

/**
 * Errors returned when trying to retrieve a literal unescaped value.
 */
public sealed class ConversionErrorKind {
    /**
     * The literal failed to be escaped, take a look at [EscapeError] for more information.
     */
    public data class FailedToUnescape(public var value: EscapeError) : ConversionErrorKind()
    /**
     * Trying to convert a literal with the wrong type.
     */
    public data object InvalidLiteralKind : ConversionErrorKind()

    public fun rustEquals(other: ConversionErrorKind): Boolean = this == other
}

/**
 * An iterator over `TokenStream`s `TokenTree`s.
 * The iteration is "shallow", e.g., the iterator doesn't recurse into delimited groups,
 * and returns whole groups as token trees.
 */
public open class IntoIter internal constructor(
    valueCtorArg: ArrayDeque<bridge.TokenTree<bridge.TokenStream, bridge.Span, bridge.Symbol>>,
) : Iterable<TokenTree> {
    public var value: ArrayDeque<bridge.TokenTree<bridge.TokenStream, bridge.Span, bridge.Symbol>> = valueCtorArg
        internal set

    public var field0: ArrayDeque<bridge.TokenTree<bridge.TokenStream, bridge.Span, bridge.Symbol>>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<TokenTree> = object : AbstractIterator<TokenTree>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter = IntoIter(value)

    public open fun next(): TokenTree? =
        this.value.next()?.let({ tree -> when (tree) {
            is bridge.TokenTree.Group<*, *, *> -> {
                val (tt) = tree
                TokenTree.Group(Group(tt))
            }
            is bridge.TokenTree.Punct<*, *, *> -> {
                val (tt1) = tree
                TokenTree.Punct(Punct(tt1))
            }
            is bridge.TokenTree.Ident<*, *, *> -> {
                val (tt2) = tree
                TokenTree.Ident(Ident(tt2))
            }
            is bridge.TokenTree.Literal<*, *, *> -> {
                val (tt3) = tree
                TokenTree.Literal(Literal(tt3))
            }
        } })

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.value.size; Pair(n, n) }

    public open fun count(): Int = this.value.count()

    public companion object {
        public operator fun invoke(
            value: ArrayDeque<bridge.TokenTree<bridge.TokenStream, bridge.Span, bridge.Symbol>>,
        ): IntoIter = IntoIter(
            valueCtorArg = value,
        )
    }
}

public fun TokenStream.intoIter(): IntoIter = IntoIter(ArrayDeque(listOfNotNull(this.value?.let(BridgeMethods::tsIntoTrees).unwrapOrDefault())))

/**
 * A single token or a delimited sequence of token trees (e.g., `[1, (), ..]`).
 */
public sealed class TokenTree {
    /**
     * A token stream surrounded by bracket delimiters.
     */
    public data class Group(public var value: Group) : TokenTree()
    /**
     * An identifier.
     */
    public data class Ident(public var value: Ident) : TokenTree()
    /**
     * A single punctuation character (`+`, `,`, `$`, etc.).
     */
    public data class Punct(public var value: Punct) : TokenTree()
    /**
     * A literal character (``a``), string (`"hello"`), number (`2.3`), etc.
     */
    public data class Literal(public var value: Literal) : TokenTree()

    /**
     * Returns the span of this tree, delegating to the `span` method of
     * the contained token or a delimited stream.
     */
    public open fun span(): Span =
        when (this) {
            is TokenTree.Group<*, *, *> -> {
                val (t) = this as TokenTree.Group<*, *, *>
                t.span()
            }
            is TokenTree.Ident<*, *, *> -> {
                val (t1) = this as TokenTree.Ident<*, *, *>
                t1.span()
            }
            is TokenTree.Punct<*, *, *> -> {
                val (t2) = this as TokenTree.Punct<*, *, *>
                t2.span()
            }
            is TokenTree.Literal<*, *, *> -> {
                val (t3) = this as TokenTree.Literal<*, *, *>
                t3.span()
            }
        }

    /**
     * Configures the span for *only this token*.
     *
     * Note that if this token is a `Group` then this method will not configure
     * the span of each of the internal tokens, this will simply delegate to
     * the `setSpan` method of each variant.
     */
    public open fun setSpan(span: Span) {
        when (this) {
            is TokenTree.Group<*, *, *> -> {
                val (t) = this as TokenTree.Group<*, *, *>
                t.setSpan(span)
            }
            is TokenTree.Ident<*, *, *> -> {
                val (t1) = this as TokenTree.Ident<*, *, *>
                t1.setSpan(span)
            }
            is TokenTree.Punct<*, *, *> -> {
                val (t2) = this as TokenTree.Punct<*, *, *>
                t2.setSpan(span)
            }
            is TokenTree.Literal<*, *, *> -> {
                val (t3) = this as TokenTree.Literal<*, *, *>
                t3.setSpan(span)
            }
        }
    }

    /**
     * Prints token tree in a form convenient for debugging.
     */
    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        // Each of these has the name in the struct type in the derived debug,
        // so don't bother with an extra layer of indirection
        return when (this) {
            is TokenTree.Group<*, *, *> -> {
                val (tt) = this as TokenTree.Group<*, *, *>
                tt.fmt(f)
            }
            is TokenTree.Ident<*, *, *> -> {
                val (tt1) = this as TokenTree.Ident<*, *, *>
                tt1.fmt(f)
            }
            is TokenTree.Punct<*, *, *> -> {
                val (tt2) = this as TokenTree.Punct<*, *, *>
                tt2.fmt(f)
            }
            is TokenTree.Literal<*, *, *> -> {
                val (tt3) = this as TokenTree.Literal<*, *, *>
                tt3.fmt(f)
            }
        }
    }

    /**
     * Prints the token tree as a string that is supposed to be losslessly convertible back
     * into the same token tree (modulo spans), except for possibly `TokenTree.Group`s
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
    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TokenTree.Group<*, *, *> -> {
                val (t) = this as TokenTree.Group<*, *, *>
                run { f.append("$t"); Result.Ok<Unit, Error>(Unit) }
            }
            is TokenTree.Ident<*, *, *> -> {
                val (t1) = this as TokenTree.Ident<*, *, *>
                run { f.append("$t"); Result.Ok<Unit, Error>(Unit) }
            }
            is TokenTree.Punct<*, *, *> -> {
                val (t2) = this as TokenTree.Punct<*, *, *>
                run { f.append("$t"); Result.Ok<Unit, Error>(Unit) }
            }
            is TokenTree.Literal<*, *, *> -> {
                val (t3) = this as TokenTree.Literal<*, *, *>
                run { f.append("$t"); Result.Ok<Unit, Error>(Unit) }
            }
        }

    public companion object {
        public fun from(g: Group): TokenTree = TokenTree.Group(g)

        public fun from(g: Ident): TokenTree = TokenTree.Ident(g)

        public fun from(g: Punct): TokenTree = TokenTree.Punct(g)

        public fun from(g: Literal): TokenTree = TokenTree.Literal(g)
    }
}

/**
 * Describes how a sequence of token trees is delimited.
 */
public sealed class Delimiter {
    /**
     * `(...)`
     */
    public data object Parenthesis : Delimiter()
    /**
     * `( ... )`
     */
    public data object Brace : Delimiter()
    /**
     * `[...]`
     */
    public data object Bracket : Delimiter()
    /**
     * `∅ ... ∅`
     * An invisible delimiter, that may, for example, appear around tokens coming from a
     * "macro variable" `$ var`. It is important to preserve operator priorities in cases like
     * `$ var * 3` where `$ var` is `1 + 2`.
     * Invisible delimiters might not survive roundtrip of a token stream through a string.
     *
     * <div class="warning">
     *
     * Note: rustc currently can ignore the grouping of tokens delimited by `None` in the output
     * of a procMacro. Only `None`-delimited groups created by a macroRules macro in the input
     * of a procMacro macro are preserved, and only in very specific circumstances.
     * Any `None`-delimited groups (re)created by a procMacro will therefore not preserve
     * operator priorities as indicated above. The other `Delimiter` variants should be used
     * instead in this context. This is a rustc bug. For details, see
     * [rust-lang/rust#67062](https://github.com/rust-lang/rust/issues/67062).
     *
     * </div>
     */
    public data object None : Delimiter()

    public fun rustEquals(other: Delimiter): Boolean = this == other
}

/**
 * Indicates whether a `Punct` token can join with the following token
 * to form a multi-character operator.
 */
public sealed class Spacing {
    /**
     * A `Punct` token can join with the following token to form a multi-character operator.
     *
     * In token streams constructed using proc macro interfaces, `Joint` punctuation tokens can be
     * followed by any other tokens. However, in token streams parsed from source code, the
     * compiler will only set spacing to `Joint` in the following cases.
     * - When a `Punct` is immediately followed by another `Punct` without a whitespace. E.g. `+`
     *   is `Joint` in `+=` and `+ +`.
     * - When a single quote `'` is immediately followed by an identifier without a whitespace.
     *   E.g. `'` is `Joint` in `scope`.
     *
     * This list may be extended in the future to enable more token combinations.
     */
    public data object Joint : Spacing()
    /**
     * A `Punct` token cannot join with the following token to form a multi-character operator.
     *
     * `Alone` punctuation tokens can be followed by any other tokens. In token streams parsed
     * from source code, the compiler will set spacing to `Alone` in all cases not covered by the
     * conditions for `Joint` above. E.g. `+` is `Alone` in `+ =`, `+ ident` and `+ ()`. In
     * particular, tokens not followed by anything will be marked as `Alone`.
     */
    public data object Alone : Spacing()

    public fun rustEquals(other: Spacing): Boolean = this == other
}

public sealed class Base(
    public val value: UInt,
) {
    public data object Decimal : Base(10u)
    public data object Binary : Base(2u)
    public data object Octal : Base(8u)
    public data object Hexadecimal : Base(16u)

    public fun rustEquals(other: Base): Boolean = this == other
}

/**
 * Retrieve an environment variable and add it to build dependency info.
 * The build system executing the compiler will know that the variable was accessed during
 * compilation, and will be able to rerun the build when the value of that variable changes.
 * Besides the dependency tracking this function should be equivalent to `env.var` from the
 * standard library, except that the argument must be UTF-8.
 */
public fun <K> envVar(key: K): Result<String, VarError> where K : AsRef<OsStr>, K : AsRef<String> {
    val key1: String = key.asRef()
    val value = env.variable(key1)
    BridgeMethods.trackEnvVar(key1, value.asDeref().ok())
    return value
}

/**
 * Track a file or directory explicitly.
 *
 * Commonly used for tracking asset preprocessing.
 */
public fun path(path: Path) {
    val path1: String = path.toStr().unwrap()
    BridgeMethods.trackPath(path1)
}

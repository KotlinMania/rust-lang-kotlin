package io.github.kotlinmania.rustlang.procmacro.bridge

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.procmacro.Span
import io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream
import io.github.kotlinmania.rustlang.procmacro.bridge.symbol.Symbol

public sealed class ApiTags {
    public data object TrackEnvVar : ApiTags()
    public data object TrackPath : ApiTags()
    public data object LiteralFromStr : ApiTags()
    public data object EmitDiagnostic : ApiTags()
    public data object TsDrop : ApiTags()
    public data object TsClone : ApiTags()
    public data object TsIsEmpty : ApiTags()
    public data object TsExpandExpr : ApiTags()
    public data object TsFromStr : ApiTags()
    public data object TsToString : ApiTags()
    public data object TsFromTokenTree : ApiTags()
    public data object TsConcatTrees : ApiTags()
    public data object TsConcatStreams : ApiTags()
    public data object TsIntoTrees : ApiTags()
    public data object SpanDebug : ApiTags()
    public data object SpanParent : ApiTags()
    public data object SpanSource : ApiTags()
    public data object SpanByteRange : ApiTags()
    public data object SpanStart : ApiTags()
    public data object SpanEnd : ApiTags()
    public data object SpanLine : ApiTags()
    public data object SpanColumn : ApiTags()
    public data object SpanFile : ApiTags()
    public data object SpanLocalFile : ApiTags()
    public data object SpanJoin : ApiTags()
    public data object SpanSubspan : ApiTags()
    public data object SpanResolvedAt : ApiTags()
    public data object SpanSourceText : ApiTags()
    public data object SpanSaveSpan : ApiTags()
    public data object SpanRecoverProcMacroSpan : ApiTags()
    public data object SymbolNormalizeAndValidateIdent : ApiTags()

    private fun kmDiscriminant(): Int =
        when (this) {
        TrackEnvVar -> 0
        TrackPath -> 1
        LiteralFromStr -> 2
        EmitDiagnostic -> 3
        TsDrop -> 4
        TsClone -> 5
        TsIsEmpty -> 6
        TsExpandExpr -> 7
        TsFromStr -> 8
        TsToString -> 9
        TsFromTokenTree -> 10
        TsConcatTrees -> 11
        TsConcatStreams -> 12
        TsIntoTrees -> 13
        SpanDebug -> 14
        SpanParent -> 15
        SpanSource -> 16
        SpanByteRange -> 17
        SpanStart -> 18
        SpanEnd -> 19
        SpanLine -> 20
        SpanColumn -> 21
        SpanFile -> 22
        SpanLocalFile -> 23
        SpanJoin -> 24
        SpanSubspan -> 25
        SpanResolvedAt -> 26
        SpanSourceText -> 27
        SpanSaveSpan -> 28
        SpanRecoverProcMacroSpan -> 29
        SymbolNormalizeAndValidateIdent -> 30
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

/**
 * Helper to wrap associated types to allow trait impl dispatch.
 * That is, normally a pair of impls for `T.Foo` and `T.Bar`
 * can overlap, but if the impls are, instead, on types like
 * `Marked<T.Foo, Foo>` and `Marked<T.Bar, Bar>`, they can't.
 */
public interface Mark<T, Unmarked> {
    public fun mark(unmarked: Unmarked): T

    public fun unmark(): Unmarked
}

public sealed class LitKind : Mark<LitKind, LitKind> {
    public data object Byte : LitKind()
    public data object Char : LitKind()
    public data object Integer : LitKind()
    public data object Float : LitKind()
    public data object Str : LitKind()
    public data class StrRaw(public var value: UByte) : LitKind()
    public data object ByteStr : LitKind()
    public data class ByteStrRaw(public var value: UByte) : LitKind()
    public data object CStr : LitKind()
    public data class CStrRaw(public var value: UByte) : LitKind()
    // This should have an `ErrorGuaranteed`, except that type isn't available
    // in this crate. (Imagine it is there.) Hence the `WithGuar` suffix. Must
    // only be constructed in `LitKind.fromInternal`, where an
    // `ErrorGuaranteed` is available.
    public data object ErrWithGuar : LitKind()

    public fun rustEquals(other: LitKind): Boolean = this == other

    public override fun unmark(): LitKind = this

    public companion object {
        public fun mark(unmarked: LitKind): LitKind = unmarked
    }
}

public sealed class IdentKind : Mark<IdentKind, IdentKind> {
    public data object Normal : IdentKind()
    public data object Raw : IdentKind()
    public data object ForcedKeyword : IdentKind()

    public fun rustEquals(other: IdentKind): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Normal -> 0
        Raw -> 1
        ForcedKeyword -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public override fun unmark(): IdentKind = this

    public open fun prefix(): String? =
        when (this) {
            IdentKind.Normal -> null
            IdentKind.Raw -> "r#"
            IdentKind.ForcedKeyword -> "k#"
        }

    public companion object {
        public fun mark(unmarked: IdentKind): IdentKind = unmarked
    }
}

public sealed class TokenTree<TokenStream, Span, Symbol> {
    public data class Group<TokenStream, Span, Symbol>(public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Group<TokenStream, Span>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Punct<TokenStream, Span, Symbol>(public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Punct<Span>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Ident<TokenStream, Span, Symbol>(public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Ident<Span, Symbol>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Literal<TokenStream, Span, Symbol>(public var value: io.github.kotlinmania.rustlang.procmacro.bridge.Literal<Span, Symbol>) : TokenTree<TokenStream, Span, Symbol>()
}

public fun <Unmarked, TokenStream : Mark<TokenStream, Unmarked>, Span : Mark<Span, Unmarked>, Symbol : Mark<Symbol, Unmarked>> markTokenTreeTokenStreamSpanSymbol(unmarked: TokenTree<Unmarked, Unmarked, Unmarked>): TokenTree<TokenStream, Span, Symbol> =
    when (unmarked) {
        is TokenTree.Group<*, *, *> -> {
            val (tt) = unmarked
            TokenTree.Group(Mark.mark(tt))
        }
        is TokenTree.Punct<*, *, *> -> {
            val (tt1) = unmarked
            TokenTree.Punct(Mark.mark(tt1))
        }
        is TokenTree.Ident<*, *, *> -> {
            val (tt2) = unmarked
            TokenTree.Ident(Mark.mark(tt2))
        }
        is TokenTree.Literal<*, *, *> -> {
            val (tt3) = unmarked
            TokenTree.Literal(Mark.mark(tt3))
        }
    }

public fun <Unmarked, TokenStream : Mark<TokenStream, Unmarked>, Span : Mark<Span, Unmarked>, Symbol : Mark<Symbol, Unmarked>> TokenTree<TokenStream, Span, Symbol>.unmark(): TokenTree<Unmarked, Unmarked, Unmarked> =
    when (this) {
        is TokenTree.Group<*, *, *> -> {
            val (tt) = this as TokenTree.Group<TokenStream, Span, Symbol>
            TokenTree.Group(tt.unmark())
        }
        is TokenTree.Punct<*, *, *> -> {
            val (tt1) = this as TokenTree.Punct<TokenStream, Span, Symbol>
            TokenTree.Punct(tt1.unmark())
        }
        is TokenTree.Ident<*, *, *> -> {
            val (tt2) = this as TokenTree.Ident<TokenStream, Span, Symbol>
            TokenTree.Ident(tt2.unmark())
        }
        is TokenTree.Literal<*, *, *> -> {
            val (tt3) = this as TokenTree.Literal<TokenStream, Span, Symbol>
            TokenTree.Literal(tt3.unmark())
        }
    }

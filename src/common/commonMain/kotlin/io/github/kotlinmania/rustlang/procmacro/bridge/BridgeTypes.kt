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

public sealed class LitKind : Mark<LitKind, Any?, LitKind> {
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

public sealed class IdentKind : Mark<IdentKind, Any?, IdentKind> {
    public data object Normal : IdentKind()
    public data object Raw : IdentKind()
    public data object ForcedKeyword : IdentKind()

    public fun rustEquals(other: IdentKind): Boolean = this == other

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

public sealed class TokenTree<TokenStream, Span, Symbol> : Mark<TokenTree<TokenStream, Span, Symbol>, Any?, TokenTree<Unmarked, Unmarked, Unmarked>> {
    public data class Group<TokenStream, Span, Symbol>(public var value: Group<TokenStream, Span>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Punct<TokenStream, Span, Symbol>(public var value: Punct<Span>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Ident<TokenStream, Span, Symbol>(public var value: Ident<Span, Symbol>) : TokenTree<TokenStream, Span, Symbol>()
    public data class Literal<TokenStream, Span, Symbol>(public var value: Literal<Span, Symbol>) : TokenTree<TokenStream, Span, Symbol>()

    public override fun unmark(): TokenTree<Unmarked, Unmarked, Unmarked> =
        when (this) {
            is TokenTree.Group -> {
                val (tt) = this
                TokenTree.Group(tt.unmark())
            }
            is TokenTree.Punct -> {
                val (tt1) = this
                TokenTree.Punct(tt1.unmark())
            }
            is TokenTree.Ident -> {
                val (tt2) = this
                TokenTree.Ident(tt2.unmark())
            }
            is TokenTree.Literal -> {
                val (tt3) = this
                TokenTree.Literal(tt3.unmark())
            }
        }

    public companion object {
        public fun <Unmarked, TokenStream, Span, Symbol> mark(unmarked: TokenTree<Unmarked, Unmarked, Unmarked>): TokenTree<TokenStream, Span, Symbol> =
            when (unmarked) {
                is TokenTree.Group -> {
                    val (tt) = unmarked
                    TokenTree.Group(Mark.Mark(tt))
                }
                is TokenTree.Punct -> {
                    val (tt1) = unmarked
                    TokenTree.Punct(Mark.Mark(tt1))
                }
                is TokenTree.Ident -> {
                    val (tt2) = unmarked
                    TokenTree.Ident(Mark.Mark(tt2))
                }
                is TokenTree.Literal -> {
                    val (tt3) = unmarked
                    TokenTree.Literal(Mark.Mark(tt3))
                }
            }
    }
}

// port-lint: source to_tokens.rs
package io.github.kotlinmania.rustlang.procmacro.totokens

import io.github.kotlinmania.rustlang.ConcatTreesHelper
import io.github.kotlinmania.rustlang.Group
import io.github.kotlinmania.rustlang.Ident
import io.github.kotlinmania.rustlang.Literal
import io.github.kotlinmania.rustlang.Punct
import io.github.kotlinmania.rustlang.Span
import io.github.kotlinmania.rustlang.TokenStream
import io.github.kotlinmania.rustlang.TokenTree
import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr

/**
 * Types that can be interpolated inside a [quote] invocation.
 */
public interface ToTokens {
    /**
     * Write `self` to the given `TokenStream`.
     *
     * # Example
     *
     * Example implementation for a struct representing Rust paths like
     * `std.cmp.PartialEq`:
     *
     * ```kotlin
     * import procmacro.Punct
     * import procmacro.Spacing
     * import procmacro.ToTokens
     * import procmacro.TokenStream
     * import procmacro.TokenTree
     * import std.iter
     *
     * @feature(procMacroTotokens)
     * public fun doctestEntry() {
     *     open class Path(public var global: Boolean, public var segments: List<PathSegment>) : ToTokens {
     *         public open fun toTokens(tokens: TokenStream) {
     *             for ((i, segment) in this.segments.mapIndexed { index, item -> Pair(index, item) }) {
     *                 if (i > 0 || this.global) {
     *                     // Double colon `.`
     *                     tokens.extend(listOf(TokenTree.from(Punct.new(':', Spacing.Joint))))
     *                     tokens.extend(listOf(TokenTree.from(Punct.new(':', Spacing.Alone))))
     *                 }
     *                 segment.toTokens(tokens)
     *             }
     *         }
     *     }
     * }
     * ```
     */
    public fun toTokens(tokens: TokenStream)

    /**
     * Convert `self` directly into a `TokenStream` object.
     *
     * This method is implicitly implemented using `toTokens`, and acts as a
     * convenience method for consumers of the `ToTokens` trait.
     */
    public fun toTokenStream(): TokenStream {
        val tokens = TokenStream.new()
        this.toTokens(tokens)
        return tokens
    }

    /**
     * Convert `self` directly into a `TokenStream` object.
     *
     * This method is implicitly implemented using `toTokens`, and acts as a
     * convenience method for consumers of the `ToTokens` trait.
     */
    public fun intoTokenStream(): TokenStream = this.toTokenStream()
}

public fun TokenTree.toTokens(tokens: TokenStream) {
    tokens.extendOne(this.clone())
}

public fun TokenTree.intoTokenStream(): TokenStream {
    val builder = ConcatTreesHelper.new(1)
    builder.push(this)
    return builder.build()
}

public fun TokenStream.toTokens(tokens: TokenStream) {
    tokens.extend(this.clone())
}

public fun TokenStream.intoTokenStream(): TokenStream = this

public fun Literal.toTokens(tokens: TokenStream) {
    tokens.extendOne(TokenTree.from(this.clone()))
}

public fun Ident.toTokens(tokens: TokenStream) {
    tokens.extendOne(TokenTree.from(this.clone()))
}

public fun Punct.toTokens(tokens: TokenStream) {
    tokens.extendOne(TokenTree.from(this.clone()))
}

public fun Group.toTokens(tokens: TokenStream) {
    tokens.extendOne(TokenTree.from(this.clone()))
}

public fun <T : ToTokens> T?.toTokens(tokens: TokenStream) {
    if (this != null) {
        val t = this
        t.toTokens(tokens)
    }
}

public fun UByte.toTokens(tokens: TokenStream) {
    Literal.u8Suffixed(this).toTokens(tokens)
}

public fun UShort.toTokens(tokens: TokenStream) {
    Literal.u16Suffixed(this).toTokens(tokens)
}

public fun UInt.toTokens(tokens: TokenStream) {
    Literal.u32Suffixed(this).toTokens(tokens)
}

public fun ULong.toTokens(tokens: TokenStream) {
    Literal.u64Suffixed(this).toTokens(tokens)
}

public fun U128.toTokens(tokens: TokenStream) {
    Literal.u128Suffixed(this).toTokens(tokens)
}

public fun Byte.toTokens(tokens: TokenStream) {
    Literal.i8Suffixed(this).toTokens(tokens)
}

public fun Short.toTokens(tokens: TokenStream) {
    Literal.i16Suffixed(this).toTokens(tokens)
}

public fun Int.toTokens(tokens: TokenStream) {
    Literal.i32Suffixed(this).toTokens(tokens)
}

public fun Long.toTokens(tokens: TokenStream) {
    Literal.i64Suffixed(this).toTokens(tokens)
}

public fun I128.toTokens(tokens: TokenStream) {
    Literal.i128Suffixed(this).toTokens(tokens)
}

public fun Float.toTokens(tokens: TokenStream) {
    Literal.f32Suffixed(this).toTokens(tokens)
}

public fun Double.toTokens(tokens: TokenStream) {
    Literal.f64Suffixed(this).toTokens(tokens)
}

public fun Boolean.toTokens(tokens: TokenStream) {
    val word =
        if (this) {
            "true"
        } else {
            "false"
        }
    Ident.new(word, Span.callSite()).toTokens(tokens)
}

public fun Char.toTokensChar(tokens: TokenStream) {
    Literal.character(this).toTokens(tokens)
}

public fun String.toTokens(tokens: TokenStream) {
    Literal.string(this).toTokens(tokens)
}

public fun CStr.toTokens(tokens: TokenStream) {
    Literal.cString(this).toTokens(tokens)
}

public fun CString.toTokens(tokens: TokenStream) {
    Literal.cString(this).toTokens(tokens)
}

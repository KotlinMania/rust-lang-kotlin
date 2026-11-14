package io.github.kotlinmania.rustlang.procmacro

import io.github.kotlinmania.rustlang.procmacro.bridge.client.Symbol

/**
 * Determines whether procMacro has been made accessible to the currently
 * running program.
 *
 * The procMacro crate is only intended for use inside the implementation of
 * procedural macros. All the functions in this crate panic if invoked from
 * outside of a procedural macro, such as from a build script or unit test or
 * ordinary Rust binary.
 *
 * With consideration for Rust libraries that are designed to support both
 * macro and non-macro use cases, `procMacro.isAvailable()` provides a
 * non-panicking way to detect whether the infrastructure required to use the
 * API of procMacro is presently available. Returns true if invoked from
 * inside of a procedural macro, false if invoked from any other binary.
 */
public fun isAvailable(): Boolean = io.github.kotlinmania.rustlang.procmacro.bridge.client.isAvailable()

public fun treeToBridgeTree(tree: TokenTree): io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree<io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream, io.github.kotlinmania.rustlang.procmacro.bridge.client.Span, Symbol> =
    when (tree) {
        is TokenTree.Group -> {
            val (tt) = tree
            io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree.Group(tt.value)
        }
        is TokenTree.Punct -> {
            val (tt1) = tree
            io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree.Punct(tt1.value)
        }
        is TokenTree.Ident -> {
            val (tt2) = tree
            io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree.Ident(tt2.value)
        }
        is TokenTree.Literal -> {
            val (tt3) = tree
            io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree.Literal(tt3.value)
        }
    }

public fun parseNumber(value: String): Pair<String, Base> {
    val iter = value.encodeToByteArray().map { it.toUByte() }.iter().copied()
    val firstDigit = iter.next() ?: return Pair("0", Base.Decimal)
    val secondDigit = iter.next() ?: return Pair(value, Base.Decimal)
    var base = Base.Decimal
    if (firstDigit == 48u) {
        // Attempt to parse encoding base.
        when (secondDigit) {
            98u -> {
                base = Base.Binary
            }
            111u -> {
                base = Base.Octal
            }
            120u -> {
                base = Base.Hexadecimal
            }
            else -> {}
        }
    }
    val offset =
        if (base == Base.Decimal) {
            0
        } else {
            2
        }
    return Pair(value.substring(offset), base)
}

public fun stripUnderscores(valueS: String): String {
    val value = valueS.encodeToByteArray().map { it.toUByte() }
    if (value.iter().copied().all({ c -> c != 95u && c != 102u })) {
        return valueS
    }
    var output = ""
    for (c in value.iter().copied()) {
        if (c != 95u) {
            output += c.toInt().toChar()
        }
    }
    return output
}

public fun Char.eq(rhs: Punct): Boolean = this == rhs.asChar()

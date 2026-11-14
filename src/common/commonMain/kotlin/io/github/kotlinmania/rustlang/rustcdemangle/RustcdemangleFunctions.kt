package io.github.kotlinmania.rustlang.rustcdemangle

import io.github.kotlinmania.rustlang.core.char.isAsciiAlphanumeric
import io.github.kotlinmania.rustlang.core.char.isAsciiPunctuation
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.rustStrChars
import io.github.kotlinmania.rustlang.rustcdemangle.v0.ParseError

/**
 * De-mangles a Rust symbol into a more readable version
 *
 * This function will take a **mangled** symbol and return a value. When printed,
 * the de-mangled version will be written. If the symbol does not look like
 * a mangled symbol, the original value will be written instead.
 *
 * # Examples
 *
 * ```kotlin
 * check(demangle("_ZN4testE").toString() == "test")
 * check(demangle("_ZN3foo3barE").toString() == "foo.bar")
 * check(demangle("foo").toString() == "foo")
 * ```
 */
public fun demangle(s: String): Demangle {
    var s1 = s
    // During ThinLTO LLVM may import and rename internal symbols, so strip out
    // those endings first as they're one of the last manglings applied to symbol
    // names.
    val llvm = ".llvm."
    when (val matchVal = s1.indexOf(llvm).takeIf { it >= 0 }) {
        null -> {}
        else -> {
            val i = matchVal
            val candidate = s1.substring(i + llvm.len())
            val allHex =
                candidate.rustStrChars().all({ c -> when (c) {
                    in 'A'..'F', in '0'..'9', '@' -> true
                    else -> false
                } })
            if (allHex) {
                s1 = s1.substring(0 until i)
            }
        }
    }
    var suffix = ""
    var style =
        run {
            val matchVal1 = io.github.kotlinmania.rustlang.rustcdemangle.legacy.demangle(s1)
            when (matchVal1) { is Result.Ok<*> -> {
                val okVal = matchVal1.value
                val d = okVal.first
                val s2 = okVal.second
                suffix = s2
                DemangleStyle.Legacy(d)
            }; is Result.Err<*> -> {
                run {
                    val matchVal2 = io.github.kotlinmania.rustlang.rustcdemangle.v0.demangle(s1)
                    when {
                        matchVal2 is Result.Ok<*> -> {
                            suffix = s1
                            DemangleStyle.v0(d)
                        }
                        // Note(eddyb) would it make sense to treat an unknown-validity
                        // symbol (e.g. one that errored with `RecursedTooDeep`) as
                        // v0-mangled, and have the error show up in the demangling?
                        // (that error already gets past this initial check, and therefore
                        // will show up in the demangling, if hidden behind a backref)
                        matchVal2 is Result.Err<*> && matchVal2.error == ParseError.Invalid -> null
                        else -> null
                    }
                }
            } }
        }
    // Output like LLVM IR adds extra period-delimited words. See if
    // we are in that case and save the trailing words if so.
    if (!suffix.isEmpty()) {
        if (suffix.startsWith('.') && isSymbolLike(suffix)) {
            // Keep the suffix.
        } else {
            // Reset the suffix and invalidate the demangling.
            suffix = ""
            style = null
        }
    }
    return Demangle(style = style, original = s1, suffix = suffix)
}

/**
 * The same as `demangle`, except return an `Err` if the string does not appear
 * to be a Rust symbol, rather than "demangling" the given string as a no-op.
 *
 * ```kotlin
 * val notARustSymbol = "la la la"
 * // The `tryDemangle` function will reject strings which are not Rust symbols.
 * check(tryDemangle(notARustSymbol).isErr())
 * // While `demangle` will just pass the non-symbol through as a no-op.
 * check(demangle(notARustSymbol).asStr() == notARustSymbol)
 * ```
 */
public fun tryDemangle(s: String): Result<Demangle, TryDemangleError> {
    val sym = demangle(s)
    return if (sym.style != null) {
        Result.Ok(sym)
    } else {
        Result.Err(TryDemangleError(priv = Unit))
    }
}

public fun isSymbolLike(s: String): Boolean =
    s.rustStrChars().all({ c ->
        // Once `char.isAsciiPunctuation` and `char.isAsciiAlphanumeric`
        // have been stable for long enough, use those instead for clarity
        isAsciiAlphanumeric(c) || isAsciiPunctuation(c)
    })

// Copied from the documentation of `char.isAsciiAlphanumeric`
public fun isAsciiAlphanumeric(c: Char): Boolean =
    when (c) {
        in 'A'..'Z', in 'a'..'z', in '0'..'9' -> true
        else -> false
    }

// Copied from the documentation of `char.isAsciiPunctuation`
public fun isAsciiPunctuation(c: Char): Boolean =
    when (c) {
        in '!'..'/', in ':'..'@', in '['..'`', in '{'..'~' -> true
        else -> false
    }

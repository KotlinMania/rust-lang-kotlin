package io.github.kotlinmania.rustlang.rustcdemangle

import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.rustcdemangle.v0.ParseError
import io.github.kotlinmania.rustlang.std.io.error.Error

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
                candidate.chars().all({ c -> when (c) {
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
            when (matchVal1) { is Result.Ok<*, *> -> {
                val okVal = matchVal1.value
                val d = okVal.first
                val s2 = okVal.second
                suffix = s2
                DemangleStyle.Legacy(d)
            }; is Result.Err<*, *> -> {
                run {
                    val matchVal2 = io.github.kotlinmania.rustlang.rustcdemangle.v0.demangle(s1)
                    when {
                        matchVal2 is Result.Ok<*, *> -> {
                            suffix = s1
                            DemangleStyle.v0(d)
                        }
                        // Note(eddyb) would it make sense to treat an unknown-validity
                        // symbol (e.g. one that errored with `RecursedTooDeep`) as
                        // v0-mangled, and have the error show up in the demangling?
                        // (that error already gets past this initial check, and therefore
                        // will show up in the demangling, if hidden behind a backref)
                        matchVal2 is Result.Err<*, *> && matchVal2.error == ParseError.Invalid -> null
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

public fun demangleLine(line: String, output: Write, includeHash: Boolean): Result<Unit, Error> {
    var head = 0
    while (head < line.length) {
        // Move to the next potential match
        val nextHead =
            run {
                val matchVal = Pair(line.substring(head).indexOf("_ZN").takeIf { it >= 0 }, line.substring(head).indexOf("_R").takeIf { it >= 0 })
                when {
                    matchVal.first != null && matchVal.second == null -> {
                        val idx = matchVal.first
                        head + idx
                    }
                    matchVal.first == null && matchVal.second != null -> {
                        val idx1 = matchVal.second
                        head + idx1
                    }
                    matchVal.first != null && matchVal.second != null -> {
                        val idx1 = matchVal.first
                        val idx2 = matchVal.second
                        head + idx1.min(idx2)
                    }
                    else -> {
                        // No more matches...
                        line.length
                    }
                }
            }
        when (val tryResult = output.writeAll(line.substring(head until nextHead).encodeToByteArray().map { it.toUByte() })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        head = nextHead
        // Find the non-matching character.
        //
        // If we do not find a character, then until the end of the line is the
        // thing to demangle.
        val matchEnd = ((line.substring(head).indexOf({ ch: Char -> !(ch == '$' || ch == '.' || ch == '_' || ch.isAsciiAlphanumeric()) }).takeIf { it >= 0 })?.let({ idx2 -> head + idx2 }) ?: line.length)
        val mangled = line.substring(head until matchEnd)
        head = head + mangled.length
        when (val matchVal1 = tryDemangle(mangled)) {
            is Ok -> {
                val demangled = matchVal1.value
                if (includeHash) {
                    output.append("$demangled")
                } else {
                    output.append("$demangled")
                }
            }
            else -> {
                when (val tryResult = output.writeAll(mangled.encodeToByteArray().map { it.toUByte() })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            }
        }
    }
    return Result.Ok(Unit)
}

/**
 * Process a stream of data from `input` into the provided `output`, demangling any symbols found
 * within.
 *
 * Note that the underlying implementation will perform many relatively small writes to the
 * output. If the output is expensive to write to (e.g., requires syscalls), consider using
 * `std.io.BufWriter`.
 */
public fun <R : BufRead, W : Write> demangleStream(input: R, output: W, includeHash: Boolean): Result<Unit, Error> {
    var buf = ""
    // We read in lines to reduce the memory usage at any time.
    //
    // demangleLine is also more efficient with relatively small buffers as it will copy around
    // trailing data during demangling. In the future we might directly stream to the output but at
    // least right now that seems to be less efficient.
    while (when (val tryResult = input.readLine(buf)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) } > 0) {
        when (val tryResult = demangleLine(buf, output, includeHash)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        buf = ""
    }
    return Result.Ok(Unit)
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
    s.chars().all({ c ->
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

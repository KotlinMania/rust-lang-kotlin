// port-lint: source legacy.rs
package io.github.kotlinmania.rustlang.rustcdemangle.legacy

import io.github.kotlinmania.rustlang.core.char.methods.toDigit
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.fromStrRadix
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.chars

/**
 * Representation of a demangled symbol name.
 */
public open class Demangle(
    public var inner: String,
    /**
     * The number of ::-separated elements in the original name.
     */
    public var elements: Int,
) {
    public open fun fmt(f: StringBuilder) {
        // Alright, let's do this.
        var inner = this.inner
        for (element in 0 until this.elements) {
            var rest = inner
            while (rest.chars().next()!!.isDigit(10)) {
                rest = rest.substring(1)
            }
            val i: Int = (inner.substring(0 until (inner.length - rest.length)).toIntOrNull()?.let { Result.Ok(it) } ?: Result.Err("failed to parse Int")).unwrap()
            inner = rest.substring(i)
            rest = rest.substring(0 until i)
            // Skip printing the hash if alternate formatting
            // was requested.
            if (f.alternate() && element + 1 == this.elements && isRustHash(rest)) {
                break
            }
            if (element != 0) {
                f.append("::")
            }
            if (rest.startsWith("_\$")) {
                rest = rest.substring(1)
            }
            while (true) {
                if (rest.startsWith('.')) {
                    when (val matchVal = rest.substring(1).chars().next()) {
                        is Some -> {
                            f.append("::")
                            rest = rest.substring(2)
                        }
                        else -> {
                            f.append(".")
                            rest = rest.substring(1)
                        }
                    }
                } else if (rest.startsWith('$')) {
                    val (escape, afterEscape) =
                        when (val matchVal1 = rest.substring(1).indexOf('$').takeIf { it >= 0 }) {
                            null -> {
                                break
                            }
                            else -> {
                                val end = matchVal1
                                Pair(rest.substring(1..end), rest.substring(end + 2))
                            }
                        }
                    // see src/librustcCodegenUtils/symbolNames/legacy.rs for these mappings
                    val unescaped =
                        when (escape) {
                            "SP" -> "@"
                            "BP" -> "*"
                            "RF" -> "&"
                            "LT" -> "<"
                            "GT" -> ">"
                            "LP" -> "("
                            "RP" -> ")"
                            "C" -> ","
                            else -> {
                                if (escape.startsWith('u')) {
                                    val digits = escape[1 until escape.size]
                                    val allLowerHex =
                                        digits.chars().all({ c -> when (c) {
                                            in '0'..'9', in 'a'..'f' -> true
                                            else -> false
                                        } })
                                    val c = UInt.fromStrRadix(digits, 16u).ok()?.let(Char::fromU32)
                                    if (Pair(allLowerHex, c) != null) {
                                        val c1 = Pair(allLowerHex, c).second
                                        // Note(eddyb) do we need to filter out control codepoints?
                                        if (!c1.isControl()) {
                                            c1.fmt(f)
                                            rest = afterEscape
                                            continue
                                        }
                                    }
                                }
                                break
                            }
                        }
                    f.append(unescaped)
                    rest = afterEscape
                } else when (val matchVal2 = rest.indexOf({ c2 -> c2 == '$' || c2 == '.' }).takeIf { it >= 0 }) {
                    null -> {
                        break
                    }
                    else -> {
                        val i1 = matchVal2
                        f.append(rest.substring(0 until i1))
                        rest = rest.substring(i1)
                    }
                }
            }
            f.append(rest)
        }
    }
}

// All Rust symbols are in theory lists of "::"-separated identifiers. Some
// assemblers, however, can't handle these characters in symbol names. To get
// around this, we use C++-style mangling. The mangling method is:
//
// 1. Prefix the symbol with "_ZN"
// 2. For each element of the path, emit the length plus the element
// 3. End the path with "E"
//
// For example, "_ZN4testE" => "test" and "_ZN3foo3barE" => "foo.bar".
//
// We're the ones printing our backtraces, so we can't rely on anything else to
// demangle our symbols. It's *much* nicer to look at demangled symbols, so
// this function is implemented to give us nice pretty output.
//
// Note that this demangler isn't quite as fancy as it could be. We have lots
// of other information in our symbols like hashes, version, type information,
// etc. Additionally, this doesn't handle glue symbols at all.

/**
 * De-mangles a Rust symbol into a more readable version
 *
 * All Rust symbols by default are mangled as they contain characters that
 * cannot be represented in all object files. The mangling mechanism is similar
 * to C++'s, but Rust has a few specifics to handle items like scopes in
 * symbols.
 *
 * This function will take a **mangled** symbol and return a value. When printed,
 * the de-mangled version will be written. If the symbol does not look like
 * a mangled symbol, the original value will be written instead.
 *
 * # Examples
 *
 * ```kotlin
 * import rustcdemangle.demangle
 *
 * check(demangle("_ZN4testE").toString() == "test")
 * check(demangle("_ZN3foo3barE").toString() == "foo.bar")
 * check(demangle("foo").toString() == "foo")
 * ```
 */
public fun demangle(s: String): Result<Pair<Demangle, String>, Unit> {
    // First validate the symbol. If it doesn't look like anything we're
    // expecting, we just print it literally. Note that we must handle non-Rust
    // symbols because we could have any function in the backtrace.
    val inner =
        if (s.startsWith("_ZN")) {
            s.substring(3)
        } else if (s.startsWith("ZN")) {
            // On Windows, dbghelp strips leading underscores, so we accept "ZN...E"
            // form too.
            s.substring(2)
        } else if (s.startsWith("__ZN")) {
            // On OSX, symbols are prefixed with an extra _
            s.substring(4)
        } else {
            return Result.Err(Unit)
        }
    // only work with ascii text
    if (inner.bytes().any({ c -> c and 0x80 != 0 })) {
        return Result.Err(Unit)
    }
    var elements = 0
    val chars = inner.chars()
    var c = when (val tryResult = chars.next().okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    while (c != 'E') {
        // Decode an identifier element's length.
        if (!c.isDigit(10)) {
            return Result.Err(Unit)
        }
        var len = 0
        while (true) {
            val matchVal = c.toDigit(10u)
            if (matchVal == null) break
            val d = matchVal
            len = when (val tryResult = len.checkedMul(10)?.let({ len1 -> len1.checkedAdd(d.toInt()) }).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            c = when (val tryResult = chars.next().okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        // `c` already contains the first character of this identifier, skip it and
        // all the other characters of this identifier, to reach the next element.
        for (unused in 0 until len) {
            c = when (val tryResult = chars.next().okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        elements += 1
    }
    return Result.Ok(Pair(Demangle(inner = inner, elements = elements), chars.asStr()))
}

// Rust hashes are hex digits with an `h` prepended.
public fun isRustHash(s: String): Boolean = s.startsWith('h') && s.substring(1).chars().all({ c -> c.isDigit(16) })

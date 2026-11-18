package io.github.kotlinmania.rustlang.getopts

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.getopts.Fail.*
import io.github.kotlinmania.rustlang.getopts.HasArg.*
import io.github.kotlinmania.rustlang.getopts.Name.*
import io.github.kotlinmania.rustlang.getopts.Occur.*
import io.github.kotlinmania.rustlang.getopts.Optval.*
import io.github.kotlinmania.unicodewidth.UnicodeWidthStr

public fun validateNames(shortName: String, longName: String) {
    val len = shortName.length
    check(len == 1 || len == 0) { "the short_name (first argument) should be a single character, or an empty string for none" }
    val len1 = longName.length
    check(len1 == 0 || len1 > 1) { "the long_name (second argument) should be longer than a single character, or an empty string for none" }
}

public fun isArg(arg: String): Boolean = arg.encodeToByteArray().map { it.toUByte() }.get(0) == 45u.toUByte() && arg.length > 1

public fun findOpt(opts: List<Opt>, nm: Name): Int? {
    // Search main options.
    val pos = opts.indexOfFirst({ opt -> opt.name == nm }).takeIf { it >= 0 }
    if (pos != null) {
        return pos
    }
    // Search in aliases.
    for (candidate in opts.iter()) {
        if (candidate.aliases.iter().any({ opt -> opt.name == nm })) {
            return opts.indexOfFirst({ opt -> opt.name == candidate.name }).takeIf { it >= 0 }
        }
    }
    return null
}

public fun formatOption(opt: OptGroup): String {
    var line = ""
    if (opt.occur != Req) {
        line += '['
    }
    // Use shortName if possible, but fall back to longName.
    if (!opt.shortName.isEmpty()) {
        line += '-'
        line += opt.shortName
    } else {
        line += "--"
        line += opt.longName
    }
    if (opt.hasarg != No) {
        line += ' '
        if (opt.hasarg == Maybe) {
            line += '['
        }
        line += opt.hint
        if (opt.hasarg == Maybe) {
            line += ']'
        }
    }
    if (opt.occur != Req) {
        line += ']'
    }
    if (opt.occur == Multi) {
        line += ".."
    }
    return line
}

/**
 * Splits a string into substrings with possibly internal whitespace,
 * each of them at most `lim` bytes long, if possible. The substrings
 * have leading and trailing whitespace removed, and are only cut at
 * whitespace boundaries.
 */
public fun eachSplitWithin(desc: String, lim: Int): List<String> {
    val rows = mutableListOf<String>()
    for (line in desc.trim().lines()) {
        val lineChars = line.chars().chain(' ')
        val words =
            lineChars.fold(Triple(mutableListOf(), 0, 0), { (words, a, z), c ->
                val idx = z + c.lenUtf8()
                // Get the current byte offset
                // If the char is whitespace, advance the word start and maybe push a word
                if (c.isWhitespace()) {
                    if (a != z) {
                        words.push(line[a until z])
                    }
                    Triple(words, idx, idx)
                } else {
                    Triple(words, a, idx)
                }
            }).first
        var row = ""
        for (word in words.iter()) {
            val sep =
                if (!row.isEmpty()) {
                    " "
                } else {
                    null
                }
            val width = row.width() + word.width() + sep.map(UnicodeWidthStr::width).unwrapOr(0)
            if (width <= lim) {
                if (sep != null) {
                    val sep1 = sep
                    row += sep1
                }
                row += word
                continue
            }
            if (!row.isEmpty()) {
                rows.add(row.clone())
                row = ""
            }
            row += word
        }
        if (!row.isEmpty()) {
            rows.add(row)
        }
    }
    return rows
}

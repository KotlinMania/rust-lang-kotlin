package io.github.kotlinmania.rustlang.test.term.terminfo

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.result.and
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.test.term.Color
import io.github.kotlinmania.rustlang.test.term.terminfo.Error.*
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.Param
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.Variables
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.expand
import io.github.kotlinmania.rustlang.test.term.terminfo.parser.compiled.msysTerminfo
import io.github.kotlinmania.rustlang.test.term.terminfo.searcher.getDbpathForTerm
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * A parsed terminfo database entry.
 */
public open class TermInfo(
    /**
     * Names for the terminal
     */
    public var names: List<String>,
    /**
     * Map of capability name to boolean value
     */
    public var bools: MutableMap<String, Boolean>,
    /**
     * Map of capability name to numeric value
     */
    public var numbers: MutableMap<String, UInt>,
    /**
     * Map of capability name to raw (unexpanded) string
     */
    public var strings: MutableMap<String, List<UByte>>,
) {
    public companion object {
        /**
         * Creates a TermInfo based on current environment.
         */
        public fun fromEnv(): Result<TermInfo, Error> {
            val term =
                run {
                    val matchVal = Env.variable("TERM")
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val name = matchVal.okValue()
                        TermInfo.fromName(name)
                    }; is Result.Err<*, *> -> {
                        return Result.Err<TermInfo, Error>(Error.TermUnset)
                    } }
                }
            return if (term.isErr() && Env.variable("MSYSCON").isOkAnd({ s -> "mintty.exe" == s })) {
                // msys terminal
                Result.Ok<TermInfo, Error>(msysTerminfo())
            } else {
                term
            }
        }

        /**
         * Creates a TermInfo for the named terminal.
         */
        public fun fromName(name: String): Result<TermInfo, Error> {
            if (false) {
                // Avoid all the work of parsing the terminfo (it's pretty slow under Miri), and just
                // assume that the standard color codes work (like e.g. the `colored` crate).
                return Result.Ok<TermInfo, Error>(TermInfo(names = emptyList(), bools = Default.default(), numbers = Default.default(), strings = Default.default()))
            }
            return (getDbpathForTerm(name)?.let { Result.Ok<Option, Error>(it) } ?: Result.Err<Option, Error>(Error.IoError(io.github.kotlinmania.rustlang.core.io.error.Error.fromStaticMessage(io.github.kotlinmania.rustlang.core.io.error.SimpleMessage(kind = ErrorKind.NotFound, message = "terminfo file not found"))))).andThen({ p -> TermInfo.fromPath(p) })
        }

        /**
         * Parse the given TermInfo.
         */
        public fun fromPath(path: Path): Result<TermInfo, Error> = TermInfo.fromPath(path)
    }
}

/**
 * A Terminal that knows how many colors it supports, with a reference to its
 * parsed Terminfo database record.
 */
public open class TerminfoTerminal<T>(
    public var numColors: UInt,
    public var out: T,
    public var ti: TermInfo,
) {
    public open fun dimIfNecessary(color: Color): Color =
        if (color >= this.numColors && (8 until 16).contains(color)) {
            color - 8u
        } else {
            color
        }

    public open fun applyCap(cmd: String, params: List<Param>): Result<Boolean> =
        when (val matchVal = this.ti.strings.get(cmd)) {
            null -> RustResult.Ok(false)
            else -> {
                val cmd1 = matchVal
                run {
                    val matchVal1 = expand(cmd1, params, Variables.new())
                    when (matchVal1) { is RustResult.Ok<*, *> -> {
                        val s = matchVal1.okValue()
                        this.out.writeAll(s).and(RustResult.Ok(true))
                    }; is RustResult.Err<*, *> -> {
                        val e = matchVal1.errValue()
                        RustResult.Err(io.github.kotlinmania.rustlang.core.io.error.Error.new(ErrorKind.InvalidData, e))
                    } }
                }
            }
        }

    public companion object {
        /**
         * Creates a new TerminfoTerminal with the given TermInfo and Write.
         */
        public fun <T> newWithTerminfo(out: T, terminfo: TermInfo): TerminfoTerminal<T> {
            val nc =
                if (terminfo.strings.containsKey("setaf") && terminfo.strings.containsKey("setab")) {
                    (terminfo.numbers.get("colors")?.let({ n -> n }) ?: 0)
                } else {
                    0
                }
            return TerminfoTerminal(out = out, ti = terminfo, numColors = nc)
        }

        /**
         * Creates a new TerminfoTerminal for the current environment with the given Write.
         *
         * Returns `None` when the terminfo cannot be found or parsed.
         */
        public fun <T> new(out: T): TerminfoTerminal<T>? = TermInfo.fromEnv().map({ ti -> TerminfoTerminal.newWithTerminfo(out, ti) }).ok()
    }
}

public fun <T : Write<T>> TerminfoTerminal<T>.fg(color: Color): Result<Boolean> {
    val color1 = this.dimIfNecessary(color)
    if (false && color1 < 8u) {
        // The Miri logic for this only works for the most basic 8 colors, which we just assume
        // the terminal will support. (`numColors` is always 0 in Miri, so higher colors will
        // just fail. But libtest doesn't use any higher colors anyway.)
        return this.out.append("\u001b[3${color}m").and(RustResult.Ok(true))
    }
    if (this.numColors > color1) {
        return this.applyCap("setaf", listOf(Param.Number(color1.toInt())))
    }
    return RustResult.Ok(false)
}

public fun <T : Write<T>> TerminfoTerminal<T>.reset(): Result<Boolean> {
    if (false) {
        return this.out.append("\u001b[0m").and(RustResult.Ok(true))
    }
    // are there any terminals that have color/attrs and not sgr0?
    // Try falling back to sgr, then op
    val cmd1 =
        when (val matchVal = listOf("sgr0", "sgr", "op").iter().findMap({ cap -> this.ti.strings.get(cap) })) {
            null -> { return RustResult.Ok(false) }
            else -> {
                val op = matchVal
                run {
                    val matchVal1 = expand(op, listOf(), Variables.new())
                    when (matchVal1) { is RustResult.Ok<*, *> -> {
                        val cmd = matchVal1.okValue()
                        cmd
                    }; is RustResult.Err<*, *> -> {
                        val e = matchVal1.errValue()
                        return RustResult.Err(io.github.kotlinmania.rustlang.core.io.error.Error.new(ErrorKind.InvalidData, e))
                    } }
                }
            }
        }
    return this.out.writeAll(cmd1).and(RustResult.Ok(true))
}

public fun <T : Write<T>> TerminfoTerminal<T>.write(buf: List<UByte>): Result<Int> = this.out.write(buf)

public fun <T : Write<T>> TerminfoTerminal<T>.flush(): Result<Unit> = this.out.flush()

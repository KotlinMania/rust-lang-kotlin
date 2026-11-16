package io.github.kotlinmania.rustlang.test.term.terminfo

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.and
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.test.term.Terminal
import io.github.kotlinmania.rustlang.test.term.color.Color
import io.github.kotlinmania.rustlang.test.term.terminfo.Error.*
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.Param
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.Variables
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.expand
import io.github.kotlinmania.rustlang.test.term.terminfo.parser.compiled.msysTerminfo
import io.github.kotlinmania.rustlang.test.term.terminfo.searcher.getDbpathForTerm

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
                    when (matchVal) { is Result.Ok -> {
                        val name = matchVal.value
                        TermInfo.fromName(name)
                    }; is Result.Err -> {
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
                // assume that the standard color codes work (like e.g. the 'colored' crate).
                return Result.Ok<TermInfo, Error>(TermInfo(names = Default.default(), bools = Default.default(), numbers = Default.default(), strings = Default.default()))
            }
            return (getDbpathForTerm(name)?.let { Result.Ok(it) } ?: Result.Err(Error.IoError(io.constError(ErrorKind.NotFound, "terminfo file not found")))).andThen({ p -> TermInfo.fromPath(p) })
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
) : Terminal, Write {
    public override fun fg(color: Color): Result<Boolean> {
        val color1 = this.dimIfNecessary(color)
        if (false && color1 < 8) {
            // The Miri logic for this only works for the most basic 8 colors, which we just assume
            // the terminal will support. (`numColors` is always 0 in Miri, so higher colors will
            // just fail. But libtest doesn't use any higher colors anyway.)
            return this.out.append("\u001b[3${color}m").and(Result.Ok(true))
        }
        if (this.numColors > color1) {
            return this.applyCap("setaf", listOf(Param.Number(color1.toInt())))
        }
        return Result.Ok(false)
    }

    public override fun reset(): Result<Boolean> {
        if (false) {
            return this.out.append("\u001b[0m").and(Result.Ok(true))
        }
        // are there any terminals that have color/attrs and not sgr0?
        // Try falling back to sgr, then op
        val cmd1 =
            when (val matchVal = listOf("sgr0", "sgr", "op").iter().findMap({ cap -> this.ti.strings.get(cap) })) {
                null -> { return Result.Ok(false) }
                else -> {
                    val op = matchVal
                    run {
                        val matchVal1 = expand(op, listOf(), Variables.new())
                        when (matchVal1) { is Result.Ok -> {
                            val cmd = matchVal1.value
                            cmd
                        }; is Result.Err -> {
                            val e = matchVal1.error
                            return Result.Err(io.github.kotlinmania.rustlang.core.io.error.Error.new(ErrorKind.InvalidData, e))
                        } }
                    }
                }
            }
        return this.out.writeAll(cmd1).and(Result.Ok(true))
    }

    public open fun dimIfNecessary(color: Color): Color =
        if (color >= this.numColors && (8 until 16).contains(color)) {
            color - 8u
        } else {
            color
        }

    public open fun applyCap(cmd: String, params: List<Param>): Result<Boolean> =
        when (val matchVal = this.ti.strings.get(cmd)) {
            null -> Result.Ok(false)
            else -> {
                val cmd1 = matchVal
                run {
                    val matchVal1 = expand(cmd1, params, Variables.new())
                    when (matchVal1) { is Result.Ok -> {
                        val s = matchVal1.value
                        this.out.writeAll(s).and(Result.Ok(true))
                    }; is Result.Err -> {
                        val e = matchVal1.error
                        Result.Err(io.github.kotlinmania.rustlang.core.io.error.Error.new(ErrorKind.InvalidData, e))
                    } }
                }
            }
        }

    public open fun write(buf: List<UByte>): Result<Int> = this.out.write(buf)

    public open fun flush(): Result<Unit> = this.out.flush()

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

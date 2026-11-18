package io.github.kotlinmania.rustlang.getopts

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.iter.sources.repeat.repeat
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.str.traits.FromStr
import io.github.kotlinmania.rustlang.getopts.Fail.*
import io.github.kotlinmania.rustlang.getopts.HasArg.*
import io.github.kotlinmania.rustlang.getopts.Name.*
import io.github.kotlinmania.rustlang.getopts.Occur.*
import io.github.kotlinmania.rustlang.getopts.Optval.*
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * A description of the options that a program can handle.
 */
public open class Options(
    public var grps: MutableList<OptGroup>,
    public var parsingStyle: ParsingStyle,
    public var longOnly: Boolean,
) {
    public open fun clone(): Options = Options(grps, parsingStyle, longOnly)

    /**
     * Set the parsing style.
     */
    public open fun parsingStyle(style: ParsingStyle): Options {
        this.parsingStyle = style
        return this
    }

    /**
     * Set or clear "long options only" mode.
     *
     * In "long options only" mode, short options cannot be clustered
     * together, and long options can be given with either a single
     * "-" or the customary "--".  This mode also changes the meaning
     * of "-a=b"; in the ordinary mode this will parse a short option
     * "-a" with argument "=b"; whereas in long-options-only mode the
     * argument will be simply "b".
     */
    public open fun longOnly(longOnly: Boolean): Options {
        this.longOnly = longOnly
        return this
    }

    /**
     * Create a generic option group, stating all parameters explicitly.
     */
    public open fun opt(shortName: String, longName: String, desc: String, hint: String, hasarg: HasArg, occur: Occur): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = hint.toString(), desc = desc.toString(), hasarg = hasarg, occur = occur))
        return this
    }

    /**
     * Create a long option that is optional and does not take an argument.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optflag("h", "help", "help flag")
     * val matches = opts.parse(listOf("-h")).unwrap()
     * check(matches.optPresent("h"))
     * ```
     */
    public open fun optflag(shortName: String, longName: String, desc: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = "".toString(), desc = desc.toString(), hasarg = No, occur = Optional))
        return this
    }

    /**
     * Create a long option that can occur more than once and does not
     * take an argument.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optflagmulti("v", "verbose", "verbosity flag")
     * val matches = opts.parse(listOf("-v", "--verbose")).unwrap()
     * check(2 == matches.optCount("v"))
     * ```
     */
    public open fun optflagmulti(shortName: String, longName: String, desc: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = "".toString(), desc = desc.toString(), hasarg = No, occur = Multi))
        return this
    }

    /**
     * Create a long option that is optional and takes an optional argument.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     * * `hint` - Hint that is used in place of the argument in the usage help,
     *   e.g. `"FILE"` for a `- o FILE` option
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optflagopt("t", "text", "flag with optional argument", "TEXT")
     * val matches = opts.parse(listOf("--text")).unwrap()
     * check(null == matches.optStr("text"))
     * val matches1 = opts.parse(listOf("--text=foo")).unwrap()
     * check("foo".toOwned() == matches1.optStr("text"))
     * ```
     */
    public open fun optflagopt(shortName: String, longName: String, desc: String, hint: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = hint.toString(), desc = desc.toString(), hasarg = Maybe, occur = Optional))
        return this
    }

    /**
     * Create a long option that is optional, takes an argument, and may occur
     * multiple times.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     * * `hint` - Hint that is used in place of the argument in the usage help,
     *   e.g. `"FILE"` for a `- o FILE` option
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optmulti("t", "text", "text option", "TEXT")
     * val matches = opts.parse(listOf("-t", "foo", "--text=bar")).unwrap()
     * val values = matches.optStrs("t")
     * check(2 == values.len())
     * check("foo" == values[0])
     * check("bar" == values[1])
     * ```
     */
    public open fun optmulti(shortName: String, longName: String, desc: String, hint: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = hint.toString(), desc = desc.toString(), hasarg = Yes, occur = Multi))
        return this
    }

    /**
     * Create a long option that is optional and takes an argument.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     * * `hint` - Hint that is used in place of the argument in the usage help,
     *   e.g. `"FILE"` for a `- o FILE` option
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optopt("o", "optional", "optional text option", "TEXT")
     * val matches = opts.parse(listOf("arg1")).unwrap()
     * check(null == matches.optStr("optional"))
     * val matches1 = opts.parse(listOf("--optional", "foo", "arg1")).unwrap()
     * check("foo".toOwned() == matches1.optStr("optional"))
     * ```
     */
    public open fun optopt(shortName: String, longName: String, desc: String, hint: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = hint.toString(), desc = desc.toString(), hasarg = Yes, occur = Optional))
        return this
    }

    /**
     * Create a long option that is required and takes an argument.
     *
     * * `shortName` - e.g. `"h"` for a `- h` option, or `""` for none
     * * `longName` - e.g. `"help"` for a `- - help` option, or `""` for none
     * * `desc` - Description for usage help
     * * `hint` - Hint that is used in place of the argument in the usage help,
     *   e.g. `"FILE"` for a `- o FILE` option
     *
     * # Example
     *
     * ```kotlin
     * import std.result
     *
     * var opts = Options.new()
     * opts.optopt("o", "optional", "optional text option", "TEXT")
     * opts.reqopt("m", "mandatory", "madatory text option", "TEXT")
     * val result = opts.parse(listOf("--mandatory", "foo"))
     * check(result.isOk())
     * val result1 = opts.parse(listOf("--optional", "foo"))
     * check(result1.isErr())
     * check(Fail.OptionMissing("mandatory".toOwned()) == result1.unwrapErr())
     * ```
     */
    public open fun reqopt(shortName: String, longName: String, desc: String, hint: String): Options {
        validateNames(shortName, longName)
        this.grps.add(OptGroup(shortName = shortName.toString(), longName = longName.toString(), hint = hint.toString(), desc = desc.toString(), hasarg = Yes, occur = Req))
        return this
    }

    /**
     * Parse command line arguments according to the provided options.
     *
     * On success returns `Ok(Matches)`. Use methods such as `optPresent`
     * `optStr`, etc. to interrogate results.
     * # Panics
     *
     * Returns `Err(Fail)` on failure: use the `Debug` implementation of `Fail`
     * to display information about it.
     */
    public open fun <C> parse(args: C): Result {
        val opts: List<Opt> = this.grps.map({ x -> x.longToShort() }).toMutableList()
        var vals = (0 until opts.size).map({ mutableListOf() }).toMutableList()
        var free: List<String> = mutableListOf()
        var argsEnd = null
        val args1 =
            when (val tryResult = args.iterator().map({ i ->
                i.asRef().toStr().okOrElse({ Fail.UnrecognizedOption("${i.asRef()}") }).map({ s -> s.toOwned() })
            }).collect<kotlin.Result<Vec>>()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Matches, Fail>(tryResult.errValue()) }
        val args2 = args1.intoIter().peekable()
        var argPos = 0
        while (true) {
            val matchVal = args2.next()
            if (matchVal == null) break
            val cur = matchVal
            if (!isArg(cur)) {
                free.add(cur)
                when (this.parsingStyle) {
                    ParsingStyle.FloatingFrees -> {}
                    ParsingStyle.StopAtFirstFree -> {
                        free.extend(args2)
                        break
                    }
                }
            } else if (cur == "--") {
                argsEnd = free.size
                free.extend(args2)
                break
            } else {
                var name = null
                var iArg = null
                var wasLong = true
                if (cur.encodeToByteArray().map { it.toUByte() }[1] == 45u.toUByte() || this.longOnly) {
                    val tail =
                        if (cur.encodeToByteArray().map { it.toUByte() }[1] == 45u.toUByte()) {
                            cur[2 until cur.size]
                        } else {
                            check(this.longOnly)
                            cur[1 until cur.size]
                        }
                    val parts = tail.splitn(2, '=')
                    name = Name.fromStr(parts.next()!!)
                    when (val matchVal1 = parts.next()) {
                        null -> {}
                        else -> {
                            val rest = matchVal1
                            iArg = rest.toString()
                        }
                    }
                } else {
                    wasLong = false
                    for ((j, ch) in cur.charIndices().skip(1)) {
                        val opt = Short(ch)
                        val optId =
                            when (val matchVal2 = findOpt(opts, opt)) {
                                null -> { return RustResult.Err(UnrecognizedOption(opt.toString())) }
                                else -> {
                                    val id = matchVal2
                                    id
                                }
                            }
                        // In a series of potential options (eg. -aheJ), if we
                        // see one which takes an argument, we assume all
                        // subsequent characters make up the argument. This
                        // allows options such as -L/usr/local/lib/foo to be
                        // interpreted correctly
                        val argFollows =
                            when (opts[optId].hasarg) {
                                Yes, Maybe -> true
                                No -> false
                            }
                        if (argFollows) {
                            name = opt
                            val next = j + ch.lenUtf8()
                            if (next < cur.len()) {
                                iArg = cur[next until cur.size].toString()
                                break
                            }
                        } else {
                            vals[optId].push(Pair(argPos, Given))
                        }
                    }
                }
                if (name != null) {
                    val nm = name
                    val optId1 =
                        when (val matchVal3 = findOpt(opts, nm)) {
                            null -> { return RustResult.Err(UnrecognizedOption(nm.toString())) }
                            else -> {
                                val id1 = matchVal3
                                id1
                            }
                        }
                    when (opts[optId1].hasarg) {
                        No -> {
                            if (iArg != null) {
                                return RustResult.Err(UnexpectedArgument(nm.toString()))
                            }
                            vals[optId1].push(Pair(argPos, Given))
                        }
                        Maybe -> {
                            // Note that here we do not handle `- - arg value`.
                            // This matches GNU getopt behavior; but also
                            // makes sense, because if this were accepted,
                            // then users could only write a "Maybe" long
                            // option at the end of the arguments when
                            // FloatingFrees is in use.
                            when (val matchVal4 = iArg.also { iArg = null }) {
                                null -> {
                                    if (wasLong || args2.peek().mapOr(true, { n -> isArg(n) })) {
                                        vals[optId1].push(Pair(argPos, Given))
                                    } else {
                                        vals[optId1].push(Pair(argPos, Val(args2.next()!!)))
                                    }
                                }
                                else -> {
                                    val iArg1 = matchVal4
                                    vals[optId1].push(Pair(argPos, Val(iArg1)))
                                }
                            }
                        }
                        Yes -> {
                            when (val matchVal6 = iArg.also { iArg = null }) {
                                null -> {
                                    when (val matchVal5 = args2.next()) {
                                        null -> {
                                            return RustResult.Err(ArgumentMissing(nm.toString()))
                                        }
                                        else -> {
                                            val n = matchVal5
                                            vals[optId1].push(Pair(argPos, Val(n)))
                                        }
                                    }
                                }
                                else -> {
                                    val iArg2 = matchVal6
                                    vals[optId1].push(Pair(argPos, Val(iArg2)))
                                }
                            }
                        }
                    }
                }
            }
            argPos += 1
        }
        check(vals.size == opts.size)
        for ((vals1, opt1) in (ArrayDeque(vals).zip(opts))) {
            if (opt1.occur == Req && vals1.isEmpty()) {
                return RustResult.Err(OptionMissing(opt1.name.toString()))
            }
            if (opt1.occur != Multi && vals1.len() > 1) {
                return RustResult.Err(OptionDuplicated(opt1.name.toString()))
            }
        }
        // Note that if "--" is last argument on command line, then index stored
        // in option does not exist in `free` and must be replaced with `None`
        argsEnd = argsEnd.filter({ pos -> pos != free.size })
        return RustResult.Ok(Matches(opts = opts, vals = vals, free = free, argsEnd = argsEnd))
    }

    /**
     * Derive a short one-line usage summary from a set of long options.
     */
    public open fun shortUsage(programName: String): String {
        val line = "Usage: $programName "
        line.pushStr(this.grps.map(::formatOption).toMutableList().join(" "))
        return line
    }

    /**
     * Derive a formatted message from a set of options.
     */
    public open fun usage(brief: String): String =
        this.usageWithFormat({ opts ->
            (brief).toString() + "\n\nOptions:\n" + (opts.toMutableList().join("\n")).toString() + "\n"
        })

    /**
     * Derive a custom formatted message from a set of options. The formatted options provided to
     * a closure as an iterator.
     */
    public open fun usageWithFormat(formatter: (Any) -> String): String = formatter(this.usageItems())

    /**
     * Derive usage items from a set of options.
     */
    public open fun usageItems(): Any {
        val descSep = "\n" + ((" ").toString().repeat(24)).toString()
        val anyShort = this.grps.any({ optref -> !optref.shortName.isEmpty() })
        val rows =
            this.grps.map({ optref ->
                val structVal = optref.clone()
                val shortName = structVal.shortName
                val longName = structVal.longName
                val hint = structVal.hint
                val desc = structVal.desc
                val hasarg = structVal.hasarg
                var row = "    ".toString()
                // short option
                when (shortName.width()) {
                    0 -> {
                        if (anyShort) {
                            row.pushStr("    ")
                        }
                    }
                    1 -> {
                        row.push('-')
                        row.pushStr(shortName)
                        if (longName.width() > 0) {
                            row.pushStr(", ")
                        } else {
                            // Only a single space here, so that any
                            // argument is printed in the correct spot.
                            row.push(' ')
                        }
                    }
                    // Note: refer issue #7.
                    else -> error("the short name should only be 1 ascii char long")
                }
                // long option
                when (longName.width()) {
                    0 -> {}
                    else -> {
                        row.pushStr(if (this.longOnly) {
                            "-"
                        } else {
                            "--"
                        })
                        row.pushStr(longName)
                        row.push(' ')
                    }
                }
                // arg
                when (hasarg) {
                    No -> {}
                    Yes -> row.pushStr(hint)
                    Maybe -> {
                        row.push('[')
                        row.pushStr(hint)
                        row.push(']')
                    }
                }
                val rowlen = row.width()
                if (rowlen < 24) {
                    for (unused in 0 until 24 - rowlen) {
                        row.push(' ')
                    }
                } else {
                    row.pushStr(descSep)
                }
                val descRows = eachSplitWithin(desc, 54)
                row.pushStr(descRows.join(descSep))
                row
            })
        return rows
    }

    public companion object {
        public fun default(): Options = Options.new()

        /**
         * Create a blank set of options.
         */
        public fun new(): Options = Options(grps = mutableListOf(), parsingStyle = ParsingStyle.FloatingFrees, longOnly = false)
    }
}

/**
 * A description of a possible option.
 */
public open class Opt(
    /**
     * Name of the option
     */
    public var name: Name,
    /**
     * Whether it has an argument
     */
    public var hasarg: HasArg,
    /**
     * How often it can occur
     */
    public var occur: Occur,
    /**
     * Which options it aliases
     */
    public var aliases: List<Opt>,
) {
    public open fun clone(): Opt = Opt(name, hasarg, occur, aliases)
}

/**
 * One group of options, e.g., both `- h` and `- - help`, along with
 * their shared description and properties.
 */
public open class OptGroup(
    /**
     * Short name of the option, e.g. `h` for a `- h` option
     */
    public var shortName: String,
    /**
     * Long name of the option, e.g. `help` for a `- - help` option
     */
    public var longName: String,
    /**
     * Hint for argument, e.g. `FILE` for a `- o FILE` option
     */
    public var hint: String,
    /**
     * Description for usage help text
     */
    public var desc: String,
    /**
     * Whether option has an argument
     */
    public var hasarg: HasArg,
    /**
     * How often it can occur
     */
    public var occur: Occur,
) {
    public open fun clone(): OptGroup = OptGroup(shortName, longName, hint, desc, hasarg, occur)

    /**
     * Translate OptGroup into Opt.
     * (Both short and long names correspond to different Opts).
     */
    public open fun longToShort(): Opt {
        val structVal = this.clone()
        val shortName = structVal.shortName
        val longName = structVal.longName
        val hasarg = structVal.hasarg
        val occur = structVal.occur
        return run {
            val matchVal = Pair(shortName.length, longName.length)
            when {
                matchVal.first == 0 && matchVal.second == 0 -> error("this long-format option was given no name")
                matchVal.first == 0 -> Opt(name = Long(longName), hasarg = hasarg, occur = occur, aliases = mutableListOf())
                matchVal.first == 1 && matchVal.second == 0 -> Opt(name = Short((shortName.encodeToByteArray().map { it.toUByte() }[0]).toInt().toChar()), hasarg = hasarg, occur = occur, aliases = mutableListOf())
                matchVal.first == 1 -> Opt(name = Long(longName), hasarg = hasarg, occur = occur, aliases = mutableListOf(Opt(name = Short((shortName.encodeToByteArray().map { it.toUByte() }[0]).toInt().toChar()), hasarg = hasarg, occur = occur, aliases = mutableListOf())))
                else -> error("something is wrong with the long-form opt")
            }
        }
    }
}

/**
 * The result of checking command line arguments. Contains a vector
 * of matches and a vector of free strings.
 */
public open class Matches(
    /**
     * Options that matched
     */
    public var opts: List<Opt>,
    /**
     * Values of the Options that matched and their positions
     */
    public var vals: List<List<Pair<Int, Optval>>>,
    /**
     * Free string fragments
     */
    public var free: List<String>,
    /**
     * Index of first free fragment after "--" separator
     */
    public var argsEnd: Int?,
) {
    public open fun clone(): Matches = Matches(opts, vals, free, argsEnd)

    public open fun optVals(nm: String): List<Pair<Int, Optval>> =
        when (val matchVal = findOpt(this.opts, Name.fromStr(nm))) {
            null -> error("No option '$nm' defined")
            else -> {
                val id = matchVal
                this.vals[id].clone()
            }
        }

    public open fun optVal(nm: String): Optval? = this.optVals(nm).map({ (_, o) -> o }).next()

    /**
     * Returns true if an option was defined
     */
    public open fun optDefined(name: String): Boolean = findOpt(this.opts, Name.fromStr(name)) != null

    /**
     * Returns true if an option was matched.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optPresent(name: String): Boolean = !this.optVals(name).isEmpty()

    /**
     * Returns the number of times an option was matched.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optCount(name: String): Int = this.optVals(name).size

    /**
     * Returns a vector of all the positions in which an option was matched.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optPositions(name: String): List<Int> = this.optVals(name).map({ (pos, _) -> pos }).toMutableList()

    /**
     * Returns true if any of several options were matched.
     */
    public open fun optsPresent(names: List<String>): Boolean =
        names.any({ nm -> run {
            val matchVal = findOpt(this.opts, Name.fromStr(nm))
            when {
                matchVal != null && !this.vals[matchVal].isEmpty() -> {
                    val id = matchVal
                    true
                }
                else -> false
            }
        } })

    /**
     * Returns true if any of several options were matched.
     *
     * Similar to `optsPresent` but accepts any argument that can be converted
     * into an iterator over string references.
     *
     * # Panics
     *
     * This function might panic if some option name is not defined.
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optopt("a", "alpha", "first option", "STR")
     * opts.optopt("b", "beta", "second option", "STR")
     * val args = mutableListOf("-a", "foo")
     * val matches = run {
     *     val matchVal = opts.parse(args)
     *     if (matchVal.isSuccess) {
     *         val m = matchVal.getOrThrow()
     *         m
     *     } else {
     *         error("panic")
     *     }
     * }
     * check(matches.optsPresentAny(listOf("alpha")))
     * check(!matches.optsPresentAny(listOf("beta")))
     * ```
     */
    public open fun <C> optsPresentAny(names: C): Boolean = names.iterator().any({ nm -> !this.optVals(nm.asRef()).isEmpty() })

    /**
     * Returns the string argument supplied to one of several matching options or `None`.
     */
    public open fun optsStr(names: List<String>): String? =
        names.mapNotNull({ nm -> when (val matchVal = this.optVal(nm)) {
            is Val -> {
                val (s) = matchVal as Val
                s
            }
            else -> null
        } }).next()

    /**
     * Returns the string argument supplied to the first matching option of
     * several options or `None`.
     *
     * Similar to `optsStr` but accepts any argument that can be converted
     * into an iterator over string references.
     *
     * # Panics
     *
     * This function might panic if some option name is not defined.
     *
     * # Example
     *
     * ```kotlin
     * var opts = Options.new()
     * opts.optopt("a", "alpha", "first option", "STR")
     * opts.optopt("b", "beta", "second option", "STR")
     * val args = mutableListOf("-a", "foo", "--beta", "bar")
     * val matches = run {
     *     val matchVal = opts.parse(args)
     *     if (matchVal.isSuccess) {
     *         val m = matchVal.getOrThrow()
     *         m
     *     } else {
     *         error("panic")
     *     }
     * }
     * check("foo".toString() == matches.optsStrFirst(listOf("alpha", "beta")))
     * check("bar".toString() == matches.optsStrFirst(listOf("beta", "alpha")))
     * ```
     */
    public open fun <C> optsStrFirst(names: C): String? =
        names.iterator().mapNotNull({ nm -> when (val matchVal = this.optVal(nm.asRef())) {
            is Val -> {
                val (s) = matchVal as Val
                s
            }
            else -> null
        } }).next()

    /**
     * Returns a vector of the arguments provided to all matches of the given
     * option.
     *
     * Used when an option accepts multiple values.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optStrs(name: String): List<String> =
        this.optVals(name).mapNotNull({ (_, v) -> when (v) {
            is Matches.Val -> {
                val (s) = v
                s
            }
            else -> null
        } }).toMutableList()

    /**
     * Returns a vector of the arguments provided to all matches of the given
     * option, together with their positions.
     *
     * Used when an option accepts multiple values.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optStrsPos(name: String): List<Pair<Int, String>> =
        this.optVals(name).mapNotNull({ (p, v) -> when (v) {
            is Matches.Val -> {
                val (s) = v
                Pair(p, s)
            }
            else -> null
        } }).toMutableList()

    /**
     * Returns the string argument supplied to a matching option or `None`.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optStr(name: String): String? =
        when (val matchVal = this.optVal(name)) {
            is Val -> {
                val (s) = matchVal as Val
                s
            }
            else -> null
        }

    /**
     * Returns the matching string, a default, or `None`.
     *
     * Returns `None` if the option was not present, `def` if the option was
     * present but no argument was provided, and the argument if the option was
     * present and an argument was provided.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun optDefault(name: String, def: String): String? =
        run {
            val matchVal = this.optVal(name)
            when {
                matchVal != null && matchVal is Val -> {
                    val (s) = matchVal as Val
                    s
                }
                matchVal != null -> def.toString()
                else -> null
            }
        }

    /**
     * Returns some matching value or `None`.
     *
     * Similar to optStr, also converts matching argument using FromStr.
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun <Err, T> optGet(name: String): io.github.kotlinmania.rustlang.core.result.Result<T?, Err> where T : FromStr<Err> {
        return when (val matchVal = this.optVal(name)) {
            is Val -> {
                val (s) = matchVal as Val
                RustResult.Ok<T?, Err>(when (val tryResult = s.parse()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<T?, Err>(tryResult.errValue()) })
            }
            Given -> RustResult.Ok<T?, Err>(null)
            null -> RustResult.Ok<T?, Err>(null)
        }
    }

    /**
     * Returns a matching value or default.
     *
     * Similar to optDefault, except the two differences.
     * Instead of returning None when argument was not present, return `def`.
     * Instead of returning &str return type T, parsed using str.parse().
     *
     * # Panics
     *
     * This function will panic if the option name is not defined.
     */
    public open fun <Err, T> optGetDefault(name: String, def: T): io.github.kotlinmania.rustlang.core.result.Result<T, Err> where T : FromStr<Err> =
        when (val matchVal = this.optVal(name)) {
            is Val -> {
                val (s) = matchVal as Val
                s.parse()
            }
            Given -> RustResult.Ok<T, Err>(def)
            null -> RustResult.Ok<T, Err>(def)
        }

    /**
     * Returns index of first free argument after "--".
     *
     * If double-dash separator is present and there are some args after it in
     * the argument list then the method returns index into `free` vector
     * indicating first argument after it.
     * behind it.
     *
     * # Examples
     *
     * ```kotlin
     * var opts = Options.new()
     * val matches = opts.parse(mutableListOf("arg1", "--", "arg2")).unwrap()
     * val endPos = matches.freeTrailingStart().unwrap()
     * check(endPos == 1)
     * check(matches.free[endPos] == "arg2".toOwned())
     * ```
     *
     * If the double-dash is missing from argument list or if there are no
     * arguments after it:
     *
     * ```kotlin
     * var opts = Options.new()
     * val matches = opts.parse(mutableListOf("arg1", "--")).unwrap()
     * check(matches.freeTrailingStart() == null)
     * val matches1 = opts.parse(mutableListOf("arg1", "arg2")).unwrap()
     * check(matches1.freeTrailingStart() == null)
     * ```
     */
    public open fun freeTrailingStart(): Int? = this.argsEnd
}

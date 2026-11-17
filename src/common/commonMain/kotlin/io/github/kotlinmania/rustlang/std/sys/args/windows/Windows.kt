// port-lint: source sys/args/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.args.windows

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.char.methods.isAsciiAlphanumeric
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.path.Iter
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.std.sys.helpers.wstr.WStrUnits
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCommandLineW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFullPathNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.ensureNoNuls
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s
import io.github.kotlinmania.rustlang.std.sys.path.windows.getLongPath
import io.github.kotlinmania.rustlang.std.sys.paths.windows.currentExe

// The Windows command line is just a string
// <https://docs.microsoft.com/en-us/archive/blogs/larryosterman/the-windows-command-line-is-just-a-string>
//
// This module implements the parsing necessary to turn that string into a list of arguments.

public fun args(): Args {
    // SAFETY: `GetCommandLineW` returns a pointer to a null terminated UTF-16
    // string so it's safe for `WStrUnits` to use.
    return run {
        val lpCmdLine = getCommandLineW()
        val parsedArgsList =
            parseLpCmdLine(WStrUnits.new(lpCmdLine), {
                currentExe().map(PathBuf::intoOsString).unwrapOrElse({ OsString.new() })
            })
        Args.new(parsedArgsList)
    }
}

/**
 * Implements the Windows command-line argument parsing algorithm.
 *
 * Microsoft's documentation for the Windows CLI argument format can be found at
 * <https://docs.microsoft.com/en-us/cpp/cpp/main-function-command-line-args?view=msvc-160#parsing-c-command-line-arguments>
 *
 * A more in-depth explanation is here:
 * <https://daviddeley.com/autohotkey/parameters/parameters.htm#WIN>
 *
 * Windows includes a function to do command line parsing in shell32.dll.
 * However, this is not used for two reasons:
 *
 * 1. Linking with that DLL causes the process to be registered as a GUI application.
 * GUI applications add a bunch of overhead, even if no windows are drawn. See
 * <https://randomascii.wordpress.com/2018/12/03/a-not-called-function-can-cause-a-5x-slowdown/>.
 *
 * 2. It does not follow the modern C/C++ argv rules outlined in the first two links above.
 *
 * This function was tested for equivalence to the C/C++ parsing rules using an
 * extensive test suite available at
 * <https://github.com/ChrisDenton/winarg/tree/std>.
 */
public fun parseLpCmdLine(lpCmdLine: WStrUnits?, exeName: () -> OsString): List<OsString> {
    val BACKSLASH: NonZero<UShort, NonZeroU16Inner> = (NonZero.new<NonZeroU16Inner, UShort>(92u.toUShort()))!!
    val QUOTE: NonZero<UShort, NonZeroU16Inner> = (NonZero.new<NonZeroU16Inner, UShort>(34u.toUShort()))!!
    val TAB: NonZero<UShort, NonZeroU16Inner> = (NonZero.new<NonZeroU16Inner, UShort>(9u.toUShort()))!!
    val SPACE: NonZero<UShort, NonZeroU16Inner> = (NonZero.new<NonZeroU16Inner, UShort>(32u.toUShort()))!!
    val retVal = mutableListOf<OsString>()
    // If the cmd line pointer is null or it points to an empty string then
    // return the name of the executable as argv[0].
    if (lpCmdLine?.let({ cmd -> cmd.peek() }) == null) {
        retVal.add(exeName())
        return retVal
    }
    val codeUnits = lpCmdLine!!
    // The executable name at the beginning is special.
    var inQuotes = false
    val cur = mutableListOf<OsString>()
    for (w in codeUnits) {
        when {
            // A quote mark always toggles `inQuotes` no matter what because
            // there are no escape characters when parsing the executable name.
            w == QUOTE -> inQuotes = !inQuotes
            // If not `inQuotes` then whitespace ends argv[0].
            (w == SPACE || w == TAB) && !inQuotes -> { break }
            // In all other cases the code unit is taken literally.
            else -> cur.add(w.get())
        }
    }
    // Skip whitespace.
    codeUnits.advanceWhile({ w1 -> w1 == SPACE || w1 == TAB })
    retVal.add(OsString.fromWide(cur))
    // Parse the arguments according to these rules:
    // * All code units are taken literally except space, tab, quote and backslash.
    // * When not `inQuotes`, space and tab separate arguments. Consecutive spaces and tabs are
    // treated as a single separator.
    // * A space or tab `inQuotes` is taken literally.
    // * A quote toggles `inQuotes` mode unless it's escaped. An escaped quote is taken literally.
    // * A quote can be escaped if preceded by an odd number of backslashes.
    // * If any number of backslashes is immediately followed by a quote then the number of
    // backslashes is halved (rounding down).
    // * Backslashes not followed by a quote are all taken literally.
    // * If `inQuotes` then a quote can also be escaped using another quote
    // (i.e. two consecutive quotes become one literal quote).
    val cur1 = mutableListOf<OsString>()
    var inQuotes1 = false
    while (true) {
        val matchVal = codeUnits.next()
        if (matchVal == null) break
        val w1 = matchVal
        when {
            // If not `inQuotes`, a space or tab ends the argument.
            (w1 == SPACE || w1 == TAB) && !inQuotes1 -> {
                retVal.add(OsString.fromWide(cur1))
                cur1.clear()
                // Skip whitespace.
                codeUnits.advanceWhile({ w2 -> w2 == SPACE || w2 == TAB })
            }
            // Backslashes can escape quotes or backslashes but only if consecutive backslashes are followed by a quote.
            w1 == BACKSLASH -> {
                val backslashCount = codeUnits.advanceWhile({ w2 -> w2 == BACKSLASH }) + 1
                if (codeUnits.peek() == QUOTE) {
                    cur1.extend(Iter.repeatN(BACKSLASH.get(), backslashCount / 2))
                    // The quote is escaped if there are an odd number of backslashes.
                    if (backslashCount % 2 == 1) {
                        codeUnits.next()
                        cur1.add(QUOTE.get())
                    }
                } else {
                    // If there is no quote on the end then there is no escaping.
                    cur1.extend(Iter.repeatN(BACKSLASH.get(), backslashCount))
                }
            }
            // If `inQuotes` and not backslash escaped (see above) then a quote either
            // unsets `inQuote` or is escaped by another quote.
            w1 == QUOTE && inQuotes1 -> run {
                val matchVal1 = codeUnits.peek()
                when {
                    // Two consecutive quotes when `inQuotes` produces one literal quote.
                    matchVal1 != null && matchVal1 == QUOTE -> {
                        cur1.add(QUOTE.get())
                        codeUnits.next()
                    }
                    // Otherwise set `inQuotes`.
                    matchVal1 != null -> inQuotes1 = false
                    // The end of the command line.
                    // Push `cur` even if empty, which we do by breaking while `inQuotes` is still set.
                    else -> { break }
                }
            }
            // If not `inQuotes` and not BACKSLASH escaped (see above) then a quote sets `inQuote`.
            w1 == QUOTE -> inQuotes1 = true
            // Everything else is always taken literally.
            else -> cur1.add(w.get())
        }
    }
    // Push the final argument, if any.
    if (!cur1.isEmpty() || inQuotes1) {
        retVal.add(OsString.fromWide(cur1))
    }
    return retVal
}

public sealed class Arg {
    /**
     * Add quotes (if needed)
     */
    public data class Regular(public var value: OsString) : Arg()
    /**
     * Append raw string without quoting
     */
    public data class Raw(public var value: OsString) : Arg()
}

public sealed class Quote {
    // Every arg is quoted
    public data object Always : Quote()
    // Whitespace and empty args are quoted
    public data object Auto : Quote()
    // Arg appended without any changes (#29494)
    public data object Never : Quote()
}

public fun appendArg(cmd: List<UShort>, arg: Arg, forceQuotes: Boolean): Result<Unit, Error> {
    val (arg3, quote) =
        when (arg) {
            is Arg.Regular -> {
                val (arg1) = arg
                Pair(arg1, if (forceQuotes) {
                    Quote.Always
                } else {
                    Quote.Auto
                })
            }
            is Arg.Raw -> {
                val (arg2) = arg
                Pair(arg2, Quote.Never)
            }
        }
    // If an argument has 0 characters then we need to quote it to ensure
    // that it actually gets passed through on the command line or otherwise
    // it will be dropped entirely when parsed on the other end.
    when (val tryResult = ensureNoNuls(arg3)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
    val argBytes = arg3.asEncodedBytes()
    val (quote1, escape) =
        when (quote) {
            Quote.Always -> Pair(true, true)
            Quote.Auto -> {
                Pair(argBytes.iter().any({ c -> c == 32u || c == 9u }) || argBytes.isEmpty(), true)
            }
            Quote.Never -> Pair(false, false)
        }
    if (quote1) {
        cmd.push('"'.code.toUShort())
    }
    var backslashes: Int = 0
    for (x in arg3.encodeWide()) {
        if (escape) {
            if (x == ('\\').code.toUShort()) {
                backslashes += 1
            } else {
                if (x == '"'.code.toUShort()) {
                    // Add n+1 backslashes to total 2n+1 before internal '"'.
                    cmd.extend((0..backslashes).map({ ('\\').code.toUShort() }))
                }
                backslashes = 0
            }
        }
        cmd.push(x)
    }
    if (quote1) {
        // Add n backslashes to total 2n before ending '"'.
        cmd.extend((0 until backslashes).map({ ('\\').code.toUShort() }))
        cmd.push('"'.code.toUShort())
    }
    return Result.Ok<Unit, Error>(Unit)
}

public fun appendBatArg(cmd: List<UShort>, arg: OsStr, quote: Boolean): Result<Unit, Error> {
    var quote1 = quote
    when (val tryResult = ensureNoNuls(arg)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
    // If an argument has 0 characters then we need to quote it to ensure
    // that it actually gets passed through on the command line or otherwise
    // it will be dropped entirely when parsed on the other end.
    //
    // We also need to quote the argument if it ends with `\` to guard against
    // bat usage such as `"%~2"` (i.e. force quote arguments) otherwise a
    // trailing slash will escape the closing quote.
    if (arg.isEmpty() || arg.asEncodedBytes().last() == 92u) {
        quote1 = true
    }
    for (cp in arg.asInner().inner.codePoints()) {
        when (val matchVal = cp.toChar()) {
            null -> {}
            else -> {
                val cp1 = matchVal
                // Rather than trying to find every ascii symbol that must be quoted,
                // we assume that all ascii symbols must be quoted unless they're known to be good.
                // We also quote Unicode control blocks for good measure.
                // Note an unquoted `\` is fine so long as the argument isn't otherwise quoted.
                val UNQUOTED: String  = "#\$*+-./:?@\\_"
                val asciiNeedsQuotes = cp1.isAscii() && !(cp1.isAsciiAlphanumeric() || UNQUOTED.contains(cp1))
                if (asciiNeedsQuotes || cp1.isControl()) {
                    quote1 = true
                }
            }
        }
    }
    if (quote1) {
        cmd.push('"'.code.toUShort())
    }
    // Loop through the string, escaping `\` only if followed by `"`.
    // And escaping `"` by doubling them.
    var backslashes: Int = 0
    for (x in arg.encodeWide()) {
        if (x == ('\\').code.toUShort()) {
            backslashes += 1
        } else {
            if (x == '"'.code.toUShort()) {
                // Add n backslashes to total 2n before internal `"`.
                cmd.extend((0 until backslashes).map({ ('\\').code.toUShort() }))
                // Appending an additional double-quote acts as an escape.
                cmd.push(34u.toUShort())
            } else if (x == ('%').code.toUShort() || x == ('\r').code.toUShort()) {
                // yt-dlp Note: replaces `%` with `% % cd: ~, %` to stop %VAR% being expanded as an environment variable.
                //
                // # Explanation
                //
                // cmd supports extracting a substring from a variable using the following syntax:
                // %variable:~startIndex,endIndex%
                //
                // In the above command `cd` is used as the variable and the startIndex and endIndex are left blank.
                // `cd` is a built-in variable that dynamically expands to the current directory so it's always available.
                // Explicitly omitting both the start and end index creates a zero-length substring.
                //
                // Therefore it all resolves to nothing. However, by doing this no-op we distract cmd.exe
                // from potentially expanding %variables% in the argument.
                cmd.extendFromSlice(ushortArrayOf(('%').code.toUShort(), ('%').code.toUShort(), 'c'.code.toUShort(), 'd'.code.toUShort(), (':').code.toUShort(), ('~').code.toUShort(), (',').code.toUShort()))
            }
            backslashes = 0
        }
        cmd.push(x)
    }
    if (quote1) {
        // Add n backslashes to total 2n before ending `"`.
        cmd.extend((0 until backslashes).map({ ('\\').code.toUShort() }))
        cmd.push('"'.code.toUShort())
    }
    return Result.Ok<Unit, Error>(Unit)
}

public fun makeBatCommandLine(script: List<UShort>, args: List<Arg>, forceQuotes: Boolean): Result<List<UShort>, Error> {
    val INVALID_ARGUMENT_ERROR: Error = constError(ErrorKind.InvalidInput, "batch file arguments are invalid")
    // Windows file names cannot contain a `"` character or end with `\\`.
    // If the script name does then return an error.
    // Also we disallow `^` and `%` to prevent escaping or environment variable substitution.
    val INVALID: List<UShort> = listOf(34u.toUShort(), 94u.toUShort(), 37u.toUShort())
    // Set the start of the command line to `cmd.exe /c "`
    // It is necessary to surround the command in an extra pair of quotes,
    // hence the trailing quote here. It will be closed after all arguments
    // have been added.
    // Using /e:ON enables "command extensions" which is essential for the `%` Note to work.
    val cmd: List<UShort>  = "cmd.exe /e:ON /v:OFF /d /c \"".encodeUtf16().toMutableList()
    // Push the script name surrounded by its quote pair.
    cmd.add(34u.toUShort())
    if (script.any({ c -> INVALID.contains(c) }) || script.last() == (92u.toUShort())) {
        return Result.Err<List<UShort>, Error>(constError(ErrorKind.InvalidInput, "`Command` rejects batch scripts that contain contain `\"`, `^` or `%` or end with `\\`"))
    }
    cmd.addAll((script.stripSuffix(listOf(0)) ?: script))
    cmd.add(34u.toUShort())
    // Append the arguments.
    // Note: This needs tests to ensure that the arguments are properly
    // reconstructed by the batch script by default.
    for (arg in args) {
        cmd.add((' ').code.toUShort())
        when (arg) {
            is Arg.Regular -> {
                val (argOs) = arg
                val argBytes = argOs.asEncodedBytes()
                // Disallow \r and \n as they may truncate the arguments.
                val DISALLOWED: List<UByte> = "\r\n".encodeToByteArray().map { it.toUByte() }
                if (argBytes.iter().any({ c -> DISALLOWED.contains(c) })) {
                    return Result.Err<List<UShort>, Error>(INVALID_ARGUMENT_ERROR)
                }
                when (val tryResult = appendBatArg(cmd, argOs, forceQuotes)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
            }
            else -> {
                // Raw arguments are passed on as-is.
                // It's the user's responsibility to properly handle arguments in this case.
                when (val tryResult = appendArg(cmd, arg, forceQuotes)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
            }
        }
    }
    // Close the quote we left opened earlier.
    cmd.add(34u.toUShort())
    return Result.Ok<List<UShort>, Error>(cmd)
}

/**
 * Takes a path and tries to return a non-verbatim path.
 *
 * This is necessary because cmd.exe does not support verbatim paths.
 */
public fun toUserPath(path: Path): Result<List<UShort>, Error> {
    return fromWideToUserPath(when (val tryResult = toU16s(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) })
}

public fun fromWideToUserPath(path: List<UShort>): Result<List<UShort>, Error> {
    // UTF-16 encoded code points, used in parsing and building UTF-16 paths.
    // All of these are in the ASCII range so they can be cast directly to `UShort`.
    val SEP: UShort = 92u.toUShort()
    val QUERY: UShort = 63u.toUShort()
    val COLON: UShort = 58u.toUShort()
    val U: UShort = 85u.toUShort()
    val N: UShort = 78u.toUShort()
    val C: UShort = 67u.toUShort()
    // Early return if the path is too long to remove the verbatim prefix.
    val LEGACY_MAX_PATH: Int = 260
    if (path.size > LEGACY_MAX_PATH) {
        return Result.Ok<List<UShort>, Error>(path)
    }
    return when {
        // `\\?\C:\...` => `C:\...`
        path.size >= 7 && path[0] == SEP && path[1] == SEP && path[2] == QUERY && path[3] == SEP && path[5] == COLON && path[6] == SEP -> run {
            val lpfilename = ((path).subList(4, (path).size)).asPtr()
            fillUtf16Buf({ buffer, size -> getFullPathNameW(lpfilename, size, buffer, MutPtr(0L)) }, { fullPath: List<UShort> ->
                if (fullPath == ((path).subList(4, path.size - 1))) {
                    val path1: List<UShort>  = fullPath.into()
                    path1.push(0)
                    path1
                } else {
                    path
                }
            })
        }
        // `\\?\UNC\...` => `\\...`
        path.size >= 8 && path[0] == SEP && path[1] == SEP && path[2] == QUERY && path[3] == SEP && path[4] == U && path[5] == N && path[6] == C && path[7] == SEP -> run {
            // Change the `C` in `UNC\` to `\` so we can get a slice that starts with `\\`.
            path[6] = 92u.toUShort()
            val lpfilename1 = ((path).subList(6, (path).size)).asPtr()
            fillUtf16Buf({ buffer, size -> getFullPathNameW(lpfilename1, size, buffer, MutPtr(0L)) }, { fullPath: List<UShort> ->
                if (fullPath == ((path).subList(6, path.size - 1))) {
                    val path1: List<UShort>  = fullPath.into()
                    path1.push(0)
                    path1
                } else {
                    // Restore the `C` in "UNC".
                    path[6] = 67u.toUShort()
                    path
                }
            })
        }
        // For everything else, leave the path unchanged.
        else -> getLongPath(path, false)
    }
}

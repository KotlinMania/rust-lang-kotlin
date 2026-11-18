package io.github.kotlinmania.rustlang.test.term.terminfo

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.test.term.terminfo.Error.*

/**
 * A terminfo creation error.
 */
public sealed class Error {
    /**
     * TermUnset Indicates that the environment doesn't include enough information to find
     * the terminfo entry.
     */
    public data object TermUnset : Error()
    /**
     * MalformedTerminfo indicates that parsing the terminfo entry failed.
     */
    public data class MalformedTerminfo(public var value: String) : Error()
    /**
     * io.Error forwards any io.Errors encountered when finding or reading the terminfo entry.
     */
    public data class IoError(public var value: io.github.kotlinmania.rustlang.core.io.error.Error) : Error()

    public open fun source(): Any? =
        when (this) {
            is IoError -> {
                val (e) = this as IoError
                e
            }
            else -> null
        }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (this) {
            TermUnset -> Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
            is MalformedTerminfo -> {
                val (e) = this as MalformedTerminfo
                run { f.append(e); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
            is IoError -> {
                val (e1) = this as IoError
                e1.fmt(f)
            }
        }
}

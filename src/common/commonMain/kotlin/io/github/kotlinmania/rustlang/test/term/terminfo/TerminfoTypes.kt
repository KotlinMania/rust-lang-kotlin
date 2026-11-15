package io.github.kotlinmania.rustlang.test.term.terminfo

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
    public data class IoError(public var value: Error) : Error()

    public open fun source(): Any? =
        when (this) {
            is IoError -> {
                val (e) = this
                e
            }
            else -> null
        }

    public open fun fmt(f: StringBuilder) {
        when (this) {
            TermUnset -> Unit
            is MalformedTerminfo -> {
                val (e) = this
                e.fmt(f)
            }
            is IoError -> {
                val (e1) = this
                e1.fmt(f)
            }
        }
    }
}

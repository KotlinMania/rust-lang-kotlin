// port-lint: source term.rs
package io.github.kotlinmania.rustlang.test.term

import io.github.kotlinmania.rustlang.test.term.terminfo.TerminfoTerminal

// Terminal formatting module.
//
// This module provides the `Terminal` trait, which abstracts over an [ANSI
// Terminal][ansi] to provide color printing, among other things. There are two
// implementations, the `TerminfoTerminal`, which uses control characters from
// a [terminfo][ti] database, and `WinConsole`, which uses the [Win32 Console
// API][win].
//
// [ansi]: https://en.wikipedia.org/wiki/AnsiEscapeCode
// [win]: https://docs.microsoft.com/en-us/windows/console/character-mode-applications
// [ti]: https://en.wikipedia.org/wiki/Terminfo

/**
 * Alias for stdout terminals.
 */
internal typealias StdoutTerminal = Terminal

/**
 * Returns a Terminal wrapping stdout, or None if a terminal couldn't be
 * opened.
 */
internal fun stdout(): StdoutTerminal? = TerminfoTerminal.new(io.stdout()).map({ t -> ((t as Any?) as StdoutTerminal) })

/**
 * Number for a terminal color
 */
internal typealias Color = UInt

internal val BLACK: Color = 0u

internal val RED: Color = 1u

internal val GREEN: Color = 2u

internal val YELLOW: Color = 3u

internal val BLUE: Color = 4u

internal val MAGENTA: Color = 5u

internal val CYAN: Color = 6u

internal val WHITE: Color = 7u

/**
 * A terminal with similar capabilities to an ANSI Terminal
 * (foreground/background colors etc).
 */
internal interface Terminal : Write {
    /**
     * Sets the foreground color to the given color.
     *
     * If the color is a bright color, but the terminal only supports 8 colors,
     * the corresponding normal color will be used instead.
     *
     * Returns `Ok(true)` if the color was set, `Ok(false)` otherwise, and `Err(e)`
     * if there was an I/O error.
     */
    public fun fg(color: Color): io.Result<Boolean>

    /**
     * Resets all terminal attributes and colors to their defaults.
     *
     * Returns `Ok(true)` if the terminal was reset, `Ok(false)` otherwise, and `Err(e)` if there
     * was an I/O error.
     *
     * *Note: This does not flush.*
     *
     * That means the reset command may get buffered so, if you aren't planning on doing anything
     * else that might flush stdout's buffer (e.g., writing a line of text), you should flush after
     * calling reset.
     */
    public fun reset(): io.Result<Boolean>
}

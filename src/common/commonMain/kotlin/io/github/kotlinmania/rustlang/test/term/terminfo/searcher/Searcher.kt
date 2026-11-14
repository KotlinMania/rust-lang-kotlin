// port-lint: source term/terminfo/searcher.rs
package io.github.kotlinmania.rustlang.test.term.terminfo.searcher

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.rustStrChars
import io.github.kotlinmania.rustlang.std.path.PathBuf

// ncurses-compatible database discovery.
//
// Does not support hashed database, only filesystem!

/**
 * Returns path to database entry for `term`
 */
internal fun getDbpathForTerm(term: String): PathBuf? {
    val dirsToSearch = mutableListOf()
    val firstChar = (term.rustStrChars().nextCodeOrNull()?.toChar() ?: return null)
    // Find search directory
    when (val matchVal = varOs("TERMINFO")) {
        null -> {}
        else -> {
            val dir = matchVal
            dirsToSearch.add(PathBuf.from(dir))
        }
    }
    when (val matchVal2 = variable("TERMINFO_DIRS")) {
        is Ok -> {
            val dirs = matchVal2.value
            for (i in dirs.split(':')) {
                if (i.isEmpty()) {
                    dirsToSearch.add(PathBuf.from("/usr/share/terminfo"))
                } else {
                    dirsToSearch.add(PathBuf.from(i))
                }
            }
        }
        else -> {
            // Found nothing in TERMINFO_DIRS, use the default paths:
            // According to /etc/terminfo/README, after looking at
            // ~/.terminfo, ncurses will search /etc/terminfo, then
            // /lib/terminfo, and eventually /usr/share/terminfo.
            // On Haiku the database can be found at /boot/system/data/terminfo
            when (val matchVal1 = homeDir()) {
                null -> {}
                else -> {
                    val homedir = matchVal1
                    homedir.push(".terminfo")
                    dirsToSearch.add(homedir)
                }
            }
            dirsToSearch.add(PathBuf.from("/etc/terminfo"))
            dirsToSearch.add(PathBuf.from("/lib/terminfo"))
            dirsToSearch.add(PathBuf.from("/usr/share/terminfo"))
            dirsToSearch.add(PathBuf.from("/boot/system/data/terminfo"))
        }
    }
    // Look for the terminal in all of the search directories
    for (p in dirsToSearch) {
        if (metadata(p).isOk()) {
            p.push(firstChar.toString())
            p.push(term)
            if (metadata(p).isOk()) {
                return p
            }
            p.pop()
            p.pop()
            // on some installations the dir is named after the hex of the char
            // (e.g., macOS)
            p.push("${firstChar.toInt()}")
            p.push(term)
            if (metadata(p).isOk()) {
                return p
            }
        }
    }
    return null
}

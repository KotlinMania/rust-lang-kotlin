// port-lint: source symbolize/gimli/parse_running_mmaps_unix.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.parserunningmmapsunix

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsString
import io.github.kotlinmania.rustlang.core.num.rustFromStrRadixInt
import io.github.kotlinmania.rustlang.core.num.rustFromStrRadixULong
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.traits.FromStr

internal open class MapsEntry(
    /**
     * start (inclusive) and limit (exclusive) of address range.
     */
    public var address: Pair<Int, Int>,
    /**
     * Offset into the file (or "whatever").
     */
    public var offset: ULong,
    /**
     * Usually the file backing the mapping.
     *
     * Note: The man page for proc includes a note about "coordination" by
     * using readelf to see the Offset field in ELF program headers. pnkfelix
     * is not yet sure if that is intended to be a comment on pathname, or what
     * form/purpose such coordination is meant to have.
     *
     * There are also some pseudo-paths:
     * "[stack]": The initial process's (aka main thread's) stack.
     * "[stack:<tid>]": a specific thread's stack. (This was only present for a limited range of Linux verisons; it was determined to be too expensive to provide.)
     * "[vdso]": Virtual dynamically linked shared object
     * "[heap]": The process's heap
     *
     * The pathname can be blank, which means it is an anonymous mapping
     * obtained via mmap.
     *
     * Newlines in pathname are replaced with an octal escape sequence.
     *
     * The pathname may have "(deleted)" appended onto it if the file-backed
     * path has been deleted.
     *
     * Note that modifications like the latter two indicated above imply that
     * in general the pathname may be ambiguous. (I.e. you cannot tell if the
     * denoted filename actually ended with the text "(deleted)", or if that
     * was added by the maps rendering.
     */
    public var pathname: OsString,
) : FromStr {
    internal open fun pathname(): OsString = this.pathname

    internal open fun ipMatches(ip: Int): Boolean = this.address.first <= ip && ip < this.address.second

    internal open fun offset(): ULong = this.offset

    public companion object {
        // Format: address perms offset dev inode pathname
        // e.g.: "ffffffffff600000-ffffffffff601000 --xp 00000000 00:00 0                  [vsyscall]"
        // e.g.: "7f5985f46000-7f5985f48000 rw-p 00039000 103:06 76021795                  /usr/lib/x8664-linux-gnu/ld-linux-x86-64.so.2"
        // e.g.: "35b1a21000-35b1a22000 rw-p 00000000 00:00 0"
        public fun fromStr(s: String): Result<MapsEntry, String> {
            fun parseStart(state: String): String {
                var state1 = state
                // Unsafe is unfortunately necessary to get the bounds check removed (for code size).
                val startIdx = state1.bytes().indexOfFirst({ b -> b != 32u }).takeIf { it >= 0 }
                if (startIdx != null) {
                    val startIdx1 = startIdx
                    // SAFETY: It comes from position, so it's in bounds.
                    // It must be on a UTF-8 boundary as it's the first byte that isn't ' '.
                    state1 = state1.getUnchecked(startIdx1 until Int.MAX_VALUE)
                }
                val matchIdx = state1.bytes().indexOfFirst({ b -> b == 32u }).takeIf { it >= 0 }
                return when (matchIdx) {
                    null -> {
                        val result = state1
                        state1 = ""
                        result
                    }
                    else -> {
                        val matchIdx1 = matchIdx
                        // SAFETY: matchIndex comes from .bytes().position() of an ASCII character,
                        // so it's both in bounds and a UTF-8 boundary
                        val result1 = state1.getUnchecked(0 until matchIdx1)
                        // SAFETY: Since matchIdx is the ' ', there must be at least the end after it.
                        state1 = state1.getUnchecked((matchIdx1 + 1) until Int.MAX_VALUE)
                        result1
                    }
                }
            }
            fun error(msg: String): String =
                if (false) {
                    msg
                } else {
                    "invalid map entry"
                }
            // While there are nicer standard library APIs available for this, we aim for minimal code size.
            val state = s
            val rangeStr = parseStart(state)
            if (rangeStr.isEmpty()) {
                return Result.Err(error("Couldn't find address"))
            }
            val permsStr = parseStart(state)
            if (permsStr.isEmpty()) {
                return Result.Err(error("Couldn't find permissions"))
            }
            val offsetStr = parseStart(state)
            if (offsetStr.isEmpty()) {
                return Result.Err(error("Couldn't find offset"))
            }
            val devStr = parseStart(state)
            if (devStr.isEmpty()) {
                return Result.Err(error("Couldn't find dev"))
            }
            val inodeStr = parseStart(state)
            if (inodeStr.isEmpty()) {
                return Result.Err(error("Couldn't find inode"))
            }
            // Pathname may be omitted in which case it will be empty
            val pathnameStr = state.trimAsciiStart()
            val hex = { s1 -> rustFromStrRadixInt(s1, 16u).mapErr({ error("Couldn't parse hex number") }) }
            val hex64 = { s1 -> rustFromStrRadixULong(s1, 16u).mapErr({ error("Couldn't parse hex number") }) }
            val address =
                when (val matchVal = rangeStr.let { s -> val idx = s.indexOf('-'); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) }) {
                    null -> {
                        return Result.Err(error("Couldn't parse address range"))
                    }
                    else -> {
                        val start = matchVal.first
                        val limit = matchVal.second
                        Pair(when (val tryResult = hex(start)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, when (val tryResult = hex(limit)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                    }
                }
            val offset = when (val tryResult = hex64(offsetStr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val pathname = pathnameStr.into()
            return Result.Ok(MapsEntry(address = address, offset = offset, pathname = pathname))
        }
    }
}

internal fun parseMaps(): Result<List<MapsEntry>, String> {
    val buf = ""
    val v = mutableListOf<MapsEntry>()
    var buf1 = buf
    while (true) {
        val matchVal = buf1.bytes().indexOfFirst({ b -> b == 10u }).takeIf { it >= 0 }
        if (matchVal == null) break
        val matchIdx = matchVal
        // Unsafe is unfortunately necessary to get the bounds check removed (for code size).
        // SAFETY: matchIdx is the position of the newline, so it must be valid.
        val line = buf1.getUnchecked(0 until matchIdx)
        v.add(when (val tryResult = line.parse()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
        // SAFETY: matchIdx is the position of the newline, so the byte after it must be valid.
        buf1 = buf1.getUnchecked((matchIdx + 1) until Int.MAX_VALUE)
    }
    return Result.Ok(v)
}

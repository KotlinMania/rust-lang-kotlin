// port-lint: source sys/path/windows_prefix.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.path.windowsprefix

import io.github.kotlinmania.rustlang.core.char.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.path.Prefix
import io.github.kotlinmania.rustlang.std.path.Prefix.DeviceNS
import io.github.kotlinmania.rustlang.std.path.Prefix.Disk
import io.github.kotlinmania.rustlang.std.path.Prefix.Verbatim
import io.github.kotlinmania.rustlang.std.path.Prefix.VerbatimDisk
import io.github.kotlinmania.rustlang.std.path.Prefix.VerbatimUNC
import io.github.kotlinmania.rustlang.std.sys.path.isSepByte
import io.github.kotlinmania.rustlang.std.sys.path.unix.isVerbatimSep

// Parse Windows prefixes, for both Windows and Cygwin.

public open class PrefixParser(
    public var path: OsStr,
    public var prefix: UByteArray,
) {
    public open fun asSlice(): PrefixParserSlice = PrefixParserSlice(path = this.path, prefix = ((this.prefix).sliceArray(0 until LEN.min(this.path.len()))), index = 0)

    public companion object {
        public fun getPrefix(path: OsStr, len: Int): UByteArray {
            val prefix = MutableList(len) { 0 }
            // SAFETY: Only ASCII characters are modified.
            for ((i, ch) in path.asEncodedBytes().iter().take(len).mapIndexed { index, item -> Pair(index, item) }) {
                prefix[i] = if (ch == 47u) {
                    92u
                } else {
                    ch
                }
            }
            return prefix
        }

        public fun new(path: OsStr, len: Int): PrefixParser = PrefixParser(path = path, prefix = PrefixParser.getPrefix(path))
    }
}

public open class PrefixParserSlice(
    public var path: OsStr,
    public var prefix: List<UByte>,
    public var index: Int,
) {
    public open fun stripPrefix(prefix: String): PrefixParserSlice? = ((this.prefix).slice(this.index until this.prefix.size)).startsWith(prefix.encodeToByteArray().map { it.toUByte() }).thenSome(this.copy(index = this.index + prefix.length))

    public open fun prefixBytes(): List<UByte> = this.path.asEncodedBytes()[0 until this.index]

    public open fun finish(): OsStr {
        // SAFETY: The unsafety here stems from converting between &OsStr and
        // &[u8] and back. This is safe to do because (1) we only look at ASCII
        // contents of the encoding and (2) new &OsStr values are produced only
        // from ASCII-bounded slices of existing &OsStr values.
        return OsStr.fromEncodedBytesUnchecked(this.path.asEncodedBytes()[this.index until this.path.asEncodedBytes().size])
    }
}

public fun parsePrefix(path: OsStr): Prefix? {
    val parser = PrefixParser.new(path)
    val parser1 = parser.asSlice()
    return when (val matchVal = parser1.takeIf { it.startsWith("\\\\") }?.removePrefix("\\\\")) {
        null -> {
            // If it has a drive like `C:` then it's a disk.
            // Otherwise there is no prefix.
            Disk((parseDrive(path) ?: return null))
        }
        else -> {
            val parser2 = matchVal
            // \\
            // It's a POSIX path.
            if (false && !path.asEncodedBytes().contains(92u)) {
                return null
            }
            // The meaning of verbatim paths can change when they use a different
            // separator.
            if (parser2.takeIf { it.startsWith("?\\") }?.removePrefix("?\\") != null && (false || !parser2.prefixBytes().contains(47u))) {
                // \\?\
                when (val matchVal2 = parser2.takeIf { it.startsWith("UNC\\") }?.removePrefix("UNC\\")) {
                    null -> {
                        val path1 = parser2.finish()
                        // in verbatim paths only recognize an exact drive prefix
                        when (val matchVal1 = parseDriveExact(path1)) {
                            null -> {
                                // \\?\prefix
                                val (prefix, _) = parseNextComponent(path1, true)
                                Verbatim(prefix)
                            }
                            else -> {
                                val drive = matchVal1
                                // \\?\C:
                                VerbatimDisk(drive)
                            }
                        }
                    }
                    else -> {
                        val parser3 = matchVal2
                        // \\?\UNC\server\share
                        val path2 = parser3.finish()
                        val (server, path3) = parseNextComponent(path2, true)
                        val (share, _) = parseNextComponent(path3, true)
                        VerbatimUNC(server, share)
                    }
                }
            } else when (val matchVal3 = parser2.takeIf { it.startsWith(".\\") }?.removePrefix(".\\")) {
                null -> {
                    val path4 = parser2.finish()
                    val (server1, path5) = parseNextComponent(path4, false)
                    val (share1, _) = parseNextComponent(path5, false)
                    if (!server1.isEmpty() && !share1.isEmpty()) {
                        // \\server\share
                        unc(server1, share1)
                    } else {
                        // no valid prefix beginning with "\\" recognized
                        null
                    }
                }
                else -> {
                    val parser4 = matchVal3
                    // \\.\COM42
                    val path6 = parser4.finish()
                    val (prefix1, _) = parseNextComponent(path6, false)
                    DeviceNS(prefix1)
                }
            }
        }
    }
}

// Parses a drive prefix, e.g. "C:" and "C:\whatever"
public fun parseDrive(path: OsStr): UByte? {
    // In most DOS systems, it is not possible to have more than 26 drive letters.
    // See <https://en.wikipedia.org/wiki/DriveLetterAssignment#CommonAssignments>.
    fun isValidDriveLetter(drive: UByte): Boolean = drive.isAsciiAlphabetic()
    return run {
        val matchVal = path.asEncodedBytes()
        when {
            matchVal.size >= 2 && matchVal[1] == 58u && isValidDriveLetter(matchVal[0]) -> {
                val drive = matchVal[0]
                drive.toAsciiUppercase()
            }
            else -> null
        }
    }
}

// Parses a drive prefix exactly, e.g. "C:"
public fun parseDriveExact(path: OsStr): UByte? {
    // only parse two bytes: the drive letter and the drive separator
    return if ((path.asEncodedBytes().get(2)?.let({ x -> isVerbatimSep(x) }) ?: true)) {
        parseDrive(path)
    } else {
        null
    }
}

// Parse the next path component.
//
// Returns the next component and the rest of the path excluding the component and separator.
// Does not recognize `/` as a separator character on Windows if `verbatim` is true.
public fun parseNextComponent(path: OsStr, verbatim: Boolean): Pair<OsStr, OsStr> {
    val separator =
        if (verbatim) {
            isVerbatimSep
        } else {
            isSepByte
        }
    return when (val matchVal = path.asEncodedBytes().iter().indexOfFirst({ x -> separator(x) }).takeIf { it >= 0 }) {
        null -> Pair(path, OsStr.new(""))
        else -> {
            val separatorStart = matchVal
            val separatorEnd = separatorStart + 1
            val component = path.asEncodedBytes()[0 until separatorStart]
            // Panic safe
            // The max `separatorEnd` is `bytes.len()` and `bytes[bytes.len() ..]` is a valid index.
            val path1 = path.asEncodedBytes()[separatorEnd until path.asEncodedBytes().size]
            // SAFETY: `path` is a valid wtf8 encoded slice and each of the separators ('/', '\')
            // is encoded in a single byte, therefore `bytes[separatorStart]` and
            // `bytes[separatorEnd]` must be code point boundaries and thus
            // `bytes[.. separatorStart]` and `bytes[separatorEnd ..]` are valid wtf8 slices.
            Pair(OsStr.fromEncodedBytesUnchecked(component), OsStr.fromEncodedBytesUnchecked(path1))
        }
    }
}

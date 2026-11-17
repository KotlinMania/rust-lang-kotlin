// port-lint: source types.rs
package io.github.kotlinmania.rustlang.backtrace.types

import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString.*
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.path.PathBuf

// Platform dependent types.

/**
 * A platform independent representation of a string. When working with `std`
 * enabled it is recommended to the convenience methods for providing
 * conversions to `std` types.
 */
public sealed class BytesOrWideString {
    /**
     * A slice, typically provided on Unix platforms.
     */
    public data class Bytes(public var value: List<UByte>) : BytesOrWideString()
    /**
     * Wide strings typically from Windows.
     */
    public data class Wide(public var value: List<UShort>) : BytesOrWideString()

    /**
     * Lossy converts to a `Cow<String>`, will allocate if `Bytes` is not valid
     * UTF-8 or if `BytesOrWideString` is `Wide`.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun toStrLossy(): String =
        when (this) {
            is Bytes -> {
                val (slice) = this
                String.fromUtf8Lossy(slice)
            }
            is Wide -> {
                val (wide) = this
                String.fromUtf16Lossy(wide)
            }
        }

    /**
     * Provides a `Path` representation of `BytesOrWideString`.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun intoPathBuf(): PathBuf {
        run {
            if (this is BytesOrWideString.Bytes) {
                val (slice) = this
                return PathBuf.from(OsStr.fromBytes(slice))
            }
        }
        run {
            if (this is BytesOrWideString.Wide) {
                val (slice1) = this
                return PathBuf.from(OsString.fromWide(slice1))
            }
        }
        if (this is BytesOrWideString.Bytes) {
            val (b) = this
            when (val matchVal = String.fromUtf8(b)) {
                is Result.Ok -> {
                    val s = matchVal.value
                    return PathBuf.from(s)
                }
                else -> {}
            }
        }
        error("unreachable")
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.toStrLossy().fmt(f)
}

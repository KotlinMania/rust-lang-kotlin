// port-lint: source os/windows/ffi.rs
package io.github.kotlinmania.rustlang.std.os.windows.ffi

import io.github.kotlinmania.rustlang.alloc.wtf8.Wtf8Buf
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.sys.FromInner
import io.github.kotlinmania.rustlang.sys.osstr.bytes.Buf

// Windows-specific extensions to primitives in the [std.ffi] module.
//
// # Overview
//
// For historical reasons, the Windows API uses a form of potentially
// ill-formed UTF-16 encoding for strings. Specifically, the 16-bit
// code units in Windows strings may contain [isolated surrogate code
// points which are not paired together][ill-formed-utf-16]. The
// Unicode standard requires that surrogate code points (those in the
// range U+D800 to U+DFFF) always be *paired*, because in the UTF-16
// encoding a *surrogate code unit pair* is used to encode a single
// character. For compatibility with code that does not enforce
// these pairings, Windows does not enforce them, either.
//
// While it is not always possible to convert such a string losslessly into
// a valid UTF-16 string (or even UTF-8), it is often desirable to be
// able to round-trip such a string from and to Windows APIs
// losslessly. For example, some Rust code may be "bridging" some
// Windows APIs together, just passing `WCHAR` strings among those
// APIs without ever really looking into the strings.
//
// If Rust code *does* need to look into those strings, it can
// convert them to valid UTF-8, possibly lossily, by substituting
// invalid sequences with [`U.Fffd.Replacement.Character`][U+FFFD], as is
// conventionally done in other Rust APIs that deal with string
// encodings.
//
// # `OsStringExt` and `OsStrExt`
//
// [OsString] is the Rust wrapper for owned strings in the
// preferred representation of the operating system. On Windows,
// this struct gets augmented with an implementation of the
// [OsStringExt] trait, which has an [OsStringExt.fromWide] method. This
// lets you create an [OsString] from a `[UShort]` slice; presumably
// you get such a slice out of a `WCHAR` Windows API.
//
// Similarly, [OsStr] is the Rust wrapper for borrowed strings from
// preferred representation of the operating system. On Windows, the
// [OsStrExt] trait provides the [OsStrExt.encodeWide] method, which
// outputs an [EncodeWide] iterator. You can [collect] this
// iterator, for example, to obtain a `List<UShort>`; you can later get a
// pointer to this vector's contents and feed it to Windows APIs.
//
// These traits, along with [OsString] and [OsStr], work in
// conjunction so that it is possible to **round-trip** strings from
// Windows and back, with no loss of data, even if the strings are
// ill-formed UTF-16.
//
// [ill-formed-utf-16]: https://simonsapin.github.io/wtf-8/#ill-formed-utf-16
// [U+FFFD]: char.ReplacementCharacter

/**
 * Windows-specific extensions to [OsString].
 */
public interface OsStringExt<T> {
    /**
     * Creates an `OsString` from a potentially ill-formed UTF-16 slice of
     * 16-bit code units.
     *
     * This is lossless: calling [OsStrExt.encodeWide] on the resulting string
     * will always return the original code units.
     *
     * # Examples
     *
     * use std.ffi.OsString;
     * use std.os.windows.prelude::*;
     *
     * // UTF-16 encoding for "Unicode".
     * let source = [0x0055, 0x006E, 0x0069, 0x0063, 0x006F, 0x0064, 0x0065];
     *
     * let string = OsString.fromWide(&source[..]);
     * ```kotlin
     */
    public fun fromWide(wide: List<UShort>): T

    public companion object {
        public fun fromWide(wide: List<UShort>): OsString = osStringExtFromWideOsString(wide)
    }
}

public fun osStringExtFromWideOsString(wide: List<UShort>): OsString = FromInner.fromInner(Buf(inner = Wtf8Buf.fromWide(wide)))

/**
 * Windows-specific extensions to [OsStr].
 */
public interface OsStrExt<T> {
    /**
     * Re-encodes an `OsStr` as a wide character sequence, i.e., potentially
     * ill-formed UTF-16.
     *
     * This is lossless: calling [OsStringExt.fromWide] and then
     * `encodeWide` on the result will yield the original code units.
     * Note that the encoding does not add a final null terminator.
     *
     * # Examples
     *
     * use std.ffi.OsString;
     * use std.os.windows.prelude::*;
     *
     * // UTF-16 encoding for "Unicode".
     * let source = [0x0055, 0x006E, 0x0069, 0x0063, 0x006F, 0x0064, 0x0065];
     *
     * let string = OsString.fromWide(&source[..]);
     *
     * let result: Vec<u16> = string.encodeWide().collect();
     * assertEq(&source[..], &result[..]);
     * ```kotlin
     */
    public fun encodeWide(): EncodeWide
}

public fun OsStr.encodeWide(): EncodeWide = EncodeWide(inner = this.asInner().inner.encodeWide())

/**
 * Iterator returned by [OsStrExt.encodeWide].
 */
public open class EncodeWide(
    public var inner: EncodeWide,
) : Iterable<UShort> {
    override fun iterator(): Iterator<UShort> = object : AbstractIterator<UShort>() {
        override fun computeNext() {
            when (val v = this@EncodeWide.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EncodeWide = EncodeWide(inner)

    public open fun fmt(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun next(): UShort? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()
}

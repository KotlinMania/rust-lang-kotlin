// port-lint: source os/unix/ffi/os_str.rs
package io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.sys.FromInner
import io.github.kotlinmania.rustlang.sys.osstr.bytes.Buf

// Note: this file is currently reused in other `std.os.{ platform }.ffi` modules to reduce duplication.
// Keep this in mind when applying changes to this file that only apply to `unix`.

/**
 * Platform-specific extensions to [OsString].
 */
public interface OsStringExt<T> {
    /**
     * Creates an [OsString] from a byte vector.
     *
     * See the module documentation for an example.
     */
    public fun fromVec(vec: List<UByte>): T

    /**
     * Yields the underlying byte vector of this [OsString].
     *
     * See the module documentation for an example.
     */
    public fun intoVec(): List<UByte>

    public companion object {
        public fun fromVec(vec: List<UByte>): OsString = osStringExtFromVecOsString(vec)
    }
}

public fun osStringExtFromVecOsString(vec: List<UByte>): OsString = FromInner.fromInner(Buf(inner = vec))

public fun OsString.intoVec(): List<UByte> = this.intoInner().inner

/**
 * Platform-specific extensions to [OsStr].
 */
public interface OsStrExt<T> {
    /**
     * Creates an [OsStr] from a byte slice.
     *
     * See the module documentation for an example.
     */
    public fun fromBytes(slice: List<UByte>): T

    /**
     * Gets the underlying byte view of the [OsStr] slice.
     *
     * See the module documentation for an example.
     */
    public fun asBytes(): List<UByte>

    public companion object {
        public fun fromBytes(slice: List<UByte>): OsStr = osStrExtFromBytesOsStr(slice)
    }
}

public fun osStrExtFromBytesOsStr(slice: List<UByte>): OsStr = transmute(slice)

public fun OsStr.asBytes(): List<UByte> = this.asInner().inner

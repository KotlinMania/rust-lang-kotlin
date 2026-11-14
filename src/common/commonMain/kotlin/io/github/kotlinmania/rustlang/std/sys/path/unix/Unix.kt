// port-lint: source sys/path/unix.rs
package io.github.kotlinmania.rustlang.std.sys.path.unix

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.env.currentDir
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.path.Prefix

public fun isVerbatimSep(b: UByte): Boolean = isSepByte(b)

public fun parsePrefix(arg0: OsStr): Prefix? = null

public val HAS_PREFIXES: Boolean = false

/**
 * Make a POSIX path absolute without changing its semantics.
 */
public fun absolute(path: Path): Result<PathBuf, Error> {
    // This is mostly a wrapper around collecting `Path.components`, with
    // exceptions made where this conflicts with the POSIX specification.
    // See 4.16 Pathname Resolution, IEEE Std 1003.1-2024
    // https://pubs.opengroup.org/onlinepubs/9799919799/basedefs/V1Chap04.html#tag0416
    // Get the components, skipping the redundant leading "." component if it exists.
    val components = (path.stripPrefix(".") ?: path).components()
    val pathOs = path.asOsStr().asEncodedBytes()
    var normalized =
        if (path.isAbsolute()) {
            // "If a pathname begins with two successive <slash> characters, the
            // first component following the leading <slash> characters may be
            // interpreted in an implementation-defined manner, although more than
            // two leading <slash> characters shall be treated as a single <slash>
            // character."
            if (pathOs.startsWith("//".encodeToByteArray()) && !pathOs.startsWith("///".encodeToByteArray())) {
                components.next()
                PathBuf.from("//")
            } else {
                PathBuf.new()
            }
        } else {
            when (val tryResult = currentDir()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
    normalized.extend(components)
    // "Interfaces using pathname resolution may specify additional constraints
    // when a pathname that does not name an existing directory contains at
    // least one non- <slash> character and contains one or more trailing
    // <slash> characters".
    // A trailing <slash> is also meaningful if "a symbolic link is
    // encountered during pathname resolution".
    if (pathOs.endsWith("/".encodeToByteArray())) {
        normalized.push("")
    }
    return Result.Ok(normalized)
}

public fun isAbsolute(path: Path): Boolean =
    if (false) {
        path.hasRoot()
    } else {
        path.hasRoot() && path.prefix() != null
    }

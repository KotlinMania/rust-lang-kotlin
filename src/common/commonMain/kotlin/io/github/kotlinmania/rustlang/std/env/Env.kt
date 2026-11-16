// port-lint: source env.rs
package io.github.kotlinmania.rustlang.std.env

import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.env.unix.env
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.DLL_EXTENSION
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.DLL_PREFIX
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.DLL_SUFFIX
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.EXE_EXTENSION
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.EXE_SUFFIX
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.FAMILY
import io.github.kotlinmania.rustlang.std.sys.envconsts.os.OS
import io.github.kotlinmania.rustlang.std.sys.paths.unix.chdir
import io.github.kotlinmania.rustlang.std.sys.paths.unix.getcwd
import io.github.kotlinmania.rustlang.std.sys.env.unix.env as envimp

// Inspection and manipulation of the process's environment.
//
// This module contains functions to inspect various aspects such as
// environment variables, process arguments, the current directory, and various
// other important directories.
//
// There are several functions and structs in this module that have a
// counterpart ending in `os`. Those ending in `os` will return an [OsString]
// and those without will return a [String].

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Returns the current working directory as a [PathBuf].
 *
 * # Platform-specific behavior
 *
 * This function [currently] corresponds to the `getcwd` function on Unix
 * and the `GetCurrentDirectoryW` function on Windows.
 *
 * # Errors
 *
 * Returns an [Err] if the current working directory value is invalid.
 * Possible cases:
 *
 * * Current directory does not exist.
 * * There are insufficient permissions to access the current directory.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * fun main(): Result<Unit> {
 *     val path = currentDir().getOrElse { return Result.failure(it) }
 *     println("The current directory is ${path.display()}")
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun currentDir(): Result<PathBuf, Error> = getcwd()

/**
 * Changes the current working directory to the specified path.
 *
 * # Platform-specific behavior
 *
 * This function [currently] corresponds to the `chdir` function on Unix
 * and the `SetCurrentDirectoryW` function on Windows.
 *
 * Returns an [Err] if the operation fails.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 * import std.path.Path
 *
 * val root = Path.new("/")
 * check(setCurrentDir(root).isOk())
 * println("Successfully changed working directory to ${root.display()}!")
 * ```
 */
public fun setCurrentDir(path: Path): Result<Unit, Error> = chdir(path)

/**
 * An iterator over a snapshot of the environment variables of this process.
 *
 * This structure is created by [env.vars]. See its documentation for more.
 *
 * [env.vars]: vars
 */
public open class Vars(
    public var inner: VarsOs,
) : Iterable<Pair<String, String>> {
    override fun iterator(): Iterator<Pair<String, String>> = object : AbstractIterator<Pair<String, String>>() {
        override fun computeNext() {
            when (val v = this@Vars.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<String, String>? = this.inner.next()?.let({ (a, b) -> Pair(a.intoString().unwrap(), b.intoString().unwrap()) })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val inner = this.inner.inner
        return DebugStruct(f, "Vars").field("inner", inner).finish()
    }
}

/**
 * An iterator over a snapshot of the environment variables of this process.
 *
 * This structure is created by [env.varsOs]. See its documentation for more.
 *
 * [env.varsOs]: varsOs
 */
public open class VarsOs(
    public var inner: envimp.Env,
) : Iterable<Pair<OsString, OsString>> {
    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@VarsOs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<OsString, OsString>? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val inner = this.inner
        return DebugStruct(f, "VarsOs").field("inner", inner).finish()
    }
}

/**
 * Returns an iterator of (variable, value) pairs of strings, for all the
 * environment variables of the current process.
 *
 * The returned iterator contains a snapshot of the process's environment
 * variables at the time of this invocation. Modifications to environment
 * variables afterwards will not be reflected in the returned iterator.
 *
 * # Panics
 *
 * While iterating, the returned iterator will panic if any key or value in the
 * environment is not valid unicode. If this is not desired, consider using
 * [env.varsOs].
 *
 * # Examples
 *
 * ```kotlin
 * // Print all environment variables.
 * for ((key, value) in vars()) {
 *     println("$key: $value")
 * }
 * ```
 *
 * [env.varsOs]: varsOs
 */
public fun vars(): Vars = Vars(inner = varsOs())

/**
 * Returns an iterator of (variable, value) pairs of OS strings, for all the
 * environment variables of the current process.
 *
 * The returned iterator contains a snapshot of the process's environment
 * variables at the time of this invocation. Modifications to environment
 * variables afterwards will not be reflected in the returned iterator.
 *
 * Note that the returned iterator will not check if the environment variables
 * are valid Unicode. If you want to panic on invalid UTF-8,
 * use the [vars] function instead.
 *
 * # Examples
 *
 * ```kotlin
 * // Print all environment variables.
 * for ((key, value) in varsOs()) {
 *     println("$key: $value")
 * }
 * ```
 */
public fun varsOs(): VarsOs = VarsOs(inner = envimp.env())

/**
 * Fetches the environment variable `key` from the current process.
 *
 * # Errors
 *
 * Returns [VarError.NotPresent] if:
 * - The variable is not set.
 * - The variable's name contains an equal sign or NUL (`'='` or `'\0'`).
 *
 * Returns [VarError.NotUnicode] if the variable's value is not valid
 * Unicode. If this is not desired, consider using [varOs].
 *
 * Use [env] or [optionEnv] instead if you want to check environment
 * variables at compile time.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * val key = "HOME"
 * run {
 *     val matchVal = variable(key)
 *     if (matchVal.isSuccess) {
 *         val value = matchVal.getOrThrow()
 *         println("$key: $value")
 *     } else {
 *         val e = matchVal.exceptionOrNull()!!
 *         println("couldn't interpret $key: $e")
 *     }
 * }
 * ```
 */
public fun variable(key: OsStr): Result<String, VarError> {
    fun inner(key1: OsStr): Result<String, VarError> {
        return when (val tryResult = envimp.getenv(key1).okOr(VarError.NotPresent)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<String, VarError>(tryResult.error) }.intoString().mapErr({ VarError.NotUnicode(it) })
    }
    return inner(key)
}

/**
 * Fetches the environment variable `key` from the current process, returning
 * [None] if the variable isn't set or if there is another error.
 *
 * It may return `None` if the environment variable's name contains
 * the equal sign character (`=`) or the NUL character.
 *
 * Note that this function will not check if the environment variable
 * is valid Unicode. If you want to have an error on invalid UTF-8,
 * use the [variable] function instead.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * val key = "HOME"
 * when (val matchVal = varOs(key)) {
 *     null -> println("$key is not defined in the environment.")
 *     else -> {
 *         val value = matchVal
 *         println("$key: $value")
 *     }
 * }
 * ```
 *
 * If expecting a delimited variable (such as `PATH`), [splitPaths]
 * can be used to separate items.
 */
public fun varOs(key: OsStr): OsString? = envimp.getenv(key)

/**
 * The error type for operations interacting with environment variables.
 * Possibly returned from [env.variable].
 *
 * [env.variable]: var
 */
public sealed class VarError {
    /**
     * The specified environment variable was not present in the current
     * process's environment.
     */
    public data object NotPresent : VarError()
    /**
     * The specified environment variable was found, but it did not contain
     * valid unicode data. The found data is returned as a payload of this
     * variant.
     */
    public data class NotUnicode(public var value: OsString) : VarError()

    public fun rustEquals(other: VarError): Boolean = this == other

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (this) {
            VarError.NotPresent -> run { f.append("environment variable not found"); Result.Ok(Unit) }
            is VarError.NotUnicode -> {
                val (s) = this
                run { f.append("environment variable was not valid unicode: $s"); Result.Ok(Unit) }
            }
        }
}

/**
 * Sets the environment variable `key` to the value `value` for the currently running
 * process.
 *
 * # Safety
 *
 * This function is sound to call in a single-threaded program.
 *
 * This function is also always sound to call on Windows, in single-threaded
 * and multi-threaded programs.
 *
 * In multi-threaded programs on other operating systems, the only sound option is
 * to not use `setVar` or `removeVar` at all.
 *
 * The exact requirement is: you
 * must ensure that there are no other threads concurrently writing or
 * *reading*(!) the environment through functions or global variables other
 * than the ones in this module. The problem is that these operating systems
 * do not provide a thread-safe way to read the environment, and most C
 * libraries, including libc itself, do not advertise which functions read
 * from the environment. Even functions from the Rust standard library may
 * read the environment without going through this module, e.g. for DNS
 * lookups from [std.net.ToSocketAddrs]. No stable guarantee is made about
 * which functions may read from the environment in future versions of a
 * library. All this makes it not practically possible for you to guarantee
 * that no other thread will read the environment, so the only sound option is
 * to not use `setVar` or `removeVar` in multi-threaded programs at all.
 *
 * Discussion of this unsafety on Unix may be found in:
 *
 *  - [Austin Group Bugzilla (for POSIX)](https://austingroupbugs.net/view.php?id=188)
 *  - [GNU C library Bugzilla](https://sourceware.org/bugzilla/showBug.cgi?id=15607#c2)
 *
 * To pass an environment variable to a child process, you can instead use [Command.env].
 *
 * # Panics
 *
 * This function may panic if `key` is empty, contains an ASCII equals sign `'='`
 * or the NUL character `'\0'`, or when `value` contains the NUL character.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * val key = "KEY"
 * run {
 *     setVar(key, "VALUE")
 * }
 * check(variable(key) == Result.success("VALUE".toString()))
 * ```
 */
public fun setVar(key: OsStr, value: OsStr) {
    val (key1, value1) = Pair(key, value)
    envimp.setenv(key1, value1).unwrapOrElse({ e ->
        error("failed to set environment variable `$key` to `$value`: $e")
    })
}

/**
 * Removes an environment variable from the environment of the currently running process.
 *
 * # Safety
 *
 * This function is sound to call in a single-threaded program.
 *
 * This function is also always sound to call on Windows, in single-threaded
 * and multi-threaded programs.
 *
 * In multi-threaded programs on other operating systems, the only sound option is
 * to not use `setVar` or `removeVar` at all.
 *
 * The exact requirement is: you
 * must ensure that there are no other threads concurrently writing or
 * *reading*(!) the environment through functions or global variables other
 * than the ones in this module. The problem is that these operating systems
 * do not provide a thread-safe way to read the environment, and most C
 * libraries, including libc itself, do not advertise which functions read
 * from the environment. Even functions from the Rust standard library may
 * read the environment without going through this module, e.g. for DNS
 * lookups from [std.net.ToSocketAddrs]. No stable guarantee is made about
 * which functions may read from the environment in future versions of a
 * library. All this makes it not practically possible for you to guarantee
 * that no other thread will read the environment, so the only sound option is
 * to not use `setVar` or `removeVar` in multi-threaded programs at all.
 *
 * Discussion of this unsafety on Unix may be found in:
 *
 *  - [Austin Group Bugzilla](https://austingroupbugs.net/view.php?id=188)
 *  - [GNU C library Bugzilla](https://sourceware.org/bugzilla/showBug.cgi?id=15607#c2)
 *
 * To prevent a child process from inheriting an environment variable, you can
 * instead use [Command.envRemove] or [Command.envClear].
 *
 * # Panics
 *
 * This function may panic if `key` is empty, contains an ASCII equals sign
 * `'='` or the NUL character `'\0'`, or when the value contains the NUL
 * character.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * val key = "KEY"
 * run {
 *     setVar(key, "VALUE")
 * }
 * check(variable(key) == Result.success("VALUE".toString()))
 * run {
 *     removeVar(key)
 * }
 * check(variable(key).isErr())
 * ```
 */
public fun removeVar(key: OsStr) {
    val key1 = key
    envimp.unsetenv(key1).unwrapOrElse({ e -> error("failed to remove environment variable `$key`: $e") })
}

/**
 * An iterator that splits an environment variable into paths according to
 * platform-specific conventions.
 *
 * The iterator element type is [PathBuf].
 *
 * This structure is created by [env.splitPaths]. See its
 * documentation for more.
 *
 * [env.splitPaths]: splitPaths
 */
public open class SplitPaths(
    public var inner: io.github.kotlinmania.rustlang.std.sys.paths.unix.SplitPaths,
) : Iterable<PathBuf> {
    override fun iterator(): Iterator<PathBuf> = object : AbstractIterator<PathBuf>() {
        override fun computeNext() {
            when (val v = this@SplitPaths.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): PathBuf? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "SplitPaths").finishNonExhaustive()
}

/**
 * Parses input according to platform conventions for the `PATH`
 * environment variable.
 *
 * Returns an iterator over the paths contained in `unparsed`. The iterator
 * element type is [PathBuf].
 *
 * On most Unix platforms, the separator is `:` and on Windows it is `;`. This
 * also performs unquoting on Windows.
 *
 * [joinPaths] can be used to recombine elements.
 *
 * # Panics
 *
 * This will panic on systems where there is no delimited `PATH` variable,
 * such as UEFI.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * val key = "PATH"
 * when (val matchVal = varOs(key)) {
 *     null -> println("$key is not defined in the environment.")
 *     else -> {
 *         val paths = matchVal
 *         for (path in splitPaths(paths)) {
 *             println("'${path.display()}'")
 *         }
 *     }
 * }
 * ```
 */
public fun splitPaths(unparsed: OsStr): SplitPaths = SplitPaths(inner = io.github.kotlinmania.rustlang.std.sys.paths.unix.splitPaths(unparsed.asRef()))

/**
 * An iterator that splits an environment variable into paths according to
 * platform-specific conventions.
 *
 * The iterator element type is <code>&[Path]</code>.
 *
 * This structure is created by [env.splitPathsRef]. See its
 * documentation for more.
 *
 * [env.splitPathsRef]: splitPathsRef
 */
public open class SplitPathsRef(
    public var inner: io.github.kotlinmania.rustlang.std.sys.paths.unix.SplitPathsRef,
) : Iterable<Path> {
    override fun iterator(): Iterator<Path> = object : AbstractIterator<Path>() {
        override fun computeNext() {
            when (val v = this@SplitPathsRef.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Path? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "SplitPathsRef").finishNonExhaustive()
}

/**
 * Parses input according to platform conventions for the `PATH`
 * environment variable.
 *
 * Unlike [splitPaths], this function does not allocate and instead yields
 * [Path]s borrowed from `unparsed`. Returns `None` on platforms that may
 * require allocations to handle `PATH` splitting conventions.
 *
 * # Platform-specific behavior
 *
 * Returns `Some` on Unix platforms and `None` on all other platforms.
 * Note that this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 */
public fun splitPathsRef(unparsed: OsStr): SplitPathsRef? {
    return SplitPathsRef(inner = (io.github.kotlinmania.rustlang.std.sys.paths.unix.splitPathsRef(unparsed.asRef()) ?: return null))
}

/**
 * The error type for operations on the `PATH` variable. Possibly returned from
 * [env.joinPaths].
 *
 * [env.joinPaths]: joinPaths
 */
public open class JoinPathsError(
    public var inner: io.github.kotlinmania.rustlang.std.sys.paths.unix.JoinPathsError,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.inner.fmt(f)

    public open fun description(): String = this.inner.description()
}

/**
 * Joins a collection of [Path]s appropriately for the `PATH`
 * environment variable.
 *
 * # Errors
 *
 * Returns an [Err] (containing an error message) if one of the input
 * [Path]s contains an invalid character for constructing the `PATH`
 * variable (a double quote on Windows or a colon on Unix or semicolon on
 * UEFI), or if the system does not have a `PATH`-like variable (e.g. WASI).
 *
 * # Examples
 *
 * Joining paths on a Unix-like platform:
 *
 * ```kotlin
 * import std.env
 * import std.ffi.OsString
 * import std.path.Path
 *
 * fun main(): Result<Unit> {
 *     val paths = listOf(Path.new("/bin"), Path.new("/usr/bin"))
 *     val pathOsString = joinPaths(paths.iter()).getOrElse { return Result.failure(it) }
 *     check(pathOsString == OsString.from("/bin:/usr/bin"))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Joining a path containing a colon on a Unix-like platform results in an
 * error:
 *
 * ```kotlin
 * import std.env
 * import std.path.Path
 *
 * val paths = listOf(Path.new("/bin"), Path.new("/usr/bi:n"))
 * check(joinPaths(paths.iter()).isErr())
 * ```
 *
 * Using `env.joinPaths()` with [env.splitPaths] to append an item to
 * the `PATH` environment variable:
 *
 * ```kotlin
 * import std.env
 * import std.path.PathBuf
 *
 * fun main(): Result<Unit> {
 *     when (val matchVal = varOs("PATH")) {
 *         null -> {}
 *         else -> {
 *             val path = matchVal
 *             var paths = splitPaths(path).collect<Vec>()
 *             paths.push(PathBuf.from("/home/xyz/bin"))
 *             val newPath = joinPaths(paths).getOrElse { return Result.failure(it) }
 *             run {
 *                 setVar("PATH", newPath)
 *             }
 *         }
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [env.splitPaths]: splitPaths
 */
public fun <I> joinPaths(paths: I): Result<OsString, JoinPathsError> where I : Iterable<OsStr> = io.github.kotlinmania.rustlang.std.sys.paths.unix.joinPaths(paths.iterator()).mapErr({ e -> JoinPathsError(inner = e) })

/**
 * Returns the path of the current user's home directory if known.
 *
 * This may return `None` if getting the directory fails or if the platform does not have user home directories.
 *
 * For storing user data and configuration it is often preferable to use more specific directories.
 * For example, [XDG Base Directories] on Unix or the `LOCALAPPDATA` and `APPDATA` environment variables on Windows.
 *
 * [XDG Base Directories]: https://specifications.freedesktop.org/basedir-spec/latest/
 *
 * # Unix
 *
 * - Returns the value of the 'HOME' environment variable if it is set
 *   (and not an empty string).
 * - Otherwise, it tries to determine the home directory by invoking the `getpwuidR` function
 *   using the UID of the current user. An empty home directory field returned from the
 *   `getpwuidR` function is considered to be a valid value.
 * - Returns `None` if the current user has no entry in the /etc/passwd file.
 *
 * # Windows
 *
 * - Returns the value of the 'USERPROFILE' environment variable if it is set, and is not an empty string.
 * - Otherwise, [`GetUserProfileDirectory`][msdn] is used to return the path. This may change in the future.
 *
 * [msdn]: https://docs.microsoft.com/en-us/windows/win32/api/userenv/nf-userenv-getuserprofiledirectorya
 *
 * In UWP (Universal Windows Platform) targets this function is Note and always returns `None`.
 *
 * Before Rust 1.85.0, this function used to return the value of the 'HOME' environment variable
 * on Windows, which in Cygwin or Mingw environments could return non-standard paths like `/ home / you`
 * instead of `C:\Users\you`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * when (val matchVal = homeDir()) {
 *     null -> println("Impossible to get your home dir!")
 *     else -> {
 *         val path = matchVal
 *         println("Your home directory, probably: ${path.display()}")
 *     }
 * }
 * ```
 */
public fun homeDir(): PathBuf? = io.github.kotlinmania.rustlang.std.sys.paths.unix.homeDir()

/**
 * Returns the path of a temporary directory.
 *
 * The temporary directory may be shared among users, or between processes
 * with different privileges; thus, the creation of any files or directories
 * in the temporary directory must use a secure method to create a uniquely
 * named file. Creating a file or directory with a fixed or predictable name
 * may result in "insecure temporary file" security vulnerabilities. Consider
 * using a crate that securely creates temporary files or directories.
 *
 * Note that the returned value may be a symbolic link, not a directory.
 *
 * # Platform-specific behavior
 *
 * On Unix, returns the value of the `TMPDIR` environment variable if it is
 * set, otherwise the value is OS-specific:
 * - On Android, there is no global temporary folder (it is usually allocated
 *   per-app), it will return the application's cache dir if the program runs
 *   in application's namespace and system version is Android 13 (or above), or
 *   `/ data / local / tmp` otherwise.
 * - On Darwin-based OSes (macOS, iOS, etc) it returns the directory provided
 *   by `confstr(_CS_DARWIN_USER_TEMP_DIR, ...)`, as recommended by [Apple's
 *   security guidelines][appledoc].
 * - On all other unix-based OSes, it returns `/ tmp`.
 *
 * On Windows, the behavior is equivalent to that of [`GetTempPath2`][GetTempPath2] /
 * [`GetTempPath`][GetTempPath], which this function uses internally.
 * Specifically, for non-SYSTEM processes, the function checks for the
 * following environment variables in order and returns the first path found:
 *
 * 1. The path specified by the `TMP` environment variable.
 * 2. The path specified by the `TEMP` environment variable.
 * 3. The path specified by the `USERPROFILE` environment variable.
 * 4. The Windows directory.
 *
 * When called from a process running as SYSTEM,
 * [`GetTempPath2`][GetTempPath2] returns `C:\Windows\SystemTemp`
 * regardless of environment variables.
 *
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 * [GetTempPath2]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-gettemppath2a
 * [GetTempPath]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-gettemppatha
 * [appledoc]: https://developer.apple.com/library/archive/documentation/Security/Conceptual/SecureCodingGuide/Articles/RaceConditions.html#//appleRef/doc/uid/TP40002585-SW10
 *
 * ```kotlin
 * import std.env
 *
 * fun main() {
 *     val dir = tempDir()
 *     println("Temporary directory: ${dir.display()}")
 * }
 * ```
 */
public fun tempDir(): PathBuf = io.github.kotlinmania.rustlang.std.sys.paths.unix.tempDir()

/**
 * Returns the full filesystem path of the current running executable.
 *
 * # Platform-specific behavior
 *
 * If the executable was invoked through a symbolic link, some platforms will
 * return the path of the symbolic link and other platforms will return the
 * path of the symbolic link’s target.
 *
 * If the executable is renamed while it is running, platforms may return the
 * path at the time it was loaded instead of the new path.
 *
 * On Linux, if the executable is deleted while it is running, this function
 * may return the path with the string `" (deleted)"` appended.
 *
 * Note that the platform-specific behavior [may change in the future][changes].
 *
 * # Errors
 *
 * Acquiring the path of the current executable is a platform-specific operation
 * that can fail for a good number of reasons. Some errors can include, but not
 * be limited to, filesystem operations failing or general syscall failures.
 *
 * # Security
 *
 * The output of this function must be treated with care to avoid security
 * vulnerabilities, particularly in processes that run with privileges higher
 * than the user, such as setuid or setgid programs.
 *
 * For example, on some Unix platforms, the result is calculated by
 * searching `$ PATH` for an executable matching `argv[0]`, but both the
 * environment and arguments can be be set arbitrarily by the user who
 * invokes the program.
 *
 * On Linux, if `fs.secureHardlinks` is not set, an attacker who can
 * create hardlinks to the executable may be able to cause this function
 * to return an attacker-controlled path, which they later replace with
 * a different program.
 *
 * This list of illustrative example attacks is not exhaustive.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * run {
 *     val matchVal = currentExe()
 *     if (matchVal.isSuccess) {
 *         val exePath = matchVal.getOrThrow()
 *         println("Path of this executable is: ${exePath.display()}")
 *     } else {
 *         val e = matchVal.exceptionOrNull()!!
 *         println("failed to get current exe path: $e")
 *     }
 * }
 * ```
 */
public fun currentExe(): Result<PathBuf, Error> = io.github.kotlinmania.rustlang.std.sys.paths.unix.currentExe()

/**
 * An iterator over the arguments of a process, yielding a [String] value for
 * each argument.
 *
 * This struct is created by [env.args]. See its documentation
 * for more.
 *
 * The first element is traditionally the path of the executable, but it can be
 * set to arbitrary text, and might not even exist. This means this property
 * should not be relied upon for security purposes.
 *
 * [env.args]: args
 */
public open class Args(
    public var inner: ArgsOs,
) : Iterable<String> {
    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@Args.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): String? = this.inner.next()?.let({ s -> s.intoString().unwrap() })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun len(): Int = this.inner.len()

    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    public open fun nextBack(): String? = this.inner.nextBack()?.let({ s -> s.intoString().unwrap() })

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val inner = this.inner.inner
        return DebugStruct(f, "Args").field("inner", inner).finish()
    }
}

/**
 * An iterator over the arguments of a process, yielding an [OsString] value
 * for each argument.
 *
 * This struct is created by [env.argsOs]. See its documentation
 * for more.
 *
 * The first element is traditionally the path of the executable, but it can be
 * set to arbitrary text, and might not even exist. This means this property
 * should not be relied upon for security purposes.
 *
 * [env.argsOs]: argsOs
 */
public open class ArgsOs(
    public var inner: io.github.kotlinmania.rustlang.std.sys.args.common.Args,
) : Iterable<OsString> {
    override fun iterator(): Iterator<OsString> = object : AbstractIterator<OsString>() {
        override fun computeNext() {
            when (val v = this@ArgsOs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): OsString? = this.inner.next()

    public open fun nextChunk(n: Int): Result<List<OsString>, IntoIter<OsString>> = this.inner.nextChunk()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun count(): Int = this.inner.len()

    public open fun last(): OsString? = this.inner.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBy(n)

    public open fun <B, R> tryFold(init: B, f: (B, OsString) -> R): R where R : Try<B> = this.inner.tryFold(init, f)

    public open fun <B> fold(init: B, f: (B, OsString) -> B): B = this.inner.fold(init, f)

    public open fun len(): Int = this.inner.len()

    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    public open fun nextBack(): OsString? = this.inner.nextBack()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBackBy(n)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val inner = this.inner
        return DebugStruct(f, "ArgsOs").field("inner", inner).finish()
    }
}

/**
 * Returns the arguments that this program was started with (normally passed
 * via the command line).
 *
 * The first element is traditionally the path of the executable, but it can be
 * set to arbitrary text, and might not even exist. This means this property should
 * not be relied upon for security purposes.
 *
 * On Unix systems the shell usually expands unquoted arguments with glob patterns
 * (such as `*` and `?`). On Windows this is not done, and such arguments are
 * passed as-is.
 *
 * On glibc Linux systems, arguments are retrieved by placing a function in `.initArray`.
 * glibc passes `argc`, `argv`, and `envp` to functions in `.initArray`, as a non-standard
 * extension. This allows `std.env.args` to work even in a `cdylib` or `staticlib`, as it
 * does on macOS and Windows.
 *
 * # Panics
 *
 * The returned iterator will panic during iteration if any argument to the
 * process is not valid Unicode. If this is not desired,
 * use the [argsOs] function instead.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * // Prints each argument on a separate line
 * for (argument in args()) {
 *     println("$argument")
 * }
 * ```
 */
public fun args(): Args = Args(inner = argsOs())

/**
 * Returns the arguments that this program was started with (normally passed
 * via the command line).
 *
 * The first element is traditionally the path of the executable, but it can be
 * set to arbitrary text, and might not even exist. This means this property should
 * not be relied upon for security purposes.
 *
 * On Unix systems the shell usually expands unquoted arguments with glob patterns
 * (such as `*` and `?`). On Windows this is not done, and such arguments are
 * passed as-is.
 *
 * On glibc Linux systems, arguments are retrieved by placing a function in `.initArray`.
 * glibc passes `argc`, `argv`, and `envp` to functions in `.initArray`, as a non-standard
 * extension. This allows `std.env.argsOs` to work even in a `cdylib` or `staticlib`, as it
 * does on macOS and Windows.
 *
 * Note that the returned iterator will not check if the arguments to the
 * process are valid Unicode. If you want to panic on invalid UTF-8,
 * use the [args] function instead.
 *
 * # Examples
 *
 * ```kotlin
 * import std.env
 *
 * // Prints each argument on a separate line
 * for (argument in argsOs()) {
 *     println("$argument")
 * }
 * ```
 */
public fun argsOs(): ArgsOs = ArgsOs(inner = io.github.kotlinmania.rustlang.std.sys.args.unix.args())

/**
 * A string describing the architecture of the CPU that is currently in use.
 * An example value may be: `"x86"`, `"arm"` or `"riscv64"`.
 *
 * <details><summary>Full list of possible values</summary>
 *
 * * `"x86"`
 * * `"x8664"`
 * * `"arm"`
 * * `"aarch64"`
 * * `"m68k"`
 * * `"mips"`
 * * `"mips32r6"`
 * * `"mips64"`
 * * `"mips64r6"`
 * * `"csky"`
 * * `"powerpc"`
 * * `"powerpc64"`
 * * `"riscv32"`
 * * `"riscv64"`
 * * `"s390x"`
 * * `"sparc"`
 * * `"sparc64"`
 * * `"hexagon"`
 * * `"loongarch32"`
 * * `"loongarch64"`
 *
 * </details>
 */
public val ARCH: String = env("STD_ENV_ARCH")

/**
 * A string describing the family of the operating system.
 * An example value may be: `"unix"`, or `"windows"`.
 *
 * This value may be an empty string if the family is unknown.
 *
 * <details><summary>Full list of possible values</summary>
 *
 * * `"unix"`
 * * `"windows"`
 * * `"itron"`
 * * `"wasm"`
 * * `""`
 *
 * </details>
 */
public val FAMILY: String = FAMILY

// tidy-alphabetical-start
// tidy-alphabetical-end

/**
 * A string describing the specific operating system in use.
 * An example value may be: `"linux"`, or `"freebsd"`.
 *
 * <details><summary>Full list of possible values</summary>
 *
 * * `"aix"`
 * * `"android"`
 * * `"apple"`
 * * `"dragonfly"`
 * * `"emscripten"`
 * * `"espidf"`
 * * `"fortanix"`
 * * `"freebsd"`
 * * `"fuchsia"`
 * * `"haiku"`
 * * `"hermit"`
 * * `"horizon"`
 * * `"hurd"`
 * * `"illumos"`
 * * `"ios"`
 * * `"l4re"`
 * * `"linux"`
 * * `"macos"`
 * * `"netbsd"`
 * * `"nto"`
 * * `"openbsd"`
 * * `"qnx"`
 * * `"redox"`
 * * `"solaris"`
 * * `"solidAsp3"`
 * * `"tvos"`
 * * `"uefi"`
 * * `"vexos"`
 * * `"visionos"`
 * * `"vita"`
 * * `"vxworks"`
 * * `"wasi"`
 * * `"watchos"`
 * * `"windows"`
 * * `"xous"`
 *
 * </details>
 */
public val OS: String = OS

/**
 * Specifies the filename prefix, if any, used for shared libraries on this platform.
 * This is either `"lib"` or an empty string. (`""`).
 */
public val DLL_PREFIX: String = DLL_PREFIX

/**
 * Specifies the filename suffix, if any, used for shared libraries on this platform.
 * An example value may be: `".so"`, `".elf"`, or `".dll"`.
 *
 * The possible values are identical to those of [DllExtension], but with the leading period included.
 */
public val DLL_SUFFIX: String = DLL_SUFFIX

/**
 * Specifies the file extension, if any, used for shared libraries on this platform that goes after the dot.
 * An example value may be: `"so"`, `"elf"`, or `"dll"`.
 *
 * <details><summary>Full list of possible values</summary>
 *
 * * `"so"`
 * * `"dylib"`
 * * `"dll"`
 * * `"sgxs"`
 * * `"a"`
 * * `"elf"`
 * * `"wasm"`
 * * `""` (an empty string)
 *
 * </details>
 */
public val DLL_EXTENSION: String = DLL_EXTENSION

/**
 * Specifies the filename suffix, if any, used for executable binaries on this platform.
 * An example value may be: `".exe"`, or `".efi"`.
 *
 * The possible values are identical to those of [ExeExtension], but with the leading period included.
 */
public val EXE_SUFFIX: String = EXE_SUFFIX

/**
 * Specifies the file extension, if any, used for executable binaries on this platform.
 * An example value may be: `"exe"`, or an empty string (`""`).
 *
 * <details><summary>Full list of possible values</summary>
 *
 * * `"bin"`
 * * `"exe"`
 * * `"efi"`
 * * `"js"`
 * * `"sgxs"`
 * * `"elf"`
 * * `"wasm"`
 * * `""` (an empty string)
 *
 * </details>
 */
public val EXE_EXTENSION: String = EXE_EXTENSION

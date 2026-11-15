// port-lint: source os/unix/fs/dirs.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.unix.fs.dirs

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.env.SplitPathsRef
import io.github.kotlinmania.rustlang.std.env.joinPaths
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.std.fs.dirs.MediaDirs
import io.github.kotlinmania.rustlang.std.fs.read
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf

public open class ExtraHomeDirs(
    public var runtime: PathBuf?,
    public var configPath: OsString?,
    public var dataPath: OsString?,
) {
    public open fun clone(): ExtraHomeDirs = ExtraHomeDirs(runtime, configPath, dataPath)
}

public open class ExtraMediaDirs(
    public var templates: PathBuf?,
) {
    public open fun clone(): ExtraMediaDirs = ExtraMediaDirs(templates)
}

/**
 * XDG-specific extensions to [`fs.HomeDirs`](HomeDirs).
 *
 * The XDG conventions are defined by the Freedesktop.org project in the
 * [XDG Base Directory Specification][xdg-basedir]. These conventions have
 * been largely adopted by Linux distributions.
 *
 * The XDG conventions are written to be usable on any Unix-like filesystem,
 * thus this extension being provided in `os.unix` rather than `os.linux`.
 * However, while some tooling does use XDG conventions on macOS, note that
 * macOS has its own separate conventions for user directories. Consider
 * carefully what conventions your users will expect your application to
 * follow along with any legacy path compatibility you might need to support.
 *
 * [xdg-basedir]: https://specifications.freedesktop.org/basedir/
 */
public interface HomeDirsExt<T> {
    /**
     * Load the user directory paths according to the
     * [XDG Base Directory Specification][xdg-basedir].
     *
     * Each base directory path is set to the value of its corresponding
     * `XDG_ *` environment variable (if it is set and non-empty), else to
     * the default value defined by the specification.
     *
     * | Field | Environment Variable | Default Value |
     * | ----- | -------------------- | ------------- |
     * | [cacheHome] | `XDG_CACHE_HOME` | `$ HOME /.cache` |
     * | [configHome] | `XDG_CONFIG_HOME` | `$ HOME /.config` |
     * | [dataHome] | `XDG_DATA_HOME` | `$ HOME /.local / share` |
     * | [stateHome] | `XDG_STATE_HOME` | `$ HOME /.local / state` |
     * | [runtimeHome] | `XDG_RUNTIME_DIR` | (see method docs) |
     * | [configDirs] | `XDG_CONFIG_DIRS` | `/ etc / xdg` |
     * | [dataDirs] | `XDG_DATA_DIRS` | `/ usr / local / share /`, `/ usr / share /` |
     *
     * Note that `$ HOME` here means [env.homeDir], which uses
     * `$ HOME` if set and non-empty, but falls back to the system password
     * database if it isn't set.
     *
     * All paths are required to be absolute. If a relative path is configured
     * by the environment, it is ignored and the default value is used instead.
     *
     * `configDirs` and `dataDirs` are a list of delimited paths using the
     * [env.splitPaths] delimiter. If some but not all paths in the list are
     * relative, those relative paths are ignored and the remaining absolute
     * paths are used. If there are no valid absolute paths, the default value
     * is used instead.
     *
     * # Errors
     *
     * Errors if the user's home directory cannot be determined.
     *
     * [xdg-basedir]: https://specifications.freedesktop.org/basedir/
     * [cacheHome]: HomeDirs.cacheHome
     * [configHome]: HomeDirs.configHome
     * [dataHome]: HomeDirs.dataHome
     * [stateHome]: HomeDirs.stateHome
     * [runtimeHome]: HomeDirsExt.runtimeHome
     * [configDirs]: HomeDirsExt.configDirs
     * [dataDirs]: HomeDirsExt.dataDirs
     */
    public fun xdg(): Result<T, Error>

    /**
     * A base directory relative to which user-specific runtime files
     * (such as sockets, named pipes, etc) should be stored.
     *
     * Files in this directory may be subjected to periodic clean-up.
     * Larger files should not be placed here, since it might reside in
     * runtime memory and cannot necessarily be swapped out to disk.
     *
     * This path does not have a default if not set. If it isn't set,
     * applications should fall back to a replacement directory with
     * similar capabilities and print a warning message.
     */
    public fun runtimeHome(): Path?

    /**
     * A preference-ordered list of base directories to search for config
     * files *in addition to* [configHome].
     *
     * The order of directories denotes their importance; the first directory
     * is the most important. Information defined relative to the more
     * important base directory takes precedent. [configHome] is not
     * necessarily present in this list, and is considered more important
     * than any base directory in this list.
     *
     * [configHome]: HomeDirs.configHome
     */
    public fun configDirs(): XdgDirs?

    /**
     * A preference-ordered list of base directories to search for data
     * files *in addition to* [dataHome].
     *
     * The order of directories denotes their importance; the first directory
     * is the most important. Information defined relative to the more
     * important base directory takes precedent. [dataHome] is not
     * necessarily present in this list, and is considered more important
     * than any base directory in this list.
     *
     * [dataHome]: HomeDirs.dataHome
     */
    public fun dataDirs(): XdgDirs?

    /**
     * Set the path for [Self.runtimeHome].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public fun setRuntimeHome(path: PathBuf): T

    /**
     * Set the paths for [Self.configDirs].
     *
     * Takes one or more paths joined appropriately for the `PATH` environment
     * variable, as by [env.joinPaths].
     *
     * # Panics
     *
     * Panics if any of the provided paths are not absolute.
     */
    public fun setConfigDirs(paths: OsString): T

    /**
     * Set the paths for [Self.dataDirs].
     *
     * Takes one or more paths joined appropriately for the `PATH` environment
     * variable, as by [env.joinPaths].
     *
     * # Panics
     *
     * Panics if any of the provided paths are not absolute.
     */
    public fun setDataDirs(paths: OsString): T

    public companion object {
        /**
         * Load the user directory paths according to the
         * [XDG Base Directory Specification][xdg-basedir].
         *
         * Each base directory path is set to the value of its corresponding
         * `XDG_ *` environment variable (if it is set and non-empty), else to
         * the default value defined by the specification.
         *
         * | Field | Environment Variable | Default Value |
         * | ----- | -------------------- | ------------- |
         * | [cacheHome] | `XDG_CACHE_HOME` | `$ HOME /.cache` |
         * | [configHome] | `XDG_CONFIG_HOME` | `$ HOME /.config` |
         * | [dataHome] | `XDG_DATA_HOME` | `$ HOME /.local / share` |
         * | [stateHome] | `XDG_STATE_HOME` | `$ HOME /.local / state` |
         * | [runtimeHome] | `XDG_RUNTIME_DIR` | (see method docs) |
         * | [configDirs] | `XDG_CONFIG_DIRS` | `/ etc / xdg` |
         * | [dataDirs] | `XDG_DATA_DIRS` | `/ usr / local / share /`, `/ usr / share /` |
         *
         * Note that `$ HOME` here means [env.homeDir], which uses
         * `$ HOME` if set and non-empty, but falls back to the system password
         * database if it isn't set.
         *
         * All paths are required to be absolute. If a relative path is configured
         * by the environment, it is ignored and the default value is used instead.
         *
         * `configDirs` and `dataDirs` are a list of delimited paths using the
         * [env.splitPaths] delimiter. If some but not all paths in the list are
         * relative, those relative paths are ignored and the remaining absolute
         * paths are used. If there are no valid absolute paths, the default value
         * is used instead.
         *
         * # Errors
         *
         * Errors if the user's home directory cannot be determined.
         *
         * [xdg-basedir]: https://specifications.freedesktop.org/basedir/
         * [cacheHome]: HomeDirs.cacheHome
         * [configHome]: HomeDirs.configHome
         * [dataHome]: HomeDirs.dataHome
         * [stateHome]: HomeDirs.stateHome
         * [runtimeHome]: HomeDirsExt.runtimeHome
         * [configDirs]: HomeDirsExt.configDirs
         * [dataDirs]: HomeDirsExt.dataDirs
         */
        public fun xdg(): Result<HomeDirs, Error> = homeDirsExtXdgHomeDirs()
    }
}

/**
 * XDG-specific extensions to [`fs.MediaDirs`](MediaDirs).
 *
 * The XDG conventions are defined by the Freedesktop.org project through the
 * [xdg-user-dirs] tool. This configuration is generally present on desktop
 * Linux distributions, although adoption is less widespread than the base
 * directory specification.
 *
 * The XDG conventions are written to be usable on any Unix-like filesystem,
 * thus this extension being provided in `os.unix` rather than `os.linux`.
 * However, while some tooling does use XDG conventions on macOS, note that
 * macOS has its own separate conventions for user directories. Consider
 * carefully what conventions your users will expect your application to
 * follow along with any legacy path compatibility you might need to support.
 *
 * [xdg-user-dirs]: https://www.freedesktop.org/wiki/Software/xdg-user-dirs/
 */
public interface MediaDirsExt<T> {
    /**
     * Load the user directory paths according to the [xdg-user-dirs] tool.
     *
     * This directly reads and parses the `$ XDG_CONFIG_HOME / user - dirs.dirs`
     * file as defined and maintained by the [xdg-user-dirs] tool.
     *
     * # Errors
     *
     * Errors if the user's home directory cannot be determined or if the
     * `$ XDG_CONFIG_HOME / user - dirs.dirs` file cannot be read.
     *
     * # Implementation-specific behavior
     *
     * Only the format maintained by xdg-user-dirs-update is supported. Any
     * configuration that does not match the expected format will result in
     * loading an unspecified path or `None` for that directory. To be more
     * specific:
     *
     * - Any line not in the format of `XDG_ { NAME } _DIR = { path }` where `{ NAME }`
     *   is one of `DESKTOP`, `DOWNLOAD`, `TEMPLATES`, `PUBLICSHARE`,
     *   `DOCUMENTS`, `MUSIC`, `PICTURES`, or `VIDEOS` is ignored.
     * - `{ path }` must be a `"`-quoted shell-escaped path.
     * - `{ path }` may only start with `/` or `$ HOME /`. A home-relative path
     *   is returned relative to [env.homeDir]; shell expansion is not
     *   performed.
     * - A directory set to just `$ HOME` marks it as removed, and results in
     *   a `None` value for that path.
     * - If shell expansion syntax other than a leading `$ HOME` is present,
     *   the produced directory path is unspecified. This is invalid config
     *   according to the xdg-user-dirs tooling.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load paths that we currently
     * ignore, such as path formats that are not canonically supported by
     * xdg-user-dirs but which may occur in manually-edited `user - dirs.dirs`.
     *
     * [xdg-user-dirs]: https://www.freedesktop.org/wiki/Software/xdg-user-dirs/
     */
    public fun xdg(): Result<T, Error>

    /**
     * The OS-privileged user "Templates" directory, often the `Templates`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     */
    public fun templates(): Path?

    /**
     * Set the paths for [Self.templates].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public fun setTemplates(path: PathBuf): T

    public companion object {
        /**
         * Load the user directory paths according to the [xdg-user-dirs] tool.
         *
         * This directly reads and parses the `$ XDG_CONFIG_HOME / user - dirs.dirs`
         * file as defined and maintained by the [xdg-user-dirs] tool.
         *
         * # Errors
         *
         * Errors if the user's home directory cannot be determined or if the
         * `$ XDG_CONFIG_HOME / user - dirs.dirs` file cannot be read.
         *
         * # Implementation-specific behavior
         *
         * Only the format maintained by xdg-user-dirs-update is supported. Any
         * configuration that does not match the expected format will result in
         * loading an unspecified path or `None` for that directory. To be more
         * specific:
         *
         * - Any line not in the format of `XDG_ { NAME } _DIR = { path }` where `{ NAME }`
         *   is one of `DESKTOP`, `DOWNLOAD`, `TEMPLATES`, `PUBLICSHARE`,
         *   `DOCUMENTS`, `MUSIC`, `PICTURES`, or `VIDEOS` is ignored.
         * - `{ path }` must be a `"`-quoted shell-escaped path.
         * - `{ path }` may only start with `/` or `$ HOME /`. A home-relative path
         *   is returned relative to [env.homeDir]; shell expansion is not
         *   performed.
         * - A directory set to just `$ HOME` marks it as removed, and results in
         *   a `None` value for that path.
         * - If shell expansion syntax other than a leading `$ HOME` is present,
         *   the produced directory path is unspecified. This is invalid config
         *   according to the xdg-user-dirs tooling.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load paths that we currently
         * ignore, such as path formats that are not canonically supported by
         * xdg-user-dirs but which may occur in manually-edited `user - dirs.dirs`.
         *
         * [xdg-user-dirs]: https://www.freedesktop.org/wiki/Software/xdg-user-dirs/
         */
        public fun xdg(): Result<MediaDirs, Error> = mediaDirsExtXdgMediaDirs()
    }
}

public open class XdgDirs(
    public var value: SplitPathsRef,
) : Iterable<Path> {
    public var field0: SplitPathsRef
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Path> = object : AbstractIterator<Path>() {
        override fun computeNext() {
            when (val v = this@XdgDirs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Path? = this.value.next()

    public companion object {
        public fun new(paths: OsStr): XdgDirs = XdgDirs(splitPathsRef(paths))
    }
}

public fun homeDirsExtXdgHomeDirs(): Result<HomeDirs, Error> {
    val dirs = HomeDirs.empty()
    val userHome = when (val tryResult = userHome()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    dirs.setCacheHome(dirOrElse({ userHome.join(".cache") }, "XDG_CACHE_HOME"))
    dirs.setConfigHome(dirOrElse({ userHome.join(".config") }, "XDG_CONFIG_HOME"))
    dirs.setDataHome(dirOrElse({ userHome.join(".local/share") }, "XDG_DATA_HOME"))
    dirs.setStateHome(dirOrElse({ userHome.join(".local/state") }, "XDG_STATE_HOME"))
    when (val matchVal = dir("XDG_RUNTIME_DIR")) {
        null -> {}
        else -> {
            val runtime = matchVal
            dirs.setRuntimeHome(runtime)
        }
    }
    dirs.setConfigDirs(dirsOr("/etc/xdg", "XDG_CONFIG_DIRS"))
    dirs.setDataDirs(dirsOr("/usr/local/share/:/usr/share/", "XDG_DATA_DIRS"))
    return Result.Ok(dirs)
}

public fun HomeDirs.runtimeHome(): Path? = this.extra.runtime

public fun HomeDirs.configDirs(): XdgDirs? = this.extra.configPath.map(XdgDirs::new)

public fun HomeDirs.dataDirs(): XdgDirs? = this.extra.dataPath.map(XdgDirs::new)

public fun HomeDirs.setRuntimeHome(path: PathBuf): HomeDirs {
    check(path.isAbsolute()) { "runtime directory path must be absolute" }
    this.extra.runtime = path
    return this
}

public fun HomeDirs.setConfigDirs(paths: OsString): HomeDirs {
    for (path in splitPathsRef(paths)) {
        check(path.isAbsolute()) { "config directory paths must be absolute" }
    }
    this.extra.configPath = paths
    return this
}

public fun HomeDirs.setDataDirs(paths: OsString): HomeDirs {
    for (path in splitPathsRef(paths)) {
        check(path.isAbsolute()) { "data directory paths must be absolute" }
    }
    this.extra.dataPath = paths
    return this
}

public fun mediaDirsExtXdgMediaDirs(): Result<MediaDirs, Error> {
    val userHome = when (val tryResult = userHome()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val configHome = dirOrElse({ userHome.join(".config") }, "XDG_CONFIG_HOME")
    val spec1 =
        run {
            val matchVal = read(configHome.join("user-dirs.dirs"))
            when {
                matchVal is Result.Ok<*, *> -> {
                    val spec = (matchVal as Result.Ok<*, *>).value
                    spec
                }
                matchVal is Result.Err<*, *> && (matchVal as Result.Err<*, *>).error.kind() == ErrorKind.NotFound -> {
                    val e = (matchVal as Result.Err<*, *>).error
                    return Result.Err(constError(ErrorKind.NotFound, "missing `\$XDG_CONFIG_HOME/user-dirs.dirs`"))
                }
                else -> {
                    val e1 = (matchVal as Result.Err<*, *>).error
                    return Result.Err(e1)
                }
            }
        }
    return Result.Ok(parseUserDirs(spec1, userHome))
}

public fun MediaDirs.templates(): Path? = this.extra.templates

public fun MediaDirs.setTemplates(path: PathBuf): MediaDirs {
    check(path.isAbsolute()) { "templates directory path must be absolute" }
    this.extra.templates = path
    return this
}

public fun splitPathsRef(paths: OsStr): SplitPathsRef {
    // returns Some on cfg(unix)
    return env.splitPathsRef(paths).unwrap()
}

public fun userHome(): Result<PathBuf, Error> = env.homeDir().filter({ p -> p.isAbsolute() }).okOr(constError(ErrorKind.InvalidData, "user home directory path not absolute"))

public fun dir(env: String): PathBuf? = varOs(env).filter({ s -> !s.isEmpty() })?.let(PathBuf::from).filter({ path -> path.isAbsolute() })

public fun dirOrElse(fallback: () -> PathBuf, env: String): PathBuf = (dir(env) ?: fallback())

public fun dirs(env: String): OsString? {
    open class DerefAsRef<T>(
        public var value: T,
    ) : AsRef<U> {
        public var field0: T
            get() = value
            set(v) {
                value = v
            }

        public open fun asRef(): U = this.value.asRef()
    }
    val dirs = (varOs(env).filter({ s -> !s.isEmpty() }) ?: return null)
    if (splitPathsRef(dirs).all({ p -> p.isAbsolute() })) {
        return dirs
    }
    val paths = splitPathsRef(dirs).filter({ p -> p.isAbsolute() })?.let({ DerefAsRef(it) })
    return joinPaths(paths).ok().filter({ s -> !s.isEmpty() })
}

public fun dirsOr(fallback: Any, env: String): OsString = (dirs(env) ?: fallback.into())

public fun parseUserDirs(spec: List<UByte>, userHome: Path): MediaDirs {
    val dirs = MediaDirs.empty()
    for ((xdg, path) in spec.split({ b -> b == 10u }).flatMap({ line -> parseUserDirsLine(line, userHome) })) {
        // load the known user directories
        when (xdg) {
            "XDG_DESKTOP_DIR".encodeToByteArray() -> dirs.desktop = path
            "XDG_DOCUMENTS_DIR".encodeToByteArray() -> dirs.documents = path
            "XDG_DOWNLOAD_DIR".encodeToByteArray() -> dirs.downloads = path
            "XDG_MUSIC_DIR".encodeToByteArray() -> dirs.music = path
            "XDG_PICTURES_DIR".encodeToByteArray() -> dirs.pictures = path
            "XDG_VIDEOS_DIR".encodeToByteArray() -> dirs.videos = path
            "XDG_TEMPLATES_DIR".encodeToByteArray() -> dirs.extra.templates = path
            "XDG_PUBLICSHARE_DIR".encodeToByteArray() -> {
                // we don't expose this directory yet as Windows also has a "public" directory
                // and it's not yet clear if we want to expose this at the target-agnostic level
            }
            else -> {
                // ignore unknown variable assignment
            }
        }
    }
    return dirs
}

public fun parseUserDirsLine(line: List<UByte>, userHome: Path): Pair<List<UByte>, PathBuf?>? {
    // trim whitespace
    val trimmed = line.trimAscii()
    // skip empty lines and comments
    if (trimmed.isEmpty() || trimmed.startsWith(ubyteArrayOf(35u))) {
        return null
    }
    // only variable assignment lines are allowed; split on `=`
    val split = trimmed.splitn(2, { b -> b == 61u })
    // extract assignment parts; ignore lines not in this format
    val variable = (split.next() ?: return null)
    val value = (split.next() ?: return null)
    check(split.next() == null)
    // the path value is quoted; unquote it
    val path = (unquote(value, userHome) ?: return null)
    val path1 = path.filter({ path1 -> path1.isAbsolute() }).filter({ path1 -> path1 != userHome })
    return Pair(variable, path1)
}

public fun unquote(bytes: List<UByte>, userHome: Path): PathBuf? {
    val rest = bytes ?: return null
    // setting to the home dir disables the directory configuration;
    // if done symbolically, return None here and bypass later work
    if (when (rest) {
        "\$HOME".encodeToByteArray(), "\$HOME/".encodeToByteArray() -> true
        else -> false
    }) {
        return null
    }
    var rest1 = rest
    val s = ArrayList(rest1.len())
    // expand leading $HOME
    if (rest1.startsWith("\$HOME/".encodeToByteArray())) {
        s.addAll(userHome.asOsStr().encodeToByteArray().map { it.toUByte() })
        if (!userHome.hasTrailingSep()) {
            s.add(47u)
        }
        rest1 = rest1[6 until rest1.size]
    }
    while (true) {
        val i =
            (rest1.iter().indexOfFirst({ b -> when (b) {
                34u, 92u, 36u, 96u -> true
                else -> false
            } }).takeIf { it >= 0 } ?: rest1.len())
        s.addAll(rest1[0 until i])
        run {
            val matchVal = rest1[i until rest1.size]
            when {
                matchVal.isEmpty() -> { break }
                matchVal.size >= 2 && matchVal[0] == 92u && (matchVal[1] == 34u || matchVal[1] == 92u || matchVal[1] == 36u || matchVal[1] == 96u) -> {
                    val c = matchVal[1]
                    val tail = matchVal.subList(2, matchVal.size)
                    // supported escapes
                    s.add(c)
                    rest1 = tail
                }
                matchVal.size >= 1 && (matchVal[0] == 34u || matchVal[0] == 92u || matchVal[0] == 36u || matchVal[0] == 96u) -> {
                    // unsupported shell syntax
                    return null
                }
                else -> {
                    if (false) {
                        error("unreachable")
                    } else {
                        return null
                    }
                }
            }
        }
    }
    return PathBuf.from(OsString.fromVec(s))
}

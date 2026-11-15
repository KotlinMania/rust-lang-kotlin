// port-lint: source os/darwin/fs/dirs.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.darwin.fs.dirs

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.sysdirGetNextSearchPathEnumeration
import io.github.kotlinmania.rustlang.libc.sysdirStartSearchPathEnumeration
import io.github.kotlinmania.rustlang.libc.sysdirsearchpathdirectoryt.*
import io.github.kotlinmania.rustlang.libc.sysdirsearchpathdomainmaskt.SYSDIR_DOMAIN_MASK_USER
import io.github.kotlinmania.rustlang.libc.unix.bsd.PATH_MAX
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SysdirSearchPathDirectoryT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SysdirSearchPathDomainMaskT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SysdirSearchPathEnumerationState
import io.github.kotlinmania.rustlang.std.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.std.fs.dirs.MediaDirs
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.env.common.Env

/**
 * Darwin-specific extensions to [`fs.HomeDirs`](HomeDirs).
 */
public interface HomeDirsExt<T> {
    /**
     * Load the standard user directory paths for the current user.
     *
     * On iOS, tvOS, watchOS, visionOS, and sandboxed macOS applications,
     * these directories are within the application's container. Outside
     * the sandbox, these are subdirectories of the `~ / Library` directory on
     * macOS.
     *
     * The produced directory paths are not guaranteed to be the canonical
     * paths to the directories; they are allowed to be sandbox-redirected
     * paths as long as the directory is accessible there.
     *
     * The loaded common directories are:
     *
     * | `HomeDirs` | [NSSearchPathDirectory] |
     * | ---------- | ----------------------- |
     * | [cacheHome] | [NSCachesDirectory] (`~ / Library / Caches`) |
     * | [configHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
     * | [dataHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
     * | [stateHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
     *
     * Note that the Application Support directory is used for the config,
     * data, and state directories. It is always possible for multiple user
     * directories to be configured to the same path, but this is the common
     * configuration on Apple platforms, making it even more important to not
     * assume files in different user directories cannot alias each other.
     *
     * # Errors
     *
     * Errors if the [user home](env.homeDir) cannot be determined.
     *
     * # Implementation-specific behavior
     *
     * Uses the `sysdir(3)` API from `libSystem` to discover the standard
     * user directories.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load additional common
     * directories not currently in this list.
     *
     * [cacheHome]: HomeDirs.cacheHome
     * [configHome]: HomeDirs.configHome
     * [dataHome]: HomeDirs.dataHome
     * [stateHome]: HomeDirs.stateHome
     *
     * [NSSearchPathDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory?language=objc
     * [NSCachesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/cachesdirectory?language=objc
     * [NSApplicationSupportDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/applicationsupportdirectory?language=objc
     */
    public fun sysdir(): Result<T, Error>

    public companion object {
        /**
         * Load the standard user directory paths for the current user.
         *
         * On iOS, tvOS, watchOS, visionOS, and sandboxed macOS applications,
         * these directories are within the application's container. Outside
         * the sandbox, these are subdirectories of the `~ / Library` directory on
         * macOS.
         *
         * The produced directory paths are not guaranteed to be the canonical
         * paths to the directories; they are allowed to be sandbox-redirected
         * paths as long as the directory is accessible there.
         *
         * The loaded common directories are:
         *
         * | `HomeDirs` | [NSSearchPathDirectory] |
         * | ---------- | ----------------------- |
         * | [cacheHome] | [NSCachesDirectory] (`~ / Library / Caches`) |
         * | [configHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
         * | [dataHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
         * | [stateHome] | [NSApplicationSupportDirectory] (`~ / Library / Application Support`) |
         *
         * Note that the Application Support directory is used for the config,
         * data, and state directories. It is always possible for multiple user
         * directories to be configured to the same path, but this is the common
         * configuration on Apple platforms, making it even more important to not
         * assume files in different user directories cannot alias each other.
         *
         * # Errors
         *
         * Errors if the [user home](env.homeDir) cannot be determined.
         *
         * # Implementation-specific behavior
         *
         * Uses the `sysdir(3)` API from `libSystem` to discover the standard
         * user directories.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load additional common
         * directories not currently in this list.
         *
         * [cacheHome]: HomeDirs.cacheHome
         * [configHome]: HomeDirs.configHome
         * [dataHome]: HomeDirs.dataHome
         * [stateHome]: HomeDirs.stateHome
         *
         * [NSSearchPathDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory?language=objc
         * [NSCachesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/cachesdirectory?language=objc
         * [NSApplicationSupportDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/applicationsupportdirectory?language=objc
         */
        public fun sysdir(): Result<HomeDirs, Error> = homeDirsExtSysdirHomeDirs()
    }
}

/**
 * Darwin-specific extensions to [`fs.MediaDirs`](MediaDirs).
 */
public interface MediaDirsExt<T> {
    /**
     * Load the standard user directory paths for the current user.
     *
     * The produced directory paths are not guaranteed to be the canonical
     * paths to the directories; they are allowed to be sandbox-redirected
     * paths as long as the directory is accessible there.
     *
     * The loaded common directories are:
     *
     * | `MediaDirs` | [NSSearchPathDirectory] |
     * | ---------- | ----------------------- |
     * | [desktop] | [NSDesktopDirectory] (`~ / Desktop`) |
     * | [documents] | [NSDocumentDirectory] (`~ / Documents`) |
     * | [downloads] | [NSDownloadsDirectory] (`~ / Downloads`) |
     * | [music] | [NSMusicDirectory] (`~ / Music`) |
     * | [pictures] | [NSPicturesDirectory] (`~ / Pictures`) |
     * | [videos] | [NSMoviesDirectory] (`~ / Movies`) |
     *
     * # Errors
     *
     * Errors if the the [user home](env.homeDir) cannot be determined.
     *
     * # Implementation-specific behavior
     *
     * Uses the `sysdir(3)` API from `libSystem` to discover the standard
     * user directories.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load additional common
     * directories not currently in this list.
     *
     * [desktop]: MediaDirs.desktop
     * [documents]: MediaDirs.documents
     * [downloads]: MediaDirs.downloads
     * [music]: MediaDirs.music
     * [pictures]: MediaDirs.pictures
     * [videos]: MediaDirs.videos
     *
     * [NSSearchPathDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory?language=objc
     * [NSDesktopDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/desktopdirectory?language=objc
     * [NSDocumentDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/documentdirectory?language=objc
     * [NSDownloadsDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/downloadsdirectory?language=objc
     * [NSMusicDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/musicdirectory?language=objc
     * [NSPicturesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/picturesdirectory?language=objc
     * [NSMoviesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/moviesdirectory?language=objc
     */
    public fun sysdir(): Result<T, Error>

    public companion object {
        /**
         * Load the standard user directory paths for the current user.
         *
         * The produced directory paths are not guaranteed to be the canonical
         * paths to the directories; they are allowed to be sandbox-redirected
         * paths as long as the directory is accessible there.
         *
         * The loaded common directories are:
         *
         * | `MediaDirs` | [NSSearchPathDirectory] |
         * | ---------- | ----------------------- |
         * | [desktop] | [NSDesktopDirectory] (`~ / Desktop`) |
         * | [documents] | [NSDocumentDirectory] (`~ / Documents`) |
         * | [downloads] | [NSDownloadsDirectory] (`~ / Downloads`) |
         * | [music] | [NSMusicDirectory] (`~ / Music`) |
         * | [pictures] | [NSPicturesDirectory] (`~ / Pictures`) |
         * | [videos] | [NSMoviesDirectory] (`~ / Movies`) |
         *
         * # Errors
         *
         * Errors if the the [user home](env.homeDir) cannot be determined.
         *
         * # Implementation-specific behavior
         *
         * Uses the `sysdir(3)` API from `libSystem` to discover the standard
         * user directories.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load additional common
         * directories not currently in this list.
         *
         * [desktop]: MediaDirs.desktop
         * [documents]: MediaDirs.documents
         * [downloads]: MediaDirs.downloads
         * [music]: MediaDirs.music
         * [pictures]: MediaDirs.pictures
         * [videos]: MediaDirs.videos
         *
         * [NSSearchPathDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory?language=objc
         * [NSDesktopDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/desktopdirectory?language=objc
         * [NSDocumentDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/documentdirectory?language=objc
         * [NSDownloadsDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/downloadsdirectory?language=objc
         * [NSMusicDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/musicdirectory?language=objc
         * [NSPicturesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/picturesdirectory?language=objc
         * [NSMoviesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/moviesdirectory?language=objc
         */
        public fun sysdir(): Result<MediaDirs, Error> = mediaDirsExtSysdirMediaDirs()
    }
}

public fun userHome(): Result<PathBuf, Error> = Env.homeDir().filter({ p -> p.isAbsolute() }).okOr(constError(ErrorKind.InvalidData, "home path not absolute"))

public fun homeDirsExtSysdirHomeDirs(): Result<HomeDirs, Error> {
    val dirs = HomeDirs.empty()
    val home = when (val tryResult = userHome()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val caches = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_CACHES)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val applicationSupport = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_APPLICATION_SUPPORT)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    dirs.cache = caches
    // Apple puts config/data/state all in Application Support
    dirs.config = applicationSupport.clone()
    dirs.data = applicationSupport.clone()
    dirs.state = applicationSupport
    return Result.Ok(dirs)
}

public fun mediaDirsExtSysdirMediaDirs(): Result<MediaDirs, Error> {
    val dirs = MediaDirs.empty()
    val home = when (val tryResult = userHome()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val desktop = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_DESKTOP)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val documents = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_DOCUMENT)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val downloads = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_DOWNLOADS)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val movies = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_MOVIES)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val music = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_MUSIC)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val pictures = when (val tryResult = getUserDir(home, SYSDIR_DIRECTORY_PICTURES)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    dirs.desktop = desktop
    dirs.documents = documents
    dirs.downloads = downloads
    dirs.music = music
    dirs.pictures = pictures
    dirs.videos = movies
    return Result.Ok(dirs)
}

/**
 * Get the path for a system directory using `sysdir(3)`.
 */
public fun getUserDir(home: Path, kind: SysdirSearchPathDirectoryT): Result<PathBuf?, Error> {
    // SAFETY: SYSDIR_DOMAIN_MASK_USER < SYSDIR_DOMAIN_MASK_ALL
    val iter = Iter.new(home, kind, SYSDIR_DOMAIN_MASK_USER)
    val path = iter.next() ?: return Result.Ok(null)
    val path1 = when (val tryResult = path) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    if (iter.next() != null) {
        // more than one path returned (shouldn't happen for SYSDIR_DOMAIN_MASK_USER)
        return Result.Err(constError(ErrorKind.InvalidData, "multiple paths returned for standard user directory"))
    }
    return Result.Ok(path1)
}

public open class Iter(
    public var home: Path,
    public var state: SysdirSearchPathEnumerationState,
) : Iterable<Result<PathBuf, Error>> {
    override fun iterator(): Iterator<Result<PathBuf, Error>> = object : AbstractIterator<Result<PathBuf, Error>>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun drop() {
        for (unused in this) {}
    }

    public open fun next(): Result<PathBuf, Error>? {
        val localStorageL235C12 = AddressableArrayLocal.fromUByteArray(UByteArray(PATH_MAX.toInt()) { 0u.toUByte() })
        val buf by localStorageL235C12
        if (this.state != 0) {
            // SAFETY: `self.state` is nonzero and comes from prior sysdir_{start|getNext}_search_path_enumeration call
            // SAFETY: sysdirGetNextSearchPathEnumeration will write at most `PATH_MAX` bytes to `path`
            this.state = sysdirGetNextSearchPathEnumeration(this.state, (localStorageL235C12.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
        }
        if (this.state == 0) {
            // exhausted
            return null
        }
        val path = CStr.fromBytesUntilNul(buf) ?: // should be impossible given home-relative paths, but be defensive
return Result.Err(constError(ErrorKind.InvalidData, "standard user directory path too long"))
        val path1 = path.toStr() ?: // should be impossible on a working system, but be defensive
return Result.Err(constError(ErrorKind.InvalidData, "standard user directory path not valid UTF-8"))
        // expand `~` shorthand
        return when {
            path1 == "~" -> Result.Ok(this.home.into())
            path1.startsWith("~/") -> Result.Ok(this.home.join(path1[2 until path1.size]))
            path1.startsWith("~") -> Result.Err(constError(ErrorKind.InvalidData, "standard user directory relative to different user"))
            else -> {
                val path2 = PathBuf.from(path1)
                if (path2.isRelative()) {
                    Result.Err(constError(ErrorKind.InvalidData, "standard user directory path not absolute"))
                } else {
                    Result.Ok(path2)
                }
            }
        }
    }

    public companion object {
        // SAFETY: `mask` must be <= `SYSDIR_DOMAIN_MASK_ALL`
        public fun new(home: Path, kind: SysdirSearchPathDirectoryT, mask: SysdirSearchPathDomainMaskT): Iter {
            // SAFETY: forwarded to the caller
            val state = sysdirStartSearchPathEnumeration(kind, mask)
            return Iter(home = home, state = state)
        }
    }
}

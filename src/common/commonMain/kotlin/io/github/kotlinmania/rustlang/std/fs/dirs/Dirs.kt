// port-lint: source fs/dirs.rs
package io.github.kotlinmania.rustlang.std.fs.dirs

import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.common.ExtraHomeDirs
import io.github.kotlinmania.rustlang.std.sys.fs.common.ExtraMediaDirs

/**
 * Common user directory paths used for user-specific application files.
 *
 * It is not required that the user directories are accessible by the current
 * user, nor that there is a directory at that path. A robust application
 * should handle the case where user directories are incorrectly configured.
 *
 * Even when configured correctly, multiple paths may point to the same location.
 * You should not assume that a file written relative to one directory will not
 * conflict with the same relative path in a different home directory.
 *
 * # Platform-specific behavior
 *
 * As the filesystem conventions for discovering directories varies between
 * operating systems, constructors for `HomeDirs` that use the host platform's
 * conventions are provided as extension traits under the `std.os` module.
 */
public open class HomeDirs(
    public var cache: PathBuf?,
    public var config: PathBuf?,
    public var data: PathBuf?,
    public var state: PathBuf?,
    public var extra: ExtraHomeDirs,
) {
    public open fun clone(): HomeDirs = HomeDirs(cache, config, data, state, extra)

    // NB: HomeDirs and MediaDirs intentionally do not implement Default. Self.empty()
    // is a logical default, but users may also intuit the default to use default
    // platform conventions. Omitting Default pushes users to explicitly choose.

    /**
     * A base directory relative to which user-specific non-essential cache
     * data files should be stored.
     *
     * "Cache" files are temporary data that can be used to cache redundant
     * work of an application, but which can be discarded arbitrarily and
     * recreated as necessary. Files in this directory may potentially be
     * automatically purged any time they are not currently open, or they
     * may not, depending on system configuration. A robust application
     * should ensure that its caches do not grow without a reasonable bound.
     *
     * This is the same directory for all applications. Applications should
     * use a subdirectory for application-specific cache files.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `$ { XDG_CACHE_HOME: - $ HOME /.cache }` |
     * | [Darwin] (macOS) | [NSCachesDirectory] (`$ HOME / Library / Caches`) |
     * | [Windows] | [{FolderidLocalAppData}] (`% LOCALAPPDATA %`) |
     *
     * Other paths can be configured via [`setCacheHome`](Self.setCacheHome).
     *
     * [NSCachesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/cachesdirectory?language=objc
     * [{FolderidLocalAppData}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidLocalappdata
     */
    public open fun cacheHome(): Path? = this.cache

    /**
     * A base directory relative to which user-specific configuration files
     * should be stored.
     *
     * "Config" files are configuration managed by the user, either by editing
     * the files directly or through a managing application. Configuration is
     * generally expected to be meaningful to the user and portable enough to
     * back up and synchronize across the same user's account on multiple
     * systems.
     *
     * This is the same directory for all applications. Applications should
     * use a subdirectory for application-specific configuration files.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `$ { XDG_CONFIG_HOME: - $ HOME /.config }` |
     * | [Darwin] (macOS) | [NSApplicationSupportDirectory] (`$ HOME / Library / Application Support`) |
     * | [Windows] | [{FolderidRoamingAppData}] (`% APPDATA %`) |
     *
     * Other paths can be configured via [`setConfigHome`](Self.setConfigHome).
     *
     * [NSApplicationSupportDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/applicationsupportdirectory?language=objc
     * [{FolderidRoamingAppData}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidRoamingappdata
     */
    public open fun configHome(): Path? = this.config

    /**
     * A base directory relative to which user-specific data files should be
     * stored.
     *
     * "Data" files are application-specific data that is meaningful to the
     * user in some way and does not implicitly rely on system configuration
     * or details of how the application is installed otherwise irrelevant to
     * the user. As such, data makes sense to back up and synchronize between
     * the same user's account on multiple systems. If a file is specific to
     * a single machine, it's probably [state](Self.stateHome).
     *
     * This is the same directory for all applications. Applications should
     * use a subdirectory for application-specific data files.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `$ { XDG_DATA_HOME: - $ HOME /.local / share }` |
     * | [Darwin] (macOS) | [NSApplicationSupportDirectory] (`$ HOME / Library / Application Support`) |
     * | [Windows] | [{FolderidRoamingAppData}] (`% APPDATA %`) |
     *
     * Other paths can be configured via [`setDataHome`](Self.setDataHome).
     *
     * [NSApplicationSupportDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/applicationsupportdirectory?language=objc
     * [{FolderidRoamingAppData}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidRoamingappdata
     */
    public open fun dataHome(): Path? = this.data

    /**
     * A base directory relative to which user-specific state files should be
     * stored.
     *
     * "State" files are data that should persist between application restarts,
     * but which is not important nor portable enough to the user to synchronize
     * between multiple systems like [data](Self.dataHome) are. Common examples
     * include history (such as logs, recently used files, etc) and any current
     * state of the application that should be reused (such as view, layout, open
     * files, undo history, etc).
     *
     * This is the same directory for all applications. Applications should
     * use a subdirectory for application-specific state files.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `$ { XDG_STATE_HOME: - $ HOME /.local / state }` |
     * | [Darwin] (macOS) | [NSApplicationSupportDirectory] (`$ HOME / Library / Application Support`) |
     * | [Windows] | [{FolderidLocalAppData}] (`% LOCALAPPDATA %`) |
     *
     * Other paths can be configured via [`setStateHome`](Self.setStateHome).
     *
     * [NSApplicationSupportDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/applicationsupportdirectory?language=objc
     * [{FolderidLocalAppData}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidLocalappdata
     */
    public open fun stateHome(): Path? = this.state

    /**
     * Set the path for [Self.cacheHome].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setCacheHome(path: PathBuf): HomeDirs {
        check(path.isAbsolute()) { "cache home directory path must be absolute" }
        this.cache = path
        return this
    }

    /**
     * Set the path for [Self.configHome].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setConfigHome(path: PathBuf): HomeDirs {
        check(path.isAbsolute()) { "config home directory path must be absolute" }
        this.config = path
        return this
    }

    /**
     * Set the path for [Self.dataHome].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setDataHome(path: PathBuf): HomeDirs {
        check(path.isAbsolute()) { "data home directory path must be absolute" }
        this.data = path
        return this
    }

    /**
     * Set the path for [Self.stateHome].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setStateHome(path: PathBuf): HomeDirs {
        check(path.isAbsolute()) { "state home directory path must be absolute" }
        this.state = path
        return this
    }

    public companion object {
        /**
         * Create a known user directory set with no known directories.
         *
         * This is useful with the builder `set_ *` methods to create a `HomeDirs`
         * with exactly the directories you want, without any other defaults.
         */
        public fun empty(): HomeDirs = HomeDirs(cache = null, config = null, data = null, state = null, extra = Default.default())
    }
}

/**
 * Common user directory paths used for user-specific media files.
 *
 * It is not required that the media directories are accessible by the current
 * user, nor that there is a directory at that path. A robust application
 * should handle the case where media directories are incorrectly configured.
 *
 * Even when configured correctly, multiple paths may point to the same location.
 * You should not assume that a file written relative to one directory will not
 * conflict with the same relative path in a different media directory.
 *
 * # Platform-specific behavior
 *
 * As the filesystem conventions for discovering directories varies between
 * operating systems, constructors for `MediaDirs` that use the host platform's
 * conventions are provided as extension traits under the `std.os` module.
 */
public open class MediaDirs(
    public var desktop: PathBuf?,
    public var documents: PathBuf?,
    public var downloads: PathBuf?,
    public var music: PathBuf?,
    public var pictures: PathBuf?,
    public var videos: PathBuf?,
    public var extra: ExtraMediaDirs,
) {
    public open fun clone(): MediaDirs = MediaDirs(desktop, documents, downloads, music, pictures, videos, extra)

    /**
     * The OS-recognized user "Desktop" directory, often the `Desktop`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir DESKTOP` (`$ HOME / Desktop`) |
     * | [Darwin] (macOS) | [NSDesktopDirectory] (`$ HOME / Desktop`) |
     * | [Windows] | [{FolderidDesktop}] (`%USERPROFILE%\Desktop`) |
     *
     * Other paths can be configured via [`setDesktop`](Self.setDesktop).
     *
     * [NSDesktopDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/desktopdirectory?language=objc
     * [{FolderidDesktop}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDesktop
     */
    public open fun desktop(): Path? = this.desktop

    /**
     * The OS-recognized user "Documents" directory, often the `Documents`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir DOCUMENTS` (`$ HOME / Documents`) |
     * | [Darwin] (macOS) | [NSDocumentDirectory] (`$ HOME / Documents`) |
     * | [Windows] | [{FolderidDocuments}] (`%USERPROFILE%\Documents`) |
     *
     * Other paths can be configured via [`setDocuments`](Self.setDocuments).
     *
     * [NSDocumentDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/documentdirectory?language=objc
     * [{FolderidDocuments}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDocuments
     */
    public open fun documents(): Path? = this.documents

    /**
     * The OS-recognized user "Downloads" directory, often the `Downloads`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir DOWNLOAD` (`$ HOME / Downloads`) |
     * | [Darwin] (macOS) | [NSDownloadsDirectory] (`$ HOME / Downloads`) |
     * | [Windows] | [{FolderidDownloads}] (`%USERPROFILE%\Downloads`) |
     *
     * Other paths can be configured via [`setDownloads`](Self.setDownloads).
     *
     * [NSDownloadsDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/downloadsdirectory?language=objc
     * [{FolderidDownloads}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDownloads
     */
    public open fun downloads(): Path? = this.downloads

    /**
     * The OS-recognized user "Music" directory, often the `Music`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir MUSIC` (`$ HOME / Music`) |
     * | [Darwin] (macOS) | [NSMusicDirectory] (`$ HOME / Music`) |
     * | [Windows] | [{FolderidMusic}] (`%USERPROFILE%\Music`) |
     *
     * Other paths can be configured via [`setMusic`](Self.setMusic).
     *
     * [NSMusicDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/musicdirectory?language=objc
     * [{FolderidMusic}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidMusic
     */
    public open fun music(): Path? = this.music

    /**
     * The OS-recognized user "Pictures" directory, often the `Pictures`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir PICTURES` (`$ HOME / Pictures`) |
     * | [Darwin] (macOS) | [NSPicturesDirectory] (`$ HOME / Pictures`) |
     * | [Windows] | [{FolderidPictures}] (`%USERPROFILE%\Pictures`) |
     *
     * Other paths can be configured via [`setPictures`](Self.setPictures).
     *
     * [NSPicturesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/picturesdirectory?language=objc
     * [{FolderidPictures}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidPictures
     */
    public open fun pictures(): Path? = this.pictures

    /**
     * The OS-recognized user "Videos" directory, often the `Videos`
     * folder in the user's home directory.
     *
     * As a media directory, this should typically be used as a default path
     * for file selection dialogs, not for automatically accessed file paths.
     *
     * # Platform-specific behavior
     *
     * When constructed using platform-specific conventions, the value is:
     *
     * | OS | Path |
     * | -- | ---- |
     * | [XDG] (Linux) | `xdg - user - dir VIDEOS` (`$ HOME / Videos`) |
     * | [Darwin] (macOS) | [NSMoviesDirectory] (`$ HOME / Movies`) |
     * | [Windows] | [{FolderidVideos}] (`%USERPROFILE%\Videos`) |
     *
     * Other paths can be configured via [`setVideos`](Self.setVideos).
     *
     * [NSMoviesDirectory]: https://developer.apple.com/documentation/foundation/filemanager/searchpathdirectory/moviesdirectory?language=objc
     * [{FolderidVideos}]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidVideos
     */
    public open fun videos(): Path? = this.videos

    /**
     * Set the path for [Self.desktop].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setDesktop(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "desktop directory path must be absolute" }
        this.desktop = path
        return this
    }

    /**
     * Set the path for [Self.documents].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setDocuments(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "documents directory path must be absolute" }
        this.documents = path
        return this
    }

    /**
     * Set the path for [Self.downloads].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setDownloads(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "downloads directory path must be absolute" }
        this.downloads = path
        return this
    }

    /**
     * Set the path for [Self.music].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setMusic(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "music directory path must be absolute" }
        this.music = path
        return this
    }

    /**
     * Set the path for [Self.pictures].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setPictures(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "pictures directory path must be absolute" }
        this.pictures = path
        return this
    }

    /**
     * Set the path for [Self.videos].
     *
     * # Panics
     *
     * Panics if the provided path is not absolute.
     */
    public open fun setVideos(path: PathBuf): MediaDirs {
        check(path.isAbsolute()) { "videos directory path must be absolute" }
        this.videos = path
        return this
    }

    public companion object {
        /**
         * Create a known user directory set with no known directories.
         *
         * This is useful with the builder `set_ *` methods to create a `MediaDirs`
         * with exactly the directories you want, without any other defaults.
         */
        public fun empty(): MediaDirs = MediaDirs(desktop = null, documents = null, downloads = null, music = null, pictures = null, videos = null, extra = Default.default())
    }
}

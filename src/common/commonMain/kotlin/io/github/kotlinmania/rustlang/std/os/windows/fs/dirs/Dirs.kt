// port-lint: source os/windows/fs/dirs.rs
package io.github.kotlinmania.rustlang.std.os.windows.fs.dirs

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.std.fs.dirs.MediaDirs
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.c
import io.github.kotlinmania.rustlang.std.sys.env.windows.Env
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.windows.os2path

/**
 * Windows-specific extensions to [`fs.HomeDirs`](HomeDirs).
 */
public interface HomeDirsExt<T> {
    /**
     * Load the known user folder paths from environment variables.
     *
     * The loaded known folders are:
     *
     * | `HomeDirs` | Environment Variable |
     * | ---------- | -------------------- |
     * | [cacheHome] | `% LOCALAPPDATA %` (`%USERPROFILE%\AppData\Local`) |
     * | [configHome] | `% APPDATA %` (`%USERPROFILE%\AppData\Roaming`) |
     * | [dataHome] | `% APPDATA %` (`%USERPROFILE%\AppData\Roaming`) |
     * | [stateHome] | `% LOCALAPPDATA %` (`%USERPROFILE%\AppData\Local`) |
     *
     * Note that caches/state are both put in `AppData\Local`, and config/data
     * in `AppData\Roaming`. It is always possible for multiple user directories
     * to be configured to the same path, but this is the common configuration
     * on Windows platforms, making it even more important to not assume files
     * in different user directories cannot alias each other.
     *
     * # Errors
     *
     * Errors if `% APPDATA %` or `% LOCALAPPDATA %` are not set to absolute paths.
     *
     * # Implementation-specific behavior
     *
     * Windows keeps these environment variables updated to contain the paths
     * to the configured folder path, but it is possible for the environment
     * variables to not match the underlying system, such as when the user or
     * a program modifies the environment directly, or if the configuration
     * changed after the environment block was copied from the system.
     *
     * Unlike [`knownFolders`](Self.knownFolders), this does not require
     * `Shell32.dll` and thus does not require the overhead of linking in
     * DLLs that may result in Windows considering the application as a
     * graphical application.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load additional common
     * directories not currently in this list.
     *
     * [cacheHome]: HomeDirs.cacheHome
     * [configHome]: HomeDirs.configHome
     * [dataHome]: HomeDirs.dataHome
     * [stateHome]: HomeDirs.stateHome
     */
    public fun appdataEnv(): Result<T, Error>

    /**
     * Load the known user folder paths using the [Known Folders] API.
     *
     * The loaded known folders are:
     *
     * | `HomeDirs` | [Knownfolderid] |
     * | ---------- | ----------------- |
     * | [cacheHome] | [FolderidLocalAppData] (`% LOCALAPPDATA %`) |
     * | [configHome] | [FolderidRoamingAppData] (`% APPDATA %`) |
     * | [dataHome] | [FolderidRoamingAppData] (`% APPDATA %`) |
     * | [stateHome] | [FolderidLocalAppData] (`% LOCALAPPDATA %`) |
     *
     * Note that caches/state are both put in LocalAppData, and config/data
     * in RoamingAppData. It is always possible for multiple user directories
     * to be configured to the same path, but this is the common configuration
     * on Windows platforms, making it even more important to not assume files
     * in different user directories cannot alias each other.
     *
     * # Errors
     *
     * Errors if the underlying system discovery API returns an error. The
     * lack of a configured path is not considered an error and results in a
     * `None` value.
     *
     * # Implementation-specific behavior
     *
     * Calls [SHGetKnownFolderPath] for the current user once for each known
     * folder. Does not create the folder if missing.
     *
     * COM should be initialized on the thread that calls this function or
     * else it may return unexpected errors.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load additional common
     * directories not currently in this list.
     *
     * [Known Folders]: https://learn.microsoft.com/en-us/windows/win32/shell/known-folders
     * [SHGetKnownFolderPath]: https://learn.microsoft.com/en-us/windows/win32/api/shlobjCore/nf-shlobjCore-shgetknownfolderpath
     * [Knownfolderid]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid
     * [FolderidLocalAppData]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidLocalappdata
     * [FolderidRoamingAppData]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidRoamingappdata
     * [cacheHome]: HomeDirs.cacheHome
     * [configHome]: HomeDirs.configHome
     * [dataHome]: HomeDirs.dataHome
     * [stateHome]: HomeDirs.stateHome
     */
    public fun knownFolders(): Result<T, Error>

    public companion object {
        /**
         * Load the known user folder paths from environment variables.
         *
         * The loaded known folders are:
         *
         * | `HomeDirs` | Environment Variable |
         * | ---------- | -------------------- |
         * | [cacheHome] | `% LOCALAPPDATA %` (`%USERPROFILE%\AppData\Local`) |
         * | [configHome] | `% APPDATA %` (`%USERPROFILE%\AppData\Roaming`) |
         * | [dataHome] | `% APPDATA %` (`%USERPROFILE%\AppData\Roaming`) |
         * | [stateHome] | `% LOCALAPPDATA %` (`%USERPROFILE%\AppData\Local`) |
         *
         * Note that caches/state are both put in `AppData\Local`, and config/data
         * in `AppData\Roaming`. It is always possible for multiple user directories
         * to be configured to the same path, but this is the common configuration
         * on Windows platforms, making it even more important to not assume files
         * in different user directories cannot alias each other.
         *
         * # Errors
         *
         * Errors if `% APPDATA %` or `% LOCALAPPDATA %` are not set to absolute paths.
         *
         * # Implementation-specific behavior
         *
         * Windows keeps these environment variables updated to contain the paths
         * to the configured folder path, but it is possible for the environment
         * variables to not match the underlying system, such as when the user or
         * a program modifies the environment directly, or if the configuration
         * changed after the environment block was copied from the system.
         *
         * Unlike [`knownFolders`](Self.knownFolders), this does not require
         * `Shell32.dll` and thus does not require the overhead of linking in
         * DLLs that may result in Windows considering the application as a
         * graphical application.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load additional common
         * directories not currently in this list.
         *
         * [cacheHome]: HomeDirs.cacheHome
         * [configHome]: HomeDirs.configHome
         * [dataHome]: HomeDirs.dataHome
         * [stateHome]: HomeDirs.stateHome
         */
        public fun appdataEnv(): Result<HomeDirs, Error> = homeDirsExtAppdataEnvHomeDirs()
        /**
         * Load the known user folder paths using the [Known Folders] API.
         *
         * The loaded known folders are:
         *
         * | `HomeDirs` | [Knownfolderid] |
         * | ---------- | ----------------- |
         * | [cacheHome] | [FolderidLocalAppData] (`% LOCALAPPDATA %`) |
         * | [configHome] | [FolderidRoamingAppData] (`% APPDATA %`) |
         * | [dataHome] | [FolderidRoamingAppData] (`% APPDATA %`) |
         * | [stateHome] | [FolderidLocalAppData] (`% LOCALAPPDATA %`) |
         *
         * Note that caches/state are both put in LocalAppData, and config/data
         * in RoamingAppData. It is always possible for multiple user directories
         * to be configured to the same path, but this is the common configuration
         * on Windows platforms, making it even more important to not assume files
         * in different user directories cannot alias each other.
         *
         * # Errors
         *
         * Errors if the underlying system discovery API returns an error. The
         * lack of a configured path is not considered an error and results in a
         * `None` value.
         *
         * # Implementation-specific behavior
         *
         * Calls [SHGetKnownFolderPath] for the current user once for each known
         * folder. Does not create the folder if missing.
         *
         * COM should be initialized on the thread that calls this function or
         * else it may return unexpected errors.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load additional common
         * directories not currently in this list.
         *
         * [Known Folders]: https://learn.microsoft.com/en-us/windows/win32/shell/known-folders
         * [SHGetKnownFolderPath]: https://learn.microsoft.com/en-us/windows/win32/api/shlobjCore/nf-shlobjCore-shgetknownfolderpath
         * [Knownfolderid]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid
         * [FolderidLocalAppData]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidLocalappdata
         * [FolderidRoamingAppData]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidRoamingappdata
         * [cacheHome]: HomeDirs.cacheHome
         * [configHome]: HomeDirs.configHome
         * [dataHome]: HomeDirs.dataHome
         * [stateHome]: HomeDirs.stateHome
         */
        public fun knownFolders(): Result<HomeDirs, Error> = homeDirsExtKnownFoldersHomeDirs()
    }
}

/**
 * Windows-specific extensions to [`fs.MediaDirs`](MediaDirs).
 */
public interface MediaDirsExt<T> {
    /**
     * Load the known user folder paths using the [Known Folders] API.
     *
     * The loaded known folders are:
     *
     * | `MediaDirs` | [Knownfolderid] |
     * | ---------- | ----------------- |
     * | [desktop] | [FolderidDesktop] (`%USERPROFILE%\Desktop`) |
     * | [documents] | [FolderidDocuments] (`%USERPROFILE%\Documents`) |
     * | [downloads] | [FolderidDownloads] (`%USERPROFILE%\Downloads`) |
     * | [music] | [FolderidMusic] (`%USERPROFILE%\Music`) |
     * | [pictures] | [FolderidPictures] (`%USERPROFILE%\Pictures`) |
     * | [videos] | [FolderidVideos] (`%USERPROFILE%\Videos`) |
     *
     * # Errors
     *
     * Errors if the underlying system discovery API returns an error. The
     * lack of a configured path is not considered an error and results in a
     * `None` value.
     *
     * # Implementation-specific behavior
     *
     * Calls [SHGetKnownFolderPath] for the current user once for each known
     * folder. Does not create the folder if missing.
     *
     * COM should be initialized on the thread that calls this function or
     * else it may return unexpected errors.
     *
     * This behavior may change in the future. One example change that we
     * explicitly reserve the right to make is to load additional common
     * directories not currently in this list.
     *
     * [Known Folders]: https://learn.microsoft.com/en-us/windows/win32/shell/known-folders
     * [SHGetKnownFolderPath]: https://learn.microsoft.com/en-us/windows/win32/api/shlobjCore/nf-shlobjCore-shgetknownfolderpath
     * [Knownfolderid]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid
     * [FolderidDesktop]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDesktop
     * [FolderidDocuments]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDocuments
     * [FolderidDownloads]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDownloads
     * [FolderidMusic]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidMusic
     * [FolderidPictures]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidPictures
     * [FolderidVideos]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidVideos
     * [desktop]: MediaDirs.desktop
     * [documents]: MediaDirs.documents
     * [downloads]: MediaDirs.downloads
     * [music]: MediaDirs.music
     * [pictures]: MediaDirs.pictures
     * [videos]: MediaDirs.videos
     */
    public fun knownFolders(): Result<T, Error>

    public companion object {
        /**
         * Load the known user folder paths using the [Known Folders] API.
         *
         * The loaded known folders are:
         *
         * | `MediaDirs` | [Knownfolderid] |
         * | ---------- | ----------------- |
         * | [desktop] | [FolderidDesktop] (`%USERPROFILE%\Desktop`) |
         * | [documents] | [FolderidDocuments] (`%USERPROFILE%\Documents`) |
         * | [downloads] | [FolderidDownloads] (`%USERPROFILE%\Downloads`) |
         * | [music] | [FolderidMusic] (`%USERPROFILE%\Music`) |
         * | [pictures] | [FolderidPictures] (`%USERPROFILE%\Pictures`) |
         * | [videos] | [FolderidVideos] (`%USERPROFILE%\Videos`) |
         *
         * # Errors
         *
         * Errors if the underlying system discovery API returns an error. The
         * lack of a configured path is not considered an error and results in a
         * `None` value.
         *
         * # Implementation-specific behavior
         *
         * Calls [SHGetKnownFolderPath] for the current user once for each known
         * folder. Does not create the folder if missing.
         *
         * COM should be initialized on the thread that calls this function or
         * else it may return unexpected errors.
         *
         * This behavior may change in the future. One example change that we
         * explicitly reserve the right to make is to load additional common
         * directories not currently in this list.
         *
         * [Known Folders]: https://learn.microsoft.com/en-us/windows/win32/shell/known-folders
         * [SHGetKnownFolderPath]: https://learn.microsoft.com/en-us/windows/win32/api/shlobjCore/nf-shlobjCore-shgetknownfolderpath
         * [Knownfolderid]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid
         * [FolderidDesktop]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDesktop
         * [FolderidDocuments]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDocuments
         * [FolderidDownloads]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidDownloads
         * [FolderidMusic]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidMusic
         * [FolderidPictures]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidPictures
         * [FolderidVideos]: https://learn.microsoft.com/en-us/windows/win32/shell/knownfolderid#folderidVideos
         * [desktop]: MediaDirs.desktop
         * [documents]: MediaDirs.documents
         * [downloads]: MediaDirs.downloads
         * [music]: MediaDirs.music
         * [pictures]: MediaDirs.pictures
         * [videos]: MediaDirs.videos
         */
        public fun knownFolders(): Result<MediaDirs, Error> = mediaDirsExtKnownFoldersMediaDirs()
    }
}

public fun homeDirsExtAppdataEnvHomeDirs(): Result<HomeDirs, Error> {
    val roamingAppData = when (val tryResult = Env.varOs("APPDATA").map(PathBuf::from).filter({ p -> p.isAbsolute() }).okOr(constError(ErrorKind.InvalidData, "non-absolute %APPDATA%"))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val localAppData = when (val tryResult = Env.varOs("LOCALAPPDATA").map(PathBuf::from).filter({ p -> p.isAbsolute() }).okOr(constError(ErrorKind.InvalidData, "non-absolute %LOCALAPPDATA%"))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    // AppData/Local -- system-local, doesn't make sense to sync to another
    // AppData/Roaming -- data that makes sense to sync across machines
    val dirs = HomeDirs.empty()
    dirs.cache = localAppData.clone()
    dirs.config = roamingAppData.clone()
    dirs.data = roamingAppData
    dirs.state = localAppData
    return Result.Ok(dirs)
}

public fun homeDirsExtKnownFoldersHomeDirs(): Result<HomeDirs, Error> {
    val localAppData = when (val tryResult = getKnownFolderPath(c.FolderidLocalAppData)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val roamingAppData = when (val tryResult = getKnownFolderPath(c.FolderidRoamingAppData)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    // AppData/Local -- system-local, doesn't make sense to sync to another
    // AppData/Roaming -- data that makes sense to sync across machines
    val dirs = HomeDirs.empty()
    dirs.cache = localAppData.clone()
    dirs.config = roamingAppData.clone()
    dirs.data = roamingAppData
    dirs.state = localAppData
    return Result.Ok(dirs)
}

public fun mediaDirsExtKnownFoldersMediaDirs(): Result<MediaDirs, Error> {
    val desktop = when (val tryResult = getKnownFolderPath(c.FolderidDesktop)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val documents = when (val tryResult = getKnownFolderPath(c.FolderidDocuments)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val downloads = when (val tryResult = getKnownFolderPath(c.FolderidDownloads)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val music = when (val tryResult = getKnownFolderPath(c.FolderidMusic)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val pictures = when (val tryResult = getKnownFolderPath(c.FolderidPictures)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val videos = when (val tryResult = getKnownFolderPath(c.FolderidVideos)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    val dirs = MediaDirs.empty()
    dirs.desktop = desktop
    dirs.documents = documents
    dirs.downloads = downloads
    dirs.music = music
    dirs.pictures = pictures
    dirs.videos = videos
    return Result.Ok(dirs)
}

/**
 * Retrieve a known folder path from the Windows API.
 */
public fun getKnownFolderPath(id: c.Guid): Result<PathBuf?, Error> {
    // Get the known folder path. hToken = NULL requests the current user
    // scope, and we set KF_FLAG_DONT_VERIFY because it's a bit faster and
    // we don't guarantee that the directories at the paths exist.
    val pszPath = MutPtr(0L)
    // SAFETY: rfid/ppszPath are valid pointers, flags are appropriate, and
    // a NULL hToken is supported by SHGetKnownFolderPath.
    val hr = c.SHGetKnownFolderPath(id, ((c.KF_FLAG_DONT_VERIFY as Any?) as Result<PathBuf?, Error>), MutPtr(0L), pszPath)
    val result =
        when (hr) {
            c.sOk -> {
                // SAFETY: pszPath was populated by a successful call to SHGetKnownFolderPath
                // and is valid up to and including its nul terminator
                val len = c.lstrlenW(pszPath)
                // SAFETY: *pszPath is valid up to and including its nul terminator
                Result.Ok(os2path(Slice.fromRawParts(pszPath, len)))
            }
            c.eFail -> {
                // This known folder id exists but does not have a path
                if (false) {
                    error("should not call get_known_folder_path on a virtual folder")
                } else {
                    Result.Err(constError(ErrorKind.InvalidInput, "virtual known folders do not have paths"))
                }
            }
            c.eInvalidarg -> {
                // This known folder id is not present on the system
                Result.Ok(null)
            }
            else -> {
                // Miscellaneous error
                Result.Err(io.github.kotlinmania.rustlang.std.io.Error.fromRawOsError(hr))
            }
        }
    // SAFETY: The caller is responsible for freeing the path returned by
    // SHGetKnownFolderPath by calling CoTaskMemFree, whether it
    // succeeds or not.
    c.CoTaskMemFree(pszPath.cast())
    return result
}

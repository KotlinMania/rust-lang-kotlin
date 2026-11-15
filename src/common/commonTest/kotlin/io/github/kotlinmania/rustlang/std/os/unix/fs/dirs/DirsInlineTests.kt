// port-lint: tests os/unix/fs/dirs.rs
package io.github.kotlinmania.rustlang.std.os.unix.fs.dirs

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.fs.dirs.MediaDirs
import io.github.kotlinmania.rustlang.path.Path

public class DirsInlineTests {
    @kotlin.test.Test
    public fun canFetchXdgBaseDirs() {
        val dirs = HomeDirs.xdg().unwrap()
        check(dirs.cacheHome() != null)
        check(dirs.configHome() != null)
        check(dirs.dataHome() != null)
        check(dirs.stateHome() != null)
        // dirs.runtime() may not exist
        check(dirs.configDirs() != null)
        check(dirs.dataDirs() != null)
    }

    @kotlin.test.Test
    public fun canFetchXdgMediaDirs() {
        val dirs1 =
            run {
                val matchVal = MediaDirs.xdg()
                when {
                    matchVal is Result.Ok<*, *> -> {
                        val dirs = (matchVal as Result.Ok<*, *>).value
                        dirs
                    }
                    matchVal is Result.Err<*, *> && (matchVal as Result.Err<*, *>).error.kind() == ErrorKind.NotFound -> {
                        val e = (matchVal as Result.Err<*, *>).error
                        // xdg-user-dirs not initialized on this system, skip the test
                        return
                    }
                    else -> {
                        val e1 = (matchVal as Result.Err<*, *>).error
                        error("failed to fetch xdg user dirs: $e")
                    }
                }
            }
        // even when user-dirs.dirs is present, directories may be unset,
        // so we can't assert anything about the dir paths here
    }

    @kotlin.test.Test
    public fun testWellUserDirsParsing() {
        val TEST_USERDIRS: String = "\n# This file is written by xdg-user-dirs-update\n# If you want to change or add directories, just edit the line you're\n# interested in. All local changes will be retained on the next run.\n# Format is XDG_xxx_DIR=\"\$HOME/yyy\", where yyy is a shell-escaped\n# homedir-relative path, or XDG_xxx_DIR=\"/yyy\", where /yyy is an\n# absolute path. No other format is supported.\n#\nXDG_DESKTOP_DIR=\"\$HOME/Desktop\"\nXDG_DOWNLOAD_DIR=\"\$HOME/Downloads\"\nXDG_TEMPLATES_DIR=\"/pub/Templates\"\nXDG_PUBLICSHARE_DIR=\"/pub\"\nXDG_DOCUMENTS_DIR=\"\$HOME/Documents\"\nXDG_MUSIC_DIR=\"\$HOME/My \\\"Music\\\"\"\nXDG_PICTURES_DIR=\"\$HOME/Pictures\"\nXDG_VIDEOS_DIR=\"\$HOME\"\n"
        val dirs = parseUserDirs(TEST_USERDIRS.encodeToByteArray().map { it.toUByte() }, Path.new("/home/ferris"))
        check(dirs.desktop() == "/home/ferris/Desktop".asRef())
        check(dirs.downloads() == "/home/ferris/Downloads".asRef())
        check(dirs.templates() == "/pub/Templates".asRef())
        check(dirs.documents() == "/home/ferris/Documents".asRef())
        check(dirs.music() == "/home/ferris/My \"Music\"".asRef())
        check(dirs.pictures() == "/home/ferris/Pictures".asRef())
        check(dirs.videos() == null)
    }

    @kotlin.test.Test
    public fun testIllUserDirsParsing() {
        val TEST_USERDIRS: String = "\n# unquoted\nXDG_DESKTOP_DIR=\$HOME/Desktop\n# mixed quoting\nXDG_DOWNLOAD_DIR=\"\$HOME\"/\"Downloads\"\n# non-HOME env\nXDG_TEMPLATES_DIR=\"/home/\$USER/Templates\"\n# missing PUBLICSHARE\n# command substitution\nXDG_DOCUMENTS_DIR=\"`pwd`/Documents\"\n# non-symbolic homedir disabled\nXDG_MUSIC_DIR=\"/home/ferris\"\n# set multiple times\nXDG_PICTURES_DIR=\"\$HOME/Pictures1\"\nXDG_PICTURES_DIR=\"\$HOME/Pictures2\"\n# relative path\nXDG_VIDEOS_DIR=\"Videos\"\n"
        val dirs = parseUserDirs(TEST_USERDIRS.encodeToByteArray().map { it.toUByte() }, Path.new("/home/ferris"))
        check(dirs.desktop() == null)
        check(dirs.downloads() == null)
        check(dirs.templates() == null)
        check(dirs.documents() == null)
        check(dirs.music() == null)
        check(dirs.pictures() == "/home/ferris/Pictures2".asRef())
        check(dirs.videos() == null)
    }
}

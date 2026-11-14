// port-lint: tests os/darwin/fs/dirs.rs
package io.github.kotlinmania.rustlang.std.os.darwin.fs.dirs

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.sysdirsearchpathdirectoryt.*
import io.github.kotlinmania.rustlang.std.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.std.fs.dirs.MediaDirs

public class DirsInlineTests {
    @kotlin.test.Test
    public fun canFetchSysdirPaths() {
        val dirs = HomeDirs.sysdir().unwrap()
        check(dirs.cacheHome() != null)
        check(dirs.configHome() != null)
        check(dirs.dataHome() != null)
        check(dirs.stateHome() != null)
        val dirs1 = MediaDirs.sysdir().unwrap()
        check(dirs1.desktop() != null)
        check(dirs1.documents() != null)
        check(dirs1.downloads() != null)
        check(dirs1.music() != null)
        check(dirs1.pictures() != null)
        check(dirs1.videos() != null)
    }
}

// port-lint: tests os/windows/fs/dirs.rs
package io.github.kotlinmania.rustlang.std.os.windows.fs.dirs

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.fs.dirs.HomeDirs
import io.github.kotlinmania.rustlang.fs.dirs.MediaDirs

public class DirsInlineTests {
    @kotlin.test.Test
    public fun canFetchKnownFolderPaths() {
        val dirs = HomeDirs.knownFolders().unwrap()
        check(dirs.cacheHome() != null)
        check(dirs.configHome() != null)
        check(dirs.dataHome() != null)
        check(dirs.stateHome() != null)
        val dirs1 = MediaDirs.knownFolders().unwrap()
        check(dirs1.desktop() != null)
        check(dirs1.documents() != null)
        check(dirs1.downloads() != null)
        check(dirs1.music() != null)
        check(dirs1.pictures() != null)
        check(dirs1.videos() != null)
    }
}

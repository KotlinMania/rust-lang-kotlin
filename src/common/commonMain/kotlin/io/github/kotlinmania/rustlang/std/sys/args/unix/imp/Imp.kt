// port-lint: source sys/args/unix.rs
package io.github.kotlinmania.rustlang.std.sys.args.unix.imp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.libc.nsgetArgc
import io.github.kotlinmania.rustlang.libc.nsgetArgv

public fun init(argc: Int, argv: ConstPtr) {
    // No need to initialize anything in here, `libdyld.dylib` has already
    // done the work for us.
}

public fun argcArgv(): Pair<Int, ConstPtr> {
    // SAFETY: The returned pointer points to a static initialized early
    // in the program scope by `libdyld.dylib`, and as such is always
    // valid.
    //
    // NOTE: Similar to `_NSGetEnviron`, there technically isn't anything
    // protecting us against concurrent modifications to this, and there
    // doesn't exist a lock that we can take. Instead, it is generally
    // expected that it's only modified in `main` / before other code
    // runs, so reading this here should be fine.
    val argc = nsgetArgc().read()
    // SAFETY: Same as above.
    val argv = nsgetArgv().read()
    // Cast from `* * cChar` to `* const * const cChar`
    return Pair(argc.toInt(), argv.cast())
}

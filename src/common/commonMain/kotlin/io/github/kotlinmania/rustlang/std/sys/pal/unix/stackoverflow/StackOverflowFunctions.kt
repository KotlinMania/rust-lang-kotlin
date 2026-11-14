package io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow

/**
 * # Safety
 * Must be called only once, on the main thread, during program startup.
 */
public fun init() {
    // miri doesn't model signals nor stack overflows and this code has some
    // synchronization properties that we don't want to expose to user code,
    // hence we disable it on miri.
    if (false) {
        return
    }
    // SAFETY: guaranteed by caller.
    io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.handlernone.init()
}

// port-lint: source sys/sync/mutex/no_threads.rs
package io.github.kotlinmania.rustlang.std.sys.sync.mutex.nothreads

import io.github.kotlinmania.rustlang.core.cell.Cell

public open class Mutex internal constructor(
    lockedCtorArg: Cell<Boolean>,
) {
    // This platform has no threads, so we can use a Cell here.
    public var locked: Cell<Boolean> = lockedCtorArg
        internal set

    // no threads on this platform
    public open fun lock() {
        check(this.locked.replace(true) == false)
    }

    public open fun unlock() {
        this.locked.set(false)
    }

    public open fun tryLock(): Boolean = (!this.locked.replace(true)).toUByte()

    public companion object {
        public operator fun invoke(
            locked: Cell<Boolean>,
        ): Mutex = Mutex(
            lockedCtorArg = locked,
        )

        public fun new(): Mutex = Mutex(locked = Cell.new(false))
    }
}

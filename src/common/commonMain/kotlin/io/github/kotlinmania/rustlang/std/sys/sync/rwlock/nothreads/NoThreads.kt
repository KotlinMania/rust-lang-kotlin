// port-lint: source sys/sync/rwlock/no_threads.rs
package io.github.kotlinmania.rustlang.std.sys.sync.rwlock.nothreads

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.process.abort
import io.github.kotlinmania.rustlang.sys.stdio.unix.panicOutput

public open class RwLock internal constructor(
    modeCtorArg: Cell<Int>,
) {
    // This platform has no threads, so we can use a Cell here.
    public var mode: Cell<Int> = modeCtorArg
        internal set

    // no threads on this platform
    public open fun read() {
        val m = this.mode.get()
        if (m >= 0) {
            this.mode.set((m.checkedAdd(1) ?: error("rwlock overflowed read locks")))
        } else {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("rwlock locked for writing").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("rwlock locked for writing").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }

    public open fun tryRead(): Boolean {
        val m = this.mode.get()
        return if (m >= 0) {
            if (m == Int.MAX_VALUE) {
                return false
            }
            this.mode.set(m + 1)
            true
        } else {
            false
        }
    }

    public open fun write() {
        if (this.mode.get() == 0) {
            this.mode.set(-1)
        } else {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("rwlock locked for reading").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("rwlock locked for reading").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }

    public open fun tryWrite(): Boolean =
        if (this.mode.get() == 0) {
            this.mode.set(-1)
            true
        } else {
            false
        }

    public open fun readUnlock() {
        check(this.mode.replace(this.mode.get() - 1) > 0) { "rwlock has not been locked for reading" }
    }

    public open fun writeUnlock() {
        check(this.mode.replace(0) == -1)
    }

    public open fun downgrade() {
        check(this.mode.replace(1) == -1)
    }

    public companion object {
        public operator fun invoke(
            mode: Cell<Int>,
        ): RwLock = RwLock(
            modeCtorArg = mode,
        )

        public fun new(): RwLock = RwLock(mode = Cell.new(0))
    }
}

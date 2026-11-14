// port-lint: source sys/sync/condvar/no_threads.rs
package io.github.kotlinmania.rustlang.std.sys.sync.condvar.nothreads

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex
import io.github.kotlinmania.rustlang.std.thread.functions.sleep

public open class Condvar {
    public open fun notifyOne() {}

    public open fun notifyAll() {}

    public open fun wait(mutex: Mutex) {
        error("condvar wait not supported")
    }

    public open fun waitTimeout(mutex: Mutex, dur: Duration): Boolean {
        sleep(dur)
        return false
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Condvar> =
            object : TypeLayout<Condvar> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Condvar =
                    Condvar()
                override fun writeNative(addr: Long, value: Condvar) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Condvar =
                    Condvar()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Condvar) {
                }
            }

        public fun new(): Condvar = Condvar()
    }
}

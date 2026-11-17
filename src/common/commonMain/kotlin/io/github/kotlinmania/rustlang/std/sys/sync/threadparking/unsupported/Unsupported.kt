// port-lint: source sys/sync/thread_parking/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.unsupported

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.time.Duration

public open class Parker {
    public open fun park() {}

    public open fun parkTimeout(dur: Duration) {}

    public open fun unpark() {}

    public companion object {
        public fun typeLayout(): TypeLayout<Parker> =
            object : TypeLayout<Parker> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Parker =
                    Parker()
                override fun writeNative(addr: Long, value: Parker) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Parker =
                    Parker()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Parker) {
                }
            }

        public fun newInPlace(parker: MutPtr) {}
    }
}

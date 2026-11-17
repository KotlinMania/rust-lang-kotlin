// port-lint: source sys/pal/wasi/stack_overflow.rs
package io.github.kotlinmania.rustlang.std.sys.pal.wasi.stackoverflow

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

public open class Handler {
    public companion object {
        public fun typeLayout(): TypeLayout<Handler> =
            object : TypeLayout<Handler> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Handler =
                    Handler()
                override fun writeNative(addr: Long, value: Handler) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Handler =
                    Handler()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Handler) {
                }
            }

        public fun new(): Handler = Handler()
    }
}

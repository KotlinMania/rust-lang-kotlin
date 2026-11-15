package io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.handlernone.dropHandler
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.handlernone.makeHandler

public open class Handler internal constructor(
    dataCtorArg: MutPtr,
) {
    public var data: MutPtr = dataCtorArg
        internal set

    public open fun drop() {
        // The stack overflow detection logic, even if disabled, has some
        // synchronization logic that should be disabled under miri.
        if (false) {
            return
        }
        dropHandler(this.data)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Handler> =
            object : TypeLayout<Handler> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Handler =
                    Handler(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Handler) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Handler =
                    Handler(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Handler) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.data)
                }
            }

        public operator fun invoke(
            data: MutPtr,
        ): Handler = Handler(
            dataCtorArg = data,
        )

        public fun new(): Handler {
            // The stack overflow detection logic, even if disabled, has some
            // synchronization logic that should be disabled under miri.
            if (false) {
                return ConstPtr(0L)
            }
            return makeHandler(false)
        }

        public fun nullVal(): Handler = Handler(data = MutPtr(0L))
    }
}

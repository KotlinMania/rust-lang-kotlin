// port-lint: source backtrace/noop.rs
package io.github.kotlinmania.rustlang.backtrace.backtrace.noop

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Empty implementation of unwinding used when no other implementation is
// appropriate.

// SAFETY: This function is safe to call. It is only marked as `unchecked` to
// avoid having to allow `unusedUnsafe` since other implementations are
// unchecked.
public fun trace(cb: (io.github.kotlinmania.rustlang.backtrace.backtrace.Frame) -> Boolean) {}

public open class Frame {
    public open fun ip(): MutPtr = MutPtr(0L)

    public open fun sp(): MutPtr = MutPtr(0L)

    public open fun symbolAddress(): MutPtr = MutPtr(0L)

    public open fun moduleBaseAddress(): MutPtr? = null

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Frame> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Frame> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Frame =
                    Frame()
                override fun writeNative(addr: Long, value: Frame) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Frame =
                    Frame()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Frame) {
                }
            }
    }
}

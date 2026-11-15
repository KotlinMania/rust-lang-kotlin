// port-lint: source sync/nonpoison.rs
package io.github.kotlinmania.rustlang.std.sync.nonpoison

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.sync.poison.Guard

// Non-poisoning synchronous locks.
//
// The difference from the locks in the [poison] module is that the locks in this module will not
// become poisoned when a thread panics while holding a guard.

/**
 * A type alias for the result of a nonblocking locking method.
 */
public typealias TryLockResult<Guard> = Result<Guard, WouldBlock>

/**
 * A lock could not be acquired at this time because the operation would otherwise block.
 */
public open class WouldBlock {
    public open fun fmt(f: StringBuilder) {
        "WouldBlock".fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        "try_lock failed because the operation would block".fmt(f)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<WouldBlock> =
            object : TypeLayout<WouldBlock> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): WouldBlock =
                    WouldBlock()
                override fun writeNative(addr: Long, value: WouldBlock) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): WouldBlock =
                    WouldBlock()
                override fun writeBytes(buf: ByteArray, offset: Int, value: WouldBlock) {
                }
            }
    }
}

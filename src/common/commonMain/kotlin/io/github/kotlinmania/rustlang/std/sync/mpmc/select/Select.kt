// port-lint: source sync/mpmc/select.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.select

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.std.sync.mpmc.array.ArrayToken
import io.github.kotlinmania.rustlang.std.sync.mpmc.list.ListToken
import io.github.kotlinmania.rustlang.std.sync.mpmc.zero.ZeroToken

/**
 * Temporary data that gets initialized during a blocking operation, and is consumed by
 * `read` or `write`.
 *
 * Each field contains data associated with a specific channel flavor.
 */
internal open class Token(
    internal var array: ArrayToken,
    internal var list: ListToken,
    internal var zero: ZeroToken,
)

/**
 * Identifier associated with an operation by a specific thread on a specific channel.
 */
internal open class Operation(
    public var value: Int,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Operation> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Operation> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Operation =
                    Operation(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Operation) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Operation =
                    Operation(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Operation) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Creates an operation identifier from a mutable reference.
         *
         * This function essentially just turns the address of the reference into a number. The
         * reference should point to a variable that is specific to the thread and the operation,
         * and is alive for the entire duration of a blocking operation.
         */
        internal fun <T> hook(r: T): Operation {
            val value = ((mutPtrFromRustCast(r))).rawAddress.toInt()
            // Make sure that the pointer address doesn't equal the numerical representation of
            // `Selected.{ Waiting, Aborted, Disconnected }`.
            check(value > 2)
            return Operation(value)
        }
    }
}

/**
 * Current state of a blocking operation.
 */
internal sealed class Selected {
    /**
     * Still waiting for an operation.
     */
    public data object Waiting : Selected()
    /**
     * The attempt to block the current thread has been aborted.
     */
    public data object Aborted : Selected()
    /**
     * An operation became ready because a channel is disconnected.
     */
    public data object Disconnected : Selected()
    /**
     * An operation became ready because a message can be sent or received.
     */
    public data class Operation(public var value: Operation) : Selected()

    public fun rustEquals(other: Selected): Boolean = this == other

    public open fun into(): Int =
        when {
            this == Selected.Waiting -> 0
            this == Selected.Aborted -> 1
            this == Selected.Disconnected -> 2
            else -> {
                val (value) = (this as Selected.Operation).value as Operation
                value
            }
        }

    public companion object {
        public fun from(value: Int): Selected =
            when (value) {
                0 -> Selected.Waiting
                1 -> Selected.Aborted
                2 -> Selected.Disconnected
                else -> {
                    val oper = value
                    Selected.Operation(Operation(oper))
                }
            }
    }
}

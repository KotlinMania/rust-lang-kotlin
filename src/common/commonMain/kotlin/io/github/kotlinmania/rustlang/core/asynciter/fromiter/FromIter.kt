// port-lint: source async_iter/from_iter.rs
package io.github.kotlinmania.rustlang.core.asynciter.fromiter

import io.github.kotlinmania.rustlang.core.asynciter.asynciter.AsyncIterator
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

public typealias Item<I> = I.Item

/**
 * An async iterator that was created from iterator.
 *
 * This async iterator is created by the [fromIter] function.
 * See it documentation for more.
 *
 * [fromIter]: fn.fromIter.html
 */
public open class FromIter<I>(
    public var iter: I,
) : AsyncIterator<Item> {
    public open fun clone(): FromIter<I> = FromIter(iter)

    public override fun pollNext(cx: Context): Poll<Item?> = Poll.Ready(this.iter.next())

    public override fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<FromIter<I>> =
            object : TypeLayout<FromIter<I>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutI.sizeBytes, alignBytes)
                override fun readNative(addr: Long): FromIter<I> =
                    FromIter(
                        (typeLayoutI).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FromIter<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FromIter<I> =
                    FromIter(
                        (typeLayoutI).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FromIter<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                }
            }
    }
}

/**
 * Converts an iterator into an async iterator.
 */
public fun <I> fromIter(iter: I): FromIter<IntoIter> = FromIter(iter = iter.iterator())

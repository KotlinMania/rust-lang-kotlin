// port-lint: source async_iter/from_iter.rs
package io.github.kotlinmania.rustlang.core.asynciter.fromiter

import io.github.kotlinmania.rustlang.core.asynciter.asynciter.AsyncIterator
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

    public companion object
}

/**
 * Converts an iterator into an async iterator.
 */
public fun <I> fromIter(iter: I): FromIter<IntoIter> = FromIter(iter = iter.iterator())

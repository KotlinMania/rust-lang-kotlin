// port-lint: source async_iter/async_iter.rs
package io.github.kotlinmania.rustlang.core.asynciter.asynciter

import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

/**
 * A trait for dealing with asynchronous iterators.
 *
 * This is the main async iterator trait. For more about the concept of async iterators
 * generally, please see the [module-level documentation]. In particular, you
 * may want to know how to [implement `AsyncIterator`][impl].
 *
 * [module-level documentation]: index.html
 * [impl]: index.html#implementing-async-iterator
 */
public interface AsyncIterator<Item> {
    /**
     * Attempts to pull out the next value of this async iterator, registering the
     * current task for wakeup if the value is not yet available, and returning
     * `None` if the async iterator is exhausted.
     *
     * # Return value
     *
     * There are several possible return values, each indicating a distinct
     * async iterator state:
     *
     * - `Poll.Pending` means that this async iterator's next value is not ready
     *   yet. Implementations will ensure that the current task will be notified
     *   when the next value may be ready.
     *
     * - `Poll.Ready(Some(val))` means that the async iterator has successfully
     *   produced a value, `val`, and may produce further values on subsequent
     *   `pollNext` calls.
     *
     * - `Poll.Ready(None)` means that the async iterator has terminated, and
     *   `pollNext` should not be invoked again.
     *
     * # Panics
     *
     * Once an async iterator has finished (returned `Ready(None)` from `pollNext`), calling its
     * `pollNext` method again may panic, block forever, or cause other kinds of
     * problems; the `AsyncIterator` trait places no requirements on the effects of
     * such a call. However, as the `pollNext` method is not marked `unchecked`,
     * Rust's usual rules apply: calls must never cause undefined behavior
     * (memory corruption, incorrect use of `unchecked` functions, or the like),
     * regardless of the async iterator's state.
     */
    public fun pollNext(cx: Context): Poll<Item?>

    /**
     * Returns the bounds on the remaining length of the async iterator.
     *
     * Specifically, `sizeHint()` returns a tuple where the first element
     * is the lower bound, and the second element is the upper bound.
     *
     * The second half of the tuple that is returned is an <code>[Option]<[Int]></code>.
     * A [None] here means that either there is no known upper bound, or the
     * upper bound is larger than [usize].
     *
     * # Implementation notes
     *
     * It is not enforced that an async iterator implementation yields the declared
     * number of elements. A buggy async iterator may yield less than the lower bound
     * or more than the upper bound of elements.
     *
     * `sizeHint()` is primarily intended to be used for optimizations such as
     * reserving space for the elements of the async iterator, but must not be
     * trusted to e.g., omit bounds checks in unchecked code. An incorrect
     * implementation of `sizeHint()` should not lead to memory safety
     * violations.
     *
     * That said, the implementation should provide a correct estimation,
     * because otherwise it would be a violation of the trait's protocol.
     *
     * The default implementation returns <code>(0, [None])</code> which is correct for any
     * async iterator.
     */
    public fun sizeHint(): Pair<Int, Int?> = Pair(0, null)
}

public fun <Item, P> Pin<P>.pollNext(cx: Context): Poll<Item?> = Target.pollNext(this.asDerefMut(), cx)

public fun <Item, P> Pin<P>.sizeHint(): Pair<Int, Int?> = this.sizeHint()

/**
 * A helper function for internal desugaring -- produces `Ready(Some(t))`,
 * which corresponds to the async iterator yielding a value.
 */
public fun <T> Poll.Companion.asyncGenReady(t: T): Poll<T?> = Poll.Ready(t)

// Note(genBlocks): This probably could be deduplicated.

/**
 * A helper constant for internal desugaring -- produces `Pending`,
 * which corresponds to the async iterator pending on an `.await`.
 */
public val PENDING: Poll<T?> = Poll.Pending

/**
 * A helper constant for internal desugaring -- produces `Ready(None)`,
 * which corresponds to the async iterator finishing its iteration.
 */
public val FINISHED: Poll<T?> = Poll.Ready(null)

/**
 * Converts something into an async iterator
 */
public interface IntoAsyncIterator<Item, out IntoAsyncIter> {
    /**
     * Converts `self` into an async iterator
     */
    public fun intoAsyncIter(): IntoAsyncIter
}

public fun <Item, I : AsyncIterator<Item>> I.intoAsyncIter(): I = this

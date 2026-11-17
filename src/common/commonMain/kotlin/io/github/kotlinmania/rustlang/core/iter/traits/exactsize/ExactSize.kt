// port-lint: source iter/traits/exact_size.rs
package io.github.kotlinmania.rustlang.core.iter.traits.exactsize

import io.github.kotlinmania.rustlang.core.cmp.Eq
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.assertFailed

/**
 * An iterator that knows its exact length.
 *
 * Many [Iterator]s don't know how many times they will iterate, but some do.
 * If an iterator knows how many times it can iterate, providing access to
 * that information can be useful. For example, if you want to iterate
 * backwards, a good start is to know where the end is.
 *
 * When implementing an `ExactSizeIterator`, you must also implement
 * [Iterator]. When doing so, the implementation of [Iterator.sizeHint]
 * *must* return the exact size of the iterator.
 *
 * The [len] method has a default implementation, so you usually shouldn't
 * implement it. However, you may be able to provide a more performant
 * implementation than the default, so overriding it in this case makes sense.
 *
 * Note that this trait is a safe trait and as such does *not* and *cannot*
 * guarantee that the returned length is correct. This means that `unchecked`
 * code **must not** rely on the correctness of [Iterator.sizeHint]. The
 * unstable and unchecked [`TrustedLen`](marker.TrustedLen) trait gives
 * this additional guarantee.
 *
 * [len]: ExactSizeIterator.len
 *
 * # When *shouldn't* an adapter be `ExactSizeIterator`?
 *
 * If an adapter makes an iterator *longer*, then it's usually incorrect for
 * that adapter to implement `ExactSizeIterator`.  The inner exact-sized
 * iterator might already be `Int.MAX`-long, and thus the length of the
 * longer adapted iterator would no longer be exactly representable in `Int`.
 *
 * This is why [`Chain.A.B`](iter.Chain) isn't `ExactSizeIterator`,
 * even when `A` and `B` are both `ExactSizeIterator`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // a finite range knows exactly how many times it will iterate
 * val five = 0 until 5
 * check(5 == five.len())
 * ```
 *
 * In the [module-level docs], we implemented an [Iterator], `Counter`.
 * Let's implement `ExactSizeIterator` for it as well:
 *
 * ```kotlin
 * // We can easily calculate the remaining number of iterations.
 * fun Counter.len(): Int {
 *     return 5 - this.count
 * }
 * // And now we can use it!
 * var counter = Counter.new()
 * check(5 == counter.len())
 * counter.next()
 * check(4 == counter.len())
 * ```
 */
public interface ExactSizeIterator {
    /**
     * Returns the exact remaining length of the iterator.
     *
     * The implementation ensures that the iterator will return exactly `len()`
     * more times a [Some] value, before returning [None].
     * This method has a default implementation, so you usually should not
     * implement it directly. However, if you can provide a more efficient
     * implementation, you can do so. See the [trait-level] docs for an
     * example.
     *
     * This function has the same safety guarantees as the
     * [Iterator.sizeHint] function.
     *
     * [trait-level]: ExactSizeIterator
     * [Some]: Some
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * // a finite range knows exactly how many times it will iterate
     * var range = 0 until 5
     * check(5 == range.len())
     * range.next()
     * check(4 == range.len())
     * ```
     */
    public fun len(): Int {
        val (lower, upper) = this.sizeHint()
        when (val matchVal = Pair(upper, lower)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = Eq
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
        return lower
    }

    /**
     * Returns `true` if the iterator is empty.
     *
     * This method has a default implementation using
     * [ExactSizeIterator.len], so you don't need to implement it yourself.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(exactSizeIsEmpty)
     * public fun doctestEntry() {
     *     var oneElement = listOf(0)
     *     check(!oneElement.isEmpty())
     *     check(oneElement.next() == 0)
     *     check(oneElement.isEmpty())
     *     check(oneElement.next() == null)
     * }
     * ```
     */
    public fun isEmpty(): Boolean = this.len() == 0
}

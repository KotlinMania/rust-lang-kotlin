// port-lint: source io/size_hint.rs
package io.github.kotlinmania.rustlang.core.io.sizehint

/**
 * Internal trait used to allow for specialization in `Read.sizeHint` and
 * `Iterator.sizeHint`.
 *
 * All types implement this through a blanket default implementation returning
 * a hint of `(0, None)`.
 *
 * Implementors should only provide [`lowerBound`](SizeHint.lowerBound) and
 * [`upperBound`](SizeHint.upperBound).
 * [`sizeHint`](SizeHint.sizeHint) is provided as a `final` method to enforce
 * correctness.
 */
public interface SizeHint {
    /**
     * Returns a lower bound on the number of elements this container-like item
     * contains.
     * For example, an array `[UByte; 12]` could return any value between `0` and
     * `12` inclusively as a correct implementation.
     *
     * Through specialization, all types implement this method returning a default
     * value of `0`.
     *
     * Implementations *must* ensure the returned value is less than or equal to
     * the true element count.
     */
    public fun lowerBound(): Int

    /**
     * Returns an upper bound on the number of elements this container-like item
     * contains if it can be determined, otherwise `None`.
     *
     * Through specialization, all types implement this method returning a default
     * value of `None`.
     *
     * Implementations *must* ensure the returned value is greater than or equal
     * to the true element count.
     */
    public fun upperBound(): Int?

    /**
     * Returns an estimate for the number of elements this container like type
     * contains.
     *
     * This is a `final` method, and is guaranteed to return
     * `(self.lowerBound(), self.upperBound())`.
     *
     * Without specialization, types implementing this trait will return `(0, None)`.
     */
    public fun sizeHint(): Pair<Int, Int?> = Pair(this.lowerBound(), this.upperBound())
}

public fun <T> T.lowerBound(): Int = 0

public fun <T> T.upperBound(): Int? = null

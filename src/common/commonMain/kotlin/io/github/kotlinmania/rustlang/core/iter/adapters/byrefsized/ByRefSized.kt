// port-lint: source iter/adapters/by_ref_sized.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.byrefsized

import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Like `Iterator.byRef`, but requiring `Sized` so it can forward generics.
 *
 * Ideally this will no longer be required, eventually, but as can be seen in
 * the benchmarks (as of Feb 2022 at least) `byRef` can have performance cost.
 */
public open class ByRefSized<I>(
    public var value: I,
) : Iterable<Item> {
    public var field0: I
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@ByRefSized.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // The following implementations use UFCS-style, rather than trusting autoderef,
    // to avoid accidentally calling the `Iterator` implementations.
    public open fun next(nextIFn: (I) -> Item?): Item? = nextIFn(this.value)

    public open fun sizeHint(sizeHintIFn: (I) -> Pair<Int, Int?>): Pair<Int, Int?> = sizeHintIFn(this.value)

    public open fun advanceBy(n: Int, advanceByIFn: (I, Int) -> Result<Unit, NonZero<Int>>): Result<Unit, NonZero<Int>> = advanceByIFn(this.value, n)

    public open fun nth(n: Int, nthIFn: (I, Int) -> Item?): Item? = nthIFn(this.value, n)

    public open fun <B> fold(init: B, f: (B, I.Item) -> B, tryFoldIFn: (I, B, (B, I.Item) -> B) -> R): B {
        // `fold` needs ownership, so this can't forward directly.
        return tryFoldIFn(this.value, init, NeverShortCircuit.wrapMut2(f)).first
    }

    public open fun <B, R> tryFold(init: B, f: (B, I.Item) -> R, tryFoldIFn: (I, B, (B, I.Item) -> R) -> R): R where R : Try<R, B, Any?> = tryFoldIFn(this.value, init, f)

    public open fun nextBack(nextBackIFn: (I) -> Item?): Item? = nextBackIFn(this.value)

    public open fun advanceBackBy(n: Int, advanceBackByIFn: (I, Int) -> Result<Unit, NonZero<Int>>): Result<Unit, NonZero<Int>> = advanceBackByIFn(this.value, n)

    public open fun nthBack(n: Int, nthBackIFn: (I, Int) -> Item?): Item? = nthBackIFn(this.value, n)

    public open fun <B> rfold(init: B, f: (B, I.Item) -> B, tryRfoldIFn: (I, B, (B, I.Item) -> B) -> R): B {
        // `rfold` needs ownership, so this can't forward directly.
        return tryRfoldIFn(this.value, init, NeverShortCircuit.wrapMut2(f)).first
    }

    public open fun <B, R> tryRfold(init: B, f: (B, I.Item) -> R, tryRfoldIFn: (I, B, (B, I.Item) -> R) -> R): R where R : Try<R, B, Any?> = tryRfoldIFn(this.value, init, f)
}

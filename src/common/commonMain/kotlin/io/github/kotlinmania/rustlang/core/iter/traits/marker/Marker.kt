// port-lint: source iter/traits/marker.rs
package io.github.kotlinmania.rustlang.core.iter.traits.marker

import io.github.kotlinmania.rustlang.core.iter.range.Step
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero

/**
 * Same as FusedIterator
 *
 * # Safety
 *
 * This is used for specialization. Therefore implementations must not
 * be scope-dependent.
 */
public interface TrustedFused

// Note: this should be a @marker and have another blanket impl for T: TrustedFused
// but that ICEs iter.Fuse specializations.

/**
 * An iterator that always continues to yield `None` when exhausted.
 *
 * Calling next on a fused iterator that has returned `None` once is guaranteed
 * to return [None] again. This trait should be implemented by all iterators
 * that behave this way because it allows optimizing [Iterator.fuse].
 *
 * Note: In general, you should not use `FusedIterator` in generic bounds if
 * you need a fused iterator. Instead, you should just call [Iterator.fuse]
 * on the iterator. If the iterator is already fused, the additional [Fuse]
 * wrapper will be a no-op with no performance penalty.
 */
public interface FusedIterator<T>

/**
 * An iterator that reports an accurate length using sizeHint.
 *
 * The iterator reports a size hint where it is either exact
 * (lower bound is equal to upper bound), or the upper bound is [None].
 * The upper bound must only be [None] if the actual iterator length is
 * larger than [usize.Max]. In that case, the lower bound must be
 * [usize.Max], resulting in an [Iterator.sizeHint] of
 * `(Int.MAX, None)`.
 *
 * The iterator must produce exactly the number of elements it reported
 * or diverge before reaching the end.
 *
 * # When *shouldn't* an adapter be `TrustedLen`?
 *
 * If an adapter makes an iterator *shorter* by a given amount, then it's
 * usually incorrect for that adapter to implement `TrustedLen`.  The inner
 * iterator might return more than `Int.MAX` items, but there's no way to
 * know what `k` elements less than that will be, since the `sizeHint` from
 * the inner iterator has already saturated and lost that information.
 *
 * This is why [`Skip.I`](iter.Skip) isn't `TrustedLen`, even when
 * `I` implements `TrustedLen`.
 *
 * # Safety
 *
 * This trait must only be implemented when the contract is upheld. Consumers
 * of this trait must inspect [Iterator.sizeHint]’s upper bound.
 */
public interface TrustedLen<T>

/**
 * An iterator that when yielding an item will have taken at least one element
 * from its underlying [SourceIter].
 *
 * Calling any method that advances the iterator, e.g.  [next] or [tryFold],
 * guarantees that for each step at least one value of the iterator's underlying source
 * has been moved out and the result of the iterator chain could be inserted
 * in its place, assuming structural constraints of the source allow such an insertion.
 * In other words this trait indicates that an iterator pipeline can be collected in place.
 *
 * The primary use of this trait is in-place iteration. Refer to the [vec.inPlaceCollect]
 * module documentation for more information.
 *
 * [vec.inPlaceCollect]: ../../../../alloc/vec/inPlaceCollect/index.html
 * [next]: Iterator.next
 * [tryFold]: Iterator.tryFold
 */
public interface InPlaceIterable {
    /**
     * The product of one-to-many item expansions that happen throughout the iterator pipeline.
     * E.g. [[u8; 4]; 4].iter().flatten().flatten() would have a `EXPAND_BY` of 16.
     * This is an upper bound, i.e. the transformations will produce at most this many items per
     * input. It's meant for layout calculations.
     */
    public val EXPAND_BY: NonZero<Int>?

    /**
     * The product of many-to-one item reductions that happen throughout the iterator pipeline.
     * E.g. [u8].iter().arrayChunks::<4>().arrayChunks::<4>() would have a `MERGE_BY` of 16.
     * This is a lower bound, i.e. the transformations will consume at least this many items per
     * output.
     */
    public val MERGE_BY: NonZero<Int>?
}

/**
 * A type that upholds all invariants of [Step].
 *
 * The invariants of [Step.stepsBetween] are a superset of the invariants
 * of [TrustedLen]. As such, [TrustedLen] is implemented for all range
 * types with the same generic type argument.
 *
 * # Safety
 *
 * The implementation of [Step] for the given type must guarantee all
 * invariants of all methods are upheld. See the [Step] trait's documentation
 * for details. Consumers are free to rely on the invariants in unchecked code.
 */
public interface TrustedStep<T> : Step<T>

// port-lint: source iter/adapters/skip.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.skip

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.unlikely
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator that skips over `n` elements of `iter`.
 *
 * This `class` is created by the [skip] method on [Iterator]. See its
 * documentation for more.
 *
 * [skip]: Iterator.skip
 * [Iterator]: trait.Iterator.html
 */
public open class Skip<I>(
    public var iter: I,
    public var n: Int,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Skip.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Skip<I> = Skip(iter, n)

    public open fun next(): Item? =
        if (unlikely(this.n > 0)) {
            this.iter.nth(take(this.n))
        } else {
            this.iter.next()
        }

    public open fun nth(n: Int): Item? {
        return if (this.n > 0) {
            val skip: Int = take(this.n)
            // Checked add to handle overflow case.
            val n1 =
                when (val matchVal = skip.checkedAdd(n)) {
                    null -> {
                        // In case of overflow, load skip value, before loading `n`.
                        // Because the amount of elements to iterate is beyond `Int.MAX`, this
                        // is split into two `nth` calls where the `skip` `nth` call is discarded.
                        (this.iter.nth(skip - 1) ?: return null)
                        n
                    }
                    else -> {
                        val nth = matchVal
                        nth
                    }
                }
            // Load nth element including skip.
            this.iter.nth(n1)
        } else {
            this.iter.nth(n)
        }
    }

    public open fun count(): Int {
        if (this.n > 0) {
            // nth(n) skips n+1
            if (this.iter.nth(this.n - 1) == null) {
                return 0
            }
        }
        return this.iter.count()
    }

    public open fun last(): Item? {
        if (this.n > 0) {
            // nth(n) skips n+1
            (this.iter.nth(this.n - 1) ?: return null)
        }
        return this.iter.last()
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (lower, upper) = this.iter.sizeHint()
        val lower1 = lower.saturatingSub(this.n)
        val upper1 =
            when (upper) {
                null -> null
                else -> {
                    val x = upper
                    x.saturatingSub(this.n)
                }
            }
        return Pair(lower1, upper1)
    }

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        val n = this.n
        this.n = 0
        if (n > 0) {
            // nth(n) skips n+1
            if (this.iter.nth(n - 1) == null) {
                return init
            }
        }
        return this.iter.tryFold(init, fold)
    }

    public open fun <Acc> fold(init: Acc, fold: (Acc, Item) -> Acc): Acc {
        if (this.n > 0) {
            // nth(n) skips n+1
            if (this.iter.nth(this.n - 1) == null) {
                return init
            }
        }
        return this.iter.fold(init, fold)
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        var n1 = n
        if (this.n > 0 && n1 == 0) {
            // If we try to process our skips now, we might witness the end of the inner iterator,
            // but there's no valid `Err(k)` that we could return. This is especially a problem for
            // unfused iterators that might end up called again by `Chain`, for example, so it's
            // better that we do nothing at all.
            return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }
        val skipInner = this.n
        val skipAndAdvance = skipInner.saturatingAdd(n1)
        val remainder =
            run {
                val matchVal = this.iter.advanceBy(skipAndAdvance)
                when (matchVal) { is Result.Ok<*, *> -> {
                    0
                }; is Result.Err<*, *> -> {
                    val n2 = matchVal.errValue()
                    n2.get()
                } }
            }
        val advancedInner = skipAndAdvance - remainder
        n1 -= advancedInner.saturatingSub(skipInner)
        this.n = this.n.saturatingSub(advancedInner)
        // skipAndAdvance may have saturated
        if (unlikely(remainder == 0 && n1 > 0)) {
            n1 = run {
                val matchVal1 = this.iter.advanceBy(n1)
                when (matchVal1) { is Result.Ok<*, *> -> {
                    0
                }; is Result.Err<*, *> -> {
                    val n3 = matchVal1.errValue()
                    n3.get()
                } }
            }
        }
        return ((NonZero.new<NonZeroUsizeInner, Int>(n1))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun iteratorGetUnchecked(idx: Int): Item {
        // SAFETY: the caller must uphold the contract for
        // `Iterator.__iterator_get_unchecked`.
        //
        // Dropping the skipped prefix when index 0 is passed is safe
        // since
        // * the caller passing index 0 means that the inner iterator has more items than `self.n`
        // * TRA contract requires that getUnchecked will only be called once
        // (unless elements are copyable)
        // * it does not conflict with in-place iteration since index 0 must be accessed
        // before something is written into the storage used by the prefix
        return run {
            if (Skip.MAY_HAVE_SIDE_EFFECT && idx == 0) {
                for (skippedIdx in 0 until this.n) {
                    (tryGetUnchecked(this.iter, skippedIdx)).let { }
                }
            }
            tryGetUnchecked(this.iter, idx + this.n)
        }
    }

    public open fun nextBack(): Item? =
        if (this.len() > 0) {
            this.iter.nextBack()
        } else {
            null
        }

    public open fun nthBack(n: Int): Item? {
        val len = this.len()
        return if (n < len) {
            this.iter.nthBack(n)
        } else {
            if (len > 0) {
                // consume the original iterator
                this.iter.nthBack(len - 1)
            }
            null
        }
    }

    public open fun <Residual, Acc, R> tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        fun <Residual, T, Acc, R : Try<R, Acc, Residual>> check(n: Int, fold1: (Acc, T) -> R): (Acc, T) -> ControlFlow<R, Acc> {
            var n1 = n
            return { acc, x ->
                n1 -= 1
                val r = fold1(acc, x)
                if (n1 == 0) {
                    ControlFlow.Break(r)
                } else {
                    ControlFlow.fromTry(r)
                }
            }
        }
        val n = this.len()
        return if (n == 0) {
            init
        } else {
            this.iter.tryRfold(init, check(n, fold)).intoTry()
        }
    }

    public open fun <Aaa> rfold(init: Aaa, fold: (Aaa, Item) -> Aaa): Aaa = this.tryRfold(init, NeverShortCircuit.wrapMut2(fold)).first

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val min = minOf(this.len(), n)
        val rem = this.iter.advanceBackBy(min)
        check(rem.isOk()) { "ExactSizeIterator contract violation" }
        return ((NonZero.new<NonZeroUsizeInner, Int>(n - min))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<Skip<I>> =
            object : TypeLayout<Skip<I>> {
                override val alignBytes: Long = listOf(8L, typeLayoutI.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 8L)
                override val sizeBytes: Long = alignUpBytes((off1 + 8L), alignBytes)
                override fun readNative(addr: Long): Skip<I> =
                    Skip(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Skip<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off1, value.n)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Skip<I> =
                    Skip(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Skip<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off1.toInt(), value.n)
                }
            }

        public fun <I> new(iter: I, n: Int): Skip<I> = Skip(iter = iter, n = n)
    }
}

public fun <Source, I> Skip<I>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY

public val MAY_HAVE_SIDE_EFFECT: Boolean = I.MAY_HAVE_SIDE_EFFECT

// SAFETY: This adapter is shortening. TrustedLen requires the upper bound to be calculated correctly.
// These requirements can only be satisfied when the upper bound of the inner iterator's upper
// bound is never `None`. I: TrustedRandomAccess happens to provide this guarantee while
// I: TrustedLen would not.

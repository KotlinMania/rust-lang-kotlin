// port-lint: source iter/adapters/take.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.take

import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.sources.repeat.Repeat
import io.github.kotlinmania.rustlang.core.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.rustSaturatingAdd
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator that only iterates over the first `n` iterations of `iter`.
 *
 * This `class` is created by the [take] method on [Iterator]. See its
 * documentation for more.
 *
 * [take]: Iterator.take
 * [Iterator]: trait.Iterator.html
 */
public open class Take<I>(
    public var iter: I,
    public var n: Int,
) : SourceIter<Any?, Source>, InPlaceIterable, TrustedFused, TrustedLen<Take<I>>, SpecTake<Take<I>>, Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Take.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Take<I> = Take(iter, n)

    public override fun next(): Item? =
        if (this.n != 0) {
            this.n -= 1
            this.iter.next()
        } else {
            null
        }

    public override fun nth(n: Int): Item? =
        if (this.n > n) {
            this.n -= n + 1
            this.iter.nth(n)
        } else {
            if (this.n > 0) {
                this.iter.nth(this.n - 1)
                this.n = 0
            }
            null
        }

    public override fun count(): Int {
        if (this.n == 0) {
            return 0
        }
        // Advancing consumes the same elements `next` would have yielded,
        // while benefiting from the inner iterator's `advanceBy` fast path.
        return run {
            val matchVal = this.iter.advanceBy(this.n)
            when (matchVal) { is Result.Ok<*> -> {
                this.n
            }; is Result.Err<*> -> {
                val remaining = matchVal.error
                this.n - remaining.get()
            } }
        }
    }

    public override fun sizeHint(): Pair<Int, Int?> {
        if (this.n == 0) {
            return Pair(0, 0)
        }
        val (lower, upper) = this.iter.sizeHint()
        val lower1 = minOf(lower, this.n)
        val upper1 =
            when {
                upper != null && upper < this.n -> {
                    val x = upper
                    x
                }
                else -> this.n
            }
        return Pair(lower1, upper1)
    }

    public override fun <Acc, R> tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Any?> {
        fun <T, Acc, R : Try<R, Acc, Any?>> check(n: RustMutableReference<Int>, fold1: (Acc, T) -> R): (Acc, T) -> ControlFlow<R, Acc> {
            val n1 = n
            return { acc, x ->
                (n1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue -= (1); targetPlace.write(currentValue) }
                val r = fold1(acc, x)
                if ((n1).read() == 0) {
                    ControlFlow.Break(r)
                } else {
                    ControlFlow.fromTry(r)
                }
            }
        }
        return if (this.n == 0) {
            init
        } else {
            val n = this.n
            this.iter.tryFold(init, check(n, fold)).intoTry()
        }
    }

    public override fun <B> fold(init: B, f: (B, Item) -> B): B = Take.specFold(this, init, f)

    public override fun forEach(f: (Item) -> Unit) {
        Take.specForEach(this, f)
    }

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int>> {
        val min = this.n.min(n)
        val rem1 =
            run {
                val matchVal = this.iter.advanceBy(min)
                when (matchVal) { is Result.Ok<*> -> {
                    0
                }; is Result.Err<*> -> {
                    val rem = matchVal.error
                    rem.get()
                } }
            }
        val advanced = min - rem1
        this.n -= advanced
        return NonZero.new(n - advanced).mapOr(Result.Ok(Unit), { Result.Err(it) })
    }

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public open fun nextBack(): Item? =
        if (this.n == 0) {
            null
        } else {
            val n = this.n
            this.n -= 1
            this.iter.nthBack(rustSaturatingSub(this.iter.len(), n))
        }

    public open fun nthBack(n: Int): Item? {
        val len = this.iter.len()
        return if (this.n > n) {
            val m = rustSaturatingSub(len, this.n) + n
            this.n -= n + 1
            this.iter.nthBack(m)
        } else {
            if (len > 0) {
                this.iter.nthBack(len - 1)
            }
            null
        }
    }

    public open fun <Acc, R> tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Any?> =
        if (this.n == 0) {
            init
        } else {
            val len = this.iter.len()
            if (len > this.n && this.iter.nthBack(len - this.n - 1) == null) {
                init
            } else {
                this.iter.tryRfold(init, fold)
            }
        }

    public open fun <Acc> rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc =
        if (this.n == 0) {
            init
        } else {
            val len = this.iter.len()
            if (len > this.n && this.iter.nthBack(len - this.n - 1) == null) {
                init
            } else {
                this.iter.rfold(init, fold)
            }
        }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> {
        // The amount by which the inner iterator needs to be shortened for it to be
        // at most as long as the take() amount.
        val trimInner = rustSaturatingSub(this.iter.len(), this.n)
        // The amount we need to advance inner to fulfill the caller's request.
        // take(), advanceBy() and len() all can be at most Int, so we don't have to worry
        // about having to advance more than usize.Max here.
        val advanceBy = rustSaturatingAdd(trimInner, n)
        val remainder =
            run {
                val matchVal = this.iter.advanceBackBy(advanceBy)
                when (matchVal) { is Result.Ok<*> -> {
                    0
                }; is Result.Err<*> -> {
                    val rem = matchVal.error
                    rem.get()
                } }
            }
        val advancedByInner = advanceBy - remainder
        val advancedBy = advancedByInner - trimInner
        this.n -= advancedBy
        return NonZero.new(n - advancedBy).mapOr(Result.Ok(Unit), { Result.Err(it) })
    }

    public override fun <B> specFold(init: B, f: (B, Item) -> B): B = this.tryFold(init, NeverShortCircuit.wrapMut2(f)).first

    public override fun specForEach(f: (Item) -> Unit) {
        // The default implementation would use a unit accumulator, so we can
        // avoid a stateful closure by folding over the remaining number
        // of items we wish to return instead.
        fun <Item> check(action: (Item) -> Unit): (Int, Item) -> Int? =
            { more, x ->
                action(x)
                more.checkedSub(1)
            }
        val remaining = this.n
        if (remaining > 0) {
            this.iter.tryFold(remaining - 1, check(f))
        }
    }

    public open fun <B> specFoldSpecTake(init: B, f: (B, Item) -> B): B {
        var acc = init
        val end = this.n.min(this.iter.size())
        for (i in 0 until end) {
            // SAFETY: i < end <= self.iter.size() and we discard the iterator at the end
            val value = this.iter.iteratorGetUnchecked(i)
            acc = f(acc, value)
        }
        return acc
    }

    public open fun specForEachSpecTake(f: (Item) -> Unit) {
        val end = this.n.min(this.iter.size())
        for (i in 0 until end) {
            // SAFETY: i < end <= self.iter.size() and we discard the iterator at the end
            val value = this.iter.iteratorGetUnchecked(i)
            f(value)
        }
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<Take<I>> =
            object : TypeLayout<Take<I>> {
                override val alignBytes: Long = listOf(8L, typeLayoutI.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 8L)
                override val sizeBytes: Long = alignUpBytes((off1 + 8L), alignBytes)
                override fun readNative(addr: Long): Take<I> =
                    Take(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Take<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off1, value.n)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Take<I> =
                    Take(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Take<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off1.toInt(), value.n)
                }
            }

        public fun <I> new(iter: I, n: Int): Take<I> = Take(iter = iter, n = n)

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
    }
}

public interface SpecTake<T> {
    public fun <B> specFold(init: B, f: (B, Item) -> B): B

    public fun specForEach(f: (Item) -> Unit)
}

public fun <T> Take<Repeat<T>>.nextBack(): Item? = this.next()

public fun <T> Take<Repeat<T>>.nthBack(n: Int): Item? = this.nth(n)

public fun <T, Acc, R> Take<Repeat<T>>.tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Any?> = this.tryFold(init, fold)

public fun <T, Acc> Take<Repeat<T>>.rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.fold(init, fold)

public fun <T> Take<Repeat<T>>.advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.advanceBy(n)

// Note: It may be tempting to impl DoubleEndedIterator for Take<RepeatWith>.
// One must fight that temptation since such implementation wouldn’t be correct
// because we have no way to return value of nth invocation of repeater followed
// by n-1st without remembering all results.
public fun <T> Take<Repeat<T>>.len(): Int = this.n

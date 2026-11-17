// port-lint: source iter/adapters/cycle.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.cycle

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An iterator that repeats endlessly.
 *
 * This `class` is created by the [cycle] method on [Iterator]. See its
 * documentation for more.
 *
 * [cycle]: Iterator.cycle
 * [Iterator]: trait.Iterator.html
 */
public open class Cycle<I>(
    public var orig: I,
    public var iter: I,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Cycle.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Cycle<I> = Cycle(orig, iter)

    public open fun next(): Item? =
        when (val matchVal = this.iter.next()) {
            null -> {
                this.iter = this.orig.clone()
                this.iter.next()
            }
            else -> {
                val y = matchVal
                y
            }
        }

    public open fun sizeHint(): Pair<Int, Int?> {
        // the cycle iterator is either empty or infinite
        return run {
            val matchVal = this.orig.sizeHint()
            when {
                matchVal.first == 0 && matchVal.second != null && matchVal.second == 0 -> {
                    val sz = matchVal
                    sz
                }
                matchVal.first == 0 -> Pair(0, null)
                else -> Pair(Int.MAX_VALUE, null)
            }
        }
    }

    public open fun <Residual, Acc, R> tryFold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        // fully iterate the current iterator. this is necessary because
        // `self.iter` may be empty even when `self.orig` isn't
        acc1 = when (val tryResult = this.iter.tryFold(acc1, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        this.iter = this.orig.clone()
        // complete a full cycle, keeping track of whether the cycled
        // iterator is empty or not. we need to return early in case
        // of an empty iterator to prevent an infinite loop
        var isEmpty = true
        acc1 = when (val tryResult = this.iter.tryFold(acc1, { acc2, x ->
            isEmpty = false
            f(acc2, x)
        })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        if (isEmpty) {
            return acc1
        }
        while (true) {
            this.iter = this.orig.clone()
            acc1 = when (val tryResult = this.iter.tryFold(acc1, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        }
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val n1 = n
        var n2 =
            run {
                val matchVal = this.iter.advanceBy(n1)
                when (matchVal) { is Result.Ok -> {
                    return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                }; is Result.Err -> {
                    val rem = matchVal.error
                    rem.get()
                } }
            }
        while (n2 > 0) {
            this.iter = this.orig.clone()
            n2 = run {
                val matchVal1 = this.iter.advanceBy(n2)
                when {
                    matchVal1 is Result.Ok -> {
                        val valueVal = matchVal1.value
                        return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                    }
                    matchVal1 is Result.Err && (matchVal1 as Result.Err).error.get() == n2 -> {
                        val e = matchVal1
                        val rem1 = matchVal1.error
                        return e
                    }
                    else -> {
                        val rem2 = matchVal1.error
                        rem2.get()
                    }
                }
            }
        }
        return ((NonZero.new<NonZeroUsizeInner, Int>(n2))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<Cycle<I>> =
            object : TypeLayout<Cycle<I>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutI.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutI.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Cycle<I> =
                    Cycle(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutI).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Cycle<I>) {
                    (typeLayoutI).writeNative(addr, value.orig)
                    (typeLayoutI).writeNative(addr + off1, value.iter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cycle<I> =
                    Cycle(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutI).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cycle<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.orig)
                    (typeLayoutI).writeBytes(buf, offset + off1.toInt(), value.iter)
                }
            }

        public fun <I> new(iter: I, cloneIFn: (I) -> I = { it }): Cycle<I> = Cycle(orig = cloneIFn(iter), iter = iter)
    }
}

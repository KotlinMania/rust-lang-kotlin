// port-lint: source iter/adapters/filter.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.filter

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.iter.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public typealias Source<I> = I.Source

/**
 * An iterator that filters the elements of `iter` with `predicate`.
 *
 * This `class` is created by the [filter] method on [Iterator]. See its
 * documentation for more.
 *
 * [filter]: Iterator.filter
 * [Iterator]: trait.Iterator.html
 */
public open class Filter<I, P>(
    // Used for `SplitWhitespace` and `SplitAsciiWhitespace` `asStr` methods
    internal var iter: I,
    public var predicate: P,
) : SourceIter<Any?, Source>, InPlaceIterable, Iterable<Item> {
    override fun iterator(): kotlin.collections.Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Filter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Filter<I, P> = Filter(iter, predicate)

    public open fun nextChunkDropless(n: Int): Result<List<Item>, IntoIter<Item>> {
        val array: List<MaybeUninit<Item>>  = List(n) { MaybeUninit.uninit(ObjectSlotLayout<Item>()) }
        var initialized = 0
        val result =
            this.iter.tryForEach({ element ->
                val idx = initialized
                // branchless index update combined with unconditionally copying the value even when
                // it is filtered reduces branching and dependencies in the loop.
                initialized = idx + (this.predicate)(element).toInt()
                // SAFETY: Loop conditions ensure the index is in bounds.
                array.getUncheckedMut(idx).write(element)
                if (initialized < n) {
                    ControlFlow.Continue()
                } else {
                    ControlFlow.Break()
                }
            })
        return when (result) {
            is ControlFlow.Break -> {
                // SAFETY: The loop above is only explicitly broken when the array has been fully initialized
                Result.Ok(MaybeUninit.arrayAssumeInit(array))
            }
            is ControlFlow.Continue -> {
                // SAFETY: The range is in bounds since the loop breaks when reaching N elements.
                Result.Err(IntoIter.newUnchecked(array, 0 until initialized))
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Filter").field("iter", this.iter).finish()

    public open fun next(): Item? = this.iter.indexOf(this.predicate).takeIf { it >= 0 }

    public open fun nextChunk(n: Int): Result<List<Item>, IntoIter<Item>> {
        // avoid codegen for the dead branch
        val fn =
            if (needsDrop<Item>()) {
                iterNextChunk<Item>
            } else {
                Filter::nextChunkDropless
            }
        return fn(this)
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (_, upper) = this.iter.sizeHint()
        return Pair(0, upper)
        // can't know a lower bound, due to the predicate
    }

    // this special case allows the compiler to make `.filter(_).count()`
    // branchless. Barring perfect branch prediction (which is unattainable in
    // the general case), this will be much faster in >90% of cases (containing
    // virtually all real workloads) and only a tiny bit slower in the rest.
    //
    // Having this specialization thus allows us to write `.filter(p).count()`
    // where we would otherwise write `.map(| x | p(x) as Int).sum()`, which is
    // less readable and also less backwards-compatible to Rust before 1.10.
    //
    // Using the branchless version will also simplify the LLVM byte code, thus
    // leaving more budget for LLVM optimizations.
    public open fun count(assumeCountLeUpperBoundIFn: (Any?, Any?) -> Unit): Int {
        fun <T> toUsize(predicate: (T) -> Boolean): (T) -> Int = { x -> predicate(x).toInt() }
        val before = this.iter.sizeHint().second.unwrapOr(Int.MAX_VALUE)
        val total = this.iter.map(toUsize(this.predicate)).sum()
        // SAFETY: `total` and `before` came from the same iterator of type `I`
        run {
            assumeCountLeUpperBoundIFn(total, before)
        }
        return total
    }

    public open fun <Acc, R> tryFold(init: Acc, fold: (Acc, I.Item) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryFold(init, filterTryFold(this.predicate, fold))

    public open fun <Acc> fold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc = this.iter.fold(init, filterFold(this.predicate, fold))

    public open fun nextBack(): Item? = this.iter.rfind(this.predicate)

    public open fun <Acc, R> tryRfold(init: Acc, fold: (Acc, I.Item) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryRfold(init, filterTryFold(this.predicate, fold))

    public open fun <Acc> rfold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc = this.iter.rfold(init, filterFold(this.predicate, fold))

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I, P> typeLayout(typeLayoutI: io.github.kotlinmania.rustlang.core.mem.TypeLayout<I>, typeLayoutP: io.github.kotlinmania.rustlang.core.mem.TypeLayout<P>): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Filter<I, P>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Filter<I, P>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutP.alignBytes).max()
                private val off1: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes(typeLayoutI.sizeBytes, typeLayoutP.alignBytes)
                override val sizeBytes: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes((off1 + typeLayoutP.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Filter<I, P> =
                    Filter(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutP).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Filter<I, P>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutP).writeNative(addr + off1, value.predicate)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Filter<I, P> =
                    Filter(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutP).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Filter<I, P>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutP).writeBytes(buf, offset + off1.toInt(), value.predicate)
                }
            }

        internal fun <I, P> new(iter: I, predicate: P): Filter<I, P> = Filter(iter = iter, predicate = predicate)

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
    }
}

public fun <T, Acc> filterFold(predicate: (T) -> Boolean, fold: (Acc, T) -> Acc): (Acc, T) -> Acc =
    { acc, item -> if (predicate(item)) {
        fold(acc, item)
    } else {
        acc
    } }

public fun <T, Acc, R : Try<R, Acc, Any?>> filterTryFold(predicate: (T) -> Boolean, fold: (Acc, T) -> R): (Acc, T) -> R =
    { acc, item -> if (predicate(item)) {
        fold(acc, item)
    } else {
        acc
    } }

public interface SpecAssumeCount {
    /**
     * # Safety
     *
     * `count` must be an number of items actually read from the iterator.
     *
     * `upper` must either:
     * - have come from `sizeHint().1` on the iterator, or
     * - be `Int.MAX` which will vacuously do nothing.
     */
    public fun assumeCountLeUpperBound(count: Int, upper: Int)
}

public fun <Item, I> specAssumeCountAssumeCountLeUpperBoundI(count: Int, upper: Int) {
    // In the default we can't trust the `upper` for soundness
    // because it came from an untrusted `sizeHint`.
    // In debug mode we might as well check that the sizeHint wasn't too small
    upper - count
}

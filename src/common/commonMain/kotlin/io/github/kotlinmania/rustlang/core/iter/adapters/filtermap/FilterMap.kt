// port-lint: source iter/adapters/filter_map.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.filtermap

import io.github.kotlinmania.rustlang.core.array.Guard
import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

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
 * An iterator that uses `f` to both filter and map elements from `iter`.
 *
 * This `class` is created by the [filterMap] method on [Iterator]. See its
 * documentation for more.
 *
 * [filterMap]: Iterator.filterMap
 * [Iterator]: trait.Iterator.html
 */
public open class FilterMap<I, F>(
    public var iter: I,
    public var f: F,
) {
    public open fun clone(): FilterMap<I, F> = FilterMap(iter, f)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "FilterMap").field("iter", this.iter).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, F> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutF: TypeLayout<F>): TypeLayout<FilterMap<I, F>> =
            object : TypeLayout<FilterMap<I, F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutF.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutF.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutF.sizeBytes), alignBytes)
                override fun readNative(addr: Long): FilterMap<I, F> =
                    FilterMap(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutF).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: FilterMap<I, F>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutF).writeNative(addr + off1, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilterMap<I, F> =
                    FilterMap(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutF).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilterMap<I, F>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutF).writeBytes(buf, offset + off1.toInt(), value.f)
                }
            }

        public fun <I, F> new(iter: I, f: F): FilterMap<I, F> = FilterMap(iter = iter, f = f)
    }
}

public fun <T, B, Acc> filterMapFold(f: (T) -> B?, fold: (Acc, B) -> Acc): (Acc, T) -> Acc =
    { acc, item -> when (val matchVal = f(item)) {
        null -> acc
        else -> {
            val x = matchVal
            fold(acc, x)
        }
    } }

public fun <Residual, T, B, Acc, R : Try<R, Acc, Residual>> filterMapTryFold(f: (T) -> B?, fold: (Acc, B) -> R): (Acc, T) -> R =
    { acc, item -> when (val matchVal = f(item)) {
        null -> acc
        else -> {
            val x = matchVal
            fold(acc, x)
        }
    } }

public fun <Item, B, I : Iterator<Item>> FilterMap<I, F>.next(): B? = this.iter.findMap(this.f)

public fun <Item, B, I : Iterator<Item>> FilterMap<I, F>.nextChunk(n: Int = 0): Result<List<B>, IntoIter<B>> {
    open class Guard<T>(
        public var array: MutableList<MaybeUninit<T>>,
        public var initialized: Int,
    ) {
        public open fun drop() {
            if (needsDrop<T>()) {
                // SAFETY: self.initialized is always <= N, which also is the length of the array.
                run {
                    this.array.getUncheckedMut(0 until this.initialized).assumeInitDrop()
                }
            }
        }
    }
    var array: List<MaybeUninit<B>> = List(n) { MaybeUninit.uninit(UnresolvedTypeLayout<B>()) }
    val guard = Guard(array = array, initialized = 0)
    val result =
        this.iter.tryForEach({ element ->
            val idx = guard.initialized
            val value = (this.f)(element)
            guard.initialized = idx + (if (value != null) 1 else 0)
            // SAFETY: Loop conditions ensure the index is in bounds.
            run {
                val optPayloadAt: ConstPtr = (addrOf(value)).byteAdd(0).cast()
                val dst = MutPtr((guard.array.asMutPtr()).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))
                copyNonoverlapping(optPayloadAt, dst, 1, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<B>>())
                forget(value)
            }
            if (guard.initialized < n) {
                ControlFlow.Continue(Unit)
            } else {
                ControlFlow.Break(Unit)
            }
        })
    val guard1 = guard
    return when (result) {
        is ControlFlow.Break<*, *> -> {
            val cfBreakValueVal = result.cfBreakValue()
            // SAFETY: The loop above is only explicitly broken when the array has been fully initialized
            Result.Ok<List<B>, IntoIter<B>>(MaybeUninit.arrayAssumeInit(array, n))
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = result.cfContinueValue()
            val initialized = guard1.initialized
            // SAFETY: The range is in bounds since the loop breaks when reaching N elements.
            Result.Err<List<B>, IntoIter<B>>(IntoIter.newUnchecked(array, 0 until initialized, n))
        }
    }
}

public fun <Item, I : Iterator<Item>> FilterMap<I, F>.sizeHint(): Pair<Int, Int?> {
    val (_, upper) = this.iter.sizeHint()
    return Pair(0, upper)
    // can't know a lower bound, due to the predicate
}

public fun <Item, Residual, B, I : Iterator<Item>, Acc, R> FilterMap<I, F>.tryFold(init: Acc, fold: (Acc, B) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryFold(init, filterMapTryFold(this.f, fold))

public fun <Item, B, I : Iterator<Item>, Acc> FilterMap<I, F>.fold(init: Acc, fold: (Acc, B) -> Acc): Acc = this.iter.fold(init, filterMapFold(this.f, fold))

public fun <Item, B, I : Iterator<Item>> FilterMap<I, F>.nextBack(): B? {
    fun <T, B> find(f: (T) -> B?): (Unit, T) -> ControlFlow<B, Unit> =
        { x -> when (val matchVal = f(x)) {
            null -> ControlFlow.Continue(Unit)
            else -> {
                val x1 = matchVal
                ControlFlow.Break(x1)
            }
        } }
    return this.iter.tryRfold(Unit, find(this.f)).breakValue()
}

public fun <Item, Residual, B, I : Iterator<Item>, Acc, R> FilterMap<I, F>.tryRfold(init: Acc, fold: (Acc, B) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryRfold(init, filterMapTryFold(this.f, fold))

public fun <Item, B, I : Iterator<Item>, Acc> FilterMap<I, F>.rfold(init: Acc, fold: (Acc, B) -> Acc): Acc = this.iter.rfold(init, filterMapFold(this.f, fold))

public fun <Source, I, F> FilterMap<I, F>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY

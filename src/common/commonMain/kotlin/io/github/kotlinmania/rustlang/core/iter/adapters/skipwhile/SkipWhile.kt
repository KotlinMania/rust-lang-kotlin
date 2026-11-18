// port-lint: source iter/adapters/skip_while.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.skipwhile

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

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
 * An iterator that rejects elements while `predicate` returns `true`.
 *
 * This `class` is created by the [skipWhile] method on [Iterator]. See its
 * documentation for more.
 *
 * [skipWhile]: Iterator.skipWhile
 * [Iterator]: trait.Iterator.html
 */
public open class SkipWhile<I, P>(
    public var iter: I,
    public var flag: Boolean,
    public var predicate: P,
) {
    public open fun clone(): SkipWhile<I, P> = SkipWhile(iter, flag, predicate)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "SkipWhile").field("iter", this.iter).field("flag", this.flag).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, P> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutP: TypeLayout<P>): TypeLayout<SkipWhile<I, P>> =
            object : TypeLayout<SkipWhile<I, P>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutP.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 1L)
                private val off2: Long = alignUpBytes((off1 + 1L), typeLayoutP.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutP.sizeBytes), alignBytes)
                override fun readNative(addr: Long): SkipWhile<I, P> =
                    SkipWhile(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + off1),
                        (typeLayoutP).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: SkipWhile<I, P>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off1, value.flag)
                    (typeLayoutP).writeNative(addr + off2, value.predicate)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SkipWhile<I, P> =
                    SkipWhile(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutP).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SkipWhile<I, P>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off1.toInt(), value.flag)
                    (typeLayoutP).writeBytes(buf, offset + off2.toInt(), value.predicate)
                }
            }

        public fun <I, P> new(iter: I, predicate: P): SkipWhile<I, P> = SkipWhile(iter = iter, flag = false, predicate = predicate)
    }
}

public fun <Item, I : Iterator<Item>> SkipWhile<I, P>.next(): Item? {
    fun <T> check(flag: RustMutableReference<Boolean>, pred: (T) -> Boolean): (T) -> Boolean {
        val flag1 = flag
        return { x ->
            if ((flag1).read() || !pred(x)) {
                (flag1).write(true)
                true
            } else {
                false
            }
        }
    }
    val flag = RustMutableReference(allocateNativeCell(this.flag, PrimitiveLayouts.BOOL), PrimitiveLayouts.BOOL)
    val pred = this.predicate
    return this.iter.indexOf(check(flag, pred)).takeIf { it >= 0 }
}

public fun <Item, I : Iterator<Item>> SkipWhile<I, P>.sizeHint(): Pair<Int, Int?> {
    val (_, upper) = this.iter.sizeHint()
    return Pair(0, upper)
    // can't know a lower bound, due to the predicate
}

public fun <Item, Residual, I : Iterator<Item>, Acc, R> SkipWhile<I, P>.tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
    var init1 = init
    if (!this.flag) {
        when (val matchVal = this.next()) {
            null -> { return init1 }
            else -> {
                val v = matchVal
                init1 = when (val tryResult = fold(init1, v)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
            }
        }
    }
    return this.iter.tryFold(init1, fold)
}

public fun <Item, I : Iterator<Item>, Acc> SkipWhile<I, P>.fold(init: Acc, fold: (Acc, Item) -> Acc): Acc {
    var init1 = init
    if (!this.flag) {
        when (val matchVal = this.next()) {
            null -> { return init1 }
            else -> {
                val v = matchVal
                init1 = fold(init1, v)
            }
        }
    }
    return this.iter.fold(init1, fold)
}

public fun <Source, P, I> SkipWhile<I, P>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY

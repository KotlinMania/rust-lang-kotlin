// port-lint: source iter/adapters/take_while.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.takewhile

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
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
 * An iterator that only accepts elements while `predicate` returns `true`.
 *
 * This `class` is created by the [takeWhile] method on [Iterator]. See its
 * documentation for more.
 *
 * [takeWhile]: Iterator.takeWhile
 * [Iterator]: trait.Iterator.html
 */
public open class TakeWhile<I, P>(
    public var iter: I,
    public var flag: Boolean,
    public var predicate: P,
) : TrustedFused,
    SourceIter<Source>,
    Iterable<Item> {
    override fun iterator(): Iterator<Item> =
        object : AbstractIterator<Item>() {
            override fun computeNext() {
                when (val v = this@TakeWhile.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): TakeWhile<I, P> = TakeWhile(iter, flag, predicate)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "TakeWhile").field("iter", this.iter).field("flag", this.flag).finish()

    public open fun next(): Item? {
        return if (this.flag) {
            null
        } else {
            val x = (this.iter.next() ?: return null)
            if ((this.predicate)(x)) {
                x
            } else {
                this.flag = true
                null
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.flag) {
            Pair(0, 0)
        } else {
            val (_, upper) = this.iter.sizeHint()
            Pair(0, upper)
            // can't know a lower bound, due to the predicate
        }

    public open fun <Acc, R> tryFold(init: Acc, fold: (Acc, I.Item) -> R): R where R : Try<R, Acc, Any?> {
        fun <T, Acc, R : Try<R, Acc, Any?>> check(flag: RustMutableReference<Boolean>, p: (T) -> Boolean, fold1: (Acc, T) -> R): (Acc, T) -> ControlFlow<R, Acc> {
            val flag1 = flag
            return { acc, x ->
                if (p(x)) {
                    ControlFlow.fromTry(fold1(acc, x))
                } else {
                    (flag1).write(true)
                    ControlFlow.Break(acc)
                }
            }
        }
        return if (this.flag) {
            init
        } else {
            val flag = this.flag
            val p = this.predicate
            this.iter.tryFold(init, check(flag, p, fold)).intoTry()
        }
    }

    public open fun <Aaa> fold(init: Aaa, fold: (Aaa, I.Item) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I, P> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutP: TypeLayout<P>): TypeLayout<TakeWhile<I, P>> =
            object : TypeLayout<TakeWhile<I, P>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutP.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 1L)
                private val off2: Long = alignUpBytes((off1 + 1L), typeLayoutP.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutP.sizeBytes), alignBytes)
                override fun readNative(addr: Long): TakeWhile<I, P> =
                    TakeWhile(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + off1),
                        (typeLayoutP).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: TakeWhile<I, P>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off1, value.flag)
                    (typeLayoutP).writeNative(addr + off2, value.predicate)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TakeWhile<I, P> =
                    TakeWhile(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutP).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TakeWhile<I, P>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off1.toInt(), value.flag)
                    (typeLayoutP).writeBytes(buf, offset + off2.toInt(), value.predicate)
                }
            }

        public fun <I, P> new(iter: I, predicate: P): TakeWhile<I, P> = TakeWhile(iter = iter, flag = false, predicate = predicate)
    }
}

public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int>? = I.MERGE_BY

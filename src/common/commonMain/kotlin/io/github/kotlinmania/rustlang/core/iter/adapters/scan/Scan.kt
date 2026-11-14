// port-lint: source iter/adapters/scan.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.scan

import io.github.kotlinmania.rustlang.core.iter.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
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
 * An iterator to maintain state while iterating another iterator.
 *
 * This `class` is created by the [scan] method on [Iterator]. See its
 * documentation for more.
 *
 * [scan]: Iterator.scan
 * [Iterator]: trait.Iterator.html
 */
public open class Scan<I, St, F>(
    public var iter: I,
    public var f: F,
    public var state: St,
) : SourceIter<Any?, Source>, InPlaceIterable, Iterable<B> {
    override fun iterator(): Iterator<B> = object : AbstractIterator<B>() {
        override fun computeNext() {
            when (val v = this@Scan.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Scan<I, St, F> = Scan(iter, f, state)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Scan").field("iter", this.iter).field("state", this.state).finish()

    public open fun next(): B? {
        val a = (this.iter.next() ?: return null)
        return (this.f)(this.state, a)
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (_, upper) = this.iter.sizeHint()
        return Pair(0, upper)
        // can't know a lower bound, due to the scan function
    }

    public open fun <Acc, R> tryFold(init: Acc, fold: (Acc, B) -> R): R where R : Try<R, Acc, Any?> {
        fun <T, St, B, Acc, R : Try<R, Acc, Any?>> scan(state: St, f: (St, T) -> B?, fold1: (Acc, B) -> R): (Acc, T) -> ControlFlow<R, Acc> =
            { acc, x -> when (val matchVal = f(state, x)) {
                null -> ControlFlow.Break(acc)
                else -> {
                    val x1 = matchVal
                    ControlFlow.fromTry(fold1(acc, x1))
                }
            } }
        val state = this.state
        val f = this.f
        return this.iter.tryFold(init, scan(state, f, fold)).intoTry()
    }

    public open fun <Aaa> fold(init: Aaa, fold: (Aaa, B) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I, St, F> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutF: TypeLayout<F>, typeLayoutSt: TypeLayout<St>): TypeLayout<Scan<I, St, F>> =
            object : TypeLayout<Scan<I, St, F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutF.alignBytes, typeLayoutSt.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutF.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutF.sizeBytes), typeLayoutSt.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutSt.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Scan<I, St, F> =
                    Scan(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutF).readNative(addr + off1),
                        (typeLayoutSt).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Scan<I, St, F>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutF).writeNative(addr + off1, value.f)
                    (typeLayoutSt).writeNative(addr + off2, value.state)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Scan<I, St, F> =
                    Scan(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutF).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutSt).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Scan<I, St, F>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutF).writeBytes(buf, offset + off1.toInt(), value.f)
                    (typeLayoutSt).writeBytes(buf, offset + off2.toInt(), value.state)
                }
            }

        internal fun <I, St, F> new(iter: I, state: St, f: F): Scan<I, St, F> = Scan(iter = iter, state = state, f = f)

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
    }
}

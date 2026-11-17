// port-lint: source iter/adapters/cloned.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.cloned

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

public typealias Source<I> = I.Source

/**
 * An iterator that clones the elements of an underlying iterator.
 *
 * This `class` is created by the [cloned] method on [Iterator]. See its
 * documentation for more.
 *
 * [cloned]: Iterator.cloned
 * [Iterator]: trait.Iterator.html
 */
public open class Cloned<I>(
    public var it: I,
) {
    public open fun clone(): Cloned<I> = Cloned(it)

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<Cloned<I>> =
            object : TypeLayout<Cloned<I>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutI.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Cloned<I> =
                    Cloned(
                        (typeLayoutI).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Cloned<I>) {
                    (typeLayoutI).writeNative(addr, value.it)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cloned<I> =
                    Cloned(
                        (typeLayoutI).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cloned<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.it)
                }
            }

        public fun <I> new(it: I): Cloned<I> = Cloned(it = it)

        /**
         * Creates a `Cloned` iterator from the default value of `I`
         * ```kotlin
         * val iter: Cloned<ArrayDeque<UByte>> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <I> default(defaultIFn: () -> I): Cloned<I> = Cloned.new(defaultIFn())
    }
}

public fun <T, Acc, R> cloneTryFold(f: (Acc, T) -> R): (Acc, T) -> R = { acc, elt -> f(acc, elt.clone()) }

public fun <I, T> Cloned<I>.next(): T? where I : Iterator<T> = this.it.next()

public fun <I, T> Cloned<I>.sizeHint(): Pair<Int, Int?> where I : Iterator<T> = this.it.sizeHint()

public fun <Residual, I, T, B, R> Cloned<I>.tryFold(init: B, f: (B, T) -> R): R where I : Iterator<T>, R : Try<R, B, Residual> = this.it.tryFold(init, cloneTryFold(f))

public fun <I, T, Acc> Cloned<I>.fold(init: Acc, f: (Acc, T) -> Acc, cloneTFn: (T) -> T = { it }): Acc where I : Iterator<T> = this.it.map(cloneTFn).fold(init, f)

public fun <I, T> Cloned<I>.iteratorGetUnchecked(idx: Int): T where I : Iterator<T> {
    // SAFETY: the caller must uphold the contract for
    // `Iterator.__iterator_get_unchecked`.
    return tryGetUnchecked(this.it, idx).clone()
}

public fun <I, T> Cloned<I>.nextBack(): T? = this.it.nextBack()

public fun <Residual, I, T, B, R> Cloned<I>.tryRfold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> = this.it.tryRfold(init, cloneTryFold(f))

public fun <I, T, Acc> Cloned<I>.rfold(init: Acc, f: (Acc, T) -> Acc, cloneTFn: (T) -> T = { it }): Acc = this.it.map(cloneTFn).rfold(init, f)

public fun <I, T> Cloned<I>.len(): Int = this.it.len()

public fun <I, T> Cloned<I>.isEmpty(): Boolean = this.it.isEmpty()

public val MAY_HAVE_SIDE_EFFECT: Boolean = true

public fun <Source, I> Cloned<I>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.it.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY

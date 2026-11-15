// port-lint: source iter/adapters/copied.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.copied

import io.github.kotlinmania.rustlang.array.iter.IntoIter
import io.github.kotlinmania.rustlang.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.ops.trytrait.Try
import io.github.kotlinmania.rustlang.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.slice.iter.Iter

public typealias Source<I> = I.Source

/**
 * An iterator that copies the elements of an underlying iterator.
 *
 * This `class` is created by the [copied] method on [Iterator]. See its
 * documentation for more.
 *
 * [copied]: Iterator.copied
 * [Iterator]: trait.Iterator.html
 */
public open class Copied<I>(
    public var it: I,
) : TrustedRandomAccess, TrustedRandomAccessNoCoerce, TrustedLen<Copied<I>>, SourceIter<Source>, InPlaceIterable, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Copied.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Copied<I> = Copied(it)

    public open fun intoInner(): I = this.it

    public override fun next(): T? = this.it.next()

    public override fun nextChunk(n: Int, specNextChunkIFn: (I) -> Result<List<T>, IntoIter<T>>): Result<List<T>, IntoIter<T>> = specNextChunkIFn(this.it)

    public override fun sizeHint(): Pair<Int, Int?> = this.it.sizeHint()

    public override fun <B, R> tryFold(init: B, f: (B, T) -> R): R where R : Try<R, B, Any?> = this.it.tryFold(init, copyTryFold(f))

    public override fun <Acc> fold(init: Acc, f: (Acc, T) -> Acc): Acc = this.it.fold(init, copyFold(f))

    public override fun nth(n: Int): T? = this.it.nth(n).copied()

    public override fun last(): T? = this.it.last()

    public override fun count(): Int = this.it.count()

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.it.advanceBy(n)

    public override fun iteratorGetUnchecked(idx: Int): T {
        // SAFETY: the caller must uphold the contract for
        // `Iterator.__iterator_get_unchecked`.
        return tryGetUnchecked(this.it, idx)
    }

    public open fun nextBack(): T? = this.it.nextBack()

    public open fun <B, R> tryRfold(init: B, f: (B, T) -> R): R where R : Try<R, B, Any?> = this.it.tryRfold(init, copyTryFold(f))

    public open fun <Acc> rfold(init: Acc, f: (Acc, T) -> Acc): Acc = this.it.rfold(init, copyFold(f))

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.it.advanceBackBy(n)

    public open fun len(): Int = this.it.len()

    public open fun isEmpty(): Boolean = this.it.isEmpty()

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.it.asInner()
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<Copied<I>> =
            object : TypeLayout<Copied<I>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutI.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Copied<I> =
                    Copied(
                        (typeLayoutI).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Copied<I>) {
                    (typeLayoutI).writeNative(addr, value.it)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Copied<I> =
                    Copied(
                        (typeLayoutI).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Copied<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.it)
                }
            }

        public fun <I> new(it: I): Copied<I> = Copied(it = it)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = I.MAY_HAVE_SIDE_EFFECT

        /**
         * Creates a `Copied` iterator from the default value of `I`
         * ```kotlin
         * val iter: Copied<ArrayDeque<UByte>> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <I> default(defaultIFn: () -> I): Copied<I> = Copied.new(defaultIFn())

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
    }
}

public fun <T, Acc> copyFold(f: (Acc, T) -> Acc): (Acc, T) -> Acc = { acc, elt -> f(acc, elt) }

public fun <T, Acc, R> copyTryFold(f: (Acc, T) -> R): (Acc, T) -> R = { acc, elt -> f(acc, elt) }

public interface SpecNextChunk<T, SelfType> : Iterator<T> {
    public fun specNextChunk(): Result<List<T>, IntoIter<T>>

    public fun next(): T?
}

public fun <I, T> I.specNextChunk(n: Int): Result<List<T>, IntoIter<T>> where I : Iterator<T> = iterNextChunk(this.copied())

public fun <T> Iter<T>.specNextChunk(n: Int): Result<List<T>, IntoIter<T>> {
    val rawArray = MutableList(n) { MaybeUninit.uninit(ObjectSlotLayout<Result<List<T>, IntoIter<T>>>()) }
    val len = this.len()
    if (T.IS_ZST) {
        if (len < n) {
            this.advanceBy(len)
            // SAFETY: ZSTs can be conjured ex nihilo; only the amount has to be correct
            return Result.Err(IntoIter.newUnchecked(rawArray, 0 until len))
        }
        this.advanceBy(n)
        // SAFETY: ditto
        return Result.Ok(MaybeUninit.arrayAssumeInit(rawArray))
    }
    if (len < n) {
        // SAFETY: `len` indicates that this many elements are available and we just checked that
        // it fits into the array.
        run {
            copyNonoverlapping(this.asRef().asPtr(), mutPtrFromRustCast(rawArray.asMutPtr()), len)
            this.advanceBy(len)
            return Result.Err(IntoIter.newUnchecked(rawArray, 0 until len))
        }
    }
    // SAFETY: `len` is larger than the array size. Copy a fixed amount here to fully initialize
    // the array.
    return run {
        copyNonoverlapping(this.asRef().asPtr(), mutPtrFromRustCast(rawArray.asMutPtr()), n)
        this.advanceBy(n)
        Result.Ok(MaybeUninit.arrayAssumeInit(rawArray))
    }
}

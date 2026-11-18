// port-lint: source iter/adapters/copied.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.copied

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter.Iter

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
) {
    public open fun clone(): Copied<I> = Copied(it)

    public open fun intoInner(): I = this.it

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

        /**
         * Creates a `Copied` iterator from the default value of `I`
         * ```kotlin
         * val iter: Copied<ArrayDeque<UByte>> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <I> default(defaultIFn: () -> I): Copied<I> = Copied.new(defaultIFn())
    }
}

public fun <T, Acc> copyFold(f: (Acc, T) -> Acc): (Acc, T) -> Acc = { acc, elt -> f(acc, elt) }

public fun <T, Acc, R> copyTryFold(f: (Acc, T) -> R): (Acc, T) -> R = { acc, elt -> f(acc, elt) }

public fun <I, T> Copied<I>.next(): T? where I : Iterator<T> = this.it.next()

public fun <I, T> Copied<I>.nextChunk(n: Int = 0, specNextChunkIFn: (I) -> Result<List<T>, IntoIter<T>>): Result<List<T>, IntoIter<T>> where I : Iterator<T> = specNextChunkIFn(this.it)

public fun <I, T> Copied<I>.sizeHint(): Pair<Int, Int?> where I : Iterator<T> = this.it.sizeHint()

public fun <Residual, I, T, B, R> Copied<I>.tryFold(init: B, f: (B, T) -> R): R where I : Iterator<T>, R : Try<R, B, Residual> = this.it.tryFold(init, copyTryFold(f))

public fun <I, T, Acc> Copied<I>.fold(init: Acc, f: (Acc, T) -> Acc): Acc where I : Iterator<T> = this.it.fold(init, copyFold(f))

public fun <I, T> Copied<I>.nth(n: Int): T? where I : Iterator<T> = this.it.nth(n).copied()

public fun <I, T> Copied<I>.last(): T? where I : Iterator<T> = this.it.last()

public fun <I, T> Copied<I>.count(): Int where I : Iterator<T> = this.it.count()

public fun <I, T> Copied<I>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> where I : Iterator<T> = this.it.advanceBy(n)

public fun <I, T> Copied<I>.iteratorGetUnchecked(idx: Int): T where I : Iterator<T> {
    // SAFETY: the caller must uphold the contract for
    // `Iterator.__iterator_get_unchecked`.
    return tryGetUnchecked(this.it, idx)
}

public fun <I, T> Copied<I>.nextBack(): T? where I : Iterator<T> = this.it.nextBack()

public fun <Residual, I, T, B, R> Copied<I>.tryRfold(init: B, f: (B, T) -> R): R where I : Iterator<T>, R : Try<R, B, Residual> = this.it.tryRfold(init, copyTryFold(f))

public fun <I, T, Acc> Copied<I>.rfold(init: Acc, f: (Acc, T) -> Acc): Acc where I : Iterator<T> = this.it.rfold(init, copyFold(f))

public fun <I, T> Copied<I>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> where I : Iterator<T> = this.it.advanceBackBy(n)

public fun <I, T> Copied<I>.len(): Int where I : Iterator<T> = this.it.len()

public fun <I, T> Copied<I>.isEmpty(): Boolean where I : Iterator<T> = this.it.isEmpty()

public val MAY_HAVE_SIDE_EFFECT: Boolean = I.MAY_HAVE_SIDE_EFFECT

public interface SpecNextChunk<T> : Iterator<T> {
    public fun specNextChunk(): Result<List<T>, IntoIter<T>>

    public fun next(): T?
}

public fun <I, T> I.specNextChunk(n: Int = 0): Result<List<T>, IntoIter<T>> where I : Iterator<T> = iterNextChunk(this.copied(), n)

public fun <T> Iter<T>.specNextChunk(n: Int = 0, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<List<T>, IntoIter<T>> {
    val rawArray = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<Result<List<T>, IntoIter<T>>>()) }
    val len = this.len()
    if (((typeLayoutT).sizeBytes == 0L)) {
        if (len < n) {
            this.advanceBy(len)
            // SAFETY: ZSTs can be conjured ex nihilo; only the amount has to be correct
            return Result.Err<List<T>, IntoIter<T>>(IntoIter.newUnchecked(rawArray, 0 until len, n))
        }
        this.advanceBy(n)
        // SAFETY: ditto
        return Result.Ok<List<T>, IntoIter<T>>(MaybeUninit.arrayAssumeInit(rawArray, n))
    }
    if (len < n) {
        // SAFETY: `len` indicates that this many elements are available and we just checked that
        // it fits into the array.
        run {
            copyNonoverlapping(this.asRef().asPtr(), mutPtrFromRustCast(rawArray.asMutPtr()), len)
            this.advanceBy(len)
            return Result.Err<List<T>, IntoIter<T>>(IntoIter.newUnchecked(rawArray, 0 until len, n))
        }
    }
    // SAFETY: `len` is larger than the array size. Copy a fixed amount here to fully initialize
    // the array.
    return run {
        copyNonoverlapping(this.asRef().asPtr(), mutPtrFromRustCast(rawArray.asMutPtr()), n)
        this.advanceBy(n)
        Result.Ok<List<T>, IntoIter<T>>(MaybeUninit.arrayAssumeInit(rawArray, n))
    }
}

public fun <Source, I> Copied<I>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.it.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY

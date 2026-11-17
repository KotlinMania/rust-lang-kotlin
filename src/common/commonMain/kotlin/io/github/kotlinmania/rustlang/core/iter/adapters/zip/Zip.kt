// port-lint: source iter/adapters/zip.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.zip

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
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

public typealias Source<A> = A.Source

/**
 * An iterator that iterates two other iterators simultaneously.
 *
 * This `class` is created by [zip] or [Iterator.zip].
 * See their documentation for more.
 */
public open class Zip<A, B>(
    public var a: A,
    public var b: B,
    // index, len and aLen are only used by the specialized version of zip
    public var index: Int,
    public var len: Int,
) : ZipImpl<A, B, Zip<A, B>, Pair<Item, Item>>, ZipFmt<A, B>, SpecFold, Iterable<Pair<Item, Item>> {
    override fun iterator(): Iterator<Pair<Item, Item>> = object : AbstractIterator<Pair<Item, Item>>() {
        override fun computeNext() {
            when (val v = this@Zip.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Zip<A, B> = Zip(a, b, index, len)

    public open fun superNth(n: Int): Pair<Item, Item>? {
        var n1 = n
        while (true) {
            val matchVal = this.next()
            if (matchVal == null) break
            val x = matchVal
            if (n1 == 0) {
                return x
            }
            n1 -= 1
        }
        return null
    }

    public override fun next(): Pair<Item, Item>? = this.next()

    public override fun sizeHint(): Pair<Int, Int?> = this.sizeHint()

    public override fun nth(n: Int): Pair<Item, Item>? = this.nth(n)

    public override fun <Acc> fold(init: Acc, f: (Acc, Pair<Item, Item>) -> Acc): Acc = this.fold(init, f)

    public open fun iteratorGetUnchecked(idx: Int): Pair<Item, Item> {
        // SAFETY: `ZipImpl.__iterator_get_unchecked` has same safety
        // requirements as `Iterator.__iterator_get_unchecked`.
        return this.getUnchecked(idx)
    }

    public override fun nextBack(): Pair<Item, Item>? = this.nextBack()

    // General Zip impl
    public open fun nextAB(): Pair<Item, Item>? {
        val x = (this.a.next() ?: return null)
        val y = (this.b.next() ?: return null)
        return Pair(x, y)
    }

    public open fun nthAB(n: Int): Pair<Item, Item>? = this.superNth(n)

    public open fun nextBackAB(): Pair<Item, Item>? {
        val aSz = this.a.len()
        val bSz = this.b.len()
        if (aSz != bSz) {
            if (aSz > bSz) {
                for (unused in 0 until aSz - bSz) {
                    this.a.nextBack()
                }
            } else {
                for (unused1 in 0 until bSz - aSz) {
                    this.b.nextBack()
                }
            }
        }
        return run {
            val matchVal = Pair(this.a.nextBack(), this.b.nextBack())
            when {
                matchVal.first != null && matchVal.second != null -> {
                    val x = matchVal.first
                    val y = matchVal.second
                    Pair(x, y)
                }
                matchVal.first == null && matchVal.second == null -> null
                else -> error("unreachable")
            }
        }
    }

    public open fun sizeHintAB(): Pair<Int, Int?> {
        val (aLower, aUpper) = this.a.sizeHint()
        val (bLower, bUpper) = this.b.sizeHint()
        val lower = minOf(aLower, bLower)
        val upper =
            run {
                val matchVal = Pair(aUpper, bUpper)
                when {
                    matchVal.first != null && matchVal.second != null -> {
                        val x = matchVal.first
                        val y = matchVal.second
                        minOf(x, y)
                    }
                    matchVal.first != null && matchVal.second == null -> {
                        val x1 = matchVal.first
                        x1
                    }
                    matchVal.first == null && matchVal.second != null -> {
                        val y1 = matchVal.second
                        y1
                    }
                    else -> null
                }
            }
        return Pair(lower, upper)
    }

    public override fun getUnchecked(idx: Int): Item {
        error("Always specialized")
    }

    public open fun <Acc> foldAB(init: Acc, f: (Acc, Pair<Item, Item>) -> Acc): Acc = this.specFold(init, f)

    public override fun fmt(f: StringBuilder): Result<Unit, Unit> = this.fmt(f)

    public open fun fmtAB(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Zip").field("a", this.a).field("b", this.b).finish(); Result.Ok<Unit, Error>(Unit) }

    // Adapted from default impl from the Iterator trait
    public override fun <Acc> specFold(init: Acc, f: (Acc, Pair<Item, Item>) -> Acc): Acc {
        var accum = init
        while (true) {
            val matchVal = this.next()
            if (matchVal == null) break
            val x = matchVal
            accum = f(accum, x)
        }
        return accum
    }

    public companion object {
        public fun <A, B> typeLayout(typeLayoutA: TypeLayout<A>, typeLayoutB: TypeLayout<B>): TypeLayout<Zip<A, B>> =
            object : TypeLayout<Zip<A, B>> {
                override val alignBytes: Long = listOf(8L, typeLayoutA.alignBytes, typeLayoutB.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutA.sizeBytes, typeLayoutB.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutB.sizeBytes), 8L)
                private val off3: Long = alignUpBytes((off2 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off3 + 8L), alignBytes)
                override fun readNative(addr: Long): Zip<A, B> =
                    Zip(
                        (typeLayoutA).readNative(addr),
                        (typeLayoutB).readNative(addr + off1),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off2),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off3),
                    )
                override fun writeNative(addr: Long, value: Zip<A, B>) {
                    (typeLayoutA).writeNative(addr, value.a)
                    (typeLayoutB).writeNative(addr + off1, value.b)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off2, value.index)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off3, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Zip<A, B> =
                    Zip(
                        (typeLayoutA).readBytes(buf, offset),
                        (typeLayoutB).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off3.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Zip<A, B>) {
                    (typeLayoutA).writeBytes(buf, offset, value.a)
                    (typeLayoutB).writeBytes(buf, offset + off1.toInt(), value.b)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off2.toInt(), value.index)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off3.toInt(), value.len)
                }
            }

        public fun <A, B> new(a: A, b: B): Zip<A, B> = ZipImpl.new(a, b)

        public fun <A, B> newAB(a: A, b: B): Zip<A, B> = Zip(a = a, b = b, index = 0, len = 0)
    }
}

/**
 * Converts the arguments to iterators and zips them.
 *
 * See the documentation of [Iterator.zip] for more.
 *
 * # Examples
 *
 * ```kotlin
 * import std.iter.zip
 *
 * val xs = listOf(1, 2, 3)
 * val ys = listOf(4, 5, 6)
 * var iter = zip(xs, ys)
 * check(iter.next()!! == Pair(1, 4))
 * check(iter.next()!! == Pair(2, 5))
 * check(iter.next()!! == Pair(3, 6))
 * check(iter.next() == null)
 * // Nested zips are also possible:
 * val zs = listOf(7, 8, 9)
 * var iter1 = zip(zip(xs, ys), zs)
 * check(iter1.next()!! == Pair(Pair(1, 4), 7))
 * check(iter1.next()!! == Pair(Pair(2, 5), 8))
 * check(iter1.next()!! == Pair(Pair(3, 6), 9))
 * check(iter1.next() == null)
 * ```
 */
public fun <A, B> zip(a: A, b: B): Zip<IntoIter, IntoIter> = ZipImpl.new(a.iterator(), b.iterator())

// Zip specialization trait
public interface ZipImpl<A, B, T, Item> {
    public fun new(a: A, b: B): T

    public fun next(): Item?

    public fun sizeHint(): Pair<Int, Int?>

    public fun nth(n: Int): Item?

    public fun nextBack(): Item?

    public fun <Acc> fold(init: Acc, f: (Acc, Item) -> Acc): Acc

    public fun getUnchecked(idx: Int): Item
}

public fun <A, B> zipImplNewZipAB(a: A, b: B): Zip<A, B> where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce = Zip(a = a, b = b, index = 0, len = 0)

public fun <A, B> Zip<A, B>.next(): Pair<Item, Item>? where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce {
    val x = (this.a.next() ?: return null)
    val y = (this.b.next() ?: return null)
    return Pair(x, y)
}

public fun <A, B> Zip<A, B>.nth(n: Int): Pair<Item, Item>? where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce = this.superNth(n)

public fun <A, B> Zip<A, B>.nextBack(): Pair<Item, Item>? where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce {
    val aSz = this.a.len()
    val bSz = this.b.len()
    if (aSz != bSz) {
        if (aSz > bSz) {
            for (unused in 0 until aSz - bSz) {
                this.a.nextBack()
            }
        } else {
            for (unused1 in 0 until bSz - aSz) {
                this.b.nextBack()
            }
        }
    }
    return run {
        val matchVal = Pair(this.a.nextBack(), this.b.nextBack())
        when {
            matchVal.first != null && matchVal.second != null -> {
                val x = matchVal.first
                val y = matchVal.second
                Pair(x, y)
            }
            matchVal.first == null && matchVal.second == null -> null
            else -> error("unreachable")
        }
    }
}

public fun <A, B> Zip<A, B>.sizeHint(): Pair<Int, Int?> where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce {
    val size = minOf(this.a.size(), this.b.size())
    return Pair(size, size)
}

public fun <A, B> Zip<A, B>.getUnchecked(idx: Int): Item where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce {
    val idx1 = this.index + idx
    // SAFETY: the caller must uphold the contract for
    // `Iterator.__iterator_get_unchecked`.
    return Pair(this.a.iteratorGetUnchecked(idx1), this.b.iteratorGetUnchecked(idx1))
}

public fun <A, B, Acc> Zip<A, B>.fold(init: Acc, f: (Acc, Pair<Item, Item>) -> Acc): Acc where A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce {
    var accum = init
    val len = this.sizeHint().first
    for (i in 0 until len) {
        // SAFETY: since Self: TrustedRandomAccessNoCoerce we can trust the size-hint to
        // calculate the length and then use that to do unchecked iteration.
        // fold consumes the iterator so we don't need to fixup any state.
        run {
            accum = f(accum, this.getUnchecked(i))
        }
    }
    return accum
}

public fun <A, B> Zip<A, B>.nextAB(): Pair<Item, Item>? where A : TrustedRandomAccess, B : TrustedRandomAccess =
    if (this.index < this.len) {
        val i = this.index
        // since getUnchecked executes code which can panic we increment the counters beforehand
        // so that the same index won't be accessed twice, as required by TrustedRandomAccess
        this.index += 1
        // SAFETY: `i` is smaller than `self.len`, thus smaller than `self.a.len()` and `self.b.len()`
        Pair(this.a.iteratorGetUnchecked(i), this.b.iteratorGetUnchecked(i))
    } else {
        null
    }

public fun <A, B> Zip<A, B>.sizeHintAB(): Pair<Int, Int?> where A : TrustedRandomAccess, B : TrustedRandomAccess {
    val len = this.len - this.index
    return Pair(len, len)
}

public fun <A, B> Zip<A, B>.nthAB(n: Int): Pair<Item, Item>? where A : TrustedRandomAccess, B : TrustedRandomAccess {
    val delta = minOf(n, this.len - this.index)
    val end = this.index + delta
    while (this.index < end) {
        val i = this.index
        // since getUnchecked executes code which can panic we increment the counters beforehand
        // so that the same index won't be accessed twice, as required by TrustedRandomAccess
        this.index += 1
        if (A.MAY_HAVE_SIDE_EFFECT) {
            // SAFETY: the usage of `cmp.min` to calculate `delta`
            // ensures that `end` is smaller than or equal to `self.len`,
            // so `i` is also smaller than `self.len`.
            run {
                this.a.iteratorGetUnchecked(i)
            }
        }
        if (B.MAY_HAVE_SIDE_EFFECT) {
            // SAFETY: same as above.
            run {
                this.b.iteratorGetUnchecked(i)
            }
        }
    }
    return this.superNth(n - delta)
}

public fun <A, B> Zip<A, B>.nextBackAB(): Pair<Item, Item>? where A : TrustedRandomAccess, B : TrustedRandomAccess {
    // No effects when the iterator is exhausted, to reduce the number of
    // cases the unchecked code has to handle.
    // See #137255 for a case where where too many epicycles lead to unsoundness.
    return if (this.index < this.len) {
        val oldLen = this.len
        // since getUnchecked and the side-effecting code can execute user code
        // which can panic we decrement the counter beforehand
        // so that the same index won't be accessed twice, as required by TrustedRandomAccess.
        // Additionally this will ensure that the side-effects code won't run a second time.
        this.len -= 1
        // Adjust a, b to equal length if we're iterating backwards.
        if (A.MAY_HAVE_SIDE_EFFECT || B.MAY_HAVE_SIDE_EFFECT) {
            // note if some forward-iteration already happened then these aren't the real
            // remaining lengths of the inner iterators, so we have to relate them to
            // Zip's internal length-tracking.
            val szA = this.a.size()
            val szB = this.b.size()
            // This condition can and must only be true on the first `nextBack` call,
            // otherwise we will break the restriction on calls to `self.nextBack()`
            // after calling `getUnchecked()`.
            if (szA != szB && (oldLen == szA || oldLen == szB)) {
                if (A.MAY_HAVE_SIDE_EFFECT && szA > oldLen) {
                    for (unused in 0 until szA - oldLen) {
                        this.a.nextBack()
                    }
                }
                if (B.MAY_HAVE_SIDE_EFFECT && szB > oldLen) {
                    for (unused1 in 0 until szB - oldLen) {
                        this.b.nextBack()
                    }
                }
                if (false) {
                    when (val matchVal = Pair(this.a.size(), this.b.size())) {
                        else -> {
                            val leftVal = matchVal.first
                            val rightVal = matchVal.second
                            if (!(leftVal == rightVal)) {
                                val kind = AssertKind.Eq
                                assertFailed(kind, leftVal, rightVal, Option.None)
                            }
                        }
                    }
                }
            }
        }
        val i = this.len
        // SAFETY: `i` is smaller than the previous value of `self.len`,
        // which is also smaller than or equal to `self.a.len()` and `self.b.len()`
        Pair(this.a.iteratorGetUnchecked(i), this.b.iteratorGetUnchecked(i))
    } else {
        null
    }
}

public val MAY_HAVE_SIDE_EFFECT: Boolean = A.MAY_HAVE_SIDE_EFFECT || B.MAY_HAVE_SIDE_EFFECT

// Arbitrarily selects the left side of the zip iteration as extractable "source"
// it would require negative trait bounds to be able to try both
public fun <Source, A, B> Zip<A, B>.asInner(): Source where A : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.a.asInner()
}

// Since SourceIter forwards the left hand side we do the same here
public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = A.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = A.MERGE_BY

public interface ZipFmt<A, B> {
    public fun fmt(f: StringBuilder): Result<Unit, Error>
}

public fun <A : TrustedRandomAccessNoCoerce, B : TrustedRandomAccessNoCoerce> Zip<A, B>.fmt(f: StringBuilder): Result<Unit, Error> {
    // It's *not safe* to call fmt on the contained iterators, since once
    // we start iterating they're in strange, potentially unchecked, states.
    return run { DebugStruct(f, "Zip").finish(); Result.Ok<Unit, Error>(Unit) }
}

//
// Note: Clarify interaction with SourceIter/InPlaceIterable. Calling `SourceIter.asInner`
// after `__iterator_get_unchecked` is supposed to be allowed.

/**
 * An iterator whose items are random-accessible efficiently
 *
 * # Safety
 *
 * The iterator's `sizeHint` must be exact and cheap to call.
 *
 * `TrustedRandomAccessNoCoerce.size` may not be overridden.
 *
 * All subtypes and all supertypes of `Self` must also implement `TrustedRandomAccess`.
 * In particular, this means that types with non-invariant parameters usually can not have
 * an impl for `TrustedRandomAccess` that depends on any trait bounds on such parameters, except
 * for bounds that come from the respective struct/enum definition itself, or bounds involving
 * traits that themselves come with a guarantee similar to this one.
 *
 * If `Self: ExactSizeIterator` then `self.len()` must always produce results consistent
 * with `self.size()`.
 *
 * If `Self: Iterator`, then `<Self as Iterator>.__iterator_get_unchecked(self, idx)`
 * must be safe to call provided the following conditions are met.
 *
 * 1. `0 <= idx` and `idx<self.size()`.
 * 2. If `Self: !Clone`, then `self.__iterator_get_unchecked(idx)` is never called with the same
 *    index on `self` more than once.
 * 3. After `self.__iterator_get_unchecked(idx)` has been called, then `self.nextBack()` will
 *    only be called at most `self.size() - idx - 1` times. If `Self: Clone` and `self` is cloned,
 *    then this number is calculated for `self` and its clone individually,
 *    but `self.nextBack()` calls that happened before the cloning count for both `self` and the clone.
 * 4. After `self.__iterator_get_unchecked(idx)` has been called, then only the following methods
 *    will be called on `self` or on any new clones of `self`:
 *     * `std.clone.Clone.clone`
 *     * `std.iter.Iterator.sizeHint`
 *     * `std.iter.DoubleEndedIterator.nextBack`
 *     * `std.iter.ExactSizeIterator.len`
 *     * `std.iter.Iterator.__iterator_get_unchecked`
 *     * `std.iter.TrustedRandomAccessNoCoerce.size`
 * 5. If `Self` is a subtype of `T`, then `self` is allowed to be coerced
 *    to `T`. If `self` is coerced to `T` after `self.__iterator_get_unchecked(idx)` has already
 *    been called, then no methods except for the ones listed under 4. are allowed to be called
 *    on the resulting value of type `T`, either. Multiple such coercion steps are allowed.
 *    Regarding 2. and 3., the number of times `__iterator_get_unchecked(idx)` or `nextBack()` is
 *    called on `self` and the resulting value of type `T` (and on further coercion results with
 *    super-supertypes) are added together and their sums must not exceed the specified bounds.
 *
 * Further, given that these conditions are met, it must guarantee that:
 *
 * * It does not change the value returned from `sizeHint`
 * * It must be safe to call the methods listed above on `self` after calling
 *   `self.__iterator_get_unchecked(idx)`, assuming that the required traits are implemented.
 * * It must also be safe to drop `self` after calling `self.__iterator_get_unchecked(idx)`.
 * * If `Self` is a subtype of `T`, then it must be safe to coerce `self` to `T`.
 */
public interface TrustedRandomAccess : TrustedRandomAccessNoCoerce

/**
 * Like [TrustedRandomAccess] but without any of the requirements / guarantees around
 * coercions to supertypes after `__iterator_get_unchecked` (they aren’t allowed here!), and
 * without the requirement that subtypes / supertypes implement `TrustedRandomAccessNoCoerce`.
 *
 * This trait was created in PR #85874 to fix soundness issue #85873 without performance regressions.
 * It is subject to change as we might want to build a more generally useful (for performance
 * optimizations) and more sophisticated trait or trait hierarchy that replaces or extends
 * [TrustedRandomAccess] and `TrustedRandomAccessNoCoerce`.
 */
public interface TrustedRandomAccessNoCoerce {
    public fun size(): Int = this.sizeHint().first

    /**
     * `true` if getting an iterator element may have side effects.
     * Remember to take inner iterators into account.
     */
    public val MAY_HAVE_SIDE_EFFECT: Boolean
}

/**
 * Like `Iterator.__iterator_get_unchecked`, but doesn't require the compiler to
 * know that `U: TrustedRandomAccess`.
 *
 * ## Safety
 *
 * Same requirements calling `getUnchecked` directly.
 */
public fun <I> tryGetUnchecked(it: I, idx: Int): Item {
    // SAFETY: the caller must uphold the contract for
    // `Iterator.__iterator_get_unchecked`.
    return it.tryGetUnchecked(idx)
}

public interface SpecTrustedRandomAccess {
    /**
     * If `Self: TrustedRandomAccess`, it must be safe to call
     * `Iterator.__iterator_get_unchecked(self, index)`.
     */
    public fun tryGetUnchecked(index: Int): Item
}

public fun <I> I.tryGetUnchecked(arg0: Int): Item {
    error("Should only be called on TrustedRandomAccess iterators")
}

public interface SpecFold {
    public fun <B> specFold(init: B, f: (B, Item) -> B): B
}

public fun <Item, A : TrustedLen<Item>, B : TrustedLen<Item>, Acc> Zip<A, B>.specFold(init: Acc, f: (Acc, Pair<Item, Item>) -> Acc): Acc {
    var accum = init
    while (true) {
        val (upper1, more) =
            when (val matchVal = this.sizeHint().second) {
                null -> {
                    // Per TrustedLen contract a None upper bound means more than usize.Max items
                    Pair(Int.MAX_VALUE, true)
                }
                else -> {
                    val upper = matchVal
                    Pair(upper, false)
                }
            }
        for (unused in 0 until upper1) {
            val pair = Pair(this.a.next().unwrapUnchecked(), this.b.next().unwrapUnchecked())
            accum = f(accum, pair)
        }
        if (!more) {
            break
        }
    }
    return accum
}

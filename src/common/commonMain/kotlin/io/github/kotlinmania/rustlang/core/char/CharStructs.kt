package io.github.kotlinmania.rustlang.core.char

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.escape.AlwaysEscaped
import io.github.kotlinmania.rustlang.core.escape.EscapeIterInner
import io.github.kotlinmania.rustlang.core.escape.MaybeEscaped
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.iteratorGetUnchecked as underlyingIteratorGetUnchecked

/**
 * Returns an iterator that yields the hexadecimal Unicode escape of a
 * character, as `char`s.
 *
 * This `class` is created by the [escapeUnicode] method on [char]. See
 * its documentation for more.
 *
 * [escapeUnicode]: char.escapeUnicode
 */
public open class EscapeUnicode internal constructor(
    valueCtorArg: EscapeIterInner<AlwaysEscaped>,
) : Iterable<Char> {
    public var value: EscapeIterInner<AlwaysEscaped> = valueCtorArg
        internal set

    public var field0: EscapeIterInner<AlwaysEscaped>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeUnicode.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeUnicode = EscapeUnicode(value)

    public open fun next(): Char? = this.value.next()?.let(Char::from)

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.value.len()
        return Pair(n, n)
    }

    public open fun count(): Int = this.value.len()

    public open fun last(): Char? = this.value.nextBack()?.let(Char::from)

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun len(): Int = this.value.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: EscapeIterInner<AlwaysEscaped>,
        ): EscapeUnicode = EscapeUnicode(
            valueCtorArg = value,
        )

        public fun new(c: Char): EscapeUnicode = EscapeUnicode(EscapeIterInner.unicode(c))
    }
}

/**
 * An iterator that yields the literal escape code of a `char`.
 *
 * This `class` is created by the [escapeDefault] method on [char]. See
 * its documentation for more.
 *
 * [escapeDefault]: char.escapeDefault
 */
public open class EscapeDefault internal constructor(
    valueCtorArg: EscapeIterInner<AlwaysEscaped>,
) : Iterable<Char> {
    public var value: EscapeIterInner<AlwaysEscaped> = valueCtorArg
        internal set

    public var field0: EscapeIterInner<AlwaysEscaped>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeDefault.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeDefault = EscapeDefault(value)

    public open fun next(): Char? = this.value.next()?.let(Char::from)

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.value.len()
        return Pair(n, n)
    }

    public open fun count(): Int = this.value.len()

    public open fun last(): Char? = this.value.nextBack()?.let(Char::from)

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun len(): Int = this.value.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: EscapeIterInner<AlwaysEscaped>,
        ): EscapeDefault = EscapeDefault(
            valueCtorArg = value,
        )

        public fun printable(c: io.github.kotlinmania.rustlang.core.ascii.Char): EscapeDefault = EscapeDefault(EscapeIterInner.ascii(c.toU8()))

        public fun backslash(c: io.github.kotlinmania.rustlang.core.ascii.Char): EscapeDefault = EscapeDefault(EscapeIterInner.backslash(c))

        public fun unicode(c: Char): EscapeDefault = EscapeDefault(EscapeIterInner.unicode(c))
    }
}

/**
 * An iterator that yields the literal escape code of a `char`.
 *
 * This `class` is created by the [escapeDebug] method on [char]. See its
 * documentation for more.
 *
 * [escapeDebug]: char.escapeDebug
 */
public open class EscapeDebug internal constructor(
    valueCtorArg: EscapeIterInner<MaybeEscaped>,
) : Iterable<Char> {
    public var value: EscapeIterInner<MaybeEscaped> = valueCtorArg
        internal set

    public var field0: EscapeIterInner<MaybeEscaped>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeDebug.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeDebug = EscapeDebug(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> {
        val n = this.len()
        return Pair(n, n)
    }

    public open fun count(): Int = this.len()

    public open fun len(): Int = this.value.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: EscapeIterInner<MaybeEscaped>,
        ): EscapeDebug = EscapeDebug(
            valueCtorArg = value,
        )

        public fun printable(chr: Char): EscapeDebug = EscapeDebug(EscapeIterInner.printable(chr))

        public fun backslash(c: io.github.kotlinmania.rustlang.core.ascii.Char): EscapeDebug = EscapeDebug(EscapeIterInner.backslash(c))

        public fun unicode(c: Char): EscapeDebug = EscapeDebug(EscapeIterInner.unicode(c))
    }
}

/**
 * Returns an iterator that yields the uppercase equivalent of a `char`.
 *
 * This `class` is created by the [toUppercase] method on [char]. See
 * its documentation for more.
 *
 * [toUppercase]: char.toUppercase
 */
public open class ToUppercase(
    public var value: CaseMappingIter,
) : TrustedLen, TrustedRandomAccessNoCoerce, TrustedRandomAccess, Iterable<Char> {
    public var field0: CaseMappingIter
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@ToUppercase.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): ToUppercase = ToUppercase(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.value.fold(init, fold)

    public open fun count(): Int = this.value.count()

    public open fun last(): Char? = this.value.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): Char = this.value.underlyingIteratorGetUnchecked(idx)

    public open fun nextBack(): Char? = this.value.nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, Char) -> Acc): Acc = this.value.rfold(init, rfold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * Returns an iterator that yields the titlecase equivalent of a `char`.
 *
 * This `class` is created by the [toTitlecase] method on [char]. See
 * its documentation for more.
 *
 * [toTitlecase]: char.toTitlecase
 */
public open class ToTitlecase(
    public var value: CaseMappingIter,
) : TrustedLen, TrustedRandomAccessNoCoerce, TrustedRandomAccess, Iterable<Char> {
    public var field0: CaseMappingIter
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@ToTitlecase.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): ToTitlecase = ToTitlecase(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.value.fold(init, fold)

    public open fun count(): Int = this.value.count()

    public open fun last(): Char? = this.value.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): Char = this.value.underlyingIteratorGetUnchecked(idx)

    public open fun nextBack(): Char? = this.value.nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, Char) -> Acc): Acc = this.value.rfold(init, rfold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * Returns an iterator that yields the lowercase equivalent of a `char`.
 *
 * This `class` is created by the [toLowercase] method on [char]. See
 * its documentation for more.
 *
 * [toLowercase]: char.toLowercase
 */
public open class ToLowercase(
    public var value: CaseMappingIter,
) : TrustedLen, TrustedRandomAccessNoCoerce, TrustedRandomAccess, Iterable<Char> {
    public var field0: CaseMappingIter
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@ToLowercase.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): ToLowercase = ToLowercase(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.value.fold(init, fold)

    public open fun count(): Int = this.value.count()

    public open fun last(): Char? = this.value.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): Char = this.value.underlyingIteratorGetUnchecked(idx)

    public open fun nextBack(): Char? = this.value.nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, Char) -> Acc): Acc = this.value.rfold(init, rfold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * Returns an iterator that yields the case-folded equivalent of a `char`.
 *
 * This `class` is created by the [toCasefoldUnnormalized] method on [char]. See
 * its documentation for more.
 *
 * [toCasefoldUnnormalized]: char.toCasefoldUnnormalized
 */
public open class ToCasefold(
    public var value: CaseMappingIter,
) : TrustedLen, TrustedRandomAccessNoCoerce, TrustedRandomAccess, Iterable<Char> {
    public var field0: CaseMappingIter
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@ToCasefold.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): ToCasefold = ToCasefold(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.value.fold(init, fold)

    public open fun count(): Int = this.value.count()

    public open fun last(): Char? = this.value.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): Char = this.value.underlyingIteratorGetUnchecked(idx)

    public open fun nextBack(): Char? = this.value.nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, Char) -> Acc): Acc = this.value.rfold(init, rfold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.value).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

public open class CaseMappingIter internal constructor(
    valueCtorArg: IntoIter<Char>,
) : TrustedLen, TrustedRandomAccessNoCoerce, TrustedRandomAccess, Iterable<Char> {
    public var value: IntoIter<Char> = valueCtorArg
        internal set

    public var field0: IntoIter<Char>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@CaseMappingIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): CaseMappingIter = CaseMappingIter(value)

    public open fun next(): Char? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.value.fold(init, fold)

    public open fun count(): Int = this.value.count()

    public open fun last(): Char? = this.value.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

    public open fun iteratorGetUnchecked(idx: Int): Char {
        // SAFETY: just forwarding requirements to caller
        return this.value.underlyingIteratorGetUnchecked(idx)
    }

    public open fun nextBack(): Char? = this.value.nextBack()

    public open fun <Acc> rfold(init: Acc, rfold: (Acc, Char) -> Acc): Acc = this.value.rfold(init, rfold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    // SAFETY: forwards to inner `array.IntoIter`

    // SAFETY: `CaseMappingIter` has no subtypes/supertypes

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        for (c in this.value.clone()) {
            f.append(c)
        }
        return Result.Ok<Unit, Unit>(Unit)
    }

    public companion object {
        public operator fun invoke(
            value: IntoIter<Char>,
        ): CaseMappingIter = CaseMappingIter(
            valueCtorArg = value,
        )

        public fun new(chars: List<Char>): CaseMappingIter {
            val iter = ArrayDeque((chars).toList())
            if (chars[2] == '\u0000') {
                iter.removeLastOrNull()
                if (chars[1] == '\u0000') {
                    iter.removeLastOrNull()
                    // Deliberately don't check `chars[0]`,
                    // as '\0' lowercases to itself
                }
            }
            return CaseMappingIter(iter)
        }

        // SAFETY: forwards to inner `array.IntoIter`
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

/**
 * The error type returned when a checked char conversion fails.
 */
public open class TryFromCharError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TryFromCharError = TryFromCharError(value)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append("unicode code point out of range"); Result.Ok<Unit, Unit>(Unit) }

    public companion object
}

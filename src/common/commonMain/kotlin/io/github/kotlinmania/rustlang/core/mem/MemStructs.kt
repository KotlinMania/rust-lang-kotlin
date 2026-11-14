package io.github.kotlinmania.rustlang.core.mem

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Opaque type representing the discriminant of an enum.
 *
 * See the [discriminant] function in this module for more information.
 */
public open class Discriminant<T>(
    public var value: Discriminant,
) : TrivialClone<Discriminant<T>> {
    public var field0: Discriminant
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns an ordering key for this [Discriminant] that has an unstable
     * implementation with regards to *semantic versioning*. This is not the be confused
     * with an [*"unstable sort"*].
     *
     * Because all types have a publicly accessible [Discriminant], regardless
     * of whether the order of said type is a part of its stability guarantees,
     * the exact ordering of the returned [DiscriminantOrd] cannot be relied on
     * for stability across crates. This is similar to the stability of the size
     * of a type as returned by [sizeOf].
     *
     * Enum values will be ordered based on the source declaration and `repr` values
     * of their variants, consistent with derived implementations of [PartialOrd]
     * and [Ord]. Noting that only the discriminant and no field values are considered.
     * All other kinds of values have an *unspecified* ordering, but will _typically_
     * be trivially ordered as equivalent.
     *
     * Users of this function should instead rely on the [Ord] implementation
     * of `T` wherever possible.
     *
     * # Examples
     *
     * ```kotlin
     * import cmp
     * import cmp.Ordering
     *
     * @feature(discriminantOrd)
     * public fun doctestEntry() {
     *     sealed class Foo {
     *         public data class A(public var value: Int) : Foo()
     *         public data class B(public var value: Int) : Foo()
     *     }
     *     // Equivalent arms will compare equal regardless of contained data
     *     assertMatches(discriminant(Foo.A(1)).ord().cmp(discriminant(Foo.A(2)).ord()), Ordering.Equal)
     *     // Inequivalent arms will compare not-equal
     *     assertMatches(discriminant(Foo.A(1)).ord().cmp(discriminant(Foo.B(1)).ord()), Ordering.Less or Ordering.Greater)
     * }
     * ```
     *
     * Upstream libraries and the compiler are free to change the exact ordering
     * of discriminants. The below _may_ break at any time:
     *
     * ```kotlin
     * import cmp
     * import cmp.Ordering
     *
     * // Foo.A is declared before Foo.B, but that ordering is not stable
     * assertMatches(discriminant(Foo.A(0)).ord().cmp(discriminant(Foo.B(0)).ord()), Ordering.Less)
     * ```
     *
     * ["unstable sort"]: slice.sortUnstable
     */
    public open fun ord(): DiscriminantOrd<T> = DiscriminantOrd(this.value)

    // N.B. These trait implementations cannot be derived because we don't want any bounds on T.

    public override fun clone(): Discriminant<T> = Discriminant(this.value)

    public open fun eq(rhs: Discriminant<T>): Boolean = this.value == rhs.value

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.value).hashCode()
        return state
    }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = DebugTuple(fmt, "Discriminant").field(this.value).finish()
}

/**
 * Implementation detail for [Discriminant.ord].
 */
public open class DiscriminantOrd<T>(
    public var value: Discriminant,
) : TrivialClone<DiscriminantOrd<T>> {
    public var field0: Discriminant
        get() = value
        set(v) {
            value = v
        }

    public override fun clone(): DiscriminantOrd<T> = DiscriminantOrd(this.value)

    public open fun eq(rhs: DiscriminantOrd<T>): Boolean = this.value == rhs.value

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.value).hashCode()
        return state
    }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = DebugTuple(fmt, "DiscriminantOrd").field(this.value).finish()

    public open fun partialCmp(other: DiscriminantOrd<T>): Ordering? = this.value.partialCmp(other.value)

    public open fun cmp(other: DiscriminantOrd<T>): Ordering = this.value.cmp(other.value)
}

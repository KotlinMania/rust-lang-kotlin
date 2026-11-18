// port-lint: source marker/variance.rs
package io.github.kotlinmania.rustlang.core.marker.variance

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Zero-sized type used to mark a scope as covariant.
 *
 * Covariant scopes must live at least as long as declared. See [the reference][1] for more
 * information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * Note: If `a` is otherwise contravariant or invariant, the resulting type is invariant.
 *
 * ## Layout
 *
 * For all `a`, the following are guaranteed:
 * * `sizeOf<PhantomCovariantLifetime<>>() == 0`
 * * `alignOf<PhantomCovariantLifetime<>>() == 1`
 */
public open class PhantomCovariantLifetime internal constructor(
    valueCtorArg: PhantomCovariant<Unit>,
) : PrivateItems<PhantomCovariantLifetime>, Variance<PhantomCovariantLifetime> {
    public var value: PhantomCovariant<Unit> = valueCtorArg
        internal set

    public var field0: PhantomCovariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): PhantomCovariantLifetime = PhantomCovariantLifetime(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomCovariantLifetime").toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: PhantomCovariant<Unit>,
        ): PhantomCovariantLifetime = PhantomCovariantLifetime(
            valueCtorArg = value,
        )

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(): PhantomCovariantLifetime = PhantomCovariantLifetime(PhantomCovariant(Unit))

        public val VALUE: PhantomCovariantLifetime = PhantomCovariantLifetime.new()
    }
}

/**
 * Zero-sized type used to mark a scope as contravariant.
 *
 * Contravariant scopes must live at most as long as declared. See [the reference][1] for
 * more information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * Note: If `a` is otherwise covariant or invariant, the resulting type is invariant.
 *
 * ## Layout
 *
 * For all `a`, the following are guaranteed:
 * * `sizeOf<PhantomContravariantLifetime<>>() == 0`
 * * `alignOf<PhantomContravariantLifetime<>>() == 1`
 */
public open class PhantomContravariantLifetime internal constructor(
    valueCtorArg: PhantomContravariant<Unit>,
) : PrivateItems<PhantomContravariantLifetime>, Variance<PhantomContravariantLifetime> {
    public var value: PhantomContravariant<Unit> = valueCtorArg
        internal set

    public var field0: PhantomContravariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): PhantomContravariantLifetime = PhantomContravariantLifetime(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomContravariantLifetime").toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: PhantomContravariant<Unit>,
        ): PhantomContravariantLifetime = PhantomContravariantLifetime(
            valueCtorArg = value,
        )

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(): PhantomContravariantLifetime = PhantomContravariantLifetime(PhantomContravariant(Unit))

        public val VALUE: PhantomContravariantLifetime = PhantomContravariantLifetime.new()
    }
}

/**
 * Zero-sized type used to mark a scope as invariant.
 *
 * Invariant scopes must be live for the exact length declared, neither shorter nor longer.
 * See [the reference][1] for more information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * ## Layout
 *
 * For all `a`, the following are guaranteed:
 * * `sizeOf<PhantomInvariantLifetime<>>() == 0`
 * * `alignOf<PhantomInvariantLifetime<>>() == 1`
 */
public open class PhantomInvariantLifetime internal constructor(
    valueCtorArg: PhantomInvariant<Unit>,
) : PrivateItems<PhantomInvariantLifetime>, Variance<PhantomInvariantLifetime> {
    public var value: PhantomInvariant<Unit> = valueCtorArg
        internal set

    public var field0: PhantomInvariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): PhantomInvariantLifetime = PhantomInvariantLifetime(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomInvariantLifetime").toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            value: PhantomInvariant<Unit>,
        ): PhantomInvariantLifetime = PhantomInvariantLifetime(
            valueCtorArg = value,
        )

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(): PhantomInvariantLifetime = PhantomInvariantLifetime(PhantomInvariant(Unit))

        public val VALUE: PhantomInvariantLifetime = PhantomInvariantLifetime.new()
    }
}

/**
 * Zero-sized type used to mark a type parameter as covariant.
 *
 * Types used as part of the return value from a function are covariant. If the type is _also_
 * passed as a parameter then it is [invariant][PhantomInvariant]. See [the reference][1] for
 * more information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * Note: If `T` is otherwise contravariant or invariant, the resulting type is invariant.
 *
 * ## Layout
 *
 * For all `T`, the following are guaranteed:
 * * `sizeOf<PhantomCovariant<T>>() == 0`
 * * `alignOf<PhantomCovariant<T>>() == 1`
 */
public open class PhantomCovariant<T>(
    public var value: Unit,
) : PrivateItems<PhantomCovariant<T>>, Variance<PhantomCovariant<T>>, TrivialClone<PhantomCovariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomCovariant").toString() + "<" + ("PhantomCovariant").toString() + ">"); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): PhantomCovariant<T> = PhantomCovariant(this.value)

    public open fun eq(arg0: PhantomCovariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomCovariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomCovariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(): PhantomCovariant<T> = PhantomCovariant(Unit)

        public val VALUE: PhantomCovariant<T> = PhantomCovariant.new()

        public fun <T> default(): PhantomCovariant<T> = PhantomCovariant(Unit)
    }
}

/**
 * Zero-sized type used to mark a type parameter as contravariant.
 *
 * Types passed as arguments to a function are contravariant. If the type is _also_ part of the
 * return value from a function then it is [invariant][PhantomInvariant]. See [the
 * reference][1] for more information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * Note: If `T` is otherwise covariant or invariant, the resulting type is invariant.
 *
 * ## Layout
 *
 * For all `T`, the following are guaranteed:
 * * `sizeOf<PhantomContravariant<T>>() == 0`
 * * `alignOf<PhantomContravariant<T>>() == 1`
 */
public open class PhantomContravariant<T>(
    public var value: Unit,
) : PrivateItems<PhantomContravariant<T>>, Variance<PhantomContravariant<T>>, TrivialClone<PhantomContravariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomContravariant").toString() + "<" + ("PhantomContravariant").toString() + ">"); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): PhantomContravariant<T> = PhantomContravariant(this.value)

    public open fun eq(arg0: PhantomContravariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomContravariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomContravariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(): PhantomContravariant<T> = PhantomContravariant(Unit)

        public val VALUE: PhantomContravariant<T> = PhantomContravariant.new()

        public fun <T> default(): PhantomContravariant<T> = PhantomContravariant(Unit)
    }
}

/**
 * Zero-sized type used to mark a type parameter as invariant.
 *
 * Types that are both passed as an argument _and_ used as part of the return value from a
 * function are invariant. See [the reference][1] for more information.
 *
 * [1]: https://doc.rust-lang.org/stable/reference/subtyping.html#variance
 *
 * ## Layout
 *
 * For all `T`, the following are guaranteed:
 * * `sizeOf<PhantomInvariant<T>>() == 0`
 * * `alignOf<PhantomInvariant<T>>() == 1`
 */
public open class PhantomInvariant<T>(
    public var value: Unit,
) : PrivateItems<PhantomInvariant<T>>, Variance<PhantomInvariant<T>>, TrivialClone<PhantomInvariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(("PhantomInvariant").toString() + "<" + ("PhantomInvariant").toString() + ">"); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): PhantomInvariant<T> = PhantomInvariant(this.value)

    public open fun eq(arg0: PhantomInvariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomInvariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomInvariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(): PhantomInvariant<T> = PhantomInvariant(Unit)

        public val VALUE: PhantomInvariant<T> = PhantomInvariant.new()

        public fun <T> default(): PhantomInvariant<T> = PhantomInvariant(Unit)
    }
}

public interface PrivateItems<T> {
    public val VALUE: T
}

/**
 * A marker trait for phantom variance types.
 */
public interface Variance<T> : PrivateItems<T>

/**
 * Construct a variance marker; equivalent to [Default.default].
 *
 * This type can be any of the following. You generally should not need to explicitly name the
 * type, however.
 *
 * - [PhantomCovariant]
 * - [PhantomContravariant]
 * - [PhantomInvariant]
 * - [PhantomCovariantLifetime]
 * - [PhantomContravariantLifetime]
 * - [PhantomInvariantLifetime]
 *
 * # Example
 *
 * ```kotlin
 * import core.marker.PhantomCovariant
 * import core.marker.variance
 *
 * @feature(phantomVarianceMarkers)
 * public fun doctestEntry() {
 *     open class BoundFn<F, P, R, Output>(
 *         public var function: F,
 *         public var parameter: P,
 *         public var returnValue: PhantomCovariant<R>,
 *     )
 *     val boundFn = BoundFn(function = identity, parameter = 5u.toUByte(), returnValue = variance())
 * }
 * ```
 */
public fun <T> variance(): T where T : Variance<T> = T.VALUE

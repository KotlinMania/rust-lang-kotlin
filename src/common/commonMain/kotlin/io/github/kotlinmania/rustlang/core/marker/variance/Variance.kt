// port-lint: source marker/variance.rs
package io.github.kotlinmania.rustlang.core.marker.variance

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<PhantomCovariant<Unit>> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomCovariantLifetime>, Variance<PhantomCovariantLifetime> {
    public var field0: PhantomCovariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public var value: PhantomCovariant<Unit>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: PhantomCovariant<Unit>, fieldLayout: TypeLayout<PhantomCovariant<Unit>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomCovariantLifetime> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomCovariantLifetime(ptr, layout) }, { it.value })

    public open fun clone(): PhantomCovariantLifetime = PhantomCovariantLifetime(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomCovariantLifetime").toString())

    public companion object {
        public operator fun invoke(value: PhantomCovariant<Unit>, fieldLayout: TypeLayout<PhantomCovariant<Unit>> = UnresolvedTypeLayout()): PhantomCovariantLifetime = PhantomCovariantLifetime(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<PhantomCovariant<Unit>> = UnresolvedTypeLayout()): PhantomCovariantLifetime = PhantomCovariantLifetime(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomCovariantLifetime = PhantomCovariantLifetime(PhantomCovariant(Unit, fieldLayout = fieldLayout), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomCovariant<Unit>(ptr, layout) }, { it.value }))

        public val VALUE: PhantomCovariantLifetime = PhantomCovariantLifetime.new(fieldLayout = UnresolvedTypeLayout<Unit>())
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
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<PhantomContravariant<Unit>> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomContravariantLifetime>, Variance<PhantomContravariantLifetime> {
    public var field0: PhantomContravariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public var value: PhantomContravariant<Unit>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: PhantomContravariant<Unit>, fieldLayout: TypeLayout<PhantomContravariant<Unit>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomContravariantLifetime> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomContravariantLifetime(ptr, layout) }, { it.value })

    public open fun clone(): PhantomContravariantLifetime = PhantomContravariantLifetime(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomContravariantLifetime").toString())

    public companion object {
        public operator fun invoke(value: PhantomContravariant<Unit>, fieldLayout: TypeLayout<PhantomContravariant<Unit>> = UnresolvedTypeLayout()): PhantomContravariantLifetime = PhantomContravariantLifetime(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<PhantomContravariant<Unit>> = UnresolvedTypeLayout()): PhantomContravariantLifetime = PhantomContravariantLifetime(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomContravariantLifetime = PhantomContravariantLifetime(PhantomContravariant(Unit, fieldLayout = fieldLayout), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomContravariant<Unit>(ptr, layout) }, { it.value }))

        public val VALUE: PhantomContravariantLifetime = PhantomContravariantLifetime.new(fieldLayout = UnresolvedTypeLayout<Unit>())
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
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<PhantomInvariant<Unit>> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomInvariantLifetime>, Variance<PhantomInvariantLifetime> {
    public var field0: PhantomInvariant<Unit>
        get() = value
        set(v) {
            value = v
        }

    public var value: PhantomInvariant<Unit>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: PhantomInvariant<Unit>, fieldLayout: TypeLayout<PhantomInvariant<Unit>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomInvariantLifetime> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomInvariantLifetime(ptr, layout) }, { it.value })

    public open fun clone(): PhantomInvariantLifetime = PhantomInvariantLifetime(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomInvariantLifetime").toString())

    public companion object {
        public operator fun invoke(value: PhantomInvariant<Unit>, fieldLayout: TypeLayout<PhantomInvariant<Unit>> = UnresolvedTypeLayout()): PhantomInvariantLifetime = PhantomInvariantLifetime(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<PhantomInvariant<Unit>> = UnresolvedTypeLayout()): PhantomInvariantLifetime = PhantomInvariantLifetime(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomInvariantLifetime = PhantomInvariantLifetime(PhantomInvariant(Unit, fieldLayout = fieldLayout), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomInvariant<Unit>(ptr, layout) }, { it.value }))

        public val VALUE: PhantomInvariantLifetime = PhantomInvariantLifetime.new(fieldLayout = UnresolvedTypeLayout<Unit>())
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
public open class PhantomCovariant<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomCovariant<T>>, Variance<PhantomCovariant<T>>, TrivialClone<PhantomCovariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public var value: Unit
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomCovariant<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomCovariant<T>(ptr, layout) }, { it.value })

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomCovariant").toString() + "<" + ("PhantomCovariant").toString() + ">")

    public open fun clone(): PhantomCovariant<T> = PhantomCovariant(this.value)

    public open fun eq(arg0: PhantomCovariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomCovariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomCovariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        public operator fun <T> invoke(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomCovariant<T> = PhantomCovariant<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomCovariant<T> = PhantomCovariant<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomCovariant<T> = PhantomCovariant(Unit, fieldLayout = fieldLayout)

        public val VALUE: PhantomCovariant<T> = PhantomCovariant.new(fieldLayout = UnresolvedTypeLayout<Unit>())

        public fun <T> default(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomCovariant<T> = PhantomCovariant(Unit, fieldLayout = fieldLayout)
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
public open class PhantomContravariant<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomContravariant<T>>, Variance<PhantomContravariant<T>>, TrivialClone<PhantomContravariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public var value: Unit
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomContravariant<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomContravariant<T>(ptr, layout) }, { it.value })

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomContravariant").toString() + "<" + ("PhantomContravariant").toString() + ">")

    public open fun clone(): PhantomContravariant<T> = PhantomContravariant(this.value)

    public open fun eq(arg0: PhantomContravariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomContravariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomContravariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        public operator fun <T> invoke(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomContravariant<T> = PhantomContravariant<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomContravariant<T> = PhantomContravariant<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomContravariant<T> = PhantomContravariant(Unit, fieldLayout = fieldLayout)

        public val VALUE: PhantomContravariant<T> = PhantomContravariant.new(fieldLayout = UnresolvedTypeLayout<Unit>())

        public fun <T> default(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomContravariant<T> = PhantomContravariant(Unit, fieldLayout = fieldLayout)
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
public open class PhantomInvariant<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout(),
) : io.github.kotlinmania.rustlang.core.marker.variance.privateitems.PrivateItems<PhantomInvariant<T>>, Variance<PhantomInvariant<T>>, TrivialClone<PhantomInvariant<T>> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public var value: Unit
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PhantomInvariant<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PhantomInvariant<T>(ptr, layout) }, { it.value })

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt(("PhantomInvariant").toString() + "<" + ("PhantomInvariant").toString() + ">")

    public open fun clone(): PhantomInvariant<T> = PhantomInvariant(this.value)

    public open fun eq(arg0: PhantomInvariant<T>): Boolean = true

    public open fun partialCmp(arg0: PhantomInvariant<T>): Ordering? = Ordering.Equal

    public open fun cmp(arg0: PhantomInvariant<T>): Ordering = Ordering.Equal

    public override fun hashCode(): Int = 0

    public companion object {
        public operator fun <T> invoke(value: Unit, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomInvariant<T> = PhantomInvariant<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomInvariant<T> = PhantomInvariant<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of the variance marker.
         */
        public fun <T> new(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomInvariant<T> = PhantomInvariant(Unit, fieldLayout = fieldLayout)

        public val VALUE: PhantomInvariant<T> = PhantomInvariant.new(fieldLayout = UnresolvedTypeLayout<Unit>())

        public fun <T> default(fieldLayout: TypeLayout<Unit> = UnresolvedTypeLayout()): PhantomInvariant<T> = PhantomInvariant(Unit, fieldLayout = fieldLayout)
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

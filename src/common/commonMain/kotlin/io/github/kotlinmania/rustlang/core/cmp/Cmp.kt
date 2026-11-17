// port-lint: source cmp.rs
package io.github.kotlinmania.rustlang.core.cmp

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.Ordering.*
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.cmp.clamp.ClampBounds
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.discriminantValue
import io.github.kotlinmania.rustlang.core.intrinsics.integerMax
import io.github.kotlinmania.rustlang.core.intrinsics.integerMin
import io.github.kotlinmania.rustlang.core.intrinsics.threeWayCompare
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.ops.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.panic.constAssert
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Utilities for comparing and ordering values.
//
// This module contains various tools for comparing and ordering values. In
// summary:
//
// * [PartialEq.Rhs] overloads the `==` and `!=` operators. In cases where
//   `Rhs` (the right hand side's type) is `Self`, this trait corresponds to a
//   partial equivalence relation.
// * [Eq] indicates that the overloaded `==` operator corresponds to an
//   equivalence relation.
// * [Ord] and [PartialOrd] are traits that allow you to define total and
//   partial orderings between values, respectively. Implementing them overloads
//   the `<`, `<=`, `>`, and `>=` operators.
// * [Ordering] is an enum returned by the main functions of [Ord] and
//   [PartialOrd], and describes an ordering of two values (less, equal, or
//   greater).
// * [Reverse] is a struct that allows you to easily reverse an ordering.
// * [max] and [min] are functions that build off of [Ord] and allow you
//   to find the maximum or minimum of two values.
//
// For more details, see the respective documentation of each item in the list.
//
// [max]: Ord.max
// [min]: Ord.min

/**
 * Trait for comparisons using the equality operator.
 *
 * Implementing this trait for types provides the `==` and `!=` operators for
 * those types.
 *
 * `x.eq(y)` can also be written `x == y`, and `x.ne(y)` can be written `x != y`.
 * We use the easier-to-read infix notation in the remainder of this documentation.
 *
 * This trait allows for comparisons using the equality operator, for types
 * that do not have a full equivalence relation. For example, in floating point
 * numbers `NaN != NaN`, so floating point types implement `PartialEq` but not
 * [trait.Eq]. Formally speaking, when `Rhs == Self`, this trait corresponds
 * to a [partial equivalence relation].
 *
 * [partial equivalence relation]: https://en.wikipedia.org/wiki/PartialEquivalenceRelation
 *
 * Implementations must ensure that `eq` and `ne` are consistent with each other:
 *
 * - `a != b` if and only if `!(a == b)`.
 *
 * The default implementation of `ne` provides this consistency and is almost
 * always sufficient. It should not be overridden without very good reason.
 *
 * If [PartialOrd] or [Ord] are also implemented for `Self` and `Rhs`, their methods must also
 * be consistent with `PartialEq` (see the documentation of those traits for the exact
 * requirements). It's easy to accidentally make them disagree by deriving some of the traits and
 * manually implementing others.
 *
 * The equality relation `==` must satisfy the following conditions
 * (for all `a`, `b`, `c` of type `A`, `B`, `C`):
 *
 * - **Symmetry**: if `A: PartialEq<B>` and `B: PartialEq<A>`, then **`a == b`
 *   implies `b == a`**; and
 *
 * - **Transitivity**: if `A: PartialEq<B>` and `B: PartialEq<C>` and `A:
 *   PartialEq<C>`, then * *`a == b`and`b == c`implies`a == c`**.
 *   This must also work for longer chains, such as when `A: PartialEq<B>`, `B: PartialEq<C>`,
 *   `C: PartialEq<D>`, and `A: PartialEq<D>` all exist.
 *
 * Note that the `B: PartialEq<A>` (symmetric) and `A: PartialEq<C>`
 * (transitive) impls are not forced to exist, but these requirements apply
 * whenever they do exist.
 *
 * Violating these requirements is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on the correctness of these
 * methods.
 *
 * ## Cross-crate considerations
 *
 * Upholding the requirements stated above can become tricky when one crate implements `PartialEq`
 * for a type of another crate (i.e., to allow comparing one of its own types with a type from the
 * standard library). The recommendation is to never implement this trait for a foreign type. In
 * other words, such a crate should do `implementation PartialEq<ForeignType> for LocalType`, but it should
 * *not* do `implementation PartialEq<LocalType> for ForeignType`.
 *
 * This avoids the problem of transitive chains that criss-cross crate boundaries: for all local
 * types `T`, you may assume that no other crate will add `implementation`s that allow comparing `T == U`. In
 * other words, if other crates add `implementation`s that allow building longer transitive chains `U1 == ...
 * == T == V1 == ...`, then all the types that appear to the right of`T` must be types that the
 * crate defining `T` already knows about. This rules out transitive chains where downstream crates
 * can add new `implementation`s that "stitch together" comparisons of foreign types in ways that violate
 * transitivity.
 *
 * Not having such foreign `implementation`s also avoids forward compatibility issues where one crate adding
 * more `PartialEq` implementations can cause build failures in downstream crates.
 *
 * ## Derivable
 *
 * This trait can be used with `@derive`. When `derive`d on structs, two
 * instances are equal if all fields are equal, and not equal if any fields
 * are not equal. When `derive`d on enums, two instances are equal if they
 * are the same variant and all fields are equal.
 *
 * ## How can I implement `PartialEq`?
 *
 * An example implementation for a domain in which two books are considered
 * the same book if their ISBN matches, even if the formats differ:
 *
 * ```kotlin
 * sealed class BookFormat {
 *     public data object Paperback : BookFormat()
 *     public data object Hardback : BookFormat()
 *     public data object Ebook : BookFormat()
 * }
 * open class Book(public var isbn: Int, public var format: BookFormat) {
 *     public open fun eq(other: Book): Boolean {
 *         return this.isbn == other.isbn
 *     }
 * }
 * val b1 = Book(isbn = 3, format = BookFormat.Paperback)
 * val b2 = Book(isbn = 3, format = BookFormat.Ebook)
 * val b3 = Book(isbn = 10, format = BookFormat.Paperback)
 * check(b1 == b2)
 * check(b1 != b3)
 * ```
 *
 * ## How can I compare two different types?
 *
 * The type you can compare with is controlled by `PartialEq`s type parameter.
 * For example, let's tweak our previous code a bit:
 *
 * ```kotlin
 * // The derive implements <BookFormat> == <BookFormat> comparisons
 * sealed class BookFormat {
 *     public data object Paperback : BookFormat()
 *     public data object Hardback : BookFormat()
 *     public data object Ebook : BookFormat()
 *
 *     public fun rustEquals(other: BookFormat): Boolean = this == other
 *
 *     // Implement <BookFormat> == <Book> comparisons
 *     public open fun eq(other: Book): Boolean {
 *         return this == other.format
 *     }
 * }
 * open class Book(public var isbn: Int, public var format: BookFormat) {
 *     // Implement <Book> == <BookFormat> comparisons
 *     public open fun eq(other: BookFormat): Boolean {
 *         return this.format == other
 *     }
 * }
 * val b1 = Book(isbn = 3, format = BookFormat.Paperback)
 * check(b1 == BookFormat.Paperback)
 * check(BookFormat.Ebook != b1)
 * ```
 *
 * By changing `implementation PartialEq for Book` to `implementation PartialEq<BookFormat> for Book`,
 * we allow `BookFormat`s to be compared with `Book`s.
 *
 * A comparison like the one above, which ignores some fields of the struct,
 * can be dangerous. It can easily lead to an unintended violation of the
 * requirements for a partial equivalence relation. For example, if we kept
 * the above implementation of `PartialEq<Book>` for `BookFormat` and added an
 * implementation of `PartialEq<Book>` for `Book` (either via a `@derive` or
 * via the manual implementation from the first example) then the result would
 * violate transitivity:
 *
 * ```kotlin
 * sealed class BookFormat {
 *     public data object Paperback : BookFormat()
 *     public data object Hardback : BookFormat()
 *     public data object Ebook : BookFormat()
 *
 *     public fun rustEquals(other: BookFormat): Boolean = this == other
 *
 *     public open fun eq(other: Book): Boolean {
 *         return this == other.format
 *     }
 * }
 * open class Book(public var isbn: Int, public var format: BookFormat) {
 *     public open fun eq(other: BookFormat): Boolean {
 *         return this.format == other
 *     }
 * }
 * fun main() {
 *     val b1 = Book(isbn = 1, format = BookFormat.Paperback)
 *     val b2 = Book(isbn = 2, format = BookFormat.Paperback)
 *     check(b1 == BookFormat.Paperback)
 *     check(BookFormat.Paperback == b2)
 *     // The following should hold by transitivity but doesn't.
 *     check(b1 == b2)
 *     // <-- PANICS
 * }
 * ```
 *
 * # Examples
 *
 * ```kotlin
 * val x: UInt = 0u
 * val y: UInt = 1u
 * check(x == y == false)
 * check(x.eq(y) == false)
 * ```
 *
 * [eq]: PartialEq.eq
 * [ne]: PartialEq.ne
 */
public interface PartialEq<Rhs> {
    /**
     * Equality operator `==`.
     *
     * Implementation of the "is equal to" operator `==`:
     * tests whether its arguments are equal.
     */
    public fun eq(other: Rhs): Boolean

    /**
     * Inequality operator `!=`.
     *
     * Implementation of the "is not equal to" or "is different from" operator `!=`:
     * tests whether its arguments are different.
     *
     * # Default implementation
     * The default implementation of the inequality operator simply calls
     * the implementation of the equality operator and negates the result.
     *
     * This default shouldn't be overridden without good reason,
     * such as when forwarding to another PartialEq implementation.
     */
    public fun ne(other: Rhs): Boolean = !this.eq(other)
}

/**
 * Trait for comparisons corresponding to [equivalence relations](
 * https://en.wikipedia.org/wiki/EquivalenceRelation).
 *
 * The primary difference to [PartialEq] is the additional requirement for reflexivity. A type
 * that implements [PartialEq] guarantees that for all `a`, `b` and `c`:
 *
 * - symmetric: `a == b` implies `b == a`
 * - transitive: `a == b` and `b == c` implies `a == c`
 * - consistent: `a != b` if and only if `!(a == b)`
 *
 * `Eq`, which builds on top of [PartialEq] also implies:
 *
 * - reflexive: `a == a`
 *
 * This property cannot be checked by the compiler, and therefore `Eq` is a trait without methods.
 *
 * Violating this property is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on the correctness of these
 * methods.
 *
 * Floating point types such as [f32] and [f64] implement only [PartialEq] but *not* `Eq`
 * because `NaN` != `NaN`.
 *
 * ## Derivable
 *
 * This trait can be used with `@derive`. When `derive`d, because `Eq` has no extra methods, it
 * is only informing the compiler that this is an equivalence relation rather than a partial
 * equivalence relation. Note that the `derive` strategy requires all fields are `Eq`, which isn't
 * always desired.
 *
 * ## How can I implement `Eq`?
 *
 * If you cannot use the `derive` strategy, specify that your type implements `Eq`, which has no
 * extra methods:
 *
 * ```kotlin
 * sealed class BookFormat {
 *     public data object Paperback : BookFormat()
 *     public data object Hardback : BookFormat()
 *     public data object Ebook : BookFormat()
 * }
 * open class Book(public var isbn: Int, public var format: BookFormat) {
 *     public open fun eq(other: Book): Boolean {
 *         return this.isbn == other.isbn
 *     }
 * }
 * ```
 */
public interface Eq : PartialEq<Eq> {
    public fun assertReceiverIsTotalEq() {}

    public fun assertFieldsAreEq() {}
}

// Note: this struct is used solely by @derive to
// assert that every component of a type implements Eq.
//
// This struct should never appear in user code.
public open class AssertParamIsEq<T : Eq>(
    public var field: Unit,
)

// This is a lang item only so that `BinOp.Cmp` in MIR can return it.
// It has no special behavior, but does require that the three variants
// `Less`/`Equal`/`Greater` remain `- 1_i8`/`0_i8`/`+ 1_i8` respectively.

/**
 * An `Ordering` is the result of a comparison between two values.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * check(1.cmp(2) == Ordering.Less)
 * check(1.cmp(1) == Ordering.Equal)
 * check(2.cmp(1) == Ordering.Greater)
 * ```
 */
public sealed class Ordering(
    public val value: Byte,
) {
    /**
     * An ordering where a compared value is less than another.
     */
    public data object Less : Ordering((-1).toByte())
    /**
     * An ordering where a compared value is equal to another.
     */
    public data object Equal : Ordering(0)
    /**
     * An ordering where a compared value is greater than another.
     */
    public data object Greater : Ordering(1)

    public fun rustEquals(other: Ordering): Boolean = this == other

    public open fun asRaw(): Byte {
        // Note(const-Note): just use `PartialOrd` against `Equal` once that's const
        return discriminantValue(this)
    }

    /**
     * Returns `true` if the ordering is the `Equal` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isEq() == false)
     * check(Ordering.Equal.isEq() == true)
     * check(Ordering.Greater.isEq() == false)
     * ```
     */
    public open fun isEq(): Boolean {
        // All the `is_ *` methods are implemented as comparisons against zero
        // to follow how clang's libcxx implements their equivalents in
        // <https://github.com/llvm/llvm-project/blob/60486292b79885b7800b082754153202bef5b1f0/libcxx/include/__compare/isEq.h#L23-L28>
        return this.asRaw() == 0.toByte()
    }

    /**
     * Returns `true` if the ordering is not the `Equal` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isNe() == true)
     * check(Ordering.Equal.isNe() == false)
     * check(Ordering.Greater.isNe() == true)
     * ```
     */
    public open fun isNe(): Boolean = this.asRaw() != 0.toByte()

    /**
     * Returns `true` if the ordering is the `Less` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isLt() == true)
     * check(Ordering.Equal.isLt() == false)
     * check(Ordering.Greater.isLt() == false)
     * ```
     */
    public open fun isLt(): Boolean = this.asRaw() < 0.toByte()

    /**
     * Returns `true` if the ordering is the `Greater` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isGt() == false)
     * check(Ordering.Equal.isGt() == false)
     * check(Ordering.Greater.isGt() == true)
     * ```
     */
    public open fun isGt(): Boolean = this.asRaw() > 0.toByte()

    /**
     * Returns `true` if the ordering is either the `Less` or `Equal` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isLe() == true)
     * check(Ordering.Equal.isLe() == true)
     * check(Ordering.Greater.isLe() == false)
     * ```
     */
    public open fun isLe(): Boolean = this.asRaw() <= 0.toByte()

    /**
     * Returns `true` if the ordering is either the `Greater` or `Equal` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.isGe() == false)
     * check(Ordering.Equal.isGe() == true)
     * check(Ordering.Greater.isGe() == true)
     * ```
     */
    public open fun isGe(): Boolean = this.asRaw() >= 0.toByte()

    /**
     * Reverses the `Ordering`.
     *
     * * `Less` becomes `Greater`.
     * * `Greater` becomes `Less`.
     * * `Equal` becomes `Equal`.
     *
     * # Examples
     *
     * Basic behavior:
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(Ordering.Less.reverse() == Ordering.Greater)
     * check(Ordering.Equal.reverse() == Ordering.Equal)
     * check(Ordering.Greater.reverse() == Ordering.Less)
     * ```
     *
     * This method can be used to reverse a comparison:
     *
     * ```kotlin
     * val data: MutableList<Any?> = mutableListOf(2, 10, 5, 8)
     * // sort the array from largest to smallest.
     * data.sortBy({ a, b -> a.cmp(b).reverse() })
     * val b: MutableList<Any?> = mutableListOf(10, 8, 5, 2)
     * check(data == b)
     * ```
     */
    public open fun reverse(): Ordering =
        when (this) {
            Less -> Greater
            Equal -> Equal
            Greater -> Less
        }

    /**
     * Chains two orderings.
     *
     * Returns `self` when it's not `Equal`. Otherwise returns `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * val result = Ordering.Equal.then(Ordering.Less)
     * check(result == Ordering.Less)
     * val result1 = Ordering.Less.then(Ordering.Equal)
     * check(result1 == Ordering.Less)
     * val result2 = Ordering.Less.then(Ordering.Greater)
     * check(result2 == Ordering.Less)
     * val result3 = Ordering.Equal.then(Ordering.Equal)
     * check(result3 == Ordering.Equal)
     * val x: Triple<Long, Long, Long> = Triple(1, 2, 7)
     * val y: Triple<Long, Long, Long> = Triple(1, 5, 3)
     * val result4 = x.first.cmp(y.first).then(x.second.cmp(y.second)).then(x.third.cmp(y.third))
     * check(result4 == Ordering.Less)
     * ```
     */
    public open fun then(other: Ordering): Ordering =
        when (this) {
            Equal -> other
            else -> this
        }

    /**
     * Chains the ordering with the given function.
     *
     * Returns `self` when it's not `Equal`. Otherwise calls `f` and returns
     * the result.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * val result = Ordering.Equal.thenWith({ Ordering.Less })
     * check(result == Ordering.Less)
     * val result1 = Ordering.Less.thenWith({ Ordering.Equal })
     * check(result1 == Ordering.Less)
     * val result2 = Ordering.Less.thenWith({ Ordering.Greater })
     * check(result2 == Ordering.Less)
     * val result3 = Ordering.Equal.thenWith({ Ordering.Equal })
     * check(result3 == Ordering.Equal)
     * val x: Triple<Long, Long, Long> = Triple(1, 2, 7)
     * val y: Triple<Long, Long, Long> = Triple(1, 5, 3)
     * val result4 = x.first.cmp(y.first).thenWith({ x.second.cmp(y.second) }).thenWith({ x.third.cmp(y.third) })
     * check(result4 == Ordering.Less)
     * ```
     */
    public open fun thenWith(f: () -> Ordering): Ordering =
        when (this) {
            Equal -> f()
            else -> this
        }
}

/**
 * A helper struct for reverse ordering.
 *
 * This struct is a helper to be used with functions like [Vec.sortByKey] and
 * can be used to reverse order a part of a key.
 *
 * [Vec.sortByKey]: ../../std/vec/struct.Vec.html#method.sortByKey
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp.Reverse
 *
 * var v = mutableListOf(1, 2, 3, 4, 5, 6)
 * v.sortByKey({ num -> Pair(num > 3, Reverse(num)) })
 * check(v == mutableListOf(3, 2, 1, 6, 5, 4))
 * ```
 */
public open class Reverse<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) : PartialOrd<Reverse<T>>, Ord<Reverse<T>> {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Reverse<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Reverse<T>(ptr, layout) }, { it.value })

    public override fun partialCmp(other: Reverse<T>): Ordering? = other.value.partialCmp(this.value)

    public override fun lt(other: Reverse<T>): Boolean = (kmCompareValues(other.value, this.value) < 0)

    public override fun le(other: Reverse<T>): Boolean = (kmCompareValues(other.value, this.value) <= 0)

    public override fun gt(other: Reverse<T>): Boolean = (kmCompareValues(other.value, this.value) > 0)

    public override fun ge(other: Reverse<T>): Boolean = (kmCompareValues(other.value, this.value) >= 0)

    public override fun cmp(other: Reverse<T>): Ordering = other.value.cmp(this.value)

    public open fun clone(): Reverse<T> = Reverse(this.value.clone(), fieldLayout = this.fieldLayout)

    public open fun cloneFrom(source: Reverse<T>) {
        this.value = source.value
    }
}

/**
 * A pair where ordering and equality work on only the `key`, ignoring the `value`.
 *
 * Used to implement `Iterator.minByKey` as `map`+`min`, for example.
 */
public open class KeyAndValue<K, V>(
    public var key: K,
    public var value: V,
) : PartialEq<KeyAndValue<K, V>>, Eq, PartialOrd<KeyAndValue<K, V>>, Ord<KeyAndValue<K, V>> {
    public open fun clone(): KeyAndValue<K, V> = KeyAndValue(key, value)

    public override fun eq(other: KeyAndValue<K, V>): Boolean = this.key == other.key

    public override fun ne(other: KeyAndValue<K, V>): Boolean = this.key != other.key

    public override fun partialCmp(other: KeyAndValue<K, V>): Ordering? = this.key.partialCmp(other.key)

    public override fun lt(other: KeyAndValue<K, V>): Boolean = (kmCompareValues(this.key, other.key) < 0)

    public override fun le(other: KeyAndValue<K, V>): Boolean = (kmCompareValues(this.key, other.key) <= 0)

    public override fun gt(other: KeyAndValue<K, V>): Boolean = (kmCompareValues(this.key, other.key) > 0)

    public override fun ge(other: KeyAndValue<K, V>): Boolean = (kmCompareValues(this.key, other.key) >= 0)

    public override fun cmp(other: KeyAndValue<K, V>): Ordering = this.key.cmp(other.key)

    public companion object {
        public fun <K, V> typeLayout(typeLayoutK: TypeLayout<K>, typeLayoutV: TypeLayout<V>): TypeLayout<KeyAndValue<K, V>> =
            object : TypeLayout<KeyAndValue<K, V>> {
                override val alignBytes: Long = listOf(1L, typeLayoutK.alignBytes, typeLayoutV.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutK.sizeBytes, typeLayoutV.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutV.sizeBytes), alignBytes)
                override fun readNative(addr: Long): KeyAndValue<K, V> =
                    KeyAndValue(
                        (typeLayoutK).readNative(addr),
                        (typeLayoutV).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: KeyAndValue<K, V>) {
                    (typeLayoutK).writeNative(addr, value.key)
                    (typeLayoutV).writeNative(addr + off1, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KeyAndValue<K, V> =
                    KeyAndValue(
                        (typeLayoutK).readBytes(buf, offset),
                        (typeLayoutV).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KeyAndValue<K, V>) {
                    (typeLayoutK).writeBytes(buf, offset, value.key)
                    (typeLayoutV).writeBytes(buf, offset + off1.toInt(), value.value)
                }
            }
    }
}

/**
 * Trait for types that form a [total order](https://en.wikipedia.org/wiki/TotalOrder).
 *
 * Implementations must be consistent with the [PartialOrd] implementation, and ensure `max`,
 * `min`, and `clamp` are consistent with `cmp`:
 *
 * - `partialCmp(a, b) == Some(cmp(a, b))`.
 * - `max(a, b) == maxBy(a, b, cmp)` (ensured by the default implementation).
 * - `min(a, b) == minBy(a, b, cmp)` (ensured by the default implementation).
 * - For `a.clamp(min, max)`, see the [method docs](#method.clamp) (ensured by the default
 *   implementation).
 *
 * Violating these requirements is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on the correctness of these
 * methods.
 *
 * ## Corollaries
 *
 * From the above and the requirements of `PartialOrd`, it follows that for all `a`, `b` and `c`:
 *
 * - exactly one of `a<b`, `a == b` or `a> b` is true; and
 * - `<` is transitive: `a<b` and `b<c` implies `a<c`. The same must hold for both `==` and
 *   `>`.
 *
 * Mathematically speaking, the `<` operator defines a strict [weak order]. In cases where `==`
 * conforms to mathematical equality, it also defines a strict [total order].
 *
 * [weak order]: https://en.wikipedia.org/wiki/WeakOrdering
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 *
 * ## Derivable
 *
 * This trait can be used with `@derive`.
 *
 * When `derive`d on structs, it will produce a
 * [lexicographic](https://en.wikipedia.org/wiki/LexicographicOrder) ordering based on the
 * top-to-bottom declaration order of the struct's members.
 *
 * When `derive`d on enums, variants are ordered primarily by their discriminants. Secondarily,
 * they are ordered by their fields. By default, the discriminant is smallest for variants at the
 * top, and largest for variants at the bottom. Here's an example:
 *
 * ```kotlin
 * sealed class E {
 *     public data object Top : E()
 *     public data object Bottom : E()
 *
 *     public fun rustEquals(other: E): Boolean = this == other
 * }
 * check(E.Top < E.Bottom)
 * ```
 *
 * However, manually setting the discriminants can override this default behavior:
 *
 * ```kotlin
 * sealed class E(public val value: Int) {
 *     public data object Top : E(2)
 *     public data object Bottom : E(1)
 *
 *     public fun rustEquals(other: E): Boolean = this == other
 * }
 * check(E.Bottom < E.Top)
 * ```
 *
 * ## Lexicographical comparison
 *
 * Lexicographical comparison is an operation with the following properties:
 *  - Two sequences are compared element by element.
 *  - The first mismatching element defines which sequence is lexicographically less or greater
 *    than the other.
 *  - If one sequence is a prefix of another, the shorter sequence is lexicographically less than
 *    the other.
 *  - If two sequences have equivalent elements and are of the same length, then the sequences are
 *    lexicographically equal.
 *  - An empty sequence is lexicographically less than any non-empty sequence.
 *  - Two empty sequences are lexicographically equal.
 *
 * ## How can I implement `Ord`?
 *
 * `Ord` requires that the type also be [PartialOrd], [PartialEq], and [Eq].
 *
 * Because `Ord` implies a stronger ordering relationship than [PartialOrd], and both `Ord` and
 * [PartialOrd] must agree, you must choose how to implement `Ord` **first**. You can choose to
 * derive it, or implement it manually. If you derive it, you should derive all four traits. If you
 * implement it manually, you should manually implement all four traits, based on the
 * implementation of `Ord`.
 *
 * Here's an example where you want to define the `Character` comparison by `health` and
 * `experience` only, disregarding the field `mana`:
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Character(
 *     public var health: UInt,
 *     public var experience: UInt,
 *     public var mana: Float,
 * ) {
 *     public open fun cmp(other: Character): Ordering {
 *         return this.experience.cmp(other.experience).then(this.health.cmp(other.health))
 *     }
 *
 *     public open fun partialCmp(other: Character): Ordering? {
 *         return this.cmp(other)
 *     }
 *
 *     public open fun eq(other: Character): Boolean {
 *         return this.health == other.health && this.experience == other.experience
 *     }
 * }
 * ```
 *
 * If all you need is to `slice.sort` a type by a field value, it can be simpler to use
 * `slice.sortByKey`.
 *
 * ## Examples of incorrect `Ord` implementations
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Character(public var health: Float) {
 *     public open fun cmp(other: Character): Ordering {
 *         return if (this.health < other.health) {
 *             Ordering.Less
 *         } else if (this.health > other.health) {
 *             Ordering.Greater
 *         } else {
 *             Ordering.Equal
 *         }
 *     }
 *
 *     public open fun partialCmp(other: Character): Ordering? {
 *         return this.cmp(other)
 *     }
 *
 *     public open fun eq(other: Character): Boolean {
 *         return this.health == other.health
 *     }
 * }
 * val a = Character(health = 4.5f)
 * val b = Character(health = Float.NaN)
 * // Mistake: floating-point values do not form a total order and using the built-in comparison
 * // operands to implement `Ord` irregardless of that reality does not change it. Use
 * // `Float.totalCmp` if you need a total order for floating-point values.
 * // Reflexivity requirement of `Ord` is not given.
 * check(a == a)
 * check(b != b)
 * // Antisymmetry requirement of `Ord` is not given. Only one of a < c and c < a is allowed to be
 * // true, not both or neither.
 * check((if (a < b) 1u else 0u).toUByte() + (if (b < a) 1u else 0u).toUByte() == 0u.toUByte())
 * ```
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Character(public var health: UInt, public var experience: UInt) {
 *     public open fun partialCmp(other: Character): Ordering? {
 *         return this.cmp(other)
 *     }
 *
 *     public open fun cmp(other: Character): Ordering {
 *         return if (this.health < 50u) {
 *             this.health.cmp(other.health)
 *         } else {
 *             this.experience.cmp(other.experience)
 *         }
 *     }
 *
 *     // For performance reasons implementing `PartialEq` this way is not the idiomatic way, but it
 *     // ensures consistent behavior between `PartialEq`, `PartialOrd` and `Ord` in this example.
 *     public open fun eq(other: Character): Boolean {
 *         return this.cmp(other) == Ordering.Equal
 *     }
 * }
 * val a = Character(health = 3u, experience = 5u)
 * val b = Character(health = 10u, experience = 77u)
 * val c = Character(health = 143u, experience = 2u)
 * // Mistake: The implementation of `Ord` compares different fields depending on the value of
 * // `self.health`, the resulting order is not total.
 * // Transitivity requirement of `Ord` is not given. If a is smaller than b and b is smaller than
 * // c, by transitive property a must also be smaller than c.
 * check(a < b && b < c && c < a)
 * // Antisymmetry requirement of `Ord` is not given. Only one of a < c and c < a is allowed to be
 * // true, not both or neither.
 * check((if (a < c) 1u else 0u).toUByte() + (if (c < a) 1u else 0u).toUByte() == 2u.toUByte())
 * ```
 *
 * The documentation of [PartialOrd] contains further examples, for example it's wrong for
 * [PartialOrd] and [PartialEq] to disagree.
 *
 * [cmp]: Ord.cmp
 */
public interface Ord<T> : Eq, PartialOrd<T> {
    /**
     * This method returns an [Ordering] between `self` and `other`.
     *
     * By convention, `self.cmp(other)` returns the ordering matching the expression
     * `self<operator> other` if true.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(5.cmp(10) == Ordering.Less)
     * check(10.cmp(5) == Ordering.Greater)
     * check(5.cmp(5) == Ordering.Equal)
     * ```
     */
    public fun cmp(other: T): Ordering

    /**
     * Compares and returns the maximum of two values.
     *
     * Returns the second argument if the comparison determines them to be equal.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.max(2) == 2)
     * check(2.max(2) == 2)
     * ```
     * ```kotlin
     * import std.cmp.Ordering
     *
     * open class Equal(public var value: String) {
     *     public var field0: String
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun eq(other: Equal): Boolean {
     *         return true
     *     }
     *
     *     public open fun partialCmp(other: Equal): Ordering? {
     *         return Ordering.Equal
     *     }
     *
     *     public open fun cmp(other: Equal): Ordering {
     *         return Ordering.Equal
     *     }
     * }
     * check(Equal("self").max(Equal("other")).first == "other")
     * ```
     */
    public fun max(other: T): T

    /**
     * Compares and returns the minimum of two values.
     *
     * Returns the first argument if the comparison determines them to be equal.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.min(2) == 1)
     * check(2.min(2) == 2)
     * ```
     * ```kotlin
     * import std.cmp.Ordering
     *
     * open class Equal(public var value: String) {
     *     public var field0: String
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun eq(other: Equal): Boolean {
     *         return true
     *     }
     *
     *     public open fun partialCmp(other: Equal): Ordering? {
     *         return Ordering.Equal
     *     }
     *
     *     public open fun cmp(other: Equal): Ordering {
     *         return Ordering.Equal
     *     }
     * }
     * check(Equal("self").min(Equal("other")).first == "self")
     * ```
     */
    public fun min(other: T): T

    /**
     * Restrict a value to a certain interval.
     *
     * Returns `max` if `self` is greater than `max`, and `min` if `self` is
     * less than `min`. Otherwise this returns `self`.
     *
     * # Panics
     *
     * Panics if `min> max`.
     *
     * # Examples
     *
     * ```kotlin
     * check((-3).clamp(-2, 1) == -2)
     * check(0.clamp(-2, 1) == 0)
     * check(2.clamp(-2, 1) == 1)
     * ```
     */
    public fun clamp(min: T, max: T): T

    /**
     * Restrict a value to a certain range.
     *
     * This is equal to `max`, `min`, or `clamp`, depending on whether the range is `min ..`,
     * `..= max`, or `min ..= max`, respectively. Exclusive ranges are not permitted.
     *
     * # Panics
     *
     * Panics on `min ..= max` if `min> max`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(clampTo)
     * public fun doctestEntry() {
     *     check((-3).clampTo(-2..1) == -2)
     *     check(0.clampTo(-2..1) == 0)
     *     check(2.clampTo(0..1) == 1)
     *     check(5.clampTo(7 until Int.MAX_VALUE) == 7)
     * }
     * ```
     */
    public fun <R> clampTo(range: R): T where R : ClampBounds<T> = range.clamp(this)
}

// Note(sizedHierarchy): remove this

/**
 * Trait for types that form a [partial order](https://en.wikipedia.org/wiki/PartialOrder).
 *
 * The `lt`, `le`, `gt`, and `ge` methods of this trait can be called using the `<`, `<=`, `>`, and
 * `>=` operators, respectively.
 *
 * This trait should **only** contain the comparison logic for a type **if one plans on only
 * implementing `PartialOrd` but not [Ord]**. Otherwise the comparison logic should be in [Ord]
 * and this trait implemented with `Some(self.cmp(other))`.
 *
 * The methods of this trait must be consistent with each other and with those of [PartialEq].
 * The following conditions must hold:
 *
 * 1. `a == b` if and only if `partialCmp(a, b) == Some(Equal)`.
 * 2. `a<b` if and only if `partialCmp(a, b) == Some(Less)`
 * 3. `a> b` if and only if `partialCmp(a, b) == Some(Greater)`
 * 4. `a <= b` if and only if `a<b || a == b`
 * 5. `a >= b` if and only if `a> b || a == b`
 * 6. `a != b` if and only if `!(a == b)`.
 *
 * Conditions 2–5 above are ensured by the default implementation. Condition 6 is already ensured
 * by [PartialEq].
 *
 * If [Ord] is also implemented for `Self` and `Rhs`, it must also be consistent with
 * `partialCmp` (see the documentation of that trait for the exact requirements). It's easy to
 * accidentally make them disagree by deriving some of the traits and manually implementing others.
 *
 * The comparison relations must satisfy the following conditions (for all `a`, `b`, `c` of type
 * `A`, `B`, `C`):
 *
 * - **Transitivity**: if `A: PartialOrd<B>` and `B: PartialOrd<C>` and `A: PartialOrd<C>`, then `a
 *   < b`and`b < c`implies`a < c`.The same must hold for both`==`and`>`. This must also
 *   work for longer chains, such as when `A: PartialOrd<B>`, `B: PartialOrd<C>`, `C:
 *   PartialOrd<D>`, and`A: PartialOrd<D>` all exist.
 * - **Duality**: if `A: PartialOrd<B>` and `B: PartialOrd<A>`, then `a<b` if and only if `b >
 *   a`.
 *
 * Note that the `B: PartialOrd<A>` (dual) and `A: PartialOrd<C>` (transitive) impls are not forced
 * to exist, but these requirements apply whenever they do exist.
 *
 * Violating these requirements is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on the correctness of these
 * methods.
 *
 * ## Cross-crate considerations
 *
 * Upholding the requirements stated above can become tricky when one crate implements `PartialOrd`
 * for a type of another crate (i.e., to allow comparing one of its own types with a type from the
 * standard library). The recommendation is to never implement this trait for a foreign type. In
 * other words, such a crate should do `implementation PartialOrd<ForeignType> for LocalType`, but it should
 * *not* do `implementation PartialOrd<LocalType> for ForeignType`.
 *
 * This avoids the problem of transitive chains that criss-cross crate boundaries: for all local
 * types `T`, you may assume that no other crate will add `implementation`s that allow comparing `T<U`. In
 * other words, if other crates add `implementation`s that allow building longer transitive chains `U1 < ...
 * < T < V1 < ...`, then all the types that appear to the right of`T` must be types that the crate
 * defining `T` already knows about. This rules out transitive chains where downstream crates can
 * add new `implementation`s that "stitch together" comparisons of foreign types in ways that violate
 * transitivity.
 *
 * Not having such foreign `implementation`s also avoids forward compatibility issues where one crate adding
 * more `PartialOrd` implementations can cause build failures in downstream crates.
 *
 * ## Corollaries
 *
 * The following corollaries follow from the above requirements:
 *
 * - irreflexivity of `<` and `>`: `!(a<a)`, `!(a> a)`
 * - transitivity of `>`: if `a> b` and `b> c` then `a> c`
 * - duality of `partialCmp`: `partialCmp(a, b) == partialCmp(b, a).map(Ordering.reverse)`
 *
 * ## Strict and non-strict partial orders
 *
 * The `<` and `>` operators behave according to a *strict* partial order. However, `<=` and `>=`
 * do **not** behave according to a *non-strict* partial order. That is because mathematically, a
 * non-strict partial order would require reflexivity, i.e. `a <= a` would need to be true for
 * every `a`. This isn't always the case for types that implement `PartialOrd`, for example:
 *
 * ```kotlin
 * val a = Double.NaN
 * check(a <= a == false)
 * ```
 *
 * ## Derivable
 *
 * This trait can be used with `@derive`.
 *
 * When `derive`d on structs, it will produce a
 * [lexicographic](https://en.wikipedia.org/wiki/LexicographicOrder) ordering based on the
 * top-to-bottom declaration order of the struct's members.
 *
 * When `derive`d on enums, variants are primarily ordered by their discriminants. Secondarily,
 * they are ordered by their fields. By default, the discriminant is smallest for variants at the
 * top, and largest for variants at the bottom. Here's an example:
 *
 * ```kotlin
 * sealed class E {
 *     public data object Top : E()
 *     public data object Bottom : E()
 *
 *     public fun rustEquals(other: E): Boolean = this == other
 * }
 * check(E.Top < E.Bottom)
 * ```
 *
 * However, manually setting the discriminants can override this default behavior:
 *
 * ```kotlin
 * sealed class E(public val value: Int) {
 *     public data object Top : E(2)
 *     public data object Bottom : E(1)
 *
 *     public fun rustEquals(other: E): Boolean = this == other
 * }
 * check(E.Bottom < E.Top)
 * ```
 *
 * ## How can I implement `PartialOrd`?
 *
 * `PartialOrd` only requires implementation of the [partialCmp] method, with the others
 * generated from default implementations.
 *
 * However it remains possible to implement the others separately for types which do not have a
 * total order. For example, for floating point numbers, `NaN<0 == false` and `NaN >= 0 == false`
 * (cf. IEEE 754-2008 section 5.11).
 *
 * `PartialOrd` requires your type to be [PartialEq].
 *
 * If your type is [Ord], you can implement [partialCmp] by using [cmp]:
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Person(
 *     public var id: UInt,
 *     public var name: String,
 *     public var height: UInt,
 * ) {
 *     public open fun partialCmp(other: Person): Ordering? {
 *         return this.cmp(other)
 *     }
 *
 *     public open fun cmp(other: Person): Ordering {
 *         return this.height.cmp(other.height)
 *     }
 *
 *     public open fun eq(other: Person): Boolean {
 *         return this.height == other.height
 *     }
 * }
 * ```
 *
 * You may also find it useful to use [partialCmp] on your type's fields. Here is an example of
 * `Person` types who have a floating-point `height` field that is the only field to be used for
 * sorting:
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Person(
 *     public var id: UInt,
 *     public var name: String,
 *     public var height: Double,
 * ) {
 *     public open fun partialCmp(other: Person): Ordering? {
 *         return this.height.partialCmp(other.height)
 *     }
 *
 *     public open fun eq(other: Person): Boolean {
 *         return this.height == other.height
 *     }
 * }
 * ```
 *
 * ## Examples of incorrect `PartialOrd` implementations
 *
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Character(public var health: UInt, public var experience: UInt) {
 *     public open fun partialCmp(other: Character): Ordering? {
 *         return this.health.cmp(other.health)
 *     }
 * }
 * val a = Character(health = 10u, experience = 5u)
 * val b = Character(health = 10u, experience = 77u)
 * // Mistake: `PartialEq` and `PartialOrd` disagree with each other.
 * check(a.partialCmp(b)!! == Ordering.Equal)
 * // a == b according to `PartialOrd`.
 * check(a != b)
 * // a != b according to `PartialEq`.
 * ```
 *
 * # Examples
 *
 * ```kotlin
 * val x: UInt = 0u
 * val y: UInt = 1u
 * check(x < y == true)
 * check(x.lt(y) == true)
 * ```
 *
 * [partialCmp]: PartialOrd.partialCmp
 * [cmp]: Ord.cmp
 */
public interface PartialOrd<Rhs> : PartialEq<Rhs> {
    /**
     * This method returns an ordering between `self` and `other` values if one exists.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * val result = 1.0.partialCmp(2.0)
     * check(result == Ordering.Less)
     * val result1 = 1.0.partialCmp(1.0)
     * check(result1 == Ordering.Equal)
     * val result2 = 2.0.partialCmp(1.0)
     * check(result2 == Ordering.Greater)
     * ```
     *
     * When comparison is impossible:
     *
     * ```kotlin
     * val result = Double.NaN.partialCmp(1.0)
     * check(result == null)
     * ```
     */
    public fun partialCmp(other: Rhs): Ordering?

    /**
     * Tests less than (for `self` and `other`) and is used by the `<` operator.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.0 < 1.0 == false)
     * check(1.0 < 2.0 == true)
     * check(2.0 < 1.0 == false)
     * ```
     */
    public fun lt(other: Rhs): Boolean = this.partialCmp(other).isSomeAnd({ it.isLt() })

    /**
     * Tests less than or equal to (for `self` and `other`) and is used by the
     * `<=` operator.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.0 <= 1.0 == true)
     * check(1.0 <= 2.0 == true)
     * check(2.0 <= 1.0 == false)
     * ```
     */
    public fun le(other: Rhs): Boolean = this.partialCmp(other).isSomeAnd({ it.isLe() })

    /**
     * Tests greater than (for `self` and `other`) and is used by the `>`
     * operator.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.0 > 1.0 == false)
     * check(1.0 > 2.0 == false)
     * check(2.0 > 1.0 == true)
     * ```
     */
    public fun gt(other: Rhs): Boolean = this.partialCmp(other).isSomeAnd({ it.isGt() })

    /**
     * Tests greater than or equal to (for `self` and `other`) and is used by
     * the `>=` operator.
     *
     * # Examples
     *
     * ```kotlin
     * check(1.0 >= 1.0 == true)
     * check(1.0 >= 2.0 == false)
     * check(2.0 >= 1.0 == true)
     * ```
     */
    public fun ge(other: Rhs): Boolean = this.partialCmp(other).isSomeAnd({ it.isGe() })

    /**
     * If `self == other`, returns `Continue(())`.
     * Otherwise, returns `Break(self<other)`, or equivalently `Break(self <= other)`.
     * The only difference when chaining `<` and `<=` is how an equality result is
     * interpreted at the end of the chain.
     *
     * This is useful for chaining together calls when implementing a lexical
     * `PartialOrd.lt`, as it allows types (like primitives) which can cheaply
     * check `==` and `<` separately to do rather than needing to calculate
     * (then optimize out) the three-way `Ordering` result.
     */
    public fun chainingLess(other: Rhs): ControlFlow<Boolean, Unit> = defaultChainingImpl(this, other, { it.isLt() })

    /**
     * Same as `__chaining_less`, but for `>`/`>=` instead of `<`/`<=`.
     */
    public fun chainingGreater(other: Rhs): ControlFlow<Boolean, Unit> = defaultChainingImpl(this, other, { it.isGt() })
}

public fun <T, U> defaultChainingImpl(lhs: T, rhs: U, p: (Ordering) -> Boolean, partialCmpTFn: (T, Rhs) -> Ordering?): ControlFlow<Boolean, Unit> where T : PartialOrd<U> {
    // It's important that this only call `partialCmp` once, not call `eq` then
    // one of the relational operators.  We don't want to `bcmp`-then-`memcp` a
    // `String`, for example, or similarly for other data structures (#108157).
    return run {
        val matchVal = partialCmpTFn(lhs, rhs)
        when {
            matchVal != null && matchVal == Equal -> ControlFlow.Continue()
            matchVal != null -> {
                val c = matchVal
                ControlFlow.Break(p(c))
            }
            else -> ControlFlow.Break(false)
        }
    }
}

/**
 * Compares and returns the minimum of two values.
 *
 * Returns the first argument if the comparison determines them to be equal.
 *
 * Internally uses an alias to [Ord.min].
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * check(minOf(1, 2) == 1)
 * check(minOf(2, 2) == 2)
 * ```
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Equal(public var value: String) {
 *     public var field0: String
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun eq(other: Equal): Boolean {
 *         return true
 *     }
 *
 *     public open fun partialCmp(other: Equal): Ordering? {
 *         return Ordering.Equal
 *     }
 *
 *     public open fun cmp(other: Equal): Ordering {
 *         return Ordering.Equal
 *     }
 * }
 * check(minOf(Equal("v1"), Equal("v2")).first == "v1")
 * ```
 */
public fun <T : Ord<T>> min(v1: T, v2: T): T = v1.min(v2)

/**
 * Returns the minimum of two values with respect to the specified comparison function.
 *
 * Returns the first argument if the comparison determines them to be equal.
 *
 * The parameter order is preserved when calling the `compare` function, i.e. `v1` is
 * always passed as the first argument and `v2` as the second.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * val absCmp = { x: Int, y: Int -> x.abs().cmp(y.abs()) }
 * val result = cmp.minBy(2, -1, absCmp)
 * check(result == -1)
 * val result1 = cmp.minBy(2, -3, absCmp)
 * check(result1 == 2)
 * val result2 = cmp.minBy(1, -1, absCmp)
 * check(result2 == 1)
 * ```
 */
public fun <T> minBy(v1: T, v2: T, compare: (T, T) -> Ordering): T =
    if (compare(v1, v2).isLe()) {
        v1
    } else {
        v2
    }

/**
 * Returns the element that gives the minimum value from the specified function.
 *
 * Returns the first argument if the comparison determines them to be equal.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * val result = cmp.minByKey(2, -1, { x: Int -> x.abs() })
 * check(result == -1)
 * val result1 = cmp.minByKey(2, -3, { x: Int -> x.abs() })
 * check(result1 == 2)
 * val result2 = cmp.minByKey(1, -1, { x: Int -> x.abs() })
 * check(result2 == 1)
 * ```
 */
public fun <T, K> minByKey(v1: T, v2: T, f: (T) -> K): T where K : Ord<K> =
    if (f(v2) < f(v1)) {
        v2
    } else {
        v1
    }

/**
 * Compares and returns the maximum of two values.
 *
 * Returns the second argument if the comparison determines them to be equal.
 *
 * Internally uses an alias to [Ord.max].
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * check(cmp.max(1, 2) == 2)
 * check(cmp.max(2, 2) == 2)
 * ```
 * ```kotlin
 * import std.cmp.Ordering
 *
 * open class Equal(public var value: String) {
 *     public var field0: String
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun eq(other: Equal): Boolean {
 *         return true
 *     }
 *
 *     public open fun partialCmp(other: Equal): Ordering? {
 *         return Ordering.Equal
 *     }
 *
 *     public open fun cmp(other: Equal): Ordering {
 *         return Ordering.Equal
 *     }
 * }
 * check(cmp.max(Equal("v1"), Equal("v2")).first == "v2")
 * ```
 */
public fun <T : Ord<T>> max(v1: T, v2: T): T = v1.max(v2)

/**
 * Returns the maximum of two values with respect to the specified comparison function.
 *
 * Returns the second argument if the comparison determines them to be equal.
 *
 * The parameter order is preserved when calling the `compare` function, i.e. `v1` is
 * always passed as the first argument and `v2` as the second.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * val absCmp = { x: Int, y: Int -> x.abs().cmp(y.abs()) }
 * val result = cmp.maxBy(3, -2, absCmp)
 * check(result == 3)
 * val result1 = cmp.maxBy(1, -2, absCmp)
 * check(result1 == -2)
 * val result2 = cmp.maxBy(1, -1, absCmp)
 * check(result2 == -1)
 * ```
 */
public fun <T> maxBy(v1: T, v2: T, compare: (T, T) -> Ordering): T =
    if (compare(v1, v2).isGt()) {
        v1
    } else {
        v2
    }

/**
 * Returns the element that gives the maximum value from the specified function.
 *
 * Returns the second argument if the comparison determines them to be equal.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * val result = cmp.maxByKey(3, -2, { x: Int -> x.abs() })
 * check(result == 3)
 * val result1 = cmp.maxByKey(1, -2, { x: Int -> x.abs() })
 * check(result1 == -2)
 * val result2 = cmp.maxByKey(1, -1, { x: Int -> x.abs() })
 * check(result2 == -1)
 * ```
 */
public fun <T, K> maxByKey(v1: T, v2: T, f: (T) -> K): T where K : Ord<K> =
    if (f(v2) < f(v1)) {
        v1
    } else {
        v2
    }

/**
 * Compares and sorts two values, returning minimum and maximum.
 *
 * Returns `[v1, v2]` if the comparison determines them to be equal.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * @feature(cmpMinmax)
 * public fun doctestEntry() {
 *     check(cmp.minmax(1, 2) == listOf(1, 2))
 *     check(cmp.minmax(2, 1) == listOf(1, 2))
 *     // You can destructure the result using array patterns
 *     val (min, max) = cmp.minmax(42, 17)
 *     check(min == 17)
 *     check(max == 42)
 * }
 * ```
 * ```kotlin
 * import std.cmp.Ordering
 *
 * @feature(cmpMinmax)
 * public fun doctestEntry() {
 *     open class Equal(public var value: String) {
 *         public var field0: String
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun eq(other: Equal): Boolean {
 *             return true
 *         }
 *
 *         public open fun partialCmp(other: Equal): Ordering? {
 *             return Ordering.Equal
 *         }
 *
 *         public open fun cmp(other: Equal): Ordering {
 *             return Ordering.Equal
 *         }
 *     }
 *     check(cmp.minmax(Equal("v1"), Equal("v2")).map({ v -> v.first }) == listOf("v1", "v2"))
 * }
 * ```
 */
public fun <T> minmax(v1: T, v2: T): List<T> where T : Ord<T> =
    if ((kmCompareValues(v2, v1) < 0)) {
        listOf(v2, v1)
    } else {
        listOf(v1, v2)
    }

/**
 * Returns minimum and maximum values with respect to the specified comparison function.
 *
 * Returns `[v1, v2]` if the comparison determines them to be equal.
 *
 * The parameter order is preserved when calling the `compare` function, i.e. `v1` is
 * always passed as the first argument and `v2` as the second.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * @feature(cmpMinmax)
 * public fun doctestEntry() {
 *     val absCmp = { x: Int, y: Int -> x.abs().cmp(y.abs()) }
 *     check(cmp.minmaxBy(-2, 1, absCmp) == listOf(1, -2))
 *     check(cmp.minmaxBy(-1, 2, absCmp) == listOf(-1, 2))
 *     check(cmp.minmaxBy(-2, 2, absCmp) == listOf(-2, 2))
 *     // You can destructure the result using array patterns
 *     val (min, max) = cmp.minmaxBy(-42, 17, absCmp)
 *     check(min == 17)
 *     check(max == -42)
 * }
 * ```
 */
public fun <T> minmaxBy(v1: T, v2: T, compare: (T, T) -> Ordering): List<T> =
    if (compare(v1, v2).isLe()) {
        listOf(v1, v2)
    } else {
        listOf(v2, v1)
    }

/**
 * Returns minimum and maximum values with respect to the specified key function.
 *
 * Returns `[v1, v2]` if the comparison determines them to be equal.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * @feature(cmpMinmax)
 * public fun doctestEntry() {
 *     check(cmp.minmaxByKey(-2, 1, { x: Int -> x.abs() }) == listOf(1, -2))
 *     check(cmp.minmaxByKey(-2, 2, { x: Int -> x.abs() }) == listOf(-2, 2))
 *     // You can destructure the result using array patterns
 *     val (min, max) = cmp.minmaxByKey(-42, 17, { x: Int -> x.abs() })
 *     check(min == 17)
 *     check(max == -42)
 * }
 * ```
 */
public fun <T, K> minmaxByKey(v1: T, v2: T, f: (T) -> K): List<T> where K : Ord<K> =
    if (f(v2) < f(v1)) {
        listOf(v2, v1)
    } else {
        listOf(v1, v2)
    }

/**
 * Implementation detail for [smallest] and [largest].
 * Marker indicating that `Self` is a tuple where all members are of the type `T`.
 * Cannot be an associated type as we require the empty tuple to implement this trait
 * for all types `T`.
 */
public interface HomogeneousTuple<T>

// Note: splat interacts poorly with the double linking of `core` in tests

/**
 * Compares and returns the minimum of the provided values.
 *
 * Returns the first argument if the comparison determines them to be equal.
 *
 * Internally uses [Ord.min].
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * @feature(cmpSplat)
 * public fun doctestEntry() {
 *     check(cmp.smallest(1) == 1)
 *     check(cmp.smallest(1, 2) == 1)
 *     check(cmp.smallest(3, 2, 1) == 1)
 *     check(cmp.smallest(1, 2, 3, 4) == 1)
 * }
 * ```
 * ```kotlin
 * import std.cmp.Ordering
 *
 * @feature(cmpSplat)
 * public fun doctestEntry() {
 *     open class Equal(public var value: String) {
 *         public var field0: String
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun eq(other: Equal): Boolean {
 *             return true
 *         }
 *
 *         public open fun partialCmp(other: Equal): Ordering? {
 *             return Ordering.Equal
 *         }
 *
 *         public open fun cmp(other: Equal): Ordering {
 *             return Ordering.Equal
 *         }
 *     }
 *     check(cmp.smallest(Equal("v1"), Equal("v2")).first == "v1")
 * }
 * ```
 *
 * # Stability
 *
 * This function is added in its current form as an experiment in variadic functions.
 * In a future iteration of the feature, this function may be removed in favour of
 * making [min] itself variadic instead.
 */
public fun <T : Ord<T>> smallest(v1: T, args: SmallestArgs<T>): T = SmallestArgs.smallest(v1, args)

/**
 * Implementation detail for [smallest].
 */
public interface SmallestArgs<T, SelfType> : HomogeneousTuple<T> {
    /**
     * Reduces all elements of a homogeneous tuple to its smallest value.
     */
    public fun smallest(v1: T, args: SelfType): T
}

public fun <T> smallestArgsSmallest(v1: T, tupleArg1: Unit): T where T : Ord<T> = v1

public fun <T> smallestArgsSmallestT(v1: T, tupleArg1: T): T where T : Ord<T> {
    val x11 = tupleArg1
    return v1.min(x11)
}

public fun <T> smallestArgsSmallestTT(v1: T, tupleArg1: Pair<T, T>): T where T : Ord<T> {
    val (x10, x11) = tupleArg1
    return v1.min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTT(v1: T, tupleArg1: Triple<T, T, T>): T where T : Ord<T> {
    val (x9, x10, x11) = tupleArg1
    return v1.min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x8, x9, x10, x11) = tupleArg1
    return v1.min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x4).min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x3).min(x4).min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x2).min(x3).min(x4).min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x1).min(x2).min(x3).min(x4).min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

public fun <T> smallestArgsSmallestTTTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.min(x0).min(x1).min(x2).min(x3).min(x4).min(x5).min(x6).min(x7).min(x8).min(x9).min(x10).min(x11)
}

// Note: splat interacts poorly with the double linking of `core` in tests

/**
 * Compares and returns the maximum of the provided values.
 *
 * Returns the last argument if the comparison determines them to be equal.
 *
 * Internally uses [Ord.max].
 *
 * # Examples
 *
 * ```kotlin
 * import std.cmp
 *
 * @feature(cmpSplat)
 * public fun doctestEntry() {
 *     check(cmp.largest(1) == 1)
 *     check(cmp.largest(1, 2) == 2)
 *     check(cmp.largest(3, 2, 1) == 3)
 *     check(cmp.largest(1, 2, 3, 4) == 4)
 * }
 * ```
 * ```kotlin
 * import std.cmp.Ordering
 *
 * @feature(cmpSplat)
 * public fun doctestEntry() {
 *     open class Equal(public var value: String) {
 *         public var field0: String
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun eq(other: Equal): Boolean {
 *             return true
 *         }
 *
 *         public open fun partialCmp(other: Equal): Ordering? {
 *             return Ordering.Equal
 *         }
 *
 *         public open fun cmp(other: Equal): Ordering {
 *             return Ordering.Equal
 *         }
 *     }
 *     check(cmp.largest(Equal("v1"), Equal("v2")).first == "v2")
 * }
 * ```
 *
 * # Stability
 *
 * This function is added in its current form as an experiment in variadic functions.
 * In a future iteration of the feature, this function may be removed in favour of
 * making [max] itself variadic instead.
 */
public fun <T : Ord<T>> largest(v1: T, args: LargestArgs<T>): T = LargestArgs.largest(v1, args)

/**
 * Implementation detail for [largest].
 */
public interface LargestArgs<T, SelfType> : HomogeneousTuple<T> {
    /**
     * Reduces all elements of a homogeneous tuple to its largest value.
     */
    public fun largest(v1: T, args: SelfType): T
}

public fun <T> largestArgsLargest(v1: T, tupleArg1: Unit): T where T : Ord<T> = v1

public fun <T> largestArgsLargestT(v1: T, tupleArg1: T): T where T : Ord<T> {
    val x11 = tupleArg1
    return v1.max(x11)
}

public fun <T> largestArgsLargestTT(v1: T, tupleArg1: Pair<T, T>): T where T : Ord<T> {
    val (x10, x11) = tupleArg1
    return v1.max(x10).max(x11)
}

public fun <T> largestArgsLargestTTT(v1: T, tupleArg1: Triple<T, T, T>): T where T : Ord<T> {
    val (x9, x10, x11) = tupleArg1
    return v1.max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x8, x9, x10, x11) = tupleArg1
    return v1.max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x4).max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x3).max(x4).max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x2).max(x3).max(x4).max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x1).max(x2).max(x3).max(x4).max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun <T> largestArgsLargestTTTTTTTTTTTT(v1: T, tupleArg1: List<Any?>): T where T : Ord<T> {
    val (x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11) = tupleArg1
    return v1.max(x0).max(x1).max(x2).max(x3).max(x4).max(x5).max(x6).max(x7).max(x8).max(x9).max(x10).max(x11)
}

public fun Boolean.eq(other: Boolean): Boolean = this == other

public fun Boolean.ne(other: Boolean): Boolean = this != other

public fun Char.eq(other: Char): Boolean = this == other

public fun Char.ne(other: Char): Boolean = this != other

public fun Int.eq(other: Int): Boolean = this == other

public fun Int.ne(other: Int): Boolean = this != other

public fun UByte.eq(other: UByte): Boolean = this == other

public fun UByte.ne(other: UByte): Boolean = this != other

public fun UShort.eq(other: UShort): Boolean = this == other

public fun UShort.ne(other: UShort): Boolean = this != other

public fun UInt.eq(other: UInt): Boolean = this == other

public fun UInt.ne(other: UInt): Boolean = this != other

public fun ULong.eq(other: ULong): Boolean = this == other

public fun ULong.ne(other: ULong): Boolean = this != other

public fun U128.eq(other: U128): Boolean = this == other

public fun U128.ne(other: U128): Boolean = this != other

public fun Byte.eq(other: Byte): Boolean = this == other

public fun Byte.ne(other: Byte): Boolean = this != other

public fun Short.eq(other: Short): Boolean = this == other

public fun Short.ne(other: Short): Boolean = this != other

public fun Long.eq(other: Long): Boolean = this == other

public fun Long.ne(other: Long): Boolean = this != other

public fun I128.eq(other: I128): Boolean = this == other

public fun I128.ne(other: I128): Boolean = this != other

public fun F16.eq(other: F16): Boolean = this == other

public fun F16.ne(other: F16): Boolean = this != other

public fun kotlin.Float.eq(other: kotlin.Float): Boolean = this == other

public fun kotlin.Float.ne(other: kotlin.Float): Boolean = this != other

public fun Double.eq(other: Double): Boolean = this == other

public fun Double.ne(other: Double): Boolean = this != other

public fun F128.eq(other: F128): Boolean = this == other

public fun F128.ne(other: F128): Boolean = this != other

public fun Unit.eq(other: Unit): Boolean = true

public fun Unit.ne(other: Unit): Boolean = false

public fun Unit.partialCmp(arg0: Unit): Ordering? = Equal

public fun Boolean.partialCmp(other: Boolean): Ordering? = this.cmp(other)

public fun Boolean.lt(other: Boolean): Boolean = this < other

public fun Boolean.le(other: Boolean): Boolean = this <= other

public fun Boolean.gt(other: Boolean): Boolean = this > other

public fun Boolean.ge(other: Boolean): Boolean = this >= other

public fun Boolean.chainingLess(other: Boolean): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Boolean.chainingGreater(other: Boolean): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun F16.partialCmp(other: F16): Ordering? =
    run {
        val matchVal = Pair(this <= other, this >= other)
        when {
            matchVal.first == false && matchVal.second == false -> null
            matchVal.first == false && matchVal.second == true -> Greater
            matchVal.first == true && matchVal.second == false -> Less
            else -> Equal
        }
    }

public fun F16.lt(other: F16): Boolean = this < other

public fun F16.le(other: F16): Boolean = this <= other

public fun F16.gt(other: F16): Boolean = this > other

public fun F16.ge(other: F16): Boolean = this >= other

public fun F16.chainingLess(other: F16): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun F16.chainingGreater(other: F16): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun kotlin.Float.partialCmp(other: kotlin.Float): Ordering? =
    run {
        val matchVal = Pair(this <= other, this >= other)
        when {
            matchVal.first == false && matchVal.second == false -> null
            matchVal.first == false && matchVal.second == true -> Greater
            matchVal.first == true && matchVal.second == false -> Less
            else -> Equal
        }
    }

public fun kotlin.Float.lt(other: kotlin.Float): Boolean = this < other

public fun kotlin.Float.le(other: kotlin.Float): Boolean = this <= other

public fun kotlin.Float.gt(other: kotlin.Float): Boolean = this > other

public fun kotlin.Float.ge(other: kotlin.Float): Boolean = this >= other

public fun kotlin.Float.chainingLess(other: kotlin.Float): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun kotlin.Float.chainingGreater(other: kotlin.Float): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Double.partialCmp(other: Double): Ordering? =
    run {
        val matchVal = Pair(this <= other, this >= other)
        when {
            matchVal.first == false && matchVal.second == false -> null
            matchVal.first == false && matchVal.second == true -> Greater
            matchVal.first == true && matchVal.second == false -> Less
            else -> Equal
        }
    }

public fun Double.lt(other: Double): Boolean = this < other

public fun Double.le(other: Double): Boolean = this <= other

public fun Double.gt(other: Double): Boolean = this > other

public fun Double.ge(other: Double): Boolean = this >= other

public fun Double.chainingLess(other: Double): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Double.chainingGreater(other: Double): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun F128.partialCmp(other: F128): Ordering? =
    run {
        val matchVal = Pair(this <= other, this >= other)
        when {
            matchVal.first == false && matchVal.second == false -> null
            matchVal.first == false && matchVal.second == true -> Greater
            matchVal.first == true && matchVal.second == false -> Less
            else -> Equal
        }
    }

public fun F128.lt(other: F128): Boolean = this < other

public fun F128.le(other: F128): Boolean = this <= other

public fun F128.gt(other: F128): Boolean = this > other

public fun F128.ge(other: F128): Boolean = this >= other

public fun F128.chainingLess(other: F128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun F128.chainingGreater(other: F128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Unit.cmp(other: Unit): Ordering = Equal

public fun Boolean.cmp(other: Boolean): Ordering {
    // Casting to i8's and converting the difference to an Ordering generates
    // more optimal assembly.
    // See <https://github.com/rust-lang/rust/issues/66780> for more info.
    return when ((this.toByte()) - (other.toByte())) {
        -1 -> Less
        0 -> Equal
        1 -> Greater
        // SAFETY: bool as i8 returns 0 or 1, so the difference can't be anything else
        else -> unreachableUnchecked()
    }
}

public fun Boolean.min(other: Boolean): Boolean = this and other

public fun Boolean.max(other: Boolean): Boolean = this or other

public fun Boolean.clamp(min: Boolean, max: Boolean): Boolean {
    check(min <= max)
    return this.max(min).min(max)
}

public fun Char.partialCmp(other: Char): Ordering? = threeWayCompare(this, other)

public fun Char.lt(other: Char): Boolean = this < other

public fun Char.le(other: Char): Boolean = this <= other

public fun Char.gt(other: Char): Boolean = this > other

public fun Char.ge(other: Char): Boolean = this >= other

public fun Char.chainingLess(other: Char): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Char.chainingGreater(other: Char): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Char.cmp(other: Char): Ordering = threeWayCompare(this, other)

public fun Char.clamp(min: Char, max: Char): Char {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun Char.min(other: Char): Char {
    val c = minOf(this.toUInt(), other.toUInt())
    return Char.fromU32Unchecked(c)
}

public fun Char.max(other: Char): Char {
    val c = maxOf(this.toUInt(), other.toUInt())
    return Char.fromU32Unchecked(c)
}

public fun Int.partialCmp(other: Int): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Int.lt(other: Int): Boolean = this < other

public fun Int.le(other: Int): Boolean = this <= other

public fun Int.gt(other: Int): Boolean = this > other

public fun Int.ge(other: Int): Boolean = this >= other

public fun Int.chainingLess(other: Int): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Int.chainingGreater(other: Int): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Int.cmp(other: Int): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Int.clamp(min: Int, max: Int): Int {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun Int.min(other: Int): Int = integerMin(this, other)

public fun Int.max(other: Int): Int = integerMax(this, other)

public fun UByte.partialCmp(other: UByte): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UByte.lt(other: UByte): Boolean = this < other

public fun UByte.le(other: UByte): Boolean = this <= other

public fun UByte.gt(other: UByte): Boolean = this > other

public fun UByte.ge(other: UByte): Boolean = this >= other

public fun UByte.chainingLess(other: UByte): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun UByte.chainingGreater(other: UByte): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun UByte.cmp(other: UByte): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UByte.clamp(min: UByte, max: UByte): UByte {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun UByte.min(other: UByte): UByte = integerMin(this, other)

public fun UByte.max(other: UByte): UByte = integerMax(this, other)

public fun UShort.partialCmp(other: UShort): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UShort.lt(other: UShort): Boolean = this < other

public fun UShort.le(other: UShort): Boolean = this <= other

public fun UShort.gt(other: UShort): Boolean = this > other

public fun UShort.ge(other: UShort): Boolean = this >= other

public fun UShort.chainingLess(other: UShort): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun UShort.chainingGreater(other: UShort): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun UShort.cmp(other: UShort): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UShort.clamp(min: UShort, max: UShort): UShort {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun UShort.min(other: UShort): UShort = integerMin(this, other)

public fun UShort.max(other: UShort): UShort = integerMax(this, other)

public fun UInt.partialCmp(other: UInt): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UInt.lt(other: UInt): Boolean = this < other

public fun UInt.le(other: UInt): Boolean = this <= other

public fun UInt.gt(other: UInt): Boolean = this > other

public fun UInt.ge(other: UInt): Boolean = this >= other

public fun UInt.chainingLess(other: UInt): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun UInt.chainingGreater(other: UInt): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun UInt.cmp(other: UInt): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun UInt.clamp(min: UInt, max: UInt): UInt {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun UInt.min(other: UInt): UInt = integerMin(this, other)

public fun UInt.max(other: UInt): UInt = integerMax(this, other)

public fun ULong.partialCmp(other: ULong): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun ULong.lt(other: ULong): Boolean = this < other

public fun ULong.le(other: ULong): Boolean = this <= other

public fun ULong.gt(other: ULong): Boolean = this > other

public fun ULong.ge(other: ULong): Boolean = this >= other

public fun ULong.chainingLess(other: ULong): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun ULong.chainingGreater(other: ULong): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun ULong.cmp(other: ULong): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun ULong.clamp(min: ULong, max: ULong): ULong {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun ULong.min(other: ULong): ULong = integerMin(this, other)

public fun ULong.max(other: ULong): ULong = integerMax(this, other)

public fun U128.partialCmp(other: U128): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun U128.lt(other: U128): Boolean = this < other

public fun U128.le(other: U128): Boolean = this <= other

public fun U128.gt(other: U128): Boolean = this > other

public fun U128.ge(other: U128): Boolean = this >= other

public fun U128.chainingLess(other: U128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun U128.chainingGreater(other: U128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun U128.cmp(other: U128): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun U128.clamp(min: U128, max: U128): U128 {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun U128.min(other: U128): U128 = integerMin(this, other)

public fun U128.max(other: U128): U128 = integerMax(this, other)

public fun Byte.partialCmp(other: Byte): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Byte.lt(other: Byte): Boolean = this < other

public fun Byte.le(other: Byte): Boolean = this <= other

public fun Byte.gt(other: Byte): Boolean = this > other

public fun Byte.ge(other: Byte): Boolean = this >= other

public fun Byte.chainingLess(other: Byte): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Byte.chainingGreater(other: Byte): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Byte.cmp(other: Byte): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Byte.clamp(min: Byte, max: Byte): Byte {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun Byte.min(other: Byte): Byte = integerMin(this, other)

public fun Byte.max(other: Byte): Byte = integerMax(this, other)

public fun Short.partialCmp(other: Short): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Short.lt(other: Short): Boolean = this < other

public fun Short.le(other: Short): Boolean = this <= other

public fun Short.gt(other: Short): Boolean = this > other

public fun Short.ge(other: Short): Boolean = this >= other

public fun Short.chainingLess(other: Short): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Short.chainingGreater(other: Short): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Short.cmp(other: Short): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Short.clamp(min: Short, max: Short): Short {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun Short.min(other: Short): Short = integerMin(this, other)

public fun Short.max(other: Short): Short = integerMax(this, other)

public fun Long.partialCmp(other: Long): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Long.lt(other: Long): Boolean = this < other

public fun Long.le(other: Long): Boolean = this <= other

public fun Long.gt(other: Long): Boolean = this > other

public fun Long.ge(other: Long): Boolean = this >= other

public fun Long.chainingLess(other: Long): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun Long.chainingGreater(other: Long): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun Long.cmp(other: Long): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun Long.clamp(min: Long, max: Long): Long {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun Long.min(other: Long): Long = integerMin(this, other)

public fun Long.max(other: Long): Long = integerMax(this, other)

public fun I128.partialCmp(other: I128): Ordering? = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun I128.lt(other: I128): Boolean = this < other

public fun I128.le(other: I128): Boolean = this <= other

public fun I128.gt(other: I128): Boolean = this > other

public fun I128.ge(other: I128): Boolean = this >= other

public fun I128.chainingLess(other: I128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs < rhs)
    }
}

public fun I128.chainingGreater(other: I128): ControlFlow<Boolean, Unit> {
    val (lhs, rhs) = Pair(this, other)
    return if (lhs == rhs) {
        Continue()
    } else {
        Break(lhs > rhs)
    }
}

public fun I128.cmp(other: I128): Ordering = run { val lhs = this; val rhs = other; when { lhs < rhs -> Ordering.Less; lhs > rhs -> Ordering.Greater; else -> Ordering.Equal } }

public fun I128.clamp(min: I128, max: I128): I128 {
    constAssert(min <= max, "min > max", "min > max. min = {min:?}, max = {max:?}", min, max)
    return if (this < min) {
        min
    } else if (this > max) {
        max
    } else {
        this
    }
}

public fun I128.min(other: I128): I128 = integerMin(this, other)

public fun I128.max(other: I128): I128 = integerMax(this, other)

public fun Nothing.eq(arg0: Nothing): Boolean = this

public fun Nothing.partialCmp(arg0: Nothing): Ordering? = this

public fun Nothing.cmp(arg0: Nothing): Ordering = this

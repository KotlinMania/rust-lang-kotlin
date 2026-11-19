// rust-kotlin: manual
// port-lint: source borrow.rs
package io.github.kotlinmania.rustlang.alloc.borrow

import io.github.kotlinmania.rustlang.alloc.borrow.Cow.*
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.result.Result

// A module for working with borrowed data.

/**
 * A generalization of `Clone` to borrowed data.
 *
 * Some types make it possible to go from borrowed to owned, usually by
 * implementing the `Clone` trait. But `Clone` works only for going from `T`
 * to `T`. The `ToOwned` trait generalizes `Clone` to construct owned data
 * from any borrow of a given type.
 */
public interface ToOwned<Owned> {
    /**
     * Creates owned data from borrowed data, usually by cloning.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val s: String = "a"
     * val ss: String = s.toOwned()
     * val v: List<Int> = listOf(1, 2)
     * val vv: List<Int> = v.toOwned()
     * ```
     */
    public fun toOwned(): Owned

    /**
     * Uses borrowed data to replace owned data, usually by cloning.
     *
     * This is borrow-generalized version of [Clone.cloneFrom].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var s: String = ""
     * "hello".cloneInto(s)
     * var v: List<Int> = mutableListOf()
     * listOf(1, 2).cloneInto(v)
     * ```
     */
    public fun cloneInto(target: Owned) {
        var target1 = target
        target1 = this.toOwned()
    }
}

public fun <T> T.toOwned(): T = this.clone()

public fun <T> T.cloneInto(target: T) {}

/**
 * A clone-on-write smart pointer.
 *
 * The type `Cow` is a smart pointer providing clone-on-write functionality: it
 * can enclose and provide immutable access to borrowed data, and clone the
 * data lazily when mutation or ownership is required. The type is designed to
 * work with general borrowed data via the `Borrow` trait.
 *
 * `Cow` implements `Deref`, which means that you can call
 * non-mutating methods directly on the data it encloses. If mutation
 * is desired, `toMut` will obtain a mutable reference to an owned
 * value, cloning if necessary.
 *
 * If you need reference-counting pointers, note that
 * [`Rc.makeMut`][rc.Rc.makeMut] and
 * [`Arc.makeMut`][sync.Arc.makeMut] can provide clone-on-write
 * functionality as well.
 *
 * # Examples
 *
 * ```kotlin
 * import std.borrow.Cow
 *
 * fun absAll(input: List<Int>) {
 *     for (i in 0 until input.size) {
 *         val v = input[i]
 *         if (v < 0) {
 *             // Clones into a vector if not already owned.
 *             input.toMut()[i] = -v
 *         }
 *     }
 * }
 * // No clone occurs because `input` doesn't need to be mutated.
 * val slice = listOf(0, 1, 2)
 * var input = Cow.from(slice)
 * absAll(input)
 * // Clone occurs because `input` needs to be mutated.
 * val slice1 = listOf(-1, 0, 1)
 * var input1 = Cow.from(slice1)
 * absAll(input1)
 * // No clone occurs because `input` is already owned.
 * var input2 = Cow.from(mutableListOf(-1, 0, 1))
 * absAll(input2)
 * ```
 *
 * Another example showing how to keep `Cow` in a struct:
 *
 * ```kotlin
 * import std.borrow.Cow
 *
 * open class Items<X>(public var values: List<X>) {
 *     public open fun new(v: List<X>): Items<X> {
 *         return Items(values = v)
 *     }
 * }
 * // Creates a container from borrowed values of a slice
 * val readonly = listOf(1, 2)
 * val borrowed = Items.new((readonly).into())
 * when {
 *     borrowed.values is Cow.Borrowed -> {
 *         val (b) = borrowed.values as Cow.Borrowed
 *         println("borrowed $b")
 *     }
 *     else -> error("expect borrowed value")
 * }
 * var cloneOnWrite = borrowed
 * // Mutates the data from slice into owned vec and pushes a new value on top
 * cloneOnWrite.values.toMut().push(3)
 * println("cloneOnWrite = ${cloneOnWrite.values}")
 * // The data was mutated. Let's check it out.
 * when {
 *     cloneOnWrite.values is Cow.Owned -> println("cloneOnWrite contains owned data")
 *     else -> error("expect owned data")
 * }
 * ```
 */
public sealed class Cow<B, Owned> {
    /**
     * Borrowed data.
     */
    public data class Borrowed<B, Owned>(
        public var value: B,
    ) : Cow<B, Owned>()

    /**
     * Owned data.
     */
    public data class Owned<B, Owned>(
        public var value: Owned,
    ) : Cow<B, Owned>()

    public open fun clone(toOwnedFn: (B) -> Owned): Cow<B, Owned> =
        when (this) {
            is Borrowed -> Borrowed(this.value)
            is Cow.Owned -> Cow.Owned(this.value.clone())
        }

    public open fun cloneFrom(source: Cow<B, Owned>) {
        when {
            this is Borrowed && source is Borrowed -> this.value = source.value
            this is Cow.Owned && source is Cow.Owned -> this.value = source.value.clone()
        }
    }

    public open fun toMut(toOwnedFn: (B) -> Owned): Owned =
        when (this) {
            is Borrowed -> toOwnedFn(this.value)
            is Cow.Owned -> this.value
        }

    public open fun intoOwned(toOwnedFn: (B) -> Owned): Owned =
        when (this) {
            is Borrowed -> toOwnedFn(this.value)
            is Cow.Owned -> this.value
        }

    public open fun deref(borrowFn: (Owned) -> B): B =
        when (this) {
            is Borrowed -> this.value
            is Cow.Owned -> borrowFn(this.value)
        }

    public open fun cmp(other: Cow<B, Owned>): Ordering {
        val a: Any? =
            when (this) {
                is Borrowed -> this.value
                is Cow.Owned -> this.value
            }
        val b: Any? =
            when (other) {
                is Borrowed -> other.value
                is Cow.Owned -> other.value
            }
        val sign = kmCompareValues(a, b)
        return when {
            sign < 0 -> Ordering.Less
            sign > 0 -> Ordering.Greater
            else -> Ordering.Equal
        }
    }

    public open fun partialCmp(other: Cow<B, Owned>): Ordering? {
        val a: Any? =
            when (this) {
                is Borrowed -> this.value
                is Cow.Owned -> this.value
            }
        val b: Any? =
            when (other) {
                is Borrowed -> other.value
                is Cow.Owned -> other.value
            }
        val sign = kmPartialCompareValues(a, b) ?: return null
        return when {
            sign < 0 -> Ordering.Less
            sign > 0 -> Ordering.Greater
            else -> Ordering.Equal
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        when (this) {
            is Borrowed -> f.append(this.value.toString())
            is Cow.Owned -> f.append(this.value.toString())
        }
        return Result.Ok(Unit)
    }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.fmt(f)

    public override fun hashCode(): Int =
        when (this) {
            is Borrowed -> this.value?.hashCode() ?: 0
            is Cow.Owned -> this.value?.hashCode() ?: 0
        }

    public companion object {
        public fun <B, Owned> from(v: B): Cow<B, Owned> = Borrowed(v)

        public fun <Owned, B> isBorrowed(c: Cow<B, Owned>): Boolean = c is Borrowed

        public fun <Owned, B> isOwned(c: Cow<B, Owned>): Boolean = c is Cow.Owned

        public fun <Owned, B> default(defaultOwnedFn: () -> Owned): Cow<B, Owned> = Cow.Owned(defaultOwnedFn())
    }
}

public fun <T> Cow<T, T>.clone(): Cow<T, T> =
    when (this) {
        is Borrowed -> Borrowed(this.value.clone())
        is Cow.Owned -> Cow.Owned(this.value.clone())
    }

public fun <T> Cow<T, T>.borrow(): T = this.deref()

public fun <T> Cow<T, T>.deref(): T =
    when (this) {
        is Borrowed -> this.value
        is Cow.Owned -> this.value
    }

public fun <T> Cow<T, T>.toMut(): T = this.deref()

public fun <T> Cow<T, T>.intoOwned(): T =
    when (this) {
        is Borrowed -> this.value.clone()
        is Cow.Owned -> this.value
    }

public fun <B, Owned> Cow<B, Owned>.eq(other: Cow<B, Owned>): Boolean = this == other

public fun <T> Cow<T, T>.asRef(): T = this.deref()

public fun Cow<String, String>.add(rhs: String): Cow<String, String> =
    if (this.deref().isEmpty()) {
        Borrowed(rhs)
    } else {
        Cow.Owned(this.deref() + rhs)
    }

public fun Cow<String, String>.add(rhs: Cow<String, String>): Cow<String, String> =
    if (this.deref().isEmpty()) {
        rhs
    } else {
        Cow.Owned(this.deref() + rhs.deref())
    }

public fun Cow<String, String>.addAssign(rhs: String) {
    if (this is Cow.Owned) {
        this.value += rhs
    } else if (this is Borrowed) {
        this.value += rhs
    }
}

public fun Cow<String, String>.addAssign(rhs: Cow<String, String>) {
    this.addAssign(rhs.deref())
}

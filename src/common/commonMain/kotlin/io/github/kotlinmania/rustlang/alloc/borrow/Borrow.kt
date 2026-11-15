// port-lint: source borrow.rs
package io.github.kotlinmania.rustlang.alloc.borrow

import io.github.kotlinmania.rustlang.alloc.borrow.Cow.*
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.cmp.Ordering

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

public fun <T> T.cloneInto(target: T) {
    target.cloneFrom(this)
}

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
public sealed class Cow<B> : Borrow<B> {
    /**
     * Borrowed data.
     */
    public data class Borrowed<B>(
        public var value: B,
    ) : Cow<B>()

    /**
     * Owned data.
     */
    public data class Owned<B>(
        public var value: Owned,
    ) : Cow<B>()

    // Note(inference): const bounds removed due to inference regressions found by crater;
    // see https://github.com/rust-lang/rust/issues/147964
    // @rustcConstUnstable(feature = "constConvert", issue = "143773")
    public open fun borrow(): B = this

    public open fun clone(): Cow<B> =
        when (this) {
            is Borrowed -> {
                val (b) = this
                Borrowed(b)
            }
            is Owned -> {
                val (o) = this
                val b1: B = o.borrow()
                Owned(b1.toOwned())
            }
        }

    public open fun cloneFrom(source: Cow<B>) {
        run {
            val matchVal = Pair(this, source)
            when {
                matchVal.first is Owned && matchVal.second is Owned -> {
                    val (dest) = matchVal.first as Owned<B>
                    val (o) = matchVal.second as Owned<B>
                    o.borrow().cloneInto(dest)
                }
                else -> {
                    val t = matchVal.first
                    val s = matchVal.second
                    t = s.clone()
                }
            }
        }
    }

    /**
     * Acquires a mutable reference to the owned form of the data.
     *
     * Clones the data if it is not already owned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.borrow.Cow
     *
     * var cow = "foo"
     * cow.toMut().makeAsciiUppercase()
     * check(cow == (("FOO" as Any?) as String))
     * ```
     */
    public open fun toMut(): Owned =
        when (this) {
            is Borrowed -> {
                val (borrowed) = this
                this = Owned(borrowed.toOwned())
                when (this) {
                    is Borrowed -> error("unreachable")
                    is Owned -> {
                        val (owned) = this
                        owned
                    }
                }
            }
            is Owned -> {
                val (owned1) = this
                owned1
            }
        }

    /**
     * Extracts the owned data.
     *
     * Clones the data if it is not already owned.
     *
     * # Examples
     *
     * Calling `intoOwned` on a `Cow.Borrowed` returns a clone of the borrowed data:
     *
     * ```kotlin
     * import std.borrow.Cow
     *
     * val s = "Hello world!"
     * val cow = s
     * check(cow.intoOwned() == (s).toString())
     * ```
     *
     * Calling `intoOwned` on a `Cow.Owned` returns the owned data. The data is moved out of the
     * `Cow` without being cloned.
     *
     * ```kotlin
     * import std.borrow.Cow
     *
     * val s = "Hello world!"
     * val cow: String = (s).toString()
     * check(cow.intoOwned() == (s).toString())
     * ```
     */
    public open fun intoOwned(): Owned =
        when (this) {
            is Borrowed -> {
                val (borrowed) = this
                borrowed.toOwned()
            }
            is Owned -> {
                val (owned) = this
                owned
            }
        }

    // Note(inference): const bounds removed due to inference regressions found by crater;
    // see https://github.com/rust-lang/rust/issues/147964
    // @rustcConstUnstable(feature = "constConvert", issue = "143773")
    public open fun deref(): B =
        when (this) {
            is Borrowed -> {
                val (borrowed) = this
                borrowed
            }
            is Owned -> {
                val (owned) = this
                owned.borrow()
            }
        }

    public open fun cmp(other: Cow<B>): Ordering = Ord.cmp(this, other)

    public open fun eq(other: Cow<C>): Boolean = PartialEq.eq(this, other)

    public open fun partialCmp(other: Cow<B>): Ordering? = PartialOrd.partialCmp(this, other)

    public open fun fmt(f: StringBuilder) {
        when (this) {
            is Borrowed -> {
                val (b) = this
                f.append((b).toString())
            }
            is Owned -> {
                val (o) = this
                f.append((o).toString())
            }
        }
    }

    public open fun fmtDisplay(f: StringBuilder) {
        when (this) {
            is Borrowed -> {
                val (b) = this
                f.append((b).toString())
            }
            is Owned -> {
                val (o) = this
                f.append((o).toString())
            }
        }
    }

    public override fun hashCode(): Int {
        var state = 0
        Hash.hash(this, state)
        return state
    }

    public companion object {
        /**
         * Returns true if the data is borrowed, i.e. if `toMut` would require additional work.
         *
         * Note: this is an associated function, which means that you have to call
         * it as `Cow.isBorrowed(c)` instead of `c.isBorrowed()`. This is so
         * that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.borrow.Cow
         *
         * @feature(cowIsBorrowed)
         * public fun doctestEntry() {
         *     val cow = "moo"
         *     check(Cow.isBorrowed(cow))
         *     val bull: String = "...moo?".toString()
         *     check(!Cow.isBorrowed(bull))
         * }
         * ```
         */
        public fun <Owned, B> isBorrowed(c: Cow<B>): Boolean where B : ToOwned<Any?> =
            when (c) {
                is Borrowed -> true
                is Owned -> false
            }

        /**
         * Returns true if the data is owned, i.e. if `toMut` would be a no-op.
         *
         * Note: this is an associated function, which means that you have to call
         * it as `Cow.isOwned(c)` instead of `c.isOwned()`. This is so that
         * there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.borrow.Cow
         *
         * @feature(cowIsBorrowed)
         * public fun doctestEntry() {
         *     val cow: String = "moo".toString()
         *     check(Cow.isOwned(cow))
         *     val bull = "...moo?"
         *     check(!Cow.isOwned(bull))
         * }
         * ```
         */
        public fun <Owned, B> isOwned(c: Cow<B>): Boolean where B : ToOwned<Any?> = !Cow.isBorrowed(c)

        /**
         * Creates an owned Cow<a, B> with the default value for the contained owned value.
         */
        public fun <Owned, B> default(defaultBFn: () -> B): Cow<B> where B : ToOwned<Any?> = Owned(defaultBFn())
    }
}

// `Cow<T>` can only implement `DerefPure` if `<T.Owned as Borrow<T>>` (and `BorrowMut<T>`) is trusted.
// For now, we restrict `DerefPure for Cow<T>` to `T: Sized` (`T as Borrow<T>` is trusted),
// `String` (`String as Borrow<String>` is trusted) and `[T]` (`List<T> as Borrow<[T]>` is trusted).
// In the future, a `BorrowPure<T>` trait analogous to `DerefPure` might generalize this.

// Note(inference): const bounds removed due to inference regressions found by crater;
// see https://github.com/rust-lang/rust/issues/147964
// @rustcConstUnstable(feature = "constConvert", issue = "143773")
public fun <Owned, T : ToOwned<Any?>> Cow<T>.asRef(): T = this

public fun Cow<String>.add(rhs: String): Cow<String> {
    this += rhs
    return this
}

public fun Cow<String>.add(rhs: Cow<String>): Cow<String> {
    this += rhs
    return this
}

public fun Cow<String>.addAssign(rhs: String) {
    if (this.isEmpty()) {
        this = Cow.Borrowed(rhs)
    } else if (!rhs.isEmpty()) {
        when (val matchVal = this) {
            is Cow.Borrowed -> {
                val (lhs) = matchVal
                var s = ""
                s += lhs
                this = Cow.Owned(s)
            }
            else -> {}
        }
        this.toMut().pushStr(rhs)
    }
}

public fun Cow<String>.addAssign(rhs: Cow<String>) {
    if (this.isEmpty()) {
        this = rhs
    } else if (!rhs.isEmpty()) {
        when (val matchVal = this) {
            is Cow.Borrowed -> {
                val (lhs) = matchVal
                var s = ""
                s += lhs
                this = Cow.Owned(s)
            }
            else -> {}
        }
        this.toMut().pushStr(rhs)
    }
}

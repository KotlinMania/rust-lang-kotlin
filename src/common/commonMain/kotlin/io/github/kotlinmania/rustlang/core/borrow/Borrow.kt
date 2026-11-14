// port-lint: source borrow.rs
package io.github.kotlinmania.rustlang.core.borrow

// Utilities for working with borrowed data.

/**
 * A trait for borrowing data.
 *
 * In Rust, it is common to provide different representations of a type for
 * different use cases. For instance, storage location and management for a
 * value can be specifically chosen as appropriate for a particular use via
 * pointer types such as [Box.T] or [Rc.T]. Beyond these generic
 * wrappers that can be used with any type, some types provide optional
 * facets providing potentially costly functionality. An example for such a
 * type is [String] which adds the ability to extend a string to the basic
 * [str]. This requires keeping additional information unnecessary for a
 * simple, immutable string.
 *
 * These types provide access to the underlying data through references
 * to the type of that data. They are said to be ‘borrowed as’ that type.
 * For instance, a [Box.T] can be borrowed as `T` while a [String]
 * can be borrowed as `String`.
 *
 * Types express that they can be borrowed as some type `T` by implementing
 * `Borrow<T>`, providing a reference to a `T` in the trait’s
 * [borrow] method. A type is free to borrow as several different types.
 * If it wishes to mutably borrow as the type, allowing the underlying data
 * to be modified, it can additionally implement [BorrowMut.T].
 *
 * Further, when providing implementations for additional traits, it needs
 * to be considered whether they should behave identically to those of the
 * underlying type as a consequence of acting as a representation of that
 * underlying type. Generic code typically uses `Borrow<T>` when it relies
 * on the identical behavior of these additional trait implementations.
 * These traits will likely appear as additional trait bounds.
 *
 * In particular `Eq`, `Ord` and `Hash` must be equivalent for
 * borrowed and owned values: `x.borrow() == y.borrow()` should give the
 * same result as `x == y`.
 *
 * If generic code merely needs to work for all types that can
 * provide a reference to related type `T`, it is often better to use
 * [AsRef.T] as more types can safely implement it.
 *
 * [Box.T]: ../../std/boxed/struct.Holder.html
 * [Mutex.T]: ../../std/sync/struct.Mutex.html
 * [Rc.T]: ../../std/rc/struct.Rc.html
 * [String]: ../../std/string/struct.String.html
 * [borrow]: Borrow.borrow
 *
 * # Examples
 *
 * As a data collection, [HashMap.K.V] owns both keys and values. If
 * the key’s actual data is wrapped in a managing type of some kind, it
 * should, however, still be possible to search for a value using a
 * reference to the key’s data. For instance, if the key is a string, then
 * it is likely stored with the hash map as a [String], while it should
 * be possible to search using a [`str`][str]. Thus, `insert` needs to
 * operate on a `String` while `get` needs to be able to use a `String`.
 *
 * Slightly simplified, the relevant parts of `HashMap<K, V>` look like
 * this:
 *
 * ```kotlin
 * import std.borrow.Borrow
 *
 * open class HashMap<K, V> {
 *     public open fun insert(key: K, value: V): V? {
 *         // ...
 *     }
 *
 *     public open fun <Q> get(k: Q): V? {
 *         // ...
 *     }
 * }
 * ```
 *
 * The entire hash map is generic over a key type `K`. Because these keys
 * are stored with the hash map, this type has to own the key’s data.
 * When inserting a key-value pair, the map is given such a `K` and needs
 * to find the correct hash bucket and check if the key is already present
 * based on that `K`. It therefore requires `K: Hash + Eq`.
 *
 * When searching for a value in the map, however, having to provide a
 * reference to a `K` as the key to search for would require to always
 * create such an owned value. For string keys, this would mean a `String`
 * value needs to be created just for the search for cases where only a
 * `String` is available.
 *
 * Instead, the `get` method is generic over the type of the underlying key
 * data, called `Q` in the method signature above. It states that `K`
 * borrows as a `Q` by requiring that `K: Borrow<Q>`. By additionally
 * requiring `Q: Hash + Eq`, it signals the requirement that `K` and `Q`
 * have implementations of the `Hash` and `Eq` traits that produce identical
 * results.
 *
 * The implementation of `get` relies in particular on identical
 * implementations of `Hash` by determining the key’s hash bucket by calling
 * `Hash.hash` on the `Q` value even though it inserted the key based on
 * the hash value calculated from the `K` value.
 *
 * As a consequence, the hash map breaks if a `K` wrapping a `Q` value
 * produces a different hash than `Q`. For instance, imagine you have a
 * type that wraps a string but compares ASCII letters ignoring their case:
 *
 * ```kotlin
 * open class CaseInsensitiveString(public var value: String) {
 *     public var field0: String
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun eq(other: CaseInsensitiveString): Boolean {
 *         return this.value.eqIgnoreAsciiCase(other.value)
 *     }
 * }
 * ```
 *
 * Because two equal values need to produce the same hash value, the
 * implementation of `Hash` needs to ignore ASCII case, too:
 *
 * ```kotlin
 * fun <H> CaseInsensitiveString.hash(state: H) {
 *     for (c in this.value.encodeToByteArray().map { it.toUByte() }) {
 *         c.toAsciiLowercase().hash(state)
 *     }
 * }
 * ```
 *
 * Can `CaseInsensitiveString` implement `Borrow<String>`? It certainly can
 * provide a reference to a string slice via its contained owned string.
 * But because its `Hash` implementation differs, it behaves differently
 * from `String` and therefore must not, in fact, implement `Borrow<String>`.
 * If it wants to allow others access to the underlying `String`, it can do
 * that via `AsRef<String>` which doesn’t carry any extra requirements.
 *
 * [HashMap.K.V]: ../../std/collections/struct.HashMap.html
 * [String]: ../../std/string/struct.String.html
 */
public interface Borrow<Borrowed> {
    /**
     * Immutably borrows from an owned value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.borrow.Borrow
     *
     * fun check(s: String) {
     *     check("Hello" == s)
     * }
     * val s = "Hello".toString()
     * check(s)
     * val s1 = "Hello"
     * check(s1)
     * ```
     */
    public fun borrow(): Borrowed
}

/**
 * A trait for mutably borrowing data.
 *
 * As a companion to [Borrow.T] this trait allows a type to borrow as
 * an underlying type by providing a mutable reference. See [Borrow.T]
 * for more information on borrowing as another type.
 */
public interface BorrowMut<Borrowed> : Borrow<Borrowed> {
    /**
     * Mutably borrows from an owned value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.borrow.BorrowMut
     *
     * fun <T : BorrowMut<List<Int>>> check(v: T) {
     *     check(listOf(1, 2, 3) == v.borrowMut())
     * }
     * val v = mutableListOf(1, 2, 3)
     * check(v)
     * ```
     */
    public fun borrowMut(): Borrowed
}

public fun <T> T.borrow(): T = this

public fun <T> T.borrowMut(): T = this

public fun <T> T.borrowT(): T = this

public fun <T> T.borrowMutT(): T = this

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.hash

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.component10
import io.github.kotlinmania.rustlang.core.mem.component11
import io.github.kotlinmania.rustlang.core.mem.component12
import io.github.kotlinmania.rustlang.core.mem.component6
import io.github.kotlinmania.rustlang.core.mem.component7
import io.github.kotlinmania.rustlang.core.mem.component8
import io.github.kotlinmania.rustlang.core.mem.component9
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.toRawParts
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * A hashable type.
 *
 * Types implementing `Hash` are able to be [hash]ed with an instance of
 * [Hasher].
 *
 * ## Implementing `Hash`
 *
 * You can derive `Hash` with `@derive(Hash)` if all fields implement `Hash`.
 * The resulting hash will be the combination of the values from calling
 * [hash] on each field.
 *
 * ```kotlin
 * open class Rustacean(public var name: String, public var country: String)
 * ```
 *
 * If you need more control over how a value is hashed, you can of course
 * implement the `Hash` trait yourself:
 *
 * ```kotlin
 * open class Person(
 *     public var id: UInt,
 *     public var name: String,
 *     public var phone: ULong,
 * ) : Hash<Person> {
 *     public override fun hashCode(): Int {
 *         var state = 0
 *         state = 31 * state + (this.id).hashCode()
 *         state = 31 * state + (this.phone).hashCode()
 *         return state
 *     }
 * }
 * ```
 *
 * ## `Hash` and `Eq`
 *
 * When implementing both `Hash` and [Eq], it is important that the following
 * property holds:
 *
 * ```text
 * k1 == k2 -> hash(k1) == hash(k2)
 * ```
 *
 * In other words, if two keys are equal, their hashes must also be equal.
 * [HashMap] and [HashSet] both rely on this behavior.
 *
 * Thankfully, you won't need to worry about upholding this property when
 * deriving both [Eq] and `Hash` with `@derive(PartialEq, Eq, Hash)`.
 *
 * Violating this property is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on the correctness of these
 * methods.
 *
 * ## Prefix collisions
 *
 * Implementations of `hash` should ensure that the data they
 * pass to the `Hasher` are prefix-free. That is,
 * values which are not equal should cause two different sequences of values to be written,
 * and neither of the two sequences should be a prefix of the other.
 *
 * For example, the standard implementation of [`Hash` for `String`][impl] passes an extra
 * `0xFF` byte to the `Hasher` so that the values `("ab", "c")` and `("a",
 * "bc")` hash differently.
 *
 * ## Portability
 *
 * Due to differences in endianness and type sizes, data fed by `Hash` to a `Hasher`
 * should not be considered portable across platforms. Additionally the data passed by most
 * standard library types should not be considered stable between compiler versions.
 *
 * This means tests shouldn't probe hard-coded hash values or data fed to a `Hasher` and
 * instead should check consistency with `Eq`.
 *
 * Serialization formats intended to be portable between platforms or compiler versions should
 * either avoid encoding hashes or only rely on `Hash` and `Hasher` implementations that
 * provide additional guarantees.
 *
 * [HashMap]: ../../std/collections/struct.HashMap.html
 * [HashSet]: ../../std/collections/struct.HashSet.html
 * [hash]: Hash.hash
 * [impl]: ../../std/primitive.str.html#impl-Hash-for-str
 */
public interface Hash<T> {
    /**
     * Feeds this value into the given [Hasher].
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.DefaultHasher
     *
     * var hasher = DefaultHasher.new()
     * 7920.hash(hasher)
     * println("Hash is ${hasher.finish()}!")
     * ```
     */
    public fun <H> hash(state: H)

    /**
     * Feeds a slice of this type into the given [Hasher].
     *
     * This method is meant as a convenience, but its implementation is
     * also explicitly left unspecified. It isn't guaranteed to be
     * equivalent to repeated calls of [hash] and implementations of
     * [Hash] should keep that in mind and call [hash] themselves
     * if the slice isn't treated as a whole unit in the [PartialEq]
     * implementation.
     *
     * For example, a [VecDeque] implementation might naïvely call
     * [asSlices] and then [hashSlice] on each slice, but this
     * is wrong since the two slices can change with a call to
     * [makeContiguous] without affecting the [PartialEq]
     * result. Since these slices aren't treated as singular
     * units, and instead part of a larger deque, this method cannot
     * be used.
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.DefaultHasher
     *
     * var hasher = DefaultHasher.new()
     * val numbers = listOf(6, 28, 496, 8128)
     * Hash.hashSlice(numbers, hasher)
     * println("Hash is ${hasher.finish()}!")
     * ```
     *
     * [VecDeque]: ../../std/collections/struct.VecDeque.html
     * [asSlices]: ../../std/collections/struct.VecDeque.html#method.asSlices
     * [makeContiguous]: ../../std/collections/struct.VecDeque.html#method.makeContiguous
     * [hash]: Hash.hash
     * [hashSlice]: Hash.hashSlice
     */
    public fun <H> hashSlice(data: List<T>, state: H) {
        for (piece in data) {
            piece.hash(state)
        }
    }
}

/**
 * A trait for hashing an arbitrary stream of bytes.
 *
 * Instances of `Hasher` usually represent state that is changed while hashing
 * data.
 *
 * `Hasher` provides a fairly basic interface for retrieving the generated hash
 * (with [finish]), and writing integers as well as slices of bytes into an
 * instance (with [write] and [writeU8] etc.). Most of the time, `Hasher`
 * instances are used in conjunction with the [Hash] trait.
 *
 * This trait provides no guarantees about how the various `write_ *` methods are
 * defined and implementations of [Hash] should not assume that they work one
 * way or another. You cannot assume, for example, that a [writeU32] call is
 * equivalent to four calls of [writeU8].  Nor can you assume that adjacent
 * `write` calls are merged, so it's possible, for example, that
 * ```kotlin
 * hasher.write(listOf(1, 2))
 * hasher.write(listOf(3, 4, 5, 6))
 * ```
 * and
 * ```kotlin
 * hasher.write(listOf(1, 2, 3, 4))
 * hasher.write(listOf(5, 6))
 * ```
 * end up producing different hashes.
 *
 * Thus to produce the same hash value, [Hash] implementations must ensure
 * for equivalent items that exactly the same sequence of calls is made -- the
 * same methods with the same parameters in the same order.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.DefaultHasher
 *
 * var hasher = DefaultHasher.new()
 * hasher.writeU32(1989)
 * hasher.writeU8(11)
 * hasher.writeU8(9)
 * hasher.write("Huh?".encodeToByteArray())
 * println("Hash is ${hasher.finish()}!")
 * ```
 *
 * [finish]: Hasher.finish
 * [write]: Hasher.write
 * [writeU8]: Hasher.writeU8
 * [writeU32]: Hasher.writeU32
 */
public interface Hasher {
    /**
     * Returns the hash value for the values written so far.
     *
     * Despite its name, the method does not reset the hasher’s internal
     * state. Additional [write]s will continue from the current value.
     * If you need to start a fresh hash value, you will have to create
     * a new hasher.
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.DefaultHasher
     *
     * var hasher = DefaultHasher.new()
     * hasher.write("Cool!".encodeToByteArray())
     * println("Hash is ${hasher.finish()}!")
     * ```
     *
     * [write]: Hasher.write
     */
    public fun finish(): ULong

    /**
     * Writes some data into this `Hasher`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.DefaultHasher
     *
     * var hasher = DefaultHasher.new()
     * val data = listOf(0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef)
     * hasher.write(data)
     * println("Hash is ${hasher.finish()}!")
     * ```
     *
     * # Note to Implementers
     *
     * You generally should not do length-prefixing as part of implementing
     * this method.  It's up to the [Hash] implementation to call
     * [Hasher.writeLengthPrefix] before sequences that need it.
     */
    public fun write(bytes: List<UByte>)

    /**
     * Writes a single `UByte` into this hasher.
     */
    public fun writeU8(i: UByte) {
        this.write(ubyteArrayOf(i))
    }

    /**
     * Writes a single `UShort` into this hasher.
     */
    public fun writeU16(i: UShort) {
        this.write(run { val rawBits = i.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) })
    }

    /**
     * Writes a single `UInt` into this hasher.
     */
    public fun writeU32(i: UInt) {
        this.write(run { val rawBits = i; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })
    }

    /**
     * Writes a single `ULong` into this hasher.
     */
    public fun writeU64(i: ULong) {
        this.write(run { val rawBits = i; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) })
    }

    /**
     * Writes a single `ULong` into this hasher.
     */
    public fun writeU128(i: U128) {
        this.write(i.toLeBytes())
    }

    /**
     * Writes a single `Int` into this hasher.
     */
    public fun writeUsize(i: Int) {
        this.write(run { val rawBits = i.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })
    }

    /**
     * Writes a single `Byte` into this hasher.
     */
    public fun writeI8(i: Byte) {
        this.writeU8(i.toUByte())
    }

    /**
     * Writes a single `Short` into this hasher.
     */
    public fun writeI16(i: Short) {
        this.writeU16(i.toUShort())
    }

    /**
     * Writes a single `Int` into this hasher.
     */
    public fun writeI32(i: Int) {
        this.writeU32(i.toUInt())
    }

    /**
     * Writes a single `Long` into this hasher.
     */
    public fun writeI64(i: Long) {
        this.writeU64(i.toULong())
    }

    /**
     * Writes a single `Long` into this hasher.
     */
    public fun writeI128(i: I128) {
        this.writeU128(i.toU128())
    }

    /**
     * Writes a single `Long` into this hasher.
     */
    public fun writeIsize(i: Int) {
        this.writeUsize(i)
    }

    /**
     * Writes a length prefix into this hasher, as part of being prefix-free.
     *
     * If you're implementing [Hash] for a custom collection, call this before
     * writing its contents to this `Hasher`.  That way
     * `(listOf(1, 2, 3), listOf(4, 5))` and
     * `(listOf(1, 2), listOf(3, 4, 5))` will provide different
     * sequences of values to the `Hasher`.
     *
     * The `implementation<T> Hash for [T]` includes a call to this method, so if you're
     * hashing a slice (or array or vector) via its `Hash.hash` method,
     * you should **not** call this yourself.
     *
     * This method is only for providing domain separation.  If you want to
     * hash a `Int` that represents part of the *data*, then it's important
     * that you pass it to [Hasher.writeUsize] instead of to this method.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hasherPrefixfreeExtras)
     * public fun doctestEntry() {
     *     fun <T : Hash<T>, H> MyCollection<T>.hash(state: H) {
     *         state.writeLengthPrefix(this.len())
     *         for (elt in this) {
     *             elt.hash(state)
     *         }
     *     }
     * }
     * ```
     *
     * # Note to Implementers
     *
     * If you've decided that your `Hasher` is willing to be susceptible to
     * Hash-DoS attacks, then you might consider skipping hashing some or all
     * of the `len` provided in the name of increased performance.
     */
    public fun writeLengthPrefix(len: Int) {
        this.writeUsize(len)
    }

    /**
     * Writes a single `String` into this hasher.
     *
     * If you're implementing [Hash], you generally do not need to call this,
     * as the `implementation Hash for String` does, so you should prefer that instead.
     *
     * This includes the domain separator for prefix-freedom, so you should
     * **not** call `Self.writeLengthPrefix` before calling this.
     *
     * # Note to Implementers
     *
     * There are at least two reasonable default ways to implement this.
     * Which one will be the default is not yet decided, so for now
     * you probably want to override it specifically.
     *
     * ## The general answer
     *
     * It's always correct to implement this with a length prefix:
     *
     * ```kotlin
     * fun writeStr(s: String) {
     *     this.writeLengthPrefix(s.length)
     *     this.write(s.encodeToByteArray().map { it.toUByte() })
     * }
     * ```
     *
     * And, if your `Hasher` works in `Int` chunks, this is likely a very
     * efficient way to do it, as anything more complicated may well end up
     * slower than just running the round with the length.
     *
     * ## If your `Hasher` works byte-wise
     *
     * One nice thing about `String` being UTF-8 is that the `b'\xFF'` byte
     * never happens.  That means that you can append that to the byte stream
     * being hashed and maintain prefix-freedom:
     *
     * ```kotlin
     * fun writeStr(s: String) {
     *     this.write(s.encodeToByteArray().map { it.toUByte() })
     *     this.writeU8(0xff)
     * }
     * ```
     *
     * This does require that your implementation not add extra padding, and
     * thus generally requires that you maintain a buffer, running a round
     * only once that buffer is full (or `finish` is called).
     *
     * That's because if `write` pads data out to a fixed chunk size, it's
     * likely that it does it in such a way that `"a"` and `"a\x00"` would
     * end up hashing the same sequence of things, introducing conflicts.
     */
    public fun writeStr(s: String) {
        this.write(s.encodeToByteArray().map { it.toUByte() })
        this.writeU8(0xff)
    }
}

/**
 * A trait for creating instances of [Hasher].
 *
 * A `BuildHasher` is typically used (e.g., by [HashMap]) to create
 * [Hasher]s for each key such that they are hashed independently of one
 * another, since [Hasher]s contain state.
 *
 * For each instance of `BuildHasher`, the [Hasher]s created by
 * [buildHasher] should be identical. That is, if the same stream of bytes
 * is fed into each hasher, the same output will also be generated.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var hasher1 = s.buildHasher()
 * var hasher2 = s.buildHasher()
 * hasher1.writeU32(8128)
 * hasher2.writeU32(8128)
 * check(hasher1.finish() == hasher2.finish())
 * ```
 *
 * [buildHasher]: BuildHasher.buildHasher
 * [HashMap]: ../../std/collections/struct.HashMap.html
 */
public interface BuildHasher<Hasher> {
    /**
     * Creates a new hasher.
     *
     * Each call to `buildHasher` on the same instance should produce identical
     * [Hasher]s.
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.RandomState
     *
     * val s = RandomState.new()
     * val newS = s.buildHasher()
     * ```
     */
    public fun buildHasher(): Hasher

    /**
     * Calculates the hash of a single value.
     *
     * This is intended as a convenience for code which *consumes* hashes, such
     * as the implementation of a hash table or in unit tests that check
     * whether a custom [Hash] implementation behaves as expected.
     *
     * This must not be used in any code which *creates* hashes, such as in an
     * implementation of [Hash].  The way to create a combined hash of
     * multiple values is to call [Hash.hash] multiple times using the same
     * [Hasher], not to call this method repeatedly and combine the results.
     *
     * # Example
     *
     * ```kotlin
     * import std.cmp.max
     *
     * open class OrderAmbivalentPair<T>(public var field0: T, public var field1: T)
     * fun <T : Hash<T>, H> OrderAmbivalentPair<T>.hash(hasher: H) {
     *     minOf(this.field0, this.field1).hash(hasher)
     *     max(this.field0, this.field1).hash(hasher)
     * }
     * // Then later, in a `@Test` for the type...
     * val bh = Hash.RandomState.new()
     * check(bh.hashOne(OrderAmbivalentPair(1, 2)) == bh.hashOne(OrderAmbivalentPair(2, 1)))
     * check(bh.hashOne(OrderAmbivalentPair(10, 2)) == bh.hashOne(OrderAmbivalentPair(2, 10)))
     * ```
     */
    public fun <T : Hash<T>> hashOne(x: T): ULong {
        val hasher = this.buildHasher()
        x.hash(hasher)
        return hasher.finish()
    }
}

public fun <H> UByte.hash(state: H) {
    state.writeU8(this)
}

public fun <H> hashHashSliceUByte(data: List<UByte>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<UByte>>())
    val ptr = data.asPtr()
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> UShort.hash(state: H) {
    state.writeU16(this)
}

public fun <H> hashHashSliceUShort(data: List<UShort>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<UShort>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> UInt.hash(state: H) {
    state.writeU32(this)
}

public fun <H> hashHashSliceUInt(data: List<UInt>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<UInt>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> ULong.hash(state: H) {
    state.writeU64(this)
}

public fun <H> hashHashSliceULong(data: List<ULong>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<ULong>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> Int.hash(state: H) {
    state.writeUsize(this)
}

public fun <H> hashHashSliceInt(data: List<Int>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<Int>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> Byte.hash(state: H) {
    state.writeI8(this)
}

public fun <H> hashHashSliceByte(data: List<Byte>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<Byte>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> Short.hash(state: H) {
    state.writeI16(this)
}

public fun <H> hashHashSliceShort(data: List<Short>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<Short>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> Long.hash(state: H) {
    state.writeI64(this)
}

public fun <H> hashHashSliceLong(data: List<Long>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<Long>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> U128.hash(state: H) {
    state.writeU128(this)
}

public fun <H> hashHashSliceU128(data: List<U128>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<U128>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> I128.hash(state: H) {
    state.writeI128(this)
}

public fun <H> hashHashSliceI128(data: List<I128>, state: H) {
    val newlen = sizeOfVal(data, typeLayoutT = UnresolvedTypeLayout<List<I128>>())
    val ptr = (data.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    state.write(linearSliceView(ptr, newlen.toInt(), PrimitiveLayouts.U8))
}

public fun <H> Boolean.hash(state: H) {
    state.writeU8(this.toUByte())
}

public fun <H> Char.hash(state: H) {
    state.writeU32(this.toUInt())
}

public fun <H> String.hash(state: H) {
    state.append(this)
}

public fun <H> Nothing.hash(arg0: H) {
    this
}

public fun <H> Unit.hash(state: H) {}

public fun <T : Hash<T>, B : Hash<B>, S> Pair<T, B>.hash(state: S) {
    val (T, B) = this
    T.hash(state)
    B.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, S> Triple<T, B, C>.hash(state: S) {
    val (T, B, C) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, S> List<Any?>.hash(state: S) {
    val (T, B, C, D) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, S> List<Any?>.hashFromTBCDE(state: S) {
    val (T, B, C, D, E) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, S> List<Any?>.hashFromTBCDEF(state: S) {
    val (T, B, C, D, E, F) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, S> List<Any?>.hashFromTBCDEFG(state: S) {
    val (T, B, C, D, E, F, G) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, H : Hash<H>, S> List<Any?>.hashFromTBCDEFGH(state: S) {
    val (T, B, C, D, E, F, G, H) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
    H.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, H : Hash<H>, I : Hash<I>, S> List<Any?>.hashFromTBCDEFGHI(state: S) {
    val (T, B, C, D, E, F, G, H, I) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
    H.hash(state)
    I.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, H : Hash<H>, I : Hash<I>, J : Hash<J>, S> List<Any?>.hashFromTBCDEFGHIJ(state: S) {
    val (T, B, C, D, E, F, G, H, I, J) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
    H.hash(state)
    I.hash(state)
    J.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, H : Hash<H>, I : Hash<I>, J : Hash<J>, K : Hash<K>, S> List<Any?>.hashFromTBCDEFGHIJK(state: S) {
    val (T, B, C, D, E, F, G, H, I, J, K) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
    H.hash(state)
    I.hash(state)
    J.hash(state)
    K.hash(state)
}

public fun <T : Hash<T>, B : Hash<B>, C : Hash<C>, D : Hash<D>, E : Hash<E>, F : Hash<F>, G : Hash<G>, H : Hash<H>, I : Hash<I>, J : Hash<J>, K : Hash<K>, L : Hash<L>, S> List<Any?>.hashFromTBCDEFGHIJKL(state: S) {
    val (T, B, C, D, E, F, G, H, I, J, K, L) = this
    T.hash(state)
    B.hash(state)
    C.hash(state)
    D.hash(state)
    E.hash(state)
    F.hash(state)
    G.hash(state)
    H.hash(state)
    I.hash(state)
    J.hash(state)
    K.hash(state)
    L.hash(state)
}

public fun <T : Hash<T>, H> List<T>.hashFromTSlice(state: H) {
    state.writeLengthPrefix(this.size)
    Hash.hashSlice(this, state)
}

public fun <H> ConstPtr.hash(state: H) {
    val (address, metadata) = this.toRawParts()
    state.writeUsize(address.addr())
    metadata.hash(state)
}

public fun <H> MutPtr.hash(state: H) {
    val (address, metadata) = this.toRawParts()
    state.writeUsize(address.addr())
    metadata.hash(state)
}

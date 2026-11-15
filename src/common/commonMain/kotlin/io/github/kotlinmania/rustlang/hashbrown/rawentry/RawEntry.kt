// port-lint: source raw_entry.rs
package io.github.kotlinmania.rustlang.hashbrown.rawentry

import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.hashbrown.Equivalent
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.equivalent
import io.github.kotlinmania.rustlang.hashbrown.makeHash
import io.github.kotlinmania.rustlang.hashbrown.makeHasher
import io.github.kotlinmania.rustlang.hashbrown.raw.Bucket
import io.github.kotlinmania.rustlang.hashbrown.raw.RawTable

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
 * Creates a raw entry builder for the `HashMap`.
 *
 * Raw entries provide the lowest level of control for searching and
 * manipulating a map. They must be manually initialized with a hash and
 * then manually searched. After this, insertions into a vacant entry
 * still require an owned key to be provided.
 *
 * Raw entries are useful for such exotic situations as:
 *
 * * Hash memoization
 * * Deferring the creation of an owned key until it is known to be required
 * * Using a search key that doesn't work with the Borrow trait
 * * Using custom comparison logic without newtype wrappers
 *
 * Because raw entries provide much more low-level control, it's much easier
 * to put the `HashMap` into an inconsistent state which, while memory-safe,
 * will cause the map to produce seemingly random results. Higher-level and
 * more foolproof APIs like `entry` should be preferred when possible.
 *
 * In particular, the hash used to initialized the raw entry must still be
 * consistent with the hash of the key that is ultimately stored in the entry.
 * This is because implementations of `HashMap` may need to recompute hashes
 * when resizing, at which point only the keys are available.
 *
 * Raw entries give mutable access to the keys. This must not be used
 * to modify how the key would compare or hash, as the map will not re-evaluate
 * where the key should go, meaning the keys may become "lost" if their
 * location does not reflect their state. For instance, if you change a key
 * so that the map now contains keys which compare equal, search may start
 * acting erratically, with two keys randomly masking each other. Implementations
 * are free to assume this doesn't happen (within the limits of memory-safety).
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.RawEntryMut
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a", 100), Pair("b", 200), Pair("c", 300)))
 * // Existing key (insert and update)
 * when (val matchVal = map.rawEntryMut().fromKey("a")) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == 100)
 *         val v = view.getMut()
 *         val newV = v * 10
 *         v = newV
 *         check(view.insert(1111) == 1000)
 *     }
 * }
 * check(map["a"] == 1111)
 * check(map.size == 3)
 * // Existing key (take)
 * val hash = computeHash(map.hasher(), "c")
 * when (val matchVal1 = map.rawEntryMut().fromKeyHashedNocheck(hash, "c")) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view1) = matchVal1
 *         check(view1.removeEntry() == Pair("c", 300))
 *     }
 * }
 * check(map.rawEntry().fromKey("c") == null)
 * check(map.size == 2)
 * // Nonexistent key (insert and update)
 * val key = "d"
 * val hash1 = computeHash(map.hasher(), key)
 * when (val matchVal2 = map.rawEntryMut().fromHash(hash1, { q -> q == key })) {
 *     is RawEntryMut.Occupied -> error("unreachable")
 *     is RawEntryMut.Vacant -> {
 *         val (view2) = matchVal2
 *         val (k, value) = view2.insert("d", 4000)
 *         check(Pair(k, value) == Pair("d", 4000))
 *         value = 40000
 *     }
 * }
 * check(map["d"] == 40000)
 * check(map.size == 3)
 * when (val matchVal3 = map.rawEntryMut().fromHash(hash1, { q -> q == key })) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view3) = matchVal3
 *         check(view3.removeEntry() == Pair("d", 40000))
 *     }
 * }
 * check(map.get("d") == null)
 * check(map.size == 2)
 * ```
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.rawEntryMut(): RawEntryBuilderMut<K, V, S, A> = RawEntryBuilderMut(map = this)

/**
 * Creates a raw immutable entry builder for the `HashMap`.
 *
 * Raw entries provide the lowest level of control for searching and
 * manipulating a map. They must be manually initialized with a hash and
 * then manually searched.
 *
 * This is useful for
 * * Hash memoization
 * * Using a search key that doesn't work with the Borrow trait
 * * Using custom comparison logic without newtype wrappers
 *
 * Unless you are in such a situation, higher-level and more foolproof APIs like
 * `get` should be preferred.
 *
 * Immutable raw entries have very limited use; you might instead want `rawEntryMut`.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.HashMap
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a", 100), Pair("b", 200), Pair("c", 300)))
 * for (k in listOf("a", "b", "c", "d", "e", "f")) {
 *     val hash = computeHash(map.hasher(), k)
 *     val v = map.get(k)
 *     val kv = v?.let({ v1 -> Pair(k, v1) })
 *     println("Key: $k and value: $v")
 *     check(map.rawEntry().fromKey(k) == kv)
 *     check(map.rawEntry().fromHash(hash, { q -> q == k }) == kv)
 *     check(map.rawEntry().fromKeyHashedNocheck(hash, k) == kv)
 * }
 * ```
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.rawEntry(): RawEntryBuilder<K, V, S, A> = RawEntryBuilder(map = this)

/**
 * A builder for computing where in a [HashMap] a key-value pair would be stored.
 *
 * See the [HashMap.rawEntryMut] docs for usage examples.
 *
 * [HashMap.rawEntryMut]: HashMap.rawEntryMut
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.HashMap
 * import hashbrown.hashmap.RawEntryBuilderMut
 * import hashbrown.hashmap.rawentrymut.Occupied
 * import hashbrown.hashmap.rawentrymut.Vacant
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair(1, 11), Pair(2, 12), Pair(3, 13), Pair(4, 14), Pair(5, 15), Pair(6, 16)))
 * check(map.size == 6)
 * val builder: RawEntryBuilderMut = map.rawEntryMut()
 * // Existing key
 * when (val matchVal = builder.fromKey(6)) {
 *     is Vacant -> error("unreachable")
 *     is Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == 16)
 *     }
 * }
 * for (key in 0 until 12) {
 *     val hash = computeHash(map.hasher(), key)
 *     val value = map.get(key)
 *     val keyValue = value?.let({ v -> Pair(key, v) })
 *     println("Key: $key and value: $value")
 *     when (val matchVal1 = map.rawEntryMut().fromKey(key)) {
 *         is Occupied -> {
 *             val (o) = matchVal1
 *             check(o.getKeyValue() == keyValue)
 *         }
 *         is Vacant -> check(value == null)
 *     }
 *     when (val matchVal2 = map.rawEntryMut().fromKeyHashedNocheck(hash, key)) {
 *         is Occupied -> {
 *             val (o1) = matchVal2
 *             check(o1.getKeyValue() == keyValue)
 *         }
 *         is Vacant -> check(value == null)
 *     }
 *     when (val matchVal3 = map.rawEntryMut().fromHash(hash, { q -> q == key })) {
 *         is Occupied -> {
 *             val (o2) = matchVal3
 *             check(o2.getKeyValue() == keyValue)
 *         }
 *         is Vacant -> check(value == null)
 *     }
 * }
 * check(map.size == 6)
 * ```
 */
public open class RawEntryBuilderMut<K, V, S, A : Allocator>(
    public var map: MutableMap<K, V>,
) {
    /**
     * Creates a `RawEntryMut` from the given key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key = "a"
     * val entry: RawEntryMut<String, UInt> = map.rawEntryMut().fromKey(key)
     * entry.insert(key, 100)
     * check(map["a"] == 100)
     * ```
     */
    public open fun <Q> fromKey(k: Q): RawEntryMut<K, V, S, A> where Q : Equivalent<K> {
        val hash = makeHash<Q, S>(this.map.hashBuilder, k)
        return this.fromKeyHashedNocheck(hash, k)
    }

    /**
     * Creates a `RawEntryMut` from the given key and its hash.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
     *     var state = hashBuilder.buildHasher()
     *     key.hash(state)
     *     return state.finish()
     * }
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key = "a"
     * val hash = computeHash(map.hasher(), key)
     * val entry: RawEntryMut<String, UInt> = map.rawEntryMut().fromKeyHashedNocheck(hash, key)
     * entry.insert(key, 100)
     * check(map["a"] == 100)
     * ```
     */
    public open fun <Q> fromKeyHashedNocheck(hash: ULong, k: Q): RawEntryMut<K, V, S, A> where Q : Equivalent<K> = this.fromHash(hash, equivalent(k))

    /**
     * Creates a `RawEntryMut` from the given hash and matching function.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
     *     var state = hashBuilder.buildHasher()
     *     key.hash(state)
     *     return state.finish()
     * }
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key = "a"
     * val hash = computeHash(map.hasher(), key)
     * val entry: RawEntryMut<String, UInt> = map.rawEntryMut().fromHash(hash, { k -> k == key })
     * entry.insert(key, 100)
     * check(map["a"] == 100)
     * ```
     */
    public open fun fromHash(hash: ULong, isMatch: (K) -> Boolean): RawEntryMut<K, V, S, A> = this.search(hash, isMatch)

    public open fun search(hash: ULong, isMatch: (K) -> Boolean): RawEntryMut<K, V, S, A> =
        when (val matchVal = this.map.table.find(hash, { (k, _) -> isMatch(k) })) {
            null -> RawEntryMut.Vacant(RawVacantEntryMut(table = this.map.table, hashBuilder = this.map.hashBuilder))
            else -> {
                val elem = matchVal
                RawEntryMut.Occupied(RawOccupiedEntryMut(elem = elem, table = this.map.table, hashBuilder = this.map.hashBuilder))
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "RawEntryBuilder").finish()
    }
}

/**
 * A view into a single entry in a map, which may either be vacant or occupied.
 *
 * This is a lower-level version of [Entry].
 *
 * This `sealed class` is constructed through the [rawEntryMut] method on [HashMap],
 * then calling one of the methods of that [RawEntryBuilderMut].
 *
 * [rawEntryMut]: HashMap.rawEntryMut
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.RawEntryMut
 * import hashbrown.hashmap.RawOccupiedEntryMut
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair('a', 1), Pair('b', 2), Pair('c', 3)))
 * check(map.size == 3)
 * // Existing key (insert)
 * val raw: RawEntryMut = map.rawEntryMut().fromKey('a')
 * val rawO: RawOccupiedEntryMut = raw.insert('a', 10)
 * check(map.size == 3)
 * // Nonexistent key (insert)
 * map.rawEntryMut().fromKey('d').insert('d', 40)
 * check(map.size == 4)
 * // Existing key (orInsert)
 * val hash = computeHash(map.hasher(), 'b')
 * val kv = map.rawEntryMut().fromKeyHashedNocheck(hash, 'b').orInsert('b', 20)
 * check(kv == Pair('b', RustMutableReference(allocateNativeCell(2, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
 * kv.second = 20
 * check(map.size == 4)
 * // Nonexistent key (orInsert)
 * val hash1 = computeHash(map.hasher(), 'e')
 * val kv1 = map.rawEntryMut().fromKeyHashedNocheck(hash1, 'e').orInsert('e', 50)
 * check(kv1 == Pair('e', RustMutableReference(allocateNativeCell(50, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
 * check(map.size == 5)
 * // Existing key (orInsertWith)
 * val hash2 = computeHash(map.hasher(), 'c')
 * val kv2 = map.rawEntryMut().fromHash(hash2, { q -> q == 'c' }).orInsertWith({ Pair('c', 30) })
 * check(kv2 == Pair('c', RustMutableReference(allocateNativeCell(3, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
 * kv2.second = 30
 * check(map.size == 5)
 * // Nonexistent key (orInsertWith)
 * val hash3 = computeHash(map.hasher(), 'f')
 * val kv3 = map.rawEntryMut().fromHash(hash3, { q -> q == 'f' }).orInsertWith({ Pair('f', 60) })
 * check(kv3 == Pair('f', RustMutableReference(allocateNativeCell(60, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
 * check(map.size == 6)
 * println("Our HashMap: $map")
 * var vec: Vec = map.iter().map({ (k, v) -> Pair(k, v) }).collect()
 * // The `Iter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair('a', 10), Pair('b', 20), Pair('c', 30), Pair('d', 40), Pair('e', 50), Pair('f', 60)))
 * ```
 */
public sealed class RawEntryMut<K, V, S, A : Allocator> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: HashMap = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("unreachable")
     *     is RawEntryMut.Occupied -> {}
     * }
     * ```
     */
    public class Occupied<K, V, S, A : Allocator> internal constructor(valueCtorArg: RawOccupiedEntryMut<K, V, S, A>) : RawEntryMut<K, V, S, A>() {
        public var value: RawOccupiedEntryMut<K, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): RawOccupiedEntryMut<K, V, S, A> = this.value

        public fun copy(value: RawOccupiedEntryMut<K, V, S, A> = this.value): Occupied<K, V, S, A> = Occupied<K, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, S, A : Allocator> invoke(
                value: RawOccupiedEntryMut<K, V, S, A>,
            ): Occupied<K, V, S, A> = Occupied<K, V, S, A>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * A vacant entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Occupied -> error("unreachable")
     *     is RawEntryMut.Vacant -> {}
     * }
     * ```
     */
    public class Vacant<K, V, S, A : Allocator> internal constructor(valueCtorArg: RawVacantEntryMut<K, V, S, A>) : RawEntryMut<K, V, S, A>() {
        public var value: RawVacantEntryMut<K, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): RawVacantEntryMut<K, V, S, A> = this.value

        public fun copy(value: RawVacantEntryMut<K, V, S, A> = this.value): Vacant<K, V, S, A> = Vacant<K, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, S, A : Allocator> invoke(
                value: RawVacantEntryMut<K, V, S, A>,
            ): Vacant<K, V, S, A> = Vacant<K, V, S, A>(
                valueCtorArg = value,
            )
        }
    }

    /**
     * Sets the value of the entry, and returns a `RawOccupiedEntryMut`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.rawEntryMut().fromKey("horseyland").insert("horseyland", 37)
     * check(entry.removeEntry() == Pair("horseyland", 37))
     * ```
     */
    public open fun insert(key: K, value: V): RawOccupiedEntryMut<K, V, S, A> =
        when (this) {
            is RawEntryMut.Occupied -> {
                val (entry) = this
                entry.insert(value)
                entry
            }
            is RawEntryMut.Vacant -> {
                val (entry1) = this
                entry1.insertEntry(key, value)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * mutable references to the key and value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rawEntryMut().fromKey("poneyland").orInsert("poneyland", 3)
     * check(map["poneyland"] == 3)
     * map.rawEntryMut().fromKey("poneyland").orInsert("poneyland", 10).second *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsert(defaultKey: K, defaultVal: V): Pair<K, V> =
        when (this) {
            is RawEntryMut.Occupied -> {
                val (entry) = this
                entry.intoKeyValue()
            }
            is RawEntryMut.Vacant -> {
                val (entry1) = this
                entry1.insert(defaultKey, defaultVal)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the result of the default function if empty,
     * and returns mutable references to the key and value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, String> = mutableMapOf()
     * map.rawEntryMut().fromKey("poneyland").orInsertWith({
     *     Pair("poneyland", "hoho".toString())
     * })
     * check(map["poneyland"] == "hoho".toString())
     * ```
     */
    public open fun orInsertWith(default: () -> Pair<K, V>): Pair<K, V> =
        when (this) {
            is RawEntryMut.Occupied -> {
                val (entry) = this
                entry.intoKeyValue()
            }
            is RawEntryMut.Vacant -> {
                val (entry1) = this
                val (k, v) = default()
                entry1.insert(k, v)
            }
        }

    /**
     * Provides in-place mutable access to an occupied entry before any
     * potential inserts into the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rawEntryMut().fromKey("poneyland").andModify({ k, v ->
     *     v += 1
     * }).orInsert("poneyland", 42)
     * check(map["poneyland"] == 42)
     * map.rawEntryMut().fromKey("poneyland").andModify({ k, v ->
     *     v += 1
     * }).orInsert("poneyland", 0)
     * check(map["poneyland"] == 43)
     * ```
     */
    public open fun andModify(f: (K, V) -> Unit): RawEntryMut<K, V, S, A> =
        when (this) {
            is RawEntryMut.Occupied -> {
                val (entry) = this
                run {
                    val (k, v) = entry.getKeyValueMut()
                    f(k, v)
                }
                RawEntryMut.Occupied(entry)
            }
            is RawEntryMut.Vacant -> {
                val (entry1) = this
                RawEntryMut.Vacant(entry1)
            }
        }

    /**
     * Provides shared access to the key and owned access to the value of
     * an occupied entry and allows to replace or remove it based on the
     * value of the returned option.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.rawEntryMut().fromKey("poneyland").andReplaceEntryWith({ k, v -> error("panic") })
     * when (entry) {
     *     is RawEntryMut.Vacant -> {}
     *     is RawEntryMut.Occupied -> error("panic")
     * }
     * map.put("poneyland", 42)
     * val entry1 = map.rawEntryMut().fromKey("poneyland").andReplaceEntryWith({ k, v ->
     *     check(k == "poneyland")
     *     check(v == 42)
     *     v + 1
     * })
     * when (entry1) {
     *     is RawEntryMut.Occupied -> {
     *         val (e) = entry1
     *         check(e.key() == "poneyland")
     *         check(e.get() == 43)
     *     }
     *     is RawEntryMut.Vacant -> error("panic")
     * }
     * check(map["poneyland"] == 43)
     * val entry2 = map.rawEntryMut().fromKey("poneyland").andReplaceEntryWith({ k, v -> null })
     * when (entry2) {
     *     is RawEntryMut.Vacant -> {}
     *     is RawEntryMut.Occupied -> error("panic")
     * }
     * check(!map.containsKey("poneyland"))
     * ```
     */
    public open fun andReplaceEntryWith(f: (K, V) -> V?): RawEntryMut<K, V, S, A> =
        when (this) {
            is RawEntryMut.Occupied -> {
                val (entry) = this
                entry.replaceEntryWith(f)
            }
            is RawEntryMut.Vacant -> this
        }

    public open fun fmt(f: StringBuilder) {
        when (this) {
            is RawEntryMut.Vacant -> {
                val (v) = this
                DebugTuple(f, "RawEntry").field(v).finish()
            }
            is RawEntryMut.Occupied -> {
                val (o) = this
                DebugTuple(f, "RawEntry").field(o).finish()
            }
        }
    }
}

/**
 * A view into an occupied entry in a `HashMap`.
 * It is part of the [RawEntryMut] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.RawEntryMut
 * import hashbrown.hashmap.RawOccupiedEntryMut
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a", 10), Pair("b", 20), Pair("c", 30)))
 * val rawO: RawOccupiedEntryMut = map.rawEntryMut().fromKey("a").insert("a", 100)
 * check(map.size == 3)
 * // Existing key (insert and update)
 * when (val matchVal = map.rawEntryMut().fromKey("a")) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == 100)
 *         val v = view.getMut()
 *         val newV = v * 10
 *         v = newV
 *         check(view.insert(1111) == 1000)
 *     }
 * }
 * check(map["a"] == 1111)
 * check(map.size == 3)
 * // Existing key (take)
 * val hash = computeHash(map.hasher(), "c")
 * when (val matchVal1 = map.rawEntryMut().fromKeyHashedNocheck(hash, "c")) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view1) = matchVal1
 *         check(view1.removeEntry() == Pair("c", 30))
 *     }
 * }
 * check(map.rawEntry().fromKey("c") == null)
 * check(map.size == 2)
 * val hash1 = computeHash(map.hasher(), "b")
 * when (val matchVal2 = map.rawEntryMut().fromHash(hash1, { q -> q == "b" })) {
 *     is RawEntryMut.Vacant -> error("unreachable")
 *     is RawEntryMut.Occupied -> {
 *         val (view2) = matchVal2
 *         check(view2.removeEntry() == Pair("b", 20))
 *     }
 * }
 * check(map.get("b") == null)
 * check(map.size == 1)
 * ```
 */
public open class RawOccupiedEntryMut<K, V, S, A : Allocator> internal constructor(
    elemCtorArg: Bucket<Pair<K, V>>,
    tableCtorArg: RawTable<Pair<K, V>, A>,
    public var hashBuilder: S,
) {
    public var elem: Bucket<Pair<K, V>> = elemCtorArg
        internal set

    public var table: RawTable<Pair<K, V>, A> = tableCtorArg
        internal set

    /**
     * Gets a reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.key() == "a")
     *     }
     * }
     * ```
     */
    public open fun key(): K = this.elem.asRef().first

    /**
     * Gets a mutable reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * val keyOne = "a"
     * val keyTwo = "a"
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put(keyOne.clone(), 10)
     * check(map[keyOne] == 10)
     * check(Rc.strongCount(keyOne) == 2 && Rc.strongCount(keyTwo) == 1)
     * when (val matchVal = map.rawEntryMut().fromKey(keyOne)) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         o.keyMut() = keyTwo.clone()
     *     }
     * }
     * check(map[keyTwo] == 10)
     * check(Rc.strongCount(keyOne) == 1 && Rc.strongCount(keyTwo) == 2)
     * ```
     */
    public open fun keyMut(): K = this.elem.asMut().first

    /**
     * Converts the entry into a mutable reference to the key in the entry
     * with a scope bound to the map itself.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * val keyOne = "a"
     * val keyTwo = "a"
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put(keyOne.clone(), 10)
     * check(map[keyOne] == 10)
     * check(Rc.strongCount(keyOne) == 2 && Rc.strongCount(keyTwo) == 1)
     * val insideKey: String
     * when (val matchVal = map.rawEntryMut().fromKey(keyOne)) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         insideKey = o.intoKey()
     *     }
     * }
     * insideKey = keyTwo.clone()
     * check(map[keyTwo] == 10)
     * check(Rc.strongCount(keyOne) == 1 && Rc.strongCount(keyTwo) == 2)
     * ```
     */
    public open fun intoKey(): K = this.elem.asMut().first

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.get() == 100)
     *     }
     * }
     * ```
     */
    public open fun get(): V = this.elem.asRef().second

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the value in the entry
     * with a scope bound to the map itself.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val value: RustMutableReference<UInt>
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         value = o.intoMut()
     *     }
     * }
     * (value).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (900); targetPlace.write(currentValue) }
     * check(map["a"] == 1000)
     * ```
     */
    public open fun intoMut(): V = this.elem.asMut().second

    /**
     * Gets a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         o.getMut() += 900
     *     }
     * }
     * check(map["a"] == 1000)
     * ```
     */
    public open fun getMut(): V = this.elem.asMut().second

    /**
     * Gets a reference to the key and value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.getKeyValue() == Pair("a", 100))
     *     }
     * }
     * ```
     */
    public open fun getKeyValue(): Pair<K, V> =
        run {
            val (key, value) = this.elem.asRef()
            Pair(key, value)
        }

    /**
     * Gets a mutable reference to the key and value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * val keyOne = "a"
     * val keyTwo = "a"
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put(keyOne.clone(), 10)
     * check(map[keyOne] == 10)
     * check(Rc.strongCount(keyOne) == 2 && Rc.strongCount(keyTwo) == 1)
     * when (val matchVal = map.rawEntryMut().fromKey(keyOne)) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         val (insideKey, insideValue) = o.getKeyValueMut()
     *         insideKey = keyTwo.clone()
     *         insideValue = 100
     *     }
     * }
     * check(map[keyTwo] == 100)
     * check(Rc.strongCount(keyOne) == 1 && Rc.strongCount(keyTwo) == 2)
     * ```
     */
    public open fun getKeyValueMut(): Pair<K, V> =
        run {
            var (key, value) = this.elem.asMut()
            Pair(key, value)
        }

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the key and value in the entry
     * with a scope bound to the map itself.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * val keyOne = "a"
     * val keyTwo = "a"
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put(keyOne.clone(), 10)
     * check(map[keyOne] == 10)
     * check(Rc.strongCount(keyOne) == 2 && Rc.strongCount(keyTwo) == 1)
     * val insideKey: String
     * val insideValue: RustMutableReference<UInt>
     * when (val matchVal = map.rawEntryMut().fromKey(keyOne)) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         val tuple = o.intoKeyValue()
     *         insideKey = tuple.first
     *         insideValue = tuple.second
     *     }
     * }
     * insideKey = keyTwo.clone()
     * (insideValue).write(100)
     * check(map[keyTwo] == 100)
     * check(Rc.strongCount(keyOne) == 1 && Rc.strongCount(keyTwo) == 2)
     * ```
     */
    public open fun intoKeyValue(): Pair<K, V> =
        run {
            var (key, value) = this.elem.asMut()
            Pair(key, value)
        }

    /**
     * Sets the value of the entry, and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.insert(1000) == 100)
     *     }
     * }
     * check(map["a"] == 1000)
     * ```
     */
    public open fun insert(value: V): V = replace(this.getMut(), value)

    /**
     * Sets the value of the entry, and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * val keyOne = "a"
     * val keyTwo = "a"
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put(keyOne.clone(), 10)
     * check(map[keyOne] == 10)
     * check(Rc.strongCount(keyOne) == 2 && Rc.strongCount(keyTwo) == 1)
     * when (val matchVal = map.rawEntryMut().fromKey(keyOne)) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         val oldKey = o.insertKey(keyTwo.clone())
     *         check(Rc.ptrEq(oldKey, keyOne))
     *     }
     * }
     * check(map[keyTwo] == 10)
     * check(Rc.strongCount(keyOne) == 1 && Rc.strongCount(keyTwo) == 2)
     * ```
     */
    public open fun insertKey(key: K): K = replace(this.keyMut(), key)

    /**
     * Takes the value out of the entry, and returns it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == 100)
     *     }
     * }
     * check(map.get("a") == null)
     * ```
     */
    public open fun remove(): V = this.removeEntry().second

    /**
     * Take the ownership of the key and value from the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         check(o.removeEntry() == Pair("a", 100))
     *     }
     * }
     * check(map.get("a") == null)
     * ```
     */
    public open fun removeEntry(): Pair<K, V> = this.table.remove(this.elem).first

    /**
     * Provides shared access to the key and owned access to the value of
     * the entry and allows to replace or remove it based on the
     * value of the returned option.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val rawEntry = when (val matchVal = map.rawEntryMut().fromKey("a")) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o) = matchVal
     *         o.replaceEntryWith({ k, v ->
     *             check(k == "a")
     *             check(v == 100)
     *             v + 900
     *         })
     *     }
     * }
     * val rawEntry1 = when (rawEntry) {
     *     is RawEntryMut.Vacant -> error("panic")
     *     is RawEntryMut.Occupied -> {
     *         val (o1) = rawEntry
     *         o1.replaceEntryWith({ k, v ->
     *             check(k == "a")
     *             check(v == 1000)
     *             null
     *         })
     *     }
     * }
     * when (rawEntry1) {
     *     is RawEntryMut.Vacant -> {}
     *     is RawEntryMut.Occupied -> error("panic")
     * }
     * check(map.get("a") == null)
     * ```
     */
    public open fun replaceEntryWith(f: (K, V) -> V?): RawEntryMut<K, V, S, A> =
        run {
            val tag =
                this.table.replaceBucketWith(this.elem.clone(), { (key, value) ->
                    f(key, value).map({ newValue -> Pair(key, newValue) })
                })
            if (tag == null) {
                RawEntryMut.Occupied(this)
            } else {
                RawEntryMut.Vacant(RawVacantEntryMut(table = this.table, hashBuilder = this.hashBuilder))
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "RawOccupiedEntryMut").field("key", this.key()).field("value", this.get()).finish()
    }

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            elem: Bucket<Pair<K, V>>,
            table: RawTable<Pair<K, V>, A>,
            hashBuilder: S,
        ): RawOccupiedEntryMut<K, V, S, A> = RawOccupiedEntryMut<K, V, S, A>(
            elemCtorArg = elem,
            tableCtorArg = table,
            hashBuilder = hashBuilder,
        )
    }
}

/**
 * A view into a vacant entry in a `HashMap`.
 * It is part of the [RawEntryMut] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.RawEntryMut
 * import hashbrown.hashmap.RawVacantEntryMut
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * val rawV: RawVacantEntryMut = when (val matchVal = map.rawEntryMut().fromKey("a")) {
 *     is RawEntryMut.Vacant -> {
 *         val (view) = matchVal
 *         view
 *     }
 *     is RawEntryMut.Occupied -> error("unreachable")
 * }
 * rawV.insert("a", 10)
 * check(map["a"] == 10 && map.size == 1)
 * // Nonexistent key (insert and update)
 * val hash = computeHash(map.hasher(), "b")
 * when (val matchVal1 = map.rawEntryMut().fromKeyHashedNocheck(hash, "b")) {
 *     is RawEntryMut.Occupied -> error("unreachable")
 *     is RawEntryMut.Vacant -> {
 *         val (view1) = matchVal1
 *         val (k, value) = view1.insert("b", 2)
 *         check(Pair(k, value) == Pair("b", 2))
 *         value = 20
 *     }
 * }
 * check(map["b"] == 20 && map.size == 2)
 * val hash1 = computeHash(map.hasher(), "c")
 * when (val matchVal2 = map.rawEntryMut().fromHash(hash1, { q -> q == "c" })) {
 *     is RawEntryMut.Occupied -> error("unreachable")
 *     is RawEntryMut.Vacant -> {
 *         val (view2) = matchVal2
 *         check(view2.insert("c", 30) == Pair("c", RustMutableReference(allocateNativeCell(30, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
 *     }
 * }
 * check(map["c"] == 30 && map.size == 3)
 * ```
 */
public open class RawVacantEntryMut<K, V, S, A : Allocator> internal constructor(
    tableCtorArg: RawTable<Pair<K, V>, A>,
    public var hashBuilder: S,
) {
    public var table: RawTable<Pair<K, V>, A> = tableCtorArg
        internal set

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.rawEntryMut().fromKey("c")) {
     *     is RawEntryMut.Occupied -> error("panic")
     *     is RawEntryMut.Vacant -> {
     *         val (v) = matchVal
     *         check(v.insert("c", 300) == Pair("c", RustMutableReference(allocateNativeCell(300, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     *     }
     * }
     * check(map["c"] == 300)
     * ```
     */
    public open fun insert(key: K, value: V): Pair<K, V> {
        val hash = makeHash<K, S>(this.hashBuilder, key)
        return this.insertHashedNocheck(hash, key, value)
    }

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
     *     var state = hashBuilder.buildHasher()
     *     key.hash(state)
     *     return state.finish()
     * }
     * var map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val key = "c"
     * val hash = computeHash(map.hasher(), key)
     * when (val matchVal = map.rawEntryMut().fromKeyHashedNocheck(hash, key)) {
     *     is RawEntryMut.Occupied -> error("panic")
     *     is RawEntryMut.Vacant -> {
     *         val (v) = matchVal
     *         check(v.insertHashedNocheck(hash, key, 300) == Pair("c", RustMutableReference(allocateNativeCell(300, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     *     }
     * }
     * check(map["c"] == 300)
     * ```
     */
    public open fun insertHashedNocheck(hash: ULong, key: K, value: V): Pair<K, V> {
        var (k, v) = this.table.insertEntry(hash, Pair(key, value), makeHasher<V, S>(this.hashBuilder))
        return Pair(k, v)
    }

    /**
     * Set the value of an entry with a custom hasher function.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.hashmap.HashMap
     * import hashbrown.hashmap.RawEntryMut
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * fun <K, S> makeHasher(hashBuilder: S): (K) -> ULong where S : BuildHasher {
     *     return { key: K ->
     *
     *         var state = hashBuilder.buildHasher()
     *         key.hash(state)
     *         state.finish()
     *     }
     * }
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key = "a"
     * val hashBuilder = map.hasher().clone()
     * val hash = makeHasher(hashBuilder)(key)
     * when (val matchVal = map.rawEntryMut().fromHash(hash, { q -> q == key })) {
     *     is RawEntryMut.Occupied -> error("panic")
     *     is RawEntryMut.Vacant -> {
     *         val (v) = matchVal
     *         check(v.insertWithHasher(hash, key, 100, makeHasher(hashBuilder)) == Pair("a", RustMutableReference(allocateNativeCell(100, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     *     }
     * }
     * map.extend(listOf(Pair("b", 200), Pair("c", 300), Pair("d", 400), Pair("e", 500), Pair("f", 600)))
     * check(map["a"] == 100)
     * ```
     */
    public open fun insertWithHasher(hash: ULong, key: K, value: V, hasher: (K) -> ULong): Pair<K, V> {
        var (k, v) = this.table.insertEntry(hash, Pair(key, value), { x -> hasher(x.first) })
        return Pair(k, v)
    }

    public open fun insertEntry(key: K, value: V): RawOccupiedEntryMut<K, V, S, A> {
        val hash = makeHash<K, S>(this.hashBuilder, key)
        val elem = this.table.insert(hash, Pair(key, value), makeHasher<V, S>(this.hashBuilder))
        return RawOccupiedEntryMut(elem = elem, table = this.table, hashBuilder = this.hashBuilder)
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "RawVacantEntryMut").finish()
    }

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            table: RawTable<Pair<K, V>, A>,
            hashBuilder: S,
        ): RawVacantEntryMut<K, V, S, A> = RawVacantEntryMut<K, V, S, A>(
            tableCtorArg = table,
            hashBuilder = hashBuilder,
        )
    }
}

/**
 * A builder for computing where in a [HashMap] a key-value pair would be stored.
 *
 * See the [HashMap.rawEntry] docs for usage examples.
 *
 * [HashMap.rawEntry]: HashMap.rawEntry
 *
 * # Examples
 *
 * ```kotlin
 * import core.hash.BuildHasher
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.RawEntryBuilder
 *
 * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
 *     var state = hashBuilder.buildHasher()
 *     key.hash(state)
 *     return state.finish()
 * }
 * var map = mutableMapOf()
 * map.extend(listOf(Pair(1, 10), Pair(2, 20), Pair(3, 30)))
 * for (k in 0 until 6) {
 *     val hash = computeHash(map.hasher(), k)
 *     val v = map.get(k)
 *     val kv = v?.let({ v1 -> Pair(k, v1) })
 *     println("Key: $k and value: $v")
 *     val builder: RawEntryBuilder = map.rawEntry()
 *     check(builder.fromKey(k) == kv)
 *     check(map.rawEntry().fromHash(hash, { q -> q == k }) == kv)
 *     check(map.rawEntry().fromKeyHashedNocheck(hash, k) == kv)
 * }
 * ```
 */
public open class RawEntryBuilder<K, V, S, A : Allocator>(
    public var map: MutableMap<K, V>,
) {
    /**
     * Access an immutable entry by key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * val map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val key = "a"
     * check(map.rawEntry().fromKey(key) == Pair("a", 100))
     * ```
     */
    public open fun <Q> fromKey(k: Q): Pair<K, V>? where Q : Equivalent<K> {
        val hash = makeHash<Q, S>(this.map.hashBuilder, k)
        return this.fromKeyHashedNocheck(hash, k)
    }

    /**
     * Access an immutable entry by a key and its hash.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.HashMap
     *
     * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
     *     var state = hashBuilder.buildHasher()
     *     key.hash(state)
     *     return state.finish()
     * }
     * val map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val key = "a"
     * val hash = computeHash(map.hasher(), key)
     * check(map.rawEntry().fromKeyHashedNocheck(hash, key) == Pair("a", 100))
     * ```
     */
    public open fun <Q> fromKeyHashedNocheck(hash: ULong, k: Q): Pair<K, V>? where Q : Equivalent<K> = this.fromHash(hash, equivalent(k))

    public open fun search(hash: ULong, isMatch: (K) -> Boolean): Pair<K, V>? =
        when (val matchVal = this.map.table.get(hash, { (k, _) -> isMatch(k) })) {
            null -> null
            else -> {
                val key = matchVal.first
                val value = matchVal.second
                Pair(key, value)
            }
        }

    /**
     * Access an immutable entry by hash and matching function.
     *
     * # Examples
     *
     * ```kotlin
     * import core.hash.BuildHasher
     * import hashbrown.HashMap
     *
     * fun <K, S : BuildHasher> computeHash(hashBuilder: S, key: K): ULong {
     *     var state = hashBuilder.buildHasher()
     *     key.hash(state)
     *     return state.finish()
     * }
     * val map: MutableMap<String, UInt> = listOf(Pair("a", 100), Pair("b", 200)).into()
     * val key = "a"
     * val hash = computeHash(map.hasher(), key)
     * check(map.rawEntry().fromHash(hash, { k -> k == key }) == Pair("a", 100))
     * ```
     */
    public open fun fromHash(hash: ULong, isMatch: (K) -> Boolean): Pair<K, V>? = this.search(hash, isMatch)

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "RawEntryBuilder").finish()
    }
}

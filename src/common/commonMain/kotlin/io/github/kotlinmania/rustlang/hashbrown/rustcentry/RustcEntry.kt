// port-lint: source rustc_entry.rs
package io.github.kotlinmania.rustlang.hashbrown.rustcentry

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.Drain
import io.github.kotlinmania.rustlang.hashbrown.IntoIter
import io.github.kotlinmania.rustlang.hashbrown.Iter
import io.github.kotlinmania.rustlang.hashbrown.IterMut
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.makeHash
import io.github.kotlinmania.rustlang.hashbrown.makeHasher
import io.github.kotlinmania.rustlang.hashbrown.raw.Bucket
import io.github.kotlinmania.rustlang.hashbrown.raw.RawTable
import io.github.kotlinmania.rustlang.hashbrown.rustcentry.RustcEntry.*

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
 * Gets the given key's corresponding entry in the map for in-place manipulation.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var letters = mutableMapOf()
 * for (ch in "a short treatise on fungi".chars()) {
 *     val counter = letters.rustcEntry(ch).orInsert(0)
 *     counter += 1
 * }
 * check(letters['s'] == 2)
 * check(letters['t'] == 3)
 * check(letters['u'] == 1)
 * check(letters.get('y') == null)
 * ```
 */
public fun <K, V, S, A> MutableMap<K, V>.rustcEntry(key: K): RustcEntry<K, V, A> where S : BuildHasher, A : Allocator {
    val hash = makeHash(this.hashBuilder, key)
    return when (val matchVal = this.table.find(hash, { q -> q.first.eq(key) })) {
        null -> {
            // Ideally we would put this in VacantEntry.insert, but Entry is not
            // generic over the BuildHasher and adding a generic parameter would be
            // a breaking change.
            this.reserve(1)
            RustcEntry.Vacant(RustcVacantEntry(hash = hash, key = key, table = this.table))
        }
        else -> {
            val elem = matchVal
            RustcEntry.Occupied(RustcOccupiedEntry(elem = elem, table = this.table))
        }
    }
}

/**
 * Tries to insert a key-value pair into the map, and returns
 * a mutable reference to the value in the entry.
 *
 * # Errors
 *
 * If the map already had this key present, nothing is updated, and an error
 * containing the occupied entry, the key, and the value is returned.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import hashbrown.HashMap
 * import hashbrown.hashmap.RustcOccupiedError
 *
 * var map = mutableMapOf()
 * check(map.rustcTryInsert(37, "a").ok()!! == "a")
 * run {
 *     val matchVal = map.rustcTryInsert(37, "b")
 *     when {
 *         matchVal is Err -> {
 *             val entry = matchVal.value.entry
 *             val key = matchVal.value.key
 *             val value = matchVal.value.value
 *             check(entry.key() == 37)
 *             check(entry.get() == "a")
 *             check(key == 37)
 *             check(value == "b")
 *         }
 *         else -> error("panic")
 *     }
 * }
 * ```
 */
public fun <K, V, S, A> MutableMap<K, V>.rustcTryInsert(key: K, value: V): Result<V, RustcOccupiedError<K, V, A>> where S : BuildHasher, A : Allocator {
    val hash = makeHash(this.hashBuilder, key)
    return when (val matchVal = this.table.find(hash, { q -> q.first.eq(key) })) {
        null -> {
            val hasher = makeHasher(this.hashBuilder)
            val entry = this.table.insertEntry(hash, Pair(key, value), hasher)
            Result.Ok<V, RustcOccupiedError<K, V, A>>(entry.second)
        }
        else -> {
            val elem = matchVal
            val entry1 = RustcOccupiedEntry(elem = elem, table = this.table)
            Result.Err<V, RustcOccupiedError<K, V, A>>(RustcOccupiedError(entry = entry1, key = key, value = value))
        }
    }
}

/**
 * The error returned by [`rustcTryInsert`](HashMap.rustcTryInsert) when the key already exists.
 *
 * Note: There are no impls for this type, because we expect the standard library will
 * immediately reconstruct these errors into its own `OccupiedError`.
 */
public open class RustcOccupiedError<K, V, A> internal constructor(
    entryCtorArg: RustcOccupiedEntry<K, V, A>,
    /**
     * The key which was not inserted, because the entry was already occupied.
     */
    public var key: K,
    /**
     * The value which was not inserted, because the entry was already occupied.
     */
    public var value: V,
) where A : Allocator {
    /**
     * The entry in the map that was already occupied.
     */
    public var entry: RustcOccupiedEntry<K, V, A> = entryCtorArg
        internal set

    public companion object {
        public operator fun <K, V, A> invoke(
            entry: RustcOccupiedEntry<K, V, A>,
            key: K,
            value: V,
        ): RustcOccupiedError<K, V, A> = RustcOccupiedError<K, V, A>(
            entryCtorArg = entry,
            key = key,
            value = value,
        )
    }
}

/**
 * A view into a single entry in a map, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [rustcEntry] method on [HashMap].
 *
 * [rustcEntry]: HashMap.rustcEntry
 */
public sealed class RustcEntry<K, V, A> {
    /**
     * An occupied entry.
     */
    public class Occupied<K, V, A> internal constructor(valueCtorArg: RustcOccupiedEntry<K, V, A>) : RustcEntry<K, V, A>() {
        public var value: RustcOccupiedEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): RustcOccupiedEntry<K, V, A> = this.value

        public fun copy(value: RustcOccupiedEntry<K, V, A> = this.value): Occupied<K, V, A> = Occupied<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A> invoke(
                value: RustcOccupiedEntry<K, V, A>,
            ): Occupied<K, V, A> = Occupied<K, V, A>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * A vacant entry.
     */
    public class Vacant<K, V, A> internal constructor(valueCtorArg: RustcVacantEntry<K, V, A>) : RustcEntry<K, V, A>() {
        public var value: RustcVacantEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): RustcVacantEntry<K, V, A> = this.value

        public fun copy(value: RustcVacantEntry<K, V, A> = this.value): Vacant<K, V, A> = Vacant<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A> invoke(
                value: RustcVacantEntry<K, V, A>,
            ): Vacant<K, V, A> = Vacant<K, V, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is Vacant -> {
                val (v) = this
                DebugTuple(f, "Entry").field(v).finish()
            }
            is Occupied -> {
                val (o) = this
                DebugTuple(f, "Entry").field(o).finish()
            }
        }

    /**
     * Sets the value of the entry, and returns a RustcOccupiedEntry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.rustcEntry("horseyland").insert(37)
     * check(entry.key() == "horseyland")
     * ```
     */
    public open fun insert(value: V): RustcOccupiedEntry<K, V, A> =
        when (this) {
            is Vacant -> {
                val (entry) = this
                entry.insertEntry(value)
            }
            is Occupied -> {
                val (entry1) = this
                entry1.insert(value)
                entry1
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(3)
     * check(map["poneyland"] == 3)
     * map.rustcEntry("poneyland").orInsert(10) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsert(default: V): V =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Vacant -> {
                val (entry1) = this
                entry1.insert(default)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the result of the default function if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, String> = mutableMapOf()
     * val s = "hoho".toString()
     * map.rustcEntry("poneyland").orInsertWith({ s })
     * check(map["poneyland"] == "hoho".toString())
     * ```
     */
    public open fun orInsertWith(default: () -> V): V =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Vacant -> {
                val (entry1) = this
                entry1.insert(default())
            }
        }

    /**
     * Returns a reference to this entry's key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * check(map.rustcEntry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.key()
            }
            is Vacant -> {
                val (entry1) = this
                entry1.key()
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
     * map.rustcEntry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 42)
     * map.rustcEntry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 43)
     * ```
     */
    public open fun andModify(f: (V) -> Unit): RustcEntry<K, V, A> =
        when (this) {
            is Occupied -> {
                val (entry) = this
                f(entry.getMut())
                Occupied(entry)
            }
            is Vacant -> {
                val (entry1) = this
                Vacant(entry1)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default value if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt?> = mutableMapOf()
     * map.rustcEntry("poneyland").orDefault()
     * check(map["poneyland"] == null)
     * ```
     */
    public open fun orDefault(defaultVFn: () -> V): V =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Vacant -> {
                val (entry1) = this
                entry1.insert(defaultVFn())
            }
        }
}

/**
 * A view into an occupied entry in a `HashMap`.
 * It is part of the [RustcEntry] enum.
 */
public open class RustcOccupiedEntry<K, V, A> internal constructor(
    elemCtorArg: Bucket<Pair<K, V>>,
    tableCtorArg: RawTable<Pair<K, V>, A>,
) where A : Allocator {
    public var elem: Bucket<Pair<K, V>> = elemCtorArg
        internal set

    public var table: RawTable<Pair<K, V>, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedEntry").field("key", this.key()).field("value", this.get()).finish()

    /**
     * Gets a reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * check(map.rustcEntry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.elem.asRef().first

    /**
     * Take the ownership of the key and value from the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         // We delete the entry from the map.
     *         o.removeEntry()
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * ```
     */
    public open fun removeEntry(): Pair<K, V> = this.table.remove(this.elem).first

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.get() == 12)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun get(): V = this.elem.asRef().second

    /**
     * Gets a mutable reference to the value in the entry.
     *
     * If you need a reference to the `RustcOccupiedEntry` which may outlive the
     * destruction of the `RustcEntry` value, see [intoMut].
     *
     * [intoMut]: #method.intoMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         o.getMut() += 10
     *         check(o.get() == 22)
     *         // We can use the same RustcEntry multiple times.
     *         o.getMut() += 2
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 24)
     * ```
     */
    public open fun getMut(): V = this.elem.asMut().second

    /**
     * Converts the RustcOccupiedEntry into a mutable reference to the value in the entry
     * with a scope bound to the map itself.
     *
     * If you need multiple references to the `RustcOccupiedEntry`, see [getMut].
     *
     * [getMut]: #method.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         o.intoMut() += 10
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 22)
     * ```
     */
    public open fun intoMut(): V = this.elem.asMut().second

    /**
     * Sets the value of the entry, and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.insert(15) == 12)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 15)
     * ```
     */
    public open fun insert(value: V): V = replace(this.getMut(), value)

    /**
     * Takes the value out of the entry, and returns it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.rustcEntry("poneyland").orInsert(12)
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == 12)
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * ```
     */
    public open fun remove(): V = this.removeEntry().second

    public companion object {
        public operator fun <K, V, A> invoke(
            elem: Bucket<Pair<K, V>>,
            table: RawTable<Pair<K, V>, A>,
        ): RustcOccupiedEntry<K, V, A> = RustcOccupiedEntry<K, V, A>(
            elemCtorArg = elem,
            tableCtorArg = table,
        )
    }
}

/**
 * A view into a vacant entry in a `HashMap`.
 * It is part of the [RustcEntry] enum.
 */
public open class RustcVacantEntry<K, V, A> internal constructor(
    public var hash: ULong,
    public var key: K,
    tableCtorArg: RawTable<Pair<K, V>, A>,
) where A : Allocator {
    public var table: RawTable<Pair<K, V>, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "VacantEntry").field(this.key()).finish()

    /**
     * Gets a reference to the key that would be used when inserting a value
     * through the `RustcVacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * check(map.rustcEntry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.key

    /**
     * Take ownership of the key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Vacant -> {
     *         val (v) = matchVal
     *         v.intoKey()
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun intoKey(): K = this.key

    /**
     * Sets the value of the entry with the RustcVacantEntry's key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Vacant -> {
     *         val (o) = matchVal
     *         o.insert(37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insert(value: V): V =
        run {
            val bucket = this.table.insertNoGrow(this.hash, Pair(this.key, value))
            bucket.asMut().second
        }

    /**
     * Sets the value of the entry with the RustcVacantEntry's key,
     * and returns a RustcOccupiedEntry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.RustcEntry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.rustcEntry("poneyland")) {
     *     is RustcEntry.Vacant -> {
     *         val (v) = matchVal
     *         val o = v.insertEntry(37)
     *         check(o.get() == 37)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun insertEntry(value: V): RustcOccupiedEntry<K, V, A> {
        val bucket = this.table.insertNoGrow(this.hash, Pair(this.key, value))
        return RustcOccupiedEntry(elem = bucket, table = this.table)
    }

    public companion object {
        public operator fun <K, V, A> invoke(
            hash: ULong,
            key: K,
            table: RawTable<Pair<K, V>, A>,
        ): RustcVacantEntry<K, V, A> = RustcVacantEntry<K, V, A>(
            hash = hash,
            key = key,
            tableCtorArg = table,
        )
    }
}

/**
 * Returns a iterator of references over the remaining items.
 */
public fun <K, V> IterMut<K, V>.rustcIter(): Iter<K, V> = this.iter()

/**
 * Returns a iterator of references over the remaining items.
 */
public fun <K, V, A : Allocator> IntoIter<K, V, A>.rustcIter(): Iter<K, V> = this.iter()

/**
 * Returns a iterator of references over the remaining items.
 */
public fun <K, V, A : Allocator> Drain<K, V, A>.rustcIter(): Iter<K, V> = this.iter()

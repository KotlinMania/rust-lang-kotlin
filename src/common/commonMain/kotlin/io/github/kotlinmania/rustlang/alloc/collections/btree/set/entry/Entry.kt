// port-lint: source collections/btree/set/entry.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.set.entry

import io.github.kotlinmania.rustlang.alloc.collections.btree.set.entry.Entry.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.setval.SetValZST
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone

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
 * A view into a single entry in a set, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [BTreeSet].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(btreeSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     set.extend(listOf("a", "b", "c"))
 *     check(set.size == 3)
 *     // Existing value (insert)
 *     val entry = set.entry("a")
 *     val rawO = entry.insert()
 *     check(set.size == 3)
 *     // Nonexistent value (insert)
 *     set.entry("d").insert()
 *     // Existing value (orInsert)
 *     set.entry("b").orInsert()
 *     // Nonexistent value (orInsert)
 *     set.entry("e").orInsert()
 *     println("Our BTreeSet: $set")
 *     check(set.iter().eq(listOf("a", "b", "c", "d", "e")))
 * }
 * ```
 */
public sealed class Entry<T, A : AllocatorClone> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf("a", "b")
     *     when (val matchVal = set.entry("a")) {
     *         is Entry.Vacant -> error("unreachable")
     *         is Entry.Occupied -> {}
     *     }
     * }
     * ```
     */
    public class Occupied<T, A : AllocatorClone> internal constructor(valueCtorArg: OccupiedEntry<T, A>) : Entry<T, A>() {
        public var value: OccupiedEntry<T, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<T, A> = this.value

        public fun copy(value: OccupiedEntry<T, A> = this.value): Occupied<T, A> = Occupied<T, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, A : AllocatorClone> invoke(
                value: OccupiedEntry<T, A>,
            ): Occupied<T, A> = Occupied<T, A>(
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
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("a")) {
     *         is Entry.Occupied -> error("unreachable")
     *         is Entry.Vacant -> {}
     *     }
     * }
     * ```
     */
    public class Vacant<T, A : AllocatorClone> internal constructor(valueCtorArg: VacantEntry<T, A>) : Entry<T, A>() {
        public var value: VacantEntry<T, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<T, A> = this.value

        public fun copy(value: VacantEntry<T, A> = this.value): Vacant<T, A> = Vacant<T, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, A : AllocatorClone> invoke(
                value: VacantEntry<T, A>,
            ): Vacant<T, A> = Vacant<T, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder) {
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
    }

    /**
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     val entry = set.entry("horseyland").insert()
     *     check(entry.get() == "horseyland")
     * }
     * ```
     */
    public open fun insert(): OccupiedEntry<T, A> =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry
            }
            is Vacant -> {
                val (entry1) = this
                entry1.insertEntry()
            }
        }

    /**
     * Ensures a value is in the entry by inserting if it was vacant.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     // nonexistent key
     *     set.entry("poneyland").orInsert()
     *     check(set.contains("poneyland"))
     *     // existing key
     *     set.entry("poneyland").orInsert()
     *     check(set.contains("poneyland"))
     *     check(set.size == 1)
     * }
     * ```
     */
    public open fun orInsert() {
        if (this is Vacant) {
            val (entry) = this
            entry.insert()
        }
    }

    /**
     * Returns a reference to this entry's value.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     set.entry("poneyland").orInsert()
     *     // existing key
     *     check(set.entry("poneyland").get() == "poneyland")
     *     // nonexistent key
     *     check(set.entry("horseland").get() == "horseland")
     * }
     * ```
     */
    public open fun get(): T =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.get()
            }
            is Vacant -> {
                val (entry1) = this
                entry1.get()
            }
        }
}

/**
 * A view into an occupied entry in a `BTreeSet`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.btreeset.Entry
 *
 * @feature(btreeSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     set.extend(listOf("a", "b", "c"))
 *     val entryO = set.entry("a").insert()
 *     check(set.size == 3)
 *     // Existing key
 *     when (val matchVal = set.entry("a")) {
 *         is Entry.Vacant -> error("unreachable")
 *         is Entry.Occupied -> {
 *             val (view) = matchVal
 *             check(view.get() == "a")
 *         }
 *     }
 *     check(set.size == 3)
 *     // Existing key (take)
 *     when (val matchVal1 = set.entry("c")) {
 *         is Entry.Vacant -> error("unreachable")
 *         is Entry.Occupied -> {
 *             val (view1) = matchVal1
 *             check(view1.remove() == "c")
 *         }
 *     }
 *     check(set.get("c") == null)
 *     check(set.size == 2)
 * }
 * ```
 */
public open class OccupiedEntry<T, A : AllocatorClone>(
    internal var inner: OccupiedEntry<T, SetValZST, A>,
) {
    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "OccupiedEntry").field("value", this.get()).finish()
    }

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     set.entry("poneyland").orInsert()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Vacant -> error("panic")
     *         is Entry.Occupied -> {
     *             val (entry) = matchVal
     *             check(entry.get() == "poneyland")
     *         }
     *     }
     * }
     * ```
     */
    public open fun get(): T = this.inner.key()

    /**
     * Takes the value out of the entry, and returns it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     set.entry("poneyland").orInsert()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Occupied -> {
     *             val (o) = matchVal
     *             check(o.remove() == "poneyland")
     *         }
     *         else -> {}
     *     }
     *     check(set.contains("poneyland") == false)
     * }
     * ```
     */
    public open fun remove(): T = this.inner.removeEntry().first
}

/**
 * A view into a vacant entry in a `BTreeSet`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.btreeset.Entry
 *
 * @feature(btreeSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     val entryV = when (val matchVal = set.entry("a")) {
 *         is Entry.Vacant -> {
 *             val (view) = matchVal
 *             view
 *         }
 *         is Entry.Occupied -> error("unreachable")
 *     }
 *     entryV.insert()
 *     check(set.contains("a") && set.size == 1)
 *     // Nonexistent key (insert)
 *     when (val matchVal1 = set.entry("b")) {
 *         is Entry.Vacant -> {
 *             val (view1) = matchVal1
 *             view1.insert()
 *         }
 *         is Entry.Occupied -> error("unreachable")
 *     }
 *     check(set.contains("b") && set.size == 2)
 * }
 * ```
 */
public open class VacantEntry<T, A : AllocatorClone>(
    internal var inner: VacantEntry<T, SetValZST, A>,
) {
    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "VacantEntry").field(this.get()).finish()
    }

    /**
     * Gets a reference to the value that would be used when inserting
     * through the `VacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     check(set.entry("poneyland").get() == "poneyland")
     * }
     * ```
     */
    public open fun get(): T = this.inner.key()

    /**
     * Take ownership of the value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Occupied -> error("panic")
     *         is Entry.Vacant -> {
     *             val (v) = matchVal
     *             check(v.intoValue() == "poneyland")
     *         }
     *     }
     * }
     * ```
     */
    public open fun intoValue(): T = this.inner.intoKey()

    /**
     * Sets the value of the entry with the VacantEntry's value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Vacant -> {
     *             val (o) = matchVal
     *             o.insert()
     *         }
     *         else -> {}
     *     }
     *     check(set.contains("poneyland"))
     * }
     * ```
     */
    public open fun insert() {
        this.inner.insert(SetValZST)
    }

    public open fun insertEntry(): OccupiedEntry<T, A> = OccupiedEntry(inner = this.inner.insertEntry(SetValZST))
}

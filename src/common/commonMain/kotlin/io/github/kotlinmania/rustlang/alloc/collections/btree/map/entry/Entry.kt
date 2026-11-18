// port-lint: source collections/btree/map/entry.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry

import io.github.kotlinmania.rustlang.alloc.collections.btree.borrow.DormantMutRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.Entry.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Mut
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugStruct(
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

private class DebugTuple(
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
 * A view into a single entry in a map, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [BTreeMap].
 *
 * [entry]: BTreeMap.entry
 */
public sealed class Entry<K, V, A : AllocatorClone> {
    /**
     * A vacant entry.
     */
    public class Vacant<K, V, A : AllocatorClone> internal constructor(valueCtorArg: VacantEntry<K, V, A>) : Entry<K, V, A>() {
        public var value: VacantEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<K, V, A> = this.value

        public fun copy(value: VacantEntry<K, V, A> = this.value): Vacant<K, V, A> = Vacant<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A : AllocatorClone> invoke(
                value: VacantEntry<K, V, A>,
            ): Vacant<K, V, A> = Vacant<K, V, A>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * An occupied entry.
     */
    public class Occupied<K, V, A : AllocatorClone> internal constructor(valueCtorArg: OccupiedEntry<K, V, A>) : Entry<K, V, A>() {
        public var value: OccupiedEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<K, V, A> = this.value

        public fun copy(value: OccupiedEntry<K, V, A> = this.value): Occupied<K, V, A> = Occupied<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A : AllocatorClone> invoke(
                value: OccupiedEntry<K, V, A>,
            ): Occupied<K, V, A> = Occupied<K, V, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (this) {
            is Vacant<*, *, *> -> {
                val (v) = this as Vacant<K, V, A>
                run { DebugTuple(f, "Entry").field(v).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
            is Occupied<*, *, *> -> {
                val (o) = this as Occupied<K, V, A>
                run { DebugTuple(f, "Entry").field(o).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * ```
     */
    public open fun orInsert(default: V): V =
        when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                entry.intoMut()
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
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
     * var map: MutableMap<String, String> = mutableMapOf()
     * val s = "hoho".toString()
     * map.entry("poneyland").orInsertWith({ s })
     * check(map["poneyland"] == "hoho".toString())
     * ```
     */
    public open fun orInsertWith(default: () -> V): V = this.orTryInsertWith({ Result.Ok(default()) }).unwrap()

    /**
     * Ensures a value is in the entry by inserting the result of a fallible default function
     * if empty, and returns a mutable reference to the value in the entry.
     *
     * This method works identically to [orInsertWith] except that the default function
     * should return a `Result` and, in the case of an error, the error is propagated.
     *
     * [orInsertWith]: Self.orInsertWith
     *
     * # Examples
     *
     * ```kotlin
     * @feature(tryEntry)
     * public fun doctestEntry(): Result<Unit> {
     *     var map: MutableMap<String, Int> = mutableMapOf()
     *     val value = "42"
     *     map.entry("poneyland").orTryInsertWith({ value.parse() }).getOrElse { return Result.failure(it) }
     *     check(map["poneyland"] == 42)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun <E> orTryInsertWith(default: () -> Result<V, E>): Result<V, E> {
        return when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                Result.Ok<V, E>(entry.intoMut())
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
                Result.Ok<V, E>(entry1.insert(when (val tryResult = default()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<V, E>(tryResult.errValue()) }))
            }
        }
    }

    /**
     * Ensures a value is in the entry by inserting, if empty, the result of the default function.
     *
     * This method allows for generating key-derived values for insertion by providing the default
     * function a reference to the key that was moved during the `.entry(key)` method call.
     *
     * The reference to the moved key is provided so that cloning or copying the key is
     * unnecessary, unlike with `.orInsertWith(|| ...)`.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsertWithKey({ key -> key.chars().count() })
     * check(map["poneyland"] == 9)
     * ```
     */
    public open fun orInsertWithKey(default: (K) -> V): V = this.orTryInsertWithKey({ k -> Result.Ok(default(k)) }).intoOk()

    /**
     * Ensures a value is in the entry by inserting, if empty, the result of the default function.
     * This method allows for generating key-derived values for insertion by providing the default
     * function a reference to the key that was moved during the `entry(key)` method call.
     *
     * This method works identically to [orInsertWithKey] except that the default function
     * should return a `Result` and, in the case of an error, the error is propagated.
     *
     * [orInsertWithKey]: Self.orInsertWithKey
     *
     * # Examples
     *
     * ```kotlin
     * @feature(tryEntry)
     * public fun doctestEntry(): Result<Unit> {
     *     var map: MutableMap<String, Int> = mutableMapOf()
     *     map.entry("42").orTryInsertWithKey({ key -> key.parse() }).getOrElse { return Result.failure(it) }
     *     check(map["42"] == 42)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun <E> orTryInsertWithKey(default: (K) -> Result<V, E>): Result<V, E> {
        return when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                Result.Ok<V, E>(entry.intoMut())
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
                val value = when (val tryResult = default(entry1.key())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<V, E>(tryResult.errValue()) }
                Result.Ok<V, E>(entry1.insert(value))
            }
        }
    }

    /**
     * Returns a reference to this entry's key.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K =
        when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                entry.key()
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
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
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 42)
     * map.entry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 43)
     * ```
     */
    public open fun andModify(f: (V) -> Unit): Entry<K, V, A> =
        when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                f(entry.getMut())
                Occupied(entry)
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
                Vacant(entry1)
            }
        }

    /**
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, String> = mutableMapOf()
     * val entry = map.entry("poneyland").insertEntry("hoho".toString())
     * check(entry.key() == "poneyland")
     * check(entry.get() == "hoho")
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, A> =
        when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                entry.insert(value)
                entry
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
                entry1.insertEntry(value)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default value if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int?> = mutableMapOf()
     * map.entry("poneyland").orDefault()
     * check(map["poneyland"] == null)
     * ```
     */
    public open fun orDefault(defaultVFn: () -> V): V =
        when (this) {
            is Occupied<*, *, *> -> {
                val (entry) = this as Occupied<K, V, A>
                entry.intoMut()
            }
            is Vacant<*, *, *> -> {
                val (entry1) = this as Vacant<K, V, A>
                entry1.insert(defaultVFn())
            }
        }
}

/**
 * A view into a vacant entry in a `BTreeMap`.
 * It is part of the [Entry] enum.
 */
public open class VacantEntry<K, V, A : AllocatorClone> internal constructor(
    public var key: K,
    handleCtorArg: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
    dormantMapCtorArg: DormantMutRef<MutableMap<K, V>>,
    /**
     * The BTreeMap will outlive this IntoIter so we don't care about drop order for `alloc`.
     */
    public var alloc: A,
    // Be invariant in `K` and `V`
    public var marker: Unit,
) {
    /**
     * `None` for a (empty) map without root
     */
    public var handle: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>? = handleCtorArg
        internal set

    public var dormantMap: DormantMutRef<MutableMap<K, V>> = dormantMapCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugTuple(f, "VacantEntry").field(this.key()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Gets a reference to the key that would be used when inserting a value
     * through the VacantEntry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.key

    /**
     * Take ownership of the key.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (v) = matchVal
     *         v.intoKey()
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun intoKey(): K = this.key

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         o.insert(37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insert(value: V): V = this.insertEntry(value).intoMut()

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         val entry = o.insertEntry(37)
     *         check(entry.get() == 37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, A> {
        val handle1 =
            when (val matchVal = this.handle) {
                null -> {
                    // SAFETY: There is no tree yet so no reference to it exists.
                    val map = this.dormantMap.reborrow()
                    val root = map.root.insert(NodeRef.newLeaf(this.alloc.clone()).forgetType())
                    // SAFETY: We *just* created the root as a leaf, and we're
                    // stacking the new handle on the original borrow scope.
                    run {
                        val leaf = root.borrowMut().castToLeafUnchecked()
                        leaf.pushWithHandle(this.key, value)
                    }
                }
                else -> {
                    val handle = matchVal
                    handle.insertRecursing(this.key, value, this.alloc.clone(), { ins ->
                        (ins.left).let { }
                        // SAFETY: Pushing a new root node doesn't invalidate
                        // handles to existing nodes.
                        val map1 = this.dormantMap.reborrow()
                        val root1 = map1.root.asMut().unwrap()
                        // same as ins.left
                        root1.pushInternalLevel(this.alloc.clone()).push(ins.kv.first, ins.kv.second, ins.right)
                    })
                }
            }
        // SAFETY: modifying the length doesn't invalidate handles to existing nodes.
        this.dormantMap.reborrow().length += 1
        return OccupiedEntry(handle = handle1.forgetNodeType(), dormantMap = this.dormantMap, alloc = this.alloc, marker = Unit)
    }

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            key: K,
            handle: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
            dormantMap: DormantMutRef<MutableMap<K, V>>,
            alloc: A,
            marker: Unit,
        ): VacantEntry<K, V, A> = VacantEntry<K, V, A>(
            key = key,
            handleCtorArg = handle,
            dormantMapCtorArg = dormantMap,
            alloc = alloc,
            marker = marker,
        )
    }
}

/**
 * A view into an occupied entry in a `BTreeMap`.
 * It is part of the [Entry] enum.
 */
public open class OccupiedEntry<K, V, A : AllocatorClone> internal constructor(
    handleCtorArg: Handle<NodeRef<Mut, K, V, LeafOrInternal>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.KV>,
    dormantMapCtorArg: DormantMutRef<MutableMap<K, V>>,
    /**
     * The BTreeMap will outlive this IntoIter so we don't care about drop order for `alloc`.
     */
    public var alloc: A,
    // Be invariant in `K` and `V`
    public var marker: Unit,
) {
    public var handle: Handle<NodeRef<Mut, K, V, LeafOrInternal>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.KV> = handleCtorArg
        internal set

    public var dormantMap: DormantMutRef<MutableMap<K, V>> = dormantMapCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "OccupiedEntry").field("key", this.key()).field("value", this.get()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Gets a reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.handle.reborrow().intoKv().first

    /**
     * Converts the entry into a reference to its key.
     */
    public open fun intoKey(): K = this.handle.intoKvMut().first

    /**
     * Take ownership of the key and value from the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         // We delete the entry from the map.
     *         o.removeEntry()
     *     }
     *     else -> {}
     * }
     * // If now try to get the value, it will panic:
     * // println("()", map["poneyland"]);
     * ```
     */
    public open fun removeEntry(): Pair<K, V> = this.removeKv()

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.get() == 12)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun get(): V = this.handle.reborrow().intoKv().second

    /**
     * Gets a mutable reference to the value in the entry.
     *
     * If you need a reference to the `OccupiedEntry` that may outlive the
     * destruction of the `Entry` value, see [intoMut].
     *
     * [intoMut]: OccupiedEntry.intoMut
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         o.getMut() += 10
     *         check(o.get() == 22)
     *         // We can use the same Entry multiple times.
     *         o.getMut() += 2
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 24)
     * ```
     */
    public open fun getMut(): V = this.handle.kvMut().second

    /**
     * Converts the entry into a mutable reference to its value.
     *
     * If you need multiple references to the `OccupiedEntry`, see [getMut].
     *
     * [getMut]: OccupiedEntry.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         o.intoMut() += 10
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 22)
     * ```
     */
    public open fun intoMut(): V = this.handle.intoValMut()

    /**
     * Sets the value of the entry with the `OccupiedEntry`s key,
     * and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
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
     * Takes the value of the entry out of the map, and returns it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreemap.Entry
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == 12)
     *     }
     *     else -> {}
     * }
     * // If we try to get "poneyland"'s value, it'll panic:
     * // println("()", map["poneyland"]);
     * ```
     */
    public open fun remove(): V = this.removeKv().second

    // Body of `removeEntry`, probably separate because the name reflects the returned pair.
    public open fun removeKv(): Pair<K, V> {
        var emptiedInternalRoot = false
        val (oldKv, _) = this.handle.removeKvTracking({ emptiedInternalRoot = true }, this.alloc.clone())
        // SAFETY: we consumed the intermediate root borrow, `self.handle`.
        val map = this.dormantMap.awaken()
        map.length -= 1
        if (emptiedInternalRoot) {
            val root = map.root.asMut().unwrap()
            root.popInternalLevel(this.alloc)
        }
        return oldKv
    }

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            handle: Handle<NodeRef<Mut, K, V, LeafOrInternal>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.KV>,
            dormantMap: DormantMutRef<MutableMap<K, V>>,
            alloc: A,
            marker: Unit,
        ): OccupiedEntry<K, V, A> = OccupiedEntry<K, V, A>(
            handleCtorArg = handle,
            dormantMapCtorArg = dormantMap,
            alloc = alloc,
            marker = marker,
        )
    }
}

/**
 * The error returned by [`tryInsert`](BTreeMap.tryInsert) when the key already exists.
 *
 * Contains the occupied entry, key, and the value that was not inserted.
 */
public open class OccupiedError<K, V, A : AllocatorClone> internal constructor(
    entryCtorArg: OccupiedEntry<K, V, A>,
    /**
     * The key which was not inserted, because the entry was already occupied.
     */
    public var key: K,
    /**
     * The value which was not inserted, because the entry was already occupied.
     */
    public var value: V,
) {
    /**
     * The entry in the map that was already occupied.
     */
    public var entry: OccupiedEntry<K, V, A> = entryCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "OccupiedError").field("key", this.entry.key()).field("uninserted_key", this.key).field("old_value", this.entry.get()).field("new_value", this.value).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            entry: OccupiedEntry<K, V, A>,
            key: K,
            value: V,
        ): OccupiedError<K, V, A> = OccupiedError<K, V, A>(
            entryCtorArg = entry,
            key = key,
            value = value,
        )
    }
}

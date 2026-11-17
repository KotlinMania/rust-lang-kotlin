// port-lint: tests raw_entry.rs
package io.github.kotlinmania.rustlang.hashbrown.rawentry

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.hashbrown.HashMap
import io.github.kotlinmania.rustlang.hashbrown.makeHash
import io.github.kotlinmania.rustlang.hashbrown.rawentry.RawEntryMut.Occupied
import io.github.kotlinmania.rustlang.hashbrown.rawentry.RawEntryMut.Vacant

public class RawEntryTestMapInlineTests {
    @kotlin.test.Test
    public fun testRawOccupiedEntryReplaceEntryWith() {
        val a = mutableMapOf()
        val key = "a key"
        val value = "an initial value"
        val newValue = "a new value"
        val entry =
            a.rawEntryMut().fromKey(key).insert(key, value).replaceEntryWith({ k, v ->
                check(k == key)
                check(v == value)
                newValue
            })
        when (entry) {
            is RawEntryMut.Occupied<*, *, *, *> -> {
                val (e) = entry
                check(e.key() == key)
                check(e.get() == newValue)
            }
            is RawEntryMut.Vacant<*, *, *, *> -> error("panic")
        }
        check(a[key] == newValue)
        check(a.size == 1)
        val entry1 =
            when (val matchVal = a.rawEntryMut().fromKey(key)) {
                is RawEntryMut.Occupied<*, *, *, *> -> {
                    val (e1) = matchVal
                    e1.replaceEntryWith({ k, v ->
                        check(k == key)
                        check(v == newValue)
                        null
                    })
                }
                is RawEntryMut.Vacant<*, *, *, *> -> error("panic")
            }
        when (entry1) {
            is RawEntryMut.Vacant<*, *, *, *> -> {}
            is RawEntryMut.Occupied<*, *, *, *> -> error("panic")
        }
        check(!a.containsKey(key))
        check(a.size == 0)
    }

    @kotlin.test.Test
    public fun testRawEntryAndReplaceEntryWith() {
        val a = mutableMapOf()
        val key = "a key"
        val value = "an initial value"
        val newValue = "a new value"
        val entry = a.rawEntryMut().fromKey(key).andReplaceEntryWith({ error("panic") })
        when (entry) {
            is RawEntryMut.Vacant<*, *, *, *> -> {}
            is RawEntryMut.Occupied<*, *, *, *> -> error("panic")
        }
        a.put(key, value)
        val entry1 =
            a.rawEntryMut().fromKey(key).andReplaceEntryWith({ k, v ->
                check(k == key)
                check(v == value)
                newValue
            })
        when (entry1) {
            is RawEntryMut.Occupied<*, *, *, *> -> {
                val (e) = entry1
                check(e.key() == key)
                check(e.get() == newValue)
            }
            is RawEntryMut.Vacant<*, *, *, *> -> error("panic")
        }
        check(a[key] == newValue)
        check(a.size == 1)
        val entry2 =
            a.rawEntryMut().fromKey(key).andReplaceEntryWith({ k, v ->
                check(k == key)
                check(v == newValue)
                null
            })
        when (entry2) {
            is RawEntryMut.Vacant<*, *, *, *> -> {}
            is RawEntryMut.Occupied<*, *, *, *> -> error("panic")
        }
        check(!a.containsKey(key))
        check(a.size == 0)
    }

    @kotlin.test.Test
    public fun testRawEntry() {
        val xs = listOf(Pair(1, 10), Pair(2, 20), Pair(3, 30), Pair(4, 40), Pair(5, 50), Pair(6, 60))
        val map: HashMap  = xs.iter().copied().collect()
        val computeHash =
            { map1: MutableMap<Int, Int>, k: Int ->
                makeHash<Int>(map1.hasher(), k)
            }
        // Existing key (insert)
        when (val matchVal = map.rawEntryMut().fromKey(1)) {
            is Vacant<*, *, *, *> -> error("unreachable")
            is Occupied<*, *, *, *> -> {
                val (view) = matchVal
                check(view.get() == 10)
                check(view.insert(100) == 10)
            }
        }
        val hash1 = computeHash(map, 1)
        check(map.rawEntry().fromKey(1).unwrap() == Pair(1, 100))
        check(map.rawEntry().fromHash(hash1, { k -> k == 1 }).unwrap() == Pair(1, 100))
        check(map.rawEntry().fromKeyHashedNocheck(hash1, 1).unwrap() == Pair(1, 100))
        check(map.len() == 6)
        // Existing key (update)
        when (val matchVal1 = map.rawEntryMut().fromKey(2)) {
            is Vacant<*, *, *, *> -> error("unreachable")
            is Occupied<*, *, *, *> -> {
                val (view1) = matchVal1
                val v = view1.getMut()
                val newV = v * 10
                v = newV
            }
        }
        val hash2 = computeHash(map, 2)
        check(map.rawEntry().fromKey(2).unwrap() == Pair(2, 200))
        check(map.rawEntry().fromHash(hash2, { k -> k == 2 }).unwrap() == Pair(2, 200))
        check(map.rawEntry().fromKeyHashedNocheck(hash2, 2).unwrap() == Pair(2, 200))
        check(map.len() == 6)
        // Existing key (take)
        val hash3 = computeHash(map, 3)
        when (val matchVal2 = map.rawEntryMut().fromKeyHashedNocheck(hash3, 3)) {
            is Vacant<*, *, *, *> -> error("unreachable")
            is Occupied<*, *, *, *> -> {
                val (view2) = matchVal2
                check(view2.removeEntry() == Pair(3, 30))
            }
        }
        check(map.rawEntry().fromKey(3) == null)
        check(map.rawEntry().fromHash(hash3, { k -> k == 3 }) == null)
        check(map.rawEntry().fromKeyHashedNocheck(hash3, 3) == null)
        check(map.len() == 5)
        // Nonexistent key (insert)
        when (val matchVal3 = map.rawEntryMut().fromKey(10)) {
            is Occupied<*, *, *, *> -> error("unreachable")
            is Vacant<*, *, *, *> -> {
                val (view3) = matchVal3
                check(view3.insert(10, 1000) == Pair(RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(1000, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
            }
        }
        check(map.rawEntry().fromKey(10).unwrap() == Pair(10, 1000))
        check(map.len() == 6)
        // Ensure all lookup methods produce equivalent results.
        for (k in 0 until 12) {
            val hash = computeHash(map, k)
            val v1 = map.get(k)
            val kv = v1?.let({ v2 -> Pair(k, v2) })
            check(map.rawEntry().fromKey(k) == kv)
            check(map.rawEntry().fromHash(hash, { q -> q == k }) == kv)
            check(map.rawEntry().fromKeyHashedNocheck(hash, k) == kv)
            when (val matchVal4 = map.rawEntryMut().fromKey(k)) {
                is Occupied<*, *, *, *> -> {
                    val (o) = matchVal4
                    check(o.getKeyValue() == kv)
                }
                is Vacant<*, *, *, *> -> check(v1 == null)
            }
            when (val matchVal5 = map.rawEntryMut().fromKeyHashedNocheck(hash, k)) {
                is Occupied<*, *, *, *> -> {
                    val (o1) = matchVal5
                    check(o1.getKeyValue() == kv)
                }
                is Vacant<*, *, *, *> -> check(v1 == null)
            }
            when (val matchVal6 = map.rawEntryMut().fromHash(hash, { q -> q == k })) {
                is Occupied<*, *, *, *> -> {
                    val (o2) = matchVal6
                    check(o2.getKeyValue() == kv)
                }
                is Vacant<*, *, *, *> -> check(v1 == null)
            }
        }
    }

    @kotlin.test.Test
    public fun testKeyWithoutHashImpl() {
        open class IntWrapper(
            public var value: ULong,
        ) {
            public var field0: ULong
                get() = value
                set(v) {
                    value = v
                }
        }
        val m: MutableMap<IntWrapper, Unit>  = HashMap.default()
        run {
            check(m.rawEntry().fromHash(0, { k -> k.first == 0 }) == null)
        }
        run {
            val vacantEntry =
                when (val matchVal = m.rawEntryMut().fromHash(0, { k -> k.first == 0 })) {
                    is RawEntryMut.Occupied<*, *, *, *> -> error("Found entry for key 0")
                    is RawEntryMut.Vacant<*, *, *, *> -> {
                        val (e) = matchVal
                        e
                    }
                }
            vacantEntry.insertWithHasher(0, IntWrapper(0), Unit, { k -> k.first })
        }
        run {
            check(m.rawEntry().fromHash(0, { k -> k.first == 0 }) != null)
            check(m.rawEntry().fromHash(1, { k -> k.first == 1 }) == null)
            check(m.rawEntry().fromHash(2, { k -> k.first == 2 }) == null)
        }
        run {
            val vacantEntry1 =
                when (val matchVal1 = m.rawEntryMut().fromHash(1, { k -> k.first == 1 })) {
                    is RawEntryMut.Occupied<*, *, *, *> -> error("Found entry for key 1")
                    is RawEntryMut.Vacant<*, *, *, *> -> {
                        val (e1) = matchVal1
                        e1
                    }
                }
            vacantEntry1.insertWithHasher(1, IntWrapper(1), Unit, { k -> k.first })
        }
        run {
            check(m.rawEntry().fromHash(0, { k -> k.first == 0 }) != null)
            check(m.rawEntry().fromHash(1, { k -> k.first == 1 }) != null)
            check(m.rawEntry().fromHash(2, { k -> k.first == 2 }) == null)
        }
        run {
            val occupiedEntry =
                when (val matchVal2 = m.rawEntryMut().fromHash(0, { k -> k.first == 0 })) {
                    is RawEntryMut.Occupied<*, *, *, *> -> {
                        val (e2) = matchVal2
                        e2
                    }
                    is RawEntryMut.Vacant<*, *, *, *> -> error("Couldn't find entry for key 0")
                }
            occupiedEntry.remove()
        }
        check(m.rawEntry().fromHash(0, { k -> k.first == 0 }) == null)
        check(m.rawEntry().fromHash(1, { k -> k.first == 1 }) != null)
        check(m.rawEntry().fromHash(2, { k -> k.first == 2 }) == null)
    }
}

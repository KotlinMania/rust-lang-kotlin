// port-lint: tests external_trait_impls/rayon/map.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map

import io.github.kotlinmania.rustlang.HashMap
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// Rayon extensions for `HashMap`.

public open class Droppable(
    public var k: Int,
    public var counter: AtomicUsize,
) {
    public open fun drop() {
        this.counter.fetchSub(1, Ordering.Relaxed)
    }

    public open fun clone(): Droppable = Droppable.new(this.k, this.counter)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.k).hashCode()
        return state
    }

    public open fun eq(other: Droppable): Boolean = this.k == other.k

    public companion object {
        public fun new(k: Int, counter: AtomicUsize): Droppable {
            counter.fetchAdd(1, Ordering.Relaxed)
            return Droppable(k = k, counter = counter)
        }
    }
}

public class MapTestParMapInlineTests {
    @kotlin.test.Test
    public fun testIntoIterDrops() {
        val key = AtomicUsize.new(0)
        val value = AtomicUsize.new(0)
        val hm1 = run {
            val hm = mutableMapOf()
            check(key.load(Ordering.Relaxed) == 0)
            check(value.load(Ordering.Relaxed) == 0)
            for (i in 0 until 100) {
                val d1 = Droppable.new(i, key)
                val d2 = Droppable.new(i + 100, value)
                hm.put(d1, d2)
            }
            check(key.load(Ordering.Relaxed) == 100)
            check(value.load(Ordering.Relaxed) == 100)
            hm
        }
        // By the way, ensure that cloning doesn't screw up the dropping.
        (hm1.clone()).let { }
        check(key.load(Ordering.Relaxed) == 100)
        check(value.load(Ordering.Relaxed) == 100)
        // Ensure that dropping the iterator does not leak anything.
        (hm1.clone().intoParIter()).let { }
        run {
            check(key.load(Ordering.Relaxed) == 100)
            check(value.load(Ordering.Relaxed) == 100)
            // retain only half
            check(key.load(Ordering.Relaxed) == 50)
            check(value.load(Ordering.Relaxed) == 50)
        }
        check(key.load(Ordering.Relaxed) == 0)
        check(value.load(Ordering.Relaxed) == 0)
    }

    @kotlin.test.Test
    public fun testDrainDrops() {
        val key = AtomicUsize.new(0)
        val value = AtomicUsize.new(0)
        var hm1 = run {
            val hm = mutableMapOf()
            check(key.load(Ordering.Relaxed) == 0)
            check(value.load(Ordering.Relaxed) == 0)
            for (i in 0 until 100) {
                val d1 = Droppable.new(i, key)
                val d2 = Droppable.new(i + 100, value)
                hm.put(d1, d2)
            }
            check(key.load(Ordering.Relaxed) == 100)
            check(value.load(Ordering.Relaxed) == 100)
            hm
        }
        // By the way, ensure that cloning doesn't screw up the dropping.
        (hm1.clone()).let { }
        check(key.load(Ordering.Relaxed) == 100)
        check(value.load(Ordering.Relaxed) == 100)
        // Ensure that dropping the drain iterator does not leak anything.
        (hm1.clone().parDrain()).let { }
        run {
            check(key.load(Ordering.Relaxed) == 100)
            check(value.load(Ordering.Relaxed) == 100)
            // retain only half
            check(hm1.isEmpty())
            check(key.load(Ordering.Relaxed) == 50)
            check(value.load(Ordering.Relaxed) == 50)
        }
        check(key.load(Ordering.Relaxed) == 0)
        check(value.load(Ordering.Relaxed) == 0)
    }

    @kotlin.test.Test
    public fun testEmptyIter() {
        val m: MutableMap<Int, Boolean>  = mutableMapOf()
        check(m.parDrain().count() == 0)
        check(m.parKeys().count() == 0)
        check(m.parValues().count() == 0)
        check(m.parValuesMut().count() == 0)
        check(m.parIter().count() == 0)
        check(m.parIterMut().count() == 0)
        check(m.size == 0)
        check(m.isEmpty())
        check(m.intoParIter().count() == 0)
    }

    @kotlin.test.Test
    public fun testIterate() {
        val m = mutableMapOf()
        for (i in 0 until 32) {
            check(m.put(i, i * 2) == null)
        }
        check(m.size == 32)
        val observed = AtomicUsize.new(0)
        m.parIter().forEach({ (k, v) ->
            check(v == k * 2)
            observed.fetchOr(1 shl k.toInt(), Ordering.Relaxed)
        })
        check(observed.intoInner() == 0xFFFFFFFF)
    }

    @kotlin.test.Test
    public fun testKeys() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = vec.intoParIter().collect()
        val keys: Vec = map.parKeys().cloned().collect()
        check(keys.len() == 3)
        check(keys.contains(1))
        check(keys.contains(2))
        check(keys.contains(3))
    }

    @kotlin.test.Test
    public fun testValues() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = vec.intoParIter().collect()
        val values: Vec = map.parValues().cloned().collect()
        check(values.len() == 3)
        check(values.contains('a'))
        check(values.contains('b'))
        check(values.contains('c'))
    }

    @kotlin.test.Test
    public fun testValuesMut() {
        val vec = mutableListOf(Pair(1, 1), Pair(2, 2), Pair(3, 3))
        val map: HashMap  = vec.intoParIter().collect()
        map.parValuesMut().forEach({ value -> value *= 2 })
        val values: Vec = map.parValues().cloned().collect()
        check(values.len() == 3)
        check(values.contains(2))
        check(values.contains(4))
        check(values.contains(6))
    }

    @kotlin.test.Test
    public fun testEq() {
        val m1 = mutableMapOf()
        m1.put(1, 2)
        m1.put(2, 3)
        m1.put(3, 4)
        val m2 = mutableMapOf()
        m2.put(1, 2)
        m2.put(2, 3)
        check(!m1.parEq(m2))
        m2.put(3, 4)
        check(m1.parEq(m2))
    }

    @kotlin.test.Test
    public fun testFromIter() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap = xs.parIter().cloned().collect()
        for ((k, v) in xs) {
            check(map.get(k) == v)
        }
    }

    @kotlin.test.Test
    public fun testExtendRef() {
        val a = mutableMapOf()
        a.put(1, "one")
        val b = mutableMapOf()
        b.put(2, "two")
        b.put(3, "three")
        a.parExtend(b)
        check(a.size == 3)
        check(a[1] == "one")
        check(a[2] == "two")
        check(a[3] == "three")
    }
}

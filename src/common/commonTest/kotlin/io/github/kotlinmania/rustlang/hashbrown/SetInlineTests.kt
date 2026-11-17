// port-lint: tests set.rs
package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Global
import io.github.kotlinmania.rustlang.hashbrown.hasher.DefaultHashBuilder
import io.github.kotlinmania.rustlang.std.hash.random.DefaultHasher

public typealias HS = HashSet<Int, DefaultHashBuilder, Global>

public typealias Hasher = DefaultHasher

public class SetTestSetInlineTests {
    @kotlin.test.Test
    public fun testZeroCapacities() {
        val s = HashSet.new()
        check(s.capacity() == 0)
        val s1 = HashSet.default()
        check(s1.capacity() == 0)
        val s2 = HashSet.withHasher(DefaultHashBuilder.default())
        check(s2.capacity() == 0)
        val s3 = HashSet.withCapacity(0)
        check(s3.capacity() == 0)
        val s4 = HashSet.withCapacityAndHasher(0, DefaultHashBuilder.default())
        check(s4.capacity() == 0)
        val s5 = HashSet.new()
        s5.insert(1)
        s5.insert(2)
        s5.remove(1)
        s5.remove(2)
        s5.shrinkToFit()
        check(s5.capacity() == 0)
        val s6 = HashSet.new()
        s6.reserve(0)
        check(s6.capacity() == 0)
    }

    @kotlin.test.Test
    public fun testDisjoint() {
        val xs = HashSet.new()
        val ys = HashSet.new()
        check(xs.isDisjoint(ys))
        check(ys.isDisjoint(xs))
        check(xs.insert(5))
        check(ys.insert(11))
        check(xs.isDisjoint(ys))
        check(ys.isDisjoint(xs))
        check(xs.insert(7))
        check(xs.insert(19))
        check(xs.insert(4))
        check(ys.insert(2))
        check(ys.insert(-11))
        check(xs.isDisjoint(ys))
        check(ys.isDisjoint(xs))
        check(ys.insert(7))
        check(!xs.isDisjoint(ys))
        check(!ys.isDisjoint(xs))
    }

    @kotlin.test.Test
    public fun testSubsetAndSuperset() {
        val a = HashSet.new()
        check(a.insert(0))
        check(a.insert(5))
        check(a.insert(11))
        check(a.insert(7))
        val b = HashSet.new()
        check(b.insert(0))
        check(b.insert(7))
        check(b.insert(19))
        check(b.insert(250))
        check(b.insert(11))
        check(b.insert(200))
        check(!a.isSubset(b))
        check(!a.isSuperset(b))
        check(!b.isSubset(a))
        check(!b.isSuperset(a))
        check(b.insert(5))
        check(a.isSubset(b))
        check(!a.isSuperset(b))
        check(!b.isSubset(a))
        check(b.isSuperset(a))
    }

    @kotlin.test.Test
    public fun testIterate() {
        val a = HashSet.new()
        for (i in 0 until 32) {
            check(a.insert(i))
        }
        var observed: UInt = 0u
        for (k in a) {
            observed = observed or (1u shl k.toInt())
        }
        check(observed == 0xFFFFFFFFu)
    }

    @kotlin.test.Test
    public fun testIntersection() {
        val a = HashSet.new()
        val b = HashSet.new()
        check(a.insert(11))
        check(a.insert(1))
        check(a.insert(3))
        check(a.insert(77))
        check(a.insert(103))
        check(a.insert(5))
        check(a.insert(-5))
        check(b.insert(2))
        check(b.insert(11))
        check(b.insert(77))
        check(b.insert(-9))
        check(b.insert(-42))
        check(b.insert(5))
        check(b.insert(3))
        var i = 0
        val expected = listOf(3, 5, 11, 77)
        for (x in a.intersection(b)) {
            check(expected.contains(x))
            i += 1
        }
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testDifference() {
        val a = HashSet.new()
        val b = HashSet.new()
        check(a.insert(1))
        check(a.insert(3))
        check(a.insert(5))
        check(a.insert(9))
        check(a.insert(11))
        check(b.insert(3))
        check(b.insert(9))
        var i = 0
        val expected = listOf(1, 5, 11)
        for (x in a.difference(b)) {
            check(expected.contains(x))
            i += 1
        }
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testSymmetricDifference() {
        val a = HashSet.new()
        val b = HashSet.new()
        check(a.insert(1))
        check(a.insert(3))
        check(a.insert(5))
        check(a.insert(9))
        check(a.insert(11))
        check(b.insert(-2))
        check(b.insert(3))
        check(b.insert(9))
        check(b.insert(14))
        check(b.insert(22))
        var i = 0
        val expected = listOf(-2, 1, 5, 11, 14, 22)
        for (x in a.symmetricDifference(b)) {
            check(expected.contains(x))
            i += 1
        }
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testSubAssign() {
        var a: HashSet = mutableListOf(1, 2, 3, 4, 5).intoIter().collect()
        val b: HashSet = mutableListOf(4, 5, 6).intoIter().collect()
        a -= b
        var i = 0
        val expected = listOf(1, 2, 3)
        for (x in a) {
            check(expected.contains(x))
            i += 1
        }
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testUnion() {
        val a = HashSet.new()
        val b = HashSet.new()
        check(a.insert(1))
        check(a.insert(3))
        check(a.insert(5))
        check(a.insert(9))
        check(a.insert(11))
        check(a.insert(16))
        check(a.insert(19))
        check(a.insert(24))
        check(b.insert(-2))
        check(b.insert(1))
        check(b.insert(5))
        check(b.insert(9))
        check(b.insert(13))
        check(b.insert(19))
        var i = 0
        val expected = listOf(-2, 1, 3, 5, 9, 11, 13, 16, 19, 24)
        for (x in a.union(b)) {
            check(expected.contains(x))
            i += 1
        }
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testFromMap() {
        val a = mutableMapOf()
        a.put(1, Unit)
        a.put(2, Unit)
        a.put(3, Unit)
        a.put(4, Unit)
        val a1: HashSet = a.into()
        check(a1.len() == 4)
        check(a1.contains(1))
        check(a1.contains(2))
        check(a1.contains(3))
        check(a1.contains(4))
    }

    @kotlin.test.Test
    public fun testFromIter() {
        val xs = listOf(1, 2, 2, 3, 4, 5, 6, 7, 8, 9)
        val set: HashSet = xs.iter().copied().collect()
        for (x in xs) {
            check(set.contains(x))
        }
        check(set.iter().len() == xs.len() - 1)
    }

    @kotlin.test.Test
    public fun testMoveIter() {
        val hs1 = run {
            val hs = HashSet.new()
            hs.insert('a')
            hs.insert('b')
            hs
        }
        val v = hs1.intoIter().toMutableList()
        check(v == listOf('a', 'b') || v == listOf('b', 'a'))
    }

    @kotlin.test.Test
    public fun testEq() {
        // These constants once happened to expose a bug in insert().
        // I'm keeping them around to prevent a regression.
        val s1 = HashSet.new()
        s1.insert(1)
        s1.insert(2)
        s1.insert(3)
        val s2 = HashSet.new()
        s2.insert(1)
        s2.insert(2)
        check(s1 != s2)
        s2.insert(3)
        check(s1 == s2)
    }

    @kotlin.test.Test
    public fun testShow() {
        val set = HashSet.new()
        val empty = HashSet.new()
        set.insert(1)
        set.insert(2)
        val setStr = "$set"
        check(setStr == "{1, 2}" || setStr == "{2, 1}")
        check("$empty" == "{}")
    }

    @kotlin.test.Test
    public fun testTrivialDrain() {
        val s = HashSet.new()
        for (unused in s.drain()) {}
        check(s.isEmpty())
        (s).let { }
        val s1 = HashSet.new()
        (s1.drain()).let { }
        check(s1.isEmpty())
    }

    @kotlin.test.Test
    public fun testDrain() {
        val s: HashSet  = (1 until 100).collect()
        // try this a bunch of times to make sure we don't screw up internal state.
        for (unused in 0 until 20) {
            check(s.len() == 99)
            run {
                var lastI = 0
                val d = s.drain()
                for ((i, x) in d.byRef().take(50).mapIndexed { index, item -> Pair(index, item) }) {
                    lastI = i
                    check(x != 0)
                }
                check(lastI == 49)
            }
            if (!s.isEmpty()) {
                error("s should be empty!")
            }
            // reset to try again.
            s.extend(1 until 100)
        }
    }

    @kotlin.test.Test
    public fun testReplace() {
        open class Foo(
            public var field0: String,
            public var field1: Int,
        ) {
            public open fun eq(other: Foo): Boolean = this.field0 == other.field0

            public override fun hashCode(): Int {
                var h = 0
                h = 31 * h + (this.field0).hashCode()
                return h
            }
        }
        val s = HashSet.new()
        check(s.replace(Foo("a", 1)) == null)
        check(s.len() == 1)
        check(s.replace(Foo("a", 2)) == Foo("a", 1))
        check(s.len() == 1)
        val it = s.iter()
        check(it.next() == Foo("a", 2))
        check(it.next() == null)
    }

    @kotlin.test.Test
    public fun testExtendRef() {
        val a = HashSet.new()
        a.insert(1)
        a.extend(listOf(2, 3, 4))
        check(a.len() == 4)
        check(a.contains(1))
        check(a.contains(2))
        check(a.contains(3))
        check(a.contains(4))
        val b = HashSet.new()
        b.insert(5)
        b.insert(6)
        a.extend(b)
        check(a.len() == 6)
        check(a.contains(1))
        check(a.contains(2))
        check(a.contains(3))
        check(a.contains(4))
        check(a.contains(5))
        check(a.contains(6))
    }

    @kotlin.test.Test
    public fun testRetain() {
        val xs = listOf(1, 2, 3, 4, 5, 6)
        val set: HashSet<Int, DefaultHashBuilder, Global>  = xs.iter().copied().toMutableSet()
        set.retain({ k -> k % 2 == 0 })
        check(set.len() == 3)
        check(set.contains(2))
        check(set.contains(4))
        check(set.contains(6))
    }

    @kotlin.test.Test
    public fun testExtractIf() {
        run {
            val set: HashSet<Int, DefaultHashBuilder, Global>  = (0 until 8).toMutableSet()
            val drained = set.extractIf({ k -> k % 2 == 0 })
            val out = drained.collect<Vec>()
            out.sortUnstable()
            check(mutableListOf(0, 2, 4, 6) == out)
            check(set.len() == 4)
        }
        run {
            val set1: HashSet<Int, DefaultHashBuilder, Global>  = (0 until 8).toMutableSet()
            set1.extractIf({ k -> k % 2 == 0 }).forEach({ drop(it) })
            check(set1.len() == 4)
        }
    }

    @kotlin.test.Test
    public fun testConstWithHasher() {
        open class MyHasher : BuildHasher {
            public open fun clone(): MyHasher = MyHasher()

            public open fun buildHasher(): DefaultHasher = DefaultHasher.new()
        }
        val EMPTY_SET: HashSet<UInt, MyHasher, Global> = HashSet.withHasher(MyHasher())
        val set = EMPTY_SET
        set.insert(19)
        check(set.contains(19))
    }

    @kotlin.test.Test
    public fun rehashInPlace() {
        val set = HashSet.new()
        for (i in 0 until 224) {
            set.insert(i)
        }
        check(set.capacity() == 224)
        for (i1 in 100 until 1400) {
            set.remove((i1 - 100))
            set.insert(i1)
        }
    }

    @kotlin.test.Test
    public fun collect() {
        // At the time of writing, this hits the ZST case in fromBaseIndex
        // (and without the `map`, it does not).
    }

    @kotlin.test.Test
    public fun testAllocationInfo() {
        check(HashSet.new().allocationSize() == 0)
        check(HashSet.new().allocationSize() == 0)
        check(HashSet.withCapacity(1).allocationSize() > sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32))
    }

    @kotlin.test.Test
    public fun duplicateInsert() {
        val set = HashSet.new()
        set.insert(1)
        set.getOrInsertWith(1, { 1 })
        set.getOrInsertWith(1, { 1 })
        check(listOf(1).iter().eq(set.iter()))
    }

    @kotlin.test.Test
    public fun someInvalidEquivalent() {
        kotlin.test.assertFails(block = fun() {
            open class Invalid(
                public var count: UInt,
                public var other: UInt,
            ) {
                public open fun eq(other: Invalid): Boolean = this.count == other.count && this.other == other.other

                public override fun hashCode(): Int {
                    var state = 0
                    state = 31 * state + (this.count).hashCode()
                    return state
                }
            }
            open class InvalidRef(
                public var count: UInt,
                public var other: UInt,
            ) : Equivalent<Invalid> {
                public override fun equivalent(key: Invalid): Boolean = this.count == key.count && this.other == key.other

                public override fun hashCode(): Int {
                    var state = 0
                    state = 31 * state + (this.count).hashCode()
                    return state
                }
            }
            val set: HashSet<Invalid, DefaultHashBuilder, Global>  = HashSet.new()
            val key = InvalidRef(count = 1u, other = 1u)
            val value = Invalid(count = 1u, other = 2u)
            if (makeHash(set.hasher(), key) == makeHash(set.hasher(), value)) {
                set.getOrInsertWith(key, { value })
            }
        })
    }
}

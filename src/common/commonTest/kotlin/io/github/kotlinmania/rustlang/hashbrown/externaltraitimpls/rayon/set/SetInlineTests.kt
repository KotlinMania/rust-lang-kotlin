// port-lint: tests external_trait_impls/rayon/set.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.set

import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.fetchOr
import io.github.kotlinmania.rustlang.core.sync.atomic.intoInner
import io.github.kotlinmania.rustlang.hashbrown.HashSet

// Rayon extensions for `HashSet`.

public class SetTestParSetInlineTests {
    @kotlin.test.Test
    public fun testDisjoint() {
        val xs = mutableSetOf()
        val ys = mutableSetOf()
        check(xs.parIsDisjoint(ys))
        check(ys.parIsDisjoint(xs))
        check(xs.add(5))
        check(ys.add(11))
        check(xs.parIsDisjoint(ys))
        check(ys.parIsDisjoint(xs))
        check(xs.add(7))
        check(xs.add(19))
        check(xs.add(4))
        check(ys.add(2))
        check(ys.add(-11))
        check(xs.parIsDisjoint(ys))
        check(ys.parIsDisjoint(xs))
        check(ys.add(7))
        check(!xs.parIsDisjoint(ys))
        check(!ys.parIsDisjoint(xs))
    }

    @kotlin.test.Test
    public fun testSubsetAndSuperset() {
        val a = mutableSetOf()
        check(a.add(0))
        check(a.add(5))
        check(a.add(11))
        check(a.add(7))
        val b = mutableSetOf()
        check(b.add(0))
        check(b.add(7))
        check(b.add(19))
        check(b.add(250))
        check(b.add(11))
        check(b.add(200))
        check(!a.parIsSubset(b))
        check(!a.parIsSuperset(b))
        check(!b.parIsSubset(a))
        check(!b.parIsSuperset(a))
        check(b.add(5))
        check(a.parIsSubset(b))
        check(!a.parIsSuperset(b))
        check(!b.parIsSubset(a))
        check(b.parIsSuperset(a))
    }

    @kotlin.test.Test
    public fun testIterate() {
        val a = mutableSetOf()
        for (i in 0 until 32) {
            check(a.add(i))
        }
        val observed = AtomicUsize.new(0)
        a.parIter().forEach({ k ->
            observed.fetchOr(1 shl k.toInt(), Ordering.Relaxed)
        })
        check(observed.intoInner() == 0xFFFFFFFF)
    }

    @kotlin.test.Test
    public fun testIntersection() {
        val a = mutableSetOf()
        val b = mutableSetOf()
        check(a.add(11))
        check(a.add(1))
        check(a.add(3))
        check(a.add(77))
        check(a.add(103))
        check(a.add(5))
        check(a.add(-5))
        check(b.add(2))
        check(b.add(11))
        check(b.add(77))
        check(b.add(-9))
        check(b.add(-42))
        check(b.add(5))
        check(b.add(3))
        val expected = listOf(3, 5, 11, 77)
        val i =
            a.parIntersection(b).map({ x ->
                check(expected.contains(x))
                1
            }).sum<Int>()
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testDifference() {
        val a = mutableSetOf()
        val b = mutableSetOf()
        check(a.add(1))
        check(a.add(3))
        check(a.add(5))
        check(a.add(9))
        check(a.add(11))
        check(b.add(3))
        check(b.add(9))
        val expected = listOf(1, 5, 11)
        val i =
            a.parDifference(b).map({ x ->
                check(expected.contains(x))
                1
            }).sum<Int>()
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testSymmetricDifference() {
        val a = mutableSetOf()
        val b = mutableSetOf()
        check(a.add(1))
        check(a.add(3))
        check(a.add(5))
        check(a.add(9))
        check(a.add(11))
        check(b.add(-2))
        check(b.add(3))
        check(b.add(9))
        check(b.add(14))
        check(b.add(22))
        val expected = listOf(-2, 1, 5, 11, 14, 22)
        val i =
            a.parSymmetricDifference(b).map({ x ->
                check(expected.contains(x))
                1
            }).sum<Int>()
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testUnion() {
        val a = mutableSetOf()
        val b = mutableSetOf()
        check(a.add(1))
        check(a.add(3))
        check(a.add(5))
        check(a.add(9))
        check(a.add(11))
        check(a.add(16))
        check(a.add(19))
        check(a.add(24))
        check(b.add(-2))
        check(b.add(1))
        check(b.add(5))
        check(b.add(9))
        check(b.add(13))
        check(b.add(19))
        val expected = listOf(-2, 1, 3, 5, 9, 11, 13, 16, 19, 24)
        val i =
            a.parUnion(b).map({ x ->
                check(expected.contains(x))
                1
            }).sum<Int>()
        check(i == expected.len())
    }

    @kotlin.test.Test
    public fun testFromIter() {
        val xs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val set: HashSet = xs.parIter().cloned().collect()
        for (x in xs) {
            check(set.contains(x))
        }
    }

    @kotlin.test.Test
    public fun testMoveIter() {
        val hs1 = run {
            val hs = mutableSetOf()
            hs.add('a')
            hs.add('b')
            hs
        }
        val v = hs1.intoParIter().toMutableList()
        check(v == listOf('a', 'b') || v == listOf('b', 'a'))
    }

    @kotlin.test.Test
    public fun testEq() {
        // These constants once happened to expose a bug in insert().
        // I'm keeping them around to prevent a regression.
        val s1 = mutableSetOf()
        s1.add(1)
        s1.add(2)
        s1.add(3)
        val s2 = mutableSetOf()
        s2.add(1)
        s2.add(2)
        check(!s1.parEq(s2))
        s2.add(3)
        check(s1.parEq(s2))
    }

    @kotlin.test.Test
    public fun testExtendRef() {
        val a = mutableSetOf()
        a.add(1)
        a.parExtend(listOf(2, 3, 4))
        check(a.size == 4)
        check(a.contains(1))
        check(a.contains(2))
        check(a.contains(3))
        check(a.contains(4))
        val b = mutableSetOf()
        b.add(5)
        b.add(6)
        a.parExtend(b)
        check(a.size == 6)
        check(a.contains(1))
        check(a.contains(2))
        check(a.contains(3))
        check(a.contains(4))
        check(a.contains(5))
        check(a.contains(6))
    }
}

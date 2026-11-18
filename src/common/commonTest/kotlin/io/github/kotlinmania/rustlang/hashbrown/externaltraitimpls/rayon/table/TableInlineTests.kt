// port-lint: tests external_trait_impls/rayon/table.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.table

import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.fetchOr
import io.github.kotlinmania.rustlang.core.sync.atomic.intoInner
import io.github.kotlinmania.rustlang.hashbrown.HashTable
import io.github.kotlinmania.rustlang.hashbrown.hasher.DefaultHashBuilder
import io.github.kotlinmania.rustlang.hashbrown.makeHash

// Rayon extensions for `HashTable`.

public class TableTestParTableInlineTests {
    @kotlin.test.Test
    public fun testIterate() {
        val hasher = DefaultHashBuilder.default()
        val a = HashTable.new()
        for (i in 0 until 32) {
            a.insertUnique(makeHash(hasher, i), i, { x -> makeHash(hasher, x) })
        }
        val observed = AtomicUsize.new(0)
        a.parIter().forEach({ k ->
            observed.fetchOr(1 shl k.toInt(), Ordering.Relaxed)
        })
        check(observed.intoInner() == 0xFFFFFFFF.toInt())
    }

    @kotlin.test.Test
    public fun testMoveIter() {
        val hasher = DefaultHashBuilder.default()
        val hs1 = run {
            val hs = HashTable.new()
            hs.insertUnique(makeHash(hasher, 'a'), 'a', { x -> makeHash(hasher, x) })
            hs.insertUnique(makeHash(hasher, 'b'), 'b', { x -> makeHash(hasher, x) })
            hs
        }
        val v = hs1.intoParIter().toMutableList()
        check(v == listOf('a', 'b') || v == listOf('b', 'a'))
    }
}

// port-lint: tests dlmalloc.rs
package io.github.kotlinmania.rustlang.dlmalloc.dlmalloc

import io.github.kotlinmania.rustlang.Allocator
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.System

// Prime the allocator with some allocations such that there will be free
// chunks in the treemap
public fun <A : Allocator> setupTreemap(a: Dlmalloc<A>) {
    val largeRequestSize = NSMALLBINS * (1 shl SMALLBIN_SHIFT)
    check(!a.isSmall(largeRequestSize))
    val largeRequest1 = a.malloc(largeRequestSize)
    check(largeRequest1 != MutPtr(0L))
    val largeRequest2 = a.malloc(largeRequestSize)
    check(largeRequest2 != MutPtr(0L))
    a.free(largeRequest1)
    check(a.treemap != 0u)
}

public class DlmallocInlineTests {
    // Test allocating, with a non-empty treemap, a specific size that used to
    // trigger an integer overflow bug
    @kotlin.test.Test
    public fun treemapAllocOverflowMinimal() {
        val a = Dlmalloc.new(System.new())
        run {
            setupTreemap(a)
            val minIdx31Size = (0xc000 shl TREEBIN_SHIFT) - a.chunkOverhead() + 1
            check(a.malloc(minIdx31Size) != MutPtr(0L))
        }
    }

    // Test allocating the maximum request size with a non-empty treemap
    @kotlin.test.Test
    public fun treemapAllocMax() {
        val a = Dlmalloc.new(System.new())
        run {
            setupTreemap(a)
            val maxRequestSize = a.maxRequest() - 1
            check(a.malloc(maxRequestSize) == MutPtr(0L))
        }
    }
}

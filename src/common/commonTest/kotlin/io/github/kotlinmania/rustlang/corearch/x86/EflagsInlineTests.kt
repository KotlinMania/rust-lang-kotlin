// port-lint: tests x86/eflags.rs
package io.github.kotlinmania.rustlang.corearch.x86

// `i386` intrinsics

public class EflagsInlineTests {
    // Uses inline assembly
    @kotlin.test.Test
    public fun testReadeflags() {
        run {
            // reads eflags, writes them back, reads them again,
            // and compare for equality:
            val v = readeflags()
            writeeflags(v)
            val u = readeflags()
            check(v == u)
        }
    }
}

// port-lint: tests x86/rdtsc.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// RDTSC instructions.

public class RdtscInlineTests {
    @kotlin.test.Test
    public fun testRdtsc() {
        val r = rdtsc()
        check(r != 0uL)
        // The chances of this being 0 are infinitesimal
    }

    @kotlin.test.Test
    public fun testRdtscp() {
        val localStorageL75C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val r = rdtscp(RustMutableReference(localStorageL75C8.asMutPtr(), localStorageL75C8.layout))
        check(r != 0uL)
        // The chances of this being 0 are infinitesimal
    }
}

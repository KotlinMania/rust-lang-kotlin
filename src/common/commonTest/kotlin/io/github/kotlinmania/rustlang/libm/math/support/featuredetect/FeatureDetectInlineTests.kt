// port-lint: tests math/support/feature_detect.rs
package io.github.kotlinmania.rustlang.libm.math.support.featuredetect

import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.libm.math.j1.V0
import io.github.kotlinmania.rustlang.libm.math.lgammar.V1
import io.github.kotlinmania.rustlang.libm.math.lgammar.V2

// Helpers for runtime target feature detection that are shared across architectures.

public class FeatureDetectInlineTests {
    @kotlin.test.Test
    public fun uniqueMasks() {
        uniqueMasks(u32, V0, V1, V2)
        check(V0 == 1u shl 0)
        check(V1 == 1u shl 1)
        check(V2 == 1u shl 2)
        check(ALL == doubleArrayOf(V0, V1, V2))
        check(NAMES == listOf("V0", "V1", "V2"))
    }

    @kotlin.test.Test
    public fun flagCacheIsUsed() {
        // Sanity check that flags are only ever set once
        val CACHE: AtomicU32  = AtomicU32.new(0)
        val f1 = Flags.fromBits(0x1u)
        val f2 = Flags.fromBits(0x2u)
        val r1 = getOrInitFlagsCache(CACHE, { f1 })
        val r2 = getOrInitFlagsCache(CACHE, { f2 })
        f1.insert(1 shl 31)
        // init bit
        check(r1 == f1)
        check(r2 == f1)
    }

    @kotlin.test.Test
    public fun selectCacheIsUsed() {
        // Sanity check that cache is used
        val CALLED: AtomicU32  = AtomicU32.new(0)
        fun inner() {
            fun nop() {}
            selectOnce(sig, init, CALLED, fetchAdd, 1, Ordering, Relaxed, nop, call, fnPtr, Func, fnPtr)
        }
        // `init` should only have been called once.
        inner()
        check(CALLED.load(Ordering.Relaxed) == 1)
        inner()
        check(CALLED.load(Ordering.Relaxed) == 1)
    }
}

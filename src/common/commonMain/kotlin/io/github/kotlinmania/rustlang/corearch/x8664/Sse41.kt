// port-lint: source x86_64/sse41.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128i

// `i686`s Streaming SIMD Extensions 4.1 (SSE4.1)

/**
 * Extracts an 64-bit integer from `a` selected with `IMM1`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_extract_epi64)
 */
public fun mmExtractEpi64(a: M128i, imm1: Int): Long {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a.asI64x2(); val _simdArg1 = imm1.toUInt(); _simdArg0 }
}

/**
 * Returns a copy of `a` with the 64-bit integer from `i` inserted at a
 * location specified by `IMM1`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_insert_epi64)
 */
public fun mmInsertEpi64(a: M128i, i: Long, imm1: Int): M128i {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI64x2(); val _simdArg1 = imm1.toUInt(); val _simdArg2 = i; _simdArg0 })
}

// port-lint: source x86_64/avx.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.loongarch64.lasx.M256i
import io.github.kotlinmania.rustlang.corearch.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simdExtract
import io.github.kotlinmania.rustlang.corearch.simdInsert

// Advanced Vector Extensions (AVX)
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref]. - [AMD64 Architecture
//   Programmer's Manual, Volume 3: General-Purpose and System
//   Instructions][amd64Ref].
//
// [Wikipedia][wiki] provides a quick overview of the instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wiki]: https://en.wikipedia.org/wiki/AdvancedVectorExtensions

// This intrinsic has no corresponding instruction.

/**
 * Copies `a` to result, and insert the 64-bit integer `i` into result
 * at the location specified by `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insert_epi64)
 */
public fun mm256InsertEpi64(a: M256i, i: Long, index: Int): M256i {
    run {
        check(0 <= index && index < (1 shl 2)) { "INDEX doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(simdInsert(a.asI64x4(), index.toUInt(), i))
}

// This intrinsic has no corresponding instruction.

/**
 * Extracts a 64-bit integer from `a`, selected with `INDEX`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extract_epi64)
 */
public fun mm256ExtractEpi64(a: M256i, index: Int): Long {
    run {
        check(0 <= index && index < (1 shl 2)) { "INDEX doesn't fit in 2 bits (unsigned)" }
    }
    return simdExtract(a.asI64x4(), index.toUInt())
}

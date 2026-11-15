// port-lint: tests x86_64/avx.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.x86.mm256SetrEpi64x

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

public fun testMm256InsertEpi64() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256InsertEpi64(a, 0L, 3)
    val e = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256ExtractEpi64() {
    val a = mm256SetrEpi64x(0L, 1L, 2L, 3L)
    val r = mm256ExtractEpi64(a, 3)
    check(r == 3L)
}

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4

public actual fun sha1msg1(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun sha1msg2(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun sha1nexte(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun sha1rnds4(a: I32x4, b: I32x4, c: Byte): I32x4 =
    null as I32x4

public actual fun sha256msg1(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun sha256msg2(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun sha256rnds2(a: I32x4, b: I32x4, k: I32x4): I32x4 =
    null as I32x4

public actual fun vsha512msg1(a: I64x4, b: I64x2): I64x4 =
    null as I64x4

public actual fun vsha512msg2(a: I64x4, b: I64x4): I64x4 =
    null as I64x4

public actual fun vsha512rnds2(a: I64x4, b: I64x4, k: I64x2): I64x4 =
    null as I64x4

public actual fun vsm3msg1(a: I32x4, b: I32x4, c: I32x4): I32x4 =
    null as I32x4

public actual fun vsm3msg2(a: I32x4, b: I32x4, c: I32x4): I32x4 =
    null as I32x4

public actual fun vsm3rnds2(a: I32x4, b: I32x4, c: I32x4, d: Int): I32x4 =
    null as I32x4

public actual fun vsm4key4128(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vsm4key4256(a: I32x8, b: I32x8): I32x8 =
    null as I32x8

public actual fun vsm4rnds4128(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vsm4rnds4256(a: I32x8, b: I32x8): I32x8 =
    null as I32x8

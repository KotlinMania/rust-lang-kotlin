// port-lint: tests aarch64/mte.rs
package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// AArch64 Memory tagging intrinsics
//
// [ACLE documentation](https://arm-software.github.io/acle/main/acle.html#markdown-toc-mte-intrinsics)

// Instruction tests are separate because the functions use generics.
//
// Note: As of 2026 MSVC  `dumpbin` doesn't support MTE.
public fun testArmMteCreateRandomTag(src: ConstPtr, mask: ULong): ConstPtr = armMteCreateRandomTag(src, mask)

public fun testArmMteIncrementTag(src: ConstPtr): ConstPtr = armMteIncrementTag(src, 1)

public fun testArmMteExcludeTag(src: ConstPtr, excluded: ULong): ULong = armMteExcludeTag(src, excluded)

public fun testArmMteSetTag(src: ConstPtr) {
    armMteSetTag(src)
}

public fun testArmMteGetTag(src: ConstPtr): ConstPtr = armMteGetTag(src)

public fun testArmMtePtrdiff(a: ConstPtr, b: ConstPtr): Long = armMtePtrdiff(a, b)

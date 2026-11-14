// port-lint: source x86/avx512vp2intersect.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8

// Vector Pair Intersection to a Pair of Mask Registers (VP2INTERSECT)

/**
 * Compute intersection of packed 32-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_2intersect_epi32&expand=0)
 */
public fun mm2intersectEpi32(a: M128i, b: M128i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask8>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask8>()).readNative((k2).rawAddress)) = vp2intersectd128(a.asI32x4(), b.asI32x4())
}

/**
 * Compute intersection of packed 64-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_2intersect_epi64&expand=0)
 */
public fun mm2intersectEpi64(a: M128i, b: M128i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask8>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask8>()).readNative((k2).rawAddress)) = vp2intersectq128(a.asI64x2(), b.asI64x2())
}

/**
 * Compute intersection of packed 32-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_2intersect_epi32&expand=0)
 */
public fun mm2562intersectEpi32(a: M256i, b: M256i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask8>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask8>()).readNative((k2).rawAddress)) = vp2intersectd256(a.asI32x8(), b.asI32x8())
}

/**
 * Compute intersection of packed 64-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_2intersect_epi64&expand=0)
 */
public fun mm2562intersectEpi64(a: M256i, b: M256i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask8>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask8>()).readNative((k2).rawAddress)) = vp2intersectq256(a.asI64x4(), b.asI64x4())
}

/**
 * Compute intersection of packed 32-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_2intersect_epi32&expand=0)
 */
public fun mm5122intersectEpi32(a: M512i, b: M512i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask16>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask16>()).readNative((k2).rawAddress)) = vp2intersectd512(a.asI32x16(), b.asI32x16())
}

/**
 * Compute intersection of packed 64-bit integer vectors a and b,
 * and store indication of match in the corresponding bit of two mask registers
 * specified by k1 and k2. A match in corresponding elements of a and b is
 * indicated by a set bit in the corresponding bit of the mask registers.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_2intersect_epi64&expand=0)
 */
public fun mm5122intersectEpi64(a: M512i, b: M512i, k1: MutPtr, k2: MutPtr) {
    Pair((ObjectSlotLayout<Mmask8>()).readNative((k1).rawAddress), (ObjectSlotLayout<Mmask8>()).readNative((k2).rawAddress)) = vp2intersectq512(a.asI64x8(), b.asI64x8())
}

public expect fun vp2intersectd128(a: I32x4, b: I32x4): Pair<UByte, UByte>

public expect fun vp2intersectq128(a: I64x2, b: I64x2): Pair<UByte, UByte>

public expect fun vp2intersectd256(a: I32x8, b: I32x8): Pair<UByte, UByte>

public expect fun vp2intersectq256(a: I64x4, b: I64x4): Pair<UByte, UByte>

public expect fun vp2intersectd512(a: I32x16, b: I32x16): Pair<UShort, UShort>

public expect fun vp2intersectq512(a: I64x8, b: I64x8): Pair<UByte, UByte>

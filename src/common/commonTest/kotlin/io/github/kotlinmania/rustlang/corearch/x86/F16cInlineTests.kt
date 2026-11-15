// port-lint: tests x86/f16c.rs
package io.github.kotlinmania.rustlang.corearch.x86

// [F16C intrinsics].
//
// [F16C intrinsics]: https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=fp16&expand=1769

public val F16_ONE: Short = 0x3c00

public val F16_TWO: Short = 0x4000

public val F16_THREE: Short = 0x4200

public val F16_FOUR: Short = 0x4400

public val F16_FIVE: Short = 0x4500

public val F16_SIX: Short = 0x4600

public val F16_SEVEN: Short = 0x4700

public val F16_EIGHT: Short = 0x4800

public fun testMmCvtphPs() {
    val a = mmSetEpi16(0, 0, 0, 0, F16_ONE, F16_TWO, F16_THREE, F16_FOUR)
    val r = mmCvtphPs(a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256CvtphPs() {
    val a = mmSetEpi16(F16_ONE, F16_TWO, F16_THREE, F16_FOUR, F16_FIVE, F16_SIX, F16_SEVEN, F16_EIGHT)
    val r = mm256CvtphPs(a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmCvtpsPh() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtpsPh<MmFroundCurDirection>(a)
    val e = mmSetEpi16(0, 0, 0, 0, F16_ONE, F16_TWO, F16_THREE, F16_FOUR)
    assertEqM128i(r, e)
}

public fun testMm256CvtpsPh() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256CvtpsPh<MmFroundCurDirection>(a)
    val e = mmSetEpi16(F16_ONE, F16_TWO, F16_THREE, F16_FOUR, F16_FIVE, F16_SIX, F16_SEVEN, F16_EIGHT)
    assertEqM128i(r, e)
}

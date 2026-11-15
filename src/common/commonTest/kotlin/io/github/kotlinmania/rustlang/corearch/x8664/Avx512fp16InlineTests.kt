// port-lint: tests x86_64/avx512fp16.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.x86.MM_FROUND_NO_EXC
import io.github.kotlinmania.rustlang.x86.MM_FROUND_TO_NEAREST_INT
import io.github.kotlinmania.rustlang.x86.mmSetrPh

public fun testMmCvti64Sh() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvti64Sh(a, 10L)
    val e = mmSetrPh(10.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM128h(r, e)
}

public fun testMmCvtRoundi64Sh() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtRoundi64Sh(a, 10L, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(10.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM128h(r, e)
}

public fun testMmCvtu64Sh() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtu64Sh(a, 10uL)
    val e = mmSetrPh(10.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM128h(r, e)
}

public fun testMmCvtRoundu64Sh() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtRoundu64Sh(a, 10uL, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(10.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM128h(r, e)
}

public fun testMmCvtshI64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtshI64(a)
    check(r == 1L)
}

public fun testMmCvtRoundshI64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtRoundshI64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    check(r == 1L)
}

public fun testMmCvtshU64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtshU64(a)
    check(r == 1uL)
}

public fun testMmCvtRoundshU64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvtRoundshU64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    check(r == 1uL)
}

public fun testMmCvttshI64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvttshI64(a)
    check(r == 1L)
}

public fun testMmCvttRoundshI64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvttRoundshI64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    check(r == 1L)
}

public fun testMmCvttshU64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvttshU64(a)
    check(r == 1uL)
}

public fun testMmCvttRoundshU64() {
    val a = mmSetrPh(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmCvttRoundshU64<MmFroundNoExc>(a)
    check(r == 1uL)
}

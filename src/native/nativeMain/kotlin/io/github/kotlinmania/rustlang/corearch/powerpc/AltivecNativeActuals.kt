@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public actual fun lvx(p: ConstPtr): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun lvebx(p: ConstPtr): VectorSignedChar =
    VectorSignedChar()

public actual fun lvehx(p: ConstPtr): VectorSignedShort =
    VectorSignedShort()

public actual fun lvewx(p: ConstPtr): VectorSignedInt =
    VectorSignedInt()

public actual fun lvxl(p: ConstPtr): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun stvx(a: VectorSignedInt, p: ConstPtr) {
}

public actual fun stvebx(a: VectorSignedChar, p: ConstPtr) {
}

public actual fun stvehx(a: VectorSignedShort, p: ConstPtr) {
}

public actual fun stvewx(a: VectorSignedInt, p: ConstPtr) {
}

public actual fun stvxl(a: VectorSignedInt, p: ConstPtr) {
}

public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    VectorSignedInt()

public actual fun vmhaddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmhraddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    VectorSignedShort()

public actual fun vmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    VectorSignedInt()

public actual fun vmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    VectorSignedShort()

public actual fun vmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    VectorSignedInt()

public actual fun vmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt =
    VectorSignedInt()

public actual fun vcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    VectorBoolChar()

public actual fun vcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    VectorBoolShort()

public actual fun vcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    VectorBoolChar()

public actual fun vcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    VectorBoolShort()

public actual fun vcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt =
    VectorBoolInt()

public actual fun vcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    VectorBoolChar()

public actual fun vcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    VectorBoolShort()

public actual fun vcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    VectorBoolChar()

public actual fun vcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    VectorBoolShort()

public actual fun vcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vexptefp(a: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun vcmpequbP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    0

public actual fun vcmpequhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    0

public actual fun vcmpequwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    0

public actual fun vcmpeqfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    0

public actual fun vcmpgtubP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    0

public actual fun vcmpgtuhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    0

public actual fun vcmpgtuwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    0

public actual fun vcmpgtsbP(cr: Int, a: VectorSignedChar, b: VectorSignedChar): Int =
    0

public actual fun vcmpgtshP(cr: Int, a: VectorSignedShort, b: VectorSignedShort): Int =
    0

public actual fun vcmpgtswP(cr: Int, a: VectorSignedInt, b: VectorSignedInt): Int =
    0

public actual fun vcmpgefpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    0

public actual fun vcmpgtfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    0

public actual fun vcmpbfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    0

public actual fun vcfsx(a: VectorSignedInt, b: Int): VectorFloat =
    VectorFloat()

public actual fun vcfux(a: VectorUnsignedInt, b: Int): VectorFloat =
    VectorFloat()

public actual fun vctsxs(a: VectorFloat, b: Int): VectorSignedInt =
    VectorSignedInt()

public actual fun vctuxs(a: VectorFloat, b: Int): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    VectorSignedChar()

public actual fun vpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    VectorSignedShort()

public actual fun vpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vupkhsb(a: VectorSignedChar): VectorSignedShort =
    VectorSignedShort()

public actual fun vupklsb(a: VectorSignedChar): VectorSignedShort =
    VectorSignedShort()

public actual fun vupkhsh(a: VectorSignedShort): VectorSignedInt =
    VectorSignedInt()

public actual fun vupklsh(a: VectorSignedShort): VectorSignedInt =
    VectorSignedInt()

public actual fun mfvscr(): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vlogefp(a: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun vsl(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vslo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsrab(a: VectorSignedChar, b: VectorUnsignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsrah(a: VectorSignedShort, b: VectorUnsignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vsraw(a: VectorSignedInt, b: VectorUnsignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsr(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vsro(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vslv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vsrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vrfin(a: VectorFloat): VectorFloat =
    VectorFloat()

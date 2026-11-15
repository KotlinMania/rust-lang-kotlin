@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public actual fun lvx(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lvebx(p: ConstPtr): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lvehx(p: ConstPtr): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lvewx(p: ConstPtr): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lvxl(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stvx(a: VectorSignedInt, p: ConstPtr): Unit = throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stvebx(a: VectorSignedChar, p: ConstPtr): Unit = throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stvehx(a: VectorSignedShort, p: ConstPtr): Unit = throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stvewx(a: VectorSignedInt, p: ConstPtr): Unit = throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stvxl(a: VectorSignedInt, p: ConstPtr): Unit = throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmhaddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmhraddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vexptefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequbP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpequwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpeqfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtubP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtuhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtuwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtsbP(cr: Int, a: VectorSignedChar, b: VectorSignedChar): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtshP(cr: Int, a: VectorSignedShort, b: VectorSignedShort): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtswP(cr: Int, a: VectorSignedInt, b: VectorSignedInt): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgefpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpgtfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpbfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcfsx(a: VectorSignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcfux(a: VectorUnsignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vctsxs(a: VectorFloat, b: Int): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vctuxs(a: VectorFloat, b: Int): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vupkhsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vupklsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vupkhsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vupklsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mfvscr(): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vlogefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsl(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vslo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrab(a: VectorSignedChar, b: VectorUnsignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrah(a: VectorSignedShort, b: VectorUnsignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsraw(a: VectorSignedInt, b: VectorUnsignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsr(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsro(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vslv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vrfin(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun lvx(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecLvx")

public actual fun lvebx(p: ConstPtr): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecLvebx")

public actual fun lvehx(p: ConstPtr): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecLvehx")

public actual fun lvewx(p: ConstPtr): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecLvewx")

public actual fun lvxl(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecLvxl")

public actual fun stvx(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecStvx")
}

public actual fun stvebx(a: VectorSignedChar, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecStvebx")
}

public actual fun stvehx(a: VectorSignedShort, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecStvehx")
}

public actual fun stvewx(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecStvewx")
}

public actual fun stvxl(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecStvxl")
}

public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVperm")

public actual fun vmhaddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmhaddshs")

public actual fun vmhraddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmhraddshs")

public actual fun vmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsumuhs")

public actual fun vmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsumshs")

public actual fun vmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsumubm")

public actual fun vmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsummbm")

public actual fun vmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsumuhm")

public actual fun vmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmsumshm")

public actual fun vsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsum2sws")

public actual fun vsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsum4ubs")

public actual fun vsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsum4sbs")

public actual fun vsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsum4shs")

public actual fun vmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmuleub")

public actual fun vmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmulesb")

public actual fun vmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmuleuh")

public actual fun vmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmulesh")

public actual fun vmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmuloub")

public actual fun vmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmulosb")

public actual fun vmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmulouh")

public actual fun vmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmulosh")

public actual fun vmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxsb")

public actual fun vmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxsh")

public actual fun vmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxsw")

public actual fun vmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxub")

public actual fun vmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxuh")

public actual fun vmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVmaxuw")

public actual fun vminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminsb")

public actual fun vminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminsh")

public actual fun vminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminsw")

public actual fun vminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminub")

public actual fun vminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminuh")

public actual fun vminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVminuw")

public actual fun vsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubsbs")

public actual fun vsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubshs")

public actual fun vsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubsws")

public actual fun vsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsububs")

public actual fun vsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubuhs")

public actual fun vsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubuws")

public actual fun vsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsubcuw")

public actual fun vaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVaddcuw")

public actual fun vaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVaddsbs")

public actual fun vaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVaddshs")

public actual fun vaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVaddsws")

public actual fun vaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVaddubs")

public actual fun vadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVadduhs")

public actual fun vadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVadduws")

public actual fun vavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavgsb")

public actual fun vavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavgsh")

public actual fun vavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavgsw")

public actual fun vavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavgub")

public actual fun vavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavguh")

public actual fun vavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVavguw")

public actual fun vcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpbfp")

public actual fun vcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequb")

public actual fun vcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequh")

public actual fun vcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequw")

public actual fun vcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpneb")

public actual fun vcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpneh")

public actual fun vcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpnew")

public actual fun vcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgefp")

public actual fun vcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtub")

public actual fun vcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtuh")

public actual fun vcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtuw")

public actual fun vcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtsb")

public actual fun vcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtsh")

public actual fun vcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtsw")

public actual fun vexptefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVexptefp")

public actual fun vcmpequbP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequbP")

public actual fun vcmpequhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequhP")

public actual fun vcmpequwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpequwP")

public actual fun vcmpeqfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpeqfpP")

public actual fun vcmpgtubP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtubP")

public actual fun vcmpgtuhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtuhP")

public actual fun vcmpgtuwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtuwP")

public actual fun vcmpgtsbP(cr: Int, a: VectorSignedChar, b: VectorSignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtsbP")

public actual fun vcmpgtshP(cr: Int, a: VectorSignedShort, b: VectorSignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtshP")

public actual fun vcmpgtswP(cr: Int, a: VectorSignedInt, b: VectorSignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtswP")

public actual fun vcmpgefpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgefpP")

public actual fun vcmpgtfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpgtfpP")

public actual fun vcmpbfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcmpbfpP")

public actual fun vcfsx(a: VectorSignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcfsx")

public actual fun vcfux(a: VectorUnsignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVcfux")

public actual fun vctsxs(a: VectorFloat, b: Int): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVctsxs")

public actual fun vctuxs(a: VectorFloat, b: Int): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVctuxs")

public actual fun vpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkshss")

public actual fun vpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkshus")

public actual fun vpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkuhus")

public actual fun vpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkswss")

public actual fun vpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkswus")

public actual fun vpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVpkuwus")

public actual fun vupkhsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVupkhsb")

public actual fun vupklsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVupklsb")

public actual fun vupkhsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVupkhsh")

public actual fun vupklsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVupklsh")

public actual fun mfvscr(): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecMfvscr")

public actual fun vlogefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVlogefp")

public actual fun vsl(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsl")

public actual fun vslo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVslo")

public actual fun vsrab(a: VectorSignedChar, b: VectorUnsignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsrab")

public actual fun vsrah(a: VectorSignedShort, b: VectorUnsignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsrah")

public actual fun vsraw(a: VectorSignedInt, b: VectorUnsignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsraw")

public actual fun vsr(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsr")

public actual fun vsro(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsro")

public actual fun vslv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVslv")

public actual fun vsrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVsrv")

public actual fun vrfin(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpcAltivecVrfin")

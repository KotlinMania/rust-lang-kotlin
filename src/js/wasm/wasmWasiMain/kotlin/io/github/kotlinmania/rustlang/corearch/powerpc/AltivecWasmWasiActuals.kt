@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun lvx(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecLvx")

public actual fun lvebx(p: ConstPtr): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecLvebx")

public actual fun lvehx(p: ConstPtr): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecLvehx")

public actual fun lvewx(p: ConstPtr): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecLvewx")

public actual fun lvxl(p: ConstPtr): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecLvxl")

public actual fun stvx(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecStvx")
}

public actual fun stvebx(a: VectorSignedChar, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecStvebx")
}

public actual fun stvehx(a: VectorSignedShort, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecStvehx")
}

public actual fun stvewx(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecStvewx")
}

public actual fun stvxl(a: VectorSignedInt, p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecStvxl")
}

public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVperm")

public actual fun vmhaddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmhaddshs")

public actual fun vmhraddshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmhraddshs")

public actual fun vmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsumuhs")

public actual fun vmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsumshs")

public actual fun vmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsumubm")

public actual fun vmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsummbm")

public actual fun vmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsumuhm")

public actual fun vmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmsumshm")

public actual fun vsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsum2sws")

public actual fun vsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsum4ubs")

public actual fun vsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsum4sbs")

public actual fun vsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsum4shs")

public actual fun vmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmuleub")

public actual fun vmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmulesb")

public actual fun vmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmuleuh")

public actual fun vmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmulesh")

public actual fun vmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmuloub")

public actual fun vmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmulosb")

public actual fun vmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmulouh")

public actual fun vmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmulosh")

public actual fun vmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxsb")

public actual fun vmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxsh")

public actual fun vmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxsw")

public actual fun vmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxub")

public actual fun vmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxuh")

public actual fun vmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVmaxuw")

public actual fun vminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminsb")

public actual fun vminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminsh")

public actual fun vminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminsw")

public actual fun vminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminub")

public actual fun vminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminuh")

public actual fun vminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVminuw")

public actual fun vsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubsbs")

public actual fun vsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubshs")

public actual fun vsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubsws")

public actual fun vsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsububs")

public actual fun vsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubuhs")

public actual fun vsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubuws")

public actual fun vsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsubcuw")

public actual fun vaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVaddcuw")

public actual fun vaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVaddsbs")

public actual fun vaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVaddshs")

public actual fun vaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVaddsws")

public actual fun vaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVaddubs")

public actual fun vadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVadduhs")

public actual fun vadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVadduws")

public actual fun vavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavgsb")

public actual fun vavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavgsh")

public actual fun vavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavgsw")

public actual fun vavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavgub")

public actual fun vavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavguh")

public actual fun vavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVavguw")

public actual fun vcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpbfp")

public actual fun vcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequb")

public actual fun vcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequh")

public actual fun vcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequw")

public actual fun vcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpneb")

public actual fun vcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpneh")

public actual fun vcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpnew")

public actual fun vcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgefp")

public actual fun vcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtub")

public actual fun vcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtuh")

public actual fun vcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtuw")

public actual fun vcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtsb")

public actual fun vcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtsh")

public actual fun vcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtsw")

public actual fun vexptefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVexptefp")

public actual fun vcmpequbP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequbP")

public actual fun vcmpequhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequhP")

public actual fun vcmpequwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpequwP")

public actual fun vcmpeqfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpeqfpP")

public actual fun vcmpgtubP(cr: Int, a: VectorUnsignedChar, b: VectorUnsignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtubP")

public actual fun vcmpgtuhP(cr: Int, a: VectorUnsignedShort, b: VectorUnsignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtuhP")

public actual fun vcmpgtuwP(cr: Int, a: VectorUnsignedInt, b: VectorUnsignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtuwP")

public actual fun vcmpgtsbP(cr: Int, a: VectorSignedChar, b: VectorSignedChar): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtsbP")

public actual fun vcmpgtshP(cr: Int, a: VectorSignedShort, b: VectorSignedShort): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtshP")

public actual fun vcmpgtswP(cr: Int, a: VectorSignedInt, b: VectorSignedInt): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtswP")

public actual fun vcmpgefpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgefpP")

public actual fun vcmpgtfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpgtfpP")

public actual fun vcmpbfpP(cr: Int, a: VectorFloat, b: VectorFloat): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcmpbfpP")

public actual fun vcfsx(a: VectorSignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcfsx")

public actual fun vcfux(a: VectorUnsignedInt, b: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVcfux")

public actual fun vctsxs(a: VectorFloat, b: Int): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVctsxs")

public actual fun vctuxs(a: VectorFloat, b: Int): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVctuxs")

public actual fun vpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkshss")

public actual fun vpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkshus")

public actual fun vpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkuhus")

public actual fun vpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkswss")

public actual fun vpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkswus")

public actual fun vpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVpkuwus")

public actual fun vupkhsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVupkhsb")

public actual fun vupklsb(a: VectorSignedChar): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVupklsb")

public actual fun vupkhsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVupkhsh")

public actual fun vupklsh(a: VectorSignedShort): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVupklsh")

public actual fun mfvscr(): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecMfvscr")

public actual fun vlogefp(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVlogefp")

public actual fun vsl(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsl")

public actual fun vslo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVslo")

public actual fun vsrab(a: VectorSignedChar, b: VectorUnsignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsrab")

public actual fun vsrah(a: VectorSignedShort, b: VectorUnsignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsrah")

public actual fun vsraw(a: VectorSignedInt, b: VectorUnsignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsraw")

public actual fun vsr(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsr")

public actual fun vsro(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsro")

public actual fun vslv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVslv")

public actual fun vsrv(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVsrv")

public actual fun vrfin(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcAltivecVrfin")

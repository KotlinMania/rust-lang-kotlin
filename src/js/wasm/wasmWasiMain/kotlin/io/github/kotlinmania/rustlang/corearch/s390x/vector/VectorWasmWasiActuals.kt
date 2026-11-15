@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I8x16

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun nearbyintV4f32(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorNearbyintV4f32")

public actual fun nearbyintV2f64(a: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorNearbyintV2f64")

public actual fun roundevenV4f32(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorRoundevenV4f32")

public actual fun roundevenV2f64(a: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorRoundevenV2f64")

public actual fun vsra(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsra")

public actual fun vsrl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsrl")

public actual fun vsl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsl")

public actual fun vsrab(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsrab")

public actual fun vsrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsrlb")

public actual fun vslb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVslb")

public actual fun verimb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar, d: Int): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVerimb")

public actual fun verimh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort, d: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVerimh")

public actual fun verimf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt, d: Int): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVerimf")

public actual fun verimg(a: VectorSignedLongLong, b: VectorSignedLongLong, c: VectorSignedLongLong, d: Int): VectorSignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVerimg")

public actual fun vperm(a: VectorSignedChar, b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVperm")

public actual fun vsumb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumb")

public actual fun vsumh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumh")

public actual fun vsumgh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumgh")

public actual fun vsumgf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumgf")

public actual fun vsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumqf")

public actual fun vsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsumqg")

public actual fun vscbiq(a: U128, b: U128): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVscbiq")

public actual fun vsbiq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsbiq")

public actual fun vsbcbiq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVsbcbiq")

public actual fun vacq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVacq")

public actual fun vscbib(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVscbib")

public actual fun vscbih(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVscbih")

public actual fun vscbif(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVscbif")

public actual fun vscbig(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVscbig")

public actual fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaeb")

public actual fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaeh")

public actual fun vfaef(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaef")

public actual fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezb")

public actual fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezh")

public actual fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezf")

public actual fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaebs")

public actual fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaehs")

public actual fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaefs")

public actual fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezbs")

public actual fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezhs")

public actual fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfaezfs")

public actual fun vll(a: UInt, b: ConstPtr): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVll")

public actual fun vstl(a: VectorSignedChar, b: UInt, c: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstl")
}

public actual fun vlrl(a: UInt, b: ConstPtr): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVlrl")

public actual fun vstrl(a: VectorUnsignedChar, b: UInt, c: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrl")
}

public actual fun lcbb(a: ConstPtr, b: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorLcbb")

public actual fun vlbb(a: ConstPtr, b: UInt): MaybeUninit<VectorSignedChar> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVlbb")

public actual fun vpksh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpksh")

public actual fun vpksf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpksf")

public actual fun vpksg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpksg")

public actual fun vpklsh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklsh")

public actual fun vpklsf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklsf")

public actual fun vpklsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklsg")

public actual fun vpkshs(a: VectorSignedShort, b: VectorSignedShort): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpkshs")

public actual fun vpksfs(a: VectorSignedInt, b: VectorSignedInt): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpksfs")

public actual fun vpksgs(a: VectorSignedLongLong, b: VectorSignedLongLong): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpksgs")

public actual fun vpklshs(a: VectorUnsignedShort, b: VectorUnsignedShort): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklshs")

public actual fun vpklsfs(a: VectorUnsignedInt, b: VectorUnsignedInt): PackedTuple<VectorUnsignedShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklsfs")

public actual fun vpklsgs(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): PackedTuple<VectorUnsignedInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVpklsgs")

public actual fun vavgb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavgb")

public actual fun vavgh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavgh")

public actual fun vavgf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavgf")

public actual fun vavgg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavgg")

public actual fun vavglb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavglb")

public actual fun vavglh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavglh")

public actual fun vavglf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavglf")

public actual fun vavglg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVavglg")

public actual fun vcksm(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVcksm")

public actual fun vmhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmhb")

public actual fun vmhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmhh")

public actual fun vmhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmhf")

public actual fun vmlhb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmlhb")

public actual fun vmlhh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmlhh")

public actual fun vmlhf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmlhf")

public actual fun vmaeb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaeb")

public actual fun vmaeh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaeh")

public actual fun vmaef(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaef")

public actual fun vmaleb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaleb")

public actual fun vmaleh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaleh")

public actual fun vmalef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalef")

public actual fun vmaob(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaob")

public actual fun vmaoh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaoh")

public actual fun vmaof(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaof")

public actual fun vmalob(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalob")

public actual fun vmaloh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmaloh")

public actual fun vmalof(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalof")

public actual fun vmahb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmahb")

public actual fun vmahh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmahh")

public actual fun vmahf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmahf")

public actual fun vmalhb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalhb")

public actual fun vmalhh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalhh")

public actual fun vmalhf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalhf")

public actual fun vmalb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalb")

public actual fun vmalh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalh")

public actual fun vmalf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmalf")

public actual fun vmallb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmallb")

public actual fun vmallh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmallh")

public actual fun vmallf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmallf")

public actual fun vgfmb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmb")

public actual fun vgfmh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmh")

public actual fun vgfmf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmf")

public actual fun vgfmg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmg")

public actual fun vgfmab(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmab")

public actual fun vgfmah(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmah")

public actual fun vgfmaf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmaf")

public actual fun vgfmag(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVgfmag")

public actual fun vbperm(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedLongLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVbperm")

public actual fun vftcisb(a: VectorFloat, b: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVftcisb")

public actual fun vftcidb(a: VectorDouble, b: UInt): PackedTuple<VectorBoolLongLong, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVftcidb")

public actual fun vtm(a: I8x16, b: I8x16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVtm")

public actual fun vstrsb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrsb")

public actual fun vstrsh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrsh")

public actual fun vstrsf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrsf")

public actual fun vstrszb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrszb")

public actual fun vstrszh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrszh")

public actual fun vstrszf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrszf")

public actual fun vistrb(a: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrb")

public actual fun vistrh(a: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrh")

public actual fun vistrf(a: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrf")

public actual fun vistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrbs")

public actual fun vistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrhs")

public actual fun vistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVistrfs")

public actual fun vmslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128, d: UInt): U128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVmslg")

public actual fun vstrcb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrcb")

public actual fun vstrch(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrch")

public actual fun vstrcf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrcf")

public actual fun vstrcbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrcbs")

public actual fun vstrchs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrchs")

public actual fun vstrcfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrcfs")

public actual fun vstrczb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczb")

public actual fun vstrczh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczh")

public actual fun vstrczf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczf")

public actual fun vstrczbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczbs")

public actual fun vstrczhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczhs")

public actual fun vstrczfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVstrczfs")

public actual fun vfeeb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeeb")

public actual fun vfeeh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeeh")

public actual fun vfeef(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeef")

public actual fun vfeezb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezb")

public actual fun vfeezh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezh")

public actual fun vfeezf(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezf")

public actual fun vfeebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeebs")

public actual fun vfeehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeehs")

public actual fun vfeefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeefs")

public actual fun vfeezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezbs")

public actual fun vfeezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezhs")

public actual fun vfeezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeezfs")

public actual fun vfeneb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeneb")

public actual fun vfeneh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfeneh")

public actual fun vfenef(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenef")

public actual fun vfenezb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezb")

public actual fun vfenezh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezh")

public actual fun vfenezf(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezf")

public actual fun vfenebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenebs")

public actual fun vfenehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenehs")

public actual fun vfenefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenefs")

public actual fun vfenezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezbs")

public actual fun vfenezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezhs")

public actual fun vfenezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVfenezfs")

public actual fun vclfnhs(a: VectorSignedShort, immarg: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVclfnhs")

public actual fun vclfnls(a: VectorSignedShort, immarg: Int): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVclfnls")

public actual fun vcfn(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVcfn")

public actual fun vcnf(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVcnf")

public actual fun vcrnfs(a: VectorFloat, b: VectorFloat, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorVcrnfs")

public actual fun minnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorMinnumV4f32")

public actual fun minnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorMinnumV2f64")

public actual fun maxnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorMaxnumV4f32")

public actual fun maxnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchS390xVectorMaxnumV2f64")

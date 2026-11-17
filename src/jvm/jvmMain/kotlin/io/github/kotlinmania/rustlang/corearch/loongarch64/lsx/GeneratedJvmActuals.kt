@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public actual fun externLsxVsrarB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun externLsxVsrarH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun externLsxVsrarW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun externLsxVsrarD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsrariB(a: V16i8, b: UInt): V16i8 =
    M128i()

public actual fun lsxVsrariH(a: V8i16, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsrariW(a: V4i32, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsrariD(a: V2i64, b: UInt): V2i64 =
    M128i()

public actual fun externLsxVsrlrB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun externLsxVsrlrH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun externLsxVsrlrW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun externLsxVsrlrD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsrlriB(a: V16i8, b: UInt): V16i8 =
    M128i()

public actual fun lsxVsrlriH(a: V8i16, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsrlriW(a: V4i32, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsrlriD(a: V2i64, b: UInt): V2i64 =
    M128i()

public actual fun lsxVbitclriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVbitclriH(a: V8u16, b: UInt): V8u16 =
    M128i()

public actual fun lsxVbitclriW(a: V4u32, b: UInt): V4u32 =
    M128i()

public actual fun lsxVbitclriD(a: V2u64, b: UInt): V2u64 =
    M128i()

public actual fun lsxVbitsetiB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVbitsetiH(a: V8u16, b: UInt): V8u16 =
    M128i()

public actual fun lsxVbitsetiW(a: V4u32, b: UInt): V4u32 =
    M128i()

public actual fun lsxVbitsetiD(a: V2u64, b: UInt): V2u64 =
    M128i()

public actual fun lsxVbitreviB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVbitreviH(a: V8u16, b: UInt): V8u16 =
    M128i()

public actual fun lsxVbitreviW(a: V4u32, b: UInt): V4u32 =
    M128i()

public actual fun lsxVbitreviD(a: V2u64, b: UInt): V2u64 =
    M128i()

public actual fun lsxVsubiBu(a: V16i8, b: UInt): V16i8 =
    M128i()

public actual fun lsxVsubiHu(a: V8i16, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsubiWu(a: V4i32, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsubiDu(a: V2i64, b: UInt): V2i64 =
    M128i()

public actual fun lsxVsatB(a: V16i8, b: UInt): V16i8 =
    M128i()

public actual fun lsxVsatH(a: V8i16, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsatW(a: V4i32, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsatD(a: V2i64, b: UInt): V2i64 =
    M128i()

public actual fun lsxVsatBu(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVsatHu(a: V8u16, b: UInt): V8u16 =
    M128i()

public actual fun lsxVsatWu(a: V4u32, b: UInt): V4u32 =
    M128i()

public actual fun lsxVsatDu(a: V2u64, b: UInt): V2u64 =
    M128i()

public actual fun externLsxVdivB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun externLsxVdivH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun externLsxVdivW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun externLsxVdivD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVdivBu(a: V16u8, b: V16u8): V16u8 =
    M128i()

public actual fun externLsxVdivHu(a: V8u16, b: V8u16): V8u16 =
    M128i()

public actual fun externLsxVdivWu(a: V4u32, b: V4u32): V4u32 =
    M128i()

public actual fun externLsxVdivDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun externLsxVhaddwHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVhaddwWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVhaddwDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVhaddwHuBu(a: V16u8, b: V16u8): V8u16 =
    M128i()

public actual fun externLsxVhaddwWuHu(a: V8u16, b: V8u16): V4u32 =
    M128i()

public actual fun externLsxVhaddwDuWu(a: V4u32, b: V4u32): V2u64 =
    M128i()

public actual fun externLsxVhsubwHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVhsubwWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVhsubwDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVhsubwHuBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVhsubwWuHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVhsubwDuWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVmodB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun externLsxVmodH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun externLsxVmodW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun externLsxVmodD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmodBu(a: V16u8, b: V16u8): V16u8 =
    M128i()

public actual fun externLsxVmodHu(a: V8u16, b: V8u16): V8u16 =
    M128i()

public actual fun externLsxVmodWu(a: V4u32, b: V4u32): V4u32 =
    M128i()

public actual fun externLsxVmodDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun externLsxVreplveB(a: V16i8, b: Int): V16i8 =
    M128i()

public actual fun externLsxVreplveH(a: V8i16, b: Int): V8i16 =
    M128i()

public actual fun externLsxVreplveW(a: V4i32, b: Int): V4i32 =
    M128i()

public actual fun externLsxVreplveD(a: V2i64, b: Int): V2i64 =
    M128i()

public actual fun externLsxVshufH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    M128i()

public actual fun externLsxVshufW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    M128i()

public actual fun externLsxVshufD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVandiB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVnoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVxoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun externLsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    M128i()

public actual fun lsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8 =
    M128i()

public actual fun externLsxVcloB(a: V16i8): V16i8 =
    M128i()

public actual fun externLsxVcloH(a: V8i16): V8i16 =
    M128i()

public actual fun externLsxVcloW(a: V4i32): V4i32 =
    M128i()

public actual fun externLsxVcloD(a: V2i64): V2i64 =
    M128i()

public actual fun externLsxVfcvtHS(a: V4f32, b: V4f32): V8i16 =
    M128i()

public actual fun externLsxVfcvtSD(a: V2f64, b: V2f64): V4f32 =
    M128()

public actual fun externLsxVfminS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun externLsxVfminD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfminaS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun externLsxVfminaD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfmaxS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun externLsxVfmaxD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfmaxaS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun externLsxVfmaxaD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfclassS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfclassD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfrecipeS(a: V4f32): V4f32 =
    M128()

public actual fun externLsxVfrecipeD(a: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfrsqrteS(a: V4f32): V4f32 =
    M128()

public actual fun externLsxVfrsqrteD(a: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfrintS(a: V4f32): V4f32 =
    M128()

public actual fun externLsxVfrintD(a: V2f64): V2f64 =
    M128d()

public actual fun externLsxVflogbS(a: V4f32): V4f32 =
    M128()

public actual fun externLsxVflogbD(a: V2f64): V2f64 =
    M128d()

public actual fun externLsxVfcvthSH(a: V8i16): V4f32 =
    M128()

public actual fun externLsxVfcvthDS(a: V4f32): V2f64 =
    M128d()

public actual fun externLsxVfcvtlSH(a: V8i16): V4f32 =
    M128()

public actual fun externLsxVfcvtlDS(a: V4f32): V2f64 =
    M128d()

public actual fun externLsxVftintWS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVftintLD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVftintWuS(a: V4f32): V4u32 =
    M128i()

public actual fun externLsxVftintLuD(a: V2f64): V2u64 =
    M128i()

public actual fun externLsxVftintrzWS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVftintrzLD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVftintrzWuS(a: V4f32): V4u32 =
    M128i()

public actual fun externLsxVftintrzLuD(a: V2f64): V2u64 =
    M128i()

public actual fun externLsxVffintSW(a: V4i32): V4f32 =
    M128()

public actual fun externLsxVffintDL(a: V2i64): V2f64 =
    M128d()

public actual fun externLsxVffintSWu(a: V4u32): V4f32 =
    M128()

public actual fun externLsxVffintDLu(a: V2u64): V2f64 =
    M128d()

public actual fun lsxVsllwilHB(a: V16i8, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsllwilWH(a: V8i16, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsllwilDW(a: V4i32, b: UInt): V2i64 =
    M128i()

public actual fun lsxVsllwilHuBu(a: V16u8, b: UInt): V8u16 =
    M128i()

public actual fun lsxVsllwilWuHu(a: V8u16, b: UInt): V4u32 =
    M128i()

public actual fun lsxVsllwilDuWu(a: V4u32, b: UInt): V2u64 =
    M128i()

public actual fun externLsxVsranBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVsranHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVsranWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssranBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVssranHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVssranWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssranBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun externLsxVssranHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun externLsxVssranWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun externLsxVsrarnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVsrarnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVsrarnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssrarnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVssrarnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVssrarnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssrarnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun externLsxVssrarnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun externLsxVssrarnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun externLsxVsrlnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVsrlnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVsrlnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssrlnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun externLsxVssrlnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun externLsxVssrlnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun externLsxVsrlrnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVsrlrnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVsrlrnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssrlrnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun externLsxVssrlrnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun externLsxVssrlrnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun lsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun externLsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    M128i()

public actual fun externLsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    M128i()

public actual fun lsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun externLsxVmskltzB(a: V16i8): V16i8 =
    M128i()

public actual fun externLsxVmskltzH(a: V8i16): V8i16 =
    M128i()

public actual fun externLsxVmskltzW(a: V4i32): V4i32 =
    M128i()

public actual fun externLsxVmskltzD(a: V2i64): V2i64 =
    M128i()

public actual fun externLsxVsigncovB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun externLsxVsigncovH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun externLsxVsigncovW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun externLsxVsigncovD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVftintrneWS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVftintrneLD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVftintrpWS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVftintrpLD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVftintrmWS(a: V4f32): V4i32 =
    M128i()

public actual fun externLsxVftintrmLD(a: V2f64): V2i64 =
    M128i()

public actual fun externLsxVftintWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun externLsxVffintSL(a: V2i64, b: V2i64): V4f32 =
    M128()

public actual fun externLsxVftintrzWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun externLsxVftintrpWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun externLsxVftintrmWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun externLsxVftintrneWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun externLsxVftintlLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftinthLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVffinthDW(a: V4i32): V2f64 =
    M128d()

public actual fun externLsxVffintlDW(a: V4i32): V2f64 =
    M128d()

public actual fun externLsxVftintrzlLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrzhLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrplLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrphLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrmlLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrmhLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrnelLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVftintrnehLS(a: V4f32): V2i64 =
    M128i()

public actual fun externLsxVfrintrneS(a: V4f32): V4f32 =
    M128()

public actual fun externLsxVfrintrneD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt) {
}

public actual fun externLsxVaddwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVaddwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVaddwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVaddwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVaddwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVaddwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVaddwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVaddwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVaddwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVaddwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVaddwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVaddwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVaddwevDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVaddwevWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVaddwevHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVaddwodDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVaddwodWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVaddwodHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVsubwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVsubwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVsubwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVsubwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVsubwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVsubwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVsubwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVsubwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVsubwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVsubwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVsubwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVsubwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVaddwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVaddwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVaddwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVaddwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVsubwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVsubwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVsubwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVsubwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVaddwevQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVaddwodQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmulwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmulwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmulwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmulwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmulwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmulwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmulwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVmulwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVmulwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVmulwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun externLsxVmulwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun externLsxVmulwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun externLsxVmulwevDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmulwevWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmulwevHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmulwodDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmulwodWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmulwodHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmulwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmulwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmulwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVmulwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun externLsxVmulwevQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmulwodQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVhaddwQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVhaddwQuDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun externLsxVhsubwQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVhsubwQuDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun externLsxVmaddwevDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmaddwevWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmaddwevHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmaddwevDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    M128i()

public actual fun externLsxVmaddwevWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    M128i()

public actual fun externLsxVmaddwevHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    M128i()

public actual fun externLsxVmaddwodDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmaddwodWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmaddwodHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmaddwodDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    M128i()

public actual fun externLsxVmaddwodWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    M128i()

public actual fun externLsxVmaddwodHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    M128i()

public actual fun externLsxVmaddwevDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmaddwevWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmaddwevHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmaddwodDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    M128i()

public actual fun externLsxVmaddwodWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    M128i()

public actual fun externLsxVmaddwodHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    M128i()

public actual fun externLsxVmaddwevQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmaddwodQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmaddwevQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    M128i()

public actual fun externLsxVmaddwodQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    M128i()

public actual fun externLsxVmaddwevQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    M128i()

public actual fun externLsxVmaddwodQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    M128i()

public actual fun externLsxVaddQ(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun externLsxVsubQ(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVldreplB(a: ConstPtr, b: Int): V16i8 =
    M128i()

public actual fun lsxVldreplH(a: ConstPtr, b: Int): V8i16 =
    M128i()

public actual fun lsxVldreplW(a: ConstPtr, b: Int): V4i32 =
    M128i()

public actual fun lsxVldreplD(a: ConstPtr, b: Int): V2i64 =
    M128i()

public actual fun externLsxVmskgezB(a: V16i8): V16i8 =
    M128i()

public actual fun externLsxVmsknzB(a: V16i8): V16i8 =
    M128i()

public actual fun externLsxVexthHB(a: V16i8): V8i16 =
    M128i()

public actual fun externLsxVexthWH(a: V8i16): V4i32 =
    M128i()

public actual fun externLsxVexthDW(a: V4i32): V2i64 =
    M128i()

public actual fun externLsxVexthQD(a: V2i64): V2i64 =
    M128i()

public actual fun externLsxVexthHuBu(a: V16u8): V8u16 =
    M128i()

public actual fun externLsxVexthWuHu(a: V8u16): V4u32 =
    M128i()

public actual fun externLsxVexthDuWu(a: V4u32): V2u64 =
    M128i()

public actual fun externLsxVexthQuDu(a: V2u64): V2u64 =
    M128i()

public actual fun externLsxVextlQD(a: V2i64): V2i64 =
    M128i()

public actual fun lsxVsrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVsrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVsrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVsrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVsrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVsrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVsrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVsrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVssrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVssrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVssrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssrlniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    M128i()

public actual fun lsxVssrlniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    M128i()

public actual fun lsxVssrlniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    M128i()

public actual fun lsxVssrlniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    M128i()

public actual fun lsxVssrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVssrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVssrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVssrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssrlrniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    M128i()

public actual fun lsxVssrlrniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    M128i()

public actual fun lsxVssrlrniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    M128i()

public actual fun lsxVssrlrniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    M128i()

public actual fun lsxVsraniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVsraniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVsraniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVsraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVsrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVsrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVsrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVsrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssraniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVssraniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVssraniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVssraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssraniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    M128i()

public actual fun lsxVssraniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    M128i()

public actual fun lsxVssraniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    M128i()

public actual fun lsxVssraniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    M128i()

public actual fun lsxVssrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVssrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVssrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVssrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVssrarniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    M128i()

public actual fun lsxVssrarniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    M128i()

public actual fun lsxVssrarniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    M128i()

public actual fun lsxVssrarniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    M128i()

public actual fun lsxVpermiW(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun externLsxVssrlrnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVssrlrnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVssrlrnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVssrlnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun externLsxVssrlnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun externLsxVssrlnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun externLsxVshufB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    M128i()

public actual fun externLsxVextlQuDu(a: V2u64): V2u64 =
    M128i()

public actual fun externLsxBnzB(a: V16u8): Int =
    0

public actual fun externLsxBnzD(a: V2u64): Int =
    0

public actual fun externLsxBnzH(a: V8u16): Int =
    0

public actual fun externLsxBnzV(a: V16u8): Int =
    0

public actual fun externLsxBnzW(a: V4u32): Int =
    0

public actual fun externLsxBzB(a: V16u8): Int =
    0

public actual fun externLsxBzD(a: V2u64): Int =
    0

public actual fun externLsxBzH(a: V8u16): Int =
    0

public actual fun externLsxBzV(a: V16u8): Int =
    0

public actual fun externLsxBzW(a: V4u32): Int =
    0

public actual fun externLsxVfcmpCafD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCafS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCeqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCeqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCltD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCltS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCorD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCorS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCueqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCueqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCuleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCuleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCultD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCultS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCunD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCuneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpCuneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpCunS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSafD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSafS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSeqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSeqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSltD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSltS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSorD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSorS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSueqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSueqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSuleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSuleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSultD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSultS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSunD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSuneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun externLsxVfcmpSuneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun externLsxVfcmpSunS(a: V4f32, b: V4f32): V4i32 =
    M128i()

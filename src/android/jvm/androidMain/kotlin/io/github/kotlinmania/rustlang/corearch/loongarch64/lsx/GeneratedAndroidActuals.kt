@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.loongarch64.lsx.V16i8
import io.github.kotlinmania.rustlang.loongarch64.lsx.V16u8
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2u64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4f32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4i32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4u32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V8i16
import io.github.kotlinmania.rustlang.loongarch64.lsx.V8u16

public actual fun lsxVsrarB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun lsxVsrarH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun lsxVsrarW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun lsxVsrarD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsrariB(a: V16i8, b: UInt): V16i8 =
    M128i()

public actual fun lsxVsrariH(a: V8i16, b: UInt): V8i16 =
    M128i()

public actual fun lsxVsrariW(a: V4i32, b: UInt): V4i32 =
    M128i()

public actual fun lsxVsrariD(a: V2i64, b: UInt): V2i64 =
    M128i()

public actual fun lsxVsrlrB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun lsxVsrlrH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun lsxVsrlrW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun lsxVsrlrD(a: V2i64, b: V2i64): V2i64 =
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

public actual fun lsxVdivB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun lsxVdivH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun lsxVdivW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun lsxVdivD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVdivBu(a: V16u8, b: V16u8): V16u8 =
    M128i()

public actual fun lsxVdivHu(a: V8u16, b: V8u16): V8u16 =
    M128i()

public actual fun lsxVdivWu(a: V4u32, b: V4u32): V4u32 =
    M128i()

public actual fun lsxVdivDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun lsxVhaddwHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVhaddwWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVhaddwDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVhaddwHuBu(a: V16u8, b: V16u8): V8u16 =
    M128i()

public actual fun lsxVhaddwWuHu(a: V8u16, b: V8u16): V4u32 =
    M128i()

public actual fun lsxVhaddwDuWu(a: V4u32, b: V4u32): V2u64 =
    M128i()

public actual fun lsxVhsubwHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVhsubwWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVhsubwDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVhsubwHuBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVhsubwWuHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVhsubwDuWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVmodB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun lsxVmodH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun lsxVmodW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun lsxVmodD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVmodBu(a: V16u8, b: V16u8): V16u8 =
    M128i()

public actual fun lsxVmodHu(a: V8u16, b: V8u16): V8u16 =
    M128i()

public actual fun lsxVmodWu(a: V4u32, b: V4u32): V4u32 =
    M128i()

public actual fun lsxVmodDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun lsxVreplveB(a: V16i8, b: Int): V16i8 =
    M128i()

public actual fun lsxVreplveH(a: V8i16, b: Int): V8i16 =
    M128i()

public actual fun lsxVreplveW(a: V4i32, b: Int): V4i32 =
    M128i()

public actual fun lsxVreplveD(a: V2i64, b: Int): V2i64 =
    M128i()

public actual fun lsxVshufH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    M128i()

public actual fun lsxVshufW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    M128i()

public actual fun lsxVshufD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVandiB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVnoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVxoriB(a: V16u8, b: UInt): V16u8 =
    M128i()

public actual fun lsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    M128i()

public actual fun lsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8 =
    M128i()

public actual fun lsxVcloB(a: V16i8): V16i8 =
    M128i()

public actual fun lsxVcloH(a: V8i16): V8i16 =
    M128i()

public actual fun lsxVcloW(a: V4i32): V4i32 =
    M128i()

public actual fun lsxVcloD(a: V2i64): V2i64 =
    M128i()

public actual fun lsxVfcvtHS(a: V4f32, b: V4f32): V8i16 =
    M128i()

public actual fun lsxVfcvtSD(a: V2f64, b: V2f64): V4f32 =
    M128()

public actual fun lsxVfminS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun lsxVfminD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun lsxVfminaS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun lsxVfminaD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun lsxVfmaxS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun lsxVfmaxD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun lsxVfmaxaS(a: V4f32, b: V4f32): V4f32 =
    M128()

public actual fun lsxVfmaxaD(a: V2f64, b: V2f64): V2f64 =
    M128d()

public actual fun lsxVfclassS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVfclassD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVfrecipeS(a: V4f32): V4f32 =
    M128()

public actual fun lsxVfrecipeD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVfrsqrteS(a: V4f32): V4f32 =
    M128()

public actual fun lsxVfrsqrteD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVfrintS(a: V4f32): V4f32 =
    M128()

public actual fun lsxVfrintD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVflogbS(a: V4f32): V4f32 =
    M128()

public actual fun lsxVflogbD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVfcvthSH(a: V8i16): V4f32 =
    M128()

public actual fun lsxVfcvthDS(a: V4f32): V2f64 =
    M128d()

public actual fun lsxVfcvtlSH(a: V8i16): V4f32 =
    M128()

public actual fun lsxVfcvtlDS(a: V4f32): V2f64 =
    M128d()

public actual fun lsxVftintWS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVftintLD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVftintWuS(a: V4f32): V4u32 =
    M128i()

public actual fun lsxVftintLuD(a: V2f64): V2u64 =
    M128i()

public actual fun lsxVftintrzWS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVftintrzLD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVftintrzWuS(a: V4f32): V4u32 =
    M128i()

public actual fun lsxVftintrzLuD(a: V2f64): V2u64 =
    M128i()

public actual fun lsxVffintSW(a: V4i32): V4f32 =
    M128()

public actual fun lsxVffintDL(a: V2i64): V2f64 =
    M128d()

public actual fun lsxVffintSWu(a: V4u32): V4f32 =
    M128()

public actual fun lsxVffintDLu(a: V2u64): V2f64 =
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

public actual fun lsxVsranBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVsranHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVsranWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssranBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVssranHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVssranWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssranBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun lsxVssranHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun lsxVssranWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun lsxVsrarnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVsrarnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVsrarnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssrarnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVssrarnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVssrarnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssrarnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun lsxVssrarnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun lsxVssrarnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun lsxVsrlnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVsrlnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVsrlnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssrlnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun lsxVssrlnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun lsxVssrlnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun lsxVsrlrnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVsrlrnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVsrlrnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssrlrnBuH(a: V8u16, b: V8u16): V16u8 =
    M128i()

public actual fun lsxVssrlrnHuW(a: V4u32, b: V4u32): V8u16 =
    M128i()

public actual fun lsxVssrlrnWuD(a: V2u64, b: V2u64): V4u32 =
    M128i()

public actual fun lsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    M128i()

public actual fun lsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    M128i()

public actual fun lsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    M128i()

public actual fun lsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    M128i()

public actual fun lsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32 =
    M128i()

public actual fun lsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64 =
    M128i()

public actual fun lsxVmskltzB(a: V16i8): V16i8 =
    M128i()

public actual fun lsxVmskltzH(a: V8i16): V8i16 =
    M128i()

public actual fun lsxVmskltzW(a: V4i32): V4i32 =
    M128i()

public actual fun lsxVmskltzD(a: V2i64): V2i64 =
    M128i()

public actual fun lsxVsigncovB(a: V16i8, b: V16i8): V16i8 =
    M128i()

public actual fun lsxVsigncovH(a: V8i16, b: V8i16): V8i16 =
    M128i()

public actual fun lsxVsigncovW(a: V4i32, b: V4i32): V4i32 =
    M128i()

public actual fun lsxVsigncovD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVftintrneWS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVftintrneLD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVftintrpWS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVftintrpLD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVftintrmWS(a: V4f32): V4i32 =
    M128i()

public actual fun lsxVftintrmLD(a: V2f64): V2i64 =
    M128i()

public actual fun lsxVftintWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun lsxVffintSL(a: V2i64, b: V2i64): V4f32 =
    M128()

public actual fun lsxVftintrzWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun lsxVftintrpWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun lsxVftintrmWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun lsxVftintrneWD(a: V2f64, b: V2f64): V4i32 =
    M128i()

public actual fun lsxVftintlLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftinthLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVffinthDW(a: V4i32): V2f64 =
    M128d()

public actual fun lsxVffintlDW(a: V4i32): V2f64 =
    M128d()

public actual fun lsxVftintrzlLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrzhLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrplLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrphLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrmlLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrmhLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrnelLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVftintrnehLS(a: V4f32): V2i64 =
    M128i()

public actual fun lsxVfrintrneS(a: V4f32): V4f32 =
    M128()

public actual fun lsxVfrintrneD(a: V2f64): V2f64 =
    M128d()

public actual fun lsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lsxVaddwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVaddwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVaddwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVaddwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVaddwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVaddwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVaddwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVaddwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVaddwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVaddwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVaddwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVaddwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVaddwevDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVaddwevWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVaddwevHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVaddwodDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVaddwodWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVaddwodHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVsubwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVsubwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVsubwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVsubwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVsubwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVsubwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVsubwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVsubwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVsubwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVsubwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVsubwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVsubwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVaddwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVaddwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVaddwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVaddwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVsubwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsubwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsubwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVsubwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVaddwevQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVaddwodQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVmulwevDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVmulwevWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVmulwevHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVmulwodDW(a: V4i32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVmulwodWH(a: V8i16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVmulwodHB(a: V16i8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVmulwevDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVmulwevWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVmulwevHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVmulwodDWu(a: V4u32, b: V4u32): V2i64 =
    M128i()

public actual fun lsxVmulwodWHu(a: V8u16, b: V8u16): V4i32 =
    M128i()

public actual fun lsxVmulwodHBu(a: V16u8, b: V16u8): V8i16 =
    M128i()

public actual fun lsxVmulwevDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVmulwevWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVmulwevHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVmulwodDWuW(a: V4u32, b: V4i32): V2i64 =
    M128i()

public actual fun lsxVmulwodWHuH(a: V8u16, b: V8i16): V4i32 =
    M128i()

public actual fun lsxVmulwodHBuB(a: V16u8, b: V16i8): V8i16 =
    M128i()

public actual fun lsxVmulwevQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVmulwodQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVmulwevQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVmulwodQDu(a: V2u64, b: V2u64): V2i64 =
    M128i()

public actual fun lsxVmulwevQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVmulwodQDuD(a: V2u64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVhaddwQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVhaddwQuDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun lsxVhsubwQD(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVhsubwQuDu(a: V2u64, b: V2u64): V2u64 =
    M128i()

public actual fun lsxVmaddwevDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    M128i()

public actual fun lsxVmaddwevWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    M128i()

public actual fun lsxVmaddwevHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    M128i()

public actual fun lsxVmaddwevDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    M128i()

public actual fun lsxVmaddwevWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    M128i()

public actual fun lsxVmaddwevHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    M128i()

public actual fun lsxVmaddwodDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    M128i()

public actual fun lsxVmaddwodWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    M128i()

public actual fun lsxVmaddwodHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    M128i()

public actual fun lsxVmaddwodDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    M128i()

public actual fun lsxVmaddwodWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    M128i()

public actual fun lsxVmaddwodHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    M128i()

public actual fun lsxVmaddwevDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    M128i()

public actual fun lsxVmaddwevWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    M128i()

public actual fun lsxVmaddwevHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    M128i()

public actual fun lsxVmaddwodDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    M128i()

public actual fun lsxVmaddwodWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    M128i()

public actual fun lsxVmaddwodHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    M128i()

public actual fun lsxVmaddwevQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVmaddwodQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVmaddwevQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    M128i()

public actual fun lsxVmaddwodQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    M128i()

public actual fun lsxVmaddwevQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVmaddwodQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    M128i()

public actual fun lsxVaddQ(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVsubQ(a: V2i64, b: V2i64): V2i64 =
    M128i()

public actual fun lsxVldreplB(a: ConstPtr, b: Int): V16i8 =
    M128i()

public actual fun lsxVldreplH(a: ConstPtr, b: Int): V8i16 =
    M128i()

public actual fun lsxVldreplW(a: ConstPtr, b: Int): V4i32 =
    M128i()

public actual fun lsxVldreplD(a: ConstPtr, b: Int): V2i64 =
    M128i()

public actual fun lsxVmskgezB(a: V16i8): V16i8 =
    M128i()

public actual fun lsxVmsknzB(a: V16i8): V16i8 =
    M128i()

public actual fun lsxVexthHB(a: V16i8): V8i16 =
    M128i()

public actual fun lsxVexthWH(a: V8i16): V4i32 =
    M128i()

public actual fun lsxVexthDW(a: V4i32): V2i64 =
    M128i()

public actual fun lsxVexthQD(a: V2i64): V2i64 =
    M128i()

public actual fun lsxVexthHuBu(a: V16u8): V8u16 =
    M128i()

public actual fun lsxVexthWuHu(a: V8u16): V4u32 =
    M128i()

public actual fun lsxVexthDuWu(a: V4u32): V2u64 =
    M128i()

public actual fun lsxVexthQuDu(a: V2u64): V2u64 =
    M128i()

public actual fun lsxVextlQD(a: V2i64): V2i64 =
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

public actual fun lsxVssrlrnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVssrlrnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVssrlrnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVssrlnBH(a: V8i16, b: V8i16): V16i8 =
    M128i()

public actual fun lsxVssrlnHW(a: V4i32, b: V4i32): V8i16 =
    M128i()

public actual fun lsxVssrlnWD(a: V2i64, b: V2i64): V4i32 =
    M128i()

public actual fun lsxVshufB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    M128i()

public actual fun lsxVextlQuDu(a: V2u64): V2u64 =
    M128i()

public actual fun lsxBnzB(a: V16u8): Int =
    0

public actual fun lsxBnzD(a: V2u64): Int =
    0

public actual fun lsxBnzH(a: V8u16): Int =
    0

public actual fun lsxBnzV(a: V16u8): Int =
    0

public actual fun lsxBnzW(a: V4u32): Int =
    0

public actual fun lsxBzB(a: V16u8): Int =
    0

public actual fun lsxBzD(a: V2u64): Int =
    0

public actual fun lsxBzH(a: V8u16): Int =
    0

public actual fun lsxBzV(a: V16u8): Int =
    0

public actual fun lsxBzW(a: V4u32): Int =
    0

public actual fun lsxVfcmpCafD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCafS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCeqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCeqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCltD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCltS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCorD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCorS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCueqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCueqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCuleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCuleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCultD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCultS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCunD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCuneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpCuneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpCunS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSafD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSafS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSeqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSeqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSltD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSltS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSorD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSorS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSueqD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSueqS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSuleD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSuleS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSultD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSultS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSunD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSuneD(a: V2f64, b: V2f64): V2i64 =
    M128i()

public actual fun lsxVfcmpSuneS(a: V4f32, b: V4f32): V4i32 =
    M128i()

public actual fun lsxVfcmpSunS(a: V4f32, b: V4f32): V4i32 =
    M128i()

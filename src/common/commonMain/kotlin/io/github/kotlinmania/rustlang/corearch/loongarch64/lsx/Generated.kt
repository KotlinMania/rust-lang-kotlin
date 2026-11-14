// port-lint: source loongarch64/lsx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public expect fun lsxVsrarB(a: V16i8, b: V16i8): V16i8

public expect fun lsxVsrarH(a: V8i16, b: V8i16): V8i16

public expect fun lsxVsrarW(a: V4i32, b: V4i32): V4i32

public expect fun lsxVsrarD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsrariB(a: V16i8, b: UInt): V16i8

public expect fun lsxVsrariH(a: V8i16, b: UInt): V8i16

public expect fun lsxVsrariW(a: V4i32, b: UInt): V4i32

public expect fun lsxVsrariD(a: V2i64, b: UInt): V2i64

public expect fun lsxVsrlrB(a: V16i8, b: V16i8): V16i8

public expect fun lsxVsrlrH(a: V8i16, b: V8i16): V8i16

public expect fun lsxVsrlrW(a: V4i32, b: V4i32): V4i32

public expect fun lsxVsrlrD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsrlriB(a: V16i8, b: UInt): V16i8

public expect fun lsxVsrlriH(a: V8i16, b: UInt): V8i16

public expect fun lsxVsrlriW(a: V4i32, b: UInt): V4i32

public expect fun lsxVsrlriD(a: V2i64, b: UInt): V2i64

public expect fun lsxVbitclriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVbitclriH(a: V8u16, b: UInt): V8u16

public expect fun lsxVbitclriW(a: V4u32, b: UInt): V4u32

public expect fun lsxVbitclriD(a: V2u64, b: UInt): V2u64

public expect fun lsxVbitsetiB(a: V16u8, b: UInt): V16u8

public expect fun lsxVbitsetiH(a: V8u16, b: UInt): V8u16

public expect fun lsxVbitsetiW(a: V4u32, b: UInt): V4u32

public expect fun lsxVbitsetiD(a: V2u64, b: UInt): V2u64

public expect fun lsxVbitreviB(a: V16u8, b: UInt): V16u8

public expect fun lsxVbitreviH(a: V8u16, b: UInt): V8u16

public expect fun lsxVbitreviW(a: V4u32, b: UInt): V4u32

public expect fun lsxVbitreviD(a: V2u64, b: UInt): V2u64

public expect fun lsxVsubiBu(a: V16i8, b: UInt): V16i8

public expect fun lsxVsubiHu(a: V8i16, b: UInt): V8i16

public expect fun lsxVsubiWu(a: V4i32, b: UInt): V4i32

public expect fun lsxVsubiDu(a: V2i64, b: UInt): V2i64

public expect fun lsxVsatB(a: V16i8, b: UInt): V16i8

public expect fun lsxVsatH(a: V8i16, b: UInt): V8i16

public expect fun lsxVsatW(a: V4i32, b: UInt): V4i32

public expect fun lsxVsatD(a: V2i64, b: UInt): V2i64

public expect fun lsxVsatBu(a: V16u8, b: UInt): V16u8

public expect fun lsxVsatHu(a: V8u16, b: UInt): V8u16

public expect fun lsxVsatWu(a: V4u32, b: UInt): V4u32

public expect fun lsxVsatDu(a: V2u64, b: UInt): V2u64

public expect fun lsxVdivB(a: V16i8, b: V16i8): V16i8

public expect fun lsxVdivH(a: V8i16, b: V8i16): V8i16

public expect fun lsxVdivW(a: V4i32, b: V4i32): V4i32

public expect fun lsxVdivD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVdivBu(a: V16u8, b: V16u8): V16u8

public expect fun lsxVdivHu(a: V8u16, b: V8u16): V8u16

public expect fun lsxVdivWu(a: V4u32, b: V4u32): V4u32

public expect fun lsxVdivDu(a: V2u64, b: V2u64): V2u64

public expect fun lsxVhaddwHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVhaddwWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVhaddwDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVhaddwHuBu(a: V16u8, b: V16u8): V8u16

public expect fun lsxVhaddwWuHu(a: V8u16, b: V8u16): V4u32

public expect fun lsxVhaddwDuWu(a: V4u32, b: V4u32): V2u64

public expect fun lsxVhsubwHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVhsubwWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVhsubwDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVhsubwHuBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVhsubwWuHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVhsubwDuWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVmodB(a: V16i8, b: V16i8): V16i8

public expect fun lsxVmodH(a: V8i16, b: V8i16): V8i16

public expect fun lsxVmodW(a: V4i32, b: V4i32): V4i32

public expect fun lsxVmodD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVmodBu(a: V16u8, b: V16u8): V16u8

public expect fun lsxVmodHu(a: V8u16, b: V8u16): V8u16

public expect fun lsxVmodWu(a: V4u32, b: V4u32): V4u32

public expect fun lsxVmodDu(a: V2u64, b: V2u64): V2u64

public expect fun lsxVreplveB(a: V16i8, b: Int): V16i8

public expect fun lsxVreplveH(a: V8i16, b: Int): V8i16

public expect fun lsxVreplveW(a: V4i32, b: Int): V4i32

public expect fun lsxVreplveD(a: V2i64, b: Int): V2i64

public expect fun lsxVshufH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun lsxVshufW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun lsxVshufD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun lsxVandiB(a: V16u8, b: UInt): V16u8

public expect fun lsxVoriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVnoriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVxoriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun lsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8

public expect fun lsxVcloB(a: V16i8): V16i8

public expect fun lsxVcloH(a: V8i16): V8i16

public expect fun lsxVcloW(a: V4i32): V4i32

public expect fun lsxVcloD(a: V2i64): V2i64

public expect fun lsxVfcvtHS(a: V4f32, b: V4f32): V8i16

public expect fun lsxVfcvtSD(a: V2f64, b: V2f64): V4f32

public expect fun lsxVfminS(a: V4f32, b: V4f32): V4f32

public expect fun lsxVfminD(a: V2f64, b: V2f64): V2f64

public expect fun lsxVfminaS(a: V4f32, b: V4f32): V4f32

public expect fun lsxVfminaD(a: V2f64, b: V2f64): V2f64

public expect fun lsxVfmaxS(a: V4f32, b: V4f32): V4f32

public expect fun lsxVfmaxD(a: V2f64, b: V2f64): V2f64

public expect fun lsxVfmaxaS(a: V4f32, b: V4f32): V4f32

public expect fun lsxVfmaxaD(a: V2f64, b: V2f64): V2f64

public expect fun lsxVfclassS(a: V4f32): V4i32

public expect fun lsxVfclassD(a: V2f64): V2i64

public expect fun lsxVfrecipeS(a: V4f32): V4f32

public expect fun lsxVfrecipeD(a: V2f64): V2f64

public expect fun lsxVfrsqrteS(a: V4f32): V4f32

public expect fun lsxVfrsqrteD(a: V2f64): V2f64

public expect fun lsxVfrintS(a: V4f32): V4f32

public expect fun lsxVfrintD(a: V2f64): V2f64

public expect fun lsxVflogbS(a: V4f32): V4f32

public expect fun lsxVflogbD(a: V2f64): V2f64

public expect fun lsxVfcvthSH(a: V8i16): V4f32

public expect fun lsxVfcvthDS(a: V4f32): V2f64

public expect fun lsxVfcvtlSH(a: V8i16): V4f32

public expect fun lsxVfcvtlDS(a: V4f32): V2f64

public expect fun lsxVftintWS(a: V4f32): V4i32

public expect fun lsxVftintLD(a: V2f64): V2i64

public expect fun lsxVftintWuS(a: V4f32): V4u32

public expect fun lsxVftintLuD(a: V2f64): V2u64

public expect fun lsxVftintrzWS(a: V4f32): V4i32

public expect fun lsxVftintrzLD(a: V2f64): V2i64

public expect fun lsxVftintrzWuS(a: V4f32): V4u32

public expect fun lsxVftintrzLuD(a: V2f64): V2u64

public expect fun lsxVffintSW(a: V4i32): V4f32

public expect fun lsxVffintDL(a: V2i64): V2f64

public expect fun lsxVffintSWu(a: V4u32): V4f32

public expect fun lsxVffintDLu(a: V2u64): V2f64

public expect fun lsxVsllwilHB(a: V16i8, b: UInt): V8i16

public expect fun lsxVsllwilWH(a: V8i16, b: UInt): V4i32

public expect fun lsxVsllwilDW(a: V4i32, b: UInt): V2i64

public expect fun lsxVsllwilHuBu(a: V16u8, b: UInt): V8u16

public expect fun lsxVsllwilWuHu(a: V8u16, b: UInt): V4u32

public expect fun lsxVsllwilDuWu(a: V4u32, b: UInt): V2u64

public expect fun lsxVsranBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVsranHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVsranWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssranBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVssranHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVssranWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssranBuH(a: V8u16, b: V8u16): V16u8

public expect fun lsxVssranHuW(a: V4u32, b: V4u32): V8u16

public expect fun lsxVssranWuD(a: V2u64, b: V2u64): V4u32

public expect fun lsxVsrarnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVsrarnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVsrarnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssrarnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVssrarnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVssrarnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssrarnBuH(a: V8u16, b: V8u16): V16u8

public expect fun lsxVssrarnHuW(a: V4u32, b: V4u32): V8u16

public expect fun lsxVssrarnWuD(a: V2u64, b: V2u64): V4u32

public expect fun lsxVsrlnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVsrlnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVsrlnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssrlnBuH(a: V8u16, b: V8u16): V16u8

public expect fun lsxVssrlnHuW(a: V4u32, b: V4u32): V8u16

public expect fun lsxVssrlnWuD(a: V2u64, b: V2u64): V4u32

public expect fun lsxVsrlrnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVsrlrnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVsrlrnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssrlrnBuH(a: V8u16, b: V8u16): V16u8

public expect fun lsxVssrlrnHuW(a: V4u32, b: V4u32): V8u16

public expect fun lsxVssrlrnWuD(a: V2u64, b: V2u64): V4u32

public expect fun lsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun lsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun lsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVmskltzB(a: V16i8): V16i8

public expect fun lsxVmskltzH(a: V8i16): V8i16

public expect fun lsxVmskltzW(a: V4i32): V4i32

public expect fun lsxVmskltzD(a: V2i64): V2i64

public expect fun lsxVsigncovB(a: V16i8, b: V16i8): V16i8

public expect fun lsxVsigncovH(a: V8i16, b: V8i16): V8i16

public expect fun lsxVsigncovW(a: V4i32, b: V4i32): V4i32

public expect fun lsxVsigncovD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVftintrneWS(a: V4f32): V4i32

public expect fun lsxVftintrneLD(a: V2f64): V2i64

public expect fun lsxVftintrpWS(a: V4f32): V4i32

public expect fun lsxVftintrpLD(a: V2f64): V2i64

public expect fun lsxVftintrmWS(a: V4f32): V4i32

public expect fun lsxVftintrmLD(a: V2f64): V2i64

public expect fun lsxVftintWD(a: V2f64, b: V2f64): V4i32

public expect fun lsxVffintSL(a: V2i64, b: V2i64): V4f32

public expect fun lsxVftintrzWD(a: V2f64, b: V2f64): V4i32

public expect fun lsxVftintrpWD(a: V2f64, b: V2f64): V4i32

public expect fun lsxVftintrmWD(a: V2f64, b: V2f64): V4i32

public expect fun lsxVftintrneWD(a: V2f64, b: V2f64): V4i32

public expect fun lsxVftintlLS(a: V4f32): V2i64

public expect fun lsxVftinthLS(a: V4f32): V2i64

public expect fun lsxVffinthDW(a: V4i32): V2f64

public expect fun lsxVffintlDW(a: V4i32): V2f64

public expect fun lsxVftintrzlLS(a: V4f32): V2i64

public expect fun lsxVftintrzhLS(a: V4f32): V2i64

public expect fun lsxVftintrplLS(a: V4f32): V2i64

public expect fun lsxVftintrphLS(a: V4f32): V2i64

public expect fun lsxVftintrmlLS(a: V4f32): V2i64

public expect fun lsxVftintrmhLS(a: V4f32): V2i64

public expect fun lsxVftintrnelLS(a: V4f32): V2i64

public expect fun lsxVftintrnehLS(a: V4f32): V2i64

public expect fun lsxVfrintrneS(a: V4f32): V4f32

public expect fun lsxVfrintrneD(a: V2f64): V2f64

public expect fun lsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVaddwevDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVaddwevWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVaddwevHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVaddwodDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVaddwodWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVaddwodHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVaddwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVaddwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVaddwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVaddwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVaddwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVaddwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVaddwevDWuW(a: V4u32, b: V4i32): V2i64

public expect fun lsxVaddwevWHuH(a: V8u16, b: V8i16): V4i32

public expect fun lsxVaddwevHBuB(a: V16u8, b: V16i8): V8i16

public expect fun lsxVaddwodDWuW(a: V4u32, b: V4i32): V2i64

public expect fun lsxVaddwodWHuH(a: V8u16, b: V8i16): V4i32

public expect fun lsxVaddwodHBuB(a: V16u8, b: V16i8): V8i16

public expect fun lsxVsubwevDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVsubwevWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVsubwevHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVsubwodDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVsubwodWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVsubwodHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVsubwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVsubwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVsubwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVsubwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVsubwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVsubwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVaddwevQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVaddwodQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVaddwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVaddwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVsubwevQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsubwodQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsubwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVsubwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVaddwevQDuD(a: V2u64, b: V2i64): V2i64

public expect fun lsxVaddwodQDuD(a: V2u64, b: V2i64): V2i64

public expect fun lsxVmulwevDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVmulwevWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVmulwevHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVmulwodDW(a: V4i32, b: V4i32): V2i64

public expect fun lsxVmulwodWH(a: V8i16, b: V8i16): V4i32

public expect fun lsxVmulwodHB(a: V16i8, b: V16i8): V8i16

public expect fun lsxVmulwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVmulwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVmulwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVmulwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun lsxVmulwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun lsxVmulwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun lsxVmulwevDWuW(a: V4u32, b: V4i32): V2i64

public expect fun lsxVmulwevWHuH(a: V8u16, b: V8i16): V4i32

public expect fun lsxVmulwevHBuB(a: V16u8, b: V16i8): V8i16

public expect fun lsxVmulwodDWuW(a: V4u32, b: V4i32): V2i64

public expect fun lsxVmulwodWHuH(a: V8u16, b: V8i16): V4i32

public expect fun lsxVmulwodHBuB(a: V16u8, b: V16i8): V8i16

public expect fun lsxVmulwevQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVmulwodQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVmulwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVmulwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun lsxVmulwevQDuD(a: V2u64, b: V2i64): V2i64

public expect fun lsxVmulwodQDuD(a: V2u64, b: V2i64): V2i64

public expect fun lsxVhaddwQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVhaddwQuDu(a: V2u64, b: V2u64): V2u64

public expect fun lsxVhsubwQD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVhsubwQuDu(a: V2u64, b: V2u64): V2u64

public expect fun lsxVmaddwevDW(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun lsxVmaddwevWH(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun lsxVmaddwevHB(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun lsxVmaddwevDWu(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun lsxVmaddwevWHu(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun lsxVmaddwevHBu(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun lsxVmaddwodDW(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun lsxVmaddwodWH(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun lsxVmaddwodHB(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun lsxVmaddwodDWu(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun lsxVmaddwodWHu(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun lsxVmaddwodHBu(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun lsxVmaddwevDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64

public expect fun lsxVmaddwevWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32

public expect fun lsxVmaddwevHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16

public expect fun lsxVmaddwodDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64

public expect fun lsxVmaddwodWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32

public expect fun lsxVmaddwodHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16

public expect fun lsxVmaddwevQD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun lsxVmaddwodQD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun lsxVmaddwevQDu(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun lsxVmaddwodQDu(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun lsxVmaddwevQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64

public expect fun lsxVmaddwodQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64

public expect fun lsxVaddQ(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsubQ(a: V2i64, b: V2i64): V2i64

public expect fun lsxVldreplB(a: ConstPtr, b: Int): V16i8

public expect fun lsxVldreplH(a: ConstPtr, b: Int): V8i16

public expect fun lsxVldreplW(a: ConstPtr, b: Int): V4i32

public expect fun lsxVldreplD(a: ConstPtr, b: Int): V2i64

public expect fun lsxVmskgezB(a: V16i8): V16i8

public expect fun lsxVmsknzB(a: V16i8): V16i8

public expect fun lsxVexthHB(a: V16i8): V8i16

public expect fun lsxVexthWH(a: V8i16): V4i32

public expect fun lsxVexthDW(a: V4i32): V2i64

public expect fun lsxVexthQD(a: V2i64): V2i64

public expect fun lsxVexthHuBu(a: V16u8): V8u16

public expect fun lsxVexthWuHu(a: V8u16): V4u32

public expect fun lsxVexthDuWu(a: V4u32): V2u64

public expect fun lsxVexthQuDu(a: V2u64): V2u64

public expect fun lsxVextlQD(a: V2i64): V2i64

public expect fun lsxVsrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVsrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVsrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVsrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVsrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVsrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVsrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVsrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVssrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVssrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVssrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssrlniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun lsxVssrlniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun lsxVssrlniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun lsxVssrlniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun lsxVssrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVssrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVssrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVssrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssrlrniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun lsxVssrlrniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun lsxVssrlrniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun lsxVssrlrniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun lsxVsraniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVsraniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVsraniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVsraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVsrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVsrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVsrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVsrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssraniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVssraniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVssraniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVssraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssraniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun lsxVssraniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun lsxVssraniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun lsxVssraniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun lsxVssrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVssrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVssrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVssrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun lsxVssrarniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun lsxVssrarniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun lsxVssrarniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun lsxVssrarniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun lsxVpermiW(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVssrlrnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVssrlrnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVssrlrnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVssrlnBH(a: V8i16, b: V8i16): V16i8

public expect fun lsxVssrlnHW(a: V4i32, b: V4i32): V8i16

public expect fun lsxVssrlnWD(a: V2i64, b: V2i64): V4i32

public expect fun lsxVshufB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun lsxVextlQuDu(a: V2u64): V2u64

public expect fun lsxBnzB(a: V16u8): Int

public expect fun lsxBnzD(a: V2u64): Int

public expect fun lsxBnzH(a: V8u16): Int

public expect fun lsxBnzV(a: V16u8): Int

public expect fun lsxBnzW(a: V4u32): Int

public expect fun lsxBzB(a: V16u8): Int

public expect fun lsxBzD(a: V2u64): Int

public expect fun lsxBzH(a: V8u16): Int

public expect fun lsxBzV(a: V16u8): Int

public expect fun lsxBzW(a: V4u32): Int

public expect fun lsxVfcmpCafD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCafS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCeqD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCeqS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCleD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCleS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCltD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCltS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCneD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCneS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCorD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCorS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCueqD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCueqS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCuleD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCuleS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCultD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCultS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCunD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCuneD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpCuneS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpCunS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSafD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSafS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSeqD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSeqS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSleD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSleS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSltD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSltS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSneD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSneS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSorD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSorS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSueqD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSueqS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSuleD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSuleS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSultD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSultS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSunD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSuneD(a: V2f64, b: V2f64): V2i64

public expect fun lsxVfcmpSuneS(a: V4f32, b: V4f32): V4i32

public expect fun lsxVfcmpSunS(a: V4f32, b: V4f32): V4i32

public fun lsxVsrarB(a: M128i, b: M128i): M128i = transmute(lsxVsrarB(transmute(a), transmute(b)))

public fun lsxVsrarH(a: M128i, b: M128i): M128i = transmute(lsxVsrarH(transmute(a), transmute(b)))

public fun lsxVsrarW(a: M128i, b: M128i): M128i = transmute(lsxVsrarW(transmute(a), transmute(b)))

public fun lsxVsrarD(a: M128i, b: M128i): M128i = transmute(lsxVsrarD(transmute(a), transmute(b)))

public fun lsxVsrariB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsrariB(transmute(a), imm3))
}

public fun lsxVsrariH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsrariH(transmute(a), imm4))
}

public fun lsxVsrariW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsrariW(transmute(a), imm5))
}

public fun lsxVsrariD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsrariD(transmute(a), imm6))
}

public fun lsxVsrlrB(a: M128i, b: M128i): M128i = transmute(lsxVsrlrB(transmute(a), transmute(b)))

public fun lsxVsrlrH(a: M128i, b: M128i): M128i = transmute(lsxVsrlrH(transmute(a), transmute(b)))

public fun lsxVsrlrW(a: M128i, b: M128i): M128i = transmute(lsxVsrlrW(transmute(a), transmute(b)))

public fun lsxVsrlrD(a: M128i, b: M128i): M128i = transmute(lsxVsrlrD(transmute(a), transmute(b)))

public fun lsxVsrlriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsrlriB(transmute(a), imm3))
}

public fun lsxVsrlriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsrlriH(transmute(a), imm4))
}

public fun lsxVsrlriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsrlriW(transmute(a), imm5))
}

public fun lsxVsrlriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsrlriD(transmute(a), imm6))
}

public fun lsxVbitclriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVbitclriB(transmute(a), imm3))
}

public fun lsxVbitclriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVbitclriH(transmute(a), imm4))
}

public fun lsxVbitclriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVbitclriW(transmute(a), imm5))
}

public fun lsxVbitclriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVbitclriD(transmute(a), imm6))
}

public fun lsxVbitsetiB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVbitsetiB(transmute(a), imm3))
}

public fun lsxVbitsetiH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVbitsetiH(transmute(a), imm4))
}

public fun lsxVbitsetiW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVbitsetiW(transmute(a), imm5))
}

public fun lsxVbitsetiD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVbitsetiD(transmute(a), imm6))
}

public fun lsxVbitreviB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVbitreviB(transmute(a), imm3))
}

public fun lsxVbitreviH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVbitreviH(transmute(a), imm4))
}

public fun lsxVbitreviW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVbitreviW(transmute(a), imm5))
}

public fun lsxVbitreviD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVbitreviD(transmute(a), imm6))
}

public fun lsxVsubiBu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsubiBu(transmute(a), imm5))
}

public fun lsxVsubiHu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsubiHu(transmute(a), imm5))
}

public fun lsxVsubiWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsubiWu(transmute(a), imm5))
}

public fun lsxVsubiDu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsubiDu(transmute(a), imm5))
}

public fun lsxVsatB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsatB(transmute(a), imm3))
}

public fun lsxVsatH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsatH(transmute(a), imm4))
}

public fun lsxVsatW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsatW(transmute(a), imm5))
}

public fun lsxVsatD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsatD(transmute(a), imm6))
}

public fun lsxVsatBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsatBu(transmute(a), imm3))
}

public fun lsxVsatHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsatHu(transmute(a), imm4))
}

public fun lsxVsatWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsatWu(transmute(a), imm5))
}

public fun lsxVsatDu(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsatDu(transmute(a), imm6))
}

public fun lsxVdivB(a: M128i, b: M128i): M128i = transmute(lsxVdivB(transmute(a), transmute(b)))

public fun lsxVdivH(a: M128i, b: M128i): M128i = transmute(lsxVdivH(transmute(a), transmute(b)))

public fun lsxVdivW(a: M128i, b: M128i): M128i = transmute(lsxVdivW(transmute(a), transmute(b)))

public fun lsxVdivD(a: M128i, b: M128i): M128i = transmute(lsxVdivD(transmute(a), transmute(b)))

public fun lsxVdivBu(a: M128i, b: M128i): M128i = transmute(lsxVdivBu(transmute(a), transmute(b)))

public fun lsxVdivHu(a: M128i, b: M128i): M128i = transmute(lsxVdivHu(transmute(a), transmute(b)))

public fun lsxVdivWu(a: M128i, b: M128i): M128i = transmute(lsxVdivWu(transmute(a), transmute(b)))

public fun lsxVdivDu(a: M128i, b: M128i): M128i = transmute(lsxVdivDu(transmute(a), transmute(b)))

public fun lsxVhaddwHB(a: M128i, b: M128i): M128i = transmute(lsxVhaddwHB(transmute(a), transmute(b)))

public fun lsxVhaddwWH(a: M128i, b: M128i): M128i = transmute(lsxVhaddwWH(transmute(a), transmute(b)))

public fun lsxVhaddwDW(a: M128i, b: M128i): M128i = transmute(lsxVhaddwDW(transmute(a), transmute(b)))

public fun lsxVhaddwHuBu(a: M128i, b: M128i): M128i = transmute(lsxVhaddwHuBu(transmute(a), transmute(b)))

public fun lsxVhaddwWuHu(a: M128i, b: M128i): M128i = transmute(lsxVhaddwWuHu(transmute(a), transmute(b)))

public fun lsxVhaddwDuWu(a: M128i, b: M128i): M128i = transmute(lsxVhaddwDuWu(transmute(a), transmute(b)))

public fun lsxVhsubwHB(a: M128i, b: M128i): M128i = transmute(lsxVhsubwHB(transmute(a), transmute(b)))

public fun lsxVhsubwWH(a: M128i, b: M128i): M128i = transmute(lsxVhsubwWH(transmute(a), transmute(b)))

public fun lsxVhsubwDW(a: M128i, b: M128i): M128i = transmute(lsxVhsubwDW(transmute(a), transmute(b)))

public fun lsxVhsubwHuBu(a: M128i, b: M128i): M128i = transmute(lsxVhsubwHuBu(transmute(a), transmute(b)))

public fun lsxVhsubwWuHu(a: M128i, b: M128i): M128i = transmute(lsxVhsubwWuHu(transmute(a), transmute(b)))

public fun lsxVhsubwDuWu(a: M128i, b: M128i): M128i = transmute(lsxVhsubwDuWu(transmute(a), transmute(b)))

public fun lsxVmodB(a: M128i, b: M128i): M128i = transmute(lsxVmodB(transmute(a), transmute(b)))

public fun lsxVmodH(a: M128i, b: M128i): M128i = transmute(lsxVmodH(transmute(a), transmute(b)))

public fun lsxVmodW(a: M128i, b: M128i): M128i = transmute(lsxVmodW(transmute(a), transmute(b)))

public fun lsxVmodD(a: M128i, b: M128i): M128i = transmute(lsxVmodD(transmute(a), transmute(b)))

public fun lsxVmodBu(a: M128i, b: M128i): M128i = transmute(lsxVmodBu(transmute(a), transmute(b)))

public fun lsxVmodHu(a: M128i, b: M128i): M128i = transmute(lsxVmodHu(transmute(a), transmute(b)))

public fun lsxVmodWu(a: M128i, b: M128i): M128i = transmute(lsxVmodWu(transmute(a), transmute(b)))

public fun lsxVmodDu(a: M128i, b: M128i): M128i = transmute(lsxVmodDu(transmute(a), transmute(b)))

public fun lsxVreplveB(a: M128i, b: Int): M128i = transmute(lsxVreplveB(transmute(a), transmute(b)))

public fun lsxVreplveH(a: M128i, b: Int): M128i = transmute(lsxVreplveH(transmute(a), transmute(b)))

public fun lsxVreplveW(a: M128i, b: Int): M128i = transmute(lsxVreplveW(transmute(a), transmute(b)))

public fun lsxVreplveD(a: M128i, b: Int): M128i = transmute(lsxVreplveD(transmute(a), transmute(b)))

public fun lsxVshufH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVshufH(transmute(a), transmute(b), transmute(c)))

public fun lsxVshufW(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVshufW(transmute(a), transmute(b), transmute(c)))

public fun lsxVshufD(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVshufD(transmute(a), transmute(b), transmute(c)))

public fun lsxVandiB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVandiB(transmute(a), imm8))
}

public fun lsxVoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVoriB(transmute(a), imm8))
}

public fun lsxVnoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVnoriB(transmute(a), imm8))
}

public fun lsxVxoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVxoriB(transmute(a), imm8))
}

public fun lsxVbitselV(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVbitselV(transmute(a), transmute(b), transmute(c)))

public fun lsxVbitseliB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVbitseliB(transmute(a), transmute(b), imm8))
}

public fun lsxVcloB(a: M128i): M128i = transmute(lsxVcloB(transmute(a)))

public fun lsxVcloH(a: M128i): M128i = transmute(lsxVcloH(transmute(a)))

public fun lsxVcloW(a: M128i): M128i = transmute(lsxVcloW(transmute(a)))

public fun lsxVcloD(a: M128i): M128i = transmute(lsxVcloD(transmute(a)))

public fun lsxVfcvtHS(a: M128, b: M128): M128i = transmute(lsxVfcvtHS(transmute(a), transmute(b)))

public fun lsxVfcvtSD(a: M128d, b: M128d): M128 = transmute(lsxVfcvtSD(transmute(a), transmute(b)))

public fun lsxVfminS(a: M128, b: M128): M128 = transmute(lsxVfminS(transmute(a), transmute(b)))

public fun lsxVfminD(a: M128d, b: M128d): M128d = transmute(lsxVfminD(transmute(a), transmute(b)))

public fun lsxVfminaS(a: M128, b: M128): M128 = transmute(lsxVfminaS(transmute(a), transmute(b)))

public fun lsxVfminaD(a: M128d, b: M128d): M128d = transmute(lsxVfminaD(transmute(a), transmute(b)))

public fun lsxVfmaxS(a: M128, b: M128): M128 = transmute(lsxVfmaxS(transmute(a), transmute(b)))

public fun lsxVfmaxD(a: M128d, b: M128d): M128d = transmute(lsxVfmaxD(transmute(a), transmute(b)))

public fun lsxVfmaxaS(a: M128, b: M128): M128 = transmute(lsxVfmaxaS(transmute(a), transmute(b)))

public fun lsxVfmaxaD(a: M128d, b: M128d): M128d = transmute(lsxVfmaxaD(transmute(a), transmute(b)))

public fun lsxVfclassS(a: M128): M128i = transmute(lsxVfclassS(transmute(a)))

public fun lsxVfclassD(a: M128d): M128i = transmute(lsxVfclassD(transmute(a)))

public fun lsxVfrecipeS(a: M128): M128 = transmute(lsxVfrecipeS(transmute(a)))

public fun lsxVfrecipeD(a: M128d): M128d = transmute(lsxVfrecipeD(transmute(a)))

public fun lsxVfrsqrteS(a: M128): M128 = transmute(lsxVfrsqrteS(transmute(a)))

public fun lsxVfrsqrteD(a: M128d): M128d = transmute(lsxVfrsqrteD(transmute(a)))

public fun lsxVfrintS(a: M128): M128 = transmute(lsxVfrintS(transmute(a)))

public fun lsxVfrintD(a: M128d): M128d = transmute(lsxVfrintD(transmute(a)))

public fun lsxVflogbS(a: M128): M128 = transmute(lsxVflogbS(transmute(a)))

public fun lsxVflogbD(a: M128d): M128d = transmute(lsxVflogbD(transmute(a)))

public fun lsxVfcvthSH(a: M128i): M128 = transmute(lsxVfcvthSH(transmute(a)))

public fun lsxVfcvthDS(a: M128): M128d = transmute(lsxVfcvthDS(transmute(a)))

public fun lsxVfcvtlSH(a: M128i): M128 = transmute(lsxVfcvtlSH(transmute(a)))

public fun lsxVfcvtlDS(a: M128): M128d = transmute(lsxVfcvtlDS(transmute(a)))

public fun lsxVftintWS(a: M128): M128i = transmute(lsxVftintWS(transmute(a)))

public fun lsxVftintLD(a: M128d): M128i = transmute(lsxVftintLD(transmute(a)))

public fun lsxVftintWuS(a: M128): M128i = transmute(lsxVftintWuS(transmute(a)))

public fun lsxVftintLuD(a: M128d): M128i = transmute(lsxVftintLuD(transmute(a)))

public fun lsxVftintrzWS(a: M128): M128i = transmute(lsxVftintrzWS(transmute(a)))

public fun lsxVftintrzLD(a: M128d): M128i = transmute(lsxVftintrzLD(transmute(a)))

public fun lsxVftintrzWuS(a: M128): M128i = transmute(lsxVftintrzWuS(transmute(a)))

public fun lsxVftintrzLuD(a: M128d): M128i = transmute(lsxVftintrzLuD(transmute(a)))

public fun lsxVffintSW(a: M128i): M128 = transmute(lsxVffintSW(transmute(a)))

public fun lsxVffintDL(a: M128i): M128d = transmute(lsxVffintDL(transmute(a)))

public fun lsxVffintSWu(a: M128i): M128 = transmute(lsxVffintSWu(transmute(a)))

public fun lsxVffintDLu(a: M128i): M128d = transmute(lsxVffintDLu(transmute(a)))

public fun lsxVsllwilHB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsllwilHB(transmute(a), imm3))
}

public fun lsxVsllwilWH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsllwilWH(transmute(a), imm4))
}

public fun lsxVsllwilDW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsllwilDW(transmute(a), imm5))
}

public fun lsxVsllwilHuBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lsxVsllwilHuBu(transmute(a), imm3))
}

public fun lsxVsllwilWuHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsllwilWuHu(transmute(a), imm4))
}

public fun lsxVsllwilDuWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsllwilDuWu(transmute(a), imm5))
}

public fun lsxVsranBH(a: M128i, b: M128i): M128i = transmute(lsxVsranBH(transmute(a), transmute(b)))

public fun lsxVsranHW(a: M128i, b: M128i): M128i = transmute(lsxVsranHW(transmute(a), transmute(b)))

public fun lsxVsranWD(a: M128i, b: M128i): M128i = transmute(lsxVsranWD(transmute(a), transmute(b)))

public fun lsxVssranBH(a: M128i, b: M128i): M128i = transmute(lsxVssranBH(transmute(a), transmute(b)))

public fun lsxVssranHW(a: M128i, b: M128i): M128i = transmute(lsxVssranHW(transmute(a), transmute(b)))

public fun lsxVssranWD(a: M128i, b: M128i): M128i = transmute(lsxVssranWD(transmute(a), transmute(b)))

public fun lsxVssranBuH(a: M128i, b: M128i): M128i = transmute(lsxVssranBuH(transmute(a), transmute(b)))

public fun lsxVssranHuW(a: M128i, b: M128i): M128i = transmute(lsxVssranHuW(transmute(a), transmute(b)))

public fun lsxVssranWuD(a: M128i, b: M128i): M128i = transmute(lsxVssranWuD(transmute(a), transmute(b)))

public fun lsxVsrarnBH(a: M128i, b: M128i): M128i = transmute(lsxVsrarnBH(transmute(a), transmute(b)))

public fun lsxVsrarnHW(a: M128i, b: M128i): M128i = transmute(lsxVsrarnHW(transmute(a), transmute(b)))

public fun lsxVsrarnWD(a: M128i, b: M128i): M128i = transmute(lsxVsrarnWD(transmute(a), transmute(b)))

public fun lsxVssrarnBH(a: M128i, b: M128i): M128i = transmute(lsxVssrarnBH(transmute(a), transmute(b)))

public fun lsxVssrarnHW(a: M128i, b: M128i): M128i = transmute(lsxVssrarnHW(transmute(a), transmute(b)))

public fun lsxVssrarnWD(a: M128i, b: M128i): M128i = transmute(lsxVssrarnWD(transmute(a), transmute(b)))

public fun lsxVssrarnBuH(a: M128i, b: M128i): M128i = transmute(lsxVssrarnBuH(transmute(a), transmute(b)))

public fun lsxVssrarnHuW(a: M128i, b: M128i): M128i = transmute(lsxVssrarnHuW(transmute(a), transmute(b)))

public fun lsxVssrarnWuD(a: M128i, b: M128i): M128i = transmute(lsxVssrarnWuD(transmute(a), transmute(b)))

public fun lsxVsrlnBH(a: M128i, b: M128i): M128i = transmute(lsxVsrlnBH(transmute(a), transmute(b)))

public fun lsxVsrlnHW(a: M128i, b: M128i): M128i = transmute(lsxVsrlnHW(transmute(a), transmute(b)))

public fun lsxVsrlnWD(a: M128i, b: M128i): M128i = transmute(lsxVsrlnWD(transmute(a), transmute(b)))

public fun lsxVssrlnBuH(a: M128i, b: M128i): M128i = transmute(lsxVssrlnBuH(transmute(a), transmute(b)))

public fun lsxVssrlnHuW(a: M128i, b: M128i): M128i = transmute(lsxVssrlnHuW(transmute(a), transmute(b)))

public fun lsxVssrlnWuD(a: M128i, b: M128i): M128i = transmute(lsxVssrlnWuD(transmute(a), transmute(b)))

public fun lsxVsrlrnBH(a: M128i, b: M128i): M128i = transmute(lsxVsrlrnBH(transmute(a), transmute(b)))

public fun lsxVsrlrnHW(a: M128i, b: M128i): M128i = transmute(lsxVsrlrnHW(transmute(a), transmute(b)))

public fun lsxVsrlrnWD(a: M128i, b: M128i): M128i = transmute(lsxVsrlrnWD(transmute(a), transmute(b)))

public fun lsxVssrlrnBuH(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnBuH(transmute(a), transmute(b)))

public fun lsxVssrlrnHuW(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnHuW(transmute(a), transmute(b)))

public fun lsxVssrlrnWuD(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnWuD(transmute(a), transmute(b)))

public fun lsxVfrstpiB(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVfrstpiB(transmute(a), transmute(b), imm5))
}

public fun lsxVfrstpiH(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVfrstpiH(transmute(a), transmute(b), imm5))
}

public fun lsxVfrstpB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVfrstpB(transmute(a), transmute(b), transmute(c)))

public fun lsxVfrstpH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVfrstpH(transmute(a), transmute(b), transmute(c)))

public fun lsxVextrinsB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVextrinsB(transmute(a), transmute(b), imm8))
}

public fun lsxVextrinsH(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVextrinsH(transmute(a), transmute(b), imm8))
}

public fun lsxVextrinsW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVextrinsW(transmute(a), transmute(b), imm8))
}

public fun lsxVextrinsD(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVextrinsD(transmute(a), transmute(b), imm8))
}

public fun lsxVmskltzB(a: M128i): M128i = transmute(lsxVmskltzB(transmute(a)))

public fun lsxVmskltzH(a: M128i): M128i = transmute(lsxVmskltzH(transmute(a)))

public fun lsxVmskltzW(a: M128i): M128i = transmute(lsxVmskltzW(transmute(a)))

public fun lsxVmskltzD(a: M128i): M128i = transmute(lsxVmskltzD(transmute(a)))

public fun lsxVsigncovB(a: M128i, b: M128i): M128i = transmute(lsxVsigncovB(transmute(a), transmute(b)))

public fun lsxVsigncovH(a: M128i, b: M128i): M128i = transmute(lsxVsigncovH(transmute(a), transmute(b)))

public fun lsxVsigncovW(a: M128i, b: M128i): M128i = transmute(lsxVsigncovW(transmute(a), transmute(b)))

public fun lsxVsigncovD(a: M128i, b: M128i): M128i = transmute(lsxVsigncovD(transmute(a), transmute(b)))

public fun lsxVftintrneWS(a: M128): M128i = transmute(lsxVftintrneWS(transmute(a)))

public fun lsxVftintrneLD(a: M128d): M128i = transmute(lsxVftintrneLD(transmute(a)))

public fun lsxVftintrpWS(a: M128): M128i = transmute(lsxVftintrpWS(transmute(a)))

public fun lsxVftintrpLD(a: M128d): M128i = transmute(lsxVftintrpLD(transmute(a)))

public fun lsxVftintrmWS(a: M128): M128i = transmute(lsxVftintrmWS(transmute(a)))

public fun lsxVftintrmLD(a: M128d): M128i = transmute(lsxVftintrmLD(transmute(a)))

public fun lsxVftintWD(a: M128d, b: M128d): M128i = transmute(lsxVftintWD(transmute(a), transmute(b)))

public fun lsxVffintSL(a: M128i, b: M128i): M128 = transmute(lsxVffintSL(transmute(a), transmute(b)))

public fun lsxVftintrzWD(a: M128d, b: M128d): M128i = transmute(lsxVftintrzWD(transmute(a), transmute(b)))

public fun lsxVftintrpWD(a: M128d, b: M128d): M128i = transmute(lsxVftintrpWD(transmute(a), transmute(b)))

public fun lsxVftintrmWD(a: M128d, b: M128d): M128i = transmute(lsxVftintrmWD(transmute(a), transmute(b)))

public fun lsxVftintrneWD(a: M128d, b: M128d): M128i = transmute(lsxVftintrneWD(transmute(a), transmute(b)))

public fun lsxVftintlLS(a: M128): M128i = transmute(lsxVftintlLS(transmute(a)))

public fun lsxVftinthLS(a: M128): M128i = transmute(lsxVftinthLS(transmute(a)))

public fun lsxVffinthDW(a: M128i): M128d = transmute(lsxVffinthDW(transmute(a)))

public fun lsxVffintlDW(a: M128i): M128d = transmute(lsxVffintlDW(transmute(a)))

public fun lsxVftintrzlLS(a: M128): M128i = transmute(lsxVftintrzlLS(transmute(a)))

public fun lsxVftintrzhLS(a: M128): M128i = transmute(lsxVftintrzhLS(transmute(a)))

public fun lsxVftintrplLS(a: M128): M128i = transmute(lsxVftintrplLS(transmute(a)))

public fun lsxVftintrphLS(a: M128): M128i = transmute(lsxVftintrphLS(transmute(a)))

public fun lsxVftintrmlLS(a: M128): M128i = transmute(lsxVftintrmlLS(transmute(a)))

public fun lsxVftintrmhLS(a: M128): M128i = transmute(lsxVftintrmhLS(transmute(a)))

public fun lsxVftintrnelLS(a: M128): M128i = transmute(lsxVftintrnelLS(transmute(a)))

public fun lsxVftintrnehLS(a: M128): M128i = transmute(lsxVftintrnehLS(transmute(a)))

public fun lsxVfrintrneS(a: M128): M128 = transmute(lsxVfrintrneS(transmute(a)))

public fun lsxVfrintrneD(a: M128d): M128d = transmute(lsxVfrintrneD(transmute(a)))

public fun lsxVstelmB(a: M128i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    lsxVstelmB(transmute(a), memAddr, immS8, imm4)
}

public fun lsxVstelmH(a: M128i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    lsxVstelmH(transmute(a), memAddr, immS8, imm3)
}

public fun lsxVstelmW(a: M128i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    lsxVstelmW(transmute(a), memAddr, immS8, imm2)
}

public fun lsxVstelmD(a: M128i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    lsxVstelmD(transmute(a), memAddr, immS8, imm1)
}

public fun lsxVaddwevDW(a: M128i, b: M128i): M128i = transmute(lsxVaddwevDW(transmute(a), transmute(b)))

public fun lsxVaddwevWH(a: M128i, b: M128i): M128i = transmute(lsxVaddwevWH(transmute(a), transmute(b)))

public fun lsxVaddwevHB(a: M128i, b: M128i): M128i = transmute(lsxVaddwevHB(transmute(a), transmute(b)))

public fun lsxVaddwodDW(a: M128i, b: M128i): M128i = transmute(lsxVaddwodDW(transmute(a), transmute(b)))

public fun lsxVaddwodWH(a: M128i, b: M128i): M128i = transmute(lsxVaddwodWH(transmute(a), transmute(b)))

public fun lsxVaddwodHB(a: M128i, b: M128i): M128i = transmute(lsxVaddwodHB(transmute(a), transmute(b)))

public fun lsxVaddwevDWu(a: M128i, b: M128i): M128i = transmute(lsxVaddwevDWu(transmute(a), transmute(b)))

public fun lsxVaddwevWHu(a: M128i, b: M128i): M128i = transmute(lsxVaddwevWHu(transmute(a), transmute(b)))

public fun lsxVaddwevHBu(a: M128i, b: M128i): M128i = transmute(lsxVaddwevHBu(transmute(a), transmute(b)))

public fun lsxVaddwodDWu(a: M128i, b: M128i): M128i = transmute(lsxVaddwodDWu(transmute(a), transmute(b)))

public fun lsxVaddwodWHu(a: M128i, b: M128i): M128i = transmute(lsxVaddwodWHu(transmute(a), transmute(b)))

public fun lsxVaddwodHBu(a: M128i, b: M128i): M128i = transmute(lsxVaddwodHBu(transmute(a), transmute(b)))

public fun lsxVaddwevDWuW(a: M128i, b: M128i): M128i = transmute(lsxVaddwevDWuW(transmute(a), transmute(b)))

public fun lsxVaddwevWHuH(a: M128i, b: M128i): M128i = transmute(lsxVaddwevWHuH(transmute(a), transmute(b)))

public fun lsxVaddwevHBuB(a: M128i, b: M128i): M128i = transmute(lsxVaddwevHBuB(transmute(a), transmute(b)))

public fun lsxVaddwodDWuW(a: M128i, b: M128i): M128i = transmute(lsxVaddwodDWuW(transmute(a), transmute(b)))

public fun lsxVaddwodWHuH(a: M128i, b: M128i): M128i = transmute(lsxVaddwodWHuH(transmute(a), transmute(b)))

public fun lsxVaddwodHBuB(a: M128i, b: M128i): M128i = transmute(lsxVaddwodHBuB(transmute(a), transmute(b)))

public fun lsxVsubwevDW(a: M128i, b: M128i): M128i = transmute(lsxVsubwevDW(transmute(a), transmute(b)))

public fun lsxVsubwevWH(a: M128i, b: M128i): M128i = transmute(lsxVsubwevWH(transmute(a), transmute(b)))

public fun lsxVsubwevHB(a: M128i, b: M128i): M128i = transmute(lsxVsubwevHB(transmute(a), transmute(b)))

public fun lsxVsubwodDW(a: M128i, b: M128i): M128i = transmute(lsxVsubwodDW(transmute(a), transmute(b)))

public fun lsxVsubwodWH(a: M128i, b: M128i): M128i = transmute(lsxVsubwodWH(transmute(a), transmute(b)))

public fun lsxVsubwodHB(a: M128i, b: M128i): M128i = transmute(lsxVsubwodHB(transmute(a), transmute(b)))

public fun lsxVsubwevDWu(a: M128i, b: M128i): M128i = transmute(lsxVsubwevDWu(transmute(a), transmute(b)))

public fun lsxVsubwevWHu(a: M128i, b: M128i): M128i = transmute(lsxVsubwevWHu(transmute(a), transmute(b)))

public fun lsxVsubwevHBu(a: M128i, b: M128i): M128i = transmute(lsxVsubwevHBu(transmute(a), transmute(b)))

public fun lsxVsubwodDWu(a: M128i, b: M128i): M128i = transmute(lsxVsubwodDWu(transmute(a), transmute(b)))

public fun lsxVsubwodWHu(a: M128i, b: M128i): M128i = transmute(lsxVsubwodWHu(transmute(a), transmute(b)))

public fun lsxVsubwodHBu(a: M128i, b: M128i): M128i = transmute(lsxVsubwodHBu(transmute(a), transmute(b)))

public fun lsxVaddwevQD(a: M128i, b: M128i): M128i = transmute(lsxVaddwevQD(transmute(a), transmute(b)))

public fun lsxVaddwodQD(a: M128i, b: M128i): M128i = transmute(lsxVaddwodQD(transmute(a), transmute(b)))

public fun lsxVaddwevQDu(a: M128i, b: M128i): M128i = transmute(lsxVaddwevQDu(transmute(a), transmute(b)))

public fun lsxVaddwodQDu(a: M128i, b: M128i): M128i = transmute(lsxVaddwodQDu(transmute(a), transmute(b)))

public fun lsxVsubwevQD(a: M128i, b: M128i): M128i = transmute(lsxVsubwevQD(transmute(a), transmute(b)))

public fun lsxVsubwodQD(a: M128i, b: M128i): M128i = transmute(lsxVsubwodQD(transmute(a), transmute(b)))

public fun lsxVsubwevQDu(a: M128i, b: M128i): M128i = transmute(lsxVsubwevQDu(transmute(a), transmute(b)))

public fun lsxVsubwodQDu(a: M128i, b: M128i): M128i = transmute(lsxVsubwodQDu(transmute(a), transmute(b)))

public fun lsxVaddwevQDuD(a: M128i, b: M128i): M128i = transmute(lsxVaddwevQDuD(transmute(a), transmute(b)))

public fun lsxVaddwodQDuD(a: M128i, b: M128i): M128i = transmute(lsxVaddwodQDuD(transmute(a), transmute(b)))

public fun lsxVmulwevDW(a: M128i, b: M128i): M128i = transmute(lsxVmulwevDW(transmute(a), transmute(b)))

public fun lsxVmulwevWH(a: M128i, b: M128i): M128i = transmute(lsxVmulwevWH(transmute(a), transmute(b)))

public fun lsxVmulwevHB(a: M128i, b: M128i): M128i = transmute(lsxVmulwevHB(transmute(a), transmute(b)))

public fun lsxVmulwodDW(a: M128i, b: M128i): M128i = transmute(lsxVmulwodDW(transmute(a), transmute(b)))

public fun lsxVmulwodWH(a: M128i, b: M128i): M128i = transmute(lsxVmulwodWH(transmute(a), transmute(b)))

public fun lsxVmulwodHB(a: M128i, b: M128i): M128i = transmute(lsxVmulwodHB(transmute(a), transmute(b)))

public fun lsxVmulwevDWu(a: M128i, b: M128i): M128i = transmute(lsxVmulwevDWu(transmute(a), transmute(b)))

public fun lsxVmulwevWHu(a: M128i, b: M128i): M128i = transmute(lsxVmulwevWHu(transmute(a), transmute(b)))

public fun lsxVmulwevHBu(a: M128i, b: M128i): M128i = transmute(lsxVmulwevHBu(transmute(a), transmute(b)))

public fun lsxVmulwodDWu(a: M128i, b: M128i): M128i = transmute(lsxVmulwodDWu(transmute(a), transmute(b)))

public fun lsxVmulwodWHu(a: M128i, b: M128i): M128i = transmute(lsxVmulwodWHu(transmute(a), transmute(b)))

public fun lsxVmulwodHBu(a: M128i, b: M128i): M128i = transmute(lsxVmulwodHBu(transmute(a), transmute(b)))

public fun lsxVmulwevDWuW(a: M128i, b: M128i): M128i = transmute(lsxVmulwevDWuW(transmute(a), transmute(b)))

public fun lsxVmulwevWHuH(a: M128i, b: M128i): M128i = transmute(lsxVmulwevWHuH(transmute(a), transmute(b)))

public fun lsxVmulwevHBuB(a: M128i, b: M128i): M128i = transmute(lsxVmulwevHBuB(transmute(a), transmute(b)))

public fun lsxVmulwodDWuW(a: M128i, b: M128i): M128i = transmute(lsxVmulwodDWuW(transmute(a), transmute(b)))

public fun lsxVmulwodWHuH(a: M128i, b: M128i): M128i = transmute(lsxVmulwodWHuH(transmute(a), transmute(b)))

public fun lsxVmulwodHBuB(a: M128i, b: M128i): M128i = transmute(lsxVmulwodHBuB(transmute(a), transmute(b)))

public fun lsxVmulwevQD(a: M128i, b: M128i): M128i = transmute(lsxVmulwevQD(transmute(a), transmute(b)))

public fun lsxVmulwodQD(a: M128i, b: M128i): M128i = transmute(lsxVmulwodQD(transmute(a), transmute(b)))

public fun lsxVmulwevQDu(a: M128i, b: M128i): M128i = transmute(lsxVmulwevQDu(transmute(a), transmute(b)))

public fun lsxVmulwodQDu(a: M128i, b: M128i): M128i = transmute(lsxVmulwodQDu(transmute(a), transmute(b)))

public fun lsxVmulwevQDuD(a: M128i, b: M128i): M128i = transmute(lsxVmulwevQDuD(transmute(a), transmute(b)))

public fun lsxVmulwodQDuD(a: M128i, b: M128i): M128i = transmute(lsxVmulwodQDuD(transmute(a), transmute(b)))

public fun lsxVhaddwQD(a: M128i, b: M128i): M128i = transmute(lsxVhaddwQD(transmute(a), transmute(b)))

public fun lsxVhaddwQuDu(a: M128i, b: M128i): M128i = transmute(lsxVhaddwQuDu(transmute(a), transmute(b)))

public fun lsxVhsubwQD(a: M128i, b: M128i): M128i = transmute(lsxVhsubwQD(transmute(a), transmute(b)))

public fun lsxVhsubwQuDu(a: M128i, b: M128i): M128i = transmute(lsxVhsubwQuDu(transmute(a), transmute(b)))

public fun lsxVmaddwevDW(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevDW(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevWH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevWH(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevHB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevHB(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevDWu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevDWu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevWHu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevWHu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevHBu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevHBu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodDW(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodDW(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodWH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodWH(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodHB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodHB(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodDWu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodDWu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodWHu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodWHu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodHBu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodHBu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevDWuW(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevDWuW(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevWHuH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevWHuH(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevHBuB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevHBuB(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodDWuW(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodDWuW(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodWHuH(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodWHuH(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodHBuB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodHBuB(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevQD(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevQD(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodQD(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodQD(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevQDu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevQDu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodQDu(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodQDu(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwevQDuD(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwevQDuD(transmute(a), transmute(b), transmute(c)))

public fun lsxVmaddwodQDuD(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVmaddwodQDuD(transmute(a), transmute(b), transmute(c)))

public fun lsxVaddQ(a: M128i, b: M128i): M128i = transmute(lsxVaddQ(transmute(a), transmute(b)))

public fun lsxVsubQ(a: M128i, b: M128i): M128i = transmute(lsxVsubQ(transmute(a), transmute(b)))

public fun lsxVldreplB(memAddr: ConstPtr, immS12: Int): M128i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmute(lsxVldreplB(memAddr, immS12))
}

public fun lsxVldreplH(memAddr: ConstPtr, immS11: Int): M128i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmute(lsxVldreplH(memAddr, immS11))
}

public fun lsxVldreplW(memAddr: ConstPtr, immS10: Int): M128i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmute(lsxVldreplW(memAddr, immS10))
}

public fun lsxVldreplD(memAddr: ConstPtr, immS9: Int): M128i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmute(lsxVldreplD(memAddr, immS9))
}

public fun lsxVmskgezB(a: M128i): M128i = transmute(lsxVmskgezB(transmute(a)))

public fun lsxVmsknzB(a: M128i): M128i = transmute(lsxVmsknzB(transmute(a)))

public fun lsxVexthHB(a: M128i): M128i = transmute(lsxVexthHB(transmute(a)))

public fun lsxVexthWH(a: M128i): M128i = transmute(lsxVexthWH(transmute(a)))

public fun lsxVexthDW(a: M128i): M128i = transmute(lsxVexthDW(transmute(a)))

public fun lsxVexthQD(a: M128i): M128i = transmute(lsxVexthQD(transmute(a)))

public fun lsxVexthHuBu(a: M128i): M128i = transmute(lsxVexthHuBu(transmute(a)))

public fun lsxVexthWuHu(a: M128i): M128i = transmute(lsxVexthWuHu(transmute(a)))

public fun lsxVexthDuWu(a: M128i): M128i = transmute(lsxVexthDuWu(transmute(a)))

public fun lsxVexthQuDu(a: M128i): M128i = transmute(lsxVexthQuDu(transmute(a)))

public fun lsxVextlQD(a: M128i): M128i = transmute(lsxVextlQD(transmute(a)))

public fun lsxVsrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsrlniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVsrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsrlniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVsrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsrlniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVsrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVsrlniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVsrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsrlrniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVsrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsrlrniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVsrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsrlrniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVsrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVsrlrniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrlniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrlniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrlniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrlniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrlniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrlniBuH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrlniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrlniHuW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrlniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrlniWuD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrlniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrlniDuQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrlrniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniBuH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrlrniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniHuW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrlrniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniWuD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrlrniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrlrniDuQ(transmute(a), transmute(b), imm7))
}

public fun lsxVsraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsraniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVsraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsraniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVsraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsraniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVsraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVsraniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVsrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVsrarniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVsrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVsrarniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVsrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVsrarniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVsrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVsrarniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssraniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVssraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssraniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVssraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssraniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVssraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssraniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssraniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssraniBuH(transmute(a), transmute(b), imm4))
}

public fun lsxVssraniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssraniHuW(transmute(a), transmute(b), imm5))
}

public fun lsxVssraniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssraniWuD(transmute(a), transmute(b), imm6))
}

public fun lsxVssraniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssraniDuQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrarniBH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrarniHW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrarniWD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrarniDQ(transmute(a), transmute(b), imm7))
}

public fun lsxVssrarniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lsxVssrarniBuH(transmute(a), transmute(b), imm4))
}

public fun lsxVssrarniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lsxVssrarniHuW(transmute(a), transmute(b), imm5))
}

public fun lsxVssrarniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lsxVssrarniWuD(transmute(a), transmute(b), imm6))
}

public fun lsxVssrarniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lsxVssrarniDuQ(transmute(a), transmute(b), imm7))
}

public fun lsxVpermiW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lsxVpermiW(transmute(a), transmute(b), imm8))
}

public fun lsxVssrlrnBH(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnBH(transmute(a), transmute(b)))

public fun lsxVssrlrnHW(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnHW(transmute(a), transmute(b)))

public fun lsxVssrlrnWD(a: M128i, b: M128i): M128i = transmute(lsxVssrlrnWD(transmute(a), transmute(b)))

public fun lsxVssrlnBH(a: M128i, b: M128i): M128i = transmute(lsxVssrlnBH(transmute(a), transmute(b)))

public fun lsxVssrlnHW(a: M128i, b: M128i): M128i = transmute(lsxVssrlnHW(transmute(a), transmute(b)))

public fun lsxVssrlnWD(a: M128i, b: M128i): M128i = transmute(lsxVssrlnWD(transmute(a), transmute(b)))

public fun lsxVshufB(a: M128i, b: M128i, c: M128i): M128i = transmute(lsxVshufB(transmute(a), transmute(b), transmute(c)))

public fun lsxVextlQuDu(a: M128i): M128i = transmute(lsxVextlQuDu(transmute(a)))

public fun lsxBnzB(a: M128i): Int = transmute(lsxBnzB(transmute(a)))

public fun lsxBnzD(a: M128i): Int = transmute(lsxBnzD(transmute(a)))

public fun lsxBnzH(a: M128i): Int = transmute(lsxBnzH(transmute(a)))

public fun lsxBnzV(a: M128i): Int = transmute(lsxBnzV(transmute(a)))

public fun lsxBnzW(a: M128i): Int = transmute(lsxBnzW(transmute(a)))

public fun lsxBzB(a: M128i): Int = transmute(lsxBzB(transmute(a)))

public fun lsxBzD(a: M128i): Int = transmute(lsxBzD(transmute(a)))

public fun lsxBzH(a: M128i): Int = transmute(lsxBzH(transmute(a)))

public fun lsxBzV(a: M128i): Int = transmute(lsxBzV(transmute(a)))

public fun lsxBzW(a: M128i): Int = transmute(lsxBzW(transmute(a)))

public fun lsxVfcmpCafD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCafD(transmute(a), transmute(b)))

public fun lsxVfcmpCafS(a: M128, b: M128): M128i = transmute(lsxVfcmpCafS(transmute(a), transmute(b)))

public fun lsxVfcmpCeqD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCeqD(transmute(a), transmute(b)))

public fun lsxVfcmpCeqS(a: M128, b: M128): M128i = transmute(lsxVfcmpCeqS(transmute(a), transmute(b)))

public fun lsxVfcmpCleD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCleD(transmute(a), transmute(b)))

public fun lsxVfcmpCleS(a: M128, b: M128): M128i = transmute(lsxVfcmpCleS(transmute(a), transmute(b)))

public fun lsxVfcmpCltD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCltD(transmute(a), transmute(b)))

public fun lsxVfcmpCltS(a: M128, b: M128): M128i = transmute(lsxVfcmpCltS(transmute(a), transmute(b)))

public fun lsxVfcmpCneD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCneD(transmute(a), transmute(b)))

public fun lsxVfcmpCneS(a: M128, b: M128): M128i = transmute(lsxVfcmpCneS(transmute(a), transmute(b)))

public fun lsxVfcmpCorD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCorD(transmute(a), transmute(b)))

public fun lsxVfcmpCorS(a: M128, b: M128): M128i = transmute(lsxVfcmpCorS(transmute(a), transmute(b)))

public fun lsxVfcmpCueqD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCueqD(transmute(a), transmute(b)))

public fun lsxVfcmpCueqS(a: M128, b: M128): M128i = transmute(lsxVfcmpCueqS(transmute(a), transmute(b)))

public fun lsxVfcmpCuleD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCuleD(transmute(a), transmute(b)))

public fun lsxVfcmpCuleS(a: M128, b: M128): M128i = transmute(lsxVfcmpCuleS(transmute(a), transmute(b)))

public fun lsxVfcmpCultD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCultD(transmute(a), transmute(b)))

public fun lsxVfcmpCultS(a: M128, b: M128): M128i = transmute(lsxVfcmpCultS(transmute(a), transmute(b)))

public fun lsxVfcmpCunD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCunD(transmute(a), transmute(b)))

public fun lsxVfcmpCuneD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpCuneD(transmute(a), transmute(b)))

public fun lsxVfcmpCuneS(a: M128, b: M128): M128i = transmute(lsxVfcmpCuneS(transmute(a), transmute(b)))

public fun lsxVfcmpCunS(a: M128, b: M128): M128i = transmute(lsxVfcmpCunS(transmute(a), transmute(b)))

public fun lsxVfcmpSafD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSafD(transmute(a), transmute(b)))

public fun lsxVfcmpSafS(a: M128, b: M128): M128i = transmute(lsxVfcmpSafS(transmute(a), transmute(b)))

public fun lsxVfcmpSeqD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSeqD(transmute(a), transmute(b)))

public fun lsxVfcmpSeqS(a: M128, b: M128): M128i = transmute(lsxVfcmpSeqS(transmute(a), transmute(b)))

public fun lsxVfcmpSleD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSleD(transmute(a), transmute(b)))

public fun lsxVfcmpSleS(a: M128, b: M128): M128i = transmute(lsxVfcmpSleS(transmute(a), transmute(b)))

public fun lsxVfcmpSltD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSltD(transmute(a), transmute(b)))

public fun lsxVfcmpSltS(a: M128, b: M128): M128i = transmute(lsxVfcmpSltS(transmute(a), transmute(b)))

public fun lsxVfcmpSneD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSneD(transmute(a), transmute(b)))

public fun lsxVfcmpSneS(a: M128, b: M128): M128i = transmute(lsxVfcmpSneS(transmute(a), transmute(b)))

public fun lsxVfcmpSorD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSorD(transmute(a), transmute(b)))

public fun lsxVfcmpSorS(a: M128, b: M128): M128i = transmute(lsxVfcmpSorS(transmute(a), transmute(b)))

public fun lsxVfcmpSueqD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSueqD(transmute(a), transmute(b)))

public fun lsxVfcmpSueqS(a: M128, b: M128): M128i = transmute(lsxVfcmpSueqS(transmute(a), transmute(b)))

public fun lsxVfcmpSuleD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSuleD(transmute(a), transmute(b)))

public fun lsxVfcmpSuleS(a: M128, b: M128): M128i = transmute(lsxVfcmpSuleS(transmute(a), transmute(b)))

public fun lsxVfcmpSultD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSultD(transmute(a), transmute(b)))

public fun lsxVfcmpSultS(a: M128, b: M128): M128i = transmute(lsxVfcmpSultS(transmute(a), transmute(b)))

public fun lsxVfcmpSunD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSunD(transmute(a), transmute(b)))

public fun lsxVfcmpSuneD(a: M128d, b: M128d): M128i = transmute(lsxVfcmpSuneD(transmute(a), transmute(b)))

public fun lsxVfcmpSuneS(a: M128, b: M128): M128i = transmute(lsxVfcmpSuneS(transmute(a), transmute(b)))

public fun lsxVfcmpSunS(a: M128, b: M128): M128i = transmute(lsxVfcmpSunS(transmute(a), transmute(b)))

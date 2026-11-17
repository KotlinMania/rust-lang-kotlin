// port-lint: source loongarch64/lsx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public expect fun externLsxVsrarB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVsrarH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVsrarW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVsrarD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVsrariB(a: V16i8, b: UInt): V16i8

public expect fun externLsxVsrariH(a: V8i16, b: UInt): V8i16

public expect fun externLsxVsrariW(a: V4i32, b: UInt): V4i32

public expect fun externLsxVsrariD(a: V2i64, b: UInt): V2i64

public expect fun externLsxVsrlrB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVsrlrH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVsrlrW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVsrlrD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVsrlriB(a: V16i8, b: UInt): V16i8

public expect fun externLsxVsrlriH(a: V8i16, b: UInt): V8i16

public expect fun externLsxVsrlriW(a: V4i32, b: UInt): V4i32

public expect fun externLsxVsrlriD(a: V2i64, b: UInt): V2i64

public expect fun externLsxVbitclriB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVbitclriH(a: V8u16, b: UInt): V8u16

public expect fun externLsxVbitclriW(a: V4u32, b: UInt): V4u32

public expect fun externLsxVbitclriD(a: V2u64, b: UInt): V2u64

public expect fun externLsxVbitsetiB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVbitsetiH(a: V8u16, b: UInt): V8u16

public expect fun externLsxVbitsetiW(a: V4u32, b: UInt): V4u32

public expect fun externLsxVbitsetiD(a: V2u64, b: UInt): V2u64

public expect fun externLsxVbitreviB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVbitreviH(a: V8u16, b: UInt): V8u16

public expect fun externLsxVbitreviW(a: V4u32, b: UInt): V4u32

public expect fun externLsxVbitreviD(a: V2u64, b: UInt): V2u64

public expect fun externLsxVsubiBu(a: V16i8, b: UInt): V16i8

public expect fun externLsxVsubiHu(a: V8i16, b: UInt): V8i16

public expect fun externLsxVsubiWu(a: V4i32, b: UInt): V4i32

public expect fun externLsxVsubiDu(a: V2i64, b: UInt): V2i64

public expect fun externLsxVsatB(a: V16i8, b: UInt): V16i8

public expect fun externLsxVsatH(a: V8i16, b: UInt): V8i16

public expect fun externLsxVsatW(a: V4i32, b: UInt): V4i32

public expect fun externLsxVsatD(a: V2i64, b: UInt): V2i64

public expect fun externLsxVsatBu(a: V16u8, b: UInt): V16u8

public expect fun externLsxVsatHu(a: V8u16, b: UInt): V8u16

public expect fun externLsxVsatWu(a: V4u32, b: UInt): V4u32

public expect fun externLsxVsatDu(a: V2u64, b: UInt): V2u64

public expect fun externLsxVdivB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVdivH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVdivW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVdivD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVdivBu(a: V16u8, b: V16u8): V16u8

public expect fun externLsxVdivHu(a: V8u16, b: V8u16): V8u16

public expect fun externLsxVdivWu(a: V4u32, b: V4u32): V4u32

public expect fun externLsxVdivDu(a: V2u64, b: V2u64): V2u64

public expect fun externLsxVhaddwHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVhaddwWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVhaddwDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVhaddwHuBu(a: V16u8, b: V16u8): V8u16

public expect fun externLsxVhaddwWuHu(a: V8u16, b: V8u16): V4u32

public expect fun externLsxVhaddwDuWu(a: V4u32, b: V4u32): V2u64

public expect fun externLsxVhsubwHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVhsubwWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVhsubwDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVhsubwHuBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVhsubwWuHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVhsubwDuWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVmodB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVmodH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVmodW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVmodD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVmodBu(a: V16u8, b: V16u8): V16u8

public expect fun externLsxVmodHu(a: V8u16, b: V8u16): V8u16

public expect fun externLsxVmodWu(a: V4u32, b: V4u32): V4u32

public expect fun externLsxVmodDu(a: V2u64, b: V2u64): V2u64

public expect fun externLsxVreplveB(a: V16i8, b: Int): V16i8

public expect fun externLsxVreplveH(a: V8i16, b: Int): V8i16

public expect fun externLsxVreplveW(a: V4i32, b: Int): V4i32

public expect fun externLsxVreplveD(a: V2i64, b: Int): V2i64

public expect fun externLsxVshufH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externLsxVshufW(a: V4i32, b: V4i32, c: V4i32): V4i32

public expect fun externLsxVshufD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externLsxVandiB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVoriB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVnoriB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVxoriB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun externLsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8

public expect fun externLsxVcloB(a: V16i8): V16i8

public expect fun externLsxVcloH(a: V8i16): V8i16

public expect fun externLsxVcloW(a: V4i32): V4i32

public expect fun externLsxVcloD(a: V2i64): V2i64

public expect fun externLsxVfcvtHS(a: V4f32, b: V4f32): V8i16

public expect fun externLsxVfcvtSD(a: V2f64, b: V2f64): V4f32

public expect fun externLsxVfminS(a: V4f32, b: V4f32): V4f32

public expect fun externLsxVfminD(a: V2f64, b: V2f64): V2f64

public expect fun externLsxVfminaS(a: V4f32, b: V4f32): V4f32

public expect fun externLsxVfminaD(a: V2f64, b: V2f64): V2f64

public expect fun externLsxVfmaxS(a: V4f32, b: V4f32): V4f32

public expect fun externLsxVfmaxD(a: V2f64, b: V2f64): V2f64

public expect fun externLsxVfmaxaS(a: V4f32, b: V4f32): V4f32

public expect fun externLsxVfmaxaD(a: V2f64, b: V2f64): V2f64

public expect fun externLsxVfclassS(a: V4f32): V4i32

public expect fun externLsxVfclassD(a: V2f64): V2i64

public expect fun externLsxVfrecipeS(a: V4f32): V4f32

public expect fun externLsxVfrecipeD(a: V2f64): V2f64

public expect fun externLsxVfrsqrteS(a: V4f32): V4f32

public expect fun externLsxVfrsqrteD(a: V2f64): V2f64

public expect fun externLsxVfrintS(a: V4f32): V4f32

public expect fun externLsxVfrintD(a: V2f64): V2f64

public expect fun externLsxVflogbS(a: V4f32): V4f32

public expect fun externLsxVflogbD(a: V2f64): V2f64

public expect fun externLsxVfcvthSH(a: V8i16): V4f32

public expect fun externLsxVfcvthDS(a: V4f32): V2f64

public expect fun externLsxVfcvtlSH(a: V8i16): V4f32

public expect fun externLsxVfcvtlDS(a: V4f32): V2f64

public expect fun externLsxVftintWS(a: V4f32): V4i32

public expect fun externLsxVftintLD(a: V2f64): V2i64

public expect fun externLsxVftintWuS(a: V4f32): V4u32

public expect fun externLsxVftintLuD(a: V2f64): V2u64

public expect fun externLsxVftintrzWS(a: V4f32): V4i32

public expect fun externLsxVftintrzLD(a: V2f64): V2i64

public expect fun externLsxVftintrzWuS(a: V4f32): V4u32

public expect fun externLsxVftintrzLuD(a: V2f64): V2u64

public expect fun externLsxVffintSW(a: V4i32): V4f32

public expect fun externLsxVffintDL(a: V2i64): V2f64

public expect fun externLsxVffintSWu(a: V4u32): V4f32

public expect fun externLsxVffintDLu(a: V2u64): V2f64

public expect fun externLsxVsllwilHB(a: V16i8, b: UInt): V8i16

public expect fun externLsxVsllwilWH(a: V8i16, b: UInt): V4i32

public expect fun externLsxVsllwilDW(a: V4i32, b: UInt): V2i64

public expect fun externLsxVsllwilHuBu(a: V16u8, b: UInt): V8u16

public expect fun externLsxVsllwilWuHu(a: V8u16, b: UInt): V4u32

public expect fun externLsxVsllwilDuWu(a: V4u32, b: UInt): V2u64

public expect fun externLsxVsranBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVsranHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVsranWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssranBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVssranHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVssranWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssranBuH(a: V8u16, b: V8u16): V16u8

public expect fun externLsxVssranHuW(a: V4u32, b: V4u32): V8u16

public expect fun externLsxVssranWuD(a: V2u64, b: V2u64): V4u32

public expect fun externLsxVsrarnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVsrarnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVsrarnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssrarnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVssrarnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVssrarnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssrarnBuH(a: V8u16, b: V8u16): V16u8

public expect fun externLsxVssrarnHuW(a: V4u32, b: V4u32): V8u16

public expect fun externLsxVssrarnWuD(a: V2u64, b: V2u64): V4u32

public expect fun externLsxVsrlnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVsrlnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVsrlnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssrlnBuH(a: V8u16, b: V8u16): V16u8

public expect fun externLsxVssrlnHuW(a: V4u32, b: V4u32): V8u16

public expect fun externLsxVssrlnWuD(a: V2u64, b: V2u64): V4u32

public expect fun externLsxVsrlrnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVsrlrnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVsrlrnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssrlrnBuH(a: V8u16, b: V8u16): V16u8

public expect fun externLsxVssrlrnHuW(a: V4u32, b: V4u32): V8u16

public expect fun externLsxVssrlrnWuD(a: V2u64, b: V2u64): V4u32

public expect fun externLsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externLsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun externLsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVmskltzB(a: V16i8): V16i8

public expect fun externLsxVmskltzH(a: V8i16): V8i16

public expect fun externLsxVmskltzW(a: V4i32): V4i32

public expect fun externLsxVmskltzD(a: V2i64): V2i64

public expect fun externLsxVsigncovB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVsigncovH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVsigncovW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVsigncovD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVftintrneWS(a: V4f32): V4i32

public expect fun externLsxVftintrneLD(a: V2f64): V2i64

public expect fun externLsxVftintrpWS(a: V4f32): V4i32

public expect fun externLsxVftintrpLD(a: V2f64): V2i64

public expect fun externLsxVftintrmWS(a: V4f32): V4i32

public expect fun externLsxVftintrmLD(a: V2f64): V2i64

public expect fun externLsxVftintWD(a: V2f64, b: V2f64): V4i32

public expect fun externLsxVffintSL(a: V2i64, b: V2i64): V4f32

public expect fun externLsxVftintrzWD(a: V2f64, b: V2f64): V4i32

public expect fun externLsxVftintrpWD(a: V2f64, b: V2f64): V4i32

public expect fun externLsxVftintrmWD(a: V2f64, b: V2f64): V4i32

public expect fun externLsxVftintrneWD(a: V2f64, b: V2f64): V4i32

public expect fun externLsxVftintlLS(a: V4f32): V2i64

public expect fun externLsxVftinthLS(a: V4f32): V2i64

public expect fun externLsxVffinthDW(a: V4i32): V2f64

public expect fun externLsxVffintlDW(a: V4i32): V2f64

public expect fun externLsxVftintrzlLS(a: V4f32): V2i64

public expect fun externLsxVftintrzhLS(a: V4f32): V2i64

public expect fun externLsxVftintrplLS(a: V4f32): V2i64

public expect fun externLsxVftintrphLS(a: V4f32): V2i64

public expect fun externLsxVftintrmlLS(a: V4f32): V2i64

public expect fun externLsxVftintrmhLS(a: V4f32): V2i64

public expect fun externLsxVftintrnelLS(a: V4f32): V2i64

public expect fun externLsxVftintrnehLS(a: V4f32): V2i64

public expect fun externLsxVfrintrneS(a: V4f32): V4f32

public expect fun externLsxVfrintrneD(a: V2f64): V2f64

public expect fun externLsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt)

public expect fun externLsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt)

public expect fun externLsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt)

public expect fun externLsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt)

public expect fun externLsxVaddwevDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVaddwevWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVaddwevHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVaddwodDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVaddwodWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVaddwodHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVaddwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVaddwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVaddwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVaddwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVaddwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVaddwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVaddwevDWuW(a: V4u32, b: V4i32): V2i64

public expect fun externLsxVaddwevWHuH(a: V8u16, b: V8i16): V4i32

public expect fun externLsxVaddwevHBuB(a: V16u8, b: V16i8): V8i16

public expect fun externLsxVaddwodDWuW(a: V4u32, b: V4i32): V2i64

public expect fun externLsxVaddwodWHuH(a: V8u16, b: V8i16): V4i32

public expect fun externLsxVaddwodHBuB(a: V16u8, b: V16i8): V8i16

public expect fun externLsxVsubwevDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVsubwevWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVsubwevHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVsubwodDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVsubwodWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVsubwodHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVsubwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVsubwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVsubwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVsubwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVsubwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVsubwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVaddwevQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVaddwodQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVaddwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVaddwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVsubwevQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVsubwodQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVsubwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVsubwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVaddwevQDuD(a: V2u64, b: V2i64): V2i64

public expect fun externLsxVaddwodQDuD(a: V2u64, b: V2i64): V2i64

public expect fun externLsxVmulwevDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVmulwevWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVmulwevHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVmulwodDW(a: V4i32, b: V4i32): V2i64

public expect fun externLsxVmulwodWH(a: V8i16, b: V8i16): V4i32

public expect fun externLsxVmulwodHB(a: V16i8, b: V16i8): V8i16

public expect fun externLsxVmulwevDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVmulwevWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVmulwevHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVmulwodDWu(a: V4u32, b: V4u32): V2i64

public expect fun externLsxVmulwodWHu(a: V8u16, b: V8u16): V4i32

public expect fun externLsxVmulwodHBu(a: V16u8, b: V16u8): V8i16

public expect fun externLsxVmulwevDWuW(a: V4u32, b: V4i32): V2i64

public expect fun externLsxVmulwevWHuH(a: V8u16, b: V8i16): V4i32

public expect fun externLsxVmulwevHBuB(a: V16u8, b: V16i8): V8i16

public expect fun externLsxVmulwodDWuW(a: V4u32, b: V4i32): V2i64

public expect fun externLsxVmulwodWHuH(a: V8u16, b: V8i16): V4i32

public expect fun externLsxVmulwodHBuB(a: V16u8, b: V16i8): V8i16

public expect fun externLsxVmulwevQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVmulwodQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVmulwevQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVmulwodQDu(a: V2u64, b: V2u64): V2i64

public expect fun externLsxVmulwevQDuD(a: V2u64, b: V2i64): V2i64

public expect fun externLsxVmulwodQDuD(a: V2u64, b: V2i64): V2i64

public expect fun externLsxVhaddwQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVhaddwQuDu(a: V2u64, b: V2u64): V2u64

public expect fun externLsxVhsubwQD(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVhsubwQuDu(a: V2u64, b: V2u64): V2u64

public expect fun externLsxVmaddwevDW(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun externLsxVmaddwevWH(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun externLsxVmaddwevHB(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun externLsxVmaddwevDWu(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun externLsxVmaddwevWHu(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun externLsxVmaddwevHBu(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun externLsxVmaddwodDW(a: V2i64, b: V4i32, c: V4i32): V2i64

public expect fun externLsxVmaddwodWH(a: V4i32, b: V8i16, c: V8i16): V4i32

public expect fun externLsxVmaddwodHB(a: V8i16, b: V16i8, c: V16i8): V8i16

public expect fun externLsxVmaddwodDWu(a: V2u64, b: V4u32, c: V4u32): V2u64

public expect fun externLsxVmaddwodWHu(a: V4u32, b: V8u16, c: V8u16): V4u32

public expect fun externLsxVmaddwodHBu(a: V8u16, b: V16u8, c: V16u8): V8u16

public expect fun externLsxVmaddwevDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64

public expect fun externLsxVmaddwevWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32

public expect fun externLsxVmaddwevHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16

public expect fun externLsxVmaddwodDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64

public expect fun externLsxVmaddwodWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32

public expect fun externLsxVmaddwodHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16

public expect fun externLsxVmaddwevQD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externLsxVmaddwodQD(a: V2i64, b: V2i64, c: V2i64): V2i64

public expect fun externLsxVmaddwevQDu(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun externLsxVmaddwodQDu(a: V2u64, b: V2u64, c: V2u64): V2u64

public expect fun externLsxVmaddwevQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64

public expect fun externLsxVmaddwodQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64

public expect fun externLsxVaddQ(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVsubQ(a: V2i64, b: V2i64): V2i64

public expect fun externLsxVldreplB(a: ConstPtr, b: Int): V16i8

public expect fun externLsxVldreplH(a: ConstPtr, b: Int): V8i16

public expect fun externLsxVldreplW(a: ConstPtr, b: Int): V4i32

public expect fun externLsxVldreplD(a: ConstPtr, b: Int): V2i64

public expect fun externLsxVmskgezB(a: V16i8): V16i8

public expect fun externLsxVmsknzB(a: V16i8): V16i8

public expect fun externLsxVexthHB(a: V16i8): V8i16

public expect fun externLsxVexthWH(a: V8i16): V4i32

public expect fun externLsxVexthDW(a: V4i32): V2i64

public expect fun externLsxVexthQD(a: V2i64): V2i64

public expect fun externLsxVexthHuBu(a: V16u8): V8u16

public expect fun externLsxVexthWuHu(a: V8u16): V4u32

public expect fun externLsxVexthDuWu(a: V4u32): V2u64

public expect fun externLsxVexthQuDu(a: V2u64): V2u64

public expect fun externLsxVextlQD(a: V2i64): V2i64

public expect fun externLsxVsrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVsrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVsrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVsrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVsrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVsrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVsrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVsrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVssrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVssrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVssrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssrlniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun externLsxVssrlniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun externLsxVssrlniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun externLsxVssrlniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun externLsxVssrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVssrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVssrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVssrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssrlrniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun externLsxVssrlrniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun externLsxVssrlrniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun externLsxVssrlrniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun externLsxVsraniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVsraniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVsraniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVsraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVsrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVsrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVsrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVsrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssraniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVssraniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVssraniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVssraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssraniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun externLsxVssraniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun externLsxVssraniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun externLsxVssraniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun externLsxVssrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun externLsxVssrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVssrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVssrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64

public expect fun externLsxVssrarniBuH(a: V16u8, b: V16i8, c: UInt): V16u8

public expect fun externLsxVssrarniHuW(a: V8u16, b: V8i16, c: UInt): V8u16

public expect fun externLsxVssrarniWuD(a: V4u32, b: V4i32, c: UInt): V4u32

public expect fun externLsxVssrarniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64

public expect fun externLsxVpermiW(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun externLsxVssrlrnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVssrlrnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVssrlrnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVssrlnBH(a: V8i16, b: V8i16): V16i8

public expect fun externLsxVssrlnHW(a: V4i32, b: V4i32): V8i16

public expect fun externLsxVssrlnWD(a: V2i64, b: V2i64): V4i32

public expect fun externLsxVshufB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externLsxVextlQuDu(a: V2u64): V2u64

public expect fun externLsxBnzB(a: V16u8): Int

public expect fun externLsxBnzD(a: V2u64): Int

public expect fun externLsxBnzH(a: V8u16): Int

public expect fun externLsxBnzV(a: V16u8): Int

public expect fun externLsxBnzW(a: V4u32): Int

public expect fun externLsxBzB(a: V16u8): Int

public expect fun externLsxBzD(a: V2u64): Int

public expect fun externLsxBzH(a: V8u16): Int

public expect fun externLsxBzV(a: V16u8): Int

public expect fun externLsxBzW(a: V4u32): Int

public expect fun externLsxVfcmpCafD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCafS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCeqD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCeqS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCleD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCleS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCltD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCltS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCneD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCneS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCorD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCorS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCueqD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCueqS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCuleD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCuleS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCultD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCultS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCunD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCuneD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpCuneS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpCunS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSafD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSafS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSeqD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSeqS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSleD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSleS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSltD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSltS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSneD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSneS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSorD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSorS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSueqD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSueqS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSuleD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSuleS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSultD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSultS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSunD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSuneD(a: V2f64, b: V2f64): V2i64

public expect fun externLsxVfcmpSuneS(a: V4f32, b: V4f32): V4i32

public expect fun externLsxVfcmpSunS(a: V4f32, b: V4f32): V4i32

public fun lsxVsrarB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrarB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrarH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrarW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsrarD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrariB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsrariB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsrariH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsrariW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsrariD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlrB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlrH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlrW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsrlrD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsrlriB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsrlriH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsrlriW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsrlriD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVbitclriB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVbitclriH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVbitclriW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVbitclriD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVbitsetiB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVbitsetiH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVbitsetiW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVbitsetiD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVbitreviB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVbitreviH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVbitreviW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVbitreviD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiBu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsubiBu(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiHu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsubiHu(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsubiWu(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiDu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsubiDu(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsatB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsatH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsatW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsatD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVsatBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVsatHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVsatWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatDu(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVsatDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVdivB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVdivB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVdivH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVdivW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVdivD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVdivBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVdivHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVdivWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVdivDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhaddwHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhaddwWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhaddwDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVhaddwHuBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVhaddwWuHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhaddwDuWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhsubwHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhsubwWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhsubwHuBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhsubwWuHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwDuWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmodB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmodH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmodW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmodD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVmodBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmodHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmodWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmodDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveB(a: M128i, b: Int): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVreplveB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveH(a: M128i, b: Int): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVreplveH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveW(a: M128i, b: Int): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVreplveW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveD(a: M128i, b: Int): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVreplveD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVshufH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVshufW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVshufD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVandiB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVandiB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVoriB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVnoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVnoriB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVxoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVxoriB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitselV(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVbitselV(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVbitseliB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVbitseliB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVcloB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVcloB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVcloH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVcloW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVcloD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtHS(a: M128, b: M128): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVfcvtHS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtSD(a: M128d, b: M128d): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvtSD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfminS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfminD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfminaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfminaS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfminaD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfmaxS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfmaxD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfmaxaS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfmaxaD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfclassS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfclassS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfclassD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfclassD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrecipeS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrecipeS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrecipeD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrecipeD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrsqrteS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrsqrteS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrsqrteD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrsqrteD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrintS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrintS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrintD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVflogbS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVflogbS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVflogbD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVflogbD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvthSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvthSH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvthDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfcvthDS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvtlSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvtlSH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvtlDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfcvtlDS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintWS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintLD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVftintWuS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVftintLuD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrzWS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzLD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVftintrzWuS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVftintrzLuD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSW(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDL(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintDL(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintSWu(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDLu(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintDLu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVsllwilHB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsllwilHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilWH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsllwilWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilDW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsllwilDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilHuBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVsllwilHuBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), imm3), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilWuHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVsllwilWuHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), imm4), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilDuWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVsllwilDuWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), imm5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsranBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsranHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsranWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssranBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssranHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssranWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssranBuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssranHuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssranWuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrarnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrarnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrarnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrarnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrarnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrarnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrarnBuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrarnHuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrarnWuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrlnBuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrlnHuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrlnWuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlrnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlrnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlrnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrlrnBuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrlrnHuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrlrnWuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrstpiB(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVfrstpiB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVfrstpiH(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVfrstpiH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVfrstpB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVfrstpB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrstpH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVfrstpH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextrinsB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVextrinsB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm8), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsH(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVextrinsH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm8), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVextrinsW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm8), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsD(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVextrinsD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm8), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVmskltzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmskltzB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmskltzH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmskltzW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmskltzD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsigncovB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsigncovH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsigncovW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsigncovD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrneWS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrneLD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrpWS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrpLD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrmWS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmLD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintWD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSL(a: M128i, b: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSL(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVftintrzWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrzWD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrpWD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrmWD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrneWD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintlLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftinthLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftinthLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffinthDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffinthDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintlDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintlDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintrzlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzlLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzhLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrplLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrplLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrphLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrphLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmlLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmhLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnelLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrnelLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnehLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrnehLS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrintrneS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrintrneS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintrneD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrintrneD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVstelmB(a: M128i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    externLsxVstelmB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), memAddr, immS8, imm4)
}

public fun lsxVstelmH(a: M128i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    externLsxVstelmH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), memAddr, immS8, imm3)
}

public fun lsxVstelmW(a: M128i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    externLsxVstelmW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), memAddr, immS8, imm2)
}

public fun lsxVstelmD(a: M128i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    externLsxVstelmD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), memAddr, immS8, imm1)
}

public fun lsxVaddwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDWuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWHuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHBuB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDWuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWHuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHBuB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwevWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwevHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwodWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwodHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwevWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwevHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwodWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwodHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQDuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQDuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDWuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWHuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHBuB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDWuW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWHuH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHBuB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQDuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQDuD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhaddwQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhaddwQuDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhsubwQuDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevDW(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwevWH(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwevHB(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwevDWu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmaddwevWHu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmaddwevHBu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodDW(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwodWH(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwodHB(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwodDWu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4u32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmaddwodWHu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8u16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmaddwodHBu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16u8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevDWuW(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwevWHuH(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwevHBuB(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodDWuW(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V4u32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwodWHuH(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V8u16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwodHBuB(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V16u8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwevQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwodQDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2u64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevQDuD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodQDuD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2u64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVldreplB(memAddr: ConstPtr, immS12: Int): M128i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVldreplB(memAddr, immS12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplH(memAddr: ConstPtr, immS11: Int): M128i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVldreplH(memAddr, immS11), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplW(memAddr: ConstPtr, immS10: Int): M128i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVldreplW(memAddr, immS10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplD(memAddr: ConstPtr, immS9: Int): M128i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVldreplD(memAddr, immS9), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVmskgezB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmskgezB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmsknzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmsknzB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHB(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVexthHB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWH(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVexthWH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDW(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVexthDW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVexthQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHuBu(a: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVexthHuBu(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWuHu(a: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVexthWuHu(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDuWu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVexthDuWu(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVexthQuDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVextlQD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsrlniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsrlniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsrlniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsrlniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsrlrniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsrlrniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsrlrniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsrlrniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVssrlniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVssrlniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVssrlniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVssrlniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVssrlniBuH(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVssrlniHuW(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVssrlniWuD(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVssrlniDuQ(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVssrlrniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVssrlrniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVssrlrniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVssrlrniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVssrlrniBuH(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVssrlrniHuW(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVssrlrniWuD(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVssrlrniDuQ(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsraniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsraniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsraniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsraniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVsrarniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVsrarniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVsrarniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVsrarniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVssraniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVssraniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVssraniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVssraniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVssraniBuH(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVssraniHuW(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVssraniWuD(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVssraniDuQ(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(externLsxVssrarniBH(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(externLsxVssrarniHW(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVssrarniWD(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(externLsxVssrarniDQ(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(externLsxVssrarniBuH(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(externLsxVssrarniHuW(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(externLsxVssrarniWuD(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(externLsxVssrarniDuQ(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVpermiW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(externLsxVpermiW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), imm8), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrlrnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrlrnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrlrnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrlnBH(transmuteWithLayouts<M128i, V8i16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<M128i, V8i16>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrlnHW(transmuteWithLayouts<M128i, V4i32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<M128i, V4i32>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrlnWD(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVshufB(transmuteWithLayouts<M128i, V16i8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<M128i, V16i8>(c, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVextlQuDu(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxBnzB(a: M128i): Int = externLsxBnzB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()))

public fun lsxBnzD(a: M128i): Int = externLsxBnzD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()))

public fun lsxBnzH(a: M128i): Int = externLsxBnzH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()))

public fun lsxBnzV(a: M128i): Int = externLsxBnzV(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()))

public fun lsxBnzW(a: M128i): Int = externLsxBnzW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()))

public fun lsxBzB(a: M128i): Int = externLsxBzB(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()))

public fun lsxBzD(a: M128i): Int = externLsxBzD(transmuteWithLayouts<M128i, V2u64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2u64>()))

public fun lsxBzH(a: M128i): Int = externLsxBzH(transmuteWithLayouts<M128i, V8u16>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V8u16>()))

public fun lsxBzV(a: M128i): Int = externLsxBzV(transmuteWithLayouts<M128i, V16u8>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V16u8>()))

public fun lsxBzW(a: M128i): Int = externLsxBzW(transmuteWithLayouts<M128i, V4u32>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V4u32>()))

public fun lsxVfcmpCafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCafD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCafS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCeqD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCeqS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCleD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCleS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCltD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCltS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCneD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCneS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCorD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCorS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCueqD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCueqS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCuleD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCuleS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCultD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCultS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCunD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCuneD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCuneS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCunS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSafD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSafS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSeqD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSeqS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSleD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSleS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSltD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSltS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSneD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSneS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSorD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSorS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSueqD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSueqS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSuleD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSuleS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSultD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSultS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSunD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSuneD(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSuneS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSunS(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

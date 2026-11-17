// port-lint: source loongarch64/lsx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public expect fun externLsxVsrarB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVsrarH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVsrarW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVsrarD(a: V2i64, b: V2i64): V2i64

public expect fun lsxVsrariB(a: V16i8, b: UInt): V16i8

public expect fun lsxVsrariH(a: V8i16, b: UInt): V8i16

public expect fun lsxVsrariW(a: V4i32, b: UInt): V4i32

public expect fun lsxVsrariD(a: V2i64, b: UInt): V2i64

public expect fun externLsxVsrlrB(a: V16i8, b: V16i8): V16i8

public expect fun externLsxVsrlrH(a: V8i16, b: V8i16): V8i16

public expect fun externLsxVsrlrW(a: V4i32, b: V4i32): V4i32

public expect fun externLsxVsrlrD(a: V2i64, b: V2i64): V2i64

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

public expect fun lsxVandiB(a: V16u8, b: UInt): V16u8

public expect fun lsxVoriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVnoriB(a: V16u8, b: UInt): V16u8

public expect fun lsxVxoriB(a: V16u8, b: UInt): V16u8

public expect fun externLsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8

public expect fun lsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8

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

public expect fun lsxVsllwilHB(a: V16i8, b: UInt): V8i16

public expect fun lsxVsllwilWH(a: V8i16, b: UInt): V4i32

public expect fun lsxVsllwilDW(a: V4i32, b: UInt): V2i64

public expect fun lsxVsllwilHuBu(a: V16u8, b: UInt): V8u16

public expect fun lsxVsllwilWuHu(a: V8u16, b: UInt): V4u32

public expect fun lsxVsllwilDuWu(a: V4u32, b: UInt): V2u64

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

public expect fun lsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun externLsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8

public expect fun externLsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16

public expect fun lsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8

public expect fun lsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16

public expect fun lsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32

public expect fun lsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64

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

public expect fun lsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt)

public expect fun lsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt)

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

public expect fun lsxVldreplB(a: ConstPtr, b: Int): V16i8

public expect fun lsxVldreplH(a: ConstPtr, b: Int): V8i16

public expect fun lsxVldreplW(a: ConstPtr, b: Int): V4i32

public expect fun lsxVldreplD(a: ConstPtr, b: Int): V2i64

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

public fun lsxVsrarB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrarB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrarH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrarW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsrarD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrariB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsrariB(a, imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsrariH(a, imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsrariW(a, imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrariD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsrariD(a, imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlrB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlrH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlrW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsrlrD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsrlriB(a, imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsrlriH(a, imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsrlriW(a, imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsrlriD(a, imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVbitclriB(a, imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVbitclriH(a, imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVbitclriW(a, imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitclriD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVbitclriD(a, imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVbitsetiB(a, imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVbitsetiH(a, imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVbitsetiW(a, imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitsetiD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVbitsetiD(a, imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVbitreviB(a, imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVbitreviH(a, imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVbitreviW(a, imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitreviD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVbitreviD(a, imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiBu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsubiBu(a, imm5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiHu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsubiHu(a, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsubiWu(a, imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsubiDu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsubiDu(a, imm5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsatB(a, imm3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsatH(a, imm4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsatW(a, imm5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatD(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsatD(a, imm6), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVsatBu(a, imm3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVsatHu(a, imm4), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVsatWu(a, imm5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsatDu(a: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVsatDu(a, imm6), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVdivB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVdivB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVdivH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVdivW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVdivD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVdivBu(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVdivHu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVdivWu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVdivDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhaddwHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhaddwWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhaddwDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVhaddwHuBu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVhaddwWuHu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhaddwDuWu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhsubwHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhsubwWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVhsubwHuBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVhsubwWuHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwDuWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmodB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmodH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmodW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmodD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVmodBu(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmodHu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmodWu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmodDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveB(a: M128i, b: Int): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVreplveB(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveH(a: M128i, b: Int): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVreplveH(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveW(a: M128i, b: Int): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVreplveW(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveD(a: M128i, b: Int): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVreplveD(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVshufH(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVshufW(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVshufD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVandiB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVandiB(a, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVoriB(a, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVnoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVnoriB(a, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVxoriB(a: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVxoriB(a, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVbitselV(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVbitselV(a, b, c), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVbitseliB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVbitseliB(a, b, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVcloB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVcloB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVcloH(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVcloW(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVcloD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtHS(a: M128, b: M128): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVfcvtHS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtSD(a: M128d, b: M128d): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvtSD(transmuteWithLayouts<M128d, M128>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>()), transmuteWithLayouts<M128d, M128>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfminS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfminD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfminaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfminaS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfminaD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfmaxS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfmaxD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfmaxaS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfmaxaD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfclassS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfclassS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfclassD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfclassD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrecipeS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrecipeS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrecipeD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrecipeD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrsqrteS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrsqrteS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrsqrteD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrsqrteD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrintS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrintS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrintD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVflogbS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVflogbS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVflogbD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVflogbD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvthSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvthSH(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvthDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfcvthDS(transmuteWithLayouts<M128, M128d>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvtlSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfcvtlSH(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvtlDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfcvtlDS(transmuteWithLayouts<M128, M128d>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVftintWuS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVftintLuD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrzWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVftintrzWuS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVftintrzLuD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSW(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSW(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDL(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintDL(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintSWu(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSWu(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDLu(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintDLu(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVsllwilHB(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsllwilHB(a, imm3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilWH(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsllwilWH(a, imm4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilDW(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsllwilDW(a, imm5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilHuBu(a: M128i, imm3: UInt): M128i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVsllwilHuBu(a, imm3), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilWuHu(a: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVsllwilWuHu(a, imm4), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsllwilDuWu(a: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVsllwilDuWu(a, imm5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsranBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsranHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsranWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssranBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssranHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssranWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssranBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssranHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssranWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrarnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrarnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrarnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrarnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrarnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrarnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrarnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrarnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrarnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrlnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrlnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrlnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsrlrnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsrlrnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsrlrnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(externLsxVssrlrnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVssrlrnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVssrlrnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrstpiB(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVfrstpiB(a, b, imm5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVfrstpiH(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVfrstpiH(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVfrstpB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVfrstpB(a, b, c), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrstpH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVfrstpH(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextrinsB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVextrinsB(a, b, imm8), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsH(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVextrinsH(a, b, imm8), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVextrinsW(a, b, imm8), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVextrinsD(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVextrinsD(a, b, imm8), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVmskltzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmskltzB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmskltzH(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmskltzW(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmskltzD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVsigncovB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsigncovH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsigncovW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsigncovD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrneWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrneLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrpWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrpLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrmWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSL(a: M128i, b: M128i): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVffintSL(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>()), transmuteWithLayouts<M128i, M128>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVftintrzWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrzWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrpWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrmWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVftintrneWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftinthLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftinthLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffinthDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffinthDW(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintlDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVffintlDW(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintrzlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrzhLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrplLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrplLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrphLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrphLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrmhLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnelLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrnelLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnehLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVftintrnehLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrintrneS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(externLsxVfrintrneS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintrneD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(externLsxVfrintrneD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVstelmB(a: M128i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    lsxVstelmB(transmuteWithLayouts<M128i, Unit>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm4)
}

public fun lsxVstelmH(a: M128i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    lsxVstelmH(transmuteWithLayouts<M128i, Unit>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm3)
}

public fun lsxVstelmW(a: M128i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    lsxVstelmW(transmuteWithLayouts<M128i, Unit>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm2)
}

public fun lsxVstelmD(a: M128i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    lsxVstelmD(transmuteWithLayouts<M128i, Unit>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm1)
}

public fun lsxVaddwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwevWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwevHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVaddwodWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVaddwodHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVsubwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVsubwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwevQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddwodQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwevWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwevHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmulwodWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmulwodHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwevQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmulwodQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhaddwQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhaddwQuDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVhsubwQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVhsubwQuDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevDW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwevWH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwevHB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwevDWu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmaddwevWHu(a, b, c), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmaddwevHBu(a, b, c), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodDW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwodWH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwodHB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwodDWu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVmaddwodWHu(a, b, c), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVmaddwodHBu(a, b, c), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevDWuW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwevWHuH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwevHBuB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodDWuW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVmaddwodWHuH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVmaddwodHBuB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevQD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodQD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwevQDu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVmaddwodQDu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwevQDuD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVmaddwodQDuD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVaddQ(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVsubQ(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVldreplB(memAddr: ConstPtr, immS12: Int): M128i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVldreplB(memAddr, immS12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplH(memAddr: ConstPtr, immS11: Int): M128i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVldreplH(memAddr, immS11), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplW(memAddr: ConstPtr, immS10: Int): M128i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVldreplW(memAddr, immS10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVldreplD(memAddr: ConstPtr, immS9: Int): M128i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVldreplD(memAddr, immS9), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVmskgezB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmskgezB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmsknzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVmsknzB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHB(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVexthHB(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWH(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVexthWH(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDW(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVexthDW(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVexthQD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHuBu(a: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(externLsxVexthHuBu(a), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWuHu(a: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(externLsxVexthWuHu(a), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDuWu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVexthDuWu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVexthQuDu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVextlQD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsrlniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsrlniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsrlniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsrlniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsrlrniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsrlrniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsrlrniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsrlrniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVssrlniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVssrlniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVssrlniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVssrlniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVssrlniBuH(a, b, imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVssrlniHuW(a, b, imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVssrlniWuD(a, b, imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVssrlniDuQ(a, b, imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVssrlrniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVssrlrniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVssrlrniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVssrlrniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVssrlrniBuH(a, b, imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVssrlrniHuW(a, b, imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVssrlrniWuD(a, b, imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVssrlrniDuQ(a, b, imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsraniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsraniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsraniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsraniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVsrarniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVsrarniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVsrarniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVsrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVsrarniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVssraniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVssraniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVssraniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVssraniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVssraniBuH(a, b, imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVssraniHuW(a, b, imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVssraniWuD(a, b, imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssraniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVssraniDuQ(a, b, imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniBH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i8, M128i>(lsxVssrarniBH(a, b, imm4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniHW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i16, M128i>(lsxVssrarniHW(a, b, imm5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniWD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVssrarniWD(a, b, imm6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniDQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2i64, M128i>(lsxVssrarniDQ(a, b, imm7), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniBuH(a: M128i, b: M128i, imm4: UInt): M128i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVssrarniBuH(a, b, imm4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniHuW(a: M128i, b: M128i, imm5: UInt): M128i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u16, M128i>(lsxVssrarniHuW(a, b, imm5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniWuD(a: M128i, b: M128i, imm6: UInt): M128i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u32, M128i>(lsxVssrarniWuD(a, b, imm6), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrarniDuQ(a: M128i, b: M128i, imm7: UInt): M128i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V2u64, M128i>(lsxVssrarniDuQ(a, b, imm7), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVpermiW(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i32, M128i>(lsxVpermiW(a, b, imm8), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVssrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrlrnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrlrnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrlrnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVssrlnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(externLsxVssrlnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVssrlnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(externLsxVshufB(a, b, c), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(externLsxVextlQuDu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxBnzB(a: M128i): Int = externLsxBnzB(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzD(a: M128i): Int = externLsxBnzD(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzH(a: M128i): Int = externLsxBnzH(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzV(a: M128i): Int = externLsxBnzV(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzW(a: M128i): Int = externLsxBnzW(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzB(a: M128i): Int = externLsxBzB(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzD(a: M128i): Int = externLsxBzD(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzH(a: M128i): Int = externLsxBzH(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzV(a: M128i): Int = externLsxBzV(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzW(a: M128i): Int = externLsxBzW(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxVfcmpCafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCafD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCafS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCeqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCeqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCltD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCltS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCorD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCorS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCueqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCueqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCuleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCuleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCultD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCultS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCunD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpCuneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCuneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpCunS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSafD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSafS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSeqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSeqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSltD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSltS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSorD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSorS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSueqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSueqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSuleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSuleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSultD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSultS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSunD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(externLsxVfcmpSuneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSuneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(externLsxVfcmpSunS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

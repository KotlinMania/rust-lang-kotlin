// port-lint: source loongarch64/lsx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
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

public fun lsxVsrarB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsrarB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsrarH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsrarW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsrarD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVsrlrB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsrlrB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsrlrH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsrlrW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsrlrD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVdivB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVdivB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVdivH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVdivW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVdivD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVdivBu(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVdivHu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVdivWu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVdivDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVdivDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVhaddwHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVhaddwWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVhaddwDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVhaddwHuBu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVhaddwWuHu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVhaddwDuWu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVhsubwHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVhsubwWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVhsubwDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwHuBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVhsubwHuBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwWuHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVhsubwWuHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwDuWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVhsubwDuWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVmodB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmodH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmodW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmodD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVmodBu(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVmodHu(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVmodWu(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmodDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVmodDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveB(a: M128i, b: Int): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVreplveB(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveH(a: M128i, b: Int): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVreplveH(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveW(a: M128i, b: Int): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVreplveW(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVreplveD(a: M128i, b: Int): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVreplveD(a, transmuteWithLayouts<Int, M128i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVshufH(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVshufW(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVshufD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVbitselV(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVbitselV(a, b, c), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVbitseliB(a: M128i, b: M128i, imm8: UInt): M128i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u8, M128i>(lsxVbitseliB(a, b, imm8), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())
}

public fun lsxVcloB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVcloB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVcloH(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVcloW(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVcloD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVcloD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtHS(a: M128, b: M128): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVfcvtHS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcvtSD(a: M128d, b: M128d): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfcvtSD(transmuteWithLayouts<M128d, M128>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>()), transmuteWithLayouts<M128d, M128>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfminS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfminD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfminaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfminaS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfminaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfminaD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfmaxS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfmaxD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfmaxaS(a: M128, b: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfmaxaS(a, b), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfmaxaD(a: M128d, b: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfmaxaD(a, b), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfclassS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfclassS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfclassD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfclassD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrecipeS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfrecipeS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrecipeD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfrecipeD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrsqrteS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfrsqrteS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrsqrteD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfrsqrteD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfrintS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfrintS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfrintD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVflogbS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVflogbS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVflogbD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVflogbD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvthSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfcvthSH(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvthDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfcvthDS(transmuteWithLayouts<M128, M128d>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVfcvtlSH(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfcvtlSH(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfcvtlDS(a: M128): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfcvtlDS(transmuteWithLayouts<M128, M128d>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVftintWuS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVftintLuD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrzWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrzLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzWuS(a: M128): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVftintrzWuS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzLuD(a: M128d): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVftintrzLuD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSW(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(lsxVffintSW(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDL(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVffintDL(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintSWu(a: M128i): M128 = transmuteWithLayouts<V4f32, M128>(lsxVffintSWu(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVffintDLu(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVffintDLu(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

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

public fun lsxVsranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsranBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsranHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsranWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVssranBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVssranHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVssranWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVssranBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVssranHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssranWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVssranWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsrarnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsrarnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsrarnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVssrarnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVssrarnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVssrarnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVssrarnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVssrarnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrarnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVssrarnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsrlnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsrlnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsrlnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVssrlnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVssrlnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVssrlnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsrlrnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsrlrnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsrlrnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnBuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16u8, M128i>(lsxVssrlrnBuH(a, b), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVssrlrnHuW(a, b), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVssrlrnWuD(a, b), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVfrstpB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVfrstpB(a, b, c), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrstpH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVfrstpH(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVmskltzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVmskltzB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzH(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmskltzH(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzW(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmskltzW(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmskltzD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmskltzD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVsigncovB(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsigncovH(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsigncovW(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsigncovD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsigncovD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrneWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrneLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrpWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrpLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWS(a: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrmWS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmLD(a: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrmLD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffintSL(a: M128i, b: M128i): M128 = transmuteWithLayouts<V4f32, M128>(lsxVffintSL(transmuteWithLayouts<M128i, M128>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>()), transmuteWithLayouts<M128i, M128>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVftintrzWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrzWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrpWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrpWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrmWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrneWD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVftintrneWD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftinthLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftinthLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVffinthDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVffinthDW(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVffintlDW(a: M128i): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVffintlDW(transmuteWithLayouts<M128i, M128d>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lsxVftintrzlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrzlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrzhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrzhLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrplLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrplLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrphLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrphLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmlLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrmlLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrmhLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrmhLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnelLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrnelLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVftintrnehLS(a: M128): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVftintrnehLS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfrintrneS(a: M128): M128 = transmuteWithLayouts<V4f32, M128>(lsxVfrintrneS(a), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lsxVfrintrneD(a: M128d): M128d = transmuteWithLayouts<V2f64, M128d>(lsxVfrintrneD(a), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

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

public fun lsxVaddwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwevWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwevHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVaddwodWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVaddwodHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsubwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsubwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsubwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsubwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsubwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsubwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVsubwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVsubwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwevQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddwodQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwevWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwevHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodDW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwodWH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwodHB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwevWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwevHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodDWu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwodWHu(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwodHBu(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwevWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwevHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodDWuW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodDWuW(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodWHuH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmulwodWHuH(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodHBuB(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmulwodHBuB(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodQDu(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwevQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwevQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmulwodQDuD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmulwodQDuD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVhaddwQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhaddwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVhaddwQuDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVhsubwQD(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVhsubwQuDu(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVhsubwQuDu(a, b), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwevDW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmaddwevWH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmaddwevHB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVmaddwevDWu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVmaddwevWHu(a, b, c), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVmaddwevHBu(a, b, c), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwodDW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmaddwodWH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmaddwodHB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVmaddwodDWu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVmaddwodWHu(a, b, c), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVmaddwodHBu(a, b, c), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwevDWuW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmaddwevWHuH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmaddwevHBuB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodDWuW(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwodDWuW(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodWHuH(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVmaddwodWHuH(a, b, c), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodHBuB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVmaddwodHBuB(a, b, c), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwevQD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwodQD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVmaddwevQDu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDu(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVmaddwodQDu(a, b, c), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwevQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwevQDuD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmaddwodQDuD(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVmaddwodQDuD(a, b, c), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVaddQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVaddQ(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVsubQ(a: M128i, b: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVsubQ(a, b), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVmskgezB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVmskgezB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVmsknzB(a: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVmsknzB(a), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHB(a: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVexthHB(a), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWH(a: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVexthWH(a), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDW(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVexthDW(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVexthQD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthHuBu(a: M128i): M128i = transmuteWithLayouts<V8u16, M128i>(lsxVexthHuBu(a), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthWuHu(a: M128i): M128i = transmuteWithLayouts<V4u32, M128i>(lsxVexthWuHu(a), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthDuWu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVexthDuWu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVexthQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVexthQuDu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQD(a: M128i): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVextlQD(a), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

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

public fun lsxVssrlrnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVssrlrnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVssrlrnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlrnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVssrlrnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnBH(a: M128i, b: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVssrlnBH(a, b), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnHW(a: M128i, b: M128i): M128i = transmuteWithLayouts<V8i16, M128i>(lsxVssrlnHW(a, b), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<M128i>())

public fun lsxVssrlnWD(a: M128i, b: M128i): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVssrlnWD(a, b), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVshufB(a: M128i, b: M128i, c: M128i): M128i = transmuteWithLayouts<V16i8, M128i>(lsxVshufB(a, b, c), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<M128i>())

public fun lsxVextlQuDu(a: M128i): M128i = transmuteWithLayouts<V2u64, M128i>(lsxVextlQuDu(a), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<M128i>())

public fun lsxBnzB(a: M128i): Int = lsxBnzB(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzD(a: M128i): Int = lsxBnzD(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzH(a: M128i): Int = lsxBnzH(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzV(a: M128i): Int = lsxBnzV(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBnzW(a: M128i): Int = lsxBnzW(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzB(a: M128i): Int = lsxBzB(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzD(a: M128i): Int = lsxBzD(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzH(a: M128i): Int = lsxBzH(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzV(a: M128i): Int = lsxBzV(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxBzW(a: M128i): Int = lsxBzW(transmuteWithLayouts<M128i, Int>(a, UnresolvedTypeLayout<M128i>(), PrimitiveLayouts.I32))

public fun lsxVfcmpCafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCafD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCafS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCeqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCeqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCltD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCltS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCorD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCorS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCueqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCueqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCuleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCuleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCultD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCultS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCunD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpCuneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCuneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpCunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpCunS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSafD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSafS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSafS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSeqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSeqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSeqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSltD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSltS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSltS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSorD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSorS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSorS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSueqD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSueqS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSueqS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSuleD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuleS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSuleS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSultD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSultS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSultS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSunD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneD(a: M128d, b: M128d): M128i = transmuteWithLayouts<V2i64, M128i>(lsxVfcmpSuneD(transmuteWithLayouts<M128d, M128i>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128d, M128i>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSuneS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSuneS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

public fun lsxVfcmpSunS(a: M128, b: M128): M128i = transmuteWithLayouts<V4i32, M128i>(lsxVfcmpSunS(transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>()), transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<M128i>())

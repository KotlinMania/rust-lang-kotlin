// port-lint: source loongarch64/lasx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32

public expect fun externLasxXvsrarB(a: V32i8, b: V32i8): V32i8

public expect fun externLasxXvsrarH(a: V16i16, b: V16i16): V16i16

public expect fun externLasxXvsrarW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvsrarD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvsrariB(a: V32i8, b: UInt): V32i8

public expect fun externLasxXvsrariH(a: V16i16, b: UInt): V16i16

public expect fun externLasxXvsrariW(a: V8i32, b: UInt): V8i32

public expect fun externLasxXvsrariD(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvsrlrB(a: V32i8, b: V32i8): V32i8

public expect fun externLasxXvsrlrH(a: V16i16, b: V16i16): V16i16

public expect fun externLasxXvsrlrW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvsrlrD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvsrlriB(a: V32i8, b: UInt): V32i8

public expect fun externLasxXvsrlriH(a: V16i16, b: UInt): V16i16

public expect fun externLasxXvsrlriW(a: V8i32, b: UInt): V8i32

public expect fun externLasxXvsrlriD(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvbitclriB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvbitclriH(a: V16u16, b: UInt): V16u16

public expect fun externLasxXvbitclriW(a: V8u32, b: UInt): V8u32

public expect fun externLasxXvbitclriD(a: V4u64, b: UInt): V4u64

public expect fun externLasxXvbitsetiB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvbitsetiH(a: V16u16, b: UInt): V16u16

public expect fun externLasxXvbitsetiW(a: V8u32, b: UInt): V8u32

public expect fun externLasxXvbitsetiD(a: V4u64, b: UInt): V4u64

public expect fun externLasxXvbitreviB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvbitreviH(a: V16u16, b: UInt): V16u16

public expect fun externLasxXvbitreviW(a: V8u32, b: UInt): V8u32

public expect fun externLasxXvbitreviD(a: V4u64, b: UInt): V4u64

public expect fun externLasxXvsubiBu(a: V32i8, b: UInt): V32i8

public expect fun externLasxXvsubiHu(a: V16i16, b: UInt): V16i16

public expect fun externLasxXvsubiWu(a: V8i32, b: UInt): V8i32

public expect fun externLasxXvsubiDu(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvsatB(a: V32i8, b: UInt): V32i8

public expect fun externLasxXvsatH(a: V16i16, b: UInt): V16i16

public expect fun externLasxXvsatW(a: V8i32, b: UInt): V8i32

public expect fun externLasxXvsatD(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvsatBu(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvsatHu(a: V16u16, b: UInt): V16u16

public expect fun externLasxXvsatWu(a: V8u32, b: UInt): V8u32

public expect fun externLasxXvsatDu(a: V4u64, b: UInt): V4u64

public expect fun externLasxXvdivB(a: V32i8, b: V32i8): V32i8

public expect fun externLasxXvdivH(a: V16i16, b: V16i16): V16i16

public expect fun externLasxXvdivW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvdivD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvdivBu(a: V32u8, b: V32u8): V32u8

public expect fun externLasxXvdivHu(a: V16u16, b: V16u16): V16u16

public expect fun externLasxXvdivWu(a: V8u32, b: V8u32): V8u32

public expect fun externLasxXvdivDu(a: V4u64, b: V4u64): V4u64

public expect fun externLasxXvhaddwHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvhaddwWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvhaddwDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16

public expect fun externLasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32

public expect fun externLasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64

public expect fun externLasxXvhsubwHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvhsubwWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvhsubwDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvmodB(a: V32i8, b: V32i8): V32i8

public expect fun externLasxXvmodH(a: V16i16, b: V16i16): V16i16

public expect fun externLasxXvmodW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvmodD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvmodBu(a: V32u8, b: V32u8): V32u8

public expect fun externLasxXvmodHu(a: V16u16, b: V16u16): V16u16

public expect fun externLasxXvmodWu(a: V8u32, b: V8u32): V8u32

public expect fun externLasxXvmodDu(a: V4u64, b: V4u64): V4u64

public expect fun externLasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8

public expect fun externLasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16

public expect fun externLasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32

public expect fun externLasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun externLasxXvandiB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvoriB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvnoriB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvxoriB(a: V32u8, b: UInt): V32u8

public expect fun externLasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8

public expect fun externLasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8

public expect fun externLasxXvcloB(a: V32i8): V32i8

public expect fun externLasxXvcloH(a: V16i16): V16i16

public expect fun externLasxXvcloW(a: V8i32): V8i32

public expect fun externLasxXvcloD(a: V4i64): V4i64

public expect fun externLasxXvfcvtHS(a: V8f32, b: V8f32): V16i16

public expect fun externLasxXvfcvtSD(a: V4f64, b: V4f64): V8f32

public expect fun externLasxXvfminS(a: V8f32, b: V8f32): V8f32

public expect fun externLasxXvfminD(a: V4f64, b: V4f64): V4f64

public expect fun externLasxXvfminaS(a: V8f32, b: V8f32): V8f32

public expect fun externLasxXvfminaD(a: V4f64, b: V4f64): V4f64

public expect fun externLasxXvfmaxS(a: V8f32, b: V8f32): V8f32

public expect fun externLasxXvfmaxD(a: V4f64, b: V4f64): V4f64

public expect fun externLasxXvfmaxaS(a: V8f32, b: V8f32): V8f32

public expect fun externLasxXvfmaxaD(a: V4f64, b: V4f64): V4f64

public expect fun externLasxXvfclassS(a: V8f32): V8i32

public expect fun externLasxXvfclassD(a: V4f64): V4i64

public expect fun externLasxXvfrecipeS(a: V8f32): V8f32

public expect fun externLasxXvfrecipeD(a: V4f64): V4f64

public expect fun externLasxXvfrsqrteS(a: V8f32): V8f32

public expect fun externLasxXvfrsqrteD(a: V4f64): V4f64

public expect fun externLasxXvfrintS(a: V8f32): V8f32

public expect fun externLasxXvfrintD(a: V4f64): V4f64

public expect fun externLasxXvflogbS(a: V8f32): V8f32

public expect fun externLasxXvflogbD(a: V4f64): V4f64

public expect fun externLasxXvfcvthSH(a: V16i16): V8f32

public expect fun externLasxXvfcvthDS(a: V8f32): V4f64

public expect fun externLasxXvfcvtlSH(a: V16i16): V8f32

public expect fun externLasxXvfcvtlDS(a: V8f32): V4f64

public expect fun externLasxXvftintWS(a: V8f32): V8i32

public expect fun externLasxXvftintLD(a: V4f64): V4i64

public expect fun externLasxXvftintWuS(a: V8f32): V8u32

public expect fun externLasxXvftintLuD(a: V4f64): V4u64

public expect fun externLasxXvftintrzWS(a: V8f32): V8i32

public expect fun externLasxXvftintrzLD(a: V4f64): V4i64

public expect fun externLasxXvftintrzWuS(a: V8f32): V8u32

public expect fun externLasxXvftintrzLuD(a: V4f64): V4u64

public expect fun externLasxXvffintSW(a: V8i32): V8f32

public expect fun externLasxXvffintDL(a: V4i64): V4f64

public expect fun externLasxXvffintSWu(a: V8u32): V8f32

public expect fun externLasxXvffintDLu(a: V4u64): V4f64

public expect fun externLasxXvreplveB(a: V32i8, b: Int): V32i8

public expect fun externLasxXvreplveH(a: V16i16, b: Int): V16i16

public expect fun externLasxXvreplveW(a: V8i32, b: Int): V8i32

public expect fun externLasxXvreplveD(a: V4i64, b: Int): V4i64

public expect fun externLasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvsllwilHB(a: V32i8, b: UInt): V16i16

public expect fun externLasxXvsllwilWH(a: V16i16, b: UInt): V8i32

public expect fun externLasxXvsllwilDW(a: V8i32, b: UInt): V4i64

public expect fun externLasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16

public expect fun externLasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32

public expect fun externLasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64

public expect fun externLasxXvsranBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvsranHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvsranWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssranBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvssranHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvssranWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssranBuH(a: V16u16, b: V16u16): V32u8

public expect fun externLasxXvssranHuW(a: V8u32, b: V8u32): V16u16

public expect fun externLasxXvssranWuD(a: V4u64, b: V4u64): V8u32

public expect fun externLasxXvsrarnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvsrarnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvsrarnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssrarnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvssrarnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvssrarnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8

public expect fun externLasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16

public expect fun externLasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32

public expect fun externLasxXvsrlnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvsrlnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvsrlnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8

public expect fun externLasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16

public expect fun externLasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32

public expect fun externLasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8

public expect fun externLasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16

public expect fun externLasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32

public expect fun externLasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8

public expect fun externLasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16

public expect fun externLasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvmskltzB(a: V32i8): V32i8

public expect fun externLasxXvmskltzH(a: V16i16): V16i16

public expect fun externLasxXvmskltzW(a: V8i32): V8i32

public expect fun externLasxXvmskltzD(a: V4i64): V4i64

public expect fun externLasxXvsigncovB(a: V32i8, b: V32i8): V32i8

public expect fun externLasxXvsigncovH(a: V16i16, b: V16i16): V16i16

public expect fun externLasxXvsigncovW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvsigncovD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvftintrneWS(a: V8f32): V8i32

public expect fun externLasxXvftintrneLD(a: V4f64): V4i64

public expect fun externLasxXvftintrpWS(a: V8f32): V8i32

public expect fun externLasxXvftintrpLD(a: V4f64): V4i64

public expect fun externLasxXvftintrmWS(a: V8f32): V8i32

public expect fun externLasxXvftintrmLD(a: V4f64): V4i64

public expect fun externLasxXvftintWD(a: V4f64, b: V4f64): V8i32

public expect fun externLasxXvffintSL(a: V4i64, b: V4i64): V8f32

public expect fun externLasxXvftintrzWD(a: V4f64, b: V4f64): V8i32

public expect fun externLasxXvftintrpWD(a: V4f64, b: V4f64): V8i32

public expect fun externLasxXvftintrmWD(a: V4f64, b: V4f64): V8i32

public expect fun externLasxXvftintrneWD(a: V4f64, b: V4f64): V8i32

public expect fun externLasxXvftinthLS(a: V8f32): V4i64

public expect fun externLasxXvftintlLS(a: V8f32): V4i64

public expect fun externLasxXvffinthDW(a: V8i32): V4f64

public expect fun externLasxXvffintlDW(a: V8i32): V4f64

public expect fun externLasxXvftintrzhLS(a: V8f32): V4i64

public expect fun externLasxXvftintrzlLS(a: V8f32): V4i64

public expect fun externLasxXvftintrphLS(a: V8f32): V4i64

public expect fun externLasxXvftintrplLS(a: V8f32): V4i64

public expect fun externLasxXvftintrmhLS(a: V8f32): V4i64

public expect fun externLasxXvftintrmlLS(a: V8f32): V4i64

public expect fun externLasxXvftintrnehLS(a: V8f32): V4i64

public expect fun externLasxXvftintrnelLS(a: V8f32): V4i64

public expect fun externLasxXvfrintrneS(a: V8f32): V8f32

public expect fun externLasxXvfrintrneD(a: V4f64): V4f64

public expect fun externLasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt)

public expect fun externLasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt)

public expect fun externLasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt)

public expect fun externLasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt)

public expect fun externLasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvpickveW(a: V8i32, b: UInt): V8i32

public expect fun externLasxXvpickveD(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvssrlnBH(a: V16i16, b: V16i16): V32i8

public expect fun externLasxXvssrlnHW(a: V8i32, b: V8i32): V16i16

public expect fun externLasxXvssrlnWD(a: V4i64, b: V4i64): V8i32

public expect fun externLasxXvextlQuDu(a: V4u64): V4u64

public expect fun externLasxVext2xvHB(a: V32i8): V16i16

public expect fun externLasxVext2xvWH(a: V16i16): V8i32

public expect fun externLasxVext2xvDW(a: V8i32): V4i64

public expect fun externLasxVext2xvWB(a: V32i8): V8i32

public expect fun externLasxVext2xvDH(a: V16i16): V4i64

public expect fun externLasxVext2xvDB(a: V32i8): V4i64

public expect fun externLasxVext2xvHuBu(a: V32i8): V16i16

public expect fun externLasxVext2xvWuHu(a: V16i16): V8i32

public expect fun externLasxVext2xvDuWu(a: V8i32): V4i64

public expect fun externLasxVext2xvWuBu(a: V32i8): V8i32

public expect fun externLasxVext2xvDuHu(a: V16i16): V4i64

public expect fun externLasxVext2xvDuBu(a: V32i8): V4i64

public expect fun externLasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvpermiD(a: V4i64, b: UInt): V4i64

public expect fun externLasxXvpermW(a: V8i32, b: V8i32): V8i32

public expect fun externLasxXvldreplB(a: ConstPtr, b: Int): V32i8

public expect fun externLasxXvldreplH(a: ConstPtr, b: Int): V16i16

public expect fun externLasxXvldreplW(a: ConstPtr, b: Int): V8i32

public expect fun externLasxXvldreplD(a: ConstPtr, b: Int): V4i64

public expect fun externLasxXvaddwevQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvaddwevDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvaddwevWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvaddwevHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvsubwevQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvsubwevDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvsubwevWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvsubwevHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvmulwevQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvmulwevDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvmulwevWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvmulwevHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvaddwodQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvaddwodDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvaddwodWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvaddwodHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvsubwodQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvsubwodDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvsubwodWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvsubwodHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvmulwodQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvmulwodDW(a: V8i32, b: V8i32): V4i64

public expect fun externLasxXvmulwodWH(a: V16i16, b: V16i16): V8i32

public expect fun externLasxXvmulwodHB(a: V32i8, b: V32i8): V16i16

public expect fun externLasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun externLasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun externLasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun externLasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun externLasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64

public expect fun externLasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32

public expect fun externLasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16

public expect fun externLasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64

public expect fun externLasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32

public expect fun externLasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16

public expect fun externLasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64

public expect fun externLasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32

public expect fun externLasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16

public expect fun externLasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64

public expect fun externLasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32

public expect fun externLasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16

public expect fun externLasxXvhaddwQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64

public expect fun externLasxXvhsubwQD(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64

public expect fun externLasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun externLasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64

public expect fun externLasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32

public expect fun externLasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16

public expect fun externLasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64

public expect fun externLasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64

public expect fun externLasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32

public expect fun externLasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16

public expect fun externLasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun externLasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64

public expect fun externLasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32

public expect fun externLasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16

public expect fun externLasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64

public expect fun externLasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64

public expect fun externLasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32

public expect fun externLasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16

public expect fun externLasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64

public expect fun externLasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64

public expect fun externLasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32

public expect fun externLasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16

public expect fun externLasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64

public expect fun externLasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64

public expect fun externLasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32

public expect fun externLasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16

public expect fun externLasxXvaddQ(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvsubQ(a: V4i64, b: V4i64): V4i64

public expect fun externLasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64

public expect fun externLasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64

public expect fun externLasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64

public expect fun externLasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64

public expect fun externLasxXvmskgezB(a: V32i8): V32i8

public expect fun externLasxXvmsknzB(a: V32i8): V32i8

public expect fun externLasxXvexthHB(a: V32i8): V16i16

public expect fun externLasxXvexthWH(a: V16i16): V8i32

public expect fun externLasxXvexthDW(a: V8i32): V4i64

public expect fun externLasxXvexthQD(a: V4i64): V4i64

public expect fun externLasxXvexthHuBu(a: V32u8): V16u16

public expect fun externLasxXvexthWuHu(a: V16u16): V8u32

public expect fun externLasxXvexthDuWu(a: V8u32): V4u64

public expect fun externLasxXvexthQuDu(a: V4u64): V4u64

public expect fun externLasxXvextlQD(a: V4i64): V4i64

public expect fun externLasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun externLasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun externLasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun externLasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun externLasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun externLasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun externLasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun externLasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun externLasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun externLasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun externLasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun externLasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun externLasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun externLasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun externLasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun externLasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun externLasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun externLasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun externLasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun externLasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun externLasxXbnzB(a: V32u8): Int

public expect fun externLasxXbnzD(a: V4u64): Int

public expect fun externLasxXbnzH(a: V16u16): Int

public expect fun externLasxXbnzV(a: V32u8): Int

public expect fun externLasxXbnzW(a: V8u32): Int

public expect fun externLasxXbzB(a: V32u8): Int

public expect fun externLasxXbzD(a: V4u64): Int

public expect fun externLasxXbzH(a: V16u16): Int

public expect fun externLasxXbzV(a: V32u8): Int

public expect fun externLasxXbzW(a: V8u32): Int

public expect fun externLasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64

public expect fun externLasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32

public expect fun externLasxXvpickveDF(a: V4f64, b: UInt): V4f64

public expect fun externLasxXvpickveWF(a: V8f32, b: UInt): V8f32

public expect fun externLasxCast128S(a: V4f32): V8f32

public expect fun externLasxCast128D(a: V2f64): V4f64

public expect fun externLasxCast128(a: V2i64): V4i64

public expect fun externLasxConcat128S(a: V4f32, b: V4f32): V8f32

public expect fun externLasxConcat128D(a: V2f64, b: V2f64): V4f64

public expect fun externLasxConcat128(a: V2i64, b: V2i64): V4i64

public expect fun externLasxExtract128LoS(a: V8f32): V4f32

public expect fun externLasxExtract128HiS(a: V8f32): V4f32

public expect fun externLasxExtract128LoD(a: V4f64): V2f64

public expect fun externLasxExtract128HiD(a: V4f64): V2f64

public expect fun externLasxExtract128Lo(a: V4i64): V2i64

public expect fun externLasxExtract128Hi(a: V4i64): V2i64

public expect fun externLasxInsert128LoS(a: V8f32, b: V4f32): V8f32

public expect fun externLasxInsert128HiS(a: V8f32, b: V4f32): V8f32

public expect fun externLasxInsert128LoD(a: V4f64, b: V2f64): V4f64

public expect fun externLasxInsert128HiD(a: V4f64, b: V2f64): V4f64

public expect fun externLasxInsert128Lo(a: V4i64, b: V2i64): V4i64

public expect fun externLasxInsert128Hi(a: V4i64, b: V2i64): V4i64

public fun lasxXvsrarB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsrarB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsrarH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsrarW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsrarD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrariB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsrariB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsrariH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsrariW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsrariD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlrB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlrH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlrW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsrlrD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlriB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlriH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlriW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsrlriD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvbitclriB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvbitclriH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvbitclriW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvbitclriD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvbitsetiB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvbitsetiH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvbitsetiW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvbitsetiD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvbitreviB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvbitreviH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvbitreviW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvbitreviD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiBu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsubiBu(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm5), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiHu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsubiHu(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsubiWu(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiDu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsubiDu(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm5), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsatB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsatH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsatW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsatD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvsatBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvsatHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvsatWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatDu(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvsatDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvdivB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvdivB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvdivH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvdivW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvdivD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvdivBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvdivHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvdivWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvdivDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvhaddwHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvhaddwWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvhaddwDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwHuBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvhaddwHuBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwWuHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvhaddwWuHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwDuWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvhaddwDuWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvhsubwHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvhsubwWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvhsubwDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwHuBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvhsubwHuBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwWuHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvhsubwWuHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwDuWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvhsubwDuWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvmodB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmodH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmodW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmodD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvmodBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvmodHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvmodWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvmodDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvshufB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvshufH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvshufW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvshufD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvandiB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvandiB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvoriB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvnoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvnoriB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvxoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvxoriB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitselV(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvbitselV(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvbitseliB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvbitseliB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvcloB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvcloB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloH(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvcloH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloW(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvcloW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvcloD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcvtHS(a: M256, b: M256): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvfcvtHS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcvtSD(a: M256d, b: M256d): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfcvtSD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfminS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfminD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfminaS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfminaS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminaD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfminaD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfmaxS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfmaxS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfmaxD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfmaxD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfmaxaS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfmaxaS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfmaxaD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfmaxaD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfclassS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfclassS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfclassD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfclassD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrecipeS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfrecipeS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrecipeD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfrecipeD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfrsqrteS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfrsqrteS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrsqrteD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfrsqrteD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfrintS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfrintS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrintD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfrintD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvflogbS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvflogbS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvflogbD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvflogbD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfcvthSH(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfcvthSH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfcvthDS(a: M256): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfcvthDS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfcvtlSH(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfcvtlSH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfcvtlDS(a: M256): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfcvtlDS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvftintWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintWS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintLD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintWuS(a: M256): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvftintWuS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintLuD(a: M256d): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvftintLuD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrzWS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrzLD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzWuS(a: M256): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvftintrzWuS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzLuD(a: M256d): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvftintrzLuD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffintSW(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvffintSW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvffintDL(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvffintDL(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvffintSWu(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvffintSWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvffintDLu(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvffintDLu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvreplveB(a: M256i, b: Int): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvreplveB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmute(b)), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveH(a: M256i, b: Int): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvreplveH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmute(b)), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveW(a: M256i, b: Int): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvreplveW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmute(b)), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveD(a: M256i, b: Int): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvreplveD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmute(b)), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpermiW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvpermiW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm8), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilHB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsllwilHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm3), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilWH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsllwilWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm4), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilDW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsllwilDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm5), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilHuBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvsllwilHuBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), imm3), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilWuHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvsllwilWuHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), imm4), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilDuWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvsllwilDuWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), imm5), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsranBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsranBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsranHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsranHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsranWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsranWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvssranBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvssranHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvssranWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvssranBuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvssranHuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvssranWuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsrarnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsrarnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsrarnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvssrarnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvssrarnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvssrarnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvssrarnBuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvssrarnHuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvssrarnWuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvssrlnBuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvssrlnHuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvssrlnWuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlrnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlrnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlrnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(externLasxXvssrlrnBuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvssrlrnHuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvssrlrnWuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrstpiB(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvfrstpiB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm5), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvfrstpiH(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvfrstpiH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvfrstpB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvfrstpB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrstpH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvfrstpH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshuf4iD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvshuf4iD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvextrinsB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm8), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsH(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvextrinsH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm8), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvextrinsW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm8), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvextrinsD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvmskltzB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvmskltzB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzH(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmskltzH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzW(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmskltzW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmskltzD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvsigncovB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsigncovH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsigncovW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsigncovD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrneWS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrneLD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrpWS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrpLD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrmWS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrmLD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintWD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffintSL(a: M256i, b: M256i): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvffintSL(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvftintrzWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrzWD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrpWD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrmWD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvftintrneWD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftinthLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftinthLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintlLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffinthDW(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvffinthDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvffintlDW(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvffintlDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvftintrzhLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrzhLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrzlLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrphLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrphLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrplLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrplLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmhLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrmhLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrmlLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrnehLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrnehLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrnelLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvftintrnelLS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrintrneS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(externLasxXvfrintrneS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrintrneD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxXvfrintrneD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvstelmB(a: M256i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    externLasxXvstelmB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), memAddr, immS8, imm4)
}

public fun lasxXvstelmH(a: M256i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    externLasxXvstelmH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), memAddr, immS8, imm3)
}

public fun lasxXvstelmW(a: M256i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    externLasxXvstelmW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), memAddr, immS8, imm2)
}

public fun lasxXvstelmD(a: M256i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    externLasxXvstelmD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), memAddr, immS8, imm1)
}

public fun lasxXvinsve0W(a: M256i, b: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvinsve0W(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm3), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvinsve0D(a: M256i, b: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvinsve0D(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm2), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpickveW(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvpickveW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm3), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpickveD(a: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvpickveD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm2), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvssrlrnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvssrlrnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvssrlrnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvssrlnBH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvssrlnHW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvssrlnWD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvextlQuDu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvextlQuDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvHB(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxVext2xvHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWH(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxVext2xvWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDW(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWB(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxVext2xvWB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDH(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDB(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvHuBu(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxVext2xvHuBu(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWuHu(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxVext2xvWuHu(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuWu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDuWu(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWuBu(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxVext2xvWuBu(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuHu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDuHu(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuBu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxVext2xvDuBu(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpermiQ(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvpermiQ(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm8), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpermiD(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvpermiD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpermW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvpermW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvldreplB(memAddr: ConstPtr, immS12: Int): M256i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvldreplB(memAddr, immS12), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplH(memAddr: ConstPtr, immS11: Int): M256i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvldreplH(memAddr, immS11), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplW(memAddr: ConstPtr, immS10: Int): M256i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvldreplW(memAddr, immS10), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplD(memAddr: ConstPtr, immS9: Int): M256i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvldreplD(memAddr, immS9), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvaddwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwevWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwevHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwevWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwevHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwevQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwevDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsubwevWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsubwevHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwevQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwevDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsubwevWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsubwevHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwevWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwevHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwevWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwevHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwodWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwodHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwodWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwodHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwodQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwodDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsubwodWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsubwodHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwodQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubwodDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvsubwodWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvsubwodHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwodWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwodHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodDWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwodWHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwodHBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevDWuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwevWHuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwevHBuB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevDWuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwevWHuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwevHBuB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodDWuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvaddwodWHuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvaddwodHBuB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodDWuW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmulwodWHuH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmulwodHBuB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvhaddwQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwQuDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvhaddwQuDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvhsubwQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwQuDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvhsubwQuDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwevQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwevDW(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmaddwevWH(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmaddwevHB(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQDu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvmaddwevQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDWu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvmaddwevDWu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWHu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvmaddwevWHu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHBu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvmaddwevHBu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwodQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwodDW(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmaddwodWH(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmaddwodHB(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQDu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvmaddwodQDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4u64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDWu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvmaddwodDWu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8u32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWHu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvmaddwodWHu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16u16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHBu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvmaddwodHBu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32u8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQDuD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwevQDuD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDWuW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwevDWuW(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWHuH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmaddwevWHuH(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHBuB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmaddwevHBuB(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQDuD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwodQDuD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4u64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDWuW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmaddwodDWuW(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V8u32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWHuH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvmaddwodWHuH(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V16u16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHBuB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvmaddwodHBuB(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V32u8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(c, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddQ(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubQ(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvsubQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwevQDuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvaddwodQDuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwevQDuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvmulwodQDuD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskgezB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvmskgezB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmsknzB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(externLasxXvmsknzB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthHB(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(externLasxXvexthHB(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthWH(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvexthWH(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthDW(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvexthDW(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthQD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvexthQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthHuBu(a: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(externLasxXvexthHuBu(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>())), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthWuHu(a: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(externLasxXvexthWuHu(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthDuWu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvexthDuWu(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthQuDu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(externLasxXvexthQuDu(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvextlQD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvextlQD(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsrlniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsrlrniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsrlrniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsrlrniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsrlrniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvssrlniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvssrlniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvssrlniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvssrlniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvssrlniBuH(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvssrlniHuW(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvssrlniWuD(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvssrlniDuQ(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvssrlrniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvssrlrniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvssrlrniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvssrlrniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvssrlrniBuH(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvssrlrniHuW(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvssrlrniWuD(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvssrlrniDuQ(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsraniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsraniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsraniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsraniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvsrarniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvsrarniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvsrarniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvsrarniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvssraniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvssraniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvssraniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvssraniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvssraniBuH(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvssraniHuW(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvssraniWuD(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvssraniDuQ(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(externLasxXvssrarniBH(transmuteWithLayouts<M256i, V32i8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(externLasxXvssrarniHW(transmuteWithLayouts<M256i, V16i16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(externLasxXvssrarniWD(transmuteWithLayouts<M256i, V8i32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(externLasxXvssrarniDQ(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(externLasxXvssrarniBuH(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()), transmuteWithLayouts<M256i, V32i8>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32i8>()), imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(externLasxXvssrarniHuW(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()), transmuteWithLayouts<M256i, V16i16>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16i16>()), imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(externLasxXvssrarniWuD(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()), transmuteWithLayouts<M256i, V8i32>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8i32>()), imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(externLasxXvssrarniDuQ(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()), transmuteWithLayouts<M256i, V4i64>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXbnzB(a: M256i): Int = externLasxXbnzB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()))

public fun lasxXbnzD(a: M256i): Int = externLasxXbnzD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()))

public fun lasxXbnzH(a: M256i): Int = externLasxXbnzH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()))

public fun lasxXbnzV(a: M256i): Int = externLasxXbnzV(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()))

public fun lasxXbnzW(a: M256i): Int = externLasxXbnzW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()))

public fun lasxXbzB(a: M256i): Int = externLasxXbzB(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()))

public fun lasxXbzD(a: M256i): Int = externLasxXbzD(transmuteWithLayouts<M256i, V4u64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4u64>()))

public fun lasxXbzH(a: M256i): Int = externLasxXbzH(transmuteWithLayouts<M256i, V16u16>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V16u16>()))

public fun lasxXbzV(a: M256i): Int = externLasxXbzV(transmuteWithLayouts<M256i, V32u8>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V32u8>()))

public fun lasxXbzW(a: M256i): Int = externLasxXbzW(transmuteWithLayouts<M256i, V8u32>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V8u32>()))

public fun lasxXvfcmpCafD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCafD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCafS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCafS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCeqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCeqD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCeqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCeqS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCleD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCleS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCltD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCltD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCltS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCltS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCneD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCneS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCorD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCorD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCorS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCorS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCueqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCueqD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCueqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCueqS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCuleD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCuleS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCultD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCultD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCultS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCultS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCunD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCunD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpCuneD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCuneS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCunS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpCunS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSafD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSafD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSafS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSafS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSeqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSeqD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSeqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSeqS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSleD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSleS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSltD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSltD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSltS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSltS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSneD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSneS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSorD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSorD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSorS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSorS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSueqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSueqD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSueqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSueqS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSuleD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSuleS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSultD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSultD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSultS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSultS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSunD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSunD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxXvfcmpSuneD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M256d, V4f64>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSuneS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSunS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(externLasxXvfcmpSunS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M256, V8f32>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpickveDF(a: M256d, imm2: UInt): M256d {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4f64, M256d>(externLasxXvpickveDF(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), imm2), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())
}

public fun lasxXvpickveWF(a: M256, imm3: UInt): M256 {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8f32, M256>(externLasxXvpickveWF(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), imm3), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())
}

public fun lasxCast128S(a: M128): M256 = transmuteWithLayouts<V8f32, M256>(externLasxCast128S(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxCast128D(a: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxCast128D(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxCast128(a: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxCast128(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxConcat128S(a: M128, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(externLasxConcat128S(transmuteWithLayouts<M128, V4f32>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxConcat128D(a: M128d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxConcat128D(transmuteWithLayouts<M128d, V2f64>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxConcat128(a: M128i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxConcat128(transmuteWithLayouts<M128i, V2i64>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxExtract128LoS(a: M256): M128 = transmuteWithLayouts<V4f32, M128>(externLasxExtract128LoS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lasxExtract128HiS(a: M256): M128 = transmuteWithLayouts<V4f32, M128>(externLasxExtract128HiS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lasxExtract128LoD(a: M256d): M128d = transmuteWithLayouts<V2f64, M128d>(externLasxExtract128LoD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lasxExtract128HiD(a: M256d): M128d = transmuteWithLayouts<V2f64, M128d>(externLasxExtract128HiD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lasxExtract128Lo(a: M256i): M128i = transmuteWithLayouts<V2i64, M128i>(externLasxExtract128Lo(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lasxExtract128Hi(a: M256i): M128i = transmuteWithLayouts<V2i64, M128i>(externLasxExtract128Hi(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lasxInsert128LoS(a: M256, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(externLasxInsert128LoS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxInsert128HiS(a: M256, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(externLasxInsert128HiS(transmuteWithLayouts<M256, V8f32>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<V8f32>()), transmuteWithLayouts<M128, V4f32>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxInsert128LoD(a: M256d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxInsert128LoD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxInsert128HiD(a: M256d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(externLasxInsert128HiD(transmuteWithLayouts<M256d, V4f64>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<V4f64>()), transmuteWithLayouts<M128d, V2f64>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxInsert128Lo(a: M256i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxInsert128Lo(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxInsert128Hi(a: M256i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(externLasxInsert128Hi(transmuteWithLayouts<M256i, V4i64>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<V4i64>()), transmuteWithLayouts<M128i, V2i64>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

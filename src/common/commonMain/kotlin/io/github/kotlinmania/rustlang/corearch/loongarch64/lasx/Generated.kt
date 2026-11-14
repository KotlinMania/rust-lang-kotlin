// port-lint: source loongarch64/lasx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32
import io.github.kotlinmania.rustlang.corearch.mem.transmute

public expect fun lasxXvsrarB(a: V32i8, b: V32i8): V32i8

public expect fun lasxXvsrarH(a: V16i16, b: V16i16): V16i16

public expect fun lasxXvsrarW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvsrarD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvsrariB(a: V32i8, b: UInt): V32i8

public expect fun lasxXvsrariH(a: V16i16, b: UInt): V16i16

public expect fun lasxXvsrariW(a: V8i32, b: UInt): V8i32

public expect fun lasxXvsrariD(a: V4i64, b: UInt): V4i64

public expect fun lasxXvsrlrB(a: V32i8, b: V32i8): V32i8

public expect fun lasxXvsrlrH(a: V16i16, b: V16i16): V16i16

public expect fun lasxXvsrlrW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvsrlrD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvsrlriB(a: V32i8, b: UInt): V32i8

public expect fun lasxXvsrlriH(a: V16i16, b: UInt): V16i16

public expect fun lasxXvsrlriW(a: V8i32, b: UInt): V8i32

public expect fun lasxXvsrlriD(a: V4i64, b: UInt): V4i64

public expect fun lasxXvbitclriB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvbitclriH(a: V16u16, b: UInt): V16u16

public expect fun lasxXvbitclriW(a: V8u32, b: UInt): V8u32

public expect fun lasxXvbitclriD(a: V4u64, b: UInt): V4u64

public expect fun lasxXvbitsetiB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvbitsetiH(a: V16u16, b: UInt): V16u16

public expect fun lasxXvbitsetiW(a: V8u32, b: UInt): V8u32

public expect fun lasxXvbitsetiD(a: V4u64, b: UInt): V4u64

public expect fun lasxXvbitreviB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvbitreviH(a: V16u16, b: UInt): V16u16

public expect fun lasxXvbitreviW(a: V8u32, b: UInt): V8u32

public expect fun lasxXvbitreviD(a: V4u64, b: UInt): V4u64

public expect fun lasxXvsubiBu(a: V32i8, b: UInt): V32i8

public expect fun lasxXvsubiHu(a: V16i16, b: UInt): V16i16

public expect fun lasxXvsubiWu(a: V8i32, b: UInt): V8i32

public expect fun lasxXvsubiDu(a: V4i64, b: UInt): V4i64

public expect fun lasxXvsatB(a: V32i8, b: UInt): V32i8

public expect fun lasxXvsatH(a: V16i16, b: UInt): V16i16

public expect fun lasxXvsatW(a: V8i32, b: UInt): V8i32

public expect fun lasxXvsatD(a: V4i64, b: UInt): V4i64

public expect fun lasxXvsatBu(a: V32u8, b: UInt): V32u8

public expect fun lasxXvsatHu(a: V16u16, b: UInt): V16u16

public expect fun lasxXvsatWu(a: V8u32, b: UInt): V8u32

public expect fun lasxXvsatDu(a: V4u64, b: UInt): V4u64

public expect fun lasxXvdivB(a: V32i8, b: V32i8): V32i8

public expect fun lasxXvdivH(a: V16i16, b: V16i16): V16i16

public expect fun lasxXvdivW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvdivD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvdivBu(a: V32u8, b: V32u8): V32u8

public expect fun lasxXvdivHu(a: V16u16, b: V16u16): V16u16

public expect fun lasxXvdivWu(a: V8u32, b: V8u32): V8u32

public expect fun lasxXvdivDu(a: V4u64, b: V4u64): V4u64

public expect fun lasxXvhaddwHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvhaddwWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvhaddwDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16

public expect fun lasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32

public expect fun lasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64

public expect fun lasxXvhsubwHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvhsubwWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvhsubwDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvmodB(a: V32i8, b: V32i8): V32i8

public expect fun lasxXvmodH(a: V16i16, b: V16i16): V16i16

public expect fun lasxXvmodW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvmodD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvmodBu(a: V32u8, b: V32u8): V32u8

public expect fun lasxXvmodHu(a: V16u16, b: V16u16): V16u16

public expect fun lasxXvmodWu(a: V8u32, b: V8u32): V8u32

public expect fun lasxXvmodDu(a: V4u64, b: V4u64): V4u64

public expect fun lasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8

public expect fun lasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16

public expect fun lasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32

public expect fun lasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun lasxXvandiB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvoriB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvnoriB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvxoriB(a: V32u8, b: UInt): V32u8

public expect fun lasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8

public expect fun lasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8

public expect fun lasxXvcloB(a: V32i8): V32i8

public expect fun lasxXvcloH(a: V16i16): V16i16

public expect fun lasxXvcloW(a: V8i32): V8i32

public expect fun lasxXvcloD(a: V4i64): V4i64

public expect fun lasxXvfcvtHS(a: V8f32, b: V8f32): V16i16

public expect fun lasxXvfcvtSD(a: V4f64, b: V4f64): V8f32

public expect fun lasxXvfminS(a: V8f32, b: V8f32): V8f32

public expect fun lasxXvfminD(a: V4f64, b: V4f64): V4f64

public expect fun lasxXvfminaS(a: V8f32, b: V8f32): V8f32

public expect fun lasxXvfminaD(a: V4f64, b: V4f64): V4f64

public expect fun lasxXvfmaxS(a: V8f32, b: V8f32): V8f32

public expect fun lasxXvfmaxD(a: V4f64, b: V4f64): V4f64

public expect fun lasxXvfmaxaS(a: V8f32, b: V8f32): V8f32

public expect fun lasxXvfmaxaD(a: V4f64, b: V4f64): V4f64

public expect fun lasxXvfclassS(a: V8f32): V8i32

public expect fun lasxXvfclassD(a: V4f64): V4i64

public expect fun lasxXvfrecipeS(a: V8f32): V8f32

public expect fun lasxXvfrecipeD(a: V4f64): V4f64

public expect fun lasxXvfrsqrteS(a: V8f32): V8f32

public expect fun lasxXvfrsqrteD(a: V4f64): V4f64

public expect fun lasxXvfrintS(a: V8f32): V8f32

public expect fun lasxXvfrintD(a: V4f64): V4f64

public expect fun lasxXvflogbS(a: V8f32): V8f32

public expect fun lasxXvflogbD(a: V4f64): V4f64

public expect fun lasxXvfcvthSH(a: V16i16): V8f32

public expect fun lasxXvfcvthDS(a: V8f32): V4f64

public expect fun lasxXvfcvtlSH(a: V16i16): V8f32

public expect fun lasxXvfcvtlDS(a: V8f32): V4f64

public expect fun lasxXvftintWS(a: V8f32): V8i32

public expect fun lasxXvftintLD(a: V4f64): V4i64

public expect fun lasxXvftintWuS(a: V8f32): V8u32

public expect fun lasxXvftintLuD(a: V4f64): V4u64

public expect fun lasxXvftintrzWS(a: V8f32): V8i32

public expect fun lasxXvftintrzLD(a: V4f64): V4i64

public expect fun lasxXvftintrzWuS(a: V8f32): V8u32

public expect fun lasxXvftintrzLuD(a: V4f64): V4u64

public expect fun lasxXvffintSW(a: V8i32): V8f32

public expect fun lasxXvffintDL(a: V4i64): V4f64

public expect fun lasxXvffintSWu(a: V8u32): V8f32

public expect fun lasxXvffintDLu(a: V4u64): V4f64

public expect fun lasxXvreplveB(a: V32i8, b: Int): V32i8

public expect fun lasxXvreplveH(a: V16i16, b: Int): V16i16

public expect fun lasxXvreplveW(a: V8i32, b: Int): V8i32

public expect fun lasxXvreplveD(a: V4i64, b: Int): V4i64

public expect fun lasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvsllwilHB(a: V32i8, b: UInt): V16i16

public expect fun lasxXvsllwilWH(a: V16i16, b: UInt): V8i32

public expect fun lasxXvsllwilDW(a: V8i32, b: UInt): V4i64

public expect fun lasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16

public expect fun lasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32

public expect fun lasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64

public expect fun lasxXvsranBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvsranHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvsranWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssranBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvssranHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvssranWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssranBuH(a: V16u16, b: V16u16): V32u8

public expect fun lasxXvssranHuW(a: V8u32, b: V8u32): V16u16

public expect fun lasxXvssranWuD(a: V4u64, b: V4u64): V8u32

public expect fun lasxXvsrarnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvsrarnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvsrarnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssrarnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvssrarnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvssrarnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8

public expect fun lasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16

public expect fun lasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32

public expect fun lasxXvsrlnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvsrlnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvsrlnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8

public expect fun lasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16

public expect fun lasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32

public expect fun lasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8

public expect fun lasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16

public expect fun lasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32

public expect fun lasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8

public expect fun lasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16

public expect fun lasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvmskltzB(a: V32i8): V32i8

public expect fun lasxXvmskltzH(a: V16i16): V16i16

public expect fun lasxXvmskltzW(a: V8i32): V8i32

public expect fun lasxXvmskltzD(a: V4i64): V4i64

public expect fun lasxXvsigncovB(a: V32i8, b: V32i8): V32i8

public expect fun lasxXvsigncovH(a: V16i16, b: V16i16): V16i16

public expect fun lasxXvsigncovW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvsigncovD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvftintrneWS(a: V8f32): V8i32

public expect fun lasxXvftintrneLD(a: V4f64): V4i64

public expect fun lasxXvftintrpWS(a: V8f32): V8i32

public expect fun lasxXvftintrpLD(a: V4f64): V4i64

public expect fun lasxXvftintrmWS(a: V8f32): V8i32

public expect fun lasxXvftintrmLD(a: V4f64): V4i64

public expect fun lasxXvftintWD(a: V4f64, b: V4f64): V8i32

public expect fun lasxXvffintSL(a: V4i64, b: V4i64): V8f32

public expect fun lasxXvftintrzWD(a: V4f64, b: V4f64): V8i32

public expect fun lasxXvftintrpWD(a: V4f64, b: V4f64): V8i32

public expect fun lasxXvftintrmWD(a: V4f64, b: V4f64): V8i32

public expect fun lasxXvftintrneWD(a: V4f64, b: V4f64): V8i32

public expect fun lasxXvftinthLS(a: V8f32): V4i64

public expect fun lasxXvftintlLS(a: V8f32): V4i64

public expect fun lasxXvffinthDW(a: V8i32): V4f64

public expect fun lasxXvffintlDW(a: V8i32): V4f64

public expect fun lasxXvftintrzhLS(a: V8f32): V4i64

public expect fun lasxXvftintrzlLS(a: V8f32): V4i64

public expect fun lasxXvftintrphLS(a: V8f32): V4i64

public expect fun lasxXvftintrplLS(a: V8f32): V4i64

public expect fun lasxXvftintrmhLS(a: V8f32): V4i64

public expect fun lasxXvftintrmlLS(a: V8f32): V4i64

public expect fun lasxXvftintrnehLS(a: V8f32): V4i64

public expect fun lasxXvftintrnelLS(a: V8f32): V4i64

public expect fun lasxXvfrintrneS(a: V8f32): V8f32

public expect fun lasxXvfrintrneD(a: V4f64): V4f64

public expect fun lasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt)

public expect fun lasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt)

public expect fun lasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt)

public expect fun lasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt)

public expect fun lasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvpickveW(a: V8i32, b: UInt): V8i32

public expect fun lasxXvpickveD(a: V4i64, b: UInt): V4i64

public expect fun lasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvssrlnBH(a: V16i16, b: V16i16): V32i8

public expect fun lasxXvssrlnHW(a: V8i32, b: V8i32): V16i16

public expect fun lasxXvssrlnWD(a: V4i64, b: V4i64): V8i32

public expect fun lasxXvextlQuDu(a: V4u64): V4u64

public expect fun lasxVext2xvHB(a: V32i8): V16i16

public expect fun lasxVext2xvWH(a: V16i16): V8i32

public expect fun lasxVext2xvDW(a: V8i32): V4i64

public expect fun lasxVext2xvWB(a: V32i8): V8i32

public expect fun lasxVext2xvDH(a: V16i16): V4i64

public expect fun lasxVext2xvDB(a: V32i8): V4i64

public expect fun lasxVext2xvHuBu(a: V32i8): V16i16

public expect fun lasxVext2xvWuHu(a: V16i16): V8i32

public expect fun lasxVext2xvDuWu(a: V8i32): V4i64

public expect fun lasxVext2xvWuBu(a: V32i8): V8i32

public expect fun lasxVext2xvDuHu(a: V16i16): V4i64

public expect fun lasxVext2xvDuBu(a: V32i8): V4i64

public expect fun lasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvpermiD(a: V4i64, b: UInt): V4i64

public expect fun lasxXvpermW(a: V8i32, b: V8i32): V8i32

public expect fun lasxXvldreplB(a: ConstPtr, b: Int): V32i8

public expect fun lasxXvldreplH(a: ConstPtr, b: Int): V16i16

public expect fun lasxXvldreplW(a: ConstPtr, b: Int): V8i32

public expect fun lasxXvldreplD(a: ConstPtr, b: Int): V4i64

public expect fun lasxXvaddwevQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvaddwevDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvaddwevWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvaddwevHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvsubwevQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvsubwevDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvsubwevWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvsubwevHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvmulwevQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvmulwevDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvmulwevWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvmulwevHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvaddwodQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvaddwodDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvaddwodWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvaddwodHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvsubwodQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvsubwodDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvsubwodWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvsubwodHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvmulwodQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvmulwodDW(a: V8i32, b: V8i32): V4i64

public expect fun lasxXvmulwodWH(a: V16i16, b: V16i16): V8i32

public expect fun lasxXvmulwodHB(a: V32i8, b: V32i8): V16i16

public expect fun lasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64

public expect fun lasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64

public expect fun lasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32

public expect fun lasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16

public expect fun lasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64

public expect fun lasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32

public expect fun lasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16

public expect fun lasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64

public expect fun lasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32

public expect fun lasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16

public expect fun lasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64

public expect fun lasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32

public expect fun lasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16

public expect fun lasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64

public expect fun lasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32

public expect fun lasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16

public expect fun lasxXvhaddwQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64

public expect fun lasxXvhsubwQD(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64

public expect fun lasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun lasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64

public expect fun lasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32

public expect fun lasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16

public expect fun lasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64

public expect fun lasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64

public expect fun lasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32

public expect fun lasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16

public expect fun lasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64

public expect fun lasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64

public expect fun lasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32

public expect fun lasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16

public expect fun lasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64

public expect fun lasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64

public expect fun lasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32

public expect fun lasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16

public expect fun lasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64

public expect fun lasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64

public expect fun lasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32

public expect fun lasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16

public expect fun lasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64

public expect fun lasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64

public expect fun lasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32

public expect fun lasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16

public expect fun lasxXvaddQ(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvsubQ(a: V4i64, b: V4i64): V4i64

public expect fun lasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64

public expect fun lasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64

public expect fun lasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64

public expect fun lasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64

public expect fun lasxXvmskgezB(a: V32i8): V32i8

public expect fun lasxXvmsknzB(a: V32i8): V32i8

public expect fun lasxXvexthHB(a: V32i8): V16i16

public expect fun lasxXvexthWH(a: V16i16): V8i32

public expect fun lasxXvexthDW(a: V8i32): V4i64

public expect fun lasxXvexthQD(a: V4i64): V4i64

public expect fun lasxXvexthHuBu(a: V32u8): V16u16

public expect fun lasxXvexthWuHu(a: V16u16): V8u32

public expect fun lasxXvexthDuWu(a: V8u32): V4u64

public expect fun lasxXvexthQuDu(a: V4u64): V4u64

public expect fun lasxXvextlQD(a: V4i64): V4i64

public expect fun lasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun lasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun lasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun lasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun lasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun lasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun lasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun lasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun lasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun lasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun lasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun lasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun lasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8

public expect fun lasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16

public expect fun lasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32

public expect fun lasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64

public expect fun lasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8

public expect fun lasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16

public expect fun lasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32

public expect fun lasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64

public expect fun lasxXbnzB(a: V32u8): Int

public expect fun lasxXbnzD(a: V4u64): Int

public expect fun lasxXbnzH(a: V16u16): Int

public expect fun lasxXbnzV(a: V32u8): Int

public expect fun lasxXbnzW(a: V8u32): Int

public expect fun lasxXbzB(a: V32u8): Int

public expect fun lasxXbzD(a: V4u64): Int

public expect fun lasxXbzH(a: V16u16): Int

public expect fun lasxXbzV(a: V32u8): Int

public expect fun lasxXbzW(a: V8u32): Int

public expect fun lasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64

public expect fun lasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32

public expect fun lasxXvpickveDF(a: V4f64, b: UInt): V4f64

public expect fun lasxXvpickveWF(a: V8f32, b: UInt): V8f32

public expect fun lasxCast128S(a: V4f32): V8f32

public expect fun lasxCast128D(a: V2f64): V4f64

public expect fun lasxCast128(a: V2i64): V4i64

public expect fun lasxConcat128S(a: V4f32, b: V4f32): V8f32

public expect fun lasxConcat128D(a: V2f64, b: V2f64): V4f64

public expect fun lasxConcat128(a: V2i64, b: V2i64): V4i64

public expect fun lasxExtract128LoS(a: V8f32): V4f32

public expect fun lasxExtract128HiS(a: V8f32): V4f32

public expect fun lasxExtract128LoD(a: V4f64): V2f64

public expect fun lasxExtract128HiD(a: V4f64): V2f64

public expect fun lasxExtract128Lo(a: V4i64): V2i64

public expect fun lasxExtract128Hi(a: V4i64): V2i64

public expect fun lasxInsert128LoS(a: V8f32, b: V4f32): V8f32

public expect fun lasxInsert128HiS(a: V8f32, b: V4f32): V8f32

public expect fun lasxInsert128LoD(a: V4f64, b: V2f64): V4f64

public expect fun lasxInsert128HiD(a: V4f64, b: V2f64): V4f64

public expect fun lasxInsert128Lo(a: V4i64, b: V2i64): V4i64

public expect fun lasxInsert128Hi(a: V4i64, b: V2i64): V4i64

public fun lasxXvsrarB(a: M256i, b: M256i): M256i = transmute(lasxXvsrarB(transmute(a), transmute(b)))

public fun lasxXvsrarH(a: M256i, b: M256i): M256i = transmute(lasxXvsrarH(transmute(a), transmute(b)))

public fun lasxXvsrarW(a: M256i, b: M256i): M256i = transmute(lasxXvsrarW(transmute(a), transmute(b)))

public fun lasxXvsrarD(a: M256i, b: M256i): M256i = transmute(lasxXvsrarD(transmute(a), transmute(b)))

public fun lasxXvsrariB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsrariB(transmute(a), imm3))
}

public fun lasxXvsrariH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsrariH(transmute(a), imm4))
}

public fun lasxXvsrariW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsrariW(transmute(a), imm5))
}

public fun lasxXvsrariD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsrariD(transmute(a), imm6))
}

public fun lasxXvsrlrB(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrB(transmute(a), transmute(b)))

public fun lasxXvsrlrH(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrH(transmute(a), transmute(b)))

public fun lasxXvsrlrW(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrW(transmute(a), transmute(b)))

public fun lasxXvsrlrD(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrD(transmute(a), transmute(b)))

public fun lasxXvsrlriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsrlriB(transmute(a), imm3))
}

public fun lasxXvsrlriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsrlriH(transmute(a), imm4))
}

public fun lasxXvsrlriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsrlriW(transmute(a), imm5))
}

public fun lasxXvsrlriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsrlriD(transmute(a), imm6))
}

public fun lasxXvbitclriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvbitclriB(transmute(a), imm3))
}

public fun lasxXvbitclriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvbitclriH(transmute(a), imm4))
}

public fun lasxXvbitclriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvbitclriW(transmute(a), imm5))
}

public fun lasxXvbitclriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvbitclriD(transmute(a), imm6))
}

public fun lasxXvbitsetiB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvbitsetiB(transmute(a), imm3))
}

public fun lasxXvbitsetiH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvbitsetiH(transmute(a), imm4))
}

public fun lasxXvbitsetiW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvbitsetiW(transmute(a), imm5))
}

public fun lasxXvbitsetiD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvbitsetiD(transmute(a), imm6))
}

public fun lasxXvbitreviB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvbitreviB(transmute(a), imm3))
}

public fun lasxXvbitreviH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvbitreviH(transmute(a), imm4))
}

public fun lasxXvbitreviW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvbitreviW(transmute(a), imm5))
}

public fun lasxXvbitreviD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvbitreviD(transmute(a), imm6))
}

public fun lasxXvsubiBu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsubiBu(transmute(a), imm5))
}

public fun lasxXvsubiHu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsubiHu(transmute(a), imm5))
}

public fun lasxXvsubiWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsubiWu(transmute(a), imm5))
}

public fun lasxXvsubiDu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsubiDu(transmute(a), imm5))
}

public fun lasxXvsatB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsatB(transmute(a), imm3))
}

public fun lasxXvsatH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsatH(transmute(a), imm4))
}

public fun lasxXvsatW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsatW(transmute(a), imm5))
}

public fun lasxXvsatD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsatD(transmute(a), imm6))
}

public fun lasxXvsatBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsatBu(transmute(a), imm3))
}

public fun lasxXvsatHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsatHu(transmute(a), imm4))
}

public fun lasxXvsatWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsatWu(transmute(a), imm5))
}

public fun lasxXvsatDu(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsatDu(transmute(a), imm6))
}

public fun lasxXvdivB(a: M256i, b: M256i): M256i = transmute(lasxXvdivB(transmute(a), transmute(b)))

public fun lasxXvdivH(a: M256i, b: M256i): M256i = transmute(lasxXvdivH(transmute(a), transmute(b)))

public fun lasxXvdivW(a: M256i, b: M256i): M256i = transmute(lasxXvdivW(transmute(a), transmute(b)))

public fun lasxXvdivD(a: M256i, b: M256i): M256i = transmute(lasxXvdivD(transmute(a), transmute(b)))

public fun lasxXvdivBu(a: M256i, b: M256i): M256i = transmute(lasxXvdivBu(transmute(a), transmute(b)))

public fun lasxXvdivHu(a: M256i, b: M256i): M256i = transmute(lasxXvdivHu(transmute(a), transmute(b)))

public fun lasxXvdivWu(a: M256i, b: M256i): M256i = transmute(lasxXvdivWu(transmute(a), transmute(b)))

public fun lasxXvdivDu(a: M256i, b: M256i): M256i = transmute(lasxXvdivDu(transmute(a), transmute(b)))

public fun lasxXvhaddwHB(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwHB(transmute(a), transmute(b)))

public fun lasxXvhaddwWH(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwWH(transmute(a), transmute(b)))

public fun lasxXvhaddwDW(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwDW(transmute(a), transmute(b)))

public fun lasxXvhaddwHuBu(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwHuBu(transmute(a), transmute(b)))

public fun lasxXvhaddwWuHu(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwWuHu(transmute(a), transmute(b)))

public fun lasxXvhaddwDuWu(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwDuWu(transmute(a), transmute(b)))

public fun lasxXvhsubwHB(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwHB(transmute(a), transmute(b)))

public fun lasxXvhsubwWH(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwWH(transmute(a), transmute(b)))

public fun lasxXvhsubwDW(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwDW(transmute(a), transmute(b)))

public fun lasxXvhsubwHuBu(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwHuBu(transmute(a), transmute(b)))

public fun lasxXvhsubwWuHu(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwWuHu(transmute(a), transmute(b)))

public fun lasxXvhsubwDuWu(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwDuWu(transmute(a), transmute(b)))

public fun lasxXvmodB(a: M256i, b: M256i): M256i = transmute(lasxXvmodB(transmute(a), transmute(b)))

public fun lasxXvmodH(a: M256i, b: M256i): M256i = transmute(lasxXvmodH(transmute(a), transmute(b)))

public fun lasxXvmodW(a: M256i, b: M256i): M256i = transmute(lasxXvmodW(transmute(a), transmute(b)))

public fun lasxXvmodD(a: M256i, b: M256i): M256i = transmute(lasxXvmodD(transmute(a), transmute(b)))

public fun lasxXvmodBu(a: M256i, b: M256i): M256i = transmute(lasxXvmodBu(transmute(a), transmute(b)))

public fun lasxXvmodHu(a: M256i, b: M256i): M256i = transmute(lasxXvmodHu(transmute(a), transmute(b)))

public fun lasxXvmodWu(a: M256i, b: M256i): M256i = transmute(lasxXvmodWu(transmute(a), transmute(b)))

public fun lasxXvmodDu(a: M256i, b: M256i): M256i = transmute(lasxXvmodDu(transmute(a), transmute(b)))

public fun lasxXvshufB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvshufB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvshufH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvshufH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvshufW(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvshufW(transmute(a), transmute(b), transmute(c)))

public fun lasxXvshufD(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvshufD(transmute(a), transmute(b), transmute(c)))

public fun lasxXvandiB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvandiB(transmute(a), imm8))
}

public fun lasxXvoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvoriB(transmute(a), imm8))
}

public fun lasxXvnoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvnoriB(transmute(a), imm8))
}

public fun lasxXvxoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvxoriB(transmute(a), imm8))
}

public fun lasxXvbitselV(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvbitselV(transmute(a), transmute(b), transmute(c)))

public fun lasxXvbitseliB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvbitseliB(transmute(a), transmute(b), imm8))
}

public fun lasxXvcloB(a: M256i): M256i = transmute(lasxXvcloB(transmute(a)))

public fun lasxXvcloH(a: M256i): M256i = transmute(lasxXvcloH(transmute(a)))

public fun lasxXvcloW(a: M256i): M256i = transmute(lasxXvcloW(transmute(a)))

public fun lasxXvcloD(a: M256i): M256i = transmute(lasxXvcloD(transmute(a)))

public fun lasxXvfcvtHS(a: M256, b: M256): M256i = transmute(lasxXvfcvtHS(transmute(a), transmute(b)))

public fun lasxXvfcvtSD(a: M256d, b: M256d): M256 = transmute(lasxXvfcvtSD(transmute(a), transmute(b)))

public fun lasxXvfminS(a: M256, b: M256): M256 = transmute(lasxXvfminS(transmute(a), transmute(b)))

public fun lasxXvfminD(a: M256d, b: M256d): M256d = transmute(lasxXvfminD(transmute(a), transmute(b)))

public fun lasxXvfminaS(a: M256, b: M256): M256 = transmute(lasxXvfminaS(transmute(a), transmute(b)))

public fun lasxXvfminaD(a: M256d, b: M256d): M256d = transmute(lasxXvfminaD(transmute(a), transmute(b)))

public fun lasxXvfmaxS(a: M256, b: M256): M256 = transmute(lasxXvfmaxS(transmute(a), transmute(b)))

public fun lasxXvfmaxD(a: M256d, b: M256d): M256d = transmute(lasxXvfmaxD(transmute(a), transmute(b)))

public fun lasxXvfmaxaS(a: M256, b: M256): M256 = transmute(lasxXvfmaxaS(transmute(a), transmute(b)))

public fun lasxXvfmaxaD(a: M256d, b: M256d): M256d = transmute(lasxXvfmaxaD(transmute(a), transmute(b)))

public fun lasxXvfclassS(a: M256): M256i = transmute(lasxXvfclassS(transmute(a)))

public fun lasxXvfclassD(a: M256d): M256i = transmute(lasxXvfclassD(transmute(a)))

public fun lasxXvfrecipeS(a: M256): M256 = transmute(lasxXvfrecipeS(transmute(a)))

public fun lasxXvfrecipeD(a: M256d): M256d = transmute(lasxXvfrecipeD(transmute(a)))

public fun lasxXvfrsqrteS(a: M256): M256 = transmute(lasxXvfrsqrteS(transmute(a)))

public fun lasxXvfrsqrteD(a: M256d): M256d = transmute(lasxXvfrsqrteD(transmute(a)))

public fun lasxXvfrintS(a: M256): M256 = transmute(lasxXvfrintS(transmute(a)))

public fun lasxXvfrintD(a: M256d): M256d = transmute(lasxXvfrintD(transmute(a)))

public fun lasxXvflogbS(a: M256): M256 = transmute(lasxXvflogbS(transmute(a)))

public fun lasxXvflogbD(a: M256d): M256d = transmute(lasxXvflogbD(transmute(a)))

public fun lasxXvfcvthSH(a: M256i): M256 = transmute(lasxXvfcvthSH(transmute(a)))

public fun lasxXvfcvthDS(a: M256): M256d = transmute(lasxXvfcvthDS(transmute(a)))

public fun lasxXvfcvtlSH(a: M256i): M256 = transmute(lasxXvfcvtlSH(transmute(a)))

public fun lasxXvfcvtlDS(a: M256): M256d = transmute(lasxXvfcvtlDS(transmute(a)))

public fun lasxXvftintWS(a: M256): M256i = transmute(lasxXvftintWS(transmute(a)))

public fun lasxXvftintLD(a: M256d): M256i = transmute(lasxXvftintLD(transmute(a)))

public fun lasxXvftintWuS(a: M256): M256i = transmute(lasxXvftintWuS(transmute(a)))

public fun lasxXvftintLuD(a: M256d): M256i = transmute(lasxXvftintLuD(transmute(a)))

public fun lasxXvftintrzWS(a: M256): M256i = transmute(lasxXvftintrzWS(transmute(a)))

public fun lasxXvftintrzLD(a: M256d): M256i = transmute(lasxXvftintrzLD(transmute(a)))

public fun lasxXvftintrzWuS(a: M256): M256i = transmute(lasxXvftintrzWuS(transmute(a)))

public fun lasxXvftintrzLuD(a: M256d): M256i = transmute(lasxXvftintrzLuD(transmute(a)))

public fun lasxXvffintSW(a: M256i): M256 = transmute(lasxXvffintSW(transmute(a)))

public fun lasxXvffintDL(a: M256i): M256d = transmute(lasxXvffintDL(transmute(a)))

public fun lasxXvffintSWu(a: M256i): M256 = transmute(lasxXvffintSWu(transmute(a)))

public fun lasxXvffintDLu(a: M256i): M256d = transmute(lasxXvffintDLu(transmute(a)))

public fun lasxXvreplveB(a: M256i, b: Int): M256i = transmute(lasxXvreplveB(transmute(a), transmute(b)))

public fun lasxXvreplveH(a: M256i, b: Int): M256i = transmute(lasxXvreplveH(transmute(a), transmute(b)))

public fun lasxXvreplveW(a: M256i, b: Int): M256i = transmute(lasxXvreplveW(transmute(a), transmute(b)))

public fun lasxXvreplveD(a: M256i, b: Int): M256i = transmute(lasxXvreplveD(transmute(a), transmute(b)))

public fun lasxXvpermiW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvpermiW(transmute(a), transmute(b), imm8))
}

public fun lasxXvsllwilHB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilHB(transmute(a), imm3))
}

public fun lasxXvsllwilWH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilWH(transmute(a), imm4))
}

public fun lasxXvsllwilDW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilDW(transmute(a), imm5))
}

public fun lasxXvsllwilHuBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilHuBu(transmute(a), imm3))
}

public fun lasxXvsllwilWuHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilWuHu(transmute(a), imm4))
}

public fun lasxXvsllwilDuWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsllwilDuWu(transmute(a), imm5))
}

public fun lasxXvsranBH(a: M256i, b: M256i): M256i = transmute(lasxXvsranBH(transmute(a), transmute(b)))

public fun lasxXvsranHW(a: M256i, b: M256i): M256i = transmute(lasxXvsranHW(transmute(a), transmute(b)))

public fun lasxXvsranWD(a: M256i, b: M256i): M256i = transmute(lasxXvsranWD(transmute(a), transmute(b)))

public fun lasxXvssranBH(a: M256i, b: M256i): M256i = transmute(lasxXvssranBH(transmute(a), transmute(b)))

public fun lasxXvssranHW(a: M256i, b: M256i): M256i = transmute(lasxXvssranHW(transmute(a), transmute(b)))

public fun lasxXvssranWD(a: M256i, b: M256i): M256i = transmute(lasxXvssranWD(transmute(a), transmute(b)))

public fun lasxXvssranBuH(a: M256i, b: M256i): M256i = transmute(lasxXvssranBuH(transmute(a), transmute(b)))

public fun lasxXvssranHuW(a: M256i, b: M256i): M256i = transmute(lasxXvssranHuW(transmute(a), transmute(b)))

public fun lasxXvssranWuD(a: M256i, b: M256i): M256i = transmute(lasxXvssranWuD(transmute(a), transmute(b)))

public fun lasxXvsrarnBH(a: M256i, b: M256i): M256i = transmute(lasxXvsrarnBH(transmute(a), transmute(b)))

public fun lasxXvsrarnHW(a: M256i, b: M256i): M256i = transmute(lasxXvsrarnHW(transmute(a), transmute(b)))

public fun lasxXvsrarnWD(a: M256i, b: M256i): M256i = transmute(lasxXvsrarnWD(transmute(a), transmute(b)))

public fun lasxXvssrarnBH(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnBH(transmute(a), transmute(b)))

public fun lasxXvssrarnHW(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnHW(transmute(a), transmute(b)))

public fun lasxXvssrarnWD(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnWD(transmute(a), transmute(b)))

public fun lasxXvssrarnBuH(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnBuH(transmute(a), transmute(b)))

public fun lasxXvssrarnHuW(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnHuW(transmute(a), transmute(b)))

public fun lasxXvssrarnWuD(a: M256i, b: M256i): M256i = transmute(lasxXvssrarnWuD(transmute(a), transmute(b)))

public fun lasxXvsrlnBH(a: M256i, b: M256i): M256i = transmute(lasxXvsrlnBH(transmute(a), transmute(b)))

public fun lasxXvsrlnHW(a: M256i, b: M256i): M256i = transmute(lasxXvsrlnHW(transmute(a), transmute(b)))

public fun lasxXvsrlnWD(a: M256i, b: M256i): M256i = transmute(lasxXvsrlnWD(transmute(a), transmute(b)))

public fun lasxXvssrlnBuH(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnBuH(transmute(a), transmute(b)))

public fun lasxXvssrlnHuW(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnHuW(transmute(a), transmute(b)))

public fun lasxXvssrlnWuD(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnWuD(transmute(a), transmute(b)))

public fun lasxXvsrlrnBH(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrnBH(transmute(a), transmute(b)))

public fun lasxXvsrlrnHW(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrnHW(transmute(a), transmute(b)))

public fun lasxXvsrlrnWD(a: M256i, b: M256i): M256i = transmute(lasxXvsrlrnWD(transmute(a), transmute(b)))

public fun lasxXvssrlrnBuH(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnBuH(transmute(a), transmute(b)))

public fun lasxXvssrlrnHuW(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnHuW(transmute(a), transmute(b)))

public fun lasxXvssrlrnWuD(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnWuD(transmute(a), transmute(b)))

public fun lasxXvfrstpiB(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvfrstpiB(transmute(a), transmute(b), imm5))
}

public fun lasxXvfrstpiH(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvfrstpiH(transmute(a), transmute(b), imm5))
}

public fun lasxXvfrstpB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvfrstpB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvfrstpH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvfrstpH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvshuf4iD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvshuf4iD(transmute(a), transmute(b), imm8))
}

public fun lasxXvextrinsB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvextrinsB(transmute(a), transmute(b), imm8))
}

public fun lasxXvextrinsH(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvextrinsH(transmute(a), transmute(b), imm8))
}

public fun lasxXvextrinsW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvextrinsW(transmute(a), transmute(b), imm8))
}

public fun lasxXvextrinsD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvextrinsD(transmute(a), transmute(b), imm8))
}

public fun lasxXvmskltzB(a: M256i): M256i = transmute(lasxXvmskltzB(transmute(a)))

public fun lasxXvmskltzH(a: M256i): M256i = transmute(lasxXvmskltzH(transmute(a)))

public fun lasxXvmskltzW(a: M256i): M256i = transmute(lasxXvmskltzW(transmute(a)))

public fun lasxXvmskltzD(a: M256i): M256i = transmute(lasxXvmskltzD(transmute(a)))

public fun lasxXvsigncovB(a: M256i, b: M256i): M256i = transmute(lasxXvsigncovB(transmute(a), transmute(b)))

public fun lasxXvsigncovH(a: M256i, b: M256i): M256i = transmute(lasxXvsigncovH(transmute(a), transmute(b)))

public fun lasxXvsigncovW(a: M256i, b: M256i): M256i = transmute(lasxXvsigncovW(transmute(a), transmute(b)))

public fun lasxXvsigncovD(a: M256i, b: M256i): M256i = transmute(lasxXvsigncovD(transmute(a), transmute(b)))

public fun lasxXvftintrneWS(a: M256): M256i = transmute(lasxXvftintrneWS(transmute(a)))

public fun lasxXvftintrneLD(a: M256d): M256i = transmute(lasxXvftintrneLD(transmute(a)))

public fun lasxXvftintrpWS(a: M256): M256i = transmute(lasxXvftintrpWS(transmute(a)))

public fun lasxXvftintrpLD(a: M256d): M256i = transmute(lasxXvftintrpLD(transmute(a)))

public fun lasxXvftintrmWS(a: M256): M256i = transmute(lasxXvftintrmWS(transmute(a)))

public fun lasxXvftintrmLD(a: M256d): M256i = transmute(lasxXvftintrmLD(transmute(a)))

public fun lasxXvftintWD(a: M256d, b: M256d): M256i = transmute(lasxXvftintWD(transmute(a), transmute(b)))

public fun lasxXvffintSL(a: M256i, b: M256i): M256 = transmute(lasxXvffintSL(transmute(a), transmute(b)))

public fun lasxXvftintrzWD(a: M256d, b: M256d): M256i = transmute(lasxXvftintrzWD(transmute(a), transmute(b)))

public fun lasxXvftintrpWD(a: M256d, b: M256d): M256i = transmute(lasxXvftintrpWD(transmute(a), transmute(b)))

public fun lasxXvftintrmWD(a: M256d, b: M256d): M256i = transmute(lasxXvftintrmWD(transmute(a), transmute(b)))

public fun lasxXvftintrneWD(a: M256d, b: M256d): M256i = transmute(lasxXvftintrneWD(transmute(a), transmute(b)))

public fun lasxXvftinthLS(a: M256): M256i = transmute(lasxXvftinthLS(transmute(a)))

public fun lasxXvftintlLS(a: M256): M256i = transmute(lasxXvftintlLS(transmute(a)))

public fun lasxXvffinthDW(a: M256i): M256d = transmute(lasxXvffinthDW(transmute(a)))

public fun lasxXvffintlDW(a: M256i): M256d = transmute(lasxXvffintlDW(transmute(a)))

public fun lasxXvftintrzhLS(a: M256): M256i = transmute(lasxXvftintrzhLS(transmute(a)))

public fun lasxXvftintrzlLS(a: M256): M256i = transmute(lasxXvftintrzlLS(transmute(a)))

public fun lasxXvftintrphLS(a: M256): M256i = transmute(lasxXvftintrphLS(transmute(a)))

public fun lasxXvftintrplLS(a: M256): M256i = transmute(lasxXvftintrplLS(transmute(a)))

public fun lasxXvftintrmhLS(a: M256): M256i = transmute(lasxXvftintrmhLS(transmute(a)))

public fun lasxXvftintrmlLS(a: M256): M256i = transmute(lasxXvftintrmlLS(transmute(a)))

public fun lasxXvftintrnehLS(a: M256): M256i = transmute(lasxXvftintrnehLS(transmute(a)))

public fun lasxXvftintrnelLS(a: M256): M256i = transmute(lasxXvftintrnelLS(transmute(a)))

public fun lasxXvfrintrneS(a: M256): M256 = transmute(lasxXvfrintrneS(transmute(a)))

public fun lasxXvfrintrneD(a: M256d): M256d = transmute(lasxXvfrintrneD(transmute(a)))

public fun lasxXvstelmB(a: M256i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    lasxXvstelmB(transmute(a), memAddr, immS8, imm4)
}

public fun lasxXvstelmH(a: M256i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    lasxXvstelmH(transmute(a), memAddr, immS8, imm3)
}

public fun lasxXvstelmW(a: M256i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    lasxXvstelmW(transmute(a), memAddr, immS8, imm2)
}

public fun lasxXvstelmD(a: M256i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    lasxXvstelmD(transmute(a), memAddr, immS8, imm1)
}

public fun lasxXvinsve0W(a: M256i, b: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvinsve0W(transmute(a), transmute(b), imm3))
}

public fun lasxXvinsve0D(a: M256i, b: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(lasxXvinsve0D(transmute(a), transmute(b), imm2))
}

public fun lasxXvpickveW(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvpickveW(transmute(a), imm3))
}

public fun lasxXvpickveD(a: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(lasxXvpickveD(transmute(a), imm2))
}

public fun lasxXvssrlrnBH(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnBH(transmute(a), transmute(b)))

public fun lasxXvssrlrnHW(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnHW(transmute(a), transmute(b)))

public fun lasxXvssrlrnWD(a: M256i, b: M256i): M256i = transmute(lasxXvssrlrnWD(transmute(a), transmute(b)))

public fun lasxXvssrlnBH(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnBH(transmute(a), transmute(b)))

public fun lasxXvssrlnHW(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnHW(transmute(a), transmute(b)))

public fun lasxXvssrlnWD(a: M256i, b: M256i): M256i = transmute(lasxXvssrlnWD(transmute(a), transmute(b)))

public fun lasxXvextlQuDu(a: M256i): M256i = transmute(lasxXvextlQuDu(transmute(a)))

public fun lasxVext2xvHB(a: M256i): M256i = transmute(lasxVext2xvHB(transmute(a)))

public fun lasxVext2xvWH(a: M256i): M256i = transmute(lasxVext2xvWH(transmute(a)))

public fun lasxVext2xvDW(a: M256i): M256i = transmute(lasxVext2xvDW(transmute(a)))

public fun lasxVext2xvWB(a: M256i): M256i = transmute(lasxVext2xvWB(transmute(a)))

public fun lasxVext2xvDH(a: M256i): M256i = transmute(lasxVext2xvDH(transmute(a)))

public fun lasxVext2xvDB(a: M256i): M256i = transmute(lasxVext2xvDB(transmute(a)))

public fun lasxVext2xvHuBu(a: M256i): M256i = transmute(lasxVext2xvHuBu(transmute(a)))

public fun lasxVext2xvWuHu(a: M256i): M256i = transmute(lasxVext2xvWuHu(transmute(a)))

public fun lasxVext2xvDuWu(a: M256i): M256i = transmute(lasxVext2xvDuWu(transmute(a)))

public fun lasxVext2xvWuBu(a: M256i): M256i = transmute(lasxVext2xvWuBu(transmute(a)))

public fun lasxVext2xvDuHu(a: M256i): M256i = transmute(lasxVext2xvDuHu(transmute(a)))

public fun lasxVext2xvDuBu(a: M256i): M256i = transmute(lasxVext2xvDuBu(transmute(a)))

public fun lasxXvpermiQ(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvpermiQ(transmute(a), transmute(b), imm8))
}

public fun lasxXvpermiD(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(lasxXvpermiD(transmute(a), imm8))
}

public fun lasxXvpermW(a: M256i, b: M256i): M256i = transmute(lasxXvpermW(transmute(a), transmute(b)))

public fun lasxXvldreplB(memAddr: ConstPtr, immS12: Int): M256i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmute(lasxXvldreplB(memAddr, immS12))
}

public fun lasxXvldreplH(memAddr: ConstPtr, immS11: Int): M256i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmute(lasxXvldreplH(memAddr, immS11))
}

public fun lasxXvldreplW(memAddr: ConstPtr, immS10: Int): M256i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmute(lasxXvldreplW(memAddr, immS10))
}

public fun lasxXvldreplD(memAddr: ConstPtr, immS9: Int): M256i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmute(lasxXvldreplD(memAddr, immS9))
}

public fun lasxXvaddwevQD(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevQD(transmute(a), transmute(b)))

public fun lasxXvaddwevDW(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevDW(transmute(a), transmute(b)))

public fun lasxXvaddwevWH(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevWH(transmute(a), transmute(b)))

public fun lasxXvaddwevHB(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevHB(transmute(a), transmute(b)))

public fun lasxXvaddwevQDu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevQDu(transmute(a), transmute(b)))

public fun lasxXvaddwevDWu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevDWu(transmute(a), transmute(b)))

public fun lasxXvaddwevWHu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevWHu(transmute(a), transmute(b)))

public fun lasxXvaddwevHBu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevHBu(transmute(a), transmute(b)))

public fun lasxXvsubwevQD(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevQD(transmute(a), transmute(b)))

public fun lasxXvsubwevDW(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevDW(transmute(a), transmute(b)))

public fun lasxXvsubwevWH(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevWH(transmute(a), transmute(b)))

public fun lasxXvsubwevHB(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevHB(transmute(a), transmute(b)))

public fun lasxXvsubwevQDu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevQDu(transmute(a), transmute(b)))

public fun lasxXvsubwevDWu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevDWu(transmute(a), transmute(b)))

public fun lasxXvsubwevWHu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevWHu(transmute(a), transmute(b)))

public fun lasxXvsubwevHBu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwevHBu(transmute(a), transmute(b)))

public fun lasxXvmulwevQD(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevQD(transmute(a), transmute(b)))

public fun lasxXvmulwevDW(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevDW(transmute(a), transmute(b)))

public fun lasxXvmulwevWH(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevWH(transmute(a), transmute(b)))

public fun lasxXvmulwevHB(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevHB(transmute(a), transmute(b)))

public fun lasxXvmulwevQDu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevQDu(transmute(a), transmute(b)))

public fun lasxXvmulwevDWu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevDWu(transmute(a), transmute(b)))

public fun lasxXvmulwevWHu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevWHu(transmute(a), transmute(b)))

public fun lasxXvmulwevHBu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevHBu(transmute(a), transmute(b)))

public fun lasxXvaddwodQD(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodQD(transmute(a), transmute(b)))

public fun lasxXvaddwodDW(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodDW(transmute(a), transmute(b)))

public fun lasxXvaddwodWH(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodWH(transmute(a), transmute(b)))

public fun lasxXvaddwodHB(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodHB(transmute(a), transmute(b)))

public fun lasxXvaddwodQDu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodQDu(transmute(a), transmute(b)))

public fun lasxXvaddwodDWu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodDWu(transmute(a), transmute(b)))

public fun lasxXvaddwodWHu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodWHu(transmute(a), transmute(b)))

public fun lasxXvaddwodHBu(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodHBu(transmute(a), transmute(b)))

public fun lasxXvsubwodQD(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodQD(transmute(a), transmute(b)))

public fun lasxXvsubwodDW(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodDW(transmute(a), transmute(b)))

public fun lasxXvsubwodWH(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodWH(transmute(a), transmute(b)))

public fun lasxXvsubwodHB(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodHB(transmute(a), transmute(b)))

public fun lasxXvsubwodQDu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodQDu(transmute(a), transmute(b)))

public fun lasxXvsubwodDWu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodDWu(transmute(a), transmute(b)))

public fun lasxXvsubwodWHu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodWHu(transmute(a), transmute(b)))

public fun lasxXvsubwodHBu(a: M256i, b: M256i): M256i = transmute(lasxXvsubwodHBu(transmute(a), transmute(b)))

public fun lasxXvmulwodQD(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodQD(transmute(a), transmute(b)))

public fun lasxXvmulwodDW(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodDW(transmute(a), transmute(b)))

public fun lasxXvmulwodWH(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodWH(transmute(a), transmute(b)))

public fun lasxXvmulwodHB(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodHB(transmute(a), transmute(b)))

public fun lasxXvmulwodQDu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodQDu(transmute(a), transmute(b)))

public fun lasxXvmulwodDWu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodDWu(transmute(a), transmute(b)))

public fun lasxXvmulwodWHu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodWHu(transmute(a), transmute(b)))

public fun lasxXvmulwodHBu(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodHBu(transmute(a), transmute(b)))

public fun lasxXvaddwevDWuW(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevDWuW(transmute(a), transmute(b)))

public fun lasxXvaddwevWHuH(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevWHuH(transmute(a), transmute(b)))

public fun lasxXvaddwevHBuB(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevHBuB(transmute(a), transmute(b)))

public fun lasxXvmulwevDWuW(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevDWuW(transmute(a), transmute(b)))

public fun lasxXvmulwevWHuH(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevWHuH(transmute(a), transmute(b)))

public fun lasxXvmulwevHBuB(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevHBuB(transmute(a), transmute(b)))

public fun lasxXvaddwodDWuW(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodDWuW(transmute(a), transmute(b)))

public fun lasxXvaddwodWHuH(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodWHuH(transmute(a), transmute(b)))

public fun lasxXvaddwodHBuB(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodHBuB(transmute(a), transmute(b)))

public fun lasxXvmulwodDWuW(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodDWuW(transmute(a), transmute(b)))

public fun lasxXvmulwodWHuH(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodWHuH(transmute(a), transmute(b)))

public fun lasxXvmulwodHBuB(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodHBuB(transmute(a), transmute(b)))

public fun lasxXvhaddwQD(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwQD(transmute(a), transmute(b)))

public fun lasxXvhaddwQuDu(a: M256i, b: M256i): M256i = transmute(lasxXvhaddwQuDu(transmute(a), transmute(b)))

public fun lasxXvhsubwQD(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwQD(transmute(a), transmute(b)))

public fun lasxXvhsubwQuDu(a: M256i, b: M256i): M256i = transmute(lasxXvhsubwQuDu(transmute(a), transmute(b)))

public fun lasxXvmaddwevQD(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevQD(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevDW(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevDW(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevWH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevWH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevHB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevHB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevQDu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevQDu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevDWu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevDWu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevWHu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevWHu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevHBu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevHBu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodQD(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodQD(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodDW(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodDW(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodWH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodWH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodHB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodHB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodQDu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodQDu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodDWu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodDWu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodWHu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodWHu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodHBu(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodHBu(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevQDuD(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevQDuD(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevDWuW(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevDWuW(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevWHuH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevWHuH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwevHBuB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwevHBuB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodQDuD(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodQDuD(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodDWuW(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodDWuW(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodWHuH(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodWHuH(transmute(a), transmute(b), transmute(c)))

public fun lasxXvmaddwodHBuB(a: M256i, b: M256i, c: M256i): M256i = transmute(lasxXvmaddwodHBuB(transmute(a), transmute(b), transmute(c)))

public fun lasxXvaddQ(a: M256i, b: M256i): M256i = transmute(lasxXvaddQ(transmute(a), transmute(b)))

public fun lasxXvsubQ(a: M256i, b: M256i): M256i = transmute(lasxXvsubQ(transmute(a), transmute(b)))

public fun lasxXvaddwevQDuD(a: M256i, b: M256i): M256i = transmute(lasxXvaddwevQDuD(transmute(a), transmute(b)))

public fun lasxXvaddwodQDuD(a: M256i, b: M256i): M256i = transmute(lasxXvaddwodQDuD(transmute(a), transmute(b)))

public fun lasxXvmulwevQDuD(a: M256i, b: M256i): M256i = transmute(lasxXvmulwevQDuD(transmute(a), transmute(b)))

public fun lasxXvmulwodQDuD(a: M256i, b: M256i): M256i = transmute(lasxXvmulwodQDuD(transmute(a), transmute(b)))

public fun lasxXvmskgezB(a: M256i): M256i = transmute(lasxXvmskgezB(transmute(a)))

public fun lasxXvmsknzB(a: M256i): M256i = transmute(lasxXvmsknzB(transmute(a)))

public fun lasxXvexthHB(a: M256i): M256i = transmute(lasxXvexthHB(transmute(a)))

public fun lasxXvexthWH(a: M256i): M256i = transmute(lasxXvexthWH(transmute(a)))

public fun lasxXvexthDW(a: M256i): M256i = transmute(lasxXvexthDW(transmute(a)))

public fun lasxXvexthQD(a: M256i): M256i = transmute(lasxXvexthQD(transmute(a)))

public fun lasxXvexthHuBu(a: M256i): M256i = transmute(lasxXvexthHuBu(transmute(a)))

public fun lasxXvexthWuHu(a: M256i): M256i = transmute(lasxXvexthWuHu(transmute(a)))

public fun lasxXvexthDuWu(a: M256i): M256i = transmute(lasxXvexthDuWu(transmute(a)))

public fun lasxXvexthQuDu(a: M256i): M256i = transmute(lasxXvexthQuDu(transmute(a)))

public fun lasxXvextlQD(a: M256i): M256i = transmute(lasxXvextlQD(transmute(a)))

public fun lasxXvsrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsrlniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvsrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsrlniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvsrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsrlniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvsrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvsrlniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvsrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsrlrniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvsrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsrlrniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvsrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsrlrniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvsrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvsrlrniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrlniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniBuH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrlniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniHuW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrlniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniWuD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrlniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrlniDuQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrlrniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniBuH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrlrniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniHuW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrlrniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniWuD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrlrniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrlrniDuQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvsraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsraniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvsraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsraniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvsraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsraniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvsraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvsraniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvsrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvsrarniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvsrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvsrarniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvsrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvsrarniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvsrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvsrarniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssraniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssraniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssraniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssraniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssraniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssraniBuH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssraniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssraniHuW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssraniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssraniWuD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssraniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssraniDuQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniBH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniHW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniWD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniDQ(transmute(a), transmute(b), imm7))
}

public fun lasxXvssrarniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniBuH(transmute(a), transmute(b), imm4))
}

public fun lasxXvssrarniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniHuW(transmute(a), transmute(b), imm5))
}

public fun lasxXvssrarniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniWuD(transmute(a), transmute(b), imm6))
}

public fun lasxXvssrarniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmute(lasxXvssrarniDuQ(transmute(a), transmute(b), imm7))
}

public fun lasxXbnzB(a: M256i): Int = transmute(lasxXbnzB(transmute(a)))

public fun lasxXbnzD(a: M256i): Int = transmute(lasxXbnzD(transmute(a)))

public fun lasxXbnzH(a: M256i): Int = transmute(lasxXbnzH(transmute(a)))

public fun lasxXbnzV(a: M256i): Int = transmute(lasxXbnzV(transmute(a)))

public fun lasxXbnzW(a: M256i): Int = transmute(lasxXbnzW(transmute(a)))

public fun lasxXbzB(a: M256i): Int = transmute(lasxXbzB(transmute(a)))

public fun lasxXbzD(a: M256i): Int = transmute(lasxXbzD(transmute(a)))

public fun lasxXbzH(a: M256i): Int = transmute(lasxXbzH(transmute(a)))

public fun lasxXbzV(a: M256i): Int = transmute(lasxXbzV(transmute(a)))

public fun lasxXbzW(a: M256i): Int = transmute(lasxXbzW(transmute(a)))

public fun lasxXvfcmpCafD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCafD(transmute(a), transmute(b)))

public fun lasxXvfcmpCafS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCafS(transmute(a), transmute(b)))

public fun lasxXvfcmpCeqD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCeqD(transmute(a), transmute(b)))

public fun lasxXvfcmpCeqS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCeqS(transmute(a), transmute(b)))

public fun lasxXvfcmpCleD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCleD(transmute(a), transmute(b)))

public fun lasxXvfcmpCleS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCleS(transmute(a), transmute(b)))

public fun lasxXvfcmpCltD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCltD(transmute(a), transmute(b)))

public fun lasxXvfcmpCltS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCltS(transmute(a), transmute(b)))

public fun lasxXvfcmpCneD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCneD(transmute(a), transmute(b)))

public fun lasxXvfcmpCneS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCneS(transmute(a), transmute(b)))

public fun lasxXvfcmpCorD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCorD(transmute(a), transmute(b)))

public fun lasxXvfcmpCorS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCorS(transmute(a), transmute(b)))

public fun lasxXvfcmpCueqD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCueqD(transmute(a), transmute(b)))

public fun lasxXvfcmpCueqS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCueqS(transmute(a), transmute(b)))

public fun lasxXvfcmpCuleD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCuleD(transmute(a), transmute(b)))

public fun lasxXvfcmpCuleS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCuleS(transmute(a), transmute(b)))

public fun lasxXvfcmpCultD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCultD(transmute(a), transmute(b)))

public fun lasxXvfcmpCultS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCultS(transmute(a), transmute(b)))

public fun lasxXvfcmpCunD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCunD(transmute(a), transmute(b)))

public fun lasxXvfcmpCuneD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpCuneD(transmute(a), transmute(b)))

public fun lasxXvfcmpCuneS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCuneS(transmute(a), transmute(b)))

public fun lasxXvfcmpCunS(a: M256, b: M256): M256i = transmute(lasxXvfcmpCunS(transmute(a), transmute(b)))

public fun lasxXvfcmpSafD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSafD(transmute(a), transmute(b)))

public fun lasxXvfcmpSafS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSafS(transmute(a), transmute(b)))

public fun lasxXvfcmpSeqD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSeqD(transmute(a), transmute(b)))

public fun lasxXvfcmpSeqS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSeqS(transmute(a), transmute(b)))

public fun lasxXvfcmpSleD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSleD(transmute(a), transmute(b)))

public fun lasxXvfcmpSleS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSleS(transmute(a), transmute(b)))

public fun lasxXvfcmpSltD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSltD(transmute(a), transmute(b)))

public fun lasxXvfcmpSltS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSltS(transmute(a), transmute(b)))

public fun lasxXvfcmpSneD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSneD(transmute(a), transmute(b)))

public fun lasxXvfcmpSneS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSneS(transmute(a), transmute(b)))

public fun lasxXvfcmpSorD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSorD(transmute(a), transmute(b)))

public fun lasxXvfcmpSorS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSorS(transmute(a), transmute(b)))

public fun lasxXvfcmpSueqD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSueqD(transmute(a), transmute(b)))

public fun lasxXvfcmpSueqS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSueqS(transmute(a), transmute(b)))

public fun lasxXvfcmpSuleD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSuleD(transmute(a), transmute(b)))

public fun lasxXvfcmpSuleS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSuleS(transmute(a), transmute(b)))

public fun lasxXvfcmpSultD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSultD(transmute(a), transmute(b)))

public fun lasxXvfcmpSultS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSultS(transmute(a), transmute(b)))

public fun lasxXvfcmpSunD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSunD(transmute(a), transmute(b)))

public fun lasxXvfcmpSuneD(a: M256d, b: M256d): M256i = transmute(lasxXvfcmpSuneD(transmute(a), transmute(b)))

public fun lasxXvfcmpSuneS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSuneS(transmute(a), transmute(b)))

public fun lasxXvfcmpSunS(a: M256, b: M256): M256i = transmute(lasxXvfcmpSunS(transmute(a), transmute(b)))

public fun lasxXvpickveDF(a: M256d, imm2: UInt): M256d {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(lasxXvpickveDF(transmute(a), imm2))
}

public fun lasxXvpickveWF(a: M256, imm3: UInt): M256 {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(lasxXvpickveWF(transmute(a), imm3))
}

public fun lasxCast128S(a: M128): M256 = transmute(lasxCast128S(transmute(a)))

public fun lasxCast128D(a: M128d): M256d = transmute(lasxCast128D(transmute(a)))

public fun lasxCast128(a: M128i): M256i = transmute(lasxCast128(transmute(a)))

public fun lasxConcat128S(a: M128, b: M128): M256 = transmute(lasxConcat128S(transmute(a), transmute(b)))

public fun lasxConcat128D(a: M128d, b: M128d): M256d = transmute(lasxConcat128D(transmute(a), transmute(b)))

public fun lasxConcat128(a: M128i, b: M128i): M256i = transmute(lasxConcat128(transmute(a), transmute(b)))

public fun lasxExtract128LoS(a: M256): M128 = transmute(lasxExtract128LoS(transmute(a)))

public fun lasxExtract128HiS(a: M256): M128 = transmute(lasxExtract128HiS(transmute(a)))

public fun lasxExtract128LoD(a: M256d): M128d = transmute(lasxExtract128LoD(transmute(a)))

public fun lasxExtract128HiD(a: M256d): M128d = transmute(lasxExtract128HiD(transmute(a)))

public fun lasxExtract128Lo(a: M256i): M128i = transmute(lasxExtract128Lo(transmute(a)))

public fun lasxExtract128Hi(a: M256i): M128i = transmute(lasxExtract128Hi(transmute(a)))

public fun lasxInsert128LoS(a: M256, b: M128): M256 = transmute(lasxInsert128LoS(transmute(a), transmute(b)))

public fun lasxInsert128HiS(a: M256, b: M128): M256 = transmute(lasxInsert128HiS(transmute(a), transmute(b)))

public fun lasxInsert128LoD(a: M256d, b: M128d): M256d = transmute(lasxInsert128LoD(transmute(a), transmute(b)))

public fun lasxInsert128HiD(a: M256d, b: M128d): M256d = transmute(lasxInsert128HiD(transmute(a), transmute(b)))

public fun lasxInsert128Lo(a: M256i, b: M128i): M256i = transmute(lasxInsert128Lo(transmute(a), transmute(b)))

public fun lasxInsert128Hi(a: M256i, b: M128i): M256i = transmute(lasxInsert128Hi(transmute(a), transmute(b)))

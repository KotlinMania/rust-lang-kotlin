// port-lint: source loongarch64/lasx/generated.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32

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

public fun lasxXvsrarB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsrarB(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsrarH(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsrarW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsrarD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrariB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsrariB(a, imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsrariH(a, imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsrariW(a, imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrariD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsrariD(a, imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsrlrB(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsrlrH(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsrlrW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsrlrD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsrlriB(a, imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsrlriH(a, imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsrlriW(a, imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsrlriD(a, imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvbitclriB(a, imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvbitclriH(a, imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvbitclriW(a, imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitclriD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvbitclriD(a, imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvbitsetiB(a, imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvbitsetiH(a, imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvbitsetiW(a, imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitsetiD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvbitsetiD(a, imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvbitreviB(a, imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvbitreviH(a, imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvbitreviW(a, imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitreviD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvbitreviD(a, imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiBu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsubiBu(a, imm5), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiHu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsubiHu(a, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsubiWu(a, imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsubiDu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsubiDu(a, imm5), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsatB(a, imm3), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsatH(a, imm4), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsatW(a, imm5), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatD(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsatD(a, imm6), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvsatBu(a, imm3), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvsatHu(a, imm4), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvsatWu(a, imm5), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsatDu(a: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvsatDu(a, imm6), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvdivB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvdivB(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvdivH(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvdivW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvdivD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvdivBu(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvdivHu(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvdivWu(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvdivDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvdivDu(a, b), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvhaddwHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvhaddwWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvhaddwDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwHuBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvhaddwHuBu(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwWuHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvhaddwWuHu(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwDuWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvhaddwDuWu(a, b), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvhsubwHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvhsubwWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvhsubwDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwHuBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvhsubwHuBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwWuHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvhsubwWuHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwDuWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvhsubwDuWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvmodB(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmodH(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmodW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmodD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvmodBu(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvmodHu(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvmodWu(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmodDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvmodDu(a, b), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvshufB(a, b, c), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvshufH(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvshufW(a, b, c), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshufD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvshufD(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvandiB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvandiB(a, imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvoriB(a, imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvnoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvnoriB(a, imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvxoriB(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvxoriB(a, imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvbitselV(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvbitselV(a, b, c), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvbitseliB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvbitseliB(a, b, imm8), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvcloB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvcloB(a), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloH(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvcloH(a), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloW(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvcloW(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvcloD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvcloD(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcvtHS(a: M256, b: M256): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvfcvtHS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcvtSD(a: M256d, b: M256d): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfcvtSD(transmuteWithLayouts<M256d, M256>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256>()), transmuteWithLayouts<M256d, M256>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfminS(a, b), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfminD(a, b), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfminaS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfminaS(a, b), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfminaD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfminaD(a, b), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfmaxS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfmaxS(a, b), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfmaxD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfmaxD(a, b), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfmaxaS(a: M256, b: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfmaxaS(a, b), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfmaxaD(a: M256d, b: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfmaxaD(a, b), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfclassS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfclassS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfclassD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfclassD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrecipeS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfrecipeS(a), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrecipeD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfrecipeD(a), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfrsqrteS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfrsqrteS(a), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrsqrteD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfrsqrteD(a), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfrintS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfrintS(a), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrintD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfrintD(a), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvflogbS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvflogbS(a), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvflogbD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvflogbD(a), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfcvthSH(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfcvthSH(transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfcvthDS(a: M256): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfcvthDS(transmuteWithLayouts<M256, M256d>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvfcvtlSH(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfcvtlSH(transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfcvtlDS(a: M256): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfcvtlDS(transmuteWithLayouts<M256, M256d>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvftintWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintWS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintLD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintWuS(a: M256): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvftintWuS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintLuD(a: M256d): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvftintLuD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrzWS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrzLD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzWuS(a: M256): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvftintrzWuS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzLuD(a: M256d): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvftintrzLuD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffintSW(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvffintSW(transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvffintDL(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvffintDL(transmuteWithLayouts<M256i, M256d>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvffintSWu(a: M256i): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvffintSWu(transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvffintDLu(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvffintDLu(transmuteWithLayouts<M256i, M256d>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvreplveB(a: M256i, b: Int): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvreplveB(a, transmuteWithLayouts<Int, M256i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveH(a: M256i, b: Int): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvreplveH(a, transmuteWithLayouts<Int, M256i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveW(a: M256i, b: Int): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvreplveW(a, transmuteWithLayouts<Int, M256i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvreplveD(a: M256i, b: Int): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvreplveD(a, transmuteWithLayouts<Int, M256i>(b, PrimitiveLayouts.I32, UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpermiW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvpermiW(a, b, imm8), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilHB(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsllwilHB(a, imm3), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilWH(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsllwilWH(a, imm4), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilDW(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsllwilDW(a, imm5), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilHuBu(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvsllwilHuBu(a, imm3), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilWuHu(a: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvsllwilWuHu(a, imm4), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsllwilDuWu(a: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvsllwilDuWu(a, imm5), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsranBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsranBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsranHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsranHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsranWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsranWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvssranBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvssranHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvssranWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvssranBuH(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvssranHuW(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssranWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvssranWuD(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsrarnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsrarnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrarnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsrarnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvssrarnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvssrarnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvssrarnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvssrarnBuH(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvssrarnHuW(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrarnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvssrarnWuD(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsrlnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsrlnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsrlnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvssrlnBuH(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvssrlnHuW(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvssrlnWuD(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsrlrnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsrlrnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlrnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsrlrnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnBuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32u8, M256i>(lasxXvssrlrnBuH(a, b), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnHuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvssrlrnHuW(a, b), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnWuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvssrlrnWuD(a, b), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrstpiB(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvfrstpiB(a, b, imm5), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvfrstpiH(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvfrstpiH(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvfrstpB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvfrstpB(a, b, c), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrstpH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvfrstpH(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvshuf4iD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvshuf4iD(a, b, imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsB(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvextrinsB(a, b, imm8), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsH(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvextrinsH(a, b, imm8), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsW(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvextrinsW(a, b, imm8), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvextrinsD(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvextrinsD(a, b, imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvmskltzB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvmskltzB(a), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzH(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmskltzH(a), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzW(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmskltzW(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskltzD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmskltzD(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvsigncovB(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsigncovH(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsigncovW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsigncovD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsigncovD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrneWS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrneLD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrpWS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrpLD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmWS(a: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrmWS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmLD(a: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrmLD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintWD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffintSL(a: M256i, b: M256i): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvffintSL(transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>()), transmuteWithLayouts<M256i, M256>(b, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvftintrzWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrzWD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrpWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrpWD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrmWD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrneWD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvftintrneWD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftinthLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftinthLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintlLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvffinthDW(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvffinthDW(transmuteWithLayouts<M256i, M256d>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvffintlDW(a: M256i): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvffintlDW(transmuteWithLayouts<M256i, M256d>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvftintrzhLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrzhLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrzlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrzlLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrphLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrphLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrplLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrplLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmhLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrmhLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrmlLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrmlLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrnehLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrnehLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvftintrnelLS(a: M256): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvftintrnelLS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfrintrneS(a: M256): M256 = transmuteWithLayouts<V8f32, M256>(lasxXvfrintrneS(a), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxXvfrintrneD(a: M256d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxXvfrintrneD(a), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxXvstelmB(a: M256i, memAddr: MutPtr, immS8: Int, imm4: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    lasxXvstelmB(transmuteWithLayouts<M256i, Unit>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm4)
}

public fun lasxXvstelmH(a: M256i, memAddr: MutPtr, immS8: Int, imm3: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    lasxXvstelmH(transmuteWithLayouts<M256i, Unit>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm3)
}

public fun lasxXvstelmW(a: M256i, memAddr: MutPtr, immS8: Int, imm2: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    lasxXvstelmW(transmuteWithLayouts<M256i, Unit>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm2)
}

public fun lasxXvstelmD(a: M256i, memAddr: MutPtr, immS8: Int, imm1: UInt) {
    run {
        check((-1 shl (8 - 1).toInt()) - 1 <= immS8 && immS8 < (1 shl (8 - 1).toInt())) { "IMM_S8 doesn't fit in 8 bits (signed)" }
    }
    run {
        check(0u <= imm1 && imm1 < (1u shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    lasxXvstelmD(transmuteWithLayouts<M256i, Unit>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<Unit>()), memAddr, immS8, imm1)
}

public fun lasxXvinsve0W(a: M256i, b: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvinsve0W(a, b, imm3), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvinsve0D(a: M256i, b: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvinsve0D(a, b, imm2), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpickveW(a: M256i, imm3: UInt): M256i {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvpickveW(a, imm3), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpickveD(a: M256i, imm2: UInt): M256i {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvpickveD(a, imm2), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvssrlrnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvssrlrnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlrnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvssrlrnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnBH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvssrlnBH(a, b), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnHW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvssrlnHW(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvssrlnWD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvssrlnWD(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvextlQuDu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvextlQuDu(a), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvHB(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxVext2xvHB(a), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWH(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxVext2xvWH(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDW(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDW(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWB(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxVext2xvWB(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDH(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDH(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDB(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDB(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvHuBu(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxVext2xvHuBu(a), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWuHu(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxVext2xvWuHu(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuWu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDuWu(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvWuBu(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxVext2xvWuBu(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuHu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDuHu(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxVext2xvDuBu(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxVext2xvDuBu(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpermiQ(a: M256i, b: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvpermiQ(a, b, imm8), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpermiD(a: M256i, imm8: UInt): M256i {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvpermiD(a, imm8), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvpermW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvpermW(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvldreplB(memAddr: ConstPtr, immS12: Int): M256i {
    run {
        check((-1 shl (12 - 1).toInt()) - 1 <= immS12 && immS12 < (1 shl (12 - 1).toInt())) { "IMM_S12 doesn't fit in 12 bits (signed)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvldreplB(memAddr, immS12), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplH(memAddr: ConstPtr, immS11: Int): M256i {
    run {
        check((-1 shl (11 - 1).toInt()) - 1 <= immS11 && immS11 < (1 shl (11 - 1).toInt())) { "IMM_S11 doesn't fit in 11 bits (signed)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvldreplH(memAddr, immS11), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplW(memAddr: ConstPtr, immS10: Int): M256i {
    run {
        check((-1 shl (10 - 1).toInt()) - 1 <= immS10 && immS10 < (1 shl (10 - 1).toInt())) { "IMM_S10 doesn't fit in 10 bits (signed)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvldreplW(memAddr, immS10), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvldreplD(memAddr: ConstPtr, immS9: Int): M256i {
    run {
        check((-1 shl (9 - 1).toInt()) - 1 <= immS9 && immS9 < (1 shl (9 - 1).toInt())) { "IMM_S9 doesn't fit in 9 bits (signed)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvldreplD(memAddr, immS9), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvaddwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwevWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwevHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwevWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwevHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwevQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwevDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsubwevWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsubwevHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwevQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwevDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsubwevWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsubwevHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwevWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwevHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwevWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwevHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwodWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwodHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwodWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwodHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwodQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwodDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsubwodWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsubwodHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwodQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubwodDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvsubwodWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvsubwodHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodDW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwodWH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwodHB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodQDu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDWu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodDWu(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWHu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwodWHu(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHBu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwodHBu(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevDWuW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwevWHuH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwevHBuB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevDWuW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwevWHuH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwevHBuB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodDWuW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvaddwodWHuH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvaddwodHBuB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodDWuW(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodDWuW(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodWHuH(a: M256i, b: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmulwodWHuH(a, b), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodHBuB(a: M256i, b: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmulwodHBuB(a, b), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvhaddwQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhaddwQuDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvhaddwQuDu(a, b), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwQD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvhsubwQD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvhsubwQuDu(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvhsubwQuDu(a, b), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwevQD(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwevDW(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmaddwevWH(a, b, c), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmaddwevHB(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQDu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvmaddwevQDu(a, b, c), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDWu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvmaddwevDWu(a, b, c), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWHu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvmaddwevWHu(a, b, c), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHBu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvmaddwevHBu(a, b, c), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwodQD(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwodDW(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmaddwodWH(a, b, c), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmaddwodHB(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQDu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvmaddwodQDu(a, b, c), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDWu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvmaddwodDWu(a, b, c), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWHu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvmaddwodWHu(a, b, c), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHBu(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvmaddwodHBu(a, b, c), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevQDuD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwevQDuD(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevDWuW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwevDWuW(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevWHuH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmaddwevWHuH(a, b, c), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwevHBuB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmaddwevHBuB(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodQDuD(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwodQDuD(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodDWuW(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmaddwodDWuW(a, b, c), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodWHuH(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvmaddwodWHuH(a, b, c), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmaddwodHBuB(a: M256i, b: M256i, c: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvmaddwodHBuB(a, b, c), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddQ(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddQ(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsubQ(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvsubQ(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwevQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwevQDuD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvaddwodQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvaddwodQDuD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwevQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwevQDuD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmulwodQDuD(a: M256i, b: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvmulwodQDuD(a, b), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmskgezB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvmskgezB(a), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvmsknzB(a: M256i): M256i = transmuteWithLayouts<V32i8, M256i>(lasxXvmsknzB(a), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthHB(a: M256i): M256i = transmuteWithLayouts<V16i16, M256i>(lasxXvexthHB(a), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthWH(a: M256i): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvexthWH(a), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthDW(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvexthDW(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthQD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvexthQD(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthHuBu(a: M256i): M256i = transmuteWithLayouts<V16u16, M256i>(lasxXvexthHuBu(a), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthWuHu(a: M256i): M256i = transmuteWithLayouts<V8u32, M256i>(lasxXvexthWuHu(a), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthDuWu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvexthDuWu(a), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvexthQuDu(a: M256i): M256i = transmuteWithLayouts<V4u64, M256i>(lasxXvexthQuDu(a), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvextlQD(a: M256i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvextlQD(a), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvsrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsrlniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsrlniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsrlniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsrlniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsrlrniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsrlrniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsrlrniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsrlrniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvssrlniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvssrlniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvssrlniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvssrlniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvssrlniBuH(a, b, imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvssrlniHuW(a, b, imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvssrlniWuD(a, b, imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvssrlniDuQ(a, b, imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvssrlrniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvssrlrniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvssrlrniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvssrlrniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvssrlrniBuH(a, b, imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvssrlrniHuW(a, b, imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvssrlrniWuD(a, b, imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrlrniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvssrlrniDuQ(a, b, imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsraniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsraniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsraniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsraniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvsrarniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvsrarniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvsrarniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvsrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvsrarniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvssraniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvssraniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvssraniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvssraniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvssraniBuH(a, b, imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvssraniHuW(a, b, imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvssraniWuD(a, b, imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssraniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvssraniDuQ(a, b, imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniBH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32i8, M256i>(lasxXvssrarniBH(a, b, imm4), UnresolvedTypeLayout<V32i8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniHW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16i16, M256i>(lasxXvssrarniHW(a, b, imm5), UnresolvedTypeLayout<V16i16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniWD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8i32, M256i>(lasxXvssrarniWD(a, b, imm6), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniDQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4i64, M256i>(lasxXvssrarniDQ(a, b, imm7), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniBuH(a: M256i, b: M256i, imm4: UInt): M256i {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<V32u8, M256i>(lasxXvssrarniBuH(a, b, imm4), UnresolvedTypeLayout<V32u8>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniHuW(a: M256i, b: M256i, imm5: UInt): M256i {
    run {
        check(0u <= imm5 && imm5 < (1u shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return transmuteWithLayouts<V16u16, M256i>(lasxXvssrarniHuW(a, b, imm5), UnresolvedTypeLayout<V16u16>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniWuD(a: M256i, b: M256i, imm6: UInt): M256i {
    run {
        check(0u <= imm6 && imm6 < (1u shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8u32, M256i>(lasxXvssrarniWuD(a, b, imm6), UnresolvedTypeLayout<V8u32>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXvssrarniDuQ(a: M256i, b: M256i, imm7: UInt): M256i {
    run {
        check(0u <= imm7 && imm7 < (1u shl 7)) { "IMM7 doesn't fit in 7 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4u64, M256i>(lasxXvssrarniDuQ(a, b, imm7), UnresolvedTypeLayout<V4u64>(), UnresolvedTypeLayout<M256i>())
}

public fun lasxXbnzB(a: M256i): Int = lasxXbnzB(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbnzD(a: M256i): Int = lasxXbnzD(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbnzH(a: M256i): Int = lasxXbnzH(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbnzV(a: M256i): Int = lasxXbnzV(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbnzW(a: M256i): Int = lasxXbnzW(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbzB(a: M256i): Int = lasxXbzB(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbzD(a: M256i): Int = lasxXbzD(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbzH(a: M256i): Int = lasxXbzH(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbzV(a: M256i): Int = lasxXbzV(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXbzW(a: M256i): Int = lasxXbzW(transmuteWithLayouts<M256i, Int>(a, UnresolvedTypeLayout<M256i>(), PrimitiveLayouts.I32))

public fun lasxXvfcmpCafD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCafD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCafS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCafS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCeqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCeqD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCeqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCeqS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCleD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCleS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCltD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCltD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCltS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCltS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCneD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCneS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCorD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCorD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCorS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCorS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCueqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCueqD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCueqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCueqS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCuleD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCuleS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCultD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCultD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCultS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCultS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCunD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCunD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpCuneD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCuneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCuneS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpCunS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpCunS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSafD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSafD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSafS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSafS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSeqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSeqD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSeqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSeqS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSleD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSleS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSltD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSltD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSltS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSltS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSneD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSneS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSorD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSorD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSorS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSorS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSueqD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSueqD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSueqS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSueqS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuleD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSuleD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuleS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSuleS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSultD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSultD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSultS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSultS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSunD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSunD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuneD(a: M256d, b: M256d): M256i = transmuteWithLayouts<V4i64, M256i>(lasxXvfcmpSuneD(transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256d, M256i>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSuneS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSuneS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvfcmpSunS(a: M256, b: M256): M256i = transmuteWithLayouts<V8i32, M256i>(lasxXvfcmpSunS(transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M256, M256i>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V8i32>(), UnresolvedTypeLayout<M256i>())

public fun lasxXvpickveDF(a: M256d, imm2: UInt): M256d {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmuteWithLayouts<V4f64, M256d>(lasxXvpickveDF(a, imm2), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())
}

public fun lasxXvpickveWF(a: M256, imm3: UInt): M256 {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<V8f32, M256>(lasxXvpickveWF(a, imm3), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())
}

public fun lasxCast128S(a: M128): M256 = transmuteWithLayouts<V8f32, M256>(lasxCast128S(transmuteWithLayouts<M128, M256>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxCast128D(a: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxCast128D(transmuteWithLayouts<M128d, M256d>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxCast128(a: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxCast128(transmuteWithLayouts<M128i, M256i>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxConcat128S(a: M128, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(lasxConcat128S(transmuteWithLayouts<M128, M256>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M256>()), transmuteWithLayouts<M128, M256>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxConcat128D(a: M128d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxConcat128D(transmuteWithLayouts<M128d, M256d>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M256d>()), transmuteWithLayouts<M128d, M256d>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxConcat128(a: M128i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxConcat128(transmuteWithLayouts<M128i, M256i>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M256i>()), transmuteWithLayouts<M128i, M256i>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxExtract128LoS(a: M256): M128 = transmuteWithLayouts<V4f32, M128>(lasxExtract128LoS(transmuteWithLayouts<M256, M128>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lasxExtract128HiS(a: M256): M128 = transmuteWithLayouts<V4f32, M128>(lasxExtract128HiS(transmuteWithLayouts<M256, M128>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M128>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<M128>())

public fun lasxExtract128LoD(a: M256d): M128d = transmuteWithLayouts<V2f64, M128d>(lasxExtract128LoD(transmuteWithLayouts<M256d, M128d>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lasxExtract128HiD(a: M256d): M128d = transmuteWithLayouts<V2f64, M128d>(lasxExtract128HiD(transmuteWithLayouts<M256d, M128d>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M128d>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<M128d>())

public fun lasxExtract128Lo(a: M256i): M128i = transmuteWithLayouts<V2i64, M128i>(lasxExtract128Lo(transmuteWithLayouts<M256i, M128i>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lasxExtract128Hi(a: M256i): M128i = transmuteWithLayouts<V2i64, M128i>(lasxExtract128Hi(transmuteWithLayouts<M256i, M128i>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M128i>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<M128i>())

public fun lasxInsert128LoS(a: M256, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(lasxInsert128LoS(a, transmuteWithLayouts<M128, M256>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxInsert128HiS(a: M256, b: M128): M256 = transmuteWithLayouts<V8f32, M256>(lasxInsert128HiS(a, transmuteWithLayouts<M128, M256>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M256>())), UnresolvedTypeLayout<V8f32>(), UnresolvedTypeLayout<M256>())

public fun lasxInsert128LoD(a: M256d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxInsert128LoD(a, transmuteWithLayouts<M128d, M256d>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxInsert128HiD(a: M256d, b: M128d): M256d = transmuteWithLayouts<V4f64, M256d>(lasxInsert128HiD(a, transmuteWithLayouts<M128d, M256d>(b, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M256d>())), UnresolvedTypeLayout<V4f64>(), UnresolvedTypeLayout<M256d>())

public fun lasxInsert128Lo(a: M256i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxInsert128Lo(a, transmuteWithLayouts<M128i, M256i>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

public fun lasxInsert128Hi(a: M256i, b: M128i): M256i = transmuteWithLayouts<V4i64, M256i>(lasxInsert128Hi(a, transmuteWithLayouts<M128i, M256i>(b, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M256i>())), UnresolvedTypeLayout<V4i64>(), UnresolvedTypeLayout<M256i>())

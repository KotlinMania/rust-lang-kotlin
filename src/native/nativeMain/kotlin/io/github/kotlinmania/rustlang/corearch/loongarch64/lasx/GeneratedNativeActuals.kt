@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun lasxXvsrarB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun lasxXvsrarH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun lasxXvsrarW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvsrarD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsrariB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun lasxXvsrariH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun lasxXvsrariW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvsrariD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvsrlrB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun lasxXvsrlrH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun lasxXvsrlrW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvsrlrD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsrlriB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun lasxXvsrlriH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun lasxXvsrlriW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvsrlriD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvbitclriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvbitclriH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun lasxXvbitclriW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun lasxXvbitclriD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun lasxXvbitsetiB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvbitsetiH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun lasxXvbitsetiW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun lasxXvbitsetiD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun lasxXvbitreviB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvbitreviH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun lasxXvbitreviW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun lasxXvbitreviD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun lasxXvsubiBu(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun lasxXvsubiHu(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun lasxXvsubiWu(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvsubiDu(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvsatB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun lasxXvsatH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun lasxXvsatW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvsatD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvsatBu(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvsatHu(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun lasxXvsatWu(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun lasxXvsatDu(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun lasxXvdivB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun lasxXvdivH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun lasxXvdivW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvdivD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvdivBu(a: V32u8, b: V32u8): V32u8 =
    M256i()

public actual fun lasxXvdivHu(a: V16u16, b: V16u16): V16u16 =
    M256i()

public actual fun lasxXvdivWu(a: V8u32, b: V8u32): V8u32 =
    M256i()

public actual fun lasxXvdivDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun lasxXvhaddwHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvhaddwWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvhaddwDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16 =
    M256i()

public actual fun lasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32 =
    M256i()

public actual fun lasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64 =
    M256i()

public actual fun lasxXvhsubwHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvhsubwWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvhsubwDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvmodB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun lasxXvmodH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun lasxXvmodW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvmodD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmodBu(a: V32u8, b: V32u8): V32u8 =
    M256i()

public actual fun lasxXvmodHu(a: V16u16, b: V16u16): V16u16 =
    M256i()

public actual fun lasxXvmodWu(a: V8u32, b: V8u32): V8u32 =
    M256i()

public actual fun lasxXvmodDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun lasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    M256i()

public actual fun lasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    M256i()

public actual fun lasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32 =
    M256i()

public actual fun lasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun lasxXvandiB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvnoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvxoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun lasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8 =
    M256i()

public actual fun lasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8 =
    M256i()

public actual fun lasxXvcloB(a: V32i8): V32i8 =
    M256i()

public actual fun lasxXvcloH(a: V16i16): V16i16 =
    M256i()

public actual fun lasxXvcloW(a: V8i32): V8i32 =
    M256i()

public actual fun lasxXvcloD(a: V4i64): V4i64 =
    M256i()

public actual fun lasxXvfcvtHS(a: V8f32, b: V8f32): V16i16 =
    M256i()

public actual fun lasxXvfcvtSD(a: V4f64, b: V4f64): V8f32 =
    M256()

public actual fun lasxXvfminS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun lasxXvfminD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfminaS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun lasxXvfminaD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfmaxS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun lasxXvfmaxD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfmaxaS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun lasxXvfmaxaD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfclassS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfclassD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfrecipeS(a: V8f32): V8f32 =
    M256()

public actual fun lasxXvfrecipeD(a: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfrsqrteS(a: V8f32): V8f32 =
    M256()

public actual fun lasxXvfrsqrteD(a: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfrintS(a: V8f32): V8f32 =
    M256()

public actual fun lasxXvfrintD(a: V4f64): V4f64 =
    M256d()

public actual fun lasxXvflogbS(a: V8f32): V8f32 =
    M256()

public actual fun lasxXvflogbD(a: V4f64): V4f64 =
    M256d()

public actual fun lasxXvfcvthSH(a: V16i16): V8f32 =
    M256()

public actual fun lasxXvfcvthDS(a: V8f32): V4f64 =
    M256d()

public actual fun lasxXvfcvtlSH(a: V16i16): V8f32 =
    M256()

public actual fun lasxXvfcvtlDS(a: V8f32): V4f64 =
    M256d()

public actual fun lasxXvftintWS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvftintLD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvftintWuS(a: V8f32): V8u32 =
    M256i()

public actual fun lasxXvftintLuD(a: V4f64): V4u64 =
    M256i()

public actual fun lasxXvftintrzWS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvftintrzLD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvftintrzWuS(a: V8f32): V8u32 =
    M256i()

public actual fun lasxXvftintrzLuD(a: V4f64): V4u64 =
    M256i()

public actual fun lasxXvffintSW(a: V8i32): V8f32 =
    M256()

public actual fun lasxXvffintDL(a: V4i64): V4f64 =
    M256d()

public actual fun lasxXvffintSWu(a: V8u32): V8f32 =
    M256()

public actual fun lasxXvffintDLu(a: V4u64): V4f64 =
    M256d()

public actual fun lasxXvreplveB(a: V32i8, b: Int): V32i8 =
    M256i()

public actual fun lasxXvreplveH(a: V16i16, b: Int): V16i16 =
    M256i()

public actual fun lasxXvreplveW(a: V8i32, b: Int): V8i32 =
    M256i()

public actual fun lasxXvreplveD(a: V4i64, b: Int): V4i64 =
    M256i()

public actual fun lasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvsllwilHB(a: V32i8, b: UInt): V16i16 =
    M256i()

public actual fun lasxXvsllwilWH(a: V16i16, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvsllwilDW(a: V8i32, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16 =
    M256i()

public actual fun lasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32 =
    M256i()

public actual fun lasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64 =
    M256i()

public actual fun lasxXvsranBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvsranHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvsranWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssranBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvssranHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvssranWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssranBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun lasxXvssranHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun lasxXvssranWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun lasxXvsrarnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvsrarnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvsrarnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssrarnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvssrarnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvssrarnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun lasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun lasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun lasxXvsrlnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvsrlnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvsrlnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun lasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun lasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun lasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun lasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun lasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun lasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    M256i()

public actual fun lasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    M256i()

public actual fun lasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvmskltzB(a: V32i8): V32i8 =
    M256i()

public actual fun lasxXvmskltzH(a: V16i16): V16i16 =
    M256i()

public actual fun lasxXvmskltzW(a: V8i32): V8i32 =
    M256i()

public actual fun lasxXvmskltzD(a: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsigncovB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun lasxXvsigncovH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun lasxXvsigncovW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvsigncovD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvftintrneWS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvftintrneLD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvftintrpWS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvftintrpLD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvftintrmWS(a: V8f32): V8i32 =
    M256i()

public actual fun lasxXvftintrmLD(a: V4f64): V4i64 =
    M256i()

public actual fun lasxXvftintWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun lasxXvffintSL(a: V4i64, b: V4i64): V8f32 =
    M256()

public actual fun lasxXvftintrzWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun lasxXvftintrpWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun lasxXvftintrmWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun lasxXvftintrneWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun lasxXvftinthLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintlLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvffinthDW(a: V8i32): V4f64 =
    M256d()

public actual fun lasxXvffintlDW(a: V8i32): V4f64 =
    M256d()

public actual fun lasxXvftintrzhLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrzlLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrphLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrplLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrmhLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrmlLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrnehLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvftintrnelLS(a: V8f32): V4i64 =
    M256i()

public actual fun lasxXvfrintrneS(a: V8f32): V8f32 =
    M256()

public actual fun lasxXvfrintrneD(a: V4f64): V4f64 =
    M256d()

public actual fun lasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt) {
}

public actual fun lasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvpickveW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun lasxXvpickveD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvssrlnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun lasxXvssrlnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun lasxXvssrlnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun lasxXvextlQuDu(a: V4u64): V4u64 =
    M256i()

public actual fun lasxVext2xvHB(a: V32i8): V16i16 =
    M256i()

public actual fun lasxVext2xvWH(a: V16i16): V8i32 =
    M256i()

public actual fun lasxVext2xvDW(a: V8i32): V4i64 =
    M256i()

public actual fun lasxVext2xvWB(a: V32i8): V8i32 =
    M256i()

public actual fun lasxVext2xvDH(a: V16i16): V4i64 =
    M256i()

public actual fun lasxVext2xvDB(a: V32i8): V4i64 =
    M256i()

public actual fun lasxVext2xvHuBu(a: V32i8): V16i16 =
    M256i()

public actual fun lasxVext2xvWuHu(a: V16i16): V8i32 =
    M256i()

public actual fun lasxVext2xvDuWu(a: V8i32): V4i64 =
    M256i()

public actual fun lasxVext2xvWuBu(a: V32i8): V8i32 =
    M256i()

public actual fun lasxVext2xvDuHu(a: V16i16): V4i64 =
    M256i()

public actual fun lasxVext2xvDuBu(a: V32i8): V4i64 =
    M256i()

public actual fun lasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvpermiD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun lasxXvpermW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun lasxXvldreplB(a: ConstPtr, b: Int): V32i8 =
    M256i()

public actual fun lasxXvldreplH(a: ConstPtr, b: Int): V16i16 =
    M256i()

public actual fun lasxXvldreplW(a: ConstPtr, b: Int): V8i32 =
    M256i()

public actual fun lasxXvldreplD(a: ConstPtr, b: Int): V4i64 =
    M256i()

public actual fun lasxXvaddwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvaddwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvaddwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvaddwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvsubwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsubwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvsubwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvsubwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvmulwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmulwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmulwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmulwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvaddwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvaddwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvaddwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvaddwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvsubwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsubwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvsubwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvsubwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvmulwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmulwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmulwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmulwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun lasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun lasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun lasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun lasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun lasxXvhaddwQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun lasxXvhsubwQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun lasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    M256i()

public actual fun lasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    M256i()

public actual fun lasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    M256i()

public actual fun lasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    M256i()

public actual fun lasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    M256i()

public actual fun lasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    M256i()

public actual fun lasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    M256i()

public actual fun lasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    M256i()

public actual fun lasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    M256i()

public actual fun lasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    M256i()

public actual fun lasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    M256i()

public actual fun lasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    M256i()

public actual fun lasxXvaddQ(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsubQ(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun lasxXvmskgezB(a: V32i8): V32i8 =
    M256i()

public actual fun lasxXvmsknzB(a: V32i8): V32i8 =
    M256i()

public actual fun lasxXvexthHB(a: V32i8): V16i16 =
    M256i()

public actual fun lasxXvexthWH(a: V16i16): V8i32 =
    M256i()

public actual fun lasxXvexthDW(a: V8i32): V4i64 =
    M256i()

public actual fun lasxXvexthQD(a: V4i64): V4i64 =
    M256i()

public actual fun lasxXvexthHuBu(a: V32u8): V16u16 =
    M256i()

public actual fun lasxXvexthWuHu(a: V16u16): V8u32 =
    M256i()

public actual fun lasxXvexthDuWu(a: V8u32): V4u64 =
    M256i()

public actual fun lasxXvexthQuDu(a: V4u64): V4u64 =
    M256i()

public actual fun lasxXvextlQD(a: V4i64): V4i64 =
    M256i()

public actual fun lasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun lasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun lasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun lasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun lasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun lasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun lasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun lasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun lasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun lasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun lasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun lasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun lasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun lasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun lasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun lasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun lasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun lasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun lasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun lasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun lasxXbnzB(a: V32u8): Int =
    0

public actual fun lasxXbnzD(a: V4u64): Int =
    0

public actual fun lasxXbnzH(a: V16u16): Int =
    0

public actual fun lasxXbnzV(a: V32u8): Int =
    0

public actual fun lasxXbnzW(a: V8u32): Int =
    0

public actual fun lasxXbzB(a: V32u8): Int =
    0

public actual fun lasxXbzD(a: V4u64): Int =
    0

public actual fun lasxXbzH(a: V16u16): Int =
    0

public actual fun lasxXbzV(a: V32u8): Int =
    0

public actual fun lasxXbzW(a: V8u32): Int =
    0

public actual fun lasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun lasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun lasxXvpickveDF(a: V4f64, b: UInt): V4f64 =
    M256d()

public actual fun lasxXvpickveWF(a: V8f32, b: UInt): V8f32 =
    M256()

public actual fun lasxCast128S(a: V4f32): V8f32 =
    M256()

public actual fun lasxCast128D(a: V2f64): V4f64 =
    M256d()

public actual fun lasxCast128(a: V2i64): V4i64 =
    M256i()

public actual fun lasxConcat128S(a: V4f32, b: V4f32): V8f32 =
    M256()

public actual fun lasxConcat128D(a: V2f64, b: V2f64): V4f64 =
    M256d()

public actual fun lasxConcat128(a: V2i64, b: V2i64): V4i64 =
    M256i()

public actual fun lasxExtract128LoS(a: V8f32): V4f32 =
    M128()

public actual fun lasxExtract128HiS(a: V8f32): V4f32 =
    M128()

public actual fun lasxExtract128LoD(a: V4f64): V2f64 =
    M128d()

public actual fun lasxExtract128HiD(a: V4f64): V2f64 =
    M128d()

public actual fun lasxExtract128Lo(a: V4i64): V2i64 =
    M128i()

public actual fun lasxExtract128Hi(a: V4i64): V2i64 =
    M128i()

public actual fun lasxInsert128LoS(a: V8f32, b: V4f32): V8f32 =
    M256()

public actual fun lasxInsert128HiS(a: V8f32, b: V4f32): V8f32 =
    M256()

public actual fun lasxInsert128LoD(a: V4f64, b: V2f64): V4f64 =
    M256d()

public actual fun lasxInsert128HiD(a: V4f64, b: V2f64): V4f64 =
    M256d()

public actual fun lasxInsert128Lo(a: V4i64, b: V2i64): V4i64 =
    M256i()

public actual fun lasxInsert128Hi(a: V4i64, b: V2i64): V4i64 =
    M256i()

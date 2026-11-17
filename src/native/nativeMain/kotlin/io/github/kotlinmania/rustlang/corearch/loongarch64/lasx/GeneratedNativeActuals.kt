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

public actual fun externLasxXvsrarB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvsrarH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvsrarW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvsrarD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsrariB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsrariH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsrariW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsrariD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsrlrB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvsrlrH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvsrlrW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvsrlrD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsrlriB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsrlriH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsrlriW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsrlriD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvbitclriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvbitclriH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun externLasxXvbitclriW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun externLasxXvbitclriD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun externLasxXvbitsetiB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvbitsetiH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun externLasxXvbitsetiW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun externLasxXvbitsetiD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun externLasxXvbitreviB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvbitreviH(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun externLasxXvbitreviW(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun externLasxXvbitreviD(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun externLasxXvsubiBu(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsubiHu(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsubiWu(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsubiDu(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsatB(a: V32i8, b: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsatH(a: V16i16, b: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsatW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsatD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsatBu(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvsatHu(a: V16u16, b: UInt): V16u16 =
    M256i()

public actual fun externLasxXvsatWu(a: V8u32, b: UInt): V8u32 =
    M256i()

public actual fun externLasxXvsatDu(a: V4u64, b: UInt): V4u64 =
    M256i()

public actual fun externLasxXvdivB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvdivH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvdivW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvdivD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvdivBu(a: V32u8, b: V32u8): V32u8 =
    M256i()

public actual fun externLasxXvdivHu(a: V16u16, b: V16u16): V16u16 =
    M256i()

public actual fun externLasxXvdivWu(a: V8u32, b: V8u32): V8u32 =
    M256i()

public actual fun externLasxXvdivDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvhaddwHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvhaddwWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvhaddwDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16 =
    M256i()

public actual fun externLasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32 =
    M256i()

public actual fun externLasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64 =
    M256i()

public actual fun externLasxXvhsubwHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvhsubwWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvhsubwDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvmodB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvmodH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvmodW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvmodD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmodBu(a: V32u8, b: V32u8): V32u8 =
    M256i()

public actual fun externLasxXvmodHu(a: V16u16, b: V16u16): V16u16 =
    M256i()

public actual fun externLasxXvmodWu(a: V8u32, b: V8u32): V8u32 =
    M256i()

public actual fun externLasxXvmodDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvandiB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvnoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvxoriB(a: V32u8, b: UInt): V32u8 =
    M256i()

public actual fun externLasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8 =
    M256i()

public actual fun externLasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8 =
    M256i()

public actual fun externLasxXvcloB(a: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvcloH(a: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvcloW(a: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvcloD(a: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvfcvtHS(a: V8f32, b: V8f32): V16i16 =
    M256i()

public actual fun externLasxXvfcvtSD(a: V4f64, b: V4f64): V8f32 =
    M256()

public actual fun externLasxXvfminS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfminD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfminaS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfminaD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfmaxS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfmaxD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfmaxaS(a: V8f32, b: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfmaxaD(a: V4f64, b: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfclassS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfclassD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfrecipeS(a: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfrecipeD(a: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfrsqrteS(a: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfrsqrteD(a: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfrintS(a: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfrintD(a: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvflogbS(a: V8f32): V8f32 =
    M256()

public actual fun externLasxXvflogbD(a: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvfcvthSH(a: V16i16): V8f32 =
    M256()

public actual fun externLasxXvfcvthDS(a: V8f32): V4f64 =
    M256d()

public actual fun externLasxXvfcvtlSH(a: V16i16): V8f32 =
    M256()

public actual fun externLasxXvfcvtlDS(a: V8f32): V4f64 =
    M256d()

public actual fun externLasxXvftintWS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvftintLD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvftintWuS(a: V8f32): V8u32 =
    M256i()

public actual fun externLasxXvftintLuD(a: V4f64): V4u64 =
    M256i()

public actual fun externLasxXvftintrzWS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvftintrzLD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvftintrzWuS(a: V8f32): V8u32 =
    M256i()

public actual fun externLasxXvftintrzLuD(a: V4f64): V4u64 =
    M256i()

public actual fun externLasxXvffintSW(a: V8i32): V8f32 =
    M256()

public actual fun externLasxXvffintDL(a: V4i64): V4f64 =
    M256d()

public actual fun externLasxXvffintSWu(a: V8u32): V8f32 =
    M256()

public actual fun externLasxXvffintDLu(a: V4u64): V4f64 =
    M256d()

public actual fun externLasxXvreplveB(a: V32i8, b: Int): V32i8 =
    M256i()

public actual fun externLasxXvreplveH(a: V16i16, b: Int): V16i16 =
    M256i()

public actual fun externLasxXvreplveW(a: V8i32, b: Int): V8i32 =
    M256i()

public actual fun externLasxXvreplveD(a: V4i64, b: Int): V4i64 =
    M256i()

public actual fun externLasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsllwilHB(a: V32i8, b: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsllwilWH(a: V16i16, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsllwilDW(a: V8i32, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16 =
    M256i()

public actual fun externLasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32 =
    M256i()

public actual fun externLasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64 =
    M256i()

public actual fun externLasxXvsranBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvsranHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvsranWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssranBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvssranHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvssranWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssranBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun externLasxXvssranHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun externLasxXvssranWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun externLasxXvsrarnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvsrarnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvsrarnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssrarnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvssrarnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvssrarnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun externLasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun externLasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun externLasxXvsrlnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvsrlnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvsrlnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun externLasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun externLasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun externLasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8 =
    M256i()

public actual fun externLasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16 =
    M256i()

public actual fun externLasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32 =
    M256i()

public actual fun externLasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvmskltzB(a: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvmskltzH(a: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvmskltzW(a: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvmskltzD(a: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsigncovB(a: V32i8, b: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvsigncovH(a: V16i16, b: V16i16): V16i16 =
    M256i()

public actual fun externLasxXvsigncovW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvsigncovD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvftintrneWS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvftintrneLD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvftintrpWS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvftintrpLD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvftintrmWS(a: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvftintrmLD(a: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvftintWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun externLasxXvffintSL(a: V4i64, b: V4i64): V8f32 =
    M256()

public actual fun externLasxXvftintrzWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun externLasxXvftintrpWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun externLasxXvftintrmWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun externLasxXvftintrneWD(a: V4f64, b: V4f64): V8i32 =
    M256i()

public actual fun externLasxXvftinthLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintlLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvffinthDW(a: V8i32): V4f64 =
    M256d()

public actual fun externLasxXvffintlDW(a: V8i32): V4f64 =
    M256d()

public actual fun externLasxXvftintrzhLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrzlLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrphLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrplLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrmhLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrmlLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrnehLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvftintrnelLS(a: V8f32): V4i64 =
    M256i()

public actual fun externLasxXvfrintrneS(a: V8f32): V8f32 =
    M256()

public actual fun externLasxXvfrintrneD(a: V4f64): V4f64 =
    M256d()

public actual fun externLasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt) {
}

public actual fun externLasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt) {
}

public actual fun externLasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt) {
}

public actual fun externLasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt) {
}

public actual fun externLasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvpickveW(a: V8i32, b: UInt): V8i32 =
    M256i()

public actual fun externLasxXvpickveD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvssrlnBH(a: V16i16, b: V16i16): V32i8 =
    M256i()

public actual fun externLasxXvssrlnHW(a: V8i32, b: V8i32): V16i16 =
    M256i()

public actual fun externLasxXvssrlnWD(a: V4i64, b: V4i64): V8i32 =
    M256i()

public actual fun externLasxXvextlQuDu(a: V4u64): V4u64 =
    M256i()

public actual fun externLasxVext2xvHB(a: V32i8): V16i16 =
    M256i()

public actual fun externLasxVext2xvWH(a: V16i16): V8i32 =
    M256i()

public actual fun externLasxVext2xvDW(a: V8i32): V4i64 =
    M256i()

public actual fun externLasxVext2xvWB(a: V32i8): V8i32 =
    M256i()

public actual fun externLasxVext2xvDH(a: V16i16): V4i64 =
    M256i()

public actual fun externLasxVext2xvDB(a: V32i8): V4i64 =
    M256i()

public actual fun externLasxVext2xvHuBu(a: V32i8): V16i16 =
    M256i()

public actual fun externLasxVext2xvWuHu(a: V16i16): V8i32 =
    M256i()

public actual fun externLasxVext2xvDuWu(a: V8i32): V4i64 =
    M256i()

public actual fun externLasxVext2xvWuBu(a: V32i8): V8i32 =
    M256i()

public actual fun externLasxVext2xvDuHu(a: V16i16): V4i64 =
    M256i()

public actual fun externLasxVext2xvDuBu(a: V32i8): V4i64 =
    M256i()

public actual fun externLasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvpermiD(a: V4i64, b: UInt): V4i64 =
    M256i()

public actual fun externLasxXvpermW(a: V8i32, b: V8i32): V8i32 =
    M256i()

public actual fun externLasxXvldreplB(a: ConstPtr, b: Int): V32i8 =
    M256i()

public actual fun externLasxXvldreplH(a: ConstPtr, b: Int): V16i16 =
    M256i()

public actual fun externLasxXvldreplW(a: ConstPtr, b: Int): V8i32 =
    M256i()

public actual fun externLasxXvldreplD(a: ConstPtr, b: Int): V4i64 =
    M256i()

public actual fun externLasxXvaddwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvaddwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvaddwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvaddwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvsubwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsubwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvsubwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvsubwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvmulwevQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmulwevDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmulwevWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmulwevHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvaddwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvaddwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvaddwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvaddwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvsubwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsubwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvsubwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvsubwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvmulwodQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmulwodDW(a: V8i32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmulwodWH(a: V16i16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmulwodHB(a: V32i8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64 =
    M256i()

public actual fun externLasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64 =
    M256i()

public actual fun externLasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32 =
    M256i()

public actual fun externLasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16 =
    M256i()

public actual fun externLasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvhaddwQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvhsubwQD(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    M256i()

public actual fun externLasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    M256i()

public actual fun externLasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    M256i()

public actual fun externLasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    M256i()

public actual fun externLasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    M256i()

public actual fun externLasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    M256i()

public actual fun externLasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvaddQ(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsubQ(a: V4i64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvmskgezB(a: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvmsknzB(a: V32i8): V32i8 =
    M256i()

public actual fun externLasxXvexthHB(a: V32i8): V16i16 =
    M256i()

public actual fun externLasxXvexthWH(a: V16i16): V8i32 =
    M256i()

public actual fun externLasxXvexthDW(a: V8i32): V4i64 =
    M256i()

public actual fun externLasxXvexthQD(a: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvexthHuBu(a: V32u8): V16u16 =
    M256i()

public actual fun externLasxXvexthWuHu(a: V16u16): V8u32 =
    M256i()

public actual fun externLasxXvexthDuWu(a: V8u32): V4u64 =
    M256i()

public actual fun externLasxXvexthQuDu(a: V4u64): V4u64 =
    M256i()

public actual fun externLasxXvextlQD(a: V4i64): V4i64 =
    M256i()

public actual fun externLasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun externLasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun externLasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun externLasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun externLasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun externLasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun externLasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun externLasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun externLasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun externLasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun externLasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun externLasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun externLasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    M256i()

public actual fun externLasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    M256i()

public actual fun externLasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    M256i()

public actual fun externLasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    M256i()

public actual fun externLasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    M256i()

public actual fun externLasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    M256i()

public actual fun externLasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    M256i()

public actual fun externLasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    M256i()

public actual fun externLasxXbnzB(a: V32u8): Int =
    0

public actual fun externLasxXbnzD(a: V4u64): Int =
    0

public actual fun externLasxXbnzH(a: V16u16): Int =
    0

public actual fun externLasxXbnzV(a: V32u8): Int =
    0

public actual fun externLasxXbnzW(a: V8u32): Int =
    0

public actual fun externLasxXbzB(a: V32u8): Int =
    0

public actual fun externLasxXbzD(a: V4u64): Int =
    0

public actual fun externLasxXbzH(a: V16u16): Int =
    0

public actual fun externLasxXbzV(a: V32u8): Int =
    0

public actual fun externLasxXbzW(a: V8u32): Int =
    0

public actual fun externLasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64 =
    M256i()

public actual fun externLasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32 =
    M256i()

public actual fun externLasxXvpickveDF(a: V4f64, b: UInt): V4f64 =
    M256d()

public actual fun externLasxXvpickveWF(a: V8f32, b: UInt): V8f32 =
    M256()

public actual fun externLasxCast128S(a: V4f32): V8f32 =
    M256()

public actual fun externLasxCast128D(a: V2f64): V4f64 =
    M256d()

public actual fun externLasxCast128(a: V2i64): V4i64 =
    M256i()

public actual fun externLasxConcat128S(a: V4f32, b: V4f32): V8f32 =
    M256()

public actual fun externLasxConcat128D(a: V2f64, b: V2f64): V4f64 =
    M256d()

public actual fun externLasxConcat128(a: V2i64, b: V2i64): V4i64 =
    M256i()

public actual fun externLasxExtract128LoS(a: V8f32): V4f32 =
    M128()

public actual fun externLasxExtract128HiS(a: V8f32): V4f32 =
    M128()

public actual fun externLasxExtract128LoD(a: V4f64): V2f64 =
    M128d()

public actual fun externLasxExtract128HiD(a: V4f64): V2f64 =
    M128d()

public actual fun externLasxExtract128Lo(a: V4i64): V2i64 =
    M128i()

public actual fun externLasxExtract128Hi(a: V4i64): V2i64 =
    M128i()

public actual fun externLasxInsert128LoS(a: V8f32, b: V4f32): V8f32 =
    M256()

public actual fun externLasxInsert128HiS(a: V8f32, b: V4f32): V8f32 =
    M256()

public actual fun externLasxInsert128LoD(a: V4f64, b: V2f64): V4f64 =
    M256d()

public actual fun externLasxInsert128HiD(a: V4f64, b: V2f64): V4f64 =
    M256d()

public actual fun externLasxInsert128Lo(a: V4i64, b: V2i64): V4i64 =
    M256i()

public actual fun externLasxInsert128Hi(a: V4i64, b: V2i64): V4i64 =
    M256i()

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object MipsMsaJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchMipsMsaExternMsaCfcmsa(a: Int): Int

    @JvmStatic
    external fun corearchMipsMsaExternMsaCtcmsa(imm5: Int, a: Int)
}

public actual fun externMsaAddAB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAddAH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAddAW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAddAD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAddsAB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAddsAH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAddsAW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAddsAD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAddsSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAddsSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAddsSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAddsSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAddsUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaAddsUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaAddsUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaAddsUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaAddvB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAddvH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAddvW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAddvD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAddviB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaAddviH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaAddviW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaAddviD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaAndV(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaAndiB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaAsubSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAsubSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAsubSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAsubSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAsubUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaAsubUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaAsubUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaAsubUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaAveSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAveSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAveSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAveSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAveUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaAveUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaAveUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaAveUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaAverSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaAverSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaAverSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaAverSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaAverUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaAverUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaAverUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaAverUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBclrB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBclrH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaBclrW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaBclrD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBclriB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaBclriH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaBclriW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaBclriD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    V8u16()

public actual fun externMsaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    V4u32()

public actual fun externMsaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    V16u8()

public actual fun externMsaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    V8u16()

public actual fun externMsaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    V4u32()

public actual fun externMsaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    V2u64()

public actual fun externMsaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    V8u16()

public actual fun externMsaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    V4u32()

public actual fun externMsaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    V16u8()

public actual fun externMsaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    V8u16()

public actual fun externMsaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    V4u32()

public actual fun externMsaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    V2u64()

public actual fun externMsaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    V16u8()

public actual fun externMsaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    V16u8()

public actual fun externMsaBnegB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBnegH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaBnegW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaBnegD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBnegiB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaBnegiH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaBnegiW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaBnegiD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaBnzB(a: V16u8): Int =
    0

public actual fun externMsaBnzH(a: V8u16): Int =
    0

public actual fun externMsaBnzW(a: V4u32): Int =
    0

public actual fun externMsaBnzD(a: V2u64): Int =
    0

public actual fun externMsaBnzV(a: V16u8): Int =
    0

public actual fun externMsaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    V16u8()

public actual fun externMsaBsetB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaBsetH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaBsetW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaBsetD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaBsetiB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaBsetiH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaBsetiW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaBsetiD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaBzB(a: V16u8): Int =
    0

public actual fun externMsaBzH(a: V8u16): Int =
    0

public actual fun externMsaBzW(a: V4u32): Int =
    0

public actual fun externMsaBzD(a: V2u64): Int =
    0

public actual fun externMsaBzV(a: V16u8): Int =
    0

public actual fun externMsaCeqB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaCeqH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaCeqW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaCeqD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaCeqiB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaCeqiH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaCeqiW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaCeqiD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaCfcmsa(a: Int): Int =
    MipsMsaJniBridge.corearchMipsMsaExternMsaCfcmsa(a)

public actual fun externMsaCleSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaCleSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaCleSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaCleSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaCleUB(a: V16u8, b: V16u8): V16i8 =
    V16i8()

public actual fun externMsaCleUH(a: V8u16, b: V8u16): V8i16 =
    V8i16()

public actual fun externMsaCleUW(a: V4u32, b: V4u32): V4i32 =
    V4i32()

public actual fun externMsaCleUD(a: V2u64, b: V2u64): V2i64 =
    V2i64()

public actual fun externMsaCleiSB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaCleiSH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaCleiSW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaCleiSD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaCleiUB(a: V16u8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaCleiUH(a: V8u16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaCleiUW(a: V4u32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaCleiUD(a: V2u64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaCltSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaCltSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaCltSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaCltSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaCltUB(a: V16u8, b: V16u8): V16i8 =
    V16i8()

public actual fun externMsaCltUH(a: V8u16, b: V8u16): V8i16 =
    V8i16()

public actual fun externMsaCltUW(a: V4u32, b: V4u32): V4i32 =
    V4i32()

public actual fun externMsaCltUD(a: V2u64, b: V2u64): V2i64 =
    V2i64()

public actual fun externMsaCltiSB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaCltiSH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaCltiSW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaCltiSD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaCltiUB(a: V16u8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaCltiUH(a: V8u16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaCltiUW(a: V4u32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaCltiUD(a: V2u64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaCopySB(a: V16i8, b: Int): Int =
    0

public actual fun externMsaCopySH(a: V8i16, b: Int): Int =
    0

public actual fun externMsaCopySW(a: V4i32, b: Int): Int =
    0

public actual fun externMsaCopySD(a: V2i64, b: Int): Long =
    0L

public actual fun externMsaCopyUB(a: V16i8, b: Int): UInt =
    0u

public actual fun externMsaCopyUH(a: V8i16, b: Int): UInt =
    0u

public actual fun externMsaCopyUW(a: V4i32, b: Int): UInt =
    0u

public actual fun externMsaCopyUD(a: V2i64, b: Int): ULong =
    0uL

public actual fun externMsaCtcmsa(imm5: Int, a: Int) {
    MipsMsaJniBridge.corearchMipsMsaExternMsaCtcmsa(imm5, a)
}

public actual fun externMsaDivSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaDivSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaDivSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaDivSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaDivUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaDivUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaDivUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaDivUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaDotpSH(a: V16i8, b: V16i8): V8i16 =
    V8i16()

public actual fun externMsaDotpSW(a: V8i16, b: V8i16): V4i32 =
    V4i32()

public actual fun externMsaDotpSD(a: V4i32, b: V4i32): V2i64 =
    V2i64()

public actual fun externMsaDotpUH(a: V16u8, b: V16u8): V8u16 =
    V8u16()

public actual fun externMsaDotpUW(a: V8u16, b: V8u16): V4u32 =
    V4u32()

public actual fun externMsaDotpUD(a: V4u32, b: V4u32): V2u64 =
    V2u64()

public actual fun externMsaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    V8i16()

public actual fun externMsaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    V4i32()

public actual fun externMsaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    V2i64()

public actual fun externMsaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    V8u16()

public actual fun externMsaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    V4u32()

public actual fun externMsaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    V2u64()

public actual fun externMsaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    V8i16()

public actual fun externMsaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    V4i32()

public actual fun externMsaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    V2i64()

public actual fun externMsaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    V8i16()

public actual fun externMsaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    V4i32()

public actual fun externMsaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    V2i64()

public actual fun externMsaFaddW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFaddD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFcafW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcafD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFceqW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFceqD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFclassW(a: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFclassD(a: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcleW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcleD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcltW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcltD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcneW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcneD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcorW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcorD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcueqW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcueqD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFculeW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFculeD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcultW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcultD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcunW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcunD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFcuneW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFcuneD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFdivW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFdivD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFexdoW(a: V2f64, b: V2f64): V4f32 =
    V4f32()

public actual fun externMsaFexp2W(a: V4f32, b: V4i32): V4f32 =
    V4f32()

public actual fun externMsaFexp2D(a: V2f64, b: V2i64): V2f64 =
    V2f64()

public actual fun externMsaFexuplD(a: V4f32): V2f64 =
    V2f64()

public actual fun externMsaFexuprD(a: V4f32): V2f64 =
    V2f64()

public actual fun externMsaFfintSW(a: V4i32): V4f32 =
    V4f32()

public actual fun externMsaFfintSD(a: V2i64): V2f64 =
    V2f64()

public actual fun externMsaFfintUW(a: V4u32): V4f32 =
    V4f32()

public actual fun externMsaFfintUD(a: V2u64): V2f64 =
    V2f64()

public actual fun externMsaFfqlW(a: V8i16): V4f32 =
    V4f32()

public actual fun externMsaFfqlD(a: V4i32): V2f64 =
    V2f64()

public actual fun externMsaFfqrW(a: V8i16): V4f32 =
    V4f32()

public actual fun externMsaFfqrD(a: V4i32): V2f64 =
    V2f64()

public actual fun externMsaFillB(a: Int): V16i8 =
    V16i8()

public actual fun externMsaFillH(a: Int): V8i16 =
    V8i16()

public actual fun externMsaFillW(a: Int): V4i32 =
    V4i32()

public actual fun externMsaFillD(a: Long): V2i64 =
    V2i64()

public actual fun externMsaFlog2W(a: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFlog2D(a: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFmaxW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFmaxD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFminW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFminD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFminAW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFminAD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFmulW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFmulD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFrintW(a: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFrintD(a: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFrcpW(a: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFrcpD(a: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFrsqrtW(a: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFrsqrtD(a: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFsafW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsafD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFseqW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFseqD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsleW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsleD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsltW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsltD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsneW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsneD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsorW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsorD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsqrtW(a: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFsqrtD(a: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFsubW(a: V4f32, b: V4f32): V4f32 =
    V4f32()

public actual fun externMsaFsubD(a: V2f64, b: V2f64): V2f64 =
    V2f64()

public actual fun externMsaFsueqW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsueqD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsuleW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsuleD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsultW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsultD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsunW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsunD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFsuneW(a: V4f32, b: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFsuneD(a: V2f64, b: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFtintSW(a: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFtintSD(a: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFtintUW(a: V4f32): V4u32 =
    V4u32()

public actual fun externMsaFtintUD(a: V2f64): V2u64 =
    V2u64()

public actual fun externMsaFtqH(a: V4f32, b: V4f32): V8i16 =
    V8i16()

public actual fun externMsaFtqW(a: V2f64, b: V2f64): V4i32 =
    V4i32()

public actual fun externMsaFtruncSW(a: V4f32): V4i32 =
    V4i32()

public actual fun externMsaFtruncSD(a: V2f64): V2i64 =
    V2i64()

public actual fun externMsaFtruncUW(a: V4f32): V4u32 =
    V4u32()

public actual fun externMsaFtruncUD(a: V2f64): V2u64 =
    V2u64()

public actual fun externMsaHaddSH(a: V16i8, b: V16i8): V8i16 =
    V8i16()

public actual fun externMsaHaddSW(a: V8i16, b: V8i16): V4i32 =
    V4i32()

public actual fun externMsaHaddSD(a: V4i32, b: V4i32): V2i64 =
    V2i64()

public actual fun externMsaHaddUH(a: V16u8, b: V16u8): V8u16 =
    V8u16()

public actual fun externMsaHaddUW(a: V8u16, b: V8u16): V4u32 =
    V4u32()

public actual fun externMsaHaddUD(a: V4u32, b: V4u32): V2u64 =
    V2u64()

public actual fun externMsaHsubSH(a: V16i8, b: V16i8): V8i16 =
    V8i16()

public actual fun externMsaHsubSW(a: V8i16, b: V8i16): V4i32 =
    V4i32()

public actual fun externMsaHsubSD(a: V4i32, b: V4i32): V2i64 =
    V2i64()

public actual fun externMsaHsubUH(a: V16u8, b: V16u8): V8i16 =
    V8i16()

public actual fun externMsaHsubUW(a: V8u16, b: V8u16): V4i32 =
    V4i32()

public actual fun externMsaHsubUD(a: V4u32, b: V4u32): V2i64 =
    V2i64()

public actual fun externMsaIlvevB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaIlvevH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaIlvevW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaIlvevD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaIlvlB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaIlvlH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaIlvlW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaIlvlD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaIlvodB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaIlvodH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaIlvodW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaIlvodD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaIlvrB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaIlvrH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaIlvrW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaIlvrD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaInsertB(a: V16i8, b: Int, c: Int): V16i8 =
    V16i8()

public actual fun externMsaInsertH(a: V8i16, b: Int, c: Int): V8i16 =
    V8i16()

public actual fun externMsaInsertW(a: V4i32, b: Int, c: Int): V4i32 =
    V4i32()

public actual fun externMsaInsertD(a: V2i64, b: Int, c: Long): V2i64 =
    V2i64()

public actual fun externMsaInsveB(a: V16i8, b: Int, c: V16i8): V16i8 =
    V16i8()

public actual fun externMsaInsveH(a: V8i16, b: Int, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaInsveW(a: V4i32, b: Int, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaInsveD(a: V2i64, b: Int, c: V2i64): V2i64 =
    V2i64()

public actual fun externMsaLdB(memAddr: MutPtr, b: Int): V16i8 =
    V16i8()

public actual fun externMsaLdH(memAddr: MutPtr, b: Int): V8i16 =
    V8i16()

public actual fun externMsaLdW(memAddr: MutPtr, b: Int): V4i32 =
    V4i32()

public actual fun externMsaLdD(memAddr: MutPtr, b: Int): V2i64 =
    V2i64()

public actual fun externMsaLdiB(a: Int): V16i8 =
    V16i8()

public actual fun externMsaLdiH(a: Int): V8i16 =
    V8i16()

public actual fun externMsaLdiW(a: Int): V4i32 =
    V4i32()

public actual fun externMsaLdiD(a: Int): V2i64 =
    V2i64()

public actual fun externMsaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMaxAB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMaxAH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMaxAW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMaxAD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMaxSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMaxSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMaxSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMaxSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMaxUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaMaxUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaMaxUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaMaxUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaMaxiSB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaMaxiSH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaMaxiSW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaMaxiSD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaMaxiUB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaMaxiUH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaMaxiUW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaMaxiUD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaMinAB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMinAH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMinAW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMinAD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMinSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMinSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMinSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMinSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMinUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaMinUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaMinUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaMinUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaMiniSB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaMiniSH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaMiniSW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaMiniSD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaMiniUB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaMiniUH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaMiniUW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaMiniUD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaModSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaModSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaModSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaModSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaModUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaModUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaModUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaModUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaMoveV(a: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    V2i64()

public actual fun externMsaMulQH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMulQW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMulrQH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMulrQW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMulvB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaMulvH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaMulvW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaMulvD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaNlocB(a: V16i8): V16i8 =
    V16i8()

public actual fun externMsaNlocH(a: V8i16): V8i16 =
    V8i16()

public actual fun externMsaNlocW(a: V4i32): V4i32 =
    V4i32()

public actual fun externMsaNlocD(a: V2i64): V2i64 =
    V2i64()

public actual fun externMsaNlzcB(a: V16i8): V16i8 =
    V16i8()

public actual fun externMsaNlzcH(a: V8i16): V8i16 =
    V8i16()

public actual fun externMsaNlzcW(a: V4i32): V4i32 =
    V4i32()

public actual fun externMsaNlzcD(a: V2i64): V2i64 =
    V2i64()

public actual fun externMsaNorV(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaNoriB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaOrV(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaOriB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaPckevB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaPckevH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaPckevW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaPckevD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaPckodB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaPckodH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaPckodW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaPckodD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaPcntB(a: V16i8): V16i8 =
    V16i8()

public actual fun externMsaPcntH(a: V8i16): V8i16 =
    V8i16()

public actual fun externMsaPcntW(a: V4i32): V4i32 =
    V4i32()

public actual fun externMsaPcntD(a: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSatSB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSatSH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSatSW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSatSD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSatUB(a: V16u8, b: Int): V16u8 =
    V16u8()

public actual fun externMsaSatUH(a: V8u16, b: Int): V8u16 =
    V8u16()

public actual fun externMsaSatUW(a: V4u32, b: Int): V4u32 =
    V4u32()

public actual fun externMsaSatUD(a: V2u64, b: Int): V2u64 =
    V2u64()

public actual fun externMsaShfB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaShfH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaShfW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    V16i8()

public actual fun externMsaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    V8i16()

public actual fun externMsaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    V4i32()

public actual fun externMsaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    V2i64()

public actual fun externMsaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    V16i8()

public actual fun externMsaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    V8i16()

public actual fun externMsaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    V4i32()

public actual fun externMsaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    V2i64()

public actual fun externMsaSllB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSllH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSllW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSllD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSlliB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSlliH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSlliW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSlliD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSplatB(a: V16i8, c: Int): V16i8 =
    V16i8()

public actual fun externMsaSplatH(a: V8i16, c: Int): V8i16 =
    V8i16()

public actual fun externMsaSplatW(a: V4i32, w: Int): V4i32 =
    V4i32()

public actual fun externMsaSplatD(a: V2i64, c: Int): V2i64 =
    V2i64()

public actual fun externMsaSplatiB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSplatiH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSplatiW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSplatiD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSraB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSraH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSraW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSraD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSraiB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSraiH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSraiW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSraiD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSrarB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSrarH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSrarW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSrarD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSrariB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSrariH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSrariW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSrariD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSrlB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSrlH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSrlW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSrlD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSrliB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSrliH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSrliW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSrliD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaSrlrB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSrlrH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSrlrW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSrlrD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSrlriB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSrlriH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSrlriW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSrlriD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
}

public actual fun externMsaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
}

public actual fun externMsaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
}

public actual fun externMsaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
}

public actual fun externMsaSubsSB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSubsSH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSubsSW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSubsSD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSubsUB(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaSubsUH(a: V8u16, b: V8u16): V8u16 =
    V8u16()

public actual fun externMsaSubsUW(a: V4u32, b: V4u32): V4u32 =
    V4u32()

public actual fun externMsaSubsUD(a: V2u64, b: V2u64): V2u64 =
    V2u64()

public actual fun externMsaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    V16u8()

public actual fun externMsaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    V8u16()

public actual fun externMsaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    V4u32()

public actual fun externMsaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    V2u64()

public actual fun externMsaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    V16i8()

public actual fun externMsaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    V8i16()

public actual fun externMsaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    V4i32()

public actual fun externMsaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    V2i64()

public actual fun externMsaSubvB(a: V16i8, b: V16i8): V16i8 =
    V16i8()

public actual fun externMsaSubvH(a: V8i16, b: V8i16): V8i16 =
    V8i16()

public actual fun externMsaSubvW(a: V4i32, b: V4i32): V4i32 =
    V4i32()

public actual fun externMsaSubvD(a: V2i64, b: V2i64): V2i64 =
    V2i64()

public actual fun externMsaSubviB(a: V16i8, b: Int): V16i8 =
    V16i8()

public actual fun externMsaSubviH(a: V8i16, b: Int): V8i16 =
    V8i16()

public actual fun externMsaSubviW(a: V4i32, b: Int): V4i32 =
    V4i32()

public actual fun externMsaSubviD(a: V2i64, b: Int): V2i64 =
    V2i64()

public actual fun externMsaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    V16i8()

public actual fun externMsaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    V8i16()

public actual fun externMsaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    V4i32()

public actual fun externMsaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    V2i64()

public actual fun externMsaXorV(a: V16u8, b: V16u8): V16u8 =
    V16u8()

public actual fun externMsaXoriB(a: V16u8, b: Int): V16u8 =
    V16u8()

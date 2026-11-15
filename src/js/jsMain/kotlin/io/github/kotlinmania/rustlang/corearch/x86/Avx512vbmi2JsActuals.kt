@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.I16x16
import io.github.kotlinmania.rustlang.simd.I16x32
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.I8x32
import io.github.kotlinmania.rustlang.simd.I8x64

public actual fun vcompressstorew(mem: MutPtr, data: I16x32, mask: UInt) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vcompressstorew256(mem: MutPtr, data: I16x16, mask: UShort) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vcompressstorew128(mem: MutPtr, data: I16x8, mask: UByte) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vcompressstoreb(mem: MutPtr, data: I8x64, mask: ULong) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vcompressstoreb256(mem: MutPtr, data: I8x32, mask: UInt) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vcompressstoreb128(mem: MutPtr, data: I8x16, mask: UShort) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vpcompressw(a: I16x32, src: I16x32, mask: UInt): I16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpcompressw256(a: I16x16, src: I16x16, mask: UShort): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpcompressw128(a: I16x8, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpcompressb(a: I8x64, src: I8x64, mask: ULong): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpcompressb256(a: I8x32, src: I8x32, mask: UInt): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpcompressb128(a: I8x16, src: I8x16, mask: UShort): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandw(a: I16x32, src: I16x32, mask: UInt): I16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandw256(a: I16x16, src: I16x16, mask: UShort): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandw128(a: I16x8, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandb(a: I8x64, src: I8x64, mask: ULong): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandb256(a: I8x32, src: I8x32, mask: UInt): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpexpandb128(a: I8x16, src: I8x16, mask: UShort): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadb128(memAddr: ConstPtr, a: I8x16, mask: UShort): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadw128(memAddr: ConstPtr, a: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadb256(memAddr: ConstPtr, a: I8x32, mask: UInt): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadw256(memAddr: ConstPtr, a: I16x16, mask: UShort): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadb512(memAddr: ConstPtr, a: I8x64, mask: ULong): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expandloadw512(memAddr: ConstPtr, a: I16x32, mask: UInt): I16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

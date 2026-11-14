@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public actual fun loadiwkey(integrityKey: M128i, keyLo: M128i, keyHi: M128i, control: UInt) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun encodekey128(keyMetadata: UInt, key: M128i): EncodeKey128Output =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun encodekey256(keyMetadata: UInt, keyLo: M128i, keyHi: M128i): EncodeKey256Output =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesenc128kl(data: M128i, handle: ConstPtr): AesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesdec128kl(data: M128i, handle: ConstPtr): AesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesenc256kl(data: M128i, handle: ConstPtr): AesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesdec256kl(data: M128i, handle: ConstPtr): AesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesencwide128kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesdecwide128kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesencwide256kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesdecwide256kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput =
    throw UnsupportedOperationException("Native FFI addon is not available")

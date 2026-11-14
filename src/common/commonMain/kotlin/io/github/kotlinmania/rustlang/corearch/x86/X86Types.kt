package io.github.kotlinmania.rustlang.corearch.x86

/**
 * The `__mmask64` type used in AVX-512 intrinsics, a 64-bit integer
 */
public typealias Mmask64 = ULong

/**
 * The `__mmask32` type used in AVX-512 intrinsics, a 32-bit integer
 */
public typealias Mmask32 = UInt

/**
 * The `__mmask16` type used in AVX-512 intrinsics, a 16-bit integer
 */
public typealias Mmask16 = UShort

/**
 * The `__mmask8` type used in AVX-512 intrinsics, a 8-bit integer
 */
public typealias Mmask8 = UByte

/**
 * The `_MM_CMPINT_ENUM` type used to specify comparison operations in AVX-512 intrinsics.
 */
public typealias MmCmpintEnum = Int

/**
 * The `MM_MANTISSA_NORM_ENUM` type used to specify mantissa normalized operations in AVX-512 intrinsics.
 */
public typealias MmMantissaNormEnum = Int

/**
 * The `MM_MANTISSA_SIGN_ENUM` type used to specify mantissa signed operations in AVX-512 intrinsics.
 */
public typealias MmMantissaSignEnum = Int

/**
 * The `MM_PERM_ENUM` type used to specify shuffle operations in AVX-512 intrinsics.
 */
public typealias MmPermEnum = Int

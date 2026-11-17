// port-lint: source ffi/primitives.rs
package io.github.kotlinmania.rustlang.core.ffi.primitives

// Defines primitive types that match C's type definitions for FFI compatibility.
//
// This module is intentionally standalone to facilitate parsing when retrieving
// core C types.

// `@doc(cfg(true))` is used to prevent rustdoc from displaying a "Available on ..." box.
// The implementation of these constants is target-specific, but every target does define them.
public typealias CChar = UByte

public typealias CSchar = Byte

public typealias CUchar = UByte

public typealias CShort = Short

public typealias CUshort = UShort

public typealias CInt = Int

public typealias CUint = UInt

public typealias CLong = Long

public typealias CUlong = ULong

public typealias CLonglong = Long

public typealias CUlonglong = ULong

public typealias CFloat = kotlin.Float

public typealias CDouble = Double

/**
 * Equivalent to C's `sizeT` type, from `stddef.h` (or `cstddef` for C++).
 *
 * This type is currently always [usize], however in the future there may be
 * platforms where this is not the case.
 */
public typealias CSizeT = Int

/**
 * Equivalent to C's `ptrdiffT` type, from `stddef.h` (or `cstddef` for C++).
 *
 * This type is currently always [isize], however in the future there may be
 * platforms where this is not the case.
 */
public typealias CPtrdiffT = Long

/**
 * Equivalent to C's `ssizeT` (on POSIX) or `SSIZE_T` (on Windows) type.
 *
 * This type is currently always [isize], however in the future there may be
 * platforms where this is not the case.
 */
public typealias CSsizeT = Long

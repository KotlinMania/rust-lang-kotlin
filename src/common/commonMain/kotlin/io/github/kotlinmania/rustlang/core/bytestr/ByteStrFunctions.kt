package io.github.kotlinmania.rustlang.core.bytestr

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.str.converts.fromUtf8
import io.github.kotlinmania.rustlang.str.converts.fromUtf8Mut
import io.github.kotlinmania.rustlang.str.error.Utf8Error

// `implementation AsRef<ByteStr> for [UByte]` omitted to avoid widespread inference failures
public fun String.asRef(): ByteStr = ByteStr.new(this)

// Omitted due to inference failures
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a, const N: Int> From<&a [u8; N]> for &a ByteStr {
// @inline
// fn from(s: &a [u8; N]) -> Self {
// ByteStr.fromBytes(s)
// }
// }
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a> From<&a [u8]> for &a ByteStr {
// @inline
// fn from(s: &a [u8]) -> Self {
// ByteStr.fromBytes(s)
// }
// }
// Omitted due to slice-from-array-issue-113238:
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a> From<&a ByteStr> for &a [u8] {
// @inline
// fn from(s: &a ByteStr) -> Self {
// &s.0
// }
// }
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a> From<&a mut ByteStr> for &a mut [u8] {
// @inline
// fn from(s: &a mut ByteStr) -> Self {
// s.0
// }
// }
// Omitted due to inference failures
//
// @unstable(feature = "byteStr", issue = "134915")
// impl<a> From<&a str> for &a ByteStr {
// @inline
// fn from(s: &a str) -> Self {
// ByteStr.fromBytes(s.asBytes())
// }
// }
public fun tryFromString(s: ByteStr): Result<String, Utf8Error> = fromUtf8(s.value)

public fun tryFromString(s: RustMutableReference<ByteStr>): Result<String, Utf8Error> = fromUtf8Mut((s).read().first)

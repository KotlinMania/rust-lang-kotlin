// port-lint: source sys/helpers/small_c_string.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice

// Make sure to stay under 4096 so the compiler doesn't insert a probe frame:
// https://docs.rs/compilerBuiltins/latest/compilerBuiltins/probestack/index.html
public val MAX_STACK_ALLOCATION: Int = 384

public val NUL_ERR: io.github.kotlinmania.rustlang.std.io.Error = constError(ErrorKind.InvalidInput, "file name contained an unexpected NUL byte")

public fun <T> runPathWithCstr(path: Path, f: (CStr) -> Result<T, Error>): Result<T, Error> = runWithCstr(path.asOsStr().asEncodedBytes(), f)

public fun <T> runWithCstr(bytes: List<UByte>, f: (CStr) -> Result<T, Error>): Result<T, Error> {
    // Dispatch and dynamic erase the closure type to prevent mono bloat.
    // See https://github.com/rust-lang/rust/pull/121101.
    return if (bytes.size >= MAX_STACK_ALLOCATION) {
        runWithCstrAllocating(bytes, f)
    } else {
        runWithCstrStack(bytes, f)
    }
}

/**
 * # Safety
 *
 * `bytes` must have a length less than `MAX_STACK_ALLOCATION`.
 */
public fun <T> runWithCstrStack(bytes: List<UByte>, f: (CStr) -> Result<T, Error>): Result<T, Error> {
    val buf = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    val bufPtr = mutPtrFromRustCast(buf.asMutPtr())
    run {
        ptr.copyNonoverlapping(bytes.asPtr(), bufPtr, bytes.size)
        (PrimitiveLayouts.U8).writeNative((MutPtr((bufPtr).rawAddress + ((bytes.size).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, 0)
    }
    return run {
        val matchVal = CStr.fromBytesWithNul(Slice.fromRawParts(bufPtr, bytes.size + 1))
        when (matchVal) { is Result.Ok -> {
            val s = matchVal.value
            f(s)
        }; is Result.Err -> {
            Result.Err<T, Error>(NUL_ERR)
        } }
    }
}

public fun <T> runWithCstrAllocating(bytes: List<UByte>, f: (CStr) -> Result<T, Error>): Result<T, Error> =
    run {
        val matchVal = CString.new(bytes)
        when (matchVal) { is Result.Ok -> {
            val s = matchVal.value
            f(s)
        }; is Result.Err -> {
            Result.Err<T, Error>(NUL_ERR)
        } }
    }

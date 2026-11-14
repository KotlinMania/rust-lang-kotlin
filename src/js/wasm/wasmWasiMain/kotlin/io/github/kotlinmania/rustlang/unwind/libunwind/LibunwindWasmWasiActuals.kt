@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.types.UnwindWord

// The host must provide the C companion wrapper exports in import module unwind.
public actual fun unwindResume(exception: MutPtr): Nothing =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindResume")

public actual fun unwindDeleteException(exception: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindDeleteException")
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetLanguageSpecificData")

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetRegionStart")

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetTextRelBase")

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetDataRelBase")

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetGr")

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindSetGr")
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindSetIp")
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetIpinfo")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindRaiseException")

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindGccSpecificHandler")

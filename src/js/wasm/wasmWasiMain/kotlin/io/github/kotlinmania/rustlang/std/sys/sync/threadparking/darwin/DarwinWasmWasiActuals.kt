@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysSyncThreadparkingDarwinDispatchTime")
private external fun wasiStdSysSyncThreadparkingDarwinDispatchTime(whenBranch: Long, delta: Long): Long

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    wasiStdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toLong(), delta).toULong()

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate")

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysSyncThreadparkingDarwinDispatchSemaphoreWait")

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal")

public actual fun dispatchRelease(obj: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysSyncThreadparkingDarwinDispatchRelease")
}

@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysSyncThreadparkingDarwinDispatchTime")
private external fun wasiRustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch: Long, delta: Long): Long

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    wasiRustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toLong(), delta).toULong()

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate")

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait")

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal")

public actual fun dispatchRelease(obj: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysSyncThreadparkingDarwinDispatchRelease")
}

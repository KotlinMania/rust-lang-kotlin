@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.UintptrT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for backtraceBacktraceLibunwindUnwindBacktrace")

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for backtraceBacktraceLibunwindUnwindGetIp")

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for backtraceBacktraceLibunwindUnwindFindEnclosingFunction")

public actual fun getSp(ctx: MutPtr): UintptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for backtraceBacktraceLibunwindGetSp")

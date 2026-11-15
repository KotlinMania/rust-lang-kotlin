@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun getProcessHeap(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysAllocWindowsGetProcessHeap")

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysAllocWindowsHeapAlloc")

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysAllocWindowsHeapReAlloc")

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysAllocWindowsHeapFree")

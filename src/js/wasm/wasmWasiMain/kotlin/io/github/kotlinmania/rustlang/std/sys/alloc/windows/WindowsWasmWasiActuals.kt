@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.c

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun getProcessHeap(): c.Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocWindowsGetProcessHeap")

public actual fun heapAlloc(hheap: c.Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocWindowsHeapAlloc")

public actual fun heapReAlloc(hheap: c.Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocWindowsHeapReAlloc")

public actual fun heapFree(hheap: c.Handle, dwflags: UInt, lpmem: ConstPtr): c.Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocWindowsHeapFree")

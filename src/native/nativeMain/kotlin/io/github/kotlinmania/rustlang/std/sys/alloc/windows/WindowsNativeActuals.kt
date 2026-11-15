@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.rustlangStdSysAllocWindowsGetProcessHeap
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.rustlangStdSysAllocWindowsHeapAlloc
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.rustlangStdSysAllocWindowsHeapFree
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.rustlangStdSysAllocWindowsHeapReAlloc
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getProcessHeap(): Handle =
    MutPtr(rustlangStdSysAllocWindowsGetProcessHeap()?.toLong() ?: 0L)

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    MutPtr(rustlangStdSysAllocWindowsHeapAlloc(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, dwbytes)?.toLong() ?: 0L)

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    MutPtr(rustlangStdSysAllocWindowsHeapReAlloc(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, lpmem.rawAddress.toCPointer<ByteVar>(), dwbytes)?.toLong() ?: 0L)

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    rustlangStdSysAllocWindowsHeapFree(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, lpmem.rawAddress.toCPointer<ByteVar>())

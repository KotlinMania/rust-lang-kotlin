@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.stdSysAllocWindowsGetProcessHeap
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.stdSysAllocWindowsHeapAlloc
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.stdSysAllocWindowsHeapFree
import io.github.kotlinmania.rustlang.std.sys.alloc.windows.cinterop.stdSysAllocWindowsHeapReAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Handle
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getProcessHeap(): Handle =
    MutPtr(stdSysAllocWindowsGetProcessHeap()?.toLong() ?: 0L)

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    MutPtr(stdSysAllocWindowsHeapAlloc(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, dwbytes)?.toLong() ?: 0L)

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    MutPtr(stdSysAllocWindowsHeapReAlloc(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, lpmem.rawAddress.toCPointer<ByteVar>(), dwbytes)?.toLong() ?: 0L)

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    stdSysAllocWindowsHeapFree(hheap.rawAddress.toCPointer<ByteVar>(), dwflags, lpmem.rawAddress.toCPointer<ByteVar>())

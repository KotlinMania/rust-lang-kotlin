@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

private object SysAllocWindowsJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysAllocWindowsGetProcessHeap(): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapAlloc(hheap: Long, dwflags: Int, dwbytes: Long): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapReAlloc(hheap: Long, dwflags: Int, lpmem: Long, dwbytes: Long): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapFree(hheap: Long, dwflags: Int, lpmem: Long): Int
}

public actual fun getProcessHeap(): Handle =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsGetProcessHeap())

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsHeapAlloc(hheap.rawAddress, dwflags.toInt(), dwbytes.toLong()))

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsHeapReAlloc(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress, dwbytes.toLong()))

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    SysAllocWindowsJniBridge.stdSysAllocWindowsHeapFree(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress)

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysAllocWindowsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysAllocWindowsGetProcessHeap(): Long

    @JvmStatic
    external fun rustlangStdSysAllocWindowsHeapAlloc(hheap: Long, dwflags: Int, dwbytes: Long): Long

    @JvmStatic
    external fun rustlangStdSysAllocWindowsHeapReAlloc(hheap: Long, dwflags: Int, lpmem: Long, dwbytes: Long): Long

    @JvmStatic
    external fun rustlangStdSysAllocWindowsHeapFree(hheap: Long, dwflags: Int, lpmem: Long): Int
}

public actual fun getProcessHeap(): Handle =
    MutPtr(StdSysAllocWindowsJniBridge.rustlangStdSysAllocWindowsGetProcessHeap())

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    MutPtr(StdSysAllocWindowsJniBridge.rustlangStdSysAllocWindowsHeapAlloc(hheap.rawAddress, dwflags.toInt(), dwbytes.toLong()))

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    MutPtr(StdSysAllocWindowsJniBridge.rustlangStdSysAllocWindowsHeapReAlloc(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress, dwbytes.toLong()))

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    StdSysAllocWindowsJniBridge.rustlangStdSysAllocWindowsHeapFree(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress)

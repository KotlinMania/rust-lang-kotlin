// port-lint: source new/helenos/time.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.time

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CLonglong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

// HelenOS time handling
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/time.h>

public typealias TimeT = CLonglong

public typealias UsecT = CLonglong

public data class Timespec(
    public var tvSec: TimeT = 0L,
    public var tvNsec: CLong = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Timespec> =
            object : TypeLayout<Timespec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Timespec) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvNsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timespec) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvNsec)
                }
            }
    }
}

public expect fun getuptime(tp: MutPtr)

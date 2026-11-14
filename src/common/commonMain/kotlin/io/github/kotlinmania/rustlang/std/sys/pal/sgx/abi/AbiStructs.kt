package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

public open class EntryReturn(
    public var field0: ULong,
    public var field1: ULong,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<EntryReturn> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<EntryReturn> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): EntryReturn =
                    EntryReturn(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: EntryReturn) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.field0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EntryReturn =
                    EntryReturn(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EntryReturn) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.field0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

package io.github.kotlinmania.rustlang.std.sys.personality.dwarf

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public open class DwarfReader internal constructor(
    ptrCtorArg: ConstPtr,
) {
    public var ptr: ConstPtr = ptrCtorArg
        internal set

    /**
     * Read a type T and then bump the pointer by that amount.
     *
     * DWARF streams are "packed", so all types must be read at align 1.
     */
    public open fun <T> read(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T =
        run {
            val result = (typeLayoutT).readNative((ConstPtr((this.ptr).rawAddress)).rawAddress)
            this.ptr = ConstPtr((this.ptr).rawAddress + ((sizeOf<T>(typeLayoutT = typeLayoutT)).toLong()))
            result
        }

    /**
     * ULEB128 and SLEB128 encodings are defined in Section 7.6 - "Variable Length Data".
     */
    public open fun readUleb128(): ULong {
        var shift: Int = 0
        var result: ULong = 0uL
        var byte: UByte
        while (true) {
            byte = this.read<UByte>()
            result = result or (((byte and 0x7Fu.toUByte()).toULong()) shl shift)
            shift += 7
            if (byte and 0x80u.toUByte() == 0u.toUByte()) {
                break
            }
        }
        return result
    }

    public open fun readSleb128(): Long {
        var shift: UInt = 0u
        var result: ULong = 0uL
        var byte: UByte
        while (true) {
            byte = this.read<UByte>()
            result = result or (((byte and 0x7Fu.toUByte()).toULong()) shl shift.toInt())
            shift += 7u
            if (byte and 0x80u.toUByte() == 0u.toUByte()) {
                break
            }
        }
        // sign-extend
        if (shift < 64u && (byte and 0x40u.toUByte()) != 0u.toUByte()) {
            result = result or (((0.inv()).toULong()) shl shift.toInt())
        }
        return result.toLong()
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DwarfReader> =
            object : TypeLayout<DwarfReader> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): DwarfReader =
                    DwarfReader(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DwarfReader) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.ptr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DwarfReader =
                    DwarfReader(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DwarfReader) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.ptr)
                }
            }

        public operator fun invoke(
            ptr: ConstPtr,
        ): DwarfReader = DwarfReader(
            ptrCtorArg = ptr,
        )

        public fun new(ptr: ConstPtr): DwarfReader = DwarfReader(ptr = ptr)
    }
}

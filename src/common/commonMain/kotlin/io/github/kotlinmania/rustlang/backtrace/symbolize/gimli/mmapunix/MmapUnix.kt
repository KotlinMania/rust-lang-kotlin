// port-lint: source symbolize/gimli/mmap_unix.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmapunix

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.fs.File
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.munmap
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_FAILED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_PRIVATE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_READ
import io.github.kotlinmania.rustlang.libc.mmap as mmap64

public open class Mmap internal constructor(
    ptrCtorArg: MutPtr,
    public var len: Int,
) {
    public var ptr: MutPtr = ptrCtorArg
        internal set

    public open fun deref(): List<UByte> = Slice.fromRawParts((this.ptr).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.len)

    public open fun drop() {
        run {
            val r = munmap(this.ptr, this.len)
            check(r == 0)
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Mmap> =
            object : TypeLayout<Mmap> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Mmap =
                    Mmap(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Mmap) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ptr)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mmap =
                    Mmap(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mmap) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ptr)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.len)
                }
            }

        public operator fun invoke(
            ptr: MutPtr,
            len: Int,
        ): Mmap = Mmap(
            ptrCtorArg = ptr,
            len = len,
        )

        public fun map(file: File, len: Int, offset: ULong): Mmap? {
            val ptr = mmap64(MutPtr(0L), len, PROT_READ, MAP_PRIVATE, file.asRawFd(), (offset.tryInto().ok() ?: return null))
            if (ptr == MAP_FAILED) {
                return null
            }
            return Mmap(ptr = ptr, len = len)
        }
    }
}

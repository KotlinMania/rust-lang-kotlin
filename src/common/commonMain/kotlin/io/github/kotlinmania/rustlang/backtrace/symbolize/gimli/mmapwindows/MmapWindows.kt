// port-lint: source symbolize/gimli/mmap_windows.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmapwindows

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.fs.File
import io.github.kotlinmania.rustlang.backtrace.windowssys.FILE_MAP_READ
import io.github.kotlinmania.rustlang.backtrace.windowssys.MemoryMappedViewAddress
import io.github.kotlinmania.rustlang.backtrace.windowssys.PAGE_READONLY
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.result.Result

public open class Mmap internal constructor(
    // keep the file alive to prevent it from being deleted which would cause
    // us to read bad data.
    public var file: File,
    ptrCtorArg: MutPtr,
    public var len: Int,
) : AutoCloseable {
    public var ptr: MutPtr = ptrCtorArg
        internal set

    override fun close() {
        this.drop()
    }

    public open fun deref(): List<UByte> = Slice.fromRawParts(ConstPtr((ConstPtr((this.ptr).rawAddress)).rawAddress), this.len)

    public open fun drop() {
        run {
            val r = UnmapViewOfFile(MemoryMappedViewAddress(value = this.ptr))
            check(r != 0)
        }
    }

    public companion object {
        public operator fun invoke(
            file: File,
            ptr: MutPtr,
            len: Int,
        ): Mmap = Mmap(
            file = file,
            ptrCtorArg = ptr,
            len = len,
        )

        public fun map(file: File, len: Int, offset: ULong): Mmap? {
            return run {
                val file1 = (file.tryClone().ok() ?: return null)
                val mapping = CreateFileMappingA(file1.asRawHandle(), MutPtr(0L), PAGE_READONLY, 0, 0, ConstPtr(0L))
                if (mapping.isNull()) {
                    return null
                }
                val ptr = MapViewOfFile(mapping, FILE_MAP_READ, (offset shr 32).toUInt(), offset.toUInt(), len)
                CloseHandle(mapping)
                if (((ptr.value).rawAddress == 0L)) {
                    return null
                }
                Mmap(file = file1, ptr = ptr.value, len = len)
            }
        }
    }
}

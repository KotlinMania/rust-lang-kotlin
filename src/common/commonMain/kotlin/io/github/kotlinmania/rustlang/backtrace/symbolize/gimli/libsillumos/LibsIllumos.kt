// port-lint: source symbolize/gimli/libs_illumos.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsillumos

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUlong
import io.github.kotlinmania.obj.NativeEndian
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.core.ffi.CStr
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.obj.elf.FileHeader64 as FileHeader
import io.github.kotlinmania.obj.elf.ProgramHeader64 as ProgramHeader

public typealias EHdr = FileHeader<NativeEndian>

public typealias PHdr = ProgramHeader<NativeEndian>

public open class LinkMap internal constructor(
    public var lAddr: CUlong,
    lNameCtorArg: ConstPtr,
    lLdCtorArg: ConstPtr,
    lNextCtorArg: ConstPtr,
    lPrevCtorArg: ConstPtr,
    lRefnameCtorArg: ConstPtr,
) {
    public var lName: ConstPtr = lNameCtorArg
        internal set

    public var lLd: ConstPtr = lLdCtorArg
        internal set

    public var lNext: ConstPtr = lNextCtorArg
        internal set

    public var lPrev: ConstPtr = lPrevCtorArg
        internal set

    public var lRefname: ConstPtr = lRefnameCtorArg
        internal set

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<LinkMap> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<LinkMap> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): LinkMap =
                    LinkMap(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: LinkMap) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.lAddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.lName)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.lLd)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 24L, value.lNext)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 32L, value.lPrev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 40L, value.lRefname)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LinkMap =
                    LinkMap(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LinkMap) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.lAddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.lName)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.lLd)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 24, value.lNext)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 32, value.lPrev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 40, value.lRefname)
                }
            }

        public operator fun invoke(
            lAddr: CUlong,
            lName: ConstPtr,
            lLd: ConstPtr,
            lNext: ConstPtr,
            lPrev: ConstPtr,
            lRefname: ConstPtr,
        ): LinkMap = LinkMap(
            lAddr = lAddr,
            lNameCtorArg = lName,
            lLdCtorArg = lLd,
            lNextCtorArg = lNext,
            lPrevCtorArg = lPrev,
            lRefnameCtorArg = lRefname,
        )
    }
}

public val RTLD_SELF: ConstPtr = ConstPtr((-3).toLong())

public val RTLD_DI_LINKMAP: CInt = 2

public expect fun dlinfo(handle: ConstPtr, request: CInt, p: MutPtr): CInt

internal fun nativeLibraries(): List<Library> {
    val libs = mutableListOf<Library>()
    // Request the current link map from the runtime linker:
    val map1 =
        run {
            val map: ConstPtr?  = null
            if (dlinfo(RTLD_SELF, RTLD_DI_LINKMAP, addrOfMut(map).cast()) != 0) {
                return libs
            }
            map
        }
    // Each entry in the link map represents a loaded object:
    var l = map1
    while (!l.isNull()) {
        // Fetch the fully qualified path of the loaded object:
        val bytes = CStr.fromPtr(l.lName).toBytes()
        val name = OsStr.fromBytes(bytes).toOwned()
        // The base address of the object loaded into memory:
        val addr = l.lAddr
        // Use the ELF header for this object to locate the program
        // header:
        val e: ConstPtr = constPtrFromRustCast(l.lAddr)
        val phoff = (ObjectSlotLayout<FileHeader<NativeEndian>>()).readNative((e).rawAddress).ePhoff.get(NativeEndian)
        val phnum = (ObjectSlotLayout<FileHeader<NativeEndian>>()).readNative((e).rawAddress).ePhnum.get(NativeEndian)
        val etype = (ObjectSlotLayout<FileHeader<NativeEndian>>()).readNative((e).rawAddress).eType.get(NativeEndian)
        val phdr: ConstPtr = ConstPtr((addr + phoff).toLong())
        val phdr1 = rawSliceList(phdr, phnum.toInt(), ObjectSlotLayout<ProgramHeader<NativeEndian>>())
        libs.add(Library(name = name, segments = phdr1.iter().map({ p ->
            val memsz = p.pMemsz.get(NativeEndian)
            val vaddr = p.pVaddr.get(NativeEndian)
            LibrarySegment(len = memsz.toInt(), statedVirtualMemoryAddress = vaddr.toInt())
        }).toMutableList(), bias = if (etype == obj.elf.ET_EXEC) {
            // Program header addresses for the base executable are
            // already absolute.
            0
        } else {
            // Other addresses are relative to the object base.
            addr.toInt()
        }))
        l = l.lNext
    }
    return libs
}

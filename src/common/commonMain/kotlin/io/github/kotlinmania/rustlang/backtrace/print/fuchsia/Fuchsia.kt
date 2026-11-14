// port-lint: source print/fuchsia.rs
package io.github.kotlinmania.rustlang.backtrace.print.fuchsia

import io.github.kotlinmania.libc.strlen
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsdliteratephdr.callback
import io.github.kotlinmania.rustlang.core.fmt.write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.obj.NativeEndian as NE

public fun interface DlIteratePhdrFCallback {
    public operator fun invoke(info: DlPhdrInfo, size: ULong, data: DsoPrinter): Int
}

// dlIteratePhdr takes a callback that will receive a dlPhdrInfo pointer
// for every DSO that has been linked into the process. dlIteratePhdr also
// ensures that the dynamic linker is locked from start to finish of the
// iteration. If the callback returns a non-zero value the iteration is
// terminated early. 'data' will be passed as the third argument to the
// callback on each call. 'size' gives the size of the dlPhdrInfo.
public expect fun dlIteratePhdr(f: DlIteratePhdrFCallback, data: DsoPrinter): Int

// We need to parse out the build ID and some basic program header data
// which means that we need a bit of stuff from the ELF spec as well.
public val PT_LOAD: UInt = 1u

public val PT_NOTE: UInt = 4u

// Now we have to replicate, bit for bit, the structure of the dlPhdrInfo
// type used by fuchsia's current dynamic linker. Chromium also has this ABI
// boundary as well as crashpad. Eventually we'd like to move these cases to
// use elf-search but we'd need to provide that in the SDK and that has not
// yet been done. Thus we (and they) are stuck having to use this method
// which incurs a tight coupling with the fuchsia libc.
public open class DlPhdrInfo internal constructor(
    addrCtorArg: ConstPtr,
    nameCtorArg: ConstPtr,
    phdrCtorArg: ConstPtr,
    public var phnum: UShort,
    public var adds: ULong,
    public var subs: ULong,
    public var tlsModid: Int,
    tlsDataCtorArg: ConstPtr,
) {
    public var addr: ConstPtr = addrCtorArg
        internal set

    public var name: ConstPtr = nameCtorArg
        internal set

    public var phdr: ConstPtr = phdrCtorArg
        internal set

    public var tlsData: ConstPtr = tlsDataCtorArg
        internal set

    public open fun programHeaders(): PhdrIter = PhdrIter(phdrs = this.phdrSlice(), base = this.addr)

    // We have no way of knowing of checking if ePhoff and ePhnum are valid.
    // libc should ensure this for us however so it's safe to form a slice here.
    public open fun phdrSlice(): List<ElfPhdr> = rawSliceList(this.phdr, this.phnum.toInt(), ElfPhdr.typeLayout())

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<DlPhdrInfo> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<DlPhdrInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): DlPhdrInfo =
                    DlPhdrInfo(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: DlPhdrInfo) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.addr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.name)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.phdr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 24L, value.phnum)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.adds)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 40L, value.subs)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 48L, value.tlsModid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 56L, value.tlsData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DlPhdrInfo =
                    DlPhdrInfo(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DlPhdrInfo) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.addr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.name)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.phdr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.phnum)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.adds)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.subs)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 48, value.tlsModid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 56, value.tlsData)
                }
            }

        public operator fun invoke(
            addr: ConstPtr,
            name: ConstPtr,
            phdr: ConstPtr,
            phnum: UShort,
            adds: ULong,
            subs: ULong,
            tlsModid: Int,
            tlsData: ConstPtr,
        ): DlPhdrInfo = DlPhdrInfo(
            addrCtorArg = addr,
            nameCtorArg = name,
            phdrCtorArg = phdr,
            phnum = phnum,
            adds = adds,
            subs = subs,
            tlsModid = tlsModid,
            tlsDataCtorArg = tlsData,
        )
    }
}

public open class PhdrIter internal constructor(
    public var phdrs: List<ElfPhdr>,
    baseCtorArg: ConstPtr,
) : Iterable<Phdr> {
    public var base: ConstPtr = baseCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<Phdr> = object : AbstractIterator<Phdr>() {
        override fun computeNext() {
            when (val v = this@PhdrIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Phdr? =
        this.phdrs.splitFirst().map({ (phdr, newPhdrs) ->
            this.phdrs = newPhdrs
            Phdr(phdr = phdr, base = this.base)
        })

    public companion object {
        public operator fun invoke(
            phdrs: List<ElfPhdr>,
            base: ConstPtr,
        ): PhdrIter = PhdrIter(
            phdrs = phdrs,
            baseCtorArg = base,
        )
    }
}

// ElfPhdr represents a 64-bit ELF program header in the endianness of the target
// architecture.
public open class ElfPhdr(
    public var pType: UInt,
    public var pFlags: UInt,
    public var pOffset: ULong,
    public var pVaddr: ULong,
    public var pPaddr: ULong,
    public var pFilesz: ULong,
    public var pMemsz: ULong,
    public var pAlign: ULong,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ElfPhdr> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ElfPhdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): ElfPhdr =
                    ElfPhdr(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: ElfPhdr) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.pType)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 4L, value.pFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.pOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 16L, value.pVaddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 24L, value.pPaddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.pFilesz)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 40L, value.pMemsz)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 48L, value.pAlign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ElfPhdr =
                    ElfPhdr(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ElfPhdr) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.pType)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.pFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.pVaddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.pPaddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.pFilesz)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.pMemsz)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.pAlign)
                }
            }
    }
}

// Phdr represents a valid ELF program header and its contents.
public open class Phdr internal constructor(
    public var phdr: ElfPhdr,
    baseCtorArg: ConstPtr,
) {
    public var base: ConstPtr = baseCtorArg
        internal set

    // We have no way of checking if pAddr or pMemsz are valid. Fuchsia's libc
    // parses the notes first however so by virtue of being here these headers
    // must be valid. NoteIter does not require the underlying data to be valid
    // but it does require the bounds to be valid. We trust that libc has ensured
    // that this is the case for us here.
    public open fun notes(): NoteIter = NoteIter.new(ConstPtr((this.base).rawAddress + ((this.phdr.pOffset.toInt()).toLong() * (PrimitiveLayouts.U8).strideBytes)), this.phdr.pMemsz.toInt())

    public companion object {
        public operator fun invoke(
            phdr: ElfPhdr,
            base: ConstPtr,
        ): Phdr = Phdr(
            phdr = phdr,
            baseCtorArg = base,
        )
    }
}

// The note type for build IDs.
public val NT_GNU_BUILD_ID: UInt = 3u

// ElfNhdr represents an ELF note header in the endianness of the target.
public typealias ElfNhdr = NoteHeader32<NE>

// Note represents an ELF note (header + contents). The name is left as a u8
// slice because it is not always null terminated and rust makes it easy enough
// to check that the bytes match eitherway.
public open class Note(
    public var name: List<UByte>,
    public var desc: List<UByte>,
    public var tipe: UInt,
)

// NoteIter lets you safely iterate over a note segment. It terminates as soon
// as an error occurs or there are no more notes. If you iterate over invalid
// data it will function as though no notes were found.
public open class NoteIter(
    public var base: List<UByte>,
    public var error: Boolean,
) : Iterable<Note> {
    override fun iterator(): kotlin.collections.Iterator<Note> = object : AbstractIterator<Note>() {
        override fun computeNext() {
            when (val v = this@NoteIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Note? {
        // Check if we've reached the end.
        if (this.base.isEmpty() || this.error) {
            return null
        }
        // We reinterpret out an nhdr but we carefully consider the resulting
        // struct. We don't trust the namesz or descsz and we make no unchecked
        // decisions based on the type. So even if we get out complete garbage
        // we should still be safe.
        val nhdr = (takeNhdr(this.base) ?: return null)
        val name = (takeBytesAlign4(nhdr.nNamesz.get(NE).toInt(), this.base) ?: return null)
        val desc = (takeBytesAlign4(nhdr.nDescsz.get(NE).toInt(), this.base) ?: return null)
        return Note(name = name, desc = desc, tipe = nhdr.nType.get(NE))
    }

    public companion object {
        // It is an invariant of function that the pointer and size given denote a
        // valid range of bytes that can all be read. The contents of these bytes
        // can be anything but the range must be valid for this to be safe.
        public fun new(base: ConstPtr, size: Int): NoteIter = NoteIter(base = rawSliceList(base, size.toInt(), PrimitiveLayouts.U8), error = false)
    }
}

// alignTo aligns 'x' to 'to'-byte alignment assuming 'to' is a power of 2.
// This follows a standard pattern in C/C++ ELF parsing code where
// (x + to - 1) & -to is used. Rust does not let you negate Int so I use
// 2's-complement conversion to recreate that.
public fun alignTo(x: Int, to: Int): Int = (x + to - 1) and (!to + 1)

// takeBytesAlign4 consumes num bytes from the slice (if present) and
// additionally ensures that the final slice is properlly aligned. If an
// either the number of bytes requested is too large or the slice can't be
// realigned afterwards due to not enough remaining bytes existing, None is
// returned and the slice is not modified.
public fun takeBytesAlign4(num: Int, bytes: List<UByte>): List<UByte>? {
    var bytes1 = bytes
    if (bytes1.size < alignTo(num, 4)) {
        return null
    }
    val (out, bytesNew) = bytes1.splitAt(num)
    bytes1 = bytesNew[alignTo(num, 4) - num until bytesNew.size]
    return out
}

/**
 * This function has no invariants the caller must uphold, but
 * it will return `None`, without mutating, if `bytes` has insufficient size or alignment.
 * If this returns `Some(nhdr)`, then `bytes` was and remains 4-byte-aligned.
 * The values in the ElfNhdr fields might be nonsense.
 */
public fun takeNhdr(bytes: List<UByte>): ElfNhdr? {
    var bytes1 = bytes
    val (out, rest) = (obj.fromBytes<ElfNhdr>(bytes1).ok() ?: return null)
    // Note that sizeOf::<ElfNhdr>() is always a multiple of 4-bytes, so the
    // 4-byte alignment is maintained.
    bytes1 = rest
    return out
}

public open class Perm(
    public var value: UInt,
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder) {
        val v = this.value
        if (v and PERM_R != 0u) {
            f.append('r')
        }
        if (v and PERM_W != 0u) {
            f.append('w')
        }
        if (v and PERM_X != 0u) {
            f.append('x')
        }
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Perm> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Perm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Perm =
                    Perm(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Perm) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Perm =
                    Perm(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Perm) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Indicates that a segment is executable.
 */
public val PERM_X: UInt = 0x1u

/**
 * Indicates that a segment is writable.
 */
public val PERM_W: UInt = 0x2u

/**
 * Indicates that a segment is readable.
 */
public val PERM_R: UInt = 0x4u

/**
 * Represents an ELF segment at runtime.
 */
public open class Segment(
    /**
     * Gives the runtime virtual address of this segment's contents.
     */
    public var addr: Int,
    /**
     * Gives the memory size of this segment's contents.
     */
    public var size: Int,
    /**
     * Gives the module virtual address of this segment with the ELF file.
     */
    public var modRelAddr: Int,
    /**
     * Gives the permissions found in the ELF file. These permissions are not
     * necessarily the permissions present at runtime however.
     */
    public var flags: Perm,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Segment> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Segment> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Segment =
                    Segment(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                        (Perm.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Segment) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.addr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.size)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.modRelAddr)
                    (Perm.typeLayout()).writeNative(addr + 24L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Segment =
                    Segment(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                        (Perm.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Segment) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.addr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.size)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.modRelAddr)
                    (Perm.typeLayout()).writeBytes(buf, offset + 24, value.flags)
                }
            }
    }
}

/**
 * Lets one iterate over Segments from a DSO.
 */
public open class SegmentIter(
    public var phdrs: List<ElfPhdr>,
    public var base: Int,
) : Iterable<Segment> {
    override fun iterator(): kotlin.collections.Iterator<Segment> = object : AbstractIterator<Segment>() {
        override fun computeNext() {
            when (val v = this@SegmentIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Segment? =
        this.phdrs.splitFirst().andThen({ (phdr, newPhdrs) ->
            this.phdrs = newPhdrs
            if (phdr.pType != PT_LOAD) {
                this.next()
            } else {
                Segment(addr = phdr.pVaddr.toInt() + this.base, size = phdr.pMemsz.toInt(), modRelAddr = phdr.pVaddr.toInt(), flags = Perm(phdr.pFlags))
            }
        })
}

/**
 * Represents an ELF DSO (Dynamic Shared Object). This type references
 * the data stored in the actual DSO rather than making its own copy.
 */
public open class Dso(
    /**
     * The dynamic linker always gives us a name, even if the name is empty.
     * In the case of the main executable this name will be empty. In the case
     * of a shared object it will be the soname (see DT_SONAME).
     */
    public var name: String,
    /**
     * On Fuchsia virtually all binaries have build IDs but this is not a strict
     * requirement. There's no way to match up DSO information with a real ELF
     * file afterwards if there is no buildId so we require that every DSO
     * have one here. DSO's without a buildId are ignored.
     */
    public var buildId: List<UByte>,
    public var base: Int,
    public var phdrs: List<ElfPhdr>,
) {
    /**
     * Returns an iterator over Segments in this DSO.
     */
    public open fun segments(): SegmentIter = SegmentIter(phdrs = this.phdrs, base = this.base)
}

public open class HexSlice(
    public var bytes: List<UByte>,
) {
    public open fun fmt(f: StringBuilder) {
        for (byte in this.bytes) {
            f.append("$byte")
        }
    }
}

public fun getBuildId(info: DlPhdrInfo): List<UByte>? {
    for (phdr in info.programHeaders()) {
        if (phdr.phdr.pType == PT_NOTE) {
            for (note in phdr.notes()) {
                if (note.tipe == NT_GNU_BUILD_ID && (note.name == "GNU\u0000".encodeToByteArray() || note.name == "GNU".encodeToByteArray())) {
                    return note.desc
                }
            }
        }
    }
    return null
}

/**
 * These errors encode issues that arise while parsing information about
 * each DSO.
 */
public sealed class Error {
    /**
     * NameError means that an error occurred while converting a C style string
     * into a rust string.
     */
    public data object NameError : Error()
    /**
     * BuildIDError means that we didn't find a build ID. This could either be
     * because the DSO had no build ID or because the segment containing the
     * build ID was malformed.
     */
    public data object BuildIDError : Error()
}

/**
 * Calls either 'dso' or 'error' for each DSO linked into the process by the
 * dynamic linker.
 *
 * # Arguments
 *
 * * `visitor` - A DsoPrinter that will have one of eats methods called foreach DSO.
 */
public fun forEachDso(visitor: DsoPrinter) {
    fun callback(info: DlPhdrInfo, size: Int, visitor1: DsoPrinter): Int {
        // dlIteratePhdr ensures that info.name will point to a valid
        // location.
        val nameLen = strlen(info.name).toInt()
        val nameSlice: List<UByte> = rawSliceList(ConstPtr((info.name).rawAddress), nameLen.toInt(), PrimitiveLayouts.U8)
        val name1 =
            run {
                val matchVal = String.fromUtf8(nameSlice)
                when (matchVal) { is Result.Ok<*> -> {
                    val name = matchVal.value
                    name
                }; is Result.Err<*> -> {
                    return visitor1.error(Error.NameError).toInt()
                } }
            }
        val buildId1 =
            when (val matchVal1 = getBuildId(info)) {
                null -> {
                    return visitor1.error(Error.BuildIDError).toInt()
                }
                else -> {
                    val buildId = matchVal1
                    buildId
                }
            }
        return visitor1.dso(Dso(name = name1, buildId = buildId1, phdrs = info.phdrSlice(), base = info.addr.rawAddress.toInt())).toInt()
    }
    dlIteratePhdr(callback, visitor)
}

public open class DsoPrinter(
    public var writer: StringBuilder,
    public var moduleCount: Int,
    public var error: Unit,
) {
    public open fun dso(dso: Dso): Boolean {
        var write =
            closure@{
                this.writer.append("{{{module:${this.moduleCount}:${dso.name}:elf:${HexSlice(bytes = dso.buildId)}}}}\n")
                for (seg in dso.segments()) {
                    this.writer.append("{{{mmap:${seg.addr}:${seg.size}:load:${this.moduleCount}:${seg.flags}:${seg.modRelAddr}}}}\n")
                }
                this.moduleCount += 1
                Result.Ok(Unit)
            }
        return run {
            val matchVal = write()
            when (matchVal) { is Result.Ok<*> -> {
                false
            }; is Result.Err<*> -> {
                val err = matchVal.error
                this.error = Result.Err(err)
                true
            } }
        }
    }

    public open fun error(error: Error): Boolean = false
}

/**
 * This function prints the Fuchsia symbolizer markup for all information contained in a DSO.
 */
public fun printDsoContext(out: StringBuilder) {
    out.append("{{{reset:begin}}}\n")
    val visitor = DsoPrinter(writer = out, moduleCount = 0, error = Unit)
    forEachDso(visitor)
    visitor.error
}

/**
 * This function prints the Fuchsia symbolizer markup to end the backtrace.
 */
public fun finishContext(out: StringBuilder) {
    out.append("{{{reset:end}}}\n")
}

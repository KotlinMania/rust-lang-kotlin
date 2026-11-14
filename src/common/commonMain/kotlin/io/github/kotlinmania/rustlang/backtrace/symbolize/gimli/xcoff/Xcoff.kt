// port-lint: source symbolize/gimli/xcoff.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.xcoff

import io.github.kotlinmania.addr2line.SplitDwarfLoad
import io.github.kotlinmania.obj.SymbolFlags
import io.github.kotlinmania.obj.read.archive.ArchiveFile
import io.github.kotlinmania.obj.read.xcoff.XcoffFile
import io.github.kotlinmania.obj.read.xcoff.XcoffSymbol
import io.github.kotlinmania.rustlang.backtrace.gimli.Dwarf
import io.github.kotlinmania.rustlang.backtrace.gimli.DwarfPackage
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Context
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Endian
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.EndianSlice
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Mapping
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.path.Path
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash.Stash
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Xcoff = FileHeader64

public fun Mapping.Companion.new(path: Path, memberName: OsStr): Mapping? {
    val map = (mmap(path) ?: return null)
    return Mapping.mk(map, closure@{ data, stash ->
        if (memberName.isEmpty()) {
            Context.new(stash, when (val tryResult = Object.parse(data)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }, null, null)
        } else {
            val archive = when (val tryResult = ArchiveFile.parse(data).ok()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
            for (member in archive.members().mapNotNull({ m -> m.ok() }).filter({ m -> OsStr.fromBytes(m.name()) == memberName })) {
                val memberData = when (val tryResult = member.data(data).ok()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
                when (val matchVal = Object.parse(memberData)) {
                    null -> {}
                    else -> {
                        val obj = matchVal
                        return@closure Context.new(stash, obj, null, null)
                    }
                }
            }
            null
        }
    })
}

public open class ParsedSym(
    public var address: ULong,
    public var size: ULong,
    public var name: String,
)

public open class Object internal constructor(
    public var syms: List<ParsedSym>,
    fileCtorArg: XcoffFile<Xcoff>,
) {
    public var file: XcoffFile<Xcoff> = fileCtorArg
        internal set

    public open fun section(arg0: Stash, name: String): List<UByte>? {
        return ((this.file.sectionByName(name) ?: return null).data().ok() ?: return null)
    }

    public open fun searchSymtab(addr: ULong): List<UByte>? {
        // Symbols, except ".text" and ".data", are sorted and are not overlapped each other,
        // so we can just perform a binary search here.
        val i2 =
            run {
                val matchVal = this.syms.binarySearchByKey(addr, { sym -> sym.address })
                when (matchVal) { is Result.Ok<*> -> {
                    val i = matchVal.value
                    i
                }; is Result.Err<*> -> {
                    val i1 = matchVal.error
                    (i1.checkedSub(1) ?: return null)
                } }
            }
        val sym = (this.syms.getOrNull(i2) ?: return null)
        return if ((sym.address until sym.address + sym.size).contains(addr)) {
            // On AIX, for a function call, for example, `foo()`, we have
            // two symbols `foo` and `.foo`. `foo` references the function
            // descriptor and `.foo` references the function entry.
            // See https://www.ibm.com/docs/en/xl-fortran-aix/16.1.0?topic=calls-linkage-convention-function
            // for more information.
            // We trim the prefix `.` here, so that the rust demangler can work
            // properly.
            sym.name.trimStartMatches(".").encodeToByteArray().map { it.toUByte() }
        } else {
            null
        }
    }

    internal open fun searchObjectMap(addr: ULong): Pair<Context, ULong>? = null

    public companion object {
        public operator fun invoke(
            syms: List<ParsedSym>,
            file: XcoffFile<Xcoff>,
        ): Object = Object(
            syms = syms,
            fileCtorArg = file,
        )

        public fun getConcreteSize(file: XcoffFile<Xcoff>, sym: XcoffSymbol<Xcoff>): ULong =
            run {
                val matchVal = sym.flags()
                when {
                    matchVal is SymbolFlags.Xcoff && matchVal.containingCsect != null -> {
                        val index = matchVal.containingCsect
                        when (val matchVal1 = file.symbolByIndex(index)) {
                            is Ok -> {
                                val tgtSym = matchVal1.value
                                Object.getConcreteSize(file, tgtSym)
                            }
                            else -> {
                                0uL
                            }
                        }
                    }
                    else -> sym.size()
                }
            }

        public fun parse(data: List<UByte>): Object? {
            val file = (XcoffFile.parse(data).ok() ?: return null)
            var syms =
                file.symbols().mapNotNull({ sym ->
                    val name = sym.name().mapOr("", { v -> v })
                    val address = sym.address()
                    val size = Object.getConcreteSize(file, sym)
                    if (name == ".text" || name == ".data") {
                        // We don't want to include ".text" and ".data" symbols.
                        // If they are included, since their ranges cover other
                        // symbols, when searching a symbol for a given address,
                        // ".text" or ".data" is returned. That's not what we expect.
                        null
                    } else {
                        ParsedSym(address = address, size = size, name = name)
                    }
                }).collect<Vec>()
            syms.sortByKey({ s -> s.address })
            return Object(syms = syms, file = file)
        }
    }
}

public open class Image(
    public var offset: Int,
    public var base: ULong,
    public var size: Int,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Image> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Image> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Image =
                    Image(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Image) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.offset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.base)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Image =
                    Image(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Image) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.offset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.base)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.size)
                }
            }
    }
}

public fun parseXcoff(data: List<UByte>): Image? {
    val localStorageL63C4 = AddressableLocal(0, PrimitiveLayouts.I32)
    val header = (Xcoff.parse(data, RustMutableReference(localStorageL63C4.asMutPtr(), localStorageL63C4.layout)).ok() ?: return null)
    (header.auxHeader(data, RustMutableReference(localStorageL63C4.asMutPtr(), localStorageL63C4.layout)).ok() ?: return null)
    val sections = (header.sections(data, RustMutableReference(localStorageL63C4.asMutPtr(), localStorageL63C4.layout)).ok() ?: return null)
    return when (val matchVal = sections.iter().indexOf({ s -> s.sName().get(0 until 5) == ".text".encodeToByteArray() }).takeIf { it >= 0 }) {
        null -> {
            null
        }
        else -> {
            val section = matchVal
            Image(offset = section.sScnptr().toInt(), base = section.sPaddr().toULong(), size = section.sSize().toInt())
        }
    }
}

public fun parseImage(path: Path, memberName: OsStr): Image? {
    val map = (mmap(path) ?: return null)
    val data = map.deref()
    return if (memberName.isEmpty()) {
        return parseXcoff(data)
    } else {
        val archive = (ArchiveFile.parse(data).ok() ?: return null)
        for (member in archive.members().mapNotNull({ m -> m.ok() }).filter({ m -> OsStr.fromBytes(m.name()) == memberName })) {
            val memberData = (member.data(data).ok() ?: return null)
            when (val matchVal = parseXcoff(memberData)) {
                null -> {}
                else -> {
                    val image = matchVal
                    return image
                }
            }
        }
        null
    }
}

internal fun handleSplitDwarf(pkg: DwarfPackage<EndianSlice<Endian>>?, stash: Stash, load: SplitDwarfLoad<EndianSlice<Endian>>): Dwarf<EndianSlice<Endian>>? = null

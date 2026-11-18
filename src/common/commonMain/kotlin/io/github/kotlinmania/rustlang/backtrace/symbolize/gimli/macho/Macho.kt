// port-lint: source symbolize/gimli/macho.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.macho

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Context
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Endian
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.EndianSlice
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Mapping
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.gimli
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.path.Path
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash.Stash
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Mach = macho.MachHeader64<NativeEndian>

public typealias MachSegment = Segment

public typealias MachSection = Section

public typealias MachNlist = Nlist

// The loading path for macOS is so different we just have a completely
// different implementation of the function here. On macOS we need to go
// probing the filesystem for a bunch of files.
public fun Mapping.Companion.new(path: Path): Mapping? {
    // First up we need to load the unique UUID which is stored in the macho
    // header of the file we're reading, specified at `path`.
    val map = (mmap(path) ?: return null)
    val (macho, data) = (findHeader(map) ?: return null)
    val endian = (macho.endian().ok() ?: return null)
    val uuid = (macho.uuid(endian, data, 0).ok() ?: return null)
    // Next we need to look for a `*.dSYM` file. For now we just probe the
    // containing directory and look around for something that matches
    // `*.dSYM`. Once it's found we root through the dwarf resources that it
    // contains and try to find a macho file which has a matching UUID as
    // the one of our own file. If we find a match that's the dwarf file we
    // want to return.
    if (uuid != null) {
        val uuid1 = uuid
        when (val matchVal = path.parent()) {
            null -> {}
            else -> {
                val parent = matchVal
                when (val matchVal1 = Mapping.loadDsym(parent, uuid1)) {
                    null -> {}
                    else -> {
                        val mapping = matchVal1
                        return mapping
                    }
                }
            }
        }
    }
    // Looks like nothing matched our UUID, so let's at least return our own
    // file. This should have the symbol table for at least some
    // symbolication purposes.
    return Mapping.mk(map, closure@{ data1, stash ->
        val (macho1, data2) = when (val tryResult = findHeader(data1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        val endian1 = when (val tryResult = macho1.endian().ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
        val obj = when (val tryResult = Object.parse(macho1, endian1, data2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        Context.new(stash, obj, null, null)
    })
}

public fun Mapping.Companion.loadDsym(dir: Path, uuid: UByteArray): Mapping? {
    for (entry in (dir.readDir().ok() ?: return null)) {
        val entry1 = (entry.ok() ?: return null)
        val filename =
            run {
                val matchVal = entry1.fileName().intoString()
                when (matchVal) { is Result.Ok<*, *> -> {
                    val name = matchVal.okValue()
                    name
                }; is Result.Err<*, *> -> {
                    continue
                } }
            }
        if (!filename.endsWith(".dSYM")) {
            continue
        }
        val candidates = entry1.path().join("Contents/Resources/DWARF")
        when (val matchVal1 = Mapping.tryDsymCandidate(candidates, uuid)) {
            null -> {}
            else -> {
                val mapping = matchVal1
                return mapping
            }
        }
    }
    return null
}

public fun Mapping.Companion.tryDsymCandidate(dir: Path, uuid: UByteArray): Mapping? {
    // Look for files in the `DWARF` directory which have a matching uuid to
    // the original object file. If we find one then we found the debug
    // information.
    for (entry in (dir.readDir().ok() ?: return null)) {
        val entry1 = (entry.ok() ?: return null)
        val map = (mmap(entry1.path()) ?: return null)
        val candidate =
            Mapping.mk(map, closure1@{ data, stash ->
                val (macho, data1) = when (val tryResult = findHeader(data)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                val endian = when (val tryResult = macho.endian().ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
                val entryUuid = when (val tryResult = when (val tryResult = macho.uuid(endian, data1, 0).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
                if (!entryUuid.contentEquals(uuid)) {
                    return@closure1 null
                }
                val obj = when (val tryResult = Object.parse(macho, endian, data1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                Context.new(stash, obj, null, null)
            })
        if (candidate != null) {
            val candidate1 = candidate
            return candidate1
        }
    }
    return null
}

public fun findHeader(data: List<UByte>): Pair<Mach, List<UByte>>? {
    val data1 = data
    val desiredCpu =
        {
            if (false) {
                macho.CPU_TYPE_X86
            } else if (false) {
                macho.CPU_TYPE_X86_64
            } else if (false) {
                macho.CPU_TYPE_ARM
            } else if (false) {
                macho.CPU_TYPE_ARM64
            } else {
                null
            }
        }
    var data2 = Bytes(data1)
    when ((data2.clone().read<U32<NativeEndian>>().ok() ?: return null).get(NativeEndian)) {
        macho.mhMagic64, macho.mhCigam64, macho.mhMagic, macho.mhCigam -> {}
        macho.fatMagic, macho.fatCigam -> {
            val headerData = data2
            val endian = BigEndian
            val header = (headerData.read<macho.FatHeader>().ok() ?: return null)
            val nfat = header.nfatArch.get(endian)
            val arch = ((0 until nfat).mapNotNull({ headerData.read<macho.FatArch32>().ok() }).indexOf({ arch -> desiredCpu() == arch.cputype.get(endian) }).takeIf { it >= 0 } ?: return null)
            val offset = arch.offset.get(endian)
            val size = arch.size.get(endian)
            data2 = (data2.readBytesAt((offset.tryInto().ok() ?: return null), (size.tryInto().ok() ?: return null)).ok() ?: return null)
        }
        macho.fatMagic64, macho.fatCigam64 -> {
            val headerData1 = data2
            val endian1 = BigEndian
            val header1 = (headerData1.read<macho.FatHeader>().ok() ?: return null)
            val nfat1 = header1.nfatArch.get(endian1)
            val arch1 = ((0 until nfat1).mapNotNull({ headerData1.read<macho.FatArch64>().ok() }).indexOf({ arch1 -> desiredCpu() == arch1.cputype.get(endian1) }).takeIf { it >= 0 } ?: return null)
            val offset1 = arch1.offset.get(endian1)
            val size1 = arch1.size.get(endian1)
            data2 = (data2.readBytesAt((offset1.tryInto().ok() ?: return null), (size1.tryInto().ok() ?: return null)).ok() ?: return null)
        }
        else -> { return null }
    }
    return MachHeader64.parse(data2.first, 0).ok()?.let({ h -> Pair(h, data2.first) })
}

// This is used both for executables/libraries and source object files.
public open class Object(
    public var endian: NativeEndian,
    public var data: List<UByte>,
    public var dwarf: List<MachSection>?,
    public var syms: List<Pair<List<UByte>, ULong>>,
    public var symsSortByName: Boolean,
    // Only set for executables/libraries, and not the source object files.
    public var objectMap: ObjectMap?,
    // The outer Option is for lazy loading, and the inner Option allows load errors to be cached.
    public var objectMappings: List<Mapping?>,
) {
    public open fun section(arg0: Stash, name: String): List<UByte>? {
        val name1 = name.encodeToByteArray().map { it.toUByte() }
        val dwarf = (this.dwarf ?: return null)
        val section =
            (dwarf.indexOf({ section ->
                val sectionName = section.name()
                sectionName == name1 || sectionName.startsWith("__".encodeToByteArray()) && name1.startsWith(".".encodeToByteArray()) && sectionName[2 until sectionName.size] == ((name1).subList(1, (name1).size))
            }).takeIf { it >= 0 } ?: return null)
        return (section.data(this.endian, this.data).ok() ?: return null)
    }

    public open fun searchSymtab(addr: ULong): List<UByte>? {
        check(!this.symsSortByName)
        val i2 =
            run {
                val matchVal = this.syms.binarySearchByKey(addr, { (_, addr1) -> addr1 })
                when (matchVal) { is Result.Ok<*, *> -> {
                    val i = matchVal.okValue()
                    i
                }; is Result.Err<*, *> -> {
                    val i1 = matchVal.errValue()
                    (i1.checkedSub(1) ?: return null)
                } }
            }
        val (sym, addr) = (this.syms.getOrNull(i2) ?: return null)
        return sym
    }

    /**
     * Try to load a context for an object file.
     *
     * If dsymutil was not run, then the DWARF may be found in the source object files.
     */
    public open fun searchObjectMap(addr: ULong): Pair<Context, ULong>? {
        // `objectMap` contains a map from addresses to symbols and object paths.
        // Look up the address and get a mapping for the object.
        val objectMap = (this.objectMap ?: return null)
        val symbol = (objectMap.get(addr) ?: return null)
        val objectIndex = symbol.objectIndex()
        val mapping = (this.objectMappings.getMut(objectIndex) ?: return null)
        if (mapping == null) {
            // No cached mapping, so create it.
            mapping = objectMapping((objectMap.objects().get(objectIndex) ?: return null))
        }
        val cx: Context = ((mapping.asRef() ?: return null).asRef() ?: return null).cx
        // Don't leak the `static` scope, make sure it's scoped to just ourselves.
        val cx1 = cx
        // We must translate the address in order to be able to look it up
        // in the DWARF in the object file.
        check(cx1.obj.syms.isEmpty() || cx1.obj.symsSortByName)
        val i = (cx1.obj.syms.binarySearchByKey(symbol.name(), { (name, _) -> name }).ok() ?: return null)
        val objectSymbol = (cx1.obj.syms.get(i) ?: return null)
        val objectAddr = ((addr - symbol.address().toULong()) + (objectSymbol.second.toULong()))
        return Pair(cx1, objectAddr)
    }

    public companion object {
        public fun parse(mach: Mach, endian: NativeEndian, data: List<UByte>): Object? {
            val isObject = mach.filetype(endian) == Object.macho.MH_OBJECT
            var dwarf = null
            var syms = mutableListOf<Pair<List<UByte>, ULong>>()
            var symsSortByName = false
            val commands = (mach.loadCommands(endian, data, 0).ok() ?: return null)
            var objectMap = null
            var objectMappings = mutableListOf()
            while (true) {
                val matchVal = commands.next()
                if (!(matchVal is Result.Ok<*, *> && matchVal.okValue() != null)) break
                val okValueVal = (matchVal as Result.Ok<*, *>).okValue()
                val command = okValueVal
                when (val matchVal2 = (MachSegment.fromCommand(command).ok() ?: return null)) {
                    null -> {
                        when (val matchVal1 = (command.symtab().ok() ?: return null)) {
                            null -> {}
                            else -> {
                                val symtab = matchVal1
                                val symbols = (symtab.symbols<Mach>(endian, data).ok() ?: return null)
                                syms = symbols.iter().mapNotNull(closure2@{ nlist: MachNlist ->
                                    val name = when (val tryResult = nlist.name(endian, symbols.strings()).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure2 Result.Err(tryResult.errValue()) }
                                    if (name.len() > 0 && nlist.isDefinition()) {
                                        Pair(name, ULong.from(nlist.nValue(endian)))
                                    } else {
                                        null
                                    }
                                }).toMutableList()
                                if (isObject) {
                                    // We never search object file symbols by address.
                                    // Instead, we already know the symbol name from the executable, and we
                                    // need to search by name to find the matching symbol in the object file.
                                    syms.sortUnstableByKey({ (name, _) -> name })
                                    symsSortByName = true
                                } else {
                                    syms.sortUnstableByKey({ (_, addr) -> addr })
                                    val map = symbols.objectMap(endian)
                                    objectMappings.resizeWith(map.objects().len(), { null })
                                    objectMap = map
                                }
                            }
                        }
                    }
                    else -> {
                        val segment = matchVal2.first
                        val sectionData = matchVal2.second
                        // Object files should have all sections in a single unnamed segment load command.
                        if (segment.name() == "__DWARF".encodeToByteArray() || (isObject && segment.name() == "".encodeToByteArray())) {
                            dwarf = segment.sections(endian, sectionData).ok()
                        }
                    }
                }
            }
            return Object(endian = endian, data = data, dwarf = dwarf, syms = syms, symsSortByName = symsSortByName, objectMap = objectMap, objectMappings = objectMappings.intoBoxedSlice())
        }
    }
}

public fun objectMapping(file: ObjectMapFile): Mapping? {
    val map = (mmap(Path.new(OsStr.fromBytes(file.path()))) ?: return null)
    val memberName = file.member()
    return Mapping.mk(map, closure3@{ data, stash ->
        val data1 =
            when (memberName) {
                null -> data
                else -> {
                    val memberName1 = memberName
                    val archive = when (val tryResult = Object.ArchiveFile.parse(data).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
                    val member = when (val tryResult = archive.members().mapNotNull(Result::ok).indexOf({ m -> m.name() == memberName1 }).takeIf { it >= 0 }) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
                    when (val tryResult = member.data(data).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
                }
            }
        val (macho, data2) = when (val tryResult = findHeader(data1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        val endian = when (val tryResult = macho.endian().ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
        val obj = when (val tryResult = Object.parse(macho, endian, data2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        Context.new(stash, obj, null, null)
    })
}

public fun handleSplitDwarf(pkg: gimli.DwarfPackage<EndianSlice<Endian>>?, stash: Stash, load: SplitDwarfLoad<EndianSlice<Endian>>): gimli.Dwarf<EndianSlice<Endian>>? = null

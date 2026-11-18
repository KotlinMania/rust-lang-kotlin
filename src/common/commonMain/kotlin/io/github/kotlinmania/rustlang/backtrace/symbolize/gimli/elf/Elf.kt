// port-lint: source symbolize/gimli/elf.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.elf

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Context
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Dwarf
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.DwarfPackage
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Either
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Endian
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.EndianSlice
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.File
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Mapping
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.canonicalize
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.extractZipPathAndroid
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap.Mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.path.Path
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.path.PathBuf
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash.Stash
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store

public typealias Elf = FileHeader64<NativeEndian>

public fun Mapping.Companion.new(path: Path): Mapping? {
    val map = (mmap(path) ?: return null)
    return Mapping.mkOrOther(map, closure@{ map1, stash ->
        val obj = when (val tryResult = Object.parse(map1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        // Try to locate an external debug file using the build ID.
        when (val matchVal = obj.buildId()?.let(::locateBuildId)) {
            null -> {}
            else -> {
                val pathDebug = matchVal
                when (val matchVal1 = Mapping.newDebug(path, pathDebug, null)) {
                    null -> {}
                    else -> {
                        val mapping = matchVal1
                        return@closure Either.A(mapping)
                    }
                }
            }
        }
        // Try to locate an external debug file using the GNU debug link section.
        when (val matchVal2 = obj.gnuDebuglinkPath(path)) {
            null -> {}
            else -> {
                val pathDebug1 = matchVal2.first
                val crc = matchVal2.second
                when (val matchVal3 = Mapping.newDebug(path, pathDebug1, crc)) {
                    null -> {}
                    else -> {
                        val mapping1 = matchVal3
                        return@closure Either.A(mapping1)
                    }
                }
            }
        }
        val dwp = Mapping.loadDwarfPackage(path, stash)
        Context.new(stash, obj, null, dwp).map(Either.B)
    })
}

/**
 * On Android, shared objects can be loaded directly from a ZIP archive
 * (see: [Library.zipOffset]).
 *
 * If `zipOffset` is not None, we interpret the `path` as an
 * "embedded" library path, and the value of `zipOffset` tells us where
 * in the ZIP archive the library data starts.
 *
 * We expect `zipOffset` to be page-aligned because the dynamic linker
 * requires this. Otherwise, loading the embedded library will fail.
 *
 * If we fail to load an embedded library for any reason, we fallback to
 * interpreting the path as a literal file on disk (same as calling [Self.new]).
 */
public fun Mapping.Companion.newAndroid(path: Path, zipOffset: ULong?): Mapping? {
    fun mapEmbeddedLibrary(path1: Path, zipOffset1: ULong): Mapping? {
        // get path of ZIP archive (delimited by `! /`)
        val zipPath = Path.new((extractZipPathAndroid(path1.asOsStr()) ?: return null))
        val file = (File.open(zipPath).ok() ?: return null)
        val len = (file.metadata().ok() ?: return null).len()
        // NOTE: we map the remainder of the entire archive instead of just the library so we don't have to determine its length
        // NOTE: mmap will fail if `zipOffset` is not page-aligned
        val map = (Mmap.map(file, (Int.tryFrom(len - zipOffset1.toInt()).ok() ?: return null), zipOffset1) ?: return null)
        return Mapping.mk(map, closure1@{ map1, stash ->
            Context.new(stash, when (val tryResult = Object.parse(map1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }, null, null)
        })
    }
    // if ZIP offset is given, try mapping as a ZIP-embedded library
    // otherwise, fallback to mapping as a literal filepath
    return if (zipOffset != null) {
        val zipOffset1 = zipOffset
        (mapEmbeddedLibrary(path, zipOffset1) ?: Mapping.new(path))
    } else {
        Mapping.new(path)
    }
}

/**
 * Load debuginfo from an external debug file.
 */
public fun Mapping.Companion.newDebug(originalPath: Path, path: PathBuf, crc: UInt?): Mapping? {
    val map = (mmap(path) ?: return null)
    return Mapping.mk(map, closure2@{ map1, stash ->
        val obj = when (val tryResult = Object.parse(map1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure2 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        if (crc != null) {
            // Note: check crc
        }
        // Try to locate a supplementary object file.
        var sup = null
        when (val matchVal = obj.gnuDebugaltlinkPath(path)) {
            null -> {}
            else -> {
                val pathSup = matchVal.first
                val buildIdSup = matchVal.second
                when (val matchVal1 = mmap(pathSup)) {
                    null -> {}
                    else -> {
                        val mapSup = matchVal1
                        val mapSup1 = stash.cacheMmap(mapSup)
                        when (val matchVal2 = Object.parse(mapSup1)) {
                            null -> {}
                            else -> {
                                val sup = matchVal2
                                if (sup.buildId() == buildIdSup) {
                                    sup = sup
                                }
                            }
                        }
                    }
                }
            }
        }
        val dwp = Mapping.loadDwarfPackage(originalPath, stash)
        Context.new(stash, obj, sup, dwp)
    })
}

/**
 * Try to locate a DWARF package file.
 */
public fun Mapping.Companion.loadDwarfPackage(path: Path, stash: Stash): Object? {
    val pathDwp = path.toPathBuf()
    val dwpExtension =
        path.extension().map({ previousExtension ->
            var previousExtension1 = previousExtension.toOsString()
            previousExtension1.push(".dwp")
            previousExtension1
        }).unwrapOrElse({ "dwp".into() })
    pathDwp.setExtension(dwpExtension)
    when (val matchVal = mmap(pathDwp)) {
        null -> {}
        else -> {
            val mapDwp = matchVal
            val mapDwp1 = stash.cacheMmap(mapDwp)
            when (val matchVal1 = Object.parse(mapDwp1)) {
                null -> {}
                else -> {
                    val dwp = matchVal1
                    return dwp
                }
            }
        }
    }
    return null
}

public open class ParsedSym(
    public var address: ULong,
    public var size: ULong,
    public var name: UInt,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ParsedSym> =
            object : TypeLayout<ParsedSym> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): ParsedSym =
                    ParsedSym(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: ParsedSym) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.address)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.size)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.name)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ParsedSym =
                    ParsedSym(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ParsedSym) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.address)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.size)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.name)
                }
            }
    }
}

public open class Object internal constructor(
    /**
     * Zero-sized type representing the native endianness.
     *
     * We could use a literal instead, but this helps ensure correctness.
     */
    public var endian: NativeEndian,
    /**
     * The entire file data.
     */
    public var data: List<UByte>,
    sectionsCtorArg: SectionTable<Elf>,
    public var strings: StringTable,
    /**
     * List of pre-parsed and sorted symbols by base address.
     */
    public var syms: List<ParsedSym>,
) {
    public var sections: SectionTable<Elf> = sectionsCtorArg
        internal set

    public open fun section(stash: Stash, name: String): List<UByte>? {
        when (val matchVal = this.sectionHeader(name)) {
            null -> {}
            else -> {
                val section = matchVal
                val data = Bytes((section.data(this.endian, this.data).ok() ?: return null))
                // Check for DWARF-standard (gABI) compression, i.e., as generated
                // by ld's `- - compress - debug - sections = zlib - gabi` and
                // `- - compress - debug - sections = zstd` flags.
                val flags: ULong = section.shFlags(this.endian).into()
                if ((flags and ULong.from(SHF_COMPRESSED)) == 0uL) {
                    // Not compressed.
                    return data.first
                }
                val header = (data.read<CompressionHeader>().ok() ?: return null)
                when (header.chType(this.endian)) {
                    ELFCOMPRESS_ZLIB -> {
                        val size = (Int.tryFrom(header.chSize(this.endian)).ok() ?: return null)
                        val buf = stash.allocate(size)
                        decompressZlib(data.first, buf)
                        return buf
                    }
                    ELFCOMPRESS_ZSTD -> {
                        val size1 = (Int.tryFrom(header.chSize(this.endian)).ok() ?: return null)
                        val buf1 = stash.allocate(size1)
                        decompressZstd(data.first, buf1)
                        return buf1
                    }
                    else -> { return null }
                }
            }
        }
        // Check for the nonstandard GNU compression format, i.e., as generated
        // by ld's `- - compress - debug - sections = zlib - gnu` flag. This means that if
        // we're actually asking for `.debugInfo` then we need to look up a
        // section named `.zdebugInfo`.
        if (!name.startsWith(".debug_")) {
            return null
        }
        val debugName = name.substring(7).encodeToByteArray().map { it.toUByte() }
        val compressedSection =
            (this.sections.iter().mapNotNull(closure3@{ header1 ->
                val name1 = when (val tryResult = this.sections.sectionName(this.endian, header1).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
                if (name1.startsWith(".zdebug_".encodeToByteArray()) && name1[8 until name1.size] == debugName) {
                    header1
                } else {
                    null
                }
            }).next() ?: return null)
        val data1 = Bytes((compressedSection.data(this.endian, this.data).ok() ?: return null))
        if ((data1.readBytes(8).ok() ?: return null).first != "ZLIB\u0000\u0000\u0000\u0000".encodeToByteArray()) {
            return null
        }
        val size2 = (Int.tryFrom((data1.read<U32Bytes>().ok() ?: return null).get(BigEndian)).ok() ?: return null)
        val buf2 = stash.allocate(size2)
        decompressZlib(data1.first, buf2)
        return buf2
    }

    public open fun sectionHeader(name: String): SectionHeader? = this.sections.sectionByName(this.endian, name.encodeToByteArray().map { it.toUByte() }).map({ (index, section) -> section })

    public open fun searchSymtab(addr: ULong): List<UByte>? {
        // Same sort of binary search as Windows above
        val i2 =
            run {
                val matchVal = this.syms.binarySearchByKey(addr, { sym -> sym.address })
                when (matchVal) { is Result.Ok<*, *> -> {
                    val i = matchVal.okValue()
                    i
                }; is Result.Err<*, *> -> {
                    val i1 = matchVal.errValue()
                    (i1.checkedSub(1) ?: return null)
                } }
            }
        val sym = (this.syms.getOrNull(i2) ?: return null)
        return if (sym.address <= addr && addr <= sym.address + sym.size) {
            this.strings.get(sym.name).ok()
        } else {
            null
        }
    }

    public open fun searchObjectMap(addr: ULong): Pair<Context, ULong>? = null

    public open fun buildId(): List<UByte>? {
        for (section in this.sections.iter()) {
            run {
                val matchVal = section.notes(this.endian, this.data)
                if (matchVal is Result.Ok<*, *> && matchVal.okValue() != null) {
                    val okValueVal = matchVal.okValue()
                    val notes = okValueVal
                    while (true) {
                        val matchVal1 = notes.next()
                        if (!(matchVal1 is Result.Ok<*, *> && matchVal1.okValue() != null)) break
                        val okValueVal1 = matchVal1.okValue()
                        val note = okValueVal1
                        if (note.name() == ELF_NOTE_GNU && note.nType(this.endian) == NT_GNU_BUILD_ID) {
                            return note.desc()
                        }
                    }
                }
            }
        }
        return null
    }

    // The contents of the ".gnuDebuglink" section is documented at:
    // https://sourceware.org/gdb/onlinedocs/gdb/Separate-Debug-Files.html
    public open fun gnuDebuglinkPath(path: Path): Pair<PathBuf, UInt>? {
        val section = (this.sectionHeader(".gnu_debuglink") ?: return null)
        val data = (section.data(this.endian, this.data).ok() ?: return null)
        val len = (data.iter().indexOfFirst({ x -> x == 0 }).takeIf { it >= 0 } ?: return null)
        val filename = OsStr.fromBytes(data[0 until len])
        val offset = (len + 1 + 3) and 3.inv()
        val crcBytes = (data.get(offset until offset + 4)?.let({ bytes -> bytes.tryInto().ok() }) ?: return null)
        val crc = run { val bytesTmp4 = crcBytes; ((bytesTmp4[3].toUInt() shl 24) or (bytesTmp4[2].toUInt() shl 16) or (bytesTmp4[1].toUInt() shl 8) or bytesTmp4[0].toUInt()) }
        val pathDebug = (locateDebuglink(path, filename) ?: return null)
        return Pair(pathDebug, crc)
    }

    // The format of the ".gnuDebugaltlink" section is based on gdb.
    public open fun gnuDebugaltlinkPath(path: Path): Pair<PathBuf, List<UByte>>? {
        val section = (this.sectionHeader(".gnu_debugaltlink") ?: return null)
        val data = (section.data(this.endian, this.data).ok() ?: return null)
        val len = (data.iter().indexOfFirst({ x -> x == 0 }).takeIf { it >= 0 } ?: return null)
        val filename = OsStr.fromBytes(data[0 until len])
        val buildId = data[len + 1 until data.size]
        val pathSup = (locateDebugaltlink(path, filename, buildId) ?: return null)
        return Pair(pathSup, buildId)
    }

    public companion object {
        public operator fun invoke(
            endian: NativeEndian,
            data: List<UByte>,
            sections: SectionTable<Elf>,
            strings: StringTable,
            syms: List<ParsedSym>,
        ): Object = Object(
            endian = endian,
            data = data,
            sectionsCtorArg = sections,
            strings = strings,
            syms = syms,
        )

        public fun parse(data: List<UByte>): Object? {
            val elf = (Elf.parse(data).ok() ?: return null)
            val endian = (elf.endian().ok() ?: return null)
            val sections = (elf.sections(endian, data).ok() ?: return null)
            var syms = (sections.symbols(endian, data, Object.elf.SHT_SYMTAB).ok() ?: return null)
            if (syms.isEmpty()) {
                syms = (sections.symbols(endian, data, Object.elf.SHT_DYNSYM).ok() ?: return null)
            }
            val strings = syms.strings()
            var syms1 =
                syms.iter().filter({ sym ->
                    val stType = sym.stType()
                    stType == Object.elf.STT_FUNC || stType == Object.elf.STT_OBJECT
                }).filter({ sym -> sym.stShndx(endian) != Object.elf.SHN_UNDEF }).map({ sym ->
                    val address = sym.stValue(endian).into()
                    val size = sym.stSize(endian).into()
                    val name = sym.stName(endian)
                    ParsedSym(address = address, size = size, name = name)
                }).collect<Vec>()
            syms1.sortUnstableByKey({ s -> s.address })
            return Object(endian = endian, data = data, sections = sections, strings = strings, syms = syms1)
        }
    }
}

public fun decompressZlib(input: List<UByte>, output: MutableList<UByte>): Unit? {
    val (status, inRead, outRead) = decompress(DecompressorOxide.new(), input, output, 0, TINFL_FLAG_USING_NON_WRAPPING_OUTPUT_BUF or TINFL_FLAG_PARSE_ZLIB_HEADER)
    return if (status == TINFLStatus.Done && inRead == input.size && outRead == output.size) {} else {
        null
    }
}

public fun decompressZstd(input: List<UByte>, output: MutableList<UByte>): Unit? {
    var input1 = input
    var output1 = output
    while (!input1.isEmpty()) {
        var decoder1 =
            run {
                val matchVal = StreamingDecoder.new(input1)
                when {
                    matchVal is Result.Ok<*, *> -> {
                        val decoder = matchVal.okValue()
                        decoder
                    }
                    matchVal is Result.Err<*, *> && matchVal.errValue() is FrameDecoderError.ReadFrameHeaderError && (matchVal.errValue() as FrameDecoderError.ReadFrameHeaderError).value is ReadFrameHeaderError.SkipFrame -> {
                        val errValueVal = matchVal.errValue()
                        val length = ((errValueVal as FrameDecoderError.ReadFrameHeaderError).value as ReadFrameHeaderError.SkipFrame).length
                        input1 = (input1.get(length.toInt() until Int.MAX_VALUE) ?: return null)
                        continue
                    }
                    else -> { return null }
                }
            }
        while (true) {
            val bytesWritten = (decoder1.read(output1).ok() ?: return null)
            if (bytesWritten == 0) {
                break
            }
            output1 = output1.subList(bytesWritten, output1.size)
        }
    }
    if (!output1.isEmpty()) {
        // Lengths didn't match, something is wrong.
        return null
    }
    return Unit
}

public val DEBUG_PATH: String = "/usr/lib/debug"

public fun debugPathExists(): Boolean {
    val DEBUG_PATH_EXISTS: AtomicU8  = AtomicU8.new(0u.toUByte())
    var exists = DEBUG_PATH_EXISTS.load(Ordering.Relaxed)
    if (exists == 0) {
        exists = if (Path.new(DEBUG_PATH).isDir()) {
            1
        } else {
            2
        }
        DEBUG_PATH_EXISTS.store(exists, Ordering.Relaxed)
    }
    return exists == 1
}

/**
 * Locate a debug file based on its build ID.
 *
 * The format of build id paths is documented at:
 * https://sourceware.org/gdb/onlinedocs/gdb/Separate-Debug-Files.html
 */
public fun locateBuildId(buildId: List<UByte>): PathBuf? {
    val BUILD_ID_PATH: String = "/usr/lib/debug/.build-id/"
    val BUILD_ID_SUFFIX: String = ".debug"
    if (buildId.size < 2) {
        return null
    }
    if (!debugPathExists()) {
        return null
    }
    var path = ""
    path += BUILD_ID_PATH
    path += (Char.fromDigit(((buildId[0].toUInt() shr 4).toUByte()).toUInt(), 16) ?: return null)
    path += (Char.fromDigit((buildId[0] and 0xfu.toUByte()).toUInt(), 16) ?: return null)
    path += '/'
    for (byte in ((buildId).subList(1, (buildId).size))) {
        path += (Char.fromDigit((byte shr 4).toUInt(), 16) ?: return null)
        path += (Char.fromDigit((byte and 0xf).toUInt(), 16) ?: return null)
    }
    path += BUILD_ID_SUFFIX
    return PathBuf.from(path)
}

/**
 * Locate a file specified in a `.gnuDebuglink` section.
 *
 * `path` is the file containing the section.
 * `filename` is from the contents of the section.
 *
 * Search order is based on gdb, documented at:
 * https://sourceware.org/gdb/onlinedocs/gdb/Separate-Debug-Files.html
 *
 * gdb also allows the user to customize the debug search path, but we don't.
 *
 * gdb also supports debuginfod, but we don't yet.
 */
public fun locateDebuglink(path: Path, filename: OsStr): PathBuf? {
    val path1 = (canonicalize(path).ok() ?: return null)
    val parent = (path1.parent() ?: return null)
    val f = PathBuf.withCapacity(DEBUG_PATH.length + parent.asOsStr().len() + filename.len() + 2)
    val filename1 = Path.new(filename)
    // Try "/parent/filename" if it differs from "path"
    f.push(parent)
    f.push(filename1)
    if (f != path1 && f.isFile()) {
        return f
    }
    // Try "/parent/.debug/filename"
    f.clear()
    f.push(parent)
    f.push(".debug")
    f.push(filename1)
    if (f.isFile()) {
        return f
    }
    if (debugPathExists()) {
        // Try "/usr/lib/debug/parent/filename"
        f.clear()
        f.push(DEBUG_PATH)
        f.push((parent.takeIf { it.startsWith("/") }?.removePrefix("/"))!!)
        f.push(filename1)
        if (f.isFile()) {
            return f
        }
    }
    return null
}

/**
 * Locate a file specified in a `.gnuDebugaltlink` section.
 *
 * `path` is the file containing the section.
 * `filename` and `buildId` are the contents of the section.
 *
 * Search order is based on gdb:
 * - filename, which is either absolute or relative to `path`
 * - the build ID path under `BUILD_ID_PATH`
 *
 * gdb also allows the user to customize the debug search path, but we don't.
 *
 * gdb also supports debuginfod, but we don't yet.
 */
public fun locateDebugaltlink(path: Path, filename: OsStr, buildId: List<UByte>): PathBuf? {
    val filename1 = Path.new(filename)
    if (filename1.isAbsolute()) {
        if (filename1.isFile()) {
            return filename1.into()
        }
    } else {
        val path1 = (canonicalize(path).ok() ?: return null)
        val parent = (path1.parent() ?: return null)
        val f = PathBuf.from(parent)
        f.push(filename1)
        if (f.isFile()) {
            return f
        }
    }
    return locateBuildId(buildId)
}

public fun handleSplitDwarf(pkg: DwarfPackage<EndianSlice<Endian>>?, stash: Stash, load: SplitDwarfLoad<EndianSlice<Endian>>): Dwarf<EndianSlice<Endian>>? {
    when (val matchVal = pkg) {
        null -> {}
        else -> {
            val dwp = matchVal
            run {
                val matchVal1 = dwp.findCu(load.dwoId, load.parent)
                if (matchVal1 is Result.Ok<*, *> && matchVal1.okValue() != null) {
                    val okValueVal = matchVal1.okValue()
                    val cu = okValueVal
                    return cu
                }
            }
        }
    }
    val path = PathBuf.new()
    when (val matchVal2 = load.compDir.asRef()) {
        null -> {}
        else -> {
            val p = matchVal2
            path.push(OsStr.fromBytes(p))
        }
    }
    path.push(OsStr.fromBytes((load.path.asRef() ?: return null)))
    when (val matchVal3 = mmap(path)) {
        null -> {}
        else -> {
            val mapDwo = matchVal3
            val mapDwo1 = stash.cacheMmap(mapDwo)
            when (val matchVal4 = Object.parse(mapDwo1)) {
                null -> {}
                else -> {
                    val dwo = matchVal4
                    return Dwarf.load({ id ->
                        val data = id.dwoName().andThen({ name -> dwo.section(stash, name) }).unwrapOr(listOf())
                        Result.Ok(EndianSlice.new(data, Endian))
                    }).ok()?.let({ dwoDwarf ->
                        dwoDwarf.makeDwo(load.parent)
                        dwoDwarf
                    })
                }
            }
        }
    }
    return null
}

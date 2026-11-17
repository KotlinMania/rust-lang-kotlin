// port-lint: source symbolize/gimli.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli

import io.github.kotlinmania.rustlang.backtrace.mystd.ffi.OsString
import io.github.kotlinmania.rustlang.backtrace.mystd.fs.File
import io.github.kotlinmania.rustlang.backtrace.mystd.path.Path
import io.github.kotlinmania.rustlang.backtrace.symbolize.ResolveWhat
import io.github.kotlinmania.rustlang.backtrace.symbolize.SymbolName
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.coff.Object
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.coff.handleSplitDwarf
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.gimli.read.EndianSlice
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.lru.Lru
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap.Mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash.Stash
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castConst
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingAdd
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.gimli.NativeEndian as Endian

// Support for symbolication using the `gimli` crate on crates.io
//
// This is the default symbolication implementation for Rust.

public val MAPPINGS_CACHE_SIZE: Int = 4

public open class Mapping(
    // static scope is a lie to Note around lack of support for self-referential structs.
    public var cx: Context,
    public var map: Mmap,
    public var stash: Stash,
) {
    public companion object {
        /**
         * Creates a `Mapping` by ensuring that the `data` specified is used to
         * create a `Context` and it can only borrow from that or the `Stash` of
         * decompressed sections or auxiliary data.
         */
        public fun mk(data: Mmap, mk: (List<UByte>, Stash) -> Context?): Mapping? {
            return Mapping.mkOrOther(data, closure@{ data1, stash ->
                val cx = when (val tryResult = mk(data1, stash)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                Either.B(cx)
            })
        }

        /**
         * Creates a `Mapping` from `data`, or if the closure decides to, returns a
         * different mapping.
         */
        public fun mkOrOther(data: Mmap, mk: (List<UByte>, Stash) -> Either<Mapping, Context>?): Mapping? {
            val stash = Stash.new()
            val cx1 =
                when (val matchVal = (mk(data, stash) ?: return null)) {
                    is Either.A<*, *> -> {
                        val (mapping) = matchVal
                        return mapping
                    }
                    is Either.B<*, *> -> {
                        val (cx) = matchVal
                        cx
                    }
                }
            return Mapping(cx = cx1, map = data, stash = stash)
        }
    }
}

public sealed class Either<A, B> {
    public data class A<A, B>(public var value: A) : Either<A, B>()
    public data class B<A, B>(public var value: B) : Either<A, B>()
}

public open class Context internal constructor(
    dwarfCtorArg: Context<EndianSlice<Endian>>,
    public var obj: Object,
    pkgCtorArg: gimli.DwarfPackage<EndianSlice<Endian>>?,
) {
    public var dwarf: Context<EndianSlice<Endian>> = dwarfCtorArg
        internal set

    public var pkg: gimli.DwarfPackage<EndianSlice<Endian>>? = pkgCtorArg
        internal set

    public open fun findFrames(stash: Stash, probe: ULong): gimli.Result<FrameIter<EndianSlice<Endian>>> {
        var l = this.dwarf.findFrames(probe)
        while (true) {
            val (load1, continuation1) =
                when (l) {
                    is LookupResult.Output -> {
                        val (output) = l
                        break
                    }
                    is LookupResult.Load -> {
                        val load = l.load
                        val continuation = l.continuation
                        Pair(load, continuation)
                    }
                }
            l = continuation1.resume(handleSplitDwarf(this.pkg, stash, load1))
        }
    }

    public companion object {
        public operator fun invoke(
            dwarf: Context<EndianSlice<Endian>>,
            obj: Object,
            pkg: gimli.DwarfPackage<EndianSlice<Endian>>?,
        ): Context = Context(
            dwarfCtorArg = dwarf,
            obj = obj,
            pkgCtorArg = pkg,
        )

        // @feature(optimizeAttr) is enabled when we're built inside libstd
        public fun new(stash: Stash, obj: Object, sup: Object?, dwp: Object?): Context? {
            var sections =
                (gimli.Dwarf.load({ id ->
                    if (true) {
                        val data = obj.section(stash, id.name()).unwrapOr(listOf())
                        Result.Ok(EndianSlice.new(data, Endian))
                    } else when (val matchVal = id.xcoffName()) {
                        null -> {
                            Result.Ok(EndianSlice.new(listOf(), Endian))
                        }
                        else -> {
                            val name = matchVal
                            val data1 = obj.section(stash, name).unwrapOr(listOf())
                            Result.Ok(EndianSlice.new(data1, Endian))
                        }
                    }
                }).ok() ?: return null)
            if (sup != null) {
                val sup1 = sup
                (sections.loadSup({ id ->
                    val data = sup1.section(stash, id.name()).unwrapOr(listOf())
                    Result.Ok(EndianSlice.new(data, Endian))
                }).ok() ?: return null)
            }
            val dwarf = (Context.fromDwarf(sections).ok() ?: return null)
            var pkg = null
            if (dwp != null) {
                val dwp1 = dwp
                pkg = (gimli.DwarfPackage.load({ id ->
                    val data = id.dwoName().andThen({ name -> dwp1.section(stash, name) }).unwrapOr(listOf())
                    Result.Ok(EndianSlice.new(data, Endian))
                }, EndianSlice.new(listOf(), Endian)).ok() ?: return null)
            }
            return Context(dwarf = dwarf, obj = obj, pkg = pkg)
        }
    }
}

public fun mmap(path: Path): Mmap? {
    val file = (File.open(path).ok() ?: return null)
    val len = ((file.metadata().ok() ?: return null).len().tryInto().ok() ?: return null)
    return Mmap.map(file, len, 0uL)
}

// Everything else should doesn't know how to load native libraries.
public fun nativeLibraries(): List<Library> = mutableListOf()

public open class Cache internal constructor(
    /**
     * All known shared libraries that have been loaded.
     */
    public var libraries: List<Library>,
    mappingsCtorArg: Lru<Pair<Int, Mapping>>,
) {
    /**
     * Mappings cache where we retain parsed dwarf information.
     *
     * This list has a fixed capacity for its entire scope which never
     * increases. The `Int` element of each pair is an index into `libraries`
     * above where `Int.maxValue()` represents the current executable. The
     * `Mapping` is corresponding parsed dwarf information.
     *
     * Note that this is basically an LRU cache and we'll be shifting things
     * around in here as we symbolize addresses.
     */
    public var mappings: Lru<Pair<Int, Mapping>> = mappingsCtorArg
        internal set

    public open fun avmaToSvma(addr: ConstPtr): Pair<Int, ConstPtr>? {
        return (this.libraries.mapIndexed { index, item -> Pair(index, item) }).mapNotNull(closure1@{ (i, lib) ->
            // First up, test if this `lib` has any segment containing the
            // `addr` (handling relocation). If this check passes then we
            // can continue below and actually translate the address.
            //
            // Note that we're using `wrappingAdd` here to avoid overflow
            // checks. It's been seen in the wild that the SVMA + bias
            // computation overflows. It seems a bit odd that would happen
            // but there's not a huge amount we can do about it other than
            // probably just ignore those segments since they're likely
            // pointing off into space. This originally came up in
            // rust-lang/backtrace-rs#329.
            if (!lib.segments.iter().any({ s ->
                val svma = s.statedVirtualMemoryAddress
                val start = svma.wrappingAdd(lib.bias)
                val end = start.wrappingAdd(s.len)
                val address = addr.rawAddress.toInt()
                start <= address && address < end
            })) {
                return@closure1 null
            }
            // Now that we know `lib` contains `addr`, we can offset with
            // the bias to find the stated virtual memory address.
            val svma = ((addr.rawAddress.toInt()) - (lib.bias.toInt()))
            Pair(i, ConstPtr(svma.toLong()))
        }).next()
    }

    public open fun mappingForLib(lib: Int): Pair<Context, Stash>? {
        val cacheIdx = this.mappings.iter().indexOfFirst({ (libId, _) -> libId == lib }).takeIf { it >= 0 }
        val cacheEntry =
            if (cacheIdx != null) {
                val idx = cacheIdx
                this.mappings.moveToFront(idx)
            } else {
                // When the mapping is not in the cache, create a new mapping and insert it,
                // which will also evict the oldest entry.
                createMapping(this.libraries[lib])?.let({ mapping -> this.mappings.pushFront(Pair(lib, mapping)) })
            }
        val (_, mapping) = (cacheEntry ?: return null)
        val cx: Context = mapping.cx
        val stash: Stash = mapping.stash
        // don't leak the `static` scope, make sure it's scoped to just
        // ourselves
        return Pair(cx, stash)
    }

    public companion object {
        public operator fun invoke(
            libraries: List<Library>,
            mappings: Lru<Pair<Int, Mapping>>,
        ): Cache = Cache(
            libraries = libraries,
            mappingsCtorArg = mappings,
        )

        public fun new(): Cache = Cache(mappings = Lru.default(), libraries = nativeLibraries())

        // unchecked because this is required to be externally synchronized
        // @feature(optimizeAttr) is enabled when we're built inside libstd
        public fun withGlobal(f: (Cache) -> Unit) {
            // A very small, very simple LRU cache for debug info mappings.
            //
            // The hit rate should be very high, since the typical stack doesn't cross
            // between many shared libraries.
            //
            // The `addr2line.Context` structures are pretty expensive to create. Its
            // cost is expected to be amortized by subsequent `locate` queries, which
            // leverage the structures built when constructing `addr2line.Context`s to
            // get nice speedups. If we didn't have this cache, that amortization would
            // never happen, and symbolicating backtraces would be ssssllllooooowwww.
            val MAPPINGS_CACHE: Cache?  = null
            f(MAPPINGS_CACHE.getOrInsertWith(Cache::new))
        }
    }
}

public open class Library(
    public var name: OsString,
    /**
     * On Android, the dynamic linker [can map libraries directly from a
     * ZIP archive][ndk-linker-changes] (typically an `.apk`).
     *
     * The linker requires that these libraries are stored uncompressed
     * and page-aligned.
     *
     * These "embedded" libraries have filepaths of the form
     * `/ path / to / my.apk / lib / mylib.so` (where `/ path / to / my.apk` is the archive
     * and `lib / mylib.so` is the name of the library within the archive).
     *
     * This mechanism is present on Android since API level 23.
     *
     * [ndk-linker-changes]: https://android.googlesource.com/platform/bionic/+/main/android-changes-for-ndk-developers.md#opening-shared-libraries-directly-from-an-apk
     */
    public var zipOffset: ULong?,
    /**
     * Segments of this library loaded into memory, and where they're loaded.
     */
    public var segments: List<LibrarySegment>,
    /**
     * The "bias" of this library, typically where it's loaded into memory.
     * This value is added to each segment's stated address to get the actual
     * virtual memory address that the segment is loaded into. Additionally
     * this bias is subtracted from real virtual memory addresses to index into
     * debuginfo and the symbol table.
     */
    public var bias: Int,
)

public open class LibrarySegment(
    /**
     * The stated address of this segment in the object file. This is not
     * actually where the segment is loaded, but rather this address plus the
     * containing library's `bias` is where to find it.
     */
    public var statedVirtualMemoryAddress: Int,
    /**
     * The size of this segment in memory.
     */
    public var len: Int,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<LibrarySegment> =
            object : TypeLayout<LibrarySegment> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LibrarySegment =
                    LibrarySegment(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: LibrarySegment) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.statedVirtualMemoryAddress)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibrarySegment =
                    LibrarySegment(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibrarySegment) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.statedVirtualMemoryAddress)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.len)
                }
            }
    }
}

public fun createMapping(lib: Library): Mapping? = Mapping.newAndroid(lib.name.asRef(), lib.zipOffset)

/**
 * Try to extract the archive path from an "embedded" library path
 * (e.g. `/ path / to / my.apk` from `/ path / to / my.apk / mylib.so`).
 *
 * Returns `None` if the path does not contain a `! /` separator.
 */
public fun extractZipPathAndroid(path: OsStr): OsStr? = (path.asBytes().windows(2).mapIndexed { index, item -> Pair(index, item) }.indexOf({ (_, chunk) -> chunk == "!/".encodeToByteArray() }).takeIf { it >= 0 })?.let({ (index, _) -> OsStr.fromBytes(path.asBytes().splitAt(index).first) })

// unchecked because this is required to be externally synchronized
public fun clearSymbolCache() {
    run {
        Cache.withGlobal({ cache -> cache.mappings.clear() })
    }
}

public fun resolve(what: ResolveWhat, cb: (io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol) -> Unit) {
    val addr = what.addressOrIp()
    var call =
        { sym: Symbol ->
            // Extend the scope of `sym` to `static` since we are unfortunately
            // required to here, but it's only ever going out as a reference so no
            // reference to it should be persisted beyond this frame anyway.
            // SAFETY: praying the above is correct
            val sym1 = sym
            (cb)(io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol(inner = sym1))
        }
    run {
        Cache.withGlobal(closure2@{ cache ->
            val (lib, addr1) =
                when (val matchVal = cache.avmaToSvma(addr.castConst().cast())) {
                    null -> { return@closure2 }
                    else -> {
                        val pair = matchVal
                        pair
                    }
                }
            // Finally, get a cached mapping or create a new mapping for this file, and
            // evaluate the DWARF info to find the file/line/name for this address.
            val (cx1, stash1) =
                when (val matchVal1 = cache.mappingForLib(lib)) {
                    null -> { return@closure2 }
                    else -> {
                        val cx = matchVal1.first
                        val stash = matchVal1.second
                        Pair(cx, stash)
                    }
                }
            var anyFrames = false
            when (val matchVal2 = cx1.findFrames(stash1, addr1.toULong())) {
                is Result.Ok<*, *> -> {
                    val frames = matchVal2.okValue()
                    while (true) {
                        val matchVal3 = frames.next()
                        if (!(matchVal3 is Result.Ok<*, *> && matchVal3.okValue() != null)) break
                        val okValueVal = (matchVal3 as Result.Ok<*, *>).okValue()
                        val frame = okValueVal
                        anyFrames = true
                        val name =
                            when (val matchVal4 = frame.function) {
                                null -> cx1.obj.searchSymtab(addr1.toULong())
                                else -> {
                                    val f = matchVal4
                                    f.name.slice()
                                }
                            }
                        call(Symbol.Frame(addr = mutPtrFromRustCast(addr1), location = frame.location, name = name))
                    }
                }
                else -> {}
            }
            if (!anyFrames) {
                when (val matchVal5 = cx1.obj.searchObjectMap(addr1.toULong())) {
                    null -> {}
                    else -> {
                        val objectCx = matchVal5.first
                        val objectAddr = matchVal5.second
                        when (val matchVal6 = objectCx.findFrames(stash1, objectAddr)) {
                            is Result.Ok<*, *> -> {
                                val frames1 = matchVal6.okValue()
                                while (true) {
                                    val matchVal7 = frames1.next()
                                    if (!(matchVal7 is Result.Ok<*, *> && matchVal7.okValue() != null)) break
                                    val okValueVal1 = (matchVal7 as Result.Ok<*, *>).okValue()
                                    val frame1 = okValueVal1
                                    anyFrames = true
                                    call(Symbol.Frame(addr = mutPtrFromRustCast(addr1), location = frame1.location, name = frame1.function.map({ f1 -> f1.name.slice() })))
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            if (!anyFrames) {
                when (val matchVal8 = cx1.obj.searchSymtab(addr1.toULong())) {
                    null -> {}
                    else -> {
                        val name1 = matchVal8
                        call(Symbol.Symtab(name = name1))
                    }
                }
            }
        })
    }
}

public sealed class Symbol {
    /**
     * We were able to locate frame information for this symbol, and
     * `addr2line`s frame internally has all the nitty gritty details.
     */
    public class Frame internal constructor(addrCtorArg: MutPtr, public var location: Location?, public var name: List<UByte>?) : Symbol() {
        public var addr: MutPtr = addrCtorArg
            internal set

        public operator fun component1(): MutPtr = this.addr

        public operator fun component2(): Location? = this.location

        public operator fun component3(): List<UByte>? = this.name

        public fun copy(addr: MutPtr = this.addr, location: Location? = this.location, name: List<UByte>? = this.name): Frame = Frame(addrCtorArg = addr, location = location, name = name)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Frame) return false
            if (this.addr != other.addr) return false
            if (this.location != other.location) return false
            if (this.name != other.name) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.addr.hashCode()
            result = 31 * result + this.location.hashCode()
            result = 31 * result + this.name.hashCode()
            return result
        }

        public companion object {
            public operator fun invoke(
                addr: MutPtr,
                location: Location?,
                name: List<UByte>?,
            ): Frame = Frame(
                addrCtorArg = addr,
                location = location,
                name = name,
            )
        }
    }
    /**
     * Couldn't find debug information, but we found it in the symbol table of
     * the elf executable.
     */
    public data class Symtab(public var name: List<UByte>) : Symbol()

    public open fun name(): SymbolName? {
        return when (this) {
            is Symbol.Frame -> {
                val name = (this as Symbol.Frame).name
                val name1 = (name.asRef() ?: return null)
                SymbolName.new(name1)
            }
            is Symbol.Symtab -> {
                val name2 = (this as Symbol.Symtab).name
                SymbolName.new(name2)
            }
        }
    }

    public open fun addr(): MutPtr? =
        when (this) {
            is Symbol.Frame -> {
                val addr = (this as Symbol.Frame).addr
                addr
            }
            is Symbol.Symtab -> null
        }

    public open fun filenameRaw(): BytesOrWideString? {
        return when (this) {
            is Symbol.Frame -> {
                val location = (this as Symbol.Frame).location
                val file = ((location.asRef() ?: return null).file ?: return null)
                BytesOrWideString.Bytes(file.encodeToByteArray().map { it.toUByte() })
            }
            is Symbol.Symtab -> null
        }
    }

    public open fun filename(): Path? {
        return when (this) {
            is Symbol.Frame -> {
                val location = (this as Symbol.Frame).location
                val file = ((location.asRef() ?: return null).file ?: return null)
                Path.new(file)
            }
            is Symbol.Symtab -> null
        }
    }

    public open fun lineno(): UInt? {
        return when (this) {
            is Symbol.Frame -> {
                val location = (this as Symbol.Frame).location
                (location.asRef() ?: return null).line
            }
            is Symbol.Symtab -> null
        }
    }

    public open fun colno(): UInt? {
        return when (this) {
            is Symbol.Frame -> {
                val location = (this as Symbol.Frame).location
                (location.asRef() ?: return null).column
            }
            is Symbol.Symtab -> null
        }
    }
}

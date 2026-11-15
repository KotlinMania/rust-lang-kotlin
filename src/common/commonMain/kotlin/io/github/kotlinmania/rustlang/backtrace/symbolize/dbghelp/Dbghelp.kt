// port-lint: source symbolize/dbghelp.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.dbghelp

import io.github.kotlinmania.rustlang.backtrace.dbghelp.Dbghelp
import io.github.kotlinmania.rustlang.backtrace.dbghelp.SymAddrIncludeInlineTrace
import io.github.kotlinmania.rustlang.backtrace.dbghelp.SymFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.dbghelp.SymGetLineFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.dbghelp.SymQueryInlineTrace
import io.github.kotlinmania.rustlang.backtrace.symbolize.ResolveWhat
import io.github.kotlinmania.rustlang.backtrace.symbolize.SymbolName
import io.github.kotlinmania.rustlang.backtrace.symbolize.adjustIp
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.backtrace.windowssys.Bool
import io.github.kotlinmania.rustlang.backtrace.windowssys.CP_UTF8
import io.github.kotlinmania.rustlang.backtrace.windowssys.ImagehlpLinew64
import io.github.kotlinmania.rustlang.backtrace.windowssys.MAX_SYM_NAME
import io.github.kotlinmania.rustlang.backtrace.windowssys.TRUE
import io.github.kotlinmania.rustlang.core.marker.PhantomData
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.std.path.Path

// Symbolication strategy using `dbghelp.dll` on Windows, only used for MSVC
//
// This symbolication strategy, like with backtraces, uses dynamically loaded
// information from `dbghelp.dll`. (see `src / dbghelp.rs` for info about why
// it's dynamically loaded).
//
// This API selects its resolution strategy based on the frame provided or the
// information we have at hand. If a frame from `StackWalkEx` is given to us
// then we use similar APIs to generate correct information about inlined
// functions. Otherwise if all we have is an address or an older stack frame
// from `StackWalk64` we use the older APIs for symbolication.
//
// There's a good deal of support in this module, but a good chunk of it is
// converting back and forth between Windows types and Rust types. For example
// symbols come to us as wide strings which we then convert to utf-8 strings if
// we can.

// Note: replace with ptr.fromRef once MSRV is high enough
public fun <T> ptrFromRef(r: T): ConstPtr = r

// Store an OsString on std so we can provide the symbol name and filename.
public open class Symbol internal constructor(
    nameCtorArg: ConstPtr,
    addrCtorArg: MutPtr,
    public var line: UInt?,
    filenameCtorArg: ConstPtr?,
    public var filenameCache: OsString?,
    public var marker: Unit,
) {
    public var name: ConstPtr = nameCtorArg
        internal set

    public var addr: MutPtr = addrCtorArg
        internal set

    public var filename: ConstPtr? = filenameCtorArg
        internal set

    public open fun name(): SymbolName? = SymbolName.new((UnresolvedTypeLayout<List<UByte>>()).readNative((this.name).rawAddress))

    public open fun addr(): MutPtr? = this.addr

    public open fun filenameRaw(): BytesOrWideString? = this.filename?.let({ slice -> BytesOrWideString.Wide(slice) })

    public open fun colno(): UInt? = null

    public open fun lineno(): UInt? = this.line

    public open fun filename(): Path? = this.filenameCache?.let(Path::new)

    public companion object {
        public operator fun invoke(
            name: ConstPtr,
            addr: MutPtr,
            line: UInt?,
            filename: ConstPtr?,
            filenameCache: OsString?,
            marker: Unit,
        ): Symbol =
            Symbol(
                nameCtorArg = name,
                addrCtorArg = addr,
                line = line,
                filenameCtorArg = filename,
                filenameCache = filenameCache,
                marker = marker,
            )
    }
}

public open class Aligned8<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T>,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) {
            fieldLayout.writeNative(fieldStorage.rawAddress, value)
        }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 8L)), AlignedTypeLayout(fieldLayout, 8L))

    public fun pointerLayout(): TypeLayout<Aligned8<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Aligned8<T>(ptr, layout) }, { it.value })
}

public fun resolve(what: ResolveWhat, cb: (io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol) -> Unit) {
    // Ensure this process's symbols are initialized
    val dbghelp1 =
        run {
            val matchVal = Dbghelp.init()
            when (matchVal) {
                is Result.Ok<*, *> -> {
                    val dbghelp = matchVal.value
                    dbghelp
                }
                is Result.Err<*, *> -> {
                    return
                }
            }
        }
    run {
        when (what) {
            is ResolveWhat.Address -> {
                resolveWithInline(dbghelp1, what.addressOrIp(), null, cb)
            }
            is ResolveWhat.Frame -> {
                val (frame) = what
                resolveWithInline(dbghelp1, frame.ip(), frame.inner.inlineContext(), cb)
            }
        }
    }
}

/**
 * Resolve the address using the modern dbghelp APIs.
 *
 * Note that calling this function requires having dbghelp 6.2+ loaded - and
 * will panic otherwise.
 */
public fun resolveWithInline(dbghelp: Init, addr: MutPtr, inlineContext: UInt?, cb: (io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol) -> Unit): Unit? {
    val inlineContext1 = inlineContext
    run {
        val currentProcess = GetCurrentProcess()
        // Ensure we have the functions we need. Return if any aren't found.
        val addr1 = adjustIp(addr).rawAddress.toULong()
        val (inlinedFrameCount1, inlineContext3) =
            if (inlineContext1 != null) {
                val ic = inlineContext1
                Pair(0, ic)
            } else {
                var inlinedFrameCount = SymAddrIncludeInlineTrace(currentProcess, addr1)
                val localStorageL176C12 = AddressableLocal(0, PrimitiveLayouts.I32)
                var inlineContext2 by localStorageL176C12
                // If there is are inlined frames but we can't load them for some reason OR if there are no
                // inlined frames, then we disregard inlinedFrameCount and inlineContext.
                if ((inlinedFrameCount > 0 && SymQueryInlineTrace(currentProcess, addr1, 0, addr1, addr1, RustMutableReference(localStorageL176C12.asMutPtr(), localStorageL176C12.layout), RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32)) != TRUE) || inlinedFrameCount == 0) {
                    inlinedFrameCount = 0
                    inlineContext2 = 0
                }
                Pair(inlinedFrameCount, inlineContext2)
            }
        val lastInlineContext = inlineContext3 + 1 + inlinedFrameCount1
        for (inlineContext4 in inlineContext3 until lastInlineContext) {
            doResolve({ info -> SymFromInlineContextW(currentProcess, addr1, inlineContext4, RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32), info) }, { line ->
                SymGetLineFromInlineContextW(currentProcess, addr1, inlineContext4, 0, RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32), line)
            }, cb)
        }
    }
    return Unit
}

/**
 * This function is only meant to be called with certain Windows API functions as its arguments,
 * using closures to simplify away here-unspecified arguments:
 * - `symFromAddr`: either `SymFromAddrW` or `SymFromInlineContextW`
 * - `getLineFromAddr`: `SymGetLineFromAddrW64` or `SymGetLineFromInlineContextW`
 */
public fun doResolve(symFromAddr: (MutPtr) -> Bool, getLineFromAddr: (RustMutableReference<ImagehlpLinew64>) -> Bool, cb: (io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol) -> Unit) {
    val data = MaybeUninit.zeroed(UnresolvedTypeLayout<Aligned8<UByteArray>>())
    val info = data.asMutPtr().cast()
    info.maxNameLen = MAX_SYM_NAME
    // the struct size in C.  the value is different to
    // `sizeOf<SYMBOL_INFOW>() - MAX_SYM_NAME + 1` (== 81)
    // due to struct alignment.
    info.sizeOfStruct = 88
    if (symFromAddr(info) != TRUE) {
        return
    }
    // If the symbol name is greater than MaxNameLen, SymFromAddrW will
    // give a buffer of (MaxNameLen - 1) characters and set NameLen to
    // the real value.
    // SAFETY: We assume NameLen has been initialized by SymFromAddrW, and we initialized MaxNameLen
    val nameLen = minOf(info.nameLen.toInt(), info.maxNameLen.toInt() - 1)
    // SAFETY: Name must be initialized by SymFromAddrW, but we only interact with it as a pointer anyways.
    val namePtr = (addrOf(info.name)).cast()
    // Reencode the utf-16 symbol to utf-8 so we can use `SymbolName.new` like
    // all other platforms
    val localStorageL253C4 = AddressableArrayLocal.fromUByteArray(UByteArray(256) { 0u.toUByte() })
    var nameBuffer by localStorageL253C4
    var nameLen1 = WideCharToMultiByte(CP_UTF8, 0, namePtr, nameLen.toInt(), localStorageL253C4.elementMutPtr(0), nameBuffer.size, MutPtr(0L), MutPtr(0L))
    if (nameLen1 == 0) {
        // If the returned length is zero that means the buffer wasn't big enough.
        // However, the buffer will be filled with as much as will fit.
        nameLen1 = nameBuffer.size
    } else if (nameLen1 > nameBuffer.size) {
        // This can't happen.
        return
    }
    val name = addrOf(((nameBuffer).sliceArray(0 until nameLen1)))
    var line = ImagehlpLinew64(sizeOfStruct = 0u, key = MutPtr(0L), lineNumber = 0u, fileName = MutPtr(0L), address = 0uL)
    line.sizeOfStruct = sizeOf<ImagehlpLinew64>(typeLayoutT = UnresolvedTypeLayout<ImagehlpLinew64>()).toUInt()
    var filename = null
    var lineno = null
    if (getLineFromAddr(line) == TRUE) {
        lineno = line.lineNumber
        val base = line.fileName
        var len = 0
        while (base.offset(len) != 0) {
            len += 1
        }
        val len1 = len.toInt()
        run {
            filename = ptrFromRef(Slice.fromRawParts(base, len1))
        }
    }
    cb(
        io.github.kotlinmania.rustlang.backtrace.symbolize
            .Symbol(inner = Symbol(name = name, addr = mutPtrFromRustCast(info.address), line = lineno, filename = filename, filenameCache = cache(filename), marker = PhantomData)),
    )
}

public fun cache(filename: ConstPtr?): OsString? = filename?.let({ f -> OsString.fromWide(f) })

public fun clearSymbolCache() {}

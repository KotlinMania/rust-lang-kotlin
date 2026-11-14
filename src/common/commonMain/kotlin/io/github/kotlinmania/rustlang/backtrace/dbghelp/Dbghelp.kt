// port-lint: source dbghelp.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.dbghelp

import io.github.kotlinmania.rustlang.backtrace.lock.LOCK
import io.github.kotlinmania.rustlang.backtrace.windowssys.Bool
import io.github.kotlinmania.rustlang.backtrace.windowssys.FALSE
import io.github.kotlinmania.rustlang.backtrace.windowssys.Handle
import io.github.kotlinmania.rustlang.backtrace.windowssys.Hinstance
import io.github.kotlinmania.rustlang.backtrace.windowssys.INFINITE
import io.github.kotlinmania.rustlang.backtrace.windowssys.Pcwstr
import io.github.kotlinmania.rustlang.backtrace.windowssys.PenumloadedModulesCallbackw64
import io.github.kotlinmania.rustlang.backtrace.windowssys.PfunctionTableAccessRoutine64
import io.github.kotlinmania.rustlang.backtrace.windowssys.PgetModuleBaseRoutine64
import io.github.kotlinmania.rustlang.backtrace.windowssys.PreadProcessMemoryRoutine64
import io.github.kotlinmania.rustlang.backtrace.windowssys.PtranslateAddressRoutine64
import io.github.kotlinmania.rustlang.backtrace.windowssys.Pwstr
import io.github.kotlinmania.rustlang.backtrace.windowssys.SYMOPT_DEFERRED_LOADS
import io.github.kotlinmania.rustlang.backtrace.windowssys.TRUE
import io.github.kotlinmania.rustlang.backtrace.windowssys.lstrlenW
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr

// A module to assist in managing dbghelp bindings on Windows
//
// Backtraces on Windows (at least for MSVC) are largely powered through
// `dbghelp.dll` and the various functions that it contains. These functions
// are currently loaded *dynamically* rather than linking to `dbghelp.dll`
// statically. This is currently done by the standard library (and is in theory
// required there), but is an effort to help reduce the static dll dependencies
// of a library since backtraces are typically pretty optional. That being
// said, `dbghelp.dll` almost always successfully loads on Windows.
//
// Note though that since we're loading all this support dynamically we can't
// actually use the raw definitions in `windowsSys`, but rather we need to define
// the function pointer types ourselves and use that. We don't really want to
// be in the business of duplicating auto-generated bindings, so we assert that all bindings match
// those in `windowsSys.rs`.
//
// Finally, you'll note here that the dll for `dbghelp.dll` is never unloaded,
// and that's currently intentional. The thinking is that we can globally cache
// it and use it between calls to the API, avoiding expensive loads/unloads. If
// this is a problem for leak detectors or something like that we can cross the
// bridge when we get there.

public fun interface FnCallback {
    public operator fun invoke(): UInt
}

public fun interface FnCallback1 {
    public operator fun invoke(arg0: UInt): UInt
}

public fun interface FnCallback2 {
    public operator fun invoke(arg0: Handle, arg1: Pcwstr, arg2: Bool): Bool
}

public fun interface FnCallback3 {
    public operator fun invoke(arg0: Handle, arg1: Pwstr, arg2: UInt): Bool
}

public fun interface FnCallback4 {
    public operator fun invoke(arg0: Handle, arg1: Pcwstr): Bool
}

public fun interface FnCallback5 {
    public operator fun invoke(arg0: Handle, arg1: PenumloadedModulesCallbackw64, arg2: ConstPtr): Bool
}

public fun interface FnCallback6 {
    public operator fun invoke(arg0: UInt, arg1: Handle, arg2: Handle, arg3: MutPtr, arg4: MutPtr, arg5: PreadProcessMemoryRoutine64, arg6: PfunctionTableAccessRoutine64, arg7: PgetModuleBaseRoutine64, arg8: PtranslateAddressRoutine64): Bool
}

public fun interface FnCallback7 {
    public operator fun invoke(arg0: Handle, arg1: ULong): MutPtr
}

public fun interface FnCallback8 {
    public operator fun invoke(arg0: Handle, arg1: ULong): ULong
}

public fun interface FnCallback9 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: MutPtr, arg3: MutPtr): Bool
}

public fun interface FnCallback10 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: MutPtr, arg3: MutPtr): Bool
}

public fun interface FnCallback11 {
    public operator fun invoke(arg0: UInt, arg1: Handle, arg2: Handle, arg3: MutPtr, arg4: MutPtr, arg5: PreadProcessMemoryRoutine64, arg6: PfunctionTableAccessRoutine64, arg7: PgetModuleBaseRoutine64, arg8: PtranslateAddressRoutine64, arg9: UInt): Bool
}

public fun interface FnCallback12 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: MutPtr, arg4: MutPtr): Bool
}

public fun interface FnCallback13 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: ULong, arg4: MutPtr, arg5: MutPtr): Bool
}

public fun interface FnCallback14 {
    public operator fun invoke(arg0: Handle, arg1: ULong): UInt
}

public fun interface FnCallback15 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: ULong, arg4: ULong, arg5: MutPtr, arg6: MutPtr): Bool
}

public open class Dbghelp internal constructor(
    dllCtorArg: Hinstance,
    public var symGetOptions: Int,
    public var symSetOptions: Int,
    public var symInitializeW: Int,
    public var symGetSearchPathW: Int,
    public var symSetSearchPathW: Int,
    public var enumerateLoadedModulesW64: Int,
    public var stackWalk64: Int,
    public var symFunctionTableAccess64: Int,
    public var symGetModuleBase64: Int,
    public var symFromAddrW: Int,
    public var symGetLineFromAddrW64: Int,
    public var stackWalkEx: Int,
    public var symFromInlineContextW: Int,
    public var symGetLineFromInlineContextW: Int,
    public var symAddrIncludeInlineTrace: Int,
    public var symQueryInlineTrace: Int,
) {
    /**
     * The loaded DLL for `dbghelp.dll`
     */
    public var dll: Hinstance = dllCtorArg
        internal set

    /**
     * Attempts to open `dbghelp.dll`. Returns success if it works or
     * error if `LoadLibraryW` fails.
     */
    public open fun ensureOpen(): Result<Unit, Unit> {
        if (!this.dll.isNull()) {
            return Result.Ok(Unit)
        }
        val lib = "dbghelp.dll\u0000".encodeToByteArray()
        return run {
            this.dll = LoadLibraryA(lib.asPtr())
            if (this.dll.isNull()) {
                Result.Err(Unit)
            } else {
                Result.Ok(Unit)
            }
        }
    }

    public open fun symGetOptions(): SymGetOptions? {
        return run {
            if (this.symGetOptions == 0) {
                val name = "SymGetOptions\u0000"
                this.symGetOptions = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymGetOptions>(this.symGetOptions)
        }
    }

    public open fun symSetOptions(): SymSetOptions? {
        return run {
            if (this.symSetOptions == 0) {
                val name = "SymSetOptions\u0000"
                this.symSetOptions = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymSetOptions>(this.symSetOptions)
        }
    }

    public open fun symInitializeW(): SymInitializeW? {
        return run {
            if (this.symInitializeW == 0) {
                val name = "SymInitializeW\u0000"
                this.symInitializeW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymInitializeW>(this.symInitializeW)
        }
    }

    public open fun symGetSearchPathW(): SymGetSearchPathW? {
        return run {
            if (this.symGetSearchPathW == 0) {
                val name = "SymGetSearchPathW\u0000"
                this.symGetSearchPathW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymGetSearchPathW>(this.symGetSearchPathW)
        }
    }

    public open fun symSetSearchPathW(): SymSetSearchPathW? {
        return run {
            if (this.symSetSearchPathW == 0) {
                val name = "SymSetSearchPathW\u0000"
                this.symSetSearchPathW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymSetSearchPathW>(this.symSetSearchPathW)
        }
    }

    public open fun enumerateLoadedModulesW64(): EnumerateLoadedModulesW64? {
        return run {
            if (this.enumerateLoadedModulesW64 == 0) {
                val name = "EnumerateLoadedModulesW64\u0000"
                this.enumerateLoadedModulesW64 = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, EnumerateLoadedModulesW64>(this.enumerateLoadedModulesW64)
        }
    }

    public open fun stackWalk64(): StackWalk64? {
        return run {
            if (this.stackWalk64 == 0) {
                val name = "StackWalk64\u0000"
                this.stackWalk64 = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, StackWalk64>(this.stackWalk64)
        }
    }

    public open fun symFunctionTableAccess64(): SymFunctionTableAccess64? {
        return run {
            if (this.symFunctionTableAccess64 == 0) {
                val name = "SymFunctionTableAccess64\u0000"
                this.symFunctionTableAccess64 = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymFunctionTableAccess64>(this.symFunctionTableAccess64)
        }
    }

    public open fun symGetModuleBase64(): SymGetModuleBase64? {
        return run {
            if (this.symGetModuleBase64 == 0) {
                val name = "SymGetModuleBase64\u0000"
                this.symGetModuleBase64 = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymGetModuleBase64>(this.symGetModuleBase64)
        }
    }

    public open fun symFromAddrW(): SymFromAddrW? {
        return run {
            if (this.symFromAddrW == 0) {
                val name = "SymFromAddrW\u0000"
                this.symFromAddrW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymFromAddrW>(this.symFromAddrW)
        }
    }

    public open fun symGetLineFromAddrW64(): SymGetLineFromAddrW64? {
        return run {
            if (this.symGetLineFromAddrW64 == 0) {
                val name = "SymGetLineFromAddrW64\u0000"
                this.symGetLineFromAddrW64 = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymGetLineFromAddrW64>(this.symGetLineFromAddrW64)
        }
    }

    public open fun stackWalkEx(): StackWalkEx? {
        return run {
            if (this.stackWalkEx == 0) {
                val name = "StackWalkEx\u0000"
                this.stackWalkEx = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, StackWalkEx>(this.stackWalkEx)
        }
    }

    public open fun symFromInlineContextW(): SymFromInlineContextW? {
        return run {
            if (this.symFromInlineContextW == 0) {
                val name = "SymFromInlineContextW\u0000"
                this.symFromInlineContextW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymFromInlineContextW>(this.symFromInlineContextW)
        }
    }

    public open fun symGetLineFromInlineContextW(): SymGetLineFromInlineContextW? {
        return run {
            if (this.symGetLineFromInlineContextW == 0) {
                val name = "SymGetLineFromInlineContextW\u0000"
                this.symGetLineFromInlineContextW = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymGetLineFromInlineContextW>(this.symGetLineFromInlineContextW)
        }
    }

    public open fun symAddrIncludeInlineTrace(): SymAddrIncludeInlineTrace? {
        return run {
            if (this.symAddrIncludeInlineTrace == 0) {
                val name = "SymAddrIncludeInlineTrace\u0000"
                this.symAddrIncludeInlineTrace = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymAddrIncludeInlineTrace>(this.symAddrIncludeInlineTrace)
        }
    }

    public open fun symQueryInlineTrace(): SymQueryInlineTrace? {
        return run {
            if (this.symQueryInlineTrace == 0) {
                val name = "SymQueryInlineTrace\u0000"
                this.symQueryInlineTrace = (this.symbol(name.encodeToByteArray().map { it.toUByte() }) ?: return null)
            }
            transmute<Int, SymQueryInlineTrace>(this.symQueryInlineTrace)
        }
    }

    public open fun symbol(symbol: List<UByte>): Int? = GetProcAddress(this.dll, symbol.asPtr()).map({ address -> address.toInt() })

    public companion object {
        public operator fun invoke(
            dll: Hinstance,
            symGetOptions: Int,
            symSetOptions: Int,
            symInitializeW: Int,
            symGetSearchPathW: Int,
            symSetSearchPathW: Int,
            enumerateLoadedModulesW64: Int,
            stackWalk64: Int,
            symFunctionTableAccess64: Int,
            symGetModuleBase64: Int,
            symFromAddrW: Int,
            symGetLineFromAddrW64: Int,
            stackWalkEx: Int,
            symFromInlineContextW: Int,
            symGetLineFromInlineContextW: Int,
            symAddrIncludeInlineTrace: Int,
            symQueryInlineTrace: Int,
        ): Dbghelp = Dbghelp(
            dllCtorArg = dll,
            symGetOptions = symGetOptions,
            symSetOptions = symSetOptions,
            symInitializeW = symInitializeW,
            symGetSearchPathW = symGetSearchPathW,
            symSetSearchPathW = symSetSearchPathW,
            enumerateLoadedModulesW64 = enumerateLoadedModulesW64,
            stackWalk64 = stackWalk64,
            symFunctionTableAccess64 = symFunctionTableAccess64,
            symGetModuleBase64 = symGetModuleBase64,
            symFromAddrW = symFromAddrW,
            symGetLineFromAddrW64 = symGetLineFromAddrW64,
            stackWalkEx = stackWalkEx,
            symFromInlineContextW = symFromInlineContextW,
            symGetLineFromInlineContextW = symGetLineFromInlineContextW,
            symAddrIncludeInlineTrace = symAddrIncludeInlineTrace,
            symQueryInlineTrace = symQueryInlineTrace,
        )
    }
}

public var DBGHELP: Dbghelp = Dbghelp(dll = MutPtr(0L), symGetOptions = 0, symSetOptions = 0, symInitializeW = 0, symGetSearchPathW = 0, symSetSearchPathW = 0, enumerateLoadedModulesW64 = 0, stackWalk64 = 0, symFunctionTableAccess64 = 0, symGetModuleBase64 = 0, symFromAddrW = 0, symGetLineFromAddrW64 = 0, stackWalkEx = 0, symFromInlineContextW = 0, symGetLineFromInlineContextW = 0, symAddrIncludeInlineTrace = 0, symQueryInlineTrace = 0)

public fun interface SymGetOptions {
    public operator fun invoke(): UInt
}

public fun interface SymSetOptions {
    public operator fun invoke(arg0: UInt): UInt
}

public fun interface SymInitializeW {
    public operator fun invoke(arg0: Handle, arg1: Pcwstr, arg2: Bool): Bool
}

public fun interface SymGetSearchPathW {
    public operator fun invoke(arg0: Handle, arg1: Pwstr, arg2: UInt): Bool
}

public fun interface SymSetSearchPathW {
    public operator fun invoke(arg0: Handle, arg1: Pcwstr): Bool
}

public fun interface EnumerateLoadedModulesW64 {
    public operator fun invoke(arg0: Handle, arg1: PenumloadedModulesCallbackw64, arg2: ConstPtr): Bool
}

public fun interface StackWalk64 {
    public operator fun invoke(arg0: UInt, arg1: Handle, arg2: Handle, arg3: MutPtr, arg4: MutPtr, arg5: PreadProcessMemoryRoutine64, arg6: PfunctionTableAccessRoutine64, arg7: PgetModuleBaseRoutine64, arg8: PtranslateAddressRoutine64): Bool
}

public fun interface SymFunctionTableAccess64 {
    public operator fun invoke(arg0: Handle, arg1: ULong): MutPtr
}

public fun interface SymGetModuleBase64 {
    public operator fun invoke(arg0: Handle, arg1: ULong): ULong
}

public fun interface SymFromAddrW {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: MutPtr, arg3: MutPtr): Bool
}

public fun interface SymGetLineFromAddrW64 {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: MutPtr, arg3: MutPtr): Bool
}

public fun interface StackWalkEx {
    public operator fun invoke(arg0: UInt, arg1: Handle, arg2: Handle, arg3: MutPtr, arg4: MutPtr, arg5: PreadProcessMemoryRoutine64, arg6: PfunctionTableAccessRoutine64, arg7: PgetModuleBaseRoutine64, arg8: PtranslateAddressRoutine64, arg9: UInt): Bool
}

public fun interface SymFromInlineContextW {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: MutPtr, arg4: MutPtr): Bool
}

public fun interface SymGetLineFromInlineContextW {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: ULong, arg4: MutPtr, arg5: MutPtr): Bool
}

public fun interface SymAddrIncludeInlineTrace {
    public operator fun invoke(arg0: Handle, arg1: ULong): UInt
}

public fun interface SymQueryInlineTrace {
    public operator fun invoke(arg0: Handle, arg1: ULong, arg2: UInt, arg3: ULong, arg4: ULong, arg5: MutPtr, arg6: MutPtr): Bool
}

public open class Init internal constructor(
    lockCtorArg: Handle,
) {
    public var lock: Handle = lockCtorArg
        internal set

    public open fun symGetOptions(): SymGetOptions = DBGHELP.symGetOptions()!!

    public open fun symSetOptions(): SymSetOptions = DBGHELP.symSetOptions()!!

    public open fun symInitializeW(): SymInitializeW = DBGHELP.symInitializeW()!!

    public open fun symGetSearchPathW(): SymGetSearchPathW = DBGHELP.symGetSearchPathW()!!

    public open fun symSetSearchPathW(): SymSetSearchPathW = DBGHELP.symSetSearchPathW()!!

    public open fun enumerateLoadedModulesW64(): EnumerateLoadedModulesW64 = DBGHELP.enumerateLoadedModulesW64()!!

    public open fun stackWalk64(): StackWalk64 = DBGHELP.stackWalk64()!!

    public open fun symFunctionTableAccess64(): SymFunctionTableAccess64 = DBGHELP.symFunctionTableAccess64()!!

    public open fun symGetModuleBase64(): SymGetModuleBase64 = DBGHELP.symGetModuleBase64()!!

    public open fun symFromAddrW(): SymFromAddrW = DBGHELP.symFromAddrW()!!

    public open fun symGetLineFromAddrW64(): SymGetLineFromAddrW64 = DBGHELP.symGetLineFromAddrW64()!!

    public open fun stackWalkEx(): StackWalkEx = DBGHELP.stackWalkEx()!!

    public open fun symFromInlineContextW(): SymFromInlineContextW = DBGHELP.symFromInlineContextW()!!

    public open fun symGetLineFromInlineContextW(): SymGetLineFromInlineContextW = DBGHELP.symGetLineFromInlineContextW()!!

    public open fun symAddrIncludeInlineTrace(): SymAddrIncludeInlineTrace = DBGHELP.symAddrIncludeInlineTrace()!!

    public open fun symQueryInlineTrace(): SymQueryInlineTrace = DBGHELP.symQueryInlineTrace()!!

    public open fun dbghelp(): MutPtr = addrOfMut(DBGHELP)

    public open fun drop() {
        run {
            val r = ReleaseMutex(this.lock)
            check(r != 0)
        }
    }

    public companion object {
        public operator fun invoke(
            lock: Handle,
        ): Init = Init(
            lockCtorArg = lock,
        )
    }
}

/**
 * Initialize all support necessary to access `dbghelp` API functions from this
 * crate.
 *
 * Note that this function is **safe**, it internally has its own
 * synchronization. Also note that it is safe to call this function multiple
 * times recursively.
 */
public fun init(): Result<Init, Unit> {
    // Helper function for generating a name that's unique to the process.
    fun mutexName(): UByteArray {
        val name: UByteArray  = "Local\\RustBacktraceMutex00000000\u0000".encodeToByteArray().toUByteArray()
        var id = GetCurrentProcessId()
        // Quick and dirty no alloc u32 to hex.
        var index = name.size - 1
        while (id > 0u) {
            name[index - 1] = when (val matchVal = (id and 0xFu).toUByte()) {
                in 0..9 -> {
                    val h = matchVal
                    48u + h
                }
                else -> {
                    val h1 = matchVal
                    65u + (h1 - 10u)
                }
            }
            id = id shr 4
            index -= 1
        }
        return name
    }
    return run {
        // First thing we need to do is to synchronize this function. This can
        // be called concurrently from other threads or recursively within one
        // thread. Note that it's trickier than that though because what we're
        // using here, `dbghelp`, *also* needs to be synchronized with all other
        // callers to `dbghelp` in this process.
        //
        // Typically there aren't really that many calls to `dbghelp` within the
        // same process and we can probably safely assume that we're the only
        // ones accessing it. There is, however, one primary other user we have
        // to worry about which is ironically ourselves, but in the standard
        // library. The Rust standard library depends on this crate for
        // backtrace support, and this crate also exists on crates.io. This
        // means that if the standard library is printing a panic backtrace it
        // may race with this crate coming from crates.io, causing segfaults.
        //
        // To help solve this synchronization problem we employ a
        // Windows-specific trick here (it is, after all, a Windows-specific
        // restriction about synchronization). We create a *session-local* named
        // mutex to protect this call. The intention here is that the standard
        // library and this crate don't have to share Rust-level APIs to
        // synchronize here but can instead work behind the scenes to make sure
        // they're synchronizing with one another. That way when this function
        // is called through the standard library or through crates.io we can be
        // sure that the same mutex is being acquired.
        //
        // So all of that is to say that the first thing we do here is we
        // atomically create a `HANDLE` which is a named mutex on Windows. We
        // synchronize a bit with other threads sharing this function
        // specifically and ensure that only one handle is created per instance
        // of this function. Note that the handle is never closed once it's
        // stored in the global.
        //
        // After we've actually go the lock we simply acquire it, and our `Init`
        // handle we hand out will be responsible for dropping it eventually.
        val LOCK: AtomicPtr  = AtomicPtr.new(MutPtr(0L))
        var INITIALIZED: Boolean = false
        var lock = LOCK.load(SeqCst)
        if (lock.isNull()) {
            val name = mutexName()
            lock = CreateMutexA(MutPtr(0L), FALSE, name.asPtr())
            if (lock.isNull()) {
                return Result.Err(Unit)
            }
            when (val matchVal = LOCK.compareExchange(MutPtr(0L), lock, SeqCst, SeqCst)) {
                is Err -> {
                    val other = matchVal.error
                    check(!other.isNull())
                    CloseHandle(lock)
                    lock = other
                }
                else -> {}
            }
        }
        check(!lock.isNull())
        val r = WaitForSingleObjectEx(lock, INFINITE, FALSE)
        check(r == 0u)
        val ret = Init(lock = lock)
        // Ok, phew! Now that we're all safely synchronized, let's actually
        // start processing everything. First up we need to ensure that
        // `dbghelp.dll` is actually loaded in this process. We do this
        // dynamically to avoid a static dependency. This has historically been
        // done to work around weird linking issues and is intended at making
        // binaries a bit more portable since this is largely just a debugging
        // utility.
        //
        // Once we've opened `dbghelp.dll` we need to call some initialization
        // functions in it, and that's detailed more below. We only do this
        // once, though, so we've got a global boolean indicating whether we're
        // done yet or not.
        // Note: https://github.com/rust-lang/backtrace-rs/issues/678
        when (val tryResult = DBGHELP.ensureOpen()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        if (INITIALIZED.inv()) {
            setOptionalOptions(ret.dbghelp())
            INITIALIZED = true
        }
        Result.Ok(ret)
    }
}

public fun setOptionalOptions(dbghelp: MutPtr): Unit? {
    run {
        val orig = ((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).symGetOptions() ?: return null)()
        // Ensure that the `SYMOPT_DEFERRED_LOADS` flag is set, because
        // according to MSVC's own docs about this: "This is the fastest, most
        // efficient way to use the symbol handler.", so let's do that!
        ((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).symSetOptions() ?: return null)(orig or SYMOPT_DEFERRED_LOADS)
        // Actually initialize symbols with MSVC. Note that this can fail, but we
        // ignore it. There's not a ton of prior art for this per se, but LLVM
        // internally seems to ignore the return value here and one of the
        // sanitizer libraries in LLVM prints a scary warning if this fails but
        // basically ignores it in the long run.
        //
        // One case this comes up a lot for Rust is that the standard library and
        // this crate on crates.io both want to compete for `SymInitializeW`. The
        // standard library historically wanted to initialize then cleanup most of
        // the time, but now that it's using this crate it means that someone will
        // get to initialization first and the other will pick up that
        // initialization.
        ((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).symInitializeW() ?: return null)(GetCurrentProcess(), MutPtr(0L), TRUE)
        // The default search path for dbghelp will only look in the current working
        // directory and (possibly) `_NT_SYMBOL_PATH` and `_NT_ALT_SYMBOL_PATH`.
        // However, we also want to look in the directory of the executable
        // and each DLL that is loaded. To do this, we need to update the search path
        // to include these directories.
        //
        // See https://learn.microsoft.com/cpp/build/reference/pdbpath for an
        // example of where symbols are usually searched for.
        val searchPathBuf = mutableListOf<UShort>()
        searchPathBuf.resize(1024, 0)
        // Prefill the buffer with the current search path.
        if (((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).symGetSearchPathW() ?: return null)(GetCurrentProcess(), searchPathBuf.asMutPtr(), null) == TRUE) {
            // Trim the buffer to the actual length of the string.
            val len = lstrlenW(searchPathBuf.asMutPtr())
            check(len >= 0)
            searchPathBuf.truncate(len)
        } else {
            // If getting the search path fails, at least include the current directory.
            searchPathBuf.clear()
            searchPathBuf.add(utf16Char('.'))
            searchPathBuf.add(utf16Char(';'))
        }
        val localStorageL374C8 = AddressableLocal(SearchPath.new(searchPathBuf), ObjectSlotLayout<SearchPath>())
        val searchPath by localStorageL374C8
        // Update the search path to include the directory of the executable and each DLL.
        ((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).enumerateLoadedModulesW64() ?: return null)(GetCurrentProcess(), ::enumLoadedModulesCallback, ((MutPtr(localStorageL374C8.asMutPtr().rawAddress))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
        val newSearchPath = searchPath.finalize()
        // Set the new search path.
        ((ObjectSlotLayout<Dbghelp>()).readNative((dbghelp).rawAddress).symSetSearchPathW() ?: return null)(GetCurrentProcess(), newSearchPath.asPtr())
    }
    return Unit
}

public open class SearchPath(
    public var searchPathUtf16: MutableList<UShort>,
) {
    /**
     * Add a path to the search path if it is not already present.
     */
    public open fun add(path: List<UShort>) {
        val sep = utf16Char(';')
        // We could deduplicate in a case-insensitive way, but case-sensitivity
        // can be configured by directory on Windows, so let's not do that.
        // https://learn.microsoft.com/windows/wsl/case-sensitivity
        if (!this.searchPathUtf16.split({ c -> c == sep }).any({ p -> p == path })) {
            if (this.searchPathUtf16.last() != sep) {
                this.searchPathUtf16.add(sep)
            }
            this.searchPathUtf16.addAll(path)
        }
    }

    public open fun finalize(): List<UShort> {
        // Add a null terminator.
        this.searchPathUtf16.add(0)
        return this.searchPathUtf16
    }

    public companion object {
        public fun new(initialSearchPath: List<UShort>): SearchPath = SearchPath(searchPathUtf16 = initialSearchPath)
    }
}

public fun utf16Char(c: Char): UShort {
    val buf = UShortArray(2) { 0u.toUShort() }
    val buf1 = c.encodeUtf16(buf)
    check(buf1.len() == 1)
    return buf1[0]
}

public fun enumLoadedModulesCallback(moduleName: Pcwstr, arg1: ULong, arg2: UInt, userContext: ConstPtr): Bool {
    // `moduleName` is an absolute path like `C:\path\to\module.dll`
    // or `C:\path\to\module.exe`
    val len: Int = lstrlenW(moduleName).tryInto().unwrap()
    if (len == 0) {
        // This should not happen, but if it does, we can just ignore it.
        return TRUE
    }
    val moduleName1 = rawSliceList(moduleName, len.toInt(), PrimitiveLayouts.U8)
    val pathSep = utf16Char('\\')
    val altPathSep = utf16Char('/')
    val endOfDirectory = moduleName1.iter().indexOfLast({ c -> c == pathSep || c == altPathSep }).takeIf { it >= 0 } ?: // `moduleName` being an absolute path, it should always contain at least one
// path separator. If not, there is nothing we can do.
return TRUE
    val searchPath = RustMutableReference(MutPtr(((MutPtr(userContext.rawAddress, userContext.rawMeta))).rawAddress), ObjectSlotLayout<SearchPath>())
    searchPath.add(moduleName1[0 until endOfDirectory])
    return TRUE
}

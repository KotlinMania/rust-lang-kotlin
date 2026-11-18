// port-lint: source sys/pal/windows/c.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.shgetknownfolderpath

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CALL_NOT_IMPLEMENTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SHELL32
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.E_NOTIMPL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Handle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Hresult
import io.github.kotlinmania.rustlang.std.sys.pal.windows.compat.LazyModule
import io.github.kotlinmania.rustlang.std.sys.pal.windows.compat.Module
import io.github.kotlinmania.rustlang.std.sys.pal.windows.compat.constCstrFromBytes

// Functions that aren't available on every version of Windows that we support,
// but we still use them and just provide some form of a fallback implementation.
// Functions in DLLs that we want to lazily load
public fun interface F {
    public operator fun invoke(arg0: ConstPtr, arg1: UInt, arg2: Handle, arg3: MutPtr): Hresult
}

/**
 * `PTR` contains a function pointer to one of three functions.
 * It starts with the `load` function.
 * When that is called it attempts to load the requested symbol.
 * If it succeeds, `PTR` is set to the address of that symbol.
 * If it fails, then `PTR` is set to `fallback`.
 */
public var PTR: Atomic<MutPtr, Align8<MutPtr>> = AtomicPtr.new(mutPtrFromRustCast(((::load as Any?) as F)))

public fun load(rfid: ConstPtr, dwflags: UInt, htoken: Handle, ppszpath: MutPtr): Hresult {
    val MODULE: LazyModule  = LazyModule.new(SHELL32)
    return run {
        val func = loadFromModule(MODULE.load())
        func(rfid, dwflags, htoken, ppszpath)
    }
}

public fun loadFromModule(module: Module?): F =
    run {
        val SYMBOL_NAME: CStr  = constCstrFromBytes(("SHGetKnownFolderPath\u0000").encodeToByteArray().map { it.toUByte() })
        when (val matchVal = module?.let({ m -> m.procAddress(SYMBOL_NAME) })) {
            null -> {
                PTR.store(mutPtrFromRustCast(((::fallback as Any?) as F)), Ordering.Relaxed)
                ::fallback
            }
            else -> {
                val f = matchVal
                PTR.store(f.asPtr(), Ordering.Relaxed)
                transmuteWithLayouts<Any?, F>(f, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<F>())
            }
        }
    }

public fun fallback(rfid: ConstPtr, dwflags: UInt, htoken: Handle, ppszpath: MutPtr): Hresult =
    run {
        SetLastError(ERROR_CALL_NOT_IMPLEMENTED)
        E_NOTIMPL
    }

public fun call(rfid: ConstPtr, dwflags: UInt, htoken: Handle, ppszpath: MutPtr): Hresult =
    run {
        val func: F = transmuteWithLayouts<Any?, F>(PTR.load(Ordering.Relaxed), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<F>())
        func(rfid, dwflags, htoken, ppszpath)
    }

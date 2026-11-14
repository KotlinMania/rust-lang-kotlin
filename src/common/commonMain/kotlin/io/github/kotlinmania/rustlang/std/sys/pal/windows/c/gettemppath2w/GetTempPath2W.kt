// port-lint: source sys/pal/windows/c.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.gettemppath2w

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.KERNEL32
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Pwstr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.compat.Module
import io.github.kotlinmania.rustlang.std.sys.pal.windows.compat.constCstrFromBytes

// Functions that aren't available on every version of Windows that we support,
// but we still use them and just provide some form of a fallback implementation.
// Functions in DLLs that we want to lazily load
public fun interface F {
    public operator fun invoke(arg0: UInt, arg1: Pwstr): UInt
}

/**
 * `PTR` contains a function pointer to one of three functions.
 * It starts with the `load` function.
 * When that is called it attempts to load the requested symbol.
 * If it succeeds, `PTR` is set to the address of that symbol.
 * If it fails, then `PTR` is set to `fallback`.
 */
public var PTR: Atomic<MutPtr> = AtomicPtr.new(mutPtrFromRustCast(((::load as Any?) as F)))

public fun load(bufferlength: UInt, buffer: Pwstr): UInt =
    run {
        val func = loadFromModule(Module.new(KERNEL32))
        func(bufferlength, buffer)
    }

public fun loadFromModule(module: Module?): F =
    run {
        val SYMBOL_NAME: CStr  = constCstrFromBytes(("GetTempPath2W\u0000").encodeToByteArray().map { it.toUByte() })
        when (val matchVal = module?.let({ m -> m.procAddress(SYMBOL_NAME) })) {
            null -> {
                PTR.store(mutPtrFromRustCast(((::fallback as Any?) as F)), Ordering.Relaxed)
                ::fallback
            }
            else -> {
                val f = matchVal
                PTR.store(f.asPtr(), Ordering.Relaxed)
                transmute(f)
            }
        }
    }

public fun fallback(bufferlength: UInt, buffer: Pwstr): UInt = GetTempPathW(bufferlength, buffer)

public fun call(bufferlength: UInt, buffer: Pwstr): UInt =
    run {
        val func: F = transmute(PTR.load(Ordering.Relaxed))
        func(bufferlength, buffer)
    }

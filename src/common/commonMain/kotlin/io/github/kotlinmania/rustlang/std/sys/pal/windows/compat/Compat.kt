// port-lint: source sys/pal/windows/compat.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.compat

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.std.sync.lazylock.LazyLock
import io.github.kotlinmania.rustlang.std.sys.c

// A "compatibility layer" for supporting older versions of Windows
//
// The standard library uses some Windows API functions that are not present
// on older versions of Windows.  (Note that the oldest version of Windows
// that Rust supports is Windows 7 (client) and Windows Server 2008 (server).)
// This module implements a form of delayed DLL import binding, using
// `GetModuleHandle` and `GetProcAddress` to look up DLL entry points at
// runtime.
//
// This is implemented simply by storing a function pointer in an atomic.
// Loading and calling this function will have little or no overhead
// compared with calling any other dynamically imported function.
//
// The stored function pointer starts out as an importer function which will
// swap itself with the real function when it's called for the first time. If
// the real function can't be imported then a fallback function is used in its
// place. While this is low cost for the happy path (where the function is
// already loaded) it does mean there's some overhead the first time the
// function is called. In the worst case, multiple threads may all end up
// importing the same function unnecessarily.
//
// Helper macro for creating CStrs from literals and symbol names.
//
// Load a function or use a fallback implementation if that fails.

/**
 * Creates a C string wrapper from a byte slice, in a constant context.
 *
 * This is a utility function used by the [ansiStr] macro.
 *
 * # Panics
 *
 * Panics if the slice is not null terminated or contains nulls, except as the last item
 */
public fun constCstrFromBytes(bytes: List<UByte>): CStr {
    if (!when (val matchVal = bytes.last()) {
        0 -> true
        else -> false
    }) {
        error("A CStr must be null terminated")
    }
    var i = 0
    // At this point `len()` is at least 1.
    while (i < bytes.size - 1) {
        if (bytes[i] == 0u.toUByte()) {
            error("A CStr must not have interior nulls")
        }
        i += 1
    }
    // SAFETY: The safety is ensured by the above checks.
    return CStr.fromBytesWithNulUnchecked(bytes)
}

/**
 * Represents a loaded module.
 *
 * Note that the modules std depends on must not be unloaded.
 * Therefore a `Module` is always valid for the scope of std.
 */
public open class Module internal constructor(
    valueCtorArg: NonNull,
) {
    public var value: NonNull = valueCtorArg
        internal set

    public var field0: NonNull
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Module = Module(value)

    // Try to get the address of a function.
    public open fun procAddress(name: CStr): NonNull? =
        run {
            // SAFETY:
            // `self.0` will always be a valid module.
            // A CStr is always null terminated.
            val proc = c.GetProcAddress(this.value.asPtr(), name.asPtr().cast())
            // SAFETY: `GetProcAddress` returns None on null.
            proc.map({ p -> NonNull.newUnchecked(mutPtrFromRustCast(p)) })
        }

    public companion object {
        public operator fun invoke(
            value: NonNull,
        ): Module = Module(
            valueCtorArg = value,
        )

        /**
         * Try to get a handle to a loaded module.
         *
         * # SAFETY
         *
         * This should only be use for modules that exist for the scope of std
         * (e.g. kernel32 and ntdll).
         */
        public fun new(name: CStr): Module? {
            // SAFETY: A CStr is always null terminated.
            return run {
                val module = c.GetModuleHandleA(name.asPtr().cast())
                NonNull.new(module).map({ Module(it) })
            }
        }

        /**
         * Try to load a module handle.
         *
         * # SAFETY
         *
         * This should only be use for modules that exist for the scope of std.
         */
        public fun load(name: CStr): Module? {
            // SAFETY: A CStr is always null terminated.
            return run {
                val module = c.LoadLibraryExA(name.asPtr().cast(), MutPtr(0L), c.LOAD_LIBRARY_SEARCH_SYSTEM32)
                NonNull.new(module).map({ Module(it) })
            }
        }
    }
}

public typealias LazyLoadModule = () -> Module?

public fun lazyLoadModule(name: CStr): LazyLoadModule = { Module.load(name) }

/**
 * Represents a loaded module.
 *
 * Note that the modules std depends on must not be unloaded.
 * Therefore a `Module` is always valid for the scope of std.
 */
public open class LazyModule internal constructor(
    valueCtorArg: LazyLock<Module?, LazyLoadModule>,
) {
    public var value: LazyLock<Module?, LazyLoadModule> = valueCtorArg
        internal set

    public var field0: LazyLock<Module?, LazyLoadModule>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Get the module handle, loading it if necessary.
     */
    public open fun load(): Module? = LazyLock.force(this.value)

    public companion object {
        public operator fun invoke(
            value: LazyLock<Module?, LazyLoadModule>,
        ): LazyModule = LazyModule(
            valueCtorArg = value,
        )

        /**
         *  Create a lazy-loaded handle to a module.
         */
        public fun new(name: CStr): LazyModule = LazyModule(LazyLock.new(lazyLoadModule(name)))
    }
}

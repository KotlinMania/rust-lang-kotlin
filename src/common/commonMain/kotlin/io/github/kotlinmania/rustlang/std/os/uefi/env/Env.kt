// port-lint: source os/uefi/env.rs
package io.github.kotlinmania.rustlang.std.os.uefi.env

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// UEFI-specific extensions to the primitives in `std.env` module

public var SYSTEM_TABLE: Atomic<MutPtr> = AtomicPtr.new(MutPtr(0L))

public var IMAGE_HANDLE: Atomic<MutPtr> = AtomicPtr.new(MutPtr(0L))

// Flag to check if BootServices are still valid.
// Start with assuming that they are not available
public var BOOT_SERVICES_FLAG: Atomic<Boolean> = AtomicBool.new(false)

/**
 * Initializes the global System Table and Image Handle pointers.
 *
 * The standard library requires access to the UEFI System Table and the Application Image Handle
 * to operate. Those are provided to UEFI Applications via their application entry point. By
 * calling `initGlobals()`, those pointers are retained by the standard library for future use.
 * Thus this function must be called before any of the standard library services are used.
 *
 * The pointers are never exposed to any entity outside of this application and it is guaranteed
 * that, once the application exited, these pointers are never dereferenced again.
 *
 * Callers are required to ensure the pointers are valid for the entire scope of this
 * application. In particular, UEFI Boot Services must not be exited while an application with the
 * standard library is loaded.
 *
 * # SAFETY
 * Calling this function more than once will panic.
 */
internal fun initGlobals(handle: NonNull, systemTable: NonNull) {
    IMAGE_HANDLE.compareExchange(MutPtr(0L), handle.asPtr(), Ordering.Release, Ordering.Acquire).unwrap()
    SYSTEM_TABLE.compareExchange(MutPtr(0L), systemTable.asPtr(), Ordering.Release, Ordering.Acquire).unwrap()
    BOOT_SERVICES_FLAG.store(true, Ordering.Release)
}

/**
 * Gets the SystemTable Pointer.
 *
 * If you want to use `BootServices` then please use [bootServices] as it performs some
 * additional checks.
 *
 * Note: This function panics if the System Table or Image Handle is not initialized.
 */
public fun systemTable(): NonNull = trySystemTable()!!

/**
 * Gets the ImageHandle Pointer.
 *
 * Note: This function panics if the System Table or Image Handle is not initialized.
 */
public fun imageHandle(): NonNull = tryImageHandle()!!

/**
 * Gets the BootServices Pointer.
 *
 * This function also checks if `ExitBootServices` has already been called.
 */
public fun bootServices(): NonNull? {
    return if (BOOT_SERVICES_FLAG.load(Ordering.Acquire)) {
        val systemTable: NonNull = (trySystemTable() ?: return null).cast()
        val bootServices = systemTable.asPtr().bootServices
        NonNull.new(bootServices).map({ x -> x.cast() })
    } else {
        null
    }
}

/**
 * Gets the SystemTable Pointer.
 *
 * This function is mostly intended for places where panic is not an option.
 */
internal fun trySystemTable(): NonNull? = NonNull.new(SYSTEM_TABLE.load(Ordering.Acquire))

/**
 * Gets the SystemHandle Pointer.
 *
 * This function is mostly intended for places where panicking is not an option.
 */
internal fun tryImageHandle(): NonNull? = NonNull.new(IMAGE_HANDLE.load(Ordering.Acquire))

internal fun disableBootServices() {
    BOOT_SERVICES_FLAG.store(false, Ordering.Release)
}

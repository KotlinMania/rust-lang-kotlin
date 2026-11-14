// port-lint: source os/fortanix_sgx/arch.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.fortanixsgx.arch

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

// SGX-specific access to architectural features.
//
// The functionality in this module is further documented in the Intel
// Software Developer's Manual, Volume 3, Chapter 40.

/**
 * Wrapper struct to force 16-byte alignment.
 */
public open class Align16<T>(
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
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<Align16<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align16<T>(ptr, layout) }, { it.value })
}

/**
 * Wrapper struct to force 128-byte alignment.
 */
public open class Align128<T>(
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
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 128L)), AlignedTypeLayout(fieldLayout, 128L))

    public fun pointerLayout(): TypeLayout<Align128<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align128<T>(ptr, layout) }, { it.value })
}

/**
 * Wrapper struct to force 512-byte alignment.
 */
public open class Align512<T>(
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
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 512L)), AlignedTypeLayout(fieldLayout, 512L))

    public fun pointerLayout(): TypeLayout<Align512<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align512<T>(ptr, layout) }, { it.value })
}

public val ENCLU_EREPORT: UInt = 0u

public val ENCLU_EGETKEY: UInt = 1u

/**
 * Call the `EGETKEY` instruction to obtain a 128-bit secret key.
 */
public fun egetkey(request: Align512<UByteArray>): Result<Align16<UByteArray>, UInt> =
    run {
        val out = MaybeUninit.uninit(ObjectSlotLayout<Result<Align16<UByteArray>, UInt>>())
        val error: Any?
        asm("xchg %rbx, {0}", "enclu", "mov {0}, %rbx", inout, reg, request, inlateout, "eax", ENCLU_EGETKEY, error, "rcx", out, asMutPtr, options(attSyntax, nostack))
        when (error) {
            0 -> Result.Ok(out.assumeInit())
            else -> {
                val err = error
                Result.Err(err)
            }
        }
    }

/**
 * Call the `EREPORT` instruction.
 *
 * This creates a cryptographic report describing the contents of the current
 * enclave. The report may be verified by the enclave described in
 * `targetinfo`.
 */
public fun ereport(targetinfo: Align512<UByteArray>, reportdata: Align128<UByteArray>): Align512<UByteArray> =
    run {
        val report = MaybeUninit.uninit(ObjectSlotLayout<Align512<UByteArray>>())
        asm("xchg %rbx, {0}", "enclu", "mov {0}, %rbx", inout, reg, targetinfo, "eax", ENCLU_EREPORT, "rcx", reportdata, "rdx", report, asMutPtr, options(attSyntax, preservesFlags, nostack))
        report.assumeInit()
    }

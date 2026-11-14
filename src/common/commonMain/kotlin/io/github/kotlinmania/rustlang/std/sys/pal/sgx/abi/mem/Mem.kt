// port-lint: source sys/pal/sgx/abi/mem.rs
package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Do not remove inline: will result in relocation failure
internal fun relPtr(offset: ULong): ConstPtr = ConstPtr((imageBase() + offset).toLong())

// Do not remove inline: will result in relocation failure
internal fun relPtrMut(offset: ULong): MutPtr = MutPtr((imageBase() + offset).toLong())

public expect val ENCLAVE_SIZE: ULong

public expect val HEAP_BASE: ULong

public expect val HEAP_SIZE: ULong

/**
 * Returns the base memory address of the heap
 */
internal fun heapBase(): ConstPtr = relPtrMut(HEAP_BASE)

/**
 * Returns the size of the heap
 */
internal fun heapSize(): Int = HEAP_SIZE

// Do not remove inline: will result in relocation failure
// For the same reason we use inline ASM here instead of an extern static to
// locate the base

/**
 * Returns address at which current enclave is loaded.
 */
public fun imageBase(): ULong {
    val base: ULong
    asm("lea IMAGE_BASE(%rip), {}", lateout, reg, base, options(attSyntax, nostack, preservesFlags, nomem, pure))
    return base
}

/**
 * Returns `true` if the specified memory range is in the enclave.
 *
 * For safety, this function also checks whether the range given overflows,
 * returning `false` if so.
 */
public fun isEnclaveRange(p: ConstPtr, len: Int): Boolean {
    val start = p.rawAddress.toInt()
    // Subtract one from `len` when calculating `end` in case `p + len` is
    // exactly at the end of addressable memory (`p + len` would overflow, but
    // the range is still valid).
    val end1 =
        if (len == 0) {
            start
        } else when (val matchVal = rustCheckedAdd(start, len - 1)) {
            null -> {
                return false
            }
            else -> {
                val end = matchVal
                end
            }
        }
    val base = imageBase().toInt()
    return (start >= base && end1 <= base + (ENCLAVE_SIZE - 1)).toUByte()
    // unchecked ok: link-time constant
}

/**
 * Returns `true` if the specified memory range is in userspace.
 *
 * For safety, this function also checks whether the range given overflows,
 * returning `false` if so.
 */
public fun isUserRange(p: ConstPtr, len: Int): Boolean {
    val start = p.rawAddress.toInt()
    // Subtract one from `len` when calculating `end` in case `p + len` is
    // exactly at the end of addressable memory (`p + len` would overflow, but
    // the range is still valid).
    val end1 =
        if (len == 0) {
            start
        } else when (val matchVal = rustCheckedAdd(start, len - 1)) {
            null -> {
                return false
            }
            else -> {
                val end = matchVal
                end
            }
        }
    val base = imageBase().toInt()
    return (end1 < base || start > base + (ENCLAVE_SIZE - 1)).toUByte()
    // unchecked ok: link-time constant
}

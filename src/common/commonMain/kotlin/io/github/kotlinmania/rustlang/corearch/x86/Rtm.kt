// port-lint: source x86/rtm.rs
package io.github.kotlinmania.rustlang.corearch.x86

import kotlin.experimental.and
import kotlin.experimental.inv

// Intel's Restricted Transactional Memory (RTM).
//
// This CPU feature is available on Intel Broadwell or later CPUs (and some Haswell).
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [Wikipedia][wikipediaRtm] provides a quick overview of the assembly instructions, and
// Intel's [programming considerations][intelConsid] details what sorts of instructions within a
// transaction are likely to cause an abort.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [wikipediaRtm]: https://en.wikipedia.org/wiki/TransactionalSynchronizationExtensions#RestrictedTransactionalMemory
// [intelConsid]: https://software.intel.com/en-us/cpp-compiler-developer-guide-and-reference-intel-transactional-synchronization-extensions-intel-tsx-programming-considerations

public expect fun x86Xbegin(): Int

public expect fun x86Xend()

public expect fun x86Xabort(imm8: Byte)

public expect fun x86Xtest(): Int

/**
 * Transaction successfully started.
 */
public val XBEGIN_STARTED: UInt = 0u.inv()

/**
 * Transaction explicitly aborted with xabort. The parameter passed to xabort is available with
 * `_xabort_code(status)`.
 */
public val XABORT_EXPLICIT: UInt = 1u shl 0

/**
 * Transaction retry is possible.
 */
public val XABORT_RETRY: UInt = 1u shl 1

/**
 * Transaction abort due to a memory conflict with another thread.
 */
public val XABORT_CONFLICT: UInt = 1u shl 2

/**
 * Transaction abort due to the transaction using too much memory.
 */
public val XABORT_CAPACITY: UInt = 1u shl 3

/**
 * Transaction abort due to a debug trap.
 */
public val XABORT_DEBUG: UInt = 1u shl 4

/**
 * Transaction abort in a inner nested transaction.
 */
public val XABORT_NESTED: UInt = 1u shl 5

/**
 * Specifies the start of a restricted transactional memory (RTM) code region and returns a value
 * indicating status.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_xbegin)
 */
public fun xbegin(): UInt = x86Xbegin().toUInt()

/**
 * Specifies the end of a restricted transactional memory (RTM) code region.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_xend)
 */
public fun xend() {
    x86Xend()
}

/**
 * Forces a restricted transactional memory (RTM) region to abort.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_xabort)
 */
public fun xabort(imm8: UInt) {
    run {
        check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    x86Xabort(imm8.toByte())
}

/**
 * Queries whether the processor is executing in a transactional region identified by restricted
 * transactional memory (RTM) or hardware lock elision (HLE).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_xtest)
 */
public fun xtest(): UByte = x86Xtest().toUByte()

/**
 * Retrieves the parameter passed to [xabort] when [xbegin]s status has the
 * `_XABORT_EXPLICIT` flag set.
 */
public fun xabortCode(status: UInt): UInt = (status shr 24) and 0xFFu

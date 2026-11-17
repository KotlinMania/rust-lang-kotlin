// port-lint: source x86/rdtsc.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// RDTSC instructions.

/**
 * Reads the current value of the processor’s time-stamp counter.
 *
 * The processor monotonically increments the time-stamp counter MSR
 * every clock cycle and resets it to 0 whenever the processor is
 * reset.
 *
 * The RDTSC instruction is not a serializing instruction. It does
 * not necessarily wait until all previous instructions have been
 * executed before reading the counter. Similarly, subsequent
 * instructions may begin execution before the read operation is
 * performed.
 *
 * On processors that support the Intel 64 architecture, the
 * high-order 32 bits of each of RAX and RDX are cleared.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdtsc)
 */
public fun rdtsc(): ULong = externRdtsc()

/**
 * Reads the current value of the processor’s time-stamp counter and
 * the `IA32_TSC_AUX MSR`.
 *
 * The processor monotonically increments the time-stamp counter MSR
 * every clock cycle and resets it to 0 whenever the processor is
 * reset.
 *
 * The RDTSCP instruction waits until all previous instructions have
 * been executed before reading the counter. However, subsequent
 * instructions may begin execution before the read operation is
 * performed.
 *
 * On processors that support the Intel 64 architecture, the
 * high-order 32 bits of each of RAX, RDX, and RCX are cleared.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__rdtscp)
 */
public fun rdtscp(aux: MutPtr): ULong {
    val aux1 = aux
    val (tsc, auxval) = rdtscp()
    (PrimitiveLayouts.U32).writeNative((aux1).rawAddress, auxval)
    return tsc
}

public expect fun externRdtsc(): ULong

public expect fun rdtscp(): Pair<ULong, UInt>

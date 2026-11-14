// port-lint: source arm_shared/hints.rs
package io.github.kotlinmania.rustlang.corearch.armshared

import io.github.kotlinmania.rustlang.corearch.arch.asm

// # References
//
// - Section 7.4 "Hints" of ACLE
// - Section 7.7 "NOP" of ACLE
// Section 10.1 of ACLE says that the supported arches are: 8, 6K, 6-M
// LLVM says "instruction requires: armv6k"

/**
 * Generates a WFI (wait for interrupt) hint instruction, or nothing.
 *
 * The WFI instruction allows (but does not require) the processor to enter a
 * low-power state until one of a number of asynchronous events occurs.
 */
public fun wfi() {
    // SAFETY: The right HINT_WFI constant is passed to the llvm intrinsic。
    run {
        hint(HINT_WFI)
    }
}

// Section 10.1 of ACLE says that the supported arches are: 8, 6K, 6-M
// LLVM says "instruction requires: armv6k"

/**
 * Generates a WFE (wait for event) hint instruction, or nothing.
 *
 * The WFE instruction allows (but does not require) the processor to enter a
 * low-power state until some event occurs such as a SEV being issued by
 * another processor.
 */
public fun wfe() {
    // SAFETY: The right HINT_WFE constant is passed to the llvm intrinsic。
    run {
        hint(HINT_WFE)
    }
}

// Section 10.1 of ACLE says that the supported arches are: 8, 6K, 6-M, 7-M
// LLVM says "instruction requires: armv6k"

/**
 * Generates a SEV (send a global event) hint instruction.
 *
 * This causes an event to be signaled to all processors in a multiprocessor
 * system. It is a NOP on a uniprocessor system.
 */
public fun sev() {
    // SAFETY: The right HINT_SEV constant is passed to the llvm intrinsic。
    run {
        hint(HINT_SEV)
    }
}

// LLVM says "instruction requires: armv8"

/**
 * Generates a send a local event hint instruction.
 *
 * This causes an event to be signaled to only the processor executing this
 * instruction. In a multiprocessor system, it is not required to affect the
 * other processors.
 */
public fun sevl() {
    // SAFETY: The right HINT_SEVL constant is passed to the llvm intrinsic。
    run {
        hint(HINT_SEVL)
    }
}

// Section 10.1 of ACLE says that the supported arches are: 8, 6K, 6-M
// LLVM says "instruction requires: armv6k"
// On ARMv6 in Thumb mode, T2 is required (see Arm DDI0406C Section A8.8.427)

/**
 * Generates a YIELD hint instruction.
 *
 * This enables multithreading software to indicate to the hardware that it is
 * performing a task, for example a spin-lock, that could be swapped out to
 * improve overall system performance.
 */
public fun yield() {
    // SAFETY: The right HINT_YIELD constant is passed to the llvm intrinsic。
    run {
        hint(HINT_YIELD)
    }
}

/**
 * Generates an unspecified no-op instruction.
 *
 * Note that not all architectures provide a distinguished NOP instruction. On
 * those that do, it is unspecified whether this intrinsic generates it or
 * another instruction. It is not guaranteed that inserting this instruction
 * will increase execution time.
 */
public fun nop() {
    // SAFETY: `nop` performs no operation.
    run {
        asm("nop", options(nomem, nostack, preservesFlags))
    }
}

public expect fun hint(arg0: Int)

// from LLVM 7.0.1's lib/Target/ARM/{ARMInstrThumb,ARMInstrInfo,ARMInstrThumb2}.td
public val HINT_NOP: Int = 0

public val HINT_YIELD: Int = 1

public val HINT_WFE: Int = 2

public val HINT_WFI: Int = 3

public val HINT_SEV: Int = 4

public val HINT_SEVL: Int = 5

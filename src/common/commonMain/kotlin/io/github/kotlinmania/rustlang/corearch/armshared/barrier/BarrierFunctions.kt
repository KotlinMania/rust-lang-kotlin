package io.github.kotlinmania.rustlang.corearch.armshared.barrier

import io.github.kotlinmania.rustlang.armshared.Dmb
import io.github.kotlinmania.rustlang.armshared.Dsb
import io.github.kotlinmania.rustlang.armshared.Isb
import io.github.kotlinmania.rustlang.dmb as underlyingDmb
import io.github.kotlinmania.rustlang.dsb as underlyingDsb
import io.github.kotlinmania.rustlang.isb as underlyingIsb

/**
 * Generates a DMB (data memory barrier) instruction or equivalent CP15 instruction.
 *
 * DMB ensures the observed ordering of memory accesses. Memory accesses of the specified type
 * issued before the DMB are guaranteed to be observed (in the specified scope) before memory
 * accesses issued after the DMB.
 *
 * For example, DMB should be used between storing data, and updating a flag variable that makes
 * that data available to another core.
 *
 * The __dmb() intrinsic also acts as a compiler memory barrier of the appropriate type.
 */
public fun <A> dmb(arg: A) where A : Dmb {
    arg.underlyingDmb()
}

/**
 * Generates a DSB (data synchronization barrier) instruction or equivalent CP15 instruction.
 *
 * DSB ensures the completion of memory accesses. A DSB behaves as the equivalent DMB and has
 * additional properties. After a DSB instruction completes, all memory accesses of the specified
 * type issued before the DSB are guaranteed to have completed.
 *
 * The __dsb() intrinsic also acts as a compiler memory barrier of the appropriate type.
 */
public fun <A> dsb(arg: A) where A : Dsb {
    arg.underlyingDsb()
}

/**
 * Generates an ISB (instruction synchronization barrier) instruction or equivalent CP15
 * instruction.
 *
 * This instruction flushes the processor pipeline fetch buffers, so that following instructions
 * are fetched from cache or memory.
 *
 * An ISB is needed after some system maintenance operations. An ISB is also needed before
 * transferring control to code that has been loaded or modified in memory, for example by an
 * overlay mechanism or just-in-time code generator.  (Note that if instruction and data caches are
 * separate, privileged cache maintenance operations would be needed in order to unify the caches.)
 *
 * The only supported argument for the __isb() intrinsic is 15, corresponding to the SY (full
 * system) scope of the ISB instruction.
 */
public fun <A> isb(arg: A) where A : Isb {
    arg.underlyingIsb()
}

public expect fun dmb(arg0: Int)

public expect fun dsb(arg0: Int)

public expect fun isb(arg0: Int)

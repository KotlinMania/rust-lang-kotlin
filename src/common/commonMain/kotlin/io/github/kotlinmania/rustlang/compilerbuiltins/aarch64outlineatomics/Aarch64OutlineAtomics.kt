// port-lint: source aarch64_outline_atomics.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.aarch64outlineatomics

import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store

// Aarch64 targets have two possible implementations for atomics:
// 1. Load-Locked, Store-Conditional (LL/SC), older and slower.
// 2. Large System Extensions (LSE), newer and faster.
// To avoid breaking backwards compat, C toolchains introduced a concept of "outlined atomics",
// where atomic operations call into the compiler runtime to dispatch between two depending on
// which is supported on the current CPU.
// See <https://community.arm.com/arm-community-blogs/b/tools-software-ides-blog/posts/making-the-most-of-the-arm-architecture-in-gcc-10#:~:text=out%20of%20line%20atomics> for more discussion.
//
// Ported from `aarch64 / lse.S` in LLVM's compiler-rt.
//
// Generate functions for each of the following symbols:
//  __aarch64_casM_ORDER
//  __aarch64_swpN_ORDER
//  __aarch64_ldaddN_ORDER
//  __aarch64_ldclrN_ORDER
//  __aarch64_ldeorN_ORDER
//  __aarch64_ldsetN_ORDER
// for N = (1, 2, 4, 8), M = (1, 2, 4, 8, 16), ORDER = ( relax, acq, rel, acqRel )
//
// The original `lse.S` has some truly horrifying code that expects to be compiled multiple times with different constants.
// We do something similar, but with macro arguments.
//
// Translate a byte size to a Rust type.
//
// Given a byte size and a register number, return a register of the appropriate size.
//
// See <https://developer.arm.com/documentation/102374/0101/Registers-in-AArch64---general-purpose-registers>.
//
// Given an atomic ordering, translate it to the acquire suffix for the lxdr aarch64 ASM instruction.
//
// Given an atomic ordering, translate it to the release suffix for the stxr aarch64 ASM instruction.
//
// Given a size in bytes, translate it to the byte suffix for an aarch64 ASM instruction.
//
// Given a byte size, translate it to an Unsigned eXTend instruction
// with the correct semantics.
//
// See <https://developer.arm.com/documentation/ddi0596/2020-12/Base-Instructions/UXTB--Unsigned-Extend-Byte--an-alias-of-UBFM->
//
// Given an atomic ordering and byte size, translate it to a LoaD eXclusive Register instruction
// with the correct semantics.
//
// See <https://developer.arm.com/documentation/ddi0596/2020-12/Base-Instructions/LDXR--Load-Exclusive-Register->.
//
// Given an atomic ordering and byte size, translate it to a STore eXclusive Register instruction
// with the correct semantics.
//
// See <https://developer.arm.com/documentation/ddi0596/2020-12/Base-Instructions/STXR--Store-Exclusive-Register->.
//
// Given an atomic ordering and byte size, translate it to a LoaD eXclusive Pair of registers instruction
// with the correct semantics.
//
// See <https://developer.arm.com/documentation/ddi0596/2020-12/Base-Instructions/LDXP--Load-Exclusive-Pair-of-Registers->
//
// Given an atomic ordering and byte size, translate it to a STore eXclusive Pair of registers instruction
// with the correct semantics.
//
// See <https://developer.arm.com/documentation/ddi0596/2020-12/Base-Instructions/STXP--Store-Exclusive-Pair-of-registers->.
//
// See <https://doc.rust-lang.org/stable/std/sync/atomic/struct.AtomicI8.html#method.compareAndSwap>.
//
// See <https://doc.rust-lang.org/stable/std/sync/atomic/struct.AtomicI8.html#method.swap>.
//
// See (e.g.) <https://doc.rust-lang.org/stable/std/sync/atomic/struct.AtomicI8.html#method.fetchAdd>.

/**
 * non-zero if the host supports LSE atomics.
 */
public var HAVE_LSE_ATOMICS: AtomicU8 = AtomicU8.new(0u.toUByte())

/**
 * Call to enable LSE in outline atomic operations. The caller must verify
 * LSE operations are supported.
 */
public fun rustEnableLse() {
    HAVE_LSE_ATOMICS.store(1u.toUByte(), Ordering.Relaxed)
}

/**
 * Function to enable/disable LSE. To be used only for testing purposes.
 */
public fun setHaveLseAtomics(hasLse: Boolean) {
    val lseFlag =
        if (hasLse) {
            1
        } else {
            0
        }
    HAVE_LSE_ATOMICS.store(lseFlag, Ordering.Relaxed)
}

/**
 * Function to obtain whether LSE is enabled or not. To be used only for testing purposes.
 */
public fun getHaveLseAtomics(): Boolean = HAVE_LSE_ATOMICS.load(Ordering.Relaxed) != 0u.toUByte()

private const val FOREACH_ORDERING_TEMPLATE: String = "\$macro!(Relaxed, \$bytes, \$ {concat(\$name, relax)}); \$macro!(Acquire, \$bytes, \$ {concat(\$name, acq)}); \$macro!(Release, \$bytes, \$ {concat(\$name, rel)}); \$macro!(AcqRel, \$bytes, \$ {concat(\$name, acqRel)});"

private const val FOREACH_ORDERING_TEMPLATE_1: String = "\$macro!(Relaxed, \$ {concat(\$name, relax)}); \$macro!(Acquire, \$ {concat(\$name, acq)}); \$macro!(Release, \$ {concat(\$name, rel)}); \$macro!(AcqRel, \$ {concat(\$name, acqRel)});"

private const val FOREACH_BYTES_TEMPLATE: String = "foreachOrdering(\$macro, 1, \$ {concat(aarch64, \$name, \"1\")}); foreachOrdering(\$macro, 2, \$ {concat(aarch64, \$name, \"2\")}); foreachOrdering(\$macro, 4, \$ {concat(aarch64, \$name, \"4\")}); foreachOrdering(\$macro, 8, \$ {concat(aarch64, \$name, \"8\")});"

private const val FOREACH_CAS_TEMPLATE: String = "foreachBytes(\$macro, cas);"

private const val FOREACH_CAS16_TEMPLATE: String = "foreachOrdering(\$macro, aarch64Cas16);"

private const val FOREACH_SWP_TEMPLATE: String = "foreachBytes(\$macro, swp);"

private const val FOREACH_LDADD_TEMPLATE: String = "foreachBytes(\$macro, ldadd);"

private const val FOREACH_LDCLR_TEMPLATE: String = "foreachBytes(\$macro, ldclr);"

private const val FOREACH_LDEOR_TEMPLATE: String = "foreachBytes(\$macro, ldeor);"

private const val FOREACH_LDSET_TEMPLATE: String = "foreachBytes(\$macro, ldset);"

public fun foreachOrdering(macro: String = "", bytes: String = "", name: String = ""): String =
    when {
        macro.isNotEmpty() && bytes.isNotEmpty() && name.isNotEmpty() -> arrayOf("\$macro" to macro, "\$bytes" to bytes, "\$name" to name).fold(FOREACH_ORDERING_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        macro.isNotEmpty() && name.isNotEmpty() -> arrayOf("\$macro" to macro, "\$name" to name).fold(FOREACH_ORDERING_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

public fun foreachBytes(macro: String = "", name: String = ""): String =
    arrayOf("\$macro" to macro, "\$name" to name).fold(FOREACH_BYTES_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }

/**
 * Generate different macros for cas/swp/add/clr/eor/set so that we can test them separately.
 */

public fun foreachCas(macro: String = ""): String =
    FOREACH_CAS_TEMPLATE.replace("\$macro", macro)

/**
 * Only CAS supports 16 bytes, and it has a different implementation that uses a different macro.
 */

public fun foreachCas16(macro: String = ""): String =
    FOREACH_CAS16_TEMPLATE.replace("\$macro", macro)

public fun foreachSwp(macro: String = ""): String =
    FOREACH_SWP_TEMPLATE.replace("\$macro", macro)

public fun foreachLdadd(macro: String = ""): String =
    FOREACH_LDADD_TEMPLATE.replace("\$macro", macro)

public fun foreachLdclr(macro: String = ""): String =
    FOREACH_LDCLR_TEMPLATE.replace("\$macro", macro)

public fun foreachLdeor(macro: String = ""): String =
    FOREACH_LDEOR_TEMPLATE.replace("\$macro", macro)

public fun foreachLdset(macro: String = ""): String =
    FOREACH_LDSET_TEMPLATE.replace("\$macro", macro)

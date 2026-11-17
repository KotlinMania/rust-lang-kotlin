// port-lint: source x86/cpuid.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

// `cpuid` intrinsics

// ^^ the derived impl of Debug for CpuidResult is not @inline and that's OK.

/**
 * Result of the `cpuid` instruction.
 */
public open class CpuidResult(
    /**
     * EAX register.
     */
    public var eax: UInt,
    /**
     * EBX register.
     */
    public var ebx: UInt,
    /**
     * ECX register.
     */
    public var ecx: UInt,
    /**
     * EDX register.
     */
    public var edx: UInt,
) {
    public open fun clone(): CpuidResult = CpuidResult(eax, ebx, ecx, edx)

    public companion object {
        public fun typeLayout(): TypeLayout<CpuidResult> =
            object : TypeLayout<CpuidResult> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CpuidResult =
                    CpuidResult(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: CpuidResult) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.eax)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.ebx)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.ecx)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.edx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CpuidResult =
                    CpuidResult(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CpuidResult) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.eax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.ebx)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.ecx)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.edx)
                }
            }
    }
}

/**
 * Returns the result of the `cpuid` instruction for a given `leaf` (`EAX`)
 * and `subLeaf` (`ECX`).
 *
 * There are two types of information leaves - basic leaves (with `leaf<0x8000000`)
 * and extended leaves (with `leaf >= 0x80000000`). The highest supported basic and
 * extended leaves can be obtained by calling CPUID with `0` and `0x80000000`,
 * respectively, and reading the value in the `EAX` register. If the leaf supports
 * more than one sub-leaf, then the procedure of obtaining the highest supported
 * sub-leaf, as well as the behavior if a invalid sub-leaf value is passed, depends
 * on the specific leaf.
 *
 * If the `leaf` value is higher than the maximum supported basic or extended leaf
 * for the processor, this returns the information for the highest supported basic
 * information leaf (with the passed `subLeaf` value). If the `leaf` value is less
 * than or equal to the highest basic or extended leaf value, but the leaf is not
 * supported on the processor, all zeros are returned.
 *
 * The [CPUID Wikipedia page][wikiCpuid] contains information on how to query which
 * information using the `EAX` and `ECX` registers, and the interpretation of
 * the results returned in `EAX`, `EBX`, `ECX`, and `EDX`.
 *
 * The references are:
 * - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
 *   Instruction Set Reference, A-Z][intel64Ref].
 * - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
 *   System Instructions][amd64Ref].
 *
 * [wikiCpuid]: https://en.wikipedia.org/wiki/CPUID
 * [intel64Ref]: https://cdrdv2-public.intel.com/671110/325383-sdm-vol-2abcd.pdf
 * [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
 */
public fun cpuidCount(leaf: UInt, subLeaf: UInt): CpuidResult {
    if (false) {
        error("`__cpuid` cannot be used in SGX")
    }
    val eax: Any?
    val ebx: Any?
    val ecx: Any?
    val edx: Any?
    // LLVM sometimes reserves `ebx` for its internal use, we so we need to use
    // a scratch register for it instead.
    run {
        Unit
    }
    run {
        Unit
    }
    return CpuidResult(eax = eax, ebx = ebx, ecx = ecx, edx = edx)
}

/**
 * Calls CPUID with the provided `leaf` value, with `subLeaf` set to 0.
 * See [`cpuidCount`](fn.__cpuid_count.html).
 */
public fun cpuid(leaf: UInt): CpuidResult = cpuidCount(leaf, 0u)

/**
 * Returns the EAX and EBX register after calling CPUID with the provided `leaf`,
 * with `subLeaf` set to 0.
 *
 * If `leaf` if 0 or `0x80000000`, the first tuple argument contains the maximum
 * supported basic or extended leaf, respectively.
 *
 * See also [`cpuid`](fn.__cpuid.html) and
 * [`cpuidCount`](fn.__cpuid_count.html).
 */
public fun getCpuidMax(leaf: UInt): Pair<UInt, UInt> {
    val structVal = cpuid(leaf)
    val eax = structVal.eax
    val ebx = structVal.ebx
    return Pair(eax, ebx)
}

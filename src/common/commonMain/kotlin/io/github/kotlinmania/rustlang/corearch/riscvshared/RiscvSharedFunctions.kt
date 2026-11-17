package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * Generates the `PAUSE` instruction
 *
 * The PAUSE instruction is a HINT that indicates the current hart's rate of instruction retirement
 * should be temporarily reduced or paused. The duration of its effect must be bounded and may be zero.
 */
public fun pause() {
    // Use `.option` directives to expose this HINT instruction
    // (no-op if not supported by the hardware) without `@targetFeature`.
    run {
        Unit
    }
}

/**
 * Generates the `NOP` instruction
 *
 * The NOP instruction does not change any architecturally visible state, except for
 * advancing the `pc` and incrementing any applicable performance counters.
 */
public fun nop() {
    run {
        Unit
    }
}

/**
 * Generates the `WFI` instruction
 *
 * The WFI instruction provides a hint to the implementation that the current hart can be stalled
 * until an interrupt might need servicing. This instruction is a hint,
 * and a legal implementation is to simply implement WFI as a NOP.
 */
public fun wfi() {
    Unit
}

/**
 * Generates the `FENCE.I` instruction
 *
 * A FENCE.I instruction ensures that a subsequent instruction fetch on a RISC-V hart will see
 * any previous data stores already visible to the same RISC-V hart.
 *
 * FENCE.I does not ensure that other RISC-V harts' instruction fetches will observe the
 * local hart's stores in a multiprocessor system.
 */
public fun fenceI() {
    Unit
}

/**
 * Supervisor memory management fence for given virtual address and address space
 *
 * The fence orders only reads and writes made to leaf page table entries corresponding to
 * the virtual address in parameter `vaddr`, for the address space identified by integer parameter
 * `asid`. Accesses to global mappings are not ordered. The fence also invalidates all
 * address-translation cache entries that contain leaf page table entries corresponding to the
 * virtual address in parameter `vaddr` and that match the address space identified by integer
 * parameter `asid`, except for entries containing global mappings.
 */
public fun sfenceVma(vaddr: Int, asid: Int) {
    Unit
}

/**
 * Supervisor memory management fence for given virtual address
 *
 * The fence orders only reads and writes made to leaf page table entries corresponding to
 * the virtual address in parameter `vaddr`, for all address spaces.
 * The fence also invalidates all address-translation cache entries that contain leaf page
 * table entries corresponding to the virtual address in parameter `vaddr`, for all address spaces.
 */
public fun sfenceVmaVaddr(vaddr: Int) {
    Unit
}

/**
 * Supervisor memory management fence for given address space
 *
 * The fence orders all reads and writes made to any level of the page tables,
 * but only for the address space identified by integer parameter `asid`.
 *
 * Accesses to global mappings are not ordered. The fence also invalidates all
 * address-translation cache entries matching the address space identified by integer
 * parameter `asid`, except for entries containing global mappings.
 */
public fun sfenceVmaAsid(asid: Int) {
    Unit
}

/**
 * Supervisor memory management fence for all address spaces and virtual addresses
 *
 * The fence orders all reads and writes made to any level of the page
 * tables, for all address spaces. The fence also invalidates all address-translation cache entries,
 * for all address spaces.
 */
public fun sfenceVmaAll() {
    Unit
}

/**
 * Invalidate supervisor translation cache for given virtual address and address space
 *
 * This instruction invalidates any address-translation cache entries that an
 * `SFENCE.VMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 */
public fun sinvalVma(vaddr: Int, asid: Int) {
    // asm("sinval.vma (), ()", in(reg) vaddr, in(reg) asid, options(nostack, preservesFlags));
    Unit
}

/**
 * Invalidate supervisor translation cache for given virtual address
 *
 * This instruction invalidates any address-translation cache entries that an
 * `SFENCE.VMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 */
public fun sinvalVmaVaddr(vaddr: Int) {
    Unit
}

/**
 * Invalidate supervisor translation cache for given address space
 *
 * This instruction invalidates any address-translation cache entries that an
 * `SFENCE.VMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 */
public fun sinvalVmaAsid(asid: Int) {
    Unit
}

/**
 * Invalidate supervisor translation cache for all address spaces and virtual addresses
 *
 * This instruction invalidates any address-translation cache entries that an
 * `SFENCE.VMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 */
public fun sinvalVmaAll() {
    Unit
}

/**
 * Generates the `SFENCE.W.INVAL` instruction
 *
 * This instruction guarantees that any previous stores already visible to the current RISC-V hart
 * are ordered before subsequent `SINVAL.VMA` instructions executed by the same hart.
 */
public fun sfenceWInval() {
    // asm("sfence.w.inval", options(nostack, preservesFlags));
    Unit
}

/**
 * Generates the `SFENCE.INVAL.IR` instruction
 *
 * This instruction guarantees that any previous SINVAL.VMA instructions executed by the current hart
 * are ordered before subsequent implicit references by that hart to the memory-management data structures.
 */
public fun sfenceInvalIr() {
    // asm("sfence.inval.ir", options(nostack, preservesFlags));
    Unit
}

/**
 * Loads virtual machine memory by signed byte integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.B`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvB(src: ConstPtr): Byte {
    val value: Byte
    Unit
    return value
}

/**
 * Loads virtual machine memory by unsigned byte integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.BU`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvBu(src: ConstPtr): UByte {
    val value: UByte
    Unit
    return value
}

/**
 * Loads virtual machine memory by signed half integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.H`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvH(src: ConstPtr): Short {
    val value: Short
    Unit
    return value
}

/**
 * Loads virtual machine memory by unsigned half integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.HU`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvHu(src: ConstPtr): UShort {
    val value: UShort
    Unit
    return value
}

/**
 * Accesses virtual machine instruction by unsigned half integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * the memory being read must be executable in both stages of address translation,
 * but read permission is not required.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLVX.HU`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvxHu(src: ConstPtr): UShort {
    val insn: UShort
    Unit
    return insn
}

/**
 * Loads virtual machine memory by signed word integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.W`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvW(src: ConstPtr): Int {
    val value: Int
    Unit
    return value
}

/**
 * Accesses virtual machine instruction by unsigned word integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * the memory being read must be executable in both stages of address translation,
 * but read permission is not required.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLVX.WU`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvxWu(src: ConstPtr): UInt {
    val insn: UInt
    Unit
    return insn
}

/**
 * Stores virtual machine memory by byte integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HSV.B`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hsvB(dst: MutPtr, src: Byte) {
    Unit
}

/**
 * Stores virtual machine memory by half integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HSV.H`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hsvH(dst: MutPtr, src: Short) {
    Unit
}

/**
 * Stores virtual machine memory by word integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HSV.W`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hsvW(dst: MutPtr, src: Int) {
    Unit
}

/**
 * Hypervisor memory management fence for given guest virtual address and guest address space
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all
 * implicit reads by that hart done for VS-stage address translation for instructions that:
 * - are subsequent to the `HFENCE.VVMA`, and
 * - execute when `hgatp.VMID` has the same setting as it did when `HFENCE.VVMA` executed.
 *
 * This fence specifies a single guest virtual address, and a single guest address-space identifier.
 */
public fun hfenceVvma(vaddr: Int, asid: Int) {
    // asm("hfence.vvma (), ()", in(reg) vaddr, in(reg) asid, options(nostack, preservesFlags));
    Unit
}

/**
 * Hypervisor memory management fence for given guest virtual address
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all
 * implicit reads by that hart done for VS-stage address translation for instructions that:
 * - are subsequent to the `HFENCE.VVMA`, and
 * - execute when `hgatp.VMID` has the same setting as it did when `HFENCE.VVMA` executed.
 *
 * This fence specifies a single guest virtual address.
 */
public fun hfenceVvmaVaddr(vaddr: Int) {
    Unit
}

/**
 * Hypervisor memory management fence for given guest address space
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all
 * implicit reads by that hart done for VS-stage address translation for instructions that:
 * - are subsequent to the `HFENCE.VVMA`, and
 * - execute when `hgatp.VMID` has the same setting as it did when `HFENCE.VVMA` executed.
 *
 * This fence specifies a single guest address-space identifier.
 */
public fun hfenceVvmaAsid(asid: Int) {
    Unit
}

/**
 * Hypervisor memory management fence for all guest address spaces and guest virtual addresses
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all
 * implicit reads by that hart done for VS-stage address translation for instructions that:
 * - are subsequent to the `HFENCE.VVMA`, and
 * - execute when `hgatp.VMID` has the same setting as it did when `HFENCE.VVMA` executed.
 *
 * This fence applies to any guest address spaces and guest virtual addresses.
 */
public fun hfenceVvmaAll() {
    Unit
}

/**
 * Hypervisor memory management fence for guest physical address and virtual machine
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all implicit reads
 * by that hart done for G-stage address translation for instructions that follow the HFENCE.GVMA.
 *
 * This fence specifies a single guest physical address, **shifted right by 2 bits**, and a single virtual machine
 * by virtual machine identifier (VMID).
 */
public fun hfenceGvma(gaddr: Int, vmid: Int) {
    // asm("hfence.gvma (), ()", in(reg) gaddr, in(reg) vmid, options(nostack, preservesFlags));
    Unit
}

/**
 * Hypervisor memory management fence for guest physical address
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all implicit reads
 * by that hart done for G-stage address translation for instructions that follow the HFENCE.GVMA.
 *
 * This fence specifies a single guest physical address; **the physical address should be shifted right by 2 bits**.
 */
public fun hfenceGvmaGaddr(gaddr: Int) {
    Unit
}

/**
 * Hypervisor memory management fence for given virtual machine
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all implicit reads
 * by that hart done for G-stage address translation for instructions that follow the HFENCE.GVMA.
 *
 * This fence specifies a single virtual machine by virtual machine identifier (VMID).
 */
public fun hfenceGvmaVmid(vmid: Int) {
    Unit
}

/**
 * Hypervisor memory management fence for all virtual machines and guest physical addresses
 *
 * Guarantees that any previous stores already visible to the current hart are ordered before all implicit reads
 * by that hart done for G-stage address translation for instructions that follow the HFENCE.GVMA.
 *
 * This fence specifies all guest physical addresses and all virtual machines.
 */
public fun hfenceGvmaAll() {
    Unit
}

/**
 * Invalidate hypervisor translation cache for given guest virtual address and guest address space
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.VVMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 *
 * This fence specifies a single guest virtual address, and a single guest address-space identifier.
 */
public fun hinvalVvma(vaddr: Int, asid: Int) {
    // asm("hinval.vvma (), ()", in(reg) vaddr, in(reg) asid, options(nostack, preservesFlags));
    Unit
}

/**
 * Invalidate hypervisor translation cache for given guest virtual address
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.VVMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 *
 * This fence specifies a single guest virtual address.
 */
public fun hinvalVvmaVaddr(vaddr: Int) {
    Unit
}

/**
 * Invalidate hypervisor translation cache for given guest address space
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.VVMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 *
 * This fence specifies a single guest address-space identifier.
 */
public fun hinvalVvmaAsid(asid: Int) {
    Unit
}

/**
 * Invalidate hypervisor translation cache for all guest address spaces and guest virtual addresses
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.VVMA` instruction with the same values of `vaddr` and `asid` would invalidate.
 *
 * This fence applies to any guest address spaces and guest virtual addresses.
 */
public fun hinvalVvmaAll() {
    Unit
}

/**
 * Invalidate hypervisor translation cache for guest physical address and virtual machine
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.GVMA` instruction with the same values of `gaddr` and `vmid` would invalidate.
 *
 * This fence specifies a single guest physical address, **shifted right by 2 bits**, and a single virtual machine
 * by virtual machine identifier (VMID).
 */
public fun hinvalGvma(gaddr: Int, vmid: Int) {
    // asm("hinval.gvma (), ()", in(reg) gaddr, in(reg) vmid, options(nostack, preservesFlags));
    Unit
}

/**
 * Invalidate hypervisor translation cache for guest physical address
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.GVMA` instruction with the same values of `gaddr` and `vmid` would invalidate.
 *
 * This fence specifies a single guest physical address; **the physical address should be shifted right by 2 bits**.
 */
public fun hinvalGvmaGaddr(gaddr: Int) {
    Unit
}

/**
 * Invalidate hypervisor translation cache for given virtual machine
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.GVMA` instruction with the same values of `gaddr` and `vmid` would invalidate.
 *
 * This fence specifies a single virtual machine by virtual machine identifier (VMID).
 */
public fun hinvalGvmaVmid(vmid: Int) {
    Unit
}

/**
 * Invalidate hypervisor translation cache for all virtual machines and guest physical addresses
 *
 * This instruction invalidates any address-translation cache entries that an
 * `HFENCE.GVMA` instruction with the same values of `gaddr` and `vmid` would invalidate.
 *
 * This fence specifies all guest physical addresses and all virtual machines.
 */
public fun hinvalGvmaAll() {
    Unit
}

/**
 * Reads the floating-point rounding mode register `frm`
 *
 * According to "F" Standard Extension for Single-Precision Floating-Point, Version 2.2,
 * the rounding mode field is defined as listed in the table below:
 *
 * | Rounding Mode | Mnemonic | Meaning |
 * |:-------------|:----------|:---------|
 * | 000 | RNE | Round to Nearest, ties to Even |
 * | 001 | RTZ | Round towards Zero |
 * | 010 | RDN | Round Down (towards −∞) |
 * | 011 | RUP | Round Up (towards +∞) |
 * | 100 | RMM | Round to Nearest, ties to Max Magnitude |
 * | 101 |     | _Reserved for future use._ |
 * | 110 |     | _Reserved for future use._ |
 * | 111 | DYN | In Rounding Mode register, _reserved_. |
 */
public fun frrm(): UInt {
    val value: UInt
    run {
        Unit
    }
    return value
}

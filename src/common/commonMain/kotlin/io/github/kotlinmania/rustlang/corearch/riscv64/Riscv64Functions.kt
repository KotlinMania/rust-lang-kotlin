package io.github.kotlinmania.rustlang.corearch.riscv64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.arch.asm

/**
 * Loads virtual machine memory by unsigned word integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This operation is not available under RV32 base instruction set.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.WU`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvWu(src: ConstPtr): UInt {
    val value: UInt
    asm(".insn i 0x73, 0x4, {}, {}, 0x681", lateout, reg, value, reg, src, options(readonly, nostack, preservesFlags))
    return value
}

/**
 * Loads virtual machine memory by double integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This operation is not available under RV32 base instruction set.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HLV.D`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hlvD(src: ConstPtr): Long {
    val value: Long
    asm(".insn i 0x73, 0x4, {}, {}, 0x6C0", lateout, reg, value, reg, src, options(readonly, nostack, preservesFlags))
    return value
}

/**
 * Stores virtual machine memory by double integer
 *
 * This instruction performs an explicit memory access as though `V = 1`;
 * i.e., with the address translation and protection, and the endianness, that apply to memory
 * accesses in either VS-mode or VU-mode.
 *
 * This function is unchecked for it accesses the virtual supervisor or user via a `HSV.D`
 * instruction which is effectively a dereference to any memory address.
 */
public fun hsvD(dst: MutPtr, src: Long) {
    asm(".insn r 0x73, 0x4, 0x37, x0, {}, {}", reg, dst, reg, src, options(nostack, preservesFlags))
}

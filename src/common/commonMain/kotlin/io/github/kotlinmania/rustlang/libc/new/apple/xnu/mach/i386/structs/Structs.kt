// port-lint: source new/apple/xnu/mach/i386/_structs.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.xnu.mach.i386.structs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*

// Header: `i386 / _structs.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/osfmk/mach/i386/_structs.h>

public data class DarwinMmstReg(
    public var mmstReg: UByteArray = UByteArray(10),
    public var mmstRsrv: UByteArray = UByteArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DarwinMmstReg) return false
        if (!this.mmstReg.contentEquals(other.mmstReg)) return false
        if (!this.mmstRsrv.contentEquals(other.mmstRsrv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.mmstReg.contentHashCode()
        result = 31 * result + this.mmstRsrv.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DarwinMmstReg> =
            object : TypeLayout<DarwinMmstReg> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): DarwinMmstReg =
                    DarwinMmstReg(
                        (PrimitiveArrayLayouts.uByteArray(10)).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(6)).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: DarwinMmstReg) {
                    (PrimitiveArrayLayouts.uByteArray(10)).writeNative(addr, value.mmstReg)
                    (PrimitiveArrayLayouts.uByteArray(6)).writeNative(addr + 10L, value.mmstRsrv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinMmstReg =
                    DarwinMmstReg(
                        (PrimitiveArrayLayouts.uByteArray(10)).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(6)).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinMmstReg) {
                    (PrimitiveArrayLayouts.uByteArray(10)).writeBytes(buf, offset, value.mmstReg)
                    (PrimitiveArrayLayouts.uByteArray(6)).writeBytes(buf, offset + 10, value.mmstRsrv)
                }
            }
    }
}

public data class DarwinXmmReg(
    public var xmmReg: UByteArray = UByteArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DarwinXmmReg) return false
        if (!this.xmmReg.contentEquals(other.xmmReg)) return false
        return true
    }

    override fun hashCode(): Int = this.xmmReg.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<DarwinXmmReg> =
            object : TypeLayout<DarwinXmmReg> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): DarwinXmmReg =
                    DarwinXmmReg(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DarwinXmmReg) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.xmmReg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinXmmReg =
                    DarwinXmmReg(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinXmmReg) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.xmmReg)
                }
            }
    }
}

public class DarwinX86ThreadState64(
    public var rax: ULong = 0uL,
    public var rbx: ULong = 0uL,
    public var rcx: ULong = 0uL,
    public var rdx: ULong = 0uL,
    public var rdi: ULong = 0uL,
    public var rsi: ULong = 0uL,
    public var rbp: ULong = 0uL,
    public var rsp: ULong = 0uL,
    public var r8: ULong = 0uL,
    public var r9: ULong = 0uL,
    public var r10: ULong = 0uL,
    public var r11: ULong = 0uL,
    public var r12: ULong = 0uL,
    public var r13: ULong = 0uL,
    public var r14: ULong = 0uL,
    public var r15: ULong = 0uL,
    public var rip: ULong = 0uL,
    public var rflags: ULong = 0uL,
    public var cs: ULong = 0uL,
    public var fs: ULong = 0uL,
    public var gs: ULong = 0uL,
) {
    public fun clone(): DarwinX86ThreadState64 = DarwinX86ThreadState64(rax, rbx, rcx, rdx, rdi, rsi, rbp, rsp, r8, r9, r10, r11, r12, r13, r14, r15, rip, rflags, cs, fs, gs)

    public companion object {
        public fun typeLayout(): TypeLayout<DarwinX86ThreadState64> =
            object : TypeLayout<DarwinX86ThreadState64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 168L
                override fun readNative(addr: Long): DarwinX86ThreadState64 =
                    DarwinX86ThreadState64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                    )
                override fun writeNative(addr: Long, value: DarwinX86ThreadState64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rax)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rbx)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.rcx)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.rdx)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.rdi)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.rsi)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.rbp)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.rsp)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.r8)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.r9)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.r10)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.r11)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.r12)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.r13)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.r14)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.r15)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.rflags)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.cs)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.fs)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.gs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinX86ThreadState64 =
                    DarwinX86ThreadState64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinX86ThreadState64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rax)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rbx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.rcx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.rdx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.rdi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.rsi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.rbp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.rsp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.r8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.r9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.r10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.r11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.r12)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.r13)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.r14)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.r15)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.rflags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.cs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.fs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.gs)
                }
            }
    }
}

public data class DarwinX86ExceptionState64(
    public var trapno: UShort = 0u,
    public var cpu: UShort = 0u,
    public var err: UInt = 0u,
    public var faultvaddr: ULong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<DarwinX86ExceptionState64> =
            object : TypeLayout<DarwinX86ExceptionState64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): DarwinX86ExceptionState64 =
                    DarwinX86ExceptionState64(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: DarwinX86ExceptionState64) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.trapno)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.cpu)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.err)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.faultvaddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinX86ExceptionState64 =
                    DarwinX86ExceptionState64(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinX86ExceptionState64) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.trapno)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.cpu)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.err)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.faultvaddr)
                }
            }
    }
}

public class DarwinX86FloatState64(
    public var fpuReserved: IntArray = IntArray(2),
    public var fpuFcw: CShort = 0,
    public var fpuFsw: CShort = 0,
    public var fpuFtw: UByte = 0u,
    public var fpuRsrv1: UByte = 0u,
    public var fpuFop: UShort = 0u,
    public var fpuIp: UInt = 0u,
    public var fpuCs: UShort = 0u,
    public var fpuRsrv2: UShort = 0u,
    public var fpuDp: UInt = 0u,
    public var fpuDs: UShort = 0u,
    public var fpuRsrv3: UShort = 0u,
    public var fpuMxcsr: UInt = 0u,
    public var fpuMxcsrmask: UInt = 0u,
    public var fpuStmm0: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm1: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm2: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm3: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm4: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm5: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm6: DarwinMmstReg = DarwinMmstReg(),
    public var fpuStmm7: DarwinMmstReg = DarwinMmstReg(),
    public var fpuXmm0: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm1: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm2: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm3: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm4: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm5: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm6: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm7: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm8: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm9: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm10: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm11: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm12: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm13: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm14: DarwinXmmReg = DarwinXmmReg(),
    public var fpuXmm15: DarwinXmmReg = DarwinXmmReg(),
    // Note(apple): this field is actually [u8; 96], but defining it with a bigger type allows
    // us to auto-implement traits for it since the length of the array is less than 32
    public var fpuRsrv4: UIntArray = UIntArray(24),
    public var fpuReserved1: CInt = 0,
) {
    public fun clone(): DarwinX86FloatState64 = DarwinX86FloatState64(fpuReserved, fpuFcw, fpuFsw, fpuFtw, fpuRsrv1, fpuFop, fpuIp, fpuCs, fpuRsrv2, fpuDp, fpuDs, fpuRsrv3, fpuMxcsr, fpuMxcsrmask, fpuStmm0, fpuStmm1, fpuStmm2, fpuStmm3, fpuStmm4, fpuStmm5, fpuStmm6, fpuStmm7, fpuXmm0, fpuXmm1, fpuXmm2, fpuXmm3, fpuXmm4, fpuXmm5, fpuXmm6, fpuXmm7, fpuXmm8, fpuXmm9, fpuXmm10, fpuXmm11, fpuXmm12, fpuXmm13, fpuXmm14, fpuXmm15, fpuRsrv4, fpuReserved1)

    public companion object {
        public fun typeLayout(): TypeLayout<DarwinX86FloatState64> =
            object : TypeLayout<DarwinX86FloatState64> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 524L
                override fun readNative(addr: Long): DarwinX86FloatState64 =
                    DarwinX86FloatState64(
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                        (PrimitiveLayouts.I16).readNative(addr + 10L),
                        (PrimitiveLayouts.U8).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 13L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 22L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 28L),
                        (PrimitiveLayouts.U16).readNative(addr + 30L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 40L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 56L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 72L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 88L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 104L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 120L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 136L),
                        (DarwinMmstReg.typeLayout()).readNative(addr + 152L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 168L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 184L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 200L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 216L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 232L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 248L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 264L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 280L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 296L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 312L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 328L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 344L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 360L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 376L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 392L),
                        (DarwinXmmReg.typeLayout()).readNative(addr + 408L),
                        (PrimitiveArrayLayouts.uIntArray(24)).readNative(addr + 424L),
                        (PrimitiveLayouts.I32).readNative(addr + 520L),
                    )
                override fun writeNative(addr: Long, value: DarwinX86FloatState64) {
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr, value.fpuReserved)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.fpuFcw)
                    (PrimitiveLayouts.I16).writeNative(addr + 10L, value.fpuFsw)
                    (PrimitiveLayouts.U8).writeNative(addr + 12L, value.fpuFtw)
                    (PrimitiveLayouts.U8).writeNative(addr + 13L, value.fpuRsrv1)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.fpuFop)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.fpuIp)
                    (PrimitiveLayouts.U16).writeNative(addr + 20L, value.fpuCs)
                    (PrimitiveLayouts.U16).writeNative(addr + 22L, value.fpuRsrv2)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.fpuDp)
                    (PrimitiveLayouts.U16).writeNative(addr + 28L, value.fpuDs)
                    (PrimitiveLayouts.U16).writeNative(addr + 30L, value.fpuRsrv3)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.fpuMxcsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.fpuMxcsrmask)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 40L, value.fpuStmm0)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 56L, value.fpuStmm1)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 72L, value.fpuStmm2)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 88L, value.fpuStmm3)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 104L, value.fpuStmm4)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 120L, value.fpuStmm5)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 136L, value.fpuStmm6)
                    (DarwinMmstReg.typeLayout()).writeNative(addr + 152L, value.fpuStmm7)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 168L, value.fpuXmm0)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 184L, value.fpuXmm1)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 200L, value.fpuXmm2)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 216L, value.fpuXmm3)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 232L, value.fpuXmm4)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 248L, value.fpuXmm5)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 264L, value.fpuXmm6)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 280L, value.fpuXmm7)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 296L, value.fpuXmm8)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 312L, value.fpuXmm9)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 328L, value.fpuXmm10)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 344L, value.fpuXmm11)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 360L, value.fpuXmm12)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 376L, value.fpuXmm13)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 392L, value.fpuXmm14)
                    (DarwinXmmReg.typeLayout()).writeNative(addr + 408L, value.fpuXmm15)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeNative(addr + 424L, value.fpuRsrv4)
                    (PrimitiveLayouts.I32).writeNative(addr + 520L, value.fpuReserved1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinX86FloatState64 =
                    DarwinX86FloatState64(
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 13),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 22),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 30),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 40),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 56),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 72),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 88),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 104),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 120),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 136),
                        (DarwinMmstReg.typeLayout()).readBytes(buf, offset + 152),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 168),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 184),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 200),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 216),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 232),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 248),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 264),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 280),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 296),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 312),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 328),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 344),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 360),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 376),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 392),
                        (DarwinXmmReg.typeLayout()).readBytes(buf, offset + 408),
                        (PrimitiveArrayLayouts.uIntArray(24)).readBytes(buf, offset + 424),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 520),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinX86FloatState64) {
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset, value.fpuReserved)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.fpuFcw)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 10, value.fpuFsw)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 12, value.fpuFtw)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 13, value.fpuRsrv1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.fpuFop)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.fpuIp)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.fpuCs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 22, value.fpuRsrv2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.fpuDp)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 28, value.fpuDs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 30, value.fpuRsrv3)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.fpuMxcsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.fpuMxcsrmask)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 40, value.fpuStmm0)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 56, value.fpuStmm1)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 72, value.fpuStmm2)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 88, value.fpuStmm3)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 104, value.fpuStmm4)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 120, value.fpuStmm5)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 136, value.fpuStmm6)
                    (DarwinMmstReg.typeLayout()).writeBytes(buf, offset + 152, value.fpuStmm7)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 168, value.fpuXmm0)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 184, value.fpuXmm1)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 200, value.fpuXmm2)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 216, value.fpuXmm3)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 232, value.fpuXmm4)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 248, value.fpuXmm5)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 264, value.fpuXmm6)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 280, value.fpuXmm7)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 296, value.fpuXmm8)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 312, value.fpuXmm9)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 328, value.fpuXmm10)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 344, value.fpuXmm11)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 360, value.fpuXmm12)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 376, value.fpuXmm13)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 392, value.fpuXmm14)
                    (DarwinXmmReg.typeLayout()).writeBytes(buf, offset + 408, value.fpuXmm15)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeBytes(buf, offset + 424, value.fpuRsrv4)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 520, value.fpuReserved1)
                }
            }
    }
}

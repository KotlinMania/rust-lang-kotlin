// port-lint: source detect/os/linux/riscv.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.riscv

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.libc.prctl
import io.github.kotlinmania.rustlang.libc.syscall
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.stddetect.detect.arch.riscv.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Initializer
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv
import io.github.kotlinmania.rustlang.stddetect.detect.os.riscv.implyFeatures
import kotlin.experimental.and

// Run-time feature detection for RISC-V on Linux.
//
// On RISC-V, detection using auxv only supports single-letter extensions.
// So, we use riscvHwprobe that supports multi-letter extensions if available.
// <https://www.kernel.org/doc/html/latest/arch/riscv/hwprobe.html>

// See <https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/include/uapi/linux/prctl.h?h=v6.16>
// for runtime status query constants.
public val PR_RISCV_V_GET_CONTROL: CInt = 70

public val PR_RISCV_V_VSTATE_CTRL_ON: CInt = 2

public val PR_RISCV_V_VSTATE_CTRL_CUR_MASK: CInt = 3

// See <https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/arch/riscv/include/uapi/asm/hwprobe.h?h=v6.16>
// for riscvHwprobe struct and hardware probing constants.
public open class RiscvHwprobe(
    public var key: Long,
    public var value: ULong,
) {
    // key is overwritten to -1 if not supported by riscvHwprobe syscall.
    public open fun get(): ULong? = (this.key != -1L).thenSome(this.value)

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvHwprobe> =
            object : TypeLayout<RiscvHwprobe> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): RiscvHwprobe =
                    RiscvHwprobe(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: RiscvHwprobe) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.key)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvHwprobe =
                    RiscvHwprobe(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvHwprobe) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.key)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.value)
                }
            }
    }
}

public val NR_RISCV_HWPROBE: CLong = 258L

public val RISCV_HWPROBE_KEY_BASE_BEHAVIOR: Long = 3L

public val RISCV_HWPROBE_BASE_BEHAVIOR_IMA: ULong = 1uL shl 0

public val RISCV_HWPROBE_KEY_IMA_EXT_0: Long = 4L

public val RISCV_HWPROBE_IMA_FD: ULong = 1uL shl 0

public val RISCV_HWPROBE_IMA_C: ULong = 1uL shl 1

public val RISCV_HWPROBE_IMA_V: ULong = 1uL shl 2

public val RISCV_HWPROBE_EXT_ZBA: ULong = 1uL shl 3

public val RISCV_HWPROBE_EXT_ZBB: ULong = 1uL shl 4

public val RISCV_HWPROBE_EXT_ZBS: ULong = 1uL shl 5

public val RISCV_HWPROBE_EXT_ZICBOZ: ULong = 1uL shl 6

public val RISCV_HWPROBE_EXT_ZBC: ULong = 1uL shl 7

public val RISCV_HWPROBE_EXT_ZBKB: ULong = 1uL shl 8

public val RISCV_HWPROBE_EXT_ZBKC: ULong = 1uL shl 9

public val RISCV_HWPROBE_EXT_ZBKX: ULong = 1uL shl 10

public val RISCV_HWPROBE_EXT_ZKND: ULong = 1uL shl 11

public val RISCV_HWPROBE_EXT_ZKNE: ULong = 1uL shl 12

public val RISCV_HWPROBE_EXT_ZKNH: ULong = 1uL shl 13

public val RISCV_HWPROBE_EXT_ZKSED: ULong = 1uL shl 14

public val RISCV_HWPROBE_EXT_ZKSH: ULong = 1uL shl 15

public val RISCV_HWPROBE_EXT_ZKT: ULong = 1uL shl 16

public val RISCV_HWPROBE_EXT_ZVBB: ULong = 1uL shl 17

public val RISCV_HWPROBE_EXT_ZVBC: ULong = 1uL shl 18

public val RISCV_HWPROBE_EXT_ZVKB: ULong = 1uL shl 19

public val RISCV_HWPROBE_EXT_ZVKG: ULong = 1uL shl 20

public val RISCV_HWPROBE_EXT_ZVKNED: ULong = 1uL shl 21

public val RISCV_HWPROBE_EXT_ZVKNHA: ULong = 1uL shl 22

public val RISCV_HWPROBE_EXT_ZVKNHB: ULong = 1uL shl 23

public val RISCV_HWPROBE_EXT_ZVKSED: ULong = 1uL shl 24

public val RISCV_HWPROBE_EXT_ZVKSH: ULong = 1uL shl 25

public val RISCV_HWPROBE_EXT_ZVKT: ULong = 1uL shl 26

public val RISCV_HWPROBE_EXT_ZFH: ULong = 1uL shl 27

public val RISCV_HWPROBE_EXT_ZFHMIN: ULong = 1uL shl 28

public val RISCV_HWPROBE_EXT_ZIHINTNTL: ULong = 1uL shl 29

public val RISCV_HWPROBE_EXT_ZVFH: ULong = 1uL shl 30

public val RISCV_HWPROBE_EXT_ZVFHMIN: ULong = 1uL shl 31

public val RISCV_HWPROBE_EXT_ZFA: ULong = 1uL shl 32

public val RISCV_HWPROBE_EXT_ZTSO: ULong = 1uL shl 33

public val RISCV_HWPROBE_EXT_ZACAS: ULong = 1uL shl 34

public val RISCV_HWPROBE_EXT_ZICOND: ULong = 1uL shl 35

public val RISCV_HWPROBE_EXT_ZIHINTPAUSE: ULong = 1uL shl 36

public val RISCV_HWPROBE_EXT_ZVE32X: ULong = 1uL shl 37

public val RISCV_HWPROBE_EXT_ZVE32F: ULong = 1uL shl 38

public val RISCV_HWPROBE_EXT_ZVE64X: ULong = 1uL shl 39

public val RISCV_HWPROBE_EXT_ZVE64F: ULong = 1uL shl 40

public val RISCV_HWPROBE_EXT_ZVE64D: ULong = 1uL shl 41

public val RISCV_HWPROBE_EXT_ZIMOP: ULong = 1uL shl 42

public val RISCV_HWPROBE_EXT_ZCA: ULong = 1uL shl 43

public val RISCV_HWPROBE_EXT_ZCB: ULong = 1uL shl 44

public val RISCV_HWPROBE_EXT_ZCD: ULong = 1uL shl 45

public val RISCV_HWPROBE_EXT_ZCF: ULong = 1uL shl 46

public val RISCV_HWPROBE_EXT_ZCMOP: ULong = 1uL shl 47

public val RISCV_HWPROBE_EXT_ZAWRS: ULong = 1uL shl 48

// Excluded because it only reports the existence of `prctl`-based pointer masking control.
// const RISCV_HWPROBE_EXT_SUPM: u64 = 1 << 49;
public val RISCV_HWPROBE_EXT_ZICNTR: ULong = 1uL shl 50

public val RISCV_HWPROBE_EXT_ZIHPM: ULong = 1uL shl 51

public val RISCV_HWPROBE_EXT_ZFBFMIN: ULong = 1uL shl 52

public val RISCV_HWPROBE_EXT_ZVFBFMIN: ULong = 1uL shl 53

public val RISCV_HWPROBE_EXT_ZVFBFWMA: ULong = 1uL shl 54

public val RISCV_HWPROBE_EXT_ZICBOM: ULong = 1uL shl 55

public val RISCV_HWPROBE_EXT_ZAAMO: ULong = 1uL shl 56

public val RISCV_HWPROBE_EXT_ZALRSC: ULong = 1uL shl 57

public val RISCV_HWPROBE_EXT_ZABHA: ULong = 1uL shl 58

public val RISCV_HWPROBE_KEY_CPUPERF_0: Long = 5L

public val RISCV_HWPROBE_MISALIGNED_FAST: ULong = 3uL

public val RISCV_HWPROBE_MISALIGNED_MASK: ULong = 7uL

public val RISCV_HWPROBE_KEY_MISALIGNED_SCALAR_PERF: Long = 9L

public val RISCV_HWPROBE_MISALIGNED_SCALAR_FAST: ULong = 3uL

public val RISCV_HWPROBE_KEY_MISALIGNED_VECTOR_PERF: Long = 10L

public val RISCV_HWPROBE_MISALIGNED_VECTOR_FAST: ULong = 3uL

// syscall returns an unsupported error if riscvHwprobe is not supported,
// so we can safely use this function on older versions of Linux.
public fun riscvHwprobe(out: MutableList<RiscvHwprobe>): Boolean {
    fun riscvHwprobe(pairs: MutPtr, pairCount: SizeT, cpuSetSize: SizeT, cpus: MutPtr, flags: CUint): CLong = syscall(NR_RISCV_HWPROBE, pairs.rawAddress, pairCount.toLong(), cpuSetSize.toLong(), cpus.rawAddress, flags.toLong())
    return riscvHwprobe(out.asMutPtr(), out.size.toULong(), 0uL, MutPtr(0L), 0u) == 0L
}

/**
 * Read list of supported features from (1) the auxiliary vector
 * and (2) the results of `riscvHwprobe` and `prctl` system calls.
 */
public fun detectFeatures(): Initializer {
    var value = Initializer.default()
    var enableFeature =
        { feature, enable ->
            if (enable) {
                value.set(feature.toUInt())
            }
        }
    // Use auxiliary vector to enable single-letter ISA extensions.
    // The values are part of the platform-specific [asm/hwcap.h][hwcap]
    //
    // [hwcap]: https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/arch/riscv/include/uapi/asm/hwcap.h?h=v6.16
    val auxv = auxv().expect("read auxvec")
    // should not fail on RISC-V platform
    var hasI = test(auxv.hwcap, (105u.toUByte() - 97u.toUByte()).into())
    enableFeature(Feature.A, test(auxv.hwcap, (97u.toUByte() - 97u.toUByte()).into()))
    enableFeature(Feature.C, test(auxv.hwcap, (99u.toUByte() - 97u.toUByte()).into()))
    enableFeature(Feature.D, test(auxv.hwcap, (100u.toUByte() - 97u.toUByte()).into()))
    enableFeature(Feature.F, test(auxv.hwcap, (102u.toUByte() - 97u.toUByte()).into()))
    enableFeature(Feature.M, test(auxv.hwcap, (109u.toUByte() - 97u.toUByte()).into()))
    val hasV = test(auxv.hwcap, (118u.toUByte() - 97u.toUByte()).into())
    var isVSet = false
    // Use riscvHwprobe syscall to query more extensions and
    // performance-related capabilities.
    run {
        sealed class Indices {
            public data object BaseBehavior : Indices()
            public data object Extensions : Indices()
            public data object MisalignedScalarPerf : Indices()
            public data object MisalignedVectorPerf : Indices()
            public data object MisalignedScalarPerfFallback : Indices()

            private fun kmDiscriminant(): Int =
                when (this) {
                BaseBehavior -> 0
                Extensions -> 1
                MisalignedScalarPerf -> 2
                MisalignedVectorPerf -> 3
                MisalignedScalarPerfFallback -> 4
                }

            public fun toByte(): Byte = kmDiscriminant().toByte()

            public fun toUByte(): UByte = kmDiscriminant().toUByte()

            public fun toShort(): Short = kmDiscriminant().toShort()

            public fun toUShort(): UShort = kmDiscriminant().toUShort()

            public fun toInt(): Int = kmDiscriminant()

            public fun toUInt(): UInt = kmDiscriminant().toUInt()

            public fun toLong(): Long = kmDiscriminant().toLong()

            public fun toULong(): ULong = kmDiscriminant().toULong()
        }
        val t = mutableListOf(RiscvHwprobe(key = RISCV_HWPROBE_KEY_BASE_BEHAVIOR, value = 0uL), RiscvHwprobe(key = RISCV_HWPROBE_KEY_IMA_EXT_0, value = 0uL), RiscvHwprobe(key = RISCV_HWPROBE_KEY_MISALIGNED_SCALAR_PERF, value = 0uL), RiscvHwprobe(key = RISCV_HWPROBE_KEY_MISALIGNED_VECTOR_PERF, value = 0uL), RiscvHwprobe(key = RISCV_HWPROBE_KEY_CPUPERF_0, value = 0uL))
        if (!riscvHwprobe(t)) {
            break
        }
        // Query scalar misaligned behavior.
        when (val matchVal1 = t[Indices.MisalignedScalarPerf.value.toInt()].get()) {
            null -> {
                when (val matchVal = t[Indices.MisalignedScalarPerfFallback.value.toInt()].get()) {
                    null -> {}
                    else -> {
                        val value1 = matchVal
                        // Deprecated method for fallback
                        enableFeature(Feature.UnalignedScalarMem, value1 and RISCV_HWPROBE_MISALIGNED_MASK == RISCV_HWPROBE_MISALIGNED_FAST)
                    }
                }
            }
            else -> {
                val value2 = matchVal1
                enableFeature(Feature.UnalignedScalarMem, value2 == RISCV_HWPROBE_MISALIGNED_SCALAR_FAST)
            }
        }
        // Query vector misaligned behavior.
        when (val matchVal2 = t[Indices.MisalignedVectorPerf.value.toInt()].get()) {
            null -> {}
            else -> {
                val value3 = matchVal2
                enableFeature(Feature.UnalignedVectorMem, value3 == RISCV_HWPROBE_MISALIGNED_VECTOR_FAST)
            }
        }
        // Query whether "I" base and extensions "M" and "A" (as in the ISA
        // manual version 2.2) are enabled.  "I" base at that time corresponds
        // to "I", "Zicsr", "Zicntr" and "Zifencei" (as in the ISA manual version
        // 20240411).
        // This is a current requirement of
        // `RISCV_HWPROBE_KEY_IMA_EXT_0`-based tests.
        if (t[Indices.BaseBehavior.value.toInt()].get().isNoneOr({ value4 -> value4 and RISCV_HWPROBE_BASE_BEHAVIOR_IMA == 0uL })) {
            break
        }
        hasI = true
        enableFeature(Feature.Zicsr, true)
        enableFeature(Feature.Zicntr, true)
        enableFeature(Feature.Zifencei, true)
        enableFeature(Feature.M, true)
        enableFeature(Feature.A, true)
        // Enable features based on `RISCV_HWPROBE_KEY_IMA_EXT_0`.
        val imaExt0 = t[Indices.Extensions.value.toInt()].get() ?: break
        val test = { mask -> (imaExt0 and mask) != 0uL }
        enableFeature(Feature.D, test(RISCV_HWPROBE_IMA_FD.toUInt()))
        // F is implied.
        enableFeature(Feature.C, test(RISCV_HWPROBE_IMA_C.toUInt()))
        enableFeature(Feature.Zicntr, test(RISCV_HWPROBE_EXT_ZICNTR.toUInt()))
        enableFeature(Feature.Zihpm, test(RISCV_HWPROBE_EXT_ZIHPM.toUInt()))
        enableFeature(Feature.Zihintntl, test(RISCV_HWPROBE_EXT_ZIHINTNTL.toUInt()))
        enableFeature(Feature.Zihintpause, test(RISCV_HWPROBE_EXT_ZIHINTPAUSE.toUInt()))
        enableFeature(Feature.Zimop, test(RISCV_HWPROBE_EXT_ZIMOP.toUInt()))
        enableFeature(Feature.Zicbom, test(RISCV_HWPROBE_EXT_ZICBOM.toUInt()))
        enableFeature(Feature.Zicboz, test(RISCV_HWPROBE_EXT_ZICBOZ.toUInt()))
        enableFeature(Feature.Zicond, test(RISCV_HWPROBE_EXT_ZICOND.toUInt()))
        enableFeature(Feature.Zalrsc, test(RISCV_HWPROBE_EXT_ZALRSC.toUInt()))
        enableFeature(Feature.Zaamo, test(RISCV_HWPROBE_EXT_ZAAMO.toUInt()))
        enableFeature(Feature.Zawrs, test(RISCV_HWPROBE_EXT_ZAWRS.toUInt()))
        enableFeature(Feature.Zabha, test(RISCV_HWPROBE_EXT_ZABHA.toUInt()))
        enableFeature(Feature.Zacas, test(RISCV_HWPROBE_EXT_ZACAS.toUInt()))
        enableFeature(Feature.Ztso, test(RISCV_HWPROBE_EXT_ZTSO.toUInt()))
        enableFeature(Feature.Zba, test(RISCV_HWPROBE_EXT_ZBA.toUInt()))
        enableFeature(Feature.Zbb, test(RISCV_HWPROBE_EXT_ZBB.toUInt()))
        enableFeature(Feature.Zbs, test(RISCV_HWPROBE_EXT_ZBS.toUInt()))
        enableFeature(Feature.Zbc, test(RISCV_HWPROBE_EXT_ZBC.toUInt()))
        enableFeature(Feature.Zbkb, test(RISCV_HWPROBE_EXT_ZBKB.toUInt()))
        enableFeature(Feature.Zbkc, test(RISCV_HWPROBE_EXT_ZBKC.toUInt()))
        enableFeature(Feature.Zbkx, test(RISCV_HWPROBE_EXT_ZBKX.toUInt()))
        enableFeature(Feature.Zknd, test(RISCV_HWPROBE_EXT_ZKND.toUInt()))
        enableFeature(Feature.Zkne, test(RISCV_HWPROBE_EXT_ZKNE.toUInt()))
        enableFeature(Feature.Zknh, test(RISCV_HWPROBE_EXT_ZKNH.toUInt()))
        enableFeature(Feature.Zksed, test(RISCV_HWPROBE_EXT_ZKSED.toUInt()))
        enableFeature(Feature.Zksh, test(RISCV_HWPROBE_EXT_ZKSH.toUInt()))
        enableFeature(Feature.Zkt, test(RISCV_HWPROBE_EXT_ZKT.toUInt()))
        enableFeature(Feature.Zcmop, test(RISCV_HWPROBE_EXT_ZCMOP.toUInt()))
        enableFeature(Feature.Zca, test(RISCV_HWPROBE_EXT_ZCA.toUInt()))
        enableFeature(Feature.Zcf, test(RISCV_HWPROBE_EXT_ZCF.toUInt()))
        enableFeature(Feature.Zcd, test(RISCV_HWPROBE_EXT_ZCD.toUInt()))
        enableFeature(Feature.Zcb, test(RISCV_HWPROBE_EXT_ZCB.toUInt()))
        enableFeature(Feature.Zfh, test(RISCV_HWPROBE_EXT_ZFH.toUInt()))
        enableFeature(Feature.Zfhmin, test(RISCV_HWPROBE_EXT_ZFHMIN.toUInt()))
        enableFeature(Feature.Zfa, test(RISCV_HWPROBE_EXT_ZFA.toUInt()))
        enableFeature(Feature.Zfbfmin, test(RISCV_HWPROBE_EXT_ZFBFMIN.toUInt()))
        // Use prctl (if any) to determine whether the vector extension
        // is enabled on the current thread (assuming the entire process
        // share the same status).  If prctl fails (e.g. QEMU userland emulator
        // as of version 9.2.3), use auxiliary vector to retrieve the default
        // vector status on the process startup.
        val hasVectors = run {
            val vStatus = prctl(PR_RISCV_V_GET_CONTROL)
            if (vStatus >= 0) {
                (vStatus and PR_RISCV_V_VSTATE_CTRL_CUR_MASK) == PR_RISCV_V_VSTATE_CTRL_ON
            } else {
                hasV
            }
        }
        if (hasVectors) {
            enableFeature(Feature.V, test(RISCV_HWPROBE_IMA_V.toUInt()))
            enableFeature(Feature.Zve32x, test(RISCV_HWPROBE_EXT_ZVE32X.toUInt()))
            enableFeature(Feature.Zve32f, test(RISCV_HWPROBE_EXT_ZVE32F.toUInt()))
            enableFeature(Feature.Zve64x, test(RISCV_HWPROBE_EXT_ZVE64X.toUInt()))
            enableFeature(Feature.Zve64f, test(RISCV_HWPROBE_EXT_ZVE64F.toUInt()))
            enableFeature(Feature.Zve64d, test(RISCV_HWPROBE_EXT_ZVE64D.toUInt()))
            enableFeature(Feature.Zvbb, test(RISCV_HWPROBE_EXT_ZVBB.toUInt()))
            enableFeature(Feature.Zvbc, test(RISCV_HWPROBE_EXT_ZVBC.toUInt()))
            enableFeature(Feature.Zvkb, test(RISCV_HWPROBE_EXT_ZVKB.toUInt()))
            enableFeature(Feature.Zvkg, test(RISCV_HWPROBE_EXT_ZVKG.toUInt()))
            enableFeature(Feature.Zvkned, test(RISCV_HWPROBE_EXT_ZVKNED.toUInt()))
            enableFeature(Feature.Zvknha, test(RISCV_HWPROBE_EXT_ZVKNHA.toUInt()))
            enableFeature(Feature.Zvknhb, test(RISCV_HWPROBE_EXT_ZVKNHB.toUInt()))
            enableFeature(Feature.Zvksed, test(RISCV_HWPROBE_EXT_ZVKSED.toUInt()))
            enableFeature(Feature.Zvksh, test(RISCV_HWPROBE_EXT_ZVKSH.toUInt()))
            enableFeature(Feature.Zvkt, test(RISCV_HWPROBE_EXT_ZVKT.toUInt()))
            enableFeature(Feature.Zvfh, test(RISCV_HWPROBE_EXT_ZVFH.toUInt()))
            enableFeature(Feature.Zvfhmin, test(RISCV_HWPROBE_EXT_ZVFHMIN.toUInt()))
            enableFeature(Feature.Zvfbfmin, test(RISCV_HWPROBE_EXT_ZVFBFMIN.toUInt()))
            enableFeature(Feature.Zvfbfwma, test(RISCV_HWPROBE_EXT_ZVFBFWMA.toUInt()))
        }
        isVSet = true
    }
    // Set V purely depending on the auxiliary vector
    // only if no fine-grained vector extension detection is available.
    if (!isVSet) {
        enableFeature(Feature.V, hasV)
    }
    // Handle base ISA.
    // If future RV128I is supported, implement with `enableFeature` here.
    // Note that we should use `targetArch` instead of `targetPointerWidth`
    // to avoid misdetection caused by experimental ABIs such as RV64ILP32.
    enableFeature(Feature.Rv64i, hasI)
    return implyFeatures(value)
}

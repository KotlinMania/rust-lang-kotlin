// port-lint: source detect/os/linux/riscv.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.riscv

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CLong
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.libc.prctl
import io.github.kotlinmania.libc.syscall
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv
import io.github.kotlinmania.rustlang.stddetect.detect.os.riscv.implyFeatures

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
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<RiscvHwprobe> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<RiscvHwprobe> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): RiscvHwprobe =
                    RiscvHwprobe(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: RiscvHwprobe) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr, value.key)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvHwprobe =
                    RiscvHwprobe(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvHwprobe) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset, value.key)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.value)
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
    return RiscvHwprobe(out.asMutPtr(), out.size, 0, MutPtr(0L), 0) == 0
}

/**
 * Read list of supported features from (1) the auxiliary vector
 * and (2) the results of `riscvHwprobe` and `prctl` system calls.
 */
internal fun detectFeatures(): Initializer {
    var value = Cache.Initializer.default()
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
    var hasI = test(auxv.hwcap, (105u - 97u).into())
    enableFeature(Feature::a, test(auxv.hwcap, (97u - 97u).into()))
    enableFeature(Feature::c, test(auxv.hwcap, (99u - 97u).into()))
    enableFeature(Feature::d, test(auxv.hwcap, (100u - 97u).into()))
    enableFeature(Feature::f, test(auxv.hwcap, (102u - 97u).into()))
    enableFeature(Feature::m, test(auxv.hwcap, (109u - 97u).into()))
    val hasV = test(auxv.hwcap, (118u - 97u).into())
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
                        enableFeature(Feature::unalignedScalarMem, value1 and RISCV_HWPROBE_MISALIGNED_MASK == RISCV_HWPROBE_MISALIGNED_FAST)
                    }
                }
            }
            else -> {
                val value2 = matchVal1
                enableFeature(Feature::unalignedScalarMem, value2 == RISCV_HWPROBE_MISALIGNED_SCALAR_FAST)
            }
        }
        // Query vector misaligned behavior.
        when (val matchVal2 = t[Indices.MisalignedVectorPerf.value.toInt()].get()) {
            null -> {}
            else -> {
                val value3 = matchVal2
                enableFeature(Feature::unalignedVectorMem, value3 == RISCV_HWPROBE_MISALIGNED_VECTOR_FAST)
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
        enableFeature(Feature::zicsr, true)
        enableFeature(Feature::zicntr, true)
        enableFeature(Feature::zifencei, true)
        enableFeature(Feature::m, true)
        enableFeature(Feature::a, true)
        // Enable features based on `RISCV_HWPROBE_KEY_IMA_EXT_0`.
        val imaExt0 = t[Indices.Extensions.value.toInt()].get() ?: break
        val test = { mask -> (imaExt0 and mask) != 0 }
        enableFeature(Feature::d, test(RISCV_HWPROBE_IMA_FD.toUInt()))
        // F is implied.
        enableFeature(Feature::c, test(RISCV_HWPROBE_IMA_C.toUInt()))
        enableFeature(Feature::zicntr, test(RISCV_HWPROBE_EXT_ZICNTR.toUInt()))
        enableFeature(Feature::zihpm, test(RISCV_HWPROBE_EXT_ZIHPM.toUInt()))
        enableFeature(Feature::zihintntl, test(RISCV_HWPROBE_EXT_ZIHINTNTL.toUInt()))
        enableFeature(Feature::zihintpause, test(RISCV_HWPROBE_EXT_ZIHINTPAUSE.toUInt()))
        enableFeature(Feature::zimop, test(RISCV_HWPROBE_EXT_ZIMOP.toUInt()))
        enableFeature(Feature::zicbom, test(RISCV_HWPROBE_EXT_ZICBOM.toUInt()))
        enableFeature(Feature::zicboz, test(RISCV_HWPROBE_EXT_ZICBOZ.toUInt()))
        enableFeature(Feature::zicond, test(RISCV_HWPROBE_EXT_ZICOND.toUInt()))
        enableFeature(Feature::zalrsc, test(RISCV_HWPROBE_EXT_ZALRSC.toUInt()))
        enableFeature(Feature::zaamo, test(RISCV_HWPROBE_EXT_ZAAMO.toUInt()))
        enableFeature(Feature::zawrs, test(RISCV_HWPROBE_EXT_ZAWRS.toUInt()))
        enableFeature(Feature::zabha, test(RISCV_HWPROBE_EXT_ZABHA.toUInt()))
        enableFeature(Feature::zacas, test(RISCV_HWPROBE_EXT_ZACAS.toUInt()))
        enableFeature(Feature::ztso, test(RISCV_HWPROBE_EXT_ZTSO.toUInt()))
        enableFeature(Feature::zba, test(RISCV_HWPROBE_EXT_ZBA.toUInt()))
        enableFeature(Feature::zbb, test(RISCV_HWPROBE_EXT_ZBB.toUInt()))
        enableFeature(Feature::zbs, test(RISCV_HWPROBE_EXT_ZBS.toUInt()))
        enableFeature(Feature::zbc, test(RISCV_HWPROBE_EXT_ZBC.toUInt()))
        enableFeature(Feature::zbkb, test(RISCV_HWPROBE_EXT_ZBKB.toUInt()))
        enableFeature(Feature::zbkc, test(RISCV_HWPROBE_EXT_ZBKC.toUInt()))
        enableFeature(Feature::zbkx, test(RISCV_HWPROBE_EXT_ZBKX.toUInt()))
        enableFeature(Feature::zknd, test(RISCV_HWPROBE_EXT_ZKND.toUInt()))
        enableFeature(Feature::zkne, test(RISCV_HWPROBE_EXT_ZKNE.toUInt()))
        enableFeature(Feature::zknh, test(RISCV_HWPROBE_EXT_ZKNH.toUInt()))
        enableFeature(Feature::zksed, test(RISCV_HWPROBE_EXT_ZKSED.toUInt()))
        enableFeature(Feature::zksh, test(RISCV_HWPROBE_EXT_ZKSH.toUInt()))
        enableFeature(Feature::zkt, test(RISCV_HWPROBE_EXT_ZKT.toUInt()))
        enableFeature(Feature::zcmop, test(RISCV_HWPROBE_EXT_ZCMOP.toUInt()))
        enableFeature(Feature::zca, test(RISCV_HWPROBE_EXT_ZCA.toUInt()))
        enableFeature(Feature::zcf, test(RISCV_HWPROBE_EXT_ZCF.toUInt()))
        enableFeature(Feature::zcd, test(RISCV_HWPROBE_EXT_ZCD.toUInt()))
        enableFeature(Feature::zcb, test(RISCV_HWPROBE_EXT_ZCB.toUInt()))
        enableFeature(Feature::zfh, test(RISCV_HWPROBE_EXT_ZFH.toUInt()))
        enableFeature(Feature::zfhmin, test(RISCV_HWPROBE_EXT_ZFHMIN.toUInt()))
        enableFeature(Feature::zfa, test(RISCV_HWPROBE_EXT_ZFA.toUInt()))
        enableFeature(Feature::zfbfmin, test(RISCV_HWPROBE_EXT_ZFBFMIN.toUInt()))
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
            enableFeature(Feature::v, test(RISCV_HWPROBE_IMA_V.toUInt()))
            enableFeature(Feature::zve32x, test(RISCV_HWPROBE_EXT_ZVE32X.toUInt()))
            enableFeature(Feature::zve32f, test(RISCV_HWPROBE_EXT_ZVE32F.toUInt()))
            enableFeature(Feature::zve64x, test(RISCV_HWPROBE_EXT_ZVE64X.toUInt()))
            enableFeature(Feature::zve64f, test(RISCV_HWPROBE_EXT_ZVE64F.toUInt()))
            enableFeature(Feature::zve64d, test(RISCV_HWPROBE_EXT_ZVE64D.toUInt()))
            enableFeature(Feature::zvbb, test(RISCV_HWPROBE_EXT_ZVBB.toUInt()))
            enableFeature(Feature::zvbc, test(RISCV_HWPROBE_EXT_ZVBC.toUInt()))
            enableFeature(Feature::zvkb, test(RISCV_HWPROBE_EXT_ZVKB.toUInt()))
            enableFeature(Feature::zvkg, test(RISCV_HWPROBE_EXT_ZVKG.toUInt()))
            enableFeature(Feature::zvkned, test(RISCV_HWPROBE_EXT_ZVKNED.toUInt()))
            enableFeature(Feature::zvknha, test(RISCV_HWPROBE_EXT_ZVKNHA.toUInt()))
            enableFeature(Feature::zvknhb, test(RISCV_HWPROBE_EXT_ZVKNHB.toUInt()))
            enableFeature(Feature::zvksed, test(RISCV_HWPROBE_EXT_ZVKSED.toUInt()))
            enableFeature(Feature::zvksh, test(RISCV_HWPROBE_EXT_ZVKSH.toUInt()))
            enableFeature(Feature::zvkt, test(RISCV_HWPROBE_EXT_ZVKT.toUInt()))
            enableFeature(Feature::zvfh, test(RISCV_HWPROBE_EXT_ZVFH.toUInt()))
            enableFeature(Feature::zvfhmin, test(RISCV_HWPROBE_EXT_ZVFHMIN.toUInt()))
            enableFeature(Feature::zvfbfmin, test(RISCV_HWPROBE_EXT_ZVFBFMIN.toUInt()))
            enableFeature(Feature::zvfbfwma, test(RISCV_HWPROBE_EXT_ZVFBFWMA.toUInt()))
        }
        isVSet = true
    }
    // Set V purely depending on the auxiliary vector
    // only if no fine-grained vector extension detection is available.
    if (!isVSet) {
        enableFeature(Feature::v, hasV)
    }
    // Handle base ISA.
    // If future RV128I is supported, implement with `enableFeature` here.
    // Note that we should use `targetArch` instead of `targetPointerWidth`
    // to avoid misdetection caused by experimental ABIs such as RV64ILP32.
    enableFeature(Feature::rv64i, hasI)
    return implyFeatures(value)
}

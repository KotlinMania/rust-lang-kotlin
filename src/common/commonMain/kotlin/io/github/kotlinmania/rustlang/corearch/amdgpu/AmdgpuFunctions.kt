package io.github.kotlinmania.rustlang.corearch.amdgpu

public expect fun llvmWorkitemIdX(): UInt

public expect fun llvmWorkitemIdY(): UInt

public expect fun llvmWorkitemIdZ(): UInt

public expect fun llvmWorkgroupIdX(): UInt

public expect fun llvmWorkgroupIdY(): UInt

public expect fun llvmWorkgroupIdZ(): UInt

public expect fun llvmGroupstaticsize(): UInt

public expect fun llvmDispatchId(): ULong

public expect fun llvmWavefrontsize(): UInt

public expect fun llvmSBarrier()

public expect fun llvmSBarrierSignal(barrierType: Int)

public expect fun llvmSBarrierSignalIsfirst(barrierType: Int): Boolean

public expect fun llvmSBarrierWait(barrierType: Short)

public expect fun llvmSGetBarrierState(barrierType: Int): UInt

public expect fun llvmWaveBarrier()

public expect fun llvmSchedBarrier(mask: UInt)

public expect fun llvmSchedGroupBarrier(mask: UInt, size: UInt, syncId: UInt)

public expect fun llvmSSleep(count: UInt)

public expect fun llvmSSethalt(value: UInt): Nothing

public expect fun llvmSGetpc(): Long

public expect fun llvmMbcntLo(value: UInt, init: UInt): UInt

public expect fun llvmMbcntHi(value: UInt, init: UInt): UInt

public expect fun llvmBallot(b: Boolean): ULong

public expect fun llvmInverseBallot(value: ULong): Boolean

public expect fun llvmWaveReduceUmin(value: UInt, strategy: UInt): UInt

public expect fun llvmWaveReduceMin(value: Int, strategy: UInt): Int

public expect fun llvmWaveReduceUmax(value: UInt, strategy: UInt): UInt

public expect fun llvmWaveReduceMax(value: Int, strategy: UInt): Int

public expect fun llvmWaveReduceAdd(value: UInt, strategy: UInt): UInt

public expect fun llvmWaveReduceAnd(value: UInt, strategy: UInt): UInt

public expect fun llvmWaveReduceOr(value: UInt, strategy: UInt): UInt

public expect fun llvmWaveReduceXor(value: UInt, strategy: UInt): UInt

// The following intrinsics can have multiple sizes
public expect fun llvmReadfirstlaneU32(value: UInt): UInt

public expect fun llvmReadfirstlaneU64(value: ULong): ULong

public expect fun llvmReadlaneU32(value: UInt, lane: UInt): UInt

public expect fun llvmReadlaneU64(value: ULong, lane: UInt): ULong

public expect fun llvmWritelaneU32(value: UInt, lane: UInt, default: UInt): UInt

public expect fun llvmWritelaneU64(value: ULong, lane: UInt, default: ULong): ULong

public expect fun llvmEndpgm(): Nothing

public expect fun llvmUpdateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt

public expect fun llvmSMemrealtime(): ULong

public expect fun llvmDsPermute(lane: UInt, value: UInt): UInt

public expect fun llvmDsBpermute(lane: UInt, value: UInt): UInt

public expect fun llvmPerm(src0: UInt, src1: UInt, selector: UInt): UInt

// gfx10
public expect fun llvmPermlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt

// gfx10
public expect fun llvmPermlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt

public expect fun llvmSGetWaveidInWorkgroup(): UInt

// gfx11
public expect fun llvmPermlane64U32(value: UInt): UInt

// gfx12
public expect fun llvmPermlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt

// gfx12
public expect fun llvmPermlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt

public expect fun llvmWaveId(): UInt

// gfx950
public expect fun llvmPermlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt>

// gfx950
public expect fun llvmPermlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt>

/**
 * Returns the x coordinate of the workitem index within the workgroup.
 */
public fun workitemIdX(): UInt = llvmWorkitemIdX()

/**
 * Returns the y coordinate of the workitem index within the workgroup.
 */
public fun workitemIdY(): UInt = llvmWorkitemIdY()

/**
 * Returns the z coordinate of the workitem index within the workgroup.
 */
public fun workitemIdZ(): UInt = llvmWorkitemIdZ()

/**
 * Returns the x coordinate of the workgroup index within the dispatch.
 */
public fun workgroupIdX(): UInt = llvmWorkgroupIdX()

/**
 * Returns the y coordinate of the workgroup index within the dispatch.
 */
public fun workgroupIdY(): UInt = llvmWorkgroupIdY()

/**
 * Returns the z coordinate of the workgroup index within the dispatch.
 */
public fun workgroupIdZ(): UInt = llvmWorkgroupIdZ()

/**
 * Returns the size of statically allocated shared memory for this program in bytes.
 */
public fun groupstaticsize(): UInt = llvmGroupstaticsize()

/**
 * Returns the id of the dispatch that is currently executed.
 */
public fun dispatchId(): ULong = llvmDispatchId()

/**
 * Returns the number of threads in a wavefront.
 *
 * Is always a power of 2.
 */
public fun wavefrontsize(): UInt = llvmWavefrontsize()

/**
 * Synchronize all wavefronts in a workgroup.
 *
 * Each wavefronts in a workgroup waits at the barrier until all wavefronts in the workgroup arrive at a barrier.
 */
public fun sBarrier() {
    llvmSBarrier()
}

/**
 * Signal a specific barrier type.
 *
 * Only for non-named barriers.
 */
public fun sBarrierSignal(barrierType: Int) {
    llvmSBarrierSignal(barrierType)
}

/**
 * Signal a specific barrier type.
 *
 * Only for non-named barriers.
 * Provides access to the sBarrierSignalFirst instruction;
 * additionally ensures that the result value is valid even when
 * the intrinsic is used from a wavefront that is not running in a workgroup.
 */
public fun sBarrierSignalIsfirst(barrierType: Int): Boolean = llvmSBarrierSignalIsfirst(barrierType)

/**
 * Wait for a specific barrier type.
 *
 * Only for non-named barriers.
 */
public fun sBarrierWait(barrierType: Short) {
    llvmSBarrierWait(barrierType)
}

/**
 * Get the state of a specific barrier type.
 *
 * The `barrierType` argument must be uniform, otherwise behavior is undefined.
 */
public fun sGetBarrierState(barrierType: Int): UInt = llvmSGetBarrierState(barrierType)

/**
 * A barrier for only the threads within the current wavefront.
 *
 * Does not result in an instruction but restricts the compiler.
 */
public fun waveBarrier() {
    llvmWaveBarrier()
}

/**
 * Prevent movement of some instruction types.
 *
 * Controls the types of instructions that may be allowed to cross the intrinsic during instruction scheduling.
 * The parameter is a mask for the instruction types that can cross the intrinsic.
 *
 * - 0x0000: No instructions may be scheduled across `schedBarrier`.
 * - 0x0001: All, non-memory, non-side-effect producing instructions may be scheduled across `schedBarrier`, i.e. allow ALU instructions to pass.
 * - 0x0002: VALU instructions may be scheduled across `schedBarrier`.
 * - 0x0004: SALU instructions may be scheduled across `schedBarrier`.
 * - 0x0008: MFMA/WMMA instructions may be scheduled across `schedBarrier`.
 * - 0x0010: All VMEM instructions may be scheduled across `schedBarrier`.
 * - 0x0020: VMEM read instructions may be scheduled across `schedBarrier`.
 * - 0x0040: VMEM write instructions may be scheduled across `schedBarrier`.
 * - 0x0080: All DS instructions may be scheduled across `schedBarrier`.
 * - 0x0100: All DS read instructions may be scheduled across `schedBarrier`.
 * - 0x0200: All DS write instructions may be scheduled across `schedBarrier`.
 * - 0x0400: All Transcendental (e.g. V_EXP) instructions may be scheduled across `schedBarrier`.
 */
public fun schedBarrier(mask: UInt) {
    run {
        check(0u <= mask && mask < (1u shl 11)) { "MASK doesn't fit in 11 bits (unsigned)" }
    }
    llvmSchedBarrier(mask)
}

/**
 * Creates schedule groups with specific properties to create custom scheduling pipelines.
 *
 * The ordering between groups is enforced by the instruction scheduler.
 * The intrinsic applies to the code that precedes the intrinsic.
 * The intrinsic takes three values that control the behavior of the schedule groups.
 *
 * - `mask`: Classify instruction groups using the [schedBarrier] mask values.
 * - `size`: The number of instructions that are in the group.
 * - `syncId`: Order is enforced between groups with matching values.
 *
 * The mask can include multiple instruction types. It is undefined behavior to set values beyond the range of valid masks.
 *
 * Combining multiple `schedGroupBarrier` intrinsics enables an ordering of specific instruction types during instruction scheduling.
 * For example, the following enforces a sequence of 1 VMEM read, followed by 1 VALU instruction, followed by 5 MFMA instructions.
 *
 * ```kotlin
 * // 1 VMEM read
 * schedGroupBarrier(32, 1, 0)
 * // 1 VALU
 * schedGroupBarrier(2, 1, 0)
 * // 5 MFMA
 * schedGroupBarrier(8, 5, 0)
 * ```
 */
public fun schedGroupBarrier(mask: UInt, size: UInt, syncId: UInt) {
    run {
        check(0u <= mask && mask < (1u shl 11)) { "MASK doesn't fit in 11 bits (unsigned)" }
    }
    llvmSchedGroupBarrier(mask, size, syncId)
}

/**
 * Sleeps for approximately `COUNT * 64` cycles.
 *
 * `COUNT` must be a constant.
 * Only the lower 7 bits of `COUNT` are used.
 * If `COUNT == 0x8000`, sleep forever until woken up, or killed.
 */
public fun sSleep(count: UInt) {
    llvmSSleep(count)
}

/**
 * Stop execution of the kernel.
 *
 * This usually signals an error state.
 */
public fun sSethalt(value: UInt): Nothing {
    run {
        check(0u <= value && value < (1u shl 3)) { "VALUE doesn't fit in 3 bits (unsigned)" }
    }
    return llvmSSethalt(value)
}

/**
 * Returns the current process counter.
 *
 * Provides access to the sGetpcB64 instruction, but with the return value sign-extended
 * from the width of the underlying PC hardware register even on processors where the
 * sGetpcB64 instruction returns a zero-extended value.
 */
public fun sGetpc(): Long = llvmSGetpc()

/**
 * Masked bit count, low 32 lanes.
 *
 * Computes the number of bits set in `value`, masked with a thread mask
 * which contains 1 for all active threads less than the current thread within a wavefront.
 * `init` is added to the result.
 */
public fun mbcntLo(value: UInt, init: UInt): UInt = llvmMbcntLo(value, init)

/**
 * Masked bit count, high 32 lanes.
 *
 * Computes the number of bits set in `value`, masked with a thread mask
 * which contains 1 for all active threads less than the current thread within a wavefront.
 * `init` is added to the result.
 */
public fun mbcntHi(value: UInt, init: UInt): UInt = llvmMbcntHi(value, init)

/**
 * Returns a bitfield (`UInt` or `ULong`) containing the result of its i1 argument
 * in all active lanes, and zero in all inactive lanes.
 */
public fun ballot(b: Boolean): ULong = llvmBallot(b)

/**
 * Indexes into the `value` with the current lane id and returns for each lane
 * if the corresponding bit is set.
 *
 * While [ballot] converts a `Boolean` to a mask, `inverseBallot` converts a mask back to a `Boolean`.
 * This means `inverseBallot(ballot(b)) == b`.
 * The inverse of `ballot(inverseBallot(value)) ~ = value` is not always true as inactive lanes are set to zero by `ballot`.
 */
public fun inverseBallot(value: ULong): Boolean = llvmInverseBallot(value)

/**
 * Performs an arithmetic min reduction on the unsigned values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceUmin(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceUmin(value, strategy)
}

/**
 * Performs an arithmetic min reduction on the signed values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceMin(value: Int, strategy: UInt): Int {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceMin(value, strategy)
}

/**
 * Performs an arithmetic max reduction on the unsigned values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceUmax(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceUmax(value, strategy)
}

/**
 * Performs an arithmetic max reduction on the signed values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceMax(value: Int, strategy: UInt): Int {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceMax(value, strategy)
}

/**
 * Performs an arithmetic add reduction on the values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceAdd(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceAdd(value, strategy)
}

/**
 * Performs a logical and reduction on the unsigned values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceAnd(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceAnd(value, strategy)
}

/**
 * Performs a logical or reduction on the unsigned values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceOr(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceOr(value, strategy)
}

/**
 * Performs a logical xor reduction on the unsigned values provided by each lane in the wavefront.
 *
 * The `STRATEGY` argument is a hint for the reduction strategy.
 * - 0: Target default preference
 * - 1: Iterative strategy
 * - 2: DPP
 *
 * If target does not support the DPP operations (e.g. gfx6/7), reduction will be performed using default iterative strategy.
 */
public fun waveReduceXor(value: UInt, strategy: UInt): UInt {
    run {
        check(strategy <= 2u)
    }
    return llvmWaveReduceXor(value, strategy)
}

// The following intrinsics can have multiple sizes

/**
 * Get `value` from the first active lane in the wavefront.
 */
public fun readfirstlaneU32(value: UInt): UInt = llvmReadfirstlaneU32(value)

/**
 * Get `value` from the first active lane in the wavefront.
 */
public fun readfirstlaneU64(value: ULong): ULong = llvmReadfirstlaneU64(value)

/**
 * Get `value` from the lane at index `lane` in the wavefront.
 *
 * The lane argument must be uniform across the currently active threads
 * of the current wavefront. Otherwise, the result is undefined.
 */
public fun readlaneU32(value: UInt, lane: UInt): UInt = llvmReadlaneU32(value, lane)

/**
 * Get `value` from the lane at index `lane` in the wavefront.
 *
 * The lane argument must be uniform across the currently active threads
 * of the current wavefront. Otherwise, the result is undefined.
 */
public fun readlaneU64(value: ULong, lane: UInt): ULong = llvmReadlaneU64(value, lane)

/**
 * Return `value` for the lane at index `lane` in the wavefront.
 * Return `default` for all other lanes.
 *
 * The value to write and lane select arguments must be uniform across the
 * currently active threads of the current wavefront. Otherwise, the result is
 * undefined.
 *
 * `value` is the value returned by `lane`.
 * `default` is the value returned by all lanes other than `lane`.
 */
public fun writelaneU32(value: UInt, lane: UInt, default: UInt): UInt = llvmWritelaneU32(value, lane, default)

/**
 * Return `value` for the lane at index `lane` in the wavefront.
 * Return `default` for all other lanes.
 *
 * The value to write and lane select arguments must be uniform across the
 * currently active threads of the current wavefront. Otherwise, the result is
 * undefined.
 *
 * `value` is the value returned by `lane`.
 * `default` is the value returned by all lanes other than `lane`.
 */
public fun writelaneU64(value: ULong, lane: UInt, default: ULong): ULong = llvmWritelaneU64(value, lane, default)

/**
 * Stop execution of the wavefront.
 *
 * This usually signals the end of a successful execution.
 */
public fun endpgm(): Nothing = llvmEndpgm()

/**
 * The `updateDpp` intrinsic represents the `update.dpp` operation in AMDGPU.
 * It takes an old value, a source operand, a DPP control operand, a row mask, a bank mask, and a bound control.
 * This operation is equivalent to a sequence of `vMovB32` operations.
 *
 * `llvm.amdgcn.update.dpp.Int<old><src><dppCtrl><rowMask><bankMask><boundCtrl>`
 * Should be equivalent to:
 * ```kotlin
 * vMovB32<dest><old> vMovB32<dest><src><dppCtrl><rowMask><bankMask><boundCtrl>
 * ```
 */
public fun updateDpp(old: UInt, src: UInt, dppCtrl: UInt, rowMask: UInt, bankMask: UInt, boundControl: Boolean): UInt = llvmUpdateDpp(old, src, dppCtrl, rowMask, bankMask, boundControl)

/**
 * Measures time based on a fixed frequency.
 *
 * Provides a real-time clock counter that runs at constant speed (typically 100 MHz) independent of ALU clock speeds.
 * The clock is consistent across the chip, so can be used for measuring between different wavefronts.
 */
public fun sMemrealtime(): ULong = llvmSMemrealtime()

/**
 * Scatter data across all lanes in a wavefront.
 *
 * Writes `value` to the lane `lane`.
 *
 * Reading from inactive lanes returns `0`.
 * In case multiple values get written to the same `lane`, the value from the source lane with the higher index is taken.
 */
public fun dsPermute(lane: UInt, value: UInt): UInt = llvmDsPermute(lane, value)

/**
 * Gather data across all lanes in a wavefront.
 *
 * Returns the `value` given to `dsPermute` by lane `lane`.
 *
 * Reading from inactive lanes returns `0`.
 */
public fun dsBpermute(lane: UInt, value: UInt): UInt = llvmDsBpermute(lane, value)

/**
 * Permute a 64-bit value.
 *
 * `selector` selects between different patterns in which the 64-bit values represented by `src0` and `src1` are permuted.
 */
public fun perm(src0: UInt, src1: UInt, selector: UInt): UInt = llvmPerm(src0, src1, selector)

// gfx10

/**
 * Performs arbitrary gather-style operation within a row (16 contiguous lanes) of the second input operand.
 *
 * The third and fourth inputs must be uniform across the current wavefront.
 * These are combined into a single 64-bit value representing lane selects used to swizzle within each row.
 */
public fun permlane16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt = llvmPermlane16U32(old, src0, src1, src2, fi, boundControl)

// gfx10

/**
 * Performs arbitrary gather-style operation across two rows (16 contiguous lanes) of the second input operand.
 *
 * The third and fourth inputs must be uniform across the current wavefront.
 * These are combined into a single 64-bit value representing lane selects used to swizzle within each row.
 */
public fun permlanex16U32(old: UInt, src0: UInt, src1: UInt, src2: UInt, fi: Boolean, boundControl: Boolean): UInt = llvmPermlanex16U32(old, src0, src1, src2, fi, boundControl)

/**
 * Get the index of the current wavefront in the workgroup.
 */
public fun sGetWaveidInWorkgroup(): UInt = llvmSGetWaveidInWorkgroup()

// gfx11

/**
 * Swap `value` between upper and lower 32 lanes in a wavefront.
 *
 * Does nothing for wave32.
 */
public fun permlane64U32(value: UInt): UInt = llvmPermlane64U32(value)

// gfx12

/**
 * Performs arbitrary gather-style operation within a row (16 contiguous lanes) of the second input operand.
 *
 * In contrast to [permlane16U32], allows each lane to specify its own gather lane.
 */
public fun permlane16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt = llvmPermlane16Var(old, src0, src1, fi, boundControl)

// gfx12

/**
 * Performs arbitrary gather-style operation across two rows (16 contiguous lanes) of the second input operand.
 *
 * In contrast to [permlanex16U32], allows each lane to specify its own gather lane.
 */
public fun permlanex16Var(old: UInt, src0: UInt, src1: UInt, fi: Boolean, boundControl: Boolean): UInt = llvmPermlanex16Var(old, src0, src1, fi, boundControl)

/**
 * Get the index of the current wavefront in the workgroup.
 */
public fun waveId(): UInt = llvmWaveId()

// gfx950

/**
 * Provide direct access to `vPermlane16SwapB32` instruction on supported targets.
 *
 * Swaps the values across lanes of first 2 operands.
 * Odd rows of the first operand are swapped with even rows of the second operand (one row is 16 lanes).
 * Returns a pair for the swapped registers.
 * The first element of the return corresponds to the swapped element of the first argument.
 */
public fun permlane16Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> = llvmPermlane16Swap(vdstOld, vsrcSrc0, fi, boundControl)

// gfx950

/**
 * Provide direct access to `vPermlane32SwapB32` instruction on supported targets.
 *
 * Swaps the values across lanes of first 2 operands.
 * Rows 2 and 3 of the first operand are swapped with rows 0 and 1 of the second operand (one row is 16 lanes).
 * Returns a pair for the swapped registers.
 * The first element of the return corresponds to the swapped element of the first argument.
 */
public fun permlane32Swap(vdstOld: UInt, vsrcSrc0: UInt, fi: Boolean, boundControl: Boolean): Pair<UInt, UInt> = llvmPermlane32Swap(vdstOld, vsrcSrc0, fi, boundControl)

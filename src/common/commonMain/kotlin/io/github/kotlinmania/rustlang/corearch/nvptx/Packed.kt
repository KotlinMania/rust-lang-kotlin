// port-lint: source nvptx/packed.rs
package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.fmt.Result
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.Mem
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.debugSimdFinish

// NVPTX Packed data types (SIMD)
//
// Packed Data Types is what PTX calls SIMD types. See [PTX ISA (Packed Data Types)](https://docs.nvidia.com/cuda/parallel-thread-execution/#packed-data-types) for a full reference.

public expect fun llvmF16x2Minimum(a: F16x2, b: F16x2): F16x2

public expect fun llvmF16x2Maximum(a: F16x2, b: F16x2): F16x2

/**
 * PTX-specific 32-bit wide floating point (f16 x 2) vector type
 */
public open class F16x2(
    public var value: List<F16>,
) {
    public var field0: List<F16>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): F16x2 = F16x2(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): List<F16> = (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): List<F16> = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "f16x2", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<F16x2> =
            object : TypeLayout<F16x2> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): F16x2 =
                    F16x2(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: F16x2) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): F16x2 =
                    F16x2(
                        (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: F16x2) {
                    (fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, 2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: F16): F16x2 = Simd.simdSplat(value)

        public fun from(simd: Simd<F16>): F16x2 = Mem.transmute(simd)
    }
}

public fun fromSimdF16(simd: F16x2): Simd<F16> = Mem.transmute(simd)

/**
 * Add two values, round to nearest even
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-add>
 *
 * Corresponds to the CUDA C intrinsics:
 *  - [`hadd2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g921c795176eaa31265bd80ef4fe4b8e6)
 *  - [`hadd2Rn`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g6cd8ddb2c3d670e1a10c3eb2e7644f82)
 */
public fun f16x2Add(a: F16x2, b: F16x2): F16x2 = simdAdd(a, b)

/**
 * Subtract two values, round to nearest even
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-sub>
 *
 * Corresponds to the CUDA C intrinsics:
 *  - [`hsub2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1ga5536c9c3d853d8c8b9de60e18b41e54)
 *  - [`hsub2Rn`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g8adc164c68d553354f749f0f0645a874)
 */
public fun f16x2Sub(a: F16x2, b: F16x2): F16x2 = simdSub(a, b)

/**
 * Multiply two values, round to nearest even
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-mul>
 *
 * Corresponds to the CUDA C intrinsics:
 *  - [`hmul2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g70de3f2ee48babe4e0969397ac17708e)
 *  - [`hmul2Rn`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g99f8fe23a4b4c6898d6faf999afaa76e)
 */
public fun f16x2Mul(a: F16x2, b: F16x2): F16x2 = simdMul(a, b)

/**
 * Fused multiply-add, round to nearest even
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-fma>
 *
 * Corresponds to the CUDA C intrinsic [`hfma2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g43628ba21ded8b1e188a367348008dab)
 */
public fun f16x2Fma(a: F16x2, b: F16x2, c: F16x2): F16x2 = simdFma(a, b, c)

/**
 * Arithmetic negate
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-neg>
 *
 * Corresponds to the CUDA C intrinsic [`hneg2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Arithmetic.html#groupCudaMathHalf2Arithmetic1g90e216094729014f541fa1e5a5aaeaf7)
 */
public fun f16x2Neg(a: F16x2): F16x2 = simdNeg(a)

/**
 * Find the minimum of two values
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-min>
 *
 * Corresponds to the CUDA C intrinsic [`hmin2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Comparison.html#groupCudaMathHalf2Comparison1g9e17a33f96061804166f3fbd395422b6)
 */
public fun f16x2Min(a: F16x2, b: F16x2): F16x2 = simdMinimumNumberNsz(a, b)

/**
 * Find the minimum of two values, NaNs pass through.
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-min>
 *
 * Corresponds to the CUDA C intrinsic [`hmin2Nan`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Comparison.html#groupCudaMathHalf2Comparison1g8bb8f58e9294cc261d2f42c4d5aecd6b)
 */
public fun f16x2MinNan(a: F16x2, b: F16x2): F16x2 = llvmF16x2Minimum(a, b)

/**
 * Find the maximum of two values
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-max>
 *
 * Corresponds to the CUDA C intrinsic [`hmax2`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Comparison.html#groupCudaMathHalf2Comparison1g59fc7fc7975d8127b202444a05e57e3d)
 */
public fun f16x2Max(a: F16x2, b: F16x2): F16x2 = simdMaximumNumberNsz(a, b)

/**
 * Find the maximum of two values, NaNs pass through.
 *
 * <https://docs.nvidia.com/cuda/parallel-thread-execution/#half-precision-floating-point-instructions-max>
 *
 * Corresponds to the CUDA C intrinsic [`hmax2Nan`](https://docs.nvidia.com/cuda/cuda-math-api/groupCudaMathHalf2Comparison.html#groupCudaMathHalf2Comparison1g41623db7850e3074fd9daa80a14c3897)
 */
public fun f16x2MaxNan(a: F16x2, b: F16x2): F16x2 = llvmF16x2Maximum(a, b)

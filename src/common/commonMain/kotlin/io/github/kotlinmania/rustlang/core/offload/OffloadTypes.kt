@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.offload

import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Defines how execution units access memory regions.
 *
 * # Safety
 *
 * Implementations must guarantee that generated views are disjoint.
 *
 * Implementations must also implement [checkLaunch] correctly since it is soundness-critical.
 *
 * [checkLaunch]: PartitioningStrategy.checkLaunch
 */
public interface PartitioningStrategy<out View, out ViewMut> {
    /**
     * Returns the execution index of the current unit.
     */
    public fun index(): Int

    /**
     * Returns a read-only view of the region for the current execution context.
     *
     * # Safety
     *
     * `ptr` must point to `len` valid, initialized elements of type `T`.
     * The memory must stay valid for scope `a`.
     */
    public fun get(ptr: NonNull, len: Int): View?

    /**
     * Returns a mutable view of the region for the current execution context.
     *
     * # Safety
     *
     * `ptr` must point to `len` valid, initialized elements of type `T`.
     * The memory must stay valid for scope `a`.
     * The returned view must be disjoint from all other active views.
     */
    public fun getMut(ptr: NonNull, len: Int): ViewMut?

    /**
     * Checks that a kernel using this strategy can be launched with `len`
     * elements on a `grid` by `block` launch configuration.
     *
     * This is called automatically before the kernel is launched.
     *
     * # Soundness
     *
     * This check is soundness-critical. It may only return `Ok` unconditionally
     * if and only if a launch is valid for arbitrary `len`, `grid` and `block` values.
     * If there is any configuration for which this strategy would produce incorrect
     * results or cause undefined behavior, it must return a [LaunchError] instead.
     */
    public fun checkLaunch(len: Int, grid: UIntArray, block: UIntArray): Result<Unit, LaunchError>
}

// Launch validation helpers.

/**
 * Implementation detail of [offload]: validates a [Region] argument against
 * the launch configuration of an offload call.
 */
public interface RegionLaunchCheck {
    /**
     * Panics if the region's partitioning strategy rejects the launch.
     */
    public fun offloadCheckLaunch(grid: UIntArray, block: UIntArray)
}

/**
 * no-op launch check for kernel arguments that are not [Region]s.
 */
public interface DefaultLaunchCheck {
    /**
     * Does nothing.
     */
    public fun offloadCheckLaunch(grid: UIntArray, block: UIntArray)
}

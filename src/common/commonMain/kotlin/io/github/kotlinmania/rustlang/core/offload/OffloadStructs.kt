@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.offload

import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// Region & Partitioning Strategy

/**
 * Error returned by [PartitioningStrategy.checkLaunch] when a launch
 * configuration is not compatible with a partitioning strategy.
 */
public open class LaunchError(
    public var message: String,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append(this.message); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        /**
         * Creates a new [LaunchError] with the given message.
         */
        public fun new(message: String): LaunchError = LaunchError(message = message)
    }
}

/**
 * A memory region bound to a partitioning strategy.
 */
public open class Region<T, S : PartitioningStrategy<View, ViewMut>, View, ViewMut>(
    public var ptr: NonNull,
    public var len: Int,
    public var marker: Unit,
) : RegionLaunchCheck {
    /**
     * Returns a read-only view for the current execution context.
     */
    public open fun get(getSFn: (NonNull, Int) -> View?): View? {
        // SAFETY: `self.ptr` points to `self.len` valid elements for scope `a`.
        return getSFn(this.ptr, this.len)
    }

    /**
     * Returns a mutable view for the current execution context.
     */
    public open fun getMut(getMutSFn: (NonNull, Int) -> ViewMut?): ViewMut? {
        // SAFETY: `self.ptr` points to `self.len` valid elements for scope `a`.
        // The strategy guarantees that the returned view is disjoint.
        return getMutSFn(this.ptr, this.len)
    }

    /**
     * Checks that this region can be launched with the given configuration.
     *
     * Called automatically by the [offload] macro before launching a kernel.
     */
    public open fun checkLaunch(grid: UIntArray, block: UIntArray, checkLaunchSFn: (Int, UIntArray, UIntArray) -> Result<Unit, LaunchError>): Result<Unit, LaunchError> = checkLaunchSFn(this.len, grid, block)

    /**
     * Reborrows the region, producing a new region that aliases the same memory with
     * the scope of the borrow.
     */
    public open fun reborrow(): Region<T, S, View, ViewMut> = Region(ptr = this.ptr, len = this.len, marker = Unit)

    public override fun offloadCheckLaunch(grid: UIntArray, block: UIntArray) {
        when (val matchVal = this.checkLaunch(grid, block)) {
            is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                error("offload launch is not supported by the region's partitioning strategy: $err")
            }
            else -> {}
        }
    }

    public companion object {
        /**
         * Creates a new partitioned region from data convertible into a [RawRegion].
         */
        public fun <View, ViewMut, T, S : PartitioningStrategy<View, ViewMut>> new(data: RawRegion<T>): Region<T, S, View, ViewMut> {
            val raw = data
            return Region(ptr = raw.ptr, len = raw.len, marker = Unit)
        }
    }
}

/**
 * Raw representation used to build a [Region] from common aggregate types.
 */
public open class RawRegion<T>(
    public var ptr: NonNull,
    public var len: Int,
    public var marker: Unit,
) {
    public companion object {
        public fun <T> from(data: MutableList<T>): RawRegion<T> {
            // SAFETY: `data.asMutPtr()` is non-null, because it is derived from a reference.
            val ptr = NonNull.newUnchecked(data.asMutPtr())
            return RawRegion(ptr = ptr, len = data.size, marker = Unit)
        }

        public fun <T> from(data: List<T>, n: Int): RawRegion<T> {
            // SAFETY: `data.asMutPtr()` is non-null, because it is derived from a reference.
            val ptr = NonNull.newUnchecked(data.asMutPtr())
            return RawRegion(ptr = ptr, len = n, marker = Unit)
        }
    }
}

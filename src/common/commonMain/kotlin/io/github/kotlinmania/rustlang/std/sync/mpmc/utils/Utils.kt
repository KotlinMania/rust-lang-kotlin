// port-lint: source sync/mpmc/utils.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.utils

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.std.thread.functions.yieldNow

// Starting from Intel's Sandy Bridge, spatial prefetcher is now pulling pairs of 64-byte cache
// lines at a time, so we have to align to 128 bytes rather than 64.
//
// Sources:
// - https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-optimization-manual.pdf
// - https://github.com/facebook/folly/blob/1b5288e6eea6df074758f877c849b6e73bbb9fbb/folly/lang/Align.h#L107
//
// ARM's big.LITTLE architecture has asymmetric cores and "big" cores have 128-byte cache line size.
//
// Sources:
// - https://www.mono-project.com/news/2016/09/12/arm64-icache/
//
// powerpc64 has 128-byte cache line size.
//
// Sources:
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuPpc64x.go#L9
// arm, mips and mips64 have 32-byte cache line size.
//
// Sources:
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuArm.go#L7
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuMips.go#L7
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuMipsle.go#L7
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuMips64x.go#L9
// s390x has 256-byte cache line size.
//
// Sources:
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuS390x.go#L7
// x86, wasm and riscv have 64-byte cache line size.
//
// Sources:
// - https://github.com/golang/go/blob/dda2991c2ea0c5914714469c4defc2562a907230/src/internal/cpu/cpuX86.go#L9
// - https://github.com/golang/go/blob/3dd58676054223962cd915bb0934d1f9f489d4d2/src/internal/cpu/cpuWasm.go#L7
// - https://github.com/golang/go/blob/5e31f78c8a4ed1b872ddc194f0cd1ae931b37d7e/src/internal/cpu/cpuRiscv64.go#L7
//
// All others are assumed to have 64-byte cache line size.

/**
 * Pads and aligns a value to the length of a cache line.
 */
public open class CachePadded<T>(
    public var value: T,
) {
    public open fun clone(): CachePadded<T> = CachePadded(value)

    public open fun deref(): T = this.value

    public open fun derefMut(): T = this.value

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<CachePadded<T>> =
            object : TypeLayout<CachePadded<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): CachePadded<T> =
                    CachePadded(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CachePadded<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CachePadded<T> =
                    CachePadded(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CachePadded<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Pads and aligns a value to the length of a cache line.
         */
        public fun <T> new(value: T): CachePadded<T> = CachePadded(value = value)
    }
}

public val SPIN_LIMIT: UInt = 6u

/**
 * Performs quadratic backoff in spin loops.
 */
public open class Backoff internal constructor(
    stepCtorArg: Cell<UInt>,
) {
    public var step: Cell<UInt> = stepCtorArg
        internal set

    /**
     * Backs off using lightweight spinning.
     *
     * This method should be used for retrying an operation because another thread made
     * progress. i.e. on CAS failure.
     */
    public open fun spinLight() {
        val step = this.step.get().min(SPIN_LIMIT)
        for (unused in 0 until step.pow(2)) {
            kmSpinLoopHint()
        }
        this.step.set(this.step.get() + 1)
    }

    /**
     * Backs off using heavyweight spinning.
     *
     * This method should be used in blocking loops where parking the thread is not an option.
     */
    public open fun spinHeavy() {
        if (this.step.get() <= SPIN_LIMIT) {
            for (unused in 0 until this.step.get().pow(2)) {
                kmSpinLoopHint()
            }
        } else {
            yieldNow()
        }
        this.step.set(this.step.get() + 1)
    }

    public companion object {
        public operator fun invoke(
            step: Cell<UInt>,
        ): Backoff = Backoff(
            stepCtorArg = step,
        )

        /**
         * Creates a new `Backoff`.
         */
        public fun new(): Backoff = Backoff(step = Cell.new(0))
    }
}

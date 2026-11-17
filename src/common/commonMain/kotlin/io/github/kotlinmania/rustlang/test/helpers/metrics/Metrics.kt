// port-lint: source helpers/metrics.rs
package io.github.kotlinmania.rustlang.test.helpers.metrics

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

// Benchmark metrics.

public open class Metric(
    public var value: Double,
    public var noise: Double,
) {
    public open fun clone(): Metric = Metric(value, noise)

    public companion object {
        public fun typeLayout(): TypeLayout<Metric> =
            object : TypeLayout<Metric> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Metric =
                    Metric(
                        (PrimitiveLayouts.F64).readNative(addr),
                        (PrimitiveLayouts.F64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Metric) {
                    (PrimitiveLayouts.F64).writeNative(addr, value.value)
                    (PrimitiveLayouts.F64).writeNative(addr + 8L, value.noise)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Metric =
                    Metric(
                        (PrimitiveLayouts.F64).readBytes(buf, offset),
                        (PrimitiveLayouts.F64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Metric) {
                    (PrimitiveLayouts.F64).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.F64).writeBytes(buf, offset + 8, value.noise)
                }
            }

        public fun new(value: Double, noise: Double): Metric = Metric(value = value, noise = noise)
    }
}

public open class MetricMap(
    public var value: MutableMap<String, Metric>,
) {
    public var field0: MutableMap<String, Metric>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): MetricMap = MetricMap(value)

    /**
     * Insert a named `value` (+/- `noise`) metric into the map. The value
     * must be non-negative. The `noise` indicates the uncertainty of the
     * metric, which doubles as the "noise range" of acceptable
     * pairwise-regressions on this named value, when comparing from one
     * metric to the next using `compareToOld`.
     *
     * If `noise` is positive, then it means this metric is of a value
     * you want to see grow smaller, so a change larger than `noise` in the
     * positive direction represents a regression.
     *
     * If `noise` is negative, then it means this metric is of a value
     * you want to see grow larger, so a change larger than `noise` in the
     * negative direction represents a regression.
     */
    public open fun insertMetric(name: String, value: Double, noise: Double) {
        val m = Metric(value = value, noise = noise)
        this.value.put(name.toOwned(), m)
    }

    public open fun fmtMetrics(): String {
        val v = this.value.iter().map({ (k, v) -> "$k: ${v.value} (+/- ${v.noise})" }).collect<Vec>()
        return v.join(", ")
    }

    public companion object {
        public fun new(): MetricMap = MetricMap(mutableMapOf())
    }
}

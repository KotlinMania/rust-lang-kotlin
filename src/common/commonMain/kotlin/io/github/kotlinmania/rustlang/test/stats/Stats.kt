// port-lint: source stats.rs
package io.github.kotlinmania.rustlang.test.stats

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import kotlin.math.abs
import kotlin.math.floor

public fun localSort(v: MutableList<Double>) {
    v.sortBy({ x: Double, y: Double -> x.totalCmp(y) })
}

/**
 * Trait that provides simple descriptive statistics on a univariate set of numeric samples.
 */
public interface Stats {
    /**
     * Sum of the samples.
     *
     * Note: this method sacrifices performance at the altar of accuracy
     * Depends on IEEE 754 arithmetic guarantees. See proof of correctness at:
     * ["Adaptive Precision Floating-Point Arithmetic and Fast Robust Geometric
     * Predicates"][paper]
     *
     * [paper]: https://www.cs.cmu.edu/~quake-papers/robust-arithmetic.ps
     */
    public fun sum(): Double

    /**
     * Minimum value of the samples.
     */
    public fun min(): Double

    /**
     * Maximum value of the samples.
     */
    public fun max(): Double

    /**
     * Arithmetic mean (average) of the samples: sum divided by sample-count.
     *
     * See: <https://en.wikipedia.org/wiki/ArithmeticMean>
     */
    public fun mean(): Double

    /**
     * Median of the samples: value separating the lower half of the samples from the higher half.
     * Equal to `self.percentile(50.0)`.
     *
     * See: <https://en.wikipedia.org/wiki/Median>
     */
    public fun median(): Double

    /**
     * Variance of the samples: bias-corrected mean of the squares of the differences of each
     * sample from the sample mean. Note that this calculates the _sample variance_ rather than the
     * population variance, which is assumed to be unknown. It therefore corrects the `(n - 1) / n`
     * bias that would appear if we calculated a population variance, by dividing by `(n - 1)` rather
     * than `n`.
     *
     * See: <https://en.wikipedia.org/wiki/Variance>
     */
    public fun variable(): Double

    /**
     * Standard deviation: the square root of the sample variance.
     *
     * Note: this is not a robust statistic for non-normal distributions. Prefer the
     * `medianAbsDev` for unknown distributions.
     *
     * See: <https://en.wikipedia.org/wiki/StandardDeviation>
     */
    public fun stdDev(): Double

    /**
     * Standard deviation as a percent of the mean value. See `stdDev` and `mean`.
     *
     * Note: this is not a robust statistic for non-normal distributions. Prefer the
     * `medianAbsDevPct` for unknown distributions.
     */
    public fun stdDevPct(): Double

    /**
     * Scaled median of the absolute deviations of each sample from the sample median. This is a
     * robust (distribution-agnostic) estimator of sample variability. Use this in preference to
     * `stdDev` if you cannot assume your sample is normally distributed. Note that this is scaled
     * by the constant `1.4826` to allow its use as a consistent estimator for the standard
     * deviation.
     *
     * See: <https://en.wikipedia.org/wiki/MedianAbsoluteDeviation>
     */
    public fun medianAbsDev(): Double

    /**
     * Median absolute deviation as a percent of the median. See `medianAbsDev` and `median`.
     */
    public fun medianAbsDevPct(): Double

    /**
     * Percentile: the value below which `pct` percent of the values in `self` fall. For example,
     * percentile(95.0) will return the value `v` such that 95% of the samples `s` in `self`
     * satisfy `s <= v`.
     *
     * Calculated by linear interpolation between closest ranks.
     *
     * See: <https://en.wikipedia.org/wiki/Percentile>
     */
    public fun percentile(pct: Double): Double

    /**
     * Quartiles of the sample: three values that divide the sample into four equal groups, each
     * with 1/4 of the data. The middle value is the median. See `median` and `percentile`. This
     * function may calculate the 3 quartiles more efficiently than 3 calls to `percentile`, but
     * is otherwise equivalent.
     *
     * See also: <https://en.wikipedia.org/wiki/Quartile>
     */
    public fun quartiles(): Triple<Double, Double, Double>

    /**
     * Inter-quartile range: the difference between the 25th percentile (1st quartile) and the 75th
     * percentile (3rd quartile). See `quartiles`.
     *
     * See also: <https://en.wikipedia.org/wiki/InterquartileRange>
     */
    public fun iqr(): Double
}

/**
 * Extracted collection of all the summary statistics of a sample set.
 */
public open class Summary(
    public var sum: Double,
    public var min: Double,
    public var max: Double,
    public var mean: Double,
    public var median: Double,
    public var variable: Double,
    public var stdDev: Double,
    public var stdDevPct: Double,
    public var medianAbsDev: Double,
    public var medianAbsDevPct: Double,
    public var quartiles: Triple<Double, Double, Double>,
    public var iqr: Double,
) {
    public open fun clone(): Summary = Summary(sum, min, max, mean, median, variable, stdDev, stdDevPct, medianAbsDev, medianAbsDevPct, quartiles, iqr)

    public companion object {
        /**
         * Constructs a new summary of a sample set.
         */
        public fun new(samples: List<Double>): Summary = Summary(sum = samples.sum(), min = samples.min(), max = samples.max(), mean = samples.mean(), median = samples.median(), variable = samples.variable(), stdDev = samples.stdDev(), stdDevPct = samples.stdDevPct(), medianAbsDev = samples.medianAbsDev(), medianAbsDevPct = samples.medianAbsDevPct(), quartiles = samples.quartiles(), iqr = samples.iqr())
    }
}

// Note #11059 handle NaN, inf and overflow
public fun List<Double>.sum(): Double {
    val partials = mutableListOf()
    for (x in this) {
        var x1 = x
        var j = 0
        // This inner loop applies `hi`/`lo` summation to each
        // partial so that the list of partial sums remains exact.
        for (i in 0 until partials.size) {
            val localStorageL149C16 = AddressableLocal(partials[i], PrimitiveLayouts.F64)
            val y by localStorageL149C16
            if (x1.abs() < abs(y)) {
                swap(x1, RustMutableReference(localStorageL149C16.asMutPtr(), localStorageL149C16.layout))
            }
            // Rounded `x + y` is stored in `hi` with round-off stored in
            // `lo`. Together `hi + lo` are exactly equal to `x + y`.
            val hi = x1 + y
            val lo = y - (hi - x1)
            if (lo != 0.0) {
                partials[j] = lo
                j += 1
            }
            x1 = hi
        }
        if (j >= partials.size) {
            partials.add(x1)
        } else {
            partials[j] = x1
            partials.truncate(j + 1)
        }
    }
    val zero: Double = 0.0
    return ArrayDeque(partials).fold(zero, { p, q -> p + q })
}

public fun List<Double>.min(): Double {
    check(!this.isEmpty())
    return this.fold(this[0], { p, q -> p.min(q) })
}

public fun List<Double>.max(): Double {
    check(!this.isEmpty())
    return this.fold(this[0], { p, q -> p.max(q) })
}

public fun List<Double>.mean(): Double {
    check(!this.isEmpty())
    return this.sum() / (this.size.toDouble())
}

public fun List<Double>.median(): Double = this.percentile(50.0)

public fun List<Double>.variable(): Double =
    if (this.size < 2) {
        0.0
    } else {
        val mean = this.mean()
        var v: Double = 0.0
        for (s in this) {
            val x = s - mean
            v += x * x
        }
        // N.B., this is _supposed to be_ len-1, not len. If you
        // change it back to len, you will be calculating a
        // population variance, not a sample variance.
        val denom = (this.size - 1).toDouble()
        v / denom
    }

public fun List<Double>.stdDev(): Double = this.variable().sqrt()

public fun List<Double>.stdDevPct(): Double {
    val hundred = 100.0
    return (this.stdDev() / this.mean()) * hundred
}

public fun List<Double>.medianAbsDev(): Double {
    val med = this.median()
    val absDevs: List<Double> = this.map({ v -> (med - v).abs() }).toMutableList()
    // This constant is derived by smarter statistics brains than me, but it is
    // consistent with how R and other packages treat the MAD.
    val number = 1.4826
    return absDevs.median() * number
}

public fun List<Double>.medianAbsDevPct(): Double {
    val hundred = 100.0
    return (this.medianAbsDev() / this.median()) * hundred
}

public fun List<Double>.percentile(pct: Double): Double {
    val tmp = this.toMutableList()
    localSort(tmp)
    return percentileOfSorted(tmp, pct)
}

public fun List<Double>.quartiles(): Triple<Double, Double, Double> {
    val tmp = this.toMutableList()
    localSort(tmp)
    val first = 25.0
    val a = percentileOfSorted(tmp, first)
    val second = 50.0
    val b = percentileOfSorted(tmp, second)
    val third = 75.0
    val c = percentileOfSorted(tmp, third)
    return Triple(a, b, c)
}

public fun List<Double>.iqr(): Double {
    val (a, _, c) = this.quartiles()
    return c - a
}

// Helper function: extract a value representing the `pct` percentile of a sorted sample-set, using
// linear interpolation. If samples are not sorted, return nonsensical value.
public fun percentileOfSorted(sortedSamples: List<Double>, pct: Double): Double {
    check(!sortedSamples.isEmpty())
    if (sortedSamples.size == 1) {
        return sortedSamples[0]
    }
    val zero: Double = 0.0
    check(zero <= pct)
    val hundred = 100.0
    check(pct <= hundred)
    if (pct == hundred) {
        return sortedSamples[sortedSamples.size - 1]
    }
    val length = (sortedSamples.size - 1).toDouble()
    val rank = (pct / hundred) * length
    val lrank = floor(rank)
    val d = rank - lrank
    val n = lrank.toInt()
    val lo = sortedSamples[n]
    val hi = sortedSamples[n + 1]
    return lo + (hi - lo) * d
}

/**
 * Winsorize a set of samples, replacing values above the `100 - pct` percentile
 * and below the `pct` percentile with those percentiles themselves. This is a
 * way of minimizing the effect of outliers, at the cost of biasing the sample.
 * It differs from trimming in that it does not change the number of samples,
 * just changes the values of those that are outliers.
 *
 * See: <https://en.wikipedia.org/wiki/Winsorising>
 */
public fun winsorize(samples: MutableList<Double>, pct: Double) {
    val tmp = samples.toMutableList()
    localSort(tmp)
    val lo = percentileOfSorted(tmp, pct)
    val hundred = 100.0
    val hi = percentileOfSorted(tmp, hundred - pct)
    for (idx in samples.indices) {
        if (samples[idx] > hi) {
            samples[idx] = hi
        } else if (samples[idx] < lo) {
            samples[idx] = lo
        }
    }
}

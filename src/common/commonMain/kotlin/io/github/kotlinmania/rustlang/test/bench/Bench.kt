// port-lint: source bench.rs
package io.github.kotlinmania.rustlang.test.bench

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.pow
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.io.stdio.setOutputCapture
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.test.BenchMode
import io.github.kotlinmania.rustlang.test.CompletedTest
import io.github.kotlinmania.rustlang.test.Sender
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.TestId
import io.github.kotlinmania.rustlang.test.TestResult
import io.github.kotlinmania.rustlang.test.stats.Summary
import io.github.kotlinmania.rustlang.test.stats.winsorize

// Benchmarking module.

/**
 * An identity function that *__hints__* to the compiler to be maximally pessimistic about what
 * `blackBox` could do.
 *
 * See [std.hint.blackBox] for details.
 */
public fun <T> blackBox(dummy: T): T = dummy

/**
 * Manager of the benchmarking runs.
 *
 * This is fed into functions marked with `@bench` to allow for
 * set-up & tear-down before running a piece of code repeatedly via a
 * call to `iter`.
 */
public open class Bencher(
    public var mode: BenchMode,
    public var summary: Summary?,
    public var bytes: ULong,
) {
    public open fun clone(): Bencher = Bencher(mode, summary, bytes)

    /**
     * Callback for benchmark functions to run in their body.
     */
    public open fun <T> iter(inner: () -> T) {
        if (this.mode == BenchMode.Single) {
            nsIterInner(inner, 1uL)
            return
        }
        this.summary = iter(inner)
    }

    public open fun bench(f: (Bencher) -> Result<Unit, String>): Result<Summary?, String> {
        val result = f(this)
        return result.map({ this.summary })
    }

    public companion object
}

public open class BenchSamples(
    public var nsIterSumm: Summary,
    public var mbS: Int,
) {
    public open fun clone(): BenchSamples = BenchSamples(nsIterSumm, mbS)

    public companion object
}

public fun fmtBenchSamples(bs: BenchSamples): String {
    val output = ""
    val median = bs.nsIterSumm.median
    val deviation = bs.nsIterSumm.max - bs.nsIterSumm.min
    output.append("${fmtThousandsSep(median, ',')} ns/iter (+/- ${fmtThousandsSep(deviation, ',')})").unwrap()
    if (bs.mbS != 0) {
        output.append(" = ${bs.mbS} MB/s").unwrap()
    }
    return output
}

// Format a number with thousands separators
public fun fmtThousandsSep(n: Double, sep: Char): String {
    var n1 = n
    var output = ""
    var trailing = false
    for (pow in listOf(9, 6, 3, 0)) {
        val base = 10.pow(pow.toUInt())
        if (pow == 0 || trailing || n1 / base.toDouble() >= 1.0) {
            run {
                val matchVal = Pair(pow, trailing)
                when {
                    // modern CPUs can execute multiple instructions per nanosecond
                    // e.g. benching an ADD takes about 0.25ns.
                    matchVal.first == 0 && matchVal.second == true -> output.append("${n1 / base.toDouble()}").unwrap()
                    matchVal.first == 0 && matchVal.second == false -> output.append("${n1 / base.toDouble()}").unwrap()
                    matchVal.second == true -> output.append("${n1.toInt() / base}").unwrap()
                    else -> output.append("${n1.toInt() / base}").unwrap()
                }
            }
            if (pow != 0) {
                output += sep
            }
            trailing = true
        }
        n1 %= base.toDouble()
    }
    return output
}

public fun <T> nsIterInner(inner: () -> T, k: ULong): ULong {
    val start = Instant.now()
    for (unused in 0 until k) {
        blackBox(inner())
    }
    return start.elapsed().asNanos().toULong()
}

public fun <T> iter(inner: () -> T): Summary {
    // Initial bench run to get ballpark figure.
    val nsSingle = nsIterInner(inner, 1uL)
    // Try to estimate iter count for 1ms falling back to 1m
    // iterations if first run took < 1ns.
    val nsTargetTotal = 1000000
    // 1ms
    var n = nsTargetTotal / max(1, nsSingle)
    // if the first run took more than 1ms we don't want to just
    // be left doing 0 iterations on every loop. The unfortunate
    // side effect of not being able to do as many runs is
    // automatically handled by the statistical analysis below
    // (i.e., larger error bars).
    n = max(1, n)
    var totalRun = Duration.new(0, 0)
    val samples: MutableList<Double> = MutableList(50) { 0.0 }
    while (true) {
        val loopStart = Instant.now()
        for (idx in samples.indices) {
            samples[idx] = nsIterInner(inner, n).toDouble() / n.toDouble()
        }
        winsorize(samples, 5.0)
        val summ = Summary.new(samples)
        for (idx1 in samples.indices) {
            val ns = nsIterInner(inner, 5uL * n)
            samples[idx1] = ns.toDouble() / (5 * n).toDouble()
        }
        winsorize(samples, 5.0)
        val summ5 = Summary.new(samples)
        val loopRun = loopStart.elapsed()
        // If we've run for 100ms and seem to have converged to a
        // stable median.
        if (loopRun > Duration.fromMillis(100) && summ.medianAbsDevPct < 1.0 && summ.median - summ5.median < summ5.medianAbsDev) {
            return summ5
        }
        totalRun += loopRun
        // Longest we ever run for is 3s.
        if (totalRun > Duration.fromSecs(3)) {
            return summ5
        }
        // If we overflow here just return the results so far. We check a
        // multiplier of 10 because we're about to multiply by 2 and the
        // next iteration of the loop will also multiply by 5 (to calculate
        // the summ5 result)
        n = when (val matchVal = n.checkedMul(10)) {
            null -> {
                return summ5
            }
            else -> n * 2
        }
    }
}

public fun benchmark(id: TestId, desc: TestDesc, monitorCh: Sender<CompletedTest>, nocapture: Boolean, f: (Bencher) -> Result<Unit, String>) {
    val bs = Bencher(mode = BenchMode.Auto, summary = null, bytes = 0uL)
    val data = Mutex.new(mutableListOf())
    if (!nocapture) {
        setOutputCapture(data.clone())
    }
    val result = catchUnwind(AssertUnwindSafe({ bs.bench(f) }))
    setOutputCapture(null)
    val testResult =
        when {
            // bs.bench(f) (
            result is Result.Ok<*, *> && result.okValue() is Result.Ok<*, *> && (result.okValue() as Result.Ok<*, *>).okValue() != null -> {
                val okValueVal = result.okValue()
                val okValueVal1 = okValueVal.okValue()
                val nsIterSumm = okValueVal1
                val nsIter = max(nsIterSumm.median.toULong(), 1)
                val mbS = bs.bytes * 1000uL / nsIter
                val bs1 = BenchSamples(nsIterSumm = nsIterSumm, mbS = mbS.toInt())
                TestResult.TrBench(bs1)
            }
            result is Result.Ok<*, *> && result.okValue() is Result.Ok<*, *> && (result.okValue() as Result.Ok<*, *>).okValue() == null -> {
                val okValueVal2 = result.okValue()
                val None = okValueVal2.okValue()
                // iter not called, so no data.
                // Note: error in this case?
                val samples: MutableList<Double> = MutableList(1) { 0.0 }
                val bs2 = BenchSamples(nsIterSumm = Summary.new(samples), mbS = 0)
                TestResult.TrBench(bs2)
            }
            result is Result.Err<*, *> -> TestResult.TrFailed
            else -> {
                val okValueVal3 = result.okValue()
                TestResult.TrFailed
            }
        }
    val stdout = data.lock().unwrap().toVec()
    val message = CompletedTest.new(id, desc, testResult, null, stdout)
    monitorCh.send(message).unwrap()
}

public fun runOnce(f: (Bencher) -> Result<Unit, String>): Result<Unit, String> {
    val bs = Bencher(mode = BenchMode.Single, summary = null, bytes = 0uL)
    return bs.bench(f).map({ Unit })
}

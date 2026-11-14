// port-lint: source formatters/pretty.rs
package io.github.kotlinmania.rustlang.test.formatters.pretty

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.bench.fmtBenchSamples
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.formatters.OutputFormatter
import io.github.kotlinmania.rustlang.test.term.color.Color
import io.github.kotlinmania.rustlang.test.term.color.cyan
import io.github.kotlinmania.rustlang.test.term.color.green
import io.github.kotlinmania.rustlang.test.term.color.red
import io.github.kotlinmania.rustlang.test.term.color.yellow
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.test.time.TestTimeOptions

internal open class PrettyFormatter<T>(
    public var out: OutputLocation<T>,
    public var useColor: Boolean,
    public var timeOptions: TestTimeOptions?,
    /**
     * Number of columns to fill when aligning names
     */
    public var maxNameLen: Int,
    public var isMultithreaded: Boolean,
) : OutputFormatter {
    internal open fun writeOk(): io.Result<Unit> = this.writeShortResult("ok", green)

    internal open fun writeFailed(): io.Result<Unit> = this.writeShortResult("FAILED", red)

    internal open fun writeIgnored(message: String?): io.Result<Unit> =
        if (message != null) {
            this.writeShortResult("ignored, $message", yellow)
        } else {
            this.writeShortResult("ignored", yellow)
        }

    internal open fun writeTimeFailed(): io.Result<Unit> = this.writeShortResult("FAILED (time limit exceeded)", red)

    internal open fun writeBench(): io.Result<Unit> = this.writePretty("bench", cyan)

    internal open fun writeShortResult(result: String, color: Color): io.Result<Unit> = this.writePretty(result, color)

    internal open fun writePretty(word: String, color: Color): io.Result<Unit> {
        return when (val matchVal = this.out) {
            is OutputLocation.Pretty -> {
                val (term) = matchVal
                if (this.useColor) {
                    when (val tryResult = term.fg(color)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                when (val tryResult = term.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                if (this.useColor) {
                    when (val tryResult = term.reset()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                term.flush()
            }
            is OutputLocation.Raw -> {
                val (stdout) = matchVal
                when (val tryResult = stdout.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                stdout.flush()
            }
        }
    }

    internal open fun writePlain(s: String): io.Result<Unit> {
        val s1 = s
        when (val tryResult = this.out.writeAll(s1.encodeToByteArray().map { it.toUByte() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.out.flush()
    }

    public open fun writeTime(desc: TestDesc, execTime: TestExecTime?): io.Result<Unit> {
        if (Pair(this.timeOptions, execTime) != null) {
            val opts = Pair(this.timeOptions, execTime).first
            val time = Pair(this.timeOptions, execTime).second
            val timeStr = " <$time>"
            val color =
                if (this.useColor) {
                    if (opts.isCritical(desc, time)) {
                        red
                    } else if (opts.isWarn(desc, time)) {
                        yellow
                    } else {
                        null
                    }
                } else {
                    null
                }
            when (color) {
                null -> when (val tryResult = this.writePlain(timeStr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                else -> {
                    val color1 = color
                    when (val tryResult = this.writePretty(timeStr, color1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        }
        return Result.Ok(Unit)
    }

    public open fun writeResults(inputs: List<Pair<TestDesc, List<UByte>>>, resultsType: String): io.Result<Unit> {
        val resultsOutStr = "\n$resultsType:\n"
        when (val tryResult = this.writePlain(resultsOutStr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val results = mutableListOf()
        var stdouts = ""
        for ((f, stdout) in inputs) {
            results.add(f.name.toString())
            if (!stdout.isEmpty()) {
                stdouts += "---- ${f.name} stdout ----\n"
                val output = String.fromUtf8Lossy(stdout)
                stdouts += output
                stdouts += '\n'
            }
        }
        if (!stdouts.isEmpty()) {
            when (val tryResult = this.writePlain("\n")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            when (val tryResult = this.writePlain(stdouts)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        when (val tryResult = this.writePlain(resultsOutStr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        results.sort()
        for (name in results) {
            when (val tryResult = this.writePlain("    $name\n")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        return Result.Ok(Unit)
    }

    internal open fun writeSuccesses(state: ConsoleTestState): io.Result<Unit> = this.writeResults(state.notFailures, "successes")

    internal open fun writeFailures(state: ConsoleTestState): io.Result<Unit> = this.writeResults(state.failures, "failures")

    internal open fun writeTimeFailures(state: ConsoleTestState): io.Result<Unit> = this.writeResults(state.timeFailures, "failures (time limit exceeded)")

    public open fun writeTestName(desc: TestDesc): io.Result<Unit> {
        val name = desc.paddedName(this.maxNameLen, desc.name.padding())
        when (val matchVal = desc.testMode()) {
            null -> {
                when (val tryResult = this.writePlain("test $name ... ")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            else -> {
                val testMode = matchVal
                when (val tryResult = this.writePlain("test $name - $testMode ... ")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
        }
        return Result.Ok(Unit)
    }

    public override fun writeDiscoveryStart(): io.Result<Unit> = Result.Ok(Unit)

    public override fun writeTestDiscovered(desc: TestDesc, testType: String): io.Result<Unit> = this.writePlain("${desc.name}: $testType\n")

    public override fun writeDiscoveryFinish(state: ConsoleTestDiscoveryState): io.Result<Unit> {
        fun plural(count: Int, s: String): String =
            when (count) {
                1 -> "1 $s"
                else -> {
                    val n = count
                    "$n ${s}s"
                }
            }
        if (state.tests != 0 || state.benchmarks != 0) {
            when (val tryResult = this.writePlain("\n")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        return this.writePlain((plural(state.tests, "test")).toString() + ", " + (plural(state.benchmarks, "benchmark")).toString() + "\n")
    }

    public override fun writeRunStart(testCount: Int, shuffleSeed: ULong?): io.Result<Unit> {
        val noun =
            if (testCount != 1) {
                "tests"
            } else {
                "test"
            }
        val shuffleSeedMsg =
            if (shuffleSeed != null) {
                " (shuffle seed: $shuffleSeed)"
            } else {
                ""
            }
        return this.writePlain("\nrunning $testCount $noun$shuffleSeedMsg\n")
    }

    public override fun writeTestStart(desc: TestDesc): io.Result<Unit> {
        // When running tests concurrently, we should not print
        // the test's name as the result will be mis-aligned.
        // When running the tests serially, we print the name here so
        // that the user can see which test hangs.
        if (!this.isMultithreaded) {
            when (val tryResult = this.writeTestName(desc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        return Result.Ok(Unit)
    }

    public override fun writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, arg3: List<UByte>, arg4: ConsoleTestState): io.Result<Unit> {
        if (this.isMultithreaded) {
            when (val tryResult = this.writeTestName(desc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        when (result) {
            TestResult.TrOk -> when (val tryResult = this.writeOk()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            TestResult.TrFailed -> when (val tryResult = this.writeFailed()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            is TestResult.TrFailedMsg -> when (val tryResult = this.writeFailed()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            TestResult.TrIgnored -> when (val tryResult = this.writeIgnored(desc.ignoreMessage)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            is TestResult.TrBench -> {
                val (bs) = result
                when (val tryResult = this.writeBench()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = this.writePlain(": ${fmtBenchSamples(bs)}")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            TestResult.TrTimedFail -> when (val tryResult = this.writeTimeFailed()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        when (val tryResult = this.writeTime(desc, execTime)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.writePlain("\n")
    }

    public override fun writeTimeout(desc: TestDesc): io.Result<Unit> = this.writePlain("test ${desc.name} has been running for over ${time.TEST_WARN_TIMEOUT_S} seconds\n")

    public override fun writeRunFinish(state: ConsoleTestState): io.Result<Boolean> {
        if (state.options.displayOutput) {
            when (val tryResult = this.writeSuccesses(state)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        val success = state.failed == 0
        if (!success) {
            if (!state.failures.isEmpty()) {
                when (val tryResult = this.writeFailures(state)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            if (!state.timeFailures.isEmpty()) {
                when (val tryResult = this.writeTimeFailures(state)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
        }
        when (val tryResult = this.writePlain("\ntest result: ")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        if (success) {
            // There's no parallelism at this point so it's safe to use color
            when (val tryResult = this.writePretty("ok", green)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        } else {
            when (val tryResult = this.writePretty("FAILED", red)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        val s = ". ${state.passed} passed; ${state.failed} failed; ${state.ignored} ignored; ${state.measured} measured; ${state.filteredOut} filtered out"
        when (val tryResult = this.writePlain(s)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val matchVal = state.execTime) {
            null -> {}
            else -> {
                val execTime = matchVal
                val timeStr = "; finished in $execTime"
                when (val tryResult = this.writePlain(timeStr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
        }
        when (val tryResult = this.writePlain("\n\n")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(success)
    }

    public override fun writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): io.Result<Unit> = this.writePlain("all doctests ran in ${totalTime}s; merged doctests compilation took ${compilationTime}s\n")

    public companion object {
        internal fun <T> new(out: OutputLocation<T>, useColor: Boolean, maxNameLen: Int, isMultithreaded: Boolean, timeOptions: TestTimeOptions?): PrettyFormatter<T> = PrettyFormatter(out = out, useColor = useColor, maxNameLen = maxNameLen, isMultithreaded = isMultithreaded, timeOptions = timeOptions)
    }
}

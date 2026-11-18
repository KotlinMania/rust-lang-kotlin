// port-lint: source formatters/pretty.rs
package io.github.kotlinmania.rustlang.test.formatters.pretty

import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.bench.fmtBenchSamples
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.term.Color
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TEST_WARN_TIMEOUT_S
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.test.time.TestTimeOptions
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public open class PrettyFormatter<T>(
    public var out: OutputLocation<T>,
    public var useColor: Boolean,
    public var timeOptions: TestTimeOptions?,
    /**
     * Number of columns to fill when aligning names
     */
    public var maxNameLen: Int,
    public var isMultithreaded: Boolean,
) {
    public open fun writeOk(): Result<Unit> = this.writeShortResult("ok", Color.GREEN)

    public open fun writeFailed(): Result<Unit> = this.writeShortResult("FAILED", Color.RED)

    public open fun writeIgnored(message: String?): Result<Unit> =
        if (message != null) {
            this.writeShortResult("ignored, $message", Color.YELLOW)
        } else {
            this.writeShortResult("ignored", Color.YELLOW)
        }

    public open fun writeTimeFailed(): Result<Unit> = this.writeShortResult("FAILED (time limit exceeded)", Color.RED)

    public open fun writeBench(): Result<Unit> = this.writePretty("bench", Color.CYAN)

    public open fun writeShortResult(result: String, color: Color.Color): Result<Unit> = this.writePretty(result, color)

    public open fun writePretty(word: String, color: Color.Color): Result<Unit> {
        return when (val matchVal = this.out) {
            is OutputLocation.Pretty<*> -> {
                val (term) = matchVal
                if (this.useColor) {
                    when (val tryResult = term.fg(color)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
                when (val tryResult = term.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                if (this.useColor) {
                    when (val tryResult = term.reset()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
                term.flush()
            }
            is OutputLocation.Raw<*> -> {
                val (stdout) = matchVal
                when (val tryResult = stdout.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                stdout.flush()
            }
        }
    }

    public open fun writePlain(s: String): Result<Unit> {
        val s1 = s
        when (val tryResult = this.out.writeAll(s1.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return this.out.flush()
    }

    public open fun writeTime(desc: TestDesc, execTime: TestExecTime?): Result<Unit> {
        if (Pair(this.timeOptions, execTime) != null) {
            val opts = Pair(this.timeOptions, execTime).first
            val time = Pair(this.timeOptions, execTime).second
            val timeStr = " <$time>"
            val color =
                if (this.useColor) {
                    if (opts.isCritical(desc, time)) {
                        Color.RED
                    } else if (opts.isWarn(desc, time)) {
                        Color.YELLOW
                    } else {
                        null
                    }
                } else {
                    null
                }
            when (color) {
                null -> when (val tryResult = this.writePlain(timeStr)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                else -> {
                    val color1 = color
                    when (val tryResult = this.writePretty(timeStr, color1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
            }
        }
        return RustResult.Ok(Unit)
    }

    public open fun writeResults(inputs: List<Pair<TestDesc, List<UByte>>>, resultsType: String): Result<Unit> {
        val resultsOutStr = "\n$resultsType:\n"
        when (val tryResult = this.writePlain(resultsOutStr)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
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
            when (val tryResult = this.writePlain("\n")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when (val tryResult = this.writePlain(stdouts)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        when (val tryResult = this.writePlain(resultsOutStr)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        results.sort()
        for (name in results) {
            when (val tryResult = this.writePlain("    $name\n")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        return RustResult.Ok(Unit)
    }

    public open fun writeSuccesses(state: ConsoleTestState): Result<Unit> = this.writeResults(state.notFailures, "successes")

    public open fun writeFailures(state: ConsoleTestState): Result<Unit> = this.writeResults(state.failures, "failures")

    public open fun writeTimeFailures(state: ConsoleTestState): Result<Unit> = this.writeResults(state.timeFailures, "failures (time limit exceeded)")

    public open fun writeTestName(desc: TestDesc): Result<Unit> {
        val name = desc.paddedName(this.maxNameLen, desc.name.padding())
        when (val matchVal = desc.testMode()) {
            null -> {
                when (val tryResult = this.writePlain("test $name ... ")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            else -> {
                val testMode = matchVal
                when (val tryResult = this.writePlain("test $name - $testMode ... ")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
        }
        return RustResult.Ok(Unit)
    }

    public companion object {
        public fun <T> new(out: OutputLocation<T>, useColor: Boolean, maxNameLen: Int, isMultithreaded: Boolean, timeOptions: TestTimeOptions?): PrettyFormatter<T> = PrettyFormatter(out = out, useColor = useColor, maxNameLen = maxNameLen, isMultithreaded = isMultithreaded, timeOptions = timeOptions)
    }
}

public fun <T : Write<T>> PrettyFormatter<T>.writeDiscoveryStart(): Result<Unit> = RustResult.Ok(Unit)

public fun <T : Write<T>> PrettyFormatter<T>.writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit> = this.writePlain("${desc.name}: $testType\n")

public fun <T : Write<T>> PrettyFormatter<T>.writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit> {
    fun plural(count: Int, s: String): String =
        when (count) {
            1 -> "1 $s"
            else -> {
                val n = count
                "$n ${s}s"
            }
        }
    if (state.tests != 0 || state.benchmarks != 0) {
        when (val tryResult = this.writePlain("\n")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    return this.writePlain((plural(state.tests, "test")).toString() + ", " + (plural(state.benchmarks, "benchmark")).toString() + "\n")
}

public fun <T : Write<T>> PrettyFormatter<T>.writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit> {
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

public fun <T : Write<T>> PrettyFormatter<T>.writeTestStart(desc: TestDesc): Result<Unit> {
    // When running tests concurrently, we should not print
    // the test's name as the result will be mis-aligned.
    // When running the tests serially, we print the name here so
    // that the user can see which test hangs.
    if (!this.isMultithreaded) {
        when (val tryResult = this.writeTestName(desc)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    return RustResult.Ok(Unit)
}

public fun <T : Write<T>> PrettyFormatter<T>.writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, arg3: List<UByte>, arg4: ConsoleTestState): Result<Unit> {
    if (this.isMultithreaded) {
        when (val tryResult = this.writeTestName(desc)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    when (result) {
        TestResult.TrOk -> when (val tryResult = this.writeOk()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        TestResult.TrFailed -> when (val tryResult = this.writeFailed()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        is TestResult.TrFailedMsg -> when (val tryResult = this.writeFailed()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        TestResult.TrIgnored -> when (val tryResult = this.writeIgnored(desc.ignoreMessage)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        is TestResult.TrBench -> {
            val (bs) = result
            when (val tryResult = this.writeBench()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when (val tryResult = this.writePlain(": ${fmtBenchSamples(bs)}")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        TestResult.TrTimedFail -> when (val tryResult = this.writeTimeFailed()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    when (val tryResult = this.writeTime(desc, execTime)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return this.writePlain("\n")
}

public fun <T : Write<T>> PrettyFormatter<T>.writeTimeout(desc: TestDesc): Result<Unit> = this.writePlain("test ${desc.name} has been running for over $TEST_WARN_TIMEOUT_S seconds\n")

public fun <T : Write<T>> PrettyFormatter<T>.writeRunFinish(state: ConsoleTestState): Result<Boolean> {
    if (state.options.displayOutput) {
        when (val tryResult = this.writeSuccesses(state)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    val success = state.failed == 0
    if (!success) {
        if (!state.failures.isEmpty()) {
            when (val tryResult = this.writeFailures(state)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        if (!state.timeFailures.isEmpty()) {
            when (val tryResult = this.writeTimeFailures(state)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
    }
    when (val tryResult = this.writePlain("\ntest result: ")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    if (success) {
        // There's no parallelism at this point so it's safe to use color
        when (val tryResult = this.writePretty("ok", Color.GREEN)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    } else {
        when (val tryResult = this.writePretty("FAILED", Color.RED)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    val s = ". ${state.passed} passed; ${state.failed} failed; ${state.ignored} ignored; ${state.measured} measured; ${state.filteredOut} filtered out"
    when (val tryResult = this.writePlain(s)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val matchVal = state.execTime) {
        null -> {}
        else -> {
            val execTime = matchVal
            val timeStr = "; finished in $execTime"
            when (val tryResult = this.writePlain(timeStr)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
    }
    when (val tryResult = this.writePlain("\n\n")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(success)
}

public fun <T : Write<T>> PrettyFormatter<T>.writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit> = this.writePlain("all doctests ran in ${totalTime}s; merged doctests compilation took ${compilationTime}s\n")
